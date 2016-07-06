define(['lib/jquery/pintuer',
    'app-modules/cloud-app/appManage/services/app-management',
    'app-utils/jqx/window',
    'app-utils/variableService',
    'app-utils/messageBoxService',
    'app-utils/validatorService',
    'validator',
    "app-utils/$extendService"], function(pintuer,appManagementService,window,variable,messageBox) {

    var appManagement = avalon.define({
        $id:'appManagement',
        title:"已购买的应用",
        detailData:{},
        editData:{},

        refreshClick: function(){
            initGrid();
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        showAppOrder:function(row){
            //TODO
        },
        removeClk: function(){
            messageBox.confirm({
                message:"您确定要删除选中的密钥吗？",
                callback:function(){
                    var messages = [];
                    var selectObj = $("#appManagementGrid").ptGrid("getselectedrow");
                    for(var msg in selectObj){
                        messages.push(selectObj[msg].resKeypairsSid);
                    }
                    if(appManagementService.deleteSSHs(messages)){
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
        appManageDetail:function (row) {
            window.initWindow({
                title: " 应用管理",
                url: variable.app_modules+"/console/host/views/ssh-key-detail.html",
                btn: ['关闭'],
                area: ['550px', '380px'],
                callback:function(){
                    var rowDate = $("#appManagementGrid").ptGrid("getrowdata",row);
                    avalon.vmodels.appManagement.detailData = rowDate;
                }
            });
        }
    });

    var searchObj = {
        appName:"",
        mgtObjSid:variable.currentUser.mgtObjSid
    };

    var initGrid = function(){
        $("#appManagementGrid").ptGrid({
            sortable: true,
            controller: appManagement,
            data: {
                url: "/rest/keypairs/findKeypairs",
                //url: "/rest/app/displayCloudApp",
                type: 'POST',
                params: searchObj
            },
            columns: [
                { text: '云应用名称', datafield: 'productName' ,width: 300},
                { text: '状态', datafield: 'statusName',width: 160},
                { text: '到期时间', datafield: 'endTime',width: 140},
                { text: '评价', datafield: 'appraise',width:140},
                { text: '操作', datafield: 'operation',width:100,align: 'center'
                    ,cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="appManageDetail(' + row + ')">管理</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="showAppOrder(' + row + ')">关联订单</a>';
                    return cellsHtml;
                    }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshClick()"},
                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchSel",type:"select",align:"right",data:[{name:"应用名称",value:"1"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#appManagementGrid").ptGrid("getselectedrow");
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
            avalon.vmodels.appContainer.secondNavFlag = "cloudapp";
            avalon.vmodels.appContainer.navSelectedFlag = 'app.cloudapp-management';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
            initGrid();
            avalon.scan(0,appManagement);
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };
        $ctrl.$vmodels = [appManagement];
    });

});