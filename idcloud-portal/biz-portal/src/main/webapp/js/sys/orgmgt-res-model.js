
var orgresModel = function () {
		var me = this;
	    this.itemsOrg = ko.observableArray();
	    var orgSid;
	    var level;
	 // 定义右击弹出框
	    var contextMenu;
	    var orgGridIndex = -1;
	    
	    // 初始化部门树等
	    this.initOrgTree = function(){
	        
	    	$("#saveAddOrg").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#cancelAddOrg").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#saveEditOrg").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#cancelEditOrg").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        
			$('#jqxTabsOrg').jqxTabs({width : '84.6%', height : '100%', position : 'top', theme : currentTheme});
	    	var source;	    	// 查询出user值
		     Core.AjaxRequest({
				url : ws_url + "/rest/org/findAllOrg",
				params : {},
				type:'post',
				async: false,
				callback : function (data) {
					  
	                var source =
	                {
	                    datatype: "json",
	                    datafields: [
	                        { name: 'orgSid' },
				             { name: 'parentOrgSid' },
				             { name: 'orgText' },
				             { name: 'orgValue' }
	                    ],
	                    id: 'orgSid',
	                    localdata: data
	                };
	                var dataAdapter = new $.jqx.dataAdapter(source);
	                dataAdapter.dataBind();
	                var records = dataAdapter.getRecordsHierarchy('orgSid', 'parentOrgSid', 'items', [{ name: 'orgText', map: 'label'},{ name: 'orgSid', map: 'value'}]);
	
		            $('#jqxTreeOrg').jqxTree({source: records, height: '100%', width: '100%', theme : currentTheme});
		            $('#jqxTreeOrg').jqxTree('expandAll');
		            if(orgSid) {
		            	me.selectTreeNode('jqxTreeOrg', orgSid);
		            } else {
		            	$('#jqxTreeOrg').jqxTree('selectItem', null);
		            }
		            

				}
			 });
		   
            $('#jqxExpanderOrg').jqxExpander({width:'14.9%', height:'100%', theme : currentTheme});
            

   	    };
	    
   	    //初始化业务类型树
   	    this.initBizTypeTree = function () {
   	    	$('#jqxTreeBizType').jqxTree('clear');
   	    	if(!orgSid || !level && level == 0) {
   	    		return;
   	    	}
   	    	Core.AjaxRequest({
				url : ws_url + "/rest/org/biztypes",
				type:'post',
				params: {
					'orgSid' : orgSid
				},
				callback : function (data) {
	                var source ={
	                    datatype: "json",
	                    datafields: [
	                         { name: 'bizSid' },
				             { name: 'parentBizSid' },
				             { name: 'name' },
				             { name: 'level' },
				             { name: 'selected' },
	                    ],
	                    id: 'bizSid',
	                    localdata: data
	                };
	                var dataAdapter = new $.jqx.dataAdapter(source);
	                dataAdapter.dataBind();
	                var records = dataAdapter.getRecordsHierarchy('bizSid', 'parentBizSid', 'items', [{ name: 'name', map: 'label'},{ name: 'bizSid', map: 'value'}, {name: 'level', map:'level'}, { name: 'selected', map: 'checked'}]);
	
		            $('#jqxTreeBizType').jqxTree({
		            	source: records, 
		            	height: '100%', 
		            	width: '100%', 
		            	hasThreeStates : true,
		    			checkboxes : true,
		    			allowDrag : false,
		            	theme : currentTheme
		            });
		            $('#jqxTreeBizType').jqxTree('expandItem', $("#jqxTreeBizType").jqxTree("getItems")[0]);
		           
				}
			 });
		   
   	    };
   	    
	    // 初始化部门内容
	    this.searchOrgContent = function(params){
	    	 var defaultSearch = {
	    		
	         };
	    	
	    	 var obj = $.extend(defaultSearch, params);
	    	 Core.AjaxRequest({
	    		url : ws_url + "/rest/org/findAllOrg",
				params : {},
				type:'post',
	 			async:false,
	 			callback : function (data) {
	 			   me.itemsOrg(data);
	 		       me.initOrgDatagrid(me.itemsOrg);
	 			}
	 		 });
	    }; 
	    
	    // 初始化服务定义的数据源
	    this.initOrgDatagrid = function(initdata){
	    	var sourcedatagrid ={
		              datatype: "json",
		              localdata: initdata
		     };
	    	 
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#jqxgridOrg").jqxGrid(
	          {
	              width: "98%",
	              height: "380px",
				  theme:currentTheme,
	              columnsresize: true,
	              source: dataAdapter,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: false,
	              localization: gridLocalizationObj,
	              selectionmode:"singlerow",
	              columns: [
	                  { text: '序号', datafield: 'orgSid',align: 'center',cellsalign:'center', },
	  	              { text: '部门名称', datafield: 'orgName'},
	                  { text: '父部门名称', datafield: 'parentOrgName'},
	                  { text: '部门描述', datafield: 'orgDesc' },
	                  { text: '创建人', datafield: 'createdBy',align: 'center',cellsalign:'center', }
	              ],
	              showtoolbar: true,
	              rendertoolbar: function (toolbar) {
	                  var me = this;
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var orgAddBtn = $("<div><a id='orgAddBtn' data-bind='click: orgAddItem'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增部门&nbsp;&nbsp;</a></div>");
	                  var orgEditBtn = $("<div><a id='orgEditBtn' data-bind='click: orgEditItem' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>更新部门&nbsp;&nbsp;</a></div>");
	                  var orgDelBtn = $("<div><a id='orgDelBtn' data-bind='click: orgDelItem' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除部门&nbsp;&nbsp;</a></div>");
	                  var orgQuotaBtn = $("<div><a id='orgQuotaBtn' data-bind='click: viewOrgQuota' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text-inv'></i>查看配额情况&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(orgAddBtn);
	                  container.append(orgEditBtn);
	                  container.append(orgDelBtn);
	                  container.append(orgQuotaBtn);
	                  
	    	    	  $("#orgAddBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
	    	    	  $("#orgEditBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
	    	    	  $("#orgDelBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
	       			  $("#orgQuotaBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
	              }
	          });
	          /*
	    	  $("#jqxgridOrg").on('bindingcomplete', function (event) {
			    	$("#jqxgridOrg").jqxGrid('selectrow', 0);
			  });
	    	  */
	    	  $('#jqxgridOrg').on('rowselect',function(event) {
	    		  var args = event.args;
	  			  var index = args.rowindex;
	  			  orgGridIndex = args.rowindex;
	  			  var data = $('#jqxgridOrg').jqxGrid('getrowdata', index);
	  			  orgSid = data.orgSid;
	  			  level = data.level;
	  			  if(orgSid) {
	   	    		var selectOrgItem = $('#jqxTreeOrg').jqxTree('getSelectedItem');
	   	    		if(selectOrgItem){
	   	    			if(selectOrgItem.value === orgSid) {
	   	    				return;
	   	    			} else {
	   	    				me.selectTreeNode('jqxTreeOrg', orgSid);
	   	    			}
	   	    		} else {
	   	    			me.selectTreeNode('jqxTreeOrg', orgSid);
	   	    		}
	   	    	 }
	  			 me.initBizTypeTree();
	    	  });
	    	  
	    	  $('#jqxTreeOrg').on('select',function(event) {
	    			var args = event.args;
	    			var item = $('#jqxTreeOrg').jqxTree('getItem', args.element);
	    			orgSid = item.value;
	    			level = item.level;
	    			if(orgGridIndex >= 0) {
	    				var gridRow = $('#jqxgridOrg').jqxGrid('getrowdata', orgGridIndex);
	    				if(gridRow && gridRow.orgSid == orgSid) {
	    					return;
	    				}
	    			}
	    			me.flushOrgGird();
	    			$("#add-orgSid").val(orgSid);
	 				$("#edit-orgSid").val(orgSid);
					$("#roleSavebtn").jqxButton({disabled: false,theme:currentTheme});
					if(level === 0) {
						$("#orgAddBtn").jqxButton({disabled: false });
					} else {
						$("#orgAddBtn").jqxButton({disabled: true });
					}
					$("#orgEditBtn").jqxButton({disabled: false });
					$("#orgDelBtn").jqxButton({disabled: false });
					$("#orgQuotaBtn").jqxButton({disabled: false });
	    			me.initBizTypeTree();

	    	  });
	    };

	    this.selectTreeNode = function (id, value) {
	    	var tree = $('#' + id);
	    	var items = tree.jqxTree('getItems');
            for(var i = 0; i < items.length; i++) {
            	if(items[i].value === value) {
            		tree.jqxTree('selectItem', items[i]);
            		break;
            	}
            }
	    };

	    // 初始化模板弹出window
	    this.initOrgTempPopWindow = function(){
			$("#addOrgWindow").jqxWindow({
	                width: 400, 
	                height:220,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                theme:currentTheme,
	                cancelButton: $("#cancelAddOrg"), 
	                modalOpacity: 0.3           
	         });
			
			$("#editOrgWindow").jqxWindow({
                width: 400, 
                height:220,
                resizable: false,  
                isModal: true, 
                theme:currentTheme,
                autoOpen: false, 
                cancelButton: $("#cancelEditOrg"), 
                modalOpacity: 0.3           
         });
			
			$("#viewQuotaWindow").jqxWindow({
                width: 400, 
                height:220,
                resizable: false,  
                isModal: true, 
                theme:currentTheme,
                autoOpen: false, 
                cancelButton: $("#cancelViewQuota"), 
                modalOpacity: 0.3           
            });
			
			//初始化配额列表
	    	$("#orgQuotaGrid").jqxGrid({
	             width: "100%",
	             height:"140px",
	             theme:currentTheme,
	             columnsresize: true,
	             pageable: false, 
	             sortable: false,
	             localization: gridLocalizationObj,
	             columns: [
	                 { text: '配额项名称', datafield: 'quotaName'},
	                 { text: '已分配配额', datafield: 'quotaValue'}
	             ]
	         });
	    };
	    
	    // 初始化验证规则
	    this.initValidator = function(){

			$('#addOrgForm').jqxValidator({
		        rules: [  
		                  { input: '#add-orgName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-orgName', message: '部门名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },

		               ]
			});
			
		    	
			// 新增org池验证成功
			$('#addOrgForm').on('validationSuccess', function (event) {
				 var pool = Core.parseJSON($("#addOrgForm").serializeJson());
				 
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/org/create",
		 				params :pool,
		 				callback : function (data) {
		 					// 刷新基本信息
		 					me.initOrgTree();
		 					me.initBizTypeTree();
		 					me.flushOrgGird();
							$("#addOrgWindow").jqxWindow('close');
//	   					    $("#orgResBtn").jqxButton({disabled: true });
	    	 			    $("#orgAddBtn").jqxButton({disabled: true });
		 			    },
		 			    failure:function(data){
							$("#addOrgWindow").jqxWindow('close');
		 			    }
		 			});
		     });
			
			$('#editOrgForm').jqxValidator({
		        rules: [  
		                  { input: '#edit-orgName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
		                  { input: '#edit-orgName', message: '部门名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
		                  
		               ]
			});
		    	
			// 编辑Ip验证成功
			$('#editOrgForm').on('validationSuccess', function (event) {
				 var pool = Core.parseJSON($("#editOrgForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/org/update",
		 				params :pool,
		 				callback : function (data) {
		 					// 刷新基本信息
		 					me.initOrgTree();
		 					me.initBizTypeTree();
		 					me.flushOrgGird();
		 					$("#editOrgWindow").jqxWindow('close');
//		 					$("#orgResBtn").jqxButton({disabled: true });
	    	 			    $("#orgAddBtn").jqxButton({disabled: true });
		 			    },
		 			    failure:function(data){
							$("#editOrgWindow").jqxWindow('close');
		 			    }
		 			});
		     });
	    	
	    };
	    
	    this.flushOrgGird = function () {
	    	$("#jqxgridOrg").jqxGrid('clear');
			$("#jqxgridOrg").jqxGrid('clearselection');
	    	var url = "";
	    	var type =""; 
	    	if(orgSid && level) {
	    		url = ws_url + "/rest/org/"+orgSid;
	    		type = "get";
	    	} else {
	    		url = ws_url + "/rest/org/findAllOrg";
	    		type = "post";
	    	}
	    	Core.AjaxRequest({
				url : url,
				params : {},
				type: type,
				async:false,
				callback : function (orgdata) {
					var sourcedatagrid ={
							datatype: "json",
							localdata: orgdata
					};
					var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
					$("#jqxgridOrg").jqxGrid({source: dataAdapter});
				}
			});
	    };
	    
	    this.addOrgSubmit = function(){
	    	// 判断是否通过了验证
	    	$('#addOrgForm').jqxValidator('validate');
	    };

	    this.editOrgSubmit = function(){
	    	// 判断是否通过了验证
	    	$('#editOrgForm').jqxValidator('validate');
	    };
	    
		// 新增部门
		this.orgAddItem = function() {
			var ok =  $('#orgAddBtn').jqxButton('disabled');
			if(!ok) {
				 $("#add-orgName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
			     $("#add-parentOrgName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme, disabled: true});
			     $("#add-sortRank").jqxNumberInput({ width: 250, height: '22px', inputMode: 'simple',theme:currentTheme, spinButtons: true,decimalDigits:0, min: 1, max: 100, value: 1});
			     $("#add-orgDesc").jqxInput({placeHolder: "", height: 46, width: 250, minLength: 1,theme:currentTheme});
			 	 $("#saveAddOrg").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
				 $("#cancelAddOrg").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });	
			     $("#add-orgName").val("");
			     $("#add-orgDesc").val("");
			     
			     var orgSid = $("#add-orgSid").val();
			     if(orgSid!=""){
			    	 $("tr.hide").show();
			    	 var data = searchOrg(orgSid);
			    	 $("#add-parentOrgName").val(data[0].orgName);
			    	 $("#add-parentOrgSid").val(data[0].orgSid);
			     }else{
			    	 $("tr.hide").hide();
			     }

			 	 // 设置弹出框位置
			 	 var windowW = $(window).width(); 
			 	 var windowH = $(window).height(); 
			 	 $("#addOrgWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-220)/2 } });
			 	 $("#addOrgWindow").jqxWindow('open');
			}	
		};
		
		// 更新
		this.orgEditItem = function() {
		    $("#edit-orgName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
		    $("#edit-parentOrgName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme, disabled: true });
		    $("#edit-sortRank").jqxNumberInput({ width: 250, height: '22px', inputMode: 'simple',theme:currentTheme, spinButtons: true,decimalDigits:0, min: 0, max: 10, value: 0});
		    $("#edit-orgDesc").jqxInput({placeHolder: "", height: 46, width: 250, minLength: 1,theme:currentTheme});
			$("#saveEditOrg").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
			$("#cancelEditOrg").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });	    
			// 将常用的字段可以使用这个方法设置数据
			var ok =  $('#orgEditBtn').jqxButton('disabled');

	    	if(!ok && orgSid){
	    		    // 将常用的字段可以使用这个方法设置数据
	    			setEditOrgForm(orgSid);
	    		   
	    		    var windowW = $(window).width(); 
	    	    	var windowH = $(window).height(); 
	    	    	$("#editOrgWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-220)/2 } });
	    	    	$("#editOrgWindow").jqxWindow('open');
	    	}
		};
     this.viewOrgQuota = function() {
    		var rowindex = $('#jqxgridOrg').jqxGrid('getselectedrowindex');
			var ok =  $('#orgQuotaBtn').jqxButton('disabled');
			$("#cancelViewQuota").jqxButton({width: '60',theme:currentTheme,height: '23px', disabled: false });
	    	if(!ok && orgSid){
	    		    Core.AjaxRequest({
	    				url : ws_url + "/rest/org/findUsageQuota/" + orgSid,
	    				type:'get',
	    				callback : function (data) {
	    					//配额Grid显示
	    			    	 var sourcedatagrid ={
	    				         datatype: "json",
	    				         datafields: [
				      	                 { name: 'quotaKey'},
				      	                 { name: 'quotaName'},
				      	                 { name: 'quotaValue'},
	    				      	  ],
	    				      	 localdata: data
	    				     };
	    			   	    var griddataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	    			        $("#orgQuotaGrid").jqxGrid({source: griddataAdapter});
	    				}
	    		   });
	    		    var windowW = $(window).width(); 
	    	    	var windowH = $(window).height(); 
	    	    	$("#viewQuotaWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-220)/2 } });
	    	    	$("#viewQuotaWindow").jqxWindow('open');
	         };
	  };
		   
	  /** 删除部门 */
	  this.orgDelItem = function () {
		var ok =  $('#orgEditBtn').jqxButton('disabled');

	  	if(!ok && orgSid){
			Core.confirm({
				title:"提示",
				message:"确定要删除该部门吗？",
				confirmCallback:function(){
					Core.AjaxRequest({
						url : ws_url + "/rest/org/delete/"+ orgSid,
		 				type:"get",
		 				callback : function (data) {
		 					orgSid = null;
		 					level = null;
		 					me.initOrgTree();
		 					me.initBizTypeTree();
		 					me.flushOrgGird();
		 			    },
		 			});
				}
			});
	  	}
	};
	 
  };
  
  function setEditOrgForm(orgSid){
	  var data = searchOrg(orgSid);
	  
	  $("#edit-orgSid").val(data[0].orgSid);
	  $("#edit-sortRank").jqxNumberInput('val', data[0].sortRank);
	  $("#edit-orgName").jqxInput({value:data[0].orgName});
	  $("#edit-parentOrgName").jqxInput({value:data[0].parentOrgName});
	  $("#edit-orgDesc").jqxInput({value:data[0].orgDesc});
  };
  // 根据sid查询资源池信息
  function searchOrg(orgSid){
	  var orgData;
	  Core.AjaxRequest({
		  url : ws_url + "/rest/org/"+orgSid+"",
		  type:"get",
		  async:false,
		  callback : function (data) {
			  orgData = data;
		  },
		  failure:function(data){
			  
		  }
	  });
	  return orgData;
  }
  
  function saveOrdBizRel() {
	  	var selectOrgItem = $('#jqxTreeOrg').jqxTree('getSelectedItem');
 		if(!selectOrgItem){
 			return;
 		}
 		var orgSid =  selectOrgItem.value;
		
		var checkBizTypeSid="";
		// 获取选中的业务
		var backItems = $('#jqxTreeBizType').jqxTree('getCheckedItems');
		var checkitems = $('#jqxTreeBizType').jqxTree('getExpandItems');

      	for (var i = 0; i < checkitems.length; i++) {
      		if (checkitems[i].checked == null){
      			var checkitem = checkitems[i];
      			checkBizTypeSid += checkitem.value + ",";
      		}
      	}
      	if(backItems.length != 0){
  			 for(var i=0;i<backItems.length;i++){
  				 var checkitem = backItems[i];
	        		 checkBizTypeSid += checkitem.value + ",";
  			 }
  		}

      	if (checkBizTypeSid == "") {
      		checkBizTypeSid = ",";
      	} 

      	Core.confirm({
				title:"提示",
				message:"确定要关联资源该业务类型数据吗？",
				confirmCallback:function(){
					Core.AjaxRequest({
						url : ws_url + "/rest/org/create/"+ orgSid +"/" + checkBizTypeSid,
						params :{},
			 			type : "POST",

		 				 callback : function (data) {
		 					
	   		 			 },
		 			     failure:function(data){
	   					   
		 			     }
		 			});
				}
      	});
	};
  