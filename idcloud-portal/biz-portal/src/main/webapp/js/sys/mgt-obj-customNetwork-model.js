var mgtObjCustomNetworkModel = function () {
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		
    	};
    // 用户数据
    this.searchInfo = function(mgtObjSid){
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/networks/find/customNetwork",
 			type:'post',
 			async:false,
 			params:{
 				mgtObjSid : mgtObjSid,
 				networkType : "03"
 			},
 			callback : function (data) {
 				me.items(data);
 				var sourcedatagrid ={
		              datatype: "json",
		              localdata: me.items
 			    };
 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#customNetworkDatagrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    	
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	$("#search-custom-network-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	
        $("#search-pc-relation-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
        $("#customNetworktCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
  
    // 初始化用户datagrid的数据源
    this.initDatagrid = function(){
    	$("#customNetworkDatagrid").jqxGrid({
            width: "100%",
			theme:currentTheme,
            columnsresize: true,
            pageable: true, 
            pagesize: 10, 
            autoheight: true,
            autowidth: false,
            sortable: true,
            selectionmode:"single",
            localization: gridLocalizationObj,
            columns: [
                { text: '网络名称', datafield: 'networkName'},
                { text: '网关', datafield: 'gateway'},
                { text: '状态', datafield: 'statusName'},
                { text: 'IP总数(个)', datafield: 'ipTotalCount',cellsalign:'right', width:80},
                { text: '操作', datafield: 'Edit', columntype: 'button',width:60, cellsrenderer: function () {
                    return "编辑";
                 }, buttonclick: function (row) {
	                  editrow = row;
	                  var data = $('#customNetworkDatagrid').jqxGrid('getrowdata', editrow);
	                  var network = new editCustomNetworkModel();
	                  // 为编辑赋值
	                  $("#edit-custom-resNetworkSid").val(data.resNetworkSid);
          		  
	          		  // 编辑框赋值
	          		  network.setEditNetworkForm(data);
	              	  // 设置弹出框位置
	              	  var windowW = $(window).width(); 
	                  var windowH = $(window).height(); 
	                  $("#editCustomNetworkWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-390)/2 } });
	                  $("#editCustomNetworkWindow").jqxWindow('open');                  }
                },
                { text: '', datafield: 'viewIpGrid', columntype: 'button',width:100, cellsrenderer: function () {
              	  return "管理IP列表";
	                   }, buttonclick: function (row) {
	                      
	                      editrow = row;
	              		  var data = $('#customNetworkDatagrid').jqxGrid('getrowdata', editrow);
	            		  
	              		  // 初始化
	              		  var networkIp = new customNetworkViewIpGrid();
	              		  resourcePoolSid = data.resNetworkSid;
	                	  networkIp.searchIpConfigInfo(data.resNetworkSid);
		              	  // 设置弹出框位置
		              	  var windowW = $(window).width(); 
		                  var windowH = $(window).height(); 
		                  $("#viewCustomNetworkOwnIpGrid").jqxWindow({ position: { x: (windowW-750)/2, y: (windowH-465)/2 } });
		                  $("#viewCustomNetworkOwnIpGrid").jqxWindow('open');                 
		               }
                }
            ],
            showtoolbar: true,
            // 设置toolbar操作按钮
            rendertoolbar: function (toolbar) {
                var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                var refreshBtn = $("<div><a id='refreshCustomNetworkBtn' onclick='refreshCustomNetworkGrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                var addNetwork = $("<div><a id='addCustomNetwork' onclick='addCustomNetworkInfoWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
                var removeNetwork = $("<div><a id='removeCustomNetwork' onclick='removeCustomNetworkInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                toolbar.append(container);
                container.append(addNetwork);
                container.append(removeNetwork);
                container.append(refreshBtn);
            }
        });
    	// 控制按钮是否可用
  	  	$("#customNetworkDatagrid").on('rowselect', function (event) {
	  		  var args = event.args; 
	  		  var index = args.rowindex;
	  		  var data = $('#customNetworkDatagrid').jqxGrid('getrowdata', index);
 			  $("#removeCustomNetwork").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
        });
    	me.initButton();
    };
    
    // 判断操作按钮，初始化按钮
    this.initButton = function(){
    	 // 判断datagrid是否有被选中的
    	var selectedrowindex = $('#customNetworkDatagrid').jqxGrid('getselectedrowindexes'); 
    	// 大于-1表示有被选中的
    	if(selectedrowindex > 0){
    		$("#addCustomNetwork").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
   			$("#removeCustomNetwork").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
   			$("#refreshCustomNetworkBtn").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    	}else{
    		$("#addCustomNetwork").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
  			$("#removeCustomNetwork").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
  			$("#refreshCustomNetworkBtn").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    	}
    	
    };
    
    
    // 初始化弹出window
    this.initPopWindow = function(){
    	$("#customNetworkWindow").jqxWindow({
		      width: 1000, 
		      height:450,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#customNetworktCancel"), 
		      modalOpacity: 0.3
		  });
    };
    
};

// 删除自定义网络
function removeCustomNetworkInfo(){
	// 得到datagrid的选择项
	var rowArr = $('#customNetworkDatagrid').jqxGrid('getselectedrowindex');
	var isUsed = true;
	var networkSids ="";
	if(rowArr >= 0){
		// 判断是否可以移除
//		for(var i = 0; i < rowArr.length; i++){
//			// 查询出该行数据
//			var data = $('#customNetworkDatagrid').jqxGrid('getrowdata', rowArr[i]);
//			if(i == rowArr.length - 1){
//				networkSids += data.resNetworkSid;
//			}else{
//				networkSids+= data.resNetworkSid+",";
//			}
//		}
		var customData = $('#customNetworkDatagrid').jqxGrid('getrowdata', rowArr);
		
		var rowindex = $('#jqxgridBiz').jqxGrid('getselectedrowindex');
		var netWorkdata = $('#jqxgridBiz').jqxGrid('getrowdata', rowindex);
		
		// 移除所选择的网络
		Core.confirm({
			title:"提示",
			message:"确定要删除所选网络吗?",
			yes:"确定",
			confirmCallback:function(){
				Core.AjaxRequest({
	 				url : ws_url + "/rest/networks/delete/custom",
	 				type:"post",
	 				async:false,
	 				params:{
	 					resNetworkSid :customData.resNetworkSid,
	 					mgtObjSid : netWorkdata.mgtObjSid
	 				},
	 				callback : function (data) {
	 					// 清除datagrid的选择项
						$('#customNetworkDatagrid').jqxGrid('clearselection');
						searchCustomNetworkMgt();
	 				    // 初始化按钮
	 					resNetwork.initButton();
	 			    },
	 			    failure:function(data){
	 			    	
	 			    }
	 			});
			}
	    });
	}else{
		Core.alert({
			message:"请选择网络数据！",
			type:"info"
		});
	}
}

//条件查询IP配置管理
function searchCustomNetworkMgt(){
	 var rowindex = $('#jqxgridBiz').jqxGrid('getselectedrowindex');
	 var data = $('#jqxgridBiz').jqxGrid('getrowdata', rowindex);
	 Core.AjaxRequest({
			url : ws_url + "/rest/networks/find/customNetwork",
			type:'post',
			async:false,
			params:{
				mgtObjSid : data.mgtObjSid,
				networkNameLike : $("#search-custom-network-name").val()
			},
			callback : function (data) {
				var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
			    };
			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
			    $("#customNetworkDatagrid").jqxGrid({source: dataAdapter});
			}
		 });
};
//刷新
function refreshCustomNetworkGrid(){
	
	var net = new mgtObjCustomNetworkModel();
	net.searchInfo(mgtObjSid);
}
