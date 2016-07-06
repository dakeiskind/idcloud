var orderModel = function() {
	var me = this;
	this.items = ko.observableArray();
	this.detailItems = ko.observableArray();
	this.orderSearchObj = {
			 orderIdLike: null, 
    		 tanentId : null, 
    		 status: null,
    		 mgtObjNameLike:null,
    		 ownerId:null,
    		 startTime:null,
    		 endTime:null
	};
	// 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	this.initInput = function() {
		// 初始化查询组件
		$("#order-id").jqxInput({
			placeHolder : "",
			height : 23,
			width : 120,
			minLength : 1,
			theme : currentTheme
		});
//		$("#order-mgtObjName").jqxInput({
//			placeHolder : "",
//			height : 23,
//			width : 120,
//			minLength : 1,
//			theme : currentTheme
//		});
		$("#order-ownerId").jqxInput({
			placeHolder : "",
			height : 23,
			width : 120,
			minLength : 1,
			theme : currentTheme
		});
		$("#search-button").jqxButton({
			width: '50px',height:'25px',
			theme : currentTheme
		});
		$("#order-create-startDt").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,
			allowKeyboardDelete: true,allowNullDate: true,showFooter:true,clearString:'清除',todayString:'今天', 
			value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd 00:00:00'});
  	  
  	    $("#order-create-endDt").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,
  	    	allowKeyboardDelete: true,allowNullDate: true,showFooter:true,clearString:'清除',todayString:'今天', 
  	    	value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd 23:59:59'});
  	  
	};
	 /** 查询订单 */
    this.searchOrder = function(){
    
    	me.orderSearchObj.orderIdLike = ($("#order-id").val()=="")?null:$("#order-id").val();
//    	me.orderSearchObj.tanentId = $("#tenant").val()=="全部"?"":$("#tenant").val(); 
    	me.orderSearchObj.status = $("#order-status").val()=="全部"?null:$("#order-status").val();
//    	me.orderSearchObj.mgtObjNameLike = ($("#order-mgtObjName").val()=="")?null:$("#order-mgtObjName").val();
    	me.orderSearchObj.ownerIdLike = ($("#order-ownerId").val()=="")?null:$("#order-ownerId").val();
    	me.orderSearchObj.startTime = ($("#order-create-startDt").val()=="")?null:$("#order-create-startDt").val();
    	me.orderSearchObj.endTime = ($("#order-create-endDt").val()=="")?null:$("#order-create-endDt").val();
		this.searchOrderInfo();
    };
	
	   // 用户数据
    this.searchOrderInfo = function(){
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/orders/orderList",
 			type:'post',
 			params:me.orderSearchObj,
 			async:false,
 			callback : function (data) {
 				me.items(data);
 				var sourcedatagrid ={
			              datatype: "json",
			              localdata: me.items
 			    };
 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#orderGrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
	// 初始化订单datagrid的数据源
	this.initOrderDatagrid = function() {
		// 查询出order值
		$("#orderGrid").jqxGrid(
		{
            width: "100%",
			theme: currentTheme,
            columnsresize: true,
            pageable: true, 
            pagesize: 10, 
            autoheight: true,
            autowidth: false,
            sortable: true,
            rowsheight:30,
            selectionmode:"singlerow",
			localization : gridLocalizationObj,
			columns : [ {
				text : '订单编号',
				datafield : 'orderId'
			}, 
			 {
				text : '所有者',
				datafield : 'ownerId'
			}, {
				text : '状态',
				datafield : 'statusName'
			},{
					text : '金额',
					datafield : 'amount'
			} ,{
				text : '订购时间',
				datafield : 'createdDt'
			},{
					text : '支付时间',
					datafield : 'timePurchase'
			},{
				text : '开通时间',
				datafield : 'dredgeDate'
			},
			{ text: '详情', datafield: '',width:80,cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
	        	  var td = $("#orderGrid").jqxGrid('getrowdata', row);
	        	  return "<div class='customButton' style='margin-top:4px;margin-left:10px;float:left' onclick='popOrderDetailInfoWindow("+JSON.stringify(td)+")'>详情</div>";
	          }
			}],
			showtoolbar : false,
			rendertoolbar : function(toolbar) {
				var me = this;
				var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
				var addBtn = $("<a id='jqxAddBtn' data-bind='click: openOrderDetailWindow'style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>订单详情&nbsp;&nbsp;</a>");
				var editBtn = $("<a id='jqxEditBtn' data-bind='click: openOrderApprovedRecordWindow' style='margin-left:-1px;'>&nbsp;&nbsp;<i class='icons-blue icon-shuffle'></i>流程信息&nbsp;&nbsp;</a>");
				toolbar.append(container);
				container.append(addBtn);
				container.append(editBtn);
			}
		});
		$("#jqxAddBtn").jqxButton({
			width : '80',
			theme : currentTheme
		});
		$("#jqxEditBtn").jqxButton({
			width : '80',
			theme : currentTheme
		});
		
		// 控制按钮是否可用
		  $("#orderGrid").on('rowselect', function (event) {
			  $("#jqxAddBtn").jqxButton({ disabled: false });
			  $("#jqxEditBtn").jqxButton({disabled: false});
		  });
		  $("#jqxAddBtn").jqxButton({width: '60',theme:"metro",height: '18px', disabled: true });
		  $("#jqxEditBtn").jqxButton({ width: '60',theme:"metro",height: '18px',  disabled: true});
		  
		  
		  $("#orderDetailInfoGrid").jqxGrid({
				width : "99.8%",
				height:"200px",
				theme : currentTheme,
				columnsresize : true,
				pageable : true,
				pagesize : 10,
				autoheight : false,
				localization : gridLocalizationObj,
				columns : [ 
				            /*
				            {text : '订单编号',datafield : 'orderId'},
				            {text : '服务名称',datafield : 'serviceName'}, 
				            {text : '服务实例名称',datafield : 'instanceName'}, 
				            {text : '数量',datafield : 'quantity'}, 
				            */
					        {text : '服务名称',datafield : 'serviceName'},
					        {text : '数量',datafield : 'quantity'},
					        {text : '计费类型',datafield : 'billingTypeName'},
					        {text : '时长',datafield : 'buyLength'},
					        {text : '金额',datafield : 'amount'},
					/*
					        { text: '操作系统名称', datafield: 'instanceName', width: 150},
					        { text: '主机名称', datafield: 'resInstName'},
					        { text: '操作系统', datafield: 'osVersionName', width: 250},
//				            {text : '配置规格描述',datafield : 'specificationDesc',width : 700}, 
//				            {text : '状态',datafield : 'statusName'}
*/
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

	  	    $("#orderDetailInfoGrid").on('rowselect', function (event) {
		  		  var args = event.args; 
		  		  var index = args.rowindex;
		  		  var data = $('#orderDetailInfoGrid').jqxGrid('getrowdata', index);
		  		  Core.AjaxRequest({
		 				type : 'get',
		 				url : ws_url + "/rest/orders/getInstSpecForDataDisk/" + data.instanceSid,
		 				callback : function(data) {
		 					var sourcedatagrid ={
		 			              datatype: "json",
		 			              localdata: data
		 				    };
		 				    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		 				    $("#orderDetailSpecGrid").jqxGrid({source: dataAdapter});
		 				}
	 			  });
	        });

			$("#orderDetailSpecGrid").jqxGrid({
				width : "99.8%",
				height:"220px",
				theme : currentTheme,
				columnsresize : true,
				pageable : true,
				pagesize : 10,
				autoheight : false,
				localization : gridLocalizationObj,
				columns : [ 
				            { text: '规格名称', datafield: 'description',width:100},
			                { text: '规格值', datafield: 'valueText'}
				          ],
	           showtoolbar: true,
	           // 设置toolbar操作按钮
	           rendertoolbar: function (toolbar) {
	               var container = $("<div id='explain' style='width:100%;height:30px;line-height:35px;'></div>");
	               var orderDetail = $("<font>&nbsp;&nbsp;配置规格描述</font>");
	               toolbar.append(container); 
	               container.append(orderDetail);
	           }
			});
			
		  $("#orderApprovedDataGrid").jqxGrid({
				width : "99.8%",
				height : "220px",
//				autoheight:true,
				theme : "metro",
				columnsresize : true,
				pageable : false,
				pagesize : 10,
				autoheight : false,
				localization : gridLocalizationObj,
				columns : [ {
					text : '流程实例编号',
					datafield : 'processInstanceId'
				}, {
					text : '流程类型',
					datafield : 'processTypeName'
				}, {
					text : '审核者',
					datafield : 'approvorId'
				}, {
					text : '审核操作',
					datafield : 'approvorActionName'
				}, {
					text : '审核状态',
					datafield : 'approveStatusName'
				}, {
					text : '审核意见',
					datafield : 'approveOpinion'
				}, {
					text : '审核时间',
					datafield : 'approveDate'
				} ],
 	              showtoolbar: true,
 	              rendertoolbar: function (toolbar) {
 	                  var container = $("<div id='explain' style='width:100%;height:30px;line-height:35px;'></div>");
 	                  var orderDetail = $("<font>&nbsp;&nbsp;订单审核记录</font>");
 	                  toolbar.append(container); 
 	                  container.append(orderDetail);
 	              }
			});
		 
	};
	
	// 初始化弹出window
	this.initPopWindow = function() {

		// 初始化订单详情Window
		$("#orderDetailWindow").jqxWindow({
			width : 910,
			height : 360,
			resizable : false,
			isModal : true,
			autoOpen : false,
			cancelButton : $("#orderDetailCancel"),
			theme: currentTheme,
			modalOpacity : 0.3,
			initContent : function() {
				// 初始化取消按钮
				$('#orderDetailCancel').jqxButton({
					width : '50px',
					height : '25px',
					theme :currentTheme
				});
			}
		});

		// 初始化订单审核记录Window
		$("#orderApprovedRecordWindow").jqxWindow({
			width : 900,
			height : 550,
			resizable : false,
			theme: currentTheme,
			isModal : true,
			autoOpen : false,
			cancelButton : $("#orderApprovedRecordCancel"),
			modalOpacity : 0.3,
			initContent : function() {
				// 初始化取消按钮
				$("#orderApprovedRecordCancel").jqxButton({
					width : '50px',
					height : '25px',
					theme : currentTheme
				});
			}
		});
		
		
		//新的详情弹出窗初始化
		$('#orderTabWindow').jqxWindow({
			showCollapseButton: false, 
			showCloseButton: true,
			height: 645, width: 750,
        	isModal : true,
            resizable: false,    
            autoOpen: false, 
            cancelButton : $("#closeOrderTabWin"),
			theme: currentTheme,
            initContent: function () {
                $('#orderDetailTabs').jqxTabs({ height:'90%', width:'100%', theme:"metro"});
        	    $("#closeOrderTabWin").jqxButton({ width: '50',height:"25",theme:"metro"});
            }
        });
		
	};
	/* 以下都是和页面绑定的方法 */
	// 弹出订单详情框
	this.openOrderDetailWindow = function() {
		var rowindex = $('#orderGrid').jqxGrid('getselectedrowindex');
		var data = $('#orderGrid').jqxGrid('getrowdata', rowindex);

		if (rowindex >= 0) {
			Core.AjaxRequest({
				type : 'get',
				url : ws_url + "/rest/orders/getOrder?orderId=" + data.orderId,
				callback : function(data) {
					// 给订单基本信息赋值
					$("#orderNumber").html(data.orderId);
					$("#ownerTenant").html(data.tenantName);
					$("#owner").html(data.userName);
					$("#orderStatus").html(data.statusName);
					$("#openTime").html(data.dredgeDate);

					me.detailItems(data.orderDetail);

					// 给订单明细列表赋值
					var sourcedatagrid = {
						datatype : "json",
						localdata : me.detailItems
					};
					var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
					$("#view-orderDetailGrid").jqxGrid({
						width : "99.8%",
						height : "120px",
						theme : currentTheme,
						columnsresize : true,
						pageable : true,
						pagesize : 10,
						source : dataAdapter,
						autoheight : false,
						localization : gridLocalizationObj,
						columns : [ {
							text : '订单编号',
							datafield : 'orderId',
							width : 150
						},
						/*
						{
							text : '流程实例',
							datafield : 'processInstanceId',
							width : 85
						},
						*/
						 {
							text : '服务名称',
							datafield : 'serviceName',
							width : 85
						}, {
							text : '数量',
							datafield : 'quantity',
							width : 85
						}, 
						/*
						{
							text : '计费类型',
							datafield : 'billingTypeName',
							width : 85
						}, {
							text : '计费时长',
							datafield : 'purchaseLongTime',
							width : 85
						}, {
							text : '到期时间',
							datafield : 'expectedTime',
							width : 85
						},
						*/
						 {
							text : '配置规格描述',
							datafield : 'specificationDesc',
							width : 700
						}, {
							text : '状态',
							datafield : 'statusName',
							width : 65
						} ]
					});
				}
			});

			// 设置window的显示位置
			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#orderDetailWindow").jqxWindow({
				position : {
					x : (windowW - 800) / 2,
					y : (windowH - 350) / 2
				}
			});
			$("#orderDetailWindow").jqxWindow('open');
		}

	};
	// 弹出订单审核记录
	this.openOrderApprovedRecordWindow = function() {
		var rowindex = $('#orderGrid').jqxGrid('getselectedrowindex');
		var data = $('#orderGrid').jqxGrid('getrowdata', rowindex);
		if (rowindex >= 0) {
			Core.AjaxRequest({
				type : 'get',
				url : ws_url + "/rest/orders/getOrder?orderId=" + data.orderId,
				callback : function(data) {
					//初始化流程图
			    	$("#processPic").attr("src", ws_url + "/rest/approve/platform/getApproveFlow/" + data.orderDetail[0].processInstanceId);
					Core.AjaxRequest({
						type : 'get',
			    		url : ws_url + "/rest/approve/platform/getApproveHistoryRecords/" + data.orderDetail[0].processInstanceId,
						callback : function(data) {
							// 给订单明细列表赋值
							var sourcedatagrid = {
								datatype : "json",
								localdata : data.approveRecord
							};
							var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
							// 初始化
							$("#orderApprovedRecordGrid").jqxGrid({
								width : "100%",
								height : "160px",
								theme : "metro",
								columnsresize : true,
								pageable : false,
								source:dataAdapter,
								pagesize : 10,
								autoheight : false,
								localization : gridLocalizationObj,
								columns : [ {
									text : '流程实例编号',
									datafield : 'processInstanceId'
								}, {
									text : '流程类型',
									datafield : 'processTypeName'
								}, {
									text : '审核者',
									datafield : 'approvorId'
								}, {
									text : '审核操作',
									datafield : 'approvorActionName'
								}, {
									text : '审核状态',
									datafield : 'approveStatusName'
								}, {
									text : '审核意见',
									datafield : 'approveOpinion'
								}, {
									text : '审核时间',
									datafield : 'approveDate'
								} ],
				   	              showtoolbar: true,
				   	              // 设置toolbar操作按钮
				   	              rendertoolbar: function (toolbar) {
				   	                  var container = $("<div id='explain' style='width:100%;height:30px;line-height:35px;'></div>");
				   	                  var orderDetail = $("<font>&nbsp;&nbsp;订单审核记录</font>");
				   	                  toolbar.append(container); 
				   	                  container.append(orderDetail);
				   	              }
							});
							
							if(data.activityImageInfo != null) {
			 					var widthScale = $("#processPic").width() / data.activityImageInfo.imageWidth;
			 					var heightScale = $("#processPic").height() / data.activityImageInfo.imageHeight;
				 				$("#processTrace").css("left", data.activityImageInfo.x * widthScale - parseFloat($("#processTrace").css("border-width"))  + "px");
				 				$("#processTrace").css("top", data.activityImageInfo.y * heightScale - parseFloat($("#processTrace").css("border-width")) + "px");
				 				$("#processTrace").css("width", data.activityImageInfo.width * widthScale + "px");
				 				$("#processTrace").css("height", data.activityImageInfo.height * heightScale + "px");
			 				}
			 				data.activityImageInfo = null;
						}
					});
					
				}
			});
			
			// 设置window的显示位置
			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#orderApprovedRecordWindow").jqxWindow({
				position : {
					x : (windowW - 900) / 2,
					y : (windowH - 550) / 2
				}
			});
			$("#orderApprovedRecordWindow").jqxWindow('open');
		}

	};
};

function popOrderDetailInfoWindow(data){
	if (data != null && data != "") {
		Core.AjaxRequest({
			type : 'get',
			url : ws_url + "/rest/orders/getOrder?orderId=" + data.orderId,
			callback : function(data) {
				// 给订单基本信息赋值
				$("#baseInfo-orderNumber").html(data.orderId);
				$("#baseInfo-mgtObjName").html(data.tenantName);
				$("#baseInfo-owner").html(data.userName);
				$("#baseInfo-orderStatus").html(data.statusName);
				$("#baseInfo-openTime").html(data.dredgeDate);
				$("#baseInfo-payTime").html(data.timePurchase);
				$("#baseInfo-orderAmount").html(data.amount);

				// 给订单明细列表赋值
				var sourcedatagrid = {
					datatype : "json",
					localdata : data.vmServiceInstances
				};

				var sourceOrdeDetail = {
					datatype : "json",
					localdata : data.orderDetail
				};
				var dataAdapter = new $.jqx.dataAdapter(sourceOrdeDetail);
				$("#orderDetailInfoGrid").jqxGrid({source : dataAdapter});

				$("#orderDetailInfoGrid").jqxGrid('selectrow', 0);
				$("#orderDetailInfoGrid").trigger('rowselect');
			}
		});
		
		Core.AjaxRequest({
			type : 'get',
			url : ws_url + "/rest/orders/getOrder?orderId=" + data.orderId,
			callback : function(data) {
				//初始化流程图
		    	$("#processTabPic").attr("src", ws_url + "/rest/approve/platform/getApproveFlow/" + data.orderDetail[0].processInstanceId);
				Core.AjaxRequest({
					type : 'get',
		    		url : ws_url + "/rest/approve/platform/getApproveHistoryRecords/" + data.orderDetail[0].processInstanceId,
					callback : function(data) {
						// 给订单明细列表赋值
						var sourcedatagrid = {
							datatype : "json",
							localdata : data.approveRecord
						};
						var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
						$("#orderApprovedDataGrid").jqxGrid({source : dataAdapter});
						
						if(data.activityImageInfo != null) {
		 					var widthScale = $("#processTabPic").width() / data.activityImageInfo.imageWidth;
		 					var heightScale = $("#processTabPic").height() / data.activityImageInfo.imageHeight;
			 				$("#processTabTrace").css("left", data.activityImageInfo.x * widthScale - parseFloat($("#processTabTrace").css("border-width"))  + "px");
			 				$("#processTabTrace").css("top", data.activityImageInfo.y * heightScale - parseFloat($("#processTabTrace").css("border-width")) + "px");
			 				$("#processTabTrace").css("width", data.activityImageInfo.width * widthScale + "px");
			 				$("#processTabTrace").css("height", data.activityImageInfo.height * heightScale + "px");
		 				}
		 				data.activityImageInfo = null;
					}
				});
			}
		});
	}
	
	$("#orderTabWindow").jqxWindow('open');
}
