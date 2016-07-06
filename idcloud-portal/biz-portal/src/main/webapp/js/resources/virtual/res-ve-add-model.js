var virtualVeAddModel = function () {
		var me = this;
		
		// 判断新增的区域、数据中心是否重复
		this.getVeManageMentUrl = function(url){
			var Todata = null;
			Core.AjaxRequest({
	 			url : ws_url + "/rest/ves/checkVeRepeat/",
	 			type:'POST',
	 			async:false,
	 			params:{
	 				managementUrl : url
	 			},
	 			callback : function (data) {
	 				Todata = data;
	 			}
	 		 });
			return Todata;
		};
		this.getRegionAndVc = function(name,type){
			var Todata = null;
			Core.AjaxRequest({
	 			url : ws_url + "/rest/topology/checkTopologyRepeat/",
	 			type:'POST',
	 			async:false,
	 			params:{
	 				resTopologyName : name,
	 				resTopologyType : type
	 			},
	 			callback : function (data) {
	 				Todata = data;
	 			}
	 		 });
			return Todata;
		};
		
		
		// 验证初始化
	    this.initValidator = function(){
	    	$('#addVeForm').jqxValidator({
                rules: [
                          { input: '#add-ve-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-ve-resTopologyName', message: '环境名称不能超过64个字符', action: 'blur', rule: 'length=1,64' },
                          
                          { input: '#add-ve-managementUrl', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                      { input: '#add-ve-managementUrl', message: '管理地址不能超过128个字符', action: 'keyup, blur', rule: 'length=1,128' },
	                      { input: '#add-ve-managementUrl', message: '管理地址重复，请重新输入', action: 'blur', rule: function (input, commit) {
		                  	  	var list = me.getVeManageMentUrl(input.val());
		                  	  	if(list.length > 0){
		                  	  		return false;
		                  	  	}else{
		                  	  		return true;
		                  	  	}
		                      }
			              },
	                      
	                      { input: '#add-ve-managementAccount', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-ve-managementAccount', message: '用户账号不能超过64个字符', action: 'blur', rule: 'length=1,64' },
	                      { input: '#add-ve-managementAccount', message: '只能输入英文或者数字', action: 'keyup, blur',rule: function (input, commit) {
	                    	  if(/[\u4E00-\u9FA5]/g.test(input.val())){
	                              return false;
	                          }else{
	                              return true;
	                          }

	              	  		}
		                  },
                          
                          { input: '#add-ve-managementPassword', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-ve-managementPassword', message: '用户密码不能超过16个字符', action: 'blur', rule: 'length=1,16' },
                          
                          { input: '#add-ve-confirmPassword', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-ve-confirmPassword', message: '用户密码不能超过16个字符', action: 'blur', rule: 'length=1,16' },
                          { input: '#add-ve-confirmPassword', message: '前后输入的密码不一致', action: 'blur', rule: function(input,commit){
                        	  // 判断密码输入的是否是一致的
                        	  if(input.val() == $("#add-ve-managementPassword").val()){
                        		  return true;
                        	  }else{
                        		  return false;
                        	  }
                          	} 
                          },
                          
                          { input: '#add-ve-updateCycle', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-ve-updateCycle', message: '更新周期不能超过16个字符', action: 'blur', rule: 'length=1,16' },
                          { input: '#add-ve-updateCycle', message: '请必须输入数字', action: 'blur', rule: 'number' },
                          
                          
                       ]
	    	});
	    	
	    	// 新增集群验证
			$('#addHostClusterForm').jqxValidator({
		        rules: [  
		                  { input: '#add-cluster-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-cluster-resTopologyName', message: '集群名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
		                	    var list = me.getRegionAndVc(input.val(),"VC");
		                  	  	if(list.length > 0){
		                  	  		return false;
		                  	  	}else{
		                  	  		return true;
		                  	  	}
		                     }
			              },
		               ]
			});
			
			$('#addHostClusterForm').on('validationSuccess', function (event) {
				 var topology = Core.parseJSON($("#addHostClusterForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/topology/create",
		 				params :topology,
		 				callback : function (data) {
		 					$("#addHostClusterWindow").jqxWindow('close');
		 					// 刷新基本信息
		 					if($("#containerPool").length > 0){
		 						setPoolTreeValue();
		 					}else{
		 						setVirtualTreeValue();
		 						setStorageVirtualTreeValue();
		 					}
		 			    },
		 			    failure:function(data){
							$("#addHostClusterWindow").jqxWindow('close');
		 			    }
		 			});
		     });
			
	    };
		
		//初始下拉列表框的联动问题
	    this.initComboxLinkage = function(){
	    	var codeadd = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth:150});
	    	$('#add-ve-zone').on('change', function (event){     
				    var args = event.args;
				    if (args) {
				    var item = args.item;
				    var value = item.value;
				    codeadd.getCustomCode("add-ve-dc","/topology","resTopologyName","resTopologySid",false,"POST",{parentTopologySid:value,resTopologyType:"DC"});
				} 
			});
	    	
	    	$('#add-ve-virtualPlatformType').on('change', function (event){     
			    var args = event.args;
			    if (args) {
			    var item = args.item;
			    var value = item.value;
			    codeadd.getCommonCodeByConditions("add-ve-virtualPlatformVersion",null,{parentCodeValue:value});
			} 
		});
	    };
	   
	    // 初始化弹出window
	    this.initPopWindow = function(){
	    	$("#addVeWindow").jqxWindow({
                width: 450, 
                height:395,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addVeCancel"), 
                theme: currentTheme,
                modalOpacity: 0.3,
                initContent:function(){
                	 // 初始化新增用户页面组件
        	        $("#add-ve-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-ve-managementUrl").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-ve-managementAccount").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-ve-managementPassword").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-ve-confirmPassword").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#add-ve-updateCycle").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        
        	        $("#addVeSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#addVeCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        
        	        $("#addVeConfirmSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#addVeConfirmCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        $("#addVeGoVirtual").jqxButton({ width: '50',height:"25",theme:currentTheme});
        	        
        	        var codeadd = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth:150});
           			codeadd.getCommonCode("add-ve-resTopologyType","RES_TOPOLOGY_TYPE");
           			codeadd.getCommonCode("add-ve-virtualPlatformType","VIRTUAL_PLATFORM_TYPE");
           			$("#add-ve-resTopologyType").val("VE");
           			$("#add-ve-resTopologyType").jqxDropDownList({
           				disabled:true
           			});
           			
//           			codeadd.getCustomCode("add-ve-zone","/topology","resTopologyName","resTopologySid",false,"POST",{resTopologyType:"R"});
//           			var zoneSid =  $("#add-ve-zone").val();
//           			
//           			codeadd.getCustomCode("add-ve-dc","/topology","resTopologyName","resTopologySid",false,"POST",{parentTopologySid:zoneSid,resTopologyType:"DC"});
           			var virtualType =  $("#add-ve-virtualPlatformType").val();
           			codeadd.getCommonCodeByConditions("add-ve-virtualPlatformVersion",null,{parentCodeValue:virtualType});
                }
             });
	    	
	    	$("#addHostClusterWindow").jqxWindow({
		        width: 350, 
		        height:158,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addHostClusterCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	//初始化文本框
		        	$("#add-cluster-resTopologyName").jqxInput({placeHolder: "", height: 23, width: 150,maxLength:32, minLength: 1,theme:currentTheme});
		        	$("#add-cluster-description").jqxInput({placeHolder: "", height: 35, width: 250, minLength: 1,theme:currentTheme});
			    	//初始化按钮
		        	$("#addHostClusterSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addHostClusterCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	    	
	    	$("#newfileUploadWindow").jqxWindow({
		        width: 350, 
		        height:130,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#importFileCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
		        	//$("#fileAttach").jqxInput({placeHolder: "", height: 22, width: 220,maxLength:32, minLength: 1,theme:currentTheme});
//		        	$("#add-cluster-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});
			    	$("#importFile").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#importFileCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
		
	    	
	    	
	    };
  };
  
  // 确认资源环境信息
  function confirmVirtualInfo(){
	  // 先检查是否输入的信息都是正确的
	  var isOk = $('#addVeForm').jqxValidator('validate');
	  // 表单验证不成功
	  if($('#add-ve-virtualPlatformType').val() == "IVM" || $('#add-ve-virtualPlatformType').val() == "Other"){
		  if($("#add-ve-resTopologyName").val() == null || $("#add-ve-resTopologyName").val() == ""){
			  return;
		  }else{
			  isOk = true;
		  }
	  }else{
		  if(!isOk){
			  return;
		  }
	  }
	 
	  var virtualPlatformType =  $('#add-ve-virtualPlatformType').jqxDropDownList('getSelectedItem').label;
	  
	  // 判断下拉列表框的值是否是空
	  var zone = $("#add-ve-zone").val();
	  var dc = $("#add-ve-dc").val();
	  var virtual = Core.parseJSON($("#addVeForm").serializeJson());
	  if("OpenStack" == virtualPlatformType){
		  
	  }else if("VMware" == virtualPlatformType){
		  // 判断是否能连接vCenter
		  Core.AjaxRequest({
				url : ws_url + "/rest/ves/testConnect",
				params : virtual,
				async:false,
				callback : function (data) {
					if(data.result == "failure"){
						isOk = false;
						Core.alert({
							title: "提示",
	 						message: "对不起，不能连接到该资源环境！",
	 						callback:function(){
//	 							$('#addVeWindow').jqxWindow('close');
	 						}
						});
					}
			    }
		  });
	  }
	  // 判断是否能连接vCenter
//	  Core.AjaxRequest({
//			url : ws_url + "/rest/ves/testConnect",
//			params : virtual,
//			async:false,
//			callback : function (data) {
//				if(data.result == "failure"){
//					isOk = false;
//					Core.alert({
//						title: "提示",
// 						message: "对不起，不能连接到该资源环境！",
// 						callback:function(){
// 							$('#addVeWindow').jqxWindow('close');
// 							
// 						}
//					});
//				}
//		    }
//	  });
	  
	  if(isOk){
		  if(zone =="" || zone == null){
			  Core.alert({
				  title: "提示",
				  message: "区域不能为空"
			  });
			  return false;
		  }
		  if(dc =="" || dc == null){
			  Core.alert({
				  title: "提示",
				  message: "数据中心不能为空"
			  });
			  return false;
		  }
		  // 如果正确，跳转到确认信息
		  $("#virtualInfo").css({"color":"gray"});
		  $("#confirmInfo").css({"color":"#0099d7"});
		  $("#veInfo").animate({left:'-=304'},'fast',function(){
			  $("#confirmVeInfo").animate({left:'-=304'},'fast');
			  // 给确认消息赋值
			  $("#add-ve-confirm-zone").html($("#add-ve-zone").jqxDropDownList('getSelectedItem').label);
			  $("#add-ve-confirm-dc").html($("#add-ve-dc").jqxDropDownList('getSelectedItem').label);
			  $("#add-ve-confirm-resTopologyName").html($("#add-ve-resTopologyName").val());
			  $("#add-ve-confirm-resTopologyType").html($('#add-ve-resTopologyType').jqxDropDownList('getSelectedItem').label);
			  $("#add-ve-confirm-virtualPlatformType").html($("#add-ve-virtualPlatformType").jqxDropDownList('getSelectedItem').label);
			  $("#add-ve-confirm-virtualPlatformVersion").html($("#add-ve-virtualPlatformVersion").jqxDropDownList('getSelectedItem').label);
			  $("#add-ve-confirm-managementUrl").html($("#add-ve-managementUrl").val());
			  $("#add-ve-confirm-managementAccount").html($("#add-ve-managementAccount").val());
			  $("#add-ve-confirm-managementPassword").html($("#add-ve-managementPassword").val());
			  $("#add-ve-confirm-confirmPassword").html($("#add-ve-confirmPassword").val());
			  $("#add-ve-confirm-updateCycle").html($("#add-ve-updateCycle").val());
		  });
	  }
  }
  
  // 返回上一步
  function goInsertVirtualInfo(){
	  $("#virtualInfo").css({"color":"#0099d7"});
	  $("#confirmInfo").css({"color":"gray"});
	  $("#confirmVeInfo").animate({left:'+=304'},'fast',function(){
		  $("#veInfo").animate({left:'+=304'},'fast');
	  });
  }
  
  // 保存资源环境信息
  function saveConfirmVirtualInfo(){
	  var virtual = Core.parseJSON($("#addVeForm").serializeJson());
	  Core.AjaxRequest({
			url : ws_url + "/rest/ves/create",
			params :virtual,
			callback : function (data) {
				if($("#virtualVeDatagrid").length > 0){
					// 在datagrid中新增，刷新datagrid、tree
					var ve = new virtualVeDatagridModel();
					ve.searchVirtualVeInfo();
					setVirtualTreeValue();
				}else{
					// 在概要中新增，刷新tree
					setVirtualTreeValue();
				}
				$("#addVeWindow").jqxWindow('close');
		    }
	  });
  }
  
  // 关闭新增资源环境窗口
  function closeAddVirtualWindow(){
	  $('#addVeWindow').jqxWindow('close');
  }
  
  //弹出新增集群窗口
  function popAddHostClusterWindow(){
		$("#add-cluster-resTopologyName").val(null);
		$("#add-cluster-description").val(null);
		var windowW = $(window).width(); 
	  	var windowH = $(window).height(); 
		$("#add-cluster-parentTopologySid").val(resTopologySid);
		$("#add-cluster-resTopologyType").val("VC");
	  	$("#addHostClusterWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
	  	$("#addHostClusterWindow").jqxWindow('open');
  }
  //模板下载
  function exportHostStorangModel(){
	 window.location = ws_url + "/rest/ves/hoststorangexcels/exportExcelModel";
  }
	 
  function openHostStorangModel(){
	 var windowW = $(window).width(); 
	 var windowH = $(window).height(); 
	 $("#newfileUploadWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
	 $("#newfileUploadWindow").jqxWindow('open');
  }
	  
  function importHostStorangModel(){
		 var filename = $("#fileAttach").val();
		 filename = filename.substring(filename.lastIndexOf("\\")+1,filename.length);
		 if(filename !=""&& filename!=null){
			 Core.AjaxFormSubmit({
			 formId : "upload",
			 url : ws_url+"/rest/ves/importFile",
			 async : false,
			 callback : function(data) {
				 if(null!=data){
					 Core.AjaxRequest({
						 url : ws_url + "/rest/ves/importFileDate",
						 type:"post",
						 params:{
							 	data:data,
								resTopologySid:resTopologySid
						 },
						 async:false,
						 callback : function (data) {
							 if(data.status == "failure"){
								 $('#newfileUploadWindow').jqxWindow('close');
							 }else{
								 $('#newfileUploadWindow').jqxWindow('close');
		 							setVirtualTreeValue();
			 						setStorageVirtualTreeValue();
							 }
						 }
					 });
				 }
			 }
			 });
		 }
	  }	  
  function addHostClusterSubmit(){
	$('#addHostClusterForm').jqxValidator('validate');
  }
  