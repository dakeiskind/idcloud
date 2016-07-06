package com.h3c.idcloud.core.rest.res.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.adapter.pojo.network.result.SecurityGroupQueryResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.ServerSecurityGroupAddResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.ServerSecurityGroupDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmQuerySgsResult;
import com.h3c.idcloud.core.pojo.dto.res.ResSecurityGroup;
import com.h3c.idcloud.core.pojo.instance.ResSecurityGroupInst;
import com.h3c.idcloud.core.rest.res.SecurityGroupRest;
import com.h3c.idcloud.core.service.res.api.ResSecurityGroupService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.jboss.resteasy.spi.ApplicationException;
import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Map;
@Component
public class SecurityGroupRestImpl implements SecurityGroupRest {

	@Reference(version = "1.0.0")
	ResSecurityGroupService resSecurityGroupService;
	/**
	 * 新增
	 * 
	 * */
	@Override
	public Response create(String params) {
		String resultJson = "";
		try {
			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);

			String name = map.get("name").toString();
			String desp = map.get("desp").toString();
			String mgtObjSid = map.get("mgtObjSid").toString();
			
			ResSecurityGroupInst resSecurityGroupInst = new ResSecurityGroupInst();
			resSecurityGroupInst.setDescription(desp);
			resSecurityGroupInst.setMgtObjSid(Long.parseLong(mgtObjSid));
			resSecurityGroupInst.setSecurityGourpName(name);
			
			SgCreateResult createResult = resSecurityGroupService.createSg(resSecurityGroupInst);
			if (createResult != null && createResult.isSuccess()) {
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)));
			} else {
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
						.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
			}
		} catch (ApplicationException e) {
			resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
		} catch (Exception e) {
			resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
		}
		return Response.status(Status.OK).entity(resultJson).build();
	}

	/**
	 * 编辑安全组
	 */
	@Override
	@WebMethod
	@POST
	@Path("/update")
	public Response update(String params){
		String resultJson = "";
		try {
			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
			String id = map.get("id").toString();
			String desp = map.get("desp").toString();
			String name = map.get("name").toString();
			
			ResSecurityGroup rg = new ResSecurityGroup();
			rg.setId(Long.parseLong(id));
			rg.setDescription(desp);
			rg.setSecurityGroupName(name);
			int result = resSecurityGroupService.updateByPrimaryKeySelective(rg);
			if (result==1) {
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
			} else {
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
						.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
			}				
		} catch (Exception e) {
			resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
		}
		return Response.status(Status.OK).entity(resultJson).build();
	}
	
	/**
	 * 删除
	 * 
	 */
	@Override
	@WebMethod
	@DELETE
	@Path("/delete/{id}")
	public Response delete(@PathParam("id") String id) {
		String resultJson;
		SgDeleteResult result = resSecurityGroupService.deleteSg(id);
		if (result.isSuccess()) {
			resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
					.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS)));
		} else {
			resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE)));
		}

		return Response.status(Status.OK).entity(resultJson).build();
	}

	@Override
	@WebMethod
	@POST
	@Path("/queryByParams")
	public Response query(String params) {
		String resultJson = "";
		try {
			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
			String mgtObjSid = map.get("mgtObjSid").toString();
			String name = map.get("name").toString();
			
			Criteria example = new Criteria();
			example.put("mgtObjSid",Long.parseLong(mgtObjSid));
			example.put("securityGroupName",name);
			List<ResSecurityGroup> groups = resSecurityGroupService.selectByParams(example);
			resultJson = JsonUtil.toJson(new RestResult(groups));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Status.OK).entity(resultJson).build();
	}
	
	@Override
	@WebMethod
	@POST
	@Path("/queryByVm")
	public Response queryByVm(String params) {
		String resultJson = "";
		try {
			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
			String mgtObjSid = map.get("mgtObjSid").toString();
			String vmId = map.get("vmSid").toString();
			VmQuerySgsResult groups = resSecurityGroupService.selectByVm(vmId,mgtObjSid);
			resultJson = JsonUtil.toJson(new RestResult(groups.getSecurityGroups()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Status.OK).entity(resultJson).build();
	}

	@Override
	@WebMethod
	@POST
	@Path("/attach")
	public Response attach(String params) {
		String resultJson = "";
		try {
			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
			String vm = map.get("vm").toString();
			String sg = map.get("sg").toString();
			String[] sgIds = sg.split(",");
			ServerSecurityGroupAddResult result = resSecurityGroupService.attach(sg, vm);
			if (result.isSuccess()) {
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, "安全组绑定成功！", null));
			} else {
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,"安全组绑定失败！", null));
			}
		} catch (ApplicationException e) {
			resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,"安全组绑定失败！", null));
		} catch (Exception e) {
			resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, "安全组绑定失败！", null));
		}
		return Response.status(Status.OK).entity(resultJson).build();
	}

	@Override
	@WebMethod
	@POST
	@Path("/detach")
	public Response detach(String params) {
		String resultJson = "";
		try {
			Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
			String vm = map.get("vm").toString();
			String sg = map.get("sg").toString();
			ServerSecurityGroupDeleteResult groupDeleteResult = this.resSecurityGroupService.detach(sg, vm);
			if (groupDeleteResult.isSuccess()) {
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, "安全组解除绑定成功！", null));
			} else {
				resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,"安全组解除绑定失败！", null));
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
			resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,"安全组解除绑定失败！", null));
		} catch (Exception e) {
			e.printStackTrace();
			resultJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,"安全组解除绑定失败！", null));
		}
		return Response.status(Status.OK).entity(resultJson).build();
	}

	@Override
	public Response queryByTenant(@PathParam("mgtObjSid") String mgtObjSid) {
		Criteria example = new Criteria();
		example.put("mgtObjSid", mgtObjSid);
		SecurityGroupQueryResult list = this.resSecurityGroupService.selectSecurityGroups(mgtObjSid);
		String json = JsonUtil.toJson(new RestResult(list.getSecurityGroups()));
		return Response.status(Status.OK).entity(json).build();
	}

}
