<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/index-model.js"></script>
		<link rel="stylesheet" href="${ctx}/css/index.css" type="text/css" />
		<script type="text/javascript" src="${ctx}/jqwidgets/jqxprogressbar.js"></script>
		<script type="text/javascript" src="${ctx}/jqwidgets/jqxcore.js"></script>
		<title>阅联云管理平台</title>
</head>
<body>
	<%@ include file="/pages/common/header.jsp"%>
	<div id="container"> 
	   	 <iframe src="${ctx}/pages/platform-index3.jsp" id="iframediv" style="width:100%;height:99.5%;background:#fff;margin:0px;padding:0px;overflow-y:auto" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>
	</div>  
</body>
<script type="text/javascript">
	    var value = 50;
		/* 设置整个页面的布局*/
	    var bodyh = document.documentElement.clientHeight;
	    document.getElementById("container").style.height = (parseInt(bodyh) - 61) + 'px';
		
 		// 当浏览器页面大小发生改变时
		window.onresize = function(){
			  // 设置container容器的大小
			  var bodyh = document.documentElement.clientHeight;
			  document.getElementById("container").style.height = (parseInt(bodyh) - 61) + 'px';
	    };
	      
		/** 页面加载完成之后，初始化js */ 
	    $(function(){
	    	// 用户未登陆跳转至登陆
			if(typeof currentUser == "undefined" || null == currentUser) {
				window.location.href = ctx + "/pages/login.jsp";
			} 
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

		function checkHead(){
			var show = $("#container").css("top");
			if(parseInt(show)==0){
				return 'icon-resize-small-2';
			}
			if(parseInt(show)!=0){
				return 'icon-resize-full-3';
			}
		}
	
		// 子页面操作父页面，整体页面上下移动
		function operatPage(type){
			if(type == "up"){
				$("#header").animate({top : '-=61'}, 'fast');
				$("#container").animate({top : '-=61'},'fast',function() {
					$(this).css("height", "+=61");
				});
			} else if(type == "down") {
				$("#header").animate({top : '+=61'}, 'fast');
				$("#container").animate({top : '+=61'},'fast',function() {
					$(this).css("height", "-=61");
				});
			}
			
		}
</script>
</html>