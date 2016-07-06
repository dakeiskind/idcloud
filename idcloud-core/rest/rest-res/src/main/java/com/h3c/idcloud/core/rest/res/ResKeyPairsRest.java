package com.h3c.idcloud.core.rest.res;


import com.h3c.idcloud.core.pojo.dto.res.ResKeypairs;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/keypairs")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface ResKeyPairsRest {

	/**
	 * 通过mgtObjSid只查询密钥的Sid和名称
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/findKeypairs/{mgtObjSid}")
	public Response findKeypairsByMgtObjSid(@PathParam("mgtObjSid") String mgtObjSid);
	
	/**
	 * 查询秘钥对
	 * @params params
	 * 必须包含mgtObjSid和keypairsName
	 * 
	 */
	@WebMethod
	@POST
	@Path("/findKeypairs")
	public Response findKeypairs(String params);
	
	/**
	 * 删除秘钥
	 * 
	 */
	@WebMethod
	@POST
	@Path("/deleteKeypairs")
	public Response deleteKeypairs(@Context HttpServletRequest request,String[] resKeypairsSids);
	
	/**
	 * 创建秘钥对
	 * 
	 */
	@WebMethod
	@POST
	@Path("/creatKeypairs")
	public Response creatKeypairs(@Context HttpServletRequest request,String params);
	
	/**
	 * 
	 * 创建完成时下载私钥
	 */
	@WebMethod
	@GET
	@Path("/download/exportprivateKeys/{attachmentName}")
	public Response exportprivateKeys(@PathParam("attachmentName") String attachmentName, @Context HttpServletResponse servletResponse);
	
	/**
	 * 导入秘钥对
	 * 
	 */
	@WebMethod
	@POST
	@Path("/importKeypairs")
	public Response importKeypairs(@Context HttpServletRequest request,String params);

	/**
	 * 导出租户下所有秘钥对
	 * 
	 */
	@WebMethod
	@GET
	@Path("/download/exportAllKeypairs/{userSid}")
	@Produces("application/vnd.ms-excel")
	public Response exportAllKeypairs(@PathParam("userSid") String userSid, @Context HttpServletResponse servletResponse);
	
	/**
	 * 检测秘钥对名称是否重复
	 */
	@WebMethod
	@GET
	@Path("/checkKeypairs/{keypairsName}")
	public Response checkKeypairs(@PathParam("keypairsName") String keypairsName);

	/**
	 * 修改密钥名称与描述
	 * @param resKeypairs
	 * @return
	 */
	@WebMethod
	@POST
	@Path("/modifyKeypairs")
	public Response modifyKeypairs(ResKeypairs resKeypairs);

	/**
	 * 根据名称查看秘钥对
	 *
	 */
	@WebMethod
	@POST
	@Path("/findKeypairsByName")
	public Response findKeypairsByName(String params);

}
