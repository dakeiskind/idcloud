var addViosHostModel = function () {
	
	// 验证新增画面
	this.initValidator = function(){
    	// 新增VIOS验证
    	$('#addHostViosForm').jqxValidator({
            rules: [  
                      
                      { input: '#add-vios-power-vios-lpar-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#add-vios-power-vios-lpar-name', message: 'VIOS分区名不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
                      
                      { input: '#add-vios-power-cpu-max-units', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#add-vios-power-cpu-min-units', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#add-vios-power-cpu-units', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#add-vios-power-cpu-max-cores', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#add-vios-power-cpu-min-cores', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#add-vios-power-cpu-cores', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#add-vios-power-memory-max', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#add-vios-power-memory-min', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#add-vios-power-memory-size', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      
                      { input: '#add-vios-host-ip', message: '请输入正确的IP地址', action: 'blur',rule: function (input, commit) {
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
    	
	};
	 // 初始化window
    this.initPopWindow = function(){
    	// 新增vios
    	$("#addHostViosWindow").jqxWindow({
            width: 750, 
            height:260,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#addhostViosCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	
            	$("#add-vios-power-vios-lpar-name").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-vios-power-version").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-vios-power-cpu-max-units").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-vios-power-cpu-min-units").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-vios-power-cpu-units").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-vios-power-cpu-max-cores").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-vios-power-cpu-min-cores").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-vios-power-cpu-cores").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	
            	$("#add-vios-power-memory-max").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-vios-power-memory-min").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-vios-power-memory-size").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	
            	$("#add-vios-host-user").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-vios-host-password").jqxPasswordInput({placeHolder: "", height: 23, width: 120, showStrength: false, showStrengthPosition: "right",theme:currentTheme});
            	$("#add-vios-host-ip").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	
            	$("#addhostViosSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
    	    	$("#addhostViosCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            }
        });
    	
    };
};

// 提交新增主机VIOS信息
function submitAddhostViosInfo(){
	
	var isok = $('#addHostViosForm').jqxValidator('validate');
	if(isok){
		var vios = JSON.parse($("#addHostViosForm").serializeJson());
		 Core.AjaxRequest({
			url : ws_url + "/rest/vioss/create",
			params :vios,
			async:false,
			callback : function (data) {
				 // 关闭window
				$("#addHostViosWindow").jqxWindow('close');
				var me = new viosHostModel();
				me.searchViosHostInfo();
		    }
		});
	}
}


