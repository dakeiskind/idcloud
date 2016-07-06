// WebService请求地址
var location_host = "10.21.8.89:8888/idcloud-rest";
//var location_host = "www.dmcloud.com.cn:8112/idcloud-rest";
var ws_url = "http://" + location_host;// 发布使用的url
//var ws_url = "http://59.151.26.11:58080" + web_service_name;// 发布使用的url
var Core = {};

/**
 * Ajax请求方法
 * 
 * @param {Object}
 *            settings.params 参数对象，必须
 * @param {Object}
 *            settings.async 是否异步提交
 * @param {String}
 *            settings.url 请求地址，必须
 * @param {Function}
 *            settings.callback 成功后回调方法，必须
 * @param {boolean}
 *            settings.showMsg 处理成功时，是否显示提示信息 true:显示 false:不显示
 * @param {boolean}
 *            settings.showWaiting 是否显示等待条 true:显示 false:不显示
 * @param {Number}
 *            settings.timeout 超时时间
 * @param {String}
 *            settings.successMsg 成功消息
 * @param {String}
 *            settings.failureMsg 失败消息a
 */
Core.AjaxRequest = function (settings) {
    // 参数对象
    var params = settings.params === undefined ? null : settings.params,
    showWaiting = settings.showWaiting === undefined ? true : settings.showWaiting,
    showMsg = settings.showMsg === undefined ? true : settings.showMsg,
    async = settings.async === undefined ? true : settings.async,
    dataType = settings.dataType === undefined ? "json" : settings.dataType, 
    type = settings.type === undefined ? "POST" : settings.type,
    contentType = settings.contentType === undefined ? "application/json; charset=UTF-8" : settings.contentType,
    cache = settings.cache === undefined ? true : settings.cache,
    timeout = settings.timeout === undefined ? 60 * 1000 : settings.timeout,
    waiting = null;

    if (showWaiting) {
    	waiting = 1;
    	if($("#alertWindow").length > 0){
    		$("body").find("#cover_div").remove();
    	}
    	var bodyheight = document.documentElement.clientHeight+230;
    	var div = "<div id='cover_div' style='position:absolute;top:0px;left:0px;width:100%;height:"+bodyheight+"px;background:#fff;filter:Alpha(Opacity=40);/* IE */-moz-opacity:0.4;/* Moz + FF */opacity: 0.4;z-index:999999'><img src='images/loading6.gif' style='position:absolute;top:38%;left:48%'></img></div>";
    	$("body").append(div);
    }

    // 发送请求
    jQuery.ajax({
    	contentType : contentType,
        url: urlAddRandom(settings.url),
        type: type,
        data: JSON.stringify(params),
        async: async,
        dataType: dataType,
        beforeSend: function(XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("authorization","Bearer "+$.cookie('IDC_TOKEN'));
        },
        cache : cache,
        timeout: timeout,
        success: function (result, textStatus) {
            if (waiting != null) {
            	clearcoverlayout();
            }

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

			// callback给调用
			if(result.data != undefined){
				if (settings.callback) {
					settings.callback(result.data);
				}
			}else{
				// 没有返回data,将状态返回给用户
				if (settings.callback) {
					settings.callback(result.status);
				}
			}
        },
        error: function (response, options) {
            if (waiting != null) {
            	// 关闭遮盖层
            	clearcoverlayout();
            }
            if (typeof response.getResponseHeader == "function") {
				settings.failure(response);
            }
        }
    });
};


/**
 * 会话过期显示DIV
 */
Core.showTimeoutDiv = function () {
	// 得到最顶层窗口
	var win = Core.getWin();
	var div = "<div id='notification'><span>会话过期，请注意未保存的数据，请<u><a href='#' onclick='Core.goToLogin();'>点击这里</a></u>重新登录。</span></div>";
	if (win.document.getElementById("notification") == null) {
		Core.getWin().document.body.innerHTML = div;
	}
};

/** 得到最顶层的window对象 */
Core.getWin = function () {
    var win = window;
    while (win != win.parent) {
        win = win.parent;
    }
    return win;
};

/**
 * 登录页面迁移
 */
Core.goToLogin = function () {
	Core.getWin().location.href = ctx + "/pages/login.jsp";
};


/** 问候 */
Core.sayHello = function () {
    var hour = new Date().getHours(),
     hello = '';
    if (hour < 6) {
        hello = '凌晨好';
    } else if (hour < 9) {
        hello = '早上好';
    } else if (hour < 12) {
        hello = '上午好';
    } else if (hour < 14) {
        hello = '中午好';
    } else if (hour < 17) {
        hello = '下午好';
    } else if (hour < 19) {
        hello = '傍晚好';
    } else if (hour < 22) {
        hello = '晚上好';
    } else {
        hello = '夜里好';
    }
    return hello + '，';
} ;

/** 格式化日期 */
Date.prototype.format = function(format){ 
	var o = { 
		"M+" : this.getMonth()+1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth()+3)/3), 
		"S" : this.getMilliseconds()
	};

	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	}

	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	} 
	return format;
	//For example:
	//var date = new Date();
	//alert(date.format("yyyy年MM月dd日""));
	//alert(date.format("MM月d日""));
};

/*将yyyy-MM-dd hh:mm:ss格式的字条串转化为时间对象*/
String.prototype.toDate = function(){ 
	var temp = this.toString(); 
	temp = temp.replace(/-/g, "/"); 
	var date = new Date(Date.parse(temp)); 
	return date; 
};
/*将'/','''和'"'编码转义*/
String.prototype.toEscapeString=function(){
	var s=this.replace(/\\/g,'\\\\').replace(/\'/g,'\\\'').replace(/\"/g,'\\\"');
	return s;
};

/**
 * 操作cookie
 * 
 * Core.cookie('username'); //获得cookie   
 * Core.cookie('username', '张三'); //设置cookie   
 * Core.cookie('username', '李四', { expires: 3 }); //设置带时间的cookie 3小时   
 * Core.cookie('username', '', { expires: -1 }); //删除cookie  
 * Core.cookie('username', null); //删除 cookie  
 */
Core.cookie = function(name, value, options) {
	if (typeof value != 'undefined') {
		options = options || {};
		if (value === null) {
			value = '';
			options.expires = -1;
		}
		var expires = '';
		if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if (typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime()
						+ (options.expires * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = ';expires=' + date.toUTCString();
		}
		var path = options.path ? ';path=' + options.path : '';
		var domain = options.domain ? ';domain=' + options.domain : '';
		var secure = options.secure ? ';secure' : '';
		document.cookie = [ name, '=', encodeURIComponent(value), expires,
				path, domain, secure ].join('');
	} else {
		var cookieValue = null;
		if (document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for ( var i = 0; i < cookies.length; i++) {
				var cookie = jQuery.trim(cookies[i]);
				if (cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie
							.substring(name.length + 1));
					break;
				}
			}
		}
		return cookieValue;
	}
};

/**
 * 判断是否是项目支持的浏览器
 * 
 */
Core.browser = function(){
	var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    var isSupportBrowser = false;
    (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
    (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
    (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
    (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
    (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0; 

    //以下进行测试
    if (Sys.ie) {
	    if (Number(Sys.ie) >=9) { 	
	    	isSupportBrowser = true;
	   }
    }else if (Sys.firefox) {
    	//document.write('Firefox: ' + Sys.firefox);
    	isSupportBrowser = true;
    }else if (Sys.chrome){
    	//document.write('Chrome: ' + Sys.chrome);
    	isSupportBrowser = true;
    }else if (Sys.opera){
    	//document.write('Opera: ' + Sys.opera);
    	isSupportBrowser = false;
    }else if (Sys.safari){
    	//document.write('Safari: ' + Sys.safari);
    	isSupportBrowser = true;
    }else{
    	isSupportBrowser = true;
    }
    return isSupportBrowser;
};

function clearcoverlayout(){
	$("body").find("#cover_div").remove();  
}

function opensavelayout(){
	if($("#alertWindow").length > 0){
		$("body").find("#cover_div").remove();
	}
	var div = "<div id='cover_div' style='position:absolute;top:0px;left:0px;width:100%;height:100%;background:#fff;filter:Alpha(Opacity=40);/* IE */-moz-opacity:0.4;/* Moz + FF */opacity: 0.4;z-index:999999'><img src='images/commons/loading6.gif' style='position:absolute;top:48%;left:48%'></img></div>";
	$("body").append(div);
}

//判断url是否添加随机数
function urlAddRandom(url){
	if(url.indexOf("?")>0){
		url=url+"&r="+Math.random();
		return url;
	}else{
		url=url+"?r="+Math.random();
		return url;
	}
}

/**
 * 产生随机数
 * 
 * @returns 随机数
 */
Core.randNum = function () {
	var start = 10000;
	var end = 999999999999;
    return Math.floor(Math.random() * (end - start + 1) + start);
};

$.removeArray = function(array, from, to) {
	var rest = array.slice((to || from) + 1 || array.length);
	array.length = from < 0 ? array.length + from : from;
	return array.push.apply(array, rest);
};