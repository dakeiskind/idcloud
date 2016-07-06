var alarmBindModel = function(conso,rule){

	/** 确认告警信息 */
	this.defineAlarmInfo = function(){
		// 清空以前确认信息
		$("#alarmDefineContent").val("");
		var rowindex = $('#alarmConsoledatagrid').jqxGrid('getselectedrowindex');
		var ok =  $("#defineAlarmInfo").jqxButton("disabled");
    	if(rowindex >= 0 && !ok){

    		    var data = $('#alarmConsoledatagrid').jqxGrid('getrowdata', rowindex);
    		    // 告警sid
    		    $("#defineConsoleAlarmSid").val(data.alarmDataSid);
    		    // 告警确认人
    		    $("#confirmUser").val(currentUser.account);

    		    //告警规则sid
    		    $("#alarmRuleSid").val(data.alarmRuleSid);
    		    $("#alarmId").val(data.alarmId);
    		    $("#alarmTime").val(data.alarmTime);
    		    $("#alarmLevel").val(data.alarmLevel);
    		    $("#alarmType").val(data.alarmType);
    		    $("#alarmTarget").val(data.alarmTarget);
    		    $("#title").val(data.title);
    		    $("#content").val(data.content);

    		    // 设置window的位置
    		    var windowW = $(window).width();
    	    	var windowH = $(window).height();
    	    	$("#defineAlarmInfoWindow").jqxWindow({ position: { x: (windowW-380)/2, y: (windowH-230)/2 } });
    	    	$("#defineAlarmInfoWindow").jqxWindow('open');
    	}
	};

	/** 提交确认告警信息 */
	this.DefineAlarmInfoSubmit = function(){
		$('#defineAlarmInfoForm').jqxValidator('validate');
	};

	/** 消除告警信息 */
	this.clearAlarmInfo = function(){
		var rowindex = $('#alarmConsoledatagrid').jqxGrid('getselectedrowindex');
		var ok =  $("#clearAlarmInfo").jqxButton("disabled");
    	if(rowindex >= 0 && !ok){
    		var data = $('#alarmConsoledatagrid').jqxGrid('getrowdata', rowindex);
			var alarm = new Object;
			alarm.alarmDataSid = data.alarmDataSid;
			alarm.status = '03';
	    	Core.confirm({
				title:"请选择",
				message:"您确定要消除该告警信息吗？",
				yes:"确定",
				confirmCallback:function(){
					Core.AjaxRequest({
						url : ws_url + "/rest/alarms/setStatus",
						params :alarm,
						callback : function (data) {
								//更新菜单
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
							conso.searchAlarmConsoleInfo();
						},
						failure:function(data){}
					});
				}
			});
    	}
	};

	/** 条件查询告警信息 */
	this.searchAlarmConsole = function(){
		conso.searchObj.alarmType = $("#search-alarm-type").val()=="全部"?"":$("#search-alarm-type").val();
		conso.searchObj.status = $("#search-alarm-status").val()=="全部"?"":$("#search-alarm-status").val();
		if($("#alarmFromTime").val() != null && $("#alarmFromTime").val() !== ""){
			conso.searchObj.alarmTimeFromDate = $("#alarmFromTime").val()=="全部"?"":$("#alarmFromTime").val()+" 00:00:00";
		}
		if($("#alarmToTime").val() != null && $("#alarmToTime").val() !== ""){
			conso.searchObj.alarmTimeToDate = $("#alarmToTime").val()=="全部"?"":$("#alarmToTime").val()+" 23:59:59";
		}
		conso.searchAlarmConsoleInfo();
	};

	/*******************告警规则*******************/
	/** 弹出新增告警规则window */
	this.addAlarmRuleInfo = function(){
		var windowW = $(window).width();
	    var windowH = $(window).height();
	    // 清空先前新增残留的数据
	    $("#add-title").val("");
        $("#add-accumulateCount").val("");
        $("#add-alarmThreshold").val("");
        $("#add-content").val("");

	     $("#addAlarmRuleInfoWindow").jqxWindow({ position: { x: (windowW-550)/2, y: (windowH-250)/2 } });
	     $("#addAlarmRuleInfoWindow").jqxWindow('open');
	};

	/** 提交新增告警规则 */
	this.ruleAlarmInfoSubmit =function(){
		$('#addAlarmRuleInfoForm').jqxValidator('validate');
	};

	/** 弹出编辑告警规则window */
	this.editAlarmRuleInfo = function(){
		var rowindex = $('#alarmRuledatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#alarmRuledatagrid').jqxGrid('getrowdata', rowindex);

    		// 初始化下拉框
    		var ruleConsole = new codeModel({width:150,autoDropDownHeight:true});
        	ruleConsole.getCommonCode("edit-alarmLevel","ALARM_LEVEL");
	    	ruleConsole.getCommonCode("edit-alarmType","ALARM_TYPE");
	    	ruleConsole.getCommonCode("edit-alarmKpi","ALARM_KPI");
	    	ruleConsole.getCommonCode("edit-operator","CHECK_OPTR");

    		// 给编辑页面赋值
    		$("#editAlarmRuleSid").val(data.alarmRuleSid);
    		rule.setEditAlarmRuleForm(data);

    		// 设置编辑
    		var windowW = $(window).width();
    	    var windowH = $(window).height();
    	    $("#editAlarmRuleInfoWindow").jqxWindow({ position: { x: (windowW-550)/2, y: (windowH-250)/2 } });
    	    $("#editAlarmRuleInfoWindow").jqxWindow('open');
    	}
	};

	/** 提交编辑告警规则信息 */
	this.editRuleAlarmInfoSubmit =function(){
		$('#editAlarmRuleInfoForm').jqxValidator('validate');
	};

	/** 删除告警规则 */
	this.removeAlarmRuleInfo = function(){
		var rowindex = $('#alarmRuledatagrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#alarmRuledatagrid').jqxGrid('getrowdata', rowindex);
    		 var alarmRule = new Object;
    		 alarmRule.alarmRuleSid = data.alarmRuleSid;
		    	Core.confirm({
					title:"提示",
					message:"确定要删除该告警规则吗？",
					yes:"确定",
					confirmCallback:function(){
						Core.AjaxRequest({
			 				url : ws_url + "/rest/alarms/rule/delete/"+data.alarmRuleSid,
			 				type:"get",
			 				callback : function (data) {
			 					rule.searchAlarmRuleInfo();
			 			    },
			 			    failure:function(data){

			 			    }
			 			});
					}
			});
    	}
	},

	/** 同步告警规则 */
	this.syncAlarmInfo = function(){
		Core.AjaxRequest({
				url : ws_url + "/rest/alarms/sync",
				type:"post",
				params:{},
				async:true,
				callback : function (data) {
					if(data.result == "success"){
						// 刷新datagrid
						Core.alert({
							message:"告警信息同步完成！",
							type:"info",
							callback:function(){
								conso.searchAlarmConsoleInfo();
							}
						});
					}
			    },
			    failure:function(data){

			    }
			});
	},

	/** 条件查询告警规则 */
	this.searchAlarmRule = function(){

		rule.searchObj.alarmLevel = $("#search-alarm-rule-level").val()=="全部"?"":$("#search-alarm-rule-level").val();
		rule.searchObj.alarmType = $("#search-alarm-rule-type").val()=="全部"?"":$("#search-alarm-rule-type").val();
		rule.searchObj.alarmKpi = $("#search-alarm-rule-kpi").val()=="全部"?"":$("#search-alarm-rule-kpi").val();
		//alert(JSON.stringify(rule.searchObj));
    	rule.searchAlarmRuleInfo();

	};
};

  
  