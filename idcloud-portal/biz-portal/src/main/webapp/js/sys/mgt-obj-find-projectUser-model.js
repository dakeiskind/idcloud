var mgtObjSid = "";
var tenantPlatformModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    
	    // 用户数据
	    this.searchInfo = function(){
	    	$("#jqxgridBiz").on('rowselect', function (event) {
    			var args = event.args; 
    			var index = args.rowindex;
    			var data = $('#jqxgridBiz').jqxGrid('getrowdata', index);
    			mgtObjSid = data.mgtObjSid;
         
	    	$("#tenantUserPlatformDatagrid").jqxGrid("clearselection");
	    	initUserMenu();
	    	Core.AjaxRequest({
	    		url : ws_url + "/rest/user/findUsersByMgtObjSid/"+mgtObjSid,
				type:'get',
				async:false,
				callback : function(data) {
					
					me.items(data);
				    var sourcedatagrid = {
						datatype : "json",
						localdata : me.items
					};
					var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
					$("#tenantUserPlatformDatagrid").jqxGrid({source : dataAdapter});
				}
			});
	    	});
	    };
	    
	    this.searchRerInfo = function(){
	    	
	    	$("#tenantUserPlatformDatagrid").jqxGrid("clearselection");
	    	initUserMenu();
	    	Core.AjaxRequest({
	    		url : ws_url + "/rest/user/findUsersByMgtObjSid/"+mgtObjSid,
				type:'get',
				async:false,
				callback : function(data) {
					
					me.items(data);
				    var sourcedatagrid = {
						datatype : "json",
						localdata : me.items
					};
					var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
					$("#tenantUserPlatformDatagrid").jqxGrid({source : dataAdapter});
				}
			});
	    	
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	  $("#tenant-user-platform-account").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	    	  $("#tenant-user-platform-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	          var codesearch = new codeModel({width:100,autoDropDownHeight:true});
	    	  codesearch.getCommonCode("tenant-user-platform-status","USER_STATUS",true);
	    	  $("#search-user-platform-button").jqxButton({ width: '60px',height:"25px",theme:currentTheme});
	    };
	    
	    // 表单验证
	    this.initValidator = function(){
	    	$('#addQuestionForm').jqxValidator({
                rules: [
		                  { input: '#add-question-title', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-question-title', message: '问题标题不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
                          
                          { input: '#add-question-desc', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-question-desc', message: '问题标题不能超过256个字符', action: 'keyup, blur', rule: 'length=1,256' },
                          
                       ]
	    	});
	    	
	    	$('#editQuestionForm').jqxValidator({
                rules: [
		                  { input: '#edit-question-title', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-question-title', message: '问题标题不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
                          
                          { input: '#edit-question-desc', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-question-desc', message: '问题标题不能超过256个字符', action: 'keyup, blur', rule: 'length=1,256' },
                          
                       ]
	    	});

	    	// 新增用户表单验证成功
	    	$('#addQuestionForm').on('validationSuccess', function (event) {
		    		 var data = Core.parseJSON($("#addQuestionForm").serializeJson());
		    		 Core.AjaxRequest({
		    			url : ws_url + "/rest/issue/create",
		 				params :data,
		 				callback : function (data) {
		 					// 关闭新增问题页面
 							$("#addQuestionWindow").jqxWindow('close');
 							// 刷新datagrid
 							me.searchInfo();
		 			    }
		 			});
	    	 });
	    	
	    	// 编辑用户表单验证成功
	    	$('#editQuestionForm').on('validationSuccess', function (event) {
		    		 var data = Core.parseJSON($("#editQuestionForm").serializeJson());
		    		 Core.AjaxRequest({
		    			url : ws_url + "/rest/issue/update",
		 				params :data,
		 				callback : function (data) {
		 					// 关闭新增问题页面
 							$("#editQuestionWindow").jqxWindow('close');
 							// 刷新datagrid
 							me.searchInfo();
		 			    }
		 			});
	    	 });

	    };
	  
	    // 初始化用户datagrid的数据源
	    this.initDatagrid = function(){
	    	$("#tenantUserPlatformDatagrid").jqxGrid({
	    		  width: "100%", 
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              sortable: true,
	              autowidth: false,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '用户账号', datafield: 'account'},
	                  { text: '用户名称', datafield: 'realName'},
	                  { text: '所属项目经理', datafield: 'accountName'},
	                  { text: '所属项目', datafield: 'projectName'},
	                  { text: '联系电话', datafield: 'mobile'},
	                  { text: '电子邮箱', datafield: 'email'},
	                  { text: '用户状态', datafield: 'statusName',width:80},
	                  { text: '创建时间', datafield: 'createdDt'}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnTenantProGroup' style='margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var refresh = $("<a id='jqxProRefresh' onclick='searchTenantProUser()' >&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a>");
	                  var add = $("<a id='jqxProAdd' onclick='popAddProUserWindow()' >&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>新增&nbsp;&nbsp;</a>");
	                  var edit = $("<a id='jqxProEdit' onclick='popEditProUserWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a>");
	                  var remove = $("<a id='jqxProRemove' onclick='removeTenantProUser()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>删除&nbsp;&nbsp;</a>");
	                  var enable = $("<a id='jqxProEnable' onclick='enabledProUser()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>启用&nbsp;&nbsp;</a>");
	                  var disable = $("<a id='jqxProDisable' onclick='disableProUser()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>禁用&nbsp;&nbsp;</a>");
	                  var passwd = $("<a id='jqxProChangePasswd' onclick='popChangePasswdProWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>重置密码&nbsp;&nbsp;</a>");
	                  toolbar.append(container);
	                  container.append(refresh);
	                  container.append(add);
	                  container.append(edit);
	                  container.append(remove);
	                  container.append(enable);
	                  container.append(disable);
	                  container.append(passwd);
	              }
	          });

			// 控制按钮是否可用
			$("#tenantUserPlatformDatagrid").on('rowselect', function(event) {
				var args = event.args;
				var index = args.rowindex;
				var data = $('#tenantUserPlatformDatagrid').jqxGrid('getrowdata', index);
				if(data.status == "0"){
					$("#jqxProDisable").jqxButton({disabled : true});
					$("#jqxProEnable").jqxButton({disabled : false});
				}else{
					$("#jqxProDisable").jqxButton({disabled : false});
					$("#jqxProEnable").jqxButton({disabled : true});
				}
				$("#jqxProEdit").jqxButton({disabled : false});
				$("#jqxProRemove").jqxButton({disabled : false});
				$("#jqxProChangePasswd").jqxButton({disabled : false});
				
			});
			
			$("#jqxProRefresh").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : false});
			$("#jqxProAdd").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : false});
			$("#jqxProEdit").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : true});
			$("#jqxProRemove").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : true});
			$("#jqxProDisable").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : true});
			$("#jqxProChangePasswd").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : true});
			$("#jqxProEnable").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : true});
	    };
};

  // 删除用户
  function removeTenantProUser(){
	  var rowindex = $('#tenantUserPlatformDatagrid').jqxGrid('getselectedrowindex');
	  var ok = $("#jqxProRemove").jqxButton("disabled");
	  if(rowindex >=0 && !ok){
		  var data = $('#tenantUserPlatformDatagrid').jqxGrid('getrowdata', rowindex);
		  Core.confirm({
				message : "您确定要删除该用户吗？",
				confirmCallback : function() {
					Core.AjaxRequest({
						url : ws_url + "/rest/user/deleteUser?userSids="+data.userSid,
						type:"get",
						callback : function (data) {
							// 刷新datagrid
							var tenants = new tenantPlatformModel();
							tenants.searchRerInfo();
						}
				   });
				}
			});
	  }
  }
  
  // 启用用户
  function enabledProUser(){
	  var rowindex = $('#tenantUserPlatformDatagrid').jqxGrid('getselectedrowindex');
	  var ok = $("#jqxProEnable").jqxButton("disabled");
	  if(rowindex >=0 && !ok){
		  var data = $('#tenantUserPlatformDatagrid').jqxGrid('getrowdata', rowindex);
		  Core.confirm({
				message : "您确定要启用该用户吗？",
				confirmCallback : function() {
					if(data.status == "1"){
						Core.alert({title:"提示",message:"该用户已启用！"});
					}else{
						Core.AjaxRequest({
							url : ws_url + "/rest/user/operationUser?userSids="+data.userSid +"&action=1",
							type:"get",
							callback : function (data) {
								var tenants = new tenantPlatformModel();
								tenants.searchRerInfo();
							}
					   });
					}
				}
			});
	  }
  }
  
  // 禁用用户
  function disableProUser(){
	  var rowindex = $('#tenantUserPlatformDatagrid').jqxGrid('getselectedrowindex');
	  var ok = $("#jqxProDisable").jqxButton("disabled");
	  if(rowindex >=0 && !ok){
		  var data = $('#tenantUserPlatformDatagrid').jqxGrid('getrowdata', rowindex);
		  
		  Core.confirm({
				message : "您确定要禁用该用户吗？",
				confirmCallback : function() {
					if(data.status == "0"){
						Core.alert({title:"提示",message:"该用户已禁用！"});
					}else{
						Core.AjaxRequest({
							url : ws_url + "/rest/user/operationUser?userSids="+data.userSid+"&action=0",
							type:"get",
							callback : function (data) {
								var tenants = new tenantPlatformModel();
								tenants.searchRerInfo();
							}
					   });
					}
				}
		  });
	  }
  }
  
  // 查询用户
  function searchTenantProUser(){
	  $("#tenantUserPlatformDatagrid").jqxGrid("clearselection");
  	  initUserMenu();
	  Core.AjaxRequest({
			url : ws_url + "/rest/user/findUsersByParams",
			params : {
				"accountLike": ($("#tenant-user-platform-account").val() =="")? null:$("#tenant-user-platform-account").val(),
				"realNameLike": ($("#tenant-user-platform-name").val() =="")? null : $("#tenant-user-platform-name").val(),
				"status": ($("#tenant-user-platform-status").val() == "全部") ? null : $("#tenant-user-platform-status").val(),
			    "userMgtObj":mgtObjSid
			},
			callback : function (data) {
			    var sourcedatagrid = {
					datatype : "json",
					localdata : data
				};
				var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
				$("#tenantUserPlatformDatagrid").jqxGrid({source : dataAdapter});
			}
	   });
  }
  
  function initUserMenu(){
	    $("#jqxProRefresh").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : false});
		$("#jqxProAdd").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : false});
		$("#jqxProEdit").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : true});
		$("#jqxProRemove").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : true});
		$("#jqxProDisable").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : true});
		$("#jqxProChangePasswd").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : true});
		$("#jqxProEnable").jqxButton({width : '60',theme : currentTheme,height : '18px',disabled : true});
  }