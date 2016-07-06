// 编辑网络
var editPniNetworkModel = function () {
	var me = this;
	var currentObj = new Object();
	// 判断内部网络名称是否重复
	this.getNetworkName = function(name){
		var Todata = null;
		Core.AjaxRequest({
 			url : ws_url + "/rest/networks",
 			type:'POST',
 			async:false,
 			params:{
 				networkName : name
 			},
 			callback : function (data) {
 				Todata = data;
 			}
 		 });
		return Todata;
	};
	
	// 设置修改值
    this.setEditNetworkForm = function(data){
    	
    	currentObj = data;
    	
    	$("#edit-networkName").val(data.networkName);
        $("#edit-subnet").val(data.subnet);
        $("#edit-subnetMask").val(data.subnetMask);
        $("#edit-gateway").val(data.gateway);
        $("#edit-dns1").val(data.dns1);
        $("#edit-dns2").val(data.dns2);
        $("#edit-ipType").val(data.ipType);
        $("#edit-networkType_n1").val(data.networkType);
        $("#edit-vlanId-network").val(data.vlanId);
        $("#edit-ipRetainStart1").val(data.ipRetainStart1);
        $("#edit-ipRetainEnd1").val(data.ipRetainEnd1);
        $("#edit-ipRetainStart2").val(data.ipRetainStart2);
        $("#edit-ipRetainEnd2").val(data.ipRetainEnd2);
        $("#edit-ipRetainStart3").val(data.ipRetainStart3);
        $("#edit-ipRetainEnd3").val(data.ipRetainEnd3);
        $("#edit-description").val(data.description);
   };
   this.initInput = function(){
	// 初始化组件
       $("#edit-networkName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
       $("#edit-subnet").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme,disabled:true});
       $("#edit-subnetMask").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme,disabled:true});
       $("#edit-gateway").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme,disabled:false});
       $("#edit-dns1").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme,disabled:false});
       $("#edit-dns2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme,disabled:false});
//       $("#edit-vlanId-network").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme,disabled:true});
       $("#edit-ipRetainStart1").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme,disabled:true});
       $("#edit-ipRetainEnd1").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme,disabled:true});
       $("#edit-ipRetainStart2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme,disabled:true});
       $("#edit-ipRetainEnd2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme,disabled:true});
       $("#edit-ipRetainStart3").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme,disabled:true});
       $("#edit-ipRetainEnd3").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme,disabled:true});
       $("#edit-description").jqxInput({placeHolder: "", height: 46, width:400, minLength: 1,theme:currentTheme});
       
       $("#editNetworkSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
       $("#editNetworkCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
       
       // 初始化下拉列表框 
		var codesearch = new codeModel({width:150,autoDropDownHeight:true,disabled:true});
		codesearch.getCommonCode("edit-ipType","IP_TYPE",false);
		codesearch.getCommonCode("edit-networkType_n1","NETWORK_TYPE",false);
		var codesearch1 = new codeModel({width:150,autoDropDownHeight:true,disabled:false});
		codesearch1.getCustomCode("edit-vlanId-network","/vlanRes","vlanId","vlanId",false,"POST",{unused:""});
		$("#edit-vlanId-network").jqxDropDownList({ autoDropDownHeight:false,dropDownWidth:150,dropDownHeight:200});
   };
    // 初始化验证规则   
    this.initValidator = function(){
    	$('#editNetworkForm').jqxValidator({
    		 rules: [  
	                  { input: '#edit-networkName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-networkName', message: '网络名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                  { input: '#edit-networkName', message: '网络名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
                    	  
	                	  if(currentObj.networkName == input.val()){
		                  		return true;
	                  	  }	else{
		                  	   var list = me.getNetworkName(input.val());
		                  	  	if(list.length > 0){
		                  	  		return false;
		                  	  	}else{
		                  	  		return true;
		                  	  	}
	                  	  }	
	                    }
		              }, 
		              { input: '#edit-networkName', message: '存在特殊字符，请修改', action: 'blur', rule: function(input,commit){
	  			    		if(/^[.A-Za-z0-9_\-\u4e00-\u9fa5]+$/g.test(input.val())){
	  		  					return true;
	  		  				}else{
	  		  					return false;
	  		  				}
	  			    	}
		              },
	                  
	                  { input: '#edit-subnet', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-subnetMask', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  
	                  { input: '#edit-gateway', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
	                	  if(input.val() == null || input.val() == ""){
                    	        return true; 
                    	  }else{
                    		  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	                    	   if(!pattern.test($("#edit-gateway").val())){
                  	  				return false;
                  	  			}else{
                  	  				return true;
                  	  			}
                    	  }
	                	  
              	  		}
	                  },
		            ]
			});
		    	
    	// 更新ip池验证成功
		$('#editNetworkForm').on('validationSuccess', function (event) {
			 var pool = Core.parseJSON($("#editNetworkForm").serializeJson());
			 var param = {};
			 param.resNetworkSid = $("#edit-resNetworkSid").val();
    		 var newObj = $.extend(pool,param);
    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/networks/update",
	 				params :newObj,
	 				callback : function (data) {
	 	            	me.initEditContent();
	 					// 刷新基本信息
	 	            	var pni = new poolPniDatagridModel();
	 	            	pni.searchPoolPniInfo();
	 					$("#editNetworkWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editNetworkWindow").jqxWindow('close');
	 			    }
	 			});
	     });
    };
	 // 初始化弹出window
	this.initPopWindow = function(){   	
		$("#editNetworkWindow").jqxWindow({
            width: 600, 
            height:390,
            theme:currentTheme,
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#editNetworkCancel"), 
            modalOpacity: 0.3
        });
		
	};
	
    // 判断操作按钮，初始化按钮
    this.initEditContent = function(){
    	// 初始化组件
        $("#edit-networkName").val("");
        $("#edit-subnet").val("");
        $("#edit-subnetMask").val("");
        $("#edit-gateway").val("");
        $("#edit-dns1").val("");
        $("#edit-dns2").val("");
        $("#edit-ipType").val("");
        $("#edit-networkType_n1").val("");
        $("#edit-vlanId").val("");
        $("#edit-ipRetainStart1").val("");
        $("#edit-ipRetainEnd1").val("");
        $("#edit-ipRetainStart2").val("");
        $("#edit-ipRetainEnd2").val("");
        $("#edit-ipRetainStart3").val("");
        $("#edit-ipRetainEnd3").val("");
        
    };
    
};


// 提交将选择主机加入池
function editNetworkSubmit(){
	$('#editNetworkForm').jqxValidator('validate');
}
