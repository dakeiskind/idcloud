var resourceModel = function() {
	var me = this;
	this.items = ko.observableArray();
	this.fresSid = "";
	this.resSidStr = "";
	this.dataRes = null;
	this.dataReturn = null;
	this.searchObj = {
		fbizSid : "",
		sbizSid : "",
		statusName : ""
	};
	
	// 用户数据
	this.searchResourceReport = function() {
		Core.AjaxRequest({
			url: ws_url + "/rest/freeres/findFresByParams",
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
			}
		});
	};

	// 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	this.initInput = function() {
		// 初始化查询组件
		$("#search-button").jqxButton({
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
		
		var data = [{fieldText:"全部",valueText:"0"},
		            {fieldText:"待处理",valueText:"1"},
		            {fieldText:"待确认",valueText:"2"},
		            {fieldText:"回收中",valueText:"3"},
		            {fieldText:"已回收",valueText:"4"}
		            ];
		
		var source ={
	             datatype: "json",
	             datafields: [
	                 { name: "fieldText" },
	                 { name: "valueText" }
	             ],
	             localdata:data
	         };
		var dataAdapter = new $.jqx.dataAdapter(source);
		$("#search-status").jqxDropDownList({
			placeHolder: "请选择...",
            selectedIndex: -1, 
            source: dataAdapter,
            displayMember: "fieldText",
            valueMember: "valueText",
            width: 70,
            dropDownHeight: 120,
            height: 22,
            theme:"metro"
      });

//		var week = ["第一周","第二周","第三周","第四周"];
		
		$("#start-year").jqxDropDownList({
			placeHolder: "请选择...",
            source: year,
            width: 70,
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
		            width: 70,
		            height:22,
		            theme:"metro",
		            selectedIndex:0
				});
			}
		});
		
		$("#finish-year").jqxDropDownList({
			placeHolder: "请选择...",
			source: year,
            width: 70,
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
		            width: 70,
		            height:22,
		            theme:"metro",
		            selectedIndex:-1
				});
			}else{
				$("#finish-month").jqxDropDownList({
					placeHolder: "请选择...",
		            source: month,
		            width: 70,
		            height:22,
		            theme:"metro",
		            selectedIndex:-1
				});
			}
		});
		
		$("#start-month").jqxDropDownList({
			placeHolder: "请选择...",
            source: month,
            width: 70,
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
			            width: 70,
			            height:22,
			            theme:"metro",
			            selectedIndex:-1
					});
				}
			}
			var coun = getWeek(sy,startM2);
			var week1 = [];
			for(var i=1;i<=coun;i++){
				week1.push("第"+i+"周");
			}
			$("#start-week").jqxDropDownList({
				placeHolder: "请选择...",
	            source: week1,
	            width: 70,
	            height:22,
	            dropDownHeight: 100,
	            theme:"metro",
	            selectedIndex:-1
			});
		});
		
		$("#finish-month").jqxDropDownList({
			placeHolder: "请选择...",
            source: month,
            width: 70,
            height:22,
            theme:"metro"
		});
		
		$('#finish-month').on('change', function (event){
			var sy = $('#start-year').val();
			var fy = $('#finish-year').val();
			var sm = $('#start-month').val();
			var fm = $('#finish-month').val();
			if(sy==fy&&sm==fm){
				var sindex = $("#start-week").jqxDropDownList('getSelectedIndex'); 
				var week2=[];
				var coun = getWeek(fy,fm);
				for(var i=parseInt(sindex)+1;i<=coun;i++){
					week2.push(("第"+i+"周"));
				}
				$("#finish-week").jqxDropDownList({
					placeHolder: "请选择...",
		            source: week2,
		            width: 70,
		            height:22,
		            theme:"metro",
		            selectedIndex:-1
				});
			}else{
				var week2=[];
				var coun = getWeek(fy,fm);
				for(var i=1;i<=coun;i++){
					week2.push("第"+i+"周");
				}
				$("#finish-week").jqxDropDownList({
					placeHolder: "请选择...",
		            source: week2,
		            width: 70,
		            height:22,
		            theme:"metro",
		            selectedIndex:-1
				});
			}
		});
		
		$("#start-week").jqxDropDownList({
			placeHolder: "请选择...",
            width: 70,
            height:22,
            dropDownHeight: 100,
            theme:"metro"
		});
		var sw;
		var fw;
		$('#start-week').on('open', function (event){
			sw = $("#start-week").val();
		});
		$('#start-week').on('close', function (event){
			var sy = $('#start-year').val();
			var fy = $('#finish-year').val();
			var sm = $('#start-month').val();
			var fm = $('#finish-month').val();
			var fw = $("#start-week").val();
			if(sy==fy&&sm==fm){
				if(sw!=fw){
					var week2=[];
					var coun = getWeek(fy,fm);
					var sindex = $("#start-week").jqxDropDownList('getSelectedIndex'); 
					for(var i=parseInt(sindex)+1;i<=coun;i++){
						week2.push("第"+i+"周");
					}
					$("#finish-week").jqxDropDownList({
						placeHolder: "请选择...",
			            source: week2,
			            width: 70,
			            height:22,
			            theme:"metro",
			            selectedIndex:-1
					});
				}
			}
		});
		
		$("#finish-week").jqxDropDownList({
			placeHolder: "请选择...",
            width: 70,
            height:22,
            dropDownHeight: 100,
            theme:"metro"
		});
		
		// 初始化下拉列表框
		var codesearch = new codeModel({
			width : 100,
			autoDropDownHeight : false,
			dropDownHeight : 150
		});
		codesearch.getCustomCode("search-fbizs", "/bizType/findFbizType", "name",
				"bizSid", true, "POST", {});
		codesearch.getCustomCode("search-sbizs", "/bizType/findSbizType", "name",
				"bizSid", true, "POST", {});
		$('#search-fbizs').on('change', function (event){
			var index = $("#search-fbizs").jqxDropDownList('getSelectedIndex');
			var param;
			if(index > 0){
				param = event.args.item.value;
			}else{
				param = -1;
			}
			codesearch.getCustomCode("search-sbizs", "/bizType/findSbizType", "name",
					"bizSid", true, "POST", {"parentBizSid":param});
			});
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
				  { text: '业务属性', datafield: 'fbizText',width:80},
                  { text: '项目名称', datafield: 'sbizText',width:100},
                  { text: '虚拟机名称', datafield: 'resName',width:100},
                  { text: '创建时间', datafield: 'statTime',width:80},
                  { text: 'CPU核数', datafield: 'CPU_CORES',cellsalign:'right',width:50},
                  { text: 'CPU使用率(%)', datafield: 'CPU_USAGE',cellsalign:'right',width:60},
                  { text: '内存(GB)', datafield: 'MEMORY',cellsalign:'right',width:50},
                  { text: '内存使用率(%)', datafield: 'MEM_USAGE',cellsalign:'right',width:60},
                  { text: '虚拟存储(GB)', datafield: 'STORAGE',cellsalign:'right',width:50},
                  { text: '虚拟存储使用量', datafield: 'ST_USED',cellsalign:'right',width:50},
                  { text: '存储使用率(%)', datafield: 'ST_USAGE',cellsalign:'right',width:60},
                  { text: '峰值流量(Mbps)', datafield: 'MAX_IO',cellsalign:'right',width:55},
                  { text: '均值流量(Mbps)', datafield: 'AVG_IO',cellsalign:'right',width:55},
                  { text: '公网IP', datafield: 'PUB_IP',width:100},
                  { text: '内网IP', datafield: 'PRIVAGE_IP',width:100},
                  { text: '最后发起时间', datafield: 'opTime',width:80},
                  { text: '发起人', datafield: 'starter',align: 'center',cellsalign:'center',width:70},
                  { text: '发起次数', datafield: 'startNum',cellsalign:'right',width:60},
                  { text: '状态', datafield: 'statusName',align: 'center',cellsalign:'center'}
              ],
              showtoolbar: true,
              // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var changeInfoBtn = $("<div><a id='changeInfoBtn' onClick='changeFres()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>发起调整&nbsp;&nbsp;</a></div>");
                  var excelReportBtn = $("<div><a id='excelReportBtn' data-bind='click: excelResourceReport' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-report-1'></i>导出&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
                  container.append(changeInfoBtn);
                  container.append(excelReportBtn);
              }
          });
		
		// 控制按钮是否可用
		$("#resourceDatagrid").on('rowselect', function (event) {
  		  var args = event.args; 
  		  var index = args.rowindex;
  		  var data = $('#resourceDatagrid').jqxGrid('getrowdata', index);
  		  
  		  if (data.STATUS == 1 || data.STATUS == 2){
  			  $("#changeInfoBtn").jqxButton({width: '60',theme:"metro",height: '18px', disabled: false });
  		  }
  		  else {
  			 $("#changeInfoBtn").jqxButton({width: '60',theme:"metro",height: '18px', disabled: true });
  		  }
        });
		
		$("#changeInfoBtn").jqxButton({width: '60',theme:"metro",height: '18px', disabled: true });
		$("#excelReportBtn").jqxButton({width: '60',theme:"metro",height: '18px', disabled: false });
	};
	
	//初始化磁盘列表
	this.initResourceDiskDatagrid = function() {
		var _ids = 0;
		$("#diskList").jqxGrid(
	        {
	            width: "90%",
	            theme: currentTheme,
	            pageable: false,
	            height: "160",
	            sortable: false,
	            altrows: true,
	            enabletooltips: true,
	            //选中模式
	            selectionmode: 'singlerow',
	            columns: [
	              { text: '磁盘名称', datafield: 'diskName', cellsalign: 'center',align: 'center', width: '35%', editable: false },
	              { text: '磁盘用途',  datafield: 'diskDesc', cellsalign: 'center', align: 'center', width: '35%', editable: false },
	              { text: '当前值',  datafield: 'curValue', align: 'center', cellsalign: 'right', width: '30%', columntype: 'numberinput', editable: false }
	            ]
	     });
	};
	
    // 初始化弹出window
    this.initPopWindow = function(){
    	var that = this;
		$("#changeInfoWindow").jqxWindow({
                width: 500, 
                height:400,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#Cancel"), 
                theme: currentTheme,
                modalOpacity: 0.3,
                initContent:function(){
                	 // 初始化调整配置页面组件
                	that.initResourceDiskDatagrid();
        			 
        	        $("#Save").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#Cancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
                }
         });
    };
};

//打开显示配置修改页面
function changeFres(){	
	var resourcemodel = new resourceModel();
	var ok =  $("#changeInfoBtn").jqxButton("disabled");
	if (ok) {
		return false;
	}
	
	//取得选中的闲置资源数据
	var dataRes = null;
	var rowindex = $('#resourceDatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		dataRes = $('#resourceDatagrid').jqxGrid('getrowdata', rowindex);
		
		//设置显示原来的数据
		$("#cpuCores").html(dataRes.CPU_CORES);
		$("#cpuUsage").html(dataRes.CPU_USAGE);
		$("#memorySize").html(dataRes.MEMORY);
		$("#memoryUsage").html(dataRes.MEM_USAGE);
		$("#storageSize").html(dataRes.STORAGE);
		$("#storageUsage").html(dataRes.ST_USAGE);
		$("#pubIp").html(dataRes.PUB_IP);
		$("#privateIp").html(dataRes.PRIVAGE_IP);
	}
	resourcemodel.dataRes = dataRes;
	resourcemodel.resSidStr = resourcemodel.dataRes.RES_SID;
	
	//初始化磁盘列表的原始数据
	 Core.AjaxRequest({
			url: ws_url + "/rest/freeres/findFreeDiskResByParams",
			type : 'post',
			params : {resName : resourcemodel.resSidStr},
			callback : function(data) {
				var sysDisk = "";
				var dataDisk = "";
				$.each(data, function (i, diskInfo) {
					if (diskInfo.instSpecName == "SYSTEM_DISK") {
						sysDisk = diskInfo.instSpecValue;
					}
					else if (diskInfo.instSpecName == "DATA_DISK") {
						if (diskInfo.instSpecValue == null || diskInfo.instSpecValue == ""){
							dataDisk = 0;
						}
						else {
							dataDisk = diskInfo.instSpecValue;
						}
					}
				});
				var data = [
					            {"diskName" : "system disk(G)",  "diskDesc" : "系统盘", "curValue" : sysDisk},
					            {"diskName" : "data disk(G)",  "diskDesc" : "外置存储盘", "curValue" : dataDisk}
					        ];
				var dataSource =
			     {
			         localdata: data,
			         datatype: "local",
			         datafields:
			         [
			             { name: 'diskName', type: 'string' },
			             { name: 'diskDesc', type: 'string' },
			             { name: 'curValue', type: 'number' }
			         ]
			     };
			    var dataAdapter = new $.jqx.dataAdapter(dataSource);
			    $("#diskList").jqxGrid({source: dataAdapter});
			}
		});


	// 初始化新增window位置
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#changeInfoWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-500)/2 } });
	$("#changeInfoWindow").jqxWindow('open');
}

//保存修改过的配置信息
function submitChangeInfo(){
	var data = null;
	var resourcemodel = new resourceModel();
	var rowindex = $('#resourceDatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		data = $('#resourceDatagrid').jqxGrid('getrowdata', rowindex);
		resourcemodel.fresSid = data.fresSid;
	}	
	resourcemodel.dataRes = data;
	var fresSidNew = resourcemodel.fresSid;
	
	//建议信息
	var reDesc = $("#recoveryDesc").val();

	var jsonStr = {fresSid : "", 
					content : ""
				};
	jsonStr.fresSid = fresSidNew;
	jsonStr.content = reDesc;
	if (reDesc == ""){
		Core.alert({
			title:"提示",
			message:"没有填写建议，请输入建议！",
			callback:function(){
			}
		});
		return false;
	}
//	alert(fresSidNew);
	 //保存调整的配置数据，并发送闲置回收邮件
	 Core.AjaxRequest({
			url: ws_url + "/rest/freeres/saveFreeResdescbyparams",
			type : 'post',
			params : jsonStr,
			callback : function(data) {
				searchResourceResh();
				$("#changeInfoWindow").jqxWindow('close');				
			}
	 });
}

//将字符串转换成json对象
function strToJson(str){ 
	var json = eval('(' + str + ')'); 
	return json; 
} 
function searchResourceResh() {
	var resource = new resourceModel();
	resource.searchObj.fbizSid = $("#search-fbizs").val()=="全部"?"":$("#search-fbizs").val();
	resource.searchObj.sbizSid = $("#search-sbizs").val()=="全部"?"":$("#search-sbizs").val();
	resource.searchObj.statusName = $("#search-status").val();
	var sy = $("#start-year").val();
	var sm = $("#start-month").val();
	var smIndex = $("#start-month").jqxDropDownList('getSelectedIndex');
	if (smIndex == -1){
		sm = "";
	}
	var sw = $("#start-week").jqxDropDownList('getSelectedIndex');
	if(sy!=""){
		if(sm!=""){
			if(sw!=-1){
				var week = $("#start-week").val();
				var weekNum = week.substring(1,2);
				var dateScope = getDateInfo(sy,sm,weekNum);
				resource.searchObj.statTime = dateScope.split(',')[0];
			}else{
				resource.searchObj.statTime = new Date(sy,sm,1);
			}
		}else{
			resource.searchObj.statTime = new Date(sy,1,1);
		}
	}
	var fy = $("#finish-year").val();
	var fm = $("#finish-month").val();
	var fmIndex = $("#finish-month").jqxDropDownList('getSelectedIndex');
	if (fmIndex == -1){
		fm = "";
	}
	var fw = $("#finish-week").jqxDropDownList('getSelectedIndex');
	if(fy!=""){
		if(fm!=""){
			if(fw!=-1){
				var week = $("#finish-week").val();
				var weekNum = week.substring(1,2);
				var dateScope = getDateInfo(fy,fm,weekNum);
				resource.searchObj.finishDate = dateScope.split(',')[1];
			}else{
				resource.searchObj.finishDate = new Date(fy,fm,1);
			}
		}else{
			resource.searchObj.finishDate = new Date(fy,1,1);
		}
	}
	resource.searchResourceReport();
};

var reportsBindModel = function(resource) {
	/** ***********************资源报表绑定事件******************************* */
	/** 查询资源报表 */
	this.searchResource = function() {
		resource.searchObj.fbizSid = $("#search-fbizs").val()=="全部"?"":$("#search-fbizs").val();
		resource.searchObj.sbizSid = $("#search-sbizs").val()=="全部"?"":$("#search-sbizs").val();
		var staIndex = $("#search-status").jqxDropDownList('getSelectedIndex');
		resource.searchObj.statusName = staIndex==-1 ? "" : $("#search-status").val();
//		alert(resource.searchObj.statusName);
		var sy = $("#start-year").val();
		var sm = $("#start-month").val();
		var smIndex = $("#start-month").jqxDropDownList('getSelectedIndex');
		if (smIndex == -1){
			sm = "";
		}
		var sw = $("#start-week").jqxDropDownList('getSelectedIndex');
		if(sy!=""){
			if(sm!=""){
				if(sw!=-1){
					var week = $("#start-week").val();
					var weekNum = week.substring(1,2);
					var dateScope = getDateInfo(sy,sm,weekNum);
					resource.searchObj.statTime = dateScope.split(',')[0];
				}else{
					resource.searchObj.statTime = new Date(sy,sm,1);
				}
			}else{
				resource.searchObj.statTime = new Date(sy,1,1);
			}
		}
		var fy = $("#finish-year").val();
		var fm = $("#finish-month").val();
		var fmIndex = $("#finish-month").jqxDropDownList('getSelectedIndex');
		if (fmIndex == -1){
			fm = "";
		}
		var fw = $("#finish-week").jqxDropDownList('getSelectedIndex');
		if(fy!=""){
			if(fm!=""){
				if(fw!=-1){
					var week = $("#finish-week").val();
					var weekNum = week.substring(1,2);
					var dateScope = getDateInfo(fy,fm,weekNum);
					resource.searchObj.finishDate = dateScope.split(',')[1];
				}else{
					resource.searchObj.finishDate = new Date(fy,fm,1);
				}
			}else{
				resource.searchObj.finishDate = new Date(fy,12,1);
			}
		}
		resource.searchResourceReport();
	};
    
	// 導出Excel
	this.excelResourceReport = function() {
		// 判断按钮是否可用
      	var ok =  $("#excelReportBtn").jqxButton("disabled");
      	if (!ok) {
      		resource.searchObj.fbizSid = $("#search-fbizs").val()=="全部"?"":$("#search-fbizs").val();
    		resource.searchObj.sbizSid = $("#search-sbizs").val()=="全部"?"":$("#search-sbizs").val();
    		var sy = $("#start-year").val();
    		var sm = $("#start-month").val();
    		var smIndex = $("#start-month").jqxDropDownList('getSelectedIndex');
    		if (smIndex == -1){
    			sm = "";
    		}
    		var sw = $("#start-week").jqxDropDownList('getSelectedIndex');
    		if(sy!=""){
    			if(sm!=""){
    				if(sw!=-1){
    					var week = $("#start-week").val();
    					var weekNum = week.substring(1,2);
    					var dateScope = getDateInfo(sy,sm,weekNum);
    					resource.searchObj.statTime = dateScope.split(',')[0];
    				}else{
    					resource.searchObj.statTime = new Date(sy,sm,1);
    				}
    			}else{
    				resource.searchObj.statTime = new Date(sy,1,1);
    			}
    		}
    		var fy = $("#finish-year").val();
    		var fm = $("#finish-month").val();
    		var fmIndex = $("#finish-month").jqxDropDownList('getSelectedIndex');
    		if (fmIndex == -1){
    			fm = "";
    		}
    		var fw = $("#finish-week").jqxDropDownList('getSelectedIndex');
    		if(fy!=""){
    			if(fm!=""){
    				if(fw!=-1){
    					var week = $("#finish-week").val();
    					var weekNum = week.substring(1,2);
    					var dateScope = getDateInfo(fy,fm,weekNum);
    					resource.searchObj.finishDate = dateScope.split(',')[1];
    				}else{
    					resource.searchObj.finishDate = new Date(fy,fm,1);
    				}
    			}else{
    				resource.searchObj.finishDate = new Date(fy,12,1);
    			}
    		}
    		resource.searchResourceReport();
    		window.open(ws_url + "/rest/reports/resource/fres/excel/" + JSON.stringify(resource.searchObj));
      	}
	};
};
	
	function getWeek(year,month){
		 var d = new Date();
	     // what day is first day
	     d.setFullYear(year, month-1, 1);
	     var w1 = d.getDay();
	     if (w1 == 0) w1 = 7;
	     // total day of month
	     d.setFullYear(year, month, 0);
	     var dd = d.getDate();
	     // first Monday
	     var d1;
	     if (w1 != 1) d1 = 7 - w1 + 2;
	     else d1 = 1;
	     return week_count = Math.ceil((dd-d1+1)/7);
	}
	
	//
	function getDateInfo(year,month,weekNum){
		var d = new Date();
	    d.setFullYear(year, month-1, 1);
	    var w1 = d.getDay();
	    if (w1 == 0) w1 = 7;
	    d.setFullYear(year, month, 0);
	    var dd = d.getDate();
	    var d1;
	    if (w1 != 1) d1 = 7 - w1 + 2;
	    else d1 = 1;
	    var monday = d1+weekNum*7;
	    var sunday = monday + 6;
	    var from = year+"-"+month+"-"+monday;
	    var to;
	    if (sunday <= dd) {
	        to = year+"-"+month+"-"+sunday;
	    } else {
	        d.setFullYear(year, month-1, sunday);
	        to = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
	    }
	    return from+","+to;
	}
