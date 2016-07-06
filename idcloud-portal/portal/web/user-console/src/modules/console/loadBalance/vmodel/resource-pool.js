/**
 * Created by Administrator on 2016/1/28.
 */
define(["app-modules/console/loadBalance/services/resource-pool",
        "lib/jquery/pintuer",
        'app-utils/messageBoxService',
        'app-utils/jqx/window',
        'app-utils/variableService',
        "app-utils/$extendService"
    ], function  (resourcePoolService,pintuer,messageBox,window,variable) {
    var loadBalanceMgt = avalon.define({
        $id:'loadBalanceMgt',
        title:'资源池',
        data:{
            name:null,
            describe:null,
            provider:"0",
            subnet:"",
            agreement:"",
            LBMode:""
        },
        modifyData:{
            sid:null,
            name:null,
            loadBalanceID:"df2a4cc1-6119-47ff-9ebe",
            describe:null,
            LBMode:""
        },
        dataDetail:{
            loadBalanceID:"df2a4cc1-6119-47ff-9ebe",
            loadBalanceName:null,
            describe:null,
            //projectID:"372976b4453e4d3cb11af8e0112c68f9",
            VIP:null,
            provider:null,
            subnet:null,
            agreement:null,
            LBMode:null,
            member:null,
            status:null
        },
        addVIPData:{
            name:null,
            describe:null,
            VIPSubnet:"",
            IPAddress:null,
            portNumber:null,
            agreement:"",
            sessionPersistence:"",
            appCookie:null,
            connectionLimit:null
        },
        bindingFloatIPData:{
            IPAddress:"",
            waitConnectionPort:""
        },

        add:function(){
            window.initWindow({
                title: "新增资源池",
                url: variable.app_modules+"/console/loadBalance/views/add-resource-pool.html",
                btn: ['添加','取消'],
                area: ['450px', '500px'],
                confirm:function(){
                    if($("#addResourcePoolForm").valid()){
                        var t = avalon.vmodels.addResourcePool.data;
                        messageBox.msgNotification({
                            type:"success",
                            message: t.name+ "添加成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                }
            });
        },
        backList:function(){
            $("#loadbalanceDetail").addClass("hidden");
            $("#loadbalanceDetail").removeClass("show");
            $("#loadbalanceLsit").removeClass("hidden");
            $("#loadbalanceLsit").addClass("fadein-left");
        },
        more:function(){
            messageBox.msgNotification({
                type:"info",
                message: "查看更多!"
            });
        },
        resetPassword:function(){
            messageBox.msgNotification({
                type:"info",
                message: "重置密码!"
            });
        },

        refreshclick:function(){
            $('#loadbalanceGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新完成!"
            });
        },
        removeClk:function(){
            messageBox.confirm({
                message:"您确定要删除选中的资源池吗？",
                callback:function(){
                    $("#loadbalanceGrid").ptGrid("removeRow");
                    $("#loadbalanceGrid").ptGrid("applyfilters");
                    $('#loadbalanceGrid').ptGrid("refreshfilterrow");
                    messageBox.msgNotification({
                        type:"success",
                        message:"删除成功!"
                    });
                }
            });
        },
        search:function(){
            if($("#searchSel").val()==1){
                searchObj.loadBalanceName = $("#searchText").val();
                searchObj.agreement = ""
            }else if($("#searchSel").val()==2){
                searchObj.agreement = $("#searchText").val().toUpperCase();
                searchObj.loadBalanceName = "";
            }
            //$("#loadbalanceGrid").ptGrid("applyfilters");
            $('#loadbalanceGrid').ptGrid("refreshfilterrow");
        },

        edit:function(row){
            var rowDate = $("#loadbalanceGrid").ptGrid("getrowdata",row);
            window.initWindow({
                title: "修改资源池",
                url: variable.app_modules+"/console/loadBalance/views/modify-resource-pool.html",
                btn: ['保存','取消'],
                area: ['450px', '380px'],
                confirm:function(){
                    if($("#modifyResourcePoolForm").valid()){
                        var t = avalon.vmodels.modifyResourcePool.data;
                        messageBox.msgNotification({
                            type:"success",
                            message: t.name+ "修改成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                    avalon.vmodels.loadBalanceMgt.modifyData.name = rowDate.loadBalanceName;
                    avalon.vmodels.loadBalanceMgt.modifyData.describe = rowDate.describe;
                    if(rowDate.LBMode == "ROUND_ROBIN"){
                        avalon.vmodels.loadBalanceMgt.modifyData.LBMode = "ROUND_ROBIN";
                    }else if(rowDate.LBMode == "LEAST_CONNECTIONS"){
                        avalon.vmodels.loadBalanceMgt.modifyData.LBMode = "LEAST_CONNECTIONS";
                    }else if(rowDate.LBMode == "SOURCE_IP"){
                        avalon.vmodels.loadBalanceMgt.modifyData.LBMode = "SOURCE_IP";
                    }

                }
            });

        },
        detail:function(row){
            var rowDate = $("#loadbalanceGrid").ptGrid("getrowdata",row);
            avalon.vmodels.loadBalanceMgt.dataDetail = rowDate;
            $("#loadbalanceLsit").addClass("hidden");
            $("#loadbalanceLsit").removeClass("show");
            $("#loadbalanceDetail").removeClass("hidden");
            $("#loadbalanceDetail").addClass("fadein-right");
        },
        addVIP:function(row){
            window.initWindow({
                title: "添加VIP",
                url: variable.app_modules+"/console/loadBalance/views/add-VIP.html",
                btn: ['添加','取消'],
                area: ['450px', '520px'],
                confirm:function(){
                    if($("#addVIPForm").valid()){
                        var t = avalon.vmodels.addVIP.data;
                        messageBox.msgNotification({
                            type:"success",
                            message: t.name+ "添加成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                }
            });
        },
        bindingFloatIP:function(row){
            window.initWindow({
                title: "管理浮动IP的关联",
                url: variable.app_modules+"/console/loadBalance/views/binding-float-IP.html",
                btn: ['关联','取消'],
                area: ['450px', '280px'],
                confirm:function(){
                    if($("#bindingFloatIPForm").valid()){
                        var t = avalon.vmodels.bindingFloatIP.data;
                        messageBox.msgNotification({
                            type:"success",
                            message: t.IPAddress+ "添加成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                }
            });
        },
        deleteResourcePool:function(row){
            messageBox.confirm({
                message:"您确定要删除吗？",
                callback:function(){
                    $("#loadbalanceGrid").ptGrid("removeRow",row);
                    $('#loadbalanceGrid').ptGrid("refreshfilterrow");
                }
            });
        }

    });

    var searchObj = {
        loadBalanceName:"",
        agreement:""
    };

    //初始grid
    var initGrid = function(){
        $("#loadbalanceGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: loadBalanceMgt,
            data:{
                localData: resourcePoolService.getGridDate(),
                params: searchObj
            },
            columns: [
                {text:'负载均衡ID/名称',datafield:"loadBalanceName",width:"130"},
                {text:"描述",datafield:"describe"},
                {text:"提供者",datafield:"provider"},
                {text:"子网",datafield:"subnet"},
                {text:"协议",datafield:"agreement"},
                {text:"LB方式",datafield:"LBMode"},
                {text:"状态",datafield:"status"},
                {text:"VIP(虚拟IP)",datafield:"VIP"},
                {text:"操作", datafield: '',sortable: false,width:"130",align:"center"
                    , cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="detail('+row+')">详情</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="edit('+row+')">修改</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<div class="button-group">';
                    cellsHtml += '<button type="button" class="button dropdown-toggle">';
                    cellsHtml += '更多<span class="downward"></span>';
                    cellsHtml += '</button>';
                    cellsHtml += '<ul class="drop-menu pull-right">';
                    cellsHtml += '<li><a href="javascript:void(0);" ms-click="addVIP('+row+')">添加VIP</a> </li>';
                    cellsHtml += '<li><a href="javascript:void(0);" ms-click="bindingFloatIP('+row+')">绑定浮动IP</a> </li>';
                    cellsHtml += '<li><a href="javascript:void(0);" ms-click="deleteResourcePool('+row+')">删除资源池</a> </li>';
                    cellsHtml += '</ul>';
                    cellsHtml += '</div>';
                    return cellsHtml;
                    }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
                {id: "removeBtn",name:"删除",type:"button",disabled:true,click:"removeClk()"},

                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchSel",type:"select",align:"right",data:[{name:"负载均衡ID/名称",value:"1"},{name:"协议",value:"2"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#loadbalanceGrid").ptGrid("getselectedrow");
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
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.loadbalance-instance';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            // require(['lib/jquery/pintuer']);
            initGrid();
            pintuer.init();
            //avalon.scan(0,consoleDisk);
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [loadBalanceMgt];

    });
});