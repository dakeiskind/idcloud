var vlanEditName = "";
var editPnvModel = function () {
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
			
	    // 初始化验证规则
	    this.initValidator = function(){
	    	$('#editPnvForm').jqxValidator({
                rules: [
                          { input: '#edit-pnv-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-pnv-name', message: 'VLAN池名称不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
                          { input: '#edit-pnv-name', message: 'VLAN池名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
                        	  if(vlanEditName == input.val()){
    		                  		return true;
    	                  	  }	else{
    		                  	   var list = me.getVlanPoolName(input.val());
    		                  	  	if(list.length > 0){
    		                  	  		return false;
    		                  	  	}else{
    		                  	  		return true;
    		                  	  	}
    	                  	  }	
    	                    }
    		              },                          
                       ]
	    	});
	    	
	    	// 编辑用户表单验证成功
	    	$('#editPnvForm').on('validationSuccess', function (event) {
	    			
	    			var valnPojo = Core.parseJSON($("#editPnvForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/poolVlans/update",
		 				type:"POST",
		 				params :valnPojo,
		 				callback : function (data) {
		 					// 关闭编辑窗口
		 					$("#editPnvWindow").jqxWindow('close');
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
			$("#editPnvWindow").jqxWindow({
	                width: 350, 
	                height:190,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#editPnvCancel"), 
	                modalOpacity: 0.3,
	                theme:currentTheme,
	                initContent:function(){
	                	$("#edit-pnv-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                	
	        	        // 初始化编辑用户页面组件
//	        	        $("#edit-pnv-type").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	        	        
	                	$("#edit-pnv-type_1").jqxRadioButton({ width: 100, height: 22,theme:currentTheme,checked: true,});
	        	        $("#edit-pnv-type_2").jqxRadioButton({ width: 100, height: 22,theme:currentTheme,disabled:true});
	                
	        	        $("#edit-pnv-description").jqxInput({placeHolder: "", height: 40, width: 200, minLength: 1,maxLength:128,theme:currentTheme});
	        	        
	        	        $("#editPnvSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        $("#editPnvCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                }
	         });
	    };
  };
  
  // 弹出编辑vlan池
  function popEditVlanPoolWindow(){
	  	var rowindex = $('#poolPNVDatagrid').jqxGrid('getselectedrowindex');
		var ok =  $('#jqxEditBtn').jqxButton('disabled');
	  	if(!ok && rowindex >= 0){
	  		var data = $('#poolPNVDatagrid').jqxGrid('getrowdata', rowindex);
	  		// 给编辑组件赋值 
	  		$("#edit-pnv-resPoolSid").val(data.resPoolSid);
	  		$("#edit-pnv-name").val(data.resPoolName);
	  		vlanEditName = data.resPoolName;
	  		$("#edit-pnv-description").val(data.description);
		  	// 获取所属资源分区 
		  	var windowW = $(window).width(); 
			var windowH = $(window).height(); 
			$("#editPnvWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-190)/2 } });
			$("#editPnvWindow").jqxWindow('open');
	  	}
	  	
  }
  
  // 提交编辑VLAN池信息
  function submitEditVlan(){
	  $('#editPnvForm').jqxValidator('validate');
  }

  