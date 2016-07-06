var resInsSid; 
var resVeTargetHost;
var vmDatas=new Array();
var targetHostModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	   
	    // 给datagrid赋值
	    this.searchHostConfigInfo = function(vmData,allFlag){
	    	vmDatas=vmData;
	    	if(allFlag){
	    		resVeTargetHost=resVeSid;
 	    	   $("#nextButtonDiv").css("display","inline");
 	    	   $("#migrateHostButtonDiv").css("display","none");
 	    	  Core.AjaxRequest({
		 			url : ws_url + "/rest/host",
		 			type:'post',
		 			params:{
		 				resTopologySid:resVeSid
		 			},
		 			async:false,
		 			callback : function (data) {
		 				me.items(data);
		 				var sourcedatagrid ={
	 			              datatype: "json",
	 			              localdata: me.items
		 			    };
		 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		 			    $("#targetHostDatagrid").jqxGrid({source: dataAdapter});
		 			}
		 		 });
 	       }else{
 	    	   $("#migrateHostButtonDiv").css("display","inline");
 	    	   $("#nextButtonDiv").css("display","none");
 	    	  Core.AjaxRequest({
 		 			url : ws_url + "/rest/vms/findCommonHostByVms",
 		 			type:'post',
 		 			params:{
 		 				vmData:vmData
 		 			},
 		 			async:false,
 		 			callback : function (data) {
 		 				me.items(data);
 		 				var sourcedatagrid ={
 	 			              datatype: "json",
 	 			              localdata: me.items
 		 			    };
 		 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 		 			    $("#targetHostDatagrid").jqxGrid({source: dataAdapter});
 		 			}
 		 		 });
 	       }
	    };
	   
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initMigrateInput = function(){
	        
	        $("#search-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme}); 
	        // 初始化下拉列表框 
			 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			 var params={parentTopologySid:resTopologySid};
			 codesearch.getCustomCode("search-cluster","/vcs","clusterName","resTopologySid",true,"post",params);
			};
		
			// 根据集群查询主机
	    this.searchTargetHosts=function(resVeSid){
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/host",
	 			type:'post',
	 			params:{
	 				resTopologySid:resVeSid
	 			},
	 			async:false,
	 			callback : function (data) {
	 				me.items(data);
	 				var sourcedatagrid ={
 			              datatype: "json",
 			              localdata: me.items
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#targetHostDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
			
			
	 // 初始化弹出window
	    this.initPopWindow = function(){
	    	$("#targetHostWindow").jqxWindow({
	            width: 900, 
	            height:300,
	            resizable: false,  
	            theme:currentTheme,
	            isModal: true, 
	            autoOpen: false, 
	            cancelButton: $("#targetHostCancel"), 
	            modalOpacity: 0.3,
	            initContent:function(){
	            	$("#targetHostSave").jqxButton({ width: '50',height:"25",theme:currentTheme,disabled:true});
	    	        $("#migrateVmHostSave").jqxButton({ width: '50',height:"25",theme:currentTheme,disabled:true});
	    	        $("#targetHostCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	            }
	        });
	    };
	    
	    this.getDataGridData = function(){
	    	 var hostData;
	    	 Core.AjaxRequest({
		 			url : ws_url + "/rest/vms/findCommonHostByVms",
		 			type:'post',
		 			params:me.searchObj,
		 			async:false,
		 			callback : function (data) {
		 				hostData = data;
		 			}
		     });
	    	 return hostData;
	    };
	    
	    // 初始化用户datagrid的数据源
	    this.initHostDatagrid = function(){
	          $("#targetHostDatagrid").jqxGrid({
	              width: "100%",
	              height:170,
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: false,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '主机名称', datafield: 'hostName'},
	                  { text: 'CPU(核)', datafield: 'cpuCores'},
	                  { text: 'CPU使用率', datafield: 'hostCpuUsage',cellsalign:'right',width:80},
	                  { text: '内存(GB)', datafield: 'memorySizeGb',cellsalign:'right',width:60},
	                  { text: '内存使用率', datafield: 'hostMemoryUsage',cellsalign:'right',width:80},
	                  { text: '状态', datafield: 'statusName'},
	              ]
	          });
              // 控制按钮是否可用
        	  $("#targetHostDatagrid").on('rowselect', function (event) {
        		  $("#migrateVmHostSave").jqxButton({ width: '50',height:"25",theme:currentTheme,disabled:false});
        		  $("#targetHostSave").jqxButton({ width: '50',height:"25",theme:currentTheme,disabled:false});
        	  });
	    };
	    
  };
  
  //只迁移主机
  function migrateVmHost(){
	  var rowindex = $('#targetHostDatagrid').jqxGrid('getselectedrowindex');
	  if(rowindex >= 0){
			var data = $('#targetHostDatagrid').jqxGrid('getrowdata', rowindex);
			migrateVm(vmDatas,data.resHostSid,null);
	  }
	 
  }
  
  
  
  // 选择目标存储
  function selectTargetStorage(){
	      // 检查datagrid是否选中了
		  var rowindex = $('#targetHostDatagrid').jqxGrid('getselectedrowindex');
	  	  if(rowindex >= 0){
	  		var data = $('#targetHostDatagrid').jqxGrid('getrowdata', rowindex);
	  		var targetStorage = new targetStorageModel();
	  		targetStorage.initPopWindow();
	  		targetStorage.initStorageDatagrid();
	  		targetStorage.searchStorageConfigInfo(vmData,data.resHostSid);
	  		// 设置弹出框位置
	  		var windowW = $(window).width();
	      	var windowH = $(window).height(); 
	      	$("#targetStorageWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-260)/2 } });
	      	$("#targetStorageWindow").jqxWindow('open');
	  	}
  }
  
	 //根据集群查询主机
	function searchMigrateHost(){
		resClusterSid=$("#search-cluster").val();
		if(!("全部"==resClusterSid)){
			resVeSid=resClusterSid;
		}else{
			resVeSid=resVeTargetHost;
		}
		var model=new targetHostModel();
		model.searchTargetHosts(resVeSid);
	
	  };
