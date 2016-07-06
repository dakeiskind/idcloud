var storageDetailModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    
	    this.popStorageDetailInfoWindow = function(obj){
	    	//　初始化基本信息
	    	me.initBasicVmInfo(obj);
	    	// 初始化配置信息
	    	me.initConfigVmInfo(obj);
	    };
	    
	    // 初始化基本信息
	    this.initBasicVmInfo = function(data) {
	    	$("#locationNumber").html(data.locationNumber);
			$("#name").html(data.name);
			$("#equipType").html(data.equipType);
			$("#equipCategoryName").html(data.equipCategoryName);
			$("#serialNumber").html(data.serialNumber);
			$("#brand").html(data.brand);
			$("#model").html(data.model);
			$("#equipRackName").html(data.equipRackName);
			$("#equipRoomName").html(data.equipRoomName);
			$("#equipCabinetName").html(data.equipCabinetName);
			$("#remoteMgtIp1").html(data.remoteMgtIp1);
			$("#remoteMgtIp2").html(data.remoteMgtIp2);
			$("#relevanceIp").html(data.relevanceIp);
			$("#remoteMgtUser").html(data.remoteMgtUser);
			$("#remoteMgtPwd").html(data.remoteMgtPwd);
			$("#capacity").html(data.capacity);
		};
		
		// 初始化配置信息
	    this.initConfigVmInfo = function(obj) {
			var equipStorageSid = obj.equipSid;
			Core.AjaxRequest({
				url : ws_url + "/rest/phystorages/findMaintenanceSan/"+ equipStorageSid,
				type : "get",
				async : false,
				callback : function(data) {
					$("#maintCompany").html(data.maintCompany);
					$("#maintTel").html(data.maintTel);
					$("#purchaseDate").html(data.purchaseDate);
					$("#startEndDate").html(data.startEndDate);
					$("#warrantyPeriod").html(data.warrantyPeriod);
					$("#equipSupplier").html(data.equipSupplier);
					$("#spec").html(data.spec);
					$("#description").html(data.description);
				}
			});
		};
};

  