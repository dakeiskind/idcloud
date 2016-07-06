<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<!DOCTYPE html>
<% 
	String hostSid = request.getParameter("resHostSid");
	String monitorNodeId = request.getParameter("monitorNodeId");
%>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/host/host-detail-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/physical/res-server-info-model.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/highcharts/highcharts.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/highcharts/highcharts-more.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/highcharts/modules/exporting.js"></script>
		<style type="text/css">
			#monitorContent .customPanel{
				display:none;
				overflow:hidden;
			}
			.showContent{
				display:block;
			}
			
		</style>
</head>
<body>
 <div style="position:absolute;top:0px;left:0px;width:32%;height:99.5%">
  			<div class="customPanel" style="width:98%; height: 240px;margin-left:5px;margin-top:7px;">
		   		<div class="title">&nbsp;&nbsp;基本信息</div>
				<div>
					<table border="0" width="100%" cellspacing="2" cellpadding="5">
						<tr>
	        			<td align="right" width="100px">名称:</td>
	        			<td align="left">
	        				<span id="hostName"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right">CPU(核):</td>
	        			<td align="left">
	        				<span id="totalCpu"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right">内存(GB):</td>
	        			<td align="left">
	        				<span id="memory"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right">主机操作系统:</td>
	        			<td align="left">
	        				<span id="osType"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right">主机型号:</td>
	        			<td align="left">
	        				<span id="hostType"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right">主机IP:</td>
	        			<td align="left">
	        				<span id="ipAddr"></span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td align="right">状态:</td>
	        			<td align="left">
	        				<span id="status"></span>
	        			</td>
	        		</tr>
					</table>
				</div>
	   </div>
	   
	   <div class="customPanel" style="width:98%; height: 140px;margin-left:5px;margin-top:7px;">
		   		<div class="title">&nbsp;&nbsp;维保信息
		   			<a style="cursor:pointer;position:absolute;right:5px;color:#0099d7 " onclick="popSearchHostInfo()">设备详情</a>
		   		</div>
				<div>
					<table border="0" width="100%" cellspacing="2" cellpadding="5">
						<tr>
		        			<td align="right" width="60px">维保厂商:</td>
		        			<td align="left">
		        				<span id="maintCompany"></span>
		        			</td>
		        			<td align="right" width="60">维保电话:</td>
		        			<td align="left">
		        				<span id="maintTel"></span>
		        			</td>
		        		</tr>
		        		<tr>
		        			<td align="right">购置日期:</td>
		        			<td align="left">
		        				<span id="purchaseDate"></span>
		        			</td>
		        			<td align="right">保修期限:</td>
		        			<td align="left">
		        				<span id="warrantyPeriod"></span>
		        			</td>
		        		</tr>
		        		<tr>
		        			<td align="right">设备提供商:</td>
		        			<td align="left">
		        				<span id="equipSupplier"></span>
		        			</td>
		        			<td align="right">保修起始日期:</td>
		        			<td align="left">
		        				<span id="startEndDate"></span>
		        			</td>
		        		</tr>
					</table>
				</div>
	   </div>
	   
	   <div class="customPanel" style="width:98%; height:130px;margin-left:5px;margin-top:7px;">
			<div class="title">&nbsp;&nbsp;用户管理
				<font style="cursor:pointer;position:absolute;right:45px" onclick="popAddHostUserWindow()"><i class="icons-blue icon-plus-1"></i></font>
  		    	<font style="cursor:pointer;position:absolute;right:25px" onclick="popEditHostUserWindow()"><i class="icons-blue icon-pencil"></i></font>
  		    	<font style="cursor:pointer;position:absolute;right:5px" onclick="removeHostUserInfo()"><i class="icons-blue icon-trash"></i></font>
			</div>
			<div>
				<div style="width:100%; height: 100px; margin-top: 5px;overflow-y:auto;">
					<div id='hostUserDatagrid' style="width: 98%;margin-left: 1%"></div>
				</div>
			</div>
	   </div>
	   
	   <div class="customPanel" style="width:98%;height: 130px;margin-left:5px;margin-top:7px;">
			<div class="title">&nbsp;&nbsp;存储信息</div>
			<div>
				<div style="width:100%; height: 100px; margin-top: 5px;overflow-y:auto;">
					<div id='hostStorageDatagrid' style="width: 98%;margin-left: 1%"></div>
				</div>
			</div>
	   </div>
	   
  </div>
  
  
   <!-- 监控div-->
   <div id="monitorContainer" style="position:absolute;top:0px;left:32.5%;width:67.2%;height:99.5%;">
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
				   			 	 	     <input id="searchMonitorBtn" type="button" onclick="setHostMonitorPerid()" value="查询" />
				   			 	 	</td>
				   			 	 	<td align="right">切换至:</td> 
				   			 	 	<td align="right" width="105px"><div id="searchMonitorContent"></div></td>
				   			 	 </tr>
				   			 </table>
				   		</div>
				   		
						<div id="monitorContent" style="width:98%;height:99%;margin-left:1%;margin-top:7px;">   		
					   		<div  class="customPanel" style="display:block; width:100%;">
						  		    <div class="title">&nbsp;&nbsp;CPU使用率</div>
							        <div id="cpuHostContainer">
							        	
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
							        	<div id='memoryHostUsedchartContainer' style="width:100%; height:275px;border:0px;"></div>
							        </div>
					  		</div>
					  		<div id="customPanelDisk" class="customPanel" style="width:100%;margin-top:7px">
						  		    <div class="title">&nbsp;&nbsp;虚拟机磁盘占用率</div>
							        <div id="diskHostContainer">
							        	
							        </div>
					  		</div>
				  	</div>
		     </div>
            </div>
         </div> 
 </div>
 
  <!-- 新增管理账户 -->
 <div id="addHostUserWindow">
       <div>新增管理账户</div>
       <div id="addHostUserForm" style="overflow: hidden;">
           <input type="hidden" data-name="resSid" id="add-host-user-resVmSid"/>
           <input type="hidden" data-name="resType" id="add-type-host-user"/>
           <table border="0" width="100%" cellspacing="2" cellpadding="0">
               <tr>
                   <td align="right" height="30px;"><font style="color:red">*</font>账户名称:</td>
                   <td align="left" >
                       <input type="text" data-name="userName" id="add-host-user-name" />
                   </td>
                    <td align="right" height="30px;"><font style="color:red">*</font>密码:</td>
                   <td align="left" >
                       <input type="password" data-name="password" id="add-host-user-password" />
                   </td>
               </tr>
                <tr>
                   <td align="right" height="30px;">用户组:</td>
                   <td align="left" >
                       <input type="text" data-name="userGroup" id="add-host-user-userGroup" />
                   </td>
                    <td align="right" height="30px;">权限:</td>
                   <td align="left" >
                       <input type="text" data-name="privilege" id="add-host-user-privilege" />
                   </td>
               </tr>
                <tr>
                   <td align="right" height="30px;">描述:</td>
                   <td align="left" colspan="3">
                       <textarea data-name="description" id="add-host-user-description" ></textarea>
                   </td>
               </tr>
               <tr>
               	<td colspan="4" height="30px;" align="right" >
               		<input style="margin-right: 5px;" onclick="submitHostUserInfo()" id="addHostUserSave" type="button" value="保存" />
               		<input id="addHostUserCancel" type="button" value="取消" />
               	</td>
               </tr>
           </table>
       </div>
  </div>
  
  <!-- 编辑管理账户 -->
 <div id="editHostUserWindow">
       <div>编辑管理账户</div>
       <div id="editHostUserForm" style="overflow: hidden;">
           <input type="hidden" data-name="osUserSid" id="edit-host-user-sid"/>
           <table border="0" width="100%" cellspacing="2" cellpadding="0">
               <tr>
                   <td align="right" height="30px;"><font style="color:red">*</font>账户名称:</td>
                   <td align="left" >
                       <input type="text" data-name="userName" id="edit-host-user-name" />
                   </td>
                   <td align="right" height="30px;"><font style="color:red">*</font>密码:</td>
                   <td align="left" >
                       <input type="password" data-name="password" id="edit-host-user-password" />
                   </td>
               </tr>
               <tr>
                   <td align="right" height="30px;">用户组:</td>
                   <td align="left" >
                       <input type="text" data-name="userGroup" id="edit-host-user-userGroup" />
                   </td>
                    <td align="right" height="30px;">权限:</td>
                   <td align="left" >
                       <input type="text" data-name="privilege" id="edit-host-user-privilege" />
                   </td>
               </tr>
                <tr>
                   <td align="right" height="30px;">描述:</td>
                   <td align="left" colspan="3">
                       <textarea data-name="description" id="edit-host-user-description" ></textarea>
                   </td>
               </tr>
               <tr>
               	<td colspan="4" height="30px;" align="right" >
               		<input style="margin-right: 5px;" onclick="submitEditHostUserInfo()" id="editHostUserSave" type="button" value="保存" />
               		<input id="editHostUserCancel" type="button" value="取消" />
               	</td>
               </tr>
           </table>
       </div>
  </div>
 <%@ include file="/pages/resources/physical/ce/server/res-ce-server-info.jsp"%>
</body>  
<script type="text/javascript">
    
	var resHostSid = '<%=hostSid %>';
	var monitorNodeId = '<%=monitorNodeId %>';
	
	// 设置基本信息
	getHostBasicInfo(resHostSid);
	
	
	// 初始化查询下来框和查询按钮
	var dataPerid = [
		     {name:"天",value:"Day"},
		     {name:"周",value:"Week"},
		     {name:"月",value:"Month"}
		];
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
	
	// 查询按钮
	$("#searchMonitorBtn").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	
		// 监控类型
	var dataContent = [
	     {name:"CPU使用率",value:"cpu"},
	     {name:"内存使用率",value:"memory"},
	     {name:"文件系统使用率",value:"vd"}
	];
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
	
	var info = new hostInfoModel(resHostSid);
	
	info.initPopWindow();
	info.initValidator();
	
	info.initHostUserDatagrid();
	info.searchHostUserInfo();
	
	info.initHostStorageDatagrid();
	info.searchHostStorageInfo1();
	
	// 监控信息
    if(monitorNodeId == null || monitorNodeId == "" || monitorNodeId == "null"){
    }else{
    	info.initMonitorInfo();
    }
	
	//给概要基本信息赋值
	function getHostBasicInfo(resHostSid){
		 Core.AjaxRequest({
	 			url : ws_url + "/rest/host/"+resHostSid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				console.log(JSON.stringify(data));
	 				// 基本信息
	 				if(data.statusName=="正常"||data.statusName=="维护") {
	 					$('#host_stop').html("<span style='cursor:pointer;color:#3B9AFF' '><i class='icon-power'></i><span onclick='hostOperation(this.innerHTML)'>关机</span></span>");
	 					$('#host_reboot').html("<span style='cursor:pointer;color:#3B9AFF'><i class='icon-spin3'></i><span onclick='hostOperation(this.innerHTML)'>重启</span></span>");
	 					if(data.statusName == "维护") {
	 						$('#host_mainmodel').html("<span style='cursor:pointer;color:#3B9AFF'><i class='icon-wrench-1'></i><span onclick='hostOperation(this.innerHTML)'>退出维护模式</span></span>");
	 					} else {
	 					$('#host_mainmodel').html("<span style='cursor:pointer;color:#3B9AFF'><i class='icon-wrench-1'></i><span onclick='popSetEnterModel()'>进入维护模式</span></span>");
	 					}
	 				}
	 				$("#hostName").html(data.hostName);
	 			 	$("#status").html(data.statusName);
	 			 	$("#ipAddr").html(data.hostIp);
	 			 	$("#totalCpu").html(data.cpuCores==null?data.cpuNumber:data.cpuCores);
	 			 	$("#memory").html((data.memorySize/1024).toFixed(2));
	 			 	$("#osType").html(data.hostOsTypeName);
	 			 	$("#hostType").html(data.model);
	 			 	
	 			 	// 维保信息
	 			 	$("#maintCompany").html(data.maintCompany);
	 			 	$("#maintTel").html(data.maintTel);
	 			 	$("#purchaseDate").html(data.purchaseDate);
	 			 	$("#warrantyPeriod").html(data.warrantyPeriod);
	 			 	$("#equipSupplier").html(data.equipSupplier);
	 			 	$("#startEndDate").html(data.startEndDate);
	 			 
	 			}
	     });
	 }
     // 
	 $('#searchMonitorContent').on('select',function (event){
    	var args = event.args;
        if (args) {
	        var item = args.item;
	        var value = item.value;
	        
	        var hostInfo = new hostInfoModel();
	        var timeType = $("#searchMonitorInfo").val();
	        
	        if("cpu" == value){
	        	$("#monitorContent .customPanel").css("display","none");
	        	$("#monitorContent .customPanel").eq(0).css("display","block");
	        	
	        	var peroid = getThePeridTime(1);
	        	hostInfo.initHostCpuUsedRate(monitorNodeId,peroid,timeType);
	        	
	        }else if("memory" == value){
	        	$("#monitorContent .customPanel").css("display","none");
	        	$("#monitorContent .customPanel").eq(1).css("display","block");
	        	
	        	var peroid = getThePeridTime(1);
	        	hostInfo.initHostMemoryUsedRate(monitorNodeId,peroid,timeType);
	        }else if("vd" == value){
	        	$("#monitorContent .customPanel").css("display","none");
	        	$("#monitorContent .customPanel").eq(2).css("display","block");
	        	
	        	var peroid = getThePeridTime(1);
	        	hostInfo.initHostDiskHistoryUsedRate(monitorNodeId,peroid,timeType);
	        }
        }
 	});


</script>
</html>

