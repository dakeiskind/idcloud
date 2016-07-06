<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
   	.countNumCss {
	    background-position: 0 -210px;
	    color: #fff;
	    cursor: pointer;
	    font-family: Microsoft Yahei;
	    font-size: 11px;
	    height: 12px;
	    line-height: 11px;
	    padding: 0.1em 0.4em 0.2em;
	    position: relative;
/* 	    right: -2px; */
	    text-align: center;
	    top: -10px;
	    background: #f16433;
	    box-shadow:0 1px 1px rgba(0,0,0,0.3);
	    border-radius:2px;
	    text-shadow:0 1px 1px rgba(0,0,0,0.3);
	}
    .tips{
    	cursor:pointer;
    }
	.tips:hover{
		color:#1897D3;
	}
	#hiddenDiv {
		width:100px;
		height:100px; 
		z-index:200;
		font-size:12px;
		position:absolute;
		top:50px;
		left:-50px;
		border-color: #e5e5e5;
		color: #767676;
		background: #ffffff;
		text-align: center;
      	border-radius:10px;
      	border:1px solid #EEEEEE;
	}
	.top_bk{
		width:100%;
		height:55px;
		background: url(../images/login/title_bk.png) left no-repeat;
	}
	.top{
		width:100%;
		height:60px;
/* 		background:black; */
		
		position: absolute;
	}
</style>
<div id="header">
    <div class="logo">
		  &nbsp;&nbsp;&nbsp;<img style="margin-top:6px;" src="${ctx}/images/logo/logo-blue-hp.png" width="42px;" height="42px"  border="0"/>
		  <span style="position:absolute;left:60px;top:17px;font-size:18px;color:#000;">  
		  		<b id="headerTitle"></b>
		  </span>
	</div>
	<div id="navMenu" class="navMenu">
			<ul id="menuContent"></ul>
	</div>
	<div class="user"> 
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">    
			<tr> 
				<td align="right"><span style="color:#0099d7;font-size:12px"><span id="userName"></span></span></td>          
				<td width="50px" align="right"> 
					<div style="width:100%;height:58px;position:relative;cursor:pointer;">               
						<div id="jqxTooltip" style="position:absolute;top:12px;left:0px;"><i class="icons-24-blue icon-user"></i>
							<div style="position:absolute;top:18px;left:24px;"><i class="icon-down-dir"></i></div>      
						</div>
						<div id="hiddenDiv" style="display:none">
    						<p class="tips" data-bind="click: resetPassword"><i class="icon-key"></i>&nbsp;修改密码</p><p class="tips" data-bind="click: popUserManagementWindow"><i class="icon-tag"></i>&nbsp;账户管理</p><p class="tips" data-bind="click: logout"><i class="icon-logout"></i>&nbsp;退出&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
						</div>
					</div> 
				</td>      
			</tr> 
			 
		</table> 
	</div>
	<div id="coverDivIn" style="position:absolute;top:0px;left:0px;display:none;width:100%;height:100%;z-index:999999"></div>  
</div>

<div id="resetPasswdWindow">
     <div>修改密码</div>
     <div id="resetPasswdForm" style="overflow: hidden;">
     	<input type="hidden" data-name="userSids" id="resetPasswdUserSid"/>
         <table border="0" width="100%" cellspacing="5" cellpadding="0">
             <tr>
                 <td align="right"><font style="color:red">*</font>用户账号:</td>
                 <td align="left"><span id="reset-passwd-account"></span></td>
             </tr>
             <tr>
                 <td align="right"><font style="color:red">*</font>新密码:</td>
                 <td align="left"><input type="password" id="resetPasswordInput" class="text-input" /></td>
             </tr>
             <tr>
                 <td align="right"><font style="color:red">*</font>确认新密码:</td>
                 <td align="left"><input type="password" id="resetPasswordConfirmInput" class="text-input" /></td>
             </tr>
             <tr>
                 <td align="right" colspan="2">
                 	<br />
                  <input style="margin-right: 5px;" data-bind="click: reset_passwd_submit" type="button" id="resetPasswdSave" value="保存" />
                  <input id="resetPasswdCancel" type="button" value="取消" />
                 </td>
             </tr>
         </table>
     </div>
</div>

<!-- 账户管理 -->
<div id="accountManagementWindow">
     <div>账户管理</div>
     <div id="accountMgtForm" style="overflow: hidden;">
     	 <input type="hidden" data-name="userSid" id="mgt-usersid-index"/>
         <table border="0" width="100%" cellspacing="5" cellpadding="0">
             <tr>
                 <td align="right"><font style="color:red">*</font>姓名:</td>
                 <td align="left"><input type="text" data-name="realName" id="realName-index"/></td>
             </tr>
             <tr>
                 <td align="right">联系电话:</td>
                 <td align="left">
                     <input type="text" data-name="mobile" id="mobil-index"/>
                 </td>
             </tr>
             <tr>
                 <td align="right"><font style="color:red">*</font>电子邮箱:</td>
                 <td align="left">
                    <input type="text" data-name="email" id="email-index"/>
                 </td>
             </tr>
             <tr>
                 <td align="right" colspan="2">
	                  <br />
	                  <input style="margin-right: 5px;" data-bind="click: submitUserMgtInfo" type="button" id="indexUserMgtSave" value="保存" />
	                  <input id="indexUserMgtCancel" type="button" value="取消" />
                 </td>
             </tr>
         </table>
     </div>
</div>

<script type="text/javascript"> 
	var moudulesData = null; 
	var firstTabsItem = "";
	var preOpenTabsIndex = [0];
	var preBasicTabsIndex = 0;
	//给title赋值
	$("#headerTitle").html(Core.getSysconfig("system.name"));
	
    /** 获取当前用户的角色和权限数据，生成导航条 */
    if(currentUser != null || currentUser != 'undefined'){
    	Core.AjaxRequest({ 
    		url : ws_url + "/rest/user/platform/findMoudules/" + currentUser.userSid,
    		type : "GET",
    		async: false,
    		callback : function (data) {
    			
    			createNavList(data);
    			moudulesData = data;
    		} 
    	});
    }
    
    /** 创建菜单 */
	function createNavList(data) { 
    	var countMsg;
		var str = '';
		var isFirst = true;
		for ( var i = 0; i < data.length; i++) {
			// 找出一级菜单
			if(data[i].parentSid == null || data[i].parentSid == "" || data[i].parentSid == "0"){
				if(isFirst){
					firstTabsItem = data[i];
					str += '<li class="liShow" onclick="javascript: addTabsOnMainPage(this,' + "'"
					+ data[i].moduleUrl + "'" + ",'" + data[i].moduleName + "','" + data[i].moduleIconUrl + "','"+data[i].moduleSid+"'"
					+ ');">';
					str += '<i style="font-size:14px" class="'+data[i].moduleIconUrl+'"></i>';
					str += '<font style="font-size:14px;"><b>'+data[i].moduleName+'</b></font>';
					str += '</li>';
				}else{
					str += '<li onclick="javascript: addTabsOnMainPage(this,' + "'"
					+ data[i].moduleUrl + "'" + ",'" + data[i].moduleName + "','" + data[i].moduleIconUrl + "','"+data[i].moduleSid+"'"
					+ ');">';  
					str += '<i style="font-size:14px" class="'+data[i].moduleIconUrl+'"></i>';
					str += '<font style="font-size:14px;"><b>'+data[i].moduleName+'</b></font>';
					str += '</li>';
				}
				isFirst = false;
			}
		}
		
		$("#menuContent").html(str);
		
	}

	/** 点击菜单事件 */
	function addTabsOnMainPage(obj,url, name,icon,sid){ 
		 // 切换header头部的样式
		$("#menuContent .liShow").removeClass("liShow");
		$(obj).addClass("liShow");
		 
		if($("#jqxMainTabs").length > 0){
			$("#jqxMainTabs").remove();
		}
		// 查询出二级菜单
		var li = "";
		var div = "";
		var index = 0;
		var firstUrl = "";
		var subSid = sid;
		for( var i = 0; i < moudulesData.length; i++) {
			
			if(moudulesData[i].parentSid == sid){  
				if(index == 0){
					firstUrl = moudulesData[i].moduleUrl;
					li +="<li style='margin-left: 20px;'hasclosebutton='false'><div style='height:18px;line-height:18px'><i class='icons-blue "+moudulesData[i].moduleIconUrl+"'></i>"+moudulesData[i].moduleName+"</div></li>";
				}else{
					li +="<li hasclosebutton='false'><div style='height:18px;line-height:18px'><i class='icons-blue "+moudulesData[i].moduleIconUrl+"'></i>"+moudulesData[i].moduleName+"</div></li>";
				}
				div += "<div class='content' id='div"+index+"' sid='"+moudulesData[i].moduleSid+"' name='"+moudulesData[i].moduleName+"' url='"+moudulesData[i].moduleUrl+"'>"
				       + "</div>";
				index ++ ;
			}
		}
		
		// 如果一级菜单下面不存在二级菜单时
		var tabs = "";
		if(index== 0){ 
			
			 if(url.indexOf("http") != -1){
			 }else{
				 url = ctx+url;
			 }
			tabs += "<iframe id='iframe1' src='"+(url)+"' style='width:100%;height:99.5%;background:#fff;margin:0px;padding:0px;overflow-y:auto' frameborder='no' border='0' marginwidth='0' marginheight='0' scrolling='yes'></iframe>";
		}else{ 
			tabs += "<div id='jqxMainTabs' style='border:1px;'><ul>"+li+"</ul>"+div+"</div>";
		}
		
		$("#container").html(tabs);
		
		if(index== 0){ 
		}else{ 
			$('#jqxMainTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:currentTheme,selectionTracker:"checked",showCloseButtons: true});
			$('#jqxMainTabs').on('selected', function (event) {
		         var pageIndex = event.args.item;
		         var url = $(".content").eq(pageIndex).attr("url");
		         var name = $(".content").eq(pageIndex).attr("name");
		         subSid = $(".content").eq(pageIndex).attr("sid");
		         
		         if(pageIndex < 4){
		        	 preBasicTabsIndex = pageIndex;
		         }
		         
		         preOpenTabsIndex.push(pageIndex);
		         
		         lazyLoadInTabs(pageIndex,url,subSid);
		         //lazyLoadInTabs
		    });
			
			$('#jqxMainTabs').on('removed', function (event) {
				
				var len = preOpenTabsIndex.length;
				if(len <= 1){
					var val = preOpenTabsIndex[0];
					$('#jqxMainTabs').jqxTabs({ selectedItem: (val+1) });
				}else{
					var val = preOpenTabsIndex[len-2];
					$('#jqxMainTabs').jqxTabs({ selectedItem: (val+1) });
				}
		    });
		}
		
		
		if($(".content").length > 0){
			subSid = $(".content").eq(0).attr("sid");
		}
		lazyLoadInTabs(0,firstUrl,subSid);
	}
	
	var selectIndex = 0;
	/** 首页图标点击事件 */
	function addTabsOnMainPage2(obj,url, name,icon,sid){ 
		 // 切换header头部的样式
		$("#menuContent .liShow").removeClass("liShow");
		$(obj).addClass("liShow");
		 
		if($("#jqxMainTabs").length > 0){
			$("#jqxMainTabs").remove();
		}
		// 查询出二级菜单
		var li = "";
		var div = "";
		var index = 0;
		var firstUrl = "";
		var subSid = sid;
		for( var i = 0; i < moudulesData.length; i++) {
			
			if(moudulesData[i].parentSid == sid){  
				if(index == 0){
					firstUrl = moudulesData[i].moduleUrl;
					li +="<li style='margin-left: 20px;'hasclosebutton='false'><div style='height:18px;line-height:18px'><i class='icons-blue "+moudulesData[i].moduleIconUrl+"'></i>"+moudulesData[i].moduleName+"</div></li>";
				}else{
					li +="<li hasclosebutton='false'><div style='height:18px;line-height:18px'><i class='icons-blue "+moudulesData[i].moduleIconUrl+"'></i>"+moudulesData[i].moduleName+"</div></li>";
				}
				div += "<div class='content' sid='"+moudulesData[i].moduleSid+"' name='"+moudulesData[i].moduleName+"' url='"+moudulesData[i].moduleUrl+"'>"
				       + "<iframe src='' id='iframeDiv"+index+"' style='width:100%;height:99.5%;background:#fff;margin:0px;padding:0px;overflow-y:auto' frameborder='no' border='0' marginwidth='0' marginheight='0' scrolling='yes'></iframe>"		
				       + "</div>";
				index ++ ;
			}
		}
		
		// 如果一级菜单下面不存在二级菜单时
		var tabs = "";
		if(index== 0){ 
			 if(url.indexOf("http") != -1){
			 }else{
				 url = ctx+url;
			 }
			tabs += "<iframe src='"+(url)+"' style='width:100%;height:99.5%;background:#fff;margin:0px;padding:0px;overflow-y:auto' frameborder='no' border='0' marginwidth='0' marginheight='0' scrolling='yes'></iframe>";
		}else{ 
			tabs += "<div id='jqxMainTabs' style='border:1px;'><ul>"+li+"</ul>"+div+"</div>";
		}
		
		$("#container").html(tabs);
		$('#jqxMainTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:currentTheme,selectionTracker:"checked",showCloseButtons: true});
		$('#jqxMainTabs').on('selected', function (event) {
	         var pageIndex = event.args.item;
	         var url = $(".content").eq(pageIndex).attr("url");
	         var name = $(".content").eq(pageIndex).attr("name");
	         subSid = $(".content").eq(pageIndex).attr("sid");
	         
	         lazyLoad(pageIndex,url,subSid);
	    });
			
		$('#jqxMainTabs').jqxTabs('select', selectIndex);
		if($(".content").length > 0){
			subSid = $(".content").eq(0).attr("sid");
		}
		lazyLoad(0,firstUrl,subSid);
	}
	
	// 切换加载
	function lazyLoad(index,url,subSid){
		 if(url.indexOf("http") != -1){
			 $("#iframeDiv"+index+"").attr("src",url);
		 }else{
			 
			 $("#iframeDiv"+index+"").attr("src",ctx+url+"?moduleSid="+subSid);
		 }
	}
	
	// 切换加载--只加载第一次，第二次不加载
	function lazyLoadInTabs(index,url,subSid){
		 if($("#iframeDiv"+index+"").length > 0){
		 }else{
			 if(url.indexOf("http") != -1){
				 $("#div"+index+"").html("<iframe src='"+url+"' id='iframeDiv"+index+"' style='width:100%;height:99.5%;background:#fff;margin:0px;padding:0px;overflow-y:auto' frameborder='no' border='0' marginwidth='0' marginheight='0' scrolling='yes'></iframe>");
			 }else{
				 //$("#iframeDiv"+index+"").attr("src",ctx+url+"?moduleSid="+subSid);
				 $("#div"+index+"").html("<iframe src='"+ctx+url+"?moduleSid="+subSid+"' id='iframeDiv"+index+"' style='width:100%;height:99.5%;background:#fff;margin:0px;padding:0px;overflow-y:auto' frameborder='no' border='0' marginwidth='0' marginheight='0' scrolling='yes'></iframe>");
			 }
		 }
		 
	}
	
	/** 弹出第一级页面 */
    function loadFirstPage(data){
    	if($("#jqxMainTabs").length > 0){
			$("#jqxMainTabs").remove();
		}
		// 查询出二级菜单
		var li = "";
		var div = "";
		var index = 0;
		var firstUrl = "";
		var subSid = data.moduleSid; 
		for( var i = 0; i < moudulesData.length; i++) {
			
			if(moudulesData[i].parentSid == data.moduleSid){  
				if(index == 0){
					firstUrl = moudulesData[i].moduleUrl;
					li +="<li style='margin-left: 20px;'hasclosebutton='false'><div style='height:18px;line-height:18px'><i class='icons-blue "+moudulesData[i].moduleIconUrl+"'></i>"+moudulesData[i].moduleName+"</div></li>";
				}else{
					li +="<li hasclosebutton='false'><div style='height:18px;line-height:18px'><i class='icons-blue "+moudulesData[i].moduleIconUrl+"'></i>"+moudulesData[i].moduleName+"</div></li>";
				}
				
				div += "<div class='content' sid='"+moudulesData[i].moduleSid+"' name='"+moudulesData[i].moduleName+"' url='"+moudulesData[i].moduleUrl+"'>"
				       + "<iframe src='' id='iframeDiv"+index+"' style='width:100%;height:99.5%;background:#fff;margin:0px;padding:0px;overflow-y:auto' frameborder='no' border='0' marginwidth='0' marginheight='0' scrolling='yes'></iframe>"		
				       + "</div>";
				index ++ ;
			}
		}
		
		// 如果一级菜单下面不存在二级菜单时
		var tabs = "";
		if(index== 0){ 
			tabs += "<iframe src='"+(ctx+data.moduleUrl)+"' style='width:100%;height:99.5%;background:#fff;margin:0px;padding:0px;overflow-y:auto' frameborder='no' border='0' marginwidth='0' marginheight='0' scrolling='yes'></iframe>";
		}else{ 
			tabs += "<div id='jqxMainTabs' style='border:1px;'><ul>"+li+"</ul>"+div+"</div>";
		}
		
		$("#container").html(tabs);
		if(index != 0){ 
			$('#jqxMainTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:currentTheme,selectionTracker:"checked",showCloseButtons: true});
			$('#jqxMainTabs').on('selected', function (event) {
		         var pageIndex = event.args.item;
		         var url = $(".content").eq(pageIndex).attr("url");
		         var name = $(".content").eq(pageIndex).attr("name");
		         subSid = $(".content").eq(pageIndex).attr("sid");
		         
		         lazyLoad(pageIndex,url,subSid);
		    });
		}
		
		if($(".content").length > 0){
			subSid = $(".content").eq(0).attr("sid");
		}
		lazyLoad(0,firstUrl,subSid);
	} 
	
	(function(){
		  var title = document.getElementById('jqxTooltip');
		  var hiddenDiv = document.getElementById('hiddenDiv');
		  var timer = null;
		  
		  hiddenDiv.onmouseover = title.onmouseover = function(){
		      if(timer) clearTimeout(timer);
		      hiddenDiv.style.display = 'block';
		  };
		 hiddenDiv.onmouseout = title.onmouseout = function(){
		    timer = setTimeout(function(){
		      hiddenDiv.style.display = 'none';
		    },400);
		    
		  };
		})();
	
	// 供子页面调用---在父级的tab中弹出新的虚拟机选项卡
	function addParentTabs(sid,monitorNodeId,name){
		var isok = true;
		for(var i=0;i<$('#jqxMainTabs').jqxTabs('length');i++){
			if($('#jqxMainTabs').jqxTabs('getTitleAt', i) == name){
				$('#jqxMainTabs').jqxTabs('select', i); 
				isok = false;
			}
		}
		
		if(isok){
			$('#jqxMainTabs').jqxTabs('addLast', '<div style="height:18px;line-height:18px"><i class="icons-blue icon-book"></i>'+name+'</div>', '<iframe src="${ctx}/pages/resources/vm/res-vm-info.jsp?resVmSid='+sid+'&monitorNodeId='+monitorNodeId+'" style="width:100%;height:99.5%;background:#fff;margin:0px;padding:0px;overflow:hidden" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>');
		}
	}
	
	// 供子页面调用---在父级的tab中弹出新的主机选项卡
	function addHostParentTabs(sid,monitorNodeId,name){
		var isok = true;
		for(var i=0;i<$('#jqxMainTabs').jqxTabs('length');i++){
			if($('#jqxMainTabs').jqxTabs('getTitleAt', i) == name){
				$('#jqxMainTabs').jqxTabs('select', i); 
				isok = false;
			}
		}
		
		if(isok){
			$('#jqxMainTabs').jqxTabs('addLast', '<div style="height:18px;line-height:18px"><i class="icons-blue icon-book"></i>'+name+'</div>', '<iframe src="${ctx}/pages/resources/host/res-host-detail-index.jsp?resHostSid='+sid+'&monitorNodeId='+monitorNodeId+'" style="width:100%;height:99.5%;background:#fff;margin:0px;padding:0px;overflow:hidden" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>');
		}
		
	}
	
	// 返回container容器的高度
	function getContainerHeight(){
		return $("#container").height();
	}
	
	// 遮住header，防止点击首页之后，马上点击其他模块，出现页面上移的问题
    function coverDiv(type){
    	if("cover" == type){
    		$("#coverDivIn").css("display","block");
    	}else if("remove" == type){
    		$("#coverDivIn").css("display","none");
    	}
    }
		
</script>
