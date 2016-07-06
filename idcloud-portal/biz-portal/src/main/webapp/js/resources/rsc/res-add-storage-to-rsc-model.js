var addStorageToRscModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    
	    // 查询条件
	    this.searchObj = {
    		 storageNameLike:"",
    		 storageCategory : ""
		};
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-add-rsc-storage-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-add-rsc-storage-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        // 初始化下拉列表框 
			 var codesearch = new codeModel({width:120,autoDropDownHeight:true});
			 codesearch.getCommonCode("search-add-rsc-storage-type","STORAGE_CATEGORY",true);
			 
	    };
	    
	    // 用户数据
	    this.searchCanableStorageRscInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/storage/rsc/add",
	 			type:'POST',
	 			params:me.searchObj,
	 			async:false,
	 			callback : function (data) {
	 				me.items(data);
	 				var sourcedatagrid ={
 			              datatype: "json",
 			              localdata: me.items
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#addStorageToRscDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initStorageDatagrid = function(){
	          $("#addStorageToRscDatagrid").jqxGrid({
	              width: "99.8%",
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
	                  { text: '资源环境', datafield: 'resTopologyName'},
	                  { text: '存储类别', datafield: 'storageCategoryName'},
	                  { text: '总容量(GB)', datafield: 'totalCapacity',width:80},
	                  { text: '可用容量(GB)', datafield: 'availableCapacity',width:80},
	                  { text: '置备容量(GB)', datafield: 'provisionCapacity',width:80},
	                  { text: '分配容量(GB)', datafield: 'allotCapacity',width:80},
	                  { text: '状态', datafield: 'statusName',width:60}
	              ],
	              showtoolbar: false,
	          });
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
			$("#addStorageToRscWindow").jqxWindow({
	                width: 900, 
	                height:430,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#addStorageToRscCancel"), 
	                theme: currentTheme,
	                modalOpacity: 0.3,
	                initContent:function(){
	                	$("#addStorageToRscSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        $("#addStorageToRscCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                }
	         });
	    };
  };
  
  // 查询存储类别下的存储
  function searchAddStorageToRscMgt(){
	  var storage = new addStorageToRscModel();
	  storage.searchObj.storageNameLike = $("#search-add-rsc-storage-name").val();
	  storage.searchObj.storageCategory = $("#search-add-rsc-storage-type").val()=="全部"?"":$("#search-add-rsc-storage-type").val();
	  storage.searchCanableStorageRscInfo();
  }
  
  // 添加存储到rsc
  function submitTheStoragesToRsc(){
	  	 var resStorageSids = "";
		 var rowindex = $('#addStorageToRscDatagrid').jqxGrid('getselectedrowindexes');
		 if(rowindex.length > 0){
			// 存储选中的集群
	  		for(var i=0;i<rowindex.length;i++){
	  			var data = $('#addStorageToRscDatagrid').jqxGrid('getrowdata', rowindex[i]);
	  			if(i == rowindex.length-1){
	  				resStorageSids+= data.resStorageSid;
				}else{
					resStorageSids+= data.resStorageSid+",";
				}
	  		}
	  		
	  		// 提交选中的集群和存储
	   		Core.AjaxRequest({
					url : ws_url + "/rest/storage/rsc/relation",
					type:"post",
					params:{
						resStorageClassSid : resTopologySid,
						resStorageSids : resStorageSids,
						action : "add"
					},
					callback : function (data) {
						// 清除选择项
						$('#addStorageToRscDatagrid').jqxGrid('clearselection');
						
						// 刷新当前可以添加存储列表
						var addStorage = new addStorageToRscModel();
						addStorage.searchCanableStorageRscInfo();
						
						// 关闭添加存储window
						$("#addStorageToRscWindow").jqxWindow('close');
						
						if($("#storageRscDatagrid").length > 0){
							// 刷新grid
							var rsc = new rscStorageMgtModel();
							rsc.searchStorageRscInfo();
						}else{
							// 刷新统计图
						}
						
						// 刷新左边的树
						setStorageRefreshVirtualTreeValue();
						
						
				    },
				    failure:function(data){
				    	
				    }
			 });
		 }
  }
  

  