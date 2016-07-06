<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    .tips{
    	cursor:pointer;
    }
	.tips:hover{
		color:#1897D3;
	}
</style>
<div id="header">
    <div class="logo"> 
		  &nbsp;&nbsp;&nbsp;<img style="margin-top:7px;" src="${ctx}/images/logo/logo-blue-hp.png" width="45px;" height="45px"  border="0"/>
		  <span style="position:absolute;left:70px;top:17px;font-size:18px;color:#000;"> 
		  		<b>阅联云管理平台</b>  
		  </span>
	</div>
	<div id="navMenu" class="navMenu">
			<ul id="menuContent"></ul>
	</div>
	<div class="user"> 
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">    
			<tr> 
				<td align="right"><span style="color:#0099d7;font-size:12px"><span id="userName"></span>&nbsp;&nbsp;</span></td>          
				<td width="55px" align="right">
					<div style="width:100%;height:58px;position:relative;cursor:pointer;">              
						<div id="jqxTooltip" style="position:absolute;top:8px;left:0px;"><i class="icons-32-blue icon-user"></i>
							<div style="position:absolute;top:20px;left:32px;"><i class="icon-down-dir"></i></div>      
						</div>
					</div> 
				</td>      
			</tr> 
			 
		</table> 
	</div>  
</div>

<div id="resetPasswdWindow">
     <div>修改密码</div>
     <div id="resetPasswdForm" style="overflow: hidden;">
     	<input type="hidden" data-name="userSid" id="resetPasswdUserSid"/>
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
                 <td align="right">性别:</td>
                 <td align="left">
                 	<div data-name="sex" id="sex-index"></div>
                 </td>
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
    /** 获取当前用户的角色和权限数据，生成导航条 */
    Core.AjaxRequest({
		url : ws_url + "/rest/user/platform/findMoudules/" + currentUser.userSid,
		type : "GET",
		callback : function (data) {
			createNavList(data);
		} 
	});
    
    /** 创建菜单 */
	function createNavList(data) { 
		var str = '';
		for ( var i = 0; i < data.length; i++) {
			if(data[i].moduleName == "平台首页"){
				continue;
			}else{
				str += '<li onclick="javascript: addTabsOnMainPage(this,' + "'"
				+ data[i].moduleUrl + "'" + ",'" + data[i].moduleName + "','" + data[i].moduleIconUrl + "','"+(i)+"'"
				+ ');">';  
				str += '<div style="width:100%;height:38px;margin:0px;padding:0px;"><i style="font-size:32px" class="'+data[i].moduleIconUrl+'"></i></div>';
				str += '<div><font style="font-size:14px;">'+data[i].moduleName+'</font></div></li>';  
			}
		}
		
		$("#menuContent").html(str);
		// 计算ul的长度
		var count = $("#menuContent li").length;
		var uwidth = count*80 + count*1 + 1;
		$("#menuContent").css("width",uwidth+"px");
	}


	$("#jqxTooltip").jqxTooltip({   
		width: 90,   
	    height: 100,    
		content: '<p class="tips" data-bind="click: resetPassword"><i class="icon-key"></i>&nbsp;修改密码</p><p class="tips" data-bind="click: popUserManagementWindow"><i class="icon-tag"></i>&nbsp;账户管理</p><p class="tips" data-bind="click: logout"><i class="icon-logout"></i>&nbsp;退出&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>',   
		position: 'bottom-left',
		autoHide:false,
		opacity: 0.9,
		theme:"metro"
	});
	
	function addTabsOnMainPage(obj,url, name,icon,index){
		if(index == 0){
			index++;
		}
		
		if($("#iframeTabdiv"+index).length==0){
			$("#jqxMainTabs").jqxTabs('addLast', '<div style="height:18px;line-height:18px"><i class="icons-blue ' + icon + '"></i>'+name+'</div>', '<iframe src="${ctx}'+url+'" id="iframeTabdiv'+index+'" style="width:100%;height:99.5%;background:#fff;margin:0px;padding:0px;overflow:hidden" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>');
    	}else{
    		for(var i=0;i<$('#jqxMainTabs').jqxTabs('length');i++){
    			if($('#jqxMainTabs').jqxTabs('getTitleAt', i) == name){
    				$('#jqxMainTabs').jqxTabs('select', i); 
    			}
    		}
    	}
		
	}
	

</script>
