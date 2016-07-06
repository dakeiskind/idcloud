package com.h3c.idcloud.core.rest.product;

import com.h3c.idcloud.core.pojo.dto.product.InstanceArray;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;

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

import java.lang.reflect.Array;
import java.util.Map;

/**
 * 云主机相关Rest接口
 * Created by tdz on 2016/3/28.
 */
@Path("/cs")
@Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
@Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
@Scope("singleton")
public interface CsRest {

    /**
     * 获取云主机实例集合
     *
     * @return Service List
     */
    @POST
    Response selectServiceInstanceInfo(String params,@Context HttpServletRequest req);

    /**
     * 获取云主机实例集合，并取得实例实时状态
     *
     * @return Service List
     */
    @POST
    @Path("/serviceInstancelistAndCurrentState")
    Response getServiceInstanceList(@Context HttpServletRequest request);

    /**
     * 获取一个服务的详细信息
     */
    @GET
    @Path("/{serviceInstanceSid}")
    Response getServiceInstance(@PathParam("serviceInstanceSid") String serviceInstanceSid);

    /**
     * 操作服务实例
     */
    @POST
    @Path("/serviceInstanceOperation")
    Response serviceInstanceOperation(InstanceArray[] params, @Context HttpServletRequest request);

    /**
     * 退订服务
     */
    @POST
    @Path("/release")
    Response releaseCsInstance(ServiceInstance[] params,@Context HttpServletRequest request);

    /**
     * 获取vnc
     * @param resVmSid
     * @param request
     * @return
     */
    @GET
    @Path("/vnc/{resVmSid}")
    Response getWebConsoleUrl(@PathParam("resVmSid") String resVmSid, @Context HttpServletRequest request);

    /**
     * 获取云主机可关联的弹性公网IP
     */
    @POST
    @Path("/getEipForHost")
    Response getEipForHost(String params, @Context HttpServletRequest request);
}
