var addHostToResPoolModel = function(){
	var me = this;
    this.items = ko.observableArray();
    
    // 给datagrid赋值
    this.searchfindHostAddToPoolData = function(virtualPlatformType){
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/pools/host/findAddHost/"+resTopologySid+"/"+virtualPlatformType+"",
 			type:'get',
 			async:false,
 			callback : function (data) {
 				me.items(data);
 				var sourcedatagrid ={
		              datatype: "json",
		              localdata: me.items
 			    };
 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#findHostAddToPool").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
   
    // 初始化用户datagrid的数据源
    this.initfindHostAddToPoolDatagrid = function(){
          $("#findHostAddToPool").jqxGrid({
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
                  { text: '虚拟类型', datafield: 'virtualPlatformType'},
                  { text: '使用状态', datafield: 'usageStatusName'}
              ]
          });
    };
    
    // 初始化弹出window
	this.initPopWindow = function(){   	
		  $("#addHostToRespoolWindow").jqxWindow({
		        width: 680, 
		        height:258,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addToPoolCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
			    	$("#addToPoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addToPoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
};

// 弹出添加主机到池的window框
function popAddHostToPoolWindow(){
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 清除datagrid的选择情况
//  	$("#findHostAddToPool").jqxGrid("clearselection");
  	
  	$("#addHostToRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-258)/2 } });
  	$("#addHostToRespoolWindow").jqxWindow('open');
}

// 提交将选择主机加入池
function addHostToPoolSubmit(){
	// 得到选中的值
	 var resSids ="";
	 var rowindex = $('#findHostAddToPool').jqxGrid('getselectedrowindexes');
	 
	 if(rowindex.length >= 0){
	   		for(var i=0;i<rowindex.length;i++){
	   			var data = $('#findHostAddToPool').jqxGrid('getrowdata', rowindex[i]);
	   			if(i == rowindex.length-1){
	   				resSids+= data.resSid;
				}else{
					resSids+= data.resSid+",";
				}
	   		}
	   		
	   		// 提交添加存储
	   		Core.AjaxRequest({
					url : ws_url + "/rest/pools/host/addToPool/"+resTopologySid+"/"+resSids+"",
					type:"get",
					callback : function (data) {
						// 关闭window
						$("#addHostToRespoolWindow").jqxWindow('close');
						// 刷新
						setHostTreeValue();
						
				    },
				    failure:function(data){
				    	
				    }
				});
	   	}
}
