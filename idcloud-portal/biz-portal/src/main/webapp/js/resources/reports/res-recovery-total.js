var resourceModel = function() {
	var me = this;
	this.items = ko.observableArray();
	this.searchObj = {
		fbizSid : null,
		statTime : null,
		finishDate : null
		
	};
	// 用户数据
	this.searchResourceReport = function() {
		var that = this;
		Core.AjaxRequest({
			url: ws_url + "/rest/reports/resource/recovery/report",
			type : 'post',
			params : me.searchObj,
			callback : function(data) {
				me.items(data);
 				var sourcedatagrid ={
		              datatype: "json",
		              localdata: me.items
 			    };
 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#resourceDatagrid").jqxGrid({source: dataAdapter});
 			    
 			    //回收统计图
 			    var bizName = [];
 			    var cpuCt = [];
 			    var memCt = [];
 			    var stCt = [];
 			    var pubIipCt = [];
 			    var ctTol = [];
 			    var priIpCt = [];
 			    if (data.length > 0){
 			    	for (var i =0; i < data.length; i++){
 			    		bizName.push(data[i].fbizText);
 			    		cpuCt[i] = data[i].cpuRecovery;
 			    		memCt[i] = data[i].memRecovery;
 			    		stCt[i]  = data[i].stTBRecovery;
 			    		pubIipCt[i]  = data[i].pubIp;
 			    		priIpCt[i] = data[i].privateIp;
 			    	}
 			    	ctTol[0] = {name: 'CPU（核）', data: cpuCt, color: '#77A753'};
 			    	ctTol[1] = {name: '内存（G）', data: memCt, color: '#4A72BA'};
 			    	ctTol[2] = {name: '存储（T）', data: stCt,  color: '#F3BF1D'};
 			    	ctTol[3] = {name: '外网IP回收数',  data: pubIipCt,  color: '#AFBFAD'};
 			    	ctTol[4] = {name: '内网IP回收数',  data: priIpCt,  color: '#A90900'};
 			    }
 			   that.recoveryTotalChart(bizName, ctTol);
			}
		});
		
	};

	// 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	this.initInput = function() {
		// 初始化查询组件
		$("#search-resource-button").jqxButton({
			width : '60px',
			height : '25px',
			theme : currentTheme
		});

		var now = new Date().getFullYear();
		var year = [];
		for(var i = now;i>=now-10;i--){
			year.push(i);
		}
		
		var month = [];
		for(var i = 1;i<=12;i++){
			month.push(i);
		}
		
		$("#start-year").jqxDropDownList({
			placeHolder: "请选择...",
            source: year,
            width: 100,
            height:22,
            theme:"metro"
		});
		var startYear;
		var startYear2;
		$('#start-year').on('open', function (event){   
			startYear = $('#start-year').val();
		});
		$('#start-year').on('close', function (event){
			startYear2 = $('#start-year').val();
			if(startYear != startYear2){
				var year2 = [];
				var end = $('#start-year').val();
				for(var i = now;i>=end;i--){
					year2.push(i);
				}
				$("#finish-year").jqxDropDownList({
					placeHolder: "请选择...",
					source: year2,
		            width: 100,
		            height:22,
		            theme:"metro",
		            selectedIndex:0
				});
			}
		});
		
		$("#finish-year").jqxDropDownList({
			placeHolder: "请选择...",
			source: year,
            width: 100,
            height:22,
            theme:"metro"
		});
		$('#finish-year').on('change', function (event){
			var sy = $('#start-year').val();
			var fy = $('#finish-year').val();
			if(sy==fy){
				var sm = $('#start-month').val();
				var month2=[];
				for(var i=sm;i<=12;i++){
					month2.push(i);
				}
				$("#finish-month").jqxDropDownList({
					placeHolder: "请选择...",
		            source: month2,
		            width: 80,
		            height:22,
		            theme:"metro",
		            selectedIndex:-1
				});
			}else{
				$("#finish-month").jqxDropDownList({
					placeHolder: "请选择...",
		            source: month,
		            width: 80,
		            height:22,
		            theme:"metro",
		            selectedIndex:-1
				});
			}
		});
		
		$("#start-month").jqxDropDownList({
			placeHolder: "请选择...",
            source: month,
            width: 80,
            height:22,
            theme:"metro"
		});
		
		var startM;
		var startM2;
		$('#start-month').on('open', function (event){
			startM = $('#start-month').val();
		});
		$('#start-month').on('close', function (event){
			startM2 = $('#start-month').val();
			var sy = $('#start-year').val();
			var fy = $('#finish-year').val();
			if(sy==fy){
				if(startM != startM2){
					var month2 = [];
					for(var i = startM2;i<=12;i++){
						month2.push(i);
					}
					$("#finish-month").jqxDropDownList({
						placeHolder: "请选择...",
			            source: month2,
			            width: 80,
			            height:22,
			            theme:"metro",
			            selectedIndex:-1
					});
				}
			}
		});
		
		$("#finish-month").jqxDropDownList({
			placeHolder: "请选择...",
            source: month,
            width: 80,
            height:22,
            theme:"metro"
		});
		
		// 初始化下拉列表框
		var codesearch = new codeModel({
			width : 120,
			autoDropDownHeight : true
		});
		codesearch.getCustomCode("search-fbizs", "/bizType/findFbizType", "name",
				"bizSid", true, "POST", {});
//		codesearch.getCustomCode("search-sbizs", "/bizType/findSbizType", "name",
//				"bizSid", true, "POST", {});
//		$('#search-fbizs').on('change', function (event){
//			console.log(event.args.item.value);
//			if ($('#search-fbizs').val() != "全部"){
//				codesearch.getCustomCode("search-sbizs", "/bizType/findSbizType", "name",
//						"bizSid", true, "POST", {"parentBizSid":event.args.item.value});
//			}
//		});

	};
	// 初始化用户datagrid的数据源
	this.initResourceDatagrid = function() {
		$("#resourceDatagrid").jqxGrid({
			width: "100%",
			theme: currentTheme,
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
				  { text: '业务属性', datafield: 'fbizText',width:'20%'},
				  { text: 'CPU（核）', datafield: 'cpuRecovery',width:'15%',cellsalign:'right'},
				  { text: '内存（GB）', datafield: 'memRecovery',width:'15%',cellsalign:'right'},
				  { text: '虚拟存储（GB）', datafield: 'stRecovery',width:'15%',cellsalign:'right'},
				  { text: '外网IP回收数', datafield: 'pubIp',width:'15%',cellsalign:'right'},
				  { text: '内网IP回收数', datafield: 'privateIp',width:'20%',cellsalign:'right'}
              ],
              showtoolbar: true,
              // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
//                  var refreshBtn = $("<div><a id='jqxRefreshResBtn' data-bind='click: changeFres' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>发起调整&nbsp;&nbsp;</a></div>");
                  var excelReportBtn = $("<div><a id='excelReportBtn' data-bind='click: excelResourceReport' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-report-1'></i>导出&nbsp;&nbsp;</a></div>");
//                  var pdfReportBtn = $("<div><a id='pdfReportBtn' data-bind='click: pdfResourceReport' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-report-1'></i>导出PDF&nbsp;&nbsp;</a></div>");
//                  var mailReportBtn = $("<div><a id='mailReportBtn' data-bind='click: emailFres' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-report-1'></i>发送邮件&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
//                  container.append(refreshBtn);
                  container.append(excelReportBtn);
//                  container.append(pdfReportBtn);
//                  container.append(mailReportBtn);
              }
          });
		
		// 控制按钮是否可用
//		$("#resourceDatagrid").on('rowselect', function (event) {
//  		  var args = event.args; 
//  		  var index = args.rowindex;
//  		  var data = $('#jqxRefreshResBtn').jqxGrid('getrowdata', index);
////  		  $("#jqxRefreshResBtn").jqxButton({width: '60',theme:"metro",height: '18px', disabled: false });
//        });
		
//		$("#jqxRefreshResBtn").jqxButton({width: '60',theme:"metro",height: '18px', disabled: true });
		$("#excelReportBtn").jqxButton({width: '60',theme:"metro",height: '18px', disabled: false });
//		$("#pdfReportBtn").jqxButton({width: '60',theme:"metro",height: '18px', disabled: false });
//		$("#mailReportBtn").jqxButton({width: '60',theme:"metro",height: '18px', disabled: false });

	};

	//回收统计线图
	 this.recoveryTotalChart = function(bizName, ctTol){
			$('#resRecoveryTotal').highcharts({
	            title: {
	                text: ''
	            },
	            subtitle: {
	                text: ''
	            },
	            xAxis: {
//	                 categories: sampleData.time
					categories: bizName
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: ''
	                }
	            },
	            tooltip: {
	                shared: true,
	                useHTML: true
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            legend: {
	                borderWidth: 0
	            },
	            credits:{ 
	                enabled:false
	            },
	            exporting:{ 
	                enabled:false
	            },
	            plotOptions: {
	                line: {
	                    dataLabels: {
	                        enabled: true
	                    },
	                    enableMouseTracking: true
	                }
	            },
	            series: ctTol
//	            	[{
//	                name: 'CPU（核）',
//					data: [60, 53, 40],
//	                color:'#77A753'	    
//	            }, {
//	                name: '内存（G）',
//	                data: [200, 230, 270],
//	                color:'#4A72BA'	    
//	            }, {
//	                name: '存储（T）',
//	                data: [500, 540, 550],
//	                color:'#F3BF1D'	   
//	            }, {
//	                name: 'IP回收数',
//	                data: [2, 4, 5],
//	                color:'#FFBF1D'
//	            }]
	        });
		 };

};
var reportsBindModel = function(resource) {
	/** ***********************资源报表绑定事件******************************* */
	/** 查询资源报表 */
	this.searchResource = function() {
		resource.searchObj.fbizSid = $("#search-fbizs").val()=="全部"?"":$("#search-fbizs").val();
//		resource.searchObj.sbizSid = $("#search-sbizs").val()=="全部"?"":$("#search-sbizs").val();

		var startYear = $("#start-year").val();
		var startMonth = $("#start-month").val();
		if (startYear!="" && startYear!=null){
			if(startYear!="0"){
				if(startMonth!="0"){
					resource.searchObj.statTime = new Date(startYear,startMonth-1,1);
				}else{
					resource.searchObj.statTime = new Date(startYear,0,1);
				}
			}
		}

		var finishYear = $("#finish-year").val();
		var finishMonth = $("#finish-month").val();
		if (finishYear!="" && finishYear!=null){
			if(finishYear!="0"){
				if(finishMonth!="0"){
					resource.searchObj.finishDate = new Date(finishYear,finishMonth-1,1);
				}else{
					resource.searchObj.finishDate = new Date(finishYear,0,1);
				}
			}
		}
		
//		var startYear = $("#start-year").val();
//		var startMonth = $("#start-month").val();
//		if (startYear!="" && startYear!=null){
//			if(startYear!="0"){
//				if(startMonth!="0"){
//					resource.searchObj.statTime = new Date(startYear,startMonth-1,1);
//				}else{
//					resource.searchObj.statTime = new Date(startYear,0,1);
//				}
//			}
//			else {
//				resource.searchObj.statTime = null;
//			}
//		}
//		
//		var finishYear = $("#finish-year").val();
//		var finishMonth = $("#finish-month").val();
//		if (finishYear!="" && finishYear!=null){
//			if(finishYear!="0"){
//				if(finishMonth!="0"){
//					resource.searchObj.finishDate = new Date(finishYear,finishMonth-1,1);
//				}else{
//					resource.searchObj.finishDate = new Date(finishYear,0,1);
//				}
//			}
//			else {
//				resource.searchObj.finishDate = null;			
//			}
//		}
		
		resource.searchResourceReport();
	};
    
	// 導出Excel
	this.excelResourceReport = function() {
		// 判断按钮是否可用
      	var ok =  $("#excelReportBtn").jqxButton("disabled");
      	if (!ok) {
      		resource.searchObj.fbizSid = $("#search-fbizs").val()=="全部"?"":$("#search-fbizs").val();
//    		resource.searchObj.sbizSid = $("#search-sbizs").val()=="全部"?"":$("#search-sbizs").val();

    		var startYear = $("#start-year").val();
    		var startMonth = $("#start-month").val();
    		if (startYear!="" && startYear!=null){
    			if(startYear!="0"){
    				if(startMonth!="0"){
    					resource.searchObj.statTime = new Date(startYear,startMonth-1,1);
    				}else{
    					resource.searchObj.statTime = new Date(startYear,0,1);
    				}
    			}
    		}

    		var finishYear = $("#finish-year").val();
    		var finishMonth = $("#finish-month").val();
    		if (finishYear!="" && finishYear!=null){
    			if(finishYear!="0"){
    				if(finishMonth!="0"){
    					resource.searchObj.finishDate = new Date(finishYear,finishMonth-1,1);
    				}else{
    					resource.searchObj.finishDate = new Date(finishYear,0,1);
    				}
    			}
    		}
    		
//    		var startYear = $("#start-year").val();
//    		var startMonth = $("#start-month").val();
//    		if(startYear!=""&&startYear!=0){
//    			if(startMonth!=""&&startMonth!=0){
//    				resource.searchObj.startDate = new Date(startYear,startMonth-1,1);
//    			}else{
//    				resource.searchObj.startDate = new Date(startYear,0,1);
//    			}
//    		}
//    		var finishYear = $("#finish-year").val();
//    		var finishMonth = $("#finish-month").val();
//    		if(finishYear!=""&&finishYear!=0){
//    			if(finishMonth!=""&&finishMonth!=0){
//    				resource.searchObj.finishDate = new Date(finishYear,finishMonth-1,1);
//    			}else{
//    				resource.searchObj.finishDate = new Date(finishYear,0,1);
//    			}
//    		}
    		
    		resource.searchResourceReport();
    		window.open(ws_url + "/rest/reports/resource/recovery/total/excel/" + JSON.stringify(resource.searchObj));
      	}
	};
	
	// 導出PDF
	this.pdfResourceReport = function() {
		// 判断按钮是否可用
      	var ok =  $("#pdfReportBtn").jqxButton("disabled");
      	if (!ok) {
      		resource.searchObj.fbizSid = $("#search-fbizs").val()=="全部"?"":$("#search-fbizs").val();
//    		resource.searchObj.sbizSid = $("#search-sbizs").val()=="全部"?"":$("#search-sbizs").val();

    		var startYear = $("#start-year").val();
    		var startMonth = $("#start-month").val();
    		if(startYear!=""&&startYear!=0){
    			if(startMonth!=""&&startMonth!=0){
    				resource.searchObj.startDate = new Date(startYear,startMonth-1,1);
    			}else{
    				resource.searchObj.startDate = new Date(startYear,0,1);
    			}
    		}
    		var finishYear = $("#finish-year").val();
    		var finishMonth = $("#finish-month").val();
    		if(finishYear!=""&&finishYear!=0){
    			if(finishMonth!=""&&finishMonth!=0){
    				resource.searchObj.finishDate = new Date(finishYear,finishMonth-1,1);
    			}else{
    				resource.searchObj.finishDate = new Date(finishYear,0,1);
    			}
    		}
    		
    		resource.searchResourceReport();
			window.open(ws_url + "/rest/reports/resource/recovery/total/pdf/" + JSON.stringify(resource.searchObj));
      	}
	};
	
	this.emailFres = function() {
		// 判断按钮是否可用
      	var ok =  $("#mailReportBtn").jqxButton("disabled");
      	if (!ok) {
      		Core.AjaxRequest({
    			url : ws_url + "/rest/reports/resource/recoveryemail",
    			type : 'post',
    			callback : function(data) {
    			}
    		});
      	}
	};
	

};
