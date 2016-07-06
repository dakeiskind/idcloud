define(["app-modules/console/cloudwatch/services/console-strategy-mgt",
    "lib/jquery/pintuer",
    'app-utils/jqx/window',
    'app-utils/variableService',
    'app-utils/messageBoxService',
    'app-utils/validatorService',
    'validator',
    "app-utils/$extendService"], function  (strategyService,pintuer,window,variable,messageBox) {

    var strategyMgt = avalon.define({
        $id:'strategyMgt',
        title:'告警策略',
        manageTitle:"",
        manageData:{
            strategyName:"",
            strategyType:"",
            desc:"",
            editTime:""
        },
        addStrategy:function(){
            window.initWindow({
                title: "创建告警策略",
                url: variable.app_modules+"/console/cloudwatch/views/strategy-add.html",
                area: ['500px', '380px'],
                confirm:function(){
                    if($("#addPnForm").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message:"添加私有网络成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){

                }

            });
        },
        search:function(){
            if($("#searchSel").val()==1){
                searchObj.strategyName = $("#searchText").val();
                searchObj.strategyType = "";
            }else if($("#searchSel").val()==2){
                searchObj.strategyName = "";
                searchObj.strategyType = $("#searchText").val();
            }
            $("#strategyMgtGrid").ptGrid("applyfilters");
            $('#strategyMgtGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"查询成功!"
            });
        },
        refreshclick:function(){
            $("#strategyMgtGrid").ptGrid("applyfilters");
            $('#strategyMgtGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功!"
            });
        },
        removeClk:function(row){
            messageBox.confirm({
                message:"您确定要删除该条策略吗？",
                callback:function(){
                    $("#strategyMgtGrid").ptGrid("removeRow",row);
                    $('#strategyMgtGrid').ptGrid("refreshfilterrow");
                    messageBox.msgNotification({
                        type:"success",
                        message:"删除成功!"
                    });
                }
            });
        },
        manageClick:function(row){
            var rowDate = $("#strategyMgtGrid").ptGrid("getrowdata",row);
            avalon.vmodels.strategyMgt.manageTitle = rowDate.strategyName;
            var dataDetail = {
                strategyName:rowDate.strategyName,
                strategyType:rowDate.strategyType,
                desc:rowDate.desc,
                editTime:rowDate.editTime
            };
            avalon.vmodels.strategyMgt.manageData = dataDetail;

            $("#strategyMgtList").addClass("hidden");
            $("#strategyMgtList").removeClass("show");
            $("#strategyMgtPage").removeClass("hidden");
            $("#strategyMgtPage").addClass("fadein-right");
        },
        backList:function(){
            $("#strategyMgtPage").addClass("hidden");
            $("#strategyMgtPage").removeClass("show");
            $("#strategyMgtList").removeClass("hidden");
            $("#strategyMgtList").addClass("fadein-left");
        }

    });

    var searchObj = {
        strategyName:"",
        strategyType:""
    };

    //初始grid
    var initGrid = function(){
        $("#strategyMgtGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: strategyMgt,
            data:{
                localData: strategyService.getGridDate(),
                params: searchObj
            },
            columns: [
                { text: '策略名称', datafield: 'strategyName'},
                { text: '策略类型', datafield: 'strategyType'},
                { text: '已应用', datafield: 'used'},
                { text: '最后修改时间', datafield: 'editTime'},
                {text:"描述",datafield: 'desc'},
                { text: '操作', datafield: '',sortable: false,width:190,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="editClick('+row+')">修改</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="manageClick('+row+')">管理</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="copyClick('+row+')">复制</a>';
                    return cellsHtml;
                }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
                {id: "removeBtn",name:"删除",type:"button",icon:"icon-trash-o",disabled:true,click:"removeClk()"},

                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchSel",type:"select",align:"right",data:[{name:"策略名称",value:"1"},{name:"策略类型",value:"2"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#strategyMgtGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#removeBtn").removeAttr("disabled");
                }else{
                    $("#removeBtn").attr("disabled","disabled");
                }
            }
        });
        $("#manageRule").ptGrid({
            sortable: true,
            pageable:false,
            controller: strategyMgt,
            data:{
                localData:strategyService.getRuleGridData()
            },
            columns: [
                { text: '监控项', datafield: 'monitoriItems'},
                { text: '条件', datafield: 'condition'},
                { text: '阀值', datafield: 'threshold'},
                { text: '持续时间', datafield: 'duration'}
                ],
            toolbars:[
                {id: "mgtRuleBtn",name:"管理告警规则",type:"button",click:"mgtRuleBtnClick()"},
            ]
        });
        $("#alarmObjGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            pageable:false,
            controller: strategyMgt,
            data:{
                localData:strategyService.getRelationGridData()
            },
            columns: [
                { text: '名称', datafield: 'relationName'},
                { text: '内网IP', datafield: 'insiderNet'},
                { text: '网络', datafield: 'netWork'},
                { text: '操作', datafield: 'operation'}
            ],
            toolbars:[
                {id: "addRelBtn",name:"+新增关联",type:"button",click:"addRelBtnClick()"},
                {id: "relieveBtn",name:"解除",type:"button",disabled:true,click:"relieveBtnClick()"},
                {id: "relieveAllBtn",name:"全部解除",type:"button",disabled:true,click:"relieveAllBtnClick()"},
                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchSel",type:"select",align:"right",data:[{name:"名称",value:"1"},{name:"内网IP",value:"2"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#alarmObjGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#relieveBtn").removeAttr("disabled");
                    $("#relieveAllBtn").removeAttr("disabled");
                }else{
                    $("#relieveBtn").attr("disabled","disabled");
                    $("#relieveAllBtn").attr("disabled","disabled");
                }
            }
        });
        $("#alarmInform").ptGrid({
            sortable: true,
            //pageable:false,
            controller: strategyMgt,
            data:{
                localData:strategyService.getNoticeGridData()
            },
            columns: [
                { text: '告警联系组', datafield: 'alarmCG'},
                { text: '告警联系人', datafield: 'alarmContacts'},
                { text: '网络', datafield: 'netWork'},
                { text: '通知方式', datafield: 'noticeMethod'}
            ],
            toolbars:[
                {id: "manageContactsBtn",name:"管理告警联系组",type:"button",click:"manageContactsBtnClick()"},
            ]
        });
    }



    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "cloudwatch";
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.console-strategy-mgt';
        };

        $ctrl.$onRendered = function () {
            initGrid();
            pintuer.init();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [strategyMgt];

    });
});

