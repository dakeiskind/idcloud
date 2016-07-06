var idcOrderBindModel = function(idcOrder){
	
	/** 审核 */
	this.idcMgtSubmit = function(){
		var orderSid = $("#orderApproveId").val();
		var bzId = $("#orderBzId").val();
		var approveResult = $("#approveIdcStatus").val();
		if(approveResult == '01') {
			approveResult = "000000";
    	} else {
    		approveResult = "000001";
    	}
		var approveNode = $("#approveIdcNote").val();
		Core.AjaxRequest({
			url : ws_url + "/rest/idc/order/approve",
			params :{
				bzId: bzId,
				dispatchInfo: approveNode,
				dispatchCode: approveResult
			},
			callback : function (data) {
				searchIdcOrder();
				$("#idcApproveWindow").jqxWindow('close');
		    },
		    failure:function(data){
		    }
		});
	};
	
	/** 资源检测 */
	this.idcResCheckSubmit = function(){
		idcOrder.resSelectData.push({
			'instanceSid': $("#instanceId").val(),
			'rescomuteid': $("#rescomuteid").val(),
			'vLanIDO': $("#vLanIDO").val(),
//       		'vLanIDI': $("#vLanIDI").val(),
			'wanIp': $("#wanIps").val() == '自动分配' ? '' :$("#wanIps").val()
//       		'lanIp': $("#lanIps").val() == '自动分配' ? '' :$("#lanIps").val()
		});
		Core.AjaxRequest({
			url : ws_url + "/rest/approve/platform/vmResCheck",
			params :{
				processObjectId: $("#instanceId").val(),
				detailSid: $("#orderDetailId").val(),
				orderId: $("#orderId").val(),
				resSet: idcOrder.resSelectData,
//				processInstanceId: $("#processInstanceId").val(),
//				approvorAction: $("#approvorAction").val(),
				processType: $("#processType").val()
			},
			callback : function (data) {
		    },
		    failure:function(data){
		    }
		});
	};
	
	
	/** 保存关联关系 */
	this.approveIDCMgtSubmit = function(){
		idcOrder.resSelectData.push({
			'instanceSid': $("#instanceId").val(),
			'os' : $("#osType").val(),
			'rescomuteid': $("#rescomuteid").val(),
			'vLanIDO': $("#vLanIDO").val(),
//       		'vLanIDI': $("#vLanIDI").val(),
			'wanIp': $("#wanIps").val() == '自动分配' ? '' :$("#wanIps").val()
//       		'lanIp': $("#lanIps").val() == '自动分配' ? '' :$("#lanIps").val()
		});
		Core.AjaxRequest({
			url : ws_url + "/rest/idc/order/idcOrderDetail/resApply",
			params :{
				processObjectId: $("#instanceId").val(),
				detailSid: $("#orderDetailId").val(),
				orderId: $("#orderId").val(),
				resSet: idcOrder.resSelectData,
				vmOpType : $("#vmOpType").val(),
//				processInstanceId: $("#processInstanceId").val(),
//				approvorAction: $("#approvorAction").val(),
				processType: $("#processType").val()
			},
			callback : function (data) {
				searchIdcOrder();
				$("#idctabwindow").jqxWindow('close');
		    },
		    failure:function(data){
		    }
		});
	};
	
	this.idcFeedbackSubmit = function(){
		var orderSid = $("#orderApproveId").val();
		var bzId = $("#orderBzId").val();
		var idcFeedbackNote = $("#approveIdcNote").val();
		Core.AjaxRequest({
			url : ws_url + "/rest/idc/order/complete",
			params :{
				bzId: bzId,
				dispatchInfo: idcFeedbackNote
			},
			callback : function (data) {
				searchIdcOrder();
				$("#idcApproveWindow").jqxWindow('close');
		    },
		    failure:function(data){
		    }
		});
	};
	
	
	this.idcSpecCheckSubmit = function(){
		idcOrder.resSelectData.push({
			'instanceSid': $("#instanceId").val(),
//			'rescomuteid': $("#rescomuteidSpec").val()
		});
		Core.AjaxRequest({
			url : ws_url + "/rest/approve/platform/vmResCheck",
			params :{
				processObjectId: $("#instanceId").val(),
				detailSid: $("#orderDetailId").val(),
				orderId: $("#orderId").val(),
				resSet: idcOrder.resSelectData,
//				processInstanceId: $("#processInstanceId").val(),
//				approvorAction: $("#approvorAction").val(),
				processType: $("#processType").val()
			},
			callback : function (data) {
		    },
		    failure:function(data){
		    }
		});
	};
	
	
	//变更的提交
	this.specChangeSubmit = function(){
		idcOrder.resSelectData.push({
			'instanceSid': $("#instanceId").val(),
//			'rescomuteid': $("#rescomuteidSpec").val(),
		});
		Core.AjaxRequest({
			url : ws_url + "/rest/idc/order/idcOrderDetail/resApply",
			params :{
				processObjectId: $("#instanceId").val(),
				detailSid: $("#orderDetailId").val(),
				orderId: $("#orderId").val(),
				resSet: idcOrder.resSelectData,
				vmOpType : $("#vmOpType").val()
			},
			callback : function (data) {
				searchIdcOrder();
				$("#idctabwindow").jqxWindow('close');
		    },
		    failure:function(data){
		    }
		});
	}
};