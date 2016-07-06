var resVeSid; 
var vmData=new Array();
var migrateSelectModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	  
	    this.setData = function (datas){
	    	vmData=datas;
	    	//查询资源环境
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/vms/findVeByVm",
	 			type:'post',
	 			params:{
	 				resVmSid:datas[0]
	 			},
	 			async:false,
	 			callback : function (data) {
	 				resVeSid=data.resTopologySid;
	 			}
	 		 });
	    	
	    }
	    
	 // 初始化弹出window
	    this.initPopWindow = function(){
	    	$("#migrateSelectWindow").jqxWindow({
	            width: 350, 
	            height:185,
	            resizable: false,  
	            theme:currentTheme,
	            isModal: true, 
	            autoOpen: false, 
	            cancelButton: $("#vmMigrateCancel"), 
	            modalOpacity: 0.3,
	            initContent:function(){
	    	       
	            	$("#jqxRadioButton").jqxRadioButton({ width: 250, height: 25, theme:currentTheme,checked: true});
	                $("#jqxRadioButton2").jqxRadioButton({ width: 250, height: 25,theme:currentTheme});
	                $("#jqxRadioButton3").jqxRadioButton({ width: 250, height: 25,theme:currentTheme});
	            	
	            	
	    	        $("#vmMigrateSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	    	        $("#vmMigrateCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	            }
	        });
	    };
  };

  //根据选择弹出对应页面
  function vmMigrateSubmit(){
	  var hostFlag = $("#jqxRadioButton").jqxRadioButton('val');
	  var storageFlag = $("#jqxRadioButton2").jqxRadioButton('val');
	  var allFlag = $("#jqxRadioButton3").jqxRadioButton('val');
	  if(hostFlag || allFlag){
		  var targetHost = new targetHostModel();
		  targetHost.initPopWindow();
	  		targetHost.initHostDatagrid();
	  		targetHost.searchHostConfigInfo(vmData,allFlag);
	  		targetHost.initPopWindow();
	  		// 设置弹出框位置
	  		var windowW = $(window).width(); 
	      	var windowH = $(window).height(); 
	      	$("#targetHostWindow").jqxWindow({ position: { x: (windowW-800)/2,y: (windowH-300)/2 }});
	      	$("#targetHostWindow").jqxWindow('open');
	  }else if(storageFlag){
	  		var targetStorage = new targetStorageModel();
	  		targetStorage.initPopWindow();
	  		targetStorage.initStorageDatagrid();
	  		targetStorage.searchStorageConfigInfo(vmData,null);
	  		// 设置弹出框位置
	  		var windowW = $(window).width();
	      	var windowH = $(window).height(); 
	      	$("#targetStorageWindow").jqxWindow({ position: { x: (windowW-800)/2,y: (windowH-260)/2 } });
	      	$("#targetStorageWindow").jqxWindow('open');
	  }
  }
  
  //虚拟机迁移
  function migrateVm(vmData,targetHostSid,targetStorageSid){
	  Core.confirm({
			title : "提示",
			message : "确定迁移这些虚拟机吗？",
			confirmCallback : function() {
				Core.AjaxRequest({
					url : ws_url + "/rest/vms/migrateVm",
					type:'post',
					params:{
						vmData:vmData,
						targetHostSid:targetHostSid,
						targetStorageSid:targetStorageSid
					},
					async:false,
					callback : function (data) {
						$("#targetStorageWindow").jqxWindow('close');
						$("#targetHostWindow").jqxWindow('close');
						$("#migrateSelectWindow").jqxWindow('close');
						
						 // 清除列表选择项
						  $('#vmdatagrid').jqxGrid('clearselection');
						  // 带着查询条件刷新列表
						  searchVm();
					}
				 });
			}
	  });
}
