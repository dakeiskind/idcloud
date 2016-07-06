package com.h3c.idcloud.core.rest.res;

import org.springframework.context.annotation.Scope;

import javax.jws.WebMethod;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/securitygroup")
@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
@Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
@Scope("singleton")
public interface SecurityGroupRest {

	/**
	 * 根据条件查询安全组（检测名称是否重复）
	 */
	@WebMethod
	@POST
	@Path("/queryByParams")
	public Response query(String params);
	
	/**
	 * 根据条件查询虚拟机已绑定的安全组
	 */
	@WebMethod
	@POST
	@Path("/queryByVm")
	public Response queryByVm(String params);

	/**
	 * 查询安全组列表
	 */
	@WebMethod
	@GET
	@Path("/tenant/{mgtObjSid}")
	Response queryByTenant(@PathParam("mgtObjSid") String mgtObjSid);

	/**
	 * 新增安全组
	 */
	@WebMethod
	@POST
	@Path("/create")
	public Response create(String params);
	
	/**
	 * 编辑安全组
	 */
	@WebMethod
	@POST
	@Path("/update")
	public Response update(String params);

	/**
	 * 删除
	 */
	@WebMethod
	@DELETE
	@Path("/delete/{id}")
	public Response delete(@PathParam("id") String id);

	@WebMethod
	@POST
	@Path("/attach")
	public Response attach(String params);

	@WebMethod
	@POST
	@Path("/detach")
	Response detach(String params);

}
