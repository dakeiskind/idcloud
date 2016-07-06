package com.h3c.idcloud.core.rest.res.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.res.ResVmBackup;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.rest.res.ResVmBackUpRest;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.res.api.ResVmBackupService;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.core.service.system.api.SidService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class ResVmBackUpRestImpl implements ResVmBackUpRest {
	
	/** 服务实例Service **/
	@Reference(version = "1.0.0")
	private ServiceInstanceService instanceService;
	
	/** 服务实例和资源的管理Service **/
	@Reference(version = "1.0.0")
	private ServiceInstResService instResService;

	/** 流水账号Service */
	@Reference(version = "1.0.0")
	private SidService sidService;
	
	/** 虚拟机快照Service **/
	@Reference(version = "1.0.0")
	private ResVmBackupService vmBackupService;
	
	/** 管理对象Service **/
	@Reference(version = "1.0.0")
	private MgtObjService mgtObjService;
	
	@Override
	public Response createSnapshot(String params) {
		String resultJson = "";
		try {
			Map<String, String> map = JsonUtil.fromJson(params, Map.class);
			long instanceSid = Long.parseLong(map.get("instanceSid"));
			String snapshotName = map.get("snapshotName");
			ServiceInstance instance = this.instanceService.selectByPrimaryKey(instanceSid);
//			String snapShotId = sidService.getMaxSid(WebConstants.SidCategory.VD_SNAPSHOT_ID);
			String snapShotId = sidService.getMaxSid(WebConstants.SidCategory.VM_NAME);
			
			MgtObj mgtObj = mgtObjService.selectByPrimaryKey(instance.getMgtObjSid());
			
			Criteria example = new Criteria();
			example.put("instanceSid", instanceSid);
			List<ServiceInstRes> instRes = this.instResService.selectByParams(example);
			if(!CollectionUtils.isEmpty(instRes)){
				ResVmBackup vmBackup = new ResVmBackup();
				vmBackup.setResVmSid(instRes.get(0).getResId());
				vmBackup.setBackupName(snapShotId);
				vmBackup.setBackupAlias(snapshotName);
				vmBackup.setBackupType(WebConstants.BACKUP_TYPE.SNAPSHOT);
				vmBackup.setStatus(WebConstants.BACKUP_STATUS.CREATING);
				vmBackup.setBackupTime(new Date());
				vmBackup.setCreatedDt(new Date());
				vmBackup.setCreatedBy(AuthUtil.getAuthUser().getAccount());
				vmBackup.setUpdatedDt(new Date());
				vmBackup.setUpdatedBy(AuthUtil.getAuthUser().getAccount());
				vmBackup.setVersion(1L);
				ResInstResult result = this.vmBackupService.createSnapShot(vmBackup,mgtObj);
				if (result.getStatus()) {
					resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
							.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)));
				} else {
					resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
							.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
				}
			}else{
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
						.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
			}
			
		} catch (Exception e) {
			resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
		}
		return Response.status(Status.OK).entity(resultJson).build();
	}

	@Override
	public Response getSnapshotList(@PathParam("zone") String zone ,String params) {
		String resultJson = "";
		String veSid = "";
		Criteria example = new Criteria();

//		String resultJson = "";
//		try {
//			Map<String, String> map = JsonUtil.fromJson(params, Map.class);
//			long instanceSid = Long.parseLong(map.get("instanceSid").toString());
//			Criteria example = new Criteria();
//			example.put("instanceSid", instanceSid);
//			List<ServiceInstRes> instRes = this.instResService.selectByParams(example);
//
//			example = new Criteria();
//			example.put("resVmSid", instRes.get(0).getResId());
//			example.put("backupType", WebConstants.BACKUP_TYPE.SNAPSHOT);
//			example.setOrderByClause("A.BACKUP_TIME DESC");
			List<ResVmBackup> bakcups = vmBackupService.selectByParams(example);
			resultJson = JsonUtil.toJson(bakcups);
//		} catch (Exception e) {
//			resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
//		}
//		return Response.status(Status.OK).entity(resultJson).build();
		return Response.status(Status.OK).entity(resultJson).build();
	}

	@Override
	public Response updateVmBackup(String params) {
		String returnJson = "";
		try {
			Map<String, String> map = JsonUtil.fromJson(params, Map.class);
			long resVmBackupSid = Long.parseLong(map.get("resVmBackupSid").toString());
			String name = map.get("backupName").toString();
			ResVmBackup vmBackup = new ResVmBackup();
			vmBackup.setBackupSid(resVmBackupSid);
			vmBackup.setBackupAlias(name);
			int result = vmBackupService.updateByPrimaryKeySelective(vmBackup);
				
			if (result == 1) {
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
			} else {
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
						.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
			}

		} catch (Exception e) {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
		}
		return Response.status(Status.OK).entity(returnJson).build();
	}

	@Override
	public Response deleteSnapShot(String params) {
		String returnJson = "";
		try {
			Map<String, String> map = JsonUtil.fromJson(params, Map.class);
			long resVmBackupSid = Long.parseLong(map.get("backupSid").toString());
			long instanceSid = Long.parseLong(map.get("instanceSid").toString());
			ServiceInstance instance = this.instanceService.selectByPrimaryKey(instanceSid);
			MgtObj mgtObj = mgtObjService.selectByPrimaryKey(instance.getMgtObjSid());
			
			ResInstResult result = this.vmBackupService.deleteVmSnapShot(resVmBackupSid,mgtObj);
			
			if (result.getStatus()) {
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)));
			} else {
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
						.getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)));
			}

		} catch (Exception e) {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)));
		}
		return Response.status(Status.OK).entity(returnJson).build();
	}

	@Override
	public Response revertVmBySnapshot(String params) {
		String returnJson = "";
		try {
			Map<String, String> map = JsonUtil.fromJson(params, Map.class);
			long instanceSid = Long.parseLong(map.get("instanceSid").toString());
			ServiceInstance instance = this.instanceService.selectByPrimaryKey(instanceSid);
			MgtObj mgtObj = mgtObjService.selectByPrimaryKey(instance.getMgtObjSid());
			long backupSid = Long.parseLong(map.get("snapshotSid").toString());
			ResInstResult result = this.vmBackupService.revertVmBySnapshot(backupSid,mgtObj);
			
			if (result.getStatus()) {
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage("虚拟机恢复成功！")));
			} else {
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
						.getMessage("虚拟机恢复失败！")));
			}

		} catch (Exception e) {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage("虚拟机恢复失败！")));
		}
		return Response.status(Status.OK).entity(returnJson).build();
	}

}
