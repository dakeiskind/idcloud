var editPcdnModel = function () {
		var me = this;
			
	    // 初始化验证规则
	    this.initValidator = function(){
	    	$('#editPcdnForm').jqxValidator({
                rules: [
                        { input: '#edit-pcdn-cdnName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                        { input: '#edit-pcdn-cdnAccount', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                        { input: '#edit-pcdn-cdnPassword', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                        { input: '#edit-pcdn-cdnAddress', message: '不能为空', action: 'keyup, blur', rule: 'required' },                    
                       ]
	    	});
	    	
	    	// 编辑用户表单验证成功
	    	$('#editPcdnForm').on('validationSuccess', function (event) {
	    			
	    			var osPojo = Core.parseJSON($("#editPcdnForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/cdn/updateCDN",
		 				type:"post",
		 				params :osPojo,
		 				callback : function (data) {
		 					// 关闭编辑窗口
		 					$("#editPcdnWindow").jqxWindow('close');
		 					// 刷新datagrid
		 					var pcdn = new poolPcdnDatagridModel();
		 					pcdn.searchPcdnInfo();
		 			    },
		 			    failure:function(data){
		 			    	
		 			    }
		 			});
	    	 });
	    	
	    };
	    
	    // 初始化弹出window
	    this.initPopWindow = function(){
			$("#editPcdnWindow").jqxWindow({
	                width: 350, 
	                height:220,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#editPcdnCancel"), 
	                modalOpacity: 0.3,
	                theme:currentTheme,
	                initContent:function(){
	                	$("#edit-pcdn-cdnName").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,theme:currentTheme});
	                	$("#edit-pcdn-cdnAccount").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,theme:currentTheme});
	        	        $("#edit-pcdn-cdnPassword").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,maxLength:128,theme:currentTheme});
	        	        $("#edit-pcdn-cdnAddress").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,maxLength:128,theme:currentTheme});
	        	        $("#editPcdnSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        $("#editPcdnCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        var codeadd = new codeModel({width:200,autoDropDownHeight:true,dropDownWidth:200});
	        	    	codeadd.getCustomCode("edit-pcdn-resTopologySid","/objStorages/findVeBySid","resTopologyName","resTopologySid",false,"POST",{resTopologySid:resTopologySid});
	                }
	         });
	    };
  };
  
  // 弹出编辑os池
  function popEditCDNPoolWindow(){
	  	var rowindex = $('#poolPCDNDatagrid').jqxGrid('getselectedrowindex');
		var ok =  $('#jqxEditCDNBtn').jqxButton('disabled');
	  	if(!ok && rowindex >= 0){
	  		var data = $('#poolPCDNDatagrid').jqxGrid('getrowdata', rowindex);
	  		// 给编辑组件赋值 
	  		$("#edit-pcdn-cdnName").val(data.cdnName);
		  	$("#edit-pcdn-cdnAccount").val(data.cdnAccount);
		  	$("#edit-pcdn-cdnPassword").val(data.cdnPassword);
		  	$("#edit-pcdn-cdnAddress").val(data.cdnAddress);
		  	$("#edit-pcdn-resTopologySid").val(data.parentTopologyName);
		  	$("#edit-pcdn-parentTopologySid").val(data.resPoolSid);
		  	$("#edit-pcdn-cdnSid").val(data.cdnSid);
		  	// 获取所属资源分区 
		  	var windowW = $(window).width(); 
			var windowH = $(window).height(); 
			$("#editPcdnWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-220)/2 } });
			$("#editPcdnWindow").jqxWindow('open');
	  	}
	  	
  }
  
  // 提交编辑os池信息
  function submitEditCDN(){
	  $('#editPcdnForm').jqxValidator('validate');
  }

  