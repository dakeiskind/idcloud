<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<style type="text/css">			
			#comContentDiv .show{
				display:block;
			}
			.comContent{
				width:100%;
				height:100%;
				display:none;
			}
			
			#comMenu{
				width:100%;
				height:100%;
			}
			#comMenu ul{
				width:100%;
				height:100%;
				margin:0px;
				padding:0px;
				list-style:none;
			}
			#comMenu ul li{
				width:100%;
				height:30px;
				margin:0px;
				padding:0px;
				text-align:left;
				font-size:14px;
				line-height:30px;
				cursor:pointer;
			}
			
			#comMenu .selected{
				background:#1FAEFF;
				color:#fff;
			}
		</style>
		<div id="comSplitter" style="border:0px;">
		    <div class="splitter-panel">
		         <div id="comMenu">
		         	   <ul id="commonLi">
		         	   	
		         	   </ul>
		         </div>
		    </div>
		    <div id="comContentDiv" class="splitter-panel">
		       
		    </div>
		</div>

<script>
   // 初始化
   function initLevel3CommonPage(moduleSid){
	 	//查询出该权限下，是否存在子权限
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
							div += '<div class="comContent show"></div>';
						}else{
							li += '<li url='+data[i].moduleUrl+'>&nbsp;&nbsp;&nbsp;&nbsp;<i class='+data[i].moduleIconUrl+'></i>&nbsp;&nbsp;'+data[i].moduleName+'</li> ';
							div += '<div class="comContent"></div>';
						}
						index++;
					}
				}
				$("#commonLi").html(li);
				$("#comContentDiv").html(div);
			} 
		});
		
		// 创建jqxTabs选项卡,计算tabs的高度
		$('#comSplitter').jqxSplitter({width:"100%",height:"100%",theme:currentTheme, panels: [{ size: 200 ,max:200 ,min:100}] });
		var firstUrl = $("#commonLi .selected").attr("url");
		$(".comContent.show").html('<iframe src="${ctx}'+firstUrl+'" style="width:99.8%;height:99.8%;background:#fff;margin:0px;padding:0px;overflow:hidden;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>');
		$("#comMenu ul li").each(function (index){
			$(this).click(function(){
				$(".selected").removeClass("selected");
				$(this).addClass("selected");
				$(".show").removeClass("show");
				$(".comContent").eq(index).addClass("show");
				
				var url = $(this).attr("url");
				
				$(".comContent").eq(index).html('<iframe src="${ctx}'+url+'" style="width:99.8%;height:99.8%;background:#fff;margin:0px;padding:0px;overflow:hidden;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>');
		
			});
		});
   }
</script>