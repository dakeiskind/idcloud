var addStorageModel = function(){
	var me = this;
    this.items = ko.observableArray();
	 // 初始化验证规则   
    this.initValidator = function(){
    	$('#addStorageForms').jqxValidator({
            rules: [  
                      { input: '#add-storageNames', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#add-storageNames', message: '存储名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
                      
                      { input: '#add-totalCapacitys', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#add-totalCapacitys', message: '只能输入100倍数的正整数', action: 'keyup, blur', rule: function (input, commit) {
                    	    var num = input.val();
            	  			if(num > 0 && num % 100 == 0){
              	  				return true;
              	  			}else{
              	  				return false;
              	  			}
              	  		}
	                  },
	                  { input: '#add-availableCapacitys', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-availableCapacitys', message: '只能输入数字且不能大于总容量', action: 'keyup, blur', rule: function (input, commit) {
	                	  if(!/[^\d]/g.test(input.val()) && parseInt(input.val()) <= parseInt($("#add-totalCapacitys").val())){
	                		  return true;
	                	  }else{
	                		  return false;
	                	  }
	                  }
	                  },
	                  { input: '#add-storage-lunNo', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-storage-lunNo', message: '不能超过128个字符', action: 'keyup, blur', rule: 'length=1,128' },
	                  
                      { input: '#add-storageUnitNos', message: '逻辑单元号不能超过64个字符', action: 'keyup, blur', rule: function (input, commit) {
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
//    	$('#addStorageForms').on('validationSuccess', function (event) {
//	    		 var storage = JSON.parse($("#addStorageForms").serializeJson());
//	    		 // 判断是否是datagrid的新增存储
////	    		 if($("#storageConfigMgtdatagrid").length > 0 && resTopologyType == "pool"){
////	    			 // 设置是在pool新增存储
////	    			 storage.isResPoolSearch = "pool";
////	    		 }
//	    		 Core.AjaxRequest({
//	 				url : ws_url + "/rest/storage/createStorage",
//	 				params :storage,
//	 				callback : function (data) {
//	 					var storage = new storageConfigMgtModel();
//	 					storage.searchStorageConfigInfo();
//	 					$("#addStoragesWindow").jqxWindow('close');
//	 					// 刷新左边的Tree
// 						//setStorageTreeValueRefresh();
// 						//setStoragePoolTreeValueRefresh();
//	 			    },
//	 			    failure:function(data){
//	 			    	$("#addStoragesWindow").jqxWindow('close');
//	 			    }
//	 			});
//    	 });
//    	
    	
    };
    this.searchfindHostAddToStorageData = function(resTopologySid){
      	 Core.AjaxRequest({
   			url : ws_url + "/rest/topology/findHostAddToStorage",
   			type:'post',
   			async:false,
   			params:{
   				resTopologySid: resTopologySid
   			},
   			callback : function (data) {
   				me.items(data);
   				var sourcedatagrid ={
  		              datatype: "json",
  		              localdata: me.items
   			    };
   			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
   			    $("#findStorageWithHost").jqxGrid({source: dataAdapter});
   			}
   		 });
      };
    this.initfindHostAddToStorageDatagrid = function(){
        $("#findStorageWithHost").jqxGrid({
            width: "99.6%",
			theme:currentTheme,
            columnsresize: true,
            pageable: true, 
            pagesize: 5, 
            autoheight: true,
            autowidth: false,
            sortable: true,
            selectionmode:"checkbox",
            localization: gridLocalizationObj,
            columns: [
                { text: '主机名称', datafield: 'hostName'},
                { text: '主机型号', datafield: 'hostType',width:150},
                { text: 'CPU(核)', datafield: 'cpuCores',width:60},
                { text: '内存(MB)', datafield: 'memorySize',width:60},
                { text: 'IP地址', datafield: 'hostIp'},
                { text: '使用状态', datafield: 'statusName',width:60}
            ]
        });
  };

    // 初始化弹出window
    this.initPopWindow = function(){
    	$("#addStoragesWindow").jqxWindow({
            width: 550, 
            height:300,
            resizable: false,  
            theme:currentTheme,
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#storageCancels"), 
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化新增用户页面组件
    	        $("#add-storageNames").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	        $("#add-totalCapacitys").jqxInput({placeHolder: "", height: 22, width: 150, maxLength:20,minLength: 1,theme:currentTheme});
    	        $("#add-availableCapacitys").jqxInput({placeHolder: "", height: 22, width: 150, maxLength:20,minLength: 1,theme:currentTheme});
    	        $("#add-storage-lunNo").jqxInput({placeHolder: "", height: 22, width: 150, maxLength:20,minLength: 1,theme:currentTheme});
    	        $("#add-storageUnitNos").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	        $("#add-storageTags").jqxInput({placeHolder: "", height: 22, width: 150,minLength: 0,maxLength: 32, theme:currentTheme});
    	        $("#add-storage-uuids").jqxInput({placeHolder: "", height: 22, width: 150,minLength: 0,maxLength: 256, theme:currentTheme});
    	       
    	        $("#storageSaves").jqxButton({ width: '60',height:"25",theme:currentTheme});
    	        $("#storageCancels").jqxButton({ width: '50',height:"25",theme:currentTheme});
    	        var codeadd = new codeModel({width:150,autoDropDownHeight:true});
             	    codeadd.getCommonCode("add-storageTypes","STORAGE_TYPE");
                    codeadd.getCommonCode("add-hardDiskTypes","HARD_DISK_TYPE");
             	    codeadd.getCommonCode("add-storageCategorys","STORAGE_CATEGORY");
             		codeadd.getCommonCode("add-storagePurposes","STORAGE_PURPOSE");
             		codeadd.getCommonCode("add-storage-statuss","STORAGE_STATUS");
                    codeadd.getCustomCode("add-storage-resequipStorageSid","/phystorages/downlistStorage/storage","name","equipSid",true,"POST",{resTopologySid:resTopologySid});
            }
        });
    	
    	$("#addStorageWithHostWindow").jqxWindow({
    		 width: 800, 
             height:300,
             resizable: false,  
             theme:currentTheme,
             isModal: true, 
             autoOpen: false, 
             cancelButton: $("#addStorageWithHostCancel"), 
             modalOpacity: 0.3,
             initContent:function(){
            	 
            	 $("#add-search-storage-host-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
     	         $("#add-search-storage-host-IP").jqxInput({placeHolder: "", height: 22, width: 150, maxLength:20,minLength: 1,theme:currentTheme});
     	         $("#add-search-storage-vmIp").jqxInput({placeHolder: "", height: 22, width: 150, maxLength:20,minLength: 1,theme:currentTheme});
            	 $("#add-search-storage-button").jqxButton({ width: '50',height:"25",theme:currentTheme});
     	         
            	 $("#addStorageWithHostSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
            	 $("#addStorageWithHostCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
             }
    		
    	});
    };
};	

// 提交新增存储

 function findHostsSubmit(){
	 var isOK = $('#addStorageForms').jqxValidator('validate');
	 if(!isOK){
		  return;
	 }
	 if(isOK){
		  var windowW = $(window).width(); 
		  var windowH = $(window).height(); 
		  var storage = new addStorageModel();
		  storage.searchfindHostAddToStorageData(resTopologySid);
		  $("#addStorageWithHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-300)/2 } });
		  $("#addStorageWithHostWindow").jqxWindow('open');
	 }
  }
 //存储修改，与底层存储关联，在集群节点下创建共享存储，不与单个主机有关联
 
 function addStoragesSubmit(){

	 var storages = Core.parseJSON($("#addStorageForms").serializeJson());
	 console.dir(storages);
		 Core.AjaxRequest({
			 url : ws_url + "/rest/storage/createStorageX86/",
			 type:"post",
			 params:storages,
			 callback : function (data) {
				 $("#addStoragesWindow").jqxWindow('close');
				 if($("#storageConfigdatagrid").length > 0){
					 var storage = new storageConfigMgtModel();
					 storage.searchStorageConfigInfo();
				 }else{
					 var storage = new pveStorageConfigMgtModel();
					 storage.searchStorageConfigInfo();
				 }
			 }
		 });

 }
	

function addHostToClusterSubmit(){
	 var storages = Core.parseJSON($("#addStorageForms").serializeJson());
	 var rowindex = $('#findStorageWithHost').jqxGrid('getselectedrowindexes');
	 var resHostSids = "";
	 var resStorageSid = "";
	 if(rowindex.length > 0){
		 for(var i=0;i<rowindex.length;i++){
			 var data = $('#findStorageWithHost').jqxGrid('getrowdata', rowindex[i]);
			 if(i == rowindex.length-1){
				 if(data != null && data != "" && data != 'undefined' ){
					 resHostSids += data.resHostSid;
				 }
			 }else{
					if(data != null && data != "" && data != 'undefined' ){
						resHostSids += data.resHostSid + ",";
					}
			 }
		 }
		 Core.AjaxRequest({
			 url : ws_url + "/rest/storage/createHostToStorageX86/",
			 type:"post",
			 params:storages,
			 callback : function (data) {
				 resStorageSid =  data.resStorageSid;
				 if(""!=data.resStorageSid){
					 Core.AjaxRequest({
						 url : ws_url + "/rest/storage/createHostStorageX86/"+resStorageSid+"/"+resHostSids,
						 type:"get",
						 callback : function (data) {
							 $("#addStorageWithHostWindow").jqxWindow('close');
							 $("#addStoragesWindow").jqxWindow('close');
							 
							 if($("#storageConfigdatagrid").length > 0){
								 var storage = new storageConfigMgtModel();
								 storage.searchStorageConfigInfo();
							 }else{
								 var storage = new pveStorageConfigMgtModel();
								 storage.searchStorageConfigInfo();
							 }
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
	 }
}

// 查询现在存储时，关联的主机
function searchStorageRelationHost(){
	var searchObj = {
		 resTopologySid: resTopologySid,
		 hostNameLike : $("#add-search-storage-host-name").val(),
		 hostIpLike :($("#add-search-storage-host-IP").val() =="")?null:$("#add-search-storage-host-IP").val(),
		 vmIpLike : ($("#add-search-storage-vmIp").val() == "")?null:$("#add-search-storage-vmIp").val()
	};
	// 查询
	Core.AjaxRequest({
		url : ws_url + "/rest/topology/findHostAddToStorage",
		type:'post',
		async:false,
		params:searchObj,
		callback : function (data) {
			var sourcedatagrid ={
	              datatype: "json",
	              localdata: data
		    };
		    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		    $("#findStorageWithHost").jqxGrid({source: dataAdapter});
		}
	 });
}

