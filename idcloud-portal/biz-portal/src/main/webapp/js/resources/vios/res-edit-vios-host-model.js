var editViosHostModel = function () {
	
	// 验证新增画面
	this.initValidator = function(){
    	
    	// 编辑VIOS验证
    	$('#editHostViosForm').jqxValidator({
            rules: [  
					  { input: '#edit-vios-power-vios-lpar-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
					  { input: '#edit-vios-power-vios-lpar-name', message: 'VIOS分区名不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
                      
					  { input: '#edit-vios-power-cpu-max-units', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#edit-vios-power-cpu-min-units', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#edit-vios-power-cpu-units', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#edit-vios-power-cpu-max-cores', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#edit-vios-power-cpu-min-cores', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#edit-vios-power-cpu-cores', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#edit-vios-power-memory-max', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#edit-vios-power-memory-min', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#edit-vios-power-memory-size', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      
                      { input: '#edit-vios-host-ip', message: '请输入正确的IP地址', action: 'blur',rule: function (input, commit) {
	                	  if(input.val() == null || input.val() == ""){
              	  				return true;
              	  			}else{
              	  			    var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	                       	    if(!pattern.test(input.val())){
                 	  				return false;
                 	  			}else{
                 	  				return true;
                 	  			}
	              	  		}
              	  		}
	                  }
                   ]
    	});
    	
    	// 编辑主机VIOS验证
    	$('#editHostViosForm').on('validationSuccess', function (event) {
	    		 var vios = JSON.parse($("#editHostViosForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/vioss/update",
	 				params :vios,
	 				callback : function (data) {
	 					 // 关闭window
	 					$("#editHostViosWindow").jqxWindow('close');
	 					var viosHost = new viosHostModel();
	 					viosHost.searchViosHostInfo();
	 			    }
	 			});
    	 });
    	
	};
	 // 初始化window
    this.initPopWindow = function(){
    	
    	// 编辑vios
    	$("#editHostViosWindow").jqxWindow({
            width: 700, 
            height:225,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#edithostViosCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	
            	$("#edit-vios-power-vios-lpar-name").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-vios-power-version").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	
            	$("#edit-vios-power-cpu-max-units").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-vios-power-cpu-min-units").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-vios-power-cpu-units").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-vios-power-cpu-max-cores").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-vios-power-cpu-min-cores").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-vios-power-cpu-cores").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	
            	$("#edit-vios-power-memory-max").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-vios-power-memory-min").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-vios-power-memory-size").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	
            	$("#edit-vios-host-user").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-vios-host-password").jqxPasswordInput({placeHolder: "", height: 23, width: 120, showStrength: false, showStrengthPosition: "right",theme:currentTheme});
            	$("#edit-vios-host-ip").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	
            	$("#edithostViosSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
    	    	$("#edithostViosCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            }
        });
    };
};

// 提交编辑主机VIOS信息
function submitEdithostViosInfo(){
	$('#editHostViosForm').jqxValidator('validate');
}
