var bizTypeModel = function () {
	var me = this;
	this.itemsBizType = ko.observableArray();
	var bizTypeSid = null;
	var level = null;
	//存储当前选择业务类型节点信息
	var bizType = {};
	// 定义右击弹出框
	var contextMenu;

	//左侧业务类型Tree
	var bizTypeTree;

	var quotaChanges = [];

	//初始化页面中的jqx组件
	this.initInput = function(){
		$("#search-biz-type-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
		$("#search-biz-type-property").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
		$("#search-biz-type-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
		$('#jqxTabsBiz').jqxTabs({width : '84.5%', height : '99.2%', position : 'top', theme : currentTheme});
		$('#jqxTreeBiz').jqxTree({height: '100%', width: '100%', theme : currentTheme});
		$('#jqxExpanderBiz').jqxExpander({width:'14.9%', height:'100%', theme : currentTheme});
		$("#jqxgridBiz").jqxGrid({
			width: "98%",
			height: "340px",
			theme:currentTheme,
			columnsresize: true,
			pageable: true, 
			pagesize: 10, 
			autoheight: false,
			localization: gridLocalizationObj,
			selectionmode:"singlerow",
			columns: [
				{ text: '项目名称', datafield: 'name'},
				{ text: '业务属性', datafield: 'parentBizName'},
				{ text: '业务类型描述', datafield: 'description' },
				{ text: '创建人', datafield: 'createdBy' }
			],
			showtoolbar: true,
			rendertoolbar: function (toolbar) {
				var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
				var orgAddBtn = $("<div><a id='bizTypeAddBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增业务&nbsp;&nbsp;</a></div>");
				var orgEditBtn = $("<div><a id='bizTypeEditBtn'  style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑业务&nbsp;&nbsp;</a></div>");
				var bizTypeQuotaBtn = $("<div><a id='bizTypeQuotaBtn'  style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>设置配额&nbsp;&nbsp;</a></div>");
				var orgDelBtn = $("<div><a id='bizTypeDelBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除业务&nbsp;&nbsp;</a></div>");
				toolbar.append(container);
				container.append(orgAddBtn);
				container.append(orgEditBtn);
				container.append(bizTypeQuotaBtn);
				container.append(orgDelBtn);

				$("#bizTypeAddBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
				$("#bizTypeEditBtn").jqxButton({ width: '80',theme:currentTheme,disabled: true});
				$("#bizTypeQuotaBtn").jqxButton({ width: '80',theme:currentTheme,disabled: true});
				$("#bizTypeDelBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
			}
		});

		//配额设置弹出窗口控件初始化-start
		$("#setQuotaWindow").jqxWindow({
			width: 700, 
			height:420,
			resizable: false,  
			isModal: true, 
			theme:currentTheme,
			autoOpen: false, 
			cancelButton: $("#cancelQuotaInfo"), 
			modalOpacity: 0.3
		});

		var quotaTypeSource = {
				datatype: "json",
				datafields: [
				{ name: 'codeValue' },
				{ name: 'codeDisplay' }
				],
				localdata: [{"codeValue":"cores","codeDisplay":"虚拟机核数"},{"codeValue":"ram","codeDisplay":"虚拟机内存(GB)"},
				{"codeValue":"vLanIdoLimit","codeDisplay":"业务外网IP数"},{"codeValue":"vLanIdiLimit","codeDisplay":"业务内网IP数"}]
		};
		var quotaTypeDataAdapter = new $.jqx.dataAdapter(quotaTypeSource);
		$("#set-quotaType").jqxDropDownList({
			source: quotaTypeDataAdapter,
			displayMember: "codeDisplay",
			valueMember: "codeValue",
			width: 250,
			height: 23,
			autoDropDownHeight : true,
			theme:currentTheme
		});
		$("#saveQuotaInfo").jqxButton({theme:currentTheme});
		$("#cancelQuotaInfo").jqxButton({theme:currentTheme});
		//配额设置弹出窗口控件初始化-end

		//新增业务弹出窗口初始化-start
		$("#addBizTypeWindow").jqxWindow({
			width: 400, 
			height:520,
			resizable: false,  
			isModal: true, 
			autoOpen: false, 
			theme:currentTheme,
			cancelButton: $("#cancelAddBizType"), 
			modalOpacity: 0.3           
		});
		$("#add-bizName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		$("#add-parentBizName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme, disabled: true});
		//$("#add-biz-sortRank").jqxNumberInput({ width: '150px', height: '22px', inputMode: 'simple',theme:currentTheme, spinButtons: true,decimalDigits:0, min: 0, max: 10, value: 0});
		$("#add-bizDesc").jqxInput({placeHolder: "", height: 46, width: 250, minLength: 1,theme:currentTheme});
		$("#add-owner").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		$("#add-ownerTel").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		$("#add-pmTel").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		$("#add-pmEmail").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		$("#add-pm").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		$("#add-biz-status").jqxDropDownList({
			selectedIndex: 0, 
			width: 250,
			height: 23,
			autoDropDownHeight : true,
			theme:currentTheme
		});
		var addcanConfig = {
				selectedIndex: 0, 
				width: 250,
				height: 23,
				autoDropDownHeight : true,
				theme:currentTheme
		};
		$("#add-fwPort").jqxDropDownList(addcanConfig);
		$("#add-isBizCont").jqxDropDownList(addcanConfig);
		$("#add-isProNo").jqxDropDownList(addcanConfig);
		$("#add-isProAttach").jqxDropDownList(addcanConfig);
		$("#edit-createDt").jqxDateTimeInput({width: '250px', culture: 'zh-CN', formatString: 'd', height: 22,showFooter:true,clearString:'清除',todayString:'今天',allowNullDate: true,value: null,theme:"metro"});
		$("#saveAddBizType").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
		$("#cancelAddBizType").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
		//新增业务弹出窗口初始化-end

		//编辑业务弹出窗口初始化-start
		$("#editBizTypeWindow").jqxWindow({
			width: 400, 
			height:520,
			resizable: false,  
			isModal: true, 
			theme:currentTheme,
			autoOpen: false, 
			cancelButton: $("#cancelEditBizType"), 
			modalOpacity: 0.3
		});
		$("#edit-bizName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		$("#edit-parentBizName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme, disabled: true});
		//$("#edit-biz-sortRank").jqxNumberInput({ width: '150px', height: '22px', inputMode: 'simple',theme:currentTheme, spinButtons: true,decimalDigits:0, min: 0, max: 10, value: 0});
		$("#edit-bizDesc").jqxInput({placeHolder: "", height: 46, width: 250, minLength: 1,theme:currentTheme});
		$("#edit-owner").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		$("#edit-ownerTel").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		$("#edit-pmTel").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		$("#edit-pmEmail").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		$("#edit-pm").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		$("#edit-biz-status").jqxDropDownList({
			selectedIndex: 0, 
			width: 250,
			height: 23,
			autoDropDownHeight : true,
			theme:currentTheme
		});
		var addcanConfig = {
				selectedIndex: 0, 
				width: 250,
				height: 23,
				autoDropDownHeight : true,
				theme:currentTheme
		};
		$("#edit-fwPort").jqxDropDownList(addcanConfig);
		$("#edit-isBizCont").jqxDropDownList(addcanConfig);
		$("#edit-isProNo").jqxDropDownList(addcanConfig);
		$("#edit-isProAttach").jqxDropDownList(addcanConfig);
		$("#saveEditBizType").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
		$("#cancelEditBizType").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
		//编辑业务弹出窗口初始化-start

		$("#bizTypeResBtn").jqxButton({ width: '80',theme:currentTheme,disabled: true});
	};

	// 初始化业务类型树
	this.initBizTypeTree = function(){
		Core.AjaxRequest({
			url : ws_url + "/rest/bizType/findAllBizType",
			type:'post',
			callback : function (data) {
				var source ={
					datatype: "json",
					datafields: [
						{ name: 'bizSid' },
						{ name: 'parentBizSid' },
						{ name: 'name' },
						{ name: 'level' },
						{ name: 'description' }
					],
					id: 'bizSid',
					localdata: data
				};
				var dataAdapter = new $.jqx.dataAdapter(source);
				dataAdapter.dataBind();
				var records = dataAdapter.getRecordsHierarchy('bizSid', 'parentBizSid', 'items', [{ name: 'name', map: 'label'},{ name: 'bizSid', map: 'value'}, {name: 'level', map:'level'}]);
				$('#jqxTreeBiz').jqxTree({source: records});
				$('#jqxTreeBiz').jqxTree('expandItem', $("#jqxTreeBiz").jqxTree("getItems")[0]);
			}
		 });
	};

	//初始化业务datagrid
	this.initBizTypeDatagrid = function(data){
		if(bizTypeSid) {
			Core.AjaxRequest({
				url : ws_url + "/rest/bizType/"+bizTypeSid+"",
				params : {},
				type : 'get',
				callback : function(data) {
					var sourcedatagrid ={
						datatype: "json",
						localdata: data
					};
					var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
					$("#jqxgridBiz").jqxGrid({source: dataAdapter});
	 				$("#add-bizSid").val(bizTypeSid);
	 				$("#edit-bizSid").val(bizTypeSid);
				}
			});
		}
	};

	//弹出新增业务窗口
	this.popAddBizWindow = function(){
		var bizStatusSource =
		{
			datatype: "json",
			datafields: [
			{ name: 'codeValue' },
			{ name: 'codeDisplay' }
			],
			localdata: [{"codeValue":"01","codeDisplay":"有效"},{"codeValue":"02","codeDisplay":"无效"}]
		};
		var bizStatus_dataAdapter = new $.jqx.dataAdapter(bizStatusSource);
		$("#add-biz-status").jqxDropDownList({displayMember: "codeDisplay", valueMember: "codeValue", source: bizStatus_dataAdapter});
		
		var height = 520;
		if(level === 2) {
			$("tr.hide").hide();
			$("tr.pm").show();
			height = 420;
//		} else if(level === 1 || bizType.level === 0) {
//			$("tr.hide").show();
//			$("tr.pm").hide();
//			height = 420;
		}else if(level === 1){
			$("tr.hide").hide();
			$("tr.pm").show();
			height = 360;
		}else if(level === 0){
			$("tr.hide").show();
			$("tr.pm").hide();
			height = 420;
		}

		var codeMode = new codeModel({
			selectedIndex: 0, 
			displayMember: "codeDisplay", 
			valueMember: "codeValue",
			width: 250,
			height: 23,
			autoDropDownHeight : true,
			theme:currentTheme
		});
		codeMode.getCommonCode('add-networktype', 'ORDER_NETWORK_TYPE', false);

		var canSource = {
				datatype: "json",
				datafields: [
				{ name: 'codeValue' },
				{ name: 'codeDisplay' }
				],
				localdata: [{"codeValue":"1","codeDisplay":"否"},{"codeValue":"0","codeDisplay":"是"}]
		};
		var can_dataAdapter = new $.jqx.dataAdapter(canSource);
		var canConfig = {displayMember: "codeDisplay", valueMember: "codeValue", source: can_dataAdapter};
		$("#add-fwPort").jqxDropDownList(canConfig);
		$("#add-isBizCont").jqxDropDownList(canConfig);
		$("#add-isProNo").jqxDropDownList(canConfig);
		$("#add-isProAttach").jqxDropDownList(canConfig);

		if(bizType) {
			$("#add-parentBizName").val(bizType.bizName);
			$("#add-parentBizSid").val(bizType.bizSid);
			$("#add-level").val((bizType.level + 1) + "");
		} else {
			$("#add-level").val("0");
		}
		//清空页面赋值
		$("#add-bizName").val("");
		$("#add-bizDesc").val("");
		$("#add-owner").val("");
		$("#add-ownerTel").val("");
		$("#add-pmTel").val("");
		$("#add-pmEmail").val("");
		$("#add-pm").val("");
		$("#add-networktype").val("0");
		$("#add-fwPort").val("0");
		$("#add-isBizCont").val("0");
		$("#add-isProNo").val("0");
		$("#add-isProAttach").val("0");
	
		// 设置弹出框位置
		var windowW = $(window).width(); 
		var windowH = $(window).height(); 
		$("#addBizTypeWindow").jqxWindow({ height: height, position: { x: (windowW-400)/2, y: (windowH-height)/2 } });
		$("#addBizTypeWindow").jqxWindow('open');
	};

	//弹出编辑业务窗口
	this.popEditBizWindow = function(){
		var bizStatusSource = {
				datatype: "json",
				datafields: [
					{ name: 'codeValue' },
					{ name: 'codeDisplay' }
					],
					localdata: [{"codeValue":"01","codeDisplay":"有效"},{"codeValue":"02","codeDisplay":"无效"}]
		};
		var bizStatus_dataAdapter = new $.jqx.dataAdapter(bizStatusSource);
		$("#edit-biz-status").jqxDropDownList({displayMember: "codeDisplay", valueMember: "codeValue", source: bizStatus_dataAdapter});

		var height = 570;
		if(level === 2) {
			$("tr.hide").hide();
			$("tr.pm").show();
			height = 420;
		} else if(level === 1 || level === 0) {
			$("tr.hide").show();
			$("tr.pm").hide();
			height = 480;
		}
		var codeMode = new codeModel({
			selectedIndex: 0, 
			displayMember: "codeDisplay", 
			valueMember: "codeValue",
			width: 250,
			height: 23,
			autoDropDownHeight : true,
			theme:currentTheme
		});
		codeMode.getCommonCode('edit-networktype', 'ORDER_NETWORK_TYPE', false);

		var canSource = {
			datatype: "json",
			datafields: [
			{ name: 'codeValue' },
			{ name: 'codeDisplay' }
			],
			localdata: [{"codeValue":"1","codeDisplay":"否"},{"codeValue":"0","codeDisplay":"是"}]
		};
		var can_dataAdapter = new $.jqx.dataAdapter(canSource);
		var canConfig = {displayMember: "codeDisplay", valueMember: "codeValue", source: can_dataAdapter};
		$("#edit-fwPort").jqxDropDownList(canConfig);
		$("#edit-isBizCont").jqxDropDownList(canConfig);
		$("#edit-isProNo").jqxDropDownList(canConfig);
		$("#edit-isProAttach").jqxDropDownList(canConfig);
		//$("#edit-createDt").jqxDateTimeInput({width: '250px', culture: 'zh-CN', formatString: 'd', height: 22,showFooter:true,clearString:'清除',todayString:'今天',allowNullDate: true,value: null,theme:"metro"});


		// 获取datagrid的data
		var rows = $('#jqxgridBiz').jqxGrid('getrows');
		var rowData = null;
		for(var i = 0; i < rows.length; i++) {
			if(rows[i].bizSid == bizTypeSid) {
				rowData = rows[i];
				break;
			}
		}
		if(rowData) {
			//页面赋值
			$("#edit-bizSid").val(rowData.bizSid);
			$("#edit-parentBizName").val(rowData.parentBizName);
			$("#edit-bizName").val(rowData.name);
			$("#edit-bizDesc").val(rowData.description);
			$("#edit-biz-status").val(rowData.status);
			$("#edit-owner").val(rowData.owner);
			$("#edit-ownerTel").val(rowData.ownerTel);
			$("#edit-pmTel").val(rowData.pmTel);
			$("#edit-pmEmail").val(rowData.pmEmail);
			$("#edit-pm").val(rowData.pm);
			$("#edit-networktype").val(rowData.networktype);
			$("#edit-fwPort").val(rowData.fwport);
			$("#edit-isBizCont").val(rowData.isbizcont);
			$("#edit-isProNo").val(rowData.isprono);
			$("#edit-isProAttach").val(rowData.isproattach);
			$('#edit-createDt').val(rowData.createdDt);
		}

		// 设置弹出框位置
		var windowW = $(window).width(); 
		var windowH = $(window).height(); 
		$("#editBizTypeWindow").jqxWindow({height:height, position: { x: (windowW-400)/2, y: (windowH-height)/2 } });
		$("#editBizTypeWindow").jqxWindow('open');
	};

	//弹出设置配额窗口
	this.popSetQuotaWindow = function() {
		//初始化配额变更信息
		quotaChanges = [];
		//设置弹出窗口内容
		var data = bizType;

		me.initEditQuotaGrid(data.level);
		$("#remainQuota").html("");
		//如果是一级业务添加时剩余配额不显示 BUG#1860
		if(data.level == 1) {
			$("#remainQuota").hide();
			$("#remainQuotaText").hide();
		} else {
			$("#remainQuota").show();
			$("#remainQuotaText").show();
		}

		var parentBizSid = data.parentBizSid;
		var bizSid = data.bizSid;
		var level = data.level;
		Core.AjaxRequest({
			url : ws_url + "/rest/bizType/quotas",
			type:'post',
			params: {
				'bizSid': bizSid,
				'parentBizSid': parentBizSid,
				'level': level
			},
			showMsg : false,
			callback : function (data) {
				$("#set-quotaType").val("cores");
				$("#set-quotaType").trigger('change');
				//配额Grid显示
				var sourcedatagrid ={
					datatype: "json",
					datafields: [
						{ name: 'quotaSid'},
						{ name: 'quotaKey'},
						{ name: 'quotaName'},
						{ name: 'quotaValue'},
						{ name: 'quotaObjSid'},
						{ name: 'remainQuota'},
						{ name: 'usagedQuota'},
						{ name: 'description'},
						{ name: 'createdBy'},
						{ name: 'quotaOldValue'}
					],
					addrow: function (rowid, rowdata, position, commit) {
						rowdata['oper'] = 'add';
						rowdata['quotaObj'] = 0;
						rowdata['quotaObjSid'] = bizSid;
						rowdata['rowid'] = rowid;
						quotaChanges.push(rowdata);
						commit(true);
						$("#editQuotaGrid").jqxGrid('selectrow', rowid);
						setTimeout($("#editQuotaGrid").jqxGrid('beginrowedit', rowid, 'quotaValue'), 200);
					},
					deleterow: function (rowid, commit) {
						commit(true);
					},
					updaterow: function (rowid, newdata, commit) {
						var index = -1;
						for(var i = 0; i < quotaChanges.length; i++) {
							if(quotaChanges[i].rowid == rowid) {
								index = i;
								break;
							}
						}
						if(index >= 0) {
							quotaChanges[index] = $.extend(quotaChanges[index], newdata);
						} else {
							newdata['oper'] = 'update';
							quotaChanges.push(newdata);
						}
						commit(true);
					},
					localdata: data
				};
				var griddataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
				$("#editQuotaGrid").jqxGrid({source: griddataAdapter});

				//显示设定配额弹出窗口, 设置弹出框位置
				var windowW = $(window).width(); 
				var windowH = $(window).height(); 
				$("#setQuotaWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-420)/2 } });
				$("#setQuotaWindow").jqxWindow('open');
			},
			failure: function (result) {
				Core.alert({
					title: "错误",
					type: "error",
					width: 500,
					message: result.message
				});
			}
		});
	};

	this.initEditQuotaGrid = function (level) {
		if(level == 1) {
			$("#editQuotaGrid").jqxGrid({
				width: "100%",
				height: "250px",
				theme:currentTheme,
				columnsresize: true,
				editable: true,
				selectionmode: 'singlerow',
				editmode: 'multiplecellsadvanced',
				localization: gridLocalizationObj,
				columns: [
					{ text: '配额项名称', datafield: 'quotaName', width:100, editable: false},
					{ text: '配额项值', datafield: 'quotaValue', width:80, columntype: 'numberinput',
						validation: function (cell, value) {
							if (value <= 0) {
								return { result: false, message: "配额值必须为正数!" };
							}
							var usagedQuota = parseInt(this.owner.getrowdatabyid(cell.row).usagedQuota);
							if(parseInt(value) < usagedQuota) {
								return { result: false, message: "配额不能少于子业务类型已分配配额!" };
							}
							return true;
						},
						createeditor: function (row, cellvalue, editor) {
							var usagedQuota = this.owner.getrowdatabyid(row).usagedQuota;
							var minSetting = 1;
							if(usagedQuota && parseInt(usagedQuota) > 0) {
								minSetting = parseInt(usagedQuota);
							} 
							editor.jqxNumberInput({min: minSetting}).val(cellvalue);
						}
					},
					{ text: '业务类型已分配配额', datafield: 'usagedQuota', width:120, editable: false},
					{ text: '剩余配额', datafield: 'remainQuota', width:80, editable: false},
					{ text: '描述', datafield: 'description', columntype: 'textbox',
						validation: function (cell, value) {
							if (value && value.length > 200) {
								return { result: false, message: "描述文字长度不能超过100!" };
							}
							return true;
						}
					},
					{ text: '创建人', datafield: 'createdBy', width:80, editable: false}
				]
			});
		} else if(level == 2) {
			$("#editQuotaGrid").jqxGrid({
				width: "100%",
				height: "250px",
				theme:currentTheme,
				columnsresize: true,
				editable: true,
				selectionmode: 'singlerow',
				editmode: 'multiplecellsadvanced',
				localization: gridLocalizationObj,
				columns: [
					{ text: '配额项名称', datafield: 'quotaName', width:100, editable: false},
					{ text: '配额项值', datafield: 'quotaValue', width:80, columntype: 'numberinput',
						validation: function (cell, value) {
							if (value <= 0) {
								return { result: false, message: "配额值必须为正数!" };
							}
							var quotaOldValue = this.owner.getrowdatabyid(cell.row).quotaOldValue;
							if(!quotaOldValue) {
								quotaOldValue = 0;
							}
							if(this.owner.getrowdatabyid(cell.row).remainQuota != undefined && (value - quotaOldValue) > this.owner.getrowdatabyid(cell.row).remainQuota) {
								return { result: false, message: "配额增幅不能超过业务属性剩余配额!" };
							}
							return true;
						},
						createeditor: function (row, cellvalue, editor) {
							var rowData = this.owner.getrowdatabyid(row);
							var remainQuota = rowData.remainQuota;
							var quotaValue = rowData.quotaValue;
							var maxSetting = 0;
							if(rowData.oper && rowData.oper == 'add') {
								maxSetting = parseInt(remainQuota);
							} else {
								maxSetting = (parseInt(remainQuota) + parseInt(quotaValue));
							}
							editor.jqxNumberInput({min: 1, max: maxSetting}).val(1);
						}
					},
					{ text: '业务属性剩余配额', datafield: 'remainQuota', width:120, editable: false},
					{ text: '描述', datafield: 'description', columntype: 'textbox',
						validation: function (cell, value) {
							if (value && value.length > 200) {
								return { result: false, message: "描述文字长度不能超过100!" };
							}
							return true;
						}
					},
					{ text: '创建人', datafield: 'createdBy', width:80, editable: false}
				]
			});
		}
	};

	//新增业务数据提交
	this.addBizTypeSubmit = function () {
		// 判断是否通过了验证
		$('#addBizTypeForm').jqxValidator('validate');
	};

	//编辑业务数据提交
	this.editBizTypeSubmit = function () {
		// 判断是否通过了验证
		$('#editBizTypeForm').jqxValidator('validate');
	};

	//业务配额设置提交
	this.setQuotasSubmit = function () {
		var confirmQuotaNames = [];
		for(var i = 0; i < quotaChanges.length; i++) {
			delete quotaChanges[i]['rowid'];
			delete quotaChanges[i]['uid'];
			if(quotaChanges[i].oper == 'delete') {
				confirmQuotaNames.push('"' + quotaChanges[i].quotaName + '"');
			}
		}
		if(bizTypeSid) {
			if(level === 1 && confirmQuotaNames.length > 0) {
				Core.confirm({
					title:"提示",
					width: 500,
					message:"删除业务属性配额设置，其所属的业务类型配额设置也将会被删除，是否确认删除？",
					confirmCallback:function(){
						Core.AjaxRequest({
							url : ws_url + "/rest/bizType/quotas/create",
							type:'post',
							params: quotaChanges,
							callback : function (data) {
								$("#setQuotaWindow").jqxWindow('close');
							},
							failure: function (data) {
								$("#setQuotaWindow").jqxWindow('close');
								}
						});
					}
				});
			} else {
				Core.AjaxRequest({
					url : ws_url + "/rest/bizType/quotas/create",
					type:'post',
					params: quotaChanges,
					callback : function (data) {
						$("#setQuotaWindow").jqxWindow('close');
					},
					failure: function (data) {
						$("#setQuotaWindow").jqxWindow('close');
					}
				});
			}
		}
	};

	//选择业务后，资源数据加载
	this.selectBizDataLoad = function () {
		if(level != null) {
			//按钮设置
			if(level == 2) {
				$("#bizTypeAddBtn").jqxButton({disabled: true});
			} else {
				$("#bizTypeAddBtn").jqxButton({disabled: false});
			}
			$("#bizTypeEditBtn").jqxButton({disabled: false});
			$("#bizTypeDelBtn").jqxButton({disabled: false});
			if(level == 0) {
				$("#bizTypeQuotaBtn").jqxButton({disabled: true});
				$("#bizTypeResBtn").jqxButton({disabled: true});
			} else {
				$("#bizTypeQuotaBtn").jqxButton({disabled: false});
				$("#bizTypeResBtn").jqxButton({disabled: false});
			}
			if(level == 0) {
				$("#jqxExpanderResBiz .resoure").hide();

				$("#jqxTreeRz").jqxTree('clear');
				$("#jqxTreeCompute").jqxTree('clear');
				$("#jqxTreeNetwork").jqxTree('clear');
			} else if(level == 1){
				//设置资源拓扑Tab显示, 默认显示资源分区Tab
				$("#jqxExpanderResBiz .resoure").removeClass("show");
				$("#jqxExpanderResBiz .resoure:eq(0)").addClass("show");
				$("#jqxExpanderResBiz .resoure:eq(0)").show();
				$("#jqxExpanderResBiz .resoure:gt(0)").hide();
				$("#jqxExpanderResBiz .content:eq(0)").show();
				$("#jqxExpanderResBiz .content:gt(0)").hide();

				//初始化资源分区Tree
				var treeItems = $('#jqxTreeRz').jqxTree('getItems');
				if(!treeItems || treeItems.length == 0) {
					me.initRecourceZoneTree();
				} else {
					if(bizType['flushSelect'] === false) {
						me.initRecourceZoneTree(bizTypeSid);
					} else if(bizType['flushSelect'] === true){
						me.flushRecourceZoneTreeSelect();
					}
				}
					
			} else if(level == 2) {
				//设置资源拓扑Tab显示, 默认显示计算资源Tab
				$("#jqxExpanderResBiz .content:gt(0)").show();
				$("#jqxExpanderResBiz .resoure").removeClass("show");
				$("#jqxExpanderResBiz .resoure:eq(1)").addClass("show");
				$("#jqxExpanderResBiz .resoure:gt(0)").show();
				$("#jqxExpanderResBiz .resoure:eq(0)").hide();
				$("#jqxExpanderResBiz .content:eq(1)").show();
				$("#jqxExpanderResBiz .content:eq(0)").hide();
				$("#jqxExpanderResBiz .content:eq(2)").hide();
				
				
				//初始化计算资源和网络资源Tree
				var computeTreeItems = $('#jqxTreeCompute').jqxTree('getItems');
				if(!computeTreeItems || computeTreeItems.length == 0) {
					me.initComputeResourceTree();
				} else {
					if(bizType['flushSelect'] === false) {
						me.initComputeResourceTree();
					} else if(bizType['flushSelect'] === true){
						me.flushComputeResourceTreeSelect();
					}
				}

				
				if(bizType['flushSelect'] === false) {
					$('#jqxTreeNetwork').jqxTree('clear');
				}
				//me.initNetworkResourceTree();
			}
		}
	};

	//绑定页面处理事件
	this.initBindPageEvent = function () {
		//业务树点击事件处理
		$('#jqxTreeBiz').on('select',function(event) {
			var args = event.args;
			var item = $('#jqxTreeBiz').jqxTree('getItem', args.element);
			$('#jqxgridBiz').jqxGrid('clearSelection');
			if(item.value === bizTypeSid) {
				return;
			}
			if(!bizType) bizType = {};
			bizType['bizSid'] = item.value;
			bizType['bizName'] = item.label;
			bizType['level'] = item.level;
			var parentItem = $('#jqxTreeBiz').jqxTree('getItem',item.parentElement);
			if(parentItem && (parentItem.value === bizType['parentBizSid'])) {
				bizType['flushSelect'] = true;
			} else {
				bizType['flushSelect'] = false;
			}
			bizType['parentBizSid'] = parentItem ? parentItem.value : null;
			bizTypeSid = item.value;
			level = item.level;

			me.selectBizDataLoad();

			me.searchBizTypeInfo();
			
		});

		//业务Grid选择事件处理
		$("#jqxgridBiz").on('rowselect', function (event) {
			var args = event.args; 
			var index = args.rowindex;
			$('#jqxTreeBiz').jqxTree('selectItem', null);
			if(index >= 0){
				var rowData = $('#jqxgridBiz').jqxGrid('getrowdata', index);
				if(rowData.bizSid === bizTypeSid) {
					return;
				}
				level = rowData.level;
				bizTypeSid = rowData.bizSid;
				var bizName = rowData.name;
				bizType['bizSid'] = bizTypeSid;
				bizType['bizName'] = bizName;
				bizType['level'] = level;
				if(rowData.parentBizSid === bizType['parentBizSid']) {
					bizType['flushSelect'] = true;
				} else {
					bizType['flushSelect'] = false;
				}
				bizType['parentBizSid'] = rowData.parentBizSid;
				
			}
			me.selectBizDataLoad();
		});

		$("#jqxExpanderResBiz .resoure").each(function(index){
			$(this).click(index, function(event){
				$("#jqxExpanderResBiz .resoure").removeClass("show");
				$(this).addClass("show");
				$("#jqxExpanderResBiz .content").not(event.data).hide();
				$("#jqxExpanderResBiz .content").eq(event.data).show();
				if(index == 1) {
					var treeItems = $('#jqxTreeCompute').jqxTree('getItems');
					if(!treeItems || treeItems.length == 0) {
						me.initComputeResourceTree();
					} else {
						if(bizType['flushSelect'] === false) {
							me.initComputeResourceTree();
						} else if(bizType['flushSelect'] === false) {
							me.flushComputeResourceTreeSelect();
						}
					}
				} else if(index == 2) {
					var treeItems = $('#jqxTreeNetwork').jqxTree('getItems');
					if(!treeItems || treeItems.length == 0) {
						me.initNetworkResourceTree();
					} else {
						if(bizType['flushSelect'] === false) {
							me.initNetworkResourceTree();
						} else if(bizType['flushSelect'] === true) {
							me.flushNetworkResourceTreeSelect();
						}
					}
				}
			});
		});

		$("#bizTypeAddBtn").on('click', function (event){
			var ok =  $('#bizTypeAddBtn').jqxButton('disabled');
			if(!ok) { me.popAddBizWindow();}
		});
		$("#bizTypeEditBtn").on('click', function (event){
			var ok =  $('#bizTypeEditBtn').jqxButton('disabled');
			if(!ok) { me.popEditBizWindow();}
		});
		$("#bizTypeDelBtn").on('click', function (event){
			var ok =  $('#bizTypeDelBtn').jqxButton('disabled');
			if(!ok) { me.bizTypeDelItem();}
		});
		$("#bizTypeResBtn").on('click', function (event) {
			var ok =  $('#bizTypeResBtn').jqxButton('disabled');
			if(!ok) { me.saveBizResItems();}
		});
		$("#bizTypeQuotaBtn").on('click', function (event) {
			var ok =  $('#bizTypeQuotaBtn').jqxButton('disabled');
			if(!ok) { me.popSetQuotaWindow(); }
		});

		//绑定添加业务类型提交事件
		$("#saveAddBizType").on('click', me.addBizTypeSubmit);
		//绑定更新业务类型提交事件
		$("#saveEditBizType").on('click', me.editBizTypeSubmit);
		//绑定保存配额提交事件
		$("#saveQuotaInfo").on('click', me.setQuotasSubmit);
		//绑定业务查询事件
		$("#search-biz-type-button").on('click', me.searchBizTypeInfo);

		$("#addQuotaType").jqxButton({theme:currentTheme}).on('click', function (event){
			var rows = $("#editQuotaGrid").jqxGrid('getrows');
			for(var i = 0; i < rows.length; i++) {
				if(rows[i].quotaKey == $("#set-quotaType").val()) {
					Core.alert({
						title: "错误",
						type: "error",
						message: "该业务配额类型已添加！",
					});
					return;
				}
			}
			var newRow = {};
			newRow['quotaKey'] = $("#set-quotaType").val();
			newRow['quotaValue'] = 1;
			newRow['quotaName'] = $('#set-quotaType').jqxDropDownList('getSelectedItem').label;
			newRow['createdBy'] = currentUser ? currentUser.account : "";
			if($("#remainQuota").html() != "") {
				newRow['remainQuota'] = parseInt($("#remainQuota").html());
			}
			$("#editQuotaGrid").jqxGrid('addrow', null, newRow);
		});
			 $("#delQuotaType").jqxButton({theme:currentTheme}).on('click', function (event){
				 var selectedrowindex = $("#editQuotaGrid").jqxGrid('getselectedrowindex');
				 var rowscount = $("#editQuotaGrid").jqxGrid('getdatainformation').rowscount;
				 if(selectedrowindex >= 0 && selectedrowindex < rowscount) {
					 var id = $("#editQuotaGrid").jqxGrid('getrowid', selectedrowindex);
					 var deleteRow = {};
					 deleteRow['quotaSid'] = $("#editQuotaGrid").jqxGrid('getrowdatabyid', id).quotaSid;
					 deleteRow['quotaName'] = $("#editQuotaGrid").jqxGrid('getrowdatabyid', id).quotaName;
					 deleteRow['quotaKey'] = $("#editQuotaGrid").jqxGrid('getrowdatabyid', id).quotaKey;
					 deleteRow['quotaObjSid'] = $("#editQuotaGrid").jqxGrid('getrowdatabyid', id).quotaObjSid;
					 $("#editQuotaGrid").jqxGrid('deleterow', id);
	          	     deleteRow['oper'] = 'delete';
	          	     deleteRow['rowid'] = id;
	          	     quotaChanges.push(deleteRow);
					 $('#editQuotaGrid').jqxGrid('unselectrow', selectedrowindex);
				 }
			 });
		  
		  
		// 新增业务类型验证成功
		$('#addBizTypeForm').on('validationSuccess', function (event) {
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/bizType/create",
	 				params :{
	 					parentBizSid : $('#add-parentBizSid').val(),
	    		 		name : $('#add-bizName').val(),
	    		 		owner : $('#add-owner').val(),
	    		 		ownerTel : $('#add-ownerTel').val(),
	    		 		status : $('#add-biz-status').val(),
	    		 		description : $('#add-bizDesc').val(),
	    		 		pmTel : $('#add-pmTel').val(),
	    		 		pmEmail : $('#add-pmEmail').val(),
	    		 		pm : $('#add-pm').val(),
	    		 		networktype : $('#add-networktype').val(),
	    		 		fwport : $('#add-fwPort').val(),
	    		 		isbizcont : $('#add-isBizCont').val(),
	    		 		isprono : $('#add-isProNo').val(),
	    		 		isproattach : $('#add-isProAttach').val(),
	    		 		level : $('#add-level').val()
	 				},
	 				callback : function (data) {
	 					//刷新业务信息树
	 					me.initBizTypeTree();
	 					me.searchBizTypeInfo();
						$("#addBizTypeWindow").jqxWindow('close');
   					    $("#bizTypeResBtn").jqxButton({disabled: true });
	 			    },
	 			    failure:function(data){
						$("#addBizTypeWindow").jqxWindow('close');
	 			    }
	 			});
	     });
		
		// 更新业务类型验证成功
		$('#editBizTypeForm').on('validationSuccess', function (event) {
			   var data = {
 					bizSid : $("#edit-bizSid").val(),
 					parentBizSid : $('#edit-parentBizSid').val(),
    		 		name : $('#edit-bizName').val(),
    		 		owner : $('#edit-owner').val(),
    		 		ownerTel : $('#edit-ownerTel').val(),
    		 		status : $('#edit-biz-status').val(),
    		 		pmTel : $('#edit-pmTel').val(),
    		 		pmEmail : $('#edit-pmEmail').val(),
    		 		pm : $('#edit-pm').val(),
    		 		description : $('#edit-bizDesc').val(),
    		 		networktype : $('#edit-networktype').val(),
    		 		fwport : $('#edit-fwPort').val(),
    		 		isbizcont : $('#edit-isBizCont').val(),
    		 		isprono : $('#edit-isProNo').val(),
    		 		isproattach : $('#edit-isProAttach').val(),
    		 		level : $('#edit-level').val(),
    		 		createdDt : $('#edit-createDt').val()
 				};
			   
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/bizType/update",
	 				params :data,
	 				callback : function (data) {
	 					//刷新Tree节点显示
	 					var tree = $('#jqxTreeBiz');
	 			    	var items = tree.jqxTree('getItems');
	 			    	var curItem = null;
	 		            for(var i = 0; i < items.length; i++) {
	 		            	if(items[i].value === bizTypeSid) {
	 		            		curItem = items[i];
	 		            		break;
	 		            	}
	 		            }
	 		            if(curItem) {
	 		            	$('#jqxTreeBiz').jqxTree('updateItem', curItem, { label: $('#edit-bizName').val() });
	 		            }
	 		            me.searchBizTypeInfo();
						$("#editBizTypeWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editBizTypeWindow").jqxWindow('close');
	 			    }
	 			});
	     });


		$("#set-quotaType").jqxDropDownList().on('change', function (event) {
		
			 if(level != null && level === 2) {
				 var quotaKey = $(this).val();
				 var parentBizSid = bizType['parentBizSid'];
				 var bizSid = bizTypeSid;
				 Core.AjaxRequest({
						url : ws_url + "/rest/bizType/remain/quota",
						type:'post',
						params: {
							'bizSid': bizSid,
							'parentBizSid': parentBizSid,
							'quotaKey': quotaKey
						},
						callback : function (data) {
			                var remainQuota = data.remainQuota;
			                if(remainQuota >= 0) {
			                	$("#remainQuota").html(remainQuota + "");
			                	$("#addQuotaType").jqxButton({disabled: false});
			                }
			                if(remainQuota <= 0) {
			                	$("#addQuotaType").jqxButton({disabled: true});
			                }
						}
				});
			 }
		});
	};

	// 初始化验证规则
	this.initValidator = function(){
		$('#addBizTypeForm').jqxValidator({
			rules: [  
				{ input: '#add-bizName', message: '业务类型名称必须为1-32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
				{ input: '#add-bizDesc', message: '业务描述名称不能超过128个字符!', action: 'keyup, blur', rule: 'length=0,128' },
				{ input: '#add-owner', message: '维护人员不能超过16个字符!', action: 'keyup, blur', rule: 'length=0,16' },
				{ input: '#add-ownerTel', message: '维护人员电话请输入正确的格式!', action: 'keyup, blur', rule: 'length=11,11' },
				{ input: '#add-pm', message: '项目经理不能超过16个字符!', action: 'keyup, blur', rule: 'length=0,16' },
				{ input: '#add-pmTel', message: '项目经理电话请输入正确的格式!', action: 'keyup, blur', rule: 'length=11,11' }
			]
		});
		$('#editBizTypeForm').jqxValidator({
			rules: [  
				{ input: '#edit-bizName', message: '业务类型名称必须为1-32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
				{ input: '#edit-bizDesc', message: '业务描述名称不能超过128个字符!', action: 'keyup, blur', rule: 'length=0,128' },
				{ input: '#edit-owner', message: '维护人员不能超过16个字符!', action: 'keyup, blur', rule: 'length=0,16' },
				{ input: '#edit-ownerTel', message: '维护人员电话请输入正确的格式!', action: 'keyup, blur', rule: 'length=11,11' },
				{ input: '#edit-pm', message: '项目经理不能超过16个字符!', action: 'keyup, blur', rule: 'length=0,16' },
				{ input: '#edit-pmTel', message: '项目经理电话请输入正确的格式!', action: 'keyup, blur', rule: 'length=11,11' }
			]
		});
	};

	// 删除业务类型
	this.bizTypeDelItem = function () {
		if(bizTypeSid){
	    		 Core.confirm({
		    			title:"提示",
		    			width:400,
		    			message:"确定要删除该业务类型以及关联的关系和用户吗？",
		    			confirmCallback:function(){
		    				Core.AjaxRequest({
		    					url : ws_url + "/rest/bizType/delete/"+bizTypeSid,
		    	 				type:"get",
		    	 				showMsg: false,
		    	 				callback : function (data) {
		    	 					//刷新Tree节点显示
		    	 					var tree = $('#jqxTreeBiz');
		    	 			    	var items = tree.jqxTree('getItems');
		    	 			    	var curItem = null;
		    	 		            for(var i = 0; i < items.length; i++) {
		    	 		            	if(items[i].value === bizTypeSid) {
		    	 		            		curItem = items[i];
		    	 		            		break;
		    	 		            	}
		    	 		            }
		    	 		            if(curItem) {
		    	 		            	$('#jqxTreeBiz').jqxTree('removeItem', curItem);
		    	 		            }
		    	 		            me.searchBizTypeInfo();
		    	 		            $('#jqxTreeCompute').jqxTree('clear');
		    	 		            $('#jqxTreeNetwork').jqxTree('clear');
		    	 		            bizTypeSid = null;
		    	 		            level = null;
		    	 		       		bizType = {};
		    	 			    },
		    					failure : function (data) {
		    						Core.alert({
		    							title: "错误",
		    							type: "error",
		    							width: 430,
		    							message: data.message
		    						});
		    					}
		    	 			});
		    			}
		    	    });
	    	 }
	    	
	    };
	    
	    //初始化资源分区树
	    this.initRecourceZoneTree = function () {
	    	if(!bizTypeSid)  return;
    		$('#jqxTreeRz').jqxTree('clear');
	    	Core.AjaxRequest({
				url : ws_url + "/rest/bizType/rz",
				type:'post',
				params: {
					'bizSid' : bizTypeSid,
					'mode': '0',
				},
				callback : function (data) {
					var treeData = data.treeData;
					var checkedData = data.checkedData;
					treeData = me.mergeTreeValue(treeData);
	                var source ={
	                    datatype: "json",
	                    datafields: [
	                         { name: 'resTopologySid' },
				             { name: 'parentTopologySid' },
				             { name: 'resTopologyName' },
				             { name: 'resTopologyType' },
				             { name: 'checked' },
	                    ],
	                    id: 'resTopologySid',
	                    localdata: treeData
	                };
	                var dataAdapter = new $.jqx.dataAdapter(source);
	                dataAdapter.dataBind();
	                var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'resTopologySid', map: 'value'}, { name: 'checked', map: 'checked'}]);
		            $('#jqxTreeRz').jqxTree({source: records, height: '99.5%', width: '100%', checkboxes: true, theme : currentTheme, hasThreeStates: true});
		            $('#jqxTreeRz').jqxTree('expandItem', $("#jqxTreeRz").jqxTree("getItems")[0]);
		            me.setTreeExpend('jqxTreeRz', 1);
		            if(checkedData) {
			            var items = $('#jqxTreeRz').jqxTree('getItems');
			            for(var i = 0; i < items.length; i++) {
			            	for(var j = 0; j < checkedData.length; j++) {
				            	if(items[i].value == (checkedData[j].resSetSid + "," + checkedData[j].resSetType)) {
				            		$('#jqxTreeRz').jqxTree('checkItem', items[i], true);
				            		break;
				            	}
			            	}
			            }
		            }
				}
			 });
	    };
	    
	    //刷新资源分区树选择
	    this.flushRecourceZoneTreeSelect = function () {
	    	if(!bizTypeSid)  return;
	    	$('#jqxTreeRz').jqxTree('uncheckAll');
	    	Core.AjaxRequest({
				url : ws_url + "/rest/bizType/rz",
				type:'post',
				params: {
					'bizSid' : bizTypeSid,
					'mode': '2',
				},
				callback : function (data) {
					var checkedData = data.checkedData;
					if(checkedData) {
						me.setTreeExpend('jqxTreeRz', 1);
			            var items = $('#jqxTreeRz').jqxTree('getItems');
			            for(var i = 0; i < items.length; i++) {
			            	for(var j = 0; j < checkedData.length; j++) {
				            	if(items[i].value == (checkedData[j].resSetSid + "," + checkedData[j].resSetType)) {
				            		$('#jqxTreeRz').jqxTree('checkItem', items[i], true);
				            		break;
				            	}
			            	}
			            }
		            }
				}
			 });
	    };
	    
	    //初始化计算资源树
	    this.initComputeResourceTree = function () {
	    	if(bizTypeSid) {
	    		$('#jqxTreeCompute').jqxTree('clear');
	    		var parentBizSid = bizType['parentBizSid'];
		    	Core.AjaxRequest({
					url : ws_url + "/rest/bizType/compute",
					type:'post',
					params: {
						'bizSid': bizTypeSid,
						'parentBizSid': parentBizSid,
						'mode': '0',
					},
					async:false,
					callback : function (data) {
						var treeData = data.treeData;
						var checkedData = data.checkedData;
						treeData = me.mergeTreeValue(treeData);
		                var source ={
		                    datatype: "json",
		                    datafields: [
		                         { name: 'resTopologySid' },
					             { name: 'parentTopologySid' },
					             { name: 'resTopologyName' },
					             { name: 'resTopologyType' },
					             { name: 'checked' },
		                    ],
		                    id: 'resTopologySid',
		                    localdata: treeData
		                };
		                var dataAdapter = new $.jqx.dataAdapter(source);
		                dataAdapter.dataBind();
		                var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'resTopologySid', map: 'value'}, { name: 'checked', map: 'checked'}]);
			            $('#jqxTreeCompute').jqxTree({source: records, height: '99.5%', width: '100%', checkboxes: true, theme : currentTheme, hasThreeStates: true});
			            me.setTreeExpend('jqxTreeCompute', 1);
			            if(checkedData) {
				            var items = $('#jqxTreeCompute').jqxTree('getItems');
				            for(var i = 0; i < items.length; i++) {
				            	for(var j = 0; j < checkedData.length; j++) {
					            	if(items[i].value == (checkedData[j].resSetSid + "," + checkedData[j].resSetType)) {
					            		$('#jqxTreeCompute').jqxTree('checkItem', items[i], true);
					            		break;
					            	}
				            	}
				            }
			            }
					}
				 });
	    	}
	    };
	    
	   //刷新计算资源树选择
	    this.flushComputeResourceTreeSelect = function () {
	    	if(!bizTypeSid)  return;
	    	$('#jqxTreeCompute').jqxTree('uncheckAll');
	    	var parentBizSid = bizType['parentBizSid'];
	    	Core.AjaxRequest({
				url : ws_url + "/rest/bizType/compute",
				type:'post',
				params: {
					'bizSid': bizTypeSid,
					'parentBizSid': parentBizSid,
					'mode': '2',
				},
				async:false,
				callback : function (data) {
					var checkedData = data.checkedData;
		            if(checkedData) {
		            	me.setTreeExpend('jqxTreeCompute', 1);
			            var items = $('#jqxTreeCompute').jqxTree('getItems');
			            for(var i = 0; i < items.length; i++) {
			            	for(var j = 0; j < checkedData.length; j++) {
				            	if(items[i].value == (checkedData[j].resSetSid + "," + checkedData[j].resSetType)) {
				            		$('#jqxTreeCompute').jqxTree('checkItem', items[i], true);
				            		break;
				            	}
			            	}
			            }
		            }
				}
			 });
	    };

	    //初始化网络资源Tree
	    this.initNetworkResourceTree = function () {
	    	if(bizTypeSid) {
	    		$('#jqxTreeNetwork').jqxTree('clear');
	    		var parentBizSid = bizType['parentBizSid'];
		    	Core.AjaxRequest({
					url : ws_url + "/rest/bizType/network",
					type:'post',
					params: {
						'bizSid': bizTypeSid,
						'parentBizSid': parentBizSid,
						'mode': '0',
					},
					async: false,
					callback : function (data) {
						var treeData = data.treeData;
						var checkedData = data.checkedData;
						treeData = me.mergeTreeValue(treeData);
		                var source ={
		                    datatype: "json",
		                    datafields: [
		                         { name: 'resTopologySid' },
					             { name: 'parentTopologySid' },
					             { name: 'resTopologyName' },
					             { name: 'resTopologyType' },
					             { name: 'checked' },
		                    ],
		                    id: 'resTopologySid',
		                    localdata: treeData
		                };
		                var dataAdapter = new $.jqx.dataAdapter(source);
		                dataAdapter.dataBind();
		                var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'resTopologySid', map: 'value'}, { name: 'checked', map: 'checked'}]);
		               
			            $('#jqxTreeNetwork').jqxTree({source: records, height: '99.5%', width: '100%', checkboxes: true, theme : currentTheme, hasThreeStates: true});
			            me.setTreeExpend('jqxTreeNetwork', 1);
			            if(checkedData) {
				            var items = $('#jqxTreeNetwork').jqxTree('getItems');
				            for(var i = 0; i < items.length; i++) {
				            	for(var j = 0; j < checkedData.length; j++) {
					            	if(items[i].value == (checkedData[j].resSetSid + "," + checkedData[j].resSetType)) {
					            		$('#jqxTreeNetwork').jqxTree('checkItem', items[i], true);
					            		break;
					            	}
				            	}
				            }
			            }
					}
				 });
	    	}
	    };

	    //刷新网络资源树选择
	    this.flushNetworkResourceTreeSelect = function () {
	    	if(!bizTypeSid)  return;
	    	$('#jqxTreeNetwork').jqxTree('uncheckAll');
	    	var parentBizSid = bizType['parentBizSid'];
	    	Core.AjaxRequest({
				url : ws_url + "/rest/bizType/network",
				type:'post',
				params: {
					'bizSid': bizTypeSid,
					'parentBizSid': parentBizSid,
					'mode': '2',
				},
				async: false,
				callback : function (data) {
					var checkedData = data.checkedData;
		            if(checkedData) {
		            	me.setTreeExpend('jqxTreeNetwork', 1);
			            var items = $('#jqxTreeNetwork').jqxTree('getItems');
			            for(var i = 0; i < items.length; i++) {
			            	for(var j = 0; j < checkedData.length; j++) {
				            	if(items[i].value == (checkedData[j].resSetSid + "," + checkedData[j].resSetType)) {
				            		$('#jqxTreeNetwork').jqxTree('checkItem', items[i], true);
				            		break;
				            	}
			            	}
			            }
		            }
				}
			 });
	    };

	    this.setTreeExpend = function (id, level) {
	    	var tree = $("#" + id);
	    	var treeItems = tree.jqxTree("getItems");
	    	for(var i = 0; i < treeItems.length; i++) {
	    		if(treeItems[i].level <= level) {
	    			tree.jqxTree('expandItem', treeItems[i]);
	    		} else if(treeItems[i].level > level) {
	    			tree.jqxTree('collapseItem', treeItems[i]);
	    		}
	    	}
	    };
	    
	    this.mergeTreeValue = function (records) {
	    	var array = [];
	    	for(var i = 0; i< records.length; i++) {
	    		var treeNode = {};
	    		var parentresTopologyType = me.getParentType(records, records[i].parentTopologySid);
	    		treeNode.resTopologySid = records[i].resTopologySid + "," + records[i].resTopologyType;
	    		treeNode.parentTopologySid = records[i].parentTopologySid + "," + (parentresTopologyType ? parentresTopologyType : "");
	    		treeNode.resTopologyName = records[i].resTopologyName;
	    		treeNode.checked = records[i].checked;
	    		array.push(treeNode);
	    	}
	    	return array;
	    };

	    this.getParentType = function (records, parentResTopologySid) {
	    	for(var i = 0; i < records.length; i++) {
	    		if(records[i].resTopologySid == parentResTopologySid) {
	    			return records[i].resTopologyType;
	    		}
	    	}
	    };

	    this.saveBizResItems = function () {
	    	if(bizTypeSid) {
	    		var bizResArray = [];
	    		var clearFlag = 1;
	    		if(level === 1) {
	    			var checkedItems = $('#jqxTreeRz').jqxTree('getCheckedItems');
	    			for(var i = 0; i < checkedItems.length; i++) {
	    				var type = checkedItems[i].value.split(',')[1];
	    				var value =  checkedItems[i].value.split(',')[0];
	    				if(type == "RZ") {
		    				var bizRes = {};
		    				bizRes.resSetSid = value;
		    				bizRes.bizSid = bizTypeSid;
		    				bizRes.resCategory = 3;
		    				bizRes.resSetType = type;
		    				bizResArray.push(bizRes);
	    				}
	    			}
	    			clearFlag = 1;
	    		} else if(level === 2) {
	    			var checkedComputeItems = $('#jqxTreeCompute').jqxTree('getCheckedItems');
	    			for(var i = 0; i < checkedComputeItems.length; i++) {
	    				var type = checkedComputeItems[i].value.split(',')[1];
	    				var value =  checkedComputeItems[i].value.split(',')[0];
	    				if(type == "RES-HOST" || type == "VC") {
	    					if(type == "RES-HOST") {
	    						var parentItem = $("#jqxTreeCompute").jqxTree('getPrevItem', checkedComputeItems[i]);
	    						//判断主机的集群节点是否也选中
	    						var isExist = false;
	    						for(var j = 0; j < checkedComputeItems.length; j++)  {
	    							if(checkedComputeItems[j].value == parentItem.value) {
	    								isExist = true;
	    								break;
	    							}
	    						}
	    						if(isExist) continue;
	    					}
		    				var bizRes = {};
		    				bizRes.resSetSid = value;
		    				bizRes.bizSid = bizTypeSid;
		    				bizRes.resCategory = 0;
		    				bizRes.resSetType = type;
		    				bizResArray.push(bizRes);
	    				}
	    			}
	    			var checkedNetworkItems = $('#jqxTreeNetwork').jqxTree('getCheckedItems');
	    			if(checkedNetworkItems) {
		    			for(var i = 0; i < checkedNetworkItems.length; i++) {
		    				var type = checkedNetworkItems[i].value.split(',')[1];
		    				var value =  checkedNetworkItems[i].value.split(',')[0];
		    				if(type == "RES-NETWORK") {
		    					var parentItem = $('#jqxTreeNetwork').jqxTree('getItem',checkedNetworkItems[i].parentElement);
		    					var parentType = parentItem.value.split(',')[1];
			    				var bizRes = {};
			    				bizRes.resSetSid = value;
			    				bizRes.bizSid = bizTypeSid;
			    				if(parentType == 'PNI') {
			    					bizRes.resCategory = 2;
			    				} else if(parentType == 'PNE') {
			    					bizRes.resCategory = 1;
			    				}
			    				bizRes.resSetType = type;
			    				bizResArray.push(bizRes);
		    				}
		    			}
	    			}
	    			var computeTreeItems = $('#jqxTreeCompute').jqxTree('getItems');
	    			var networkTreeItems = $('#jqxTreeNetwork').jqxTree('getItems');
	    			if( (computeTreeItems && computeTreeItems.length > 0) &&  (networkTreeItems && networkTreeItems.length > 0)) {
	    				clearFlag = 1;
	    			} else if((computeTreeItems && computeTreeItems.length > 0) &&  (!networkTreeItems || networkTreeItems.length == 0)){
	    				clearFlag = 0;
	    			}
	    		}
	    		Core.confirm({
					title:"提示",
					message:"确定要关联资源该资源数据吗？",
					confirmCallback:function(){
						Core.AjaxRequest({
							url : ws_url + "/rest/bizType/relate",
							params :{
								'bizReses': bizResArray,
								'bizSid': bizTypeSid,
								'clearFlag': clearFlag,
							},
				 			type : "POST",
			 				callback : function (data) {
			 					 // 刷新基本信息
		   		 			},
			 			    failure:function(data){
			 			    	
			 			    }
			 			});
					}
	        	});
	    	}
	    };


	    // 查询管理的
	    this.searchBizTypeInfo = function () {
	  	  var bizNameLike = $("#search-biz-type-name").val();
	  	  var parentBizNameLike = $("#search-biz-type-property").val();
	  	  // 当前tree选中的items
	  	  Core.AjaxRequest({
	  			url : ws_url + "/rest/bizType/findAll",
	  			params :{
	  				'bizNameLike': bizNameLike,
	  				'parentBizSids' : bizTypeSid,
	  				'parentBizNameLike': parentBizNameLike
	  			},
	  			type : "POST",
	  			callback : function (data) {
	  				 // 刷新基本信息
	  				var sourcedatagrid ={
	  		              datatype: "json",
	  		              localdata: data
	   			    };
	   			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	   			    $("#jqxgridBiz").jqxGrid({source: dataAdapter});
	  	 		}
	  	  });
	  	  
	    }

	 };


  
 
  
  
  