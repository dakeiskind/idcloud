var switchDetailModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    
	    this.popSwitchDetailInfoWindow = function(obj){
	    	
		    	//　初始化基本信息
		    	me.initBasicInfo(obj);
		    	// 初始化配置信息
		    	me.initConfigInfo(obj);
	    	
	    };
	    
	    // 初始化基本信息
	    this.initBasicInfo = function(data) {
	    	$("#switch-hostName").html(data.hostName);
	    	$("#switch-hostIp").html(data.hostIp);
	    	$("#switch-hostOsType").html(data.hostOsType);
	    	$("#switch-cpuNumber").html(data.cpuNumber);
	    	$("#switch-cpuCores").html(data.cpuCores);
	    	$("#switch-cpuType").html(data.cpuType);
	    	$("#switch-memorySize").html(data.memorySize);
	    	$("#switch-managementUser").html(data.managementUser);
	    	$("#switch-managementPwd").html(data.managementPwd);
	    	// $("#locationNumber").html(data.locationNumber);
			$("#switch-name").html(data.name);
			$("#switch-equipType").html(data.equipType);
			$("#switch-equipCategoryName").html(data.equipCategoryName);
			$("#switch-serialNumber").html(data.serialNumber);
			$("#switch-brand").html(data.brand);
			$("#switch-model").html(data.model);
			$("#switch-equipRackName").html(data.equipRackName);
			$("#switch-equipRoomName").html(data.equipRoomName);
			$("#switch-equipCabinetName").html(data.equipCabinetName);
			$("#switch-remoteMgtIp1").html(data.remoteMgtIp1);
			$("#switch-remoteMgtIp2").html(data.remoteMgtIp2);
			$("#switch-relevanceIp").html(data.relevanceIp);
			$("#switch-remoteMgtUser").html(data.remoteMgtUser);
			$("#switch-remoteMgtPwd").html(data.remoteMgtPwd);
		};
		
		// 初始化配置信息
	    this.initConfigInfo = function(obj) {
			var equipSwitchSid = obj.equipSwitchSid;
			Core.AjaxRequest({
				url : ws_url + "/rest/phynetwork/findMaintenanceRack/"+equipSwitchSid,
				type : "get",
				async : false,
				callback : function(data) {
					$("#switch-locationNumber").html(data.locationNumber);
					$("#switch-maintCompany").html(data.maintCompany);
					$("#switch-maintTel").html(data.maintTel);
					$("#switch-purchaseDate").html(data.purchaseDate);
					$("#switch-startEndDate").html(data.startEndDate);
					$("#switch-warrantyPeriod").html(data.warrantyPeriod);
					$("#switch-equipSupplier").html(data.equipSupplier);
					$("#switch-spec").html(data.spec);
					$("#switch-description").html(data.description);
				}
			});
		};
};

  