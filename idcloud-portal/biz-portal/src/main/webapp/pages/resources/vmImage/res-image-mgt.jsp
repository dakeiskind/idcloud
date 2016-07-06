<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vmImage/res-image-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vmImage/res-image-add-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vmImage/res-image-release-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vmImage/res-image-edit-model.js"></script>
	<style type="text/css">
		#jqxWidget{
				width:98%;
				height:98.5%;
				margin:0px;
				padding:0px;
				margin-left:0.5%;
		}
		
	</style>	
</head>
<body>
	<div style="margin:0px;padding:0px;height:8px;"></div>
    <div id='jqxWidget' style="border:0px;">
          <div id="tabswidget">
              <ul>
                  <li style="margin-left: 30px;">操作系统模板</li>
              </ul>
              <div id="imageMgtTabDiv">
<%--                 <%@ include file="/pages/resources/vmImage/image/res-virtual-image-mgt.jsp"%> --%>
              </div>
          </div>
    </div>
     <%@ include file="../vmImage/image/res-image-find-index.jsp"%>
     <%@ include file="../vmImage/image/res-image-release-model.jsp"%>
     <%@ include file="../vmImage/image/res-image-edit-index.jsp"%>
</body>



<script type="text/javascript">
	$(document).ready(function () { 
	   // $('#mainSplitter').jqxSplitter({ width: "100%", theme : currentTheme, height: "100%", panels: [{ size: 150,min:80}] });
	    $("#tabswidget").jqxTabs({  height: '100%', width: '100%', theme : currentTheme});
	    $("#imageMgtTabDiv").load(ctx+"/pages/resources/vmImage/image/res-virtual-image-mgt.jsp",
           	function(){	
	    		initResVirtualImage();
		});
	
	});	

</script>
</html>