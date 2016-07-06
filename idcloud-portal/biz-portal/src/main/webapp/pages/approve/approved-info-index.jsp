<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<%--<script type="text/javascript" src="${ctx}/js/approve/unapprove-info-model.js"></script>--%>
		<script type="text/javascript" src="${ctx}/js/approve/approved-info-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/approve/approve-bind-model.js"></script>

		<title></title>
		<style type="text/css">
		#containerApprove{
			width:100%;
			height:100%;
			margin:0px;
			padding:0px;
		}
		.specGrid-cell-red {
			/** color:red */
			background-color: red
		}
		.tableCss{
			border-right:1px solid #dadada;
			border-bottom:1px solid #dadada;
			border-spacing: 0;
			overflow: hidden;
			width:99.9%;
			height:100%;
			font-family: "微软雅黑", "Microsoft Yahei", Georgia, Serif;
			color: #787878;
		}
		.tableCss td{color: #000;border-left:1px solid #dadada;border-top:1px solid #dadada}
		.tableCss th{border-left:1px solid #dadada;border-top:1px solid #dadada}
	</style>
	</head>
<div style="width: 100%; height: 30px; padding: 10px 0px 10px 0px;">
	<table border="0" height="100%" cellspacing="0" cellpadding="2">
		<tr>
			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;审核对象：</td>
			<td><input type="text" id="search-flowed-instance" />&nbsp;&nbsp;</td>
			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;审核人：</td>
			<td><input type="text" id="search-flowed-createdby" />&nbsp;&nbsp;</td>
			<td align="right" nowrap="nowrap">流程类型：</td>
			<td>
				<div id="search-flow-type-approve"></div>
			</td>
			<td align="right" nowrap="nowrap">审核时间：</td>
			<td>
				<div id='approveFromTime'></div>
			</td>
			<td>至</td>
			<td><div id='approveToTime'></div></td>
			<td><a data-bind="click:searchapprovedFlow"
				id="search-approved-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
		</tr>
	</table>
</div>
<div style="width: 98%; height: 80%; margin-left: 1%;">
	<div id='approveddatagrid' style="width: 100%; height: 450px;"></div>
</div>


 <%@ include file="approved-detail-window.jsp"%>
<!-- 
<div id="instance-approvedWindow" style="display:none" >
	<div class="title">&nbsp;</div>
	<div id="instance-approvedForm" style="overflow: hidden; position: relative;">
		<input type="hidden" id="instance-approvedprocessInstanceId" />
		<input type="hidden" id="instance-approvedapprovorAction" />
		<input type="hidden" id="instance-approvedprocessType" />
		<input type="hidden" id="instance-approvedinstanceSid" />
		<table id="instance-apporvedTargetInfo" border="0" width="100%" cellspacing="5" cellpadding="0">
			<tr><td>
				<table border="0" width="100%" cellspacing="5" cellpadding="0">
					<tr>
						<td align="right" width="75px">实例号:</td>
						<td align="left"><span id="instance-approvedinstanceName"></span></td>
						<td align="right">所有者:</td>
						<td align="left"><span id="instance-approvedowner"></span></td>
					</tr>
					<tr>
						<td align="right">所属业务:</td>
						<td align="left"><span id="instance-approvedbizText"></span></td>
						<td align="right">&nbsp;</td>
						<td align="left">&nbsp;</td>
					</tr>
				</table>
			</td></tr>
			<tr><td>
				<div id="instance-approvedspecGrid"></div>
			</td></tr>
			<tr><td>
				<div id="instance-approveddiskSpecGrid"></div>
			</td></tr>
		</table>
		<div
			style="text-align: right">
				<input style="margin-right: 6px;" id="instance-approvedDetailCancel" type="button" value="取消" />
		</div>
	</div>
</div>
 -->

<script type="text/javascript">

	$(function(){

		// 初始化用户管理js
		var approved =	initApprovedPageJs();

		approved.searchApproveInfo();

		var bindmodel = new approveBindModel(null,approved);

		bindmodel.initReflushApproveList();
		//将model和view绑定
		ko.applyBindings(bindmodel);
	});



	function initApprovedPageJs() {
		// 初始化sys-index页面model
		var appedModel = new approvedModel();
		// 初始化页面组件
		appedModel.initInput();
		// 初始化弹出框
		appedModel.initPopWindow();
		// 初始化组件验证规则
		appedModel.initValidator();
		// 初始化datagrid
		appedModel.initApprovedDatagrid();
		// 为datagrid赋值
		appedModel.searchApproveInfo();

		return appedModel;
	}
</script>