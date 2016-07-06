var x86HostName = x86HostIp = "";
var virtualOtherHostDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    	 "qm.hostNameLike":null,
	    	 "qm.hostOsType":null,
	    	 "qm.status":null,
			 "qm.resTopologySid":resTopologySid,
			 "qm.resTopologyType" : resTopologyType,
		};
	    // 用户数据
	    this.searchHostInfo = function(){
	    	var dataAdapter = Core.getPagingDataAdapter({
				gridId: "otherhostdatagrid",
				url: ws_url + "/rest/host/findAll",
				params: me.searchObj
			});
	    	
	    	$("#otherhostdatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initHostInput = function(){
	    	// 初始化查询组件
	        $("#search-other-host-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-other-host-os-type").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-other-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        // 初始化下拉列表框 
			 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			 codesearch.getCommonCode("search-other-host-status","HOST_STATUS",true);
	    };
	    
	    // 初始化用户datagrid的数据源
	    this.initHostDatagrid = function(){
	    	  
	    	  var dataAdapter = Core.getPagingDataAdapter({
					gridId: "otherhostdatagrid",
					url: ws_url + "/rest/host/findAll",
					params: me.searchObj
			  });
	    	
	          $("#otherhostdatagrid").jqxGrid({
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
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
					  { text: '主机名称', datafield: 'hostName', cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
						  return "<div style='padding-top:3px;'><a class='datagrid-link' onclick='goOtherHostDetailPage("+row+")' href='#'>&nbsp;"+value+"</a></div>";
					  }},
	                  { text: '主机型号', datafield: 'model',width:150},
	                  { text: '主机IP', datafield: 'hostIp',width:100},
	                  { text: 'CPU总量', datafield: 'cpuNumber',cellsalign:'right',width:100},
	                  { text: 'CPU使用率', datafield: 'hostCpuUsage',cellsalign:'right',width:100},
	                  { text: '内存总量(GB)', datafield: 'memorySizeGb',cellsalign:'right',width:100},
	                  { text: '内存使用率', datafield: 'hostMemoryUsage',cellsalign:'right',width:100},
	                  { text: '操作系统', datafield: 'hostOsTypeName'},
	                  { text: '操作', width:80,  datafield: '',align:'center',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
 			        	  return "<div class='customButton' style='margin-top:4px;margin-left:10px;' onclick='viewOtherHostMonitorInfo("+row+")'>详情</div>";
 			             }
 			          }
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addHost = $("<div><a id='addOtherHost' onclick='popAddX86HostWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editHost = $("<div><a id='editOtherHost' onclick='popEditX86HostInOtherWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var removeHost = $("<div><a id='removeOtherHost' onclick='removeOtherHost()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
	                  var refreshBtn = $("<div><a id='jqxRefreshOtherHostBtn' onclick='refreshOtherHostGrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
	                  var exportBtn = $("<div><a id='jqxExportOtherHostBtn' onclick='exportOtherHostDatagrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-export'></i>导出&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  if("DC"==resTopologyType){
	                	  container.append(refreshBtn);
		                  container.append(exportBtn);
		                  $("#jqxRefreshOtherHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
		       			  $("#jqxExportOtherHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
	                  }else{
	                	  container.append(addHost);
		                  container.append(editHost);
		                  container.append(removeHost);
		                  container.append(refreshBtn);
		                  container.append(exportBtn);
		                  $("#jqxRefreshOtherHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
		       			  $("#jqxExportOtherHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
		       			  $("#addOtherHost").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
		       			  $("#editOtherHost").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
		       			  $("#removeOtherHost").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
	                  }
	                 
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#otherhostdatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#otherhostdatagrid').jqxGrid('getrowdata', index);
	    		  if(null!=data){
	    			  $("#editOtherHost").jqxButton({ disabled: false });
	    			  $("#removeOtherHost").jqxButton({ disabled: false });
	    		  }
	          });
	    };
	  
  };

  // 查询主机
  function searchOtherHost(){
	  
	  var host = new virtualOtherHostDatagridModel();
	  host.searchObj["qm.hostNameLike"] = $("#search-other-host-name").val();
	  host.searchObj["qm.hostOsType"] = $("#search-other-host-os-type").val();
	  host.searchObj["qm.status"] = $("#search-other-host-status").val()=="全部"?"":$("#search-other-host-status").val(); 
	  host.searchHostInfo();
  }
  
  // 跳转到主机详情页面
  function goOtherHostDetailPage(row){
	  // 通过Grid的index获得主机信息
	  var data = $('#otherhostdatagrid').jqxGrid('getrowdata', row);
	  
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
  
  //监控信息
  function viewOtherHostMonitorInfo(row){
	  var data = $('#otherhostdatagrid').jqxGrid('getrowdata', row);
	  window.parent.addHostParentTabs(data.resHostSid,data.monitorNodeId,data.hostName); 
  }
  
  // 导出主机列表
  function exportOtherHostDatagrid(){
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
	  window.location = ws_url + "/rest/host/exportOtherHost/" + params; 
	  
  }
  
  // 刷新主机列表
  function refreshOtherHostGrid(){
	  var host = new virtualOtherHostDatagridModel();
	  host.searchObj["qm.hostNameLike"] = $("#search-other-host-name").val();
	  host.searchObj["qm.hostOsType"] = $("#search-other-host-os-type").val();
	  host.searchObj["qm.status"] = $("#search-other-host-status").val()=="全部"?"":$("#search-other-host-status").val(); 
	  host.searchHostInfo();
  }
  
  // 弹出编辑window
  function popEditOtherHostWindow(){
	  	var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
	  	hostconfig.getCommonCode("edit-status","HOST_STATUS");
	  	hostconfig.getCommonCode("edit-hostOsType","HOST_OS_TYPE");
	  	hostconfig.getCommonCode("edit-cpuType","CPU_TYPE");
	  	hostconfig.getCommonCode("edit-isViosFlag","IS_VIOS_FLAG");
	  	// 检查datagrid是否选中了
	  	var rowindex = $('#otherhostdatagrid').jqxGrid('getselectedrowindex');
	  	var ok =  $("#editOtherHost").jqxButton("disabled");
	  	if(rowindex >= 0 && !ok){
	  		var data = $('#otherhostdatagrid').jqxGrid('getrowdata', rowindex);
	  		// 初始化值主机值
	  		$("#edit-hostName").val(data.hostName);
	    	$("#edit-hostType").val(data.hostType);
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
	  		
	  		if("VE" == resTopologyType){
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
  
  // 删除主机
  function removeOtherHost(){
	  var rowindex = $('#otherhostdatagrid').jqxGrid('getselectedrowindex');
	  if(rowindex >= 0){
		  var data = $('#otherhostdatagrid').jqxGrid('getrowdata', rowindex);
		  Core.confirm({
			  title:"提示",
			  message:"确定要删除该主机吗?",
			  confirmCallback:function(){
				  Core.AjaxRequest({
			            url : ws_url + "/rest/phycomputes/delete/server/"+data.resHostSid,
			            type:"get",
			            callback : function (data) {
			               var x86Host = new virtualOtherHostDatagridModel();
			               x86Host.searchHostInfo();
			            }
			      });
			  }
		  });
	  }
  }
  
  function popEditX86HostInOtherWindow(){
	 	var editX86host = new editX86HostModel();
	    editX86host.initInput();
	    
	    var rowindex = $('#otherhostdatagrid').jqxGrid('getselectedrowindex');
		var ok =  $('#editOtherHost').jqxButton('disabled');
		if(!ok && rowindex >= 0){
			  var data = $('#otherhostdatagrid').jqxGrid('getrowdata', rowindex);
			  x86HostName = data.hostName;
			  x86HostIp = data.hostIp;
			  $("#edit-phyhost-hostName").val(data.hostName);
		      $("#edit-phyhost-hostIp").val(data.hostIp);
		      $("#edit-phyhost-hostOsType").val(data.hostOsType);
		      $("#edit-phyhost-cpuNumber").val(data.cpuNumber);
		      $("#edit-phyhost-cpuType").val(data.cpuType);
		      $("#edit-phyhost-cpuCores").val(data.cpuCores);
		      $("#edit-phyhost-memorySize").val(data.memorySize);
		      $("#edit-phyhost-managementUser").val(data.managementUser);
		      $("#edit-phyhost-managementPwd").val(data.managementPwd);
		      $("#edit-phyhost-equipCategory").val(data.equipCategory);
		      $("#edit-phyhost-equipType").val(data.equipType);

		      $("#edit-phyhost-name").val(data.name);
		      $("#edit-phyhost-brand").val(data.brand);
		      $("#edit-phyhost-model").val(data.model);
		      $("#edit-phyhost-serialNumber").val(data.serialNumber);
		      $("#edit-phyhost-locationNumber").val(data.locationNumber);
		      $("#edit-phyhost-equipRoomSid").val(data.equipRoomSid);
		      $("#edit-phyhost-equipCabinetSid").val(data.equipCabinetSid);
		      $("#edit-phyhost-equipRackSid").val(data.equipRackSid);
		      $("#edit-phyhost-resTopologySid").val(data.resTopologySid);
		      $("#edit-phyhost-description").val(data.description);
		      $("#edit-phyhost-remoteMgtIp1").val(data.remoteMgtIp1);
		      $("#edit-phyhost-remoteMgtIp2").val(data.remoteMgtIp2);
		      $("#edit-phyhost-relevanceIp").val(data.relevanceIp);
		      $("#edit-phyhost-remoteMgtUser").val(data.remoteMgtUser);
		      $("#edit-phyhost-remoteMgtPwd").val(data.remoteMgtPwd);
		      
		      $("#edit-pve-resHostSid").val(data.resHostSid);
		      
		      var equipSid = data.resHostSid;
		      Core.AjaxRequest({
			      url : ws_url + "/rest/phycomputes/findMaintenanceServer/"+equipSid,
			      type : "get",
			      async : false,
			      callback : function(result) {
			    	  $("#edit-phyhost-purchaseDate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
                	  $("#edit-phyhost-startdate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
				        if(null==result){
				            $("#edit-phyhost-maintCompany").val("");
				            $("#edit-phyhost-maintTel").val("");
				            $("#edit-phyhost-purchaseDate").val("");
				            $("#edit-phyhost-startEndDate").val("");
				            $("#edit-phyhost-spec").val("");
				            $("#edit-phyhost-description").val("");
				            $("#edit-phyhost-warrantyPeriod").val("");
				            $("#edit-phyhost-equipSupplier").val("");
				        }else{
				            $("#edit-phyhost-maintCompany").val(result.maintCompany);
				            $("#edit-phyhost-maintTel").val(result.maintTel);
				            $("#edit-phyhost-purchaseDate").jqxDateTimeInput('val', result.purchaseDate);
		                	$("#edit-phyhost-startdate").jqxDateTimeInput('val',result.startEndDate);
				            $("#edit-phyhost-spec").val(result.spec);
				            $("#edit-phyhost-description").val(result.description);
				            $("#edit-phyhost-warrantyPeriod").val(result.warrantyPeriod);
				            $("#edit-phyhost-equipSupplier").val(result.equipSupplier);
				        } 
		          }
		      });
		      
		      var windowW = $(window).width();
			  var windowH = $(window).height(); 
			  $("#editX86HostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-560)/2 } });
			  $("#editX86HostWindow").jqxWindow('open');
		}
	   
}
 