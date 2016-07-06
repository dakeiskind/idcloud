<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/pages/common/taglibs.jsp"%>
    <%@ include file="/pages/common/resources.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=8" />
    <script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
    <%--<script type="text/javascript" src="${ctx}/scripts/ajaxfileupload.js"></script>--%>
    <script type="text/javascript" src="${ctx}/js/sys/agreement-mgt-model.js"></script>
    <style type="text/css">
        table{
            font-size: 12px;
            font-family: '微软雅黑', 'Microsoft Yahei', 'segoe ui', arial, sans-serif;
            color: #767676;
        }

        .box{
            position:relative;
            float:left;
        }

        input.uploadFile{
            position:absolute;
            /* 		right:0px; */
            left: 0;
            top:0px;
            opacity:0;
            filter:alpha(opacity=0);
            cursor:pointer;
            width:280px;
            height:30px;
            overflow: hidden;
        }
        input.textbox{
            float:left;
            padding:5px;
            color:#999;
            height:18px;
            line-height:24px;
            border:1px #ccc solid;
            width:200px;
            margin-right:4px;
        }
        a.link{
            float:left;
            display:inline-block;
            padding:4px 16px;
            color:#5E5252;
            font:14px "Microsoft YaHei", Verdana, Geneva, sans-serif;
            cursor:pointer;
            background-color:#E8E7E7;
            line-height:22px;
            text-decoration:none;
            height:22.5px;
        }

    </style>
</head>
<body>
<%-- 收索栏 --%>
    <div style="width: 100%;height: 30px;padding: 5px 0px 5px 0px">
        <table border="0" height="100%" cellspacing="0" cellpadding="2">
            <tr>
                <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;客戶：</td>
                <td align="left"><input type="text" id="search-agreement-custom"></td>
                <td align="right" nowrap="nowrap">&nbsp;&nbsp;协议标题：</td>
                <td align="left"><input type="text" id="search-agreement-title">&nbsp;&nbsp;</td>
                <td align="right" nowrap="nowrap">&nbsp;&nbsp;签订开始：</td>
                <td align="left"><div id="search-agreement-startDate-start"></div></td>
                <td align="right" nowrap="nowrap">&nbsp;&nbsp;签订结束：</td>
                <td align="left"><div id="search-agreement-startDate-end"></div></td>
                <td><a  onclick="searchAgreement()" id="search-agreement-btn"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            </tr>
        </table>
    </div>
<%--grid数据展示--%>
    <div style="width:99%;height:90%;margin-left:0.5%;overflow-y:auto;">
        <div id='jqxgridAgreement' style="width:100%;height:450px;"></div>
    </div>
<%--添加协议--%>
    <div id="addAgreementWindow">
        <div>+添加大客户协议</div>
        <div id="addAgreementForm" style="overflow: auto;">
            <table border="0" width="100%" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right" style="width: 20%">客户：</td>
                    <td align="left"><div data-name="accountName"  id="add-agreement-custom"></div> </td>
                    <td align="right"><font style="color:red">*</font>协议标题：</td>
                    <td align="left"><input type="text" data-name="agreementTitle" id="add-agreement-title"></td>
                </tr>
                <tr>
                    <td align="right">签订日：</td>
                    <td align="left"><div  data-name="dateSigned" id="add-agreement-date-signed"></div></td>
                    <td align="right"><font style="color:red">*</font>签订价格：</td>
                    <td align="left"><input type="text" data-name="price" id="add-agreement-price"></td>
                </tr>
                <tr>
                    <td align="right">付款情况描述：</td>
                    <td align="left"><input type="text"  data-name="payment" id="add-agreement-desc"></td>
                    <%--<td align="right"><font style="color:red">*</font>协议文件：</td>--%>
                    <%--<td align="left"><input type="file" data-name="agreementFile" id="add-agreement-file" style="width: 150px"></td>--%>
                </tr>

                <tr style="display: table-row">
                    <td align="right" class="label"><span style="color: red">*</span>协议文件</td>
                    <td align="left" class="value">
                        <div id="projectFiles">
                            <table class="main">
                                <tbody>
                                <tr>
                                    <td align="right" width="335" colspan="2">
                                        <input type="button" name="addUpload" onclick="addUploadInput(this)" value="增加上传" />&nbsp;&nbsp;&nbsp;&nbsp;&ndash;
                                        <input style="margin-right: 5px;" onclick="submitUploadFile(this)" type="button" name="importFile" value="上传" />
                                    </td>
                                </tr>
                                </tbody>
                                <tbody name="uploadedDiv"></tbody>
                                <tbody name="uploadInputDiv"></tbody>
                            </table>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td align="right">协议主要内容:</td>
                    <td align="left" colspan="3"><textarea rows="5"  date-name="brief" id="add-agreement-content"></textarea></td>
                </tr>
                <tr>
                    <td align="right" colspan="4">
                        <input style="margin-right: 5px" type="button" value='确定' id='add-save-agreement-btn' onclick="addAgreementSubmit()" />
                        <input type="button" value='取消' id='add-cancle-agreement-btn' />
                    </td>
                </tr>
            </table>
        </div>
    </div>
<%-- 编辑协议 --%>
    <div id="editAgreementWindow">
        <div>编辑大客户协议</div>
        <div id="editAgreementForm" style="overflow: auto">
            <input type="hidden" data-name="accountSid" id="edit-account-sid" />
            <table border="0" width="100%" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right" style="width: 20%">客户：</td>
                    <td align="left"><div data-name="accountName"  id="edit-agreement-custom"></div> </td>
                    <td align="right"><font style="color:red">*</font>协议标题：</td>
                    <td align="left"><input type="text" data-name="agreementTitle" id="edit-agreement-title"></td>
                </tr>
                <tr>
                    <td align="right">签订日：</td>
                    <td align="left"><div  data-name="dateSigned" id="edit-agreement-date-signed"></div></td>
                    <td align="right"><font style="color:red">*</font>签订价格：</td>
                    <td align="left"><input type="text" data-name="price" id="edit-agreement-price"></td>
                </tr>
                <tr>
                    <td align="right">付款情况描述：</td>
                    <td align="left"><input type="text"  data-name="payment" id="edit-agreement-desc"></td>
                    <%--<td align="right"><font style="color:red">*</font>协议文件：</td>--%>
                    <%--<td align="left"><input type="file" data-name="agreementFile" id="edit-agreement-file" style="width: 150px"></td>--%>
                </tr>
                <%--<tr>--%>
                    <%--<td align="right"><span style="color: red">*</span>协议文件：</td>--%>
                    <%--<td align="right" width="355" colspan="2">--%>
                        <%--<input type="button" name="addUpload" onclick="addUploadInput(this)" value="增加上传" />--%>
                        <%--<input style="margin-right: 5px;" onclick="submitUploadFile(this)" type="button" name="importFile" value="上传" />--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <tr>
                    <td align="right">协议主要内容:</td>
                    <td align="left" colspan="3"><textarea  date-name="brief" id="edit-agreement-content"></textarea></td>
                </tr>
                <tr>
                    <td align="right" colspan="4">
                        <input style="margin-right: 5px" type="button" value='确定' id='edit-save-agreement-btn' onclick="editAgreementSubmit()" />
                        <input type="button" value='取消' id='edit-cancle-agreement-btn' />
                    </td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>
<script type="text/javascript">
    // 初始化agreement-mgt-index页面model
    var agreementModel = new agreementModel();
    // 初始化页面组件
    agreementModel.initInput();
    // 初始化弹出框
    agreementModel.initPopWindow();
    // 初始化datagrid
    agreementModel.initAgreementDatagrid();
    // 初始化组件验证规则
    agreementModel.initValidator();
</script>