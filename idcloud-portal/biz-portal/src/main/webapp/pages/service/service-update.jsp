<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Expires" CONTENT="-1"> 

<div id="<%=request.getParameter("navigationBarIdUpdate")%>" style="width:99.5%;height:80%;margin-left:0.3%;margin-top:0.3%;">
	<div>服务基本信息</div>
	<div>
		<div id="updateServiceForm" style="margin-top: 5px;height: 95%; width: 100%; font-size: 14px; overflow: hidden;">
			<input type="hidden" id="serviceSid" value="<%=request.getParameter("serviceSid")%>" /> 
			<input type="hidden" id="navigationBarIdUpdate" value="<%=request.getParameter("navigationBarIdUpdate")%>" />
			<table class="viewTable" width="100%" cellpadding="1px" cellspacing="0">
				<tr>
					<td align="right" width="150px"><font style="color: red">*</font>服务名称：</td>
					<td align="left"><input type="text" data-name="serviceName" id="update-serviceName" /></td>
				</tr>
				<tr>
					<td align="right" width="150px">描述：</td>
					<td align="left"><textarea data-name="description" id="update-description"></textarea></td>
				</tr>
				<tr>
					<td align="right" width="150px">服务类型：</td>
					<td align="left"><div data-name="serviceType" id="update-serviceType"></div></td>
				</tr>
				<tr>
					<td align="right" width="150px">关联服务：</td>
					<td align="left">
						<div data-name="relationService" id="update-relation-service" style="float:left;"></div>
						<div style="height:22px;float:left;color:green;font-size:12px;line-height:22px;">※ 当服务类型是复合服务时，可以关联多个原子服务</div>
					</td>
				</tr>
				<tr>
					<td align="right" width="150px">所属服务名称：</td>
					<td align="left"><input type="text" data-name="parentCatalogName" id="update-parentCatalogName" value="<%=request.getParameter("parentCatalogName")%>"/></td>
				</tr>
				<tr>
					<td align="right" width="150px">状态：</td>
					<td align="left"><div data-name="serviceStatus" id="update-serviceStatus"></div></td>
				</tr>
				<!-- sunyu add for Fun#193 -->
				<tr>
					<td align="right" width="150px">服务适用范围：</td>
					<td align="left"><div data-name="serviceScope" id="update-serviceScope"></div></td>
				</tr>
				<tr>
					<td align="right" width="150px">是否订购：</td>
					<td align="left"><div data-name="canOrder" id="update-canOrder"></div></td>
				</tr>
				<!-- end -->
			</table>
		</div>
	</div>
	<div>服务配置</div>
	<div>
		<div id="vUpdateConfigGrid" style="height: 60%; width: 99%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
		 <div id="updateConfigWindow">
            <div>更新服务配置</div>
            <div id="updateConfigForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>配置名称:</td>
                        <td align="left"><input type="text" data-name="name" id="update-configName"/></td>
                        
                        <td align="right">数据类型:</td>
                        <td align="left">
                           <div data-name="dataType" id="update-configDataType"></div>
                        </td>
                    </tr>
                    <tr>
 						<td align="right"><font style="color:red">*</font>配置取值:</td>
                        <td align="left" ><input type="text" data-name="value" id="update-configValue"/></td>
                        
                        <td align="right"><font style="color:red">*</font>配置单位:</td>
                        <td align="left"><input type="text" data-name="unit" id="update-configUnit"/></td>
                    </tr>
                    <tr>             
                        <td align="right">配置描述:</td>
                        <td align="left" colspan="3"><textarea data-name="description" id="update-configDescription"></textarea></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
	                        <input style="margin-right: 5px;" data-bind='click: updateConfigSubmit' type="button" id="saveUpdateConfig" value="保存" />
	                        <input id="cancelUpdateConfig" type="button" value="取消" />
                        </td>
                    </tr>
                </table>
            </div>
       </div>
	</div>
	<div>服务规格</div>
	<div>
		<div id="vUpdateSpecGrid" style="height: 60%; width: 99%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
		<div id="updateSpecWindow">
            <div>更新服务规格</div>
            <div id="updateSpecForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>规格名称:</td>
                        <td align="left"><input type="text" data-name="name" id="update-specName"/></td>
                        
                        <td align="right">数据类型:</td>
                        <td align="left">
                           <div data-name="dataType" id="update-specDataType"></div>
                        </td>
                    </tr>
                    <tr>
 						<td align="right"><font style="color:red">*</font>规格取值:</td>
                        <td align="left" ><input type="text" data-name="valueDomain" id="update-specValueDomain"/></td>
                        
                        <td align="right"><font style="color:red">*</font>规格单位:</td>
                        <td align="left"><input type="text" data-name="unit" id="update-specUnit"/></td>
                    </tr>
                    <tr>             
                        <td align="right">规格说明:</td>
                        <td align="left" colspan="3"><textarea data-name="description" id="update-specDescription"></textarea></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" data-bind='click: updateSpecSubmit' type="button" id="saveUpdateSpec" value="保存" />
                        <input id="cancelUpdateSpec" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>
	</div>
	<div>性能指标</div>
	<div>
		<div id="vUpdatePerformanceGrid" style="height: 60%; width: 99%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
		<div id="updatePerformanceWindow">
            <div>更新服务性能</div>
            <div id="updatePerformanceForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>性能名称:</td>
                        <td align="left"><input type="text" data-name="name" id="update-performanceName"/></td>
                        
                        <td align="right">数据类型:</td>
                        <td align="left">
                           <div data-name="dataType" id="update-performanceDataType"></div>
                        </td>
                    </tr>
                    <tr>
 						<td align="right"><font style="color:red">*</font>性能取值:</td>
                        <td align="left" ><input type="text" data-name="valueDomain" id="update-performanceValueDomain"/></td>
                        
                        <td align="right"><font style="color:red">*</font>性能单位:</td>
                        <td align="left"><input type="text" data-name="unit" id="update-performanceUnit"/></td>
                    </tr>
                      <tr>
 						<td align="right"><font style="color:red">*</font>性能分组:</td>
                        <td align="left" ><input type="text" data-name="perfGroup" id="update-performancePerfGroup"/></td>
                        
                        <td align="right"><font style="color:red">*</font>显示样式:</td>
                        <td align="left"><input type="text" data-name="displayStyle" id="update-performanceDisplayStyle"/></td>
                    </tr>
                    <tr>             
                        <td align="right">性能说明:</td>
                        <td align="left" colspan="3"><textarea data-name="description" id="update-performanceDescription"></textarea></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" data-bind='click: updatePerformanceSubmit' type="button" id="saveUpdatePerformance" value="保存" />
                        <input id="cancelUpdatePerformance" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>
	</div>
	<div>服务操作项</div>
	<div>
		<div id="vUpdateOperateGrid" style="height: 60%; width: 99%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
		<div id="updateOperateWindow">
            <div>更新服务操作</div>
            <div id="updateOperateForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>操作名称:</td>
                        <td align="left"><input type="text" data-name="name" id="update-operateName"/></td>
                        <td align="right">数据类型:</td>
                        <td align="left">
                           <div data-name="type" id="update-operateType"></div>
                        </td>
                    </tr>
                    <tr>
 						<td align="right"><font style="color:red">*</font>操作定义:</td>
                        <td align="left" colspan="3"><input type="text" data-name="wsDefinition" id="update-operateWsDefinition"/></td>
                    </tr>
                    <tr>             
                        <td align="right">操作描述:</td>
                        <td align="left" colspan="3"><textarea data-name="description" id="update-operateDescription"></textarea></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" data-bind='click: updateOperateSubmit' type="button" id="saveUpdateOperate" value="保存" />
                        <input id="cancelUpdateOperate" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>
	</div>
</div>
<div style="text-align: right;">
	<input id="updateServiceBtn" data-bind='click: updateServiceMgtSubmit' type="button" value="保存" /> 
	<input id="resetUpdateServiceBtn" type="button" value="重置" />&nbsp;
</div>
<script type="text/javascript">
	$(function(){
		var navigationBarIdUpdate = '<%=request.getParameter("navigationBarIdUpdate")%>';
		var parentCatalogSid = '<%=request.getParameter("parentCatalogSid")%>';
		var serviceSid = '<%=request.getParameter("serviceSid")%>';
		var parentCatalogName = '<%=request.getParameter("parentCatalogName")%>';

		$("#" + navigationBarIdUpdate).jqxNavigationBar({
			theme: currentTheme,
			width : "99.5%",
			height : "370"
		});
		
		var model = new serviceMgtModel();
		model.initUpdateServiceInfo(serviceSid, parentCatalogName);
		//model.initUpdateServicePopWindow();
		//model.initUpdateMgtValidator();
		
		// sunyu
		// 配置服务
		$("#updateConfigWindow").jqxWindow({
            width: 500, 
            height:200,
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#cancelUpdateConfig"), 
            modalOpacity: 0.3           
	     });
		
		$("#updateConfigBtn").click(function() {
			var rowindex = $('#vUpdateConfigGrid').jqxGrid('getselectedrowindex');
			if (rowindex >= 0) {
				
				var data = $('#vUpdateConfigGrid').jqxGrid('getrowdata', rowindex);
				$("#update-configName").val(data.name);
				$("#update-configDataType").val(data.dataType);
				$("#update-configValue").val(data.value);
				$("#update-configUnit").val(data.unit);
				$("#update-configDescription").val(data.description);
				
				// 初始化新增window位置
				var windowW = $(window).width();
				var windowH = $(window).height();
				$("#updateConfigWindow").jqxWindow({
					position : {
						x : (windowW - 500) / 2,
						y : (windowH - 200) / 2
					}
				});
				$("#updateConfigWindow").jqxWindow('open');
				
			} else {
				return;
			}
			
		});
		
		$("#saveUpdateConfig").click(function () {
			$("#updateConfigForm").jqxValidator('validate');	
		});
		
		$("#updateConfigForm").jqxValidator({
			rules : [ {input : '#update-configName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#update-configName', message : '配置名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
			          {input : '#update-configValue', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#update-configValue', message : '配置值为0-256个字符!', action : 'keyup, blur', rule : 'length=0,256' },
			          {input : '#update-configUnit', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#update-configUnit', message : '配置单位为0-16个字符!', action : 'keyup, blur', rule : 'length=0,16' },
			        ]
		});
		
		// 新增用户表单验证成功
		$("#updateConfigForm").on('validationSuccess', function (event) {
			var data = $('#vUpdateConfigGrid').jqxGrid('getrowdata', rowindex);
			var temp = Core.parseJSON($("#updateConfigForm").serializeJson());
			var rowindex = $('#vUpdateConfigGrid').jqxGrid('getselectedrowindex');
			
			rowid = $('#vUpdateConfigGrid').jqxGrid('getrowid', rowindex);
			
			var row = { configSid: data.configSid,name: $("#update-configName").val(), description: $("#update-configDescription").val(), dataType: $("#update-configDataType").val(),
					unit: $("#update-configUnit").val(), value: $("#update-configValue").val()};
            //$('#vUpdateConfigGrid').jqxGrid('addrow', "index", row);
            $("#vUpdateConfigGrid").jqxGrid('updaterow', rowid, row);
            //清除元素值
            $("#update-configName").val("");
            $("#update-configDescription").val("");
            $("#update-configDataType").val("");
            $("#update-configUnit").val("");
            $("#update-configValue").val("");

            //关闭弹出框
            $("#updateConfigWindow").jqxWindow('close');
		 });
		
		//服务规格
		$("#updateSpecWindow").jqxWindow({
            width: 500, 
            height:200,
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#cancelUpdateSpec"),
            theme: currentTheme,
            modalOpacity: 0.3           
	     });
		
		$("#updateSpecBtn").click(function() {
			var rowindex = $('#vUpdateSpecGrid').jqxGrid('getselectedrowindex');
			if (rowindex >= 0) {
				var data = $('#vUpdateSpecGrid').jqxGrid('getrowdata', rowindex);
				$("#update-specName").val(data.name);
				$("#update-specDataType").val(data.dataType);
				$("#update-specValueDomain").val(data.valueDomain);
				$("#update-specUnit").val(data.unit);
				$("#update-specDescription").val(data.description);
				
				

				
				// 初始化更新window位置
				var windowW = $(window).width();
				var windowH = $(window).height();
				$("#updateSpecWindow").jqxWindow({
					position : {
						x : (windowW - 500) / 2,
						y : (windowH - 200) / 2
					}
				});
				$("#updateSpecWindow").jqxWindow('open');
				
			} else {
				return;
			}
			
			
			
		});
		
		$("#saveUpdateSpec").click(function () {
			$("#updateSpecForm").jqxValidator('validate');	
		});
		
		$("#updateSpecForm").jqxValidator({
			rules : [ {input : '#update-specName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#update-specName', message : '规格名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
			          {input : '#update-specValueDomain', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#update-specValueDomain', message : '规格值为0-256个字符!', action : 'keyup, blur', rule : 'length=0,256' },
			          {input : '#update-specUnit', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#update-specUnit', message : '规格单位为0-16个字符!', action : 'keyup, blur', rule : 'length=0,16' },
			        ]
		});
		
		$("#updateSpecForm").on('validationSuccess', function (event) {
			
			
			var data = $('#vUpdateSpecGrid').jqxGrid('getrowdata', rowindex);
			var temp = Core.parseJSON($("#updateSpecForm").serializeJson());
			var rowindex = $('#vUpdateSpecGrid').jqxGrid('getselectedrowindex');
			
			rowid = $('#vUpdateSpecGrid').jqxGrid('getrowid', rowindex);
			
			
			var row = { specSid: data.specSid,name: $("#update-specName").val(), description: $("#update-specDescription").val(), dataType: $("#update-specDataType").val(),
					unit: $("#update-specUnit").val(), valueDomain: $("#update-specValueDomain").val()};
            //$('#vUpdateSpecGrid').jqxGrid('addrow', "index", row);
            $("#vUpdateSpecGrid").jqxGrid('updaterow', rowid, row);
            //清除元素值
            $("#update-specName").val("");
            $("#update-specDescription").val("");
            $("#update-specDataType").val("");
            $("#update-specUnit").val("");
            $("#update-specValueDomain").val("");
            //关闭弹出框
            $("#updateSpecWindow").jqxWindow('close');
		 });
		
		//服务性能指标
		$("#updatePerformanceWindow").jqxWindow({
            width: 500, 
            height:220,
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#cancelUpdatePerformance"), 
            modalOpacity: 0.3           
	     });
		
		$("#updatePerformanceBtn").click(function() {
			var rowindex = $('#vUpdatePerformanceGrid').jqxGrid('getselectedrowindex');
			if (rowindex >= 0) {
				
				var data = $('#vUpdatePerformanceGrid').jqxGrid('getrowdata', rowindex);
				$("#update-performanceName").val(data.name);
				$("#update-performanceDataType").val(data.dataType);
				$("#update-performanceValueDomain").val(data.valueDomain);
				$("#update-performanceUnit").val(data.unit);
				$("#update-performancePerfGroup").val(data.perfGroup);
				$("#update-performanceDisplayStyle").val(data.displayStyle);
				$("#update-performanceDescription").val(data.description);
				
				// 初始化新增window位置
				var windowW = $(window).width();
				var windowH = $(window).height();
				$("#updatePerformanceWindow").jqxWindow({
					position : {
						x : (windowW - 500) / 2,
						y : (windowH - 220) / 2
					}
				});
				$("#updatePerformanceWindow").jqxWindow('open');
			} else {
				return;
			}
			
		});
		
		$("#saveUpdatePerformance").click(function () {
			$("#updatePerformanceForm").jqxValidator('validate');	
		});
		
		$("#updatePerformanceForm").jqxValidator({
			rules : [ {input : '#update-performanceName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#update-performanceName', message : '性能名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
			          {input : '#update-performanceValueDomain', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#update-performanceValueDomain', message : '性能值为0-256个字符!', action : 'keyup, blur', rule : 'length=0,256' },
			          {input : '#update-performanceUnit', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#update-performanceUnit', message : '性能单位为0-16个字符!', action : 'keyup, blur', rule : 'length=0,16' },
			          {input : '#update-performancePerfGroup', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#update-performancePerfGroup', message : '性能分组值为0-256个字符!', action : 'keyup, blur', rule : 'length=0,16' },
			          {input : '#update-performanceDisplayStyle', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#update-performanceDisplayStyle', message : '显示样式单位为0-16个字符!', action : 'keyup, blur', rule : 'length=0,16' },
			        ]
		});
		
		$("#updatePerformanceForm").on('validationSuccess', function (event) {
			
			var data = $('#vUpdatePerformanceGrid').jqxGrid('getrowdata', rowindex);
			
			var temp = Core.parseJSON($("#updatePerformanceForm").serializeJson());
			
			var rowindex = $('#vUpdatePerformanceGrid').jqxGrid('getselectedrowindex');
			
			rowid = $('#vUpdatePerformanceGrid').jqxGrid('getrowid', rowindex);
			
			var row = { perfSid:data.perfSid,name: $("#update-performanceName").val(), description: $("#update-performanceDescription").val(), dataType: $("#update-performanceDataType").val(),
					unit: $("#update-performanceUnit").val(), valueDomain: $("#update-performanceValueDomain").val(), perfGroup: $("#update-performancePerfGroup").val(), 
					displayStyle: $("#update-performanceDisplayStyle").val()};
            //$('#vUpdatePerformanceGrid').jqxGrid('addrow', "index", row);
            $("#vUpdatePerformanceGrid").jqxGrid('updaterow', rowid, row);
            //清除元素值
            $("#update-performanceName").val("");
            $("#update-performanceDescription").val("");
            $("#update-performanceDataType").val("");
            $("#update-performanceUnit").val("");
            $("#update-performanceValueDomain").val("");
            $("#update-performancePerfGroup").val("");
            $("#update-performanceDisplayStyle").val("");

            //关闭弹出框
            $("#updatePerformanceWindow").jqxWindow('close');
		 });
		
		//服务操作
		$("#updateOperateWindow").jqxWindow({
            width: 500, 
            height:200,
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#cancelUpdateOperate"), 
            modalOpacity: 0.3           
	     });
		
		$("#updateOperateBtn").click(function() {
			var rowindex = $('#vUpdateOperateGrid').jqxGrid('getselectedrowindex');
			if (rowindex >= 0) {
				
				var data = $('#vUpdateOperateGrid').jqxGrid('getrowdata', rowindex);
				$("#update-operateName").val(data.name);
				$("#update-operateType").val(data.type);
				$("#update-operateWsDefinition").val(data.wsDefinition);
				$("#update-operateDescription").val(data.description);
				
				// 初始化新增window位置
				var windowW = $(window).width();
				var windowH = $(window).height();
				$("#updateOperateWindow").jqxWindow({
					position : {
						x : (windowW - 500) / 2,
						y : (windowH - 200) / 2
					}
				});
				$("#updateOperateWindow").jqxWindow('open');
			} else {
				return;
			}
			
		});
		
		$("#saveUpdateOperate").click(function () {
			$("#updateOperateForm").jqxValidator('validate');	
		});
		
		$("#updateOperateForm").jqxValidator({
			rules : [ {input : '#update-operateName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#update-operateName', message : '操作名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
			          {input : '#update-operateWsDefinition', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#update-operateWsDefinition', message : '操作定义值为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
			        ]
		});
		
		// 新增用户表单验证成功
		$("#updateOperateForm").on('validationSuccess', function (event) {
			var data = $('#vUpdateOperateGrid').jqxGrid('getrowdata', rowindex);
			var temp = Core.parseJSON($("#updateOperateForm").serializeJson());
			
			var rowindex = $('#vUpdateOperateGrid').jqxGrid('getselectedrowindex');
			
			rowid = $('#vUpdateOperateGrid').jqxGrid('getrowid', rowindex);
			
			var row = { operationSid:data.operationSid,name: $("#update-operateName").val(), description: $("#update-operateDescription").val(), type: $("#update-operateType").val(),
					wsDefinition: $("#update-operateWsDefinition").val()};
            //$('#vUpdateOperateGrid').jqxGrid('addrow', "index", row);
            $("#vUpdateOperateGrid").jqxGrid('updaterow', rowid, row);
            //清除元素值
            $("#update-operateName").val("");
            $("#update-operateWsDefinition").val("");
            $("#update-operateType").val("");
            $("#update-operateDescription").val("");

            //关闭弹出框
            $("#updateOperateWindow").jqxWindow('close');
		 });
		
		//保存服务
		$("#updateServiceBtn").click(function () {
			$("#updateServiceForm").jqxValidator('validate');	
		});
		
		$("#updateServiceForm").jqxValidator({
			rules : [ {input : '#update-serviceName', message : '不能为空!', action : 'keyup, blur', rule : 'required'	},
			          {input : '#update-serviceName', message : '操作名称为0-64个字符!', action : 'keyup, blur', rule : 'length=0,64' },
			        ]
		});
		
		$("#updateServiceForm").on('validationSuccess', function (event) {
			
			var serviceForm = Core.parseJSON($("#updateServiceForm").serializeJson());
		    //得出的值多出个"",无法直接映射的处理办法。
		    var rowsConfig = $('#vUpdateConfigGrid').jqxGrid('getrows');
		    
		    var rowsConfigNew = [];
		    for(var i = 0; i < rowsConfig.length; i++)
		    {
		    	var rowConfig ={};
		        var row = rowsConfig[i];
		        rowConfig.configSid = row.configSid;
		        rowConfig.name = row.name;
		        rowConfig.description = row.description;
		        rowConfig.dataType = row.dataType;
		        rowConfig.unit = row.unit;
		        rowConfig.value = row.value;
		        rowsConfigNew[i] = rowConfig;
		    }
			
		    var rowsSpec = $('#vUpdateSpecGrid').jqxGrid('getrows');
		    
		    var rowsSpecNew = [];
		    for(var i = 0; i < rowsSpec.length; i++)
		    {
		    	var rowSpec ={};
		        var row = rowsSpec[i];
		        rowSpec.specSid = row.specSid;
		        rowSpec.name = row.name;
		        rowSpec.description = row.description;
		        rowSpec.dataType = row.dataType;
		        rowSpec.unit = row.unit;
		        rowSpec.valueDomain = row.valueDomain;
		        rowsSpecNew[i] = rowSpec;
		    }
		    
		    
		    var rowsPerformance = $('#vUpdatePerformanceGrid').jqxGrid('getrows');
		    
		    var rowsPerformanceNew = [];
		    for(var i = 0; i < rowsPerformance.length; i++)
		    {
		    	var rowPerformance ={};
		        var row = rowsPerformance[i];
		        rowPerformance.perfSid = row.perfSid;
		        rowPerformance.name = row.name;
		        rowPerformance.description = row.description;
		        rowPerformance.dataType = row.dataType;
		        rowPerformance.unit = row.unit;
		        rowPerformance.valueDomain = row.valueDomain;
		        rowPerformance.perfGroup = row.perfGroup;
		        rowPerformance.displayStyle = row.displayStyle;
		        rowsPerformanceNew[i] = rowPerformance;
		    }
		    
		    
		    var rowsOperate = $('#vUpdateOperateGrid').jqxGrid('getrows');
		    
		    var rowsOperateNew = [];
		    for(var i = 0; i < rowsOperate.length; i++)
		    {
		    	var rowOperate ={};
		        var row = rowsOperate[i];
		        rowOperate.operationSid = row.operationSid;
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
			
			param.serviceSid = $("#serviceSid").val();
			
			var newObj = $.extend(serviceForm,param);
			//console.log(JSON.stringify(newObj));
			 Core.AjaxRequest({
					url : ws_url + "/rest/services/platform/updateService",
					params :newObj,
					cache: false,
					showMsg: false,
					callback : function (data) {
						if (data != null) {
							if (data.status = 'success') {
								Core.alert({
									title:"提示",
									message:"更新成功！",
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
							} else {
								Core.alert({
									title:"提示",
									message:"更新失败！",
									callback:function(){
										//$("#" + addTempWindow).jqxWindow('close');
									}
								});
							}
						}
						
				    },
				    failure:function(data){
				    	Core.alert({
							title:"提示",
							message:"更新失败！",
							callback:function(){
								//$("#" + addTempWindow).jqxWindow('close');
							}
						});
				    }
				});
		 });
});	
</script>
