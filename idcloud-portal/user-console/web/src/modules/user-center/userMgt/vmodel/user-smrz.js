
define(['lib/jquery/pintuer','app-utils/messageBoxService','app-utils/validatorService','validator'], function  (pintuer,messageBox,validate) {
	
	var userSmrz = avalon.define({
		$id:'userSmrz',
		title:"实名认证",
        commitu:function(){
            if($("#usmrzFrom").valid()){
                messageBox.confirm({
                    message:"您确定要提交么？",
                    callback:function(){
                        alert("确定提交!");
                    }
                });
            }
        },
		commitc:function() {
			if ($("#csmrzForm").valid()) {
				messageBox.confirm({
					message: "您确定要提交么？",
					callback: function () {
						alert("确定提交!");
					}
				});
			}
		}
	});

	var change=function(){
		$("#suser").click(function(){
			$("#susermodle").removeClass("hidden");
			$("#susermodle").addClass("show");
			$("#cusermodle").removeClass("show");
			$("#cusermodle").addClass("hidden");
		});
		$("#cuser").click(function(){
			$("#susermodle").removeClass("show");
			$("#susermodle").addClass("hidden");
			$("#cusermodle").removeClass("hidden");
			$("#cusermodle").addClass("show");
		});
		$("#smrztj").click(function(){
			$("#ajaxForm").ajaxSubmit(function() {
			$("#smrzwtj").addClass("hidden");
			$("#tjsmrz").removeClass("hidden");
			$("#tjsmrz").addClass("show");
			});
		});
		$("#csmrztj").click(function(){
			$("#cxmrz").ajaxSubmit(function() {
				$("#cusermodle").addClass("hidden");
				$("#usertypec").addClass("hidden");
				$("#ctjsmrz").removeClass("hidden");
				$("#ctjsmrz").addClass("show");
			});
		});
	};
	var preview = function(){
		$("#loadimg").change(function(){
			var objUrl = getObjectURL(this.files[0]) ;
			console.log("objUrl = "+objUrl) ;
			if (objUrl) {
				$("#img0").attr("src", objUrl) ;
			}
		}) ;
	};
	var getObjectURL = function(file){
		var url = null ;
		if (window.createObjectURL!=undefined) { // basic
			url = window.createObjectURL(file) ;
		} else if (window.URL!=undefined) { // mozilla(firefox)
			url = window.URL.createObjectURL(file) ;
		} else if (window.webkitURL!=undefined) { // webkit or chrome
			url = window.webkitURL.createObjectURL(file) ;
		}
		return url ;
	};
	var validator = function () {
		$("#usmrzFrom").validate({
			onkeyup: function(element) {
				$(element).valid();
			},
			rules: {
                uidnum: {required: true,idCardNumber:true},
				loadimg:{required: true}
			},
			messages: {
                uidnum: {
					required:"不能为空",
				},
				loadimg:{
					required:"不能为空",
				}
			},
			submitHandler:function(form){
				alert("submitted");
			}
		});
		$("#csmrzForm").validate({
			onkeyup: function(element) {
				$(element).valid();
			},
			rules: {
				idnum: {required: true,idCardNumber:true},
				busnum:{required: true, minlength: 15},
				cloadimg:{required: true},
				cloadimgc:{required: true}
			},
			messages: {
				idnum: {
					required:"不能为空",
				},
				busnum:{
					required:"不能为空",
					minlength:"请输入正确的执照"
				},
				cloadimg:{
					required:"请上传文件"
				},
				cloadimgc:{
					required:"请上传文件"
				}
			},
			submitHandler:function(form){
				alert("submitted");
			}
		});
	};
	return avalon.controller(function ($ctrl) {
		  	
		    $ctrl.$onEnter = function (param, rs, rj) {
		    	// 进入视图
				avalon.vmodels.userContainer.secondNavFlag = "account"
		    	avalon.vmodels.userContainer.navSelectedFlag = 'user.account-auth';
		    };
	    
		    $ctrl.$onRendered = function () {
		    	// 视图渲染后，意思是avalon.scan完成
                pintuer.init();
				change();
                validator();
		    };
	 
		    $ctrl.$onBeforeUnload = function () {
		    	 // 视图销毁前
		    };
	    	$ctrl.$vmodels = [userSmrz];
   		});
	
});