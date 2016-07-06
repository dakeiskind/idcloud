<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div style="width: 100%; height: 30px; padding: 10px 0px 10px 0px;color:#767676;font-size: 12px;">
	<table border="0" height="100%" cellspacing="0" cellpadding="2" style="table-layout: fixed;">
		<tr>
			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;IDC流水号：</td>
			<td><input type="text" id="idc-transaction-id" />&nbsp;&nbsp;</td>
			<td align="right" nowrap="nowrap">&nbsp;&nbsp;订单类型：</td>
			<td>
				<div id="idc-order-type"></div>
			</td>
			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;订单状态：</td>
			<td>
				<div id="idc-order-status"></div>
			</td>
			<td>
				<a onclick="searchIdcOrder()" id="idc-order-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a>
			</td>
		</tr>
	</table>
</div>
<div style="width: 98%; height: 80%; margin-left: 1%;padding: 10px 0px 10px 0px;">
	<div id='idcOrderDataGrid' style="width: 100%; height: 450px;"></div>
</div>
<!-- 审核和反馈 -->
<div style="width: 100%; height: 800px; margin-top: 50px;display: none;" id="tabApproveWin">
	<div id="idcApproveWindow" style="display: none">
		<div class="title">&nbsp;</div>
    	<div style="overflow: hidden;">
			<input type="hidden" id="orderApproveId" /> 
			<input type="hidden" id="orderBzId" /> 
			<div>
				<table  border="0" width="100%" cellspacing="5" cellpadding="0">
					<tr id="idcApproveStatus">
						<td align="right" style="width: 20%;">审核状态:</td>
						<td align="left">
							<div id="approveIdcStatus"></div>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top" id="approveTextTd"><font style="color: red">*</font>审批注释:</td>
						<td align="right" valign="top" id="idcFeedback" style="display: none;">
							<font style="color: red">*</font>反馈注释:
						</td>
						<td align="left"><textarea id="approveIdcNote" rows="8" cols="40"></textarea></td>
					</tr>
				</table>
			</div>
			<div style=" width: 99.5%; height: 25px; text-align: right">
				<input data-bind='click: idcMgtSubmit' type="button" id="approveButton" value="确定" /> 
				<input data-bind='click: idcFeedbackSubmit' type="button" id="feedbackButton" value="确定" style="display: none;"/> 
				<input style="margin-right: 6px;" id="idcMgtCancel" class="idcMgtCancel" type="button" value="取消" />
			</div>
		</div>
	</div>
</div>
<!-- 开通 -->
<div style="width: 100%; height: 800px; margin-top: 50px;display: none;" id="tabResChooseWin">
	<div id="idctabwindow" style="display: none">
		<div class="title">&nbsp;</div>
    	<div style="overflow: hidden;" id="winContent">
    		<div id="idctab" style="width: 100%; height: 100%;">
            	<ul style="margin-left: 6px;">
					<li style="margin-left: 6px;"><i class='icons-blue icon-tasks'></i>规格明细列表</li>
            	</ul>
				<div id="approveIDCMgtWindow">
					<div id="approveIDCMgtForm" style="overflow: hidden; position: relative;">
						<input type="hidden" id="instanceId" /> 
						<input type="hidden" id="orderId" /> 
						<input type="hidden" id="orderDetailId" />
						<input type="hidden" id="processType" />
						<input type="hidden" id="vmOpType" />
		
						<table id="apporveIDCOrderInfo" border="0" width="100%" cellspacing="5" cellpadding="0">
							<tr><td colspan="2">
								<div id="idc-orderSpecGrid"></div>
							</td></tr>
							<tr><td colspan="2">
								<div id="idc-orderDetail-tab">
									<ul style="margin-left: 6px;">
										<li id="bizResourceTbl"><i class='icons-blue icon-list'></i>资源选择</li>
									</ul>
									<div id="idcOrder-resource-tabcontent" style="margin-top:5px">
										<table border="0" width="100%" cellspacing="4" cellpadding="0">
											<tr id="ostr">
												<td width="10"></td>
												<td align="right" width="90">
													操作系统:
												</td>
												<td width="210">
													<div id="osType" style="float: left;margin-left: 3px;"></div>
												</td>
												<td align="left" width="90">
												</td>
												<td>
												</td>
											</tr>
											<tr>
												<td width="10"></td>
												<td align="right" width="90">
													计算资源集:
												</td>
												<td width="210">
													<div id="rescomuteid" style="float: left;margin-left: 3px;"></div>
												</td>
												<td align="left" width="90">
												</td>
												<td>
												</td>
											</tr>
											<tr id="wanTr1" style="display:none" >
												<td width="10"></td>
												<td align="right" width="90">
													业务外网:
												</td>
												<td>
													<div id="vLanIDO" style="float: left;margin-left: 3px;"></div>
												</td>
												<td align="right" width="90">
													可选IP地址:
												</td>
												<td>
													<div id="wanIps" style="float: left;margin-left: 3px;"></div>
												</td>
											</tr>
										</table>
									</div>
								</div>
							</td></tr>
						</table>
						<div style=" width: 99.5%; height: 25px; text-align: right">
							<input data-bind='click: idcResCheckSubmit' type="button" id="idcResCheck" value="资源检查" /> 
							<input data-bind='click: approveIDCMgtSubmit' type="button"
								id="approveIDCMgtSave" value="确定" /> <input style="margin-right: 6px;"
								id="approveIDCMgtCancel" class="approveIDCMgtCancel" type="button" value="取消" />
						</div>
					</div>
				<!-- 变更 -->
					<div id="idcChangeMgtForm" style="overflow: hidden; position: relative;">
<!-- 						<input type="hidden" id="instanceId" />  -->
<!-- 						<input type="hidden" id="orderId" />  -->
<!-- 						<input type="hidden" id="orderDetailId" /> -->
<!-- 						<input type="hidden" id="processType" /> -->
<!-- 						<input type="hidden" id="vmOpType" /> -->
						<table id="idcChangeInfo" border="0" width="100%" cellspacing="5" cellpadding="0">
							<tr><td colspan="2">
								<div id="idc-orderBaseSpecGrid"></div>
							</td></tr>
							<tr><td colspan="2">
								<div id="idc-changeDetail-tab">
									<ul style="margin-left: 6px;">
										<li id="bizDiskTbl"><i class='icons-blue icon-list'></i>磁盘变更明细</li>
									</ul>
									<div id="idcOrder-diskSpec-tabcontent" style="margin-top:5px">
										<div id="idcOrder-diskSpec"></div>
									</div>
								</div>
							</td></tr>
<!-- 							<tr> -->
<!-- 								<td colspan="2"> -->
<!-- 									<div style="float: left;hight:25px;line-height: 25px;">计算资源集:</div> -->
<!-- 									<div id="rescomuteidSpec" style="float: left;margin-left: 3px;hight:30px;"></div> -->
<!-- 								</td> -->
<!-- 							</tr> -->
						</table>
						<div style=" width: 99.5%; height: 25px; text-align: right">
							<input data-bind='click: idcSpecCheckSubmit' type="button" id="idcSpecCheck" value="资源检查" /> 
							<input data-bind='click: specChangeSubmit' type="button"
								id="specChangeSave" value="确定" /> <input style="margin-right: 6px;"
								id="idcSpecCheckCancel" class="approveIDCMgtCancel" type="button" value="取消" />
						</div>
					</div>
				</div>
				
				
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	function initIdcOrderPageJs() {
		// 初始化sys-index页面model
		var idcOrderModel = new idcOrderMgtModel();
		// 初始化页面组件
		idcOrderModel.initIdcOrderMgtInput();
		// 初始化弹出框
		idcOrderModel.initIdcOrderMgtPopWindow();
		// 初始化组件验证规则
// 		idcOrderModel.initValidator();
		// 初始化datagrid
		idcOrderModel.initIdcOrderDataGrid();
		// 为datagrid赋值
		idcOrderModel.searchIdcOrderInfo();

		return idcOrderModel;
	}
</script>