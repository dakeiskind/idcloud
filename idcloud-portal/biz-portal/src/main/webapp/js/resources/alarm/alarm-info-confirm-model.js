// 新增主机 
var confirmAlarmInfoModel = function () {
	// 设置修改值
	this.setAlarmInfoForm = function(data){
		 // 告警sid
	    $("#defineConsoleAlarmSid").val(data.alarmDataSid);
	    // 告警确认人
	    $("#confirmUser").val(currentUser.account);
    };
	
	 // 初始化验证规则
    this.initValidator = function(){
    	$('#defineAlarmInfoForm').jqxValidator({
            rules: [
                      { input: '#alarmDefineContent', message: '内容不能超过128个字符', action: 'keyup, blur', rule: 'length=1,128' }
                   ]
    	});
    	// 确定验证信息成功
    	$('#defineAlarmInfoForm').on('validationSuccess', function (event) {
	    		 var alarm = Core.parseJSON($("#defineAlarmInfoForm").serializeJson());
	    		 alarm.status="02",
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/alarms/update",
	 				params :alarm,
	 				callback : function (data) {
	 					$("#defineAlarmInfoWindow").jqxWindow('close');
	 					var me=new resAlarmeModel();
	 					me.searchAlarmConsoleInfo();
	 			    },
	 			    failure:function(data){
	 			    	$("#defineAlarmInfoWindow").jqxWindow('close');
	 			    }
	 			});
    	 });
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
    };
 };
 
 
	/** 提交确认告警信息 */
	function DefineAlarmInfoSubmit(){
		$('#defineAlarmInfoForm').jqxValidator('validate');
	};
  