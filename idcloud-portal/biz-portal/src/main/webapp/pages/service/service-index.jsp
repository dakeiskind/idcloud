<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/common/resources.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
<script type="text/javascript" src="${ctx}/js/service/service-catalog-model.js"></script>
<script type="text/javascript" src="${ctx}/js/service/service-mgt-model.js"></script>
<script type="text/javascript" src="${ctx}/js/service/service-instance-model.js"></script>
<script type="text/javascript" src="${ctx}/js/service/service-bind-model.js"></script>

<title></title>
	<style type="text/css">
		#containerServiceCatalog {
			width:100%;
			height:100%;
			margin:0px;
			padding:0px;
		}
	</style>
</head>
<body class='default'>
	<div id="containerServiceCatalog">
		<div id='jqxWidget'>
			<div id='jqxTabs' style="border: 0px;">
				<ul>
					<li style="margin-left: 30px;"><i class='icons-blue icon-folder-open'></i>服务目录</li>
					<li><i class='icons-blue icon-cog-1'></i>服务定义</li>
					<li><i class='icons-blue icon-soundcloud-1'></i>服务实例</li>
				</ul>
				<div>
					<%@ include file="service-catalog-index.jsp"%>
				</div>
				<div>
 					<%@ include file="service-mgt-index.jsp"%>
				</div>
				<div>
					<%@ include file="service-instance-index.jsp"%>
				</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
	$(function() {
		var high = $("#containerServiceCatalog").css("height");
		// 创建jqxTabs选项卡,计算tabs的高度
		$('#jqxTabs').jqxTabs({
			position : 'top',
			theme : "metro",
			height: high,
			width:"100%",
			selectionTracker : "checked"
		});

		// 初始化服务目录js
		var serviceCatalog = initServiceCatalogPageJs();
		// 初始化服务管理js
		var serviceMgt = initServiceMgtPageJs();
		// 初始化服务实例js
		var serviceInstance = initServiceInstancePageJs();
		
		var bindmodel = new serviceBindModel(serviceCatalog, serviceMgt, serviceInstance);

		//将model和view绑定
		ko.applyBindings(bindmodel);
	});
</script>

</html>