<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
	
<div style="width: 100%; height: 800px; margin-top: 50px;display: none;" id="orderDetailTabWin">
	<div id="orderTabWindow" style="display: none">
    	<div class="title">&nbsp;订单详情</div>
        <div style="overflow: hidden;" id="winContent">
        	<div id="orderDetailTabs" style="width: 100%; height: 80%;">
            	<ul style="margin-left: 6px;">
					<li style="margin-left: 6px;"><i class='icons-blue icon-tasks'></i>订单详情</li>
		        	<li><i class='icons-blue icon-list'></i>订单流程信息</li>
             	</ul>
				<div id="orderInfoWindow">
					<div id="orderInfoForm" style="overflow: hidden; position: relative;height: 100%;">
						<table id="orderBaseInfo" border="0" width="100%" height="100%"  cellspacing="5" cellpadding="0">
							<tr><td colspan="2">
								<table border="0" width="100%" cellspacing="5" cellpadding="0">
									<tr>
										<td align="right" width="13%">订单编号:</td>
										<td align="left" width="20%"><span id="baseInfo-orderNumber"></span></td>
										<td align="right" width="13%">所属项目:</td>
										<td align="left" width="20%"><span id="baseInfo-mgtObjName"></span></td>
										<td align="right" width="13%">所有者:</td>
										<td align="left"><span id="baseInfo-owner"></span></td>
									</tr>
									<tr>
										<td align="right">订单状态:</td>
										<td align="left"><span id="baseInfo-orderStatus"></span></td>
										<td align="right">支付时间:</td>
										<td align="left"><span id="baseInfo-payTime"></span></td>
										<td align="right">开通时间:</td>
										<td align="left"><span id="baseInfo-openTime"></span></td>
									</tr>
									<tr>
										<td align="right">金额:</td>
										<td align="left"><span id="baseInfo-orderAmount"></span></td>
									</tr>
								</table>
							</td></tr> 
							<tr><td colspan="2">
								<div id="orderDetailInfoGrid"></div>
							</td></tr>
							<tr><td colspan="2">
								<div id="orderDetailSpecGrid"></div>
							</td></tr>
						</table>
					</div>
				</div>
			 	<div id="orderApproveProcessWindow">
					<div id="orderApproveProcessForm" style="overflow: hidden;">
						<div style="border: 0px solid gray; width: 100%; height: 60%;position:relative;">
<!-- 							<img id="processPic" width="100%" height="320px" src="" data-bind='event: {load: showUnapprovedHistory}'/> -->
							<img id="processTabPic" onload="if(this.width>740)this.width=740;if(this.height>250)this.height=250" src="" />
							<!-- 给执行的节点加框 -->
							<div id="processTabTrace" style="position: absolute; border: 2px solid red;"></div>
						</div>
						<div style="height: 38%;">
							<div id="orderApprovedDataGrid"></div>
						</div>
					</div>
				</div>
    		</div>
    		<div style="text-align: right;margin-top: 10px;">
    			<input style="margin-right: 6px;" id="closeOrderTabWin" class="approveMgtCancel" type="button" value="关闭" />
    		</div>
    	</div>
    </div>
</div>
