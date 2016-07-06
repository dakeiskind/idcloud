var virtualHostDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    	 "qm.hostNameLike":null,
	    	 "qm.hostOsType":null,
	    	 "qm.status":null,
			 "qm.resTopologySid":resTopologySid,
			 "qm.resTopologyType" : resTopologyType,
	    		
		};
	    this.staObj = {
    		 hostNameLike:"",
	    	 hostOsType:"",
	    	 status:"",
			 resTopologySid:resTopologySid
	    };

	    // 获取资源池下主机的信息
	    this.getHostByResPoolSid = function(resPoolSid){
	    	 var hostData;
	    	 Core.AjaxRequest({
		 			url : ws_url + "/rest/hosts/getHostByResPoolSid/"+resPoolSid,
		 			type:'get',
		 			async:false,
		 			callback : function (data) {
		 				hostData = data;
		 			}
		     });
	    	 return hostData;
	    }
	    
	    // 统计主机资源查询方法
	    this.getDataGridData = function(){
	    	 var hostData;
	    	 Core.AjaxRequest({
		 			url : ws_url + "/rest/hosts/statistical/topology",
		 			type:'post',
		 			params:me.staObj,
		 			async:false,
		 			callback : function (data) {
		 				hostData = data;
		 			}
		     });
	    	 return hostData;
	    };
	    
	    this.HostResourcesStatistics = function(){
	    	var hostData =  me.getDataGridData();
			
			var data = new Object();
			data.hostCount = hostData.staTotalHost;
			data.attr = new Array();
			var value = [hostData.staNormalHost,hostData.staOffineHost,hostData.staMaintainHost,hostData.staFaultHost];
			var name =["正常","离线","维护","故障"];
			for(var i=0; i<4;i++){
				var obj = new Object();
				var val = value[i] == null ? 0:value[i];
				obj.name = name[i]+"("+val+")";
				obj.value = val;
				data.attr.push(obj);
			}
			return data;
	    };
	    
	    this.HostResourcesStatisticsInStoragePool = function(resPoolSid){
	    	var hostData = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/hosts/statistical/topology/"+resPoolSid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				hostData = data;
	 			}
		    });
			var data = new Object();
			data.hostCount = hostData.staTotalHost;
			data.attr = new Array();
			var value = [hostData.staNormalHost,hostData.staOffineHost,hostData.staMaintainHost,hostData.staFaultHost];
			var name =["正常","离线","维护","故障"];
			for(var i=0; i<4;i++){
				var obj = new Object();
				var val = value[i] == null ? 0:value[i];
				obj.name = name[i]+"("+val+")";
				obj.value = val;
				data.attr.push(obj);
			}
			return data;
	    };
	    
	    // 主机资源统计
	    this.HostResStatisticsInPool = function(resPoolSid){
	    	
	    	 var hostData = null;
	    	 Core.AjaxRequest({
		 			url : ws_url + "/rest/hosts/statistical/pool/status/"+resPoolSid,
		 			type:'get',
		 			async:false,
		 			callback : function (data) {
		 				hostData = data;
		 			}
		     });
			
			var data = new Object();
			data.hostCount = hostData.staTotalHost;
			data.attr = new Array();
			
			var value = [hostData.staNormalHost,hostData.staOffineHost,hostData.staMaintainHost,hostData.staFaultHost];
			var name =["正常","离线","维护","故障"];
			
			for(var i=0; i<4;i++){
				var obj = new Object();
				var val = value[i] == null ? 0:value[i];
				obj.name = name[i]+"("+val+")";
				obj.value = val;
				data.attr.push(obj);
			}
			// 获得pc下主机的cpu总数、memory总数
			data.all = new Object();
			data.all.cpuTotalCounts = hostData.cpuCores;
			data.all.memoryTotalSize = hostData.memorySize;
			return data;
	    };
	    
	    // 用户数据
	    this.searchHostInfo = function(){
	    	var dataAdapter = Core.getPagingDataAdapter({
				gridId: "hostdatagrid",
				url: ws_url + "/rest/hosts/findAll",
				params: me.searchObj
			});
	    	
	    	$("#hostdatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initHostInput = function(){
	    	// 初始化查询组件
	        $("#search-host-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-host-os-type").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        // 初始化下拉列表框 
			 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			 codesearch.getCommonCode("search-host-status","HOST_STATUS",true);
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initHostDatagrid = function(){
	    	  
	    	  var dataAdapter = Core.getPagingDataAdapter({
					gridId: "hostdatagrid",
					url: ws_url + "/rest/hosts/findAll",
					params: me.searchObj
			  });

	          $("#hostdatagrid").jqxGrid({
	              width: "100%",
				  theme:currentTheme,
				  source: dataAdapter,
	              columnsresize: true,
	              virtualmode: true,
				  rendergridrows: function(){
					  return dataAdapter.records;
				  },
	              pageable: true, 
	              pagesize: 10, 
	              rowsheight: 32,
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
					  { text: '主机名称', datafield: 'hostName', cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
						  return "<div style='padding-top:10px;'><a class='datagrid-link' onclick='goHostDetailPage("+row+")' href='#'>&nbsp;"+value+"</a></div>";
					  }},
	                  { text: '主机型号', datafield: 'model'},
	                  { text: '主机IP', datafield: 'hostIp',width:100},
	                  { text: '管理IP', datafield: 'managementIp',width:100},
	                  { text: 'CPU个数(个)', datafield: 'cpuNumber',cellsalign:'right',width:80},
	                  { text: 'CPU核数(核)', datafield: 'cpuCores',cellsalign:'right',width:80},
	                  { text: '内存(GB)', datafield: 'memorySizeGb',cellsalign:'right',width:60},
	                  { text: '虚拟机(个)', datafield: 'vmCount',cellsalign:'right',width:80},
	                  { text: '监控状态',datafield: 'monitorNodeId',width:60,cellsrenderer:function (row, datafield, value) {
	                	  var monitor= "";
	                	  if(value != null && value != "" && value != "null"){
	                		  monitor = "<i class='icon-circle' title='已监控' style='font-size:16px;color:#62CB62'></i>";
	                	  }else{
	                		  monitor = "<i class='icon-circle' title='未监控' style='font-size:16px;color:#BFBFBF'></i>";
	                	  }
	                      return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+monitor +"&nbsp";
	                  }},
	                  { text: '所属环境&集群', datafield: 'parentTopologyName',width:80},
//	                  { text: '操作系统', datafield: 'hostOsType',align: 'center',cellsalign:'center',width:150},
	                  { text: '操作系统', datafield: 'hostOsTypeName',width:180},
	                  { text: '主机状态', datafield: 'statusName',align: 'center',cellsalign:'center',width:60},
	                  { text: '操作', width:80,  datafield: '',align:'center',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
 			        	  return "<div class='customButton' style='margin-top:4px;margin-left:10px;' onclick='viewHostMonitorInfo("+row+")'>详情</div>";
 			             }
 			          }
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addHost = $("<div><a id='addHost' onclick='popAddX86HostWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editHost = $("<div><a id='editHost' onclick='popEditX86HostWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var removeHost = $("<div><a id='removeHost' onclick='removeX86Host()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
	                  var refreshBtn = $("<div><a id='jqxRefreshHostBtn' onclick='refreshHostGrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
	                  var exportBtn = $("<div><a id='jqxExportHostBtn' onclick='exportHostDatagrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-export'></i>导出&nbsp;&nbsp;</a></div>");
	                  var setMonitorBtn = $("<div><a id='jqxSetHostMonitorBtn' onclick='getHostMonitorNodeId()' style='margin-left:-2px'>&nbsp;&nbsp;<i class='icons-blue icon-level-down'></i>加入监控&nbsp;&nbsp;</a></div>");
	                  var deleteMonitorBtn = $("<div><a id='jqxDeleteHostMonitorBtn' onclick='deleteHostMonitorNodeId()' style='margin-left:-2px'>&nbsp;&nbsp;<i class='icons-blue icon-level-up'></i>退出监控&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  if("DC"==resTopologyType){
	                	  container.append(refreshBtn);
		                  container.append(exportBtn);
		                  $("#jqxRefreshHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
		       			  $("#jqxExportHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
	                  }else{
	                	  container.append(refreshBtn);
	                	  container.append(addHost);
		                  container.append(editHost);
		                  container.append(removeHost);
		                  container.append(setMonitorBtn);
		                  container.append(deleteMonitorBtn);
		                  container.append(exportBtn);
		                  $("#jqxRefreshHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
		       			  $("#jqxExportHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
		       			  $("#addHost").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
		       			  $("#editHost").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
		       			  $("#jqxSetHostMonitorBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
		       			  $("#jqxDeleteHostMonitorBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
		       			  $("#removeHost").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
	                  }
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#hostdatagrid").on('rowselect', function (event) {
	    		  
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#hostdatagrid').jqxGrid('getrowdata', index);
	    		  
    			  if(data.monitorNodeId != null && data.monitorNodeId != "" && data.monitorNodeId != "null"){
    				  $("#jqxSetHostMonitorBtn").jqxButton({ disabled: true });
	       			  $("#jqxDeleteHostMonitorBtn").jqxButton({ disabled: false });
    			  }else{
    				  $("#jqxSetHostMonitorBtn").jqxButton({ disabled: false });
	       			  $("#jqxDeleteHostMonitorBtn").jqxButton({ disabled: true });
    			  }
	    		  
	    		  $("#editHost").jqxButton({ disabled: false });
    			  $("#removeHost").jqxButton({ disabled: false });
	          });

   			
	          
	    };
	    
	  
  };

  // 查询主机
  function searchHost(){
	  
	  var host = new virtualHostDatagridModel();
	  host.searchObj["qm.hostNameLike"] = $("#search-host-name").val();
	  host.searchObj["qm.hostOsType"] = $("#search-host-os-type").val();
	  host.searchObj["qm.status"] = $("#search-host-status").val()=="全部"?"":$("#search-host-status").val(); 
	  host.searchHostInfo();
  }
  
  // 跳转到主机详情页面
  function goHostDetailPage(row){
	  // 通过Grid的index获得主机信息
	  var data = $('#hostdatagrid').jqxGrid('getrowdata', row);
	  
	  var items = $('#jqxTreeVirtual').jqxTree('getItems');
	  var index = 0;
	  for(var i=0;i<items.length;i++){
		  var attr = items[i].value.split(",");
		  if(data.resHostSid == attr[0]){
			 index = i;
			 break;
		  }
	  }
	  $('#jqxTreeVirtual').jqxTree('selectItem', $("#jqxTreeVirtual").find('li')[index]);
	  $('#jqxTreeVirtual').jqxTree('expandItem', $("#jqxTreeVirtual").find('li')[index]);
	
  }
  
  // 导出主机列表
  function exportHostDatagrid(){
	  var hostNameLike =  $("#search-host-name").val();
	  var hostOsType = $("#search-host-os-type").val();
	  var status = $("#search-host-status").val()=="全部"?"":$("#search-host-status").val();
	  var staObj = {
		 hostNameLike: hostNameLike,
    	 hostOsType: hostOsType,
    	 status: status,
		 resTopologySid:resTopologySid
	  };
	  
	  var params = JSON.stringify(staObj);
	  window.location = ws_url + "/rest/host/exportHost/" + params; 
	  
  }
  
  // 刷新主机列表
  function refreshHostGrid(){
	  var host = new virtualHostDatagridModel();
	  host.searchObj["qm.hostNameLike"] = $("#search-host-name").val();
	  host.searchObj["qm.hostOsType"] = $("#search-host-os-type").val();
	  host.searchObj["qm.status"] = $("#search-host-status").val()=="全部"?"":$("#search-host-status").val(); 
	  host.searchHostInfo();
  }
  
  // 弹出编辑window
  function popEditHostWindow(){
		var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
	  	hostconfig.getCommonCode("edit-status","HOST_STATUS");
	  	hostconfig.getCommonCode("edit-hostOsType","OS_VERSION");
	  	hostconfig.getCommonCode("edit-cpuType","CPU_TYPE");
	  	hostconfig.getCommonCode("edit-isViosFlag","IS_VIOS_FLAG");
	  	// 检查datagrid是否选中了
	  	var rowindex = $('#hostdatagrid').jqxGrid('getselectedrowindex');
	  	var ok =  $("#editHost").jqxButton("disabled");
	  	if(rowindex >= 0 && !ok){
	  		var data = $('#hostdatagrid').jqxGrid('getrowdata', rowindex);
	  		// 初始化值
	  		$("#edit-hostName").val(data.hostName);
	    	$("#edit-hostType").val(data.model);
	    	$("#edit-uuid").val(data.uuid);
	    	$("#edit-vendor").val(data.vendor);
	    	$("#edit-isViosFlag").val(data.isViosFlag);
	    	$("#edit-status").val(data.status);
	    	$("#edit-hostIp").val(data.hostIp);
	    	$("#edit-managementIp").val(data.managementIp); 
	    	$("#edit-memorySize").val(data.memorySize);
	    	$("#edit-cpuNumber").val(data.cpuNumber);
	    	$("#edit-cpuCores").val(data.cpuCores);
	    	$("#edit-cpuType").val(data.cpuType);
	    	$("#edit-cpuGhz").val(data.cpuGhz);
	    	$("#edit-nicNumber").val(data.nicNumber);
	    	$("#edit-hostOsType").val(data.hostOsType);
	    	$("#edit-resHostSid").val(data.resHostSid);
	    	if("VMware" == resTopologyType){
				$(".isViosFlag").hide();
			}else{
				$(".isViosFlag").show();
			}
	  		
	  		// 设置弹出框位置
	  		var windowW = $(window).width(); 
	      	var windowH = $(window).height(); 
	      	$("#editHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-428)/2 } });
	      	$("#editHostWindow").jqxWindow('open');
  	}
  }
  
  function removeX86Host(){
	  var rowindex = $('#hostdatagrid').jqxGrid('getselectedrowindex');
	  if(rowindex >= 0){
		  var data = $('#hostdatagrid').jqxGrid('getrowdata', rowindex);
		  Core.confirm({
			  title:"提示",
			  message:"确定要删除该主机吗?",
			  confirmCallback:function(){
				  Core.AjaxRequest({
			            url : ws_url + "/rest/phycomputes/delete/server/"+data.resHostSid,
			            type:"get",
			            callback : function (data) {
			               var x86Host = new virtualHostDatagridModel();
			               x86Host.searchHostInfo();
			            }
			      });
			  }
		  });
	  }
  }
  
  function popHostStorageWindow(){
	  Core.AjaxRequest({
			url : ws_url + "/rest/hosts/getMountStorage/"+resTopologySid,
			type:'get',
			callback : function (data) {
				var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
			    };
			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
			    $("#findHostStorageDatagrid").jqxGrid({source: dataAdapter});
			}
		 });
	  var windowW = $(window).width(); 
	  var windowH = $(window).height(); 
	  $("#findHostStorageWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-300)/2 } });
	  $("#findHostStorageWindow").jqxWindow('open');
}
  
  // 查看主机监控信息
  function viewHostMonitorInfo(row){
	  var data = $('#hostdatagrid').jqxGrid('getrowdata', row);
	  window.parent.addHostParentTabs(data.resHostSid,data.monitorNodeId,data.hostName);  
  }
  
  // 设置主机监控节点
  function getHostMonitorNodeId(){
	  
	  var rowindex = $('#hostdatagrid').jqxGrid('selectedrowindex');
	  var isok = $("#jqxSetHostMonitorBtn").jqxButton("disabled");
	  if(rowindex >= 0 && !isok){
		    var data = $('#hostdatagrid').jqxGrid('getrowdata', rowindex);
		    Core.confirms({
				  title:"提示",
				  message:"确定要将该主机加入监控吗？",
				  confirmCallback : function() {
					  
					  Core.AjaxRequest({
					  		url : ws_url + "/rest/hosts/addNodeId/"+data.resHostSid,
					  		type:"get",
					  		async:false,
					  		callback : function(result) {
					  			 // 清除列表选择项
								 $('#hostdatagrid').jqxGrid('clearselection');
								 // 带着查询条件刷新列表
								 refreshHostGrid();
							  }
					  	});
				  }
			  });
	  }else{
		Core.alert({
			  title:"提示",
			  message:'请选择一条记录！',
		});
	  }
  }
  
  // 删除主机监控节点
  function deleteHostMonitorNodeId(){
	  var rowindex = $('#hostdatagrid').jqxGrid('selectedrowindex');
	  var isok = $("#jqxDeleteHostMonitorBtn").jqxButton("disabled");
	  if(rowindex >= 0 && !isok){
		    var data = $('#hostdatagrid').jqxGrid('getrowdata', rowindex);
		    Core.confirms({
				  title:"提示",
				  message:"确定要将该主机退出监控吗？",
				  confirmCallback : function() {
					  
					  Core.AjaxRequest({
					  		url : ws_url + "/rest/hosts/deleteNodeId/"+data.resHostSid,
					  		type:"get",
					  		async:false,
					  		callback : function(result) {
					  			 // 清除列表选择项
								 $('#hostdatagrid').jqxGrid('clearselection');
								 // 带着查询条件刷新列表
								 refreshHostGrid();
							  }
					  	});
				  }
			  });
	  }else{
		Core.alert({
			  title:"提示",
			  message:'请选择一条记录！',
		});
	  }
  }
