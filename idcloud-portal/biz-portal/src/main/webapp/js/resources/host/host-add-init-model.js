// 新增主机 
var addHostModel = function () {
	    // 验证新增画面
		this.initValidator = function(){
	    	$('#addHostForm').jqxValidator({
	            rules: [  
	                      { input: '#add-hostName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-hostName', message: '主机名称不能超过128个字符', action: 'keyup, blur', rule: 'length=1,128' },
	                      
	                      { input: '#add-hostIp', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-hostIp', message: '主机IP不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
	                      { input: '#add-hostIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
	                    	  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	                    	  if(!pattern.test(input.val())){
                  	  				return false;
                  	  			}else{
                  	  				return true;
                  	  			}
                  	  		}
		                  },
	                      
	                      { input: '#add-memorySize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-memorySize', message: '内存大小不能超过10个字符', action: 'keyup, blur', rule: 'length=1,10' },
	                      { input: '#add-memorySize', message: '请必须输入数字', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#add-cpuNumber', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-cpuNumber', message: 'CPU个数不能超过4个字符', action: 'keyup, blur', rule: 'length=1,4' },
	                      { input: '#add-cpuNumber', message: '请必须输入数字', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#add-cpuCores', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-cpuCores', message: 'CPU核数不能超过4个字符', action: 'keyup, blur', rule: 'length=1,4' },
	                      { input: '#add-cpuCores', message: '请必须输入数字', action: 'keyup, blur', rule: 'number' }
	                   ]
	    	});
	    	
	    	// 新增主机验证成功
	    	$('#addHostForm').on('validationSuccess', function (event) {
		    		 var hostdata = JSON.parse($("#addHostForm").serializeJson());
		    		 // 判断是否是datagrid里面的新增
//		    		 if($("#hostConfigMgtdatagrid").length > 0 && resTopologyType == "pool"){
//		    			 // 设置是在pool新增主机
//		    			 hostdata.isResPoolSearch = "pool";
//		    		 }
//		    		 // 设置主机状态,以便于统计信息
//		    		 hostdata.hostStatus = "01";
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/host/create",
		 				params :hostdata,
		 				callback : function (data) {
		 					
		 					$("#addHostWindow").jqxWindow('close');
		 					var host =  new virtualHostDatagridModel();
		 					host.searchHostInfo();
//		 					setVirtualTreeValue();
//	 						setStorageVirtualTreeValue();
//	 						// 刷新左边的Tree
//	 						setHostTreeValueRefresh();
//	 						setPoolTreeValueRefresh();
							
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
                height:380,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addhostCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
                	$("#add-hostName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-hostType").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-uuid").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-vendor").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-hostIp").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-memorySize").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-cpuNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-cpuCores").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-cpuGhz").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	//$("#add-cpuType").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-managementIp").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-nicNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	$("#add-serialNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
                	
                	//得到所有的分区
                	$("#add-host-belong-rz").jqxDropDownList({
    					width: 150,
    		            height: 22,
    		            theme:"metro"
    				});
                	Core.AjaxRequest({
		 				url : ws_url + "/rest/topology",
		 				params :{resTopologyType:"RZ"},
		 				callback : function (rzData) {
		 					$("#add-host-belong-rz").jqxDropDownList({
		 						width: 150,
		 			            height: 22,
		 			            source: rzData,
		 			            selectedIndex: 0,
		 			            displayMember: "resTopologyName", 
		 			            valueMember: "resTopologySid",
		 			            autoDropDownHeight :true,
		 			            theme:"metro"
		 					});
		 			    }
		 			});
                	
                	var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
        	    	hostconfig.getCommonCode("add-status","HOST_STATUS");
        	    	hostconfig.getCommonCode("add-hostOsType","HOST_OS_TYPE");
        	    	hostconfig.getCommonCode("add-cpuType","CPU_TYPE");
        	    	
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
	 	$("#add-hostType").val("");
	 	$("#add-uuid").val("");
	 	$("#add-vendor").val("");
	 	$("#add-hostIp").val("");
	 	$("#add-memorySize").val("");
	 	$("#add-cpuNumber").val("");
	 	$("#add-cpuCores").val("");
	 	$("#add-cpuGhz").val("");
	 	$("#add-managementIp").val("");
	 	$("#add-nicNumber").val("");
	 	$("#add-serialNumber").val("");
	 	$("#add-host-belong-veName").html("");
	 	
		$("#add-host-parentTopologySid").val(resTopologySid);
		
		Core.AjaxRequest({
			url : ws_url + "/rest/topology/findByParams",
			params :{"resTopologyType":"VE","findParentBySid":resTopologySid},
			callback : function (veData) {
				$("#add-host-belong-veName").html(veData[0].resTopologyName);
		    }
		});
		
	    var windowW = $(window).width();
	  	var windowH = $(window).height(); 
	  	$("#add-storage-parentTopologySid").val(resTopologySid);
	  	$("#addHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-300)/2 } });
	  	$("#addHostWindow").jqxWindow('open');
 }
  