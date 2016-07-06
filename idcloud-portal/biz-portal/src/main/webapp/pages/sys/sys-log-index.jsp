<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/pages/common/taglibs.jsp" %>
    <%@ include file="/pages/common/resources.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
    <script type="text/javascript" src="${ctx}/js/sys/sys-log-model.js"></script>
    <style type="text/css">
        table {
            font-size: 12px;
            font-family: '微软雅黑', 'Microsoft Yahei', 'segoe ui', arial, sans-serif;
            color: #767676;
        }
    </style>
</head>
<body>
<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
    <table border="0" height="100%" cellspacing="0" cellpadding="2">
        <tr>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;用户账号：</td>
            <td><input type="text" id="search-log-account"/>&nbsp;&nbsp;</td>
            <td align="right" nowrap="nowrap">关键字：</td>
            <td><input type="text" id="search-log-name"/>&nbsp;&nbsp;</td>
            <td align="right" nowrap="nowrap">操作级别：</td>
            <td>
                <div id='operationLevel'></div>
            </td>
            <td align="right" nowrap="nowrap">操作时间：</td>
            <td>
                <div id='sysLogFromTime'></div>
            </td>
            <td>至</td>
            <td>
                <div id='sysLogToTime'></div>
            </td>
            <td><a onclick="searchLogInfo()" id="search-log-button"><i
                    class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
        </tr>
    </table>
</div>
<div style="width:98%;height:90%;margin-left:1%;">
    <div id='sysLogdatagrid' style="width:100%;height:450px;"></div>
</div>
</body>
</html>
<script type="text/javascript">
    // 初始化sys-index页面model
    var sysLog = new sysLogModel();
    // 初始化页面组件
    sysLog.initInput();
    // 初始化datagrid
    sysLog.initSysLogDatagrid();
</script>