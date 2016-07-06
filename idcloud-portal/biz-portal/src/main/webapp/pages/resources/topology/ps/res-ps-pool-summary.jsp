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
  		<div class="customPanel" style="width:37.3%;height:200px;float:left;z-index:999">
  		    <div class="title">&nbsp;&nbsp;基本信息</div>   
	        <div>
	        	<table id="datacenterBaseInfo" border="0" width="100%" cellspacing="0" cellpadding="5">
	        		<tr>
	        			<td align="right" width="100px" height="25px">名称:</td>
	        			<td align="left">
	        				<span id="resTopologyName">存储资源池</span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">类型:</td>
	        			<td align="left">
	        				<span id="virtualPlatformType">资源池</span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">分配策略:</td>
	        			<td align="left">
	        				<span id="resPcPolicy"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">分配方式:</td>
	        			<td align="left">
	        				<span id="resPcMode"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">分配比率:</td>
	        			<td align="left">
	        				<span id="resPcRate"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">分配阀值:</td>
	        			<td align="left">
	        				<span id="resPcThreshold"></span>
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
	  		<div class="customPanel" style="width:61%;height:125px;margin-left:1%;float:left">
	  		    <div class="title">&nbsp;&nbsp;操作
<!-- 	  		         <font style="position:absolute;right:5px"><i class="icons-blue icon-help"></i></font> -->
	  		    </div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>	
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popSetAdvanceConfigWindow()"><i class="icons-blue icon-cog"></i>&nbsp;高级设置</span></td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	<div style="position:relative;width:98%;height:370px;margin-left:1%;">
  		<div class="customPanel" style="width:61%;height:350px;margin-left:38.5%;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
	        <div>
	        	<div id='storageChart' style="width:49%; float:left; height:320px;border:0px"></div>
	        	<div id='storageCountChart' style="width:50%;float:left; height:300px;border:0px"></div>	
	        </div>
  		</div>
  		<div class="customPanel" style="position:absolute;left:0;top:75px;width:37.3%;height:190px;">
  		    <div class="title">&nbsp;&nbsp;资源使用及分配</div>   
	        <div>
	        	   <table border="0" width="100%" cellspacing="0" cellpadding="0">
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
  </div>
  
<script type="text/javascript">

	function initPoolPSSummary(){
   		// 初始化基本信息
   		findPcConfigInfo();
		// 初始化资源分配	
   		initResStorageAllot();
		
   		var sData = new storageConfigMgtModel();
   		var stoData = sData.StorageResourcesStatisticsInTopologyRz(resTopologySid);
   		
   		var stoDataPs = sData.StorageResourcesStatisticsVolumeInTopologyRz(resTopologySid);
		
		// 初始化chart图
		initStorageChart(stoDataPs);
		initStorageCountChart(stoData);
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
             title: "外置存储统计",
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
                                     labelRadius: 125,
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
         $('#storageChart').jqxChart(settings);
	 }
	 
	// 初始化存储个数图表
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
             title: "存储池资源统计",
             description: "(总容量:"+data.storageCount+"个)",
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
                                     labelRadius: 125,
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
         $('#storageCountChart').jqxChart(settings);
	 }
	 
	// 查询计算资源池的配置信息
	 function findPcConfigInfo(){
		 Core.AjaxRequest({
	 			url : ws_url + "/rest/topologyConfigs",
	 			type:'post',
	 			params:{
	 				resTopologySid : resTopologySid
	 			},
	 			async:false,
	 			callback : function (data) {
	 				if(data != null){
	 					for(var i=0;i<data.length;i++){
		 					if(data[i].configKey == "allocation_policy"){
			 					$("#resPcPolicy").html(data[i].configValueName);
			 				}else if(data[i].configKey == "allocation_mode"){
			 					$("#resPcMode").html("按容量");
			 				}else if(data[i].configKey == "allocation_rate"){
			 					$("#resPcRate").html(data[i].configValue);
			 				}else if(data[i].configKey == "allocation_threshold"){
			 					$("#resPcThreshold").html(data[i].configValue);
			 				}
		 				}
	 				}
	 				
	 			 }
		  });
	 }
	 // 初始化存储的基本信息
	 function initResStorageAllot(){
		 Core.AjaxRequest({
	 			url : ws_url + "/rest/storages/statistical/rz/allot/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 			    if(data != null){
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
	 
</script>

