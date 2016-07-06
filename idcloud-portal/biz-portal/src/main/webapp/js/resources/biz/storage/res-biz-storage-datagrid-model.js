var bizStorageConfigMgtModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    	 "qm.storageNameLike":null,
	    	 "qm.bizNameLike":null,
	    	 "qm.storageCategory":null,
	    	 "qm.hardDiskType":null,
	    	 "qm.storagePurpose":null,
//	    	 "qm.resStorageClassSid":null,
	    	 "qm.resBizLevel":resBizLevel,
	    	 "qm.resBizSid" : resBizSid	
		};
	    
	    
	    // 用户数据
	    this.searchStorageConfigInfo = function(){
			var dataAdapter = Core.getPagingDataAdapter({
				gridId: "bizStorageConfigdatagrid",
				url : ws_url + "/rest/storages/biz",
				params: me.searchObj
			});
			$("#bizStorageConfigdatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-biz-storage-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-biz-name-storage").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        
	        $("#search-biz-storage-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        // 初始化下拉列表框 
			var codesearch = new codeModel({width:120,autoDropDownHeight:true});
			codesearch.getCommonCode("search-biz-storage-type","STORAGE_CATEGORY",true);
			//codesearch.getCustomCode("search-biz-storage-mgt-classify","/topology/findStorageClassify/10","resTopologyName","resTopologySid",true,"GET");
			codesearch.getCommonCode("search-biz-storage-storagePurposes","STORAGE_PURPOSE",true);
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initStorageDatagrid = function(){
	    	var dataAdapter = Core.getPagingDataAdapter({
				gridId: "bizStorageConfigdatagrid",
				url : ws_url + "/rest/storages/biz",
				params: me.searchObj
			});
	          $("#bizStorageConfigdatagrid").jqxGrid({
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
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '存储名称', datafield: 'storageName',width:120},
	                  { text: '项目名称', datafield: 'mgtObjName',width:120},
	                  { text: '存储分类', datafield: 'storageCategoryName'},
	                  { text: '存储用途', datafield: 'storagePurposeName',width:100},
	                  { text: '总容量(GB)', datafield: 'totalCapacity'},
	                  { text: '可用容量(GB)', datafield: 'availableCapacity'},
	                  { text: '置备容量(GB)', datafield: 'provisionCapacity'},
	                  { text: '分配容量(GB)', datafield: 'allotCapacity'},
	                  { text: '使用率', datafield: 'storageUsage'},
	                  { text: '分配率', datafield: 'storageRate'},
	                  { text: '状态', datafield: 'statusName',width:80}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var exportBtn = $("<div><a id='jqxExportStorageBtn' onclick='exportBizStorageDatagrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-export'></i>导出&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(exportBtn);
	              }
	          });
	          
	    	  $("#jqxExportStorageBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
	          
	    };
  };
  
   //查询资源环境
  function searchBizStorageConfigMgt(){
	  var storage = new bizStorageConfigMgtModel();
	  storage.searchObj["qm.storageNameLike"] = $("#search-biz-storage-name").val();
	  storage.searchObj["qm.bizNameLike"] = $("#search-biz-name-storage").val();
	  storage.searchObj["qm.storageCategory"] = $("#search-biz-storage-type").val()=="全部"?"":$("#search-biz-storage-type").val();
	  storage.searchObj["qm.storagePurpose"] = $("#search-biz-storage-storagePurposes").val()=="全部"?"":$("#search-biz-storage-storagePurposes").val();
//	  storage.searchObj["qm.resStorageClassSid"] = $("#search-biz-storage-mgt-classify").val()=="全部"?"":$("#search-biz-storage-mgt-classify").val();
	  storage.searchStorageConfigInfo();
  }
  
  // 导出存储Excel
  function exportBizStorageDatagrid(){
	  
	  this.searchObj = {
			 storageNameLike : $("#search-biz-storage-name").val(),
		     storageType : $("#search-biz-storage-type").val()=="全部"?"":$("#search-biz-storage-type").val(),
			 resBizLevel:resBizLevel,
	    	 resBizSid : resBizSid		
      };
	  var params = JSON.stringify(searchObj);
	  window.location = ws_url + "/rest/storages/biz/exportBizStorage/" + params;
  }
  

  