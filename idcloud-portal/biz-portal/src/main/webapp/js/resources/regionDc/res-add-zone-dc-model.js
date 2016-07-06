var resAddZoneDcModel = function(){
	var me = this;
	
	// 判断新增的区域、数据中心是否重复
	this.getRegionAndDc = function(name,type){
		var Todata = null;
		Core.AjaxRequest({
 			url : ws_url + "/rest/topologys/checkTopologyRepeat/",
 			type:'POST',
 			async:false,
 			params:{
 				resTopologyName : name,
 				resTopologyType : type
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
		$('#addRegionForm').jqxValidator({
	        rules: [  
	                  { input: '#add-region-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-region-resTopologyName', message: '区域名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
	                  { input: '#add-region-resTopologyName', message: '区域名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
	                  	  	var list = me.getRegionAndDc(input.val(),"R");
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
		$('#addDcForm').jqxValidator({
	        rules: [  
	                  { input: '#add-dc-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-dc-resTopologyName', message: '数据中心名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
	                  { input: '#add-dc-resTopologyName', message: '数据中心名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
	                  	  	var list = me.getRegionAndDc(input.val(),"DC");
	                  	  	if(list.length > 0){
	                  	  		return false;
	                  	  	}else{
	                  	  		return true;
	                  	  	}
	                      }
		              },
	               ]
		});
		
		// 新增存储分类验证
		$('#addRscForm').jqxValidator({
	        rules: [  
	                  { input: '#add-rsc-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-rsc-resTopologyName', message: '存储分类名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
	                  { input: '#add-rsc-resTopologyName', message: '存储分类名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
	                  	  	var list = me.getRegionAndDc(input.val(),"RSC");
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
		$('#addRegionForm').on('validationSuccess', function (event) {
			alert("99999");
			 var topology = Core.parseJSON($("#addRegionForm").serializeJson());
				alert(JSON.stringify(topology));
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/topologys/create",
	 				params :topology,
	 				callback : function (data) {
	 					$("#addRegionWindow").jqxWindow('close');
	 					// 刷新基本信息
	 					if($("#containerPool").length > 0){
	 						setPoolTreeValue();
	 					}else{
	 						setVirtualTreeValue();
	 						setStorageVirtualTreeValue();
	 					}
	 					
	 			    },
	 			    failure:function(data){
						$("#addRegionWindow").jqxWindow('close');
	 			    }
	 			});
	     });
	    	
		// 新增数据中心验证成功
		$('#addDcForm').on('validationSuccess', function (event) {
			 var topology = Core.parseJSON($("#addDcForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/topologys/create",
	 				params :topology,
	 				callback : function (data) {
	 					$("#addDcWindow").jqxWindow('close');
	 					// 刷新基本信息
	 					if($("#containerPool").length > 0){
	 						setPoolTreeValue();
	 					}else{
//	 						setVirtualTreeValue();
//	 						setStorageVirtualTreeValue();
	 						setPhysicalTreeValue();
	 					}
	 			    },
	 			    failure:function(data){
						$("#addDcWindow").jqxWindow('close');
	 			    }
	 			});
	     });
		
		// 新增存储分类验证成功
		$('#addRscForm').on('validationSuccess', function (event) {
			 var topology = Core.parseJSON($("#addRscForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/topologys/create",
	 				params :topology,
	 				callback : function (data) {
	 					$("#addRscWindow").jqxWindow('close');
	 					// 刷新基本信息
	 					setStorageVirtualTreeValue();
	 			    },
	 			    failure:function(data){
						$("#addRscWindow").jqxWindow('close');
	 			    }
	 			});
	     });
		
    };
    
 // 初始化弹出window
	this.initPopWindow = function(){   
		// 新增区域window
		  $("#addRegionWindow").jqxWindow({
		        width: 350, 
		        height:158,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addRegionCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#add-region-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#add-region-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
		        	
			    	$("#addRegionSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addRegionCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
		
		 // 新增数据中心window
		  $("#addDcWindow").jqxWindow({
		        width: 350, 
		        height:158,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addDcCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#add-dc-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#add-dc-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
		        	
			    	$("#addDcSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addDcCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
		  
		  // 新增存储分类window
		  $("#addRscWindow").jqxWindow({
		        width: 350, 
		        height:158,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addRscCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#add-rsc-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#add-rsc-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
		        	
			    	$("#addRscSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addRscCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
	
};

//弹出新增区域window
function popAddRegionWindow(){
	$("#add-region-resTopologyName").val(null);
	$("#add-region-description").val(null);
	
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 设置新增类型
	$("#add-region-resTopologyType").val("R");
	$("#add-region-sortNo").val("1");
	
  	$("#addRegionWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
  	$("#addRegionWindow").jqxWindow('open');
}

// 弹出新增数据中心window
function popAddDcWindow(){
	$("#add-dc-resTopologyName").val(null);
	$("#add-dc-description").val(null);
	
	var windowW = $(window).width();
  	var windowH = $(window).height(); 
  	// 设置新增类型
  	$("#add-dc-parentTopologySid").val(resTopologySid);
  	$("#add-dc-sortNo").val("2");
	$("#add-dc-resTopologyType").val("DC");
  	
  	$("#addDcWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
  	$("#addDcWindow").jqxWindow('open');
}

//弹出新增存储分类window
function popAddRscWindow(){
	$("#add-rsc-resTopologyName").val(null);
	$("#add-rsc-description").val(null);
	
	var windowW = $(window).width();
  	var windowH = $(window).height(); 
  	// 设置新增类型
  	$("#add-rsc-parentTopologySid").val(resTopologySid);
  	$("#add-rsc-sortNo").val("6");
	$("#add-rsc-resTopologyType").val("RSC");
  	
  	$("#addRscWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
  	$("#addRscWindow").jqxWindow('open');
}

//提交区域信息
function addRegionSubmit(){
	$('#addRegionForm').jqxValidator('validate');
}

// 提交数据中心信息
function addDcSubmit(){
	$('#addDcForm').jqxValidator('validate');
}

//提交存储分类信息
function addRscSubmit(){
	$('#addRscForm').jqxValidator('validate');
}

// 删除数据中心或者集群
function removeZoneOrDc(type){
	var message="";
	if("R" == type){
		message = "确定要删除该区域吗？";
	}else if("DC" == type){
		message = "确定要删除该数据中心吗？";
	}else if("VE" == type){
		message = "确定要删除该资源环境吗？";
	}else if("RSC" == type){
		message = "确定要删除该存储分类吗？";
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
 					if($("#containerPool").length > 0){
 						setPoolTreeValue();
 					}else{
 						setVirtualTreeValue();
 						setStorageVirtualTreeValue();
 					}
 			    },
 			    failure:function(data){
 			    	
 			    }
 			});
		}
   });
}
//function removeZoneOrDcByPhy(type){
//	var message="";
//	if("DC" == type){
//		message = "确定要删除该数据中心吗？";
//	}
//	
//	Core.confirm({
//		title:"提示",
//		message: message,
//		confirmCallback:function(){
//			Core.AjaxRequest({
// 				url : ws_url + "/rest/topology/deleteDC/"+resTopologySid+"",
// 				type:"get",
// 				callback : function (data) {
// 					// 刷新左边的Tree
// 					if($("#containerPool").length > 0){
// 						setPoolTreeValue();
// 					}else{
// 						setVirtualTreeValue();
// 						setStorageVirtualTreeValue();
// 					}
// 			    },
// 			    failure:function(data){
// 			    	
// 			    }
// 			});
//		}
//   });
//}

function removeCluster(type){
	Core.confirm({
		title:"提示",
		message:  "确定要删除该集群吗？",
		confirmCallback:function(){
			Core.AjaxRequest({
				url : ws_url + "/rest/topologys/deleteVC",
				type:"post",
				params:{
					resTopologySid:resTopologySid,
					type:type
				},
				async:false,
				callback : function (data) {
					// 刷新左边的Tree
					if($("#containerPool").length > 0){
						setPoolTreeValue();
					}else{
						setVirtualTreeValue();
						setStorageVirtualTreeValue();
					}
			    },
			    failure:function(data){
			    	
			    }
			});
		}
	});
}