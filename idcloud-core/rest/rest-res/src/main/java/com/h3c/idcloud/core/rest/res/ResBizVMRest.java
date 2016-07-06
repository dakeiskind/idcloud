package com.h3c.idcloud.core.rest.res;
import com.h3c.idcloud.core.pojo.dto.res.ResBizVm;
import com.h3c.idcloud.core.pojo.dto.res.ResBizVmTO;
import org.springframework.context.annotation.Scope;
import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/resbiz/vm")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Scope("singleton")
public interface ResBizVMRest {
	
	/**
	 * 查询vm列表
	 */
	@WebMethod
	@POST
	@Path("/findVM")
	public Response findVM(ResBizVm vm);
	
	/**
	 * 查询可纳管的vm列表
	 */
	@WebMethod
	@POST
	@Path("/findNanotubeVm")
	Response findNanotubeableVM(ResBizVm vm);
	
	/**
	 * 查询vm列表
	 */
	@WebMethod
	@POST
	@Path("/findVMAndMonitorInfo")
	public Response findVMAndMonitorInfo(ResBizVm vm);
	
	/**
	 * 查询虚拟机列表--分页
	 */
	@WebMethod
	@GET
	@Path("/findAll")
	public Response findBizVmByPaging(@Context HttpServletRequest request);
	
	/**
	 * 查询项目资源统计
	 */
	@WebMethod
	@POST
	@Path("/findMgtObjResCount")
	public Response findMgtObjResCount(String params);
	
	/**
	 * 获取虚拟机信息
	 */
	@WebMethod
	@POST
	@Path("/getVmInfo")
	public Response getVmInfo(ResBizVm vm)throws Exception;
	
	/**
	 * 虚拟机纳管
	 */
	@WebMethod
	@POST
	@Path("/relateResBizVm")
	public Response relateResBizVm(ResBizVm bizVm);
	
	/**
	 * 取消虚拟机纳管
	 */
	@WebMethod
	@POST
	@Path("/unRelateResBizVm")
	public Response unRelateResBizVm(ResBizVmTO resBizVmTO);

	/**
	 * 统计业务对应的虚机个数
	 */
	@WebMethod
	@POST
	@Path("/getBizVmCount")
	public Response getBizVmCount(ResBizVm vm);
	
	/**
	 * 设置虚拟机监控节点
	 */
	@WebMethod
	@POST
	@Path("/setMonitorNode")
	public Response setMonitorNode(ResBizVmTO resBizVmTO);
	
	
	/**
	 * 修改納管以后的虚拟机信息
	 */
	@WebMethod
	@POST
	@Path("/modify")
	public Response modifyInfo(ResBizVmTO resBizVmTO);

	/**
	 * 将虚拟机和业务的关系导出
	 */
	@WebMethod
	@GET
	@Path("/getBizVmForReport/{params}")
	@Produces("application/vnd.ms-excel")
	public Response getBizVmForReport(@PathParam("params") String params, @Context HttpServletResponse servletResponse);
	
	/**
	 * POI - 导出vm列表
	 */
	@WebMethod
	@GET
	@Path("/exportBizVm/{params}")
	@Produces("application/vnd.ms-excel")
	public Response exportBizVmDatagrid(@PathParam("params") String params);

	/**
	 * 将业务虚拟机监控信息导出
	 */
	@WebMethod
	@GET
	@Path("/getBizVmMonitorForReport/{params}")
	@Produces("application/vnd.ms-excel")
	public Response getBizVmMonitorForReport(@PathParam("params") String params, @Context HttpServletResponse servletResponse);
	
	/**
	 * 统计业务下的虚拟机信息
	 */
	@WebMethod
	@GET
	@Path("/statistical/biz/{resBizSid}")
	Response statisticalBizOfVM(@PathParam("resBizSid") Long resBizSid);

	/**
	 * 将业务虚拟机监控信息导出
	 */
	@WebMethod
	@GET
	@Path("/getVmMonitorForReport")
	@Produces("application/vnd.ms-excel")
	public Response getVmMonitorForReport(@Context HttpServletResponse servletResponse);
	
}
