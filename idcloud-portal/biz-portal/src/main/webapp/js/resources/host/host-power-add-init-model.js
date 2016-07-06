// 新增主机 
var addPowerHostModel = function () {
		var me = this;
		// 判断新增的主机名称是否重复
		this.getHostName = function(name){
			var Todata = null;
			Core.AjaxRequest({
	 			url : ws_url + "/rest/host",
	 			type:'POST',
	 			async:false,
	 			params:{
	 				hostName : name
	 			},
	 			callback : function (data) {
	 				Todata = data;
	 			}
	 		 });
			return Todata;
		};
		// 判断新增的主机IP地址是否重复
		this.getHostIp = function(name){
			var Todata = null;
			Core.AjaxRequest({
	 			url : ws_url + "/rest/host",
	 			type:'POST',
	 			async:false,
	 			params:{
	 				hostIp : name
	 			},
	 			callback : function (data) {
	 				Todata = data;
	 			}
	 		 });
			return Todata;
		};
	   
		this.initInput = function(){
			// 查询拓扑结构所属的DC
			Core.AjaxRequest({
 				url : ws_url + "/rest/topology/findParent/"+resTopologySid,
 				type:'GET',
 				async:false,
 				callback : function (data) {
 					
 					var codeadd = new codeModel({width:170,autoDropDownHeight:true,dropDownWidth:170});
 				 	codeadd.getCustomCode("add-power-equipRoomSid","/phylocations/res/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:data[0].resTopologySid});
 				 	var zoneSid =  $("#add-power-equipRoomSid").val();
 				 	codeadd.getCustomCode("add-power-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSid});
 				 	var cabinetSid =  $("#add-power-equipCabinetSid").val();
 				 	codeadd.getCustomCode("add-power-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSid});
 				 	codeadd.getCustomCode("add-power-equipCategory","/system/getCodesByEquipCategory","codeDisplay","codeValue",false,"POST",{codeCategory:"EQUIP_CATEGORY"});
 				 	
 				 	var code = new codeModel({width:170,autoDropDownHeight:false,dropDownHeight:230,dropDownWidth:170});
 				 	code.getCommonCode("add-power-hostOsType","OS_VERSION",false);
 				 	code.getCommonCode("add-power-isVios","IS_VIOS_FLAG",false);
 				 	
 				}
 			});
		};
	    // 验证新增画面
		this.initValidator = function(){
	    	$('#addPowerHostForm').jqxValidator({
	            rules: [  
	                      { input: '#add-power-hostName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-power-hostName', message: '主机名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
	                      { input: '#add-power-hostName', message: '主机名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
		                  	  	var list = me.getHostName(input.val());
		                  	  	if(list.length > 0){
		                  	  		return false;
		                  	  	}else{
		                  	  		return true;
		                  	  	}
		                      }
			              },
	                      
	                      { input: '#add-power-hostIp', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-power-hostIp', message: '主机IP不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
	                      { input: '#add-power-hostIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
	                    	  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	                    	  if(!pattern.test(input.val())){
                  	  				return false;
                  	  			}else{
                  	  				return true;
                  	  			}
                  	  		}
		                  },
		                  { input: '#add-power-hostIp', message: '主机IP地址重复，请重新输入', action: 'blur', rule: function (input, commit) {
		                  	  	var list = me.getHostIp(input.val());
		                  	  	if(list.length > 0){
		                  	  		return false;
		                  	  	}else{
		                  	  		return true;
		                  	  	}
		                      }
			              },
		                  
	                      
	                      { input: '#add-power-cpuNumber', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-power-cpuNumber', message: 'CPU个数不能超过4个字符', action: 'keyup, blur', rule: 'length=1,4' },
	                      { input: '#add-power-cpuNumber', message: '请必须输入数字', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#add-power-memorySize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-power-memorySize', message: '内存大小不能超过10个字符', action: 'keyup, blur', rule: 'length=1,10' },
	                      { input: '#add-power-memorySize', message: '请必须输入数字', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#add-power-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-power-equipType', message: '主机编号不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
	                      
	                      { input: '#add-power-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-power-name', message: '主机类型不能超过10个字符', action: 'keyup, blur', rule: 'length=1,10' },
	                      
	                      { input: '#add-power-warrantyPeriod', message: '保修期限不能超过5个字符', action: 'blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
                  	  				return true;
                  	  			}else{
                  	  				if(input.val().length > 5){
                  	  					return false;
                  	  				}else{
                  	  					return true;
                  	  				}
                  	  			}
                  	  		}
		                  },
		                  
		                  { input: '#add-power-remoteMgtIp1', message: '请输入正确的IP地址', action: 'blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
                  	  				return true;
                  	  			}else{
              	  					var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
  	                    	  		if(!pattern.test(input.val())){
                    	  				return false;
                    	  			}else{
                    	  				return true;
                    	  			}
	                    	  	}
                  	  		}
		                  },
		                  
		                  { input: '#add-power-remoteMgtIp2', message: '请输入正确的IP地址', action: 'blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
                  	  				return true;
                  	  			}else{
              	  					var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
  	                    	  		if(!pattern.test(input.val())){
                    	  				return false;
                    	  			}else{
                    	  				return true;
                    	  			}
	                    	  	}
                  	  		}
		                  },
		                  
		                  { input: '#add-power-relevanceIp', message: '请输入正确的IP地址', action: 'blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
                  	  				return true;
                  	  			}else{
              	  					var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
  	                    	  		if(!pattern.test(input.val())){
                    	  				return false;
                    	  			}else{
                    	  				return true;
                    	  			}
	                    	  	}
                  	  		}
		                  },
	                   ]
	    	});
	    	
	    	// 新增主机验证成功
	    	$('#addPowerHostForm').on('validationSuccess', function (event) {
		    		 var hostdata = JSON.parse($("#addPowerHostForm").serializeJson());
		    		 // 主机新增标志位
		    		 hostdata.phyhost = "01";
		    		 console.log(JSON.stringify(hostdata));
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/phycomputes/create/server",
		 				params :hostdata,
		 				callback : function (data) {
		 					$("#addPowerHostWindow").jqxWindow('close');
		 					//更新左边Tree
//		 					setVirtualTreeValue();
		 					setVirtualRefreshTreeValue();
		 					var hostibm = new virtualPveHostDatagridModel();
		 					hostibm.searchHostInfo();
		 					
		 			    }
		 			});
	    	 });
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	// 新增window
	    	$("#addPowerHostWindow").jqxWindow({
                width: 800, 
                height:600,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addPowerHostCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	  $("#add-power-hostName").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-hostIp").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-cpuNumber").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-cpuType").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-managementPwd").jqxPasswordInput({  width: '170px', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme});
                	  $("#add-power-managementUser").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-memorySize").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-locationNumber").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-name").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-serialNumber").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-brand").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-model").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  
                	  $("#add-power-equipType").jqxInput({placeHolder: "", height:22 , width: 200, minLength: 1,theme:currentTheme});
                	  $("#add-power-viosUser").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-viosPwd").jqxPasswordInput({  width: '170px', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme});
                	  
                	  $("#add-power-spec").jqxInput({placeHolder: "", height:30 , width: 500, minLength: 1,theme:currentTheme});
                	  $("#add-power-description").jqxInput({placeHolder: "", height:30 , width: 500, minLength: 1,theme:currentTheme});
                	  $("#add-power-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-remoteMgtPwd").jqxPasswordInput({  width: '170px', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme});
                	  $("#add-power-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-maintCompany").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-maintTel").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-purchaseDate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
                	  $("#add-power-startdate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
                	  $("#add-power-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-maintTel").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-power-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
        	    	
                	  $("#addPowerHostSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                	  $("#addPowerHostCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                }
            });
	    };
 };
  
// 供非datagrid操作按钮调用  弹出新增主机框
 function popAddPowerHostWindow(){
	 	var addPowerhost = new addPowerHostModel();
	    addPowerhost.initInput();
	    
	    $("#add-power-hostName").val(null);
  	  	$("#add-power-hostIp").val(null);
  	  	$("#add-power-cpuNumber").val(null);
  	  	$("#add-power-cpuType").val(null);
  	  	$("#add-power-managementPwd").val(null);
  	  	$("#add-power-managementUser").val(null);
  	  	$("#add-power-memorySize").val(null);
  	  	$("#add-power-locationNumber").val(null);
  	  	$("#add-power-name").val(null);
  	  	$("#add-power-serialNumber").val(null);
  	  	$("#add-power-brand").val(null);
  	  	$("#add-power-model").val(null);
  	  	$("#add-power-spec").val(null);
  	  	$("#add-power-description").val(null);
  	  	$("#add-power-remoteMgtIp1").val(null);
  	  	$("#add-power-remoteMgtIp2").val(null);
  	  	$("#add-power-relevanceIp").val(null);
  	  	$("#add-power-remoteMgtPwd").val(null);
  	  	$("#add-power-remoteMgtUser").val(null);
  	  	$("#add-power-maintCompany").val(null);
  	  	$("#add-power-maintTel").val(null);
  	  	$("#add-power-purchaseDate").val(null);
  	  	$("#add-power-startdate").val(null);
  	  	$("#add-power-warrantyPeriod").val(null);
  	  	$("#add-power-maintTel").val(null);
  	  	$("#add-power-equipSupplier").val(null);
  	  	$("#add-power-viosUser").val(null);
  	  	$("#add-power-viosPwd").val(null);
  	  	$("#add-power-equipType").val(null);
	    
  	  	$("#add-power-resTopologySid").val(resTopologySid);
  	  	
	    var windowW = $(window).width();
	  	var windowH = $(window).height(); 
	  	$("#addPowerHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-600)/2 } });
	  	$("#addPowerHostWindow").jqxWindow('open');
 }
 
 // 提交新增Power主机信息
 function addPowerHostSubmit(){
	 $('#addPowerHostForm').jqxValidator('validate');
 }
  