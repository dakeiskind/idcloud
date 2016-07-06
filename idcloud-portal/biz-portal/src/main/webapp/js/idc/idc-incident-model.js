var idcIncidentTicketModel = function() {
	var me = this;
	this.items = ko.observableArray();
	this.detailsItems = ko.observableArray();
	this.searchObj = {
			idcIncidentIdLike:"",
			staffNameLike:"",
			status:""
	};
	this.searchDetailsObj = {
			
	};
	// 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	this.initInput = function() {
		// 初始化查询组件
		$("#idc-incident-id").jqxInput({placeHolder : "",height : 22,width : 120,minLength : 1,theme : currentTheme});
		$("#idc-incident-staffName").jqxInput({placeHolder : "",height : 22,width : 120,minLength : 1,theme : currentTheme});
		var codesearch = new codeModel({width:120,autoDropDownHeight:true});
		codesearch.getCommonCode("idc-incident-status","IDC_INCIDENT_STATUS",true);
		$("#idc-incident-button").jqxButton({width: '50px',height:'25px',theme : currentTheme});
	};
	
	// 验证
	 // 初始化验证规则
    this.initValidator = function(){
    	$('#feedbackIncidentVmForm').jqxValidator({
            rules: [
                    	// 处理结果
//                      { input: '#feedback-handle-result', message: '不能为空', action: 'keyup, blur', rule: 'required' },
//                      { input: '#feedback-handle-result', message: '处理结果不能超过256个字符', action: 'keyup, blur', rule: 'length=1,256' },
                     
	                  // 处理意见
                      { input: '#feedback-handle-opinion', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#feedback-handle-opinion', message: '处理意见不能超过512个字符', action: 'keyup, blur', rule: 'length=1,512' },
                      
                      // 处理人名字
                      { input: '#feedback-handle-staffName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#feedback-handle-staffName', message: '处理人名称不能超过16个字符',  action: 'keyup, blur', rule: 'length=1,16' },
                      
                      // 处理人电话
                      { input: '#feedback-handle-tel', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#feedback-handle-tel', message: '处理人名称不能超过20个字符',  action: 'keyup, blur', rule: 'length=1,20' },

                   ]
    	});
    	
    	$('#feedbackIncidentVmForm').on('validationSuccess', function (event) {
	   		 var feedback = Core.parseJSON($("#feedbackIncidentVmForm").serializeJson());
	   		 Core.AjaxRequest({         
					url : ws_url + "/rest/idc/incident/feedback",
					params :feedback,
					callback : function (data) {
						searchIncidentTicket();
						$("#feedbackIncidentVmWindow").jqxWindow('close');
				    }
			});
	   });
    	
    };
    
    // 用户数据
    this.searchIncidentTicketInfo = function(){
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/idc/incident",
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
 			    $("#incidentTicketGrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    	 
    	 // 故障单号详情数据
     	 Core.AjaxRequest({
  			url : ws_url + "/rest/idc/incident/details",
  			type:'post',
  			params:me.searchDetailsObj,
  			async:false,
  			callback : function (data) {
  				var sourcedatagrid ={
 			              datatype: "json",
 			              localdata: data
  			    };
  				 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid, { autoBind: true });
  			    detailsItems = dataAdapter.records;
  			}
  		 });
    };
    var nestedGrids = new Array();
    
	// 初始化订单datagrid的数据源
	this.initIncidentTicketDatagrid = function() {
        var initrowdetails = function (index, parentElement, gridElement, record) {
	            var id = record.idcIncidentId.toString();
	            var grid = $($(parentElement).children()[0]);
	            nestedGrids[index] = grid;
	            var filtergroup = new $.jqx.filter();
	            var filtervalue = id;
	            var filtercondition = 'equal';
	            var filter = filtergroup.createfilter('stringfilter', filtervalue, filtercondition);
	            var detailsbyid = [];
	            for (var m = 0; m < detailsItems.length; m++) {
	                var result = filter.evaluate(detailsItems[m]["idcIncidentId"]);
	                if (result)
	                	detailsbyid.push(detailsItems[m]);
	            }
	            var detailssource = { 
	            	datatype: "json",
	                localdata: detailsbyid
	            };
	            var nestedGridAdapter = new $.jqx.dataAdapter(detailssource);
	            if (grid != null) {
	            	grid.jqxGrid({
	        			source: nestedGridAdapter,
	        			width:"95%",
	        			autowidth: false,
	        			autoheight: true,
	        			columnsresize: true,
	        			rowsheight: 30,
	                    enablehover: false,
	        			theme:currentTheme,
	        			localization : gridLocalizationObj,
	        			columns: [
	        			          { text: '故障虚机名称', datafield: 'hpVmName',width:200},
	        			          { text: '故障类型', datafield: 'incidentTypeName' ,width:250},
	        			          { text: '故障描述', datafield: 'incidentDesc'},
	        			          { text: '处理意见', datafield: 'comments'},
	        			          { text: '状态', datafield: 'statusName' ,width:80},
	        			          { text: '操作', width:80,  datafield: '',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
	        			        	  var td = grid.jqxGrid('getrowdata', row);
	        			        	  return "<div class='customButton' style='margin-top:4px;margin-left:10px;' onclick='popHandleIncidentVmWindow("+JSON.stringify(td)+")'>处理</div>";
	        			             }
	        			          }
	        			   ]
	        		});
	            }
        }
		
		// 查询出order值
		$("#incidentTicketGrid").jqxGrid({
            width: "100%",
			theme: currentTheme,
            columnsresize: true,
            pageable: true, 
            pagesize: 10, 
            autoheight: true,
            autowidth: false,
            sortable: true,
            rowdetails: true,
            rowsheight: 35,
            initrowdetails: initrowdetails,
            rowdetailstemplate: { rowdetails: "<div id='grid' style='margin: 10px;'></div>", rowdetailsheight: 220, rowdetailshidden: true },
            selectionmode:"none",
			localization : gridLocalizationObj,
			columns : [ 
			            {text : '故障单号',datafield : 'idcIncidentId'}, 
			            {text : '申报人名字',datafield : 'staffName'}, 
			            {text : '申报人电话',datafield : 'tel'}, 
			            {text : '申报人邮箱',datafield : 'email'}, 
			            {text : '处理时限',datafield : 'duration'}, 
			            {text : '结果确认',datafield : 'resultConfirmedName',cellsrenderer:function(row, columnfield, value, defaulthtml, columnproperties){
		                	  return "<div style='padding-top:10px;'><a class='datagrid-link' onclick='popIdcFeedBackInfoWindow("+row+")' href='#'>&nbsp;&nbsp;"+value+"</a></div>";
		                 }},
//			            {text : '用户满意度',datafield : 'csi'},
//			            {text : '评价备注',datafield : 'bz'},
			            {text : '状态',datafield : 'statusName'},
			            { text: '操作', width:150,  datafield: '',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
  			        	        var data = $("#incidentTicketGrid").jqxGrid('getrowdata', row);
  			        	        if(data.status == "01"){
  			        	        	return "<a class='customButton' style='margin-top:6px;margin-left:10px;' onclick='popHandleAllIncidentVmWindow("+JSON.stringify(data)+")'>全部处理</a>";
  			        	        }else if(data.status =='02'){
  			        	        	return "<a class='customButton' style='margin-top:6px;margin-left:10px;' onclick='popHandleAllIncidentVmWindow("+JSON.stringify(data)+")'>全部处理</a>";
  			        	        }else if(data.status =='03'){
  			        	        	return "<a class='customButton' style='margin-top:6px;margin-left:10px;' onclick='popHandleAllIncidentVmWindow("+JSON.stringify(data)+")'>全部处理</a>&nbsp;<a class='customButton' style='margin-top:6px;margin-left:10px;' onclick='popFeedbackWindow("+JSON.stringify(data)+")'>反馈</a>";
  			        	        }else if(data.status =='04'){
  			        	        	return "<a class='customButton' style='margin-top:6px;margin-left:10px;' onclick='popFeedbackWindow("+JSON.stringify(data)+")'>反馈</a>";
  			        	        }
  			        	  		
  			               }
  			            }
			],
			showtoolbar : false
		});
	};
	
	// 初始化弹出window
	this.initPopWindow = function() {
		// 初始化订单详情Window
		$("#viewIdcFeedbackInfoWindow").jqxWindow({
			width : 300,
			height : 200,
			resizable : false,
			isModal : true,
			autoOpen : false,
			cancelButton : $("#viewIncidentCancelButton"),
			theme: currentTheme,
			modalOpacity : 0.3,
			initContent : function() {
				// 初始化取消按钮
				$('#viewIncidentCancelButton').jqxButton({width : '50px',height : '25px',theme :currentTheme});
			}
		});
		
		// 处理故障虚拟机window
		$("#handleIncidentVmWindow").jqxWindow({
			width : 400,
			height : 170,
			resizable : false,
			isModal : true,
			autoOpen : false,
			cancelButton : $("#handleIncidentCancelButton"),
			theme: currentTheme,
			modalOpacity : 0.3,
			initContent : function() {
				// 初始化取消按钮
				$("#handle-comments").jqxInput({placeHolder: "", height:100, width: 300, minLength: 1,maxLength:512,theme:currentTheme});
				$('#handleIncidentSaveButton').jqxButton({width : '50px',height : '25px',theme :currentTheme});
				$('#handleIncidentCancelButton').jqxButton({width : '50px',height : '25px',theme :currentTheme});
			}
		});
		
		// 反馈window
		$("#feedbackIncidentVmWindow").jqxWindow({
			width : 400,
			height : 220,
			resizable : false,
			isModal : true,
			autoOpen : false,
			cancelButton : $("#feedbackIncidentCancelButton"),
			theme: currentTheme,
			modalOpacity : 0.3,
			initContent : function() {
				// 初始化取消按钮
//				$("#feedback-handle-result").jqxInput({placeHolder: "", height:30, width: 200, minLength: 1,theme:currentTheme});
				$("#feedback-handle-opinion").jqxInput({placeHolder: "", height:50, width: 280, minLength: 1,maxLength:512,theme:currentTheme});
				$("#feedback-handle-completeDate").jqxDateTimeInput({ height:23, width: 170,theme:currentTheme, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
				$("#feedback-handle-staffName").jqxInput({placeHolder: "", height:23, width: 170, minLength: 1,theme:currentTheme});
				$("#feedback-handle-tel").jqxInput({placeHolder: "", height:23, width: 170, minLength: 1,theme:currentTheme});
				
				$('#feedbackIncidentSaveButton').jqxButton({width : '50px',height : '25px',theme :currentTheme});
				$('#feedbackIncidentCancelButton').jqxButton({width : '50px',height : '25px',theme :currentTheme});
			}
		});
	};

};

/** 查询订单 */
 function searchIncidentTicket(){
    var me = new idcIncidentTicketModel();
	me.searchObj.idcIncidentIdLike = $("#idc-incident-id").val();
	me.searchObj.staffNameLike = $("#idc-incident-staffName").val(); 
	me.searchObj.status = $("#idc-incident-status").val()=="全部"?"":$("#idc-incident-status").val();
	me.initIncidentTicketDatagrid();
	me.searchIncidentTicketInfo();
};

// 弹出idc反馈信息
function popIdcFeedBackInfoWindow(row){
	 // 获取虚拟机故障单的pojo信息
	 var data = $('#incidentTicketGrid').jqxGrid('getrowdata', row);
	 $("#idcIncidentId").html(data.idcIncidentId);
	 $("#resultConfirmedName").html(data.resultConfirmedName);
	 $("#confirmedTime").html(data.confirmedTime);
	 $("#csi").html(data.csi);
	 $("#bz").html(data.bz);
	 var windowW = $(window).width(); 
     var windowH = $(window).height(); 
     $("#viewIdcFeedbackInfoWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-200)/2 } });
     $("#viewIdcFeedbackInfoWindow").jqxWindow('open');
}

// 弹出处理故障虚拟机window
function popHandleIncidentVmWindow(obj){
	// 清空
	$("#handle-idc-incident-id").val(null);
	$("#handle-comments").val(null);
	$("#handle-hpVmId").val(null);
	
	$("#handle-hpVmId").val(obj.hpVmId);
	$("#handle-comments").val(obj.comments);
	
	var windowW = $(window).width(); 
    var windowH = $(window).height(); 
    $("#handleIncidentVmWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-150)/2 } });
    $("#handleIncidentVmWindow").jqxWindow('open');
}

// 提交处理故障虚拟机意见
function submitHandleInfo(){
	 var handleInfo = Core.parseJSON($("#handleIncidentVmForm").serializeJson());
	 
	 // 点击故障虚拟机处理按钮
	 if($("#handle-idc-incident-id").val() == null || $("#handle-idc-incident-id").val() == ""){
		// 故障单号详情数据
	 	 Core.AjaxRequest({
				url : ws_url + "/rest/idc/incident/details/handle",
				type:'post',
				params:handleInfo,
				async:false,
				callback : function (data) {
					 // 刷新
					 searchIncidentTicket();
					 $("#handleIncidentVmWindow").jqxWindow('close');
				}
		 });
	 }else{
		 // 去掉hpVmId字段
		 delete handleInfo["hpVmId"];
		 // 故障单号详情数据
	 	 Core.AjaxRequest({
				url : ws_url + "/rest/idc/incident/details/handle/all",
				type:'post',
				params:handleInfo,
				async:false,
				callback : function (data) {
					 // 刷新
					 searchIncidentTicket();
					 $("#handleIncidentVmWindow").jqxWindow('close');
				}
		 });
	 }
}

// 全部处理故障虚拟机意见
function popHandleAllIncidentVmWindow(obj){
	// 清空
	$("#handle-idc-incident-id").val(null);
	$("#handle-comments").val(null);
	$("#handle-hpVmId").val(null);
	
	$("#handle-idc-incident-id").val(obj.idcIncidentId);
	$("#handle-comments").val(obj.comments);
	
	var windowW = $(window).width(); 
    var windowH = $(window).height(); 
    $("#handleIncidentVmWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-170)/2 } });
    $("#handleIncidentVmWindow").jqxWindow('open');
}

// 反馈
function popFeedbackWindow(obj){
	
	$("#feedback-idc-incident-id").val(obj.idcIncidentId);
	$("#feedback-idc-servcode").val(obj.idcServcode);
	$("#feedback-idc-transaction-id").val(obj.idcTransactionId);
	$("#feedback-handle-result").val("success");
	$("#feedback-handle-userName").val(obj.userName);
	$("#feedback-handle-password").val(obj.password);
	$("#feedback-handle-staffName").val(currentUser.account);
	$("#feedback-handle-tel").val(currentUser.mobile);
	$("#feedback-handle-opinion").val(obj.handleCommets.replace(/\|/g, "</br>"));
	
	
	var windowW = $(window).width(); 
    var windowH = $(window).height(); 
    $("#feedbackIncidentVmWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-220)/2 } });
    $("#feedbackIncidentVmWindow").jqxWindow('open');
}

// 提交反馈信息
function submitFeedbackInfo(){
	$('#feedbackIncidentVmForm').jqxValidator('validate');
}

// demo
function submitdemo(){
//	var data = {  
//			  "userName": "test",
//			  "password": "test",	
//			  "idcIncidentId": "11111123",
//			  "idcServcode": "20014",
//			  "resultConfirmed": "2",
//			  "csi": "5",
//			  "idcTransactionId": "tcasdfasdfsdfwerwer333332asdf"
//	};
//	
//	 // 故障单号详情数据
//	 Core.AjaxRequest({
//			url : ws_url + "/rest/idc/vm/handle/confirmed",
//			type:'post',
//			params:data,
//			async:false,
//			callback : function (data) {
//				
//			}
//	 });
	
	
//	var data = {
//			"userName":"test",
//			"password":"test",
//			"idcIncidentId" : "11111123",
//			"duration" : "2015-02-07 12:11:23",
//			"staffName" : "李国才",
//			"tel" : "15120757948",
//			"email" : "text@hp.com",
//			"idcTransactionId" : "sss34343434343",
//			"idcServcode" : "20001",
//			"incidentList":[
//			     {"hpVmId":"1212","incidentType":"9000003","incidentDesc":"我想飞的跟高是滴是滴"},
//			     {"hpVmId":"2323","incidentType":"9000002","incidentDesc":"哈哈哈是"},
//			     {"hpVmId":"565656","incidentType":"9000004","incidentDesc":"贾斯丁贾斯丁"}
//			]
//	};
//	
//	 // 故障单号详情数据
//	 Core.AjaxRequest({
//			url : ws_url + "/rest/idc/vm/incident",
//			type:'post',
//			params:data,
//			async:false,
//			callback : function (data) {
//				console.log(JSON.stringify(data));
//			}
//	 });
}
