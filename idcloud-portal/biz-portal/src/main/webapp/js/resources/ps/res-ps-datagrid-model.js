var poolPsDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
    		 storageNameLike:"",
    		 storagePurpose : "",
//    		 resStorageClassSid : "",
    		 storageCategory : "",
	    	 resPoolSid : resTopologySid
		};
	    
	    // 用户数据
	    this.searchPsInfo = function(){
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/storage",
	 			type:'post',
	 			params:me.searchObj,
	 			async:false,
	 			callback : function (data) {
	 				var sourcedatagrid ={
				              datatype: "json",
				              localdata: data
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#poolPsDatagrid").jqxGrid({source: dataAdapter});
	 			 }
 		  });
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
				obj.name = name[i];
				obj.value = value[i];
				data.attr.push(obj);
			}
			return data;
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-ps-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-ps-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        var codesearch = new codeModel({width:120,autoDropDownHeight:true});
	        codesearch.getCommonCode("search-storage-mgt-storageCategory","STORAGE_CATEGORY", true);
	        //codesearch.getCustomCode("search-storage-mgt-classify","/topology/findStorageClassify/"+resTopologySid,"resTopologyName","resTopologySid",true,"GET");
	        codesearch.getCommonCode("search-storage-mgt-storagePurpose","STORAGE_PURPOSE",true);
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initPsDatagrid = function(){
	          $("#poolPsDatagrid").jqxGrid({
	              width: "100%",
				  theme:currentTheme,
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
						{ text: '存储分类', datafield: 'storageCategoryName',align: 'center',cellsalign:'center'},
						{ text: '存储用途', datafield: 'storagePurposeName',align: 'center',cellsalign:'center'},
						{ text: '总容量(GB)', datafield: 'totalCapacity',cellsalign:'right',width:80},
						{ text: '可使用(GB)', datafield: 'availableCapacity',cellsalign:'right',width:80},
						{ text: '置备容量(GB)', datafield: 'provisionCapacity',cellsalign:'right',width:80},
						{ text: '分配容量(GB)', datafield: 'allotCapacity',cellsalign:'right',width:80},
						{ text: '使用率(%)', datafield: 'storageUsage',cellsalign:'right',width:80},
						{ text: '分配率(%)', datafield: 'storageRate',cellsalign:'right',width:80},
						{ text: '状态', datafield: 'statusName',align: 'center',cellsalign:'center'}
	              ],
	              showtoolbar: true,
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addBtn = $("<div><a id='jqxAddBtn' onclick ='popAddRelationStorageWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>关联存储&nbsp;&nbsp;</a></div>");
	                  var cancelBtn = $("<div><a id='jqxCancelBtn' onclick ='cancelRelationStorage()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel'></i>取消关联&nbsp;&nbsp;</a></div>");

	                  toolbar.append(container);
	                  container.append(addBtn);
	                  container.append(cancelBtn);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#poolPsDatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#poolPsDatagrid').jqxGrid('getrowdata', index);
	    		  $("#jqxCancelBtn").jqxButton({disabled: false});
	    		  
	          });
	    	  
	    	  $("#jqxCancelBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
   			  $("#jqxAddBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',});
	    };
};
  // 查询计算资源池关联的集群
  function searchPS(){
	  var ps = new poolPsDatagridModel();
	  ps.searchObj.storageNameLike = $("#search-ps-name").val();
	  ps.searchObj.storageCategory = $("#search-storage-mgt-storageCategory").val()=="全部"?"":$("#search-storage-mgt-storageCategory").val();
	  ps.searchObj.storagePurpose = $("#search-storage-mgt-storagePurpose").val()=="全部"?"":$("#search-storage-mgt-storagePurpose").val();
	  ps.searchPsInfo();
  }
  
  // 删除VLAN池
  function removeStoragePool(){
	    var rowindex = $('#poolPsDatagrid').jqxGrid('getselectedrowindex');
		var ok =  $('#jqxDeleteBtn').jqxButton('disabled');
	  	if(!ok && rowindex >= 0){
	  		var data = $('#poolPsDatagrid').jqxGrid('getrowdata', rowindex);
			    Core.confirm({
					title:"提示",
					message:"您确定要删除该存储资源吗？",
					confirmCallback:function(){
						Core.AjaxRequest({
							url : ws_url + "/rest/poolVlans/delete/"+data.resPoolSid,
			 				type:"get",
			 				callback : function (data) {
			 					var pdm = new poolPnvDatagridModel();
			 					pdm.searchPnvInfo();
			 			    },
			 			});
					}
				});
	  	}
  }
  
  // 取消关联
  function cancelRelationStorage(){
	  var resStorageSids = "";
	  var rowindex = $('#poolPsDatagrid').jqxGrid('getselectedrowindexes');
	  if(rowindex.length > 0){
			  Core.confirm({
					title:"提示",
					message:"您确定要取消关联该存储吗?",
					yes:"确定",
					confirmCallback:function(){
						// 存储选中的集群
				   		for(var i=0;i<rowindex.length;i++){
				   			var data = $('#poolPsDatagrid').jqxGrid('getrowdata', rowindex[i]);
				   			if(i == rowindex.length-1){
				   				// 为了查询出集群下面的存储，加上引号
				   				resStorageSids += data.resStorageSid;
							}else{
								resStorageSids += data.resStorageSid + ",";
							}
				   		}
				   		
				   		// 提交取消关联
				   		Core.AjaxRequest({
			 				url : ws_url + "/rest/storage/cancelRelations",
			 				type:"POST",
			 				params:{
			 					resStorageSids : resStorageSids
			 				},
			 				callback : function (data) {
			 					  var ps = new poolPsDatagridModel();
			 					  ps.searchObj.storageNameLike = $("#search-ps-name").val();
			 					  ps.searchPsInfo();
			 					  // 取消选中
			 					 $('#poolPsDatagrid').jqxGrid('clearselection');
			 			    }
			 			});
				   		
					}
			  });
	  }
  }
  
  // 弹出新增关联存储window
  function popAddRelationStorageWindow(){
	    // 刷新可以关联的存储
	    var ps = new poolPsRelationModel();
	    ps.searchStoragesInfo();
	  	var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#relationPsPoolWindow").jqxWindow({ position: { x: (windowW-750)/2, y: (windowH-430)/2 } });
    	$("#relationPsPoolWindow").jqxWindow('open');
  }