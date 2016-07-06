var bizNetworkDatagridModel = function(){
	
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
		networkNameLike: "", 
		networkType: "", 
		ipOwnerNetwork:"",
		resBizLevel:resBizLevel,
		resBizSid:resBizSid
	};
    // 查询网络
    this.searchBizNetworkInfo = function(){
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/networks/biz",
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
 			    $("#bizNetworkdatagrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	$("#search-biz-ip").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	var networkconfig = new codeModel({width:120,autoDropDownHeight:true});
    	$("#search-biz-networkName").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	networkconfig.getCommonCode("search-biz-network-type","NETWORK_TYPE",true);
        $("#search-biz-network-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    	
    };

    // 初始化用户datagrid的数据源
    this.initBizNetworkDatagrid = function(){
          $("#bizNetworkdatagrid").jqxGrid({
              width: "100%",
			  theme:currentTheme,
              columnsresize: true,
              pageable: true, 
              pagesize: 10, 
              autoheight: true,
              autowidth: false,
              sortable: true,
              selectionmode:"none",
              localization: gridLocalizationObj,
              columns: [
                  { text: '网络名称', datafield: 'networkName'},
                  { text: '网络类型', datafield: 'networkTypeName',width:80},
                  { text: 'VLAN ID', datafield: 'vlanIdName',width:70},
                  { text: '子网掩码', datafield: 'subnetMask'},
                  { text: '子网', datafield: 'subnet'},
                  { text: '网关', datafield: 'gateway'},
                  { text: 'IP使用数(个)', datafield: 'ipUseCount', width:80},
                  { text: 'IP总数(个)', datafield: 'ipTotalCount', width:80},
                  { text: '使用状态', datafield: 'usageStatusName', width:80},
                  { text: '操作', datafield: 'viewIpGrid', columntype: 'button',width:110, cellsrenderer: function () {
                	  return "管理IP列表";
	                   }, buttonclick: function (row) {
	                      editrow = row;
	              		  var data = $('#bizNetworkdatagrid').jqxGrid('getrowdata', editrow);
	            		  
	              		  // 初始化
	              		  var networkIp = new bizNetworkViewIpGridModel();
	              		  resNetworkSid = data.resNetworkSid;
	                	  networkIp.searchIpConfigInfo(data.resNetworkSid);
		              	  // 设置弹出框位置
		              	  var windowW = $(window).width(); 
		                  var windowH = $(window).height(); 
		                  $("#viewBizNetworkOwnIpGrid").jqxWindow({ position: { x: (windowW-750)/2, y: (windowH-465)/2 } });
		                  $("#viewBizNetworkOwnIpGrid").jqxWindow('open');                 
		               }
                  }
              ],
              showtoolbar: true,
              // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var exportNetwork = $("<div><a id='exportNetwork' onclick='exportNetwork()'>&nbsp;&nbsp;<i class='icons-blue icon-export'></i>导出&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
                  container.append(exportNetwork);
              }
          });
          
          $("#exportNetwork").jqxButton({ width: '80',theme:currentTheme,height: '18px'});
          
    };
    
};

function exportNetwork(){
	 this.searchObj = {
			 networkNameLike : $("#search-biz-networkName").val(),
			 ipType : $("#search-biz-network-type").val()=="全部"?"":$("#search-biz-network-type").val(),
			 resBizLevel:resBizLevel,
			 resBizSid:resBizSid,
			 resParentBizSid:resParentBizSid
      };
	  var params = JSON.stringify(searchObj);
	  window.location = ws_url + "/rest/networks/exportNetwork/" + params; 
}

//条件查询IP配置管理
function searchBizNetwork(){
	var network = new bizNetworkDatagridModel();
	network.searchObj.networkNameLike = $("#search-biz-networkName").val();
	network.searchObj.ipOwnerNetwork = $("#search-biz-ip").val();
	network.searchObj.networkType = $("#search-biz-network-type").val()=="全部"?"":$("#search-biz-network-type").val();
	network.searchObj.resBizSid = resBizSid;
	network.searchBizNetworkInfo();
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

  