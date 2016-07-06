var resInsVmSid;
var resHostSid;
var targetStorageModel = function() {

	var me = this;
	this.items = ko.observableArray();
	// 给datagrid赋值
	this.searchStorageConfigInfo = function(resInsSid, resSid) {
		resInsVmSid = resInsSid;
		resHostSid = resSid;
		Core.AjaxRequest({
			url : ws_url + "/rest/storages",
			type : 'post',
			params : {
				relateHostSid : resSid,
			},
			async : false,
			callback : function(data) {
				me.items(data);
				var sourcedatagrid = {
					datatype : "json",
					localdata : me.items
				};
				var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
				$("#targetStorageDatagrid").jqxGrid({
					source : dataAdapter
				});
			}
		});
	};

	// 初始化弹出window
	this.initPopWindow = function() {
		$("#targetStorageWindow").jqxWindow({
			width : 800,
			height : 220,
			resizable : false,
			theme : currentTheme,
			isModal : true,
			autoOpen : false,
			cancelButton : $("#targetStorageCancel"),
			modalOpacity : 0.3,
			initContent : function() {

				$("#targetStorageSave").jqxButton({
					width : '50',
					height : "25",
					theme : currentTheme,
					disabled:true
				});
				$("#targetStorageCancel").jqxButton({
					width : '50',
					height : "25",
					theme : currentTheme
				});
			}
		});
	};

	// 初始化用户datagrid的数据源
	this.initStorageDatagrid = function() {
		$("#targetStorageDatagrid").jqxGrid({
			width : "100%",
			theme : currentTheme,
			columnsresize : true,
			pageable : true,
			pagesize : 10,
			autoheight : true,
			autowidth : false,
			sortable : true,
			selectionmode : "singlerow",
			localization : gridLocalizationObj,
			columns : [ {
				text : '存储名称',
				datafield : 'volumeName',
				width : 150
			},  {
				text : '硬盘类型',
				datafield : 'hardDiskTypeName',
				width : 80
			}, {
				text : '存储类别',
				datafield : 'storageCategoryName'
			},  {
				text : '逻辑单元号',
				datafield : 'volumeUnitNo'
			}, {
				text : '已使用(GB)',
				datafield : 'allocateAvailableCapacity',
				width : 80
			}, {
				text : '总容量(GB)',
				datafield : 'availableCapacity',
				width : 80
			}, {
				text : '使用率(%)',
				datafield : 'usedCapacityPercentage',
				width : 80
			}, {
				text : '使用状态',
				datafield : 'usageStatusName'
			} ]
		});
		// 控制按钮是否可用
  	  $("#targetStorageDatagrid").on('rowselect', function (event) {
  		  $("#targetStorageSave").jqxButton({ width: '50',height:"25",theme:currentTheme,disabled:false});
  	  });
	};

};

// 弹出编辑存储window框
function migrate() {
	Core.confirm({
				title : "提示",
				message : "迁移时需要关闭电源，确认进行该操作吗？",
				confirmCallback : function() {
					var rowindex = $('#targetStorageDatagrid').jqxGrid(
							'getselectedrowindex');
					if (rowindex >= 0) {
						var data = $('#targetStorageDatagrid').jqxGrid('getrowdata', rowindex);
						Core.AjaxRequest({
									url : ws_url+ "/rest/resourceInstance/platform/relocateVm",
									type : 'post',
									params : {
										resInsVmSid : resInsVmSid,
										hostSid : resHostSid,
										storageSid : data.resSid
									},
									callback : function(data) {
										$("#targetStorageWindow").jqxWindow('close');
										$("#targetHostWindow").jqxWindow('close');
										var me=new vmManagedModel();
										me.searchVMInfo();
									},
									failure:function(){
										
									}
								});
					}
				}
			});

}
