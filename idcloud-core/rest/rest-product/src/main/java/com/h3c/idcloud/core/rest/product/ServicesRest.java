package com.h3c.idcloud.core.rest.product;

import com.h3c.idcloud.core.pojo.dto.product.ServiceCatalog;
import com.h3c.idcloud.core.pojo.dto.product.ServiceDefine;
import com.h3c.idcloud.core.pojo.dto.product.ServiceTemplate;
import com.h3c.idcloud.core.pojo.vo.product.ServiceCatalogVo;
import org.springframework.context.annotation.Scope;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/services")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Scope("singleton")
public interface ServicesRest {

		/**
	 * getService
	 * Description: Get Service list information according to parameters
	 * @param  - Service structure
	 * @return Service List
	 */
	@WebMethod
	@POST
	@Path("/servicelist")
	Response getServiceList();

	/**
	 * 获取一个服务的详细信息
	 *
	 * @param serviceSid
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/serviceDetail/{serviceSid}")
	Response getServiceDetail(String serviceSid);

	/**
	 * 获取一个服务下服务模板的详细信息
	 *
	 * @param serviceSid
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/templatelist/{serviceSid}")
	Response getServiceTemplate(String serviceSid);

	/**
	 * 取得所有服务和服务类型组成Tree
	 */
	@WebMethod
	@POST
	@Path("/serviceTree")
	Response getServiceAndCatalogList();

	/**
	 * 获取单一服务信息
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/{serviceSid}")
	Response getService(String serviceSid);

	/**
	 * 获取单一服务操作信息
	 * @return
	 */
	@WebMethod
	@POST
	@Path("/getServiceOperation")
	Response getServiceOperation(String serviceSid);


	/**
	 * 根据用户获取service服务
	 */
	@WebMethod
	@POST
	@Path("/findAll")
	Response findAllServiceByRole(String params);

	/**
	 * 获取服务定义codes
	 * @param params
	 * @return
     */
	@WebMethod
	@POST
	@Path("/findServiceDefines")
	Response findServiceDefines(String params);
	/**
	 * 查询所有服务
	 */
	@WebMethod
	@POST
	@Path("/platform/findAllService")
	Response findAllService();
	
	/**
	 * 初始化服务目录树
	 */
	@WebMethod
	@POST
	@Path("/platform/initServiceMenuMgtTree")
	Response initServiceMenuMgtTree();
	
	/**
	 * 查询服务目录
	 */
	@WebMethod
	@POST
	@Path("/platform/findServiceCatalog")
	Response findServiceCatalog(ServiceCatalogVo serviceCatalog);
	
	/**
	 * 查询单个服务目录
	 */
	@WebMethod
	@POST
	@Path("/platform/findServiceCatalogBySid")
	Response findServiceCatalogBySid(String params);
	
	/**
	 * 查询服务定义列表
	 */
	@WebMethod
	@POST
	@Path("/platform/findServiceDefineByParams")
	Response findServiceDefineByParams(String params);
	
	/**
	 * 查询单个服务定义
	 */
	@WebMethod
	@POST
	@Path("/platform/findServiceDefineBySid")
	Response findServiceDefineBySid(String params);
	
	/**
	 * 查询服务模板列表
	 */
	@WebMethod
	@POST
	@Path("/platform/findServiceTempByServiceSid")
	Response findServiceTempByServiceSid(String params);
	
	/**
	 * 查询服务模板列表
	 */
	@WebMethod
	@POST
	@Path("/platform/findServiceTempSpecByParams")
	Response findServiceTempSpecByParams(String params);
	
	/**
	 * 新增后台服务模板
	 * 
	 * @param template
	 * @return
	 */
	@WebMethod
	@POST
    @Path("/platform/insertServiceTemplate")
	Response insertServiceTemplate(ServiceTemplate template);
	
	/**
	 * 删除服务模板
	 * 
	 * @param templateSid
	 * @return
	 */
	@WebMethod
	@GET
    @Path("/platform/deleteTemplate")
	Response deleteTemplate(@QueryParam("templateSid") Long templateSid);
	
	/**
	 * 更新后台服务模板
	 * 
	 * @param template
	 * @return
	 */
	@WebMethod
	@POST
    @Path("/platform/updateServiceTemplate")
	Response updateServiceTemplate(ServiceTemplate template);
	
	
	/**
	 * 新增后台服务
	 * 
	 * @param service
	 * @return
	 */
	@WebMethod
	@POST
    @Path("/platform/insertService")
	Response insertService(ServiceDefine service, @Context HttpServletRequest servletRequest);
    
	/**
	 * 更新后台服务
	 * 
	 * @param service
	 * @return
	 */
	@WebMethod
	@POST
    @Path("/platform/updateService")
	Response updateService(ServiceDefine service);
	
	
	/**
	 * 发布、禁用服务
	 */
	@WebMethod
	@GET
	@Path("/platform/operationService")
	Response operationService(@QueryParam("serviceSid") Long serviceSid,
							  @QueryParam("action") String action);
	
	/**
	 * 创建服务目录
	 * 
	 * @param serviceCatalog
	 * @return
	 */
	@WebMethod
	@POST
	@Path("/platform/addServiceCatalog")
	Response addServiceCatalog(ServiceCatalog serviceCatalog);
	
	/**
	 * 更新服务目录
	 * 
	 * @param serviceCatalog
	 * @return
	 */
	@WebMethod
	@POST
	@Path("/platform/updateServiceCatalog")
	Response updateServiceCatalog(ServiceCatalog serviceCatalog);
	
	/**
	 * 删除租户
	 * 
	 * @param catalogSid
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/platform/deleteServiceCatalog/{catalogSid}")
	Response deleteServiceCatalog(@PathParam("catalogSid") Long catalogSid);
	
	/**
	 * 删除服务目录
	 * 
	 * @param catalogSid
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/platform/deleteCatalog/{catalogSid}")
	Response deleteCatalog(@PathParam("catalogSid") Long catalogSid);
}
