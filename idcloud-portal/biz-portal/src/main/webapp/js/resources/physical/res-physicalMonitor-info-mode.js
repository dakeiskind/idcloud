var physicalMonitorDetailModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    
	    this.popPhysicalDetailInfoWindow = function(objSid,objType){
		    	//　初始化基本信息
		    	me.initBasicInfo(objSid,objType);
		    	// 初始化配置信息
		    	//me.initConfigInfo(objSid,objType);
	    };
	    
	    this.initPopWindow = function(){
	    	$("#switchWindows").jqxWindow({
	              width: 800, 
	              height:500,
	              theme:currentTheme,  
	              resizable: false,  
	              isModal: true, 
	              autoOpen: false, 
	              cancelButton: $("#switch-close-button"), 
	              modalOpacity: 0.3,
	              initContent:function(){
	                // 初始化新增用户页面组件
	              $("#switch-close-button").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
	              }
	     });
	    };
	    
	    // 初始化基本信息
	    this.initBasicInfo = function(objSid,objType) {
	    		 Core.AjaxRequest({
		 			url : ws_url + "/rest/ip/find/physicalInfo/"+objSid+"/"+objType,
		 			type:'get',
		 			async:false,
		 			callback : function (data) {
		 				if("SW"==objType){
							var windowW = $(window).width();
						    var windowH = $(window).height(); 
						    $("#switchWindows").jqxWindow({ position: {x: (windowW-800)/2, y: (windowH-500)/2 } });
						    $("#switchWindows").jqxWindow('open');
						    var switchDetail = new switchDetailModel();
						    switchDetail.popSwitchDetailInfoWindow(data);
		 				}
		 			}
	     		});
		};
};

  