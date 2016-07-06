var editStorageModel = function(){
	
	this.setEditStorageForm = function(data){
   	    $("#edit-storageNames").val(data.storageName);
        $("#edit-totalCapacitys").val(data.totalCapacity);
        $("#edit-availableCapacitys").val(data.availableCapacity);
//      $("#edit-provisionCapacitys").val(data.provisionCapacity);
        $("#edit-storageTypes").val(data.storageType);
        $("#edit-hardDiskTypes").val(data.hardDiskType);
        $("#edit-storageCategorys").val(data.storageCategory);
        $("#edit-storagePurposes").val(data.storagePurpose);
        $("#edit-storageUnitNos").val(data.storageUnitNo);
        $("#edit-storageTags").val(data.storageTag);
        $("#edit-storage-uuids").val(data.uuid);
        $("#edit-storage-statuss").val(data.status);
        $("#edit-storage-resequipStorageSid").val(data.resEquipStorageSid);
   };
   // 初始化验证规则   
   this.initValidator = function(){
   	$('#editStoragesForm').jqxValidator({
           rules: [  
                     { input: '#edit-storageNames', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                     { input: '#edit-storageNames', message: '存储名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
                     
                     { input: '#edit-totalCapacitys', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                     { input: '#edit-totalCapacitys', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
	         	  			if(/[^\d]/g.test(input.val())){
	           	  				return false;
	           	  			}else{
	           	  				return true;
	           	  			}
           	  		    }
	                  },
	                  { input: '#edit-availableCapacitys', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-availableCapacitys', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
	                	  if(/[^\d]/g.test(input.val())){
	                		  return false;
	                	  }else{
	                		  return true;
	                	  }
	                  }
	                  },
//	                  { input: '#edit-provisionCapacitys', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
//	                  { input: '#edit-provisionCapacitys', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
//	                	  if(/[^\d]/g.test(input.val())){
//	                		  return false;
//	                	  }else{
//	                		  return true;
//	                	  }
//	                  }
//	                  },
                     { input: '#edit-storageUnitNos', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                     { input: '#edit-storageUnitNos', message: '存储标签不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
                     { input: '#edit-storage-uuids', message: '不能为空!', action: 'keyup, blur', rule: 'required' }
                  ]
   	});
   	
   	// 编辑块存储验证成功
   	$('#editStoragesForm').on('validationSuccess', function (event) {
   		 var storage = JSON.parse($("#editStoragesForm").serializeJson());
   		 console.log(JSON.stringify(storage));
   		 Core.AjaxRequest({
				url : ws_url + "/rest/storage/updatestorage",
				params :storage,
				callback : function (data) {
					if($("#storageConfigdatagrid").length > 0){
	 					$("#editStoragesWindow").jqxWindow('close');
						var storage = new storageConfigMgtModel();
						storage.searchStorageConfigInfo();
 					}else{
 						var storage = new pveStorageConfigMgtModel();
						storage.searchStorageConfigInfo();
 						// 刷新左边的Tree
 //						setStorageTreeValue();
// 						setStoragePoolTreeValue();
 					}
					//$("#addStorageWindow").jqxWindow('close');
					$("#editStoragesWindow").jqxWindow('close');
			    },
			    failure:function(data){
			    	$("#editStoragesWindow").jqxWindow('close');
			    }
			});
	     });
   };
   
   // 初始化弹出window
   this.initPopWindow = function(){
   	 $("#editStoragesWindow").jqxWindow({
            width: 550, 
            height:250,
            resizable: false,  
            theme:currentTheme,
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#editStorageCancels"), 
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化新增用户页面组件
    	        $("#edit-storageNames").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	        $("#edit-totalCapacitys").jqxInput({placeHolder: "", height: 22, width: 150, maxLength:20,minLength: 1,theme:currentTheme});
//    	        $("#edit-provisionCapacitys").jqxInput({placeHolder: "", height: 22, width: 150, maxLength:20,minLength: 1,theme:currentTheme});
    	        $("#edit-availableCapacitys").jqxInput({placeHolder: "", height: 22, width: 150, maxLength:20,minLength: 1,theme:currentTheme});
    	        $("#edit-storageUnitNos").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	        $("#edit-storageTags").jqxInput({placeHolder: "", height: 22, width: 150,minLength: 0,maxLength: 32, theme:currentTheme});
    	        $("#edit-storage-uuids").jqxInput({placeHolder: "", height: 22, width: 150,minLength: 0,maxLength: 32, theme:currentTheme});
    	        $("#editStorageSaves").jqxButton({ width: '50',height:"25",theme:currentTheme});
    	        $("#editStorageCancels").jqxButton({ width: '50',height:"25",theme:currentTheme});
    	        
    	    }
        });
   };
   this.initCodeDropList = function(){
	    var codeadd = new codeModel({width:150,autoDropDownHeight:true});
		    codeadd.getCommonCode("edit-storageTypes","STORAGE_TYPE");
		    codeadd.getCommonCode("edit-hardDiskTypes","HARD_DISK_TYPE");
			codeadd.getCommonCode("edit-storageCategorys","STORAGE_CATEGORY");
		    codeadd.getCommonCode("edit-storagePurposes","STORAGE_PURPOSE");
		    codeadd.getCommonCode("edit-storage-statuss","STORAGE_STATUS");
         // codeadd.getCustomCode("edit-storage-resequipStorageSid","/phystorages/downlistStorage/storage","name","equipSid",true,"POST",{resTopologySid:resTopologySid});
 }
 
   
  // 根据sid查询某个存储
	this.getStorageByResSid = function(resSid){
		var storageData;
		 Core.AjaxRequest({
	 			url : ws_url + "/rest/storage/"+resSid+"",
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				storageData = data;
	 			}
	 		 });
		 return storageData;
	};
};

//提交编辑存储
function editStorageSubmits(){
	$('#editStoragesForm').jqxValidator('validate');
}

//弹出编辑存储window框,供非datagrid调用
function popEditStorageInfoWindow(){
		// 为编辑赋值
		var editStorage = new editStorageConfigMgtModel();
		var data = editStorage.getStorageByResSid(resTopologySid);
		$("#resSidStorage").val(data.resSid);
		var codeadd = new codeModel({width:150,autoDropDownHeight:true});
			
    codeadd.getCustomCode("edit-storage-resequipStorageSid","/phystorages/downlistStorage/storage","name","equipSid",true,"POST",{resTopologySid:resTopologySid});
		editStorage.setEditStorageForm(data);
		// 设置window的位置
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#editStorageWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-200)/2 } });
    	$("#editStorageWindow").jqxWindow('open');
}