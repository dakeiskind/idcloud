<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/message/msg-bind-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/message/msg-inbox-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/message/msg-outbox-model.js"></script>
		<title></title>
		<style type="text/css">
			#containerMsg{
				width:100%;
				height:100%;
				margin:0px;
				padding:0px;
			}
		</style>
	</head>
	<body class='default'>
		<div id="containerMsg">
			<div id='jqxWidget'>
				<div id='jqxTabs' style="border:0px;width:100%;">
					<ul>
						<li style="margin-left: 30px;"><i class='icons-blue icon-incon-3'></i>收件箱</li>
						<li><i class='icons-blue icon-inbox'></i>发件箱</li>
					</ul>
					<div>
						<%@ include file="msg-inbox-index.jsp"%>
					</div>
					<div>
						<%@ include file="msg-outbox-index.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function(){
			// 创建jqxTabs选项卡,计算tabs的高度
			$('#jqxTabs').jqxTabs({position: 'top',theme:"metro",selectionTracker:"checked"});

			//初始化收件箱js
			var inBox = initInBoxPageJs();

			//初始化发件箱js
			var outBox = initOutBoxPageJs();

			var bindmodel = new msgBindModel(inBox , outBox);

			//将model和view绑定
			ko.applyBindings(bindmodel);
		});
	</script>
</html>