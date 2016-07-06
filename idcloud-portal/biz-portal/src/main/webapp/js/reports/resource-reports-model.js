var resourceModel = function() {
	var me = this;
	this.items = ko.observableArray();
	this.searchObj = {
		tenantSid : "",
		perfLevel : "",
	};
	// 用户数据
	this.searchResourceReport = function() {
		Core.AjaxRequest({
			url : ws_url + "/rest/reports/resource/host",
			type : 'post',
			dataType : "text",
			params : me.searchObj,
			callback : function(data) {
				$("#resourceDatagrid").html(data);
			}
		});
	};

	// 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	this.initInput = function() {
		// 初始化查询组件
		$("#search-resource-button").jqxButton({
			width : '60px',
			height : '25px',
			theme : currentTheme
		});
		$("#excel-resource-button").jqxButton({
			disabled : true,
			width : '90px',
			height : '25px',
			theme : currentTheme
		});
		$("#pdf-resource-button").jqxButton({
			disabled : true,
			width : '80px',
			height : '25px',
			theme : currentTheme
		});

		// 初始化下拉列表框
		var codesearch = new codeModel({
			width : 120,
			autoDropDownHeight : true
		});
		codesearch.getCommonCode("search-level", "PERF_LEVEL", true);
		codesearch.getCustomCode("search-tenants", "/tenants/", "tenantName",
				"tenantSid", true, "POST", {});

	};
	// 初始化用户datagrid的数据源
	this.initResourceDatagrid = function() {
		Core.AjaxRequest({
			url : ws_url + "/rest/reports/resource/host",
			type : 'post',
			params : me.searchObj,
			async : false,
			callback : function(data) {
				$("#resourceDatagrid").html(data);
			}
		});

	};

	// 初始化資源樹
	this.initTreeReports = function() {
		var data = [ {
			id : "0",
			parentid : "-1",
			expanded : true,
			selected : true,
			icon : ctx + "/images/icon/region.png",
			text : "资源信息统计报表",
			value : "1"
		}, {
			id : "1",
			parentid : "0",
			icon : ctx + "/images/icon/region.png",
			text : "虚拟机资源",
			value : "1"
		}, {
			id : "2",
			parentid : "0",
			icon : ctx + "/images/icon/region.png",
			text : "块存储资源",
			value : "1"
		}, {
			id : "3",
			parentid : "0",
			icon : ctx + "/images/icon/region.png",
			text : "对象存储资源",
			value : "1"
		}, {
			id : "4",
			parentid : "0",
			icon : ctx + "/images/icon/region.png",
			text : "公网IP资源",
			value : "1"
		}, {
			id : "5",
			parentid : "0",
			icon : ctx + "/images/icon/region.png",
			text : "内网IP资源",
			value : "1"
		}, {
			id : "6",
			parentid : "0",
			icon : ctx + "/images/icon/region.png",
			text : "客户邮箱资源",
			value : "1"
		}, {
			id : "7",
			parentid : "0",
			icon : ctx + "/images/icon/region.png",
			text : "SharePoint资源",
			value : "1"
		}, {
			id : "8",
			parentid : "-1",
			expanded : true,
			icon : ctx + "/images/icon/region.png",
			text : "资费信息统计报表",
			value : "1"
		}, {
			id : "9",
			parentid : "8",
			icon : ctx + "/images/icon/region.png",
			text : "月资费账单",
			value : "1"
		}, {
			id : "10",
			parentid : "8",
			icon : ctx + "/images/icon/region.png",
			text : "季度资费账单",
			value : "1"
		}, {
			id : "11",
			parentid : "8",
			icon : ctx + "/images/icon/region.png",
			text : "年资费账单",
			value : "1"
		}, {
			id : "12",
			parentid : "-1",
			expanded : true,
			icon : ctx + "/images/icon/region.png",
			text : "客户邮箱资源",
			value : "1"
		}, {
			id : "13",
			parentid : "12",
			icon : ctx + "/images/icon/region.png",
			text : "用户信息",
			value : "1"
		}, {
			id : "14",
			parentid : "12",
			icon : ctx + "/images/icon/region.png",
			text : "服务信息",
			value : "1"
		}, {
			id : "15",
			parentid : "12",
			icon : ctx + "/images/icon/region.png",
			text : "订单信息",
			value : "1"
		} ];

		var source = {
			datatype : "json",
			datafields : [ {
				name : 'id'
			}, {
				name : 'parentid'
			}, {
				name : 'text'
			}, {
				name : 'value'
			}, {
				name : 'icon'
			}, {
				name : 'expanded'
			}, {
				name : 'selected'
			} ],
			id : 'id',
			icon : 'icon',
			expanded : 'expanded',
			selected : 'selected',
			localdata : data
		};
		// create data adapter.
		var dataAdapter = new $.jqx.dataAdapter(source);
		// perform Data Binding.
		dataAdapter.dataBind();
		var records = dataAdapter.getRecordsHierarchy('id', 'parentid',
				'items', [ {
					name : 'text',
					map : 'label'
				} ]);

		// create jqxTree
		$('#jqxTreeReports').jqxTree({
			source : records,
			width : '99.7%',
			height : '100%'
		});

		// 当选择某个item的时候
		$('#jqxTreeReports')
				.on(
						'select',
						function(event) {
							var args = event.args;
							var item = $('#jqxTreeReports').jqxTree('getItem',
									args.element);
							if (item.label == "资源信息统计报表") {
								$("#datacenterTabs").remove();
								$("#mainSplitterStorage .listContent")
										.load(
												ctx
														+ "/pages/respool/datacenter-template-index.jsp",
												function() {
													initDatacenterTabs();
												});
							}
						});
	};

};
var reportsBindModel = function(resource) {
	/** ***********************资源报表绑定事件******************************* */
	/** 查询资源报表 */
	this.searchResource = function() {
		resource.searchObj.tenantSid = $("#search-tenants").val()=="全部"?"":$("#search-tenants").val();
		resource.searchObj.perfLevel = $("#search-level").val()=="全部"?"":$("#search-level").val();
		resource.searchResourceReport();
		$("#reportsDisplayDIV").css({display:"block"});
		$("#excel-resource-button").jqxButton({ disabled: false});
		$("#pdf-resource-button").jqxButton({ disabled: false});
	};
    
	// 導出Excel
	this.excelResourceReport = function() {
		// 判断按钮是否可用
      	var ok =  $("#excel-resource-button").jqxButton("disabled");
      	if (!ok) {
      		resource.searchObj.tenantSid = $("#search-tenants").val()=="全部"?"":$("#search-tenants").val();
    		resource.searchObj.perfLevel = $("#search-level").val()=="全部"?"":$("#search-level").val();
    		resource.searchResourceReport();
    		window.open(ws_url + "/rest/reports/resource/host/excel/" + JSON.stringify(resource.searchObj));
      	}
	};
	
	// 導出PDF
	this.pdfResourceReport = function() {
		// 判断按钮是否可用
      	var ok =  $("#pdf-resource-button").jqxButton("disabled");
      	if (!ok) {
			resource.searchObj.tenantSid = $("#search-tenants").val()=="全部"?"":$("#search-tenants").val();
			resource.searchObj.perfLevel = $("#search-level").val()=="全部"?"":$("#search-level").val();
			resource.searchResourceReport();
			window.open(ws_url + "/rest/reports/resource/host/pdf/" + JSON.stringify(resource.searchObj));
      	}
	};
};
