var roleNameMess ="";
function getRadioValue(name) {
	var radioes = document.getElementsByName(name);
	for (var i = 0; i < radioes.length; i++) {
		if (radioes[i].checked) {
			return radioes[i].value;
		}
	}
	return false;
}

function setRadioValue(name, sRadioValue) { // 传入radio的name和选中项的值
	var oRadio = document.getElementsByName(name);
	for (var i = 0; i < oRadio.length; i++) // 循环
	{
		if (oRadio[i].value == sRadioValue) // 比较值
		{
			oRadio[i].checked = true; // 修改选中状态
			//break; 停止循环
		}
		else
		{
			oRadio[i].checked = false;
		}
	}
}
var roleModel = function() {
	var me = this;
	this.items = ko.observableArray();
	this.searchObj = {
		roleName : "",
	};
	// 角色数据
	this.searchRoleInfo = function() {
		Core.AjaxRequest({
			url : ws_url + "/rest/roles/",
			type : 'post',
			params : me.searchObj,
			async : false,
			callback : function(data) {
				me.items(data);
				var sourcedatagrid = {
					datatype : "json",
					localdata : me.items
				};
				var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
				$("#roleDatagrid").jqxGrid({
					source : dataAdapter
				});

				if (!(data == null || data == "")) {
					// 默认选择第一行
					$('#roleDatagrid').jqxGrid('selectrow', 0);
				}

			}
		});
	};

	this.searchRoleByName = function(name){
		var Todata = null;
		Core.AjaxRequest({
			url : ws_url + "/rest/roles/findRoleByName",
			type:'post',
			params:{
				roleName:name
			},
			async:false,
			callback : function (data) {
				Todata = data;
			}
		});
		return Todata;
	};

	// 给权限Tree赋值
	this.searchModuleTreeInfo = function() {
		var rowindex = $('#roleDatagrid').jqxGrid('getselectedrowindex');
		if (rowindex >= 0) {
			var data = $('#roleDatagrid').jqxGrid('getrowdata', rowindex);
			// 后台权限Tree数据
			Core.AjaxRequest({
				url : ws_url + "/rest/roles/modules?roleSid=" + data.roleSid
				+ "&moduleCategory=2",
				type : 'get',
				callback : function(data) {
					me.initBackModuleTree(data);
				}
			});

			// 前台权限Tree数据
			Core.AjaxRequest({
				url : ws_url + "/rest/roles/modules?roleSid=" + data.roleSid
				+ "&moduleCategory=1",
				type : 'get',
				callback : function(data) {
					me.initFontModuleTree(data);
				}
			});
		}
	};

	// 初始化tree
	this.initModuleTree = function(roleSid) {
		// 后台权限Tree数据
		Core.AjaxRequest({
			url : ws_url + "/rest/roles/modules?roleSid=" + roleSid
			+ "&moduleCategory=2",
			type : 'get',
			callback : function(data) {
				me.initBackModuleTree(data);
			}
		});

		// 前台权限Tree数据
		Core.AjaxRequest({
			url : ws_url + "/rest/roles/modules?roleSid=" + roleSid
			+ "&moduleCategory=1",
			type : 'get',
			callback : function(data) {
				me.initFontModuleTree(data);
			}
		});
	};

	// 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	this.initInput = function() {
		// 初始化查询组件
		// $("#search-roleName").jqxInput({placeHolder: "", height: 22, width:
		// 100, minLength: 1,theme:currentTheme});
		// $("#search-role-button").jqxButton({ width:
		// '50',height:"25",theme:currentTheme});
		$("#roleSavebtn").jqxButton({
			width : '50',
			height : "25",
			theme : currentTheme
		});

		// 初始化新增角色页面组件
		$("#add-roleName").jqxInput({
			placeHolder : "",
			height : 22,
			width : 150,
			minLength : 1,
			theme : currentTheme
		});
		$("#add-roleDesc").jqxInput({
			placeHolder : "",
			height : 40,
			width : 150,
			minLength : 1,
			theme : currentTheme
		});
		$("#addRoleSave").jqxButton({
			width : '50',
			height : "25",
			theme : currentTheme
		});
		$("#addRoleCancel").jqxButton({
			width : '50',
			height : "25",
			theme : currentTheme
		});

		// 初始化编辑用户页面组件
		$("#edit-roleName").jqxInput({
			placeHolder : "",
			height : 22,
			width : 150,
			minLength : 1,
			theme : currentTheme
		});
		$("#edit-roleDesc").jqxInput({
			placeHolder : "",
			height : 40,
			width : 150,
			minLength : 1,
			theme : currentTheme
		});
		$("#editRoleSave").jqxButton({
			width : '50',
			height : "25",
			theme : currentTheme
		});
		$("#editRoleCancel").jqxButton({
			width : '50',
			height : "25",
			theme : currentTheme
		});

	};
	this.setEditRoleForm = function(data) {
		$("#edit-roleName").jqxInput({
			value : data.account
		});
		$("#edit-roleDesc").jqxInput({
			value : data.realName
		});
	};
	// 初始化验证规则
	this.initValidator = function() {
		$('#addRoleForm').jqxValidator({
			rules : [ {input : '#add-roleName',message : '不能为空!',	action : 'keyup, blur',rule : 'required'},
				{input : '#add-roleName',message : '角色名称不能超过32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
				{input : '#add-roleName',message : '角色名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
					var list = me.searchRoleByName(input.val());
					if(list.length > 0){
						return false;
					}else{
						return true;
					}
				}
				},

				{
					input : '#add-roleDesc',
					message : '角色描述不能超过128个字符!',
					action : 'keyup, blur',
					rule : 'length=0,128'
				},

			]
		});

		$('#editRoleForm').jqxValidator({
			rules : [ {
				input : '#edit-roleName',
				message : '不能为空!',
				action : 'keyup, blur',
				rule : 'required'
			}, {
				input : '#edit-roleName',
				message : '角色名称不能超过32个字符!',
				action : 'keyup, blur',
				rule : 'length=1,32'
			},

				{input: '#edit-roleName', message: '角色名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
					if(roleNameMess == input.val()){
						return true;
					} else{
						var list = me.searchRoleByName(input.val());
						if(list.length > 0){
							return false;
						}else{
							return true;
						}
					}
				}
				},

				{
					input : '#edit-roleDesc',
					message : '角色描述不能超过128个字符!',
					action : 'keyup, blur',
					rule : 'length=0,128'
				},

			]
		});

		// 新增用户表单验证成功
		$('#addRoleForm').on('validationSuccess', function(event) {
			var role = Core.parseJSON($("#addRoleForm").serializeJson());
			role.roleType = getRadioValue("add-roleType");
			Core.AjaxRequest({
				url : ws_url + "/rest/roles/create",
				params : role,
				callback : function(data) {
					$("#addRoleWindow").jqxWindow('close');

					// 刷新角色列表
					var role = new roleModel();
					role.searchRoleInfo();
					// 关闭新增角色Window
				},
				failure:function(data)
				{
//					console.log(data);
				}
			});
		});

		// 编辑用户表单验证成功
		$('#editRoleForm').on('validationSuccess', function(event) {
			var role = Core.parseJSON($("#editRoleForm").serializeJson());
			role.roleType = getRadioValue("edit-roleType");
			Core.AjaxRequest({
				url : ws_url + "/rest/roles/update",
				params : role,
				callback : function(data) {
					$("#editRoleWindow").jqxWindow('close');
					// 刷新角色列表
					var role = new roleModel();
					role.searchRoleInfo();
				}
			});
		});

	};
	// 初始化用户datagrid的数据源
	this.initRoleDatagrid = function() {
		$("#roleDatagrid")
				.jqxGrid(
						{
							width : "60%",
							theme : currentTheme,
							columnsresize : true,
							pageable : true,
							pagesize : 10,
							autoheight : true,
							autowidth : false,
							sortable : true,
							selectionmode : "singlerow",
							localization : gridLocalizationObj,
							columns : [ {
								text : '角色名称',
								datafield : 'roleName'
							}, {
								text : '角色描述',
								datafield : 'roleDesc'
							},
								// { text: '操作', cellsrenderer:
								// imagerenderer,width:100,cellsalign:"center"}
							],
							showtoolbar : true,
							// 设置toolbar操作按钮
							rendertoolbar : function(toolbar) {
								var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
								var addBtn = $("<div onclick='popAddRoleWindow()'><a id='jqxAddRoleBtn' >&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
								var editBtn = $("<div onclick='popEditRoleWindow()'><a id='jqxEditRoleBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
								var deleteBtn = $("<div onclick='removeRole()'><a id='jqxDeleteRoleBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
								toolbar.append(container);
								container.append(addBtn);
								container.append(editBtn);
								container.append(deleteBtn);
							}
						});

		// 控制按钮是否可用
		$("#roleDatagrid").on('rowselect', function(event) {
			var args = event.args;
			var index = args.rowindex;
			var data = $('#roleDatagrid').jqxGrid('getrowdata', index);

			me.initModuleTree(data.roleSid);

			if (data.status == "2") {
				$("#jqxEditRoleBtn").jqxButton({
					disabled : false
				});
				$("#jqxDeleteRoleBtn").jqxButton({
					disabled : false
				});
			} else {
				$("#jqxEditRoleBtn").jqxButton({
					disabled : false
				});
				$("#jqxDeleteRoleBtn").jqxButton({
					disabled : false
				});
			}
		});

		$("#jqxEditRoleBtn").jqxButton({
			width : '60',
			theme : currentTheme,
			height : '18px',
			disabled : true
		});
		$("#jqxDeleteRoleBtn").jqxButton({
			width : '60',
			theme : currentTheme,
			height : '18px',
			disabled : true
		});
		$("#jqxAddRoleBtn").jqxButton({
			width : '60',
			height : '18px',
			theme : currentTheme
		});

	};
	// 初始化弹出window
	this.initPopWindow = function() {
		$("#addRoleWindow").jqxWindow({
			width : 400,
			height : 200,
			resizable : false,
			isModal : true,
			autoOpen : false,
			theme : currentTheme,
			cancelButton : $("#addRoleCancel"),
			modalOpacity : 0.3

		});

		$("#editRoleWindow").jqxWindow({
			width : 400,
			height : 200,
			resizable : false,
			isModal : true,
			autoOpen : false,
			theme : currentTheme,
			cancelButton : $("#editRoleCancel"),
			modalOpacity : 0.3
		});
	};

	// 初始化后台权限Tree
	this.initBackModuleTree = function(data) {
		// 初始化资源池的tree
		var source = {
			datatype : "json",
			datafields : [ {
				name : 'moduleSid'
			}, {
				name : 'parentSid'
			}, {
				name : 'moduleName'
			}, {
				name : 'moduleSid'
			}, {
				name : 'selected'
			} ],
			id : 'moduleSid',
			localdata : data
		};
		// create data adapter.
		var dataAdapter = new $.jqx.dataAdapter(source);
		// perform Data Binding.
		dataAdapter.dataBind();
		var records = dataAdapter.getRecordsHierarchy('moduleSid', 'parentSid',
				'items', [ {
					name : 'moduleName',
					map : 'label'
				}, {
					name : 'moduleSid',
					map : 'value'
				}, {
					name : 'selected',
					map : 'checked'
				} ]);
		// create jqxTree
		$('#backModuleTree').jqxTree({
			source : records,
			width : '100%',
			height : '100%',
			hasThreeStates : true,
			checkboxes : true,
			allowDrag : false,
			theme : currentTheme
		});
		// 全部展开
		$('#backModuleTree').jqxTree('expandAll');

	};

	// 初始化前台权限Tree
	this.initFontModuleTree = function(data) {
		// 初始化资源池的tree
		var source = {
			datatype : "json",
			datafields : [ {
				name : 'moduleSid'
			}, {
				name : 'parentSid'
			}, {
				name : 'moduleName'
			}, {
				name : 'moduleSid'
			}, {
				name : 'selected'
			} ],
			id : 'moduleSid',
			localdata : data
		};
		// create data adapter.
		var dataAdapter = new $.jqx.dataAdapter(source);
		// perform Data Binding.
		dataAdapter.dataBind();
		var records = dataAdapter.getRecordsHierarchy('moduleSid', 'parentSid',
				'items', [ {
					name : 'moduleName',
					map : 'label'
				}, {
					name : 'moduleSid',
					map : 'value'
				}, {
					name : 'selected',
					map : 'checked'
				} ]);
		// create jqxTree
		$('#frontModuleTree').jqxTree({
			source : records,
			width : '99.7%',
			height : '100%',
			hasThreeStates : true,
			checkboxes : true,
			allowDrag : false,
			theme : currentTheme
		});
		// 全部展开
		$('#frontModuleTree').jqxTree('expandAll');

	};

};

// 弹出新建角色窗口
function popAddRoleWindow() {
	$("#add-roleName").val("");
	$("#add-roleDesc").val("");
	setRadioValue("add-roleType", "01");

	var windowW = $(window).width();
	var windowH = $(window).height();
	$("#addRoleWindow").jqxWindow({
		position : {
			x : (windowW - 400) / 2,
			y : (windowH - 200) / 2
		}
	});
	$("#addRoleWindow").jqxWindow('open');
}
// 提交新角色
function submitAddRole() {
	$("#addRoleForm").jqxValidator('validate');
}

// 弹出编辑角色窗口
function popEditRoleWindow() {
	var rowindex = $('#roleDatagrid').jqxGrid('getselectedrowindex');
	var ok = $("#jqxEditRoleBtn").jqxButton("disabled");
	if (rowindex >= 0 && !ok) {
		var data = $('#roleDatagrid').jqxGrid('getrowdata', rowindex);
		roleNameMess = data.roleName;
		$("#roleSid").val(data.roleSid);
		$("#edit-roleName").val(data.roleName);
		$("#edit-roleDesc").val(data.roleDesc);
		setRadioValue("edit-roleType", data.roleType);
		var windowW = $(window).width();
		var windowH = $(window).height();
		$("#editRoleWindow").jqxWindow({
			position : {
				x : (windowW - 400) / 2,
				y : (windowH - 200) / 2
			}
		});
		$("#editRoleWindow").jqxWindow('open');
	}

}
// 提交新角色
function submitEditRole() {
	$("#editRoleForm").jqxValidator('validate');
}

// 保存角色的权限
function saveRoleModule() {
	var rowindex = $('#roleDatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		var data = $('#roleDatagrid').jqxGrid('getrowdata', rowindex);
		// 获取后台选中的角色
		var backItems = $('#backModuleTree').jqxTree('getCheckedItems');
		// 获取前台选中的角色
		var frontItems = $('#frontModuleTree').jqxTree('getCheckedItems');
		var list = [];

		//获取前台的选中菜单的父级菜单
		var expandFrontItems = $('#frontModuleTree').jqxTree('getExpandItems');
		for(var i=0;i<expandFrontItems.length;i++){
			if (expandFrontItems[i].checked == null){
				var obj = {};
				obj.roleSid = data.roleSid;
				obj.moduleSid = expandFrontItems[i].value;
				list.push(obj);
			}
		}
		//获取后台的选中菜单的父级菜单
		var expandBackItems = $('#backModuleTree').jqxTree('getExpandItems');
		for(var i=0;i<expandBackItems.length;i++){
			if (expandBackItems[i].checked == null){
				var obj = {};
				obj.roleSid = data.roleSid;
				obj.moduleSid = expandBackItems[i].value;
				list.push(obj);
			}
		}
		if(backItems.length == 0 && backItems.frontItems ==0){
			var obj = {};
			obj.roleSid = data.roleSid;
			obj.moduleSid = null;
			list.push(obj);
		}else{
			for(var i=0;i<backItems.length;i++){
				var obj = {};
				obj.roleSid = data.roleSid;
				obj.moduleSid = backItems[i].value;
				list.push(obj);
			}

			for(var j=0;j<frontItems.length;j++){
				var obj = {};
				obj.roleSid = data.roleSid;
				obj.moduleSid = frontItems[j].value;
				list.push(obj);
			}
		}

		Core.AjaxRequest({
			url : ws_url + "/rest/roles/add",
			type:'post',
			params:list,
			callback : function (data) {

			}
		});

	}
}

// 删除角色
function removeRole() {
	var rowindex = $('#roleDatagrid').jqxGrid('getselectedrowindex');
	var ok = $("#jqxDeleteRoleBtn").jqxButton("disabled");
	if (rowindex >= 0 && !ok) {
		var data = $('#roleDatagrid').jqxGrid('getrowdata', rowindex);
		Core.confirm({
			title : "请选择",
			message : "您确定要删除该角色吗？",
			confirmCallback : function() {
				Core.AjaxRequest({
					url : ws_url + "/rest/roles/delete/" + data.roleSid,
					type : "get",
					callback : function(data) {
						var role = new roleModel();
						role.searchRoleInfo();
					},
					failure : function(data) {

					}
				});
			}
		});
	}
}