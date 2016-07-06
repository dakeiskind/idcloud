// 新增主机 
var addVlanModel = function () {
		var me = this;

	    // 验证新增画面
		this.initValidator = function(){
			$('#addVlanForm').jqxValidator({
	            rules: [  
	                      { input: '#add-vlanId', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-vlanId', message: 'VLAN不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
	                      
	                      { input: '#add-vlanName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-vlanName', message: 'VLAN名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' }
	                   ]
	    	});
	    	
			// 新增vlan池验证成功
			$('#addVlanForm').on('validationSuccess', function (event) {
	    		 var vlan = Core.parseJSON($("#addVlanForm").serializeJson());
	    		 var param = {};
				 param.resTopologySid = resTopologySid;
				 param.isResPoolSearch = resTopologyType;
	    		 var newObj = $.extend(vlan,param);
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/vlans/create",
	 				params :newObj,
	 				callback : function (data) {
	 	            	me.initAddContent();
	 					var vlan = new vlanConfigMgtModel();
	 					vlan.searchVlanConfigInfo();
	 					$("#addVlanWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
	 			    	$("#addVlanWindow").jqxWindow('close');
	 			    }
	 			});
			});
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	$("#addVlanWindow").jqxWindow({
	            width: 500, 
	            height:150,
	            theme:currentTheme,
	            resizable: false,  
	            isModal: true, 
	            autoOpen: false, 
	            cancelButton: $("#addVlanCancel"), 
	            modalOpacity: 0.3,
	            initContent:function(){
	            	// 初始化新增用户页面组件
	    	        $("#add-vlanId").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	    	        $("#add-vlanName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	    	        $("#add-tag").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	    	        $("#addVlanSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	    	        $("#addVlanCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	            }
	        });
	    };
	    
	    // 判断操作按钮，初始化按钮
	    this.initAddContent = function(){
	    	// 初始化组件
	        $("#add-vlanId").val("");
	        $("#add-vlanName").val("");
	        $("#add-tag").val("");
	    };
 };
 
 //弹出新增vlan框
 function addVlanInfoWindow(){
 	var windowW = $(window).width(); 
 	var windowH = $(window).height(); 
 	$("#addVlanWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-150)/2 } });
 	$("#addVlanWindow").jqxWindow('open');
 }

 //提交新增VLAN信息
 function addVlanSubmit(){
 	$('#addVlanForm').jqxValidator('validate');
 };
  