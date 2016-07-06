define(['lib/jquery/pintuer',
    'app-modules/cloud-app/appManage/services/app-subscription',
    'app-utils/jqx/window',
    'app-utils/variableService',
    'app-utils/messageBoxService',
    'app-utils/validatorService',
    'validator',
    "app-utils/$extendService"], function(pintuer,appSubscriptionService,window,variable,messageBox) {

    var appSubscription = avalon.define({
        $id:'appSubscription',
        title:"已订阅应用",
        detailData:{},
        editData:{},

        buyCloudApp:function(row){
            alert("asdfasdfasdfasdf");
        },
        cancelSub:function(row){
            alert("cancelSub");
        },
        refreshClick: function(){
            initGrid();
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        search: function(){
            if($("#searchSel").val()==1) {
                searchObj.keypairsName = $("#searchText").val();
            }
            initGrid();
            messageBox.msgNotification({
                type:"success",
                message:"查询成功！"
            });
        }
    });

    var searchObj = {
        appName:"",
        mgtObjSid:variable.currentUser.mgtObjSid
    };

    var initGrid = function(){
        $("#appSubscriptionGrid").ptGrid({
            sortable: true,
            controller: appSubscription,
            data: {
                url: "/rest/keypairs/findKeypairs",
                //url: "/rest/app/displaySubsCloudApp",
                type: 'POST',
                params: searchObj
            },
            columns: [
                { text: '云应用名称', datafield: 'appName' ,width: 250},
                { text: '应用详情', datafield: 'appDetail'},
                { text: '操作', datafield: 'operation',width:100,align: 'center'
                    ,cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="buyCloudApp(' + row + ')">购买</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="cancelSub(' + row + ')">取消订阅</a>';
                    return cellsHtml;
                    }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshClick()"},
                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchSel",type:"select",align:"right",data:[{name:"应用名称",value:"1"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#appSubscriptionGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#removeBtn").removeAttr("disabled");
                }else{
                    $("#removeBtn").attr("disabled","disabled");
                }
            }
        });
    }

    return avalon.controller(function ($ctrl) {

        $ctrl.$onEnter = function (param, rs, rj) {
            // 进入视图
            avalon.vmodels.appContainer.secondNavFlag = "cloudapp";
            avalon.vmodels.appContainer.navSelectedFlag = 'app.cloudapp-subscription';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
            initGrid();
            avalon.scan(0,appSubscription);
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };
        $ctrl.$vmodels = [appSubscription];
    });

});