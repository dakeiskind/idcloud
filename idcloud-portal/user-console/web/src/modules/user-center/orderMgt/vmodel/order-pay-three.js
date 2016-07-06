define([''], function() {
	var payFlowThree = avalon.define({
		$id: 'payFlowThree',
		resultTitle: "",
		resultSubTitle:"",
		resultContent:"",
		orderType:"",
		viewOrderList:function(){
			var urlPath = window.location.href.split("#!")[1];
			if("/console/cs/host" == urlPath){
				avalon.router.go("user.expense-order");
			}else if("/user/expense/order" == urlPath){
				window.location.reload();
			}else if("/console/eip/home" == urlPath){
                avalon.router.go("user.expense-order");
            }
		},
		viewVmList:function(){
			var urlPath = window.location.href.split("#!")[1];
			if("/console/cs/host" == urlPath){
				window.location.reload();
			}else if("/user/expense/order" == urlPath){
				avalon.router.go("console.cs-host");
			}
		},
		 viewEipList:function(){
			 var urlPath = window.location.href.split("#!")[1];
			 if("/console/eip/home" == urlPath){
				 window.location.reload();
			 }else if("/user/expense/order" == urlPath){
				 avalon.router.go("console.eip-home");
			 }
		 },
		 viewCbsList:function(){
			 var urlPath = window.location.href.split("#!")[1];
			 if("/console/cs/cbs" == urlPath){
				 window.location.reload();
			 }else if("/user/expense/order" == urlPath){
				 avalon.router.go("console.cs-cbs");
			 }
		 },
		createResultPage:function(bol,msg){
			if(payFlowThree.orderType == "云主机服务"){
			if(bol){
				// 支付成功
				payFlowThree.resultTitle = "支付成功";
				var str = '<div class="step-bar  bg-green" style="width: 33%;">'+
					  	'<span class="step-point bg-green icon-check"></span><span class="step-text">确订订单</span>'+
					  '</div>'+
					  '<div class="step-bar  bg-green" style="width: 33%;">'+
					  	'<span class="step-point bg-green icon-check"></span><span class="step-text">支付</span>'+
					  '</div>'+
					  '<div class="step-bar  bg-green" style="width: 34%;">'+
						'<span class="step-point bg-green icon-check"></span><span class="step-text">支付成功</span>'+
					  '</div>';
				payFlowThree.resultSubTitle = str;

				var str2 = '<div class="text-center text-big">恭喜,支付成功!</div>'+
						   '<div class="text-center margin">您购买的云主机正在努力创建中，一般需要1-5分钟，请你耐心等待。</div>'+
						   '<div class="text-center  margin">'+
							'<span class="text-blue text-small" style="cursor: pointer" ms-click="viewOrderList()">查看订单记录</span>'+
							'&nbsp;|&nbsp;'+
							'<span class="text-blue text-small" style="cursor: pointer" ms-click="viewVmList()">管理控制台</span>'+
						   '</div>';

				payFlowThree.resultContent = str2;
			}else{
				// 支付失败
				payFlowThree.resultTitle = "支付失败";
				var str = '<div class="step-bar  bg-green" style="width: 33%;">'+
							'<span class="step-point bg-green icon-check"></span><span class="step-text">确订订单</span>'+
						  '</div>'+
						  '<div class="step-bar  bg-green" style="width: 33%;">'+
							'<span class="step-point bg-green icon-check"></span><span class="step-text">支付</span>'+
						  '</div>'+
						  '<div class="step-bar  bg-yellow" style="width: 34%;">'+
							'<span class="step-point bg-yellow icon-times"></span><span class="step-text text-yellow">支付失败</span>'+
						  '</div>';

				payFlowThree.resultSubTitle = str;

				var str2 = '<div class="text-center text-big text-yellow">对不起,支付失败!</div>'+
						   '<div class="text-center margin">'+msg+'</div>';

				payFlowThree.resultContent = str2;
			}
			}else if(payFlowThree.orderType == "弹性公网IP服务"){
				if(bol){
					// 支付成功
					payFlowThree.resultTitle = "支付成功";
					var str = '<div class="step-bar  bg-green" style="width: 33%;">'+
							  '<span class="step-point bg-green icon-check"></span><span class="step-text">确订订单</span>'+
							  '</div>'+
							  '<div class="step-bar  bg-green" style="width: 33%;">'+
							  '<span class="step-point bg-green icon-check"></span><span class="step-text">支付</span>'+
							  '</div>'+
							  '<div class="step-bar  bg-green" style="width: 34%;">'+
							  '<span class="step-point bg-green icon-check"></span><span class="step-text">支付成功</span>'+
							  '</div>';
					payFlowThree.resultSubTitle = str;

					var str2 = '<div class="text-center text-big">恭喜,支付成功!</div>'+
							   '<div class="text-center margin">您购买的弹性公网IP正在努力创建中，一般需要1-5分钟，请你耐心等待。</div>'+
							   '<div class="text-center  margin">'+
							   '<span class="text-blue text-small" style="cursor: pointer" ms-click="viewOrderList()">查看订单记录</span>'+
							   '&nbsp;|&nbsp;'+
							   '<span class="text-blue text-small" style="cursor: pointer" ms-click="viewEipList()">管理控制台</span>'+
							   '</div>';

					payFlowThree.resultContent = str2;
				}else{
					// 支付失败
					payFlowThree.resultTitle = "支付失败";
					var str = '<div class="step-bar  bg-green" style="width: 33%;">'+
							  '<span class="step-point bg-green icon-check"></span><span class="step-text">确订订单</span>'+
							  '</div>'+
							  '<div class="step-bar  bg-green" style="width: 33%;">'+
							  '<span class="step-point bg-green icon-check"></span><span class="step-text">支付</span>'+
							  '</div>'+
							  '<div class="step-bar  bg-yellow" style="width: 34%;">'+
							  '<span class="step-point bg-yellow icon-times"></span><span class="step-text text-yellow">支付失败</span>'+
							  '</div>';

					payFlowThree.resultSubTitle = str;

					var str2 = '<div class="text-center text-big text-yellow">对不起,支付失败!</div>'+
							   '<div class="text-center margin">'+msg+'</div>';

					payFlowThree.resultContent = str2;
				}
			}else if(payFlowThree.orderType == "云硬盘服务"){
				if(bol){
					// 支付成功
					payFlowThree.resultTitle = "支付成功";
					var str = '<div class="step-bar  bg-green" style="width: 33%;">'+
							  '<span class="step-point bg-green icon-check"></span><span class="step-text">确订订单</span>'+
							  '</div>'+
							  '<div class="step-bar  bg-green" style="width: 33%;">'+
							  '<span class="step-point bg-green icon-check"></span><span class="step-text">支付</span>'+
							  '</div>'+
							  '<div class="step-bar  bg-green" style="width: 34%;">'+
							  '<span class="step-point bg-green icon-check"></span><span class="step-text">支付成功</span>'+
							  '</div>';
					payFlowThree.resultSubTitle = str;

					var str2 = '<div class="text-center text-big">恭喜,支付成功!</div>'+
							   '<div class="text-center margin">您购买的云硬盘正在努力创建中，一般需要1-5分钟，请你耐心等待。</div>'+
							   '<div class="text-center  margin">'+
							   '<span class="text-blue text-small" style="cursor: pointer" ms-click="viewOrderList()">查看订单记录</span>'+
							   '&nbsp;|&nbsp;'+
							   '<span class="text-blue text-small" style="cursor: pointer" ms-click="viewCbsList()">管理控制台</span>'+
							   '</div>';

					payFlowThree.resultContent = str2;
				}else{
					// 支付失败
					payFlowThree.resultTitle = "支付失败";
					var str = '<div class="step-bar  bg-green" style="width: 33%;">'+
							  '<span class="step-point bg-green icon-check"></span><span class="step-text">确订订单</span>'+
							  '</div>'+
							  '<div class="step-bar  bg-green" style="width: 33%;">'+
							  '<span class="step-point bg-green icon-check"></span><span class="step-text">支付</span>'+
							  '</div>'+
							  '<div class="step-bar  bg-yellow" style="width: 34%;">'+
							  '<span class="step-point bg-yellow icon-times"></span><span class="step-text text-yellow">支付失败</span>'+
							  '</div>';

					payFlowThree.resultSubTitle = str;

					var str2 = '<div class="text-center text-big text-yellow">对不起,支付失败!</div>'+
							   '<div class="text-center margin">'+msg+'</div>';

					payFlowThree.resultContent = str2;
				}
			}
		}
	});

	return payFlowThree;
});