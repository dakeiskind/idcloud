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
  		<div class="customPanel" style="width:40.3%;height:200px;float:left;z-index:999">
  		    <div class="title">&nbsp;&nbsp;基本信息</div>   
	        <div>
	        	<table id="datacenterBaseInfo" border="0" width="100%" cellspacing="0" cellpadding="5">
	        		<tr>
	        			<td align="right" width="100px" height="25px">名称:</td>
	        			<td align="left">
	        				<span id="resTopologyName">计算资源池</span>
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
	  		<div class="customPanel" style="width:58%;height:125px;margin-left:1%;float:left">
	  		    <div class="title">&nbsp;&nbsp;操作
	  		    </div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>	
	        			<td align="left" width="33%" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popSetAdvanceConfigWindow()"><i class="icons-blue icon-cog"></i>&nbsp;高级设置</span></td>
	        			<td align="left" width="33%" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popPcRelationCluster()"><i class="icons-blue icon-cog"></i>&nbsp;关联集群</span></td>
	        			<td align="left" width="33%" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popPcRelationHostWindow()"> <i class="icons-blue icon-cog"></i>&nbsp;关联主机</span></span></td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	<div style="position:relative;width:98%;height:370px;margin-left:1%;">
  		<div class="customPanel" style="width:58%;height:350px;margin-left:41.7%;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
	        <div>
	        	<div id='VmChart' style="width:50%; height:320px;float:left;border:0px"></div>	
	        	<div id='hostChart' style="width:49%; margin-left:0.5%;height:320px;float:left;border:0px"></div>
	        </div>
  		</div>
  		
  		<div class="customPanel" style="position:absolute;left:0;top:75px;width:40.3%;height:150px;">
  		    <div class="title">&nbsp;&nbsp;资源分配</div>   
	        <div>
	        	   <table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
	        	   			<td width="22%" align="right" height="25px">CPU总核数：</td>
	        	   			<td width="25%" align="left"><span id="cpuTotalCapacity" style="color:#D45753"></span></td>
	        	   			<td width="30%" align="right">CPU分配核数：</td>
	        	   			<td width="20%" align="left"><span id="cpuAllotAllot" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >CPU分配率：</td>
	        	   			<td height="30px" colspan="3">
	        	   			     <span id='cpuAllotProgressBar' style="color:orange"></span>
	        	   			 </td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" height="25px">内存总容量：</td>
	        	   			<td align="left"><span id="memoryTotalCapacity" style="color:#D45753"></span></td>
	        	   			<td align="right">内存已分配容量：</td>
	        	   			<td align="left"><span id="memoryAllotAllot" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >内存分配率：</td>
	        	   			<td height="30px" colspan="3">
	        	   			      <span id='memoryAllotProgressBar' style="color:orange"></span>
	        	   			</td>
	        	   		</tr>
	        	   </table>	
	        </div>
  		</div>
  		
  	</div>
  </div>
  
<script type="text/javascript">
	function initPoolPCSummary(){
		// 初始化主机监控信息值
   	 	var host = new virtualHostDatagridModel();
   		var hostData =  host.HostResStatisticsInPool(resTopologySid);
   		
   		// 初始化虚拟机监控信息值
   		var vm = new virtualVMDatagridModel();
   		var vmData = vm.getVmStatisticsInfoInResPool(resTopologySid);
   		
   		// 设置高级配置
   		findPcConfigInfo();
   		
   		// 初始化资源分配
   		initResAllot(hostData,vmData);
   		
		// 初始化chart图
		initVMChart(vmData);
		initHostChart(hostData);
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
         $('#VmChart').jqxChart(settings);
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
         $('#hostChart').jqxChart(settings);
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
	 				for(var i=0;i<data.length;i++){
	 					if(data[i].configKey == "allocation_policy"){
		 					$("#resPcPolicy").html(data[i].configValueName);
		 				}else if(data[i].configKey == "allocation_mode"){
		 					if("M" == data[i].configValue){
		 						$("#resPcMode").html("按内存");
		 					}else{
		 						$("#resPcMode").html("按CPU");
		 					}
		 				}else if(data[i].configKey == "allocation_rate"){
		 					$("#resPcRate").html(data[i].configValue);
		 				}else if(data[i].configKey == "allocation_threshold"){
		 					$("#resPcThreshold").html(data[i].configValue);
		 				}
	 				}
	 			 }
		  });
	 }
	 
	 // 初始化资源分配
	 function initResAllot(hData,vData){
		 if(resTopologyType == 'PCVP' || resTopologyType == 'PCP'){
			 $("#cpuTotalCapacity").html((hData.all.cpuTotalCounts == null)?0:hData.all.cpuTotalCounts+"个");
			 $("#cpuAllotAllot").html((vData.allot.cpuTotalCounts == null)?0:vData.allot.cpuTotalCounts+"个");
			 var t = vData.allot.cpuTotalCounts/hData.all.cpuTotalCounts;
	 		 $("#cpuAllotProgressBar").html(((vData.allot.cpuTotalCounts == null || hData.all.cpuTotalCounts == null)?0:t*100).toFixed(2)+"%");
			 
	 		 $("#memoryTotalCapacity").html((hData.all.memoryTotalSize == null)?0:hData.all.memoryTotalSize+"GB");
			 $("#memoryAllotAllot").html((vData.allot.memoryTotalSize == null)?0:vData.allot.memoryTotalSize+"GB");
			 $("#memoryAllotProgressBar").html(((vData.allot.memoryTotalSize == null || hData.all.memoryTotalSize == null)?0:vData.allot.memoryTotalSize/hData.all.memoryTotalSize*100).toFixed(2)+"%");
		 }else{
			 $("#cpuTotalCapacity").html((hData.all.cpuTotalCounts == null)?0:hData.all.cpuTotalCounts+"核");
			 $("#cpuAllotAllot").html((vData.allot.cpuTotalCounts == null)?0:vData.allot.cpuTotalCounts+"核");
			 var t = vData.allot.cpuTotalCounts/hData.all.cpuTotalCounts;
	 		 $("#cpuAllotProgressBar").html(((vData.allot.cpuTotalCounts == null || hData.all.cpuTotalCounts == null)?0:t*100).toFixed(2)+"%");
			 
	 		 $("#memoryTotalCapacity").html((hData.all.memoryTotalSize == null)?0:hData.all.memoryTotalSize+"GB");
			 $("#memoryAllotAllot").html((vData.allot.memoryTotalSize == null)?0:vData.allot.memoryTotalSize+"GB");
			 $("#memoryAllotProgressBar").html(((vData.allot.memoryTotalSize == null || hData.all.memoryTotalSize == null)?0:vData.allot.memoryTotalSize/hData.all.memoryTotalSize*100).toFixed(2)+"%");
		 }
		 
	 }
</script>

