<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style type="text/css">
	#datacenterBaseInfo td{
		border-bottom:1px solid #E9E9E9;
	}
	#datacenterBaseInfo tr:hover{
		background:#E6E6E6;
	}
</style>
<div id="resDChostSummary" style="width:100%;height:100%;">
	<div style="margin:0px;padding:0px;height:10px;"></div>
	<div style="width:98%;height:185px;margin-left:1%;">
		<div class="customPanel" style="width:49.3%;height:175px;float:left">
			<div class="title">&nbsp;&nbsp;基本信息
				<font style="cursor:pointer;position:absolute;right:5px" onclick="setBizBasicInfo()"><i class="icons-blue icon-arrows-cw"></i></font>
			</div>
			<div>
				<table id="datacenterBaseInfo" border="0" width="100%" cellspacing="0" cellpadding="5">
					<!-- <tr>
                        <td align="right" width="100px" height="25px">业务名称:</td>
                        <td align="left">
                            <span id="bizName"></span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" width="80px" height="25px">维护人员:</td>
                        <td align="left">
                            <span id="maintainer"></span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" height="25px">维护人员电话:</td>
                        <td align="left">
                            <span id="maintainerPhone"></span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" width="80px" height="25px">项目经理:</td>
                        <td align="left">
                            <span id="pmName"></span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" height="25px">项目经理电话:</td>
                        <td align="left">
                            <span id="pmPhone"></span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" height="25px">描述:</td>
                        <td align="left">
                            <span id="description"></span>
                        </td>
                    </tr> -->
				</table>
			</div>
		</div>
		<div class="customPanel" style="width:49%;height:175px;margin-left:1%;float:left">
			<div class="title">&nbsp;&nbsp;资源占用信息</div>
			<div>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td width="25%" align="right" height="23px">CPU总核数：</td>
						<td width="25%" align="left"><span id="cpuTotalAllot" style="color:#D45753"></span></td>
						<td width="25%" align="right">CPU占用核数：</td>
						<td width="25%" align="left"><span id="cpuAllotCapacity" style="color:#D45753"></span></td>
					</tr>
					<tr>
						<td align="right" >CPU占用率：</td>
						<td height="25px" colspan="3">
							<span id='cpuAllotProgressBar' style="color:orange"></span>
						</td>
					</tr>
					<tr>
						<td align="right" height="23px">内存总容量：</td>
						<td align="left"><span id="memoryTotalAllot" style="color:#D45753"></span></td>
						<td align="right">内存已占用容量：</td>
						<td align="left"><span id="memoryAllotCapacity" style="color:#D45753"></span></td>
					</tr>
					<tr>
						<td align="right" >内存占用率：</td>
						<td height="25px" colspan="3">
							<span id='memoryAllotProgressBar' style="color:orange"></span>
						</td>
					</tr>
					<tr>
						<td align="right" height="23px">存储总容量：</td>
						<td align="left"><span id="storageTotalAllot" style="color:#D45753"></span></td>
						<td align="right">存储已占用容量：</td>
						<td align="left"><span id="storageAllotCapacity" style="color:#D45753"></span></td>
					</tr>
					<tr>
						<td align="right" >存储占用率：</td>
						<td height="25px" colspan="3">
							<span id='storageAllotProgressBar' style="color:orange"></span>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div style="width:98%;height:370px;margin-left:1%;">
		<div class="customPanel" style="width:99.6%;height:350px;float:left;">
			<div class="title">&nbsp;&nbsp;资源分布</div>
			<div>
				<div id='hostBizChart' style="width:25%; height:320px;float:left;border:0px"></div>
				<div id='storageBizChart' style="width:25%; height:320px;float:left;border:0px"></div>
				<div id='networkBizChart' style="width:25%; height:320px;float:left;border:0px"></div>
				<div id='vmBizChart' style="width:25%; height:320px;float:left;border:0px"></div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

	function initBizSummary(){
		// 设置业务视图的基本信息
		setBizBasicInfo();
		var hostData = hostResStatisticsInBiz();
		var storageData = storageResStatisticsInBiz();
		var networkData = networkResStatisticsInBiz();
		var vmData = vmResStatisticsInBiz();

		if(hostData.allotInfo.cpuTotalAllot == 0 || vmData.allotInfo.cpuAllotCapacity == 0){
			$("#cpuTotalAllot").html("0核");
			$("#cpuAllotCapacity").html("0核");
			$("#cpuAllotProgressBar").html("0.00%");
		}else{
			$("#cpuTotalAllot").html((hostData.allotInfo.cpuTotalAllot == null ? "0":hostData.allotInfo.cpuTotalAllot)+"核");
			$("#cpuAllotCapacity").html((vmData.allotInfo.cpuAllotCapacity == null ? "0":vmData.allotInfo.cpuAllotCapacity)+"核");
			$("#cpuAllotProgressBar").html((vmData.allotInfo.cpuAllotCapacity/hostData.allotInfo.cpuTotalAllot*100).toFixed(2)+"%");
		}

		if(hostData.allotInfo.memoryTotalAllot == 0 || vmData.allotInfo.memoryAllotCapacity == 0){
			$("#memoryTotalAllot").html("0GB");
			$("#memoryAllotCapacity").html("0GB");
			$("#memoryAllotProgressBar").html("0.00%");
		}else{
			$("#memoryTotalAllot").html((hostData.allotInfo.memoryTotalAllot == null ? "0":hostData.allotInfo.memoryTotalAllot)+"GB");
			$("#memoryAllotCapacity").html((vmData.allotInfo.memoryAllotCapacity == null ? "0":vmData.allotInfo.memoryAllotCapacity)+"GB");
			$("#memoryAllotProgressBar").html((vmData.allotInfo.memoryAllotCapacity/hostData.allotInfo.memoryTotalAllot*100).toFixed(2)+"%");
		}
		if(storageData.allotInfo.storageTotalAllot == 0 || vmData.allotInfo.diskSize == 0){
			$("#storageTotalAllot").html("0GB");
			$("#storageAllotCapacity").html("0GB");
			$("#storageAllotProgressBar").html("0.00%");
		}else{
			$("#storageTotalAllot").html(storageData.allotInfo.storageTotalAllot+"GB");
			$("#storageAllotCapacity").html(vmData.allotInfo.diskSize+"GB");
			$("#storageAllotProgressBar").html((vmData.allotInfo.diskSize/storageData.allotInfo.storageTotalAllot*100).toFixed(2)+"%");
		}

		// 初始化图表
		initVMChart(vmData);
		initHostChart(hostData);
		initNetworkChart(networkData);
		initStorageChart(storageData);
	}

	// 初始化虚拟机图表
	function initVMChart(data){
		var source =
		{
			datatype: "json",
			datafields: [
				{ name: 'name' },
				{ name: 'value' }
			],
			localdata: data.attr
		};
		var dataAdapter = new $.jqx.dataAdapter(source, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); } });
		// prepare jqxChart settings
		var settings = {
			title: "虚拟机资源统计",
			description: "(总数:"+data.vmCount+"台)",
			enableAnimations: true,
			showLegend: true,
			showBorderLine: false,
			legendPosition: { left: 200, top: 140, width: 50, height: 50 },
			padding: { left: 5, top: 5, right: 5, bottom: 5 },
			titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
			source: dataAdapter,
			colorScheme: 'scheme01',
			seriesGroups:
					[
						{
							type: 'pie',
							showLabels: true,
							series:
									[
										{
											dataField: 'value',
											displayText: 'name',
											labelRadius: 120,
											initialAngle: 15,
											radius: 100,
											centerOffset: 0,
											formatSettings: { sufix: '', decimalPlaces: 0 }
										}
									]
						}
					]
		};
		// setup the chart
		$('#vmBizChart').jqxChart(settings);
	}

	// 初始化主机图表
	function initHostChart(data){

		var source =
		{
			datatype: "json",
			datafields: [
				{ name: 'name' },
				{ name: 'value' }
			],
			localdata: data.attr
		};
		var dataAdapter = new $.jqx.dataAdapter(source, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); } });
		// prepare jqxChart settings
		var settings = {
			title: "主机资源统计",
			description: "(总数:"+data.hostCount+"台)",
			enableAnimations: true,
			showLegend: true,
			showBorderLine: false,
			legendPosition: { left: 200, top: 140, width: 50, height: 50 },
			padding: { left: 5, top: 5, right: 5, bottom: 5 },
			titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
			source: dataAdapter,
			colorScheme: 'scheme01',
			seriesGroups:
					[
						{
							type: 'pie',
							showLabels: true,
							series:
									[
										{
											dataField: 'value',
											displayText: 'name',
											labelRadius: 120,
											initialAngle: 15,
											radius: 100,
											centerOffset: 0,
											formatSettings: { sufix: '', decimalPlaces: 0 }
										}
									]
						}
					]
		};
		// setup the chart
		$('#hostBizChart').jqxChart(settings);
	}

	// 初始化虚拟机图表
	function initNetworkChart(data){
		var source =
		{
			datatype: "json",
			datafields: [
				{ name: 'name' },
				{ name: 'value' }
			],
			localdata: data.attr
		};
		var dataAdapter = new $.jqx.dataAdapter(source, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); } });
		// prepare jqxChart settings
		var settings = {
			title: "网络资源统计",
			description: "(总数:"+data.totalCount+"个)",
			enableAnimations: true,
			showLegend: true,
			showBorderLine: false,
			legendPosition: { left: 200, top: 140, width: 50, height: 50 },
			padding: { left: 5, top: 5, right: 5, bottom: 5 },
			titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
			source: dataAdapter,
			colorScheme: 'scheme01',
			seriesGroups:
					[
						{
							type: 'pie',
							showLabels: true,
							series:
									[
										{
											dataField: 'value',
											displayText: 'name',
											labelRadius: 120,
											initialAngle: 15,
											radius: 100,
											centerOffset: 0,
											formatSettings: { sufix: '', decimalPlaces: 0 }
										}
									]
						}
					]
		};
		// setup the chart
		$('#networkBizChart').jqxChart(settings);
	}

	// 初始化虚拟机图表
	function initStorageChart(data){
		var source =
		{
			datatype: "json",
			datafields: [
				{ name: 'name' },
				{ name: 'value' }
			],
			localdata: data.attr
		};
		var dataAdapter = new $.jqx.dataAdapter(source, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); } });
		// prepare jqxChart settings
		var settings = {
			title: "存储资源统计",
			description: "(总数:"+data.storageCount+"个)",
			enableAnimations: true,
			showLegend: true,
			showBorderLine: false,
			legendPosition: { left: 200, top: 140, width: 50, height: 50 },
			padding: { left: 5, top: 5, right: 5, bottom: 5 },
			titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
			source: dataAdapter,
			colorScheme: 'scheme01',
			seriesGroups:
					[
						{
							type: 'pie',
							showLabels: true,
							series:
									[
										{
											dataField: 'value',
											displayText: 'name',
											labelRadius: 120,
											initialAngle: 15,
											radius: 100,
											centerOffset: 0,
											formatSettings: { sufix: '', decimalPlaces: 0 }
										}
									]
						}
					]
		};
		// setup the chart
		$('#storageBizChart').jqxChart(settings);
	}

	// 设置业务的基本信息
	function setBizBasicInfo(){
		$("#datacenterBaseInfo").empty();
		Core.AjaxRequest({
							 url : ws_url + "/rest/mgtObj/load/"+resBizSid,
							 type:'post',
							 async:false,
							 callback : function (data) {
								 if(data!=null&&data.mgtObjExts!=null&&data.mgtObjExts.length>0){
									 for(var i=0;i<data.mgtObjExts.length;i++){
										 var tr = '<tr><td align="right" width="100px" height="25px">'+data.mgtObjExts[i]['attrName']+':</td>'+
												  '<td align="left"><span id="bizName">'+data.mgtObjExts[i]['attrValue']+'</span></td></tr>';
										 $("#datacenterBaseInfo").append(tr);
									 }
								 }else{
									 if(data.description==null){
										 data.description="";
									 }
									 var tr = '<tr><td align="right" width="100px" height="25px">名称:</td>'+
											  '<td align="left"><span id="bizName">'+data.name+'</span></td></tr>'+
											  '<tr><td align="right" width="100px" height="25px">描述:</td>'+
											  '<td align="left"><span id="bizName">'+data.description+'</span></td></tr>'
									 $("#datacenterBaseInfo").append(tr);
								 }
							 }
						 });
	}

	// 获取主机图表信息
	function hostResStatisticsInBiz(){

		var hostData = null;
		Core.AjaxRequest({
							 url : ws_url + "/rest/hosts/statistical/biz/"+resBizSid,
							 type:'get',
							 async:false,
							 callback : function (data) {

								 hostData = data;
							 }
						 });

		var data = new Object();
		data.hostCount = hostData.staTotalHost;
		data.attr = new Array();

		var value = [hostData.staNormalHost,hostData.staOffineHost,hostData.staMaintainHost,hostData.staFaultHost];
		var name =["正常","离线","维护","故障"];

		for(var i=0; i<4;i++){
			var obj = new Object();
			obj.name = name[i];
			obj.value = (value[i]==null || value[i]=="")?0 : value[i];
			data.attr.push(obj);
		}
		data.allotInfo = new Object();
		data.allotInfo.cpuTotalAllot =  (hostData.cpuCores==null || hostData.cpuCores=="")?0 : hostData.cpuCores;
		data.allotInfo.memoryTotalAllot = (hostData.memorySize==null || hostData.memorySize=="")?0 : hostData.memorySize;
		return data;
	};

	// 获取存储图表信息
	function storageResStatisticsInBiz(){
		var storageData = null;
		Core.AjaxRequest({
							 url : ws_url + "/rest/storages/statistical/biz/"+resBizSid,
							 type:'get',
							 async:false,
							 callback : function (data) {
								 storageData = data;
							 }
						 });
		var data = new Object();
		data.storageCount = storageData.staTotalStorage;
		data.attr = new Array();
		var value = [storageData.staUsableStorage,storageData.staFaultStorage,storageData.staUnusableStorage];
		var name =["可用","故障","不可用"];
		for(var i=0; i<3;i++){
			var obj = new Object();
			var val = (value[i]==null || value[i]=="")?0 : value[i];
			obj.name = name[i];
			obj.value = val;
			data.attr.push(obj);
		}
		data.allotInfo = new Object();
		data.allotInfo.storageTotalAllot = (storageData.totalCapacity==null || storageData.totalCapacity=="")?0 : storageData.totalCapacity;
		data.allotInfo.allotCapacity = (storageData.allotCapacity==null || storageData.allotCapacity=="")?0 : storageData.allotCapacity;

		return data;
	}

	// 获取业务下的网络图表信息
	function networkResStatisticsInBiz(){
		var networkData = null;
		Core.AjaxRequest({
							 url : ws_url + "/rest/networks/statistical/biz/"+resBizSid,
							 type:'get',
							 async:false,
							 callback : function (data) {
								 networkData = data;
							 }
						 });
		var data = new Object();
		data.totalCount = networkData.totalCount;
		data.attr = new Array();
		var value = [networkData.pniCount,networkData.pneCount];
		var name =["内部网络","外部网络"];
		for(var i=0; i<2;i++){
			var obj = new Object();
			var val = (value[i]==null || value[i]=="")?0 : value[i];
			obj.name = name[i];
			obj.value = val;
			data.attr.push(obj);
		}
		return data;
	}

	// 获取业务下的虚机图表信息
	function vmResStatisticsInBiz(){
		var vmData = null;
		Core.AjaxRequest({
							 url : ws_url + "/rest/resbiz/vm/statistical/biz/"+resBizSid,
							 type:'get',
							 async:false,
							 callback : function (data) {
								 vmData = data;
							 }
						 });

		var data = new Object();
		data.vmCount = vmData.staTotalVm;
		data.attr = new Array();

		var value = [vmData.staNormalVm,vmData.staDownVm,vmData.staPauseVm,vmData.staOthersVm];
		var name =["正常","关机","暂停","其他"];

		for(var i=0; i<4;i++){
			var obj = new Object();
			var val = (value[i]==null || value[i]=="")?0 : value[i];
			obj.name = name[i];
			obj.value = val;
			data.attr.push(obj);
		}
		data.allotInfo = new Object();
		data.allotInfo.cpuAllotCapacity = (vmData.cpuCores==null || vmData.cpuCores=="")?0 : vmData.cpuCores;
		data.allotInfo.memoryAllotCapacity = (vmData.memorySize==null || vmData.memorySize=="")?0 : vmData.memorySize;
		data.allotInfo.diskSize = (vmData.staVmDisk==null || vmData.staVmDisk=="")?0 : vmData.staVmDisk;
		return data;
	}
</script>

