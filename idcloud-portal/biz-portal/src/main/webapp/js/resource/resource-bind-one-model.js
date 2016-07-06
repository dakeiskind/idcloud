/**
 * 该view和js绑定的功能：主机配置、块存储配置管理、Ip配置管理
 */
var resourceBindOneModel = function(host,storage,ip){
	
	var me = this;
	
 /************************主机配置管理*************************/
	// 弹出新增主机window
	this.addHostInfo = function(){
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#addHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-338)/2 } });
    	$("#addHostWindow").jqxWindow('open');
	};
	
	// 提交新增主机信息
	this.addHostSubmit = function(){
		$('#addHostForm').jqxValidator('validate');
	};
	
	// 弹出编辑主机window
	this.editHostInfo = function(){
		var rowindex = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
    		// 给编辑画面赋值
    		$("#resSidHost").val(data.resSid);
    		host.setEdithostForm(data);
    		// 根据监控状态显示编辑画面
    		if(data.monitorStatusName == "未监控"){
    			$("#monitorText").html("未加入监控");
    			$("#getMonitorNode").jqxButton({ width: '80',theme:currentTheme,height: '25px',disabled: false});
    		}else{
    			$("#monitorText").html("已加入监控");
    			$("#getMonitorNode").jqxButton({ width: '80',theme:currentTheme,height: '25px',disabled: true});
    		}
    		
    		// 设置弹出框位置
    		var windowW = $(window).width(); 
        	var windowH = $(window).height(); 
        	$("#editHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-428)/2 } });
        	$("#editHostWindow").jqxWindow('open');
    	}
	};
	
	// 提交编辑主机信息
	this.editHostSubmit = function(){
		$('#editHostForm').jqxValidator('validate');
	};
	
	// 删除主机信息
	this.removeHostInfo = function(){
		var rowindex = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
		    	Core.confirm({
					title:"提示",
					message:"确定要删除该主机吗?",
					yes:"确定",
					confirmCallback:function(){
						Core.AjaxRequest({
			 				url : ws_url + "/rest/hosts/delete/"+data.resSid+"",
			 				type:"get",
			 				callback : function (data) {
			 					host.searchHostConfigInfo();
			 			    },
			 			    failure:function(data){
			 			    	
			 			    }
			 			});
					}
			});
    	}
	};
	
	// 弹出详情window
	this.detailHostInfo = function(){
		var rowindex = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
    		host.setdetailhostForm(data);
    		// 设置弹出框位置
    		var windowW = $(window).width(); 
        	var windowH = $(window).height(); 
        	$("#detailHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-338)/2 } });
        	$("#detailHostWindow").jqxWindow('open');
    	}
	};
	
	// 弹出监控信息window
	this.monitorHostInfo = function(){
		var rowindex = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
    		// 设置弹出框位置
    		var windowW = $(window).width(); 
        	var windowH = $(window).height(); 
        	$("#monitorHostWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-400)/2 } });
        	$("#monitorHostWindow").jqxWindow('open');
    	}
	};
	
	// 弹出已挂载存储window
	this.mountStorageHostInfo = function(){
		var rowindex = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
    		// 给已挂载datagrid赋值
		    Core.AjaxRequest({
	 			url : ws_url + "/rest/hosts/getMountStorage/"+data.resSid+"",
	 			type:'get',
	 			callback : function (data) {
	 				var sourcedatagrid ={
			              datatype: "json",
			              localdata: data
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#mountedStorageDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
    		
    		// 设置弹出框位置
    		var windowW = $(window).width(); 
        	var windowH = $(window).height(); 
        	$("#mountStorageHostWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-326)/2 } });
        	$("#mountStorageHostWindow").jqxWindow('open');
    	}
	};
	
	// 移除已挂载的存储
	this.removeHostStorage = function(){
		// 得到datagrid的选择项
		var rowArr = $('#mountedStorageDatagrid').jqxGrid('getselectedrowindexes');
		var isUsed = true;
		var storageSids ="";
		if(rowArr.length > 0){
			// 判断是否可以移除
			for(var i=0;i<rowArr.length;i++){
				// 查询出该行数据
				var data = $('#mountedStorageDatagrid').jqxGrid('getrowdata', rowArr[i]);
				if(i == rowArr.length-1){
					storageSids+= data.resSid;
				}else{
					storageSids+= data.resSid+",";
				}
				
				if(data.usageStatus == "01"){ 
					isUsed = false;
					// 清除datagrid的选择项
					$('#mountedStorageDatagrid').jqxGrid('clearselection');
					break;
				}
			}
			// 判断是否有已使用的存储
			if(isUsed){
				// 移除所选择的存储
				var rowindex = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
		    	var hostData = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
				Core.confirm({
					title:"提示",
					message:"确定要卸载该存储吗?",
					yes:"确定",
					confirmCallback:function(){
						Core.AjaxRequest({
			 				url : ws_url + "/rest/hosts/uninstallMountedStorage/"+hostData.resSid+"/"+storageSids+"",
			 				type:"get",
			 				callback : function (data) {
			 					for(var i=0;i<rowArr.length;i++){
			 						$("#mountedStorageDatagrid").jqxGrid('deleterow', rowArr[i]);
			 					}
			 				    // 清除datagrid的选择项
								$('#mountedStorageDatagrid').jqxGrid('clearselection');
			 			    },
			 			    failure:function(data){
			 			    	
			 			    }
			 			});
					}
			    });
			}else{
				Core.alert({
					message:"该存储已使用，无法卸载！",
					type:"info"
				});
			}
		}else{
			// 没有选择存储
		}
	};
	
	// 弹出可挂载存储window
    this.addHostStorage = function (){
    	var rowindex = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
   		 
   		    // 给已挂载datagrid赋值
   		    Core.AjaxRequest({
   	 			url : ws_url + "/rest/hosts/getCanMountStorage/"+data.resSid+"",
   	 			type:'get',
   	 			callback : function (data) {
   	 				var sourcedatagrid ={
   				              datatype: "json",
   				              localdata: data
   	 			    };
   	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
   	 			    $("#addHostStorageDatagrid").jqxGrid({source: dataAdapter});
   	 			}
   	 		 });
   		    
   		// 设置弹出框位置
    		var windowW = $(window).width(); 
        	var windowH = $(window).height(); 
        	$("#addHostStorageWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-250)/2 } });
        	$("#addHostStorageWindow").jqxWindow('open');
    	}
    	 
	};	
	
	// 添加挂载存储
	this.addHostStorageSubmit = function(){
    	var storageSids ="";
		var rowindex = $('#addHostStorageDatagrid').jqxGrid('getselectedrowindexes');
		
		var index = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	var hostData = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', index);
		
    	if(rowindex.length >= 0){
    		for(var i=0;i<rowindex.length;i++){
    			var data = $('#addHostStorageDatagrid').jqxGrid('getrowdata', rowindex[i]);
    			if(i == rowindex.length-1){
					storageSids+= data.resSid;
				}else{
					storageSids+= data.resSid+",";
				}
    		}
    		
    		// 提交添加存储
    		Core.AjaxRequest({
 				url : ws_url + "/rest/hosts/addHostMountedStorage/"+hostData.resSid+"/"+storageSids+"",
 				type:"get",
 				callback : function (data) {
 					for(var i=0;i<rowindex.length;i++){
 						$("#addHostStorageDatagrid").jqxGrid('deleterow', rowindex[i]);
 					}
 					
 					// 清除datagrid的选择项
					$('#addHostStorageDatagrid').jqxGrid('clearselection');
 					
 					// 刷新已挂载datagrid
 					 Core.AjaxRequest({
 			 			url : ws_url + "/rest/hosts/getMountStorage/"+hostData.resSid+"",
 			 			type:'get',
 			 			callback : function (data) {
 			 				var sourcedatagrid ={
 					              datatype: "json",
 					              localdata: data
 			 			    };
 			 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			 			    $("#mountedStorageDatagrid").jqxGrid({source: dataAdapter});
 			 			}
 			 		 });
 					
 			    },
 			    failure:function(data){
 			    	
 			    }
 			});
    	}
	};
	
	// 获取监控节点
	this.getMonitorNode = function(){
		var rowindex = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
		var ok =  $("#getMonitorNode").jqxButton("disabled");
    	if(rowindex >= 0 && !ok){
    		var data = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
    		
    		// 获取监控节点
    		Core.AjaxRequest({
 				url : ws_url + "/rest/hosts/getMonitorNode",
 				params :{
 					resSid:data.resSid,
 					hostName:data.hostName,
 					hostIp:data.hostIp			
 				},
 				callback : function (data) {
 					$("#monitorText").html("已加入监控");
 			    },
 			    failure:function(data){
					
 			    }
 			});
    	}
	};
	
	// 弹出主机发现部署window
	this.hostDiscoveryDeploymentInfo = function(){
	    // 查询出已发现主机列表的数据
	    Core.AjaxRequest({
 			url : ws_url + "/rest/hosts/getDiscoveryHost",
 			type:'post',
 			callback : function (data) {
 				var sourcedatagrid ={
			              datatype: "json",
			              localdata: data
 			    };
 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#hostDiscoveryDeploymentDatagrid").jqxGrid({source: dataAdapter});
 			}
 		 });
		    
		// 设置弹出框位置
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#hostDiscoveryDeploymentWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-280)/2 } });
    	$("#hostDiscoveryDeploymentWindow").jqxWindow('open');
	};
	
	// 刷新发现主机列表
	this.refreshDiscoveryHost = function(){
		 Core.AjaxRequest({
	 			url : ws_url + "/rest/hosts/getDiscoveryHost",
	 			type:'post',
	 			callback : function (data) {
	 				var sourcedatagrid ={
				              datatype: "json",
				              localdata: data
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#hostDiscoveryDeploymentDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	};
	// 发现主机详情
	this.discoveryHostDetailInfo = function(){
		// 先查询出某个主机详细信息
		var index = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getselectedrowindex');
		if(index >=0){
			var data = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getrowdata', index);
			// 给window中的主机属性赋值
			$("#hostName").html(data.hostName);
			$("#macAddress").html(data.macAddress);
			$("#nodeHostName").html(data.nodeName);
			$("#osname").html(data.osname);
			$("#ipaddress").html(data.ipaddress);
			$("#manufacturer").html(data.manufacturer);
			$("#serialnumber").html(data.serialnumber);
			$("#productname").html(data.productname);
			$("#hardwaremodel").html(data.hardwaremodel);
			$("#status").html(data.status);
			$("#physicalprocessorcount").html(data.physicalprocessorcount);
			$("#uuid").html(data.uuid);
			$("#processorcount").html(data.processorcount);
			$("#interfaces").html(data.interfaces);
			// cpu核数详情
			var cpu = data.processorcountArray.processor0;
			var cpup = "";
			for(var i=0;i<data.processorcount;i++){
				cpup +="<p style='margin:0px;padding:0px;'>processor"+i+":"+cpu+"</p>";
			}
			$("#cpuDetail").html(cpup);
			// 网卡详情
			var nic = JSON.stringify(data.interfaceArray);
			var nicArr = nic.substring(1,nic.length-1).split(",");
			var nicDetail ="";
			for(var i=0;i<nicArr.length;i++){
				nicDetail +="<p style='margin:0px;padding:0px;'>"+nicArr[i].replace(/\"/g,"")+"</p>";
			}
			$("#interfaceDetail").html(nicDetail);

			// 设置window显示的位置
			var windowW = $(window).width(); 
	    	var windowH = $(window).height(); 
	    	$("#discoveryDeploymentDetailWindow").jqxWindow({ position: { x: (windowW-750)/2, y: (windowH-300)/2 } });
	    	$("#discoveryDeploymentDetailWindow").jqxWindow('open');
		}
	};
	
	// 弹出部署系统
	this.deploymentSystemInfo = function(){
		var index = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getselectedrowindex');
		if(index >=0){
			var data = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getrowdata', index);
			var windowW = $(window).width(); 
	    	var windowH = $(window).height(); 
	    	$("#DeploymentSystemWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-100)/2 } });
	    	$("#DeploymentSystemWindow").jqxWindow('open');
		}
		
	};
	
	// 提交主机要部署的系统
	this.deploymentSystemSubmit = function(){
		var index = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getselectedrowindex');
		if(index >=0){
			var data = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getrowdata', index);
			
			var macAddress = data.macAddress;
			var hostOsType = $("#hostOsType").val();
			// 提交部署系统
			Core.AjaxRequest({
	 			url : ws_url + "/rest/hosts/deploymentSystem",
	 			type:'post',
	 			params:{
	 				macAddress : macAddress,
	 				type : hostOsType
	 			},
	 			callback : function (data) {
	 				$("#DeploymentSystemWindow").jqxWindow('close');
	 			}
	 		 });
		}
	};
	
	// 删除发现主机部署
	this.removeDiscoveryHost = function(){
		var index = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getselectedrowindex');
		if(index >=0){
			var data = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getrowdata', index);
			Core.confirm({
				title:"提示",
				message:"确定要删除该主机吗?",
				yes:"确定",
				confirmCallback:function(){
					Core.AjaxRequest({
			 			url : ws_url + "/rest/hosts/deleteDeploymentHost/"+data.nodeName+"",
			 			type:'get',
			 			callback : function (data) {
			 				me.refreshDiscoveryHost();
			 			}
			 		 });
				}
		    });
		}
	};
	
	// 将主机加入平台
	this.addToPlatformInfo = function(){
		var rowindex = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getrowdata', rowindex);
    		
    		host.setplatformhostForm(data);
    		
    		var windowW = $(window).width(); 
        	var windowH = $(window).height(); 
        	$("#addToPlatformWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-338)/2 } });
        	$("#addToPlatformWindow").jqxWindow('open');
    		
    	}
	};
	
	// 提交将主机加入到平台
	this.platformHostSubmit = function(){
		$('#addToPlatformForm').jqxValidator('validate');
	};
	
	// 条件查询主机配置管理  
	this.searchHostConfigMgt = function(){
		host.searchObj.hostName = $("#search-host-name").val();
		host.searchObj.manageStatus = $("#search-host-mgt-status").val()=="全部"?"":$("#search-host-mgt-status").val();
		host.searchObj.usageStatus = $("#search-host-usage-status").val()=="全部"?"":$("#search-host-usage-status").val();
		host.searchHostConfigInfo();
	};
	
 /************************块存储配置管理************************/
	// 弹出新增块存储window
	this.addStorageInfo = function(){
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#addStorageWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-200)/2 } });
    	$("#addStorageWindow").jqxWindow('open');
	};
	
	// 提交新增块存储信息
	this.addStorageSubmit = function(){
		$('#addStorageForm').jqxValidator('validate');
	};
	
	// 弹出新增块存储window
	this.editStorageInfo = function(){
		var rowindex = $('#storageConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#storageConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
    		// 为编辑赋值
    		$("#resSidStorage").val(data.resSid);
    		storage.setEditStorageForm(data);
    		// 设置window的位置
    		var windowW = $(window).width(); 
        	var windowH = $(window).height(); 
        	$("#editStorageWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-200)/2 } });
        	$("#editStorageWindow").jqxWindow('open');
    	}
	};
	
	// 提交编辑块存储信息
	this.editStorageSubmit = function(){
		$('#editStorageForm').jqxValidator('validate');
	};
	
	// 删除块存储信息
	this.removeStorageInfo = function(){
		var rowindex = $('#storageConfigMgtdatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#storageConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
		    	Core.confirm({
					title:"提示",
					message:"确定要删除该块存储吗?",
					yes:"确定",
					confirmCallback:function(){
						Core.AjaxRequest({
			 				url : ws_url + "/rest/storages/delete/"+data.resSid+"",
			 				type:"get",
			 				callback : function (data) {
			 					storage.searchStorageConfigInfo();
			 			    },
			 			    failure:function(data){
			 			    	
			 			    }
			 			});
					}
			});
    	}
	};
	
	// 条件查询块存储配置管理
	this.searchStorageConfigMgt = function(){
		storage.searchObj.volumeName = $("#search-storage-name").val();
		storage.searchObj.volumeType = $("#search-storage-type").val()=="全部"?"":$("#search-storage-type").val();
		storage.searchObj.manageStatus = $("#search-storage-mgt-status").val()=="全部"?"":$("#search-storage-mgt-status").val();
		storage.searchObj.usageStatus = $("#search-storage-usage-status").val()=="全部"?"":$("#search-storage-usage-status").val();
		storage.searchStorageConfigInfo();
	};
	
	 /*************************Ip配置管理***************************/
		// 条件查询IP配置管理
		this.searchIpConfigMgt = function(){
			ip.searchObj.ipAddress = $("#search-IP-address").val();
			ip.searchObj.ipCategory = $("#search-IP-category").val()=="全部"?"":$("#search-IP-category").val();
			ip.searchObj.ipType = $("#search-IP-type").val()=="全部"?"":$("#search-IP-type").val();
			ip.searchObj.manageStatus = $("#search-IP-mgt-status").val()=="全部"?"":$("#search-IP-mgt-status").val();
			ip.searchObj.usageStatus = $("#search-IP-usage-status").val()=="全部"?"":$("#search-IP-usage-status").val();
			ip.searchIpConfigInfo();
		};
};
  
  
  