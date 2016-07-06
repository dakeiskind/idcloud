var addPosModel = function () {
		var me = this;
		
	    // 初始化验证规则
	    this.initValidator = function(){
	    	$('#addPosForm').jqxValidator({
                rules: [
                          { input: '#add-pos-osVisitAddress', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-pos-clientDownloadUrl', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-pos-totalCapacity', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-pos-uuid', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-pos-objStorageName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          
                       ]
	    	});
	    	
	    	// 新增用户表单验证成功
	    	$('#addPosForm').on('validationSuccess', function (event) {
	    			var osPojo = Core.parseJSON($("#addPosForm").serializeJson());
		    		 Core.AjaxRequest({
		    			url : ws_url + "/rest/objStorages/creatObjectStorage",
		 				type:"post",
		 				params :osPojo,
		 				callback : function (data) {
		 					// 关闭新增窗口
		 					var pos = new poolPosDatagridModel();
	 					    pos.searchPosInfo();
		 					$("#addPosWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	
		 			    }
		 			});
	    	 });
	    	
	    };
	    
	    // 初始化弹出window
	    this.initPopWindow = function(){
			$("#addPosWindow").jqxWindow({
	                width: 350, 
	                height:250,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#addPosCancel"), 
	                modalOpacity: 0.3,
	                theme:currentTheme,
	                initContent:function(){
	                	$("#add-pos-osVisitAddress").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,theme:currentTheme});
	                	$("#add-pos-clientDownloadUrl").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,theme:currentTheme});
	        	        $("#add-pos-totalCapacity").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,maxLength:128,theme:currentTheme});
	        	        $("#add-pos-uuid").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,maxLength:128,theme:currentTheme});
	        	        $("#add-pos-objStorageName").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,maxLength:128,theme:currentTheme});
	        	        $("#addPosSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        $("#addPosCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        var codeadd = new codeModel({width:200,autoDropDownHeight:true,dropDownWidth:200});
	        	    	codeadd.getCustomCode("add-pos-resTopologySid","/objStorages/findVeBySid","resTopologyName","resTopologySid",false,"POST",{resTopologySid:resTopologySid});
	        	    	
	                }
	         });
	    };
  };
  
  // 弹出新增vlan池
  function popAddObjectStroagePoolWindow(){
	    // 清空数据
	  	$("#add-pos-osVisitAddress").val("");
	  	$("#add-pos-clientDownloadUrl").val("");
	  	$("#add-pos-totalCapacity").val("");
	  	$("#add-pos-uuid").val("");
	  	$("#add-pos-objStorageName").val("");
	  	$("#add-pos-parentTopologySid").val(resTopologySid);
	  	
	  	var windowW = $(window).width(); 
		var windowH = $(window).height(); 
		$("#addPosWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-190)/2 } });
		$("#addPosWindow").jqxWindow('open');
  }
  
  // 提交新增VLAN池信息
  function submitAddObjectStorage(){
	  $('#addPosForm').jqxValidator('validate');
  }

  