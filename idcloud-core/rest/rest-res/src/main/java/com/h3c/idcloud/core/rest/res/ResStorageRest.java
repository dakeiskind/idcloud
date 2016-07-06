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
@Path("/storages")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface ResStorageRest {

    /**
     * 查询存储列表。并进行服务器分页
     *
     * @param request
     * @return
     */
    @GET
    Response findStorageByPage(@Context HttpServletRequest request);

    /**
     * 统计拓扑结构下的存储信息
     *
     * @return
     */
    @GET
    @Path("/statistical/topology/{resTopologySid}")
    Response statisticalStoragePoolOfStorage(@PathParam("resTopologySid") String resTopologySid);

    /**
     * 统计拓扑结构下的存储分配信息
     *
     * @param resPoolSid
     * @return
     */
    @GET
    @Path("/statistical/topology/allot/{resTopologySid}")
    Response statisticalStoragePoolOfStorageAllotInfo(@PathParam("resTopologySid") String resPoolSid);


    /**
     * 统计拓扑结构下的存储容量信息
     *
     * @return
     */
    @GET
    @Path("/statistical/topology/volume/{resTopologySid}")
    Response statisticalStorageVolumeOfStorage(@PathParam("resTopologySid") String resTopologySid);

    /**
     * 统计资源集群下的存储容量信息
     *
     * @param resTopologySid
     * @return
     */
    @GET
    @Path("/statistical/cluster/volume/{resTopologySid}")
    Response statisticalClusterOfStorageVolume(@PathParam("resTopologySid") String resTopologySid);

    /**
     * 统计资源集群下的存储分配信息
     *
     * @param resTopologySid
     * @return
     */
    @GET
    @Path("/statistical/cluster/allot/{resTopologySid}")
    Response statisticalClusterOfStorageAllotInfo(@PathParam("resTopologySid") String resTopologySid);

    /**
     * 统计主机下的存储信息
     *
     * @param resTopologySid
     * @return
     */
    @GET
    @Path("/statistical/host/volume/{resTopologySid}")
    Response statisticalHostOfStorageVolume(@PathParam("resTopologySid") String resTopologySid);

    /**
     * 统计主机下的存储分配信息
     *
     * @param resTopologySid
     * @return
     */
    @GET
    @Path("/statistical/host/allot/{resTopologySid}")
    Response statisticalHostOfStorageAllotInfo(@PathParam("resTopologySid") String resTopologySid);


    /**
     * 统计资源分区下存储分配信息
     *
     * @param resPoolSid
     * @return
     */
    @GET
    @Path("/statistical/rz/allot/{resTopologySid}")
    Response statisticalRzfStorageAllotInfo(@PathParam("resTopologySid") String resPoolSid);

    /**
     * 统计资源分区下存储容量使用信息
     *
     * @param resPoolSid
     * @return
     */
    @GET
    @Path("/statistical/rz/volume/{resTopologySid}")
    Response statisticalVolumeRzOfStorage(@PathParam("resTopologySid") String resPoolSid);

    /**
     * 统计业务下的存储信息
     */
    @GET
    @Path("/statistical/biz/{resBizSid}")
    Response statisticalBizOfStorage(@PathParam("resBizSid") Long resBizSid);

    /**
     * 查询业务块存储列表---分页
     */
    @Produces({ MediaType.APPLICATION_FORM_URLENCODED })
    @GET
    @Path("/biz")
    Response findBizStorageByPage(@Context HttpServletRequest request);
}
