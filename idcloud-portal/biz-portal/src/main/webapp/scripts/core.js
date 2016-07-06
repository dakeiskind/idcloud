// WebService请求地址
var web_service_name = "/idcloud-rest";
//var ws_url = "http://" + window.location.host + web_service_name;// 发布使用的url
var ws_url = "http://localhost:8888" + web_service_name;// 发布使用的url

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
 *            settings.failureMsg 失败消息
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
	waiting = null;

    //cache = settings.cache === undefined ? true : settings.cache,
    //timeout = settings.timeout === undefined ? 60 * 1000 : settings.timeout,
//    waiting = null;
    if (showWaiting) {
    	waiting = 1;
    	if($("#alertWindow").length > 0){
    		$("body").find("#cover_div").remove();
    	}
    	var div = "<div id='cover_div' style='position:absolute;top:0px;left:0px;width:100%;height:100%;background:#fff;filter:Alpha(Opacity=10);/* IE */-moz-opacity:0.1;/* Moz + FF */opacity: 0.1;z-index:999999'><img src="+ctx+"/images/commons/loading6.gif style='position:absolute;top:48%;left:48%'></img></div>";
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
			XMLHttpRequest.setRequestHeader("authorization","Bearer "+($.cookie('PLATFORM_IDC_TOKEN') == 'undefined'?"":$.cookie('PLATFORM_IDC_TOKEN')));
        },
        //cache : cache,
        //timeout: timeout,
        success: function (result, textStatus) {
			// 关闭loading层
            if (waiting != null) {
            	clearcoverlayout();
            }
			// 返回值200
			if(result.code == 200){
				if(result.message == undefined || result.message == '' || result.message == null){
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
				}else {
					// 是否显示message
					if(showMsg){
						if(result.status){
							Core.alert({
										   type: "info",
										   message: result.message
									   });
						}else{
							Core.alert({
										   type: "error",
										   message: result.message
									   });
						}
					}
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
				}
			}else{
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
			}
        },
        error: function (response, options) {
            if (waiting != null) {
            	// 关闭遮盖层
            	clearcoverlayout();
            }
            if (typeof response.getResponseHeader == "function") {
            	if (response.status == 405) {
//                		Core.alertError("您请求的地址在服务器中不存在！", '', function (){
//                			Core.CloseWindow();
//                		});
            	} else if(response.status == 403) {
//            		goLoginPage();
            	} else if (response.status == 320) {
            		if (window.location.pathname == ctx + "/pages/login.jsp" || window.location.pathname == ctx + "/") {

            		} else {
            			Core.showTimeoutDiv();

            		}
            	}else{
            		settings.failure(response.responseJSON);
            	}
            }
        }
    });
};

/**
 * AjaxSubmit提交带file的表单
 * 
 * @param {Object}
 *            settings.async 是否异步提交，
 * @param {String}
 *            settings.url 请求地址，必须
 * @param {Function}
 *            settings.formId form的ID，必须
 * @param {boolean}
 *            settings.showMsg 处理成功时，是否显示提示信息 true:显示 false:不显示
 * @param {boolean}
 *            settings.showWaiting 是否显示等待条 true:显示 false:不显示
 * @param {String}
 *            settings.successMsg 成功消息
 * @param {String}
 *            settings.failureMsg 失败消息
 */

Core.AjaxFormSubmit = function (settings) {
    // 参数对象
	if (settings.formId === undefined) {
		return;
	}
	var showWaiting = settings.showWaiting === undefined ? true : settings.showWaiting;
	var async = settings.async === undefined ? true : settings.async;
	var showMsg = settings.showMsg === undefined ? true : settings.showMsg;
	var url = settings.url === undefined ? $("#"+settings.formId).attr("action") : settings.url;
	var dataType = settings.dataType === undefined ? "json" : settings.dataType;
	var waiting = null;
    if (showWaiting) {
//        waiting = mini.loading("正在处理中，请稍后.", "提示");
    }
	var options = {
		 type:'POST',
		 async : async,
		 dataType: dataType,
	     url: url,
	     success: function (result) { 
//           if (waiting != null) {
//            	mini.hideMessageBox(waiting);
//            }
           if ((result instanceof Object) == false) {
	    		if (settings.callback) { // 回调方法
				    settings.callback(result);
				}
	    		return;
           }
            if (result.status == undefined) {
                if (result.timeout != undefined && result.timeout == true) {
                    var message = '发生异常.';
                    if (result.message && result.message != '') {
                        message = result.message;
                    }
//                	Core.alertWarning(message, '提示', function () {
//                        var win = window;
//                        while (win != win.parent) {
//                            win = win.parent;
//                        }
//                        win.location = ctx;
//                	});
                } else {
					if (settings.callback) { // 回调方法
					    settings.callback(result);
					}
                }
            } else if (result.status == true) {
                if (showMsg == true) {
                    // 请求成功时的提示
                    var successMsg = '操作成功.';
                    if (result.message && result.message != '') {
                        successMsg = result.message;
                    } else if (settings.successMsg && settings.successMsg != '') {
                        successMsg = settings.successMsg;
                    }
//                    Core.alertInfo(successMsg, '提示', function () {
//	                    if (settings.callback) { // 回调方法
//	                        settings.callback(result);
//	                    }
//                    });
                } else {
                    if (settings.callback) { // 回调方法
                        settings.callback(result);
                    }
                }
            } else if (result.status == false) {
                var message = '发生异常.';
                if (result.message && result.message != '') {
                	// 后台设定的业务消息
                    message = result.message;
                } else if (settings.failureMsg && settings.failureMsg != '') {
                	// 前台指定的错误信息
                    message = settings.failureMsg;
                }
                if (result.exceptionMessage && result.exceptionMessage != '') {
                	// 有异常信息
//                    Core.ExceptionWindow(message, result.exceptionMessage, function () {
//	                    if(typeof settings.failure == "function"){
//	                    	settings.failure(result);
//	                	}
//                    });
                } else {
//                	Core.alertError(message, '错误', function () {
//	                    if(typeof settings.failure == "function"){
//	                    	settings.failure(result);
//	                	}
//                	});
                }
            }
        },
        error: function (response, options) {
//            if (waiting != null) {
//            	mini.hideMessageBox(waiting);
//            }
            if (typeof response.getResponseHeader == "function") {
                // 得到response里面的头信息
                var sessionStatus = response.getResponseHeader("session-status");
                if (sessionStatus == "timeout") {
                	// 登陆超时
//                	Core.alertWarning('<span style="color:red"><b>登录超时，请重新登录！</b></span>', '提示', function () {
//                        var win = window;
//                        while (win != win.parent) {
//                            win = win.parent;
//                        }
//                        win.location = ctx;
//                    });
                    return false;
                } else if (sessionStatus == "pagenotfind") {
                	// 页面不存在
//                	Core.alertWarning('<span style="color:red"><b>很抱歉，您要访问的页面不存在！</b></span>', '提示', function () {});
                    return false;
                } else {
                	if (response.status == 405) {
//                		Core.alertError("您请求的地址在服务器中不存在！", '', function (){
//                			Core.CloseWindow();
//                		});
                	} else {
//                		Core.alertServerError();
                	}
                }
            }
        }
	};
	$("#"+settings.formId).ajaxSubmit(options);
};

/**
 * 取得分页jqx.dataAdapter
 * 
 * @param {String}
 *            settings.gridId 主键ID，必须
 * @param {String}
 *            settings.url 请求地址，必须
 * @param {Object}
 *            settings.params 参数对象，必须
 * @param {Object}
 *            settings.async 是否异步提交
 * @param {String}
 *            settings.cache 是否缓存
 */
Core.getPagingDataAdapter = function (settings) {
    // 参数对象
    var params = settings.params === undefined ? null : settings.params;
    var async = settings.async === undefined ? true : settings.async;
    var cache = settings.cache === undefined ? false : settings.cache;
    var dataType = settings.dataType === undefined ? "json" : settings.dataType;
    var type = settings.type === undefined ? "GET" : settings.type;
    var contentType = settings.contentType === undefined ? "application/x-www-form-urlencoded; charset=UTF-8" : settings.contentType;

    var source =
    {
    	 datatype: dataType,
         url: settings.url,
         root: 'dataList',
         cache: cache,
         data: params,
         type: type,
         contentType: "application/json",
		 beforeprocessing: function(data){		
			if (data != null)
			{
				source.totalrecords = data.totalRows;
			}
		 },
		 sort: function(){
			$("#"+settings.gridId).jqxGrid('updatebounddata', 'sort');
		 },
         filter: function()
         {
            // update the grid and send a request to the server.
            $("#"+settings.gridId).jqxGrid('updatebounddata', 'filter');
         }
    };
    var dataAdapter = new $.jqx.dataAdapter(source, {
		loadError: function(xhr, status, error){
			if (typeof xhr.getResponseHeader == "function") {
				if (xhr.status == 320) {
            		if (window.location.pathname == ctx + "/pages/login.jsp" || window.location.pathname == ctx + "/") {

            		} else {
            			Core.showTimeoutDiv();

            		}
            	}
            }
		},
		contentType : contentType,
        async: async,
        beforeSend: function(XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("authorization","Bearer "+($.cookie('PLATFORM_IDC_TOKEN') == 'undefined'?"":$.cookie('PLATFORM_IDC_TOKEN')));
        }
	});
    return dataAdapter;
};

/**
 * 服务器/网络错误信息MiniUI弹出窗口。
 */
Core.alertServerError = function () {
	// 窗口标题
    mini.showMessageBox({
        title: "错误",
        iconCls: "mini-messagebox-error",
        buttons: ["ok"],
        message: "<span style='color:red'><b>访问发生错误，请检查您的网络或重新登陆！</b></span>",
        callback: function (action) {
//            if (callback) { // 回调方法
//                callback(action);
//            }
        }
    });
};

/**
 * 显示异常信息的窗口
 * 
 * @param message
 *            异常信息
 * @param exceptionMsg
 *            异常详细信息
 */
Core.ExceptionWindow = function (message, exceptionMessage, callback) {
    var _message = message === undefined ? '发生异常!' : message == '' ? '发生异常!' : message,
     _exceptionMessage = exceptionMessage === undefined ? '发生异常!' : exceptionMessage == '' ? '发生异常!' : exceptionMessage;
	_message = exceptionMessage === undefined ? _message : exceptionMessage == '' ? _message : _exceptionMessage;
	mini.showMessageBox({
        title: "错误",
        buttons: ["ok"],
        iconCls: "mini-messagebox-error",
        message: "<span style='color:red'><b>访问发生错误，请检查您的网络或重新登陆！</b></span>",
        showModal: false,
        callback: function (action) {
            if (callback) { // 回调方法
                callback(action);
            }
        }
    });
};

/**
 * 关闭MiniUI窗口。
 * 
 * @param action 返回值
 */
Core.CloseWindow = function (action) {
    if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
    else window.close();            
};

/**
 * jqwidget自定义Confirm窗口
 * 
 * @param {String}
 *            settings.title 窗口标题
 * @param {String}
 *            settings.message 显示信息
 * @param {String}
 *            settings.okBtnName 确定按钮名称
 * @param {String}
 *            settings.cancelBtnNm 取消按钮名称
 * @param {String}
 *            settings.type 提示类型(info, warning, error)
 * @param {Function}
 *            settings.confirmCallback 确定按钮回调函数
 * @param {Function}
 *            settings.cancelCallback 取消按钮回调函数
 */
Core.confirm = function (settings) {
	
    var title = settings.title === undefined ? "请选择" : settings.title,
    	message = settings.message === undefined ? "" : settings.message,
    	okBtnNm = settings.yes === undefined ? "确定" : settings.yes,
    	cancelBtnNm = settings.no === undefined ? "取消" : settings.no,
	    type = settings.type === undefined ? "info" : settings.type;
        width = settings.width === undefined ? 300 : settings.width;
	    height = settings.height === undefined ? 130 : settings.height;
   
	$('<div id="confirmWindow">'+
		'<div id="customWindowHeader">'+
		  '<span id="captureContainer" style="float: left">'+title+'</span>'+
		'</div>'+
		'<div id="customWindowContent" style="overflow: hidden">'+
			'<div style="width:100%;height:70px;line-height:60px;position:relative;top:0px;left:0px;"><i class="icons-32-blue icon-help-circled"></i><font style="position:absolute;left:45px;">'+message+'</font></div>'+
			'<div style="float: right; margin-top: 0px;">'+
				' <input type="button" style="cursor:pointer;margin-right: 5px" value=' + okBtnNm + ' id="okButton" />'+
				' <input type="button" style="cursor:pointer" value=' + cancelBtnNm + ' id="cancelButton" />'+
			'</div>'+
		'</div>'+
		'</div>').appendTo($("body"));

	$("#confirmWindow .jqx-window-modal").css("width","1200px");
	
	var windowW = $(window).width(); 
	var windowH = $(window).height();
	 $('#confirmWindow').jqxWindow({ 
		 position: { x: (windowW-width)/2, y: (windowH-height)/2 },
		 width: width,
         height: height, 
         resizable: false,
         isModal: true, 
         closeButtonAction:'close',
         theme:currentTheme,
         initContent: function () {
             $('#okButton').jqxButton({ width: '60px', disabled: false , theme: currentTheme});
             $('#cancelButton').jqxButton({ width: '60px', disabled: false, theme: currentTheme });
             $('#okButton').focus();
         }
     });

	$('#confirmWindow').find('#okButton').click(function() {
		$('#confirmWindow').jqxWindow('close');
		$('#confirmWindow').jqxWindow('destroy');
		if (settings.confirmCallback) {
			settings.confirmCallback();	
		}
	});
	
	$('#confirmWindow').find('#cancelButton').click(function() {
		$('#confirmWindow').jqxWindow('close');
		$('#confirmWindow').jqxWindow('destroy');
		if (settings.cancelCallback) {
			settings.cancelCallback();	
		}
	});
};

/**
 * jqwidget自定义alert窗口
 * 
 * @param {String}
 *            settings.title 窗口标题
 * @param {String}
 *            settings.message 显示信息
 * @param {String}
 *            settings.okBtnName 确定按钮名称
 * @param {String}
 *            settings.cancelBtnNm 取消按钮名称
 * @param {String}
 *            settings.type 提示类型(info, warning, error)
 * @param {Function}
 *            settings.callback 确定按钮回调函数
 */
Core.alert = function (settings) {
	var msgWidth = 0;
	var msg = settings.message.split('<br/>');
	for(var i=0;i<msg.length;i++){
		var msgDetail = msg[i].split('<p');
		for(var j=0;j<msgDetail.length;j++){
			var content = msgDetail[j].split('>');
			if(content.length==1){
				var msgLength = msgDetail[j].replace(/[^\x00-\xff]/g, 'xx').length+10;
				msgWidth = msgLength > msgWidth ? msgLength : msgWidth;
			}else{
				var msgLength = content[1].replace(/[^\x00-\xff]/g, 'xx').length+10;
				msgWidth = msgLength > msgWidth ? msgLength : msgWidth;
			}
		}
	}
	var widthAuto = msgWidth*7<280?280:msgWidth*7+30;
	
	var title = settings.title === undefined ? "提示" : settings.title,
	    message = settings.message === undefined ? "" : settings.message,
	    type = settings.type === undefined ? "info" : settings.type;
		width = settings.width === undefined ? widthAuto : settings.width;
		height = settings.height === undefined ? 130 : settings.height;
	   
		var typeIcon = '<i class="icons-32-blue icon-info-circled"></i>';
		if (type == "warning") {
			typeIcon = '<i class="icons-32-yellow icon-attention-1"></i>';
		}else if (type == "error") {
			typeIcon = '<i class="icons-32-red icon-cancel-circled"></i>';
		} else 	if (type == "info") {
			typeIcon = '<i class="icons-32-blue icon-info-circled"></i>';
		}
		
		// 判断body中是否存在了alertWindow
		if($("#alertWindow").length > 0){
			$('#alertWindow').jqxWindow('destroy');
		}

		$('<div id="alertWindow">'+
			'<div id="customWindowHeader">'+
			  '<span id="captureContainer" style="float: left">'+title+'</span>'+
			'</div>'+
			'<div id="customWindowContent" style="overflow: hidden">'+
				'<div style="width:100%;height:70px;line-height:60px;position:relative;top:0px;left:0px;">'
				+ typeIcon + 
				'<font style="position:absolute;left:45px;">'+ message + '</font></div>'+
				'<div style="position: absolute;right: 0px;">'+
					' <input type="button" style="cursor:pointer;margin-right: 5px" value="确定" id="okButton" />'+
				'</div>'+
			'</div>'+
			'</div>').appendTo($("body"));
		
		var windowW = $(window).width(); 
		var windowH = $(window).height();
		 $('#alertWindow').jqxWindow({
			 position: { x: (windowW-width)/2, y: (windowH-height)/2 },
			 width: width,
	         height: height, 
	         resizable: false,
	         isModal: true,
	         theme:currentTheme,
	         closeButtonAction:'close',
	         initContent: function () {
	             $('#okButton').jqxButton({ width: '60px', disabled: false, theme: currentTheme});
	             $('#okButton').focus();
	         }
	     });

		$('#alertWindow').find('#okButton').click(function() {
			$('#alertWindow').jqxWindow('destroy');
//			$('#alertWindow').jqxWindow('close');
			if (settings.callback) {
				settings.callback();	
			}
		});
};



/**
 * 系统提示信息MiniUI窗口。
 */
Core.alertInfo = function (message, title, callback) {
	// 窗口标题
	if (title == undefined || title == null || title == "") {
		title = "提示";
	}
    mini.showMessageBox({
        title: title,
        iconCls: "mini-messagebox-info",
        buttons: ["ok"],
        message: message,
        callback: function (action) {
            if (callback) { // 回调方法
                callback(action);
            }
        }
    });
};

/**
 * 系统警告信息MiniUI窗口。
 */
Core.alertWarning = function (message, title, callback) {
	// 窗口标题
	if (title == undefined || title == null || title == "") {
		title = "警告";
	}
    mini.showMessageBox({
        title: title,
        iconCls: "mini-messagebox-warning",
        buttons: ["ok"],
        message: message,
        callback: function (action) {
            if (callback) { // 回调方法
                callback(action);
            }
        }
    });
};

/**
 * 系统错误信息MiniUI窗口。
 */
Core.alertError = function (message, title, callback) {
	// 窗口标题
	if (title == undefined || title == null || title == "") {
		title = "错误";
	}
    mini.showMessageBox({
        title: title,
        iconCls: "mini-messagebox-error",
        buttons: ["ok"],
        message: "<span style='color:red'><b>" + message + "</b></span>",
        callback: function (action) {
            if (callback) { // 回调方法
                callback(action);
            }
        }
    });
};

/**
 * 附件文件下载。
 */
Core.attachmentDownload = function (attachmentSid) {
	if (attachmentSid == undefined || attachmentSid == null || attachmentSid == "") {
		return;
	}
	if (isNaN(attachmentSid)) {
		return;
	}
	var url= ctx + "/download/" + attachmentSid;
	window.location=url;
};

/**
 * map转数组。
 * 
 * @param {Map}map
 *            map对象
 * @return 数组
 */
Core.map2Ary = function (map) {
    var list = [];
    for (var key in map) {
        list.push([key, map[key]]);
    }
    return list;
};
/**
 * 获取map中的值。
 * 
 * @param {String}value
 *            要渲染的值
 * @param {Map}mapping
 *            Map对象
 * @param {String}defaultText
 *            没有对应的key时的默认值
 * @return {String}对应的value值
 */
Core.map = function (value, mapping, defaultText) {
    return mapping[value] || (defaultText === undefined || defaultText === null ? value : defaultText);
};

/**
 * 会话过期显示DIV
 */
Core.showTimeoutDiv = function () {
	// 得到最顶层窗口
	var win = Core.getWin();
	var div = "<div id='notification'><span>会话过期，请注意未保存的数据，请<u><a href='#' onclick='Core.goToLogin();'>点击这里</a></u>重新登录。</span></div>";
	if (win.document.getElementById("notification") == null) {
		Core.getWin().document.body.innerHTML += div;
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

Core.openWindow = function (url,name,iWidth,iHeight) {
	//window.screen.height获得屏幕的高，window.screen.width获得屏幕的宽  
	var iTop = (window.screen.height-30-iHeight)/2;       //获得窗口的垂直位置;  
	var iLeft = (window.screen.width-10-iWidth)/2;        //获得窗口的水平位置;  
	window.open(url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,modal=yes,location=no,status=no');  
};

/** 模态窗口方法 */
Core.openModalWindow = function (url,param,iWidth,iHeight,e) {
	// 相对于浏览器的居中位置   
	var iTop = (window.screen.height-30-iHeight)/2;       //获得窗口的垂直位置;  
	var iLeft = (window.screen.width-10-iWidth)/2;        //获得窗口的水平位置;  
	var result= window.showModalDialog(url,param,'dialogWidth='+iWidth+'px;dialogHeight='+iHeight+'px;dialogLeft:'+iLeft+'px;dialogTop:'+iTop+'px','toolbar=no;menubar=no;center=yes;scrollbars=auto;resizeable=no;location=no;status=no');  
	return result;
};

/**
 * JSON字符串Object转换
 */
Core.parseJSON = function (jsonStr) {

	jsonStr = jsonStr.replace(/\\/g,"\\\\");

	var obj =  jQuery.parseJSON(jsonStr);

	return obj;
};

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

/**
 * 禁止鼠标右键
 */
/*开发阶段先屏蔽
document.oncontextmenu = function() { 
	return false; 
};
*/

/**
 * 禁止back space键
 */
/*
document.body.onkeydown = function(event) {
	if(event.keyCode==8) {
		return false;
	}
};
*/

/**
 * 禁止点击back space按钮
 */
//javascript:window.history.forward(1); 

/**
 * 扩展基础类 判断以什么结尾
 *//*
String.prototype.endsWith = function (suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};
*//**
 * 扩展基础类 得到字符串的长度，包括中文和英文
 *//*
String.prototype.charlen = function () {
    var arr = this.match(/[^\x00-\xff]/ig);
    return this.length + (arr == null ? 0 : arr.length);
}
*//**
 * 扩展基础类 字符串首尾去空格
 *//*
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

*//**
 * 扩展基础类 字符串包含字符串判断
 *//*
String.prototype.contains = function (sub) {
    return this.indexOf(sub) != -1;
}*/

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
/**
 * 根据名称获取系统配置
 * 
 * @returns 配置值
 */
Core.getSysconfig = function (configKey) {
	var configValue="";
	Core.AjaxRequest({
		type:'get',
	 	url : ws_url + "/rest/configs/getProp/"+configKey,
		async : false,
		callback : function(data) {

			configValue = data[0].configValue;
		}
	});
	return configValue;
};
