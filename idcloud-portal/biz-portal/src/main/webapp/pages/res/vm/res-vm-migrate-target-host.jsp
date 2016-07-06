<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="targetHostWindow">
	<div>选择目标主机</div>
	<div style="width: 100%; height: 100%">
		<div style="width: 98%; height: 80%; margin-left: 1%;">
			<div id='targetHostDatagrid' style="width: 100%; height: 450px;"></div>
		</div>
		<div style="width: 100%; padding-top: 10px; text-align: right;">

			<input style="margin-right: 5px;" type="button"
				onclick="selectTargetStorage()" id="targetHostSave" value="下一步" />
			<input id="targetHostCancel" type="button" value="取消" />
		</div>
	</div>
</div>

<script type="text/javascript">
		// 初始化主机列表
		var targetHost = new targetHostModel();
		targetHost.initPopWindow();
		targetHost.initHostDatagrid();
</script>