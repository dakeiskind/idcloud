package com.h3c.idcloud.core.service.res.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpAttach;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpCreate;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpDelete;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpDetach;
import com.h3c.idcloud.core.adapter.pojo.network.Port;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpAttachResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpDetachResult;
import com.h3c.idcloud.core.persist.res.dao.ResExtIpMapper;
import com.h3c.idcloud.core.persist.res.dao.ResExtNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResFloatingIpMapper;
import com.h3c.idcloud.core.persist.res.dao.ResNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcPortsMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcRouterMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResExtIp;
import com.h3c.idcloud.core.pojo.dto.res.ResExtNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResFloatingIp;
import com.h3c.idcloud.core.pojo.dto.res.ResNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.res.ResVpc;
import com.h3c.idcloud.core.pojo.dto.res.ResVpcPorts;
import com.h3c.idcloud.core.pojo.dto.res.ResVpcRouter;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.res.api.ResFloatingIpService;
import com.h3c.idcloud.core.service.res.api.base.ResBaseService;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import com.h3c.idcloud.infra.log.task.TaskLogger;
import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service(version = "1.0.0")
@Component
public class ResFloatingIpServiceImpl implements ResFloatingIpService {

    private static final Logger logger = LoggerFactory.getLogger(ResFloatingIpServiceImpl.class);

    @Autowired
    private ResFloatingIpMapper resFloatingIpMapper;

    @Autowired
    private ResExtNetworkMapper resExtNetworkMapper;

    @Autowired
    private TaskLoggerFactory taskLogger;

    @Autowired
    private ResBaseService resBaseService;

    @Autowired
    private ResVmMapper resVmMapper;

    @Autowired
    private ResExtIpMapper resExtIpMapper;

    @Autowired
    private ResNetworkMapper resNetworkMapper;

    @Autowired
    private ResVpcRouterMapper resVpcRouterMapper;

    @Autowired
    private ResVpcMapper resVpcMapper;

    @Autowired
    private ResVpcPortsMapper resVpcPortsMapper;


    /**
     * 申请弹性IP
     *
     * @implSpec JSON
     * {"region":"10","zone":"12e0c825-3ff5-11e5-8c09-005056ba3c46","eip_bandwidth":"1"}
     */
    @Override
    @Transactional
    public ResInstResult applyFloatingIP(ResCommonInst resCommonInst) {
        logger.info("申请FloatingIp | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        String floatingIpSpec = resCommonInst.getResSpecParam();
        JsonNode jsonNode = JsonUtil.fromJson(floatingIpSpec);
        String zone = jsonNode.findValue("zone").getTextValue();
        resCommonInst.setZoneId(zone);

        // 查找可用的floating network
        List<ResExtNetwork> resExtNetworks = this.resExtNetworkMapper.selectAvailableByZone(zone);
        if (resExtNetworks == null || resExtNetworks.size() == 0) {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "没有足够的可用浮动IP资源。");
        }
        ResExtNetwork resExtNetwork = resExtNetworks.get(0);

        // 插入任务表
        TaskLog log = new TaskLog();
        String userAccout = resCommonInst.getUserAccount();
        if (Strings.isNullOrEmpty(userAccout)) {
            log.setAccount("admin");
        } else {
            log.setAccount(userAccout);
        }

        log.setTaskDetail("创建浮动Ip");
        log.setTaskTarget(resExtNetwork.getNetworkName());
        log.setTaskType(WebConstants.TaskType.CREATE_FLOATINGIP);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ResInstResult result = null;
        try {
            // 创建FloatingIp关系
            ResFloatingIp resFloatingIp = new ResFloatingIp();
            resFloatingIp.setResExtNetworkSid(resExtNetwork.getResExtNetworkSid());
            resFloatingIp.setMgtObjSid(resCommonInst.getMgtObjSid());
            resFloatingIp.setBandwidth(Integer.parseInt(jsonNode.get("eip_bandwidth").getTextValue()));
            resFloatingIp.setStatus(WebConstants.ResFloatingIpStatus.CREATING);
            WebUtil.prepareInsertParams(resFloatingIp);
            this.resFloatingIpMapper.insertSelective(resFloatingIp);

            // 创建Driver参数
            FloatingIpCreate floatingIpCreate = new FloatingIpCreate();
            ResVe resVe = this.resBaseService.getVeFromZone(zone);
            this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, floatingIpCreate);
            floatingIpCreate.setId(resFloatingIp.getResSid());
            String region = this.resBaseService.getRegionFromZone(zone);
            floatingIpCreate.setRegion(region);

            // 发送消息
            logger.info("输入MQ参数：" + JsonUtil.toJson(floatingIpCreate));
            MQHelper.sendMessage(floatingIpCreate);
            Map<String, String> resFloatingIpMap = Maps.newHashMap();
            resFloatingIpMap.put(resFloatingIp.getResSid(), WebConstants.ResourceType.RES_FLOATING_IP);
            result = new ResInstResult(ResInstResult.SUCCESS, "", resFloatingIpMap);
        } catch (Exception e) {
            logger.error("创建浮动IP异常：{}", Throwables.getStackTraceAsString(e));
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("创建浮动IP异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;
    }

    /**
     * {
     * "zone":"12e0c825-3ff5-11e5-8c09-005056ba3c46",
     * "floatingIpSid":"5b80c151-fcc3-11e5-9171-5cb90105be61",// ResFLoatingIp#ResSid
     * "subNetSid":"5811e31e-f73d-11e5-a4f0-5cb90105be61",// ResVmNetwork#ResNetworkSid
     * "vmIp":"10.10.103.3",// ResVmNetwork#IpAddress
     * "resVmSid":"fc1ccf9c-fbbe-11e5-9171-5cb90105be61"// ResVmNetwork#ResVmSid
     * }
     */
    @Override
    public ResInstResult attachFloatingIp(ResCommonInst resCommonInst) {
        logger.info("挂载浮动IP | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        String floatingIpSpec = resCommonInst.getResSpecParam();
        JsonNode jsonNode = JsonUtil.fromJson(floatingIpSpec);
        String zone = jsonNode.findValue("zone").getTextValue();
        resCommonInst.setZoneId(zone);

        ResVm resVm = this.resVmMapper.selectByPrimaryKey(jsonNode.get("resVmSid").getTextValue());
        // 插入任务表
        TaskLog log = new TaskLog();
        String userAccount = resCommonInst.getUserAccount();
        if (Strings.isNullOrEmpty(userAccount)) {
            log.setAccount("admin");
        } else {
            log.setAccount(userAccount);
        }

        log.setTaskDetail("挂载浮动IP");
        log.setTaskTarget(resVm.getVmName());
        log.setTaskType(WebConstants.TaskType.ATTACH_FLOATINGIP);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ResInstResult result = new ResInstResult();
        try {
            ResFloatingIp resFloatingIp = this.resFloatingIpMapper.selectByPrimaryKey(jsonNode.get("floatingIpSid")
                                                                                              .getTextValue());

            ResExtIp resExtIp = this.resExtIpMapper.selectByPrimaryKey(resFloatingIp.getResIpSid());

            FloatingIpAttach floatingIpAttach = new FloatingIpAttach();
            String region = this.resBaseService.getRegionFromZone(zone);
            floatingIpAttach.setRegion(region);
            ResVe resVe = this.resBaseService.getVeFromZone(zone);
            this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, floatingIpAttach);

            floatingIpAttach.setServerId(resVm.getUuid());
            floatingIpAttach.setFixedIp(jsonNode.get("vmIp").getTextValue());
            floatingIpAttach.setFloatingIpId(resFloatingIp.getResSid());
            floatingIpAttach.setFloatingIp(resExtIp.getIpAddress());

            // 租户下可以连接公网的路由器ID
            Criteria criteria = new Criteria();
            criteria.put("mgtObjSid", resCommonInst.getMgtObjSid())
                    .put("parentTopologySid", resVe.getResTopologySid());
            List<ResVpcRouter> resVpcRouters = this.resVpcRouterMapper.selectByParams(criteria);
            ResVpcRouter router = resVpcRouters.stream()
                                              .filter(resVpcRouter -> !Strings.isNullOrEmpty(resVpcRouter.getExternalGatewayInfo()))
                                              .findAny()
                                              .get();
            floatingIpAttach.setRouterId(router.getRouterId());
            // 子网的ID
            ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(jsonNode.get("subNetSid").getTextValue());
            floatingIpAttach.setSubnetId(resNetwork.getUuid());
            // 网络的ID
            ResVpc resVpc = this.resVpcMapper.selectByPrimaryKey(resNetwork.getParentTopologySid());
            floatingIpAttach.setNetworkId(resVpc.getUuid());

            logger.info("挂载浮动IP | 输入MQ参数：{}", JsonUtil.toJson(floatingIpAttach));
            Object object = MQHelper.rpc(floatingIpAttach);
            FloatingIpAttachResult floatingIpAttachResult = (FloatingIpAttachResult) object;
            logger.info("挂载浮动IP | MQ返回：{}", JsonUtil.toJson(floatingIpAttachResult));
            ResFloatingIp resFloatingIpVm = this.resFloatingIpMapper.selectByPrimaryKey(floatingIpAttachResult.getFloatingIpId());

            if (floatingIpAttachResult.isSuccess()) {
                resFloatingIpVm.setMappingNetworkSid(resNetwork.getResNetworkSid());
                resFloatingIpVm.setMappingVmSid(resVm.getResVmSid());
                WebUtil.prepareUpdateParams(resFloatingIp, userAccount);
                this.resFloatingIpMapper.updateByPrimaryKeySelective(resFloatingIpVm);

                // 创建端口
                Port port = floatingIpAttachResult.getPort();
                if (port != null) {
                    ResVpcPorts vpcPorts = new ResVpcPorts(port);
                    vpcPorts.setVpcId(resVpc.getResVpcSid());
                    vpcPorts.setOwnerId(userAccount);
                    vpcPorts.setMgtObjSid(resCommonInst.getMgtObjSid());
                    WebUtil.prepareInsertParams(vpcPorts, userAccount);
                    this.resVpcPortsMapper.insertSelective(vpcPorts);
                }

                log = new TaskLog();
                log.setTaskLogSid(taskId);
                taskLogger.success(log);
                result.setStatus(ResInstResult.SUCCESS);
                result.setData(resFloatingIp);
            } else {
                logger.error("{} 挂载浮动IP：{} 失败：{}",
                             resVm.getVmName(),
                             floatingIpAttachResult.getFloatingIp(),
                             floatingIpAttachResult.getErrMsg()
                );
                log = new TaskLog();
                log.setTaskLogSid(taskId);
                log.setTaskFailureReason(
                        resVm.getVmName() + "挂载浮动IP：" + floatingIpAttachResult.getFloatingIp() + "失败：" +
                                floatingIpAttachResult.getErrMsg());
                taskLogger.fail(log);
                result.setStatus(ResInstResult.FAILURE);
                result.setData(resFloatingIp);
            }
        } catch (Exception e) {
            logger.error("挂载浮动IP异常：{}", Throwables.getStackTraceAsString(e));
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("挂载浮动IP异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        } return result;
    }

    /**
     * 卸载FloatingIP
     */
    @Override
    public ResInstResult detachFloatingIp(ResCommonInst resCommonInst) {
        logger.info("卸载浮动IP | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        String floatingIpSpec = resCommonInst.getResSpecParam();
        JsonNode jsonNode = JsonUtil.fromJson(floatingIpSpec);
        String zone = jsonNode.findValue("zone").getTextValue();
        resCommonInst.setZoneId(zone);

        ResVm resVm = this.resVmMapper.selectByPrimaryKey(jsonNode.get("resVmSid").getTextValue());
        // 插入任务表
        TaskLog log = new TaskLog();
        String userAccout = resCommonInst.getUserAccount();
        if (Strings.isNullOrEmpty(userAccout)) {
            log.setAccount("admin");
        } else {
            log.setAccount(userAccout);
        }

        log.setTaskDetail("卸载浮动IP");
        log.setTaskTarget(resVm.getVmName());
        log.setTaskType(WebConstants.TaskType.DETACH_FLOATINGIP);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ResInstResult result = new ResInstResult();
        try {
            ResFloatingIp resFloatingIp = this.resFloatingIpMapper.selectByPrimaryKey(jsonNode.get("floatingIpSid")
                                                                                              .getTextValue());

            ResExtIp resExtIp = this.resExtIpMapper.selectByPrimaryKey(resFloatingIp.getResIpSid());

            FloatingIpDetach floatingIpDetach = new FloatingIpDetach();
            String region = this.resBaseService.getRegionFromZone(zone);
            floatingIpDetach.setRegion(region);
            ResVe resVe = this.resBaseService.getVeFromZone(zone);
            this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, floatingIpDetach);

            floatingIpDetach.setServerId(resVm.getUuid());
            floatingIpDetach.setFloatingIpId(resFloatingIp.getResSid());
            floatingIpDetach.setFloatingIp(resExtIp.getIpAddress());

            logger.info("卸载浮动IP | 输入MQ参数：" + JsonUtil.toJson(floatingIpDetach));
            Object object = MQHelper.rpc(floatingIpDetach);
            FloatingIpDetachResult floatingIpDetachResult = (FloatingIpDetachResult) object;
            ResFloatingIp resFloatingIpVm = this.resFloatingIpMapper.selectByPrimaryKey(floatingIpDetachResult.getFloatingIpId());

            if (floatingIpDetachResult.isSuccess()) {
                ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(resFloatingIpVm.getMappingNetworkSid());
                // 删除端口信息
                this.resVpcPortsMapper.deleteByParams(new Criteria().put("diviceId", resFloatingIp.getUuid())
                                                                    .put("vpcId", resNetwork.getParentTopologySid()));

                // 更新关联信息
                resFloatingIpVm.setMappingIpSid("");
                resFloatingIpVm.setMappingNetworkSid("");
                resFloatingIpVm.setMappingVmSid("");
                WebUtil.prepareUpdateParams(resFloatingIp, userAccout);
                this.resFloatingIpMapper.updateByPrimaryKeySelective(resFloatingIpVm);

                log = new TaskLog();
                log.setTaskLogSid(taskId);
                taskLogger.success(log);
                result.setStatus(ResInstResult.SUCCESS);
                result.setData(resFloatingIp);
            } else {
                logger.error("{} 卸载浮动IP | {} 失败：{}",
                             resVm.getVmName(),
                             resExtIp.getIpAddress(),
                             floatingIpDetachResult.getErrMsg()
                );
                log = new TaskLog();
                log.setTaskLogSid(taskId);
                log.setTaskFailureReason(resVm.getVmName() + "卸载浮动IP：" + resExtIp.getIpAddress() + "失败：" +
                                                 floatingIpDetachResult.getErrMsg());
                taskLogger.fail(log);
                result.setStatus(ResInstResult.FAILURE);
                result.setData(resFloatingIp);
            }
        } catch (Exception e) {
            logger.error("卸载浮动IP异常：{}", Throwables.getStackTraceAsString(e));
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("卸载浮动IP异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;

    }

    /**
     * 释放浮动ip
     *
     * @implSpec JSON
     * {
     * "region":"10",
     * "zone":"12e0c825-3ff5-11e5-8c09-005056ba3c46",
     * "resSid":"5b80c151-fcc3-11e5-9171-5cb90105be61"
     * }
     */
    @Override
    @Transactional
    public ResInstResult deleteFloatingIP(ResCommonInst resCommonInst) {
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        String zone = jsonNode.get("zone").getTextValue();
        resCommonInst.setZoneId(zone);
        ResFloatingIp resFloatingIp = this.resFloatingIpMapper.selectByPrimaryKey(jsonNode.findValue("resSid")
                                                                                          .getTextValue());

        ResExtIp resExtIp = this.resExtIpMapper.selectByPrimaryKey(resFloatingIp.getResIpSid());

        // 插入任务表
        TaskLog log = new TaskLog();
        String userAccout = resCommonInst.getUserAccount();
        if (Strings.isNullOrEmpty(userAccout)) {
            log.setAccount("admin");
        } else {
            log.setAccount(userAccout);
        }

        log.setTaskDetail("删除浮动IP");
        log.setTaskTarget(resExtIp == null ? "" : resExtIp.getIpAddress());
        log.setTaskType(WebConstants.TaskType.DELETE_FLOATINGIP);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ResInstResult result;
        try {
            // 异常数据，不发送底层，直接删除
            if (WebConstants.ResFloatingIpStatus.ERROR.equals(resFloatingIp.getStatus())) {
                int cnt = this.resFloatingIpMapper.deleteByPrimaryKey(jsonNode.findValue("resSid").getTextValue());
                logger.info("删除浮动IP | 删除异常浮动IP {} ：{}", jsonNode.findValue("resSid").getTextValue(), cnt > 0 ? "成功" : "失败");
                return new ResInstResult(ResInstResult.SUCCESS, "", null);
            }
            //更新浮动IP为删除中
            resFloatingIp.setStatus(WebConstants.ResFloatingIpStatus.DELETING);
            this.resFloatingIpMapper.updateByPrimaryKeySelective(resFloatingIp);

            FloatingIpDelete floatingIpDelete = new FloatingIpDelete();
            String region = this.resBaseService.getRegionFromZone(zone);
            floatingIpDelete.setRegion(region);
            ResVe resVe = this.resBaseService.getVeFromZone(zone);
            this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, floatingIpDelete);

            floatingIpDelete.setFloatingIpId(resFloatingIp.getUuid());
            floatingIpDelete.setResId(resFloatingIp.getResSid());
            logger.info("输入MQ参数：" + JsonUtil.toJson(floatingIpDelete));
            MQHelper.sendMessage(floatingIpDelete);
            result = new ResInstResult(ResInstResult.SUCCESS, "", null);
        } catch (Exception e) {
            logger.error("删除浮动IP异常：{}", Throwables.getStackTraceAsString(e));
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("删除浮动IP异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;
    }
}