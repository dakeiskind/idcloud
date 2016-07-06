/**
 * 该view和js绑定的功能：VLAN配置管理、Exchange配置管理、SharePoint配置管理
 */
var resourceBindTwoModel = function(vlan,exchange,sharepoint){
	
	/*********************VLAN配置管理*******************************/
	// 弹出新增VLANwindow
	this.addVlanInfo = function(){
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#addVlanWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-150)/2 } });
    	$("#addVlanWindow").jqxWindow('open');
	};
	
	// 提交新增VLAN信息
	this.addVlanSubmit = function(){
		$('#addVlanForm').jqxValidator('validate');
	};
	
	// 弹出编辑VLANwindow
	this.editVlanInfo = function(){
		var rowindex = $('#vlanConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#vlanConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
    		// 为编辑赋值
    		$("#resSidVlan").val(data.resSid);
    		vlan.setEditVlanForm(data);
    		// 设置弹出框位置
    		var windowW = $(window).width(); 
        	var windowH = $(window).height(); 
        	$("#editVlanWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-150)/2 } });
        	$("#editVlanWindow").jqxWindow('open');
    	}
		
	};
	
	// 提交编辑VLAN信息
	this.editVlanSubmit = function(){
		$('#editVlanForm').jqxValidator('validate');
	};
	
	// 删除Vlan信息
	this.removeVlanInfo = function(){
		var rowindex = $('#vlanConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#vlanConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
    		Core.confirm({
				title:"提示",
				message:"确定要删除该VLAN吗?",
				yes:"确定",
				confirmCallback:function(){
					Core.AjaxRequest({
		 				url : ws_url + "/rest/vlans/delete/"+data.resSid+"",
		 				type:"get",
		 				callback : function (data) {
		 					vlan.searchVlanConfigInfo();
		 				    // 初始化按钮
		 					vlan.initButton();
		 			    },
		 			    failure:function(data){
		 			    	
		 			    }
		 			});
				}
		    });
    	}
	};
	
	// 条件查询Vlan配置管理
	this.searchVlanConfigMgt = function(){
		vlan.searchObj.vlanName = $("#search-vlan-name").val();
		vlan.searchObj.manageStatus = $("#search-vlan-mgt-status").val()=="全部"?"":$("#search-vlan-mgt-status").val();
		vlan.searchObj.usageStatus = $("#search-valn-usage-status").val()=="全部"?"":$("#search-valn-usage-status").val();
		vlan.searchVlanConfigInfo();
	};
	
	
	/*********************exchange配置管理*******************************/
	// 弹出新增Exchange window
	this.addExchangeInfo = function(){
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#addExchangeWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-150)/2 } });
    	$("#addExchangeWindow").jqxWindow('open');
	};
	
	// 提交新增Exchange信息
	this.addExchangeSubmit = function(){
		$('#addExchangeForm').jqxValidator('validate');
	};
	
	// 弹出编辑Exchange window
	this.editExchangeInfo = function(){
		var rowindex = $('#exchangeConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#exchangeConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
    		// 为编辑赋值
    		$("#resSidExchange").val(data.resSid);
    		exchange.setEditExchangeForm(data);
    		// 设置window显示位置
    		var windowW = $(window).width(); 
        	var windowH = $(window).height(); 
        	$("#editExchangeWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-150)/2 } });
        	$("#editExchangeWindow").jqxWindow('open');
    	}
		
	};
	
	// 删除exchange信息
	this.removeExchangeInfo = function(){
		var rowindex = $('#exchangeConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#exchangeConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
    		Core.confirm({
				title:"提示",
				message:"确定要删除该Exchange服务吗?",
				yes:"确定",
				confirmCallback:function(){
					Core.AjaxRequest({
		 				url : ws_url + "/rest/exchanges/delete/"+data.resSid+"",
		 				type:"get",
		 				callback : function (data) {
		 					exchange.searchExchangeConfigInfo();
		 			    },
		 			    failure:function(data){
		 			    	
		 			    }
		 			});
				}
		    });
    	}
	};
	
	// 提交编辑Exchange信息
	this.editExchangeSubmit = function(){
		$('#editExchangeForm').jqxValidator('validate');
	};
	
	// 条件查询exchange配置管理
	this.searchExchangeConfigMgt = function(){
		exchange.searchObj.serviceAddress = $("#search-service-address").val();
		exchange.searchObj.manageStatus = $("#search-exchange-mgt-status").val()=="全部"?"":$("#search-exchange-mgt-status").val();
		exchange.searchObj.usageStatus = $("#search-exchange-usage-status").val()=="全部"?"":$("#search-exchange-usage-status").val();
		exchange.searchExchangeConfigInfo();
	};
	
	
	/*********************sharepoint配置管理*******************************/
	// 弹出新增Sharepoint window
	this.addSharepointInfo = function(){
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#addSharepointWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-150)/2 } });
    	$("#addSharepointWindow").jqxWindow('open');
	};
	
	// 提交新增Sharepoint信息
	this.addSharepointSubmit = function(){
		$('#addSharepointForm').jqxValidator('validate');
	};
	
	// 弹出编辑Sharepoint window
	this.editSharepointInfo = function(){
		var rowindex = $('#sharepointConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#sharepointConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
    		// 为编辑赋值
    		$("#resSidSharepoint").val(data.resSid);
    		sharepoint.setEditSharepointForm(data);
    		// 设置window显示位置
    		var windowW = $(window).width(); 
        	var windowH = $(window).height(); 
        	$("#editSharepointWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-150)/2 } });
        	$("#editSharepointWindow").jqxWindow('open');
    	}
		
	};
	
	// 删除Sharepoint信息
	this.removeSharepointInfo = function(){
		var rowindex = $('#sharepointConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#sharepointConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
    		Core.confirm({
				title:"提示",
				message:"确定要删除该Sharepoint服务吗?",
				yes:"确定",
				confirmCallback:function(){
					Core.AjaxRequest({
		 				url : ws_url + "/rest/sharepoints/delete/"+data.resSid+"",
		 				type:"get",
		 				callback : function (data) {
		 					sharepoint.searchSharepointConfigInfo();
		 			    },
		 			    failure:function(data){
		 			    	
		 			    }
		 			});
				}
		    });
    	}
	};
	
	// 提交编辑Sharepoint信息
	this.editSharepointSubmit = function(){
		$('#editSharepointForm').jqxValidator('validate');
	};
	
	// 条件查询Sharepoint配置管理
	this.searchSharepointConfigMgt = function(){
		sharepoint.searchObj.serviceAddress = $("#search-sharepoint-service-address").val();
		sharepoint.searchObj.manageStatus = $("#search-sharepoint-mgt-status").val()=="全部"?"":$("#search-sharepoint-mgt-status").val();
		sharepoint.searchObj.usageStatus = $("#search-sharepoint-usage-status").val()=="全部"?"":$("#search-sharepoint-usage-status").val();
		sharepoint.searchSharepointConfigInfo();
	};
};
  
  
  