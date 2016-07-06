var pniRelationHostModel = function(){
	
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
//    		networkNameLike: "", 
//    		networkType: "01", 
//    		ipOwnerNetwork:"",
//    		parentTopologySid:resTopologySid
    	};
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	$("#search-pni-relation-hostName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
    	$("#search-pni-relation-hostIp").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
    	
    	$("#search-pni-relation-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    	
    };


    // 初始化用户datagrid的数据源
    this.initPoolPniRelationHostDatagrid = function(){
          $("#pniRelationedHostDatagrid").jqxGrid({
              width: "100%",
			  theme:currentTheme,
              columnsresize: true,
              pageable: true, 
              pagesize: 10, 
              autoheight: true,
              autowidth: false,
              sortable: true,
              selectionmode:"checkbox",
              localization: gridLocalizationObj,
              columns: [
                      { text: '主机名称', datafield: 'hostName'},
  	                  { text: '主机IP', datafield: 'hostIp',width:100},
  	                  { text: 'CPU个数(个)', datafield: 'cpuNumber',cellsalign:'right',width:80},
  	                  { text: 'CPU核数(核)', datafield: 'cpuCores',cellsalign:'right',width:80},
  	                  { text: '内存(GB)', datafield: 'memorySizeGb',cellsalign:'right',width:60},
  	                  { text: '所属拓扑结构', datafield: 'parentTopologyName',width:140},
  	                  { text: '操作系统', datafield: 'hostOsTypeName',width:140},
  	                  { text: '主机状态', datafield: 'statusName',align: 'center',cellsalign:'center',width:60}
                  
              ],
              showtoolbar: true,
              // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var addRelationHost = $("<div><a id='addRelationHost' onclick='popAddNetworkRelationHostWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增关联主机&nbsp;&nbsp;</a></div>");
                  var deleteRelationHost = $("<div><a id='deleteRelationHost' onclick='removeRelationHost()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除关联主机&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
                  container.append(addRelationHost);
                  container.append(deleteRelationHost);
                  
              }
          });
          
          // 控制按钮是否可用
    	  $("#pniRelationedHostDatagrid").on('rowselect', function (event) {
    		  var args = event.args; 
    		  var index = args.rowindex;
    		  var data = $('#pniRelationedHostDatagrid').jqxGrid('getrowdata', index);
   			  $("#deleteRelationHost").jqxButton({disabled: false});
          });
    	  $("#addRelationHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',});
		  $("#deleteRelationHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
		  
		  // 新增关联主机Window
		  $("#addPniRelationedHostDatagrid").jqxGrid({
              width: "100%",
			  theme:currentTheme,
              columnsresize: true,
              pageable: true, 
              pagesize: 10, 
              autoheight: true,
              autowidth: false,
              sortable: true,
              selectionmode:"checkbox",
              localization: gridLocalizationObj,
              columns: [
                      { text: '主机名称', datafield: 'hostName'},
                      { text: '所属拓扑结构', datafield: 'parentTopologyName',width:140},
  	                  { text: '主机IP', datafield: 'hostIp',width:100},
  	                  { text: 'CPU个数(个)', datafield: 'cpuNumber',cellsalign:'right',width:80},
  	                  { text: 'CPU核数(核)', datafield: 'cpuCores',cellsalign:'right',width:80},
  	                  { text: '内存(GB)', datafield: 'memorySizeGb',cellsalign:'right',width:60},
  	                  { text: '主机状态', datafield: 'statusName',align: 'center',cellsalign:'center',width:60}
                  
              ],
              showtoolbar: false,
            
          });
		 
    };
    
    // 初始化弹出window
    this.initPopWindow = function(){
    	$("#pniRelationHostWindow").jqxWindow({
		      width: 800, 
		      height:450,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#relationHostWindowClose"), 
		      modalOpacity: 0.3,
		      initContent: function () {
		    	  $("#relationHostWindowClose").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
		      }
		  });
    	
    	$("#addPniRelationHostWindow").jqxWindow({
		      width: 800, 
		      height:420,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#addRelationHostWindowClose"), 
		      modalOpacity: 0.3,
		      initContent: function () {
		    	  $("#search-pni-unrelation-hostName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
		      	  $("#search-pni-unrelation-hostIp").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
		      	
		      	  $("#search-pni-unrelation-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
		    	  
		    	  $("#addRelationHostWindowClose").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
		    	  $("#addRelationHostWindowSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
		      }
		  });
    	
    };
    
    
};

//条件查询IP配置管理
function searchPniNetworkRelationHost(){
	
	var rowindex = $('#poolPniNetworkdatagrid').jqxGrid('getselectedrowindex');
	var netdata = $('#poolPniNetworkdatagrid').jqxGrid('getrowdata', rowindex);
	
    var objSearch = {
    		hostNameLike : $("#search-pni-relation-hostName").val(),
    		hostIpLike : $("#search-pni-relation-hostIp").val(),
    		parentVeTopologySid:$("#search-pni-relation-ve").val()=="全部"?"":$("#search-pni-relation-ve").val(),
    		resNetworkSid: netdata.resNetworkSid
    };
	
	Core.AjaxRequest({
			url : ws_url + "/rest/host/find/network/relationed",
			type:'post',
			params:objSearch,
			async:false,
			callback : function (data) {
				var sourcedatagrid ={
	              datatype: "json",
	              localdata: data
			    };
			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
			    $("#pniRelationedHostDatagrid").jqxGrid({source: dataAdapter});
			}
	});

};

// 弹出可以添加的关联主机
function popAddNetworkRelationHostWindow(){
	// 初始化下拉框
	var codeadd = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth:150});
	codeadd.getCustomCode("search-pni-unrelation-ve","/host/find/ve/inNetworkPool/"+resTopologySid,"resTopologyName","resTopologySid",true,"GET",null);
	
	var rowArr = $('#poolPniNetworkdatagrid').jqxGrid('getselectedrowindex');
	var data = $('#poolPniNetworkdatagrid').jqxGrid('getrowdata', rowArr);
	
	var objSearch = {
    		resTopologySid : resTopologySid,
    		resNetworkSid: data.resNetworkSid
    };
	
	Core.AjaxRequest({
		url : ws_url + "/rest/host/find/network/unrelation",
		type:'POST',
		async:false,
		params:objSearch,
		callback : function (data) {
			var sourcedatagrid ={
              datatype: "json",
              localdata: data
		    };
		    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		    $("#addPniRelationedHostDatagrid").jqxGrid({source: dataAdapter});
		}
	 });

	
	var windowW = $(window).width(); 
    var windowH = $(window).height(); 
      
    $("#addPniRelationHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-420)/2 } });
    $("#addPniRelationHostWindow").jqxWindow('open');
}

// 保存新增的关联主机
function submitRelationHostInfo(){
	
	// 选中的网络
	var rowindex = $('#poolPniNetworkdatagrid').jqxGrid('getselectedrowindex');
	var netdata = $('#poolPniNetworkdatagrid').jqxGrid('getrowdata', rowindex);
	
	var rowArr = $('#addPniRelationedHostDatagrid').jqxGrid('getselectedrowindexes');
	var hostSids ="";
	if(rowArr.length > 0){
		// 判断是否可以移除
		for(var i = 0; i < rowArr.length; i++){
			// 查询出该行数据
			var data = $('#addPniRelationedHostDatagrid').jqxGrid('getrowdata', rowArr[i]);
			if(i == rowArr.length - 1){
				hostSids += data.resHostSid;
			}else{
				hostSids+= data.resHostSid+",";
			}
		}
		
		Core.AjaxRequest({
				url : ws_url + "/rest/host/network/relation/host",
				type:"POST",
				params:{
					resNetworkSid : netdata.resNetworkSid,
					resHostSids: hostSids
				},
				async:false,
				callback : function (data) {
					// 清除datagrid的选择项
				$('#addPniRelationedHostDatagrid').jqxGrid('clearselection');
				
				// 先给网络已经关联的主机列表赋值
				Core.AjaxRequest({
		 			url : ws_url + "/rest/host/find/network/relationed",
		 			type:'post',
		 			params:{
		 				resNetworkSid:netdata.resNetworkSid
		 			},
		 			async:false,
		 			callback : function (data) {
		 				var sourcedatagrid ={
				              datatype: "json",
				              localdata: data
		 			    };
		 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		 			    $("#pniRelationedHostDatagrid").jqxGrid({source: dataAdapter});
		 			}
		 		 });
				
				 // 关闭window
				 $("#addPniRelationHostWindow").jqxWindow('close');
			    },
			    failure:function(data){
			    	
			    }
			});
		
		
	}else{
		Core.alert({
			message:"请选择网络数据！",
			type:"info"
		});
	}
}

// 删除关联主机
function removeRelationHost(){
	// 选中的网络
	var rowindex = $('#poolPniNetworkdatagrid').jqxGrid('getselectedrowindex');
	var netdata = $('#poolPniNetworkdatagrid').jqxGrid('getrowdata', rowindex);
	
	var rowArr = $('#pniRelationedHostDatagrid').jqxGrid('getselectedrowindexes');
	var hostSids ="";
	if(rowArr.length > 0){
		// 判断是否可以移除
		for(var i = 0; i < rowArr.length; i++){
			// 查询出该行数据
			var data = $('#pniRelationedHostDatagrid').jqxGrid('getrowdata', rowArr[i]);
			if(i == rowArr.length - 1){
				hostSids += data.resHostSid;
			}else{
				hostSids+= data.resHostSid+",";
			}
		}
		
		Core.confirm({
			title:"提示",
			message:"确定要解绑所选主机吗?",
			yes:"确定",
			confirmCallback:function(){
				Core.AjaxRequest({
					url : ws_url + "/rest/host/network/unrelation/host",
					type:"POST",
					params:{
						resNetworkSid : netdata.resNetworkSid,
						resHostSids: hostSids
					},
					async:false,
					callback : function (data) {
						// 清除datagrid的选择项
					$('#pniRelationedHostDatagrid').jqxGrid('clearselection');
					
					// 先给网络已经关联的主机列表赋值
					Core.AjaxRequest({
			 			url : ws_url + "/rest/host/find/network/relationed",
			 			type:'post',
			 			params:{
			 				resNetworkSid:netdata.resNetworkSid
			 			},
			 			async:false,
			 			callback : function (data) {
			 				var sourcedatagrid ={
					              datatype: "json",
					              localdata: data
			 			    };
			 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
			 			    $("#pniRelationedHostDatagrid").jqxGrid({source: dataAdapter});
			 			}
			 		 });
					
				    }
				});
			}
	    });
	}	
}


//查询可添加的主机
function searchPniNetworkUnRelationHost(){
	var rowindex = $('#poolPniNetworkdatagrid').jqxGrid('getselectedrowindex');
	var data = $('#poolPniNetworkdatagrid').jqxGrid('getrowdata', rowindex);
	
    var objSearch = {
    		hostNameLike : $("#search-pni-unrelation-hostName").val(),
    		hostIpLike : $("#search-pni-unrelation-hostIp").val(),
    		parentVeTopologySid:$("#search-pni-unrelation-ve").val()=="全部"?"":$("#search-pni-unrelation-ve").val(),
    		resTopologySid : resTopologySid,
    		resNetworkSid: data.resNetworkSid
    };
	
    Core.AjaxRequest({
		url : ws_url + "/rest/host/find/network/unrelation",
		type:'POST',
		async:false,
		params:objSearch,
		callback : function (data) {
			var sourcedatagrid ={
              datatype: "json",
              localdata: data
		    };
		    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		    $("#addPniRelationedHostDatagrid").jqxGrid({source: dataAdapter});
		}
	 });
}
