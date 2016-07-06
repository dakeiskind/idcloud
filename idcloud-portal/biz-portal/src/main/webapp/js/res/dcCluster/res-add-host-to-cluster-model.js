var addHostToClusterModel = function(){
	var me = this;
    this.items = ko.observableArray();
    
    // 给datagrid赋值
    this.searchfindHostAddToClusterData = function(){
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/topologys/findHostAddToCluster/"+resTopologySid+"",
 			type:'get',
 			async:false,
 			callback : function (data) {
 				me.items(data);
 				var sourcedatagrid ={
		              datatype: "json",
		              localdata: me.items
 			    };
 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#findHostAddToCluster").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
   
    // 初始化用户datagrid的数据源
    this.initfindHostAddToClusterDatagrid = function(){
          $("#findHostAddToCluster").jqxGrid({
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
		  $("#addHostToClusterWindow").jqxWindow({
		        width: 680, 
		        height:258,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addToClusterCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
			    	$("#addToClusterSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addToClusterCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
};

// 弹出添加主机到池的window框
function popAddHostToClusterWindow(){
	var windowW = $(window).width();
  	var windowH = $(window).height();
  	// 清空datagrid的选择项
//  	$("#findHostAddToCluster").jqxGrid("clearselection");
  	$("#addHostToClusterWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-258)/2 } });
  	$("#addHostToClusterWindow").jqxWindow('open');
}

// 提交将选择主机加入池
function addHostToClusterSubmit(){
	// 得到选中的值
	 var resSids ="";
	 var rowindex = $('#findHostAddToCluster').jqxGrid('getselectedrowindexes');
	 
	 if(rowindex.length >= 0){
	   		for(var i=0;i<rowindex.length;i++){
	   			var data = $('#findHostAddToCluster').jqxGrid('getrowdata', rowindex[i]);
	   			if(i == rowindex.length-1){
	   				resSids+= data.resSid;
				}else{
					resSids+= data.resSid+",";
				}
	   		}
	   		
	   		// 提交添加存储
	   		Core.AjaxRequest({
					url : ws_url + "/rest/topologys/addToCluster/"+resTopologySid+"/"+resSids+"",
					type:"get",
					callback : function (data) {
						// 关闭window
						$("#addHostToClusterWindow").jqxWindow('close');
						// 刷新
						setHostTreeValue();
						
				    },
				    failure:function(data){
				    	
				    }
				});
	   	}
}