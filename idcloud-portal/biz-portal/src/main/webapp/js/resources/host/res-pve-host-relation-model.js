var virtualPveHostRelationDatagridModel = function () {
	
	// 验证新增画面
	this.initValidator = function(){
		// 新增主机cpu池验证
    	$('#addPveHostCpuPoolForm').jqxValidator({
            rules: [  
                      { input: '#add-host-cpu-pool-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#add-host-cpu-pool-name', message: 'cpu池名称不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
                      
                      { input: '#add-host-cpu-pool-max', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#add-host-cpu-pool-max', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      
                      { input: '#add-host-cpu-pool-reserve', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#add-host-cpu-pool-reserve', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                     
                   ]
    	});
    	// 编辑主机cpu池验证
    	$('#editPveHostCpuPoolForm').jqxValidator({
            rules: [  
                      { input: '#edit-host-cpu-pool-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#edit-host-cpu-pool-name', message: 'cpu池名称不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
                      
                      { input: '#edit-host-cpu-pool-max', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#edit-host-cpu-pool-max', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      
                      { input: '#edit-host-cpu-pool-reserve', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#edit-host-cpu-pool-reserve', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                     
                   ]
    	});
    	
    	// 新增PhyIO验证
    	$('#addPveHostPhyIoForm').jqxValidator({
            rules: [  
                      { input: '#add-host_item_index', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#add-host_item_index', message: '配件类型编码不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
                      
                      { input: '#add-host_item_addr', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#add-host_item_addr', message: '配件类型编码不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
                      
                      { input: '#add-host-item_location', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#add-host-item_location', message: '配件类型编码不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
                     
                      { input: '#add-res_alloc_status', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                   ]
    	});
    	// 编辑PhyIO验证
    	$('#editPveHostPhyIoForm').jqxValidator({
            rules: [  
					  { input: '#edit-host_item_index', message: '不能为空', action: 'keyup, blur', rule: 'required' },
					  { input: '#edit-host_item_index', message: '配件类型编码不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
					
					  { input: '#edit-host_item_addr', message: '不能为空', action: 'keyup, blur', rule: 'required' },
					  { input: '#edit-host_item_addr', message: '配件类型编码不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
					
					  { input: '#edit-host-item_location', message: '不能为空', action: 'keyup, blur', rule: 'required' },
					  { input: '#edit-host-item_location', message: '配件类型编码不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
                     
					  { input: '#add-res_alloc_status', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                     
                   ]
    	});
    	
    	// 新增VIOS验证
    	$('#addPveHostViosForm').jqxValidator({
            rules: [  
                      { input: '#add-power-cpu-max-units', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#add-power-cpu-min-units', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#add-power-cpu-units', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#add-power-cpu-max-cores', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#add-power-cpu-min-cores', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#add-power-cpu-cores', message: '请输入数字！', action: 'keyup, blur', rule: 'number' }
                   ]
    	});
    	
    	// 编辑VIOS验证
    	$('#editPveHostViosForm').jqxValidator({
            rules: [  
                      { input: '#edit-power-cpu-max-units', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#edit-power-cpu-min-units', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#edit-power-cpu-units', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#edit-power-cpu-max-cores', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#edit-power-cpu-min-cores', message: '请输入数字！', action: 'keyup, blur', rule: 'number' },
                      { input: '#edit-power-cpu-cores', message: '请输入数字！', action: 'keyup, blur', rule: 'number' }
                   ]
    	});
    	
    	// 新增主机验证
    	$('#addPveHostCpuPoolForm').on('validationSuccess', function (event) {
	    		 var cpuPool = JSON.parse($("#addPveHostCpuPoolForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/cpuPools/create",
	 				params :cpuPool,
	 				callback : function (data) {
	 					 // 关闭window
	 					$("#addPveHostCpuPoolWindow").jqxWindow('close');
	 					 var pveHost = new virtualPveHostDatagridModel();
	 					 pveHost.initWithPowerHostDatagrid();
	 					 pveHost.searchPveHostCpuPoolInfo(resTopologySid);
	 			    }
	 			});
    	 });
    	// 编辑主机验证
    	$('#editPveHostCpuPoolForm').on('validationSuccess', function (event) {
	    		 var cpuPool = JSON.parse($("#editPveHostCpuPoolForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/cpuPools/update",
	 				params :cpuPool,
	 				callback : function (data) {
	 					 // 关闭window
	 					$("#editPveHostCpuPoolWindow").jqxWindow('close');
	 					 var pveHost = new virtualPveHostDatagridModel();
	 					 pveHost.initWithPowerHostDatagrid();
	 					 pveHost.searchPveHostCpuPoolInfo(resTopologySid);
	 			    }
	 			});
    	 });
    	
    	// 新增PhyIO验证
    	$('#addPveHostPhyIoForm').on('validationSuccess', function (event) {
	    		 var phyIo = JSON.parse($("#addPveHostPhyIoForm").serializeJson());
	    		 if(phyIo.resAllocFlag == "0"){
	    			 phyIo.resVmSid = "";
	    		 }
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/hostItems/create",
	 				params :phyIo,
	 				callback : function (data) {
	 					 // 关闭window
	 					$("#addPveHostPhyIoWindow").jqxWindow('close');
	 					 var pveHost = new virtualPveHostDatagridModel();
	 					 pveHost.initWithPowerHostDatagrid();
	 					 pveHost.searchPveHostCpuPoolInfo(resTopologySid);
	 			    }
	 			});
    	 });
    	
    	// 编辑PhyIO验证
    	$('#editPveHostPhyIoForm').on('validationSuccess', function (event) {
	    		 var phyIo = JSON.parse($("#editPveHostPhyIoForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/hostItems/update",
	 				params :phyIo,
	 				callback : function (data) {
	 					 // 关闭window
	 					$("#editPveHostPhyIoWindow").jqxWindow('close');
	 					 var pveHost = new virtualPveHostDatagridModel();
	 					 pveHost.initWithPowerHostDatagrid();
	 					 pveHost.searchPveHostCpuPoolInfo(resTopologySid);
	 			    }
	 			});
    	 });
    	
    	// 新增主机VIOS验证
    	$('#addPveHostViosForm').on('validationSuccess', function (event) {
	    		 var vios = JSON.parse($("#addPveHostViosForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/vioss/create",
	 				params :vios,
	 				callback : function (data) {
	 					 // 关闭window
	 					$("#addPveHostViosWindow").jqxWindow('close');
	 					 var pveHost = new virtualPveHostDatagridModel();
	 					 pveHost.initWithPowerHostDatagrid();
	 					 pveHost.searchPveHostCpuPoolInfo(resTopologySid);
	 			    }
	 			});
    	 });
    	
    	// 新增主机VIOS验证
    	$('#editPveHostViosForm').on('validationSuccess', function (event) {
	    		 var vios = JSON.parse($("#editPveHostViosForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/vioss/update",
	 				params :vios,
	 				callback : function (data) {
	 					 // 关闭window
	 					$("#editPveHostViosWindow").jqxWindow('close');
	 					 var pveHost = new virtualPveHostDatagridModel();
	 					 pveHost.initWithPowerHostDatagrid();
	 					 pveHost.searchPveHostCpuPoolInfo(resTopologySid);
	 			    }
	 			});
    	 });
    	
	};
	 // 初始化window
    this.initPopWindow = function(){
    	// 新增主机CPU池
    	$("#addPveHostCpuPoolWindow").jqxWindow({
            width: 350, 
            height:170,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#addPvehostCpuPoolCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化新增用户页面组件
            	$("#add-host-cpu-pool-name").jqxInput({placeHolder: "", height: 23, width: 220, minLength: 1,theme:currentTheme});
            	$("#add-host-cpu-pool-max").jqxInput({placeHolder: "", height: 23, width: 220, minLength: 1,theme:currentTheme});
            	$("#add-host-cpu-pool-reserve").jqxInput({placeHolder: "", height: 23, width: 220, minLength: 1,theme:currentTheme});
            	
            	$("#addPvehostCpuPoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
    	    	$("#addPvehostCpuPoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            }
        });
    	// 编辑主机CPU池
    	$("#editPveHostCpuPoolWindow").jqxWindow({
            width: 350, 
            height:170,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#editPvehostCpuPoolCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化新增用户页面组件
            	$("#edit-host-cpu-pool-name").jqxInput({placeHolder: "", height: 23, width: 220, minLength: 1,theme:currentTheme});
            	$("#edit-host-cpu-pool-max").jqxInput({placeHolder: "", height: 23, width: 220, minLength: 1,theme:currentTheme});
            	$("#edit-host-cpu-pool-reserve").jqxInput({placeHolder: "", height: 23, width: 220, minLength: 1,theme:currentTheme});
            	
            	$("#editPvehostCpuPoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
    	    	$("#editPvehostCpuPoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            }
        });
    	// 新增虚拟机换机
    	$("#addPveHostVsWindow").jqxWindow({
            width: 400, 
            height:100,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#addPvehostVsCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	
            	$("#addPvehostVsSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
    	    	$("#addPvehostVsCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            }
        });
    	
    	// 新增phyIo
    	$("#addPveHostPhyIoWindow").jqxWindow({
            width: 600, 
            height:260,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#addPvehostPhyIoCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	
            	$("#add-host_item_index").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-host_item_addr").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-host-item_location").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
            	
            	$("#add-res_alloc_status").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
            	
            	$("#add-host_item_desc").jqxInput({placeHolder: "", height: 60, width: 450, minLength: 1,theme:currentTheme});
            	
            	$("#addPvehostPhyIoSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
    	    	$("#addPvehostPhyIoCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            }
        });
    	
    	// 编辑phyIo
    	$("#editPveHostPhyIoWindow").jqxWindow({
            width: 600, 
            height:260,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#editPvehostPhyIoCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	$("#edit-host_item_index").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-host_item_addr").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-host-item_location").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
            	
            	$("#edit-res_alloc_status").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-host_item_desc").jqxInput({placeHolder: "", height: 60, width: 450, minLength: 1,theme:currentTheme});
            	
            	$("#editPvehostPhyIoSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
    	    	$("#editPvehostPhyIoCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            }
        });
    	
    	// 新增vios
    	$("#addPveHostViosWindow").jqxWindow({
            width: 700, 
            height:160,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#addPvehostViosCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	
            	$("#add-power-cpu-max-units").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-power-cpu-min-units").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-power-cpu-units").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-power-cpu-max-cores").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-power-cpu-min-cores").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-power-cpu-cores").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	
            	$("#add-vios-user").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#add-vios-password").jqxPasswordInput({placeHolder: "", height: 23, width: 120, showStrength: false, showStrengthPosition: "right",theme:currentTheme});
            	$("#add-vios-ip").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	
            	$("#addPvehostViosSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
    	    	$("#addPvehostViosCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            }
        });
    	
    	// 编辑vios
    	$("#editPveHostViosWindow").jqxWindow({
            width: 700, 
            height:160,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#editPvehostViosCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	
            	$("#edit-power-cpu-max-units").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-power-cpu-min-units").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-power-cpu-units").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-power-cpu-max-cores").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-power-cpu-min-cores").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-power-cpu-cores").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	
            	$("#edit-vios-user").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	$("#edit-vios-password").jqxPasswordInput({placeHolder: "", height: 23, width: 120, showStrength: false, showStrengthPosition: "right",theme:currentTheme});
            	$("#edit-vios-ip").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
            	
            	$("#editPvehostViosSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
    	    	$("#editPvehostViosCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            }
        });
    };
};


// 弹出新增主机cpu池
function popAddPveHostCpuPool(){
	// 清空数据
	$("#add-host-cpu-pool-sid").val(resTopologySid);
	$("#add-host-cpu-pool-name").val("");
	$("#add-host-cpu-pool-max").val("");
	$("#add-host-cpu-pool-reserve").val("");

	
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#addPveHostCpuPoolWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-170)/2 } });
	$("#addPveHostCpuPoolWindow").jqxWindow('open');
}

// 提交新增主机cpu池
function submitPvehostCpuPoolInfo(){
	$('#addPveHostCpuPoolForm').jqxValidator('validate');
}

// 弹出编辑主机cpu池
function popEditPveHostCpuPool(){
	var rowindex = $('#pveHostCpuPoolDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#pveHostCpuPoolDatagrid').jqxGrid('getrowdata', rowindex);
		$("#edit-host-cpu-pool-sid").val(data.resCpuPoolSid);
		$("#edit-host-cpu-pool-name").val(data.cpuPoolName);
		$("#edit-host-cpu-pool-max").val(data.maxValue);
		$("#edit-host-cpu-pool-reserve").val(data.reservedValue);
		
		var windowW = $(window).width(); 
		var windowH = $(window).height(); 
		$("#editPveHostCpuPoolWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-170)/2 } });
		$("#editPveHostCpuPoolWindow").jqxWindow('open');
   	}
}

//提交更新主机cpu池
function submitEditPvehostCpuPoolInfo(){
	$('#editPveHostCpuPoolForm').jqxValidator('validate');
}

// 删除主机CpuPool
function deletePveHostCpuPool(){
	var rowindex = $('#pveHostCpuPoolDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#pveHostCpuPoolDatagrid').jqxGrid('getrowdata', rowindex);
	   	Core.confirm({
			title:"提示",
			message:"确定要删该主机Cpu池吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
					url : ws_url + "/rest/cpuPools/delete/"+data.resCpuPoolSid,
					type:"get",
					callback : function (data) {
						 // 关闭window
						 var pveHost = new virtualPveHostDatagridModel();
						 pveHost.initWithPowerHostDatagrid();
						 pveHost.searchPveHostCpuPoolInfo(resTopologySid);
				    }
			    });
			}
		});
   	}
}

// 弹出新增虚拟交换机window
function popAddPveHostVsWindow(){
	// 初始化新增用户页面组件
	var codesearch = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth:150});
	codesearch.getCustomCode("add-vs-sid","/vss/findPveHostVs/"+resTopologySid+"","resVsName","resVsSid",false,"GET",null);
	
	$("#add-host-vs-sid").val(resTopologySid);
	// 清空数据
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#addPveHostVsWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-100)/2 } });
	$("#addPveHostVsWindow").jqxWindow('open');
}

// 提交新增交换虚拟机信息
function submitAddPvehostVsInfo(){
	 var vs = JSON.parse($("#addPveHostVsForm").serializeJson());
	 Core.AjaxRequest({
			url : ws_url + "/rest/vss/create/vshost",
			params :vs,
			callback : function (data) {
				 // 关闭window
				$("#addPveHostVsWindow").jqxWindow('close');
				 var pveHost = new virtualPveHostDatagridModel();
				 pveHost.initWithPowerHostDatagrid();
				 pveHost.searchPveHostCpuPoolInfo(resTopologySid);
		    }
	  });
}

// 删除交换机
function deletePveHostVs(){
	var rowindex = $('#pveHostDvsDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#pveHostDvsDatagrid').jqxGrid('getrowdata', rowindex);
	   	Core.confirm({
			title:"提示",
			message:"确定要删该虚拟交换机吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
					url : ws_url + "/rest/vss/delete/vshost",
					params :{
						resHostSid : data.resHostSid,
					    resVsSid : data.resVsSid
					},
					callback : function (data) {
						 // 关闭window
						 var pveHost = new virtualPveHostDatagridModel();
						 pveHost.initWithPowerHostDatagrid();
						 pveHost.searchPveHostCpuPoolInfo(resTopologySid);
				    }
			    });
			}
	     });
   	}
}

// 弹出新增Phy IO窗口
function popAddPveHostPhyIoWindow(){
	
	$("#add-host-phy-io-sid").val(resTopologySid);
	$("#add-host_item_index").val("");
	$("#add-host_item_addr").val("");
	$("#add-host-item_location").val("");
	$("#add-res_alloc_status").val("");
	$("#add-host_item_desc").val("");
	
	var host = new codeModel({width:150,autoDropDownHeight:true});
	host.getCommonCode("add-host-item-type-code","HOST_ITEM_TYPE_CODE");
	host.getCommonCode("add-res_alloc_flag","RES_ALLOC_FLAG");
	var hostIo = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth : 150,});
	hostIo.getCustomCode("add-res_vm_sid", "/vms", "vmName", "resVmSid", false, "POST", {allocateResHostSid : resTopologySid});
	
	$(".allotZoon").hide();
	// 清空数据
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#addPveHostPhyIoWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-230)/2 } });
	$("#addPveHostPhyIoWindow").jqxWindow('open');
}

//提交更新主机cpu池
function submitPvehostPhyIoInfo(){
	$('#addPveHostPhyIoForm').jqxValidator('validate');
}

//弹出编辑Phy IO窗口
function popEditPveHostPhyIoWindow(){
	
	var rowindex = $('#pveHostPhysicalDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
   		
	   	var data = $('#pveHostPhysicalDatagrid').jqxGrid('getrowdata', rowindex);
	   	var host = new codeModel({width:150,autoDropDownHeight:true,disabled:false});
    	host.getCommonCode("edit-host-item-type-code","HOST_ITEM_TYPE_CODE");
    	var hostfalg = new codeModel({width:150,autoDropDownHeight:true,disabled:true});
    	hostfalg.getCommonCode("edit-res_alloc_flag","RES_ALLOC_FLAG");
    	var hostIo = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth : 150,});
    	hostIo.getCustomCode("edit-res_vm_sid", "/vms", "vmName", "resVmSid", false, "POST", {allocateResHostSid : resTopologySid});
    	
    	if( data.resVmSid == "0"){
 	    	$(".allotZoon").hide();
 	    }else{
 	    	$(".allotZoon").show();
 	    }
    	
		$("#edit-host-phy-io-sid").val(data.hostItemId);
		$("#edit-host-item-type-code").val(data.hostItemTypeCode);
    	$("#edit-host_item_index").val(data.hostItemIndex);
    	$("#edit-host_item_addr").val(data.hostItemAddr);
    	$("#edit-host-item_location").val(data.itemLocation);
    	
    	$("#edit-res_alloc_flag").val(data.resAllocFlag);
    	$("#edit-res_vm_sid").val(data.resVmSid);
    	$("#edit-res_alloc_status").val(data.resAllocStatus);
    	
    	$("#edit-part_amount").val(data.partAmount);
    	$("#edit-sub_part_amount").val(data.subPartAmount);
    	$("#edit-host_item_desc").val(data.hostItemDesc);
		
		// 清空数据
		var windowW = $(window).width(); 
		var windowH = $(window).height(); 
		$("#editPveHostPhyIoWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-230)/2 } });
		$("#editPveHostPhyIoWindow").jqxWindow('open');
   	}
}

function submitEditPvehostPhyIoInfo(){
	$('#editPveHostPhyIoForm').jqxValidator('validate');
}

// 删除配件
function deletePveHostItem(){
	var rowindex = $('#pveHostPhysicalDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#pveHostPhysicalDatagrid').jqxGrid('getrowdata', rowindex);
	   	Core.confirm({
			title:"提示",
			message:"确定要删该主机配件吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
					url : ws_url + "/rest/hostItems/delete/"+data.hostItemId,
					type:"get",
					callback : function (data) {
						 // 关闭window
						 var pveHost = new virtualPveHostDatagridModel();
						 pveHost.initWithPowerHostDatagrid();
						 pveHost.searchPveHostCpuPoolInfo(resTopologySid);
				    }
			    });
			}
		});
   	}
}

// 弹出新增主机Vios
function popAddPveHostViosWindow(){
	$("#add-host-vios-sid").val(resTopologySid);
	
	$("#add-power-cpu-max-units").val("");
	$("#add-power-cpu-min-units").val("");
	$("#add-power-cpu-units").val("");
	$("#add-power-cpu-max-cores").val("");
	$("#add-power-cpu-min-cores").val("");
	$("#add-power-cpu-cores").val("");
	$("#add-vios-user").val("");
	$("#add-vios-password").val("");
	$("#add-vios-ip").val("");
	
	// 清空数据
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#addPveHostViosWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-160)/2 } });
	$("#addPveHostViosWindow").jqxWindow('open');
}

// 提交新增主机VIOS信息
function submitAddPvehostViosInfo(){
	$('#addPveHostViosForm').jqxValidator('validate');
}

//弹出编辑主机Vios
function popEditPveHostViosWindow(){
	var rowindex = $('#pveHostViosDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#pveHostViosDatagrid').jqxGrid('getrowdata', rowindex);
		
		$("#edit-host-vios-sid").val(data.resViosId);
		$("#edit-power-cpu-max-units").val(data.powerCpuMaxUnits);
    	$("#edit-power-cpu-min-units").val(data.powerCpuMinUnits);
    	$("#edit-power-cpu-units").val(data.powerCpuUnits);
    	$("#edit-power-cpu-max-cores").val(data.powerCpuMaxCores);
    	$("#edit-power-cpu-min-cores").val(data.powerCpuMinCores);
    	$("#edit-power-cpu-cores").val(data.powerCpuCores);
    	
    	$("#edit-vios-user").val(data.user);
    	$("#edit-vios-password").val(data.password);
    	$("#edit-vios-ip").val(data.ip);
		
    	// 清空数据
    	var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#editPveHostViosWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-160)/2 } });
    	$("#editPveHostViosWindow").jqxWindow('open');
   	}
	
}

// 提交编辑主机VIOS信息
function submitEditPvehostViosInfo(){
	$('#editPveHostViosForm').jqxValidator('validate');
}

// 删除主机Vios信息
function deletePveHostVios(){
	var rowindex = $('#pveHostViosDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#pveHostViosDatagrid').jqxGrid('getrowdata', rowindex);
	   	Core.confirm({
			title:"提示",
			message:"确定要删该主机VIOS吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
					url : ws_url + "/rest/vioss/delete/"+data.resViosId,
					type:"get",
					callback : function (data) {
						 // 关闭window
						 var pveHost = new virtualPveHostDatagridModel();
						 pveHost.initWithPowerHostDatagrid();
						 pveHost.searchPveHostCpuPoolInfo(resTopologySid);
				    }
			    });
			}
		});
   	}
}
