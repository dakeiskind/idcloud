<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style type="text/css">
	#datacenterBaseInfo td{
		height:25px;
		border-bottom:1px solid #E9E9E9;
	}
	#datacenterBaseInfo tr:hover{
		background:#E6E6E6;
	}  
</style>
  <div id="resDChostSummary" style="width:100%;height:100%;">
  	<div style="margin:0px;padding:0px;height:10px;"></div>
  	<div style="width:98%;height:140px;margin-left:1%;">
  		<div class="customPanel" style="width:40.3%;height:125px;float:left;z-index:999">
  		    <div class="title">&nbsp;&nbsp;基本信息</div>   
	        <div>
	        	<table id="datacenterBaseInfo" border="0" width="100%" cellspacing="0" cellpadding="5">
	        		<tr>
	        			<td align="right" width="100px" height="25px">名称:</td>
	        			<td align="left">
	        				<span id="resTopologyName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">类型:</td>
	        			<td align="left">
	        				<span>存储类别</span>
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
	  		<div class="customPanel" style="width:58%;height:125px;margin-left:1%;float:left">
	  		    <div class="title">&nbsp;&nbsp;操作</div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<td align="left" width="33%" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddRscStorageWindowUsingByNoGrid()"><i class="icons-blue icon-arrows-ccw"></i>&nbsp;添加存储&nbsp;&nbsp;&nbsp;</span></td>	
	        			<td align="left" width="33%" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popRemoveRscStorageWindow()"><i class="icons-blue icon-cog"></i>&nbsp;移除存储&nbsp;&nbsp;&nbsp;</span></td>
	        			<td align="left" width="33%"height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeRscItem()"><i class="icons-blue icon-cancel-3"></i>&nbsp;删除存储分类</span></td>
	        		</tr>
	        		<tr>
	        			<td align="left" width="33%" height="25px" id="isStorage"></td>	
	        			<td align="left" width="33%" height="25px"></td>
	        			<td align="left" width="33%" height="25px"></td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	<div style="position:relative;width:98%;height:370px;margin-left:1%;">
  		<div class="customPanel" style="width:58%;height:350px;margin-left:41.7%;">
  		    <div class="title">&nbsp;&nbsp;资源统计
  		    <font style="cursor:pointer;position:absolute;right:5px" onclick="refreshStoragePie()"><i class="icons-blue icon-arrows-cw"></i></font>
  		    </div>   
	        <div>
	        	<div id='StorageCountChart' style="width:49%; height:320px;float:left;border:0px"></div>	
	        	<div id='StorageChart' style="width:50%; height:320px;float:left;border:0px"></div>	
	        </div>
  		</div>
  	</div>
  </div>
  
  
  
<script type="text/javascript">
	
	function initStorageRscSummary(){
		
		// 给基本信息赋值
		var rscIndex = new rscIndexMgtModel();
		rscIndex.setRscBasicInfo();
		
		var storageData = initResStorageAllotInRsc();
		// 初始化存储资源监控信息值
   		var storage = new storageConfigMgtModel();
   		var storagedata = storage.StorageResourcesStatisticsInRsc(resTopologySid);
   		
		// 初始化chart图
		initStorageChart(storageData);
		initStorageCountChart(storagedata);
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
             localdata: data[1]
         };
         var dataAdapter = new $.jqx.dataAdapter(source, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); } });
         // prepare jqxChart settings
         var settings = {
             title: "存储资源统计",
             description: "(总数:"+data[0]+"GB)",
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
         $('#StorageChart').jqxChart(settings);
	 }
	 
	 // 初始化虚拟机图表
	 function initStorageCountChart(data){
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
         $('#StorageCountChart').jqxChart(settings);
	 }
	 
	 function initResStorageAllotInRsc(){
		 var all = new Array();
		 var arr = new Array();
		 Core.AjaxRequest({
	 			url : ws_url + "/rest/storage/rsc",
	 			type:'post',
	 			params:{
	 				resStorageClassSid : resTopologySid
	 			},
	 			async:false,
	 			callback : function (data) {
	 				var storageTotalSize=0,storageAllotSize=0;
	 				for(var i=0;i<data.length;i++){
	 					storageTotalSize +=data[i].totalCapacity;
	 					storageAllotSize +=data[i].totalCapacity-data[i].availableCapacity;
	 				}
	 				 $("#storageTotalCapacity").html(storageTotalSize+"GB");
	 		 		 $("#storageAllotAllot").html(storageAllotSize+"GB");
	 		 		 $("#storageAllotProgressBar").html((storageAllotSize/storageTotalSize*100).toFixed(2)+"%");
	 		 		 all.push(storageTotalSize);
	 		 		 var name = ["未使用","已使用"];
	 		 		 var value = [storageTotalSize-storageAllotSize,storageAllotSize];
	 		 		 for(var j=0;j<2;j++){
	 		 			var obj = new Object();
	 		 			obj.name = name[j];
	 		 			obj.value = value[j];
	 		 			arr.push(obj);
	 		 		 }
	 			}
		  });
		 all.push(arr);	
		 return all;
	 }
	 
	 // 刷新存储资源统计
	 function refreshStoragePie(){
		 var storageData = initResStorageAllotInRsc();
		 // 初始化chart图
		 initStorageChart(storageData);
	 }
</script>

