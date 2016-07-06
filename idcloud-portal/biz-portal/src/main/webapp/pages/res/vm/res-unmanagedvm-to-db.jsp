<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="unMgtVmToDbWindow">
	<div>纳管虚拟机</div>
	<div id="unMgtVmToDbForm" style="overflow: hidden;">
		<div class="customPanel" style="width: 100%; height: 200px;">
			<div class="title">虚拟机信息</div>
			<div>
				<table border="0" width="100%" cellspacing="7" cellpadding="0">
					<tr>
						<td align="right" width="100"><font style="color: red">*</font>虚拟机名称：</td>
						<td align="left"><span id="view-vm-name" data-bind="name"></span></td>
						<td align="right" width="130">操作系统：</td>
						<td align="left" width="200"><span id="view-vm-system" data-bind="guestType"></span></td>
						<td align="right" width="100">所属主机：</td>
						<td align="left"><span id="view-belong-vm" data-bind="hostName"></span></td>

					</tr>
					<tr>
						<td align="right">CPU（核）：</td>
						<td align="left"><span id="view-vm-cpu" data-bind="numCpu"></span></td>
						<td align="right">内存大小（MB）：</td>
						<td align="left"><span id="view-vm-memory" data-bind="memorySizeMB"></span></td>
						<td align="right">IP：</td>
						<td align="left"><span id="view-wm-ip"></span></td>
					</tr>
					<tr>
						<td align="right">状态：</td>
						<td align="left"><span id="view-vm-statusName" data-bind="status"></span></td>
					</tr>
					<tr>
						<td colspan="6">
							<div id='vmVDisksgrid' style="width: 100%; height: 450px;"></div>
						</td>
					</tr>
				</table>

			</div>
		</div>

		<div class="customPanel"
			style="width: 100%; height: 125px; margin-top: 10px;">
			<div class="title">纳管信息</div>
			<div>
				<table border="0" width="100%" cellspacing="5" cellpadding="0">
					<tr>
						<td align="right" width="100"><font style="color: red">*</font>租户：</td>
						<td align="left"><div id="managed-tenant" data-bind="tenantSid"></div></td>
						<td align="right" width="100"><font style="color: red">*</font>用户：</td>
						<td align="left"><div id="managed-user" data-bind="ownerId"></div></td>
						<td align="right" width="100"><font style="color: red">*</font>IP：</td>
						<td align="left"><div id="managed-ip" data-bind="resIpSid"></div></td>
					</tr>
					<tr>
						<td align="right" width="100"><font style="color: red">*</font>操作系统：</td>
						<td align="left"><div id="managed-vm-system" data-bind="platformVmSystem"></div></td>
						<td align="right" width="100"><font style="color: red"></font>计费类型：</td>
						<td align="left"><div id="managed-billingType" data-bind="billingType"></div></td>
						<td align="right" width="100"><font style="color: red"></font>购买时长：</td>
						<td align="left"><div id="managed-buy-time" data-bind="buyTimes"></div></td>
					</tr>
				</table>
			</div>
		</div>
		<div style="width: 100%; padding-top: 10px; text-align: right">
			<input style="margin-right: 5px;" onclick='unMgtVmToDbSave()'
				type="button" id="unMgtVmToDbSave" value="保存" /> <input
				id="unMgtVmToDbCancel" type="button" value="取消" />
		</div>
	</div>
</div>

<script type="text/javascript">
	var ummgtvmtodb = new umMgtVmToDb();
	ummgtvmtodb.initPopWindow();
	ummgtvmtodb.initValidator();
</script>