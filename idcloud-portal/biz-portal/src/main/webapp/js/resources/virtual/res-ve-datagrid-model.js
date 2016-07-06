var virtualVeDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    	 resTopologyNameLike:"",
	    	 virtualPlatformType:"",
	    	 managementAccountLike:"",
	    	 connectStatus:"",
	    	 resTopologyType:"VE",	
	    	 resTopologySid:resTopologySid
		};
	    // 查询用户名是否重复
	    this.searchVirtualVeByName = function(name){
	    	var Todata = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/user/findAll",
	 			type:'post',
	 			params:{
	 				accountRepeat:name
	 			},
	 			async:false,
	 			callback : function (data) {
	 				Todata = data;
	 			}
	 		 });
	    	return Todata;
	    };
	    // 用户数据
	    this.searchVirtualVeInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/ves",
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
	 			    $("#virtualVeDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-ve-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-mgt-account").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	       
	        // 初始化下拉列表框 
			 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			 codesearch.getCommonCode("search-ve-virtual-type","VIRTUAL_PLATFORM_TYPE",true);
			 codesearch.getCommonCode("search-ve-connect-status","CONNECT_STATUS",true);
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initVirtualVeDatagrid = function(){
	          $("#virtualVeDatagrid").jqxGrid({
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
	                  { text: '环境名称', datafield: 'resTopologyName'},
	                  { text: '虚拟化类型', datafield: 'virtualPlatformType',align: 'center',cellsalign:'center'},
	                  { text: '所属数据中心', datafield: 'parentTopologyName'},
	                  { text: '环境版本', datafield: 'virtualPlatformVersionName',width:100},
	                  { text: '管理地址', datafield: 'managementUrl',width:100},
	                  { text: '管理账户', datafield: 'managementAccount',width:100},
	                  { text: '连接状态', datafield: 'connectStatusName',width:100,align: 'center',cellsalign:'center'},
	                  { text: '更新状态', datafield: 'updateStatusName',width:100,align: 'center',cellsalign:'center'},
	                  { text: '更新周期(小时)', datafield: 'updateCycle',cellsalign:'right',width:100},
	                  { text: '最近更新时间', datafield: 'updateTime',width:110}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addBtn = $("<div><a id='jqxAddBtn' onclick='popAddVeWindow()' &nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editBtn = $("<div><a id='jqxEditBtn' onclick='popEditVeDataGridWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var deleteBtn = $("<div><a id='jqxDeleteBtn' onclick='removeVeDataGridItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
	                  var synBtn = $("<div><a id='jqxSynBtn' onclick='synVe()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-ccw'></i>同步&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addBtn);
	                  container.append(editBtn);
	                  container.append(deleteBtn);
	                  container.append(synBtn);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#virtualVeDatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#virtualVeDatagrid').jqxGrid('getrowdata', index);
	    		  
	    		  if(data.status == "2"){
	    			  $("#jqxEditBtn").jqxButton({ disabled: false });
		   			  $("#jqxDeleteBtn").jqxButton({disabled: false});
		   			  $("#jqxSynBtn").jqxButton({disabled: false});
	    		  }else{
	    			  $("#jqxEditBtn").jqxButton({disabled: false });
		   			  $("#jqxDeleteBtn").jqxButton({disabled: false});
		   			  $("#jqxSynBtn").jqxButton({disabled: false});
	    		  }
	          });
	    	  
	    	  $("#jqxEditBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
   			  $("#jqxDeleteBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
   			  $("#jqxSynBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
	          $("#jqxAddBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme});
	          
	    };
	    
	    // 初始化弹出window
		this.initPopWindow = function(){
//			$("#newfileUploadWindow").jqxWindow({
//		        width: 350, 
//		        height:158,
//		        theme:currentTheme,  
//		        resizable: false,  
//		        isModal: true, 
//		        autoOpen: false, 
//		        cancelButton: $("#importFileCancel"), 
//		        modalOpacity: 0.3,
//		        initContent:function(){
//		        	// 初始化新增用户页面组件
////		        	$("#fileAttach").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
////		        	$("#add-cluster-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
//			    	$("#importFile").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
//			    	$("#importFileCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
//		        }
//		    });
		}
  };
  
  //弹出新增资源环境
  function popAddVeWindow(){
	  // 清空先前新增资源环境的数据
	  // 初始化左边导航的选择
	  $("#virtualInfo").css({"color":"#0099d7"});
	  $("#confirmInfo").css({"color":"gray"});
	  // 初始化添加画面的位置
	  $("#veInfo").css("left","0px");
	  $("#confirmVeInfo").css("left","280px");
	  // 清空填入的数据
	  $("#add-ve-resTopologyName").val(null);
      $("#add-ve-managementUrl").val(null);
      $("#add-ve-managementAccount").val(null);
      $("#add-ve-managementPassword").val(null);
      $("#add-ve-confirmPassword").val(null);
      $("#add-ve-updateCycle").val(null);
      var codeadd = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth:150});
      codeadd.getCommonCode("add-ve-resTopologyType","RES_TOPOLOGY_TYPE");
      codeadd.getCommonCode("add-ve-virtualPlatformType","VIRTUAL_PLATFORM_TYPE");
      $("#add-ve-resTopologyType").val("VE");
      $("#add-ve-resTopologyType").jqxDropDownList({
    	  disabled:true
      });
      // 区域
      codeadd.getCustomCode("add-ve-zone","/topologys","resTopologyName","resTopologySid",false,"POST",{resTopologyType:"R"});
      $("#add-ve-zone").val(parentTopologySid);
      $("#add-ve-zone").jqxDropDownList({
		  disabled:true
	  });
      var zoneSid =  $("#add-ve-zone").val();
      
      // 数据中心
      codeadd.getCustomCode("add-ve-dc","/topologys","resTopologyName","resTopologySid",false,"POST",{parentTopologySid:zoneSid,resTopologyType:"DC"});
      $("#add-ve-dc").val(resTopologySid);
      $("#add-ve-dc").jqxDropDownList({
		  disabled:true
	  });
      
      // 资源环境
      var virtualType =  $("#add-ve-virtualPlatformType").val();
      codeadd.getCommonCodeByConditions("add-ve-virtualPlatformVersion",null,{parentCodeValue:virtualType});
	  
      
      
	  var windowW = $(window).width(); 
  	  var windowH = $(window).height(); 
  	  $("#addVeWindow").jqxWindow({ position: { x: (windowW-450)/2, y: (windowH-395)/2 } });
  	  $("#addVeWindow").jqxWindow('open');
  }
  
  // 弹出编辑资源环境Window
  function popEditVeDataGridWindow(){
	  var rowindex = $('#virtualVeDatagrid').jqxGrid('getselectedrowindex');
	  var ok =  $("#jqxEditBtn").jqxButton("disabled");
  	  if(rowindex >= 0 && !ok){
  		    var data = $('#virtualVeDatagrid').jqxGrid('getrowdata', rowindex);
  		    // 赋值
  		  $("#resTopologyName").html(data.resTopologyName);
			$("#virtualPlatformType").html(data.virtualPlatformType);
			$("#virtualPlatformVersion").html(data.virtualPlatformVersionName);
			$("#managementUrl").html(data.managementUrl);
			$("#managementAccount").html(data.managementAccount);
			$("#updateCycle").html(data.updateCycle);
			$("#description").html(data.description);
			
			// 给弹出编辑Window赋值
			$("#edit-ve-resTopologySid").val(data.resTopologySid);
			$("#edit-ve-resTopologyType").val(data.resTopologyType);
			$("#edit-ve-resTopologyName").val(data.resTopologyName);
	        $("#edit-ve-managementUrl").val(data.managementUrl);
	        $("#edit-ve-managementAccount").val(data.managementAccount);
	        $("#edit-ve-managementPassword").val(data.managementPassword);
	        $("#edit-ve-confirmPassword").val(data.managementPassword);
	        $("#edit-ve-updateCycle").val(data.updateCycle); 
	        $("#edit-ve-taskId").val(data.taskId); 
	        
	        $("#edit-ve-virtualPlatformType").val(data.virtualPlatformType); 
	        $("#edit-ve-virtualPlatformVersion").val(data.virtualPlatformVersion); 
	        
  	        // 弹出Window
  	        var windowW = $(window).width(); 
  	  	    var windowH = $(window).height(); 
  	  	    $("#editVeWindow").jqxWindow({ position: { x: (windowW-450)/2, y: (windowH-160)/2 } });
  	  	    $("#editVeWindow").jqxWindow('open');
  	  }
	   
  }
  
  // 查询资源环境
  function searchVE(){
	  var ve = new virtualVeDatagridModel();
	  ve.searchObj.resTopologyNameLike = $("#search-ve-name").val();
	  ve.searchObj.managementAccountLike = $("#search-mgt-account").val();
	  ve.searchObj.connectStatus = $("#search-ve-connect-status").val()=="全部"?"":$("#search-ve-connect-status").val();
	  ve.searchObj.virtualPlatformType = $("#search-ve-virtual-type").val()=="全部"?"":$("#search-ve-virtual-type").val(); 
  	  ve.searchVirtualVeInfo();
  }
  
  // 删除资源环境
  function removeVeDataGridItem(){
	  var rowindex = $('#virtualVeDatagrid').jqxGrid('getselectedrowindex');
	  var ok =  $("#jqxDeleteBtn").jqxButton("disabled");
  	  if(rowindex >= 0 && !ok){
  		      var data = $('#virtualVeDatagrid').jqxGrid('getrowdata', rowindex);
	  		  Core.confirm({
					title:"提示",
					message:"您确定要删除该资源环境吗？",
					confirmCallback:function(){
						Core.AjaxRequest({
			 				url : ws_url + "/rest/ves/delete/"+data.resTopologySid,
			 				type:"get",
			 				callback : function (data) {
			 					var ve = new virtualVeDatagridModel();
			 					ve.searchVirtualVeInfo();
			 			    }
			 			});
					}
			});
  		    
  	  }
  }
  
  //删除资源环境
  function removeVeInOverView(){
	  Core.confirm({
			title:"提示",
			message:"您确定要删除该资源环境吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
	 				url : ws_url + "/rest/ves/delete/"+resTopologySid,
	 				type:"get",
	 				callback : function (data) {
	 				// 刷新左边的Tree
	 					if($("#containerPool").length > 0){
	 						setPoolTreeValue();
	 					}else{
	 						setVirtualTreeValue();
	 						setStorageVirtualTreeValue();
	 					}
	 			    }
	 			});
			}
	  });
  }

  // 同步资源环境
  function synVe(){
	  var rowindex = $('#virtualVeDatagrid').jqxGrid('getselectedrowindex');
      var data = $('#virtualVeDatagrid').jqxGrid('getrowdata', rowindex);
		Core.AjaxRequest({
			url : ws_url + "/rest/ves/manualSyn",
			type:"post",
			params:{
				resTopologySid:data.resTopologySid
			},
			callback : function (data) {
//				var ve = new virtualVeDatagridModel();
//				ve.searchVirtualVeInfo();
		    }
		});
  		    
  }
  
  //同步资源环境--供非datagrid方法调用
  function synVeProvideToSummary(){
		Core.AjaxRequest({
			url : ws_url + "/rest/ves/manualSyn",
			type:"post",
			params:{
				resTopologySid:resTopologySid
			},
			callback : function (data) {
		    }
		});
  }  