package com.h3c.idcloud.core.rest.system;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.context.annotation.Scope;

@Path("/approve")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface ApproveRecordRest {
	
	/**
	 * 获取不同类型的审核
	 * 
	 * @param request
	 * @return
	 */
	@WebMethod
	@Produces({ MediaType.APPLICATION_FORM_URLENCODED })
	@GET
	@Path("/getApproveByType")
	public Response selectApproveRecord(@Context HttpServletRequest request);
	
	/**
	 * 获取不同类型的实例审核
	 * 
	 * @param params
	 * @return
	 */
	@WebMethod
	@POST
	@Path("/getApproveByTypeInst")
	public Response selectApproveRecordInst(String params);
	
	/**
	 * 获取审核流程图
	 *
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/platform/getApproveFlow/{processInstanceId}")
	public void getApproveFlow(@Context HttpServletRequest request, @Context HttpServletResponse response,
							   @PathParam("processInstanceId") String processInstanceId);
	
	/**
	 * 获取审核记录
	 *
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/platform/getApproveHistoryRecords/{processInstanceId}")
	public Response getApproveHistoryRecords(@PathParam("processInstanceId") String processInstanceId);
	
	/**
	 * 获取审核详情
	 * 
	 * @param orderId
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/platform/getApproveDetailsRecords")
	public Response findApproveDetailsRecords(@QueryParam("orderId") String orderId);
	
	/**
	 * 管理员审核
	 * 
	 * @param params
	 * @return
	 */
	@WebMethod
	@POST
	@Path("/platform/createAdminApprove")
	public Response createAdminApprove(String params);
	
	/**
	 * 审核
	 * 
	 * @param params
	 * @return
	 */
	@WebMethod
	@POST
	@Path("/platform/createApprove")
	public Response createApprove(String params);
	
	/**
	 * 初始化审核
	 * 
	 * @param params
	 * @return
	 */
	@WebMethod
	@POST
	@Path("/platform/initApprove")
	public Response initApprove(String params);
	
	/**
	 * 获取审批实例信息
	 * 
	 * @param instanceId
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/platform/approve/freeinstance/{instanceId}")
	public Response getApproveFreeInstance(@PathParam("instanceId") String instanceId);
	
	/**
	 * 获取审批实例信息
	 * 
	 * @param approveObjectId
	 * @param processInstanceId
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/platform/approve/adjustinstance/{approveObjectId}/{processInstanceId}")
	public Response getApproveAdjustInstance(@PathParam("approveObjectId") String approveObjectId, @PathParam("processInstanceId") String processInstanceId);
	
	/**
	 * 获取虚机变更审批信息
	 *
	 * @param approveObjectId
	 * @param processInstanceId
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/platform/approve/adjustinstanceDetail/{approveObjectId}/{processInstanceId}")
	public Response getApproveAdjustInstanceDetail(@PathParam("approveObjectId") String approveObjectId, @PathParam("processInstanceId") String processInstanceId);

	/**
	 * 获取其他变更审批信息
	 *
	 * @param approveObjectId
	 * @param processInstanceId
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/adjustOtherDetail/{approveObjectId}/{processInstanceId}")
	public Response getApproveAdjustOtherDetail(@PathParam("approveObjectId") String approveObjectId, @PathParam("processInstanceId") String processInstanceId);
	
	/**
	 * 获取已审批实例信息
	 *
	 * @param approveObjectId
	 * @param processInstanceId
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/platform/approved/adjustinstance/{approveObjectId}/{processInstanceId}")
	public Response getApprovedAdjustInstance(@PathParam("approveObjectId") String approveObjectId, @PathParam("processInstanceId") String processInstanceId);

	/**
	 * 获取已审批实例信息
	 *
	 * @param approveObjectId
	 * @param processInstanceId
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/platform/approved/adjustOther/{approveObjectId}/{processInstanceId}")
	public Response getApprovedAdjustOther(@PathParam("approveObjectId") String approveObjectId, @PathParam("processInstanceId") String processInstanceId);
	
	/**
	 * 获取需回收实例信息
	 * 
	 * @param instanceSid
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/portal/approve/idleinstance/{instanceSid}")
	public Response getIdleAdjustInstance(@PathParam("instanceSid") String instanceSid);	
	
	/**
	 * 进行资源的回收操作
	 * 
	 * @param instanceSid
	 * @return
	 */
	@WebMethod
	@GET
	@Path("/portal/approve/recovery/{instanceSid}")
	public Response insertRecovery(@PathParam("instanceSid") String instanceSid);

	/**
	 * 终审前的资源检查
	 * 
	 * @param params
	 * @return
	 */
	@WebMethod
	@POST
	@Path("/platform/vmResCheck")
	public Response vmResCheck(String params);

	/**
	 * 获取终审Hba信息
	 */
	@WebMethod
	@POST
	@Path("/hba")
	public Response getHbaList(String params);

	/**
	 * 获取终审交换机信息
	 */
	@WebMethod
	@POST
	@Path("/switch")
	public Response getSwithList(String params);

	/**
	 * 获取CPU资源池信息
	 */
	@WebMethod
	@POST
	@Path("/cpuPool")
	public Response getCpuPoolList(String params);
	
	/**
	 * 获取内网网络
	 */
	@WebMethod
	@POST
	@Path("/lans")
	public Response getLanList(String params);
	
	/**
	 * 检查主机名是否重复
	 */
	@WebMethod
	@POST
	@Path("/checkVmName")
	public Response checkVmName(String params);

}
