var poolPneDatagridModel = function(){
	
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		networkNameLike: "", 
    		networkType: "02", 
    		ipOwnerNetwork:"",
    		parentTopologySid:resTopologySid
    	};
    // 查询网络
    this.searchPoolPneInfo = function(){
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/networks",
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
 			    $("#poolPneNetworkdatagrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	var networkconfig = new codeModel({width:120,autoDropDownHeight:true});
    	$("#search-pne-networkName").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	$("#search-pne-ip").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	$("#search-pne-network-button").jqxButton({ width: '50',theme:currentTheme,height: '25px',  disabled: false});
    };

    // 初始化用户datagrid的数据源
    this.initPoolPneDatagrid = function(){
          $("#poolPneNetworkdatagrid").jqxGrid({
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
            	  { text: 'VLAN ID', datafield: 'vlanIdName',cellsalign:'left',width:80},
                  { text: '网络名称', datafield: 'networkName'},
                  { text: '网关', datafield: 'gateway'},
                  { text: 'IP使用数(个)', datafield: 'ipUseCount', cellsalign:'right',width:80},
                  { text: 'IP总数(个)', datafield: 'ipTotalCount',cellsalign:'right', width:80},
                  { text: '使用状态', datafield: 'usageStatusName', align: 'center',cellsalign:'center',width:80},
                  { text: '操作', datafield: 'Edit', columntype: 'button',width:60, cellsrenderer: function () {
                      return "编辑";
                   }, buttonclick: function (row) {
                      // open the popup window when the user clicks a button.
                      editrow = row;
              		  var data = $('#poolPneNetworkdatagrid').jqxGrid('getrowdata', editrow);
            		  var network = new editPneNetworkModel();
            		  // 为编辑赋值
            		  $("#edit-pne-resNetworkSid").val(data.resNetworkSid);
            		  // 编辑框赋值
            		  network.setEditNetworkForm(data);
	              	  // 设置弹出框位置
	              	  var windowW = $(window).width(); 
	                  var windowH = $(window).height(); 
	                  $("#editPneNetworkWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-390)/2 } });
	                  $("#editPneNetworkWindow").jqxWindow('open');                  }
                  },
                  { text: '', datafield: 'viewIpGrid', columntype: 'button',width:100, cellsrenderer: function () {
                	  return "管理IP列表";
	                   }, buttonclick: function (row) {
	                      
	                      editrow = row;
	              		  var data = $('#poolPneNetworkdatagrid').jqxGrid('getrowdata', editrow);
	              		 
	              		  // 初始化
	              		  var networkIp = new pneNetworkViewIpGrid();
	              		  resourcePoolSid = data.resNetworkSid;
	              		  
	                	  networkIp.searchIpConfigInfo(data.resNetworkSid);
		              	  // 设置弹出框位置
		              	  var windowW = $(window).width(); 
		                  var windowH = $(window).height(); 
		                  $("#viewPneNetworkOwnIpGrid").jqxWindow({ position: { x: (windowW-750)/2, y: (windowH-465)/2 } });
		                  $("#viewPneNetworkOwnIpGrid").jqxWindow('open');                 
		               }
                  }
              ],
              showtoolbar: true,
              // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var addNetwork = $("<div><a id='addNetwork' onclick='addPneNetworkInfoWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
                  var removeNetwork = $("<div><a id='removeNetwork' onclick='removePneNetworkInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
                  container.append(addNetwork);
                  container.append(removeNetwork);
              }
          });
          
       // 控制按钮是否可用
    	  $("#poolPneNetworkdatagrid").on('rowselect', function (event) {
    		  var args = event.args; 
    		  var index = args.rowindex;
    		  var data = $('#poolPneNetworkdatagrid').jqxGrid('getrowdata', index);
   			  $("#removeNetwork").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
          });
    	  // 初始化按钮
    	  me.initButton();
          
    };
    
   
    // 判断操作按钮，初始化按钮
    this.initButton = function(){
    	 // 判断datagrid是否有被选中的
    	var selectedrowindex = $('#poolPneNetworkdatagrid').jqxGrid('selectedrowindex'); 
    	// 大于-1表示有被选中的
    	if(selectedrowindex > -1){
    		$("#addNetwork").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
   			$("#removeNetwork").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    	}else{
    		$("#addNetwork").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
  			$("#removeNetwork").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
    	}
    	
    };
   
};

//条件查询IP配置管理
function searchPneNetwork(){
	var network = new poolPneDatagridModel();
	network.searchObj.networkNameLike = $("#search-pne-networkName").val();
	network.searchObj.ipOwnerNetwork = $("#search-pne-ip").val();
	network.searchObj.parentTopologySid = resTopologySid;
	network.searchPoolPneInfo();
};

//删除ip
function removePneNetworkInfo(){
// 得到datagrid的选择项
	var rowArr = $('#poolPneNetworkdatagrid').jqxGrid('getselectedrowindexes');
	var isUsed = true;
	var networkSids ="";
	if(rowArr.length > 0){
		// 判断是否可以移除
		for(var i = 0; i < rowArr.length; i++){
			// 查询出该行数据
			var data = $('#poolPneNetworkdatagrid').jqxGrid('getrowdata', rowArr[i]);
			if(i == rowArr.length - 1){
				networkSids += data.resNetworkSid;
			}else{
				networkSids+= data.resNetworkSid+",";
			}
			
//			if(data.usageStatus == "01"){ 
//				isUsed = false;
//				// 清除datagrid的选择项
//				$('#poolPneNetworkdatagrid').jqxGrid('clearselection');
//				break;
//			}
		}
		
		// 判断是否有已使用的网络
		if(isUsed){
			// 移除所选择的网络
			Core.confirm({
				title:"提示",
				message:"确定要删除所选网络吗?",
				yes:"确定",
				confirmCallback:function(){
					Core.AjaxRequest({
		 				url : ws_url + "/rest/networks/delete/"+networkSids+"",
		 				type:"get",
		 				callback : function (data) {

		 					// 清除datagrid的选择项
							$('#poolPneNetworkdatagrid').jqxGrid('clearselection');
							
		 					var resNetwork = new poolPneDatagridModel();
		 					resNetwork.searchPoolPneInfo();
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
				message:"该网络已使用，无法删除！",
				type:"info"
			});
		}
	}else{
		Core.alert({
			message:"请选择网络数据！",
			type:"info"
		});
	}
}


// 弹出管理IP列表
function popManagtmentIpGrid(){
	// 初始化
	  var networkIp = new pneNetworkViewIpGrid();
	 
	  networkIp.searchIpConfigInfo();
	  // 设置弹出框位置
	  var windowW = $(window).width(); 
      var windowH = $(window).height(); 
      $("#viewPneNetworkOwnIpGrid").jqxWindow({ position: { x: (windowW-750)/2, y: (windowH-465)/2 } });
      $("#viewPneNetworkOwnIpGrid").jqxWindow('open');
}

  