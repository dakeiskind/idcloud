var ExchangeConfigModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.detailItems = ko.observableArray();
	    this.searchObj = {
	    		serviceAddress: "", 
	    		manageStatus:"",
	    		usageStatus:"",
	    		isResPoolSearch:resTopologyType,
	    		resTopologySid:resTopologySid
		};
	    // 给datagrid赋值
	    this.searchExchangeConfigInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/exchanges",
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
	 			    $("#exchangeConfigMgtdatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 给datagrid赋值
	    this.searchExchangeDetailInfo = function(allocateResExchangeSid){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/exchanges/details/"+allocateResExchangeSid+"",
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				me.detailItems(data);
	 				var sourcedatagrid ={
 			              datatype: "json",
 			              localdata: me.detailItems
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#exchangeDetailInfoDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 得到Exchange数据
	    this.getDataGridData = function(){
	    	 var exData;
	    	 Core.AjaxRequest({
	    		 	url : ws_url + "/rest/exchanges",
		 			type:'post',
		 			params:me.searchObj,
		 			async:false,
		 			callback : function (data) {
		 				exData = data;
		 			}
		     });
	    	 return exData;
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	    	$("#search-service-address").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:"metro"});
	    	
	    	var vlanconfig = new codeModel({width:120,autoDropDownHeight:true});
	    	vlanconfig.getCommonCode("search-exchange-mgt-status","MANAGEMENT_STATUS",true);
	    	vlanconfig.getCommonCode("search-exchange-usage-status","USAGE_STATUS",true);
	        $("#search-exchange-button").jqxButton({ width: '50px',height:'25px',theme:"metro"});
	    	
	    };
	    this.setEditExchangeForm = function(data){
	    	 $("#edit-serviceAddress").val(data.serviceAddress);
	         $("#edit-maxTenantCount").val(data.maxTenantCount);
	         $("#edit-maxStorageCapacity").val(data.maxStorageCapacity);
	    };
	    // 初始化验证规则   
	    this.initValidator = function(){
	    	$('#addExchangeForm').jqxValidator({
                rules: [  
                          { input: '#add-serviceAddress', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-serviceAddress', message: '服务地址不能超过128个字符', action: 'keyup, blur', rule: 'length=1,128' },
                          { input: '#add-serviceAddress', message: '必须输入英文或数字', action: 'keyup, blur', rule: function (input, commit) {
              	  			if(/[\u4E00-\u9FA5]/g.test(input.val())){
              	  				return false;
              	  			}else{
              	  				return true;
              	  			}
              	  		    }
		                  },
                          
                          { input: '#add-maxTenantCount', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-maxTenantCount', message: '最大分配租户数量名称不能超过8个字符', action: 'keyup, blur', rule: 'length=1,8' },
                          { input: '#add-maxTenantCount', message: '请必须输入数字', action: 'keyup, blur', rule:'number'},
                          
                          { input: '#add-maxStorageCapacity', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-maxStorageCapacity', message: '最大邮箱存储容量不能超过16个字符!', action: 'keyup, blur', rule: 'length=1,16' },
                          { input: '#add-maxStorageCapacity', message: '请必须输入数字', action: 'keyup, blur', rule:'number'},
                       ]
	    	});

	    	$('#editExchangeForm').jqxValidator({
                rules: [  
                          { input: '#edit-serviceAddress', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-serviceAddress', message: '服务地址不能超过128个字符', action: 'keyup, blur', rule: 'length=1,128' },
                          { input: '#edit-serviceAddress', message: '必须输入英文或数字', action: 'keyup, blur', rule: function (input, commit) {
              	  			if(/[\u4E00-\u9FA5]/g.test(input.val())){
              	  				return false;
              	  			}else{
              	  				return true;
              	  			}
              	  		    }
		                  },
                          
                          { input: '#edit-maxTenantCount', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-maxTenantCount', message: '最大分配租户数量名称不能超过8个字符!', action: 'keyup, blur', rule: 'length=1,8' },
                          { input: '#edit-maxTenantCount', message: '请必须输入数字', action: 'keyup, blur', rule:'number'},
                          
                          { input: '#edit-maxStorageCapacity', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-maxStorageCapacity', message: '最大邮箱存储容量不能超过16个字符!', action: 'keyup, blur', rule: 'length=1,16' },
                          { input: '#edit-maxStorageCapacity', message: '请必须输入数字', action: 'keyup, blur', rule:'number'},
                       ]
	    	});
	    	
	    	
	    	// 新增Exchange验证成功
	    	$('#addExchangeForm').on('validationSuccess', function (event) {
		    		 var exchange = Core.parseJSON($("#addExchangeForm").serializeJson());
		    		 exchange.resPoolSid = resTopologySid;
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/exchanges/create",
		 				params :exchange,
		 				callback : function (data) {
			 					var ex = new ExchangeConfigModel();
			 					ex.searchExchangeConfigInfo();
								$("#addExchangeWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	$("#addExchangeWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
	    	
	    	// 编辑Exchange验证成功
	    	$('#editExchangeForm').on('validationSuccess', function (event) {
	    		 var exchange = Core.parseJSON($("#editExchangeForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/exchanges/update",
		 				params :exchange,
		 				callback : function (data) {
		 					var ex = new ExchangeConfigModel();
		 					ex.searchExchangeConfigInfo();
							$("#editExchangeWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	$("#editExchangeWindow").jqxWindow('close');
		 			    }
		 			});
		      });
	    	
	    };
	    // 初始化用户datagrid的数据源
	    this.initExchangeDatagrid = function(){
	    	 // Exchange详情
	    	 $("#exchangeDetailInfoDatagrid").jqxGrid({
	              width: "100%",
				  theme:"metro",
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '分配域名', datafield: 'allocateDomain',width:200},
	                  { text: '用户数量', datafield: 'userAmount',width:100},
	                  { text: '已使用用户数量', datafield: 'usedUserCount',width:120},
	                  { text: '单用户邮箱容量', datafield: 'singleMailboxCapacity',width:120},
	                  { text: '所属租户', datafield: 'tenantName'}
	              ]
	          });
	    	
	    	 // exchange的datagrid
	          $("#exchangeConfigMgtdatagrid").jqxGrid({
	              width: "100%",
				  theme:"metro",
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '服务地址', datafield: 'serviceAddress',width:150},
	                  { text: '最大分配租户数量', datafield: 'maxTenantCount',width:150},
	                  { text: '最大邮箱存储容量', datafield: 'maxStorageCapacity',width:80},
	                  { text: '管理状态', datafield: 'manageStatusName'},
	                  { text: '使用状态', datafield: 'usageStatusName'}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addExchange = $("<div><a id='addExchange' onclick='addExchangeInfo()'>&nbsp;&nbsp;<i class='icon-plus'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editExchange = $("<div><a id='editExchange' style='margin-left:-1px' onclick='editExchangeInfo()'>&nbsp;&nbsp;<i class='icon-pencil'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var detailExchange = $("<div><a id='detailExchange' onclick='detailExchangeInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-list-bullet'></i>地址分配列表&nbsp;&nbsp;</a></div>");
	                  var removeExchange = $("<div><a id='removeExchange' onclick='removeExchangeInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-cancel'></i>删除&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addExchange);
	                  container.append(editExchange);
	                  container.append(detailExchange);
	                  container.append(removeExchange);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#exchangeConfigMgtdatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#exchangeConfigMgtdatagrid').jqxGrid('getrowdata', index);
	   			  $("#editExchange").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: false});
	   			  $("#detailExchange").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: false});
	   			  $("#removeExchange").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: false});
	          });
	    	  
	    	  $("#addExchange").jqxButton({width: '60',theme:"metro",height: '18px', disabled: false });
   			  $("#editExchange").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: true});
   			  $("#detailExchange").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: true});
   			  $("#removeExchange").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: true});
	          
	    };
	    // 初始化弹出window
	    this.initPopWindow = function(){
	    	$("#addExchangeWindow").jqxWindow({
                width: 550, 
                height:150,
                theme:currentTheme,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addExchangeCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#add-serviceAddress").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-maxTenantCount").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-maxStorageCapacity").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#addExchangeSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#addExchangeCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
                }
            });
           
	    	$("#editExchangeWindow").jqxWindow({
                width: 550, 
                height:150,
                theme:currentTheme,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#editExchangeCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#edit-serviceAddress").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#edit-maxTenantCount").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#edit-maxStorageCapacity").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#editExchangeSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#editExchangeCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
                }
            });
	    	
	    	$("#exchangeDetailInfoWindow").jqxWindow({
                width: 800, 
                height:400,
                theme:currentTheme,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#exchangeDetailInfoCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#exchangeDetailInfoCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
                }
            });
	    };
	    
	    // Sharepoint资源统计
	    this.exResourcesStatistics = function(){
			var exData =  me.getDataGridData();
			
			var data = new Object();
			data.exCount = exData.length;
			data.attr = new Array();
			
			var value = [0,0];
			var name =["未使用","已使用"];
			for(var i=0;i<exData.length;i++){
				
				if("未使用" == exData[i].usageStatusName){
					// 未使用
					value[0] += 1; 
				}else if("已使用" == exData[i].usageStatusName){
					// 已使用
					value[1] += 1; 
				}
			}
			for(var i=0; i<2;i++){
				var obj = new Object();
				obj.name = name[i];
				obj.value = value[i];
				data.attr.push(obj);
			}
			return data;
	    };
  };
  
  //弹出新增Exchange window
	function addExchangeInfo(){
		// 初始化新增用户页面组件
        $("#add-serviceAddress").val(null);
        $("#add-maxTenantCount").val(null);
        $("#add-maxStorageCapacity").val(null);
        
		var windowW = $(window).width(); 
	  	var windowH = $(window).height(); 
	  	$("#addExchangeWindow").jqxWindow({ position: { x: (windowW-550)/2, y: (windowH-150)/2 } });
	  	$("#addExchangeWindow").jqxWindow('open');
	};
	
	// 提交新增Exchange信息
	function addExchangeSubmit(){
		$('#addExchangeForm').jqxValidator('validate');
	};
	
	// 弹出编辑Exchange window
	function editExchangeInfo(){
		var rowindex = $('#exchangeConfigMgtdatagrid').jqxGrid('getselectedrowindex');
	  	if(rowindex >= 0){
	  		var data = $('#exchangeConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
	  		// 为编辑赋值
	  		$("#resSidExchange").val(data.resSid);
	  		var exchange = new ExchangeConfigModel();
	  		exchange.setEditExchangeForm(data);
	  		// 设置window显示位置
	  		var windowW = $(window).width(); 
	      	var windowH = $(window).height(); 
	      	$("#editExchangeWindow").jqxWindow({ position: { x: (windowW-550)/2, y: (windowH-150)/2 } });
	      	$("#editExchangeWindow").jqxWindow('open');
	  	}
		
	};
	
	// 删除exchange信息
     function removeExchangeInfo(){
		var rowindex = $('#exchangeConfigMgtdatagrid').jqxGrid('getselectedrowindex');
	  	if(rowindex >= 0){
	  		var data = $('#exchangeConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
	  		Core.confirm({
					title:"提示",
					message:"确定要删除该Exchange服务吗?",
					yes:"确定",
					confirmCallback:function(){
						Core.AjaxRequest({
			 				url : ws_url + "/rest/exchanges/delete/"+data.resSid+"",
			 				type:"get",
			 				callback : function (data) {
			 					var exchange = new ExchangeConfigModel();
			 					exchange.searchExchangeConfigInfo();
			 			    },
			 			    failure:function(data){
			 			    	
			 			    }
			 			});
					}
			    });
	  	}
	};
	
	// 提交编辑Exchange信息
	function editExchangeSubmit(){
		$('#editExchangeForm').jqxValidator('validate');
	};
	
	// 条件查询exchange配置管理
	function searchExchangeConfigMgt(){
		var exchange = new ExchangeConfigModel();
		exchange.searchObj.serviceAddress = $("#search-service-address").val();
		exchange.searchObj.manageStatus = $("#search-exchange-mgt-status").val()=="全部"?"":$("#search-exchange-mgt-status").val();
		exchange.searchObj.usageStatus = $("#search-exchange-usage-status").val()=="全部"?"":$("#search-exchange-usage-status").val();
		exchange.searchObj.isResPoolSearch = resTopologyType ;
		exchange.searchObj.resTopologySid = resTopologySid ;
		exchange.searchExchangeConfigInfo();
	};
	
	// 弹出Exchange详细信息window
	function detailExchangeInfo(){
		var windowW = $(window).width(); 
	  	var windowH = $(window).height(); 
	  	// 给Exchange详情的datagrid赋值
	    var rowindex = $('#exchangeConfigMgtdatagrid').jqxGrid('getselectedrowindex');
	    if(rowindex >= 0){
	  		var data = $('#exchangeConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
	  		var exchange = new ExchangeConfigModel();
	  		exchange.searchExchangeDetailInfo(data.resSid);
	  		$("#exchangeDetailInfoWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-400)/2 } });
		  	$("#exchangeDetailInfoWindow").jqxWindow('open');
	    }
	}
  