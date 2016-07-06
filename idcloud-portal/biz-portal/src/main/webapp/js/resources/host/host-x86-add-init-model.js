// 新增主机 
var addX86HostModel = function () {
	
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
 				 	codeadd.getCustomCode("add-phyhost-equipRoomSid","/phylocations/res/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:data[0].resTopologySid});
 				 	var zoneSid =  $("#add-phyhost-equipRoomSid").val();
 				 	codeadd.getCustomCode("add-phyhost-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSid});
 				 	var cabinetSid =  $("#add-phyhost-equipCabinetSid").val();
 				 	codeadd.getCustomCode("add-phyhost-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSid});
 				 	codeadd.getCustomCode("add-phyhost-equipCategory","/system/getCodesByEquipCategory","codeDisplay","codeValue",false,"POST",{codeCategory:"EQUIP_CATEGORY"});
 				 	
 				 	var code = new codeModel({width:170,autoDropDownHeight:false,dropDownHeight:230,dropDownWidth:170});
 				 	code.getCommonCode("add-phyhost-hostOsType","OS_VERSION",false);
 				 	
 				}
 			});
		};
	    // 验证新增画面
		this.initValidator = function(){
	    	$('#addX86HostForm').jqxValidator({
	            rules: [  
	                      { input: '#add-phyhost-hostName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-phyhost-hostName', message: '主机名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
	                      { input: '#add-phyhost-hostName', message: '主机名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
		                  	  	var list = me.getHostName(input.val());
		                  	  	if(list.length > 0){
		                  	  		return false;
		                  	  	}else{
		                  	  		return true;
		                  	  	}
		                      }
			              },
	                      
	                      
	                      
	                      { input: '#add-phyhost-hostIp', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-phyhost-hostIp', message: '主机IP不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
	                      { input: '#add-phyhost-hostIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
	                    	  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	                    	  if(!pattern.test(input.val())){
                  	  				return false;
                  	  			}else{
                  	  				return true;
                  	  			}
                  	  		}
		                  },
		                  { input: '#add-phyhost-hostIp', message: '主机IP地址重复，请重新输入', action: 'blur', rule: function (input, commit) {
		                  	  	var list = me.getHostIp(input.val());
		                  	  	if(list.length > 0){
		                  	  		return false;
		                  	  	}else{
		                  	  		return true;
		                  	  	}
		                      }
			              },
	                      
	                      { input: '#add-phyhost-cpuNumber', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-phyhost-cpuNumber', message: 'CPU个数不能超过4个字符', action: 'keyup, blur', rule: 'length=1,4' },
	                      { input: '#add-phyhost-cpuNumber', message: '请必须输入数字', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#add-phyhost-cpuCores', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-phyhost-cpuCores', message: 'CPU核数不能超过4个字符', action: 'keyup, blur', rule: 'length=1,4' },
	                      { input: '#add-phyhost-cpuCores', message: '请必须输入数字', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#add-phyhost-memorySize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-phyhost-memorySize', message: '内存大小不能超过10个字符', action: 'keyup, blur', rule: 'length=1,10' },
	                      { input: '#add-phyhost-memorySize', message: '请必须输入数字', action: 'keyup, blur', rule: 'number' },
	                      
	                      { input: '#add-phyhost-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-phyhost-name', message: '主机编号不能超过10个字符', action: 'keyup, blur', rule: 'length=1,10' },
	                      { input: '#add-phyhost-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-phyhost-equipType', message: '主机类型不能超过36个字符', action: 'keyup, blur', rule: 'length=1,36' },
	                      
	                      { input: '#add-phyhost-warrantyPeriod', message: '保修期限不能超过5个字符', action: 'blur',rule: function (input, commit) {
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
		                  
		                  { input: '#add-phyhost-remoteMgtIp1', message: '请输入正确的IP地址', action: 'blur',rule: function (input, commit) {
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
		                  
		                  { input: '#add-phyhost-remoteMgtIp2', message: '请输入正确的IP地址', action: 'blur',rule: function (input, commit) {
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
		                  
		                  { input: '#add-phyhost-relevanceIp', message: '请输入正确的IP地址', action: 'blur',rule: function (input, commit) {
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
	    	$('#addX86HostForm').on('validationSuccess', function (event) {
		    		 var hostdata = JSON.parse($("#addX86HostForm").serializeJson());
		    		 // x86主机新增标志位
		    		 hostdata.phyhost = "00";
		    		 console.log(JSON.stringify(hostdata));
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/phycomputes/create/server",
		 				params :hostdata,
		 				callback : function (data) {
		 					$("#addX86HostWindow").jqxWindow('close');
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
		 			    	$("#addHostWindow").jqxWindow('close');
		 			    }
		 			});
	    	 });
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	// 新增window
	    	$("#addX86HostWindow").jqxWindow({
                width: 800, 
                height:560,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addhostCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	  $("#add-phyhost-hostName").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-hostIp").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-cpuNumber").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-cpuType").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-cpuCores").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-managementPwd").jqxPasswordInput({  width: '170px', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme});
                	  $("#add-phyhost-managementUser").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-memorySize").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-locationNumber").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-name").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-serialNumber").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-brand").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-model").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  
                	  $("#add-phyhost-equipType").jqxInput({placeHolder: "", height:22 , width: 200, minLength: 1,theme:currentTheme});
                	  
                	  $("#add-phyhost-spec").jqxInput({placeHolder: "", height:30 , width: 500, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-description").jqxInput({placeHolder: "", height:30 , width: 500, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-remoteMgtPwd").jqxPasswordInput({  width: '170px', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme});
                	  $("#add-phyhost-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-maintCompany").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-maintTel").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-purchaseDate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
                	  $("#add-phyhost-startdate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
                	  $("#add-phyhost-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-maintTel").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
                	  $("#add-phyhost-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
        	    	
                	  $("#addhostSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                	  $("#addhostCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
                }
            });
	    };
 };
  
// 供非datagrid操作按钮调用  弹出新增主机框
 function popAddX86HostWindow(){
	 	var addX86host = new addX86HostModel();
	    addX86host.initInput();
	    
	    $("#add-phyhost-hostName").val(null);
  	  	$("#add-phyhost-hostIp").val(null);
  	  	$("#add-phyhost-cpuNumber").val(null);
  	  	$("#add-phyhost-cpuType").val(null);
  	  	$("#add-phyhost-cpuCores").val(null);
  	  	$("#add-phyhost-managementPwd").val(null);
  	  	$("#add-phyhost-managementUser").val(null);
  	  	$("#add-phyhost-memorySize").val(null);
  	  	$("#add-phyhost-locationNumber").val(null);
  	  	$("#add-phyhost-name").val(null);
  	  	$("#add-phyhost-serialNumber").val(null);
  	  	$("#add-phyhost-brand").val(null);
  	  	$("#add-phyhost-model").val(null);
  	  	$("#add-phyhost-spec").val(null);
  	  	$("#add-phyhost-description").val(null);
  	  	$("#add-phyhost-remoteMgtIp1").val(null);
  	  	$("#add-phyhost-remoteMgtIp2").val(null);
  	  	$("#add-phyhost-relevanceIp").val(null);
  	  	$("#add-phyhost-remoteMgtPwd").val(null);
  	  	$("#add-phyhost-remoteMgtUser").val(null);
  	  	$("#add-phyhost-maintCompany").val(null);
  	  	$("#add-phyhost-maintTel").val(null);
  	  	$("#add-phyhost-purchaseDate").val(null);
  	  	$("#add-phyhost-startdate").val(null);
  	  	$("#add-phyhost-warrantyPeriod").val(null);
  	  	$("#add-phyhost-maintTel").val(null);
  	  	$("#add-phyhost-equipSupplier").val(null);
  	  	$("#add-phyhost-equipType").val(null);
	    
  	  	$("#add-phyhost-resTopologySid").val(resTopologySid);
  	  	
	    var windowW = $(window).width();
	  	var windowH = $(window).height(); 
	  	$("#addX86HostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-560)/2 } });
	  	$("#addX86HostWindow").jqxWindow('open');
 }
 
 // 提交新增x86主机信息
 function addX86HostSubmit(){
	 $('#addX86HostForm').jqxValidator('validate');
 }
  