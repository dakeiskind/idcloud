<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style type="text/css">
	#datacenterBaseInfo td{
		border-bottom:1px solid #E9E9E9;
	}
	#datacenterBaseInfo tr:hover{
		background:#E6E6E6;
	}  
</style>
  <div id="resRegionSummary" style="width:100%;height:100%;">
  	<div style="margin:0px;padding:0px;height:10px;"></div>
  	<div style="width:98%;height:140px;margin-left:1%;">
  		<div class="customPanel" style="width:49.3%;height:125px;float:left">
  		    <div class="title">&nbsp;&nbsp;基本信息
  		    	<!-- <font style="cursor:pointer;position:absolute;right:5px" onclick="popEditRegionWindow()"><i class="icons-blue icon-edit"></i></font> -->
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
	        				区域
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
	  	<div class="customPanel" style="width:49%;height:125px;margin-left:1%;float:left">
	  		    <div class="title">&nbsp;&nbsp;操作
	  		         <font style="position:absolute;right:5px"><i class="icons-blue icon-help"></i></font>
	  		    </div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<!-- <td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddRegionWindow()"><i class="icon-list-add" ></i>&nbsp;添加区域</span></td> -->
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddDcWindow()"><i class="icons-blue icon-plus-3"></i>&nbsp;添加数据中心</span></td>
	        			<!-- <td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeZoneOrDc('R')"><i class="icons-blue icon-cancel-3"></i>&nbsp;删除区域</span></td> -->
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	<div style="width:98%;height:400px;margin-left:1%;">
  		<div class="customPanel" style="width:99.6%;height:380px;float:left;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
        <div>
        	<div id='hostChart' style="width:20%; height:170px;float:left;border: 0;"></div>
        	<div id='cabinetChart' style="width:20%; height:170px;float:left;border: 0;"></div>
             <div id='rackChart' style="width:20%; height:170px;float:left;border: 0;"></div>
        	<div id='frameChart' style="width:20%; height:170px;float:left;border: 0;"></div>
        	<div id='serverChart' style="width:20%; height:170px;float:left;border: 0;"></div>
        	<!-- <div id='VmChart' style="width:50%; height:320px;float:left;border:0px"></div>	 -->
        </div>
        	<div id='switchChart' style="width:20%; height:170px;float:left;border: 0;"></div>
        	<div id='loadbalancerChart' style="width:20%; height:170px;float:left;border: 0;"></div>
        	<div id='firewallChart' style="width:20%; height:170px;float:left;border: 0;"></div>
        	<div id='storageChart' style="width:20%; height:170px;float:left;border: 0;"></div>
             <div id='storageCharts' style="width:20%; height:170px;float:left;border: 0;"></div>
        	<!-- <div id='switchChart' style="width:25%; height:170px;float:left;"></div> -->
  		</div>
  	</div>
  </div>
   
<script type="text/javascript">
	
	function initRegionSummary(){
       var dc = new resEditZoneDcModel();
        dc.setDcDetailsInfo();
        dc.initPopWindow();
        dc.initValidator();
        
        var labHouse = new labHouseDatagridModels();
        var labHouseData =  labHouse.LabHouseResourcesStatistics();
        //初始化机柜监控信息
        var cabinet = new cabinetDatagridModels();
        var cabinetData = cabinet.CabinetResourcesStatistics(); 
        //初始化机架监控信息
        var rack = new rackDatagridModels();
        var rackData = rack.RackResourcesStatistics(); 
        //初始化机框监控信息
        var frame = new  machineFrameDatagridModels();
        var frameData = frame.FrameResourcesStatistics();
        //初始化服务器监控信息
        var server = new serverDatagridModels();
        var serverData = server.ServerResourcesStatistics();
        //初始化交换机监控信息
        var switchs = new switchDatagridModels();
        var switchData = switchs.SwitchResourcesStatistics();
        //初始化负载均衡监控信息
        var loadbalancer = new loadbalancerDatagridModels();
        var loadbalancerData = loadbalancer.LoadbalancerResourcesStatistics();
        //初始化防火墙监控信息
        var firewall = new firewallDatagridModels();
        var firewallData = firewall.FirewallResourcesStatistics();
        //初始化存储监控信息
        var storage = new sanDatagridModels();
        var storageData = storage.StorageResourcesStatistics();
        
        // 初始化chart图表
        initLabHouseChart(labHouseData);
        initCabinet(cabinetData);
        initCabinetFrames(frameData);
        initRack(rackData);
        initServer(serverData);
        initSwitch(switchData);
        initloadbalancer(loadbalancerData);
        initFirewall(firewallData);
        initStorage(storageData);

  
	}
	 
	 // 初始化主机图表
	   function initLabHouseChart(data){
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
             title: "机房资源统计",
             description: "(总数:"+data.roomCount+"个)",
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
                                     llabelRadius: 50,
                                     initialAngle: 15,
                                     radius: 40,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         $('#hostChart').jqxChart(settings);
     }
	 function initCabinet(data){
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
             title: "机柜资源统计",
             description: "(总数:"+data.cabinetCount+"个)",
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
                                     labelRadius: 50,
                                     initialAngle: 15,
                                     radius: 40,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         $('#cabinetChart').jqxChart(settings);
     }
     function initRack(data){
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
             title: "机架资源统计",
             description: "(总数:"+data.rackCount+"个)",
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
                                     labelRadius: 50,
                                     initialAngle: 15,
                                     radius: 40,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         $('#rackChart').jqxChart(settings);
     }
	 
	 function initCabinetFrames(data){
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
             title: "刀箱资源统计",
             description: "(总数:"+data.frameCount+"个)",
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
                                     labelRadius: 50,
                                     initialAngle: 15,
                                     radius: 40,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         $('#frameChart').jqxChart(settings);
     }
	 
	 function initServer(data){
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
             title: "服务器资源统计",
             description: "(总数:"+data.serverCount+"个)",
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
                                     labelRadius: 50,
                                     initialAngle: 15,
                                     radius: 40,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         $('#serverChart').jqxChart(settings);
     }
	  function initSwitch(data){
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
             title: "交换机资源统计",
             description: "(总数:"+data.switchCount+"个)",
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
                                     labelRadius: 50,
                                     initialAngle: 15,
                                     radius: 40,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         $('#switchChart').jqxChart(settings);
     }
	 function initloadbalancer(data){
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
             title: "负载均衡器资源统计",
             description: "(总数:"+data.loadbalancerCount+"个)",
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
                                     labelRadius: 50,
                                     initialAngle: 15,
                                     radius: 40,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         $('#loadbalancerChart').jqxChart(settings);
	 }
	 function initFirewall(data){
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
             title: "防火墙资源统计",
             description: "(总数:"+data.firewallCount+"个)",
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
                                     labelRadius: 50,
                                     initialAngle: 15,
                                     radius: 40,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         $('#firewallChart').jqxChart(settings);
	 }
	 function initStorage(data){
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
                                     labelRadius: 50,
                                     initialAngle: 15,
                                     radius: 40,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         $('#storageChart').jqxChart(settings);
	 }
</script>

