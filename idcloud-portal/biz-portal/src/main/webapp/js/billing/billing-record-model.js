var billingRecordModel = function () {
	var me = this;
	this.items = ko.observableArray();
	this.detailsItems = ko.observableArray();
	this.searchObj = {
			accountSid:"",
			billingRecordIdLike : null,
			startTime : null,
			endTime : null
	};
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	  $("#search-billingRecord-number").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
        
    	  $("#search-billingRecord-dateFrom").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss',showFooter:true,clearString:'清除',todayString:'今天'});
    	  $("#search-billingRecord-dateTo").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd 23:59:59',showFooter:true,clearString:'清除',todayString:'今天'});
    	  $("#search-billingRecord-button").jqxButton({ width: '60px',height:"25px",theme:currentTheme});
    };
    
    // 用户数据
    this.searchBillingRecordInfo = function(accountSid){
    	 Core.AjaxRequest({
    		url : ws_url + "/rest/billingRecord/billingAcountCrdList",
 			type:'post',
 			params:{
 				billingAccountSid : accountSid
 			},
 			async:false,
 			callback : function (data) {
 				console.log(JSON.stringify(data));
 				me.items(data);
 				var sourcedatagrid ={
			              datatype: "json",
			              localdata: me.items
 			    };
 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#billingRecordGrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
	// 初始化订单datagrid的数据源
	this.initBillingRecordDatagrid = function() {
		// 查询出billingRecord值
		$("#billingRecordGrid").jqxGrid({
            width: "100%",
			theme: currentTheme,
            columnsresize: true,
            pageable: true, 
            pagesize: 10, 
            autoheight: true,
            autowidth: false,
            sortable: true,
            rowdetails: false,
//            rowsheight: 35,
            selectionmode:"none",
			localization : gridLocalizationObj,
			columns : [ 
			            {text : '流水号',datafield : 'tradeNo', width: 240},
			            {text : '充值渠道',datafield : 'opTypeName',width:100},
			            {text : '金额',datafield : 'opAmount'},
			            {text : '付款时间',datafield : 'updatedDt'},
			            {text : '创建时间',datafield : 'createdDt'}, 
			            {text : '充值状态',datafield : 'statusName',width:80}
			],
			showtoolbar : false
		});
		
	};
	
	// 初始化弹出window
    this.initPopWindow = function(){
    	 // 账单window
		 $("#billingBalanceDetailWindow").jqxWindow({
               width: 800, 
               height:430,
               resizable: false,  
               isModal: true, 
               autoOpen: false, 
               cancelButton: $("#billingRecordCancel"), 
               theme: currentTheme,
               modalOpacity: 0.3,
               initContent:function(){
               	$("#billingRecordCancel").jqxButton({ width: '50',height:"25",theme:"metro"});
              }
          });
    	
    };
	 
};
  
  function searchBillingRecord(){
	 var me = new billingRecordModel();
	 me.searchObj.accountSid = $("#billing-record-accountSid").val();
	 me.searchObj.flowNoLike = ($("#search-billingRecord-number").val() == "") ? null : $("#search-billingRecord-number").val(); 
	 me.searchObj.fromDate = ($("#search-billingRecord-dateFrom").val() == "") ? null : $("#search-billingRecord-dateFrom").val();
	 me.searchObj.toDate = ($("#search-billingRecord-dateTo").val() == "") ? null : $("#search-billingRecord-dateTo").val();
	 
     var beginTime = $("#search-billingRecord-dateFrom").val();
	 var endTime = $("#search-billingRecord-dateTo").val();
	 var beginTimes = beginTime.substring(0, 10).split('-');
	 var endTimes = endTime.substring(0, 10).split('-');
	 beginTime = beginTimes[1] + '-' + beginTimes[2] + '-' + beginTimes[0] + ' ' + beginTime.substring(10, 19);
	 endTime = endTimes[1] + '-' + endTimes[2] + '-' + endTimes[0] + ' ' + endTime.substring(10, 19);
	 var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
	 if (a < 0) {
    	 Core.alert({
				type : "error",
				message:"您选择的起止时间有误，请重新选择！"
			});
     } else if (a > 0) {
    	 Core.AjaxRequest({
    	 		url : ws_url + "/rest/billingRecord/platform/billingRecordList",
    				type:'post',
    				params: me.searchObj,
    				async:false,
    				callback : function (data) {
    					me.items(data);
    					var sourcedatagrid ={
    				              datatype: "json",
    				              localdata: me.items
    				    };
    				    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
    				    $("#billingRecordGrid").jqxGrid({source: dataAdapter});
    				}
    			 });
     }else{
    	 Core.AjaxRequest({
 	 		url : ws_url + "/rest/billingRecord/platform/billingRecordList",
 				type:'post',
 				params: me.searchObj,
 				async:false,
 				callback : function (data) {
 					me.items(data);
 					var sourcedatagrid ={
 				              datatype: "json",
 				              localdata: me.items
 				    };
 				    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 				    $("#billingRecordGrid").jqxGrid({source: dataAdapter});
 				}
 			 });
     }
  }