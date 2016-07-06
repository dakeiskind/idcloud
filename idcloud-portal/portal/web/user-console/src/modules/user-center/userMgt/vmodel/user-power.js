define(['app-modules/user-center/userMgt/services/user-power',
        'lib/jquery/pintuer',
        'app-utils/jqx/window',
        'app-utils/variableService',
        'app-utils/messageBoxService',
        'app-utils/$extendService'], function(userPowerService,pintuer,window,variable,messageBox) {

    var userPower = avalon.define({
        $id:'userPower',
        title:"权限管理",
        addData:{
            roleID:null,
            roleDes:null
        },

        addNewRole:function(){
            window.initWindow({
                title: "新建角色",
                url: variable.app_modules+"/user-center/userMgt/views/user-power-add.html",
                btn: ['添加', '取消'],
                area: ['680px', '400px'],
                confirm:function(){
                    var t = avalon.vmodels.addRole.data;
                    if($("#addRoleForm").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message: t.roleID+"添加成功!"
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
            $("#roleGrid").ptGrid("applyfilters");
            $('#roleGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功!"
            });
        },
        search:function(){
            if($("#searchSel").val()==1){
                searchObj.role = $("#searchText").val();
            }
            $("#roleGrid").ptGrid("applyfilters");
            $('#roleGrid').ptGrid("refreshfilterrow");
        },

        detail:function(row){
            window.initWindow({
                title: "新建成员",
                url: variable.app_modules+"/user-center/userMgt/views/user-power-detail.html",
                btn: ['关闭'],
                area: ['680px', '400px'],
                confirm:function(){
                    return true;
                },
                callback:function(){
                    var data = $("#roleGrid").ptGrid("getrowdata",row);
                    userPower.addData.roleID = data.role;
                    userPower.addData.roleDes = data.Des;
                }
            });
        }

    });

    var searchObj = {
        role:""
    };

    //初始grid
    var initGrid = function(){
        $("#roleGrid").ptGrid({
            sortable: true,
            controller: userPower,
            data:{
                localData: userPowerService.getGridDate(),
                params: searchObj
            },
            columns: [
                { text: '角色', datafield: 'role'},
                { text: '操作', datafield: '',sortable: false,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    //cellsHtml += '<a href="javascript:void(0);" class="icon-eye" ms-click="edit('+row+')">&nbsp;查看</a>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="detail('+row+')">查看</a>';
                    return cellsHtml;
                }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},

                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},

                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},

                {id: "searchSel",type:"select",align:"right",data:[{name:"角色",value:"1"}]}

            ]
        });
    }
    return avalon.controller(function ($ctrl) {

        $ctrl.$onEnter = function (param, rs, rj) {
            // 进入视图
            avalon.vmodels.userContainer.secondNavFlag = "account"
            avalon.vmodels.userContainer.navSelectedFlag = 'user.account-privilege';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
            initGrid();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };
        $ctrl.$vmodels = [userPower];
    });

});