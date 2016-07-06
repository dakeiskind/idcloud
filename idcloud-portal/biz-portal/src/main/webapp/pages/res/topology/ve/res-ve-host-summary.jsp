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
  		<div class="customPanel" style="width:42.5%;height:226px;float:left;z-index:999">
  		    <div class="title">&nbsp;&nbsp;基本信息
  		    	<font style="cursor:pointer;position:absolute;right:5px" onclick="popEditHostVeWindow()"><i class="icons-blue icon-edit"></i></font>
  		    </div>   
	        <div>
	        	<table id="datacenterBaseInfo" border="0" width="100%" cellspacing="0" cellpadding="5">
	        		<tr>
	        			<td align="right" width="80px" height="25px">环境名称:</td>
	        			<td align="left">
	        				<span id="resTopologyName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">环境类型:</td>
	        			<td align="left">
	        				<span id="virtualPlatformTypeVc"></span>
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
	        			<td align="right" width="80px" height="25px">更新周期:</td>
	        			<td align="left">
	        				<span id="updateCycle"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="80px" height="25px">消息队列:</td>
	        			<td align="left">
	        				<span id="mqTopic"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">环境描述:</td>
	        			<td align="left">
	        				<span id="description"></span>
	        			</td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
	  		<div class="customPanel" style="width:56%;height:125px;margin-left:1%;float:left">
	  		    <div class="title">&nbsp;&nbsp;操作
<!-- 	  		         <font style="position:absolute;right:5px"><i class="icons-blue icon-help"></i></font> -->
	  		    </div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddHostClusterWindow()"><i class="icon-list-add" ></i>&nbsp;添加集群</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddHostToClusterWindow()"><i class="icon-list-add"></i>&nbsp;添加已有主机</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popRemoveHostOutClusterWindow()"><i class="icon-list-add"></i>&nbsp;移除主机</span></td>
	        		</tr>
	        		<tr>	
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeDcAndCluster('03')"><i class="icons-blue icon-cancel-3"></i>&nbsp;删除资源环境</span></td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	<div style="width:98%;height:370px;margin-left:1%;">
  		<div class="customPanel" style="width:56%;height:350px;margin-left:43.6%;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
        <div>
        	<div id='VmChart' style="width:50%; height:320px;float:left;border:0px"></div>	
        	<div id='hostChart' style="width:49%; margin-left:0.5%;height:320px;float:left;border:0px"></div>
        </div>
  		</div>
  	</div>
  </div>
   
  <%@ include file="../../dcCluster/res-add-host-to-cluster-host.jsp"%>
  <%@ include file="../../dcCluster/res-remove-host-out-cluster-host.jsp"%>
  
<script type="text/javascript">
	
	function initHostVESummary(){
		
		// 初始化主机监控信息值
   	 	var host = new hostConfigModel();
   		var hostData =  host.HostResourcesStatistics();
   		
   		// 初始化虚拟机监控信息值
   		var vm = new vmManagedModel();
   		var vmData = vm.getVmStatisticsInfo();
   		
   		//  编辑集群或者数据中心
		var dc = new resEditDcAndClusterModel();
		dc.setHostVe();
		dc.initPopWindow();
		dc.initValidator();
		
		// 初始化添加已有主机入资源环境
		var findHost = new addHostToClusterModel();
		findHost.initfindHostAddToClusterDatagrid();
		findHost.initPopWindow();
		findHost.searchfindHostAddToClusterData();
		
		// 初始化从资源环境移除主机
		var removeHost = new removeHostOutClusterModel();
		removeHost.initfindHostRemoveOutClusterDatagrid();
		removeHost.initPopWindow();
		removeHost.searchfindHostRemoveOutClusterData();
		
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
                                     formatSettings: { sufix: '(台)', decimalPlaces: 0 }
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
                                     formatSettings: { sufix: '(台)', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         // setup the chart
         $('#hostChart').jqxChart(settings);
	 }
</script>

