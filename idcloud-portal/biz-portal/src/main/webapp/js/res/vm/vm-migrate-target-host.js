var resInsSid; 
var targetHostModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	   
	    // 给datagrid赋值
	    this.searchHostConfigInfo = function(resInstanceSid,resPoolSid){
	    	resInsSid=resInstanceSid;
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/hosts",
	 			type:'post',
	 			params:{
	 				resPoolSid:resPoolSid
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
	            height:250,
	            resizable: false,  
	            theme:currentTheme,
	            isModal: true, 
	            autoOpen: false, 
	            cancelButton: $("#targetHostCancel"), 
	            modalOpacity: 0.3,
	            initContent:function(){
	    	       
	    	        $("#targetHostSave").jqxButton({ width: '50',height:"25",theme:currentTheme,disabled:true});
	    	        $("#targetHostCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
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
	    
	    // 初始化用户datagrid的数据源
	    this.initHostDatagrid = function(){
	          $("#targetHostDatagrid").jqxGrid({
	              width: "100%",
	              height:170,
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
	                  { text: '主机名称', datafield: 'hostName'},
	                  { text: '主机型号', datafield: 'hostTypeName',width:80},
	                  { text: 'CPU(个)', datafield: 'cpuNumber',width:60},
	                  { text: 'CPU(核)', datafield: 'cpuCores',width:60},
	                  { text: '内存(MB)', datafield: 'memorySize',width:80},
	                  { text: 'IP地址', datafield: 'hostIp'},
	                  { text: '使用状态', datafield: 'usageStatusName'}
	              ]
	          });
              // 控制按钮是否可用
        	  $("#targetHostDatagrid").on('rowselect', function (event) {
        		  $("#targetHostSave").jqxButton({ width: '50',height:"25",theme:currentTheme,disabled:false});
        	  });
	    };
	    
  };
  
  
  
  
  // 选择目标存储
  function selectTargetStorage(){
	      // 检查datagrid是否选中了
		  var rowindex = $('#targetHostDatagrid').jqxGrid('getselectedrowindex');
	  	  if(rowindex >= 0){
	  		var data = $('#targetHostDatagrid').jqxGrid('getrowdata', rowindex);
	  		var targetStorage = new targetStorageModel();
	  		targetStorage.initPopWindow();
	  		targetStorage.initStorageDatagrid();
	  		targetStorage.searchStorageConfigInfo(resInsSid,data.resSid);
	  		// 设置弹出框位置
	  		var windowW = $(window).width();
	      	var windowH = $(window).height(); 
	      	$("#targetStorageWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-260)/2 } });
	      	$("#targetStorageWindow").jqxWindow('open');
	  	}
  }
  
