<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/billing/billing-plan-model.js"></script>
		
		<title></title>
		<style type="text/css">
			#containerSys{
				width:100%;
				height:100%;
				margin:0px;
				padding:0px;
			}
			.datagridLink{
				color:#0099d7;  
			}
			.datagridLink:hover{
				color:#fff;  
			}
		</style>
</head>
<body  class='default'>
   <div id="containerSys">
   	<%@ include file="billing-plan-tab.jsp"%>
<!--    		<div id='jqxWidget'> -->
<!-- 		        <div id='jqxTabs' style="border:0px;width:100%;"> -->
<!-- 		            <ul> -->
<!-- 		                <li style="margin-left: 30px;"><i class='icons-blue icon-cog-1'></i>资费计划</li> -->
<!-- 		                <li><i class='icons-blue icon-calculator'></i>账单管理</li>   -->
<!-- 		            </ul> -->
<!-- 		            <div>   -->
<%-- 		               <%@ include file="billing-plan-tab.jsp"%> --%>
<!-- 		            </div> -->
<!-- 		            <div> -->
<%-- 		               <%@ include file="billing-mgt-tab.jsp"%> --%>
<!-- 		            </div> -->
<!-- 		        </div> -->
<!--         </div>   -->
   </div>

</body>
<script type="text/javascript">
// 		$(function(){
// 			// 创建jqxTabs选项卡,计算tabs的高度
//             $('#jqxTabs').jqxTabs({position: 'top',theme:"metro",selectionTracker:"checked"});
			
//             // 初始化资费计划js
// 			var billingPlanModel = initBillingPlanPageJs();
// // 			// 初始化资费管理js
// // 		    var billingMgtModel =	initBillingMgtPageJs();
			
// 			var bindmodel = new sysBindModel(billingMgtModel,billingPlanModel);
// 			//将model和view绑定
//   			ko.applyBindings(bindmodel);
// 		});
</script>

</html>