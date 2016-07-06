<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style type="text/css">
	#datacenterBaseInfo td{
		border-bottom:1px solid #E9E9E9;
	}
	#datacenterBaseInfo tr:hover{
		background:#E6E6E6;
	}  
</style>
  <div id="resDChostSummary" style="width:100%;height:100%;">
  	<div style="margin:0px;padding:0px;height:10px;"></div>
  	<div style="width:98%;height:140px;margin-left:1%;">
  		<div class="customPanel" style="width:49.5%;height:125px;float:left">
  		    <div class="title">&nbsp;&nbsp;基本信息
  		    	<font style="cursor:pointer;position:absolute;right:5px" onclick="popEditHostDcWindow()"><i class="icons-blue icon-edit"></i></font>
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
	        				数据中心
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
<!-- 	  		         <font style="position:absolute;right:5px"><i class="icons-blue icon-help"></i></font> -->
	  		    </div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddHostVeWindow()"><i class="icons-blue icon-plus-3"></i>&nbsp;添加资源环境</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddStorageRespoolIpWindow()"><i class="icon-plus-circled"></i>&nbsp;添加资源池</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="addStorageWindow()"><i class="icon-desktop"></i>&nbsp;添加新存储</span></td>
	        			
	        		</tr>
	        		<tr>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeDcAndCluster('02')"><i class="icons-blue icon-cancel-3"></i>&nbsp;删除数据中心</span></td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	<div style="width:48.8%;height:370px;margin-left:1%;">
  		<div class="customPanel" style="width:99.6%;height:350px;float:left;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
        <div>
        	<div id='storageChart' style="width:100%; height:320px;float:left;border:0px"></div>	
        </div>
  		</div>
  	</div>
  </div>
   
<script type="text/javascript">
	
	function initHostDCSummary(){
		// 初始化存储使用统计
		var staStorage  = new storageConfigMgtModel();
		var storagedata = staStorage.getStorageStatisticsData();
   		
   		//  编辑集群或者数据中心
		var dc = new resEditDcAndClusterModel();
		dc.setHostDcAndCluster();
		dc.initPopWindow();
		dc.initValidator();
		
		initStorageChart(storagedata);
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
             title: "存储资源统计",
             description: "(总容量:"+data.total+"GB)",
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
                                     formatSettings: { sufix: '(GB)', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         // setup the chart
         $('#storageChart').jqxChart(settings);
	 }

</script>

