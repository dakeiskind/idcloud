var VlanConfigModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		vlanName: "", 
	    		manageStatus:"",
	    		usageStatus:""
		};
	    // 给datagrid赋值
	    this.searchVlanConfigInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/vlans",
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
	 			    $("#vlanConfigMgtdatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	    	$("#search-vlan-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	    	
	    	var vlanconfig = new codeModel({width:120,autoDropDownHeight:true});
	    	vlanconfig.getCommonCode("search-vlan-mgt-status","MANAGEMENT_STATUS",true);
	    	vlanconfig.getCommonCode("search-valn-usage-status","USAGE_STATUS",true);
	        $("#search-vlan-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	    	
	    };
	    this.setEditVlanForm = function(data){
	    	 $("#edit-vlanId").val(data.vlanId);
	         $("#edit-vlanName").val(data.vlanName);
	         $("#edit-tag").val(data.tag);

	    };
	    // 初始化验证规则   
	    this.initValidator = function(){
	    	$('#addVlanForm').jqxValidator({
                rules: [  
                          { input: '#add-vlanId', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-vlanId', message: 'VLAN不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          
                          { input: '#add-vlanName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-vlanName', message: 'VLAN名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' }
                       ]
	    	});
	    	
	    	$('#editVlanForm').jqxValidator({
                rules: [  
                          { input: '#edit-vlanId', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-vlanId', message: 'VLAN不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          
                          { input: '#edit-vlanName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-vlanName', message: 'VLAN名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' }
                       ]
	    	});
	    	
	    	// 新增块存储验证成功
	    	$('#addVlanForm').on('validationSuccess', function (event) {
		    		 var vlan = Core.parseJSON($("#addVlanForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/vlans/create",
		 				params :vlan,
		 				callback : function (data) {
		 					me.searchVlanConfigInfo();
 							$("#addVlanWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	$("#addVlanWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
	    	
	    	// 编辑块存储验证成功
	    	$('#editVlanForm').on('validationSuccess', function (event) {
	    		 var vlans = Core.parseJSON($("#editVlanForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/vlans/update",
	 				params :vlans,
	 				callback : function (data) {
	 					me.searchVlanConfigInfo();
						$("#editVlanWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editVlanWindow").jqxWindow('close');
	 			    }
	 			});
   	         });
	    };
	    // 初始化用户datagrid的数据源
	    this.initVlanDatagrid = function(){
	          $("#vlanConfigMgtdatagrid").jqxGrid({
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
	                  { text: 'VLAN名称', datafield: 'vlanName',width:150},
	                  { text: 'VLAN ID', datafield: 'vlanId',width:150},
	                  { text: '标签', datafield: 'tag',width:80},
	                  { text: '管理状态', datafield: 'manageStatusName'},
	                  { text: '使用状态', datafield: 'usageStatusName'}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addVlan = $("<div><a id='addVlan' data-bind='click:addVlanInfo'>&nbsp;&nbsp;<i class='icon-plus'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editVlan = $("<div><a id='editVlan' style='margin-left:-1px' data-bind='click:editVlanInfo'>&nbsp;&nbsp;<i class='icon-pencil'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var removeVlan = $("<div><a id='removeVlan' data-bind='click:removeVlanInfo' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-cancel'></i>删除&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addVlan);
	                  container.append(editVlan);
	                  container.append(removeVlan);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#vlanConfigMgtdatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#vlanConfigMgtdatagrid').jqxGrid('getrowdata', index);
	   			  $("#editVlan").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	   			  $("#removeVlan").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	          });
	    	  // 初始化按钮
	    	  me.initButton();
	          
	    };
	    // 判断操作按钮，初始化按钮
	    this.initButton = function(){
	    	 // 判断datagrid是否有被选中的
	    	var selectedrowindex = $('#vlanConfigMgtdatagrid').jqxGrid('selectedrowindex'); 
	    	// 大于-1表示有被选中的
	    	if(selectedrowindex > -1){
	    		$("#editVlan").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	   			$("#removeVlan").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	    	}else{
	    		$("#addVlan").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
	  			$("#editVlan").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	  			$("#removeVlan").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	    	}
	    	
	    };
	    // 初始化弹出window
	    this.initPopWindow = function(){
	    	$("#addVlanWindow").jqxWindow({
                width: 500, 
                height:150,
                theme:currentTheme,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addVlanCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#add-vlanId").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-vlanName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-tag").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#addVlanSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#addVlanCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
                }
            });
	    	
	    	$("#editVlanWindow").jqxWindow({
                width: 500, 
                height:150,
                theme:currentTheme,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#editVlanCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#edit-vlanId").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#edit-vlanName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#edit-tag").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#editVlanSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#editVlanCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
                }
            });
	    };

  };
  