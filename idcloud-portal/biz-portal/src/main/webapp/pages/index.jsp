<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/index-model.js"></script>
<%-- 		<script src="${ctx}/scripts/highcharts/highcharts.js"></script> --%>
<%-- 		<script src="${ctx}/scripts/highcharts/modules/exporting.js"></script> --%>
		<link rel="stylesheet" href="${ctx}/css/index.css" type="text/css" />
		
		<title>阅联云管理平台</title>

</head>
<body>
    <%@ include file="/pages/common/header.jsp"%>
	<div id="container"> 
		<div id="content_list" class="content_list">
			 <div class="title">
			 	 <div class="tabs" style="">管理菜单</div>   
			 </div>     
			 <div id="list_nav" class="list_nav"></div>
		</div>
		<div id="content_details" class="content_details">
			<div class="title">
				<div style="width:40px;height:40px;text-align:right;border-right:1px solid #eee;border-left:1px solid #eee;">  
					<i id="switchPanel" isclicked="false" class="icons-32-black icon-menu"></i>&nbsp;  
				</div>
				<div id="moudel_name" style="position:absolute;top:0px;left:45px;width:151px;height:40px;line-height:40px;text-align:left;">
					平台首页
				</div>
				<div style="position:absolute;top:0px;right:10px;width:151px;height:39px;line-height:39px;text-align:right;">
<!-- 					<i class="icons-24-blue icon-cog"></i>&nbsp;&nbsp;  
					<i class="icons-24-blue icon-floppy-1"></i>&nbsp;&nbsp; -->
					<i class="icons-24-blue icon-angle-double-up" style="cursor: pointer;" direction="up" onclick="headControl(this)"></i>   
					<i class="icons-24-blue icon-angle-double-down" style="display:none;cursor: pointer;" direction="down" onclick="headControl(this)"></i>  
				</div>  
			</div>      
			<div id="content_nav" class="content_nav">        
				<iframe src="${ctx}/pages/platform-index.jsp" id="iframediv" style="width:100%;height:100%;background:#fff;margin:0px;padding:0px;overflow-y:auto" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe> 
			</div>
		</div>
	</div>  
</body>
<script type="text/javascript">
	//	$("#iframediv").load(ctx+"/pages/platform-index.jsp");  
		/* 设置整个页面的布局*/
	    var bodyh = document.documentElement.clientHeight;
		document.getElementById("container").style.height = (parseInt(bodyh) - 61) + 'px';
		var containerW = $("#container").css("width");
		var containerH = $("#container").css("height");
		// 计算出右边的div的大小   
		$("#content_details").css("width",""+(parseInt(containerW) - 200)+"px");
		// 设置左边和右边容器的高度
		document.getElementById("content_nav").style.height = (parseInt(containerH) - 41) + 'px';    
		
 		// 当浏览器页面大小发生改变时
		window.onresize = function(){
			  // 设置container容器的大小
			  var bodyh = document.documentElement.clientHeight;
			  if($("#header").css("top") == "-61px"){
				  document.getElementById("container").style.height = (parseInt(bodyh)) + 'px';      
			  }else{
				  document.getElementById("container").style.height = (parseInt(bodyh) - 61) + 'px';   
			  }
			  var containerW = $("#container").css("width");
			  var containerH = $("#container").css("height");  
			  if($("#switchPanel").attr("isclicked") == "true"){
			  }else{
				  $("#content_details").css("width",""+(parseInt(containerW) - 200)+"px");
			  }
			  
			  // 设置左边和右边容器的高度
			  document.getElementById("content_nav").style.height = (parseInt(containerH) - 41) + 'px';
	    };
	      
	
	    $(function(){
	    	// 用户未登陆跳转至登陆
			if(typeof currentUser == "undefined" || null == currentUser) {
				window.location.href = ctx + "/pages/login.jsp";
			} 
	    	// 用户名设置
	    	$("#userName").html(currentUser.realName);
	    	
			// 功能和内容相互切换
			$("#switchPanel").click(function(){
				if($(this).attr("isclicked") == "false") {
					$(this).attr("isclicked","true");
					// nav-list向左边移动相同的宽度,右边的content-details的宽度新增相同的宽度，并向左边移动相同的距离
					$("#content_list").animate({left:'-=200'},'800');
					$("#content_details").animate({left:'-=200'},'800',function(){
						$(this).css("width","+=200");
					});
				} else {
	 				// 得到当前nav-list的宽度
	 				$(this).attr("isclicked","false");
	 				// nav-list向左边移动相同的宽度,右边的content-details的宽度新增相同的宽度，并向左边移动相同的距离
	 				$("#content_list").animate({left:'+=200'},'800');
	 				$("#content_details").animate({left:'+=200'},'800',function(){
	 					$(this).css("width","-=200");
	 				});
				}
			});
			
			// 获取服务目录
			Core.AjaxRequest({
 				url : ws_url + "/rest/user/platform/findMoudules/" + currentUser.userSid,
 				type : "GET",
 				callback : function (data) {
 					createNavList(data);
 				} 
 			});
			// 初始化系统配置js
			var index = initIndexConfigPageJs();

			var bindmodel = new indexBindModel(index);
			// 将model和view绑定
			ko.applyBindings(bindmodel);
		});

	/** 创建菜单 */
	function createNavList(data) {
		var str = '<ul>';
		for ( var i = 0; i < data.length; i++) {
			if(i == 0){
				str += '<li class="navList listSelected" onclick="javascript: changeLoad(this,' + "'"
				+ data[i].moduleUrl + "'" + ",'" + data[i].moduleName + "'"
				+ ');">';
				str += '<i class="' + data[i].moduleIconUrl + '"></i>';
				str += '<font style="font-size:15px;margin-left:15px;">'
				+ data[i].moduleName + '</font></li>';
			}else{
				str += '<li class="navList" onclick="javascript: changeLoad(this,' + "'"
				+ data[i].moduleUrl + "'" + ",'" + data[i].moduleName + "'"
				+ ');">';
				str += '<i class="' + data[i].moduleIconUrl + '"></i>';
				str += '<font style="font-size:15px;margin-left:15px;">'
				+ data[i].moduleName + '</font></li>';
			}
		}
		str += '</ul>';
		$("#list_nav").html(str);
	}

	/** 加载菜单切换 */
	function changeLoad(obj,url, name,index) {
		
        if(index == null){
        	$("#list_nav .listSelected").removeClass("listSelected");
    		$(obj).addClass("listSelected");
        }else{
        	$("#list_nav .listSelected").removeClass("listSelected");
        	$("#list_nav .navList").eq(index).addClass("listSelected");
        }
		$("#moudel_name").html(name);
		$("#iframediv").attr("src", ctx + url);
	}

	// 页面头部控制
	function headControl(obj) {
		if ($(obj).attr("direction") == "up") {
			$("#header").animate({
				top : '-=61'
			}, '500');
			$("#container")
					.animate(
							{
								top : '-=61'
							},
							'500',
							function() {
								$(this).css("height", "+=61");
								var containerH = $("#container").css("height");
								// 设置左边和右边容器的高度
								document.getElementById("content_nav").style.height = (parseInt(containerH) - 41)
										+ 'px';
								$(".icon-angle-double-up").hide();
								$(".icon-angle-double-down").show();
							});
		} else if ($(obj).attr("direction") == "down") {
			$("#header").animate({
				top : '+=61'
			}, '500');
			$("#container")
					.animate(
							{
								top : '+=61'
							},
							'500',
							function() {
								$(this).css("height", "-=61");
								var containerH = $("#container").css("height");
								// 设置左边和右边容器的高度
								document.getElementById("content_nav").style.height = (parseInt(containerH) - 41)
										+ 'px';
								$(".icon-angle-double-up").show();
								$(".icon-angle-double-down").hide();
							});
		}
	}

	function initIndexConfigPageJs() {
		var indexConfig = new indexConfigModel();
		// 初始化页面组件
		indexConfig.initInput();
		// 初始化弹出框
		indexConfig.initPopWindow();
		// 初始化组件验证规则
		indexConfig.initValidator();

		return indexConfig;
	}
</script>
</html>