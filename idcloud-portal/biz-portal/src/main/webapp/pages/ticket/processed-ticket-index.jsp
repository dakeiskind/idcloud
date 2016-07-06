<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/pages/common/taglibs.jsp"%>
    <%@ include file="/pages/common/resources.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=8" />
    <script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
    <script type="text/javascript" src="${ctx}/js/ticket/ticket-mgt-model.js"></script>
    <script type="text/javascript" src="${ctx}/js/ticket/ticket-bind-model.js"></script>
    <script type="text/javascript" src="${ctx}/js/ticket/ticket-autoHandler-model.js"></script>

    <style type="text/css">
        #viewForm {
            width: 100%;
        }
        #viewForm .panel {
            width: 98%;
            border-bottom: 1px solid #DADADA;
            border-left: 1px solid #DADADA;
            border-right: 1px solid #DADADA;
            border-top: 1px solid #DADADA;
            box-shadow: #DADADA 0px 3px 3px;
            position: relative;
            padding: 1%;
        }

        .viewTable {
            width: 100%;
            color:#767676;
            font-size: 12px;
        }

        .viewTable .leftTd {
            text-align: right;
        }
    </style>
</head>
<body>
<div style="width:100%;height:30px;padding:10px 0px 10px 0px;overflow:hidden">
    <table border="0" height="100%" cellspacing="0" cellpadding="2">
        <tr>
            <td align="right" style="font-size:12px;">&nbsp;&nbsp;&nbsp;&nbsp;工单号：</td>
            <td><input type="text" id="search-ticket-id" />&nbsp;&nbsp;</td>
            <td align="right" style="font-size:12px;">&nbsp;&nbsp;创建时间：</td>
            <td>
                <div id="search-ticket-createDt-start"></div>
            </td>
            <td align="right" nowrap="nowrap">&nbsp;~&nbsp;</td>
            <td>
                <div id="search-ticket-createDt-end"></div>
            </td>
            <td align="right" style="font-size:12px;">&nbsp;&nbsp;类型：</td>
            <td><div id="search-ticket-type"></div></td>
            <td align="right" style="font-size:12px;">&nbsp;&nbsp;状态：</td>
            <td><div id="search-ticket-status"></div></td>
            <td><a data-bind="click: searchTicket" id="search-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
        </tr>
    </table>
</div>
<div style="width: 100%; height: 350px;">
    <div id='jqxgridTicket' style="margin-left: 1%"></div>
</div>

<div id="popupViewWindow" display="none">
    <div>查看工单</div>

    <div id="viewForm" style="overflow: hidden;">

        <div class="panel" style="margin-bottom: 10px; height:200px">
            <p
                    style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
                <span id="box">基本信息</span>
            </p>
            <hr />
            <input type="hidden" data-name="ticketSid" id="process-ticketSid"/>
            <input type="hidden" data-name="processObjectId" id="processObjectId"/>
            <input type="hidden" data-name="orderId" id="orderId"/>
            <input type="hidden" data-name="processType" id="processType"/>
            <table class="viewTable" width="100%" cellpadding="5" cellspacing="5">
                <tr>
                    <td align="right" width="80px">工单编号：</td>
                    <td id="view-ticketNo"></td>
                    <!-- <td align="right" width="80px">所属租户：</td>
                    <td id="view-tenantName"></td> -->
                    <td align="right" width="80px">提交者：</td>
                    <td id="view-ticketUser"></td>
                </tr>
                <tr>
                    <td align="right" width="80px">工单状态：</td>
                    <td id="view-statusName"></td>
                    <td align="right">问题类型：</td>
                    <td id="view-questionTypeName"></td>
                    <td align="right">紧急程度：</td>
                    <td id="view-questionLevelName"></td>
                </tr>
                <tr>
                    <td align="right" width="80px">服务名称：</td>
                    <td id="view-serviceName"></td>
                    <td align="right" width="80px">创建时间：</td>
                    <td id="view-createdDt" colspan="3"></td>
                </tr>
                <tr>
                    <td align="right" width="80px">工单主题：</td>
                    <td id="view-title" colspan="5"></td>
                </tr>
                <tr>
                    <td align="right" width="80px">工单内容：</td>
                    <td id="view-content" colspan="5"></td>
                </tr>
            </table>
        </div>
        <div id="viewTicket" class="panel"	style="margin-bottom: 10px; clear: both; height: 220px;">
            <p style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
                <span id="box">工单记录列表</span>
            </p>
            <hr />
            <div id="jqxgridView" style="height:150px; width: 100%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
        </div>

        <button id="cancelView" style="float: right;margin-top:5px;" type="button">关闭</button>
    </div>
</div>
</body>
<script type="text/javascript">
    //初始化工单管理js
    var ticketMgt = initTicketMgtPageJs();
    var bindmodel = new ticketBindModel(ticketMgt);

    //将model和view绑定
    ko.applyBindings(bindmodel);

    function initTicketMgtPageJs() {
        // 初始化服务实例-index页面model
        var model = new ticketMgtModel();
        model.initProcessedTicketMgtInput();
        model.initProcessedTicketMgtDatagrid();
        model.initProcessedTicketMgtPopWindow();

        model.searchObj.titleLike = '';
        model.searchObj.status = '04,05';
        $("#search-ticket-status").val("04");
        model.searchTicketMgt();
        return model;
    }
</script>
</html>
