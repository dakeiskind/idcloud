var alarmConsoleModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		alarmLevel: "", 
	    		alarmType: "", 
	    		status:"",
	    		alarmTimeFromDate:"",
	    		alarmTimeToDate:""
		};
	    // adaptor alarm列表数据，只包括未处理告警数据
	    this.searchAlarmListInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/alarms",
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
	 			    $("#alarmConsoledatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    // 查询告警列表数据，包括已确认数据和未处理数据
	    this.searchAlarmConsoleInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/alarms",
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
	 			    $("#alarmConsoledatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	    	var alarmConsole = new codeModel({width:150,autoDropDownHeight:true});
//	    	alarmConsole.getCommonCode("search-alarm-level","ALARM_LEVEL",true);
	    	alarmConsole.getCommonCode("search-alarm-type","ALARM_TYPE",true);
	    	alarmConsole.getCommonCode("search-alarm-status","ALARM_STATUS",true);
	    	$("#alarmFromTime").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
		    $("#alarmToTime").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd',height: 22, allowNullDate: true,value:null,theme:currentTheme});
	        $("#search-alarm-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	    	
	    };
	    this.setEditUserForm = function(data){
	    	
	    };
	    // 初始化验证规则
	    this.initValidator = function(){
	    	$('#defineAlarmInfoForm').jqxValidator({
				rules : [ {
					input : '#alarmDefineContent',
					message : '告警确认内容不能为空',
					action : 'keyup, blur',
					rule : function(input, commit) {
						if (input.val().trim() == "") {
							return false;
						}
						return true;
					}
				} ]
	    	});
	    	//确定验证信息成功
	    	$('#defineAlarmInfoForm').on('validationSuccess', function (event) {
		    		 var alarm = JSON.parse($("#defineAlarmInfoForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/alarms/update",
		 				params :alarm,
		 				callback : function (data) {
		 					me.searchAlarmListInfo();
 							$("#defineAlarmInfoWindow").jqxWindow('close');

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
						    			window.parent.$("#menuContent").find("b:contains('告警管理')").parents("li").addClass("liShow");
						    		}
						    	});
						    }
		 			    },
		 			    failure:function(data){
		 			    	$("#defineAlarmInfoWindow").jqxWindow('close');
		 			    }
		 			});
	    	});
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
	                  { text: '告警标题', datafield: 'title'},
	                  { text: '告警内容', datafield: 'content'},
	                  { text: '目标对象', datafield: 'alarmTargetName',width:120},
	                  { text: '告警时间', datafield: 'alarmTime',width:140},
	                  { text: '告警等级', datafield: 'alarmLevelName',width:60},
	                  { text: '告警类型', datafield: 'alarmTypeName',width:80},
	                  { text: '告警状态', datafield: 'statusName',width:60},
	                  { text: '告警确认人', datafield: 'confirmUser',width:80},
	                  { text: '告警确认内容', datafield: 'confirmContent',width:150},
	                  { text: '告警确认时间', datafield: 'confirmTime',width:140}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var approveDetail = $("<div><a id='defineAlarmInfo' data-bind='click:defineAlarmInfo'>&nbsp;&nbsp;<i class='icons-blue icon-check'></i>确认警告信息&nbsp;&nbsp;</a></div>");
	                  var historyApprove = $("<div><a id='clearAlarmInfo' style='margin-left:-1px' data-bind='click:clearAlarmInfo'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-1'></i>消除告警信息&nbsp;&nbsp;</a></div>");
	                  var syncAlarm = $("<div><a id='syncAlarmInfo' style='margin-left:-1px' data-bind='click:syncAlarmInfo'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-ccw'></i>同步&nbsp;&nbsp;</a></div>");
	               
	                  toolbar.append(container);
	                  container.append(approveDetail);  
	                  container.append(historyApprove);
	                  container.append(syncAlarm);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#alarmConsoledatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#alarmConsoledatagrid').jqxGrid('getrowdata', index);
	    		  if(data.status == "02"){
	    			  $("#defineAlarmInfo").jqxButton({disabled: true });
		   			  $("#clearAlarmInfo").jqxButton({disabled: false});
	    		  }
	    		  else if(data.status == "03"){
	    			  $("#defineAlarmInfo").jqxButton({disabled: true });
		   			  $("#clearAlarmInfo").jqxButton({disabled: true});
	    		  }
	    		  else{
	    			  $("#defineAlarmInfo").jqxButton({disabled: false });
		   			  $("#clearAlarmInfo").jqxButton({disabled: true});
	    		  }
	          });
	    	  
	    	  $("#defineAlarmInfo").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
   			  $("#clearAlarmInfo").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
   			  $("#syncAlarmInfo").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	    };
	    // 初始化弹出window
	    this.initPopWindow = function(){
			$("#defineAlarmInfoWindow").jqxWindow({
	                width: 400, 
	                height:190,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                theme: currentTheme,
	                cancelButton: $("#consoleInfoCancel"), 
	                modalOpacity: 0.3,
	                initContent:function(){
	                	$("#alarmDefineTime").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd',height: 22,theme:currentTheme});
	                	$("#confirmUser").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme,disabled: true});
	                	$("#alarmDefineContent").jqxInput({placeHolder: "", height: 50, width: 250, minLength: 1,theme:currentTheme});
	                	$("#consoleInfoSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	                	$("#consoleInfoCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	                }
	         });

//			$('#addUserWindow').on('close', function (event) {
//				removeValidatorInfo();
//			});
//			$("#editUserWindow").jqxWindow({
//                width: 500, 
//                height:260,
//                resizable: false,  
//                isModal: true, 
//                autoOpen: false, 
//                cancelButton: $("#editCancel"), 
//                modalOpacity: 0.3           
//             });
//	         $("#changePasswdWindow").jqxWindow({
//	                width: 300, 
//	                height:170,
//	                resizable: false,  
//	                isModal: true, 
//	                autoOpen: false, 
//	                cancelButton: $("#passwdCancel"), 
//	                modalOpacity: 0.3           
//	             });
	    };

  };

  