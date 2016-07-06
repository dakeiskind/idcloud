<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="/pages/common/taglibs.jsp"%>
	<%@ include file="/pages/common/resources.jsp"%>
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<script type="text/javascript" src="${ctx}/js/order/order-model.js"></script>
	<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
	<style type="text/css">
	#containerOrder {
		width: 100%;
		height: 100%;
		margin: 0px;
		padding: 0px;
	}
	.searchInfoDiv {
		color:#767676;
		font-size: 12px;
	</style>
</head>
<body class='default'>
	<div id="containerOrder">
		<div id='jqxWidget'>
			<div>
				<div class="searchInfoDiv" style="width: 100%; height: 30px; padding: 10px 0px 10px 0px;">
					<table border="0" height="100%" cellspacing="0" cellpadding="2">
						<tr>
							<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;订单编号：</td>
							<td><input type="text" id="order-id" />&nbsp;&nbsp;</td>
<!-- 							<td align="right" nowrap="nowrap">所属租户：</td> -->
<!-- 							<td> -->
<!-- 								<div id="tenant"></div> -->
<!-- 							</td> -->
							<td align="right" nowrap="nowrap">&nbsp;&nbsp;订单状态：</td>
							<td>
								<div id="order-status"></div>
							</td>
<!-- 							<td align="right" nowrap="nowrap">&nbsp;&nbsp;项目名称：</td> -->
<!-- 							<td><input type="text" id="order-mgtObjName" />&nbsp;&nbsp;</td> -->
							<td align="right" nowrap="nowrap">&nbsp;&nbsp;所有者：</td>
							<td><input type="text" id="order-ownerId" />&nbsp;&nbsp;</td>
							<td align="right" nowrap="nowrap">&nbsp;&nbsp;订购时间：</td>
							<td>
								<div id="order-create-startDt"></div>
							</td>
							<td align="right" nowrap="nowrap">&nbsp;~&nbsp;</td>
							<td>
								<div id="order-create-endDt"></div>
							</td>
							<td><a data-bind="click: searchOrder" id="search-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
						</tr>
					</table>
				</div>
			    <div style="width:98%;height:80%;margin-left:1%;"> 
			     	<div id='orderGrid'></div> 
			    </div>   
			</div>
		</div>
	</div>
	<%@ include file="order-detail.jsp"%>
	<%@ include file="order-process-info.jsp"%>
	<%@ include file="order-detail-tab.jsp"%>
</body>
<script type="text/javascript">
	$(function() {
		// 创建jqxTabs选项卡,计算tabs的高度
		var containerW = $("#containerOrder").css("height");
		var i = parseInt(containerW) + "px";

		// 初始化sys-index页面model
		var model = new orderModel();
		model.initInput();
		model.initOrderDatagrid();
		model.initPopWindow();

		// 初始化下拉列表框 
		var codesearch = new codeModel();
		codesearch.getCommonCode("order-status", "ORDER_STATUS",true);
		/*
		var tenantsearch = new codeModel({
			autoDropDownHeight : false,
			dropDownWidth : 155,
			dropDownHeight : 185
		});
		tenantsearch.getCustomCode("tenant", "/tenants", "tenantName",
				"tenantSid", true, "post", null);
		*/

		 
		//Grid赋值
		model.searchOrderInfo();
		//将model和view绑定
		ko.applyBindings(model);
	});
</script>
</html>