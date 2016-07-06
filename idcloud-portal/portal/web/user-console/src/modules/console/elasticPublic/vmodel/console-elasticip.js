/**
 * Created by Tdz on 2016/1/27.
 */
define(['app-modules/console/elasticPublic/services/console-elasticip',
        'layer','app-utils/httpService','lib/jquery/pintuer',
        'app-utils/validatorService',
        'app-utils/variableService',
        'app-utils/codeService',
        "app-utils/$extendService",
        "lib/jquery/jquery.bgiframe.min","lib/jquery/jquery.multiSelect"
        ], function(elaService,layer,http,pinter,validate,variable,code) {

    var consoleElasticIP = avalon.define({
        $id:'orderEDg',
        title:"订购",
        regionData:code.getCustomCode("/topologys","POST",{resTopologyType:"R"}), // 区域数据
        regionValue:"", // 当前区域下拉框数据
        zoneValue:"", // 当前区域分区下拉框数据
        zoneData:[], // 区域分区数据
        pnValue:"",
        orderconfirm:"", // 订单确认页面地址
        orderData:[], // 当前购物车数据
        orderHistoryListData:[], // 当前历史清单数据
        orderNowData:[], // 当前立即购买数据
        //initData:function(name){
        //    var obj = elaService.getData(name);
        //    return obj;
        //},
        // 确认订单
        toOrderConfim: function () {
            consoleElasticIP.orderData = consoleElasticIP.orderHistoryListData;
            consoleElasticIP.orderconfirm = '';
            consoleElasticIP.orderconfirm =  variable.app_modules + "/user-center/orderMgt/views/order-pay-flow.html";
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
            var obj = getCloudObj("now");
            consoleElasticIP.orderData = consoleElasticIP.orderNowData;
            consoleElasticIP.orderNowData = [];
            if(obj.status){
                consoleElasticIP.orderconfirm = '';
                consoleElasticIP.orderconfirm =  variable.app_modules + "/user-center/orderMgt/views/order-pay-flow.html";
                // 获取数据
                $("#orderDg").addClass("hidden");
                $("#orderDg").removeClass("show");
                $("#orderConfirm").addClass("show");
                $("#orderConfirm").removeClass("hidden");
                $("#orderConfirm").addClass("fadein-left");
                $("#hostNav").addClass("hidden");
            }
        },
        changeCount: function(){
            var orderCountvalue = $("#orderCount").val();
            var g = /^[1-9]*[1-9][0-9]*$/;
            var flag = g.test(orderCountvalue);
            if(!flag){
                $("#orderCount").val(1);
            }
            consoleElasticIP.calculationPrice();
        },
        changeSliderValue : function(){
            var rangevalue = $("#bandwidth").val()
            var g = /^[1-9]*[1-9][0-9]*$/;
            var flag = g.test(rangevalue);
            if(!flag||parseInt(rangevalue)<10){
                $("#bandwidth").val(10);
            }
            var slider = $("#range").data("ionRangeSlider");
            var max = $("#range").data().options;
            slider.update({
          from: $("#bandwidth").val()
      });
        },

        decCountBtn:function(){
            $("#decbtn").decCount();
        },
        addCountBtn:function(){
            $("#addbtn").addCount();
        },
        addEShopping : function(){
            var obj = getCloudObj("history");
            if(obj.status){
                // 动画
                $("#anamite").show().animate({left : '+=250px',top : '+=400px',height : '-=550px',width : '-=500px'}, 'fast');
                $("#anamite").animate({left : '+=400px',top : '-=250px',height : '+=150px',width : '+=100px'},'fast',function() {
                    $("#anamite").hide();
                    $("#anamite").css({"top" : "80px","left" : "300px","width" : "550px","height" : "600px"});
                });
                $('#shoppingServer').showShopping(obj);
            }

        },
        createShoppingCartInfo:function(data){

        },
        //购买方式
        billingType:function(obj){

            $(".selected-biling-type").length > 0?$(".selected-biling-type").remove():null;
            $(obj).next().append("<span class='selected-biling-type text-small'>个月</span>");
            consoleElasticIP.calculationPrice();
        },
         calculationPrice:function(){
             getCloudObj("now",false);
             elaService.getPrice(consoleElasticIP.orderNowData);
         }
    });

    consoleElasticIP.$watch("regionValue", function(a, b) {
        consoleElasticIP.zoneData = code.getCustomCode("/topologys","POST",{parentTopologySid:a,resTopologyType:"RZ"});
        var temp = consoleElasticIP.zoneData;
        consoleElasticIP.zoneValue = temp[0].resTopologySid;
    });
    // 初始化分区
    var zoom = consoleElasticIP.regionData;
    consoleElasticIP.regionValue = zoom[0].resTopologySid;
    //得到表单数据对象
    function getCloudObj(type){
        // 清空立即订购和购物车数组
        consoleElasticIP.orderNowData = [];

        var notice = []; // 验证选项错误数据
        var $id = new Date().getTime(); // 动态ID

        var displayData = new Object(); // 展示数据
        var transferData = new Object(); // 传输数据
        displayData.status = true;

        transferData.id = $id;
        transferData.orderType = "eip";
        transferData.title = "弹性公网IP";
        transferData.billingType = "Month";
        // 地域分布
        transferData.regionSid = $("#areaEBig").val();
        transferData.regionName = $("#areaEBig option:selected").text();
        transferData.zoneSid = $("#areaESmall").val();
        transferData.zoneName = $("#areaESmall option:selected").text();
        // 验证地域和分区
        if($("#areaDEBig").val() == null){
            notice.push('区域');
            displayData.status = false;
            $("#areaDEBig").css("border","1px solid red");
        }else{
            $("#areaDEBig").css("border","1px solid #DADADA");
        }
        if($("#areaDESmall").val() == null){
            notice.push('区域分区');
            displayData.status = false;
            $("#areaDESmall").css("border","1px solid red");
        }else{
            $("#areaDESmall").css("border","1px solid #DADADA");
        }

        // 带宽
        transferData.bandwidth = $("#bandwidth").val();
        // 付费方式
        var buyTimeLength = $("input:radio[name=billingType]:checked").val();
        transferData.buyTime = buyTimeLength;
        transferData.buyTimeType = (parseInt(buyTimeLength)>9)?"Year":"Month";
        // 购买数量
        transferData.orderCount = $("#orderCount").val();
        // 价钱
        transferData.moneyPer = $("#productPrice").html().split("￥")[1];

        // 显示验证消息
        if(!displayData.status){
            layer.msg(notice.join("、")+"不能为空");
        }else{
            if(type == "now"){
                consoleElasticIP.orderNowData.push(transferData);
            }else{
                consoleElasticIP.orderHistoryListData.push(transferData);
            }
        }

        // 传输数据
        displayData.id = $id;
        displayData.title = "弹性公网IP";
        displayData.perCount = $('#orderCount').val();
        var buytime = $("input:radio[name=billingType]:checked").val();
        if(transferData.buyTimeType == "Month"){
            displayData.count = buytime+"个月"+" X "+$('#orderCount').val()+"台";
        }else{
            displayData.count = buytime/12+"年"+" X "+$('#orderCount').val()+"台";
        }

        var data = new Array();
        var region = new Object();
        region.name="地域";
        region.value=$("#areaDEBig option:selected").text()+"("+$("#areaDESmall option:selected").text()+")";
        data.push(region);

        var bandwidth = new Object();
        bandwidth.name="带宽";
        bandwidth.value=$("#bandwidth").val()+"Mbps";
        data.push(bandwidth);
        var money = new Object();

        money.name="配置费用";
        money.value=$("#productPrice").text();
        money.style="color:red";
        data.push(money);
        displayData.data = data;
        displayData.delFunction='del(\''+$id+'\')';

        return displayData;
    }
    return consoleElasticIP;
});