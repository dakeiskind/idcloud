var extendStorageModel = function(){
	
	this.setExtendStorageForm = function(data){
   	    $("#extend-storageNames").val(data.storageName);
        $("#extend-totalCapacitys").val(data.totalCapacity);
        $("#extend-newTotalCapacitys").jqxNumberInput('val',100);
   };
   // 初始化验证规则   
   this.initValidator = function(){
   	$('#extendStoragesForm').jqxValidator({});
   	// 扩容块存储验证成功
   	$('#extendStoragesForm').on('validationSuccess', function (event) {
   		 var storage = new Object();
   		 storage.resStorageSid = $("#resExtendStorageSid").val();
   		 storage.totalCapacity = $("#extend-newTotalCapacitys").val();
   		 Core.AjaxRequest({
				url : ws_url + "/rest/storage/extendStorage",
				params :storage,
				callback : function (data) {
					if($("#storageConfigdatagrid").length > 0){
	 					$("#extendStoragesWindow").jqxWindow('close');
						var storage = new storageConfigMgtModel();
						storage.searchStorageConfigInfo();
 					}else{
 						var storage = new pveStorageConfigMgtModel();
						storage.searchStorageConfigInfo();
 					}
					$("#extendStoragesWindow").jqxWindow('close');
			    },
			    failure:function(data){
			    	$("#extendStoragesWindow").jqxWindow('close');
			    }
			});
	     });
   };
   
   // 初始化弹出window
   this.initPopWindow = function(){
   	 $("#extendStoragesWindow").jqxWindow({
            width: 550, 
            height:200,
            resizable: false,  
            theme:currentTheme,
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#extendStorageCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化新增用户页面组件
    	        $("#extend-storageNames").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	        $("#extend-totalCapacitys").jqxInput({placeHolder: "", height: 22, width: 150, maxLength:20,minLength: 1,theme:currentTheme});
    	        $("#extend-newTotalCapacitys").jqxNumberInput({
    				height : 22,
    				width : 150,
    				min : 100,
    				decimalDigits : 0,
    				spinButtons : true,
    				inputMode : 'simple',
    				theme : currentTheme,
    				readOnly : true,
    				spinButtonsStep : 100
    			});
    	        $("#extendStorageSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
    	        $("#extendStorageCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
    	        $("#extend-newTotalCapacitys").jqxNumberInput('val',100);
    	    }
        });
   };
};

//提交扩容存储
function extendStorageSubmit(){
	$('#extendStoragesForm').jqxValidator('validate');
}

