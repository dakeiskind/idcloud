var resEditRzModel = function(){
	var me = this;
	this.cuttentData = new Object();
	// 判断新增的区域、数据中心是否重复
	this.getTopologyName = function(name,type){
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
	this.setRzDetailsInfo = function(){
		var data = me.searchTopologyInfo(resTopologySid);
		$("#resTopologyName").html(data.resTopologyName);
		$("#description").html(data.description);
		
		$("#edit-rz-resTopologyName").val(data.resTopologyName);
		$("#edit-rz-description").val(data.description);
		$("#edit-rz-resTopologySid").val(resTopologySid);
	};
	
    // 验证
    this.initValidator = function(){
    	// 新增区域验证
		$('#editRzForm').jqxValidator({
	        rules: [  
	                  { input: '#edit-rz-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-rz-resTopologyName', message: '资源分区名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
	                	  if(cuttentData.resTopologyName == input.val()){
		                  		return true;
	                  	  }	else{
		                  	   var list = me.getTopologyName(input.val(),"RZ");
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
		$('#editRzForm').on('validationSuccess', function (event) {
			 var topology = Core.parseJSON($("#editRzForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/topologys/update",
	 				params :topology,
	 				callback : function (data) {
	 					$("#editRzWindow").jqxWindow('close');
	 					// 刷新基本信息
	 					if($("#poolRzDatagrid").length > 0){
	 						var rz = new poolRzDatagridModel();
	 						rz.searchPoolRzInfo();
	 					}else{
	 						setPoolTreeValue();
	 					}
	 			    },
	 			    failure:function(data){
						$("#editRzWindow").jqxWindow('close');
	 			    }
	 			});
	     });
    };
    
 // 初始化弹出window
	this.initPopWindow = function(){   
		// 新增区域window
		  $("#editRzWindow").jqxWindow({
		        width: 350, 
		        height:158,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#editRzCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	$("#edit-rz-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 120,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#edit-rz-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
		        	
			    	$("#editRzSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#editRzCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
	
};

//弹出新增区域window
function popEditRzWindow(){
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	$("#editRzWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
  	$("#editRzWindow").jqxWindow('open');
}

//提交区域信息
function editRzSubmit(){
	
	$('#editRzForm').jqxValidator('validate');
}
