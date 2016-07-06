<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Expires" CONTENT="-1"> 
<style type="text/css">
#testForm {
	width: 100%;
}

#testForm .panel {
	width: 98%;
	border-bottom: 1px solid #DADADA;
	border-left: 1px solid #DADADA;
	border-right: 1px solid #DADADA;
	border-top: 1px solid #DADADA;
	box-shadow: #DADADA 0px 3px 3px;
	position: relative;
	padding: 1%;
}

</style>

<div id="orderDetailWindow">
		<div>查看订单详情</div>
		<div id="testForm">
		<div class="panel" style="margin-bottom: 10px">
			<p
				style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
				<span id="box">订单基本信息</span>
			</p>
			<hr />
				<div style="overflow: hidden; height: 25.5%">
					<table width="100%" height="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right" width="120">订单编号1：</td>
							<td align="left" width="100"><span id="orderNumber"></span></td>
							<!-- 
							<td align="right">所属租户：</td>
							<td align="left"><span id="ownerTenant"></span></td>
							 -->
							<td align="right">所有者：</td>
							<td align="left"><span id="owner"></span></td>
						</tr>
						<tr>
							<td align="right">订单状态：</td>
							<td align="left"><span id="orderStatus"></span></td>
							<td align="right">开通时间：</td>
							<td align="left"><span id="openTime"></span></td>
						</tr>
					</table>

				</div>
			</div>
				<div class="panel" style="margin-bottom: 10px;">
			<p
				style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
				<span id="box">订单明细列表</span>
			</p>
			<hr />
				<div>
				<div id='view-orderDetailGrid'></div>
				</div>
			</div>
			<div style="width: 100%; height: 30px; text-align: right">
				<input style="margin-top: 5px;" id="orderDetailCancel" type="button"
					value="关闭" />
			</div>
		</div>
	</div>

<script type="text/javascript">
	$(function(){


	});

</script>
