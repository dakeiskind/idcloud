/**
 * 
 */
package com.h3c.idcloud.core.rest.system;

import com.h3c.idcloud.core.pojo.dto.system.Code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author jipeigui
 * 
 */
@Path("/system")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface CodesRest {

	/**
	 * 根据数据参数查询数据字典数据
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/getCodeByParams")
	Response getCodesByParams(String params);
	
	/**
	 * 根据数据参数查询数据字典数据
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/getCodesByOsVersionParams")
	Response getCodesByOsVersionParams(String params);
	
	/**
	 * 根据数据参数查询数据字典数据
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/findUserStatus")
	Response findUserStatus(String params);
	
	/**
	 * 根据数据字典类型查询数据字典数据
	 * 
	 * @param codeCategory
	 * @return
	 */
	@GET
	@Path("/{codeCategory}")
	Response getCodesByCategory(@PathParam("codeCategory") String codeCategory);
	
	/**
	 * 根据数据字典类型查询数据字典数据
	 * 
	 * @param installedSoftware
	 * @return
	 */
	@GET
	@Path("/findImageSoftWareTypeByCodeValue/{installedSoftware}")
	Response findImageSoftWareTypeByCodeValue(@PathParam("installedSoftware") String installedSoftware);
	
	/**
	 * 查寻镜像可部署的数据库和中间件数据
	 * 
	 */
	@POST
	@Path("/findImageSoftWare")
	Response findImageSoftWare(String params);
	
	/**
	 * 根据数据参数查询主机分类
	 * @param params
	 * @return
	 */
	@POST
	@Path("/getCodesByEquipCategory")
	Response getCodesByEquipCategory(String params);

	/**
	 * 查寻镜像上所支持的所有软件
	 * 
	 */
	
	@POST
	@Path("/findSoftWareByImage")
	Response findSoftWareByImage(String params);
	

	/**
	 * 查寻操作系统
	 * 
	 */
	
	@GET
	@Path("/findOsVersion")
	Response findOsVersion(@Context HttpServletRequest request);
	
	/**
	 * 
	 * 添加操作系统
	 */
	
	@POST
	@Path("/create")
	Response creatOsVersion(Code code);
	
	/**
	 * 
	 * 删除操作系统
	 */
	
	@GET
	@Path("/deleteSystemCode")
	Response deleteUser(@QueryParam("codeSids") String codeSids);
	
	/**
	 * 更新后台用户
	 * 
	 * @param code
	 * @return
	 */
	
	@POST
	@Path("/updateSystemCode")
	Response updateSystemCode(Code code);
	
	/**
	 * 
	 * 添加操作系统类型
	 */
	
	@POST
	@Path("/createOsType")
	Response createOsType(Code code);
}
