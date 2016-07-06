
var idcOrderMgtModel = function () {
		var me = this;
	    this.itemsIdcOrderMgt = ko.observableArray();
	    this.itemsIdcOrderMgtAllocate = ko.observableArray();
	    this.itemsIdcOrderMgtView = ko.observableArray();
	    this.itemsIdcOrderMgtProcess = ko.observableArray();
	    this.searchObj = {
	    		transactionid:"",
	    		idcOrderType:"",
	    		status: ""
		};
	    this.orderDetails = ko.observableArray();
	    //资源选择数据
	    this.resSelectData = new Array();
	    // 查询工单数据
	    this.searchIdcOrderInfo = function(options){
	    	 Core.AjaxRequest({
	    		 url : ws_url + "/rest/idc/order/find/all",
	 			 type:'post',
	 			 params: me.searchObj,
	 			 async:false,
	 			 callback : function (data) {
	 				 me.itemsIdcOrderMgt(data);
	 				 var sourcedatagrid ={
	 			              datatype: "json",
	 			              localdata: me.itemsIdcOrderMgt
	 			     };
	 		    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	 		    	 $("#idcOrderDataGrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    	 Core.AjaxRequest({
	    		 url : ws_url + "/rest/idc/order/find/allDetail",
	 			 type:'post',
	 			 params: me.searchObj,
	 			 async:false,
	 			 callback : function (data) {
	 				 for(var i=0;i<data.length;i++){
	 					 data[i].configInfo = "操作系统："+data[i].os+"，CPU："+data[i].cpu+"核，内存："+data[i].memory+"GB，磁盘："+data[i].disk+"GB";
	 				 } 
	 				 var sourcedatagrid ={
 			              datatype: "json",
 			              localdata: data
	 			     };
	 		    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid, { autoBind: true });
	 		    	 orderDetails = dataAdapter.records;
	 			}
	 		 });
	    };
	    var nestedGrids = new Array();
	    // 查询工单datagrid的数据源
	    this.initIdcOrderDataGrid = function(){
		    var initrowdetails = function (index, parentElement, gridElement, record) {
	            var id = record.orderSid.toString();
	            var grid = $($(parentElement).children()[0]);
	            nestedGrids[index] = grid;
	            var filtergroup = new $.jqx.filter();
	            var filtervalue = id;
	            var filtercondition = 'equal';
	            var filter = filtergroup.createfilter('stringfilter', filtervalue, filtercondition);
	            var ordersbyid = [];
	            for (var m = 0; m < orderDetails.length; m++) {
	                var result = filter.evaluate(orderDetails[m]["orderSid"]);
	                if (result)
	                    ordersbyid.push(orderDetails[m]);
	            }
	            var orderssource = { 
	            	datatype: "json",
	                localdata: ordersbyid
	            };
	            var nestedGridAdapter = new $.jqx.dataAdapter(orderssource);

	            if (grid != null) {
	            	if(record.status=="4"||record.status=="5"){
	            		grid.jqxGrid({
	            			source: nestedGridAdapter,
	            			autowidth: true,
	            			height: 100,
	            			theme:currentTheme,
	            			columns: [
	            			          { text: '虚拟机名称', datafield: 'hpVmName', width: 200 },
	            			          { text: '配置信息', datafield: 'configInfo', width: 400 },
	            			          { text: '操作类型', datafield: 'vmOpTypeName', width: 200 },
	            			          { text: '状态', datafield: 'statusName', width: 100 },
	            			          { text: '完成时间', datafield: 'completeTime', width: 200 },	 
	            			          { text: '操作', width:150,  datafield: '',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
		            			        	  var td = grid.jqxGrid('getrowdata', row);
		            			        	  if(td.status=="1"||td.status=="4"){
		            			        		  if(td.vmOpType=="1"){
			            			        		  return "<div class='customButton' style='width:92%;margin-left:2%;margin-top:1%;margin-bottom:1%;height:57%;' onclick='popIDCResChooseWindow("+JSON.stringify(td)+")'>资源申请</div>";
			            						  }else if(td.vmOpType=="3"){
			            							  return "<div class='customButton' style='width:92%;margin-left:2%;margin-top:1%;margin-bottom:1%;height:57%;' onclick='popIDCResChooseWindow("+JSON.stringify(td)+")'>资源申请</div>";
			            						  }else{
			            							  return "<div class='customButton' style='width:92%;margin-left:2%;margin-top:1%;margin-bottom:1%;height:57%;' onclick='popIDCResChooseWindow("+JSON.stringify(td)+")'>资源申请</div>";
			            						  }
		            			        	  }else{
		            			        		  return "";
		            			        	  }
		            			          }
	            			          }
	            			   ]
	            		});
	            	}else{
	            		grid.jqxGrid({
	            			source: nestedGridAdapter,
	            			autowidth: true,
	            			height: 100,
	            			theme:currentTheme,
	            			columns: [
	            			          { text: '虚拟机名称', datafield: 'hpVmName', width: 200 },
	            			          { text: '配置信息', datafield: 'configInfo', width: 400 },
	            			          { text: '操作类型', datafield: 'vmOpTypeName', width: 200 },
	            			          { text: '状态', datafield: 'statusName', width: 100 },
	            			          { text: '完成时间', datafield: 'completeTime', width: 200 }
	            			]
	            		});
	            	}
	            }
	          };
	          $("#idcOrderDataGrid").jqxGrid(
	          {
	              width: "98%",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
	              sortable: true,
	              pagesize: 10, 
	              autoheight: true,
	              rowdetails: true,
	              initrowdetails: initrowdetails,
	              rowdetailstemplate: { rowdetails: "<div id='grid' style='margin: 10px;'></div>",rowdetailsheight: 150,  rowdetailshidden: true },
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: 'IDC订单号', datafield: 'orderSid',width:200},
	                  { text: 'IDC流水号', datafield: 'transactionid',width:200},
	                  { text: 'IDC订单类型', datafield: 'typeName'},
	                  { text: 'IDC订单状态', datafield: 'statusName' },
	                  { text: '完成时间', datafield: 'completeTime' },
	                  { text: '操作', width:200,  datafield: '',
	                	  cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
	                		  var data = $("#idcOrderDataGrid").jqxGrid('getrowdata', row);
	                		  if(data.status=="1"&&data.idcOrderType!="3"){
	                			  return "<div class='customButton' style='margin-left:5%;margin-top:1%;margin-bottom:1%;height:50%;' onclick='approveIDCOrder("+row+")'>审核</div>";
	                		  }else if(data.status=="6"){
	                			  return "<div class='customButton' style='margin-left:5%;margin-top:1%;margin-bottom:1%;height:50%;' onclick='feedbackOver("+row+")'>完成反馈</div>";
	                		  }
				  		}
	                  }
	              ]
	          });
	          $("#idc-orderSpecGrid").jqxGrid(
    		  {
    			  width: "732px",
 	              height:"100px",
 	              theme:currentTheme,
 	              columnsresize: true,
 	              pageable: false, 
 	              sortable: true,
 	              localization: gridLocalizationObj,
 	              columns: [
 	                 { text: '规格名称', datafield: 'description',width:100},
 	                 { text: '规格值', datafield: 'valueText'}
 	              ]
    		  });
	          
	          //变更的grid
	          $("#idc-orderBaseSpecGrid").jqxGrid(
	          {
	        	  width: "732px",
 	              height:"100px",
 	              theme:currentTheme,
 	              columnsresize: true,
 	              pageable: false, 
 	              sortable: true,
 	              localization: gridLocalizationObj,
 	              columns: [
 	                 { text: '规格项', datafield: 'description', editable: false},
	 	             { text: '当前值', datafield: 'value', width:215, editable: false},
	 	             { text: '调整后值', datafield: 'newValue', width:215, editable: false}
 	              ]
	          });
	          $("#idcOrder-diskSpec").jqxGrid(
	          {
	        	  width: "712px",
 	              height:"100px",
 	              theme:currentTheme,
 	              columnsresize: true,
 	              pageable: false, 
 	              sortable: true,
 	              localization: gridLocalizationObj,
 	              columns: [
 	                 { text: '磁盘名称', datafield: 'description', width:170, editable: false},
	 	             { text: '磁盘用途', datafield: 'valueText', width:130, editable: false},
	 	             { text: '当前值', datafield: 'value', width:130, editable: false},
	 	             { text: '调整后值', datafield: 'newValue', width:130, editable: false},
	 	             { text: '操作', datafield: 'operate', editable: false}
 	              ]
	          });
	    };
	    
	   // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initIdcOrderMgtInput = function(){
	    	$("#idc-transaction-id").jqxInput({placeHolder : "",height : 22,width : 120,minLength : 1,theme : currentTheme});
	    	var codesearch = new codeModel({width:120,autoDropDownHeight:true});
			codesearch.getCommonCode("idc-order-type","IDC_ORDER_TYPE",true);
			codesearch = new codeModel({width:120,autoDropDownHeight:true});
			codesearch.getCommonCode("idc-order-status","IDC_ORDER_STATUS",true);
			$("#idc-order-button").jqxButton({width: '50px',height:'25px',theme : currentTheme});
	    };

	    // 初始化弹出window
	    this.initIdcOrderMgtPopWindow = function(){
	    	// 审核详情window
			$('#idctabwindow').jqxWindow({
	        	isModal : true,
	            showCollapseButton: false, 
	            resizable: false,    
                autoOpen: false, 
                showCloseButton: true,
	            cancelButton : $(".approveIDCMgtCancel"),
				theme: currentTheme,
	            initContent: function () {
	                $('#idctab').jqxTabs({ height:'100%', width:'100%', theme:"metro"});
	                $("#idc-orderDetail-tab").jqxTabs({ height:'170px', width:'734px', theme:"metro"});
	                $("#idc-changeDetail-tab").jqxTabs({ height:'170px', width:'100%', theme:"metro"});
	                // 初始化
//                	$("#approveNote").jqxInput({placeHolder: "", height: 50, width: 300, minLength: 1,theme:"metro"});
                	$("#approveIDCMgtSave").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	    $("#approveIDCMgtCancel").jqxButton({ width: '50',height:"25",theme:"metro"});
		 	        $("#idcResCheck").jqxButton({ width: '70',height:"25",theme:"metro"});
	       	        
	       	        //变更弹出页面初始化
                	$("#idcSpecCheck").jqxButton({ width: '70',height:"25",theme:"metro"});
                	$("#specChangeSave").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	    $("#idcSpecCheckCancel").jqxButton({ width: '50',height:"25",theme:"metro"});
//         	        unapp.getCommonCode("instance-approveStatus","APPROVE_STATUS");
//         	        $("#instance-approveStatus").on('change', function () {
//	       	        	if($(this).val() == '01') {
//	       	        		$("#instance-approveNote").val('通过');
//	       	        	} else {
//	       	        		$("#instance-approveNote").val('');
//	       	        	}
//	       	        });
//	       	        $("#instance-approveStatus").trigger('change');
//	       	        $("#instance-changeCheck").jqxButton({ width: '70',height:"25",theme:"metro"});
	            }
	        });
			// 审核详情window
			$('#idcApproveWindow').jqxWindow({
	        	isModal : true,
	            showCollapseButton: false, 
	            resizable: false,    
                autoOpen: false, 
                showCloseButton: true,
	            cancelButton : $(".idcMgtCancel"),
				theme: currentTheme,
	            initContent: function () {
                	$("#approveButton").jqxButton({ width: '50',height:"25",theme:"metro"});
                	$("#feedbackButton").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	    $("#idcMgtCancel").jqxButton({ width: '50',height:"25",theme:"metro"});
	            }
	        });
	    };
	    this.initIpAddressDropDownList = function(id, netSid){
	    	if(!netSid || netSid == '') return;
	    	Core.AjaxRequest({
				url :ws_url +"/rest/ip/network/"+ netSid, 
				type:'get',
				async:false,
				callback : function (data) {
					if(!data || data.length == 0) return;
					for(var i = 0; i < data.length; i++) {
						data[i].ipAddressWithStatus = data[i].ipAddress + "(" + data[i].usageStatusName + ")";
					}
					var source ={
			             datatype: "json",
			             datafields: [
			                 { name: 'ipAddress' },
			                 { name: 'ipAddressWithStatus' },
			             ],
			             localdata:data
			         };
					 var dataAdapter = new $.jqx.dataAdapter(source);
					 $("#"+id+"").jqxDropDownList({
						   placeHolder: "请选择...",
		                   selectedIndex: 0, 
		                   source: dataAdapter,
		                   displayMember: 'ipAddressWithStatus', 
		                   valueMember: 'ipAddress',
		                   width: 220,
		                   height: 22,
		                   autoDropDownHeight :false,
		                   dropDownWidth :220,
		                   dropDownHeight :180,
		                   disabled:false,
		                   theme:"metro"
		             });
					
					$("#"+id+"").jqxDropDownList('insertAt', { label:"自动分配" ,value:""},0);
		        } 
		     });
	    };
	    
    };
    
    function approveIDCOrder(row){
    	 var me = new idcOrderMgtModel();
    	 var data = $("#idcOrderDataGrid").jqxGrid('getrowdata', row);
    	 if(data.status=="1"){
    		 $("#idcApproveStatus").show();
    		 $("#approveTextTd").show();
    		 $("#idcFeedback").hide();
	   		 $("#approveButton").show();
	   		 $("#feedbackButton").hide();
    		 $("#idcApproveWindow .title").html("IDC订单审核--"+data.orderSid+"订单");
    		 var unapp = new codeModel({width:200,autoDropDownHeight:true});
    		 unapp.getCommonCode("approveIdcStatus","APPROVE_STATUS");
  	         $("#approveIdcStatus").on('change', function () {
	        	if($(this).val() == '01') {
	        		$("#approveIdcNote").val('通过');
	        	} else {
	        		$("#approveIdcNote").val('');
	        	}
	         });
	         $("#approveIdcStatus").trigger('change');
	         $("#orderApproveId").val(data.orderSid);
	         $("#orderBzId").val(data.bzId);
    		 var winSize = {width:450, height:250};
    	     $("#idcApproveWindow").jqxWindow(winSize);
    		 $("#idcApproveWindow").jqxWindow('open');
    	 }else{
    		 Core.alert({
				title:"提示",
				message:"该订单信息以修改，请刷新后在进行操作！",
				callback:function(){
 					// 刷新列表
 					me.searchIdcOrderInfo();
				}
			});
    	 }
    }
    
    function feedbackOver(row){
    	 var me = new idcOrderMgtModel();
	   	 var data = $("#idcOrderDataGrid").jqxGrid('getrowdata', row);
	   	 if(data.status=="6"){
	   		 $("#idcApproveStatus").hide();
	   		 $("#approveTextTd").hide();
	   		 $("#idcFeedback").show();
	   		 $("#approveButton").hide();
	   		 $("#feedbackButton").show();
	   		 $("#idcApproveWindow .title").html("IDC订单反馈--"+data.orderSid+"订单");
	         $("#orderApproveId").val(data.orderSid);
	         $("#orderBzId").val(data.bzId);
	   		 var winSize = {width:450, height:200};
	   	     $("#idcApproveWindow").jqxWindow(winSize);
	   		 $("#idcApproveWindow").jqxWindow('open');
	   	 }else{
	   		 Core.alert({
					title:"提示",
					message:"该订单信息以修改，请刷新后在进行操作！",
					callback:function(){
						// 刷新列表
						me.searchIdcOrderInfo();
					}
				});
	   	 }
    }

    function popIDCResChooseWindow(td){
    	var me = new idcOrderMgtModel();
    	if(td.vmOpType=="1"){
    		addIdcVm(td);
    	}else if(td.vmOpType=="3"){
    		changeVmSpec(td);
    	}else{
    		Core.confirm({
    			title:"提示",
				message:"您确定要退订该虚拟机吗？",
				yes:"确定",
				confirmCallback:function(){
					 Core.AjaxRequest({
			 				url : ws_url + "/rest/idc/order/idcOrderDetail/resApply",
			 				params :{
			 					processObjectId: td.instanceSid,
			 					detailSid: td.orderDetailSid,
			 					orderId: td.orderSid,
			 					vmOpType : td.vmOpType
			 				},
			 				callback : function (data) {
			 					me.searchIdcOrderInfo();
			 			    },
			 			    failure:function(data){
			 			    	
			 			    }
			 			});
				}
    		});
    	}
    }
    
    function addIdcVm(td){
    	$("#approveIDCMgtForm").show();
    	$("#idcChangeMgtForm").hide();
    	var me = new idcOrderMgtModel();
    	$("#idctabwindow .title").html("IDC订单资源分配");
    	var order ;
    	Core.AjaxRequest({
   		 url : ws_url + "/rest/idc/order/find/all",
			 type:'post',
			 params: {orderSid:td.orderSid},
			 async:false,
			 callback : function (data) {
				 $("#idcOrder-transactionId").html(data[0].transactionId);
				 $("#idcOrder-status").html(data[0].statusName);
				 $("#idcOrder-createdDt").html(data[0].createdDt);
				 order = data[0];
			}
		 });
    	$("#vmOpType").val(td.vmOpType);
    	if(td.vmOpType=="1"){
    		$("#instanceId").val(td.hpInstanceSid);
    		$("#processType").val("01");
    	}else{
    		$("#processType").val("12");
    		$("#instanceId").val(td.instanceSid);
    	}
    	$("#orderId").val(td.orderSid);
    	$("#orderDetailId").val(td.orderDetailSid);
   	 	var spec = {description:"配置",valueText:"CPU："+td.cpu+"核，内存："+td.memory+"GB，磁盘："+td.disk+"GB"};
   	 	var specInfo ={
             datatype: "json",
             localdata: spec
	     };
  	 	var dataSpec = new $.jqx.dataAdapter(specInfo);
  	 	$("#idc-orderSpecGrid").jqxGrid({source: dataSpec});
   	 	
  	 	Core.AjaxRequest({
			url : ws_url + "/rest/idc/order/image",
			type : "post",
			params : {
				type : '2'
			},
			async : false,
			callback : function(data) {
				var source ={
		             datatype: "json",
		             datafields: [
		                 { name: "imageName" },
		                 { name: "imageId" }
		             ],
		             localdata:data
		         };
				 var dataAdapter = new $.jqx.dataAdapter(source);
				 $("#osType").jqxDropDownList({
					 selectedIndex: 0,
                     source: dataAdapter,
                     width:220, 
                     dropDownWidth:220,
                     displayMember: "imageName", 
                     valueMember: "resImageId",
                     autoDropDownHeight : true,
                     theme:"metro"
				});
				$("#osType").jqxDropDownList('insertAt', { label:td.os ,value:td.os},0);
			},
			failure : function() {
			}
		});
  	 	
  	 	
  	 	var app = new codeModel({width:220, dropDownWidth:220,autoDropDownHeight:false, dropDownHeight:180});
  	 	app = new codeModel({width:220, dropDownWidth:220,autoDropDownHeight:false, dropDownHeight:180});
	    app.getCustomCode('rescomuteid', '/biz/getSubmitIDCUserResources', 'resSetType', 'resSetSid', false, 'POST', {'resType': 0, 'ownerId': order.owner});
	    app = new codeModel({width:220, dropDownWidth:220,autoDropDownHeight:false, dropDownHeight:180});
		app.getCustomCode('vLanIDO', '/biz/getSubmitIDCUserResources', 'resSetType', 'resSetSid', false, 'POST', {'resType': 1, 'ownerId': order.owner});
		me.initIpAddressDropDownList('wanIps', $('#vLanIDO').val());
		$("#wanTr1").show();
  	 	
    	var winSize = {width:700, height:400};
    	$("#idctabwindow").jqxWindow(winSize);
		$("#idctabwindow").jqxWindow('open');
    }
    
    function changeVmSpec(td){
    	$("#approveIDCMgtForm").hide();
    	$("#idcChangeMgtForm").show();
    	
    	var me = new idcOrderMgtModel();
    	$("#idctabwindow .title").html("IDC订单虚拟机规格变更");
    	var order ;
    	Core.AjaxRequest({
      		 url : ws_url + "/rest/idc/order/find/all",
   			 type:'post',
   			 params: {orderSid:td.orderSid},
   			 async:false,
   			 callback : function (data) {
   				 order = data[0];
   			}
   		 });
    	Core.AjaxRequest({
			type : 'get',
			url : ws_url + "/rest/approve/platform/approve/adjustinstance/" + td.instanceSid + "/0",
			callback : function(data) {
				var specs = data.specs;
				var griddataAdapter = new $.jqx.dataAdapter(specs);
				griddataAdapter.dataBind();
			    $("#idc-orderBaseSpecGrid").jqxGrid({source: griddataAdapter});
			    
			    var diskSpecs = data.diskSpecs;
				var diskdataAdapter = new $.jqx.dataAdapter(diskSpecs);
				diskdataAdapter.dataBind();
			    $("#idcOrder-diskSpec").jqxGrid({source: diskdataAdapter});
			}
		 });
    	
    	$("#vmOpType").val(td.vmOpType);
    	if(td.vmOpType=="1"){
    		$("#instanceId").val(td.hpInstanceSid);
    		$("#processType").val("01");
    	}else{
    		$("#processType").val("12");
    		$("#instanceId").val(td.instanceSid);
    	}
    	$("#orderId").val(td.orderSid);
    	$("#orderDetailId").val(td.orderDetailSid);
   	 	
//  	var app = new codeModel({width:220, dropDownWidth:220,autoDropDownHeight:false, dropDownHeight:180});
//	    app.getCustomCode('rescomuteidSpec', '/biz/getSubmitUserResources', 'resSetType', 'resSetSid', false, 'POST', {'resType': 0, 'ownerId': order.owner});
	    
    	var winSize = {width:700, height:430};
    	$("#idctabwindow").jqxWindow(winSize);
		$("#idctabwindow").jqxWindow('open');
    }
    
    
    function searchIdcOrder(){
    	var me = new idcOrderMgtModel();
    	me.searchObj.transactionid = $("#idc-transaction-id").val();
    	me.searchObj.idcOrderType = $("#idc-order-type").val()=="全部"?"":$("#idc-order-type").val();
    	me.searchObj.status = $("#idc-order-status").val()=="全部"?"":$("#idc-order-status").val();
    	me.initIdcOrderDataGrid();
    	me.searchIdcOrderInfo();
    }
    
    
  