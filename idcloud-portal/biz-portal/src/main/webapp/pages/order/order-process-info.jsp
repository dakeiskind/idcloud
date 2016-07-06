<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">


<div id="orderApprovedRecordWindow">
	<div>查看订单审批记录</div>
	<div id="testForm">
		<div style="border: 1px solid gray; width: 100%; height: 60%; position:relative;">
			<img id="processPic" onload="if(this.width>780)this.width=780;if(this.height>250)this.height=250"
				src="" />
			<!-- 给执行的节点加框 -->
			<div id="processTrace" style="position: absolute; border: 2px solid red;"></div>
		</div>
		<div>
			<div id='orderApprovedRecordGrid'></div>
		</div>
		<div style="width: 100%; height: 30px; text-align: right">
			<input style="margin-top: 5px;" id="orderApprovedRecordCancel"
				type="button" value="关闭" />
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {

	});
</script>
