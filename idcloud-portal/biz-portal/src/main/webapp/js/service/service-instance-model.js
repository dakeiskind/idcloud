// 服务实例js
var serviceInstanceModel = function () {
		var me = this;
	    this.itemsServiceInstance = ko.observableArray();
	    this.itemsServiceInstanceDetailStorage = ko.observableArray();
	    this.itemsServiceInstanceDetailResource = ko.observableArray();

	    // 用户数据
	    this.searchServiceInstance = function(){
	    	 var defaultSearch = {
	    			 orderIdLike : $("#orderIdLike").val(), 
	    			 instanceNameLike: $("#instanceNameLike").val(), 
	    			 dredgeFromDate : $("#dredgeFromDate").val(),
	    			 dredgeToDate : $("#dredgeToDate").val(), 
	    			 expiringFromDate : $("#expiringFromDate").val(), 
	    			 expiringToDate : $("#expiringToDate").val(), 
	    			 status: $("#status").val()=="全部"?"":$("#status").val(), 
	    			 serviceSid: $("#search-serviceSid").val()=="全部"?"":$("#search-serviceSid").val(),
	         };
	    	
	    	 Core.AjaxRequest({
	    		 url : ws_url + "/rest/serviceInstances/platform/searchServiceInstance",
	 			type:'post',
	 			params:defaultSearch,
	 			async:false,
	 			callback : function (data) {
	 			   me.itemsServiceInstance(data);
	 			   var sourcedatagrid ={
			              datatype: "json",
			              localdata: me.itemsServiceInstance
			       };
		    	   var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
		    	   $("#jqxgrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    this.setViewServiceInstanceDetail = function(data){
	    	 $("#orderId").html(data.orderId);
	    	 $("#instanceName").html(data.instanceName);
	    	 $("#serviceName").html(data.serviceName);
	    	 $("#dredgeDate").html(data.dredgeDate);
	    	 $("#expiringDate").html(data.expiringDate);
	    	 $("#tenantName").html(data.tenantName);
//	    	 $("#description").html(data.description);
	    	 
	    	 //配置信息
	    	 Core.AjaxRequest({
	    		url : ws_url + "/rest/serviceInstances/platform/searchServiceInstanceDetailConfig",
	 			type:'post',
	 			params:{
	 				    instanceSid : data.instanceSid
	 				},
	 			async:false,
	 			callback : function (data) {
	 				//alert(JSON.stringify(data));
	 				 $("#osTypeName").html(data.osTypeName);
	 		    	 $("#cpuCores").html(data.cpuCores);
	 		    	 $("#memorySize").html(data.memorySize);

	 			}
	 		 });
	    	 
	    	 //存储信息
	    	 Core.AjaxRequest({
		    		url : ws_url + "/rest/serviceInstances/platform/searchServiceInstanceDetailStorage",
		 			type:'post',
		 			params:{
		 				    instanceSid : data.instanceSid
		 				},
		 			async:false,
		 			callback : function (data) {
		 				 me.itemsServiceInstanceDetailStorage(data);
			 		     me.initServiceInstanceDetailStorageDatagrid(me.itemsServiceInstanceDetailStorage);

		 			}
		 		 });
	    	//资源实例信息
	    	 Core.AjaxRequest({
		    		url : ws_url + "/rest/serviceInstances/platform/searchServiceInstanceDetailResource",
		 			type:'post',
		 			params:{
		 				    instanceSid : data.instanceSid
		 				},
		 			async:false,
		 			callback : function (data) {
		 				 me.itemsServiceInstanceDetailResource(data);
			 		     me.initServiceInstanceDetailResourceDatagrid(me.itemsServiceInstanceDetailResource);

		 			}
		 		 });
	    };
	    
	    
	   // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initServiceInstanceInput = function(){
	    	// 初始化查询组件
	        $("#orderIdLike").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
	        $("#instanceNameLike").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
	        $("#search-button").jqxButton({ width: '50',height: 23, theme:currentTheme});
	        $("#dredgeFromDate").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
	        $("#dredgeToDate").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
	        $("#expiringFromDate").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
	        $("#expiringToDate").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
	        $("#cancel").jqxButton({ width: '50',height: 23, theme:currentTheme});

	        // 初始化下拉列表框 
			var codesearch = new codeModel({width:150,autoDropDownHeight:true});
			codesearch.getCommonCode("status", "SERVICE_INASTANCE_STATUS",true);
			codesearch.getCustomCode("search-serviceSid","/services/platform/findAllService","serviceName","serviceSid",true);

	    };
	    
	    // 初始化服务实例资源的数据源
	    this.initServiceInstanceDetailResourceDatagrid = function(data){
 	 
	    	 var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	 
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#vResourceGrid").jqxGrid(
	          {
	              width: "100%",
				  height:"70px",
				  theme:currentTheme,
	              source: dataAdapter,
	              columnsresize: true,
	              pageable: false, 
	              pagesize: 10, 
	              autoheight: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '资源实例名称', datafield: 'resInstanceName'},
	                  { text: '资源实例类型', datafield: 'resInstanceTypeName'},
	                  { text: '分配实例ID', datafield: 'allocateInstanceId' },
	                  { text: '资源池', datafield: 'resPoolName' }
	              ],
	              showtoolbar: false
	          });
	   };
	    
	    // 初始化服务实例存储的数据源
	    this.initServiceInstanceDetailStorageDatagrid = function(data){
 	 
	    	 var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	 
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#vDiskGrid").jqxGrid(
	          {
	              width: "100%",
				  height:"50px",
				  theme:currentTheme,
	              source: dataAdapter,
	              columnsresize: true,
	              pageable: false, 
	              pagesize: 10, 
	              autoheight: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '存储名称', datafield: 'resInstanceName'},
	                  { text: '存储用途', datafield: 'storagePurposeName'},
	                  { text: '存储大小', datafield: 'allocateDiskSize' }
	              ],
	              showtoolbar: false
	          });
	   };
	    
	    // 初始化用户datagrid的数据源
	    this.initServiceInstanceDatagrid = function(){
 	 
	          $("#jqxgrid").jqxGrid(
	          {
	              width: "98%",
				 // height:"99%",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '服务实例名称', datafield: 'instanceName'},
	                  { text: '订单号', datafield: 'orderId'},
	                  { text: '所属服务', datafield: 'serviceName' },
	                  { text: '计费类型', datafield: 'billingTypeName' },
	                  { text: '开通时间', datafield: 'dredgeDate' },
	                  { text: '到期时间', datafield: 'expiringDate' },
	                  { text: '所有者', datafield: 'ownerId' },
	                  { text: '所属租户', datafield: 'tenantName' },
	                  { text: '实例状态', datafield: 'statusName' }
	              ],
	              showtoolbar: true,
	              rendertoolbar: function (toolbar) {
	                  var me = this;
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var startBtn = $("<div><a id='startBtn' onclick='startItem()'>&nbsp;&nbsp;<i class='icons-blue icon-play'></i>启用&nbsp;&nbsp;</a></div>");
	                  var disableBtn = $("<div><a id='disableBtn' onclick='disableItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-block'></i>禁用&nbsp;&nbsp;</a></div>");
	                  var releaseBtn = $("<div><a id='releaseBtn' onclick='releaseItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel'></i>退订&nbsp;&nbsp;</a></div>");
	                  var viewBtn = $("<div><a id='viewBtn' onclick='viewItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>详情&nbsp;&nbsp;</a></div>");
       
	                  toolbar.append(container);
	                  container.append(startBtn);
	                  container.append(disableBtn);
	                  container.append(releaseBtn);
	                  container.append(viewBtn);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#jqxgrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#jqxgrid').jqxGrid('getrowdata', index);
	    		  if (data.status == "03") {
	    			  	//已开通
		   			  	$("#disableBtn").jqxButton({ width: '80',theme:currentTheme,disabled: false});
		   			  	$("#releaseBtn").jqxButton({ width: '100',theme:currentTheme,disabled: false});
		   			  	$("#startBtn").jqxButton({width: '80',theme:currentTheme, disabled: true });
		   			  	$("#viewBtn").jqxButton({ width: '80',theme:currentTheme,disabled: false});
					} else if (data.status == "05") {
						//禁用
						$("#disableBtn").jqxButton({ width: '80',theme:currentTheme,disabled: true});
			    		$("#releaseBtn").jqxButton({ width: '100',theme:currentTheme,disabled: false});
			    		$("#startBtn").jqxButton({width: '80',theme:currentTheme, disabled: false });
			   		  	$("#viewBtn").jqxButton({ width: '80',theme:currentTheme,disabled: false});
					} else if (data.status == "99") {
						//退订
						$("#disableBtn").jqxButton({ width: '80',theme:currentTheme,disabled: true});
			    		$("#releaseBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
			    		$("#startBtn").jqxButton({width: '80',theme:currentTheme, disabled: true });
			   		  	$("#viewBtn").jqxButton({ width: '80',theme:currentTheme,disabled: false});
					} else {
						$("#disableBtn").jqxButton({ width: '80',theme:currentTheme,disabled: true});
			    		$("#releaseBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
			    		$("#startBtn").jqxButton({width: '80',theme:currentTheme, disabled: true });
			   		  	$("#viewBtn").jqxButton({ width: '80',theme:currentTheme,disabled: false});
					}    		  
	          });
	    	  
	    	  $("#startBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
   			  $("#disableBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
   			  $("#releaseBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
   			  $("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
	    };
	    
	    // 初始化弹出window
	    this.initServiceInstancePopWindow = function(){
			$("#popupWindow").jqxWindow({
	                width: 950, 
	                height:470,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#cancel"), 
	                theme: currentTheme,
	                modalOpacity: 0.3           
	         });
	    };
	    
  };
  
  // 启用服务实例
  function startItem() {
		var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#startBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
			Core.confirm({
				title : "提示",
				message : "确定要启用该服务实例吗?",
				yes : "确定",
				confirmCallback : function() {
					Core.AjaxRequest({
						url : ws_url + "/rest/serviceInstance/serviceInstanceOperation",
						params : {
							serviceInstanceSid : data.instanceSid,
							action : "resume"
						},
						type : "post",
						callback : function(data) {
							Core.alert({
								title : "提示",
								message : "启用成功！",
								confirmCallback : function() {
									var serviceInstance = new serviceInstanceModel();
									serviceInstance.searchServiceInstance();
								}
							});
						},
						failure : function(data) {
							Core.alert({
								title : "提示",
								message : "启用失败！"
							});
						}
					});
				}
			});
		}
	}
  
  // 禁用服务实例
  function disableItem(){
	  var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#disableBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
			Core.confirm({
				title : "提示",
				message : "确定要禁用该服务实例吗?",
				yes : "确定",
				confirmCallback : function() {
					Core.AjaxRequest({
						url : ws_url + "/rest/serviceInstance/serviceInstanceOperation",
						params : {
							serviceInstanceSid : data.instanceSid,
							action : "suspend"
						},
						type : "post",
						callback : function(data) {
							Core.alert({
								title : "提示",
								message : "禁用成功！",
								confirmCallback : function() {
									var serviceInstance = new serviceInstanceModel();
									serviceInstance.searchServiceInstance();
								}
							});
						},
						failure : function(data) {
							Core.alert({
								title : "提示",
								message : "禁用失败！"
							});
						}
					});
				}
			});
		}
  }
  
  // 退订
  function releaseItem(){
	  var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#releaseBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
			Core.confirm({
				title : "提示",
				message : "确定要退订该服务实例吗?",
				yes : "确定",
				confirmCallback : function() {
					Core.AjaxRequest({
						url : ws_url + "/rest/serviceInstance/serviceInstanceOperation",
						params : {
							serviceInstanceSid : data.instanceSid,
							action : "release"
						},
						type : "post",
						callback : function(data) {
							Core.alert({
								title : "提示",
								message : "退订成功！",
								confirmCallback : function() {
									var serviceInstance = new serviceInstanceModel();
									serviceInstance.searchServiceInstance();
								}
							});
						},
						failure : function(data) {
							Core.alert({
								title : "提示",
								message : "退订失败！"
							});
						}
					});
				}
			});
		}
  }
  // 查看详情
  function viewItem(){
	  var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
		var ok = $("#viewBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
			$("#instanceSid").val(data.instanceSid);
			// 将常用的字段可以使用这个方法设置数据
			var serviceInstance = new serviceInstanceModel();
			serviceInstance.setViewServiceInstanceDetail(data);

			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#popupWindow").jqxWindow({
				position : {
					x : (windowW - 800) / 2,
					y : (windowH - 450) / 2
				}
			});
			$("#popupWindow").jqxWindow('open');
		}
  }
  
  // 查询服务实例
  function searchServiceInstance(){
	  var serviceInstance = new serviceInstanceModel();
	  serviceInstance.searchServiceInstance();
  }
  