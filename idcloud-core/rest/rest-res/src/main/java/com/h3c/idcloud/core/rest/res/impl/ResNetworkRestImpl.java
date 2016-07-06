package com.h3c.idcloud.core.rest.res.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;
import com.h3c.idcloud.core.pojo.dto.res.*;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.rest.res.ResNetworkRest;
import com.h3c.idcloud.core.service.res.api.*;
import com.h3c.idcloud.core.service.res.api.base.ResBaseService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;

/**
 * Created by jpg on 2016/3/2.
 */
@Component
public class ResNetworkRestImpl implements ResNetworkRest {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Context
    private HttpServletRequest request;

    /** 资源网络Service */
    @Reference(version = "1.0.0")
    private ResNetworkService resNetworkService;

    /** 资源网络Service */
    @Reference(version = "1.0.0")
    private ResVpcService resVpcService;

    /** 资源网络Service */
    @Reference(version = "1.0.0")
    private ResTopologyConfigService resTopologyConfigService;

    /** 资源网络Service */
    @Reference(version = "1.0.0")
    private ResBaseService resBaseService;

    /** 资源网络Service */
    @Reference(version = "1.0.0")
    private ResVpcRouterService resVpcRouterService;

    /** 资源网络Service */
    @Reference(version = "1.0.0")
    private ResVpcPortsService resVpcPortsService;

    /** 资源网络Service */
    @Reference(version = "1.0.0")
    private ResVmService resVmService;


    /**
     * 根据条件查询VPC列表
     *
     * @param info
     * @return
     */
    @Override
    public Response findVpcByParams(@Context UriInfo info,@Context HttpServletRequest request) {
        Criteria example = new Criteria();
        MultivaluedMap<String, String> param =  info.getQueryParameters();

        if (!StringUtil.isNullOrEmpty(param.getFirst("zoneSid"))) {
            example.put("zone",param.getFirst("zoneSid"));
        }
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        example.put("mgtObjSid",authUser.getMgtObjSid());
        example.setOrderByClause("A.CREATED_DT DESC");
        List<ResVpc> list = this.resVpcService.selectByParams(example);
        return Response.ok(new RestResult(list)).build();
    }

    /**
     * 根据条件查询subnet列表
     *
     * @param info
     * @return
     */
    @Override
    public Response findSubnetByParams(@Context UriInfo info,@Context HttpServletRequest request) {
        Criteria example = new Criteria();
        MultivaluedMap<String, String> param =  info.getQueryParameters();

        if (!StringUtil.isNullOrEmpty(param.getFirst("zoneSid"))) {
            example.put("zone",param.getFirst("zoneSid"));
        }
        if (!StringUtil.isNullOrEmpty(param.getFirst("parentTopologySid"))) {
            example.put("parentTopologySid",param.getFirst("parentTopologySid"));
        }

        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        example.put("mgtObjSid",authUser.getMgtObjSid());
        example.setOrderByClause("A.CREATED_DT DESC");
        List<ResNetwork> list = this.resNetworkService.selectSubnetInVpc(example);
        return Response.ok(new RestResult(list)).build();
    }

    /**
     * 根据条件查询路由器(router)列表
     *
     * @param info
     * @return
     */
    @Override
    public Response findRouterByParams(@Context UriInfo info,@Context HttpServletRequest request) {
        Criteria example = new Criteria();
        MultivaluedMap<String, String> param =  info.getQueryParameters();

        if (!StringUtil.isNullOrEmpty(param.getFirst("zoneSid"))) {
            example.put("zone",param.getFirst("zoneSid"));
        }

        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        example.put("mgtObjSid",authUser.getMgtObjSid());

        List<ResVpcRouter> list = this.resVpcRouterService.selectByParams(example);
        return Response.ok(new RestResult(list)).build();
    }

    /**
     * 根据条件查询端口(ports)列表
     *
     * @param routerId
     * @return
     */
    @Override
    public Response findPortsByRouterId(String routerId,@Context HttpServletRequest request) {
        Criteria example = new Criteria();

        if (!StringUtil.isNullOrEmpty(routerId)) {
            example.put("deviceId",routerId);
        }

        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        example.put("mgtObjSid",authUser.getMgtObjSid());

        List<ResVpcPorts> list = this.resVpcPortsService.selectByParams(example);
        return Response.ok(new RestResult(list)).build();
    }

    /**
     * 更新vpc信息
     *
     * @param vpc
     * @return
     */
    @Override
    public Response updateVpc(ResVpc vpc) {
        String returnJson = "";
        int result = this.resVpcService.updateByPrimaryKeySelective(vpc);
        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
        }
        return Response.status(Response.Status.OK).entity(returnJson).build();
    }

    /**
     * 更新subnet信息
     *
     * @param network
     * @return
     */
    @Override
    public Response updateSubnet(ResNetwork network) {
        String returnJson = "";
        int result = this.resNetworkService.updateByPrimaryKeySelective(network);
        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
        }
        return Response.status(Response.Status.OK).entity(returnJson).build();
    }

    /**
     * 更新路由器信息
     *
     * @param router@return
     */
    @Override
    public Response updateRouter(ResVpcRouter router) {
        String returnJson = "";
        int result = this.resVpcRouterService.updateByPrimaryKeySelective(router);
        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
        }
        return Response.status(Response.Status.OK).entity(returnJson).build();
    }

    /**
     * 更新端口信息
     *
     * @param ports
     * @return
     */
    @Override
    public Response updatePorts(ResVpcPorts ports) {
        String returnJson = "";
        int result = this.resVpcPortsService.updateByPrimaryKeySelective(ports);
        if (1 == result) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
        }
        return Response.status(Response.Status.OK).entity(returnJson).build();
    }

    @Override
    public Response statisticalTopologyOfNetwork(String resTopologySid) {
        ResNetwork list = this.resNetworkService.statisticalTopologyOfNetwork(resTopologySid);
        String json = JsonUtil.toJson(list);
        return Response.ok(json).build();

    }

    /**
     * 统计业务下的网络信息
     */
    @Override
    public Response statisticalBizOfNetwork(
            @PathParam("resBizSid") Long resBizSid) {

        Criteria example = new Criteria();
        example.put("resBizSid", resBizSid);
        ResNetwork list = this.resNetworkService.statisticalBizOfNetwork(example);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.status(Response.Status.OK).entity(json).build();
    }

    /**
     * 查询业务的网络
     */
    @Override
    public Response findBizNetwork(ResNetwork network) {
        Criteria example = new Criteria();
        if (!StringUtil.isNullOrEmpty(network.getNetworkNameLike())) {
            example.put("networkNameLike", network.getNetworkNameLike());
        }

        if (!StringUtil.isNullOrEmpty(network.getIpType())) {
            example.put("ipType", network.getIpType());
        }

        if (!StringUtil.isNullOrEmpty(network.getIpOwnerNetwork())) {
            example.put("ipOwnerNetwork", network.getIpOwnerNetwork());
        }

        if (!StringUtil.isNullOrEmpty(network.getNetworkType())) {
            example.put("networkType", network.getNetworkType());
        }

        if (!StringUtil.isNullOrEmpty(network.getParentTopologySid())) {
            example.put("parentTopologySid", network.getParentTopologySid());
        }
        if (!StringUtil.isNullOrEmpty(network.getResBizSid())) {
            example.put("resBizSid", network.getResBizSid());
        }
        example.setOrderByClause("A.NETWORK_NAME ASC");
        List<ResNetwork> list = this.resNetworkService.selectByBizParams(example);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @Override
    public Response findNetworkByMgtObj(String resZoneSid, String mgtObjSid) {
        ServiceBaseInput resBaseInst = new ServiceBaseInput();
        resBaseInst.setZoneId(resZoneSid);
        resBaseInst.setMgtObjSid(Long.parseLong(mgtObjSid));
        List<ResVpc> vpc = this.resNetworkService.findNetworkByTenant(resBaseInst);
        return Response.ok(new RestResult(vpc)).build();
    }

    @Override
    public Response findSubnetByNetwork(String resVpcId) {
        Map<String, String> result = this.resNetworkService.findSubnetByNetwork(resVpcId);
        List<Map<String, String>> list = Lists.newArrayList();
        result.forEach((key, val) -> list.add(ImmutableMap.of("id", key, "val", val)));
        return Response.ok(new RestResult(list)).build();
    }

    @Override
    public Response createDefaultNetwork(Map<String, String> paramMap) {
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        // TODO RestParam zone => SpecParam
        ResCommonInst resNetInst = new ResCommonInst();
        resNetInst.setMgtObjSid(authUser.getMgtObjSid());
        resNetInst.setUserAccount(authUser.getAccount());
        resNetInst.setUserPass("1");

        String param = "{\n" +
                "zone:\"" + paramMap.get("zone") + "\"\n" +
                "vpcName:\"default-vpc\",\n" +
                "vpcDescription:\"\",\n" +
                "vpcSegment:\"\",\n" +
                "subNet:{\n" +
                "     subNetName:\"default-net\",\n" +
                "     subNetDescription:\"\",\n" +
                "     subNetCidr:\"10.10.100.0/24\"\n" +
                "}";
        resNetInst.setResSpecParam(param);
        String netsid = this.resNetworkService.createNetworkWithSub(resNetInst);
        ResNetwork resNetwork = this.resNetworkService.selectByPrimaryKey(netsid);
        RestResult restResult = new RestResult(resNetwork);
        return Response.status(Response.Status.OK).entity(restResult).build();
    }

    @Override
    public Response createVpcWithSubnet(@Context HttpServletRequest request) {

        ResCommonInst resNetInst = RequestContextUtil.buildResBaseInfo(request,ResCommonInst.class,true);
        String netsid = this.resNetworkService.createNetworkWithSub(resNetInst);

        RestResult restResult;
        if (Strings.isNullOrEmpty(netsid)) {
            restResult = new RestResult(RestResult.Status.FAILURE,
                                        WebUtil.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)
            );
        } else {
            restResult = new RestResult(RestResult.Status.SUCCESS,
                                        WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)
            );
        }
        return Response.status(Response.Status.OK).entity(restResult).build();
    }

    /**
     * Create router.
     *
     * @param request  the request
     * @return the response
     */
    @Override
    public Response createRouter(@Context HttpServletRequest request) {

        ResCommonInst resNetInst = RequestContextUtil.buildResBaseInfo(request,ResCommonInst.class,true);
        ResVpcRouter router = this.resVpcRouterService.createVpcRouter(resNetInst);

        RestResult restResult;
        if (StringUtil.isNullOrEmpty(router)) {
            restResult = new RestResult(RestResult.Status.FAILURE,
                    WebUtil.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)
            );
        } else {
            restResult = new RestResult(RestResult.Status.SUCCESS,
                    WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)
            );
        }
        return Response.status(Response.Status.OK).entity(restResult).build();
    }

    /**
     * remove router.
     *
     * @param request  the request
     * @return the response
     */
    @Override
    public Response deleteRouter(@Context HttpServletRequest request) {

        ResCommonInst resNetInst = RequestContextUtil.buildResBaseInfo(request,ResCommonInst.class,true);
        int result = this.resVpcRouterService.removeVpcRouter(resNetInst);

        RestResult restResult;
        if (1 != result) {
            restResult = new RestResult(RestResult.Status.FAILURE,
                    WebUtil.getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)
            );
        } else {
            restResult = new RestResult(RestResult.Status.SUCCESS,
                    WebUtil.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)
            );
        }
        return Response.status(Response.Status.OK).entity(restResult).build();
    }

    /**
     * 网络关联路由器
     *
     * @param request the request
     * @return the response
     */
    @Override
    public Response attachSubNetWithRouter(@Context HttpServletRequest request) {

        ResCommonInst resNetInst = RequestContextUtil.buildResBaseInfo(request,ResCommonInst.class,true);
        int result = this.resVpcRouterService.attachSubNetWithRouter(resNetInst);

        RestResult restResult;
        if (1 != result) {
            restResult = new RestResult(RestResult.Status.FAILURE,
                    WebUtil.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)
            );
        } else {
            restResult = new RestResult(RestResult.Status.SUCCESS,
                    WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)
            );
        }
        return Response.status(Response.Status.OK).entity(restResult).build();
    }

    /**
     * 网络解绑路由器
     *
     * @param request the request
     * @return the response
     */
    @Override
    public Response dettachSubNetWithRouter(@Context HttpServletRequest request) {
        ResCommonInst resNetInst = RequestContextUtil.buildResBaseInfo(request,ResCommonInst.class,true);
        int result = this.resVpcRouterService.dettachSubNetWithRouter(resNetInst);

        RestResult restResult;
        if (1 != result) {
            restResult = new RestResult(RestResult.Status.FAILURE,
                    WebUtil.getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)
            );
        } else {
            restResult = new RestResult(RestResult.Status.SUCCESS,
                    WebUtil.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)
            );
        }
        return Response.status(Response.Status.OK).entity(restResult).build();
    }

    /**
     * remove vpc.
     *
     * @param request  the request
     * @return the response
     */
    @Override
    public Response deleteVpc(@Context HttpServletRequest request) {

        ResCommonInst resNetInst = RequestContextUtil.buildResBaseInfo(request,ResCommonInst.class,true);
        int result = this.resNetworkService.deleteNetwork(resNetInst);

        RestResult restResult;
        if (1 != result) {
            restResult = new RestResult(RestResult.Status.FAILURE,
                    WebUtil.getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)
            );
        } else {
            restResult = new RestResult(RestResult.Status.SUCCESS,
                    WebUtil.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)
            );
        }
        return Response.status(Response.Status.OK).entity(restResult).build();
    }

    /**
     * remove subnetwork.
     *
     * @param request  the request
     * @return the response
     */
    @Override
    public Response deleteSubnetwork(@Context HttpServletRequest request) {

        ResCommonInst resNetInst = RequestContextUtil.buildResBaseInfo(request,ResCommonInst.class,true);
        int result = this.resNetworkService.deleteSubNets(resNetInst);

        RestResult restResult;
        if (1 != result) {
            restResult = new RestResult(RestResult.Status.FAILURE,
                    WebUtil.getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)
            );
        } else {
            restResult = new RestResult(RestResult.Status.SUCCESS,
                    WebUtil.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)
            );
        }
        return Response.status(Response.Status.OK).entity(restResult).build();
    }

    /**
     * Create vpc with subnet response.
     *
     * @param request  the request
     * @return the response
     */
    @Override
    public Response createSubnet(@Context HttpServletRequest request) {

        ResCommonInst resNetInst = RequestContextUtil.buildResBaseInfo(request,ResCommonInst.class,true);
        String netsid = this.resNetworkService.createSubnet(resNetInst);

        RestResult restResult;
        if (Strings.isNullOrEmpty(netsid)) {
            restResult = new RestResult(RestResult.Status.FAILURE,
                    WebUtil.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)
            );
        } else {
            restResult = new RestResult(RestResult.Status.SUCCESS,
                    WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)
            );
        }
        return Response.status(Response.Status.OK).entity(restResult).build();
    }

    /**
     * Create vpc with subnet response.
     *
     * @param zoneSid
     * @return the response
     */
    @Override
    public Response findTopologyData(String zoneSid,@Context HttpServletRequest request) {

        Map<String,Object> map = new HashMap<String,Object>();
        //
        List<Object> networkList = new ArrayList<Object>();
        List<Object> routerList = new ArrayList<Object>();
        List<Object> serverList = new ArrayList<Object>();
        List<Object> portsList = new ArrayList<Object>();
        // 固定数据-外部网络(唯一)
        Map<String,Object> extNetwork = new HashMap<String,Object>();
        extNetwork.put("status","ACTIVE");
        extNetwork.put("subnets",new ArrayList());
        extNetwork.put("name","public");
        extNetwork.put("router:external",true);
        extNetwork.put("url","");
        extNetwork.put("id","f7c09947-1345-4783-bdcd-6e85c817beb6");
        networkList.add(extNetwork);

        // 公网-路由器
        Criteria publicRouter = new Criteria();
        publicRouter.put("zone",zoneSid);
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        publicRouter.put("mgtObjSid",authUser.getMgtObjSid());
        publicRouter.put("externalGatewayInfoNotNull","yes"); // 公网路由器
        List<ResVpcRouter> rou = this.resVpcRouterService.selectByParams(publicRouter);
        if(rou != null && rou.size() > 0){
            ResVpcRouter router = rou.get(0);

            Map<String,Object> extRouter = new HashMap<String,Object>();
            extRouter.put("status","ACTIVE");
            Map<String,Object> egi = new HashMap<String,Object>();
            egi.put("network_id","f7c09947-1345-4783-bdcd-6e85c817beb6");
            egi.put("enable_snat",true);
            egi.put("external_fixed_ips",new ArrayList());
            extRouter.put("external_gateway_info",egi);
            extRouter.put("name",router.getRouterName());
            extRouter.put("url","");
            extRouter.put("id",router.getRouterId());
            routerList.add(extRouter);

            // 公网-端口
            Map<String,Object> extPort = new HashMap<String,Object>();
            extPort.put("status","ACTIVE");
            extPort.put("network_id","f7c09947-1345-4783-bdcd-6e85c817beb6");
            extPort.put("url","");
            extPort.put("device_owner","network:router_gateway");
            extPort.put("fixed_ips",new ArrayList());
            extPort.put("id","f2f69a89-0798-11e6-bd47-0250f2000002");
            extPort.put("device_id",router.getRouterId());
            portsList.add(extPort);
        }

        // 路由器
        Criteria router = new Criteria();
        router.put("zone",zoneSid);
        router.put("mgtObjSid",authUser.getMgtObjSid());
        router.put("externalGatewayInfoNull","yes");
        List<ResVpcRouter> pnRouter = this.resVpcRouterService.selectByParams(publicRouter);
        if(pnRouter != null && pnRouter.size() > 0){
            for(ResVpcRouter vpcRou : pnRouter){
                Map<String,Object> extRouter = new HashMap<String,Object>();
                extRouter.put("status","ACTIVE");
                extRouter.put("external_gateway_info",null);
                extRouter.put("name",vpcRou.getRouterName());
                extRouter.put("url","");
                extRouter.put("id",vpcRou.getRouterId());
                routerList.add(extRouter);
            }
        }

        // VPC网络
        Criteria example = new Criteria();
        example.put("zone",zoneSid);
        example.put("mgtObjSid",authUser.getMgtObjSid());
        List<ResVpc> vpcList = this.resVpcService.selectByParams(example);
        if(vpcList != null && vpcList.size() > 0){
            for(ResVpc vpc:vpcList){
                Map<String,Object> vpcMap  = new HashMap<String,Object>();
                vpcMap.put("status","ACTIVE");
                // 查询子网
                example = new Criteria();
                example.put("parentTopologySid",vpc.getResVpcSid());
                List<ResNetwork> subnetList = this.resNetworkService.selectByParams(example);
                List snList =  new ArrayList();
                if(subnetList != null && subnetList.size() > 0){
                    for(ResNetwork network:subnetList){
                        Map<String,String> sub = new HashMap<String,String>();
                        sub.put("url","");
                        sub.put("cidr",network.getSubnet()+"/24");
                        sub.put("id",network.getResNetworkSid());
                        snList.add(sub);
                    }
                }
                vpcMap.put("subnets",snList);
                vpcMap.put("name",vpc.getVpcName());
                vpcMap.put("router:external",false);
                vpcMap.put("url","");
                vpcMap.put("id",vpc.getResVpcSid());
                networkList.add(vpcMap);

                // 添加路由器或server和port
                example = new Criteria();
                example.put("vpcId",vpc.getUuid());
                List<ResVpcPorts> portList = this.resVpcPortsService.selectByParams(example);
                if(portList != null && portList.size() > 0){
                    for(ResVpcPorts port:portList){
                        if("network:router_interface".equals(port.getDeviceOwner())){
                            // 添加到路由器list，同时增加一条port
                            example = new Criteria();
                            example.put("routerId",port.getDeviceId());
                            List<ResVpcRouter> vpcRouter = this.resVpcRouterService.selectByParams(example);
                            // 判断当前路由是否在路由器list中
                            boolean isok = false;
                            for(int i=0;i<routerList.size();i++){
                                Map<String,Object> mapp = (Map<String, Object>) routerList.get(i);
                                if(vpcRouter.get(0).getRouterId().equals(mapp.get("id").toString())){
                                    isok = true;
                                    break;
                                }
                            }
                            if(isok){
                                // 添加端口
                                Map<String,Object> attachRouter = new HashMap<String,Object>();
                                attachRouter.put("status","ACTIVE");
                                attachRouter.put("network_id",vpc.getResVpcSid());
                                attachRouter.put("url","");
                                attachRouter.put("device_owner",port.getDeviceOwner());
                                attachRouter.put("fixed_ips",new ArrayList());
                                attachRouter.put("id", port.getResPortSid());
                                attachRouter.put("device_id",vpcRouter.get(0).getRouterId());
                                portsList.add(attachRouter);
                            }
                        }else if("compute:nova".equals(port.getDeviceOwner())){
                            // 添加到虚拟机list，同时增加一条port
                            ResVm vm = this.resVmService.selectByPrimaryKey(port.getDeviceId());
                            Map<String,Object> vmMap = new HashMap<String,Object>();
                            vmMap.put("status","ACTIVE");
                            vmMap.put("task",null);
                            vmMap.put("url","");
                            vmMap.put("console","vnc");
                            vmMap.put("name",vm.getVmName());
                            vmMap.put("id", vm.getResVmSid());
                            serverList.add(vmMap);

                            // 添加端口
                            Map<String,Object> attachVm = new HashMap<String,Object>();
                            attachVm.put("status","ACTIVE");
                            attachVm.put("network_id",vpc.getResVpcSid());
                            attachVm.put("url","");
                            attachVm.put("device_owner",port.getDeviceOwner());
                            attachVm.put("fixed_ips",new ArrayList());
                            attachVm.put("id", port.getResPortSid());
                            attachVm.put("device_id", vm.getResVmSid());
                            portsList.add(attachVm);
                        }
                    }
                }
            }
        }

        map.put("networks",networkList);
        map.put("routers",routerList);
        map.put("ports",portsList);
        map.put("servers",serverList);

        return Response.status(Response.Status.OK).entity(new RestResult(map)).build();
    }

}
