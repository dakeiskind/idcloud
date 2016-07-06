<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
	
	<div style="width: 100%; height: 620px; margin-top: 50px;display: none;" id="mainContainer">
            <div id="window" style="display: none">
                <div id="windowHeader">
                    <span>
                        	审核详情
                    </span>
                </div>
                <div style="overflow: hidden;" id="windowContent">
                <div id="tab" style="width: 100%; height: 480px;">
                        <ul style="margin-left: 6px;">
							<li style="margin-left: 6px;"><i class='icons-blue icon-tasks'></i>审核明细</li>
		                	<li><i class='icons-blue icon-list'></i>审核历史</li>
                        </ul>
                        
		<div id="approveDetailWindow" style="display:none">
		<div class="title" style="margin-left: 6px;margin-top: 6px;height:20px">&nbsp;</div>
		<div id="approveDetailForm" style="overflow: hidden; position: relative;height:480px">
		<table id="apporveTargetInfo" border="0" width="100%" cellspacing="5" cellpadding="0">
			<tr><td colspan="2">
				<table border="0" width="100%" cellspacing="5" cellpadding="0">
					<tr>
						<td align="right">订单编号:</td>
						<td align="left"><span id="unapprove-detail-orderNumber"></span></td>
						<!-- 
						<td align="right">所属租户:</td>
						<td align="left"><span id="unapprove-detail-ownerTenant"></span></td>
						 -->
						<td align="right">商务合同号:</td>
						<td align="left"><span id="unapprove-detail-contractId"></span></td>
						<td align="right">项目立项号:</td>
						<td align="left"><span id="unapprove-detail-projectId"></span></td>
					</tr>
					<tr>
						<td align="right">所有者:</td>
						<td align="left"><span id="unapprove-detail-owner"></span></td>
						<td align="right">订单状态:</td>
						<td align="left"><span id="unapprove-detail-orderStatus"></span></td>
						<td align="right">开通时间:</td>
						<td align="left"><span id="unapprove-detail-openTime"></span></td>
					</tr>
					<tr>
							<td align="right">合同附件:</td>
							<td align="left" colspan="2">
								<a id="unapprove-contractFile" style="color: blue;"></a>
							</td>
							<td align="right">立项附件:</td>
							<td align="left" colspan="2">
								<a id="unapprove-projectFile" style="color: blue;"></a>
							</td>
						</tr>
				</table>
			</td></tr>
			<tr><td colspan="2">
				<div id="unapprove-detail-orderDetailsGrid"></div>
			</td></tr>
			<tr><td colspan="2">             
			    	<div >
                    	<div id="jqxExpander" ><a href='#' style="theme: metro;">&nbsp;&nbsp;规格明细列表</a></div>    	
						<div id="unapprove-detail-serviceSpecGrid"></div>
					</div>
			</td></tr>
		</table>
		
		</div>
	</div>
	
 	<div id="unapproveHistoryWindow">
		<div id="unapproveHistoryForm" style="overflow: hidden;">
		<div style="border: 0px solid gray; width: 100%; height: 320px;position:relative;">
			<img id="processPic"
				width="100%" height="320px"
				src="" data-bind='event: {load: showUnapprovedHistory}'/>
			给执行的节点加框
			<div id="processTrace"
				style="position: absolute; border: 2px solid red;"></div>
		</div>
		<div id="unapproveHistoryGrid"></div>
		</div>
	</div> 	
    </div>
    
    <div style="text-align: right">
			<input style="margin-right: 6px;margin-top: 6px;" id="approveDetailCancel" type="button" value="关闭" />
		</div>
    </div>
    </div>
</div>

<script type="text/javascript">
// 收缩展开效果
 $(document).ready(function () {
    // Create jqxExpander    
    //$("#jqxExpander").jqxExpander({ width: '100%', toggleMode: "click",showArrow: true, theme: currentTheme});
    $("#jqxExpander").click(function(){
		$("#unapprove-detail-serviceSpecGrid").toggle();
	});  
}); 
</script>
	
		

