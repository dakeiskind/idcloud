<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/index-model.js"></script>
		<link rel="stylesheet" href="${ctx}/css/index.css" type="text/css" />
		<title id="mainTitle"></title>
</head>
<body>
    <%@ include file="/pages/common/header.jsp"%>
	<div id="container"> 

	</div>  
</body>
<script type="text/javascript">
		document.title = Core.getSysconfig("system.name");
		/* 设置整个页面的布局*/
	    var bodyh = document.documentElement.clientHeight;
		document.getElementById("container").style.height = (parseInt(bodyh) - 61) + 'px';    
// 		document.getElementById("container").style.height = (parseInt(bodyh)) + 'px';    
		
 		// 当浏览器页面大小发生改变时
		window.onresize = function(){
			  // 设置container容器的大小
			  var bodyh = document.documentElement.clientHeight;
			  document.getElementById("container").style.height = (parseInt(bodyh) - 61) + 'px';
// 			  document.getElementById("container").style.height = (parseInt(bodyh)) + 'px';
	    };
	      
		/** 页面加载完成之后，初始化js */ 
	    $(function(){
	    	// 用户未登陆跳转至登陆
			if(typeof currentUser == "undefined" || null == currentUser) {
				window.location.href = ctx + "/pages/login.jsp";
			} 
	    	
	    	// 加载首页项
			loadFirstPage(firstTabsItem);
	    	
	    	// 用户名设置
	    	$("#userName").html(currentUser.realName);
			// 初始化系统配置js
			var index = initIndexConfigPageJs();
			var bindmodel = new indexBindModel(index);
			// 将model和view绑定
			ko.applyBindings(bindmodel);
		});
		
		
	
		// 首页用户的基本操作
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
		
		// 子页面操作父页面，整体页面上下移动
		function operatPage(type){
			if(type == "up"){
				$("#header").animate({top : '-=61'}, 'fast');
// 				$("#header").animate({height : '-=61'}, 'fast');
				$("#container").animate({top : '-=61'},'fast',function() {
					$(this).css("height", "+=61");
				});
			} else if(type == "down") {
				$("#header").animate({top : '+=61'}, 'fast');
// 				$("#header").animate({height : '+=61'}, 'fast');
				$("#container").animate({top : '+=61'},'fast',function() {
					coverDiv("remove");
					$(this).css("height", "-=61");
				});
			}
			
		}

		function checkHead(){
			var show = $("#container").css("top");
			if(parseInt(show)==0){
				return 'icon-resize-small-2';
			}
			if(parseInt(show)!=0){
				return 'icon-resize-full-3';
			}
		}
</script>
</html>