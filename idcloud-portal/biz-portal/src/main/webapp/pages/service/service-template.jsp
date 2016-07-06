<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<style type="text/css">
#addTempForm {
	width: 100%;
}

#addTempForm .panel {
	width: 98%;
	border-bottom: 1px solid #DADADA;
	border-left: 1px solid #DADADA;
	border-right: 1px solid #DADADA;
	border-top: 1px solid #DADADA;
	box-shadow: #DADADA 0px 3px 3px;
	position: relative;
	padding: 1%;
}

#addTempForm .viewTable {
	width: 100%;
}

#addTempForm .viewTable .leftTd {
	text-align: right;
}
</style>

<div style="width: 99.5%; height: 339px;">
	<div id="templategrid" style="margin-left: 1px;margin-right: 1px"></div>
</div>

<div id="addTempWindow">
	<div>新增模板</div>
	<div id="addTempForm" style="overflow: hidden;">

		<div class="panel" style="margin-bottom: 10px">
			<p
				style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
				<span id="box">基本信息</span>
			</p>
			<hr />

			<input type="hidden" data-name="serviceSid" id="add-serviceSid" />

			<table border="0" width="100%" cellspacing="5" cellpadding="0">
				<tr>
					<td align="right"><font style="color: red">*</font>模板名称:</td>
					<td align="left"><input type="text" data-name="templateName"  id="add_templateName" /></td>
					<td align="right">所属服务:</td>
					<td align="left"><input type="text" data-name="serviceName"	 id="add_serviceName" /></td>
					<td align="right">模板状态:</td>
					<td align="left"><div data-name="templateStatus"  id="add_templateStatus"></div></td>
				</tr>
				<tr>
					<td align="right">资费计划:</td>
					<td align="left"><div data-name="billingPlanSid"  id="add_billingPlanSid"></div></td>
					<td align="right">模板图片:</td>
					<td align="left"><input type="text" data-name="imagePath"  id="add_imagePath" /></td>
					<td align="right">到期时间:</td>
					<td align="left">
						<div id="add_expiringDate" data-name="expiringDate"></div>
					</td>
				</tr>
				<tr>
					<td align="right">模板描述:</td>
					<td align="left" colspan="5"><textarea data-name="description" id="add_description"></textarea></td>
				</tr>

			</table>
		</div>
		<div id="vmSpecDetail" class="panel"
			style="margin-bottom: 10px; clear: both; height: 250px;">
			<p
				style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
				<span id="box">模板规格列表</span>
			</p>
			<hr />
			<div id="addTempSpecdatagrid"
				style="height: 60%; width: 100%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
		</div>
		<div style="float: right;">
			<input id="addSave" type="button" value="保存" /> 
			<input id="cancelAdd" type="button" value="关闭" />
		</div>
	</div>

</div>

<div id="editTempWindow">
	<div>编辑模板</div>
	<div id="editTempForm" style="overflow: hidden;">

		<div class="panel" style="margin-bottom: 10px">
			<p
				style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
				<span id="box">基本信息</span>
			</p>
			<hr />

			<input type="hidden" data-name="serviceSid" id="edit-serviceSid"  />
			<input type="hidden" data-name="templateSid" id="edit-templateSid"  />

			<table border="0" width="100%" cellspacing="5" cellpadding="0">
				<tr>
					<td align="right"><font style="color: red">*</font>模板名称:</td>
					<td align="left"><input type="text" data-name="templateName" id="edit_templateName" /></td>
					<td align="right">所属服务:</td>
					<td align="left"><input type="text" data-name="serviceName"	id="edit_serviceName" /></td>
					<td align="right">模板状态:</td>
					<td align="left"><div data-name="templateStatus" id="edit_templateStatus"></div></td>
				</tr>
				<tr>
					<td align="right">资费计划:</td>
					<td align="left"><div data-name="billingPlanSid" id="edit_billingPlanSid"></div></td>
					<td align="right">模板图片:</td>
					<td align="left"><input type="text" data-name="imagePath" id="edit_imagePath" /></td>
					<td align="right">到期时间:</td>
					<td align="left">
						<div id="edit_expiringDate" data-name="expiringDate"></div>
					</td>
				</tr>
				<tr>
					<td align="right">模板描述:</td>
					<td align="left" colspan="5"><textarea data-name="description" id="edit_description"></textarea></td>
				</tr>

			</table>
		</div>
		<div id="vmSpecDetail" class="panel"
			style="margin-bottom: 10px; clear: both; height: 250px;">
			<p
				style="font-size: 16px; font-weight: 500; color: #0099d7; margin: auto;">
				<span id="box">模板规格列表</span>
			</p>
			<hr />
			<div id="editTempSpecdatagrid"
				style="height: 60%; width: 100%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
		</div>
		<div style="float: right;">
			<input id="editSave" type="button" value="保存" /> 
			<input id="cancelEdit" type="button" value="关闭" />
		</div>
	</div>

</div>

<script type="text/javascript">
	var serviceSid = '<%=request.getParameter("serviceSid")%>';
	//$("#add-serviceSid").val(serviceSid);
	var serviceName = '<%=request.getParameter("serviceName")%>';

	
	
	$(function(){
		
	    $("#cancelAdd").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	    $("#cancelEdit").jqxButton({ width: '50px', height:'25px', theme:currentTheme});

 	     //初始化新增弹出窗口
 		$("#addTempWindow").jqxWindow({
                width: 1050, 
                height:540,
                resizable: false,  
                isModal: true,
                theme: currentTheme,
                autoOpen: false, 
                //cancelButton: $("#cancelAdd"), 
                modalOpacity: 0.3           
 	     });

		
 		$("#cancelAdd").click(function() {
 			$("#addTempWindow").jqxWindow('close');
 		});
		//初始化编辑弹出窗口
		$("#editTempWindow").jqxWindow({
               width: 1050, 
               height:540,
               resizable: false,  
               isModal: true, 
               theme: currentTheme,
               autoOpen: false, 
               cancelButton: $("#cancelEdit"), 
               modalOpacity: 0.3           
	     });

		//初始化模块管理列表
		initServiceTempGrid();
		
		// 打开新增弹出窗口
		$("#addTempBtn").click(function() {
// 			if ($("#addTempWindow").length > 0) {
// 				$("#addTempWindow")
// 			}
			
			//初始化弹出窗口
			initAddTempWindows(serviceSid,serviceName);
			
			// 初始化新增window位置
			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#addTempWindow").jqxWindow({
				position : {
					x : (windowW - 800) / 2,
					y : (windowH - 600) / 2
				}
			});
			
			$("#addTempWindow").jqxWindow('open');
			
		});
		

		
		// 打开编辑弹出窗口
		$("#editTempBtn").click(function() {
			//清除编辑列表选择
			$("#editTempSpecdatagrid").jqxGrid('clearselection');
			
			//初始化弹出窗口
			initEditTempWindows(serviceSid,serviceName);

			//选择编辑列表
			var rows = $("#editTempSpecdatagrid").jqxGrid('getrows');
		    for(var i = 0; i < rows.length - 1; i++)
		    {
		        var row = rows[i];
		        
		        if(row.checkbox == true){
					$("#editTempSpecdatagrid").jqxGrid('selectrow', i);
		        	//alert(rows.length +"    " + i + "   "+ row.name + "   " +row.checkbox);
				}
		    }
			// 初始化新增window位置
			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#editTempWindow").jqxWindow({
				position : {
					x : (windowW - 800) / 2,
					y : (windowH - 600) / 2
				}
			});
			$("#editTempWindow").jqxWindow('open');
		});
	});
	
	
	
	$("#addTempForm").jqxValidator({
		rules : [ {
			input : '#add_templateName',
			message : '不能为空!',
			action : 'keyup, blur',
			rule : 'required'
		}, {
			input : '#add_templateName',
			message : '模板名称为0-64个字符!',
			action : 'keyup, blur',
			rule : 'length=0,64'
		}, ]
	});
	
	$("#editTempForm").jqxValidator({
		rules : [ {
			input : '#edit_templateName',
			message : '不能为空!',
			action : 'keyup, blur',
			rule : 'required'
		}, {
			input : '#edit_templateName',
			message : '模板名称为0-64个字符!',
			action : 'keyup, blur',
			rule : 'length=0,64'
		}, ]
	});
	// 删除服务模板
	$("#delTempBtn").click(function() {
		var rowindex = $('#templategrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#templategrid').jqxGrid('getrowdata', rowindex);
		    	Core.confirm({
					title:"提示",
					message:"确定要删除该服务模板吗?",
					yes:"确定",
					confirmCallback:function(){
						Core.AjaxRequest({
			 				url : ws_url + "/rest/services/platform/deleteTemplate?templateSid="+data.templateSid,
			 				type:"get",
			 				cache: false,
			 				callback : function (data) {
			 					Core.alert({
			 						title:"提示",
			 						message:"删除成功！",
			 						callback:function(){
			 							refleshServiceTempGrid();
			 						}
			 					});
			 			    },
			 			    failure:function(data){
			 			    	Core.alert({
			 						title:"提示",
			 						message:"删除失败！"
			 					});
			 			    }
			 			});
					}
			});
    	}
	});
	
	// 新增用户表单验证成功
	$("#addTempForm").on('validationSuccess', function (event) {
		//console.log("----");
		var temp = Core.parseJSON($("#addTempForm").serializeJson());
		//console.log(JSON.stringify(temp));
		var rowindexs = [];
	    var selectData=[];
	    rowindexs = $("#addTempSpecdatagrid").jqxGrid('getselectedrowindexes');
	    var gridValidation = true;
	    $.each(rowindexs, function(i, n) {
	    	var value = $("#addTempSpecdatagrid").jqxGrid('getcellvalue', n, "value");
	    	if(value == "" || value == null) {
	    		$("#addTempSpecdatagrid").jqxGrid('showvalidationpopup', n, "value", "数值不能为空!");
	    		gridValidation = false;
	    		return false;
	    	} else {
		    	var data = $("#addTempSpecdatagrid").jqxGrid('getrowdata', n);
		    	//sunyu
		    	data.createdDt ="";
		    	data.updatedDt ="";
		    	//end
		    	selectData[i] = data;
	    	}
	    });
	    
	    if(!gridValidation){
            return false;
        }
	    
		 var param = {};
		 param.serviceSpecs = selectData;
		 var newObj = $.extend(temp,param);
		 //console.log(JSON.stringify(newObj));
		 Core.AjaxRequest({
				url : ws_url + "/rest/services/platform/insertServiceTemplate",
				params :newObj,
				cache: false,
				callback : function (data) {
					Core.alert({
						title:"提示",
						message:"模板新增成功！",
						// sunyu update for 133
						//confirmCallback:function(){
						callback:function(){	
						// end
							$("#addTempWindow").jqxWindow('close');
							//$("#" + addTempWindow).jqxWindow('destroy');
							//initServiceTempGrid();
							refleshServiceTempGrid();
						}
					});
			    },
			    failure:function(data){
			    	Core.alert({
						title:"提示",
						message:"模板新增失败！",
						callback:function(){
							$("#addTempWindow").jqxWindow('destory');
						}
					});
			    }
			});
	 });
	
	// 编辑模板表单验证成功
	$("#editTempForm").on('validationSuccess', function (event) {
		var temp = Core.parseJSON($("#editTempForm").serializeJson());
		var rowindexs = [];
	    var selectData=[];
	    rowindexs = $("#editTempSpecdatagrid").jqxGrid('getselectedrowindexes');
	    var gridValidation = true;
	    $.each(rowindexs, function(i, n) {
	    	var value = $("#editTempSpecdatagrid").jqxGrid('getcellvalue', n, "value");
	    	if(value == "" || value == null) {
	    		$("#editTempSpecdatagrid").jqxGrid('showvalidationpopup', n, "value", "数值不能为空!");
	    		gridValidation = false;
	    		return false;
	    	} else {
		    	var data = $("#editTempSpecdatagrid").jqxGrid('getrowdata', n);
		    	//sunyu
		    	data.createdDt ="";
		    	data.updatedDt ="";
		    	//end
		    	selectData[i] = data;
	    	}
	    });
	    
	    if(!gridValidation){
            return false;
        }
	    
		 var param = {};
		 param.serviceSpecs = selectData;
		 var newObj = $.extend(temp,param);
		 
		 Core.AjaxRequest({
				url : ws_url + "/rest/services/platform/updateServiceTemplate",
				params :newObj,
				cache: false,
				callback : function (data) {
					Core.alert({
						title:"提示",
						message:"模板编辑成功！",
						callback:function(){ 
							$("#editTempWindow").jqxWindow('close');
							refleshServiceTempGrid();
						}
					});
			    },
			    failure:function(data){
			    	Core.alert({
						title:"提示",
						message:"模板编辑失败！",
						callback:function(){
							$("#editTempWindow").jqxWindow('close');
						}
					});
			    }
			});
	 });
	
	 $("#addSave").click(function () {
		$("#addTempForm").jqxValidator('validate');	
	 }); 
	 
	 $("#editSave").click(function () {
			$("#editTempForm").jqxValidator('validate');	
		 }); 
	
	 

	/** 初始化新增弹出框  */
	function initAddTempWindows(serviceSid,serviceName) {
		// 初始化模块新增下拉框
		$("#add-serviceSid").val(serviceSid);
		
		var codeadd = new codeModel({width : 150, autoDropDownHeight : true});
		codeadd.getCommonCode("add_templateStatus", "SERVICE_TEMPLATE_STATUS");
		codeadd.getCommonCode("add_billingPlanSid", "PLAN");

        $("#add_serviceName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme,disabled: true});
        $("#add_serviceName").jqxInput('val', serviceName); 
        $("#add_templateName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
        $("#add_templateName").jqxInput('val', ''); 
        $("#add_imagePath").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
        $("#add_description").jqxInput({placeHolder: "", height: 46, width: 670, minLength: 1,theme:currentTheme});
        $("#add_expiringDate").jqxDateTimeInput({width: '150px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
	    $("#addSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});

		var item = ko.observableArray();
		
		//初始化弹出框模板规格列表
		Core.AjaxRequest({
			url : ws_url + "/rest/services/platform/findServiceTempSpecByParams",
			type : 'post',
			cache: false,
			params : {
				serviceSid : serviceSid
			},
			async : false,
			callback : function(data) {
				item(data);
				var sourcedatagrid = {
					datatype : "json",
					localdata : item
				};
				var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
				$("#addTempSpecdatagrid").jqxGrid(
					{
						width : "770px",
						height : "200px",
						theme : currentTheme,
						source : dataAdapter,
						columnsresize : true,
						pageable : true,
						pagesize : 10,
						editable: true,
						autoheight : false,
						autowidth : false,
						selectionmode : "checkbox",
		                altrows: true,
						localization: gridLocalizationObj,
						columns : [{
							text : '规格名称',
							datafield : 'name',
							editable: false
						}, {
							text : '规格说明',
							datafield : 'description',
							editable: false
						}, {
							text : '数值',
							datafield : 'value',
							columntype: 'numberinput', 
							editable: true,
		                    createeditor: function (row, cellvalue, editor) {
		                    	editor.jqxNumberInput({ min: 0, max:999, decimalDigits: 0, digits: 3 });
		                    }
						}, {
							text : '单位',
							datafield : 'unit',
							editable: false
						} , {
							text : '取值范围',
							datafield : 'valueDomain',
							editable: false
						} ],
						showtoolbar : false,
						
					});

			}
		});
	}
	
	
	/** 初始化编辑弹出框  */
	function initEditTempWindows(serviceSid,serviceName) {
		$("#edit-serviceSid").val(serviceSid);
		// 初始化模块编辑下拉框和输入框
		var codeadd = new codeModel({width : 150, autoDropDownHeight : true});
		codeadd.getCommonCode("edit_templateStatus", "SERVICE_TEMPLATE_STATUS");
		codeadd.getCommonCode("edit_billingPlanSid", "PLAN");
        $("#edit_serviceName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme,disabled: true});
        $("#edit_serviceName").jqxInput('val', serviceName); 
        $("#edit_templateName").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
        $("#edit_imagePath").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
        $("#edit_description").jqxInput({placeHolder: "", height: 46, width: 670, minLength: 1,theme:currentTheme});
        $("#edit_expiringDate").jqxDateTimeInput({width: '150px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
	    $("#editSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
		var templateSid="";
	    var rowindex = $('#templategrid').jqxGrid('getselectedrowindex');
    	if(rowindex >= 0){
    		var data = $('#templategrid').jqxGrid('getrowdata', rowindex);
    		//编辑窗口赋值   		
            $("#edit_templateName").jqxInput({value: data.templateName});
            $("#edit_serviceName").jqxInput({value:serviceName}); 
            $("#edit_templateStatus").val({value: (data.templateStatus == null)?0:data.templateStatus});
            $("#edit_billingPlanSid").val({value: (data.billingPlanSid == null)?0:data.billingPlanSid});
            $("#edit_imagePath").jqxInput({value: data.imagePath});
            $("#edit_description").jqxInput({value: data.description});
            templateSid = data.templateSid;
    		$("#edit-templateSid").val(templateSid);
			var eDate = data.expiringDate;
			if(eDate !=null && eDate!="") {
				var date1 = new Date(eDate.substring(0,4),eDate.substring(5,7), eDate.substring(8,10), 
	            		eDate.substring(11,13), eDate.substring(14,16), eDate.substring(17,19));
	    		$("#edit_expiringDate").jqxDateTimeInput({value: date1});
			} else {
				$("#edit_expiringDate").jqxDateTimeInput({value: null});
			}
			
    	}
		
		var item = ko.observableArray();

		//初始化弹出框模板规格列表
		Core.AjaxRequest({
			url : ws_url + "/rest/services/platform/findServiceTempSpecByParams",
			type : 'post',
			cache: false,
			params : {
				serviceSid : serviceSid,
				templateSid : templateSid
			},
			async : false,
			callback : function(data) {
				item(data);
				var sourcedatagrid = {
					datatype : "json",
					localdata : item
				};
				var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
				$("#editTempSpecdatagrid").jqxGrid(
					{
						width : "770px",
						height : "200px",
						theme : currentTheme,
						source : dataAdapter,
						columnsresize : true,
						pageable : true,
						pagesize : 10,
						editable: true,
						autoheight : false,
						autowidth : false,
						localization: gridLocalizationObj,
						ready: function()
		                {

		                },
						selectionmode : "checkbox",
		                altrows: true,
						theme : currentTheme,
						columns : [{
							text : '规格名称',
							datafield : 'name',
							editable: false
						}, {
							text : '规格说明',
							datafield : 'description',
							editable: false
						}, {
							text : '数值',
							datafield : 'value',
							columntype: 'numberinput', 
							editable: true,
		                    createeditor: function (row, cellvalue, editor) {
		                    	editor.jqxNumberInput({ min: 0, max:999, decimalDigits: 0, digits: 3 });
		                    }
						}, {
							text : '单位',
							datafield : 'unit',
							editable: false
						} , {
							text : '取值范围',
							datafield : 'valueDomain',
							editable: false
						} , {
							text : 'checkbox',
							datafield : 'checkbox',
							hidden : true
						} ],
						showtoolbar : false,
					});
			}
		});
	}
	
	function initServiceTempGrid(){
		// 初始化服务管理模板列表信息
   	 	Core.AjaxRequest({
			url : ws_url + "/rest/services/platform/findServiceTempByServiceSid",
			type:'post',
			cache: false,
			params:{
				serviceSid : serviceSid
				},
			async:false,
			callback : function (data) {
		    	var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     	};

	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#templategrid").jqxGrid(
	          {
	              width: "99.8%",
				  height:"390px",
				  theme:currentTheme,
	              source: dataAdapter,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: false,
	              autowidth: false,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '模版名称', datafield: 'templateName'},
	                  { text: '模版描述', datafield: 'description'},
	                  { text: '到期时间', datafield: 'expiringDate' },
	                  { text: '模板状态', datafield: 'templateStatusName' },
	                  { text: '资费计划', datafield: 'pricingSid' }
	              ],
	              showtoolbar: true,
	              rendertoolbar: function (toolbar) {
	                  var me = this;
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addTempBtnLabel = $("<div><a id='addTempBtn' data-bind='click: addTempItem'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增模板&nbsp;&nbsp;</a></div>");
	                  var editTempBtnLabel = $("<div><a id='editTempBtn' data-bind='click: editTempItem' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑模板&nbsp;&nbsp;</a></div>");
	                  var delTempBtnLabel = $("<div><a id='delTempBtn' data-bind='click: delTempItem' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除模板&nbsp;&nbsp;</a></div>");
	                  
	                  toolbar.append(container);
	                  container.append($(addTempBtnLabel));	                  
	                  container.append($(editTempBtnLabel));
	                  container.append($(delTempBtnLabel));
	              }
	          });
	          
	       // 控制按钮是否可用
	    	  $("#templategrid").on('rowselect', function (event) {
    		  	var args = event.args; 
    		  	var index = args.rowindex;
    		  
    			$("#addTempBtn").jqxButton({width: '80',theme:currentTheme, disabled: false });
    			$("#editTempBtn").jqxButton({width: '80',theme:currentTheme, disabled: false });
   		  		$("#delTempBtn").jqxButton({ width: '80',theme:currentTheme,disabled: false});
					   		  
	          });
	    	  $("#addTempBtn").jqxButton({width: '80',theme:currentTheme,height: '18px', disabled: false });
   			  $("#editTempBtn").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
   			  $("#delTempBtn").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
			}
		 });
	};
	
	function refleshServiceTempGrid(){
		// 初始化服务管理模板列表信息
   	 	Core.AjaxRequest({
			url : ws_url + "/rest/services/platform/findServiceTempByServiceSid",
			type:'post',
			cache: false,
			params:{
				serviceSid : serviceSid
				},
			async:false,
			callback : function (data) {
		    	var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     	};

	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#templategrid").jqxGrid({source: dataAdapter});
	          
			}
		 });
	};
	
</script>
<!-- <script type="text/javascript"> -->
// $("#add-serviceSid").val(serviceSid);
<!-- </script> -->