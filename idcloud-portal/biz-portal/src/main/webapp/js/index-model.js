var indexConfigModel = function() {
	var me = this;
	this.items = ko.observableArray();
	this.searchObj = {
		configName : "",
		configKey : "",
	};

	// 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	this.initInput = function() {

		// 重置密码组件初始化
		$("#resetPasswordInput").jqxInput({
			placeHolder : "",
			height : 22,
			width : 150,
			minLength : 1,
			theme : currentTheme
		});
		$("#resetPasswordConfirmInput").jqxInput({
			placeHolder : "",
			height : 22,
			width : 150,
			minLength : 1,
			theme : currentTheme
		});
		$("#resetPasswdSave").jqxButton({
			width : '50',
			height : "25",
			theme : currentTheme
		});
		$("#resetPasswdCancel").jqxButton({
			width : '50',
			height : "25",
			theme : currentTheme
		});
		
		// 初始化用户管理组件
        $("#realName-index").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        $("#mobil-index").jqxInput({placeHolder: "", height: 22, width: 150,minLength: 0,maxLength: 32, theme:currentTheme});
        $("#email-index").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        var codeadd = new codeModel({width:150,autoDropDownHeight:true});
		
		$("#indexUserMgtSave").jqxButton({
			width : '50',
			height : "25",
			theme : currentTheme
		});
		$("#indexUserMgtCancel").jqxButton({
			width : '50',
			height : "25",
			theme : currentTheme
		});
	};

	// 初始化弹出window
	this.initPopWindow = function() {
		// 初始化弹出window
		$("#resetPasswdWindow").jqxWindow({
			width : 300,
			height : 170,
			resizable : false,
			isModal : true,
			autoOpen : false,
			theme: currentTheme,
			cancelButton : $("#resetPasswdCancel"),
			modalOpacity : 0.3
		});
		
		// 初始化弹出window
		$("#accountManagementWindow").jqxWindow({
			width : 350,
			height : 200,
			resizable : false,
			isModal : true,
			autoOpen : false,
			theme: currentTheme,
			cancelButton : $("#indexUserMgtCancel"),
			modalOpacity : 0.3
		});
	};
	
	// 初始化验证规则
	this.initValidator = function() {
		// 重置密码
		$('#resetPasswdForm').jqxValidator({
			rules : [ {
				input : '#resetPasswordInput',
				message : '不能为空',
				action : 'keyup, blur',
				rule : 'required'
			}, {
				input : '#resetPasswordInput',
				message : '请输入1-16位密码',
				action : 'keyup, blur',
				rule : 'length=1,16'
			}, {
				input : '#resetPasswordConfirmInput',
				message : '不能为空',
				action : 'keyup, blur',
				rule : 'required'
			}, {
				input : '#resetPasswordConfirmInput',
				message : '密码输入不一致',
				action : 'keyup, focus',
				rule : function(input, commit) {
					// call commit with false, when you are doing server
					// validation and you want to display a validation error on
					// this field.
					if (input.val() === $('#resetPasswordInput').val()) {
						return true;
					}
					return false;
				}
			}, ]
		});
		
		// 账户管理
		$('#accountMgtForm').jqxValidator({
			rules : [ {
				input : '#realName-index',
				message : '不能为空',
				action : 'keyup, blur',
				rule : 'required'
			}, {
				input : '#realName-index',
				message : '请输入1-16位的名称',
				action : 'keyup, blur',
				rule : 'length=1,16'
			},{ 
				input: '#email-index', 
				message: '不能为空', 
				action: 'keyup, blur', 
				rule: 'required' 
			},{ 
				input: '#email-index', 
				message: '请输入正确的邮箱地址', 
				action: 'keyup', 
				rule: 'email' 
			}]
		});

		// 修改密码验证成功
		$('#resetPasswdForm').on('validationSuccess', function(event) {
			var userSid = $("#resetPasswdUserSid").val();
			var newPassword = $("#resetPasswordConfirmInput").val();

			Core.AjaxRequest({
				url : ws_url + "/rest/user/modifyPassword",
				params : {
					userSids : userSid,
					newPassword : newPassword
				},
				callback : function(data) {
					Core.alert({
						title : "提示",
						message : "密码修改成功！",
						callback : function() {
							$("#resetPasswdWindow").jqxWindow('close');
						}
					});
				},
				failure : function(data) {
					Core.alert({
						title : "提示",
						message : "密码修改失败！",
						callback : function() {
							$("#resetPasswdWindow").jqxWindow('close');
						}
					});
				}
			});
		});
		
		
		// 提交新的账户信息
		$('#accountMgtForm').on('validationSuccess', function(event) {
			var user = Core.parseJSON($("#accountMgtForm").serializeJson());
			 Core.AjaxRequest({
	 				url : ws_url + "/rest/user/updateUser",
	 				params :user,
	 				callback : function (data) {
						$("#accountManagementWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
	 			    	$("#accountManagementWindow").jqxWindow('close');
	 			    }
	 			});
		});
	};

};
var indexBindModel = function(index) {

	/** 注销用户 */
	this.logout = function() {
		Core.AjaxRequest({
			url : ws_url + "/rest/user/logout",
			params : {
			},  
			callback : function (data) {
				window.location.href = ctx + "/pages/login.jsp";
            }
		});
	};
 	
	/** 弹出修改用户密码window */
	this.resetPassword = function() {
		if(null != currentUser) {
			$("#reset-passwd-account").html(currentUser.account);
			$("#resetPasswdUserSid").val(currentUser.userSid);
			var windowW = $(window).width(); 
			var windowH = $(window).height(); 
			$("#resetPasswdWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-170)/2 } });
			$("#resetPasswdWindow").jqxWindow('open');
		}
	};
	/** 提交新密码 */
	this.reset_passwd_submit = function() {
		$('#resetPasswdForm').jqxValidator('validate');
	};
	
	/** 弹出用户管理window */
	this.popUserManagementWindow = function() {
		if(null != currentUser) {
			Core.AjaxRequest({
				url : ws_url + "/rest/user/current",
				type : "GET",
				async : false,
				callback : function(data) {
					$("#mgt-usersid-index").val(data.user.userSid);
					$("#realName-index").val(data.user.realName);
					$("#mobil-index").val(data.user.mobile);
					$("#email-index").val(data.user.email);
				},
				failure : function(){
					
				}
			});
			
			var windowW = $(window).width(); 
			var windowH = $(window).height(); 
			$("#accountManagementWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-170)/2 } });
			$("#accountManagementWindow").jqxWindow('open');
		}
	};
	/** 提交新的用户信息 */
	this.submitUserMgtInfo = function() {
		$('#accountMgtForm').jqxValidator('validate');
	};
};
