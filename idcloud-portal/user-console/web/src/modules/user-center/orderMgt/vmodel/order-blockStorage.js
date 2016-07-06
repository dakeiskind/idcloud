define(['layer',"app-modules/user-center/orderMgt/services/order-blockStorage",
        'app-utils/variableService',
		'app-utils/codeService',
        "app-utils/$extendService",
		'app-utils/jqx/shoppingServer'], function(layer,bsService,variable,code) {
	
	var orderBsInfo = avalon.define({
		$id:'orderBsPage',
		flag:false,
		regionData:[], // 区域数据,
        regionValue:"", // 当前区域下拉框数据
		zoneValue:"",
		zoneData:[],
        orderconfirm:"", // 订单确认页面地址
		orderData:[],// 当前购物车数据
        orderHistoryListData:[], // 当前历史清单数据
        orderNowData:[], // 当前立即购买数据
		initFormData:function(){
			orderBsInfo.regionData = code.getCustomCode("/topologys","POST",{resTopologyType:"R"});
			var zoom = orderBsInfo.regionData;
			orderBsInfo.regionValue = zoom[0].resTopologySid;
			if(!orderBsInfo.flag){
			bsService.getPrice(getDetaultMoney());
				//orderBsInfo.flag = !orderBsInfo.flag;
			}
		},
		// 确认购物车信息
		goToOrderConfirm:function(){
			orderBsInfo.orderData = orderBsInfo.orderHistoryListData;
			orderBsInfo.orderconfirm = '';
			orderBsInfo.orderconfirm =  variable.app_modules + "/user-center/orderMgt/views/order-pay-flow.html";
			// 获取数据
			$("#orderDg").addClass("hidden");
			$("#orderDg").removeClass("show");
			$("#orderConfirm").addClass("show");
			$("#orderConfirm").removeClass("hidden");
			$("#orderConfirm").addClass("fadein-left");
			$("#hostNav").addClass("hidden");
		},
		// 立即购买
		nowOrderConfirm:function(){
			orderBsInfo.orderNowData = [];
			var obj = getCloudObj("now");
			orderBsInfo.orderData = orderBsInfo.orderNowData;
			orderBsInfo.orderNowData = [];
			if(obj.status){
				orderBsInfo.orderconfirm = '';
				orderBsInfo.orderconfirm =  variable.app_modules + "/user-center/orderMgt/views/order-pay-flow.html";
				// 获取数据
				$("#orderDg").addClass("hidden");
				$("#orderDg").removeClass("show");
				$("#orderConfirm").addClass("show");
				$("#orderConfirm").removeClass("hidden");
				$("#orderConfirm").addClass("fadein-left");
				$("#hostNav").addClass("hidden");
			}
		},
		// 购买数量输入框
		changeCount: function(){
			var orderCountvalue = $("#orderCount").val()
			var g = /^[1-9]*[1-9][0-9]*$/;
			var flag = g.test(orderCountvalue);
			if(!flag){
				$("#orderCount").val(1);
			}
			orderBsInfo.calculationPrice();
		},
        decCountBtn:function(){
            $("#decbtn").decCount();
        },
        addCountBtn:function(){
            $("#addbtn").addCount();
        },
		// 加入选购清单
		addToShoppingList:function(){
			var obj = getCloudObj("history");
			if(obj.status){
				// 动画
				$("#bsAnamite").show().animate({left : '+=250px',top : '+=250px',height : '-=250px',width : '-=250px'}, 'fast');
				$("#bsAnamite").animate({left : '+=480px',top : '-=290px',height : '+=100px',width : '+=100px'},'fast',function() {
					$("#bsAnamite").hide();
					$("#bsAnamite").css({"top" : "80px","left" : "200px","width" : "300px","height" : "300px"});
				});
                $('#shoppingServer').showShopping(obj);
			}
		},
		//购买方式显示样式
		billingType:function(obj){
			$(".selected-biling-type").length > 0?$(".selected-biling-type").remove():null;
			$(obj).next().append("<span class='selected-biling-type text-small'>个月</span>");
            orderBsInfo.calculationPrice();
		},
        calculationPrice:function(){
            orderBsInfo.orderNowData=[];
            getCloudObj("now",false);
            bsService.getPrice(orderBsInfo.orderNowData);
        }
	});

	// 1.监听区域属性
    orderBsInfo.$watch("regionValue", function(a, b) {
        orderBsInfo.zoneData = code.getCustomCode("/topologys","POST",{parentTopologySid:a,resTopologyType:"RZ"});
        var temp = orderBsInfo.zoneData;
        orderBsInfo.zoneValue = temp[0].resTopologySid;
    });
	function getDetaultMoney(){
		var list = [];
		var transferData = new Object();
		transferData.orderType = "cbs";
		transferData.title = "云硬盘";
		// 计费类型
		transferData.billingType = "Month";
		// 地域分布
		transferData.regionSid = $("#areaBBig").val();
		transferData.regionName = $("#areaBBig option:selected").text();
		transferData.zoneSid = $("#areaBSmall").val();
		transferData.zoneName = $("#areaBSmall option:selected").text();
		// 硬盘类型
		transferData.diskType = $("input:radio[name=diskType]:checked").val();
		// 云硬盘大小
		transferData.diskSize = $("#cbsRangeValue").val();
		// 购买方式
		var buyTimeLength = $("input:radio[name=billingType]:checked").val();
		transferData.buyTime = (parseInt(buyTimeLength)>9)?parseInt(buyTimeLength)/12:parseInt(buyTimeLength);
		transferData.buyTimeType = (parseInt(buyTimeLength)>9)?"Year":"Month";
		// 购买数量
		transferData.orderCount = $("#orderBsCount").val();

		list.push(transferData);

		return list;

	}

    //得到表单数据对象
    function getCloudObj(type){
		var $id = new Date().getTime(); // 动态ID
		var validatorFlag = true;
		var notice = [];
		// 传输数据
		var transferData = new Object();
		transferData.id = $id;
		transferData.orderType = "cbs";
		transferData.title = "云硬盘";
		// 计费类型
		transferData.billingType = "Month";
		// 地域分布
		transferData.regionSid = $("#areaBBig").val();
		transferData.regionName = $("#areaBBig option:selected").text();
		transferData.zoneSid = $("#areaBSmall").val();
		transferData.zoneName = $("#areaBSmall option:selected").text();
		// 验证地域和分区
		if($("#areaDBBig").val() == null && isValidator){
			notice.push('区域');
			validatorFlag = false;
			$("#areaDBBig").css("border","1px solid red");
		}else{
			$("#areaDBBig").css("border","1px solid #DADADA");
		}
		if($("#areaDBSmall").val() == null && isValidator){
			notice.push('区域分区');
			validatorFlag = false;
			$("#areaDBSmall").css("border","1px solid red");
		}else{
			$("#areaDBSmall").css("border","1px solid #DADADA");
		}
		// 硬盘类型
		transferData.diskType = $("input:radio[name=diskType]:checked").val();
		// 云硬盘大小
		transferData.diskSize = $("#cbsRangeValue").val();
		// 购买方式
		var buyTimeLength = $("input:radio[name=billingType]:checked").val();
		transferData.buyTime = (parseInt(buyTimeLength)>9)?parseInt(buyTimeLength)/12:parseInt(buyTimeLength);
		transferData.buyTimeType = (parseInt(buyTimeLength)>9)?"Year":"Month";
		// 购买数量
        transferData.orderCount = $("#orderBsCount").val();
		// 价钱
		transferData.moneyPer = $("#cbsPrice").html().split("￥")[1];
		if(!validatorFlag){
			layer.msg(notice.join("、")+"不能为空");
		}else{
			if(type == "now"){
				orderBsInfo.orderNowData.push(transferData);
			}else{
				orderBsInfo.orderHistoryListData.push(transferData);
			}
		}

		// 展示数据
		var displayData = new Object();
		displayData.id = $id;
		displayData.status = true;
		displayData.title = "云硬盘";
		displayData.perCount = $('#orderBsCount').val();
		var buyTime = $("input:radio[name=billingType]:checked").val();
		displayData.count = ((parseInt(buyTime) > 9)? (parseInt(buyTime)/12+"年"): buyTime+"个月")+" X "+$('#orderBsCount').val()+"个";

		var data = new Array();
		var region = new Object();
		region.name="地域";
		region.value=$("#areaDBBig option:selected").text()+"("+$("#areaDBSmall option:selected").text()+")";
		data.push(region);

		var spec = new Object();
		spec.name="类型";
		if($("input:radio[name=diskType]:checked").val()==="cloud_normal"){
			spec.value= "普通硬盘";
		}else if($("input:radio[name=diskType]:checked").val()==="cloud_ssd"){
			spec.value= "SSD硬盘";
		}
		data.push(spec);

		var size = new Object();
		size.name="大小";
		size.value= $("#cbsRange").val()+"GB";
		data.push(size);

		var money = new Object();
		money.name="配置费用";
		money.value=$("#cbsPrice").text();
		money.style="color:red";
		data.push(money);

		displayData.data = data;
        displayData.delFunction='del(\''+$id+'\')';
		return displayData;
    }

    return orderBsInfo;

});