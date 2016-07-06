var tenantProjectWindowModel = function () {
	 var me = this;
	 this.items = ko.observableArray();
	 // 判断新增用户名是否重复 
	 this.checkAccountIsRepeat = function(val){
		 var isOk = false;
		 Core.AjaxRequest({
				url :ws_url + "/rest/user/validateUserAccount?account=" + val ,
				type:"get",
				async:false,
				callback : function (data) {
					if(data.status == "success"){ 
						isOk = true;
				    }else{
				    	isOk =false;
				    }
	            }
          });
		 
		 return isOk;
	 };
	 // 验证新增用户
	 this.initValidator = function(){
	    	$('#addTenantProUserForm').jqxValidator({
               rules: [
		                  { input: '#add-user-pro-account', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-user-pro-account', message: '用户账号不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
		                  { input: '#add-user-pro-account', message: '请输入英文或数字', action: 'keyup, blur', rule: function (input, commit) {
		                	  if(/[\u4E00-\u9FA5]/g.test(input.val())){
                	  				return false;
                	  			}else{
                	  				return true;
                	  			}
                          	}
                          },
                          { input: '#add-user-pro-account', message: '用户账号重复，请重新输入', action: 'keyup, blur', rule: function (input, commit) {
                              if (me.checkAccountIsRepeat(input.val())) {
                                  return true;
                              }
                              return false;
                          	}
                          },
                       
                          { input: '#add-user-pro-realName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-user-pro-realName', message: '用户名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,256' },
                          
                          { input: '#add-user-pro-password', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-user-pro-password', message: '密码不能超过16位', action: 'keyup, blur', rule: 'length=1,16' },
                          
                          { input: '#add-user-pro-emaile', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-user-pro-emaile', message: '请输入正确的邮箱格式', action: 'keyup, blur', rule: 'email' },
                       
                     ]
	    	});
	    	
	    	$('#editTenantProUserForm').jqxValidator({
	               rules: [
			                  { input: '#edit-user-pro-account', message: '不能为空', action: 'keyup, blur', rule: 'required' },
			                  { input: '#edit-user-pro-account', message: '用户账号不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
			                  { input: '#edit-user-pro-account', message: '请输入英文或数字', action: 'keyup, blur', rule: function (input, commit) {
			                	  if(/[\u4E00-\u9FA5]/g.test(input.val())){
	                	  				return false;
	                	  			}else{
	                	  				return true;
	                	  			}
	                          	}
	                          },
	                          { input: '#edit-user-pro-account', message: '用户账号重复，请重新输入', action: 'keyup, blur', rule: function (input, commit) {
	                        	  var rowindex = $('#tenantUserPlatformDatagrid').jqxGrid('getselectedrowindex');
	                        	  var data = $('#tenantUserPlatformDatagrid').jqxGrid('getrowdata', rowindex);
	                        	  if(data.account == input.val()){
	                        		  return true;
	                        	  }else{
	                        		  if (me.checkAccountIsRepeat(input.val())) {
		                                  return true;
		                              }else{
		                            	  return false;
		                              }
	                        	  }
	                        	 
	                          	}
	                          },
	                       
	                          { input: '#edit-user-pro-realName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                          { input: '#edit-user-pro-realName', message: '用户名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,256' },
	                          
	                          { input: '#edit-user-pro-emaile', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                          { input: '#edit-user-pro-emaile', message: '请输入正确的邮箱格式', action: 'keyup, blur', rule: 'email' },
	                       
	                     ]
		    	});
	    	
	    	$('#changePasswordProForm').jqxValidator({
	               rules: [
			                  { input: '#change-new-pro-password', message: '不能为空', action: 'keyup, blur', rule: 'required' },
			                  { input: '#change-new-pro-password', message: '密码不能超过16个字符', action: 'keyup, blur', rule: 'length=1,16' },
			                  
			                  { input: '#change-confirm-pro-password', message: '不能为空', action: 'keyup, blur', rule: 'required' },
			                  { input: '#change-confirm-pro-password', message: '密码不能超过16个字符', action: 'keyup, blur', rule: 'length=1,16' },
	                          { input: '#change-confirm-pro-password', message: '密码输入不一致，请重新输入', action: 'keyup, blur', rule: function (input, commit) {
	                        	  if($("#change-new-pro-password").val() == input.val()){
	                        		  return true;
	                        	  }else{
	                        		  return false;
	                        	  }
	                        	 
	                          	}
	                          },
	                     ]
		    	});
	    	
	    	// 新增用户表单验证成功
	    	$('#addTenantProUserForm').on('validationSuccess', function (event) {
		    		 var user = Core.parseJSON($("#addTenantProUserForm").serializeJson());
		    		 //console.log(JSON.stringify(user));
		    		 user.mgtObjSid = currentUser.mgtObjSid;
					 Core.AjaxRequest({
							url : ws_url + "/rest/user/insertUser",
							params : user,
							callback : function (data) {
								if(data.status == "success"){
									// 关闭window弹出框
									$('#addTenantProUserWindow').jqxWindow('close');
									Core.alert({title:"提示",message:"新增成功！"});
									var tenants = new tenantPlatformModel();
									tenants.searchRerInfo();
								}else{
									Core.alert({title:"提示",message:"新增失败！"});
								}
						    }
				    });
	    	 });
	    	
	    	// 编辑用户表单验证成功
	    	$('#editTenantProUserForm').on('validationSuccess', function (event) {
	    		  var user = Core.parseJSON($("#editTenantProUserForm").serializeJson());
	    		  Core.AjaxRequest({
						url : ws_url + "/rest/user/updateUser",
						params : user,
						callback : function (data) {
							    $('#editTenantProUserWindow').jqxWindow('close');
							    var tenants = new tenantPlatformModel();
								tenants.searchRerInfo();
						}
				   });
	    	 });
	    	
	    	// 修改密码表单验证成功
	    	$('#changePasswordProForm').on('validationSuccess', function (event) {
	    		  var data = Core.parseJSON($("#changePasswordProForm").serializeJson());
	    		  Core.AjaxRequest({
						url : ws_url + "/rest/user/modifyPassword",
						params:data,
						callback : function (data) {
							Core.alert({title:"提示",message:"修改密码成功!"});
							$('#changePasswordProWindow').jqxWindow('close');
						},
						failure:function(data){
						   Core.alert({title:"提示",message:"修改密码失败!"});
						   $('#changePasswordWindow').jqxWindow('close');
						}
				  });
	    	 });
	    	
	    };
	
	    // 初始化弹出window
	    this.initPopWindow = function(){
	         $("#addTenantProUserWindow").jqxWindow({
	                width: 500, 
	                height:200,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#add-user-pro-button-cancel"),
	                theme: currentTheme,
	                modalOpacity: 0.3,
	                initContent: function() {
		                  $("#add-user-pro-account").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
		      	    	  $("#add-user-pro-realName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
		      	    	  $("#add-user-pro-password").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1 ,theme:currentTheme, showStrengthPosition: "right"});
		      	    	  $("#add-user-pro-mobile").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
		      	    	  $("#add-user-pro-emaile").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
		      	    	  //$("#add-project-pro-mgtObjSid").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
		      	    	  
		      	    	  $("#add-user-pro-button-save").jqxButton({ width: '60px',height:"25px",theme:currentTheme});
		      	    	  $("#add-user-pro-button-cancel").jqxButton({ width: '60px',height:"25px",theme:currentTheme});
		      	    	var condition = {};
		    			var params = {
		    	  			condition: condition
		    	  	  	};
		    	  	    condition['userSid'] = currentUser.userSid;
//		      	    	Core.AjaxRequest({
//		      	    		url : ws_url + "/rest/mgtObj/findByParams",
//		    				params : params,
//		    				type:'post',
//		    				async:false,
//		    				callback : function(data) {
//		    					var dataList = new Array;
//		    					for ( var i = 0; i < data.length; i++) {
//		    						if(data[i].mgtObjSid!=null){
//		    							dataList.push(data[i]);
//		    						}
//		    					}
//		    					$("#add-project-mgtObjSid").jqxDropDownList({
//		  		  	        	  height: 22,
//		  		  	        	  width: 150,
//		  		  	        	  dropDownHeight : 100,
//		  		  	        	  dropDownWidth: 150,
//		  		  	        	  theme:currentTheme,
//		  		  	              displayMember: 'projectName', 
//		  		                  valueMember: 'mgtObjSid',
//		  		                  selectedIndex: 0,
//		  		                  source:dataList,
//		  		                  placeHolder:"",
//		  		                  autoDropDownHeight: true 
//		  		  			  });
//		    				}
//		      	    	});
	                }  
	         
	         
             });
	         
	         $("#editTenantProUserWindow").jqxWindow({
	        	 	width: 500, 
	                height:180,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#edit-user-pro-button-cancel"),
	                theme: currentTheme,
	                modalOpacity: 0.3,
	                initContent: function() {
		                  $("#edit-user-pro-account").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
		      	    	  $("#edit-user-pro-realName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
		      	    	  $("#edit-user-pro-mobile").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
		      	    	  $("#edit-user-pro-emaile").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
		      	    	  
		      	    	  $("#edit-user-pro-button-save").jqxButton({ width: '50px',height:"25px",theme:currentTheme});
		      	    	  $("#edit-user-pro-button-cancel").jqxButton({ width: '50px',height:"25px",theme:currentTheme});
			      	    	var codesearch = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth:150});
			       			codesearch.getCommonCode("edit-user-pro-status","USER_STATUS",false);
		      	    	var condition = {};
		    			var params = {
		    	  			condition: condition
		    	  	  	};
//		    	  	    condition['userSid'] = currentUser.userSid;
//		      	    	Core.AjaxRequest({
//		      	    		url : ws_url + "/rest/mgtObj/findByParams",
//		    				params : params,
//		    				type:'post',
//		    				async:false,
//		    				callback : function(data) {
//		    					var dataList = new Array;
//		    					for ( var i = 0; i < data.length; i++) {
//		    						if(data[i].mgtObjSid!=null){
//		    							dataList.push(data[i]);
//		    						}
//		    					}
//		    					$("#edit-project-mgtObjSid").jqxDropDownList({
//		  		  	        	  height: 22,
//		  		  	        	  width: 150,
//		  		  	        	  dropDownHeight : 100,
//		  		  	        	  dropDownWidth: 150,
//		  		  	        	  theme:currentTheme,
//		  		  	              displayMember: 'projectName', 
//		  		                  valueMember: 'mgtObjSid',
//		  		                  selectedIndex: 0,
//		  		                  source:dataList,
//		  		                  placeHolder:"",
//		  		                  autoDropDownHeight: true 
//		  		  			  });
//		    				}
//		      	    	});
	                }          
	         });
	         
	         $("#changePasswordProWindow").jqxWindow({
	        	 	width: 250, 
	                height:150,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#change-passwd-pro-button-cancel"),
	                theme: currentTheme,
	                modalOpacity: 0.3,
	                initContent: function() {
		                  $("#change-new-pro-password").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1 ,theme:currentTheme, showStrengthPosition: "right"});
		      	    	  $("#change-confirm-pro-password").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1 ,theme:currentTheme, showStrengthPosition: "right"});
		      	    	  
		      	    	  $("#change-passwd-pro-button-save").jqxButton({ width: '50px',height:"25px",theme:currentTheme});
		      	    	  $("#change-passwd-pro-button-cancel").jqxButton({ width: '50px',height:"25px",theme:currentTheme});
	                }          
	         });
	    };
  };
  
  // 弹出新增用户窗口
  function popAddProUserWindow(){
	  // 清空输入框,初始化数据
	  $("#add-user-pro-account").val("");
  	  $("#add-user-pro-realName").val("");
  	  $("#add-user-pro-password").val("");
  	  $("#add-user-pro-mobile").val("");
  	  $("#add-user-pro-emaile").val("");
  	  $("#add-user-pro-projectName").val(mgtObjSid);
	  $("#addTenantProUserWindow").jqxWindow("open");
  }
  
  // 弹出编辑用户窗口
  function popEditProUserWindow(){
	  var rowindex = $('#tenantUserPlatformDatagrid').jqxGrid('getselectedrowindex');
	  var ok = $("#jqxProEdit").jqxButton("disabled");
	  if(rowindex >=0 && !ok){
		  var data = $('#tenantUserPlatformDatagrid').jqxGrid('getrowdata', rowindex);
		  // 清空输入框,初始化数据
		  $("#edit-user-pro-userSid").val(data.userSid);
		  $("#edit-user-pro-account").val(data.account);
		  $("#edit-user-pro-realName").val(data.realName);
    	  $("#edit-user-pro-mobile").val(data.mobile);
    	  $("#edit-user-pro-emaile").val(data.email);
    	  $("#edit-user-pro-status").val(data.status);
    	  $("#edit-project-pro-mgtObjSid").val(data.userMgtObj);
		  
		  $("#editTenantProUserWindow").jqxWindow("open");
	  }
  }
  
  //弹出修改密码窗口
  function popChangePasswdProWindow(){
	  var rowindex = $('#tenantUserPlatformDatagrid').jqxGrid('getselectedrowindex');
	  var ok = $("#jqxProChangePasswd").jqxButton("disabled");
	  if(rowindex >=0 && !ok){
		  var data = $('#tenantUserPlatformDatagrid').jqxGrid('getrowdata', rowindex);
		  // 清空输入框,初始化数据
		  $("#change-user-pro-account").html(data.account);
		  $("#change-new-pro-password").val("");
	  	  $("#change-confirm-pro-password").val("");
	  	  $("#change-user-pro-userSid").val(data.userSid);
		  $("#changePasswordProWindow").jqxWindow("open");
	  }
  }
  
  //弹出新增问题窗口
  function submitTenantProUserInfo(){
	  $('#addTenantProUserForm').jqxValidator('validate');
  }

  //弹出编辑问题窗口
  function submitEditTenantUserInfo(){
	  $('#editTenantProUserForm').jqxValidator('validate');
  }
  
   // 提交用户重置密码信息
  function submitChangeUserProPasswdInfo(){
	  $('#changePasswordProForm').jqxValidator('validate');
  }

 