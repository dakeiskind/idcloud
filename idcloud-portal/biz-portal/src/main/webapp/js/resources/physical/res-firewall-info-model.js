var firewallDetailModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    
	    this.popFirewallDetailInfoWindow = function(obj){
	    	//　初始化基本信息
	    	me.initBasicVmInfo(obj);
	    	// 初始化配置信息
	    	me.initConfigVmInfo(obj);
	    };
	    
	    // 初始化基本信息
	    this.initBasicVmInfo = function(data) {
	    	$("#fire-locationNumber").html(data.locationNumber);
			$("#fire-name").html(data.name);
			$("#fire-equipType").html(data.equipType);
			$("#fire-equipCategoryName").html(data.equipCategoryName);
			$("#fire-serialNumber").html(data.serialNumber);
			$("#fire-brand").html(data.brand);
			$("#fire-model").html(data.model);
			$("#fire-equipRackName").html(data.equipRackName);
			$("#fire-equipCabinetName").html(data.equipCabinetName);
			$("#fire-equipRoomName").html(data.equipRoomName);
			$("#fire-remoteMgtIp1").html(data.remoteMgtIp1);
			$("#fire-remoteMgtIp2").html(data.remoteMgtIp2);
			$("#fire-relevanceIp").html(data.relevanceIp);
			$("#fire-remoteMgtUser").html(data.remoteMgtUser);
			$("#fire-remoteMgtPwd").html(data.remoteMgtPwd);
		};
		
		// 初始化配置信息
	    this.initConfigVmInfo = function(obj) {
			var equipSid = obj.equipSid;
			Core.AjaxRequest({
				url : ws_url + "/rest/phynetwork/findMaintenanceRack/"+equipSid,
				type : "get",
				async : false,
				callback : function(data) {
					$("#fire-maintCompany").html(data.maintCompany);
					$("#fire-maintTel").html(data.maintTel);
					$("#fire-purchaseDate").html(data.purchaseDate);
					$("#fire-startEndDate").html(data.startEndDate);
					$("#fire-warrantyPeriod").html(data.warrantyPeriod);
					$("#fire-equipSupplier").html(data.equipSupplier);
					$("#fire-spec").html(data.spec);
					$("#fire-description").html(data.description);
				}
			});
		};
};

  