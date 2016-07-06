var loadbalancerDetailModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    
	    this.popLoadbalancerDetailInfoWindow = function(obj){
	    	//　初始化基本信息
	    	me.initBasicVmInfo(obj);
	    	// 初始化配置信息
	    	me.initConfigVmInfo(obj);
	    };
	    
	    // 初始化基本信息
	    this.initBasicVmInfo = function(data) {
	    	$("#loadbalance-locationNumber").html(data.locationNumber);
			$("#loadbalance-name").html(data.name);
			$("#loadbalance-equipType").html(data.equipType);
			$("#loadbalance-equipCategoryName").html(data.equipCategoryName);
			$("#loadbalance-serialNumber").html(data.serialNumber);
			$("#loadbalance-brand").html(data.brand);
			$("#loadbalance-model").html(data.model);
			$("#loadbalance-equipRackName").html(data.equipRackName);
			$("#loadbalance-equipRoomName").html(data.equipRoomName);
			$("#loadbalance-equipCabinetName").html(data.equipCabinetName);
			$("#loadbalance-remoteMgtIp1").html(data.remoteMgtIp1);
			$("#loadbalance-remoteMgtIp2").html(data.remoteMgtIp2);
			$("#loadbalance-relevanceIp").html(data.relevanceIp);
			$("#loadbalance-remoteMgtUser").html(data.remoteMgtUser);
			$("#loadbalance-remoteMgtPwd").html(data.remoteMgtPwd);
		};
		
		// 初始化配置信息
	    this.initConfigVmInfo = function(obj) {
			var equipLoadBalanceSid = obj.equipLoadBalanceSid;
			Core.AjaxRequest({
				url : ws_url + "/rest/phynetwork/findMaintenanceLb/"+equipLoadBalanceSid,
				type : "get",
				async : false,
				callback : function(data) {
					$("#loadbalance-maintCompany").html(data.maintCompany);
					$("#loadbalance-maintTel").html(data.maintTel);
					$("#loadbalance-purchaseDate").html(data.purchaseDate);
					$("#loadbalance-startEndDate").html(data.startEndDate);
					$("#loadbalance-warrantyPeriod").html(data.warrantyPeriod);
					$("#loadbalance-equipSupplier").html(data.equipSupplier);
					$("#loadbalance-spec").html(data.spec);
					$("#loadbalance-description").html(data.description);
				}
			});
		};
};

  