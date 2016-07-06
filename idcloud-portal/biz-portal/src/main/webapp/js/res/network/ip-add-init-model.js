// 新增主机 
var addIpModel = function () {
		var me = this;

	    // 验证新增画面
		this.initValidator = function(){
			$('#addIpForm').jqxValidator({
		        rules: [  
		                  { input: '#add-ipAddressStart-ip1', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-ipAddressStart-ip1', message: 'ip起始地址不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
		                  { input: '#add-ipAddressEnd-ip1', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-ipAddressEnd-ip1', message: 'ip终止地址不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
			               ]
				});
	    	
			// 新增ip池验证成功
			$('#addIpForm').on('validationSuccess', function (event) {
				 var pool = Core.parseJSON($("#addIpForm").serializeJson());
				 var param = {};
				 param.resTopologySid = resTopologySid;
				 param.isResPoolSearch = resTopologyType;
	    		 var newObj = $.extend(pool,param);
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/ips/create",
		 				params :newObj,
		 				callback : function (data) {
		 	            	me.initAddContent();
		 					// 刷新基本信息
		 					var ip = new ipConfigMgtModel();
		 					ip.searchIpConfigInfo();
		 					
		 					$("#addIpWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
							$("#addIpWindow").jqxWindow('close');
		 			    }
		 			});
		     });
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	$("#addIpWindow").jqxWindow({
	            width: 600, 
	            height:250,
	            theme:currentTheme,
	            resizable: false,  
	            isModal: true, 
	            autoOpen: false, 
	            cancelButton: $("#addIpCancel"), 
	            modalOpacity: 0.3,
	            initContent:function(){
	            	// 初始化组件
	                $("#add-ipAddressStart-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	                $("#add-ipAddressEnd-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	                $("#add-subnetMask-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	                $("#add-dns-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	                $("#add-gateway-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	                $("#add-comments-ip1").jqxInput({placeHolder: "", height: 46, width:500, minLength: 1,theme:currentTheme});
	                $("#add-mapPublicIp-ip1").jqxInput({placeHolder: "", height: 22, width:100, minLength: 1,theme:currentTheme});

	                $("#addIpSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                $("#addIpCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                
	                // 初始化下拉列表框 
	        		var codesearch = new codeModel({width:100,autoDropDownHeight:true});
	        		codesearch.getCommonCode("add-ipType-ip1","IP_TYPE",false);
	        		codesearch.getCommonCode("add-ipCategory-ip1","IP_CATEGORY",false);
	        		codesearch.getCustomCode("add-vlanId-ip1","/vlans","vlanName","vlanId",false);

	            }
	        });
	    };
	    
	    // 判断操作按钮，初始化按钮
	    this.initAddContent = function(){
	    	// 初始化组件
	        $("#add-ipAddressStart-ip1").val("");
	        $("#add-ipAddressEnd-ip1").val("");
	        $("#add-subnetMask-ip1").val("");
	        $("#add-dns-ip1").val("");
	        $("#add-gateway-ip1").val("");
	        $("#add-comments-ip1").val("");
	        $("#add-ipType-ip1").val("");
	        $("#add-ipCategory-ip1").val("");
	        $("#add-vlanId-ip1").val("");
	        $("#add-mapPublicIp-ip1").val("");
	       };
 };
 
//弹出添加ip框
 function addIpInfoWindow(){

	if(resTopologyType == "pool") {
		//查询公网还是内网
		var resPoolData = "";
		var resPoolSid = resTopologySid;

		Core.AjaxRequest({
				url : ws_url + "/rest/pools/"+resPoolSid+"",
				type:"get",
				async:false,
				callback : function (data) {
					resPoolData = data;
			    },
			    failure:function(data){
			    	
			    }
		 });
		
		if(resPoolData.ipCategory == "01") {
			$("#add-ipCategory-ip1").jqxDropDownList({selectedIndex: 0, disabled: true});
		} else if (resPoolData.ipCategory == "02") {
			$("#add-ipCategory-ip1").jqxDropDownList({selectedIndex: 1, disabled: true});
		}

	} else {
		$("#add-ipCategory-ip1").jqxDropDownList({
		      disabled: false
		  });
	}
 	var windowW = $(window).width(); 
   	var windowH = $(window).height();
   	$("#addIpWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-250)/2 } });
   	$("#addIpWindow").jqxWindow('open');
 }

 // 提交将选择主机加入池
 function addIpSubmit(){
 	$('#addIpForm').jqxValidator('validate');
 }
  