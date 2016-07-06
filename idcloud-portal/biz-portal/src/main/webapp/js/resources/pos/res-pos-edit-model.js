var editPosModel = function () {
		var me = this;
			
	    // 初始化验证规则
	    this.initValidator = function(){
	    	$('#editPosForm').jqxValidator({
                rules: [
                        { input: '#edit-pos-osVisitAddress', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                        { input: '#edit-pos-clientDownloadUrl', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                        { input: '#edit-pos-totalCapacity', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                        { input: '#edit-pos-uuid', message: '不能为空', action: 'keyup, blur', rule: 'required' },                    
                        { input: '#edit-pos-objStorageName', message: '不能为空', action: 'keyup, blur', rule: 'required' },                    
                       ]
	    	});
	    	
	    	// 编辑用户表单验证成功
	    	$('#editPosForm').on('validationSuccess', function (event) {
	    			
	    			var osPojo = Core.parseJSON($("#editPosForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/objStorages/updateObjStorages",
		 				type:"post",
		 				params :osPojo,
		 				callback : function (data) {
		 					// 关闭编辑窗口
		 					$("#editPosWindow").jqxWindow('close');
		 					// 刷新datagrid
		 					var pos = new poolPosDatagridModel();
	 					    pos.searchPosInfo();
		 			    },
		 			    failure:function(data){
		 			    	
		 			    }
		 			});
	    	 });
	    	
	    };
	    
	    // 初始化弹出window
	    this.initPopWindow = function(){
			$("#editPosWindow").jqxWindow({
	                width: 350, 
	                height:250,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#editPosCancel"), 
	                modalOpacity: 0.3,
	                theme:currentTheme,
	                initContent:function(){
	                	$("#edit-pos-osVisitAddress").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,theme:currentTheme});
	                	$("#edit-pos-clientDownloadUrl").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,theme:currentTheme});
	        	        $("#edit-pos-totalCapacity").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,maxLength:128,theme:currentTheme});
	        	        $("#edit-pos-uuid").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,maxLength:128,theme:currentTheme});
	        	        $("#edit-pos-objStorageName").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,maxLength:128,theme:currentTheme});
	        	        $("#editPosSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        $("#editPosCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        var codeadd = new codeModel({width:200,autoDropDownHeight:true,dropDownWidth:200});
	        	    	codeadd.getCustomCode("edit-pos-resTopologySid","/objStorages/findVeBySid","resTopologyName","resTopologySid",false,"POST",{resTopologySid:resTopologySid});
	                }
	         });
	    };
  };
  
  // 弹出编辑os池
  function popEditObjectStroagePoolWindow(){
	  	var rowindex = $('#poolPOSDatagrid').jqxGrid('getselectedrowindex');
		var ok =  $('#jqxEditObjStoBtn').jqxButton('disabled');
	  	if(!ok && rowindex >= 0){
	  		var data = $('#poolPOSDatagrid').jqxGrid('getrowdata', rowindex);
	  		// 给编辑组件赋值 
	  		$("#edit-pos-osVisitAddress").val(data.osVisitAddress);
		  	$("#edit-pos-clientDownloadUrl").val(data.clientDownloadUrl);
		  	$("#edit-pos-totalCapacity").val(data.totalCapacity);
		  	$("#edit-pos-uuid").val(data.uuid);
		  	$("#edit-pos-objStorageName").val(data.objStorageName);
		  	$("#edit-pos-parentTopologySid").val(data.resPoolSid);
		  	$("#edit-pos-resOsSid").val(data.resOsSid);
		  	// 获取所属资源分区 
		  	var windowW = $(window).width(); 
			var windowH = $(window).height(); 
			$("#editPosWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-220)/2 } });
			$("#editPosWindow").jqxWindow('open');
	  	}
	  	
  }
  
  // 提交编辑os池信息
  function submitEditObjectStorage(){
	  $('#editPosForm').jqxValidator('validate');
  }

  