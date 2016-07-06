define(['lib/jquery/pintuer',
        'app-utils/messageBoxService',
        'app-utils/codeService'], function(pintuer,messageBox,codeService) {

    var userCzjl = avalon.define({
        $id:'userCzjl',
        title: "操作记录",
        exportLog:function(){
            messageBox.msgNotification({
                type:"success",
                message:"导出日志......"
            });
        },
        findLogsByParams:function(){
            searchObj.actTarget = $("#actTarget").val();
            searchObj.startTime = $("#startTime").val();
            $("#endTime").val() == "" || $("#endTime").val() == null ? $("#endTime").val() == null:
                searchObj.endTime = $("#endTime").val()+" 23:59:59";
            searchObj.actResult = $("#actResult").val();
            initGrid();
            messageBox.msgNotification({
                type:"success",
                message:"查询成功！"
            });
        }
    });

    var searchObj = {
        actTarget:null,
        startTime:null,
        endTime:null,
        actResult:null
    };

    //输入框初始
    var initInput = function(){
        $('#beginDate').datepicker({});
        $('#endDate').datepicker({});
        codeService.setOption('actTarget','ACT_TARGET');
        codeService.setOption('actResult','SYS_LOG_RESULT');
    };

    var initGrid = function(){
        $("#logGrid").ptGrid({
            selectionmode:"singlerow",
            sortable: true,
            controller: userCzjl,
            data: {
                url: "/rest/logs/findLogsByParams",
                type: 'post',
                params: searchObj
            },
            columns: [
                { text: '操作名称', datafield: 'actName' },
                { text: '操作模块', datafield: 'actTargetName' },
                { text: '客户端IP', datafield: 'opIp',width:200},
                { text: '操作时间', datafield: 'actStartDate'},
                { text: '操作结果', datafield: 'actResultName'}
            ],
            toolbars:[
            ]
        });
    }

    return avalon.controller(function ($ctrl) {

        $ctrl.$onEnter = function (param, rs, rj) {
            // 进入视图
            avalon.vmodels.userContainer.secondNavFlag = "service"
            avalon.vmodels.userContainer.navSelectedFlag = 'user.service-log';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
            initGrid();
            initInput();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };
        $ctrl.$vmodels = [userCzjl];
    });

});