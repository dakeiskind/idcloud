var virtualPveHostDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.detailsItems = ko.observableArray();
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
	    // 用户数据
	    this.searchHostInfo = function(){
	    	var dataAdapter = Core.getPagingDataAdapter({
				gridId: "pvehostdatagrid",
				url: ws_url + "/rest/host/findAll",
				params: me.searchObj
			});
	    	
	    	$("#pvehostdatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initHostInput = function(){
	    	// 初始化查询组件
	        $("#search-pve-host-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-pve-host-os-type").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-pve-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        // 初始化下拉列表框 
//			 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
//			 codesearch.getCommonCode("search-pve-host-status","HOST_STATUS",true);
	    };
	    
	    // 查询出power主机的CPU池
	    this.searchPveHostCpuPoolInfo = function(resHostSid){
	    	// cpu池赋值
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/cpuPools",
	 			type:'post',
	 			params:{
	 				resHostSid : resHostSid
	 			},
	 			async:false,
	 			callback : function (data) {
	 				var sourcedatagrid ={
			              datatype: "json",
			              localdata: data
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#pveHostCpuPoolDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    	
	    	// VIOS
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/vioss",
	 			type:'post',
	 			params:{
	 				resHostSid : resHostSid
	 			},
	 			async:false,
	 			callback : function (data) {
	 				var sourcedatagrid ={
			              datatype: "json",
			              localdata: data
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#pveHostViosDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    	
	    	// 虚拟交换机池赋值
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/vss/host",
	 			type:'post',
	 			params:{
	 				resHostSid : resHostSid
	 			},
	 			async:false,
	 			callback : function (data) {
	 				
	 				var sourcedatagrid ={
			              datatype: "json",
			              localdata: data
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#pveHostDvsDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    	
	    	// Phy IO
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/hostItems",
	 			type:'post',
	 			params:{
	 				resHostSid : resHostSid
	 			},
	 			async:false,
	 			callback : function (data) {
	 				var preData = [];
	 				var subData = [];
	 				// 拆分数据
	 				for(var i=0;i<data.length;i++){
	 					if(data[i].supHostItemId == null ||data[i].supHostItemId == ""){
	 						preData.push(data[i]);
	 					}else{
	 						subData.push(data[i]);
	 					}
	 				}
	 				
	 				var sourcedatagrid ={
			              datatype: "json",
			              localdata: preData
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#pveHostPhysicalDatagrid").jqxGrid({source: dataAdapter});
	 			    
	 			    // 子集赋值
	 			   var subdatagrid ={
	 			              datatype: "json",
	 			              localdata: subData
	  			    };
	  				 var subdataAdapter = new $.jqx.dataAdapter(subdatagrid, { autoBind: true });
	  			    detailsItems = subdataAdapter.records;
	 			}
	 		 });
	    };
	    
	    
	    // 初始化power主机相关信息的datagrid
	    this.initWithPowerHostDatagrid = function(){
	    	// cpu池
	    	$("#pveHostCpuPoolDatagrid").jqxGrid({
	              width: "99.8%",
	              height:152,
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: false, 
	              autoheight: false,
	              sortable: true,
	              selectionmode:"single",
	              localization: gridLocalizationObj,
	              columns: [
	            	  { text: '池名称', datafield: 'cpuPoolName'},
	                  { text: '预留值', datafield: 'reservedValue'},
	                  { text: '最大值', datafield: 'maxValue'}
	              ],
	              showtoolbar: false
	          });
	    	
	    	// 主机Vios池
	    	$("#pveHostViosDatagrid").jqxGrid({
	              width: "99.8%",
	              height:152,
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: false, 
	              autoheight: false,
	              sortable: true,
	              selectionmode:"single",
	              localization: gridLocalizationObj,
	              columns: [
	            	  { text: 'CPU使用量', datafield: 'powerCpuUnits'},
	                  { text: 'CPU使用核数', datafield: 'powerCpuCores'},
	                  { text: '管理用户名', datafield: 'user'},
	                  { text: '管理IP', datafield: 'ip'}
	              ],
	              showtoolbar: false
	          });
	    	
	    	// 虚拟交换机
	    	$("#pveHostDvsDatagrid").jqxGrid({
	              width: "99.8%",
	              height:152,
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: false, 
	              autoheight: false,
	              sortable: true,
	              selectionmode:"single",
	              localization: gridLocalizationObj,
	              columns: [
	            	  { text: '交换机名称', datafield: 'resVsName'},
	                  { text: 'VLAN_ID', datafield: 'vlanId'}
	              ],
	              showtoolbar: false
	          });
	    	
	    	/*------主机配件的详细列表------*/
	    	var nestedGrids = new Array();
	    	var initrowdetails = function (index, parentElement, gridElement, record) {
	    						
	    				var id = record.hostItemId.toString();
			            var grid = $($(parentElement).children()[0]);
			            nestedGrids[index] = grid;
			            var filtergroup = new $.jqx.filter();
			            var filtervalue = id;
			            var filtercondition = 'equal';
			            var filter = filtergroup.createfilter('stringfilter', filtervalue, filtercondition);
			            var detailsbyid = [];
			            for (var m = 0; m < detailsItems.length; m++) {
			                var result = filter.evaluate(detailsItems[m]["supHostItemId"]);
			                if (result)
			                	detailsbyid.push(detailsItems[m]);
			            }
			            var detailssource = { 
			            	datatype: "json",
			                localdata: detailsbyid
			            };
			            var nestedGridAdapter = new $.jqx.dataAdapter(detailssource);
			            if (grid != null) {
			            	grid.jqxGrid({
			        			source: nestedGridAdapter,
			        			width:"95%",
			        			autowidth: false,
			        			autoheight: true,
			        			columnsresize: true,
			        			rowsheight: 30,
			                    enablehover: false,
			        			theme:currentTheme,
			        			localization : gridLocalizationObj,
			        			columns: [
			        			          { text: 'SLOT', datafield: 'itemLocation'},
			        			          { text: '配件描述', datafield: 'hostItemDesc'},
			        			          { text: '所有者', datafield: 'owner'},
			        			          { text: 'Mac Address/WWPN', datafield: 'wwpn',width:180}
			        			         
			        			   ]
			        		});
			            }
		        };
	    	
	    	  // Phy IO
	    	  $("#pveHostPhysicalDatagrid").jqxGrid({
	              width: "99.8%",
	              height:152,
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: false, 
	              autoheight: false,
	              sortable: true,
	              rowdetails: true,
	              rowsheight: 35,
	              initrowdetails: initrowdetails,
	              rowdetailstemplate: { rowdetails: "<div id='grid' style='margin: 10px;'></div>", rowdetailsheight: 220, rowdetailshidden: true },
	              selectionmode:"single",
	              localization: gridLocalizationObj,
	              columns: [
	            	  { text: '配件类型', datafield: 'hostItemTypeCodeName'},
	                  { text: 'SLOT', datafield: 'itemLocation'},
	                  { text: '配件描述', datafield: 'hostItemDesc'}
	              ],
	              showtoolbar: false
	          });
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initHostDatagrid = function(){
	    	  
	    	  var dataAdapter = Core.getPagingDataAdapter({
					gridId: "pvehostdatagrid",
					url: ws_url + "/rest/host/findAll",
					params: me.searchObj
			  });
	    	
	          $("#pvehostdatagrid").jqxGrid({
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
					  { text: '主机名称', datafield: 'hostName',width:180, cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
						  return "<div style='padding-top:10px;'><a class='datagrid-link' onclick='goPveHostDetailPage("+row+")' href='#'>&nbsp;"+value+"</a></div>";
					  }},
	                  { text: '主机型号', datafield: 'model',width:140},
	                  { text: '主机IP', datafield: 'hostIp',width:120},
	                  { text: 'CPU总量', datafield: 'cpuNumber',cellsalign:'right',width:100},
	                  { text: '可用CPU', datafield: 'cpuAvailable',cellsalign:'right',width:100},
	                  { text: '内存总量(GB)', datafield: 'memorySizeGb',cellsalign:'right',width:100},
	                  { text: '可用内存(GB)', datafield: 'memoryAvailable',cellsalign:'right',width:100},
	                  { text: '操作系统', datafield: 'hostOsTypeName'},
	                  { text: '操作', width:80,  datafield: '',align:'center',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
 			        	  return "<div class='customButton' style='margin-top:4px;margin-left:10px;' onclick='viewPveHostMonitorInfo("+row+")'>详情</div>";
 			             }
 			          }
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addHost = $("<div><a id='addPveHost' onclick='popAddPowerHostWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editHost = $("<div><a id='editPveHost' onclick='popEditPowerHostWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var removeHost = $("<div><a id='removePveHost' onclick='removePowerHost()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
	                  var refreshBtn = $("<div><a id='jqxRefreshPveHostBtn' onclick='refreshPveHostGrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
	                  var exportBtn = $("<div><a id='jqxExportPveHostBtn' onclick='exportPveHostDatagrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-export'></i>导出&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  if("DC"==resTopologyType){
	                	  container.append(refreshBtn);
		                  container.append(exportBtn);
		                  $("#jqxRefreshPveHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
		       			  $("#jqxExportPveHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
	                  }else{
	                	  container.append(addHost);
		                  container.append(editHost);
		                  container.append(removeHost);
		                  container.append(refreshBtn);
		                  container.append(exportBtn);
		                  $("#jqxRefreshPveHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
		       			  $("#jqxExportPveHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
		       			  $("#addPveHost").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
		       			  $("#editPveHost").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
		       			  $("#removePveHost").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
	                  }
	                 
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#pvehostdatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#pvehostdatagrid').jqxGrid('getrowdata', index);
	    		  if(null!=data){
	    			  $("#editPveHost").jqxButton({ disabled: false });
	    			  $("#removePveHost").jqxButton({ disabled: false });
	    		  }
	          });
	    };
	  
  };

  // 查询主机
  function searchPveHost(){
	  
	  var host = new virtualPveHostDatagridModel();
	  host.searchObj["qm.hostNameLike"] = $("#search-pve-host-name").val();
	  host.searchObj["qm.hostOsType"] = $("#search-pve-host-os-type").val();
//	  host.searchObj["qm.status"] = $("#search-pve-host-status").val()=="全部"?"":$("#search-pve-host-status").val(); 
	  host.searchHostInfo();
  }
  
  // 跳转到主机详情页面
  function goPveHostDetailPage(row){
	  // 通过Grid的index获得主机信息
	  var data = $('#pvehostdatagrid').jqxGrid('getrowdata', row);
	  
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
  
  // 监控信息
  function viewPveHostMonitorInfo(row){
	  var data = $('#pvehostdatagrid').jqxGrid('getrowdata', row);
	  window.parent.addHostParentTabs(data.resHostSid,data.monitorNodeId,data.hostName); 
  }
  
  // 导出主机列表
  function exportPveHostDatagrid(){
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
	  window.location = ws_url + "/rest/host/exportPveHost/" + params; 
	  
  }
  
  // 刷新主机列表
  function refreshPveHostGrid(){
	  var host = new virtualPveHostDatagridModel();
	  host.searchObj["qm.hostNameLike"] = $("#search-pve-host-name").val();
	  host.searchObj["qm.hostOsType"] = $("#search-pve-host-os-type").val();
//	  host.searchObj["qm.status"] = $("#search-pve-host-status").val()=="全部"?"":$("#search-pve-host-status").val(); 
	  host.searchHostInfo();
  }
  
  // 弹出编辑window
  function popEditPveHostWindow(){
	  	var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
	  	hostconfig.getCommonCode("edit-status","HOST_STATUS");
	  	hostconfig.getCommonCode("edit-hostOsType","HOST_OS_TYPE");
	  	hostconfig.getCommonCode("edit-cpuType","CPU_TYPE");
	  	hostconfig.getCommonCode("edit-isViosFlag","IS_VIOS_FLAG");
	  	// 检查datagrid是否选中了
	  	var rowindex = $('#pvehostdatagrid').jqxGrid('getselectedrowindex');
	  	var ok =  $("#editPveHost").jqxButton("disabled");
	  	if(rowindex >= 0 && !ok){
	  		var data = $('#pvehostdatagrid').jqxGrid('getrowdata', rowindex);
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
  function removePowerHost(){
	  var rowindex = $('#pvehostdatagrid').jqxGrid('getselectedrowindex');
	  if(rowindex >= 0){
		  var data = $('#pvehostdatagrid').jqxGrid('getrowdata', rowindex);
		  Core.confirm({
			  title:"提示",
			  message:"确定要删除该主机吗?",
			  confirmCallback:function(){
				  Core.AjaxRequest({
			            url : ws_url + "/rest/phycomputes/delete/server/"+data.resHostSid,
			            type:"get",
			            callback : function (data) {
			               var powerHost = new virtualPveHostDatagridModel();
			               powerHost.searchHostInfo();
			            }
			      });
			  }
		  });
	  }
  }
 