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
	<script type="text/javascript" src="${ctx}/js/sys/tenant-mgt-model.js"></script>
	
	<style type="text/css">
	#moduleTree .jqx-widget-content-metro {
		border: 0px;
	}
	</style>
</head>
<body>
	<div style="width: 100%; height: 30px; padding: 10px 0px 10px 0px;">
		<table border="0" height="100%" cellspacing="0" cellpadding="2">
			<tr>
				<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;租户名称：</td>
				<td><input type="text" id="search-tenant-name" />&nbsp;&nbsp;</td>
				<td align="right" nowrap="nowrap">租户类型：</td>
				<td>
					<div id="search-tenant-type"></div>
				</td>
				<td align="right" nowrap="nowrap">&nbsp;&nbsp;项目类型：</td>
				<td>
					<div id="search-enterprise-type"></div>
				</td>
				<td align="right">&nbsp;&nbsp;状态：</td>
				<td>
					<div id="search-tenant-status"></div>
				</td>
				<td><a data-bind="click: tenantSearch"
					id="search-tenant-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
			</tr>
		</table>
	</div>
	<div style="width: 98%; height: 80%; margin-left: 1%;">
		<div id='tenantdatagrid' style="width: 100%;"></div>
	</div>

	<div id="addTenantWindow">
		<div>新增租户</div>
		<div id="addTenantForm" style="overflow: hidden;">
			<table border="0" width="100%" cellspacing="5" cellpadding="0">
				<tr>
					<td align="right"><font style="color: red">*</font>租户名称:</td>
					<td align="left"><input type="text" data-name="tenantName"
						id="add-tenant-name" /></td>
					<td align="right"><font style="color: red">*</font>租户类型:</td>
					<td align="left"><div data-name="tenantType"
							id="add-tenant-type"></div></td>
					<td align="right"><font style="color: red">*</font>状态:</td>
					<td align="left"><div data-name="status"
							id="add-Tenant-status"></div></td>
				</tr>
				<tr>
					<td align="right">联系人:</td>
					<td align="left"><input type="text" data-name="contact"
						id="add-tenant-contact" /></td>
					<td align="right">联系人电话:</td>
					<td align="left"><input type="text" data-name="contactPhone"
						id="add-tenant-contactPhone" /></td>
					<td align="right">联系人职务:</td>
					<td align="left"><input type="text"
						data-name="contactPosition" id="add-tenant-contactPosition" /></td>
				</tr>
				<tr>
					<td align="right"><font style="color: red">*</font>邮箱:</td>
					<td align="left"><input type="text" data-name="email"
						id="add-tenant-email" /></td>
					<td align="right">邮编:</td>
					<td align="left"><input type="text" data-name="postCode"
						id="add-tenant-postCode" /></td>
					<td align="right">项目类型:</td>
					<td align="left">
						<div data-name="businessType" id="add-Tenant-businessType"></div>
					</td>
				</tr>
				<tr>
					<td align="right"><font style="color: red">*</font>VLAN:</td>
					<td align="left"><div data-name="vlanId" id="add-Tenant-vlan"></div></td>
					<td align="right">域名:</td>
					<td align="left"><input type="text" data-name="domainName"
						id="add-tenant-domainName" /></td>
					<td align="right">域名地址:</td>
					<td align="left"><input type="text" data-name="domainAddress"
						id="add-Tenant-domainAddress" /></td>
				</tr>
				<tr>
					<td align="right"><font style="color: red">*</font>服务限制数量:</td>
					<td align="left">
						<div data-name="serviceLimitQuantity"
							id="add-Tenant-serviceLimitQuantity"></div>
					</td>
					<td align="right"><font style="color: red">*</font>OU名称:</td>
					<td align="left"><input data-name="tenantShortName"
						id="add-Tenant-tenantShortName" /></td>
					<td align="right">关联组织:</td>
					<td align="left">
						<div data-name="orgSid" id="addOrgSid"></div>
					</td>
				</tr>
				<tr>
					<td align="right">地址:</td>
					<td align="left" colspan="5"><input type="text"
						data-name="address" id="add-Tenant-address" /></td>
				</tr>
				<tr>
					<td align="right">租户描述:</td>
					<td align="left" colspan="5"><textarea data-name="description"
							id="add-Tenant-description"></textarea></td>
				</tr>
				<tr>
					<td align="right" colspan="6"><input
						style="margin-right: 5px;" type="button"
						data-bind='click:addTenant_submit' id="tenantSave" value="保存" />
						<input id="tenantCancel" type="button" value="取消" /></td>
				</tr>
			</table>
		</div>
	</div>

	<div id="editTenantWindow">
		<div>编辑租户</div>
		<div id="editTenantForm" style="overflow: hidden;">
			<input type="hidden" data-name="tenantSid" id="tenantSid" />
			<table border="0" width="100%" cellspacing="5" cellpadding="0">
				<tr>
					<td align="right"><font style="color: red">*</font>租户名称:</td>
					<td align="left"><input type="text" data-name="tenantName"
						id="edit-tenant-name" /></td>
					<td align="right"><font style="color: red">*</font>租户类型:</td>
					<td align="left"><div data-name="tenantType"
							id="edit-tenant-type"></div></td>
					<td align="right"><font style="color: red">*</font>状态:</td>
					<td align="left"><div data-name="status"
							id="edit-Tenant-status"></div></td>
				</tr>
				<tr>
					<td align="right">联系人:</td>
					<td align="left"><input type="text" data-name="contact"
						id="edit-tenant-contact" /></td>
					<td align="right">联系人电话:</td>
					<td align="left"><input type="text" data-name="contactPhone"
						id="edit-tenant-contactPhone" /></td>
					<td align="right">联系人职务:</td>
					<td align="left"><input type="text"
						data-name="contactPosition" id="edit-tenant-contactPosition" /></td>
				</tr>
				<tr>
					<td align="right"><font style="color: red">*</font>邮箱:</td>
					<td align="left"><input type="text" data-name="email"
						id="edit-tenant-email" /></td>
					<td align="right">邮编:</td>
					<td align="left"><input type="text" data-name="postCode"
						id="edit-tenant-postCode" /></td>
					<td align="right">项目类型:</td>
					<td align="left">
						<div data-name="businessType" id="edit-Tenant-businessType"></div>
					</td>
				</tr>
				<tr>
					<td align="right"><font style="color: red">*</font>VLAN:</td>
					<td align="left"><div data-name="vlanId" id="edit-Tenant-vlan"></div></td>
					<td align="right">域名:</td>
					<td align="left"><input type="text" data-name="domainName"
						id="edit-tenant-domainName" /></td>
					<td align="right">域名地址:</td>
					<td align="left"><input type="text" data-name="domainAddress"
						id="edit-Tenant-domainAddress" /></td>
				</tr>
				<tr>
					<td align="right"><font style="color: red">*</font>服务限制数量:</td>
					<td align="left">
						<div data-name="serviceLimitQuantity"
							id="edit-Tenant-serviceLimitQuantity"></div>
					</td>
					<td align="right"><font style="color: red">*</font>OU名称:</td>
					<td align="left"><input data-name="tenantShortName"
						id="edit-Tenant-tenantShortName" /></td>
					<td align="right">关联组织:</td>
					<td align="left">
						<div data-name="orgSid" id="editOrgSid"></div>
					</td>
				</tr>
				<tr>
					<td align="right">地址:</td>
					<td align="left" colspan="5"><input type="text"
						data-name="address" id="edit-Tenant-address" /></td>
				</tr>
				<tr>
					<td align="right">租户描述:</td>
					<td align="left" colspan="5"><textarea data-name="description"
							id="edit-Tenant-description"></textarea></td>
				</tr>
				<tr>
					<td align="right" colspan="6"><input
						style="margin-right: 5px;" type="button"
						data-bind='click:editTenant_submit' id="editTenantSave" value="保存" />
						<input id="editTenantCancel" type="button" value="取消" /></td>
				</tr>
			</table>
		</div>
	</div>

	<div id="quotaTenantWindow">
		<div>配额信息</div>
		<div id="quotaTenantForm" style="overflow: hidden;">
			<input type="hidden" data-name="tenantSid" id="quotaTenantSid" />
			<div id="quotaContent"
				style="width: 100%; height: 420px; overflow-y: auto; border-bottom: 1px solid #eee">
				<table id="tableContent" border="0" width="100%" cellspacing="5"
					cellpadding="0">
				</table>
			</div>
			<div style="width: 100%; height: 40px; text-align: right;">
				<br /> <input style="margin-right: 5px;"
					data-bind="click:quota_submit" type="button" id="quotaTenantSave"
					value="保存" /> <input id="quotaTenantCancel" type="button"
					value="取消" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			initTenantPageJs();
		});

		function initTenantPageJs() {
			// 初始化sys-index页面model
			var tenantmodel = new tenantModel();
			// 初始化页面组件
			tenantmodel.initInput();
			// 初始化弹出框
			tenantmodel.initPopWindow();
			// 初始化组件验证规则
			tenantmodel.initValidator();
			// 初始化datagrid
			tenantmodel.initTenantDatagrid();

			return tenantmodel;
		}
	</script>
</html>