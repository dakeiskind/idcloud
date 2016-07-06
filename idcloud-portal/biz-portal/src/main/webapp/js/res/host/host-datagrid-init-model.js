 var hostConfigModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		hostName: "", 
	    		manageStatus:"",
	    		usageStatus:"",
	    		isResPoolSearch:resTopologyType,
	    		resTopologySid:resTopologySid
		};
	    // 给datagrid赋值
	    this.searchHostConfigInfo = function(){
	    	 
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/hosts",
	 			type:'post',
	 			params:me.searchObj,
	 			async:false,
	 			callback : function (data) {
	 				me.items(data);
	 				var sourcedatagrid ={
 			              datatype: "json",
 			              localdata: me.items
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#hostConfigMgtdatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    this.getDataGridData = function(){
	    	 var hostData;
	    	 Core.AjaxRequest({
		 			url : ws_url + "/rest/hosts",
		 			type:'post',
		 			params:me.searchObj,
		 			async:false,
		 			callback : function (data) {
		 				hostData = data;
		 			}
		     });
	    	 return hostData;
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	    	$("#search-host-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	    	
	    	var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
	    	hostconfig.getCommonCode("search-host-mgt-status","MANAGEMENT_STATUS",true);
	    	hostconfig.getCommonCode("search-host-usage-status","USAGE_STATUS",true);
	        $("#search-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	    	
	    };
	    // 初始化用户datagrid的数据源
	    this.initHostDatagrid = function(){
	          $("#hostConfigMgtdatagrid").jqxGrid({
	              width: "100%",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '主机名称', datafield: 'hostName',width:100},
	                  { text: '主机型号', datafield: 'hostType',width:150},
	                  { text: 'CPU(个)', datafield: 'cpuNumber',width:60},
	                  { text: 'CPU(核)', datafield: 'cpuCores',width:60},
	                  { text: '内存(MB)', datafield: 'memorySize',width:80},
	                  { text: 'IP地址', datafield: 'hostIp'},
	                  { text: '操作系统', datafield: 'hostOsTypeName'},
	                  { text: '虚拟类型', datafield: 'virtualPlatformType'},
	                  { text: '监控状态', datafield: 'monitorStatusName'},
	                  { text: '管理状态', datafield: 'manageStatusName'},
	                  { text: '所属资源池', datafield: 'resourcePoolName'},
	                  { text: '使用状态', datafield: 'usageStatusName'}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addHost = $("<div><a id='addHost' onclick='javascript:popAddHostWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editHost = $("<div><a id='editHost' onclick='javascript:popEditHostWindow()' style='margin-left:-1px' >&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var removeHost = $("<div><a id='removeHost' onclick='removeHost()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
	                  var detailHost = $("<div><a id='detailHost' onclick='detailHost()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>详情&nbsp;&nbsp;</a></div>");
	                  //var runStatusHost = $("<div><a id='runStatusHost' onclick='monitorHostWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-cancel'></i>运行状态&nbsp;&nbsp;</a></div>");
	                  var mountVolume = $("<div><a id='mountVolume' onclick='popMountStorageHost()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-database'></i>挂载存储&nbsp;&nbsp;</a></div>");
	                  var findHost = $("<div><a id='findHost' onclick='popFindDeploymentHost()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-retweet-1'></i>主机发现部署&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addHost);
	                  container.append(editHost);
	                  container.append(removeHost);
	                  container.append(detailHost);
	                  //container.append(runStatusHost);
	                  container.append(mountVolume);
	                  container.append(findHost);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#hostConfigMgtdatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', index);
	   			  $("#editHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	   			  $("#removeHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	   			  $("#detailHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
		  		  //$("#runStatusHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
		  		  $("#mountVolume").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
		  		  $("#findHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	          });
	    	  $("#addHost").jqxButton({width: '80',theme:currentTheme,height: '18px', disabled: false });
	  		  $("#editHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	  		  $("#removeHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	  		  $("#detailHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	  		  //$("#runStatusHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	  		  $("#mountVolume").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	  		  $("#findHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	    };
	    
	    // 主机资源统计
	    this.HostResourcesStatistics = function(){
			var hostData =  me.getDataGridData();
			
			var data = new Object();
			data.hostCount = hostData.length;
			data.attr = new Array();
			
			var value = [0,0,0,0];
			var name =["正常","离线","故障","其他"];
			for(var i=0;i<hostData.length;i++){
				
				if("01" == hostData[i].hostStatus){
					// 正常
					value[0] += 1; 
				}else if("02" == hostData[i].hostStatus){
					// 离线
					value[1] += 1; 
				}else if("03" == hostData[i].hostStatus){
					// 故障
					value[2] += 1; 
				}else if("04" == hostData[i].hostStatus){
					// 其他
					value[3] += 1; 
				} 
			}
			for(var i=0; i<4;i++){
				var obj = new Object();
				obj.name = name[i];
				obj.value = value[i];
				data.attr.push(obj);
			}
			return data;
	    };
  };
  
  // 查询主机列表
  function searchHostDatagrid(){
	  var host = new hostConfigModel();
	  host.searchObj.hostName = $("#search-host-name").val();
	  host.searchObj.manageStatus = $("#search-host-mgt-status").val()=="全部"?"":$("#search-host-mgt-status").val();
	  host.searchObj.usageStatus = $("#search-host-usage-status").val()=="全部"?"":$("#search-host-usage-status").val();
	  host.searchObj.isResPoolSearch = resTopologyType ;
	  host.searchObj.resTopologySid = resTopologySid ;
	  host.searchHostConfigInfo();
  }
  
  
  //弹出新增window
  function popAddHostWindow(){
	  	$("#add-host-parentTopologySid").val(resTopologySid);
	    var windowW = $(window).width(); 
	  	var windowH = $(window).height(); 
	  	$("#add-host-parentTopologySid").val(resTopologySid);
	  	$("#addHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-338)/2 } });
	  	$("#addHostWindow").jqxWindow('open');
  }
  
  // 弹出编辑window
  function popEditHostWindow(){
	      // 检查datagrid是否选中了
		  var rowindex = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
	  	  if(rowindex >= 0){
	  		var data = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
	  		// 给编辑画面赋值
	  		$("#resSidHost").val(data.resSid);
	  		var edithost = new editHostModel();
	  		edithost.setEdithostForm(data);
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
  }
  
  // 删除主机
  function removeHost(){
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
				 					var host = new hostConfigModel();
				 					host.searchHostConfigInfo();
				 			    },
				 			    failure:function(data){
				 			    	
				 			    }
				 			});
						}
				});
		}
  }
  
 // 查看主机详情
  function detailHost(){
	  var rowindex = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
  	  if(rowindex >= 0){
  		var data = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
  		// 给编辑画面赋值
  		var detailhost = new detailHostModel();
  		detailhost.setdetailhostForm(data);
  		
  		// 设置弹出框位置
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#detailHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-338)/2 } });
    	$("#detailHostWindow").jqxWindow('open');
  	}
  }
  
  // 弹出已挂载存储
  function popMountStorageHost(){
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
      	$("#mountStorageHosDatagridtWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-326)/2 } });
      	$("#mountStorageHosDatagridtWindow").jqxWindow('open');
  	  }
  }
  
  // 弹出主机部署window
  function popFindDeploymentHost(){
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
  }
  
  // 弹出监控信息
  function monitorHostWindow(){
	  var rowindex = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
      if(rowindex >= 0){
	  		var data = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
	  		// 设置弹出框位置
	  		var windowW = $(window).width(); 
	      	var windowH = $(window).height(); 
	      	$("#monitorHostWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-400)/2 } });
	      	$("#monitorHostWindow").jqxWindow('open');
  	  }
  }
  