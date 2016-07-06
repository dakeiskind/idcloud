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
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>

<%-- 		<script type="text/javascript" src="${ctx}/js/sys/tenant-mgt-model.js"></script> --%>
		
		<style type="text/css">
			
			#containerSys{
				width:100%;
				height:100%;
				margin:0px;
				padding:0px;
				overflow:hidden;
			}
			.sysContent{
				width:100%;
				height:100%;
				display:none;
			}
			#containerSys .show{
				display:block;
			}
			
			#sysMenu{
				width:100%;
				height:100%;
			}
			#sysMenu ul{
				width:100%;
				height:100%;
				margin:0px;
				padding:0px;
				list-style:none;
			}
			#sysMenu ul li{
				width:100%;
				height:30px;
				margin:0px;
				padding:0px;
				text-align:left;
				font-size:14px;
				line-height:30px;
				cursor:pointer;
			}
			
			#sysMenu .selected{
				background:#1FAEFF;
				color:#fff;
			}
		</style>
</head>

<body>
   <div id="containerSys">
 		<div id="mainSplitter" style="border:0px;">
            <div class="splitter-panel">
                 <div id="sysMenu">
                 	   <ul id="sysLi">
                 	   	
                 	   </ul>
                 </div>
            </div>
            <div id="sysDiv" class="splitter-panel">
               
            </div>
        </div>
   </div>
<!-- 		               <div class="sysContent"> -->
<%-- 		               		 <iframe src="${ctx}/pages/sys/tenant-mgt-index.jsp" style="width:99.8%;height:99.8%;background:#fff;margin:0px;padding:0px;overflow:hidden;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe> --%>
<!-- 		               </div> -->
</body>
<script type="text/javascript">
	   
		$(function(){
			var moduleSid = '<%=moduleSid%>';
	//		alert(moduleSid);
			// 查询出该权限下，是否存在子权限
			Core.AjaxRequest({ 
				url : ws_url + "/rest/user/platform/findMoudules/" + currentUser.userSid,
				type : "GET",
				async: false,
				callback : function (data) {
					var li="";
					var div="";
					var index = 0;
					for(var i=0;i<data.length;i++){
						if(data[i].parentSid == moduleSid){
							if(index == 0){
								li += '<li class="selected" url='+data[i].moduleUrl+'>&nbsp;&nbsp;&nbsp;&nbsp;<i class='+data[i].moduleIconUrl+'></i>&nbsp;&nbsp;'+data[i].moduleName+'</li>';
								div += '<div class="sysContent show"></div>';
							}else{
								li += '<li url='+data[i].moduleUrl+'>&nbsp;&nbsp;&nbsp;&nbsp;<i class='+data[i].moduleIconUrl+'></i>&nbsp;&nbsp;'+data[i].moduleName+'</li> ';
								div += '<div class="sysContent"></div>';
							}
							index++;
						}
					}
					$("#sysLi").html(li);
					$("#sysDiv").html(div);
				} 
			});
			
			// 创建jqxTabs选项卡,计算tabs的高度
            $('#mainSplitter').jqxSplitter({width:"100%",height:"100%",theme:currentTheme, panels: [{ size: 200 ,max:200 ,min:100}] });
			var firstUrl = $("#sysLi .selected").attr("url");
			$(".sysContent.show").html('<iframe src="${ctx}'+firstUrl+'" style="width:99.8%;height:99.8%;background:#fff;margin:0px;padding:0px;overflow:hidden;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>');
			$("#sysMenu ul li").each(function (index){
				$(this).click(function(){
					$(".selected").removeClass("selected");
					$(this).addClass("selected");
					$(".show").removeClass("show");
					$(".sysContent").eq(index).addClass("show");
					
					var url = $(this).attr("url");
					
					$(".sysContent").eq(index).html('<iframe src="${ctx}'+url+'" style="width:99.8%;height:99.8%;background:#fff;margin:0px;padding:0px;overflow:hidden;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>');

				});
			});
			
		});
		
</script>

</html>