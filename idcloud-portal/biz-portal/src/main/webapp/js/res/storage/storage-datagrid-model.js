var storageConfigMgtModel = function(){
	
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		volumeName: "", 
    		volumeType: "", 
    		manageStatus:"",
    		usageStatus:"",
    		isResPoolSearch:resTopologyType,
    		resTopologySid:resTopologySid
	};
    // 给datagrid赋值
    this.searchStorageConfigInfo = function(){
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/storages",
 			type:'post',
 			params:me.searchObj,
 			async:false,
 			callback : function (data) {
 				me.items(data);
 				var sourcedatagrid ={
			              datatype: "json",
			              localdata: me.items
 			    };
 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#storageConfigMgtdatagrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	$("#search-storage-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	
    	var storageconfig = new codeModel({width:120,autoDropDownHeight:true});
    	storageconfig.getCommonCode("search-storage-type","STORAGE_TYPE",true);
    	storageconfig.getCommonCode("search-storage-mgt-status","MANAGEMENT_STATUS",true);
    	storageconfig.getCommonCode("search-storage-usage-status","USAGE_STATUS",true);
        $("#search-storage-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    	
    };

    // 初始化用户datagrid的数据源
    this.initStorageDatagrid = function(){
          $("#storageConfigMgtdatagrid").jqxGrid({
              width: "100%",
			  theme:currentTheme,
              columnsresize: true,
              pageable: true, 
              pagesize: 10, 
              autoheight: true,
              autowidth: false,
              sortable: true,
              selectionmode:"singlerow",
              localization: gridLocalizationObj,
              columns: [
                  { text: '存储名称', datafield: 'volumeName',width:150},
                  { text: '存储类型', datafield: 'volumeTypeName',width:150},
                  { text: '硬盘类型', datafield: 'hardDiskTypeName',width:80},
                  { text: '存储类别', datafield: 'storageCategoryName'},
                  { text: '存储用途', datafield: 'storagePurposeName'},
                  { text: '逻辑单元号', datafield: 'volumeUnitNo'},
                  { text: '已使用(GB)', datafield: 'allocateAvailableCapacity',width:80},  
                  { text: '总容量(GB)', datafield: 'availableCapacity',width:80},
                  { text: '使用率(%)', datafield: 'usedCapacityPercentage',width:80},
                  { text: '管理状态', datafield: 'manageStatusName'},
                  { text: '所属资源池', datafield: 'resourcePoolName'},
                  { text: '使用状态', datafield: 'usageStatusName'}
              ],
              showtoolbar: true,
              // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var approveDetail = $("<div><a id='addStorage' onclick='addStorageInfoWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
                  var historyApprove = $("<div><a id='editStorage' style='margin-left:-1px' onclick='editStorageInfoWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                  var ruleRemove = $("<div><a id='removeStorage' onclick='removeStorageInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
                  container.append(approveDetail);
                  container.append(historyApprove);
                  container.append(ruleRemove);
              }
          });
          
          // 控制按钮是否可用
    	  $("#storageConfigMgtdatagrid").on('rowselect', function (event) {
    		  var args = event.args; 
    		  var index = args.rowindex;
    		  var data = $('#storageConfigMgtdatagrid').jqxGrid('getrowdata', index);
   			  $("#editStorage").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
   			  $("#removeStorage").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
          });
    	  
    	  $("#addStorage").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
			  $("#editStorage").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
			  $("#removeStorage").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
          
    };
    
    // 获得存储图表数据
    this.getStorageStatisticsData = function(){
    	var storageData = null;
    	Core.AjaxRequest({
 			url : ws_url + "/rest/storages",
 			type:'post',
 			params:me.searchObj,
 			async:false,
 			callback : function (data) {
 				storageData = data;
 			}
 		 });
    	
    	var data = new Object();
		data.attr = new Array();
		
		var value = [0,0,0];
		var name =["已使用","未使用"];
		for(var i=0;i<storageData.length;i++){
			// 已使用
			value[0] += storageData[i].allocateAvailableCapacity; 
			//未使用
			value[1] += (storageData[i].availableCapacity - storageData[i].allocateAvailableCapacity); 
			// 总量
			value[2] += storageData[i].availableCapacity; 
		}
		for(var i=0; i<2;i++){
			var obj = new Object();
			obj.name = name[i];
			obj.value = value[i];
			data.attr.push(obj);
		}
		data.total = value[2];
		return data;
    };
 
};
// 查询存储
function searchStorageConfigMgt(){
	var storage = new storageConfigMgtModel();
	storage.searchObj.volumeName = $("#search-storage-name").val();
	storage.searchObj.volumeType = $("#search-storage-type").val()=="全部"?"":$("#search-storage-type").val();
	storage.searchObj.manageStatus = $("#search-storage-mgt-status").val()=="全部"?"":$("#search-storage-mgt-status").val();
	storage.searchObj.usageStatus = $("#search-storage-usage-status").val()=="全部"?"":$("#search-storage-usage-status").val();
	storage.searchObj.resTopologySid = resTopologySid;
	storage.searchStorageConfigInfo();
}

// 弹出新增存储window框
function addStorageInfoWindow(){
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	// 清除新增残留数据
	$("#add-volumeName").val("");
    $("#add-availableCapacity").val("");
    $("#add-volumeUnitNo").val("");
    $("#add-volumeTag").val("");
	
	$("#storage-parentTopologySid").val(resTopologySid);
	$("#addStorageWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-200)/2 } });
	$("#addStorageWindow").jqxWindow('open');
}

// 弹出编辑存储window框
function editStorageInfoWindow(){
	var rowindex = $('#storageConfigMgtdatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		var data = $('#storageConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
		// 为编辑赋值
		var editStorage = new editStorageConfigMgtModel();
		$("#resSidStorage").val(data.resSid);
		editStorage.setEditStorageForm(data);
		// 设置window的位置
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#editStorageWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-200)/2 } });
    	$("#editStorageWindow").jqxWindow('open');
	}
}

// 删除存储
function removeStorageInfo(){
	var rowindex = $('#storageConfigMgtdatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		var data = $('#storageConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
	    	Core.confirm({
				title:"提示",
				message:"确定要删除该块存储吗?",
				yes:"确定",
				confirmCallback:function(){
					Core.AjaxRequest({
		 				url : ws_url + "/rest/storages/delete/"+data.resSid+"",
		 				type:"get",
		 				callback : function (data) {
		 					var storage = new storageConfigMgtModel();
		 					storage.searchStorageConfigInfo();
		 			    },
		 			    failure:function(data){
		 			    	
		 			    }
		 			});
				}
		});
	}
}


