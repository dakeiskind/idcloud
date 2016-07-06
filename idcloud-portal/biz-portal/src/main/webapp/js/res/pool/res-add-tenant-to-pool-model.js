var addTenantToResPoolModel = function(){
	var me = this;
    this.items = ko.observableArray();
    
    // 给datagrid赋值
    this.searchfindTenantAddToPoolData = function(){
    	 Core.AjaxRequest({
 			//url : ws_url + "/rest/pools/host/findAddHost/"+resTopologySid+"/"+virtualPlatformType+"",
    		url : ws_url + "/rest/tenants",
    		type:'post',
 			async:false,
 			callback : function (data) {
 				me.items(data);
 				var sourcedatagrid ={
		              datatype: "json",
		              localdata: me.items
 			    };
 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#findTenantAddToPool").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
   
    // 初始化用户datagrid的数据源
    this.initfindTenantAddToPoolDatagrid = function(){
          $("#findTenantAddToPool").jqxGrid({
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
		  $("#addTenantToRespoolWindow").jqxWindow({
		        width: 680, 
		        height:258,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addTenantToPoolCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
			    	$("#addTenantToPoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addTenantToPoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
};

// 弹出添加主机到池的window框
function popAddTenantToPoolWindow(){
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 清除datagrid的选择情况
  	//$("#findTenantAddToPool").jqxGrid("clearselection");
  	
  	$("#addTenantToRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-258)/2 } });
  	$("#addTenantToRespoolWindow").jqxWindow('open');
}

// 提交将选择主机加入池
function addTenantToPoolSubmit(){
	// 得到选中的值
	 var tenantSids ="";
	 var rowindex = $('#findTenantAddToPool').jqxGrid('getselectedrowindexes');
	 
	 if(rowindex.length >= 0){
	   		for(var i=0;i<rowindex.length;i++){
	   			var data = $('#findTenantAddToPool').jqxGrid('getrowdata', rowindex[i]);
	   			if(i == rowindex.length-1){
	   				tenantSids+= data.tenantSid;
				}else{
					tenantSids+= data.tenantSid+",";
				}
	   		}
	   		
	   		// 提交添加存储
	   		Core.AjaxRequest({
					url : ws_url + "/rest/pools/host/addTenantToPool/"+resTopologySid+"/"+tenantSids+"",
					type:"get",
					callback : function (data) {
						// 关闭window
						$("#addTenantToRespoolWindow").jqxWindow('close');
						// 刷新
						setHostTreeValue();
						
				    },
				    failure:function(data){
				    	
				    }
				});
	   	}
}
