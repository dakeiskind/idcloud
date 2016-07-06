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
    <script type="text/javascript" src="${ctx}/js/marketing/marketing-gift-card-batch-mgt.js"></script>
</head>
<body>
<div style="width:100%;height:30px;padding:5px 0px 5px 0px;">
    <table  border="0" height="100%" cellspacing="0" cellpadding="2">
        <tr>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;批次号:</td>
            <td><input type="text" id="batchNoSch" /></td>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;卡名称:</td>
            <td><input type="text" id="cardNameSch" /></td>
            <td align="right" nowrap="nowrap">&nbsp;&nbsp;状态:</td>
            <td><div id="statusSch"></div></td>
            <td><a id="searchBtn"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
        </tr>
    </table>
</div>
<div style="width:99%;height:98%;margin-left:0.5%;overflow-y:auto;">
    <div id='mygrid'></div>
</div>
<!-- 弹出窗口 -->
<div class="jqxwindow" id="addWindow">
    <div>
        <i class='icons-blue icon-plus-3'></i>添加
    </div>
    <div class="jqxwindowbody">
        <table class="table_01">
            <tr>
                <td class="table_name_s">礼品卡名称:</td>
                <td class="w200"><input type="text" id="addcardName"></td>
            </tr>
            <tr>
                <td class="table_name_s">面额:</td>
                <td class="w200"><input type="text" id="addfaceValue">元</td>
            </tr>
            <tr>
                <td class="table_name_s">发行量:</td>
                <td class="w200"><input type="text" id="addquantity">张</td>
            </tr>

            <tr>
                <td class="table_name_s">有效期开始:</td>
                <td class="w200"><div id="addvalidStart"></div></td>
            </tr>
            <tr>
                <td class="table_name_s">有效期截至:</td>
                <td class="w200"><div id="addvalidTo"></div></td>
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
<div class="jqxwindow" id="modifyWindow">
    <div>
        <i class="icons-blue icon-edit"></i>编辑
    </div>
    <div class="jqxwindowbody">
        <table class="table_01">
            <tr style="display: none;">
                <td>sid</td>
                <td><input type="text" id="modBatchSid" /></td>
            </tr>
            <tr>
                <td class="table_name">批次号:</td>
                <td class="w200"><input type="text" id="modBatchNo" disabled="disabled"></td>

            </tr>
            <tr>
                <td class="table_name_s">礼品卡名称:</td>
                <td class="w200"><input type="text" id="modcardName"></td>
            </tr>
            <tr>
                <td class="table_name_s">面额:</td>
                <td class="w200"><input type="text" id="modfaceValue">元</td>
            </tr>发行量
            <tr>
                <td class="table_name_s">发行量:</td>
                <td class="w200"><input type="text" id="modquantity">张</td>
            </tr>

            <tr>
                <td class="table_name_s">有效期开始:</td>
                <td class="w200"><div id="modvalidStart"></div></td>
            </tr>
            <tr>
                <td class="table_name_s">有效期截至:</td>
                <td class="w200"><div id="modvalidTo"></div></td>
            </tr>
            <tr>
                <td align="right" colspan="4">
                    <input style="margin-right: 5px;" type="button" id="saveModifyBtn" value="保存" />
                    <input id="returnModifyBtn" type="button" value="取消" /></td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>


