var poolPniDatagridModel = function(){
	
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		networkNameLike: "", 
    		networkType: "01", 
    		ipOwnerNetwork:"",
    		parentTopologySid:resTopologySid
    	};
    // 查询网络
    this.searchPoolPniInfo = function(){
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
 			    $("#poolPniNetworkdatagrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
    // 统计网络资源池下的ip信息
    this.statisticsIpInNetworkPool = function(networkType,resTopologySid){
    	var ipData = null;
    	Core.AjaxRequest({
 			url : ws_url + "/rest/ip/statistics/network/"+networkType+"/"+resTopologySid,
 			type:'get',
 			async:false,
 			callback : function (data) {
 				ipData = data;
 			}
 		});
    	
    	var all = new Array();
    	var arr = new Array();
    	all.push(ipData.totalCount);
 		var name = ["未使用","已使用"];
 		var value = [ipData.unusedCount,ipData.usedCount];
	 	for(var j=0;j<2;j++){
 			var obj = new Object();
 			obj.name = name[j];
 			obj.value = (value[j] == null)? 0:value[j];
 			arr.push(obj);
	    }
	 	all.push(arr);
		return all;
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	var networkconfig = new codeModel({width:120,autoDropDownHeight:true});
    	$("#search-pni-networkName").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	$("#search-pni-ip").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-pni-network-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    	
    };

    // 初始化用户datagrid的数据源
    this.initPoolPniDatagrid = function(){
          $("#poolPniNetworkdatagrid").jqxGrid({
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
              		  var data = $('#poolPniNetworkdatagrid').jqxGrid('getrowdata', editrow);
            		  var network = new editPniNetworkModel();
            		  // 为编辑赋值
            		  $("#edit-resNetworkSid").val(data.resNetworkSid);
            		  
            		  // 编辑框赋值
            		  network.setEditNetworkForm(data);
	              	  // 设置弹出框位置
	              	  var windowW = $(window).width(); 
	                  var windowH = $(window).height(); 
	                  $("#editNetworkWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-390)/2 } });
	                  $("#editNetworkWindow").jqxWindow('open');                  }
                  },
                  { text: '', datafield: 'viewIpGrid', columntype: 'button',width:100, cellsrenderer: function () {
                	  return "管理IP列表";
	                   }, buttonclick: function (row) {
	                      
	                      editrow = row;
	              		  var data = $('#poolPniNetworkdatagrid').jqxGrid('getrowdata', editrow);
	            		  
	              		  // 初始化
	              		  var networkIp = new pniNetworkViewIpGrid();
	              		  resourcePoolSid = data.resNetworkSid;
	                	  networkIp.searchIpConfigInfo(data.resNetworkSid);
		              	  // 设置弹出框位置
		              	  var windowW = $(window).width(); 
		                  var windowH = $(window).height(); 
		                  $("#viewNetworkOwnIpGrid").jqxWindow({ position: { x: (windowW-750)/2, y: (windowH-465)/2 } });
		                  $("#viewNetworkOwnIpGrid").jqxWindow('open');                 
		               }
                  }
              ],
              showtoolbar: true,
              // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var addNetwork = $("<div><a id='addNetwork' onclick='addNetworkInfoWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
                  var relationHost = $("<div><a id='relationHost' onclick='popRelationHostWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-link'></i>关联主机&nbsp;&nbsp;</a></div>");
                  var removeNetwork = $("<div><a id='removeNetwork' onclick='removePniNetworkInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
                  container.append(addNetwork);
                  container.append(relationHost);
                  container.append(removeNetwork);
              }
          });
          
       // 控制按钮是否可用
    	  $("#poolPniNetworkdatagrid").on('rowselect', function (event) {
    		  var args = event.args; 
    		  var index = args.rowindex;
    		  var data = $('#poolPniNetworkdatagrid').jqxGrid('getrowdata', index);
    		 
    		  $("#relationHost").jqxButton({disabled: false});
   			  $("#removeNetwork").jqxButton({disabled: false});
          });
    	  $("#addNetwork").jqxButton({ width: '80',theme:currentTheme,height: '18px',});
		  $("#relationHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
		  $("#removeNetwork").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
          
    };
    
};

//条件查询IP配置管理
function searchPniNetwork(){
	var network = new poolPniDatagridModel();
	network.searchObj.networkNameLike = $("#search-pni-networkName").val();
	network.searchObj.ipOwnerNetwork = $("#search-pni-ip").val();
	network.searchObj.parentTopologySid = resTopologySid;
	network.searchPoolPniInfo();
};

//删除ip
function removePniNetworkInfo(){
// 得到datagrid的选择项
	var rowArr = $('#poolPniNetworkdatagrid').jqxGrid('getselectedrowindexes');
	var isUsed = true;
	var networkSids ="";
	if(rowArr.length > 0){
		// 判断是否可以移除
		for(var i = 0; i < rowArr.length; i++){
			// 查询出该行数据
			var data = $('#poolPniNetworkdatagrid').jqxGrid('getrowdata', rowArr[i]);
			if(i == rowArr.length - 1){
				networkSids += data.resNetworkSid;
			}else{
				networkSids+= data.resNetworkSid+",";
			}
			
//			if(data.usageStatus == "01"){ 
//				isUsed = false;
//				// 清除datagrid的选择项
//				$('#poolPniNetworkdatagrid').jqxGrid('clearselection');
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
							$('#poolPniNetworkdatagrid').jqxGrid('clearselection');
							
		 					var resNetwork = new poolPniDatagridModel();
		 					resNetwork.searchPoolPniInfo();
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
	  var networkIp = new networkViewIpGrid();
	 
	  networkIp.searchIpConfigInfo();
	  // 设置弹出框位置
	  var windowW = $(window).width(); 
      var windowH = $(window).height(); 
      
      $("#viewNetworkOwnIpGrid").jqxWindow({ position: { x: (windowW-750)/2, y: (windowH-465)/2 } });
      $("#viewNetworkOwnIpGrid").jqxWindow('open');
}

// 弹出关联主机
function popRelationHostWindow(){
	var rowArr = $('#poolPniNetworkdatagrid').jqxGrid('getselectedrowindexes');
	if(rowArr.length == 1){
		// 查询出该行数据
		var data = $('#poolPniNetworkdatagrid').jqxGrid('getrowdata', rowArr[0]);
		
		// 先给网络已经关联的主机列表赋值
		Core.AjaxRequest({
 			url : ws_url + "/rest/host/find/network/relationed",
 			type:'post',
 			params:{
 				resNetworkSid:data.resNetworkSid
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
		
		var codeadd = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth:150});
		codeadd.getCustomCode("search-pni-relation-ve","/host/find/ve/inNetworkPool/"+resTopologySid,"resTopologyName","resTopologySid",true,"GET",null);
		
		var windowW = $(window).width(); 
	    var windowH = $(window).height(); 
	      
	    $("#pniRelationHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-450)/2 } });
	    $("#pniRelationHostWindow").jqxWindow('open');
		
	}else{
		Core.alert({
			message:"关联主机时，只能选择一个网络！",
			type:"info"
		});
	}
}

  