/**
 * 
 */
package com.h3c.idcloud.core.rest.system;

import com.h3c.idcloud.core.pojo.dto.system.Sysconfig;

import javax.jws.WebMethod;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author jipeigui
 * 
 */
@Path("/configs")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface ConfigsRest {

	/**
	 * 查询系统配置
	 * 
	 * @return
	 */
	@POST
	public Response  findSysConfig(Sysconfig sysconfig);

	@GET
	@Path("/getSysById")
	public Response getSysValue(@QueryParam("configSid") Long configSid);

	@GET
	@Path("/getProp/{name}")
	public Response getProperty(@PathParam("name") String name);
	
	/**
	 * 修改系统配置
	 * 
	 * @return
	 */
	@POST
	@Path("/update")
	public Response editSysConfig(Sysconfig sysconfig);

	/**
	 * 得到配置所有的所有类型
	 * 
	 * @return
	 */
	@POST
	@Path("/getConfigType")
	public Response getConfigType(Sysconfig sysconfig);
	
}
