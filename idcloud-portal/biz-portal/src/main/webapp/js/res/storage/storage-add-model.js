var addStorageConfigMgtModel = function(){
	 // 初始化验证规则   
    this.initValidator = function(){
    	$('#addStorageForm').jqxValidator({
            rules: [  
                      { input: '#add-volumeName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#add-volumeName', message: '存储名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
                      
                      { input: '#add-availableCapacity', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#add-availableCapacity', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
            	  			if(/[^\d]/g.test(input.val())){
              	  				return false;
              	  			}else{
              	  				return true;
              	  			}
              	  		}
	                  },
                      { input: '#add-volumeUnitNo', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#add-volumeUnitNo', message: '存储标签不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' }
                   ]
    	});
    	
    	// 新增块存储验证成功
    	$('#addStorageForm').on('validationSuccess', function (event) {
	    		 var storage = Core.parseJSON($("#addStorageForm").serializeJson());
	    		 // 判断是否是datagrid的新增存储
	    		 if($("#storageConfigMgtdatagrid").length > 0 && resTopologyType == "pool"){
	    			 // 设置是在pool新增存储
	    			 storage.isResPoolSearch = "pool";
	    		 }
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/storages/create",
	 				params :storage,
	 				callback : function (data) {
	 					if($("#storageConfigMgtdatagrid").length > 0){
	 						var storage = new storageConfigMgtModel();
		 					storage.searchStorageConfigInfo();
	 					}else{
	 						// 刷新左边的Tree
	 						setHostTreeValue();
	 						setHostPoolTreeValue();
	 					}
						$("#addStorageWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
	 			    	$("#addStorageWindow").jqxWindow('close');
	 			    }
	 			});
    	 });
    };
    
    // 初始化弹出window
    this.initPopWindow = function(){
    	$("#addStorageWindow").jqxWindow({
            width: 500, 
            height:195,
            resizable: false,  
            theme:currentTheme,
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#storageCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化新增用户页面组件
    	        $("#add-volumeName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	        $("#add-availableCapacity").jqxInput({placeHolder: "", height: 22, width: 150, maxLength:20,minLength: 1,theme:currentTheme});
    	        $("#add-volumeUnitNo").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	        $("#add-volumeTag").jqxInput({placeHolder: "", height: 22, width: 150,minLength: 0,maxLength: 32, theme:currentTheme});
    	       
    	        $("#storageSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
    	        $("#storageCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
    	        
    	        var codeadd = new codeModel({width:150,autoDropDownHeight:true});
       			 codeadd.getCommonCode("add-volumeType","STORAGE_TYPE");
       			 codeadd.getCommonCode("add-hardDiskType","HARD_DISK_TYPE");
       			 codeadd.getCommonCode("add-storageCategory","STORAGE_CATEGORY");
       		     codeadd.getCommonCode("add-storagePurpose","STORAGE_PURPOSE");
            }
        });
    };
};	

// 提交新增存储
function addStorageSubmit(){
	$('#addStorageForm').jqxValidator('validate');
}


//弹出新增存储window框,供非datagrid列表操作调用
function addStorageWindow(){
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	// 清除新增残留数据
	$("#add-volumeName").val("");
    $("#add-availableCapacity").val("");
    $("#add-volumeUnitNo").val("");
    $("#add-volumeTag").val("");
	
	$("#storage-parentTopologySid").val(resTopologySid);
	$("#addStorageWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-200)/2 } });
	$("#addStorageWindow").jqxWindow('open');
}