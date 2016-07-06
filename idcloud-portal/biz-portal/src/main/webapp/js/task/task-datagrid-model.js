var taskDatagridModel = function () {
    var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
        "qm.taskType": null,
        "qm.taskStatus": null
    };
    // 用户数据
    this.searchTaskInfo = function () {
        $("#taskDatagrid").jqxGrid("gotopage", 0);
        var dataAdapter = Core.getPagingDataAdapter({
                                                        gridId: "taskDatagrid",
                                                        url: ws_url + "/rest/tasks/findTaskLog",
                                                        params: me.searchObj
                                                    });
        $("#taskDatagrid").jqxGrid({
                                       source: dataAdapter,
                                       rendergridrows: function () {
                                           return dataAdapter.records;
                                       }
                                   });
    };

    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initTaskInput = function () {
        // 初始化查询组件
        $("#search-task-button").jqxButton({width: '100px', height: '25px', theme: currentTheme});
        // 初始化下拉列表框
        var codesearch = new codeModel({width: 150, autoDropDownHeight: true});
        codesearch.getCommonCode("search-task-status", "TASK_STATUS", true);
        codesearch.getCommonCode("search-taskType", "TASK_TYPE", true);
    };

    // 初始化用户datagrid的数据源
    this.initTaskDatagrid = function () {
        var dataAdapter = Core.getPagingDataAdapter({
                                                        gridId: "taskDatagrid",
                                                        url: ws_url + "/rest/tasks/findTaskLog",
                                                        params: me.searchObj
                                                    });
        $("#taskDatagrid").jqxGrid({
                                       width: "100%",
                                       theme: currentTheme,
                                       source: dataAdapter,
                                       virtualmode: true,
                                       rendergridrows: function () {
                                           return dataAdapter.records;
                                       },
                                       columnsresize: true,
                                       pageable: true,
                                       pagesize: 10,
                                       autoheight: true,
                                       autowidth: false,
                                       sortable: true,
                                       selectionmode: "singlerow",
                                       localization: gridLocalizationObj,
                                       columns: [
                                           {text: '任务类型', datafield: 'taskTypeName', width: 300},
                                           {text: '任务对象', datafield: 'taskTarget', width: 300},
                                           {text: '操作人', datafield: 'operator'},
                                           {
                                               text: '状态',
                                               datafield: 'taskStatusName',
                                               align: 'center',
                                               width: 200,
                                               cellsalign: 'center',
                                               cellsrenderer: function (row, columnfield, value,
                                                                        defaulthtml,
                                                                        columnproperties) {
                                                   return "<div style='padding-top:3px;text-align:center;margin-right:auto; margin-left:auto;'><a class='datagrid-link' onclick='openTaskLogMessage()' href='#'>&nbsp;"
                                                          + value + "</a></div>";
                                               }
                                           },
                                           {text: '开始时间', datafield: 'taskStartDate', width: 180},
                                           {text: '完成时间', datafield: 'taskEndDate'}
                                       ],
                                       showtoolbar: true,
                                       // 设置toolbar操作按钮
                                       rendertoolbar: function (toolbar) {
                                           var container = $(
                                               "<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                                           var refreshBtn = $(
                                               "<div><a id='jqxRefreshTaskBtn' onclick='RefreshTaskDataGridItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-loop-alt'></i>刷新&nbsp;&nbsp;</a></div>");
                                           toolbar.append(container);
                                           container.append(refreshBtn);
                                       }
                                   });

        // 控制按钮是否可用
        $("#taskDatagrid").on('rowselect', function (event) {
            var args = event.args;
            var index = args.rowindex;
            var data = $('#taskDatagrid').jqxGrid('getrowdata', index);

        });
        $("#jqxRefreshTaskBtn").jqxButton({width: '60', theme: currentTheme, height: '18px'});
    };
};
//刷新虚拟机模板
function RefreshTaskDataGridItem() {
    var task = new taskDatagridModel();
    task.searchTaskInfo();
}

// 查询镜像模板
function searchTaskInfo() {
    var task = new taskDatagridModel();
    task.searchObj["qm.taskType"] =
        $("#search-taskType").val() == "全部" ? "" : $("#search-taskType").val();
    task.searchObj["qm.taskStatus"] =
        $("#search-task-status").val() == "全部" ? "" : $("#search-task-status").val();
    task.searchTaskInfo();
}
//查看日志状态
function openTaskLogMessage() {
    var rowindex = $('#taskDatagrid').jqxGrid('getselectedrowindex');
    var data = $('#taskDatagrid').jqxGrid('getrowdata', rowindex);
    if (data.taskStatus == "09") {
        Core.alerts({
                        title: "任务详情",
                        message: data.taskTypeName + "<br/>" + data.taskFailureReason,
                        width: 300,
                        height: 130,
                    });
    } else {
        Core.alerts({
                        title: "任务详情",
                        message: data.taskTypeName,
                        width: 300,
                        height: 130,
                    });
    }
}

Core.alerts = function (settings) {
    var title = settings.title === undefined ? "提示" : settings.title,
        message = settings.message === undefined ? "" : settings.message,
        type = settings.type === undefined ? "info" : settings.type;
    width = 600;
    height = 400;

    // 判断body中是否存在了alertWindow
    if ($("#alertWindow").length > 0) {
        $('#alertWindow').jqxWindow('destroy');
    }

    $('<div id="alertWindow" style="width:300px;height:250px;">' +
      '<div id="customWindowHeader">' +
      '<span id="captureContainer" style="float: left">' + title + '</span>' +
      '</div>' +
      '<div id="customWindowContent" style="overflow: hidden">' +
      '<div style="width:100%;height:325px;line-height:30px;position:absolute;top:30px;left:0px;overflow:auto">'
      +
      '<font style="position:absolute;left:20px;font-size:12px;">' + message + '</font></div>' +
      '<div style="float: right; margin-top: 330px">' +
      ' <input type="button" style="cursor:pointer;margin-right: 15px" value="确定" id="okButton" />'
      +
      '</div>' +
      '</div>' +
      '</div>').appendTo($("body"));
    var windowW = $(window).width();
    var windowH = $(window).height();
    $('#alertWindow').jqxWindow({
                                    position: {x: (windowW - width) / 2, y: (windowH - height) / 2},
                                    width: width,
                                    height: height,
                                    resizable: false,
                                    isModal: true,
                                    theme: currentTheme,
                                    closeButtonAction: 'close',
                                    initContent: function () {
                                        $('#okButton').jqxButton(
                                            {width: '60px', disabled: false, theme: currentTheme});
                                        $('#okButton').focus();
                                    }
                                });

    $('#alertWindow').find('#okButton').click(function () {
        $('#alertWindow').jqxWindow('destroy');
//				$('#alertWindow').jqxWindow('close');
        if (settings.callback) {
            settings.callback();
        }
    });
};
  