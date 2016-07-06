<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style type="text/css">
	#datacenterBaseInfo td{
		border-bottom:1px solid #E9E9E9;
	}
	#datacenterBaseInfo tr:hover{
		background:#E6E6E6;
	}
	.main{
		width:300px;
		height:40px;
		position:absolute;
		left:50%;
		top:50%;
		margin-left:-150px;
		margin-top:-30px;
	}
 	.box{
		position:relative;
		float:left;
	} 
	input.uploadFile{
		position:absolute;
		right:0px;
		top:0px;
		opacity:0;
		filter:alpha(opacity=0);
		cursor:pointer;
		width:276px;
		height:36px;
		overflow: hidden;
	}
	input.textbox{
		float:left;
		padding:5px;
		color:#999;
		height:18px;
		line-height:24px;
		border:1px #ccc solid;
		width:200px;
		margin-right:4px;
	}
	a.link{
		float:left;
		display:inline-block;
		padding:4px 16px;
		color:#5E5252;
		font:14px "Microsoft YaHei", Verdana, Geneva, sans-serif;
		cursor:pointer;
		background-color:#E8E7E7;
		line-height:22px;
		text-decoration:none;
		height:22.5px;
	}  
</style>
  <div id="resDChostSummary" style="width:100%;height:100%;">
  	<div style="margin:0px;padding:0px;height:10px;"></div>
  	<div style="width:98%;height:140px;margin-left:1%;">
  		<div class="customPanel" style="width:49.3%;height:125px;float:left">
  		    <div class="title">&nbsp;&nbsp;基本信息
  		    	<!-- <font style="cursor:pointer;position:absolute;right:5px" onclick="popEditDcWindow()"><i class="icons-blue icon-edit"></i></font> -->
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
	  		         <font style="position:absolute;right:5px"><i class="icons-blue icon-help"></i></font>
	  		    </div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="exportPhyRes()"><i class="icon-export-1" ></i>&nbsp;导出</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="addPhyRes()"><i class="icon-list-add" ></i>&nbsp;添加物理设备分区</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="exportPhyModel()"><i class="icon-download" ></i>&nbsp;模板导出</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="importPhyModel()"><i class="icon-list-add" ></i>&nbsp;设备信息导入</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeZoneOrDcByPhy('DC')"><i class="icons-blue icon-cancel-3"></i>&nbsp;删除物理设备</span></td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	<div style="width:98%;height:400px;margin-left:1%;">
  		<div class="customPanel" style="width:99.6%;height:380px;float:left;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
        <div>
        	<div id='hostChart' style="width:20%;height:170px;float:left;border: 0;"></div>
        	<div id='cabinetChart' style="width:20%;height:170px;float:left;border: 0;"></div>
            <div id='rackChart' style="width:20%; height:170px;float:left;border: 0;"></div>
        	<div id='frameChart' style="width:20%;height:170px;float:left;border: 0;"></div>
        	<div id='serverChart' style="width:20%; height:170px;float:left;border: 0;"></div>
           
        	<!-- <div id='storageChart' style="width:33%; height:320px;float:left;"></div>
        	<div id='VmChart' style="width:33%; height:320px;float:left;"></div>	 -->
        </div>
        	<div id='switchChart' style="width:20%; height:170px;float:left;border: 0;"></div>
        	<div id='loadbalancerChart' style="width:20%; height:170px;float:left;border: 0;"></div>
        	<div id='firewallChart' style="width:20%; height:170px;float:left;border: 0;"></div>
        	<div id='storageChart' style="width:20%; height:170px;float:left;border: 0;"></div>
            <div id='storageCharts' style="width:20%; height:170px;float:left;border: 0;"></div>
  		</div>
  	</div>

  </div>
<!--     	<div id="newPhyfileUploadWindow" style="display: none;">
  		<div>批量导入</div>
  		 <div id="newPhyfileUploadForm" >
		<form id="phyUpload" name="upload" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
			<table>
				<tr>
					<td height="5px" align="right" style="font-size: 14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择附件：</td>
					<td align="left" class="l-border-bottom-blue"colspan="3"> 
	     				<input type="file" id="phyFileAttach" name="phyFileAttach"  onchange="ye.value=value" style='width: 215px;cursor: pointer;' />
	     				<input type="file" id="fileAttach" name="fileAttach" style='width: 215px;cursor: pointer;' />
	     				<input type="button" value="上传" id="submitFile"/>
					</td>
				</tr>
				<tr>
					<td height="1px" align="right" style="font-size: 14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><span id="infoPhy" height="10px" align="right" style="font-size: 14px"></span></td>
				</tr>
				 <tr>
                    <td align="right" colspan="4">
                    <input style="margin-right: 5px;" onclick='importPhysicalFileModel()' type="button" id="importPhyFile" value="导入" />
                    <input id="importPhyFileCancel" type="button" value="取消" /></td>
                 </tr>
			</table>
		</form>
		</div>
   	</div> -->
<!--    	
   	<div id="newPhyfileUploadWindow" style="display: none;">
            <div>批量导入</div>
            <div id="newPhyfileUploadForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
						<td height="5px" align="right" style="font-size: 14px">&nbsp;选择附件：</td>
						<td>
							<form id="uploadform" method="post" action="upload.jsp" target="upload_frame" enctype="multipart/form-data">
								<input type="file" id="fileAttach" name="fileAttach" />
								<input type="button" value="上传" id="submitFile" />
							</form>
						</td>
					</tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick='importPhysicalModel()' type="button" id="importPhyFile" value="导入" />
                        <input id="importPhyFileCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div> -->
       <div id="newPhyfileUploadWindow" style="display: none;">
  		<div>批量导入</div>
  		 <div id="newPhyfileUploadForm">
		<form id="phyUpload" name="upload" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
			<table class="main">
			<!-- 	 <tr>
					<td height="5px" align="right" style="font-size: 14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择附件：</td>
					<td align="left" class="l-border-bottom-blue"colspan="3"> 
	     				<input type="file" id="fileAttach" name="fileAttach" style='width: 215px;cursor: pointer;' />
					</td>
				</tr>  -->
				<tr>
					<td>
						<div  class="box">
			            <input type="file" class="uploadFile" id="phyFileAttach" name="fileAttach" onchange="getFile(this,'copyFile')" />
						<input type="text" name="copyFile"  class="textbox" />
						<a href="javascript:void(0);"  class="link">浏览</a>
						</div>
		            </td>
				</tr> 
 				<tr>
					<td height="1px" align="right" style="font-size: 14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><span id="info" height="10px" align="right" style="font-size: 14px"></span></td>
				</tr> 
				 <tr>
                    <td align="right" colspan="4">
                    <input style="margin-right: 5px;" onclick='importPhysicalModel()' type="button" id="importPhyFile" value="导入" />
                    <input id="importPhyFileCancel" type="button" value="取消" /></td>
                 </tr>
			</table>
		</form>
		</div>
   </div>

   
<script type="text/javascript">
	
	function initHostDCSummary(){
		// 初始化主机监控信息值
   	 	/* var host = new virtualHostDatagridModel();
   		var hostData =  host.HostResourcesStatistics();
   		
   		// 初始化虚拟机监控信息值
   		var vm = new virtualVMDatagridModel();
   		var vmData = vm.getVmStatisticsInfo();
   		
   		// 初始化存储监控信息
   		var storage = new storageConfigMgtModel();
   		var storageData = storage.StorageResourcesStatisticsInTopology(resTopologySid);
   	 */
   		//  编辑集群或者数据中心
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
	 //模板导出
	 function exportPhyModel(){
		 window.location = ws_url + "/rest/phyexcels/exportPhysicalModel"
	 }
	 
	 //增加物理资源分区
 	 function addPhyRes(){
		 Core.confirm({
			  title:"提示",
			  message:'您确认要增加物理资源吗？',
			  confirmCallback : function() {
				  Core.AjaxRequest({
			 			url : ws_url + "/rest/topology/addphysical/tree",
			 			type:'post',
			 			params:{
			 				resTopologySid:resTopologySid
			 			},
			 			callback : function (data) {
			 				initPhysicalTopologyTree(data);
			 				
			 			}
			 	  });
			  }
		 });
	 }
 	function removeZoneOrDcByPhy(type){
 		var message="";
 		if("DC" == type){
 			message = "确定要删除该数据中心的物理设备吗？";
 		}
 		Core.confirm({
 			title:"提示",
 			message: message,
 			confirmCallback:function(){
 				Core.AjaxRequest({
 	 				url : ws_url + "/rest/topology/deleteDCByPhy/"+resTopologySid+"",
 	 				type:"get",
 	 				callback : function (data) {
 	 					//console.log(JSON.stringify(data));
 	 					// 刷新左边的Tree
 	 					//if($("#containerPool").length > 0){
 	 					//	setPoolTreeValue();
 	 					//}else{
 	 					//	setVirtualTreeValue();
 	 					//	setStorageVirtualTreeValue();
 	 					//}
 	 					
 	 					setPhysicalTreeValue();
 	 			    },
 	 			    failure:function(data){
 	 			    	
 	 			    }
 	 			});
 			}
 	   });
 	}
 	
 	 function getFile(obj,inputName){
 		var file_name = $(obj).val();
 		file_name = file_name.substring(file_name.lastIndexOf("\\")+1,file_name.length);
 		$("input[name='"+inputName+"']").val(file_name);
 	 }
	 //设备信息导入
	 function importPhyModel(){
		 document.getElementById("newPhyfileUploadWindow").style.display="";
		 var windowW = $(window).width(); 
  	 	 var windowH = $(window).height(); 
		 $("#newPhyfileUploadWindow").jqxWindow({
			 width: 350, 
			 height:130,
			 resizable: false,  
	         isModal: true, 
	         autoOpen: false, 
	         cancelButton: $("#importPhyFileCancel"), 
	         position: { x: (windowW-350)/2, y: (windowH-158)/2  } 
		 }); 
		 $("#importPhyFile").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	  	 $("#importPhyFileCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	  // $("#fileAttach").jqxButton({ width: '300px',height:'25px',theme:currentTheme});
		 $("#newPhyfileUploadWindow").jqxWindow('open');
	 }
/* 	 
	 function importPhysicalModel(){
		 var filename = $("#fileAttach").val();
		 filename = filename.substring(filename.lastIndexOf("\\")+1,filename.length);
		 if(filename !=""&& filename!=null){
			 Core.AjaxFormSubmit({
			 formId : "upload",
			 url : ws_url+"/rest/ves/importFile",
			 async : false,
			 callback : function(data) {
				 if(null!=data){
					 Core.AjaxRequest({
						 url : ws_url + "/rest/ves/importFileDate",
						 type:"post",
						 params:{
							 	data:data,
								resTopologySid:resTopologySid
						 },
						 async:false,
						 callback : function (data) {
							 if(data.status == "failure"){
								 $('#newfileUploadWindow').jqxWindow('close');
							 }else{
								 $('#newfileUploadWindow').jqxWindow('close');
		 							setVirtualTreeValue();
			 						setStorageVirtualTreeValue();
							 }
						 }
					 });
				 }
			 }
			 });
		 }
	  }	 */  
	  function importPhysicalModel(){
		 var filename = $("#phyFileAttach").val();
		 filename = filename.substring(filename.lastIndexOf("\\")+1,filename.length);
		 if(filename !=""&& filename!=null){
			 Core.AjaxFormSubmit({
				 formId : "phyUpload",
				 url : ws_url+"/rest/phyexcels/importPhysicalFile",
				 async : false,
				 callback : function(data) {
					 Core.AjaxRequest({
						 url : ws_url + "/rest/phyexcels/importPhysicalFileDate",
						 type:"post",
						 params:{
							 	data:data,
								resTopologySid:resTopologySid
						 },
						 callback : function(data) {
							 if("success"==data.result){
								  Core.confirm({
									  title:"提示",
									  message:'导入成功！',
								  });
							 }else{
								 Core.confirm({
									  title:"提示",
									  message:'导入失败！',
								  });
							 }
							 $("#newPhyfileUploadWindow").jqxWindow('close');
							 window.location.reload();
						 }
					 });
				 }
			 });
		 }
	 }
	  
function exportPhyRes(){
	
  window.location = ws_url + "/rest/phyexcels/exportPhyRes/"+resTopologySid; 
}
	 
</script>

