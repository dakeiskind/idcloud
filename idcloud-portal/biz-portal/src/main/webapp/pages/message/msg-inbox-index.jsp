<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
    <table border="0" height="100%" cellspacing="0" cellpadding="2">
        <tr>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;消息类型：</td>
            <td>
                <div id="search-inbox-type"></div>
            </td>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;是否已读：</td>
            <td>
                <div id="search-inbox-read-flag"></div>
            </td>
            <td align="right" nowrap="nowrap">发件时间：</td>
            <td>
                <div id='search-inbox-start-time'></div>
            </td>
            <td>至</td>
            <td>
                <div id='search-inbox-end-time'></div>
            </td>
            <td><a data-bind="click:searchAlarmConsole" id="search-inbox-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
        </tr>
    </table>
</div>
<div style="width:98%;height:80%;margin-left:1%;">
    <div id='msgInboxDategrid' style="width:100%;height:450px;"></div>
</div>
<script type="text/javascript">
    function initMsgInboxPageJs(){
        return
    }
</script>

