<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/idc/idcOrder-bind-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/idc/idcOrder-mgt-model.js"></script>
		
		<title></title>
		<style type="text/css">
			#containerIdcOrder{
				width:100%;
				height:100%;
				margin:0px;
				padding:0px;
			}
		</style>
</head>
<body  class='default'>
   <div id="containerIdcOrder">
   		<div id='jqxWidget'>
		        <div id='jqxTabs' style="border:0px;width:100%;">
<!-- 		            <ul> -->
<!-- 		                <li style="margin-left: 30px;"><i class='icons-blue icon-monitor'></i>IDC订单管理</li> -->
<!-- 		            </ul> -->
		            <div>  
 		               <%@ include file="idcOrder-info-index.jsp"%>
		            </div>
		        </div>
        </div>  
   </div>

</body>
<script type="text/javascript">
		$(function(){
            // 初始化问题控制台js
 			var idcOrder = initIdcOrderPageJs();
 			
 			var bindmodel = new idcOrderBindModel(idcOrder);
 			//将model和view绑定
  			ko.applyBindings(bindmodel);
		});
</script>

</html>