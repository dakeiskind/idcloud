// 编辑主机 
var editIpModel = function () {
	var me = this;

	// 设置修改值
    this.setEditIpForm = function(data){
   	 	$("#edit-ipAddressStart-ip1").val(data.ipAddress);
        $("#edit-subnetMask-ip1").val(data.subnetMask);
        $("#edit-dns-ip1").val(data.dns);
        $("#edit-gateway-ip1").val(data.gateway);
        $("#edit-comments-ip1").val(data.comments);
        $("#edit-ipType-ip-ip1").val(data.ipType);
        $("#edit-ipCategory-ip1").val(data.ipCategory);
        $("#edit-vlanId-ip1").val(data.vlanId);
        $("#edit-mapPublicIp-ip1").val(data.mapPublicIp);

   };
    // 初始化验证规则   
    this.initValidator = function(){
    	$('#editIpForm').jqxValidator({
	        rules: [  
	                  { input: '#edit-ipAddressStart-ip1', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#edit-ipAddressStart-ip1', message: 'ip起始地址不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
		               ]
			});
		    	
    	// 更新ip池验证成功
		$('#editIpForm').on('validationSuccess', function (event) {
			 var pool = Core.parseJSON($("#editIpForm").serializeJson());
			 var param = {};
			 param.resTopologySid = resTopologySid;
			 param.isResPoolSearch = resTopologyType;
			 param.resSid = $("#edit-resSid-ip1").val();
    		 var newObj = $.extend(pool,param);
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/ips/update",
	 				params :newObj,
	 				callback : function (data) {
	 	            	me.initEditContent();
	 					// 刷新基本信息
	 					var ip = new ipConfigMgtModel();
	 					ip.searchIpConfigInfo();
						$("#editIpWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
						$("#editIpWindow").jqxWindow('close');
	 			    }
	 			});
	     });
    };
	 // 初始化弹出window
	this.initPopWindow = function(){   	
		$("#editIpWindow").jqxWindow({
            width: 600, 
            height:250,
            theme:currentTheme,
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#editIpCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化组件
                $("#edit-ipAddressStart-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,disabled: true, theme:currentTheme});
                $("#edit-subnetMask-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
                $("#edit-dns-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
                $("#edit-gateway-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
                $("#edit-comments-ip1").jqxInput({placeHolder: "", height: 46, width:500, minLength: 1,theme:currentTheme});
                $("#edit-mapPublicIp-ip1").jqxInput({placeHolder: "", height: 22, width:100, minLength: 1,theme:currentTheme});

                $("#editIpSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
                $("#editIpCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
                
                // 初始化下拉列表框 
        		 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
        		 codesearch.getCommonCode("edit-ipType-ip1","IP_TYPE",false);
        		 codesearch.getCommonCode("edit-ipCategory-ip1","IP_CATEGORY",false);
        		 codesearch.getCustomCode("edit-vlanId-ip1","/vlans","vlanName","vlanId",false);          }
        });
		
	};
	
    // 判断操作按钮，初始化按钮
    this.initEditContent = function(){
    	// 初始化组件
        $("#edit-ipAddressStart-ip1").val("");
        $("#edit-subnetMask-ip1").val("");
        $("#edit-dns-ip1").val("");
        $("#edit-gateway-ip1").val("");
        $("#edit-comments-ip1").val("");
        $("#edit-ipType-ip1").val("");
        $("#edit-ipCategory-ip1").val("");
        $("#edit-vlanId-ip1").val("");
        $("#edit-mapPublicIp-ip1").val("");
        
    };
    
};

//弹出编辑ip框
function editIpInfoWindow(){
	var rowindex = $('#ipConfigMgtdatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		var data = $('#ipConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
		var ip = new editIpModel();
		// 为编辑赋值
		$("#edit-resSid-ip1").val(data.resSid);
		// 编辑框赋值
		ip.setEditIpForm(data);
		// 设置弹出框位置
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#editIpWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-250)/2 } });
    	$("#editIpWindow").jqxWindow('open');
	}
}

// 提交将选择主机加入池
function editIpSubmit(){
	$('#editIpForm').jqxValidator('validate');
}
  