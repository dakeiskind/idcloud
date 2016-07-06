<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="targetHostWindow">
	<div>选择目标主机</div>
	<div style="width: 100%; height: 100%">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
           			<td align="right">&nbsp;&nbsp;集群：</td>
           			<td>
           				<div id="search-cluster"></div>
           			</td>
           			<td><a onclick="searchMigrateHost()" id="search-host-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
	
		<div style="width: 98%; height: 80%; margin-left: 1%;">
			<div id='targetHostDatagrid' style="width: 100%; height: 450px;"></div>
		</div>
		<div style="width: 100%; padding-top: 10px; text-align: right;">
			
			<span id="nextButtonDiv">
				<input style="margin-right: 5px;" type="button"
					onclick="selectTargetStorage()" id="targetHostSave" value="下一步" />
			</span>
			<span id="migrateHostButtonDiv">
				<input style="margin-right: 5px;" type="button"
					onclick="migrateVmHost()" id="migrateVmHostSave" value="迁移" />
			</span>
			<input id="targetHostCancel" type="button" value="取消" />
		</div>
	</div>
</div>
 <%@ include file="res-vm-migrate-target-storage.jsp"%>
<script type="text/javascript">
		// 初始化主机列表
		var targetHost = new targetHostModel();
		targetHost.initPopWindow();
		targetHost.initHostDatagrid();
		targetHost.initMigrateInput();
</script>