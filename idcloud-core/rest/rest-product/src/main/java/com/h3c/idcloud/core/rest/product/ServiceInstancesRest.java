package com.h3c.idcloud.core.rest.product;

import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import org.springframework.context.annotation.Scope;

import javax.jws.WebMethod;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/serviceInstances")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Scope("singleton")
public interface ServiceInstancesRest {

	/**
	 * 获取一个服务的详细信息
	 * 
	 * @param serviceInstanceSid
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/{serviceInstanceSid}")
	public Response getServiceInstance(@PathParam("serviceInstanceSid")String serviceInstanceSid);

	/**
	 * 查询出前台用户的ServiceInstance列表服务
	 */
	@WebMethod
	@POST
	@Path("/portal/searchServiceInstance")
	public Response getServiceInstanceList(ServiceInstance service);

	/**
	 * 根据条件获取后台serviceInstance列表服务
	 */
	@WebMethod
	@POST
	@Path("/platform/searchServiceInstance")
	public Response searchServiceInstance(String params);

	/**
	 * 根据条件获取后台serviceInstance列表服务
	 */
	@WebMethod
	@POST
	@Path("/platform/searchServiceInstanceByName")
	public Response searchServiceInstanceByName(String params);

	/**
	 * 根据条件获取后台serviceInstance列表 配置信息
	 */
	@WebMethod
	@POST
	@Path("/platform/searchServiceInstanceDetailConfig")
	public Response searchServiceInstanceDetailConfig(String params);

	// /**
	// * 根据条件获取后台serviceInstance列表 存储信息
	// */
	// @WebMethod
	// @POST
	// @Path("/platform/searchServiceInstanceDetailStorage")
	// public Response searchServiceInstanceDetailStorage(String params);

	// /**
	// * 根据条件获取后台serviceInstance列表 资源信息
	// */
	// @WebMethod
	// @POST
	// @Path("/platform/searchServiceInstanceDetailResource")
	// public Response searchServiceInstanceDetailResource(String params);

	/**
	 * 调整租期
	 */
	@WebMethod
	@POST
	@Path("/changeExpiringDate")
	public Response changeExpiringDate(String params);

	/**
	 * 获取最新版本号
	 */
	@WebMethod
	@POST
	@Path("/getNewVersion")
	public Response getNewVersion(String params);

	/**
	 * 云主机变更（规格调整）
	 */
	@WebMethod
	@POST
	@Path("/setNewVersion")
	public Response setNewVersion(String params);

	/**
	 * 服务调整规格
	 */
	@WebMethod
	@POST
	@Path("/adjust/spec/{serviceSid}/{instanceSid}")
	public Response adjustSpec(String params, @PathParam("serviceSid") Long serviceSid,
							   @PathParam("instanceSid") Long instanceSid);

	/**
	 * 按条件获取对应的版本记录
	 */
	@WebMethod
	@POST
	@Path("/getLatest")
	public Response getLatest(String params);

	/**
	 * 按条件获取对应的版本记录，用于变更时判断是否显示
	 */
	@WebMethod
	@POST
	@Path("/getLatestSpecs")
	public Response getLatestSpecs(String params);

	@WebMethod
	@POST
	@Path("/queryInstanceSpec")
	public Response queryInstanceSpec(String params);

	@WebMethod
	@POST
	@Path("/getServiceInstRes")
	Response getServiceInstRes(String params);

	@WebMethod
	@POST
	@Path("/getNetWorkInfo")
	public Response getNetWorkInfo(String params);

	@WebMethod
	@POST
	@Path("/getInstanceCount")
	public Response getInstanceCount(String params);

	@WebMethod
	@POST
	@Path("/modify/instanceSpecs")
	public Response modifyInstanceSpec(String params);

	@WebMethod
	@POST
	@Path("/mgtObj/find")
	public Response getInstanceCountByMgtObj(ServiceInstance instance);

	@WebMethod
	@POST
	@Path("/mgtObj/findServerCount")
	public Response getServerCountByMgtObj(ServiceInstance instance);

	@WebMethod
	@GET
	@Path("/mgtObj/find/{mgtObjSid}")
	public Response findMgtObjBySid(@PathParam("mgtObjSid") Long mgtObjSid);

	@WebMethod
	@GET
	@Path("/judgement/exist/service/{serviceSid}")
	public Response judgementExistObjStorage(@PathParam("serviceSid") Long serviceSid);

	@WebMethod
	@GET
	@Path("/find/objStorage")
	public Response findObjStorageByMgtObjSid();

	@WebMethod
	@POST
	@Path("/find/checkInstanceName")
	public Response checkInstanceName(String params);

	/**
	 * 产品类型
	 * 
	 * @param serviceName
	 * @param code
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/getBssProductList/{serviceName}/code/{code}")
	public Response getBssProductList(@PathParam("serviceName") String serviceName, @PathParam("code") String code);

	/**
	 * 价格
	 * 
	 * @param params
	 * @return
	 */
	@WebMethod
	@POST
	@Path("/getBssProductPrice")
	public Response getBssProductPrice(String params);

	/**
	 * 合同列表
	 * 
	 * @param name
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/getContractList/{name}")
	public Response getContractList(@PathParam("name") String name);
	
	/**
	 * 查询得到可用于关联弹性IP的虚机
	 * @param params
	 * @return
	 */
	@WebMethod
	@POST
	@Path("/getInstanceForFloatingip")
	public Response getInstanceForFloatingip(String params);

	/**
	 * 得到最新的changeLog记录
	 *
	 * @param serviceInstanceSid
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/getLastChangeLog/{serviceInstanceSid}")
	public Response getLastChangeLog(@PathParam("serviceInstanceSid") Long serviceInstanceSid);


}
