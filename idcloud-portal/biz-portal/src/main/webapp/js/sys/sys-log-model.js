var sysLogModel = function () {
    var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
        "qm.accountLike": null,
        "qm.actNameLike": null,
        "qm.actLevelLike": null,
        "qm.actStartDateLike": null,
        "actEndDateLike": null
    };
    // 用户数据
    this.searchSyslogInfo = function () {
        var dataAdapter = Core.getPagingDataAdapter({
                                                        gridId: "sysLogdatagrid",
                                                        url: ws_url + "/rest/logs/findLogs",
                                                        params: me.searchObj
                                                    });
        $("#sysLogdatagrid").jqxGrid({
                                         source: dataAdapter,
                                         rendergridrows: function () {
                                             return dataAdapter.records;
                                         }
                                     });
    };

    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function () {
        // 初始化查询组件
        $("#search-log-account")
            .jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1, theme: currentTheme});
        $("#search-log-name")
            .jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1, theme: currentTheme});
        var log = new codeModel({width: 150, autoDropDownHeight: true});
        log.getCommonCode("operationLevel", "SYS_LOG_LEVEL", true);

        $("#sysLogFromTime").jqxDateTimeInput({
                                                  width: '120px',
                                                  culture: 'zh-CN',
                                                  formatString: 'd',
                                                  height: 22,
                                                  allowNullDate: true,
                                                  value: null,
                                                  theme: currentTheme
                                              });
        $("#sysLogToTime").jqxDateTimeInput({
                                                width: '120px',
                                                culture: 'zh-CN',
                                                formatString: 'd',
                                                height: 22,
                                                allowNullDate: true,
                                                value: null,
                                                theme: currentTheme
                                            });
        $("#search-log-button").jqxButton({width: '50px', height: '25px', theme: currentTheme});
    };

    // 初始化用户datagrid的数据源
    this.initSysLogDatagrid = function () {
        var dataAdapter = Core.getPagingDataAdapter({
                                                        gridId: "sysLogdatagrid",
                                                        url: ws_url + "/rest/logs/findLogs",
                                                        params: me.searchObj
                                                    });
        $("#sysLogdatagrid").jqxGrid({
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
                                         selectionmode: "checkbox",
                                         localization: gridLocalizationObj,
                                         columns: [
                                             //{ text: '序号', width:50, cellsalign: 'center', align:
                                             // 'center', cellsrenderer: function(row, columnfield,
                                             // value, defaulthtml, columnproperties){ return "<div
                                             // style='width:47px;height:22px;text-align:center;line-height:25px;'>"+(row+1)+"</div>";
                                             // }},
                                             {
                                                 text: '用户帐号',
                                                 datafield: 'account',
                                                 cellsalign: 'center',
                                                 align: 'center',
                                                 width: 100
                                             },
                                             {text: '操作对象', datafield: 'actTarget'},
                                             {text: '操作名称', datafield: 'actName'},
                                             {
                                                 text: '操作级别',
                                                 datafield: 'actLevelName',
                                                 cellsalign: 'center',
                                                 align: 'center',
                                                 width: 60
                                             },
                                             {text: '开始时间', datafield: 'actStartDate'},
                                             {text: '结束时间', datafield: 'actEndDate'},
                                             {
                                                 text: '操作结果',
                                                 datafield: 'actResultName',
                                                 cellsalign: 'center',
                                                 align: 'center',
                                                 width: 60
                                             },
                                             {text: '操作内容', datafield: 'actDetail'},
                                             {text: '失败原因', datafield: 'actFailureReason'}
                                         ]
                                     });
    };
};

// 查询
function searchLogInfo() {
    var log = new sysLogModel();
    log.searchObj["qm.accountLike"] = $("#search-log-account").val();
    log.searchObj["qm.actNameLike"] = $("#search-log-name").val();
    log.searchObj["qm.actLevelLike"] =
        $("#operationLevel").val() == "全部" ? "" : $("#operationLevel").val();
    log.searchObj["qm.actStartDateLike"] =
        $("#sysLogFromTime").val() == "" ? "" : ($("#sysLogFromTime").val() + " 00:00:00");
    log.searchObj["qm.actEndDateLike"] =
        $("#sysLogToTime").val() == "" ? "" : ($("#sysLogToTime").val() + " 23:59:59");

    log.searchSyslogInfo();
};