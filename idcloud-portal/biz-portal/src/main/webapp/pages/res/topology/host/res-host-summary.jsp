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
  		    	<font style="position:absolute;right:5px;cursor:pointer" onclick="popExternalEditHostWindow()"><i class="icons-blue icon-edit"></i></font>
  		    </div>   
	        <div>
	        	<table id="clusterBaseInfo" border="0" width="100%" cellspacing="0" cellpadding="5">
	        		<tr>
	        			<td align="right" width="100px" height="25px">名称:</td>
	        			<td align="left">
	        				<span id="hostName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">描述:</td>
	        			<td align="left">
	        				<span id="description"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">状态:</td>
	        			<td align="left">
	        				<span id="status"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">是否维护状态:</td>
	        			<td align="left">
	        				否
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">所属集群:</td>
	        			<td align="left">
	        				空
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right" height="25px">主机IP:</td>
	        			<td align="left">
	        				<span id="ipAddr"></span>
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
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF"><i class="icon-wrench-3"></i>进入维护模式</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF"><i class="icon-cw-circled"></i>重新启动</span></td>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF"><i class="icon-off"></i>关机</span></td>
	        		</tr>
	        		<tr>
	        			<td align="left" height="25px"><span style="cursor:pointer;color:#3B9AFF" onclick="removeHostInfo()"><i class="icon-cancel-3"></i>移除主机</span></td>
	        			<td align="left" height="25px">&nbsp;</td>
	        			<td align="left" height="25px">&nbsp;</td>
	        		</tr>
	        	</table>
	        </div>
  		</div>
  	</div>
  	
  	<div style="width:98%;height:330px;margin-left:1%;position:relative">
  		<div class="customPanel" style="width:49.5%;height:310px;float:left;">
  		    <div class="title">&nbsp;&nbsp;资源统计</div>   
	        <div>
	        	<div id='VmChart' style="height:280px;border:0px"></div>	
	        </div>
  		</div>
  		
  		<div class="customPanel" style="position:absolute;left:50.7%;top:-50px;width:49%;height:200px;">
  		    <div class="title">&nbsp;&nbsp;监控信息</div>   
	        <div>
	        	   <table border="0" width="100%" cellspacing="0" cellpadding="0">
	        	        <tr>
	        	   			<td colspan="4">&nbsp;</td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td width="25%" align="right" height="25px">CPU可用资源：</td>
	        	   			<td width="25%" align="left">4532 MHz</td>
	        	   			<td width="25%" align="right">CPU已使用资源：</td>
	        	   			<td width="25%" align="left">680 MHz</td>
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
	        	   			<td align="left">65536 MB</td>
	        	   			<td align="right">CPU已使用容量：</td>
	        	   			<td align="left">28180 MB</td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" >CPU使用率：</td>
	        	   			<td height="30px" colspan="3"><div style='overflow: hidden;' id='memoryProgressBar'></div></td>
	        	   		</tr>
<!-- 	        	   		<tr> -->
<!-- 	        	   			<td height="25px">&nbsp;&nbsp;网络流出流速：114.64 KB/s</td> -->
<!-- 	        	   			<td>网络流入流速：4.05 KB/s</td> -->
<!-- 	        	   		</tr> -->
<!-- 	        	   		<tr> -->
<!-- 	        	   			<td height="25px">&nbsp;&nbsp;磁盘I/O写入：102.43 KB/s</td> -->
<!-- 	        	   			<td>磁盘I/O读出：284.78 KB/s</td> -->
<!-- 	        	   		</tr> -->
	        	   </table>	
	        </div>
  		</div>
  	</div>
  </div>
  
  <%@ include file="../../host/res-edit-host.jsp"%>
   
<script type="text/javascript">

	function initHostSummary(){
		 // 基本信息赋值
		 setHostSummaryBasicInfo();
		 
		// 初始化虚拟机监控信息值
   		var vm = new vmManagedModel();
   		var vmData = vm.getVmStatisticsInfo();
		
		 initVMChart(vmData);
		 // 初始化进度条
		 $("#cpuProgressBar").jqxProgressBar({ width: '95%', height: 18, value: 15,showText:true,theme:currentTheme});
		 $("#memoryProgressBar").jqxProgressBar({ width: '95%', height: 18, value: 43,showText:true,theme:currentTheme});
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
                                     radius: 90,
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
	 
	//给概要基本信息赋值
	 function setHostSummaryBasicInfo(){
	 	var host = new editHostModel();
	 	if(resTopologySid.substring(0,1) =="p"){
	 		resTopologySid = resTopologySid.substring(1);
	 	}
	 	var data = host.getHostByResSid(resTopologySid);
	 	
	 	$("#hostName").html(data.hostName);
	 	$("#description").html(data.resName);
	 	$("#status").html(data.usageStatusName);
	 	$("#ipAddr").html(data.hostIp);
	 	
	 }
	
	//删除存储，供非datagrid调用的删除方法
	 function removeHostInfo(){
	 	if(resTopologySid.substring(0,1) =="p"){
	  		resTopologySid = resTopologySid.substring(1);
	  	}
	 	Core.confirm({
			title:"提示",
			message:"确定要删除该主机吗?",
			yes:"确定",
			confirmCallback:function(){
				Core.AjaxRequest({
	 				url : ws_url + "/rest/hosts/delete/"+resTopologySid+"",
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

