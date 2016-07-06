var bizNetworkViewIpGridModel = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		ipAddressLike: "",
    		ipType:"",
    		usageStatus:"",
    		resNetworkSid:""
    	};
    
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
 			    $("#bizNetworkIpConfigMgtdatagrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	var ipconfig = new codeModel({width:120,autoDropDownHeight:true});
    	$("#search-biz-network-IP-address").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	ipconfig.getCommonCode("search-biz-network-IP-usage-status","RES_IP_STATUS",true);
        $("#search-biz-network-ip-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
        $("#bizNetworkIpCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
        
        // 新增Ip
        $("#add-biz-network-IP-address").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        $("#biz-network-button-save").jqxButton({ width: '50',height:"25",theme:currentTheme});
        $("#biz-network-button-cancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
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
 				resNetworkSid : resNetworkSid
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
		$('#addBizNetworkOwnIpForm').jqxValidator({
	        rules: [  
	                  { input: '#add-biz-network-IP-address', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-biz-network-IP-address', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
                    	  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                    	  if(!pattern.test(input.val())){
              	  				return false;
              	  			}else{
              	  				return true;
              	  			}
              	  		}
	                  },
	                  { input: '#add-biz-network-IP-address', message: 'IP地址重复，请重新输入', action: 'blur', rule: function (input, commit) {
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
		$('#addBizNetworkOwnIpForm').on('validationSuccess', function (event) {
			 	 var ip = Core.parseJSON($("#addBizNetworkOwnIpForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/ip/create",
	 				params :ip,
	 				callback : function (data) {
	 					$("#addBizNetworkOwnIpWindow").jqxWindow('close');
	 					// 刷新基本信息
	 					var  bizIp = new bizNetworkViewIpGridModel();
	 					bizIp.searchIpConfigInfo(resNetworkSid);
	 			    }
	 			});
	     });
	   
    };
  
    // 初始化用户datagrid的数据源
    this.initIpDatagrid = function(){
          $("#bizNetworkIpConfigMgtdatagrid").jqxGrid({
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
                  { text: 'IP地址', datafield: 'ipAddress',width:120},
                  { text: '使用状态', datafield: 'usageStatusName',width:80},
                  { text: '项目名称', datafield: 'resBizName',width:120},
                  { text: '虚拟机', datafield: 'vmName',width:150},
                  { text: '备注', datafield: 'description'}
                  
              ],
              showtoolbar: true,
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var addBtn = $("<div><a id='jqxAddBtn' onclick ='popAddBizNetworkWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus'></i>新增&nbsp;&nbsp;</a></div>");
                  var remarkBtn = $("<div><a id='jqxRemarkBtn' onclick ='popRemarkIpInNetwork()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-location'></i>占用&nbsp;&nbsp;</a></div>");
                  var releseBtn = $("<div><a id='jqxReleseBtn' onclick ='popReleseIpInNetwork()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-share'></i>释放&nbsp;&nbsp;</a></div>");
//                  var deleteBtn = $("<div><a id='jqxDeleteBtn' onclick ='removeIpInNetwork()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel'></i>删除&nbsp;&nbsp;</a></div>");

                  toolbar.append(container);
                  container.append(addBtn);
                  container.append(remarkBtn);
                  container.append(releseBtn);
//                  container.append(deleteBtn);
              }
          });
          // 控制按钮是否可用
    	  $("#bizNetworkIpConfigMgtdatagrid").on('rowselect', function (event) {
    		  var args = event.args; 
    		  var index = args.rowindex;
    		  var data = $('#bizNetworkIpConfigMgtdatagrid').jqxGrid('getrowdata', index);
    		  
    		  $("#jqxRemarkBtn").jqxButton({disabled: false });
//    		  $("#jqxDeleteBtn").jqxButton({disabled: false});
    		  $("#jqxReleseBtn").jqxButton({disabled: false});
          });
    	  
//    	  $("#jqxDeleteBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
    	  $("#jqxAddBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
    	  $("#jqxRemarkBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
    	  $("#jqxReleseBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
    };
    
    
    // 初始化弹出window
    this.initPopWindow = function(){
    	$("#viewBizNetworkOwnIpGrid").jqxWindow({
		      width: 750, 
		      height:465,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#bizNetworkIpCancel"), 
		      modalOpacity: 0.3
		  });
    	
    	$("#addBizNetworkOwnIpWindow").jqxWindow({
		      width: 250, 
		      height:100,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#biz-network-button-cancel"), 
		      modalOpacity: 0.3,
		      initContent: function () {
		    	  
		      }
		  });
    };
    
};

//条件查询IP配置管理
function searchBizNetworkIpConfigMgt(){
	var networkIp = new bizNetworkViewIpGridModel();  
	networkIp.searchObj.ipAddressLike = $("#search-biz-network-IP-address").val();
	networkIp.searchObj.usageStatus = $("#search-biz-network-IP-usage-status").val()=="全部"?"":$("#search-biz-network-IP-usage-status").val();
	networkIp.searchIpConfigInfo(resNetworkSid);
};

// 释放ip
function popReleseIpInNetwork(){
	var resIpSids = "";
	  var rowindex = $('#bizNetworkIpConfigMgtdatagrid').jqxGrid('getselectedrowindexes');
	  if(rowindex.length > 0){
		  
		  Core.confirm({
				title:"提示",
				message:"确定要释放该IP吗？",
				confirmCallback:function(){
					// 存储选中的集群
			   		for(var i=0;i<rowindex.length;i++){
			   		 var data = $('#bizNetworkIpConfigMgtdatagrid').jqxGrid('getrowdata', rowindex[i]);
			   		 if("01" == data.usageStatus){
			       			Core.alert({
									title:"提示",
									message:"选中IP存在未使用的，不能移除占用！"
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
	    					// 取消掉datagrid的选中状态
	    					$('#bizNetworkIpConfigMgtdatagrid').jqxGrid('clearselection');
	    					// 刷新ip列表
	    					var networkIp = new bizNetworkViewIpGridModel();  
	    					networkIp.searchObj.ipAddressLike = $("#search-biz-network-IP-address").val();
	    					networkIp.searchObj.ipType = $("#search-biz-network-IP-type").val()=="全部"?"":$("#search-biz-network-IP-type").val();
	    					networkIp.searchObj.usageStatus = $("#search-biz-network-IP-usage-status").val()=="全部"?"":$("#search-biz-network-IP-usage-status").val();
	    					networkIp.searchIpConfigInfo(resNetworkSid);
	    			    }
	    			});
				}
		  });
	 }
}

// 删除ips
function removeIpInNetwork(){
	  var resIpSids = "";
	  var rowindex = $('#bizNetworkIpConfigMgtdatagrid').jqxGrid('getselectedrowindexes');
	  if(rowindex.length > 0){
		  
		  Core.confirm({
				title:"提示",
				message:"确定要删除该IP吗？",
				confirmCallback:function(){
					// 存储选中的集群
			   		for(var i=0;i<rowindex.length;i++){
			   			var data = $('#bizNetworkIpConfigMgtdatagrid').jqxGrid('getrowdata', rowindex[i]);
			   			
			   		 if("01" == data.usageStatus){
			       			Core.alert({
									title:"提示",
									message:"选中IP已有被使用的，不能删除！"
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
	    	   			url : ws_url + "/rest/ip/deletes",
	    				type:"POST",
	    				params:{
	    					resIpSids : resIpSids
	    				},
	    				callback : function (data) {
	    					// 刷新ip列表
	    					var networkIp = new bizNetworkViewIpGridModel();  
	    					networkIp.searchObj.ipAddressLike = $("#search-biz-network-IP-address").val();
	    					networkIp.searchObj.ipType = $("#search-biz-network-IP-type").val()=="全部"?"":$("#search-biz-network-IP-type").val();
	    					networkIp.searchObj.usageStatus = $("#search-biz-network-IP-usage-status").val()=="全部"?"":$("#search-biz-network-IP-usage-status").val();
	    					networkIp.searchIpConfigInfo(resNetworkSid);
	    					// 取消掉datagrid的选中状态
	    					$('#bizNetworkIpConfigMgtdatagrid').jqxGrid('clearselection');
	    					
	    			    }
	    			});
				}
		  });
	 }
}

// 弹出新增Ip框
function popAddBizNetworkWindow(){
   
	$("#add-biz-network-resSid").val(resNetworkSid);
    // 设置弹出框位置
    var windowW = $(window).width(); 
    var windowH = $(window).height(); 
    $("#addBizNetworkOwnIpWindow").jqxWindow({ position: { x: (windowW-250)/2, y: (windowH-100)/2 } });
    $("#addBizNetworkOwnIpWindow").jqxWindow('open');
}

// 提交ip信息
function submitNetworkIpInfo(){
	$('#addBizNetworkOwnIpForm').jqxValidator('validate');
}
  