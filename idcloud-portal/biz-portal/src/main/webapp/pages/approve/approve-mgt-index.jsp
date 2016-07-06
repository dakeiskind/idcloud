<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/approve/unapprove-info-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/approve/approved-info-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/approve/approve-bind-model.js"></script>
		
		<title></title>
		<style type="text/css">
			#containerApprove{
				width:100%;
				height:100%;
				margin:0px;
				padding:0px;
			}
			.specGrid-cell-red {
				/** color:red */
				background-color: red
			}
			.tableCss{
				border-right:1px solid #dadada;
				border-bottom:1px solid #dadada;
				border-spacing: 0;
				overflow: hidden;
				width:99.9%;
				height:100%;
				font-family: "微软雅黑", "Microsoft Yahei", Georgia, Serif;
				color: #787878;
			}
			.tableCss td{color: #000;border-left:1px solid #dadada;border-top:1px solid #dadada}
			.tableCss th{border-left:1px solid #dadada;border-top:1px solid #dadada}
		</style>
</head>
<body  class='default'>
   <div id="containerApprove">
   		<div id='jqxWidget'>
		        <div id='jqxTabs' style="border:0px;width:100%;">
		            <ul>
		                <li style="margin-left: 30px;"><i class='icons-blue icon-tasks'></i>待审核信息</li>
		                <li><i class='icons-blue icon-list'></i>已审核信息</li>
		            </ul>
		            <div>  
		               <%@ include file="unapprove-info-index.jsp"%>
		            </div>
		            <div>
		               <%@ include file="approved-info-index.jsp"%>
		            </div>
		        </div>
        </div>  
   </div>

</body>
<script type="text/javascript">
		$(function(){
			// 创建jqxTabs选项卡,计算tabs的高度
            $('#jqxTabs').jqxTabs({position: 'top',theme:"metro",selectionTracker:"checked"});

            // 初始化未审核js
			var unapprove = initUnapprovePageJs();
			// 初始化用户管理js
		    var approved =	initApprovedPageJs();
		    $("#jqxTabs li").each(function(index){
	           	 $(this).click(index, function(event){
	           		 if(event.data == 0) {
	           			unapprove.searchApproveInfo();
	           		 } else if(event.data == 1) {
	           			approved.searchApproveInfo();
	           		 }
	           	 });
           });

			var bindmodel = new approveBindModel(unapprove,approved);

			bindmodel.initReflushApproveList();
			//将model和view绑定
  			ko.applyBindings(bindmodel);
		});
</script>

</html>