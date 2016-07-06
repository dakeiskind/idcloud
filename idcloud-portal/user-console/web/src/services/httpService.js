define(["jquery","./messageBoxService",'layer','cookie','base64'], function($,messageBox,layer) {
	 var http = function(){
		   	var me = this;
			this.flag = true;
		 	//this.ws_url = "http://www.dmcloud.com.cn:8112/idcloud-rest";
		 	this.ws_url = "http://localhost:8888/idcloud-rest";
		 	/** Ajax请求方法 */
			 this.AjaxRequest = function(settings){
	        	 // 参数对象
	            var params = settings.params === undefined ? null : settings.params,
	            showWaiting = settings.showWaiting === undefined ? true : settings.showWaiting,
	            showMsg = settings.showMsg === undefined ? true : settings.showMsg,
	            showTimeout = settings.showTimeout === undefined ? true : settings.showTimeout,
	            async = settings.async === undefined ? true : settings.async,
	            dataType = settings.dataType === undefined ? "json" : settings.dataType, 
	            type = settings.type === undefined ? "post" : settings.type,
	            cache = settings.cache === undefined ? false : settings.cache,
	            timeout = settings.timeout === undefined ? 60 * 1000 : settings.timeout,
	            waiting = null;
				 var index = null;
	            if (showWaiting) {
					waiting = 1;
					index = layer.load(1, {
						shade: [0.1,'#000']
					});
	            }
				if(type.toLowerCase()=="get" && params instanceof Object){
				}else{
					params = JSON.stringify(params);
				}
	            // 发送请求
	            jQuery.ajax({
	            	contentType : "application/json; charset=UTF-8",
	                url: me.ws_url+settings.url,
	                type: type,
	                data: params,
	                async: async,
	                dataType: dataType,
	                beforeSend: function(XMLHttpRequest) {
			            XMLHttpRequest.setRequestHeader("authorization","Bearer "+($.cookie('IDC_TOKEN') == 'undefined'?"":$.cookie('IDC_TOKEN')));
			        },
	                cache : cache,
	                timeout: timeout,
	                success: function (result, textStatus) {
						// 清除掉loading层
						if (waiting != null) {
							layer.close(index);
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

                        // 返回值200
						if(result.code == "200"){
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
										messageBox.msgNotification({
											message:result.message,
											type:"success"
										});
									}else{
										messageBox.msgNotification({
											message:result.message,
											type:"error"
										});
									}
								}
								// callback给调用
								if(result.data != undefined){
									if (settings.callback) {
										settings.callback(result.data,result.message);
									}
								}else{
									// 没有返回data,将状态返回给用户
									if (settings.callback) {
										settings.callback(result.status,result.message);
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
						// 清除掉loading层
						if (waiting != null) {
							layer.close(index);
						}
	                    if(response.status == 500){
							 if(settings.showMsg){
								 messageBox.msgNotification({
									 message:"服务器内部错误，请联系管理员.",
									 type:"error"
								 });
							 }else{
								 if (settings.failure) {
									 settings.failure(options);
								 }
							 }

                    	}else if (response.status == 405) {
                    		alert(405);
                    	}if (response.status == 404) {
                    		messageBox.msgNotification({
	                			message:"对不起该链接不存在.",
	                			type:"wanring"
	                		 });
                    	} else if(response.status == 403) {
                    		alert(403);
                    	} else if (response.status == 320) {
							console.log("320 会话过期！");
							if(me.flag){
								me.flag = false;
								// 清空cookie
								$.removeCookie('IDC_TOKEN', {path: "/"});
								$.removeCookie('USER_SID', {path: "/"});
								messageBox.alert({
									title:"错误消息",
									width:330,
									message:"当前的会话已过期，请重新登录。",
									callback:function(){
										// 保存当前url，并base64加密
										var preUrl = $.base64.btoa(window.location.href);
										window.location.href = "http://"+window.location.host+"/login.html?callback="+preUrl;
									}
								});

							}
                    	}
	                }
	            });
	        };

		 	this.clearCoverlayout = function(){
				$("body").find("#cover_div").remove();
			};
	        
	        /** Ajax请求 -- 带附件的提交 */
	        
	        /** jqxgrid分页方法 */
	        this.getPagingDataAdapter = function (settings) {
	            // 参数对象
	            var params = settings.params === undefined ? null : settings.params;
	            var async = settings.async === undefined ? true : settings.async;
	            var cache = settings.cache === undefined ? false : settings.cache;
	            var dataType = settings.dataType === undefined ? "json" : settings.dataType;
	            var type = settings.type === undefined ? "GET" : settings.type;
	            var contentType = settings.contentType === undefined ? "application/x-www-form-urlencoded; charset=UTF-8" : settings.contentType;

	            var source ={
	            	 datatype: dataType,
	                 url: me.ws_url+settings.url,
	                 root: 'dataList',
	                 cache: cache,
	                 data: params,
	                 type: type,
	        		 beforeprocessing: function(data){		
	        			if (data != null)
	        			{
	        				source.totalrecords = data.totalRows;
	        			}
	        		 },
	        		 sort: function(){
	        			$("#"+settings.gridId).jqxGrid('updatebounddata', 'sort');
	        		 }
	            };
	            var dataAdapter = new $.jqx.dataAdapter(source, {
	        		loadError: function(xhr, status, error){
	        			console.log(xhr);
	        			if (typeof xhr.getResponseHeader == "function") {
	        				if (xhr.status == 320) {
	                    		if (window.location.pathname == ctx + "/pages/login.jsp" || window.location.pathname == ctx + "/") {
	                    			return;
	                    		} else {
	                    			Core.showTimeoutDiv();
	                				return;
	                    		}
	                    	}
	                    }
	        		},
	        		contentType : contentType,
	                async: async,
	                beforeSend: function(XMLHttpRequest) {
	                    XMLHttpRequest.setRequestHeader("PLATFORM","PLATFORM");
	                },
	        	});
	            return dataAdapter;
	        };
	        
	        
	 }; 
	 return new http();

});