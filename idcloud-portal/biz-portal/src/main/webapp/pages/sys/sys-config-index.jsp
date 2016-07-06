<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=8" />
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/sys/sys-config-model.js"></script>
    </head>
  <body>
<div id='configTabs' style="border:0px;">
    <ul>
    </ul>
</div>
</body>
</html>
<script type="text/javascript">
	 $(function(){
		 var type;
		  // 初始化数据中心tabs选项卡
		  Core.AjaxRequest({
 			url : ws_url + "/rest/system/SYS_CONFIG_TYPE",
 			type:'get',
 			async:false,
 			callback : function (data) {
 				var lis = "";
 				var divs = "";
 				for (var i=0;i<data.length;i++) {  
			 		if(i==0){
			 			type = data[i].codeValue;
			 		}
			    	lis = lis + "<li id='"+data[i].codeValue+"'>"+data[i].codeDisplay+"</li>";
			    	divs = divs + "<div id='configTabsContent"+data[i].codeValue+"' name='contentDiv'></div>";
				}  
		    	$('#configTabs').children("ul").html(lis);
		    	$('#configTabs').append(divs);
 			}
 		 });
		 $('#configTabs').find('li:first').css("margin-left","30px");
		 $('#configTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});

		  $("#configTabsContent"+type).load(ctx+"/pages/sys/sys-config-tab.jsp",function(){
			 var sysConfig = new sysConfigModel();
			 // 初始化页面组件
			sysConfig.initInput();
			// 初始化弹出框
			sysConfig.initPopWindow();
			// 初始化组件验证规则
			sysConfig.initValidator();
			// 初始化datagrid
			sysConfig.initSystemdatagrid();
			 sysConfig.searchObj.configType = type;
		 	 // 为datagrid赋值
		     sysConfig.searchSystemInfo(); 
   	    });
	 });
	 
	 $('#configTabs').on('selected', function (event) {
	  	 var pageIndex = event.args.item;  
	  	 var typeid = $($('#configTabs').find('li')[pageIndex]).attr('id');
	  	 $("div[name='contentDiv']").html("");
	  	 $("#configTabsContent"+typeid).load(ctx+"/pages/sys/sys-config-tab.jsp",function(){
	  		var sysConfig = new sysConfigModel();
	  		 // 初始化页面组件
	  		sysConfig.initInput();
	  		// 初始化弹出框
	  		sysConfig.initPopWindow();
	  		// 初始化组件验证规则
	  		sysConfig.initValidator();
	  		// 初始化datagrid
	  		sysConfig.initSystemdatagrid();
	  		 sysConfig.searchObj.configType = typeid;
		 	 // 为datagrid赋值
		     sysConfig.searchSystemInfo(); 
   	    });
	 });
	
</script>
