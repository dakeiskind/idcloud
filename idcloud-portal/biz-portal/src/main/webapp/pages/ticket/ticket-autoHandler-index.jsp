<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<div id="tab-autoHandler-div">
	<table id="openX86VmResSelect" class="viewTable" border="0" width="100%" cellspacing="4" cellpadding="0">
		<input type="hidden" id="processType" />
		<input type="hidden" id="ticket-autoHandler-resType" />
		<input type="hidden" id="ticket-autoHandler-ve" />
		<input type="hidden" id="ticket-autoHandler-mgtObjSid" />
		<tr class="ticket-autoHandler-resPartition" style="display:none" >
			<td align="right" width="90">
				分区类型:
			</td>
			<td width="210">
				<div id="ticket-autoHandler-partitionType" style="float: left;margin-left: 3px;"></div>
			</td>
			<td align="right" width="90">
			</td>
			<td>
			</td>
		</tr>
		<tr id="ticket-autoHandler-rescomuteTr" style="display:none">
			<td align="right" width="90">计算资源集:</td>
			<td colspan="3">
				<input id="ticket-autoHandler-resMgtObjSid" type="hidden">
				<input id="ticket-autoHandler-resVe" type="hidden">
				<input id="ticket-autoHandler-rescomuteid" type="hidden" >
				<input id="ticket-autoHandler-rescomuteType" type="hidden" >
				<div id="ticket-autoHandler-rescomuteLabel">
					<div id="ticket-autoHandler-rescomuteTree"></div>
				</div>
			</td>
		</tr>
		<tr class="ticket-autoHandler-resSwitch" style="display:none" >
			<td align="right" width="90">
				虚拟机交换机:
			</td>
			<td width="210">
				<div id="ticket-autoHandler-virtualSwitch" style="float: left;margin-left: 3px;"></div>
			</td>
			<td align="left" width="90">
			</td>
			<td>
			</td>
		</tr>
		<tr class="ticket-autoHandler-resHba" style="display:none" >
			<td align="right" width="90">
				根分区hba卡:
			</td>
			<td width="210">
				<div id="ticket-autoHandler-rootHbaCard" style="float: left;margin-left: 3px;"></div>
			</td>
			<td align="right" width="90">
				网络hba卡
			</td>
			<td>
				<div id="ticket-autoHandler-networkHbaCard" style="float: left;margin-left: 3px;"></div>
			</td>
		</tr>
		<tr id="ticket-autoHandler-wanTr" style="display:none" >
			<td align="right" width="90">业务外网:</td>
			<td>
				<div id="ticket-autoHandler-vLanIDO" style="float: left;margin-left: 3px;"></div>
			</td>
			<td align="right" width="90">可选IP地址:</td>
			<td>
				<div id="ticket-autoHandler-wanIps" style="float: left;margin-left: 3px;"></div>
			</td>
		</tr>
		<tr id="ticket-autoHandler-lanTr" style="display:none">
			<td align="right" width="60">业务内网:</td>
			<td>
				<div id="ticket-autoHandler-vLanIDI" style="float: left;margin-left: 3px;"></div>
			</td>
			<td align="right" width="90">可选IP地址:</td>
			<td>
				<div id="ticket-autoHandler-lanIps" style="float: left;margin-left: 3px;"></div>
			</td>
		</tr>
		<tr>
			<td colspan="4" style="text-align: right;">
				<input onclick='resCheckSubmit()' type="button" id="resCheckSubmit" value="资源检查" />
				<input id="autoProcessTicket"  onclick='excecuteSubmit()' type="button" value="处理"/>&nbsp;
			</td>
		</tr>
	</table>
	<table id="changeResSelect" border="0" width="60%" cellspacing="4" cellpadding="0">
		<tr>
			<td style="text-align: right;">
				<input id="changeAutoProcessTicket" onclick='excecuteSubmit()' type="button" value="处理"/>&nbsp;
			</td>
		</tr>
	</table>
</div>
	