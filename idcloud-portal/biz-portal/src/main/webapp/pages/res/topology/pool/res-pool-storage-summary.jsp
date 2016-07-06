<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style type="text/css">
	#clusterBaseInfo td{
		border-bottom:1px solid #E9E9E9;
	}
	#clusterBaseInfo tr:hover{
		background:#E6E6E6;
	}  
</style>
  <div style="width:100%;height:100%;">
  	<div style="margin:0px;padding:0px;height:10px;"></div>
  	<div style="position:relative;width:98%;height:140px;margin-left:1%;">
  		<!-- sunyu modified 'z-index:99' attr for #119 -->
  		<div class="customPanel" style="position:absolute;top:0px;left:0px;width:47%;height:300px;z-index:99">
  		    <div class="title">&nbsp;&nbsp;基本信息
  		    	<font style="position:absolute;right:5px;cursor:pointer" onclick="popEditRespoolWindow()"><i class="icons-blue icon-pencil"></i></font>
  		    </div>   
	        <div>
	        	<table id="clusterBaseInfo" border="0" width="100%" cellspacing="2" cellpadding="0">
	        		<tr>
	        			<td align="right" width="100px" height="25px">名称：</td>
	        			<td align="left">
	        				<span id="resPoolName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">类型：</td>
	        			<td align="left">
	        				<span id="pooltype"></span>
	        			</td>
	        		</tr>

	        		<tr>
	        			<td align="right" height="25px">存储类型：</td>
	        			<td align="left">
	        				<span id="storageTypeName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">分配策略：</td>
	        			<td align="left">
	        				<span id="allocationPolicy"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">换算公式：</td>
	        			<td align="left">
	        				<span id="conversionFormula"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">可分配阀值：</td>
	        			<td align="left">
	        				<span id="allocationThreshold"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">性能保障等级：</td>
	        			<td align="left">
	        				<span id="perfLevel"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">资源池状态：</td>
	        			<td align="left">
	        				<span id="statusName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">操作处理器：</td>
	        			<td align="left">
	        				<span id="operationHandler"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">资源池描述：</td>
	        			<td align="left">
	        				<span id="description"></span>
	        			</td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
	  		<div class="customPanel" style="position:absolute;left:48%;top:0px;width:51.8%;height:90px;float:left">
	  		    <div class="title">&nbsp;&nbsp;操作
<!-- 	  		         <font style="position:absolute;right:5px;"><i class="icons-blue icon-help"></i></font> -->
	  		    </div>
	        <div>
	        	<table border="0" width="70%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddStorageToPoolWindow()"><i class="icon-desktop"></i>&nbsp;添加已有存储</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popremoveStorageOutPoolWindow()"><i class="icon-trash-2"></i>&nbsp;移除存储</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeStorageRespool()"><i class="icon-trash-2"></i>&nbsp;删除资源池</span></td>
	        			
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	
  	
  	<div style="width:98%;height:350px;margin-left:1%;position:relative;">
  	    
  		<div class="customPanel" style="position:absolute;left:48%;top:-35px;width:51.8%;height:330px;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
        <div>
        	<div id='storageChart' style="width:100%; height:300px;float:left;border:0px;"></div>	
        </div>
  		</div>
  		
  		<div class="customPanel" style="position:absolute;left:0px;top:175px;width:47%;height:185px;">
  		    <div class="title">&nbsp;&nbsp;监控信息</div>   
            <div>
        	   <table border="0" width="100%" cellspacing="0" cellpadding="0">
	        	   		<tr>
	        	   			<td height="35" align="right">总容量：</td>
	        	   			<td><span id="monitor-total-volume"></span>(GB)</td>
	        	   			<td align="right">使用容量：</td>
	        	   			<td><span id="monitor-used-volume"></span>(GB)</td>
	        	   			<td align="right">可用容量：</td>
	        	   			<td><span id="monitor-unused-volume"></span>(GB)</td>
	        	   		</tr>
	        	   		<tr>
	        	   		    <td align="right">存储使用率：</td>
	        	   			<td height="30px" colspan="5"><div style='margin-left:8px;overflow: hidden;' id='storageProgressBar'></div></td>
	        	   		</tr>
	        	   </table>	
               </div>
  		</div>
  	</div>
  </div>
  
  <%@ include file="../../pool/res-edit-pool-host.jsp"%>
  <%@ include file="../../pool/res-add-storage-to-pool-storage.jsp"%>
  <%@ include file="../../pool/res-remove-storage-out-pool-storage.jsp"%>
            	
<script type="text/javascript">

     function initRespoolSummary(){
    	// 初始化存储使用统计
 		var staStorage  = new storageConfigMgtModel();
 		var storagedata = staStorage.getStorageStatisticsData();
 		
 		// 设置监控信息
 		$("#monitor-total-volume").html(storagedata.total);
 		$("#monitor-used-volume").html(storagedata.attr[0].value);
 		$("#monitor-unused-volume").html(storagedata.attr[1].value);
 		$("#monitor-used-rate").html(Math.round((storagedata.attr[0].value/storagedata.total)*100));
 		$("#storageProgressBar").val(((storagedata.attr[0].value/storagedata.total)*100).toFixed(2));
    	 
    	 // 初始化资源统计值
    	 var respool = new editResPoolModel();
    	 // 初始化基本信息值
    	 respool.setHostResPool();
    	 // 初始化window
    	 respool.initPopWindow();
    	 // 初始化验证
    	 respool.initValidator();
    	 
    	 var data = respool.searchResPool(resTopologySid);
    	 // 初始化存储添加入池model
    	 var storageToPool = new addStorageToResPoolModel();
    	 storageToPool.initFindStorageAddToPoolDatagrid();
    	 storageToPool.initPopWindow();
    	 storageToPool.searchFindStorageAddToPoolData(data.storageType);
    	 
    	 // 移除资源池下的存储
    	 var removeStorage = new removeStorageOutResPoolModel();
    	 removeStorage.initFindStorageAddToPoolDatagrid();
    	 removeStorage.initPopWindow();
    	 removeStorage.searchFindRemoveStoragePoolData();
    	 
         // 初始化图形
         initStorageChart(storagedata);
     } 
     
	 // 初始化进度条
	 $("#storageProgressBar").jqxProgressBar({ width: 250, height: 18, value: 0,showText:true,theme:currentTheme});
	
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
         $('#storageChart').jqxChart(settings);
	 }

</script>

