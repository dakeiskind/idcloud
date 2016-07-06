var addResPoolModel = function(){
	var me = this;
    
    // 验证
    this.initValidator = function(){
    	// 添加主机资源池验证规则
		$('#addHostRespoolForm').jqxValidator({
	        rules: [  
	                  { input: '#add-resPoolName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-resPoolName', message: '主机名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
	                  
	                  { input: '#add-operationHandler', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-operationHandler', message: '主机IP不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' }
	                 
	               ]
		});
	    	
		// 主机资源池验证成功
		$('#addHostRespoolForm').on('validationSuccess', function (event) {
			try {
				 var pool = Core.parseJSON($("#addHostRespoolForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/pools/host/create",
	 				params :pool,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					setPoolTreeValue();
						$("#addHostRespoolWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#addHostRespoolWindow").jqxWindow('close');
	 			    }
	 			});
	    	} catch (e) {
	    	   if(e.name == "SyntaxError"){
	    		   Core.alert({
 						title: "提示",
 						type:"error",
 						message: "页面存在特殊字符，请检查！"
 					});
	    	   }
	    	}
			
	     });
    };
    
    // 验证存储资源池
    this.initStorageValidator = function(){
    	// 添加存储资源池验证规则
		$('#addStorageRespoolForm').jqxValidator({
	        rules: [  
	                  { input: '#add-storage-resPoolName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-storage-resPoolName', message: '主机名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
	                  
	                  { input: '#add-storage-operationHandler', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-storage-operationHandler', message: '主机IP不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' }
	                 
	               ]
		});
	    	
		// 存储资源池验证成功
		$('#addStorageRespoolForm').on('validationSuccess', function (event) {
			try {
				 var pool = Core.parseJSON($("#addStorageRespoolForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/pools/storage/create",
		 				params :pool,
		 				callback : function (data) {
		 					// 刷新Tree
		 					setPoolTreeValue();
							$("#addStorageRespoolWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
							$("#addStorageRespoolWindow").jqxWindow('close');
		 			    }
		 			});
	    	} catch (e) {
	    	   if(e.name == "SyntaxError"){
	    		   Core.alert({
						title: "提示",
						type:"error",
						message: "页面存在特殊字符，请检查！"
					});
	    	   }
	    	}
			
	     });
    };
    
    // 验证Vlan
    this.initVlanValidator = function(){
		
		$('#addVlanRespoolForm').jqxValidator({
	        rules: [  
	                  { input: '#add-resPoolName-vlan', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-resPoolName-vlan', message: '主机名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
	                  
	                  { input: '#add-operationHandler-vlan', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-operationHandler-vlan', message: '操作处理器不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' },
	                  { input: '#add-allocationThreshold-vlan', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
	          	  			if(/[^\d]/g.test(input.val())){
	            	  				return false;
	            	  			}else{
	            	  				return true;
	            	  			}
	            	  		}
		                  }
	                 
	               ]
		});
	    	
		// 新增Vlan池验证成功
		$('#addVlanRespoolForm').on('validationSuccess', function (event) {
			try {
				var pool = Core.parseJSON($("#addVlanRespoolForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/pools/vlan/create",
	 				params :pool,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					setNetworkTreeValue();
						$("#addVlanRespoolWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#addVlanRespoolWindow").jqxWindow('close');
	 			    }
	 			});
	    	} catch (e) {
	    	   if(e.name == "SyntaxError"){
	    		   Core.alert({
						title: "提示",
						type:"error",
						message: "页面存在特殊字符，请检查！"
					});
	    	   }
	    	}
			
	     });
    };
    
 // 验证Ip
    this.initIpValidator = function(){
		
		$('#addIpRespoolForm').jqxValidator({
	        rules: [  
	                  { input: '#add-resPoolName-ip', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-resPoolName-ip', message: 'ip名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
	                  
	                  { input: '#add-operationHandler-ip', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-operationHandler-ip', message: '操作处理器不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' }
	                 
	               ]
		});
	    	
		// 新增IP池验证成功
		$('#addIpRespoolForm').on('validationSuccess', function (event) {
			try {
				 var pool = Core.parseJSON($("#addIpRespoolForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/pools/ip/create",
	 				params :pool,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					setNetworkTreeValue();
						$("#addIpRespoolWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#addIpRespoolWindow").jqxWindow('close');
	 			    }
	 			});
	    	} catch (e) {
	    	   if(e.name == "SyntaxError"){
	    		   Core.alert({
						title: "提示",
						type:"error",
						message: "页面存在特殊字符，请检查！"
					});
	    	   }
	    	}
			
	     });
    };
    
    // 新增sharepoint资源池
    this.initSharePointValidator = function(){
    	// 添加新增sharepoint资源池规则
		$('#addSharepointRespoolForm').jqxValidator({
	        rules: [  
	                  { input: '#add-resPoolName-sharepoint', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-resPoolName-sharepoint', message: '主机名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
	                  
	                  { input: '#add-operationHandler-sharepoint', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-operationHandler-sharepoint', message: '主机IP不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' }
	                 
	               ]
		});
	    	
		// 主机资源池验证成功
		$('#addSharepointRespoolForm').on('validationSuccess', function (event) {
			try {
				var pool = Core.parseJSON($("#addSharepointRespoolForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/pools/sharepoint/create",
	 				params :pool,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					setSpExTreeValue();
						$("#addSharepointRespoolWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#addSharepointRespoolWindow").jqxWindow('close');
	 			    }
	 			});
	    	} catch (e) {
	    	   if(e.name == "SyntaxError"){
	    		   Core.alert({
						title: "提示",
						type:"error",
						message: "页面存在特殊字符，请检查！"
					});
	    	   }
	    	}
			
	     });
    };
    
 // 新增Exchange资源池
    this.initExchangeValidator = function(){
    	// 添加新增sharepoint资源池规则
		$('#addExchangeRespoolForm').jqxValidator({
	        rules: [  
	                  { input: '#add-resPoolName-exchange', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-resPoolName-exchange', message: '主机名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
	                  
	                  { input: '#add-operationHandler-exchange', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-operationHandler-exchange', message: '主机IP不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' }
	                 
	               ]
		});
	    	
		// 主机资源池验证成功
		$('#addExchangeRespoolForm').on('validationSuccess', function (event) {
			try {
				 var pool = Core.parseJSON($("#addExchangeRespoolForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/pools/exchange/create",
	 				params :pool,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					setSpExTreeValue();
						$("#addExchangeRespoolWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#addExchangeRespoolWindow").jqxWindow('close');
	 			    }
	 			});
	    	} catch (e) {
	    	   if(e.name == "SyntaxError"){
	    		   Core.alert({
						title: "提示",
						type:"error",
						message: "页面存在特殊字符，请检查！"
					});
	    	   }
	    	}
			
	     });
    };
    
    // 初始化主机弹出window
	this.initPopWindow = function(){   	
		  $("#addHostRespoolWindow").jqxWindow({
		        width: 680, 
		        height:288,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addHostRespoolCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#add-resPoolName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
		        	$("#add-conversionFormula").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
		        	$("#add-allocationThreshold").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
		        	$("#add-operationHandler").jqxInput({placeHolder: "", height: 22, width: 420, minLength: 1,theme:currentTheme});
		        	$("#add-pool-description").jqxInput({placeHolder: "", height: 60, width: 420, minLength: 1,theme:currentTheme});
		        	
		        	var respool = new codeModel({width:120,autoDropDownHeight:true});
		        	respool.getCommonCode("add-status","RESOURCE_POOL_STATUS");
		        	respool.getCommonCode("add-perfLevel","PERF_LEVEL");
		        	respool.getCommonCode("add-allocationPolicy","ALLOCATION_POLICY");
		        	respool.getCommonCode("add-pool-virtualPlatformType","VIRTUAL_PLATFORM_TYPE");
		        	respool.getCommonCode("add-resPoolType","RESOURCE_POOL_TYPE");
		        	
			    	$("#addHostRespoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addHostRespoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
		  
		  
	 };
	 
	// 初始化存储弹出window
		this.initStoragePopWindow = function(){   	
			  $("#addStorageRespoolWindow").jqxWindow({
			        width: 680, 
			        height:288,
			        theme:currentTheme,  
			        resizable: false,  
			        isModal: true, 
			        autoOpen: false, 
			        cancelButton: $("#addResStoragePoolCancel"), 
			        modalOpacity: 0.3,
			        initContent:function(){
			        	// 初始化新增用户页面组件
			        	$("#add-storage-resPoolName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
			        	$("#add-storage-conversionFormula").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
			        	$("#add-storage-allocationThreshold").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
			        	$("#add-storage-operationHandler").jqxInput({placeHolder: "", height: 22, width: 420, minLength: 1,theme:currentTheme});
			        	$("#add-storage-description").jqxInput({placeHolder: "", height: 60, width: 420, minLength: 1,theme:currentTheme});
			        	
			        	var respool = new codeModel({width:120,autoDropDownHeight:true});
			        	respool.getCommonCode("add-storage-status","RESOURCE_POOL_STATUS");
			        	respool.getCommonCode("add-storage-perfLevel","PERF_LEVEL");
			        	respool.getCommonCode("add-storage-storageType","STORAGE_TYPE");
			        	respool.getCommonCode("add-storage-allocationPolicy","ALLOCATION_POLICY");
			        	
				    	$("#addResStoragePoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
				    	$("#addResStoragePoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			        }
			    });
		 };
	 
	 
	 // 初始化Vlan弹出window
	this.initPopVlanWindow = function(){   	
		$("#addVlanRespoolWindow").jqxWindow({
	        width: 680, 
	        height:288,
	        theme:currentTheme,  
	        resizable: false,  
	        isModal: true, 
	        autoOpen: false, 
	        cancelButton: $("#addVlanRespoolCancel"), 
	        modalOpacity: 0.3,
	        initContent:function(){
	        	// 初始化新增用户页面组件
	        	$("#add-resPoolName-vlan").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
	        	$("#add-conversionFormula-vlan").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
	        	$("#add-allocationThreshold-vlan").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
	        	$("#add-operationHandler-vlan").jqxInput({placeHolder: "", height: 22, width: 420, minLength: 1,theme:currentTheme});
	        	$("#add-description-vlan").jqxInput({placeHolder: "", height: 60, width: 420, minLength: 1,theme:currentTheme});
	        	
	        	var respool = new codeModel({width:120,autoDropDownHeight:true});
	        	respool.getCommonCode("add-status-vlan","RESOURCE_POOL_STATUS");
	        	respool.getCommonCode("add-perfLevel-vlan","PERF_LEVEL");
	        	respool.getCommonCode("add-allocationPolicy-vlan","ALLOCATION_POLICY");
	        	respool.getCommonCode("add-vlanType-vlan","VLAN_TYPE");
		    	
		    	$("#addVlanRespoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		    	$("#addVlanRespoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
	        }
	    });
		  
	 };
	 
	 // 初始化Ip弹出window
		this.initPopIpWindow = function(){   	
			  				  
			  $("#addIpRespoolWindow").jqxWindow({
			        width: 680, 
			        height:300,
			        theme:currentTheme,  
			        resizable: false,  
			        isModal: true, 
			        autoOpen: false, 
			        cancelButton: $("#addIpRespoolCancel"), 
			        modalOpacity: 0.3,
			        initContent:function(){
			        	// 初始化新增用户页面组件
			        	$("#add-resPoolName-ip").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
			        	$("#add-conversionFormula-ip").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
			        	$("#add-allocationThreshold-ip").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
			        	$("#add-floatingIpPoolName-ip").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
			        	$("#add-operationHandler-ip").jqxInput({placeHolder: "", height: 22, width: 420, minLength: 1,theme:currentTheme});
			        	$("#add-description-ip").jqxInput({placeHolder: "", height: 60, width: 420, minLength: 1,theme:currentTheme});
			        	
			        	var respool = new codeModel({width:120,autoDropDownHeight:true});
			        	respool.getCommonCode("add-status-ip","RESOURCE_POOL_STATUS");
			        	respool.getCommonCode("add-perfLevel-ip","PERF_LEVEL");
			        	respool.getCommonCode("add-allocationPolicy-ip","ALLOCATION_POLICY");
			        	respool.getCommonCode("add-ipCategory-ip","IP_CATEGORY");
			        	respool.getCommonCode("add-virtualPlatformType-ip","VIRTUAL_PLATFORM_TYPE");
				    	
				    	$("#addIpRespoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
				    	$("#addIpRespoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			        }
			    });
			  
		 };
		 
		// 初始化主机弹出window
			this.initPopSharepointWindow = function(){   	
				  $("#addSharepointRespoolWindow").jqxWindow({
				        width: 680, 
				        height:288,
				        theme:currentTheme,  
				        resizable: false,  
				        isModal: true, 
				        autoOpen: false, 
				        cancelButton: $("#addSharepointRespoolCancel"), 
				        modalOpacity: 0.3,
				        initContent:function(){
				        	// 初始化新增用户页面组件
				        	$("#add-resPoolName-sharepoint").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
				        	$("#add-conversionFormula-sharepoint").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
				        	$("#add-allocationThreshold-sharepoint").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
				        	$("#add-operationHandler-sharepoint").jqxInput({placeHolder: "", height: 22, width: 420, minLength: 1,theme:currentTheme});
				        	$("#add-pool-description-sharepoint").jqxInput({placeHolder: "", height: 60, width: 420, minLength: 1,theme:currentTheme});
				        	
				        	var respool = new codeModel({width:120,autoDropDownHeight:true});
				        	respool.getCommonCode("add-status-sharepoint","RESOURCE_POOL_STATUS");
				        	respool.getCommonCode("add-perfLevel-sharepoint","PERF_LEVEL");
				        	respool.getCommonCode("add-allocationPolicy-sharepoint","ALLOCATION_POLICY");
				        	
					    	$("#addSharepointRespoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
					    	$("#addSharepointRespoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
				        }
				    });
			 };
			 
			// 初始化主机弹出window
				this.initPopExchangeWindow = function(){   	
					  $("#addExchangeRespoolWindow").jqxWindow({
					        width: 680, 
					        height:288,
					        theme:currentTheme,  
					        resizable: false,  
					        isModal: true, 
					        autoOpen: false, 
					        cancelButton: $("#addExchangeRespoolCancel"), 
					        modalOpacity: 0.3,
					        initContent:function(){
					        	// 初始化新增用户页面组件
					        	$("#add-resPoolName-exchange").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
					        	$("#add-conversionFormula-exchange").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
					        	$("#add-allocationThreshold-exchange").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
					        	$("#add-operationHandler-exchange").jqxInput({placeHolder: "", height: 22, width: 420, minLength: 1,theme:currentTheme});
					        	$("#add-pool-description-exchange").jqxInput({placeHolder: "", height: 60, width: 420, minLength: 1,theme:currentTheme});
					        	
					        	var respool = new codeModel({width:120,autoDropDownHeight:true});
					        	respool.getCommonCode("add-status-exchange","RESOURCE_POOL_STATUS");
					        	respool.getCommonCode("add-perfLevel-exchange","PERF_LEVEL");
					        	respool.getCommonCode("add-allocationPolicy-exchange","ALLOCATION_POLICY");
					        	
						    	$("#addExchangeRespoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
						    	$("#addExchangeRespoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
					        }
					    });
				 };
};

// 弹出新增主机池window
function popAddRespoolWindow(){
	// 设置弹出框位置
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 清除原来新增资源池的数据
  	$("#add-resPoolName").val("");
  	$("#add-conversionFormula").val("");
  	$("#add-allocationThreshold").val("");
  	$("#add-operationHandler").val("");
  	$("#add-pool-description").val("");
  	
  	$("#add-parentTopologySid").val(resTopologySid);
  	$("#addHostRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-288)/2 } });
  	$("#addHostRespoolWindow").jqxWindow('open');
}
  

//弹出新增vlan池window
function popAddRespoolVlanWindow(){
	// 设置弹出框位置
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#add-parentTopologySid").val(resTopologySid);
	$("#addVlanRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-288)/2 } });
	$("#addVlanRespoolWindow").jqxWindow('open');
}


//弹出新增ip池window
function popAddRespoolIpWindow(){
	// 设置弹出框位置
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#add-parentTopologySid").val(resTopologySid);
	$("#addIpRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-288)/2 } });
	$("#addIpRespoolWindow").jqxWindow('open');
}


//弹出新增存储池window
function popAddStorageRespoolIpWindow(){
	// 设置弹出框位置
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#add-storage-resPoolName").val("");
	$("#add-storage-conversionFormula").val("");
	$("#add-storage-allocationThreshold").val("");
	$("#add-storage-operationHandler").val("");
	$("#add-storage-description").val("");
	
	$("#add-storage-parentTopologySid").val(resTopologySid);
	$("#addStorageRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-288)/2 } });
	$("#addStorageRespoolWindow").jqxWindow('open');
}

//弹出新增Sharepoint池window
function popAddRespoolSharepointWindow(){
	// 设置弹出框位置
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 清除原来新增资源池的数据
  	$("#add-resPoolName-sharepoint").val("");
  	$("#add-conversionFormula-sharepoint").val("");
  	$("#add-allocationThreshold-sharepoint").val("");
  	$("#add-operationHandler-sharepoint").val("");
  	$("#add-pool-description-sharepoint").val("");
  	
  	$("#add-parentTopologySid-sharepoint").val(resTopologySid);
  	
  	$("#addSharepointRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-288)/2 } });
  	$("#addSharepointRespoolWindow").jqxWindow('open');
}

//弹出新增Sharepoint池window
function popAddRespoolExchangeWindow(){
	// 设置弹出框位置
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 清除原来新增资源池的数据
  	$("#add-resPoolName-exchange").val("");
  	$("#add-conversionFormula-exchange").val("");
  	$("#add-allocationThreshold-exchange").val("");
  	$("#add-operationHandler-exchange").val("");
  	$("#add-pool-description-exchange").val("");
  	
  	$("#add-parentTopologySid-exchange").val(resTopologySid);
  	
  	$("#addExchangeRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-288)/2 } });
  	$("#addExchangeRespoolWindow").jqxWindow('open');
}

//提交新增Exchange池信息
function addRespoolExchangeSubmit(){
	$('#addExchangeRespoolForm').jqxValidator('validate');
}

//提交新增sharepoint池信息
function addRespoolSharepointSubmit(){
	$('#addSharepointRespoolForm').jqxValidator('validate');
}

// 提交新增主机池信息
function addRespoolSubmit(){
	$('#addHostRespoolForm').jqxValidator('validate');
}

//提交新增vlan池信息
function addRespoolVlanSubmit(){
	$('#addVlanRespoolForm').jqxValidator('validate');
}

//提交新增ip池信息
function addRespoolIpSubmit(){
	$('#addIpRespoolForm').jqxValidator('validate');
}

//提交新增存储池信息
function addResStoragePoolSubmit(){
	$('#addStorageRespoolForm').jqxValidator('validate');
}

// 删除主机资源池
function removeRespool(){
	Core.confirm({
		title:"提示",
		message:"确定要删除该主机资源池吗？",
		confirmCallback:function(){
			Core.AjaxRequest({
 				url : ws_url + "/rest/pools/host/delete/"+resTopologySid+"",
 				type:"get",
 				callback : function (data) {
 					// 刷新左边的Tree
 					setPoolTreeValue();
 			    },
 			    failure:function(data){
 			    	
 			    }
 			});
		}
   });
}

//删除Vlan资源池
function removeVlanRespool(){
	Core.confirm({
		title:"提示",
		message:"确定要删除该VLAN资源池吗？",
		confirmCallback:function(){
			Core.AjaxRequest({
 				url : ws_url + "/rest/pools/vlan/delete/"+resTopologySid+"",
 				type:"get",
 				callback : function (data) {
 					// 刷新左边的Tree
 					setNetworkTreeValue();
 			    },
 			    failure:function(data){
 			    	
 			    }
 			});
		}
   });
}

//删除Ip资源池
function removeIpRespool(){
	Core.confirm({
		title:"提示",
		message:"确定要删除该IP资源池吗？",
		confirmCallback:function(){
			Core.AjaxRequest({
 				url : ws_url + "/rest/pools/ip/delete/"+resTopologySid+"",
 				type:"get",
 				callback : function (data) {
 					// 刷新左边的Tree
 					setNetworkTreeValue();
 			    },
 			    failure:function(data){
 			    	
 			    }
 			});
		}
   });
}

//删除Exchange资源池
function removeExchangeRespool(){
	Core.confirm({
		title:"提示",
		message:"确定要删除该Exchange资源池吗？",
		confirmCallback:function(){
			Core.AjaxRequest({
 				url : ws_url + "/rest/pools/exchange/delete/"+resTopologySid+"",
 				type:"get",
 				callback : function (data) {
 					// 刷新左边的Tree
 					setSpExTreeValue();
 			    },
 			    failure:function(data){
 			    	
 			    }
 			});
		}
   });
}

//删除sharepoint资源池
function removeSharepointRespool(){
	Core.confirm({
		title:"提示",
		message:"确定要删除该sharepoint资源池吗？",
		confirmCallback:function(){
			Core.AjaxRequest({
 				url : ws_url + "/rest/pools/sharepoint/delete/"+resTopologySid+"",
 				type:"get",
 				callback : function (data) {
 					// 刷新左边的Tree
 					setSpExTreeValue();
 			    },
 			    failure:function(data){
 			    	
 			    }
 			});
		}
   });
}

// 删除存储资源池
function removeStorageRespool(){
	Core.confirm({
		title:"提示",
		message:"确定要删除该存储资源池吗？",
		confirmCallback:function(){
			Core.AjaxRequest({
 				url : ws_url + "/rest/pools/storage/delete/"+resTopologySid+"",
 				type:"get",
 				callback : function (data) {
 					// 刷新左边的Tree
 					setHostPoolTreeValue();
 			    },
 			    failure:function(data){
 			    	
 			    }
 			});
		}
   });
}