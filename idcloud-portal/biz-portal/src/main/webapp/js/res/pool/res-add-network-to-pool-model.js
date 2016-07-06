var addNetworkToResPoolModel = function(){
	var me = this;
    this.items = ko.observableArray();

	 /*********************************** vlan**************************************/

    // 给vlan datagrid赋值
    this.searchfindVlanAddToPoolData = function(){
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/pools/vlan/findAddVlan/"+resTopologySid + "",
 			type:'get',
 			async:false,
 			callback : function (data) {
 				me.items(data);
 				var sourcedatagrid ={
		              datatype: "json",
		              localdata: me.items
 			    };
 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#findVlanAddToPool").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
    // 给Ip datagrid赋值
    this.searchfindIpAddToPoolData = function(){
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/pools/ip/findAddIp/"+resTopologySid + "",
 			type:'get',
 			async:false,
 			callback : function (data) {
 				me.items(data);
 				var sourcedatagrid ={
		              datatype: "json",
		              localdata: me.items
 			    };
 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#findIpAddToPool").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
    // 根据sid查询资源池信息
	this.searchResPool = function(){
		var resPoolData;

		Core.AjaxRequest({
				url : ws_url + "/rest/pools/"+resTopologySid+"",
				type:"get",
				async:false,
				callback : function (data) {
					resPoolData = data;
			    },
			    failure:function(data){
			    	
			    }
		 });
		return resPoolData;
	};
	
    // 初始化用户vlandatagrid的数据源
    this.initfindVlanAddToPoolDatagrid = function(){
          $("#findVlanAddToPool").jqxGrid({
              width: "100%",
			  theme:currentTheme,
              columnsresize: true,
              pageable: true, 
              pagesize: 5, 
              autoheight: true,
              autowidth: false,
              sortable: true,
              selectionmode:"checkbox",
              localization: gridLocalizationObj,
              columns: [
                  { text: 'VLAN ID', datafield: 'vlanId'},
                  { text: '名称', datafield: 'vlanName'},
                  { text: '状态', datafield: 'statusName'},
                  { text: '标签', datafield: 'tag'}
              ]
          });
    };
    
    // 初始化用户datagrid的数据源
    this.initfindIpAddToPoolDatagrid = function(){
          $("#findIpAddToPool").jqxGrid({
              width: "100%",
			  theme:currentTheme,
              columnsresize: true,
              pageable: true, 
              pagesize: 5, 
              autoheight: true,
              autowidth: false,
              sortable: true,
              selectionmode:"checkbox",
              localization: gridLocalizationObj,
              columns: [
                  { text: 'IP地址', datafield: 'ipAddress'},
                  { text: 'IP类别', datafield: 'ipCategoryName'},
                  { text: 'IP类型', datafield: 'ipTypeName'},
                  { text: '对应公网地址', datafield: 'mapPublicIp'},
                  { text: 'VLANID', datafield: 'vlanId'},
                  { text: '备注', datafield: 'comments'}
              ]
          });
    };
    
    // 初始化弹出window
	this.initVlanPopWindow = function(){   	
		  $("#addVlanToRespoolWindow").jqxWindow({
		        width: 680, 
		        height:258,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addVlanToPoolCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
			    	$("#addVlanToPoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addVlanToPoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
	 
    // 初始化IP弹出window
	this.initIpPopWindow = function(){   	
		  $("#addIpToRespoolWindow").jqxWindow({
		        width: 600, 
		        height:250,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addIpToPoolCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
			    	$("#addIpToPoolSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addIpToPoolCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
	 
	// 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
        $("#add-ipAddressStart").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#add-ipAddressEnd").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#add-subnetMask").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#add-dns").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#add-gateway").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#add-comments").jqxInput({placeHolder: "", height: 46, width:500, minLength: 1,theme:currentTheme});

        $("#addIpToPoolSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
        $("#addIpToPoolCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
        
        // 初始化下拉列表框 
		 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
		 codesearch.getCommonCode("add-ipType","IP_TYPE",false);
		 codesearch.getCustomCode("add-vlanId","/vlans","vlanName","vlanId",false);

    };
		 
	// 验证Ip
	this.initIpValidator = function(){
		
		$('#addIpToRespoolForm').jqxValidator({
	        rules: [  
	                  { input: '#add-ipAddressStart', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-ipAddressStart', message: 'ip起始地址不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
	                  { input: '#add-ipAddressEnd', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-ipAddressEnd', message: 'ip终止地址不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' }
		                 
		               ]
			});
		    	
			// 新增ip池验证成功
			$('#addIpToRespoolForm').on('validationSuccess', function (event) {
				 var pool = Core.parseJSON($("#addIpToRespoolForm").serializeJson());
				 var param = {};
	    		 param.resTopologySid = resTopologySid;
	    		 param.ipCategory = $("#add-ipCategory").val();
	    		 var newObj = $.extend(pool,param);
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/pools/ip/addToPool",
		 				params :newObj,
		 				callback : function (data) {
		 					// 刷新基本信息
		 					setNetworkTreeValue();
							$("#addIpToRespoolWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
							$("#addIpToRespoolWindow").jqxWindow('close');
		 			    }
		 			});
		     });
	    };
	};

	// 弹出添加Vlan到池的window框
	function popAddVlanToPoolWindow(){
		var windowW = $(window).width(); 
	  	var windowH = $(window).height(); 
	  	$("#addVlanToRespoolWindow").jqxWindow({ position: { x: (windowW-680)/2, y: (windowH-258)/2 } });
	  	$("#addVlanToRespoolWindow").jqxWindow('open');
	}
		
	// 提交将选择Vlan加入池
	function addVlanToPoolSubmit(){
		// 得到选中的值
		 var resSids ="";
		 var rowindex = $('#findVlanAddToPool').jqxGrid('getselectedrowindexes');
		 
		 if(rowindex.length >= 0){
		   		for(var i=0;i<rowindex.length;i++){
		   			var data = $('#findVlanAddToPool').jqxGrid('getrowdata', rowindex[i]);
		   			if(i == rowindex.length-1){
		   				resSids+= data.resSid;
					}else{
						resSids+= data.resSid+",";
					}
		   		}
   		
	   		// 提交添加存储
	   		Core.AjaxRequest({
				url : ws_url + "/rest/pools/vlan/addToPool/"+resTopologySid+"/"+resSids+"",
				type:"get",
				callback : function (data) {
					// 关闭window
					$("#addVlanToRespoolWindow").jqxWindow('close');
					// 刷新
					setNetworkTreeValue();
					
			    },
			    failure:function(data){
			    	
			    }
		});
   	}
  	
	};

	// 弹出添加Ip到池的window框
	function popAddIpToPoolWindow(){
		var respool = new addNetworkToResPoolModel();
		// 编辑框赋值
		var resPoolData = respool.searchResPool();
	  	$("#add-ipCategory").val(resPoolData.ipCategory);

		var windowW = $(window).width(); 
	  	var windowH = $(window).height();
	  	$("#addIpToRespoolWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-250)/2 } });
	  	$("#addIpToRespoolWindow").jqxWindow('open');
	}

	// 提交将选择Ip加入池
	function addIpToPoolSubmit(){
		//$('#addIpToRespoolForm').jqxValidator('validate');
		
		// 得到选中的值
		 var resSids ="";
		 var rowindex = $('#findIpAddToPool').jqxGrid('getselectedrowindexes');
		 
		 if(rowindex.length >= 0){
		   		for(var i=0;i<rowindex.length;i++){
		   			var data = $('#findIpAddToPool').jqxGrid('getrowdata', rowindex[i]);
		   			if(i == rowindex.length-1){
		   				resSids+= data.resSid;
					}else{
						resSids+= data.resSid+",";
					}
		   		}
  		
	   		// 提交添加存储
	   		Core.AjaxRequest({
				url : ws_url + "/rest/pools/ip/addToPool/"+resTopologySid+"/"+resSids+"",
				type:"get",
				callback : function (data) {
					// 关闭window
					$("#addIpToRespoolWindow").jqxWindow('close');
					// 刷新
					setNetworkTreeValue();
					
			    },
			    failure:function(data){
			    	
			    }
		});
  	}
	}
