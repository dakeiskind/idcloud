var vmInfoModel = function(resVmSid){
	var me = this;
    this.items = ko.observableArray();
    this.softWareItems = ko.observableArray();
    this.storageItems = ko.observableArray();
    this.userItems = ko.observableArray();
    
	// 设置基本信息
	this.setVmBasicInfo = function(){
		Core.AjaxRequest({
			url : ws_url + "/rest/vms/getVmInfo",
			type:"post",
			async:false,
			params:{
				resVmSid:resVmSid
			},
			callback : function(data) {
				// 计算cpu使用率,内存使用率
				if((data.managementAccount == null ||data.managementAccount == "")&&(data.managementPassword == null ||data.managementPassword == "")){
					$("#accountPasswd").html(""+"&nbsp;&nbsp;<font onclick='popVmMgtUserAndPasswordName(\""+data.resVmSid+"\")'><i class='icons-blue icon-pencil'></i></font>");
				}else{
					$("#accountPasswd").html(data.managementAccount+"/"+data.managementPassword+"&nbsp;&nbsp;<font onclick='popVmMgtUserAndPasswordName(\""+data.resVmSid+"\")'><i class='icons-blue icon-pencil'></i></font>");
				}
				
				if(data.osName == null ||data.osName == ""){
					$("#osName").html(""+"&nbsp;&nbsp;<font onclick='popEditOsName(\""+data.resVmSid+"\",\""+data.osName+"\")'><i class='icons-blue icon-pencil'></i></font>");
				}else{
					$("#osName").html(data.osName+"&nbsp;&nbsp;<font onclick='popEditOsName(\""+data.resVmSid+"\",\""+data.osName+"\")'><i class='icons-blue icon-pencil'></i></font>");
				}
				
				// 计算内存使用率
				var memoryRate = ((data.useMemorySize/data.memorySize)*100).toFixed(2);
//				$("#allocateVmName").html(data.vmName+"&nbsp;&nbsp;<font onclick='popEditVmName(\""+data.resVmSid+"\")'><i class='icons-blue icon-pencil'></i></font>");
				$("#allocateVmName").html(data.vmName);
				$("#provisionStorage").html(data.provisionStorage);
				$("#statusName").html(data.statusName);
				$("#osTypeName").html(data.osVersionName);
				$("#tenantName").html(data.tenantName);
				$("#hostName1").html(data.ownerHost);
				$("#ip").html(data.vmIp);
				$("#cpuCores").html(data.cpuCores==null?data.powerCpuUsedUnits:data.cpuCores);
				$("#memorySize").html(data.memorySize);
				$("#vmCreateTime").html(data.createdDt);
			}
		});
	};
	
	   // 初始化验证规则   
	   this.initValidator = function(){
	   	$('#addVmUserForm').jqxValidator({
	           rules: [  
	                     { input: '#add-vm-user-name', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#add-vm-user-name', message: '账户名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                     { input: '#add-vm-user-password', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#add-vm-user-password', message: '账户名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
		                 
	                  ]
	   	});
	   	
	   	$('#editVmUserForm').jqxValidator({
	           rules: [  
	                     { input: '#edit-vm-user-name', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#edit-vm-user-name', message: '账户名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                     { input: '#edit-vm-user-password', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#edit-vm-user-password', message: '账户名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                  ]
	   	});
	   	
	   	$('#editVmMgtUserForm').jqxValidator({
	           rules: [  
	                     { input: '#edit-vm-mgt-user-name', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#edit-vm-mgt-user-name', message: '账户名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                     { input: '#edit-vm-mgt-user-password', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#edit-vm-mgt-user-password', message: '账户名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                  ]
	   	});
	   	
	   	$('#editVmOsNameForm').jqxValidator({
	           rules: [  
	                     { input: '#edit-vm-os-name', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#edit-vm-os-name', message: '操作系统名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     	                     
	                  ]
	   	});
	   	
	   	// 新增磁盘验证
	   	$('#addVmVdForm').jqxValidator({
	           rules: [  
	                     { input: '#add-vm-vd-name', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#add-vm-vd-name', message: '磁盘名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                     { input: '#add-vm-vd-logicVolume', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#add-vm-vd-logicVolume', message: '逻辑卷名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                     { input: '#add-vm-vd-size', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#add-vm-vd-size', message: '请输入数字!', action: 'keyup, blur', rule: 'number' },
	                     
	                     { input: '#add-vm-vd-devicePath', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#add-vm-vd-devicePath', message: '磁盘路径不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                     { input: '#add-vm-vd-mountPoint', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#add-vm-vd-mountPoint', message: '挂载点不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                     { input: '#add-vm-vd-fileSystemType', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#add-vm-vd-fileSystemType', message: '文件系统类型不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     	                     
	                  ]
	   	});
	   	
		// 编辑磁盘验证
	   	$('#editVmVdForm').jqxValidator({
	           rules: [  
	                     { input: '#edit-vm-vd-name', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#edit-vm-vd-name', message: '操作系统名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                     { input: '#edit-vm-vd-logicVolume', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#edit-vm-vd-logicVolume', message: '操作系统名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                     { input: '#edit-vm-vd-size', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#edit-vm-vd-size', message: '请输入数字!', action: 'keyup, blur', rule: 'number' },
	                     
	                     { input: '#edit-vm-useDiskSize', message: '请输入数字!', action: 'keyup, blur', rule: 'number' },
	                     
	                     { input: '#edit-vm-vd-devicePath', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#edit-vm-vd-devicePath', message: '操作系统名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                     { input: '#edit-vm-vd-mountPoint', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#edit-vm-vd-mountPoint', message: '操作系统名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                     { input: '#edit-vm-vd-fileSystemType', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#edit-vm-vd-fileSystemType', message: '操作系统名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     	                     
	                  ]
	   	});
	   	
	   	// 新增软件验证
	   	$('#addVmSoftwareForm').jqxValidator({
	           rules: [  
	                     
	                  ]
	   	});
	   	
	   	// 编辑软件验证
	   	$('#editVmSoftwareForm').jqxValidator({
	           rules: [  
	                     
	                  ]
	   	});
	   	
	   	// 编辑块存储验证成功
	   	$('#addVmUserForm').on('validationSuccess', function (event) {
	   		 var vmuser = JSON.parse($("#addVmUserForm").serializeJson());
	   		 Core.AjaxRequest({
					url : ws_url + "/rest/osUsers/platform/create",
					params :vmuser,
					async:false,
					callback : function (data) {
						 var info = new vmInfoModel(resVmSid);
						 info.initVmUserDatagrid();
						 info.searchVmUserInfo();
						$("#addVmUserWindow").jqxWindow('close');
				    }
				});
		});
	   	
	    // 编辑块存储验证成功
	   	$('#editVmUserForm').on('validationSuccess', function (event) {
	   		 var vmuser = JSON.parse($("#editVmUserForm").serializeJson());
	   		 Core.AjaxRequest({
					url : ws_url + "/rest/osUsers/update",
					params :vmuser,
					async:false,
					callback : function (data) {
						var info = new vmInfoModel(resVmSid);
						info.initVmUserDatagrid();
						info.searchVmUserInfo();
						$("#editVmUserWindow").jqxWindow('close');
				    }
				});
		});
	   	
	   	// 编辑块存储验证成功
	   	$('#editVmMgtUserForm').on('validationSuccess', function (event) {
	   		 var vmuser = JSON.parse($("#editVmMgtUserForm").serializeJson());
	   		 Core.AjaxRequest({
					url : ws_url + "/rest/vms/update",
					params :vmuser,
					callback : function (data) {
						me.setVmBasicInfo();
						$("#editVmMgtUserWindow").jqxWindow('close');
				    }
			 });
		});
	   	
	   	// 编辑块存储验证成功
	   	$('#editVmOsNameForm').on('validationSuccess', function (event) {
	   		 var vmuser = JSON.parse($("#editVmOsNameForm").serializeJson());
	   		 Core.AjaxRequest({
					url : ws_url + "/rest/vms/update",
					params :vmuser,
					callback : function (data) {
						me.setVmBasicInfo();
						$("#editVmOsNameWindow").jqxWindow('close');
				    }
			 });
		});
	   	
	   	// 新增磁盘
	   	$('#addVmVdForm').on('validationSuccess', function (event) {
	   		 var storage = JSON.parse($("#addVmVdForm").serializeJson());
	   		 // 判断是否选择了存储
	   		 if($("#add-vm-vd-storage").val() == null || $("#add-vm-vd-storage").val() == ""){
	   			Core.alert({
					  title:"提示",
					  message:'请选择存储!'
				});
	   			return false;
	   		 }
	   		 Core.AjaxRequest({
					url : ws_url + "/rest/vds/create",
					params :storage,
					callback : function (data) {
						var info = new vmInfoModel(resVmSid);
						info.initVmStorageDatagrid();
						info.searchVmStorageInfo();
						$("#addVmVdWindow").jqxWindow('close');
				    }
			 });
		});
	   	
	   	// 编辑磁盘
	   	$('#editVmVdForm').on('validationSuccess', function (event) {
	   		 var storage = JSON.parse($("#editVmVdForm").serializeJson());
	   		 
	   		 Core.AjaxRequest({
					url : ws_url + "/rest/vds/update",
					params :storage,
					callback : function (data) {
						var info = new vmInfoModel(resVmSid);
						info.initVmStorageDatagrid();
						info.searchVmStorageInfo();
						$("#editVmVdWindow").jqxWindow('close');
				    }
			 });
		});
	   	
	   	// 新增软件
	   	$('#addVmSoftwareForm').on('validationSuccess', function (event) {
	   		 var software = JSON.parse($("#addVmSoftwareForm").serializeJson());

	   		 Core.AjaxRequest({
					url : ws_url + "/rest/os/softwares/create",
					params :software,
					callback : function (data) {
						
						var info = new vmInfoModel(resVmSid);
						info.initVmSoftwareDatagrid();
						info.searchVmSoftwareInfo();
						$("#addVmSoftwareWindow").jqxWindow('close');
				    }
			 });
		});
	   	
		// 编辑软件
	   	$('#editVmSoftwareForm').on('validationSuccess', function (event) {
	   		 var software = JSON.parse($("#editVmSoftwareForm").serializeJson());

	   		 Core.AjaxRequest({
					url : ws_url + "/rest/os/softwares/update",
					params :software,
					callback : function (data) {
						var info = new vmInfoModel(resVmSid);
						info.initVmSoftwareDatagrid();
						info.searchVmSoftwareInfo();
						$("#editVmSoftwareWindow").jqxWindow('close');
				    }
			 });
		});
	   	
	};
	
	
	 // 初始化弹出window
    this.initPopWindow = function(){
		$("#editVmNameWindow").jqxWindow({
            width: 280, 
            height:100,
            resizable: true,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#modifyVmCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	$("#edit-vm-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#modifyVmSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            	$("#modifyVmCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            }
		});
		
		$("#addVmUserWindow").jqxWindow({
            width: 450, 
            height:170,
            resizable: true,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#addVmUserCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	$("#add-vm-user-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-vm-user-password").jqxPasswordInput({ width: '150', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme });
            	
            	$("#add-vm-user-userGroup").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-vm-user-privilege").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-vm-user-description").jqxInput({placeHolder: "", height: 35, width:370, minLength: 1,theme:currentTheme});
            	
            	$("#addVmUserSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            	$("#addVmUserCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            }
		});
		
		$("#editVmUserWindow").jqxWindow({
            width: 450, 
            height:170,
            resizable: true,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#editVmUserCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	$("#edit-vm-user-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-vm-user-password").jqxPasswordInput({ width: '150', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme });
            	
            	$("#edit-vm-user-userGroup").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-vm-user-privilege").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-vm-user-description").jqxInput({placeHolder: "", height: 35, width:370, minLength: 1,theme:currentTheme});
            	
            	$("#editVmUserSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            	$("#editVmUserCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            }
		});
		
		$("#editVmMgtUserWindow").jqxWindow({
            width: 300, 
            height:130,
            resizable: true,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#editVmMgtUserCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	$("#edit-vm-mgt-user-name").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 1,theme:currentTheme});
            	$("#edit-vm-mgt-user-password").jqxPasswordInput({ width: '200', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme });
            	
            	$("#editVmMgtUserSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            	$("#editVmMgtUserCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            }
		});
		
		$("#editVmOsNameWindow").jqxWindow({
            width: 400, 
            height:80,
            resizable: true,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#editVmOsNameCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	$("#edit-vm-os-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#editVmOsNameSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            	$("#editVmOsNameCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            }
		});
		
		$("#addVmVdWindow").jqxWindow({
            width: 500, 
            height:210,
            resizable: true,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#addVmVdInfoCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	$("#add-vm-vd-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-vm-vd-logicVolume").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-vm-vd-size").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-vm-vd-devicePath").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-vm-vd-mountPoint").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-vm-vd-fileSystemType").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	
            	var codesearch = new codeModel({width:150,autoDropDownHeight:true});
            	codesearch.getCommonCode("add-vm-vd-storagePurpose","STORAGE_PURPOSE",false);
            	var code = new codeModel({width:150,dropDownWidth:150,autoDropDownHeight:true});
            	code.getCustomCode("add-vm-vd-storage","/storage/vmStorage/"+resVmSid+"","storageName","resStorageSid",false,"GET");
            	
            	$("#addVmVdInfoSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            	$("#addVmVdInfoCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            }
		});
		
		$("#editVmVdWindow").jqxWindow({
            width: 530, 
            height:210,
            resizable: true,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#editVmVdInfoCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	$("#edit-vm-vd-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-vm-vd-logicVolume").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-vm-vd-size").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-vm-vd-devicePath").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-vm-vd-mountPoint").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-vm-vd-fileSystemType").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-vm-useDiskSize").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	
            	var codesearch = new codeModel({width:150,autoDropDownHeight:true});
            	codesearch.getCommonCode("edit-vm-vd-storagePurpose","STORAGE_PURPOSE",false);
            	
            	$("#editVmVdInfoSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            	$("#editVmVdInfoCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            }
		});
		// 新增软件窗口
		$("#addVmSoftwareWindow").jqxWindow({
            width: 500, 
            height:180,
            resizable: true,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#addVmSoftwareInfoCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	$("#add-vm-install_path").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-vm-install_user_group").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-vm-install_user").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	
            	var codesearch = new codeModel({width:150,autoDropDownHeight:true});
            	codesearch.getCommonCode("add-vm-software_category","SOFTWARE_CATEGORY",false);
            	codesearch.getCommonCodeByConditions("add-vm-software_type",false,{parentCodeValue:$("#add-vm-software_category").val()});
            	var codeversion = new codeModel({width:150,dropDownHeight:150,autoDropDownHeight:false});
            	codeversion.getCommonCodeByConditions("add-vm-software_version",false,{parentCodeValue:$("#add-vm-software_type").val()});
            	
            	$("#addVmSoftwareInfoSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            	$("#addVmSoftwareInfoCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            }
		});
		
		// 编辑软件窗口
		$("#editVmSoftwareWindow").jqxWindow({
            width: 500, 
            height:180,
            resizable: true,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#editVmSoftwareInfoCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	$("#edit-vm-install_path").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-vm-install_user_group").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-vm-install_user").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	
            	$("#editVmSoftwareInfoSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            	$("#editVmSoftwareInfoCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            }
		});
		
    };
	
	  // 初始化虚拟机的硬盘
	  this.searchVmStorageInfo = function(){
		  Core.AjaxRequest({
				url : ws_url + "/rest/vds",
				type:"post",
				async:false,
				params:{
					resVmSid:resVmSid
				},
				callback : function(data) {
					me.storageItems(data);
	 				var sourcedatagrid ={
			              datatype: "json",
			              localdata: me.storageItems
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#vmStorageDatagrid").jqxGrid({source: dataAdapter});
				}
			});
		 
	    };
	    
	    // 初始化用户datagrid的数据源
	    this.initVmStorageDatagrid = function(){
	          $("#vmStorageDatagrid").jqxGrid({
	              width: "98%",
				  theme:currentTheme,
	              columnsresize: true,
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '存储名称', datafield: 'vdName',width:120},
	                  { text: '逻辑卷名称', datafield: 'logicVolume'},
	                  { text: '存储类别', datafield: 'storagePurposeName',width:60},
	                  { text: '大小(GB)', datafield: 'allocateDiskSize',width:80},
	                  { text: '挂载点', datafield: 'mountPoint',width:80},
	                  { text: '文件系统类型', datafield: 'fileSystemType'},
	                  { text: '存储路径', datafield: 'storagePath'}
	              ]
	          });
	    };
	   
     	// 初始化虚拟机的IP
	    this.searchVmUserInfo = function(){
			  Core.AjaxRequest({
					url : ws_url + "/rest/osUsers",
					type:"post",
					async:false,
					params:{
						resSid:resVmSid
					},
					callback : function(data) {
						me.userItems(data);
		 				var sourcedatagrid ={
				              datatype: "json",
				              localdata: me.userItems
		 			    };
		 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		 			    $("#vmUserDatagrid").jqxGrid({source: dataAdapter});
					}
				});
	     };
	    
	    // 初始化用户datagrid的数据源
	    this.initVmUserDatagrid = function(){
	          $("#vmUserDatagrid").jqxGrid({
	              width: "98%",
				  theme:currentTheme,
	              columnsresize: true,
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '用户名', datafield: 'userName'},
	                  { text: '密码', datafield: 'password'}
	              ]
	          });
	    };
	    
	    
	    // 初始化虚拟机的网络IP
	    this.searchVmNicInfo = function(){
			  Core.AjaxRequest({
					url : ws_url + "/rest/vmNetworks",
					type:"post",
					async:false,
					params:{
						resVmSid:resVmSid
					},
					callback : function(data) {
						me.items(data);
		 				var sourcedatagrid ={
				              datatype: "json",
				              localdata: me.items
		 			    };
		 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		 			    $("#vmNicDatagrid").jqxGrid({source: dataAdapter});
					}
				});
		 
	     };

	    // 初始化用户datagrid的数据源
	    this.initVmNicDatagrid = function(){
	          $("#vmNicDatagrid").jqxGrid({
	              width: "98%",
				  theme:currentTheme,
	              columnsresize: true,
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: 'IP地址', datafield: 'ipAddress'},
	                  { text: '所属网络', datafield: 'resNetworkName'}
	              ]
	          });
	    };
	    
	    // 初始化虚拟机的软件信息
	    this.searchVmSoftwareInfo = function(){
			  Core.AjaxRequest({
					url : ws_url + "/rest/os/softwares",
					type:"post",
					async:false,
					params:{
						resSid:resVmSid
					},
					callback : function(data) {
						me.softWareItems(data);
		 				var sourcedatagrid ={
				              datatype: "json",
				              localdata: me.softWareItems
		 			    };
		 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		 			    $("#vmSoftwareDatagrid").jqxGrid({source: dataAdapter});
					}
				});
		 
	     };

	    // 初始化用户软件datagrid
	    this.initVmSoftwareDatagrid = function(){
	          $("#vmSoftwareDatagrid").jqxGrid({
	              width: "98%",
				  theme:currentTheme,
	              columnsresize: true,
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '软件分类', datafield: 'softwareCategoryName'},
	                  { text: '软件类型', datafield: 'softwareTypeName'},
	                  { text: '软件版本', datafield: 'softwareVersionName'},
	                  { text: '安装路径', datafield: 'installPath'}
	                 
	              ]
	          });
	          
	          $("#vmStorageDatagrid").jqxGrid({
	              width: "98%",
				  theme:currentTheme,
	              columnsresize: true,
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '存储名称', datafield: 'vdName',width:120},
	                  { text: '逻辑卷名称', datafield: 'logicVolume'},
	                  { text: '存储类别', datafield: 'storagePurposeName',width:60},
	                  { text: '大小(GB)', datafield: 'allocateDiskSize',width:80},
	                  { text: '挂载点', datafield: 'mountPoint',width:80},
	                  { text: '文件系统类型', datafield: 'fileSystemType'},
	                  { text: '存储路径', datafield: 'storagePath'}
	              ]
	          });
	    };
	    
	    
	    // cpu使用率
		this.initVmCpuUsedRate = function(nodeId,perid,timeType){
			var json = null;
			var stime = perid+" 00:00:00";
			
			Highcharts.setOptions({
		        global: {
		            useUTC: false //开启UTC
		        }
		    });
			
			// 获取监控信息
			Core.AjaxRequest({
				url : ws_url + "/rest/vms/getCpuInfo/"+nodeId+"/"+timeType,
				type:"get",
				async:false,
				callback : function(data) {
					json = data;
				}
			});
			
			var str = "";
			var chartsTitle = [];
			for(var i=0;i<json.length;i++){
				chartsTitle.push(json[i].name);
				str += "<div id='cpuVmUsedchartContainer"+i+"' style='width:1020px;height:275px;border:0px;'></div>";
			}
			
			$("#cpuVmContainer").html(str);
			
			for(var i=0;i<json.length;i++){
				// 初始化图表
				$('#cpuVmUsedchartContainer'+i+'').highcharts({
		            chart: {
		                zoomType: 'x',
		                spacingRight: 20
		            },
		            title: {
		                text: chartsTitle[i]
		            },
		            subtitle: {
		                text: '（单击图表拖动进行放大）',
						style : {
							color: '#666666',
							fontFamily : '微软雅黑'
						}
		            },
		            xAxis: {
	                    type: 'datetime',
	                    dateTimeLabelFormats: {
	                        day: '%m-%d'
	                    },
	                    style: {
	                        fontFamily : '微软雅黑'
	                    },
		                minRange : 600 * 1000,
		                maxZoom: 7 * 24 * 3600 * 1000, // fourteen days
		                title: {
		                    text: ""
		                }
		            },
		            yAxis: {
		                title: {
		                    text: '使用率(%)',
			                style: {
		                		fontSize : '12px',
		                		fontWeight : 'bold',
								fontFamily : '微软雅黑'
			                }
		                },
		                min: 0,
		                max:100
		            },
		            tooltip: {
		                shared: true
		            },
		            credits:{ 
	                    enabled:false
	                },
		            exporting:{ 
	                    enabled:false
	                },
		            legend: {
		                enabled: false
		            },
		            plotOptions: {
		                area: {
	                       tooltip: {
	                            xDateFormat: '%Y-%m-%d %H:%M:%S',
	                            valueDecimals: 2,
	                            valueSuffix: '%'
	                        },
		                    fillColor: {
		                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
		                        stops: [
		                            [0, Highcharts.getOptions().colors[0]],
		                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
		                        ]
		                    },
		                    lineWidth: 1,
		                    marker: {
		                        enabled: false
		                    },
		                    shadow: false,
		                    states: {
		                        hover: {
		                            lineWidth: 1
		                        }
		                    },
		                    threshold: null
		                }
		            },
		    
		            series: [{
		                type: 'area',
		                name: '使用率',
		                pointInterval: 600 * 1000,
		                pointStart: stime,
		                data:json[i].value
		            }]
		        });
			}
			
		};
		
		// 内存使用率
		this.initVmMemoryUsedRate = function(nodeId,perid,timeType){
			var json = null;
			var stime = perid+" 00:00:00";
			Highcharts.setOptions({
		        global: {
		            useUTC: false //开启UTC
		        }
		    });
			// 获取监控信息
			Core.AjaxRequest({
				url : ws_url + "/rest/vms/getMemoryInfo/"+nodeId+"/"+timeType,
				type:"get",
				async:false,
				callback : function(data) {
					$("#mem_max_usage").html((data.maxUsage == null?"0.00":data.maxUsage)+"%");
					$("#mem_avg_usage").html((data.avgUsage == null?"0.00":data.avgUsage)+"%"); 
					$("#mem_min_usage").html((data.minUsage == null?"0.00":data.minUsage)+"%");
					json = data;
				}
			});
			
			// 初始化图表
			$('#memoryVmUsedchartContainer').highcharts({
	            chart: {
	                zoomType: 'x',
	                spacingRight: 20
	            },
	            title: {
	                text: ''
	            },
	            subtitle: {
	                text: '（单击图表拖动进行放大）',
					style : {
						color: '#666666',
						fontFamily : '微软雅黑'
					}
	            },
	            xAxis: {
                    type: 'datetime',
                    dateTimeLabelFormats: {
                        day: '%m-%d'
                    },
                    style: {
                        fontFamily : '微软雅黑'
                    },
	                minRange : 600 * 1000,
	                maxZoom: 7 * 24 * 3600 * 1000, // fourteen days
	                title: {
	                    text: ""
	                }
	            },
	            yAxis: {
	                title: {
	                    text: '使用率(%)',
		                style: {
	                		fontSize : '12px',
	                		fontWeight : 'bold',
							fontFamily : '微软雅黑'
		                }
	                },
	                min: 0,
	                max:100
	            },
	            tooltip: {
	                shared: true
	            },
	            credits:{ 
                    enabled:false
                },
	            exporting:{ 
                    enabled:false
                },
	            legend: {
	                enabled: false
	            },
	            plotOptions: {
	                area: {
                       tooltip: {
                            xDateFormat: '%Y-%m-%d %H:%M:%S',
                            valueDecimals: 2,
                            valueSuffix: '%'
                        },
	                    fillColor: {
	                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
	                        stops: [
	                            [0, Highcharts.getOptions().colors[0]],
	                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
	                        ]
	                    },
	                    lineWidth: 1,
	                    marker: {
	                        enabled: false
	                    },
	                    shadow: false,
	                    states: {
	                        hover: {
	                            lineWidth: 1
	                        }
	                    },
	                    threshold: null
	                }
	            },
	    
	            series: [{
	                type: 'area',
	                name: '使用率',
	                pointInterval: 600 * 1000,
	                pointStart: stime,
	                data:json.data
	            }]
	        });
		};
		
		// 逻辑磁盘占用率
		this.initVmDiskHistoryUsedRate = function(nodeId,perid,timeType){
			var json = null;
			var stime = perid+" 00:00:00";
			Highcharts.setOptions({
		        global: {
		            useUTC: false //开启UTC
		        }
		    });
			// 获取监控信息
			Core.AjaxRequest({
				url : ws_url + "/rest/vms/getDiskInfo/"+nodeId+"/"+timeType,
				type:"get",
				async:false,
				callback : function(data) {
 					json = data;
				}
			});
			
			// 解析数据，如果数据中存在多个磁盘的，需要分开显示
			var str = "";
			var chartsTitle = [];
			for(var i=0;i<json.length;i++){
				chartsTitle.push(json[i].name);
				str += "<div id='diskVmUsedchartContainer"+i+"' style='width:1020px;height:275px;border:0px;'></div>";
			}
			
			$("#diskVmContainer").html(str);
			
			for(var i=0;i<json.length;i++){
				
				// 初始化charts
				$('#diskVmUsedchartContainer'+i+'').highcharts({
		            chart: {
		                zoomType: 'x',
		                spacingRight: 20
		            },
		            title: {
		                text: chartsTitle[i]
		            },
		            subtitle: {
		                text: '（单击图表拖动进行放大）',
						style : {
							color: '#666666',
							fontFamily : '微软雅黑'
						}
		            },
		            xAxis: {
	                    type: 'datetime',
	                    dateTimeLabelFormats: {
	                        day: '%m-%d'
	                    },
	                    style: {
	                        fontFamily : '微软雅黑'
	                    },
		                minRange : 600 * 1000,
		                maxZoom: 7 * 24 * 3600 * 1000, // fourteen days
		                title: {
		                    text: ""
		                }
		            },
		            yAxis: {
		                title: {
		                    text: '使用量(MB)',
			                style: {
		                		fontSize : '12px',
		                		fontWeight : 'bold',
								fontFamily : '微软雅黑'
			                }
		                },
		                min: 0
		            },
		            tooltip: {
		                shared: true
		            },
		            credits:{ 
	                    enabled:false
	                },
		            exporting:{ 
	                    enabled:false
	                },
		            legend: {
		                enabled: false
		            },
		            plotOptions: {
		                area: {
	                       tooltip: {
	                            xDateFormat: '%Y-%m-%d %H:%M:%S',
	                            valueDecimals: 2,
	                            valueSuffix: 'MB'
	                        },
		                    fillColor: {
		                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
		                        stops: [
		                            [0, Highcharts.getOptions().colors[0]],
		                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
		                        ]
		                    },
		                    lineWidth: 1,
		                    marker: {
		                        enabled: false
		                    },
		                    shadow: false,
		                    states: {
		                        hover: {
		                            lineWidth: 1
		                        }
		                    },
		                    threshold: null
		                }
		            },
		    
		            series: [{
		                type: 'area',
		                name: '使用量',
		                pointInterval: 600 * 1000,
		                pointStart: stime,
		                data:json[i].value
		            }]
		        });
			}

		};
		
		// 初始化监控信息
		this.initMonitorInfo = function(){
			// 时间
			var peroid = getThePeridTime(1);
			
			var vm = new vmInfoModel(resVmSid);
			vm.initVmCpuUsedRate(monitorNodeId,peroid,"Day");
			
		};

};

// 设置时间
function getThePeridTime(dcount){
	// 当前时间
	 var date = new Date();
	// 目标时间
	var milliseconds=date.getTime()-1000*60*60*24*dcount;
    var newDate = new Date();       
    newDate.setTime(milliseconds);       
    
    var newYear = newDate.getFullYear();
	var newMonth = (newDate.getMonth()+1)<10?("0"+(newDate.getMonth()+1)):(newDate.getMonth()+1);
	var newDay = newDate.getDate()<10?("0"+newDate.getDate()):newDate.getDate();
	
	var newFromtTime = newYear +"-"+ newMonth +"-"+ newDay;
	
    return newFromtTime;  
}

// 查询
function setMonitorPerid(){
	var timeType = $("#searchMonitorInfo").val();
	
    if(monitorNodeId != null  && monitorNodeId != "" && monitorNodeId != "null"){
    	$("#monitorContent .customPanel").css("display","none");
    	$("#monitorContent .customPanel").eq(0).css("display","block");
    	var vmInfo = new vmInfoModel();
    	if("Day" == timeType){
    		var peroid = getThePeridTime(1);
        	vmInfo.initVmCpuUsedRate(monitorNodeId,peroid,timeType);
    	}else if("Week" == timeType){
    		var peroid = getThePeridTime(7);
        	vmInfo.initVmCpuUsedRate(monitorNodeId,peroid,timeType);
    	}else if("Month" == timeType){
    		var peroid = getThePeridTime(30);
        	vmInfo.initVmCpuUsedRate(monitorNodeId,peroid,timeType);
    	}
    	
    }
}

// 修改虚拟机名称
function popEditVmName(vmSid){
	// 赋值
	$("#edit-vm-resVmSid").val(vmSid);
	// 清空虚拟机名称
	$("#edit-vm-name").val("");
	
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#editVmNameWindow").jqxWindow({ position: { x: (windowW-280)/2, y: (windowH-100)/2 } });
	$("#editVmNameWindow").jqxWindow('open');
}

// 提交修改后的虚拟机名称
function submitVmNameSave(){
	if($("#edit-vm-name").val() == null || $("#edit-vm-name").val() == ""){
		
	}else{
		var name = $("#edit-vm-name").val();
		var resVmSid = $("#edit-vm-resVmSid").val();
		
		Core.AjaxRequest({
			url : ws_url + "/rest/vms/reNameVm",
			params : {
				resVmSid : resVmSid,
				newVmName : name
			},
			async : false,
			callback : function(data) {
				// 关闭window
				$('#editVmNameWindow').jqxWindow('close');
				// 刷新 基本信息
				var vm = new vmInfoModel();
				vm.setVmBasicInfo();
			}
		});
	}
}

//弹出新增虚机管理账户window
function popAddVmUserWindow(){
	$("#add-vm-user-resVmSid").val(resVmSid);
	// 清除输入
	$("#add-vm-user-name").val("");
	$("#add-vm-user-password").val("");
	$("#add-vm-user-userGroup").val("");
	$("#add-vm-user-privilege").val("");
	$("#add-vm-user-description").val("");
	$("#add-type-user").val("RES-VM");
	
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#addVmUserWindow").jqxWindow({ position: { x: (windowW-450)/2, y: (windowH-170)/2 } });
	$("#addVmUserWindow").jqxWindow('open');
}

//提交新增vm管理账户
function submitVmUserInfo(){
	$('#addVmUserForm').jqxValidator('validate');
}

//弹出编辑虚机管理账户window
function popEditVmUserWindow(){
	
	var rowindex = $('#vmUserDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#vmUserDatagrid').jqxGrid('getrowdata', rowindex);
	   	$("#edit-vm-user-sid").val(data.osUserSid);
		// 清除输入
		$("#edit-vm-user-name").val(data.userName);
		$("#edit-vm-user-password").val(data.password);
		
		$("#edit-vm-user-userGroup").val(data.userGroup);
    	$("#edit-vm-user-privilege").val(data.privilege);
    	$("#edit-vm-user-description").val(data.description);
		
		var windowW = $(window).width(); 
		var windowH = $(window).height(); 
		$("#editVmUserWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-130)/2 } });
		$("#editVmUserWindow").jqxWindow('open');
   	}
}

//提交编辑vm管理账户
function submitEditVmUserInfo(){
	$('#editVmUserForm').jqxValidator('validate');
}

// 删除vm账户
function removeVmUserInfo(){
	var rowindex = $('#vmUserDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#vmUserDatagrid').jqxGrid('getrowdata', rowindex);
	   	Core.confirm({
			title:"提示",
			message:"确定要删该虚机账户吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
					url : ws_url + "/rest/osUsers/delete/"+data.osUserSid,
					type:"get",
					callback : function (data) {
						 // 关闭window
						 var info = new vmInfoModel(resVmSid);
						 info.initVmUserDatagrid();
						 info.searchVmUserInfo();
				    }
			    });
			}
		});
   	}
}

// 弹出修改vm的管理账户和密码
function popVmMgtUserAndPasswordName(resVmSid){
	
	$("#edit-vm-mgt-user-sid").val(resVmSid);
	Core.AjaxRequest({
		url : ws_url + "/rest/vms/getVmInfo",
		type:"post",
		async:false,
		params:{
			resVmSid:resVmSid
		},
		callback : function(data) {
			// 清除输入
			$("#edit-vm-mgt-user-name").val(data.managementAccount);
			$("#edit-vm-mgt-user-password").val(data.managementPassword);
		}
	});
	
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#editVmMgtUserWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-130)/2 } });
	$("#editVmMgtUserWindow").jqxWindow('open');
}

// 提交编辑的管理账户信息
function submitVmMgtUserAndPasswordName(){
	$('#editVmMgtUserForm').jqxValidator('validate');
}

// 弹出编辑操作系统名称
function popEditOsName(resVmSid,osName){
	$("#edit-vm-os-name-sid").val(resVmSid);
	$("#edit-vm-os-name").val((osName == "null")?"":osName);
	
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#editVmOsNameWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-80)/2 } });
	$("#editVmOsNameWindow").jqxWindow('open');
}

// 提交编辑操作系统名称信息
function submitVmOsName(){
	$('#editVmOsNameForm').jqxValidator('validate');
}

// 弹出新增虚机磁盘window
function popAddVmVdWindow(){
	$("#add-vm-vd-sid").val(resVmSid);
	
	$("#add-vm-vd-name").val("");
	$("#add-vm-vd-logicVolume").val("");
	$("#add-vm-vd-size").val("");
	$("#add-vm-vd-devicePath").val("");
	$("#add-vm-vd-mountPoint").val("");
	$("#add-vm-vd-fileSystemType").val("");
	
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#addVmVdWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-210)/2 } });
	$("#addVmVdWindow").jqxWindow('open');
}
//弹出存储下存储卷datagrid
function popViewVmVdWindow(){
	var rowIndex = $('#vmStorageDatagrid').jqxGrid('getselectedrowindex');
	var data = $("#vmStorageDatagrid").jqxGrid("getrowdata", rowIndex);
	if(data == null){return;}
	var storageSid = data.allocateResStorageSid;
	var volumeModel = new storageVolumeModel();
	volumeModel.initPopWindow("viewVmStorageWindow");
	volumeModel.initVolumeDatagrid("vmStorageVolumeDatagrid",storageSid);
	// 设置window的位置
	var windowW = $(window).width();
	var windowH = $(window).height();
	$("#viewVmStorageWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-210)/2 } });
	$("#viewVmStorageWindow").jqxWindow('open');
}


//提交新增虚机window信息
function submitVmVdInfo(){
	$('#addVmVdForm').jqxValidator('validate');
}

// 弹出编辑磁盘window
function popEditVmVdWindow(){
	var rowindex = $('#vmStorageDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#vmStorageDatagrid').jqxGrid('getrowdata', rowindex);
	   	$("#edit-vm-vd-sid").val(data.resVdSid);
		// 清除输入
		$("#edit-vm-vd-name").val(data.vdName);
    	$("#edit-vm-vd-logicVolume").val(data.logicVolume);
    	$("#edit-vm-vd-size").val(data.allocateDiskSize);
    	$("#edit-vm-vd-devicePath").val(data.path);
    	$("#edit-vm-vd-mountPoint").val(data.mountPoint);
    	$("#edit-vm-vd-fileSystemType").val(data.fileSystemType);
    	
    	$("#edit-vm-vd-storagePurpose").val(data.storagePurpose);
    	
		var windowW = $(window).width(); 
		var windowH = $(window).height(); 
		$("#editVmVdWindow").jqxWindow({ position: { x: (windowW-530)/2, y: (windowH-210)/2 } });
		$("#editVmVdWindow").jqxWindow('open');
   	}
}

// 提交编辑磁盘info
function submitEditVmVdInfo(){
	$('#editVmVdForm').jqxValidator('validate');
}

// 删除虚拟机磁盘
function removeVmVdInfo(){
	var rowindex = $('#vmStorageDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#vmStorageDatagrid').jqxGrid('getrowdata', rowindex);
	   	Core.confirm({
			title:"提示",
			message:"确定要删该虚机磁盘吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
					url : ws_url + "/rest/vds/delete/"+data.resVdSid,
					type:"get",
					callback : function (data) {
						 // 关闭window
						 var info = new vmInfoModel(resVmSid);
						 info.initVmStorageDatagrid();
						 info.searchVmStorageInfo();
				    }
			    });
			}
		});
   	}
}

// 弹出新增软件窗口
function popAddVmSoftwareWindow(){
	
	$("#add-vm-software-sid").val(resVmSid);
	$("#add-vm-software-type").val("VM");
	$("#add-vm-install_path").val("");
	$("#add-vm-install_user_group").val("");
	$("#add-vm-install_user").val("");
	
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#addVmSoftwareWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-180)/2 } });
	$("#addVmSoftwareWindow").jqxWindow('open');
}

//提交新增软件信息
function submitAddVmSoftwareInfo(){
	$('#addVmSoftwareForm').jqxValidator('validate');
}

//弹出编辑软件window
function popEditVmSoftwareWindow(){
	var rowindex = $('#vmSoftwareDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#vmSoftwareDatagrid').jqxGrid('getrowdata', rowindex);
	   	$("#edit-software-sid").val(data.resSortwareSid);
		// 清除输入
		$("#edit-vm-install_path").val(data.installPath);
		$("#edit-vm-install_user_group").val(data.installUserGroup);
		$("#edit-vm-install_user").val(data.installUser);
		
		var codesearch = new codeModel({width:150,autoDropDownHeight:true});
    	codesearch.getCommonCode("edit-vm-software_category","SOFTWARE_CATEGORY",false);
		$("#edit-vm-software_category").val(data.softwareCategory);
    	codesearch.getCommonCodeByConditions("edit-vm-software_type",false,{parentCodeValue:$("#edit-vm-software_category").val()});
    	$("#edit-vm-software_type").val(data.softwareType);
    	var codeversion = new codeModel({width:150,dropDownHeight:150,autoDropDownHeight:false});
    	codeversion.getCommonCodeByConditions("edit-vm-software_version",false,{parentCodeValue:$("#edit-vm-software_type").val()});
    	$("#edit-vm-software_version").val(data.softwareVersion);
    	
		var windowW = $(window).width(); 
		var windowH = $(window).height(); 
		$("#editVmSoftwareWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-180)/2 } });
		$("#editVmSoftwareWindow").jqxWindow('open');
   	}
}

//提交编辑软件信息
function submitEditVmSoftwareInfo(){
	$('#editVmSoftwareForm').jqxValidator('validate');
}

// 删除虚机软件
function removeVmSoftwareInfo(){
	var rowindex = $('#vmSoftwareDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#vmSoftwareDatagrid').jqxGrid('getrowdata', rowindex);
	   	Core.confirm({
			title:"提示",
			message:"确定要删该软件吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
					url : ws_url + "/rest/os/softwares/delete/"+data.resSortwareSid,
					type:"get",
					callback : function (data) {
						 // 关闭window
						 var info = new vmInfoModel(resVmSid);
						 info.initVmSoftwareDatagrid();
						 info.searchVmSoftwareInfo();
				    }
			    });
			}
		});
   	}
}
  