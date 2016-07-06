<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/pages/common/taglibs.jsp"%>
	<%@ include file="/pages/common/resources.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
	<script type="text/javascript" src="${ctx}/js/sys/role-mgt-model.js"></script>

	<style type="text/css">
		#moduleTree .jqx-widget-content-metro {
			border: 0px;

		}
	</style>
</head>
<body>
<div
		style="position: relative; width: 98%; height: 95%; margin-left: 1%; margin-top: 10px;overflow: hidden">
	<div id='roleDatagrid' style="width: 60%; height: 90%"></div>
	<div style="position: absolute; left: 60.5%; top: 0px; border: 1px solid #E5E5E5; width: 39%; height: 98%;">
		<div style="width: 100%; height: 29px; background: #F4F4F4; padding-top: 4px;line-height:24px; border-bottom: 1px solid #E5E5E5;">
			<a id='roleSavebtn' style="margin-left: 5px;"
			   onclick="saveRoleModule()">&nbsp;&nbsp;<i
					class='icons-blue icon-floppy'></i>保存&nbsp;&nbsp;
			</a>
		</div>
		<div id="moduleTree" style="width: 100%; height: 94%; padding-top: 0px;">
			<div id='viewType' style="border: 0px;">
				<ul>
					<li style="margin-left: 20px;">运维门户</li>
					<li>自服务门户</li>
				</ul>
				<div>
					<div id="backModuleTree" style="border: 0px; border-top: 0px;"></div>
				</div>
				<div>
					<div id="frontModuleTree" style="border: 0px; border-top: 0px;"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="addRoleWindow">
	<div>新增角色</div>
	<div id="addRoleForm" style="overflow: hidden;">
		<table border="0" width="100%" cellspacing="5" cellpadding="0">
			<tr>
				<td align="right"><font style="color: red">*</font>角色名称:</td>
				<td align="left"><input type="text" data-name="roleName"
										id="add-roleName" /></td>
			</tr>
			<tr>
				<td align="right">角色描述:</td>
				<td align="left"><textarea data-name="roleDesc"
										   id="add-roleDesc"></textarea></td>
			</tr>
			<tr>
				<td align="right">角色类型:</td>
				<td align="left"><input id="add-roleType-01" type="radio"
										name="add-roleType" value="01" checked="checked" /><span>前台</span>
					<input id="add-roleType-02" type="radio" name="add-roleType"
						   value="02" /><span>后台</span></td>
			</tr>

			<tr>
				<td align="center" colspan="2"><input
						style="margin-right: 5px;" type="button" onclick="submitAddRole()"
						id="addRoleSave" value="保存" /> <input id="addRoleCancel"
															  type="button" value="取消" /></td>
			</tr>
		</table>
	</div>
</div>

<div id="editRoleWindow">
	<div>编辑角色</div>
	<div id="editRoleForm" style="overflow: hidden;">
		<input type="hidden" data-name="roleSid" id="roleSid" />
		<table border="0" width="100%" cellspacing="5" cellpadding="0">
			<tr>
				<td align="right"><font style="color: red">*</font>角色名称:</td>
				<td align="left"><input type="text" data-name="roleName"
										id="edit-roleName" /></td>
			</tr>
			<tr>
				<td align="right">角色描述:</td>
				<td align="left"><textarea data-name="roleDesc"
										   id="edit-roleDesc"></textarea></td>
			</tr>
			<tr>
				<td align="right">角色类型:</td>
				<td align="left"><input id="edit-roleType-01" type="radio"
										name="edit-roleType" value="01" /><span>前台</span> <input
						id="edit-roleType-02" type="radio" name="edit-roleType" value="02" /><span>后台</span></td>
			</tr>
			<tr>
				<td align="center" colspan="2"><input
						style="margin-right: 5px;" type="button"
						onclick="submitEditRole()" id="editRoleSave" value="保存" /> <input
						id="editRoleCancel" type="button" value="取消" /></td>
			</tr>
		</table>
	</div>
</div>
</body>
<script type="text/javascript">
	$(function() {
		initRolePageJs();
		$('#viewType').jqxTabs({
			position : 'top',
			width : "100%",
			height : "100%",
			theme : "metro",
			selectionTracker : "checked"
		});
	});

	function initRolePageJs() {
		// 初始化sys-index页面model
		var rolemodel = new roleModel();
		// 初始化页面组件
		rolemodel.initInput();
		// 初始化弹出框
		rolemodel.initPopWindow();
		// 初始化datagrid
		rolemodel.initRoleDatagrid();
		// 为datagrid赋值
		rolemodel.searchRoleInfo();
		// 给权限Module赋值
		rolemodel.searchModuleTreeInfo();
		// 初始化组件验证规则
		rolemodel.initValidator();

		return rolemodel;
	}
</script>
</html>
