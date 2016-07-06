var removeHostOutResPoolModel = function(){
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
    this.searchFindRemoveStoragePoolData = function(){
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
	 			    $("#removeHostOutPool").jqxGrid({source: dataAdapter});
	 			}
	 		 });
    };
   
    // 初始化存储datagrid的数据源
    this.initFindHostDatagrid = function(){
    	$("#removeHostOutPool").jqxGrid({
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
                { text: '主机名称', datafield: 'hostName'},
                { text: 'CPU(核)', datafield: 'cpuCores'},
                { text: '操作系统', datafield: 'hostOsTypeName'},
                { text: '虚拟类型', datafield: 'virtualPlatformType'},
                { text: '管理状态', datafield: 'manageStatusName'},
                { text: '所属资源池', datafield: 'resourcePoolName'},
                { text: '使用状态', datafield: 'usageStatusName'}
            ]
        });
    };
    
    // 初始化弹出window
	this.initPopWindow = function(){   	
		 $("#addRemoveOutRespoolWindow").jqxWindow({
		        width: 680, 
		        height:258,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#removeHostOutPoolCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
			    	$("#removeHostOutPoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#removeHostOutPoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
};

// 弹出可以添加的存储window框
function popRemoveHostOutPoolWindow(){
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 清空datagrid的选择项
  	$("#removeHostOutPool").jqxGrid("clearselection");
  	$("#addRemoveOutRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-258)/2 } });
  	$("#addRemoveOutRespoolWindow").jqxWindow('open');
}

// 提交将选择存储加入存储池
function removeHostOutPoolSubmit(){
	// 得到选中的值
	 var resSids ="";
	 var rowindex = $('#removeHostOutPool').jqxGrid('getselectedrowindexes');
	 
	 if(rowindex.length >= 0){
	   		for(var i=0;i<rowindex.length;i++){
	   			var data = $('#removeHostOutPool').jqxGrid('getrowdata', rowindex[i]);
	   			if(i == rowindex.length-1){
	   				resSids+= data.resSid;
				}else{
					resSids+= data.resSid+",";
				}
	   		}
	   		
	   		// 提交添加存储
	   		Core.AjaxRequest({
					url : ws_url + "/rest/pools/host/removeOutPool/"+resSids+"",
					type:"get",
					callback : function (data) {
						// 关闭window
						$("#addRemoveOutRespoolWindow").jqxWindow('close');
						// 刷新
						setHostTreeValue();
				    },
				    failure:function(data){
				    	
				    }
				});
	   	}
}
