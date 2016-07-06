package com.h3c.idcloud.core.rest.product;

import org.springframework.context.annotation.Scope;

import javax.jws.WebMethod;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/serviceInstance")
@Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
@Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
@Scope("singleton")
public interface ServiceInstanceResourceRest {

    /**
     * 获取服务实例集合
     *
     * Description: 获取服务实例集合
     *
     * @return Service List
     */
    @WebMethod
    @POST
    @Path("/serviceInstancelist")
    Response getSuccessServiceInstanceList(String params);

    /**
     * 获取服务实例集合，并取得实例实时状态
     *
     * Description: Get Service list information according to parameters
     *
     * @return Service List
     */
    @WebMethod
    @POST
    @Path("/serviceInstancelistAndCurrentState")
    Response getServiceInstanceList(String params);

    /**
     * 获取一个服务的详细信息
     */
    @WebMethod
    @GET
    @Path("/{serviceInstanceSid}")
    Response getServiceInstance(String serviceInstanceSid);

    /**
     * 操作服务实例
     */
    @WebMethod
    @POST
    @Path("/serviceInstanceOperation")
    Response serviceInstanceOperation(String params);

    /**
     * 退订服务
     */
    @WebMethod
    @GET
    @Path("/release/{serviceInstanceSid}")
    Response releaseServiceInstance(String serviceInstanceSid);

    /**
     * 获取服务实例集合
     *
     * Description: Get Service list information according to parameters
     *
     * @return Service List
     */
    @WebMethod
    @POST
    @Path("/serviceAllInstancelist")
    Response getAllServiceInstanceList(String params);


    /**
     * 获取订单详情下的服务实例
     */
    @WebMethod
    @GET
    @Path("/serviceInstanceByDetailSid/{detailSid}")
    Response serviceInstanceByDetailSid(String detailSid);

    @WebMethod
    @POST
    @Path("/serviceInstanceByParam")
    Response serviceInstanceByParam(String params);

    /**
     * 获取块存储服务实例
     */
    @WebMethod
    @POST
    @Path("/findVolume")
    Response selectVolumeStorageInfo();

    /**
     * 获取浮动ip服务实例
     */
    @WebMethod
    @POST
    @Path("/findFloatingIps")
    Response selectFloatingIpInfo();

    /**
     * 查询当前用户下的服务实例
     */
    @WebMethod
    @GET
    @Path("/findTargetHost")
    Response selectTargethost(@QueryParam("OStype") String OStype);

    /**
     * 更新服务实例
     */
    @WebMethod
    @POST
    @Path("/updateServiceInstance")
    Response updateServiceInstance(String params);

    /**
     * 获取租户下服务实例集合
     *
     * Description: Get Service list information according to parameters
     *
     * @return Service List
     */
    @WebMethod
    @POST
    @Path("/getTenantOrderServicelist")
    Response getTenantOrderServicelist(String params);

    /**
     * 获取租户下购买的服务实例
     *
     * Description: Get Service list information according to parameters
     *
     * @return Service List
     */
    @WebMethod
    @POST
    @Path("/getTenantOrderServiceInstancelist")
    Response getTenantOrderServiceInstancelist(String params);

    /**
     * 获取租户下购买的服务实例配额
     *
     * Description: Get Service list information according to parameters
     *
     * @return Service List
     */
    @WebMethod
    @POST
    @Path("/getTenantOrderServiceInstanceQuotalist")
    Response getTenantOrderServiceInstanceQuotalist(String params);

    /**
     * 查询当前用户下的权限
     */
    @WebMethod
    @GET
    @Path("/checkAllServiceShow")
    Response checkAllServiceShow();


    /**
     * 获取一个服务的规格信息
     */
    @WebMethod
    @GET
    @Path("/spec/{serviceInstanceSid}")
    Response getServiceInstanceSpec(String serviceInstanceSid);

    /**
     * 获取部门下的所有虚拟机信息
     */
    @WebMethod
    @POST
    @Path("/getOrgServiceInstance")
    Response getOrgServiceInstance(String params);

//	/**
//	 * 根据条件获取后台serviceInstance列表服务
//	 */
//	@WebMethod
//	@POST
//	@Path("/platform/searchServiceInstance")
//	public Response searchServiceInstance(String params);
//	
//	/**
//	 * 根据条件获取后台serviceInstance列表 配置信息
//	 */
//	@WebMethod
//	@POST
//	@Path("/platform/searchServiceInstanceDetailConfig")
//	public Response searchServiceInstanceDetailConfig(String params);
//	
//	/**
//	 * 根据条件获取后台serviceInstance列表  存储信息
//	 */
//	@WebMethod
//	@POST
//	@Path("/platform/searchServiceInstanceDetailStorage")
//	public Response searchServiceInstanceDetailStorage(String params);
//	
//	/**
//	 * 根据条件获取后台serviceInstance列表  资源信息
//	 */
//	@WebMethod
//	@POST
//	@Path("/platform/searchServiceInstanceDetailResource")
//	public Response searchServiceInstanceDetailResource(String params);

}
