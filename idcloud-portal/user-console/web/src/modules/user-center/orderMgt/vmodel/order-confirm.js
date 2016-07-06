define(['lib/jquery/pintuer',
		'app-utils/httpService',
		'app-utils/$extendService',
        'app-utils/validatorService'
        ], function(pinter,http) {
	
	var orderConfirmInfo = avalon.define({
		$id:'orderconfirm',
		title:"订单确认",
		orderNumber:"",
		data:[],
		totalMoney:0,
		amountMoney:100.00, //用户余额
		usedAmountMoney:0.00, // 余额付款金额
		usedBankMoney:0.00, // 支付宝或网银付款金额
		amountFlag:true, // 是否使用余额支付
		backList: function () {
            $("#orderDg").removeClass("hidden");
            $("#orderDg").addClass("fadein-left");
            $("#orderConfirm").addClass("hidden");
            $("#orderConfirm").removeClass("show");
            $("#hostNav").addClass("show");
            $("#hostNav").removeClass("hidden");
            avalon.vmodels.orderDg.orderconfirm = '';
        },
		// 去支付
		toPay: function(){
			// 订单数据
			console.log("订单数据："+JSON.stringify(orderConfirmInfo.data));
			var list = [];
			for(var i=0;i<orderConfirmInfo.data.length;i++){
				var data = orderConfirmInfo.data[i];
				var orderObj = new Object();
				orderObj.serviceSid = 100;
				orderObj.pid = -1;
				orderObj.specificationDesc ='CPU:'+data.cpu+'核、内存:'+data.memory+'GB、系统盘:'+data.systemDisk+'GB、操作系统:'+data.os+' 、网络:'+data.netName.join("、")+'';
				orderObj.hostPassword = '';
				orderObj.instanceList = [];
				orderObj.instanceList.push({"instanceName": "","hostPassword": ""});
				orderObj.specifications = {};
				orderObj.specifications.dataDisk = [];
				orderObj.specifications.nets = data.netName;
				orderObj.specifications.systemDisk = data.systemDisk;
				orderObj.specifications.cpu = data.cpu;
				orderObj.specifications.memory = data.memory;
				orderObj.specifications.os = data.os;
				orderObj.specifications.keyPair = data.sshkey;
				orderObj.specifications.region = data.areaBig;
				orderObj.specifications.zone = data.areaSmall;
				orderObj.specifications.securityGroup = "UUID";
				orderObj.billingType = "Month";
				orderObj.billingDetail = "1";
				orderObj.moneyPer = data.moneyPer;
				orderObj.mgtObjSid = "1065";
				orderObj.quantity = data.orderCount;
				orderObj.buyLength = data.buyTime;
				list.push(orderObj);
			}

			console.log("提交数据："+JSON.stringify(list));

	        var dataList = orderConfirmInfo.data;
			var _tr = "";
	        if(dataList.length > 0){

	        	for(var i=0;i<dataList.length;i++){
					var str = "";
					if(i == dataList.length-1){
						str = "<tr><td style='padding-top:15px;padding-bottom:15px;font-size:12px;' align='left' valign='middle'>";
					}else{
						str = "<tr><td style='padding-top:15px;font-size:12px;' align='left' valign='middle'>";
					}
					str += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服务名称:云主机服务器，";
					str += "数量:"+dataList[i].orderCount+"，";
	        		str += "配置：CPU:"+dataList[i].cpu+"核、"+
	        			   "内存:"+dataList[i].memory+"GB、"+
	        			   "系统盘:"+dataList[i].systemDisk+"GB、"+
	        			   "操作系统:"+dataList[i].os+"、"+
	        			   "网络类型:"+dataList[i].netType+"</td>";
					if(i == dataList.length-1){
						str += "<td width='100' valign='middle'>￥"+dataList[i].moneyPer+"</td></tr>";
					}else{
						str += "<td width='100' style='padding-top:15px;'valign='middle'>￥"+dataList[i].moneyPer+"</td></tr>";
					}

					_tr += str;
	        	}
	        }

			//$("#orderComfirm").html(_tr);
			//orderConfirmInfo.orderNumber = "121212121212";
			//$("#secondDIV").removeClass("hidden");
			//$("#secondDIV").removeClass("show");
			//$("#firstDIV").addClass("hidden");
			//$("#firstDIV").removeClass("show");
			//$("#confirmtitle").text("支付");

             http.AjaxRequest({
			 	url : "/rest/orders/save",
			 	type : "POST",
			 	params: list,
			 	async : false,
			 	callback : function(data) {
					console.log(JSON.stringify(data));
					$("#orderComfirm").html(_tr);
					orderConfirmInfo.orderNumber = data;
					$("#secondDIV").removeClass("hidden");
					$("#secondDIV").removeClass("show");
					$("#firstDIV").addClass("hidden");
					$("#firstDIV").removeClass("show");
					$("#confirmtitle").text("支付");
			 	}
			 });
			
		},
		// 确认支付
		toConfirmPay: function(){
			http.AjaxRequest({
				url : "/rest/orders/pay/"+orderConfirmInfo.orderNumber,
				type : "POST",
				async : false,
				callback : function(data) {
					if(data){
						$("#secondDIV").addClass("hidden");
			            $("#secondDIV").removeClass("show");
			            $("#thirdDIV").removeClass("hidden");
						$("#thirdDIV").addClass("show");
						$("#thirdDIV").removeClass("show");
			            $("#confirmtitle").text("支付成功");
					}else{
						$("#confirmtitle").text("支付失败");
					}
				}
			});
		},
		inputAmountValue:function(obj){
			// 判断值的类型
			var reg= /^\d+(\.\d{0,5})?$/;
			if(reg.test($(obj).val())){
				if((orderConfirmInfo.amountMoney-orderConfirmInfo.totalMoney) > 0){
					// 余额大于总价
					if(parseFloat($(obj).val()) > orderConfirmInfo.totalMoney){
						orderConfirmInfo.usedAmountMoney =orderConfirmInfo.totalMoney;
						orderConfirmInfo.usedBankMoney = "0.00";
					}else{
						orderConfirmInfo.usedAmountMoney =parseFloat($(obj).val()).toFixed(2);
						orderConfirmInfo.usedBankMoney = (orderConfirmInfo.totalMoney - parseFloat($(obj).val())).toFixed(2);
					}
				}else{
					// 余额小于总价
					if(parseFloat($(obj).val()) > orderConfirmInfo.amountMoney){
						orderConfirmInfo.usedAmountMoney =orderConfirmInfo.amountMoney.toFixed(2);
						orderConfirmInfo.usedBankMoney = (orderConfirmInfo.totalMoney - orderConfirmInfo.amountMoney).toFixed(2);
					}else{
						orderConfirmInfo.usedAmountMoney =parseFloat($(obj).val()).toFixed(2);
						orderConfirmInfo.usedBankMoney = (orderConfirmInfo.totalMoney - parseFloat($(obj).val())).toFixed(2);
					}
				}
			}else{
				orderConfirmInfo.usedAmountMoney = "0.00";
				orderConfirmInfo.usedBankMoney = orderConfirmInfo.totalMoney;
			}
		}

	});

	// 监听余额checkbox
	orderConfirmInfo.$watch("amountFlag", function(a, b) {
		if(a){
		}else{
			orderConfirmInfo.usedBankMoney = (parseFloat(orderConfirmInfo.usedBankMoney)
											+ parseFloat(orderConfirmInfo.usedAmountMoney)).toFixed(2);
			orderConfirmInfo.usedAmountMoney ="0.00";

		}
	});

	//初始grid
	return orderConfirmInfo;
});