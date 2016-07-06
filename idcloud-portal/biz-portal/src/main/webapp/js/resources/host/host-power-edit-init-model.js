var powerHostName = powerHostIp = "";
// 编辑主机 
var editPowerHostModel = function () {
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
 					
 					var codeedit = new codeModel({width:170,autoDropDownHeight:true,dropDownWidth:170});
 				 	codeedit.getCustomCode("edit-power-equipRoomSid","/phylocations/res/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:data[0].resTopologySid});
 				 	var zoneSid =  $("#edit-power-equipRoomSid").val();
 				 	codeedit.getCustomCode("edit-power-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSid});
 				 	var cabinetSid =  $("#edit-power-equipCabinetSid").val();
 				 	codeedit.getCustomCode("edit-power-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSid});
 				 	codeedit.getCustomCode("edit-power-equipCategory","/system/getCodesByEquipCategory","codeDisplay","codeValue",false,"POST",{codeCategory:"EQUIP_CATEGORY"});
 				 	
 				 	var code = new codeModel({width:170,autoDropDownHeight:false,dropDownHeight:230,dropDownWidth:170});
 				 	code.getCommonCode("edit-power-hostOsType","OS_VERSION",false);
 				 	code.getCommonCode("edit-power-isVios","IS_VIOS_FLAG",false);
 				}
 			});
		};
	    // 验证新增画面
		this.initValidator = function(){
	    	$('#editPowerHostForm').jqxValidator({
	            rules: [  
	                      { input: '#edit-power-hostName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-power-hostName', message: '主机名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
	                      { input: '#edit-power-hostName', message: '主机名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
		                	  if(powerHostName == input.val()){
			                  		return true;
		                  	  }	else{
			                  	   var list = me.getHostName(input.val());
			                  	  	if(list.length > 0){
			                  	  		return false;
			                  	  	}else{
			                  	  		return true;
			                  	  	}
		                  	  }	
		                    }
			              },
	                      
	                      { input: '#edit-power-hostIp', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-power-hostIp', message: '主机IP不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
	                      { input: '#edit-power-hostIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
	                    	  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	                    	  if(!pattern.test(input.val())){
                  	  				return false;
                  	  			}else{
                  	  				return true;
                  	  			}
                  	  		}
		                  },
		                  { input: '#edit-power-hostIp', message: '主机IP地址重复，请重新输入', action: 'blur', rule: function (input, commit) {
		                	  if(powerHostIp == input.val()){
			                  		return true;
		                  	  }	else{
			                  	   var list = me.getHostIp(input.val());
			                  	  	if(list.length > 0){
			                  	  		return false;
			                  	  	}else{
			                  	  		return true;
			                  	  	}
		                  	  }	
		                    }
			              },
	                      
	                      { input: '#edit-power-cpuNumber', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-power-cpuNumber', message: 'CPU个数不能超过4个字符', action: 'keyup, blur', rule: 'length=1,4' },
	                      { input: '#edit-power-cpuNumber', message: '请必须输入数字', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#edit-power-memorySize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-power-memorySize', message: '内存大小不能超过10个字符', action: 'keyup, blur', rule: 'length=1,10' },
	                      { input: '#edit-power-memorySize', message: '请必须输入数字', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#edit-power-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-power-name', message: '主机编号不能超过10个字符', action: 'keyup, blur', rule: 'length=1,10' },

	                      { input: '#edit-power-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-power-equipType', message: '主机类型不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
	                      
	                      { input: '#edit-power-warrantyPeriod', message: '保修期限不能超过5个字符', action: 'blur',rule: function (input, commit) {
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
		                  
		                  { input: '#edit-power-remoteMgtIp1', message: '请输入正确的IP地址', action: 'blur',rule: function (input, commit) {
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
		                  
		                  { input: '#edit-power-remoteMgtIp2', message: '请输入正确的IP地址', action: 'blur',rule: function (input, commit) {
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
		                  
		                  { input: '#edit-power-relevanceIp', message: '请输入正确的IP地址', action: 'blur',rule: function (input, commit) {
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
	    	$('#editPowerHostForm').on('validationSuccess', function (event) {
		    		 var hostdata = JSON.parse($("#editPowerHostForm").serializeJson());
		    		 // power主机新增标志位
		    		 hostdata.phyhost = "01";
		    		 console.log(JSON.stringify(hostdata));
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/phycomputes/update/server",
		 				params :hostdata,
		 				callback : function (data) {
		 					$("#editPowerHostWindow").jqxWindow('close');
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
	    	$("#editPowerHostWindow").jqxWindow({
                width: 800, 
                height:600,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#editPowerHostCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	  $("#edit-power-hostName").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-hostIp").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-cpuNumber").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-cpuType").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-managementPwd").jqxPasswordInput({  width: '170px', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme});
                	  $("#edit-power-managementUser").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-memorySize").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-locationNumber").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-name").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-serialNumber").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-brand").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-model").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  
                	  $("#edit-power-equipType").jqxInput({placeHolder: "", height:22 , width: 200, minLength: 1,theme:currentTheme});
                	  $("#edit-power-viosUser").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-viosPwd").jqxPasswordInput({  width: '170px', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme});
                	  
                	  $("#edit-power-spec").jqxInput({placeHolder: "", height:30 , width: 500, minLength: 1,theme:currentTheme});
                	  $("#edit-power-description").jqxInput({placeHolder: "", height:30 , width: 500, minLength: 1,theme:currentTheme});
                	  $("#edit-power-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-remoteMgtPwd").jqxPasswordInput({  width: '170px', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme});
                	  $("#edit-power-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-maintCompany").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-maintTel").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  
                	  $("#edit-power-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-maintTel").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-power-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
        	    	
                	  $("#editPowerHostSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                	  $("#editPowerHostCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                }
            });
	    };
 };
  
// 供非datagrid操作按钮调用  弹出编辑主机框
 function popEditPowerHostWindow(){
	 	var editPowerhost = new editPowerHostModel();
	 	editPowerhost.initInput();
	    
	    var rowindex = $('#pvehostdatagrid').jqxGrid('getselectedrowindex');
		var ok =  $('#editPveHost').jqxButton('disabled');
		if(!ok && rowindex >= 0){
			  var data = $('#pvehostdatagrid').jqxGrid('getrowdata', rowindex);
			  powerHostName = data.hostName;
			  powerHostIp = data.hostIp;
			  $("#edit-power-hostName").val(data.hostName);
		      $("#edit-power-hostIp").val(data.hostIp);
		      $("#edit-power-hostOsType").val(data.hostOsType);
		      $("#edit-power-cpuNumber").val(data.cpuNumber);
		      $("#edit-power-cpuType").val(data.cpuType);
		      $("#edit-power-memorySize").val(data.memorySize);
		      $("#edit-power-managementUser").val(data.managementUser);
		      $("#edit-power-managementPwd").val(data.managementPwd);
		      $("#edit-power-equipCategory").val(data.equipCategory);
		      $("#edit-power-equipType").val(data.equipType);
		      $("#edit-power-name").val(data.name);
		      $("#edit-power-brand").val(data.brand);
		      $("#edit-power-model").val(data.model);
		      if(data.serialNumber!=null){
		    	  $("#edit-power-serialNumber").val(data.serialNumber);
		      }else{
		    	  $("#edit-power-serialNumber").val(data.phySerialNumber);
		      }
		     // $("#edit-power-serialNumber").val(data.serialNumber);
		      $("#edit-power-locationNumber").val(data.locationNumber);
		      $("#edit-power-equipRoomSid").val(data.equipRoomSid);
		      $("#edit-power-equipCabinetSid").val(data.equipCabinetSid);
		      $("#edit-power-equipRackSid").val(data.equipRackSid);
		      $("#edit-power-resTopologySid").val(data.resTopologySid);
		      $("#edit-power-description").val(data.description);
		      $("#edit-power-remoteMgtIp1").val(data.remoteMgtIp1);
		      $("#edit-power-remoteMgtIp2").val(data.remoteMgtIp2);
		      $("#edit-power-relevanceIp").val(data.relevanceIp);
		      $("#edit-power-remoteMgtUser").val(data.remoteMgtUser);
		      $("#edit-power-remoteMgtPwd").val(data.remoteMgtPwd);
		      $("#edit-power-viosPwd").val(data.viosPassWord);
		      $("#edit-power-viosUser").val(data.viosUser);
		      $("#edit-power-isVios").val(data.isViosFlag);
		      
		      $("#edit-power-resHostSid").val(data.resHostSid);
		      var equipSid = data.resHostSid;

		      Core.AjaxRequest({
			      url : ws_url + "/rest/phycomputes/findMaintenanceServer/"+equipSid,
			      type : "get",
			      async : false,
			      callback : function(result) {
			    	  $("#edit-power-purchaseDate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
                	  $("#edit-power-startdate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
				        if(null==result){
				          $("#edit-power-maintCompany").val("");
				          $("#edit-power-maintTel").val("");
				          $("#edit-power-purchaseDate").val("");
				          $("#edit-power-startEndDate").val("");
				          $("#edit-power-spec").val("");
				          $("#edit-power-description").val("");
				          $("#edit-power-warrantyPeriod").val("");
				          $("#edit-power-equipSupplier").val("");
				        }else{
				          $("#edit-power-maintCompany").val(result.maintCompany);
				          $("#edit-power-maintTel").val(result.maintTel);
				          $("#edit-power-purchaseDate").jqxDateTimeInput('val', result.purchaseDate);
	                	  $("#edit-power-startdate").jqxDateTimeInput('val',result.startEndDate);
	                	  
				          $("#edit-power-spec").val(result.spec);
				          $("#edit-power-description").val(result.description);
				          $("#edit-power-warrantyPeriod").val(result.warrantyPeriod);
				          $("#edit-power-equipSupplier").val(result.equipSupplier);
				        }
			      }
		      });
		      
		      var windowW = $(window).width();
			  var windowH = $(window).height(); 
			  $("#editPowerHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-600)/2 } });
			  $("#editPowerHostWindow").jqxWindow('open');
		}
	   
 }
 
 // 提交新增Power主机信息
 function editPowerHostSubmit(){
	 $('#editPowerHostForm').jqxValidator('validate');
 }
  