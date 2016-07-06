var resEditDcAndClusterModel = function(){
	var me = this;
	this.cuttentData = new Object();
	// 根据sid查询资源池信息
	this.searchDcAndCluster = function(resTopologySid){
		var resTopologyData;
		Core.AjaxRequest({
				url : ws_url + "/rest/topologys/"+resTopologySid+"",
				type:"get",
				async:false,
				callback : function (data) {
					resTopologyData = data;
					cuttentData = data;
			    },
			    failure:function(data){
			    	
			    }
		 });
		return resTopologyData;
	};
	
	this.getDcAndCluster = function(name,type){
		var Todata = null;
		Core.AjaxRequest({
 			url : ws_url + "/rest/topologys/checkTopologyRepeat/",
 			type:'POST',
 			async:false,
 			params:{
 				resTopologyName : name,
 				resType : type
 			},
 			callback : function (data) {
 				Todata = data;
 			}
 		 });
		return Todata;
	};
	
	// 设置主机资源池的基本信息
	this.setHostDcAndCluster = function(){
		var data = me.searchDcAndCluster(resTopologySid);
		$("#resTopologyName").html(data.resTopologyName);
		if(data.openHA =="0"){
			$("#cluster").html("已关闭");
		}else{
			$("#cluster").html("已开启");
		}
		
		$("#description").html(data.description);
	};
	
	// 设置资源环境的基本信息
	this.setHostVe = function(){
		var data = me.searchDcAndCluster(resTopologySid);
		$("#resTopologyName").html(data.resTopologyName);
		$("#virtualPlatformTypeVc").html(data.virtualPlatformTypeVc);
		$("#virtualPlatformVersion").html(data.virtualPlatformVersionName);
		$("#managementUrl").html(data.managementUrl);
		$("#managementAccount").html(data.managementAccount);
		$("#updateCycle").html(data.updateCycle);
		$("#mqTopic").html(data.mqTopic);
		$("#description").html(data.description);
	};
	
	// 设置要编辑的区域信息
	this.setEditHostRegionForm = function(){
		var data = me.searchDcAndCluster(resTopologySid);
		$("#edit-region-resTopologySid").val(data.resTopologySid);
		$("#edit-region-resTopologyType").val(data.resTopologyType);
		$("#edit-region-resTopologyName").val(data.resTopologyName);
		$("#edit-region-description").val(data.description);
    };
	
	// 设置要编辑的数据中心信息
	this.setEditHostDcForm = function(){
		var data = me.searchDcAndCluster(resTopologySid);
		$("#edit-dc-resTopologySid").val(data.resTopologySid);
		$("#edit-dc-resTopologyType").val(data.resTopologyType);
		$("#edit-dc-resTopologyName").val(data.resTopologyName);
		$("#edit-dc-description").val(data.description);
    };
    
    // 设置要编辑的资源环境信息
	this.setEditHostVeForm = function(){
		var data = me.searchDcAndCluster(resTopologySid);
		$("#edit-ve-resTopologySid").val(data.resTopologySid);
		$("#edit-ve-resTopologyType").val(data.resTopologyType);
		$("#edit-ve-resTopologyName").val(data.resTopologyName);
		$("#edit-ve-virtualPlatformType").val(data.virtualPlatformTypeVc);
    	$("#edit-ve-version").val(data.virtualPlatformVersion);
    	$("#edit-ve-mgtUrl").val(data.managementUrl);
    	$("#edit-ve-mgtAccount").val(data.managementAccount);
    	$("#edit-ve-mgtPasswd").val(data.managementPassword);
    	$("#edit-ve-updateCycle").val(data.updateCycle);
    	$("#edit-ve-mqTopic").val(data.mqTopic);
    	$("#edit-ve-description").val(data.description);
    };
    
    // 设置要编辑的集群信息
	this.setEditHostClusterForm = function(){
		var data = me.searchDcAndCluster(resTopologySid);
		$("#edit-cluster-resTopologySid").val(data.resTopologySid);
		$("#edit-cluster-resTopologyType").val(data.resTopologyType);
		$("#edit-cluster-resTopologyName").val(data.resTopologyName);
		$("#edit-cluster-description").val(data.description);
    };
    
   // 设置高级配置
	this.setEditHostConfigForm = function(){
		var data = me.searchDcAndCluster(resTopologySid);
		$("#edit-ha-resTopologySid").val(data.resTopologySid);
		$("#edit-ha-resTopologyType").val("ha");
		if(data.openHA == "0"){
			$("#openHA").val(false);
		}else{
			$("#openHA").val(true);
		}
		
    };
    
    // 验证
    this.initValidator = function(){
    	
    	$('#editHostRegionForm').jqxValidator({
	        rules: [  
	                  { input: '#edit-region-resTopologyName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-region-resTopologyName', message: '区域名称重复，请重新输入', action: 'keyup', rule: function (input, commit) {
		                	  if(cuttentData.resTopologyName == input.val()){
		                  		return true;
		                  	  }	else{
		                  		var list = me.getDcAndCluster(input.val(),"01");
		                  	  	if(list.length > 0){
		                  	  		return false;
		                  	  	}else{
		                  	  		return true;
		                  	  	}
		                  	  }	
	                      }
		              },
	               ]
		});
    	
		$('#editHostDcForm').jqxValidator({
	        rules: [  
	                  { input: '#edit-dc-resTopologyName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-dc-resTopologyName', message: '数据中心名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
	                	  if(cuttentData.resTopologyName == input.val()){
		                  		return true;
		                  	  }	else{
		                  		 var list = me.getDcAndCluster(input.val(),"02");
			                  	  	if(list.length > 0){
			                  	  		return false;
			                  	  	}else{
			                  	  		return true;
			                  	  	}
		                  	  }
	                      }
		               },
	               ]
		});
		
		// 编辑资源环境验证
		$('#editHostVeForm').jqxValidator({
	        rules: [  
	                  { input: '#edit-ve-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-ve-resTopologyName', message: '资源环境名称不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32'},
	                  { input: '#edit-ve-resTopologyName', message: '数据中心名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
	                	  if(cuttentData.resTopologyName == input.val()){
		                  		return true;
		                  	  }	else{
		                  		 var list = me.getDcAndCluster(input.val(),"03");
			                  	  	if(list.length > 0){
			                  	  		return false;
			                  	  	}else{
			                  	  		return true;
			                  	  	}
		                  	  }
	                      }
		               },
	                  
	                  { input: '#edit-ve-mgtUrl', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-ve-mgtUrl', message: '管理地址不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
	                  
	               ]
		});
		
		$('#editHostClusterForm').jqxValidator({
	        rules: [  
	                  { input: '#edit-cluster-resTopologyName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-cluster-resTopologyName', message: '集群名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
	                	  if (cuttentData.resTopologyName == input.val()) {
	                    		  return true;
	                    	  } else{
	                    		var list = me.getDcAndCluster(input.val(),"04");
	  	                  	  	if(list.length > 0){
	  	                  	  		return false;
	  	                  	  	}else{
	  	                  	  		return true;
	  	                  	  	}
	                    	  }	
	                      }
		                  },
	                  
	               ]
		});
	    
		// 编辑区域验证成功
		$('#editHostRegionForm').on('validationSuccess', function (event) {
			 var topology = Core.parseJSON($("#editHostRegionForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/topologys/update",
	 				params :topology,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					me.setHostDcAndCluster();
	 					$("#editHostRegionWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editHostRegionWindow").jqxWindow('close');
	 			    }
	 			});
	     });
		
		// 编辑数据中心验证成功
		$('#editHostDcForm').on('validationSuccess', function (event) {
			 var topology = Core.parseJSON($("#editHostDcForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/topologys/update",
	 				params :topology,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					me.setHostDcAndCluster();
	 					$("#editHostDcWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editHostDcWindow").jqxWindow('close');
	 			    }
	 			});
	     });
		
		// 新增资源环境验证成功
		$('#editHostVeForm').on('validationSuccess', function (event) {
			 var topology = Core.parseJSON($("#editHostVeForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/topologys/update",
	 				params :topology,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					me.setHostVe();
	 					$("#editHostVeWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editHostVeWindow").jqxWindow('close');
	 			    }
	 			});
	     });
		
		// 编辑集群验证成功
		$('#editHostClusterForm').on('validationSuccess', function (event) {
			 var topology = Core.parseJSON($("#editHostClusterForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/topologys/update",
	 				params :topology,
	 				callback : function (data) {
	 					// 刷新基本信息
	 					me.setHostDcAndCluster();
	 					$("#editHostClusterWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editHostClusterWindow").jqxWindow('close');
	 			    }
	 			});
	     });
		
    };
    
 // 初始化弹出window
	this.initPopWindow = function(){   	
		// 编辑区域window
		  $("#editHostRegionWindow").jqxWindow({
		        width: 350, 
		        height:158,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#editHostRegionCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#edit-region-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#edit-region-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
		        	
			    	$("#editHostRegionSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#editHostRegionCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
		
		 // 编辑数据中心window
		  $("#editHostDcWindow").jqxWindow({
		        width: 350, 
		        height:158,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#editHostDcCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#edit-dc-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#edit-dc-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
		        	
			    	$("#editHostDcSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#editHostDcCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
		  
		  // 编辑资源环境window
		  $("#editHostVeWindow").jqxWindow({
		        width: 480, 
		        height:260,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#editHostVeCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增页面组件
		        	$("#edit-ve-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#edit-ve-mgtUrl").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#edit-ve-mgtAccount").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#edit-ve-mgtPasswd").jqxPasswordInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme,showStrengthPosition: "right"});
		        	$("#edit-ve-updateCycle").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#edit-ve-mqTopic").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#edit-ve-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
		        	
		        	var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
        	    	hostconfig.getCommonCode("edit-ve-virtualPlatformType","VIRTUAL_PLATFORM_TYPE");
		        	
			    	$("#editHostVeSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#editHostVeCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
		  
		  // 编辑集群window
		  $("#editHostClusterWindow").jqxWindow({
		        width: 350, 
		        height:158,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#editHostClusterCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#edit-cluster-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#edit-cluster-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
		        	
			    	$("#editHostClusterSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#editHostClusterCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
		  
		// 高级设置window
		  $("#editAdvancedConfigWindow").jqxWindow({
		        width: 350, 
		        height:158,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#openHA").jqxCheckBox({ width: 90, height: 22,theme:currentTheme});
		        	
			    	$("#editAdvancedConfigSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
	
};

//弹出编辑区域window
function popEditHostRegionWindow(){
	var topology = new resEditDcAndClusterModel();
	// 编辑框赋值
	topology.setEditHostRegionForm();
	// 设置弹出框位置
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	$("#editHostRegionWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-150)/2 } });
  	$("#editHostRegionWindow").jqxWindow('open');
}

// 弹出编辑数据中心window
function popEditHostDcWindow(){
	var topology = new resEditDcAndClusterModel();
	// 编辑框赋值
	topology.setEditHostDcForm();
	// 设置弹出框位置
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	$("#editHostDcWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-150)/2 } });
  	$("#editHostDcWindow").jqxWindow('open');
}

// 弹出编辑资源环境window
function popEditHostVeWindow(){
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 设置编辑值
  	var topology = new resEditDcAndClusterModel();
	topology.setEditHostVeForm();
  	
  	$("#editHostVeWindow").jqxWindow({ position: { x: (windowW-480)/2, y: (windowH-300)/2 } });
  	$("#editHostVeWindow").jqxWindow('open');
}

//弹出编辑集群window
function popEditHostClusterWindow(){
	var topology = new resEditDcAndClusterModel();
	// 编辑框赋值
	topology.setEditHostClusterForm();
	// 设置弹出框位置
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	$("#editHostClusterWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-150)/2 } });
  	$("#editHostClusterWindow").jqxWindow('open');
}

//弹出高级配置window
function popAdvancedConfigWindow(){
	var topology = new resEditDcAndClusterModel();
	// 编辑框赋值
	topology.setEditHostConfigForm();
	// 设置弹出框位置
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	$("#editAdvancedConfigWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-150)/2 } });
  	$("#editAdvancedConfigWindow").jqxWindow('open');
}

//提交编辑数据中心信息
function editHostRegionSubmit(){
	$('#editHostRegionForm').jqxValidator('validate');
}
  
// 提交编辑数据中心信息
function editHostDcSubmit(){
	$('#editHostDcForm').jqxValidator('validate');
}

//提交编辑资源环境信息
function editHostVeSubmit(){
	$('#editHostVeForm').jqxValidator('validate');
}

//提交编辑集群信息
function editHostClusterSubmit(){
	$('#editHostClusterForm').jqxValidator('validate');
}

// 提交高级设置
function editAdvancedConfigSubmit(){
	  var sid =  $("#edit-ha-resTopologySid").val();
	  var type = $("#edit-ha-resTopologyType").val();
	  var openHA = $("#openHA").val();
	     if(openHA){
	    	 openHA = "1";
	     }else{
	    	 openHA = "0";
	     }
	     var topology = new Object();
	     topology.resTopologySid = sid;
	     topology.resTopologyType = type;
	     topology.openHA = openHA;
		 Core.AjaxRequest({
			url : ws_url + "/rest/topologys/update",
			params :topology,
			callback : function (data) {
				// 刷新基本信息
				var me = new resEditDcAndClusterModel();
				me.setHostDcAndCluster();
				$("#editAdvancedConfigWindow").jqxWindow('close');
		    },
		    failure:function(data){
				$("#editAdvancedConfigWindow").jqxWindow('close');
		    }
		});
}