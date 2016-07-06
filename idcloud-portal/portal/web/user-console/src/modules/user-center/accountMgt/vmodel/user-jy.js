define(['lib/jquery/pintuer',
        'app-modules/user-center/accountMgt/services/user-jy',
        'app-utils/jqx/window',
        'app-utils/messageBoxService',
        'app-utils/codeService',
        'app-utils/variableService',
        "app-utils/$extendService",
        'datepicker'], function  (pintuer,jyService,window,messageBox,codeS) {
    var userJy = avalon.define({
        $id:'userJy',
        title:"交易管理",
        searchCz:function(){
            searchObjCz.channel = $("#czChannel").val();
            searchObjCz.paymentTime = $("#beginRechargeDateFile").val();
            console.log(JSON.stringify(searchObjCz));
            $("#rechargeGrid").ptGrid("applyfilters");
            $('#rechargeGrid').ptGrid("refreshfilterrow");
            initGridRecharge();
            messageBox.msgNotification({
                type:"success",
                message:"查询成功！"
            });
        },
        searchProduct:function(){
            searchObjProduct.renewName=$("#renewName").val();
            searchObjProduct.creatTime = $("#beginRenewDate").val();
            searchObjProduct.endTime = $("#endRenewDate").val();
            $("#renewGrid").ptGrid("applyfilters");
            $('#renewGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"查询成功！"
            });
        },
        searchBill:function(){
            searchObjBill.serviceSid=$("#productBillName").val();
            searchObjBill.payStatus = $("#payStatus").val();
            searchObjBill.startTime = $("#startTime").val();
            searchObjBill.endTime = $("#endTime").val();
            console.log(JSON.stringify(searchObjBill));
            $("#billGrid").ptGrid("applyfilters");
            $('#billGrid').ptGrid("refreshfilterrow");
            initGridBill();
            messageBox.msgNotification({
                                           type:"success",
                                           message:"查询成功！"
                                       });
        },
        refreshclick:function(){
            $("#billGrid").ptGrid("applyfilters");
            $('#billGrid').ptGrid("refreshfilterrow");
            initGridBill();
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        refreshRecClick:function(){
            $("#rechargeGrid").ptGrid("applyfilters");
            $('#rechargeGrid').ptGrid("refreshfilterrow");
            initGridRecharge();
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        }
    });

    //创建账单明细对象
    var searchObjBill={
        serviceSid:"",
        startTime:"",
        endTime:"",
        payStatus:""
    };

    //初始化账单明细对象
    var searchObjSelect =function() {
        codeS.setOption('payStatus','DEPOSITE_PAY_STATUS');
        codeS.setOption('productBillName','SERVICE_NAME');
        //codeS.setCommonServiceOption("productBillName","/services/findServiceDefines","post",null);

    };

    //创建充值明细表单对象
    var searchObjCz = {
        channel:"",
        paymentTime:""
    };

    //初始化充值明细搜索下拉框
    var  initSearchCz =function(){
        codeS.setOption('czChannel','CHARGE_CHANNEL');
    };

    //创建产品续费对象
    //var searchObjProduct ={
    //    renewName:"",
    //    creatTime:"",
    //    endTime:""
    //};

    //账单明细
    var initGridBill = function(){

        $('#startTimeDiv').datepicker({
            autoclose: true,
            onClose: function( selectedDate ) {
               $( "#endTimeDiv" ).datepicker( "option", "minDate", selectedDate );
        }});
        $('#endTimeDiv').datepicker({autoclose: true,
            onClose: function( selectedDate ) {
            $( "#startTimeDiv" ).datepicker( "option", "maxDate", selectedDate );
        }});

        $("#billGrid").ptGrid({
            //selectionmode:"checkbox",
            sortable: true,
            controller: userJy,
            data:{
                url: "/rest/billingAccount/displayBillRecords",
                type: 'GET',
                params: searchObjBill
            },
            columns: [
                { text: '账单号', datafield: 'billId'},
                { text: '产品', datafield: 'serviceName'},
                {text:"收费方式", datafield:"billingTypeName"},
                {text:'应付金额',datafield:'amount'},
                {text:'实际支付金额',datafield:'realAmount'},
                {text:'支付状态',datafield:'statusName'},
                { text: '账期', datafield: 'billTime'},
                {text:"开始时间", datafield:"startTime"},
                {text:"结束时间", datafield:"endTime"},
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"}
                //{id:"exportBtn",name:"导出",type:"button",icon:"icon-share",click:"exportsBill()"},
                //{id: "afterPayBtn",name:"后付费",type:"button",icon:"icon-rmb",disabled:true,click:"afterClick()"},
                //{id: "beforePayBtn",name:"预付费",type:"button",icon:"icon-rmb",disabled:true,click:"beforeClick()"}
            ],
            rowselect:function(){
                var selectDatas = $("#billGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#afterPayBtn").removeAttr("disabled");
                    $("#beforePayBtn").removeAttr("disabled");
                }else{
                    $("#afterPayBtn").attr("disabled","disabled");
                    $("#beforePayBtn").attr("disabled","disabled");
                }
            }
        });
    }



    //初始化充值明细表单
    var initGridRecharge = function(){
        $("#rechargeGrid").ptGrid({
            sortable: true,
            controller:userJy,
            data:{
                url: "/rest/billingAccount/displayPaymentRecords",
                type: 'GET',
                params: searchObjCz
            },
            columns: [
                {text:'流水号', datafield:"flowId"},
                {text:"充值渠道",datafield:"channelName"},
                {text:"金额(元)", datafield:"amountDeposited"},
                {text:"支付金额", datafield:"amountReceived"},
                {text:"充值奖励(元)", datafield:"amountGift"},
                {text:"第三方交易号", datafield:"thirdPaymentNo"},
                {text:"充值人", datafield:"userName"},
                {text:"支付状态", datafield:"payStatusName"},
                {text:"所属账户", datafield:"accountName"},
                {text:"充值时间", datafield:"time"}
            ],
            toolbars:[
                {id: "refreshRecBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshRecClick()"}
            ]
        });
    }

    //续费明细列表
    //var initGridRenew = function(){
    //    $("#renewGrid").ptGrid({
    //        sortable: true,
    //        controller:userJy,
    //        data:{
    //            localData:jyService.getDataProduct(),
    //            params:searchObjProduct
    //        },
    //        columns: [
    //            { text: '账号', datafield: 'account'},
    //            { text: '产品名称', datafield: 'renewName'},
    //            { text: '单位', datafield: 'company'},
    //            { text: '续费数量', datafield: 'renewNum'},
    //            {text:'续费金额',datafield:'renewPay'},
    //            {text:'创建时间',datafield:'creatTime'},
    //            {text:'结束时间',datafield:'endTime'},
    //            { text: '续费开始时间', datafield: 'renewTime'}
    //        ],
    //        toolbars:[
    //            {id: "refreshRenBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshRenClick()"}
    //        ]
    //    });
    //}

    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.userContainer.secondNavFlag = "expense"
            avalon.vmodels.userContainer.navSelectedFlag = 'user.expense-consumption';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            // require(['lib/jquery/pintuer']);
            pintuer.init();
            initGridBill();
            initGridRecharge();
            //initGridRenew();
            searchObjSelect();
            initSearchCz();
            $("#bill-picker").datepicker({  format:'yyyy-mm-dd',autoclose: true});
            $("#beginRechargeDate").datepicker({  format:'yyyy-mm-dd',autoclose: true});
            $("#renew-picker").datepicker({  format:'yyyy-mm-dd',autoclose: true});
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [userJy];

    });
});
