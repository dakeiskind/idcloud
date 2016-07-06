<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style type="text/css">
	#clusterBaseInfo td{
		border-bottom:1px solid #E9E9E9;
	}
	#clusterBaseInfo tr:hover{
		background:#E6E6E6;
	}  
</style>
  <div style="width:100%;height:100%;">
  	<div style="margin:0px;padding:0px;height:10px;"></div>
  	<div style="width:98%;height:160px;margin-left:1%;">
  		<div class="customPanel" style="width:55%;height:150px;float:left">
  		    <div class="title">&nbsp;&nbsp;基本信息</div>   
	        <div>
	        	<table id="clusterBaseInfo" border="0" width="100%" cellspacing="0" cellpadding="5">
	        		<tr>
	        			<td align="right" width="100px" height="25px">集群名称:</td>
	        			<td align="left">
	        				<span id="resTopologyName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">拓扑类型:</td>
	        			<td align="left">
	        				虚拟集群
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">HA功能:</td>
	        			<td align="left">
	        				<span id="openHA"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">描述:</td>
	        			<td align="left">
	        				<span id="description"></span>
	        			</td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
	  		<div class="customPanel" style="width:43.3%;height:150px;margin-left:1%;float:left">
	  		    <div class="title">&nbsp;&nbsp;操作</div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="asyncClusterInfo()"><i class="icon-arrows-ccw"></i>&nbsp;同步</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddHostToClusterWindow()"><i class="icon-cog"></i>&nbsp;关联主机</span></td>
	        			<!-- <td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddHostWindow()"><i class="icon-arrows-ccw"></i>&nbsp;添加新主机</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popRemoveHostOutClusterWindow()"><i class="icon-arrows-ccw"></i>&nbsp;移除主机</span></td> -->
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeCluster('VC')"><i class="icon-cancel-3"></i>&nbsp;删除集群</span></td>
<!-- 	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeDcAndCluster('03')"><i class="icon-cancel-3"></i>&nbsp;删除集群</span></td> -->
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	<div style="width:98%;height:340px;margin-left:1%;position:relative">
  		<div class="customPanel" style="width:55%;height:340px;float:left;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
	        <div>
	        	<div id='hostChart' style="width:32.5%;height:300px;float:left;border:0px;"></div>
	        	<div id='storageChart' style="width:32.5%;;margin-left:0.5%;height:300px;float:left;border:0px;"></div>	
	        	<div id='VmChart' style="width:32.5%;margin-left:0.5%; height:300px;float:left;border:0px;"></div>
	        </div>
  		</div>
  		
  		<div class="customPanel" style="width:43.3%;margin-left:1%;height:300px;float:left;">
  		    <div class="title">&nbsp;&nbsp;资源分配信息</div>   
	        <div>
	        	<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
	        	   			<td width="25%" align="right" height="25px">CPU总核数：</td>
	        	   			<td width="20%" align="left"><span id="cpuTotalAllot" style="color:#D45753"></span></td>
	        	   			<td width="35%" align="right">CPU分配核数：</td>
	        	   			<td width="20%" align="left"><span id="cpuAllotCapacity" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >CPU分配率：</td>
	        	   			<td height="30px" colspan="3">
	        	   			     <span id='cpuAllotProgressBar' style="color:orange"></span>
	        	   			 </td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" height="25px">内存总容量：</td>
	        	   			<td align="left"><span id="memoryTotalAllot" style="color:#D45753"></span></td>
	        	   			<td align="right">内存已分配容量：</td>
	        	   			<td align="left"><span id="memoryAllotCapacity" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >内存分配率：</td>
	        	   			<td height="30px" colspan="3">
	        	   			      <span id='memoryAllotProgressBar' style="color:orange"></span>
	        	   			</td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" height="25px">存储总容量：</td>
	        	   			<td align="left"><span id="storageTotalAllot" style="color:#D45753"></span></td>
	        	   			<td align="right">存储已分配容量：</td>
	        	   			<td align="left"><span id="storageAllotCapacity" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >存储分配率：</td>
	        	   			<td height="30px" colspan="3">
	        	   			      <span id='storageAllotProgressBar' style="color:orange"></span>
	        	   			</td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" height="25px">本地存储总容量：</td>
	        	   			<td align="left"><span id="localStorageTotalAllot" style="color:#D45753"></span></td>
	        	   			<td align="right">本地存储已分配容量：</td>
	        	   			<td align="left"><span id="localStorageAllotCapacity" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >本地存储分配率：</td>
	        	   			<td height="30px" colspan="3">
	        	   			      <span id='localStorageAllotProgressBar' style="color:orange"></span>
	        	   			</td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" height="25px">外置存储总容量：</td>
	        	   			<td align="left"><span id="shareStorageTotalAllot" style="color:#D45753"></span></td>
	        	   			<td align="right">外置存储已分配容量：</td>
	        	   			<td align="left"><span id="shareStorageAllotCapacity" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >外置存储分配率：</td>
	        	   			<td height="30px" colspan="3">
	        	   			      <span id='shareStorageAllotProgressBar' style="color:orange"></span>
	        	   			</td>
	        	   		</tr>
	        	   		
	        	   </table>	
	        </div>
  		</div>
  	</div>
  	<div>
  		<%@ include file="res-add-host-to-cluster-host.jsp"%>
  	</div>
  	<div>
  		<%@ include file="res-remove-host-out-cluster-host.jsp"%>
  	</div>
  	
  </div>
  
<script type="text/javascript">
	
	function initHostClusterSummary(){
		// 初始化主机监控信息值
   	 	var host = new virtualHostDatagridModel();
   		var hostData =  host.HostResourcesStatistics();
   		
   		// 初始化虚拟机监控信息值
   		var vm = new virtualVMDatagridModel();
   		var vmData = vm.getVmStatisticsInfo();
   		
   		// 初始化存储资源监控信息值
   		var storage = new storageConfigMgtModel();
   		var storageData = storage.StorageResourcesStatisticsVolumeInCluster(resTopologySid);
   		
   		// 初始化添加已有主机入集群
		var findHost = new addHostToClusterModel();
		findHost.initfindHostAddToClusterDatagrid();
		findHost.initPopWindow();
		findHost.searchfindHostAddToClusterData();
		
		// 初始化从集群移除主机
		var removeHost = new removeHostOutClusterModel();
		removeHost.initfindHostRemoveOutClusterDatagrid();
		removeHost.initPopWindow();
		removeHost.searchfindHostRemoveOutClusterData();
		
   		// 初始化存储分配信息
   		getStorageAllotInfo();
   		
		// 查询集群的详细信息，并设置集群的基本信息
		 var clusterData = initClusterBasicInfo();
		 $("#cpuTotalAllot").html((clusterData.cpuTotalCount==null)?"0核":clusterData.cpuTotalCount+"核");
		 $("#cpuAllotCapacity").html((clusterData.cpuAllotCount == null)?"0核":clusterData.cpuAllotCount+"核");
		 $("#memoryTotalAllot").html((clusterData.memoryTotalVolume == null)?"0GB":(clusterData.memoryTotalVolume/1024).toFixed(2)+"GB");
		 $("#memoryAllotCapacity").html((clusterData.memoryAllotVolume == null)?0:(clusterData.memoryAllotVolume/1024).toFixed(2)+"GB");
		 $("#cpuAllotProgressBar").html((clusterData.cpuAllotCount == null)?0:((clusterData.cpuAllotCount/clusterData.cpuTotalCount)*100).toFixed(2)+"%");
		 $("#memoryAllotProgressBar").html((clusterData.memoryAllotVolume == null)?0:(clusterData.memoryAllotVolume/clusterData.memoryTotalVolume*100).toFixed(2)+"%");
		
		initVMChart(vmData);
		initStorageChart(storageData);
		initHostChart(hostData);
	}
	
	function initClusterBasicInfo(){
		var clusterData;
		Core.AjaxRequest({
 			url : ws_url + "/rest/vcs/"+resTopologySid,
 			type:'get',
 			async:false,
 			callback : function (data) {
 				clusterData = data;
				console.log(JSON.stringify(data));
 				$("#resTopologyName").html(data.resTopologyName);
 				// 资源分区
 				$("#openHA").html((data.openHa == "0")?"关闭":"开启");
 				
 				if(data.description != null){
 					if(data.description.length > 100){
 						var desc = data.description.substring(0,100)+"...";
 						$("#description").attr("title",data.description);
 						$("#description").html(desc);
 					}else{
 						$("#description").html(data.description);
 					}
 				}
 			}
		 });
		return clusterData;
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
                                     labelRadius: 90,
                                     initialAngle: 15,
                                     radius: 80,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         // setup the chart
         $('#VmChart').jqxChart(settings);
	 }
	 
	 // 初始化存储图表
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
             title: "外置存储容量统计",
             description: "(总容量:"+data.storageCount+"GB)",
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
                                     labelRadius: 90,
                                     initialAngle: 15,
                                     radius: 80,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         // setup the chart
         $('#storageChart').jqxChart(settings);
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
                                     labelRadius: 90,
                                     initialAngle: 15,
                                     radius: 80,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         // setup the chart
         $('#hostChart').jqxChart(settings);
	 }
	 
	// 查询出存储的分配信息
	function getStorageAllotInfo(){
		 Core.AjaxRequest({
	 			url : ws_url + "/rest/storages/statistical/cluster/allot/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				if(null!=data){
	 					$("#storageTotalAllot").html(data.totalCapacity+"GB");
		 				$("#storageAllotCapacity").html((data.allotCapacity == null ? "0":data.allotCapacity) +"GB");
		 				$("#storageAllotProgressBar").html((data.allotRate == null ? "0.00":data.allotRate) +"%");
		 				
		 				$("#localStorageTotalAllot").html(data.localTotalCapacity+"GB");
		 				$("#localStorageAllotCapacity").html((data.localAllotCapacity == null ? "0":data.localAllotCapacity) +"GB");
		 				$("#localStorageAllotProgressBar").html((data.localAllotRate == null ? "0.00":data.localAllotRate) +"%");
		 				
		 				$("#shareStorageTotalAllot").html(data.shareTotalCapacity+"GB");
		 				$("#shareStorageAllotCapacity").html((data.shareAllotCapacity == null ? "0":data.shareAllotCapacity) +"GB");
		 				$("#shareStorageAllotProgressBar").html((data.shareAllotRate == null ? "0.00":data.shareAllotRate) +"%");
	 				}else{
	 					$("#storageTotalAllot").html("0GB");
		 				$("#storageAllotCapacity").html("0GB");
		 				$("#storageAllotProgressBar").html("0.00%");
		 				
		 				$("#localStorageTotalAllot").html("0GB");
		 				$("#localStorageAllotCapacity").html("0GB");
		 				$("#localStorageAllotProgressBar").html("0.00%");
		 				
		 				$("#shareStorageTotalAllot").html("0GB");
		 				$("#shareStorageAllotCapacity").html("0GB");
		 				$("#shareStorageAllotProgressBar").html("0.00%");
	 				}
	 			 }
		  });
	}
	 
	 function asyncClusterInfo() {
			var clusterData;
			Core.AjaxRequest({
	 			url : ws_url + "/rest/vcs/asyncClusterInfo/"+resTopologySid,
	 			type:'get',
	 			callback : function (data) {
	 				
	 				$("#cluster_summary").load(ctx+"/pages/resources/topology/cluster/res-cluster-virtual-summary.jsp",function(){
	 					initHostClusterSummary();
                	});
	 				
	 			}
			 }); 
		//	refreshTree();
	 }
	
</script>

