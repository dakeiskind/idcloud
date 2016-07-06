<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/pages/common/taglibs.jsp"%>
	<%@ include file="/pages/common/resources.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
	<script type="text/javascript" src="${ctx}/js/service/service-instance-model.js"></script>
	
	<style type="text/css">
		#testForm {
			width: 100%;
		}
		#testForm .panel {
			width: 98%;
			border-bottom: 1px solid #DADADA;
			border-left: 1px solid #DADADA;
			border-right: 1px solid #DADADA;
			border-top: 1px solid #DADADA;
			box-shadow: #DADADA 0px 3px 3px;
			position: relative;
			padding: 1%;
		}
		#testForm .viewTable {
			width: 100%;
		}
		#testForm .viewTable .leftTd {
			text-align: right;
		}
	</style>
</head>
<body>
	<div style="width: 100%; height: 55px; padding: 10px 0px 10px 0px;">
		<table border="0" height="100%" cellspacing="0" cellpadding="2">
			<tr>
				<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;订单号：</td>
				<td><input type="text" id="orderIdLike" />&nbsp;&nbsp;</td>
				<td align="right">所属服务：</td>
				<td>
					<div id="search-serviceSid"></div>
				</td>
				<td align="right">&nbsp;&nbsp;开通时间：</td>
				<td>
					<div id="dredgeFromDate"></div>
				</td>
				<td align="right">&nbsp;&nbsp;至：</td>
				<td>
					<div id="dredgeToDate"></div>
				</td>
			</tr>
			<tr>
				<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;实例名：</td>
				<td><input type="text" id="instanceNameLike" />&nbsp;&nbsp;</td>
				<td align="right">实例状态：</td>
				<td>
					<div id="status"></div>
				</td>
				<td align="right">&nbsp;&nbsp;到期时间：</td>
				<td>
					<div id="expiringFromDate"></div>
				</td>
				<td align="right">&nbsp;&nbsp;至：</td>
				<td>
					<div id="expiringToDate"></div>
				</td>
				<td><a onclick="searchServiceInstance()" id="search-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
			</tr>
		</table>
	</div>
	 
	<div style="width: 100%; height: auto;">
		<div id='jqxgrid' style="margin-left: 1%;"></div>
	</div>
	
	<div id="popupWindow">
		<div>服务实例</div>
	
		<div id="testForm" style="overflow: hidden;">
	
			<div class="panel" style="margin-bottom: 10px">
				<p
					style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
					<span id="box">基本信息</span>
				</p>
				<hr />
				<input type="hidden" data-name="instanceSid" id="userSid" />
				<table class="viewTable" width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">订单号：</td>
						<td id="orderId"></td>
						<td align="right">实例名称：</td>
						<td id="instanceName"></td>
						<td align="right">所属服务：</td>
						<td id="serviceName"></td>
					</tr>
					<tr>
						<td align="right">开通时间：</td>
						<td id="dredgeDate"></td>
						<td align="right">到期时间：</td>
						<td id="expiringDate"></td>
						<td align="right">所属租户：</td>
						<td id="tenantName"></td>
					</tr>
				</table>
			</div>
			<div style="margin: 0px; padding: 0px">
				<div class="panel"
					style="margin-bottom: 10px; margin-right: 10px; width: 47%; float: left; display: inline; height: 100px;">
					<p
						style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
						<span id="box">配置信息</span>
					</p>
					<hr />
					<table class="viewTable" width="100%" cellpadding="0"
						cellspacing="0">
						<tr>
							<td align="right" width="70px">操作系统：</td>
							<td id="osTypeName" width="200px"></td>
						</tr>
						<tr>
							<td align="right" width="70px">CPU核数：</td>
							<td id="cpuCores" width="200px"></td>
						</tr>
						<tr>
							<td align="right" width="70px">内存大小：</td>
							<td id="memorySize" width="200px"></td>
						</tr>
					</table>
				</div>
				<div class="panel"
					style="margin-bottom: 10px; width: 47%; float: left; display: inline; height: 100px;">
					<p
						style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
						<span id="box">存储信息</span>
					</p>
					<hr />
					<div id="vDiskGrid"
						style="height: 60%; width: 100%; margin-top: 5px; font-size: 14px;"></div>
				</div>
			</div>
			<div id="vmDivDetail" class="panel"
				style="margin-bottom: 10px; clear: both; height: 150px;">
				<p
					style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
					<span id="box">资源实例列表</span>
				</p>
				<hr />
				<div id="vResourceGrid"
					style="height: 60%; width: 100%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
			</div>
	
			<input id="cancel" style="float: right;" type="button" value="关闭" />
		</div>
	</div>
</body>
<script type="text/javascript">
	// 初始化服务实例-index页面model
	var model = new serviceInstanceModel();
	model.initServiceInstanceInput();
	model.initServiceInstanceDatagrid();
	model.searchServiceInstance();
	model.initServiceInstancePopWindow();
</script>
</html>
