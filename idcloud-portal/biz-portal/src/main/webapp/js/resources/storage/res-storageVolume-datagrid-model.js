var storageVolumeModel = function() {
	var me = this;
	this.items = ko.observableArray();
	this.searchObj = {
		"qm.resStorageSid" : null
	};
	this.initPopWindow = function(windowName) {
		/*$("#viewStorageCancel").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : false});*/
		$("#"+windowName+"").jqxWindow({
			width : 900,
			height : 300,
			resizable : false,
			theme : currentTheme,
			isModal : true,
			autoOpen : false,
//			cancelButton: $("#viewStorageCancel"), 
			modalOpacity : 0.3
		});
	};
	this.initVolumeDatagrid = function(gridName,resStorageSid) {
		me.searchObj["qm.resStorageSid"] = resStorageSid;
		var dataAdapter = Core.getPagingDataAdapter({
			gridId : "storageVolumeDatagrid",
			url : ws_url + "/rest/storage/volume",
			params : me.searchObj
		});
		$("#"+gridName+"").jqxGrid({
			width : "99%",
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
			autowidth : false,
			sortable : true,
			localization : gridLocalizationObj,
			columns : [ {
				text : '名称',
				datafield : 'volumeName'
			}, {
				text : '序列号',
				datafield : 'volumeWwn',
				align : 'center',
				cellsalign : 'center'
			}, {
				text : '容量',
				datafield : 'size',
				align : 'center',
				cellsalign : 'center'
			}, {
				text : '状态',
				datafield : 'statusName',
				align : 'center',
				cellsalign : 'center'
			} ],
			showtoolbar : false

		});

	}
}
