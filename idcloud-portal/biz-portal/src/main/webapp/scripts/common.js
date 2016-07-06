// 当前登录用户
var currentUser;
// 当前主题
var currentTheme = "metro";
// 当前国际化
var currentCulture = "zh-CN";

var tkParam = "";

var tkUrl = "";

/** 加载页面判断user是否登陆 */
function setCurrentUser() {
	if($.cookie("PLATFORM_USER_SID") == undefined || $.cookie("PLATFORM_USER_SID") == null){
		// 没有token返回到登录页面，如果在登录页面就不会再回到登录页面
		var url = window.location.pathname;
		if(url.indexOf("pages/login.jsp")){
			return;}

		window.location.href = ctx+"/pages/login.jsp";
	}else{
		Core.AjaxRequest({
			url : ws_url + "/rest/user/current/"+ $.cookie("PLATFORM_USER_SID"),
			type : "GET",
			async : false,
			callback : function(data) {
				if (typeof data != "undefined" && null != data) {
					currentUser = data;
				}
			},
			failure : function(){
				var div = "<div id='notification' ><span>会话过期，请注意未保存的数据，请<u><a href="+ctx+"//pages/login.jsp>点击这里</a></u>重新登录。</span></div>";
				$("body").append(div);
			}
		});
	}


}
setCurrentUser();
// 查询用户并存入全局变量

/** 鉴权信息加密 */
function encryptAuth() {
	if( !(typeof currentUser == "undefined" && null == currentUser)) {
		var userInfo = currentUser.account + currentUser.realName + "1";
		tkParam = "?userId="+ currentUser.account + "&userName=" + currentUser.realName + "&roleId=1" + "&authId=" + $.md5(userInfo);
	}
}
encryptAuth();

/** 获取天馈接口地址 */
function getTkUrl() {
	/*Core.AjaxRequest({
		url : ws_url + "/rest/configs/getProp/tk.interface.url",
		dataType : "text",
		type : "GET",
		async : false,
		callback : function (data) {
			tkUrl = data;
		} 
     });*/
}
getTkUrl();


// 列表中国语言设置
var gridLocalizationObj = {
	pagergotopagestring : "当前页:",
	pagershowrowsstring : "每页显示:",
	pagerrangestring : " 共 ",
	pagernextbuttonstring : "下一页",
	pagerpreviousbuttonstring : "上一页",
	sortascendingstring : "正序",
	sortdescendingstring : "倒序",
	sortremovestring : "清除排序",
	firstDay : 1,
	percentsymbol : "%",
	currencysymbol : "￥",
	currencysymbolposition : "before",
	decimalseparator : ".",
	thousandsseparator : ",",
	emptydatastring:"暂没有数据",
	days : {
	    // full day names
		names: ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],
	    // abbreviated day names
		namesAbbr: ["周日","周一","周二","周三","周四","周五","周六"],
	    // shortest day names
		namesShort: ["周日","周一","周二","周三","周四","周五","周六"]
	},
	months : {
	    // full month names (13 months for lunar calendards -- 13th month should be "" if not lunar)
		names: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月",""],
	    // abbreviated month names
		namesAbbr: ["1","2","3","4","5","6","7","8","9","10","11","12",""]
	}
};

/**
 * 动态引入JS文件
 * 
 * @param url
 */
function addJS(url) {
	var script = document.createElement("script");
	script.setAttribute("async", "true");
	script.setAttribute("src", url);
	document.body.appendChild(script);
}

/**
 * 去掉HTML注释
 * 
 * @param e
 */
function unComment(e) {
	var t = e;
	for ( var n = 0, r = t.length; n < r; n++) {
		var i = document.getElementById(t[n]);
		for ( var s = 0, o = i.childNodes.length; s < o; s++) {
			var u = i.childNodes[s];
			if (u.nodeType == 3) {
				if (u.nodeType == document.COMMENT_NODE) {
					i.innerHTML = u.nodeValue;
					break
				}
			} else
				i.innerHTML = u.nodeValue
		}
	}
}

/**
 * 将form表单转换成json字符串
 */
(function($){  
    $.fn.serializeJson=function(){  
        var str = '{';
        var id = this.attr("id");
        
        var length = $("#"+id+" [data-name]").length;
        var array = $("#"+id+" [data-name]");
        
    	for(var i=0;i<length;i++){
    		var name = array.eq(i).attr("data-name");
    		var id = array.eq(i).attr("id");
    		if(i == (length-1)){
    			str+='"'+ name +'"' + ':' + '"'+ $("#"+id+"").val() +'"' + "}";
    		}else{
        		str+='"'+ name +'"' + ":" + '"'+ $("#"+id+"").val() +'"' + ",";
    		}
    	}
        return str;  
    };  
})(jQuery);

/**
 * 删除验证信息
 * 
 */
function removeValidatorInfo(){
	var info =$("div.jqx-validator-hint.jqx-rc-all");
	if(info.length > 0){
		info.remove();
	}
}

/**
 * ip操作工具类
 */
var IPUtil = {
		/** 子网掩码 */
		subnetMask : ["128.0.0.0","192.0.0.0","224.0.0.0",
						"240.0.0.0","248.0.0.0","252.0.0.0",
						"254.0.0.0","255.0.0.0","255.128.0.0",
						"255.192.0.0","255.224.0.0","255.240.0.0",
						"255.248.0.0","255.252.0.0","255.254.0.0",
						"255.255.0.0","255.255.128.0","255.255.192.0",
						"255.255.224.0","255.255.240.0","255.255.248.0",
						"255.255.252.0","255.255.254.0","255.255.255.0",
						"255.255.255.128","255.255.255.192","255.255.255.224",
						"255.255.255.240","255.255.255.248","255.255.255.252",
						"255.255.255.254","255.255.255.255"	],
		/**
		 * 判断2个ip地址是否在一个网段内
		 */
		isEqualIPAddress: function(addr1,addr2,mask){
			if(!addr1 || !addr2 || !mask){
				return false;
			}
			var res1 = [],res2 = [];
			addr1 = addr1.split(".");
			addr2 = addr2.split(".");
			mask  = mask.split(".");
			for(var i = 0,ilen = addr1.length; i < ilen ; i += 1){
				res1.push(parseInt(addr1[i]) & parseInt(mask[i]));
				res2.push(parseInt(addr2[i]) & parseInt(mask[i]));
			}
			if(res1.join(".") == res2.join(".")){
				return true;
			}else{
				return false;
			}
		},
		
		/**
		 * 验证子网掩码是否正确
		 * 
		 * @param val
		 * @returns {Boolean}
		 */				
		isExist: function(array,val){
			for(var i=0; i<array.length; i++){
				if(array[i] == val){
					 return true;
				}	
			}
			return false;
		},
		
		/**
		 * 结束IP>=开始IP
		 * 
		 * @param startIp
		 * @param endIp
		 */
		judgeIpSize:function(startIp, endIp){
			var ipStartArgs = startIp.split(".");
			var ipEndArgs = endIp.split(".");
			if (ipStartArgs[0] == ipEndArgs[0]){
				if(ipStartArgs[1] == ipEndArgs[1]){
					if(ipStartArgs[2] == ipEndArgs[2]){
						if(ipStartArgs[3] == ipEndArgs[3]){
							return true;
						}else{
							if(parseInt(ipStartArgs[3]) < parseInt(ipEndArgs[3])){
								return true;
							}else{
								return false;
							}
						}
					}else{
						if(parseInt(ipStartArgs[2]) < parseInt(ipEndArgs[2])){
							return true;
						}else{
							return false;
						}
					}
				}else{
					if(parseInt(ipStartArgs[1]) < parseInt(ipEndArgs[1])){
						return true;
					}else{
						return false;
					}
				}
			}else{
				if(parseInt(ipStartArgs[0]) < parseInt(ipEndArgs[0])){
					return true;
				}else{
					return false;
				}
			}
		},
		
		/**
		 * 根据子网和掩码计算ip段
		 * 
		 * @param startIP
		 * @param netmask
		 * @returns {Array}
		 */
		computeAllIpAddress: function(startIP, netmask){
			var net = this.getNets(startIP, netmask);
			// 获取开始ip和结束ip
			var ipStartArgs = net.startIP.split(".");
			var ipEndArgs = net.endIP.split(".");
			
			var arr = new Array();
			
			if (ipStartArgs[0] == ipEndArgs[0] && ipStartArgs[1]== ipEndArgs[1] && ipStartArgs[2] == ipEndArgs[2]) {
				for (var i = parseInt(ipStartArgs[3]); i <= parseInt(ipEndArgs[3]); i++) {
					var ip = ipStartArgs[0] + "." + ipStartArgs[1] + "." + ipStartArgs[2] + "." + i;
					arr.push(ip);
				}
			} else if (ipStartArgs[0] == ipEndArgs[0] && ipStartArgs[1] == ipEndArgs[1] && !ipStartArgs[2] == ipEndArgs[2]) {
				for (var i = parseInt(ipStartArgs[2]); i <= parseInt(ipEndArgs[2]); i++) {
					if (i == parseInt(ipStartArgs[2])) {
						for (var j = parseInt(ipStartArgs[3]); j < 256; j++) {
							var ip = ipStartArgs[0] + "." + ipStartArgs[1] + "." + String.valueOf(i) + "." + j;
							arr.push(ip);
						}
					} else if (i == parseInt(ipEndArgs[2])) {
						for (var j = 1; j <= parseInt(ipEndArgs[3]); j++) {
							var ip = ipStartArgs[0] + "." + ipStartArgs[1] + "." + String.valueOf(i) + "." + j;
							arr.push(ip);
						}
					} else {
						for (var j = 1; j < 255; j++) {
							
							var ip = ipStartArgs[0] + "." + ipStartArgs[1] + "." + String.valueOf(i) + "." + j;
							arr.push(ip);
							
						}
					}
				}
			}
			
			return arr;
		},
		
		/**
		 * 根据子网和子网掩码获取ip地址
		 * 
		 * @param startIP 开始ip
		 * @param netmask 子网掩码
		 * @returns {___nets0}
		 */
	 	getNets :function(startIP, netmask){
	 		var nets = new Object();
		 	var start = this.Negation(startIP, netmask).split(".");
			nets.startIP = start[0] + "." + start[1] + "." + start[2] + "." + (parseInt(start[3]) + 1);
			nets.endIP = this.TaskOR(this.Negation(startIP, netmask), netmask);
			nets.netMask = netmask;
			return nets;
	 	},
	 	/**
	 	 * 根据子网和掩码获取到该段ip的网络地址
	 	 * 
	 	 * @param startIP
	 	 * @param netmask
	 	 * @returns {String}
	 	 */
	 	Negation :function(startIP, netmask){
	 		var temp1 = startIP.split(".");
			var temp2 = netmask.split(".");
			var rets = new Array(4);
			for (var i = 0; i < 4; i++) {
				rets[i] = parseInt(temp1[i]) & parseInt(temp2[i]);
			}
			return rets[0] + "." + rets[1] + "." + rets[2] + "." + rets[3];
	 	},
	 	/**
	 	 * 根据子网和掩码获取最大ip地址
	 	 * 
	 	 * @param startIP
	 	 * @param netmask
	 	 * @returns {String}
	 	 */
	 	TaskOR :function(startIP, netmask){
	 		var temp1 = startIP.split(".");
			var temp2 = netmask.split(".");
			var rets = new Array(4);
			for (var i = 0; i < 4; i++) {
				rets[i] = 255 - (parseInt(temp1[i]) ^ parseInt(temp2[i]));
			}
			return rets[0] + "." + rets[1] + "." + rets[2] + "." + (rets[3] - 1);
	 	}
};




