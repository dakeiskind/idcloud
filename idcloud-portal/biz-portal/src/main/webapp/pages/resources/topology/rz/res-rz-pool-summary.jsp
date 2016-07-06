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
  	<div style="width:98%;height:135px;margin-left:1%;">
  		<div class="customPanel" style="width:35%;height:120px;float:left;z-index:999">
  		    <div class="title">&nbsp;&nbsp;基本信息
  		    	<font style="cursor:pointer;position:absolute;right:5px" onclick="popEditRzWindow()"><i class="icons-blue icon-edit"></i></font>
  		    </div>   
	        <div>
	        	<table id="datacenterBaseInfo" border="0" width="100%" cellspacing="0" cellpadding="5">
	        		<tr>
	        			<td align="right" width="80px" height="25px">名称:</td>
	        			<td align="left">
	        				<span id="resTopologyName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">类型:</td>
	        			<td align="left">
	        				资源分区
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
	  		<div class="customPanel" style="width:64%;height:120px;margin-left:0.5%;float:left">
	  		    <div class="title">&nbsp;&nbsp;操作
<!-- 	  		         <font style="position:absolute;right:5px"><i class="icons-blue icon-help"></i></font> -->
	  		    </div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeRz()"><i class="icons-blue icon-cancel-3"></i>&nbsp;删除资源分区</span></td>
	        		</tr>
	        	</table>
	        </div>
  		</div> 
  	</div>
  	<div style="width:98%;height:370px;margin-left:1%;">
  		<div class="customPanel" style="width:35%;height:300px;float:left;">
  		    <div class="title">&nbsp;&nbsp;资源分配</div>   
	        <div>
	        	<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
	        	   			<td width="30%" align="right" height="25px">CPU总核数：</td>
	        	   			<td width="20%" align="left"><span id="cpuTotalAllot" style="color:#D45753"></span></td>
	        	   			<td width="30%" align="right">CPU分配核数：</td>
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
  		
  		<div class="customPanel" style="width:64%;margin-left:0.5%;height:350px;float:left;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
	        <div>
	        	<div id='hostRzChart' style="width:32%; margin-left:0.5%;height:300px;float:left;border:0px"></div>
	        	<div id='storageRzChart' style="width:32%; height:300px;float:left;border:0px"></div>
	        	<div id='networkRzChart' style="width:32%; height:300px;float:left;border:0px"></div>		
	        </div>
  		</div>
  	</div>
  </div>
  
<script type="text/javascript">
	
	function initPoolRZSummary(){
		
   		// 初始化存储监控信息值
   		getStorageAllotInfo();
   		//初始化主机监控信息
   		var hostData = initResHostAllot();
   		
   		// 初始化主机分配信息
   		initResHostAllotInfo();
   		
   		var sData = new storageConfigMgtModel();
   		var stoData = sData.StorageResourcesStatisticsVolumeInTopologyRz(resTopologySid);
   		
   		// 初始化网络统计
   		var network = new poolRzDatagridModel();
   		var netData = network.statisticsNetworkInTopology(resTopologySid);
   		
   		//  编辑集群或者数据中心
		var rz = new resEditRzModel();
		rz.setRzDetailsInfo();
		rz.initPopWindow();
		rz.initValidator();
		
		// 初始化chart图
		initStorageChart(stoData);
		initHostChart(hostData);
		initNetworkChart(netData);
	}
	
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
            title: "外置存储容量",
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
        $('#storageRzChart').jqxChart(settings);
	 }
	
	 // 初始化网络图表
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
         $('#networkRzChart').jqxChart(settings);
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
             localdata: data[1]
         };
         var dataAdapter = new $.jqx.dataAdapter(source, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); } });
         // prepare jqxChart settings
         var settings = {
             title: "主机资源统计",
             description: "(总数:"+data[0]+"台)",
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
         $('#hostRzChart').jqxChart(settings);
	 }
	 
	   // 获取主机分配信息
	function initResHostAllotInfo(){
		  
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/hosts/statistical/rz/allot/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				if(data !=null){
	 					$("#cpuTotalAllot").html(data.cpuTotalCores+"核");
		 				$("#cpuAllotCapacity").html(data.cpuAllotCores+"核");
		 				$("#cpuAllotProgressBar").html((data.cpuAllotCores/data.cpuTotalCores*100).toFixed(2)+"%");
		 				
		 				$("#memoryTotalAllot").html(data.memorySize+"GB");
		 				$("#memoryAllotCapacity").html(data.memoryAllotSize+"GB");
		 				$("#memoryAllotProgressBar").html((data.memoryAllotSize/data.memorySize*100).toFixed(2)+"%");
	 				}else{
	 					$("#cpuTotalAllot").html("0核");
		 				$("#cpuAllotCapacity").html("0核");
		 				$("#cpuAllotProgressBar").html("0.00%");
		 				
		 				$("#memoryTotalAllot").html("0GB");
		 				$("#memoryAllotCapacity").html("0GB");
		 				$("#memoryAllotProgressBar").html("0.00%");
	 				}
	 				
	 			}
		    });
	 };
	
	// 统计主机状态信息
	function initResHostAllot(){
		 var all = new Array();
		 var arr = new Array();
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/hosts/statistical/rz/status/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				 hostData = data;
	 			}
		    });
			var data = new Object();
			data.hostCount = hostData.staTotalHost;
			all.push(data.hostCount);
			var value = [hostData.staNormalHost,hostData.staOffineHost,hostData.staMaintainHost,hostData.staFaultHost];
			var name =["正常","离线","维护","故障"];
			for(var i=0; i<4;i++){
				var obj = new Object();
				obj.name = name[i];
				obj.value = value[i];
				arr.push(obj);
			}
			 all.push(arr);	
			return all;
	    };
	 
		// 查询出存储的分配信息
		function getStorageAllotInfo(){
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

