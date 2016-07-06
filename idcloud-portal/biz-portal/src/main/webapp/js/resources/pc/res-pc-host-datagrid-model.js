var pcHostDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    	 hostNameLike:null,
	    	 ownerVc:null,
	    	 status:null,
			 resPoolSid: ""+resTopologySid+""
		};

	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initHostInput = function(){
	    	// 初始化查询组件
	        $("#search-pc-host-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
//	        $("#search-pc-host-vc").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-pc-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        // 初始化下拉列表框 
			var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			codesearch.getCommonCode("search-pc-host-status","HOST_STATUS",true);
	    };
	    
	    // 查询主机列表
	    this.searchHostInfo = function(){
	    	 Core.AjaxRequest({
	  			url : ws_url + "/rest/host",
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
	  			    $("#pcHostdatagrid").jqxGrid({source: dataAdapter});
	  			}
	  		 });
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initHostDatagrid = function(){
	    	
	          $("#pcHostdatagrid").jqxGrid({
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
					  { text: '主机名称', datafield: 'hostName', cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
						  return "<div style='padding-top:3px;'><a class='datagrid-link' onclick='goPoolHostMonitorPage("+row+")' href='#'>&nbsp;"+value+"</a></div>";
					  }},
					  { text: '所属集群', datafield: 'parentTopologyName',width:120},
	                  { text: '主机IP', datafield: 'hostIp',width:120},
	                  { text: 'CPU数(个)', datafield: 'cpuNumber',cellsalign:'right',width:80},
	                  { text: 'CPU(核)', datafield: 'cpuCores',cellsalign:'right',width:80},
	                  { text: 'CPU使用率', datafield: 'hostCpuUsage',cellsalign:'right',width:80},
	                  { text: '内存(GB)', datafield: 'memorySizeGb',cellsalign:'right',width:80},
	                  { text: '内存使用率', datafield: 'hostMemoryUsage',cellsalign:'right',width:80},
	                  { text: '虚拟机(个)', datafield: 'vmCount',cellsalign:'right',width:80},
	                  { text: '主机状态', datafield: 'statusName',align: 'center',cellsalign:'center',width:60}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var refreshBtn = $("<div><a id='jqxRefreshHostBtn' onclick='refreshPcHostGrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
	                  var exportBtn = $("<div><a id='jqxExportHostBtn' onclick='exportPcHostDatagrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-export'></i>导出&nbsp;&nbsp;</a></div>");
	                  var relationBtn = $("<div><a id='jqxRelationHostBtn' onclick='popPcRelationHostWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-export'></i>关联主机&nbsp;&nbsp;</a></div>");
	                  var cancelRelationBtn = $("<div><a id='jqxCancelRelationHostBtn' onclick='popCancelPcRelationHostWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-export'></i>取消关联&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(refreshBtn);
	                  container.append(exportBtn);
	                  container.append(relationBtn);
	                  container.append(cancelRelationBtn);
	              }
	          });

   			  $("#jqxRefreshHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
   			  $("#jqxExportHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
   			  $("#jqxRelationHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
   			  $("#jqxCancelRelationHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
	          
	    };
  };

  // 查询主机
  function searchPcHost(){
	  var host = new pcHostDatagridModel();
	  host.searchObj.hostNameLike = $("#search-pc-host-name").val();
	  host.searchObj.ownerVc = $("#search-pc-host-vc").val();
	  host.searchObj.status = $("#search-pc-host-status").val()=="全部"?"":$("#search-pc-host-status").val(); 
	  host.searchObj.resPoolSid = resTopologySid;
	  host.searchHostInfo();
  }
  
  // 导出主机列表
  function exportPcHostDatagrid(){
	  var hostNameLike =  $("#search-pc-host-name").val();
	  var ownerVc = $("#search-pc-host-vc").val();
	  var status = $("#search-pc-host-status").val()=="全部"?"":$("#search-pc-host-status").val(); 
	  var staObj = {
		 hostNameLike: hostNameLike,
		 ownerVc: ownerVc,
    	 status: status,
    	 resPoolSid:resTopologySid
	  };
	  
	  var params = JSON.stringify(staObj);
	  window.location = ws_url + "/rest/host/pc/exportHost/" + params; 
	  
  }
  
  // 刷新主机列表
  function refreshPcHostGrid(){
	  var host = new pcHostDatagridModel();
	  host.searchObj.hostNameLike = $("#search-pc-host-name").val();
	  host.searchObj.ownerVc = $("#search-pc-host-vc").val();
	  host.searchObj.status = $("#search-pc-host-status").val()=="全部"?"":$("#search-pc-host-status").val(); 
	  host.searchObj.resPoolSid = resTopologySid;
	  host.searchHostInfo();
  }
  
  // 取消关联
  function popCancelPcRelationHostWindow(){
	  var resHostSids = "";
	  var rowindex = $('#pcHostdatagrid').jqxGrid('getselectedrowindexes');
	  if(rowindex.length > 0){
		  Core.confirm({
				title:"提示",
				message:"确定要取消该主机关联吗？",
				confirmCallback:function(){
					// 存储选中的集群
			   		for(var i=0;i<rowindex.length;i++){
			   			var data = $('#pcHostdatagrid').jqxGrid('getrowdata', rowindex[i]);
			   		    if(i == rowindex.length-1){
				   			resHostSids += data.resHostSid;
						}else{
							 resHostSids += data.resHostSid + ",";
						}
			       }
			   	   // 提交取消关联
	    	   	   Core.AjaxRequest({
	    	   			url : ws_url + "/rest/host/cancelRelation",
	    				type:"POST",
	    				params:{
	    					resHostSids : resHostSids
	    				},
	    				callback : function (data) {
	    					refreshPcHostGrid();
	    					// 关闭window
	    					$('#pcHostdatagrid').jqxGrid('clearselection');
	    			    }
	    		   });
				}
		  });
	 }
  }
  
  // 显示Pool下主机的显示信息
  function goPoolHostMonitorPage(row){
	  var data = $('#pcHostdatagrid').jqxGrid('getrowdata', row);
	  window.parent.addHostParentTabs(data.resHostSid,data.monitorNodeId,data.hostName);  
  }
  