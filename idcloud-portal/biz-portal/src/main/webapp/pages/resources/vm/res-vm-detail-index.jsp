<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
	String vmName = request.getParameter("vmName");
%>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/vm/vm-info-model.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/highcharts/highcharts.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/highcharts/highcharts-more.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/highcharts/modules/exporting.js"></script>
		<style type="text/css">
			#allVmInfo{
				width:100%;
				height:100%;
				padding:0px;
				margin:0px;
			}
			
			#monitorContent .customPanel{
				display:none;
				overflow:hidden;
			}
			.showContent{
				display:block;
			}
			
		</style>
</head>
<body class='default'>
	   <div style="position:absolute;top:0px;left:0px;width:32%;height:99.5%;">
	   			<div class="customPanel" style="width:98%; height: 280px;margin-left:5px;margin-top:7px;">
				   		<div class="title">&nbsp;&nbsp;基本信息</div>
						<div>
							<table border="0" width="100%" cellspacing="2" cellpadding="5">
								<tr>
									<td align="right" width="120">虚拟机名称:</td>
									<td align="left"><span id="allocateVmName"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">操作系统:</td>
									<td align="left"><span id="osTypeName"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">CPU(核):</td>
									<td align="left"><span id="cpuCores"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">内存(MB):</td>
									<td align="left" colspan="3"><span id="memorySize"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">置备容量(GB):</td>
									<td align="left" colspan="3"><span id="provisionStorage"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">虚机状态:</td>
									<td align="left"><span id="statusName"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">所属主机:</td>
									<td align="left"><span id="hostName1"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">初始管理账户/密码:</td>
									<td align="left"><span id="accountPasswd"></span></td>
								</tr>
								<tr>
									<td align="right" width="100">创建时间:</td>
									<td align="left"><span id="vmCreateTime"></span></td>
								</tr>
							</table>
						</div>
			   </div>
			   
			   <div class="customPanel" style="width:98%;height: 130px;margin-left:5px;margin-top:7px;">
					<div class="title">&nbsp;&nbsp;磁盘信息</div>
					<div>
						<div style="width:100%; height: 100px; margin-top: 5px;overflow-y:auto;">
							<div id='vmStorageDatagrid' style="width: 98%;margin-left: 1%"></div>
						</div>
					</div>
			   </div>
			   
			   <div class="customPanel" style="width:98%; height:130px;margin-left:5px;margin-top:7px;">
					<div class="title">&nbsp;&nbsp;网卡信息</div>
					<div>
						<div style="width:100%; height: 100px; margin-top: 5px;overflow-y:auto;">
							<div id='vmNicDatagrid' style="width: 98%;margin-left: 1%"></div>
						</div>
					</div>
			   </div>
	   </div>
	   
	   <div style="position:absolute;top:0px;left:32.5%;width:67.2%;height:99.5%;">
	   		 <div class="customPanel" style="position:absolute;left:0.5%;top:10px;width:99%;height:97%;overflow-y:auto;">
				<div class="title">&nbsp;&nbsp;监控信息</div>
				<div>
					<div style="width:100%;height:100%;">
					   		<div style="width:98%;height:35px;margin-left:1%;">
					   			 <table border="0" width="100%" height="35px" cellpadding="5" cellspacing="0">
					   			     <tr>
					   			 	 	<td align="right" width="60px">周期:</td>
					   			 	 	<td align="left"  width="105px"><div id="searchMonitorInfo"></div></td>
					   			 	 	<td align="left" width="60px">
					   			 	 	     <input id="searchMonitorBtn" type="button" onclick="setMonitorPerid()" value="查询" />
					   			 	 	</td>
					   			 	 	<td align="right">切换至:</td> 
					   			 	 	<td align="right" width="105px"><div id="searchMonitorContent"></div></td>
					   			 	 </tr>
					   			 </table>
					   		</div>
					   		
							<div id="monitorContent" style="width:98%;height:99%;margin-left:1%;">   		
						   		<div  class="customPanel" style="display:block; width:100%;height:300px;">
							  		    <div class="title">&nbsp;&nbsp;CPU使用率 
							  		    	<div style="position:absolute;top:0px;left:50%;width:50%;height:20px;text-align:right">
							  		    		最大使用率:<font id="cpu_max_usage" style="color:#D45753"></font>&nbsp;&nbsp;&nbsp;
							  		    		平均使用率:<font id="cpu_avg_usage" style="color:#D45753"></font>&nbsp;&nbsp;&nbsp;
							  		    		最小使用率:<font id="cpu_min_usage" style="color:#D45753"></font>&nbsp;&nbsp;&nbsp;
							  		    	</div>
							  		    </div>
								        <div>
								        	<div id='cpuUsedchartContainer' style="width:100%; height:275px;border:0px;"></div>
								        </div>
						  		</div>
						  		
						  		<div class="customPanel" style="width:100%;height:300px;margin-top:7px;">
							  		    <div class="title">&nbsp;&nbsp;内存使用率
							  		    	<div style="position:absolute;top:0px;left:50%;width:50%;height:20px;text-align:right">
							  		    		最大使用率:<font id="mem_max_usage" style="color:#D45753"></font>&nbsp;&nbsp;&nbsp;
							  		    		平均使用率:<font id="mem_avg_usage" style="color:#D45753"></font>&nbsp;&nbsp;&nbsp;
							  		    		最小使用率:<font id="mem_min_usage" style="color:#D45753"></font>&nbsp;&nbsp;&nbsp;
							  		    	</div>
							  		    </div>
								        <div>
								        	<div id='memoryUsedchartContainer' style="width:100%; height:275px;border:0px;"></div>
								        </div>
						  		</div>
						  		<div class="customPanel" style="width:100%;height:300px;margin-top:7px;">
							  		    <div class="title">&nbsp;&nbsp;虚拟机磁盘占用率</div>
								        <div>
								        	<div id='diskUsedchartContainer' style="width:100%; height:275px;border:0px;"></div>
								        </div>
						  		</div>
						
						  		<div class="customPanel" style="width:100%;height:300px;margin-top:7px;">
							  		    <div class="title">&nbsp;&nbsp;网络流入流速
							  		    	<div style="position:absolute;top:0px;left:50%;width:50%;height:20px;text-align:right">
							  		    		网络流入峰值:<font id="network_max_in" style="color:#D45753"></font>&nbsp;&nbsp;&nbsp;
							  		    		网络流入平均值:<font id="network_avg_in" style="color:#D45753"></font>&nbsp;&nbsp;&nbsp;
							  		    	</div>
							  		    </div>
								        <div>
								        	<div id='networkSpeedInchartContainer' style="width:100%; height:275px;border:0px;"></div>
								        </div>
						  		</div>
						  		
						  		<div class="customPanel" style="width:100%;height:300px;margin-top:7px;">
							  		    <div class="title">&nbsp;&nbsp;网络流出流速
							  		    	<div style="position:absolute;top:0px;left:50%;width:50%;height:20px;text-align:right">
							  		    		网络流出峰值:<font id="network_max_out" style="color:#D45753"></font>&nbsp;&nbsp;&nbsp;
							  		    		网络流出平均值:<font id="network_avg_out" style="color:#D45753"></font>&nbsp;&nbsp;&nbsp;
							  		    	</div>
							  		    </div>
								        <div>
								        	<div id='networkSpeedOutchartContainer' style="width:100%; height:275px;border:0px;"></div>
								        </div>
						  		</div>
					  	</div>
			     </div>
             </div>
          </div> 
	 </div>
	 
	 <!-- 修改虚拟机名称 -->
	 <div id="editVmNameWindow">
       <div>修改虚机名称</div>
       <div id="modifyVmForm" style="overflow: hidden;">
           <input type="hidden" data-name="resVmSid" id="edit-vm-resVmSid"/>
           <table border="0" width="100%" cellspacing="2" cellpadding="0">
               <tr>
                   <td align="right" height="30px;">虚拟机名称:</td>
                   <td align="left" >
                       <input type="text" data-name="vmName" id="edit-vm-name" />
                   </td>
               </tr>
               <tr>
               	<td colspan="2" height="30px;" align="right" >
               		<input style="margin-right: 5px;" onclick="submitVmNameSave()" id="modifyVmSave" type="button" value="保存" />
               		<input id="modifyVmCancel" type="button" value="取消" />
               	</td>
               </tr>
           </table>
       </div>
</div>

</body>
<script type="text/javascript">
    var vmName = '<%=vmName %>';
    var resVmSid;
    var isok = true;
    $(function(){
    	Core.AjaxRequest({
 			url : ws_url + "/rest/vms",
 			type:'post',
 			params:{
 				vmName : vmName
 			},
 			async:false,
 			callback : function (data) {
 				if(data == null || data == ""){
 					isok = false;
 				}else{
 					resVmSid = data[0].resVmSid;
 				}
 			}
     	});
    	
    	if(isok){
    		var info = new vmInfoModel(resVmSid);
      	     // 基本信息
      	     info.setVmBasicInfo();
      	 	 // 初始化window
      	 	 info.initPopWindow();
      	     // 存储信息
      	     info.initVmStorageDatagrid();
      	     info.searchVmStorageInfo();
      	     // 网卡信息
      	     info.initVmNicDatagrid();
      	     info.searchVmNicInfo();
      	     // 监控信息
      	     info.initMonitorInfo();
      	     
      	    // 初始化查询和刷新按钮
      		var dataPerid = [
      		     {name:"小时",value:"hour"},
      		     {name:"天",value:"day"},
      		     {name:"周",value:"week"},
      		     {name:"月",value:"month"}
      		];
      	     // Create a jqxDropDownList
      	    var source = {
      	           datatype: "json",
      	           datafields: [
      	               { name: 'name' },
      	               { name: 'value' }
      	           ],
      	           localData:dataPerid
      	    };
      	    var dataAdapter = new $.jqx.dataAdapter(source);
      	    
      	    $("#searchMonitorInfo").jqxDropDownList({
      	        selectedIndex: 0, source: dataAdapter, displayMember: "name", valueMember: "value", width: 100, height: 22,theme:currentTheme,autoDropDownHeight: true
      	    }); 
      	    
      	    // 初始化查询和刷新按钮
      		var dataContent = [
      		     {name:"CPU使用率",value:"cpu"},
      		     {name:"内存使用率",value:"memory"},
      		     {name:"虚拟机磁盘占用率",value:"vd"},
      		     {name:"网络流入流速",value:"networkIn"},
      		     {name:"网络流出流速",value:"networkOut"}
      		];
      	     // Create a jqxDropDownList
      	    var source = {
      	           datatype: "json",
      	           datafields: [
      	               { name: 'name' },
      	               { name: 'value' }
      	           ],
      	           localData:dataContent
      	    };
      	    var dataAdapter = new $.jqx.dataAdapter(source);
      	    
      	    $("#searchMonitorContent").jqxDropDownList({
      	        selectedIndex: 0, source: dataAdapter, displayMember: "name", valueMember: "value", width: 150, height: 22,theme:currentTheme,autoDropDownHeight: true
      	    });
      	    
      	    
      	 	$('#searchMonitorContent').on('select',function (event){
      	  
   	     	 var args = event.args;
   	         if (args) {
   	 	        var item = args.item;
   	 	        var value = item.value;
   	 	        
   	 	        if("cpu" == value){
   	 	        	$("#monitorContent .customPanel").css("display","none");
   	 	        	$("#monitorContent .customPanel").eq(0).css("display","block");
   	 	        }else if("memory" == value){
   	 	        	$("#monitorContent .customPanel").css("display","none");
   	 	        	$("#monitorContent .customPanel").eq(1).css("display","block");
   	 	        }else if("vd" == value){
   	 	        	$("#monitorContent .customPanel").css("display","none");
   	 	        	$("#monitorContent .customPanel").eq(2).css("display","block");
   	 	        }else if("networkIn" == value){
   	 	        	$("#monitorContent .customPanel").css("display","none");
   	 	        	$("#monitorContent .customPanel").eq(3).css("display","block");
   	 	        }else if("networkOut" == value){
   	 	        	$("#monitorContent .customPanel").css("display","none");
   	 	        	$("#monitorContent .customPanel").eq(4).css("display","block");
   	 	        }
                 }
     	     });
      	     
      		$("#searchMonitorBtn").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
    	}
    	
    });
	
</script>

</html>