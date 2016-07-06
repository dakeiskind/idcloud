var tenantModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.shortName = null;
	    // sunyu add for #194
	    this.oldTenantName = null;
	    // end
	    this.cuttentData = new Object();
	    this.searchObj = {
	    		 "qm.tenantNameLike": "", 
	    		 "qm.tenantType" : "", 
	    		 "qm.businessType": "", 
	    		 "qm.status": ""
		};
	    // 查询租户名是否重复
	    this.searchTenantByName = function(name){
	    	var Todata = null;
	    	Core.AjaxRequest({
	    		url : ws_url + "/rest/tenants",
	 			type:'post',
	 			params:{
	 				tenantNameRepeat:name
	 			},
	 			async:false,
	 			callback : function (data) {
	 				Todata = data;
	 			}
	 		 });
	    	return Todata;
	    };
	    // 得到所有租户信息
	    this.getTenantInfo = function(){
	    	var tenantAll ;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/tenants",
	 			type:'post',
	 			params:me.searchObj,
	 			async:false,
	 			callback : function (data) {
	 				tenantAll = data;
	 			}
	 		 });
	    	
	    	return tenantAll;
	    };
	    // 用户数据
	    this.searchTenantInfo = function(options){
//	    	 Core.AjaxRequest({
//	 			url : ws_url + "/rest/tenants",
//	 			type:'post',
//	 			params:me.searchObj,
//	 			async:false,
//	 			callback : function (data) {
//	 				
//	 				me.items(data);
//	 				var sourcedatagrid ={
// 			              datatype: "json",
// 			              localdata: me.items
//	 			    };
//	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
//	 			    $("#tenantdatagrid").jqxGrid({source: dataAdapter});
//	 			}
//	 		 });
			var dataAdapter = Core.getPagingDataAdapter({
				gridId: "tenantdatagrid",
				url: ws_url + "/rest/tenants/paging",
				params: me.searchObj
			});
			$("#tenantdatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-tenant-name").jqxInput({placeHolder: "", height: 23, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-tenant-button").jqxButton({ width: '50',theme:currentTheme});
	        
	        var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			codesearch.getCommonCode("search-tenant-type","TENANT_TYPE",true);
			codesearch.getCommonCode("search-enterprise-type","BUSINESS_TYPE",true);
			codesearch.getCommonCode("search-tenant-status","TENANT_STATUS",true);
			
			// 新增租户组件初始化
			 $("#add-tenant-name").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
	         $("#add-tenant-contact").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
	         $("#add-tenant-contactPhone").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 0,maxLength:32,theme:currentTheme});
	         $("#add-tenant-contactPosition").jqxInput({placeHolder: "", height: 23, width: 120, theme:currentTheme});
	         $("#add-tenant-email").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});  
	         $("#add-tenant-postCode").jqxInput({placeHolder: "", height: 23, width: 120, maxLength: 10,theme:currentTheme});  
	         $("#add-tenant-domainName").jqxInput({placeHolder: "", height: 23, width: 120, maxLength:64,minLength: 1,theme:currentTheme});
	         $("#add-Tenant-domainAddress").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
	         $("#add-Tenant-tenantShortName").jqxInput({placeHolder: "", height: 23, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
	         $("#add-Tenant-serviceLimitQuantity").jqxNumberInput({ width: '120px', height: '23px', inputMode: 'simple',theme:currentTheme, spinButtons: true,decimalDigits:0, min: 0, max: 10, value: 0});
	         $("#add-Tenant-address").jqxInput({placeHolder: "", height: 23, width: 400, maxLength:64,theme:currentTheme});
	         $("#add-Tenant-description").jqxInput({placeHolder: "", height: 40, width: 400, maxLength:512,theme:currentTheme});
	         $("#tenantSave").jqxButton({ width: '50',theme:currentTheme});
		     $("#tenantCancel").jqxButton({ width: '50',theme:currentTheme});
	         
	         var codeadd = new codeModel({width:120,autoDropDownHeight:true});
			 codeadd.getCommonCode("add-tenant-type","TENANT_TYPE");
			 codeadd.getCommonCode("add-Tenant-status","TENANT_STATUS");
			 codeadd.getCommonCode("add-Tenant-businessType","BUSINESS_TYPE");
			 codeadd.getCustomCode("add-Tenant-vlan","/vlan/findVlan","vlanName","vlanId");
   			 codeadd.getCustomCode("addOrgSid","/org/findAllOrgInfo","orgName","orgSid");
   			
			// 新增租户组件初始化
			 $("#edit-tenant-name").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
	         $("#edit-tenant-contact").jqxInput({placeHolder: "", height: 23, width: 120, maxLength:32,theme:currentTheme});
	         $("#edit-tenant-contactPhone").jqxInput({placeHolder: "", height: 23, width: 120,theme:currentTheme});
	         $("#edit-tenant-contactPosition").jqxInput({placeHolder: "", height: 23, width: 120, maxLength:32,theme:currentTheme});
	         $("#edit-tenant-email").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});  
	         $("#edit-tenant-postCode").jqxInput({placeHolder: "", height: 23, width: 120, maxLength: 10,theme:currentTheme});  
	         $("#edit-tenant-domainName").jqxInput({placeHolder: "", height: 23, width: 120, maxLength:64,minLength: 1,theme:currentTheme});
	         $("#edit-Tenant-tenantShortName").jqxInput({placeHolder: "", height: 23, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
	         $("#edit-Tenant-domainAddress").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
	         $("#edit-Tenant-serviceLimitQuantity").jqxNumberInput({ width: '120px', height: '23px', inputMode: 'simple',theme:currentTheme, spinButtons: true,decimalDigits:0, min: 0, max: 10, value: 0});
	         $("#edit-Tenant-address").jqxInput({placeHolder: "", height: 23, width: 400, maxLength: 64,theme:currentTheme});
	         $("#edit-Tenant-description").jqxInput({placeHolder: "", height: 40, width: 400, maxLength: 512,theme:currentTheme});
	         $("#editTenantSave").jqxButton({ width: '50',theme:currentTheme});
		     $("#editTenantCancel").jqxButton({ width: '50',theme:currentTheme});
	         
	         var codeedit = new codeModel({width:120,autoDropDownHeight:true});
			 codeedit.getCommonCode("edit-tenant-type","TENANT_TYPE");
			 codeedit.getCommonCode("edit-Tenant-status","TENANT_STATUS");
			 codeedit.getCommonCode("edit-Tenant-businessType","BUSINESS_TYPE");
			 codeedit.getCustomCode("edit-Tenant-vlan","/vlan/findVlan","vlanName","vlanId");
			 codeedit.getCustomCode("editOrgSid","/org/findAllOrgInfo","orgName","orgSid");

			// 配额信息初始化
			 $("#quotaTenantSave").jqxButton({ width: '50',theme:currentTheme});
		     $("#quotaTenantCancel").jqxButton({ width: '50',theme:currentTheme});
	    	 
	    };
	    this.setEditTenantForm = function(data){
	    	 cuttentData = data;
	    	 me.shortName = data.tenantShortName;
	    	 // sunyu add for #194
	    	 me.oldTenantName = data.tenantName;
	    	 // end
	    	 $("#edit-tenant-name").jqxInput({value:data.tenantName});
	         $("#edit-tenant-contact").jqxInput({value:(data.contact == null)? "" :data.contact});
	         $("#edit-tenant-contactPhone").jqxInput({value:(data.contactPhone == null)? "" :data.contactPhone});
	         $("#edit-tenant-contactPosition").jqxInput({value:(data.contactPosition == null)? "" :data.contactPosition});
	         $("#edit-tenant-email").jqxInput({value:(data.email == null)? "" :data.email});  
	         $("#edit-tenant-postCode").jqxInput({value:(data.postCode == null)? "" :data.postCode});  
	         $("#edit-tenant-domainName").jqxInput({value:(data.domainName == null)? "" :data.domainName});
	         
	         $("#edit-Tenant-tenantShortName").jqxInput({value:(data.tenantShortName == null)? "" :data.tenantShortName});
	         
	         $("#edit-Tenant-domainAddress").jqxInput({value:(data.domainAddress == null)? "" :data.domainAddress});
	         $("#edit-Tenant-serviceLimitQuantity").jqxNumberInput({value:(data.serviceLimitQuantity == null)? 0 :data.serviceLimitQuantity});
	         $("#edit-Tenant-address").jqxInput({value:(data.address == null)? "" :data.address});
	         $("#edit-Tenant-description").jqxInput({value:(data.description == null)? "" :data.description});
	         
	         $("#edit-tenant-type").val(data.tenantType);
	         $("#edit-Tenant-status").val(data.status);
	         $("#edit-Tenant-businessType").val(data.businessType);
	         $("#edit-Tenant-vlan").val((data.vlanId == null)?"":data.vlanId);
	    };
	    // 初始化验证规则
	    this.initValidator = function(){
	    	
	    	$('#addTenantForm').jqxValidator({
                rules: [
                        	// 租户名称
                          { input: '#add-tenant-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-tenant-name', message: '租户名称不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
                          { input: '#add-tenant-name', message: '租户名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
                        	  var list = me.searchTenantByName(input.val());
                        	  	if(list.length > 0){
                        	  		return false;
                        	  	}else{
                        	  		return true;
                        	  	}
                  	  		}
		                  },
		                  // OU名称
                          { input: '#add-Tenant-tenantShortName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-Tenant-tenantShortName', message: 'OU名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
                        	    //input.val()
                        	  	var list = me.getTenantInfo();
                        	  	var isOk = false;
                        	  	for(var i=0; i<list.length;i++){
                        	  		if(input.val() == list[i].tenantShortName){
                        	  			isOk = true;
                        	  			break;
                        	  		}
                        	  	}
                        	  	if(isOk){
                        	  		return false;
                        	  	}else{
                        	  		return true;
                        	  	}
                            }
		                  },
                          // 邮箱
                          { input: '#add-tenant-email', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-tenant-email', message: '请输入正确的邮箱地址', action: 'keyup', rule: 'email' },

                       ]
	    	});
	    	
	    	
	    	$('#editTenantForm').jqxValidator({
                rules: [
                        	// 租户名称
                          { input: '#edit-tenant-name', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-tenant-name', message: '租户名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          // sunyu update for #194
                          { input: '#edit-tenant-name', message: '租户名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
                        	  if (me.oldTenantName == input.val()) {
                        		  return true;
                        	  } else {
                        		  	var list = me.searchTenantByName(input.val());
	                          	  	if(list.length > 0){
	                          	  		return false;
	                          	  	}else{
	                          	  		return true;
	                          	  	}
                    	  		}
                        	  }
                        	  
		                  },
		                  // OU名称
                          { input: '#edit-Tenant-tenantShortName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-Tenant-tenantShortName', message: 'OU名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
                        	    //input.val()
                        	  if(cuttentData.tenantShortName == input.val()){
                        		  return true;
                        	  }else{
                        		  var list = me.getTenantInfo();
                          	  	var isOk = false;
                          	  	for(var i=0; i<list.length;i++){
                          	  		if(input.val() == list[i].tenantShortName){
                      	  				isOk = true;
                          	  			break;
                          	  		}
                          	  	}
                          	  	if(isOk){
                          	  		return false;
                          	  	}else{
                          	  		return true;
                          	  	}
                        	  }
                            }
		                  },
                          // 邮箱
                          { input: '#edit-tenant-email', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-tenant-email', message: '请输入正确的邮箱地址!', action: 'keyup', rule: 'email' },
                          
                       ]
	    	});
	    	
	    	// 新增用户表单验证成功
	    	$('#addTenantForm').on('validationSuccess', function (event) {
		    		 var tenant = Core.parseJSON($("#addTenantForm").serializeJson());
		    		 Core.AjaxRequest({         
		 				url : ws_url + "/rest/tenants/create",
		 				params :tenant,
		 				callback : function (data) {
 							me.searchTenantInfo();
 							$("#addTenantWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
 							$("#addTenantWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
	    	
	    	// 编辑用户表单验证成功
	    	$('#editTenantForm').on('validationSuccess', function (event) {
		    		 var tenant = Core.parseJSON($("#editTenantForm").serializeJson());
		    		 Core.AjaxRequest({         
		 				url : ws_url + "/rest/tenants/update",
		 				params :tenant,
		 				callback : function (data) {
 							me.searchTenantInfo();
 							$("#editTenantWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	$("#editTenantWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
	    };
	    // 初始化用户datagrid的数据源
	    this.initTenantDatagrid = function(){
			var dataAdapter = Core.getPagingDataAdapter({
				gridId: "tenantdatagrid",
				url: ws_url + "/rest/tenants/paging",
				params: me.searchObj
			});
	          $("#tenantdatagrid").jqxGrid({
	              width: "100%",
	              source: dataAdapter,
	              virtualmode: true,
	              rendergridrows: function(){
	            	  return dataAdapter.records;
	              },
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
	                  { text: '租户名称', datafield: 'tenantName',width:150},
	                  { text: '租户类型', datafield: 'tenantTypeName',width:80},
	                  { text: '状态', datafield: 'statusName'},
	                  { text: 'VLAN名称', datafield: 'vlanName',width:100},
	                  { text: 'OU名称', datafield: 'tenantShortName'},
	                  { text: '域名地址', datafield: 'domainAddress'},
	                  { text: '项目类型', datafield: 'businessTypeName'},
	                  { text: '联系人', datafield: 'contact',width:80},
	                  { text: '联系人电话', datafield: 'contactPhone',width:100},
	                  { text: '邮箱', datafield: 'email',width:150}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnTenantGroup' style='margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addBtn = $("<a id='jqxAddTenantBtn' onclick='addTenantItem()' >&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a>");
	                  var editBtn = $("<a id='jqxEditTenantBtn' onclick='editTenantItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a>");
	                  var deleteBtn = $("<a id='jqxDeleteTenantBtn' onclick='removeTenantItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a>");
	                  var changeBtn = $("<a id='jqxCheckTenantBtn' onclick='checkTenant()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-ok-3'></i>审核&nbsp;&nbsp;</a>");
	                  var checkBtn = $("<a id='jqxQuotaTenantBtn' onclick='quotaTenant()'  style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-feather'></i>配额&nbsp;&nbsp;</a>");
	                  toolbar.append(container);
	                  container.append(addBtn);
	                  container.append(editBtn);
	                  container.append(deleteBtn);
	                  container.append(changeBtn);
	                  container.append(checkBtn);
	                  
	    	    	  $("#jqxEditTenantBtn").jqxButton({width: 60,height:18,theme:currentTheme, disabled: true });
	       			  $("#jqxDeleteTenantBtn").jqxButton({ width: 60,height:18,theme:currentTheme,disabled: true});
	       			  $("#jqxCheckTenantBtn").jqxButton({ width: 60,height:18,theme:currentTheme,disabled: true});
	       			  $("#jqxQuotaTenantBtn").jqxButton({ width: 60,height:18,theme:currentTheme,disabled: true});
	    	          $("#jqxAddTenantBtn").jqxButton({ width: 60,height:18,theme:currentTheme});
	              }
	          });
	          
	          

	          
	          // 控制按钮是否可用
	    	  $("#tenantdatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#tenantdatagrid').jqxGrid('getrowdata', index);
	    		  if(data.status == "1"){
	    			  $("#jqxEditTenantBtn").jqxButton({ disabled: false });
		   			  $("#jqxDeleteTenantBtn").jqxButton({disabled: false});
		   			  $("#jqxCheckTenantBtn").jqxButton({disabled: false});
		   			  $("#jqxQuotaTenantBtn").jqxButton({disabled: false});
	    		  }else{
	    			  $("#jqxEditTenantBtn").jqxButton({ disabled: false });
		   			  $("#jqxDeleteTenantBtn").jqxButton({disabled: false});
		   			  $("#jqxCheckTenantBtn").jqxButton({disabled: true});
		   			  $("#jqxQuotaTenantBtn").jqxButton({disabled: false});
	    		  }
	          });
	    };
	    // 初始化弹出window
	    this.initPopWindow = function(){
			$("#addTenantWindow").jqxWindow({
	                width: 650, 
	                height:300,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#tenantCancel"), 
	                theme: currentTheme,
	                modalOpacity: 0.3           
	         });
			$("#editTenantWindow").jqxWindow({
					width: 650, 
	                height:300,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                theme: currentTheme,
	                cancelButton: $("#editTenantCancel"), 
	                modalOpacity: 0.3                   
             });
	         $("#quotaTenantWindow").jqxWindow({
	                width: 650, 
	                height:500,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                theme: currentTheme,
	                cancelButton: $("#quotaTenantCancel"), 
	                modalOpacity: 0.3           
	         });
	    };
	    
    /*************************租户绑定事件********************************/      
      /** 弹出新增租户window */
      this.addTenantItem = function () {
    	   //初始化租户新增画面
    	     $("#add-tenant-name").val("");
	         $("#add-tenant-contact").val("");
	         $("#add-tenant-contactPhone").val("");
	         $("#add-tenant-contactPosition").val("");
	         $("#add-tenant-email").val("");
	         $("#add-Tenant-tenantShortName").val("");
	         $("#add-tenant-postCode").val("");
	         $("#add-tenant-domainName").val("");
	         $("#add-Tenant-domainAddress").val("");
	         $("#add-Tenant-serviceLimitQuantity").val("");
	         $("#add-Tenant-address").val("");
	         $("#add-Tenant-description").val("");
	         
	         var codeadd = new codeModel({autoDropDownHeight:true});
			 codeadd.getCommonCode("add-tenant-type","TENANT_TYPE");
			 codeadd.getCommonCode("add-Tenant-status","TENANT_STATUS");
			 codeadd.getCommonCode("add-Tenant-businessType","BUSINESS_TYPE");
			 codeadd.getCustomCode("add-Tenant-vlan","/vlan/findVlan","vlanName","vlanId");
    	  
    	  
	    	var windowW = $(window).width(); 
	    	var windowH = $(window).height(); 
	    	$("#addTenantWindow").jqxWindow({ position: { x: (windowW-650)/2, y: (windowH-300)/2 } });
	    	$("#addTenantWindow").jqxWindow('open');
	    };
	    
	   /** 提交新增租户信息 */  
	   this.addTenant_submit = function(){
		   $('#addTenantForm').jqxValidator('validate');
	   };
	   
	   /** 删除租户 */
	   this.removeTenantItem = function(){
		   var rowindex = $('#tenantdatagrid').jqxGrid('getselectedrowindex');
	    	if(rowindex >= 0){
	    		var data = $('#tenantdatagrid').jqxGrid('getrowdata', rowindex);
		    	Core.confirm({
					title:"提示",
					message:"确定要删除该租户吗？",
					confirmCallback:function(){
						Core.AjaxRequest({
			 				url : ws_url + "/rest/tenants/delete/" + data.tenantSid,
			 				type: "get",
			 				callback : function (data) {
			 					this.searchTenantInfo();
			 			    },
			 			    failure:function(data){
			 			    	this.searchTenantInfo();
			 			    }
			 			});
					}
		    	});
	    	}
	   };
	   
	   /** 编辑租户 */
	   this.editTenantItem = function(){
		   var rowindex = $('#tenantdatagrid').jqxGrid('getselectedrowindex');
	    	if(rowindex >= 0){
	    		    var data = $('#tenantdatagrid').jqxGrid('getrowdata', rowindex);
	    		    $("#tenantSid").val(data.tenantSid);
	    		    // 将常用的字段可以使用这个方法设置数据
	    		    this.setEditTenantForm(data);
	    		   
	    		    var windowW = $(window).width(); 
	    	    	var windowH = $(window).height(); 
	    	    	$("#editTenantWindow").jqxWindow({ position: { x: (windowW-650)/2, y: (windowH-300)/2 } });
	    	    	$("#editTenantWindow").jqxWindow('open');
	    	}
	   };
	   
	   /** 提交编辑租户信息 */
	   this.editTenant_submit = function(){
		   $('#editTenantForm').jqxValidator('validate');
	   };
	   
	   /** 审核租户 */
	   this.checkTenant = function(){
			var rowindex = $('#tenantdatagrid').jqxGrid('getselectedrowindex');
	      	// 判断审核按钮是否可用
	      	var ok =  $("#jqxCheckTenantBtn").jqxButton("disabled");
		    	if(rowindex >= 0 && !ok){
		    		var data = $('#tenantdatagrid').jqxGrid('getrowdata', rowindex);
				    	Core.confirm({
							title: "请选择",
							message: "您确定审核通过该租户吗？",
							confirmCallback:function(){
								Core.AjaxRequest({
					 				url : ws_url + "/rest/tenants/" + data.tenantSid + "/operation/approved",
					 				type: "get",
					 				callback : function (data) {
					 					this.searchTenantInfo();
					 			    },
					 			    failure:function(data){
			 							this.searchTenantInfo();
					 			    }
					 			});
							}
					});
		    	}
	   };
	   
	   /** 显示配额window */
	   this.quotaTenant = function(){
		    var rowindex = $('#tenantdatagrid').jqxGrid('getselectedrowindex');
	    	if(rowindex >= 0){
	    		    var data = $('#tenantdatagrid').jqxGrid('getrowdata', rowindex);
	    		    // 查询该租户是否有配额
	    		    Core.AjaxRequest({
		 				url : ws_url + "/rest/tenants/" + data.tenantSid + "/quotas",
		 				type : "POST",
		 				callback : function (data) {
		 					if(data == null || data == ""){
		 						Core.alert({
			 						message:"对不起，您没有任何配额信息！"
			 					});
		 					}else{
		 						var str = "";
		 						for(var i= 0; i<data.length;i++){
		 							str +="<tr>"+
		 									"<td width='150' align='right'>"+data[i].quotaName+"</td>"+
		 									"<td width='150'><input type='text' data-name="+data[i].quotaId+" class='quota' value="+data[i].quotaValue+" /></td>"+
		 									"<td><span style='font-size:12px;color:green'>※"+data[i].description+"</span></td>"+
	 									"</tr>";
		 						}
		 						
		 						$("#tableContent").html(str);
		 						$(".quota").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,maxLength: 10,theme:currentTheme});
		 						
		 						var windowW = $(window).width(); 
		 		    	    	var windowH = $(window).height(); 
		 		    	    	$("#quotaTenantWindow").jqxWindow({ position: { x: (windowW-650)/2, y: (windowH-500)/2 } });
		 		    	    	$("#quotaTenantWindow").jqxWindow('open');
		 					}
		 			    }
		 			});
	    	}
	   };
	   /** 保存修改租户的配额 */
	   this.quota_submit = function(){
		   var quota = Core.parseJSON($("#tableContent").serializeJson());
		   var json = JSON.stringify(quota).replace("{","").replace("}","").split(",");
		   var array = new Array();
		   for(var i=0;i<json.length;i++){
			   var quo = json[i].split(":");
			   var len0 = quo[0].length;
			   var len1 = quo[1].length;
			   var quota ={
				   "quotaId":quo[0].substring(1,len0-1),
				   "quotaValue":quo[1].substring(1,len1-1)
			   };
			   array[i] = quota;
		   }
		   Core.AjaxRequest({
				url : ws_url + "/rest/tenants/quotas/update",
				type:"post",
				params:array,
				callback : function (data) {
					$("#quotaTenantWindow").jqxWindow('close');
			    },
			    failure:function(data){

			    }
			});
		   
	   };

	   /** 租户查询 */
	   this.tenantSearch = function(){
		   this.searchObj["qm.tenantNameLike"] = $("#search-tenant-name").val();
		   this.searchObj["qm.tenantType"] = $("#search-tenant-type").val()=="全部"?"":$("#search-tenant-type").val();
		   this.searchObj["qm.businessType"] = $("#search-enterprise-type").val()=="全部"?"":$("#search-enterprise-type").val(); 
		   this.searchObj["qm.status"] = $("#search-tenant-status").val()=="全部"?"":$("#search-tenant-status").val();
	       this.searchTenantInfo();
	   };
  };