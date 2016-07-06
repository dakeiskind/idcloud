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
        <div id="popupAllocateWindow" display="none">
            <div>分配用户</div>

            <div id="allocateForm" style="overflow: hidden;">
                <div style="width: 100%; height: 260px;">
                    <div id='jqxgridAllocate' style="margin-left: 1%"></div>
                </div>
                <div>
                    <input type="hidden" id="allocate-ticketSid"/>

                    <table border="0" width="100%" cellspacing="5" cellpadding="0">
                        <tr>
                            <td align="right" colspan="4">
                                <input style="margin-right: 5px;" data-bind='click: allocateUserSubmit' type="button" id="saveAllocate" value="确定" />
                                <input id="cancelAllocate" type="button" value="关闭" />
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div id="popupProcessWindow" display="none">
            <div>处理工单</div>

            <div id="processForm" style="overflow: hidden;">

                <div class="panel" style="margin-bottom: 10px">
                    <p
                            style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
                        <span id="box">基本信息</span>
                    </p>
                    <hr />
                    <input type="hidden" data-name="ticketSid" id="view-ticketSid"/>
                    <input type="hidden" id="operate" value="01"/>
                    <input type="hidden" id="operator"/>
                    <input type="hidden" id="operateTime" >

                    <table class="viewTable" width="100%" cellpadding="0" cellspacing="0">
                        <tr>
                            <td align="right" width="80px">工单编号：</td>
                            <td id="process-ticketNo"></td>
                            <!-- <td align="right" width="80px">所属租户：</td>
                            <td id="process-tenantName"></td> -->
                            <td align="right" width="80px">提交者：</td>
                            <td id="process-ticketUser"></td>
                        </tr>
                        <tr>
                            <td align="right" width="80px">工单状态：</td>
                            <td id="process-statusName"></td>
                            <td align="right">问题类型：</td>
                            <td id="process-questionTypeName"></td>
                            <td align="right">紧急程度：</td>
                            <td id="process-questionLevelName"></td>
                        </tr>
                        <tr>
                            <td align="right" width="80px">服务名称：</td>
                            <td id="process-serviceName"></td>
                            <td align="right" width="80px">创建时间：</td>
                            <td id="process-createdDt" colspan="3"></td>
                        </tr>
                        <tr>
                            <td align="right" width="80px">工单主题：</td>
                            <td id="process-title" colspan="5"></td>
                        </tr>
                        <tr>
                            <td align="right" width="80px">工单内容：</td>
                            <td id="process-content" colspan="5"></td>
                        </tr>
                    </table>
                </div>

                <div id="handlerTab" style="width: 100%; height: 250px;">
                    <ul style="margin-left: 6px;">
                        <li style="margin-left: 6px;"><i class='icons-blue icon-tasks'></i>手动处理</li>
                        <li id="autoHandlerTabHeader"><i class='icons-blue icon-list'></i>自动处理</li>
                        <li><i class='icons-blue icon-list'></i>处理记录</li>
                    </ul>
                    <div id="tab-manualHandler" style="overflow: auto;">
                        <table class="viewTable" width="100%" cellpadding="3" cellspacing="2" border="0">
                            <input type="hidden" id="ticket-mgtObjSid" />
                            <input type="hidden" id="ticket-ve" />
                            <input type="hidden" id="ticket-resType" />
                            <input type="hidden" id="ticket-partitionType" />
                            <tbody id="hostOpenTicket" style="display: none;">
                            <tr>
                                <td align="right" width="140px" height="25px">物理机名称：</td>
                                <td><div id="hostOpen-name" ></div></td>
                            </tr>
                            <tbody id="hostDiskDiv">

                            </tbody>
                            </tbody>
                            <tbody id="aixDiskTicket" style="display: none;">
                            </tbody>
                            <tbody id="vmOpenTicket" style="display: none;">
                            <tr>
                                <td align="right" width="140px" height="25px">云主机名称：</td>
                                <td><input type="text" id="vmOpen-name" />
                                    <input type="button" id="matchVmOpenName" value="匹配" onclick="matchVmName()" /></td>
                            </tr>
                            </tbody>
                            <tr>
                                <td align="right" width="140px" height="25px">处理人：</td>
                                <td id="process-operator" width="180px" colspan="2"></td>
                            </tr>
                            <tr>
                                <td align="right" width="140px" height="25px">处理时间：</td>
                                <td id="process-operateTime" colspan="2"></td>
                            </tr>
                            <tr>
                                <td align="right" width="140px">处理内容：</td>
                                <td colspan="2"><textarea data-name="operateContent" id="process-operateContent" class="k-textbox" maxlength="256"
                                                          style="height: 40px; width: 250px; resize: none;border-color: #e5e5e5;"></textarea></td>
                            </tr>
                            <tr>
                                <td align="right" width="140px" height="25px">处理状态：</td>
                                <td colspan="2"><div id="process-operateStatus"></div></td>
                            </tr>
                            <tr>
                                <td colspan="3" align="right"  valign="bottom" height="45px"><input id="processTicket"  data-bind='click: processTicketSubmit' type="button" value="处理"/>&nbsp;</td>
                            </tr>
                        </table>
                    </div>
                    <div id="tab-autoHandler">
                        <%@ include file="ticket-autoHandler-index.jsp" %>
                    </div>
                    <div id="tab-handlerRecord">
                        <div id="jqxgridProcess" style="width: 100%; margin:5px; font-size: 14px; overflow: hidden;"></div>
                    </div>
                </div>
            </div>
        </div>
        <div id="popAddTicketWindow" style="display: none;">
            <div>添加日常维护工单</div>
            <div id="addTicketForm" style="overflow: hidden;">
                <div class="panel" style="margin-bottom: 10px">
                    <table class="viewTable" width="100%" cellpadding="3" cellspacing="0">
                        <tr>
                            <td align="right" width="80px">工单类型：</td>
                            <td>日常维护工单</td>
                        </tr>
                        <tr>
                            <td align="right" width="80px"><font style="color: red">＊</font>工单主题：</td>
                            <td><input type="text" id="ticketTitle" /></td>
                        </tr>
                        <tr>
                            <td align="right" width="80px"><font style="color: red">＊</font>工单内容：</td>
                            <td><textarea id="ticketContent" class="k-textbox" maxlength="256"
                                          style="height: 80px; width: 250px; resize: none;border-color: #e5e5e5;"></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
                <div style="float: right;margin-right: 10px;">
                    <input type="button" id="saveTicketBtn" onclick="submitAddTicket()" value="提交" />
                    <input type="button" id="cancelTicketBtn" value="关闭" />
                </div>
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
            model.initTicketMgtInput();
            model.initTicketMgtDatagrid();
            model.initTicketMgtPopWindow();
            model.initValidator();

            model.searchObj.titleLike = '';
            model.searchObj.status = '01';
            $("#search-ticket-status").val("01");
            model.searchTicketMgt();
            return model;
        }
    </script>
</html>
