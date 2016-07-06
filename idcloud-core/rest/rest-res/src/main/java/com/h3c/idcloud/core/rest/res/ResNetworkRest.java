package com.h3c.idcloud.core.rest.res;


import com.h3c.idcloud.core.pojo.dto.res.ResNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResVpc;
import com.h3c.idcloud.core.pojo.dto.res.ResVpcPorts;
import com.h3c.idcloud.core.pojo.dto.res.ResVpcRouter;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资源网络接口
 * <p/>
 * Created by jpg on 2016/3/2.
 */
@Path("/networks")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface ResNetworkRest {

    /**
     * 根据条件查询VPC列表
     *
     * @param info
     * @return
     */
    @GET
    @Path("/vpc")
    Response findVpcByParams(@Context UriInfo info,@Context HttpServletRequest request);

    /**
     * 根据条件查询subnet列表
     *
     * @param info
     * @return
     */
    @GET
    @Path("/subnet")
    Response findSubnetByParams(@Context UriInfo info,@Context HttpServletRequest request);

    /**
     * 根据条件查询路由器列表
     *
     * @param info
     * @return
     */
    @GET
    @Path("/router")
    Response findRouterByParams(@Context UriInfo info,@Context HttpServletRequest request);

    /**
     * 根据条件查询端口列表
     *
     * @param routerId
     * @return
     */
    @GET
    @Path("/ports/{routerId}")
    Response findPortsByRouterId(@PathParam("routerId") String routerId,@Context HttpServletRequest request);

    /**
     * 更新vpc信息
     *
     * @param vpc
     * @return
     */
    @POST
    @Path("/vpc/update")
    Response updateVpc(ResVpc vpc);

    /**
     * 更新subnet信息
     *
     * @param network
     * @return
     */
    @POST
    @Path("/subnet/update")
    Response updateSubnet(ResNetwork network);

    /**
     * 更新路由器信息
     *
     * @param router
     * @return
     */
    @POST
    @Path("/router/update")
    Response updateRouter(ResVpcRouter router);

    /**
     * 更新端口信息
     *
     * @param ports
     * @return
     */
    @POST
    @Path("/ports/update")
    Response updatePorts (ResVpcPorts ports);

    /**
     * Statistical topology of network response.
     *
     * @param resTopologySid the res topology sid
     * @return the response
     */
    @GET
    @Path("/statistical/topology/{resTopologySid}")
    Response statisticalTopologyOfNetwork(@PathParam("resTopologySid") String resTopologySid);

    /**
     * 统计业务下的网络信息
     *
     * @param resBizSid the res biz sid
     * @return the response
     */
    @GET
    @Path("/statistical/biz/{resBizSid}")
    Response statisticalBizOfNetwork(@PathParam("resBizSid") Long resBizSid);

    /**
     * 查询网络列表
     *
     * @param network the network
     * @return the response
     */
    @POST
    @Path("/biz")
    Response findBizNetwork(ResNetwork network);

    /**
     * 查找租户下所有网络
     *
     * @param resZoneSid 区域Sid
     * @param mgtObjSid  管理对象ID
     * @return the response
     */
    @GET
    @Path("/mgtObj/{mgtObjSid}")
    Response findNetworkByMgtObj(@QueryParam("resZoneSid") String resZoneSid, @PathParam("mgtObjSid") String mgtObjSid);

    /**
     * 查询网络下的所有子网
     *
     * @param resVpcId 网络id
     * @return the response
     */
    @GET
    @Path("/{resVpcId}/subnets")
    Response findSubnetByNetwork(@PathParam("resVpcId") String resVpcId);

    /**
     * 查询网络列表
     *
     * @param map the map
     * @return the response
     */
    @POST
    @Path("/default")
    @Deprecated
    Response createDefaultNetwork(Map<String,String> map);

    /**
     * Create vpc with subnet response.
     *
     * @param request  the request
     * @return the response
     */
    @POST
    @Path("/vpc")
    Response createVpcWithSubnet(@Context HttpServletRequest request);

    /**
     * Create router.
     *
     * @param request  the request
     * @return the response
     */
    @POST
    @Path("/router")
    Response createRouter(@Context HttpServletRequest request);

    /**
     * remove router.
     *
     * @param request  the request
     * @return the response
     */
    @POST
    @Path("/router/delete")
    Response deleteRouter(@Context HttpServletRequest request);

    /**
     * 网络关联路由器
     *
     * @param request  the request
     * @return the response
     */
    @POST
    @Path("/attach/router")
    Response attachSubNetWithRouter(@Context HttpServletRequest request);

    /**
     * 网络解绑路由器
     *
     * @param request  the request
     * @return the response
     */
    @POST
    @Path("/dettach/router")
    Response dettachSubNetWithRouter(@Context HttpServletRequest request);

    /**
     * remove vpc.
     *
     * @param request  the request
     * @return the response
     */
    @POST
    @Path("/vpc/delete")
    Response deleteVpc(@Context HttpServletRequest request);

    /**
     * remove subnetwork.
     *
     * @param request  the request
     * @return the response
     */
    @POST
    @Path("/subnet/delete")
    Response deleteSubnetwork(@Context HttpServletRequest request);

    /**
     * Create vpc with subnet response.
     *
     * @param request  the request
     * @return the response
     */
    @POST
    @Path("/subnet")
    Response createSubnet(@Context HttpServletRequest request);

    /**
     * Create vpc with subnet response.
     *
     * @param zoneSid
     * @return the response
     */
    @GET
    @Path("/find/Topology/data/{zoneSid}")
    Response findTopologyData(@PathParam("zoneSid") String zoneSid,@Context HttpServletRequest request);
}
