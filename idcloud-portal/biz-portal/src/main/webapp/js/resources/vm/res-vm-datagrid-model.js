var virtualVMDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		"qm.vmNameLike" : null,
	    		"qm.status" : null,
	    		"qm.vmIpLike":null,
	    		"qm.resTopologyType" : resTopologyType,
	    		"qm.resTopologySid" : resTopologySid	
		};
	    this.staObj = {
	    		vmNameLike:"",
	    		status:"",
	    		resTopologyType:resTopologyType,
	    		resTopologySid:resTopologySid	
	    };
	    this.VData = new Object();
	    
	    // 用户数据
	    this.searchVMInfo = function(){
	    	var dataAdapter = Core.getPagingDataAdapter({
				gridId: "vmdatagrid",
				url: ws_url + "/rest/vms/findAll",
				params: me.searchObj
			});
	    	
	    	$("#vmdatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    };

	    // 在资源池下统计虚拟机资源
	    this.getVmStatisticsInfoInResPool = function(resPoolSid){
	    	var vmData = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/vms/statistical/pool/status/"+resPoolSid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				vmData = data;
	 			}
	 		});
	    	
	    	var data = new Object();
			data.vmCount = vmData.staTotalVm;
			data.attr = new Array();
			
			var value = [vmData.staNormalVm,vmData.staDownVm,vmData.staPauseVm,vmData.staOthersVm];
			var name =["正常","关机","暂停","其他"];
			
			for(var i=0; i<4;i++){
				var obj = new Object();
				var val = value[i] == null ? 0:value[i];
				obj.name = name[i]+"("+val+")";
				obj.value = val;
				data.attr.push(obj);
			}
			
			// 获得pc下虚拟机的cpu总数、memory总数
			data.allot = new Object();
			data.allot.cpuTotalCounts = vmData.cpuCores;
			data.allot.memoryTotalSize = vmData.memorySize;
			return data;
	    	
	    };
	    
	    // 统计虚拟机资源---拓扑结构
	    this.getVmStatisticsInfo = function(){
	    	var vmData = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/vms/statistical/topology/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				vmData = data;
	 			}
	 		});
	    	
	    	var data = new Object();
			data.vmCount = vmData.staTotalVm;
			data.attr = new Array();
			
			var value = [vmData.staNormalVm,vmData.staDownVm,vmData.staPauseVm,vmData.staOthersVm];
			var name =["正常","关机","暂停","其他"];
			
			for(var i=0; i<4;i++){
				var obj = new Object();
				var val = value[i] == null ? 0:value[i];
				obj.name = name[i]+"("+val+")";
				obj.value = val;
				data.attr.push(obj);
			}
			return data;
	    	
	    };
	    
	    // 统计虚拟机资源---主机下
	    this.getVmStatisticsInfoInHost = function(){
	    	var vmData = null;
	    	 Core.AjaxRequest({
				url : ws_url + "/rest/vms/findByparam/"+resTopologySid,
				type:"get",
				async:false,
				callback : function (data) {
					vmData = data;
				}
			 });
	    	var data = new Object();
			data.vmCount = vmData.staTotalVm;
			data.attr = new Array();
			
			var value = [vmData.staNormalVm,vmData.staDownVm,vmData.staPauseVm,vmData.staOthersVm];
			var name =["正常","关机","暂停","其他"];
			
			for(var i=0; i<4;i++){
				var obj = new Object();
				var val = value[i] == null ? 0:value[i];
				obj.name = name[i]+"("+val+")";
				obj.value = val;
				data.attr.push(obj);
			}
			return data;
	    	
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initVMInput = function(){
	    	// 初始化查询组件
	        $("#search-vm-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-vm-ip").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        
	        $("#search-vm-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme}); 
	        // 初始化下拉列表框 
			 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			 codesearch.getCommonCode("search-vm-status","RES_VM_STATUS",true);
//			 codesearch.getCustomCode("search-vmrole","/user/findAllRole","roleName","roleSid",true);
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initVMDatagrid = function(){
	    	  
	    	 var dataAdapter = Core.getPagingDataAdapter({
					gridId: "vmdatagrid",
					url: ws_url + "/rest/vms/findAll",
					params: me.searchObj
			  });
	          $("#vmdatagrid").jqxGrid({
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
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"checkbox",
	              localization: gridLocalizationObj,
	              columns: [
	                  
	                  { text: '虚拟机名称', datafield: 'vmName', cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
						  return "<div style='padding-top:3px;'><a class='datagrid-link' onclick='goVmMonitorPage("+row+")' href='#'>&nbsp;"+value+"</a></div>";
					  }},
	                  { text: '所属项目', datafield: 'mgtObjName', cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
						  return "<div style='padding-top:3px;'><a class='datagrid-link' onclick='popMgtObjDetail("+row+")' href='#'>&nbsp;"+value+"</a></div>";
					  }},
	                  { text: '操作系统', datafield: 'osVersionName',width:200},
	                  { text: 'CPU(核)', datafield: 'cpuCores',cellsalign:'right',width:60},
	                  { text: 'CPU使用率', datafield: 'cpuUseRate',cellsalign:'right',width:80},
	                  { text: '内存(GB)', datafield: 'memorySizeGb',cellsalign:'right',width:60},
	                  { text: '内存使用率', datafield: 'memoryUseRate',cellsalign:'right',width:90},
	                  { text: '分配存储容量(GB)', datafield: 'allotStorageVolume',cellsalign:'right',width:110},
	                  { text: '监控状态',datafield: 'monitorNodeId',width:60,cellsrenderer:function (row, datafield, value) {
	                	 
	                	  var monitor= "";
	                	  if(value != null && value != "" && value != "null"){
	                		  monitor = "<i class='icon-circle' title='已监控' style='font-size:16px;color:#62CB62'></i>";
	                	  }else{
	                		  monitor = "<i class='icon-circle' title='未监控' style='font-size:16px;color:#BFBFBF'></i>";
	                	  }
	                      return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+monitor +"&nbsp";
	                  }},
	                  { text: 'IP地址', datafield: 'vmIp'},
	                  { text: '状态', datafield: 'statusName',width:60,align: 'center',cellsalign:'center'},
	                  { text: '操作', datafield: '',align: 'center',cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
						   return "<div style='width:100px;height:22px;line-height:25px;'>&nbsp;&nbsp;<i onclick='goVmMonitorPage("+row+")' title='详情' class='icons-blue icon-popup'></i>&nbsp;&nbsp;&nbsp;&nbsp;<i  onclick='popConfigIpWindow("+row+")' title='调整IP' class='icons-blue icon-stackexchange'></i>" +
						   		"<!--&nbsp;&nbsp;<i onclick='webconsole("+row+")' title='控制台' class='icons-blue icon-desktop'></i>&nbsp;&nbsp;<i onclick='switchVNCBtn("+row+")' title='开启/关闭VNC服务' class='icons-blue icon-exchange'></i>--></div>";
					  	},width:80
	                  }
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var refreshBtn = $("<div><a id='jqxRefreshVmBtn' onclick='refreshVmGrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
//	                  var detailsBtn = $("<div><a id='jqxDetailsBtn' onclick='popVmDetailInfo()'>&nbsp;&nbsp;<i class='icons-blue icon-popup'></i>详情&nbsp;&nbsp;</a></div>");
	                  var exportBtn = $("<div><a id='jqxExportVmBtn' onclick='exportVmDatagrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-export'></i>导出&nbsp;&nbsp;</a></div>");
//					  var asyncBtn = $("<div><a id='jqxAsyncBtn' onclick='asyncVmInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-ccw'></i>同步&nbsp;&nbsp;</a></div>");               
//					  var multiopBtn = $("<div><a id='jqxMultiopBtn' onclick='multiop()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-tasks'></i>批量操作&nbsp;&nbsp;</a></div>");               
//	                  var monitorBtn = $("<div><a id='jqxMonitorBtn' onclick='popMonitorWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>监控&nbsp;&nbsp;</a></div>");
	                  var startVmBtn = $("<div><a id='jqxStartVmBtn' onclick='startVm()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-play'></i>开机&nbsp;&nbsp;</a></div>");
	                  var closeVmBtn = $("<div><a id='jqxCloseVmBtn' onclick='stopVm()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-stop'></i>关机&nbsp;&nbsp;</a></div>");
//	                  var restartBtn = $("<div><a id='jqxRestartBtn' onclick='restartVm()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cw'></i>重启&nbsp;&nbsp;</a></div>");
//	                  var chooseBtn = $("<div><a id='jqxChooseVmBtn' data-bind='click: removeUserItem' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>调整配置&nbsp;&nbsp;</a></div>");
//	                  var consoleBtn = $("<div><a id='jqxConsoleBtn' onclick='openWebConsole()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-popup'></i>控制台&nbsp;&nbsp;</a></div>");
//	                  var removeBtn = $("<div><a id='jqxRemoveVmBtn' onclick='popMigrateVmWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-move'></i>迁移&nbsp;&nbsp;</a></div>");
//	                  var configIpBtn = $("<div><a id='jqxConfigIpVmBtn' onclick='popConfigIpWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-stackexchange'></i>调整IP地址&nbsp;&nbsp;</a></div>");
	                  var setMonitorBtn = $("<div><a id='jqxSetMonitorBtn' onclick='getVmMonitorNodeId()' style='margin-left:-2px'>&nbsp;&nbsp;<i class='icons-blue icon-level-down'></i>加入监控&nbsp;&nbsp;</a></div>");
	                  var deleteMonitorBtn = $("<div><a id='jqxDeleteMonitorBtn' onclick='deleteVmMonitorNodeId()' style='margin-left:-2px'>&nbsp;&nbsp;<i class='icons-blue icon-level-up'></i>退出监控&nbsp;&nbsp;</a></div>");
	                  var tuidingBtn = $("<div><a id='jqxTuidingBtn' onclick='deleteVm()' style='margin-left:-2px'>&nbsp;&nbsp;<i class='icons-blue icon-trash'></i>删除&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(refreshBtn);
//	                  container.append(detailsBtn);
//	                  container.append(asyncBtn);
//	                  container.append(editBtn);
//	                  container.append(closeBtn);
//	                  container.append(restartBtn);
//	                  container.append(chooseBtn);
	                  container.append(setMonitorBtn);
	                  container.append(deleteMonitorBtn);
	                  container.append(startVmBtn);
	                  container.append(closeVmBtn);
//	                  container.append(removeBtn);
//	                  container.append(configIpBtn);
//	                  container.append(consoleBtn);
	                  container.append(tuidingBtn);
	                  container.append(exportBtn);
	              }
	          });

				// 控制按钮是否可用
				$("#vmdatagrid").on('rowselect', function(event) {

				      var args = event.args;
				      var data = args.row;
				      
				      if(args.rowindex.length > 10){
				    	  $("#jqxSetMonitorBtn").jqxButton({disabled: true});
				    	  $("#jqxDeleteMonitorBtn").jqxButton({disabled: true});
				    	  $("#jqxStartVmBtn").jqxButton({disabled: true});
				    	  $("#jqxCloseVmBtn").jqxButton({disabled: true});
				    	  $("#jqxTuidingBtn").jqxButton({disabled: true});
				      }
					  
				      if(data.monitorNodeId != null){
				    	  $("#jqxSetMonitorBtn").jqxButton({disabled: true});
				    	  $("#jqxDeleteMonitorBtn").jqxButton({disabled: false});
				      }else{
				    	  $("#jqxSetMonitorBtn").jqxButton({disabled: false});
				    	  $("#jqxDeleteMonitorBtn").jqxButton({disabled: true});
				      }
				      
				      // 判断虚拟机的状态是否正常
				      if(data.status == 'normal'){
				    	  $("#jqxStartVmBtn").jqxButton({disabled: true});
				    	  $("#jqxCloseVmBtn").jqxButton({disabled: false});
				      }else if(data.status == 'poweredOff'){
				    	  $("#jqxStartVmBtn").jqxButton({disabled: false});
				    	  $("#jqxCloseVmBtn").jqxButton({disabled: true});
				      }
		    		  
//	    			  $("#jqxAsyncBtn").jqxButton({ disabled: false });
//	    			  $("#jqxEditvmBtn").jqxButton({ disabled: false });
//		   			  $("#jqxCloseBtn").jqxButton({disabled: false});
//		   			  $("#jqxRestartBtn").jqxButton({disabled: false});
//		   			  $("#jqxChooseVmBtn").jqxButton({disabled: true});
					  
//		   			  $("#jqxRemoveVmBtn").jqxButton({disabled: false});
//		   			  $("#jqxConfigIpVmBtn").jqxButton({disabled: false});
		   			  $("#jqxTuidingBtn").jqxButton({disabled: false});
		   			 
		   			  
//		   			  $("#jqxweb").jqxButton({disabled: false});
//		   			  $("#jqxDetailsBtn").jqxButton({disabled: false});
	          });
				
			  $("#jqxRefreshVmBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});	
//	    	  $("#jqxAsyncBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
//	    	  $("#jqxMultiopBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
//	    	  $("#jqxEditvmBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
//   			  $("#jqxCloseBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
//   			  $("#jqxRestartBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
//   	      $("#jqxChooseVmBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			  $("#jqxSetMonitorBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			  $("#jqxDeleteMonitorBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			  
			  $("#jqxStartVmBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			  $("#jqxCloseVmBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			  
//   			  $("#jqxRemoveVmBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
//   			  $("#jqxConfigIpVmBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
   			  $("#jqxTuidingBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
//	          $("#jqxDetailsBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
	          $("#jqxExportVmBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
//	          $("#jqxweb").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
	          
	    };
	    
	    // 初始化弹出window
  };

  // 查询虚拟机
  function searchVm(){
	  var vm = new virtualVMDatagridModel();
	  vm.searchObj["qm.vmNameLike"] = $("#search-vm-name").val();
	  vm.searchObj["qm.vmIpLike"] = $("#search-vm-ip").val();
	  vm.searchObj["qm.status"] = $("#search-vm-status").val()=="全部"?"":$("#search-vm-status").val();
	  vm.searchVMInfo();
  }
  
  //批量开机
  function startVm(){
	  var rowindex = $('#vmdatagrid').jqxGrid('selectedrowindexes');
	  var ok =  $("#jqxStartVmBtn").jqxButton("disabled");
		var msg = "";
		var datas = new Array();
		var flag=false;
		if(ok){
			return;
		}
		if(rowindex.length > 0 ){
				for(var i=0;i<rowindex.length;i++){
					var data = $('#vmdatagrid').jqxGrid('getrowdata', rowindex[i]);
						datas[i]=data;
						if("poweredOff" != data.status){
							flag = true;
						}
				}
			if(!flag){
				Core.confirm({
					title:"请选择",
					message:"您确定要开启选择的虚拟机吗？",
					confirmCallback:function(){
						Core.AjaxRequest({
							  url : ws_url + "/rest/vms/multiop/start",
							  type:"post",
							  params:datas,
							  async:true,
							  callback : function(result) {
								  // 清除列表选择项
								  $('#vmdatagrid').jqxGrid('clearselection');
								  // 带着查询条件刷新列表
								  searchVm();
							  }
						  });
					}
				});
			}else{
				Core.alert({
					type : "error",
					message:"选中虚拟机的状态不正确，不能启动！"
				});
			}
		}else{
			 Core.alert({
					title: "错误",
					type: "error",
					width: 240,
					message: "请选择数据后再进行操作！",
				  });
		}
  }
  
  
//批量关机
  function stopVm(){
	  	var rowindex = $('#vmdatagrid').jqxGrid('selectedrowindexes');
	  	var ok =  $("#jqxCloseVmBtn").jqxButton("disabled");
	  	if(ok){
			return;
		}
		var datas = new Array();
		var flag=false;
		if(rowindex.length > 0){
				for(var i=0;i<rowindex.length;i++){
					var data = $('#vmdatagrid').jqxGrid('getrowdata', rowindex[i]);
						datas[i]=data;
						if("normal" != data.status){
							flag = true;
						}
				}
			if(!flag){
				Core.confirm({
					title:"请选择",
					message:"您确定要关闭选择的虚拟机吗？",
					confirmCallback:function(){
						Core.AjaxRequest({
							  url : ws_url + "/rest/vms/multiop/stop",
							  type:"post",
							  params:datas,
							  async:true,
							  callback : function(result) {
								  // 清除列表选择项
								  $('#vmdatagrid').jqxGrid('clearselection');
								  // 带着查询条件刷新列表
								  searchVm();
							  }
						  });
					}
				});
			}else{
				Core.alert({
					type : "error",
					message:"选中虚拟机的状态不正确，不能启动！"
				});
			}
			
		}else{
			 Core.alert({
					title: "错误",
					type: "error",
					width: 240,
					message: "请选择数据后再进行操作！",
				  });
		}
  }
  
//批量重启
  function restartVm(){
	  var rowindex = $('#vmdatagrid').jqxGrid('selectedrowindexes');
		var msg = "";
		var datas = new Array();
		var flag=false;
		if(rowindex.length > 0){
				for(var i=0;i<rowindex.length;i++){
					var data = $('#vmdatagrid').jqxGrid('getrowdata', rowindex[i]);
						datas[i]=data;
						if("poweredOff" == data.status){
							flag = true;
						}
				}
			if(!flag){
				Core.confirm({
					title:"请选择",
					message:"您确定要重启选择的虚拟机吗？",
					confirmCallback:function(){
						Core.AjaxRequest({
							  url : ws_url + "/rest/vms/multiop/reboot",
							  type:"post",
							  params:datas,
							  async:true,
							  callback : function(result) {
								  // 清除列表选择项
								  $('#vmdatagrid').jqxGrid('clearselection');
								  // 带着查询条件刷新列表
								  searchVm();
							  }
						  });
					}
				});
			}else{
				Core.alert({
					type : "error",
					message:"存在有已关机的虚拟机，不能重启！"
				});
			}
			
		}else{
			 Core.alert({
					title: "错误",
					type: "error",
					width: 240,
					message: "请选择数据后再进行操作！",
				  });
		}
  }

  //删除虚拟机
  function deleteVm(){
		  var rowindex = $('#vmdatagrid').jqxGrid('selectedrowindexes');
		  var ok =  $("#jqxTuidingBtn").jqxButton("disabled");
		  if(ok){
				return;
			}
		var msg = "";
		var datas = new Array();
		if(rowindex.length > 0){
			for(var i=0;i<rowindex.length;i++){
				var data = $('#vmdatagrid').jqxGrid('getrowdata', rowindex[i]);
				datas[i]=data;
			}
			 Core.confirm({
				 title:"请选择",
				 message:"虚拟机删除后将无法恢复，确认删除选择的虚拟机吗？",
				 width:350,
				 confirmCallback:function(){
					var data = $('#vmdatagrid').jqxGrid('getrowdata', rowindex);
					Core.AjaxRequest({
						  url : ws_url + "/rest/vms/multiop/destory",
						  type:"post",
						  params:datas,
						  async:true,
						  callback : function(result) {
							  // 清除列表选择项
							  $('#vmdatagrid').jqxGrid('clearselection');
							  // 带着查询条件刷新列表
							  searchVm();
						  }
					  });
				 }
			 });
	}else{
		 Core.alert({
				title: "错误",
				type: "error",
				width: 240,
				message: "请选择数据后再进行操作！",
			  });
	}
  };

  //批量迁移虚拟机
  function migrateVm(){
	var rowindex = $('#vmdatagrid').jqxGrid('selectedrowindexes');
	if(rowindex > 0){
		 Core.confirm({
			 title:"请选择",
			 message:"您确定要迁移选择的虚拟机吗？",
			 confirmCallback:function(){
				var data = $('#vmdatagrid').jqxGrid('getrowdata', rowindex);
				var resVmSid = data.resVmSid;
				Core.AjaxRequest({
			  		url : ws_url + "/rest/vms/deleteVm/"+resVmSid,
			  		type:"get",
			  		async:false,
			  		showMsg:false,
			  		callback : function(data) {
			  			var vmDetail=vmDetailModel();
			  			var data={resVmSid:resVmSid};
			  			vmDetail.setVmBasicInfo(data);
			  			
			  			 // 清除列表选择项
						 $('#vmdatagrid').jqxGrid('clearselection');
						 // 带着查询条件刷新列表
						 searchVm();
			  		}
			  	});
			 }
		 });
	}else{
		 Core.alert({
				title: "错误",
				type: "error",
				width: 240,
				message: "请选择数据后再进行操作！",
			  });
	}
  };
  
  //调整虚拟机
  function reconfigVm(){
	
  	var vmReconfig = new vmReconfigModel();
  	var data={resVmSid:resVmSid,cpuCores:$("#cpuCores").html(),memorySize:$("#memorySize").html()};
  	vmReconfig.setVmReconfigForm(data);
      var windowW = $(window).width(); 
    	var windowH = $(window).height();
    	$("#vmReconfigWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-140)/2 } });
    	$("#vmReconfigWindow").jqxWindow('open');
  };
  
//弹出选择迁移类型
  function popMigrateVmWindow() {
  	var rowindex = $('#vmdatagrid').jqxGrid('selectedrowindexes');
  	var datas = new Array();
  	if(rowindex.length > 0){
		for(var i=0;i<rowindex.length;i++){
			var data = $('#vmdatagrid').jqxGrid('getrowdata', rowindex[i]);
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
  	}else{
		 Core.alert({
				title: "错误",
				type: "error",
				width: 240,
				message: "请选择数据后再进行操作！",
			  });
	}
}
  
  //调用WebConsole
//  function openWebConsole(row){
////	  var rowindex = $('#vmdatagrid').jqxGrid('getselectedrowindex');
////	  console.log(JSON.stringify(rowindex));
////	  var ok =  $("#jqxConsoleBtn").jqxButton("disabled");
//	  var rowData = $('#vmdatagrid').jqxGrid('getrowdata', row);
//		//  var data = $('#vmdatagrid').jqxGrid('getrowdata', rowindex);
//		  var resVmSid = rowData.resVmSid;
//		  var host = window.location.hostname;
//		  var hostSid = rowData.allocateResHostSid;
//		  Core.AjaxRequest({
//			  url : ws_url + "/rest/vms/findVmVncSwitch/"+hostSid,
//			  type:"get",
//			  callback : function(result) {
//				  if(!result){
//					  Core.confirms({
//						  title:"提示",
//						  message:"这台虚拟机所属资源环境没有开启VNC服务！"+'<p style="margin-top: -40px;">'+"不能开启控制台。"+'</p>',
//					  });
//				  }else{
//					  Core.AjaxRequest({
//						  url : ws_url + "/rest/vms/findVmVncPorts/"+resVmSid,
//						  type:"get",
//						  callback : function(result) {
//							  if(result!="" && null!=result){
//								  Core.AjaxRequest({
//									  url : ws_url + "/rest/vms/openWebConsole",
//									  type:"post",
//									  params:{
//											resVmSid:resVmSid,
//											host:host
//									  },
//									  async:false,
//									  callback : function(result) {
//										  
//										  var oWin=window.open(result.url, {replace:true});
//										  oWin.document.title=rowData.vmName;
//									  }
//								  }); 
//							  }else{
//								  Core.confirms({
//									  title:"提示",
//									  message:"这台虚拟机没有配置VNC服务端口！"+'<p style="margin-top: -40px;">'+"不能开启控制台。"+'</p>',
//								  });
//							  }
//						  }
//					  }); 
//				  }
//			  }
//		  });
//  }
  //switch VNC
//  function switchVNCBtn(row){
////	  var rowindex = $('#vmdatagrid').jqxGrid('getselectedrowindex');
////	  var ok =  $("#jqxSwitchBtn").jqxButton("disabled");
////	  if(rowindex >= 0  && !ok){
//	  var rowData = $('#vmdatagrid').jqxGrid('getrowdata', row);
//		  var resVmSid = rowData.resVmSid;
//		  var allocateResHostSid = rowData.allocateResHostSid;
//		  var hostSid = rowData.allocateResHostSid;
//		  Core.AjaxRequest({
//			  url : ws_url + "/rest/vms/findVmVncSwitch/"+hostSid,
//			  type:"get",
//			  callback : function(result) {
//				  if(!result){
//					  Core.confirms({
//						  title:"提示",
//						  message:"这台虚拟机所属资源环境没有开启VNC服务！"+'<p style="margin-top: -40px;">'+"不能开启/关闭VNC端口。"+'</p>',
//					  });
//				  }else{
//					  //判断同ve环境下的某主机vnc端口是否已经达到上限
//					  Core.AjaxRequest({
//						  url : ws_url + "/rest/vms/findCountVmVncPort/"+allocateResHostSid,
//						  type:"get",
//						  callback : function(result) {
//							  //如果没有达到上限则添加，反之不添加退出
//							  if(result){
//								  Core.AjaxRequest({
//									  url : ws_url + "/rest/vms/findVmVncPorts/"+resVmSid,
//									  type:"get",
//									  callback : function(result) {
//										  if(result!="" && null!=result){
//											  var myBoolean=new Boolean(false);
//											  Core.confirms({
//												  title:"提示",
//												  message:"该虚拟机已配置VNC服务,要<B>关闭</B>它吗？关闭VNC端口需要重启"+'<p style="margin-top: -40px;">'+"您的虚拟机，请先保存好数据！需要关闭VNC服务请点击<B>确定</B>！"+'</p>',
//												  confirmCallback : function() {
//													  Core.AjaxRequest({
//														  url : ws_url + "/rest/vms/opencloseVNC/"+resVmSid+"/"+myBoolean,
//													  	  type:"get",
//													  	  callback : function(result) {
//													  	}	
//													  });
//												  }
//											  });
//										  }else{
//											  var myBoolean=new Boolean(true);
//											  Core.confirms({
//												  title:"提示",
//												  message:"配置VNC服务端口需要<B>重启</B>您的虚拟机，请先保存好您的数据！"+'<p style="margin-top: -40px;">'+"配置服务点击<B>确定</B>，关闭点击<B>取消</B>！"+'</p>',
//												  confirmCallback : function() {
//													  Core.AjaxRequest({
//														  url : ws_url + "/rest/vms/opencloseVNC/"+resVmSid+"/"+myBoolean,
//													  	  type:"get",
//													  	  callback : function(result) {
//													  		  
//													  	}	
//													  });
//												  }
//											  });
//										  }
//									  }
//								  }); 
//							  }else{
//								  var myBoolean=new Boolean(false);
//								  Core.AjaxRequest({
//									  url : ws_url + "/rest/vms/findVmVncPorts/"+resVmSid,
//									  type:"get",
//									  callback : function(result) {
//										  if(result!="" && null!=result){
//											  Core.confirms({
//												  title:"提示",
//												  message:"该资源环境下的VNC端口已达上限！"+'<p style="margin-top: -40px;">'+"要关闭该虚机VNC端口吗？"+'</p>',
//												  confirmCallback : function() {
//													  Core.AjaxRequest({
//														  url : ws_url + "/rest/vms/opencloseVNC/"+resVmSid+"/"+myBoolean,
//													  	  type:"get",
//													  	  callback : function(result) {
//													  	}	
//													  });
//												  }
//											  });
//										  }else{
//											  Core.confirms({
//												  title:"提示",
//												  message:"该资源环境下的VNC端口已达上限！"+'<p style="margin-top: -40px;">'+"不能开启。"+'</p>',
//											  });
//										  }
//									  }
//								  });
//							  }
//						  }
//					  });
//				  }
//			  } 
//		  });
//		
////	  }
//  };
  
  // 导出虚拟机
  function exportVmDatagrid(){
	  
	  var vmNameLike =  $("#search-vm-name").val();
	  var status = $("#search-vm-status").val()=="全部"?"":$("#search-vm-status").val();
	 
	  var staObj = {
		  vmNameLike: vmNameLike,
		  status: status,
		  resTopologyType:resTopologyType,
		  resTopologySid:resTopologySid	
	  };
	  
	  var params = JSON.stringify(staObj);
	  window.location = ws_url + "/rest/vms/exportVm/" + params; 
  }
  //同步虚拟机
  function asyncVmInfo(){
	  
	  var rowindex = $('#vmdatagrid').jqxGrid('selectedrowindexes');
		var datas = new Array();
		var flag=false;
		if(rowindex.length > 0){
				for(var i=0;i<rowindex.length;i++){
					var data = $('#vmdatagrid').jqxGrid('getrowdata', rowindex[i]);
						datas[i]=data;
				}
				Core.AjaxRequest({
			  		url : ws_url + "/rest/vms/synaVmInfo",
			  		type:"post",
			  		params:datas,
			  		async:true,
			  		showMsg:true,
			  		callback : function(result) {
			  			 // 清除列表选择项
						 $('#vmdatagrid').jqxGrid('clearselection');
						 // 带着查询条件刷新列表
						 searchVm();
					  }
			  	});
		}else{
			 Core.alert({
					title: "错误",
					type: "error",
					width: 240,
					message: "请选择数据后再进行操作！",
				  });
		}
  }
  Core.confirms = function (settings) {
		
	    var title = settings.title === undefined ? "请选择" : settings.title,
	    	message = settings.message === undefined ? "" : settings.message,
	    	okBtnNm = settings.yes === undefined ? "确定" : settings.yes,
	    	cancelBtnNm = settings.no === undefined ? "取消" : settings.no,
		    type = settings.type === undefined ? "info" : settings.type;
	   
		$('<div id="confirmWindow">'+
			'<div id="customWindowHeader">'+
			  '<span id="captureContainer" style="float: left">'+title+'</span>'+
			'</div>'+
			'<div id="customWindowContent" style="overflow: hidden">'+
				'<div style="width:100%;height:70px;line-height:60px;position:relative;top:0px;left:0px;"><i class="icons-32-blue icon-help-circled"></i><font style="position:absolute;left:50px;height:70px;margin-top: -10px;">'+message+'</font></div>'+
				'<div style="float: right; margin-top: 0px;">'+
					' <input type="button" style="cursor:pointer;margin-right: 5px" value=' + okBtnNm + ' id="okButton" />'+
					' <input type="button" style="cursor:pointer" value=' + cancelBtnNm + ' id="cancelButton" />'+
				'</div>'+
			'</div>'+
			'</div>').appendTo($("body"));

		$("#confirmWindow .jqx-window-modal").css("width","1200px");
		
		var windowW = $(window).width(); 
		var windowH = $(window).height();
		 $('#confirmWindow').jqxWindow({ 
			 position: { x: (windowW-300)/2, y: (windowH-130)/2 },
			 width: 400,
	         height: 130, 
	         resizable: false,
	         isModal: true, 
	         closeButtonAction:'close',
	         theme:currentTheme,
	         initContent: function () {
	             $('#okButton').jqxButton({ width: '60px', disabled: false , theme: currentTheme});
	             $('#cancelButton').jqxButton({ width: '60px', disabled: false, theme: currentTheme });
	             $('#okButton').focus();
	         }
	     });

		$('#confirmWindow').find('#okButton').click(function() {
			$('#confirmWindow').jqxWindow('close');
			$('#confirmWindow').jqxWindow('destroy');
			if (settings.confirmCallback) {
				settings.confirmCallback();	
			}
		});
		
		$('#confirmWindow').find('#cancelButton').click(function() {
			$('#confirmWindow').jqxWindow('close');
			$('#confirmWindow').jqxWindow('destroy');
			if (settings.cancelCallback) {
				settings.cancelCallback();	
			}
		});
	};

	function multiop() {
		var isDisable = $("#jqxMultiopBtn").jqxButton('disabled');
		if(isDisable) {return}
		var selecteRowIndexs = $('#vmdatagrid').jqxGrid('selectedrowindexes');
		var selecteNum = selecteRowIndexs.length;
		var status = $('#vmdatagrid').jqxGrid('getrowdata', selecteRowIndexs[0]).status;
		var datas = new Array();
		if('poweredOff'== status ||'normal'== status) 
		{
			for (var i = 0; i < selecteNum; i++) {
				var data = $('#vmdatagrid').jqxGrid('getrowdata', selecteRowIndexs[i]);
				datas[i] = data;
			}
			if('poweredOff'== status) {
				  Core.confirms({
					  title:"提示",
					  message:'您是想开启这些虚拟机吗？',
					  confirmCallback : function() {
						 initOP('start',datas);
					  }
						  });
					  }else if('normal'== status) {
						  Core.confirms({
							  title:"提示",
							  message:'您是想关闭这些虚拟机吗？',
							  confirmCallback : function() {
								  initOP('stop',datas);
							  },
							  cancelCallback : function() {
								  Core.confirms({
									  title:"提示",
									  message:'您是想重启这些虚拟机吗？',
									  confirmCallback : function() {
										  initOP('restart',datas);
									  }
										  });
							  }
								  });
					  }
			}
	}
	var initOP = function(op,datas){
		Core.AjaxRequest({
			  url : ws_url + "/rest/vms/multiop/"+op,
			  type:"post",
			  params:datas,
			  async:true,
			  callback : function(result) {
				  var vmmanagedmodel = new virtualVMDatagridModel();
		  			vmmanagedmodel.searchVMInfo();
			  }
		  }); 
	};
  
  // 弹出虚拟机监控详情
  function goVmMonitorPage(row){
	    // 获得当前双击的vm数据
	    var rowData = $('#vmdatagrid').jqxGrid('getrowdata', row);
	    window.parent.addParentTabs(rowData.resVmSid,rowData.monitorNodeId,rowData.vmName);
  }
  
  function popMgtObjDetail(row){
	  // 获得当前双击的vm数据
	  var rowData = $('#vmdatagrid').jqxGrid('getrowdata', row);
	  $("#vm-view-mgtObjDetail").html("");
	  Core.AjaxRequest({
		  url : ws_url + "/rest/mgtObj/load/" + rowData.mgtObjSid,
		  type:'post',
		  async: false,
		  params: {},
		  callback : function(data) {
			  var detail = "";
			  for(var i=0;i<data.mgtObjExts.length;i++){
				  if(data.mgtObjExts[i].attrKey=="projectFiles"){
					  var names = data.mgtObjExts[i].fileName;
					  var fileSid = data.mgtObjExts[i].attrValue;
					  var fileNames = new Array;
					  if(names!=null&&names.length>0){
						  fileNames = names.split(",");
					  }
					  var fileSids = fileSid.split(",");
					  detail = detail + "<tr><td style='text-align:right;font-size:12px;width:40%;' class='tdBorder'>"+data.mgtObjExts[i].attrName+"：</td>" +
					  "<td style='text-align:left;font-size:12px;' class='tdBorder'>";
					  for(var n=0;n<fileNames.length;n++){
						  detail = detail + "<a style='color: blue;' fileSid='"+fileSids[n]+"' " +
						  "onclick='mgtObjFileDownLoad(this)' class='datagrid-link'>"+fileNames[n]+"</a>";
					  }
					  detail = detail + "</td></tr>";
				  }else{
					  detail = detail + "<tr><td style='text-align:right;font-size:12px;width:40%;' class='tdBorder'>"+data.mgtObjExts[i].attrName+"：</td>" +
					  "<td style='text-align:left;font-size:12px;' class='tdBorder'>"+data.mgtObjExts[i].attrValue+"</td></tr>";
				  }
			  }
			  $("#vm-view-mgtObjDetail").append(detail);
		  }
	   });
	   $("#vm-view-mgtObjMsg").jqxWindow("open");
  }
  
  function webconsole(row){
	  var rowData = $('#vmdatagrid').jqxGrid('getrowdata', row);
	  var vmSid = rowData.resVmSid;
	  if('normal'!= rowData.status){
		  Core.confirm({
			  title:"提示",
			  message:'这台虚拟机没有开机，无法使用控制台！',
		  });
	  }else{
		  Core.AjaxRequest({
				url : ws_url+ "/rest/vms/openWebConsoleByOpenStackInPlarform/"+rowData.resVmSid,
				type:'get',
	 			async:false,
				callback : function(data) {
					if(null!=data.url){
						window.open(data.url, "_blank");
					}else{
						Core.confirm({
							  title:"提示",
							  message:'开启控制台失败！',
						});
					}
					
				}
			});
	  }
  }

  
  // 刷新vm列表
  function refreshVmGrid(){
	  var vm = new virtualVMDatagridModel();
	  vm.searchObj["qm.vmNameLike"] = $("#search-vm-name").val();
	  vm.searchObj["qm.vmIpLike"] = $("#search-vm-ip").val();
	  vm.searchObj["qm.status"] = $("#search-vm-status").val()=="全部"?"":$("#search-vm-status").val();
	  vm.searchVMInfo();
  }
  
  // 获取vm的监控节点
  function getVmMonitorNodeId(){
	
	  var rowindex = $('#vmdatagrid').jqxGrid('selectedrowindexes');
	  
	  var isok = $("#jqxSetMonitorBtn").jqxButton("disabled");
	  
	  if(isok){
			return;
	  }
	  
	  if(rowindex.length == 1){
		    var data = $('#vmdatagrid').jqxGrid('getrowdata', rowindex[0]);
		    
		    if(data.monitorNodeId != null && data.monitorNodeId != ""){
		    	Core.alert({
					  title:"提示",
					  message:'已加入监控，请重新选择虚拟机！',
				});
		    }else{
		    	 if(data.vmIp == null ||data.vmIp == ""){
				    	Core.alert({
							  title:"提示",
							  message:'加入监控，IP不能为空！',
						});
				  }else{
				    	Core.confirms({
							  title:"提示",
							  message:"确定要将该虚拟机加入监控吗？",
							  confirmCallback : function() {
								  Core.AjaxRequest({
								  		url : ws_url + "/rest/vms/addNodeId/"+data.resVmSid,
								  		type:"get",
								  		async:false,
								  		callback : function(result) {
								  			 // 清除列表选择项
											 $('#vmdatagrid').jqxGrid('clearselection');
											 // 带着查询条件刷新列表
											 searchVm();
										  }
								  	});
							  }
						  });
				    }
		    }
		    
	  }else{
		Core.alert({
			  title:"提示",
			  message:'请只能选择一条记录！',
		});
	  }
  }
  
  // 退出监控
  function deleteVmMonitorNodeId(){
	  var rowindex = $('#vmdatagrid').jqxGrid('selectedrowindexes');
	  
	  var isok = $("#jqxDeleteMonitorBtn").jqxButton("disabled");
	  
	  if(isok){
			return;
	  }
	  
	  if(rowindex.length == 1){
		    var data = $('#vmdatagrid').jqxGrid('getrowdata', rowindex[0]);
		    
		    Core.confirm({
				title:"请选择",
				message:"您确定要删除该虚拟机的监控节点吗？",
				confirmCallback:function(){
					Core.AjaxRequest({
				  		url : ws_url + "/rest/vms/deleteNodeId/"+data.resVmSid,
				  		type:"get",
				  		async:false,
				  		callback : function(result) {
				  			 // 清除列表选择项
							 $('#vmdatagrid').jqxGrid('clearselection');
							 // 带着查询条件刷新列表
							 searchVm();
						  }
				  	});
				}
			});
			
	  }else{
		Core.alert({
			  title:"提示",
			  message:'请只能选择一条记录！',
		});
	  }
  }
  
