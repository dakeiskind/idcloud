<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resource/resource-bind-one-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resource/resource-bind-two-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resource/host-config-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resource/Ip-config-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resource/storage-config-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resource/vlan-config-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resource/exchange-config-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resource/sharepoint-config-model.js"></script>
		
		<title></title>
		<style type="text/css">
			#containerApprove{
				width:100%;
				height:100%;
				margin:0px;
				padding:0px;
			}
		</style>
</head>
<body  class='default'>
   <div id="containerApprove">
   		<div id='jqxWidget'>
		        <div id='jqxTabs' style="border:0px;width:100%;">
		            <ul>
		                <li style="margin-left: 30px;">主机配置管理</li>
		                <li>块存储配置管理</li>
		                <li>IP配置管理</li>
		                <li>VLAN配置管理</li>
		                <li>Exchange配置管理</li>
		                <li>SharePoint配置管理</li>
		            </ul>
		            <div>  
		            	<%@ include file="host-config-mgt-index.jsp"%>
		            </div>
		            <div>
 						<%@ include file="storage-config-mgt-index.jsp"%>
		            </div>
		            <div>
 		               <%@ include file="Ip-config-mgt-index.jsp"%>
		            </div>
		            <div>
		               <%@ include file="vlan-config-mgt-index.jsp"%>
		            </div>
        		    <div>
        		       <%@ include file="exchange-config-mgt-index.jsp"%>
		            </div>
		        	<div>
		        		 <%@ include file="sharepoint-config-mgt-index.jsp"%>
		            </div>
		        </div>
        </div>  
   </div>

</body>
<script type="text/javascript">
		$(function(){
			// 创建jqxTabs选项卡,计算tabs的高度
            $('#jqxTabs').jqxTabs({position: 'top',theme:currentTheme,selectionTracker:"checked"});
			
            // 初始化ip地址js
 			var host = initHostConfigJs();
             // 初始化ip地址js
 			var ip = initIPConfigJs();
 			// 初始化块存储js
 		    var storage = initStorageConfigJs();
 		    // 初始化vlan地址js
 			var vlan = initVlanConfigJs();
 		    // 初始化exchange
 		    var exchange = initExchangeConfigJs();
 		   // 初始化sharepoint
 		    var sharepoint = initSharepointConfigJs();

 			var bindOnemodel = new resourceBindOneModel(host,storage,ip);
 			
 			  
 			var bindTwomodel = new resourceBindTwoModel(vlan,exchange,sharepoint);
 			
 			var bindmodel = $.extend(bindOnemodel,bindTwomodel);
 			//将model和view绑定
   			ko.applyBindings(bindmodel);
		});
</script>

</html>