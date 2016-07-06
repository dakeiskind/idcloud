define(['app-utils/validatorService','validator'], function(validate) {
	
	var testValidator = avalon.define({
		$id:'testValidator',
		init:function(){
			$("#signupForm").validate({
	    		onkeyup: function(element) {
				 	$(element).valid(); 
				},
				rules: {
					username: {required: true,maxlength: 5},
					zipCode:{required:true,isZipCode:true},
					agree:"required",
					gender_male:"required",
					email: {required: true,email: true},
					password: {required: true,maxlength: 5},
					confirm_password: {required: true,maxlength: 5,equalTo: "#password"}
				},
				messages: {
					username: {
						required:"不能为空",
						maxlength: '字段不能超过5个字符'
					},
					gender_male:{
						required:"请选择一项"
					},
					email: {
						required:"不能为空",
						email: '请输入正确的邮箱地址'
					},
					zipCode:{
						required:"不能为空",
						isZipCode: '请正确填写您的邮政编码'
					},
					password: {
						required:"不能为空",
						maxlength: "字段不能超过5个字符"
					},
					confirm_password: {
						required:"不能为空",
						maxlength: "字段不能超过5个字符",
						equalTo: "密码不相同"
					}
				},
				submitHandler:function(form){
		            alert("submitted");
		        } 
			});
		}
		
	});

	return avalon.controller(function ($ctrl) {
		  	
		    $ctrl.$onEnter = function (param, rs, rj) {
		    	// 进入视图
		    	avalon.vmodels.userContainer.navSelectedFlag = 'user.demo-validator';
		    };
	    
		    $ctrl.$onRendered = function () {
		    	testValidator.init();

		    };
	 
		    $ctrl.$onBeforeUnload = function () {
		    	 // 视图销毁前
		    };
	    	$ctrl.$vmodels = [testValidator];
   		});
	
});