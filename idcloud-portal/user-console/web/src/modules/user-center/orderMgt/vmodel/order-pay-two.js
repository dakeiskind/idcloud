define(['app-utils/httpService','app-utils/variableService'], function(http,variable) {
	var payFlowTwo = avalon.define({
		$id: 'payFlowTwo',
		orderId:"",
		payResultFlag:false,
		payResultResult:"",
		orderType:"",
		amountFlag:true, // 是否用余额支付flag
		totalMoney:0, // 总金额
		amountMoney:0, // 余额总金额
		usedAmountMoney:0, // 支付余额的总金额
		usedBankMoney:0, // 其他支付方式支付金额
		createOrderDetailPage:function(data){
            console.log(JSON.stringify(data))
			// 设置订单号
			payFlowTwo.orderId = data.orderId;
			// 设置余额
			payFlowTwo.amountMoney = data.amountMoney;
			payFlowTwo.totalMoney = 0;
			var _tr = "";
            if(data.orderDetails instanceof Array) {
                for (var i = 0; i < data.orderDetails.length; i++) {
                    var dataList = data.orderDetails[i];
					payFlowTwo.orderType=data.orderDetails[0].serviceName;
                    var str = "";
                    if (i == data.orderDetails.length - 1) {
                        str =
                            "<tr><td style='padding-top:15px;padding-bottom:15px;font-size:12px;' align='left' valign='middle'>";
                    } else {
                        str =
                            "<tr><td style='padding-top:15px;font-size:12px;' align='left' valign='middle'>";
                    }
                    str += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服务名称：" + dataList.serviceName + "，";
                    str += "数量:" + dataList.quantity + "，";
                    str += dataList.specificationDesc + "</td>";
                    if (i == data.orderDetails.length - 1) {
                        str +=
                            "<td width='100' valign='middle'>￥" + parseFloat(dataList.amount)
                                .toFixed(2) + "</td></tr>";
                    } else {
                        str +=
                            "<td width='100' style='padding-top:15px;'valign='middle'>￥"
                            + parseFloat(dataList.amount).toFixed(2) + "</td></tr>";
                    }
                    payFlowTwo.totalMoney =
                        (parseFloat(payFlowTwo.totalMoney) + parseFloat(dataList.amount)).toFixed(
                            2);
                    _tr += str;
                }
            }
			var moneyFlag = parseFloat(payFlowTwo.amountMoney)-payFlowTwo.totalMoney;
			if(moneyFlag > 0){
				payFlowTwo.usedAmountMoney = (parseFloat(payFlowTwo.totalMoney)).toFixed(2);
				payFlowTwo.usedBankMoney = "0.00";
			}else{
				payFlowTwo.usedAmountMoney = parseFloat(payFlowTwo.amountMoney).toFixed(2);
				payFlowTwo.usedBankMoney = (payFlowTwo.totalMoney- parseFloat(payFlowTwo.amountMoney)).toFixed(2);
			}
			$("#orderComfirm").html(_tr);
		},
		inputAmountValue:function(obj){ // 监听输入框值的变化
			// 判断值的类型
			var reg= /^\d+(\.\d{0,5})?$/;
			if(reg.test($(obj).val())){
				if((payFlowTwo.amountMoney-payFlowTwo.totalMoney) > 0){
					// 余额大于总价
					if(parseFloat($(obj).val()) > payFlowTwo.totalMoney){
						payFlowTwo.usedAmountMoney =payFlowTwo.totalMoney;
						payFlowTwo.usedBankMoney = "0.00";
					}else{
						payFlowTwo.usedAmountMoney =parseFloat($(obj).val()).toFixed(2);
						payFlowTwo.usedBankMoney = (payFlowTwo.totalMoney - parseFloat($(obj).val())).toFixed(2);
					}
				}else{
					// 余额小于总价
					if(parseFloat($(obj).val()) > payFlowTwo.amountMoney){
						payFlowTwo.usedAmountMoney =payFlowTwo.amountMoney.toFixed(2);
						payFlowTwo.usedBankMoney = (payFlowTwo.totalMoney - payFlowTwo.amountMoney).toFixed(2);
					}else{
						payFlowTwo.usedAmountMoney =parseFloat($(obj).val()).toFixed(2);
						payFlowTwo.usedBankMoney = (payFlowTwo.totalMoney - parseFloat($(obj).val())).toFixed(2);
					}
				}
			}else{
				payFlowTwo.usedAmountMoney = "0.00";
				payFlowTwo.usedBankMoney = payFlowTwo.totalMoney;
			}
		},
		confirmPay:function(){
			payFlowTwo.payResultFlag = false;
			http.AjaxRequest({
				url : "/rest/orders/pay/"+payFlowTwo.orderId,
				type : "POST",
				showMsg:false,
				callback : function(data,message) {
					if(data){
						payFlowTwo.payResultFlag = true;
					}else{
						payFlowTwo.payResultResult = message;
						payFlowTwo.payResultFlag = false;
					}
					avalon.vmodels.orderPayFlow.payResultPage = '';
					avalon.vmodels.orderPayFlow.payResultPage = variable.app_modules + "/user-center/orderMgt/views/order-pay-three.html";
					$("#secondDIV").addClass("hidden");
					$("#secondDIV").removeClass("show");
					$("#thirdDIV").removeClass("hidden");
					$("#thirdDIV").addClass("show");
					$("#thirdDIV").removeClass("show");

				},
				failure:function(){
					//payFlowTwo.payResultFlag = false;
					//avalon.vmodels.orderPayFlow.payResultPage = variable.app_modules + "/user-center/orderMgt/views/order-pay-three.html";
					//$("#secondDIV").addClass("hidden");
					//$("#secondDIV").removeClass("show");
					//$("#thirdDIV").removeClass("hidden");
					//$("#thirdDIV").addClass("show");
					//$("#thirdDIV").removeClass("show");
				}
			});
		}
	});

	// 监听余额checkbox
	payFlowTwo.$watch("amountFlag", function(a, b) {
		if(a){
		}else{
			payFlowTwo.usedBankMoney = (parseFloat(payFlowTwo.usedBankMoney)
			+ parseFloat(payFlowTwo.usedAmountMoney)).toFixed(2);
			payFlowTwo.usedAmountMoney ="0.00";

		}
	});

	return payFlowTwo;
});