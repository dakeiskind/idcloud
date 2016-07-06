var fileNameSids = "";
var fileNameProSids = "";
var uapproveInfoModelInstance = null;
var approveBindModel = function (unapp,apped) {
	var me = this;
    this.items = ko.observableArray();
	/*******************待审核******************/
    /** 审核详情(待审核) */
    this.openUnapproveDetails = function(){
    	var rowindex = $('#unapprovedatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		     //ChengQi start
    		     var data = $('#unapprovedatagrid').jqxGrid('getrowdata', rowindex);
    		     if(!data.processType) return;
    		     if(data.processType == '01') {
    		    	 //云主机开通审批流程
    		    	 me.showOrderApproveDetailPopWin(data);
    		     } else if(data.processType == '11') {
    		    	//闲置资源回收审批流程
    		    	me.showFreeResApproveDetailPopWin(data);
    		     } else if(data.processType == '12') {
      		    	//实例变更审批流程
      		    	me.showInstanceApproveDetailPopWin(data);
      		     } else if(data.processType == '02') {
      		    	//实例退订审批流程
      		    	me.showCancelApproveDetailPopWin();
      		     }
    	}
    }

    /** 审核详情(已审核) */
    this.openapproveDetails = function(){
    	var rowindex = $('#approveddatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#approveddatagrid').jqxGrid('getrowdata', rowindex);
    		if(!data.processType) return;
//    		var winSize = {};
    		if(data.processType == '01') {
    			//云主机开通审核详情弹出框初始化
		    	 me.showOrderApprovedDetailPopWin(data);
//		    	 winSize = {width:750, height:610};
		     } else if(data.processType == '12') {
  		    	//云主机服务变更审核详情弹出框初始化
  		    	me.showInstanceApprovedDetailPopWin(data);
//  		    	winSize = {width:700, height:585};
  		     }  else if(data.processType == '02') {
   		    	//云主机服务退订审核详情弹出框初始化
   		    	me.showCancelApprovedDetailPopWin(data);
//   		    	winSize = {width:700, height:585};
   		     } 
    		//初始化审核历史Tab页数据
		    me.openapproveHistory();
//		    var windowW = $(window).width();
//			var windowH = $(window).height();
//			winSize.position = {
//					x : (windowW - winSize.width) / 2,
//					y : (windowH - winSize.height) / 2
//			};
//			$("#jqxapprovedwindow").jqxWindow(winSize);
//			$("#jqxapprovedwindow").jqxWindow('open');
    	}
    };
	
	/** 管理员审核显示审核弹出窗口 */
	this.openUnapproveMgt = function(){
		var rowindex = $('#unapprovedatagrid').jqxGrid('getselectedrowindex');
		if(rowindex >= 0){
			var data = $('#unapprovedatagrid').jqxGrid('getrowdata', rowindex);
			if(!data.processType) return;
//			var winSize = {};
			if(data.processType == '01') {
				//初始化云主机开通管理员审核弹出框
				me.showOrderApprovePopWin(data);
//				winSize = {width:750, height:645};
			} else if(data.processType == '12') {
				//初始化云主机服务变更管理员审核弹出框
				me.showInstanceApprovePopWin(data);
//				winSize = {width:700, height:585};
			} else if(data.processType == '02') {
				//初始化云主机服务退订管理员审核弹出框
				me.showCancelApprovePopWin(data);
//				winSize = {width:700, height:585};
			}
			//初始化审核历史Tab页数据
			me.openUnapproveHistory();
//			var windowW = $(window).width();
//			var windowH = $(window).height();
//			winSize.position = {
//					x : (windowW - winSize.width) / 2,
//					y : (windowH - winSize.height) / 2
//			};
//			$("#tabwindow").jqxWindow(winSize);
//			$("#tabwindow").jqxWindow('open');
		}
	};


	
	this.saveInstanceSpecEdit = function () {
		var validateResult = $('#instanceSpecEditForm').jqxValidator('validate');
		if(!validateResult) {
			return;
		}
		var orderId = $("#orderId").val();
		var rowindex =  $('#unapprove-orderDetailsGrid').jqxGrid('getselectedrowindex');
   		if(rowindex >= 0){
   			var rowData = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', rowindex);
   			if(rowData) {
           		Core.AjaxRequest({
     				type : 'post',
     				url : ws_url + "/rest/serviceInstances/modify/instanceSpecs",
     				showMsg:true,
     				params: {
     					"orderId": orderId,
     					"instanceSid": rowData.instanceSid,
     					"instanceName": $("#instanceSpecName").val(),
     					"resInstanceName": $("#resInstanceSpecName").val(),
     					"CPU": $("#instanceSpecCpu").val(),
     					"MEMORY": $("#instanceSpecMemory").val(),
     					//"OS": $("#instanceSpecOs").val(),
     					//"NEED_WAN": $("#instanceSpecWan").val(),
     					//"NEED_LAN": $("#instanceSpecLan").val(),
     					"updateAllFlag": $("#updateAllFlag").is(":checked") ? "1": "0"
     				},
     				callback : function(data) {
     					Core.AjaxRequest({
		     				type : 'get',
		     				url : ws_url + "/rest/order/getInstSpecForDataDisk/" + rowData.instanceSid,
		     				callback : function(data) {
		     					var rowId = $('#unapprove-orderDetailsGrid').jqxGrid('getrowid', rowindex);
		     					rowData['instanceName'] = $("#instanceSpecName").val();
		     					rowData['resInstName'] = $("#resInstanceSpecName").val();
		     					$('#unapprove-orderDetailsGrid').jqxGrid('updaterow', rowId, rowData);
		     					
		     					var sourcedatagrid ={
		     			              datatype: "json",
		     			              localdata: data
		     				    };
		     				    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		     				    $("#unapprove-serviceSpecGrid").jqxGrid({source: dataAdapter});
		     				    
//		     				    var unapp = new unapproveModel();
//		     		   			unapp.isFinal = true;
//		     		   			unapp.instanceSid = rowData.instanceSid;
//		     		   			unapp.targetServiceInstanceIndex = rowindex;
		     		   			uapproveInfoModelInstance.checkIntanceAllSelect();
		     				}
		     			});
     					$("#instanceSpecEditWindow").jqxWindow('close');
     				}
     			});
   			}
   		}
	};

	/** 加载云主机开通审核窗口**/
	this.showOrderApprovePopWin = function (data) {
		uapproveInfoModelInstance = unapp;
		/*
		if('运维管理员终审' == data.approvorAction) {
			$("#unapprove-serviceSpecGrid").jqxGrid({toolbarheight: 34});
		} else {
			$("#unapprove-serviceSpecGrid").jqxGrid({toolbarheight: 0});
		}
		*/
		//$("#unapprove-serviceSpecGrid").jqxGrid({toolbarheight: 0});
		//设置云主机开通审核明细内容显示
		$('#unapprove-orderDetailsGrid').jqxGrid('clearselection');

		$('#approveMgtForm').show();
		if(data.approvorAction =="运维管理员审核"){
			$("#approveIsNeedPrincipal").show();
		}else{
			$("#approveIsNeedPrincipal").hide();
		}
		//设置云主机服务变更审核明细内容隐藏
		$('#instance-approveForm').hide();
		$('#cancel-approveForm').hide();
//		$("#approveIsNeedPrincipal").hide();
//		$('#approveStatus').val("01");
		$('#checkQuotaMsg').html('');
		$('#bizResourceTbl').css('display', 'none');
		
		//清除资源选中内容
		unapp.clearResSelectData();
			Core.AjaxRequest({
					type : 'get',
					url : ws_url + "/rest/order/getApproveOrder?orderId=" + data.approveObject + "&processInstanceId=" + data.processInstanceId,
					callback : function(data) {
		    		    $("#unapprove-orderStatus").html(data.orderDetail[0].statusName);
		    		    $("#unapprove-mgtObjName").html(data.tenantName);
		    		    $("#unapprove-owner").html(data.ownerId);
	   				    
	   				    //初始化所有实例的资源选择配置
		       	        var orderInstances = data.vmServiceInstances;
		       	        for(var i = 0; i < orderInstances.length; i++) {
		       	        	var instanceResSelect = orderInstances[i];
		       	        	var ve = '';
		       	        	var needWan = false;
		       	        	var needLan = false;
		       	        	orderInstances[i]['resSelStatus'] = '0';
		       	        	Core.AjaxRequest({
			     				type : 'get',
			     				url : ws_url + "/rest/order/getInstSpec/" + instanceResSelect.instanceSid,
			     				async: false,
			     				callback : function(data2) {
				 		 	        for(var i=0;i<data2.length;i++){
				 		 	        	if(data2[i].name=='OS'){
				 		 	        		if(data2[i].value!=null&&data2[i].value!="") {
				 		 	        			ve = data2[i].ve;
				 		 	        		}
				 		 	        	}
				 		 	        	if(data2[i].name=='NEED_WAN'){
				 		 	        		if(data2[i].value!=null&&data2[i].value!=""){
				 		 	        			if(data2[i].value=="1"){
				 		 	        				needWan = true;
				 		 	        			}
				 		 	        		}
				 		 	        	}
				 		 	        	if(data2[i].name=='NEED_LAN'){
				 		 	        		if(data2[i].value!=null&&data2[i].value!=""){
				 		 	        			if(data2[i].value=="1"){
				 		 	   		 	        	needLan = true;
				 		 	        			}
				 		 	        		}
				 		 	        	}
				 		 	        }
			     				}
			     			});
		       	        	
		       	        	unapp.resSelectData.push({
		       	        		'instanceSid': instanceResSelect.instanceSid,
		       	        		'rescomuteid': '',
		       	        		'rescomuteType': '',
		       	        		'vLanIDO': '',
		       	        		'vLanIDI': '',
		       	        		'wanIp': '',
		       	        		'lanIp': '',
		       	        		'virtualSwitch': '',
		       	        		'cpuResPool': '',
		       	        		'rootHbaCard': '',
		       	        		'networkHbaCard':'',
		       	        		'stHbaCard' : '',
		       	        		'partitionType': '1',
		       	        		'allocateType': '1',
		       	        		'aixParSid': '',
		       	        		've': ve,
		       	        		'mgtObjSid': instanceResSelect.mgtObjSid,
		       	        		'needWan': needWan,
		       	        		'needLan': needLan,
		       	        		'resPoolType': '',
		       	        		'resType': ve === 'VMware' ? '1': ''
		       	        	});
		       	        }

		 			  if(data.isFinal) {
					      // 初始化订单明细列表
					      $("#unapprove-orderDetailsGrid").jqxGrid({
					             width: "100%",
					             height:"140px",
					             theme:currentTheme,
					             columnsresize: true,
					             sortable: true,
					             localization: gridLocalizationObj,
					             columns: [
					                 { text: '操作系统名称', datafield: 'instanceName', width: 150},
//					                 { text: '实例类型', datafield: 'serviceName'},
					                 { text: '主机名称', datafield: 'resInstName'},
					                 { text: '操作系统', datafield: 'osVersionName', width: 250},
					                 { text: '资源选择状态', datafield: 'resSelStatus',align: 'center',cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
										   var statusHtml =  "<div style='width:100px;height:22px;line-height:25px;'>&nbsp;&nbsp;";
										   if(value == '1') {
											   statusHtml += "<i class='icons-blue icon-ok-3'></i>";
										   } else {
											   statusHtml +="<i class='icons-red icon-cancel-4'></i>";
										   }
										   statusHtml += "</div>";
										   return statusHtml;
									  	},width:80
					                  },
					                  { text: '操作', datafield: 'operate', cellclassname:'operateCol', columntype: 'custom', width:60, cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
					                	   //return "<div class='customButton' style='margin-top:2px;margin-left:10px;float:left'  data-bind='click:showInstanceSpecEditWindow'>编辑</div>";
					                	  return "<div class='customButton' style='margin-top:2px;float:left'  onclick='showInstanceSpecEditWindow("+row+")'>编辑</div>";
//					                	  return "<div class='customButton' style='margin-top:2px;margin-left:10px;float:left' >编辑</div>";
//					                	  $(editHtml).bind('click', showInstanceSpecEditWindow);
//					                	  return editHtml;
//					                	  return "<a style='margin-top:2px;margin-left:10px;float:left' href='javascript:showInstanceSpecEditWindow("+row+")'>编辑</a>";
					                	  }
					                  }
					             ],
					             showtoolbar: true,
					           // 设置toolbar操作按钮
					           rendertoolbar: function (toolbar) {
					               var container = $("<div id='explain' style='width:100%;height:30px;line-height:35px;'></div>");
					               var orderDetail = $("<font>&nbsp;&nbsp;订单明细列表</font>");
					               toolbar.append(container); 
					               container.append(orderDetail);
					           }
					     });
					      
		 				 var resData = unapp.getResData(data.vmServiceInstances[0].instanceSid);
//		 				 var app = new codeModel({width:220, dropDownWidth:220,autoDropDownHeight:false, dropDownHeight:180});
		 				 $('#resVe').val(resData.ve);
		 				unapp.getCustomCode('partitionType', '/system/PARTITION_TYPE' , 'codeDisplay', 'codeValue', false, 'GET', null);
//		 				unapp.getCustomCode('allocateType', '/system/ALLOCATE_TYPE' , 'codeDisplay', 'codeValue', false, 'GET', null);
//		 				$("#allocateType").jqxDropDownList('selectIndex', 0 );
		 				$("#allocateType-create").jqxRadioButton({ width: 80, height: 25,groupName :"allocateType", checked: true,theme:"metro"});
		 				$("#allocateType-nanotube").jqxRadioButton({ width: 80, height: 25,groupName :"allocateType",theme:"metro"});
	     				 if(resData.needWan === true) {
	 	        			unapp.getCustomCode('vLanIDO', '/biz/getSubmitUserResources', 'resSetType', 'resSetSid', false, 'POST', {'resType': 1, 'orderId': data.orderId});
	 	        			unapp.initIpAddressDropDownList('wanIps', $('#vLanIDO').val());
	     				 }
	     				 if(resData.needLan === true) {
	     					unapp.initDropDownList('vLanIDI', 'networkName', 'resNetworkSid');
	     					unapp.setAutoAllocate('vLanIDI');
	     					//me.getCustomCode('vLanIDI', '/approve/lans', 'networkName', 'resNetworkSid', false, 'POST', {'hostSid': value.split(",")[0]});
	 	   		 	        unapp.initIpAddressDropDownList('lanIps', $('#vLanIDI').val());
	     				 }
	 		 	        //当终审时， 设置业务资源修改是为显示
	 		 	        $('#bizResourceTbl').css('display', '');
	 		 	        $('#bizResourceTbl .jqx-tabs-titleContentWrapper').css('margin-top','0px');
	 		 	        $('#vmResCheck').show();
	 		 	         unapp.isFinal = true;
			 		  } else {
			 			// 初始化订单明细列表
					      $("#unapprove-orderDetailsGrid").jqxGrid({
					             width: "100%",
					             height:"140px",
					             theme:currentTheme,
					             columnsresize: true,
					             sortable: true,
					             localization: gridLocalizationObj,
					             columns: [
					                 { text: '操作系统名称', datafield: 'instanceName', width: 150},
//					                 { text: '实例类型', datafield: 'serviceName'},
					                 { text: '主机名称', datafield: 'resInstName'},
					                 { text: '操作系统', datafield: 'osVersionName', width: 250}
					             ],
					             showtoolbar: true,
					           // 设置toolbar操作按钮
					           rendertoolbar: function (toolbar) {
					               var container = $("<div id='explain' style='width:100%;height:30px;line-height:35px;'></div>");
					               var orderDetail = $("<font>&nbsp;&nbsp;订单明细列表</font>");
					               toolbar.append(container); 
					               container.append(orderDetail);
					           }
					     });

			 			 unapp.isFinal = false;
			 			 $('#unapprove-orderDetailsGrid').jqxGrid('hidecolumn', 'resSelStatus');
		 				 $('#vmResCheck').hide(); 
		 				 $("#approveMgtSave").jqxButton({disabled: false});
		 			  }
		 			
		 			var sourcedatagrid ={
			              datatype: "json",
			              localdata: data.vmServiceInstances
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	 			    $("#unapprove-orderDetailsGrid").jqxGrid({source: dataAdapter});
		 			var winSize = {};
				    winSize = {width:750, height:645};
				    var windowW = $(window).width();
	    			var windowH = $(window).height();
	    			winSize.position = {
	    					x : (windowW - winSize.width) / 2,
	    					y : (windowH - winSize.height) / 2
	    			};
	    			$("#tabwindow").jqxWindow(winSize);
	    			$("#tabwindow").jqxWindow('open');
//	    			$("#approveStatus").trigger('change');
					}
				});

	      Core.AjaxRequest({
				type : 'get',
				url : ws_url + "/rest/order/checkQuota/" + data.approveObject,
				callback : function(data) {
					if(data && data.checkResult === false) {
	      				$('#checkQuotaMsg').html("※ " + data.checkMessage + "超过配额限制！");
		    			return;
					}
					
				}
	      });
		  
		    $("#unapprove-orderNumber").html(data.approveObject);
			$("#unapprove-ownerTenant").html(data.tenantName);
			
			$("#unapprove-openTime").html(data.createdDt);
//			$('#approveStatus').val("01");
		    //审核隐藏变量
	    	$("#processInstanceId").val(data.processInstanceId);
	    	$("#approvorAction").val(data.approvorAction);
	    	$("#processType").val(data.processType);
		    $("#tabwindow").jqxWindow({title: data.processTypeName+"("+data.approvorActionName+")"}); 
		    $("#approveMgtWindow .title1").html(data.processTypeName);
		    $('#unapprove-instance-tab').jqxTabs('select', 0);
		    $('#tab2').jqxTabs('select', 0);
		    $("#content").html("<font style='font-size:12px;font-weight:300'>流程类型:"+data.processTypeName+",订单编号:"+data.approveObject+",服务名称:"+data.serviceName+",租户:"+data.tenantName+",开通时间:"+data.createdDt+"</font>");
	};

	
	/** 云主机开通审核详情弹出窗口(已审核) */
	this.showOrderApprovedDetailPopWin = function (data) {
			$('#approvedDetailForm').show();
			$('#instance-approvedForm').hide();
			$('#cancel-approvedForm').hide();
			Core.AjaxRequest({
					type : 'get',
					url : ws_url + "/rest/order/getApproveOrder?orderId=" + data.approveObject + "&processInstanceId=" + data.processInstanceId,
					callback : function(data) {
		    		    $("#orderStatus").html(data.orderDetail[0].statusName);
		    		    $("#mgtObjName").html(data.tenantName);
		    		    $("#owner").html(data.ownerId);
		 				var sourcedatagrid ={
				              datatype: "json",
				              localdata: data.vmServiceInstances
		 			    };
		 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		 			    $("#orderDetailsGrid").jqxGrid({source: dataAdapter});
		 			    
		 			    var winSize = {};
					    winSize = {width:750, height:610};
					    var windowW = $(window).width();
		    			var windowH = $(window).height();
		    			winSize.position = {
		    					x : (windowW - winSize.width) / 2,
		    					y : (windowH - winSize.height) / 2
		    			};
		    			$("#jqxapprovedwindow").jqxWindow(winSize);
		    			$("#jqxapprovedwindow").jqxWindow('open');
		 			    
					}
				});
		    // 初始化订单明细列表
	    	 $("#orderDetailsGrid").jqxGrid({
	             width: "100%",
	             height:"140px",
	             theme:currentTheme,
	             columnsresize: true,
	             sortable: true,
	             localization: gridLocalizationObj,
	             columns: [
	                 { text: '操作系统名称', datafield: 'instanceName', width: 150},
	                 { text: '主机名称', datafield: 'resInstName'},
	                 { text: '操作系统', datafield: 'osVersionName', width: 250},
	                 { text: '申请时间', datafield: 'createdDt'},
	             ],
	             showtoolbar: true,
	           // 设置toolbar操作按钮
	           rendertoolbar: function (toolbar) {
	               var container = $("<div id='explain' style='width:100%;height:30px;line-height:35px;'></div>");
	               var orderDetail = $("<font>&nbsp;&nbsp;订单明细列表</font>");
	               toolbar.append(container); 
	               container.append(orderDetail);
	           }
	         });
	    	// 初始化规格
	    	$("#serviceSpecGrid").jqxGrid({
	             width: "100%",
	             height:"260px",
	             theme:currentTheme,
	             columnsresize: true,
	             pageable: false, 
	             sortable: true,
	             localization: gridLocalizationObj,
	             columns: [
	                 { text: '规格名称', datafield: 'description',width:100},
	                 { text: '规格值', datafield: 'valueText'}
	             ],
	             showtoolbar: false,
	           // 设置toolbar操作按钮
	           rendertoolbar: function (toolbar) {
	               var container = $("<div id='explain' style='width:100%;height:30px;line-height:35px;'></div>");
	               var orderDetail = $("<font>&nbsp;&nbsp;规格明细列表</font>");
	               toolbar.append(container); 
	               container.append(orderDetail);
	           }
	         });
		  
	        $("#orderNumber").html(data.approveObject);
			  $("#ownerTenant").html(data.tenantName);
			  $("#openTime").html(data.createdDt);
			 
		    $("#jqxapprovedwindow").jqxWindow({title: data.processTypeName+"("+data.approvorActionName+")"}); 

	};

	/** 云主机服务变更审核弹出窗口 */
	this.showInstanceApprovePopWin = function (rowData) {
		//设置云主机开通审核明细内容隐藏
		$('#approveMgtForm').hide();
		$('#cancel-approveForm').hide();
		//设置云主机服务变更审核明细内容显示
		$('#instance-approveForm').show();
		//设置隐藏变量
		$("#instance-instanceSid").val(rowData.processObjectId);
		$("#instance-processInstanceId").val(rowData.processInstanceId);
		$("#instance-approvorAction").val(rowData.approvorAction);
		$("#instance-processType").val(rowData.processType);
		$("#tabwindow").jqxWindow({title: rowData.processTypeName+"("+rowData.approvorActionName+")"}); 
//		$('#instance-approveStatus').val("01");
		$('#tab2').jqxTabs('select', 0);
	   
		$("#instance-isNeedPrincipal").hide();
		 Core.AjaxRequest({
				type : 'get',
				url : ws_url + "/rest/approve/platform/approve/adjustinstanceDetail/" + rowData.processObjectId + "/" + rowData.processInstanceId,
				callback : function(data) {
					//设置实例信息
					$('#instance-instanceName').html(data.instanceName);
					$('#instance-owner').html(data.ownerId);
					$('#instance-mgtObjName').html(data.mgtObjName);
					$('#instance-mgtObjSid').html(data.mgtObjSid);

				    
				    var diskSpecs = data.diskSpecs;
				    if(diskSpecs && diskSpecs.length > 0) {
						var diskdataAdapter = new $.jqx.dataAdapter(diskSpecs);
						diskdataAdapter.dataBind();
					    $("#instance-diskSpecGrid").jqxGrid({source: diskdataAdapter});
				    }else{
				    	var diskdataAdapter = new $.jqx.dataAdapter(null);
						diskdataAdapter.dataBind();
				    	$("#instance-diskSpecGrid").jqxGrid({source: diskdataAdapter});
				    }
				    
//				    var netSpecs = data.netSpecs;
//					var netdataAdapter = new $.jqx.dataAdapter(netSpecs);
//					netdataAdapter.dataBind();
//				    $("#instance-netSpecGrid").jqxGrid({source: netdataAdapter});
//				    me.initApproveNetsTable(data.netSpecs);
				    
//				    $("#wanList1").css("display","none");
//   				$("#lanList1").css("display","none");
				    if(data.isFinal) { 
				    	//当终审时， 设置业务资源修改是为显示
	 		 	        $('#bizResourceTbl2').css('display', '');
	 		 	        $('#instance-changeCheck').show();
//				    	var app = new codeModel({width:150, dropDownWidth:150,autoDropDownHeight:true});
//				    	app.getCustomCode('rescomuteid2', '/biz/getSubmitUserResources', 'resSetType', 'resSetSid', false, 'POST', {'resType': 0, 'orderId': data.orderId});
//				    	for(var i=0;i<specs.length;i++){
//				    		if(specs[i].name=='NEED_WAN'){
//				    			if(specs[i].newValue!=null&&specs[i].newValue!=""){
//				    				if(specs[i].newValue=="需要"){
//				    					app = new codeModel({width:150, dropDownWidth:150, autoDropDownHeight:true});
//				    					app.getCustomCode('vLanIDO2', '/biz/getSubmitUserResources', 'resSetType', 'resSetSid', false, 'POST', {'resType': 1, 'ownerId': data.ownerId});
//				    					$("#wanList1").css("display","block");
//				    				}
//				    			}
//				    		}
//				    		if(specs[i].name=='NEED_LAN'){
//				    			if(specs[i].newValue!=null&&specs[i].newValue!=""){
//				    				if(specs[i].newValue=="需要"){
//				    					app = new codeModel({width:150, dropDownWidth:150, autoDropDownHeight:true});
//				    					app.getCustomCode('vLanIDI2', '/biz/getSubmitUserResources', 'resSetType', 'resSetSid', false, 'POST', {'resType': 2, 'ownerId': data.ownerId});
//				    					$("#lanList1").css("display","block");
//				    				}
//				    			}
//				    		}
//				    	}
	 		 	        
	 		 	        $("#instance-specGrid").jqxGrid(
	 							{
	 					             width: "680px",
	 					             height: "227px",
	 								 theme:currentTheme,
	 					             columnsresize: true,
	 					             editable: true,
	 					             selectionmode: 'singlerow',
	 					             editmode: 'multiplecellsadvanced',
	 					             localization: gridLocalizationObj,
	 					             columns: [
	 					 	             { text: '规格项', datafield: 'description', editable: false},
	 					 	             { text: '当前值', datafield: 'value', width:215, editable: false},
	 					 	             { text: '调整后值', datafield: 'newValue', width:215, editable: true, columntype: 'custom',
	 					 	            	createeditor: function (row, cellvalue, editor, cellText, width, height) {
	 											var rowData = this.owner.getrowdatabyid(row);
	 											if(rowData.name === 'POWER_CPU_USED_UNIT'
	 												|| rowData.name === 'POWER_CPU_MIN_UNIT'
	 												|| rowData.name === 'POWER_CPU_MAX_UNIT'){
	 												editor.jqxNumberInput({ min: 0, decimalDigits: 1, inputMode: 'simple',  width: width, height: height, spinButtons: true, readOnly: true });
	 											} else if(rowData.name === 'MEMORY_MAX'
	 												|| rowData.name === 'MEMORY_MIN'
	 												|| rowData.name === 'CPU_MIN'
	 												|| rowData.name === 'CPU_MAX') {
	 												editor.jqxNumberInput({ min: 0, decimalDigits: 0, inputMode: 'simple',  width: width, height: height, spinButtons: true});
	 											}else if(rowData.name === 'MEMORY' ) {
	 												editor.jqxDropDownList({autoDropDownHeight: true,
	 													placeHolder: "请选择...",
	 													source: ['2', '4', '8', '16', '32', '64']
	 												});
	 											} else if(rowData.name === 'CPU') {
	 												editor.jqxDropDownList({autoDropDownHeight: true,
	 													placeHolder: "请选择...",
	 													source: ['1', '2', '4', '8', '16']
	 												});
	 											}
	 										},
	 										initeditor : function (row, cellValue, editor, cellText, width, height) {
	 											var rowData = this.owner.getrowdatabyid(row);
	 											if(rowData.name === 'POWER_CPU_USED_UNIT' || rowData.name === 'POWER_CPU_MIN_UNIT'|| rowData.name === 'POWER_CPU_MAX_UNIT'
	 												|| rowData.name === 'MEMORY_MIN' || rowData.name === 'MEMORY_MAX' || rowData.name === 'CPU_MIN' || rowData.name === 'CPU_MAX'){
	 												editor.jqxNumberInput().val(cellValue);
	 											} else if(rowData.name === 'MEMORY' || rowData.name === 'CPU') {
	 												editor.jqxDropDownList('selectItem', cellValue);
	 											}
	 										},
	 										validation: function (cell, value) {
	 											var rowData = this.owner.getrowdatabyid(cell.row);
	 											if(rowData.name === 'MEMORY_MIN') {
	 												var memory = unapp.getSpecData(this.owner.getrows(), 'MEMORY', 'newValue');
	 												var memoryDesc = unapp.getSpecData(this.owner.getrows(), 'MEMORY', 'description');
	 												if(memory < value) {
	 													return { result: false, message: rowData.description + "不能大于当前"+ memoryDesc +"。" };
	 												}
	 											}
	 											if(rowData.name === 'MEMORY_MAX') {
	 												var memory = unapp.getSpecData(this.owner.getrows(), 'MEMORY', 'newValue');
	 												var memoryDesc = unapp.getSpecData(this.owner.getrows(), 'MEMORY', 'description');
	 												if(memory > value) {
	 													return { result: false, message: rowData.description + "不能小于当前"+ memoryDesc +"。" };
	 												}
	 											}
	 											if(rowData.name === 'CPU_MIN') {
	 												var cpu = unapp.getSpecData(this.owner.getrows(), 'CPU', 'newValue');
	 												var cpuDesc = unapp.getSpecData(this.owner.getrows(), 'CPU', 'description');
	 												if(cpu < value) {
	 													return { result: false, message: rowData.description + "不能大于当前"+ cpuDesc +"。" };
	 												}
	 											}
	 											if(rowData.name === 'CPU_MAX') {
	 												var cpu = unapp.getSpecData(this.owner.getrows(), 'CPU', 'newValue');
	 												var cpuDesc = unapp.getSpecData(this.owner.getrows(), 'CPU', 'description');
	 												if(cpu > value) {
	 													return { result: false, message: rowData.description + "不能小于当前"+ cpuDesc +"。" };
	 												}
	 											}
	 											return true;
	 										}	
	 					 	             }
	 					             ],
	 					             showtoolbar: true,
	 					             toolbarheight:26,
	 				   	              // 设置toolbar操作按钮
	 				   	              rendertoolbar: function (toolbar) {
	 				   	            	  var container = $("<div style='width:100%;height:25px;line-height:25px;'></div>");
	 				   	                  var orderDetail = $("<font>&nbsp;&nbsp;基本规格变更列表</font>");
	 				   	                  toolbar.append(container); 
	 				   	                  container.append(orderDetail);
	 				   	              }
	 					 });
				    }	
				    else {
				    	 $("#instance-specGrid").jqxGrid({
				             width: "680px",
				             height: "227px",
							 theme:currentTheme,
				             columnsresize: true,
				             editable: true,
				             selectionmode: 'singlerow',
				             editmode: 'multiplecellsadvanced',
				             localization: gridLocalizationObj,
				             columns: [
				 	             { text: '规格项', datafield: 'description', editable: false},
				 	             { text: '当前值', datafield: 'value', width:215, editable: false},
				 	             { text: '调整后值', datafield: 'newValue', width:215, editable: false}
				             ],
				             showtoolbar: true,
				             toolbarheight:26,
			   	              // 设置toolbar操作按钮
			   	             rendertoolbar: function (toolbar) {
			   	            	  var container = $("<div style='width:100%;height:25px;line-height:25px;'></div>");
			   	                  var orderDetail = $("<font>&nbsp;&nbsp;基本规格变更列表</font>");
			   	                  toolbar.append(container); 
			   	                  container.append(orderDetail);
			   	             }
		 				});

				    	$('#instance-changeCheck').hide();
				    }

					var specs = data.specs;
					var griddataAdapter = new $.jqx.dataAdapter(specs);
					griddataAdapter.dataBind();
				    $("#instance-specGrid").jqxGrid({source: griddataAdapter});

				    var winSize = {};
				    winSize = {width:700, height:680};
				    var windowW = $(window).width();
	    			var windowH = $(window).height();
	    			winSize.position = {
	    					x : (windowW - winSize.width) / 2,
	    					y : (windowH - winSize.height) / 2
	    			};
	    			$("#tabwindow").jqxWindow(winSize);
	    			$("#tabwindow").jqxWindow('open');
//	    			$("#instance-approveStatus").trigger('change');
				}
		});
	};
	
	
	/** 云主机服务退订审核弹出窗口 */
	this.showCancelApprovePopWin = function (rowData) {
		//设置云主机开通审核明细内容隐藏
		$('#approveMgtForm').hide();
		$('#instance-approveForm').hide();
		//设置云主机服务变更审核明细内容显示
		$('#cancel-approveForm').show();
		//设置隐藏变量
		$("#cancel-instanceSid").val(rowData.processObjectId);
		$("#cancel-processInstanceId").val(rowData.processInstanceId);
		$("#cancel-approvorAction").val(rowData.approvorAction);
		$("#cancel-processType").val(rowData.processType);
		$("#tabwindow").jqxWindow({title: rowData.processTypeName+"("+rowData.approvorActionName+")"}); 
//		$('#cancel-approveStatus').val("01");
		$("#cancel-isNeedPrincipal").hide();
		 Core.AjaxRequest({
				type : 'get',
				async: false,
				url : ws_url + "/rest/approve/platform/approve/adjustinstanceDetail/" + rowData.processObjectId + "/" + rowData.processInstanceId,
				callback : function(data) {
					//设置实例信息
					$('#cancel-instanceName').html(data.instanceName);
					$('#cancel-owner').html(data.ownerId);
					$('#cancel-mgtObjName').html(data.mgtObjName);
					$('#cancel-mgtObjSid').html(data.mgtObjSid);
				    $('#cancel-changeCheck').hide();
				    
				    var winSize = {};
				    winSize = {width:700, height:585};
				    var windowW = $(window).width();
	    			var windowH = $(window).height();
	    			winSize.position = {
	    					x : (windowW - winSize.width) / 2,
	    					y : (windowH - winSize.height) / 2
	    			};
	    			$("#tabwindow").jqxWindow(winSize);
	    			$("#tabwindow").jqxWindow('open');
//	    			$("#cancel-approveStatus").trigger('change');
				}
		});
		 Core.AjaxRequest({
				type : 'get',
				url : ws_url + "/rest/order/getInstSpecForDataDisk/" + rowData.processObjectId,
				async: false,
				callback : function(data) {
					var sourcedatagrid ={
			              datatype: "json",
			              localdata: data
				    };
				    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
				    $("#cancel-specGrid").jqxGrid({source: dataAdapter});
				}
		});
		 $($("[id^='cancel-approveStatus-']")[0]).jqxRadioButton('check');
	};

	/** 云主机服务变更审核详情弹出窗 */
	this.showInstanceApprovedDetailPopWin = function (rowData) {
		$("#instance-approvedspecGrid").jqxGrid('clear');
		$("#instance-approveddiskSpecGrid").jqxGrid('clear');
		$('#approvedDetailForm').hide();
		$('#cancel-approvedForm').hide();
		$('#instance-approvedForm').show();
		//设置隐藏变量
		$("#instance-approvedinstanceSid").val(rowData.processObjectId);
		$("#instance-approvedprocessInstanceId").val(rowData.processInstanceId);
		$("#instance-approvedapprovorAction").val(rowData.approvorAction);
		$("#instance-approvedprocessType").val(rowData.processType);
		$("#jqxapprovedwindow").jqxWindow({title: rowData.processTypeName+"("+rowData.approvorActionName+")"}); 
		 Core.AjaxRequest({
				type : 'get',
				url : ws_url + "/rest/approve/platform/approved/adjustinstance/" + rowData.processObjectId + "/" + rowData.processInstanceId,
				callback : function(data) {
					//设置实例信息
					$('#instance-approvedinstanceName').html(data.instanceName);
					$('#instance-approvedowner').html(data.ownerId);
					$('#instance-approvedMgtObjName').html(data.mgtObjName);
					var specs = data.specs;
					if(specs && specs.length > 0) {
						var griddataAdapter = new $.jqx.dataAdapter(specs);
						griddataAdapter.dataBind();
					    $("#instance-approvedspecGrid").jqxGrid({source: griddataAdapter});
					}
				    
				    var diskSpecs = data.diskSpecs;
				    if(diskSpecs && diskSpecs.length > 0) {
						var diskdataAdapter = new $.jqx.dataAdapter(diskSpecs);
						diskdataAdapter.dataBind();
					    $("#instance-approveddiskSpecGrid").jqxGrid({source: diskdataAdapter});
				    }
//				    var netSpecs = data.netSpecs;
//					var netdataAdapter = new $.jqx.dataAdapter(netSpecs);
//					netdataAdapter.dataBind();
//				    $("#instance-approvednetSpecGrid").jqxGrid({source: netdataAdapter});
				    
	 			    var winSize = {};
				    winSize = {width:750, height:585};
				    var windowW = $(window).width();
	    			var windowH = $(window).height();
	    			winSize.position = {
	    					x : (windowW - winSize.width) / 2,
	    					y : (windowH - winSize.height) / 2
	    			};
	    			$("#jqxapprovedwindow").jqxWindow(winSize);
	    			$("#jqxapprovedwindow").jqxWindow('open');
				}
		});
	};
	
	/** 云主机服务退订审核详情弹出窗 */
	this.showCancelApprovedDetailPopWin = function (rowData) {
		$('#approvedDetailForm').hide();
		$('#instance-approvedForm').hide();
		$('#cancel-approvedForm').show();
		//设置隐藏变量
		$("#cancel-approvedinstanceSid").val(rowData.processObjectId);
		$("#cancel-approvedprocessInstanceId").val(rowData.processInstanceId);
		$("#cancel-approvedapprovorAction").val(rowData.approvorAction);
		$("#cancel-approvedprocessType").val(rowData.processType);
		$("#jqxapprovedwindow").jqxWindow({title: rowData.processTypeName+"("+rowData.approvorActionName+")"}); 
		 Core.AjaxRequest({
				type : 'get',
				url : ws_url + "/rest/approve/platform/approved/adjustinstance/" + rowData.processObjectId + "/" + rowData.processInstanceId,
				callback : function(data) {
					//设置实例信息
					$('#cancel-approvedinstanceName').html(data.instanceName);
					$('#cancel-approvedowner').html(data.ownerId);
					$('#cancel-approvedMgtObjName').html(data.mgtObjName);
//					var specs = data.specs;
//					var griddataAdapter = new $.jqx.dataAdapter(specs);
//					griddataAdapter.dataBind();
//				    $("#cancel-approvedspecGrid").jqxGrid({source: griddataAdapter});
				    
//				    var diskSpecs = data.diskSpecs;
//					var diskdataAdapter = new $.jqx.dataAdapter(diskSpecs);
//					diskdataAdapter.dataBind();
//				    $("#cancel-approveddiskSpecGrid").jqxGrid({source: diskdataAdapter});

	 			    var winSize = {};
				    winSize = {width:750, height:585};
				    var windowW = $(window).width();
	    			var windowH = $(window).height();
	    			winSize.position = {
	    					x : (windowW - winSize.width) / 2,
	    					y : (windowH - winSize.height) / 2
	    			};
	    			$("#jqxapprovedwindow").jqxWindow(winSize);
	    			$("#jqxapprovedwindow").jqxWindow('open');
				}
		});
		 Core.AjaxRequest({
				type : 'get',
				url : ws_url + "/rest/order/getInstSpecForDataDisk/" + rowData.processObjectId,
				async: false,
				callback : function(data) {
					var sourcedatagrid ={
			              datatype: "json",
			              localdata: data
				    };
				    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
				    $("#cancel-approvedspecGrid").jqxGrid({source: dataAdapter});
				}
		});
	};
	
	/** 云主机开通审核提交 */
	this.approveMgtSubmit = function(){
		$('#approveMgtForm').jqxValidator('validate');
	};

	/** 云主机服务变更审核提交 */
	this.instanceApproveMgtSubmit = function(){
		$('#instance-approveForm').jqxValidator('validate');
	};
	
	/** 云主机服务退订审核提交 */
	this.cancelApproveMgtSubmit = function(){
		$('#cancel-approveForm').jqxValidator('validate');
	};
	
	/** 加载审核历史流程图（未审核） */
	this.openUnapproveHistory = function(){
		//初始化流程图
		var rowindex = $('#unapprovedatagrid').jqxGrid('getselectedrowindex');
		if(rowindex >= 0){
			var data = $('#unapprovedatagrid').jqxGrid('getrowdata', rowindex);
			$("#processPic").attr("src", ws_url + "/rest/approve/platform/getApproveFlow/" + data.processInstanceId + "?r=" + new Date().getTime());
		}
		
	};

	/** 加载审核历史审核记录（未审核） */
	this.showUnapprovedHistory = function () {
		var rowindex = $('#unapprovedatagrid').jqxGrid('getselectedrowindex');
		if(rowindex >= 0){
			var data = $('#unapprovedatagrid').jqxGrid('getrowdata', rowindex);
			unapp.searchApproveHistory(data);
		}
	};

	/** 加载审核历史流程图（已审核） */
	this.openapproveHistory = function(){
			var rowindex = $('#approveddatagrid').jqxGrid('getselectedrowindex');
		if(rowindex >= 0){
			    var data = $('#approveddatagrid').jqxGrid('getrowdata', rowindex);
			    $("#processPic1").attr("src", ws_url + "/rest/approve/platform/getApproveFlow/" + data.processInstanceId + "?r=" + new Date().getTime());
		}
	};

	/** 加载审核历史审核记录（已审核） */
	this.showApprovedHistory = function () {
		var rowindex = $('#approveddatagrid').jqxGrid('getselectedrowindex');
		if(rowindex >= 0){
			var data = $('#approveddatagrid').jqxGrid('getrowdata', rowindex);
			apped.searchApproveHistory(data);
		}
	};

	/** 待审核记录查询条件组装,并调用查询方法 */
	this.searchUnapproveFlow = function(){
		 unapp.searchObj = {approveType:"01"};
		 var approveObjectLike = $("#search-flow-instance").val();
		 if($.trim(approveObjectLike) != "") {
			 unapp.searchObj['qm.approveObjectLike'] = approveObjectLike;
		 }
		 var proposeByLike = $("#search-flow-createdby").val();
		 if($.trim(proposeByLike) != "") {
			 unapp.searchObj['qm.proposeByLike'] = proposeByLike;
		 }
		 var processType = $("#search-flow-type-unapprove").val();
		 if(processType != "全部") {
			 unapp.searchObj['qm.processType'] = processType;
		 }
     	 unapp.searchApproveInfo();
	};

	/** 已审核记录查询条件组装,并调用查询方法 */
	this.searchapprovedFlow = function(){
		 apped.searchObj = {approveType:"02"};
		 var approveObjectLike = $("#search-flowed-instance").val();
		 if($.trim(approveObjectLike) != "") {
			 apped.searchObj['qm.approveObjectLike'] = approveObjectLike;
		 }
		 var approvorIdLike = $("#search-flowed-createdby").val();
		 if($.trim(approvorIdLike) != "") {
			 apped.searchObj['qm.approvorIdLike'] = approvorIdLike;
		 }
		 var processType = $("#search-flow-type-approve").val();
		 if(processType != "全部") {
			 apped.searchObj['qm.processType'] = processType;
		 }
	     // sunyu updated for #106
		 var approveFromTime = $("#approveFromTime").val();
	     if ($.trim(approveFromTime) != "") {
	    	 apped.searchObj['qm.approveFromTime'] = approveFromTime + " 00:00:00";
	     }
	     var approveToTime = $("#approveToTime").val();
	     if ($.trim(approveToTime) != "") {
	    	 apped.searchObj['qm.approveToTime'] = approveToTime + " 23:59:59";
	     }
	     // end
    	 apped.searchApproveInfo();
	};
	this.initReflushApproveList = function () {
		// 管理员审核验证成功提交
    	$('#approveMgtForm').on('validationSuccess', function (event) {
    		var selectIndex = $('#unapprove-orderDetailsGrid').jqxGrid('selectedrowindex'); 
        	if(selectIndex >= 0) {
          		var data = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', selectIndex);
            	unapp.setInstanceResSet(
            		data.instanceSid, 
            		$("#rescomuteid").val() == '自动分配' ? '' :$("#rescomuteid").val(),
            		$("#rescomuteType").val(),
            		$("#vLanIDO").val(),
            		$("#vLanIDI").val() == '自动分配' ? '' :$("#vLanIDI").val(),
            		$("#wanIps").val() == '自动分配' ? '' :$("#wanIps").val(),
            		$("#lanIps").val() == '自动分配' ? '' :$("#lanIps").val(),
            	    $('#partitionType').val() == '1' ? ($("#virtualSwitch").val() == '自动分配' ? '' :$("#virtualSwitch").val()) : '',
            	 	$('#partitionType').val() == '1' ? ($("#cpuResPool").val() == '自动分配' ? '' :$("#cpuResPool").val()) : '',
            	 	$('#partitionType').val() == '0' ? ($("#rootHbaCard").val() == '自动分配' ? '' :$("#rootHbaCard").val()) : '',
            	 	$('#partitionType').val() == '0' ? ($("#networkHbaCard").val() == '自动分配' ? '' :$("#networkHbaCard").val()) : '',
    	 			$('#partitionType').val() == '0' ? ($("#stHbaDiv").val() == '自动分配' ? '' :$("#stHbaDiv").val()) : '',
		 	        unapp.getResType(),
		 	        $('#resVe').val() == 'HMC,IVM' ? $('#partitionType').val() : '',
		 	        unapp.getResPoolType(),
// 	        	    $("#allocateType").val(),
		 	        $("#allocateType-create").val()==true?"1":"2",
 	        	    $("#aixPar").val()
            	);
          	}
        	var approveStatus;
	    	for(var i=0; i<$("[id^='approveStatus-']").length;i++){
	    		if($($("[id^='approveStatus-']")[i]).val()==true){
	    			approveStatus = $($("[id^='approveStatus-']")[i]).attr("name");
	    		}
	    	}
    		 Core.AjaxRequest({         
 				url : ws_url + "/rest/approve/platform/createAdminApprove",
 				params :{
 					processObjectId: $("#processObjectId").val(),
 					detailSid: $("#detailSid").val(),
 					orderId: $("#orderId").val(),
 					resSet: unapp.resSelectData,
// 					approveStatus: $("#approveStatus").val(),
 					approveStatus: approveStatus,
 					approveNote: $("#approveNote").val(),
 					processInstanceId: $("#processInstanceId").val(),
 					approvorAction: $("#approvorAction").val(),
 					processType: $("#processType").val(),
 					isNeedPrincipal: !$("#approveIsNeedPrincipal").is(':hidden') ? $("#approveIsNeedPrincipal").val(): null
 				},
 				callback : function (data) {
 					unapp.searchApproveInfo();
					$("#tabwindow").jqxWindow('close');
					$('#unapprovedatagrid').jqxGrid('clearselection');
					$('#orderDetailsGrid').jqxGrid('clearselection');
					$("#mgtApprove").jqxButton({ width: '60',theme:"metro",height: '18px',  disabled: true});
					
					//更新菜单
					/** 获取当前用户的角色和权限数据，生成导航条 */
				    if(currentUser != null || currentUser != 'undefined'){
				    	Core.AjaxRequest({ 
				    		url : ws_url + "/rest/user/platform/findMoudules/" + currentUser.userSid,
				    		type : "GET",
				    		async: false,
				    		callback : function (data) {
				    			
				    			window.parent.createNavList(data);
				    			window.parent.moudulesData = data;
				    			
				    			window.parent.$("#menuContent .liShow").removeClass("liShow");
				    			window.parent.$("#menuContent").find("b:contains('审核管理')").parents("li").addClass("liShow");
				    		} 
				    	});
				    }
 			    },
 			    failure:function(data){
					$("#tabwindow").jqxWindow('close');
					$('#unapprovedatagrid').jqxGrid('clearselection');
					$('#orderDetailsGrid').jqxGrid('clearselection');
					$("#mgtApprove").jqxButton({ width: '60',theme:"metro",height: '18px',  disabled: true});
 			    }
 			});
    	});
    	
	};
	
	// 实例审核验证成功提交
	$('#instance-approveForm').on('validationSuccess', function (event) {	 
		 var changeArr = [];
//		 var approveStatus = $("#instance-approveStatus").val();
		 var approveStatus;
    	 for(var i=0; i<$("[id^='instance-approveStatus-']").length;i++){
    		if($($("[id^='instance-approveStatus-']")[i]).val()==true){
    			approveStatus = $($("[id^='instance-approveStatus-']")[i]).attr("name");
    		}
    	 }
		 if(approveStatus == '01') {
			 me.submitInstanceApproveInfo();
		 } else if(approveStatus == '02') {
			 me.submitInstanceApproveInfo();
		 }
	});
	
	this.submitInstanceApproveInfo = function () {
		var trs = $("#approve-net-info").find("tr");
		var nets = new Array;
		for(var i = 0;i< trs.length;i++){
			if($($(trs[i]).find("td")[3]).attr("name") == "add" ){
				var netType = $($(trs[i]).find("td")[0]).attr("name");
				var netWork = $($(trs[i]).find("td")[1]).children().val();
				var ipaddress = $($(trs[i]).find("td")[2]).children().val();
				var net = {
						resNetworkId : netWork,
						resNetworkType: netType,
						ipAddress:ipaddress,
						operate:$($(trs[i]).find("td")[3]).attr("name")
				};
				nets.push(net);
			}else if($($(trs[i]).find("td")[3]).attr("name") == "delete"){
				var netType = $($(trs[i]).find("td")[0]).attr("name");
				var netWork = $($(trs[i]).find("td")[1]).attr("name");
				var ipaddress = $($(trs[i]).find("td")[2]).attr("name");
				var net = {
						resNetworkId : netWork,
						resNetworkType: netType,
						ipAddress:ipaddress,
						operate:$($(trs[i]).find("td")[3]).attr("name")
				};
				nets.push(net);
			}
		}
		var json_data = JSON.stringify(nets);
		
		var rows =  $('#instance-specGrid').jqxGrid('getrows');
		var cpu = unapp.getSpecData(rows, 'CPU', 'newValue');
		var cpuMin = unapp.getSpecData(rows, 'CPU_MIN', 'newValue');
		var cpuMax = unapp.getSpecData(rows, 'CPU_MAX', 'newValue');
		var memory = unapp.getSpecData(rows, 'MEMORY', 'newValue');
		var memoryMin = unapp.getSpecData(rows, 'MEMORY_MIN', 'newValue');
		var memoryMax = unapp.getSpecData(rows, 'MEMORY_MAX', 'newValue');
		var powerCpuUsedUnit = unapp.getSpecData(rows, 'POWER_CPU_USED_UNIT', 'newValue');
		var powerCpuMinUnit = unapp.getSpecData(rows, 'POWER_CPU_MIN_UNIT', 'newValue');
		var powerCpuMaxUnit = unapp.getSpecData(rows, 'POWER_CPU_MAX_UNIT', 'newValue');
		
		 var instance_approveStatus;
	   	 for(var i=0; i<$("[id^='instance-approveStatus-']").length;i++){
	   		if($($("[id^='instance-approveStatus-']")[i]).val()==true){
	   			instance_approveStatus = $($("[id^='instance-approveStatus-']")[i]).attr("name");
	   		}
	   	 }
		
		Core.AjaxRequest({         
			url : ws_url + "/rest/approve/platform/createAdminApprove",
			params :{
				map:null,
				instanceSid: $("#instance-instanceSid").val(),
				processObjectId: $("#processObjectId").val(),
//				approveStatus: $("#instance-approveStatus").val(),
				approveStatus: instance_approveStatus,
				approveNote: $("#instance-approveNote").val(),
				processInstanceId: $("#instance-processInstanceId").val(),
				approvorAction: $("#instance-approvorAction").val(),
				processType: $("#instance-processType").val(),
				isNeedPrincipal: !$("#instance-isNeedPrincipal").is(':hidden') ? $("#instance-isNeedPrincipal").val(): null,
				cpu: cpu,
				cpuMin: cpuMin,
				cpuMax: cpuMax,
				memory: memory,
				memoryMin: memoryMin,
				memoryMax: memoryMax,
				powerCpuUsedUnit: powerCpuUsedUnit,
				powerCpuMinUnit: powerCpuMinUnit,
				powerCpuMaxUnit: powerCpuMaxUnit,
//				rescomuteid: $("#rescomuteid2").val() == '全部' ? null : $("#rescomuteid2").val() ,
//				vLanIDO: $("#vLanIDO2").val() == '全部' ? null : $("#vLanIDO2").val() ,
//				vLanIDI: $("#vLanIDI2").val() == '全部' ? null : $("#vLanIDI2").val() 
				nets: json_data
			},
			callback : function (data) {
				unapp.searchApproveInfo();
				$("#tabwindow").jqxWindow('close');
				$('#unapprovedatagrid').jqxGrid('clearselection');
				$("#mgtApprove").jqxButton({ width: '60',theme:"metro",height: '18px',  disabled: true});
				
				//更新菜单
				/** 获取当前用户的角色和权限数据，生成导航条 */
			    if(currentUser != null || currentUser != 'undefined'){
			    	Core.AjaxRequest({ 
			    		url : ws_url + "/rest/user/platform/findMoudules/" + currentUser.userSid,
			    		type : "GET",
			    		async: false,
			    		callback : function (data) {
			    			
			    			window.parent.createNavList(data);
			    			window.parent.moudulesData = data;
			    			
			    			window.parent.$("#menuContent .liShow").removeClass("liShow");
			    			window.parent.$("#menuContent").find("b:contains('审核管理')").parents("li").addClass("liShow");
			    		} 
			    	});
			    }
		    },
		    failure:function(data){
		    	$("#tabwindow").jqxWindow('close');
		    	$('#unapprovedatagrid').jqxGrid('clearselection');
		    	$("#mgtApprove").jqxButton({ width: '60',theme:"metro",height: '18px',  disabled: true});
		    }
		});
	};
	
	// 实例审核验证成功提交
	$('#cancel-approveForm').on('validationSuccess', function (event) {	
		var cancel_approveStatus;
	   	for(var i=0; i<$("[id^='cancel-approveStatus-']").length;i++){
	   		if($($("[id^='cancel-approveStatus-']")[i]).val()==true){
	   			cancel_approveStatus = $($("[id^='cancel-approveStatus-']")[i]).attr("name");
	   		}
	   	}
	   	var cancel_type;
	   	if($(".cancelTypeTr").css("display")!="none"){
	   		cancel_type = $("#cancel-type-create").val()==true?"1":"2";
	   	}
		Core.AjaxRequest({         
			url : ws_url + "/rest/approve/platform/createAdminApprove",
			params :{
				map:null,
				instanceSid: $("#cancel-instanceSid").val(),
				processObjectId: $("#processObjectId").val(),
//				approveStatus: $("#cancel-approveStatus").val(),
				approveStatus: cancel_approveStatus,
				cancelType: cancel_type,
				approveNote: $("#cancel-approveNote").val(),
				processInstanceId: $("#cancel-processInstanceId").val(),
				approvorAction: $("#cancel-approvorAction").val(),
				processType: $("#cancel-processType").val(),
				isNeedPrincipal: !$("#cancel-isNeedPrincipal").is(':hidden') ? $("#cancel-isNeedPrincipal").val(): null,
			},
			callback : function (data) {
				unapp.searchApproveInfo();
				$("#tabwindow").jqxWindow('close');
				$('#unapprovedatagrid').jqxGrid('clearselection');
				$("#mgtApprove").jqxButton({ width: '60',theme:"metro",height: '18px',  disabled: true});
				
				//更新菜单
				/** 获取当前用户的角色和权限数据，生成导航条 */
			    if(currentUser != null || currentUser != 'undefined'){
			    	Core.AjaxRequest({ 
			    		url : ws_url + "/rest/user/platform/findMoudules/" + currentUser.userSid,
			    		type : "GET",
			    		async: false,
			    		callback : function (data) {
			    			
			    			window.parent.createNavList(data);
			    			window.parent.moudulesData = data;
			    			
			    			window.parent.$("#menuContent .liShow").removeClass("liShow");
			    			window.parent.$("#menuContent").find("b:contains('审核管理')").parents("li").addClass("liShow");
			    		} 
			    	});
			    }
		    },
		    failure:function(data){
		    	$("#tabwindow").jqxWindow('close');
		    	$('#unapprovedatagrid').jqxGrid('clearselection');
		    	$("#mgtApprove").jqxButton({ width: '60',theme:"metro",height: '18px',  disabled: true});
		    }
		});
	});
	
	/** 资源开通检查提交 */
	this.vmResCheckSubmit = function () {
		var selectIndex = $('#unapprove-orderDetailsGrid').jqxGrid('selectedrowindex'); 
    	if(selectIndex >= 0) {
      		var data = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', selectIndex);
        	unapp.setInstanceResSet(
        		data.instanceSid, 
        		$("#rescomuteid").val() == '自动分配' ? '' :$("#rescomuteid").val(),
        		$("#rescomuteType").val(),
        		$("#vLanIDO").val(),
        		$("#vLanIDI").val() == '自动分配' ? '' :$("#vLanIDI").val(),
        		$("#wanIps").val() == '自动分配' ? '' :$("#wanIps").val(),
	 	        $("#lanIps").val() == '自动分配' ? '' :$("#lanIps").val(),
	 	        $('#partitionType').val() == '1' ? ($("#virtualSwitch").val() == '自动分配' ? '' :$("#virtualSwitch").val()) : '',
	 	        $('#partitionType').val() == '1' ? ($("#cpuResPool").val() == '自动分配' ? '' :$("#cpuResPool").val()) : '',
        		$('#partitionType').val() == '0' ? ($("#rootHbaCard").val() == '自动分配' ? '' :$("#rootHbaCard").val()) : '',
        		$('#partitionType').val() == '0' ? ($("#networkHbaCard").val() == '自动分配' ? '' :$("#networkHbaCard").val()) : '',
				$('#partitionType').val() == '0' ? ($("#stHbaDiv").val() == '自动分配' ? '' :$("#stHbaDiv").val()) : '',
	 	        unapp.getResType(),
	 	        $('#resVe').val() == 'HMC,IVM' ? $('#partitionType').val() : '',
	 	        unapp.getResPoolType(),
//	        	    $("#allocateType").val(),
	 	            $("#allocateType-create").val()==true?"1":"2",
 	        	    $("#aixPar").val()
        	);
      	}
    	
    	var approveStatus;
	   	for(var i=0; i<$("[id^='approveStatus-']").length;i++){
	   		if($($("[id^='approveStatus-']")[i]).val()==true){
	   			approveStatus = $($("[id^='approveStatus-']")[i]).attr("name");
	   		}
	   	}
    	
		 Core.AjaxRequest({
			url : ws_url + "/rest/approve/platform/vmResCheck",
			params :{
				processObjectId: $("#processObjectId").val(),
				detailSid: $("#detailSid").val(),
				orderId: $("#orderId").val(),
				resSet: unapp.resSelectData,
//				approveStatus: $("#approveStatus").val(),
				approveStatus: approveStatus,
				approveNote: $("#approveNote").val(),
				processInstanceId: $("#processInstanceId").val(),
				approvorAction: $("#approvorAction").val(),
				processType: $("#processType").val()
			},
			callback : function (data) {
		    },
		    failure:function(data){
		    }
		});
	
	};
	
	/** 资源变更检查提交 */
	this.instanceChangeCheckSubmit = function () {
		//封装网络的json
		var trs = $("#approve-net-info").find("tr");
		var nets = new Array;
		for(var i = 0;i< trs.length;i++){
			if($($(trs[i]).find("td")[3]).attr("name") == "add"){
				var netType = $($(trs[i]).find("td")[0]).attr("name");
				var netWork = $($(trs[i]).find("td")[1]).children().val();
				var ipaddress = $($(trs[i]).find("td")[2]).children().val();
				var net = {
						resNetworkId : netWork,
						resNetworkType: netType,
						ipAddress:ipaddress,
						operate:"add"
				};
				nets.push(net);
			}else if($($(trs[i]).find("td")[3]).attr("name") == "delete"){
				var netType = $($(trs[i]).find("td")[0]).attr("name");
				var netWork = $($(trs[i]).find("td")[1]).attr("name");
				var ipaddress = $($(trs[i]).find("td")[2]).attr("name");
				var net = {
						resNetworkId : netWork,
						resNetworkType: netType,
						ipAddress:ipaddress,
						operate:"delete"
				};
				nets.push(net);
			}
		}
		var json_data = JSON.stringify(nets);
		var rows =  $('#instance-specGrid').jqxGrid('getrows');
		var cpu = unapp.getSpecData(rows, 'CPU', 'newValue');
		var cpuMin = unapp.getSpecData(rows, 'CPU_MIN', 'newValue');
		var cpuMax = unapp.getSpecData(rows, 'CPU_MAX', 'newValue');
		var memory = unapp.getSpecData(rows, 'MEMORY', 'newValue');
		var memoryMin = unapp.getSpecData(rows, 'MEMORY_MIN', 'newValue');
		var memoryMax = unapp.getSpecData(rows, 'MEMORY_MAX', 'newValue');
		var powerCpuUsedUnit = unapp.getSpecData(rows, 'POWER_CPU_USED_UNIT', 'newValue');
		var powerCpuMinUnit = unapp.getSpecData(rows, 'POWER_CPU_MIN_UNIT', 'newValue');
		var powerCpuMaxUnit = unapp.getSpecData(rows, 'POWER_CPU_MAX_UNIT', 'newValue');
		
		var instance_approveStatus;
	   	for(var i=0; i<$("[id^='instance-approveStatus-']").length;i++){
	   		if($($("[id^='instance-approveStatus-']")[i]).val()==true){
	   			instance_approveStatus = $($("[id^='instance-approveStatus-']")[i]).attr("name");
	   		}
	   	}
		
		Core.AjaxRequest({
			url : ws_url + "/rest/approve/platform/vmResCheck",
			params :{
				processObjectId: $("#instance-instanceSid").val(),
//				approveStatus: $("#instance-approveStatus").val(),
				approveStatus: instance_approveStatus,
				approveNote: $("#instance-approveNote").val(),
				processInstanceId: $("#instance-processInstanceId").val(),
				approvorAction: $("#instance-approvorAction").val(),
//				rescomuteid: $("#rescomuteid2").val() == '全部' ? null : $("#rescomuteid2").val() ,
//				vLanIDO: $("#vLanIDO2").val() == '全部' ? null : $("#vLanIDO2").val() ,
//				vLanIDI: $("#vLanIDI2").val() == '全部' ? null : $("#vLanIDI2").val() ,
				cpu: cpu,
				cpuMin: cpuMin,
				cpuMax: cpuMax,
				memory: memory,
				memoryMin: memoryMin,
				memoryMax: memoryMax,
				powerCpuUsedUnit: powerCpuUsedUnit,
				powerCpuMinUnit: powerCpuMinUnit,
				powerCpuMaxUnit: powerCpuMaxUnit,
				nets: json_data,
				processType: $("#instance-processType").val()
			},
			callback : function (data) {
		    },
		    failure:function(data){
		    }
		});
	};
	
	this.initApproveNetsTable = function(nets){
		$("#approve-net-info").empty();
		for(var i=0;i<nets.length;i++){
			if(nets[i].operate == "add"){
				var html ="<tr><td name='"+nets[i].resNetworkType+"'>"+nets[i].resNetworkTypeName+"</td>"+
				"<td style='height:25px;'><div id='net"+i+"'></div></td>"+
				"<td style='height:25px;'><div id='ip"+i+"'></div></td>"+
				"<td style='height:25px;' name='"+nets[i].operate+"'>"+nets[i].operateText+"</td></tr>";
				$("#approve-net-info").append(html);
				if(nets[i].resNetworkType!=""){
					Core.AjaxRequest({
	    				url :ws_url +"/rest/biz/getSubmitUserResources", 
	    				type:"post",
	    				params:{
	    					ownerId : $("#instance-owner").html(),
	    					resType : (parseInt(nets[i].resNetworkType)==2?1:2)
	    				},
	    				async:false,
	    				callback : function (data) {
	    					$("#net"+i).jqxDropDownList({ 
	    						source: data, 
	    						displayMember: "resSetType", 
	    						valueMember: "resSetSid",
	    						height: 20,
	    						theme: currentTheme,
	    						selectedIndex: 0
	    					});
	    				}
	    			});
				}
				if(nets[i].resNetworkId!=""){
					$("#net"+i).jqxDropDownList('val', nets[i].resNetworkId);
				}
				if($("#net"+i).val()!=""){
					Core.AjaxRequest({
        				url :ws_url +"/rest/ip/network/"+ $("#net"+i).val(), 
        				type:'get',
        				async:false,
        				callback : function (data) {
        					var source = new Array;
        					var indexselect = {
        						ipAddressWithStatus:"自动分配",
        						ipAddress:""
        					};
        					source.push(indexselect);
//        					if(!data || data.length == 0) return;
        					for(var m = 0; m < data.length; m++) {
        						var trs = $("#approve-net-info").find("tr");
        						var f = true;
        						for(var j = 0; j<trs.length;j++){
        							if($($(trs[j]).find("td")[3]).attr("name") == "add"){
        								if($($(trs[j]).find("td")[2]).children().val() == data[m].ipAddress&&$(event.target).attr("id")!=$($(trs[j]).find("td")[2]).children().attr("id")){
        									//该ip已存在，不再显示
	        								f = false;
        								}
        							}
        						}
        						if(f){
        							data[m].ipAddressWithStatus = data[m].ipAddress + "(" + data[m].usageStatusName + ")";
        							source.push(data[m]);
        						}
        					}
        					$("#ip"+i).jqxDropDownList({ 
        						source: source, 
        						displayMember: "ipAddressWithStatus", 
        						valueMember: "ipAddress",
        						height: 20,
	    						theme: currentTheme,
	    						selectedIndex: 0
        					});
        				}
        			});
					if(nets[i].ipAddress!=""){
						$("#ip"+i).jqxDropDownList('val', nets[i].ipAddress);
					}
				}else{
					var ipsource = new Array;
					var indexselect = {
							ipAddressWithStatus:"自动分配",
							ipAddress:""
						};
					ipsource.push(indexselect);
					$("#ip"+i).jqxDropDownList({source: ipsource, 
						displayMember: "ipAddressWithStatus", 
						valueMember: "ipAddress",
						height: 20,selectedIndex: 0,
						theme: currentTheme});
				}
				$("#net"+i).on('change', function (event){
					var source = new Array;
					var indexselect = {
							ipAddressWithStatus:"自动分配",
							ipAddress:""
						};
					source.push(indexselect);
					$(event.target.parentElement.nextElementSibling.childNodes).jqxDropDownList({source: source, 
						displayMember: "ipAddressWithStatus", 
						valueMember: "ipAddress",
						height: 20,selectedIndex: 0,
						theme: currentTheme});
				});
				$("#ip"+i).on('open', function (event){
					var selectedIndex = $(event.target).jqxDropDownList('selectedIndex'); 
					var netId = $(event.target.parentElement.previousElementSibling.childNodes).val();
					if(netId!=""){
						Core.AjaxRequest({
	        				url :ws_url +"/rest/ip/network/"+ netId, 
	        				type:'get',
	        				async:false,
	        				callback : function (data) {
	        					var source = new Array;
	        					var indexselect = {
	        						ipAddressWithStatus:"自动分配",
	        						ipAddress:""
	        					};
	        					source.push(indexselect);
	        					if(!data || data.length == 0) return;
	        					for(var m = 0; m < data.length; m++) {
	        						var trs = $("#approve-net-info").find("tr");
	        						var f = true;
	        						for(var j = 0; j<trs.length;j++){
	        							if($($(trs[j]).find("td")[3]).attr("name") == "add"){
	        								if($($(trs[j]).find("td")[2]).children().val() == data[m].ipAddress&&$(event.target).attr("id")!=$($(trs[j]).find("td")[2]).children().attr("id")){
	        									//该ip已存在，不再显示
		        								f = false;
	        								}
	        							}
	        						}
	        						if(f){
	        							data[m].ipAddressWithStatus = data[m].ipAddress + "(" + data[m].usageStatusName + ")";
	        							source.push(data[m]);
	        						}
	        					}
	        					$(event.target).jqxDropDownList({ 
	        						source: source, 
	        						displayMember: "ipAddressWithStatus", 
	        						valueMember: "ipAddress",
	        						height: 20,
		    						theme: currentTheme,
		    						selectedIndex: selectedIndex
	        					});
	        				}
	        			});
					}
				});
			}else{
				var html = "<tr><td style='height:25px;' name='"+nets[i].resNetworkType+"'>"+(typeof(nets[i].resNetworkTypeName)=="undefined"?"":nets[i].resNetworkTypeName)+"</td>"+
					"<td style='height:25px;' name='"+nets[i].resNetworkId+"'>"+nets[i].resNetworkName+"</td>"+
					"<td style='height:25px;' name='"+nets[i].ipAddress+"'>"+nets[i].ipAddressWithStatus+"</td>"+
					"<td style='height:25px;' name='"+nets[i].operate+"'>"+nets[i].operateText+"</td></tr>";
				$("#approve-net-info").append(html);
			}				
		}
	};
};



function showInstanceSpecEditWindow (rowindex){
	Core.AjaxRequest({
		type : 'post',
		params : {
			type : '1'
		},
		async: false,
		url : ws_url + "/rest/order/images",
		callback : function(data) {
				var sourcedatagrid ={
					datatype: "json",
					localdata: data
				};
				var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
				$("#instanceSpecOs").jqxDropDownList({source: dataAdapter});
		}
	});
	
	//var rowindex =  $('#unapprove-orderDetailsGrid').jqxGrid('getselectedrowindex');
		if(rowindex >= 0){
			var rowData = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', rowindex);
			if(rowData) {
       		Core.AjaxRequest({
 				type : 'get',
 				url : ws_url + "/rest/order/getInstSpec/" + rowData.instanceSid,
 				callback : function(data) {
 					var curData = uapproveInfoModelInstance.getResData(rowData.instanceSid);
 					if(curData.ve == 'HMC,IVM') {
 						$("#hostNameRule").html('如：DEV-KFCSY-AIX7.1-01');
 					} else if(curData.ve == 'VMware') {
 						$("#hostNameRule").html('如：开发测试云-DEV-KFCSY-RHEL6.4-64-01');
 					}
 					$("#instanceSpecName").val(rowData.instanceName);
 					$("#resInstanceSpecName").val(rowData.resInstName);
 					$("#instanceSpecCpu").val(getSpecByName(data, 'CPU'));
 					$("#instanceSpecMemory").val(getSpecByName(data, 'MEMORY'));
 					$("#instanceSpecOs").val(getSpecByName(data, 'OS'));
 					//$("#instanceSpecWan").val(getSpecByName(data, 'NEED_WAN'));
 					//$("#instanceSpecLan").val(getSpecByName(data, 'NEED_LAN'));
 				}
 			});
       		
       		Core.AjaxRequest({
    			type : 'get',
    			url : ws_url + "/rest/order/getInstSpecForDataDisk/" + rowData.instanceSid,
    			callback : function(data) {
    				
    				var sourcedatagrid ={
    		              datatype: "json",
    		              localdata: data
    			    };
    			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
    			    $("#unapprove-serviceSpecGrid").jqxGrid({source: dataAdapter});
    			}
       		});
			}
		}
	var windowW = $(window).width();
	var windowH = $(window).height();
	$("#instanceSpecEditWindow").jqxWindow({
		position : {
				x : (windowW - 400) / 2,
				y : (windowH - 300) / 2
		}
	});
	$("#instanceSpecEditWindow").jqxWindow('open');
	
	uapproveInfoModelInstance.checkIntanceAllSelect();
};

function getSpecByName (specs, specName) {
	for(var i = 0; i < specs.length; i++) {
		if(specs[i].name == specName) {
			return specs[i].value;
		}
	}
};

function fileDownLoad(){
	window.location = ws_url + "/rest/common/file/down/"+fileNameSids;
}

function fileDownLoad2(){
	window.location = ws_url + "/rest/common/file/down/"+fileNameProSids;
}
