var addPcdnModel = function () {
		var me = this;
		
	    // 初始化验证规则
	    this.initValidator = function(){
	    	$('#addPcdnForm').jqxValidator({
                rules: [
                          { input: '#add-pcdn-cdnName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-pcdn-cdnAccount', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-pcdn-cdnPassword', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-pcdn-cdnAddress', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          
                       ]
	    	});
	    	
	    	// 新增用户表单验证成功
	    	$('#addPcdnForm').on('validationSuccess', function (event) {
	    			var osPojo = Core.parseJSON($("#addPcdnForm").serializeJson());
		    		 Core.AjaxRequest({
		    			url : ws_url + "/rest/cdn/creatCdn",
		 				type:"post",
		 				params :osPojo,
		 				callback : function (data) {
		 					// 关闭新增窗口
		 					var pcdn = new poolPcdnDatagridModel();
		 					pcdn.searchPcdnInfo();
		 					$("#addPcdnWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	
		 			    }
		 			});
	    	 });
	    	
	    };
	    
	    // 初始化弹出window
	    this.initPopWindow = function(){
			$("#addPcdnWindow").jqxWindow({
	                width: 350, 
	                height:220,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#addPcdnCancel"), 
	                modalOpacity: 0.3,
	                theme:currentTheme,
	                initContent:function(){
	                	$("#add-pcdn-cdnName").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,theme:currentTheme});
	                	$("#add-pcdn-cdnAccount").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,theme:currentTheme});
	        	        $("#add-pcdn-cdnPassword").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,maxLength:128,theme:currentTheme});
	        	        $("#add-pcdn-cdnAddress").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,maxLength:128,theme:currentTheme});
	        	        $("#addPcdnSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        $("#addPcdnCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        var codeadd = new codeModel({width:200,autoDropDownHeight:true,dropDownWidth:200});
	        	    	codeadd.getCustomCode("add-pcdn-resTopologySid","/objStorages/findVeBySid","resTopologyName","resTopologySid",false,"POST",{resTopologySid:resTopologySid});
	        	    	
	                }
	         });
	    };
  };
  
  // 弹出新增vlan池
  function popAddCDNPoolWindow(){
	    // 清空数据
	  	$("#add-pcdn-cdnName").val("");
	  	$("#add-pcdn-cdnAccount").val("");
	  	$("#add-pcdn-cdnPassword").val("");
	  	$("#add-pcdn-cdnAddress").val("");
	  	$("#add-pcdn-parentTopologySid").val(resTopologySid);
	  	var windowW = $(window).width(); 
		var windowH = $(window).height(); 
		$("#addPcdnWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-190)/2 } });
		$("#addPcdnWindow").jqxWindow('open');
  }
  
  // 提交新增VLAN池信息
  function submitAddCdn(){
	  $('#addPcdnForm').jqxValidator('validate');
  }

  