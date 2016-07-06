var approvedModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.itemsHistory = ko.observableArray();

	    this.searchObj = {
	    		approveType:"02"
		};
	    // 用户数据
	    this.searchApproveInfo = function(){
	    	$("#approveddatagrid").jqxGrid("gotopage",0);
			var dataAdapter = Core.getPagingDataAdapter({
				gridId: "unapprovedatagrid",
				url: ws_url + "/rest/approve/getApproveByType",
				params: me.searchObj
			});
			$("#approveddatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
			/*
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/approve/getApproveByType",
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
	 			    $("#approveddatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	 		 */
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-flowed-instance").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:"metro"});
	        $("#search-flowed-createdby").jqxInput({placeHolder: "", height: 22, width: 80, minLength: 1,theme:"metro"});
	        var app = new codeModel({width:140,autoDropDownHeight:true});
	        app.getCommonCode("search-flow-type-approve","PROCESS_TYPE",true);
	        $("#approveFromTime").jqxDateTimeInput({width: '110px', culture: 'zh-CN', formatString: 'd', height: 22,showFooter:true,clearString:'清除',todayString:'今天',allowNullDate: true,value: null,theme:"metro"});
	        $("#approveToTime").jqxDateTimeInput({width: '110px', culture: 'zh-CN', formatString: 'd',height: 22,showFooter:true,clearString:'清除',todayString:'今天',allowNullDate: true,value: null,theme:"metro"});
	        $("#search-approved-button").jqxButton({ width: '50px',height:'25px',theme:"metro"});
	    	
	    };
	    this.setEditUserForm = function(data){
	    	
	    };
	    // 初始化验证规则
	    this.initValidator = function(){
	    	
	    
	    };
	    // 初始化用户datagrid的数据源
	    this.initApprovedDatagrid = function(){
	          $("#approveddatagrid").jqxGrid({
	              width: "100%",
				  theme: currentTheme,
				  virtualmode: true,
				  rendergridrows: function(){
					  return dataAdapter.records;
				  },
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
   	              autorowheight: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
//	                  { text: '流程实例编号', datafield: 'processInstanceId', width:120, cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
//	                	  return "<div style='padding-top:3px;'><a class='datagridLink' href='#'>&nbsp;"+value+"</a></div>";
//	                  }},
	                  { text: '流程类型', datafield: 'processTypeName',width:120},
	                  { text: '审核对象', datafield: 'approveObject',width:200},
	                  { text: '审核操作', datafield: 'approvorActionName'},
	                  { text: '服务名称', datafield: 'serviceName'},
	                  //{ text: '租户', datafield: 'tenantName',width:150},
	                  { text: '审核人', datafield: 'approvorId',width:150},
	                  { text: '审核时间', datafield: 'approveDate',width:150},
	                  { text: '申请人', datafield: 'proposeBy',width:150},
	                  { text: '申请时间', datafield: 'proposeDt',width:150}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var approvedDetail = $("<div><a id='approvedDetail' data-bind='click:openapproveDetails'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>审核详情&nbsp;&nbsp;</a></div>");
	                  //var approvedHistory = $("<div><a id='approvedHistory' data-bind='click:openapproveHistory' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icon-cancel'></i>审核历史&nbsp;&nbsp;</a></div>");
	                  var approveDetail = $("<div><a id='approveDetail'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>审核详情&nbsp;&nbsp;</a></div>");
	                  var historyApprove = $("<div><a id='historyApprove' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-list-2'></i>审核历史&nbsp;&nbsp;</a></div>");
	               
	                  toolbar.append(container);
	                  container.append(approvedDetail);
	                  //container.append(approvedHistory);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#approveddatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#approveddatagrid').jqxGrid('getrowdata', index);
	    		  $("#approvedDetail").jqxButton({width: '60',theme:"metro",height: '18px', disabled: false });
	   			  //$("#approvedHistory").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: false});
			    	
	   			  $("#serviceSpecGrid").hide();  	
	          });
	    	  
	    	  $("#approvedDetail").jqxButton({width: '60',theme:"metro",height: '18px', disabled: true });
   			  //$("#approvedHistory").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: true});
	    };
	    // 初始化弹出window
	    this.initPopWindow = function(){

	            $('#jqxapprovedwindow').jqxWindow({
	            	isModal : true,
		            showCollapseButton: false, 
		            resizable: false,    
	                autoOpen: false, 
	                showCloseButton: true,
	                showCollapseButton: false, 
	                maxHeight: 800, maxWidth: 850, minHeight: 400, minWidth: 400, height: 600, width: 750,
	                cancelButton : $("#approvedDetailCancel"),
	    			theme: currentTheme,
	                initContent: function () {
	                    $('#jqxapprovedtab').jqxTabs({ height:'94%', width:'100%', theme:"metro"});
	                    $("#approved-spec-tab").jqxTabs({ height:'250px', width:'734px', theme:"metro"});
	                    $('#approvedDetailCancel').jqxButton({
	    					width : '50px',
	    					height : '25px',
	    					theme :currentTheme
	    				});
	                }
	            });
	            
	    	
			$("#orderDetailsGrid").on('rowselect', function (event) {
           		var rowindex = event.args.rowindex;
           		if(rowindex >= 0){
           			var data = $('#orderDetailsGrid').jqxGrid('getrowdata', rowindex);
           			if(data) {
		           		Core.AjaxRequest({
		     				type : 'get',
		     				url : ws_url + "/rest/order/getInstSpecForDataDisk/" + data.instanceSid,
		     				callback : function(data) {
		     					
		     					var sourcedatagrid ={
		     			              datatype: "json",
		     			              localdata: data
		     				    };
		     				    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		     				    $("#serviceSpecGrid").jqxGrid({source: dataAdapter});
		     				  
		     				}
		     			});
           			}
           		}
            });
			
			$("#orderDetailsGrid").on('bindingcomplete', function (event) {
		    	$("#orderDetailsGrid").jqxGrid('selectrow', 0);
		    });	
			
			$("#collapse-serviceSpecGrid").on('click', function (event) {
		    	$("#serviceSpecGrid").show();
		    });			

			$("#instance-approvedspecGrid").jqxGrid(
					{
			             width: "100%",
			             height: "150px",
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
			$("#instance-approveddiskSpecGrid").jqxGrid(
					{
			             width: "99%",
			             height: "200px",
						 theme:currentTheme,
			             columnsresize: true,
			             editable: true,
			             selectionmode: 'singlerow',
			             editmode: 'multiplecellsadvanced',
			             localization: gridLocalizationObj,
			             columns: [
			 	             { text: '磁盘名称', datafield: 'name', width:170, editable: false},
			 	             { text: '磁盘用途', datafield: 'valueText', width:130, editable: false},
			 	             { text: '当前值', datafield: 'value', width:130, editable: false},
			 	             { text: '调整后值', datafield: 'newValue', width:130, editable: false},
			 	             { text: '操作', datafield: 'operate', editable: false}
			             ],
			             showtoolbar: false
//			             ,
//			             toolbarheight:26,
//		   	              // 设置toolbar操作按钮
//		   	              rendertoolbar: function (toolbar) {
//		   	                  var container = $("<div style='width:100%;height:25px;line-height:25px;'></div>");
//		   	                  var orderDetail = $("<font>&nbsp;&nbsp;磁盘规格变更列表</font>");
//		   	                  toolbar.append(container); 
//		   	                  container.append(orderDetail);
//		   	              }
					 });
			
//				$("#instance-approvednetSpecGrid").jqxGrid(
//					{
//			             width: "97%",
//			             height: "252px",
//						 theme:currentTheme,
//			             columnsresize: true,
//			             editable: true,
//			             selectionmode: 'singlerow',
//			             editmode: 'multiplecellsadvanced',
//			             localization: gridLocalizationObj,
//			             columns: [
//			 	              { text: '类型', datafield: 'resNetworkTypeName', width:80,editable: false },
//			 	              { text: '网络', datafield: 'resNetworkName', width:240,editable: false},
//			 	              { text: 'IP地址', datafield: 'ipAddress', width:200,editable: false},
//			 	              { text: '操作', datafield: 'operateText', editable: false}
//			             ],
//			             showtoolbar: false
//					 });
			
		        $('#jqxapprovedtab').on('selected', function (event) {
		            var index = event.args.item;
		            if (index == 1) {
		            	var rowindex = $('#approveddatagrid').jqxGrid('getselectedrowindex');
				    	if(rowindex >= 0){
						    var data = $('#approveddatagrid').jqxGrid('getrowdata', rowindex);
						    me.searchApproveHistory(data);
				    	}
		            }
		        });
			
	    };
	    /***********************************************审核历史记录****************************************/
	    
	    // 查询审核历史记录
	    this.searchApproveHistory = function(data){

	    	//初始化工单记录
	    	 Core.AjaxRequest({
	    		url : ws_url + "/rest/approve/platform/getApproveHistoryRecords/" + data.processInstanceId,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				me.itemsHistory(data.approveRecord);
	 				me.initOrderApproveRecordDatagrid(me.itemsHistory);
	 				if(data.activityImageInfo!=null) {
		 				var widthScale = $("#processPic1").width() / data.activityImageInfo.imageWidth;
	 					var heightScale = $("#processPic1").height() / data.activityImageInfo.imageHeight;
	 					var borderWidth = $("#processTrace1").css("border-width");
	 					var left = data.activityImageInfo.x * widthScale - (borderWidth == '' ?  0 : parseFloat(borderWidth));
	 					var top = data.activityImageInfo.y * heightScale - (borderWidth == '' ?  0 : parseFloat(borderWidth));
		 				$("#processTrace1").css("left", left  + "px");
		 				$("#processTrace1").css("top", top + "px");
		 				$("#processTrace1").css("width", data.activityImageInfo.width * widthScale + "px");
		 				$("#processTrace1").css("height", data.activityImageInfo.height * heightScale + "px");
	 				}
	 				data.activityImageInfo = null;
	 			}
	 		 });
	    };
	    
	    // 初始化查看工单datagrid的数据源
	    this.initOrderApproveRecordDatagrid = function(data){
	    	 var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	 
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	    	// 初始化
        	 $("#approvedHistoryGrid").jqxGrid({
   	              width: "100%",
   	              height:"160px",
   				  theme:"metro",
   	              columnsresize: true,
   	              source:dataAdapter,
   	              //autoheight: false,
   	              //autowidth: false,
   	              //autorowheight: true,
   	              sortable: true,
   	              localization: gridLocalizationObj,
   	              columns: [
   	                     { text: '流程类型', datafield: 'processTypeName'},
      	                  { text: '审核操作', datafield: 'approvorActionName'},
      	                  { text: '审核者', datafield: 'approvorId'},
      	                  { text: '审核状态', datafield: 'approveStatusName'},   	                  
      	                  { text: '审核时间', datafield: 'approveDate'},
      	                  { text: '审核意见', datafield: 'approveOpinion'}
   	              ],
   	              showtoolbar: true,
   	              // 设置toolbar操作按钮
   	              rendertoolbar: function (toolbar) {
   	                  var container = $("<div id='btnUserGroup' style='width:100%;height:30px;line-height:35px;'></div>");
   	                  var orderDetail = $("<font>&nbsp;&nbsp;订单审批记录</font>");
   	                  toolbar.append(container); 
   	                  container.append(orderDetail);
   	              }
   	          });
	    };
	    /***********************************************审核历史记录****************************************/

  };

  