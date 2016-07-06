var vmDetailModel = function(){
	var me = this;
    this.items = ko.observableArray();
    
	 // 初始化虚拟机的存储
	  this.searchVmStorageInfo = function(resInstanceSid,name){
		  Core.AjaxRequest({
				url : ws_url + "/rest/resourceInstance/getResStoragesByOwner/"+resInstanceSid+"/"+name+"",
				type:"get",
				async:false,
				callback : function(data) {
					me.items(data);
	 				var sourcedatagrid ={
			              datatype: "json",
			              localdata: me.items
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#vmStorageDatagrid").jqxGrid({source: dataAdapter});
				}
			});
		 
	    };
	    
	    // 初始化用户datagrid的数据源
	    this.initVmStorageDatagrid = function(){
	          $("#vmStorageDatagrid").jqxGrid({
	              width: "98%",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 5, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '存储名称', datafield: 'resInstanceName'},
	                  { text: '存储用途', datafield: 'storagePurposeName'},
	                  { text: '存储大小', datafield: 'allocateDiskSize'},
	                  { text: '所属存储', datafield: 'allocateResStorageName'},
	                  { text: '使用状态', datafield: 'stoInsStatusName'}
	              ]
	          });
	    };
	
	// 设置基本信息
	this.setVmBasicInfo = function(data){
		$("#allocateVmName").html(data.allocateVmName);
		$("#statusName").html(data.statusName);
		$("#osTypeName").html(data.osTypeName);
		$("#tenantName").html(data.tenantName);
		$("#perfLevelName").html(data.perfLevelName);
		$("#realName").html(data.realName);
		$("#hostName1").html(data.hostName);
		$("#ip").html(data.ip);
		$("#cpuCores").html(data.cpuCores);
		$("#memorySize").html(data.memorySize);
	};
	
	 // 初始化window
    this.initPopWindow = function(){
    	// 虚拟机详情
    	$("#vmDetailWindow").jqxWindow({
            width: 800, 
            height:400,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#vmDetailCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
    	    	$("#vmDetailCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            }
        });
    };
};

// 弹出虚拟机详情window
function popVmDetailInfo(){
	var rowindex = $('#vmMgtdatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		    var data = $('#vmMgtdatagrid').jqxGrid('getrowdata', rowindex);
		    
		    var vmDetail = new vmDetailModel();
		    vmDetail.setVmBasicInfo(data);
		    vmDetail.searchVmStorageInfo(data.resInstanceSid,data.realName);
		    
		    var windowW = $(window).width(); 
		  	var windowH = $(window).height();
		  	$("#vmDetailWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-338)/2 } });
		  	$("#vmDetailWindow").jqxWindow('open');
	}
	
}



  
  