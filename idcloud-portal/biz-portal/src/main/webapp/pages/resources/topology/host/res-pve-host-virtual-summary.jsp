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
  		
  		<div class="customPanel" style="position:absolute;left:50%;width:48.6%;height:215px;">
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
  	</div>
  	
  	<div style="width:98%;height:400px;margin-left:1%;position:relative;">
  		
  		<div id="hostPveItem" class="customPanel" style="width:60.7%;height:180px;float:left;">
  		    <div class="title">&nbsp;&nbsp;主机配件
  		    	<font style="cursor:pointer;position:absolute;right:45px" onclick="popAddPveHostPhyIoWindow()"><i class="icons-blue icon-plus-1"></i></font>
  		    	<font style="cursor:pointer;position:absolute;right:25px" onclick="popEditPveHostPhyIoWindow()"><i class="icons-blue icon-pencil"></i></font>
  		    	<font style="cursor:pointer;position:absolute;right:5px" onclick="deletePveHostItem()"><i class="icons-blue icon-trash"></i></font>
  		    </div>   
	        <div>
	        	 <div id='pveHostPhysicalDatagrid'></div>   
	        </div>
  		</div>
  		
  		<div id="hostPveVios" class="customPanel" style="width:38.2%;margin-left:0.5%;height:180px;float:left;">
  		    <div class="title">&nbsp;&nbsp;主机VIOS
  		    	<font style="cursor:pointer;position:absolute;right:45px" onclick="popAddPveHostViosWindow()"><i class="icons-blue icon-plus-1"></i></font>
  		    	<font style="cursor:pointer;position:absolute;right:25px" onclick="popEditPveHostViosWindow()"><i class="icons-blue icon-pencil"></i></font>
  		    	<font style="cursor:pointer;position:absolute;right:5px" onclick="deletePveHostVios()"><i class="icons-blue icon-trash"></i></font>
  		    </div>   
	        <div>
	        	 <div id='pveHostViosDatagrid'></div>   
	        </div>
  		</div>
  	
  		<div class="customPanel" style="width:70%;height:180px;margin-top:10px;float:left;">
  		    <div class="title">&nbsp;&nbsp;主机CPU池
  		    	<font style="cursor:pointer;position:absolute;right:45px" onclick="popAddPveHostCpuPool()"><i class="icons-blue icon-plus-1"></i></font>
  		    	<font style="cursor:pointer;position:absolute;right:25px" onclick="popEditPveHostCpuPool()"><i class="icons-blue icon-pencil"></i></font>
  		    	<font style="cursor:pointer;position:absolute;right:5px" onclick="deletePveHostCpuPool()"><i class="icons-blue icon-trash"></i></font>
  		    </div>   
	        <div>
	        	<div id='pveHostCpuPoolDatagrid' ></div> 
	        </div>
  		</div>
  		
  		<div class="customPanel" style="width:29%;margin-left:0.5%;margin-top:10px;height:180px;float:left;">
  		    <div class="title">&nbsp;&nbsp;虚拟交换机
  		    	<font style="cursor:pointer;position:absolute;right:25px" onclick="popAddPveHostVsWindow()"><i class="icons-blue icon-plus-1"></i></font>
  		    	<font style="cursor:pointer;position:absolute;right:5px" onclick="deletePveHostVs()"><i class="icons-blue icon-trash"></i></font>
  		    </div>   
	        <div>
	             <div id='pveHostDvsDatagrid'></div> 
	        </div>
  		</div>
  		
  	</div>
  </div> 
  
<script type="text/javascript">

	function initHostSummary(){
		 // 初始化资源分配信息
		 getHostMonitorInfo();
		 // 获取主机信息
		//getHostBasicInfo();
		 
		 var hostData =  setHostSummaryBasicInfo();
		 if("01" == hostData.isViosFlag){
			 // 不是vios
			 $("#hostPveVios").hide();
			 $("#hostPveItem").css("width","99.7%");
		 }else{
			 $("#hostPveVios").show();
			 $("#hostPveItem").css("width","60.7%");
		 }
		
		 var pveHost = new virtualPveHostDatagridModel();
		 pveHost.initWithPowerHostDatagrid();
		 pveHost.searchPveHostCpuPoolInfo(resTopologySid);
	}
	
	// 根据sid获取主机的资源监控信息
	function getHostMonitorInfo(){
		Core.AjaxRequest({
 			url : ws_url + "/rest/host/"+resTopologySid,
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
	
	//给概要基本信息赋值
	var pveHostData;
	 function setHostSummaryBasicInfo(){
		
		 Core.AjaxRequest({
	 			url : ws_url + "/rest/host/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				pveHostData = data;
	 				$("#hostName").html(data.hostName);
	 			 	$("#status").html(data.statusName);
	 			 	$("#ipAddr").html(data.hostIp);
	 			 	$("#totalCpu").html(data.cpuCores);
	 			 	$("#memory").html((data.memorySize/1024).toFixed(2));
	 			 	$("#osType").html(data.hostOsType);
	 			 	$("#hostType").html(data.model);
	 			}
	     });
		 return pveHostData;
	 }
	 // 根据sid获取主机基本信息
	function getHostBasicInfo(){
		Core.AjaxRequest({
 			url : ws_url + "/rest/host/"+resTopologySid,
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
</script>

