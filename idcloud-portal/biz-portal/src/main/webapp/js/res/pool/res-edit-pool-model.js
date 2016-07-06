var editResPoolModel = function(){
	var me = this;
	
	// 根据sid查询资源池信息
	this.searchResPool = function(resPoolSid){
		var resPoolData;
		Core.AjaxRequest({
				url : ws_url + "/rest/pools/"+resPoolSid+"",
				type:"get",
				async:false,
				callback : function (data) {
					resPoolData = data;
			    },
			    failure:function(data){
			    	
			    }
		 });
		return resPoolData;
	};
	
	// 设置主机资源池的基本信息
	this.setHostResPool = function(){
		var data = me.searchResPool(resTopologySid);
		// 当资源池是物理主机池的时候，不显示虚拟类型
		if("RES-POOL-PM" == data.resPoolType){
			$("#isPysical").hide();
		}else{
			$("#isPysical").show();
		}
		$("#resPoolName").html(data.resPoolName);
		$("#pooltype").html(data.resPoolTypeName);
		$("#virtualPlatformType").html(data.virtualPlatformType);
		$("#allocationPolicy").html(data.allocationPolicyName);
		$("#conversionFormula").html(data.conversionFormula);
		$("#allocationThreshold").html(data.allocationThreshold);
		$("#perfLevel").html(data.perfLevelName);
		$("#statusName").html(data.statusName);
		$("#operationHandler").html(data.operationHandler);
		// sunyu updated for #119
		//$("#description").html(data.description);
		if(data.description != null) {
			var str = data.description;
			if (str.length > 80) {
				str = str.substring(0, 80) + "...";
				$("#description").html(str);
				$("#description").attr("title", data.description);
			} else {
				$("#description").html(data.description);
			}
		}
		// end
	};
	
	// 设置Vlan资源池的基本信息
	this.setVlanResPool = function(){
		var data = me.searchResPool(resTopologySid);

		$("#vlanType-vlan").html(data.vlanTypeName);
		$("#allocationPolicy-vlan").html(data.allocationPolicyName);
		$("#perfLevel-vlan").html(data.perfLevelName);
		$("#statusName-vlan").html(data.statusName);
		$("#operationHandler-vlan").html(data.operationHandler);
		$("#description-vlan").html(data.description);
	};
	
	// 设置Ip资源池的基本信息
	this.setIpResPool = function(){
		var data = me.searchResPool(resTopologySid);
		$("#ipCategory-ip").html(data.ipCategoryName);
		$("#allocationPolicy-ip").html(data.allocationPolicyName);
		$("#virtualPlatformType-ip").html(data.virtualPlatformTypeName);
		$("#perfLevel-ip").html(data.perfLevelName);
		$("#statusName-ip").html(data.statusName);
		$("#operationHandler-ip").html(data.operationHandler);
		$("#description-ip").html(data.description);
		$("#ipCategory").val(data.ipCategory);

	};
	
	// 设置Sharepoint资源池的基本信息
	this.setSharepointResPool = function(){
		var data = me.searchResPool(resTopologySid);
		$("#resPoolName-sharepoint").html(data.resPoolName);
		$("#pooltype-sharepoint").html(data.resPoolTypeName);
		$("#allocationPolicy-sharepoint").html(data.allocationPolicyName);
		$("#conversionFormula-sharepoint").html(data.conversionFormula);
		$("#allocationThreshold-sharepoint").html(data.allocationThreshold);
		$("#perfLevel-sharepoint").html(data.perfLevelName);
		$("#statusName-sharepoint").html(data.statusName);
		$("#operationHandler-sharepoint").html(data.operationHandler);
		$("#description-sharepoint").html(data.description);

	};
	
	// 设置Exchange资源池的基本信息
	this.setExchangeResPool = function(){
		var data = me.searchResPool(resTopologySid);
		$("#resPoolName-exchange").html(data.resPoolName);
		$("#pooltype-exchange").html(data.resPoolTypeName);
		$("#allocationPolicy-exchange").html(data.allocationPolicyName);
		$("#conversionFormula-exchange").html(data.conversionFormula);
		$("#allocationThreshold-exchange").html(data.allocationThreshold);
		$("#perfLevel-exchange").html(data.perfLevelName);
		$("#statusName-exchange").html(data.statusName);
		$("#operationHandler-exchange").html(data.operationHandler);
		$("#description-exchange").html(data.description);

	};
	
	// 设置要编辑的资源池信息
	this.setEditRespoolForm = function(){
		var data = me.searchResPool(resTopologySid);
		$("#edit-resPoolSid").val(data.resPoolSid);
		$("#edit-resPoolName").val(data.resPoolName);
		$("#edit-status").val(data.status);
		$("#edit-perfLevel").val(data.perfLevel);
		$("#edit-allocationPolicy").val(data.allocationPolicy);
		$("#edit-operationHandler").val(data.operationHandler);
		$("#edit-conversionFormula").val(data.conversionFormula);
		$("#edit-allocationThreshold").val(data.allocationThreshold);
		$("#edit-pool-description").val(data.description);

    };
    
	// 设置要编辑的Vlan资源池信息
	this.setEditRespoolVlanForm = function(){
		var data = me.searchResPool(resTopologySid);
		$("#edit-resPoolSid-vlan").val(data.resPoolSid);
		$("#edit-resPoolName-vlan").val(data.resPoolName);
		$("#edit-status-vlan").val(data.status);
		$("#edit-perfLevel-vlan").val(data.perfLevel);
		$("#edit-allocationPolicy-vlan").val(data.allocationPolicy);
		$("#edit-conversionFormula-vlan").val(data.conversionFormula);
		$("#edit-allocationThreshold-vlan").val(data.allocationThreshold);
		$("#edit-vlanType-vlan").val(data.vlanType);
		$("#edit-operationHandler-vlan").val(data.operationHandler);
		$("#edit-description-vlan").val(data.description);

    };
    
	// 设置要编辑的Ip资源池信息
	this.setEditRespoolIpForm = function(){
		var data = me.searchResPool(resTopologySid);

		$("#edit-resPoolSid-ip").val(data.resPoolSid);
		$("#edit-resPoolName-ip").val(data.resPoolName);
		$("#edit-status-ip").val(data.status);
		$("#edit-perfLevel-ip").val(data.perfLevel);
		$("#edit-allocationPolicy-ip").val(data.allocationPolicy);
		$("#edit-conversionFormula-ip").val(data.conversionFormula);
		$("#edit-allocationThreshold-ip").val(data.allocationThreshold);
		$("#edit-floatingIpPoolName-ip").val(data.floatingIpPoolName);
		$("#edit-ipCategory-ip").val(data.ipCategory);
		$("#edit-virtualPlatformType-ip").val(data.virtualPlatformType);
		$("#edit-operationHandler-ip").val(data.operationHandler);
		$("#edit-pool-description-ip").val(data.description);

    };
    
 // 设置要编辑的sharepoint资源池信息
	this.setEditRespoolSharepointForm = function(){
		var data = me.searchResPool(resTopologySid);
		$("#edit-resPoolSid-sharepoint").val(data.resPoolSid);
		$("#edit-resPoolName-sharepoint").val(data.resPoolName);
		$("#edit-status-sharepoint").val(data.status);
		$("#edit-perfLevel-sharepoint").val(data.perfLevel);
		$("#edit-allocationPolicy-sharepoint").val(data.allocationPolicy);
		$("#edit-conversionFormula-sharepoint").val(data.conversionFormula);
		$("#edit-allocationThreshold-sharepoint").val(data.allocationThreshold);
		$("#edit-floatingIpPoolName-sharepoint").val(data.floatingIpPoolName);
		$("#edit-ipCategory-sharepoint").val(data.ipCategory);
		$("#edit-operationHandler-sharepoint").val(data.operationHandler);
		$("#edit-pool-description-sharepoint").val(data.description);

    };
    
 // 设置要编辑的sharepoint资源池信息
	this.setEditRespoolExchangeForm = function(){
		var data = me.searchResPool(resTopologySid);
		$("#edit-resPoolSid-exchange").val(data.resPoolSid);
		$("#edit-resPoolName-exchange").val(data.resPoolName);
		$("#edit-status-exchange").val(data.status);
		$("#edit-perfLevel-exchange").val(data.perfLevel);
		$("#edit-allocationPolicy-exchange").val(data.allocationPolicy);
		$("#edit-conversionFormula-exchange").val(data.conversionFormula);
		$("#edit-allocationThreshold-exchange").val(data.allocationThreshold);
		$("#edit-floatingIpPoolName-exchange").val(data.floatingIpPoolName);
		$("#edit-ipCategory-exchange").val(data.ipCategory);
		$("#edit-operationHandler-exchange").val(data.operationHandler);
		$("#edit-pool-description-exchange").val(data.description);

    };
    
    // 验证
    this.initValidator = function(){
		$('#editHostRespoolForm').jqxValidator({
	        rules: [  
	                  { input: '#edit-resPoolName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-resPoolName', message: '主机名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
	                  
	                  { input: '#edit-operationHandler', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-operationHandler', message: '主机IP不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' }
	                 
	               ]
		});
	    	
		// 编辑主机验证成功
		$('#editHostRespoolForm').on('validationSuccess', function (event) {
			 try {
				 var pool = Core.parseJSON($("#editHostRespoolForm").serializeJson());
				 Core.AjaxRequest({
		 				url : ws_url + "/rest/pools/update",
		 				params :pool,
		 				callback : function (data) {
		 					// 刷新基本信息
		 					me.setHostResPool();
							$("#editHostRespoolWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
							$("#editHostRespoolWindow").jqxWindow('close');
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
		$('#editVlanRespoolForm').jqxValidator({
	        rules: [  
	                  { input: '#edit-resPoolName-vlan', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-resPoolName-vlan', message: 'vlan名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
	                  
	                  { input: '#edit-operationHandler-vlan', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-operationHandler-vlan', message: 'Vlan操作处理不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' }
	                 
	               ]
		});
	    	
		// 编辑Vlan验证成功
		$('#editVlanRespoolForm').on('validationSuccess', function (event) {
			try {
				var pool = Core.parseJSON($("#editVlanRespoolForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/pools/vlan/update",
	 				params :pool,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					me.setVlanResPool();
						$("#editVlanRespoolWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editVlanRespoolWindow").jqxWindow('close');
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
		$('#editIpRespoolForm').jqxValidator({
	        rules: [  
	                  { input: '#edit-resPoolName-ip', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-resPoolName-ip', message: 'ip名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
	                  
	                  { input: '#edit-operationHandler-ip', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-operationHandler-ip', message: 'Ip操作处理不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' }
	                 
	               ]
		});
	    	
		// 编辑Ip验证成功
		$('#editIpRespoolForm').on('validationSuccess', function (event) {
			try {
				var pool = Core.parseJSON($("#editIpRespoolForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/pools/ip/update",
	 				params :pool,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					me.setIpResPool();
						$("#editIpRespoolWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editIpRespoolWindow").jqxWindow('close');
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
    
    // 编辑sharepoint验证
    this.initSharePointValidator = function(){
		$('#editSharepointRespoolForm').jqxValidator({
	        rules: [  
	                  { input: '#edit-resPoolName-sharepoint', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-resPoolName-sharepoint', message: '主机名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
	                  
	                  { input: '#edit-operationHandler-sharepoint', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-operationHandler-sharepoint', message: '主机IP不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' }
	               ]
		});
	    	
		// 编辑SharePoint验证成功
		$('#editSharepointRespoolForm').on('validationSuccess', function (event) {
			try {
				var pool = Core.parseJSON($("#editSharepointRespoolForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/pools/sharepoint/update",
	 				params :pool,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					me.setSharepointResPool();
						$("#editSharepointRespoolWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editSharepointRespoolWindow").jqxWindow('close');
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
    
    // 编辑Exchange验证
    this.initExchangeValidator = function(){
		$('#editExchangeRespoolForm').jqxValidator({
	        rules: [  
	                  { input: '#edit-resPoolName-exchange', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-resPoolName-exchange', message: '主机名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
	                  
	                  { input: '#edit-operationHandler-exchange', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-operationHandler-exchange', message: '主机IP不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' }
	               ]
		});
	    	
		// 编辑SharePoint验证成功
		$('#editExchangeRespoolForm').on('validationSuccess', function (event) {
			try {
				var pool = Core.parseJSON($("#editExchangeRespoolForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/pools/exchange/update",
	 				params :pool,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					me.setExchangeResPool();
						$("#editExchangeRespoolWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editExchangeRespoolWindow").jqxWindow('close');
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
		  $("#editHostRespoolWindow").jqxWindow({
		        width: 680, 
		        height:258,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#editHostRespoolCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#edit-resPoolName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
		        	$("#edit-conversionFormula").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
		        	$("#edit-allocationThreshold").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
		        	$("#edit-operationHandler").jqxInput({placeHolder: "", height: 22, width: 420, minLength: 1,theme:currentTheme});
		        	$("#edit-pool-description").jqxInput({placeHolder: "", height: 60, width: 420, minLength: 1,maxLength: 256,theme:currentTheme});
		        	
		        	var respool = new codeModel({width:120,autoDropDownHeight:true});
		        	respool.getCommonCode("edit-status","RESOURCE_POOL_STATUS");
		        	respool.getCommonCode("edit-perfLevel","PERF_LEVEL");
		        	respool.getCommonCode("edit-allocationPolicy","ALLOCATION_POLICY");
			    	
			    	$("#editHostRespoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#editHostRespoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
	 
	    // 初始化SharePoint弹出window
		this.initSharePointPopWindow = function(){   	
			  $("#editSharepointRespoolWindow").jqxWindow({
			        width: 680, 
			        height:258,
			        theme:currentTheme,  
			        resizable: false,  
			        isModal: true, 
			        autoOpen: false, 
			        cancelButton: $("#editSharepointRespoolCancel"), 
			        modalOpacity: 0.3,
			        initContent:function(){
			        	// 初始化新增用户页面组件
			        	$("#edit-resPoolName-sharepoint").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
			        	$("#edit-conversionFormula-sharepoint").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
			        	$("#edit-allocationThreshold-sharepoint").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
			        	$("#edit-operationHandler-sharepoint").jqxInput({placeHolder: "", height: 22, width: 420, minLength: 1,theme:currentTheme});
			        	$("#edit-pool-description-sharepoint").jqxInput({placeHolder: "", height: 60, width: 420, minLength: 1,theme:currentTheme});
			        	
			        	var respool = new codeModel({width:120,autoDropDownHeight:true});
			        	respool.getCommonCode("edit-status-sharepoint","RESOURCE_POOL_STATUS");
			        	respool.getCommonCode("edit-perfLevel-sharepoint","PERF_LEVEL");
			        	respool.getCommonCode("edit-allocationPolicy-sharepoint","ALLOCATION_POLICY");
				    	
				    	$("#editSharepointRespoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
				    	$("#editSharepointRespoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			        }
			    });
		 };
		 
		 // 初始化Exchange弹出window
			this.initExchangePopWindow = function(){   	
				  $("#editExchangeRespoolWindow").jqxWindow({
				        width: 680, 
				        height:258,
				        theme:currentTheme,  
				        resizable: false,  
				        isModal: true, 
				        autoOpen: false, 
				        cancelButton: $("#editExchangeRespoolCancel"), 
				        modalOpacity: 0.3,
				        initContent:function(){
				        	// 初始化新增用户页面组件
				        	$("#edit-resPoolName-exchange").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
				        	$("#edit-conversionFormula-exchange").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
				        	$("#edit-allocationThreshold-exchange").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
				        	$("#edit-operationHandler-exchange").jqxInput({placeHolder: "", height: 22, width: 420, minLength: 1,theme:currentTheme});
				        	$("#edit-pool-description-exchange").jqxInput({placeHolder: "", height: 60, width: 420, minLength: 1,theme:currentTheme});
				        	
				        	var respool = new codeModel({width:120,autoDropDownHeight:true});
				        	respool.getCommonCode("edit-status-exchange","RESOURCE_POOL_STATUS");
				        	respool.getCommonCode("edit-perfLevel-exchange","PERF_LEVEL");
				        	respool.getCommonCode("edit-allocationPolicy-exchange","ALLOCATION_POLICY");
					    	
					    	$("#editExchangeRespoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
					    	$("#editExchangeRespoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
				        }
				    });
			 };
	 
    // 初始化vlan弹出window
	this.initPopVlanWindow = function(){   	
		$("#editVlanRespoolWindow").jqxWindow({
	        width: 680, 
	        height:288,
	        theme:currentTheme,  
	        resizable: false,  
	        isModal: true, 
	        autoOpen: false, 
	        cancelButton: $("#editVlanRespoolCancel"), 
	        modalOpacity: 0.3,
	        initContent:function(){
	        	// 初始化新增用户页面组件
	        	$("#edit-resPoolName-vlan").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
	        	$("#edit-conversionFormula-vlan").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
	        	$("#edit-allocationThreshold-vlan").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
	        	$("#edit-operationHandler-vlan").jqxInput({placeHolder: "", height: 22, width: 420, minLength: 1,theme:currentTheme});
	        	$("#edit-description-vlan").jqxInput({placeHolder: "", height: 60, width: 420, minLength: 1,theme:currentTheme});
	        	
	        	var respool = new codeModel({width:120,autoDropDownHeight:true});
	        	respool.getCommonCode("edit-status-vlan","RESOURCE_POOL_STATUS");
	        	respool.getCommonCode("edit-perfLevel-vlan","PERF_LEVEL");
	        	respool.getCommonCode("edit-allocationPolicy-vlan","ALLOCATION_POLICY");
	        	respool.getCommonCode("edit-vlanType-vlan","VLAN_TYPE");
		    	
		    	$("#editVlanRespoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		    	$("#editVlanRespoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
	        }
	    });
	 };
		 
    // 初始化ip弹出window
	this.initPopIpWindow = function(){   	
		$("#editIpRespoolWindow").jqxWindow({
	        width: 680, 
	        height:310,
	        theme:currentTheme,  
	        resizable: false,  
	        isModal: true, 
	        autoOpen: false, 
	        cancelButton: $("#editIpRespoolCancel"), 
	        modalOpacity: 0.3,
	        initContent:function(){
	        	// 初始化新增用户页面组件
	        	$("#edit-resPoolName-ip").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
	        	$("#edit-conversionFormula-ip").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
	        	$("#edit-allocationThreshold-ip").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
	        	$("#edit-floatingIpPoolName-ip").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
	        	$("#edit-operationHandler-ip").jqxInput({placeHolder: "", height: 22, width: 420, minLength: 1,theme:currentTheme});
	        	$("#edit-description-ip").jqxInput({placeHolder: "", height: 60, width: 420, minLength: 1,theme:currentTheme});
	        	
	        	var respool = new codeModel({width:120,autoDropDownHeight:true});
	        	respool.getCommonCode("edit-status-ip","RESOURCE_POOL_STATUS");
	        	respool.getCommonCode("edit-perfLevel-ip","PERF_LEVEL");
	        	respool.getCommonCode("edit-allocationPolicy-ip","ALLOCATION_POLICY");
	        	respool.getCommonCode("edit-ipCategory-ip","IP_CATEGORY");
	        	respool.getCommonCode("edit-virtualPlatformType-ip","VIRTUAL_PLATFORM_TYPE");
		    	
		    	$("#editIpRespoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		    	$("#editIpRespoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
	        }
	    });
	 };
	
};

// 弹出编辑资源池window
function popEditRespoolWindow(){
	
	var respool = new editResPoolModel();
	// 编辑框赋值
	respool.setEditRespoolForm();
	// 设置弹出框位置
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	$("#editHostRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-258)/2 } });
  	$("#editHostRespoolWindow").jqxWindow('open');
}
  
// 提交编辑资源池信息
function editRespoolSubmit(){
	$('#editHostRespoolForm').jqxValidator('validate');
}


//弹出编辑vlan资源池window
function popEditRespoolVlanWindow(){
	
	var respool = new editResPoolModel();
	// 编辑框赋值
	respool.setEditRespoolVlanForm();
	// 设置弹出框位置
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	$("#editVlanRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-258)/2 } });
  	$("#editVlanRespoolWindow").jqxWindow('open');
}
  
// 提交编辑vlan资源池信息
function editRespoolVlanSubmit(){
	$('#editVlanRespoolForm').jqxValidator('validate');
}

//弹出编辑资源池window
function popEditRespoolIpWindow(){
	
	var respool = new editResPoolModel();
	// 编辑框赋值
	respool.setEditRespoolIpForm();
	// 设置弹出框位置
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	$("#editIpRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-258)/2 } });
  	$("#editIpRespoolWindow").jqxWindow('open');
}
  
// 提交编辑资源池信息
function editRespoolIpSubmit(){
	$('#editIpRespoolForm').jqxValidator('validate');
}

// 弹出编辑sharepoint资源池window
function popEditRespoolSharepointWindow(){
	var respool = new editResPoolModel();
	// 编辑框赋值
	respool.setEditRespoolSharepointForm();
	// 设置弹出框位置
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	$("#editSharepointRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-258)/2 } });
  	$("#editSharepointRespoolWindow").jqxWindow('open');
}
// 提交编辑Sharepoint资源池信息
function editRespoolSharepointSubmit(){
	$('#editSharepointRespoolForm').jqxValidator('validate');
}

//弹出编辑Exchange资源池window
function popEditRespoolExchangeWindow(){
	var respool = new editResPoolModel();
	// 编辑框赋值
	respool.setEditRespoolExchangeForm();
	// 设置弹出框位置
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	$("#editExchangeRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-258)/2 } });
  	$("#editExchangeRespoolWindow").jqxWindow('open');
}

//提交编辑Exchange资源池信息
function editRespoolExchangeSubmit(){
	$('#editExchangeRespoolForm').jqxValidator('validate');
}
