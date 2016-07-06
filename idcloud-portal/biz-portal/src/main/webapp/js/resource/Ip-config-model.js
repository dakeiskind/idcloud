var IpConfigModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		ipAddress: "", 
	    		ipCategory: "", 
	    		ipType:"",
	    		manageStatus:"",
	    		usageStatus:""
		};
	    // 用户数据
	    this.searchIpConfigInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/ips",
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
	 			    $("#ipConfigMgtdatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	    	var ipconfig = new codeModel({width:120,autoDropDownHeight:true});
	    	$("#search-IP-address").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	    	ipconfig.getCommonCode("search-IP-category","IP_CATEGORY",true);
	    	ipconfig.getCommonCode("search-IP-type","IP_TYPE",true);
	    	ipconfig.getCommonCode("search-IP-mgt-status","MANAGEMENT_STATUS",true);
	    	ipconfig.getCommonCode("search-IP-usage-status","USAGE_STATUS",true);
	        $("#search-ip-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	    	
	    };
	    // 初始化验证规则
	    this.initValidator = function(){
	    	
	    };
	    // 初始化用户datagrid的数据源
	    this.initIpDatagrid = function(){
	          $("#ipConfigMgtdatagrid").jqxGrid({
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
	                  { text: 'IP地址', datafield: 'ipAddress'},
	                  { text: 'IP类别', datafield: 'ipCategoryName'},
	                  { text: 'IP类型', datafield: 'ipTypeName'},
	                  { text: '对应公网地址', datafield: 'mapPublicIp'},
	                  { text: 'VLAN ID', datafield: 'vlanId'},
	                  { text: '备注', datafield: 'comments'},
	                  { text: '管理状态', datafield: 'manageStatusName'},
	                  { text: '使用状态', datafield: 'usageStatusName'}
	              ]
	          });
	          
	    };
	    // 初始化弹出window
	    this.initPopWindow = function(){

	    };

  };

  