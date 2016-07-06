<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style type="text/css">
	#clusterBaseInfo td{
		border-bottom:1px solid #E9E9E9;
	}
	#clusterBaseInfo tr:hover{
		background:#E6E6E6;
	}  
</style>
  <div style="width:100%;height:100%;overflow-y:auto">
  	<div style="margin:0px;padding:0px;height:10px;"></div>
  	<div style="width:98%;height:185px;margin-left:1%;">
  		<div class="customPanel" style="width:49.5%;height:175px;float:left">
  		    <div class="title">&nbsp;&nbsp;基本信息
  		    	<font style="position:absolute;right:5px;cursor:pointer" onclick="popEditStorageInfoWindow()"><i class="icons-blue icon-edit"></i></font>
  		    </div>   
	        <div>
	        	<table id="clusterBaseInfo" border="0" width="100%" cellspacing="0" cellpadding="5">
	        		<tr>
	        			<td align="right" width="100px" height="25px">存储名称:</td>
	        			<td align="left">
	        				<span id="volumeName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" width="100px" height="25px">存储类型:</td>
	        			<td align="left">
	        				<span id="volumeType"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">使用状态:</td>
	        			<td align="left">
	        				<span id="usageStatusName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">管理状态:</td>
	        			<td align="left">
	        				<span id="manageStatusName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">总容量(GB):</td>
	        			<td align="left">
	        				<span id="availableCapacity"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">可用容量(GB):</td>
	        			<td align="left">
	        				<span id="canUsed"></span>
	        			</td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
	  		<div class="customPanel" style="width:49%;height:125px;margin-left:1%;float:left">
	  		    <div class="title">&nbsp;&nbsp;操作
<!-- 	  		         <font style="position:absolute;right:5px;"><i class="icons-blue icon-help"></i></font> -->
	  		    </div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeStorage()"><i class="icon-cancel-3"></i>删除存储</span></td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	
  	<div style="width:98%;height:330px;margin-left:1%;position:relative">
	  		<div class="customPanel" style="width:49.5%;height:310px;float:left;">
	  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
		        <div>
		        	<div id='StorgaeChart' style="height:280px;border:0px"></div>	
		        </div>
	  		</div>
  	</div>
  </div>
 
  
  <%@ include file="../../storage/res-edit-storage.jsp"%>
   
<script type="text/javascript">

	function initStorageSummary(){
		 // 基本信息赋值
		 var dataChart = setStorageSummaryBasicInfo();
		 // 初始化资源统计画面
		 initStorageChart(dataChart);
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
                                     radius: 90,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '(GB)', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         // setup the chart
         $('#StorgaeChart').jqxChart(settings);
	 }
	 
	//给概要基本信息赋值
	 function setStorageSummaryBasicInfo(){
	 	var storage = new editStorageConfigMgtModel();
	 	if(resTopologySid.substring(0,1) =="p"){
	 		resTopologySid = resTopologySid.substring(1);
	 	}
	 	var data = storage.getStorageByResSid(resTopologySid);
		// 基本信息赋值
	 	$("#volumeName").html(data.volumeName);
	 	$("#volumeType").html(data.volumeType);
	 	$("#usageStatusName").html(data.usageStatusName);
	 	$("#manageStatusName").html(data.manageStatusName);
	 	$("#availableCapacity").html(data.availableCapacity);
	 	$("#canUsed").html(data.availableCapacity - data.allocateAvailableCapacity);
	 	
	 	var datachart = new Object();
	 	datachart.total = data.availableCapacity;
	 	datachart.attr = new Array();
	 	
		var value = [data.allocateAvailableCapacity,data.availableCapacity - data.allocateAvailableCapacity];
		var name =["已使用","未使用"];
	 	
		for(var i=0; i<2;i++){
			var obj = new Object();
			obj.name = name[i];
			obj.value = value[i];
			datachart.attr.push(obj);
		}
		return datachart;
	 }
	
	//删除存储，供非datagrid调用的删除方法
	 function removeStorage(){
	 	if(resTopologySid.substring(0,1) =="p"){
	  		resTopologySid = resTopologySid.substring(1);
	  	}
	 	Core.confirm({
	 		title:"提示",
	 		message:"确定要删除该块存储吗?",
	 		yes:"确定",
	 		confirmCallback:function(){
	 			Core.AjaxRequest({
	  				url : ws_url + "/rest/storages/delete/"+resTopologySid+"",
	  				type:"get",
	  				callback : function (data) {
	  					setHostTreeValue();
	  			    },
	  			    failure:function(data){
	 		 			    	
	  			    }
	  			});
	 		}
	     });
	 }
</script>

