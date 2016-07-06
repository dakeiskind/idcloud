<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="/pages/common/taglibs.jsp"%>
	<%@ include file="/pages/common/resources.jsp"%>

	<script type="text/javascript" src="${ctx}/js/idc/idc-incident-model.js"></script>
	<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
	<style type="text/css">
		#containerOrder {
			width: 100%;
			height: 100%;
			margin: 0px;
			padding: 0px;
		}
		.searchInfoDiv {
			color:#767676;
			font-size: 12px;
		}
		table{
		　　table-layout: fixed;
		}
		td{
		
		　　white-space: nowrap;
		　　overflow: hidden;
		　　text-overflow: ellipsis;
		
		}
	</style>
</head>
<body class='default'>
	<div id="containerOrder">
		<div id='jqxWidget'>
			<div>
				<div class="searchInfoDiv" style="width: 100%; height: 30px; padding: 10px 0px 10px 0px;">
					<table border="0" height="100%" cellspacing="0" cellpadding="2">
						<tr>
							<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;故障单号：</td>
							<td><input type="text" id="idc-incident-id" />&nbsp;&nbsp;</td>
							<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;申报人：</td>
							<td><input type="text" id="idc-incident-staffName" />&nbsp;&nbsp;</td>
							<td align="right" nowrap="nowrap">&nbsp;&nbsp;状态：</td>
							<td>
								<div id="idc-incident-status"></div>
							</td>
							<td>
								<a onclick="searchIncidentTicket()" id="idc-incident-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a>
<!--  								<a onclick="submitdemo()" id="idc-incident-button"><i class='icons-blue icon-search-4'></i>demo&nbsp;</a> -->
							</td>
						</tr>
					</table>
				</div>
			    <div style="width:98%;height:80%;margin-left:1%;"> 
			     	<div id='incidentTicketGrid' style="width:100%;height:450px;"></div> 
			    </div>   
			</div>
		</div>
	</div>
	
	<!-- 查看idc反馈信息window -->
	<div id="viewIdcFeedbackInfoWindow">
            <div>查看IDC反馈信息</div>
            <div id="" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" width="80px">故障单号：</td>
                        <td align="left">
                        	<span id="idcIncidentId"></span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">结果确认：</td>
                        <td align="left">
                        	<span id="resultConfirmedName"></span>
                        </td>
                    </tr>
                     <tr>
                        <td align="right">确认时间：</td>
                        <td align="left">
                        	<span id="confirmedTime"></span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">客户满意度：</td>
                        <td align="left">
                        	<span id="csi"></span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="top">评价备注：</td>
                        <td align="left" valign="top" height="50px;">
                        	<span id="bz"></span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="2">
	                        <input id="viewIncidentCancelButton" type="button" value="关闭" />
                        </td>
                    </tr>
                </table>
            </div>
    </div>
    
    <!-- 故障虚拟机处理结果 -->
	<div id="handleIncidentVmWindow">
            <div>填写处理结果</div>
            <div id="handleIncidentVmForm" style="overflow: hidden;">
            	<input type="hidden" data-name="idcIncidentId" id="handle-idc-incident-id"/>
                <input type="hidden" data-name="hpVmId" id="handle-hpVmId"/>
                <table border="0" width="100%" cellspacing="3" cellpadding="0">
                    <tr>
                        <td align="right" valign="top" width="80px">处理意见：</td>
                        <td align="left" valign="top" height="50px;">
                        	<textarea data-name="comments" id="handle-comments"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="2">
                        	<input id="handleIncidentSaveButton" onclick="submitHandleInfo()" type="button" value="保存" />
	                        <input id="handleIncidentCancelButton" type="button" value="关闭" />
                        </td>
                    </tr>
                </table>
            </div>
    </div>
    
    <!-- 反馈 -->
	<div id="feedbackIncidentVmWindow">
            <div>填写反馈信息</div>
            <div id="feedbackIncidentVmForm" style="overflow: hidden;">
            	<input type="hidden" data-name="idcIncidentId" id="feedback-idc-incident-id"/>
            	<input type="hidden" data-name="idcServcode" id="feedback-idc-servcode"/>
            	<input type="hidden" data-name="idcTransactionId" id="feedback-idc-transaction-id"/>
            	<input type="hidden" data-name="result" id="feedback-handle-result"/>
            	<input type="hidden" data-name="userName" id="feedback-handle-userName"/>
            	<input type="hidden" data-name="password" id="feedback-handle-password"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
<!--                     <tr> -->
<!--                         <td align="right" valign="top"><font style="color:red">*</font>处理结果：</td> -->
<!--                         <td align="left" valign="top"> -->
<!--                         	<textarea data-name="result" id="feedback-handle-result"></textarea> -->
<!--                         </td> -->
<!--                     </tr> -->
					 <tr>
                        <td align="right"><font style="color:red">*</font>处理人名字：</td>
                        <td align="left">
                        	<input data-name="staffName" id="feedback-handle-staffName"></input>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>处理人电话：</td>
                        <td align="left" >
                        	<input data-name="tel" id="feedback-handle-tel"></input>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>处理完成时间：</td>
                        <td align="left">
                        	<div data-name="completeDate" id="feedback-handle-completeDate"></div>
                        </td>
                    </tr>
                     <tr>
                        <td align="right" valign="top"><font style="color:red">*</font>处理意见：</td>
                        <td align="left" valign="top" >
                        	<textarea data-name="comments" id="feedback-handle-opinion"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="2" >
                        	<input id="feedbackIncidentSaveButton" style="margin-top:3px" onclick="submitFeedbackInfo()" type="button" value="提交" />
	                        <input id="feedbackIncidentCancelButton" style="margin-top:3px" type="button" value="关闭" />
                        </td>
                    </tr>
                </table>
            </div>
    </div>
	
</body>
<script type="text/javascript">
	$(function() {
		// 初始化js
		var incident = new idcIncidentTicketModel();
		incident.initInput();
		
		incident.initIncidentTicketDatagrid();
		incident.initPopWindow();
		incident.initValidator();
		incident.searchIncidentTicketInfo();
	});
</script>
</html>