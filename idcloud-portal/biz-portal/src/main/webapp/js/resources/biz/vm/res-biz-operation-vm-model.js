 
  // 取消虚拟机的纳管 
  function popCancelVmWindow(){
		
		var rowindexex = $('#bizVmdatagrid').jqxGrid('getselectedrowindexes');
		resBizVmSidList = new Array();
		var ok =  $("#cancelVm").jqxButton("disabled");
		if(rowindexex.length > 0 && !ok){
  		    for (var i = 0; i < rowindexex.length; i++){
  		    	var data = $('#bizVmdatagrid').jqxGrid('getrowdata', rowindexex[i]);
  		    	data.createdDt = "";
  		    	data.updatedDt = "";
  		    	data.dredgeDate = "";
  		    	resBizVmSidList.push(data);
  		    }
  		    // 确定提交取消纳管的虚拟机
			Core.confirm({
				title:"提示",
				message:"您确定要取消纳管该虚拟机吗？",
				confirmCallback:function(){
					Core.AjaxRequest({
						url : ws_url + "/rest/resbiz/vm/unRelateResBizVm",
						type:'post',
						params :{
							orgSid : "",
							account : "",
							resBizVmList : resBizVmSidList
						},
						callback : function (data) {
							// 清除列表选择项
							$('#bizVmdatagrid').jqxGrid('clearselection');
							// 刷新列表
							searchBizVm();
					    },
					    failure:function(data){
					    	// 清除列表选择项
							$('#bizVmdatagrid').jqxGrid('clearselection');
							// 刷新列表
							searchBizVm();
					    }
					});
				}
		    });
     	}	    
	};
	
	// 设置虚拟机监控
    function popSetMonitorNodeWindow(){
		var rowindexex = $('#bizVmdatagrid').jqxGrid('getselectedrowindexes');
		
		resBizVmSidList = new Array();
		var ok =  $("#setMonitorNode").jqxButton("disabled");
		if(rowindexex.length > 0 && !ok){
  		    for (var i = 0; i < rowindexex.length; i++){
  		    	var data = $('#bizVmdatagrid').jqxGrid('getrowdata', rowindexex[i]);
  		    	if(data.vmIp != null || data.vmIp != ""){
  		    		Core.alert({
						title:"提示",
						message:" 选中的虚机IP有为空，不能设置监控！"
  		    		});
  		    		return;
  		    	}
  		    	
  		    	data.createdDt = "";
  		    	data.updatedDt = "";
  		    	data.dredgeDate = "";
  		    	resBizVmSidList.push(data);
  		    }
  		  
  		    // 设置虚拟机监控节点
			Core.confirm({
				title:"提示",
				message:"您确定要添加该虚拟机监控节点吗？",
				confirmCallback:function(){
					Core.AjaxRequest({
						url : ws_url + "/rest/resbiz/vm/setMonitorNode",
						type:'post',
						params :{
							orgSid : "",
							account : "",
							resBizVmList : resBizVmSidList
						},
						callback : function (data) {
							// 清除列表选择项
							$('#bizVmdatagrid').jqxGrid('clearselection');
							// 刷新列表
							searchBizVm();
					    },
					    failure:function(data){
					    	// 清除列表选择项
							$('#bizVmdatagrid').jqxGrid('clearselection');
							// 刷新列表
							searchBizVm();
					    }
					});
				}
		    });
  	    }	    
	};
	
	 //同步虚拟机
	  function asyncVmInfo(){
		    var rowindex = $('#bizVmdatagrid').jqxGrid('selectedrowindexes');
			var datas = new Array();
			var flag=false;
			if(rowindex.length >= 0){
					for(var i=0;i<rowindex.length;i++){
						var data = $('#bizVmdatagrid').jqxGrid('getrowdata', rowindex[i]);
							datas[i]=data;
					}
					Core.AjaxRequest({
				  		url : ws_url + "/rest/vms/synaVmInfo",
				  		type:"post",
				  		params:datas,
				  		async:true,
				  		showMsg:true,
				  		callback : function(result) {
				  			// 清除列表选择项
							$('#bizVmdatagrid').jqxGrid('clearselection');
							// 刷新列表
							searchBizVm();
						}
				  	});
			}
		  
	  }
	  
	  //批量开机
	  function startVm(){
		  var rowindex = $('#bizVmdatagrid').jqxGrid('selectedrowindexes');
			
			var datas = new Array();
			var flag=false;
			if(rowindex.length > 0){
				for(var i=0;i<rowindex.length;i++){
					var data = $('#bizVmdatagrid').jqxGrid('getrowdata', rowindex[i]);
					datas[i]=data;
					if("normal" == data.status){
						flag = true;
					}
				}
				if(!flag){
					Core.confirm({
						title:"请选择",
						message:"您确定要开启该虚拟机吗？",
						confirmCallback:function(){
							Core.AjaxRequest({
								  url : ws_url + "/rest/vms/multiop/start",
								  type:"post",
								  params:datas,
								  async:true,
								  callback : function(result) {
									  // 清除列表选择项
									  $('#bizVmdatagrid').jqxGrid('clearselection');
									  // 刷新列表
									  searchBizVm();
								  }
							  });
						}
					});
				}else{
					Core.alert({
						type : "error",
						message:"存在有已开机的虚拟机，不能启动！"
					});
				}
				
			}
	  }
	  
	  //批量关机
	  function stopVm(){
		  var rowindex = $('#bizVmdatagrid').jqxGrid('selectedrowindexes');
			var datas = new Array();
			var flag=false;
			if(rowindex.length > 0){
				for(var i=0;i<rowindex.length;i++){
					var data = $('#bizVmdatagrid').jqxGrid('getrowdata', rowindex[i]);
					datas[i]=data;
					if("poweredOff" == data.status){
						flag = true;
					}
				}
				if(!flag){
					Core.confirm({
						title:"请选择",
						message:"您确定要关闭这些虚拟机吗？",
						confirmCallback:function(){
							Core.AjaxRequest({
								  url : ws_url + "/rest/vms/multiop/stop",
								  type:"post",
								  params:datas,
								  async:true,
								  callback : function(result) {
									  // 清除列表选择项
									  $('#bizVmdatagrid').jqxGrid('clearselection');
									  // 刷新列表
									  searchBizVm();
								  }
							  });
						}
					});
				}else{
					Core.alert({
						type : "error",
						message:"存在有已关机的虚拟机，不能关机！"
					});
				}
				
			}
	  }
	  
	  //批量重启
	  function restartVm(){
		  var rowindex = $('#bizVmdatagrid').jqxGrid('selectedrowindexes');
			var msg = "";
			var datas = new Array();
			var flag=false;
			if(rowindex.length >= 0){
				for(var i=0;i<rowindex.length;i++){
					var data = $('#bizVmdatagrid').jqxGrid('getrowdata', rowindex[i]);
					datas[i]=data;
					if("poweredOff" == data.status){
						flag = true;
					}
				}
				if(!flag){
					Core.confirm({
						title:"请选择",
						message:"您确定要重启这些虚拟机吗？",
						confirmCallback:function(){
							Core.AjaxRequest({
								  url : ws_url + "/rest/vms/multiop/restart",
								  type:"post",
								  params:datas,
								  async:true,
								  callback : function(result) {
									  // 清除列表选择项
									  $('#bizVmdatagrid').jqxGrid('clearselection');
									  // 刷新列表
									  searchBizVm();
								  }
							  });
						}
					});
				}else{
					Core.alert({
						type : "error",
						message:"存在有已关机的虚拟机，不能重启！"
					});
				}
				
			}
	  }
		
	  //删除虚拟机
	  function deleteVm(){
		  var rowindex = $('#bizVmdatagrid').jqxGrid('selectedrowindexes');
			var msg = "";
			var datas = new Array();
			if(rowindex.length > 0){
				for(var i=0;i<rowindex.length;i++){
					var data = $('#bizVmdatagrid').jqxGrid('getrowdata', rowindex[i]);
					datas[i]=data;
				}
				 Core.confirm({
					 title:"请选择",
					 message:"您确定要退订这些虚拟机吗？",
					 confirmCallback:function(){
//						var data = $('#bizVmdatagrid').jqxGrid('getrowdata', rowindex);
						Core.AjaxRequest({
							  url : ws_url + "/rest/vms/multiop/destory",
							  type:"post",
							  params:datas,
							  async:true,
							  callback : function(result) {
								  // 清除列表选择项
								  $('#bizVmdatagrid').jqxGrid('clearselection');
								  // 刷新列表
								  searchBizVm();
							  }
						  });
					 }
				 });
		   }
	  };

	  //打开WebConsole
	  function webconsole(row){
		  var rowData = $('#bizVmdatagrid').jqxGrid('getrowdata', row);		  
		  var vmSid = rowData.resBizVmSid;
		  var vmName = rowData.vmName;
		  var status = rowData.status;
		  var resVmSid = vmSid+","+vmName
		  if('normal'!= status){
			  Core.confirm({
				  title:"提示",
				  message:'这台虚拟机不是正常状态，无法启用控制台！',
			  });
		  }else{
			open('POST', ctx+"/pages/webconsole/res-webconsole.jsp", resVmSid, '_blank');
		  }
		//  var oWin=window.open(ctx+"/pages/webconsole/res-webconsole.jsp?resVmSid="+resVmSid, "_blank");
		  
		  
	  }
	  
	  var open = function(verb, url, data, target) {
		  var form = document.createElement("form");
		  form.action = url;
		  form.method = verb;
		  form.target = target;
		  if (data) {
		      var input = document.createElement("textarea");
		      input.name = "vmrcconsole";
		      input.value = data;
		      form.appendChild(input);
		  }
		  form.style.display = 'none';
		  document.body.appendChild(form);
		  form.submit();
		};
		  
	  //调用WebConsole
//	  function openWebConsole(row){
//		  var rowData = $('#bizVmdatagrid').jqxGrid('getrowdata', row);
//			  var resVmSid = rowData.resBizVmSid;
//			  var host = window.location.hostname;
//			  var hostSid = rowData.allocateResHostSid;
//			  Core.AjaxRequest({
//				  url : ws_url + "/rest/vms/findVmVncSwitch/"+hostSid,
//				  type:"get",
//				  callback : function(result) {
//					  if(!result){
//						  Core.confirms({
//							  title:"提示",
//							  message:"这台虚拟机所属资源环境没有开启VNC服务！"+'<p style="margin-top: -40px;">'+"不能开启控制台。"+'</p>',
//						  });
//					  }else{
//						  Core.AjaxRequest({
//							  url : ws_url + "/rest/vms/findVmVncPorts/"+resVmSid,
//							  type:"get",
//							  callback : function(result) {
//								  if(result!="" && null!=result){
//									  Core.AjaxRequest({
//										  url : ws_url + "/rest/vms/openWebConsole",
//										  type:"post",
//										  params:{
//												resVmSid:resVmSid,
//												host:host
//										  },
//										  async:false,
//										  callback : function(result) {
//											  
//											  var oWin=window.open(result.url, {replace:true});
//											  oWin.document.title=rowData.vmName;
//										  }
//									  }); 
//								  }else{
//									  Core.confirms({
//										  title:"提示",
//										  message:"这台虚拟机没有配置VNC服务端口！"+'<p style="margin-top: -40px;">'+"不能开启控制台。"+'</p>',
//									  });
//								  }
//							  }
//						  }); 
//					  }
//				  }
//			  });
//	  }
	  
	  //switch VNC
//	  function switchVNCBtn(row){
//		  var rowData = $('#bizVmdatagrid').jqxGrid('getrowdata', row);
//			  var resVmSid = rowData.resBizVmSid;
//			  var allocateResHostSid = rowData.allocateResHostSid;
//			  var hostSid = rowData.allocateResHostSid;
//			  Core.AjaxRequest({
//				  url : ws_url + "/rest/vms/findVmVncSwitch/"+hostSid,
//				  type:"get",
//				  callback : function(result) {
//					  if(!result){
//						  Core.confirms({
//							  title:"提示",
//							  message:"这台虚拟机所属资源环境没有开启VNC服务！"+'<p style="margin-top: -40px;">'+"不能开启/关闭VNC端口。"+'</p>',
//						  });
//					  }else{
//						  //判断同ve环境下的某主机vnc端口是否已经达到上限
//						  Core.AjaxRequest({
//							  url : ws_url + "/rest/vms/findCountVmVncPort/"+allocateResHostSid,
//							  type:"get",
//							  callback : function(result) {
//								  //如果没有达到上限则添加，反之不添加退出
//								  if(result){
//									  Core.AjaxRequest({
//										  url : ws_url + "/rest/vms/findVmVncPorts/"+resVmSid,
//										  type:"get",
//										  callback : function(result) {
//											  if(result!="" && null!=result){
//												  var myBoolean=new Boolean(false);
//												  Core.confirms({
//													  title:"提示",
//													  message:"该虚拟机已配置VNC服务,要<B>关闭</B>它吗？关闭VNC端口需要重启"+'<p style="margin-top: -40px;">'+"您的虚拟机，请先保存好数据！需要关闭VNC服务请点击<B>确定</B>！"+'</p>',
//													  confirmCallback : function() {
//														  Core.AjaxRequest({
//															  url : ws_url + "/rest/vms/opencloseVNC/"+resVmSid+"/"+myBoolean,
//														  	  type:"get",
//														  	  callback : function(result) {
//														  	}	
//														  });
//													  }
//												  });
//											  }else{
//												  var myBoolean=new Boolean(true);
//												  Core.confirms({
//													  title:"提示",
//													  message:"配置VNC服务端口需要<B>重启</B>您的虚拟机，请先保存好您的数据！"+'<p style="margin-top: -40px;">'+"配置服务点击<B>确定</B>，关闭点击<B>取消</B>！"+'</p>',
//													  confirmCallback : function() {
//														  Core.AjaxRequest({
//															  url : ws_url + "/rest/vms/opencloseVNC/"+resVmSid+"/"+myBoolean,
//														  	  type:"get",
//														  	  callback : function(result) {
//														  		  
//														  	}	
//														  });
//													  }
//												  });
//											  }
//										  }
//									  }); 
//								  }else{
//									  var myBoolean=new Boolean(false);
//									  Core.AjaxRequest({
//										  url : ws_url + "/rest/vms/findVmVncPorts/"+resVmSid,
//										  type:"get",
//										  callback : function(result) {
//											  if(result!="" && null!=result){
//												  Core.confirms({
//													  title:"提示",
//													  message:"该资源环境下的VNC端口已达上限！"+'<p style="margin-top: -40px;">'+"要关闭该虚机VNC端口吗？"+'</p>',
//													  confirmCallback : function() {
//														  Core.AjaxRequest({
//															  url : ws_url + "/rest/vms/opencloseVNC/"+resVmSid+"/"+myBoolean,
//														  	  type:"get",
//														  	  callback : function(result) {
//														  	}	
//														  });
//													  }
//												  });
//											  }else{
//												  Core.confirms({
//													  title:"提示",
//													  message:"该资源环境下的VNC端口已达上限！"+'<p style="margin-top: -40px;">'+"不能开启。"+'</p>',
//												  });
//											  }
//										  }
//									  });
//								  }
//							  }
//						  });
//					  }
//				  } 
//			  });
//	  };
