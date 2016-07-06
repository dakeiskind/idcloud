<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/pages/common/taglibs.jsp"%>
    <%@ include file="/pages/common/resources.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=8" />
    <script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
    <script type="text/javascript">
        var batchNo=<%=request.getParameter("batchNo")%>;
        var needTitle=<%=request.getParameter("needTitle")%>;
        if(batchNo!=null){
            batchNo='<%=request.getParameter("batchNo")%>';
        }
    </script>
    <script type="text/javascript" src="${ctx}/js/marketing/marketing-gift-card-mgt.js"></script>
</head>
<body>
<div style="width:100%;height:60px;padding:5px 0px 5px 0px;">
    <table  border="0" height="100%" cellspacing="0" cellpadding="2">
        <tr>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;批次号:</td>
            <td><input type="text" id="batchNoSch" /></td>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;卡号:</td>
            <td><input type="text"  id="cardNoSch" /></td>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;礼品卡名称:</td>
            <td><input type="text"  id="griftCardName"/></td>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;充值帐号:</td>
            <td><input type="text"  id="chargedAccount"/></td>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;状态:</td>
            <td><div type="text"  id="statusSch"/></td>
        </tr>
        <tr>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;客户经理:</td>
            <td><input type="text"  id="userClient"/></td>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;充值时间:</td>
            <td><div id="validStartDtSearch"></div></td>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;至:</td>
            <td><div id="validToDtSearch"></div></td>
            <td><a id="searchBtn"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
        </tr>
    </table>
</div>
<div style="width:99%;height:98%;margin-left:0.5%;overflow-y:auto;">
    <div id='mygrid'></div>
</div>
</body>
</html>


