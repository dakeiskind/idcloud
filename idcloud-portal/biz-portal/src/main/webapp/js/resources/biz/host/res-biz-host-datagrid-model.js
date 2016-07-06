var bizHostDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    	 "qm.hostNameLike":null,
	    	 "qm.bizNameLike":null,
	    	 "qm.hostOsType":null,
	    	 "qm.status":null,
	    	 "qm.resBizLevel":resBizLevel,
			 "qm.resBizSid":resBizSid
		};
	    
	    this.staObj = {
    		 hostNameLike:"",
	    	 hostOsType:"",
	    	 status:"",
	    	 resBizSid:resBizSid
	    };
	    
	    // 用户数据
	    this.searchHostInfo = function(){
	    	var dataAdapter = Core.getPagingDataAdapter({
				gridId: "bizHostdatagrid",
				url: ws_url + "/rest/hosts/biz/findAll",
				params: me.searchObj
			});
	    	
	    	$("#bizHostdatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initHostInput = function(){
	    	// 初始化查询组件
	        $("#search-biz-host-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-biz-name-host").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        
//	        $("#search-biz-host-os-type").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-biz-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        // 初始化下拉列表框 
			 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			 codesearch.getCommonCode("search-biz-host-status","HOST_STATUS",true);
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initHostDatagrid = function(){
	    	  
	    	  var dataAdapter = Core.getPagingDataAdapter({
					gridId: "bizHostdatagrid",
					url: ws_url + "/rest/hosts/biz/findAll",
					params: me.searchObj
			  });
	    	
	          $("#bizHostdatagrid").jqxGrid({
	              width: "100%",
				  theme:currentTheme,
				  source: dataAdapter,
	              columnsresize: true,
	              virtualmode: true,
				  rendergridrows: function(){
					  return dataAdapter.records;
				  },
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"none",
	              localization: gridLocalizationObj,
	              columns: [
					  { text: '主机名称', datafield: 'hostName'},
					  { text: '所属项目', datafield: 'mgtObjName'},
	                  { text: '主机IP', datafield: 'hostIp',width:120},
	                  { text: 'CPU(个)', datafield: 'cpuNumber',width:80},
	                  { text: 'CPU(核)', datafield: 'cpuCores',width:80},
	                  { text: 'CPU使用率', datafield: 'hostCpuUsage',width:100},
	                  { text: '内存(GB)', datafield: 'memorySizeGb',width:80},
	                  { text: '内存使用率', datafield: 'hostMemoryUsage',width:100},
	                  { text: '虚拟机(个)', datafield: 'vmCount',width:80},
	                  { text: '主机状态', datafield: 'statusName',width:60},
	                  { text: '所属集群', datafield: 'parentTopologyName',width:120}
	                  
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var refreshBtn = $("<div><a id='jqxRefreshHostBtn' onclick='refreshHostGrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
	                  var exportBtn = $("<div><a id='jqxExportHostBtn' onclick='exportBizHostDatagrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-export'></i>导出&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(refreshBtn);
	                  container.append(exportBtn);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#bizHostdatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#bizHostdatagrid').jqxGrid('getrowdata', index);
	          });

   			  $("#jqxRefreshHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
   			  $("#jqxExportHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
	          
	    };

  };

  // 查询主机
  function searchBizHost(){
	  
	  var host = new bizHostDatagridModel();
	  host.searchObj["qm.hostNameLike"] = $("#search-biz-host-name").val();
	  host.searchObj["qm.bizNameLike"] = $("#search-biz-name-host").val();
//	  host.searchObj["qm.hostOsType"] = $("#search-biz-host-os-type").val();
	  host.searchObj["qm.status"] = $("#search-biz-host-status").val()=="全部"?"":$("#search-biz-host-status").val(); 
	  host.searchObj["qm.resBizSid"] = resBizSid;
      host.searchHostInfo();
  }
  
  // 导出主机列表
  function exportBizHostDatagrid(){
	  var hostNameLike =  $("#search-biz-host-name").val();
	  var bizNameLike = $("#search-biz-name-host").val();
	  var status = $("#search-biz-host-status").val()=="全部"?"":$("#search-biz-host-status").val();
	  var staObj = {
		 hostNameLike: hostNameLike,
    	 bizNameLike: bizNameLike,
    	 status: status,
    	 resBizSid:resBizSid
	  };
	  
	  var params = JSON.stringify(staObj);
	  window.location = ws_url + "/rest/hosts/exportHost/biz/" + params;
	  
  }
  
  // 刷新主机列表
  function refreshHostGrid(){
	  var host = new virtualHostDatagridModel();
	  host.searchObj["qm.hostNameLike"] = $("#search-host-name").val();
	  host.searchObj["qm.bizNameLike"] = $("#search-biz-name-host").val();
//	  host.searchObj["qm.hostOsType"] = $("#search-host-os-type").val();
	  host.searchObj["qm.status"] = $("#search-host-status").val()=="全部"?"":$("#search-host-status").val(); 
	  host.searchHostInfo();
  }
  