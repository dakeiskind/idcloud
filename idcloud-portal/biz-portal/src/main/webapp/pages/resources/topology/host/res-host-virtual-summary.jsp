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
  	<div style="width:98%;height:225px;margin-left:1%;">
  		<div class="customPanel" style="width:49.3%;height:215px;float:left">
  		    <div class="title">&nbsp;&nbsp;基本信息</div>   
	        <div>
	        	<table id="clusterBaseInfo" border="0" width="100%" cellspacing="0" cellpadding="5">
	        		<tr>
	        			<td align="right" width="100px" height="25px">名称:</td>
	        			<td align="left">
	        				<span id="hostName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">CPU(核):</td>
	        			<td align="left">
	        				<span id="totalCpu"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">内存(GB):</td>
	        			<td align="left">
	        				<span id="memory"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">主机操作系统:</td>
	        			<td align="left">
	        				<span id="osType"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">主机型号:</td>
	        			<td align="left">
	        				<span id="hostType"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">主机IP:</td>
	        			<td align="left">
	        				<span id="ipAddr"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">状态:</td>
	        			<td align="left">
	        				<span id="status"></span>
	        			</td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
	  		<div class="customPanel" style="width:49%;height:100px;margin-left:1%;float:left">
	  		    <div class="title">&nbsp;&nbsp;操作</div>
	        <div>
	        	<table border="0" width="97%" style="margin-left:3%" cellspacing="5" cellpadding="0">
	        		<tr>
	        			<td align="left" width="33%" height="20px"><span style="cursor:pointer;color:#3B9AFF" onclick="asyncHostInfo()"><i class="icon-arrows-ccw"></i>&nbsp;同步</span></td>
	        			<td align="left" width="33%" height="20px"><span style="cursor:pointer;color:#3B9AFF" onclick="popHostStorageWindow()"><i class="icon-plus"></i>&nbsp;设置主机存储</span></td>
	        			<td align="left" width="33%"height="20px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeHostInfo()"><i class="icon-cancel-3"></i>&nbsp;删除主机</span></td>
	        		</tr>
	        		<tr>
	        			<td align="left" height="20px"><span style="cursor:pointer;color:#3B9AFF" onclick="viewOverviewHostMonitorInfo()"><i class="icon-doc-text"></i>&nbsp;详情</span></td>
	        			<td align="left" height="20px"></td>
	        			<td align="left" height="20px"></td>
	        		</tr>
	        		<tr></tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	
  	<div style="width:98%;height:300px;margin-left:1%;position:relative">
  		<div class="customPanel" style="width:49.3%;height:310px;float:left;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
	        <div>
	        	<div id='storageChart' style="width:45%;height:280px;float:left;border:0px;"></div>
	        	<div id='VmChart' style="width:54%;float:left;height:280px;border:0px;"></div>	
	        </div>
  		</div>
  		
  		<div class="customPanel" style="position:absolute;left:50.4%;top:-115px;width:49%;height:170px;">
  		    <div class="title">&nbsp;&nbsp;资源监控
  		    	<!-- <font style="cursor:pointer;position:absolute;right:5px" onclick="asyncHostMonitorInfo()"><i class="icons-blue icon-arrows-cw"></i></font> -->
  		    </div>   
	        <div>
	        	   <table border="0" width="100%" cellspacing="0" cellpadding="0">
	        	        <tr>
	        	   			<td colspan="4">&nbsp;</td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td width="25%" align="right">CPU总容量：</td>
	        	   			<td width="25%" align="left"><span id="cpuTotalCapacity"></span></td>
	        	   			<td width="25%" align="right" height="25px">CPU可用容量：</td>
	        	   			<td width="25%" align="left"><span id="cpuAvailCapacity"></span></td>
	        	   			
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >CPU使用率：</td>
	        	   			<td height="30px" colspan="3"><div style='overflow: hidden;' id='cpuProgressBar'></div></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td colspan="4">&nbsp;</td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" height="25px">内存可用容量：</td>
	        	   			<td align="left"><span id="memoryAvailCapacity"></span></td>
	        	   			<td align="right">内存总容量：</td>
	        	   			<td align="left"><span id="memoryTotalCapacity"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >内存使用率：</td>
	        	   			<td height="30px" colspan="3"><div style='overflow: hidden;' id='memoryProgressBar'></div></td>
	        	   		</tr>
	        	   </table>	
	        </div>
  		</div>
  		
  			<div id="reLocalVmWindowsP" style = "display: none">
				<div id = "reLocalVmWindowsC"style="width:100%;margin:0px;" ></div>
			<input id="reLocalCancel" type="button" value="取消" style="float:right;margin-top: 5px;margin-right: 5px"/>
			<input style="float:right; margin-top: 5px; margin-right: 5px" onclick="migrateVmByHost()" type="button" id="reLocalOK" value="确定" />
		</div>
		<div id="popSetEnterModelWindow" style = "display: none">
			<div>操作虚拟机</div>
			<div id="setEnterModelConfigForm"
				style="overflow: hidden;">
				<input type="hidden" data-name="resTopologySid"
					id="set-ve-topologysid" />
					
					<div style='margin-top: 10px;' id='shutDownVm'>
			           关闭所有虚拟机</div>
			        <div style='margin-top: 10px;' id='reLocalVm'>
			            <span>迁移所有虚拟机</span></div>
			            
			            
			     	<div style="width: 100%; padding-top: 10px; text-align: right">
						<input style="margin-right: 5px;" onclick='submitSelect()'
							type="button" id="ok" value="确定" />
						<input id="cancel" type="button" value="取消" />
					</div>
			</div>
		</div>
  		
  		<div class="customPanel" style="position:absolute;left:50.4%;top:65px;width:49%;height:300px;">
  		    <div class="title">&nbsp;&nbsp;资源分配</div>   
	        <div>
	        	   <table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
	        	   			<td width="25%" align="right" height="25px">CPU总核数：</td>
	        	   			<td width="20%" align="left"><span id="cpuTotalAllot" style="color:#D45753"></span></td>
	        	   			<td width="35%" align="right">CPU分配核数：</td>
	        	   			<td width="20%" align="left"><span id="cpuAllotCapacity" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >CPU分配率：</td>
	        	   			<td height="30px" colspan="3">
	        	   			     <span id='cpuAllotProgressBar' style="color:orange"></span>
	        	   			 </td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" height="25px">内存总容量：</td>
	        	   			<td align="left"><span id="memoryTotalAllot" style="color:#D45753"></span></td>
	        	   			<td align="right">内存已分配容量：</td>
	        	   			<td align="left"><span id="memoryAllotCapacity" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >内存分配率：</td>
	        	   			<td height="30px" colspan="3">
	        	   			      <span id='memoryAllotProgressBar' style="color:orange"></span>
	        	   			</td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" height="25px">存储总容量：</td>
	        	   			<td align="left"><span id="storageTotalAllot" style="color:#D45753"></span></td>
	        	   			<td align="right">存储已分配容量：</td>
	        	   			<td align="left"><span id="storageAllotCapacity" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >存储分配率：</td>
	        	   			<td height="30px" colspan="3">
	        	   			      <span id='storageAllotProgressBar' style="color:orange"></span>
	        	   			</td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" height="25px">本地存储总容量：</td>
	        	   			<td align="left"><span id="localStorageTotalAllot" style="color:#D45753"></span></td>
	        	   			<td align="right">本地存储已分配容量：</td>
	        	   			<td align="left"><span id="localStorageAllotCapacity" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >本地存储分配率：</td>
	        	   			<td height="30px" colspan="3">
	        	   			      <span id='localStorageAllotProgressBar' style="color:orange"></span>
	        	   			</td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" height="25px">外置存储总容量：</td>
	        	   			<td align="left"><span id="shareStorageTotalAllot" style="color:#D45753"></span></td>
	        	   			<td align="right">外置存储已分配容量：</td>
	        	   			<td align="left"><span id="shareStorageAllotCapacity" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >外置存储分配率：</td>
	        	   			<td height="30px" colspan="3">
	        	   			      <span id='shareStorageAllotProgressBar' style="color:orange"></span>
	        	   			</td>
	        	   		</tr>
	        	   </table>	
	        </div>
  		</div>
  		
  	</div>
  </div>
  <%@ include file="../../vm/res-vm-migrate-select.jsp"%>
  <%@ include file="../host/res-host-storage.jsp"%>
<script type="text/javascript">

	function initHostSummary(){
		 // 基本信息赋值
		 var hostData = setHostSummaryBasicInfo();
		 
		 // 初始化资源分配信息
		 $("#cpuTotalAllot").html(hostData.cpuTotalCores+"核");
		 
		 $("#cpuAllotCapacity").html((hostData.cpuAllotCores == null)?"0核":hostData.cpuAllotCores+"核");
		 $("#memoryTotalAllot").html(((hostData.memorySize/1024).toFixed(2))+"GB");
		 $("#memoryAllotCapacity").html(((hostData.memoryAllotSize == null)?0:(hostData.memoryAllotSize/1024).toFixed(2))+"GB");
		 $("#cpuAllotProgressBar").html((hostData.cpuAllotCores/hostData.cpuTotalCores*100).toFixed(2)+"%");
		 $("#memoryAllotProgressBar").html((hostData.memoryAllotSize/hostData.memorySize*100).toFixed(2)+"%");
		 
		// 初始化虚拟机监控信息值
		var vm = new virtualVMDatagridModel();
   		var vmData = vm.getVmStatisticsInfoInHost();
   		
   		// 初始化存储资源监控信息值
   		var storage = new storageConfigMgtModel();
   		var storageData = storage.StorageResourcesStatisticsVolumeInHost(resTopologySid);
   		
/* 	   	 var hostStorage = new addMountedHostStorageModel();
	     hostStorage.initMountDatagrid();
	     hostStorage.initPopWindow();
	     hostStorage.searchMountedStorageData(hostData.resHostSid); */
   		
   		// 初始化虚拟机统计信息
		initVMChart(vmData);
		initStorageChart(storageData);
		
		// 获取主机信息
		getHostBasicInfo();
		// 获取存储分配信息
		getStorageAllotInfo();
	}

	 // 初始化虚拟机图表
	 function initVMChart(data){
		 var source ={
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
                                     labelRadius: 90,
                                     initialAngle: 15, 
                                     radius: 80,
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
	 
	 // 初始化存储图表
	 function initStorageChart(data){
		 var source ={
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
             title: "外置存储容量统计",
             description: "(总容量:"+data.storageCount+"GB)",
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
                                     labelRadius: 90,
                                     initialAngle: 15, 
                                     radius: 80,
                                     centerOffset: 0,
                                     formatSettings: { sufix: '', decimalPlaces: 0 }
                                 }
                             ]
                     }
                 ]
         };
         // setup the chart
         $('#storageChart').jqxChart(settings);
	 }
	 
	//给概要基本信息赋值
	 var hostData; 
	 function setHostSummaryBasicInfo(){
		 
		 Core.AjaxRequest({
	 			url : ws_url + "/rest/hosts/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				hostData = data;
	 				$("#hostName").html(data.hostName);
	 			 	$("#status").html(data.statusName);
	 			 	$("#ipAddr").html(data.hostIp);
	 			 	$("#totalCpu").html(data.cpuCores);
	 			 	$("#memory").html((data.memorySize/1024).toFixed(2));
	 			 	$("#osType").html(data.hostOsType);
	 			 	$("#hostType").html(data.model);
	 			}
	     });
		 return hostData;
	 }
	
	//删除存储，供非datagrid调用的删除方法
	 function removeHostInfo(){
	 	Core.confirm({
			title:"提示",
			message:"您确定要删除该主机吗?",
			yes:"确定",
			confirmCallback:function(){
				Core.AjaxRequest({
	 				url : ws_url + "/rest/hosts/delete/"+resTopologySid+"",
	 				type:"get",
	 				callback : function (data) {
	 					//setHostTreeValue();
	 					setHostVirtualTree();
	 			    },
	 			    failure:function(data){
	 			    	
	 			    }
	 			});
			}
		});
	 }
	
	// 根据sid获取主机基本信息
	function getHostBasicInfo(){
		Core.AjaxRequest({
 			url : ws_url + "/rest/hosts/"+resTopologySid,
 			type:'get',
 			async:false,
 			callback : function (data) {
 				// cpu总容量
 				var cpuTotal = data.cpuCores*data.cpuGhz;
 				$("#cpuTotalCapacity").html(cpuTotal.toFixed(2)+"GHz");
 				// cpu可用容量
 				var cpuAvail =(cpuTotal == 0)? 0: (data.cpuCores*data.cpuGhz-data.useCpuGhz);
 				$("#cpuAvailCapacity").html(cpuAvail.toFixed(2)+"GHz");
 				// cpu使用率
 				var rate = (cpuTotal == 0)? 0: data.useCpuGhz/cpuTotal*100;
 				$("#cpuProgressBar").jqxProgressBar({ width: '95%', height: 18, value:rate ,showText:true,theme:currentTheme});
 				
 				// 内存可用容量
	   			$("#memoryAvailCapacity").html(((data.memorySize-data.useMemorySize)/1024).toFixed(2)+"GB");
 				// 内存总容量
				$("#memoryTotalCapacity").html((data.memorySize/1024).toFixed(2)+"GB");
 				// 内存使用率
				$("#memoryProgressBar").jqxProgressBar({ width: '95%', height: 18, value: (data.useMemorySize)/data.memorySize*100,showText:true,theme:currentTheme});
 			}
     	});
	}
	// 同步主机监控信息
	function asyncHostMonitorInfo(){
		// 获取监控信息
		Core.AjaxRequest({
 			url : ws_url + "/rest/host/getHostMonitorInfo/"+resTopologySid,
 			type:'get',
 			async:false,
 			callback : function (data) {
 				getHostBasicInfo();
 			}
     	});
	}
	
	// 查询出存储的分配信息
	function getStorageAllotInfo() {
		Core.AjaxRequest({
			url : ws_url + "/rest/storages/statistical/host/allot/"+ resTopologySid,
			type : 'get',
			async : false,
			callback : function(data) {
				if(data != null){
					$("#storageTotalAllot").html(data.totalCapacity + "GB");
					$("#storageAllotCapacity").html((data.allotCapacity == null ? "0" : data.allotCapacity)+ "GB");
					$("#storageAllotProgressBar").html((data.allotRate == null ? "0.00" : data.allotRate)+ "%");
					
					$("#localStorageTotalAllot").html(data.localTotalCapacity+"GB");
	 				$("#localStorageAllotCapacity").html((data.localAllotCapacity == null ? "0":data.localAllotCapacity) +"GB");
	 				$("#localStorageAllotProgressBar").html((data.localAllotRate == null ? "0.00":data.localAllotRate) +"%");
	 				
	 				$("#shareStorageTotalAllot").html(data.shareTotalCapacity+"GB");
	 				$("#shareStorageAllotCapacity").html((data.shareAllotCapacity == null ? "0":data.shareAllotCapacity) +"GB");
	 				$("#shareStorageAllotProgressBar").html((data.shareAllotRate == null ? "0.00":data.shareAllotRate) +"%");
				}else{
					$("#storageTotalAllot").html("0GB");
					$("#storageAllotCapacity").html("0GB");
					$("#storageAllotProgressBar").html("0.00%");
					
					$("#localStorageTotalAllot").html("0GB");
	 				$("#localStorageAllotCapacity").html("0GB");
	 				$("#localStorageAllotProgressBar").html("0.00%");
	 				
	 				$("#shareStorageTotalAllot").html("0GB");
	 				$("#shareStorageAllotCapacity").html("0GB");
	 				$("#shareStorageAllotProgressBar").html("0.00%");
				}
			}
		});
	}
	
	//弹出新增网络对话框
	function popSetEnterModel() {
		var windowW = $(window).width();
		var windowH = $(window).height();
		
		Core.AjaxRequest({
			url : ws_url + "/rest/vms/checkVmStatusByHost",
			type : 'post',
			params:{
				resHostSid : resTopologySid	
			},
			async : false,
			callback : function(data) {
				if(data.status=="success"){
					hostOperation("进入维护模式");
				}else{
					Core.confirm({
						title:"提示",
						message:"该主机下还有未关闭的虚拟机,需要关闭或者迁移这些虚拟机吗？",
						width:400,
						yes:"确定",
						confirmCallback:function(){
							$("#popSetEnterModelWindow").jqxWindow({
								position : {
									x : (windowW - 350) / 2,
									y : (windowH - 160) / 2
								},
								width : 350,
								height : 160,
								isModal : true,
								resizable : false,
								theme : currentTheme,
								okButton : $('#ok'),
								cancelButton : $('#cancel'),
								initContent:function(){
						    	       
					            	$("#shutDownVm").jqxRadioButton({ width: 250, height: 25, theme:currentTheme,checked: true});
					                $("#reLocalVm").jqxRadioButton({ width: 250, height: 25,theme:currentTheme});
					            	
					    	        $("#ok").jqxButton({ width: '50',height:"25",theme:currentTheme});
					    	        $("#cancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
					            }
							});

							$("#popSetEnterModelWindow").jqxWindow('open');
						}
					});
				}
			}
		});
	}

	// 初始化用户datagrid的数据源
	 function initVMDatagrid() {
		this.searchObj = {
			"qm.vmNameLike" : null,
			"qm.status" : null,
			"qm.vmIpLike" : null,
			"qm.resTopologyType" : resTopologyType,
			"qm.resTopologySid" : resTopologySid
		};
		var dataAdapter = Core.getPagingDataAdapter({
			gridId : "vmdatagrid",
			url : ws_url + "/rest/vms/findAll",
			params : searchObj
		});
		$("#reLocalVmWindowsC").jqxGrid({
			width : "98%",
            height:180,
			theme : currentTheme,
			source : dataAdapter,
			columnsresize : true,
			virtualmode : true,
			rendergridrows : function() {
				return dataAdapter.records;
			},
			pageable : true,
			pagesize : 5,
			autoheight : false,
			autowidth : false,
			sortable : true,
			selectionmode : "checkbox",
			localization : gridLocalizationObj,
			columns : [

			{
				text : '虚拟机名称',
				datafield : 'vmName'
			}, {
				text : '操作系统',
				datafield : 'osVersionName'
			}, {
				text : '状态',
				datafield : 'statusName'
			} ]
			
		});
		// 控制按钮是否可用
    	  $("#reLocalVmWindowsC").on('rowselect', function (event) {
    		  $("#reLocalOK").jqxButton({ width: '50',height:"25",theme:currentTheme,disabled:false});
    	  });
	};

	function submitSelect() {
		if ($("#shutDownVm").jqxRadioButton('checked')) {
			Core.AjaxRequest({
						url : ws_url + "/rest/hosts/stopAllVmOfHost/"
								+ resTopologySid,
						type : 'get',
						callback : function(data) {
						$("#host_summary").load(ctx
							+ "/pages/resources/topology/host/res-host-virtual-summary.jsp",
							function() {
							initHostSummary();
							});
						}
					});
		} else if ($("#reLocalVm").jqxRadioButton('checked')) {
			initVMDatagrid();
			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#reLocalVmWindowsP").jqxWindow({
				title : '请选择要迁移的虚拟机',
				width : 800,
				height : 255,
				isModal : true,
				theme : currentTheme,
				showCloseButton : true,
				okButton : $('#reLocalOK'),
				cancelButton : $('#reLocalCancel'),
				initContent:function(){
	    	        $("#reLocalOK").jqxButton({ width: '50',height:"25",theme:currentTheme,disabled:true});
	    	        $("#reLocalCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	            }
				
			});
			$("#reLocalVmWindowsP").jqxWindow('open');

		}
	}
	
	//弹出选择迁移类型
  function migrateVmByHost() {
  	var rowindex = $('#reLocalVmWindowsC').jqxGrid('selectedrowindexes');
  	var datas = new Array();
  	if(rowindex.length >= 0){
		for(var i=0;i<rowindex.length;i++){
			var data = $('#reLocalVmWindowsC').jqxGrid('getrowdata', rowindex[i]);
			datas[i]=data.resVmSid;
		}
  		var selectMigrateType=new migrateSelectModel();
  		selectMigrateType.setData(datas);
  		selectMigrateType.initPopWindow();
  		var windowW = $(window).width();
  		var windowH = $(window).height();
  		$("#migrateSelectWindow").jqxWindow({
  			position : {
  				x : (windowW - 350) / 2,
  				y : (windowH - 185) / 2
  			}
  		});
  		$("#migrateSelectWindow").jqxWindow('open');
  	}
}
	function hostOperation(value){
		var url;
	 if(value == '关机') {
		url = ws_url + "/rest/host/stop/"+resTopologySid+"";
		}else if(value == '重启') {
		url = ws_url + "/rest/host/reboot/"+resTopologySid+"";
		}else if(value == '进入维护模式') {
		url = ws_url + "/rest/host/enterMaintenance/"+resTopologySid+"";
		}else if(value == '退出维护模式') {
		url = ws_url + "/rest/host/exitMaintenance/"+resTopologySid+"";
		} 
		
		if (hostData.statusName != '维护') {
			if (value == '关机'||value=='重启') {
				Core.confirm({
					title:"提示",
					message:"主机当前未处于维护状态，您确定要"+value+"吗?",
					yes:"确定",
					confirmCallback:function(){
						Core.AjaxRequest({
			 				url : url ,
			 				type:"get",
			 				callback : function (data) {
			 					$("#host_summary").load(ctx+"/pages/resources/topology/host/res-host-virtual-summary.jsp",function(){
			 						initHostSummary();
			                	});
			 	 			}
			 			});
					}
				});
				
			}else{
				Core.confirm({
					title:"提示",
					message:"您确定要"+value+"吗?",
					yes:"确定",
					confirmCallback:function(){
						Core.AjaxRequest({
			 				url : url ,
			 				type:"get",
			 				callback : function (data) {
			 					$("#host_summary").load(ctx+"/pages/resources/topology/host/res-host-virtual-summary.jsp",function(){
			 						initHostSummary();
			                	});
			 			    }
			 			});
					}
				});
			}

		}else {
			Core.confirm({
				title:"提示",
				message:"您确定要"+value+"吗?",
				yes:"确定",
				confirmCallback:function(){
					Core.AjaxRequest({
		 				url : url ,
		 				type:"get",
		 				callback : function (data) {
		 					$("#host_summary").load(ctx+"/pages/resources/topology/host/res-host-virtual-summary.jsp",function(){
		 						initHostSummary();
		                	});
		 			    }
		 			});
				}
			});
		}
	}
	
	function asyncHostInfo(){
		Core.AjaxRequest({
			url:ws_url + "/rest/hosts/asyncHostInfo/" + resTopologySid,
			type:'get',
			async:false,
			callback : function (data) {
				$("#host_summary").load(ctx+"/pages/resources/topology/host/res-host-virtual-summary.jsp",function(){
						initHostSummary();
            	});
			}
		});
	}
	
	// 弹出主机详情
	function viewOverviewHostMonitorInfo(){
		Core.AjaxRequest({
 			url : ws_url + "/rest/hosts/"+resTopologySid,
 			type:'get',
 			async:false,
 			callback : function (data){
 				window.parent.addHostParentTabs(data.resHostSid,data.monitorNodeId,data.hostName);  
 			}
		});
	}
</script>

