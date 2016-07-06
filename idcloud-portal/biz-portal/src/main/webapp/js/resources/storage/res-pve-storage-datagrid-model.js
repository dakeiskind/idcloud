var pveStorageConfigMgtModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    	 "qm.storageNameLike":null,
	    	 "qm.storageCategory":null,
	    	 "qm.hardDiskType":null,
	    	 "qm.storagePurpose":null,
//	    	 "qm.resStorageClassSid":null,
	    	 "qm.resTopologyType" : resTopologyType,
	    	 "qm.resTopologySid" : resTopologySid	
		};
	    this.VData = new Object();
	    // 用户数据
	    this.searchStorageConfigInfo = function(){
	    	
			var dataAdapter = Core.getPagingDataAdapter({
				gridId: "pveStorageConfigdatagrid",
				url : ws_url + "/rest/storage",
				params: me.searchObj
			});
			$("#pveStorageConfigdatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    };
	    
	    // 统计资源分层结构下的存储信息
	    this.StorageResourcesStatisticsInTopologyRz = function(resTopologySid){
	    	var storageData = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/storage/statistical/rz/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				storageData = data;
	 			}
		    });
			var data = new Object();
			data.storageCount = storageData.staTotalStorage;
			data.attr = new Array();
			var value = [storageData.staUsableStorage,storageData.staFaultStorage,storageData.staUnusableStorage];
			var name =["可用","故障","不可用"];
			for(var i=0; i<3;i++){
				var obj = new Object();
				var val = (value[i]==null)?0 : value[i];
				obj.name = name[i]+"("+val+")";
				obj.value = val;
				data.attr.push(obj);
			}
			return data;
	    };
	    
	    // 统计资源拓扑结构下的存储信息
	    this.StorageResourcesStatisticsInTopology = function(resTopologySid){
	    	var storageData = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/storage/statistical/topology/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				
	 				storageData = data;
	 			}
		    });
			var data = new Object();
			data.storageCount = storageData.staTotalStorage;
			data.attr = new Array();
			var value = [storageData.staUsableStorage,storageData.staFaultStorage,storageData.staUnusableStorage];
			var name =["可用","故障","不可用"];
			for(var i=0; i<3;i++){
				var obj = new Object();
				var val = value[i] == null ? 0:value[i];
				obj.name = name[i]+"("+val+")";
				obj.value = val;
				data.attr.push(obj);
			}
			return data;
	    };
	    
	    // 统计集群下的存储信息
	    this.StorageResourcesStatisticsInCluster = function(resTopologySid){
	    	var storageData = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/storage/statistical/cluster/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				storageData = data;
	 			}
		    });
			var data = new Object();
			data.storageCount = storageData.staTotalStorage;
			data.attr = new Array();
			var value = [storageData.staUsableStorage,storageData.staFaultStorage,storageData.staUnusableStorage];
			var name =["可用","故障","不可用"];
			for(var i=0; i<3;i++){
				var obj = new Object();
				var val = value[i] == null ? 0:value[i];
				obj.name = name[i]+"("+val+")";
				obj.value = val;
				data.attr.push(obj);
			}
			return data;
	    };
	    
	    // 统计主机下的存储信息
	    this.StorageResourcesStatisticsInHost = function(resTopologySid){
	    	var storageData = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/storage/statistical/host/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				storageData = data;
	 			}
		    });
			var data = new Object();
			data.storageCount = storageData.staTotalStorage;
			data.attr = new Array();
			var value = [storageData.staUsableStorage,storageData.staFaultStorage,storageData.staUnusableStorage];
			var name =["可用","故障","不可用"];
			for(var i=0; i<3;i++){
				var obj = new Object();
				var val = value[i] == null ? 0:value[i];
				obj.name = name[i]+"("+val+")";
				obj.value = val;
				data.attr.push(obj);
			}
			return data;
	    };
	    
	    // 统计集群下的存储信息
	    this.StorageResourcesStatisticsInRsc = function(resTopologySid){
	    	var storageData = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/storage/statistical/rsc/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				storageData = data;
	 			}
		    });
			var data = new Object();
			data.storageCount = storageData.staTotalStorage;
			data.attr = new Array();
			var value = [storageData.staUsableStorage,storageData.staFaultStorage,storageData.staUnusableStorage];
			var name =["可用","故障","不可用"];
			for(var i=0; i<3;i++){
				var obj = new Object();
				var val = (value[i] == null) ? 0 : value[i];
				obj.name = name[i]+"("+val+")";
				obj.value = val;
				data.attr.push(obj);
			}
			return data;
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-pve-storage-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-pve-storage-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        // 初始化下拉列表框 
			var codesearch = new codeModel({width:120,autoDropDownHeight:true});
			codesearch.getCommonCode("search-pve-storage-type","STORAGE_CATEGORY",true);
			//codesearch.getCustomCode("search-pve-storage-mgt-classify","/topology/findStorageClassify/"+resTopologySid,"resTopologyName","resTopologySid",true,"GET");
			codesearch.getCommonCode("search-pve-storage-storagePurposes","STORAGE_PURPOSE",true);
		
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initStorageDatagrid = function(){
	    	var dataAdapter = Core.getPagingDataAdapter({
				gridId: "pveStorageConfigdatagrid",
				url : ws_url + "/rest/storage",
				params: me.searchObj
			});
	          $("#pveStorageConfigdatagrid").jqxGrid({
	              width: "100%",
				  theme:currentTheme,
				  source: dataAdapter,
				  virtualmode: true,
				  rendergridrows: function(){
					  return dataAdapter.records;
				  },
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"checkbox",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '存储名称', datafield: 'storageName'},
	                  { text: '存储类型', datafield: 'storageType',align: 'center',cellsalign:'center'},
	                  { text: '存储类别', datafield: 'storageCategoryName',align: 'center',cellsalign:'center'},
	                  { text: '存储用途', datafield: 'storagePurposeName',align: 'center',cellsalign:'center'},
	                  { text: '总容量(GB)', datafield: 'totalCapacity',cellsalign:'right',width:80},
	                  { text: '已用容量(GB)', datafield: 'hadUsedCapacity',cellsalign:'right',width:80},
	                  { text: '使用率', datafield: 'storageUsage',cellsalign:'right',width:80},
	                  { text: '分配容量(GB)', datafield: 'allotCapacity',cellsalign:'right',width:80},
	                  { text: '分配率', datafield: 'storageRate',cellsalign:'right',width:80},
	                  { text: '状态', datafield: 'statusName',align: 'center',cellsalign:'center',width:120}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addStoragesBtn = $("<div><a id='jqxAddStoragesBtn' onclick='addPveStoragesWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editStoragesBtn = $("<div><a id='jqxEditStoragesBtn' onclick='editPveStoragesInfoWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var deleteStoragesBtn = $("<div><a id='jqxDeleteStoragesBtn' onclick='removePveStoragesInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
	                  var exportBtn = $("<div><a id='jqxExportStorageBtn' onclick='exportTopologyPveStorageDatagrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-export'></i>导出&nbsp;&nbsp;</a></div>");
	                  var typeBtn = $("<div><a id='jqxStorageTypeBtn' onclick='popHMCSetStorageTypeWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cog'></i>设置存储类型&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  if("DC"==resTopologyType){
	                	  container.append(exportBtn);
		                  container.append(typeBtn);
		                  $("#jqxExportStorageBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
		    	    	  $("#jqxStorageTypeBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
	                  }else{
	                	  container.append(addStoragesBtn);
		                  container.append(editStoragesBtn);
		                  container.append(deleteStoragesBtn);
		                  container.append(exportBtn);
		                  container.append(typeBtn);
		                  $("#jqxAddStoragesBtn").jqxButton({width: '60',theme:currentTheme,height: '18px'});
		    	          $("#jqxEditStoragesBtn").jqxButton({width: '60',theme:currentTheme,height: '18px'});
		    	          $("#jqxDeleteStoragesBtn").jqxButton({width: '60',theme:currentTheme,height: '18px'});
		    	    	  $("#jqxExportStorageBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
		    	    	  $("#jqxStorageTypeBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
	                  }
	                  
	              }
	          });
	          
	          $("#pveStorageConfigdatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#pveStorageConfigdatagrid').jqxGrid('getrowdata', index);
	    		  $("#jqxStorageTypeBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
	          });
	         /* $("#jqxAddStoragesBtn").jqxButton({width: '60',theme:currentTheme,height: '18px'});
	          $("#jqxEditStoragesBtn").jqxButton({width: '60',theme:currentTheme,height: '18px'});
	          $("#jqxDeleteStoragesBtn").jqxButton({width: '60',theme:currentTheme,height: '18px'});
	    	  $("#jqxExportStorageBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
	    	  $("#jqxStorageTypeBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });*/
	    };
  };
  
   //查询资源环境
  function searchPveStorageConfigMgt(){
	  var storage = new pveStorageConfigMgtModel();
	  storage.searchObj["qm.storageNameLike"] = $("#search-pve-storage-name").val();
	  storage.searchObj["qm.storageCategory"] = $("#search-pve-storage-type").val()=="全部"?"":$("#search-pve-storage-type").val();
	  storage.searchObj["qm.storagePurpose"] = $("#search-pve-storage-storagePurposes").val()=="全部"?"":$("#search-pve-storage-storagePurposes").val();
	  storage.searchStorageConfigInfo();
  }
  
  // 导出存储Excel
  function exportTopologyPveStorageDatagrid(){
      this.searchObj = {
	    	 storageNameLike : $("#search-storage-name").val(),
	    	 storageType : $("#search-storage-type").val()=="全部"?"":$("#search-storage-type").val(),
	    	// hardDiskType : $("#search-storage-mgt-status").val()=="全部"?"":$("#search-storage-mgt-status").val(),
	    	 resTopologySid : resTopologySid	
      };
	  
	  var params = JSON.stringify(searchObj);
	  window.location = ws_url + "/rest/storage/exportStorage/" + params; 
  }

//弹出新增存储window框
  function addPveStoragesWindow(){
  	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 清除新增残留数据
  	  $("#add-storageNames").val("");
      $("#add-totalCapacitys").val("");
      $("#add-availableCapacitys").val("");
      $("#add-provisionCapacitys").val("");
      $("#add-storageUnitNos").val("");
      $("#add-storageTags").val("");
      $("#add-storage-uuids").val("");
      var codeadd = new codeModel({width:150,autoDropDownHeight:true});
      codeadd.getCustomCode("add-storage-resequipStorageSid","/phystorages/downlistStorage/storage","name","equipSid",true,"POST",{resTopologySid:resTopologySid});
  	$("#storage-parentTopologySids").val(resTopologySid);
  	$("#addStoragesWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-200)/2 } });
  	$("#addStoragesWindow").jqxWindow('open');
  }
  
//弹出编辑存储window框
  function editPveStoragesInfoWindow(){
  	var rowindex = $('#pveStorageConfigdatagrid').jqxGrid('getselectedrowindex');
  	if(rowindex >= 0){
  		var data = $('#pveStorageConfigdatagrid').jqxGrid('getrowdata', rowindex);
var codeadd = new codeModel({width:150,autoDropDownHeight:true});
		
  	    codeadd.getCustomCode("edit-storage-resequipStorageSid","/phystorages/downlistStorage/storage","name","equipSid",true,"POST",{resTopologySid:resTopologySid});
  		// 为编辑赋值
  		var editStorage = new editStorageModel();
  		$("#resSidStorages").val(data.resStorageSid);
  		editStorage.setEditStorageForm(data);
  		// 设置window的位置
  		var windowW = $(window).width(); 
      	var windowH = $(window).height(); 
      	$("#editStoragesWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-200)/2 } });
      	$("#editStoragesWindow").jqxWindow('open');
  	}
  }
  //删除存储
  function removePveStoragesInfo(){
	  var rowindex = $('#pveStorageConfigdatagrid').jqxGrid('getselectedrowindexes');
	  var resStorageSids = "";
	  if(rowindex.length > 0){
		  for(var i=0;i<rowindex.length;i++){
			  var data = $('#pveStorageConfigdatagrid').jqxGrid('getrowdata', rowindex[i]);
			  if(i == rowindex.length-1){
				  if(data != null && data != "" && data != 'undefined' ){
					  resStorageSids += data.resStorageSid;
				  }
			  }else{
				  if(data != null && data != "" && data != 'undefined' ){
					resStorageSids += data.resStorageSid + ",";
				  }
			 }
		  }
		  Core.confirm({
			  title:"提示",
		      message:"确定要删除该存储吗?",
		      yes:"确定",
			  confirmCallback:function(){
				  Core.AjaxRequest({
					  url : ws_url + "/rest/storage/deleteHostStorage/"+resStorageSids,
					  type:"get",
					  callback : function (data) {
						  var storage = new pveStorageConfigMgtModel();
						  storage.searchStorageConfigInfo();
					  }
				  });
			  }
		  });
	  }
  }