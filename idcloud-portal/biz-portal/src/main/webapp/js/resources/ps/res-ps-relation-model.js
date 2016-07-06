var poolPsRelationModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    
	    this.searchObj = {
    		 storageNameLike:"",
    		 storageCategory : "",
    		 resTopologySid : resTopologySid
		};
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-select-storage-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-select-storage-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        // 初始化下拉列表框 
			 var codesearch = new codeModel({width:120,autoDropDownHeight:true});
			 codesearch.getCommonCode("search-select-storage-mgt-storageCategory","STORAGE_CATEGORY", true);
	    };
	    
	    // 用户数据
	    this.searchStoragesInfo = function(){
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/storage/findRelationStorage",
	 			type:'POST',
	 			params:me.searchObj,
 				async:false,
	 			callback : function (data) {
	 				var sourcedatagrid ={
				              datatype: "json",
				              localdata: data
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#poolPsRelationDatagrid").jqxGrid({source: dataAdapter});
	 			 }
 		  });
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initPsDatagrid = function(){
	          $("#poolPsRelationDatagrid").jqxGrid({
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
						{ text: '存储类型', datafield: 'storageType'},
						{ text: '存储分类', datafield: 'storageCategoryName'},
						{ text: '关联主机', datafield: 'ownerHost'},
						{ text: '所属集群', datafield: 'ownerCluster'},
						{ text: '可使用(GB)', datafield: 'availableCapacity',width:80},
						{ text: '总容量(GB)', datafield: 'totalCapacity',width:80},
						{ text: '状态', datafield: 'statusName'}
	              ]
	          });
	         
	    };
	    
	    this.initPopWindow = function(){
			$("#relationPsPoolWindow").jqxWindow({
	                width: 750, 
	                height:430,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#relationPoolCancel"), 
	                theme: currentTheme,
	                modalOpacity: 0.3,
	                initContent:function(){
	                	$("#relationPoolSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        $("#relationPoolCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                }
	         });
	    };
};

// 保存新增存储
function saveTheRelationStorage(){
	var resStorageSids = "";
	  var rowindex = $('#poolPsRelationDatagrid').jqxGrid('getselectedrowindexes');
	  if(rowindex.length > 0){
			// 存储选中的集群
	   		for(var i=0;i<rowindex.length;i++){
	   			var data = $('#poolPsRelationDatagrid').jqxGrid('getrowdata', rowindex[i]);
	   			if(i == rowindex.length-1){
	   				// 为了查询出集群下面的存储，加上引号
	   				if(data != null && data != "" && data != 'undefined' ){
	   					resStorageSids += data.resStorageSid;
					}
				}else{
					if(data != null && data != "" && data != 'undefined' ){
						resStorageSids += data.resStorageSid + ",";
					}
				}
	   		}
	   		
	   	    // 提交取消关联
	   		Core.AjaxRequest({
	   			url : ws_url + "/rest/storage/relationStorages",
				type:"POST",
				params:{
					resTopologySid : resTopologySid,
					resStorageSids : resStorageSids
				},
				callback : function (data) {
					// 关闭当前页面，刷新主页面
					$("#relationPsPoolWindow").jqxWindow('close');
					var poolps = new poolPsDatagridModel();
					poolps.searchPsInfo();
					// 刷新当前可关联存储
					var ps = new poolPsRelationModel();
					ps.searchStoragesInfo();
					// 取消掉datagrid的选中状态
					$('#poolPsRelationDatagrid').jqxGrid('clearselection');
					
			    }
			});
	 }
}

// 查询
function searchSelectStorageConfigMgt(){
	  var ps = new poolPsRelationModel();
	  ps.searchObj.storageNameLike = $("#search-select-storage-name").val();
	  ps.searchObj.storageCategory = $("#search-select-storage-mgt-storageCategory").val()=="全部"?"":$("#search-select-storage-mgt-storageCategory").val();
	  ps.searchObj.resTopologySid = resTopologySid;
	  ps.searchStoragesInfo();
}
