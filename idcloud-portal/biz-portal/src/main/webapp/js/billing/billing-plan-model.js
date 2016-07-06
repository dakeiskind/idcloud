var detailsItems = ko.observableArray();
var billingPricingObj = new Object();
var billingPlanModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.itemsSpec = ko.observableArray();
	    this.searchObj = {
	    		billingPlanName: "", 
	    		billingPlanType : "", 
	    		planStatus: ""
		};
	    // 用户数据
	    this.searchBillingPlanInfo = function(options){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/billings/platform/findBillingPlan",
	 			type:'post',
	 			params:me.searchObj,
	 			async:false,
	 			callback : function (data) {
	 				me.items(data);
	 				var sourcedatagrid ={
 			              datatype: "json",
 			              localdata: me.items
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#billingPlanGrid").jqxGrid({source: dataAdapter});
	 				
	 			}
	 		 });
	    };
	    
	    var nestedGrids = new Array();
	    
	    // 初始化资费定价明细grid和添加规格grid
	    this.initBillDetailAndSpecDatagrid = function(){
	    	var initrowdetails = function (index, parentElement, gridElement, record) {
 	            var id = record.billingPricingSid.toString();
 	            var grid = $($(parentElement).children()[0]);
 	            nestedGrids[index] = grid;
 	            var filtergroup = new $.jqx.filter();
 	            var filtervalue = id;
 	            var filtercondition = 'equal';
 	            var filter = filtergroup.createfilter('stringfilter', filtervalue, filtercondition);
 	            var detailsbyid = [];
 	            for (var m = 0; m < detailsItems.length; m++) {
 	                var result = filter.evaluate(detailsItems[m]["billingPricingSid"]);
 	                if (result)
 	                	detailsbyid.push(detailsItems[m]);
 	            }
 	            var detailssource = { 
 	            	datatype: "json",
 	                localdata: detailsbyid
 	            };
 	            var nestedGridAdapter = new $.jqx.dataAdapter(detailssource);
 	            if (grid != null) {
 	            	grid.jqxGrid({
 	        			source: nestedGridAdapter,
 	        			width:"95%",
 	        			height: 116,
 	        			autowidth: false,
 	        			columnsresize: true,
 	        			rowsheight: 30,
 	                    enablehover: false,
						enabletooltips:true,
 	        			theme:currentTheme,
 	        			localization : gridLocalizationObj,
 	        			columns: [
 	        			          { text: '定价明细', datafield: 'value'},
 	        			          //{ text: '单价', datafield: 'unitPrice',width:60},
 	        			          { text: '单位', datafield: 'chargeUnit',width:60},
 	        			          { text: '折扣率', datafield: 'discount',width:60},
// 	        			          { text: '始终按月计算，', datafield: 'isMonth',width:90, cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
// 	        			        	  if(1 == value){
// 	        			        		  return "<div style='width:67px;height:27px;margin-top:4px;margin-left:10px;text-align:center;line-height:25px;'>是</div>";
// 	        			        	  }else{
// 	        			        		  return "<div style='width:67px;height:27px;margin-top:4px;margin-left:10px;text-align:center;line-height:25px;'>否</div>";
// 	        			        	  }
// //	        			        	  return "<div class='customButton' style='margin-top:4px;margin-left:10px;' onclick='deleteBillPricingDetailInfo("+JSON.stringify(td)+")'>删除</div>";
// 	        			             }},
 	        			          { text: '操作', width:80,  datafield: '',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
 	        			        	  var td = grid.jqxGrid('getrowdata', row);
 	        			        	  return "<div class='customButton' style='margin-top:4px;margin-left:10px;' onclick='deleteBillPricingDetailInfo("+JSON.stringify(td)+")'>删除</div>";
 	        			             }
 	        			          }
 	        			   ]
 	        		});
 	            }
             };
             
    	     // 资费定价明细datagrid
    	     $("#billingPriceDetails").jqxGrid({
   	              width: "100%",
   	              height:210,
   				  theme:currentTheme,
   	              columnsresize: true, 
   	              autoheight: false,
   	              autowidth: false,
   	              sortable: true,
   	              editable: false,
   	              rowdetails: true,
				 enabletooltips:true,
   	              rowsheight: 30,
   	              initrowdetails: initrowdetails,
   	              rowdetailstemplate: { rowdetails: "<div id='grid' style='margin-top:5px;'></div>", rowdetailsheight: 140, rowdetailshidden: true },
   	              selectionmode:"single",
   	              localization: gridLocalizationObj,
   	              columns: [
   	                  { text: '计价组合详细',  editable: false,datafield: 'billingConfigDescription'},
   	                  { text: '收费类型', datafield: 'chargeTypeName'},
   	                  { text: '定价类型',  editable: false, datafield: 'billingTypeName'},
   	                  { text: '状态',  editable: false,datafield: 'statusName'},
   	                  { text: '操作', editable: false, width:80,  datafield: '',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
   	                	  return "<div class='customButton' style='margin-top:4px;margin-left:12px;width:50px;height:15px;' onclick='popBillPricingWindow("+row+")'>定价</div>";
   	                  	}
 			          }
   	              ],
   	              showtoolbar: false
	          });
	    };
	  
	    // 初始化用户datagrid的数据源
	    this.initBillingPlanDatagrid = function(){
	          $("#billingPlanGrid").jqxGrid({
	              width: "100%",
				  theme:"metro",
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '资费计划名称', datafield: 'billingPlanName'},
	                  { text: '资费计划类型', datafield: 'billingPlanTypeName'},
	                  { text: '服务名称', datafield: 'serviceName'},
	                  { text: '适用范围', datafield: 'planScope'},
	                  { text: '状态', datafield: 'planStatusName'}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addBtn = $("<div><a id='jqxAddBtn' onclick='addBillingPlanItem()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editBtn = $("<div><a id='jqxEditBtn' onclick='editBillingPlanItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var deleteBtn = $("<div><a id='jqxDeleteBtn' onclick='removeBillingPlanItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
	                  var mgtbilling = $("<div><a id='jqxMgtbillingBtn' onclick='mgtBillingPrice()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-wrench'></i>管理计费定价&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addBtn);	                  
	                  container.append(editBtn);
	                  container.append(deleteBtn);
	                  container.append(mgtbilling);
	              }
	          });
	       // 控制按钮是否可用
	    	  $("#billingPlanGrid").on('rowselect', function (event) {
    			  $("#jqxEditBtn").jqxButton({ disabled: false });
	   			  $("#jqxDeleteBtn").jqxButton({disabled: false});
	   			  $("#jqxMgtbillingBtn").jqxButton({ width: '60',theme:"metro",height: '18px',  disabled: false});
	          });
	          $("#jqxAddBtn").jqxButton({ width: '60',height: '18px', theme:"metro"});
	    	  $("#jqxEditBtn").jqxButton({width: '60',theme:"metro",height: '18px', disabled: true });
   			  $("#jqxDeleteBtn").jqxButton({ width: '60',theme:"metro",height: '18px',  disabled: true});
   			  $("#jqxMgtbillingBtn").jqxButton({ width: '60',theme:"metro",height: '18px',  disabled: true});
	          
	    };
	    
	    // 初始化弹出window
	    this.initPopWindow = function(){
			$("#addBillingPlanWindow").jqxWindow({
	                width: 780,
	                height:430,
	                resizable: true,
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#Cancel"), 
	                theme: currentTheme,
	                modalOpacity: 0.3,
	                initContent:function(){
	                	// 初始化新增用户页面组件
	        	        $("#add-plan-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:"metro"});
	        	        $("#add-plan-scope").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:"metro"});
	        	        $("#Save").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	        $("#Cancel").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	        
	        	        var codeadd = new codeModel({width:150,autoDropDownHeight:true});
		       			 codeadd.getCommonCode("add-plan-type","BILLING_PLAN_TYPE");
		       			 codeadd.getCommonCode("add-plan-status","BILLING_PLAN_STATUS");
		       			 var serviceadd = new codeModel({width:150,autoDropDownHeight : false,
		       				dropDownWidth : 150,
		       				dropDownHeight : 100});
		       			serviceadd.getCustomCode("add-service-name","/services/findServiceDefines","serviceName","serviceSid",false,"post",null);

						var isBillArray = [
							{ value: "true", label: "是" },
							{ value: "false", label: "否" },
							];

						var isBillSource =
						{
							datatype: "array",
							datafields: [
								{ name: 'label', type: 'string' },
								{ name: 'value', type: 'string' }
							],
							localdata: isBillArray
						};
						var isBillAdapter = new $.jqx.dataAdapter(isBillSource, {
							autoBind: true
						});
						$("#billingPalnSpec").jqxGrid({
							width: "100%",
							height: 210,
							theme:currentTheme,
							columnsresize: true,
							autoheight: false,
							autowidth: false,
							sortable: true,
							editable: true,
							enabletooltips: true,
							selectionmode: 'checkbox',
							localization: gridLocalizationObj,
							columns: [
								//{ text: '', datafield: 'isSelected', columntype: 'checkbox', width: 20 },
								{ text: '规格ID', datafield: 'sid',disabled:false},
								{ text: '规格名称', datafield: 'name',editable:false},
								{ text: '规格说明', datafield: 'description',editable:false},
								{ text: '数据类型', datafield: 'dataType',editable:false},
								{ text: '单位',  datafield: 'unit',editable:false},
								{ text: '取值范围',  datafield: 'valueDomain',editable:false},
								{ text: '参与计费',  datafield: 'bill',displayfield: 'billDisplay',columntype: 'dropdownlist',editable:true,
									createeditor: function (row, value, editor) {
										editor.jqxDropDownList({ source: isBillAdapter, displayMember: 'label', valueMember: 'value' });
									}}
							],
							showtoolbar: false
						});
						me.loadBillingPlanSpecData(isBillAdapter);
						$("#add-service-name").on({
							"change": function (event) {
								var args = event.args;
								if (args) {
									me.loadBillingPlanSpecData(isBillAdapter);
								}
							}
						});
					}
	         });

			$("#editBillingPlanWindow").jqxWindow({
                width: 580, 
                height:160,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#editCancel"), 
                theme: currentTheme,
                modalOpacity: 0.3
             });
			
			// 定价明细
			$("#mgtBillingPriceWindow").jqxWindow({
                width: 700, 
                height:400,
                resizable: false, 
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#mgtBillingCancel"), 
                theme: currentTheme,
                modalOpacity: 0.3,
                initContent:function(){
	        	     $("#mgtBillingCancel").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	     var code = new codeModel({width:150,autoDropDownHeight:true});
	        	     code.getCommonCode("billing-type","BILLING_TYPE_YM");
	        	     var code2 = new codeModel({width:150,autoDropDownHeight:true,disabled: true});
	        	     code2.getCommonCode("billingStatus","BILLING_PLAN_STATUS");
                }
			});

			//详细定价规格window
			$("#addPriceDetailWindow").jqxWindow({
				width: 900,
				height:400,
				resizable: true,
				isModal: true,
				autoOpen: false,
				cancelButton: $("#addPriceDetailCancel"),
				theme: currentTheme,
				modalOpacity: 0.3,
				initContent:function(){
					$("#addPriceDetailSave").jqxButton({ width: '50',height:"25",theme:"metro"});
					$("#addPriceDetailCancel").jqxButton({ width: '50',height:"25",theme:"metro"});

					//var cellbeginedit = function (row, datafield, columntype, value) {
					//	var specData = $('#addPriceDetailSpecsGrid').jqxGrid('getrowdata', row);
					//	if(specData.billLabel == "No")
					//		return false;
					//}

					//var cellsrenderer = function (row, column, value, defaultHtml) {
					//	var specData = $('#addPriceDetailSpecsGrid').jqxGrid('getrowdata', row);
					//	if (specData.billLabel == "Yes") {
					//		var element = $(defaultHtml);
					//		element.css('color', '#999');
					//		return element[0].outerHTML;
					//	}
					//	return defaultHtml;
					//}

					var isBillArray = [
						{ value: "01", label: "增量收费" },
						{ value: "02", label: "固定收费" },
						{ value: "00", label: "不收费" },
					];

					var isBillSource =
					{
						datatype: "array",
						datafields: [
							{ name: 'label', type: 'string' },
							{ name: 'value', type: 'string' }
						],
						localdata: isBillArray
					};
					var isBillAdapter = new $.jqx.dataAdapter(isBillSource, {
						autoBind: true
					});

					$("#addPriceDetailSpecsGrid").jqxGrid({
						width: "100%",
						height: 175,
						theme:currentTheme,
						columnsresize: true,
						autoheight: false,
						autowidth: false,
						sortable: true,
						editable: true,
						selectionmode:"checkbox",
						localization: gridLocalizationObj,
						columns: [
							{ text: '规格SID', datafield: 'sid',editable:false,width:60},
							{ text: '规格名称', datafield: 'name',editable:false},
							{ text: '规格说明', datafield: 'description',editable:false},
							{ text: '数据类型', datafield: 'dataType',editable:false},
							{ text: '单位',  datafield: 'unit',editable:false},
							{ text: '取值范围',  datafield: 'valueDomain',editable:false},
							//{ text: '参与计费',  datafield: 'billLabel',editable:false},
							{ text: '计费类型',  datafield: 'billingChargeType',displayfield: 'billDisplay',columntype: 'dropdownlist',editable:true,
								createeditor: function (row, value, editor) {
									editor.jqxDropDownList({ source: isBillAdapter,selectedIndex:0, displayMember: 'label', valueMember: 'value',dropDownHeight : 70,autoDropDownHeight: true });
								}},
							{ text: '单价',  datafield: 'unitPrice',editable:true},
							{ text: '初始单价(增量计费)',  datafield: 'initPrice',editable:true,width:100},
							{ text: '规格值',  datafield: 'moduleValue',editable:true,
								validation: function (cell, value) {
									var rowIndex = cell.row;
									var specData = $('#addPriceDetailSpecsGrid').jqxGrid('getrowdata', rowIndex);
									//对取值范围进行验证
									// TODO 规格值，计费类型 区间和费区间验证
									if (value == "" || value == null || value == "null") {
										return { result: false, message: "请按照取计费类型填写正确值！" };
									}
									return true;
								}
							},
						],
						showtoolbar: false
					});

					//根据选中的计费类型禁用某些选项
					//$("#addPriceDetailSpecsGrid").on('cellendedit', function (event) {
					//	var column = $("#addPriceDetailSpecsGrid").jqxGrid('getcolumn', event.args.datafield);
					//	if(column.datafield == "billingChargeType"){
					//		$("#addPriceDetailSpecsGrid").jqxGrid('endcelledit', 0, "unitPrice", false);
					//		$("#addPriceDetailSpecsGrid").jqxGrid('endcelledit', 0, "initPrice", false);
					//	}else {
                    //
					//	}
					//});
				}
			});

			
			$("#addSpecWindow").jqxWindow({
                width: 700, 
                height:250,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addSpecCancel"), 
                theme: currentTheme,
                modalOpacity: 0.3,
                initContent:function(){
                	 $("#addSpecSave").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	     $("#addSpecCancel").jqxButton({ width: '50',height:"25",theme:"metro"});

					//计费项目不可编辑
					var cellbeginedit = function (row, datafield, columntype, value) {
						var specData = $('#addSpecGrid').jqxGrid('getrowdata', row);
						if(specData.billLabel == "Yes")
						   return false;
					}
					//对于非计费的重新渲染
					var cellsrenderer = function (row, column, value, defaultHtml) {
						var specData = $('#addSpecGrid').jqxGrid('getrowdata', row);
						if (specData.billLabel == "Yes") {
							var element = $(defaultHtml);
							element.css('color', '#999');
							return element[0].outerHTML;
						}
						return defaultHtml;
					}

	        	     $("#addSpecGrid").jqxGrid({
		   	              width: "100%",
		   	              height: 175,
		   				  theme:currentTheme,
		   	              columnsresize: true,
		   	              autoheight: false,
		   	              autowidth: false,
		   	              sortable: true,
		   	              editable: true,
		   	              selectionmode:"checkbox",
		   	              localization: gridLocalizationObj,
		   	              columns: [
							  { text: '规格SID', datafield: 'sid',editable:false},
		   	                  { text: '规格名称', datafield: 'name',editable:false},
		   	                  { text: '规格说明', datafield: 'description',editable:false},
		   	                  { text: '数据类型', datafield: 'dataType',editable:false},
		   	                  { text: '单位',  datafield: 'unit',editable:false},
		   	                  { text: '取值范围',  datafield: 'valueDomain',editable:false},
							  { text: '是否计费',  datafield: 'billLabel',editable:false},
							  { text: '固定值(非计费项)',  datafield: 'value',editable:true,cellbeginedit:cellbeginedit,
								  validation: function (cell, value) {
									  var rowIndex = cell.row;
									  var specData = $('#addSpecGrid').jqxGrid('getrowdata', rowIndex);
									  //获得取值范围
									  var valueDomain = specData.valueDomain;
									  //对取值范围进行验证
									  // TODO 规格项取值范围值验证，暂时只对空进行验证
									  if (value == "" || value == null || value == "null") {
										  return { result: false, message: "请按照取值范围填写相应值！" };
									  }
									  return true;
								  }, cellsrenderer: cellsrenderer
							  },
		   	              ],
		   	              showtoolbar: false
	   	          });
                }
             });
			
			// 弹出定价window
			$("#popServicePricingWindow").jqxWindow({
                width: 430, 
                height:350,
                resizable: true,
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#serviceBillingCancel"), 
                theme: currentTheme,
                modalOpacity: 0.3,
                initContent:function(){}
             });
			
			// 弹出收费window
			$("#popSetChargeTypeWindow").jqxWindow({
                width: 350, 
                height:80,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#setChargeTypeSave"), 
                theme: currentTheme,
                modalOpacity: 0.3,
                initContent:function(){
                	var code = new codeModel({width:150,autoDropDownHeight:true});
	        	    code.getCommonCode("charge-type","BILLING_CHARGE_TYPE");
                	$("#setChargeTypeSave").jqxButton({ width: '50',height:"25",theme:"metro"});
                }
             });
			
	    };

	this.loadBillingPlanSpecData = function(isBillAdapter){
		var serviceSid = $("#add-service-name").val();
		Core.AjaxRequest({
			url : ws_url + "/rest/billings/getBillingPlanServiceSpec/"+serviceSid,
			type:'get',
			async:false,
			callback : function (data) {
				var sourcedatagrid ={
					datatype: "json",
					localdata: data,
					datafields: [
						{ name: 'isSelected', type: 'bool'},
						{ name: 'sid', type: 'string'},
						{ name: 'name', type: 'string'},
						{ name: 'description', type: 'string'},
						{ name: 'dataType', type: 'string'},
						{ name: 'unit', type: 'string'},
						{ name: 'valueDomain', type: 'string'},
						{ name: 'billDisplay', value: 'bill', values: { source: isBillAdapter.records, value: 'value', name: 'label' } },
						{ name: 'bill', type: 'bool'}
					]
				};
				var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
				$("#billingPalnSpec").jqxGrid({source: dataAdapter});
			}
		});
	}
	    
	    //给编辑Windows赋值
	    this.setEditBillingPlanForm = function(data){
	    	 $("#edit-plan-name").jqxInput({value:data.billingPlanName});
	         $("#edit-plan-scope").jqxInput({value:data.planScope});
	         $("#edit-service-name").val(data.serviceSid);
	         $("#edit-plan-status").val(data.planStatus);
	         $("#edit-plan-type").val(data.billingPlanType);
	    };
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-billing-plan-name").jqxInput({placeHolder: "", height: 23, width: 100, minLength: 1,theme:"metro"});
	        $("#search-button").jqxButton({ width: '50',theme:"metro"});
	        
	        $("#edit-plan-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:"metro"});
	        $("#edit-plan-scope").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:"metro"});
	        $("#editSave").jqxButton({ width: '50',height:"25",theme:"metro"});
	        $("#editCancel").jqxButton({ width: '50',height:"25",theme:"metro"});
	        var codeEdit = new codeModel({width:150,autoDropDownHeight:true});
	        codeEdit.getCommonCode("edit-plan-type","BILLING_PLAN_TYPE");
	        codeEdit.getCommonCode("edit-plan-status","BILLING_PLAN_STATUS");
   			 var serviceEdit = new codeModel({width:150,autoDropDownHeight : false,
   				dropDownWidth : 150,
   				dropDownHeight : 100});
   			serviceEdit.getCustomCode("edit-service-name","/services/findServiceDefines","serviceName","serviceSid",true,"post",null);
	        
	        var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			codesearch.getCommonCode("search-billing-plan-type","BILLING_PLAN_TYPE",true);
//			// 初始化下拉列表框 
			 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			 codesearch.getCommonCode("search-billing-plan-status","BILLING_PLAN_STATUS",true);
	    };
	   
	    // 初始化验证规则
	    this.initValidator = function(){
	    	$('#addBillingPlanForm').jqxValidator({
                rules: [
                          { input: '#add-plan-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-plan-name', message: '最多为64个字符', action: 'keyup, blur', rule: 'length=0,64' }
                       ]
	    	});
	    	$('#editBillingPlanForm').jqxValidator({
                rules: [
                          { input: '#edit-plan-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-plan-name', message: '最多为64个字符', action: 'keyup, blur', rule: 'length=0,64' }
                       ]
	    	});

	    	$('#addBillingPlanForm').on('validationSuccess', function (event) {
				//验证通过添加资费计划
		    	 var billingPlan = Core.parseJSON($("#addBillingPlanForm").serializeJson());
				var rowindexes = $('#billingPalnSpec').jqxGrid('getselectedrowindexes');
				var specs = new Array();

				//验证选择的规格是否设置规格计费项
				for(var i=0;i<rowindexes.length;i++){
					var specData = $('#billingPalnSpec').jqxGrid('getrowdata', rowindexes[i]);
					if(specData.bill == "" || specData.bill == null){
						Core.alert({title:"提示",message:"请设置规格["+specData.name+"]是否参与计费！"});
						return;
					}

				}
				//获取资费计划关联的规格信息
				if(rowindexes.length > 0){
					for(var i=0;i<rowindexes.length;i++){
						var specObj = new Object();
						var specData = $('#billingPalnSpec').jqxGrid('getrowdata', rowindexes[i]);
						specObj.sid = specData.sid;
						specObj.name = specData.name;
						specObj.bill = specData.bill;
						specObj.description = specData.description;
						specs.push(specObj);
					}

				}
				  billingPlan.billingPlanSpecVos = specs;
                     Core.AjaxRequest({
		 				url : ws_url + "/rest/billings/insertBillingPlan",
		 				params :billingPlan,
		 				callback : function (data) {
		 					me.searchBillingPlanInfo();
 							$("#addBillingPlanWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	$("#addBillingPlanWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });

	    	$('#editBillingPlanForm').on('validationSuccess', function (event) {
		    		 var billingPlan = Core.parseJSON($("#editBillingPlanForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/billings/updateBillingPlan",
		 				params :billingPlan,
		 				callback : function (data) {
		 					me.searchBillingPlanInfo();
 							$("#editBillingPlanWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	$("#editBillingPlanWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
  };
};
  
	//弹出规格组合window
  function popAddSpecWindow (){
	// 查询添加的规格
		var rowindex = $('#billingPlanGrid').jqxGrid('getselectedrowindex');
	  	if(rowindex >= 0){
			    var data = $('#billingPlanGrid').jqxGrid('getrowdata', rowindex);
			    console.log(data.billingPlanSid);
			    var type = $('#billing-type').val();
			    Core.AjaxRequest({
				url : ws_url + "/rest/billings/getBillingPlanServiceSpec/"+data.serviceSid+"/"+data.billingPlanSid,
				type:'get',
				async:false,
				callback : function (data) {
					if(data == null || data == ''){
					}else{
						// 清空选择
						$("#addSpecGrid").jqxGrid('clearselection');
					}
					var sourcedatagrid ={
						datatype: "json",
						localdata: data
					};
					var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
					$("#addSpecGrid").jqxGrid({source: dataAdapter});

				}
			});
			    
			     var windowW = $(window).width(); 
			 	 var windowH = $(window).height(); 
			 	 $("#addSpecWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-250)/2 } });
			 	 $("#addSpecWindow").jqxWindow('open');
	  	}
   };


//保存资费明细组合信息
  function saveAddPriceDetail(){
	  var selectRows = $('#addPriceDetailSpecsGrid').jqxGrid('getselectedrowindexes');
	  if(selectRows.length > 0){
		  var pricingItems = new Array();

		  var pricingIndex = $('#billingPriceDetails').jqxGrid('getselectedrowindex');
		  var pricingData = $('#billingPriceDetails').jqxGrid('getrowdata', pricingIndex);

		  //验证规格数据值
		  for(var i=0;i<selectRows.length;i++){
			  var itemData = $('#addPriceDetailSpecsGrid').jqxGrid('getrowdata', selectRows[i]);
			  if(itemData.billingChargeType == "01" && (
					  itemData.unitPrice == "" || itemData.unitPrice == null ||
					  itemData.initPrice == "" || itemData.initPrice == null ||
					  itemData.moduleValue == "" || itemData.moduleValue == null
				  )){
				  Core.alert({title:"提示",message:"请设置规格["+itemData.name+"]单价，初始单价及规格值！"});
				  return;
			  }else if(itemData.billingChargeType == "02" &&(
					  itemData.unitPrice == "" ||  itemData.unitPrice == null ||
					  itemData.moduleValue == "" || itemData.moduleValue == null
				  )){
				  Core.alert({title:"提示",message:"请设置规格["+itemData.name+"]单价及规格值！"});
				  return;
			  }else if(itemData.billingChargeType == "00" && (itemData.moduleValue == "" || itemData.moduleValue == null)
				  ){
				  Core.alert({title:"提示",message:"请设置规格["+itemData.name+"]规格值！"});
				  return;
			  }else if(itemData.billingChargeType == "" || itemData.billingChargeType == null){
				  Core.alert({title:"提示",message:"请设置规格["+itemData.name+"]计费类型！"});
				  return;
			  }

		  }

		  for(var i=0;i<selectRows.length;i++){
			  // 查询出该行数据
			  var itemData = $('#addPriceDetailSpecsGrid').jqxGrid('getrowdata', selectRows[i]);
			  var itemObj = new Object();
			  //所选择规格项sid
			  itemObj.specSid = $("#billed-specs").val();
			  itemObj.name = $("#billed-specs").text();
			  itemObj.billingPricingSid = pricingData.billingPricingSid;
			  itemObj.moduleName = itemData.description;
			  itemObj.moduleCode = itemData.name;
			  itemObj.moduleValue = itemData.moduleValue;
			  itemObj.billingChargeType = itemData.billingChargeType;
			  itemObj.unitPrice = itemData.unitPrice;
			  itemObj.initPrice = itemData.initPrice;
			  pricingItems.push(itemObj);
		  }
		  //提交资费明细信息
		  Core.AjaxRequest({
			  url : ws_url + "/rest/billings/create/priceDetails",
			  type:'post',
			  params:pricingItems,
			  async:false,
			  callback : function (data) {
				  // 刷新资费定价明细
				  refreshBillPricing();
				  // 关闭服务定价窗口
				  $("#addPriceDetailWindow").jqxWindow('close');
			  }
		  });

	  }else{
		  Core.alert({title:"提示",message:"请选择至少一项规格作为计费项 ！"});
	  }

  }

 // 保存选中的添加规格
   function saveAddSpec(){
	   var rowArr = $('#addSpecGrid').jqxGrid('getselectedrowindexes');
	   if(rowArr.length > 0){

		   //验证规格组合信息
		   for(var i=0;i<rowArr.length;i++){
			   var specData = $('#addSpecGrid').jqxGrid('getrowdata', rowArr[i]);
			   if(specData.value == "" || specData.value == null){
				   Core.alert({title:"提示",message:"请设置规格["+specData.name+"]非计费项固定值！"});
				   return;
			   }

		   }

		   var pricingObj = new Object();
		   var pricingSpecs = new Array();
		   for(var i=0;i<rowArr.length;i++){
				// 查询出该行数据
			   var specData = $('#addSpecGrid').jqxGrid('getrowdata', rowArr[i]);
			   var specObj = new Object();
			   specObj.sid = specData.sid;
			   specObj.name = specData.name;
			   specObj.bill = specData.bill;
			   specObj.description = specData.description;
			   specObj.value = specData.value;
			   pricingSpecs.push(specObj);
		   }
		   // 资费计划SID
		   var rowindex = $('#billingPlanGrid').jqxGrid('getselectedrowindex');
   		   var planData = $('#billingPlanGrid').jqxGrid('getrowdata', rowindex);
		   pricingObj.billingPlanSpecVoList = pricingSpecs;
   		   pricingObj.billingPlanSid = planData.billingPlanSid;
		   pricingObj.billingType = $("#billing-type").val();
		   pricingObj.status = $("#billingStatus").val();
		   
		   billingPricingObj = pricingObj;
		   
		   setChargeTypeWindow();
		  
	   }else{
		   Core.alert({title:"提示",message:"请选择一条服务规格 ！"});
	   }
   }
   
   // 删除选择好的资费定价明细
   function deleteBillingDetails(){
	   var rowindex = $('#billingPriceDetails').jqxGrid('getselectedrowindex');
	   if(rowindex >= 0){
		   Core.confirm({
				title:"请选择",
				message:"您确定要删除该资费详情吗？",
				confirmCallback:function(){
					var data = $('#billingPriceDetails').jqxGrid('getrowdata', rowindex);
					// 删除资费定价详情
					Core.AjaxRequest({
			 			url : ws_url + "/rest/billings/delete/BillingPricing/"+data.billingPricingSid,
			 			type:'get',
			 			async:false,
			 			callback : function (data) {
			 				// 刷新资费定价明细
			 				refreshBillPricing();
			 				
			 			}
					 });
				}
		  });
	   }
   }
   
   // 弹出新增资费计划
   function addBillingPlanItem () {
	  	// 初始化用户新增页面
	    $("#add-plan-name").val("");
        $("#add-plan-scope").val("");
		
		// 初始化新增window位置
	   	var windowW = $(window).width(); 
	   	var windowH = $(window).height(); 
	   	$("#addBillingPlanWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-260)/2 } });
	   	$("#addBillingPlanWindow").jqxWindow('open');
   };
   /** 弹出资费计划编辑window */
   function editBillingPlanItem() {
	   	var rowindex = $('#billingPlanGrid').jqxGrid('getselectedrowindex');
	   	if(rowindex >= 0){
	   		    var data = $('#billingPlanGrid').jqxGrid('getrowdata', rowindex);
	   		    $("#billingPlanSid").val(data.billingPlanSid);
	   		    // 将常用的字段可以使用这个方法设置数据
	   		    var plan = new billingPlanModel();
	   		    plan.setEditBillingPlanForm(data);
	   		   
	   		    var windowW = $(window).width(); 
	   	    	var windowH = $(window).height(); 
	   	    	$("#editBillingPlanWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-260)/2 } });
	   	    	$("#editBillingPlanWindow").jqxWindow('open');
	   	}
   };
 
   // 提交新增资费计划信息
   function addBillingPlanSubmit(){
	   	// 判断是否通过了验证
	   	$('#addBillingPlanForm').jqxValidator('validate');
   };
   
   // 提交编辑资费计划信息
   function editBillingPlanSubmit(){
	   	// 判断是否通过了验证
	   	$('#editBillingPlanForm').jqxValidator('validate');
   };
   
   // 删除资费计划
   function removeBillingPlanItem() {
   	  var rowindex = $('#billingPlanGrid').jqxGrid('getselectedrowindex');
   	  if(rowindex >= 0){
   		   var data = $('#billingPlanGrid').jqxGrid('getrowdata', rowindex);
		   Core.confirm({
					title:"提示",
					message:"确定要删除该资费计划吗？",
					confirmCallback:function(){
						Core.AjaxRequest({
			 				url : ws_url + "/rest/billings/deleteBillingPlan/"+data.billingPlanSid,
			 				type:"get",
			 				callback : function (data) {
			 					billingPlanSearch();
			 			    },
			 			    failure:function(data){
			 			    }
			 			});
					}
			});
   		}
   };
   
   // 刷新资费定价明细datagrid
   function refreshBillPricing(){
	     var type = $("#billing-type").val();
	     var rowindex = $('#billingPlanGrid').jqxGrid('getselectedrowindex');
 		 var planData = $('#billingPlanGrid').jqxGrid('getrowdata', rowindex);
	     Core.AjaxRequest({
 			url : ws_url + "/rest/billings/getBillingBytype/"+type+"/"+planData.billingPlanSid+"",
 			type:'get',
 			async:false,
 			callback : function (data) {
 				 var sourcedatagrid ={
 				          datatype: "json",
 				          localdata: data
 				   };
 				   var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
 				   $("#billingPriceDetails").jqxGrid({source: dataAdapter});
 			}
 		 });
	     
	     Core.AjaxRequest({
	  			url : ws_url + "/rest/billings/billingPricingDetail/findAll",
	  			type:'post',
	  			params:{},
	  			async:false,
	  			callback : function (data) {
	  				var sourcedatagrid ={
	 			              datatype: "json",
	 			              localdata: data
	  			    };
	  				 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid, { autoBind: true });
	  			    detailsItems = dataAdapter.records;
	  			}
  		 });
   }
 
   // 弹出管理计费定价window
   function mgtBillingPrice(){
		var rowindex = $('#billingPlanGrid').jqxGrid('getselectedrowindex');
		
	   	if(rowindex >= 0){
	   		    var data = $('#billingPlanGrid').jqxGrid('getrowdata', rowindex);
	   		    $("#mgtBillingPriceSid").val(data.billingPlanSid);
	   		    
	   		    // 查询资费定价明细赋值
	   		    Core.AjaxRequest({
	   	 			url : ws_url + "/rest/billings/getBillingBytype/Month/"+data.billingPlanSid+"",
	   	 			type:'get',
	   	 			async:false,
	   	 			callback : function (data) {
	   	 				 var sourcedatagrid ={
	   	 				          datatype: "json",
	   	 				          localdata: data
	   	 				   };
	   	 				   var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	   	 				   $("#billingPriceDetails").jqxGrid({source: dataAdapter});
	   	 			}
	   	 		 });
	   		     // 查询定价明细Details
		   		 Core.AjaxRequest({
			  			url : ws_url + "/rest/billings/billingPricingDetail/findAll",
			  			type:'post',
			  			params:{},
			  			async:false,
			  			callback : function (data) {
			  				var sourcedatagrid ={
			 			              datatype: "json",
			 			              localdata: data
			  			    };
			  				 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid, { autoBind: true });
			  			    detailsItems = dataAdapter.records;
			  			}
		  		 });
		   		 
		   		 // 初始化定价类型
		   		var code = new codeModel({width:150,autoDropDownHeight:true});
		   		code.getCommonCode("billing-type","BILLING_TYPE_YM");
	   		   
	   		    var windowW = $(window).width(); 
	   	    	var windowH = $(window).height(); 
	   	    	$("#mgtBillingPriceWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-400)/2 } });
	   	    	$("#mgtBillingPriceWindow").jqxWindow('open');
	   	}
		  
   };
   
   // 弹出订单定价
   function popBillPricingWindow(row){
	   //$("#unitPrice").jqxInput({placeHolder: "", height: 22, width: 180, minLength: 1,theme:"metro"});
	   $("#addPriceDetailSave").jqxButton({ width: '50',height:"25",theme:"metro"});
	   $("#addPriceDetailCancel").jqxButton({ width: '50',height:"25",theme:"metro"});


	   //初始化可定价规格项信息
	   var billedSpecs = new codeModel({width:150,autoDropDownHeight : false,
		   dropDownWidth : 150,
		   dropDownHeight : 100});
	   var data = $('#billingPriceDetails').jqxGrid('getrowdata', row);
	   var rowindexes = $('#billingPlanGrid').jqxGrid('getselectedrowindexes');
	   var planData = $('#billingPlanGrid').jqxGrid('getrowdata', rowindexes[0]);
	   var billingPlanSid = data.billingPlanSid;
	   billedSpecs.getCustomCode("billed-specs","/billings/getBilledPlanServiceSpec/"+billingPlanSid,"specName","billingPlanSpecSid",false,"get",null);

	   //初始化grid信息
	   Core.AjaxRequest({
		   url : ws_url + "/rest/billings/getBillingPlanServiceSpec/"+planData.serviceSid+"/"+data.billingPlanSid,
		   type:'get',
		   async:false,
		   callback : function (data) {
			   if(data == null || data == ''){
			   }else{
				   // 清空选择
				   $("#addPriceDetailSpecsGrid").jqxGrid('clearselection');
			   }
			   var sourcedatagrid ={
				   datatype: "json",
				   localdata: data
			   };
			   var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
			   $("#addPriceDetailSpecsGrid").jqxGrid({source: dataAdapter});

		   }
	   });


	   // 弹出定价window
	   var windowW = $(window).width();
	   var windowH = $(window).height();
	   $("#addPriceDetailWindow").jqxWindow({ position: { x: (windowW-430)/2, y: (windowH-300)/2 } });
	   $("#addPriceDetailWindow").jqxWindow('open');

	   // 验证规则
//	   var rules = [];
//	   var data = $('#billingPriceDetails').jqxGrid('getrowdata', row);
//	   $("#billingPricingSid").val(data.billingPricingSid);
//	   $("#configBillingPriceDetails").val(data.billingConfigName);
//	   $("#configBillingPriceDetailsDescription").val(data.billingConfigDescription);
//	   $("#billDetailsChargeType").val(data.billingType);
//	   var unitName = data.billingTypeName.substring(1);
//
//	   var specStr = data.billingConfigDescription;
//	   var specSids = data.billingSpecSid;
//	   var array = specStr.substring(1, specStr.length-1).split(",");
//
//	   var specData = getServiceSpecDetailInfoByConfigName(specSids);
//	   var str = "";
//	   str += "<tr><td colspan='2'><fieldset style='padding-top:2px;padding:-bottom:2px;border:1px solid #D6D6D6'><legend><font style='color:red'>*</font>配置项：</legend> <table width='100%' border='0' cellspacing='5' cellpadding='0'>";
//	   // 循环配置项
//
//	   for(var i=0;i<array.length;i++){
//		   if(specData[i].unit == "" || specData[i].unit == "无"){
//			   str += "<tr><td align='right' width='90px'>"+array[i]+":</td><td><input type='text' id='detailName"+i+"'><span id='detailUnit"+i+"'></span></td></tr>";
//		   }else{
//			   str += "<tr><td align='right' width='90px'>"+array[i]+":</td><td><input type='text' id='detailName"+i+"'><span id='detailUnit"+i+"'>&nbsp;"+specData[i].unit+"</span></td></tr>";
//		   }
//	   }
//	   str +="</table></fieldset></td></tr>";
//	   str += "<tr><td align='right' width='107px'><font style='color:red'>*</font>单价:</td><td><input type='text' id='unitPrice'></input><span>元</span></td></tr>";
////	   str += "<tr><td align='right' width='107px'><font style='color:red'>*</font>折扣率:</td><td><input type='text' value='1' id='discount'></input></td></tr>";
//
//	   if(data.billingType == "Month"){
//		   str +="<tr><td colspan='2'><div id='jqxIsCheckBox' data-name='isMonth' style='margin-left: 107px;margin-top:10px; float: left;'>不满一月时，是否按一月计费</div></td></tr>";
//	   }
//
//	   $("#servicePricingTable").html(str);
//
//	   // 初始化验证
//	   for(var n=0;n<array.length;n++){
//		   var vla = specData[n].valueDomain;
//		   var dataType = specData[n].dataType;
//		   rules.push({ input: '#detailName'+n, message: '不能为空', action: 'keyup, blur', rule: 'required' });
//		   //针对数字类型的验证
//		   if(dataType == "int" || dataType == "float"){
//			   rules.push({ input: '#detailName'+n, message: '只能输入数字', action: 'keyup, blur', rule: 'number' });
//		   }
//		   rules.push({ input: '#detailName'+n, message: ''+specData[n].name+'超出输入最大值', action: 'keyup, blur', rule: 'length=0,'+ vla.length+'' });
//
//	   }
//	   // 单价验证
//	   rules.push({ input: '#unitPrice', message: '不能为空', action: 'keyup, blur', rule: 'required' });
//	   rules.push({ input: '#unitPrice', message: '只能输入数字', action: 'keyup, blur', rule: 'number' });
//	   rules.push({ input: '#unitPrice', message: '必须为正数', action: 'keyup, blur', rule: function (input, commit) {
//           if (parseFloat(input.val())>0) {
//               return true;
//           }
//           return false;
//       } });
//	   rules.push({ input: '#unitPrice', message: '单价最大长度不能超过10个字符', action: 'keyup, blur', rule: 'length=0,10' });
//
//	   // 添加验证
//	   $('#servicePricingTable').jqxValidator({
//           rules: rules
//   	   });
//
//	   // 初始化组件
//	   for(var j=0;j<array.length;j++){
//		   $("#detailName"+j+"").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:"metro"});
//	   }
////	   $("#discount").jqxInput({placeHolder: "", height: 22, width: 180, minLength: 1,disabled:true,theme:"metro"});
//	   if(data.billingType == "Month"){
//		   $("#jqxIsCheckBox").jqxCheckBox({ width: 120, height: 25,theme:"metro"});
//	   }
//
//	   $("#unitPrice").jqxInput({placeHolder: "", height: 22, width: 180, minLength: 1,theme:"metro"});
//	   $("#serviceBillingSave").jqxButton({ width: '50',height:"25",theme:"metro"});
//       $("#serviceBillingCancel").jqxButton({ width: '50',height:"25",theme:"metro"});
//
//	   // 弹出定价window
//	   var windowW = $(window).width();
//	   var windowH = $(window).height();
//	   $("#popServicePricingWindow").jqxWindow({ position: { x: (windowW-430)/2, y: (windowH-300)/2 } });
//	   $("#popServicePricingWindow").jqxWindow('open');
   }
   
   // 保存资费定价详细信息
   function saveMgtBillingPrice(){
	   var specStr = $("#configBillingPriceDetails").val();
	   var specDescriptionStr = $("#configBillingPriceDetailsDescription").val();
	   var array = specStr.substring(1, specStr.length-1).split(",");
	   var arrayDesc = specDescriptionStr.substring(1, specDescriptionStr.length-1).split(",");
	   // 判断定价window验证是否通过
	   var  isok =  $('#servicePricingTable').jqxValidator('validate');
	   if(!isok){
		   return;
	   }
	   
	   var configDetailName=configDetailDescription = "{";
	   // 获取数据
	   for(var i=0;i<array.length;i++){
		   if(i == array.length-1){
			   configDetailDescription += arrayDesc[i]+":"+$("#detailName"+i+"").val()+$("#detailUnit"+i+"").html()+"}";
			   configDetailName += array[i]+":"+$("#detailName"+i+"").val()+"}";
		   }else{
			   configDetailDescription += arrayDesc[i]+":"+$("#detailName"+i+"").val()+$("#detailUnit"+i+"").html()+",";
			   configDetailName += array[i]+":"+$("#detailName"+i+"").val()+",";
		   }
	   }
	   
	   var billPricing = new Object();
	   
	   billPricing.billingPricingSid = $("#billingPricingSid").val();
	   billPricing.configDetailName = configDetailName;
	   billPricing.configDetailDescription = configDetailDescription;
	   // 判断当前的定价类型
	   if("Day" == $("#billDetailsChargeType").val()){
		   billPricing.chargeUnit = "元";
	   }else if("Month" == $("#billDetailsChargeType").val()){
		   billPricing.chargeUnit = "元";
	   }
	   billPricing.unitPrice = $("#unitPrice").val(); 
//	   billPricing.discount = $("#discount").val();
	   billPricing.isMonth = $("#jqxIsCheckBox").val()? 1:0;
	   // 先判断当前的配置项是否已定价
	   var isok = isSameBillPriceConfiguration($("#billingPricingSid").val(),configDetailName);
	   
	   if(isok){
		   Core.AjaxRequest({
	 			url : ws_url + "/rest/billings/create/BillingPriceDetails",
	 			type:'post',
	 			params:billPricing,
	 			async:false,
	 			callback : function (data) {
	 				// 刷新资费定价明细
	 				refreshBillPricing();
	 				// 关闭服务定价窗口
	 				$("#popServicePricingWindow").jqxWindow('close');
	 			}
			 });
	   }else{
		   Core.alert({title:"提示",message:"当前配置项已定价，请重新输入 ！"});
	   }
	   
	  
   }
   
   // 资费计划查询
   function billingPlanSearch(){
	   var plan = new billingPlanModel();
	   plan.searchObj.billingPlanName = $("#search-billing-plan-name").val().toUpperCase();
	   plan.searchObj.billingPlanType = $("#search-billing-plan-type").val()=="全部"?"":$("#search-billing-plan-type").val();
	   plan.searchObj.planStatus = $("#search-billing-plan-status").val()=="全部"?"":$("#search-billing-plan-status").val(); 
	   plan.searchBillingPlanInfo();
   };
   
   // 弹出设置收费类型window
   function setChargeTypeWindow(){
	   
	   var code = new codeModel({width:150,autoDropDownHeight:true});
	   code.getCommonCode("charge-type","BILLING_CHARGE_TYPE");
	   
	   var windowW = $(window).width(); 
	   var windowH = $(window).height(); 
	   $("#popSetChargeTypeWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-80)/2 } });
	   $("#popSetChargeTypeWindow").jqxWindow('open');

   }
  
   // 提交定价信息
   function submitPricingInfo(){
	   
	   // 判断当收费类型是增量收费时，服务规格只能选择1个
	   billingPricingObj.chargeType = $("#charge-type").val();
	   //if($("#charge-type").val() == "01"){
		//   var configName = billingPricingObj.billingConfigName;
		//   var str = configName.substring(1, configName.length-1).split(",");
		//   if(str.length > 1){
		//	   Core.alert({title:"提示",message:"选择增量收费时，服务规格只能选择一个 "});
		//	   return;
		//   }
	   //}
	   
	   // 插入数据库
	   Core.AjaxRequest({
 			url : ws_url + "/rest/billings/create/BillingPrice",
 			type:'post',
 			params:billingPricingObj,
 			async:false,
 			callback : function (data) {
 				// 刷新资费定价window
 				refreshBillPricing();
 				// 关闭新增规格项window
 				$("#popSetChargeTypeWindow").jqxWindow('close');
 				$("#addSpecWindow").jqxWindow('close');
 			}
 	   });
   }
   
   // 删除定价详细
   function deleteBillPricingDetailInfo(obj){
	   // 插入数据库
	   Core.AjaxRequest({
 			url : ws_url + "/rest/billings/delete/BillingPriceDetails/"+obj.pricingDetailSid,
 			type:'get',
 			async:false,
 			callback : function (data) {
 				// 刷新资费定价window
 				refreshBillPricing();
 			}
 	   });
   }
   
   // 判断是否有相同资费定价明细details
   function isSameBillPriceConfiguration(billingPricingSid,config){
	   var isok = false;
	   Core.AjaxRequest({
			url : ws_url + "/rest/billings/checkSamePricingConfig/BillingPriceDetails/"+billingPricingSid+"/"+config+"",
			type:'get',
			async:false,
			callback : function (data) {
				if(data.result == "true"){
					isok = true;
				}else{
					isok = false;
				}
			}
	   });

	   return isok;
   }
   
   // 获得配置项在服务规格中的详细信息
   function getServiceSpecDetailInfoByConfigName(specSids){
	  
	   var specData = [];
	   Core.AjaxRequest({
			url : ws_url + "/rest/billings/getServiceSpecDetailsInfo/"+specSids+"",
			type:'get',
			async:false,
			callback : function (data) {
				specData = data;
			}
	   });
	   return specData;
   }
   