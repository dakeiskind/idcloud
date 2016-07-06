var setPcAdvanceConfigModel = function () {
		var me = this;

	    // 验证新增画面
		this.initValidator = function(){
			$('#setPcAdvanceConfigForm').jqxValidator({
		        rules: [  
		                  // 资源分配公式
		                  { input: '#set-config-rate', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#set-config-rate', message: '资源分配比率不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
		                  { input: '#set-config-rate', message: '资源分配比率只能输入数字', action: 'keyup, blur', rule: 'number' },
		                  
		                  { input: '#set-config-threshold', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#set-config-threshold', message: '资源分配阀值不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
			           ]
				});
	    	
			// 新增ip池验证成功
			$('#setPcAdvanceConfigForm').on('validationSuccess', function (event) {
					 var config = Core.parseJSON($("#setPcAdvanceConfigForm").serializeJson());
					 var arr = new Array();
					 var arrName = ["资源分配策略","资源分配方式","资源分配比率","资源可分配阀值"];
					 var arrKey = ["allocation_policy","allocation_mode","allocation_rate","allocation_threshold"];
					 var arrValue = [config.policy,config.mode,config.rate,config.threshold];
					 for(var i=0;i<4;i++){
						 var obj = new Object();
						 obj.configName = arrName[i];
						 obj.configKey = arrKey[i];
						 obj.configValue = arrValue[i];
					     obj.resTopologySid = resTopologySid;
					     arr.push(obj);
					 }
					 
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/topologyConfigs/create",
		 				params :arr,
		 				callback : function (data) {
		 					findPcConfigInfo();
		 					$("#setPcAdvanceConfigWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
							$("#setPcAdvanceConfigWindow").jqxWindow('close');
		 			    }
		 			});
		     });
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	$("#setPcAdvanceConfigWindow").jqxWindow({
	            width: 300, 
	            height:160,
	            theme:currentTheme,
	            resizable: false,  
	            isModal: true, 
	            autoOpen: false, 
	            cancelButton: $("#setConfigCancel"), 
	            modalOpacity: 0.3,
	            initContent:function(){
	            	// 初始化组件
	                $("#set-config-rate").jqxInput({placeHolder: "", height: 22, width: 75, minLength: 1,theme:currentTheme});
	                $("#set-config-threshold").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	               
	                $("#setConfigSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                $("#setConfigCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                
	            }
	        });
	    };
	    
	    // 判断操作按钮，初始化按钮
	    this.initAddContent = function(){
	    	// 初始化组件
	        $("#set-config-formula").val("");
	        $("#set-config-threshold").val("");
	       };
 };
 
//弹出
 function popSetAdvanceConfigWindow(){
	    var windowW = $(window).width(); 
	 	var windowH = $(window).height(); 
	 	// 初始化高级设置信息
	 	var codesearch = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth:150,async:false});
 		codesearch.getCommonCode("set-config-policy","ALLOCATION_POLICY",false);
		$("#set-config-rate").val("");
		$("#set-config-threshold").val("");
		if("PCX" == resTopologyType || "PCVX" == resTopologyType || "PCP" == resTopologyType || "PCVP" == resTopologyType){
			var data =[
			           	 {name:"按内存",value:"M"},
		             	 {name:"按CPU",value:"C"}
			          ];
		}else{
			var data =[
			           	 {name:"按容量",value:"C"}
			          ];
		}
		
		$("#set-config-mode").jqxDropDownList({
            selectedIndex: 0, 
            source: data, 
            displayMember: "name", 
            valueMember: "value", 
            width: 60, 
            height: 22,
            autoDropDownHeight: true,
            theme:currentTheme
        });
		
	 	// 如果已经存在了配置项，就赋值
	 	Core.AjaxRequest({
 			url : ws_url + "/rest/topologyConfigs",
 			type:'post',
 			params:{
 				resTopologySid : resTopologySid
 			},
 			async:false,
 			callback : function (data) {
 				for(var i=0;i<data.length;i++){
 					if(data[i].configKey == "allocation_policy"){
	 					$("#set-config-policy").val(data[i].configValue);
	 				}else if(data[i].configKey == "allocation_mode"){
	 					$("#set-config-mode").val(data[i].configValue);
	 				}else if(data[i].configKey == "allocation_rate"){
	 					$("#set-config-rate").val(data[i].configValue);
	 				}else if(data[i].configKey == "allocation_threshold"){
	 					$("#set-config-threshold").val(data[i].configValue);
	 				}
 				}
 			 }
	     });
	 	$("#set-config-topologysid").val(resTopologySid);
	 	// 清除新增残留数据
	 	$("#setPcAdvanceConfigWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-160)/2 } });
	 	$("#setPcAdvanceConfigWindow").jqxWindow('open');
 }

 // 提交将选择主机加入池
 function submitAdvanceConfig(){
 	$('#setPcAdvanceConfigForm').jqxValidator('validate');
 }
  