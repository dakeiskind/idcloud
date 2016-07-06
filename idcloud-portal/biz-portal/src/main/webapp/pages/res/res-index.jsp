<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>

		<style type="text/css">
			#containerRespool{
				width:99.95%;
				height:99.9%;
				margin:0px;
				padding:0px;
			}
		</style>   
</head>
<body class='default'>
   <div id="containerRespool">
   		  <div id='jqxWidget'>
		         <div id='jqxTabs' style="border:0px;">
		            <ul>
		                <li style="margin-left: 30px;"><i class='icons-blue icon-mobile-5'></i>主机资源管理</li>
		                <li><i class='icons-blue icon-sitemap'></i>网络资源管理</li>  
		                <li><i class='icons-blue icon-database'></i>存储资源管理</li>
		                <li><i class='icons-blue icon-data-science-inv'></i>Exchange&SharePoint</li>
		            </ul>
		            <div id="tabContent0" style="height:100%;margin:0px;padding:0px;overflow:hidden;">  
 		                <iframe src="${ctx}/pages/res/host/res-host-mgt.jsp" style="width:100%;height:100%;background:#fff;margin:0px;padding:0px;overflow-y:auto" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>
		            </div>
		            <div id="tabContent1" style="margin:0px;padding:0px;overflow:hidden;">
 		               
		            </div>
		            <div id="tabContent2" style="margin:0px;padding:0px;overflow:hidden;">
 		               
		            </div>
		            <div id="tabContent3" style="margin:0px;padding:0px;overflow:hidden;">
 		               
		            </div>
		        </div>
         </div>
   </div>
</body>
<script type="text/javascript">
		$(function(){
 			var high = $("#containerRespool").css("height");
			// 创建jqxTabs选项卡,计算tabs的高度
            $('#jqxTabs').jqxTabs({position: 'top',width:"100%",height:high,theme:"metro",selectionTracker:"checked"});

            // 当选项卡选择的时候
            $('#jqxTabs').on('selected', function (event) {
                var pageIndex = event.args.item;
                if(pageIndex == 0){
                	$("#tabContent0").html('<iframe src="${ctx}/pages/res/host/res-host-mgt.jsp" style="width:100%;height:100%;background:#fff;margin:0px;padding:0px;overflow-y:auto" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>');
                }else if(pageIndex == 1){
                	$("#tabContent1").html('<iframe src="${ctx}/pages/res/network/res-network-mgt.jsp" style="width:100%;height:100%;background:#fff;margin:0px;padding:0px;overflow-y:auto" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>');
                }else if(pageIndex == 2){
                	$("#tabContent2").html('<iframe src="${ctx}/pages/res/storage/res-storage-mgt.jsp" style="width:100%;height:100%;background:#fff;margin:0px;padding:0px;overflow-y:auto" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>');
                }else if(pageIndex == 3){
                	$("#tabContent3").html('<iframe src="${ctx}/pages/res/sharepointExchange/res-spex-mgt.jsp" style="width:100%;height:100%;background:#fff;margin:0px;padding:0px;overflow-y:auto" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>');
                }
            });
		});
		
		// 当window大小发生改变的时候
		window.onresize = function(){
			var high = $("#containerRespool").css("height");
			// 创建jqxTabs选项卡,计算tabs的高度
            $('#jqxTabs').jqxTabs({height:high});
		};

</script>

</html>