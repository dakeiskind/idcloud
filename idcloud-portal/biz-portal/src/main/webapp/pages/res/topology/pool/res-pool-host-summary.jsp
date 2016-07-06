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
  		<div class="customPanel" style="position:absolute;top:0px;left:0px;width:47%;height:300px;">
  		    <div class="title">&nbsp;&nbsp;基本信息
  		    	<font style="position:absolute;right:5px;cursor:pointer" onclick="popEditRespoolWindow()"><i class="icons-blue icon-edit"></i></font>
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

	        		<tr id="isPysical">
	        			<td align="right" height="25px">虚拟类型：</td>
	        			<td align="left">
	        				<span id="virtualPlatformType"></span>
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
	        	<table border="0" width="99%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddHostToPoolWindow()"><i class="icon-mobile-5"></i>&nbsp;添加主机</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popRemoveHostOutPoolWindow()"><i class="icon-cancel-3"></i>&nbsp;移除主机</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeRespool()"><i class="icon-cancel-3"></i>&nbsp;删除资源池</span></td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	
  	<div style="width:98%;height:350px;margin-left:1%;position:relative;">
  	    
  		<div class="customPanel" style="position:absolute;left:48%;top:-35px;width:51.8%;height:330px;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
        <div>
        	<div id='VmChart' style="width:50%; height:300px;float:left;border:0px;"></div>	
        	<div id='hostChart' style="width:50%;height:300px;float:left;border:0px;"></div>  
        </div>
  		</div>
  		
  		<div class="customPanel" style="position:absolute;left:0px;top:175px;width:47%;height:185px;">
  		    <div class="title">&nbsp;&nbsp;资源池监控信息</div>   
            <div>
        	   <table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
        	   			<td align="right" colspan="6">&nbsp;</td>
        	   		</tr>
        	   		<tr>
        	   			<td width="23%" align="right" height="25px">物理CPU总核数：</td>
        	   			<td width="10%" align="left">8</td>
        	   			<td width="24%" align="right">已分配vCPU总核数：</td>
        	   			<td width="10%" align="left">4</td>
        	   			<td width="23%" align="right">CPU虚拟化比例：</td>
        	   			<td width="10%" align="left" >1:3</td>
        	   		</tr>
        	   		<tr>
        	   			<td align="right" height="30px">CPU使用率：</td>
        	   			<td height="30px" colspan="5"><div style='overflow: hidden;' id='cpuProgressBar'></div></td>
        	   		</tr>
        	   		<tr>
        	   			<td align="right" colspan="6">&nbsp;</td>
        	   		</tr>
        	   		<tr>
        	   			<td align="right" height="25px">物理内存(MB)：</td>
        	   			<td align="left">65536</td>
        	   			<td align="right">已分配虚拟内存(MB)：</td>
        	   			<td align="left">32768</td>
        	   			<td align="right">内存虚拟化比例：</td>
        	   			<td align="left" >1:2</td>
        	   		</tr>
        	   		<tr>
        	   			<td align="right" height="30px" >内存使用率：</td>
        	   			<td colspan="5"><div style='overflow: hidden;' id='memoryProgressBar'></div></td>
        	   			
        	   		</tr>
				</table>	
			</div>
  		</div>
  	</div>
  </div>
  
  <%@ include file="../../pool/res-edit-pool-host.jsp"%>
  <%@ include file="../../pool/res-add-host-to-pool-host.jsp"%>
  <%@ include file="../../pool/res-remove-host-out-pool-host.jsp"%>
            	
<script type="text/javascript">

     function initRespoolSummary(){
    	 // 初始化主机监控信息值
    	 var host = new hostConfigModel();
    	 var hostData =  host.HostResourcesStatistics();
    	 
    	 // 初始化虚拟机监控信息值
    	 var vm = new vmManagedModel();
   		 var vmData = vm.getVmStatisticsInfo();
    	 
    	 // 初始化资源统计值
    	 var respool = new editResPoolModel();
    	 // 初始化基本信息值
    	 respool.setHostResPool();
    	 // 初始化window
    	 respool.initPopWindow();
    	 // 初始化验证
    	 respool.initValidator();
    	 
    	 var hostpool = new removeHostOutResPoolModel();
    	 hostpool.initFindHostDatagrid();
    	 hostpool.initPopWindow();
    	 hostpool.searchFindRemoveStoragePoolData();
    	 
    	 var data = respool.searchResPool(resTopologySid);
    	 // 初始化主机添加池model
    	 var hostToPool = new addHostToResPoolModel();
    	 hostToPool.initfindHostAddToPoolDatagrid();
    	 hostToPool.initPopWindow();
    	 hostToPool.searchfindHostAddToPoolData(data.virtualPlatformType);
    	 
         
         initVMChart(vmData);
     	 initHostChart(hostData);
     } 
     
	 // 初始化进度条
	 $("#cpuProgressBar").jqxProgressBar({ width: '95%', height: 18, value: 16.6,showText:true,theme:currentTheme});
	 $("#memoryProgressBar").jqxProgressBar({ width: '95%', height: 18, value: 25,showText:true,theme:currentTheme});
	
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
                                     labelRadius: 100,
                                     initialAngle: 15,
                                     radius: 90,
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
             legendPosition: { left: 200, top: 140, width: 60, height: 50 },
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
                                     labelRadius: 100,
                                     initialAngle: 15,
                                     radius: 90,
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
	 
</script>

