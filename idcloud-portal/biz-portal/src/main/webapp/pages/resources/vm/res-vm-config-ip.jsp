<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="vmConfigIpWindow">
	<div>调整IP地址</div>
	<div id="vmConfigIpForm" style="overflow: auto;">
		<input type="hidden" data-name="resVmSid" id="resVmSid" />
		<table id="ipTable" border="0" width="100%" cellspacing="5"
			cellpadding="0">
			<tr>
				<td align='right'><font style='color: red'></font>IP地址：</td>
				<td><input type='text' class='ipAddress jqx-widget-content jqx-widget-content-metro jqx-input jqx-input-metro jqx-widget jqx-widget-metro jqx-rc-all jqx-rc-all-metro' /> <a
					id='jqxTuidingBtn' onclick='appendTr($(this))'
					style='margin-left: -1px'>&nbsp;&nbsp;<i
						class='icons-blue icon-plus-2'></i></a></td>
			</tr>
		</table>
		<div style="width: 100%; padding-top: 10px; text-align: right">
			<input style="margin-right: 5px;" onclick='vmConfigIpSubmit()'
				type="button" id="vmConfigIpSave" value="保存" /> <input
				id="vmConfigIpCancel" type="button" value="取消" />
		</div>
	</div>
</div>
<script type="text/javascript">
	var vmconfigip = new vmConfigIpModel();
	vmconfigip.initPopWindow();
	// 	    vmconfigip.initInput();
	vmconfigip.initValidator();
</script>