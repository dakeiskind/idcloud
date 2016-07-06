<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>

		<style type="text/css">
			#containerResource{
				width:99.95%;
				height:99.9%;
				margin:0px;
				padding:0px;
			}
			
			#resMenu{
				width:100%;
				height:100%;
				margin:0px;
				padding:0px;
				list-style:none;
			}
			
			#resMenu li{
				width:100%;
				height:30px;
				margin:0px;
				padding:0px;
				text-align:left;
				font-size:14px;
				line-height:30px;
				cursor:pointer;
			}
			
			#resMenu .selected{
				background:#1FAEFF;
				color:#fff;
			}
			
		</style>
</head>
<body class='default'>
   <div id="containerResource">
   		  <div id='jqxWidget'>
   		  		<div id="mainSplitter" style="border:0px;">
		            <div class="splitter-panel">
		            	 <ul id="resMenu">
		            	 	 <li class="selected">&nbsp;&nbsp;&nbsp;<i class='icon-shareable'></i>&nbsp;资源管理</li>
	                 	   	 <li>&nbsp;&nbsp;&nbsp;<i class='icon-easel'></i>&nbsp;资源池管理</li>
	                 	   	 <li>&nbsp;&nbsp;&nbsp;<i class='icon-docs'></i>&nbsp;模板管理</li>
	                 	   	 <li>&nbsp;&nbsp;&nbsp;<i class='icon-megaphone'></i>&nbsp;业务视图</li>
	                 	   	 <li>&nbsp;&nbsp;&nbsp;<i class='icon-megaphone'></i>&nbsp;设备管理</li>
	                 	   	 <li>&nbsp;&nbsp;&nbsp;<i class='icon-tasks'></i>&nbsp;闲置资源管理</li>
	                 	   	 <li>&nbsp;&nbsp;&nbsp;<i class='icon-loop-outline'></i>&nbsp;资源回收管理</li>
		            	 </ul>
		            </div>
		            <div class="splitter-panel">
		            	<div style="height:100%;margin:0px;padding:0px;overflow:hidden;">
			            	<iframe id="resIframeDiv" src="${ctx}/pages/resources/virtual/res-virtual-mgt.jsp" style="width:100%;height:100%;background:#fff;margin:0px;padding:0px;overflow-y:auto" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>  
			            </div>
		            </div>
	            </div>
         </div>
   </div>
</body>
<script type="text/javascript">
		var high = $("#containerResource").css("height");
		// 创建jqxSplitter分页器,计算页面容器的高度
		$('#mainSplitter').jqxSplitter({ width: "100%", height: high, theme:currentTheme, panels: [{ size: 200 ,max:200 ,min:100}] });
		// 当window大小发生改变的时候
		window.onresize = function(){
			var high = $("#containerResource").css("height");
			// 创建jqxTabs选项卡,计算tabs的高度
		    $('#mainSplitter').jqxSplitter({height:high});
		};
		
		$(function(){
            // 当选择左边二级目录时
            $("#resMenu li").each(function (index){
				$(this).click(function(){
					$(".selected").removeClass("selected");
					$(this).addClass("selected");
					if(0 == index){
						$("#resIframeDiv").attr("src", ctx + "/pages/resources/virtual/res-virtual-mgt.jsp");
					}else if(1 == index){
				    	$("#resIframeDiv").attr("src", ctx + "/pages/resources/pool/res-pool-mgt.jsp");
					}else if(2 == index){
						$("#resIframeDiv").attr("src", ctx + "/pages/resources/vmImage/res-image-mgt.jsp");
					}else if(3 == index){
						$("#resIframeDiv").attr("src", ctx + "/pages/resources/biz/res-biz-mgt.jsp");
					}else if(4 == index){
						$("#resIframeDiv").attr("src", tkUrl + "/device/devindex.aspx" + tkParam);
					}else if(5 == index){
						$("#resIframeDiv").attr("src", ctx + "/pages/resources/reports/res-fres-mgt.jsp");
					}else if(6 == index){
						$("#resIframeDiv").attr("src", ctx + "/pages/resources/reports/res-recovery-mgt.jsp");
					}
				});
			});
		});

</script>

</html>