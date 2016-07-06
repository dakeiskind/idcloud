var StorageConfigModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		volumeName: "", 
	    		volumeType: "", 
	    		manageStatus:"",
	    		usageStatus:""
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
	    this.setEditStorageForm = function(data){
	    	 $("#edit-volumeName").val(data.volumeName);
	         $("#edit-availableCapacity").val(data.availableCapacity);
	         $("#edit-volumeType").val(data.volumeType);
	         $("#edit-hardDiskType").val(data.hardDiskType);
	         $("#edit-storageCategory").val(data.storageCategory);
	         $("#edit-storagePurpose").val(data.storagePurpose);
	         $("#edit-volumeUnitNo").val(data.volumeUnitNo);
	         $("#edit-volumeTag").val(data.volumeTag);
	    };
	    // 初始化验证规则   
	    this.initValidator = function(){
	    	$('#addStorageForm').jqxValidator({
                rules: [  
                          { input: '#add-volumeName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-volumeName', message: '存储名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
                          
                          { input: '#add-availableCapacity', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-availableCapacity', message: '总容量不能超过20个字符!', action: 'keyup, blur', rule: 'length=1,20' },
                          
                          { input: '#add-volumeUnitNo', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-volumeUnitNo', message: '存储标签不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' }
                       ]
	    	});
	    	
	    	$('#editStorageForm').jqxValidator({
                rules: [  
                          { input: '#edit-volumeName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-volumeName', message: '存储名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
                          
                          { input: '#edit-availableCapacity', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-availableCapacity', message: '总容量不能超过20个字符!', action: 'keyup, blur', rule: 'length=1,20' },
                          
                          { input: '#edit-volumeUnitNo', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-volumeUnitNo', message: '存储标签不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' }
                       ]
	    	});
	    	
	    	// 新增块存储验证成功
	    	$('#addStorageForm').on('validationSuccess', function (event) {
		    		 var storage = Core.parseJSON($("#addStorageForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/storages/create",
		 				params :storage,
		 				callback : function (data) {
		 					me.searchStorageConfigInfo();
 							$("#addStorageWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	$("#addStorageWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
	    	
	    	// 编辑块存储验证成功
	    	$('#editStorageForm').on('validationSuccess', function (event) {
	    		 var storage = Core.parseJSON($("#editStorageForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/storages/update",
	 				params :storage,
	 				callback : function (data) {
	 					me.searchStorageConfigInfo();
						$("#editStorageWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
	 			    	$("#editStorageWindow").jqxWindow('close');
	 			    }
	 			});
   	         });
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
	                  var approveDetail = $("<div><a id='addStorage' data-bind='click:addStorageInfo'>&nbsp;&nbsp;<i class='icon-plus'></i>新增&nbsp;&nbsp;</a></div>");
	                  var historyApprove = $("<div><a id='editStorage' style='margin-left:-1px' data-bind='click:editStorageInfo'>&nbsp;&nbsp;<i class='icon-pencil'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var ruleRemove = $("<div><a id='removeStorage' data-bind='click:removeStorageInfo' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-cancel'></i>删除&nbsp;&nbsp;</a></div>");
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
	    // 初始化弹出window
	    this.initPopWindow = function(){
	    	$("#addStorageWindow").jqxWindow({
                width: 500, 
                height:195,
                resizable: false,  
                theme:currentTheme,
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#storageCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#add-volumeName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-availableCapacity").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-volumeUnitNo").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-volumeTag").jqxInput({placeHolder: "", height: 22, width: 150,minLength: 0,maxLength: 32, theme:currentTheme});
        	       
        	        $("#storageSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#storageCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        
        	        var codeadd = new codeModel({width:150,autoDropDownHeight:true});
	       			 codeadd.getCommonCode("add-volumeType","STORAGE_TYPE");
	       			 codeadd.getCommonCode("add-hardDiskType","HARD_DISK_TYPE");
	       			 codeadd.getCommonCode("add-storageCategory","STORAGE_CATEGORY");
	       		     codeadd.getCommonCode("add-storagePurpose","STORAGE_PURPOSE");
                }
            });
	    	 
	    	 $("#editStorageWindow").jqxWindow({
	             width: 500, 
	             height:195,
	             resizable: false,  
	             theme:currentTheme,
	             isModal: true, 
	             autoOpen: false, 
	             cancelButton: $("#editStorageCancel"), 
	             modalOpacity: 0.3,
	             initContent:function(){
	             	// 初始化新增用户页面组件
	     	        $("#edit-volumeName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	     	        $("#edit-availableCapacity").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	     	        $("#edit-volumeUnitNo").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	     	        $("#edit-volumeTag").jqxInput({placeHolder: "", height: 22, width: 150,minLength: 0,maxLength: 32, theme:currentTheme});
	     	        $("#editStorageSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	     	        $("#editStorageCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	     	        
	     	        var codeadd = new codeModel({width:150,autoDropDownHeight:true});
	        			 codeadd.getCommonCode("edit-volumeType","STORAGE_TYPE");
	        			 codeadd.getCommonCode("edit-hardDiskType","HARD_DISK_TYPE");
	        			 codeadd.getCommonCode("edit-storageCategory","STORAGE_CATEGORY");
	        		     codeadd.getCommonCode("edit-storagePurpose","STORAGE_PURPOSE");
	             }
	         });
	    };
	    
	   

  };
  