<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Expires" CONTENT="-1"> 

<div id="<%=request.getParameter("navigationBarIdAdd")%>" style="width:99.5%;height:80%;margin-left:0.3%;margin-top:0.3%;">
	<div>服务基本信息</div>
	<div>
		<div id="addServiceForm" style="margin-top: 5px;height: 95%; width: 100%; font-size: 14px; overflow: hidden;">
			<input type="hidden" id="serviceSid" value="<%=request.getParameter("serviceSid")%>" /> 
			<input type="hidden" id="navigationBarIdAdd" value="<%=request.getParameter("navigationBarIdAdd")%>" />
			<table class="viewTable" width="100%" cellpadding="1px" cellspacing="0">
				<tr>
					<td align="right" width="150px"><font style="color: red">*</font>服务名称：</td>
					<td align="left"><input type="text" data-name="serviceName" id="add-serviceName" /></td>
				</tr>
				<tr>
					<td align="right" width="150px"><font style="color: red">*</font>服务代码：</td>
					<td align="left"><input type="text" data-name="serviceCode" id="add-serviceCode" /></td>
				</tr>
				<tr>
					<td align="right" width="150px">描述：</td>
					<td align="left"><textarea data-name="description" id="add-description"></textarea></td>
				</tr>
				<tr>
					<td align="right" width="150px">服务类型：</td>
					<td align="left"><div data-name="serviceType" id="add-serviceType"></div></td>
				</tr>
				<tr>
					<td align="right" width="150px">关联服务：</td>
					<td align="left">
						<div data-name="relationService" id="add-relation-service" style="float:left;"></div>
						<div style="height:22px;float:left;color:green;font-size:12px;line-height:22px;">※ 当服务类型是复合服务时，可以关联多个原子服务</div>
					</td>
				</tr>
				<tr>
					<td align="right" width="150px">所属服务名称：</td>
					<td align="left"><input type="text" data-name="parentCatalogName" id="add-parentCatalogName" value="<%=request.getParameter("parentCatalogName")%>"/></td>
				</tr>
				<tr>
					<td align="right" width="150px">状态：</td>
					<td align="left"><div data-name="serviceStatus" id="add-serviceStatus"></div></td>
				</tr>
				<!-- sunyu add for Fun#174 -->
				<tr>
					<td align="right" width="150px">服务适用范围：</td>
					<td align="left"><div data-name="serviceScope" id="add-serviceScope"></div></td>
				</tr>
				<tr>
					<td align="right" width="150px">是否订购：</td>
					<td align="left"><div data-name="canOrder" id="add-canOrder"></div></td>
				</tr>
				<!-- end -->
			</table>
		</div>
	</div>
	<div>服务配置</div>
	<div>
		<div id="vAddConfigGrid" style="height: 60%; width: 99%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
		 <div id="addConfigWindow">
            <div>添加服务配置</div>
            <div id="addConfigForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>配置名称:</td>
                        <td align="left"><input type="text" data-name="name" id="add-configName"/></td>
                        
                        <td align="right">数据类型:</td>
                        <td align="left">
                           <div data-name="dataType" id="add-configDataType"></div>
                        </td>
                    </tr>
                    <tr>
 						<td align="right"><font style="color:red">*</font>配置取值:</td>
                        <td align="left" ><input type="text" data-name="value" id="add-configValue"/></td>
                        
                        <td align="right"><font style="color:red">*</font>配置单位:</td>
                        <td align="left"><input type="text" data-name="unit" id="add-configUnit"/></td>
                    </tr>
                    <tr>             
                        <td align="right">配置描述:</td>
                        <td align="left" colspan="3"><textarea data-name="description" id="add-configDescription"></textarea></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
	                        <input style="margin-right: 5px;" data-bind='click: addConfigSubmit' type="button" id="saveConfig" value="保存" />
	                        <input id="cancelConfig" type="button" value="取消" />
                        </td>
                    </tr>
                </table>
            </div>
       </div>
	</div>
	<div>服务规格</div>
	<div>
		<div id="vAddSpecGrid" style="height: 60%; width: 99%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
		<div id="addSpecWindow">
            <div>添加服务规格</div>
            <div id="addSpecForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>规格名称:</td>
                        <td align="left"><input type="text" data-name="name" id="add-specName"/></td>
                        
                        <td align="right">数据类型:</td>
                        <td align="left">
                           <div data-name="dataType" id="add-specDataType"></div>
                        </td>
                    </tr>
                    <tr>
 						<td align="right"><font style="color:red">*</font>规格取值:</td>
                        <td align="left" ><input type="text" data-name="valueDomain" id="add-specValueDomain"/></td>
                        
                        <td align="right"><font style="color:red">*</font>规格单位:</td>
                        <td align="left"><input type="text" data-name="unit" id="add-specUnit"/></td>
                    </tr>
                    <tr>             
                        <td align="right">规格说明:</td>
                        <td align="left" colspan="3"><textarea data-name="description" id="add-specDescription"></textarea></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" data-bind='click: addSpecSubmit' type="button" id="saveSpec" value="保存" />
                        <input id="cancelSpec" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>
	</div>
	<div>性能指标</div>
	<div>
		<div id="vAddPerformanceGrid" style="height: 60%; width: 99%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
		<div id="addPerformanceWindow">
            <div>添加服务性能</div>
            <div id="addPerformanceForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>性能名称:</td>
                        <td align="left"><input type="text" data-name="name" id="add-performanceName"/></td>
                        
                        <td align="right">数据类型:</td>
                        <td align="left">
                           <div data-name="dataType" id="add-performanceDataType"></div>
                        </td>
                    </tr>
                    <tr>
 						<td align="right"><font style="color:red">*</font>性能取值:</td>
                        <td align="left" ><input type="text" data-name="valueDomain" id="add-performanceValueDomain"/></td>
                        
                        <td align="right"><font style="color:red">*</font>性能单位:</td>
                        <td align="left"><input type="text" data-name="unit" id="add-performanceUnit"/></td>
                    </tr>
                      <tr>
 						<td align="right"><font style="color:red">*</font>性能分组:</td>
                        <td align="left" ><input type="text" data-name="perfGroup" id="add-performancePerfGroup"/></td>
                        
                        <td align="right"><font style="color:red">*</font>显示样式:</td>
                        <td align="left"><input type="text" data-name="displayStyle" id="add-performanceDisplayStyle"/></td>
                    </tr>
                    <tr>             
                        <td align="right">性能说明:</td>
                        <td align="left" colspan="3"><textarea data-name="description" id="add-performanceDescription"></textarea></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" data-bind='click: addPerformanceSubmit' type="button" id="savePerformance" value="保存" />
                        <input id="cancelPerformance" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>
	</div>
	<div>服务操作项</div>
	<div>
		<div id="vAddOperateGrid" style="height: 60%; width: 99%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
		<div id="addOperateWindow">
            <div>添加服务操作</div>
            <div id="addOperateForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>操作名称:</td>
                        <td align="left"><input type="text" data-name="name" id="add-operateName"/></td>
                        <td align="right">数据类型:</td>
                        <td align="left">
                           <div data-name="type" id="add-operateType"></div>
                        </td>
                    </tr>
                    <tr>
 						<td align="right"><font style="color:red">*</font>操作定义:</td>
                        <td align="left" colspan="3"><input type="text" data-name="wsDefinition" id="add-operateWsDefinition"/></td>
                    </tr>
                    <tr>             
                        <td align="right">操作描述:</td>
                        <td align="left" colspan="3"><textarea data-name="description" id="add-operateDescription"></textarea></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" data-bind='click: addOperateSubmit' type="button" id="saveOperate" value="保存" />
                        <input id="cancelOperate" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>
	</div>
</div>
<div style="text-align: right;">
	<input id="addServiceBtn" type="button" value="保存" /> 
	<input id="resetServiceBtn" type="button" value="重置" />&nbsp;
</div>
<script type="text/javascript">
	$(function(){
		var navigationBarIdAdd = '<%=request.getParameter("navigationBarIdAdd")%>';
		var parentCatalogSid = '<%=request.getParameter("parentCatalogSid")%>';

		$("#" + navigationBarIdAdd).jqxNavigationBar({
			theme: currentTheme,
			width : "99.5%",
			height : "370"
		});
		
		var model = new serviceMgtModel();
		model.initAddServiceInfo();
		
		/*****************************************************服务配置***********************************************************/
		//初始化新增服务配置弹出窗口
		$("#addConfigWindow").jqxWindow({
               width: 500, 
               height:200,
               resizable: false,  
               isModal: true, 
               autoOpen: false, 
               theme: currentTheme,
               cancelButton: $("#cancelConfig"), 
               modalOpacity: 0.3           
	     });
		
		// 打开新增服务配置弹出窗口
		$("#addConfigBtn").click(function() {

			// 初始化新增window位置
			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#addConfigWindow").jqxWindow({
				position : {
					x : (windowW - 500) / 2,
					y : (windowH - 200) / 2
				}
			});
			$("#addConfigWindow").jqxWindow('open');
		});
		
		$("#saveConfig").click(function () {
			$("#addConfigForm").jqxValidator('validate');	
		});
		
		$("#addConfigForm").jqxValidator({
			rules : [ {input : '#add-configName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#add-configName', message : '配置名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
			          {input : '#add-configValue', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#add-configValue', message : '配置值为0-256个字符!', action : 'keyup, blur', rule : 'length=0,256' },
			          {input : '#add-configUnit', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#add-configUnit', message : '配置单位为0-16个字符!', action : 'keyup, blur', rule : 'length=0,16' },
			        ]
		});
		
		// 新增用户表单验证成功
		$("#addConfigForm").on('validationSuccess', function (event) {
			var temp = Core.parseJSON($("#addConfigForm").serializeJson());
			
			var row = { name: $("#add-configName").val(), description: $("#add-configDescription").val(), dataType: $("#add-configDataType").val(),
					unit: $("#add-configUnit").val(), value: $("#add-configValue").val()};
            $('#vAddConfigGrid').jqxGrid('addrow', null, row);
            //清除元素值
            $("#add-configName").val("");
            $("#add-configDescription").val("");
            $("#add-configDataType").val("");
            $("#add-configUnit").val("");
            $("#add-configValue").val("");

            //关闭弹出框
            $("#addConfigWindow").jqxWindow('close');
		 });
		
	/********************************************************服务配置***********************************************************/
	
	/*****************************************************服务规格***********************************************************/
		//初始化新增服务配置弹出窗口
		$("#addSpecWindow").jqxWindow({
               width: 500, 
               height:200,
               resizable: false,  
               isModal: true, 
               autoOpen: false, 
               cancelButton: $("#cancelSpec"),
               theme: currentTheme,
               modalOpacity: 0.3           
	     });
		
		// 打开新增服务配置弹出窗口
		$("#addSpecBtn").click(function() {

			// 初始化新增window位置
			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#addSpecWindow").jqxWindow({
				position : {
					x : (windowW - 500) / 2,
					y : (windowH - 200) / 2
				}
			});
			$("#addSpecWindow").jqxWindow('open');
		});
		
		$("#saveSpec").click(function () {
			$("#addSpecForm").jqxValidator('validate');	
		});
		
		$("#addSpecForm").jqxValidator({
			rules : [ {input : '#add-specName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#add-specName', message : '规格名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
			          {input : '#add-specValueDomain', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#add-specValueDomain', message : '规格值为0-256个字符!', action : 'keyup, blur', rule : 'length=0,256' },
			          {input : '#add-specUnit', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#add-specUnit', message : '规格单位为0-16个字符!', action : 'keyup, blur', rule : 'length=0,16' },
			        ]
		});
		
		// 新增服务规格验证成功
		$("#addSpecForm").on('validationSuccess', function (event) {
			var temp = Core.parseJSON($("#addSpecForm").serializeJson());
			
			var row = { name: $("#add-specName").val(), description: $("#add-specDescription").val(), dataType: $("#add-specDataType").val(),
					unit: $("#add-specUnit").val(), valueDomain: $("#add-specValueDomain").val()};
            $('#vAddSpecGrid').jqxGrid('addrow', null, row);
            //清除元素值
            $("#add-specName").val("");
            $("#add-specDescription").val("");
            $("#add-specDataType").val("");
            $("#add-specUnit").val("");
            $("#add-specValueDomain").val("");
            //关闭弹出框
            $("#addSpecWindow").jqxWindow('close');
		 });
	/********************************************************服务规格***********************************************************/
	
	/*****************************************************服务性能指标***********************************************************/
		//初始化新增服务配置弹出窗口
		$("#addPerformanceWindow").jqxWindow({
               width: 500, 
               height:220,
               resizable: false,  
               isModal: true, 
               autoOpen: false, 
               theme: currentTheme,
               cancelButton: $("#cancelPerformance"), 
               modalOpacity: 0.3           
	     });
		
		// 打开新增服务配置弹出窗口
		$("#addPerformanceBtn").click(function() {

			// 初始化新增window位置
			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#addPerformanceWindow").jqxWindow({
				position : {
					x : (windowW - 500) / 2,
					y : (windowH - 220) / 2
				}
			});
			$("#addPerformanceWindow").jqxWindow('open');
		});
		
		$("#savePerformance").click(function () {
			$("#addPerformanceForm").jqxValidator('validate');	
		});
		
		$("#addPerformanceForm").jqxValidator({
			rules : [ {input : '#add-performanceName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#add-performanceName', message : '性能名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
			          {input : '#add-performanceValueDomain', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#add-performanceValueDomain', message : '性能值为0-256个字符!', action : 'keyup, blur', rule : 'length=0,256' },
			          {input : '#add-performanceUnit', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#add-performanceUnit', message : '性能单位为0-16个字符!', action : 'keyup, blur', rule : 'length=0,16' },
			          {input : '#add-performancePerfGroup', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#add-performancePerfGroup', message : '性能分组值为0-256个字符!', action : 'keyup, blur', rule : 'length=0,16' },
			          {input : '#add-performanceDisplayStyle', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#add-performanceDisplayStyle', message : '显示样式单位为0-16个字符!', action : 'keyup, blur', rule : 'length=0,16' },
			        ]
		});
		
		// 新增服务规格验证成功
		$("#addPerformanceForm").on('validationSuccess', function (event) {
			var temp = Core.parseJSON($("#addPerformanceForm").serializeJson());
			
			var row = { name: $("#add-performanceName").val(), description: $("#add-performanceDescription").val(), dataType: $("#add-performanceDataType").val(),
					unit: $("#add-performanceUnit").val(), valueDomain: $("#add-performanceValueDomain").val(), perfGroup: $("#add-performancePerfGroup").val(), 
					displayStyle: $("#add-performanceDisplayStyle").val()};
            $('#vAddPerformanceGrid').jqxGrid('addrow', null, row);
            //清除元素值
            $("#add-performanceName").val("");
            $("#add-performanceDescription").val("");
            $("#add-performanceDataType").val("");
            $("#add-performanceUnit").val("");
            $("#add-performanceValueDomain").val("");
            $("#add-performancePerfGroup").val("");
            $("#add-performanceDisplayStyle").val("");

            //关闭弹出框
            $("#addPerformanceWindow").jqxWindow('close');
		 });
	/********************************************************服务性能指标***********************************************************/
	
	/**********************************************************服务操作***********************************************************/
		//初始化新增服务配置弹出窗口
		$("#addOperateWindow").jqxWindow({
               width: 500, 
               height:200,
               resizable: false,  
               isModal: true, 
               autoOpen: false, 
               theme: currentTheme,
               cancelButton: $("#cancelOperate"), 
               modalOpacity: 0.3           
	     });
		
		// 打开新增服务配置弹出窗口
		$("#addOperateBtn").click(function() {

			// 初始化新增window位置
			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#addOperateWindow").jqxWindow({
				position : {
					x : (windowW - 500) / 2,
					y : (windowH - 200) / 2
				}
			});
			$("#addOperateWindow").jqxWindow('open');
		});
		
		$("#saveOperate").click(function () {
			$("#addOperateForm").jqxValidator('validate');	
		});
		
		$("#addOperateForm").jqxValidator({
			rules : [ {input : '#add-operateName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#add-operateName', message : '操作名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
			          {input : '#add-operateWsDefinition', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#add-operateWsDefinition', message : '操作定义值为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
			        ]
		});
		
		// 新增用户表单验证成功
		$("#addOperateForm").on('validationSuccess', function (event) {
			var temp = Core.parseJSON($("#addOperateForm").serializeJson());
			
			var row = { name: $("#add-operateName").val(), description: $("#add-operateDescription").val(), type: $("#add-operateType").val(),
					wsDefinition: $("#add-operateWsDefinition").val()};
            $('#vAddOperateGrid').jqxGrid('addrow', null, row);
            //清除元素值
            $("#add-operateName").val("");
            $("#add-operateWsDefinition").val("");
            $("#add-operateType").val("");
            $("#add-operateDescription").val("");

            //关闭弹出框
            $("#addOperateWindow").jqxWindow('close');
		 });
		
	/********************************************************服务操作***********************************************************/
	
	/********************************************************保存服务***********************************************************/
		$("#addServiceBtn").click(function () {
			$("#addServiceForm").jqxValidator('validate');	
		});
		
		$("#addServiceForm").jqxValidator({
			rules : [ {input : '#add-serviceName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#add-serviceName', message : '操作名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
			        ]
		});
		// 新增服务表单验证成功
		$("#addServiceForm").on('validationSuccess', function (event) {
			
			var serviceForm = Core.parseJSON($("#addServiceForm").serializeJson());
			
			if("02" == serviceForm.serviceType){
				
			}else{
				
			}
			
		    //得出的值多出个"",无法直接映射的处理办法。
		    var rowsConfig = $('#vAddConfigGrid').jqxGrid('getrows');
		    
		    var rowsConfigNew = [];
		    for(var i = 0; i < rowsConfig.length; i++)
		    {
		    	var rowConfig ={};
		        var row = rowsConfig[i];
		        rowConfig.name = row.name;
		        rowConfig.description = row.description;
		        rowConfig.dataType = row.dataType;
		        rowConfig.unit = row.unit;
		        rowConfig.value = row.value;
		        rowsConfigNew[i] = rowConfig;
		    }
			
		    var rowsSpec = $('#vAddSpecGrid').jqxGrid('getrows');
		    
		    var rowsSpecNew = [];
		    for(var i = 0; i < rowsSpec.length; i++)
		    {
		    	var rowSpec ={};
		        var row = rowsSpec[i];
		        rowSpec.name = row.name;
		        rowSpec.description = row.description;
		        rowSpec.dataType = row.dataType;
		        rowSpec.unit = row.unit;
		        rowSpec.valueDomain = row.valueDomain;
		        rowsSpecNew[i] = rowSpec;
		    }
		    
		    
		    var rowsPerformance = $('#vAddPerformanceGrid').jqxGrid('getrows');
		    
		    var rowsPerformanceNew = [];
		    for(var i = 0; i < rowsPerformance.length; i++)
		    {
		    	var rowPerformance ={};
		        var row = rowsPerformance[i];
		        rowPerformance.name = row.name;
		        rowPerformance.description = row.description;
		        rowPerformance.dataType = row.dataType;
		        rowPerformance.unit = row.unit;
		        rowPerformance.valueDomain = row.valueDomain;
		        rowPerformance.perfGroup = row.perfGroup;
		        rowPerformance.displayStyle = row.displayStyle;
		        rowsPerformanceNew[i] = rowPerformance;
		    }
		    
		    
		    var rowsOperate = $('#vAddOperateGrid').jqxGrid('getrows');
		    
		    var rowsOperateNew = [];
		    for(var i = 0; i < rowsOperate.length; i++)
		    {
		    	var rowOperate ={};
		        var row = rowsOperate[i];
		        rowOperate.name = row.name;
		        rowOperate.description = row.description;
		        rowOperate.type = row.type;
		        rowOperate.wsDefinition = row.wsDefinition;
		        rowsOperateNew[i] = rowOperate;
		    }

			var param = {};
			param.serviceConfig = rowsConfigNew;
			param.serviceSpec = rowsSpecNew;
			param.servicePerf = rowsPerformanceNew;
			param.serviceOperation = rowsOperateNew;
			param.parentCatalogSid = parentCatalogSid;
			
			var newObj = $.extend(serviceForm,param);
			 Core.AjaxRequest({
					url : ws_url + "/rest/services/platform/insertService",
					params :newObj,
					cache: false,
					callback : function (data) {
						Core.alert({
							title:"提示",
							message:"服务新增成功！",
							callback:function(){ 
								var tab_index = $('#jqxTabsDefine').jqxTabs('selectedItem'); 
								
								$('#jqxTabsDefine').jqxTabs('removeAt', tab_index);
								
								//model.refreshServiceMgtContent(parentCatalogSid);
								Core.AjaxRequest({
									url : ws_url + "/rest/services/platform/findServiceCatalogBySid",
									params : {
										"catalogSid" : parentCatalogSid,
										},
									type : 'post',
									callback : function(data) {
										Core.AjaxRequest({
											url : ws_url + "/rest/services/platform/findServiceDefineByParams",
								 			type:'post',
								 			params:{
								 				parentCatalogSid : data.catalogSid
								 			},
								 			async:false,
								 			callback : function (data) {
								 				model.itemsServiceDefine(data);
									 		     
						    	 				var sourcedatagrid ={
						     			              datatype: "json",
						     			              localdata: model.itemsServiceDefine
						    	 			    };
						    	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
						    	 			    $("#jqxgridDefine").jqxGrid({source: dataAdapter});
						    	 			    
						    	 			   var selectIndex = $('#jqxgridDefine').jqxGrid('getselectedrowindex');
						    	 			   	$("#defineAddBtn").jqxButton({disabled: false });
						    	 			   	if (selectIndex >= 0) {
							    	 			   	$("#defineUpdateBtn").jqxButton({disabled: false });
							    					$("#defineDetailBtn").jqxButton({disabled: false });
							    					$("#defineTemplateMgtBtn").jqxButton({disabled: false });
							    					$("#defineDeployBtn").jqxButton({disabled: false });
							    					$("#defineDisableBtn").jqxButton({disabled: false });
						    	 			   	} else {
							    	 			   	$("#defineUpdateBtn").jqxButton({disabled: true });
							    					$("#defineDetailBtn").jqxButton({disabled: true });
							    					$("#defineTemplateMgtBtn").jqxButton({disabled: true });
							    					$("#defineDeployBtn").jqxButton({disabled: true });
							    					$("#defineDisableBtn").jqxButton({disabled: true });
						    	 			   	}
									 		     
								 			}
								 		 });
									}
								});
								
							}
						});
				    },
				    failure:function(data){
				    	Core.alert({
							title:"提示",
							message:"服务新增失败！",
							callback:function(){
								//$("#" + addTempWindow).jqxWindow('close');
								var tab_index = $('#jqxTabsDefine').jqxTabs('selectedItem'); 
								
								$('#jqxTabsDefine').jqxTabs('removeAt', tab_index);
							}
						});
				    }
				});
		 });
		
	/********************************************************保存服务***********************************************************/

	/********************************************************重置服务***********************************************************/
		$("#resetServiceBtn").click(function () {
			$("#add-serviceName").val("");
            $("#add-description").val("");
            
            //清除元素值
            $("#add-configName").val("");
            $("#add-configDescription").val("");
            $("#add-configDataType").val("");
            $("#add-configUnit").val("");
            $("#add-configValue").val("");
            $('#vAddConfigGrid').jqxGrid('clear');

	        //清除元素值
            $("#add-specName").val("");
            $("#add-specDescription").val("");
            $("#add-specDataType").val("");
            $("#add-specUnit").val("");
            $("#add-specValueDomain").val("");
            $('#vAddSpecGrid').jqxGrid('clear');

            //清除元素值
            $("#add-performanceName").val("");
            $("#add-performanceDescription").val("");
            $("#add-performanceDataType").val("");
            $("#add-performanceUnit").val("");
            $("#add-performanceValueDomain").val("");
            $("#add-performancePerfGroup").val("");
            $("#add-performanceDisplayStyle").val("");
            $('#vAddPerformanceGrid').jqxGrid('clear');

			//清除元素值
            $("#add-operateName").val("");
            $("#add-operateWsDefinition").val("");
            $("#add-operateType").val("");
            $("#add-operateDescription").val("");
            $('#vAddOperateGrid').jqxGrid('clear');
		});

	/********************************************************重置服务***********************************************************/
		// 服务类型
		$("#add-serviceType").on('select', function (event){
		    var args = event.args;
		    if (args) {
			    var item = args.item;
			    var value = item.value;
			    if("02" == value){
			    	var codeadd1 = new codeModel({width : 350, dropDownWidth : 350, isCheckboxes:true,autoDropDownHeight : true});
					codeadd1.getCustomCode("add-relation-service", "/services/findAll","serviceName","serviceSid", false,"POST",{serviceStatus:"03",serviceType:"01"});
			    	$("#add-relation-service").jqxDropDownList({ disabled: false });
			    }else{
			    	var codeadd1 = new codeModel({width : 350, dropDownWidth : 350, isCheckboxes:true,autoDropDownHeight : true});
					codeadd1.getCustomCode("add-relation-service", "/services/findAll","serviceName","serviceSid", false,"POST",{serviceStatus:"03",serviceType:"01"});
					$("#add-relation-service").jqxDropDownList({ disabled: true });
			    }
			}                        
		});
});	
</script>
