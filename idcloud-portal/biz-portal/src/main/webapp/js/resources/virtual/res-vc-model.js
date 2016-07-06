var vmModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		 account: "", 
    			 realName : "", 
    			 userType: "", 
    			 roleSid: "",
    			 status:"",
		};
	    // 查询用户名是否重复
	    this.searchUserByName = function(name){
	    	var Todata = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/user/findAll",
	 			type:'post',
	 			params:{
	 				accountRepeat:name
	 			},
	 			async:false,
	 			callback : function (data) {
	 				Todata = data;
	 			}
	 		 });
	    	return Todata;
	    };
	    // 用户数据
	    this.searchVMInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/user/findAll",
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
	 			    $("#vmdatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initVMInput = function(){
	    	// 初始化查询组件
	        $("#search-vmaccount").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-vmname").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-vmbutton").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        
	        // 初始化下拉列表框 
			 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			 codesearch.getCommonCode("user_types","USER_TYPE",true);
			 codesearch.getCommonCode("search-statuss","USER_STATUS",true);
			 codesearch.getCustomCode("search-roles","/user/findAllRole","roleName","roleSid",true);
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initVMDatagrid = function(){
	          $("#vmdatagrid").jqxGrid({
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
	                  { text: '名称', datafield: 'realName',width:120},
	                  { text: '类型', datafield: 'userTypeName',width:120},
	                  { text: '描述', datafield: 'sexName',width:60},
	                  { text: 'DVS', datafield: 'roleName'},
	                  { text: '域', datafield: 'tenantName',width:150},
	                  { text: '资源环境', datafield: 'mobile',width:150},
	                  { text: '状态', datafield: 'email',width:150},
//	                  { text: '发现时间', datafield: 'statusName',width:100},
//	                  { text: '修改时间', datafield: 'statusName',width:100}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addBtn = $("<div><a id='jqxRelationBtn' data-bind='click: addUserItem'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>关联&nbsp;&nbsp;</a></div>");
	                  var editBtn = $("<div><a id='jqxChooseBtn' data-bind='click: editUserItem' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>选择列&nbsp;&nbsp;</a></div>");
	                  var deleteBtn = $("<div><a id='jqxRefreshBtn' data-bind='click: removeUserItem' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>刷新&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addBtn);
	                  container.append(editBtn);
	                  container.append(deleteBtn);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#vmdatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#vmdatagrid').jqxGrid('getrowdata', index);
	    		  
	    		  if(data.status == "2"){
	    			  $("#jqxChooseBtn").jqxButton({ disabled: false });
		   			  $("#jqxRefreshBtn").jqxButton({disabled: false});
	    		  }else{
	    			  $("#jqxChooseBtn").jqxButton({disabled: false });
		   			  $("#jqxRefreshBtn").jqxButton({disabled: false});
	    		  }
	          });
	    	  
	    	  $("#jqxChooseBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
   			  $("#jqxRefreshBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
	          $("#jqxRelationBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme});
	          
	    };
	    
	    // 初始化弹出window
	    this.initVMPopWindow = function(){
//			$("#addUserWindow").jqxWindow({
//	                width: 550, 
//	                height:270,
//	                resizable: false,  
//	                isModal: true, 
//	                autoOpen: false, 
//	                cancelButton: $("#Cancel"), 
//	                theme: currentTheme,
//	                modalOpacity: 0.3,
//	                initContent:function(){
//	                	 // 初始化新增用户页面组件
//	        	        $("#add-account").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
//	        	        
//	        	        $("#add-password").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
//	        	        $("#add-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
//	        	        $("#add-mobil").jqxInput({placeHolder: "", height: 22, width: 150,minLength: 0,maxLength: 32, theme:currentTheme});
//	        	        $("#add-email").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});  
//	        	        $("#add-limit").jqxNumberInput({ width: '150px', height: '22px', inputMode: 'simple',theme:currentTheme, spinButtons: true,decimalDigits:0, min: 0, max: 10, value: 0});
//	        	        $("#add-role-0").jqxCheckBox({ width: 90, height: 22,theme:currentTheme});
//	        	        $("#add-role-1").jqxCheckBox({ width: 90, height: 22,theme:currentTheme});
//	        	        $("#add-role-2").jqxCheckBox({ width: 100, height: 22,checked: true,theme:currentTheme});
//	        	        $("#add-role-3").jqxRadioButton({ width: 100, height: 22,theme:currentTheme});
//	        	        $("#add-role-4").jqxRadioButton({ width: 100, height: 22,checked: true,theme:currentTheme});
//	        	        $("#Save").jqxButton({ width: '50',height:"25",theme:currentTheme});
//	        	        $("#Cancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
//	        	        
//	        	        var codeadd = new codeModel({width:150,autoDropDownHeight:true});
//	           			 codeadd.getCommonCode("add-type","USER_TYPE");
//	           			 codeadd.getCommonCode("add-status","USER_STATUS");
//	           			 codeadd.getCustomCode("addUserOrgSid","/org/findAllOrgInfo","orgName","orgSid");
//
//	           			 codeadd.getCommonCode("add-sex","GENDER");
//	           			 codeadd.getCustomCode("tenantSidUser","/user/platform/findAllTenant","tenantName","tenantSid");
//	                }
//	         });
//			$("#editUserWindow").jqxWindow({
//                width: 550, 
//                height:270,
//                resizable: false,  
//                isModal: true, 
//                autoOpen: false, 
//                cancelButton: $("#editCancel"), 
//                theme: currentTheme,
//                modalOpacity: 0.3           
//             });
//	         $("#changePasswdWindow").jqxWindow({
//	                width: 300, 
//	                height:170,
//	                resizable: false,  
//	                isModal: true, 
//	                autoOpen: false, 
//	                cancelButton: $("#passwdCancel"),
//	                theme: currentTheme,
//	                modalOpacity: 0.3           
//	             });
	    };

  };

  