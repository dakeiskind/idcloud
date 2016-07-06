var virtualImageEditModel = function () {
		var me = this;
		// 验证初始化
	    this.initValidator = function(){
	    	$('#editImageForm').jqxValidator({
                rules: [
                          { input: '#edit-image-imageName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-image-imageName', message: '模板名称不能超过256个字符', action: 'blur', rule: 'length=1,256' },
//                          { input: '#add-image-osAdmin', message: '不能为空', action: 'keyup, blur', rule: 'required' },
//                          { input: '#add-image-osAdmin', message: '管理账号不能超过16个字符', action: 'blur', rule: 'length=1,16' },
//                          { input: '#add-image-osPasswd', message: '不能为空', action: 'keyup, blur', rule: 'required' },
//                          { input: '#add-image-osPasswd', message: '管理密码不能超过16个字符', action: 'blur', rule: 'length=1,16' },
                       ]
	    	});
	    };
	    
	    this.initInput = function(){
	    	//$("#edit-image-imageType1").jqxRadioButton({ width: 100, height: 22,checked: true,theme:currentTheme});
	        $("#editImageSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
   			$("#editImageCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
   			$("#edit-image-imageName").jqxInput({placeHolder: "", height: 22, width: 300, minLength: 256,theme:currentTheme});
//   			$("#add-image-osAdmin").jqxInput({placeHolder: "", height: 22, width: 300, minLength: 256,theme:currentTheme});
//   			$("#add-image-osPasswd").jqxInput({placeHolder: "", height: 22, width: 300, minLength: 256,theme:currentTheme});
        	var codesearch = new codeModel({width:300,autoDropDownHeight:false,dropDownWidth:300});
   			codesearch.getCommonCode("edit-image-osType","OS_TYPE",true);
   			var imageOsType =  $("#edit-image-osType").val();
   			//codesearch.getCommonCodeByConditions("add-image-osVersion",false,{parentCodeValue:imageOsType});
   			codesearch.getCustomCode("edit-image-osVersion","/system/getCodeByParams","codeDisplay","codeValue",true,"POST",{parentCodeValue:imageOsType});
	    };
		
		//初始下拉列表框的联动问题
	    this.initComboxLinkage = function(){
	    	var codeadd = new codeModel({width:300,autoDropDownHeight:false,dropDownWidth:300});
	    	$('#edit-image-osType').on('change', function (event){     
				    var args = event.args;
				    if (args) {
				    var item = args.item;
				    var value = item.value;
//				    codeadd.getCommonCodeByConditions("add-image-osVersion",false,{parentCodeValue:value});
				    codeadd.getCustomCode("edit-image-osVersion","/system/getCodeByParams","codeDisplay","codeValue",true,"POST",{parentCodeValue:value});
				    me.getOsVersionValue();
				    } 
			});
	    };  
	    this.getOsVersionValue = function (){
	    	var items = $("#edit-image-osVersion").jqxDropDownList('getItems');
	    	if(items.length > 0){
	    		$("#edit-image-osVersion").jqxDropDownList('selectItem', items[0] );
	    	}else{
	    		$("#edit-image-osVersion").jqxDropDownList({placeHolder: ""});
	    	} 
	    };
	    // 初始化弹出window
	    this.initPopWindow = function(){
	    	$("#editImageWindow").jqxWindow({
                width: 450, 
                height:180,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#editImageCancel"), 
                theme: currentTheme,
                modalOpacity: 0.3,
                initContent:function(){
                	 // 初始化新增用户页面组件
                	
                }
             });
	    };
	    this.setImageBasicInfo = function(data){
	  	  $("#edit-image-imageName").val(data);
	    };
	    this.setImageUUIDInfo = function(data){
	    	$("#uuid").val(data);
	    };
	    this.setImageSidInfo = function(data){
	    	$("#edit-image-resImageSid").val(data);
	    };
	    this.setImageVeInfo = function(data){
	    	$("#edit-image-resVeSid").val(data);
	    };
	    this.setImageOsTypeInfo = function(data){
	    	$("#edit-image-osType").val(data);
	    };
	    this.setImageOsVersionInfo = function(data){
	    	$("#edit-image-osVersion").val(data);
	    };
	   
	   
  };

  function editImageSaveInfo(){
	     var isOk = $('#editImageForm').jqxValidator('validate');
		 var virtual =  JSON.parse($("#editImageForm").serializeJson());
		 var osTypesid = $("#edit-image-osType").jqxDropDownList('getItems');
		 var osVersionsid = $("#edit-image-osVersion").jqxDropDownList('getItems');
		  if(isOk){
			  if(virtual.osType =='全部'){
				  Core.confirm({
					  title:"提示",
					  message:"请选择操作系统类型"
				  });
				  return false;
			  }else
			  if(virtual.osVersion =='全部'){
				  Core.confirm({
					  title:"提示",
					  message:"请选择操作系统版本"
				  });
				  return false;
			  }
			  Core.AjaxRequest({
					url : ws_url + "/rest/templates/imageUpdate",
					params :virtual,
					callback : function (data) {
						 var image = new virtualImageDatagridModel();
						 image.searchVirtualImageInfo();
						 $("#editImageWindow").jqxWindow('close');
						
				    }
			  });
		  }
  }
  
  // 关闭新增资源环境窗口
  function closeAddVirtualWindow(){
	  $('#editImageWindow').jqxWindow('close');
  }

  