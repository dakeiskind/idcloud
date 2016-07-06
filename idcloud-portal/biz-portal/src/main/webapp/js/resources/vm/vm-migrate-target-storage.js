var resInsVmSid;
var resHostSid;
var targetStorageModel = function() {

	var me = this;
	this.items = ko.observableArray();
	// 给datagrid赋值
	this.searchStorageConfigInfo = function(vmData,targetHostSid) {
		resHostSid=targetHostSid;
		if(resHostSid!=null){
			Core.AjaxRequest({
				url : ws_url + "/rest/storage/host/",
				type:"post",
				params:{
					resHostSid:resHostSid
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
		}else{
			Core.AjaxRequest({
				url : ws_url + "/rest/vms/findCommonStorageByVms",
				type : 'post',
				params : {
					vmData:vmData
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
		}
	};

	// 初始化弹出window
	this.initPopWindow = function() {
		$("#targetStorageWindow").jqxWindow({
			width : 800,
			height : 250,
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
			height:170,
			theme : currentTheme,
			columnsresize : true,
			pageable : true,
			pagesize : 10,
			autoheight : false,
			autowidth : false,
			sortable : true,
			selectionmode : "singlerow",
			localization : gridLocalizationObj,
			columns : [ {
				text : '存储名称',
				datafield : 'storageName',
				width : 150
			}, {
				text : '存储类别',
				datafield : 'storageCategoryName'
			},{ text: '可用容量(GB)', datafield: 'availableCapacity',cellsalign:'right',width:80},
            { text: '置备容量(GB)', datafield: 'provisionCapacity',cellsalign:'right',width:80},
            {
				text : '总容量(GB)',
				datafield : 'totalCapacity'
				
			} , {
				text : '状态',
				datafield : 'statusName'
				
			}]
		});
		// 控制按钮是否可用
  	  $("#targetStorageDatagrid").on('rowselect', function (event) {
  		  $("#targetStorageSave").jqxButton({ width: '50',height:"25",theme:currentTheme,disabled:false});
  	  });
	};

};

//迁移存储
function migrateVmSto(){
	  var rowindex = $('#targetStorageDatagrid').jqxGrid('getselectedrowindex');
	  if(rowindex >= 0){
			var data = $('#targetStorageDatagrid').jqxGrid('getrowdata', rowindex);
			migrateVm(vmData,resHostSid,data.resStorageSid);
	  }
	 
}

