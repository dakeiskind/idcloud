define(["lib/jquery/pintuer",
    "app-modules/user-center/accountMgt/services/user-fp",
    'app-utils/jqx/window',
    'app-utils/messageBoxService',
    'app-utils/codeService',
    'app-utils/variableService',
    'app-utils/validatorService',
    'validator'], function(pintuer,fpService,window,messageBox,codeService,variable,validate){
    var userFp = avalon.define({
        $id:"userFp",
        title:"发票管理",
        data:{},
        invoiceItemData:[],
        invoiceType:null,
        serviceName:null,
        invoiceAmount:{
            totalAmount:0,
            availableAmount:9999.99,
            usedAmount:1235.50
        },

        //按条件搜索
        searchFp:function(){
            searchObjList.invoiceStatus = $("#fpStatus").val();
            searchObjList.searchStartTime = $("#startTime").val();
            $("#endTime").val() != "" && $("#endTime").val() != null ?
                searchObjList.searchEndTime = $("#endTime").val()+" 23:59:59" : searchObjList.searchEndTime = "";
            initGrid();
            messageBox.msgNotification({
                type:"success",
                message:"查询成功！"
            });
        },
        refreshFpClick:function(){
            initGrid();
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },

        //发票类型切换
        invoiceTypeSwitch:function(obj){
            if($(obj).val() != userFp.invoiceType){
                userFp.invoiceType = $(obj).val();
                if($(obj).val() == '01'){
                    $("#special").removeClass("hidden");
                }else{
                    $("#special").addClass("hidden");
                }
            }
        },
        addSelect:function(){
            window.initWindow({
                title: "新增",
                url: variable.app_modules+"/user-center/accountMgt/views/addFp.html",
                btn: ['确定', '取消'],
                area: ['400px', '180px'],
                confirm:function(){
                    if($("#addFpForm").valid()){
                        //TODO 这里有BUG 以后修改
                        var data = avalon.vmodels.fpAdd.data;
                        userFp.invoiceItemData.push(data);
                        //alert(JSON.stringify(userFp.invoiceItemData));
                        data.invoiceItemAmount = parseFloat(data.invoiceItemAmount).toFixed(2);
                        $("#invoiceItemDetails").find(".text-gray").addClass("hidden");
                        $("#invoiceItemDetails").append(
                            '<li style="font-size: 12px;">'
                            +data.serviceName+'：'+data.invoiceItemAmount+'元' +
                            //'<a href="javascript:void(0);" class="text-blue text-small margin-left">删除</a>' +
                            '</li>');
                        userFp.invoiceAmount.totalAmount = parseFloat(data.invoiceItemAmount*1 + userFp.invoiceAmount.totalAmount*1).toFixed(2);
                        return true;
                    }
                    return false;
                },
                callback:function(){
                    avalon.vmodels.userFp.serviceName = $("#invoiceItem").find("option:selected").text();
                }
            });
        },

        submit:function(){
            if(validata()){
                var result = fpService.addInvoice(userFp.data,userFp.invoiceItemData);
                if(result){
                    initGrid();
                    successOperation();
                    messageBox.msgNotification({
                        type:"success",
                        message:"申请发票成功！"
                    });
                }
            }else{
                messageBox.msgNotification({
                    type:"warning",
                    message:"输入有误，请检查！"
                });
                return;
            }
        }
    });

    var successOperation = function(){
        $(".tab-nav li:nth-child(2)").removeClass("active");
        $(".tab-nav li:nth-child(1)").addClass("active");
        $(".tab-body div:nth-child(2)").removeClass("active");
        $(".tab-body div:nth-child(1)").addClass("active");
        userFp.data = {};
        userFp.invoiceItemData = [];
        userFp.invoiceAmount.totalAmount = parseFloat(0).toFixed(2);
        $(".form-group").removeClass("check-success");
        $("#invoiceItemDetails").empty();
        $("#invoiceItemDetails").append('<li class="text-gray text-little">暂无条目</li>');
    };

    var validata = function(){
        userFp.data.invoiceType = $("#invoiceType").val();
        userFp.data.appUserSid = $.cookie("USER_SID");
        if(userFp.data.invoiceType == '00'){
            if(userFp.data.invoiceHead != null && userFp.data.invoiceHead != ""  &&
                userFp.data.receiver != null && userFp.data.receiver != "" &&
                userFp.data.address != null && userFp.data.address != "" &&
                userFp.data.postalCode != null && userFp.data.postalCode != "" &&
                userFp.data.phone != null && userFp.data.phone != ""){
                if($("#invoiceHead").next().length == 0 &&
                    $("#receiver").next().length == 0 &&
                    $("#address").next().length == 0 &&
                    $("#postalCode").next().length == 0 &&
                    $("#phone").next().length == 0){
                    if(userFp.invoiceItemData.length != 0){
                        $("#invoiceItemDetails").next().addClass("hidden");
                        userFp.data.totalAmount = userFp.invoiceAmount.totalAmount;
                        return true;
                    }else{
                        $("#invoiceItemDetails").next().removeClass("hidden");
                        return false;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
        if(userFp.data.invoiceType == '01'){
            if(userFp.data.invoiceHead != null && userFp.data.invoiceHead != ""  &&
                userFp.data.receiver != null && userFp.data.receiver != "" &&
                userFp.data.address != null && userFp.data.address != "" &&
                userFp.data.postalCode != null && userFp.data.postalCode != "" &&
                userFp.data.phone != null && userFp.data.phone != "" &&
                userFp.data.taxpayerId != null && userFp.data.taxpayerId != "" &&
                userFp.data.bankNo != null && userFp.data.bankNo != "" &&
                userFp.data.taxpayerAddress != null && userFp.data.taxpayerAddress != "" &&
                userFp.data.taxpayerPhone != null && userFp.data.taxpayerPhone != ""){
                if(!($(".input-help").length > 0)){
                    if(userFp.invoiceItemData.length != 0){
                        $("#invoiceItemDetails").next().addClass("hidden");
                        userFp.data.totalAmount = userFp.invoiceAmount.totalAmount;
                        userFp.data.bankName = $("#bankName").val();
                        return true;
                    }else{
                        $("#invoiceItemDetails").next().removeClass("hidden");
                        return false;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    };

    var initInput = function(){
        $('#beginDate').datepicker({});
        $('#endDate').datepicker({});
        codeService.setOption('fpStatus','INVOICE_STATUS');
        codeService.setOption('invoiceType','INVOICE_TYPE');
        codeService.setOption('invoiceItem','INVOICE_ITEM');
        codeService.setOption('bankName','BANK');
        //刷新数据
        userFp.data = {};
        userFp.invoiceAmount.totalAmount = 0;
        userFp.invoiceAmount.totalAmount = parseFloat(userFp.invoiceAmount.totalAmount).toFixed(2);
        userFp.invoiceAmount.availableAmount = parseFloat(userFp.invoiceAmount.availableAmount).toFixed(2);
        userFp.invoiceAmount.usedAmount = parseFloat(userFp.invoiceAmount.usedAmount).toFixed(2);
    };

    var searchObjList = {
        searchStartTime:"",
        searchEndTime:"",
        invoiceStatus:""
    };
    //发票列表初始化
    var initGrid = function(){
        $("#fpGrid").ptGrid({
            sortable: true,
            controller:userFp,
            data:{
                url: "/rest/invoices/findByParams",
                type: 'post',
                params: searchObjList
            },
            columns: [
                { text: '发票编号', datafield: 'invoiceNo'},
                { text: '发票抬头', datafield: 'invoiceHead'},
                { text: '发票类型', datafield: 'invoiceTypeName'},
                {text:'申请时间',datafield:'createdDt'},
                {text:'状态',datafield:'invoiceStatusName'},
                { text: '备注', datafield: 'description'}
            ],
            toolbars:[
                {id: "refreshFpBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshFpClick()"},
            ]
        });
    };

    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.userContainer.secondNavFlag = "expense"
            avalon.vmodels.userContainer.navSelectedFlag = 'user.expense-invoice';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            // require(['lib/jquery/pintuer']);
            pintuer.init();
            initGrid();
            initInput();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [userFp];

    });
});
