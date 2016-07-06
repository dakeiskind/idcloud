var bladeFrameDetailModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    
	    this.popBladeframeDetailInfoWindow = function(obj){
	    	//　初始化基本信息
	    	me.initBasicVmInfo(obj);
	    	// 初始化配置信息
	    	me.initConfigVmInfo(obj);
	    };
	    
	    // 初始化基本信息
	    this.initBasicVmInfo = function(data) {
	    	$("#bladeFrame-locationNumber").html(data.locationNumber);
			$("#bladeFrame-name").html(data.name);
			$("#bladeFrame-equipType").html(data.equipType);
			$("#bladeFrame-equipCategoryName").html(data.equipCategoryName);
			$("#bladeFrame-serialNumber").html(data.serialNumber);
			$("#bladeFrame-brand").html(data.brand);
			$("#bladeFrame-model").html(data.model);
			$("#bladeFrame-equipCabinetName").html(data.equipCabinetName);
			$("#bladeFrame-equipRoomName").html(data.equipRoomName);
			$("#bladeFrame-equipRackName").html(data.equipRackName);
			$("#bladeFrame-spec").html(data.spec);
			$("#bladeFrame-description").html(data.description);
			$("#bladeFrame-remoteMgtIp1").html(data.remoteMgtIp1);
			$("#bladeFrame-remoteMgtIp2").html(data.remoteMgtIp2);
			$("#bladeFrame-relevanceIp").html(data.relevanceIp);
			$("#bladeFrame-remoteMgtUser").html(data.remoteMgtUser);
			$("#bladeFrame-remoteMgtPwd").html(data.remoteMgtPwd);
		};
		
		// 初始化配置信息
	    this.initConfigVmInfo = function(obj) {
			var equipSid = obj.equipSid;
			Core.AjaxRequest({
				url : ws_url + "/rest/phycomputes/findMaintenance/"+equipSid,
				type : "get",
				async : false,
				callback : function(data) {
					$("#bladeFrame-maintCompany").html(data.maintCompany);
					$("#bladeFrame-maintTel").html(data.maintTel);
					$("#bladeFrame-purchaseDate").html(data.purchaseDate);
					$("#bladeFrame-startEndDate").html(data.startEndDate);
					$("#bladeFrame-warrantyPeriod").html(data.warrantyPeriod);
					$("#bladeFrame-equipSupplier").html(data.equipSupplier);
				}
			});
		};
};

  