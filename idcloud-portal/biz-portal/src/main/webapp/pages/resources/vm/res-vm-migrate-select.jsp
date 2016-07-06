<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="migrateSelectWindow">
	<div>选择迁移类型</div>
	<div style="width: 100%; height: 100% ;margin-left:20px">
		<div style='margin-top: 10px;' id='jqxRadioButton'>
           更改主机</div>
        <div style='margin-top: 10px;' id='jqxRadioButton2'>
            <span>更改数据存储</span></div>
        <div style='margin-top: 10px;' id='jqxRadioButton3'>
            <span>更改主机和数据存储</span></div>
            
            
     <div style="width: 100%; padding-top: 10px; text-align: right">
			<input style="margin-right: 5px;" onclick='vmMigrateSubmit()'
				type="button" id="vmMigrateSave" value="确定" />
			<input id="vmMigrateCancel" type="button" value="取消" />
		</div>
	</div>
	
</div>
 <%@ include file="res-vm-migrate-target-host.jsp"%>
<%@ include file="res-vm-migrate-target-storage.jsp"%>
<script type="text/javascript">
		// 初始化主机列表
		var migratehost = new migrateSelectModel();
		migratehost.initPopWindow();
// 		targetHost.initHostDatagrid();
</script>