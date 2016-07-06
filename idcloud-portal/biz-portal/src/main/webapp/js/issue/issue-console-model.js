var issueConsoleModel = function() {
	var me = this;
	this.items = ko.observableArray();
	this.searchObj = {
		issuePriority : "",
		issueType : "",
		issueStatus : "",
		createdBy : "",
		createdDtFromDate : "",
		createdDtToDate : ""
	};
	// 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	this.initInput = function() {
		// 初始化查询组件
		var issueConsole = new codeModel({width : 150, autoDropDownHeight : true});
		issueConsole.getCommonCode("search-issue-priority", "ISSUE_PRIORITY", true);
		issueConsole.getCommonCode("search-issue-type", "ISSUE_TYPE", true);
		issueConsole.getCommonCode("search-issue-status", "ISSUE_STATUS", true);
		$("#search-created-by").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
		$("#createdDtFromDate").jqxDateTimeInput({
			width : '150px',
			culture : 'zh-CN',
			formatString : 'd',
			height : 22,
			allowNullDate : true,
			value : null,
			theme : currentTheme
		});
		$("#createdDtToDate").jqxDateTimeInput({
			width : '150px',
			culture : 'zh-CN',
			formatString : 'd',
			height : 22,
			allowNullDate : true,
			value : null,
			theme : currentTheme
		});
		$("#search-issue-button").jqxButton({
			width : '50px',
			height : '25px',
			theme : currentTheme
		});

		// 初始化弹出框
		me.initPopWindow();
		// 初始化组件验证规则
		me.initValidator();
		// 初始化datagrid
		me.initIssueConsoledatagrid();
		// 为datagrid赋值
		me.searchIssueConsoleInfo();
	};
	// 初始化验证规则
	this.initValidator = function() {
		$('#defineIssueInfoForm').jqxValidator({
			rules : [ {
				input : '#issueComment',
				message : '回复内容不能为空且不能含有双引号',
				action : 'keyup, blur',
				rule : function(input, commit) {
					if (/["]+/g.test(input.val())) {
						return false;
					}
					if (input.val().trim() == "") {
						return false;
					}
					return true;
				}
			} ]
		});
		// 确定验证信息成功
		$('#defineIssueInfoForm').on('validationSuccess',
			function(event) {
				var issue = Core.parseJSON($("#defineIssueInfoForm").serializeJson());
				if($("#issueStatus").val()=='03' || $("#issueStatus").val()=='04'){
					Core.alert({
						message : '该工单'+$("#issueStatusName").text(),
						type: 'error'
					});
					return;
				}
				Core.AjaxRequest({
					url : ws_url + "/rest/issueReply/reply",
					type : 'post',
					params : issue,
					callback : function(data) {
						me.searchIssueConsoleInfo();
						$("#issueComment").val("");
						$("#defineIssueInfoWindow").jqxWindow('close');
					},
					failure : function(data) {
						$("#defineIssueInfoWindow").jqxWindow('close');
					}
				});
			});
	};
	// 初始化用户datagrid的数据源
	this.initIssueConsoledatagrid = function() {
		$("#issueConsoledatagrid").jqxGrid({
			width : "99.8%",
			theme : currentTheme,
			columnsresize : true,
			pageable : true,
			pagesize : 10,
			autoheight : true,
			autowidth : false,
			sortable : true,
			selectionmode : "singlerow",
			localization : gridLocalizationObj,
			columns : [
				{text : '问题标题', datafield : 'issueTitle', width : 130},
				{text : '问题类型', datafield : 'issueTypeName', width : 130},
				{text : '问题级别', datafield : 'issuePriorityName'},
				{text : '问题状态', datafield : 'issueStatusName'},
				{text : '提问者', datafield : 'createdBy'},
				{text : '创建时间', datafield : 'createdDt'},
				{text : '处理人', datafield : 'updatedBy'},
				{text : '处理时间', datafield : 'updatedDt'}
			],
			showtoolbar : true,
			// 设置toolbar操作按钮
			rendertoolbar : function(toolbar) {
				var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
				var approveDetail = $("<div><a id='defineIssueInfo' data-bind='click:defineIssueInfo'>&nbsp;&nbsp;<i class='icons-blue'></i>回复&nbsp;&nbsp;</a></div>");
				var approveClose = $("<div><a id='approveClose' data-bind='click:approveClose'>&nbsp;&nbsp;<i class='icons-blue'></i>关闭&nbsp;&nbsp;</a></div>");
				toolbar.append(container);
				container.append(approveDetail);
				container.append(approveClose);
			}
		});
		$("#defineIssueInfo").jqxButton({
			width : '60',
			theme : currentTheme,
			height : '18px',
			disabled : true
		});
		$("#approveClose").jqxButton({
			width : '60',
			theme : currentTheme,
			height : '18px',
			disabled : true
		});
		// 控制按钮是否可用
		$("#issueConsoledatagrid").on('rowselect', function(event) {
			var args = event.args;
			var index = args.rowindex;
			var data = $('#issueConsoledatagrid').jqxGrid('getrowdata', index);
			if (data !== null) {
				$("#defineIssueInfo").jqxButton({
					width : '60',
					theme : currentTheme,
					height : '18px',
					disabled : false
				});
				if(data.issueStatus == '01' || data.issueStatus == '02'){
					$("#approveClose").jqxButton({
						width : '60',
						theme : currentTheme,
						height : '18px',
						disabled : false
					});
				}else{
					$("#approveClose").jqxButton({
						width : '60',
						theme : currentTheme,
						height : '18px',
						disabled : true
					});
				}
			}
		});
	};
	// 初始化弹出window
	this.initPopWindow = function() {
		$("#defineIssueInfoWindow").jqxWindow({
			width : 750,
			height : 500,
			resizable : true,
			isModal : true,
			autoOpen : false,
			theme : currentTheme,
			cancelButton : $("#consoleInfoCancel"),
			modalOpacity : 0.3,
			initContent : function() {
				$("#issueComment").jqxInput({placeHolder : "回复内容", height : 80, width : 640, minLength : 1, theme : currentTheme});
				$("#consoleInfoSave").jqxButton({width : '50px', height : '25px', theme : currentTheme});
				$("#consoleInfoCancel").jqxButton({width : '50px', height : '25px', theme : currentTheme});
			}
		});
	};
	// 查询用户工单
	this.searchIssueConsoleInfo = function() {
		if(me.searchObj.createdDtFromDate != "" && me.searchObj.createdDtToDate != "") {
			if(new Date(me.searchObj.createdDtFromDate) > new Date(me.searchObj.createdDtToDate)) {
				Core.alert({
					title:"提示",
					type:"error",
					message:"问题创建起始时间不能晚于结束时间 ！"
				});
				return false;
			}
		}
		//createdDtToDate + 24h
		var condition = JSON.parse(JSON.stringify(me.searchObj));
		if(condition.createdDtToDate != "") {
			var dd = new Date(condition.createdDtToDate);
			dd.setDate(dd.getDate() + 1);
			condition.createdDtToDate = dd.format("yyyy-MM-dd");
		}
		Core.AjaxRequest({
			url : ws_url + "/rest/issue/find",
			type : 'post',
			params : condition,
			async : false,
			callback : function(data) {
				me.items(data);
				var sourcedatagrid = {
					datatype : "json",
					localdata : me.items
				};
				var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
				$("#issueConsoledatagrid").jqxGrid({
					source : dataAdapter
				});
			}
		});
	};
};
