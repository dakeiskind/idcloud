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
<script type="text/javascript" src="${ctx}/js/orgmgt/orgmgt-res-model.js"></script>
<script type="text/javascript" src="${ctx}/js/orgmgt/orgmgt-bind-model.js"></script>

<title></title>
	<style type="text/css">
		#containerOrgmgt {
			width:100%;
			height:100%;
			margin:0px;
			padding:0px;
		}
	</style>
</head>
<body class='default'>
	<div id="containerOrgmgt">
		<div id='jqxWidget'>
			<div id='jqxTabs' style="border: 0px;">
				<ul>
					<li style="margin-left: 30px;"><i class='icons-blue icon-folder-open'></i>组织管理</li>
				</ul>
				<div>
					<%@ include file="orgmgt-res-index.jsp"%>
				</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
	$(function() {
		var high = $("#containerOrgmgt").css("height");
		// 创建jqxTabs选项卡,计算tabs的高度
		$('#jqxTabs').jqxTabs({
			position : 'top',
			theme : "metro",
			height: high,
			width:"100%",
			selectionTracker : "checked"
		});

		// 初始化服务目录js
		var orgRes = initOrgresPageJs();
		
		var bindmodel = new orgBindModel(orgRes);

		//将model和view绑定
		ko.applyBindings(bindmodel);
	});
</script>

</html>