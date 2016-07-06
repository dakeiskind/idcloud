<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
	String moduleSid = request.getParameter("moduleSid");
%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<style type="text/css">
			#containerApprove{
				width:100%;
				height:100%;
				margin:0px;
				padding:0px;
			}
		</style>
</head>
<body>
   <div id="containerApprove">
   		<%@ include file="/pages/common/level3CommonPage.jsp"%>
   </div>
</body>
<script type="text/javascript">
		$(function(){
			var moduleSid = '<%=moduleSid%>';
			// 初始化3级菜单
			initLevel3CommonPage(moduleSid);
		});
</script>

</html>