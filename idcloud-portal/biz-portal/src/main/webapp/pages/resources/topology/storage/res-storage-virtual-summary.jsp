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
  	<div style="width:98%;height:400px;margin-left:1%;">
  		<div class="customPanel" style="width:40.3%;height:250px;float:left;z-index:999">
  		    <div class="title">&nbsp;&nbsp;基本信息</div>   
	        <div>
	        	<table id="datacenterBaseInfo" border="0" width="100%" cellspacing="0" cellpadding="5">
	        		<tr>
	        			<td align="right" width="100px" height="25px">存储名称:</td>
	        			<td align="left">
	        				<span id="storageName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">存储类型:</td>
	        			<td align="left">
	        				<span id="storageType"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">存储类别:</td>
	        			<td align="left">
	        				<span id="storageCategoryName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">总容量(GB):</td>
	        			<td align="left">
	        				<span id="totalCapacity"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">可用容量(GB):</td>
	        			<td align="left">
	        				<span id="availableCapacity"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">置备容量(GB):</td>
	        			<td align="left">
	        				<span id="provisionCapacity"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">分配容量(GB):</td>
	        			<td align="left">
	        				<span id="allotCapacity"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">分配率(%):</td>
	        			<td align="left">
	        				<span id="storageRate"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">状态:</td>
	        			<td align="left">
	        				<span id="statusName"></span>
	        			</td>
	        		</tr>
	        		
	        	</table>
	        </div>
  		</div>
		
		<div class="customPanel" style="width:58%;height:350px;margin-left:41.7%;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
	        <div>
	        	<div id='StorageChart' style="width:49%; margin:0 auto;height:320px;border:0px"></div>
	        </div>
  		</div>
		
  	</div>

  </div>
  
<script type="text/javascript">
	
	function initStorageSummary(){
		
		var storageData = null;
		Core.AjaxRequest({
 			url : ws_url + "/rest/storage/"+resTopologySid,
 			type:'get',
 			async:false,
 			callback : function (data) {
 				storageData = data;
 			}
	    });
		
		// 设置基本信息
		setStorageBasicInfo(storageData);
		
		// 存储统计信息
		var sData = initResStorageAllotInStorage(storageData);
		
		// 初始化chart图
		initStorageChart(sData);
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
	 
	 // 初始化基本信息
	 function setStorageBasicInfo(data){
		 $("#storageName").html(data.storageName);
		 $("#storageType").html(data.storageType);
		 $("#allotCapacity").html(data.allotCapacity);
		 $("#storageCategoryName").html(data.storageCategoryName);
		 $("#totalCapacity").html(data.totalCapacity);
		 $("#availableCapacity").html(data.availableCapacity);
		 $("#statusName").html(data.statusName);
		 $("#provisionCapacity").html(data.provisionCapacity);
		 $("#storageRate").html(data.storageRate);
	 }
	 
	 function initResStorageAllotInStorage(data){
			var all = new Array();
			var arr = new Array();
			var storageTotalSize=0,storageAllotSize=0;
			if(data.totalCapacity == null ||data.totalCapacity == ""){
				storageTotalSize =0;
				storageAllotSize =0;
			}else{
				 if(data.availableCapacity == null ||data.availableCapacity == ""){
					 storageTotalSize = 0;
					 storageAllotSize = 0;
				 }else{
					 storageTotalSize =data.totalCapacity;
					 storageAllotSize =data.totalCapacity-data.availableCapacity;
				 }
			}
			
	 		 all.push(storageTotalSize);
	 		 var name = ["未使用","已使用"];
	 		 var value = [storageTotalSize-storageAllotSize,storageAllotSize];
	 		 for(var j=0;j<2;j++){
	 			var obj = new Object();
	 			obj.name = name[j]+"("+value[j]+")";
	 			obj.value = value[j];
	 			arr.push(obj);
	 		 }
			 all.push(arr);	
			 return all;
	 }
	 
</script>

