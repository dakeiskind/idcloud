var issueBindModel = function(conso){
	var me = this;
	/**清除日期条件*/
	$("#search-reset-button").on("click", function(event) {
		$('#createdDtFromDate').jqxDateTimeInput({
			value : null
		});
		$('#createdDtToDate').jqxDateTimeInput({
			value : null
		});
	});

	/** 回复问题 */
	this.defineIssueInfo = function(){
		var rowindex = $('#issueConsoledatagrid').jqxGrid('getselectedrowindex');
		var ok =  $("#defineIssueInfo").jqxButton("disabled");
    	if(rowindex >= 0 && !ok){
			var data = $('#issueConsoledatagrid').jqxGrid('getrowdata', rowindex);
			me.selectIssueReply(data.issueSid)
    	    // 问题Sid
    	    $("#issueSid").val(data.issueSid);
    	    // 问题标题
    	    $("#issueTitle").text(data.issueTitle);
			//问题级别
			$("#issuePriorityName").text(data.issuePriorityName);
			//问题类型
			$("#issueStatus").val(data.issueStatus);
			$("#issueTypeName").text(data.issueTypeName);
			//问题状态
			$("#issueStatusName").text(data.issueStatusName);
    	    // 问题描述
    	    $("#issueDesc").text(data.issueDesc == null?"":data.issueDesc);
    	    // 设置window的位置
    	    var windowW = $(window).width();
    	   	var windowH = $(window).height();
    	   	$("#defineIssueInfoWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-520)/2 } });
    	   	$("#defineIssueInfoWindow").jqxWindow('open');
    	}	    
	};

	/** 关闭问题 */
	this.approveClose = function(){
		var rowindex = $('#issueConsoledatagrid').jqxGrid('getselectedrowindex');
		var ok =  $("#defineIssueInfo").jqxButton("disabled");
		if(rowindex >= 0 && !ok) {
			var data = $('#issueConsoledatagrid').jqxGrid('getrowdata', rowindex);
			if(data.issueStatus == '03' || data.issueStatus == '04'){
				return;
			}
			var issue = {};
			issue.issueSid = data.issueSid;
			issue.issueStatus = '03';
			Core.confirm({
				title:"请选择",
				message:"您确定要关闭该工单吗？",
				yes:"确定",
				confirmCallback:function(){
					Core.AjaxRequest({
						url : ws_url + "/rest/issue/update",
						type : 'POST',
						params:issue,
						async : false,
						callback : function(data) {
							conso.searchIssueConsoleInfo();
						}
					});
				}
			});
		}
	};
	
	/** 提交确认问题信息 */
	this.DefineIssueInfoSubmit = function(){
		$('#defineIssueInfoForm').jqxValidator('validate');
	};

	/** 条件查询告警信息 */
	this.searchIssueConsole = function(){
		conso.searchObj.issuePriority = $("#search-issue-priority").val()=="全部"?"":$("#search-issue-priority").val();
		conso.searchObj.issueType = $("#search-issue-type").val()=="全部"?"":$("#search-issue-type").val();
		conso.searchObj.issueStatus = $("#search-issue-status").val()=="全部"?"":$("#search-issue-status").val();
		conso.searchObj.createdBy = $("#search-created-by").val()=="全部"?"":$("#search-created-by").val();
		conso.searchObj.createdDtFromDate = $("#createdDtFromDate").val()=="全部"?"":$("#createdDtFromDate").val();
		conso.searchObj.createdDtToDate = $("#createdDtToDate").val()=="全部"?"":$("#createdDtToDate").val();
		conso.searchIssueConsoleInfo();
	};

	/** 获取回复记录 */
	this.selectIssueReply = function(issueSid){
		Core.AjaxRequest({
			url : ws_url + "/rest/issueReply/findReply/"+issueSid,
			type : 'GET',
			async : false,
			callback : function(data) {
				$("#issueReply").empty();
				for(var i = data.length-1;i>=0;i--){
					// 问题回复
					if(data[i].replyType == '02'){
						$("#issueReply").append(
							'<div style="text-align: left;margin-left: 10px;margin-top: 5px;"> ' +
							'<div style="line-height: 16px;"> ' +
							'<span>'+data[i].createdBy+'</span>&nbsp;&nbsp; ' +
							'<span style="color: #808080">'+data[i].createdDt+'</span> ' +
							'</div> ' +
							'<div style="line-height: 20px;"><span>'+data[i].content+'</span></div> ' +
							'</div>'
						)
					}else{
						$("#issueReply").append(
							'<div style="text-align: right;margin-right: 10px;margin-top: 5px;"> ' +
							'<div style="line-height: 16px;"> ' +
							'<span style="color: #808080">'+data[i].createdDt+'</span>&nbsp;&nbsp; ' +
							'<span>'+data[i].createdBy+'</span> ' +
							'</div> ' +
							'<div style="line-height: 20px;"><span>'+data[i].content+'</span></div> ' +
							'</div>'
						)
					}
				}
			}
		});
	}
};
  
  
  