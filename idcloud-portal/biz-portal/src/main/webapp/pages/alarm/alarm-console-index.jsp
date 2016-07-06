<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/pages/common/taglibs.jsp"%>
        <%@ include file="/pages/common/resources.jsp"%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
        <script type="text/javascript" src="${ctx}/js/alarm/alarm-bind-model.js"></script>
        <script type="text/javascript" src="${ctx}/js/alarm/alarm-console-model.js"></script>
        <title></title>
    </head>
    <body>
        <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
            <table border="0" height="100%" cellspacing="0" cellpadding="2">
                <tr>
                    <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;告警类型：</td>
                    <td>
                        <div id="search-alarm-type"></div>
                    </td>
                    <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;告警状态：</td>
                    <td>
                        <div id="search-alarm-status"></div>
                    </td>
                    <td align="right" nowrap="nowrap">告警时间：</td>
                    <td>
                        <div id='alarmFromTime'></div>
                    </td>
                    <td>至</td>
                    <td>
                        <div id='alarmToTime'></div>
                    </td>
                    <td><a data-bind="click:searchAlarmConsole" id="search-alarm-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
                </tr>
            </table>
        </div>
        <div style="width:98%;height:80%;margin-left:1%;">
            <div id='alarmConsoledatagrid' style="width:100%;height:450px;"></div>
        </div>

        <div id="defineAlarmInfoWindow">
            <div>确定告警信息</div>
            <div id="defineAlarmInfoForm" style="overflow: hidden;">
                <input type="hidden" data-name="alarmDataSid" id="defineConsoleAlarmSid"/>
                <input type="hidden" data-name="alarmRuleSid" id="alarmRuleSid" />
                <input type="hidden" data-name="alarmId" id="alarmId" />
                <input type="hidden" data-name="alarmTimeToDate" id="alarmTime" />
                <input type="hidden" data-name="alarmLevel" id="alarmLevel" />
                <input type="hidden" data-name="alarmType" id="alarmType" />
                <input type="hidden" data-name="alarmTarget" id="alarmTarget" />
                <input type="hidden" data-name="title" id="title" />
                <input type="hidden" data-name="content" id="content" />
                <input type="hidden" data-name="status" id="status" value="02"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>告警确认人:</td>
                        <td align="left">
                            <input type="text" data-name="confirmUser" id="confirmUser"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>告警确认时间:</td>
                        <td align="left">
                            <div data-name="confirmTime" id='alarmDefineTime'></div>
                        </td>
                    </tr>
                    <tr>
                        <td width="100px" align="right" valign="top">告警确认内容:</td>
                        <td>
                            <textarea id="alarmDefineContent" data-name="confirmContent"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="right">
                            <input style="margin-right: 5px;" data-bind="click:DefineAlarmInfoSubmit" id="consoleInfoSave" type="button" value="保存" />
                            <input id="consoleInfoCancel" type="button" value="取消" />
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </body>

<script type="text/javascript">

    $(function(){

        // 初始化告警控制台js
        var conso = initAlarmConsolePageJs();
        var bindmodel = new alarmBindModel(conso,null);
        //将model和view绑定
        ko.applyBindings(bindmodel);
    });

    function initAlarmConsolePageJs(){
        // 初始化sys-index页面model
        var alarmConsole = new alarmConsoleModel();
        // 初始化页面组件
        alarmConsole.initInput();
        // 初始化弹出框
        alarmConsole.initPopWindow();
        // 初始化组件验证规则
        alarmConsole.initValidator();
        // 初始化datagrid
        alarmConsole.initAlarmConsoledatagrid();
        // 为datagrid赋值
        alarmConsole.searchAlarmListInfo();
        return alarmConsole;
    }
</script>
</html>