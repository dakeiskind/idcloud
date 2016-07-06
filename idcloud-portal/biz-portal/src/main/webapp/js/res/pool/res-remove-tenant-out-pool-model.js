var removeTenantOutResPoolModel = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		hostName: "", 
    		manageStatus:"",
    		usageStatus:"",
    		isResPoolSearch:resTopologyType,
    		resTopologySid:resTopologySid
	};
    
    // 给datagrid赋值
    this.searchFindRemoveTenantPoolData = function(){
    	 Core.AjaxRequest({
	    		//url : ws_url + "/rest/pools/host/findAddHost/"+resTopologySid+"/"+virtualPlatformType+"",
	     		url : ws_url + "/rest/tenants/respooltenant/" + resTopologySid,
	     		type:'get',
	 			params:me.searchObj,
	 			async:false,
	 			callback : function (data) {
	 				me.items(data);
	 				var sourcedatagrid ={
			              datatype: "json",
			              localdata: me.items
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#removeTenantOutPool").jqxGrid({source: dataAdapter});
	 			}
	 		 });
    };
   
    // 初始化存储datagrid的数据源
    this.initFindTenantDatagrid = function(){
    	$("#removeTenantOutPool").jqxGrid({
            width: "100%",
			theme:currentTheme,
            columnsresize: true,
            pageable: true, 
            pagesize: 5, 
            autoheight: true,
            autowidth: false,
            sortable: true,
            selectionmode:"checkbox",
            localization: gridLocalizationObj,
            columns: [
				{ text: '租户名称', datafield: 'tenantName'},
				{ text: '租户类型', datafield: 'tenantTypeName'},
				{ text: '联系人', datafield: 'contact'},
				{ text: '联系电话', datafield: 'contactPhone'}
            ]
        });
    };
    
    // 初始化弹出window
	this.initPopWindow = function(){   	
		 $("#addRemoveTenantOutRespoolWindow").jqxWindow({
		        width: 680, 
		        height:258,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#removeTenantOutPoolCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
			    	$("#removeTenantOutPoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#removeTenantOutPoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
};

// 弹出可以添加的存储window框
function popRemoveTenantOutPoolWindow(){
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 清空datagrid的选择项
//  	$("#removeHostOutPool").jqxGrid("clearselection");
  	$("#addRemoveTenantOutRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-258)/2 } });
  	$("#addRemoveTenantOutRespoolWindow").jqxWindow('open');
}

// 提交将选择存储加入存储池
function removeTenantOutPoolSubmit(){
	// 得到选中的值
	 var tenantSids ="";
	 var rowindex = $('#removeTenantOutPool').jqxGrid('getselectedrowindexes');
	 var resPoolSid = resTopologySid;
	 if(rowindex.length >= 0){
	   		for(var i=0;i<rowindex.length;i++){
	   			var data = $('#removeTenantOutPool').jqxGrid('getrowdata', rowindex[i]);
	   			if(i == rowindex.length-1){
	   				tenantSids+= data.tenantSid;
				}else{
					tenantSids+= data.tenantSid+",";
				}
	   		}
	   		
	   		// 提交添加存储
	   		Core.AjaxRequest({
	    		    url : ws_url + "/rest/pools/host/removeTenantOutPool/"+resPoolSid+"/"+tenantSids+"",
					type:"get",
					callback : function (data) {
						// 关闭window
						$("#addRemoveTenantOutRespoolWindow").jqxWindow('close');
						// 刷新
						setHostTreeValue();
				    },
				    failure:function(data){
				    	
				    }
				});
	   	}
}
