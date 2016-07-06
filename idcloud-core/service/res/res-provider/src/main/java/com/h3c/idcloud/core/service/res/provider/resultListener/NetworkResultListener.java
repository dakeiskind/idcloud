/*
 * ts-resource
 * 概要：FloatingIpResultListener.java
 *
 * 创建人：徐印
 * 创建日：2015-3-18
 * 更新履历
 * 2015-3-18  徐印  创建
 *
 * @(#)FloatingIpResultListener.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.service.res.provider.resultListener;


import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.h3c.idcloud.core.adapter.pojo.network.Port;
import com.h3c.idcloud.core.adapter.pojo.network.Router;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.NetCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.NetDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgDeleteResult;
import com.h3c.idcloud.core.persist.res.dao.ResExtIpMapper;
import com.h3c.idcloud.core.persist.res.dao.ResExtNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResFloatingIpMapper;
import com.h3c.idcloud.core.persist.res.dao.ResIpMapper;
import com.h3c.idcloud.core.persist.res.dao.ResNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResSecurityGroupMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcPortsMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcRouterMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResExtIp;
import com.h3c.idcloud.core.pojo.dto.res.ResExtNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResFloatingIp;
import com.h3c.idcloud.core.pojo.dto.res.ResNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResSecurityGroup;
import com.h3c.idcloud.core.pojo.dto.res.ResVpc;
import com.h3c.idcloud.core.pojo.dto.res.ResVpcPorts;
import com.h3c.idcloud.core.pojo.dto.res.ResVpcRouter;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.product.api.ResourceRequestHanlder;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import com.h3c.idcloud.infra.log.task.TaskLogger;
import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * FloatingIpResultListener.java
 *
 * @author Chaohong.Mao
 */
@Component
public class NetworkResultListener {

    private static final Logger logger = LoggerFactory.getLogger(VmResultListener.class);

    @Autowired
    private TaskLoggerFactory taskLogger;
    @Autowired
    private ResIpMapper resIpMapper;
    @Autowired
    private ResExtIpMapper resExtIpMapper;
    @Autowired
    private ResExtNetworkMapper resExtNetworkMapper;
    @Autowired
    private ResNetworkMapper resNetworkMapper;
    @Autowired
    private ResFloatingIpMapper resFloatingIpMapper;
    @Autowired
    private ResSecurityGroupMapper resSecurityGroupMapper;
    @Autowired
    private ResVpcMapper resVpcMapper;
    @Autowired
    private ResVpcRouterMapper resVpcRouterMapper;
    @Autowired
    private ResVpcPortsMapper resVpcPortsMapper;

    @Reference(version = "1.0.0", group = "floatingIpOpenHandlerImpl")
    private ResourceRequestHanlder floatingIpOpenHandler;
    @Reference(version = "1.0.0", group = "floatingIpDeleteHandlerImpl")
    private ResourceRequestHanlder floatingIpDeleteHandler;

    /**
     * 处理创建浮动IP结果
     *
     * @param floatingIpCreateResult the floating ip create result
     */
    @Transactional
    public void handleMessage(FloatingIpCreateResult floatingIpCreateResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        ResInstResult result = new ResInstResult();
        logger.info("创建FloatingIp回调 | 返回参数：{}", JsonUtil.toJson(floatingIpCreateResult));
        ResFloatingIp resFloatingIp = this.resFloatingIpMapper.selectByPrimaryKey(floatingIpCreateResult.getId());
        ResExtNetwork resExtNetwork = this.resExtNetworkMapper.selectByPrimaryKey(resFloatingIp.getResExtNetworkSid());
        try {
            // FloatingIp创建成功
            if (floatingIpCreateResult.isSuccess()) {
                String ipAddress = floatingIpCreateResult.getFloatingIpAddr();
                Criteria criteria = new Criteria();
                criteria.put("ipAddress", ipAddress);
                criteria.put("resExtNetworkSid", resExtNetwork.getResExtNetworkSid());
                List<ResExtIp> resIps = this.resExtIpMapper.selectByParams(criteria);
                logger.debug("创建FloatingIp回调 | 查找可用IP个数：{}", resIps.size());
                if (resIps.size() > 0) {
                    resFloatingIp.setResIpSid(resIps.get(0).getResSid());
                    resFloatingIp.setStatus(WebConstants.ResFloatingIpStatus.NORMAL);
                    resFloatingIp.setUuid(floatingIpCreateResult.getFloatingIpId());
                    WebUtil.prepareUpdateParams(resFloatingIp);
                    this.resFloatingIpMapper.updateByPrimaryKeySelective(resFloatingIp);
                }
                TaskLog log = new TaskLog();
                log.setTaskTarget(resExtNetwork.getNetworkName());
                log.setTaskType(WebConstants.TaskType.CREATE_FLOATINGIP);
                taskLogger.success(log);
                result.setStatus(ResInstResult.SUCCESS);
                result.setData(resFloatingIp);

            } else {
                resFloatingIp.setStatus(WebConstants.ResFloatingIpStatus.ERROR);
                WebUtil.prepareUpdateParams(resFloatingIp);
                this.resFloatingIpMapper.updateByPrimaryKeySelective(resFloatingIp);
                logger.error("创建FloatingIp失败：{}", floatingIpCreateResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(resExtNetwork.getNetworkName());
                log.setTaskType(WebConstants.TaskType.CREATE_FLOATINGIP);
                log.setTaskFailureReason(String.format("创建FloatingIp失败：%s", floatingIpCreateResult.getErrMsg()));
                taskLogger.fail(log);
            }

        } catch (Exception e) {
            logger.error("创建FloatingIp回调异常：{}", Throwables.getStackTraceAsString(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(resExtNetwork.getNetworkName());
            log.setTaskType(WebConstants.TaskType.CREATE_FLOATINGIP);
            log.setTaskFailureReason(String.format("创建FloatingIp失败：%s", Throwables.getStackTraceAsString(e)));
            taskLogger.fail(log);
        }
    }

    /**
     * 处理删除FloatingIp结果
     *
     * @param floatingIpDeleteResult the floating ip delete result
     */
    @Transactional
    public void handleMessage(FloatingIpDeleteResult floatingIpDeleteResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        logger.info("删除FloatingIp回调 | 返回参数：{}", JsonUtil.toJson(floatingIpDeleteResult));
        ResFloatingIp resFloatingIp = this.resFloatingIpMapper.selectByPrimaryKey(floatingIpDeleteResult.getResId());
        ResExtIp resExtIp = this.resExtIpMapper.selectByPrimaryKey(resFloatingIp.getResIpSid());
        try {
            if (floatingIpDeleteResult.isSuccess()) {

                // 删除FloatingIp表
                int cnt = this.resFloatingIpMapper.deleteByPrimaryKey(floatingIpDeleteResult.getResId());
                logger.info("删除FloatingIp回调 | 删除FloatingIp数据：{}", cnt > 0 ? "成功" : "失败");
                TaskLog log = new TaskLog();
                log.setTaskTarget(resExtIp.getIpAddress());
                log.setTaskType(WebConstants.TaskType.DELETE_FLOATINGIP);
                taskLogger.success(log);
            } else {
                logger.error("{} 删除失败：{}", resFloatingIp.getIpAddress(), floatingIpDeleteResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(resExtIp.getIpAddress());
                log.setTaskType(WebConstants.TaskType.DELETE_FLOATINGIP);
                log.setTaskFailureReason(String.format("%s删除失败：%s",
                                                       resFloatingIp.getIpAddress(),
                                                       floatingIpDeleteResult.getErrMsg()
                ));
                taskLogger.fail(log);
            }

        } catch (Exception e) {
            logger.error("删除FloatingIp回调异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(resExtIp.getIpAddress());
            log.setTaskType(WebConstants.TaskType.DELETE_FLOATINGIP);
            log.setTaskFailureReason(String.format("%s删除失败：%s",
                                                   resFloatingIp.getIpAddress(),
                                                   ExceptionUtils.getFullStackTrace(e)
            ));
            taskLogger.fail(log);
        }
    }

    /**
     * 创建网络及子网返回结果
     *
     * @param netCreateResult the net create result
     */
    @Transactional
    public void handleMessage(NetCreateResult netCreateResult) {
        try {
            logger.info("创建网络回调 | MQ返回参数：" + JsonUtil.toJson(netCreateResult));
            // 创建成功,设置网络的状态为已创建
            ResNetwork network = this.resNetworkMapper.selectByPrimaryKey(netCreateResult.getResId());
            ResVpc resVpc = this.resVpcMapper.selectByPrimaryKey(network.getParentTopologySid());
            Map<String, Object> options = netCreateResult.getOptions();
            String userAccount = options.get("ownerId").toString();
            Long mgtObjSid = Long.valueOf(options.get("mgtObjSid").toString());
            if (netCreateResult.isSuccess()) {
                logger.info("创建网络回调 | 创建网络成功，更新网络状态");
                // 更新子网状态和UUID
                network.setUuid(netCreateResult.getSubnet().getId());
                network.setStatus(WebConstants.NETWORK_STATUS.CREATE_SUCCESS);

                // 更新网络的状态和UUID
                if (!Strings.isNullOrEmpty(netCreateResult.getId())) {
                    resVpc.setStatus(WebConstants.NETWORK_STATUS.CREATE_SUCCESS);
                    resVpc.setUuid(netCreateResult.getId());
                    WebUtil.prepareUpdateParams(resVpc, userAccount);
                    this.resVpcMapper.updateByPrimaryKeySelective(resVpc);
                }
            } else {
                logger.warn("创建网络回调 | 创建网络失败，更新网络状态: {}", netCreateResult.getErrMsg());
                // 创建失败,设置网络的状态为创建失败
                network.setStatus(WebConstants.NETWORK_STATUS.CREATE_FAILURE);
                if (!Strings.isNullOrEmpty(netCreateResult.getId())) {
                    resVpc.setStatus(WebConstants.NETWORK_STATUS.CREATE_FAILURE);
                    WebUtil.prepareUpdateParams(resVpc, userAccount);
                    this.resVpcMapper.updateByPrimaryKeySelective(resVpc);
                }
            }
            WebUtil.prepareUpdateParams(network, userAccount);
            this.resNetworkMapper.updateByPrimaryKeySelective(network);

            // 公网路由器
            Router router = netCreateResult.getRouter();
            if (router != null) {
                logger.debug("创建网络回调 | 创建路由");
                ResVpcRouter resVpcRouter = new ResVpcRouter();
                resVpcRouter.setParentTopologySid(resVpc.getParentTopologySid());
                resVpcRouter.setRouterId(router.getId());
                resVpcRouter.setRouterName(router.getName());
                if (router.getExternalGateway() != null) {
                    resVpcRouter.setExternalGatewayInfo(JsonUtil.toJson(router.getExternalGateway()));
                }

                resVpcRouter.setStatus(WebConstants.NETWORK_STATUS.CREATE_SUCCESS);
                resVpcRouter.setOwnerId(userAccount);
                resVpcRouter.setMgtObjSid(mgtObjSid);
                WebUtil.prepareInsertParams(resVpcRouter, userAccount);
                this.resVpcRouterMapper.insertSelective(resVpcRouter);
            }

            // Port信息
            Set<Port> ports = netCreateResult.getPorts();
            if (ports != null && ports.size() > 0) {
                logger.debug("创建网络回调 | 创建端口");
                for (Port port : ports) {
                    ResVpcPorts vpcPorts = new ResVpcPorts(port);
                    vpcPorts.setVpcId(resVpc.getResVpcSid());
                    vpcPorts.setOwnerId(userAccount);
                    vpcPorts.setMgtObjSid(mgtObjSid);
                    WebUtil.prepareInsertParams(vpcPorts, userAccount);
                    this.resVpcPortsMapper.insertSelective(vpcPorts);
                }
            }

        } catch (Exception e) {
            logger.error("创建网络回调 | 发生异常：{}", ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
        }

    }

    /**
     * 删除自定义网络返回结果
     *
     * @param netDeleteResult the net delete result
     */
    @Transactional
    public void handleMessage(NetDeleteResult netDeleteResult) {
        try {
            Map<String, Object> options = netDeleteResult.getOptions();
            // 删除网络
            if (options.get("result") == null) {
                logger.info("删除网络回调 | MQ返回:{}", JsonUtil.toJson(netDeleteResult));
                ResVpc resVpc = this.resVpcMapper.selectByUuid(netDeleteResult.getNetId());
                // 删除成功
                if (netDeleteResult.isSuccess()) {
                    this.resVpcMapper.deleteByPrimaryKey(resVpc.getResVpcSid());
                    // 删除网络端口
                    this.resVpcPortsMapper.deleteByParams(new Criteria("vpcId", resVpc.getResVpcSid()));
                } else {
                    resVpc.setStatus(WebConstants.NETWORK_STATUS.REMOVE_FAILURE);
                    this.resVpcMapper.updateByPrimaryKeySelective(resVpc);
                }
            } else {
                logger.info("删除子网回调 | MQ返回:{}", JsonUtil.toJson(netDeleteResult));
                // 删除子网
                Map<String, Boolean> netSids = (Map<String, Boolean>) options.get("result");
                netSids.forEach((key, result) -> {
                    ResNetwork resNetwork = this.resNetworkMapper.selectByUuid(key);
                    if (result) {
                        // 成功， 删除ip表和网络表
                        Criteria example = new Criteria();
                        example.put("resNetworkSidUsingDelete", resNetwork.getResNetworkSid());
                        this.resIpMapper.deleteByParams(example);
                        this.resNetworkMapper.deleteByPrimaryKey(resNetwork.getResNetworkSid());
                    } else {
                        // 删除失败，更新网络状态
                        resNetwork.setStatus(WebConstants.NETWORK_STATUS.REMOVE_FAILURE);
                        this.resNetworkMapper.updateByPrimaryKeySelective(resNetwork);
                    }
                });
            }
        } catch (Exception e) {
            logger.error("删除网络回调 | 异常发生：{}", Throwables.getStackTraceAsString(e));
        }

    }

    /**
     * 处理创建安全组结果
     *
     * @param sgCreateResult the sg create result
     */
    @Transactional
    public void handleMessage(SgCreateResult sgCreateResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);

        ResSecurityGroup resSecurityGroup = resSecurityGroupMapper.selectByPrimaryKey(Long.parseLong(sgCreateResult
                .getResId()));
        try {
            ResInstResult result = new ResInstResult();
            // 安全组创建成功
            if (sgCreateResult.isSuccess()) {
                resSecurityGroup.setUuid(sgCreateResult.getId());
                resSecurityGroup.setStatus(WebConstants.SECURITY_GROUP_STATUS.CREATE_SUCCESS);
                resSecurityGroupMapper.updateByPrimaryKeySelective(resSecurityGroup);

                TaskLog log = new TaskLog();
                log.setTaskTarget(sgCreateResult.getName());
                log.setTaskType(WebConstants.TaskType.CREATE_SECURITY_GROUP);
                taskLogger.success(log);
                result.setStatus(ResInstResult.SUCCESS);
                result.setData(sgCreateResult);
            } else {
                logger.error("创建安全组失败：" + sgCreateResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(sgCreateResult.getName());
                log.setTaskType(WebConstants.TaskType.CREATE_SECURITY_GROUP);
                log.setTaskFailureReason("创建安全组失败：" + sgCreateResult.getErrMsg());
                taskLogger.fail(log);

                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(sgCreateResult.getErrMsg());
            }
        } catch (Exception e) {
            logger.error("创建安全组失败：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(sgCreateResult.getName());
            log.setTaskType(WebConstants.TaskType.CREATE_SECURITY_GROUP);
            log.setTaskFailureReason("创建安全组失败：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);
        }
    }

    /**
     * 处理删除安全组结果
     *
     * @param sgDeleteResult the sg delete result
     */
    @Transactional
    public void handleMessage(SgDeleteResult sgDeleteResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        String resId = sgDeleteResult.getResId();
        ResSecurityGroup resSecurityGroup = resSecurityGroupMapper.selectByPrimaryKey(Long.parseLong(resId));
        try {
            ResInstResult result = new ResInstResult();
            // 安全组创建成功
            if (sgDeleteResult.isSuccess()) {
                resSecurityGroupMapper.deleteByPrimaryKey(Long.parseLong(resId));

                TaskLog log = new TaskLog();
                log.setTaskTarget(resSecurityGroup.getSecurityGroupName());
                log.setTaskType(WebConstants.TaskType.DELETE_SECURITY_GROUP);
                taskLogger.success(log);
                result.setStatus(ResInstResult.SUCCESS);
                result.setData(sgDeleteResult);
            } else {
                logger.error(" 删除安全组失败：" + sgDeleteResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(resSecurityGroup.getSecurityGroupName());
                log.setTaskType(WebConstants.TaskType.DELETE_SECURITY_GROUP);
                log.setTaskFailureReason("删除安全组失败：" + sgDeleteResult.getErrMsg());
                taskLogger.fail(log);

                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(sgDeleteResult.getErrMsg());
            }
        } catch (Exception e) {
            logger.error("删除安全组失败：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(resSecurityGroup.getSecurityGroupName());
            log.setTaskType(WebConstants.TaskType.DELETE_SECURITY_GROUP);
            log.setTaskFailureReason("删除安全组失败：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);
        }
    }

}
