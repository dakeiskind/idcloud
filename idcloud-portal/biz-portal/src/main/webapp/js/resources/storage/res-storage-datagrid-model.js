var storageConfigMgtModel = function() {
	var me = this;
	this.items = ko.observableArray();
	this.searchObj = {
		// storageNameLike:"",
		// storageType:"",
		// hardDiskType:"",
		"qm.storageNameLike" : null,
		"qm.storageCategory" : null,
		"qm.hardDiskType" : null,
		"qm.storagePurpose" : null,
		// "qm.resStorageClassSid":null,
		"qm.resTopologyType" : resTopologyType,
		"qm.resTopologySid" : resTopologySid,
		"qm.resStorageSid" : null
	};
	this.VData = new Object();
	// 用户数据
	this.searchStorageConfigInfo = function() {
		var dataAdapter = Core.getPagingDataAdapter({
			gridId : "storageConfigdatagrid",
			url : ws_url + "/rest/storages",
			params : me.searchObj
		});
		$("#storageConfigdatagrid").jqxGrid({
			source : dataAdapter,
			rendergridrows : function() {
				return dataAdapter.records;
			}
		});
	};

	// 统计资源分层结构下的存储信息
	this.StorageResourcesStatisticsInTopologyRz = function(resTopologySid) {
		var storageData = null;
		Core.AjaxRequest({
			url : ws_url + "/rest/storages/statistical/topology/" + resTopologySid,
			type : 'get',
			async : false,
			callback : function(data) {
				storageData = data;
			}
		});
		var data = new Object();
		data.storageCount = storageData.staTotalStorage;
		data.attr = new Array();
		var value = [ storageData.staUsableStorage,
				storageData.staFaultStorage, storageData.staUnusableStorage ];
		var name = [ "可用", "故障", "不可用" ];
		for (var i = 0; i < 3; i++) {
			var obj = new Object();
			var val = (value[i] == null) ? 0 : value[i];
			obj.name = name[i] + "(" + val + ")";
			obj.value = val;
			data.attr.push(obj);
		}
		return data;
	};

	// 统计资源分层结构下的存储信息
	this.StorageResourcesStatisticsVolumeInTopologyRz = function(resTopologySid) {
		var storageData = null;
		Core.AjaxRequest({
			url : ws_url + "/rest/storages/statistical/rz/volume/"+ resTopologySid,
			type : 'get',
			async : false,
			callback : function(data) {
				storageData = data;
			}
		});
		var data = new Object();
		data.storageCount = storageData.totalCapacity;
		data.attr = new Array();
		var value = [ storageData.hadUsedCapacity,
				storageData.availableCapacity ];
		var name = [ "已使用", "未使用" ];
		for (var i = 0; i < 2; i++) {
			var obj = new Object();
			var val = (value[i] == null) ? 0 : value[i];
			obj.name = name[i] + "(" + val + ")";
			obj.value = val;
			data.attr.push(obj);
		}
		return data;
	};

	// 统计资源拓扑结构下的存储信息
	this.StorageResourcesStatisticsInTopology = function(resTopologySid) {
		var storageData = null;
		Core.AjaxRequest({
			url : ws_url + "/rest/storages/statistical/topology/"
					+ resTopologySid,
			type : 'get',
			async : false,
			callback : function(data) {

				storageData = data;
			}
		});
		var data = new Object();
		data.storageCount = storageData.staTotalStorage;
		data.attr = new Array();
		var value = [ storageData.staUsableStorage,
				storageData.staFaultStorage, storageData.staUnusableStorage ];
		var name = [ "可用", "故障", "不可用" ];
		for (var i = 0; i < 3; i++) {
			var obj = new Object();
			var val = value[i] == null ? 0 : value[i];
			obj.name = name[i] + "(" + val + ")";
			obj.value = val;
			data.attr.push(obj);
		}
		return data;
	};

	// 统计资源拓扑结构下的存储容量信息
	this.StorageResourcesStatisticsVolumeInTopology = function(resTopologySid) {
		var storageData = null;
		Core.AjaxRequest({
			url : ws_url + "/rest/storages/statistical/topology/volume/"
					+ resTopologySid,
			type : 'get',
			async : false,
			callback : function(data) {
				storageData = data;
			}
		});
		var data = new Object();
		data.storageCount = storageData.totalCapacity;
		data.attr = new Array();
		var value = [ storageData.hadUsedCapacity,
				storageData.availableCapacity ];
		var name = [ "已使用", "未使用" ];
		for (var i = 0; i < 2; i++) {
			var obj = new Object();
			var val = (value[i] == null) ? 0 : value[i];
			obj.name = name[i] + "(" + val + ")";
			obj.value = val;
			data.attr.push(obj);
		}
		return data;
	};

	// 统计集群下的存储信息
	this.StorageResourcesStatisticsInCluster = function(resTopologySid) {
		var storageData = null;
		Core.AjaxRequest({
			url : ws_url + "/rest/storages/statistical/cluster/"
					+ resTopologySid,
			type : 'get',
			async : false,
			callback : function(data) {
				storageData = data;
			}
		});
		var data = new Object();
		data.storageCount = storageData.staTotalStorage;
		data.attr = new Array();
		var value = [ storageData.staUsableStorage,
				storageData.staFaultStorage, storageData.staUnusableStorage ];
		var name = [ "可用", "故障", "不可用" ];
		for (var i = 0; i < 3; i++) {
			var obj = new Object();
			var val = value[i] == null ? 0 : value[i];
			obj.name = name[i] + "(" + val + ")";
			obj.value = val;
			data.attr.push(obj);
		}
		return data;
	};

	// 统计集群下的外置存储容量信息
	this.StorageResourcesStatisticsVolumeInCluster = function(resTopologySid) {
		var storageData = null;
		Core.AjaxRequest({
			url : ws_url + "/rest/storages/statistical/cluster/volume/"
					+ resTopologySid,
			type : 'get',
			async : false,
			callback : function(data) {
				storageData = data;
			}
		});
		var data = new Object();
		data.storageCount = storageData.totalCapacity;
		data.attr = new Array();
		var value = [ storageData.hadUsedCapacity,
				storageData.availableCapacity ];
		var name = [ "已使用", "未使用" ];
		for (var i = 0; i < 2; i++) {
			var obj = new Object();
			var val = (value[i] == null) ? 0 : value[i];
			obj.name = name[i] + "(" + val + ")";
			obj.value = val;
			data.attr.push(obj);
		}
		return data;
	};

	// 统计主机下的存储信息
	this.StorageResourcesStatisticsInHost = function(resTopologySid) {
		var storageData = null;
		Core.AjaxRequest({
			url : ws_url + "/rest/storages/statistical/host/" + resTopologySid,
			type : 'get',
			async : false,
			callback : function(data) {
				storageData = data;
			}
		});
		var data = new Object();
		data.storageCount = storageData.staTotalStorage;
		data.attr = new Array();
		var value = [ storageData.staUsableStorage,
				storageData.staFaultStorage, storageData.staUnusableStorage ];
		var name = [ "可用", "故障", "不可用" ];
		for (var i = 0; i < 3; i++) {
			var obj = new Object();
			var val = value[i] == null ? 0 : value[i];
			obj.name = name[i] + "(" + val + ")";
			obj.value = val;
			data.attr.push(obj);
		}
		return data;
	};

	// 统计主机下的存储容量信息
	this.StorageResourcesStatisticsVolumeInHost = function(resTopologySid) {
		var storageData = null;
		Core.AjaxRequest({
			url : ws_url + "/rest/storages/statistical/host/volume/"
					+ resTopologySid,
			type : 'get',
			async : false,
			callback : function(data) {
				storageData = data;
			}
		});
		var data = new Object();
		data.storageCount = storageData.totalCapacity;
		data.attr = new Array();
		var value = [ storageData.hadUsedCapacity,
				storageData.availableCapacity ];
		var name = [ "已使用", "未使用" ];
		for (var i = 0; i < 2; i++) {
			var obj = new Object();
			var val = (value[i] == null) ? 0 : value[i];
			obj.name = name[i] + "(" + val + ")";
			obj.value = val;
			data.attr.push(obj);
		}
		return data;
	};

	// 统计集群下的存储信息
	this.StorageResourcesStatisticsInRsc = function(resTopologySid) {
		var storageData = null;
		Core.AjaxRequest({
			url : ws_url + "/rest/storages/statistical/rsc/" + resTopologySid,
			type : 'get',
			async : false,
			callback : function(data) {
				storageData = data;
			}
		});
		var data = new Object();
		data.storageCount = storageData.staTotalStorage;
		data.attr = new Array();
		var value = [ storageData.staUsableStorage,
				storageData.staFaultStorage, storageData.staUnusableStorage ];
		var name = [ "可用", "故障", "不可用" ];
		for (var i = 0; i < 3; i++) {
			var obj = new Object();
			var val = (value[i] == null) ? 0 : value[i];
			obj.name = name[i] + "(" + val + ")";
			obj.value = val;
			data.attr.push(obj);
		}
		return data;
	};

	// 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	this.initInput = function() {
		// 初始化查询组件
		$("#search-storage-name").jqxInput({
			placeHolder : "",
			height : 22,
			width : 100,
			minLength : 1,
			theme : currentTheme
		});
		$("#search-storage-button").jqxButton({
			width : '50px',
			height : '25px',
			theme : currentTheme
		});
		// 初始化下拉列表框
		var codesearch = new codeModel({
			width : 120,
			autoDropDownHeight : true
		});
		codesearch.getCommonCode("search-storage-type", "STORAGE_CATEGORY",
				true);
		// codesearch.getCommonCode("search-storage-mgt-status","HARD_DISK_TYPE",true);
		// codesearch.getCustomCode("search-storage-mgt-classify","/topology/findStorageClassify/"+resTopologySid,"resTopologyName","resTopologySid",true,"GET");
		// codesearch.getCommonCode("search-storage-usage-status","USAGE_STATUS",true);
		codesearch.getCommonCode("search-storage-storagePurpose",
				"STORAGE_PURPOSE", true);
	};

	// 初始化用户datagrid的数据源
	this.initStorageDatagrid = function() {
		var dataAdapter = Core.getPagingDataAdapter({
			gridId : "storageConfigdatagrid",
			url : ws_url + "/rest/storages",
			params : me.searchObj
		});
		$("#storageConfigdatagrid").jqxGrid({
			                width : "100%",
							theme : currentTheme,
							source : dataAdapter,
							virtualmode : true,
							rendergridrows : function() {
								return dataAdapter.records;
							},
							columnsresize : true,
							pageable : true,
							pagesize : 10,
							autoheight : true,
							rowsheight: 28,
							autowidth : false,
							sortable : true,
							// selectionmode:"checkbox",
							localization : gridLocalizationObj,
							columns : [ {
								text : '存储名称',
								datafield : 'storageName'
							}, {
								text : '存储类型',
								datafield : 'storageType',
								align : 'center',
								cellsalign : 'center'
							}, {
								text : '存储类别',
								datafield : 'storageCategoryName',
								align : 'center',
								cellsalign : 'center'
							}, {
								text : '存储用途',
								datafield : 'storagePurposeName',
								align : 'center',
								cellsalign : 'center'
							}, {
								text : '总容量(GB)',
								datafield : 'totalCapacity',
								cellsalign : 'right',
								width : 80
							}, {
								text : '已用容量(GB)',
								datafield : 'hadUsedCapacity',
								cellsalign : 'right',
								width : 80
							}, {
								text : '使用率',
								datafield : 'storageUsage',
								cellsalign : 'right',
								width : 80
							}, {
								text : '分配容量(GB)',
								datafield : 'allotCapacity',
								cellsalign : 'right',
								width : 80
							}, {
								text : '分配率',
								datafield : 'storageRate',
								cellsalign : 'right',
								width : 80
							}, {
								text : '状态',
								datafield : 'statusName',
								align : 'center',
								cellsalign : 'center',
								width : 120
							},{
								text : '操作',
								datafield : '',
								align : 'center',
								cellsalign : 'center',
								width : 80,
								cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
			 			        	  return "<div class='customButton' style='margin-top:4px;margin-left:10px;' onhover='background-color: #0099d7;' onclick='viewStorageInfo("+row+")'>详情</div>";
			 			             }
							} ],
							showtoolbar : true,
							// 设置toolbar操作按钮
							rendertoolbar : function(toolbar) {
								var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
				                var refreshBtn = $("<div><a id='jqxRefreshStorageBtn' onclick='searchStorageConfigMgt()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
								var addStoragesBtn = $("<div><a id='jqxAddStoragesBtn' onclick='addStoragesWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>添加&nbsp;&nbsp;</a></div>");
								var editStoragesBtn = $("<div><a id='jqxEditStoragesBtn' onclick='editStoragesInfoWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
								var extendStoragesBtn = $("<div><a id='jqxExtendStoragesBtn' onclick='extendStoragesInfoWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-hdd'></i>扩容&nbsp;&nbsp;</a></div>");
								var deleteStoragesBtn = $("<div><a id='jqxDeleteStoragesBtn' onclick='removeStoragesInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
								var exportBtn = $("<div><a id='jqxExportStorageBtn' onclick='exportTopologyStorageDatagrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-export'></i>导出&nbsp;&nbsp;</a></div>");
								var typeBtn = $("<div><a id='jqxStorageTypeBtn' onclick='popSetStorageTypeWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cog'></i>设置存储类型&nbsp;&nbsp;</a></div>");
								toolbar.append(container);
								if ("DC" == resTopologyType) {
									container.append(exportBtn);
									container.append(typeBtn);
									$("#jqxExportStorageBtn").jqxButton({
										width : '60',
										theme : currentTheme,
										height : '18px',
										disabled : false
									});
									$("#jqxStorageTypeBtn").jqxButton({
										width : '60',
										theme : currentTheme,
										height : '18px',
										disabled : true
									});
								} else {
									container.append(refreshBtn);
									container.append(addStoragesBtn);
									container.append(editStoragesBtn);
									container.append(extendStoragesBtn);
									container.append(deleteStoragesBtn);
									container.append(exportBtn);
									container.append(typeBtn);
									initStorageMenu();
								}
								if ("VMware" == resTopologyType || "Other" == resTopologyType) {
									$("#jqxAddStoragesBtn").jqxButton({
										disabled : true
									});

								}else if("VC" == resTopologyType ){
									Core.AjaxRequest({
							 			url : ws_url + "/rest/topologys/virtual/tree",
							 			type:'post',
							 			params:{},
							 			callback : function (data) {
							 				for (var i = 0; i < data.length; i++) {
							 					var attr = data[i].topologyValue.split(",");
												if(attr[1] == "HOST" && data[i].parentTopologySid == resTopologySid){
													$("#jqxAddStoragesBtn").jqxButton({disabled : false});
													break;

												}
											}
							 			}
							 	    });
								}else{
									//$("#jqxAddStoragesBtn").jqxButton({disabled : true});
								}
								

							}
						});

		$("#storageConfigdatagrid").on('rowselect',
				function(event) {
					var args = event.args;
					var index = args.rowindex;
					var data = $('#storageConfigdatagrid').jqxGrid(
							'getrowdata', index);
					$("#jqxEditStoragesBtn").jqxButton({
						disabled : false
					});
					$("#jqxExtendStoragesBtn").jqxButton({
						disabled : false
					});
					$("#jqxDeleteStoragesBtn").jqxButton({
						disabled : false
					});
					$("#jqxStorageTypeBtn").jqxButton({
						width : '60',
						theme : currentTheme,
						height : '18px',
						disabled : false
					});
				});
		/*
		 * $("#jqxAddStoragesBtn").jqxButton({width:
		 * '60',theme:currentTheme,height: '18px'});
		 * $("#jqxEditStoragesBtn").jqxButton({width:
		 * '60',theme:currentTheme,height: '18px'});
		 * $("#jqxDeleteStoragesBtn").jqxButton({width:
		 * '60',theme:currentTheme,height: '18px'});
		 * $("#jqxExportStorageBtn").jqxButton({width:
		 * '60',theme:currentTheme,height: '18px', disabled: false });
		 * $("#jqxStorageTypeBtn").jqxButton({width:
		 * '60',theme:currentTheme,height: '18px', disabled: true });
		 */
	};

};
function initStorageMenu() {
	$("#jqxRefreshStorageBtn").jqxButton({
		width : '60',
		theme : currentTheme,
		height : '18px',
		disabled : false
	});
	$("#jqxAddStoragesBtn").jqxButton({
		width : '60',
		theme : currentTheme,
		height : '18px',
		disabled : true
	});
	$("#jqxEditStoragesBtn").jqxButton({
		width : '60',
		theme : currentTheme,
		height : '18px',
		disabled : true
	});
	$("#jqxExtendStoragesBtn").jqxButton({
		width : '60',
		theme : currentTheme,
		height : '18px',
		disabled : true
	});
	$("#jqxDeleteStoragesBtn").jqxButton({
		width : '60',
		theme : currentTheme,
		height : '18px',
		disabled : true
	});
	$("#jqxExportStorageBtn").jqxButton({
		width : '60',
		theme : currentTheme,
		height : '18px',
		disabled : false
	});
	$("#jqxStorageTypeBtn").jqxButton({
		width : '60',
		theme : currentTheme,
		height : '18px',
		disabled : false
	});
}
// 查询资源环境
function searchStorageConfigMgt() {
	var storage = new storageConfigMgtModel();
	storage.searchObj["qm.storageNameLike"] = $("#search-storage-name").val();
	storage.searchObj["qm.storageCategory"] = $("#search-storage-type").val() == "全部" ? ""
			: $("#search-storage-type").val();
	storage.searchObj["qm.storagePurpose"] = $("#search-storage-storagePurpose")
			.val() == "全部" ? "" : $("#search-storage-storagePurpose").val();
	// storage.searchObj["qm.resStorageClassSid"] =
	// $("#search-storage-mgt-classify").val()=="全部"?"":$("#search-storage-mgt-classify").val();
	// storage.searchObj["qm.hardDiskType"] =
	// $("#search-storage-mgt-status").val()=="全部"?"":$("#search-storage-mgt-status").val();
	storage.searchStorageConfigInfo();
}

// 导出存储Excel
function exportTopologyStorageDatagrid() {
	this.searchObj = {
		storageNameLike : $("#search-storage-name").val(),
		storageType : $("#search-storage-type").val() == "全部" ? "" : $(
				"#search-storage-type").val(),
		// hardDiskType :
		// $("#search-storage-mgt-status").val()=="全部"?"":$("#search-storage-mgt-status").val(),
		resTopologySid : resTopologySid
	};

	var params = JSON.stringify(searchObj);
	window.location = ws_url + "/rest/storage/exportStorage/" + params;
}

// 弹出新增存储window框
function addStoragesWindow() {
	var ok = $('#jqxAddStoragesBtn').jqxButton('disabled');
	if(ok){
		return;
	}
	var windowW = $(window).width();
	var windowH = $(window).height();
	var editStorage = new editStorageModel();
	editStorage.initCodeDropList();
	// 清除新增残留数据
	$("#add-storageNames").val("");
	$("#add-totalCapacitys").val("");
	$("#add-availableCapacitys").val("");
	$("#add-storage-lunNo").val("");
	$("#add-storageUnitNos").val("");
	$("#add-storageTags").val("");
	$("#add-storage-uuids").val("");
	var codeadd = new codeModel({
		width : 150,
		autoDropDownHeight : true
	});
	if (resTopologyType == "VC") {
		codeadd.getCustomCode("add-storage-resequipStorageSid",
				"/phystorages/downlistStorage/storage", "name", "equipSid",
				true, "POST", {
					resTopologySid : parentTopologySid
				});
	} else {
		codeadd.getCustomCode("add-storage-resequipStorageSid",
				"/phystorages/downlistStorage/storage", "name", "equipSid",
				true, "POST", {
					resTopologySid : resTopologySid
				});
	}
	$("#storage-parentTopologySids").val(resTopologySid);
	$("#addStoragesWindow").jqxWindow({
		position : {
			x : (windowW - 500) / 2,
			y : (windowH - 200) / 2
		}
	});
	$("#addStoragesWindow").jqxWindow('open');
}

// function findHostsSubmit(){
// var windowW = $(window).width();
// var windowH = $(window).height();
// var storage = new addStorageModel();
// storage.searchfindHostAddToStorageData(resTopologySid);
// $("#addStorageWithHostWindow").jqxWindow({ position: { x: (windowW-500)/2, y:
// (windowH-200)/2 } });
// $("#addStorageWithHostWindow").jqxWindow('open');
// }

// 弹出编辑存储window框
function editStoragesInfoWindow() {
	var ok = $('#jqxEditStoragesBtn').jqxButton('disabled');
	if(ok){
		return;
	}
	var rowindex = $('#storageConfigdatagrid').jqxGrid('getselectedrowindex');
	if (rowindex >= 0) {
		var data = $('#storageConfigdatagrid').jqxGrid('getrowdata', rowindex);
		// 为编辑赋值
		var codeadd = new codeModel({
			width : 150,
			autoDropDownHeight : true
		});

		codeadd.getCustomCode("edit-storage-resequipStorageSids",
				"/phystorages/downlistStorage/storage", "name", "equipSid",
				true, "POST", {
					resTopologySid : resTopologySid
				});
		var editStorage = new editStorageModel();
		editStorage.initCodeDropList();
		$("#resSidStorages").val(data.resStorageSid);

		editStorage.setEditStorageForm(data);
		// 设置window的位置
		var windowW = $(window).width();
		var windowH = $(window).height();
		$("#editStoragesWindow").jqxWindow({
			position : {
				x : (windowW - 500) / 2,
				y : (windowH - 280) / 2
			}
		});
		$("#editStoragesWindow").jqxWindow('open');
	}
}

function extendStoragesInfoWindow() {
	var ok = $('#jqxExtendStoragesBtn').jqxButton('disabled');
	if(ok){
		return;
	}
	var storage = new Object();
	var rowindex = $('#storageConfigdatagrid').jqxGrid('getselectedrowindex');
	var data = $('#storageConfigdatagrid').jqxGrid('getrowdata', rowindex);
	if (data == null) {
		Core.alert({
			title : "提示",
			message : "请选择一行数据编辑",
		})
		return;
	}
	$("#resExtendStorageSid").val(data.resStorageSid);
	var extendStoModel = new extendStorageModel();
	extendStoModel.initPopWindow();
	extendStoModel.setExtendStorageForm(data);
	// 设置window的位置
	var windowW = $(window).width();
	var windowH = $(window).height();
	$("#extendStoragesWindow").jqxWindow({
		position : {
			x : (windowW - 500) / 2,
			y : (windowH - 280) / 2
		}
	});
	$("#extendStoragesWindow").jqxWindow('open');

}
// 删除存储
function removeStoragesInfo() {
	var ok = $('#jqxDeleteStoragesBtn').jqxButton('disabled');
	if(ok){
		return;
	}
	var rowindex = $('#storageConfigdatagrid').jqxGrid('getselectedrowindex');
	var resStorageSids = "";
	var data = $('#storageConfigdatagrid').jqxGrid('getrowdata', rowindex);
	if (data == null) {
		Core.alert({
			title : "提示",
			message : "请选择一行数据编辑",
		})
		return;
	}
	resStorageSids = data.resStorageSid;
	/*
	 * for(var i=0;i<rowindex.length;i++){ var data =
	 * $('#storageConfigdatagrid').jqxGrid('getrowdata', rowindex[i]); if(i ==
	 * rowindex.length-1){ if(data != null && data != "" && data != 'undefined' ){
	 * resStorageSids += data.resStorageSid; } }else{ if(data != null && data != "" &&
	 * data != 'undefined' ){ resStorageSids += data.resStorageSid + ","; } } }
	 */
	Core.AjaxRequest({
		url : ws_url + "/rest/vds/countVdByStorage/"
				+ resStorageSids,
		type : "get",
		callback : function(data) {
			Core.confirm({
				title : "提示",
				message : "确定要删除该存储吗?",
				yes : "确定",
				confirmCallback : function() {
					Core.AjaxRequest({
						url : ws_url + "/rest/storage/deleteHostStorageMQ/"
								+ resStorageSids,
						type : "get",
						callback : function(data) {
							var storage = new storageConfigMgtModel();
							storage.searchStorageConfigInfo();
						}
					});
				}
			});
		}
	});
}
function viewStorageInfo(rowIndex){
	var data = $('#storageConfigdatagrid').jqxGrid('getrowdata', rowIndex);
	var volumeModel = new storageVolumeModel();
	volumeModel.initPopWindow("viewStorageWindow");
	var storageSid = data.resStorageSid;
	volumeModel.initVolumeDatagrid("storageVolumeDatagrid",storageSid);
	// 设置window的位置
	var windowW = $(window).width();
	var windowH = $(window).height();
	$("#viewStorageWindow").jqxWindow({
		position : {
			x : (windowW - 500) / 2,
			y : (windowH - 280) / 2
		}
	});
	$("#viewStorageWindow").jqxWindow('open');
}
// var rowindex = $('#storageConfigdatagrid').jqxGrid('getselectedrowindex');
// if(rowindex >= 0){
// var data = $('#storageConfigdatagrid').jqxGrid('getrowdata', rowindex);
// Core.confirm({
// title:"提示",
// message:"确定要删除该块存储吗?",
// yes:"确定",
// confirmCallback:function(){
// Core.AjaxRequest({
// url : ws_url + "/rest/storage/deletestorage/"+data.resStorageSid,
// type:"get",
// callback : function (data) {
// var storage = new storageHostMgtModel();
// storage.searchStorageHostInfo();
//  		 					
// // 刷新左边的Tree
// setStorageTreeValueRefresh();
// setStoragePoolTreeValueRefresh();
// },
// failure:function(data){
//  		 			    	
// }
// });
// }
// });
// }
