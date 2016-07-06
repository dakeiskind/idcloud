var hostConfigModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		hostName: "", 
	    		manageStatus:"",
	    		usageStatus:""
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
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	    	$("#search-host-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	    	
	    	var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
	    	hostconfig.getCommonCode("search-host-mgt-status","MANAGEMENT_STATUS",true);
	    	hostconfig.getCommonCode("search-host-usage-status","USAGE_STATUS",true);
	        $("#search-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	    	
	    };
	    
	    // 给主机编辑画面赋值
	    this.setEdithostForm = function(data){
	    	$("#edit-hostName").val(data.hostName);
        	$("#edit-fullName").val(data.fullName);
        	$("#edit-hostType").val(data.hostType);
        	$("#edit-clusterName").val(data.clusterName);
        	$("#edit-rackNumber").val(data.rackNumber);
        	$("#edit-cageEnclosure").val(data.cageEnclosure);
        	$("#edit-uuid").val(data.uuid);
        	$("#edit-vendor").val(data.vendor);
        	$("#edit-dataCenter").val(data.dataCenter);
        	
        	$("#edit-hostIp").val(data.hostIp);
        	$("#edit-memorySize").val(data.memorySize);
        	$("#edit-cpuNumber").val(data.cpuNumber);
        	$("#edit-cpuCores").val(data.cpuCores);
        	$("#edit-cpuGhz").val(data.cpuGhz);
        	$("#edit-cpuType").val(data.cpuType);
        	
        	$("#edit-virtualPlatformType").val(data.virtualPlatformType);
        	$("#edit-hostOsType").val(data.hostOsType);

	    };
	    
	    // 给主机详情赋值
	    this.setdetailhostForm = function(data){
	    	$("#detail-hostName").html(data.hostName);
        	$("#detail-fullName").html(data.fullName);
        	$("#detail-hostType").html(data.hostType);
        	$("#detail-clusterName").html(data.clusterName);
        	$("#detail-rackNumber").html(data.rackNumber);
        	$("#detail-cageEnclosure").html(data.cageEnclosure);
        	$("#detail-uuid").html(data.uuid);
        	$("#detail-vendor").html(data.vendor);
        	$("#detail-dataCenter").html(data.dataCenter);
        	
        	$("#detail-hostIp").html(data.hostIp);
        	$("#detail-memorySize").html(data.memorySize);
        	$("#detail-cpuNumber").html(data.cpuNumber);
        	$("#detail-cpuCores").html(data.cpuCores);
        	$("#detail-cpuGhz").html(data.cpuGhz);
        	$("#detail-cpuType").html(data.cpuType);
        	
        	$("#detail-virtualPlatformType").html(data.virtualPlatformType);
        	$("#detail-hostOsType").html(data.hostOsType);

	    };
	    
	    // 给加入平台页面赋值
	    this.setplatformhostForm = function(data){
	   
	    	$("#platform-hostName").val(data.ipaddress);
        	$("#platform-fullName").val(data.hostName);
        	$("#platform-hostType").val(data.productname);
      
        	$("#platform-uuid").val(data.uuid);
        	$("#platform-vendor").val(data.manufacturer);
        	
        	
        	$("#platform-hostIp").val(data.ipaddress);
        
        	$("#platform-cpuNumber").val(data.physicalprocessorcount);
        	$("#platform-cpuCores").val(data.processorcount);
        	$("#platform-cpuGhz").val(data.processorcountArray.processor0.substring(data.processorcountArray.processor0.length-7,data.processorcountArray.processor0.length-3));
        	$("#platform-cpuType").val(data.processorcountArray.processor0.substring(0,21));
        	
//        	$("#platform-virtualPlatformType").html(data.virtualPlatformType);
//        	$("#platform-hostOsType").html(data.hostOsType);

	    };
	    
	    // 初始化验证规则   
	    this.initValidator = function(){
	    	$('#addHostForm').jqxValidator({
                rules: [  
                          { input: '#add-hostName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-hostName', message: '主机名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          
                          { input: '#add-hostIp', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-hostIp', message: '主机IP不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          
                          { input: '#add-memorySize', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-memorySize', message: '内存大小不能超过10个字符!', action: 'keyup, blur', rule: 'length=1,10' },
                          
                          { input: '#add-cpuNumber', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-cpuNumber', message: 'CPU个数不能超过4个字符!', action: 'keyup, blur', rule: 'length=1,4' },
                          
                          { input: '#add-cpuCores', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-cpuCores', message: 'CPU核数不能超过4个字符!', action: 'keyup, blur', rule: 'length=1,4' },
                       ]
	    	});
	    	$('#editHostForm').jqxValidator({
                rules: [  
                          { input: '#edit-hostName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-hostName', message: '主机名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          
                          { input: '#edit-hostIp', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-hostIp', message: '主机IP不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          
                          { input: '#edit-memorySize', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-memorySize', message: '内存大小不能超过10个字符!', action: 'keyup, blur', rule: 'length=1,10' },
                          
                          { input: '#edit-cpuNumber', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-cpuNumber', message: 'CPU个数不能超过4个字符!', action: 'keyup, blur', rule: 'length=1,4' },
                          
                          { input: '#edit-cpuCores', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-cpuCores', message: 'CPU核数不能超过4个字符!', action: 'keyup, blur', rule: 'length=1,4' },
                       ]
	    	});
	    	
	    	// 加入平台
	    	$('#addToPlatformForm').jqxValidator({
                rules: [  
                          { input: '#platform-hostName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#platform-hostName', message: '主机名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          
                          { input: '#platform-hostIp', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#platform-hostIp', message: '主机IP不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          
                          { input: '#platform-memorySize', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#platform-memorySize', message: '内存大小不能超过10个字符!', action: 'keyup, blur', rule: 'length=1,10' },
                          
                          { input: '#platform-cpuNumber', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#platform-cpuNumber', message: 'CPU个数不能超过4个字符!', action: 'keyup, blur', rule: 'length=1,4' },
                          
                          { input: '#platform-cpuCores', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#platform-cpuCores', message: 'CPU核数不能超过4个字符!', action: 'keyup, blur', rule: 'length=1,4' },
                       ]
	    	});
	    	
	    	// 新增主机验证成功
	    	$('#addHostForm').on('validationSuccess', function (event) {
		    		 var host = Core.parseJSON($("#addHostForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/hosts/create",
		 				params :host,
		 				callback : function (data) {
		 					me.searchHostConfigInfo();
 							$("#addHostWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	$("#addHostWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
	    	
	    	// 编辑主机验证成功
	    	$('#editHostForm').on('validationSuccess', function (event) {
	    		 var vlans = Core.parseJSON($("#editHostForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/hosts/update",
	 				params :vlans,
	 				callback : function (data) {
	 					me.searchHostConfigInfo();
						$("#editHostWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editHostWindow").jqxWindow('close');
	 			    }
	 			});
   	         });
	    	
	    	// 加入平台
	    	$('#addToPlatformForm').on('validationSuccess', function (event) {
		    		 var host = Core.parseJSON($("#addToPlatformForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/hosts/create",
		 				params :host,
		 				callback : function (data) {
		 					me.searchHostConfigInfo();
 							$("#addToPlatformWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	$("#addToPlatformWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
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
	                  { text: '主机名称', datafield: 'hostName',width:200},
	                  { text: '主机型号', datafield: 'hostType',width:80},
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
	                  var addHost = $("<div><a id='addHost' data-bind='click:addHostInfo'>&nbsp;&nbsp;<i class='icon-plus'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editHost = $("<div><a id='editHost' style='margin-left:-1px' data-bind='click:editHostInfo'>&nbsp;&nbsp;<i class='icon-pencil'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var removeHost = $("<div><a id='removeHost' data-bind='click:removeHostInfo' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-cancel'></i>删除&nbsp;&nbsp;</a></div>");
	                  var detailHost = $("<div><a id='detailHost' data-bind='click:detailHostInfo' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-cancel'></i>详情&nbsp;&nbsp;</a></div>");
	                  var runStatusHost = $("<div><a id='runStatusHost' data-bind='click:monitorHostInfo' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-cancel'></i>运行状态&nbsp;&nbsp;</a></div>");
	                  var mountVolume = $("<div><a id='mountVolume' data-bind='click:mountStorageHostInfo' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-cancel'></i>挂载存储&nbsp;&nbsp;</a></div>");
	                  var findHost = $("<div><a id='findHost' data-bind='click:hostDiscoveryDeploymentInfo' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-cancel'></i>发现主机部署&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addHost);
	                  container.append(editHost);
	                  container.append(removeHost);
	                  container.append(detailHost);
	                  container.append(runStatusHost);
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
		  		  $("#runStatusHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
		  		  $("#mountVolume").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
		  		  $("#findHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	          });
	    	  $("#addHost").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
	  		  $("#editHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	  		  $("#removeHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	  		  $("#detailHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	  		  $("#runStatusHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	  		  $("#mountVolume").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	  		  $("#findHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	  		  
	  		 // 初始化已挂载存储
    		 $("#mountedStorageDatagrid").jqxGrid({
  	              width: "100%",
  				  theme:currentTheme,
  	              columnsresize: true,
  	              autoheight: true,
  	              autowidth: false,
  	              sortable: true,
  	              selectionmode:"checkbox",
  	              localization: gridLocalizationObj,
  	              columns: [
  	                  { text: '存储名称', datafield: 'volumeName'},
  	                  { text: '存储类型', datafield: 'volumeTypeName'},
  	                  { text: '硬盘类型', datafield: 'hardDiskTypeName',width:60},
  	                  { text: '存储类别', datafield: 'storageCategoryName',width:60},
  	                  { text: '存储用途', datafield: 'storagePurposeName',width:60},
  	                  { text: '总容量(GB)', datafield: 'availableCapacity',width:80},
  	                  { text: '使用状态', datafield: 'usageStatusName',width:60}
  	              ] ,
  	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addMountStorage = $("<div><a id='addMountStorage'  data-bind='click:addHostStorage'>&nbsp;&nbsp;<i class='icon-plus'></i>新增存储&nbsp;&nbsp;</a></div>");
	                  var editMountStorage = $("<div><a id='editMountStorage' data-bind='click:removeHostStorage' style='margin-left:-1px' >&nbsp;&nbsp;<i class='icon-pencil'></i>卸载存储&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addMountStorage);
	                  container.append(editMountStorage);
	              }
  	          });
    		 
    		 $("#mountedStorageDatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#mountedStorageDatagrid').jqxGrid('getrowdata', index);
	   			  $("#editMountStorage").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	          });
	    	  $("#addMountStorage").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
	  		  $("#editMountStorage").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	  		  
	  		// 初始化可挂载datagrid
	   		 $("#addHostStorageDatagrid").jqxGrid({
	   	              width: "100%",
	   				  theme:currentTheme,
	   	              columnsresize: true,
	   	              autoheight: true,
	   	              autowidth: false,
	   	              sortable: true,
	   	              selectionmode:"checkbox",
	   	              localization: gridLocalizationObj,
	   	              columns: [
	   	                  { text: '存储名称', datafield: 'volumeName'},
	   	                  { text: '硬盘类型', datafield: 'hardDiskTypeName',width:60},
	   	                  { text: '存储类别', datafield: 'storageCategoryName',width:60},
	   	                  { text: '存储用途', datafield: 'storagePurposeName',width:60},
	   	                  { text: '总容量(GB)', datafield: 'availableCapacity',width:80},
	   	                  { text: '使用状态', datafield: 'usageStatusName',width:60}
	   	              ] 
	   	       });
	   		 
	   	// 初始化发现主机部署
    		 $("#hostDiscoveryDeploymentDatagrid").jqxGrid({
  	              width: "100%",
  				  theme:currentTheme,
  	              columnsresize: true,
  	              pageable: true, 
	              pagesize: 5, 
  	              autoheight: true,
  	              autowidth: false,
  	              sortable: true,
  	              localization: gridLocalizationObj,
  	              columns: [
  	                  { text: '节点', datafield: 'macAddress'},
  	                  { text: '厂商', datafield: 'manufacturer'},
  	                  { text: '产品名称', datafield: 'productname'},
  	                  { text: '序列号', datafield: 'serialnumber'},
  	                  { text: 'CPU个数', datafield: 'physicalprocessorcount',width:70},
  	                  { text: 'CPU总核数', datafield: 'processorcount',width:70},
  	                  { text: '操作系统', datafield: 'osname'},
  	                  { text: '状态', datafield: 'status'}
  	              ] ,
  	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var detailDiscoveryHost = $("<div><a id='detailDiscoveryHost'  data-bind='click:discoveryHostDetailInfo'>&nbsp;&nbsp;<i class='icon-plus'></i>详情&nbsp;&nbsp;</a></div>");
	                  var deploymentSystem = $("<div><a id='deploymentSystem' data-bind='click:deploymentSystemInfo' style='margin-left:-1px' >&nbsp;&nbsp;<i class='icon-pencil'></i>部署系统&nbsp;&nbsp;</a></div>");
	                  var joinPlatform = $("<div><a id='joinPlatform'  data-bind='click:addToPlatformInfo' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-plus'></i>加入平台&nbsp;&nbsp;</a></div>");
	                  var deleteDiscoveryHost = $("<div><a id='deleteDiscoveryHost' data-bind='click:removeDiscoveryHost' style='margin-left:0px' >&nbsp;&nbsp;<i class='icon-pencil'></i>删除&nbsp;&nbsp;</a></div>");
	                  var discoveryHostRefresh = $("<div><a id='discoveryHostRefresh' data-bind='click:refreshDiscoveryHost' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-plus'></i>刷新&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(detailDiscoveryHost);
	                  container.append(deploymentSystem);
	                  container.append(joinPlatform);
	                  container.append(deleteDiscoveryHost);
	                  container.append(discoveryHostRefresh);
	              }
  	          });
    		 
    		 $("#hostDiscoveryDeploymentDatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getrowdata', index);
	    		  
	    		  if(data.status == "已部署系统"){
	    			  $("#detailDiscoveryHost").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
			  		  $("#deploymentSystem").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
			  		  $("#joinPlatform").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false});
			  		  $("#deleteDiscoveryHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	    		  }else if(data.status == "加入平台"){
	    			  $("#detailDiscoveryHost").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
			  		  $("#deploymentSystem").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
			  		  $("#joinPlatform").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true});
			  		  $("#deleteDiscoveryHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	    		  }else if(data.status == "未部署系统"){
	    			  $("#detailDiscoveryHost").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
			  		  $("#deploymentSystem").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
			  		  $("#joinPlatform").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled:true });
			  		  $("#deleteDiscoveryHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	    		  }else if(data.status == "部署系统中"){
	    			  $("#detailDiscoveryHost").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
			  		  $("#deploymentSystem").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
			  		  $("#joinPlatform").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true});
			  		  $("#deleteDiscoveryHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	    		  }
	    		 
	          });
	    	  $("#detailDiscoveryHost").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
	  		  $("#deploymentSystem").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	  		  $("#joinPlatform").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
	  		  $("#deleteDiscoveryHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	  		  $("#discoveryHostRefresh").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
	    };
	
	    // 初始化弹出window
	    this.initPopWindow = function(){
	    	// 新增window
	    	$("#addHostWindow").jqxWindow({
                width: 800, 
                height:338,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addhostCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
                	$("#add-hostName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-fullName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-hostType").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-clusterName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-rackNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-cageEnclosure").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-uuid").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-vendor").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-dataCenter").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	
                	$("#add-hostIp").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-memorySize").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-cpuNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-cpuCores").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-cpuGhz").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-cpuType").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	
                	var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
        	    	hostconfig.getCommonCode("add-virtualPlatformType","VIRTUAL_PLATFORM_TYPE");
        	    	hostconfig.getCommonCode("add-hostOsType","OS_TYPE");
        	    	
        	    	$("#addhostSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
        	    	$("#addhostCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                }
            });
	    	// 编辑window
	    	$("#editHostWindow").jqxWindow({
                width: 800, 
                height:428,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#edithostCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
                	$("#edit-hostName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#edit-fullName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#edit-hostType").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#edit-clusterName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#edit-rackNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#edit-cageEnclosure").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#edit-uuid").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#edit-vendor").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#edit-dataCenter").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	
                	$("#edit-hostIp").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#edit-memorySize").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#edit-cpuNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#edit-cpuCores").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#edit-cpuGhz").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#edit-cpuType").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	
                	var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
        	    	hostconfig.getCommonCode("edit-virtualPlatformType","VIRTUAL_PLATFORM_TYPE");
        	    	hostconfig.getCommonCode("edit-hostOsType","HOST_OS_TYPE");
        	    	
        	    	$("#getMonitorNode").jqxButton({ width: '80',theme:currentTheme,height: '25px'});
        	    	
        	    	$("#edithostSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
        	    	$("#edithostCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                }
            });
	    	
	    	// 主机详情
	    	$("#detailHostWindow").jqxWindow({
                width: 800, 
                height:338,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#detailhostCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	    	
        	    	$("#detailhostCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                }
            });
	    	
	    	// 监控信息
	    	$("#monitorHostWindow").jqxWindow({
                width: 700, 
                height:400,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $(""), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	    	
                }
            });
	    	
	    	// 挂载存储window
	    	$("#mountStorageHostWindow").jqxWindow({
                width: 700, 
                height:326,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#mountStorageCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化组件
                	 $("#mountStorageCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px',});
                }
            });
	    	
	    	// 添加新挂载存储window
	    	$("#addHostStorageWindow").jqxWindow({
                width: 500, 
                height:250,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addHostStorageCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化组件
                	 $("#addHostStorageSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                	 $("#addHostStorageCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px',});
                }
            });
	    	
	    	// 发现主机部署window
	    	$("#hostDiscoveryDeploymentWindow").jqxWindow({
	    		width:800, 
	            height:280,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false,  
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化组件
                }
            });
	    	
	    	
	    	// 发现主机部署的主机详情
	    	$("#discoveryDeploymentDetailWindow").jqxWindow({
	    		width:750, 
	            height:300,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false,  
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化组件
                }
            });
	    	
	    	// 给发现主机部署系统
	    	$("#DeploymentSystemWindow").jqxWindow({
	    		width:300, 
	            height:100,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false,  
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化组件
                	$("#deploymentSystemSave").jqxButton({ width: '50',theme:currentTheme,height: '25px',});
                	var hostconfig = new codeModel({width:140,autoDropDownHeight:true});
        	    	hostconfig.getAttribute1CommonCode("hostOsType","HOST_OS_TYPE");
                }
            });
	    	
	    	// 加入平台
	    	$("#addToPlatformWindow").jqxWindow({
                width: 800, 
                height:338,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#platformhostCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
                	$("#platform-hostName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#platform-fullName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#platform-hostType").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#platform-clusterName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#platform-rackNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#platform-cageEnclosure").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#platform-uuid").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#platform-vendor").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#platform-dataCenter").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	
                	$("#platform-hostIp").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#platform-memorySize").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#platform-cpuNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#platform-cpuCores").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#platform-cpuGhz").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#platform-cpuType").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	
                	var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
        	    	hostconfig.getCommonCode("platform-virtualPlatformType","VIRTUAL_PLATFORM_TYPE");
        	    	hostconfig.getCommonCode("platform-hostOsType","OS_TYPE");
        	    	
        	    	$("#platformhostSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
        	    	$("#platformhostCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                }
            });
	    };
  };
  