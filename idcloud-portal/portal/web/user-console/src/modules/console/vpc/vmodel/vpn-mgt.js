define(["app-modules/console/vpc/services/vpn-mgt",
    "lib/jquery/pintuer",
    'app-utils/jqx/window',
    'app-utils/variableService',
    'app-utils/messageBoxService',
    'app-utils/validatorService',
    'validator',
    "app-utils/$extendService"
], function  (vpnMgtService,pintuer,window,variable,messageBox) {

    var VPNMgt = avalon.define({
        $id:'VPNMgt',
        title:'虚拟专用网络',
        editIKEData:null,
        editIPSecData:null,
        editNodeData:null,
        addIKEBtnClick:function(){
            window.initWindow({
                title: "添加IKE策略",
                url: variable.app_modules+"/console/vpc/views/ike-add.html",
                btn: ['确定', '取消'],
                area: ['500px', '380px'],
                confirm:function(){
                    if($("#addIKEForm").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message:"添加IKE策略成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){

                }
            });
        },
        addIPSecBtnClick:function(){
            window.initWindow({
                title: "添加IPSec策略",
                url: variable.app_modules+"/console/vpc/views/ipsec-add.html",
                btn: ['确定', '取消'],
                area: ['500px', '380px'],
                confirm:function(){
                    if($("#addIPSecForm").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message:"添加IPSec策略成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){

                }
            });
        },
        addVPNBtnClick:function(){
            window.initWindow({
                title: "添加VPN服务",
                url: variable.app_modules+"/console/vpc/views/vpn-add.html",
                btn: ['确定', '取消'],
                area: ['500px', '300px'],
                confirm:function(){
                    if($("#addVPNForm").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message:"添加VPN服务成功成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){

                }
            });
        },
        addNodeBtnClick:function(){
            window.initWindow({
                title: "添加IPSec站点链接",
                url: variable.app_modules+"/console/vpc/views/node-add.html",
                btn: ['确定', '取消'],
                area: ['500px', '380px'],
                confirm:function(){
                    if($("#addNodeForm").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message:"添加IPSec站点链接成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){

                }
            });
        },
        editIKE:function(row){
            window.initWindow({
                title: "编辑IKE策略",
                url: variable.app_modules+"/console/vpc/views/ike-edit.html",
                btn: ['保存', '取消'],
                area: ['500px', '380px'],
                confirm:function(){
                    if($("#editIKEForm").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message:"编辑IKE策略成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                    var gridData = $("#ikeGrid").ptGrid("getrowdata",row);
                    VPNMgt.editIKEData={
                        IKEName:gridData.IKEName,
                        IKEdesc:gridData.IKEdesc,
                        IKEAlgorithm:gridData.IKEAlgorithm,
                        IKEEncryption:gridData.IKEEncryption,
                        IKEPfs:gridData.IKEPfs
                    };
                }

            });
        },
        editIPSec:function(row){
            window.initWindow({
                title: "编辑IPSec策略",
                url: variable.app_modules+"/console/vpc/views/ipsec-edit.html",
                btn: ['保存', '取消'],
                area: ['500px', '380px'],
                confirm:function(){
                    if($("#editIPSecForm").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message:"编辑IPSec策略成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                    var gridData = $("#ipsecGrid").ptGrid("getrowdata",row);
                    VPNMgt.editIPSecData={
                        IPSecName:gridData.IPSecName,
                        IPSecdesc:gridData.IPSecdesc,
                        IPSecAlgorithm:gridData.IPSecAlgorithm,
                        IPSecEncryption:gridData.IPSecEncryption,
                        IPSecPfs:gridData.IPSecPfs
                    };
                }

            });
        },
        editNode:function(row){
            window.initWindow({
                title: "编辑IPSec站点链接",
                url: variable.app_modules+"/console/vpc/views/node-edit.html",
                btn: ['保存', '取消'],
                area: ['500px', '380px'],
                confirm:function(){
                    if($("#editNodeForm").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message:"编辑IPSec站点链接成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                    var gridData = $("#ipsecNodeGrid").ptGrid("getrowdata",row);
                    VPNMgt.editNodeData={
                        ipsNodeName:gridData.nodeName,
                        nodeDesc:gridData.nodeDesc,
                        vpnName:gridData.vpnName,
                        nodeIKE:gridData.nodeIKE,
                        nodeIPSec:gridData.nodeIPSec,
                        nodeStatus:gridData.nodeStatus
                    };
                }

            });
        },
        removeIKEClick:function(row){
            messageBox.confirm({
                message:"您确定要删除该IKE策略吗？",
                callback:function(){
                    $("#ikeGrid").ptGrid("removeRow");
                    $('#ikeGrid').ptGrid("refreshfilterrow");
                    messageBox.msgNotification({
                        type:"success",
                        message:"删除IKE策略成功!"
                    });
                }
            });
        },
        removeIPSecClick:function(row){
            messageBox.confirm({
                message:"您确定要删除该IPSec策略吗？",
                callback:function(){
                    $("#ipsecGrid").ptGrid("removeRow");
                    $('#ipsecGrid').ptGrid("refreshfilterrow");
                    messageBox.msgNotification({
                        type:"success",
                        message:"删除IPSec策略成功!"
                    });
                }
            });
        },
        removeVPNClick:function(row){
            messageBox.confirm({
                message:"您确定要删除该VPN服务吗？",
                callback:function(){
                    $("#vpnGrid").ptGrid("removeRow");
                    $('#vpnGrid').ptGrid("refreshfilterrow");
                    messageBox.msgNotification({
                        type:"success",
                        message:"删除VPN服务成功!"
                    });
                }
            });
        },
        removeNodeClick:function(row){
            messageBox.confirm({
                message:"您确定要删除该IPSec站点链接吗？",
                callback:function(){
                    $("#ipsecNodeGrid").ptGrid("removeRow");
                    $('#ipsecNodeGrid').ptGrid("refreshfilterrow");
                    messageBox.msgNotification({
                        type:"success",
                        message:"删除IPSec站点链接成功!"
                    });
                }
            });
        },
        refreshIKEClick:function(){
            $("#ikeGrid").ptGrid("applyfilters");
            $('#ikeGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        refreshIPSecClick:function(){
            $("#ipsecGrid").ptGrid("applyfilters");
            $('#ipsecGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        refreshVPNClick:function(){
            $("#vpnGrid").ptGrid("applyfilters");
            $('#vpnGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        refreshNodeClick:function(){
            $("#ipsecNodeGrid").ptGrid("applyfilters");
            $('#ipsecNodeGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        searchIKEClick:function(){
            if($("#searchIKESel").val() == 0){
                searchIKEObj.IKEName=$("#searchIKEText").val();
                searchIKEObj.IKEPfs="";
            }else{
                searchIKEObj.IKEPfs=$("#searchIKEText").val();
                searchIKEObj.IKEName="";
            }
            $("#ikeGrid").ptGrid("applyfilters");
            $('#ikeGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"查询成功!"
            });
        },
        searchIPSecClick:function(){
            if($("#searchIPSecSel").val() == 0){
                searchIPSecObj.IPSecName=$("#searchIPSecText").val();
                searchIPSecObj.IPSecPfs="";
            }else{
                searchIPSecObj.IPSecPfs=$("#searchIPSecText").val();
                searchIPSecObj.IPSecName="";
            }
            $("#ipsecGrid").ptGrid("applyfilters");
            $('#ipsecGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"查询成功!"
            });
        },
        searchVPNClick:function(){
            if($("#searchVPNSel").val() == 0){
                searchVPNObj.VPNName=$("#searchVPNText").val();
                searchVPNObj.VPNStatus="";
            }else{
                searchVPNObj.VPNStatus=$("#searchVPNText").val();
                searchVPNObj.VPNName="";
            }
            $("#vpnGrid").ptGrid("applyfilters");
            $('#vpnGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"查询成功!"
            });
        },
        searchNodeClick:function(){
            if($("#searchNodeSel").val() == 0){
                searchNodeObj.nodeName=$("#searchNodeText").val();
                searchNodeObj.nodeStatus="";
            }else{
                searchNodeObj.nodeStatus=$("#searchNodeText").val();
                searchNodeObj.nodeName="";
            }
            $("#ipsecNodeGrid").ptGrid("applyfilters");
            $('#ipsecNodeGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"查询成功!"
            });
        }
    });

    //初始化ike策略搜索对象
    var searchIKEObj = {
        IKEName:"",
        IKEPfs:""
    };

    //初始化IPSec策略搜索对象
    var searchIPSecObj = {
        IPSecName:"",
        IPSecPfs:""
    };

    //初始化VPN服务搜索对象
    var searchVPNObj = {
        VPNName:"",
        VPNStatus:""
    };

    //初始化IPSec站点链接搜索对象
    var searchNodeObj = {
        nodeName:"",
        nodeStatus:""
    };

    //初始化grid
    var initGrid = function(){
        $("#ikeGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: VPNMgt,
            data:{
                localData: vpnMgtService.getIkeGridData(),
                params: searchIKEObj
            },
            columns: [
                { text: '名称', datafield: 'IKEName'},
                { text: '描述', datafield: 'IKEdesc'},
                { text: '授权算法', datafield: 'IKEAlgorithm'},
                { text: '加密算法', datafield: 'IKEEncryption'},
                { text: 'PFS',datafield:'IKEPfs'},
                { text: '操作', datafield: '',sortable: false,width:190,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="editIKE('+row+')">编辑IKE策略</a>';
                    return cellsHtml;
                }
                }
            ],
            toolbars:[
                {id: "refreshIKEBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshIKEClick()"},
                {id:"addIKEBtn",name:"添加IKE策略",type:"button",icon:"icon-plus",click:"addIKEBtnClick()"},
                {id: "removeIKEBtn",name:"删除",type:"button",icon:"icon-minus",disabled:true,click:"removeIKEClick()"},
                {id: "searchIKEBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"searchIKEClick()"},
                {id: "searchIKEText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchIKESel",type:"select",align:"right",data:[{name:"名称",value:"0"},{name:"PFS",value:"1"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#ikeGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#removeIKEBtn").removeAttr("disabled");
                }else{
                    $("#removeIKEBtn").attr("disabled","disabled");
                }
            }
        });
        $("#ipsecGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: VPNMgt,
            data:{
                localData: vpnMgtService.getIPSecGridData(),
                params: searchIPSecObj
            },
            columns: [
                { text: '名称', datafield: 'IPSecName'},
                { text: '描述', datafield: 'IPSecdesc'},
                { text: '授权算法', datafield: 'IPSecAlgorithm'},
                { text: '加密算法', datafield: 'IPSecEncryption'},
                { text: 'PFS',datafield:'IPSecPfs'},
                { text: '操作', datafield: '',sortable: false,width:190,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="editIPSec('+row+')">编辑IPSec策略</a>';
                    return cellsHtml;
                }
                }
            ],
            toolbars:[
                {id: "refreshIPSecBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshIPSecClick()"},
                {id:"addIPSecBtn",name:"添加IPSec策略",type:"button",icon:"icon-plus",click:"addIPSecBtnClick()"},
                {id: "removeIPSecBtn",name:"删除",type:"button",icon:"icon-minus",disabled:true,click:"removeIPSecClick()"},
                {id: "searchIPSecBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"searchIPSecClick()"},
                {id: "searchIPSecText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchIPSecSel",type:"select",align:"right",data:[{name:"名称",value:"0"},{name:"PFS",value:"1"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#ipsecGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#removeIPSecBtn").removeAttr("disabled");
                }else{
                    $("#removeIPSecBtn").attr("disabled","disabled");
                }
            }
        });
        $("#vpnGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: VPNMgt,
            data:{
                localData: vpnMgtService.getVPNGridData(),
                params: searchVPNObj
            },
            columns: [
                { text: '名称', datafield: 'VPNName'},
                { text: '描述', datafield: 'VPNDesc'},
                { text: '本地段公共IP', datafield: 'publicIP'},
                { text: '子网', datafield: 'subNet'},
                { text: '路由',datafield:'VPNRouter'},
                { text: '状态',datafield:'VPNStatus'}
                //{ text: '操作', datafield: '',sortable: false,width:190,align:"center"
                //    , cellsrenderer:function (row,rowdata) {
                //    var cellsHtml = "";
                //    cellsHtml += '<a href="javascript:void(0);" ms-click="editVPN('+row+')">编辑IPSec策略</a>';
                //    return cellsHtml;
                //}
                //}
            ],
            toolbars:[
                {id: "refreshVPNBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshVPNClick()"},
                {id:"addVPNBtn",name:"添加VPN服务",type:"button",icon:"icon-plus",click:"addVPNBtnClick()"},
                {id: "removeVPNBtn",name:"删除",type:"button",icon:"icon-minus",disabled:true,click:"removeVPNClick()"},
                {id: "searchVPNBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"searchVPNClick()"},
                {id: "searchVPNText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchVPNSel",type:"select",align:"right",data:[{name:"名称",value:"0"},{name:"状态",value:"1"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#vpnGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#removeVPNBtn").removeAttr("disabled");
                }else{
                    $("#removeVPNBtn").attr("disabled","disabled");
                }
            }
        });
        $("#ipsecNodeGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: VPNMgt,
            data:{
                localData: vpnMgtService.getIPSecNodeData(),
                params: searchNodeObj
            },
            columns: [
                { text: '名称', datafield: 'nodeName'},
                { text: '描述', datafield: 'nodeDesc'},
                { text: 'VPN服务', datafield: 'vpnName'},
                { text: 'IKE策略', datafield: 'nodeIKE'},
                { text: 'IPSec策略',datafield:'nodeIPSec'},
                { text: '状态',datafield:'nodeStatus'},
                { text: '操作', datafield: '',sortable: false,width:190,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="editNode('+row+')">编辑站点链接</a>';
                    return cellsHtml;
                }
                }
            ],
            toolbars:[
                {id: "refreshIKEBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshNodeClick()"},
                {id:"addNodeBtn",name:"添加IPSec站点链接",type:"button",icon:"icon-plus",click:"addNodeBtnClick()"},
                {id: "removeNodeBtn",name:"删除",type:"button",icon:"icon-minus",disabled:true,click:"removeNodeClick()"},
                {id: "searchNodeBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"searchNodeClick()"},
                {id: "searchNodeText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchNodeSel",type:"select",align:"right",data:[{name:"名称",value:"0"},{name:"状态",value:"1"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#ipsecNodeGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#removeNodeBtn").removeAttr("disabled");
                }else{
                    $("#removeNodeBtn").attr("disabled","disabled");
                }
            }
        });
    }


    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "vpc";
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.vpc-vpn';
        };

        $ctrl.$onRendered = function () {
            initGrid();
            pintuer.init();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [VPNMgt];

    });
});
