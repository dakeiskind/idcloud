define(["lib/jquery/pintuer",
    "app-modules/console/cloudwatch/services/alarm-contacts-mgt",
    'app-utils/jqx/window',
    'app-utils/messageBoxService',
    'app-utils/variableService',
    'app-utils/validatorService',
    "app-utils/$extendService"], function(pintuer,contactsService,window,messageBox,variable,validate){
    var alarmContacts = avalon.define({
        $id:"alarmContacts",
        title:"告警联系人",
        editData:null,
        addContactsClick:function(){
            alarmContacts.editData = null;
            window.initWindow({
                title: "新建告警联系人",
                url: variable.app_modules+"/console/cloudwatch/views/alarm-contacts-add.html",
                btn: ['确定', '取消'],
                area: ['480px', '370px'],
                confirm:function(){
                    if($("#createAlarmContactsFrom").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message:"提交成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){

                }
            });
        },
        addGroupClick:function(){
            alarmContacts.editData = null;
            window.initWindow({
                title: "新建告警联系组",
                url: variable.app_modules+"/console/cloudwatch/views/alarm-contacts-group-add.html",
                btn: ['确定', '取消'],
                area: ['650px', '450px'],
                confirm:function(){
                    if($("#createAlarmContactsGroupFrom").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message:"提交成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){

                }
            });
        },
        refreshFpClick:function(){
            $('#contactsListGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"info",
                message:"刷新完成!"
            });
        },
        refreshGroupClick:function(){
            $('#contactsListGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"info",
                message:"刷新完成!"
            });
        },
        removeUserClick:function(){
            messageBox.confirm({
                message:"您确定要删除选中告警联系人吗？",
                callback:function(){
                    $("#contactsListGrid").ptGrid("removeRow");
                    $('#contactsListGrid').ptGrid("refreshfilterrow");
                }
            });
        },
        edit:function(row){
            window.initWindow({
                title: "编辑告警联系人",
                url: variable.app_modules+"/console/cloudwatch/views/alarm-contacts-add.html",
                btn: ['确定', '取消'],
                area: ['480px', '370px'],
                confirm:function(){
                    if($("#createAlarmContactsFrom").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message:"提交成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                    var rowData = $("#contactsListGrid").ptGrid("getrowdata",row);
                    alarmContacts.editData = rowData;
                }
            });
        },
        remove:function(row){
            messageBox.confirm({
                message:"您确定要删除吗？",
                callback:function(){
                    $("#contactsListGrid").ptGrid("removeRow",row);
                    $('#contactsListGrid').ptGrid("refreshfilterrow");
                }
            });
        },
        editGroup:function(row){
            window.initWindow({
                title: "编辑联系组",
                url: variable.app_modules+"/console/cloudwatch/views/alarm-contacts-group-add.html",
                btn: ['确定', '取消'],
                area: ['650px', '450px'],
                confirm:function(){
                    if($("#createAlarmContactsFrom").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message:"提交成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                    var rowData = $("#contactsGroup").ptGrid("getrowdata",row);
                    alarmContacts.editData = rowData;
                }
            });
        },
        removeGroup:function(row){
            messageBox.confirm({
                message:"您确定要删除吗？",
                callback:function(){
                    $("#contactsGroup").ptGrid("removeRow",row);
                    $('#contactsGroup').ptGrid("refreshfilterrow");
                }
            });
        }
    });

    //发票列表初始化
    var initGrid = function(){
        $("#contactsListGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller:alarmContacts,
            data:{
                localData:contactsService.getData()
                //params: searchObjList
            },
            columns: [
                { text: '姓名', datafield: 'contactName'},
                { text: '手机号码', datafield: 'contactPhone'},
                { text: 'Email', datafield: 'contactEmail'},
                { text:'所属报警组',datafield:'contactGroup'},
                { text: '操作', datafield: '',sortable: false,width:90,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                        var cellsHtml = "";
                        cellsHtml += '<a href="javascript:void(0);" ms-click="edit('+row+')">编辑</a>';
                        cellsHtml += '<span class="text-explode">|</span>';
                        cellsHtml += '<a href="javascript:void(0);" ms-click="remove('+row+')">删除</a>';
                        return cellsHtml;
                    }
                }
            ],
            toolbars:[
                {id: "refreshFpBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshFpClick()"},
                {id: "addGroupBtn",name:"添加到报警联系组",type:"button",click:"addGroupClick()"},
                {id: "addGroupBtn",name:"删除联系人",type:"button",click:"removeUserClick()"},
            ]
        });

        $("#contactsGroup").ptGrid({
            sortable: true,
            controller:alarmContacts,
            data:{
                localData:contactsService.getGroupData()
                //params: searchObjList
            },
            columns: [
                { text: '联系组名', datafield: 'groupName'},
                { text: '描述', datafield: 'groupDes'},
                { text: '操作', datafield: '',sortable: false,width:90,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="editGroup('+row+')">编辑</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="removeGroup('+row+')">删除</a>';
                    return cellsHtml;
                }
                }
            ],
            toolbars:[
                {id: "refreshFpBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshGroupClick()"},
            ]
        });
    };

    var initAll = function(){
        $(".tab-head .tab-nav li").on("click",function(){
            if(this.value==0){
                $("#addContacts").addClass("show");
                $("#addContacts").removeClass("hidden");
                $("#addGroup").addClass("hidden");
                $("#addGroup").removeClass("show");
            }else{
                $("#addContacts").addClass("hidden");
                $("#addContacts").removeClass("show");
                $("#addGroup").addClass("show");
                $("#addGroup").removeClass("hidden");
            }
        });
        initGrid();
    };


    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "cloudwatch";
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.alarm-contacts-mgt';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            // require(['lib/jquery/pintuer']);
            pintuer.init();
            initAll();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [alarmContacts];

    });
});

