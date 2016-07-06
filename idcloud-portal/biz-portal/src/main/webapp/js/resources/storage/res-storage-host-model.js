var storageHostMgtModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    
	    this.searchObj = {
    		 storageNameLike:"",
    		// resStorageClassSid : "",
    		 storagePurpose : "",
    		 storageCategory : "",
    		 resHostSid : resTopologySid
		};
	    
	    // 用户数据
	    this.searchStorageHostInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/storage/host",
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
	 			    $("#storageHostDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-storage-host-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-storage-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        // 初始化下拉列表框 
			var codesearch = new codeModel({width:120,autoDropDownHeight:true});
			codesearch.getCommonCode("search-storage-host-type","STORAGE_CATEGORY",true);
			//codesearch.getCustomCode("search-storage-host-mgt-classify","/topology/findStorageClassify/host/"+resTopologySid+"","resTopologyName","resTopologySid",true,"GET");
			codesearch.getCommonCode("search-storage-host-storagePurpose","STORAGE_PURPOSE",true);
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initStorageDatagrid = function(){
	          $("#storageHostDatagrid").jqxGrid({
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
						{ text: '存储名称', datafield: 'storageName',width:120},
						{ text: '存储类型', datafield: 'storageType'},
						{ text: '存储类别', datafield: 'storageCategoryName'},
						{ text: '存储用途', datafield: 'storagePurposeName'},
						{ text: '总容量(GB)', datafield: 'totalCapacity'},
						{ text: '已用容量(GB)', datafield: 'hadUsedCapacity',width:80},
						{ text: '使用率(%)', datafield: 'storageUsage'},
						{ text: '分配容量(GB)', datafield: 'allotCapacity'},
						{ text: '分配率(%)', datafield: 'storageRate'},
						{ text: '状态', datafield: 'statusName',width:80}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addStorageBtn = $("<div><a id='jqxAddStorageBtn' onclick='addStorageWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>添加&nbsp;&nbsp;</a></div>");
	                  var editStorageBtn = $("<div><a id='jqxEditStorageBtn' onclick='editStorageInfoWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var deleteStorageBtn = $("<div><a id='jqxDeleteStorageBtn' onclick='removeStorageInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
	                  var refreshBtn = $("<div><a id='jqxRefreshStorageBtn' onclick='refreshStorageGrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
	                  var exportBtn = $("<div><a id='jqxExportStorageBtn' onclick='exportHostStorageDatagrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-export'></i>导出&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addStorageBtn);
	                  container.append(editStorageBtn);
	                  container.append(deleteStorageBtn);
	                  container.append(refreshBtn);
	                  container.append(exportBtn);
	                  initHostStorageMenu();
	              }
	          });
	          $("#storageHostDatagrid").on('rowselect',
	  				function(event) {
	  					var args = event.args;
	  					var index = args.rowindex;
	  					var data = $('#storageHostDatagrid').jqxGrid('getrowdata', index);
	  					$("#jqxEditStorageBtn").jqxButton({disabled : false});
	  		            $("#jqxDeleteStorageBtn").jqxButton({disabled : false});
	  				    $("#jqxExportStorageBtn").jqxButton({disabled : false});
	  			  
	  					
	          });
   			  if("HOST" == resTopologyType){
   				$("#jqxAddStorageBtn").jqxButton({disabled : true});
   			  }
	          
	    };
  };
  
  // 查询主机下的存储
  function searchStorageHostConfigMgt(){
	  var hostStorage = new storageHostMgtModel();
	  hostStorage.searchObj.storageNameLike = $("#search-storage-host-name").val();
	  hostStorage.searchObj.storageCategory = $("#search-storage-host-type").val()=="全部"?"":$("#search-storage-host-type").val();
	  hostStorage.searchObj.storagePurpose = $("#search-storage-host-storagePurpose").val()=="全部"?"":$("#search-storage-host-storagePurpose").val();
	  //hostStorage.searchObj.resStorageClassSid = $("#search-storage-host-mgt-classify").val()=="全部"?"":$("#search-storage-host-mgt-classify").val();
	  hostStorage.searchStorageHostInfo();
  }
  //按钮
  function initHostStorageMenu(){
  	$("#jqxAddStorageBtn").jqxButton({width: '60',theme:currentTheme,height: '18px',disabled : true});
    $("#jqxEditStorageBtn").jqxButton({width: '60',theme:currentTheme,height: '18px',disabled : true});
    $("#jqxDeleteStorageBtn").jqxButton({width: '60',theme:currentTheme,height: '18px',disabled : true});
	$("#jqxRefreshStorageBtn").jqxButton({width: '60',theme:currentTheme,height: '18px',disabled : false});
	$("#jqxExportStorageBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled : true});
  }
  // 刷新存储列表
  function refreshStorageGrid(){
	var hostStorage = new storageHostMgtModel();
	hostStorage.searchStorageHostInfo();
  }

  // 导出主机相关的存储
  function exportHostStorageDatagrid(){
	  window.location = ws_url + "/rest/storage/host/exportStorage/" + resTopologySid; 
  }

//弹出新增存储window框
function addStorageWindow(){
	var ok = $('#jqxAddStorageBtn').jqxButton('disabled');
	if(ok){
		return;
	}
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	// 清除新增残留数据
	$("#add-storageName").val("");
    $("#add-totalCapacity").val("");
    $("#add-storageUnitNo").val("");
    $("#add-storageTag").val("");
    $("#add-storage-uuid").val("");
    $("#add-provisionCapacity").val("");
    $("#add-availableCapacity").val("");
    $("#add-lunNo").val("");

    $("#storage-parentTopologySid").val(resTopologySid);
	$("#addStorageWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-300)/2 } });
	$("#addStorageWindow").jqxWindow('open');
}
//弹出编辑存储window框
function editStorageInfoWindow(){
	var ok = $('#jqxEditStorageBtn').jqxButton('disabled');
	if(ok){
		return;
	}
	var rowindex = $('#storageHostDatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		var data = $('#storageHostDatagrid').jqxGrid('getrowdata', rowindex);
		var codeadd = new codeModel({width:150,autoDropDownHeight:true});
		
  	    codeadd.getCustomCode("edit-storage-resequipStorageSids","/phystorages/downlistStorage/storage","name","equipSid",true,"POST",{resTopologySid:resTopologySid});
		// 为编辑赋值
		var editStorage = new editStorageConfigMgtModel();
		$("#resSidStorage").val(data.resStorageSid);
		editStorage.setEditStorageForm(data);
		// 设置window的位置
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#editStorageWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-200)/2 } });
    	$("#editStorageWindow").jqxWindow('open');
	}
}
//删除存储
function removeStorageInfo(){
	var ok = $('#jqxDeleteStorageBtn').jqxButton('disabled');
	if(ok){
		return;
	}
	var rowindex = $('#storageHostDatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		var data = $('#storageHostDatagrid').jqxGrid('getrowdata', rowindex);
	    	Core.confirm({
				title:"提示",
				message:"确定要删除该块存储吗?",
				yes:"确定",
				confirmCallback:function(){
					Core.AjaxRequest({
		 				url : ws_url + "/rest/storage/delete/"+data.resStorageSid+"/"+resTopologySid,
		 				type:"get",
		 				callback : function (data) {
		 					var storage = new storageHostMgtModel();
		 					storage.searchStorageHostInfo();
		 					
		 					// 刷新左边的Tree
	 						/*setStorageTreeValueRefresh();
	 						setStoragePoolTreeValueRefresh();*/
		 			    },
		 			    failure:function(data){
		 			    	
		 			    }
		 			});
				}
		});
	}
}
  