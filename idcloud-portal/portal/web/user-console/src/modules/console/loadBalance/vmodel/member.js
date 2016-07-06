define(['app-modules/console/loadBalance/services/member',
        'lib/jquery/pintuer',
        'app-utils/jqx/window',
        'app-utils/messageBoxService',
        'app-utils/variableService',
        "app-utils/$extendService"
], function  (memberService,pintuer,window,messageBox,variable) {
    var member = avalon.define({
        $id:'member',
        title:'成员',
        data:{
            memberID:"5be07441-8c17-4a8e-8e19-d5c090ea211e",
            projectID:"372976b4453e4d3cb11af8e0112c68f9",
            resourcePool:"",
            address:"",
            agreementPort:"",
            weight:"",
            status:""
        },
        addData:{
            resourcePool:""
        },
        modifyData:{
            memberID:"5be07441-8c17-4a8e-8e19-d5c090ea211e",
            resourcePool:"",
            weight:""
        },

        add:function(){
            window.initWindow({
                title: "新增成员",
                url: variable.app_modules+"/console/loadBalance/views/add-member.html",
                btn: ['添加','取消'],
                area: ['450px', '520px'],
                confirm:function(){
                    if($("#addMemberForm").valid()){
                        var t = avalon.vmodels.addMember.data;
                        messageBox.msgNotification({
                            type:"success",
                            message: t.resourcePool+ "添加成功!"
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
            $('#memberGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新完成!"
            });
        },
        removeClk:function(){
            messageBox.confirm({
                message:"您确定要删除选中的成员吗？",
                callback:function(){
                    $("#memberGrid").ptGrid("removeRow");
                    $("#memberGrid").ptGrid("applyfilters");
                    $('#memberGrid').ptGrid("refreshfilterrow");
                    messageBox.msgNotification({
                        type:"success",
                        message:"删除成功!"
                    });
                }
            });
        },
        search:function(){
            if($("#searchSel").val()==1){
                searchObj.IPAddress = $("#searchText").val();
                searchObj.agreementPort = ""
            }else if($("#searchSel").val()==2){
                searchObj.agreementPort = $("#searchText").val();
                searchObj.IPAddress = "";
            }
            //$("#loadbalanceGrid").ptGrid("applyfilters");
            $('#memberGrid').ptGrid("refreshfilterrow");
        },

        detail:function(row){
            var rowDate = $("#memberGrid").ptGrid("getrowdata",row);
            window.initWindow({
                title: "详情",
                url: variable.app_modules+"/console/loadBalance/views/member-detail.html",
                btn: ['关闭'],
                area: ['450px', '300px'],
                confirm:function(){
                    return true;
                },
                callback:function(){
                    member.data.resourcePool = rowDate.resourcePool;
                    member.data.address = rowDate.IPAddress;
                    member.data.agreementPort = rowDate.agreementPort;
                    member.data.weight = rowDate.weight;
                    member.data.status = rowDate.status;
                }
            });
        },
        modify:function(row){
            var rowDate = $("#memberGrid").ptGrid("getrowdata",row);
            window.initWindow({
                title: "详情",
                url: variable.app_modules+"/console/loadBalance/views/modify-member.html",
                btn: ['保存','取消'],
                area: ['450px', '280px'],
                confirm:function(){
                    if($("#modifyMemberForm").valid()){
                        var t = avalon.vmodels.modifyMember.data;
                        messageBox.msgNotification({
                            type:"success",
                            message: t.resourcePool+ "修改成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                    member.modifyData.resourcePool = rowDate.resourcePool;
                    member.modifyData.weight = rowDate.weight;
                }
            });
        },
        deleteMember:function(row){
            messageBox.confirm({
                message:"您确定要删除吗？",
                callback:function(){
                    $("#memberGrid").ptGrid("removeRow",row);
                    $('#memberGrid').ptGrid("refreshfilterrow");
                }
            });
        }
    });

    var searchObj = {
        IPAddress:"",
        agreementPort:""
    };

    //初始grid
    var initGrid = function(){
        $("#memberGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: member,
            data:{
                localData: memberService.getGridDate(),
                params: searchObj
            },
            columns: [
                {text:'IP地址',datafield:"IPAddress",width:"200"},
                {text:"协议端口",datafield:"agreementPort"},
                {text:"重量",datafield:"weight"},
                {text:"资源池",datafield:"resourcePool"},
                {text:"状态",datafield:"status"},
                {text:"操作", datafield: '',sortable: false,width:"80",align:"center"
                    , cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="detail('+row+')">详情</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="modify('+row+')">修改</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="deleteMember('+row+')">删除</a>';
                    return cellsHtml;
                }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
                {id: "removeBtn",name:"删除",type:"button",disabled:true,click:"removeClk()"},

                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchSel",type:"select",align:"right",data:[{name:"IP地址",value:"1"},{name:"协议端口",value:"2"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#memberGrid").ptGrid("getselectedrow");
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
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.loadbalance-member';
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

        $ctrl.$vmodels = [member];

    });
});