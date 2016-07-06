var rscRemoveStorageMgtModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    
	    // 查询条件
	    this.searchObj = {
    		 storageNameLike:"",
    		 storageType : "",
    		 resStorageClassSid : resTopologySid
		};
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-rsc-remove-storage-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-rsc-remove-storage-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        // 初始化下拉列表框 
			 var codesearch = new codeModel({width:120,autoDropDownHeight:true});
			 codesearch.getCommonCode("search-rsc-remove-storage-type","STORAGE_TYPE",true);
			 
	    };
	    
	    // 用户数据
	    this.searchRemoveStorageRscInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/storage/rsc",
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
	 			    $("#storageRscRemoveDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initRemoveStorageDatagrid = function(){
	          $("#storageRscRemoveDatagrid").jqxGrid({
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
	                  { text: '存储类型', datafield: 'storageType'},
	                  { text: '总容量(GB)', datafield: 'totalCapacity',width:100},
	                  { text: '可用容量(GB)', datafield: 'availableCapacity',width:100},
	                  { text: '置备容量(GB)', datafield: 'provisionCapacity',width:100},
	                  { text: '分配容量(GB)', datafield: 'allotCapacity',width:100},
	                  { text: '分配率(%)', datafield: 'storageRate',width:80},
	                  { text: '状态', datafield: 'statusName',width:60}
	              ]
	              
	          });
	    };
	    
	    this.initPopWindow = function(){
			$("#rscRemoveStorageWindow").jqxWindow({
	                width: 900, 
	                height:430,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#rscRemoveStorageCancel"), 
	                theme: currentTheme,
	                modalOpacity: 0.3,
	                initContent:function(){
	                	$("#rscRemoveStorageSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        $("#rscRemoveStorageCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                }
	         });
	    };
  };
  
  
  // 查询存储类别下的存储
  function searchRscRemoveStorageConfigMgt(){
	  var storage = new rscRemoveStorageMgtModel();
	  storage.searchObj.storageNameLike = $("#search-rsc-remove-storage-name").val();
	  storage.searchObj.storageType = $("#search-rsc-remove-storage-type").val()=="全部"?"":$("#search-rsc-remove-storage-type").val();
	  storage.searchObj.resStorageClassSid = resTopologySid;
	  storage.searchRemoveStorageRscInfo();
  }
  
  // 弹出移除存储的window
  function popRemoveRscStorageWindow(){
	  var windowW = $(window).width(); 
	  var windowH = $(window).height(); 
	  $("#rscRemoveStorageWindow").jqxWindow({ position: { x: (windowW-900)/2, y: (windowH-430)/2 } });
	  $("#rscRemoveStorageWindow").jqxWindow('open');
  }
  
  // 移除存储
  function rscRemoveStorageSubmit(){
	     var resStorageSids = "";
		 var rowindex = $('#storageRscRemoveDatagrid').jqxGrid('getselectedrowindexes');
		 if(rowindex.length > 0){
			 Core.confirm({
					title:"提示",
					message:"确定要移除所选存储吗?",
					yes:"确定",
					confirmCallback:function(){
						// 存储选中的集群
				  		for(var i=0;i<rowindex.length;i++){
				  			var data = $('#storageRscRemoveDatagrid').jqxGrid('getrowdata', rowindex[i]);
				  			if(i == rowindex.length-1){
				  				resStorageSids+= data.resStorageSid;
							}else{
								resStorageSids+= data.resStorageSid+",";
							}
				  		}
				  		
				  		// 后台需要清除掉集群sid数组上面的引号
				   		Core.AjaxRequest({
								url : ws_url + "/rest/storage/rsc/relation",
								type:"post",
								params:{
									resStorageClassSid : resTopologySid,
									resStorageSids : resStorageSids,
									action : "remove"
								},
								callback : function (data) {
									// 清除选择项
									$('#storageRscRemoveDatagrid').jqxGrid('clearselection');
									// 关闭window
									$("#rscRemoveStorageWindow").jqxWindow('close');
									// 关闭添加存储window
									var rsc = new rscRemoveStorageMgtModel();
									rsc.searchRemoveStorageRscInfo();
									// 刷新左边的存储tree
									setStorageRefreshVirtualTreeValue();
							    }
						 });
					}
			 });
	  		
		 }
  }

  