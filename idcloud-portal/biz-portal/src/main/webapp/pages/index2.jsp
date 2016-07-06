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
		
		<title id="indexTitle"></title>

</head>
<body>
    <%@ include file="/pages/common/header.jsp"%>
	<div id="container"> 
	    <div id='jqxMainTabs' style="border:1px;">
	    	<ul>
	    		<li style="margin-left: 20px;" hasclosebutton='false'><div style="height:18px;line-height:18px"><i class='icons-blue icon-user'></i>首页</div></li>
	    	</ul>
	    	<div>
	    		<iframe src="${ctx}/pages/platform-index2.jsp" id="iframediv" style="width:100%;height:99.5%;background:#fff;margin:0px;padding:0px;overflow-y:auto" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>
	    	</div>
	    </div>
	</div>  
</body>
<script type="text/javascript">
$("#indexTitle").html(Core.getSysconfig("system.name"));
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
	    	
	    	$('#jqxMainTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:currentTheme,selectionTracker:"checked",showCloseButtons: true});
	    	
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
	
	
</script>
</html>