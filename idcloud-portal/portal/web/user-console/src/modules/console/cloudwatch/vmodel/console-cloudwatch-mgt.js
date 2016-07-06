define(["app-modules/console/cloudwatch/services/console-cloudwatch-mgt",
    "lib/jquery/pintuer",
    'app-utils/jqx/window',
    'app-utils/variableService',
    'app-utils/messageBoxService',
    'app-utils/validatorService',
    'validator',
    "app-utils/$extendService"], function  (watchService,pintuer,window,variable,messageBox) {

    var watchMgt = avalon.define({
        $id:'watchMgt',
        title:'告警控制台',
        search:function(){
            if($("#searchSel").val()==1){
                searchObj.alarmObj = $("#searchText").val();
                searchObj.alarmStaus = "";
            }else if($("#searchSel").val()==2){
                searchObj.alarmObj = "";
                searchObj.alarmStaus = $("#searchText").val();
            }
            $("#watchMgtGrid").ptGrid("applyfilters");
            $('#watchMgtGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"查询成功!"
            });
        },
        refreshclick:function(){
            $("#watchMgtGrid").ptGrid("applyfilters");
            $('#watchMgtGrid').ptGrid("refreshfilterrow");

            messageBox.msgNotification({
                type:"success",
                message:"刷新成功!"
            });
        },
        removeClk:function(row){
            messageBox.confirm({
                message:"您确定要清除该条告警吗？",
                callback:function(){
                    $("#watchMgtGrid").ptGrid("removeRow",row);
                    $('#watchMgtGrid').ptGrid("refreshfilterrow");
                    messageBox.msgNotification({
                        type:"success",
                        message:"清除成功!"
                    });
                }
            });
        },
        confirmClick:function(row){
            messageBox.confirm({
                message:"确认该条告警？",
                callback:function(){
                    $("#watchMgtGrid").ptGrid("removeRow",row);
                    $('#watchMgtGrid').ptGrid("refreshfilterrow");
                    messageBox.msgNotification({
                        type:"success",
                        message:"确认成功!"
                    });
                }
            });
        }

    });

    var searchObj = {
        alarmObj:"",
        alarmStaus:""
    };

    //初始grid
    var initGrid = function(){
        $("#watchMgtGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: watchMgt,
            data:{
                localData: watchService.getGridDate(),
                params: searchObj
            },
            columns: [
                { text: '发生时间', datafield: 'happenTime'},
                { text: '告警对象', datafield: 'alarmObj'},
                { text: '告警内容', datafield: 'alarmContent'},
                { text: '持续时长', datafield: 'duration'},
                {text:"告警状态",datafield: 'alarmStaus'},
                {text:"策略类型",datafield: 'strategyType'},
                {text:"策略名称",datafield: 'strategyName'}
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
                {id: "confirmBtn",name:"确认告警",type:"button",icon:"icon-expand",disabled:true,click:"confirmClick()"},
                {id: "removeBtn",name:"清除告警",type:"button",icon:"icon-trash-o",disabled:true,click:"removeClk()"},

                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchSel",type:"select",align:"right",data:[{name:"告警对象",value:"1"},{name:"告警状态",value:"2"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#watchMgtGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#confirmBtn").removeAttr("disabled");
                    $("#removeBtn").removeAttr("disabled");
                }else{
                    $("#confirmBtn").attr("disabled","disabled");
                    $("#removeBtn").attr("disabled","disabled");
                }
            }
        });
    }

    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "cloudwatch";
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.console-cloudwatch-mgt';
        };

        $ctrl.$onRendered = function () {
            initGrid();
            pintuer.init();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [watchMgt];

    });
});
