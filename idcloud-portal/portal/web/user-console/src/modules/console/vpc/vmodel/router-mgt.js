define(["app-modules/console/vpc/services/router-mgt",
        "lib/jquery/pintuer",
        'app-utils/jqx/window',
        'app-utils/variableService',
        'app-utils/messageBoxService',
        'app-utils/codeService',
        'app-utils/httpService',
        'app-utils/validatorService',
        'validator',
        "app-utils/$extendService"
       ], function  (routerService,pintuer,window,variable,messageBox,code,http) {
    
    var routerMgt = avalon.define({
        $id:'routerMgt',
        title:'路由器',
        editRouterData:null,
        addPortData:null,
        editPortData:null,
        selectedRouter: {},
        portRulesName:"",

        regionData:[],
        regionValue:"",
        zoneData:[],
        zoneValue:"",
        initData: function(){
            // 区域数据
            routerMgt.regionData = code.getCustomCode("/topologys","POST",{resTopologyType:"R"});
            routerMgt.regionValue = routerMgt.regionData[0].resTopologySid;
            // 分区数据
            routerMgt.zoneData = code.getCustomCode("/topologys","POST",{parentTopologySid:routerMgt.regionValue,resTopologyType:"RZ"});
            routerMgt.zoneValue = routerMgt.zoneData[0].resTopologySid;
        },
        refreshclick:function(){
            initRouterGrid();
        },
        addRouter:function(){ // 新增路由
            window.initWindow({
                title: "创建路由器", 
                url: variable.app_modules+"/console/vpc/views/router-add.html",
                btn: ['保存', '取消'],
                area: '500px',
                confirm:function(){
                    if($("#addRouterForm").valid()){
                        var router = avalon.vmodels.routerAddWindow.addRouterData;
                        var data = {
                            zone: routerMgt.zoneValue,
                            routerName: router.routerName
                        };
                        http.AjaxRequest({
                            url :"/rest/networks/router",
                            type: "POST",
                            params: data,
                            async: true,
                            callback : function (data) {
                                initRouterGrid();
                            }
                        });
                        return true;
                    }
                    return false;
                }
            });
        },
        editRouter:function(row){ // 编辑路由

            window.initWindow({
                title: "编辑路由器", 
                url: variable.app_modules+"/console/vpc/views/router-edit.html",
                btn: ['保存', '取消'],
                area: '500px',
                confirm:function(){
                    if($("#editRouterForm").valid()){
                        var data = avalon.vmodels.routerEditWindow.editData;
                        http.AjaxRequest({
                            url :"/rest/networks/router/update",
                            type: "POST",
                            params: data,
                            async: true,
                            callback : function (data) {
                                initRouterGrid();
                            }
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                    var gridData = $("#routerGrid").ptGrid("getrowdata",row);
                    routerMgt.editRouterData={
                        resRouterSid: gridData.resRouterSid,
                        routerName:gridData.routerName
                    };

                }
            });
        },
        removeRouter:function(row){ // 删除路由
            messageBox.confirm({
                message:"您确定要删除该路由器吗？",
                callback:function(){
                    var gridData = $("#routerGrid").ptGrid("getrowdata",row);
                    var data = {
                        zone: routerMgt.zoneValue,
                        resRouterSid: gridData.resRouterSid
                    };
                    http.AjaxRequest({
                        url :"/rest/networks/router/delete",
                        type: "POST",
                        params: data,
                        async: true,
                        callback : function (data) {
                            initRouterGrid();
                        }
                    });
                }
            });
        },
        // 跳转到路由器管理页面
        routerManagement:function(row){
            // 端口转发数据

            var gridData = $("#routerGrid").ptGrid("getrowdata",row);
            routerMgt.selectedRouter = gridData;
            routerMgt.portRulesName = gridData.routerName;

            var data = routerMgt.getPortsData(gridData.routerId);

            $("#portRulesGrid").ptGrid("setdata",data);
            $('#portRulesGrid').ptGrid("refreshfilterrow");

            // 传输数据
            routerMgt.addPortData = {
                zoneSid: routerMgt.zoneValue,
                routerName:gridData.routerName,
                resRouterSid: gridData.resRouterSid,
                routerId:gridData.routerId
            };

            $("#routerContainer").addClass("hidden");
            $("#routerContainer").removeClass("show");
            $("#routerPort").removeClass("hidden");
            $("#routerPort").addClass("fadein-right");
        },
        backList:function(){ // 隐藏转发规则页面

            $("#routerPort").addClass("hidden");
            $("#routerPort").removeClass("show");
            $("#routerContainer").removeClass("hidden");
            $("#routerContainer").addClass("fadein-left");
        },
        getPortsData: function(routerId){ // 根据路由器SID查询端口数据
            var portsData = [];
            http.AjaxRequest({
                url :"/rest/networks/ports/"+routerId,
                type: "GET",
                async: false,
                callback : function (data) {
                    portsData = data;
                }
            });
            return portsData;
        },
        addPortsWindow: function(){ // 添加端口
            window.initWindow({
                title: "添加端口", 
                url: variable.app_modules+"/console/vpc/views/router-rule-add.html",
                btn: ['保存', '取消'],
                area: '500px',
                confirm:function(){
                    if($("#addPortsForm").valid()){
                        var addPortsData = avalon.vmodels.addPortsWindow.addRuleData;
                        var subnetSid = avalon.vmodels.addPortsWindow.subnetValue;
                        var data = {
                            zone: addPortsData.zoneSid,
                            resRouterSid: addPortsData.resRouterSid,
                            portName: addPortsData.portName,
                            resNetworkSid: subnetSid
                        };
                        var routerId = routerMgt.selectedRouter.routerId;
                        http.AjaxRequest({
                            url :"/rest/networks/attach/router",
                            type: "POST",
                            params: data,
                            async: true,
                            callback : function() {
                                var data = routerMgt.getPortsData(routerId);
                                $("#portRulesGrid").ptGrid("setdata",data);
                                $('#portRulesGrid').ptGrid("refreshfilterrow");
                            }
                        });
                        return true;
                    }
                    return false;
                }
            });
        },
        editPortsWindow:function(row){
            window.initWindow({
                title: "编辑端口", 
                url: variable.app_modules+"/console/vpc/views/router-rule-edit.html",
                btn: ['保存', '取消'],
                area: '500px',
                confirm:function(){
                    if($("#editPortsForm").valid()){
                        var data = avalon.vmodels.portsEditWindow.editPortData;
                        var routerId = routerMgt.selectedRouter.routerId;
                        http.AjaxRequest({
                            url :"/rest/networks/ports/update",
                            type: "POST",
                            params: data,
                            async: false,
                            callback : function () {
                                var data = routerMgt.getPortsData(routerId);
                                $("#portRulesGrid").ptGrid("setdata",data);
                                $('#portRulesGrid').ptGrid("refreshfilterrow");
                            }
                        });

                        return true;
                    }
                    return false;
                },
                callback:function(){
                    var gridData = $("#portRulesGrid").ptGrid("getrowdata",row);
                    routerMgt.editPortData={
                        portName: gridData.portName,
                        resPortSid: gridData.resPortSid
                    };

                }
            });
        },
        removePortWindow:function(row){ // 删除端口
             messageBox.confirm({
                message:"您确定要删除该端口吗？",
                callback:function(){
                    var gridData = $("#portRulesGrid").ptGrid("getrowdata",row);
                    var data = {
                        zone: routerMgt.zoneValue,
                        resRouterSid: routerMgt.selectedRouter.resRouterSid,
                        resNetworkSid: gridData.subnetId
                    };

                    http.AjaxRequest({
                        url :"/rest/networks/dettach/router",
                        type: "POST",
                        params: data,
                        async: true,
                        callback : function() {
                            var data = routerMgt.getPortsData(routerId);
                            $("#portRulesGrid").ptGrid("setdata",data);
                            $('#portRulesGrid').ptGrid("refreshfilterrow");
                        }
                    });
                }
            });
        },
        search:function(){
            messageBox.msgNotification({
                type:"success",
                message:"查询成功!"
            });
        }
    });

    //初始路由grid
    var initRouterGrid = function(){
        var json = {
            zoneSid: routerMgt.zoneValue,
        };
        $("#routerGrid").ptGrid({
            sortable: true,
            controller: routerMgt,
            data:{
                url:"/rest/networks/router?"+ $.param(json),
                type : "GET",
            },
            columns: [
                { text: '路由器ID', datafield: 'routerId'},
                { text: '路由器名称', datafield: 'routerName'},
                { text: '状态', datafield: 'statusName'},
                { text: '创建时间', datafield: 'createdDt'},
                { text: '操作', datafield: '',sortable: false,width:130,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                        var cellsHtml = "";
                        cellsHtml += '<a href="javascript:void(0);" ms-click="routerManagement('+row+')">管理</a>';
                        cellsHtml += '<span class="text-explode">|</span>';
                        cellsHtml += '<a href="javascript:void(0);" ms-click="editRouter('+row+')">编辑</a>';
                        cellsHtml += '<span class="text-explode">|</span>';
                        cellsHtml += '<a href="javascript:void(0);" ms-click="removeRouter('+row+')">删除</a>';
                        return cellsHtml;
                    }
                }
            ],
            toolbars:[
               {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
               {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
               {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
               {id: "searchSel",type:"select",align:"right",data:[{name:"全部",value:""},{name:"路由名称",value:"1"},{name:"所属网络",value:"2"}]}
            ]
        });
    }

    //初始端口转发规则grid
    var initPortRulesGrid = function(){
        $("#portRulesGrid").ptGrid({
            sortable: true,
            controller: routerMgt,
            data:{
                localData:[]
            },
            columns: [
                { text: '名称', datafield: 'portName'},
                { text: '固定IP', datafield: 'ipAddress'},
                { text: '类型', datafield: 'deviceOwner'},
                { text: '状态', datafield: 'statusName'},
                { text: '描述', datafield: 'description'},
                { text: '操作', datafield: '',sortable: false,width:130,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                        var cellsHtml = "";
                        cellsHtml += '<a href="javascript:void(0);" ms-click="editPortsWindow('+row+')">编辑</a>';
                        cellsHtml += '<span class="text-explode">|</span>';
                        cellsHtml += '<a href="javascript:void(0);" ms-click="removePortWindow('+row+')">删除</a>';
                        return cellsHtml;
                    }
                }
            ],
            toolbars:[
               {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
               {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
               {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
               {id: "searchSel",type:"select",align:"right",data:[{name:"全部",value:""},{name:"名称",value:"1"},{name:"状态",value:"2"}]}
            ]
        });
    }

    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "vpc";
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.vpc-router';
        };

        $ctrl.$onRendered = function () {
            // 初始化数据
            routerMgt.initData();

            initRouterGrid();
            initPortRulesGrid();
            pintuer.init();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [routerMgt];

    });
});