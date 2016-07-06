var resEditZoneDcModel = function(){
	var me = this;
	this.cuttentData = new Object();
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
	
	this.searchTopologyInfo = function(resTopolodySid){
		var remoteData = null;
		Core.AjaxRequest({
 			url : ws_url + "/rest/topologys/"+resTopolodySid,
 			type:'get',
 			async:false,
 			callback : function (data) {
 				remoteData = data;
 				cuttentData = data;
 			}
 	    });
		return remoteData;
	};
	// 设置区域的基本信息
	this.setRegionDetailsInfo = function(){
		var data = me.searchTopologyInfo(resTopologySid);
		$("#resTopologyName").html(data.resTopologyName);
//		$("#description").html(data.description);
		if(data.description != null && data.description != ""){
			if(data.description.length > 32){
				var desc = data.description.substring(0,32)+"...";
				$("#description").attr("title",data.description);
				$("#description").html(desc);
			}else{
				$("#description").html(data.description);
			}
		}
		$("#edit-region-resTopologyName").val(data.resTopologyName);
		$("#edit-region-description").val(data.description);
		$("#edit-region-resTopologySid").val(resTopologySid);
	};
	
	// 设置数据中心基本信息
	this.setDcDetailsInfo = function(){
		var data = me.searchTopologyInfo(resTopologySid);
		$("#resTopologyName").html(data.resTopologyName);
		//$("#description").html(data.description);
		if(data.description != null && data.description != ""){
			if(data.description.length > 32){
				var desc = data.description.substring(0,32)+"...";
				$("#description").attr("title",data.description);
				$("#description").html(desc);
			}else{
				$("#description").html(data.description);
			}
		}
		
		$("#edit-dc-resTopologyName").val(data.resTopologyName);
		$("#edit-dc-description").val(data.description);
		$("#edit-dc-parentTopologySid").val(data.parentTopologySid);
		$("#edit-dc-resTopologySid").val(resTopologySid);
	};
	
    // 验证
    this.initValidator = function(){
    	// 新增区域验证
		$('#editRegionForm').jqxValidator({
	        rules: [  
	                  { input: '#edit-region-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-region-resTopologyName', message: '区域名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
	                  { input: '#edit-region-resTopologyName', message: '区域名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
	                	  if(cuttentData.resTopologyName == input.val()){
		                  		return true;
	                  	  }	else {
		                  	   var list = me.getRegionAndDc(input.val(),"R");
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
    	
    	// 新增数据中心验证
		$('#editDcForm').jqxValidator({
	        rules: [  
	                  { input: '#edit-dc-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-dc-resTopologyName', message: '数据中心名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
	                  { input: '#edit-dc-resTopologyName', message: '数据中心名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
	                	  if(cuttentData.resTopologyName == input.val()){
		                  		return true;
	                  	  }	else{
		                  	   var list = me.getRegionAndDc(input.val(),"DC");
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
		
		// 新增区域验证成功
		$('#editRegionForm').on('validationSuccess', function (event) {
			 var topology = Core.parseJSON($("#editRegionForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/topology/update",
	 				params :topology,
	 				callback : function (data) {
	 					$("#editRegionWindow").jqxWindow('close');
	 					// 刷新基本信息
	 					if($("#containerPool").length > 0){
	 						setPoolTreeValue();
	 					}else{
	 						setVirtualTreeValue();
	 					}
	 			    },
	 			    failure:function(data){
						$("#editRegionWindow").jqxWindow('close');
	 			    }
	 			});
	     });
	    	
		// 新增数据中心验证成功
		$('#editDcForm').on('validationSuccess', function (event) {
			 var topology = Core.parseJSON($("#editDcForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/topology/update",
	 				params :topology,
	 				callback : function (data) {
	 					$("#editDcWindow").jqxWindow('close');
	 					// 刷新基本信息
	 					if($("#containerPool").length > 0){
	 						setPoolTreeValue();
	 					}else{
	 						setVirtualTreeValue();
	 					}
	 			    },
	 			    failure:function(data){
						$("#editDcWindow").jqxWindow('close');
	 			    }
	 			});
	     });
		
    };
    
 // 初始化弹出window
	this.initPopWindow = function(){   
		// 新增区域window
		  $("#editRegionWindow").jqxWindow({
		        width: 350, 
		        height:158,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#editRegionCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#edit-region-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#edit-region-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
		        	
			    	$("#editRegionSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#editRegionCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
		
		 // 新增数据中心window
		  $("#editDcWindow").jqxWindow({
		        width: 350, 
		        height:158,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#editDcCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#edit-dc-resTopologyName").jqxInput({placeHolder: "", height: 23, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#edit-dc-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
		        	
			    	$("#editDcSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#editDcCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
		  
	 };
	
};

//弹出新增区域window
function popEditRegionWindow(){
	
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 设置新增类型
	$("#edit-region-resTopologyType").val("R");
  	
  	$("#editRegionWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
  	$("#editRegionWindow").jqxWindow('open');
}

// 弹出新增数据中心window
function popEditDcWindow(){
	
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 设置新增类型
	$("#edit-dc-resTopologyType").val("DC");
  	
  	$("#editDcWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
  	$("#editDcWindow").jqxWindow('open');
}

//提交区域信息
function editRegionSubmit(){
	$('#editRegionForm').jqxValidator('validate');
}

// 提交数据中心信息
function editDcSubmit(){
	$('#editDcForm').jqxValidator('validate');
}

