var x86HostName = x86HostIp = "";
// 编辑主机 
var editX86HostModel = function () {
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
 				 	codeedit.getCustomCode("edit-phyhost-equipRoomSid","/phylocations/res/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:data[0].resTopologySid});
 				 	var zoneSid =  $("#edit-phyhost-equipRoomSid").val();
 				 	codeedit.getCustomCode("edit-phyhost-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSid});
 				 	var cabinetSid =  $("#edit-phyhost-equipCabinetSid").val();
 				 	codeedit.getCustomCode("edit-phyhost-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSid});
 				 	codeedit.getCustomCode("edit-phyhost-equipCategory","/system/getCodesByEquipCategory","codeDisplay","codeValue",false,"POST",{codeCategory:"EQUIP_CATEGORY"});
 				 	
 				 	var code = new codeModel({width:170,autoDropDownHeight:false,dropDownHeight:230,dropDownWidth:170});
 				 	code.getCommonCode("edit-phyhost-hostOsType","OS_VERSION",false);
 				 	
 				}
 			});
		};
	    // 验证新增画面
		this.initValidator = function(){
	    	$('#editX86HostForm').jqxValidator({
	            rules: [  
	                      { input: '#edit-phyhost-hostName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-phyhost-hostName', message: '主机名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
	                      { input: '#edit-phyhost-hostName', message: '主机名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
		                	  if(x86HostName == input.val()){
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
	                      
	                      { input: '#edit-phyhost-hostIp', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-phyhost-hostIp', message: '主机IP不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
	                      { input: '#edit-phyhost-hostIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
	                    	  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	                    	  if(!pattern.test(input.val())){
                  	  				return false;
                  	  			}else{
                  	  				return true;
                  	  			}
                  	  		}
		                  },
		                  { input: '#edit-phyhost-hostIp', message: '主机IP地址重复，请重新输入', action: 'blur', rule: function (input, commit) {
		                	  if(x86HostIp == input.val()){
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
	                      
	                      { input: '#edit-phyhost-cpuNumber', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-phyhost-cpuNumber', message: 'CPU个数不能超过4个字符', action: 'keyup, blur', rule: 'length=1,4' },
	                      { input: '#edit-phyhost-cpuNumber', message: '请必须输入数字', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#edit-phyhost-cpuCores', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-phyhost-cpuCores', message: 'CPU核数不能超过4个字符', action: 'keyup, blur', rule: 'length=1,4' },
	                      { input: '#edit-phyhost-cpuCores', message: '请必须输入数字', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#edit-phyhost-memorySize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-phyhost-memorySize', message: '内存大小不能超过10个字符', action: 'keyup, blur', rule: 'length=1,10' },
	                      { input: '#edit-phyhost-memorySize', message: '请必须输入数字', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#edit-phyhost-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-phyhost-name', message: '主机编号不能超过10个字符', action: 'keyup, blur', rule: 'length=1,10' },

	                      { input: '#edit-phyhost-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#edit-phyhost-equipType', message: '主机类型不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
	                      
	                      { input: '#edit-phyhost-warrantyPeriod', message: '保修期限不能超过5个字符', action: 'blur',rule: function (input, commit) {
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
		                  
		                  { input: '#edit-phyhost-remoteMgtIp1', message: '请输入正确的IP地址', action: 'blur',rule: function (input, commit) {
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
		                  
		                  { input: '#edit-phyhost-remoteMgtIp2', message: '请输入正确的IP地址', action: 'blur',rule: function (input, commit) {
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
		                  
		                  { input: '#edit-phyhost-relevanceIp', message: '请输入正确的IP地址', action: 'blur',rule: function (input, commit) {
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
	    	$('#editX86HostForm').on('validationSuccess', function (event) {
		    		 var hostdata = JSON.parse($("#editX86HostForm").serializeJson());
		    		 // x86主机新增标志位
		    		 hostdata.phyhost = "00";
		    		 //console.log(JSON.stringify(hostdata));
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/phycomputes/update/server",
		 				params :hostdata,
		 				callback : function (data) {
		 					$("#editX86HostWindow").jqxWindow('close');
		 					//更新左边Tree
//		 					setVirtualTreeValue();
//		 					setHostTreeValueRefresh();
		 					setVirtualRefreshTreeValue();
		 					var idflag = document.getElementById('hostdatagrid');
		 					if(idflag){
		 						var host = new virtualHostDatagridModel();
			 					host.searchHostInfo();
		 					}else{
		 						var host = new virtualOtherHostDatagridModel();
		 						host.searchHostInfo();
		 					}
		 			    },
		 			    failure:function(data){
		 			    	$("#editHostWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	// 新增window
	    	$("#editX86HostWindow").jqxWindow({
                width: 800, 
                height:560,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#edithostCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	  $("#edit-phyhost-hostName").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-hostIp").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-cpuNumber").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-cpuType").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-cpuCores").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-managementPwd").jqxPasswordInput({  width: '170px', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme});
                	  $("#edit-phyhost-managementUser").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-memorySize").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-locationNumber").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-name").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-serialNumber").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-brand").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-model").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  
                	  $("#edit-phyhost-equipType").jqxInput({placeHolder: "", height:22 , width: 200, minLength: 1,theme:currentTheme});
                	  
                	  $("#edit-phyhost-spec").jqxInput({placeHolder: "", height:30 , width: 500, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-description").jqxInput({placeHolder: "", height:30 , width: 500, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-remoteMgtPwd").jqxPasswordInput({  width: '170px', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme});
                	  $("#edit-phyhost-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-maintCompany").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-maintTel").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	 
                	  $("#edit-phyhost-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-maintTel").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#edit-phyhost-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
        	    	
                	  $("#edithostSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                	  $("#edithostCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                }
            });
	    };
 };
  
// 供非datagrid操作按钮调用  弹出编辑主机框
 function popEditX86HostWindow(){
	 	var editX86host = new editX86HostModel();
	    editX86host.initInput();
	    
	    var rowindex = $('#hostdatagrid').jqxGrid('getselectedrowindex');
		var ok =  $('#editHost').jqxButton('disabled');
		if(!ok && rowindex >= 0){
			  var data = $('#hostdatagrid').jqxGrid('getrowdata', rowindex);
			  x86HostName = data.hostName;
			  x86HostIp = data.hostIp;
			  $("#edit-phyhost-hostName").val(data.hostName);
		      $("#edit-phyhost-hostIp").val(data.hostIp);
		      $("#edit-phyhost-hostOsType").val(data.hostOsType);
		      $("#edit-phyhost-cpuNumber").val(data.cpuNumber);
		      $("#edit-phyhost-cpuType").val(data.cpuType);
		      $("#edit-phyhost-cpuCores").val(data.cpuCores);
		      $("#edit-phyhost-memorySize").val(data.memorySize);
		      $("#edit-phyhost-managementUser").val(data.managementUser);
		      $("#edit-phyhost-managementPwd").val(data.managementPwd);
		      $("#edit-phyhost-equipCategory").val(data.equipCategory);
		      $("#edit-phyhost-equipType").val(data.equipType);

		      $("#edit-phyhost-name").val(data.name);
		      $("#edit-phyhost-brand").val(data.brand);
		      $("#edit-phyhost-model").val(data.model);
		      if(data.serialNumber!=null){
		    	  $("#edit-phyhost-serialNumber").val(data.serialNumber);
		      }else{
		    	  $("#edit-phyhost-serialNumber").val(data.phySerialNumber);
		      }
		     // $("#edit-phyhost-serialNumber").val(data.serialNumber);
		      $("#edit-phyhost-locationNumber").val(data.locationNumber);
		      $("#edit-phyhost-equipRoomSid").val(data.equipRoomSid);
		      $("#edit-phyhost-equipCabinetSid").val(data.equipCabinetSid);
		      $("#edit-phyhost-equipRackSid").val(data.equipRackSid);
		      $("#edit-phyhost-resTopologySid").val(data.resTopologySid);
		      $("#edit-phyhost-description").val(data.description);
		      $("#edit-phyhost-remoteMgtIp1").val(data.remoteMgtIp1);
		      $("#edit-phyhost-remoteMgtIp2").val(data.remoteMgtIp2);
		      $("#edit-phyhost-relevanceIp").val(data.relevanceIp);
		      $("#edit-phyhost-remoteMgtUser").val(data.remoteMgtUser);
		      $("#edit-phyhost-remoteMgtPwd").val(data.remoteMgtPwd);
		      
		      $("#edit-pve-resHostSid").val(data.resHostSid);
		      var equipSid = data.resHostSid;
		      Core.AjaxRequest({
			      url : ws_url + "/rest/phycomputes/findMaintenanceServer/"+equipSid,
			      type : "get",
			      async : false,
			      callback : function(result) {
			    	  $("#edit-phyhost-purchaseDate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
                	  $("#edit-phyhost-startdate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
				        if(null==result){
				            $("#edit-phyhost-maintCompany").val("");
				            $("#edit-phyhost-maintTel").val("");
				            $("#edit-phyhost-purchaseDate").val("");
				            $("#edit-phyhost-startEndDate").val("");
				            $("#edit-phyhost-spec").val("");
				            $("#edit-phyhost-description").val("");
				            $("#edit-phyhost-warrantyPeriod").val("");
				            $("#edit-phyhost-equipSupplier").val("");
				        }else{
				            $("#edit-phyhost-maintCompany").val(result.maintCompany);
				            $("#edit-phyhost-maintTel").val(result.maintTel);
				            
				            $("#edit-phyhost-purchaseDate").jqxDateTimeInput('val', result.purchaseDate);
		                	$("#edit-phyhost-startdate").jqxDateTimeInput('val',result.startEndDate);
		                	
				            $("#edit-phyhost-spec").val(result.spec);
				            $("#edit-phyhost-description").val(result.description);
				            $("#edit-phyhost-warrantyPeriod").val(result.warrantyPeriod);
				            $("#edit-phyhost-equipSupplier").val(result.equipSupplier);
				        } 
		          }
		      });
		      
		      var windowW = $(window).width();
			  var windowH = $(window).height(); 
			  $("#editX86HostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-560)/2 } });
			  $("#editX86HostWindow").jqxWindow('open');
		}
	   
 }
 
 // 提交新增x86主机信息
 function editX86HostSubmit(){
	 $('#editX86HostForm').jqxValidator('validate');
 }
  