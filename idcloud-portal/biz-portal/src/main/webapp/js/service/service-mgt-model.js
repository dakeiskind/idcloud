// 服务定义
var serviceMgtModel = function () {
		var me = this;
	    this.itemsServiceDefine = ko.observableArray();
	    this.itemsDetailConfig = ko.observableArray();
	    this.itemsDetailSpec = ko.observableArray();
	    this.itemsDetailPerformance = ko.observableArray();
	    this.itemsDetailOperate = ko.observableArray();
	    this.itemsTemplate = ko.observableArray();
	    this.itemsAddConfig = ko.observableArray();
	    this.itemsAddSpec = ko.observableArray();
	    this.itemsAddPerformance = ko.observableArray();
	    this.itemsAddOperate = ko.observableArray();
	    this.itemsUpdateConfig = ko.observableArray();
	    this.itemsUpdateSpec = ko.observableArray();
	    this.itemsUpdatePerformance = ko.observableArray();
	    this.itemsUpdateOperate = ko.observableArray();
	    var catalogSid = "";
	    var catalogName = "";
	    
	    // 初始化服务管理等
	    this.initServiceMgtTree = function(){
			$('#jqxTabsDefine').jqxTabs({width : '79.5%', height : '99.5%', position : 'top', showCloseButtons: true, theme : currentTheme});
			$('#jqxTabsDefine').jqxTabs('hideCloseButtonAt', 0); 

			Core.AjaxRequest({
				url : ws_url + "/rest/services/platform/findServiceCatalog",
				params : {},
				type:'post',
				callback : function (data) {
					var source =
	                {
	                    datatype: "json",
	                    datafields: [
	                        { name: 'catalogSid' },
	                        { name: 'parentCatalogSid' },
	                        { name: 'catalogName' },
	                        { name: 'value' }
	                    ],
	                    id: 'catalogSid',
	                    localdata: data
	                };
	                var dataAdapter = new $.jqx.dataAdapter(source);
	                dataAdapter.dataBind();
	                var records = dataAdapter.getRecordsHierarchy('catalogSid', 'parentCatalogSid', 'items', [{ name: 'catalogName', map: 'label'}]);					
					
		            $('#jqxTreeDefine').jqxTree({source: records, height: '100%', width: '100%', theme : currentTheme});
		            $('#jqxTreeDefine').jqxTree('expandAll');
		            $('#jqxTreeDefine').jqxTree('selectItem', null);
				}
			 });
		   
            $('#jqxExpanderDefine').jqxExpander({ showArrow: false, toggleMode: 'none', width:'20%', height:'100%', theme : currentTheme});
            
            $('#jqxTreeDefine').on('select', function(event) {
            	// sunyu add for #133
            	$('#jqxgridDefine').jqxGrid('clearselection');
            	// end
    			var args = event.args;
 	    	
    			var item = $('#jqxTreeDefine').jqxTree('getItem', args.element);
    			
    			//获取父服务目录
    			catalogSid = item.value;
    			catalogName = item.label;
    			$('#catalogSid').val(catalogSid);
    			$('#catalogName').val(catalogName);

    			Core.AjaxRequest({
    				url : ws_url + "/rest/services/platform/findServiceCatalogBySid",
    				params : {
    					"catalogSid" : item.value,
    					},
    				type : 'post',
    				callback : function(data) {
    					Core.AjaxRequest({
    						url : ws_url + "/rest/services/platform/findServiceDefineByParams",
    			 			type:'post',
    			 			params:{
    			 				parentCatalogSid : data.catalogSid
    			 			},
    			 			async:false,
    			 			callback : function (data) {
    			 				 me.itemsServiceDefine(data);
    				 		     
    	    	 				var sourcedatagrid ={
    	     			              datatype: "json",
    	     			              localdata: me.itemsServiceDefine
    	    	 			    };
    	    	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
    	    	 			    $("#jqxgridDefine").jqxGrid({source: dataAdapter});
    	    	 			    
    	    					$("#defineAddBtn").jqxButton({disabled: false });
    	    					$("#defineDetailBtn").jqxButton({disabled: true });
    	    					$("#defineTemplateMgtBtn").jqxButton({disabled: true });
    	    					$("#defineDeployBtn").jqxButton({disabled: true });
    	    					$("#defineDisableBtn").jqxButton({disabled: true });
    	    					// sunyu add for #133
    	    					$('#jqxTabsDefine').jqxTabs('select', 0);
    	    					// end
    			 			}
    			 		 });
    				}
    			});

    		});
	    };

	    // 刷新服务管理内容
	    this.refreshServiceMgtContent = function(params){
	    	Core.AjaxRequest({
				url : ws_url + "/rest/services/platform/findServiceCatalogBySid",
				params : {
					"catalogSid" : catalogSid,
					},
				type : 'post',
				callback : function(data) {
					Core.AjaxRequest({
						url : ws_url + "/rest/services/platform/findServiceDefineByParams",
			 			type:'post',
			 			params:{
			 				parentCatalogSid : data.catalogSid
			 			},
			 			async:false,
			 			callback : function (data) {
			 				 me.itemsServiceDefine(data);
				 		     
	    	 				var sourcedatagrid ={
	     			              datatype: "json",
	     			              localdata: me.itemsServiceDefine
	    	 			    };
	    	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	    	 			    $("#jqxgridDefine").jqxGrid({source: dataAdapter});
	    	 			    
	    					$("#defineAddBtn").jqxButton({disabled: false });
	    					$("#defineDetailBtn").jqxButton({disabled: true });
	    					$("#defineTemplateMgtBtn").jqxButton({disabled: true });
	    					$("#defineDeployBtn").jqxButton({disabled: true });
	    					$("#defineDisableBtn").jqxButton({disabled: true });
				 		     
			 			}
			 		 });
				}
			});
	    }; 	    
	    // 初始化服务管理内容
	    this.searchServiceMgtContent = function(params){
	    	 var defaultSearch = {
	    		
	         };
	    	
	    	 var obj = $.extend(defaultSearch, params);
	    	 Core.AjaxRequest({
				url : ws_url + "/rest/services/platform/findServiceDefineByParams",
	 			type:'post',
	 			params:obj,
	 			async:false,
	 			callback : function (data) {
	 			   me.itemsServiceDefine(data);
	 		       me.initServiceMgtDatagrid(me.itemsServiceDefine);
	 			}
	 		 });
	    }; 
	    
	    // 初始化服务定义的数据源
	    this.initServiceMgtDatagrid = function(data){
	    	var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#jqxgridDefine").jqxGrid(
	          {
	              width: "98%",
				  theme:currentTheme,
	              source: dataAdapter,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '服务名称', datafield: 'serviceName'},
	                  { text: '描述', datafield: 'description'},
	                  { text: '类型', datafield: 'serviceTypeName' },
	                  { text: '所属服务目录', datafield: 'parentCatalogName' },
	                  { text: '状态', datafield: 'serviceStatusName' }
	              ],
	              showtoolbar: true,
	              rendertoolbar: function (toolbar) {
	                  var me = this;
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var defineAddBtn = $("<div><a id='defineAddBtn' onclick='addServiceDefineItem()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增服务&nbsp;&nbsp;</a></div>");
	                  var defineUpdateBtn = $("<div><a id='defineUpdateBtn' onclick='updateServiceDefineItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>更新服务&nbsp;&nbsp;</a></div>");
	                  var defineDetailBtn = $("<div><a id='defineDetailBtn' onclick='viewServiceDefineDetailInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>查看服务&nbsp;&nbsp;</a></div>");
	                  var defineTemplateMgtBtn = $("<div><a id='defineTemplateMgtBtn' onclick='defineTemplateMgtItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-folder'></i>模板管理&nbsp;&nbsp;</a></div>");
	                  var defineDeployBtn = $("<div><a id='defineDeployBtn' onclick='defineDeployItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-right-5'></i>发布服务&nbsp;&nbsp;</a></div>");
	                  var defineDisableBtn = $("<div><a id='defineDisableBtn' onclick='defineDisableItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-block'></i>禁用服务&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(defineAddBtn);
	                  container.append(defineUpdateBtn);
	                  container.append(defineDetailBtn);
	                  container.append(defineTemplateMgtBtn);
	                  container.append(defineDeployBtn);
	                  container.append(defineDisableBtn);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#jqxgridDefine").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#jqxgridDefine').jqxGrid('getrowdata', index);
	    		  
    			  $("#defineUpdateBtn").jqxButton({width: '80',theme:currentTheme, disabled: false });
    			  $("#defineDetailBtn").jqxButton({width: '80',theme:currentTheme, disabled: false });
	   			  $("#defineTemplateMgtBtn").jqxButton({ width: '80',theme:currentTheme,disabled: false});
	   			  $("#defineDeployBtn").jqxButton({ width: '100',theme:currentTheme,disabled: false});
	   			  $("#defineDisableBtn").jqxButton({ width: '80',theme:currentTheme,disabled: false});
	          });
	    	  
	    	  $("#defineAddBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
	    	  $("#defineUpdateBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
	    	  $("#defineDetailBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
   			  $("#defineTemplateMgtBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
   			  $("#defineDeployBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
   			  $("#defineDisableBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
   			  
   			  if(catalogSid != "") {
		    	 $("#defineAddBtn").jqxButton({disabled: false });
	    	  }

	    };
	    
	    
	    /******************************************服务管理*****************************************************************/

	    /******************************************查看服务开始*****************************************************************/
	    // 初始化服务管理查看详情基本信息
	    this.searchServiceMgtBaseInfo = function(serviceSid,vConfigGrid,vSpecGrid,vPerformanceGrid,vOperateGrid,
	    		serviceName,description,serviceTypeName,parentCatalogName,serviceStatusName){

	    	 Core.AjaxRequest({
				url : ws_url + "/rest/services/platform/findServiceDefineBySid",
	 			type:'post',
	 			params:{
	 				serviceSid : serviceSid
	 				},
	 			async:false,
	 			callback : function (data) {
	 				$("#" + serviceName).html(data.serviceName);
	 		    	$("#" + description).html(data.description);
	 		    	$("#" + serviceTypeName).html(serviceTypeName);
	 		    	$("#" + parentCatalogName).html(parentCatalogName);
	 		    	$("#" + serviceStatusName).html(serviceStatusName);
	 		    	me.itemsDetailConfig(data.serviceConfig);
	 		    	me.initServiceMgtDetailConfigDatagrid(vConfigGrid,me.itemsDetailConfig);
	 		    	me.itemsDetailSpec(data.serviceSpec);
	 		    	me.initServiceMgtDetailSpecDatagrid(vSpecGrid,me.itemsDetailSpec);
	 		    	me.itemsDetailPerformance(data.servicePerf);
	 		    	me.initServiceMgtDetailPerformanceDatagrid(vPerformanceGrid,me.itemsDetailPerformance);
	 		    	me.itemsDetailOperate(data.serviceOperation);
	 		    	me.initServiceMgtDetailOperateDatagrid(vOperateGrid,me.itemsDetailOperate);

	 			}
	 		 });
	    }; 
	    
	    // 初始化服务管理查看详情的数据源
	    this.initServiceMgtDetailConfigDatagrid = function(vConfigGrid,data){
	    	
	    	 var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	 
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#" + vConfigGrid).jqxGrid(
	          {
	              width: "99%",
				  height:"50px",
				  theme:currentTheme,
	              source: dataAdapter,
	              columnsresize: true,
	              pageable: false, 
	              pagesize: 10, 
	              autoheight: true,
	              localization: gridLocalizationObj,
	              columns: [
  	                  { text: '名称', datafield: 'name'},
	                  { text: '说明', datafield: 'description'},
	                  { text: '数据类型', datafield: 'dataType' },
	                  { text: '单位', datafield: 'unit' },
	                  { text: '取值', datafield: 'value' }
	              ],
	              showtoolbar: false
	          });
	   };
	    
	    // 初始化服务管理详情规格的数据源
	    this.initServiceMgtDetailSpecDatagrid = function(vSpecGrid,data){
	    	 
	    	 var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	 
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#" + vSpecGrid).jqxGrid(
	          {
	              width: "99%",
				  height:"50px",
				  theme:currentTheme,
	              source: dataAdapter,
	              columnsresize: true,
	              pageable: false, 
	              pagesize: 10, 
	              autoheight: true,
	              localization: gridLocalizationObj,
	              columns: [
	  	                  { text: '名称', datafield: 'name'},
		                  { text: '说明', datafield: 'description'},
		                  { text: '数据类型', datafield: 'dataType' },
		                  { text: '单位', datafield: 'unit' },
		                  { text: '取值', datafield: 'valueDomain' }
	              ],
	              showtoolbar: false
	          });
	   };
	    
	   // 初始化服务管理详情性能指标的数据源
	    this.initServiceMgtDetailPerformanceDatagrid = function(vPerformanceGrid,data){    	
	    	 var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	 
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#" + vPerformanceGrid).jqxGrid(
	          {
	              width: "99%",
				  height:"50px",
				  theme:currentTheme,
	              source: dataAdapter,
	              columnsresize: true,
	              pageable: false, 
	              pagesize: 10, 
	              autoheight: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '名称', datafield: 'name'},
	                  { text: '说明', datafield: 'description'},
	                  { text: '数据类型', datafield: 'dataType' },
	                  { text: '单位', datafield: 'unit' },
	                  { text: '取值', datafield: 'valueDomain' },
	                  { text: '分组', datafield: 'perfGroup' },
	                  { text: '显示样式', datafield: 'displayStyle' }
	              ],
	              showtoolbar: false
	          });
	   };
	   
	   
	    // 初始化服务管理详情操作的数据源
	    this.initServiceMgtDetailOperateDatagrid = function(vOperateGrid, data){
	    	 var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	 
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#" + vOperateGrid).jqxGrid(
	          {
	              width: "99%",
				  height:"50px",
				  theme:currentTheme,
	              source: dataAdapter,
	              columnsresize: true,
	              pageable: false, 
	              pagesize: 10, 
	              autoheight: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '名称', datafield: 'name'},
	                  { text: '说明', datafield: 'description'},
	                  { text: '定义', datafield: 'wsDefinition' },
	                  { text: '类型', datafield: 'type' }
	              ],
	              showtoolbar: false
	          });
	   };
	   /******************************************查看服务结束*****************************************************************/
	   
	   /******************************************新增服务开始*****************************************************************/
	    // 初始化服务管理查看详情基本信息
	    this.initAddServiceInfo = function(){
	    	// 初始化模块新增下拉框
			var codeadd = new codeModel({width : 350, dropDownWidth : 350,autoDropDownHeight : true});
			codeadd.getCommonCode("add-serviceType", "SERVICE_TYPE", true);
			codeadd.getCommonCode("add-serviceStatus", "SERVICE_STATUS", true);
			var codeadd1 = new codeModel({width : 350, dropDownWidth : 350, isCheckboxes:true,disabled:true,autoDropDownHeight : true});
			codeadd1.getCustomCode("add-relation-service", "/services/findAll","serviceName","serviceSid", false,"POST",{serviceStatus:"03",serviceType:"01"});
			
			// sunyu add for Fun#174
			codeadd.getCommonCode("add-serviceScope", "SERVICE_SCOPE", false);
			
			var canOrderSource =
	         {
	             datatype: "json",
	             datafields: [
	                 { name: 'codeValue' },
	                 { name: 'codeDisplay' }
	             ],
	             localdata: [{"codeValue":"0","codeDisplay":"否"},{"codeValue":"1","codeDisplay":"是"}]
	         };
			var canOrder_dataAdapter = new $.jqx.dataAdapter(canOrderSource);
			$("#add-canOrder").jqxDropDownList({
                selectedIndex: 0, 
                source: canOrder_dataAdapter,
                displayMember: "codeDisplay", 
                valueMember: "codeValue",
                width: 120,
                height: 22,
                autoDropDownHeight : true,
                theme:"metro"
			});
			// end
	        //初始化服务基本信息
			$("#add-parentCatalogName").jqxInput({placeHolder: "", height: 23, width: 350, minLength: 1,theme:currentTheme,disabled: true});
	        $("#add-serviceName").jqxInput({placeHolder: "", height: 23, width: 350, minLength: 1,theme:currentTheme});
			$("#add-serviceCode").jqxInput({placeHolder: "", height: 23, width: 350, minLength: 1,theme:currentTheme});
	        $("#add-description").jqxInput({placeHolder: "", height: 46, width: 350, minLength: 1,theme:currentTheme});
	        //初始化服务配置信息
			var codeaddconfig = new codeModel({width : 150, autoDropDownHeight : true});
			codeaddconfig.getCommonCode("add-configDataType", "DATA_TYPE");
	        $("#add-configName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#add-configValue").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#add-configUnit").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#add-configDescription").jqxInput({placeHolder: "", height: 46, width: 390, minLength: 1,theme:currentTheme});
	        $("#saveConfig").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#cancelConfig").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        //初始化服务规格信息
			var codeaddspec = new codeModel({width : 150, autoDropDownHeight : true});
			codeaddspec.getCommonCode("add-specDataType", "DATA_TYPE");
	        $("#add-specName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#add-specValueDomain").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#add-specUnit").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#add-specDescription").jqxInput({placeHolder: "", height: 46, width: 390, minLength: 1,theme:currentTheme});
	        $("#saveSpec").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#cancelSpec").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        //初始化服务性能信息
			var codeaddperf = new codeModel({width : 150, autoDropDownHeight : true});
			codeaddperf.getCommonCode("add-performanceDataType", "DATA_TYPE");
	        $("#add-performanceName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#add-performanceValueDomain").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#add-performanceUnit").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#add-performancePerfGroup").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#add-performanceDisplayStyle").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#add-performanceDescription").jqxInput({placeHolder: "", height: 46, width: 390, minLength: 1,theme:currentTheme});
	        $("#savePerformance").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#cancelPerformance").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        //初始化服务操作信息
			var codeaddoperate = new codeModel({width : 150, autoDropDownHeight : true});
			codeaddoperate.getCommonCode("add-operateType", "DATA_TYPE");
	        $("#add-operateName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#add-operateWsDefinition").jqxInput({placeHolder: "", height: 23, width: 390, minLength: 1,theme:currentTheme});
	        $("#add-operateDescription").jqxInput({placeHolder: "", height: 46, width: 390, minLength: 1,theme:currentTheme});
	        $("#saveOperate").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#cancelOperate").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        //初始化服务按钮
	        $("#addServiceBtn").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#resetServiceBtn").jqxButton({ width: '50',height:"25",theme:currentTheme})
	        //初始化服务新增各列表
	        var initData = {};    
	        me.itemsAddConfig(initData);
	    	me.initServiceMgtAddConfigDatagrid(me.itemsAddConfig);
	        me.itemsAddSpec(initData);
	    	me.initServiceMgtAddSpecDatagrid(me.itemsAddSpec);
	        me.itemsAddPerformance(initData);
	    	me.initServiceMgtAddPerformanceDatagrid(me.itemsAddPerformance);
	        me.itemsAddOperate(initData);
	    	me.initServiceMgtAddOperateDatagrid(me.itemsAddOperate);
	    }; 
	    
	    // 初始化服务管理新增配置的数据源
	    this.initServiceMgtAddConfigDatagrid = function(data){

	          $("#vAddConfigGrid").jqxGrid(
	          {
	              width: "99%",
				  height:"200px",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: false, 
	              pagesize: 10, 
	              autoheight: false,
	              localization: gridLocalizationObj,
	              columns: [
 	                  { text: '名称', datafield: 'name'},
	                  { text: '说明', datafield: 'description'},
	                  { text: '数据类型', datafield: 'dataType' },
	                  { text: '单位', datafield: 'unit' },
	                  { text: '取值', datafield: 'value' }
	              ],
	              showstatusbar: true,
	              renderstatusbar: function (statusbar) {
	                  var me = this;
	                  var container = $("<div id='btnConfigGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addConfigBtnLabel = $("<div><a id='addConfigBtn' data-bind='click: addConfigItem'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>添加&nbsp;&nbsp;</a></div>");
	                  
	                  statusbar.append(container);
	                  container.append($(addConfigBtnLabel));	                  
	              }

	          });
	          
	    	  $("#addConfigBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });

	   };
	    
	    // 初始化服务管理详情规格的数据源
	    this.initServiceMgtAddSpecDatagrid = function(data){

	          $("#vAddSpecGrid").jqxGrid(
	          {
	              width: "99%",
				  height:"200px",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: false, 
	              pagesize: 10, 
	              autoheight: false,
	              localization: gridLocalizationObj,
	              columns: [
	  	                  { text: '名称', datafield: 'name'},
		                  { text: '说明', datafield: 'description'},
		                  { text: '数据类型', datafield: 'dataType' },
		                  { text: '单位', datafield: 'unit' },
		                  { text: '取值', datafield: 'valueDomain' }
	              ],
	              showstatusbar: true,
	              renderstatusbar: function (statusbar) {
	                  var me = this;
	                  var container = $("<div id='btnSpecGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addSpecBtnLabel = $("<div><a id='addSpecBtn' data-bind='click: addSpecItem'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>添加&nbsp;&nbsp;</a></div>");
	                  
	                  statusbar.append(container);
	                  container.append($(addSpecBtnLabel));	                  
	              }
	          });
	    	  $("#addSpecBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });

	   };
	    
	   // 初始化服务管理详情性能指标的数据源
	    this.initServiceMgtAddPerformanceDatagrid = function(data){    	
	    		    	 
	          $("#vAddPerformanceGrid").jqxGrid(
	          {
	              width: "99%",
				  height:"200px",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: false, 
	              pagesize: 10, 
	              autoheight: false,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '名称', datafield: 'name'},
	                  { text: '说明', datafield: 'description'},
	                  { text: '数据类型', datafield: 'dataType' },
	                  { text: '单位', datafield: 'unit' },
	                  { text: '取值', datafield: 'valueDomain' },
	                  { text: '分组', datafield: 'perfGroup' },
	                  { text: '显示样式', datafield: 'displayStyle'}
	              ],
	              showstatusbar: true,
	              renderstatusbar: function (statusbar) {
	                  var me = this;
	                  var container = $("<div id='btnPerformanceGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addPerformanceBtnLabel = $("<div><a id='addPerformanceBtn' data-bind='click: addPerformanceItem'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>添加&nbsp;&nbsp;</a></div>");
	                  
	                  statusbar.append(container);
	                  container.append($(addPerformanceBtnLabel));	                  
	              }
	          });
	    	  $("#addPerformanceBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });

	   };
	   
	    // 初始化服务管理详情操作的数据源
	    this.initServiceMgtAddOperateDatagrid = function(data){
	    	 
	          $("#vAddOperateGrid").jqxGrid(
	          {
	              width: "99%",
				  height:"200px",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: false, 
	              pagesize: 10, 
	              autoheight: false,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '名称', datafield: 'name'},
	                  { text: '说明', datafield: 'description'},
	                  { text: '定义', datafield: 'wsDefinition' },
	                  { text: '类型', datafield: 'type' }
	              ],
	              showstatusbar: true,
	              renderstatusbar: function (statusbar) {
	                  var me = this;
	                  var container = $("<div id='btnOperateGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addOperateBtnLabel = $("<div><a id='addOperateBtn' data-bind='click: addOperateItem'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>添加&nbsp;&nbsp;</a></div>");
	                  
	                  statusbar.append(container);
	                  container.append($(addOperateBtnLabel));	                  
	              }
	          });
	    	  $("#addOperateBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });

	   };
	   /******************************************新增服务结束*****************************************************************/
	   
	   /******************************************编辑服务开始*****************************************************************/
	    // 初始化服务管理查看详情基本信息
	    this.initUpdateServiceInfo = function(serviceSid,parentCatalogName){
	    	// 初始化模块新增下拉框
			var codeupdate = new codeModel({width : 350, autoDropDownHeight : true});
			codeupdate.getCommonCode("update-serviceType", "SERVICE_TYPE", true);
			codeupdate.getCommonCode("update-serviceStatus", "SERVICE_STATUS", true);
			// sunyu add for Fun#193
			codeupdate.getCommonCode("update-serviceScope", "SERVICE_SCOPE", false);
			
			var canOrderSource =
	         {
	             datatype: "json",
	             datafields: [
	                 { name: 'codeValue' },
	                 { name: 'codeDisplay' }
	             ],
	             localdata: [{"codeValue":"0","codeDisplay":"否"},{"codeValue":"1","codeDisplay":"是"}]
	         };
			var canOrder_dataAdapter = new $.jqx.dataAdapter(canOrderSource);
			$("#update-canOrder").jqxDropDownList({
                selectedIndex: 0, 
                source: canOrder_dataAdapter,
                displayMember: "codeDisplay", 
                valueMember: "codeValue",
                width: 120,
                height: 22,
                autoDropDownHeight : true,
                theme:"metro"
			});
			// end
//			$("#update-serviceScope").on("change", function(event){
//				var item = args.item;
//				alert(item.label);
//				alert(item.value);
//			});
			
			
	        //初始化服务基本信息
			$("#update-parentCatalogName").jqxInput({placeHolder: "", height: 23, width: 350, minLength: 1,theme:currentTheme,disabled: true});
	        $("#update-serviceName").jqxInput({placeHolder: "", height: 23, width: 350, minLength: 1,theme:currentTheme});
	        $("#update-description").jqxInput({placeHolder: "", height: 46, width: 350, minLength: 1,theme:currentTheme});
	        
	        //初始化服务配置信息
			var codeupdateconfig = new codeModel({width : 150, autoDropDownHeight : true});
			codeupdateconfig.getCommonCode("update-configDataType", "DATA_TYPE");
	        $("#update-configName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#update-configValue").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#update-configUnit").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#update-configDescription").jqxInput({placeHolder: "", height: 46, width: 390, minLength: 1,theme:currentTheme});
	        $("#saveUpdateConfig").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#cancelUpdateConfig").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        //初始化服务规格信息
			var codeupdatespec = new codeModel({width : 150, autoDropDownHeight : true});
			codeupdatespec.getCommonCode("update-specDataType", "DATA_TYPE");
	        $("#update-specName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#update-specValueDomain").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#update-specUnit").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#update-specDescription").jqxInput({placeHolder: "", height: 46, width: 390, minLength: 1,theme:currentTheme});
	        $("#saveUpdateSpec").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#cancelUpdateSpec").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        //初始化服务性能信息
			var codeupdateperf = new codeModel({width : 150, autoDropDownHeight : true});
			codeupdateperf.getCommonCode("update-performanceDataType", "DATA_TYPE");
	        $("#update-performanceName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#update-performanceValueDomain").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#update-performanceUnit").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#update-performancePerfGroup").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#update-performanceDisplayStyle").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#update-performanceDescription").jqxInput({placeHolder: "", height: 46, width: 390, minLength: 1,theme:currentTheme});
	        $("#saveUpdatePerformance").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#cancelUpdatePerformance").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        //初始化服务操作信息
			var codeupdateoperate = new codeModel({width : 150, autoDropDownHeight : true});
			codeupdateoperate.getCommonCode("update-operateType", "DATA_TYPE");
	        $("#update-operateName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#update-operateWsDefinition").jqxInput({placeHolder: "", height: 23, width: 390, minLength: 1,theme:currentTheme});
	        $("#update-operateDescription").jqxInput({placeHolder: "", height: 46, width: 390, minLength: 1,theme:currentTheme});
	        $("#saveUpdateOperate").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#cancelUpdateOperate").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        //初始化服务按钮
	        $("#updateServiceBtn").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#resetUpdateServiceBtn").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        
	        Core.AjaxRequest({
				url : ws_url + "/rest/services/platform/findServiceDefineBySid",
	 			type:'post',
	 			params:{
	 				serviceSid : serviceSid
	 				},
	 			async:false,
	 			callback : function (data) {
	 				$("#update-serviceName").val(data.serviceName);
	 		    	$("#update-serviceStatus").val(data.serviceStatus);
	 		    	$("#update-parentCatalogName").val(parentCatalogName);
	 		    	var codeupdate = new codeModel({width : 350, autoDropDownHeight : true});
	 		    	codeupdate.getCommonCode("update-serviceType", "SERVICE_TYPE", true);
	 		    	$("#update-serviceType").val(data.serviceType);
	 		    	$("#update-description").val(data.description);
	 		    	//$("#update-serviceScope").val(data.serviceScope);
	 		    	$("#update-serviceScope").jqxDropDownList('val', data.serviceScope);
	 		    	$("#update-canOrder").val(data.canOrder);
	 		    	me.itemsUpdateConfig(data.serviceConfig);
	 		    	me.initServiceMgtUpdateConfigDatagrid(me.itemsUpdateConfig);
	 		    	me.itemsUpdateSpec(data.serviceSpec);
	 		    	me.initServiceMgtUpdateSpecDatagrid(me.itemsUpdateSpec);
	 		    	me.itemsUpdatePerformance(data.servicePerf);
	 		    	me.initServiceMgtUpdatePerformanceDatagrid(me.itemsUpdatePerformance);
	 		    	me.itemsUpdateOperate(data.serviceOperation);
	 		    	me.initServiceMgtUpdateOperateDatagrid(me.itemsUpdateOperate);

	 			}
	 		 });
	    }; 
	    
	    // 初始化服务管理新增配置的数据源
	    this.initServiceMgtUpdateConfigDatagrid = function(data){
	    	 var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	 
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#vUpdateConfigGrid").jqxGrid(
	          {
	              width: "99%",
				  height:"200px",
				  theme:currentTheme,
	              columnsresize: true,
	              source: dataAdapter,
	              pageable: false, 
	              pagesize: 10, 
	              autoheight: false,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '名称', datafield: 'name'},
	                  { text: '说明', datafield: 'description'},
	                  { text: '数据类型', datafield: 'dataType' },
	                  { text: '单位', datafield: 'unit' },
	                  { text: '取值', datafield: 'value' }
	              ],
	              showstatusbar: true,
	              renderstatusbar: function (statusbar) {
	                  var me = this;
	                  var container = $("<div id='btnConfigGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var updateConfigBtnLabel = $("<div><a id='updateConfigBtn' data-bind='click: updateConfigItem'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>更新&nbsp;&nbsp;</a></div>");
	                  
	                  statusbar.append(container);
	                  container.append($(updateConfigBtnLabel));	                  
	              }

	          });
	          // 控制按钮是否可用
	    	  $("#vUpdateConfigGrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#vUpdateConfigGrid').jqxGrid('getrowdata', index);
	    		  
    			  $("#updateConfigBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
	          });
	    	  $("#updateConfigBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });

	   };
	    
	    // 初始化服务管理详情规格的数据源
	    this.initServiceMgtUpdateSpecDatagrid = function(data){
	    	    	
	    	 var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	 
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#vUpdateSpecGrid").jqxGrid(
	          {
	              width: "99%",
				  height:"200px",
				  theme:currentTheme,
	              columnsresize: true,
	              source: dataAdapter,
	              pageable: false, 
	              pagesize: 10, 
	              autoheight: false,
	              localization: gridLocalizationObj,
	              columns: [
	  	                  { text: '名称', datafield: 'name'},
		                  { text: '说明', datafield: 'description'},
		                  { text: '数据类型', datafield: 'dataType' },
		                  { text: '单位', datafield: 'unit' },
		                  { text: '取值', datafield: 'valueDomain' }
	              ],
	              showstatusbar: true,
	              renderstatusbar: function (statusbar) {
	                  var me = this;
	                  var container = $("<div id='btnSpecGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var updateSpecBtnLabel = $("<div><a id='updateSpecBtn' data-bind='click: updateSpecItem'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>更新&nbsp;&nbsp;</a></div>");
	                  
	                  statusbar.append(container);
	                  container.append($(updateSpecBtnLabel));	                  
	              }
	          });
	          // 控制按钮是否可用
	    	  $("#vUpdateSpecGrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#vUpdateSpecGrid').jqxGrid('getrowdata', index);
	    		  
    			  $("#updateSpecBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
	          });
	    	  $("#updateSpecBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });

	   };
	    
	   // 初始化服务管理详情性能指标的数据源
	    this.initServiceMgtUpdatePerformanceDatagrid = function(data){    	
	    	 var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	 
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#vUpdatePerformanceGrid").jqxGrid(
	          {
	              width: "99%",
				  height:"200px",
				  theme:currentTheme,
	              columnsresize: true,
	              source: dataAdapter,
	              pageable: false, 
	              pagesize: 10, 
	              autoheight: false,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '名称', datafield: 'name'},
	                  { text: '说明', datafield: 'description'},
	                  { text: '数据类型', datafield: 'dataType' },
	                  { text: '单位', datafield: 'unit' },
	                  { text: '取值', datafield: 'valueDomain' },
	                  { text: '分组', datafield: 'perfGroup' },
	                  { text: '显示样式', datafield: 'displayStyle'}
	              ],
	              showstatusbar: true,
	              renderstatusbar: function (statusbar) {
	                  var me = this;
	                  var container = $("<div id='btnPerformanceGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var updatePerformanceBtnLabel = $("<div><a id='updatePerformanceBtn' data-bind='click: updatePerformanceItem'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>更新&nbsp;&nbsp;</a></div>");
	                  
	                  statusbar.append(container);
	                  container.append($(updatePerformanceBtnLabel));	                  
	              }
	          });
	       // 控制按钮是否可用
	    	  $("#vUpdatePerformanceGrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#vUpdatePerformanceGrid').jqxGrid('getrowdata', index);
	    		  
    			  $("#updatePerformanceBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
	          });
	    	  $("#updatePerformanceBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });

	   };
	   
	   
	    // 初始化服务管理详情操作的数据源
	    this.initServiceMgtUpdateOperateDatagrid = function(data){
	    	   
	    	 var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	 
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);

	          $("#vUpdateOperateGrid").jqxGrid(
	          {
	              width: "99%",
				  height:"200px",
				  theme:currentTheme,
	              columnsresize: true,
	              source: dataAdapter,
	              pageable: false, 
	              pagesize: 10, 
	              autoheight: false,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '名称', datafield: 'name'},
	                  { text: '说明', datafield: 'description'},
	                  { text: '定义', datafield: 'wsDefinition' },
	                  { text: '类型', datafield: 'type' }
	              ],
	              showstatusbar: true,
	              renderstatusbar: function (statusbar) {
	                  var me = this;
	                  var container = $("<div id='btnOperateGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var updateOperateBtnLabel = $("<div><a id='updateOperateBtn' data-bind='click: updateOperateItem'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>更新&nbsp;&nbsp;</a></div>");
	                  
	                  statusbar.append(container);
	                  container.append($(updateOperateBtnLabel));	                  
	              }
	          });
		       // 控制按钮是否可用
	    	  $("#vUpdateOperateGrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#vUpdateOperateGrid').jqxGrid('getrowdata', index);
	    		  
    			  $("#updateOperateBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
	          });
	    	  $("#updateOperateBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });

	   };
	   /******************************************编辑服务结束*****************************************************************/
	   
	   
	   /******************************************模板管理开始*****************************************************************/
	   this.initTemplateInput = function() {
		    $("#cancelAdd").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
		    $("#cancelEdit").jqxButton({ width: '50px', height:'25px', theme:currentTheme});
		    
		    //新增
		    $("#add_serviceName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme,disabled: true});
	        $("#add_serviceName").jqxInput('val', serviceName); 
	        $("#add_templateName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#add_templateName").jqxInput('val', ''); 
	        $("#add_imagePath").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#add_description").jqxInput({placeHolder: "", height: 46, width: 670, minLength: 1,theme:currentTheme});
	        $("#add_expiringDate").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
		    $("#addSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
		    
		    var codeadd = new codeModel({width : 150, autoDropDownHeight : true});
			codeadd.getCommonCode("add_templateStatus", "SERVICE_TEMPLATE_STATUS");
			codeadd.getCommonCode("add_billingPlanSid", "PLAN");
			
			//编辑
			var codeeidt = new codeModel({width : 150, autoDropDownHeight : true});
			codeeidt.getCommonCode("edit_templateStatus", "SERVICE_TEMPLATE_STATUS");
			codeeidt.getCommonCode("edit_billingPlanSid", "PLAN");
	        $("#edit_serviceName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme,disabled: true});
	        //$("#edit_serviceName").jqxInput('val', serviceName); 
	        $("#edit_templateName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#edit_imagePath").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#edit_description").jqxInput({placeHolder: "", height: 46, width: 670, minLength: 1,theme:currentTheme});
	        $("#edit_expiringDate").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
		    $("#editSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
			
	   };
//	   // 初始化服务管理模板列表信息
//	   this.searchServiceMgtTemplateInfo = function(serviceSid,jqxTemplategrid){
//	    	 Core.AjaxRequest({
//				url : ws_url + "/rest/services/platform/findServiceTempByServiceSid",
//	 			type:'post',
//	 			params:{
//	 				serviceSid : serviceSid
//	 				},
//	 			async:false,
//	 			callback : function (data) {
//	 		    	me.itemsTemplate(data.serviceOperation);
//	 		    	me.initServiceMgtTemplateDatagrid(jqxTemplategrid,me.itemsTemplate);
//
//	 			}
//	 		 });
//	    }; 
//	    
//	    // 初始化服务管理模板的数据源
//	    this.initServiceMgtTemplateDatagrid = function(jqxTemplategrid, data){
//	    	 var sourcedatagrid ={
//		              datatype: "json",
//		              localdata: data
//		     };
//	    	 
//	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
//	          $("#" + jqxTemplategrid).jqxGrid(
//	          {
//	              width: "99.5%",
//				  height:"360px",
//				  theme:currentTheme,
//	              source: dataAdapter,
//	              columnsresize: true,
//	              pageable: true, 
//	              pagesize: 10, 
//	              autoheight: false,
//	              autowidth: false,
//	              selectionmode:"singlerow",
//	              localization: gridLocalizationObj,
//	              columns: [
//	                  { text: '模版名称', datafield: 'templateName'},
//	                  { text: '模版描述', datafield: 'description'},
//	                  { text: '到期时间', datafield: 'expiringDate' },
//	                  { text: '模板状态', datafield: 'templateStatusName' },
//	                  { text: '资费计划', datafield: 'pricingSid' }
//	              ],
//	              showtoolbar: true,
//	              rendertoolbar: function (toolbar) {
//	                  var me = this;
//	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
//	                  var addTempBtn = $("<div><a id='addTempBtn' data-bind='click: addTempItem'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增模板&nbsp;&nbsp;</a></div>");
//	                  var editTempBtn = $("<div><a id='editTempBtn' data-bind='click: editTempItem' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑模板&nbsp;&nbsp;</a></div>");
//	                  var delTempBtn = $("<div><a id='delTempBtn' data-bind='click: delTempItem' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除模板&nbsp;&nbsp;</a></div>");
//	                  
//	                  toolbar.append(container);
//	                  container.append(addTempBtn);
//	                  container.append(editTempBtn);
//	                  container.append(delTempBtn);
//	              }
//	          });
//	          
//	       // 控制按钮是否可用
//	    	  $("#" + jqxTemplategrid).on('rowselect', function (event) {
//	    		  var args = event.args; 
//	    		  var index = args.rowindex;
//	    		  
//	    		$("#addTempBtn").jqxButton({width: '80',theme:currentTheme, disabled: false });
//	    		$("#editTempBtn").jqxButton({width: '80',theme:currentTheme, disabled: true });
//	   		  	$("#delTempBtn").jqxButton({ width: '80',theme:currentTheme,disabled: true});
//					   		  
//	          });
//	    	  
//	    	  $("#addTempBtn").jqxButton({width: '80',theme:currentTheme,height: '18px', disabled: false });
//   			  $("#editTempBtn").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
//   			  $("#delTempBtn").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
//	   };
//	   
//	    // 初始化模板弹出window
//	    this.initServiceTempPopWindow = function(){
//			$("#addTempWindow").jqxWindow({
//	                width: 1050, 
//	                height:550,
//	                resizable: false,  
//	                isModal: true, 
//	                autoOpen: false, 
//	                cancelButton: $("#Cancel"),
//	                theme: currentTheme,
//	                modalOpacity: 0.3           
//	         });
//	    };
	    /******************************************模板管理结束*****************************************************************/
	    
		/*****************************************************服务定义更新开始***********************************************************/

		/*****************************************************服务配置***********************************************************/
	    // 初始化模板弹出window
	    this.initServiceTempPopWindow = function(){
	    	 //初始化更新服务配置弹出窗口
			$("#updateConfigWindow").jqxWindow({
	               width: 500, 
	               height:200,
	               resizable: false,  
	               isModal: true, 
	               autoOpen: false, 
	               theme: currentTheme,
	               cancelButton: $("#cancelUpdateConfig"), 
	               modalOpacity: 0.3           
		     });
			//初始化更新服务配置弹出窗口
			$("#updateSpecWindow").jqxWindow({
	               width: 500, 
	               height:200,
	               resizable: false,  
	               isModal: true, 
	               autoOpen: false, 
	               cancelButton: $("#cancelUpdateSpec"),
	               theme: currentTheme,
	               modalOpacity: 0.3           
		     });updateConfigForm
			//初始化新增服务配置弹出窗口
			$("#updatePerformanceWindow").jqxWindow({
	               width: 500, 
	               height:220,
	               resizable: false,  
	               isModal: true, 
	               autoOpen: false, 
	               theme: currentTheme,
	               cancelButton: $("#cancelUpdatePerformance"), 
	               modalOpacity: 0.3           
		     });
			//初始化新增服务配置弹出窗口
			$("#updateOperateWindow").jqxWindow({
	               width: 500, 
	               height:200,
	               resizable: false,  
	               isModal: true, 
	               autoOpen: false, 
	               theme: currentTheme,
	               cancelButton: $("#cancelUpdateOperate"), 
	               modalOpacity: 0.3           
		     });
		
	    };
	  
	    // 初始化验证规则
	    this.initUpdateMgtValidator = function(){
	    	
	    	$("#updateConfigForm").jqxValidator({
				rules : [ {input : '#update-configName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
				          {input : '#update-configName', message : '配置名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
				          {input : '#update-configValue', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
				          {input : '#update-configValue', message : '配置值为0-256个字符!', action : 'keyup, blur', rule : 'length=0,256' },
				          {input : '#update-configUnit', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
				          {input : '#update-configUnit', message : '配置单位为0-16个字符!', action : 'keyup, blur', rule : 'length=0,16' },
				        ]
			});
			
			// 更新用户表单验证成功
			$("#updateConfigForm").on('validationSuccess', function (event) {
				var temp = Core.parseJSON($("#updateConfigForm").serializeJson());
				
				var row = { name: $("#update-configName").val(), description: $("#update-configDescription").val(), dataType: $("#update-configDataType").val(),
						unit: $("#update-configUnit").val(), value: $("#update-configValue").val()};
	            $('#vUpdateConfigGrid').jqxGrid('addrow', "index", row);
	            //清除元素值
	            $("#update-configName").val("");
	            $("#update-configDescription").val("");
	            $("#update-configDataType").val("");
	            $("#update-configUnit").val("");
	            $("#update-configValue").val("");

	            //关闭弹出框
	            $("#updateConfigWindow").jqxWindow('close');
			 });
			
		/********************************************************服务配置***********************************************************/
		
		/*****************************************************服务规格***********************************************************/
			
			$("#updateSpecForm").jqxValidator({
				rules : [ {input : '#update-specName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
				          {input : '#update-specName', message : '规格名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
				          {input : '#update-specValueDomain', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
				          {input : '#update-specValueDomain', message : '规格值为0-256个字符!', action : 'keyup, blur', rule : 'length=0,256' },
				          {input : '#update-specUnit', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
				          {input : '#update-specUnit', message : '规格单位为0-16个字符!', action : 'keyup, blur', rule : 'length=0,16' },
				        ]
			});
			
			// 新增服务规格验证成功
			$("#updateSpecForm").on('validationSuccess', function (event) {
				var temp = Core.parseJSON($("#updateSpecForm").serializeJson());
				
				var row = { name: $("#update-specName").val(), description: $("#update-specDescription").val(), dataType: $("#update-specDataType").val(),
						unit: $("#update-specUnit").val(), valueDomain: $("#update-specValueDomain").val()};
	            $('#vUpdateSpecGrid').jqxGrid('addrow', "index", row);
	            //清除元素值
	            $("#update-specName").val("");
	            $("#update-specDescription").val("");
	            $("#update-specDataType").val("");
	            $("#update-specUnit").val("");
	            $("#update-specValueDomain").val("");
	            //关闭弹出框
	            $("#updateSpecWindow").jqxWindow('close');
			 });
		/********************************************************服务规格***********************************************************/
		
		/*****************************************************服务性能指标***********************************************************/
				
			$("#updatePerformanceForm").jqxValidator({
				rules : [ {input : '#update-performanceName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
				          {input : '#update-performanceName', message : '性能名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
				          {input : '#update-performanceValueDomain', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
				          {input : '#update-performanceValueDomain', message : '性能值为0-256个字符!', action : 'keyup, blur', rule : 'length=0,256' },
				          {input : '#update-performanceUnit', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
				          {input : '#update-performanceUnit', message : '性能单位为0-16个字符!', action : 'keyup, blur', rule : 'length=0,16' },
				          {input : '#update-performancePerfGroup', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
				          {input : '#update-performancePerfGroup', message : '性能分组值为0-256个字符!', action : 'keyup, blur', rule : 'length=0,16' },
				          {input : '#update-performanceDisplayStyle', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
				          {input : '#update-performanceDisplayStyle', message : '显示样式单位为0-16个字符!', action : 'keyup, blur', rule : 'length=0,16' },
				        ]
			});
			
			// 新增服务规格验证成功
			$("#updatePerformanceForm").on('validationSuccess', function (event) {
				var temp = Core.parseJSON($("#updatePerformanceForm").serializeJson());
				
				var row = { name: $("#update-performanceName").val(), description: $("#update-performanceDescription").val(), dataType: $("#update-performanceDataType").val(),
						unit: $("#update-performanceUnit").val(), valueDomain: $("#update-performanceValueDomain").val(), perfGroup: $("#update-performancePerfGroup").val(), 
						displayStyle: $("#update-performanceDisplayStyle").val()};
	            $('#vUpdatePerformanceGrid').jqxGrid('addrow', "index", row);
	            //清除元素值
	            $("#update-performanceName").val("");
	            $("#update-performanceDescription").val("");
	            $("#update-performanceDataType").val("");
	            $("#update-performanceUnit").val("");
	            $("#update-performanceValueDomain").val("");
	            $("#update-performancePerfGroup").val("");
	            $("#update-performanceDisplayStyle").val("");

	            //关闭弹出框
	            $("#updatePerformanceWindow").jqxWindow('close');
			 });
		/********************************************************服务性能指标***********************************************************/
		
		/**********************************************************服务操作***********************************************************/
				
			$("#updateOperateForm").jqxValidator({
				rules : [ {input : '#update-operateName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
				          {input : '#update-operateName', message : '操作名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
				          {input : '#update-operateWsDefinition', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
				          {input : '#update-operateWsDefinition', message : '操作定义值为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
				        ]
			});
			
			// 新增用户表单验证成功
			$("#updateOperateForm").on('validationSuccess', function (event) {
				var temp = Core.parseJSON($("#updateOperateForm").serializeJson());
				
				var row = { name: $("#update-operateName").val(), description: $("#update-operateDescription").val(), type: $("#update-operateType").val(),
						wsDefinition: $("#update-operateWsDefinition").val()};
	            $('#vUpdateOperateGrid').jqxGrid('addrow', "index", row);
	            //清除元素值
	            $("#update-operateName").val("");
	            $("#update-operateWsDefinition").val("");
	            $("#update-operateType").val("");
	            $("#update-operateDescription").val("");

	            //关闭弹出框
	            $("#updateOperateWindow").jqxWindow('close');
			 });
			
		/********************************************************服务操作***********************************************************/

			$("#updateServiceForm").jqxValidator({
				rules : [ {input : '#update-serviceName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
				          {input : '#update-serviceName', message : '操作名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
				        ]
			});
			// 更新服务表单验证成功
			$("#updateServiceForm").on('validationSuccess', function (event) {
				
				var serviceForm = Core.parseJSON($("#updateServiceForm").serializeJson());
			    //得出的值多出个"",无法直接映射的处理办法。
			    var rowsConfig = $('#vUpdateConfigGrid').jqxGrid('getrows');
			    var rowConfig ={};
			    var rowsConfigNew = [];
			    for(var i = 0; i < rowsConfig.length; i++)
			    {
			        var row = rowsConfig[i];
			        rowConfig.name = row.name;
			        rowConfig.description = row.description;
			        rowConfig.dataType = row.dataType;
			        rowConfig.unit = row.unit;
			        rowConfig.value = row.value;
			        rowsConfigNew[i] = rowConfig;
			    }
				
			    var rowsSpec = $('#vUpdateSpecGrid').jqxGrid('getrows');
			    var rowSpec ={};
			    var rowsSpecNew = [];
			    for(var i = 0; i < rowsSpec.length; i++)
			    {
			        var row = rowsSpec[i];
			        rowSpec.name = row.name;
			        rowSpec.description = row.description;
			        rowSpec.dataType = row.dataType;
			        rowSpec.unit = row.unit;
			        rowSpec.valueDomain = row.valueDomain;
			        rowsSpecNew[i] = rowSpec;
			    }
			    
			    
			    var rowsPerformance = $('#vUpdatePerformanceGrid').jqxGrid('getrows');
			    var rowPerformance ={};
			    var rowsPerformanceNew = [];
			    for(var i = 0; i < rowsPerformance.length; i++)
			    {
			        var row = rowsPerformance[i];
			        rowPerformance.name = row.name;
			        rowPerformance.description = row.description;
			        rowPerformance.dataType = row.dataType;
			        rowPerformance.unit = row.unit;
			        rowPerformance.valueDomain = row.valueDomain;
			        rowPerformance.perfGroup = row.perfGroup;
			        rowPerformance.displayStyle = row.displayStyle;
			        rowsPerformanceNew[i] = rowPerformance;
			    }
			    
			    
			    var rowsOperate = $('#vUpdateOperateGrid').jqxGrid('getrows');
			    var rowOperate ={};
			    var rowsOperateNew = [];
			    for(var i = 0; i < rowsOperate.length; i++)
			    {
			        var row = rowsOperate[i];
			        rowOperate.name = row.name;
			        rowOperate.description = row.description;
			        rowOperate.type = row.type;
			        rowOperate.wsDefinition = row.wsDefinition;
			        rowsOperateNew[i] = rowOperate;
			    }

				var param = {};
				param.serviceConfig = rowsConfigNew;
				param.serviceSpec = rowsSpecNew;
				param.servicePerf = rowsPerformanceNew;
				param.serviceOperation = rowsOperateNew;
				param.parentCatalogSid = parentCatalogSid;
				
				var newObj = $.extend(serviceForm,param);
				
				 Core.AjaxRequest({
						url : ws_url + "/rest/services/platform/updateService",
						params :newObj,
						cache: false,
						callback : function (data) {
							Core.alert({
								title:"提示",
								message:"服务更新成功！",
								confirmCallback:function(){ 
									//$('#jqxTabsDefine').jqxTabs('select', 0);
									//model.searchServiceMgtContent;
								}
							});
					    },
					    failure:function(data){
					    	Core.alert({
								title:"提示",
								message:"服务更新失败！",
								confirmCallback:function(){
									//$("#" + addTempWindow).jqxWindow('close');
								}
							});
					    }
					});
			 });
	    };
	    
	    
	    
	    
	    
		
	
	/********************************************************保存服务***********************************************************/		
		
	/********************************************************保存服务***********************************************************/

	/********************************************************重置服务***********************************************************/
		$("#resetUpdateServiceBtn").click(function () {
			$("#update-serviceName").val("");
            $("#update-description").val("");
            
            //清除元素值
            $("#update-configName").val("");
            $("#update-configDescription").val("");
            $("#update-configDataType").val("");
            $("#update-configUnit").val("");
            $("#update-configValue").val("");
            $('#vUpdateConfigGrid').jqxGrid('clear');

	        //清除元素值
            $("#update-specName").val("");
            $("#update-specDescription").val("");
            $("#update-specDataType").val("");
            $("#update-specUnit").val("");
            $("#update-specValueDomain").val("");
            $('#vUpdateSpecGrid').jqxGrid('clear');

            //清除元素值
            $("#update-performanceName").val("");
            $("#update-performanceDescription").val("");
            $("#update-performanceDataType").val("");
            $("#update-performanceUnit").val("");
            $("#update-performanceValueDomain").val("");
            $("#update-performancePerfGroup").val("");
            $("#update-performanceDisplayStyle").val("");
            $('#vUpdatePerformanceGrid').jqxGrid('clear');

			//清除元素值
            $("#update-operateName").val("");
            $("#update-operateWsDefinition").val("");
            $("#update-operateType").val("");
            $("#update-operateDescription").val("");
            $('#vUpdateOperateGrid').jqxGrid('clear');
		});

	/********************************************************重置服务***********************************************************/
	/*****************************************************服务定义更新结束***********************************************************/
	    this.setEditServiceConfigForm = function(data){
//	    	 $("#edit-tenant-name").jqxInput({value:data.tenantName});
//	         $("#edit-tenant-contact").jqxInput({value:(data.contact == null)? "" :data.contact});
//	         $("#edit-tenant-contactPhone").jqxInput({value:(data.contactPhone == null)? "" :data.contactPhone});
//	         $("#edit-tenant-contactPosition").jqxInput({value:(data.contactPosition == null)? "" :data.contactPosition});
//	         $("#edit-tenant-email").jqxInput({value:(data.email == null)? "" :data.email});  
//	         $("#edit-tenant-postCode").jqxInput({value:(data.postCode == null)? "" :data.postCode});  
//	         $("#edit-tenant-domainName").jqxInput({value:(data.domainName == null)? "" :data.domainName});
//	         $("#edit-Tenant-domainAddress").jqxInput({value:(data.domainAddress == null)? "" :data.domainAddress});
//	         $("#edit-Tenant-serviceLimitQuantity").jqxNumberInput({value:(data.serviceLimitQuantity == null)? 0 :data.serviceLimitQuantity});
//	         $("#edit-Tenant-address").jqxInput({value:(data.address == null)? "" :data.address});
//	         $("#edit-Tenant-description").jqxInput({value:(data.description == null)? "" :data.description});
//	         
//	         $("#edit-tenant-type").val(data.tenantType);
//	         $("#edit-Tenant-status").val(data.status);
//	         $("#edit-Tenant-businessType").val(data.businessType);
//	         $("#edit-Tenant-vlan").val((data.vlan == null)?"":data.vlan);
	    };
  };
  
  // 新增服务定义
  function addServiceDefineItem() {
		var ok =  $('#defineAddBtn').jqxButton('disabled');
		if (!ok) {
			// 判断审核按钮是否可用
			var tabindex = $('#jqxTabsDefine').jqxTabs('length');
			var contentId = "view" + tabindex;
			var contentDiv = "<div id='" + contentId + "'></div>";
			var tabTitle = "新增服务";
			var sameTitle = false;
			for(var i = 1; i < tabindex; i++ ) {
				var title = $('#jqxTabsDefine').jqxTabs('getTitleAt', i);
				//相同的服务不添加tab了
				if(title == tabTitle) {
					$('#jqxTabsDefine').jqxTabs('select', i);
					sameTitle = true;
					break;
				}
			}
			
			if ($("#addConfigWindow").length > 0) {
				$("#addConfigWindow").remove();
			}
			if ($("#addSpecWindow").length > 0) {
				$("#addSpecWindow").remove();
			}
			if ($("#addPerformanceWindow").length > 0) {
				$("#addPerformanceWindow").remove();
			}
			if ($("#addOperateWindow").length > 0) {
				$("#addOperateWindow").remove();
			}
			
			if(!sameTitle) {
				$('#jqxTabsDefine').jqxTabs('addLast', tabTitle, contentDiv);
				$("#" + contentId).load(ctx + "/pages/service/service-add.jsp",
					{
						"serviceSid" : "",
						"navigationBarIdAdd" : "navigationBarIdAdd" + tabindex,
						"parentCatalogSid" : $('#catalogSid').val(),
						"parentCatalogName" : $('#catalogName').val()
					});
				}
		}
	}
  
    // 更新服务定义
   function updateServiceDefineItem() {
		var rowindex = $('#jqxgridDefine').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#defineDetailBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			
			var data = $('#jqxgridDefine').jqxGrid('getrowdata', rowindex);
			var tabindex = $('#jqxTabsDefine').jqxTabs('length');
			var contentId = "view" + tabindex;
			var contentDiv = "<div id='" + contentId + "'></div>";
			var tabTitle = '更新服务';
			
			var sameTitle = false;
			for(var i = 1; i < tabindex; i++ ) {
				var title = $('#jqxTabsDefine').jqxTabs('getTitleAt', i);
				//相同的服务不添加tab了
				if(title == tabTitle) {
					$('#jqxTabsDefine').jqxTabs('select', i);
					sameTitle = true;
					break;
				}
			}
			
			if ($("#updateConfigWindow").length > 0) {
				$("#updateConfigWindow").remove();
			}
			if ($("#updateSpecWindow").length > 0) {
				$("#updateSpecWindow").remove();
			}
			if ($("#updatePerformanceWindow").length > 0) {
				$("#updatePerformanceWindow").remove();
			}
			if ($("#updateOperateWindow").length > 0) {
				$("#updateOperateWindow").remove();
			}
			
			if(!sameTitle) {
				$('#jqxTabsDefine').jqxTabs('addLast', tabTitle, contentDiv);
				$("#" + contentId).load(ctx + "/pages/service/service-update.jsp",
					{
						"serviceSid" : data.serviceSid,
						"navigationBarIdUpdate" : "navigationBarIdUpdate" + tabindex,
						"parentCatalogSid" : $('#catalogSid').val(),
						"parentCatalogName" : $('#catalogName').val()
					});
				}
			}
			
	}
  
   // 查看服务定义
   function viewServiceDefineDetailInfo() {
		var rowindex = $('#jqxgridDefine').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#defineDetailBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			
			var data = $('#jqxgridDefine').jqxGrid('getrowdata', rowindex);
			var tabindex = $('#jqxTabsDefine').jqxTabs('length');
			var contentId = "view" + tabindex;
			var contentDiv = "<div id='" + contentId + "'></div>";
			var tabTitle = '查看-' + data.serviceName;
			
			var sameTitle = false;
			for(var i = 1; i < tabindex; i++ ) {
				var title = $('#jqxTabsDefine').jqxTabs('getTitleAt', i);
				//相同的服务不添加tab了
				if(title == tabTitle) {
					$('#jqxTabsDefine').jqxTabs('select', i);
					sameTitle = true;
					break;
				}
			}
			
			if(!sameTitle) {
				$('#jqxTabsDefine').jqxTabs('addLast', tabTitle, contentDiv);
				$("#" + contentId).load(ctx + "/pages/service/service-detail.jsp",
					{
						"serviceSid" : data.serviceSid,
						"jqxNavigationBarId" : "jqxNavigationBarId" + tabindex,
						"vConfigGrid" : "vConfigGrid" + tabindex,
						"vSpecGrid" : "vSpecGrid" + tabindex,
						"vPerformanceGrid" : "vPerformanceGrid" + tabindex,
						"vOperateGrid" : "vOperateGrid" + tabindex,
						"serviceName" : data.serviceName,
						"description" : data.description,
						"serviceTypeName" : data.serviceTypeName,
						"parentCatalogName" : data.parentCatalogName,
						"serviceStatusName" : data.serviceStatusName
					});
				}
			}
	}
   
   // 模板管理
   function defineTemplateMgtItem() {
		var rowindex = $('#jqxgridDefine').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#defineDetailBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			// 获取信息
			var data = $('#jqxgridDefine').jqxGrid('getrowdata', rowindex);
			// 得到tab页数
			var tabindex = $('#jqxTabsDefine').jqxTabs('length');
			
			if (tabindex > 1) {
				$('#jqxTabsDefine').jqxTabs('removeAt', 1);
			}
			
			if ($("#addTempWindow").length > 0) {
				$("#addTempWindow").remove();
			}
			
			
			//var contentId = "template" + data.serviceSid;
			//var contentId = "templateContentId";
			var contentDiv = "<div id='templateContentId'></div>";
			var tabTitle = '模板-' + data.serviceName;
			
			var loadPage = function (url, param) {
				$.post(url, param, function (data) {
                    //$("#templateContentId").text(data);
                    $('#jqxTabsDefine').jqxTabs('setContentAt', 1, data); 
                });
			};
			
			
			$('#jqxTabsDefine').jqxTabs('addLast', tabTitle, contentDiv);
			
			
			$('#jqxTabsDefine').jqxTabs('setTitleAt', 1, tabTitle); 
			$('#jqxTabsDefine').jqxTabs('select', 1);

			loadPage(ctx + "/pages/service/service-template.jsp",{
				"serviceSid" : data.serviceSid,
				"serviceName" : data.serviceName
			});
			
			
//			var sameTitle = false;
//			for(var i = 1; i < tabindex; i++ ) {
//				var title = $('#jqxTabsDefine').jqxTabs('getTitleAt', i);
//				if(title == tabTitle) {
//					$('#jqxTabsDefine').jqxTabs('select', i);
//					sameTitle = true;
//					break;
//				}
//			}
			//$('#jqxTabsDefine').jqxTabs('select', 1);
			//$('#jqxTabsDefine').jqxTabs('setTitleAt', 1, tabTitle);
			//没有相同的title才加载
			//if(!sameTitle) {
				//$('#jqxTabsDefine').jqxTabs('addLast', tabTitle, contentDiv);
//				$("#" + contentId).load(
//					ctx + "/pages/service/service-template.jsp", {
//						"serviceSid" : data.serviceSid,
//						"templategrid" : "templategrid" + data.serviceSid,
//						"serviceName" : data.serviceName,
//						"addTempWindow" : "addTempWindow" + data.serviceSid,
//						"addTempForm" : "addTempForm" + data.serviceSid,
//						"editTempWindow" : "editTempWindow" + data.serviceSid,
//						"editTempForm" : "editTempForm" + data.serviceSid,
//						"addTempSpecdatagrid" : "addTempSpecdatagrid" + data.serviceSid,
//						"addSave" : "addSave" + data.serviceSid,
//						"editTempSpecdatagrid" : "editTempSpecdatagrid" + data.serviceSid,
//						"editSave" : "editSave" + data.serviceSid,
//						"addTempBtn" : "addTempBtn" + data.serviceSid,
//						"editTempBtn" : "editTempBtn" + data.serviceSid,
//						"delTempBtn" : "delTempBtn" + data.serviceSid,
//						"add-templateName" : "add-templateName" + data.serviceSid,
//						"add-serviceName" : "add-serviceName" + data.serviceSid,
//						"add-templateStatus" : "add-templateStatus" + data.serviceSid,
//						"add-billingPlanSid" : "add-billingPlanSid" + data.serviceSid,
//						"add-imagePath" : "add-imagePath" + data.serviceSid,
//						"add-expiringDate" : "add-expiringDate" + data.serviceSid,
//						"add-description" : "add-description" + data.serviceSid,
//						"edit-templateName" : "edit-templateName" + data.serviceSid,
//						"edit-serviceName" : "edit-serviceName" + data.serviceSid,
//						"edit-templateStatus" : "edit-templateStatus" + data.serviceSid,
//						"edit-billingPlanSid" : "edit-billingPlanSid" + data.serviceSid,
//						"edit-imagePath" : "edit-imagePath" + data.serviceSid,
//						"edit-expiringDate" : "edit-expiringDate" + data.serviceSid,
//						"edit-description" : "edit-description" + data.serviceSid,
//					});
			//}

		}
	}
  
   // 发布服务
   function defineDeployItem() {
		var rowindex = $('#jqxgridDefine').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#defineDeployBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgridDefine').jqxGrid('getrowdata', rowindex);
			Core.confirm({
				title:"请选择",
				message:"您确定发布该服务吗？",
				confirmCallback:function(){
					Core.AjaxRequest({
		 				url : ws_url + "/rest/services/platform/operationService?serviceSid="+data.serviceSid+"&action="+"03",
		 				type:"get",
		 				callback : function (data) {
		 					Core.alert({
		 						title:"提示",
		 						message:"发布成功！",
		 						callback:function(){
		 							var serviceMgt = new serviceMgtModel();
		 							serviceMgt.refreshServiceMgtContent();
		 						}
		 					});
		 					
		 			    },
		 			    failure:function(data){
		 			    	Core.alert({
		 						title:"提示",
		 						message:"发布失败！",
		 						callback:function(){
		 							//user.searchUserInfo();
		 						}
		 					});
		 			    }
		 			});
				}
		   });
		}
  }
   
  // 禁用服务
  function defineDisableItem(){
	  var rowindex = $('#jqxgridDefine').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#defineDeployBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgridDefine').jqxGrid('getrowdata', rowindex);
			Core.confirm({
				title:"请选择",
				message:"您确定禁用该服务吗？",
				confirmCallback:function(){
					Core.AjaxRequest({
		 				url : ws_url + "/rest/services/platform/operationService?serviceSid="+data.serviceSid+"&action="+"05",
		 				type:"get",
		 				callback : function (data) {
		 					Core.alert({
		 						title:"提示",
		 						message:"禁用成功！",
		 						callback:function(){
		 							var serviceMgt = new serviceMgtModel();
		 							serviceMgt.refreshServiceMgtContent();
		 						}
		 					});
		 					
		 			    },
		 			    failure:function(data){
		 			    	Core.alert({
		 						title:"提示",
		 						message:"禁用失败！",
		 						callback:function(){
		 							//user.searchUserInfo();
		 						}
		 					});
		 			    }
		 			});
				}
			});
		}
  } 
   
  