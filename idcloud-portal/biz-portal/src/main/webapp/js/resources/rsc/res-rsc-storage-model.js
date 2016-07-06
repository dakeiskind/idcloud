var rscStorageMgtModel = function () {
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
	        $("#search-rsc-storage-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-rsc-storage-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        // 初始化下拉列表框 
			 var codesearch = new codeModel({width:120,autoDropDownHeight:true});
			 codesearch.getCommonCode("search-rsc-storage-type","STORAGE_TYPE",true);
			 
	    };
	    
	    // 用户数据
	    this.searchStorageRscInfo = function(){
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
	 			    $("#storageRscDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initStorageDatagrid = function(){
	          $("#storageRscDatagrid").jqxGrid({
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
	                  { text: '资源环境', datafield: 'resTopologyName'},
	                  { text: '存储类型', datafield: 'storageType'},
	                  { text: '存储类别', datafield: 'storageCategoryName'},
	                  { text: '总容量(GB)', datafield: 'totalCapacity',cellsalign:'right',width:100},
	                  { text: '可用容量(GB)', datafield: 'availableCapacity',cellsalign:'right',width:100},
	                  { text: '置备容量(GB)', datafield: 'provisionCapacity',cellsalign:'right',width:100},
	                  { text: '分配容量(GB)', datafield: 'allotCapacity',cellsalign:'right',width:100},
	                  { text: '分配率(%)', datafield: 'storageRate',cellsalign:'right',width:80},
	                  { text: '状态', datafield: 'statusName',align: 'center',cellsalign:'center'}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addBtn = $("<div><a id='jqxAddRscStorageBtn' onclick='popAddRscStorageWindow()' &nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>添加存储&nbsp;&nbsp;</a></div>");
	                  var deleteBtn = $("<div><a id='jqxRscDeleteStorageBtn' onclick='removeRscStorageWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>移除存储&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addBtn);
	                  container.append(deleteBtn);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#storageRscDatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#storageRscDatagrid').jqxGrid('getrowdata', index);
	    		  
	    		  $("#jqxRscDeleteStorageBtn").jqxButton({disabled: false});
	          });
	    	  
   			  $("#jqxRscDeleteStorageBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
	          $("#jqxAddRscStorageBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme});
	          
	    };
  };
  
  
  // 查询存储类别下的存储
  function searchRscStorageConfigMgt(){
	  var storage = new rscStorageMgtModel();
	  storage.searchObj.storageNameLike = $("#search-rsc-storage-name").val();
	  storage.searchObj.storageType = $("#search-rsc-storage-type").val()=="全部"?"":$("#search-rsc-storage-type").val();
	  storage.searchObj.resStorageClassSid = resTopologySid;
	  storage.searchStorageRscInfo();
  }
  
  // 弹出添加存储window
  function popAddRscStorageWindow(){
	 
	  // 刷新当前可以添加存储列表
	  var addStorage = new addStorageToRscModel();
	  addStorage.searchCanableStorageRscInfo();
      var windowW = $(window).width(); 
	  var windowH = $(window).height(); 
	  $("#addStorageToRscWindow").jqxWindow({ position: { x: (windowW-900)/2, y: (windowH-430)/2 } });
	  $("#addStorageToRscWindow").jqxWindow('open');
  }
  
  //弹出添加存储window---供非datagrid调用
  function popAddRscStorageWindowUsingByNoGrid(){
	  // 刷新当前可以添加存储列表
	  var addStorage = new addStorageToRscModel();
	  addStorage.searchCanableStorageRscInfo();
      var windowW = $(window).width(); 
	  var windowH = $(window).height(); 
	  $("#addStorageToRscWindow").jqxWindow({ position: { x: (windowW-750)/2, y: (windowH-430)/2 } });
	  $("#addStorageToRscWindow").jqxWindow('open');
  }
  
  
  // 移除存储
  function removeRscStorageWindow(){
	     var resStorageSids = "";
		 var rowindex = $('#storageRscDatagrid').jqxGrid('getselectedrowindexes');
		 if(rowindex.length > 0){
			 Core.confirm({
					title:"提示",
					message:"确定要移除所选存储吗?",
					yes:"确定",
					confirmCallback:function(){
						// 存储选中的集群
				  		for(var i=0;i<rowindex.length;i++){
				  			var data = $('#storageRscDatagrid').jqxGrid('getrowdata', rowindex[i]);
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
									$('#storageRscDatagrid').jqxGrid('clearselection');
									// 关闭添加存储window
									var rsc = new rscStorageMgtModel();
									rsc.searchStorageRscInfo();
									
									// 刷新左边的存储tree
									setStorageRefreshVirtualTreeValue();
							    }
						 });
					}
			 });
	  		
		 }
  }

  