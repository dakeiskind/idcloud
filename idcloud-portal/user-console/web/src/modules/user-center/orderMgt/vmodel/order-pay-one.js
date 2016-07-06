define(['app-utils/variableService','app-utils/httpService'], function(variable,http) {
	var payFlowOne = avalon.define({
		$id: 'payFlowOne',
		totalMoney:0,
		orderData:{},
		initDatagrid:function(data){
			for(var i=0;i<data.length;i++);
			// 1.从订购页面跳转
			var totalMoney = 0;
			var localData = [];
			if(data[0].title=="云主机"){
				for(var i=0;i<data.length;i++){
					var obj = new Object();
					obj.serverName = "云主机";
					obj.serverConfig = "cpu:"+data[i].cpu+"核"+
									   ",内存："+data[i].memory+"GB"+
									   ",系统盘："+data[i].systemDisk+"GB"+
									   ",操作系统："+data[i].osName;
					obj.serverCount = data[i].orderCount+"台";
					if(data[i].buyTimeType == "Month"){
						obj.serverTime = data[i].buyTime+"个月";
					}else{
						obj.serverTime = data[i].buyTime+"年";
					}

					obj.serverPrice = data[i].moneyPer+"元";
					totalMoney += parseInt(data[i].moneyPer);
					localData.push(obj);
				}
				payFlowOne.totalMoney = parseFloat(totalMoney).toFixed(2);
				// 初始化grid
				$("#orderConfirmGrid").ptGrid({
				  pagesize:4,
				  data:{
					  localData:localData
				  },
				  columns: [
					  { text: '服务名称', datafield: 'serverName',sortable: true},
					  { text: '配置', datafield: 'serverConfig',sortable: true},
					  { text: '数量', datafield: 'serverCount'},
					  { text: '时长', datafield: 'serverTime'},
					  {text:'价格',datafield:'serverPrice'}
				  ]
			  });
			}else if(data[0].title=="弹性公网IP"){
				for(var i=0;i<data.length;i++){
					var obj = new Object();
					obj.serverName = "弹性公网IP";
					obj.serverConfig = "带宽:"+data[i].bandwidth+"Mbps";
					obj.serverCount = data[i].orderCount+"个";
					if(data[i].buyTimeType == "Month"){
						obj.serverTime = data[i].buyTime+"个月";
					}else{
						obj.serverTime = data[i].buyTime+"年";
					}

					obj.serverPrice = data[i].moneyPer+"元";
					totalMoney += parseInt(data[i].moneyPer);
					localData.push(obj);
				}
				payFlowOne.totalMoney = parseFloat(totalMoney).toFixed(2);
				// 初始化grid
				$("#orderConfirmGrid").ptGrid({
				  pagesize:4,
				  data:{
					  localData:localData
				  },
				  columns: [
					  { text: '服务名称', datafield: 'serverName',sortable: true},
					  { text: '配置', datafield: 'serverConfig',sortable: true},
					  { text: '数量', datafield: 'serverCount'},
					  { text: '时长', datafield: 'serverTime'},
					  {text:'价格',datafield:'serverPrice'}
				  ]
			  });
			}else if(data[0].title=="云硬盘"){
				for(var i=0;i<data.length;i++){
					var obj = new Object();
					obj.serverName = "云硬盘";
					obj.serverConfig = "硬盘类型:"+data[i].diskType+
							",大小:"+data[i].diskSize+"GB";
					obj.serverCount = data[i].orderCount+"个";
					if(data[i].buyTimeType == "Month"){
						obj.serverTime = data[i].buyTime+"个月";
					}else{
						obj.serverTime = data[i].buyTime+"年";
					}

					obj.serverPrice = data[i].moneyPer+"元";
					totalMoney += parseInt(data[i].moneyPer);
					localData.push(obj);
				}
				payFlowOne.totalMoney = parseFloat(totalMoney).toFixed(2);
				// 初始化grid
				$("#orderConfirmGrid").ptGrid({
				  pagesize:4,
				  data:{
					  localData:localData
				  },
				  columns: [
					  { text: '服务名称', datafield: 'serverName',sortable: true},
					  { text: '配置', datafield: 'serverConfig',sortable: true},
					  { text: '数量', datafield: 'serverCount'},
					  { text: '时长', datafield: 'serverTime'},
					  {text:'价格',datafield:'serverPrice'}
				  ]
			  });
			}
            payFlowOne.orderData=data;
		},
		backPreStep:function(){
			$("#orderDg").removeClass("hidden");
			$("#orderDg").addClass("fadein-left");
			$("#hostNav").removeClass("hidden");
			$("#hostNav").addClass("fadein-left");
			$("#orderPayFlowContainer").addClass("hidden");
			$("#orderPayFlowContainer").removeClass("show");
		},
		gotoNextStep:function(){

			// 提交订单数据，生成orderId
			var list = payFlowOne.createOrderData(payFlowOne.orderData);

			var dataList = payFlowOne.orderData;
			var obj = new Object();
            console.log(JSON.stringify(dataList))
			obj.orderDetails = [];
			for(var i=0;i<dataList.length;i++){
				if(dataList[0].orderType =="cs"){
					var subObj = new Object();
					subObj.serviceName = "云主机服务";
					subObj.amount = dataList[i].moneyPer;
					subObj.billingTypeName = "按月计费";
					subObj.billingTypeYmName = "按月";
					subObj.expectedTime =  null;
					subObj.expiringDate =  null;
					subObj.quantity = dataList[i].orderCount+"台";
					subObj.specificationDesc =  "CPU:"+dataList[i].cpu+"核、"+
							"内存:"+dataList[i].memory+"GB、"+
							"系统盘:"+dataList[i].systemDisk+"GB、"+
							"操作系统:"+dataList[i].osName+"、"+
							"网络:"+dataList[i].vpcName;
					subObj.purchaseLongTime = dataList[i].buyTime+"月";
					obj.orderDetails.push(subObj);
				}else if(dataList[0].orderType =="eip"){
					var subObj = new Object();
					subObj.serviceName = "弹性公网IP服务";
					subObj.amount = dataList[i].moneyPer;
					subObj.billingTypeName = "按月计费";
					subObj.billingTypeYmName = "按月";
					subObj.expectedTime =  null;
					subObj.expiringDate =  null;
					subObj.quantity = dataList[i].orderCount+"个";
					subObj.specificationDesc =  "带宽:"+dataList[i].bandwidth+"Mbps";
					subObj.purchaseLongTime = dataList[i].buyTime+"月";
					obj.orderDetails.push(subObj);
				}else if(dataList[0].orderType =="cbs"){
                    var subObj = new Object();
                    subObj.serviceName = "云硬盘服务";
                    subObj.amount = dataList[i].moneyPer;
                    subObj.billingTypeName = "按月计费";
                    subObj.billingTypeYmName = "按月";
                    subObj.expectedTime =  null;
                    subObj.expiringDate =  null;
                    subObj.quantity = dataList[i].orderCount+"个";
                    subObj.specificationDesc =  "硬盘类型:"+dataList[i].diskType+
                                                ",大小:"+dataList[i].diskSize+"GB";
                    subObj.purchaseLongTime = dataList[i].buyTime+"月";
                    obj.orderDetails.push(subObj);
                }
			}

			http.AjaxRequest({
				url : "/rest/orders/save",
				type : "POST",
				params: list,
				showMsg: false,
				callback : function(data) {
					obj.orderId = data;
						http.AjaxRequest({
						url : "/rest/billingAccount/display/account/"+variable.currentUser.userSid+"",
						type : "GET",
						async:false,
						showMsg:false,
						callback : function(data) {
							obj.amountMoney = parseFloat(data).toFixed(2);
							payFlowOne.orderData = obj;

							avalon.vmodels.orderPayFlow.payPage="";
							avalon.vmodels.orderPayFlow.payPage = variable.app_modules + "/user-center/orderMgt/views/order-pay-two.html";
							$("#secondDIV").removeClass("hidden");
							$("#secondDIV").addClass("show");
							$("#firstDIV").addClass("hidden");
							$("#firstDIV").removeClass("show");
						},
						failure: function(){

							$("#firstDIV").addClass("hidden");
							$("#firstDIV").removeClass("show");
							$("#orderDg").removeClass("hidden");
							$("#orderDg").addClass("fadein-left");
						}
					});
				}
			});



		},
		createOrderData:function(orderData){
			var list = [];
			for(var i=0;i<orderData.length;i++){
				var data = orderData[i];
				var orderObj = new Object();
				orderObj.serviceCode = data.orderType;
				orderObj.data = {
					"billingType": data.buyTimeType,
					"duration": data.buyTime,
					"orderType": "01",
					"quantity": data.orderCount
				};
                if(orderData[0].orderType =="cs"){
                    // 获取数据盘
                    var dataDisk = [];
                    for(var j=0;j<data.dataDisk.length;j++){
                        var obj = new Object();
                        obj.dataDiskCategory =  "cloud_ssd";
                        obj.dataDiskSize = data.dataDisk[j];
                        obj.dataDiskSnapshot = "";
                        obj.dataDiskDevice = "";
                        obj.dataDiskDeletewithinstance = "true";
                        obj.dataDiskInstanceId = null
                        dataDisk.push(obj);
                    }
                    orderObj.specifications = {
                        "region": data.regionSid,
                        "zone": data.zoneSid,
                        "password": "",
                        "hostname": "test",
                        "instance": [
                            {
                                "instanceCategory": data.hostType,
                                "cpu": data.cpu,
                                "memory": data.memory
                            }
                        ],
                        "systemDisk": [
                            {
                                "systemDiskCategory": "cloud_efficiency",
                                "systemDiskSize": data.systemDisk,
                                "systemDiskDevice": "/dev/xvda"
                            }
                        ],
                        "dataDisk": dataDisk,
                        "networkType": "vpc", // 网络类型
                        "networks": data.subNetwork, // 子网
                        "bandwidth": "0", // 带宽
                        "os": {
							"imageType": data.isoType,
							"imageId": data.os
						},
                        "keyPair": data.sshkey,
                        "securityGroup": "ccd98605-77ba-11e5-b6e5-005056a52fbf"

                    };
					orderObj.specificationsDesc =  "CPU:"+data.cpu+"核、内存:"+data.memory+"GB、"+
												   "操作系统:"+data.osName;
                }else if(orderData[0].orderType =="eip"){
                    orderObj.specifications={
                        "region": data.regionSid,
                        "zone": data.zoneSid,
                        "eip_bandwidth":data.bandwidth
                    };
					orderObj.specificationsDesc =  "带宽:"+data.bandwidth+"GB";
                }else if(orderData[0].orderType =="cbs"){
                    orderObj.specifications={
                        "region": data.regionSid,
                        "zone": data.zoneSid,
                        "dataDisk":[
                            {
                                "dataDiskCategory":data.diskType,
                                "dataDiskSize":data.diskSize
                            }
                        ]
                    };
                    orderObj.specificationsDesc =  "XXXX";
                }
				list.push(orderObj);
			}
			return list;
		},
	});
	//初始grid
	return payFlowOne;
});