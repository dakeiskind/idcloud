var editStorageConfigMgtModel = function(){
	
	this.setEditStorageForm = function(data){
   	    $("#edit-storageName").val(data.storageName);
        $("#edit-totalCapacity").val(data.totalCapacity);
        $("#edit-storageType").val(data.storageType);
        $("#edit-hardDiskType").val(data.hardDiskType);
        $("#edit-storageCategory").val(data.storageCategory);
        $("#edit-storagePurpose").val(data.storagePurpose);
        $("#edit-storageUnitNo").val(data.storageUnitNo);
        $("#edit-storageTag").val(data.storageTag);
        $("#edit-storage-uuid").val(data.uuid);
        $("#edit-storage-status").val(data.status);
        $("#edit-storage-resequipStorageSids").val(data.resEquipStorageSid);
   };
   // 初始化验证规则   
   this.initValidator = function(){
	   $('#editStorageForm').jqxValidator({
           rules: [  
                     { input: '#edit-storageName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                     { input: '#edit-storageName', message: '存储名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
                     
                     { input: '#edit-totalCapacity', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                     { input: '#edit-totalCapacity', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
           	  			if(/[^\d]/g.test(input.val())){
             	  				return false;
             	  			}else{
             	  				return true;
             	  			}
             	  		}
	                  },
                     { input: '#edit-storageUnitNo', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                     { input: '#edit-storageUnitNo', message: '存储标签不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
                     { input: '#edit-storage-uuid', message: '不能为空', action: 'keyup, blur', rule: 'required' }
                  ]
   	  });
    	   
	   
   	// 编辑块存储验证成功
   	$('#editStorageForm').on('validationSuccess', function (event) {
   		 var storage = JSON.parse($("#editStorageForm").serializeJson());
   		 Core.AjaxRequest({
				url : ws_url + "/rest/storage/update",
				params :storage,
				callback : function (data) {
					if($("#storageHostDatagrid").length > 0){
 						var storage = new storageHostMgtModel();
	 					storage.searchStorageHostInfo();
	 					$("#editStorageWindow").jqxWindow('close');
 					}else{
 						// 刷新左边的Tree
// 						setStorageTreeValue();
// 						setStoragePoolTreeValue();
 						setVirtualRefreshTreeValue();
 					}
					//$("#addStorageWindow").jqxWindow('close');
//					$("#editStorageWindow").jqxWindow('close');
			    },
			    failure:function(data){
			    	$("#editStorageWindow").jqxWindow('close');
			    }
			});
	     });
   };
   
   // 初始化弹出window
   this.initPopWindow = function(){
   	 $("#editStorageWindow").jqxWindow({
            width: 500, 
            height:280,
            resizable: false,  
            theme:currentTheme,
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#editStorageCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化新增用户页面组件
    	        $("#edit-storageName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	        $("#edit-totalCapacity").jqxInput({placeHolder: "", height: 22, width: 150, maxLength:20,minLength: 1,theme:currentTheme});
    	        $("#edit-storageUnitNo").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	        $("#edit-storageTag").jqxInput({placeHolder: "", height: 22, width: 150,minLength: 0,maxLength: 32, theme:currentTheme});
    	        $("#edit-storage-uuid").jqxInput({placeHolder: "", height: 22, width: 150,minLength: 0,maxLength: 32, theme:currentTheme});
    	        $("#editStoragebyHostSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
    	        $("#editStorageCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
    	        
    	        var codeadd = new codeModel({width:150,autoDropDownHeight:true});
       			 codeadd.getCommonCode("edit-storageType","STORAGE_TYPE");
       			 codeadd.getCommonCode("edit-hardDiskType","HARD_DISK_TYPE");
       			 codeadd.getCommonCode("edit-storageCategory","STORAGE_CATEGORY");
       		     codeadd.getCommonCode("edit-storagePurpose","STORAGE_PURPOSE");
       		     codeadd.getCommonCode("edit-storage-status","STORAGE_STATUS");
            }
        });
   };
   
  // 根据sid查询某个存储
	this.getStorageByResSid = function(resSid){
		var storageData;
		 Core.AjaxRequest({
	 			url : ws_url + "/rest/storages/"+resSid+"",
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
function editStorageSubmit(){
	$('#editStorageForm').jqxValidator('validate');
}

//弹出编辑存储window框,供非datagrid调用
function popEditStorageInfoWindow(){
		// 为编辑赋值
		var editStorage = new editStorageConfigMgtModel();
		var data = editStorage.getStorageByResSid(resTopologySid);
		$("#resSidStorage").val(data.resSid);
		editStorage.setEditStorageForm(data);
		// 设置window的位置
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#editStorageWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-200)/2 } });
    	$("#editStorageWindow").jqxWindow('open');
}