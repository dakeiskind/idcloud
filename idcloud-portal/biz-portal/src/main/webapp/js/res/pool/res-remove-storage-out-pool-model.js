var removeStorageOutResPoolModel = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		volumeName: "", 
    		volumeType: "", 
    		manageStatus:"",
    		usageStatus:"",
    		isResPoolSearch:resTopologyType,
    		resTopologySid:resTopologySid
	};
    
    // 给datagrid赋值
    this.searchFindRemoveStoragePoolData = function(){
    	Core.AjaxRequest({
 			url : ws_url + "/rest/storages",
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
 			    $("#removeStorageOutRespool").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
   
    // 初始化存储datagrid的数据源
    this.initFindStorageAddToPoolDatagrid = function(){
    	$("#removeStorageOutRespool").jqxGrid({
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
                { text: '存储名称', datafield: 'volumeName'},
                { text: '存储类型', datafield: 'volumeTypeName'},
                { text: '硬盘类型', datafield: 'hardDiskTypeName'},
                { text: '存储类别', datafield: 'storageCategoryName'},
                { text: '使用率(%)', datafield: 'usedCapacityPercentage'},
                { text: '管理状态', datafield: 'manageStatusName'},
                { text: '使用状态', datafield: 'usageStatusName'}
            ]
        });
    };
    
    // 初始化弹出window
	this.initPopWindow = function(){   	
		  $("#removeStorageOutRespoolWindow").jqxWindow({
		        width: 700, 
		        height:258,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#removeStorageOutPoolCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
			    	$("#removeStorageOutPoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#removeStorageOutPoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
};

// 弹出可以添加的存储window框
function popremoveStorageOutPoolWindow(){
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 清除datagrid的选择情况
  	$("#removeStorageOutRespool").jqxGrid("clearselection");
  	
  	$("#removeStorageOutRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-258)/2 } });
  	$("#removeStorageOutRespoolWindow").jqxWindow('open');
}

// 提交将选择存储加入存储池
function removeStorageOutPoolSubmit(){
	// 得到选中的值
	 var resSids ="";
	 var rowindex = $('#removeStorageOutRespool').jqxGrid('getselectedrowindexes');
	 
	 if(rowindex.length >= 0){
	   		for(var i=0;i<rowindex.length;i++){
	   			var data = $('#removeStorageOutRespool').jqxGrid('getrowdata', rowindex[i]);
	   			if(i == rowindex.length-1){
	   				resSids+= data.resSid;
				}else{
					resSids+= data.resSid+",";
				}
	   		}
	   		
	   		// 提交添加存储
	   		Core.AjaxRequest({
					url : ws_url + "/rest/pools/storage/removeOutStorage/"+resSids+"",
					type:"get",
					callback : function (data) {
						// 关闭window
						$("#removeStorageOutRespoolWindow").jqxWindow('close');
						// 刷新
						setHostTreeValue();
				    },
				    failure:function(data){
				    	
				    }
				});
	   	}
}
