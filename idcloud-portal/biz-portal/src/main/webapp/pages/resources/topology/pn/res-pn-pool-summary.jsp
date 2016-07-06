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
  <div id="resPnSummary" style="width:100%;height:100%;">
  	<div style="margin:0px;padding:0px;height:10px;"></div>
  	
  	<div style="width:98%;height:200px;margin-left:1%;">
  		<div class="customPanel" style="width:45%;float:left;height:200px;">
  		    <div class="title">&nbsp;&nbsp;基本信息</div>   
	        <div>
	        	<table id="datacenterBaseInfo" border="0" width="100%" cellspacing="0" cellpadding="5">
	        		<tr>
	        			<td align="right" width="100px" height="25px">名称:</td>
	        			<td align="left">
	        				<span>网络资源池</span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">类型:</td>
	        			<td align="left">
	        				<span>网络资源池</span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">所属分区:</td>
	        			<td align="left">
	        				<span id="parentRZ"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">VLAN池(个):</td>
	        			<td align="left">
	        				<span id="vlanCount"></span>
	        			</td>
	        		</tr>
	        		<!-- <tr>
	        			<td align="right" width="80px" height="25px">内部网络(个):</td>
	        			<td align="left">
	        				<span id="pniCount"></span>
	        			</td>
	        		</tr> -->
	        		<tr>
	        			<td align="right" width="80px" height="25px">外部网络(个):</td>
	        			<td align="left">
	        				<span id="pneCount"></span>
	        			</td>
	        		</tr>
	        		
	        	</table>
	        </div>
  		</div>
  		<div class="customPanel" style="width:53.5%;margin-left:1%;float:left;height:200px;">
  		    <div class="title">&nbsp;&nbsp;操作</div>   
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
                        <td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="exportNetWorkInfo()"><i class="icons-blue icon-export-1" ></i>&nbsp;导出</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddVlanPoolWindow()"><i class="icon-list-add" ></i>&nbsp;新增VLAN池</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="addNetworkInfoWindowUsingPn()"><i class="icon-list-add"></i>&nbsp;新增内部网络</span></td>
<!-- 	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="addPneNetworkInfoWindowUsingPn()"><i class="icon-list-add"></i>&nbsp;新增外部网络</span></td> -->
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	
  	<div style="width:98%;height:auto;margin-left:1%;margin-top:10px;">
  		<div class="customPanel" style="width:100%;height:320px;">
  		    <div class="title">&nbsp;&nbsp;网络资源统计</div>   
	        <div>
	        	<div id='vlanChart' style="width:49.5%; height:290px;float:left;border:0px"></div>
        		<div id='pniChart' style="width:49.5%; margin-left:1%;height:290px;float:left;border:0px"></div>
<!--         		<div id='pneChart' style="width:32%; margin-left:1%;height:290px;float:left;border:0px"></div> -->
	        </div>
  		</div>
  	</div>

  </div>
  
<script type="text/javascript">
	
	function initPoolPNSummary(){
		
		// 获取网络资源池下的vlan资源统计信息
		var vlanPn = new poolPnvDatagridModel();
		var vlanData = vlanPn.statisticsVlanInPn(resTopologySid);

		// 获取内部网络资源池信息
		var network = new poolPniDatagridModel();
 		var pniData = network.statisticsIpInNetworkPool("01",resTopologySid); 

		// 获取外部网络资源池信息
// 		var pneData = network.statisticsIpInNetworkPool("02",resTopologySid);
		// 初始化chart图
		initVlanChart(vlanData);
		initPniChart(pniData); 
// 		initPneChart(pneData);
		
		// 设置基本信息
		setBasicInfo();
	}
	
	 // 设置基本信息
	 function setBasicInfo(){
		 
		 Core.AjaxRequest({
 			url : ws_url + "/rest/topology/"+resTopologySid,
 			type:'get',
 			async:false,
 			callback : function (data) {
 				$("#parentRZ").html(data.parentTopologyName);
 				$("#vlanCount").html(data.vlanCount);
 			//	$("#pniCount").html(data.pneCount);
 				$("#pneCount").html(data.pniCount);
 			}
 		 });
		 
		 
	 }
	 
	 // 初始化vlan图表
	 function initVlanChart(data){
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
             title: "VLAN资源统计",
             description: "(总数:"+data[0]+"个)",
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
         $('#vlanChart').jqxChart(settings);
	 }
	 
	 // 初始化内部网络图表
	  function initPniChart(data){

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
             title: "内网IP资源统计",
             description: "(总数:"+data[0]+"个)",
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
         $('#pniChart').jqxChart(settings);
	 }
	 
	  // 初始化外部网络图表
	 function initPneChart(data){

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
             title: "外网IP资源统计",
             description: "(总数:"+data[0]+"个)",
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
         $('#pneChart').jqxChart(settings);
	 }
	  
	  
	 // 手动刷新网络统计信息
	 function refreshPnInfo(){
			// 设置基本信息
			setBasicInfo();
	 }

     function exportNetWorkInfo(){
    	 
        window.location = ws_url + "/rest/networks/exportNetWorkInfo/"+resTopologySid;
     }
</script>

