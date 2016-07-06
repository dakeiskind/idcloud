<%@ page contentType="text/html; charset=UTF-8" language="java"  pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/pages/common/taglibs.jsp"%>
    <%@ include file="/pages/common/resources.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
    <script type="text/javascript" src="${ctx}/js/marketing/billing-deposit-prize-model.js"></script>
    <style type="text/css">
        table{
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
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;充值奖励名称：</td>
            <td><input type="text" id="search-title" />&nbsp;&nbsp;</td>
            <td align="right" nowrap="nowrap">有效开始时间：</td>
            <td> <div id='search-validStartDtStr'></div></td>
            <td>至</td>
            <td><div id='search-validToDtStr'></div></td>
            <td><a onclick="searchDepositPrizeInfo()" id="search-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
        </tr>
    </table>
</div>
<div style="width:98%;height:90%;margin-left:1%;">
    <div id='depositPrizedatagrid' style="width:100%;height:450px;"></div>
</div>
<%--添加--%>
<div  id="addDpWindow">
    <div><i class='icons-blue icon-plus-3'></i>添加</div>
    <div  id="addDpForm" style="overflow: auto">
        <table width="100%" cellspacing="5" cellpadding="0" border="0">
            <tr >
                <td align="right" style="width: 20%"><span style="color: red">*</span>充值奖励名称：</td>
                <td align="left"><input type="text" data-name="title" id="add-title"></td>
            </tr>
            <tr >
                <td align="right"><span style="color: red">*</span>有效期开始:</td>
                <td><div  data-name="validStartDt"  id="add-validStartDt"></div></td>
                <td align="right"><span style="color: red">*</span>有效期截至:</td>
                <td><div data-name="validToDt"  id="add-validToDt"></div></td>
            </tr>
            <tr >
                <td align="right"><span style="color: red">*</span>充值额度1:</td>
                <td><input type="text" data-name="minDeposit1" id="add-minDeposit1" ></td>
                <td align="right"><span style="color: red">*</span>奖励金额1:</td>
                <td><input  data-name="cashGiven1"  id="add-cashGiven1"></td>
            </tr>
            <tr >
                <td align="right" ><span style="color: red">*</span>充值额度2:</td>
                <td><input  data-name="minDeposit2"  id="add-minDeposit2" ></td>
                <td align="right"><span style="color: red">*</span>奖励金额2:</td>
                <td><input  data-name="cashGiven2"  id="add-cashGiven2" ></td>
            </tr>
            <tr >
                <td align="right"><span style="color: red">*</span>充值额度3:</td>
                <td><input  data-name="minDeposit3"  id="add-minDeposit3" ></td>
                <td align="right"><span style="color: red">*</span>奖励金额3:</td>
                <td><input data-name="cashGiven3" id="add-cashGiven3" ></td>
            </tr>
            <tr >
                <td align="right">描述:</td>
                <td align="left" colspan="3"><textarea rows="5" date-name="des"  id="add-desc"></textarea></td>
            </tr>
            <tr >
                <td colspan="4" align="center" style="margin-top: 10px">
                    <input style="margin-right: 5px" type="button"  value="确定"	 id="add-save-dp" onclick="addDpInfoSubmit()" />
                    <input type="button" value="取消 " id='add-cancle-dp' />
                </td>
            </tr>
        </table>
    </div>
</div>
<%-- 编辑 --%>
<div  id="editDpWindow">
    <div><i class="icons-blue icon-edit"></i>编辑</div>
    <div id="editDpForm" style="overflow: auto">
        <input type="hidden" data-name="depositPrizeSid" id="edit-sid" />
        <table width="100%" cellspacing="5" cellpadding="0" border="0">
            <%--&lt;%&ndash;<tr style="display: none;">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>sid</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td><input type="text" id="depositPrizeSid" /></td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
            <%--<tr style="display: none;">--%>
                <%--<td>version</td>--%>
                <%--<td><input type="text" id="version" /></td>--%>
            <%--</tr>--%>
            <tr>
                <td align="right"><span style="color: red">*</span>充值奖励名称:</td>
                <td align="left"><input  data-name="title" id="edit-title" ></td>
            </tr>
            <tr>
                <td align="right"><span style="color: red">*</span>有效期开始:</td>
                <td align="left"><div data-name="validStartDtStr" id="edit-validStartDt"></div></td>
            </tr>
            <tr>
                <td align="right" ><span style="color: red">*</span>有效期截至:</td>
                <td align="left"><div data-name="validToDtStr" id="edit-validToDt"></div></td>
            </tr>
            <tr >
                <td align="right"><span style="color: red">*</span>充值额度1:</td>
                <td align="left"><input data-name="minDeposit1" id="edit-minDeposit1" ></td>
            </tr>
            <tr >
                <td align="right"><span style="color: red">*</span>奖励金额1:</td>
                <td align="left"><input data-name="cashGiven1" id="edit-cashGiven1" ></td>
            </tr>
            <tr >
                <td align="right"><span style="color: red">*</span>充值额度2:</td>
                <td align="left"><input data-name="minDeposit2" id="edit-minDeposit2" ></td>
            </tr>
            <tr >
                <td align="right"><span style="color: red">*</span>奖励金额2:</td>
                <td align="left"><input data-name="cashGiven2" id="edit-cashGiven2" ></td>
            </tr>
            <tr >
                <td align="right"><span style="color: red">*</span>充值额度3:</td>
                <td align="left"><input data-name="minDeposit3" id="edit-minDeposit3" ></td>
            </tr>
            <tr >
                <td align="right"><span style="color: red">*</span>奖励金额3:</td>
                <td><input data-name="cashGiven3" id="edit-cashGiven3" ></td>
            </tr>
            <tr >
                <td align="right">描述:</td>
                <td align="left"><textarea rows="5"  data-name="des"  id="edit-desc"></textarea></td>
            </tr>
            <tr >
                <td colspan="4" align="center" style="margin-top: 10px">
                    <input style="margin-right: 5px" type="button" value="确定" id='edit-saveDpBtn' onclick="editDpInfoSubmit()" />
                    <input type="button" value="取消 " id='edit-cancleDpBtn' />
                </td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    //初始化deprize页面的model
    var dep = new depPrizeModel();
    //初始化页面组件
    dep.initInput();
    //初始化验证规则
    dep.initValidator();
    //初始化datagrid
    dep.initDeprizeDatagrid();
    //初始化toolbar按钮状态
    dep.initOperationBtn();
    //初始化弹出Window
    dep.initPopWindow();

</script>
