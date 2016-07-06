define(["app-modules/user-center/userMgt/services/user-sub",
    "lib/jquery/pintuer",
    'app-utils/jqx/window',
    'app-utils/variableService',
    'app-utils/messageBoxService',
     'app-utils/httpService',
    'app-utils/validatorService',
    'validator',
    "app-utils/$extendService"], function  (subUserService,pintuer,window,variable,messageBox,http) {

    var subUser = avalon.define({
        $id:'subUser',
        title:'子用户',
        editSubData:null,
        addSub:function(){
            window.initWindow({
                title: "新建成员",
                url: variable.app_modules+"/user-center/userMgt/views/user-sub-add.html",
                btn: ['添加', '取消'],
                area: ['500px', '380px'],
                confirm:function(){
                    if($("#addSubUserForm").valid()){
                        var obj = avalon.vmodels.subUserAdd.addSubUserData;
                        console.log(JSON.stringify(obj));
                        subUserService.add(obj);
                        initGrid();
                        return true;
                    }
                    return false;
                },
                callback:function(){

                }

            });
        },
        edit:function(row){
            window.initWindow({
                title: "编辑成员",
                url: variable.app_modules+"/user-center/userMgt/views/user-sub-edit.html",
                btn: ['确定', '取消'],
                area: ['500px', '380px'],
                confirm:function(){
                    if($("#editSubForm").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message:"编辑成员成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                    var gridData = $("#subUserGrid").ptGrid("getrowdata",row);
                    subUser.editSubData={
                        account:gridData.account,
                        email:gridData.email,
                        mobile:gridData.mobile
                    };
                }
            });
        },
        remove:function(row){
            var gridData = $("#subUserGrid").ptGrid("getrowdata",row);
            messageBox.confirm({
                message:"您确定要删除该用户成员吗？",
                callback:function() {
                    console.log(gridData.userSid);
                    http.AjaxRequest({
                                         url:"/rest/user/deleteUser?userSids="+gridData.userSid,
                                         type:"get",
                                         callback:function(data){
                                             initGrid();
                                             if (data){
                                                 messageBox.msgNotification({
                                                                                type:"success",
                                                                                message:"删除用户成功"
                                                                            });
                                             }
                                         }
                                     });

                }
            });
        },
        search:function(){
            if($("#searchSel").val()==1){
                searchObj.account = $("#searchText").val();
                searchObj.statusName = "";
            }else if($("#searchSel").val()==2){
                searchObj.account = "";
                searchObj.statusName = $("#searchText").val();
            }
            $("#subUserGrid").ptGrid("applyfilters");
            $('#subUserGrid').ptGrid("refreshfilterrow");
        },
        refreshclick:function(){
           initGrid();
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功!"
            });
        }
    });

    var searchObj = {
        account:null,
        statusName:null
    };


    //初始grid
    var initGrid = function(){
        $("#subUserGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: subUser,
            data:{
                url: "/rest/user/findTenantUsers",
                type: 'POST',
                params: searchObj
            },
            columns: [
                { text: '登陆邮箱', datafield: 'email'},
                { text: '账号', datafield: 'account'},
                { text: '电话', datafield: 'mobile'},
                { text: '公司信息', datafield: 'tenantName'},
                { text: '状态',datafield:'statusName'},
                { text: '操作', datafield: '',sortable: false,width:190,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    if (rowdata.account != variable.currentUser.account ){
                        cellsHtml += '<a href="javascript:void(0);" ms-click="edit('+row+')">编辑成员</a>';
                        cellsHtml += '<span class="text-explode">|</span>';
                        cellsHtml += '<a href="javascript:void(0);" ms-click="remove('+row+')">删除成员</a>';
                    }
                    return cellsHtml;
                }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},

                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},

                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},

                {id: "searchSel",type:"select",align:"right",data:[{name:"账号",value:"1"},{name:"状态",value:"2"}]}

            ]
        });
    }

    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.userContainer.secondNavFlag = "account"
            avalon.vmodels.userContainer.navSelectedFlag = 'user.account-subuser';
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

        $ctrl.$vmodels = [subUser];

    });
});
