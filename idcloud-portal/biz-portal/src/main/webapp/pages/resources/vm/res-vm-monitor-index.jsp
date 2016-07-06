<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   <div id="monitorVmWindow">
        <div>监控信息</div>
        <div>
        		<div style="width:100%;height:100%;overflow-y:auto;">
			   		<div style="width:98%;height:40px;margin-left:1%;">
			   			 <table border="0" width="100%" height="40px" cellpadding="0" cellspacing="0">
			   			 	 <tr>
			   			 	 	<td width="30" align="right">周期:</td>
			   			 	 	<td width="130" align="center"><div id="searchMonitorInfo"></div></td>
			   			 	 	<td width="80" align="left"><input id="searchMonitorBtn" type="button" value="查询" /></td>
			   			 	 	<td align="right"><input id="searchMonitorRefresh" type="button" value="刷新" /></td>
			   			 	 </tr>
			   			 </table>
			   		</div>
			   		
					<div style="width:98%;margin-left:1%;">   		
					   		<div class="customPanel" style="width:49%;height:300px;float:left">
						  		    <div class="title">&nbsp;&nbsp;CPU占用率</div>
							        <div>
							        	<div id='cpuUsedchartContainer' style="width:370px; height:275px;border:0px;"></div>
							        </div>
					  		</div>
					  		
					  		<div class="customPanel" style="width:49%;height:300px;margin-left:1%;float:left">
						  		    <div class="title">&nbsp;&nbsp;内存占用率</div>
							        <div>
							        	<div id='memoryUsedchartContainer' style="width:370px; height:275px;border:0px;"></div>
							        </div>
					  		</div>
					  		<div class="customPanel" style="width:49%;margin-top:10px;height:300px;float:left">
						  		    <div class="title">&nbsp;&nbsp;虚拟机磁盘占用率</div>
							        <div>
							        	<div id='diskUsedchartContainer' style="width:370px; height:275px;border:0px;"></div>
							        </div>
					  		</div>
					  		
					  		<div class="customPanel" style="width:49%;height:300px;margin-top:10px;margin-left:1%;float:left">
						  		    <div class="title">&nbsp;&nbsp;磁盘IO</div>
							        <div>
							        	<div id='diskIoUsedchartContainer' style="width:370px; height:275px;border:0px;"></div>
							        </div>
					  		</div>
				  	</div>
				  	
				  	<div style="width:98%;margin-left:1%;">   		
					   		<div class="customPanel" style="width:99.6%;height:300px;margin-top:10px; float:left">
						  		    <div class="title">&nbsp;&nbsp;网络流速</div>
							        <div>
							        	<div id='networkSpeedchartContainer' style="width:740px; height:275px;border:0px;"></div>
							        </div>
					  		</div>
				  	</div>
			   
			   </div>
        </div>
   </div> 
  <script type="text/javascript">
	  var monitorVm = new monitorVMModel();
	  monitorVm.initPopWindow();
  </script>
   <script type="text/javascript">
   		
   		// 初始化查询和刷新按钮
   		var source = ["一个小时","一天","一个月"];
        // Create a jqxDropDownList
        $("#searchMonitorInfo").jqxDropDownList({ source: source, selectedIndex: 1, width: '120', height: '22',theme:currentTheme});
   		
		$("#searchMonitorBtn").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		$("#searchMonitorRefresh").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		
   </script>
   