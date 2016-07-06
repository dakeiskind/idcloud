package com.h3c.idcloud.core.rest.product;

import org.springframework.context.annotation.Scope;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Tdz on 2016/4/6.
 */
@Path("/eip")
@Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
@Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
@Scope("singleton")
public interface EipRest {
    /**
     * 获取浮动ip服务实例
     */
    @POST
    @Path("/findFloatingIps")
    Response selectFloatingIpInfo(@Context HttpServletRequest request);

    /**
     * 绑定弹性公网IP
     */
    @POST
    @Path("/attachFloatingIp")
    Response attachFloatingIp(String params,@Context HttpServletRequest request);

    /**
     * 解绑弹性公网IP
     */
    @POST
    @Path("/dettachFloatingIp")
    Response dettachFloatingIp(String params,@Context HttpServletRequest request);

    /**
     * 退订服务
     */
    @GET
    @Path("/release/{serviceInstanceSid}")
    Response releaseEipInstance(@PathParam("serviceInstanceSid") String serviceInstanceSid, @Context HttpServletRequest request);

    /**
     * 查找可用于绑定弹性IP的服务实例
     */
    @POST
    @Path("/getInstanceForFloatingip")
    Response getInstanceForFloatingip(String params, @Context HttpServletRequest request);
}
