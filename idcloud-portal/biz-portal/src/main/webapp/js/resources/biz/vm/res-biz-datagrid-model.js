var virtualBizVMDatagridModel = function () {
 	
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		"qm.vmNameLike":"",
	    		"qm.bizNameLike":"",
	    		"qm.status":"",
	    		"qm.vmIpLike":"",
	    		"qm.parentBizSids":resBizSid
		};

	    // 用户数据
	    this.searchVMInfo = function(){
	    	var dataAdapter = Core.getPagingDataAdapter({
				gridId: "bizVmdatagrid",
				url: ws_url + "/rest/resbiz/vm/findAll",
				params: me.searchObj
			});
	    	
	    	$("#bizVmdatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    };
	    	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initVMInput = function(){
	    	// 初始化查询组件
	        $("#search-biz-vm-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-biz-vm-ip").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
//	        $("#search-biz-name-vm").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-biz-vm-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme}); 

	        // 初始化下拉列表框 
            if(10 == resBizSid){
            	$("#export-biz-vm-button").jqxButton({ width: '120px',height:'25px',theme:currentTheme}); 
            	$("#search-biz-nanotube-vm-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme}); 
//            	$("#export-vm-monitor-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme}); 
            }
	        
			var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			codesearch.getCommonCode("search-biz-vm-status","RES_VM_STATUS",true);
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initVMDatagrid = function(){
	    	 var dataAdapter = Core.getPagingDataAdapter({
					gridId: "bizVmdatagrid",
					url: ws_url + "/rest/resbiz/vm/findAll",
					params: me.searchObj
			  });
	          $("#bizVmdatagrid").jqxGrid({
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
	              selectionmode:"checkbox",
	              localization: gridLocalizationObj,
	              columns: [
		  	                  { text: '虚拟机名称', datafield: 'vmName', width:160,cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
		  	                	  var td = $("#bizVmdatagrid").jqxGrid('getrowdata', row);
				            	  if(td.inNoticeTime=="1"){
				            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>"+
				            		  			"<a class='datagrid-link' onclick='goBizVmMonitorPage("+row+")' href='#'>&nbsp;"+value+"</a></div>";
				            	  }else if(td.inNoticeTime=="-1"){
				            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>"+
				            		  			"<a class='datagrid-link' onclick='goBizVmMonitorPage("+row+")' href='#'>&nbsp;"+value+"</a></div>";
				            	  }
							  }},
							  { text: '所属项目', datafield: 'mgtObjName',width:120,cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
				              	  var td = $("#bizVmdatagrid").jqxGrid('getrowdata', row);
				            	  if(td.inNoticeTime=="1"){
				            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+value+"</div>";
				            	  }else if(td.inNoticeTime=="-1"){
				            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+value+"</div>";
				            	  }
				              } },
			                  { text: '操作系统', datafield: 'osVersion',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
				              	  var td = $("#bizVmdatagrid").jqxGrid('getrowdata', row);
				            	  if(td.inNoticeTime=="1"){
				            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+value+"</div>";
				            	  }else if(td.inNoticeTime=="-1"){
				            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+value+"</div>";
				            	  }
				              } },
			                  { text: 'CPU(核)', datafield: 'cpuCores',width:60,cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
				              	  var td = $("#bizVmdatagrid").jqxGrid('getrowdata', row);
				            	  if(td.inNoticeTime=="1"){
				            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+value+"</div>";
				            	  }else if(td.inNoticeTime=="-1"){
				            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+value+"</div>";
				            	  }
				              } },
//			                  { text: 'CPU使用率', datafield: 'cpuUsage',width:60},
//			                  { text: 'CPU使用率峰值', datafield: 'cpuMaxUsage',width:60},
			                  { text: '内存(MB)', datafield: 'memorySize',width:60,cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
				              	  var td = $("#bizVmdatagrid").jqxGrid('getrowdata', row);
				            	  if(td.inNoticeTime=="1"){
				            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+value+"</div>";
				            	  }else if(td.inNoticeTime=="-1"){
				            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+value+"</div>";
				            	  }
				              } },
//			                  { text: '内存使用率', datafield: 'memUsage',width:60},
//			                  { text: '内存使用率峰值', datafield: 'memMaxUsage',width:60},
			                  { text: '存储(GB)', datafield: 'vmDiskSize',width:60,cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
				              	  var td = $("#bizVmdatagrid").jqxGrid('getrowdata', row);
				            	  if(td.inNoticeTime=="1"){
				            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+value+"</div>";
				            	  }else if(td.inNoticeTime=="-1"){
				            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+value+"</div>";
				            	  }
				              } },
//			                  { text: '存储使用率', datafield: 'diskUsage',width:60},
			                  { text: 'IP地址', datafield: 'vmIp',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
				              	  var td = $("#bizVmdatagrid").jqxGrid('getrowdata', row);
				            	  if(td.inNoticeTime=="1"){
				            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+value+"</div>";
				            	  }else if(td.inNoticeTime=="-1"){
				            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+value+"</div>";
				            	  }
				              } },
			                  { text: '监控/状态',width:60,cellsrenderer:function (row, datafield, value) {
			                	  var data = $('#bizVmdatagrid').jqxGrid('getrowdata', row);
			                	  var monitor = "";
			                	  var status = "";
			                	  if(data.monitorNodeId != null && data.monitorNodeId != ""){
			                		  monitor = "<i class='icon-circle' title='已监控' style='font-size:16px;color:#62CB62'></i>";
			                	  }else{
//			                		  if(data.monitorStatus =="02"){
//			                			  monitor = "<i class='icon-circle' title='已监控' style='font-size:16px;color:#62CB62'></i>";
//			                		  }else{
			                			  monitor = "<i class='icon-circle' title='未监控' style='font-size:16px;color:#BFBFBF'></i>";
//			                		  }
			                	  }
			                	  
			                	  if(data.status =="normal"){
			                		  status = "<i class='icon-circle' title='开机' style='font-size:16px;color:#62CB62'></i>";
			                	  }else{
			                		  status = "<i class='icon-circle' title='关机' style='font-size:16px;color:#BFBFBF'></i>";
			                	  }
				            	  if(data.inNoticeTime=="1"){
				            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+monitor +"&nbsp;&nbsp;" +status+"</div>";
				            	  }else if(data.inNoticeTime=="-1"){
				            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+monitor +"&nbsp;&nbsp;" +status+"</div>";
				            	  } else if(data.inNoticeTime=="0"){
				            		  return "<div style='width: 100%;height: 100%;line-height:25px;'>&nbsp;"+monitor +"&nbsp;&nbsp;" +status+"</div>";
				            	  }
			                  }},
//			                  { text: '所属主机', datafield: 'ownerHost',width:130},
//			                  { text: '均值流量(Mbps)', datafield: 'netWorkAvg',width:60},
//			                  { text: '峰值流量(Mbps)', datafield: 'netWorkMax',width:60},
//			                  { text: '业务属性', datafield: 'parentBizName',width:60},
//			                  { text: '创建时间', datafield: 'dredgeDate',width:100},
			                  { text: '操作', datafield: '',cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
								   var tr = "<div style='width:80px;height:22px;line-height:25px;'>&nbsp;&nbsp;<i onclick='goBizVmMonitorPage("+row+")' title='详情' class='icons-blue icon-popup'></i>"+
								   				"&nbsp;&nbsp;<i onclick='popConfigIpWindow("+row+")' title='调整IP' class='icons-blue icon-wrench'></i></div>";
								      var td = $("#bizVmdatagrid").jqxGrid('getrowdata', row);
					            	  if(td.inNoticeTime=="1"){
					            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>"+tr+"</div>";
					            	  } else if(td.inNoticeTime=="-1"){
					            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>"+tr+"</div>";
					            	  } else if(td.inNoticeTime=="0") {
					            		  return "<div style='width: 100%;height: 100%;line-height:25px;'>"+tr+"</div>";
					            	  }
							  	},width:80
//							  	&nbsp;&nbsp;<i onclick='switchVNCBtn("+row+")' title='开启/关闭VNC服务' class='icons-blue icon-exchange'></i> 
			                  }
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var refresh = $("<div><a id='refreshVm' onclick='refreshVm()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-check'></i>刷新&nbsp;&nbsp;</a></div>");
	                  var cancel = $("<div><a id='cancelVm' onclick='popCancelVmWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-check'></i>取消纳管&nbsp;&nbsp;</a></div>");
	                  var setMonitorBtn = $("<div><a id='jqxSetMonitorBtn' onclick='getVmMonitorNodeId()' style='margin-left:-2px'>&nbsp;&nbsp;<i class='icons-blue icon-level-down'></i>加入监控&nbsp;&nbsp;</a></div>");
	                  var deleteMonitorBtn = $("<div><a id='jqxDeleteMonitorBtn' onclick='deleteVmMonitorNodeId()' style='margin-left:-2px'>&nbsp;&nbsp;<i class='icons-blue icon-level-up'></i>退出监控&nbsp;&nbsp;</a></div>");
//	                  var modifyVm = $("<div><a id='modifyVm' onclick='popModifyVmWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>修改&nbsp;&nbsp;</a></div>");

	                  //vm
//					  var asyncBtn = $("<div><a id='jqxAsyncBtn' onclick='asyncVmInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-ccw'></i>同步&nbsp;&nbsp;</a></div>");               
	                  var editBtn = $("<div><a id='jqxEditvmBtn' onclick='startVm()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-play'></i>开机&nbsp;&nbsp;</a></div>");
	                  var closeBtn = $("<div><a id='jqxCloseBtn' onclick='stopVm()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-stop'></i>关机&nbsp;&nbsp;</a></div>");
//	                  var restartBtn = $("<div><a id='jqxRestartBtn' onclick='restartVm()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cw'></i>重启&nbsp;&nbsp;</a></div>");
//	                  var removeBtn = $("<div><a id='jqxRemoveVmBtn' onclick='popMigrateVmWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-move'></i>迁移&nbsp;&nbsp;</a></div>");
	                  var tuidingBtn = $("<div><a id='jqxTuidingBtn' onclick='deleteVm()' style='margin-left:-2px'>&nbsp;&nbsp;<i class='icons-blue icon-trash'></i>删除&nbsp;&nbsp;</a></div>");
					 
	                  toolbar.append(container);
	                  container.append(refresh);  
	                  container.append(cancel);  
	                  container.append(setMonitorBtn);  
	                  container.append(deleteMonitorBtn);  
//	                  container.append(modifyVm);
	                  
//	                  container.append(asyncBtn);
	                  container.append(editBtn);
	                  container.append(closeBtn);
//	                  container.append(restartBtn);
//	                  container.append(removeBtn);
	                  container.append(tuidingBtn);
	                 
	              }
	          });
	          
	       // 控制按钮是否可用
			  $("#bizVmdatagrid").on('rowselect', function(event) {
				  
			      var args = event.args;
			      var data = args.row;
			      if(data.monitorNodeId != null){
			    	  $("#jqxSetMonitorBtn").jqxButton({disabled: true});
			    	  $("#jqxDeleteMonitorBtn").jqxButton({disabled: false});
			      }else{
			    	  $("#jqxSetMonitorBtn").jqxButton({disabled: false});
			    	  $("#jqxDeleteMonitorBtn").jqxButton({disabled: true});
			      }
				  
				  $("#cancelVm").jqxButton({disabled: false }); 
//	              $("#setMonitorNode").jqxButton({disabled: false }); 
//	              $("#modifyVm").jqxButton({disabled: false }); 
	    		  
//    			  $("#jqxAsyncBtn").jqxButton({ disabled: false });
    			  $("#jqxEditvmBtn").jqxButton({ disabled: false });
	   			  $("#jqxCloseBtn").jqxButton({disabled: false});
//	   			  $("#jqxRestartBtn").jqxButton({disabled: false});
//	   			  $("#jqxRemoveVmBtn").jqxButton({disabled: false});
	   			  $("#jqxTuidingBtn").jqxButton({disabled: false});
              });
			  
			  $("#refreshVm").jqxButton({width: '60',theme:currentTheme,height: '18px'}); 
			  $("#cancelVm").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true }); 
			  $("#jqxSetMonitorBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true }); 
              $("#jqxDeleteMonitorBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true }); 
//              $("#modifyVm").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true }); 
			  
//	    	  $("#jqxAsyncBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
	    	  $("#jqxEditvmBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
 			  $("#jqxCloseBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
// 			  $("#jqxRestartBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
// 			  $("#jqxRemoveVmBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
 			  $("#jqxTuidingBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
	          
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
			$("#chooseFilesWindow").jqxWindow({
	            width: 650, 
	            height:250,
	            resizable: true,  
	            isModal: true, 
	            autoOpen: false, 
	            theme: currentTheme,
	            cancelButton: $("#exportBizVmCancel"), 
	            modalOpacity: 0.3,
	            initContent:function(){
	            	$("#checkBox1").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox2").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox3").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox4").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox5").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox6").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox7").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox8").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox9").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox10").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox11").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox12").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox13").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox14").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox15").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox16").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox17").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#checkBox18").jqxCheckBox({ width: 120, height: 25 ,theme:currentTheme, checked: true});
	            	$("#exportExcelSubmit").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	            	$("#exportBizVmCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	            }
			});
	    };
  };

  // 查询虚拟机
  function searchBizVm(){
	  var vm = new virtualBizVMDatagridModel();
	  vm.searchObj["qm.vmNameLike"] = $("#search-biz-vm-name").val();
	  vm.searchObj["qm.vmIpLike"] = $("#search-biz-vm-ip").val();
//	  vm.searchObj["qm.bizNameLike"] = $("#search-biz-name-vm").val();
	  vm.searchObj["qm.status"] = $("#search-biz-vm-status").val()=="全部"?"":$("#search-biz-vm-status").val();
	  vm.searchVMInfo();
  }
 
  // 弹出虚拟机监控详情
  function goBizVmMonitorPage(row){
	    // 获得当前双击的vm数据
	    var rowData = $('#bizVmdatagrid').jqxGrid('getrowdata', row);
	    window.parent.addParentTabs(rowData.resBizVmSid,rowData.monitorNodeId,rowData.vmName);
  }
  
//弹出选择迁移类型
  function popMigrateVmWindow() {
  	var rowindex = $('#bizVmdatagrid').jqxGrid('selectedrowindexes');
  	var datas = new Array();
  	if(rowindex.length >= 0){
		for(var i=0;i<rowindex.length;i++){
			var data = $('#bizVmdatagrid').jqxGrid('getrowdata', rowindex[i]);
			datas[i]=data.resBizVmSid;
		}
  		var selectMigrateType=new migrateSelectModel();
  		selectMigrateType.setData(datas);
  		selectMigrateType.initPopWindow();
  		var windowW = $(window).width();
  		var windowH = $(window).height();
  		$("#migrateSelectWindow").jqxWindow({
  			position : {
  				x : (windowW - 350) / 2,
  				y : (windowH - 185) / 2
  			}
  		});
  		$("#migrateSelectWindow").jqxWindow('open');
  	}
}
  
//弹出导出window,选择导出字段
  function exportBizVMRel(){
  var windowW = $(window).width();
  var windowH = $(window).height();
  $("#chooseFilesWindow").jqxWindow({ position: { x: (windowW-650)/2, y: (windowH-250)/2 } });
  $("#chooseFilesWindow").jqxWindow('open');
	  window.location = ws_url + "/rest/mgtObj/exportMgtObjRes";
  }
  
  function exportVmMonitor(){
	  window.location = ws_url + "/rest/resbiz/vm/getVmMonitorForReport";
  }

  // 导出vm列表
  function exportExcel(){
  	var str = true;
  	var length = $("#fieldTable").find("td").length;
  	for(var i=1;i<=length;i++){
  		str = str +","+$("#checkBox"+i).jqxCheckBox('checked');
  	}
  	$("#chooseFilesWindow").jqxWindow('close');
  	
  	window.location = ws_url + "/rest/resbiz/vm/getBizVmMonitorForReport/"+str;
  	
  };
  
  // 刷新业务视图虚拟机列表
  function refreshVm(){
	  var vm = new virtualBizVMDatagridModel();
	  vm.searchObj["qm.vmNameLike"] = $("#search-biz-vm-name").val();
	  vm.searchObj["qm.vmIpLike"] = $("#search-biz-vm-ip").val();
//	  vm.searchObj["qm.bizNameLike"] = $("#search-biz-name-vm").val();
	  vm.searchObj["qm.status"] = $("#search-biz-vm-status").val()=="全部"?"":$("#search-biz-vm-status").val();
	  vm.searchVMInfo();
  }
  

  // 获取vm的监控节点
  function getVmMonitorNodeId(){
	
	  var rowindex = $('#bizVmdatagrid').jqxGrid('selectedrowindexes');
	  
	  var isok = $("#jqxSetMonitorBtn").jqxButton("disabled");
	  
	  if(rowindex.length == 1 && !isok){
		    var data = $('#bizVmdatagrid').jqxGrid('getrowdata', rowindex[0]);
		    
		    Core.confirm({
				  title:"提示",
				  message:"确定要将该虚拟机加入监控吗？",
				  confirmCallback : function() {
					  Core.AjaxRequest({
					  		url : ws_url + "/rest/vms/addNodeId/"+data.resBizVmSid,
					  		type:"get",
					  		async:false,
					  		callback : function(result) {
					  			 // 清除列表选择项
								 $('#bizVmdatagrid').jqxGrid('clearselection');
								 // 带着查询条件刷新列表
								 searchBizVm();
							  }
					  	});
				  }
			  });
	  }else if(rowindex.length > 1){
		Core.alert({
			  title:"提示",
			  message:'请只能选择一条记录！',
		});
	  }
  }
  
  // 退出监控
  function deleteVmMonitorNodeId(){
	  var rowindex = $('#bizVmdatagrid').jqxGrid('selectedrowindexes');
	  
	  var isok = $("#jqxDeleteMonitorBtn").jqxButton("disabled");
	  
	  if(rowindex.length == 1 && !isok){
		    var data = $('#bizVmdatagrid').jqxGrid('getrowdata', rowindex[0]);
		    
		    Core.confirm({
				title:"请选择",
				message:"您确定要删除该虚拟机的监控节点吗？",
				confirmCallback:function(){
					Core.AjaxRequest({
				  		url : ws_url + "/rest/vms/deleteNodeId/"+data.resBizVmSid,
				  		type:"get",
				  		async:false,
				  		callback : function(result) {
				  			 // 清除列表选择项
							 $('#bizVmdatagrid').jqxGrid('clearselection');
							 // 带着查询条件刷新列表
							 searchBizVm();
						  }
				  	});
				}
			});
			
	  }else if(rowindex.length > 1){
		Core.alert({
			  title:"提示",
			  message:'请只能选择一条记录！',
		});
	  }
  }
  