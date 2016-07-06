// 编辑主机 
var editHostModel = function () {
	// 设置修改值
	this.setEdithostForm = function(data){
    	$("#edit-hostName").val(data.hostName);
    	$("#edit-hostType").val(data.hostType);
    	$("#edit-uuid").val(data.uuid);
    	
    	$("#edit-vendor").val(data.vendor);
    	$("#edit-serialNumber").val(data.serialNumber);
    	$("#edit-status").val(data.status);
    	
    	$("#edit-hostIp").val(data.hostIp);
    	$("#edit-managementIp").val(data.managementIp); 
    	$("#edit-memorySize").val(data.memorySize);
    	
    	$("#edit-cpuNumber").val(data.cpuNumber);
    	$("#edit-cpuCores").val(data.cpuCores);
    	$("#edit-cpuType").val(data.cpuType);
    	
    	$("#edit-cpuGhz").val(data.cpuGhz);
    	$("#edit-nicNumber").val(data.nicNumber);
    	$("#edit-hostOsType").val(data.hostOsType);
    	$("#edit-resHostSid").val(data.resHostSid);
    	

    };
    // 初始化验证规则   
    this.initValidator = function(){
			$('#editHostForm').jqxValidator({
		        rules: [  
		                  { input: '#edit-hostName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#edit-hostName', message: '主机名称不能超过128个字符', action: 'keyup, blur', rule: 'length=1,128' },
		                  
		                  { input: '#edit-hostIp', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#edit-hostIp', message: '主机IP不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
		                  
		                  { input: '#edit-memorySize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#edit-memorySize', message: '内存大小不能超过10个字符', action: 'keyup, blur', rule: 'length=1,10' },
		                  
		                  { input: '#edit-cpuNumber', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#edit-cpuNumber', message: 'CPU个数不能超过4个字符', action: 'keyup, blur', rule: 'length=1,4' },
		                  
		                  { input: '#edit-cpuCores', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#edit-cpuCores', message: 'CPU核数不能超过4个字符', action: 'keyup, blur', rule: 'length=1,4' },
		               ]
			});
		    	
			// 编辑主机验证成功
			$('#editHostForm').on('validationSuccess', function (event) {
				 var vlans = Core.parseJSON($("#editHostForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/host/update",
		 				params :vlans,
		 				callback : function (data) {
//		 					if($("#hostConfigMgtdatagrid").length>0){
//		 						var host = new virtualHostDatagridModel();
//			 					host.searchHostInfo();
//		 					}else{
//		 						setHostBasicInfo();
//		 					}
		 					var host = new virtualHostDatagridModel();
		 					host.searchHostInfo();
		 					
							$("#editHostWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
							$("#editHostWindow").jqxWindow('close');
		 			    }
		 			});
		     });
    };
    // 初始化组件
    this.initInput = function(){
    	// 初始化页面组件
    	$("#editHostWindow").jqxWindow({
            width: 800, 
            height:338,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#edithostCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	$("#edit-hostName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-hostType").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-uuid").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-vendor").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-serialNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-nicNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	
            	$("#edit-hostIp").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-managementIp").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-memorySize").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-cpuNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-cpuCores").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-cpuGhz").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	
            	var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
            	hostconfig.getCommonCode("edit-status","HOST_STATUS");
            	hostconfig.getCommonCode("edit-hostOsType","HOST_OS_TYPE");
            	hostconfig.getCommonCode("edit-cpuType","CPU_TYPE");
            	
//            	$("#getMonitorNode").jqxButton({ width: '80',theme:currentTheme,height: '25px'});
            	
            	$("#edithostSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            	$("#edithostCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            }
    	});
    };
    
	 // 初始化弹出window
	this.initPopWindow = function(){   	
		  $("#editHostWindow").jqxWindow({
		        width: 800, 
		        height:428,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#edithostCancel"), 
		        modalOpacity: 0.3
		    });
	};
	
	// 根据sid查询某个主机
	this.getHostByResSid = function(resSid){
		var hostData;
		 Core.AjaxRequest({
	 			url : ws_url + "/rest/hosts/"+resSid+"",
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				hostData = data;
	 			}
	 		 });
		 return hostData;
	};
};

//提交编辑主机信息
function editHostSubmit(){
	$('#editHostForm').jqxValidator('validate');
}
// 获取监控节点
//function getMonitorNode(){
//	var rowindex = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
//	var ok =  $("#getMonitorNode").jqxButton("disabled");
//	if(rowindex >= 0 && !ok){
//		var data = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
//		// 获取监控节点
//		Core.AjaxRequest({
//				url : ws_url + "/rest/hosts/getMonitorNode",
//				params :{
//					resSid:data.resSid,
//					hostName:data.hostName,
//					hostIp:data.hostIp			
//				},
//				callback : function (data) {
//					$("#monitorText").html("已加入监控");
//			    },
//			    failure:function(data){
//				
//			    }
//			});
//	}
//}



////供非datagrid的方法调用主机编辑
//function popExternalEditHostWindow(){
//	// 查询某个主机
//	var host = new editHostModel();
//	// 判断是否资源池下面的主机
//	if(resTopologySid.substring(0,1) =="p"){
//		resTopologySid = resTopologySid.substring(1);
//	}
//	var data = host.getHostByResSid(resTopologySid);
//	
//	$("#resSidHost").val(data.resSid);
//	host.setEdithostForm(data);
//	// 根据监控状态显示编辑画面
//	if(data.monitorStatusName == "未监控"){
//		$("#monitorText").html("未加入监控");
//		$("#getMonitorNode").jqxButton({ width: '80',theme:currentTheme,height: '25px',disabled: false});
//	}else{
//		$("#monitorText").html("已加入监控");
//		$("#getMonitorNode").jqxButton({ width: '80',theme:currentTheme,height: '25px',disabled: true});
//	}
//	// 设置弹出框位置
//	var windowW = $(window).width(); 
//  	var windowH = $(window).height(); 
//  	$("#editHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-428)/2 } });
//  	$("#editHostWindow").jqxWindow('open');
//}

////给概要基本信息赋值
//function setHostBasicInfo(){
//	var host = new editHostModel();
//	if(resTopologySid.substring(0,1) =="p"){
//		resTopologySid = resTopologySid.substring(1);
//	}
//	var data = host.getHostByResSid(resTopologySid);
//	
//	$("#hostName").html(data.hostName);
//	$("#description").html(data.resName);
//	$("#status").html(data.usageStatusName);
//	$("#ipAddr").html(data.hostIp);
//	
//}
  