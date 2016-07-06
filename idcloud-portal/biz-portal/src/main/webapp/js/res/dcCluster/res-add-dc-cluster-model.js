var resAddDcAndClusterModel = function(){
	var me = this;
	
	this.getDcAndCluster = function(name,type){
		var Todata = null;
		Core.AjaxRequest({
 			url : ws_url + "/rest/topologys/checkTopologyRepeat/",
 			type:'POST',
 			async:false,
 			params:{
 				resTopologyName : name,
 				resType : type,
 				topologyParentSid : resTopologySid
 			},
 			callback : function (data) {
 				Todata = data;
 			}
 		 });
		return Todata;
	};
	
    // 验证
    this.initValidator = function(){
    	
    	// 新增区域验证
		$('#addHostRegionForm').jqxValidator({
	        rules: [  
	                  { input: '#add-region-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-region-resTopologyName', message: '区域名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
                  	  	var list = me.getDcAndCluster(input.val(),"01");
                  	  	if(list.length > 0){
                  	  		return false;
                  	  	}else{
                  	  		return true;
                  	  	}
                      }
	                  },
	                  
	               ]
		});
    	
    	// 新增数据中心验证
		$('#addHostDcForm').jqxValidator({
	        rules: [  
	                  { input: '#add-dc-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-dc-resTopologyName', message: '数据中心名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
                  	  	var list = me.getDcAndCluster(input.val(),"02");
                  	  	if(list.length > 0){
                  	  		return false;
                  	  	}else{
                  	  		return true;
                  	  	}
                      }
	                  },
	                  
	               ]
		});
		
		// 新增数据中心验证
		$('#addHostVeForm').jqxValidator({
	        rules: [  
	                  { input: '#add-ve-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-ve-resTopologyName', message: '资源环境名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
		                  	  	var list = me.getDcAndCluster(input.val(),"03");
		                  	  	if(list.length > 0){
	                  	  		    return false;
		                  	  	}else{
		                  	  		return true;
		                  	  	}
	                      }
	                  },
	                  
	                  { input: '#add-ve-mgtUrl', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-ve-mgtUrl', message: '管理地址不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
	                  
	               ]
		});
		
		// 新增集群验证
		$('#addHostClusterForm').jqxValidator({
	        rules: [  
	                  { input: '#add-cluster-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-cluster-resTopologyName', message: '集群名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
	                  	  	var list = me.getDcAndCluster(input.val(),"04");
	                  	  	if(list.length > 0){
	                  	  		return false;
	                  	  	}else{
	                  	  		return true;
	                  	  	}
	                      }
		                  },
	               ]
		});
		
		// 新增区域验证成功
		$('#addHostRegionForm').on('validationSuccess', function (event) {
			 var topology = Core.parseJSON($("#addHostRegionForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/topologys/create",
	 				params :topology,
	 				callback : function (data) {
	 					$("#addHostRegionWindow").jqxWindow('close');
	 					// 刷新基本信息
	 					setHostTreeValue();
	 			    },
	 			    failure:function(data){
						$("#addHostRegionWindow").jqxWindow('close');
	 			    }
	 			});
	     });
	    	
		// 新增数据中心验证成功
		$('#addHostDcForm').on('validationSuccess', function (event) {
			 var topology = Core.parseJSON($("#addHostDcForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/topologys/create",
	 				params :topology,
	 				callback : function (data) {
	 					$("#addHostDcWindow").jqxWindow('close');
	 					// 刷新基本信息
	 					setHostTreeValue();
	 					
	 			    },
	 			    failure:function(data){
						$("#addHostDcWindow").jqxWindow('close');
	 			    }
	 			});
	     });
		
		// 新增资源环境验证成功
		$('#addHostVeForm').on('validationSuccess', function (event) {
			 var topology = Core.parseJSON($("#addHostVeForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/topologys/create",
	 				params :topology,
	 				callback : function (data) {
	 					$("#addHostVeWindow").jqxWindow('close');
	 					// 刷新基本信息
	 					setHostTreeValue();
	 			    },
	 			    failure:function(data){
						$("#addHostVeWindow").jqxWindow('close');
	 			    }
	 			});
	     });
		
		// 新增集群验证成功
		$('#addHostClusterForm').on('validationSuccess', function (event) {
			 var topology = Core.parseJSON($("#addHostClusterForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/topologys/create",
	 				params :topology,
	 				callback : function (data) {
	 					$("#addHostClusterWindow").jqxWindow('close');
	 					// 刷新基本信息
	 					setHostTreeValue();
	 			    },
	 			    failure:function(data){
						$("#addHostClusterWindow").jqxWindow('close');
	 			    }
	 			});
	     });
    };
    
 // 初始化弹出window
	this.initPopWindow = function(){   
		
		// 新增区域window
		  $("#addHostRegionWindow").jqxWindow({
		        width: 350, 
		        height:158,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addHostRegionCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#add-region-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#add-region-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
		        	
			    	$("#addHostRegionSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addHostRegionCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
		
		 // 新增数据中心window
		  $("#addHostDcWindow").jqxWindow({
		        width: 350, 
		        height:158,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addHostDcCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#add-dc-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#add-dc-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
		        	
			    	$("#addHostDcSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addHostDcCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
		  
		// 新增资源环境window
		  $("#addHostVeWindow").jqxWindow({
		        width: 480, 
		        height:260,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addHostVeCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增页面组件
		        	$("#add-ve-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#add-ve-mgtUrl").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#add-ve-mgtAccount").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#add-ve-mgtPasswd").jqxPasswordInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme,showStrengthPosition: "right"});
		        	$("#add-ve-updateCycle").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#add-ve-mqTopic").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#add-ve-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
		        	
		        	var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
        	    	hostconfig.getCommonCode("add-ve-virtualPlatformType","VIRTUAL_PLATFORM_TYPE");
        	    	
			    	$("#addHostVeSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addHostVeCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
		  
		  // 新增集群window
		  $("#addHostClusterWindow").jqxWindow({
		        width: 350, 
		        height:158,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addHostClusterCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#add-cluster-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#add-cluster-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
		        	
//		        	var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
//        	    	hostconfig.getCommonCode("add-cluster-virtualPlatformType","VIRTUAL_PLATFORM_TYPE");
        	    	
			    	$("#addHostClusterSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addHostClusterCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
	
};

//弹出新增区域window
function popAddHostRegionWindow(){
	$("#add-region-resTopologyName").val(null);
	$("#add-region-description").val(null);
	
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 设置新增类型
	$("#add-region-resTopologyType").val("01");
  	
  	$("#addHostRegionWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
  	$("#addHostRegionWindow").jqxWindow('open');
}

// 弹出新增数据中心window
function popAddHostDcWindow(){
	$("#add-dc-resTopologyName").val(null);
	$("#add-dc-description").val(null);
	
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 设置新增类型
  	$("#add-dc-parentTopologySid").val(resTopologySid);
	$("#add-dc-resTopologyType").val("02");
  	
  	$("#addHostDcWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
  	$("#addHostDcWindow").jqxWindow('open');
}

//弹出新增资源环境window
function popAddHostVeWindow(){
	$("#add-ve-resTopologyName").val(null);
	$("#add-ve-version").val(null);
	$("#add-ve-mgtUrl").val(null);
	$("#add-ve-mgtAccount").val(null);
	$("#add-ve-mgtPasswd").val(null);
	$("#add-ve-updateCycle").val(null);
	$("#add-ve-mqTopic").val(null);
	$("#add-ve-description").val(null);
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 设置新增类型
  	$("#add-ve-parentTopologySid").val(resTopologySid);
	$("#add-ve-resTopologyType").val("03");
  	
  	$("#addHostVeWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-300)/2 } });
  	$("#addHostVeWindow").jqxWindow('open');
}

//弹出新增集群window
function popAddHostClusterWindow(){
	$("#add-cluster-resTopologyName").val(null);
	$("#add-cluster-description").val(null);
	
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 设置新增类型

	$("#add-cluster-parentTopologySid").val(resTopologySid);
	$("#add-cluster-resTopologyType").val("04");
  	
  	$("#addHostClusterWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
  	$("#addHostClusterWindow").jqxWindow('open');
}

//提交区域信息
function addHostRegionSubmit(){
	$('#addHostRegionForm').jqxValidator('validate');
}

// 提交数据中心信息
function addHostDcSubmit(){
	$('#addHostDcForm').jqxValidator('validate');
}

// 提交资源环境信息
function addHostVeSubmit(){
	$('#addHostVeForm').jqxValidator('validate');
}

//提交集群信息
function addHostClusterSubmit(){
	$('#addHostClusterForm').jqxValidator('validate');
}

// 删除数据中心或者集群
function removeDcAndCluster(type){
	var message="";
	if("01" == type){
		message = "确定要删除该区域吗？";
	}else if("02" == type){
		message = "确定要删除该数据中心吗？";
	}else if("03" == type){
		message = "确定要删除该资源环境吗？";
	}else if("04" == type){
		message = "确定要删除该集群吗？";
	}
	
	Core.confirm({
		title:"提示",
		message: message,
		confirmCallback:function(){
			Core.AjaxRequest({
 				url : ws_url + "/rest/topologys/delete/"+resTopologySid+"",
 				type:"get",
 				callback : function (data) {
 					// 刷新左边的Tree
 					setHostTreeValue();
 			    },
 			    failure:function(data){
 			    	
 			    }
 			});
		}
   });

}