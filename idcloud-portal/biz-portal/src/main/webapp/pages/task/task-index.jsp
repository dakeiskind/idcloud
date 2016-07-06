<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/pages/common/taglibs.jsp" %>
    <%@ include file="/pages/common/resources.jsp" %>

    <script type="text/javascript" src="${ctx}/js/task/task-datagrid-model.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
    <style type="text/css">
        #containerOrder {
            width: 100%;
            height: 100%;
            margin: 0px;
            padding: 0px;
        }

        .searchInfoDiv {
            color: #767676;
            font-size: 12px;
    </style>
</head>
<body class='default'>
<div id="containerOrder">
    <div id='jqxWidget'>
        <div>
            <div class="searchInfoDiv"
                 style="width: 100%; height: 30px; padding: 10px 0px 10px 0px;">
                <table border="0" height="100%" cellspacing="0" cellpadding="2">
                    <tr>
                        <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;任务类型：</td>
                        <td>
                            <div id="search-taskType"></div>
                        </td>

                        <td align="right" nowrap="nowrap">状态：</td>
                        <td>
                            <div id="search-task-status"></div>
                        </td>
                        <td><a onclick="searchTaskInfo()" id="search-task-button"><i
                                class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
                    </tr>
                </table>
            </div>
            <div style="width:98%;height:80%;margin-left:1%;">
                <div id='taskDatagrid' style="width:100%;height:450px;"></div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    //初始化任务管理页面
    var task = new taskDatagridModel();
    //初始化页面组件
    task.initTaskInput();
    // 初始化datagrid
    task.initTaskDatagrid();
    //为datagrid赋值
    task.searchTaskInfo();

</script>
</html>