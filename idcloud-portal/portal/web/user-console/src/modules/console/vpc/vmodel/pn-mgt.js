define(['lib/jquery/pintuer',
        'app-utils/jqx/window',
        'app-utils/variableService',
        'app-utils/messageBoxService',
        'app-utils/codeService',
        'app-utils/httpService',
        'app-utils/validatorService',
        'validator',
        "app-utils/$extendService"], function  (pintuer,window,variable,messageBox,code,http) {

    var pnMgt = avalon.define({
        $id:'pnMgt',
        title:'私有网络',
        regionData:[],
        regionValue:"",
        zoneData:[],
        zoneValue:"",
        eidtPnData:null,
        initModelData: function(){
            pnMgt.regionData = code.getCustomCode("/topologys","POST",{resTopologyType:"R"});
            pnMgt.regionValue = pnMgt.regionData[0].resTopologySid;

            pnMgt.zoneData = code.getCustomCode("/topologys","POST",{parentTopologySid:pnMgt.regionValue,resTopologyType:"RZ"});
            pnMgt.zoneValue = pnMgt.zoneData[0].resTopologySid;
        },
        regionChanged: function(){
            pnMgt.zoneData = code.getCustomCode("/topologys","POST",{parentTopologySid:pnMgt.regionValue,resTopologyType:"RZ"});
            pnMgt.zoneValue = pnMgt.zoneData[0].resTopologySid;
        },
        zoneChanged: function(){
            // 重新查询值
            initGrid();
        },
        refreshclick:function(){
            initGrid();
        },
        // 跳转到子网视图
        goSubnetNetworkPage:function(){
            avalon.router.go("console.vpc-subnet");
        },
        // 跳转到路由器视图
        goRouterPage:function(){
            avalon.router.go("console.vpc-router");
        },
        removePn:function(row){
            messageBox.confirm({
                message:"您确定要删除该私有网络吗？",
                callback:function(){
                    var gridData = $("#pnGrid").ptGrid("getrowdata",row);
                    var data = {
                        zone: gridData.zone,
                        networkSid: gridData.resVpcSid
                    }
                    http.AjaxRequest({
                        url :"/rest/networks/vpc/delete",
                        type: "POST",
                        params: data,
                        async: true,
                        callback : function (data) {
                            initGrid();
                        }
                    });
                }
            });
        },
        addCustomNetwork:function(){
            window.initWindow({
                title: "创建私有网络", 
                url: variable.app_modules+"/console/vpc/views/pn-add.html",
                //btn: ['保存', '取消'],
                area: '500px'
             });
        },
        eidtCustomNetwork:function(row){
            window.initWindow({
                title: "编辑私有网络", 
                url: variable.app_modules+"/console/vpc/views/pn-edit.html",
                btn: ['保存', '取消'],
                area: ['500px', '380px'],
                confirm:function(){
                    if($("#editPnForm").valid()){
                        var data = avalon.vmodels.pnEditWindow.editPnData;
                        http.AjaxRequest({
                            url :"/rest/networks/vpc/update",
                            type: "POST",
                            params: data,
                            async: true,
                            callback : function (data) {
                                initGrid();
                            }
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                    var gridData = $("#pnGrid").ptGrid("getrowdata",row);
                    pnMgt.eidtPnData={
                        resVpcSid: gridData.resVpcSid,
                        vpcName:gridData.vpcName,
                        description:gridData.description,
                        cidr:gridData.cidr
                    };
                }
                
             });
        }
    });

    //初始grid
    var initGrid = function(){
        var json = {
            zoneSid: pnMgt.zoneValue,
        };
        $("#pnGrid").ptGrid({
            sortable: true,
            controller: pnMgt,
            data:{
                url:"/rest/networks/vpc?"+ $.param(json),
                type : "GET",
            },
            columns: [
                { text: 'VPC ID/名称', datafield: 'vpcName'},
                { text: '网段', datafield: 'cidr'},
                { text: '状态', datafield: 'statusName'},
                { text: '创建时间', datafield: 'createdDt'},
                { text: '描述', datafield: 'description'},
                { text: '操作', datafield: '',sortable: false,width:230,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                        var cellsHtml = "";
                        cellsHtml += '<a href="javascript:void(0);" ms-click="goSubnetNetworkPage()">子网管理</a>';
                        cellsHtml += '<span class="text-explode">|</span>';
                        cellsHtml += '<a href="javascript:void(0);" ms-click="goRouterPage()">路由管理</a>'; 
                        cellsHtml += '<span class="text-explode">|</span>';
                        cellsHtml += '<a href="javascript:void(0);" ms-click="eidtCustomNetwork('+row+')">编辑</a>';
                        cellsHtml += '<span class="text-explode">|</span>';
                        cellsHtml += '<a href="javascript:void(0);" ms-click="removePn('+row+')">删除</a>';
                        return cellsHtml;
                    }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"}
            ]
        });
    }

    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "vpc";
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.vpc-network';
        };

        $ctrl.$onRendered = function () {

            pintuer.init();
            pnMgt.initModelData();
            initGrid();
        }

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [pnMgt];

    });
});