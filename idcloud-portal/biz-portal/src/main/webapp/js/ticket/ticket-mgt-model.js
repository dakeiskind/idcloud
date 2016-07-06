var sysDiskData = new Array;
var dataDisks = new Array;
var ticketMgtModel = function () {
	var me = this;
	this.itemsTicketMgt = ko.observableArray();
	this.itemsTicketMgtAllocate = ko.observableArray();
	this.itemsTicketMgtView = ko.observableArray();
	this.itemsTicketMgtProcess = ko.observableArray();
	this.searchObj = {
		ticketNoLike: "",
		createdStartDt : "",
		createdEndDt: "",
		processType: "",
		status:""
	};
	/******************************************工单查询*****************************************************************/

		// 查询工单数据
	this.searchTicketMgt = function(options){

		Core.AjaxRequest({
			url : ws_url + "/rest/tickets/platform/findTickets",
			type:'post',
			params: me.searchObj,
			async:false,
			callback : function (data) {
				me.itemsTicketMgt(data);

				var sourcedatagrid ={
					datatype: "json",
					localdata: me.itemsTicketMgt
				};

				var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);

				$("#jqxgridTicket").jqxGrid({source: dataAdapter});
			}
		});
	};

	//根据不同的工单状态Tab页，显示不同的工单操作和内容
	this.switchViewByTab = function (tabIndex) {
		$('#jqxgridTicket').jqxGrid('clearselection');
		$("#allocateBtn").jqxButton({disabled: true});
		$("#processBtn").jqxButton({disabled: true});
		$("#viewBtn").jqxButton({disabled: true});
		if(tabIndex === 0) {
			$("#addTicketBtn").show();
			$("#allocateBtn").show();
			$("#processBtn").hide();
			$("#viewBtn").show();
		} else if(tabIndex == 1) {
			$("#addTicketBtn").hide();
			$("#allocateBtn").hide();
			$("#processBtn").show();
			$("#viewBtn").show();
		} else if(tabIndex == 2) {
			$("#addTicketBtn").hide();
			$("#allocateBtn").hide();
			$("#processBtn").hide();
			$("#viewBtn").show();
		}
	};

	// 查询工单datagrid的数据源
	this.initTicketMgtDatagrid = function(){
		$("#jqxgridTicket").jqxGrid({
				width: "98%",
				theme:currentTheme,
				columnsresize: true,
				pageable: true,
				sortable: true,
				pagesize: 10,
				autoheight: true,
				localization: gridLocalizationObj,
				columns: [
					{ text: '工单号', datafield: 'ticketNo'},
					{ text: '工单主题', datafield: 'title', width: 350},
					{ text: '工单类型', datafield: 'questionTypeName' },
					{ text: '服务名称', datafield: 'serviceName' },
					{ text: '提交人', datafield: 'ticketUser' },
					{ text: '分配给', datafield: 'allocationTicketUser' },
					{ text: '工单状态', datafield: 'statusName' },
					{ text: '更新时间', datafield: 'updatedDt' },
					{ text: '创建时间', datafield: 'createdDt' }
				],
				showtoolbar: true,
				rendertoolbar: function (toolbar) {
					var me = this;

					var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
					var addTicketBtn = $("<div><a id='addTicketBtn' data-bind='click: addNormalTicket'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>添加日常维护工单&nbsp;&nbsp;</a></div>");
					var allocateBtn = $("<div><a id='allocateBtn' data-bind='click: allocateItem'>&nbsp;&nbsp;<i class='icons-blue icon-exchange'></i>分配&nbsp;&nbsp;</a></div>");
					var processBtn = $("<div><a id='processBtn' data-bind='click: processItem' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-right-open'></i>处理&nbsp;&nbsp;</a></div>");
					var viewBtn = $("<div><a id='viewBtn' data-bind='click: viewItem' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>查看&nbsp;&nbsp;</a></div>");
					/*
					 var executeAgainBtn = $("<div><a id='executeAgainBtn' data-bind='click: executeAgain' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>重新执行&nbsp;&nbsp;</a></div>");
					 */

					toolbar.append(container);
					container.append(addTicketBtn);
					container.append(allocateBtn);
					container.append(processBtn);
					container.append(viewBtn);
					/*
					 container.append(executeAgainBtn);
					 */
				}
			});

		// 控制按钮是否可用
		$("#jqxgridTicket").on('rowselect', function (event) {
			$("#addTicketBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
			$("#allocateBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			$("#processBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			//$("#executeAgainBtn").jqxButton({ width: '75',theme:currentTheme,height: '18px',  disabled: true});
			var args = event.args;
			var index = args.rowindex;
			var data = $('#jqxgridTicket').jqxGrid('getrowdata', index);
			if (data.allocateAccess === true) {
				if (data.status == "01" || data.status == "02") {
					$("#allocateBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
				}
			}
			if (data.processAccess === true) {
				if (data.status == "02" || data.status == "03" || data.status == "04") {
					$("#processBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
				}
			}
			if(index >= 0) {
				$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
			}
			/*
			 if ((data.processType == "01" || data.processType == "12" || data.processType == "02") && data.status == "01"){
			 $("#executeAgainBtn").jqxButton({ width: '75',theme:currentTheme,height: '18px',  disabled: false});
			 }
			 */
		});

		$("#addTicketBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
		$("#allocateBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
		$("#processBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
		$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
		/*
		 $("#executeAgainBtn").jqxButton({ width: '75',theme:currentTheme,height: '18px',  disabled: true});
		 */

	};
	this.initPendingTicketMgtDatagrid = function(){
		$("#jqxgridTicket").jqxGrid({
			width: "98%",
			theme:currentTheme,
			columnsresize: true,
			pageable: true,
			sortable: true,
			pagesize: 10,
			autoheight: true,
			localization: gridLocalizationObj,
			columns: [
				{ text: '工单号', datafield: 'ticketNo'},
				{ text: '工单主题', datafield: 'title', width: 350},
				{ text: '工单类型', datafield: 'questionTypeName' },
				{ text: '服务名称', datafield: 'serviceName' },
				{ text: '提交人', datafield: 'ticketUser' },
				{ text: '分配给', datafield: 'allocationTicketUser' },
				{ text: '工单状态', datafield: 'statusName' },
				{ text: '更新时间', datafield: 'updatedDt' },
				{ text: '创建时间', datafield: 'createdDt' }
			],
			showtoolbar: true,
			rendertoolbar: function (toolbar) {
				var me = this;

				var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
				var processBtn = $("<div><a id='processBtn' data-bind='click: processItem' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-right-open'></i>处理&nbsp;&nbsp;</a></div>");
				var viewBtn = $("<div><a id='viewBtn' data-bind='click: viewItem' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>查看&nbsp;&nbsp;</a></div>");

				toolbar.append(container);
				container.append(processBtn);
				container.append(viewBtn);
			}
		});

		// 控制按钮是否可用
		$("#jqxgridTicket").on('rowselect', function (event) {
			$("#processBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			//$("#executeAgainBtn").jqxButton({ width: '75',theme:currentTheme,height: '18px',  disabled: true});
			var args = event.args;
			var index = args.rowindex;
			var data = $('#jqxgridTicket').jqxGrid('getrowdata', index);
			if (data.processAccess === true) {
				if (data.status == "02" || data.status == "03" || data.status == "04") {
					$("#processBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
				}
			}
			if(index >= 0) {
				$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
			}
		});

		$("#processBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
		$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});

	};
	this.initProcessedTicketMgtDatagrid = function(){
		$("#jqxgridTicket").jqxGrid({
			width: "98%",
			theme:currentTheme,
			columnsresize: true,
			pageable: true,
			sortable: true,
			pagesize: 10,
			autoheight: true,
			localization: gridLocalizationObj,
			columns: [
				{ text: '工单号', datafield: 'ticketNo'},
				{ text: '工单主题', datafield: 'title', width: 350},
				{ text: '工单类型', datafield: 'questionTypeName' },
				{ text: '服务名称', datafield: 'serviceName' },
				{ text: '提交人', datafield: 'ticketUser' },
				{ text: '分配给', datafield: 'allocationTicketUser' },
				{ text: '工单状态', datafield: 'statusName' },
				{ text: '更新时间', datafield: 'updatedDt' },
				{ text: '创建时间', datafield: 'createdDt' }
			],
			showtoolbar: true,
			rendertoolbar: function (toolbar) {
				var me = this;

				var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
				var viewBtn = $("<div><a id='viewBtn' data-bind='click: viewItem' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>查看&nbsp;&nbsp;</a></div>");

				toolbar.append(container);
				container.append(viewBtn);
			}
		});

		// 控制按钮是否可用
		$("#jqxgridTicket").on('rowselect', function (event) {
			$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			var args = event.args;
			var index = args.rowindex;
			if(index >= 0) {
				$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
			}
		});

		$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});

	};
	/******************************************工单查询*****************************************************************/

	/******************************************工单初始化*****************************************************************/
		// 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	this.initTicketMgtInput = function(){
		// 初始化查询组件
		$("#search-ticket-id").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
		$("#search-ticket-createDt-start").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,
			allowKeyboardDelete: true,allowNullDate: true,showFooter:true,clearString:'清除',todayString:'今天',
			value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd 00:00:00'});

		$("#search-ticket-createDt-end").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,
			allowKeyboardDelete: true,allowNullDate: true,showFooter:true,clearString:'清除',todayString:'今天',
			value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd 23:59:59'});
		$("#search-button").jqxButton({ width: '50',height:"25",theme:currentTheme});
		// 初始化下拉列表框
		var codesearch = new codeModel({width:150,autoDropDownHeight:true});
		codesearch.getCommonCode("search-ticket-type", "TICKET_TYPE",true);
		codesearch.getCommonCode("search-ticket-status", "TICKET_STATUS",false);

		//初始化工单查看
		$("#cancelView").jqxButton({ width: '50',height:"25",theme:currentTheme});
		//初始化工单分配
		$("#cancelAllocate").jqxButton({ width: '50',height:"25",theme:currentTheme});
		$("#saveAllocate").jqxButton({ width: '50',height:"25",theme:currentTheme});

		//初始化工单处理
		$("#processTicket").jqxButton({ width: '50',height:"25",theme:currentTheme,disabled: true});
		$("#autoProcessTicket").jqxButton({ width: '50',height:"25",theme:currentTheme});
		$("#resCheckSubmit").jqxButton({ width: '70',height:"25",theme:currentTheme});
		$("#changeAutoProcessTicket").jqxButton({ width: '50',height:"25",theme:currentTheme});
		codesearch.getCommonCodeByConditions("process-operateStatus", false, {
			'codeCategory': 'TICKET_STATUS',
			'codeValues': "'04'"
		});
		$('#handlerTab').jqxTabs({ height:'250px', width:'100%', theme: currentTheme});
		$('#handlerTab').on('selected', function (event) {
			var selectedTab = event.args.item;
			if(selectedTab === 0) {
				$('#operate').val('01');
			} else if(selectedTab === 1) {
				$('#operate').val('02');
			} else if(selectedTab === 2) {
				var rowindex = $('#jqxgridTicket').jqxGrid('getselectedrowindex');
				if (rowindex >= 0) {
					var ticketData = $('#jqxgridTicket').jqxGrid('getrowdata', rowindex);
					//初始化工单记录
					Core.AjaxRequest({
						url : ws_url + "/rest/tickets/platform/" + ticketData.ticketSid + "/ticketRecord",
						type:'post',
						params:{},
						async:false,
						callback : function (data) {
							me.itemsTicketMgtProcess(data);
							me.initTicketMgtProcessDatagrid(data);
						}
					});
				}
			}
		});
	};
	this.initPendingTicketMgtInput = function(){
		// 初始化查询组件
		$("#search-ticket-id").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
		$("#search-ticket-createDt-start").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,
			allowKeyboardDelete: true,allowNullDate: true,showFooter:true,clearString:'清除',todayString:'今天',
			value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd 00:00:00'});

		$("#search-ticket-createDt-end").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,
			allowKeyboardDelete: true,allowNullDate: true,showFooter:true,clearString:'清除',todayString:'今天',
			value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd 23:59:59'});
		$("#search-button").jqxButton({ width: '50',height:"25",theme:currentTheme});
		// 初始化下拉列表框
		var codesearch = new codeModel({width:150,autoDropDownHeight:true});
		codesearch.getCommonCode("search-ticket-type", "TICKET_TYPE",true);
		codesearch.getCommonCode("search-ticket-status", "TICKET_STATUS",false);

		//初始化工单查看
		$("#cancelView").jqxButton({ width: '50',height:"25",theme:currentTheme});

		//初始化工单处理
		$("#processTicket").jqxButton({ width: '50',height:"25",theme:currentTheme,disabled: true});
		$("#autoProcessTicket").jqxButton({ width: '50',height:"25",theme:currentTheme});
		$("#resCheckSubmit").jqxButton({ width: '70',height:"25",theme:currentTheme});
		$("#changeAutoProcessTicket").jqxButton({ width: '50',height:"25",theme:currentTheme});
		codesearch.getCommonCodeByConditions("process-operateStatus", false, {
			'codeCategory': 'TICKET_STATUS',
			'codeValues': "'04'"
		});
		$('#handlerTab').jqxTabs({ height:'250px', width:'100%', theme: currentTheme});
		$('#handlerTab').on('selected', function (event) {
			var selectedTab = event.args.item;
			if(selectedTab === 0) {
				$('#operate').val('01');
			} else if(selectedTab === 1) {
				$('#operate').val('02');
			} else if(selectedTab === 2) {
				var rowindex = $('#jqxgridTicket').jqxGrid('getselectedrowindex');
				if (rowindex >= 0) {
					var ticketData = $('#jqxgridTicket').jqxGrid('getrowdata', rowindex);
					//初始化工单记录
					Core.AjaxRequest({
						url : ws_url + "/rest/tickets/platform/" + ticketData.ticketSid + "/ticketRecord",
						type:'post',
						params:{},
						async:false,
						callback : function (data) {
							me.itemsTicketMgtProcess(data);
							me.initTicketMgtProcessDatagrid(data);
						}
					});
				}
			}
		});
	};
	this.initProcessedTicketMgtInput = function(){
		// 初始化查询组件
		$("#search-ticket-id").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
		$("#search-ticket-createDt-start").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,
			allowKeyboardDelete: true,allowNullDate: true,showFooter:true,clearString:'清除',todayString:'今天',
			value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd 00:00:00'});

		$("#search-ticket-createDt-end").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,
			allowKeyboardDelete: true,allowNullDate: true,showFooter:true,clearString:'清除',todayString:'今天',
			value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd 23:59:59'});
		$("#search-button").jqxButton({ width: '50',height:"25",theme:currentTheme});
		// 初始化下拉列表框
		var codesearch = new codeModel({width:150,autoDropDownHeight:true});
		codesearch.getCommonCode("search-ticket-type", "TICKET_TYPE",true);
		codesearch.getCommonCode("search-ticket-status", "TICKET_STATUS",false);

		//初始化工单查看
		$("#cancelView").jqxButton({ width: '50',height:"25",theme:currentTheme});

	};

	// 初始化弹出window
	this.initTicketMgtPopWindow = function(){
		$("#popupAllocateWindow").jqxWindow({
			width: 800,
			height:350,
			resizable: false,
			isModal: true,
			autoOpen: false,
			theme: currentTheme,
			cancelButton: $("#cancelAllocate"),
			modalOpacity: 0.3
		});
		$("#popupViewWindow").jqxWindow({
			width: 800,
			height:540,
			resizable: false,
			isModal: true,
			autoOpen: false,
			theme: currentTheme,
			cancelButton: $("#cancelView"),
			modalOpacity: 0.3
		});
		$("#popupProcessWindow").jqxWindow({
			width: 800,
			height:430,
			resizable: false,
			isModal: true,
			autoOpen: false,
			theme: currentTheme,
			cancelButton: $("#cancelProcess"),
			modalOpacity: 0.3,
			initContent: function () {
				$('#vLanIDO').change(function () {
					me.initIpAddressDropDownList('wanIps', $(this).val());
				});

				$('#vLanIDI').change(function () {
					me.initIpAddressDropDownList('lanIps', $(this).val());
				});
			}
		});
		//添加工单窗口
		$("#popAddTicketWindow").jqxWindow({
			width: 390,
			height:250,
			resizable: false,
			isModal: true,
			autoOpen: false,
			theme: currentTheme,
			cancelButton: $("#cancelTicketBtn"),
			modalOpacity: 0.3,
			initContent: function () {
				$("#ticketTitle").jqxInput({placeHolder: "", height: 22, width: 190, minLength: 1,theme:currentTheme});
				$("#ticketTitle").val("日常维护工单-");

				$("#saveTicketBtn").jqxButton({ width: '50',height:"25",theme:currentTheme});
				$("#cancelTicketBtn").jqxButton({ width: '50',height:"25",theme:currentTheme});
			}
		});
	};
	this.initPendingTicketMgtPopWindow = function(){
		$("#popupViewWindow").jqxWindow({
			width: 800,
			height:540,
			resizable: false,
			isModal: true,
			autoOpen: false,
			theme: currentTheme,
			cancelButton: $("#cancelView"),
			modalOpacity: 0.3
		});
		$("#popupProcessWindow").jqxWindow({
			width: 800,
			height:430,
			resizable: false,
			isModal: true,
			autoOpen: false,
			theme: currentTheme,
			cancelButton: $("#cancelProcess"),
			modalOpacity: 0.3,
			initContent: function () {
				$('#vLanIDO').change(function () {
					me.initIpAddressDropDownList('wanIps', $(this).val());
				});

				$('#vLanIDI').change(function () {
					me.initIpAddressDropDownList('lanIps', $(this).val());
				});
			}
		});
	};
	this.initProcessedTicketMgtPopWindow = function(){
		$("#popupViewWindow").jqxWindow({
			width: 800,
			height:540,
			resizable: false,
			isModal: true,
			autoOpen: false,
			theme: currentTheme,
			cancelButton: $("#cancelView"),
			modalOpacity: 0.3
		});
	};

	/** 初始化IP列表*/
	this.initIpAddressDropDownList = function(id, netSid){
//	    	me.createIpAddressDropDownList($("#"+id+""), netSid);

		var dropdownCmp = $("#"+id+"");
		dropdownCmp.jqxDropDownList({
			placeHolder: "请选择...",
			displayMember: 'ipAddressWithStatus',
			valueMember: 'ipAddress',
			width: 220,
			height: 22,
			autoDropDownHeight :false,
			dropDownWidth :220,
			dropDownHeight :180,
			disabled:false,
			theme: currentTheme
		});
		if(!netSid || netSid === "") {
			dropdownCmp.jqxDropDownList('clear');
			return;
		}
		if(netSid == '自动分配') {
			dropdownCmp.jqxDropDownList('clear');
			$("#"+id+"").jqxDropDownList('insertAt', { label:"自动分配" ,value:"自动分配"},0);
			$("#"+id+"").jqxDropDownList('selectIndex', 0 );
		} else {
			Core.AjaxRequest({
				url :ws_url +"/rest/ip/network/"+ netSid,
				type:'get',
				async:false,
				callback : function (data) {
					if(data.length === 0) {
						me.checkInstanceResSelect(id);
					}
					for(var i = 0; i < data.length; i++) {
						data[i].ipAddressWithStatus = data[i].ipAddress + "(" + data[i].usageStatusName + ")";
					}
					var source ={
						datatype: "json",
						datafields: [
							{ name: 'ipAddress' },
							{ name: 'ipAddressWithStatus' }
						],
						localdata:data
					};
					var dataAdapter = new $.jqx.dataAdapter(source);
					dropdownCmp.jqxDropDownList({source: dataAdapter});
					if(data.length > 0) {
						$("#"+id+"").jqxDropDownList('insertAt', { label:"自动分配" ,value:"自动分配"},0);
					}
					$("#"+id+"").jqxDropDownList('selectIndex', 0 );
				}
			});
		}

	};

	this.createIpAddressDropDownList = function (elem, netSid) {
		elem.jqxDropDownList({
			placeHolder: "请选择...",
			width: 220,
			height: 22,
			autoDropDownHeight :false,
			dropDownWidth :220,
			dropDownHeight :180,
			disabled:false,
			theme:currentTheme
		});
		if(!netSid || netSid === "") {
			elem.jqxDropDownList('clear');
			return;
		}
		if(netSid == '自动分配') {
			elem.jqxDropDownList('clear');
			elem.jqxDropDownList('insertAt', { label:"自动分配" ,value:"自动分配"},0);
			elem.jqxDropDownList('selectIndex', 0 );
		}else{
			Core.AjaxRequest({
				url :ws_url +"/rest/ip/network/"+ netSid,
				type:'get',
				async:false,
				callback : function (data) {
					for(var i = 0; i < data.length; i++) {
						data[i].ipAddressWithStatus = data[i].ipAddress + "(" + data[i].usageStatusName + ")";
					}
					var source ={
						datatype: "json",
						datafields: [
							{ name: 'ipAddress' },
							{ name: 'ipAddressWithStatus' }
						],
						localdata:data
					};
					var dataAdapter = new $.jqx.dataAdapter(source);
					elem.jqxDropDownList({source: dataAdapter});
//						if(data.length > 0) {
					elem.jqxDropDownList('insertAt', { label:"自动分配" ,value:"自动分配"},0);
//						}
					elem.jqxDropDownList('selectIndex', 0 );
				}
			});
		}
	};
	/******************************************工单初始化*****************************************************************/

	/******************************************工单分配*****************************************************************/
		// 分配用户
	this.searchTicketMgtAllocate = function(options){
		var defaultSearch = {

		};

		var obj = $.extend(defaultSearch, options);

		Core.AjaxRequest({
			url : ws_url + "/rest/tickets/platform/findAllocateUserSelect",
			type:'post',
			params:obj,
			async:false,
			callback : function (data) {
				me.itemsTicketMgtAllocate(data);
				me.initTicketMgtAllocateDatagrid(me.itemsTicketMgtAllocate);
			}
		});
	};

	// 初始化用户datagrid的数据源
	this.initTicketMgtAllocateDatagrid = function(data){

		var sourcedatagrid ={
			datatype: "json",
			localdata: data
		};

		var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
		$("#jqxgridAllocate").jqxGrid(
			{
				width: "98%",
				height:"250px",
				theme:currentTheme,
				source: dataAdapter,
				columnsresize: true,
				pageable: true,
				pagesize: 10,
				sortable: false,
				autoheight: false,
				selectionmode : "checkbox",
				localization: gridLocalizationObj,
				columns: [
					{ text: '用户名称', datafield: 'realName'},
					{ text: '用户账号', datafield: 'account'},
					{ text: '电子邮件', datafield: 'email' },
					{ text: '电话', datafield: 'mobile' }
				],
				showtoolbar: true,
				rendertoolbar: function (toolbar) {

				}
			});

	};

	//
	this.allocateTicketMgtUser = function(ticketSid){
		var rowindexs = [];
		var allocateUsers=[];
		rowindexs = $("#jqxgridAllocate").jqxGrid('getselectedrowindexes');

		var gridValidation = true;
		$.each(rowindexs, function(i, n) {
			var value = $("#jqxgridAllocate").jqxGrid('getcellvalue', n, "account");
			if(value === "" || value == null) {
				gridValidation = false;
				return false;
			} else {
				var data = $("#jqxgridAllocate").jqxGrid('getrowdata', n);
				allocateUsers[i] = data;
			}
		});

		if(!gridValidation){
			Core.alert({
				title:"提示",
				message:"请选择分配用户",
				confirmCallback:function(){
				}
			});
			return false;
		}

		var param = {};
		param.ticketSid = $("#allocate-ticketSid").val();
		param.allocateUsers = allocateUsers;

		Core.AjaxRequest({
			url : ws_url + "/rest/tickets/platform/updateAllocateUser",
			params :param,
			callback : function (data) {
				$('#jqxgridTicket').jqxGrid('clearselection');
				$("#allocateBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
				$("#processBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
				$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
				me.searchTicketMgt();
				$("#popupAllocateWindow").jqxWindow('close');
				$("#jqxgridAllocate").jqxGrid('clearselection');

			},
			failure:function(data){
//			    	$("#popupAllocateWindow").jqxWindow('close');
			}
		});
	};
	/******************************************工单分配*****************************************************************/
	/******************************************工单查看*****************************************************************/

		// 查看工单
	this.searchTicketMgtView = function(ticketData){
		//初始化基本信息
		$("#view-ticketNo").html(ticketData.ticketNo);
		$("#view-tenantName").html(ticketData.tenantName);
		$("#view-ticketUser").html(ticketData.ticketUser);
		$("#view-statusName").html(ticketData.statusName);
		$("#view-questionTypeName").html(ticketData.questionTypeName);
		$("#view-questionLevelName").html(ticketData.questionLevelName);
		$("#view-serviceName").html(ticketData.serviceName);
		$("#view-createdDt").html(ticketData.createdDt);
		$("#view-title").html(ticketData.title);
		$("#view-content").html(ticketData.content);

		var defaultSearch = {

		};

		//初始化工单记录
		Core.AjaxRequest({
			url : ws_url + "/rest/tickets/platform/" + ticketData.ticketSid + "/ticketRecord",
			type:'post',
			params:defaultSearch,
			async:false,
			callback : function (data) {
				me.itemsTicketMgtView(data);
				me.initTicketMgtViewDatagrid(data);
			}
		});
	};

	// 重新执行工单
	this.searchTicketMgtExecute = function(ticketData){
		//初始化基本信息
		$("#execute-ticketNo").html(ticketData.ticketNo);
		$("#execute-tenantName").html(ticketData.tenantName);
		$("#executeview-ticketUser").html(ticketData.ticketUser);
		$("#execute-statusName").html(ticketData.statusName);
		$("#execute-questionTypeName").html(ticketData.questionTypeName);
		$("#execute-questionLevelName").html(ticketData.questionLevelName);
		$("#execute-serviceName").html(ticketData.serviceName);
		$("#execute-createdDt").html(ticketData.createdDt);
		$("#execute-title").html(ticketData.title);
		$("#execute-content").html(ticketData.content);

//    	 var defaultSearch = {
//    			 
//         };

//    	 //初始化工单记录
//    	 Core.AjaxRequest({
//    		url : ws_url + "/rest/tickets/platform/" + ticketData.ticketSid + "/ticketRecord",
// 			type:'post',
// 			params:defaultSearch,
// 			async:false,
// 			callback : function (data) {
// 				me.itemsTicketMgtView(data);
// 				me.initTicketMgtViewDatagrid(me.itemsTicketMgtView);
// 			}
// 		 });
	};

	// 初始化查看工单datagrid的数据源
	this.initTicketMgtViewDatagrid = function(data){
		var sourcedatagrid ={
			datatype: "json",
			localdata: data
		};

		var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
		$("#jqxgridView").jqxGrid(
			{
				width: "100%",
				height:"190px",
				theme:currentTheme,
				source: dataAdapter,
				columnsresize: true,
				pageable: true,
				pagesize: 6,
				autoheight: false,
				localization: gridLocalizationObj,
				columns: [
					{ text: '工单编号', datafield: 'ticketNo'},
					{ text: '处理人', datafield: 'operator'},
					{ text: '操作', datafield: 'operateName' },
					{ text: '处理内容', datafield: 'operateContent' },
					{ text: '处理时间', datafield: 'operateTime' }
				]
			});
	};
	/******************************************工单查看*****************************************************************/

	/******************************************工单处理*****************************************************************/

		// 处理工单
	this.searchTicketMgtProcess = function(ticketData){
		$("#wanTr").hide();
		$("#lanTr").hide();
		$("#rescomuteTr").hide();

		//初始化基本信息
		$("#process-ticketNo").html(ticketData.ticketNo);
		$("#process-tenantName").html(ticketData.tenantName);
		$("#process-ticketUser").html(ticketData.ticketUser);
		$("#process-statusName").html(ticketData.statusName);
		$("#process-questionTypeName").html(ticketData.questionTypeName);
		$("#process-questionLevelName").html(ticketData.questionLevelName);
		$("#process-serviceName").html(ticketData.serviceName);
		$("#process-createdDt").html(ticketData.createdDt);
		$("#process-title").html(ticketData.title);
		$("#process-content").html(ticketData.content);
		$("#process-ticketSid").val(ticketData.ticketSid);
		$("#processObjectId").val(ticketData.processObjectId);
		$("#processType").val(ticketData.processType);


		if(ticketData.autoHandlerFlag === 1) {
			$("#autoHandlerTabHeader").show();
//			 if(ticketData.processType == '01') {
//				 $("#openResSelect").show();
//				 $("#changeResSelect").hide();
//				 $("#instanceNetGrid").hide();
//			 } else if(ticketData.processType == '12') {
//				 $("#changeResSelect").show();
//				 $("#openResSelect").hide();
//			 }
		} else {
			$("#autoHandlerTabHeader").hide();
		}
		var defaultSearch = {

		};
		var rowData = ticketData;
//	    if(rowData.processType == "02") {
//	    	$("#rescomuteTr").hide();
//			$("#wanTr").hide();
//			$("#lanTr").hide();
//			$("#resCheckSubmit").hide();
//			$("#executeTicket").hide();
//	    } 
		//如果是可以自动处理的
		if(rowData.autoHandlerFlag === 1) {
			var autoHandler = new ticketAutoHandlerModel();
			autoHandler.initAutoHandler(rowData);
		}
		Core.AjaxRequest({
			type : 'get',
			url : ws_url + "/rest/serviceInstances/" + rowData.processObjectId,
			async:false,
			callback : function (data) {
				$("#ticket-mgtObjSid").val(data.mgtObjSid);
			}
		});
		var changeLogData ={};
		if(rowData.processType != "07"){
			Core.AjaxRequest({
				type : 'get',
				url : ws_url + "/rest/serviceInstances/getLastChangeLog/" + rowData.processObjectId,
				async:false,
				callback : function (data) {
					changeLogData = data;
					$("#ticket-resType").val(data.variables.resType);
					$("#ticket-ve").val(data.variables.ve);
				}
			});
		}
//    	//根据工单类型来判断手动处理部分的显示内容
//    	if (rowData.processType == "04") {
//    		//物理机开通失败工单的处理
//    		var instanceSid = rowData.processObjectId;
//    		//得到磁盘，生成挑选磁盘的页面
//    		$("#aixDiskTicket").html("");
//       	 	Core.AjaxRequest({
//       	 		url : ws_url + "/rest/serviceInstances/getVdInstance/" + instanceSid,
//    			type:'get',
//    			async:false,
//    			callback : function (data) {
//    				 for(var i =0;i<data.length;i++){
//    					 var tr = "<tr><td align='right' width='140px' height='25px'>" +
//    					 		      "<input instanceSid='"+data[i].instanceSid+"' type='hidden' />"+data[i].instanceName+":</td>" +
//    					 		  "<td width='170px'><div id='disk"+i+"'></div></td>" +
//    					 		  "<td><input type='button' onclick='matchDisk(this)' id='match"+i+"' value='匹配' /></td></tr>";
//    					 $("#aixDiskTicket").append(tr);
//    					 
//    					 $("#match"+i).jqxButton({ width: '60',theme:currentTheme,height: '22px',  disabled: false});
//    					 //查询配件表
//    					 var diskPurpose = "";
//    					 var specs = data[i].serviceInstanceSpec;
//    					 for(var j=0;j<specs.length;j++){
//    						 if(specs[j].name=="STORAGE_PURPOSE"){
//    							 diskPurpose = specs[j].value;
//    						 }
//    					 }
//    					 var condition = {};
//    					 if(diskPurpose=="01"){
//    						 //系统盘需要查询本地盘
//    						 condition["hostItemTypeCode"] = "1";
//    					 }else if(diskPurpose=="02"){
//    						 //外置存储盘需要查询光纤卡
//    						 condition["hostItemTypeCode"] = "2";
//    					 }
//    					 condition["resAllocStatus"] = "0";
//    					 Core.AjaxRequest({
//    			       	 		url : ws_url + "/rest/hostItems",
//    			    			type:'post',
//    			    			params:condition,
//    			    			async:false,
//    			    			callback : function (data) {
//	    			    			 $("#disk"+i).jqxDropDownList({
//	    			 	                   source: data,
//	    			 	                   displayMember: "hostItemId", 
//	    			 	                   valueMember: "hostItemId",
//	    			 	                   width: 150,
//	    			 	                   height: 22,
//	    			 	                   dropDownHeight : 150,
//	    			 	                   theme:"metro"
//	    			 	             });
//    			    			}
//    					 });
//    				 }
//    			}
//    		});
//       	 	$("#aixDiskTicket").show();
//       	 	$("#hostDiskDiv").hide();
//       	 	$("#vmOpenTicket").hide();
//       	 	$("#hostOpenTicket").hide();
//    	}else 
		if(rowData.processType == "02"){
			$("#vmOpen-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
			$("#matchVmOpenName").jqxButton({ width: '50',height:"25",theme:currentTheme});
			//初始化已匹配的
			Core.AjaxRequest({
				url : ws_url + "/rest/vms/findVmInfoByServiceInstance",
				type:'post',
				params:{
					instanceSid:ticketData.processObjectId
				},
				async:false,
				callback : function (data) {
					if(data.vmName!=null){
						$("#processTicket").jqxButton({disabled: false});
					}
					$("#vmOpen-name").val(data.vmName);
				}
			});

			$("#vmOpenTicket").show();
			$("#hostDiskDiv").hide();
			$("#hostOpenTicket").hide();
			$("#aixDiskTicket").hide();
		}else if(rowData.processType == "03"){
			//物理机开通失败工单的处理
			var instanceSid = rowData.processObjectId;
			$("#ticket-partitionType").val("0");
			Core.AjaxRequest({
				type : 'post',
				url : ws_url + "/rest/mgtObj/findMgtObjComTree",
				async: false,
				params: {
					resPoolType: me.getTicketResPoolType(),
					mgtObjSid: $("#ticket-mgtObjSid").val()
				},
				callback: function (treeData) {
					$("#hostOpen-name").jqxDropDownList({
						source: treeData,
						displayMember: "resTopologyName",
						valueMember: "resTopologySid",
						width: 150,
						height: 22,
						dropDownHeight : 150,
						theme:"metro"
					});
				}
			});
			$('#hostOpen-name').on('change', function (event) {
				sysDiskData = new Array;
				dataDisks = new Array;
				if($(this).val() !== ""){
					Core.AjaxRequest({
						type : 'post',
						url : ws_url + "/rest/storage/host",
						async: false,
						params: {
							resHostSid: $(this).val()
						},
						callback: function (treeData) {
							for(var i=0;i<treeData.length;i++){
								if(treeData[i].storagePurpose=="01"){
									sysDiskData.push(treeData[i]);
								}else{
									dataDisks.push(treeData[i]);
								}
							}
							if(treeData.length!=$("div[id^='host-disk']").length){
								Core.alert({
									title:"提示",
									message:"该主机的存储和申请的不一致，请重新选择！"
								});
							}else{
								var sysDiskNum = 0;
								for(var i = 0;i<$("div[id^='host-disk']").length;i++){
									$($("div[id^='host-disk']")[i]).jqxDropDownList('clear');
									if($($("div[id^='host-disk']")[i]).attr("purpose")=='01'){
										sysDiskNum++;
										$($("div[id^='host-disk']")[i]).jqxDropDownList({
											source: sysDiskData,
											selectedIndex:0,
											displayMember: "storageName",
											valueMember: "resStorageSid",
											width: 150,
											height: 22,
											dropDownHeight : 150,
											theme:"metro"
										});
									}else{
										$($("div[id^='host-disk']")[i]).jqxDropDownList({
											source: dataDisks,
											selectedIndex:0,
											displayMember: "storageName",
											valueMember: "resStorageSid",
											width: 150,
											height: 22,
											dropDownHeight : 150,
											theme:"metro"
										});
										//这个下拉框选择的磁盘不能出现在其他的下拉框中
										$($("div[id^='host-disk']")[i]).on('open' , function (event) {
											var diskId = $(this).attr('id');
											var nowDiskData = new Array;
											for(var j=0;j<dataDisks.length;j++){
												var diskFlag = true;
												for(var d=0;d<$("div[id^='host-disk']").length;d++){
													if($($("div[id^='host-disk']")).attr('id')!=diskId){
														if(dataDisks[j].resStorageSid == $($("div[id^='host-disk']")).val()){
															diskFlag = false;
														}
													}
												}
												if(diskFlag){
													nowDiskData.push(dataDisks[j]);
												}
											}
											$(this).jqxDropDownList({
												source: nowDiskData,
												selectedIndex:0,
												displayMember: "storageName",
												valueMember: "resStorageSid",
												width: 150,
												height: 22,
												dropDownHeight : 150,
												theme:"metro"
											});
										});
									}
								}
								if(sysDiskNum!=sysDiskData.length){
									Core.alert({
										title:"提示",
										message:"该主机的存储和申请的不一致，请重新选择！"
									});
									return;
								}
							}
						}
					});
				}else{
					for(var i=0;i<$("div[id^='host-disk']").length;i++){
						$($("div[id^='host-disk']")[i]).jqxDropDownList('clear');
					}
				}
			});

			//得到磁盘，生成挑选磁盘的页面
			$("#hostDiskDiv").html("");
			Core.AjaxRequest({
				url : ws_url + "/rest/serviceInstances/getVdInstance/" + instanceSid,
				type:'get',
				async:false,
				callback : function (data) {
					for(var i =0;i<data.length;i++){
						//查询storage
						var diskPurpose = "";
						var specs = data[i].serviceInstanceSpec;
						for(var j=0;j<specs.length;j++){
							if(specs[j].name=="STORAGE_PURPOSE"){
								diskPurpose = specs[j].value;
							}
						}

						var tr = "<tr><td align='right' width='140px' height='25px'>" +
							"<input instanceSid='"+data[i].instanceSid+"' type='hidden' />"+data[i].instanceName+":</td>" +
							"<td width='170px'><div id='host-disk"+i+"' purpose='"+diskPurpose+"' instanceSid='"+data[i].instanceSid+"' ></div></td>" +
							"<td><input type='button' onclick='matchHostDisk(this)' id='host-match-"+i+"' value='匹配' /></td></tr>";
						$("#hostDiskDiv").append(tr);

						$("#host-match-"+i).jqxButton({ width: '60',theme:currentTheme,height: '22px',  disabled: false});

						$("#host-disk"+i).jqxDropDownList({
							width: 150,
							height: 22,
							dropDownHeight : 150,
							theme:"metro"
						});
					}
				}
			});
			$("#aixDiskTicket").hide();
			$("#vmOpenTicket").hide();
			$("#hostDiskDiv").show();
			$("#hostOpenTicket").show();

			$('#hostOpen-name').val(changeLogData.variables.hostId);
			$('#hostOpen-name').trigger('change');

		}else{
			$("#aixDiskTicket").hide();
			$("#hostOpenTicket").hide();
			$("#hostDiskDiv").hide();
			$("#vmOpenTicket").hide();
			$("#processTicket").jqxButton({disabled: false});
		}


		//设置当前操作者和是时间
		Core.AjaxRequest({
			url : ws_url + "/rest/tickets/platform/findTicketBySid/" + ticketData.ticketSid,
			type:'get',
			callback : function (data) {
				$("#process-operator").html(data.operator);
				$("#process-operateTime").html(data.operateTime);
				$("#operator").val(data.operator);
				$("#operateTime").val(data.operateTime);
			}
		});

		//初始化工单记录
		Core.AjaxRequest({
			url : ws_url + "/rest/tickets/platform/" + ticketData.ticketSid + "/ticketRecord",
			type:'post',
			params:defaultSearch,
			async:false,
			callback : function (data) {
				me.itemsTicketMgtProcess(data);
				me.initTicketMgtProcessDatagrid(data);
			}
		});

	};

	this.getTicketResPoolType = function () {
		var ve = $("#ticket-ve").val();
		var partitionType = $("#ticket-partitionType").val();
		var resType = $("#ticket-resType").val();
		var resPoolType = "";
		if(ve == 'HMC,IVM') {
			if(partitionType == '1') {
				resPoolType = "PCVP";
			} else if(partitionType == '0'){
				resPoolType = "PCP";
			}
		} else if(ve == 'VMware') {
			if(resType == '1') {
				resPoolType = "PCVX";
			} else if(resType == '2') {
				resPoolType = "PCX";
			}
		}
		return resPoolType;
	};

	// 自定义数据字典方法
	this.getCustomCode1 =function(editor,url,fieldText,valueText,nullItem,methodType,params,selected){
		Core.AjaxRequest({
			url :ws_url +"/rest"+ url,
			type:methodType = null ? "get":methodType,
			params:params = null ? "":params,
			async:false,
			callback : function (data) {
				var source ={
					datatype: "json",
					datafields: [
						{ name: fieldText },
						{ name: valueText }
					],
					localdata:data
				};

				var settings ={
					message: "没有可以选择的资源，请检查后再试"
				};

				if (data.length < 1) {
					Core.alert(settings);
					obj.isEmpty = true;
					return;
				}
				else {
					obj.isEmpty = false;
				}

				var i = 0;
				$.each(data, function (i, row) {
					if (row.resSetType == selected) {
						obj.selectedIndex = i;
						return false;
					}
				});


				var dataAdapter = new $.jqx.dataAdapter(source);
				editor.jqxDropDownList({
					placeHolder: "请选择...",
					selectedIndex: 0,
					source: dataAdapter,
					displayMember: fieldText,
					valueMember: valueText,
					width: 250,
					//height: 200,
					autoDropDownHeight: true,

					//dropDownWidth :200,
					//dropDownHeight :200,
					disabled: false,
					theme:"metro"
				});
				// 判断是否显示空白行
				if(typeof(nullItem) == "boolean"&& nullItem ){
					$("#"+id+"").jqxDropDownList('insertAt', { label:"全部" ,value:""},0);
				}
			}
		});
	};

	this.getNetsSpecIpValue = function (nets, vlanSid) {
		var ipAddress = '';
		for(var i = 0; i < nets.length; i++) {
			if(nets[i].resNetworkId === vlanSid) {
				ipAddress = nets[i].ipAddress;
				break;
			}
		}
		return ipAddress;
	};

	this.getSpecByName = function (specs, specName) {
		for(var i = 0; i < specs.length; i++) {
			if(specs[i].name == specName) {
				return specs[i].value;
			}
		}
	};

	// 初始化查看工单datagrid的数据源
	this.initTicketMgtProcessDatagrid = function(data){
		var sourcedatagrid ={
			datatype: "json",
			localdata: data
		};

		var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
		$("#jqxgridProcess").jqxGrid(
			{
				width: "778px",
				height:"176px",
				theme:currentTheme,
				source: dataAdapter,
				columnsresize: true,
				pageable: true,
				pagesize: 10,
				autoheight: false,
				sortable: true,
				localization: gridLocalizationObj,
				columns: [
					{ text: '工单编号', datafield: 'ticketNo'},
					{ text: '处理人', datafield: 'operator'},
					{ text: '操作', datafield: 'operateName' },
					{ text: '处理内容', datafield: 'operateContent' },
					{ text: '处理时间', datafield: 'operateTime' }
				]
			});
	};
	/******************************************工单处理*****************************************************************/

		// 初始化验证规则
	this.initValidator = function(){
		$('#processForm').jqxValidator({
			rules: [
				{ input: '#process-operateContent', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
				{ input: '#process-operateContent', message: '目录名称为0-256个字符!', action: 'keyup, blur', rule: 'length=0,256' }
			]
		});

		// 新增用户表单验证成功
		$('#processForm').on('validationSuccess', function (event) {

			var rowindex = $('#jqxgridTicket').jqxGrid('getselectedrowindex');
			// 判断审核按钮是否可用
			var ok = $("#processTicket").jqxButton("disabled");
			if (rowindex >= 0 && !ok) {
				var data = $('#jqxgridTicket').jqxGrid('getrowdata', rowindex);

				var param = {};
				param.ticketSid = data.ticketSid;
				param.action = $("#operate").val();
				param.operator = $("#operator").val();
				param.operateTime = $("#operateTime").val();
				param.operateContent = $("#process-operateContent").val();
				param.operateStatus = $("#process-operateStatus").val();

				Core.AjaxRequest({
					url : ws_url + "/rest/tickets/platform/operationTicket",
					params :param,
					callback : function (data) {
						$('#jqxgridTicket').jqxGrid('clearselection');
						$("#allocateBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
						$("#processBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
						$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
						me.searchTicketMgt();
						$("#process-operateContent").val("");
						$("#popupProcessWindow").jqxWindow('close');
					},
					failure:function(data){
						$("#popupProcessWindow").jqxWindow('close');

					}
				});

			}
		});

		//添加工单的验证
		$('#addTicketForm').jqxValidator({
			rules: [
				{ input: '#ticketTitle', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
				{ input: '#ticketTitle', message: '目录名称为0-256个字符!', action: 'keyup, blur', rule: 'length=0,256' },
				{ input: '#ticketContent', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
				{ input: '#ticketContent', message: '目录名称为0-256个字符!', action: 'keyup, blur', rule: 'length=0,256' }
			]
		});

		// 新增表单验证成功
		$('#addTicketForm').on('validationSuccess', function (event) {
			var param = {};
			param.processType = "01";
			param.questionType = "03";
//			param.questionLevel = $("#questionLevels").val();
			param.title = $("#ticketTitle").val();
			param.content = $("#ticketContent").val();
//			param.autoHandlerFlag = $("#autoHandlerFlag").val();
			param.autoHandlerFlag = "0";

			Core.AjaxRequest({
				url : ws_url + "/rest/tickets/platform/createTicket",
				params :{
					title : $("#ticketTitle").val(),
					content : $("#ticketContent").val()
				},
				async:false,
				callback : function (data) {
					$('#jqxgridTicket').jqxGrid('clearselection');
					$("#allocateBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
					$("#processBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
					$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
					me.searchTicketMgt();
					$("#process-operateContent").val("");
					$("#popAddTicketWindow").jqxWindow('close');

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
								window.parent.$("#menuContent").find("b:contains('工单管理')").parents("li").addClass("liShow");
							}
						});
					}
				},
				failure:function(data){
					$("#popAddTicketWindow").jqxWindow('close');
				}
			});
		});
	};
	this.initPendingValidator = function(){
		$('#processForm').jqxValidator({
			rules: [
				{ input: '#process-operateContent', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
				{ input: '#process-operateContent', message: '目录名称为0-256个字符!', action: 'keyup, blur', rule: 'length=0,256' }
			]
		});
		// 新增用户表单验证成功
		$('#processForm').on('validationSuccess', function (event) {

			var rowindex = $('#jqxgridTicket').jqxGrid('getselectedrowindex');
			// 判断审核按钮是否可用
			var ok = $("#processTicket").jqxButton("disabled");
			if (rowindex >= 0 && !ok) {
				var data = $('#jqxgridTicket').jqxGrid('getrowdata', rowindex);

				var param = {};
				param.ticketSid = data.ticketSid;
				param.action = $("#operate").val();
				param.operator = $("#operator").val();
				param.operateTime = $("#operateTime").val();
				param.operateContent = $("#process-operateContent").val();
				param.operateStatus = $("#process-operateStatus").val();

				Core.AjaxRequest({
					url : ws_url + "/rest/tickets/platform/operationTicket",
					params :param,
					callback : function (data) {
						$('#jqxgridTicket').jqxGrid('clearselection');
						$("#allocateBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
						$("#processBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
						$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
						me.searchTicketMgt();
						$("#process-operateContent").val("");
						$("#popupProcessWindow").jqxWindow('close');
					},
					failure:function(data){
						$("#popupProcessWindow").jqxWindow('close');

					}
				});

			}
		});
	};
};

function submitAddTicket(){
	$('#addTicketForm').jqxValidator('validate');
}


//磁盘的匹配
function matchDisk(obj){
	//得到选择的资源
	var diskChooseDiv = $($($(obj).parents("tr")[0]).find("td:eq(1)")[0]).children()[0];
	var diskChooseSid = $(diskChooseDiv).val();

	var instanceSid = $($($($(obj).parents("tr")[0]).find("td:eq(0)")[0]).children()[0]).attr("instancesid");
	/*//进行匹配
	 Core.AjaxRequest({
	 url : ws_url + "/rest/",
	 type:'post',
	 params:{
	 instanceSid:instanceSid,
	 resSid:diskChooseSid
	 },
	 async:false,
	 callback : function (data) {
	 }
	 });*/
}

//匹配虚机
function matchVmName(){
	var vmName = $("#vmOpen-name").val();
	var instanceSid = $("#processObjectId").val();
	if(vmName === ""||vmName==null){
		Core.alert({
			title:"提示",
			message:"请输入要匹配的云主机名称！"
		});
		return;
	}else if(instanceSid === ""||instanceSid==null){
		Core.alert({
			title:"提示",
			message:"获取相关实例出错！"
		});
		return;
	}else{
		Core.AjaxRequest({
			url : ws_url + "/rest/serviceInstances/matchInstanceAndVm",
			type:'post',
			params:{
				instanceSid:instanceSid,
				vmName:vmName
			},
			async:false,
			callback : function (data) {
				$("#processTicket").jqxButton({disabled: false});
			}
		});
	}
}

function matchHostDisk(obj){
	var instanceSid = $("#processObjectId").val();
	var hostSid = $("#hostOpen-name").val();
	var index = $(obj).attr("id").split("host-match-")[1];
	var vdInstance = $("#host-disk"+index).attr("instanceSid");
	var storageSid = $("#host-disk"+index).val();

	if(instanceSid === ""||instanceSid==null||hostSid === ""||hostSid==null
		||vdInstance === ""||vdInstance==null||storageSid === ""||storageSid==null){
		Core.alert({
			title:"提示",
			message:"匹配信息出错，请检查！"
		});
		return;
	}else{
		Core.AjaxRequest({
			url : ws_url + "/rest/serviceInstances/matchInstanceHostAndStorage",
			type:'post',
			params:{
				instanceSid:instanceSid,
				hostSid:hostSid,
				vdInstance:vdInstance,
				storageSid:storageSid
			},
			async:false,
			callback : function (data) {
				$("#host-disk"+index).attr("matchFlag","1");
				$("#processTicket").jqxButton({disabled: false});
			}
		});
	}
	var diskArray = $("div[id^='host-disk']");
	var matchFlags = true;
	for(var i=0;i<diskArray.length;i++){
		if("1"!=$(diskArray[i]).attr("matchFlag")){
			matchFlags = false;
		}
	}
	if(matchFlags){
		$("#processTicket").jqxButton({disabled: false});
	}
}
  
  
  