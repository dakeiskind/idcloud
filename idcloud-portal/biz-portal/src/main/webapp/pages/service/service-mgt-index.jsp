<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/pages/common/taglibs.jsp"%>
	<%@ include file="/pages/common/resources.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
	<script type="text/javascript" src="${ctx}/js/service/service-mgt-model.js"></script>
	<script type="text/javascript" src="${ctx}/js/service/service-bind-model.js"></script>
	<style type="text/css">
		html,body{
			height:99.5%
		}
	</style>
</head>
<body>
	<div id="serviceDefine" style="width:99.5%;height:99.8%;margin-left:0.3%;margin-top:0.3%;min-width: 1000px;">
		<div id='jqxExpanderDefine' style="display: inline; float: left;width: 20%;height:100%;">
			<div>服务管理</div>
			<div style="overflow: hidden;">
				<div style="border: none;" id='jqxTreeDefine'></div>
			</div>
		</div>
		<div id='jqxTabsDefine'	style="display: inline; float: left; margin-left: 3px;">
			<ul>
				<li style="font-size: 14px">服务管理</li>
			</ul>
			<div>
				<input type="hidden" id="catalogSid" />
				<input type="hidden" id="catalogName" />
	
				<div style="width: 100%; padding: 10px 0px 10px 0px;">
					<p style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
						<span id="box">&nbsp;&nbsp;&nbsp;&nbsp;<font style="color: green;font-size: 12px;">※ 在此展示所选服务目录下的服务列表,并提供服务查看、发布、禁用等操作！</font></span>
					</p>
				</div>
				<div style="width: 100%;">
					<div id='jqxgridDefine' style="margin-left: 1%"></div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	// 初始化服务实例-index页面model
	var model = new serviceMgtModel();
	model.initServiceMgtTree();
	model.searchServiceMgtContent();
</script>
</html>
