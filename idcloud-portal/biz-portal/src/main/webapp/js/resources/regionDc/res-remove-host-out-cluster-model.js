var removeHostOutClusterModel = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		hostName: "", 
    		//manageStatus:"",
    		status:"",
    		//isResPoolSearch:resTopologyType,
    		resTopologySid:resTopologySid
	};
    // 给datagrid赋值
    this.searchfindHostRemoveOutClusterData = function(){
    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/hosts",
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
	 			    $("#findHostRemoveOutCluster").jqxGrid({source: dataAdapter});
	 			}
	 		 });
    };
   
    // 初始化用户datagrid的数据源
    this.initfindHostRemoveOutClusterDatagrid = function(){
          $("#findHostRemoveOutCluster").jqxGrid({
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
                  { text: '主机名称', datafield: 'hostName',width:200},
                  { text: '主机型号', datafield: 'hostType',width:80},
                  { text: 'CPU(核)', datafield: 'cpuCores',width:60},
                  { text: '内存(MB)', datafield: 'memorySize',width:80},
                  { text: 'IP地址', datafield: 'hostIp'},
                  { text: '使用状态', datafield: 'statusName'}
              ]
          });
    };
    
    // 初始化弹出window
	this.initPopWindow = function(){   	
		  $("#removeHostOutClusterWindow").jqxWindow({
		        width: 680, 
		        height:258,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#removeOutClusterCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
			    	$("#removeOutClusterSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#removeOutClusterCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
};

// 弹出添加主机到池的window框
function popRemoveHostOutClusterWindow(){
	var windowW = $(window).width();
  	var windowH = $(window).height();
  	// 清空datagrid的选择项
  //	$("#findHostRemoveOutCluster").jqxGrid("clearselection");
  	$("#removeHostOutClusterWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-258)/2 } });
  	$("#removeHostOutClusterWindow").jqxWindow('open');
}

// 提交将选择主机加入池
function removeHostOutClusterSubmit(){
	// 得到选中的值
	 var resHostSid ="";
	 var rows = $('#findHostRemoveOutCluster').jqxGrid('getselectedrowindexes');
	 if(rows.length >= 0){
	   		for(var i=0;i<rows.length;i++){
	   			var data = $('#findHostRemoveOutCluster').jqxGrid('getrowdata', rows[i]);
	   			if(i == rows.length-1){
	   				resHostSid+= data.resHostSid;
				}else{
					resHostSid+= data.resHostSid+",";
				}
	   		}
	   		
	   		// 提交
	   		Core.AjaxRequest({
					url : ws_url + "/rest/topologys/removeOutCluster/"+resTopologySid+"/"+resHostSid+"",
					type:"get",
					callback : function (data) {
						var host = new removeHostOutClusterModel();
						host.searchfindHostRemoveOutClusterData();
						var clustrt = new addHostToClusterModel();
						clustrt.searchfindHostAddToClusterData();
						// 关闭window
						$("#removeHostOutClusterWindow").jqxWindow('close');
						// 刷新
						//setHostTreeValue();
						setVirtualTreeValue();
 						setStorageVirtualTreeValue();
						
				    },
				    failure:function(data){
				    	
				    }
				});
	   	}
}