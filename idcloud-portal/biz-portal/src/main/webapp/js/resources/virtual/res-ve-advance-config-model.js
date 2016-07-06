var setVeAdvanceConfigModel = function () {
		var me = this;

	    // 验证新增画面
		this.initValidator = function(){
			$('#setVeAdvanceConfigForm').jqxValidator({
		        rules: [  
		                  // 资源分配公式
		                  { input: '#set-end-port', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#set-end-port', message: '资源分配阀值不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
		                  { input: '#set-end-port', message: '只能输入数字', action: 'keyup, blur', rule: 'number' },
		                  
		                  { input: '#set-start-port', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#set-start-port', message: '资源分配阀值不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
		                  { input: '#set-start-port', message: '只能输入数字', action: 'keyup, blur', rule: 'number' }
			           ]
				});
	    	
			// 新增ip池验证成功
			$('#setVeAdvanceConfigForm').on('validationSuccess', function (event) {
					 var config = Core.parseJSON($("#setVeAdvanceConfigForm").serializeJson());
					 var arr = new Array();
					 var arrName = ["是否开启VNC","VNC连接配置开始端口","VNC连接配置结束端口"];
					 var arrKey = ["open_vnc","vnc_connect_port_start","vnc_connect_port_end"];
					 var arrValue = [config.vnc,config.startPort,config.endPort];
					 for(var i=0;i<3;i++){
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
		 					// 给前端资源环境基本信息赋值
		 					findVeConfigInfo();
		 					$("#setVeAdvanceConfigWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
							$("#setVeAdvanceConfigWindow").jqxWindow('close');
		 			    }
		 			});
		     });
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	$("#setVeAdvanceConfigWindow").jqxWindow({
	            width: 350, 
	            height:160,
	            theme:currentTheme,
	            resizable: false,  
	            isModal: true, 
	            autoOpen: false, 
	            cancelButton: $("#setVeConfigCancel"), 
	            modalOpacity: 0.3,
	            initContent:function(){
	            	// 初始化组件
	                $("#set-start-port").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#set-end-port").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                
	                
	                $("#setVeConfigSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                $("#setVeConfigCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                
	            }
	        });
	    };
	    
	    // 判断操作按钮，初始化按钮
	    this.initAddContent = function(){
	    	// 初始化组件
	        $("#set-start-port").val("");
	        $("#set-end-port").val("");
	    };
 };
 
//弹出新增网络对话框
 function popSetVeAdvanceConfigWindow(){
	    var windowW = $(window).width(); 
	 	var windowH = $(window).height(); 
	 	// 初始化高级设置信息
		$("#set-config-rate").val("");
		$("#set-config-threshold").val("");
		
		var data =[
		           	 {name:"关闭",value:false},
	             	 {name:"开启",value:true}
		          ];
		
		$("#set-open-vnc").jqxDropDownList({
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
 					if(data[i].configKey == "open_vnc"){
 						$("#set-open-vnc").val(data[i].configValue);
	 				}else if(data[i].configKey == "vnc_connect_port_start"){
	 					$("#set-start-port").val(data[i].configValue);
	 				}else if(data[i].configKey == "vnc_connect_port_end"){
	 					$("#set-end-port").val(data[i].configValue);
	 				}
 				}
 			 }
	     });
	 	$("#set-ve-topologysid").val(resTopologySid);
	 	// 清除新增残留数据
	 	$("#setVeAdvanceConfigWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-160)/2 } });
	 	$("#setVeAdvanceConfigWindow").jqxWindow('open');
 }

 // 提交将选择主机加入池
 function submitVeAdvanceConfig(){
 	$('#setVeAdvanceConfigForm').jqxValidator('validate');
 }
  