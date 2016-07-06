var adminLevelModel = function () {
	var me = this;
	this.items = ko.observableArray();
	//搜索条件
	this.searchObj = {
		"qm.levelNameLike": ""
	};

	// 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	this.initInput = function(){
		// 初始化查询组件
		$("#account-level").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
		$("#search-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
		// 初始化datagrid
		me.initAdminLeveldatagrid();
		// 初始化弹出框
		me.initPopWindow();
		// 初始化组件验证规则
		me.initValidator();
	};

	// 初始化用户datagrid的数据源
	this.initAdminLeveldatagrid = function(){
		var dataAdapter = Core.getPagingDataAdapter({
			gridId: "accountLevelGrid",
			url: ws_url + "/rest/level/find",
			params: me.searchObj
		});
		$("#accountLevelGrid").jqxGrid({
			width: "99.8%",
			theme: currentTheme,
			source: dataAdapter,
			virtualmode: true,
			rendergridrows: function(){
				for(var i = 0;i < dataAdapter.records.length;i++){
					dataAdapter.records[i].isAllowPrerender == '1' ? dataAdapter.records[i].isAllowPrerender = '是':dataAdapter.records[i].isAllowPrerender = '否';
					dataAdapter.records[i].isAllowCreateMovie == '1' ? dataAdapter.records[i].isAllowCreateMovie = '是':dataAdapter.records[i].isAllowCreateMovie = '否';
					dataAdapter.records[i].isAllowSceneAnalysis == '1' ? dataAdapter.records[i].isAllowSceneAnalysis = '是':dataAdapter.records[i].isAllowSceneAnalysis = '否';
					dataAdapter.records[i].isAllowCreateThumbnail == '1' ? dataAdapter.records[i].isAllowCreateThumbnail = '是':dataAdapter.records[i].isAllowCreateThumbnail = '否';
				}
				return dataAdapter.records;
			},
			columnsresize: true,
			pageable: true,
			pagesize: 10,
			autoheight: true,
			autowidth: false,
			sortable: true,
			selectionmode:"singlerow",
			localization: gridLocalizationObj,
			columns: [
				{ text: '账户等级', datafield: 'levelName'},
				{ text: '折扣（%）', datafield: 'discount'},
				{ text: '等级满足额度（元）', datafield: 'levelLimit'},
				{ text: '等级描述', datafield: 'levelDescription',width:180},
				{ text: '是否允许预渲染', datafield: 'isAllowPrerender',width:110},
				{ text: '是否允许生成视频小样', datafield: 'isAllowCreateMovie',width:130},
				{ text: '是否允许场景分析', datafield: 'isAllowSceneAnalysis',width:110},
				{ text: '是否允许生成缩略图', datafield: 'isAllowCreateThumbnail',width:130},
				{ text: '最大预渲染帧数', datafield: 'maxAllowedFrameCount',width:110},
				{ text: '最后操作人', datafield: 'updatedBy'},
				{ text: '最后操作时间', datafield: 'updatedDt',width:180}
			],
			showtoolbar: true,
			// 设置toolbar操作按钮
			rendertoolbar: function (toolbar) {
				var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
				var addAccountLevel = $("<div><a id='addAccountLevel' data-bind='click:addAccountLevel'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>添加&nbsp;&nbsp;</a></div>");
				var modifyAccountLevel = $("<div><a id='modifyAccountLevel' style='margin-left:-1px' data-bind='click:modifyAccountLevel'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
				var deleteAccountLevel = $("<div><a id='deleteAccountLevel' style='margin-left:-1px' data-bind='click:deleteAccountLevel'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-1'></i>删除&nbsp;&nbsp;</a></div>");

				toolbar.append(container);
				container.append(addAccountLevel);
				container.append(modifyAccountLevel);
				container.append(deleteAccountLevel);
			}
		});
		// 控制按钮是否可用
		$("#accountLevelGrid").on('rowselect', function (event) {
			var args = event.args;
			var index = args.rowindex;
			if(index >= 0) {
				$("#modifyAccountLevel").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
				$("#deleteAccountLevel").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
			}
		});

		$("#addAccountLevel").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false});
		$("#modifyAccountLevel").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
		$("#deleteAccountLevel").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
	};

	//初始化弹出框
	this.initPopWindow = function(){
		//初始化编辑账户等级弹窗
		$("#editLevelWindow").jqxWindow({
			showCollapseButton: false
			,width: 630
			,height: 380
			,isModal: true
			,autoOpen: false ,resizable: false
			,theme : currentTheme
			,cancelButton: $("#cancelEditLevelBtn")
			,initContent:function(){
				// 初始化新增账户等级页面组件
				$("#edit-level").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
				$("#edit-discount").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
				$("#edit-level-limit").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
				$("#edit-level-description").jqxInput({width: 450, height: 150, minLength: 1,theme:currentTheme});

				$("#saveEditLevelBtn").jqxButton({ width: 60, theme : currentTheme});
				$("#cancelEditLevelBtn").jqxButton({ width: 60, theme : currentTheme});

				var localData = [
					{label:"是",value:"1"},
					{label:"否",value:"0"}
				];
				var source ={
					datatype: "json",
					datafields: [
						{ name: 'label' },
						{ name: 'value' }
					],
					localData:localData
				};
				var dataAdapter = new $.jqx.dataAdapter(source);
				$("#edit-isAllowPrerender").jqxDropDownList({
					selectedIndex: 0, source: dataAdapter, autoDropDownHeight:true, displayMember: "label", valueMember: "value", theme:currentTheme, width:150, height: 22
				});
				$("#edit-isAllowCreateMovie").jqxDropDownList({
					selectedIndex: 0, source: dataAdapter, autoDropDownHeight:true, displayMember: "label", valueMember: "value", theme:currentTheme, width:150, height: 22
				});
				$("#edit-isAllowSceneAnalysis").jqxDropDownList({
					selectedIndex: 0, source: dataAdapter, autoDropDownHeight:true, displayMember: "label", valueMember: "value", theme:currentTheme, width:150, height: 22
				});
				$("#edit-isAllowCreateThumbnail").jqxDropDownList({
					selectedIndex: 0, source: dataAdapter, autoDropDownHeight:true, displayMember: "label", valueMember: "value", theme:currentTheme, width:150, height: 22
				});
				$("#edit-isAllowPrerender").jqxDropDownList({
					selectedIndex: 0, source: dataAdapter, autoDropDownHeight:true, displayMember: "label", valueMember: "value", theme:currentTheme, width:150, height: 22
				});
				$("#edit-maxAllowedFrameCount").jqxNumberInput({
						width: 150, height: 22, value:5,inputMode: 'simple', spinButtons: true, decimalDigits: 0, min: 0,max: 50,theme:currentTheme}
				);
			}
		});
		//初始化新增账户等级弹窗
		$("#addLevelWindow").jqxWindow({
			showCollapseButton: false
			,width: 630
			,height: 380
			,isModal: true
			,autoOpen: false ,resizable: false
			,theme : currentTheme
			,cancelButton: $("#cancelAddLevelBtn")
			,initContent:function(){
				// 初始化新增账户等级页面组件
				$("#add-level").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
				$("#add-discount").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
				$("#add-level-limit").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
				//$("#add-prior").jqxNumberInput({width: 150, height: 22, inputMode: 'simple', spinButtons: true, decimalDigits: 0, min: 0,max: 50,theme:currentTheme});
				$("#add-level-description").jqxInput({width: 450, height: 150, minLength: 1,theme:currentTheme});

				$("#saveAddLevelBtn").jqxButton({ width: 60, theme : currentTheme});
				$("#cancelAddLevelBtn").jqxButton({ width: 60, theme : currentTheme});

				var localData = [
					{label:"是",value:"1"},
					{label:"否",value:"0"}
				];
				var source ={
					datatype: "json",
					datafields: [
						{ name: 'label' },
						{ name: 'value' }
					],
					localData:localData
				};
				var dataAdapter = new $.jqx.dataAdapter(source);
				$("#add-isAllowPrerender").jqxDropDownList({
					selectedIndex: 0, source: dataAdapter, autoDropDownHeight:true, displayMember: "label", valueMember: "value", theme:currentTheme, width:150, height: 22
				});
				$("#add-isAllowCreateMovie").jqxDropDownList({
					selectedIndex: 0, source: dataAdapter, autoDropDownHeight:true, displayMember: "label", valueMember: "value", theme:currentTheme, width:150, height: 22
				});
				$("#add-isAllowSceneAnalysis").jqxDropDownList({
					selectedIndex: 0, source: dataAdapter, autoDropDownHeight:true, displayMember: "label", valueMember: "value", theme:currentTheme, width:150, height: 22
				});
				$("#add-isAllowCreateThumbnail").jqxDropDownList({
					selectedIndex: 0, source: dataAdapter, autoDropDownHeight:true, displayMember: "label", valueMember: "value", theme:currentTheme, width:150, height: 22
				});
				$("#add-isAllowPrerender").jqxDropDownList({
					selectedIndex: 0, source: dataAdapter, autoDropDownHeight:true, displayMember: "label", valueMember: "value", theme:currentTheme, width:150, height: 22
				});
				$("#add-maxAllowedFrameCount").jqxNumberInput({
					width: 150, height: 22, value:5,inputMode: 'simple', spinButtons: true, decimalDigits: 0, min: 0,max: 50,theme:currentTheme}
				);
			}
		});
	};

	// 初始化验证规则
	this.initValidator = function(){
		//新增账户等级验证
		$("#addLevelForm").jqxValidator({
			rules: [
				{ input: '#add-level', message: '不能为空', action: 'keyup, blur', rule: 'required' },
				{ input: '#add-level', message: '不能超过32个字符', action: 'keyup, blur', rule: function (input) {
					if(input.val().length > 32){
						return false;
					}else{
						return true;
					}
				}},
				{ input: '#add-discount', message: '不能为空', action: 'keyup, blur', rule: 'required' },
				{ input: '#add-discount', message: '只能输入数字', action: 'keyup, blur', rule: function (input) {
					if(/[^\d]/g.test(input.val())){
						return false;
					}else{
						return true;
					}
				}},
				{ input: '#add-discount', message: '折扣率不能大于100%', action: 'keyup, blur', rule: function (input) {
					if(input.val() > 100){
						return false;
					}else{
						return true;
					}
				}},
				{ input: '#add-level-limit', message: '不能为空', action: 'keyup, blur', rule: 'required' },
				{ input: '#add-level-limit', message: '请输入大于等于0的数', action: 'keyup, blur', rule: function (input) {
					if(input.val() < 0){
						return false;
					}else{
						return true;
					}
				}},
				{ input: '#add-level-limit', message: '输入额度过大', action: 'keyup, blur', rule: 'length=1,8' },
				{ input: '#add-level-description', message: '不能超过1024个字符', action: 'keyup, blur', rule: function (input) {
					if(input.val().length > 1024){
						return false;
					}else{
						return true;
					}
				}}
			]
		});

		//编辑账户等级验证
		$("#editLevelForm").jqxValidator({
			animationDuration: 1,
			rules: [
				{ input: '#edit-level', message: '不能为空', action: 'keyup, blur', rule: 'required' },
				{ input: '#edit-level', message: '不能超过32个字符', action: 'keyup, blur', rule: function (input) {
					if(input.val().length > 32){
						return false;
					}else{
						return true;
					}
				} },

				{ input: '#edit-discount', message: '不能为空', action: 'keyup, blur', rule: 'required' },
				{ input: '#edit-discount', message: '只能输入数字', action: 'keyup, blur', rule: function (input) {
					if(/[^\d]/g.test(input.val())){
						return false;
					}else{
						return true;
					}
				}},
				{ input: '#edit-discount', message: '折扣率不能大于100%', action: 'keyup, blur', rule: function (input) {
					if(input.val() > 100){
						return false;
					}else{
						return true;
					}
				}},

				{ input: '#edit-level-limit', message: '不能为空', action: 'keyup, blur', rule: 'required' },
				{ input: '#edit-level-limit', message: '请输入大于等于0的数', action: 'keyup, blur', rule: function (input) {
					if(input.val() < 0){
						return false;
					}else{
						return true;
					}
				}},
				{ input: '#edit-level-limit', message: '输入额度过大', action: 'keyup, blur', rule: 'length=1,8' },

				{ input: '#edit-level-description', message: '不能超过1024个字符', action: 'keyup, blur', rule: function (input, commit) {
					if(input.val().length > 1024){
						return false;
					}else{
						return true;
					}
				} }
			]
		});
	};

	//查询列表数据
	this.searchDataInfo = function(){
		$("#accountLevelGrid").jqxGrid('applyfilters');
		$('#accountLevelGrid').jqxGrid('refreshfilterrow');
		$('#accountLevelGrid').jqxGrid('clearselection');
	};

	//刷新
	this.refreshDataInfo = function(){
		$("#accountLevelGrid").jqxGrid('updatebounddata');
		$("#accountLevelGrid").jqxGrid('clearselection');
		$("#accountLevelGrid").jqxGrid('refresh');
	};
};

var adminLevelBindModel = function(obj){

	//查询列表数据
	this.searchAdminLevelInfo = function(){
		obj.searchObj["qm.levelNameLike"] = $("#account-level").val();
		obj.searchDataInfo();
	};

	//添加账户等级
	this.addAccountLevel = function(){
		$("#add-level").val("");
		$("#add-discount").val("");
		$("#add-level-limit").val("");
		$("#add-prior").val("");
		$("#add-level-description").val("");
		$("#add-isAllowPrerender").val(1);
		$("#add-isAllowCreateMovie").val(1);
		$("#add-isAllowSceneAnalysis").val(1);
		$("#add-isAllowCreateThumbnail").val(1);
		$("#add-isAllowPrerender").val(1);
		$("#add-maxAllowedFrameCount").val(5);

		var windowW = $(window).width();
		var windowH = $(window).height();
		$("#addLevelWindow").jqxWindow({ position: { x: (windowW-560)/2, y: (windowH-340)/2 } });
		$('#addLevelWindow').jqxWindow('open');
	};

	//编辑帐号等级
	this.modifyAccountLevel = function(){
		var rowindex = $('#accountLevelGrid').jqxGrid('getselectedrowindex');
		if(rowindex >= 0){

			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#editLevelWindow").jqxWindow({ position: { x: (windowW-560)/2, y: (windowH-340)/2 } });
			$('#editLevelWindow').jqxWindow('open');

			setTimeout(function(){
				var data = $('#accountLevelGrid').jqxGrid('getrowdata', rowindex);
				$("#edit-sid").val(data.levelSid);
				$("#edit-level").val(data.levelName);
				$("#edit-discount").val(data.discount);
				$("#edit-level-limit").val(data.levelLimit);
				//$("#edit-prior").val(data.prior);
				$("#edit-level-description").val(data.levelDescription);
				// 初始化编辑账户等级页面组件

				$("#edit-isAllowPrerender").val(data.isAllowPrerender == '是' ? 1 : 0);
				$("#edit-isAllowCreateMovie").val(data.isAllowCreateMovie == '是' ? 1 : 0);
				$("#edit-isAllowSceneAnalysis").val(data.isAllowSceneAnalysis == '是' ? 1 : 0);
				$("#edit-isAllowCreateThumbnail").val(data.isAllowCreateThumbnail == '是' ? 1 : 0);
				$("#edit-maxAllowedFrameCount").val(data.maxAllowedFrameCount);
			},200);

		}
	};

	//删除
	this.deleteAccountLevel = function(){
		var rowindex = $('#accountLevelGrid').jqxGrid('getselectedrowindex');
		if(rowindex >= 0){
			var data = $('#accountLevelGrid').jqxGrid('getrowdata', rowindex);
			Core.confirm({
				title:'提示'
				,message:'确定要删除该账户等级吗？'
				,confirmCallback:function(){
					Core.AjaxRequest({
						url : ws_url + "/rest/level/deleteLevel"
						,params : {levelSid:data.levelSid}
						,async: false
						,callback : function (data) {
							if(data == "success"){
								obj.refreshDataInfo();
							}else{
								Core.alert({
									message:'删除失败',
									type:'error'
								});
							}
						}
						,failure:function(data){
							Core.alert({
								message:'删除失败',
								type:'error'
							});
						}
					});
				}
			});
		}
	};

	//提交新增账户等级的信息
	this.addLevelSubmit = function(){
		//判断是否验证通过
		if($('#addLevelForm').jqxValidator('validate')){
			var addlevel = new Object;
			addlevel.levelName = $("#add-level").val();
			addlevel.discount = $("#add-discount").val();
			addlevel.levelLimit = $("#add-level-limit").val();

			//addlevel.prior = $("#add-prior").val();
			addlevel.levelDescription= $("#add-level-description").val();

			addlevel.isAllowPrerender = $("#add-isAllowPrerender").val();
			addlevel.isAllowCreateMovie=$("#add-isAllowCreateMovie").val();
			addlevel.isAllowSceneAnalysis=$("#add-isAllowSceneAnalysis").val();
			addlevel.isAllowCreateThumbnail=$("#add-isAllowCreateThumbnail").val();

			addlevel.maxAllowedFrameCount=$("#add-maxAllowedFrameCount").val();

			Core.AjaxRequest({
				url : ws_url + "/rest/level/insertLevel"
				,type: 'post'
				,params : addlevel
				,async : false
				,callback : function (data) {
					if(data){
						$("#addLevelWindow").jqxWindow('close');
						obj.refreshDataInfo();
					}else{
						Core.alert({
							message:'添加失败',
							type:'error'
						});
					}
				}
				,failure:function(data){}
			});
		}
	};

	//提交编辑账户等级的信息
	this.editLevelSubmit = function(){
		//判断是否验证通过
		if($('#editLevelForm').jqxValidator('validate')){
			var editlevel = new Object();
			editlevel.levelSid = $("#edit-sid").val();
			editlevel.levelName = $("#edit-level").val();
			editlevel.discount = $("#edit-discount").val();
			editlevel.levelLimit = $("#edit-level-limit").val();
			//editlevel.prior = $("#edit-prior").val();
			editlevel.levelDescription= $("#edit-level-description").val();
			editlevel.isAllowPrerender = $("#edit-isAllowPrerender").val();
			editlevel.isAllowCreateMovie=$("#edit-isAllowCreateMovie").val();
			editlevel.isAllowSceneAnalysis=$("#edit-isAllowSceneAnalysis").val();
			editlevel.isAllowCreateThumbnail=$("#edit-isAllowCreateThumbnail").val();
			editlevel.maxAllowedFrameCount=$("#edit-maxAllowedFrameCount").val();
			Core.AjaxRequest({
				url : ws_url + "/rest/level/updateLevel"
				,params : editlevel
				,async: false
				,callback : function (data) {
					if(data){
						$("#editLevelWindow").jqxWindow('close');
						obj.refreshDataInfo();
					}else{
						Core.alert({
							message:'修改失败',
							type:'error'
						});
					}
				}
				,failure:function(data){}
			});
		}
	};
};
$(function(){
	//初始化js对象
	var adminLevelObj = new adminLevelModel();
	//初始化页面主键
	adminLevelObj.initInput();
	//初始化主键事件
	var adminLevelModelObj = new adminLevelBindModel(adminLevelObj);

	ko.applyBindings(adminLevelModelObj);
});