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
  	<div style="width:98%;height:102px;margin-left:1%;">
  		<div class="customPanel" style="width:40.3%;height:200px;float:left;z-index:999">
  		    <div class="title">&nbsp;&nbsp;基本信息
  		    	<font style="cursor:pointer;position:absolute;right:5px" onclick="popEditVeWindow()"><i class="icons-blue icon-edit"></i></font>
  		    </div>   
	        <div>
	        	<table id="datacenterBaseInfo" border="0" width="100%" cellspacing="0" cellpadding="5">
	        		<tr>
	        			<td align="right" width="100px" height="25px">环境名称:</td>
	        			<td align="left">
	        				<span id="resTopologyName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">环境类型:</td>
	        			<td align="left">
	        				<span id="virtualPlatformType"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">环境版本:</td>
	        			<td align="left">
	        				<span id="virtualPlatformVersion"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">管理地址:</td>
	        			<td align="left">
	        				<span id="managementUrl"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">管理账号:</td>
	        			<td align="left">
	        				<span id="managementAccount"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">更新周期(小时):</td>
	        			<td align="left">
	        				<span id="updateCycle"></span>
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
	  		<div class="customPanel" style="width:58%;height:90px;margin-left:1%;float:left">
	  		    <div class="title">&nbsp;&nbsp;操作
<!-- 	  		         <font style="position:absolute;right:5px"><i class="icons-blue icon-help"></i></font> -->
	  		    </div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<td align="left" width="33%" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="synVeProvideToSummary()"><i class="icons-blue icon-arrows-ccw"></i>&nbsp;同步&nbsp;&nbsp;&nbsp;</span></td>	
<!-- 	        			<td align="left" width="33%" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popSetVeAdvanceConfigWindow()"><i class="icons-blue icon-cog"></i>&nbsp;高级设置&nbsp;&nbsp;&nbsp;</span></td> -->
	        			<td align="left" width="33%"height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="deleteVeInSummary()"><i class="icons-blue icon-cancel-3"></i>&nbsp;删除资源环境</span></td>
	        			<td align="left" width="33%" height="25px" id="isStorage"></td>	
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	<div style="position:relative;width:98%;height:370px;margin-left:1%;">
  		<div class="customPanel" style="width:58%;height:330px;margin-left:41.5%;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
	        <div>
	        	<div id='hostVeChart' style="width:32.5%; height:300px;float:left;border: 0;"></div>
	        	<div id='storageVeChart' style="width:32.5%; margin-left:0.5%;height:300px;float:left;border: 0;"></div>
	        	<div id='VmVeChart' style="width:32.5%;  margin-left:0.5%;height:300px;float:left;border: 0;"></div>	
	        </div> 
  		</div>
<!--   		<div class="customPanel" style="position:absolute;top:108px;width:40.3%;height:100px;"> -->
<!--   		    <div class="title">&nbsp;&nbsp;高级设置</div>  -->
<!--   		    <div> -->
<!-- 	        	<table id="datacenterBaseInfo" border="0" width="100%" cellspacing="0" cellpadding="5"> -->
<!-- 	        		<tr> -->
<!-- 	        			<td align="right" width="100px" height="25px">是否开启VNC:</td> -->
<!-- 	        			<td align="left"> -->
<!-- 	        				<span id="openVNC"></span> -->
<!-- 	        			</td> -->
<!-- 	        		</tr> -->
<!-- 	        		<tr> -->
<!-- 	        			<td align="right" width="80px" height="25px">开始端口:</td> -->
<!-- 	        			<td align="left"> -->
<!-- 	        				<span id="startPort"></span> -->
<!-- 	        			</td> -->
<!-- 	        		</tr> -->
<!-- 	        		<tr> -->
<!-- 	        			<td align="right" width="80px" height="25px">结束端口:</td> -->
<!-- 	        			<td align="left"> -->
<!-- 	        				<span id="endPort"></span> -->
<!-- 	        			</td> -->
<!-- 	        		</tr> -->
<!-- 	        	</table> -->
<!-- 	        </div> -->
<!--   		</div> -->
  		
  		<div class="customPanel" style="position:absolute;top:108px;width:40.3%;height:300px;">
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
	        	   			<td align="right" height="25px">共享存储总容量：</td>
	        	   			<td align="left"><span id="shareStorageTotalAllot" style="color:#D45753"></span></td>
	        	   			<td align="right">共享存储已分配容量：</td>
	        	   			<td align="left"><span id="shareStorageAllotCapacity" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >共享存储分配率：</td>
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
	
	function initHostVESummary(virtualType){
		// 如果是存储的虚拟化，就加个新增存储分类
		if(virtualType == "host"){
		}else if(virtualType == "storage"){
			 $("#isStorage").html('<span style="cursor:pointer;color:#3B9AFF" onclick="popAddRscWindow()"><i class="icons-blue icon-plus"></i>&nbsp;新增存储分类</span>');
		}
		// 初始化主机监控信息值
   	 	var host = new virtualHostDatagridModel();
   		var hostData =  host.HostResourcesStatistics();
   		
   		// 初始化虚拟机监控信息值
   		var vm = new virtualVMDatagridModel();
   		var vmData = vm.getVmStatisticsInfo();
   		
   		// 初始化存储监控信息
   		var storage = new storageConfigMgtModel();
   		var storageData = storage.StorageResourcesStatisticsVolumeInTopology(resTopologySid);
   		
   		//  编辑集群或者数据中心
		var ve = new virtualVeEditModel();
		ve.setVirtualDetailsInfo();
		
		// 初始化高级信息
//		findVeConfigInfo();
		
		// 初始化存储分配信息
		getStorageAllotInfo();
		
		// 初始化主机分配信息
		getHostAllotInfo();
		
		// 初始化chart图
		initVMChart(vmData);
		initStorageChart(storageData);
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
         $('#VmVeChart').jqxChart(settings);
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
         $('#storageVeChart').jqxChart(settings);
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
         $('#hostVeChart').jqxChart(settings);
	 }
	 
	 function findVeConfigInfo(){
		 Core.AjaxRequest({
	 			url : ws_url + "/rest/topologyConfigs",
	 			type:'post',
	 			params:{
	 				resTopologySid : resTopologySid
	 			},
	 			async:false,
	 			callback : function (data) {
	 				for(var i=0;i<data.length;i++){
	 					if(data[i].configKey == "open_vnc"){
	 						if(data[i].configValue == "true"){
	 							$("#openVNC").html("开启");
	 						}else{
	 							$("#openVNC").html("关闭");
	 						}
		 				}else if(data[i].configKey == "vnc_connect_port_start"){
		 					$("#startPort").html(data[i].configValue);
		 				}else if(data[i].configKey == "vnc_connect_port_end"){
		 					$("#endPort").html(data[i].configValue);
		 				}
	 				}
	 			 }
		  });
	 }
	
	// 查询出主机的分配信息
	function getHostAllotInfo(){
		 Core.AjaxRequest({
	 			url : ws_url + "/rest/hosts/statistical/topology/allot/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				$("#cpuTotalAllot").html(data.cpuTotalCores+"核");
	 				$("#cpuAllotCapacity").html((data.cpuAllotCores == null ? "0":data.cpuAllotCores) +"核");
	 				$("#cpuAllotProgressBar").html((data.cpuAllotRate == null ? "0.00":data.cpuAllotRate) +"%");
	 				
	 				$("#memoryTotalAllot").html(data.memorySize+"GB");
	 				$("#memoryAllotCapacity").html((data.memoryAllotSize == null ? "0":data.memoryAllotSize) +"GB");
	 				$("#memoryAllotProgressBar").html((data.memoryAllotRate == null ? "0.00":data.memoryAllotRate) +"%");
	 			 }
		  });
	}
	 
	// 查询出存储的分配信息
	function getStorageAllotInfo(){
		 Core.AjaxRequest({
	 			url : ws_url + "/rest/storages/statistical/topology/allot/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 			
	 				$("#storageTotalAllot").html(data.totalCapacity+"GB");
	 				$("#storageAllotCapacity").html((data.allotCapacity == null ? "0":data.allotCapacity) +"GB");
	 				$("#storageAllotProgressBar").html((data.allotRate == null ? "0.00":data.allotRate) +"%");
	 				
	 				$("#localStorageTotalAllot").html(data.localTotalCapacity+"GB");
	 				$("#localStorageAllotCapacity").html((data.localAllotCapacity == null ? "0":data.localAllotCapacity) +"GB");
	 				$("#localStorageAllotProgressBar").html((data.localAllotRate == null ? "0.00":data.localAllotRate) +"%");
	 				
	 				$("#shareStorageTotalAllot").html(data.shareTotalCapacity+"GB");
	 				$("#shareStorageAllotCapacity").html((data.shareAllotCapacity == null ? "0":data.shareAllotCapacity) +"GB");
	 				$("#shareStorageAllotProgressBar").html((data.shareAllotRate == null ? "0.00":data.shareAllotRate) +"%");
	 			 }
		  });
	}
	
	 
	 // 删除资源环境
	 function deleteVeInSummary(){
		 Core.confirm({
				title:"提示",
				message:"您确定要删除该资源环境吗？",
				confirmCallback:function(){
					Core.AjaxRequest({  
		 				url : ws_url + "/rest/ves/delete/"+resTopologySid,
		 				type:"get",
		 				callback : function (data) {
		 					setVirtualTreeValue();
		 			    }
		 			});
				}
		 });
	 }
	 
	 
</script>

