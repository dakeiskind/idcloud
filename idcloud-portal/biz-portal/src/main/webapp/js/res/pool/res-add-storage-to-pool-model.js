var addStorageToResPoolModel = function(){
	var me = this;
    this.items = ko.observableArray();
    
    // 给datagrid赋值
    this.searchFindStorageAddToPoolData = function(storageType){
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/pools/storage/findAddStorage/"+resTopologySid+"/"+storageType+"",
 			type:'get',
 			async:false,
 			callback : function (data) {
 				me.items(data);
 				var sourcedatagrid ={
		              datatype: "json",
		              localdata: me.items
 			    };
 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#findStorageAddToPool").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
   
    // 初始化存储datagrid的数据源
    this.initFindStorageAddToPoolDatagrid = function(){
          $("#findStorageAddToPool").jqxGrid({
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
                  { text: '存储名称', datafield: 'volumeName',width:200},
                  { text: '存储类型', datafield: 'volumeTypeName',width:80},
                  { text: '硬盘类型', datafield: 'hardDiskTypeName',width:60},
                  { text: '存储类别', datafield: 'storageCategoryName',width:80},
                  { text: '存储用途', datafield: 'storagePurposeName'},
                  { text: '逻辑单元号', datafield: 'volumeUnitNo'},
                  { text: '总容量(GB)', datafield: 'availableCapacity'}
              ]
          });
    };
    
    // 初始化弹出window
	this.initPopWindow = function(){   	
		  $("#addStorageToRespoolWindow").jqxWindow({
		        width: 680, 
		        height:258,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addStorageToPoolCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
			    	$("#addStorageToPoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addStorageToPoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
};

// 弹出可以添加的存储window框
function popAddStorageToPoolWindow(){
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 清除datagrid的选择情况
  	$("#findStorageAddToPool").jqxGrid("clearselection");
  	
  	$("#addStorageToRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-258)/2 } });
  	$("#addStorageToRespoolWindow").jqxWindow('open');
}

// 提交将选择存储加入存储池
function addStorageToPoolSubmit(){
	// 得到选中的值
	 var resSids ="";
	 var rowindex = $('#findStorageAddToPool').jqxGrid('getselectedrowindexes');
	 
	 if(rowindex.length >= 0){
	   		for(var i=0;i<rowindex.length;i++){
	   			var data = $('#findStorageAddToPool').jqxGrid('getrowdata', rowindex[i]);
	   			if(i == rowindex.length-1){
	   				resSids+= data.resSid;
				}else{
					resSids+= data.resSid+",";
				}
	   		}
	   		
	   		// 提交添加存储
	   		Core.AjaxRequest({
					url : ws_url + "/rest/pools/storage/addToStorage/"+resTopologySid+"/"+resSids+"",
					type:"get",
					callback : function (data) {
						// 关闭window
						$("#addStorageToRespoolWindow").jqxWindow('close');
						// 刷新
						setHostTreeValue();
				    },
				    failure:function(data){
				    	
				    }
				});
	   	}
}
