var initMsgPageJs = function () {

	var me = this;
    getCodeData("MSG_TYPE,READ_FLAG");

	// 初始化页面中的各种组件，如:输入框、按钮、下拉列表框等
    this.init = function(){
    	$('#jqxTabsMsg').jqxTabs({width : '100%', height : '100%', position : 'top', showCloseButtons: false, theme : currentTheme});

	  	// 添加事件，控制按钮是否可用
  	  	$("#jqxgridInbox").on('rowselect', function (event) {
  			$("#delMsgBtn").jqxButton({disabled: false, theme:currentTheme});
  	  	});
  	  	$("#jqxgridInbox").on('rowunselect', function (event){
  	  		if($('#jqxgridInbox').jqxGrid('getselectedrowindexes').length < 1) {
				me.initOperationBtn('In');
			}
  	  	});
  	  	$("#jqxgridOutbox").on('rowselect', function (event) {
  			$("#delOutboxMsgBtn").jqxButton({disabled: false, theme:currentTheme});
  	  	});
        $("#jqxgridOutbox").on('rowunselect', function (event){
  	  		if($('#jqxgridOutbox').jqxGrid('getselectedrowindexes').length < 1){
  	  			me.initOperationBtn('Out');
			}
  	  	});

        //初始化搜索条件
        me.initSearchCondition();
        //初始化datagrid
        me.initDatagrid('In');
        $("#jqxTabsMsg").jqxTabs('select', 1);
        me.initDatagrid('Out');
        $("#jqxTabsMsg").jqxTabs('select', 0);
	    //初始化弹框
	    me.initPopWindow();
		//初始化验证规则
	    me.initValidator();
        me.initOperationBtn('In');
        me.initOperationBtn('Out');
    };

    //初始化操作按钮，某些按钮默认为disabled
    this.initOperationBtn = function(inOrOut){
		var paginginformation = null;
		var pagescount = 0;
		if('In' == inOrOut) {
            $("#delMsgBtn").jqxButton({disabled: true});
			paginginformation = $("#jqxgridInbox").jqxGrid('getpaginginformation');
			pagescount = paginginformation.pagescount;
            if(pagescount<1) {
                $("#clearMsgBtn").jqxButton({disabled: true});
                $("#markAllReadBtn").jqxButton({disabled: true});
            }else {
                $("#clearMsgBtn").jqxButton({disabled: false});
                $("#markAllReadBtn").jqxButton({disabled: false});
            }
        }else if('Out' == inOrOut) {
            $("#delOutboxMsgBtn").jqxButton({disabled: true});
            paginginformation = $("#jqxgridOutbox").jqxGrid('getpaginginformation');
            pagescount = paginginformation.pagescount;
            if(pagescount<1) {
                $("#clearOutboxMsgBtn").jqxButton({disabled: true});
            }else {
                $("#clearOutboxMsgBtn").jqxButton({disabled: false});
            }
        }
    };

	//初始化搜索
    this.initSearchCondition = function() {
		var codesearch = null;
    	//初始化搜索组件inbox
		$("#searchInboxBtn").jqxButton({theme : currentTheme});
		codesearch = new codeModel({width:100,autoDropDownHeight:true});
		codesearch.getCommonCode("search-inbox-type","MSG_TYPE", true);
		codesearch.getCommonCode("search-inbox-read-flag","READ_FLAG", true);
//		$('#search-inbox-start-time').jqxDateTimeInput({showFooter:true,todayString:'Today',value: null, width: '120px', height: '22px', formatString: 'yyyy-MM-dd', culture: 'zh-CN'});
//		$('#search-inbox-end-time').jqxDateTimeInput({showFooter:true,todayString:'Today',value: null, width: '120px', height: '22px', formatString: 'yyyy-MM-dd', culture: 'zh-CN'});
		Core.jqwidgets({id:"search-inbox-start-time",name:"jqxDateTimeInput",width:"150",height:"22"});
		Core.jqwidgets({id:"search-inbox-end-time",name:"jqxDateTimeInput",width:"150",height:"22"});
		//初始化搜索组件outbox
		$("#searchOutboxBtn").jqxButton({theme : currentTheme});
		codesearch = new codeModel({width:100,autoDropDownHeight:true});
		codesearch.getCommonCode("search-outbox-read-flag","READ_FLAG", true);
		$('#search-outbox-start-time').jqxDateTimeInput({showFooter:true,todayString:'Today',value: null, width: '120px', height: '22px', formatString: 'yyyy-MM-dd', culture: 'zh-CN'});
		$('#search-outbox-end-time').jqxDateTimeInput({showFooter:true,todayString:'Today',value: null, width: '120px', height: '22px', formatString: 'yyyy-MM-dd', culture: 'zh-CN'});
//		console.log($.i18n.prop('input.all'));

		me.clearSearchCondition('in');
		me.clearSearchCondition('out');
		$("#search-inbox-type").jqxDropDownList('updateAt', { label: $.i18n.prop('input.all'), value:''},0);

    };

    //清空搜索条件
    this.clearSearchCondition = function(inOrOut) {
		//搜索条件
		me.searchInObj = {
				fromUser : "",
				toUser : currentUser.user.account,
				msgType : "",
				sendDateStart : "",
				sendDateEnd : "",
				readFlag : "",
                inboxInUse : 1
		};
		me.searchOutObj = {
				fromUser : currentUser.user.account,
				toUser : "",
				msgType : "02",//私人消息
				sendDateStart : "",
				sendDateEnd : "",
				readFlag : "",
                outboxInUse :1
		};
		//重置搜索的input值
		$("#search-"+inOrOut+"box-read-flag").jqxDropDownList('updateAt', { label: $.i18n.prop('input.all'), value:''},0);
		$("#search-"+inOrOut+"box-type").val($.i18n.prop('input.all'));
		$("#search-"+inOrOut+"box-read-flag").val($.i18n.prop('input.all'));
		$("#search-"+inOrOut+"box-start-time").val('');
		$("#search-"+inOrOut+"box-end-time").val('');

        //control button usable
//        var inboxRowindexes = $('#jqxgridInbox').jqxGrid('getselectedrowindexes');
//        var outboxRowindexes = $('#jqxgridOutbox').jqxGrid('getselectedrowindexes');
//        if(inboxRowindexes && inboxRowindexes.length < 1) {
//            $("#delMsgBtn").jqxButton({disabled: true});
//            $("#clearMsgBtn").jqxButton({disabled: true});
//            $("#markAllReadBtn").jqxButton({disabled: true});
//        }
//        if(outboxRowindexes && outboxRowindexes.length < 1) {
//            $("#delOutboxMsgBtn").jqxButton({disabled: true});
//            $("#clearOutboxMsgBtn").jqxButton({disabled: true});
//        }
		window.parent.fetchUserMsgCount();
    };

    // 初始化写消息页面组件
    this.initAddWin = function() {
    	$("#add-to-user").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
    	$("#add-msg-title").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
    	$("#add-msg-content").jqxInput({width: 426, height: 120, minLength: 1,theme:currentTheme});
		$("#saveAddMsg").jqxButton({theme : currentTheme});
		$("#cancelAddMsgBtn").jqxButton({theme : currentTheme});
    };

    //初始化回复消息页面组件
    this.initReplyWin = function() {
    	$("#reply-msg-title").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
    	$("#reply-msg-content").jqxInput({width: 426, height: 120, minLength: 1,theme:currentTheme});
		$("#saveReplyMsg").jqxButton({theme : currentTheme});
		$("#cancelReplyMsgBtn").jqxButton({theme : currentTheme});
    };

    // 初始化消息页面组件
    this.initViewWin = function() {
    	$("#view-msg-content").jqxInput({width: 426, height: 120, minLength: 1, disabled: false,theme:currentTheme});
		$("#cancelViewMsgBtn").jqxButton({theme : currentTheme});
    };

	/**
	 * 查询用户列表数据
	 * @param {String}
	 * 			inOrOut: 搜索收件箱还是发件箱  In|Out
	 */
    this.searchMsg = function(inOrOut){
        $("#jqxgrid"+inOrOut+"box").jqxGrid('applyfilters');
        $("#jqxgrid"+inOrOut+"box").jqxGrid('refreshfilterrow');
        me.initOperationBtn(inOrOut);
    };

    /**
     * 初始化inbox或者outbox
     * 取值为'In'  'Out'
     */
    this.initDatagrid = function(inOrOut){
    	var toOrFromText = $.i18n.prop("module.user.infoCenter.sender");
    	var toOrFromField = 'fromUser';
    	if(inOrOut == 'Out') {
    		toOrFromText = $.i18n.prop("module.user.infoCenter.recipient");
    		toOrFromField = 'toUser';
    	}

        var msgColumn =[
            { text: toOrFromText, datafield: toOrFromField,width:100 },
            { text: $.i18n.prop("module.user.infoCenter.title"), datafield: 'msgTitle',width:150 },
            { text: $.i18n.prop("module.user.infoCenter.content"), datafield: 'msgContent'},
            { text: $.i18n.prop("module.user.infoCenter.type"), datafield: 'msgType',width:70
            	,cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties, rowdata){
		       		 return "<div class=\"jqxGridLinkCell\">"+getListColumnByCode(codeData["MSG_TYPE"],value)+"</div>";
		       	}},
            { text: $.i18n.prop("module.user.infoCenter.isRead"), datafield: 'readFlagName',width:60,
            	cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties, rowdata){
	  	    		var rtStr = "<div class=\"jqxGridLinkCell\" title ="+getListColumnByCode(codeData["READ_FLAG"],rowdata.readFlag)+">";
	  	    		if(rowdata.readFlag == '02') {
	  	    			rtStr += "<i class=\"icons-16-green2 icon-email\"></i>";
	  	    		}else{
	  	    			rtStr += "<i class=\"icons-16-disabled icon-email\"></i>";
	  	    		}
	  	    		rtStr += getListColumnByCode(codeData["READ_FLAG"],rowdata.readFlag);
	  	    		rtStr += "</div>";
	                return rtStr;
	            }
            },
            { text: $.i18n.prop("module.user.infoCenter.sendTime"), datafield: 'sendDate',width:170}

        ];

        var dataAdapter = null;
        if(inOrOut == 'In') {
            msgColumn.push({text: $.i18n.prop("common.label.title.operation"), width:60, align:'center',
                cellsrenderer:function (rowIndex, columnfield, value, defaulthtml, columnproperties, rowdata) {
                    var rtStr = '<div style="text-align: center;padding-top: 2px;">';
                    rtStr += '<a class="icons-16-green2 icon-reply-1" title="'+$.i18n.prop("module.user.infoCenter.reply")+'" href="javascript:operation(\'reply\');void(0);"></a>';
                    rtStr += '<a class="icons-16-green2 icon-eye" title="'+$.i18n.prop("module.user.infoCenter.view")+'" href="javascript:operation(\'view\');void(0);"></a>';
//                    if(rowdata.readFlag == '02') {
//                        rtStr += '<a class="icons-16-green2 icon-email" title="'+$.i18n.prop("module.user.infoCenter.markSelected")+'" href="javascript:operation(\'markRead\');void(0);"></a>';
//                    }else {
//                        rtStr += '<a class="icons-16-disabled icon-email" title="'+$.i18n.prop("module.user.infoCenter.markSelected")+'" href="javascript:operation(\'markRead\');void(0);"></a>';
//                    }
                    rtStr += '</div>';
                    return rtStr;
                }
            });
            dataAdapter = Core.NewDataAdapter({gridId:"jqxgrid"+inOrOut+"box",url:ws_url+"/rest/message/findMessageList",params:me.searchInObj});
        }else {
            dataAdapter = Core.NewDataAdapter({gridId:"jqxgrid"+inOrOut+"box",url:ws_url+"/rest/message/findMessageList",params:me.searchOutObj});
        }

        $("#jqxgrid"+inOrOut+"box").jqxGrid({
            width: "100%",
            theme : currentTheme,
            pageable : true,
            pagesize : gridPageSize,pagesizeoptions: pagesizeoptions,
            height: gridHeight,
            sortable : true,
            localization : gridLocalizationObj,
            selectionmode : "checkbox",
            virtualmode: true,
            source: dataAdapter,
            columnsresize: true,
            enabletooltips: true,enablebrowserselection: true,
            rendergridrows: function(params){
                return dataAdapter.records;
            },
            columns: msgColumn,
            showtoolbar : true,
            rendertoolbar : function(toolbar) {
                //设置toolbar操作按钮
                var container = $("<div class='jqxGridToolbarDiv'></div>");
                toolbar.append(container);
                if(inOrOut == 'In') {
                    var delMsgBtn = $("<div class='mod'><div id='delMsgBtn' class='button_01' data-bind='click: delInboxMsg'><i class='icons-blue icon-cancel-4'></i>"+$.i18n.prop('module.user.infoCenter.deleteSelected')+"</div></div>");
                    var clearMsgBtn = $("<div class='mod'><div id='clearMsgBtn' class='button_01' data-bind='click: clearInboxMsg'><i class='icons-blue icon-cancel-4'></i>"+$.i18n.prop('module.user.infoCenter.deleteAll')+"</div></div>");
                    var markAllReadBtn = $("<div class='mod'><div id='markAllReadBtn' class='button_01' data-bind='click: markAllRead'><i class='icons-blue icon-email'></i>"+$.i18n.prop('module.user.infoCenter.markAll')+"</div></div>");
                    container.append(delMsgBtn).append(clearMsgBtn).append(markAllReadBtn);
                    $("#delMsgBtn").jqxButton({disabled: true, theme:currentTheme});
                    $("#clearMsgBtn").jqxButton({disabled: false, theme:currentTheme});
                    $("#markAllReadBtn").jqxButton({disabled: false, theme:currentTheme});
                }else if(inOrOut == 'Out'){
                    var writeMsgBtn = $("<div class='mod'><div id='writeMsgBtn' class='button_01' data-bind='click: writeMsg'><i class='icons-blue icon-plus-3'></i>"+$.i18n.prop('module.user.infoCenter.writeMsg')+"</div></div>");
                    var delOutboxMsgBtn = $("<div class='mod'><div id='delOutboxMsgBtn' class='button_01' data-bind='click: delOutboxMsg'><i class='icons-blue icon-cancel-4'></i>"+$.i18n.prop('module.user.infoCenter.deleteSelected')+"</div></div>");
                    var clearOutboxMsgBtn = $("<div class='mod'><div id='clearOutboxMsgBtn' class='button_01' data-bind='click: clearOutboxMsg'><i class='icons-blue icon-cancel-4'></i>"+$.i18n.prop('module.user.infoCenter.deleteAll')+"</div></div>");
                    container.append(writeMsgBtn).append(delOutboxMsgBtn).append(clearOutboxMsgBtn);
                    $("#writeMsgBtn").jqxButton({disabled: false, theme:currentTheme });
                    $("#delOutboxMsgBtn").jqxButton({disabled: true, theme:currentTheme});
                    $("#clearOutboxMsgBtn").jqxButton({disabled: false, theme:currentTheme});
                }
            }}).on({
                'rowclick':function (event) {
                    var args = event.args;
                    // row's bound index.
                    var boundIndex = args.rowindex;
                    $("#jqxgrid"+inOrOut+"box").jqxGrid('clearselection');
                    $("#jqxgrid"+inOrOut+"box").jqxGrid('selectrow', boundIndex);
                }
        });
    };

	// 初始化弹出window
    this.initPopWindow = function(){
    	//初始化发送消息弹窗
    	$("#winAddMsg").jqxWindow({
    		showCollapseButton : false,
			width : 600,
			height : 263,
			isModal : true,
			autoOpen : false,
			theme : currentTheme,
			cancelButton : $("#cancelAddMsgBtn"),
			initContent : me.initAddWin
        });

		//初始化回复消息弹窗
    	$("#winReplyMsg").jqxWindow({
    		showCollapseButton : false,
			width : 600,
			height : 263,
			isModal : true,
			autoOpen : false,
			theme : currentTheme,
			cancelButton : $("#cancelReplyMsgBtn"),
			initContent : me.initReplyWin
         });
		//初始化查看消息弹窗
    	$("#winViewMsg").jqxWindow({
    		showCollapseButton : false,
			width : 600,
			height : 299,
			isModal : true,
			autoOpen : false,
			theme : currentTheme,
			cancelButton : $("#cancelViewMsgBtn"),
			initContent : me.initViewWin
        });
    };

    // 初始化验证规则
    this.initValidator = function(){
      	//新增消息验证
    	$("#winFormAddMsg").jqxValidator({
    		animationDuration: 1,
            rules: [
               { input: '#add-to-user', message: $.i18n.prop('core.validate.notNull'), action: 'keyup, blur', rule: 'required' },
	           { input: '#add-to-user', message: $.i18n.prop('core.validate.notOverChar',[16]), action: 'keyup, blur', rule: 'length=1,16' },
	           { input: '#add-msg-title', message: $.i18n.prop('core.validate.notNull'), action: 'keyup, blur', rule: 'required' },
	           { input: '#add-msg-title', message: $.i18n.prop('core.validate.notOverChar',[256]), action: 'keyup, blur', rule: 'length=1,256' },
	           { input: '#add-msg-content', message: $.i18n.prop('core.validate.notNull'), action: 'keyup, blur', rule: 'required' },
	           { input: '#add-msg-content', message: $.i18n.prop('core.validate.notOverChar',[1024]), action: 'keyup, blur', rule: 'length=1,1024' }
           ]
    	});

    	//回复消息验证
    	$("#winFormReplyMsg").jqxValidator({
    		animationDuration: 1,
            rules: [
				{ input: '#reply-msg-title', message: $.i18n.prop('core.validate.notNull'), action: 'keyup, blur', rule: 'required' },
				{ input: '#reply-msg-title', message: $.i18n.prop('core.validate.notOverChar',[256]), action: 'keyup, blur', rule: 'length=1,256' },
				{ input: '#reply-msg-content', message: $.i18n.prop('core.validate.notNull'), action: 'keyup, blur', rule: 'required' },
				{ input: '#reply-msg-content', message: $.i18n.prop('core.validate.notOverChar',[1024]), action: 'keyup, blur', rule: 'length=1,1024' }
           ]
    	});


    };

};
    
var msgBindModel = function(msgObj){
	//弹窗写消息
	this.writeMsg = function() {
		$("#add-to-user").val("");
		$("#add-msg-title").val("");
		$("#add-msg-content").val("");
		$('#winAddMsg').jqxWindow('open');
	};
	//保存 写消息
	this.addMsgSubmit = function() {
		//validate whether the user is exists
        if($('#winFormAddMsg').jqxValidator('validate')) {
            Core.AjaxRequest({
                url: ws_url + "/rest/user/findAll",
                params: {account: $('#add-to-user').val()},
                type: 'POST',
                showMsg: true,
                callback: function (data) {
                    if (data.length < 1) {
                        Core.alert({
                            title: $.i18n.prop('core.alter.title'),
                            message: $.i18n.prop('module.user.infoCenter.userNotFound')
                        });
                        return;
                    } else {
//					if($('#winFormAddMsg').jqxValidator('validate')){
                        var addMsg = {};
                        addMsg.toUser = $('#add-to-user').val();
                        addMsg.msgTitle = $('#add-msg-title').val();
                        addMsg.msgContent = $('#add-msg-content').val();
                        addMsg.fromUser = currentUser.user.account;
                        addMsg.msgType = '02';//私人消息
                        addMsg.readFlag = '02';//未读

                        Core.AjaxRequest({
                            url: ws_url + "/rest/message/addMessage",
                            params: addMsg,
                            type: 'POST',
                            showMsg: true,
                            callback: function (data) {
                                msgObj.clearSearchCondition('in');
                                msgObj.searchMsg('In');
                                msgObj.clearSearchCondition('out');
                                msgObj.searchMsg('Out');
                                $("#winAddMsg").jqxWindow('close');
                            }
                        });
//					}
                    }
                }
            });
        }
	};
	
	//回复消息弹窗
	this.replyMsg = function() {
		var rowindex = $('#jqxgridInbox').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#jqxgridInbox').jqxGrid('getrowdata', rowindex);
    		$("#reply-to-user").val(data.fromUser);
    		$("#reply-msg-title").val("Reply: " + data.msgTitle);
    		$('#winReplyMsg').jqxWindow('open');
    	}
	};
	
	//回复消息保存
	this.replyMsgSubmit = function() {
	   	if($('#winFormReplyMsg').jqxValidator('validate')){
			var replyMsg = {};
			replyMsg.toUser = $('#reply-to-user').val();
			replyMsg.msgTitle = $('#reply-msg-title').val();
			replyMsg.msgContent = $('#reply-msg-content').val();
			replyMsg.fromUser = currentUser.user.account;
			replyMsg.msgType = '02';//私人消息
			replyMsg.readFlag = '02';//未读
			
			Core.AjaxRequest({
				url : ws_url + "/rest/message/addMessage",
				params: replyMsg,
				type: 'POST',
			    showMsg : true,
			    callback : function (data) {
			    	msgObj.clearSearchCondition('in');
			    	msgObj.searchMsg('In');
					msgObj.clearSearchCondition('out');
					msgObj.searchMsg('Out');
			    	$("#winReplyMsg").jqxWindow('close');
			    }
			});
    	}
	};
	
	//查看消息
	this.viewMsg = function() {
		var rowindexes = $('#jqxgridInbox').jqxGrid('getselectedrowindexes');
		if(rowindexes.length == 1) {
	  		var viewObj = $('#jqxgridInbox').jqxGrid('getrowdata', rowindexes[0]);
	  		$('#view-msg-title').text(viewObj.msgTitle);
	  		$('#view-to-user').text(viewObj.toUser);
	  		$('#view-send-date').text(viewObj.sendDate);
	  		$('#view-msg-content').val(viewObj.msgContent);
	  		$('#winViewMsg').jqxWindow('open');
	  		//查看之后马上标记为已读
	  		if(viewObj.readFlag == '02') {
	  			var markReadMsg = {};
	  			markReadMsg.toUser = currentUser.user.account;
	  			markReadMsg.readFlag = '01';
	  			markReadMsg.msgSid = viewObj.msgSid;
	  			Core.AjaxRequest({
	  				url : ws_url + "/rest/message/updateMessages",
	  				params: [markReadMsg],
	  				type: 'POST',
	  				showMsg : false,
	  				callback : function (data) {
	  					msgObj.clearSearchCondition('in');
	  					msgObj.searchMsg('In');
	  				}
	  			});
	  		}
		}else{
			if (rowindexes.length > 1) {
				Core.alert({
                    title: $.i18n.prop('core.alter.title'),
					message: $.i18n.prop('module.user.infoCenter.plzSelectOne')
				});
			}
		}
	};
	
	//标记为已读
	this.markRead = function() {
		var rowindexes = $('#jqxgridInbox').jqxGrid('getselectedrowindexes');
		if(rowindexes.length > 0){
			var messages = [];
			for(var i = 0; i < rowindexes.length; i++){
				var markReadMsg = {};
//				markReadMsg.toUser = currentUser.user.account;
				markReadMsg.readFlag = '01';
				markReadMsg.msgSid = $('#jqxgridInbox').jqxGrid('getrowdata', rowindexes[i]).msgSid;
				messages.push(markReadMsg);	
			}
			Core.AjaxRequest({
				url : ws_url + "/rest/message/updateMessages",
				params: messages,
				type: 'POST',
				showMsg : true,
				callback : function (data) {
					msgObj.clearSearchCondition('in');
					msgObj.searchMsg('In');
					$("#winAddMsg").jqxWindow('close');
				}
			});
		}
	};
	//全部标记为已读
	this.markAllRead = function() {
        var paginginformation = $("#jqxgridInbox").jqxGrid('getpaginginformation');
        var pagescount = paginginformation.pagescount;
        if(pagescount>0) {
            var markAllRead = {};
            markAllRead.toUser = currentUser.user.account;
            markAllRead.readFlag = '01';
            Core.AjaxRequest({
                url: ws_url + "/rest/message/updateAllMessages",
                params: markAllRead,
                type: 'POST',
                showMsg: true,
                callback: function (data) {
                    msgObj.clearSearchCondition('in');
                    msgObj.searchMsg('In');
                    $("#winAddMsg").jqxWindow('close');
                }
            });
        }
	};
	
	//删除消息inbox
	this.delInboxMsg = function(){
		var rowindexes = $('#jqxgridInbox').jqxGrid('getselectedrowindexes');
		if(rowindexes.length > 0){
			var messages = [];
			for(var i = 0; i < rowindexes.length; i++){
				var markReadMsg = {};
				markReadMsg.inboxInUse = 0;
				var obj = $('#jqxgridInbox').jqxGrid('getrowdata', rowindexes[i]);
				if (typeof(obj) != "undefined") { 
					markReadMsg.msgSid = obj.msgSid;
					messages.push(markReadMsg);	
				}
			}
			Core.confirm({
				title: $.i18n.prop('core.alter.title'),
				message:$.i18n.prop('module.user.infoCenter.confirmDeleteSelected'),
				confirmCallback:function(){
					Core.AjaxRequest({
						type: 'POST',
						showMsg : true,
						url : ws_url + "/rest/message/updateMessages",
						params : messages,
						callback : function (data) {
							msgObj.clearSearchCondition('in');
							msgObj.searchMsg('In');
							msgObj.initOperationBtn('In');
							$('#jqxgridInbox').jqxGrid('clearselection');
						}
					});
				}
			});
		}
	};
	
	//删除消息 Outbox
	this.delOutboxMsg = function(){
		var rowindexes = $('#jqxgridOutbox').jqxGrid('getselectedrowindexes');
		if(rowindexes.length > 0){
			var messages = [];
			for(var i = 0; i < rowindexes.length; i++){
				var markReadMsg = {};
				markReadMsg.outboxInUse = 0;
				markReadMsg.msgSid = $('#jqxgridOutbox').jqxGrid('getrowdata', rowindexes[i]).msgSid;
				messages.push(markReadMsg);	
			}
			Core.confirm({
                title: $.i18n.prop('core.alter.title'),
                message:$.i18n.prop('module.user.infoCenter.confirmDeleteSelected'),
				confirmCallback:function(){
					Core.AjaxRequest({
						type: 'POST',
						showMsg : true,
						url : ws_url + "/rest/message/updateMessages",
						params : messages,
						callback : function (data) {
							msgObj.clearSearchCondition('out');
							msgObj.searchMsg('Out');
							msgObj.initOperationBtn('Out');
							$('#jqxgridOutbox').jqxGrid('clearselection');
						}
					});
				}
			});
		}
	};
	
	//清空消息 inbox
	this.clearInboxMsg = function() {
        var paginginformation = $("#jqxgridInbox").jqxGrid('getpaginginformation');
        var pagescount = paginginformation.pagescount;
        if(pagescount>0) {
            Core.confirm({
                title: $.i18n.prop('core.alter.title'),
                message: $.i18n.prop('module.user.infoCenter.confirmDeleteAll'),
                showMsg: true,
                confirmCallback: function () {
                    Core.AjaxRequest({
                        type: 'POST',
                        url: ws_url + "/rest/message/updateAllMessages",
                        params: {toUser: currentUser.user.account, inboxInUse: 0},
                        callback: function (data) {
                            msgObj.clearSearchCondition('in');
                            msgObj.searchMsg('In');
                            msgObj.initOperationBtn('In');
                        }
                    });
                }
            });
        }
	};
	
	//清空消息 Outbox
	this.clearOutboxMsg = function() {
        var paginginformation = $("#jqxgridOutbox").jqxGrid('getpaginginformation');
        var pagescount = paginginformation.pagescount;
        if(pagescount>0) {
            Core.confirm({
                title: $.i18n.prop('core.alter.title'),
                message: $.i18n.prop('module.user.infoCenter.confirmDeleteAll'),
                showMsg: true,
                confirmCallback: function () {
                    Core.AjaxRequest({
                        type: 'POST',
                        url: ws_url + "/rest/message/updateAllMessages",
                        params: {fromUser: currentUser.user.account, outboxInUse: 0},
                        callback: function (data) {
                            msgObj.clearSearchCondition('out');
                            msgObj.searchMsg('Out');
                            msgObj.initOperationBtn('Out');
                        }
                    });
                }
            });
        }
	};
	
	//按条件搜索收件箱
	this.searchInbox = function() {
		msgObj.searchInObj.msgType = $("#search-inbox-type").val() == $.i18n.prop('input.all') ? '' : $("#search-inbox-type").val();
		msgObj.searchInObj.readFlag = $("#search-inbox-read-flag").val() == $.i18n.prop('input.all') ? '' : $("#search-inbox-read-flag").val();
		msgObj.searchInObj.sendDateStart = $("#search-inbox-start-time").val();
		msgObj.searchInObj.sendDateEnd = $("#search-inbox-end-time").val() === '' ? '' : $("#search-inbox-end-time").val() + ' 23:59:59';
    	if(msgObj.searchInObj.sendDateStart && msgObj.searchInObj.sendDateEnd && msgObj.searchInObj.sendDateStart >= msgObj.searchInObj.sendDateEnd){
			Core.alert({
                    title: $.i18n.prop('core.alter.title'),
					message: $.i18n.prop('module.user.infoCenter.startTimeCantLaterThanEndTime')
				});
    	}else {
    		msgObj.searchMsg('In');
    	}
	};
	//按条件搜索发件箱
	this.searchOutbox = function() {
		msgObj.searchOutObj.readFlag = $("#search-outbox-read-flag").val() == $.i18n.prop('input.all') ? '' : $("#search-outbox-read-flag").val();
		msgObj.searchOutObj.sendDateStart = $("#search-outbox-start-time").val();
		msgObj.searchOutObj.sendDateEnd = $("#search-outbox-end-time").val() === '' ? '' : $("#search-outbox-end-time").val() + ' 23:59:59';
    	if(msgObj.searchOutObj.sendDateStart && msgObj.searchOutObj.sendDateEnd && msgObj.searchOutObj.sendDateStart >= msgObj.searchOutObj.sendDateEnd){
			Core.alert({
                    title: $.i18n.prop('core.alter.title'),
                    message: $.i18n.prop('module.user.infoCenter.startTimeCantLaterThanEndTime')
				});
    	}else {
    		msgObj.searchMsg('Out');
    	}
	};
	

};

function operation(op) {
	var rowindexes = null;
	var markReadMsg = null;
	if(op == 'view') {
        rowindexes = $('#jqxgridInbox').jqxGrid('getselectedrowindexes');
        if(rowindexes.length == 1) {
            var viewObj = $('#jqxgridInbox').jqxGrid('getrowdata', rowindexes[0]);
            $('#view-msg-title').text(viewObj.msgTitle);
            $('#view-to-user').text(viewObj.toUser);
            $('#view-send-date').text(viewObj.sendDate);
            $('#view-msg-content').val(viewObj.msgContent);
            $('#winViewMsg').jqxWindow('open');
            //查看之后马上标记为已读
            if(viewObj.readFlag == '02') {
                markReadMsg = new Object;
                markReadMsg.toUser = currentUser.user.account;
                markReadMsg.readFlag = '01';
                markReadMsg.msgSid = viewObj.msgSid;
                Core.AjaxRequest({
                    url : ws_url + "/rest/message/updateMessages",
                    params: [markReadMsg],
                    type: 'POST',
                    showMsg : false,
                    callback : function (data) {
                        msgObj.clearSearchCondition('in');
                        msgObj.searchMsg('In');
                    }
                });
            }
        }
    }else if(op == 'reply') {
        var rowindex = $('#jqxgridInbox').jqxGrid('getselectedrowindex');
        if(rowindex >= 0){
            var data = $('#jqxgridInbox').jqxGrid('getrowdata', rowindex);
            $("#reply-to-user").val(data.fromUser);
            $("#reply-msg-title").val("Reply: " + data.msgTitle);
            $('#winReplyMsg').jqxWindow('open');
        }
    }else if(op == 'markRead') {
        rowindexes = $('#jqxgridInbox').jqxGrid('getselectedrowindexes');
        if(rowindexes.length > 0){
            var messages = [];
            for(var i = 0; i < rowindexes.length; i++){
                markReadMsg = {};
//				markReadMsg.toUser = currentUser.user.account;
                markReadMsg.readFlag = '01';
                markReadMsg.msgSid = $('#jqxgridInbox').jqxGrid('getrowdata', rowindexes[i]).msgSid;
                messages.push(markReadMsg);
            }
            Core.AjaxRequest({
                url : ws_url + "/rest/message/updateMessages",
                params: messages,
                type: 'POST',
                showMsg : true,
                callback : function (data) {
                    msgObj.clearSearchCondition('in');
                    msgObj.searchMsg('In');
                    $("#winAddMsg").jqxWindow('close');
                }
            });
        }
    }
}

var msgObj = null;
var msgBindModelObj = null;
$(function(){
	//初始化js对象
    msgObj = new initMsgPageJs();
    //初始化页面主键
    msgObj.init();
	//初始化主键事件
	msgBindModelObj = new msgBindModel(msgObj);
	ko.applyBindings(msgBindModelObj);
});