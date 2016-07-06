/**
 * 
 */
package com.h3c.idcloud.core.rest.user.impl;

import java.util.List;

import javax.jws.WebMethod;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.user.Module;
import com.h3c.idcloud.core.pojo.dto.user.Role;
import com.h3c.idcloud.core.pojo.dto.user.RoleModule;
import com.h3c.idcloud.core.pojo.dto.user.UserRole;
import com.h3c.idcloud.core.rest.user.RolesRest;
import com.h3c.idcloud.core.service.user.api.ModuleService;
import com.h3c.idcloud.core.service.user.api.RoleModuleService;
import com.h3c.idcloud.core.service.user.api.RoleService;
import com.h3c.idcloud.core.service.user.api.UserRoleService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


/**
 * @author zharong
 * 
 */
@Component
public class RolesRestImpl implements RolesRest {

	Logger log = Logger.getLogger(this.getClass());

	/** 角色Service */
	@Reference(version = "1.0.0")
	RoleService roleService;

	/** 权限Service */
	@Reference(version = "1.0.0")
	ModuleService moduleService;
	
	/** 角色功能菜单Service */
	@Reference(version = "1.0.0")
	private RoleModuleService roleModuleService;

//	/** 租户Service */
//	@Autowired
//	private TenantService tenantService;
	
	/** 用户角色对应关系Service */
	@Reference(version = "1.0.0")
	private UserRoleService userRoleService;
	

	/**
	 * 查询角色列表
	 */
	@Override
	@WebMethod
	@POST
	public Response findRole(Role role) {

		Criteria example = new Criteria();
		
		if(!StringUtil.isNullOrEmpty(role.getRoleName())){
			example.put("roleNameLike", role.getRoleName());
		}
		
		List<Role> list = this.roleService.selectByParams(example);

		String json = JsonUtil.toJson(list);
		return Response.status(Status.OK).entity(json).build();
	}
	
	/**
	 * 查询角色列表
	 */
	@Override
	@WebMethod
	@POST
	@Path("/findRoleByName")
	public Response findRoleByName(Role role) {
		
		Criteria example = new Criteria();
		
		if(!StringUtil.isNullOrEmpty(role.getRoleName())){
			example.put("roleName", role.getRoleName());
		}
		
		List<Role> list = this.roleService.selectByParams(example);
		
		String json = JsonUtil.toJson(list);
		return Response.status(Status.OK).entity(json).build();
	}
	
	/**
	 * 插入选中的权限
	 */
	@Override
	@WebMethod
	@POST
	@Path("/add")
	public Response insertRoleModules(List<RoleModule> list) {
		String returnJson = "";
	
		boolean result = this.roleModuleService.saveRoleModule(list);
		
		if (result) {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
					.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS), null));
		} else {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE), null));

		}
		return Response.status(Status.OK).entity(returnJson).build();
	}
	
	/**
	 * 新增角色
	 */
	@Override
	@WebMethod
	@POST
	@Path("/create")
	public Response createRole(Role role) {
		String returnJson = "";
		WebUtil.prepareInsertParams(role);
		int result = this.roleService.insertSelective(role);
		if (result==1) {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
					.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));
		} else {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE), null));
		}
		return Response.status(Status.OK).entity(returnJson).build();

	}
	
	/**
	 * 更新角色
	 */
	@Override
	@WebMethod
	@POST
	@Path("/update")
	public Response updateRole(Role role) {
		String returnJson = "";
		WebUtil.prepareUpdateParams(role);
		int result = this.roleService.updateByPrimaryKeySelective(role);
		if (1 == result) {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
					.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));
		} else {
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE), null));
		}

		return Response.status(Status.OK).entity(returnJson).build();
	}
	
	/**
	 * 删除角色
	 */
	@Override
	@WebMethod
	@GET
	@Path("/delete/{roleSid}")
	public Response deleteRole(@PathParam("roleSid") Long roleSid) {
		String returnJson = "";
		// 判断该角色下面是否存在用户
		Criteria example = new Criteria();
		example.put("roleSid", roleSid);
		List<UserRole> list = this.userRoleService.selectByParams(example);
		if(list.size() > 0){
			returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
					.getMessage("该角色已关联用户，不能删除！"), null));
		}else{
			int result = this.roleService.deleteByPrimaryKey(roleSid);
			if(1 == result){
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
						.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS), null));
			}else{
				returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
						.getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE), null));
			}
		}
		
		return Response.status(Status.OK).entity(returnJson).build();
	}
	
	
	@Override
	@WebMethod
	@GET
	@Path("/modules")
	public Response findAllModules(@QueryParam("roleSid") long roleSid,@QueryParam("moduleCategory") int moduleCategory) {
		Criteria example = new Criteria();
		example.put("roleSid", roleSid);
		example.put("moduleCategory", moduleCategory);
		example.put("displayFlag", "1");
		List<Module> list = moduleService.selectByParams(example);
		String json = JsonUtil.toJson(list);
		return Response.status(Status.OK).entity(json).build();
	}
}
