<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/pages/common/taglibs.jsp"%>
	<%@ include file="/pages/common/resources.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
	<script type="text/javascript" src="${ctx}/js/service/service-billing-model.js"></script>
	
	<style type="text/css">

	</style>
</head>
<body>
	<div style="width: 100%; height: 35px;padding:5px 0px 0px 0px">
		<table border="0" height="30px" cellspacing="0" cellpadding="2">
			<tr>
				<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;项目名称：</td>
				<td><input type="text" id="mgtObjNameLike" />&nbsp;&nbsp;</td>
				<td><a onclick="searchServiceInstanceBillingInfo()" id="search-billing-button"><i class='icons-blue icon-search-4'></i>统计&nbsp;</a></td>
			</tr>
		</table>
	</div>
	 
	<div style="width: 100%; height: auto;">
		<div id='jqxgrid' style="margin-left: 1%;"></div>
	</div>
	
	<div id="popServiceBillingDetailsWindow">
		<div>项目服务计量详情</div>
		<div id="ServiceBillingDetailsForm" style="overflow: hidden;">
			 <div id='serviceBillingTabs' style="">
			    <ul>
			        <li style="margin-left:5px;">云主机</li>
			        <li>块存储</li>
			        <li>浮动IP</li>
<!-- 			        <li>对象存储</li>   -->
			    </ul>
			    <div style="overflow:hidden">  
			       	<div style="width:678px; height: 306px;margin:5px">
						<div id='billingVmDatagrid'></div>
					</div>
			    </div>
			    <div style="overflow:hidden">
			    	<div style="width:678px; height: 306px;margin:5px;">
						<div id='billingBlockStorageDatagrid'></div>
					</div>
			    </div>
			    <div style="overflow:hidden">
			    	<div style="width:678px; height: 306px;margin:5px;">
						<div id='billingIpDatagrid'></div>
					</div>
			    </div>
<!-- 			    <div style="overflow:hidden"> -->
<!-- 			    	<div style="width:678px; height: 306px;margin:5px;"> -->
<!-- 						<div id='billingObjStorageDatagrid'></div> -->
<!-- 					</div> -->
<!-- 			    </div> -->
		 	</div>
		 	<div style="width:100%;height:30px;text-align:right;"> 
		     	<input id="viewServiceDetailsCloseButton" style="margin-top:5px;" type="button" value="关闭" />
		    </div>
		</div>
	</div>
</body>
<script type="text/javascript">
	// 获取当前管理对象的服务实例值
	var billingServiceInstanceDetailData = null;
	// 初始化服务实例-index页面model
	var billing = new serviceBillingModel();
	billing.initServiceBillingInput();
	billing.initServiceBillingDatagrid();
	billing.initPopWindow();
	billing.searchServiceBilling();
	
	
</script>
</html>
