define(["app-modules/user-center/orderMgt/services/order-list",
        "lib/jquery/pintuer",
        "app-utils/messageBoxService",
        'app-utils/httpService',
        'app-utils/variableService',
        'app-utils/codeService',
        "app-utils/$extendService"
    ], function  (orderService,pintuer,messageBox,http,variable,codeS) {

    var orderMgt = avalon.define({
        $id: 'orderMgt',
        title: '订单管理',
        rowdata:{},
        row:0,
        payPage:"",
        search:function(){
            searchObj.serviceNameLike = $("#productName option:selected").text() == "全部" ? "": $("#productName option:selected").text();
            searchObj.status = $("#payStatus").val();
            searchObj.orderType = $("#orderType").val();
            searchObj.startTime = $('#beginDateFile').val();
            searchObj.endTime =  $('#endDateFile').val();
            $("#orderGrid").ptGrid("applyfilters");
            $('#orderGrid').ptGrid("refreshfilterrow");
            initGrid();
            messageBox.msgNotification({
                                           type:"success",
                                           message:"查询成功！"
                                       });
        },
        refresh:function(){
            initGrid();
            $("#orderGrid").ptGrid("applyfilters");
            $('#orderGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        exportOrder:function(){
            initSearchObj();
            orderService.exportOrder(searchObj);
        },
        detail:function(row){
            var rowDate = $("#orderGrid").ptGrid("getrowdata",row);
            orderMgt.rowdata = rowDate;
            orderMgt.row = row;
            if(rowDate.statusName=="未支付"){
                $("#detail-msg").show();
                $("#cancelBtn").show();
                $("#payBtn").show();
            }else{
                $("#detail-msg").hide();
                $("#cancelBtn").hide();
                $("#payBtn").hide();
            }
            $("#orderList").addClass("hidden");
            $("#orderDetail").removeClass("hidden");
            $("#orderDetail").addClass("fadein-left");
        },
        cancel:function(row){
            if(isNaN(row)){
                row = orderMgt.row;
            }
            messageBox.confirm({
                message:"您确定要作废订单吗？",
                callback:function(){
                    var rowDate = $("#orderGrid").ptGrid("getrowdata",row);
                    var  orderID = rowDate.orderId;
                    http.AjaxRequest({
                        type: 'POST',
                        url : "/rest/orders/cancel/"+orderID,
                        params:null,
                        callback : function (data) {
                            initGrid();
                        }
                    });
                    //orderService.refresh();
                    $("#orderGrid").ptGrid("applyfilters");
                    $('#orderGrid').ptGrid("refreshfilterrow");
                    $("#detail-msg").hide();
                    $("#cancelBtn").hide();
                    $("#payBtn").hide();
                }
            });
        },
        backList:function(){
            $("#orderDetail").addClass("hidden");
            $("#orderList").removeClass("hidden");
            $("#orderList").addClass("fadein-right");
        },
        goPay:function(row){
            orderMgt.rowdata = {};
            orderMgt.payPage = "";
            orderMgt.payPage = variable.app_modules + "/user-center/orderMgt/views/order-pay-flow.html";
            // 查询余额
            var amountMoney = 0;
            http.AjaxRequest({
                url : "/rest/billingAccount/display/account/"+variable.currentUser.userSid+"",
                type : "GET",
                async : false,
                showMsg:false,
                callback : function(data) {
                    amountMoney = parseFloat(data).toFixed(2);
                },
                failure: function(error){
                    amountMoney = 0;
                }
            });
            var rowDate = $("#orderGrid").ptGrid("getrowdata",row);
            rowDate.amountMoney = amountMoney;
            orderMgt.rowdata = rowDate;
            console.log(JSON.stringify(orderMgt.rowdata));
            $("#orderList").addClass("hidden");
            $("#payPage").removeClass("hidden");
            $("#payPage").addClass("fadein-left");
        },
         goConfirm:function(){
             orderMgt.payPage = "";
             orderMgt.payPage = variable.app_modules + "/user-center/orderMgt/views/order-pay-flow.html";
             // 查询余额
             var amountMoney = 0;
             http.AjaxRequest({
                                  url : "/rest/billingAccount/display/account/"+variable.currentUser.userSid+"",
                                  type : "GET",
                                  async : false,
                                  showMsg:false,
                                  callback : function(data) {
                                      amountMoney = parseFloat(data).toFixed(2);
                                  },
                                  failure: function(error){
                                      amountMoney = 0;
                                  }
                              });
             orderMgt.rowdata.amountMoney = amountMoney;
             $("#orderDetail").addClass("hidden");
             $("#orderList").addClass("hidden");
             $("#payPage").removeClass("hidden");
             $("#payPage").addClass("fadein-left");
         }
    });

    var searchObj = {
        serviceNameLike:"",
        orderType:"",
        status:"",
        startTime:"",
        endTime:""
    };

    var initSearchObj = function(){
        searchObj.serviceNameLike = $("#productName option:selected").text() == "全部" ? "": $("#productName option:selected").text();
        searchObj.status = $("#payStatus").val();
        searchObj.orderType = $("#orderType").val();
        searchObj.startTime = $('#beginDateFile').val();
        searchObj.endTime =  $('#endDateFile').val();
    };

    var init = function(){
        $('#beginDate').datepicker({autoclose: true,
            onClose: function( selectedDate ) {
                $( "#endDate" ).datepicker( "option", "minDate", selectedDate );
            }});
        $('#endDate').datepicker({autoclose: true,
            onClose: function( selectedDate ) {
                $( "#beginDate" ).datepicker( "option", "maxDate", selectedDate );
            }});
        initGrid();
    }

    //初始化收索条件
     var  initSearch =function(){
         codeS.setOption('productName','SERVICE_NAME');
         codeS.setOption('orderType','SERVICE_TYPE');
         codeS.setOption('payStatus','ORDER_STATUS');
     };


    //初始grid
    var initGrid = function(){
        $("#orderGrid").ptGrid({
            sortable: true,
            controller: orderMgt,
            data:{
                url: "/rest/orders/displayPersonalOrderList",
                type: 'POST',
                params: searchObj
            },
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refresh()"},
                {id: "expBtn",name:"导出",icon:"icon-download",type:"button",click:"exportOrder()"}
            ],
            columns: [
                { text: '订单号', datafield: 'orderId'},
                { text: '产品', datafield: 'serviceName'},
                { text:'订单类型', datafield:'orderTypeName'},
                { text: '创建时间', datafield: 'createdDt'},
                { text: '支付时间',datafield:'timePurchase'},
                { text: '状态',datafield:'statusName'},
                { text: '金额（元）',datafield:'amount'},
                { text: '操作', datafield: '',sortable: false,width:115,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="detail('+row+')">详情</a>';
                    if(rowdata.statusName=="未支付"){
                        cellsHtml += '<span class="text-explode">|</span>';
                        cellsHtml += '<a href="javascript:void(0);" ms-click="goPay('+row+')">支付</a>';
                        cellsHtml += '<span class="text-explode">|</span>';
                        cellsHtml += '<a href="javascript:void(0);" ms-click="cancel('+row+')">取消</a>';
                    }
                    return cellsHtml;
                }
                }
            ]
        });
    };

    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.userContainer.secondNavFlag = "expense";
            avalon.vmodels.userContainer.navSelectedFlag = 'user.expense-order';
        };

        $ctrl.$onRendered = function () {
            init();
            pintuer.init();
            initSearch();
            orderMgt.payPage = "";
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [orderMgt];

    });
});