// 编辑网络
var editPneNetworkModel = function () {
	var me = this;
	// 设置修改值
    this.setEditNetworkForm = function(data){
    	$("#edit-pne-networkName").val(data.networkName);
        $("#edit-pne-subnet").val(data.subnet);
        $("#edit-pne-subnetMask").val(data.subnetMask);
        $("#edit-pne-gateway").val(data.gateway);
        $("#edit-pne-dns1").val(data.dns1);
        $("#edit-pne-dns2").val(data.dns2);
        $("#edit-pne-ipType").val(data.ipType);
        $("#edit-pne-networkType_n1").val(data.networkType);
        $("#edit-pne-vlanId-network").val(data.vlanId);
        $("#edit-pne-ipRetainStart1").val(data.ipRetainStart1);
        $("#edit-pne-ipRetainEnd1").val(data.ipRetainEnd1);
        $("#edit-pne-ipRetainStart2").val(data.ipRetainStart2);
        $("#edit-pne-ipRetainEnd2").val(data.ipRetainEnd2);
        $("#edit-pne-ipRetainStart3").val(data.ipRetainStart3);
        $("#edit-pne-ipRetainEnd3").val(data.ipRetainEnd3);
        $("#edit-pne-description").val(data.description);
        $("#edit-pne-resTopologySid").val(data.parentTopologySid);
   };
   this.initInput = function(){
	// 初始化组件
       $("#edit-pne-networkName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
       $("#edit-pne-subnet").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme,disabled:true});
       $("#edit-pne-subnetMask").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme,disabled:true});
       $("#edit-pne-gateway").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme,disabled:false});
       $("#edit-pne-dns1").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme,disabled:false});
       $("#edit-pne-dns2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme,disabled:false});
//       $("#edit-pne-vlanId-network").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme,disabled:true});
       $("#edit-pne-ipRetainStart1").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
       $("#edit-pne-ipRetainEnd1").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
       $("#edit-pne-ipRetainStart2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
       $("#edit-pne-ipRetainEnd2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
       $("#edit-pne-ipRetainStart3").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
       $("#edit-pne-ipRetainEnd3").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
       $("#edit-pne-description").jqxInput({placeHolder: "", height: 46, width:400, minLength: 1,theme:currentTheme});
       
       $("#editPneNetworkSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
       $("#editPneNetworkCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
       
       // 初始化下拉列表框 
		var codesearch = new codeModel({width:150,autoDropDownHeight:true,disabled:true});
		codesearch.getCommonCode("edit-pne-ipType","IP_TYPE",false);
		codesearch.getCommonCode("edit-pne-networkType_n1","NETWORK_TYPE",false);
		
		var codesearch1 = new codeModel({width:150,autoDropDownHeight:true,disabled:false});
		//codesearch1.getCustomCode("edit-pne-vlanId-network","/vlanRes","vlanId","vlanId",false,"POST",{unused:""});
		//$("#edit-pne-vlanId-network").jqxDropDownList({ autoDropDownHeight:false,dropDownWidth:150,dropDownHeight:200});
		
   };
    // 初始化验证规则   
    this.initValidator = function(){
    	$('#editPneNetworkForm').jqxValidator({
    		 rules: [  
	                  { input: '#edit-pne-networkName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-pne-networkName', message: '网络名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                  { input: '#edit-pne-subnet', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-pne-subnetMask', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  
	                  { input: '#edit-pne-gateway', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
	                	  if(input.val() == null || input.val() == ""){
                    	        return true; 
                    	  }else{
                    		  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	                    	   if(!pattern.test($("#edit-pne-gateway").val())){
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
		$('#editPneNetworkForm').on('validationSuccess', function (event) {
			 var pool = Core.parseJSON($("#editPneNetworkForm").serializeJson());
			 var param = {};
			 param.resNetworkSid = $("#edit-pne-resNetworkSid").val();
    		 var newObj = $.extend(pool,param);
    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/networks/update",
	 				params :newObj,
	 				callback : function (data) {
	 	            	me.initEditContent();
	 					// 刷新基本信息
	 	            	var pne = new poolPneDatagridModel();
	 	            	pne.searchPoolPneInfo();
	 					$("#editPneNetworkWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editPneNetworkWindow").jqxWindow('close');
	 			    }
	 			});
	     });
    };
	 // 初始化弹出window
	this.initPopWindow = function(){   	
		$("#editPneNetworkWindow").jqxWindow({
            width: 600, 
            height:390,
            theme:currentTheme,
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#editPneNetworkCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
        	   var codesearch2 = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth:150,disabled:false});
               codesearch2.getCustomCode("edit-pne-resTopologySid","/objStorages/findVeBySid","resTopologyName","resTopologySid",false,"POST",{resTopologySid:resTopologySid});
            }
        });
		
	};
	
    // 判断操作按钮，初始化按钮
    this.initEditContent = function(){
    	// 初始化组件
        $("#edit-pne-networkName").val("");
        $("#edit-pne-subnet").val("");
        $("#edit-pne-subnetMask").val("");
        $("#edit-pne-gateway").val("");
        $("#edit-pne-dns1").val("");
        $("#edit-pne-dns2").val("");
        $("#edit-pne-ipType").val("");
        $("#edit-pne-networkType_n1").val("");
        $("#edit-pne-vlanId").val("");
        $("#edit-pne-ipRetainStart1").val("");
        $("#edit-pne-ipRetainEnd1").val("");
        $("#edit-pne-ipRetainStart2").val("");
        $("#edit-pne-ipRetainEnd2").val("");
        $("#edit-pne-ipRetainStart3").val("");
        $("#edit-pne-ipRetainEnd3").val("");
        $("#edit-pne-resTopologySid").val("");
        
    };
    
};


// 提交将选择主机加入池
function editPneNetworkSubmit(){
	$('#editPneNetworkForm').jqxValidator('validate');
}
