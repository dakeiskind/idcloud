var pneNetworkViewIpGrid = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		ipAddressLike: "",
    		ipType:"",
    		usageStatus:"",
    		resNetworkSid:""
    	};
    // 用户数据
    this.searchIpConfigInfo = function(resNetworkSid){
    	 me.searchObj.resNetworkSid = resNetworkSid;
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/ip",
 			type:'post',
 			params:me.searchObj,
 			async:false,
 			callback : function (data) {
 				me.items(data);
 				var sourcedatagrid ={
		              datatype: "json",
		              localdata: me.items
 			    };
 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#pneNetworkIpConfigMgtdatagrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
    // 判断地址是否重复
	this.getRepeatIpAddress = function(ipAddress){
		var todata = null;
		Core.AjaxRequest({
 			url : ws_url + "/rest/ip",
 			type:'POST',
 			async:false,
 			params:{
 				ipAddress: ipAddress,
 				resNetworkSid : resourcePoolSid
 			},
 			callback : function (data) {
 				todata = data;
 			}
 		 });
		return todata;
	};
	
	// 验证
    this.initValidator = function(){
    	// 新增区域验证
		$('#addPneNetworkOwnIpForm').jqxValidator({
	        rules: [  
	                  { input: '#add-pne-network-IP-address', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-pne-network-IP-address', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
                    	  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                    	  if(!pattern.test(input.val())){
              	  				return false;
              	  			}else{
              	  				return true;
              	  			}
              	  		}
	                  },
	                  { input: '#add-pne-network-IP-address', message: 'IP地址重复，请重新输入', action: 'blur', rule: function (input, commit) {
	                  	  	var list = me.getRepeatIpAddress(input.val());
	                  	  	if(list.length > 0){
	                  	  		return false;
	                  	  	}else{
	                  	  		return true;
	                  	  	}
	                      }
		              },
	               ]
		});
    	
		// 新增区域验证成功
		$('#addPneNetworkOwnIpForm').on('validationSuccess', function (event) {
			 	 var ip = Core.parseJSON($("#addPneNetworkOwnIpForm").serializeJson());
			 	 
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/ip/create",
	 				params :ip,
	 				callback : function (data) {
	 					$("#addPneNetworkOwnIpWindow").jqxWindow('close');
	 					// 刷新基本信息
	 					var  pneIp = new pneNetworkViewIpGrid();
	 					pneIp.searchIpConfigInfo(resourcePoolSid);
	 			    }
	 			});
	     });
	   
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	var ipconfig = new codeModel({width:120,autoDropDownHeight:true});
    	$("#search-pne-network-IP-address").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	ipconfig.getCommonCode("search-pne-network-IP-usage-status","RES_IP_STATUS",true);
        $("#search-pne-network-ip-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
        $("#pneNetworkIpCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
        
        // 新增Ip
        $("#add-pne-network-IP-address").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        $("#pne-network-button-save").jqxButton({ width: '50',height:"25",theme:currentTheme});
        $("#pne-network-button-cancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
    };
  
    // 初始化用户datagrid的数据源
    this.initIpDatagrid = function(){
          $("#pneNetworkIpConfigMgtdatagrid").jqxGrid({
              width: "99.5%",
			  theme:currentTheme,
              columnsresize: true,
              pageable: true, 
              pagesize: 10, 
              autoheight: true,
              autowidth: false,
              sortable: true,
              selectionmode:"checkbox",
              localization: gridLocalizationObj,
              columns: [
                  { text: 'IP地址', datafield: 'ipAddress'},
                  { text: '使用状态', datafield: 'usageStatusName'},
                  { text: '所属虚拟机', datafield: 'vmName'}
              ],
              showtoolbar: true,
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var addBtn = $("<div><a id='jqxPneAddBtn' onclick ='popAddPneNetworkWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus'></i>新增&nbsp;&nbsp;</a></div>");
                  var remarkBtn = $("<div><a id='jqxPneRemarkBtn' onclick ='popPneRemarkIpInNetwork()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-location'></i>占用&nbsp;&nbsp;</a></div>");
                  var releseBtn = $("<div><a id='jqxPneReleseBtn' onclick ='popPneReleseIpInNetwork()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-share'></i>释放&nbsp;&nbsp;</a></div>");
                  var deleteBtn = $("<div><a id='jqxPneDeleteBtn' onclick ='removePneIpInNetwork()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel'></i>删除&nbsp;&nbsp;</a></div>");

                  toolbar.append(container);
                  container.append(addBtn);
                  container.append(remarkBtn);
                  container.append(releseBtn);
                  container.append(deleteBtn);
              }
          });
          
          // 控制按钮是否可用
    	  $("#pneNetworkIpConfigMgtdatagrid").on('rowselect', function (event) {
    		  var args = event.args; 
    		  var index = args.rowindex;
    		  var data = $('#pneNetworkIpConfigMgtdatagrid').jqxGrid('getrowdata', index);
    		  $("#jqxPneDeleteBtn").jqxButton({disabled: false});
    		  $("#jqxPneRemarkBtn").jqxButton({disabled: false});
    		  $("#jqxPneReleseBtn").jqxButton({disabled: false});
    		  
          });
    	  
    	  $("#jqxPneAddBtn").jqxButton({width: '60',theme:currentTheme,height: '18px' });
    	  $("#jqxPneDeleteBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
    	  $("#jqxPneRemarkBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
    	  $("#jqxPneReleseBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
    };
    
    
    // 初始化弹出window
    this.initPopWindow = function(){
    	$("#viewPneNetworkOwnIpGrid").jqxWindow({
		      width: 750, 
		      height:465,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#pneNetworkIpCancel"), 
		      modalOpacity: 0.3
		  });
    	
    	$("#addPneNetworkOwnIpWindow").jqxWindow({
		      width: 250, 
		      height:100,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#pne-network-button-cancel"), 
		      modalOpacity: 0.3,
		      initContent: function () {
		    	  
		      }
		  });
    };
    
};

//条件查询IP配置管理
function searchPneNetworkIpConfigMgt(){
	var networkIp = new pneNetworkViewIpGrid();  
	networkIp.searchObj.ipAddressLike = $("#search-pne-network-IP-address").val();
	networkIp.searchObj.ipType = $("#search-pne-network-IP-type").val()=="全部"?"":$("#search-pne-network-IP-type").val();
	networkIp.searchObj.usageStatus = $("#search-pne-network-IP-usage-status").val()=="全部"?"":$("#search-pne-network-IP-usage-status").val();
	networkIp.searchIpConfigInfo(resourcePoolSid);
};

// 删除多个ip地址
function removePneIpInNetwork(){
	var resIpSids = "";
	  var rowindex = $('#pneNetworkIpConfigMgtdatagrid').jqxGrid('getselectedrowindexes');
	  if(rowindex.length > 0){
		  
		  Core.confirm({
				title:"提示",
				message:"确定要删除该IP吗？",
				confirmCallback:function(){
					// 存储选中的集群
			   		for(var i=0;i<rowindex.length;i++){
			   			var data = $('#pneNetworkIpConfigMgtdatagrid').jqxGrid('getrowdata', rowindex[i]);
			   			
//				   		 if("01" == data.usageStatus){
//				       			Core.alert({
//										title:"提示",
//										message:"选中IP已有被使用的，不能删除！"
//				       			});
//				       			return;
//			       		  }else{
//				       			
//			       		  }
				   		if(i == rowindex.length-1){
			   				// 为了查询出集群下面的存储，加上引号
			   				resIpSids += data.resSid;
						}else{
							resIpSids += data.resSid + ",";
						}
			       }
			   		
			   	    // 提交取消关联
	    	   		Core.AjaxRequest({
	    	   			url : ws_url + "/rest/ip/deletes",
	    				type:"POST",
	    				params:{
	    					resIpSids : resIpSids
	    				},
	    				callback : function (data) {
	    					// 刷新ip列表
	    					var networkIp = new pneNetworkViewIpGrid();  
	    					networkIp.searchObj.ipAddressLike = $("#search-pne-network-IP-address").val();
	    					networkIp.searchObj.ipType = $("#search-pne-network-IP-type").val()=="全部"?"":$("#search-pne-network-IP-type").val();
	    					networkIp.searchObj.usageStatus = $("#search-pne-network-IP-usage-status").val()=="全部"?"":$("#search-pne-network-IP-usage-status").val();
	    					networkIp.searchIpConfigInfo(resourcePoolSid);
	    					
	    					// 刷新网络列表
	    					var pne = new poolPneDatagridModel();
	    					pne.searchPoolPneInfo();
	    					
	    					// 取消掉datagrid的选中状态
	    					$('#pneNetworkIpConfigMgtdatagrid').jqxGrid('clearselection');
	    					
	    			    }
	    			});
				}
		  });
	 }
}

// 释放IP
function popPneReleseIpInNetwork(){
	  var resIpSids = "";
	  var rowindex = $('#pneNetworkIpConfigMgtdatagrid').jqxGrid('getselectedrowindexes');
	  if(rowindex.length > 0){
		  Core.confirm({
				title:"提示",
				message:"确定要释放该IP吗？",
				confirmCallback:function(){
					// 存储选中的集群
			   		for(var i=0;i<rowindex.length;i++){
			   		 var data = $('#pneNetworkIpConfigMgtdatagrid').jqxGrid('getrowdata', rowindex[i]);
			   		 if("01" == data.usageStatus){
			       			Core.alert({
									title:"提示",
									message:"选中IP存在未使用的，不能移除预占或使用！"
			       			});
			       			return;
		       		  }else{
			       			if(i == rowindex.length-1){
				   				// 为了查询出集群下面的存储，加上引号
				   				resIpSids += data.resSid;
							}else{
								resIpSids += data.resSid + ",";
							}
		       		  }
			       }
			   	   
			   	    // 提交取消关联
	    	   		Core.AjaxRequest({
	    	   			url : ws_url + "/rest/ip/release",
	    				type:"POST",
	    				params:{
	    					resIpSids : resIpSids
	    				},
	    				callback : function (data) {
	    					// 刷新ip列表
	    					var networkIp = new pneNetworkViewIpGrid();  
	    					networkIp.searchObj.ipAddressLike = $("#search-pne-network-IP-address").val();
	    					networkIp.searchObj.ipType = $("#search-pne-network-IP-type").val()=="全部"?"":$("#search-pne-network-IP-type").val();
	    					networkIp.searchObj.usageStatus = $("#search-pne-network-IP-usage-status").val()=="全部"?"":$("#search-pne-network-IP-usage-status").val();
	    					networkIp.searchIpConfigInfo(resourcePoolSid);
	    					
	    					// 刷新网络列表
	    					var pne = new poolPneDatagridModel();
	    					pne.searchPoolPneInfo();
	    					
	    					// 取消掉datagrid的选中状态
	    					$('#pneNetworkIpConfigMgtdatagrid').jqxGrid('clearselection');
	    			    }
	    			});
				}
		  });
	 }
}

//弹出新增Ip框
function popAddPneNetworkWindow(){
	$("#add-pne-network-resSid").val(resourcePoolSid);
    // 设置弹出框位置
    var windowW = $(window).width(); 
    var windowH = $(window).height(); 
    $("#addPneNetworkOwnIpWindow").jqxWindow({ position: { x: (windowW-250)/2, y: (windowH-100)/2 } });
    $("#addPneNetworkOwnIpWindow").jqxWindow('open');
}

// 提交ip信息
function submitPneNetworkIpInfo(){
	$('#addPneNetworkOwnIpForm').jqxValidator('validate');
}
  