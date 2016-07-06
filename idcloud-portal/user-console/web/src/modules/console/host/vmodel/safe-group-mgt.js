define(['app-modules/console/host/services/safe-group-mgt',
        'lib/jquery/pintuer',
        'app-utils/jqx/window',
        'app-utils/messageBoxService',
        'app-utils/variableService',
        'app-utils/validatorService'], function(safeGroupService,pintuer,window,messageBox,variable) {

    var safeGroup = avalon.define({
        $id:'safeGroup',
        title:"安全组",
        editData:null,
        createSafeGroup: function(){
            window.initWindow({
                title: "创建安全组",
                url: variable.app_modules+"/console/host/views/create-safe-group.html",
                btn: ['确定', '取消'],
                area: ['480px', '350px'],
                confirm:function(){
                    if($("#createSafeGroupFrom").valid()){
                        var data = avalon.vmodels.createSafeGroup.data;
                        messageBox.msgNotification({
                            type:"success",
                            message:data.sgName+"提交安全组成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){

                }
            });
        },
        sgRuleAdd: function(){
            window.initWindow({
                title:"添加安全组规则",
                url: variable.app_modules+"/console/host/views/add-safe-group-rule.html",
                btn:['确定','取消'],
                area: ['450px','340px'],
                confirm: function(){
                    if($("#addSafeGroupRuleFrom").valid()){
                        var data = avalon.vmodels.addSafeGroupRule.data;
                        messageBox.msgNotification({
                            type:"success",
                            message:data.port+"添加安全组规则成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback: function(){

                }
            });
        },
        returnSafeGroup: function(){
            $("#configRule").addClass("hidden");
            $("#safeGroup").removeClass("hidden");
            $("#safeGroup").addClass("fadein-left");
        },

        refreshclick: function(){
            $('#safeGroupGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        removeClk: function(){
            $("#safeGroupGrid").ptGrid("removeRow");
            $('#safeGroupGrid').ptGrid("refreshfilterrow");
        },
        search: function(){
            if($("#searchSel").val()==1){
                searchObj.safe_group_id = $("#searchText").val();
                searchObj.net_type = "";
            }else if($("#searchSel").val()==2){
                searchObj.safe_group_id = "";
                searchObj.net_type = $("#searchText").val();
            }
            $('#safeGroupGrid').ptGrid("refreshfilterrow");
        },

        refreshclickEnter: function(){
            $('#enterDirectionGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功!"
            });
        },
        removeClkEnter: function(){
            $("#enterDirectionGrid").ptGrid("removeRow");
            $('#enterDirectionGrid').ptGrid("refreshfilterrow");
        },
        refreshclickExit: function(){
            $('#exitDirectionGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功!"
            });
        },
        removeClkExit: function(){
            $("#exitDirectionGrid").ptGrid("removeRow");
            $('#exitDirectionGrid').ptGrid("refreshfilterrow");
        },

        modify: function(row){
            window.initWindow({
                title: "修改安全组",
                url: variable.app_modules+"/console/host/views/modify-safe-group.html",
                btn: ['确定', '取消'],
                area: ['480px', '480px'],
                confirm:function(){
                    if($("#modifySafeGroupFrom").valid()){
                        var data = avalon.vmodels.modifySafeGroup.modifyData;
                        messageBox.msgNotification({
                            type:"success",
                            message:data.safe_group_id+"修改成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                    var rowData = $("#safeGroupGrid").ptGrid("getrowdata",row);
                    safeGroup.editData = rowData;
                }
            });
        },
        manageCloudHost: function(row){
            window.initWindow({
                title: "加入/移除云主机",
                url: variable.app_modules+"/console/host/views/manage-cloud-host.html",
                btn: ['确定', '取消'],
                area: ['600px', '500px'],
                confirm:function(){
                    messageBox.msgNotification({
                        type:"success",
                        message:"管理云主机成功!"
                    });
                    return true;
                },
                callback:function(){
                    //var rowData = $("#safeGroupGrid").ptGrid("getrowdata",row);
                }
            });
        },
        configRule: function(row){
            $("#safeGroup").addClass("hidden");
            $("#configRule").removeClass("hidden");
            var rowData = $("#safeGroupGrid").ptGrid("getrowdata",row);
            $("#sgIdName").text(rowData.safe_group_id);
        },
        removeEnter: function(row){
            $("#enterDirectionGrid").ptGrid("removeRow",row);
            $('#enterDirectionGrid').ptGrid("refreshfilterrow");
        },
        removeExit: function(row){
            $("#exitDirectionGrid").ptGrid("removeRow",row);
            $('#exitDirectionGrid').ptGrid("refreshfilterrow");
        }

    });

    var searchObj = {
        safe_group_id: "",
        net_type:""
    };

    var initGrid = function(){
        $("#safeGroupGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: safeGroup,
            data: {
                localData:safeGroupService.getGridDate(),
                params: searchObj
            },
            columns: [
                { text: '安全组ID/名称', datafield: 'safe_group_id'},
                { text: '所属专有网络', datafield: 'proprietary_net'},
                { text: '相关实例', datafield: 'instance'},
                { text: '网络类型', datafield: 'net_type'},
                { text: '创建时间', datafield: 'createDt'},
                { text: '描述', datafield: 'describe'},
                { text: '操作', datafield: 'operation', align: 'center'
                    ,cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="modify(' + row + ')">修改</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="manageCloudHost(' + row + ')">管理云主机</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="configRule(' + row + ')">配置规则</a>';
                    return cellsHtml;
                    }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
                {id: "removeBtn",name:"删除",type:"button",icon:"icon-trash-o",disabled:true,click:"removeClk()"},

                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchSel",type:"select",align:"right",data:[{name:"安全组ID/名称",value:"1"},{name:"网络类型",value:"2"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#safeGroupGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#removeBtn").removeAttr("disabled");
                }else{
                    $("#removeBtn").attr("disabled","disabled");
                }
            }
        });
        $("#enterDirectionGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: safeGroup,
            data: {
                localData:safeGroupService.getENTERDate()
            },
            columns: [
                { text: 'IP类型', datafield: 'IPType'},
                { text: '协议类型', datafield: 'agreementType'},
                { text: '端口范围', datafield: 'port'},
                { text: '授权类型', datafield: 'authorizationType'},
                { text: '授权对象', datafield: 'authorizationObject'},
                { text: '操作', datafield: 'operation', align: 'center'
                    ,cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="removeEnter(' + row + ')">删除</a>';
                    return cellsHtml;
                    }
                }
            ],
            toolbars:[
                {id: "refreshEnterBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclickEnter()"},
                {id: "removeEnterBtn",name:"删除",type:"button",icon:"icon-trash-o",disabled:true,click:"removeClkEnter()"},

            ],
            rowselect:function(){
                var selectDatas = $("#enterDirectionGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#removeEnterBtn").removeAttr("disabled");
                }else{
                    $("#removeEnterBtn").attr("disabled","disabled");
                }
            }
        });
        $("#exitDirectionGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: safeGroup,
            data: {
                localData:safeGroupService.getEXITDate()
            },
            columns: [
                { text: 'IP类型', datafield: 'IPType'},
                { text: '协议类型', datafield: 'agreementType'},
                { text: '端口范围', datafield: 'port'},
                { text: '授权类型', datafield: 'authorizationType'},
                { text: '授权对象', datafield: 'authorizationObject'},
                { text: '操作', datafield: 'operation', align: 'center'
                    ,cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="removeExit(' + row + ')">删除</a>';
                    return cellsHtml;
                }
                }
            ],
            toolbars:[
                {id: "refreshExitBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclickExit()"},
                {id: "removeExitBtn",name:"删除",type:"button",icon:"icon-trash-o",disabled:true,click:"removeClkExit()"},

            ],
            rowselect:function(){
                var selectDatas = $("#exitDirectionGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#removeExitBtn").removeAttr("disabled");
                }else{
                    $("#removeExitBtn").attr("disabled","disabled");
                }
            }
        });
    };

    return avalon.controller(function ($ctrl) {

        $ctrl.$onEnter = function (param, rs, rj) {
            // 进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "cs";
            avalon.vmodels.userContainer.navSelectedFlag = 'console.cs-securityGroup';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
            initGrid();
            avalon.scan(0,safeGroup);
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };
        $ctrl.$vmodels = [safeGroup];
    });

});