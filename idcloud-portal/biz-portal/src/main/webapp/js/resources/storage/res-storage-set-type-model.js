var setStorageTypeModel = function () {
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	        // 初始化下拉列表框 
			 var codesearch = new codeModel({width:150,autoDropDownHeight:true,label:""});
			 codesearch.getCommonCode("set-storage-type","STORAGE_TYPE",true);
			 $("#set-storage-type").jqxDropDownList({
		            selectedIndex: 2, 
			 });
			 
			 $("#storageTypeSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
		     $("#storageTypeCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	    };
	    
	    // 初始化弹出window
	    this.initPopWindow = function(){
	         $("#setStorageTypeWindow").jqxWindow({
	                width: 300, 
	                height:100,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#storageTypeCancel"),
	                theme: currentTheme,
	                modalOpacity: 0.3           
	         });
	    };
	   
  };

  // 弹出设置存储类型window
  function popSetStorageTypeWindow(){
		var rowindex = $('#storageConfigdatagrid').jqxGrid('getselectedrowindexes');
		var resStorageSids = "";
	  	if(rowindex.length > 0){
	  		for(var i=0;i<rowindex.length;i++){
	   			var data = $('#storageConfigdatagrid').jqxGrid('getrowdata', rowindex[i]);
	   			if(i == rowindex.length-1){
	   				// 为了查询出集群下面的存储，加上引号
	   				if(data != null && data != "" && data != 'undefined' ){
	   					if(data.resStorageSid != null && data.resStorageSid != "" && data.resStorageSid != 'undefined'){
	   						resStorageSids += data.resStorageSid;
	   					}
					}
				}else{
					if(data != null && data != "" && data != 'undefined' ){
						if(data.resStorageSid != null && data.resStorageSid != "" && data.resStorageSid != 'undefined'){
							resStorageSids += data.resStorageSid + ",";
	   					}
					}
				}
	   		}
	  		
	  		// 获取选中的存储sid
	  		$("#set-storage-type-Sid").val(resStorageSids);
	  		
		    var windowW = $(window).width(); 
		  	var windowH = $(window).height(); 
		  	$("#setStorageTypeWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-100)/2 } });
		  	$("#setStorageTypeWindow").jqxWindow('open');
	  	}
  }
  
  // 弹出HMC设置存储类型window
  function popHMCSetStorageTypeWindow(){
		var rowindex = $('#pveStorageConfigdatagrid').jqxGrid('getselectedrowindexes');
		var resStorageSids = "";
	  	if(rowindex.length > 0){
	  		for(var i=0;i<rowindex.length;i++){
	   			var data = $('#pveStorageConfigdatagrid').jqxGrid('getrowdata', rowindex[i]);
	   			if(i == rowindex.length-1){
	   				// 为了查询出集群下面的存储，加上引号
	   				if(data != null && data != "" && data != 'undefined' ){
	   					if(data.resStorageSid != null && data.resStorageSid != "" && data.resStorageSid != 'undefined'){
	   						resStorageSids += data.resStorageSid;
	   					}
					}
				}else{
					if(data != null && data != "" && data != 'undefined' ){
						if(data.resStorageSid != null && data.resStorageSid != "" && data.resStorageSid != 'undefined'){
							resStorageSids += data.resStorageSid + ",";
	   					}
					}
				}
	   		}
	  		
	  		// 获取选中的存储sid
	  		$("#set-storage-type-Sid").val(resStorageSids);
	  		
		    var windowW = $(window).width(); 
		  	var windowH = $(window).height(); 
		  	$("#setStorageTypeWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-100)/2 } });
		  	$("#setStorageTypeWindow").jqxWindow('open');
	  	}
  }
  
  // 提交设置存储类型
 function submitStorageTypeInfo(){
	 var storageType = Core.parseJSON($("#setStorageTypeForm").serializeJson());
	 Core.AjaxRequest({
			url : ws_url + "/rest/storage/setStorageType",
			type:'post',
			async:false,
			params:storageType,
			callback : function (data) {
				if($('#pveStorageConfigdatagrid').length>0){
					// 关闭window
					$("#setStorageTypeWindow").jqxWindow('close');
					// 取消选中
					$('#pveStorageConfigdatagrid').jqxGrid('clearselection');
					// 刷新列表，带着查询条件刷新
					var pveStorage = new pveStorageConfigMgtModel(); 
					pveStorage.searchStorageConfigInfo();
				}else{
					// 关闭window
					$("#setStorageTypeWindow").jqxWindow('close');
					// 取消选中
					$('#storageConfigdatagrid').jqxGrid('clearselection');
					// 刷新列表，带着查询条件刷新
					searchStorageConfigMgt();
				}
			}
	    });
 }
  