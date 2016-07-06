package com.h3c.idcloud.core.rest.res;


import com.h3c.idcloud.core.pojo.dto.res.ResHost;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 资源拓扑接口
 *
 * Created by jpg on 2016/3/2.
 */
@Path("/hosts")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface ResHostRest {

    /**
     * 查询主机列表
     *
     * @param host
     * @return
     */
    @POST
    Response findHost(ResHost host);

    /**
     * 查询主机列表，并采用服务器分页方式
     *
     * @param request
     * @return
     */
    @GET
    @Path("/findAll")
    Response findHostByPaging(@Context HttpServletRequest request);

    /**
     * 根据sid查询主机信息
     *
     * @param resHostSid
     * @return
     */
    @GET
    @Path("/{resHostSid}")
    public Response findResHostBySid(@PathParam("resHostSid") String resHostSid);

    /**
     * 查询可以添加到集群的主机列表
     *
     * @param resTopologySid
     * @return
     */
    @GET
    @Path("/findHostAddToCluster/{resTopologySid}")
    Response findAddHostToPool(@PathParam("resTopologySid") String resTopologySid);

    /**
     * 统计拓扑结构下的主机信息
     *
     * @return
     */
    @POST
    @Path("/statistical/topology")
    Response statisticalTopologyOfHost(ResHost host);
    /**
     * 统计业务下的主机信息
     */
    @GET
    @Path("/statistical/biz/{resBizSid}")
    Response statisticalBizOfHost(@PathParam("resBizSid") Long resBizSid);

    /**
     * 统计拓扑结构下的主机资源分配信息
     *
     * @param resTopologySid
     * @return
     */
    @GET
    @Path("/statistical/topology/allot/{resTopologySid}")
    Response statisticalTopologyOfHostAllot(@PathParam("resTopologySid") String resTopologySid);

    /**
     * 统计资源分区下的主机状态信息
     *
     * @param resTopologySid
     * @return
     */
    @GET
    @Path("/statistical/rz/status/{resTopologySid}")
    Response statisticalRzOfHostInfo(@PathParam("resTopologySid") String resTopologySid);

    /**
     * 统计资源分区下的主机资源分配信息
     *
     * @param resTopologySid
     * @return
     */
    @GET
    @Path("/statistical/rz/allot/{resTopologySid}")
    Response statisticalRzOfHostAllotInfo(@PathParam("resTopologySid") String resTopologySid);

    /**
     * 统计资源池下的主机信息
     *
     * @param resPoolSid
     * @return
     */
    @GET
    @Path("/statistical/pool/status/{resPoolSid}")
    Response statisticalComputePoolOfHost(@PathParam("resPoolSid") String resPoolSid);
    /**
     * 查询业务Host列表--分页
     */
    @GET
    @Path("/biz/findAll")
    Response findBizHostByPaging(@Context HttpServletRequest request);
}
