// 新增主机 
var addHostModel = function () {
	    // 验证新增画面
		this.initValidator = function(){
	    	$('#addHostForm').jqxValidator({
	            rules: [  
	                      { input: '#add-hostName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-hostName', message: '主机名称不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
	                      
	                      { input: '#add-hostIp', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-hostIp', message: '主机IP不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
	                      
	                      { input: '#add-memorySize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-memorySize', message: '内存大小不能超过10个字符', action: 'keyup, blur', rule: 'length=1,10' },
	                      
	                      { input: '#add-cpuNumber', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-cpuNumber', message: 'CPU个数不能超过4个字符', action: 'keyup, blur', rule: 'length=1,4' },
	                      
	                      { input: '#add-cpuCores', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-cpuCores', message: 'CPU核数不能超过4个字符', action: 'keyup, blur', rule: 'length=1,4' },
	                   ]
	    	});
	    	
	    	// 新增主机验证成功
	    	$('#addHostForm').on('validationSuccess', function (event) {
		    		 var hostdata = Core.parseJSON($("#addHostForm").serializeJson());
		    		 // 判断是否是datagrid里面的新增
		    		 if($("#hostConfigMgtdatagrid").length > 0 && resTopologyType == "pool"){
		    			 // 设置是在pool新增主机
		    			 hostdata.isResPoolSearch = "pool";
		    		 }
		    		 // 设置主机状态,以便于统计信息
		    		 hostdata.hostStatus = "01";
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/hosts/create",
		 				params :hostdata,
		 				callback : function (data) {
		 					if($("#hostConfigMgtdatagrid").length > 0){
		 						var host = new hostConfigModel();
			 					host.searchHostConfigInfo();
		 					}else{
		 						// 刷新左边的Tree
		 						setHostTreeValue();
		 						setPoolTreeValue();
		 					}
		 					
							$("#addHostWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
		 			    	$("#addHostWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	// 新增window
	    	$("#addHostWindow").jqxWindow({
                width: 800, 
                height:338,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addhostCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
                	$("#add-hostName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-fullName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-hostType").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-clusterName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-rackNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-cageEnclosure").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-uuid").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-vendor").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-dataCenter").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	
                	$("#add-hostIp").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-memorySize").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-cpuNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-cpuCores").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-cpuGhz").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-cpuType").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	
                	var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
        	    	hostconfig.getCommonCode("add-host-virtualPlatformType","VIRTUAL_PLATFORM_TYPE");
        	    	hostconfig.getCommonCode("add-hostOsType","HOST_OS_TYPE");
        	    	
        	    	$("#addhostSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
        	    	$("#addhostCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                }
            });
	    };
 };
 
//提交新增主机信息
 function addHostSubmit(){
	  $('#addHostForm').jqxValidator('validate');
 }
  
// 供非datagrid操作按钮调用  弹出新增主机框
 function popAddHostWindow(){
	 	// 清空之前新增留下的数据
	 	$("#add-hostName").val("");
	 	$("#add-fullName").val("");
	 	$("#add-hostType").val("");
	 	$("#add-clusterName").val("");
	 	$("#add-rackNumber").val("");
	 	$("#add-cageEnclosure").val("");
	 	$("#add-uuid").val("");
	 	$("#add-vendor").val("");
	 	$("#add-dataCenter").val("");
	 	$("#add-hostIp").val("");
	 	$("#add-memorySize").val("");
	 	$("#add-cpuNumber").val("");
	 	$("#add-cpuCores").val("");
	 	$("#add-cpuGhz").val("");
	 	$("#add-cpuType").val("");
	 	
		$("#add-host-parentTopologySid").val(resTopologySid);
	    var windowW = $(window).width();
	  	var windowH = $(window).height(); 
	  	$("#add-storage-parentTopologySid").val(resTopologySid);
	  	$("#addHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-338)/2 } });
	  	$("#addHostWindow").jqxWindow('open');
 }
  