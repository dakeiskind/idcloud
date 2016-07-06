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
  		<div class="customPanel" style="position:absolute;top:0px;left:0px;width:47%;height:435px;">
  		    <div class="title">&nbsp;&nbsp;基本信息
  		    	<font style="position:absolute;right:5px;cursor:pointer" onclick="popEditRespoolExchangeWindow()"><i class="icons-blue icon-edit"></i></font>
  		    </div>   
	        <div>
	        	<table id="clusterBaseInfo" border="0" width="100%" cellspacing="2" cellpadding="0">
	        		<tr>
	        			<td align="right" width="100px" height="25px">名称：</td>
	        			<td align="left">
	        				<span id="resPoolName-exchange"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">类型：</td>
	        			<td align="left">
	        				<span id="pooltype-exchange"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">分配策略：</td>
	        			<td align="left">
	        				<span id="allocationPolicy-exchange"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">换算公式：</td>
	        			<td align="left">
	        				<span id="conversionFormula-exchange"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">可分配阀值：</td>
	        			<td align="left">
	        				<span id="allocationThreshold-exchange"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">性能保障等级：</td>
	        			<td align="left">
	        				<span id="perfLevel-exchange"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">资源池状态：</td>
	        			<td align="left">
	        				<span id="statusName-exchange"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">操作处理器：</td>
	        			<td align="left">
	        				<span id="operationHandler-exchange"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">资源池描述：</td>
	        			<td align="left">
	        				<span id="description-exchange"></span>
	        			</td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
	  		<div class="customPanel" style="position:absolute;left:48%;top:0px;width:51.8%;height:90px;float:left">
	  		    <div class="title">&nbsp;&nbsp;操作  <font style="position:absolute;right:5px;"><i class="icons-blue icon-help"></i></font>
	  		    </div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeExchangeRespool()"><i class="icon-cancel-3"></i>&nbsp;删除资源池</span></td>
<!-- 	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="popAddVlanToPoolWindow()"><i class="icon-desktop"></i>&nbsp;添加VLAN入池</span></td> -->
	        			<td align="left" height="25px"></td>
	        			<td align="left" height="25px"></td>
	        		</tr>
	    
	        	</table>
	        </div>
  		</div>
  	</div>
  	
  	
  	<div style="width:98%;height:350px;margin-left:1%;position:relative;">
  	    
  		<div class="customPanel" style="position:absolute;left:48%;top:-35px;width:51.8%;height:330px;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
        <div>
        	<div id='vlanChart' style="width:80%; height:300px;border:0px;"></div>	
        </div>
  		</div>
  		
  	</div>
  </div>
  
  <%@ include file="../../pool/res-edit-pool-exchange.jsp"%>
  
<script type="text/javascript">

     function initRespoolSummary(){
 		// 初始化统计信息值
   	 	var ex = new ExchangeConfigModel();
   		var exdata =  ex.exResourcesStatistics();
   		 
    	 // 初始化资源统计值
    	 var respool = new editResPoolModel();
    	 // 初始化Exchange基本信息值
    	 respool.setExchangeResPool();
    	 // 初始化Exchange的window
    	 respool.initExchangePopWindow();
    	 // 初始化Exchange验证
    	 respool.initExchangeValidator();
		 
    	 initExchangeChart(exdata);
     } 
	
	// 初始化vlan图表
	 function initExchangeChart(data){
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
             title: "Exchange资源统计",
             description: "(总数:"+data.exCount+"个)",
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
	 
</script>

