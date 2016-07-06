var serviceBindModel = function(serviceCatalog, serviceMgt, serviceInstance) {
	
	/**************************************************** 服务管理********************************************/
	// 新增服务
	this.defineAddItem = function() {
		var ok =  $('#defineAddBtn').jqxButton('disabled');
		if (!ok) {
			// 判断审核按钮是否可用
			var tabindex = $('#jqxTabsDefine').jqxTabs('length');
			var contentId = "view" + tabindex;
			var contentDiv = "<div id='" + contentId + "'></div>";
			var tabTitle = "新增服务";
			var sameTitle = false;
			for(var i = 1; i < tabindex; i++ ) {
				var title = $('#jqxTabsDefine').jqxTabs('getTitleAt', i);
				//相同的服务不添加tab了
				if(title == tabTitle) {
					$('#jqxTabsDefine').jqxTabs('select', i);
					sameTitle = true;
					break;
				}
			}
			
			if ($("#addConfigWindow").length > 0) {
				$("#addConfigWindow").remove();
			}
			if ($("#addSpecWindow").length > 0) {
				$("#addSpecWindow").remove();
			}
			if ($("#addPerformanceWindow").length > 0) {
				$("#addPerformanceWindow").remove();
			}
			if ($("#addOperateWindow").length > 0) {
				$("#addOperateWindow").remove();
			}
			
			if(!sameTitle) {
				$('#jqxTabsDefine').jqxTabs('addLast', tabTitle, contentDiv);
				$("#" + contentId).load(ctx + "/pages/service/service-add.jsp",
					{
						"serviceSid" : "",
						"navigationBarIdAdd" : "navigationBarIdAdd" + tabindex,
						"parentCatalogSid" : $('#catalogSid').val(),
						"parentCatalogName" : $('#catalogName').val()
					});
				}
		}
	};
	
	// 更新服务
	this.defineUpdateItem = function() {
		var rowindex = $('#jqxgridDefine').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#defineDetailBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			
			var data = $('#jqxgridDefine').jqxGrid('getrowdata', rowindex);
			var tabindex = $('#jqxTabsDefine').jqxTabs('length');
			var contentId = "view" + tabindex;
			var contentDiv = "<div id='" + contentId + "'></div>";
			var tabTitle = '更新服务';
			
			var sameTitle = false;
			for(var i = 1; i < tabindex; i++ ) {
				var title = $('#jqxTabsDefine').jqxTabs('getTitleAt', i);
				//相同的服务不添加tab了
				if(title == tabTitle) {
					$('#jqxTabsDefine').jqxTabs('select', i);
					sameTitle = true;
					break;
				}
			}
			
			if ($("#updateConfigWindow").length > 0) {
				$("#updateConfigWindow").remove();
			}
			if ($("#updateSpecWindow").length > 0) {
				$("#updateSpecWindow").remove();
			}
			if ($("#updatePerformanceWindow").length > 0) {
				$("#updatePerformanceWindow").remove();
			}
			if ($("#updateOperateWindow").length > 0) {
				$("#updateOperateWindow").remove();
			}
			
			if(!sameTitle) {
				$('#jqxTabsDefine').jqxTabs('addLast', tabTitle, contentDiv);
				$("#" + contentId).load(ctx + "/pages/service/service-update.jsp",
					{
						"serviceSid" : data.serviceSid,
						"navigationBarIdUpdate" : "navigationBarIdUpdate" + tabindex,
						"parentCatalogSid" : $('#catalogSid').val(),
						"parentCatalogName" : $('#catalogName').val()
					});
				}
			}
			
	};
	
	
	// 查看服务
	this.defineDetailItem = function() {
		var rowindex = $('#jqxgridDefine').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#defineDetailBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			
			var data = $('#jqxgridDefine').jqxGrid('getrowdata', rowindex);
			var tabindex = $('#jqxTabsDefine').jqxTabs('length');
			var contentId = "view" + tabindex;
			var contentDiv = "<div id='" + contentId + "'></div>";
			var tabTitle = '查看-' + data.serviceName;
			
			var sameTitle = false;
			for(var i = 1; i < tabindex; i++ ) {
				var title = $('#jqxTabsDefine').jqxTabs('getTitleAt', i);
				//相同的服务不添加tab了
				if(title == tabTitle) {
					$('#jqxTabsDefine').jqxTabs('select', i);
					sameTitle = true;
					break;
				}
			}
			
			if(!sameTitle) {
				$('#jqxTabsDefine').jqxTabs('addLast', tabTitle, contentDiv);
				$("#" + contentId).load(ctx + "/pages/service/service-detail.jsp",
					{
						"serviceSid" : data.serviceSid,
						"jqxNavigationBarId" : "jqxNavigationBarId" + tabindex,
						"vConfigGrid" : "vConfigGrid" + tabindex,
						"vSpecGrid" : "vSpecGrid" + tabindex,
						"vPerformanceGrid" : "vPerformanceGrid" + tabindex,
						"vOperateGrid" : "vOperateGrid" + tabindex,
						"serviceName" : data.serviceName,
						"description" : data.description,
						"serviceTypeName" : data.serviceTypeName,
						"parentCatalogName" : data.parentCatalogName,
						"serviceStatusName" : data.serviceStatusName
					});
				}
			}
	};
	
	// 模板管理
	this.defineTemplateMgtItem = function() {
		var rowindex = $('#jqxgridDefine').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#defineDetailBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			// 获取信息
			var data = $('#jqxgridDefine').jqxGrid('getrowdata', rowindex);
			// 得到tab页数
			var tabindex = $('#jqxTabsDefine').jqxTabs('length');
			
			if (tabindex > 1) {
				$('#jqxTabsDefine').jqxTabs('removeAt', 1);
			}
			
			if ($("#addTempWindow").length > 0) {
				$("#addTempWindow").remove();
			}
			
			
			//var contentId = "template" + data.serviceSid;
			//var contentId = "templateContentId";
			var contentDiv = "<div id='templateContentId'></div>";
			var tabTitle = '模板-' + data.serviceName;
			
			var loadPage = function (url, param) {
				$.post(url, param, function (data) {
                    //$("#templateContentId").text(data);
                    $('#jqxTabsDefine').jqxTabs('setContentAt', 1, data); 
                });
			};
			
			
			$('#jqxTabsDefine').jqxTabs('addLast', tabTitle, contentDiv);
			
			
			$('#jqxTabsDefine').jqxTabs('setTitleAt', 1, tabTitle); 
			$('#jqxTabsDefine').jqxTabs('select', 1);

			loadPage(ctx + "/pages/service/service-template.jsp",{
				"serviceSid" : data.serviceSid,
				"serviceName" : data.serviceName
			});
			
			
//			var sameTitle = false;
//			for(var i = 1; i < tabindex; i++ ) {
//				var title = $('#jqxTabsDefine').jqxTabs('getTitleAt', i);
//				if(title == tabTitle) {
//					$('#jqxTabsDefine').jqxTabs('select', i);
//					sameTitle = true;
//					break;
//				}
//			}
			//$('#jqxTabsDefine').jqxTabs('select', 1);
			//$('#jqxTabsDefine').jqxTabs('setTitleAt', 1, tabTitle);
			//没有相同的title才加载
			//if(!sameTitle) {
				//$('#jqxTabsDefine').jqxTabs('addLast', tabTitle, contentDiv);
//				$("#" + contentId).load(
//					ctx + "/pages/service/service-template.jsp", {
//						"serviceSid" : data.serviceSid,
//						"templategrid" : "templategrid" + data.serviceSid,
//						"serviceName" : data.serviceName,
//						"addTempWindow" : "addTempWindow" + data.serviceSid,
//						"addTempForm" : "addTempForm" + data.serviceSid,
//						"editTempWindow" : "editTempWindow" + data.serviceSid,
//						"editTempForm" : "editTempForm" + data.serviceSid,
//						"addTempSpecdatagrid" : "addTempSpecdatagrid" + data.serviceSid,
//						"addSave" : "addSave" + data.serviceSid,
//						"editTempSpecdatagrid" : "editTempSpecdatagrid" + data.serviceSid,
//						"editSave" : "editSave" + data.serviceSid,
//						"addTempBtn" : "addTempBtn" + data.serviceSid,
//						"editTempBtn" : "editTempBtn" + data.serviceSid,
//						"delTempBtn" : "delTempBtn" + data.serviceSid,
//						"add-templateName" : "add-templateName" + data.serviceSid,
//						"add-serviceName" : "add-serviceName" + data.serviceSid,
//						"add-templateStatus" : "add-templateStatus" + data.serviceSid,
//						"add-billingPlanSid" : "add-billingPlanSid" + data.serviceSid,
//						"add-imagePath" : "add-imagePath" + data.serviceSid,
//						"add-expiringDate" : "add-expiringDate" + data.serviceSid,
//						"add-description" : "add-description" + data.serviceSid,
//						"edit-templateName" : "edit-templateName" + data.serviceSid,
//						"edit-serviceName" : "edit-serviceName" + data.serviceSid,
//						"edit-templateStatus" : "edit-templateStatus" + data.serviceSid,
//						"edit-billingPlanSid" : "edit-billingPlanSid" + data.serviceSid,
//						"edit-imagePath" : "edit-imagePath" + data.serviceSid,
//						"edit-expiringDate" : "edit-expiringDate" + data.serviceSid,
//						"edit-description" : "edit-description" + data.serviceSid,
//					});
			//}

		}
	};

	// 发布服务
	this.defineDeployItem = function() {
		var rowindex = $('#jqxgridDefine').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#defineDeployBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgridDefine').jqxGrid('getrowdata', rowindex);
			Core.confirm({
				title:"请选择",
				message:"您确定发布该服务吗？",
				confirmCallback:function(){
					Core.AjaxRequest({
		 				url : ws_url + "/rest/services/platform/operationService?serviceSid="+data.serviceSid+"&action="+"03",
		 				type:"get",
		 				callback : function (data) {
		 					Core.alert({
		 						title:"提示",
		 						message:"发布成功！",
		 						callback:function(){
		 							serviceMgt.refreshServiceMgtContent();
		 						}
		 					});
		 					
		 			    },
		 			    failure:function(data){
		 			    	Core.alert({
		 						title:"提示",
		 						message:"发布失败！",
		 						callback:function(){
		 							//user.searchUserInfo();
		 						}
		 					});
		 			    }
		 			});
				}
		});
		}
	};
	
	// 禁用服务
	this.defineDisableItem = function() {
		var rowindex = $('#jqxgridDefine').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#defineDeployBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgridDefine').jqxGrid('getrowdata', rowindex);
			Core.confirm({
				title:"请选择",
				message:"您确定禁用该服务吗？",
				confirmCallback:function(){
					Core.AjaxRequest({
		 				url : ws_url + "/rest/services/platform/operationService?serviceSid="+data.serviceSid+"&action="+"05",
		 				type:"get",
		 				callback : function (data) {
		 					Core.alert({
		 						title:"提示",
		 						message:"禁用成功！",
		 						callback:function(){
		 							serviceMgt.refreshServiceMgtContent();
		 						}
		 					});
		 					
		 			    },
		 			    failure:function(data){
		 			    	Core.alert({
		 						title:"提示",
		 						message:"禁用失败！",
		 						callback:function(){
		 							//user.searchUserInfo();
		 						}
		 					});
		 			    }
		 			});
				}
		});
		}
	};
	/**************************************************** 服务管理********************************************/

	/**************************************************** 服务实例********************************************/
	// 启用
	this.startItem = function() {
		var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#startBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
			Core.confirm({
				title : "提示",
				message : "确定要启用该服务实例吗?",
				yes : "确定",
				confirmCallback : function() {
					Core.AjaxRequest({
						url : ws_url
								+ "/rest/serviceInstance/serviceInstanceOperation",
						params : {
							serviceInstanceSid : data.instanceSid,
							action : "resume"
						},
						type : "post",
						callback : function(data) {
							Core.alert({
								title : "提示",
								message : "启用成功！",
								confirmCallback : function() {
									serviceInstance.searchServiceInstance();
								}
							});
						},
						failure : function(data) {
							Core.alert({
								title : "提示",
								message : "启用失败！"
							});
						}
					});
				}
			});
		}
	};

	// 禁用
	this.disableItem = function() {
		var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#disableBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
			Core.confirm({
				title : "提示",
				message : "确定要禁用该服务实例吗?",
				yes : "确定",
				confirmCallback : function() {
					Core.AjaxRequest({
						url : ws_url + "/rest/serviceInstance/serviceInstanceOperation",
						params : {
							serviceInstanceSid : data.instanceSid,
							action : "suspend"
						},
						type : "post",
						callback : function(data) {
							Core.alert({
								title : "提示",
								message : "禁用成功！",
								confirmCallback : function() {
									serviceInstance.searchServiceInstance();
								}
							});
						},
						failure : function(data) {
							Core.alert({
								title : "提示",
								message : "禁用失败！"
							});
						}
					});
				}
			});
		}
	};
	// 退订
	this.releaseItem = function() {
		var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#releaseBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
			Core.confirm({
				title : "提示",
				message : "确定要退订该服务实例吗?",
				yes : "确定",
				confirmCallback : function() {
					Core.AjaxRequest({
						url : ws_url + "/rest/serviceInstance/serviceInstanceOperation",
						params : {
							serviceInstanceSid : data.instanceSid,
							action : "release"
						},
						type : "post",
						callback : function(data) {
							Core.alert({
								title : "提示",
								message : "退订成功！",
								confirmCallback : function() {
									serviceInstance
											.searchServiceInstance();
								}
							});
						},
						failure : function(data) {
							Core.alert({
								title : "提示",
								message : "退订失败！"
							});
						}
					});
				}
			});
		}
	};

	// 详情
	this.viewItem = function() {

		var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
		var ok = $("#viewBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
			$("#instanceSid").val(data.instanceSid);
			// 将常用的字段可以使用这个方法设置数据
			serviceInstance.setViewServiceInstanceDetail(data);

			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#popupWindow").jqxWindow({
				position : {
					x : (windowW - 800) / 2,
					y : (windowH - 450) / 2
				}
			});
			$("#popupWindow").jqxWindow('open');
		}
	};

	// 查询服务实例
	this.searchServiceInstance = function() {
		serviceInstance.searchServiceInstance();
	};
	/**************************************************** 服务实例********************************************/
	
	/**************************************************** 服务目录********************************************/

	// 新增
	this.catalogAddItem = function() {
		var ok =  $('#catalogAddBtn').jqxButton('disabled');
		if(!ok) {
			// 初始化用户新增页面
	        $("#add-catalogName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
	        $("#add-imagePath").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
	        $("#add-description").jqxInput({placeHolder: "", height: 46, width: 250, minLength: 1,theme:currentTheme});
	        $("#add-catalogName").val("");
	        $("#add-imagePath").val("");
	        $("#add-description").val("");
			// 初始化新增window位置
	    	var windowW = $(window).width(); 
	    	var windowH = $(window).height(); 
	    	$("#addCatalogWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-200)/2 } });
	    	$("#addCatalogWindow").jqxWindow('open');
		}	
	};
	
	/** 提交新增用户的信息 */
    this.addCatalogSubmit = function(){
    	// 判断是否通过了验证
    	$('#addCatalogForm').jqxValidator('validate');
    };
    
	// 更新
	this.catalogEditItem = function() {
		// 初始化用户新增页面
        $("#edit-catalogName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
        $("#edit-imagePath").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
        $("#edit-description").jqxInput({placeHolder: "", height: 46, width: 250, minLength: 1,theme:currentTheme});
        
		var rowindex = $('#jqxgridCatalog').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		    var data = $('#jqxgridCatalog').jqxGrid('getrowdata', rowindex);
    		    $("#catalogSid").val(data.catalogSid);
    		    
    		    // 将常用的字段可以使用这个方法设置数据
    		    serviceCatalog.setEditCatalogForm(data);
    		   
    		    var windowW = $(window).width(); 
    	    	var windowH = $(window).height(); 
    	    	$("#editCatalogWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-200)/2 } });
    	    	$("#editCatalogWindow").jqxWindow('open');
    	}
	};
	
	   /** 提交编辑服务目录信息 */
    this.editCatalogSubmit = function(){
    	// 判断是否通过了验证
    	$('#editCatalogForm').jqxValidator('validate');
    };
    
	
	  
  /** 删除目录 */
  this.catalogDelItem = function () {
  	var rowindex = $('#jqxgridCatalog').jqxGrid('getselectedrowindex');
  	if(rowindex >= 0){
  		var data = $('#jqxgridCatalog').jqxGrid('getrowdata', rowindex);
		    	Core.confirm({
					title:"提示",
					message:"确定要删除该服务目录吗？",
					confirmCallback:function(){
						Core.AjaxRequest({
							// sunyu update for #131
			 				//url : ws_url + "/rest/services/platform/deleteCatalog?catalogSid="+data.catalogSid,
							url : ws_url + "/rest/services/platform/deleteCatalog/"+data.catalogSid,
			 				type:"get",
			 				callback : function (data) {
//			 					Core.alert({
//			 						title:"提示",
//			 						message:"删除成功！",
//			 						callback:function(){
			 							serviceCatalog.initServiceCatalogTree();
			 							// update for #132
			 							//serviceCatalog.searchServiceCatalogContent();
			 							serviceCatalog.updateServiceCatalogDatagrid();		 						
			 						//}
			 					//});
			 			    },
//			 			    failure:function(data){
//			 			    	Core.alert({
//			 						title:"提示",
//			 						message:"删除失败！"
//			 					});
//			 			    }
			 			    // end
			 			});
					}
			});
  	}
};
/** 更新配置的信息 */
this.updateConfigSubmit = function(){
	// 判断是否通过了验证
	$('#updateConfigForm').jqxValidator('validate');
};
/** 更新规格的信息 */
this.updateSpecSubmit = function(){
	// 判断是否通过了验证
	$('#updateSpecForm').jqxValidator('validate');
};
/** 更新性能的信息 */
this.updatePerformanceSubmit = function(){
	// 判断是否通过了验证
	$('#updatePerformanceForm').jqxValidator('validate');
};
/** 更新操作的信息 */
this.updateOperateSubmit = function(){
	// 判断是否通过了验证
	$('updateOperateForm').jqxValidator('validate');
};
this.updateServiceMgtSubmit = function(){
	$("#updateServiceForm").jqxValidator('validate');	
};
/**************************************************** 服务目录********************************************/

/** 编辑配置 */
this.updateConfigItem = function(){
	var ok = $("#updateConfigBtn").jqxButton("disabled");
	var rowindex = $('#vUpdateConfigGrid').jqxGrid('getselectedrowindex');
	if (rowindex >= 0 && !ok) {
		// 初始化新增window位置
		var windowW = $(window).width();
		var windowH = $(window).height();
		$("#updateConfigWindow").jqxWindow({
			position : {
				x : (windowW - 500) / 2,
				y : (windowH - 200) / 2
			}
		});
		$("#updateConfigWindow").jqxWindow('open');
	}
};

/** 编辑规格 */
this.updateSpecItem = function(){
	var ok = $("#updateSpecBtn").jqxButton("disabled");
	var rowindex = $('#vUpdateSpecGrid').jqxGrid('getselectedrowindex');
	if (rowindex >= 0 && !ok) {
		// 初始化新增window位置
		var windowW = $(window).width();
		var windowH = $(window).height();
		$("#updateSpecWindow").jqxWindow({
			position : {
				x : (windowW - 500) / 2,
				y : (windowH - 200) / 2
			}
		});
		$("#updateSpecWindow").jqxWindow('open');
	}
};

/** 编辑性能 */
this.updatePerformanceItem = function(){
	var ok = $("#updatePerformanceBtn").jqxButton("disabled");
	var rowindex = $('#vUpdatePerformanceGrid').jqxGrid('getselectedrowindex');
	if (rowindex >= 0 && !ok) {
		// 初始化新增window位置
		var windowW = $(window).width();
		var windowH = $(window).height();
		$("#updatePerformanceWindow").jqxWindow({
			position : {
				x : (windowW - 500) / 2,
				y : (windowH - 200) / 2
			}
		});
		$("#updatePerformanceWindow").jqxWindow('open');
	}
};

/** 编辑操作 */
this.updateOperateItem = function(){
	var ok = $("#updateOperateBtn").jqxButton("disabled");
	var rowindex = $('#vUpdateOperateGrid').jqxGrid('getselectedrowindex');
	if (rowindex >= 0 && !ok) {
		// 初始化新增window位置
		var windowW = $(window).width();
		var windowH = $(window).height();
		$("#updateOperateWindow").jqxWindow({
			position : {
				x : (windowW - 500) / 2,
				y : (windowH - 200) / 2
			}
		});
		$("#updateOperateWindow").jqxWindow('open');
	}
};
}
