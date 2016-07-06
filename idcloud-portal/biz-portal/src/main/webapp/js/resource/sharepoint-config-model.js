var SharepointConfigModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		serviceAddress: "", 
	    		manageStatus:"",
	    		usageStatus:""
		};
	    // 给datagrid赋值
	    this.searchSharepointConfigInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/sharepoints",
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
	 			    $("#sharepointConfigMgtdatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	    	$("#search-sharepoint-service-address").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:"metro"});
	    	
	    	var vlanconfig = new codeModel({width:120,autoDropDownHeight:true});
	    	vlanconfig.getCommonCode("search-sharepoint-mgt-status","MANAGEMENT_STATUS",true);
	    	vlanconfig.getCommonCode("search-sharepoint-usage-status","USAGE_STATUS",true);
	        $("#search-sharepoint-button").jqxButton({ width: '50px',height:'25px',theme:"metro"});
	    	
	    };
	    this.setEditSharepointForm = function(data){
	    	 $("#edit-sharepoint-serviceAddress").val(data.serviceAddress);
	         $("#edit-sharepoint-maxStorageCapacity").val(data.maxStorageCapacity);
	    };
	    // 初始化验证规则   
	    this.initValidator = function(){
	    	$('#addSharepointForm').jqxValidator({
                rules: [  
                          { input: '#add-sharepoint-serviceAddress', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-sharepoint-serviceAddress', message: '服务地址不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          
                          { input: '#add-sharepoint-maxStorageCapacity', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-sharepoint-maxStorageCapacity', message: '最大分配租户数量名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' }
                       ]
	    	});

	    	$('#editSharepointForm').jqxValidator({
                rules: [  
                          { input: '#edit-sharepoint-serviceAddress', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-sharepoint-serviceAddress', message: '服务地址不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          
                          { input: '#edit-sharepoint-maxStorageCapacity', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-sharepoint-maxStorageCapacity', message: '最大分配租户数量名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' }
                       ]
	    	});
	    	
	    	// 新增Sharepoint验证成功
	    	$('#addSharepointForm').on('validationSuccess', function (event) {
		    		 var Sharepoint = Core.parseJSON($("#addSharepointForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/sharepoints/create",
		 				params :Sharepoint,
		 				callback : function (data) {
		 					me.searchSharepointConfigInfo();
 							$("#addSharepointWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	$("#addSharepointWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
	    	
	    	// 编辑Sharepoint验证成功
	    	$('#editSharepointForm').on('validationSuccess', function (event) {
	    		 var Sharepoint = Core.parseJSON($("#editSharepointForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/sharepoints/update",
	 				params :Sharepoint,
	 				callback : function (data) {
	 					me.searchSharepointConfigInfo();
						$("#editSharepointWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
	 			    	$("#editSharepointWindow").jqxWindow('close');
	 			    }
	 			});
   	         });
	    };
	    // 初始化用户datagrid的数据源
	    this.initSharepointDatagrid = function(){
	          $("#sharepointConfigMgtdatagrid").jqxGrid({
	              width: "100%",
				  theme:"metro",
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '服务地址', datafield: 'serviceAddress',width:150},
	                  { text: '最大分配租户数量', datafield: 'maxTenantCount',width:150},
	                  { text: '最大邮箱存储容量', datafield: 'maxStorageCapacity'},  
	                  { text: '管理状态', datafield: 'manageStatusName'},
	                  { text: '使用状态', datafield: 'usageStatusName'}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addSharepoint = $("<div><a id='addSharepoint' data-bind='click:addSharepointInfo'>&nbsp;&nbsp;<i class='icon-plus'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editSharepoint = $("<div><a id='editSharepoint' style='margin-left:-1px' data-bind='click:editSharepointInfo'>&nbsp;&nbsp;<i class='icon-pencil'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var removeSharepoint = $("<div><a id='removeSharepoint' data-bind='click:removeSharepointInfo' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-cancel'></i>删除&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addSharepoint);
	                  container.append(editSharepoint);
	                  container.append(removeSharepoint);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#sharepointConfigMgtdatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#sharepointConfigMgtdatagrid').jqxGrid('getrowdata', index);
	   			  $("#editSharepoint").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: false});
	   			  $("#removeSharepoint").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: false});
	          });
	    	  
	    	  $("#addSharepoint").jqxButton({width: '60',theme:"metro",height: '18px', disabled: false });
   			  $("#editSharepoint").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: true});
   			  $("#removeSharepoint").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: true});
	          
	    };
	    // 初始化弹出window
	    this.initPopWindow = function(){
	    	$("#addSharepointWindow").jqxWindow({
                width: 500, 
                height:120,
                theme:currentTheme,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addSharepointCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#add-sharepoint-serviceAddress").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-sharepoint-maxStorageCapacity").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#addSharepointSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#addSharepointCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
                }
            });
           
	    	$("#editSharepointWindow").jqxWindow({
                width: 500, 
                height:120,
                theme:currentTheme,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#editSharepointCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#edit-sharepoint-serviceAddress").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#edit-sharepoint-maxStorageCapacity").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#editSharepointCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
                }
            });
	    };

  };
  