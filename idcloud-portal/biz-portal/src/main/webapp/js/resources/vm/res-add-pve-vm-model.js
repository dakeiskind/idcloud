// 新增pveVm
var addPveVmModel = function () {
	
	    // 验证新增画面
		this.initValidator = function(){
	    	$('#addPveVmForm').jqxValidator({
	            rules: [  
	                      { input: '#add-pve-vmName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-pve-vmName', message: '分区名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
	                      
	                      { input: '#add-pve-parId', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-pve-parId', message: '分区编号不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
	                      
	                      { input: '#add-pve-parProfile', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-pve-parProfile', message: '分区配置不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
	                      
	                      { input: '#add-pve-powerCpuUsedUnits', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-pve-powerCpuUsedUnits', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#add-pve-powerCpuMinUnits', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-pve-powerCpuMinUnits', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#add-pve-powerCpuMaxUnits', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-pve-powerCpuMaxUnits', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
	                      
//	                      { input: '#add-pve-cpuCores', message: '不能为空', action: 'keyup, blur', rule: 'required' },
//	                      { input: '#add-pve-cpuCores', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
//	                      
//	                      { input: '#add-pve-cpuCoresMin', message: '不能为空', action: 'keyup, blur', rule: 'required' },
//	                      { input: '#add-pve-cpuCoresMin', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
//	                      
//	                      { input: '#add-pve-cpuCoresMax', message: '不能为空', action: 'keyup, blur', rule: 'required' },
//	                      { input: '#add-pve-cpuCoresMax', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#add-pve-memorySize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-pve-memorySize', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#add-pve-memoryMin', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-pve-memoryMin', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#add-pve-memoryMax', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-pve-memoryMax', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#add-pve-vmIp', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-pve-vmIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
	                    	  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	                    	  if(!pattern.test(input.val())){
                  	  				return false;
                  	  			}else{
                  	  				return true;
                  	  			}
                  	  		}
		                  },
		                  
		                  { input: '#add-pve-managementAccount', message: '管理用户名不能超过36个字符', action: 'blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
                  	  				return true;
                  	  			}else{
                  	  				if(input.val().length > 36){
                  	  					return false;
                  	  				}else{
                  	  					return true;
                  	  				}
                  	  			}
                  	  		}
		                  },
		                  
		                  { input: '#add-pve-managementPassword', message: '管理用户密码不能超过36个字符', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
                	  				return true;
                	  			}else{
                	  				if(input.val().length > 36){
                	  					return false;
                	  				}else{
                	  					return true;
                	  				}
                	  			}
                	  		}
		                  }
		                  
	                   ]
	    	});
	    	
	    	// 新增主机验证成功
	    	$('#addPveVmForm').on('validationSuccess', function (event) {
	    			 
		    		 var pveVmdata = JSON.parse($("#addPveVmForm").serializeJson());
		    		 
		    		 // 判断下拉框是否有值
		    		 if($("#add-pve-allocateResHostSid").val() == null || $("#add-pve-allocateResHostSid").val() == ""){
		    			 Core.alert({
		    				  title:"提示",
		    				  message:'主机不能为空！'
		    			 });
		    			 return false;
		    		 }
		    		 
		    		 if($("#add-pve-resCpuPoolSid").val() == null || $("#add-pve-resCpuPoolSid").val() == "全部"){
		    			 Core.alert({
		    				  title:"提示",
		    				  message:'CPU共享资源池不能为空！'
		    			 });
		    			 return false;
		    		 }
		    		 if(pveVmdata.parType ==1){
		    			// 虚拟CPU值
			    		 if($("#add-pve-cpuCores").val() == null || $("#add-pve-cpuCores").val() == ""){
			    			 Core.alert({
			    				  title:"提示",
			    				  message:'虚拟CPU值不能为空！'
			    			 });
			    			 return false;
			    		 }
			    		 // 虚拟CPU最小值
			    		 if($("#add-pve-cpuCoresMin").val() == null || $("#add-pve-cpuCoresMin").val() == ""){
			    			 Core.alert({
			    				  title:"提示",
			    				  message:'虚拟CPU最小值不能为空！'
			    			 });
			    			 return false;
			    		 }
			    		 // 虚拟CPU最大值
			    		 if($("#add-pve-cpuCoresMax").val() == null || $("#add-pve-cpuCoresMax").val() == ""){
			    			 Core.alert({
			    				  title:"提示",
			    				  message:'虚拟CPU最大值不能为空！'
			    			 });
			    			 return false;
			    		 }
		    		 }
		    		 
		    		 console.log(JSON.stringify(pveVmdata));
		    		 
		    		 Core.AjaxRequest({	
		 				url : ws_url + "/rest/vms/create/pveVm",
		 				params :pveVmdata,
		 				async:false,
		 				callback : function (data) {
		 					// 刷新列表
		 					var virPveVm = new virtualPveVMDatagridModel()
		 					virPveVm.searchVMInfo();
		 					
		 					$("#addPveVmWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
	    };
	    
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	// 新增window
	    	$("#addPveVmWindow").jqxWindow({
                width: 800, 
                height:280,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addPveVmCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	  $("#add-pve-vmName").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  $("#add-pve-parId").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  $("#add-pve-parProfile").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  
                	  $("#add-phyhost-cpuCores").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  
                	  $("#add-pve-powerCpuUsedUnits").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  $("#add-pve-powerCpuMinUnits").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  $("#add-pve-powerCpuMaxUnits").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  
                	  $("#add-pve-cpuCores").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  $("#add-pve-cpuCoresMin").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  $("#add-pve-cpuCoresMax").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  
                	  $("#add-pve-memorySize").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  $("#add-pve-memoryMin").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  $("#add-pve-memoryMax").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  
                	  $("#add-pve-osName").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  $("#add-pve-managementAccount").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  
                	  $("#add-pve-managementPassword").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  $("#add-pve-vmIp").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  
        	    	  $("#addPveVmSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
        	    	  $("#addPveVmCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                }
            });
	    };
 };
  
// 弹出新增主机框
 function popAddPveVmWindow(){
	 	// 清除原有数据
	  	$("#add-pve-vmName").val(null);
	  	$("#add-pve-parId").val(null);
	  	$("#add-pve-parProfile").val(null);
	  	$("#add-phyhost-cpuCores").val(null);
	  	$("#add-pve-powerCpuUsedUnits").val(null);
	  	$("#add-pve-powerCpuMinUnits").val(null);
	  	$("#add-pve-powerCpuMaxUnits").val(null);
	  	$("#add-pve-cpuCores").val(null);
	  	$("#add-pve-cpuCoresMin").val(null);
	  	$("#add-pve-cpuCoresMax").val(null);
	  	$("#add-pve-memorySize").val(null);
	  	$("#add-pve-memoryMin").val(null);
	  	$("#add-pve-memoryMax").val(null);
	  	$("#add-pve-osName").val(null);
	  	$("#add-pve-managementAccount").val(null);
	  	$("#add-pve-managementPassword").val(null);
	  	$("#add-pve-vmIp").val(null);
	  	
	  	// 分区类型
	  	var code = new codeModel({width:140,autoDropDownHeight:false,dropDownHeight:230});
	  	code.getCommonCode("add-pve-parType","PARTITION_TYPE",false);
	  	code.getCommonCode("add-pve-osVersion","OS_VERSION",false);
	  	code.getCommonCode("add-pve-status","RES_VM_STATUS",false);
  	   
	  	if(resTopologyType == "PHOST"){
	  		// 所属主机
		  	var codeCustom1 = new codeModel({width:140,autoDropDownHeight:false,dropDownWidth:200,dropDownHeight:230});
		  	codeCustom1.getCustomCode("add-pve-allocateResHostSid","/host","hostName","resHostSid",false,"POST",{resHostSid:resTopologySid});
	  	}else{
	  		var codeCustom1 = new codeModel({width:140,autoDropDownHeight:false,dropDownWidth:200,dropDownHeight:230});
		  	codeCustom1.getCustomCode("add-pve-allocateResHostSid","/host","hostName","resHostSid",false,"POST",{resTopologySid:resTopologySid});
	  	}
	  	
	  	
	  	// CPU共享资源池
	  	var codeCustom2 = new codeModel({width:140,autoDropDownHeight:false,dropDownWidth:140,dropDownHeight:230});
	  	codeCustom2.getCustomCode("add-pve-resCpuPoolSid","/cpuPools","cpuPoolName","resCpuPoolSid",true,"POST",{resHostSid:$("#add-pve-allocateResHostSid").val()});
	  	
	    var windowW = $(window).width();
	  	var windowH = $(window).height(); 
	  	$("#addPveVmWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-280)/2 } });
	  	$("#addPveVmWindow").jqxWindow('open');
 }
 
 // 提交新增分区信息
 function submitAddPveVmInfo(){
	 $('#addPveVmForm').jqxValidator('validate');
 }
  