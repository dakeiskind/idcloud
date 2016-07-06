
var resVmSid;

var vmDetailModel = function(){
	var me = this;
    this.items = ko.observableArray();
    
	 // 初始化虚拟机的存储
	  this.searchVmStorageInfo = function(resVmSid){
		  Core.AjaxRequest({
				url : ws_url + "/rest/vds",
				type:"post",
				async:false,
				params:{
					resVmSid:resVmSid
				},
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
//	              pageable: true, 
//	              pagesize: 5, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '存储名称', datafield: 'vdName'},
//	                  { text: '存储用途', datafield: 'storagePurposeName'},
	                  { text: '存储大小(GB)', datafield: 'allocateDiskSize'}
//	                  { text: '所属存储', datafield: 'allocateResStorageName'},
//	                  { text: '使用状态', datafield: 'stoInsStatusName'}
	              ]
	          });
	    };

		 // 初始化虚拟机的IP
		  this.searchVmNicInfo = function(resVmSid){
			  Core.AjaxRequest({
					url : ws_url + "/rest/vmNetworks",
					type:"post",
					async:false,
					params:{
						resVmSid:resVmSid
					},
					callback : function(data) {
						me.items(data);
		 				var sourcedatagrid ={
				              datatype: "json",
				              localdata: me.items
		 			    };
		 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		 			    $("#vmNicDatagrid").jqxGrid({source: dataAdapter});
					}
				});
			 
		    };

	    
	    // 初始化用户datagrid的数据源
	    this.initVmNicDatagrid = function(){
	          $("#vmNicDatagrid").jqxGrid({
	              width: "98%",
				  theme:currentTheme,
	              columnsresize: true,
//	              pageable: true, 
//	              pagesize: 5, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: 'IP地址', datafield: 'ipAddress'}
	              ]
	          });
	    };
	    
	// 设置基本信息
	this.setVmBasicInfo = function(data){
		Core.AjaxRequest({
			url : ws_url + "/rest/vms/getVmInfo",
			type:"post",
			async:false,
			params:{
				resVmSid:data.resBizVmSid
			},
			callback : function(data) {
				$("#allocateVmName").html(data.vmName);
				$("#statusName").html(data.statusName);
				$("#osTypeName").html(data.osVersion);
				$("#tenantName").html(data.tenantName);
				$("#hostName1").html(data.ownerHost);
				$("#ip").html(data.vmIp);
				$("#cpuCores").html(data.cpuCores);
				$("#memorySize").html(data.memorySize);
			}
		});

	};
	
//    // 初始化虚拟机的监控信息
//    this.searchVmInfo = function(resVmSid){
//    	Core.AjaxRequest({
//    		url : ws_url + "/rest/vms/getVmCurrentInfo",
//    		type:"post",
//    		async:false,
//    		params:{
//    			resVmSid:resVmSid
//    		},
//    		callback : function(data) {
//    			 $("#cpuUsage").html(Math.round(data.cpuUsage*100)/100/100+"%");
//    			 $("#memUsage").html(Math.round(data.memUsage*100)/100+"%");
//    			 $("#diskUsage").html(Math.round(data.diskUasge*100)/100+"%");
//    		}
//    	});
//    	
//    };
	 // 初始化window
    this.initPopWindow = function(){
    	// 虚拟机详情
    	$("#vmDetailWindow").jqxWindow({
            width: 790, 
            height:475,
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
function popVmDetailInfo(row){
	var data = $('#bizVmdatagrid').jqxGrid('getrowdata', row);
    resVmSid=data.resBizVmSid;
    var vmDetail = new vmDetailModel();
    vmDetail.setVmBasicInfo(data);
    vmDetail.searchVmStorageInfo(data.resBizVmSid);
    vmDetail.searchVmNicInfo(data.resBizVmSid);
//    vmDetail.searchVmInfo(data.resVmSid);
    
    var windowW = $(window).width(); 
  	var windowH = $(window).height();
  	$("#vmDetailWindow").jqxWindow({ position: { x: (windowW-790)/2, y: (windowH-475)/2 } });
  	$("#vmDetailWindow").jqxWindow('open');
	/**
	 * 初始化操作虚拟机按钮
	 * 
	 * @param action
	 */
	function initVmButton(stauts){
		if('normal'==status){
			
		}
	}
}