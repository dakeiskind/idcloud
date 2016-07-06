define(['app-utils/httpService','cookie'], function(http) {

	 // 获取当前用户信息
	 var getCurrentUserInfo = function(){

	 	var currentUser = null;
	 	if($.cookie("IDC_TOKEN") == undefined || $.cookie("IDC_TOKEN") == "undefined" || $.cookie("IDC_TOKEN") == null){
			var preUrl = $.base64.btoa(window.location.href);
			window.location.href = "http://"+window.location.host+"/login.html?callback="+preUrl;
        }else{
        	// 获取当前用户信息
        	http.AjaxRequest({
				url : "/rest/user/current/"+ $.cookie("USER_SID"),
				type : "GET",
				async : false,
				callback : function(data) {
					if (typeof data != "undefined" && null != data) {
						currentUser = data;
						// 刷新token过期时间
						var idcToken = $.cookie("IDC_TOKEN");
						var userSid = $.cookie("USER_SID");
						$.removeCookie('IDC_TOKEN',{path: "/"});
						$.removeCookie('USER_SID',{path: "/"});
						var timeCount = new Date().getTime()+1800000;
						var deadline = new Date(timeCount);
						// token写入cookie
						$.cookie('IDC_TOKEN', idcToken, { expires:deadline,path: '/'});
						$.cookie('USER_SID', userSid, { expires: deadline,path: '/'});
					}
				},

				failure : function(){
					$.removeCookie('IDC_TOKEN', {path: "/"});
					$.removeCookie('USER_SID', {path: "/"});
					var preUrl = $.base64.btoa(window.location.href);
					window.location.href = "http://"+window.location.host+"/login.html?callback="+preUrl;
				}
			});

			return currentUser;

        }
	 }; 
	 return {
	 	"app_framework" : "src/framework",
		"app_modules" : "src/modules",
		"currentUser":getCurrentUserInfo()

	 };

});