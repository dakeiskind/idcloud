define(['lib/jquery/pintuer',
    'app-modules/console/host/services/ssh-key',
    'app-utils/jqx/window',
    'app-utils/variableService',
    'app-utils/messageBoxService',
    'app-utils/validatorService',
    'validator',
    "app-utils/$extendService"], function(pintuer,sshKeyService,window,variable,messageBox) {

    var sshKey = avalon.define({
        $id:'sshKey',
        title:"SSH密钥",
        detailData:{},
        editData:{},

        importSshKey: function () {
            window.initWindow({
                title: " 导入",
                url: variable.app_modules+"/console/host/views/ssh-key-export.html",
                btn: ['保存', '取消'],
                area: ['540px', '360px'],
                confirm:function(){
                    if($("#ImportForm").valid()){
                        var data = avalon.vmodels.ImportSSH.data;
                        sshKeyService.ImportSSH(data);
                        messageBox.msgNotification({
                            type:"success",
                            message:"导入成功！"
                        });
                        return true;
                    }
                    return false;
                }
            });
        },
        createSshKey: function(){
            window.initWindow({
                title: "创建SSH",
                url: variable.app_modules+"/console/host/views/ssh-key-add.html",
                btn: ['保存', '取消'],
                area: ['450px', '305px'],
                confirm:function(){
                    if($("#addSshForm").valid()){
                        var params = avalon.vmodels.addSsh.data;
                        //params.mgtObjSid = variable.currentUser.mgtObjSid;
                        sshKeyService.createSSHKey(params);
                        initGrid();
                        return true;
                    }
                    return false;
                }
            });
        },

        refreshclick: function(){
            initGrid();
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        removeClk: function(){
            messageBox.confirm({
                message:"您确定要删除选中的密钥吗？",
                callback:function(){
                    var messages = [];
                    var selectObj = $("#sshKeyGrid").ptGrid("getselectedrow");
                    for(var msg in selectObj){
                        messages.push(selectObj[msg].resKeypairsSid);
                    }
                    if(sshKeyService.deleteSSHs(messages)){
                        initGrid();
                    }
                }
            });
        },
        search: function(){
            if($("#searchSel").val()==1) {
                searchObj.keypairsName = $("#searchText").val();
            }
            initGrid();
            messageBox.msgNotification({
                type:"success",
                message:"查询成功！"
            });
        },
        exportAll:function(){
            sshKeyService.exportAll();
        },

        detail: function (row) {
            window.initWindow({
                title: " SSH详情",
                url: variable.app_modules+"/console/host/views/ssh-key-detail.html",
                btn: ['关闭'],
                area: ['550px', '380px'],
                callback:function(){
                    var rowDate = $("#sshKeyGrid").ptGrid("getrowdata",row);
                    avalon.vmodels.sshKey.detailData = rowDate;
                }
            });
        },
        modifySshKey: function (row) {
            window.initWindow({
                title: " SSH密钥修改",
                url: variable.app_modules+"/console/host/views/ssh-key-edit.html",
                btn: ['保存', '取消'],
                area: ['450px', '305px'],
                confirm:function(){
                    if($("#editForm").valid()){
                        var editData = avalon.vmodels.editSSH.data;
                        if(sshKeyService.modifySSH(editData)){
                            initGrid();
                        }
                        return true;
                    }
                    return false;
                },
                callback:function(){
                    var rowDate = $("#sshKeyGrid").ptGrid("getrowdata",row);
                    var editData = {};
                    editData.resKeypairsSid = rowDate.resKeypairsSid;
                    editData.keypairsName = rowDate.keypairsName;
                    editData.description = rowDate.description;
                    avalon.vmodels.sshKey.editData = editData;
                }
            });
        }
    });

    var searchObj = {
        keypairsName:"",
        mgtObjSid:variable.currentUser.mgtObjSid
    };

    var initGrid = function(){
        $("#sshKeyGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: sshKey,
            data: {
                url: "/rest/keypairs/findKeypairs",
                type: 'POST',
                params: searchObj
            },
            columns: [
                { text: 'SSH密钥ID/名称', datafield: 'keypairsNameAndSid' ,width: 160},
                { text: '指纹', datafield: 'fingerprint',width: 300},
                { text: '创建时间', datafield: 'createdDt',width: 140},
                { text: '描述', datafield: 'description',width:400},
                { text: '操作', datafield: 'operation',width:100,align: 'center'
                    ,cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="detail(' + row + ')">详情</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="modifySshKey(' + row + ')">修改</a>';
                    return cellsHtml;
                    }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
                {id: "exportAllBtn",name:"全部导出",type:"button",disabled:true,click:"exportAll()"},
                {id: "removeBtn",name:"删除",type:"button" ,icon:"icon-trash-o",disabled:true,click:"removeClk()"},

                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchSel",type:"select",align:"right",data:[{name:"SSH密钥名称",value:"1"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#sshKeyGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#removeBtn").removeAttr("disabled");
                }else{
                    $("#removeBtn").attr("disabled","disabled");
                }
            }
        });
        if($(".tbody tr").find("td").length != '1'){
            $("#exportAllBtn").removeAttr("disabled");
        }
    }

    return avalon.controller(function ($ctrl) {

        $ctrl.$onEnter = function (param, rs, rj) {
            // 进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "cs";
            avalon.vmodels.userContainer.navSelectedFlag = 'console.cs-sshkey';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
            initGrid();
            avalon.scan(0,sshKey);
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };
        $ctrl.$vmodels = [sshKey];
    });

});