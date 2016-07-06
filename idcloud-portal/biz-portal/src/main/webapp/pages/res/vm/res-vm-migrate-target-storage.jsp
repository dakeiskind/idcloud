<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="targetStorageWindow">
	<div>选择目标存储</div>
	<div>
		<div style="width: 98%; height: 80%; margin-left: 1%;">
			<div id='targetStorageDatagrid' style="width: 100%; height: 450px;"></div>
		</div>
		<div style="width: 100%; padding-top: 10px; text-align: right;">

			<input style="margin-right: 5px;" type="button"
				onclick="migrate()" id="targetStorageSave" value="迁移" />
			<input id="targetStorageCancel" type="button" value="取消" />
		</div>
	</div>
</div>
<script type="text/javascript">
	var targetStorage = new targetStorageModel();
	targetStorage.initPopWindow();
	targetStorage.initStorageDatagrid();
</script>