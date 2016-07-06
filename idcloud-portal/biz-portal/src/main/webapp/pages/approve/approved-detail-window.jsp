<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div style="width: 100%; height: 620px; margin-top: 50px;display: none;" id="jqxapprovedmainContainer">
	 <div id="jqxapprovedwindow" style="display: none">
                <div class="title">&nbsp;</div>
                <div style="overflow: hidden;" id="jqxapprovedwindowContent">
				<div id="jqxapprovedtab" style="width: 100%; height: 480px;">
                        <ul style="margin-left: 6px;">
							<li style="margin-left: 6px;"><i class='icons-blue icon-tasks'></i>审核明细</li>
		                	<li><i class='icons-blue icon-list'></i>审核历史</li>
                        </ul>
	<div id="approvedDetailWindow">
	<div id="approvedDetailForm" style="overflow: hidden; position: relative;height:480px">
		<table id="apporveTargetInfo" border="0" width="100%" cellspacing="5" cellpadding="0">
			<!-- 
			<tr>
				<td align="right" valign="top" width="130px"><font
					class="title1"></font>信息:</td>
				<td align="left"><font id="content"></font></td>
			</tr>
			-->
			<tr><td colspan="2">
				<table border="0" width="100%" cellspacing="5" cellpadding="0">
					<tr>
						<td align="right">订单编号:</td>
						<td align="left"><span id="orderNumber"></span></td>
						<!-- 
						<td align="right">所属租户:</td>
						<td align="left"><span id="ownerTenant"></span></td>
						 
						<td align="right">商务合同号:</td>
						<td align="left"><span id="contractId"></span></td>
						<td align="right">项目立项号:</td>
						<td align="left"><span id="projectId"></span></td>-->
						<td align="right">所有者:</td>
						<td align="left"><span id="owner"></span></td>
					</tr>
					<tr>
						<td align="right">订单状态:</td>
						<td align="left"><span id="orderStatus"></span></td>
						<td align="right">开通时间:</td>
						<td align="left"><span id="openTime"></span></td>
					</tr>
					<tr>
						<td align="right">所属项目:</td>
						<td align="left"><span id="mgtObjName"></span></td>
						<td align="right"></td>
						<td align="left"></td>
					</tr>
					<tr style="display: none;">
						<td align="right">合同附件:</td>
						<td align="left" colspan="2">
							<a id="contractFile" style="color: blue;" onclick="fileDownLoad()" class="datagrid-link"></a>
						</td>
						<td align="right">立项附件:</td>
						<td align="left" colspan="2">
							<a id="projectFile" style="color: blue;" onclick="fileDownLoad2()" class="datagrid-link"></a>
						</td>
					</tr>
				</table>
			</td></tr>
			<tr><td colspan="2">
				<div id="orderDetailsGrid"></div>
			</td></tr>
			<tr><td colspan="2">
			    	<div >
                    	<div id="specapproveddetailHeader" ><a href='#' style="theme: metro;">&nbsp;&nbsp;规格明细列表</a></div>             	
						<div id="serviceSpecGrid"></div>
					</div>
				
			</td></tr>
		</table>

	</div>
	
	
	<!-- 云主机服务变更审核详情Tab页面 -->
	<div id="instance-approvedForm" style="overflow: hidden; position: relative;height: 100%;display:none">
		<input type="hidden" id="instance-approvedprocessInstanceId" />
		<input type="hidden" id="instance-approvedapprovorAction" />
		<input type="hidden" id="instance-approvedprocessType" />
		<input type="hidden" id="instance-approvedinstanceSid" />
		<table id="instance-apporvedTargetInfo" border="0" width="100%" cellspacing="5" cellpadding="0" height="100%">
			<tr><td>
				<table border="0" width="100%" cellspacing="5" cellpadding="0">
					<tr>
						<td align="right" width="10%">实例号:</td>
						<td align="left" width="40%"><span id="instance-approvedinstanceName"></span></td>
						<td align="right" width="10%">所有者:</td>
						<td align="left" width="20%"><span id="instance-approvedowner"></span></td>
					</tr>
					<tr>
						<td align="right">所属项目:</td>
						<td align="left"><span id="instance-approvedMgtObjName"></span></td>
						<td align="right">&nbsp;</td>
						<td align="left">&nbsp;</td>
					</tr>
				</table>
			</td></tr>
			<tr><td>
			    <!-- 规格变更grid -->
				<div id="instance-approvedspecGrid"></div>
			</td></tr>
			<tr><td>
			    <!-- 规格变更grid -->
				<div id="approved-spec-tab">
					<ul style="margin-left: 6px;">
						<li style="margin-left: 6px;"><i class='icons-blue icon-list'></i>磁盘变更规格项</li>
<!-- 						<li id="netWorkTab"><i class='icons-blue icon-list'></i>网络变更规格项</li> -->
					</ul>
					<div id="approved-diskspec-tabcontent">
						<!-- 磁盘规格变更grid -->
						<div id="instance-approveddiskSpecGrid" style="margin-top:1px"></div>
					</div>
<!-- 					<div id="approved-netspec-tabcontent"> -->
<!-- 						网络规格变更grid -->
<!-- 						<div id="instance-approvednetSpecGrid" style="margin-top:1px"></div> -->
<!-- 					</div> -->
				</div>
			</td></tr>
		</table>
	</div>
	
	
		<!-- 云主机服务退订审核详情Tab页面 -->
	<div id="cancel-approvedForm" style="overflow: hidden; position: relative;display:none">
		<input type="hidden" id="cancel-approvedprocessInstanceId" />
		<input type="hidden" id="cancel-approvedapprovorAction" />
		<input type="hidden" id="cancel-approvedprocessType" />
		<input type="hidden" id="cancel-approvedinstanceSid" />
		<table id="cancel-apporvedTargetInfo" border="0" width="100%" cellspacing="5" cellpadding="0">
			<tr><td>
				<table border="0" width="100%" cellspacing="5" cellpadding="0">
					<tr>
						<td align="right" width="10%">实例号:</td>
						<td align="left" width="40%"><span id="cancel-approvedinstanceName"></span></td>
						<td align="right" width="10%">所有者:</td>
						<td align="left" width="20%"><span id="cancel-approvedowner"></span></td>
					</tr>
					<tr>
						<td align="right">所属项目:</td>
						<td align="left"><span id="cancel-approvedMgtObjName"></span></td>
						<td align="right">&nbsp;</td>
						<td align="left">&nbsp;</td>
					</tr>
				</table>
			</td></tr>
			<tr><td>
			    <!-- 规格变更grid -->
				<div id="cancel-approvedspecGrid"></div>
			</td></tr>
		</table>
	</div>
	
	
	</div>

	
	
	<div id="approvedHistoryWindow">
	<div id="approvedHistoryForm" style="overflow: hidden;">
		<div style="border: 0px solid gray; width: 100%; height: 320px;position:relative;">
			<img id="processPic1"
				width="100%" height="320px"
				src="" data-bind='event: {load: showApprovedHistory}'/>
			<!-- 给执行的节点加框 -->
			<div id="processTrace1"
				style="position: absolute; border: 2px solid red;"></div>
		</div>
		<div id="approvedHistoryGrid"></div>

	</div>
	</div>
	</div>	
	    <div style="text-align: right">
			<input style="margin-right: 6px;margin-top: 6px;" id="approvedDetailCancel" type="button" value="关闭" />
		</div>
	</div>
	</div>	
	</div>	

<script type="text/javascript">
// 收缩展开效果
$(document).ready(function(){
	$("#specapproveddetailHeader").click(function(){
		$("#serviceSpecGrid").toggle();
	})   
});
</script>