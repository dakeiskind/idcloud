define(['app-modules/user-center/dashboard/service/dashboard',
	'lib/jquery/pintuer',
	'app-utils/jqx/window',
	'app-utils/variableService',
	'app-utils/messageBoxService',
	'app-utils/httpService',
	'app-utils/validatorService',
	'validator'], function  (dashboardService,pintuer,window,variable,messageBox,http,validate) {
	var data = dashboardService.getData();
	var userCz = avalon.define({
		$id:'userCz',
		title:"充值",
		data:data,
		chargeAmount:{
			alipay:null,
			unionPay:null,
			giftCard:null
		},
		clickAliPay:function(){
			$("#amount").next().remove();
			$("#amount").removeClass("border-red");
			userCz.chargeAmount.alipay = $("#amount").val();
			if(userCz.chargeAmount.alipay === "" || userCz.chargeAmount.alipay === null) {

				$("#amount").addClass("border-red");
				$("#amount").parent().append('<div class="text-red" style="position: absolute;margin-left:80px;"><ul  style="list-style-type:none;"><li>请输入充值金额</li></ul></div>');
				return;
			}
			if (isNaN(userCz.chargeAmount.alipay) || userCz.chargeAmount.alipay <= 0){
				$("#amount").addClass("border-red");
				$("#amount").parent().append('<div class="text-red" style="position: absolute;margin-left:80px;"><ul  style="list-style-type:none;"><li></li>充值金额必须是大于0的整数或者两位以内的小数</ul></div>');
				return;
			}
			if(!/^\d{1,10}(\.\d{1,2})?$/.test(userCz.chargeAmount.alipay)){
				$("#amount").addClass("border-red");
				$("#amount").parent().append('<div class="text-red" style="position: absolute;margin-left:80px;"><ul  style="list-style-type:none;"><li></li>金额最多10位，小数点后2位</ul></div>');
				return;
			}
			$("#amount").removeClass("border-red");
			$("#amount").next().remove();
			messageBox.confirm({
				message:"您确定要充值吗？",
				callback:function(){
					http.AjaxRequest({
						url : "/rest/billingAccount/deposits/"+ $.cookie('USER_SID')+"/"+userCz.chargeAmount.alipay,
						type: "POST",
						async:false,
						callback : function (data) {
							$("#amount").val("");
							messageBox.msgNotification({
								type:"info",
								message:"充值成功(跳转支付宝页面)!"
							});
						}

					});
				}
			});
		},
		clickBank:function(){
			$("#amountBank").next().remove();
			$("#amountBank").removeClass("border-red");
			userCz.data.amountBank = $("#amountBank").val();
			if(userCz.data.amountBank === "" || userCz.data.amountBank === null){
				$("#amountBank").addClass("border-red");
				$("#amountBank").parent().append('<div class="text-red" style="position: absolute;margin-left:80px;"><ul style="list-style-type:none;"><li>请输入金额</li></ul></div>');
				return;
			}
			if (isNaN(userCz.data.amountBank) || userCz.data.amountBank <= 0){
				$("#amountBank").addClass("border-red");
				$("#amountBank").parent().append('<div class="text-red" style="position: absolute;margin-left:80px;"><ul style="list-style-type:none;"><li>充值金额必须是大于0的整数或者两位以内的小数</li></ul></div>');
				return;
			}
			if(!/^\d{1,10}(\.\d{1,2})?$/.test(userCz.chargeAmount.alipay)){
				$("#amountBank").addClass("border-red");
				$("#amountBank").parent().append('<div class="text-red" style="position: absolute;margin-left:80px;"><ul  style="list-style-type:none;"><li></li>金额最多10位，小数点后2位</ul></div>');
				return;
			}
			$("#amountBank").removeClass("border-red");
			$("#amountBank").next().remove();
			messageBox.confirm({
				message:"您确定要充值吗？",
				callback:function(){
					messageBox.msgNotification({
						type:"info",
						message:"跳转网银页面!"
					});
				}
			});
		},
		clickGift:function(){
			$("#giftNum").next().remove();
			$("#giftNum").removeClass("border-red");
			$("#giftPwd").next().remove();
			$("#giftPwd").removeClass("border-red");
			var giftNum = $("#giftNum").val();
			var giftPwd = $("#giftPwd").val();
			if(giftNum === "" || giftNum === null){
				$("#giftNum").addClass("border-red");
				$("#giftNum").parent().append('<div class="text-red" style="position: absolute;margin-left:96px;"><ul><li>输入错误</li></ul></div>');
				return;
			}
			if(giftPwd === "" || giftPwd === null){
				$("#giftPwd").addClass("border-red");
				$("#giftPwd").parent().append('<div class="text-red" style="position: absolute;margin-left:96px;"><ul><li>输入错误</li></ul></div>');
				return;
			}
			$("#giftNum").removeClass("border-red");
			$("#giftNum").next().remove();
			$("#giftPwd").removeClass("border-red");
			$("#giftPwd").next().remove();
			messageBox.confirm({
				message:"您确定使用礼品卡充值吗？",
				callback:function(){
					messageBox.msgNotification({
						type:"success",
						message:"充值成功!"
					});
				}
			});
		}
	});

	var focusFunc = function(){
		$("#amount").focus(function(){
			$("#amount").next().remove();
			$("#amount").removeClass("border-red")
		});

		$("#amountBank").focus(function(){
			$("#amountBank").next().remove();
			$("#amountBank").removeClass("border-red")
		});

		$("#giftNum").focus(function(){
			$("#giftNum").next().remove();
			$("#giftNum").removeClass("border-red")
		});

		$("#giftPwd").focus(function(){
			$("#giftPwd").next().remove();
			$("#giftPwd").removeClass("border-red")
		});
	}

	return avalon.controller(function  ($ctrl) {
		$ctrl.$onEnter = function(param, rs, rj){
			//进入视图
			avalon.vmodels.userContainer.secondNavFlag = "expense"
			avalon.vmodels.userContainer.navSelectedFlag = 'user.expense-recharge';
		};

		$ctrl.$onRendered = function () {
		    pintuer.init();
			focusFunc();
			//userCz.balance = avalon.vmodels.dashbaord.data.user.balance;
		};

		$ctrl.$onBeforeUnload = function () {
		    	 // 视图销毁前
		    };

		$ctrl.$vmodels = [userCz];
		
	});
});
