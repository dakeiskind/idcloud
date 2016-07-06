package com.h3c.idcloud.core.rest.res;

import javax.jws.WebMethod;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("cs/snapshot")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface ResVmBackUpRest {
	
	/**
	 * 创建快照
	 */
	@WebMethod
	@POST
	@Path("/createSnapshot")
	public Response createSnapshot(String params);
	
	/**
	 * 得到快照列表
	 */
	@WebMethod
	@POST
	@Path("/getSnapshotList/{zone}")
	public Response getSnapshotList(@PathParam("zone") String zone ,String params);
	
	/**
	 * 更新快照
	 */
	@WebMethod
	@POST
	@Path("/updateVmBackup")
	public Response updateVmBackup(String params);
	
	/**
	 * 删除快照
	 */
	@WebMethod
	@POST
	@Path("/deleteSnapShot")
	public Response deleteSnapShot(String params);
	
	/**
	 * 从快照恢复虚拟机
	 */
	@WebMethod
	@POST
	@Path("/revertVmBySnapshot")
	public Response revertVmBySnapshot(String params);

}
