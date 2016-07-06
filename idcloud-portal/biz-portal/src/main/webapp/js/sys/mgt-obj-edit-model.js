// 编辑网络
var editCustomNetworkModel = function () {
	var me = this;
	
	// 设置修改值
    this.setEditNetworkForm = function(data){
    	$("#edit-custom-networkName").val(data.networkName);
        $("#edit-custom-subnet").val(data.subnet);
        $("#edit-custom-subnetMask").val(data.subnetMask);
        $("#edit-custom-gateway").val(data.gateway);
        $("#edit-custom-dns1").val(data.dns1);
        $("#edit-custom-dns2").val(data.dns2);
        $("#edit-custom-ipType").val(data.ipType);
        $("#edit-custom-networkType_n1").val(data.networkType);
        $("#edit-custom-ipRetainStart1").val(data.ipRetainStart1);
        $("#edit-custom-ipRetainEnd1").val(data.ipRetainEnd1);
        $("#edit-custom-ipRetainStart2").val(data.ipRetainStart2);
        $("#edit-custom-ipRetainEnd2").val(data.ipRetainEnd2);
        $("#edit-custom-ipRetainStart3").val(data.ipRetainStart3);
        $("#edit-custom-ipRetainEnd3").val(data.ipRetainEnd3);
        $("#edit-custom-description").val(data.description);
   };
   this.initInput = function(){
	// 初始化组件
       $("#edit-custom-networkName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
       $("#edit-custom-subnet").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme,disabled:true});
       $("#edit-custom-subnetMask").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme,disabled:true});
       $("#edit-custom-gateway").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme,disabled:false});
       $("#edit-custom-dns1").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme,disabled:false});
       $("#edit-custom-dns2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme,disabled:false});
       $("#edit-custom-ipRetainStart1").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
       $("#edit-custom-ipRetainEnd1").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
       $("#edit-custom-ipRetainStart2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
       $("#edit-custom-ipRetainEnd2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
       $("#edit-custom-ipRetainStart3").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
       $("#edit-custom-ipRetainEnd3").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
       $("#edit-custom-description").jqxInput({placeHolder: "", height: 46, width:400, minLength: 1,theme:currentTheme});
       
       $("#editCustomNetworkSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
       $("#editCustomNetworkCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
       
       // 初始化下拉列表框 
		var codesearch = new codeModel({width:150,autoDropDownHeight:true,disabled:true});
		codesearch.getCommonCode("edit-custom-ipType","IP_TYPE",false);
		codesearch.getCommonCode("edit-custom-networkType_n1","NETWORK_TYPE",false);
   };
    // 初始化验证规则   
    this.initValidator = function(){
    	$('#editCustomNetworkForm').jqxValidator({
    		 rules: [  
	                  { input: '#edit-custom-networkName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-custom-networkName', message: '网络名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                  { input: '#edit-custom-subnet', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-custom-subnetMask', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  
	                  { input: '#edit-custom-gateway', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
	                	  if(input.val() == null || input.val() == ""){
                    	        return true; 
                    	  }else{
                    		  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	                    	   if(!pattern.test($("#edit-custom-gateway").val())){
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
		$('#editCustomNetworkForm').on('validationSuccess', function (event) {
			 var pool = Core.parseJSON($("#editCustomNetworkForm").serializeJson());
			 var param = {};
			 param.resNetworkSid = $("#edit-custom-resNetworkSid").val();
    		 var newObj = $.extend(pool,param);
    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/networks/update",
	 				params :newObj,
	 				callback : function (data) {
	 	            	me.initEditContent();
	 					// 刷新基本信息
	 	            	var custom = new mgtObjCustomNetworkModel();
	 					var rowindex = $('#jqxgridBiz').jqxGrid('getselectedrowindex');
 				   		var cusData = $('#jqxgridBiz').jqxGrid('getrowdata', rowindex);
	 					var mgtObjSid = cusData.mgtObjSid;
	 					custom.searchInfo(mgtObjSid);
	 					$("#editCustomNetworkWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editCustomNetworkWindow").jqxWindow('close');
	 			    }
	 			});
	     });
    };
	 // 初始化弹出window
	this.initPopWindow = function(){   	
		$("#editCustomNetworkWindow").jqxWindow({
            width: 600, 
            height:390,
            theme:currentTheme,
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#editCustomNetworkCancel"), 
            modalOpacity: 0.3
        });
		
	};
	
    // 判断操作按钮，初始化按钮
    this.initEditContent = function(){
    	// 初始化组件
        $("#edit-custom-networkName").val("");
        $("#edit-custom-subnet").val("");
        $("#edit-custom-subnetMask").val("");
        $("#edit-custom-gateway").val("");
        $("#edit-custom-dns1").val("");
        $("#edit-custom-dns2").val("");
        $("#edit-custom-ipType").val("");
        $("#edit-custom-networkType_n1").val("");
        $("#edit-custom-vlanId").val("");
        $("#edit-custom-ipRetainStart1").val("");
        $("#edit-custom-ipRetainEnd1").val("");
        $("#edit-custom-ipRetainStart2").val("");
        $("#edit-custom-ipRetainEnd2").val("");
        $("#edit-custom-ipRetainStart3").val("");
        $("#edit-custom-ipRetainEnd3").val("");
        
    };
    
};


// 提交将选择主机加入池
function editCustomNetworkSubmit(){
	$('#editCustomNetworkForm').jqxValidator('validate');
}
