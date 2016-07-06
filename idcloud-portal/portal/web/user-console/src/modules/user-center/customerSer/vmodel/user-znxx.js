define(['app-modules/user-center/customerSer/services/user-znxx',
        'lib/jquery/pintuer',
        'app-utils/jqx/window',
        'app-utils/messageBoxService',
        'app-utils/variableService',
        'app-utils/httpService',
        'app-utils/$extendService'
        ], function(userZnxxService,pintuer,window,messageBox,variable,http) {

    var userZnxx = avalon.define({
        $id:'userZnxx',
        title:"站内消息",
        editData:null,
        index:0,
        detail:function(row){
            var rowData = $("#messageGrid").ptGrid("getrowdata",row);
            var title = rowData.title;
            var messageType = rowData.msgTypeName;
            var createdDt = rowData.sendDate;
            var znxxDetail = rowData.msgContent;
            window.initWindow({
                title: title,
                url: variable.app_modules+"/user-center/customerSer/views/znxx-detail.html",
                btn: ['关闭'],
                area: ['650px', '500px'],
                callback:function(){
                    var data = [
                        {messageType:messageType,createdDt:createdDt, znxxDetail:znxxDetail}
                    ];
                    userZnxx.editData = data[userZnxx.index];
                }
            });
        },
        refreshclick:function(){
            initGrid();
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        removeClk:function(){
            messageBox.confirm({
                message:"您确定要删除选中消息吗？",
                callback:function(){
                    var messages = [];
                    var selectObj = $("#messageGrid").ptGrid("getselectedrow");
                    for(var msg in selectObj){
                        messages.push(selectObj[msg].msgSid);
                    }
                    http.AjaxRequest({
                        type: 'POST',
                        url : "/rest/message/deleteMessages",
                        params : messages,
                        callback : function (data) {
                        }
                    });
                    $("#messageGrid").ptGrid("removeRow");
                    $("#messageGrid").ptGrid("applyfilters");
                    $('#messageGrid').ptGrid("refreshfilterrow");
                }
            });
        },
        //将选中的行标为已读
        signReadBtn:function(){
            messageBox.confirm({
                message:"您确定将选中消息标为已读吗？",
                callback:function(){
                    var messages = [];
                    var selectObj = $("#messageGrid").ptGrid("getselectedrow");
                    for(var msg in selectObj){
                        var obj = new Object();
                        obj.msgSid = selectObj[msg].msgSid;
                        obj.readFlag = "01";
                        messages.push(obj);
                    }
                    http.AjaxRequest({
                        type: 'POST',
                        url : "/rest/message/updateMessages",
                        params : messages,
                        callback : function (data) {
                            initGrid();
                        }
                    });
                    $("#messageGrid").ptGrid("applyfilters");
                    $('#messageGrid').ptGrid("refreshfilterrow");
                }
            });
        },
        //将选中的行标为未读
        signUnreadBtn:function(){
            messageBox.confirm({
                message:"您确定将选中消息标为未读吗？",
                callback:function(){
                    var messages = [];
                    var selectObj = $("#messageGrid").ptGrid("getselectedrow");
                    for(var msg in selectObj){
                        var obj = new Object();
                        obj.msgSid = selectObj[msg].msgSid;
                        obj.readFlag = "02";
                        messages.push(obj);
                    }
                    http.AjaxRequest({
                        type: 'POST',
                        url : "/rest/message/updateMessages",
                        params : messages,
                        callback : function (data) {
                            initGrid();
                        }
                    });
                    $("#messageGrid").ptGrid("applyfilters");
                    $('#messageGrid').ptGrid("refreshfilterrow");
                }
            });
        },
        searchAll:function(){
            searchObj.msgType = "";
            $('#messageGrid').ptGrid("refreshfilterrow");
        },
        searchNotice:function(){
            searchObj.msgType = "01";
            $('#messageGrid').ptGrid("refreshfilterrow");
        },
        searchUpdate:function(){
            searchObj.msgType = "02";
            $('#messageGrid').ptGrid("refreshfilterrow");
        },
        searchFailure:function(){
            searchObj.msgType = "03";
            $('#messageGrid').ptGrid("refreshfilterrow");
        }
    });

    var searchObj = {
        msgType: ""
    }

    var initGrid = function(){
        $("#messageGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: userZnxx,
            data: {
                url: "/rest/message/findByPage",
                type: 'GET',
                params: searchObj
            },
            columns: [
                { text: '标题内容', datafield: 'msgTitle'},
                { text: '消息类型', datafield: 'msgTypeName',width: 150},
                { text: '标记', datafield: 'readFlagName',width: 150},
                { text: '创建时间', datafield: 'sendDate',width: 200},
                { text: '操作', datafield: 'operation', sortable: false, width: 50, align: "center"
                    , cellsrenderer: function (row, rowdata) {
                    var cellsHtml = "";
                    if(rowdata.readFlag == '01'){
                        cellsHtml += '<a style="color:purple" href="javascript:void(0);" ms-click="detail(' + row + ')">查看详情</a>';
                    }else{
                        cellsHtml += '<a href="javascript:void(0);" ms-click="detail(' + row + ')">查看详情</a>';
                    }
                    return cellsHtml;
                    }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
                {id: "removeBtn",name:"删除",type:"button",icon:"icon-trash-o",disabled:true,click:"removeClk()"},
                {id: "signReadBtn",name:"标为已读",type:"button",disabled:true,click:"signReadBtn()"},
                {id: "signUnreadBtn",name:"标为未读",type:"button",disabled:true,click:"signUnreadBtn()"},

                {id: "searchFailure",name:"故障通知",type:"button",align:"right",class:"button radius-none button-small",click:"searchFailure()"},
                {id: "searchUpdate",name:"产品更新",type:"button",align:"right",class:"button radius-none button-small",click:"searchUpdate()"},
                {id: "searchNotice",name:"公告",type:"button",align:"right",class:"button radius-none button-small",click:"searchNotice()"},
                {id: "searchAll",name:"全部",type:"button",align:"right",class:"button radius-none button-small",click:"searchAll()"}
            ],
            rowselect:function(){
                var selectDatas = $("#messageGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#removeBtn").removeAttr("disabled");
                    $("#signReadBtn").removeAttr("disabled");
                    $("#signUnreadBtn").removeAttr("disabled");
                }else{
                    $("#removeBtn").attr("disabled","disabled");
                    $("#signReadBtn").attr("disabled","disabled");
                    $("#signUnreadBtn").attr("disabled","disabled");
                }
            }
        });
    }

    return avalon.controller(function ($ctrl) {

        $ctrl.$onEnter = function (param, rs, rj) {
            // 进入视图
            avalon.vmodels.userContainer.secondNavFlag = "service"
            avalon.vmodels.userContainer.navSelectedFlag = 'user.service-notifications';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
            initGrid();
            avalon.scan(0,userZnxx);
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };
        $ctrl.$vmodels = [userZnxx];
    });

});