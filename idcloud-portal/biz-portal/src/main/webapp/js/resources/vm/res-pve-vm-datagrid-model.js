var virtualPveVMDatagridModel = function () {
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
				gridId: "pvevmdatagrid",
				url: ws_url + "/rest/vms/findAll",
				params: me.searchObj
			});
	    	
	    	$("#pvevmdatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initVMInput = function(){
	    	// 初始化查询组件
	        $("#search-pve-vm-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-pve-vm-ip").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        
	        $("#search-pve-vm-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme}); 
	        // 初始化下拉列表框 
			var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			codesearch.getCommonCode("search-pve-vm-status","RES_VM_STATUS",true);
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initVMDatagrid = function(){
	    	  
	    	 var dataAdapter = Core.getPagingDataAdapter({
					gridId: "pvevmdatagrid",
					url: ws_url + "/rest/vms/findAll",
					params: me.searchObj
			  });
	          $("#pvevmdatagrid").jqxGrid({
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
	                  
	                  { text: '分区名称', datafield: 'vmName', cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
						  return "<div style='padding-top:3px;'><a class='datagrid-link' onclick='goPveVmMonitorPage("+row+")' href='#'>&nbsp;"+value+"</a></div>";
					  }},
	                  { text: '操作系统', datafield: 'osVersion',width:160},
	                  
	                  { text: '使用量', columngroup: 'physicalCpu',datafield: 'powerCpuUsedUnits',cellsalign:'center',align: 'center',width:60},
	                  { text: '最小使用量', columngroup: 'physicalCpu',datafield: 'powerCpuMinUnits',cellsalign:'center',align: 'center',width:80},
	                  { text: '最大使用量', columngroup: 'physicalCpu',datafield: 'powerCpuMaxUnits',cellsalign:'center',align: 'center',width:80},
	                  
	                  { text: '数', columngroup: 'virtualCpu',datafield: 'cpuCores',cellsalign:'center',align: 'center',width:60,cellsrenderer: function(row, columnfield, value){
	                	  var data = $('#pvevmdatagrid').jqxGrid('getrowdata', row);
	                	  var val = "";
	                	  if(data.parType == 1){
	                		  val = "<div style='width:100%;height:23px;text-align:center;line-height:23px;'>"+value+"</div>";;
	                	  }else{
	                		  val = "<div style='width:100%;height:23px;text-align:center;line-height:23px;'>N/A</div>";
	                	  }
	                	  return val;
					  }},
	                  { text: '最小数', columngroup: 'virtualCpu',datafield: 'cpuCoresMin',cellsalign:'center',align: 'center',width:80,cellsrenderer: function(row, columnfield, value){
	                	  var data = $('#pvevmdatagrid').jqxGrid('getrowdata', row);
	                	  var val = "";
	                	  if(data.parType == 1){
	                		  val = "<div style='width:100%;height:23px;text-align:center;line-height:23px;'>"+value+"</div>";;
	                	  }else{
	                		  val = "<div style='width:100%;height:23px;text-align:center;line-height:23px;'>N/A</div>";
	                	  }
	                	  return val;
					  }},
	                  { text: '最大数', columngroup: 'virtualCpu',datafield: 'cpuCoresMax',cellsalign:'center',align: 'center',width:80,cellsrenderer: function(row, columnfield, value){
	                	  var data = $('#pvevmdatagrid').jqxGrid('getrowdata', row);
	                	  var val = "";
	                	  if(data.parType == 1){
	                		  val = "<div style='width:100%;height:23px;text-align:center;line-height:23px;'>"+value+"</div>";;
	                	  }else{
	                		  val = "<div style='width:100%;height:23px;text-align:center;line-height:23px;'>N/A</div>";
	                	  }
	                	  return val;
					  }},
	                  
	                  { text: '使用量', columngroup: 'memory',datafield: 'memorySize',cellsalign:'center',align: 'center',width:60},
	                  { text: '最小使用量', columngroup: 'memory',datafield: 'memoryMin',cellsalign:'center',align: 'center',width:80},
	                  { text: '最大使用量', columngroup: 'memory',datafield: 'memoryMax',cellsalign:'center',align: 'center',width:80},
	                  
	                  { text: '系统盘容量(GB)', datafield: 'allotStorageVolume',cellsalign:'left',width:100},
	                  { text: 'IP地址', datafield: 'vmIp',width:150},
	                  { text: '状态', datafield: 'statusName',width:60,align: 'center',cellsalign:'center'}
	                  
	              ],
	              columngroups: [
	                    { text: '物理CPU(个)', align: 'center', name: 'physicalCpu' },
	                    { text: '虚拟CPU(个)', align: 'center', name: 'virtualCpu' },
	                    { text: '内存(GB)', align: 'center', name: 'memory' }
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var refreshBtn = $("<div><a id='jqxRefreshHostBtn' onclick='refreshPveVmGrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
	                  var addBtn = $("<div><a id='jqxaddPveHostBtn' onclick='popAddPveVmWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editBtn = $("<div><a id='jqxEditPveHostBtn' onclick='popEditPveVmWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var deleteBtn = $("<div><a id='jqxDeleteBtn' onclick='deletePartition()' style='margin-left:-2px'>&nbsp;&nbsp;<i class='icons-blue icon-trash'></i>删除&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(refreshBtn);
	                  container.append(addBtn);
	                  container.append(editBtn);
	                  container.append(deleteBtn);
	              }
	          });

  			  // 控制按钮是否可用
			  $("#pvevmdatagrid").on('rowselect', function(event) {
				  $("#jqxDeleteBtn").jqxButton({disabled:false});
				  $("#jqxEditPveHostBtn").jqxButton({disabled:false});
	          });
				
			  $("#jqxRefreshHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
			  $("#jqxaddPveHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
			  $("#jqxEditPveHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled:true});
			  $("#jqxDeleteBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled:true});
			  
	    };

  };

  // 查询虚拟机
  function searchPveVm(){
	  var vm = new virtualPveVMDatagridModel();
	  vm.searchObj["qm.vmNameLike"] = $("#search-pve-vm-name").val();
	  vm.searchObj["qm.vmIpLike"] = $("#search-pve-vm-ip").val();
	  vm.searchObj["qm.status"] = $("#search-pve-vm-status").val()=="全部"?"":$("#search-pve-vm-status").val();
	  vm.searchVMInfo();
  }
  
  function goPveVmMonitorPage(row){
	    // 获得当前双击的vm数据
	    var rowData = $('#pvevmdatagrid').jqxGrid('getrowdata', row);
	    window.parent.addParentTabs(rowData.resVmSid,rowData.monitorNodeId,rowData.vmName);
}
  
  // 刷新vm列表
  function refreshPveVmGrid(){
	  var vm = new virtualPveVMDatagridModel();
	  vm.searchObj["qm.vmNameLike"] = $("#search-pve-vm-name").val();
	  vm.searchObj["qm.vmIpLike"] = $("#search-pve-vm-ip").val();
	  vm.searchObj["qm.status"] = $("#search-pve-vm-status").val()=="全部"?"":$("#search-pve-vm-status").val();
	  vm.searchVMInfo();
  }
  
  // 删除分区
  function deletePartition(){
//	  alert("hahaha");
	  var rowindex = $('#pvevmdatagrid').jqxGrid('selectedrowindexes');
		var msg = "";
		var datas = new Array();
		if(rowindex.length > 0){
			for(var i=0;i<rowindex.length;i++){
				var data = $('#pvevmdatagrid').jqxGrid('getrowdata', rowindex[i]);
				datas[i]=data;
			}
			 Core.confirm({
				 title:"请选择",
				 message:"分区删除后将无法恢复，确认删除选择的分区吗？",
				 width:350,
				 confirmCallback:function(){
					var data = $('#pvevmdatagrid').jqxGrid('getrowdata', rowindex);
					Core.AjaxRequest({
						  url : ws_url + "/rest/vms/multiop/destory",
						  type:"post",
						  params:datas,
						  async:true,
						  callback : function(result) {
							  // 清除列表选择项
							  $('#pvevmdatagrid').jqxGrid('clearselection');
							  // 带着查询条件刷新列表
							  searchPveVm();
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
  }
  
