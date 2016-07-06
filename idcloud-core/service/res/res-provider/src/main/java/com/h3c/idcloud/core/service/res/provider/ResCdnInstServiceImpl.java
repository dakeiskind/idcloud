package com.h3c.idcloud.core.service.res.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.res.dao.ResCdnInstMapper;
import com.h3c.idcloud.core.persist.res.dao.ResCdnMapper;
import com.h3c.idcloud.core.persist.system.dao.MgtObjMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResCdn;
import com.h3c.idcloud.core.pojo.dto.res.ResCdnInst;
import com.h3c.idcloud.core.pojo.dto.res.ResCdnMgtObjDomainFlow;
import com.h3c.idcloud.core.pojo.dto.res.ResCdnMgtObjFlow;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.res.api.ResCdnInstService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.RESTHttpResponse;
import com.h3c.idcloud.infrastructure.common.pojo.User;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.RSETClientUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import com.h3c.idcloud.infra.log.task.TaskLogger;
import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(version = "1.0.0")
@Component
public class ResCdnInstServiceImpl implements ResCdnInstService {
    private static final Logger logger = LoggerFactory.getLogger(ResCdnInstServiceImpl.class);

    @Autowired
    private ResCdnInstMapper resCdnInstMapper;

    @Autowired
    private ResCdnMapper resCdnMapper;

    @Autowired
    private MgtObjMapper mgtObjMapper;

    @Autowired
    private TaskLoggerFactory taskLogger;


    /**
     * 启用CDN
     */
    @Override
    public ResInstResult startCDN(String resCdnSid, long mgtObjSid) {

        MgtObj mgtObj = this.mgtObjMapper.selectByPrimaryKey(mgtObjSid);
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }

        log.setTaskDetail("创建CDN");
        log.setTaskTarget(mgtObj.getName());
        log.setTaskType(WebConstants.TaskType.CREATE_CDN);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();

        ResInstResult resInstResult = new ResInstResult();
        ResCdn resCdn = this.resCdnMapper.selectByPrimaryKey(PropertiesUtil.getProperty("cdn.sid"));
        String cdnAddress = resCdn.getCdnAddress() + "/enable-service";
//		String params="{user:"+mgtObjSid+"}";
        BasicNameValuePair formVlauePair = new BasicNameValuePair("user", StringUtil.nullToEmpty(mgtObjSid));
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(formVlauePair);
        try {
            logger.info("调用CDN地址：" + cdnAddress);
            logger.info("调用CDN参数：" + JsonUtil.toJson(formparams));
            RESTHttpResponse response = RSETClientUtil.post(cdnAddress, formparams);
            if (RESTHttpResponse.SUCCESS.equals(response.getStatus())) {
                Map<String, Object> callback = JsonUtil.fromJson(response.getContent(), Map.class);
                String result = StringUtil.nullToEmpty(callback.get("result"));

                if (WebConstants.CdnResult.SUCCESS.equals(result)) {
                    ResCdnInst resCdnInst = new ResCdnInst();
                    resCdnInst.setCdnSid(resCdn.getCdnSid());
                    resCdnInst.setMgtObjSid(mgtObjSid);
                    resCdnInst.setStatus(WebConstants.CdnInstStatus.NORMAL);
                    WebUtil.prepareInsertParams(resCdnInst);
                    this.resCdnInstMapper.insertSelective(resCdnInst);
                    resInstResult.setStatus(ResInstResult.SUCCESS);
                    resInstResult.setData(resCdnInst);
                } else {
                    logger.error(mgtObjSid + " 启用CDN失败，错误ID：" + callback.get("errorId"));
                    resInstResult.setStatus(ResInstResult.FAILURE);
                    log = new TaskLog();
                    log.setTaskLogSid(taskId);
                    log.setTaskFailureReason(mgtObjSid + " 启用CDN失败，错误ID：" + callback.get("errorId"));
                    taskLogger.fail(log);
                }

            } else {
                logger.error(mgtObjSid + " 启用CDN失败,HTTP请求不成功:" + response.getContent());
                log = new TaskLog();
                log.setTaskLogSid(taskId);
                log.setTaskFailureReason(mgtObjSid + " 启用CDN失败,HTTP请求不成功:" + response.getContent());
                taskLogger.fail(log);
                resInstResult.setStatus(ResInstResult.FAILURE);
            }
        } catch (Exception e) {
            logger.error(mgtObjSid + " 启用CDN失败,异常：" + ExceptionUtils.getFullStackTrace(e));
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason(mgtObjSid + " 启用CDN失败,异常：" + e.getMessage());
            taskLogger.fail(log);
            resInstResult.setStatus(ResInstResult.FAILURE);
        }
        return resInstResult;
    }

    /**
     * 停用CDN
     */
    @Override
    public ResInstResult stopCDN(String resCdnInstSid) {
        ResCdnInst resCdnInst = this.resCdnInstMapper.selectByPrimaryKey(resCdnInstSid);
        MgtObj mgtObj = this.mgtObjMapper.selectByPrimaryKey(resCdnInst.getMgtObjSid());
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }

        log.setTaskDetail("停用CDN");
        log.setTaskTarget(mgtObj.getName());
        log.setTaskType(WebConstants.TaskType.DELETE_CDN);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();

        ResInstResult resInstResult = new ResInstResult();
        ResCdn resCdn = this.resCdnMapper.selectByPrimaryKey(resCdnInst.getCdnSid());

        String cdnAddress = resCdn.getCdnAddress() + "/disable-service";
        BasicNameValuePair formVlauePair = new BasicNameValuePair("user", StringUtil.nullToEmpty(resCdnInst.getMgtObjSid()));
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(formVlauePair);
        try {
            logger.info("调用CDN地址：" + cdnAddress);
            logger.info("调用CDN参数：" + JsonUtil.toJson(formVlauePair));
            RESTHttpResponse response = RSETClientUtil.post(cdnAddress, formparams);
            if (RESTHttpResponse.SUCCESS.equals(response.getStatus())) {
                Map<String, Object> callback = JsonUtil.fromJson(response.getContent(), Map.class);
                String result = StringUtil.nullToEmpty(callback.get("result"));

                if (WebConstants.CdnResult.SUCCESS.equals(result) || WebConstants.CdnResult.OPERATED.equals(result)) {

                    resCdnInst.setStatus(WebConstants.CdnInstStatus.DELETED);
                    WebUtil.prepareUpdateParams(resCdnInst);
                    this.resCdnInstMapper.updateByPrimaryKeySelective(resCdnInst);
                    resInstResult.setStatus(ResInstResult.SUCCESS);
                    resInstResult.setData(resCdnInst);
                } else {
                    logger.error(resCdnInst.getMgtObjSid() + " 停用CDN失败，错误ID：" + callback.get("errorId"));
                    log = new TaskLog();
                    log.setTaskLogSid(taskId);
                    log.setTaskFailureReason(resCdnInst.getMgtObjSid() + " 停用CDN失败，错误ID：" + callback.get("errorId"));
                    taskLogger.fail(log);
                    resInstResult.setStatus(ResInstResult.FAILURE);
                }

            } else {
                logger.error(resCdnInst.getMgtObjSid() + " 停用CDN失败,HTTP请求不成功:" + response.getContent());
                log = new TaskLog();
                log.setTaskLogSid(taskId);
                log.setTaskFailureReason(resCdnInst.getMgtObjSid() + " 停用CDN失败,HTTP请求不成功:" + response.getContent());
                taskLogger.fail(log);
                resInstResult.setStatus(ResInstResult.FAILURE);
            }
        } catch (Exception e) {
            logger.error(resCdnInst.getMgtObjSid() + " 停用CDN失败,异常：" + ExceptionUtils.getFullStackTrace(e));
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason(resCdnInst.getMgtObjSid() + " 停用CDN失败,异常：" + e.getMessage());
            taskLogger.fail(log);
            resInstResult.setStatus(ResInstResult.FAILURE);
        }
        return resInstResult;
    }

    /**
     * 获取租户CDN流量信息
     */
    @Override
    public ResCdnMgtObjFlow getCDNFlow(String resCdnInstSid, String startTime, String stopTime) {

        ResCdnMgtObjFlow resCdnMgtObjFlow = null;

        ResCdnInst resCdnInst = this.resCdnInstMapper.selectByPrimaryKey(resCdnInstSid);
        ResCdn resCdn = this.resCdnMapper.selectByPrimaryKey(resCdnInst.getCdnSid());

        String cdnAddress = resCdn.getCdnAddress() + "/usage/" + resCdnInst.getMgtObjSid() + "/" + startTime + "/" + stopTime;
        logger.info("调用CDN地址：" + cdnAddress);
        try {
            RESTHttpResponse response = RSETClientUtil.get(cdnAddress);
            if (RESTHttpResponse.SUCCESS.equals(response.getStatus())) {
                Map<String, Object> map = JsonUtil.fromJson(response.getContent(), Map.class);
                resCdnMgtObjFlow = new ResCdnMgtObjFlow();
                resCdnMgtObjFlow.setMgtObjSid(StringUtil.nullToEmpty(map.get("user")));
                resCdnMgtObjFlow.setTotalFlow(StringUtil.nullToEmpty(map.get("total")));
                resCdnMgtObjFlow.setStartTime(StringUtil.nullToEmpty(map.get("startTime")));
                resCdnMgtObjFlow.setEndTime(StringUtil.nullToEmpty(map.get("endTime")));
                List<ResCdnMgtObjDomainFlow> resCdnMgtObjDomainFlows = new ArrayList<ResCdnMgtObjDomainFlow>();
                List<Map<String, Object>> domainDetailFlow = (List<Map<String, Object>>) map.get("detail");
                if (domainDetailFlow != null && domainDetailFlow.size() > 0) {
                    for (Map<String, Object> detailMap : domainDetailFlow) {
                        ResCdnMgtObjDomainFlow demainFlow = new ResCdnMgtObjDomainFlow();
                        demainFlow.setDomain(StringUtil.nullToEmpty(detailMap.get("domain")));
                        demainFlow.setDomainFlow(StringUtil.nullToEmpty(detailMap.get("flow")));
                        resCdnMgtObjDomainFlows.add(demainFlow);
                    }
                    resCdnMgtObjFlow.setDomainFlows(resCdnMgtObjDomainFlows);
                }
            } else {
                logger.error(resCdnInst.getMgtObjSid() + "获取租户CDN用量失败,HTTP请求不成功:" + response.getContent());
            }
        } catch (Exception e) {
            logger.error(resCdnInst.getMgtObjSid() + "获取租户CDN用量失败,异常：" + ExceptionUtils.getFullStackTrace(e));
        }
        return resCdnMgtObjFlow;
    }


}