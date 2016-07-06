var addStorageConfigMgtModel = function(){
	 // 初始化验证规则   
    this.initValidator = function(){
    	$('#addStorageForm').jqxValidator({
            rules: [  
                      { input: '#add-storageName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#add-storageName', message: '存储名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
                      
                      { input: '#add-totalCapacity', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#add-totalCapacity', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
            	  			if(/[^\d]/g.test(input.val())){
              	  				return false;
              	  			}else{
              	  				return true;
              	  			}
              	  		}
	                  },
	                  { input: '#add-availableCapacity', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-availableCapacity', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
	                	  if(/[^\d]/g.test(input.val())){
	                		  return false;
	                	  }else{
	                		  return true;
	                	  }
	                  }
	                  },
	                  { input: '#add-lunNo', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-lunNo', message: '不能超过128个字符', action: 'keyup, blur', rule: 'length=1,128' },
	                  
//                      { input: '#add-storage-uuid', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      
                      { input: '#add-storageUnitNo', message: '逻辑单元号不能超过64个字符', action: 'keyup, blur', rule: function (input, commit) {
	                	  if(input.val() == null || input.val() == ""){
	                		  return true;
	                	  }else{
	                		  if(input.val().length > 64){
	                			  return false;
	                		  }else{
	                			  return true;
	                		  }
	                	  }
	                  }
	                  }
                      
                  ]
    	});
    	
    	// 新增块存储验证成功
    	$('#addStorageForm').on('validationSuccess', function (event) {
    			
    		     var resHostSids = "";
    		     resHostSids = resTopologySid;
	    		 var storage = JSON.parse($("#addStorageForm").serializeJson());
//	    		 resHostSids = storage.resHostSid;
	    		 
	    		 Core.AjaxRequest({
	    			 url : ws_url + "/rest/storage/createHostToStorageByHost/",
	    			 type:"post",
	    			 params:storage,
	    			 callback : function (data) {
	    				 resStorageSid =  data.resStorageSid;
	    				 if(""!=data.resStorageSid){
	    					 Core.AjaxRequest({
	    						 url : ws_url + "/rest/storage/createHostStorageByHost/"+resStorageSid+"/"+resHostSids,
	    						 type:"get",
	    						 callback : function (data) {
	    							 $("#addStorageWindow").jqxWindow('close');
	    							 var addStorage = new storageHostMgtModel();
	    							 addStorage.searchStorageHostInfo();
	    							 
	    						 }
	    					 });
	    				 }else{
	    					 Core.confirm({
	    						 title:"提示",
	    						 message:"创建失败！"
	    					 });
	    				 }
	    			
	    			 }
	    		 });
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 // 判断是否是datagrid的新增存储
//	    		 if($("#storageConfigMgtdatagrid").length > 0 && resTopologyType == "pool"){
//	    			 // 设置是在pool新增存储
//	    			 storage.isResPoolSearch = "pool";
//	    		 }
//	    		 Core.AjaxRequest({
//	 				url : ws_url + "/rest/storage/create",
//	 				params :storage,
//	 				callback : function (data) {
//	 					$("#addStorageWindow").jqxWindow('close');
//	 					
////	 					var storage = new storageHostMgtModel();
////	 					storage.searchStorageHostInfo();
//	 					setVirtualRefreshTreeValue();
//	 					// 刷新左边的Tree
// 						//setStorageTreeValueRefresh();
// 						//setStoragePoolTreeValueRefresh();
//	 			    },
//	 			    failure:function(data){
//	 			    	$("#addStorageWindow").jqxWindow('close');
//	 			    }
//	 			});
    	 });
    };
    
    // 初始化弹出window
    this.initPopWindow = function(){
    	$("#addStorageWindow").jqxWindow({
            width: 550, 
            height:300,
            resizable: false,  
            theme:currentTheme,
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#storageCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化新增用户页面组件
    	        $("#add-storageName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	        $("#add-totalCapacity").jqxInput({placeHolder: "", height: 22, width: 150, maxLength:20,minLength: 1,theme:currentTheme});
    	        $("#add-availableCapacity").jqxInput({placeHolder: "", height: 22, width: 150, maxLength:20,minLength: 1,theme:currentTheme});
    	        $("#add-lunNo").jqxInput({placeHolder: "", height: 22, width: 150, maxLength:20,minLength: 1,theme:currentTheme});
    	        $("#add-storageUnitNo").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	        $("#add-storageTag").jqxInput({placeHolder: "", height: 22, width: 150,minLength: 0,maxLength: 32, theme:currentTheme});
    	        $("#add-storage-uuid").jqxInput({placeHolder: "", height: 22, width: 150,minLength: 0,maxLength: 256, theme:currentTheme});
    	       
    	        $("#storageSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
    	        $("#storageCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
    	        
    	        var codeadd = new codeModel({width:150,autoDropDownHeight:true});
       			 codeadd.getCommonCode("add-storageType","STORAGE_TYPE");
       			 codeadd.getCommonCode("add-hardDiskType","HARD_DISK_TYPE");
       			 codeadd.getCommonCode("add-storageCategory","STORAGE_CATEGORY");
       		     codeadd.getCommonCode("add-storagePurpose","STORAGE_PURPOSE");
       		     codeadd.getCommonCode("add-storage-status","STORAGE_STATUS");
       		     codeadd.getCustomCode("add-storage-resequipStorageSids","/phystorages/downlistStorage/storage","name","equipSid",true,"POST",{resTopologySid:resTopologySid});
            }
        });
    };
};	

// 提交新增存储
function addStorageSubmit(){
	$('#addStorageForm').jqxValidator('validate');
}

