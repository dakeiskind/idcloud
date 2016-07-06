var addPnvModel = function () {
		var me = this;
		
		// 判断VLAN池名称是否重复
		this.getVlanPoolName = function(name){
			var Todata = null;
			Core.AjaxRequest({
	 			url : ws_url + "/rest/poolVlans",
	 			type:'POST',
	 			async:false,
	 			params:{
	 				resPoolName : name
	 			},
	 			callback : function (data) {
	 				Todata = data;
	 			}
	 		 });
			return Todata;
		};
		
		// 查询同一个资源分区下的VLAN
		this.searchAllVlanInResZone = function(){
			 var remoteData;
			 Core.AjaxRequest({
	 			url : ws_url + "/rest/vlanRes/findInZone",
	 			type:'post',
	 			params:{
	 				parentTopologySid : resTopologySid
	 			},
	 			async:false,
	 			callback : function (data) {
	 				remoteData = data;
	 			}
	 		 });
			 
			 return remoteData;
		};
		
	    // 初始化验证规则
	    this.initValidator = function(){
	    	$('#addPnvForm').jqxValidator({
                rules: [
                          { input: '#add-pnv-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-pnv-name', message: 'VLAN池名称不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
                          { input: '#add-pnv-name', message: 'VLAN池名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
  	                  	  	var list = me.getVlanPoolName(input.val());
  	                  	  	if(list.length > 0){
  	                  	  		return false;
  	                  	  	}else{
  	                  	  		return true;
  	                  	  	}
  	                      }
  		              },
                          
                       ]
	    	});
	    	
	    	// 新增用户表单验证成功
	    	$('#addPnvForm').on('validationSuccess', function (event) {
	    			var valnPojo = Core.parseJSON($("#addPnvForm").serializeJson());
	    			 // 获得vlan池类型
		    		 var vlan = $("#add-pnv-type_1").val()?"01":"NaN";
		    		
		    		 valnPojo.vlanPoolType = vlan;
		    
		    		 valnPojo.resTopologySid = resTopologySid;
		    		
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/poolVlans/create",
		 				type:"POST",
		 				params :valnPojo,
		 				callback : function (data) {
		 					// 关闭新增窗口
		 					$("#addPnvWindow").jqxWindow('close');
		 					// 刷新datagrid
		 					var pdm = new poolPnvDatagridModel();
		 					pdm.searchPnvInfo();
		 			    },
		 			    failure:function(data){
		 			    	
		 			    }
		 			});
	    	 });
	    	
	    };
	    
	    // 初始化弹出window
	    this.initPopWindow = function(){
			$("#addPnvWindow").jqxWindow({
	                width: 350, 
	                height:190,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#addPnvCancel"), 
	                modalOpacity: 0.3,
	                theme:currentTheme,
	                initContent:function(){
	                	$("#add-pnv-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                	
	                	$("#add-pnv-type_1").jqxRadioButton({ width: 100, height: 22,theme:currentTheme,checked: true,});
	                	
	        	        $("#add-pnv-description").jqxInput({placeHolder: "", height: 50, width: 200, minLength: 1,maxLength:128,theme:currentTheme});
	        	        
	        	        $("#addPnvSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        $("#addPnvCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                }
	         });
	    };
  };
  
  // 弹出新增vlan池
  function popAddVlanPoolWindow(){
	    // 清空数据
	  	$("#add-pnv-name").val("");
	  	$("#add-pnv-description").val("");
	  	
	  	var windowW = $(window).width(); 
		var windowH = $(window).height(); 
		$("#addPnvWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-190)/2 } });
		$("#addPnvWindow").jqxWindow('open');
  }
  
  // 提交新增VLAN池信息
  function submitAddVlan(){
	  $('#addPnvForm').jqxValidator('validate');
  }

  