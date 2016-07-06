
var sysConfigModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
    		 configName: "", 
			 configKey : "", 
			 configType : "",
	    };
	    // 用户数据
	    this.searchSystemInfo = function(){
	    	 $("#search-config-type").val(me.searchObj.configType);
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/configs",
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
 			     $("#systemdatagrid").jqxGrid({source:dataAdapter}); 
	 		     //  me.initSystemdatagrid(me.items);
	 			}
	 		 });
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-sys-config").jqxInput({placeHolder: "", height: 23, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-config-key").jqxInput({placeHolder: "", height: 23, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-sys-button").jqxButton({ width: '50',theme:currentTheme});
			
			// 新增租户组件初始化
//			 $("#config-value").jqxInput({placeHolder: "", height: 23, width: 200, minLength: 1,theme:currentTheme});
	         $("#sysConfigSave").jqxButton({ width: '50',theme:currentTheme});
		     $("#sysConfigCancel").jqxButton({ width: '50',theme:currentTheme});
	    	 
	    };
	    this.setEditConfigForm = function(data){
	    	$("#config-name").html(data.configName);
	    	$("#config-key").html(data.configKey);
	    	$("#config-description").html(data.description);
//	    	$("#config-value").jqxInput({value:(data.configValue == null)? "" :data.configValue});
	    };
	    // 初始化验证规则
	    this.initValidator = function(){
	    	$('#sysConfigForm').jqxValidator({
                rules: [
//                          { input: '#config-value', message: '不能为空', action: 'keyup, blur', rule: 'required' },
//                          { input: '#config-value', message: '配置名称不能超过128个字符', action: 'keyup, blur', rule: 'length=1,128' }
                       ]
	    	});
	    	
	    	$('#sysConfigForm').on('validationSuccess', function (event) {
	    		 var sysConfig = Core.parseJSON($("#sysConfigForm").serializeJson());
	    		 Core.AjaxRequest({         
	 				url : ws_url + "/rest/configs/update",
	 				params :sysConfig,
	 				callback : function (data) {
	 					Core.alert({
	 						title:"提示",
	 						message:"编辑系统配置成功！",
	 						callback:function(){
	 							var typeid = $("#configType").val();
	 							me.searchObj.configType = typeid;
	 							me.searchSystemInfo();
	 							$("#editConfigWindow").jqxWindow('close');
	 						}
	 					});
	 			    },
	 			    failure:function(data){
	 			    	Core.alert({
	 						title:"提示",
	 						message:"编辑系统配置失败！",
	 						callback:function(){
	 							$("#editConfigWindow").jqxWindow('close');
	 						}
	 					});
	 			    }
	 			});
   	 });
	    };
	    // 初始化用户datagrid的数据源
	    this.initSystemdatagrid = function(){
	    	 
//	    	 var sourcedatagrid ={
//	              datatype: "json",
//	              localdata: data
//	          };
//	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#systemdatagrid").jqxGrid({
	              width: "100%",
				  theme:currentTheme,
//	              source: dataAdapter,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              sortable: true,
	              autowidth: false,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '配置名称', datafield: 'configName',width:180},
	                  { text: '配置Key', datafield: 'configKey',width:150},
	                  { text: '配置值', datafield: 'configValue'},
	                  { text: '配置描述', datafield: 'description'}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnTenantGroup' style='margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var editConfig = $("<a id='editConfig' onclick='editSystemConfig()' >&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>修改配置&nbsp;&nbsp;</a>");
	                  toolbar.append(container);
	                  container.append(editConfig);
	                  
	    	    	  $("#editConfig").jqxButton({width: 60,height:18,theme:currentTheme, disabled: true });
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#systemdatagrid").on('rowselect', function (event) {
	    		  $("#editConfig").jqxButton({ disabled: false });
	          });
	    };
	    // 初始化弹出window
	    this.initPopWindow = function(){
			$("#editConfigWindow").jqxWindow({
	                width: 500, 
	                height:200,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                theme: currentTheme,
	                cancelButton: $("#sysConfigCancel"), 
	                modalOpacity: 0.3           
	         });
	    };
	   
  };
  
  /** 修改系统配置 */
  this.editSystemConfig = function(){
	  var sys = new sysConfigModel();
	   var rowindex = $('#systemdatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
		    var data = $('#systemdatagrid').jqxGrid('getrowdata', rowindex);
		    var type = data.dataType;
		    if(type!=null&&type!=""){
		    	//当是只能选择true、false的时候
		    	if(type=='boolean'){
		    		$("#valueTd").html("<div id='value-true' style='float:left;'>true</div><div id='value-false' style='float:left;'>false</div>");
		    		$("#value-true").jqxRadioButton({ width: 80});
		            $("#value-false").jqxRadioButton({ width: 80});
		            if(data.configValue=='true'){
		            	$('#value-true').jqxRadioButton('check');
		            	 $("#config-value").val("true");
		            }else{
		            	$('#value-false').jqxRadioButton('check');
		            	$("#config-value").val("false");
		            }
		            $('#value-true').on('checked', function (event) {
		                $("#config-value").val("true");
		            });
		            $('#value-false').on('checked', function (event) {
		            	$("#config-value").val("false");
		            });
		    	}
		    	//如果是填写邮箱的
		    	else if(type=='email'){
		    		$("#valueTd").html("<input type='text' id='emailReg' />");
		    		$("#emailReg").jqxInput({placeHolder: "", height: 23, width: 200, minLength: 1,theme:currentTheme});
	            	$('#emailReg').val(data.configValue);
	            	$("#config-value").val(data.configValue);
		            $('#emailReg').on('change', function (event) {
		            	var value = $('#emailReg').val();
		            	$("#config-value").val(value);
		            });
		            $('#sysConfigForm').jqxValidator({
		            	rules: [
		            	        { input: '#emailReg', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		            	        { input: '#emailReg', message: '请输入正确的邮箱', action: 'keyup, blur', rule: function (input, commit) {
	            	        	var reg = /^(([\w-]+(\.[\w-]+)*@[\w-]+((\.[\w-]+\.[\w-]+[;]{0,1})|(\.[\w-]+[;]{0,1})))*)+$/;
	//	            	        	var reg = /^[A-Za-z0-9]+([-_.][A-Za-z0-9]+)*@([A-Za-z0-9]+[-.])+[A-Za-z0-9]{2,5}$/;
		            	        	if (reg.test(input.val())) {
		            	        		return true;
		            	        	}
		            	        	return false;
		            	        }}
		            	        ]
		            });
		    	}
		    	//如果是填写数值
		    	else if(type=='long'){
		    		$("#valueTd").html("<div id='longValue'></div>");
		    		$("#longValue").jqxNumberInput({height: 23, width: 200,min:0, decimal: 0,decimalDigits:1,spinButtons: true,inputMode: 'simple',theme:currentTheme});
	            	$('#longValue').val(data.configValue);
	            	$("#config-value").val(data.configValue);
		            $('#longValue').on('change', function (event) {
		            	var value = $('#longValue').val();
		            	$("#config-value").val(value);
		            });
		    	}
		    	else{
			    	$("#valueTd").html("<input type='text' id='value-tmp' />");
		    		$("#value-tmp").jqxInput({placeHolder: "", height: 23, width: 200, minLength: 1,theme:currentTheme});
	            	$('#value-tmp').val(data.configValue);
	            	$("#config-value").val(data.configValue);
		            $('#value-tmp').on('change', function (event) {
		            	var value = $('#value-tmp').val();
		            	$("#config-value").val(value);
		            });
			    }
		    }else{
		    	$("#valueTd").html("<input type='text' id='value-tmp' />");
	    		$("#value-tmp").jqxInput({placeHolder: "", height: 23, width: 200, minLength: 1,theme:currentTheme});
            	$('#value-tmp').val(data.configValue);
            	$("#config-value").val(data.configValue);
	            $('#value-tmp').on('change', function (event) {
	            	var value = $('#value-tmp').val();
	            	$("#config-value").val(value);
	            });
		    }
		    $("#configSid").val(data.configSid);
		    $("#configType").val(data.configType);
		    sys.setEditConfigForm(data);
		    var windowW = $(window).width(); 
	    	var windowH = $(window).height(); 
	    	$("#editConfigWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-200)/2 } });
	    	$("#editConfigWindow").jqxWindow('open');
   	}
  };
  /** 提交修改系统配置 */
  function sysConfigSave(){
	   $('#sysConfigForm').jqxValidator('validate');
  };
  
  /** 查询系统配置 */
  function searchSysConfig(type){
	   var sys = new sysConfigModel();
	   sys.searchObj.configName = $("#search-sys-config").val();
       sys.searchObj.configKey = $("#search-config-key").val();
       sys.searchObj.configType = $("#search-config-type").val();
	   sys.searchSystemInfo();
  };
  
  
  