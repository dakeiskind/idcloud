var SharepointConfigModel = function () {
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
	    this.searchSharepointConfigInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/sharepoints",
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
	 			    $("#sharepointConfigMgtdatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 给datagrid赋值
	    this.searchSharepointDetailInfo = function(resInstanceSid){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/sharepoints/details/"+resInstanceSid+"",
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				me.detailItems(data);
	 				var sourcedatagrid ={
 			              datatype: "json",
 			              localdata: me.detailItems
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#sharepointDetailInfoDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 得到Sharepoint数据
	    this.getDataGridData = function(){
	    	 var spData;
	    	 Core.AjaxRequest({
	    		 	url : ws_url + "/rest/sharepoints",
		 			type:'post',
		 			params:me.searchObj,
		 			async:false,
		 			callback : function (data) {
		 				spData = data;
		 			}
		     });
	    	 return spData;
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	    	$("#search-sharepoint-service-address").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:"metro"});
	    	
	    	var vlanconfig = new codeModel({width:120,autoDropDownHeight:true});
	    	vlanconfig.getCommonCode("search-sharepoint-mgt-status","MANAGEMENT_STATUS",true);
	    	vlanconfig.getCommonCode("search-sharepoint-usage-status","USAGE_STATUS",true);
	        $("#search-sharepoint-button").jqxButton({ width: '50px',height:'25px',theme:"metro"});
	    	
	    };
	    this.setEditSharepointForm = function(data){
	    	 $("#edit-sharepoint-serviceAddress").val(data.serviceAddress);
	         $("#edit-sharepoint-maxStorageCapacity").val(data.maxStorageCapacity);
	    };
	    // 初始化验证规则   
	    this.initValidator = function(){
	    	$('#addSharepointForm').jqxValidator({
                rules: [  
                          { input: '#add-sharepoint-serviceAddress', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-sharepoint-serviceAddress', message: '服务地址不能超过128个字符', action: 'keyup, blur', rule: 'length=1,128' },
                          { input: '#add-sharepoint-serviceAddress', message: ' 必须输入英文或数字', action: 'keyup, blur', rule: function (input, commit) {
              	  			if(/[\u4E00-\u9FA5]/g.test(input.val())){
              	  				return false;
              	  			}else{
              	  				return true;
              	  			}
              	  		    }
		                  },
                          
                          { input: '#add-sharepoint-maxStorageCapacity', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-sharepoint-maxStorageCapacity', message: '最大分配租户数量名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          { input: '#add-sharepoint-maxStorageCapacity', message: '请必须输入数字', action: 'keyup, blur',rule: function (input, commit) {
              	  			if(input.val().replace(/[^\d]/g,'')){
              	  				return true;
              	  			}else{
              	  				return false;
              	  			}
              	  		}
		                  }
                       ]
	    	});

	    	$('#editSharepointForm').jqxValidator({
                rules: [  
                          { input: '#edit-sharepoint-serviceAddress', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-sharepoint-serviceAddress', message: '服务地址不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' },
                          { input: '#edit-sharepoint-serviceAddress', message: ' 必须输入英文或数字', action: 'keyup, blur', rule: function (input, commit) {
                	  			if(/[\u4E00-\u9FA5]/g.test(input.val())){
                	  				return false;
                	  			}else{
                	  				return true;
                	  			}
                	  		}
		                  },
                          
                          { input: '#edit-sharepoint-maxStorageCapacity', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-sharepoint-maxStorageCapacity', message: '最大分配租户数量名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                          { input: '#edit-sharepoint-maxStorageCapacity', message: '请必须输入数字', action: 'keyup, blur',rule: function (input, commit) {
                	  			if(input.val().replace(/[^\d]/g,'')){
                	  				return true;
                	  			}else{
                	  				return false;
                	  			}
                	  		}
  		                  }
                       ]
	    	});
	    	
	    	// 新增Sharepoint验证成功
	    	$('#addSharepointForm').on('validationSuccess', function (event) {
		    		 var Sharepoint = Core.parseJSON($("#addSharepointForm").serializeJson());
		    		 Sharepoint.resPoolSid = resTopologySid;
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/sharepoints/create",
		 				params :Sharepoint,
		 				callback : function (data) {
		 					    var sp = new SharepointConfigModel(); 	
		 					    sp.searchSharepointConfigInfo();
								$("#addSharepointWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	$("#addSharepointWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
	    	
	    	// 编辑Sharepoint验证成功
	    	$('#editSharepointForm').on('validationSuccess', function (event) {
	    		 var Sharepoint = Core.parseJSON($("#editSharepointForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/sharepoints/update",
	 				params :Sharepoint,
	 				callback : function (data) {
	 					var sp = new SharepointConfigModel();
	 					sp.searchSharepointConfigInfo();
						$("#editSharepointWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
	 			    	$("#editSharepointWindow").jqxWindow('close');
	 			    }
	 			});
		         });
	    	
	    };
	    // 初始化用户datagrid的数据源
	    this.initSharepointDatagrid = function(){
	    	// Sharepoint详情
	    	 // Exchange详情
	    	 $("#sharepointDetailInfoDatagrid").jqxGrid({
	              width: "100%",
				  theme:"metro",
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 5, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '分配地址', datafield: 'allocateSharepointAddress'},
	                  { text: '分配存储容量', datafield: 'allocateStorageCapacity',width:120},
	                  { text: '所属租户', datafield: 'tenantName',width:120}
	              ]
	          });
	    	// Sharepoint的列表信息
	          $("#sharepointConfigMgtdatagrid").jqxGrid({
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
	                  // sunyu removed for #227
	                  //{ text: '最大分配租户数量', datafield: 'maxTenantCount',width:150},
	                  // end
	                  { text: '最大邮箱存储容量', datafield: 'maxStorageCapacity'},  
	                  { text: '管理状态', datafield: 'manageStatusName'},
	                  { text: '使用状态', datafield: 'usageStatusName'}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addSharepoint = $("<div><a id='addSharepoint' onclick='addSharepointInfo()'>&nbsp;&nbsp;<i class='icon-plus'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editSharepoint = $("<div><a id='editSharepoint' style='margin-left:-1px' onclick='editSharepointInfo()'>&nbsp;&nbsp;<i class='icon-pencil'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var detailSharepoint = $("<div><a id='detailSharepoint' onclick='detailSharepointInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-list-bullet'></i>地址分配列表&nbsp;&nbsp;</a></div>");
	                  var removeSharepoint = $("<div><a id='removeSharepoint' onclick='removeSharepointInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-cancel'></i>删除&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addSharepoint);
	                  container.append(editSharepoint);
	                  container.append(detailSharepoint);
	                  container.append(removeSharepoint);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#sharepointConfigMgtdatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#sharepointConfigMgtdatagrid').jqxGrid('getrowdata', index);
	   			  $("#editSharepoint").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: false});
	   			  $("#detailSharepoint").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: false});
	   			  $("#removeSharepoint").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: false});
	          });
	    	  
	    	  $("#addSharepoint").jqxButton({width: '60',theme:"metro",height: '18px', disabled: false });
   			  $("#editSharepoint").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: true});
   			  $("#detailSharepoint").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: true});
   			  $("#removeSharepoint").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: true});
	          
	    };
	    // 初始化弹出window
	    this.initPopWindow = function(){
	    	$("#addSharepointWindow").jqxWindow({
                width: 500, 
                height:120,
                theme:currentTheme,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addSharepointCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#add-sharepoint-serviceAddress").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-sharepoint-maxStorageCapacity").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#addSharepointSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#addSharepointCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
                }
            });
           
	    	$("#editSharepointWindow").jqxWindow({
                width: 500, 
                height:120,
                theme:currentTheme,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#editSharepointCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#edit-sharepoint-serviceAddress").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#edit-sharepoint-maxStorageCapacity").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#editSharepointSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#editSharepointCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
                }
            });
	    	
	    	$("#sharepointDetailInfoWindow").jqxWindow({
                width: 600, 
                height:260,
                theme:currentTheme,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#sharepointDetailInfoCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#sharepointDetailInfoCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
                }
            });
	    };
	    
	    // Sharepoint资源统计
	    this.spResourcesStatistics = function(){
			var spData =  me.getDataGridData();
			
			var data = new Object();
			data.spCount = spData.length;
			data.attr = new Array();
			
			var value = [0,0];
			var name =["未使用","已使用"];
			for(var i=0;i<spData.length;i++){
				
				if("未使用" == spData[i].usageStatusName){
					// 未使用
					value[0] += 1; 
				}else if("已使用" == spData[i].usageStatusName){
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
  
  	//弹出新增Sharepoint window
	function addSharepointInfo(){
		$("#add-sharepoint-serviceAddress").val(null);
	    $("#add-sharepoint-maxStorageCapacity").val(null);
		var windowW = $(window).width(); 
	  	var windowH = $(window).height(); 
	  	$("#addSharepointWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-150)/2 } });
	  	$("#addSharepointWindow").jqxWindow('open');
	};
	
	// 提交新增Sharepoint信息
	function addSharepointSubmit(){
		$('#addSharepointForm').jqxValidator('validate');
	};
	
	// 弹出编辑Sharepoint window
    function editSharepointInfo(){
		var rowindex = $('#sharepointConfigMgtdatagrid').jqxGrid('getselectedrowindex');
	  	if(rowindex >= 0){
	  		var data = $('#sharepointConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
	  		// 为编辑赋值
	  		$("#resSidSharepoint").val(data.resSid);
	  		var sharepoint = new SharepointConfigModel();
	  		sharepoint.setEditSharepointForm(data);
	  		// 设置window显示位置
	  		var windowW = $(window).width(); 
	      	var windowH = $(window).height(); 
	      	$("#editSharepointWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-150)/2 } });
	      	$("#editSharepointWindow").jqxWindow('open');
	  	}
		
	};
	
	// 删除Sharepoint信息
	 function removeSharepointInfo(){
		var rowindex = $('#sharepointConfigMgtdatagrid').jqxGrid('getselectedrowindex');
	  	if(rowindex >= 0){
	  		var data = $('#sharepointConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
	  		Core.confirm({
					title:"提示",
					message:"确定要删除该Sharepoint服务吗?",
					yes:"确定",
					confirmCallback:function(){
						Core.AjaxRequest({
			 				url : ws_url + "/rest/sharepoints/delete/"+data.resSid+"",
			 				type:"get",
			 				callback : function (data) {
			 					var sharepoint = new SharepointConfigModel();
			 					sharepoint.searchSharepointConfigInfo();
			 			    },
			 			    failure:function(data){
			 			    	
			 			    }
			 			});
					}
			    });
	  	}
	};
	
	// 提交编辑Sharepoint信息
	function editSharepointSubmit(){
		$('#editSharepointForm').jqxValidator('validate');
	};
	
	// 条件查询Sharepoint配置管理
	function searchSharepointConfigMgt(){
		var sharepoint = new SharepointConfigModel();
		sharepoint.searchObj.serviceAddress = $("#search-sharepoint-service-address").val();
		sharepoint.searchObj.manageStatus = $("#search-sharepoint-mgt-status").val()=="全部"?"":$("#search-sharepoint-mgt-status").val();
		sharepoint.searchObj.usageStatus = $("#search-sharepoint-usage-status").val()=="全部"?"":$("#search-sharepoint-usage-status").val();
		sharepoint.searchObj.isResPoolSearch = resTopologyType ;
		sharepoint.searchObj.resTopologySid = resTopologySid ;
		sharepoint.searchSharepointConfigInfo();
	};
  
	// 查询Sharepoint详情
	function detailSharepointInfo(){
		var windowW = $(window).width(); 
	  	var windowH = $(window).height(); 
	  
	  	// 给Sharepoint详情的datagrid赋值
	    var rowindex = $('#sharepointConfigMgtdatagrid').jqxGrid('getselectedrowindex');
	    if(rowindex >= 0){
	  		var data = $('#sharepointConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
	  		var sharepoint = new SharepointConfigModel();
	  		sharepoint.searchSharepointDetailInfo(data.resSid);
	  		$("#sharepointDetailInfoWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-260)/2 } });
		  	$("#sharepointDetailInfoWindow").jqxWindow('open');
	    }
	  	
	}
	