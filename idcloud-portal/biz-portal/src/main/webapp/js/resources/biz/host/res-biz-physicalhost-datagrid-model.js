var bizPhysicalHostDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    	 hostNameLike:null,
	    	 status:null,
			 mgtObjSid:resBizSid,
			 hostIpLike:null
		};
	    
	    this.staObj = {
    		 hostNameLike:"",
	    	 hostOsType:"",
	    	 status:"",
	    	 resBizSid:resBizSid
	    };
	    
	    // 用户数据
	    this.searchPhysicalHostInfo = function(){
	    	$("#mgtObjSid-host").val(resBizSid);
	    	Core.AjaxRequest({
	    		url : ws_url + "/rest/host/findPhysicalHost",
				params : me.searchObj,
				type:'post',
				async:false,
				callback : function(data) {
					for(var i=0;i<data.length;i++){
						var mgtObjCondition = {};
				        var mgtObjParams = {
				    		condition: mgtObjCondition
				        };
				        mgtObjCondition['mgtObjSid'] = data[i].mgtObjSid;
				    	Core.AjaxRequest({
							url : ws_url + "/rest/mgtObj/find",
							type:'post',
							async:false,
							params: mgtObjParams,
							callback : function (mgtObjData) {
								data[i].inNoticeTime = mgtObjData[0].inNoticeTime;
							}
						});
					}
					me.items(data);
				    var sourcedatagrid = {
						datatype : "json",
						localdata : me.items
					};
					var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
					$("#bizPhysicalHostdatagrid").jqxGrid({source : dataAdapter});
				}
			});
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initPhysicalHostInput = function(){
	    	// 初始化查询组件
	        $("#search-biz-physicalhost-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-biz-physicalhost-ip").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-biz-physicalhost-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        // 初始化下拉列表框 
			 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			 codesearch.getCommonCode("search-biz-physicalhost-status","HOST_STATUS",true);
			 
			 if(10 == resBizSid){
	            	$("#search-biz-nanotube-host-button").jqxButton({ width: '120px',height:'25px',theme:currentTheme}); 
	            }
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initPhysicalHostDatagrid = function(){
	          $("#bizPhysicalHostdatagrid").jqxGrid({
	        	  width: "99.8%",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              sortable: true,
	              autowidth: false,
	              selectionmode:"checkbox",
	              localization: gridLocalizationObj,
	              columns: [
					  { text: '主机名称', datafield: 'hostName',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
		              	  var td = $("#bizPhysicalHostdatagrid").jqxGrid('getrowdata', row);
		            	  if(td.inNoticeTime=="1"){
		            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.hostName+"</div>";
		            	  }else if(td.inNoticeTime=="-1"){
		            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.hostName+"</div>";
		            	  }
		              } },
					  { text: '所属项目', datafield: 'mgtObjName',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
		              	  var td = $("#bizPhysicalHostdatagrid").jqxGrid('getrowdata', row);
		            	  if(td.inNoticeTime=="1"){
		            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.mgtObjName+"</div>";
		            	  }else if(td.inNoticeTime=="-1"){
		            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.mgtObjName+"</div>";
		            	  }
		              } },
	                  { text: '主机IP', datafield: 'hostIp',width:120,cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
	                  	  var td = $("#bizPhysicalHostdatagrid").jqxGrid('getrowdata', row);
	                	  if(td.inNoticeTime=="1"){
	                		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.hostIp+"</div>";
	                	  }else if(td.inNoticeTime=="-1"){
	                		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.hostIp+"</div>";
	                	  }
	                  } },
	                  { text: 'CPU(个)', datafield: 'cpuNumber',width:80,cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
	                  	  var td = $("#bizPhysicalHostdatagrid").jqxGrid('getrowdata', row);
	                	  if(td.inNoticeTime=="1"){
	                		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.cpuNumber+"</div>";
	                	  }else if(td.inNoticeTime=="-1"){
	                		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.cpuNumber+"</div>";
	                	  }
	                  } },
	                  { text: 'CPU(核)', datafield: 'cpuCores',width:80,cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
	                  	  var td = $("#bizPhysicalHostdatagrid").jqxGrid('getrowdata', row);
	                	  if(td.inNoticeTime=="1"){
	                		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.createdBy+"</div>";
	                	  }else if(td.inNoticeTime=="-1"){
	                		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.createdBy+"</div>";
	                	  }
	                  } },
	                  { text: 'CPU使用率', datafield: 'hostCpuUsage',width:100,cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
	                  	  var td = $("#bizPhysicalHostdatagrid").jqxGrid('getrowdata', row);
	                	  if(td.inNoticeTime=="1"){
	                		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.hostCpuUsage+"</div>";
	                	  }else if(td.inNoticeTime=="-1"){
	                		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.hostCpuUsage+"</div>";
	                	  }
	                  } },
	                  { text: '内存(GB)', datafield: 'memorySizeGb',width:80,cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
	                  	  var td = $("#bizPhysicalHostdatagrid").jqxGrid('getrowdata', row);
	                	  if(td.inNoticeTime=="1"){
	                		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.memorySizeGb+"</div>";
	                	  }else if(td.inNoticeTime=="-1"){
	                		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.memorySizeGb+"</div>";
	                	  }
	                  } },
	                  { text: '内存使用率', datafield: 'hostMemoryUsage',width:100,cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
	                  	  var td = $("#bizPhysicalHostdatagrid").jqxGrid('getrowdata', row);
	                	  if(td.inNoticeTime=="1"){
	                		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.hostMemoryUsage+"</div>";
	                	  }else if(td.inNoticeTime=="-1"){
	                		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.hostMemoryUsage+"</div>";
	                	  }
	                  } },
	                  { text: '主机状态', datafield: 'statusName',width:60,cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
	                  	  var td = $("#bizPhysicalHostdatagrid").jqxGrid('getrowdata', row);
	                	  if(td.inNoticeTime=="1"){
	                		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+(td.statusName==null?"":td.statusName)+"</div>";
	                	  }else if(td.inNoticeTime=="-1"){
	                		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+(td.statusName==null?"":td.statusName)+"</div>";
	                	  }
	                  } }
	                  
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var refreshBtn = $("<div><a id='jqxRefreshPhysicalHostBtn' onclick='refreshPhysicalHostGrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
//	                  var addHost = $("<div><a id='jqxAddHostBtn' onclick='addHostWin()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>纳管物理主机&nbsp;&nbsp;</a></div>");
	                  var deleteHost = $("<div><a id='jqxDeleteHostBtn' onclick='deleteHostWin()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>取消纳管&nbsp;&nbsp;</a></div>");
//	                  var exportBtn = $("<div><a id='jqxExportPhysicalHostBtn' onclick='exportBizPhysicalHostDatagrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-export'></i>导出&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(refreshBtn);
//	                  container.append(addHost);
	                  container.append(deleteHost);
//	                  container.append(exportBtn);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#bizPhysicalHostdatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#bizPhysicalHostdatagrid').jqxGrid('getrowdata', index);
	    		  $("#jqxDeleteHostBtn").jqxButton({disabled: false});
	          });

   			  $("#jqxRefreshPhysicalHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
//   			  $("#jqxAddHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
   			  $("#jqxDeleteHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
//   			  $("#jqxExportPhysicalHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
	          
	    };
	    
	    this.initPopWindow = function(){
	    	$("#addMgtObjHostWindow").jqxWindow({
                width: 800, 
                height:430,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#cancelMgtObjHost"), 
                theme: currentTheme,
                modalOpacity: 0.3,
                initContent:function(){
                	$("#search-add-mgtObj-host-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
                	$("#search-add-mgtObj-host-ip").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
                	$("#search-add-mgtObj-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
                	$("#saveMgtObjHost").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#cancelMgtObjHost").jqxButton({ width: '50',height:"25",theme:currentTheme});
                }
            });
	    	
	    	$("#mgtObj-hostGrid").jqxGrid({
	        	  width: "99.8%",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              sortable: true,
	              autowidth: false,
	              selectionmode:"checkbox",
	              localization: gridLocalizationObj,
	              columns: [
					  { text: '主机名称', datafield: 'hostName'},
	                  { text: '主机IP', datafield: 'hostIp',width:120},
	                  { text: 'CPU(个)', datafield: 'cpuNumber',width:80},
	                  { text: 'CPU(核)', datafield: 'cpuCores',width:80},
	                  { text: '内存(GB)', datafield: 'memorySizeGb',width:80},
	                  { text: '主机状态', datafield: 'statusName',width:60}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var relHost = $("<div><a id='jqxRelHostBtn' onclick='createHostRelWin()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>纳管物理主机&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(relHost);
	              }
	          });
	    	
	    	$("#mgtObj-hostGrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  
	    		  $("#jqxRelHostBtn").jqxButton({ disabled: false });
		   			 
	        });
	    	
	    	$("#jqxRelHostBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
	    	
	    	$("#nanotubeHostChooseBizWindow").jqxWindow({
	    		width: 300, 
                height:146,
                resizable: true,  
                isModal: true, 
                autoOpen: false, 
                theme: currentTheme,
                cancelButton: $("#chooseMgtObjCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	
                	$("#chooseMgtobjSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
                	$("#chooseMgtObjCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
                }
            });
	    };

  };

  // 查询主机
  function searchPhysicalBizHost(){
	  
	  var host = new bizPhysicalHostDatagridModel();
	  host.searchObj.hostNameLike = $("#search-biz-physicalhost-name").val();
	  host.searchObj.hostIpLike = $("#search-biz-physicalhost-ip").val();
//	  host.searchObj.bizNameLike = $("#search-biz-name-host").val();
//	  host.searchObj["qm.hostOsType"] = $("#search-biz-host-os-type").val();
	  host.searchObj.status = $("#search-biz-physicalhost-status").val()=="全部"?"":$("#search-biz-physicalhost-status").val(); 
	  host.searchObj["qm.hostNameLike"] = $("#search-biz-physicalhost-name").val();
	  host.searchObj["qm.hostIpLike"] = $("#search-biz-physicalhost-ip").val();
//	  host.searchObj["qm.bizNameLike"] = $("#search-biz-name-host").val();
//	  host.searchObj["qm.hostOsType"] = $("#search-biz-host-os-type").val();
	  host.searchObj["qm.status"] = $("#search-biz-physicalhost-status").val()=="全部"?"":$("#search-biz-physicalhost-status").val(); 
	  host.searchObj["qm.resBizSid"] = resBizSid;
      host.searchPhysicalHostInfo();
  }
  
  // 导出主机列表
  function exportPhysicalBizHostDatagrid(){
	  var hostNameLike =  $("#search-biz-physicalhost-name").val();
	  var status = $("#search-biz-physicalhost-status").val()=="全部"?"":$("#search-biz-host-status").val();
	  var staObj = {
		 hostNameLike: hostNameLike,
    	 status: status,
    	 resBizSid:resBizSid
	  };
	  
	  var params = JSON.stringify(staObj);
	  window.location = ws_url + "/rest/host/exportHost/biz/" + params; 
	  
  }
  
  function popNanotubeHostWindow(){
	  searchMgtObjHostRel();
	  $("#addMgtObjHostWindow").jqxWindow("open");
  }
  
  function createHostRelWin(){
	  var rowindex = $('#mgtObj-hostGrid').jqxGrid('getselectedrowindexes');
	  var resBizVmSids = "";
	  if(rowindex.length > 0){
		  for(var i=0;i<rowindex.length;i++){
	   			var data = $('#mgtObj-hostGrid').jqxGrid('getrowdata', rowindex[i]);
	   			if(i == rowindex.length-1){
	   				// 为了查询出集群下面的存储，加上引号
	   				resBizVmSids += data.resHostSid;
				}else{
					resBizVmSids += data.resHostSid + ",";
				}
	   	  }
		  // 选中纳管的虚拟机的sid
		  $("#biz_nanotube_resBizHostSids").val(resBizVmSids);
		  // 清除下来列表框
		  var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownHeight:200,dropDownWidth:150});
		  codeadd.getCustomCode("host_nanotube_mgtobjSid","/mgtObj/find","name","mgtObjSid", false, 'POST', {condition:{'level': '1'}});
      	  //初始化用户下拉框
      	  $("#host_nanotube_userSid").jqxDropDownList({
				width: 150,
				height: 25,
				displayMember: "account", 
				valueMember: "userSid",
				autoDropDownHeight :true,
				theme:"metro"
		  });
      	  $("#host_nanotube_mgtobjSid").on('change', function (event) {
      		  $("#host_nanotube_userSid").jqxDropDownList('clear'); 
      		  if($("#host_nanotube_mgtobjSid").val()!=null&&$("#host_nanotube_mgtobjSid").val()!=""){
      			  Core.AjaxRequest({
      				  url : ws_url + "/rest/user/findAllUsersByMgtObj/"+$("#host_nanotube_mgtobjSid").val(),
      				  type:'get',
      				  async : false,
      				  callback : function(data) {
      					  $("#host_nanotube_userSid").jqxDropDownList({
      						  width: 150,
      						  height: 25,
      						  source: data,
      						  selectedIndex: 0,
      						  displayMember: "account", 
      						  valueMember: "userSid",
      						  autoDropDownHeight :true,
      						  theme:"metro"
      					  });
      				  }
      			  });
      		  }
          });
      	  $("#host_nanotube_mgtobjSid").trigger('change');
      	  var windowW = $(window).width(); 
		  var windowH = $(window).height(); 
		  $("#nanotubeHostChooseBizWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-136)/2 } });
		  $("#nanotubeHostChooseBizWindow").jqxWindow('open');
	  }else{
		  $("#jqxRelHostBtn").jqxButton({ disabled: true });
	  }
  }
  
  function deleteHostWin(){
	  var rowindex = $('#bizPhysicalHostdatagrid').jqxGrid('getselectedrowindexes');
//	  var data;
//	  if(rowindex >= 0){
//	       data = $('#bizPhysicalHostdatagrid').jqxGrid('getrowdata', rowindex);
//	  }
	  var datas = "";
	  if(rowindex.length > 0){
		  for(var i=0;i<rowindex.length;i++){
			  var data = $('#bizPhysicalHostdatagrid').jqxGrid('getrowdata', rowindex[i]);
			  datas = datas + data.resHostSid + ",";
		  }
		  Core.confirm({
			  title:"提示",
			  message:"确定要取消关联吗？",
			  confirmCallback:function(){
				  
				  Core.AjaxRequest({
					  url : ws_url + "/rest/serviceInstances/cancelResHostMgtObjRel/"+datas,
					  type:"get",
					  async:false,
					  callback : function (data) {
						  refreshPhysicalHostGrid();
					  }
				  });
			  }
		  });
	  }else{
		  $("#jqxDeleteHostBtn").jqxButton({disabled: true});
	  }
  }
  
 function searchMgtObjHostRel(){
	  var hostName = $("#search-add-mgtObj-host-name").val();
	  var hostIp = $("#search-add-mgtObj-host-ip").val();
	  
	  Core.AjaxRequest({
  		url : ws_url + "/rest/host/findPhysicalHost",
			params : {
		    	 hostNameLike:hostName,
		    	 hostIpLike : hostIp,
		    	 mgtObjHostRel:"1"
			},
			type:'post',
			async:false,
			callback : function(data) {
			    var sourcedatagrid = {
					datatype : "json",
					localdata : data
				};
				var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
				$("#mgtObj-hostGrid").jqxGrid({source : dataAdapter});
			}
	  });
}
  
  // 刷新主机列表
  function refreshPhysicalHostGrid(){
	  var host = new bizPhysicalHostDatagridModel();
//	  host.searchObj["qm.hostNameLike"] = $("#search-host-name").val();
//	  host.searchObj["qm.bizNameLike"] = $("#search-biz-name-host").val();
//	  host.searchObj["qm.hostOsType"] = $("#search-host-os-type").val();
//	  host.searchObj["qm.status"] = $("#search-host-status").val()=="全部"?"":$("#search-host-status").val(); 
	  host.searchPhysicalHostInfo();
  }
  
function submitNanotubeHostInfo(){
	 var resHostSids = "";
	 var rowindex = $('#mgtObj-hostGrid').jqxGrid('getselectedrowindexes');
	 if(rowindex.length > 0){
		// 存储选中的主机
		for(var i=0;i<rowindex.length;i++){
			var data = $('#mgtObj-hostGrid').jqxGrid('getrowdata', rowindex[i]);
			if(i == rowindex.length-1){
				resHostSids+= data.resHostSid;
			}else{
				resHostSids+= data.resHostSid+",";
			}
		}
		if($("#host_nanotube_mgtobjSid").val()==null||$("#host_nanotube_mgtobjSid").val()==""){
			  Core.alert({
				  title : "提示",
				  message : "请选择项目！"
			  });
			  return;
		  }else if($("#host_nanotube_userSid").val()==null||$("#host_nanotube_userSid").val()==""){
			  Core.alert({
				  title : "提示",
				  message : "请选择所属用户！"
			  });
			  return;
		  }else{
				// 提交选中的主机
				Core.AjaxRequest({
					url : ws_url + "/rest/serviceInstances/saveResHostMgtObjRel/"+resHostSids
							+"/"+$("#host_nanotube_mgtobjSid").val()+"/"+$("#host_nanotube_userSid").val(),
					type:"get",
					async:false,
					callback : function (data) {
						// 清除选择项
						$('#mgtObj-hostGrid').jqxGrid('clearselection');
						refreshPhysicalHostGrid();
						$("#nanotubeHostChooseBizWindow").jqxWindow('close');
						$("#addMgtObjHostWindow").jqxWindow('close');
				    },
				    failure:function(data){
				    	
				    }
				 });
		  }
	 }
}
  