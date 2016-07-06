// 编辑pveVm
var editPveVmModel = function () {
	
	    // 验证新增画面
		this.initValidator = function(){
	    	$('#editPveVmForm').jqxValidator({
	            rules: [  
	                      { input: '#edit-pve-vmName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-pve-vmName', message: '分区名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
	                      
	                      { input: '#edit-pve-parId', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-pve-parId', message: '分区编号不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
	                      
	                      { input: '#edit-pve-parProfile', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-pve-parProfile', message: '分区配置不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
	                      
	                      { input: '#edit-pve-powerCpuUsedUnits', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-pve-powerCpuUsedUnits', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#edit-pve-powerCpuMinUnits', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-pve-powerCpuMinUnits', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#edit-pve-powerCpuMaxUnits', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-pve-powerCpuMaxUnits', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#edit-pve-memorySize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-pve-memorySize', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#edit-pve-memoryMin', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-pve-memoryMin', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#edit-pve-memoryMax', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-pve-memoryMax', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
	                      
	                     
		                  
		                  { input: '#edit-pve-managementAccount', message: '管理用户名不能超过36个字符', action: 'blur',rule: function (input, commit) {
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
		                  
		                  { input: '#edit-pve-managementPassword', message: '管理用户密码不能超过36个字符', action: 'keyup, blur',rule: function (input, commit) {
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
	    	$('#editPveVmForm').on('validationSuccess', function (event) {
	    			 
		    		 var pveVmdata = JSON.parse($("#editPveVmForm").serializeJson());
		    		 
		    		 if(pveVmdata.parType ==1){
		    			// 虚拟CPU值
			    		 if($("#edit-pve-cpuCores").val() == null || $("#edit-pve-cpuCores").val() == ""){
			    			 Core.alert({
			    				  title:"提示",
			    				  message:'虚拟CPU值不能为空！'
			    			 });
			    			 return false;
			    		 }
			    		 // 虚拟CPU最小值
			    		 if($("#edit-pve-cpuCoresMin").val() == null || $("#edit-pve-cpuCoresMin").val() == ""){
			    			 Core.alert({
			    				  title:"提示",
			    				  message:'虚拟CPU最小值不能为空！'
			    			 });
			    			 return false;
			    		 }
			    		 // 虚拟CPU最大值
			    		 if($("#edit-pve-cpuCoresMax").val() == null || $("#edit-pve-cpuCoresMax").val() == ""){
			    			 Core.alert({
			    				  title:"提示",
			    				  message:'虚拟CPU最大值不能为空！'
			    			 });
			    			 return false;
			    		 }
		    		 }
		    		 
		    		 Core.AjaxRequest({	
		 				url : ws_url + "/rest/vms/update/pveVm",
		 				params :pveVmdata,
		 				async:false,
		 				callback : function (data) {
		 					// 刷新列表
		 					var virPveVm = new virtualPveVMDatagridModel();
		 					virPveVm.searchVMInfo();
		 					
		 					$("#editPveVmWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
	    };
	    
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	// 新增window
	    	$("#editPveVmWindow").jqxWindow({
                width: 800, 
                height:280,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#editPveVmCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	  $("#edit-pve-vmName").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  $("#edit-pve-parId").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  $("#edit-pve-parProfile").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  
                	  $("#edit-pve-powerCpuUsedUnits").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  $("#edit-pve-powerCpuMinUnits").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  $("#edit-pve-powerCpuMaxUnits").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  
                	  $("#edit-pve-cpuCores").jqxInput({placeHolder: "", height:22, width: 140, minLength: 1,theme:currentTheme});
                	  $("#edit-pve-cpuCoresMin").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  $("#edit-pve-cpuCoresMax").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  
                	  $("#edit-pve-memorySize").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  $("#edit-pve-memoryMin").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  $("#edit-pve-memoryMax").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  
                	  $("#edit-pve-osName").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  $("#edit-pve-managementAccount").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  
                	  $("#edit-pve-managementPassword").jqxInput({placeHolder: "", height:22 , width: 140, minLength: 1,theme:currentTheme});
                	  
        	    	  $("#editPveVmSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
        	    	  $("#editPveVmCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                }
            });
	    };
 };
  
// 弹出新增主机框
 function popEditPveVmWindow(){
	 	// 初始化值
	 	var rowindex = $('#pvevmdatagrid').jqxGrid('selectedrowindexes');
		
		if(rowindex.length == 1){
			for(var i=0;i<rowindex.length;i++){
				var data = $('#pvevmdatagrid').jqxGrid('getrowdata', rowindex[0]);
			
				$("#edit-pve-resVmSid").val(data.resVmSid);
				$("#edit-pve-parType").val(data.parType);
				if(data.parType == "0"){
					$("#isEditPhyZoon").hide();
				}else{
					$("#isEditPhyZoon").show();
					
					$("#edit-pve-cpuCores").val(data.cpuCores);
	          	    $("#edit-pve-cpuCoresMin").val(data.cpuCoresMin);
	          	    $("#edit-pve-cpuCoresMax").val(data.cpuCoresMax);
				}
				
				$("#edit-pve-vmName").val(data.vmName);
          	    $("#edit-pve-parId").val(data.parId);
          	    $("#edit-pve-parProfile").val(data.parProfile);

          	    $("#edit-pve-powerCpuUsedUnits").val(data.powerCpuUsedUnits);
          	    $("#edit-pve-powerCpuMinUnits").val(data.powerCpuMinUnits);
          	    $("#edit-pve-powerCpuMaxUnits").val(data.powerCpuMaxUnits);
          	    
          	    $("#edit-pve-memorySize").val(data.memorySize);
          	    $("#edit-pve-memoryMin").val(data.memoryMin);
          	    $("#edit-pve-memoryMax").val(data.memoryMax);
          	    $("#edit-pve-osName").val(data.osName);
          	    $("#edit-pve-managementAccount").val(data.managementAccount);
          	    $("#edit-pve-managementPassword").val(data.managementPassword);
          	    
          	    // 分区类型
        	  	var code = new codeModel({width:140,autoDropDownHeight:false,dropDownHeight:230});
        	  	
        	  	code.getCommonCode("edit-pve-status","RES_VM_STATUS",false);
        	  	
        	  	
        	    var windowW = $(window).width();
        	  	var windowH = $(window).height(); 
        	  	$("#editPveVmWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-280)/2 } });
        	  	$("#editPveVmWindow").jqxWindow('open');

			}
		}else{
			Core.alert({
				title: "提示",
				message: "请选择一条数据后再进行操作！",
			});
		}
	  	
 }
 
 // 提交新增分区信息
 function submitEditPveVmInfo(){
	 $('#editPveVmForm').jqxValidator('validate');
 }
  