define(['lib/jquery/pintuer','app-modules/user-center/accountMgt/services/user-xf','app-utils/jqx/window','app-utils/messageBoxService','app-utils/variableService','app-utils/$extendService'], function (pintuer,xfService,window,messageBox,variable){
    var userXf = avalon.define({
       $id:"userXf",
        title:"续费",
        searchXf:function(){
            searchObj.productName = $("#productName").val();
            searchObj.startTime=$("#beginDate").val();
            searchObj.endTime=$("#endDate").val();
            searchObj.status=$("#status").val();
            $("#xfGrid").ptGrid("applyfilters");
            $('#xfGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"查询成功！"
            });
        },
        renewalsClick:function(row){
            messageBox.confirm({
                message:"您确定为产品续费吗？",
                callback:function(){
                    messageBox.msgNotification({
                        type:"info",
                        message:"续费功能后续操作"
                    });
                }
            });
        },
        refreshclick:function(){
            $("#xfGrid").ptGrid("applyfilters");
            $('#xfGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
    });

    //定义一个查询对象
    var searchObj = {
        productName:"",
        status:"",
        startTime:"",
        endTime:""
    };


    //初始化时间插件
    var initInput = function(){
        $('#xf-picker').datepicker({ format: 'yyyy-mm-dd ',autoclose: true
        });
    };

    //初始化grid
    var initGrid = function(){
        $("#xfGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller:userXf,
            data:{
                localData:xfService.getData(),
                params:searchObj
            },
            columns: [
                { text: '产品名称', datafield: 'productName'},
                { text: '单位', datafield: 'company'},
                { text: '余额', datafield: 'balance'},
                { text: '冻结余额', datafield: 'frozen'},
                {text:'可用余额',datafield:'available'},
                {text:'产品单价',datafield:'nitPrice'},
                {text:'产生费用',datafield:'allPrice'},
                {text:'激活状态',datafield:'status'},
                {text:'激活时间',datafield:'statusTime'},
                {text:'开始时间',datafield:'startTime'},
                { text: '结束时间', datafield: 'endTime'}
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
                {id: "renewalsBtn",name:"续费",type:"button",icon:"icon-random",disabled:true,click:"renewalsClick()"}
            ],
            rowselect:function(){
                var selectDatas = $("#xfGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#renewalsBtn").removeAttr("disabled");
                }else{
                    $("#renewalsBtn").attr("disabled","disabled");
                }
            }
        });
    }


    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.userContainer.secondNavFlag = "expense"
            avalon.vmodels.userContainer.navSelectedFlag = 'user.expense-renew';
        };

        $ctrl.$onRendered = function () {
            pintuer.init();
            initGrid();
            initInput();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [userXf];

    });
});
