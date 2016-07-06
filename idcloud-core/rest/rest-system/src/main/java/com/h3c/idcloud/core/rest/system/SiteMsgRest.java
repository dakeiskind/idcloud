package com.h3c.idcloud.core.rest.system;

import com.h3c.idcloud.core.pojo.dto.system.SiteMsg;
import org.springframework.context.annotation.Scope;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * @author jiangjun
 * 
 */
@Path("/sitemsg")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface SiteMsgRest {
	
	/**
	 * 查询
	 */
	@GET
	@Path("/find")
	public Response find(@Context HttpServletRequest request);

	/**
	 * 查询
	 */
	@WebMethod
	@POST
	@Path("/findAll")
	public Response findAll(Map map);
	
	/**
	 * 添加
	 */
	@WebMethod
	@POST
	@Path("/insert")
	public Response insert(SiteMsg siteMsg);

	/**
	 * 编辑角色
	 */
	@WebMethod
	@POST
	@Path("/edit")
	public Response editRole(SiteMsg siteMsg);
	
	/**
	 * 删除验证
	 */
	@WebMethod
	@POST
	@Path("/delete")
	public Response delete(SiteMsg siteMsg);
}