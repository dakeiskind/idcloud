var ExchangeConfigModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		serviceAddress: "", 
	    		manageStatus:"",
	    		usageStatus:""
		};
	    // 给datagrid赋值
	    this.searchExchangeConfigInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/exchanges",
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
	 			    $("#exchangeConfigMgtdatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	    	$("#search-service-address").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:"metro"});
	    	
	    	var vlanconfig = new codeModel({width:120,autoDropDownHeight:true});
	    	vlanconfig.getCommonCode("search-exchange-mgt-status","MANAGEMENT_STATUS",true);
	    	vlanconfig.getCommonCode("search-exchange-usage-status","USAGE_STATUS",true);
	        $("#search-exchange-button").jqxButton({ width: '50px',height:'25px',theme:"metro"});
	    	
	    };
	    this.setEditExchangeForm = function(data){
	    	 $("#edit-serviceAddress").val(data.serviceAddress);
	         $("#edit-maxTenantCount").val(data.maxTenantCount);
	         $("#edit-maxStorageCapacity").val(data.maxStorageCapacity);
	    };
	    // 初始化验证规则   
	    this.initValidator = function(){
	    	$('#addExchangeForm').jqxValidator({
                rules: [  
                          { input: '#add-serviceAddress', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-serviceAddress', message: '服务地址不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          
                          { input: '#add-maxTenantCount', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-maxTenantCount', message: '最大分配租户数量名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' }
                       ]
	    	});

	    	$('#editExchangeForm').jqxValidator({
                rules: [  
                          { input: '#edit-serviceAddress', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-serviceAddress', message: '服务地址不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          
                          { input: '#edit-maxTenantCount', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-maxTenantCount', message: '最大分配租户数量名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' }
                       ]
	    	});
	    	
	    	// 新增Exchange验证成功
	    	$('#addExchangeForm').on('validationSuccess', function (event) {
		    		 var exchange = Core.parseJSON($("#addExchangeForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/exchanges/create",
		 				params :exchange,
		 				callback : function (data) {
		 					me.searchExchangeConfigInfo();
 							$("#addExchangeWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	$("#addExchangeWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
	    	
	    	// 编辑Exchange验证成功
	    	$('#editExchangeForm').on('validationSuccess', function (event) {
	    		 var exchange = Core.parseJSON($("#editExchangeForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/exchanges/update",
	 				params :exchange,
	 				callback : function (data) {
	 					me.searchExchangeConfigInfo();
						$("#editExchangeWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
	 			    	$("#editExchangeWindow").jqxWindow('close');
	 			    }
	 			});
   	         });
	    };
	    // 初始化用户datagrid的数据源
	    this.initExchangeDatagrid = function(){
	          $("#exchangeConfigMgtdatagrid").jqxGrid({
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
	                  { text: '最大邮箱存储容量', datafield: 'maxStorageCapacity',width:80},
	                  { text: '管理状态', datafield: 'manageStatusName'},
	                  { text: '使用状态', datafield: 'usageStatusName'}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addExchange = $("<div><a id='addExchange' data-bind='click:addExchangeInfo'>&nbsp;&nbsp;<i class='icon-plus'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editExchange = $("<div><a id='editExchange' style='margin-left:-1px' data-bind='click:editExchangeInfo'>&nbsp;&nbsp;<i class='icon-pencil'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var removeExchange = $("<div><a id='removeExchange' data-bind='click:removeExchangeInfo' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-cancel'></i>删除&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addExchange);
	                  container.append(editExchange);
	                  container.append(removeExchange);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#exchangeConfigMgtdatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#exchangeConfigMgtdatagrid').jqxGrid('getrowdata', index);
	   			  $("#editExchange").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: false});
	   			  $("#removeExchange").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: false});
	          });
	    	  
	    	  $("#addExchange").jqxButton({width: '60',theme:"metro",height: '18px', disabled: false });
   			  $("#editExchange").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: true});
   			  $("#removeExchange").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: true});
	          
	    };
	    // 初始化弹出window
	    this.initPopWindow = function(){
	    	$("#addExchangeWindow").jqxWindow({
                width: 500, 
                height:150,
                theme:currentTheme,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addExchangeCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#add-serviceAddress").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-maxTenantCount").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-maxStorageCapacity").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#addExchangeSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#addExchangeCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
                }
            });
           
	    	$("#editExchangeWindow").jqxWindow({
                width: 500, 
                height:150,
                theme:currentTheme,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#editExchangeCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#edit-serviceAddress").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#edit-maxTenantCount").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#edit-maxStorageCapacity").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#editExchangeSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#editExchangeCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
                }
            });
	    };

  };
  