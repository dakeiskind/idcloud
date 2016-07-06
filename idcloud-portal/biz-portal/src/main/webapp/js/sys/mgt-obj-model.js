
//当前管理对象sid
var delRes = false;
var fileIndex = 0;
var MgtObjModel = function () {
	var mgtObjSid = null;
	var me = this;
	this.itemsBizType = ko.observableArray();
	this.searchObj = {
		parentMgtObjIds: null
	};
	this.mgtObjCondition = {};
    this.mgtObjParams = {
		condition: me.mgtObjCondition
    };
	//当前管理对象级别
	var level = null;
	//存储当前选择管理对象节点信息
	var mgtObj = {};

	var quotaChanges = [];
	
	var editPopField = [];

	//初始化页面中的jqx组件
	this.initInput = function(){
		$("#search-mgtobj-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
		$("#search-mgtobj-manager").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
		
		var codesearch = new codeModel({width:100,autoDropDownHeight:true});
		codesearch.getCommonCode("search-mgtobj-type","PROJECT_TYPE",true);
		
		//$("#search-mgtobj-startDate-start").jqxDateTimeInput({width: '100px', culture: 'zh-CN', formatString: 'd', height: 22,showFooter:true,clearString:'清除',todayString:'今天',allowNullDate: true,value: null,theme:"metro", formatString: 'yyyy-MM-dd'});
		//$("#search-mgtobj-startDate-end").jqxDateTimeInput({width: '100px', culture: 'zh-CN', formatString: 'd', height: 22,showFooter:true,clearString:'清除',todayString:'今天',allowNullDate: true,value: null,theme:"metro", formatString: 'yyyy-MM-dd'});
		//$("#search-mgtobj-endDate-start").jqxDateTimeInput({width: '100px', culture: 'zh-CN', formatString: 'd', height: 22,showFooter:true,clearString:'清除',todayString:'今天',allowNullDate: true,value: null,theme:"metro",formatString: 'yyyy-MM-dd'});
		//$("#search-mgtobj-endDate-end").jqxDateTimeInput({width: '100px', culture: 'zh-CN', formatString: 'd', height: 22,showFooter:true,clearString:'清除',todayString:'今天',allowNullDate: true,value: null,theme:"metro",formatString: 'yyyy-MM-dd'});
		
		$("#search-mgtObj-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
		$('#jqxTabsBiz').jqxTabs({width : '100%', height : '99.2%', position : 'top', theme : currentTheme});
		$('#jqxTreeBiz').jqxTree({height: '100%', width: '100%', theme : currentTheme});
		//$('#jqxExpanderBiz').jqxExpander({width:'14.9%', height:'100%', theme : currentTheme});
		$("#jqxgridBiz").jqxGrid({
			width: "98%",
//			height: "340px",
			theme:currentTheme,
			columnsresize: true,
			pageable: true, 
			pagesize: 10, 
			autoheight: false,
			rowsheight: 30,
			localization: gridLocalizationObj,
			selectionmode:"singlerow",
			columns: [
				{ text: '项目名称', datafield: 'name',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
              	  var td = $("#jqxgridBiz").jqxGrid('getrowdata', row);
            	  if(td.inNoticeTime=="1"){
            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.name+"</div>";
            	  }else if(td.inNoticeTime=="-1"){
            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.name+"</div>";
            	  }
              }},
				{ text: '项目分类', datafield: 'parentName',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
              	  var td = $("#jqxgridBiz").jqxGrid('getrowdata', row);
            	  if(td.inNoticeTime=="1"){
            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.parentName+"</div>";
            	  }else if(td.inNoticeTime=="-1"){
            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.parentName+"</div>";
            	  }
                }},
				{ text: '状态', datafield: 'statusName',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
              	  var td = $("#jqxgridBiz").jqxGrid('getrowdata', row);
            	  if(td.inNoticeTime=="1"){
            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.statusName+"</div>";
            	  }else if(td.inNoticeTime=="-1"){
            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.statusName+"</div>";
            	  }
                }},
				{ text: '创建人', datafield: 'createdBy',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
              	  var td = $("#jqxgridBiz").jqxGrid('getrowdata', row);
            	  if(td.inNoticeTime=="1"){
            		  return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.createdBy+"</div>";
            	  }else if(td.inNoticeTime=="-1"){
            		  return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.createdBy+"</div>";
            	  }
                }},
                { text: '开始时间', datafield: 'projectStartTime',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
                	var td = $("#jqxgridBiz").jqxGrid('getrowdata', row);
                	if(td.inNoticeTime=="1"){
                		return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.projectStartTime+"</div>";
                	}else if(td.inNoticeTime=="-1"){
                		return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.projectStartTime+"</div>";
                	}
                }},
                { text: '到期时间', datafield: 'projectEndTime',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
                	var td = $("#jqxgridBiz").jqxGrid('getrowdata', row);
                	if(td.inNoticeTime=="1"){
                		return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.projectEndTime+"</div>";
                	}else if(td.inNoticeTime=="-1"){
                		return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:25px;'>&nbsp;"+td.projectEndTime+"</div>";
                	}
                }},
                { text: '操作', cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
                	var td = $("#jqxgridBiz").jqxGrid('getrowdata', row);
                	if(td.inNoticeTime=="1"){
                		return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:28px;'><div class='customButton' "+
                			"style='margin-top:4px;margin-left:10px;float:left' onclick='popMgtObjDetailInfoWindow("+JSON.stringify(td)+")'>详情</div></div>";
                	}else if(td.inNoticeTime=="-1"){
                		return "<div style='background-color: #FB4242;width: 100%;height: 100%;line-height:28px;'><div class='customButton' " +
                			"style='margin-top:4px;margin-left:10px;float:left' onclick='popMgtObjDetailInfoWindow("+JSON.stringify(td)+")'>详情</div></div>";
                	}else{
                		return "<div style='width: 100%;height: 100%;line-height:28px;'><div class='customButton' " +
            			"style='margin-top:4px;margin-left:10px;float:left' onclick='popMgtObjDetailInfoWindow("+JSON.stringify(td)+")'>详情</div></div>";
                	}
                }}
			],
			showtoolbar: true,
			rendertoolbar: function (toolbar) {
				var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
				var groupAddBtn = $("<div><a id='mgtGroupAddBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增项目分类&nbsp;&nbsp;</a></div>");
				var groupEditBtn = $("<div><a id='mgtGroupEditBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>修改项目分类&nbsp;&nbsp;</a></div>");
				var groupDeleteBtn = $("<div><a id='mgtGroupDeleteBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除项目分类&nbsp;&nbsp;</a></div>");
				var orgAddBtn = $("<div><a id='bizTypeAddBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增项目&nbsp;&nbsp;</a></div>");
				var orgEditBtn = $("<div><a id='bizTypeEditBtn'  style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑项目&nbsp;&nbsp;</a></div>");
				var orgDelBtn = $("<div><a id='bizTypeDelBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除项目&nbsp;&nbsp;</a></div>");
				//var mgtObjApproveBtn = $("<div><a id='mgtObjApproveBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-ok-3'></i>审核&nbsp;&nbsp;</a></div>");
				var mtgObjQuotaBtn = $("<div><a id='mtgObjQuotaBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-book'></i>配额管理&nbsp;&nbsp;</a></div>");
				var usersMessageBtn = $("<div><a id='usersMessageBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-user'></i>成员管理&nbsp;&nbsp;</a></div>");
				toolbar.append(container);
				container.append(groupAddBtn);
				container.append(groupEditBtn);
				container.append(groupDeleteBtn);
				container.append(orgAddBtn);
				container.append(orgEditBtn);
				container.append(orgDelBtn);
				//container.append(mgtObjApproveBtn);
				container.append(mtgObjQuotaBtn);
				container.append(usersMessageBtn);

				$("#mgtGroupAddBtn").jqxButton({ width: '100',theme:currentTheme,disabled: false});
				$("#mgtGroupEditBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
				$("#mgtGroupDeleteBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
				$("#bizTypeAddBtn").jqxButton({ width: '100',theme:currentTheme,disabled: false});
				$("#bizTypeEditBtn").jqxButton({ width: '80',theme:currentTheme,disabled: true});
				$("#bizTypeDelBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
				//$("#mgtObjApproveBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
				$("#mtgObjQuotaBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
				$("#usersMessageBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
				
				$("#mgtGroupAddBtn").show();
				$("#bizTypeAddBtn").show();
				
   				$("#mgtGroupEditBtn").hide();
   				$("#mgtGroupDeleteBtn").hide();
   				$("#bizTypeEditBtn").hide();
   				$("#bizTypeDelBtn").hide();
   				$("#mtgObjQuotaBtn").hide();
   				$("#usersMessageBtn").hide();
   				//$("#mgtObjApproveBtn").hide();
				
			}
		});
		
		 // 控制按钮是否可用
  	    $("#jqxgridBiz").on('rowselect', function (event) {
   			//$("#bizTypeResBtn").jqxButton({disabled: false});
   			var args = event.args; 
   			var index = args.rowindex;
   			var data = $('#jqxgridBiz').jqxGrid('getrowdata', index);
   			
   			if(data.level=="0"){
   				if(data.mgtObjSid=='-999'){
   					$("#mgtGroupEditBtn").hide();
   					$("#mgtGroupDeleteBtn").hide();
   				}else{
   					$("#mgtGroupEditBtn").show();
   					$("#mgtGroupDeleteBtn").show();
   				}
   				$("#bizTypeAddBtn").show();
   				$("#bizTypeAddBtn").jqxButton({disabled: false});
   				$("#mgtGroupEditBtn").jqxButton({disabled: false});
   				$("#mgtGroupDeleteBtn").jqxButton({disabled: false});
   				$("#bizTypeEditBtn").hide();
   				$("#bizTypeDelBtn").hide();
   				$("#mtgObjQuotaBtn").hide();
   				$("#usersMessageBtn").hide();
   			}else{
   				$("#bizTypeAddBtn").hide();
   				$("#mgtGroupEditBtn").hide();
   				$("#mgtGroupDeleteBtn").hide();
   				
   				$("#bizTypeEditBtn").show();
   				$("#bizTypeDelBtn").show();
   				$("#mtgObjQuotaBtn").show();
   				$("#usersMessageBtn").show();
   				//if(data.status=="01"){
   				//	//$("#mgtObjApproveBtn").show();
   	   			//	$("#mgtObjApproveBtn").jqxButton({disabled: false});
   	   			//}else{
   	   			//	$("#mgtObjApproveBtn").hide();
	   			//	$("#mgtObjApproveBtn").jqxButton({disabled: true});
   	   			//}
   				$("#bizTypeEditBtn").jqxButton({disabled: false});
   				$("#bizTypeDelBtn").jqxButton({disabled: false});
   				$("#mtgObjQuotaBtn").jqxButton({disabled: false});
   	   			$("#usersMessageBtn").jqxButton({disabled: false});
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
		$("#addGroupWindow").jqxWindow({
			width: 400, 
			height:200,
			resizable: false,  
			isModal: true, 
			autoOpen: false, 
			theme:currentTheme,
			cancelButton: $("#cancelAddGroupType"), 
			modalOpacity: 0.3           
		});
		$("#add-mgtObjName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		$("#add-mgtObjDesc").jqxInput({placeHolder: "", height: 46, width: 250, minLength: 1,theme:currentTheme});
		$("#saveAddGroupType").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
		$("#cancelAddGroupType").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
		//新增业务弹出窗口初始化-end

		//编辑业务弹出窗口初始化-start
		$("#editGroupWindow").jqxWindow({
			width: 400, 
			height:250,
			resizable: false,  
			isModal: true, 
			theme:currentTheme,
			autoOpen: false, 
			cancelButton: $("#cancelEditGroupType"), 
			modalOpacity: 0.3
		});
		$("#edit-mgtObjName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		$("#edit-mgtObjDesc").jqxInput({placeHolder: "", height: 46, width: 250, minLength: 1,theme:currentTheme});
		$("#saveEditGroupType").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
		$("#cancelEditGroupType").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
		//编辑业务弹出窗口初始化-start
		
		//审核窗口初始化-start
		$("#approveMgtObjWindow").jqxWindow({
			width: 400, 
			height:200,
			resizable: false,  
			isModal: true, 
			autoOpen: false, 
			theme:currentTheme,
			cancelButton: $("#cancelApproveResult"), 
			modalOpacity: 0.3           
		});
		$("#saveApproveResult").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
		$("#cancelApproveResult").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
		//审核弹出窗口初始化-end
		
		//审核窗口初始化-start
		$("#mgtQuotaWindow").jqxWindow({
			width: 750, 
			height:230,
			resizable: false,  
			isModal: true, 
			autoOpen: false, 
			theme:currentTheme,
			cancelButton: $("#cancelMgtQuota"), 
			modalOpacity: 0.3           
		});
		$("#saveMgtQuota").jqxButton({width: '60',theme:currentTheme,height: '28px', disabled: false });
		$("#cancelMgtQuota").jqxButton({width: '60',theme:currentTheme,height: '28px', disabled: false });
		//审核弹出窗口初始化-end
		
		//关联项目经理窗口初始化-start
		$("#chooseManagerWindow").jqxWindow({
			width: 500, 
			height:300,
			resizable: false,  
			isModal: true, 
			autoOpen: false, 
			theme:currentTheme,
			cancelButton: $("#cancelMgtManager"), 
			modalOpacity: 0.3           
		});
		$("#saveMgtManager").jqxButton({width: '60',theme:currentTheme,height: '28px', disabled: false });
		$("#cancelMgtManager").jqxButton({width: '60',theme:currentTheme,height: '28px', disabled: false });
		
		$("#search-manager-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
		$("#search-manager-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
		
		$("#managerDatagrid").jqxGrid({
            width: "99%",
			theme:currentTheme,
            columnsresize: true,
            pageable: true,
            pagesize: 10, 
            autoheight: true,
            autowidth: false,
            sortable: false,
            selectionmode:"checkbox",
            localization: gridLocalizationObj,
            columns: [
                { text: '用户姓名', datafield: 'realName',width:150},
                { text: '邮箱', datafield: 'email',width:150},
                { text: '电话', datafield: 'mobile',width:150}
            ]
        });
		
		//关联项目经理窗口初始化-end

		//$("#bizTypeResBtn").jqxButton({ width: '80',theme:currentTheme,disabled: true});
		
		$("#findProjectUsersWindow").jqxWindow({
			width: 900, 
			height:500,
			resizable: false,  
			isModal: true, 
			autoOpen: false, 
			theme:currentTheme,
			///cancelButton: $("#cancelMgtQuota"), 
			modalOpacity: 0.3           
		});
		
		//项目详情
		$("#view-mgtObjMsg").jqxWindow({
			width: 400, 
			height:350,
			resizable: false,  
			isModal: true, 
			autoOpen: false, 
			theme:currentTheme,
			cancelButton: $("#cancelViewMgtObj"), 
			modalOpacity: 0.3,
			initContent: function () {
        	    $("#cancelViewMgtObj").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        }
		});
	};

	// 初始化业务类型树
	this.initBizTypeTree = function(){
		Core.AjaxRequest({
			url : ws_url + "/rest/mgtObj/findMgtObjTreeData",
			type:'post',
			params: {},
			callback : function (data) {
				var source ={
					datatype: "json",
					datafields: [
						{ name: 'mgtObjSid' },
						{ name: 'parentId' },
						{ name: 'name' },
						{ name: 'level' },
						{ name: 'mgtObjIcon' },
						{ name: 'description' }
					],
					id: 'mgtObjSid',
					localdata: data
				};
				var dataAdapter = new $.jqx.dataAdapter(source);
				dataAdapter.dataBind();
				var records = dataAdapter.getRecordsHierarchy('mgtObjSid', 'parentId', 'items', 
						[{ name: 'name', map: 'label'},{ name: 'mgtObjSid', map: 'value'}, {name: 'level', map:'level'},{ name: 'mgtObjIcon', map: 'icon'}]);
				$('#jqxTreeBiz').jqxTree({source: records});
				$('#jqxTreeBiz').jqxTree('expandItem', $("#jqxTreeBiz").jqxTree("getItems")[0]);
				$('#jqxTreeBiz').jqxTree('selectItem', $("#jqxTreeBiz").find('li:first')[0]);
			}
		 });
	};

	//初始化业务datagrid
	this.initBizTypeDatagrid = function(data){
//		var param ={};
//		if(mgtObjSid) {
//			param = {parentIds: mgtObjSid};
//		}
		me.mgtObjCondition['parentMgtObjIds'] = mgtObjSid;
		Core.AjaxRequest({
			url : ws_url + "/rest/mgtObj/find",
			params : me.mgtObjParams,
			type : 'post',
			callback : function(data) {
				var sourcedatagrid ={
					datatype: "json",
					localdata: data
				};
				var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
				$("#jqxgridBiz").jqxGrid({source: dataAdapter});
 				$("#add-bizSid").val(mgtObjSid);
 				$("#edit-bizSid").val(mgtObjSid);
// 				$("#jqxgridBiz").jqxGrid('selectrow', 0);
			}
		});
	};

	this.popBaseMgtObjField = function (oper, saveFun) {
		var id = oper + 'MgtObj';
		var popWindow = $('#' + id);
		if(popWindow.length == 0) {

			var template = $('#popTemplate');
			var copyTemplate = template.clone();
			copyTemplate.attr('id', id);
			var parent = template.parent();
			parent.append(copyTemplate);
			popWindow = copyTemplate;
			if(oper == 'add'){
				popWindow.find('.title').html('新增');
			}else if(oper == 'edit'){
				popWindow.find('.title').html('编辑');
			}
			popWindow.jqxWindow({
				width: 530, 
				height:550,
				resizable: false,  
				isModal: true, 
				autoOpen: false, 
				theme:currentTheme,
				cancelButton: popWindow.find('.cancel'), 
				modalOpacity: 0.3
			});
			popWindow.find('.save').jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
			popWindow.find('.cancel').jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });

			//加载管理对象定义
			Core.AjaxRequest({
				url : ws_url + "/rest/mgtObj/load/def",
				async: false,
				params : {
					condition: {
							mgtBizMode: 'project'
						}
					},
					type : "POST",
					callback : function (data) {
						var baseField = null;
						if(oper == 'add') {
							baseField = [
//							                 {attrName: '名称',  attrKey: 'name', displayType: 'text', },
							                 {attrName: '父名称', attrKey: 'parentName', displayType: 'text' , displayStyle: {
							                	 disabled: true
							                 }},
//							                 {attrName: '描述', attrKey: 'description', displayType: 'textarea', displayStyle: {
//							                	 height: 46
//							                 }}
							                 ];
						} else if(oper == 'edit') {
							baseField = [
//						                 {attrName: '名称',  attrKey: 'name', displayType: 'text', },
						                 {attrName: '父名称', attrKey: 'parentId', displayType: 'dropDownTree' , displayStyle: {
						                	 disabled: false
						                 }, loadData: function (elem) {
						                	 Core.AjaxRequest({
						         	  			url : ws_url + "/rest/mgtObj/find/parent",
						         	  			type:"post",
						         	  			params:{
						         	  				condition: {
						         	  					'mgtObjSid': mgtObjSid,
						         	  					'level' : "0"
//						         	  						,
//						         	  					'parentId': mgtObj['parentId']
						         	  				}
						         	  			},
						         	  			async:false,
						         	  			callback : function(data) {
						         	  				if(data!=null&&data.length>0){
						         	  					var dataAdapter = new $.jqx.dataAdapter(data);
						         	  					dataAdapter.dataBind();
						         	  					var records = dataAdapter.getRecordsHierarchy('mgtObjSid', 'parentId', 
						         	  							'items', [{ name: 'name', map: 'label'},{ name : 'mgtObjSid', map: 'value'}]);
						         	  					elem.jqxTree({source: records});
						         	  				}
						           				}
						           			 });
						                 }},
//						                 {attrName: '描述', attrKey: 'description', displayType: 'textarea', displayStyle: {
//						                	 height: 46
//						                 }}
						                 ];
							editPopField = baseField;
						}
						for(var j = 0; j < baseField.length; j++) {
							me.generateInputComponent(oper + "-main", popWindow, baseField[j]);
						}
						for(var i = 0; i < data.length; i++) {
							me.generateInputComponent(oper + "-ext", popWindow, data[i]);
						}
						me.initValidateRule(oper + "-ext",id,data);
					}
			});

			// 新增业务类型验证成功
			$('#' + id + ' .form').on('validationSuccess', saveFun);

			//绑定添加业务类型提交事件
			$('#' + id + ' .save').on('click', function () {
				$('#' + id + ' .form').jqxValidator('validate');
			});
		}
		// 设置弹出框位置
		var windowW = $(window).width(); 
		var windowH = $(window).height(); 
		popWindow.jqxWindow({ height: 400, position: { x: (windowW-400)/2, y: (windowH-400)/2 } });
		popWindow.jqxWindow('open');
	};
	
	this.initValidateRule = function(pre,id,data){
		var rulesArray = new Array;
		if(data.length>0){
			for ( var int = 0; int < data.length; int++) {
				if(data[int].validateRule!="" && data[int].validateRule!=null){
					var validateRule = data[int].validateRule;
					var rules = validateRule.split(";");
					for(var i=0;i<rules.length;i++){
						if(rules[i]!=""){
							if(rules[i]=="required"){
								rulesArray.push({ input: '#'+pre+ "-"+data[int].attrKey, message: '不能为空', action: 'keyup, blur', rule: 'required' });
							}else if(rules[i].indexOf("email")!=-1){
								rulesArray.push({ input: '#'+pre+ "-"+data[int].attrKey, message: '请输入正确的邮箱地址', action: 'keyup, blur', rule: rules[i] });
							}else if(rules[i].indexOf("ignoreSpecial")!=-1){
								rulesArray.push({ input: '#'+pre+ "-"+data[int].attrKey, message: '请输入正确的名称', action: 'keyup, blur', rule: function(input,commit){
					        	    	if(/^[A-Za-z0-9_\-\u4e00-\u9fa5]+$/g.test(input.val())){
						  					return true;
					  	  				}else{
					  	  					return false;
					  	  				}
									}
								});
							}else if(rules[i].indexOf("letterNumber")!=-1){
								rulesArray.push({ input: '#'+pre+ "-"+data[int].attrKey, message: '请输入字母和数字的组合', action: 'keyup, blur', rule: function(input,commit){
									if(/^[a-zA-Z\d-]+$/g.test(input.val())){
										return true;
									}else{
										return false;
									}
								}
								});
							}else if(rules[i].indexOf("maxLength")!=-1){
								rulesArray.push({ input: '#'+pre+ "-"+data[int].attrKey, message: '长度不能超过'+rules[i].split("=")[1]+'个字符', action: 'keyup, blur', rule: rules[i] });
							}else if(rules[i].indexOf("mobile")!=-1){
								rulesArray.push({ input: '#'+pre+ "-"+data[int].attrKey, message: '请输入正确的电话号码', action: 'keyup, blur',rule: function(input,commit){
					        	    	if(/^[1-9]\d{10}$/g.test(input.val())){
						  					return true;
					  	  				}else{
					  	  					return false;
					  	  				}
									}
								});
							}else if(rules[i].indexOf("date")!=-1){
								rulesArray.push({ input: '#'+pre+ "-"+data[int].attrKey, message: '请选择时间', action: 'valueChanged',rule: function(input,commit){
				        	    	if(input.val()==null||input.val()==""){
					  					return false;
				  	  				}else{
				  	  					return true;
				  	  				}
								}
							});
						}
						}
					}
				}
				if(data[int].attrKey=="projectName"){
					rulesArray.push({ input: '#'+pre+ "-"+data[int].attrKey, message: '该项目已存在！', action: 'keyup, blur',rule: function(input,commit){
							var flag = false;
							var condition = {};
							var params = {
					  			condition: condition
					  	  	};
							var oldName = $("#"+id+" .oldProjectName").val();
							if(oldName!=input.val()){
								condition['name'] = input.val();
								
								Core.AjaxRequest({
									url : ws_url + "/rest/mgtObj/find",
									type : "post",
									params : params,
									showTimeout : false,
									showMsg:false,
									async:false,
									callback : function(data) {
										if(data.length==0){
											flag = true;
										}
									}
								});
							}else{
								flag = true;
							}
					    	return flag;
						}
					});
				}else if(data[int].attrKey=="projectEName"){
						rulesArray.push({ input: '#'+pre+ "-"+data[int].attrKey, message: '该项目英文简写已存在！', action: 'keyup, blur',rule: function(input,commit){
							var flag = false;
							var condition = {};
							var params = {
					  			condition: condition
					  	  	};
							var oldName = $("#"+id+" .oldProjectEName").val();
							if(oldName!=input.val()){
								condition['attrValue'] = input.val();
						  	  	condition['attrKey'] = "projectEName";
								
								Core.AjaxRequest({
									url : ws_url + "/rest/mgtObj/checkMgtObjExt",
									type : "post",
									params : params,
									showTimeout : false,
									showMsg:false,
									async:false,
									callback : function(data) {
										if(data.length==0){
											flag = true;
										}
									}
								});
							}else{
								flag = true;
							}
					    	return flag;
						}
					});
				}else if(data[int].attrKey=="projectId"){
					rulesArray.push({ input: '#'+pre+ "-"+data[int].attrKey, message: '该项目编号已存在！', action: 'keyup, blur',rule: function(input,commit){
							var flag = false;
							var condition = {};
							var params = {
					  			condition: condition
					  	  	};
							var oldName = $("#"+id+" .oldProjectId").val();
							if(oldName!=input.val()){
								condition['attrValue'] = input.val();
						  	  	condition['attrKey'] = "projectId";
								
								Core.AjaxRequest({
									url : ws_url + "/rest/mgtObj/checkMgtObjExt",
									type : "post",
									params : params,
									showTimeout : false,
									showMsg:false,
									async:false,
									callback : function(data) {
										if(data.length==0){
											flag = true;
										}
									}
								});
							}else{
								flag = true;
							}
						    return flag;
						}
					});
				}
		    }
		}
		$('#' + id + ' .form').jqxValidator({
	        rules: rulesArray
		});
	}; 

	this.generateInputComponent = function (oper, window, define) {
		var displayType = define['displayType'];
		var attrName = define['attrName'];
		var attrKey = define['attrKey'];
		var displayStyle = define['displayStyle'];
		var description = define['description'];
		var trTemplate = window.find('.content tr:first');
		var copyTemplate = trTemplate.clone();
		copyTemplate.show();
		if((define.displayType!="text"&&define.displayType!="fileUpload")||(define.validateRule!=null&&define.validateRule.indexOf("required")!=-1)){
			copyTemplate.find('.label').html("<font style='color:red'>＊</font>"+attrName);
		}else{
			copyTemplate.find('.label').html(attrName);
		}
//		var endTr = window.find('.content tr:eq(-2)');
//		endTr.after(copyTemplate);
		var endTr = window.find('.autoTrs');
		endTr.append(copyTemplate);
		var defaultStyle = {placeHolder: "", minLength: 1, height: 23, width: 250, theme:currentTheme};
		var defaultDateStyle = { height:23, width: 200,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd'};
		var component = null;
		if(displayType === 'text') {
			component = $('<input type="text" />');
			copyTemplate.find('.value').append(component);
			var specConfig = $.extend({}, defaultStyle, displayStyle);
			component.jqxInput(specConfig);
			if(description!=null&&description!=""){
				var copyDescTemplate = trTemplate.clone();
				copyDescTemplate.show();
				copyDescTemplate.find('.value').html("<p style='margin:0px;margin-top:7px;font-size:12px;color:green'>※"+description+"</p>");
				endTr.append(copyDescTemplate);
			}
		} else if(displayType === 'date') {
			component = $('<div></div>');
			copyTemplate.find('.value').append(component);
			var specConfig = $.extend({}, defaultDateStyle, displayStyle);
			component.jqxDateTimeInput(specConfig);
			if(description!=null&&description!=""){
				var copyDescTemplate = trTemplate.clone();
				copyDescTemplate.show();
				copyDescTemplate.find('.value').html("<p style='margin:0px;margin-top:7px;font-size:12px;color:green'>※"+description+"</p>");
				endTr.append(copyDescTemplate);
			}
		}  else if(displayType === 'textarea') {
			component = $('<textarea/>');
			copyTemplate.find('.value').append(component);
			var specConfig = $.extend({}, defaultStyle, displayStyle);
			component.jqxInput(specConfig);
			if(description!=null&&description!=""){
				var copyDescTemplate = trTemplate.clone();
				copyDescTemplate.show();
				copyDescTemplate.find('.value').html("<p style='margin:0px;margin-top:7px;font-size:12px;color:green'>※"+description+"</p>");
				endTr.append(copyDescTemplate);
			}
		} else if(displayType === 'dropDownList') {
			component = $('<div></div>');
			copyTemplate.find('.value').append(component);
			Core.AjaxRequest({
				url :ws_url + define['valueDomain'], 
				type:"get",
				async:false,
				callback : function (data) {
					var source ={
			             datatype: "json",
			             datafields: [
			                 { name: 'codeValue' },
			                 { name: 'codeDisplay' }
			             ],
			             localdata:data
			         };
					 var versiondata = new $.jqx.dataAdapter(source);
					 component.jqxDropDownList({
		                   source: versiondata,
		                   selectedIndex: 0,
			       		   displayMember: "codeDisplay", 
			       			valueMember: "codeValue",
			       			width: 200,
			       			height: 25,
			       			autoDropDownHeight : true,
			       			theme:"metro"
		             });
				}
			});
			if(description!=null&&description!=""){
				var copyDescTemplate = trTemplate.clone();
				copyDescTemplate.show();
				copyDescTemplate.find('.value').html("<p style='margin:0px;margin-top:7px;font-size:12px;color:green'>※"+description+"</p>");
				endTr.append(copyDescTemplate);
			}
		} else if(displayType === 'dropDownTree') {
			defaultStyle = {height: 150, width: 250, theme:currentTheme};
			component = $('<input type="hidden"/>');
			var tree = $('<div/>');
			var parentDiv = $('<div />');
			parentDiv.attr('id', "dropbutton-" + attrKey);
			tree.attr('id', "tree-" + attrKey);
			parentDiv.append(tree);
			var valueTd = copyTemplate.find('.value');
			valueTd.append(parentDiv);
			valueTd.append(component);

			parentDiv.jqxDropDownButton({
				width: 250, 
				height: 22,
				dropDownWidth : 250,
				dropDownHeight : 150,
				theme:currentTheme
			});

			var specConfig = $.extend({}, defaultStyle, displayStyle);
			tree.jqxTree(specConfig);
			define.loadData(tree);

			tree.on('select', function (event) {
				var args = event.args;
				var item = $(this).jqxTree('getItem', args.element);
				var dropDownContent = '<div style="position: relative; margin-left: 0px; margin-top: 5px;">' + item.label + '</div>';
				parentDiv.jqxDropDownButton('setContent', dropDownContent);
				parentDiv.jqxDropDownButton('close');
				component.val(item.value);
			});
			
			if(description!=null&&description!=""){
				var copyDescTemplate = trTemplate.clone();
				copyDescTemplate.show();
				copyDescTemplate.find('.value').html(description);
				endTr.append(copyDescTemplate);
			}
		}else if(displayType = "fileUpload"){
			if(oper.indexOf("edit")!=-1){
				component = $('<div></div>');
				var uploadDiv = '<table class="main">'+
									'<tr>'+
										'<td align="right" width="355" colspan="2">'+
											'<input type="button" name="addUpload" onclick="addUploadInput(this)" value="增加上传" />&nbsp;&nbsp;&nbsp;'+
											'<input style="margin-right: 5px;" onclick="submitUploadFile(this)" type="button" name="importFile" value="上传" />'+
										'</td>'+
									'</tr>'+
									'<tbody name="uploadedDiv"></tbody>'+
									'<tbody name="uploadInputDiv"></tbody>'+
								'</table>';
				component.append(uploadDiv);
				copyTemplate.find('.value').append(component);
				$("input[name='addUpload']").jqxButton({ width: '70px',height:'25px',theme:currentTheme});
				$("input[name='importFile']").jqxButton({ width: '70px',height:'25px',theme:currentTheme});
			}else{
				component = $('<div></div>');
				var uploadDiv = '<table class="main">'+
									'<tr>'+
										'<td align="right" width="355" colspan="2">'+
											'<input type="button" name="addUpload" onclick="addUploadInput(this)" value="增加上传" />&nbsp;&nbsp;&nbsp;'+
											'<input style="margin-right: 5px;" onclick="submitUploadFile(this)" type="button" name="importFile" value="上传" />'+
										'</td>'+
									'</tr>'+
									'<tbody name="uploadInputDiv"></tbody>'+
								'</table>';
				
				component.append(uploadDiv);
				copyTemplate.find('.value').append(component);
				$("input[name='addUpload']").jqxButton({ width: '70px',height:'25px',theme:currentTheme});
				$("input[name='importFile']").jqxButton({ width: '70px',height:'25px',theme:currentTheme});
			}
			if(description!=null&&description!=""){
				var copyDescTemplate = trTemplate.clone();
				copyDescTemplate.show();
				copyDescTemplate.find('.value').html(description);
				endTr.append(copyDescTemplate);
			}
		}
		if(component) {
			component.attr('id', oper+ "-" + attrKey);
		}
	};
	
	//弹出新增管理对象窗口
	this.popAddMgtGroupWindow = function(){
		var windowW = $(window).width(); 
	  	var windowH = $(window).height(); 
	  	$("#add-mgtObjName").val("");
		$("#add-mgtObjDesc").val("");
	  	$("#addGroupWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-260)/2 } });
	  	$("#addGroupWindow").jqxWindow('open');
	};

	//弹出新增管理对象窗口
	this.popAddBizWindow = function(){
		if($("#addMgtObj").length > 0){$("#addMgtObj").remove();}
		me.popBaseMgtObjField('add', me.saveMgtObj);
		$('#add-main-parentName').val(mgtObj['name']);
		//根据立项与否，决定编号是否自动生成
		if($("#add-ext-projectType").length>0){
			if($("#add-ext-projectId").length>0){
				$("#add-ext-projectType").on('select', function () {
	   				var type = $(this).val();
	   				if(type == "01") {
	   					//已立项的项目，编号由用户输入
	   					$("#add-ext-projectId").val("");
	   					$("#add-ext-projectId").attr("disabled",false);
	   				} else if(type == "02") {
	   					//未立项的项目，编号自动生成
	   					$("#add-ext-projectId").val("UP"+new Date().format("yyyyMMddhhmmssS"));
	   					$("#add-ext-projectId").attr("disabled","disabled");
	   				}
	   				
	   			});
			}
		}
	};

	this.saveMgtObj = function () {
		var mgtObjComps = $('#addMgtObj').find('[id^=add-main]');
		var mgtObj = {};
		mgtObjComps.each(function () {
			var elem = $(this);
			if(elem) {
				var id = elem.attr('id');
				var key = id.replace('add-main-', '');
				mgtObj[key] = elem.val();
			}
		});
		if(mgtObjSid==null||mgtObjSid==""){
			mgtObj['parentId']="-999";
		}else{
			mgtObj['parentId'] = mgtObjSid;
		}
		mgtObj['level'] = (level === null ? 0: level + 1);
		var mgtObjExtComps = $('#addMgtObj').find('[id^=add-ext]');
		var mgtObjExts = [];
		mgtObjExtComps.each(function (){
			var elem = $(this);
			if(elem) {
				var mgtObjExt = {};
				var id = elem.attr('id');
				var key = id.replace('add-ext-', '');
				if("projectFiles" == key){
					var fileNames = "";
					var files = $("form[name='upload']").find("input[class='uploadFile']");
					for(var i=0;i<files.length;i++){
						if("1" == $(files[i]).attr("flag")){
							var fileSid = $(files[i]).attr("uploadFileSid");
							if(fileSid !=""&& fileSid!=null){
								fileNames = fileNames + fileSid +",";
							}
						}
					}
					if(fileNames !=""&& fileNames!=null){
						fileNames = fileNames.substring(0, fileNames.length-1);
					}
					mgtObjExt['attrKey'] = key;
					mgtObjExt['attrValue'] = fileNames;
					mgtObjExts.push(mgtObjExt);
				}else{
					mgtObjExt['attrKey'] = key;
					mgtObjExt['attrValue'] = elem.val();
					mgtObjExts.push(mgtObjExt);
					if(key=="projectName"){
						mgtObj["name"] = elem.val();
					}
				}
			}
		});
		if($('#addMgtObj .projectManagerSid').val()==""||$('#addMgtObj .projectManagerSid').val()==null){
			Core.alert({
				title:"提示",
				message:"请选择项目经理!"
   			});
			return;
		}
		mgtObj['mgtObjExts'] = mgtObjExts;
		mgtObj['userSid'] = $('#addMgtObj .projectManagerSid').val();
		mgtObj['status'] = "02";

		//判断开始时间小于结束时间
		var startDate="";
		var endDate="";
		for(var i=0;i<mgtObjExts.length;i++){
			if(mgtObjExts[i].attrKey=="projectStartDate"){
				startDate = mgtObjExts[i].attrValue;
			}
			if(mgtObjExts[i].attrKey=="projectEndDate"){
				endDate = mgtObjExts[i].attrValue;
			}
		}
		if((startDate<endDate)==false){
			Core.alert({
				title:"提示",
				message:"项目的结束时间必须大于开始时间！"
			});
			return;
		}
		

		/**保存配额信息**/
		var quota = new Array;
		var winCpuQuota = {};
		winCpuQuota['quotaType']="Linux,Windows";
		winCpuQuota['quotaName']="CPU核数";
		winCpuQuota['quotaKey']="cores";
		winCpuQuota['quotaValue']=0;
		var winMemQuota = {};
		winMemQuota['quotaType']="Linux,Windows";
		winMemQuota['quotaName']="内存（GB）";
		winMemQuota['quotaKey']="rams";
		winMemQuota['quotaValue']=0;
		var winStQuota = {};
		winStQuota['quotaType']="Linux,Windows";
		winStQuota['quotaName']="外置存储容量（GB）";
		winStQuota['quotaKey']="storageQuota";
		winStQuota['quotaValue']=0;
		var winNumQuota = {};
		winNumQuota['quotaType']="Linux,Windows";
		winNumQuota['quotaName']="虚拟机个数";
		winNumQuota['quotaKey']="instances";
		winNumQuota['quotaValue']=0;
		
		var aixCpuQuota = {};
		aixCpuQuota['quotaType']="AIX";
		aixCpuQuota['quotaName']="CPU核数";
		aixCpuQuota['quotaKey']="cores";
		aixCpuQuota['quotaValue']=0;
		var aixMemQuota = {};
		aixMemQuota['quotaType']="AIX";
		aixMemQuota['quotaName']="内存（GB）";
		aixMemQuota['quotaKey']="rams";
		aixMemQuota['quotaValue']=0;
		var aixStQuota = {};
		aixStQuota['quotaType']="AIX";
		aixStQuota['quotaName']="外置存储容量";
		aixStQuota['quotaKey']="storageQuota";
		aixStQuota['quotaValue']=0;
		var aixNumQuota = {};
		aixNumQuota['quotaType']="AIX";
		aixNumQuota['quotaName']="虚拟机个数";
		aixNumQuota['quotaKey']="instances";
		aixNumQuota['quotaValue']=0;
		
		quota.push(winCpuQuota);
		quota.push(winMemQuota);
		quota.push(winStQuota);
		quota.push(winNumQuota);
		quota.push(aixCpuQuota);
		quota.push(aixMemQuota);
		quota.push(aixStQuota);
		quota.push(aixNumQuota);
		
		mgtObj['mgtQuotas'] = quota;
		
		Core.AjaxRequest({
				url : ws_url + "/rest/mgtObj/create",
				params :mgtObj,
				callback : function (data) {
					//刷新业务信息树
					$('#jqxgridBiz').jqxGrid('clearselection');
					initMgtObjMenuBtn();
					me.initBizTypeTree();
					me.searchBizTypeInfo();
					$("#addMgtObj").jqxWindow('close');
//				    $("#mgtObjResRelation").jqxButton({disabled: true });
//					$("#bizTypeResBtn").jqxButton({disabled: true });
			    },
			    failure:function(data){
			    	$("#addMgtObj").jqxWindow('close');
			    }
			});
	};

	//弹出编辑业务窗口
	this.popEditBizWindow = function(){
		// 获取datagrid的data
		var rows = $('#jqxgridBiz').jqxGrid('getrows');
		var rowData = null;
		for(var i = 0; i < rows.length; i++) {
			if(rows[i].mgtObjSid == mgtObjSid) {
				rowData = rows[i];
				break;
			}
		}
		$("#edit-mgtObjSid").val(rowData.mgtObjSid);
		if(rowData.level=="0"){
			var windowW = $(window).width(); 
		  	var windowH = $(window).height(); 
		  	$("#edit-mgtObjName").val(rowData.name);
		  	$("#edit-mgtObjDesc").val(rowData.description);
		  	$("#editGroupWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-260)/2 } });
		  	$("#editGroupWindow").jqxWindow('open');
		}else{
			me.popBaseMgtObjField('edit', me.editMgtObj);
			
			mgtObj = rowData;
			//得到项目经理
			Core.AjaxRequest({
				url : ws_url + "/rest/user/findManagerByMgtObjSid/"+rowData.mgtObjSid,
				type:"get",
				async:false,
				callback : function (data) {
					if(data!=null){
						mgtObj.userSid = data.userSid;
						mgtObj.managerName = data.realName;
					}
			    },
			    failure:function(data){
			    }
			});
			
			delete mgtObj['uid'];
			//管理对象主体部分值设置
			var mgtObjComps = $('#editMgtObj').find('[id^=edit-main]');
			mgtObjComps.each(function () {
				var elem = $(this);
				var key = elem.attr('id').replace('edit-main-', '');
				elem.val('');
				elem.val(rowData[key]);
			});
			
			$('#editMgtObj .oldProjectName').val(mgtObj.name);
			for(var e=0;e<mgtObj.mgtObjExts.length;e++){
				if(mgtObj.mgtObjExts[e].attrKey=="projectEName"){
					$('#editMgtObj .oldProjectEName').val(mgtObj.mgtObjExts[e].attrValue);
				}else if(mgtObj.mgtObjExts[e].attrKey=="projectId"){
					$('#editMgtObj .oldProjectId').val(mgtObj.mgtObjExts[e].attrValue);
				}
			}
			$('#editMgtObj .projectManagerSid').val(mgtObj.userSid);
			$('#editMgtObj .projectManager').val(mgtObj.managerName);
			
			var dropDownContent = '<div style="position: relative; margin-left: 0px; margin-top: 5px;"></div>';
			$("#dropbutton-parentId").jqxDropDownButton('setContent', dropDownContent);
			var parentIdTree = $("#tree-parentId");
			var items = parentIdTree.jqxTree('getItems');
			for(var i = 0; i < items.length; i++) {
				if(items[i].value == mgtObj.parentId) {
					var dropDownContent = '<div style="position: relative; margin-left: 0px; margin-top: 5px;">' + items[i].label + '</div>';
					$("#dropbutton-parentId").jqxDropDownButton('setContent', dropDownContent);
				}
			}
	
			//打开窗口时重新出刷新数据
			for(var i = 0; i < editPopField.length; i++) {
				if(editPopField[i].loadData) {
					editPopField[i].loadData(parentIdTree);
				}
			}
	
			var mgtObjExtComps = $('#editMgtObj').find('[id^=edit-ext]');
			mgtObjExtComps.each(function () {
				var elem = $(this);
				elem.val('');
				var key = elem.attr('id').replace('edit-ext-', '');
				if("projectFiles" == key){
					var mgtObjData = {};
					var condition = {};
					var params = {
						condition: condition
				  	};
				  	var editMgtObjSid = $("#edit-mgtObjSid").val();
				  	if(editMgtObjSid!=null&&editMgtObjSid!=""&&editMgtObjSid!="undefined"){
				  		condition['mgtObjSid'] = editMgtObjSid;
				  	}
					Core.AjaxRequest({
						url : ws_url + "/rest/mgtObj/find",
						params :params,
						async:false,
						callback : function (data) {
							mgtObjData = data[0];
					    },
					    failure:function(data){
					    }
					});
					$("tbody[name='uploadedDiv']").html("");
					$("tbody[name='uploadInputDiv']").html("");
					var uploadedTrs = '';
					var names = "";
					var fileSid ="";
					for(var n=0;n<mgtObjData.mgtObjExts.length;n++){
						if(mgtObjData.mgtObjExts[n].attrKey == "projectFiles"){
							names = mgtObjData.mgtObjExts[n].fileName;
							fileSid = mgtObjData.mgtObjExts[n].attrValue;
						}
					}
					var fileNames = new Array;
					if(names!=null&&names.length>0){
						fileNames = names.split(",");
					} 
					var fileSids = fileSid.split(",");
					for(var n=0;n<fileNames.length;n++){
						uploadedTrs = uploadedTrs + "<tr><td style='width: 170px;'><input type='hidden' id='edit-mgtobj-fileSids' />"+
								"<a name='file' style='color: blue;' fileSid='"+fileSids[n]+"' " +
								"onclick='mgtObjFileDownLoad(this)' class='datagrid-link'>"+fileNames[n]+"</a></td>"+
								"<td><a  style='color: blue;' attachId='"+fileSids[n]+"' "+
								"onclick='removeUploadedFile(this)' class='datagrid-link'>删除</a></td></tr>";
					}
					$("tbody[name='uploadedDiv']").append(uploadedTrs);
				}else{
					var mgtObjExt = me.findArray(rowData['mgtObjExts'], 'attrKey', key);
					if(mgtObjExt) {
						elem.val(mgtObjExt['attrValue']);
					}
				}
			});
		}
	};

	this.editMgtObj = function () {
		var mgtObjComps = $('#editMgtObj').find('[id^=edit-main]');
		mgtObjComps.each(function () {
			var elem = $(this);
			if(elem) {
				var id = elem.attr('id');
				var key = id.replace('edit-main-', '');
				mgtObj[key] = elem.val();
			}
		});

		var mgtObjExtComps = $('#editMgtObj').find('[id^=edit-ext]');
		mgtObjExtComps.each(function (){
			var elem = $(this);
			if(elem) {
				var id = elem.attr('id');
				var key = id.replace('edit-ext-', '');
				var mgtObjExt = me.findArray(mgtObj['mgtObjExts'], 'attrKey', key);
				if(mgtObjExt) {
					mgtObjExt['attrValue'] = elem.val();
				} else {
					mgtObj['mgtObjExts'].push({'mgtObjSid': mgtObjSid, 'attrKey': key, 'attrValue': elem.val()});
				}
				if(key=="projectName"){
					mgtObj["name"] = elem.val();
				}
				if("projectFiles" == key){
					var fileNames = "";
					var eles = $("tbody[name='uploadedDiv']").find("a[name='file']");
					for(var i=0;i<eles.length;i++){
						fileNames = fileNames + $(eles[i]).attr("fileSid") +",";
					}
					
					var files = $("form[name='upload']").find("input[class='uploadFile']");
					for(var i=0;i<files.length;i++){
						if("1" == $(files[i]).attr("flag")){
							var fileSid = $(files[i]).attr("uploadFileSid");
							if(fileSid !=""&& fileSid!=null){
								fileNames = fileNames + fileSid +",";
							}
						}
					}
					if(fileNames !=""&& fileNames!=null){
						fileNames = fileNames.substring(0, fileNames.length-1);
					}
					
					mgtObjExt['attrValue'] = fileNames;
				}
			}
		});
		if($('#editMgtObj .projectManagerSid').val()==""||$('#editMgtObj .projectManagerSid').val()==null){
			Core.alert({
				title:"提示",
				message:"请选择项目经理!"
   			});
			return;
		}
		mgtObj['userSid'] = $('#editMgtObj .projectManagerSid').val();

		//判断开始时间小于结束时间
		var startDate="";
		var endDate="";
		for(var i=0;i<mgtObj["mgtObjExts"].length;i++){
			if(mgtObj["mgtObjExts"][i].attrKey=="projectStartDate"){
				startDate = mgtObj["mgtObjExts"][i].attrValue;
			}
			if(mgtObj["mgtObjExts"][i].attrKey=="projectEndDate"){
				endDate = mgtObj["mgtObjExts"][i].attrValue;
			}
		}
		if((startDate<endDate)==false){
			Core.alert({
				title:"提示",
				message:"项目的结束时间必须大于开始时间！"
			});
			return;
		}
		
		Core.AjaxRequest({
				url : ws_url + "/rest/mgtObj/update",
				params :mgtObj,
				callback : function (data) {
					//刷新业务信息树
					//刷新Tree节点显示
 					var tree = $('#jqxTreeBiz');
 			    	var items = tree.jqxTree('getItems');
 			    	var curItem = null;
 			    	var icon = null;
 		            for(var i = 0; i < items.length; i++) {
 		            	if(items[i].value === mgtObjSid) {
 		            		curItem = items[i];
 		            		icon = items[i].icon;
 		            		break;
 		            	}
 		            }
 		            if(curItem) {
 		            	$('#jqxTreeBiz').jqxTree('updateItem', curItem, { label: mgtObj.name ,icon:icon});
 		            }
 		            //移动节点到其他节点下
 		            var parentItem = $('#jqxTreeBiz').jqxTree('getItem',curItem.parentElement);
 		            if(!parentItem || parentItem.value != mgtObj.parentId) {
 		            	var newParentItem = null;
 		            	 for(var i = 0; i < items.length; i++) {
 	 		            	if(items[i].value === parseInt(mgtObj.parentId)) {
 	 		            		newParentItem = items[i];
 	 		            		break;
 	 		            	}
 	 		            }
 		            	if(newParentItem) {
 		            		$('#jqxTreeBiz').jqxTree('addTo', curItem, newParentItem);
 		            		$('#jqxTreeBiz').jqxTree('removeItem',curItem);
 		            		$('#jqxTreeBiz').jqxTree('expandItem', newParentItem);
 		            		var newCurItem = null;
 		            		var items = tree.jqxTree('getItems');
 		            		for(var i = 0; i < items.length; i++) {
 		 		            	if(items[i].value === mgtObjSid) {
 		 		            		newCurItem = items[i];
 		 		            		break;
 		 		            	}
 		 		            }
 		            		if(newCurItem) {
 		            			$('#jqxTreeBiz').jqxTree('selectItem', newCurItem);
 		            		}
 		            	}
 		            }
 		            $('#jqxgridBiz').jqxGrid('clearselection');
 					initMgtObjMenuBtn();
					me.searchBizTypeInfo();
					$("#editMgtObj").jqxWindow('close');
//				    $("#mgtObjResRelation").jqxButton({disabled: true });
//					$("#bizTypeResBtn").jqxButton({disabled: true });
			    },
			    failure:function(data){
			    	$("#editMgtObj").jqxWindow('close');
			    }
			});
	};

	this.findArray = function (array, key, value) {
		for(var i = 0; i < array.length; i++) {
			if(array[i][key] === value) {
				return array[i];
			}
		}
	};

	//弹出设置配额窗口
	this.popSetQuotaWindow = function() {
		//初始化配额变更信息
		quotaChanges = [];
		//设置弹出窗口内容
		var data = mgtObj;

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
	
	//弹出审核项目的窗口
	this.popApproveMgtObjWindow = function(){
		var rowindex = $('#jqxgridBiz').jqxGrid('getselectedrowindex');
  		var data = $('#jqxgridBiz').jqxGrid('getrowdata', rowindex);
        //console.log(JSON.stringify(data.name))
  		$("#mgtObj-Name").html(data.name);
  		$("#mgtObj-parentName").html(data.parentName);
  		$("#approve-mgtObjSid").val(data.mgtObjSid);
  		for(var i=0;i<data.mgtObjExts.length;i++){
  			if(data.mgtObjExts[i].attrKey=="projectStartDate"){
  				$("#mgtObj-startDt").html(data.mgtObjExts[i].attrValue);
  			}else if(data.mgtObjExts[i].attrKey=="projectEndDate"){
  				$("#mgtObj-endDt").html(data.mgtObjExts[i].attrValue);
  			}
  		}
  		var approveMsg = [{name:"通过",value:"02"},{name:"拒绝",value:"03"}];
  		$("#mgtApproveResult").jqxDropDownList({
			width: 170,
            height: 28,
            source: approveMsg,
            selectedIndex: 0,
            displayMember: "name", 
            valueMember: "value",
            autoDropDownHeight :true,
            theme:"metro"
		});
  		$("#mgtObj-Type").html(data.projectTypeName);
		var windowW = $(window).width(); 
	  	var windowH = $(window).height(); 
	  	$("#approveMgtObjWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-260)/2 } });
	  	$("#approveMgtObjWindow").jqxWindow('open');
	};
	
	this.popMgtQuotaWindow = function(){
		$("#winVmSum").jqxNumberInput({height: 22, width: 50, min: 0,decimalDigits:0,spinButtons: true, inputMode: 'simple',theme:currentTheme});
		$("#winCpuSum").jqxNumberInput({height: 22, width: 50, min: 0,decimalDigits:0,spinButtons: true, inputMode: 'simple',theme:currentTheme});
		$("#winMemSum").jqxNumberInput({height: 22, width: 50, min: 0,decimalDigits:0,spinButtons: true, inputMode: 'simple',theme:currentTheme});
		$("#winStSum").jqxNumberInput({height: 22, width: 50, min: 0,decimalDigits:0,spinButtons: true, inputMode: 'simple',theme:currentTheme});
		$("#aixVmSum").jqxNumberInput({height: 22, width: 50, min: 0,decimalDigits:0,spinButtons: true, inputMode: 'simple',theme:currentTheme});
		$("#aixCpuSum").jqxNumberInput({height: 22, width: 50, min: 0,decimalDigits:0,spinButtons: true, inputMode: 'simple',theme:currentTheme});
		$("#aixMemSum").jqxNumberInput({height: 22, width: 50, min: 0,decimalDigits:0,spinButtons: true, inputMode: 'simple',theme:currentTheme});
		$("#aixStSum").jqxNumberInput({height: 22, width: 50, min: 0,decimalDigits:0,spinButtons: true, inputMode: 'simple',theme:currentTheme});
		//获取配额信息
		Core.AjaxRequest({
			url : ws_url + "/rest/mgtObj/quotas/"+mgtObjSid,
			type:'get',
			async:false,
			callback : function (data) {
				if(data!=null&&data.length>0){
					for(var i=0;i<data.length;i++){
						if(data[i].quota=="instances"){
							$("#winVmSum").val(data[i].x86Quota==""?0:data[i].x86Quota);
							$("#aixVmSum").val(data[i].powerQuota==""?0:data[i].powerQuota);

							$("#winVmUsed").html(data[i].x86Used==""?0:data[i].x86Used);
							$("#aixVmUsed").html(data[i].powerUsed==""?0:data[i].powerUsed);

							$("#winVmUnused").html(data[i].x86Unused==""?0:data[i].x86Unused);
							$("#aixVmUnused").html(data[i].powerUnused==""?0:data[i].powerUnused);
						}else if(data[i].quota=="cpu"){
							$("#winCpuSum").val(data[i].x86Quota==""?0:data[i].x86Quota);
							$("#aixCpuSum").val(data[i].powerQuota==""?0:data[i].powerQuota);

							$("#winCpuUsed").html(data[i].x86Used==""?0:data[i].x86Used);
							$("#aixCpuUsed").html(data[i].powerUsed==""?0:data[i].powerUsed);

							$("#winCpuUnused").html(data[i].x86Unused==""?0:data[i].x86Unused);
							$("#aixCpuUnused").html(data[i].powerUnused==""?0:data[i].powerUnused);
						}else if(data[i].quota=="memory"){
							$("#winMemSum").val(data[i].x86Quota==""?0:data[i].x86Quota);
							$("#aixMemSum").val(data[i].powerQuota==""?0:data[i].powerQuota);

							$("#winMemUsed").html(data[i].x86Used==""?0:data[i].x86Used);
							$("#aixMemUsed").html(data[i].powerUsed==""?0:data[i].powerUsed);

							$("#winMemUnused").html(data[i].x86Unused==""?0:data[i].x86Unused);
							$("#aixMemUnused").html(data[i].powerUnused==""?0:data[i].powerUnused);
						}else if(data[i].quota=="storage"){
							$("#winStSum").val(data[i].x86Quota==""?0:data[i].x86Quota);
							$("#aixStSum").val(data[i].powerQuota==""?0:data[i].powerQuota);

							$("#winStUsed").html(data[i].x86Used==""?0:data[i].x86Used);
							$("#aixStUsed").html(data[i].powerUsed==""?0:data[i].powerUsed);

							$("#winStUnused").html(data[i].x86Unused==""?0:data[i].x86Unused);
							$("#aixStUnused").html(data[i].powerUnused==""?0:data[i].powerUnused);
						}
					}
				}
			},
			failure: function (data) {
				
			}
		});
		$("#quota-mgtObjSid").val(mgtObjSid);
		var windowW = $(window).width();
		var windowH = $(window).height();
		$("#mgtQuotaWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-260)/2 } });
		$("#mgtQuotaWindow").jqxWindow('open');
	};
	
	this.popUsersMessageWindow = function(){
		  var windowW = $(window).width(); 
		  var windowH = $(window).height(); 
		  $("#findProjectUsersWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-300)/2 } });
		  $("#findProjectUsersWindow").jqxWindow('open');
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
		if(mgtObjSid) {
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
			$("#bizTypeEditBtn").jqxButton({disabled: false});
			$("#bizTypeDelBtn").jqxButton({disabled: false});
			if(level == -1) {
				$("#mgtGroupEditBtn").hide();
   				$("#mgtGroupDeleteBtn").hide();
				$("#bizTypeAddBtn").hide();
   				$("#bizTypeAddBtn").jqxButton({disabled: true});
   				$("#mgtGroupEditBtn").jqxButton({disabled: true});
   				$("#mgtGroupDeleteBtn").jqxButton({disabled: true});
   				$("#bizTypeEditBtn").hide();
   				$("#bizTypeDelBtn").hide();
   				$("#mtgObjQuotaBtn").hide();
   				$("#usersMessageBtn").hide();
   				//$("#mgtObjApproveBtn").hide();
   				
				//$("#bizTypeResBtn").jqxButton({disabled: true});
				$("#mtgObjQuotaBtn").jqxButton({disabled: true});
				$("#usersMessageBtn").jqxButton({disabled: true});
			} else if(level == 0) {
				if(mgtObjSid=="-999"){
					$("#mgtGroupEditBtn").hide();
	   				$("#mgtGroupDeleteBtn").hide();
				}else{
					$("#mgtGroupEditBtn").show();
	   				$("#mgtGroupDeleteBtn").show();
				}
				$("#bizTypeAddBtn").show();
   				$("#bizTypeAddBtn").jqxButton({disabled: false});
   				$("#mgtGroupEditBtn").jqxButton({disabled: false});
   				$("#mgtGroupDeleteBtn").jqxButton({disabled: false});
   				$("#bizTypeEditBtn").hide();
   				$("#bizTypeDelBtn").hide();
   				$("#mtgObjQuotaBtn").hide();
   				$("#usersMessageBtn").hide();
   				//$("#mgtObjApproveBtn").hide();
   				
				$("#bizTypeAddBtn").jqxButton({disabled: false});
				//$("#bizTypeQuotaBtn").jqxButton({disabled: true});
//				$("#bizTypeResBtn").jqxButton({disabled: false});
//				$("#mgtObjResRelation").jqxButton({disabled: true});
				$("#mtgObjQuotaBtn").jqxButton({disabled: true});
				$("#usersMessageBtn").jqxButton({disabled: true});
//				$("#customNetworkBtn").jqxButton({disabled: true});
//				$("#mgtObjResBtn").jqxButton({disabled: true});
			} else {
				
				$("#bizTypeAddBtn").hide();
   				$("#mgtGroupEditBtn").hide();
   				$("#mgtGroupDeleteBtn").hide();
   				
   				$("#bizTypeEditBtn").show();
   				$("#bizTypeDelBtn").show();
   				$("#mtgObjQuotaBtn").show();
   				$("#usersMessageBtn").show();
//   				$("#mgtObjApproveBtn").show();
   				
   				$("#bizTypeEditBtn").jqxButton({disabled: false});
   				$("#bizTypeDelBtn").jqxButton({disabled: false});
   				
   				//if(mgtObj.status=="01"){
   				//	$("#mgtObjApproveBtn").show();
   	   			//	$("#mgtObjApproveBtn").jqxButton({disabled: false});
   	   			//}else{
   	   			//	$("#mgtObjApproveBtn").hide();
	   			//	$("#mgtObjApproveBtn").jqxButton({disabled: true});
   	   			//}
				
				$("#bizTypeAddBtn").jqxButton({disabled: true});
				//$("#bizTypeQuotaBtn").jqxButton({disabled: false});
				$("#mtgObjQuotaBtn").jqxButton({disabled: false});
				$("#usersMessageBtn").jqxButton({disabled: false});
				//$("#bizTypeResBtn").jqxButton({disabled: false});
//				$("#mgtObjResRelation").jqxButton({disabled: false});
//				$("#customNetworkBtn").jqxButton({disabled: false});
//				$("#mgtObjResBtn").jqxButton({disabled: false});
			}

//			if(level == 0) {
//				$("#jqxExpanderResBiz .resoure").hide();
//				//$("#jqxTreeCompute").jqxTree('clear');
////				$("#jqxTreeNetwork").jqxTree('clear');
////				$("#jqxTreeStorage").jqxTree('clear');
//
//				//初始化资源分区Tree
//				$("#jqxExpanderResBiz .resoure").removeClass("show");
//				$("#jqxExpanderResBiz .resoure:eq(0)").addClass("show");
//				$("#jqxExpanderResBiz .resoure:eq(0)").show();
//				$("#jqxExpanderResBiz .resoure:gt(0)").hide();
//				$("#jqxExpanderResBiz .content:eq(0)").show();
//				$("#jqxExpanderResBiz .content:gt(0)").hide();
//				var treeItems = $('#jqxTreeRz').jqxTree('getItems');
//				if(!treeItems || treeItems.length == 0) {
//					me.initRecourceZoneTree();
//				} else {
//					if(mgtObj['flushSelect'] === false) {
//						me.initRecourceZoneTree(mgtObjSid);
//					} else if(mgtObj['flushSelect'] === true){
//						me.flushRecourceZoneTreeSelect();
//					}
//				}
//			} else if(level > 0) {
//				//设置资源拓扑Tab显示, 默认显示计算资源Tab
////				$("#jqxExpanderResBiz .content:gt(0)").show();
//				$("#jqxExpanderResBiz .resoure").removeClass("show");
//				$("#jqxExpanderResBiz .resoure:eq(1)").addClass("show");
//				$("#jqxExpanderResBiz .resoure:eq(1)").show();
//				$("#jqxExpanderResBiz .resoure:eq(0)").hide();
//				$("#jqxExpanderResBiz .resoure:eq(2)").hide();
//				$("#jqxExpanderResBiz .content:eq(1)").show();
//				$("#jqxExpanderResBiz .content:eq(0)").hide();
////				$("#jqxExpanderResBiz .content:eq(2)").hide();
//
//				//初始化计算资源和网络资源Tree
//				//var computeTreeItems = $('#jqxTreeCompute').jqxTree('getItems');
//				//if(!computeTreeItems || computeTreeItems.length == 0) {
//				//	me.initComputeResourceTree();
//				//} else {
//				//	me.flushComputeResourceTreeSelect();
//				//}
////				var netTreeItems = $('#jqxTreeNetwork').jqxTree('getItems');
////				if(!netTreeItems || netTreeItems.length == 0) {
////					me.initNetworkResourceTree();
////				} else {
////					me.flushNetworkResourceTreeSelect();
////				}
//			}
		}

		/*
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
				$("#mgtObjResRelation").jqxButton({disabled: true});
			} else {
				$("#bizTypeQuotaBtn").jqxButton({disabled: false});
				$("#mgtObjResRelation").jqxButton({disabled: false});
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

				//me.initNetworkResourceTree();

			}
		}
		*/
	};

	//绑定页面处理事件
	this.initBindPageEvent = function () {
		//业务树点击事件处理
		$('#jqxTreeBiz').on('select',function(event) {
			me.mgtObjCondition['level']=null;
			var args = event.args;
			var item = $('#jqxTreeBiz').jqxTree('getItem', args.element);
			$('#jqxgridBiz').jqxGrid('clearSelection');
//			$('#jqxTreeNetwork').jqxTree('uncheckAll');
//			$('#jqxTreeStorage').jqxTree('uncheckAll');
//			if(item.value === mgtObjSid) {
//				return;
//			}
			mgtObjSid = item.value;
			Core.AjaxRequest({
				url : ws_url + "/rest/mgtObj/load/" + mgtObjSid,
				type:'post',
				async: false,
				params: {},
				callback : function (data) {
					if(!data) return;
					mgtObj['mgtObjSid'] = data.mgtObjSid;
					mgtObj['name'] = data.name;
					mgtObj['level'] = data.level;
					mgtObj['status'] = data.status;
					mgtObj['parentId'] = data.parentId;
					mgtObjSid = data.mgtObjSid;
					level = data.level;
				}
			});
			me.selectBizDataLoad();
			me.initBizTypeDatagrid();
			
		});

		//业务Grid选择事件处理
		$("#jqxgridBiz").on('rowselect', function (event) {
			var args = event.args; 
			var index = args.rowindex;
			$('#jqxTreeBiz').jqxTree('selectItem', null);
//			$('#jqxTreeNetwork').jqxTree('uncheckAll');
			if(index >= 0){
				var rowData = $('#jqxgridBiz').jqxGrid('getrowdata', index);
				if(rowData.mgtObjSid === mgtObjSid) {
					return;
				}
				level = rowData.level;
				mgtObjSid = rowData.mgtObjSid;
				var bizName = rowData.name;
				mgtObj['mgtObjSid'] = mgtObjSid;
				mgtObj['name'] = bizName;
				mgtObj['level'] = level;
				mgtObj['parentId'] = rowData.parentBizSid;
				mgtObj['status'] = rowData.status;
				if(rowData.parentId === mgtObj['parentId']) {
					mgtObj['flushSelect'] = true;
				} else {
					mgtObj['flushSelect'] = false;
				}

			}
			me.selectBizDataLoad();
		});

		$("#jqxExpanderResBiz .resoure").each(function(index){
			$(this).click(index, function(event){
				$("#jqxExpanderResBiz .resoure").removeClass("show");
				$(this).addClass("show");
				$("#jqxExpanderResBiz .content").not(event.data).hide();
				$("#jqxExpanderResBiz .content").eq(event.data).show();
				if(index === 1) {
//					var treeItems = $('#jqxTreeNetwork').jqxTree('getItems');
//					if(!treeItems || treeItems.length == 0) {
//						me.initNetworkResourceTree();
//					} else {
//						var checkedItems = $('#jqxTreeNetwork').jqxTree('getCheckedItems');
//						if(checkedItems && checkedItems.length > 0) {
//							me.setTreeExpend('jqxTreeNetwork', 1);
//							$('#jqxTreeNetwork').jqxTree('expandAll');
//						} else {
//							me.flushNetworkResourceTreeSelect();
//						}
//					}
//				} else if(index === 2) {
//					var treeItems = $('#jqxTreeStorage').jqxTree('getItems');
//					if(!treeItems || treeItems.length == 0) {
//						me.initStorageResourceTree();
//					} else {
//						var checkedItems = $('#jqxTreeStorage').jqxTree('getCheckedItems');
//						if(checkedItems && checkedItems.length > 0) {
////							me.setTreeExpend('jqxTreeStorage', 1);
//							$('#jqxTreeStorage').jqxTree('expandAll');
//						} else {
//							me.flushStorageResourceTreeSelect();
//						}
//					}
				}
				
			});
		});

		$("#mgtGroupAddBtn").on('click', function (event){
			var ok =  $('#mgtGroupAddBtn').jqxButton('disabled');
			if(!ok) { me.popAddMgtGroupWindow();}
		});
		$("#mgtGroupEditBtn").on('click', function (event){
			var ok =  $('#mgtGroupEditBtn').jqxButton('disabled');
			if(!ok) { me.popEditBizWindow();}
		});
		$("#mgtGroupDeleteBtn").on('click', function (event){
			var ok =  $('#mgtGroupDeleteBtn').jqxButton('disabled');
			if(!ok) { me.delMgtObjGroup();}
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
			if(!ok) { me.delMgtObj();}
		});
//		$("#mgtObjResRelation").on('click', function (event) {
//			var ok =  $('#mgtObjResRelation').jqxButton('disabled');
//			if(!ok) { me.initMgtObjResWin();}
//		});
//		$("#bizTypeResBtn").on('click', function (event) {
//			var ok =  $('#bizTypeResBtn').jqxButton('disabled');
//			if(!ok) { me.saveBizResItems();}
//		});
		$("#bizTypeQuotaBtn").on('click', function (event) {
			var ok =  $('#bizTypeQuotaBtn').jqxButton('disabled');
			if(!ok) { me.popSetQuotaWindow(); }
		});
		//$("#mgtObjApproveBtn").on('click', function (event) {
		//	var ok =  $('#mgtObjApproveBtn').jqxButton('disabled');
		//	if(!ok) { me.popApproveMgtObjWindow(); }
		//});
		$("#mtgObjQuotaBtn").on('click', function (event) {
			var ok =  $('#mtgObjQuotaBtn').jqxButton('disabled');
			if(!ok) { me.popMgtQuotaWindow(); }
		});
		$("#usersMessageBtn").on('click', function (event) {
			var ok =  $('#usersMessageBtn').jqxButton('disabled');
			if(!ok) { me.popUsersMessageWindow(); }
		});
//		$("#customNetworkBtn").on('click', function (event) {
//			var ok =  $('#customNetworkBtn').jqxButton('disabled');
//			if(!ok) { me.popCustomNetworkWindow(); }
//		});

		//绑定保存配额提交事件
		$("#saveQuotaInfo").on('click', me.setQuotasSubmit);
		//绑定业务查询事件
		$("#search-mgtObj-button").on('click', me.searchBizTypeInfo);

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

		$("#set-quotaType").jqxDropDownList().on('change', function (event) {
		
			 if(level != null && level === 2) {
				 var quotaKey = $(this).val();
				 var parentBizSid = bizType['parentBizSid'];
				 var bizSid = mgtObjSid;
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

		$('#addGroupForm').jqxValidator({
			rules: [  
		        { input: '#add-mgtObjName', message: '名称必须为1-32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
		        { input: '#add-mgtObjName', message: '该项目分类已存在！', action: 'keyup, blur',
		        	rule: function(input,commit){
						var flag = false;
						var condition = {};
						var params = {
				  			condition: condition
				  	  	};
						condition['name'] = input.val();
						
						Core.AjaxRequest({
							url : ws_url + "/rest/mgtObj/find",
							type : "post",
							params : params,
							showTimeout : false,
							showMsg:false,
							async:false,
							callback : function(data) {
								if(data.length==0){
									flag = true;
								}
							}
						});
				    	return flag;
					}
		        },
		        { input: '#add-mgtObjDesc', message: '描述不能超过128个字符!', action: 'keyup, blur', rule: 'length=0,128' }
	        ]
		});
		$('#editGroupForm').jqxValidator({
			rules: [  
				{ input: '#edit-mgtObjName', message: '名称必须为1-32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
				{ input: '#edit-mgtObjDesc', message: '描述不能超过128个字符!', action: 'keyup, blur', rule: 'length=0,128' }
			]
		});
		
		$('#addGroupForm').on('validationSuccess', function (event) {
			 var mgtObj = Core.parseJSON($("#addGroupForm").serializeJson());
    		 Core.AjaxRequest({
 				url : ws_url + "/rest/mgtObj/createMgtGroup",
 				params :mgtObj,
 				callback : function (data) {
 					me.initBizTypeTree();
//					me.searchBizTypeInfo();
					$("#addGroupWindow").jqxWindow('close');
//				    $("#mgtObjResRelation").jqxButton({disabled: true });
//				    $("#bizTypeResBtn").jqxButton({disabled: true });
 			    },
 			    failure:function(data){
					$("#addGroupWindow").jqxWindow('close');
 			    }
 			});
	     });
		
		$('#editGroupForm').on('validationSuccess', function (event) {
			var mgtObj = Core.parseJSON($("#editGroupForm").serializeJson());
			Core.AjaxRequest({
				url : ws_url + "/rest/mgtObj/update",
				params :mgtObj,
				callback : function (data) {
					me.initBizTypeTree();
					me.searchBizTypeInfo();
					$("#editGroupWindow").jqxWindow('close');
//					$("#mgtObjResRelation").jqxButton({disabled: true });
//					$("#bizTypeResBtn").jqxButton({disabled: true });
				},
				failure:function(data){
					$("#editGroupWindow").jqxWindow('close');
				}
			});
		});
	};

	// 删除管理对象
	this.delMgtObj = function () {
		if(mgtObjSid){
			 Core.confirm({
    			title:"提示",
//    			width:350,
//    			height:150,
//    			message:"确定要删除该管理对象？ <div style='position: absolute;top: 45px;'><input type='checkbox' id='delBaseRes' onchange='getDelResValue(this)' />删除物理资源</div>",
    			message:"确定要删除该项目吗？ ",
    			confirmCallback:function(){
    				Core.AjaxRequest({
    					url : ws_url + "/rest/mgtObj/delete/"+mgtObjSid+"/"+delRes,
    	 				type:"get",
    	 				showMsg: false,
    	 				callback : function (data) {
    	 					Core.alert({
    							title: "提示",
    							message: JSON.parse(data).message,
    							callback:function(){
    								//刷新Tree节点显示
    	    	 					var tree = $('#jqxTreeBiz');
    	    	 			    	var items = tree.jqxTree('getItems');
    	    	 			    	var curItem = null;
    	    	 		            for(var i = 0; i < items.length; i++) {
    	    	 		            	if(items[i].value === mgtObjSid) {
    	    	 		            		curItem = items[i];
    	    	 		            		break;
    	    	 		            	}
    	    	 		            }
    	    	 		            if(curItem) {
    	    	 		            	$('#jqxTreeBiz').jqxTree('removeItem', curItem);
    	    	 		            }
    	    	 		            me.initBizTypeTree();
    	    	 		            me.searchBizTypeInfo();
    	    	 		            //$('#jqxTreeCompute').jqxTree('clear');
//    	    	 		            $('#jqxTreeNetwork').jqxTree('clear');
    	    	 		            mgtObjSid = null;
    	    	 		            level = null;
    	    	 		       		mgtObj = {};
		 						}
    						});
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
	    // 删除管理对象
	    this.delMgtObjGroup = function () {
	    	if(mgtObjSid){
	    		Core.confirm({
	    			title:"提示",
	    			message:"确定要删除该项目分类吗？ ",
	    			confirmCallback:function(){
	    				Core.AjaxRequest({
	    					url : ws_url + "/rest/mgtObj/delete/"+mgtObjSid+"/"+delRes,
	    					type:"get",
	    					showMsg: false,
	    					callback : function (data) {
	    						Core.alert({
	    							title: "提示",
	    							message: data.message,
	    							callback:function(){
	    								//刷新Tree节点显示
	    								var tree = $('#jqxTreeBiz');
	    								var items = tree.jqxTree('getItems');
	    								var curItem = null;
	    								for(var i = 0; i < items.length; i++) {
	    									if(items[i].value === mgtObjSid) {
	    										curItem = items[i];
	    										break;
	    									}
	    								}
	    								if(curItem) {
	    									$('#jqxTreeBiz').jqxTree('removeItem', curItem);
	    								}
	    								me.initBizTypeTree();
	    								me.searchBizTypeInfo();
	    								//$('#jqxTreeCompute').jqxTree('clear');
//	    								$('#jqxTreeNetwork').jqxTree('clear');
	    								mgtObjSid = null;
	    								level = null;
	    								mgtObj = {};
	    							}
	    						});
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
	    //this.initRecourceZoneTree = function () {
	    //	if(!mgtObjSid)  return;
    	//	$('#jqxTreeRz').jqxTree('clear');
	    //	Core.AjaxRequest({
			//	url : ws_url + "/rest/mgtObj/rz",
			//	type:'post',
			//	params: {
			//		'mgtObjSid' : mgtObjSid,
			//		'mode': '0',
			//	},
			//	callback : function (data) {
			//		var treeData = data.treeData;
			//		var checkedData = data.checkedData;
			//		treeData = me.mergeTreeValue(treeData);
	    //            var source ={
	    //                datatype: "json",
	    //                datafields: [
	    //                     { name: 'resTopologySid' },
			//	             { name: 'parentTopologySid' },
			//	             { name: 'resTopologyName' },
			//	             { name: 'resTopologyType' },
			//	             { name: 'checked' },
	    //                ],
	    //                id: 'resTopologySid',
	    //                localdata: treeData
	    //            };
	    //            var dataAdapter = new $.jqx.dataAdapter(source);
	    //            dataAdapter.dataBind();
	    //            var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'resTopologySid', map: 'value'}, { name: 'checked', map: 'checked'}]);
		 //           $('#jqxTreeRz').jqxTree({source: records, height: '99.5%', width: '100%', checkboxes: true, theme : currentTheme, hasThreeStates: true});
		 //           $('#jqxTreeRz').jqxTree('expandItem', $("#jqxTreeRz").jqxTree("getItems")[0]);
		 //           me.setTreeExpend('jqxTreeRz', 1);
		 //           if(checkedData) {
			//            var items = $('#jqxTreeRz').jqxTree('getItems');
			//            for(var i = 0; i < items.length; i++) {
			//            	for(var j = 0; j < checkedData.length; j++) {
			//	            	if(items[i].value == (checkedData[j].resSetSid + "," + checkedData[j].resSetType)) {
			//	            		$('#jqxTreeRz').jqxTree('checkItem', items[i], true);
			//	            		break;
			//	            	}
			//            	}
			//            }
		 //           }
			//	}
			// });
	    //};

	    //刷新资源分区树选择
	    //this.flushRecourceZoneTreeSelect = function () {
	    //	if(!mgtObjSid)  return;
	    //	$('#jqxTreeRz').jqxTree('uncheckAll');
	    //	Core.AjaxRequest({
			//	url : ws_url + "/rest/bizType/rz",
			//	type:'post',
			//	params: {
			//		'bizSid' : mgtObjSid,
			//		'mode': '2',
			//	},
			//	callback : function (data) {
			//		var checkedData = data.checkedData;
			//		if(checkedData) {
			//			me.setTreeExpend('jqxTreeRz', 1);
			//            var items = $('#jqxTreeRz').jqxTree('getItems');
			//            for(var i = 0; i < items.length; i++) {
			//            	for(var j = 0; j < checkedData.length; j++) {
			//	            	if(items[i].value == (checkedData[j].resSetSid + "," + checkedData[j].resSetType)) {
			//	            		$('#jqxTreeRz').jqxTree('checkItem', items[i], true);
			//	            		break;
			//	            	}
			//            	}
			//            }
		 //           }
			//	}
			// });
	    //};
	    
	    //初始化计算资源树
	    //this.initComputeResourceTree = function () {
	    //	if(mgtObjSid) {
	    //		//$('#jqxTreeCompute').jqxTree('clear');
		 //   	Core.AjaxRequest({
			//		url : ws_url + "/rest/mgtObj/compute",
			//		type:'post',
			//		params: {
			//			'mgtObjSid': mgtObjSid,
			//			'parentId':mgtObj.parentId,
			//			'mode': '0',
			//		},
			//		async:false,
			//		callback : function (data) {
			//			var treeData = data.treeData;
			//			var checkedData = data.checkedData;
			//			treeData = me.mergeTreeValue(treeData);
		 //               var source ={
		 //                   datatype: "json",
		 //                   datafields: [
		 //                        { name: 'resTopologySid' },
			//		             { name: 'parentTopologySid' },
			//		             { name: 'resTopologyName' },
			//		             { name: 'resTopologyType' },
			//		             { name: 'checked' },
		 //                   ],
		 //                   id: 'resTopologySid',
		 //                   localdata: treeData
		 //               };
		 //               var dataAdapter = new $.jqx.dataAdapter(source);
		 //               dataAdapter.dataBind();
		 //               var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'resTopologySid', map: 'value'}, { name: 'checked', map: 'checked'}]);
			//            $('#jqxTreeCompute').jqxTree({source: records, height: '99.5%', width: '100%', checkboxes: true, theme : currentTheme, hasThreeStates: true});
			//            $('#jqxTreeCompute').jqxTree('expandAll');
			//            me.setTreeExpend('jqxTreeCompute', 1);
			//            if(checkedData) {
			//	            var items = $('#jqxTreeCompute').jqxTree('getItems');
			//	            for(var i = 0; i < items.length; i++) {
			//	            	for(var j = 0; j < checkedData.length; j++) {
			//		            	if(items[i].value == (checkedData[j].resSetSid + "," + checkedData[j].resSetType)) {
			//		            		$('#jqxTreeCompute').jqxTree('checkItem', items[i], true);
			//		            		break;
			//		            	}
			//	            	}
			//	            }
			//            }
			//		}
			//	 });
	    //	}
	    //};
	    
	   //刷新计算资源树选择
	   // this.flushComputeResourceTreeSelect = function () {
	   // 	if(!mgtObjSid)  return;
	   // 	$('#jqxTreeCompute').jqxTree('uncheckAll');
	   // 	Core.AjaxRequest({
		//		url : ws_url + "/rest/mgtObj/compute",
		//		type:'post',
		//		params: {
		//			'mgtObjSid': mgtObjSid,
		//			'mode': '2',
		//		},
		//		async:false,
		//		callback : function (data) {
		//			var checkedData = data.checkedData;
		//            if(checkedData) {
		//            	me.setTreeExpend('jqxTreeCompute', 1);
		//	            var items = $('#jqxTreeCompute').jqxTree('getItems');
		//	            for(var i = 0; i < items.length; i++) {
		//	            	for(var j = 0; j < checkedData.length; j++) {
		//		            	if(items[i].value == (checkedData[j].resSetSid + "," + checkedData[j].resSetType)) {
		//		            		$('#jqxTreeCompute').jqxTree('checkItem', items[i], true);
		//		            		break;
		//		            	}
		//	            	}
		//	            }
		//            }
		//		}
		//	 });
	   // 	/*$('#jqxTreeCompute').jqxTree('clear');
	   // 	Core.AjaxRequest({
		//		url : ws_url + "/rest/mgtObj/compute",
		//		type:'post',
		//		params: {
		//			'mgtObjSid': mgtObjSid,
		//			'mode': '0',
		//		},
		//		async:false,
		//		callback : function (data) {
		//			var treeData = data.treeData;
		//			treeData = me.mergeTreeValue(treeData);
	   //             var source ={
	   //                 datatype: "json",
	   //                 datafields: [
	   //                      { name: 'resTopologySid' },
		//		             { name: 'parentTopologySid' },
		//		             { name: 'resTopologyName' },
		//		             { name: 'resTopologyType' },
		//		             { name: 'checked' },
	   //                 ],
	   //                 id: 'resTopologySid',
	   //                 localdata: treeData
	   //             };
	   //             var dataAdapter = new $.jqx.dataAdapter(source);
	   //             dataAdapter.dataBind();
	   //             var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'resTopologySid', map: 'value'}, { name: 'checked', map: 'checked'}]);
		//            $('#jqxTreeCompute').jqxTree({source: records, height: '99.5%', width: '100%', checkboxes: true, theme : currentTheme, hasThreeStates: true});
		//            $('#jqxTreeCompute').jqxTree('expandAll');
		//            me.setTreeExpend('jqxTreeCompute', 1);
		//		}
		//	 });*/
	   // };

	    //初始化网络资源Tree
	    this.initNetworkResourceTree = function () {
//	    	if(mgtObjSid) {
//	    		$('#jqxTreeNetwork').jqxTree('clear');
//	    		Core.AjaxRequest({
//	    			url : ws_url + "/rest/mgtObj/network",
//	    			type:'post',
//	    			params: {
//	    				'mgtObjSid': mgtObjSid,
//	    				'mode': '0',
//	    			},
//	    			async: false,
//	    			callback : function (data) {
//	    				var treeData = data.treeData;
//	    				var checkedData = data.checkedData;
//	    				treeData = me.mergeTreeValue(treeData);
//	    				var source ={
//	    						datatype: "json",
//	    						datafields: [
//	    						             { name: 'resTopologySid' },
//	    						             { name: 'parentTopologySid' },
//	    						             { name: 'resTopologyName' },
//	    						             { name: 'resTopologyType' },
//	    						             { name: 'checked' },
//	    						             ],
//	    						             id: 'resTopologySid',
//	    						             localdata: treeData
//	    				};
//	    				var dataAdapter = new $.jqx.dataAdapter(source);
//	    				dataAdapter.dataBind();
//	    				var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'resTopologySid', map: 'value'}, { name: 'checked', map: 'checked'}]);
//	    				
//	    				$('#jqxTreeNetwork').jqxTree({source: records, height: '99.5%', width: '100%', checkboxes: true, theme : currentTheme, hasThreeStates: true});
//	    				$('#jqxTreeNetwork').jqxTree('expandAll');
//	    				me.setTreeExpend('jqxTreeNetwork', 1);
//	    				if(checkedData) {
//	    					var items = $('#jqxTreeNetwork').jqxTree('getItems');
//	    					for(var i = 0; i < items.length; i++) {
//	    						for(var j = 0; j < checkedData.length; j++) {
//	    							if(items[i].value == (checkedData[j].resSetSid + "," + checkedData[j].resSetType)) {
//	    								$('#jqxTreeNetwork').jqxTree('checkItem', items[i], true);
//	    								break;
//	    							}
//	    						}
//	    					}
//	    				}
//	    			}
//	    		});
//	    	}
	    };
	    
	    //刷新网络资源树选择
	    this.flushNetworkResourceTreeSelect = function () {
//	    	if(!mgtObjSid)  return;
//	    	$('#jqxTreeNetwork').jqxTree('uncheckAll');
//	    	Core.AjaxRequest({
//	    		url : ws_url + "/rest/mgtObj/network",
//	    		type:'post',
//	    		params: {
//	    			'mgtObjSid': mgtObjSid,
//	    			'mode': '2',
//	    		},
//	    		async: false,
//	    		callback : function (data) {
//	    			var checkedData = data.checkedData;
//	    			if(checkedData) {
//	    				me.setTreeExpend('jqxTreeNetwork', 1);
//	    				var items = $('#jqxTreeNetwork').jqxTree('getItems');
//	    				for(var i = 0; i < items.length; i++) {
//	    					for(var j = 0; j < checkedData.length; j++) {
//	    						if(items[i].value == (checkedData[j].resSetSid + "," + checkedData[j].resSetType)) {
//	    							$('#jqxTreeNetwork').jqxTree('checkItem', items[i], true);
//	    							break;
//	    						}
//	    					}
//	    				}
//	    			}
//	    		}
//	    	});
	    	/*$('#jqxTreeNetwork').jqxTree('clear');
    		Core.AjaxRequest({
    			url : ws_url + "/rest/mgtObj/network",
    			type:'post',
    			params: {
    				'mgtObjSid': mgtObjSid,
    				'mode': '0',
    			},
    			async: false,
    			callback : function (data) {
    				var treeData = data.treeData;
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
    				
    				$('#jqxTreeNetwork').jqxTree({source: records, height: '99.5%', width: '100%', checkboxes: false, theme : currentTheme, hasThreeStates: false});
    				$('#jqxTreeNetwork').jqxTree('expandAll');
//    				me.setTreeExpend('jqxTreeNetwork', 1);
    			}
    		});*/
	    };

	    //初始化存储资源Tree
	    this.initStorageResourceTree = function () {
	    	if(mgtObjSid) {
	    		$('#jqxTreeStorage').jqxTree('clear');
		    	Core.AjaxRequest({
					url : ws_url + "/rest/mgtObj/storage",
					type:'post',
					params: {
						'mgtObjSid': mgtObjSid,
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
		               
			            $('#jqxTreeStorage').jqxTree({source: records, height: '99.5%', width: '100%', checkboxes: false, theme : currentTheme, hasThreeStates: false});
			            $('#jqxTreeStorage').jqxTree('expandAll');
			            me.setTreeExpend('jqxTreeStorage', 1);
			            if(checkedData) {
				            var items = $('#jqxTreeStorage').jqxTree('getItems');
				            for(var i = 0; i < items.length; i++) {
				            	for(var j = 0; j < checkedData.length; j++) {
					            	if(items[i].value == (checkedData[j].resSetSid + "," + checkedData[j].resSetType)) {
					            		$('#jqxTreeStorage').jqxTree('checkItem', items[i], true);
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
	    this.flushStorageResourceTreeSelect = function () {
	    	if(!mgtObjSid)  return;
	    	$('#jqxTreeStorage').jqxTree('uncheckAll');
	    	Core.AjaxRequest({
				url : ws_url + "/rest/mgtObj/storage",
				type:'post',
				params: {
					'mgtObjSid': mgtObjSid,
					'mode': '2',
				},
				async: false,
				callback : function (data) {
					var checkedData = data.checkedData;
		            if(checkedData) {
		            	me.setTreeExpend('jqxTreeStorage', 1);
			            var items = $('#jqxTreeStorage').jqxTree('getItems');
			            for(var i = 0; i < items.length; i++) {
			            	for(var j = 0; j < checkedData.length; j++) {
				            	if(items[i].value == (checkedData[j].resSetSid + "," + checkedData[j].resSetType)) {
				            		$('#jqxTreeStorage').jqxTree('checkItem', items[i], true);
				            		break;
				            	}
			            	}
			            }
		            }
				}
			 });
	    /*	$('#jqxTreeStorage').jqxTree('clear');
	    	Core.AjaxRequest({
				url : ws_url + "/rest/mgtObj/storage",
				type:'post',
				params: {
					'mgtObjSid': mgtObjSid,
					'mode': '0',
				},
				async: false,
				callback : function (data) {
					var treeData = data.treeData;
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
	               
		            $('#jqxTreeStorage').jqxTree({source: records, height: '99.5%', width: '100%', checkboxes: false, theme : currentTheme, hasThreeStates: false});
		            $('#jqxTreeStorage').jqxTree('expandAll');
//		            me.setTreeExpend('jqxTreeStorage', 1);
				}
			 });*/
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
	    	if(mgtObjSid) {
	    		var bizResArray = [];
	    		var clearFlag = [];
//	    		if(level ==0) {
//	    			var checkedItems = $('#jqxTreeRz').jqxTree('getCheckedItems');
//	    			for(var i = 0; i < checkedItems.length; i++) {
//	    				var type = checkedItems[i].value.split(',')[1];
//	    				var value =  checkedItems[i].value.split(',')[0];
//	    				if(type == "RZ") {
//		    				var bizRes = {};
//		    				bizRes.resSetSid = value;
//		    				bizRes.bizSid = mgtObjSid;
//		    				bizRes.resCategory = 3;
//		    				bizRes.resSetType = type;
//		    				bizResArray.push(bizRes);
//	    				}
//	    			}
//	    			clearFlag.push("3");
//	    		}else if(level > 0) {
//	    			//var checkedComputeItems = $('#jqxTreeCompute').jqxTree('getCheckedItems');
//	    			//for(var i = 0; i < checkedComputeItems.length; i++) {
//	    			//	var type = checkedComputeItems[i].value.split(',')[1];
//	    			//	var value =  checkedComputeItems[i].value.split(',')[0];
//	    			//	if(type == "RES-HOST") {
//	    			//	//if(type == "RES-HOST" || type == "VC") {
//	    			//		/*
//	    			//		if(type == "RES-HOST") {
//	    			//			var parentItem = $("#jqxTreeCompute").jqxTree('getPrevItem', checkedComputeItems[i]);
//	    			//			//判断主机的集群节点是否也选中
//	    			//			var isExist = false;
//	    			//			for(var j = 0; j < checkedComputeItems.length; j++)  {
//	    			//				if(checkedComputeItems[j].value == parentItem.value) {
//	    			//					isExist = true;
//	    			//					break;
//	    			//				}
//	    			//			}
//	    			//			if(isExist) continue;
//	    			//		}
//	    			//		*/
//		    		//		var bizRes = {};
//		    		//		bizRes.resSetSid = value;
//		    		//		bizRes.bizSid = mgtObjSid;
//		    		//		bizRes.resCategory = 0;
//		    		//		bizRes.resSetType = type;
//		    		//		bizResArray.push(bizRes);
//	    			//	}
//	    			//}
////	    			var checkedNetworkItems = $('#jqxTreeNetwork').jqxTree('getCheckedItems');
////	    			if(checkedNetworkItems) {
////		    			for(var i = 0; i < checkedNetworkItems.length; i++) {
////		    				var type = checkedNetworkItems[i].value.split(',')[1];
////		    				var value =  checkedNetworkItems[i].value.split(',')[0];
////		    				if(type == "RES-NETWORK") {
////		    					var parentItem = $('#jqxTreeNetwork').jqxTree('getItem',checkedNetworkItems[i].parentElement);
////		    					var parentType = parentItem.value.split(',')[1];
////			    				var bizRes = {};
////			    				bizRes.resSetSid = value;
////			    				bizRes.bizSid = mgtObjSid;
////			    				if(parentType == 'PNI') {
////			    					bizRes.resCategory = 2;
////			    				} else if(parentType == 'PNE') {
////			    					bizRes.resCategory = 1;
////			    				}
////			    				bizRes.resSetType = type;
////			    				bizResArray.push(bizRes);
////		    				}
////		    			}
////	    			}
////	    			var checkedStorageItems = $('#jqxTreeStorage').jqxTree('getCheckedItems');
////	    			if(checkedStorageItems) {
////		    			for(var i = 0; i < checkedStorageItems.length; i++) {
////		    				var type = checkedStorageItems[i].value.split(',')[1];
////		    				var value =  checkedStorageItems[i].value.split(',')[0];
////		    				if(type == "RES-STORAGE") {
////			    				var bizRes = {};
////			    				bizRes.resSetSid = value;
////			    				bizRes.bizSid = mgtObjSid;
////			    				bizRes.resCategory = 4;
////			    				bizRes.resSetType = type;
////			    				bizResArray.push(bizRes);
////		    				}
////		    			}
////	    			}
////	    			var computeTreeItems = $('#jqxTreeCompute').jqxTree('getItems');
////	    			var networkTreeItems = $('#jqxTreeNetwork').jqxTree('getItems');
////	    			var storageTreeItems = $('#jqxTreeStorage').jqxTree('getItems');
//	    			if(computeTreeItems && computeTreeItems.length > 0) {
//	    				clearFlag.push("0");
//	    			}
////	    			if(networkTreeItems && networkTreeItems.length > 0) {
////	    				clearFlag.push("1");
////	    				clearFlag.push("2");
////	    			}
////	    			if(storageTreeItems && storageTreeItems.length > 0) {
////	    				clearFlag.push("4");
////	    			}
//	    		}
	    		Core.confirm({
					title:"提示",
					message:"确定要关联资源该资源数据吗？",
					confirmCallback:function(){
						Core.AjaxRequest({
							url : ws_url + "/rest/mgtObj/relateTreeRes",
							params :{
								'bizReses': bizResArray,
								'bizSid': mgtObjSid,
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
	  	  var nameLike = $("#search-mgtobj-name").val();
	  	  var mgtObjType = $("#search-mgtobj-type").val();
	  	  var mgtObjManager = $("#search-mgtobj-manager").val();
	  	  //var startDateStart = $("#search-mgtobj-startDate-start").val();
	  	  //var startDateEnd = $("#search-mgtobj-startDate-end").val();
	  	  //var endDateStart = $("#search-mgtobj-endDate-start").val();
	  	  //var endDateEnd = $("#search-mgtobj-endDate-end").val();
	  	  me.mgtObjCondition['level']=1;
	  	  if(nameLike && $.trim(nameLike) != '') { 
	  		  me.mgtObjCondition['nameLike'] = nameLike;
		  }else{
			  me.mgtObjCondition['nameLike'] = null;
		  }
//	  	  if(mgtObjSid && $.trim(mgtObjSid) != '') {
//	  		  mgtObjCondition['mgtObjSid'] = mgtObjSid;
//	  	  }

		  if(mgtObjType && $.trim(mgtObjType) != ''&& mgtObjType!="全部") {
			  me.mgtObjCondition['projectType'] = mgtObjType;
		  }else{
			  me.mgtObjCondition['projectType'] = null;
		  }

		  //if(startDateStart && $.trim(startDateStart) != '') {
			//  me.mgtObjCondition['projectStartDateStart'] = startDateStart;
		  //}else{
			//  me.mgtObjCondition['projectStartDateStart'] = null;
		  //}
          //
		  //if(startDateEnd && $.trim(startDateEnd) != '') {
			//  me.mgtObjCondition['projectStartDateEnd'] = startDateEnd;
		  //}else{
			//  me.mgtObjCondition['projectStartDateEnd'] = null;
		  //}
          //
		  //if(endDateStart && $.trim(endDateStart) != '') {
			//  me.mgtObjCondition['projectEndDateStart'] = endDateStart;
		  //}else{
			//  me.mgtObjCondition['projectEndDateStart'] = null;
		  //}
          //
		  //if(endDateEnd && $.trim(endDateEnd) != '') {
			//  me.mgtObjCondition['projectEndDateEnd'] = endDateEnd;
		  //}else{
			//  me.mgtObjCondition['projectEndDateEnd'] = null;
		  //}
		  
		  if(mgtObjManager && $.trim(mgtObjManager) != '') {
			  me.mgtObjCondition['managerAccount'] = mgtObjManager;
		  }else{
			  me.mgtObjCondition['managerAccount'] = null;
		  }
	  	  // 当前tree选中的items
	  	  Core.AjaxRequest({
	  			url : ws_url + "/rest/mgtObj/find",
	  			params : me.mgtObjParams,
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

	    };
	    
	  // 弹出自定义网络window
	  this.popCustomNetworkWindow = function(){
		     // 查询出当前管理对象
		  	 var rowindex = $('#jqxgridBiz').jqxGrid('getselectedrowindex');
		  	 var isok = $('#customNetworkBtn').jqxButton('disabled');
			 if(rowindex >= 0 && !isok){
		   			var data = $('#jqxgridBiz').jqxGrid('getrowdata', rowindex);
		   			var mgtObj  = new mgtObjCustomNetworkModel();
		   			mgtObj.searchInfo(data.mgtObjSid);
		   			
		   			var windowW = $(window).width(); 
		 			var windowH = $(window).height(); 
		 			$("#customNetworkWindow").jqxWindow({position: { x: (windowW-800)/2, y: (windowH-420)/2 } });
		 			$("#customNetworkWindow").jqxWindow('open');
			  }	   
		   
	  };
	  
	  //弹出关联资源的窗口
//	  this.initMgtObjResWin = function(){
//		  var rowindex = $('#jqxgridBiz').jqxGrid('getselectedrowindex');
//	  	  var isok = $('#mgtObjResRelation').jqxButton('disabled');
//		  if(rowindex >= 0 && !isok){
//	   			var data = $('#jqxgridBiz').jqxGrid('getrowdata', rowindex);
//	   			$("#opMgtObjSid").val(data.mgtObjSid);
//	   			var mgtObjRes = new mgtObjResModel();
//	   			mgtObjRes.initGridRowUnchecked();
//	   			mgtObjRes.searchMgtObjResInfo();
//	   			var windowW = $(window).width(); 
//	 			var windowH = $(window).height(); 
////	 			$("#mgtObjResWin").jqxWindow({position: { x: (windowW-800)/2, y: (windowH-420)/2 } });
//	 			$("#mgtObjResWin").jqxWindow('open');
//		  }	 
//	  };
};

function getDelResValue(obj){
	delRes = obj.checked;
}


function submitAddGroup(){
	$('#addGroupForm').jqxValidator('validate');
}

function submitEditGroup(){
	$('#editGroupForm').jqxValidator('validate');
}

function saveMgtObjApprove(){
	var mgtModel = new MgtObjModel();
	var mgtObj = {};
	mgtObj["mgtObjSid"]=$("#approve-mgtObjSid").val();
	mgtObj["status"]=$("#mgtApproveResult").val();
	Core.AjaxRequest({
		url : ws_url + "/rest/mgtObj/approveMgtObj",
		params :mgtObj,
		callback : function (data) {
			//刷新业务信息树
			$('#jqxgridBiz').jqxGrid('clearselection');
			initMgtObjMenuBtn();
			mgtModel.initBizTypeTree();
			mgtModel.searchBizTypeInfo();
			$("#approveMgtObjWindow").jqxWindow('close');
//		    $("#mgtObjResRelation").jqxButton({disabled: true });
//			$("#bizTypeResBtn").jqxButton({disabled: true });
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
		    			window.parent.$("#menuContent").find("b:contains('项目管理')").parents("li").addClass("liShow");
		    		} 
		    	});
		    }
	    },
	    failure:function(data){
	    	$("#approveMgtObjWindow").jqxWindow('close');
	    }
	});
}

function saveMgtQuota(){
	var mgtModel = new MgtObjModel();
	var mgtObj = {};
	mgtObj['mgtObjSid']=$("#quota-mgtObjSid").val();
	/**保存配额信息**/
	var quota = new Array;
	var winCpuQuota = {};
	winCpuQuota['quotaType']="Linux,Windows";
	winCpuQuota['quotaName']="CPU核数";
	winCpuQuota['quotaKey']="cores";
	winCpuQuota['quotaValue']=$("#winCpuSum").val();
	var winMemQuota = {};
	winMemQuota['quotaType']="Linux,Windows";
	winMemQuota['quotaName']="内存（GB）";
	winMemQuota['quotaKey']="rams";
	winMemQuota['quotaValue']=$("#winMemSum").val();
	var winStQuota = {};
	winStQuota['quotaType']="Linux,Windows";
	winStQuota['quotaName']="外置存储容量（GB）";
	winStQuota['quotaKey']="storageQuota";
	winStQuota['quotaValue']=$("#winStSum").val();
	var winNumQuota = {};
	winNumQuota['quotaType']="Linux,Windows";
	winNumQuota['quotaName']="虚拟机个数";
	winNumQuota['quotaKey']="instances";
	winNumQuota['quotaValue']=$("#winVmSum").val();
	var aixCpuQuota = {};
	aixCpuQuota['quotaType']="AIX";
	aixCpuQuota['quotaName']="CPU核数";
	aixCpuQuota['quotaKey']="cores";
	aixCpuQuota['quotaValue']=$("#aixCpuSum").val();
	var aixMemQuota = {};
	aixMemQuota['quotaType']="AIX";
	aixMemQuota['quotaName']="内存（GB）";
	aixMemQuota['quotaKey']="rams";
	aixMemQuota['quotaValue']=$("#aixMemSum").val();
	var aixStQuota = {};
	aixStQuota['quotaType']="AIX";
	aixStQuota['quotaName']="外置存储容量";
	aixStQuota['quotaKey']="storageQuota";
	aixStQuota['quotaValue']=$("#aixStSum").val();
	var aixNumQuota = {};
	aixNumQuota['quotaType']="AIX";
	aixNumQuota['quotaName']="虚拟机个数";
	aixNumQuota['quotaKey']="instances";
	aixNumQuota['quotaValue']=$("#aixVmSum").val();
	
	quota.push(winCpuQuota);
	quota.push(winMemQuota);
	quota.push(winStQuota);
	quota.push(winNumQuota);
	quota.push(aixCpuQuota);
	quota.push(aixMemQuota);
	quota.push(aixStQuota);
	quota.push(aixNumQuota);
	
	mgtObj['mgtQuotas'] = quota;
	/**保存完毕**/
	Core.AjaxRequest({
		url : ws_url + "/rest/mgtObj/updateMgtQuota",
		params :mgtObj,
		callback : function (data) {
			//刷新业务信息树
			$('#jqxgridBiz').jqxGrid('clearselection');
//			initMgtObjMenuBtn();
			mgtModel.initBizTypeDatagrid();
			mgtModel.initBizTypeTree();
			mgtModel.searchBizTypeInfo();
			$("#mgtQuotaWindow").jqxWindow('close');
//		    $("#mgtObjResRelation").jqxButton({disabled: true });
//			$("#bizTypeResBtn").jqxButton({disabled: true });
	    },
	    failure:function(data){
	    	$("#mgtQuotaWindow").jqxWindow('close');
	    }
	});
}

function initMgtObjMenuBtn(){
	$("#mgtGroupAddBtn").jqxButton({ width: '100',theme:currentTheme,disabled: false});
	$("#bizTypeAddBtn").jqxButton({ width: '100',theme:currentTheme,disabled: false});
	$("#bizTypeEditBtn").jqxButton({ width: '80',theme:currentTheme,disabled: true});
	//$("#bizTypeQuotaBtn").jqxButton({ width: '80',theme:currentTheme,disabled: true});
	$("#bizTypeDelBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
//	$("#customNetworkBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
//	$("#mgtObjResBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
//	$("#mgtObjApproveBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
	$("#mtgObjQuotaBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
	$("#usersMessageBtn").jqxButton({ width: '100',theme:currentTheme,disabled: true});
}


function getProjectManager(){
	searchManager();
	//判断初始化勾选的行
//	var managerSid = $($(".projectManagerSid")[1]).val();
//	var rows = $("#managerDatagrid").jqxGrid('getrows');
//	if(rows>0){
//		for(var i=0;i<rows.length;i++){
//			if(rows[i].userSid==managerSid){
//				$('#managerDatagrid').jqxGrid({ selectedrowindex: i});
//			}
//		}
//	}
  	$("#chooseManagerWindow").jqxWindow('open');
}

function searchManager(){
	var name = $("#search-manager-name").val();
//	$("#managerDatagrid").jqxGrid("gotopage",0);
	Core.AjaxRequest({
		url : ws_url + "/rest/user/findAll",
		params :{
			realName:name,
			roleSid:205,
			status:1
		},
		callback : function (data) {
			var sourcedatagrid = {
				datatype : "json",
				localdata : data
			};
			var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
			$("#managerDatagrid").jqxGrid({source : dataAdapter});
	    }
	});
}

function saveMgtManager(){
	var rowindex = $('#managerDatagrid').jqxGrid('getselectedrowindexes');
  	if(rowindex.length > 0){
  		if(rowindex.length > 1){
  			Core.alert({
				title:"提示",
				message:"只能关联一个项目经理!"
   			});
  	  	}else{
  	  		var data = $('#managerDatagrid').jqxGrid('getrowdata', rowindex[0]);
  	  		$(".projectManagerSid").val(data.userSid);
  	  		$(".projectManager").val(data.realName);
  	  		$("#chooseManagerWindow").jqxWindow('close');
  	  	}
  	}else{
  		Core.alert({
			title:"提示",
			message:"请选择要关联的项目经理！"
			});
  	}
}


function getFile(obj){
	var file_name = $(obj).val();
	file_name = file_name.substring(file_name.lastIndexOf("\\")+1,file_name.length);
	$($(obj).next()).val(file_name);
}

function addUploadInput(obj){
	var uploadDiv = '<tr><td colspan="2">'+
					'<form id="upload'+fileIndex+'" name="upload" method="post" enctype="multipart/form-data" accept-charset="UTF-8">'+
						'<div class="box" style="margin-top:6px;">'+
					    '<input type="file" class="uploadFile" name="fileAttach'+fileIndex+'" onchange="getFile(this)" />'+
						'<input type="text" name="copyFile'+fileIndex+'" class="textbox" />'+
						'<a href="javascript:void(0);" class="link">浏览</a>&nbsp;&nbsp;'+
						'<a href="javascript:void(0);" style="margin-left: 5px;" class="link" onclick="removeUploadFile(this)">移除</a>'+
					'</div></form>'+
					'</td></tr>';
	$($($(obj).parents("table")[0]).find("tbody[name='uploadInputDiv']")).append(uploadDiv);
	fileIndex++;
}

function submitUploadFile(obj){
	 var fileFlag = true; 
	 var files = $($(obj).parents("table")[0]).find("[class='uploadFile']");
	 for(var i=0;i<files.length;i++){
		 var filename = $(files[i]).val();
		 filename = filename.substring(filename.lastIndexOf("\\")+1,filename.length);
		 if(filename ==""|| filename==null){
			 fileFlag = false;
		 }
	 }
	 if(fileFlag){
		 var uploadForms = $("form[id^='upload']");
		 var uploadFlag = true;
		 for(var i=0;i<uploadForms.length;i++){
			 if("1" != $($(uploadForms[i]).find("input[class='uploadFile']")).attr("flag")){
				 Core.AjaxFormSubmit({
					 formId : $(uploadForms[i]).attr("id"),
					 url : ws_url+"/rest/mgtObj/uploadMgtObjFile",
					 async : false,
					 callback : function(data) {
						 if(null==data){
							 uploadFlag = false;
						 }else{
							 $($(uploadForms[i]).find("input[class='uploadFile']")[0]).attr("uploadFileSid",data[0]);
						 }
					 }
				 });
			 }
		 }
		 if(uploadFlag){
			 $("a[class='link']").remove();
			 $("input[class='uploadFile']").attr("disabled","disabled");
			 $("input[class='uploadFile']").attr("flag","1");
			 $("input[name^='copyFile']").attr("disabled","disabled");
		 }
	 }else{
		Core.alert({
			title:"提示",
			message:"请选择要上传的文件！"
		});
		return;
	 }
 }

function removeUploadFile(obj){
	$($(obj).parents("tr")[0]).remove();
}

function removeUploadedFile(obj){
	var sid = $(obj).attr("attachId");
	var sids = $("#edit-mgtobj-fileSids").val();
	var fileId = sids + sid + ",";
	$("#edit-mgtobj-fileSids").val(fileId);
	$($(obj).parents("tr")[0]).remove();
}

function mgtObjFileDownLoad(obj){
	var fileNameSids = $(obj).attr("fileSid");
	window.location = ws_url + "/rest/common/file/down/"+fileNameSids;
}

function popMgtObjDetailInfoWindow(obj){
	$("#view-mgtObjDetail").html("");
	var detail = "";
	for(var i=0;i<obj.mgtObjExts.length;i++){
		if(obj.mgtObjExts[i].attrKey=="projectFiles"){
			var names = obj.mgtObjExts[i].fileName;
			var fileSid = obj.mgtObjExts[i].attrValue;
			var fileNames = new Array;
			if(names!=null&&names.length>0){
				fileNames = names.split(",");
			}
			var fileSids = fileSid.split(",");
			detail = detail + "<tr><td style='text-align:right;font-size:12px;width:40%;' class='tdBorder'>"+obj.mgtObjExts[i].attrName+"：</td>" +
					"<td style='text-align:left;font-size:12px;' class='tdBorder'>";
			for(var n=0;n<fileNames.length;n++){
				detail = detail + "<a style='color: blue;' fileSid='"+fileSids[n]+"' " +
						"onclick='mgtObjFileDownLoad(this)' class='datagrid-link'>"+fileNames[n]+"</a>";
			}
			detail = detail + "</td></tr>";
		}else{
			detail = detail + "<tr><td style='text-align:right;font-size:12px;width:40%;' class='tdBorder'>"+obj.mgtObjExts[i].attrName+"：</td>" +
					"<td style='text-align:left;font-size:12px;' class='tdBorder'>"+obj.mgtObjExts[i].attrValue+"</td></tr>";
		}
	}
	$("#view-mgtObjDetail").append(detail);
	$("#view-mgtObjMsg").jqxWindow("open");
}

