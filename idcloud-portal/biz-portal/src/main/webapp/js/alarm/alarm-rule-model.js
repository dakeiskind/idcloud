var alarmRuleModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		alarmLevel: "", 
	    		alarmType: "", 
	    		alarmKpi:"",
	    		checkOptr:""
		};
	    // 查询告警规则列表
	    this.searchAlarmRuleInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/alarms/rule",
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
	 			    $("#alarmRuledatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	    	var ruleConsole = new codeModel({width:150,autoDropDownHeight:true});
	    	ruleConsole.getCommonCode("search-alarm-rule-level","ALARM_LEVEL",true);
	    	ruleConsole.getCommonCode("search-alarm-rule-type","ALARM_TYPE",true);
	    	ruleConsole.getCommonCode("search-alarm-rule-kpi","ALARM_KPI",true);
	    	
	        $("#search-alarm-rule-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	    };
	    this.setEditAlarmRuleForm = function(data){
	    	console.log(JSON.stringify(data.operator));
	    	$("#edit-title").val(data.title);
	    	$("#edit-accumulateCount").val(data.accumulateCount);
	    	var threshold = data.alarmThreshold;
	    	if (data.alarmKpi == 'cpu_usage'){
	    		if (threshold.indexOf('%') > 0){
	    			threshold = threshold.substring(0,threshold.indexOf('%'));
	    		}
	    	}
	    	else if (data.alarmKpi == 'memory_usage'){
	    		if (threshold.indexOf('MB') > 0){
	    			threshold = threshold.substring(0,threshold.indexOf('MB'));
	    		}
	    	}
	    	$("#edit-alarmThreshold").val(threshold);
	    	$("#edit-content").val(data.content);
	    	$("#edit-alarmLevel").val(data.alarmLevel);
	    	$("#edit-alarmType").val(data.alarmType);
	    	$("#edit-alarmKpi").val(data.alarmKpi);
	    	$("#edit-operator").val(data.operator);
	    	$("#editRuleName").val(data.title);
	    	$("#editAlarmRuleCheckOptr").val(data.checkOptr);
	    	
	    };
	    // 初始化验证规则
	    this.initValidator = function(){
	    	$('#addAlarmRuleInfoForm').jqxValidator({
                rules: [
                          { input: '#add-title', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-title', message: '标题不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
                          //{ input: '#add-accumulateCount', message: '请必须输入数字', action: 'keyup, blur', rule: function (input, commit) {
                  	  	//		if(input.val().replace(/[^\d]/g,'')){
                  	  	//			return true;
                  	  	//		}else{
                  	  	//			return false;
                  	  	//		}
                  	  	//	}
		                  //},
                          { input: '#add-alarmThreshold', message: '告警阀值不能超过10个字符', action: 'keyup, blur', rule: 'length=1,10' },
                          { input: '#add-alarmThreshold', message: '请必须输入数字', action: 'keyup, blur', rule: function (input, commit) {
                	  			if(input.val().replace(/[^\d]/g,'')){
                	  				return true;
                	  			}else{
                	  				return false;
                	  			}
                	  		}
		                  },
                          { input: '#add-content', message: '告警内容不能超过128个字符', action: 'keyup, blur', rule: 'length=1,128' }
                       ]
	    	});
	    	
	    	$('#editAlarmRuleInfoForm').jqxValidator({
                rules: [
					{ input: '#edit-title', message: '不能为空', action: 'keyup, blur', rule: 'required' },
					{ input: '#edit-title', message: '标题不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
					{ input: '#edit-content', message: '告警内容不能超过128个字符', action: 'keyup, blur', rule: 'length=1,128' },
					{ input: '#edit-alarmThreshold', message: '告警阀值必须输入数字', action: 'keyup, blur', rule: function (input, commit) {
						if(/^([0-9]+)$/.test(input.val())){
							return true;
						}else{
							return false;
						}
					}
					},

				]
	    	});
	    	// 确定新增告警验证信息成功
	    	$('#addAlarmRuleInfoForm').on('validationSuccess', function (event) {
				var rule = JSON.parse($("#addAlarmRuleInfoForm").serializeJson());
				Core.AjaxRequest({
		 			url : ws_url + "/rest/alarms/rule/create",
		 			params :rule,
		 			callback : function (data) {
		 				me.searchAlarmRuleInfo();
 						$("#addAlarmRuleInfoWindow").jqxWindow('close');
					},
		 		    failure:function(data){
		 		    	$("#addAlarmRuleInfoWindow").jqxWindow('close');
					}
		 		});
	    	});
	    	
	    	// 确定编辑告警验证信息成功
	    	$('#editAlarmRuleInfoForm').on('validationSuccess', function (event) {
				var rule = JSON.parse($("#editAlarmRuleInfoForm").serializeJson());
				Core.AjaxRequest({
					url : ws_url + "/rest/alarms/rule/update",
					params :rule,
					callback : function (data) {
						me.searchAlarmRuleInfo();
						$("#editAlarmRuleInfoWindow").jqxWindow('close');
					},
					failure:function(data){
						$("#editAlarmRuleInfoWindow").jqxWindow('close');
					}
				});
	    	 });
	    };
	    // 初始化用户datagrid的数据源
	    this.initAlarmRuledatagrid = function(){
	          $("#alarmRuledatagrid").jqxGrid({
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
	                  { text: '告警内容', datafield: 'content',width:210}, 
	                  { text: '告警指标', datafield: 'alarmKpiName'},
	                  { text: '告警等级', datafield: 'alarmLevelName'},
	                  { text: '告警类型', datafield: 'alarmTypeName'},
	                  { text: '运算符', datafield: 'checkOptrName'},
	                  { text: '告警阀值', datafield: 'alarmThreshold'},
	                  { text: '触发规则', datafield: 'accumulateCount'},
	                  { text: '告警平台类型', datafield: 'platformTypeName'}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var approveDetail = $("<div><a id='addAlarmRule' data-bind='click:addAlarmRuleInfo'>&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
	                  var historyApprove = $("<div><a id='editAlarmRule' style='margin-left:-1px' data-bind='click:editAlarmRuleInfo'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var ruleRemove = $("<div><a id='removeAlarmRule' data-bind='click:removeAlarmRuleInfo' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(approveDetail);
	                  container.append(historyApprove);
	                  container.append(ruleRemove);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#alarmRuledatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#alarmRuledatagrid').jqxGrid('getrowdata', index);
	   			  $("#editAlarmRule").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	   			  $("#removeAlarmRule").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	          });
	    	  
	    	  $("#addAlarmRule").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
   			  $("#editAlarmRule").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
   			  $("#removeAlarmRule").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	    };
	    // 初始化弹出window
	    this.initPopWindow = function(){
			$("#addAlarmRuleInfoWindow").jqxWindow({
	                width: 550, 
	                height:250,
	                resizable: false,  
	                isModal: true, 
	                theme: currentTheme,
	                autoOpen: false, 
	                cancelButton: $("#ruleInfoCancel"), 
	                modalOpacity: 0.3,
	                initContent:function(){
	                	$("#add-title").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	                	$("#add-accumulateCount").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,maxLength:10,theme:currentTheme});
	                	$("#add-alarmThreshold").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	                	$("#add-content").jqxInput({placeHolder: "", height: 50, width: 300, minLength: 1,theme:currentTheme});
	                	
	                	var ruleConsole = new codeModel({width:150,autoDropDownHeight:true});
	                	ruleConsole.getCommonCode("add-alarmLevel","ALARM_LEVEL");
	        	    	ruleConsole.getCommonCode("add-alarmType","ALARM_TYPE");
	        	    	ruleConsole.getCommonCode("add-alarmKpi","ALARM_KPI");
	        	    	ruleConsole.getCommonCode("add-operator","CHECK_OPTR");
	                	
	                	$("#ruleInfoSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	                	$("#ruleInfoCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	                }
	         });
			$("#editAlarmRuleInfoWindow").jqxWindow({
                width: 550, 
                height:250,
                resizable: false,  
                isModal: true, 
                autoOpen: false,
                theme: currentTheme,
                cancelButton: $("#editRuleInfoCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	$("#edit-title").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
                	$("#edit-accumulateCount").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 0,theme:currentTheme});
                	$("#edit-alarmThreshold").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 0,theme:currentTheme});
                	$("#edit-content").jqxInput({placeHolder: "", height: 50, width: 300, minLength: 1,theme:currentTheme});
                	
                	$("#editRuleInfoSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
                	$("#editRuleInfoCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
                }
             });
	    };

  };

  

  