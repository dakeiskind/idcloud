package com.h3c.idcloud.core.rest.res;


import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.user.User;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * 资源拓扑接口
 *
 * Created by jpg on 2016/3/2.
 */
@Path("/topologys")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface ResTopologyRest {


    /**
     * 查询资源拓扑图列表
     *
     * @param
     * @return
     */
    @POST
    Response findTopology(ResTopology topology);

    /**
     * 根据拓扑SID查询topology信息
     *
     * @param resTopologySid
     * @return
     */
    @GET
    @Path("/{resTopologySid}")
    Response findResourceTopologyById(@PathParam("resTopologySid") String resTopologySid);

    /**
     * 查询出资源环境主机视图Tree列表
     *
     * @return
     */
    @POST
    @Path("/virtual/tree")
    Response findResVirtualTopology();

    /**
     * 查询出资源池视图Tree列表
    *
            * @return
            */
    @POST
    @Path("/pool/tree")
    Response findResPoolTopology();

    /**
     * 查询出资源存储视图Tree列表
     *
     * @return
     */
    @POST
    @Path("/virtual/storage/tree")
    Response findResStorageVirtualTopology();

    /**
     * 新增资源拓扑结构
     *
     * @param topology
     * @return
     */
    @POST
    @Path("/create")
    public Response createTopology(ResTopology topology);

    /**
     * 新增资源拓扑分区
     *
     * @param topology
     * @return
     */
    @POST
    @Path("/create/Rz")
    Response CreateTopologyRz(ResTopology topology);

    /**
     * 删除资源拓扑分区
     *
     * @param resTopologySid
     * @return
     */
    @GET
    @Path("delete/rz/{resTopologySid}")
    Response deleteTopologyRz(@PathParam("resTopologySid") String resTopologySid);

    /**
     * 检查拓扑结构名称是否重复
     *
     * @param topology
     * @return
     */
    @POST
    @Path("/checkTopologyRepeat")
    Response checkTopologyIsNotRepeat(ResTopology topology);

}
