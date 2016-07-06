var virtualVeEditModel = function () {
		var me = this;
		
		this.searchVirtualInfo = function(resTopolodySid){
			var remoteData = null;
			Core.AjaxRequest({
	 			url : ws_url + "/rest/ves/"+resTopolodySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				remoteData = data;
	 			}
	 	    });
			return remoteData;
		};
		
		// 设置资源环境的详细信息
		this.setVirtualDetailsInfo = function(){
			var data = me.searchVirtualInfo(resTopologySid);
			$("#resTopologyName").html(data.resTopologyName);
			$("#virtualPlatformType").html(data.virtualPlatformType);
			$("#virtualPlatformVersion").html(data.virtualPlatformVersionName);
			$("#managementUrl").html(data.managementUrl);
			$("#managementAccount").html(data.managementAccount);
			$("#updateCycle").html(data.updateCycle);
			if(data.description != null && data.description != ""){
				if(data.description.length > 32){
					var desc = data.description.substring(0,32)+"...";
					$("#description").attr("title",data.description);
					$("#description").html(desc);
				}else{
					$("#description").html(data.description);
				}
			}
			
			// 给弹出编辑Window赋值
			$("#edit-ve-resTopologySid").val(data.resTopologySid);
			$("#edit-ve-resTopologyType").val(data.resTopologyType);
			$("#edit-ve-resTopologyName").val(data.resTopologyName);
	        $("#edit-ve-managementUrl").val(data.managementUrl);
	        $("#edit-ve-managementAccount").val(data.managementAccount);
	        $("#edit-ve-managementPassword").val(data.managementPassword);
	        $("#edit-ve-confirmPassword").val(data.managementPassword);
	        $("#edit-ve-updateCycle").val(data.updateCycle); 
	        $("#edit-ve-taskId").val(data.taskId);
	        $("#edit-ve-description").val(data.description);
	        
	        $("#edit-ve-virtualPlatformType").val(data.virtualPlatformType); 
	        $("#edit-ve-virtualPlatformVersion").val(data.virtualPlatformVersion); 
		};
		
		// 设置资源环境的详细信息
		this.setIvmAndOtherVirtualDetailsInfo = function(){
			var data = me.searchVirtualInfo(resTopologySid);
			
			$("#resTopologyName").html(data.resTopologyName);
			$("#virtualPlatformType").html(data.virtualPlatformType);
			$("#virtualPlatformVersion").html(data.virtualPlatformVersionName);
			if(data.description != null && data.description != ""){
				if(data.description.length > 32){
					var desc = data.description.substring(0,32)+"...";
					$("#description").attr("title",data.description);
					$("#description").html(desc);
				}else{
					$("#description").html(data.description);
				}
			}
			
			// 给弹出编辑Window赋值
			$("#edit-ivmOther-ve-resTopologySid").val(data.resTopologySid);
			$("#edit-ivmOther-ve-resTopologyType").val(data.resTopologyType);
			
			$("#edit-ivmOther-ve-taskId").val(data.taskId); 
			$("#edit-ivmOther-ve-virtualPlatformType").val(data.virtualPlatformType); 
		    $("#edit-ivmOther-ve-virtualPlatformVersion").val(data.virtualPlatformVersion);
		    $("#edit-ivmOther-ve-description").val(data.description);
		    
			$("#edit-ivmOther-ve-resTopologyName").val(data.resTopologyName);
		};
		
		// 验证初始化
	    this.initValidator = function(){
	    	$('#editVeForm').jqxValidator({
                rules: [
                          { input: '#edit-ve-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-ve-resTopologyName', message: '环境不能超过64个字符', action: 'blur', rule: 'length=1,64' },
                          
                          { input: '#edit-ve-managementUrl', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-ve-managementUrl', message: '管理地址不能超过128个字符', action: 'keyup, blur', rule: 'length=1,128' },
		                  { input: '#edit-ve-managementAccount', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-ve-managementAccount', message: '用户账号不能超过64个字符', action: 'blur', rule: 'length=1,64' },
                          { input: '#edit-ve-managementAccount', message: '只能输入英文或者数字', action: 'keyup, blur',rule: function (input, commit) {
	                    	  if(/[\u4E00-\u9FA5]/g.test(input.val())){
	                              return false;
	                          }else{
	                              return true;
	                          }

	              	  		}
		                  },
                          
                          { input: '#edit-ve-managementPassword', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-ve-managementPassword', message: '用户账号不能超过16个字符', action: 'blur', rule: 'length=1,16' },
                          
                          { input: '#edit-ve-confirmPassword', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-ve-confirmPassword', message: '用户账号不能超过16个字符', action: 'blur', rule: 'length=1,16' },
                          { input: '#edit-ve-confirmPassword', message: '前后输入的密码不一致', action: 'blur', rule: function(input,commit){
                        	  // 判断密码输入的是否是一致的
                        	  if(input.val() == $("#edit-ve-managementPassword").val()){
                        		  return true;
                        	  }else{
                        		  return false;
                        	  }
                          	} 
                          },
                          
                          { input: '#edit-ve-updateCycle', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-ve-updateCycle', message: '用户账号不能超过16个字符', action: 'blur', rule: 'length=1,16' },
                          { input: '#edit-ve-updateCycle', message: '请必须输入数字', action: 'blur', rule: 'number' }
                       ]
	    	});
	    	
	    	$('#editIvmAndOtherVeForm').jqxValidator({
                rules: [
                          { input: '#edit-ivmOther-ve-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-ivmOther-ve-resTopologyName', message: '环境不能超过64个字符', action: 'blur', rule: 'length=1,64' },
                         
                       ]
	    	});
	    	
	    	// 编辑资源环境
	    	$('#editVeForm').on('validationSuccess', function (event) {
	    			  var virtual = Core.parseJSON($("#editVeForm").serializeJson());
		    		  Core.AjaxRequest({
				 			url : ws_url + "/rest/ves/update",
				 			params :virtual,
				 			callback : function (data) {
				 				if($("#virtualVeDatagrid").length > 0){
				 					// 在datagrid中新增，刷新datagrid、tree
				 					var ve = new virtualVeDatagridModel();
				 					ve.searchVirtualVeInfo();
				 				}else{
				 					// 在概要中新增，刷新tree
				 					setVirtualTreeValue();
				 				}
				 				$("#editVeWindow").jqxWindow('close');
				 		    }
				 	  });
	    	 });
	    	
	    	// 编辑资源环境
	    	$('#editIvmAndOtherVeForm').on('validationSuccess', function (event) {
	    			  var virtual = Core.parseJSON($("#editIvmAndOtherVeForm").serializeJson());
		    		  Core.AjaxRequest({
				 			url : ws_url + "/rest/ves/update",
				 			params :virtual,
				 			callback : function (data) {
				 				if($("#virtualVeDatagrid").length > 0){
				 					// 在datagrid中新增，刷新datagrid、tree
				 					var ve = new virtualVeDatagridModel();
				 					ve.searchVirtualVeInfo();
				 				}else{
				 					// 在概要中新增，刷新tree
				 					setVirtualTreeValue();
				 				}
				 				$("#editIvmAndOtherVeWindow").jqxWindow('close');
				 		    }
				 	  });
	    	 });
	    };
	   
	    // 初始化弹出window
	    this.initPopWindow = function(){
	    	$("#editVeWindow").jqxWindow({
                width: 450, 
                height:200,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#editVeCancel"), 
                theme: currentTheme,
                modalOpacity: 0.3,
                initContent:function(){
                	 // 初始化新增用户页面组件
        	        $("#edit-ve-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
        	        $("#edit-ve-managementUrl").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
        	        $("#edit-ve-managementAccount").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
        	        $("#edit-ve-managementPassword").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
        	        $("#edit-ve-confirmPassword").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
        	        $("#edit-ve-updateCycle").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
        	        
        	        $("#edit-ve-description").jqxInput({placeHolder: "", height: 35, width: 350, minLength: 1,theme:currentTheme});
        	        
        	        $("#editVeSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#editVeCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	       
                }
             });
	    	
	    	$("#editIvmAndOtherVeWindow").jqxWindow({
                width: 400, 
                height:150,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#editIvmOtherVeCancel"), 
                theme: currentTheme,
                modalOpacity: 0.3,
                initContent:function(){
                	 // 初始化新增用户页面组件
        	        $("#edit-ivmOther-ve-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 180, minLength: 1,theme:currentTheme});
        	        $("#edit-ivmOther-ve-description").jqxInput({placeHolder: "", height: 30, width: 300, minLength: 1,theme:currentTheme});
        	        $("#editIvmOtherVeSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#editIvmOtherVeCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	       
                }
             });
	    };
  };
  
  function popEditVeWindow(){
	  var windowW = $(window).width(); 
  	  var windowH = $(window).height(); 
  	  $("#editVeWindow").jqxWindow({ position: { x: (windowW-450)/2, y: (windowH-160)/2 } });
  	  $("#editVeWindow").jqxWindow('open');
  }
  
  function popIvmAndOtherEditVeWindow(){
	  var windowW = $(window).width(); 
  	  var windowH = $(window).height(); 
  	  $("#editIvmAndOtherVeWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-150)/2 } });
  	  $("#editIvmAndOtherVeWindow").jqxWindow('open');
  }
  
  // 保存资源环境信息IVM、Other
  function saveIvmOtherEditVirtualInfo(){
	  $('#editIvmAndOtherVeForm').jqxValidator('validate');
  }
  
  // 保存资源环境信息
  function saveEditVirtualInfo(){
	  $('#editVeForm').jqxValidator('validate');
  }
  
  