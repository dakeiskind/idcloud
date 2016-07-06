define(["app-modules/console/loadBalance/services/monitor",
        "lib/jquery/pintuer",
        'app-utils/jqx/window',
        'app-utils/messageBoxService',
        'app-utils/variableService',
        "app-utils/$extendService"
], function  (monitorService,pintuer,window,messageBox,variable) {
    var monitor = avalon.define({
        $id:'monitor',
        title:'监控',
        addData:{
            monitorType:"PING",
            delay:null,
            overtime:null,
            maxTryTime:null,
            HTTPType:"GET",
            URL:"/",
            HTTPTypeStatus:"200"
        },

        add:function(){
            window.initWindow({
                title: "新增监控",
                url: variable.app_modules+"/console/loadBalance/views/add-monitor.html",
                btn: ['添加','取消'],
                area: ['450px', '480px'],
                confirm:function(){
                    if($("#addMonitorForm").valid()){
                        var t = avalon.vmodels.addMonitor.data;
                        messageBox.msgNotification({
                            type:"success",
                            message: t.monitorType+ "添加成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){

                }
            });
        },

        refreshclick:function(){
            $('#monitorGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新完成!"
            });
        },
        removeClk:function(){
            messageBox.confirm({
                message:"您确定要删除吗？",
                callback:function(){
                    $("#monitorGrid").ptGrid("removeRow");
                    $("#monitorGrid").ptGrid("applyfilters");
                    $('#monitorGrid').ptGrid("refreshfilterrow");
                    messageBox.msgNotification({
                        type:"success",
                        message:"删除成功!"
                    });
                }
            });
        },

        modify:function(row){
            var rowDate = $("#monitorGrid").ptGrid("getrowdata",row);
            messageBox.msgNotification({
                type:"info",
                message: rowDate.monitorType+"修改(缺少样式)"
            });
        },
        deleteMonitor:function(row){
            messageBox.confirm({
                message:"您确定要删除吗？",
                callback:function(){
                    $("#monitorGrid").ptGrid("removeRow",row);
                    $('#monitorGrid').ptGrid("refreshfilterrow");
                }
            });
        }
    });

    //初始grid
    var initGrid = function(){
        $("#monitorGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: monitor,
            data:{
                localData: monitorService.getGridDate()
            },
            columns: [
                {text:'监控类型',datafield:"monitorType",width:"200"},
                {text:"延迟",datafield:"delay"},
                {text:"超时",datafield:"overtime"},
                {text:"最大尝试次数",datafield:"maxTryTime"},
                {text:"详情",datafield:"detail"},
                {text:"操作", datafield: '',sortable: false,width:"80",align:"center"
                    , cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="modify('+row+')">修改</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="deleteMonitor('+row+')">删除</a>';
                    return cellsHtml;
                }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
                {id: "removeBtn",name:"删除",type:"button",disabled:true,click:"removeClk()"},

                //{id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                //{id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                //{id: "searchSel",type:"select",align:"right",data:[{name:"IP地址",value:"1"},{name:"协议端口",value:"2"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#monitorGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#removeBtn").removeAttr("disabled");
                }else{
                    $("#removeBtn").attr("disabled","disabled");
                }
            }
        });
    }

    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "loadbalance";
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.loadbalance-monitor';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            // require(['lib/jquery/pintuer']);
            initGrid();
            pintuer.init();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [monitor];

    });
});