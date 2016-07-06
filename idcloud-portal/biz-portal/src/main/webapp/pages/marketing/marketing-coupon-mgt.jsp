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
    <script type="text/javascript" src="${ctx}/js/marketing/marketing-coupon-mgt.js"></script>
</head>
<body>
<div style="width:100%;height:30px;padding:5px 0px 5px 0px;">
    <table  border="0" height="100%" cellspacing="0" cellpadding="2">
        <tr>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;用户级别:</td>
            <td><div  id="userLevelCodeAndNameSearch" ></div></td>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;用户群体:</td>
            <td><div id="userGroupCodeAndNameSearch" ></div></td>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;分发状态:</td>
            <td><div id="distributeStatusSearch"></div></td>
            <td><a id="searchBtn"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
        </tr>
    </table>
</div>
<div style="width:99%;height:98%;margin-left:0.5%;overflow-y:auto;">
<div id='mygrid'></div>
</div>
<!-- 弹出窗口 -->
<div id="addWindow">
    <div>
        <i class='icons-blue icon-plus-3'></i>添加
    </div>
    <div class="jqxwindowbody">
        <table class="table_01">
            <tr>
                <td class="table_name_s">有效期开始:</td>
                <td class="w150"><div id="addvalidStartDt"></div></td>
                <td class="table_name_s">有效期截至:</td>
                <td class="w200"><div id="addvalidToDt"></div></td>
            </tr>
            <tr>
                <td class="table_name_s">折扣率:</td>
                <td class="w150"><input type="text" id="adddiscountRate"></td>
                <td class="table_name">用户级别:</td>
                <td class="w200"><div id="adduserLevel"></div></td>
            </tr>
            <tr>
                <td class="table_name">用户群体:</td>
                <td class="w150"><div id="adduserGroup"></div></td>
                <td class="table_name_s">分发渠道:</td>
                <td class="w200"><div id="adddistributeChannel"></div></td>
            </tr>
            <tr>
                <td class="table_name">备注:</td>
                <td colspan="3"><textarea id="addremarks"></textarea></td>
            </tr>
            <tr>
                <td align="right" colspan="4">
                    <input style="margin-right: 5px;" type="button" id="saveAdd" value="保存" />
                    <input id="returnAdd" type="button" value="取消" /></td>
            </tr>
        </table>
    </div>
</div>

<!-- 弹出窗口 -->
<div id="modifyWindow">
    <div>
        <i class="icons-blue icon-edit"></i>编辑
    </div>
    <div class="jqxwindowbody">
        <table class="table_01">
            <tr style="display: none;">
                <td>sid</td>
                <td><input type="text" id="modcouponSid" /></td>
                <td>distributeStatus</td>
                <td><input type="text" id="modDistributeStatus" /></td>
            </tr>
            <tr>
                <td class="table_name_s">编辑:</td>
                <td colspan="3"><input type="text" id="modcouponCode" /></td>
            </tr>
            <tr>
                <td class="table_name_s">有效期开始:</td>
                <td class="w150"><div id="modvalidStartDt"></div></td>
                <td class="table_name_s">有效期截至:</td>
                <td class="w200"><div id="modvalidToDt"></div></td>
            </tr>
            <tr>
                <td class="table_name_s">折扣率:</td>
                <td class="w150"><input type="text" id="moddiscountRate"></td>
                <td class="table_name">用户级别:</td>
                <td class="w200"><div id="moduserLevel"></div></td>
            </tr>
            <tr>
                <td class="table_name">用户群体:</td>
                <td class="w150"><div id="moduserGroup"></div></td>
                <td class="table_name_s">分发渠道:</td>
                <td class="w200"><div id="moddistributeChannel"></div></td>
            </tr>
            <tr>
                <td class="table_name">备注:</td>
                <td colspan="3"><textarea id="modremarks"></textarea></td>
            </tr>
            <tr>
                <td align="right" colspan="4">
                    <input style="margin-right: 5px;" type="button" id="saveModifyBtn" value="保存" />
                    <input id="returnModifyBtn" type="button" value="取消" /></td>
            </tr>
        </table>
    </div>
</div>

<!-- 弹出窗口 -->
<div class="jqxwindow" id="distributeWindow">
    <div>分发</div>
    <div>
        <table class="table_01" border="0" cellspacing="0" cellpadding="5">
            <tr style="display: none;">
                <td align="right" nowrap="nowrap" width="100">:</td>
                <td><input type="text" id="addDistributeCouponSid" /></td>
            </tr>

            <tr>
                <td class="table_name_s">分发渠道:</td>
                <td><div id="addDistributeChannel"></div></td>
            </tr>

            <tr>
                <td colspan="2" align="center">
                    <input type="button" class="button_02" value="保存" id='saveDistributeBtn' />
                    <input type="button" class="button_02" value="返回" id='returnDistributeBtn' />
                </td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
