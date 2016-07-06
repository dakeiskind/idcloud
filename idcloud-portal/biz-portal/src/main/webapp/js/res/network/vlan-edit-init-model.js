// 编辑主机 
var editVlanModel = function () {
	var me = this;

	// 设置修改值
	this.setEditVlanForm = function(data){
   	    $("#edit-vlanId").val(data.vlanId);
        $("#edit-vlanName").val(data.vlanName);
        $("#edit-tag").val(data.tag);

    };
    // 初始化验证规则   
    this.initValidator = function(){
    	$('#editVlanForm').jqxValidator({
            rules: [  
                      { input: '#edit-vlanId', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                      { input: '#edit-vlanId', message: 'VLAN不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
                      
                      { input: '#edit-vlanName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                      { input: '#edit-vlanName', message: 'VLAN名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' }
                   ]
    	});
    	
    	// 编辑块存储验证成功
    	$('#editVlanForm').on('validationSuccess', function (event) {
    		 var vlans = Core.parseJSON($("#editVlanForm").serializeJson());
    		 Core.AjaxRequest({
 				url : ws_url + "/rest/vlans/update",
 				params :vlans,
 				callback : function (data) {
 	            	me.initEditContent();
 	            	var vlan = new vlanConfigMgtModel();
 					vlan.searchVlanConfigInfo();
					$("#editVlanWindow").jqxWindow('close');
 			    },
 			    failure:function(data){
					$("#editVlanWindow").jqxWindow('close');
 			    }
 			});
	     });
    };
	 // 初始化弹出window
	this.initPopWindow = function(){   	
		$("#editVlanWindow").jqxWindow({
            width: 500, 
            height:150,
            theme:currentTheme,
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#editVlanCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化新增用户页面组件
    	        $("#edit-vlanId").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	        $("#edit-vlanName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	        $("#edit-tag").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	        $("#editVlanSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
    	        $("#editVlanCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
            }
        });
	};
	
	 // 初始化编辑值
    this.initEditContent = function(){
    	// 初始化组件
        $("#edit-vlanId").val("");
        $("#edit-vlanName").val("");
        $("#edit-tag").val("");
    };
    
};

//弹出编辑vlan框
function editVlanInfoWindow(){
	var rowindex = $('#vlanConfigMgtdatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		var data = $('#vlanConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
		// 为编辑赋值
		$("#resSidVlan").val(data.resSid);
		var vlan = new editVlanModel();
		vlan.setEditVlanForm(data);
		// 设置弹出框位置
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#editVlanWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-150)/2 } });
    	$("#editVlanWindow").jqxWindow('open');
	}
}

//提交编辑VLAN信息
function editVlanSubmit(){
	$('#editVlanForm').jqxValidator('validate');
};
  