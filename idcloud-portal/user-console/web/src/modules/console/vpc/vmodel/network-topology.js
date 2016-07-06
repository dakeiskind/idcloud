define(['app-utils/httpService',
        "app-modules/console/vpc/services/network-topology",
        'app-utils/jqx/window',
        'app-utils/variableService',
        'app-utils/codeService',
        'app-utils/validatorService',
        'validator',
        "app-utils/$extendService"
        ], function(http,network,window,variable,code) {
    
    var topology = avalon.define({
        $id:'networkTopology',
        regionData:[],
        regionValue:"",
        zoneData:[],
        zoneValue:"",
        initModelData: function(){
            topology.regionData = code.getCustomCode("/topologys","POST",{resTopologyType:"R"});
            if(topology.regionData.length > 0){
                topology.regionValue = topology.regionData[0].resTopologySid;
                topology.zoneData = code.getCustomCode("/topologys","POST",{parentTopologySid:topology.regionValue,resTopologyType:"RZ"});
                if(topology.regionData.length > 0){
                    topology.zoneValue = topology.zoneData[0].resTopologySid;
                }
            }
        },
        initTopologyPicture:function(){
            // 获取数据
            http.AjaxRequest({
                url :"/rest/networks/find/Topology/data/"+topology.zoneValue,
                type: "GET",
                async: false,
                callback : function (data) {
                    var topology = new network('topologyContainer',data);
                    topology.init();
                }
            });
        },
        regionChanged: function(){
            topology.zoneData = code.getCustomCode("/topologys","POST",{parentTopologySid:topology.regionValue,resTopologyType:"RZ"});
            if(topology.regionData.length > 0){
                topology.zoneValue = topology.zoneData[0].resTopologySid;
            }
        },
        zoneChanged: function(){
            topology.initTopologyPicture();
        },
        createRouter: function(){
            window.initWindow({
                title: "创建路由器", 
                url: variable.app_modules+"/console/vpc/views/router-add.html",
                btn: ['保存', '取消'],
                area: '500px',
                confirm:function(){
                    if($("#addRouterForm").valid()){

                        var router = avalon.vmodels.routerAddWindow.addRouterData;
                        var data = {
                            zone: topology.zoneValue,
                            routerName: router.routerName
                        };

                        http.AjaxRequest({
                            url :"/rest/networks/router",
                            type: "POST",
                            params: data,
                            async: true,
                            callback : function (data) {
                                // 刷新拓扑结构图
                                topology.initTopologyPicture();
                            }
                        });
                        return true;
                    }
                    return false;
                }
            });
        },
        createPrivateNetwork:function(){
            window.initWindow({
                title: "创建私有网络",
                url: variable.app_modules+"/console/vpc/views/pn-add.html",
                area: '500px',
            });
        }
       
    });

    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "vpc";
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.vpc-topology';
        };

        $ctrl.$onRendered = function () {
            var dh = document.documentElement.clientHeight;
            document.getElementById("topologyContainer").style.height=(dh-135)+"px";
            topology.initModelData();
            topology.initTopologyPicture();

        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [topology];

    });
});