define(['app-modules/user-center/customerSer/services/user-gd',
    'app-utils/jqx/window',
    'lib/jquery/pintuer',
    'app-utils/messageBoxService',
    'app-utils/variableService',
    'app-utils/$extendService'], function(userGdService,window,pintuer,messageBox,variable) {

    var userGd = avalon.define({
        $id:'userGd',
        title:"工单管理",
        detailData:{},
        index:0,
        search:function(){
            if($("#searchSel").val()==1){
                searchObj.issueTitle = $("#searchText").val();
                searchObj.issueTypeName = "";
            }else if($("#searchSel").val()==2){
                searchObj.issueTitle = "";
                searchObj.issueTypeName = $("#searchText").val();
            }
            initGrid();
            $('#word_order_grid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"查询成功!"
            });
        },
        refreshclick:function(){
            $('#word_order_grid').ptGrid("refreshfilterrow");
            initGrid();
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功!"
            });
        },
        detail:function(row){
            var rowData = $("#word_order_grid").ptGrid("getrowdata",row);
            window.initWindow({
                title: "工单详情",
                url: variable.app_modules+"/user-center/customerSer/views/gd-detail.html",
                area: ['800px', '540px'],
                callback:function(){
                    avalon.vmodels.userGd.detailData = rowData;
                }
            });
        },
        cancelGd:function(row){
            var rowData = $("#word_order_grid").ptGrid("getrowdata",row);
            var issue={};
            issue.issueSid = rowData.issueSid;
            issue.issueStatus = '04';
            messageBox.confirm({
                message:"您确定要取消工单吗？",
                callback:function(){
                    userGdService.cancelGd(issue);
                    initGrid();
                }
            });
        },
        add:function(){
            window.initWindow({
                title: "提交工单",
                url: variable.app_modules+"/user-center/customerSer/views/gd-add.html",
                btn: ['保存', '取消'],
                area: ['500px', '520px'],
                confirm:function(){
                    var t = avalon.vmodels.addGd.data;
                    if($("#gdFrom").valid()){
                        userGdService.createdGd(t);
                        initGrid();
                        return true;
                    }
                    return false;
                },
                callback:function(){

                }
            });
        }
    });

    var searchObj = {
        issueTitle:"",
        issueTypeName:""
    };

    //初始grid
    var initGrid = function(){
        $("#word_order_grid").ptGrid({
            selectionmode:"singlerow",
            sortable: true,
            controller: userGd,
            data:{
                url: "/rest/issue/find",
                type: 'post',
                params: searchObj
            },
            columns: [
                { text: '问题编号', datafield: 'issueSid', width:120},
                { text: '标题', datafield: 'issueTitle', width:250},
                { text: '问题类型', datafield: 'issueTypeName'},
                { text: '处理进度', datafield: 'issueStatusName'},
                { text: '提交时间', datafield: 'createdDt'},
                { text: '操作', datafield: 'operation' ,width: 100, align: 'center'
                    ,cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="detail(' + row + ')">查看</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    if(rowdata.issueStatus == 03 || rowdata.issueStatus == 04){
                        cellsHtml += '<a href="javascript:void(0);" class="text-gray">取消工单</a>';
                    }else {
                        cellsHtml += '<a href="javascript:void(0);" ms-click="cancelGd(' + row + ')">取消工单</a>';
                    }
                    return cellsHtml;
                }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchSel",type:"select",align:"right",data:[{name:"标题",value:"1"},{name:"问题类型",value:"2"}]}
            ]
        });
    };

    return avalon.controller(function ($ctrl) {

        $ctrl.$onEnter = function (param, rs, rj) {
            // 进入视图
            avalon.vmodels.userContainer.secondNavFlag = "service";
            avalon.vmodels.userContainer.navSelectedFlag = 'user.service-issue';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
            initGrid();
            avalon.scan(0,userGd);
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };
        $ctrl.$vmodels = [userGd];
    });

});
