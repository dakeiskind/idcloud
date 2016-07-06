<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/approve/unapprove-info-model.js"></script>
		<%--<script type="text/javascript" src="${ctx}/js/approve/approved-info-model.js"></script>--%>
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
			<td><input type="text" id="search-flow-instance" />&nbsp;&nbsp;</td>
			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;申请人：</td>
			<td><input type="text" id="search-flow-createdby" />&nbsp;&nbsp;</td>
			<td align="right" nowrap="nowrap">流程类型：</td>
			<td>
				<div id="search-flow-type-unapprove"></div>
			</td>
			<td><a data-bind="click:searchUnapproveFlow"
				id="search-approve-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
		</tr>
	</table>
</div>
<div style="width: 98%; height: 80%; margin-left: 1%;">
	<div id='unapprovedatagrid' style="width: 100%; height: 450px;"></div>
</div>
<!-- 开通审核实例规格修改弹出窗 -->
<div id="instanceSpecEditWindow" style="display: none">
    <div id="windowHeader">
        <span>
                      修改订单实例规格
        </span>
    </div>
	<div id="windowContent" style="overflow: hidden; position: relative; height:480px">
		<div id="instanceSpecEditForm" >
		<table border="0" width="100%" cellspacing="5" cellpadding="0">
			<tr><td colspan="2">
				<table border="0" width="100%" cellspacing="5" cellpadding="0">
					<tr>
						<td align="right"><font style="color:red">*</font>操作系统名称:</td>
						<td align="left"><input type="text" id="instanceSpecName" /></td>
					</tr>
					<tr>
						<td align="right"><font style="color:red">*</font>主机名称:</td>
						<td align="left"><input type="text" id="resInstanceSpecName" /></td>
					</tr>
					<tr>
						<td align="right"></td>
						<td id="hostNameRule"align="left">如：开发测试云-DEV-KFCSY-RHEL6.4-64-1</td>
					</tr>
					<tr>
						<td align="right">CPU核数:</td>
						<td align="left"><div id="instanceSpecCpu"></div></td>
					</tr>
					<tr>
						<td align="right">内存(G):</td>
						<td align="left"><div id="instanceSpecMemory"></div></td>
					</tr>
					
					<tr style="display:none">
						<td align="right">操作系统:</td>
						<td align="left"><div id="instanceSpecOs"></div></td>
					</tr>
					<!-- 
					<tr>
						<td align="right">是否需要外网IP:</td>
						<td align="left"><div id="instanceSpecWan"></div></td>
					</tr>
					<tr>
						<td align="right">是否需要内网IP:</td>
						<td align="left"><div id="instanceSpecLan"></div></td>
					</tr>
					 -->
					<tr style="display:none">
						<td align="right">是否修改所有实例:</td>
						<td align="left"><input id="updateAllFlag" type="checkbox" /></td>
					</tr>
				</table>
			</td></tr>
		</table>
		</div>
		<div style="text-align: right">
			<input data-bind='click: saveInstanceSpecEdit' style="margin-right: 6px;margin-top: 6px;" id="instanceSpecEditSave" type="button" value="保存" />
			<input style="margin-right: 6px;margin-top: 6px;" id="instanceSpecEditCancel" type="button" value="关闭" />
		</div>
	</div>
</div>
<%--
 <%@ include file="approve-detail-window.jsp"%>
 --%>
 <%@ include file="approve-detail-tab.jsp"%>

<script type="text/javascript">

	$(function(){

		// 初始化未审核js
		var unapprove = initUnapprovePageJs();

		unapprove.searchApproveInfo();

		var bindmodel = new approveBindModel(unapprove,null);

		bindmodel.initReflushApproveList();
		//将model和view绑定
		ko.applyBindings(bindmodel);
	});




	function initUnapprovePageJs() {
		// 初始化sys-index页面model
		var unappModel = new unapproveModel();
		// 初始化页面组件
		unappModel.initInput();
		// 初始化弹出框
		unappModel.initPopWindow();
		// 初始化组件验证规则
		unappModel.initValidator();
		// 初始化datagrid
		unappModel.initUnapproveDatagrid();
		// 为datagrid赋值
		unappModel.searchApproveInfo();

		return unappModel;
	}
</script>

</html>