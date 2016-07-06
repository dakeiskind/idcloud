var virtualImageReleaseModel = function () {
		var me = this;
		this.label = "";
		// 验证初始化
	    this.initValidator = function(){
	    	$('#findImageWindow').jqxValidator({
                rules: [
					{ input: '#add-image-nimIp', message: '不能为空', action: 'keyup, blur', rule: 'required' },
					{ input: '#add-image-nimIp', message: 'IP地址不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
					{ input: '#add-image-nimIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
						  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
						  if(!pattern.test(input.val())){
									return false;
								}else{
									return true;
								}
							}
					},
					{ input: '#add-image-nimUser', message: '不能为空', action: 'keyup, blur', rule: 'required' },
					{ input: '#add-image-nimPassword', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                        
                     ]
	    	});
	    };
		
		//初始下拉列表框的联动问题
	    this.initComboxLinkage = function(){
	    	var codeadd = new codeModel({width:300,autoDropDownHeight:true,dropDownWidth:300});
	    	
	    	$('#release-image-zone').on('change', function (event){  
				    var args = event.args;
				    if (args) {
					    var item = args.item;
					    var value = item.value;
					    codeadd.getCustomCode("release-image-dc","/topology","resTopologyName","resTopologySid",false,"POST",{parentTopologySid:value,resTopologyType:"DC"});
					    // 判断数据中心是否为空
					    me.getDcValue();
				    } 
			});
	    	
	    	$('#release-image-dc').on('change', function (event){
	    			var args = event.args;
				    if (args) {
					    var item = args.item;
					    var value = item.value;
					    codeadd.getCustomCode("release-image-resTopologyName","/topology","resTopologyName","resTopologySid",false,"POST",{parentTopologySid:value,resTopologyType:"VE"});
					    var items = $("#release-image-resTopologyName").jqxDropDownList('getItems');
					    if(items.length > 0){
					    	$("#release-image-resTopologyName").jqxDropDownList('selectItem', items[0] );
					    }
					    
					    var vesid = items.value;
					    
				    }
	    	});
	    	
	    	$('#release-image-resTopologyName').on('change', function (event){
    			var args = event.args;
			    if (args) {
				    var item = args.item;
				    var resTopolodySid = item.value;
				    Core.AjaxRequest({
			 			url : ws_url + "/rest/ves/"+resTopolodySid,
			 			type:'get',
			 			async:false,
			 			callback : function (data) {
			 				remoteData = data;
			 				if("HMC"==remoteData.virtualPlatformType){
			 					document.getElementById("nimMessage").style.display="";
			 				}else{
			 					document.getElementById("nimMessage").style.display="none";
			 				}
			 			}
			 	    });
			    }
    	});
	    	
	    	
	    	
	    	
	    	codeadd.getCustomCode("release-image-zone","/topology","resTopologyName","resTopologySid",false,"POST",{resTopologyType:"R"});
   			var zoneSid =  $("#release-image-zone").val();
   			codeadd.getCustomCode("release-image-dc","/topology","resTopologyName","resTopologySid",false,"POST",{parentTopologySid:zoneSid,resTopologyType:"DC"});
   			var dcSid =  $("#release-image-dc").val();
   			if(dcSid==""){
   				codeadd.getCustomCode("release-image-resTopologyName","/topology","resTopologyName","resTopologySid",false,"POST",{parentTopologySid:0,resTopologyType:null});
   			}else{
   				codeadd.getCustomCode("release-image-resTopologyName","/topology","resTopologyName","resTopologySid",false,"POST",{parentTopologySid:dcSid,resTopologyType:"VE"});
   			}
   			var veSids = $("#release-image-resTopologyName").val();
   			me.getResTopologyVeSid(veSids);
	    };
	    
	    this.getDcValue = function (){
	    	
	    	var items = $("#release-image-dc").jqxDropDownList('getItems');
	    	if(items.length > 0){
	    		$("#release-image-dc").jqxDropDownList('selectItem', items[0] );
	    	}else{
	    		var codeadd = new codeModel({width:300,autoDropDownHeight:true,dropDownWidth:300});
	    		codeadd.getCustomCode("release-image-resTopologyName","/topology","resTopologyName","resTopologySid",false,"POST",{parentTopologySid:0,resTopologyType:null});
	    	}
	    };
	    this.getResTopologyVeSid = function(veSids){
			    var resTopolodySid = veSids;
			    Core.AjaxRequest({
		 			url : ws_url + "/rest/ves/"+resTopolodySid,
		 			type:'get',
		 			async:false,
		 			callback : function (data) {
		 				remoteData = data;
		 				if("HMC"==remoteData.virtualPlatformType){
		 					document.getElementById("nimMessage").style.display="";
		 				}else{
		 					document.getElementById("nimMessage").style.display="none";
		 				}
		 			}
		 	    });
	    }
	   
	    // 初始化弹出window
	    this.initPopWindow = function(){
	    	$("#releaseImageWindow").jqxWindow({
                width: 450, 
                height:250,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#releaseImageCancel"), 
                theme: currentTheme,
                modalOpacity: 0.3,
                initContent:function(){
                	 // 初始化新增用户页面组件
//                	var codesrelease = new codeModel({width:300,autoDropDownHeight:true,dropDownWidth:300});
//                	codesrelease.getCustomCode("release-image-zone","/topology","resTopologyName","resTopologySid",false,"POST",{resTopologyType:"R"});
//           			var zoneSid =  $("#release-image-zone").val();
//           			codesrelease.getCustomCode("release-image-dc","/topology","resTopologyName","resTopologySid",false,"POST",{parentTopologySid:zoneSid,resTopologyType:"DC"});
//           			var dcSid =  $("#release-image-dc").val();
//           			//alert("33"+dcSid);
//           			if(dcSid==""){
//           				codesrelease.getCustomCode("release-image-resTopologyName","/topology","resTopologyName",null,false,"POST",{parentTopologySid:1111,resTopologyType:null});
//           			}else{
//           				codesrelease.getCustomCode("release-image-resTopologyName","/topology","resTopologyName","resTopologySid",false,"POST",{parentTopologySid:dcSid,resTopologyType:"VE"});
//           			}
           			$("#releaseImageSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
           			$("#releaseImageCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
           			$("#add-image-nimIp").jqxInput({placeHolder: "", height: 22, width: 300, minLength: 256,theme:currentTheme});
           			$("#add-image-nimUser").jqxInput({placeHolder: "", height: 22, width: 300, minLength: 256,theme:currentTheme});
           			$("#add-image-nimPassword").jqxPasswordInput({placeHolder: "", height: 22, width: 300, minLength: 1 ,theme:currentTheme, showStrengthPosition: "right"});
                },
             });
	    };
	    

  };
  // 确认资源环境信息
  // 保存资源环境信息
  function releaseConfirmVirtualInfo(){
	  var virtual = Core.parseJSON($("#releaseImageForm").serializeJson());
//	  var vesid = $("#release-image-resTopologyName").val();;
//	  var dcsid = $("#release-image-dc").val();
	  var dcsid = $("#release-image-dc").jqxDropDownList('getItems');
	  var vesid = $("#release-image-resTopologyName").jqxDropDownList('getItems');
	  if(dcsid.length == 0){
		  Core.confirm({
			  title:"提示",
			  message:"请选择数据中心"
		  });
		  return false;
	  }else if(vesid.length == 0){
		  Core.confirm({
			  title:"提示",
			  message:"请选择资源环境"
		  });
		  return false;
	  }
	  Core.AjaxRequest({
			url : ws_url + "/rest/templates/create",
			params :virtual,
			callback : function (data) {
				$("#releaseImageWindow").jqxWindow('close');
		    }
	  });
  }
  
  // 关闭新增资源环境窗口
  function closeAddVirtualWindow(){
	  $('#releaseImageWindow').jqxWindow('close');
  }

  