<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/issue/issue-bind-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/issue/issue-console-model.js"></script>
</head>
<body  class='default'>
	<div>
		<%@ include file="issue-console-index.jsp"%>
	</div>
</body>
<script type="text/javascript">
		$(function(){
 			var conso = initIssueConsolePageJs();

 			var bindmodel = new issueBindModel(conso);
 			//将model和view绑定
  			ko.applyBindings(bindmodel);
		});
</script>

</html>