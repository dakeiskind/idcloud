
define(['layer','app-utils/httpService','lib/jquery/pintuer',
        "app-modules/user-center/orderMgt/services/order-dg",
        'app-utils/validatorService',
        'app-utils/variableService',
        'app-utils/codeService',
        "app-utils/$extendService",
        "lib/jquery/jquery.bgiframe.min",
		"lib/jquery/jquery.multiSelect"], function(layer,http,pinter,dgService,validate,variable,code) {

	var orderDgInfo = avalon.define({
		$id:'orderDg',
		title:"订购",
		flag:false,
        regionData:[], // 区域数据
		regionValue:"", // 当前区域下拉框数据
		zoneValue:"", // 当前区域分区下拉框数据
        zoneData:[], // 区域分区数据
		pnValue:"",
		pnwData:[], //私有网络数据
		subNetData:[],
		sshData:[],// ssh数据

		orderConfirm:"", // 订单确认页面地址
		orderData:[], // 当前购物车数据
		orderHistoryListData:[], // 当前历史清单数据
		orderNowData:[], // 当前立即购买数据

		osTypeValue:"", // 操作系统类型数据
		imageValue:"", // 镜像类型数据
		osTypeData:[], // 操作系统类型数据
		osVersionData:[], // 操作系统类型数据

		initFormData:function(){
			orderDgInfo.regionData = code.getCustomCode("/topologys","POST",{resTopologyType:"R"});
			orderDgInfo.sshData = code.getCustomCode("/keypairs/findKeypairs/"+variable.currentUser.mgtObjSid,"GET",null);
			orderDgInfo.osTypeData = code.getCommonCode("OS_TYPE");
			// 初始化分区
			var zoom = orderDgInfo.regionData;
			orderDgInfo.regionValue = zoom[0].resTopologySid;
			// 初始化操作系统类型
			var osType = orderDgInfo.osTypeData;
			orderDgInfo.osTypeValue = osType[0].codeValue;

			if(!orderDgInfo.flag){
				dgService.getPrice(getDetaultMoney());
				orderDgInfo.flag = !orderDgInfo.flag;
			}
		},
		initData:function(name){
			var obj = dgService.getData(name);
			return obj;
		},
		initShoppingCart:function(){
			dgService.initShoppingCart();
		},
		// 确认订单
		toOrderConfim: function () {
			orderDgInfo.orderData = orderDgInfo.orderHistoryListData;
			orderDgInfo.orderConfirm = '';
			orderDgInfo.orderConfirm =  variable.app_modules + "/user-center/orderMgt/views/order-pay-flow.html";
			// 获取数据
            $("#orderDg").addClass("hidden");
            $("#orderDg").removeClass("show");
            $("#orderConfirm").addClass("show");
            $("#orderConfirm").removeClass("hidden");
            $("#orderConfirm").addClass("fadein-left");
            $("#hostNav").addClass("hidden");
        },
		// 立即购买
		nowOrderConfim:function(){
			var obj = getCloudObj("now",true);
			orderDgInfo.orderData = orderDgInfo.orderNowData;
			orderDgInfo.orderNowData = [];
			if(obj.status){
				orderDgInfo.orderConfirm = '';
				orderDgInfo.orderConfirm =  variable.app_modules + "/user-center/orderMgt/views/order-pay-flow.html";
				// 获取数据
				$("#orderDg").addClass("hidden");
				$("#orderDg").removeClass("show");
				$("#orderConfirm").addClass("show");
				$("#orderConfirm").removeClass("hidden");
				$("#orderConfirm").addClass("fadein-left");
				$("#hostNav").addClass("hidden");
			}
		},
		// 初始化子网
		initMutiSelect:function(data){
			if(data.length == 0){
				return;
			}
			if(orderDgInfo.pnValue == null || orderDgInfo.pnValue == ""){
				data = [];
			}

			var str = '<select id="subNetwork" style="width:100px;height:30px;" multiple="multiple">';
			for(var i=0;i<data.length;i++){
				if(i==0){
					str +='<option selected="selected" value="'+data[i].id+'">'+data[i].val+'</option>';
				}else{
					str +='<option value="'+data[i].id+'">'+data[i].val+'</option>';
				}
			}
			str +="</select>";
			$("#privateNetwork").html(str);
			$('#subNetwork').multiSelect({
				noneSelected:"选择子网",
				width:180
			});
		},
		// 一键创建私有网络
		gotoCreatePrivateNetwork:function(){
			//code.getCustomCode("/networks/default","POST",{zone:orderDgInfo.zoneValue});
			//orderDgInfo.pnwData = code.getCustomCode("/networks/mgtObj/"+variable.currentUser.mgtObjSid+"?resZoneSid="+orderDgInfo.zoneValue,"GET",null);
			//if(orderDgInfo.pnwData.length > 0){
			//	var networkData = orderDgInfo.pnwData;
			//	orderDgInfo.pnValue = networkData[0].resVpcSid;
			//}
			avalon.router.go("console.vpc-network");
		},
		// 添加数据盘
		addDataDisk:function(){
			dgService.addDataDisk();
			orderDgInfo.calculationPrice();
		},
		changeCount: function(){
			var orderCountvalue = $("#orderCount").val();
	    	var g = /^[1-9]*[1-9][0-9]*$/;
	        var flag = g.test(orderCountvalue);
	        if(!flag){
	        	$("#orderCount").val(1);
	        }
			orderDgInfo.calculationPrice();
		},
		decCountBtn:function(){
			var inputObj = $("#decbtn").next();
			var g = /^[1-9]*[1-9][0-9]*$/;
			var flag = g.test(inputObj.val().trim());
			if(!flag){
				inputObj.val(1);
			}else{
				var countValue = parseInt(inputObj.val().trim());
				if(countValue==1){
					inputObj.val(1);
					orderDgInfo.calculationPrice();
				}else{
					inputObj.val(countValue-1);
					orderDgInfo.calculationPrice();
				}
			}
		},
		addCountBtn:function(){
			var inputObj = $("#addbtn").prev();
			var g = /^[0-9]*[1-9][0-9]*$/;
			var flag = g.test(inputObj.val().trim());
			if(!flag&&inputObj.val().trim()!='0'){
				inputObj.val(0);
			}else{
				var countValue = parseInt(inputObj.val().trim());
				if(99 == countValue){
					return;
				}
				inputObj.val(countValue + 1);
				orderDgInfo.calculationPrice();
			}
		},
		// 添加到购物清单
		addToShoppingList: function(){
			var obj = getCloudObj("history",true);
			if(obj.status){
				// 动画
				$("#anamite").show().animate({left : '+=250px',top : '+=400px',height : '-=550px',width : '-=500px'}, 'fast');
				$("#anamite").animate({left : '+=400px',top : '-=250px',height : '+=150px',width : '+=100px'},'fast',function() {
					$("#anamite").hide();
					$("#anamite").css({"top" : "80px","left" : "300px","width" : "550px","height" : "600px"});
				});
				dgService.createShoppingCartInfo(obj);
			}
		},
		//购买方式
		billingType:function(obj){
			$(".selected-biling-type").length > 0?$(".selected-biling-type").remove():null;
			$(obj).next().append("<span class='selected-biling-type text-small'>个月</span>");
			// 计费
			orderDgInfo.calculationPrice();
		},
		calculationPrice:function(){
			getCloudObj("now",false);
			dgService.getPrice(orderDgInfo.orderNowData);
		}
	});

	// 1.监听区域属性
    orderDgInfo.$watch("regionValue", function(a, b) {
		// 计费
		if(orderDgInfo.flag){
			orderDgInfo.calculationPrice();
		}
		orderDgInfo.zoneData = code.getCustomCode("/topologys","POST",{parentTopologySid:a,resTopologyType:"RZ"});
		var temp = orderDgInfo.zoneData;
		orderDgInfo.zoneValue = temp[0].resTopologySid;
	});
	// 2.监听分区属性
	orderDgInfo.$watch("zoneValue", function(a, b) {
		orderDgInfo.pnwData = code.getCustomCode("/networks/mgtObj/"+variable.currentUser.mgtObjSid+"?resZoneSid="+a,"GET",null);
		if(orderDgInfo.pnwData.length > 0){
			var networkData = orderDgInfo.pnwData;
			orderDgInfo.pnValue = networkData[0].resVpcSid;
		}
		// 计费
		if(orderDgInfo.flag){
			orderDgInfo.calculationPrice();
		}
	});
	// 3.监听操作系统属性
	orderDgInfo.$watch("osTypeValue", function(a, b) {
		// 计费
		if(orderDgInfo.flag){
			orderDgInfo.calculationPrice();
		}
		orderDgInfo.osVersionData = code.getCustomCode("/images","POST",{
			osType:a,
			status:"01"
		});
		orderDgInfo.imageValue = orderDgInfo.osVersionData[0].resImageSid;
	});

	orderDgInfo.$watch("imageValue", function(a, b) {
		// 计费
		if(orderDgInfo.flag){
			orderDgInfo.calculationPrice();
		}
	});

	// 4.监听私有网络属性
	orderDgInfo.$watch("pnValue", function(a, b) {
		// 计费
		if(orderDgInfo.flag){
			orderDgInfo.calculationPrice();
		}
		orderDgInfo.subNetData = code.getCustomCode("/networks/"+a+"/subnets","GET",null);
		orderDgInfo.initMutiSelect(orderDgInfo.subNetData);
	});

	function getDetaultMoney(){
		var list = [];
		var transferData = new Object();
		transferData.orderType = "cs";
		transferData.title = "云主机";
		// 计费类型
		transferData.billingType = "Month";
		// 地域分布
		transferData.regionSid = $("#areaBig").val();
		transferData.regionName = $("#areaBig option:selected").text();
		transferData.zoneSid = $("#areaSmall").val();
		transferData.zoneName = $("#areaSmall option:selected").text();
		// 主机类型
		transferData.hostType = $("input:radio[name=computerType]:checked").val();
		// CPU
		transferData.cpu = $("input:radio[name=cpu]:checked").val();
		// 内存
		transferData.memory = $("input:radio[name=memory]:checked").val();
		// 镜像类型
		transferData.isoType = $("input:radio[name=mirroType]:checked").val();
		// 操作系统
		transferData.osType = $("#operSystemType option:selected").val();
		transferData.os = $("#operSystemVersion option:selected").val();
		transferData.osName = $("#operSystemVersion option:selected").text();
		// 硬盘类型
		transferData.diskType = $("#diskType").val();
		// 系统盘
		transferData.systemDisk = "30";
		// 数据盘
		var lengthRange = $(".rangeDiv").length;
		transferData.dataDisk = [];
		for(var i=0;i<lengthRange;i++){
			var $id = $(".rangeDiv").eq(i).attr("id");
			transferData.dataDisk.push($("#"+$id+"").data("ionRangeSlider").input.value);
		}
		// 网络
		transferData.networkType = $("#netType option:selected").val(); // 网络类型
		transferData.vpcSid = $("#vpcName option:selected").val(); // 私有网络
		transferData.vpcName = $("#vpcName option:selected").text(); // 私有网络名称
		transferData.subNetwork = [];
		transferData.subNetworkName = $("#subNetwork").find("span").html();
		$('input[name="subNetwork[]"]:checked').each(function() {
			transferData.subNetwork.push($(this).val());
		});
		// SSH秘钥
		transferData.sshkey = $("#sshkey").val();

		// 付费方式
		var buyTimeLength = $("input:radio[name=billingType]:checked").val();
		transferData.buyTime = (parseInt(buyTimeLength)>9)?parseInt(buyTimeLength)/12:parseInt(buyTimeLength);
		transferData.buyTimeType = (parseInt(buyTimeLength)>9)?"Year":"Month";
		// 购买数量
		transferData.orderCount = $("#orderCount").val();

		list.push(transferData);

		return list;

	}
	  //得到表单数据对象
    function getCloudObj(type,isValidator){
		// 清空立即订购和购物车数组
		orderDgInfo.orderData =[];
		orderDgInfo.orderNowData = [];

		var notice = []; // 验证选项错误数据
        var $id = new Date().getTime(); // 动态ID

		var displayData = new Object(); // 展示数据
     	var transferData = new Object(); // 传输数据
		displayData.status = true;

        transferData.id = $id;
		transferData.orderType = "cs";
		transferData.title = "云主机";
		// 计费类型
		transferData.billingType = "Month";
     	// 地域分布
     	transferData.regionSid = $("#areaBig").val();
		transferData.regionName = $("#areaBig option:selected").text();
     	transferData.zoneSid = $("#areaSmall").val();
		transferData.zoneName = $("#areaSmall option:selected").text();
		// 验证地域和分区
		if($("#areaBig").val() == null && isValidator){
			notice.push('区域');
			displayData.status = false;
			$("#areaBig").css("border","1px solid red");
		}else{
			$("#areaBig").css("border","1px solid #DADADA");
		}
		if($("#areaSmall").val() == null && isValidator){
			notice.push('区域分区');
			displayData.status = false;
			$("#areaSmall").css("border","1px solid red");
		}else{
			$("#areaSmall").css("border","1px solid #DADADA");
		}

     	// 主机类型
     	transferData.hostType = $("input:radio[name=computerType]:checked").val();
     	// CPU
     	transferData.cpu = $("input:radio[name=cpu]:checked").val();
     	// 内存
     	transferData.memory = $("input:radio[name=memory]:checked").val();
     	// 镜像类型
     	transferData.isoType = $("input:radio[name=mirroType]:checked").val();
     	// 操作系统
		transferData.osType = $("#operSystemType option:selected").val();
     	transferData.os = $("#operSystemVersion option:selected").val();
		transferData.osName = $("#operSystemVersion option:selected").text();
		// 验证操作系统
		if($("#operSystemVersion").val() == null && isValidator){
			notice.push('操作系统版本');
			displayData.status = false;
			$("#operSystemVersion").css("border","1px solid red");
		}else{
			$("#operSystemVersion").css("border","1px solid #DADADA");
		}

     	// 硬盘类型
     	transferData.diskType = $("#diskType").val();
     	// 系统盘
     	transferData.systemDisk = "30";
     	// 数据盘
		var lengthRange = $(".rangeDiv").length;
		transferData.dataDisk = [];
		for(var i=0;i<lengthRange;i++){
			var $id = $(".rangeDiv").eq(i).attr("id");
			transferData.dataDisk.push($("#"+$id+"").data("ionRangeSlider").input.value);
		}
     	// 网络
     	transferData.networkType = $("#netType option:selected").val(); // 网络类型
		transferData.vpcSid = $("#vpcName option:selected").val(); // 私有网络
		transferData.vpcName = $("#vpcName option:selected").text(); // 私有网络名称
		// 验证操作系统
		if($("#vpcName").val() == null && isValidator){
			notice.push('私有网络');
			displayData.status = false;
			$("#vpcName").css("border","1px solid red");
		}else{
			$("#vpcName").css("border","1px solid #DADADA");
		}
     	transferData.subNetwork = [];
		transferData.subNetworkName = $("#subNetwork").find("span").html();
     	$('input[name="subNetwork[]"]:checked').each(function() {
     		transferData.subNetwork.push($(this).val());
     	});
		if(transferData.subNetwork.length == 0 && isValidator){
			notice.push('子网');
			$("#subNetwork").css("border","1px solid red");
			displayData.status = false;
		}else{
			$("#subNetwork").css("border","1px solid #DADADA");
		}
     	// SSH秘钥
     	transferData.sshkey = $("#sshkey").val();
		/*
		if($("#sshkey").val() == null){
			notice.push('SSH');
		 displayData.status = false;
			$("#sshkey").css("border","1px solid red");
		}else{
			$("#sshkey").css("border","1px solid #DADADA");
		}
		*/
     	// 付费方式
		var buyTimeLength = $("input:radio[name=billingType]:checked").val();
     	transferData.buyTime = (parseInt(buyTimeLength)>9)?parseInt(buyTimeLength)/12:parseInt(buyTimeLength);
		transferData.buyTimeType = (parseInt(buyTimeLength)>9)?"Year":"Month";
     	// 购买数量
     	transferData.orderCount = $("#orderCount").val();
		// 价钱
		transferData.moneyPer = $("#productPrice").html().split("￥")[1];

		// 显示验证消息
		if(!displayData.status && isValidator){
			layer.msg(notice.join("、")+"不能为空");
		}else{
			if(type == "now"){
				orderDgInfo.orderNowData.push(transferData);
			}else{
				orderDgInfo.orderHistoryListData.push(transferData);
			}
		}

		// 传输数据
		displayData.id = $id;
		displayData.title = "云主机";
		displayData.perCount = $('#orderCount').val();
		var buyTime = $("input:radio[name=billingType]:checked").val();
		displayData.count = ((parseInt(buyTime) > 9)? (parseInt(buyTime)/12+"年"): buyTime+"个月")+" X "+$('#orderCount').val()+"台";

     	var data = new Array();
    	var region = new Object();
		region.name="地域";
		region.value=$("#areaBig option:selected").text()+"("+$("#areaSmall option:selected").text()+")";
    	data.push(region);

    	var spec = new Object();
		spec.name="规格";
    	var cpu = $('input[name="cpu"]:checked').val();
    	var memory = $('input[name="memory"]:checked').val();
		spec.value= "cpu:"+cpu+"核、内存:"+memory+"GB、系统盘:30GB";
    	data.push(spec);

    	var network = new Object();
		network.name="网络";
		network.value= $("#networkType  option:selected").text()+"("+$("#vpcName  option:selected").text()+")";
    	data.push(network);

		var subNet = new Object();
		subNet.name="子网";
		subNet.value= transferData.subNetworkName;
		data.push(subNet);

		var image = new Object();
		image.name="操作系统";
		image.value= transferData.osType+"("+transferData.osName+")";
		data.push(image);

    	var money = new Object();
		money.name="配置费用";
		money.value= $("#productPrice").text();
		money.style="color:red";
    	data.push(money);

		displayData.data = data;
 
    	return displayData;
    }
  
    return orderDgInfo;
});