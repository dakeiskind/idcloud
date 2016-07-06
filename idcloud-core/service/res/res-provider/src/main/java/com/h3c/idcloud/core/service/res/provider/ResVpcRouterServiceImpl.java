package com.h3c.idcloud.core.service.res.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.google.common.base.Throwables;
import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.network.ExternalGateway;
import com.h3c.idcloud.core.adapter.pojo.network.Port;
import com.h3c.idcloud.core.adapter.pojo.network.Router;
import com.h3c.idcloud.core.adapter.pojo.network.RouterAddExternalGateway;
import com.h3c.idcloud.core.adapter.pojo.network.RouterAddInterface;
import com.h3c.idcloud.core.adapter.pojo.network.RouterCreate;
import com.h3c.idcloud.core.adapter.pojo.network.RouterDelete;
import com.h3c.idcloud.core.adapter.pojo.network.RouterRemoveInterface;
import com.h3c.idcloud.core.adapter.pojo.network.result.RouterResult;
import com.h3c.idcloud.core.persist.res.dao.ResExtNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcPortsIpMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcPortsMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcRouterMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResExtNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVpcPorts;
import com.h3c.idcloud.core.pojo.dto.res.ResVpcPortsIp;
import com.h3c.idcloud.core.pojo.dto.res.ResVpcRouter;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.service.res.api.ResVpcRouterService;
import com.h3c.idcloud.core.service.res.api.base.ResBaseService;
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
import java.util.Set;

/**
 * Res host service 类.
 *
 * @author Chaohong.Mao
 */
@Service(version = "1.0.0")
@Component
public class ResVpcRouterServiceImpl implements ResVpcRouterService {

    private static final Logger logger = LoggerFactory.getLogger(ResVpcRouterServiceImpl.class);
    @Autowired
    private ResVpcRouterMapper resVpcRouterMapper;
    @Autowired
    private ResVpcPortsMapper resVpcPortsMapper;
    @Autowired
    private ResVpcPortsIpMapper resVpcPortsIpMapper;
    @Autowired
    private ResNetworkMapper resNetworkMapper;
    @Autowired
    private ResExtNetworkMapper resExtNetworkMapper;

    @Autowired
    private ResBaseService resBaseService;


    /**
     * 查询路由器列表
     *
     * @param criteria
     * @return
     */
    @Override
    public List<ResVpcRouter> selectByParams(Criteria criteria) {
        return this.resVpcRouterMapper.selectByParams(criteria);
    }

    /**
     * 根据路由器id，查询路由器信息
     *
     * @param resRouterSid
     * @return list
     */
    @Override
    public ResVpcRouter selectByPrimaryKey(String resRouterSid) {
        return this.resVpcRouterMapper.selectByPrimaryKey(resRouterSid);
    }

    /**
     * 更新路由器信息
     *
     * @param router
     * @return
     */
    @Override
    @Transactional
    public int updateByPrimaryKeySelective(ResVpcRouter router) {
        return this.resVpcRouterMapper.updateByPrimaryKeySelective(router);
    }

    /**
     * @implSpec JSON
     * {
     * "zone":"",
     * "routerName":""
     * }
     */
    @Override
    @Transactional
    public ResVpcRouter createVpcRouter(ResCommonInst resCommonInst) {
        logger.info("创建路由 | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        String zone = jsonNode.get("zone").getTextValue();
        resCommonInst.setZoneId(zone);
        ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());
        String routerName = jsonNode.get("routerName").getTextValue();

        // 检查重名 租户在同一环境下
        Criteria criteria = new Criteria().put("mgtObjSid", resCommonInst.getMgtObjSid())
                                          .put("routerName", routerName)
                                          .put("parentTopologySid", resVe.getResTopologySid());
        List<ResVpcRouter> resVpcRouters = this.resVpcRouterMapper.selectByParams(criteria);
        if (resVpcRouters != null && resVpcRouters.size() > 0) {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "您再该环境下已有同名的路由器，请重新命名。");
        }

        // 和Dirver通讯建立路由器
        RouterCreate routerCreate = new RouterCreate();
        this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, routerCreate);
        routerCreate.setRegion(this.resBaseService.getRegionFromZone(zone));
        routerCreate.setName(routerName);

        RouterResult routerResult;
        try {
            Object result = MQHelper.rpc(routerCreate);
            routerResult = (RouterResult) result;
        } catch (MQException e) {
            logger.error("创建路由 | MQ异常发生：{}", Throwables.getStackTraceAsString(e));
            throw new RpcException(RpcException.BIZ_EXCEPTION, "创建路由失败。");
        }
        logger.info("创建路由 | MQ返回：{}", JsonUtil.toJson(routerResult));
        ResVpcRouter resVpcRouter = null;
        if (routerResult.isSuccess()) {
            Router router = routerResult.getRouter();
            logger.debug("创建路由 | 成功：{}", JsonUtil.toJson(router));
            resVpcRouter = new ResVpcRouter();
            resVpcRouter.setParentTopologySid(resVe.getResTopologySid());
            resVpcRouter.setRouterId(router.getId());
            resVpcRouter.setRouterName(router.getName());
            resVpcRouter.setZone(zone);
            if (router.getExternalGateway() != null) {
                resVpcRouter.setExternalGatewayInfo(JsonUtil.toJson(router.getExternalGateway()));
            }

            resVpcRouter.setStatus(WebConstants.NETWORK_STATUS.CREATE_SUCCESS);
            resVpcRouter.setOwnerId(resCommonInst.getUserAccount());
            resVpcRouter.setMgtObjSid(resCommonInst.getMgtObjSid());
            WebUtil.prepareInsertParams(resVpcRouter, resCommonInst.getUserAccount());
            this.resVpcRouterMapper.insertSelective(resVpcRouter);
        } else {
            logger.error("创建路由 | 失败：{}", routerResult.getErrMsg());
            throw new RpcException(RpcException.BIZ_EXCEPTION, "创建路由失败。");
        }
        return resVpcRouter;

    }

    /**
     * @implSpec JSON
     * {
     * "zone":"",
     * "resRouterSid":""
     * }
     */
    @Override
    @Transactional
    public int removeVpcRouter(ResCommonInst resCommonInst) {
        logger.info("删除路由 | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        String zone = jsonNode.get("zone").getTextValue();
        resCommonInst.setZoneId(zone);
        ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());

        ResVpcRouter resVpcRouter = this.resVpcRouterMapper.selectByPrimaryKey(jsonNode.get("resRouterSid")
                                                                                       .getTextValue());
        if (resVpcRouter == null) {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "您选择的路由器已经被删除，请刷新后重试。");
        }

        RouterDelete routerDelete = new RouterDelete();
        this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, routerDelete);
        routerDelete.setRegion(this.resBaseService.getRegionFromZone(zone));
        routerDelete.setId(resVpcRouter.getRouterId());

        RouterResult routerResult;
        try {
            Object result = MQHelper.rpc(routerDelete);
            routerResult = (RouterResult) result;
        } catch (MQException e) {
            logger.error("删除路由 | MQ异常发生：{}", Throwables.getStackTraceAsString(e));
            throw new RpcException(RpcException.BIZ_EXCEPTION, "删除路由失败。");
        }

        logger.info("删除路由 | MQ返回：{}", JsonUtil.toJson(routerResult));
        int result;
        if (routerResult.isSuccess()) {
            // 删除路由连接的对应的端口
            this.resVpcPortsMapper.deleteByParams(new Criteria("deviceId", resVpcRouter.getRouterId()));

            result = this.resVpcRouterMapper.deleteByPrimaryKey(jsonNode.get("resRouterSid").getTextValue());
        } else {
            logger.error("创建路由 | 失败：{}", routerResult.getErrMsg());
            throw new RpcException(RpcException.BIZ_EXCEPTION, "删除路由失败。");
        }

        return result;
    }

    /**
     * @implSpec JSON
     * {
     * "zone":"",
     * "portName":"",
     * "resRouterSid":"",
     * "resNetworkSid":""
     * }
     */
    @Override
    @Transactional
    public int attachSubNetWithRouter(ResCommonInst resCommonInst) {
        logger.info("关联路由 | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        String zone = jsonNode.get("zone").getTextValue();
        resCommonInst.setZoneId(zone);
        ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());

        ResVpcRouter resVpcRouter = this.resVpcRouterMapper.selectByPrimaryKey(jsonNode.get("resRouterSid")
                                                                                       .getTextValue());
        ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(jsonNode.get("resNetworkSid").getTextValue());

        RouterAddInterface routerAddInterface = new RouterAddInterface();
        this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, routerAddInterface);
        routerAddInterface.setRegion(this.resBaseService.getRegionFromZone(zone));

        routerAddInterface.setSubnetId(resNetwork.getUuid());
        routerAddInterface.setRouterId(resVpcRouter.getRouterId());

        RouterResult routerResult;
        try {
            logger.info("关联路由 | 输入MQ参数：{}", JsonUtil.toJson(routerAddInterface));
            Object result = MQHelper.rpc(routerAddInterface);
            routerResult = (RouterResult) result;
        } catch (MQException e) {
            logger.error("关联路由 | MQ异常发生：{}", Throwables.getStackTraceAsString(e));
            throw new RpcException(RpcException.BIZ_EXCEPTION, "关联路由失败。");
        }

        logger.info("关联路由 | MQ返回：{}", JsonUtil.toJson(routerResult));
        if (routerResult.isSuccess()) {
            String userAccount = resCommonInst.getUserAccount();
            Set<Port> ports = routerResult.getPorts();
            logger.debug("关联路由 | 成功：{}", JsonUtil.toJson(ports));
            for (Port port : ports) {
                // 创建端口
                ResVpcPorts vpcPorts = new ResVpcPorts(port);
                vpcPorts.setPortName(jsonNode.get("portName").getTextValue());
                vpcPorts.setVpcId(resNetwork.getParentTopologySid());
                vpcPorts.setOwnerId(userAccount);
                vpcPorts.setMgtObjSid(resCommonInst.getMgtObjSid());
                WebUtil.prepareInsertParams(vpcPorts, userAccount);
                this.resVpcPortsMapper.insertSelective(vpcPorts);

                // 创建端口ip
                port.getIps().forEach(ip -> {
                    ResVpcPortsIp resVpcPortsIp = new ResVpcPortsIp();
                    resVpcPortsIp.setSubnetId(resNetwork.getResNetworkSid());
                    resVpcPortsIp.setIpAddress(ip);
                    WebUtil.prepareInsertParams(resVpcPortsIp, userAccount);
                    this.resVpcPortsIpMapper.insertSelective(resVpcPortsIp);
                });
            }

        } else {
            logger.error("创建路由 | 失败：{}", routerResult.getErrMsg());
            throw new RpcException(RpcException.BIZ_EXCEPTION, "创建路由失败。");
        }
        return 1;
    }

    /**
     * @implSpec JSON
     * {
     * "zone":"",
     * "resRouterSid":"",
     * "resNetworkSid":""
     * }
     */
    @Override
    @Transactional
    public int dettachSubNetWithRouter(ResCommonInst resCommonInst) {
        logger.info("解绑路由 | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        String zone = jsonNode.get("zone").getTextValue();
        resCommonInst.setZoneId(zone);
        ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());

        ResVpcRouter resVpcRouter = this.resVpcRouterMapper.selectByPrimaryKey(jsonNode.get("resRouterSid")
                                                                                       .getTextValue());
        ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(jsonNode.get("resNetworkSid").getTextValue());

        RouterRemoveInterface routerRemoveInterface = new RouterRemoveInterface();
        this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, routerRemoveInterface);
        routerRemoveInterface.setRegion(this.resBaseService.getRegionFromZone(zone));

        routerRemoveInterface.setSubnetId(resNetwork.getUuid());
        routerRemoveInterface.setRouterId(resVpcRouter.getRouterId());

        RouterResult routerResult;
        try {
            logger.info("解绑路由 | 输入MQ参数：{}", JsonUtil.toJson(routerRemoveInterface));
            Object result = MQHelper.rpc(routerRemoveInterface);
            routerResult = (RouterResult) result;
        } catch (MQException e) {
            logger.error("解绑路由 | MQ异常发生：{}", Throwables.getStackTraceAsString(e));
            throw new RpcException(RpcException.BIZ_EXCEPTION, "解绑路由失败。");
        }

        logger.info("解绑路由 | MQ返回：{}", JsonUtil.toJson(routerResult));
        if (routerResult.isSuccess()) {
            Criteria param = new Criteria().put("deviceId", resVpcRouter.getRouterId())
                                           .put("vpcId", resNetwork.getParentTopologySid());
            List<ResVpcPorts> resVpcPortses = this.resVpcPortsMapper.selectByParams(param);
            ResVpcPorts resVpcPorts = resVpcPortses.get(0);
            // 删除端口IP
            this.resVpcPortsIpMapper.deleteByParams(new Criteria().put("subnetId", resNetwork.getResNetworkSid())
                                                                  .put("resPortSid", resVpcPorts.getResPortSid()));
            // 删除端口
            this.resVpcPortsMapper.deleteByPrimaryKey(resVpcPorts.getResPortSid());

        } else {
            logger.error("解绑路由 | 失败：{}", routerResult.getErrMsg());
            throw new RpcException(RpcException.BIZ_EXCEPTION, "解绑路由失败。");
        }
        return 1;
    }

    /**
     * @implSpec JSON
     * {
     * "zone":"",
     * "portName":"",
     * "resRouterSid":"",
     * "resExtNetworkSid":""
     * }
     */
    @Override
    @Transactional
    public int attachExtNetWithRouter(ResCommonInst resCommonInst) {
        logger.info("路由关联外部网络 | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        String zone = jsonNode.get("zone").getTextValue();
        resCommonInst.setZoneId(zone);
        ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());

        ResVpcRouter resVpcRouter = this.resVpcRouterMapper.selectByPrimaryKey(jsonNode.get("resRouterSid")
                                                                                       .getTextValue());
        ResExtNetwork resExtNetwork = this.resExtNetworkMapper.selectByPrimaryKey(jsonNode.get("resExtNetworkSid")
                                                                                          .getTextValue());

        RouterAddExternalGateway routerAddExternalGateway = new RouterAddExternalGateway();
        this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, routerAddExternalGateway);
        routerAddExternalGateway.setRegion(this.resBaseService.getRegionFromZone(zone));

        routerAddExternalGateway.setRouterId(resVpcRouter.getRouterId());
        ExternalGateway externalGateway = ExternalGateway.builder()
                                                         .networkId(resExtNetwork.getUuid())
                                                         .enableSnat(true)
                                                         .build();
        routerAddExternalGateway.setExternalGateway(externalGateway);

        RouterResult routerResult;
        try {
            logger.info("路由关联外部网络 | 输入MQ参数：{}", JsonUtil.toJson(routerAddExternalGateway));
            Object result = MQHelper.rpc(routerAddExternalGateway);
            routerResult = (RouterResult) result;
        } catch (MQException e) {
            logger.error("路由关联外部网络 | MQ异常发生：{}", Throwables.getStackTraceAsString(e));
            throw new RpcException(RpcException.BIZ_EXCEPTION, "路由关联外部网络失败。");
        }

        logger.info("路由关联外部网络 | MQ返回：{}", JsonUtil.toJson(routerResult));
        if (routerResult.isSuccess()) {
            String userAccount = resCommonInst.getUserAccount();
            logger.debug("路由关联外部网络 | 成功：{}", JsonUtil.toJson(routerResult));
            // 更新网关信息
            resVpcRouter.setExternalGatewayInfo(JsonUtil.toJson(routerResult.getRouter().getExternalGateway()));
            WebUtil.prepareUpdateParams(resVpcRouter, userAccount);
            this.resVpcRouterMapper.updateByPrimaryKeySelective(resVpcRouter);

            // 创建端口
            if (routerResult.getPorts() != null) {
                for (Port port : routerResult.getPorts()) {
                    ResVpcPorts vpcPorts = new ResVpcPorts(port);
                    vpcPorts.setPortName(jsonNode.get("portName").getTextValue());
                    vpcPorts.setVpcId(resExtNetwork.getResExtNetworkSid());
                    vpcPorts.setOwnerId(userAccount);
                    vpcPorts.setMgtObjSid(resCommonInst.getMgtObjSid());
                    WebUtil.prepareInsertParams(vpcPorts, userAccount);
                    this.resVpcPortsMapper.insertSelective(vpcPorts);
                }
            }
        } else {
            logger.error("路由关联外部网络 | 失败：{}", routerResult.getErrMsg());
            throw new RpcException(RpcException.BIZ_EXCEPTION, "路由关联外部网络失败。");
        }
        return 1;
    }
}