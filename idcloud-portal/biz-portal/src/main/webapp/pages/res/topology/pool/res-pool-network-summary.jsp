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
  		    <div class="title">&nbsp;&nbsp;基本信息    <font style="position:absolute;right:5px;cursor:pointer" onclick="popEditRespoolWindow()"><i class="icons-blue icon-edit"></i></font>
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
	  		         <!-- <font style="position:absolute;right:5px;"><i class="icons-blue icon-help"></i></font> -->
	  		    </div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddHostToPoolWindow()"><i class="icon-desktop"></i>&nbsp;添加VLAN</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddHostToPoolWindow()"><i class="icon-desktop"></i>&nbsp;添加IP</span></td>
	        		</tr>
	        		<tr>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeRespool()"><i class="icon-trash-2"></i>&nbsp;删除资源池</span></td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	
  	
  	<div style="width:98%;height:350px;margin-left:1%;position:relative;">
  	    
  		<div class="customPanel" style="position:absolute;left:48%;top:-35px;width:51.8%;height:330px;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
        <div>
        	<div id='vlanChart' style="width:50%; height:300px;float:left;border:0px;"></div>	
        	<div id='ipChart' style="width:49.5%; margin-left:0.5%;height:300px;float:left;border:0px;"></div>  
        </div>
  		</div>
  		
  		<div class="customPanel" style="position:absolute;left:0px;top:175px;width:47%;height:185px;">
  		    <div class="title">&nbsp;&nbsp;监控信息</div>   
            <div>
        	   <!-- <table border="0" width="100%" cellspacing="0" cellpadding="0">
	        	       
	        	   		<tr>
	        	   			<td width="55%" valign="bottom" height="25px">&nbsp;&nbsp;CPU预留率：10%</td>
	        	   			<td width="45%" valign="bottom">预留容量：0.4GHz</td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td height="30px"><div style='margin-left:8px;overflow: hidden;' id='cpuProgressBar'></div></td>
	        	   			<td>可用容量：3.6GHz</td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td height="25px" valign="bottom">&nbsp;&nbsp;内存预留率：45%</td>
	        	   			<td valign="bottom">预留容量：1.8GB</td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td height="30px"><div style='margin-left:8px;overflow: hidden;' id='memoryProgressBar'></div></td>
	        	   			<td>可用容量：4GB</td>
	        	   		</tr>
	        	   </table>	 -->
               </div>
  		</div>
  	</div>
  </div>
  <%@ include file="../../pool/res-edit-pool-vlan.jsp"%>
  <%@ include file="../../pool/res-edit-pool-ip.jsp"%>
  <%@ include file="../../pool/res-add-host-to-pool-host.jsp"%>    	
<script type="text/javascript">

     function initRespoolSummary(){
 		// 初始化统计信息值
   	 	var vlan = new vlanConfigMgtModel();
   		var vlanData =  vlan.VlanResourcesStatistics();
    		
 		// 初始化统计信息值
   	 	 var ip = new ipConfigMgtModel();
   		 var ipData =  ip.IpResourcesStatistics();
   		 
    	 // 初始化资源统计值
    	 var respool = new editResPoolModel();
    	 // 初始化vlan基本信息值
    	 respool.setVlanResPool();
    	 // 初始化vlanwindow
    	 respool.initPopVlanWindow();
    	 // 初始化vlan验证
    	 respool.initVlanValidator();
    	 
    	 // 初始化ip基本信息值
    	 respool.setIpResPool();
    	 // 初始化ip window
    	 respool.initPopIpWindow();
    	 // 初始化ip验证
    	 respool.initIpValidator();
    	 
    	 var data = respool.searchResPool(resTopologySid);
    	 
    	 // 初始化主机添加池model
    	 //var hostToPool = new addHostToResPoolModel();
    	 //hostToPool.initfindHostAddToPoolDatagrid();
    	 //hostToPool.initPopWindow();
    	 //hostToPool.searchfindHostAddToPoolData(data.virtualPlatformType);
    	 
    	 initVlanChart(vlanData);
 		 initIpChart(ipData);
     } 
     
	 // 初始化进度条
	 //$("#cpuProgressBar").jqxProgressBar({ width: 250, height: 18, value: 10,showText:true,theme:currentTheme});
	 //$("#memoryProgressBar").jqxProgressBar({ width: 250, height: 18, value: 45.23,showText:true,theme:currentTheme});
	
	// 初始化vlan图表
	 function initVlanChart(data){
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
             title: "VLAN资源统计",
             description: "(总数:"+data.vlanCount+"个)",
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
                                     formatSettings: { sufix: '(个)', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         // setup the chart
         $('#vlanChart').jqxChart(settings);
	 }
	 
	 // 初始化ip图表
	 function initIpChart(data){

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
             title: "IP资源统计",
             description: "(总数:"+data.ipCount+"个)",
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
                                     formatSettings: { sufix: '(个)', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         // setup the chart
         $('#ipChart').jqxChart(settings);
	 }
	 
</script>

