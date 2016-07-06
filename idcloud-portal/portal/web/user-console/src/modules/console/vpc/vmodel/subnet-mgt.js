define(['lib/jquery/pintuer',
        'app-utils/jqx/window',
        'app-utils/variableService',
        'app-utils/messageBoxService',
        'app-utils/codeService',
        'app-utils/httpService',
        'app-utils/validatorService',
        'validator',
        'app-utils/$extendService'
       ], function  (pintuer,window,variable,messageBox,code,http) {
    
    var subnetMgt = avalon.define({
        $id:'subnetMgt',
        title:'子网',
        eidtNetworkData:null,
        regionData:[],
        regionValue:"",
        zoneData:[],
        zoneValue:"",
        initModelData: function(){
            // 区域数据
            subnetMgt.regionData = code.getCustomCode("/topologys","POST",{resTopologyType:"R"});
            subnetMgt.regionValue = subnetMgt.regionData[0].resTopologySid;
            // 分区数据
            subnetMgt.zoneData = code.getCustomCode("/topologys","POST",{parentTopologySid:subnetMgt.regionValue,resTopologyType:"RZ"});
            subnetMgt.zoneValue = subnetMgt.zoneData[0].resTopologySid;
        },
        regionChanged: function(){
            subnetMgt.zoneData = code.getCustomCode("/topologys","POST",{parentTopologySid:subnetMgt.regionValue,resTopologyType:"RZ"});
            subnetMgt.zoneValue = subnetMgt.zoneData[0].resTopologySid;
        },
        zoneChanged: function(){
            // 重新查询值
            alert(subnetMgt.zoneValue);
        },
        refreshclick:function(){
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        addSubNetwork:function(){
            window.initWindow({
                title: "创建子网", 
                url: variable.app_modules+"/console/vpc/views/subnet-add.html",
                btn: ['保存', '取消'],
                area: '500px',
                confirm:function(){
                    if($("#addSubnetForm").valid()){
                        var obj = {
                            zone: subnetMgt.zoneValue,
                            subNet:{}
                        };

                        var data = avalon.vmodels.subnetAddWindow.addSubnetData;
                        var cidr = $("#addSubnetForm .hidden").val()+$("#addSubnetForm .cidr").val()+".0/24";
                        var vpcSid = $("#vpcSegmentInSubnet option:selected").attr("data-sid");
                        data.vpcSid = vpcSid;
                        data.subNetCidr = cidr;
                        obj.subNet = data;

                        http.AjaxRequest({
                            url :"/rest/networks/subnet",
                            type: "POST",
                            params: obj,
                            async: true,
                            callback : function (data) {
                                // 关闭新增window
                                layer.closeAll();
                                initGrid();
                            }
                        });
                        return true;
                    }
                    return false;
                }
            });
        },
        eidtSubNetwork:function(row){

            window.initWindow({
                title: "编辑子网", 
                url: variable.app_modules+"/console/vpc/views/subnet-edit.html",
                btn: ['保存', '取消'],
                area: '500px',
                confirm:function(){
                    if($("#editSubnetForm").valid()){
                        var data = avalon.vmodels.subnetEditWindow.editSubnetData;
                        http.AjaxRequest({
                            url :"/rest/networks/subnet/update",
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
                    var gridData = $("#subnetGrid").ptGrid("getrowdata",row);
                    subnetMgt.editSubnetData={
                        resNetworkSid: gridData.resNetworkSid,
                        networkName:gridData.networkName,
                        description:gridData.description
                    };
                }
            });
        },
        removeSubnet:function(row){
            messageBox.confirm({
                message:"您确定要删除该子网吗？",
                callback:function(){
                    // TODO 支持多个子网同时删除
                    var gridData = $("#subnetGrid").ptGrid("getrowdata",row);
                    var resNetworkSids = [];
                    resNetworkSids.push(gridData.resNetworkSid);
                    var data = {
                        zone: gridData.zone,
                        subNetSids: resNetworkSids
                    }
                    http.AjaxRequest({
                        url :"/rest/networks/subnet/delete",
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
        search:function(){
            messageBox.msgNotification({
                type:"success",
                message:"查询成功!"
            });
        }
    });


    //初始grid
    var initGrid = function(){
        var json = {
            zoneSid: subnetMgt.zoneValue,
        };
        $("#subnetGrid").ptGrid({
            sortable: true,
            controller: subnetMgt,
            data:{
                url:"/rest/networks/subnet?"+ $.param(json),
                type : "GET",
            },
            columns: [
                { text: '子网ID/名称', datafield: 'networkName'},
                { text: '所属网络', datafield: 'vpcName'},
                { text: '云主机数量', datafield: 'hostCounts'},
                { text: '网段', datafield: 'subnet'},
                { text: '可用IP数', datafield: 'ipUseCount'},
                { text: '创建时间', datafield: 'createdDt'},
                { text: '描述', datafield: 'description'},
                { text: '操作', datafield: '',sortable: false,width:90,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                        var cellsHtml = "";
                        cellsHtml += '<a href="javascript:void(0);" ms-click="eidtSubNetwork('+row+')">编辑</a>';
                        cellsHtml += '<span class="text-explode">|</span>';
                        cellsHtml += '<a href="javascript:void(0);" ms-click="removeSubnet('+row+')">删除</a>';
                        return cellsHtml;
                    }
                }
            ],
            toolbars:[
               {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
               {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchSel",type:"select",align:"right",data:[{name:"全部",value:""},{name:"子网名称",value:"1"},{name:"所属网络",value:"2"}]}
            ]
        });
    }

    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "vpc";
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.vpc-subnet';
        };

        $ctrl.$onRendered = function () {
            pintuer.init();
            subnetMgt.initModelData();
            initGrid();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [subnetMgt];

    });
});