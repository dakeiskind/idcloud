<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/pages/common/taglibs.jsp"%>
	<%@ include file="/pages/common/resources.jsp"%>
	<link href="${ctx}/css/login.css" rel="stylesheet">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title id="loginTitle"></title>
	
</head>

<body>
<div class="top" align="left" style="background: url('${ctx}/images/login/nav_bgp.png');">
  <div style="width: 1024px;position: relative;height: 100%;margin-left: 10%;color: #fff;">
  	<div style="float: left;height: 100%;width: 75px;">
  		<img src="${ctx}/images/login/hp-logo.png" width="42px" height="42px" style="position: absolute;top: 10%;margin-top: 8px;" />
  	</div>
  	<div style="float: left;height: 100%;width: 70%;line-height: 78px;font-size: 24px;color: white;" id="login-title-sysMsg"></div>
  </div>
</div>
<div id="loginMiddle" class="middle">
    <div align="center"  class="middle_con">
      <div class="login_box">
        <div class="login">
          <div class="login_title">管理门户</div>
          <div class="login_table" align="center">
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="list_tt">用户账号：</td>
              </tr>
              <tr>
                <td align="left"><input type="text" style="height:25px;" id="login-username" name="account" onkeydown="useEnterkeyLogin(event)" maxlength="32"/> </td>
              </tr>
              <tr>
                <td class="list_tt"></td>
              </tr>
              <tr>
                <td class="list_tt">用户密码：</td>
              </tr>
              <tr>
                <td align="left"><input type="password" style="height:25px;" id="login-password" name="password" onkeydown="useEnterkeyLogin(event)" maxlength="32"/></td>
              </tr>
              <tr>
              <td class="error" style="height: 25px;">
         			<p id="login-title"  style="font-size:15px;font-weight:500;text-align:left;margin:0px 0px 5px 0px;padding:0px;"></p>
              </td>
              </tr>
              <tr>
                <td class="list_but">
                 <div class="login_but"><a href="#" class="icon_01" onclick="javascript:verifyUser();" style="width: 175px;height: 35px;" >登&nbsp;录</a></div>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>

</div>
<div class="footer">Copyright © 2015 中国惠普有限公司 版权所有</div>
</body>
<script type="text/javascript">
	
	// 自动获取焦点
	document.getElementById('login-username').focus();
	
	document.title = Core.getSysconfig("system.name") + "- 登录";

	var bodyh = document.documentElement.clientHeight;
	document.getElementById("loginMiddle").style.height = (parseInt(bodyh) - 111) + 'px'; 
	
	$("#login-title-sysMsg").html(Core.getSysconfig("system.name")+" - 管理门户");
	
	// 当浏览器屏幕大小变化时,修改div的高度
	window.onresize = function(){
		var bodyh = document.documentElement.clientHeight;
		document.getElementById("loginMiddle").style.height = (parseInt(bodyh) - 111) + 'px';
	};
	
	// 提交登录信息
	function verifyUser(){
		if($("#login-username").val() =="" && $("#login-password").val() !=""){
			$("#login-title").css("color","red").html("请输入用户名！");
		}else if($("#login-username").val() !="" && $("#login-password").val() ==""){
			$("#login-title").css("color","red").html("请输入密码！");
		}else if($("#login-username").val() =="" && $("#login-password").val() ==""){
			$("#login-title").css("color","red").html("请输入用户名和密码！");
		}else{

			//验证用户
			Core.AjaxRequest({
				url : ws_url + "/rest/user/login",
				params : { 
					account : $("#login-username").val(),
					password : $("#login-password").val(),
					userType : "01"
				},
				callback : function (data) {
					if("false" == data.resultStatus){
						$("#login-title").css("color","red").html(""+data.data+"");
					}else{
						$.removeCookie('PLATFORM_IDC_TOKEN');
						$.removeCookie('PLATFORM_USER_SID');
						$.cookie("PLATFORM_IDC_TOKEN",data.data.accessToken);
						$.cookie("PLATFORM_USER_SID",data.data.userSid);
						window.location.href = ctx + "/pages/main.jsp";
					}
			    },
			    failure:function(data){
// 		    		var errorObj = data.responseJSON; 
		    		$("#login-title").css("color","red").html(""+data.message+"");
			    }
			});
		}
	}
	
	// 点击Enter键登录
	function useEnterkeyLogin(event){
		 if(event.keyCode==13){
			 verifyUser();
	     }
	}
	
	
</script>
</html>