var serverinfoDetailModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    
	    this.popServerDetailInfoWindow = function(obj){
	    	if (null==obj.isViosFlag) {
		    	//　初始化基本信息
		    	me.initBasicx86Info(obj);
		    	// 初始化配置信息
		    	me.initConfigx86Info(obj);
	    	}else{
	    		//　初始化基本信息
		    	me.initBasicIBMInfo(obj);
		    	// 初始化配置信息
		    	me.initConfigIBMInfo(obj);
	    	}
	    };
	    
	    // 初始化基本信息
	    this.initBasicx86Info = function(data) {
	    	$("#x86hostName").html(data.hostName);
	    	$("#x86hostIp").html(data.hostIp);
	    	$("#x86equipHostOsType").html(data.hostOsTypeName);
	    	$("#x86cpuNumber").html(data.cpuNumber);
	    	$("#x86cpuCores").html(data.cpuCores);
	    	$("#x86cpuType").html(data.cpuType);
	    	$("#x86memorySize").html(data.memorySize);
	    	$("#x86managementUser").html(data.managementUser);
	    	$("#x86managementPwd").html(data.managementPwd);
	    	$("#x86roomName").html(data.roomName);
			$("#x86cabinetName").html(data.cabinetName);
			$("#x86rackName").html(data.rackName);
//	    	$("#x86locationNumber").html(data.locationNumber);
//			$("#x86name").html(data.name);
//			$("#x86equipType").html(data.equipType);
//			$("#x86equipCategoryName").html(data.equipCategoryName);
//			$("#x86serialNumber").html(data.phySerialNumber);
//			$("#x86brand").html(data.brand);
//			$("#x86model").html(data.model);
//			$("#x86roomName").html(data.roomName);
//			$("#x86cabinetName").html(data.cabinetName);
//			$("#x86rackName").html(data.rackName);
//			$("#x86remoteMgtIp1").html(data.remoteMgtIp1);
//			$("#x86remoteMgtIp2").html(data.remoteMgtIp2);
//			$("#x86relevanceIp").html(data.relevanceIp);
//			$("#x86remoteMgtUser").html(data.remoteMgtUser);
//			$("#x86remoteMgtPwd").html(data.remoteMgtPwd);
		};
		
		// 初始化配置信息
	    this.initConfigx86Info = function(obj) {
			var resHostSid = obj.resHostSid;
			Core.AjaxRequest({
				url : ws_url + "/rest/phynetwork/findMaintenanceRack/"+resHostSid,
				type : "get",
				async : false,
				callback : function(result) {
					console.log(JSON.stringify(result));
					if(null==result){
						$("#x86maintCompany").html("");
						$("#x86maintTel").html("");
						$("#x86purchaseDate").html("");
						$("#x86startEndDate").html("");
						$("#x86warrantyPeriod").html("");
						$("#x86equipSupplier").html("");
						$("#x86spec").html("");
						$("#x86description").html("");
					}else{
						$("#x86maintCompany").html(result.maintCompany);
						$("#x86maintTel").html(result.maintTel);
						$("#x86purchaseDate").html(result.purchaseDate);
						$("#x86startEndDate").html(result.startEndDate);
						$("#x86warrantyPeriod").html(result.warrantyPeriod);
						$("#x86equipSupplier").html(result.equipSupplier);
						$("#x86spec").html(result.spec);
						$("#x86description").html(result.description);
						$("#x86locationNumber").html(result.locationNumber);
						$("#x86name").html(result.name);
						$("#x86equipType").html(result.equipType);
						$("#x86serialNumber").html(result.serialNumber);
						$("#x86brand").html(result.brand);
						$("#x86model").html(result.model);
//						$("#x86roomName").html(result.roomName);
//						$("#x86cabinetName").html(result.cabinetName);
//						$("#x86rackName").html(result.rackName);
						$("#x86remoteMgtIp1").html(result.remoteMgtIp1);
						$("#x86remoteMgtIp2").html(result.remoteMgtIp2);
						$("#x86relevanceIp").html(result.relevanceIp);
						$("#x86remoteMgtUser").html(result.remoteMgtUser);
						$("#x86remoteMgtPwd").html(result.remoteMgtPwd);
						$("#x86equipCategoryName").html(result.equipCategoryName);
				}
					
				}
			});
		};


		// 初始化基本信息
	    this.initBasicIBMInfo = function(data) {
	    	console.log(JSON.stringify(data));
	    	$("#ibm-hostName").html(data.hostName);
	    	$("#ibm-hostIp").html(data.hostIp);
	    	$("#ibm-equipHostOsType").html(data.hostOsTypeName);
	    	$("#ibm-cpuNumber").html(data.cpuNumber);
	    	// $("#cpuCores").html(data.cpuCores);
	    	$("#ibm-cpuType").html(data.cpuType);
	    	$("#ibm-locationNumber").html(data.locationNumber);
	    	$("#ibm-memorySize").html(data.memorySize);
	    	$("#ibm-managementUser").html(data.managementUser);
	    	$("#ibm-managementPwd").html(data.managementPwd);
	    	$("#ibm-managementPwd").html(data.managementPwd);
	    	if("01"==data.isViosFlag){
	    		$("#ibm-isVios").html("否");
	    	}else if("02"==data.isVios){
				$("#ibm-isVios").html("是");	
	    	}
	    	//$("#isVios").html(data.isVios);
			$("#ibm-name").html(data.name);
			$("#ibm-equipType").html(data.equipType);
			$("#ibm-equipCategoryName").html(data.equipCategoryName);
			$("#ibm-serialNumber").html(data.phySerialNumber);
			$("#ibm-brand").html(data.brand);
			$("#ibm-model").html(data.model);
			$("#ibm-roomName").html(data.roomName);
			$("#ibm-cabinetName").html(data.cabinetName);
			$("#ibm-rackName").html(data.rackName);
			$("#ibm-remoteMgtIp1").html(data.remoteMgtIp1);
			$("#ibm-remoteMgtIp2").html(data.remoteMgtIp2);
			$("#ibm-relevanceIp").html(data.relevanceIp);
			$("#ibm-remoteMgtUser").html(data.remoteMgtUser);
			$("#ibm-remoteMgtPwd").html(data.remoteMgtPwd);
			$("#ibm-viosPwd").html(data.viosUser);
			$("#ibm-viosUser").html(data.viosPassWord);
		};
		
		// 初始化配置信息
	    this.initConfigIBMInfo = function(obj) {
			var resHostSid = obj.resHostSid;
			Core.AjaxRequest({
				url : ws_url + "/rest/phynetwork/findMaintenanceRack/"+resHostSid,
				type : "get",
				async : false,
				callback : function(result) {
					if(null==result){
						$("#ibm-maintCompany").html("");
						$("#ibm-maintTel").html("");
						$("#ibm-purchaseDate").html("");
						$("#ibm-startEndDate").html("");
						$("#ibm-warrantyPeriod").html("");
						$("#ibm-equipSupplier").html("");
						$("#ibm-spec").html("");
						$("#ibm-description").html("");
					}else{
						$("#ibm-maintCompany").html(result.maintCompany);
						$("#ibm-maintTel").html(result.maintTel);
						$("#ibm-purchaseDate").html(result.purchaseDate);
						$("#ibm-startEndDate").html(result.startEndDate);
						$("#ibm-warrantyPeriod").html(result.warrantyPeriod);
						$("#ibm-equipSupplier").html(result.equipSupplier);
						$("#ibm-spec").html(result.spec);
						$("#ibm-description").html(result.description);
				}
				}
			});
		};
};

  