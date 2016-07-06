var pniNetworkViewIpGrid = function(){
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
 			    $("#networkIpConfigMgtdatagrid").jqxGrid({source: dataAdapter});
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
		$('#addPniNetworkOwnIpForm').jqxValidator({
	        rules: [  
	                  { input: '#add-pni-network-IP-address', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-pni-network-IP-address', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
                    	  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                    	  if(!pattern.test(input.val())){
              	  				return false;
              	  			}else{
              	  				return true;
              	  			}
              	  		}
	                  },
	                  { input: '#add-pni-network-IP-address', message: 'IP地址重复，请重新输入', action: 'blur', rule: function (input, commit) {
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
		$('#addPniNetworkOwnIpForm').on('validationSuccess', function (event) {
			 	 var ip = Core.parseJSON($("#addPniNetworkOwnIpForm").serializeJson());
			 	 
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/ip/create",
	 				params :ip,
	 				callback : function (data) {
	 					$("#addPniNetworkOwnIpWindow").jqxWindow('close');
	 					// 刷新基本信息
	 					var  pniIp = new pniNetworkViewIpGrid();
	 					pniIp.searchIpConfigInfo(resourcePoolSid);
	 			    }
	 			});
	     });
	   
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	var ipconfig = new codeModel({width:120,autoDropDownHeight:true});
    	$("#search-network-IP-address").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	ipconfig.getCommonCode("search-network-IP-usage-status","RES_IP_STATUS",true);
        $("#search-network-ip-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
        $("#networkIpCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
        
        // 新增Ip
        $("#add-pni-network-IP-address").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        $("#pni-network-button-save").jqxButton({ width: '50',height:"25",theme:currentTheme});
        $("#pni-network-button-cancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
    };
  
    // 初始化用户datagrid的数据源
    this.initIpDatagrid = function(){
          $("#networkIpConfigMgtdatagrid").jqxGrid({
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
                  { text: '所属对象', datafield: 'objName', cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
					  return "<div style='padding-top:10px;'><a class='datagrid-link' onclick='goEachDetailInfo("+row+")' href='#'>&nbsp;"+value+"</a></div>";
				  }},
                  { text: '备注', datafield: 'description'}
              ],
              showtoolbar: true,
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var addBtn = $("<div><a id='jqxAddBtn' onclick ='popAddPniNetworkWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus'></i>新增&nbsp;&nbsp;</a></div>");
                  var remarkBtn = $("<div><a id='jqxRemarkBtn' onclick ='popPniRemarkIpInNetwork()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-location'></i>占用&nbsp;&nbsp;</a></div>");
                  var releseBtn = $("<div><a id='jqxReleseBtn' onclick ='popReleseIpInNetwork()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-share'></i>释放&nbsp;&nbsp;</a></div>");
                  var deleteBtn = $("<div><a id='jqxDeleteBtn' onclick ='removeIpInNetwork()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel'></i>删除&nbsp;&nbsp;</a></div>");

                  toolbar.append(container);
                  container.append(addBtn);
                  container.append(remarkBtn);
                  container.append(releseBtn);
                  container.append(deleteBtn);
              }
          });
          // 控制按钮是否可用
    	  $("#networkIpConfigMgtdatagrid").on('rowselect', function (event) {
    		  var args = event.args; 
    		  var index = args.rowindex;
    		  var data = $('#networkIpConfigMgtdatagrid').jqxGrid('getrowdata', index);
    		  $("#jqxDeleteBtn").jqxButton({disabled: false});
    		  $("#jqxRemarkBtn").jqxButton({disabled: false});
    		  $("#jqxReleseBtn").jqxButton({disabled: false});
    		  
          });
    	  
    	  $("#jqxAddBtn").jqxButton({width: '60',theme:currentTheme,height: '18px' });
    	  $("#jqxDeleteBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
    	  $("#jqxRemarkBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
    	  $("#jqxReleseBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
    };
    
    
    // 初始化弹出window
    this.initPopWindow = function(){
    	$("#viewNetworkOwnIpGrid").jqxWindow({
		      width: 750, 
		      height:465,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#networkIpCancel"), 
		      modalOpacity: 0.3
		  });
    	
    	$("#addPniNetworkOwnIpWindow").jqxWindow({
		      width: 250, 
		      height:100,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#pni-network-button-cancel"), 
		      modalOpacity: 0.3,
		      initContent: function () {
		    	  
		      }
		  });
    };
    
};

//条件查询IP配置管理
function searchNetworkIpConfigMgt(){
	var networkIp = new pniNetworkViewIpGrid();  
	networkIp.searchObj.ipAddressLike = $("#search-network-IP-address").val();
	networkIp.searchObj.ipType = $("#search-network-IP-type").val()=="全部"?"":$("#search-network-IP-type").val();
	networkIp.searchObj.usageStatus = $("#search-network-IP-usage-status").val()=="全部"?"":$("#search-network-IP-usage-status").val();
	networkIp.searchIpConfigInfo(resourcePoolSid);
};

// 删除ips
function removeIpInNetwork(){
	  var resIpSids = "";
	  var rowindex = $('#networkIpConfigMgtdatagrid').jqxGrid('getselectedrowindexes');
	  if(rowindex.length > 0){
		  
		  Core.confirm({
				title:"提示",
				message:"确定要删除该IP吗？",
				confirmCallback:function(){
					// 存储选中的集群
			   		for(var i=0;i<rowindex.length;i++){
			   			var data = $('#networkIpConfigMgtdatagrid').jqxGrid('getrowdata', rowindex[i]);
			   			
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
	    					var networkIp = new pniNetworkViewIpGrid();  
	    					networkIp.searchObj.ipAddressLike = $("#search-network-IP-address").val();
	    					networkIp.searchObj.ipType = $("#search-network-IP-type").val()=="全部"?"":$("#search-network-IP-type").val();
	    					networkIp.searchObj.usageStatus = $("#search-network-IP-usage-status").val()=="全部"?"":$("#search-network-IP-usage-status").val();
	    					networkIp.searchIpConfigInfo(resourcePoolSid);
	    					// 刷新网络列表
	    					// 刷新网络列表
	    					var pni = new poolPniDatagridModel();
	    					pni.searchPoolPniInfo();
	    					// 取消掉datagrid的选中状态
	    					$('#networkIpConfigMgtdatagrid').jqxGrid('clearselection');
	    					
	    			    }
	    			});
				}
		  });
	 }
}

// 释放IP
function popReleseIpInNetwork(){
	  var resIpSids = "";
	  var rowindex = $('#networkIpConfigMgtdatagrid').jqxGrid('getselectedrowindexes');
	  if(rowindex.length > 0){
		  Core.confirm({
				title:"提示",
				message:"确定要释放该IP吗？",
				confirmCallback:function(){
					// 存储选中的集群
			   		for(var i=0;i<rowindex.length;i++){
			   		 var data = $('#networkIpConfigMgtdatagrid').jqxGrid('getrowdata', rowindex[i]);
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
	    					var networkIp = new pniNetworkViewIpGrid();  
	    					networkIp.searchObj.ipAddressLike = $("#search-network-IP-address").val();
	    					networkIp.searchObj.ipType = $("#search-network-IP-type").val()=="全部"?"":$("#search-network-IP-type").val();
	    					networkIp.searchObj.usageStatus = $("#search-network-IP-usage-status").val()=="全部"?"":$("#search-network-IP-usage-status").val();
	    					networkIp.searchIpConfigInfo(resourcePoolSid);
	    					// 刷新网络列表
	    					var pni = new poolPniDatagridModel();
	    					pni.searchPoolPniInfo();
	    					// 取消掉datagrid的选中状态
	    					$('#networkIpConfigMgtdatagrid').jqxGrid('clearselection');
	    			    }
	    			});
				}
		  });
	 }
}

//弹出新增Ip框
function popAddPniNetworkWindow(){
	$("#add-pni-network-resSid").val(resourcePoolSid);
    // 设置弹出框位置
    var windowW = $(window).width(); 
    var windowH = $(window).height(); 
    $("#addPniNetworkOwnIpWindow").jqxWindow({ position: { x: (windowW-250)/2, y: (windowH-100)/2 } });
    $("#addPniNetworkOwnIpWindow").jqxWindow('open');
}

// 提交ip信息
function submitPniNetworkIpInfo(){
	$('#addPniNetworkOwnIpForm').jqxValidator('validate');
}


// 跳转到各个详细页面
function goEachDetailInfo(row){
	var data = $('#networkIpConfigMgtdatagrid').jqxGrid('getrowdata', row);
	
	if(data.objType == 'RES-VM'){
		// 跳转到虚拟机页面
		window.parent.addParentTabs(data.objSid,data.objMonitorNodeId,data.objName);
	}else if(data.objType == 'RES-HOST'){
		// 跳转到主机页面
		window.parent.addHostParentTabs(data.objSid,data.objMonitorNodeId,data.objName);  
	}
//	else if(data.objType == 'SW'){
//		// 调用设备详情的方法
//		var sss = new physicalMonitorDetailModel();
//		sss.popPhysicalDeta`ilInfoWindow(data.objSid,data.objType);
//	}
	else{
		// 调用设备详情的方法
		var sss = new physicalMonitorDetailModel();
		sss.initPopWindow();
		sss.popPhysicalDetailInfoWindow(data.objSid,data.objType);
	}
}
  