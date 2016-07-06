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
@Path("/vms")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface ResVmRest {


    @GET
    @Path("/findAll")
    Response findVmByPaging(@Context HttpServletRequest request);

    /**
     * 查询主机下的虚拟机
     *
     * @param allocateResHostSid
     * @return
     */
    @GET
    @Path("/findByparam/{allocateResHostSid}")
    Response findOriginalVM(@PathParam("allocateResHostSid") String allocateResHostSid);

    /**
     * 统计vm在拓扑结构下的信息
     *
     * @return
     */
    @GET
    @Path("/statistical/topology/{resTopologySid}")
    Response statisticalTopologyOfVm(@PathParam("resTopologySid") String resTopologySid);

    /**
     * 统计资源池下的虚拟机信息
     *
     * @param resPoolSid
     * @return
     */
    @GET
    @Path("/statistical/pool/status/{resPoolSid}")
    Response statisticalComputePoolOfVm(@PathParam("resPoolSid") String resPoolSid);


}
