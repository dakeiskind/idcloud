var resAlarmeModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		alarmLevel: "", 
	    		alarmType: "", 
	    		status:"",
	    		alarmTimeFromDate:"",
	    		alarmTimeToDate:"",
	    		searchType:resTopologyType,
	    		searchSid:resTopologySid
		};
	    // 用户数据
	    this.searchAlarmConsoleInfo = function(){
//	    	 Core.AjaxRequest({
//	 			url : ws_url + "/rest/topologys/platform/getHostAlarm",
//	 			type:'post',
//	 			params:me.searchObj,
//	 			async:false,
//	 			callback : function (data) {
//	 				me.items(data);
//	 				var sourcedatagrid ={
// 			              datatype: "json",
// 			              localdata: me.items
//	 			    };
//	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
//	 			    $("#alarmConsoledatagrid").jqxGrid({source: dataAdapter});
//	 			}
//	 		 });
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	    	var alarmConsole = new codeModel({width:150,autoDropDownHeight:true});
	    	alarmConsole.getCommonCode("search-alarm-level","ALARM_LEVEL",true);
	    	alarmConsole.getCommonCode("search-alarm-type","ALARM_TYPE",true);
	    	alarmConsole.getCommonCode("search-alarm-status","ALARM_STATUS",true);
	    	$("#alarmFromTime").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
		    $("#alarmToTime").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd',height: 22, allowNullDate: true,value:null,theme:currentTheme});
	        $("#search-alarm-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	    	
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initAlarmConsoledatagrid = function(){
	          $("#alarmConsoledatagrid").jqxGrid({
	              width: "100%",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '告警标题', datafield: 'title', width:180,pinned: true},
	                  { text: '告警内容', datafield: 'content',width:180,pinned: true},
	                  { text: '目标对象', datafield: 'alarmTarget',width:250},
	                  { text: '告警时间', datafield: 'alarmTime',width:140},
	                  { text: '告警等级', datafield: 'alarmLevelName',width:60},
	                  { text: '告警类型', datafield: 'alarmTypeName',width:80},
	                  { text: '告警状态', datafield: 'statusName',width:60},
	                  { text: '告警确认人', datafield: 'confirmUser',width:80},
	                  { text: '告警确认内容', datafield: 'confirmContent',width:150},
	                  { text: '告警确认时间', datafield: 'confirmTime',width:140},
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var approveDetail = $("<div><a id='defineAlarmInfo' onClick='defineAlarmInfo()'>&nbsp;&nbsp;<i class='icons-blue icon-check'></i>确认警告信息&nbsp;&nbsp;</a></div>");
	                  var historyApprove = $("<div><a id='clearAlarmInfo' style='margin-left:-1px' data-bind='click:clearAlarmInfo'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-1'></i>消除告警信息&nbsp;&nbsp;</a></div>");
	               
	                  toolbar.append(container);
	                  container.append(approveDetail);  
	                  container.append(historyApprove);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#alarmConsoledatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#alarmConsoledatagrid').jqxGrid('getrowdata', index);
	    		  if(data.status == "02"){
	    			  $("#defineAlarmInfo").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
		   			  $("#clearAlarmInfo").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	    		  }else{
	    			  $("#defineAlarmInfo").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
		   			  $("#clearAlarmInfo").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	    		  }
	          });
	    	  
	    	  $("#defineAlarmInfo").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
   			  $("#clearAlarmInfo").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	    };
	  

  };
  /** 确认告警信息 */
 function defineAlarmInfo(){
		// 清空以前确认信息
		$("#alarmDefineContent").val("");
		var rowindex = $('#alarmConsoledatagrid').jqxGrid('getselectedrowindex');
		var ok =  $("#defineAlarmInfo").jqxButton("disabled");
		if(rowindex >= 0 && !ok){
		    var data = $('#alarmConsoledatagrid').jqxGrid('getrowdata', rowindex);
		    var confirm=new confirmAlarmInfoModel();
		    confirm.initPopWindow();
		    confirm.setAlarmInfoForm(data);
		    // 设置window的位置
		    var windowW = $(window).width(); 
	    	var windowH = $(window).height(); 
	    	$("#defineAlarmInfoWindow").jqxWindow({ position: { x: (windowW-650)/2, y: (windowH-260)/2 } });
	    	$("#defineAlarmInfoWindow").jqxWindow('open');
	}	    
};
/** 条件查询告警信息 */
function searchAlarmConsole(){
	var conso=new resAlarmeModel();
	conso.searchObj.alarmLevel = $("#search-alarm-level").val()=="全部"?"":$("#search-alarm-level").val();
	conso.searchObj.alarmType = $("#search-alarm-type").val()=="全部"?"":$("#search-alarm-type").val();
	conso.searchObj.status = $("#search-alarm-status").val()=="全部"?"":$("#search-alarm-status").val();
	conso.searchObj.alarmTimeFromDate = $("#alarmFromTime").val()=="全部"?"":$("#alarmFromTime").val();
	conso.searchObj.alarmTimeToDate = $("#alarmToTime").val()=="全部"?"":$("#alarmToTime").val();
	conso.searchObj.searchType = resTopologyType ;
	conso.searchObj.searchSid = resTopologySid ;
	Core.AjaxRequest({
			url : ws_url + "/rest/alarms",
			type:'post',
			params:conso.searchObj,
			async:false,
			callback : function (data) {
				conso.items(data);
				var sourcedatagrid ={
		              datatype: "json",
		              localdata: conso.items
			    };
			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
			    $("#alarmConsoledatagrid").jqxGrid({source: dataAdapter});
			}
		 });
};
  