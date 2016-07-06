var ipConfigMgtModel = function(){
	
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		ipAddress: "", 
    		ipCategory: "", 
    		ipType:"",
    		manageStatus:"",
    		usageStatus:"",
    		isResPoolSearch:resTopologyType,
    		resTopologySid:resTopologySid
    	};
    // 用户数据
    this.searchIpConfigInfo = function(){
    	
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/ips",
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
 			    $("#ipConfigMgtdatagrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	var ipconfig = new codeModel({width:120,autoDropDownHeight:true});
    	$("#search-IP-address").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	ipconfig.getCommonCode("search-IP-category","IP_CATEGORY",true);
    	ipconfig.getCommonCode("search-IP-type","IP_TYPE",true);
    	ipconfig.getCommonCode("search-IP-mgt-status","MANAGEMENT_STATUS",true);
    	ipconfig.getCommonCode("search-IP-usage-status","USAGE_STATUS",true);
        $("#search-ip-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    	
    };
    // 初始化验证规则
    this.initValidator = function(){
//    	$('#addIpForm').jqxValidator({
//	        rules: [  
//	                  { input: '#add-ipAddressStart-ip1', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
//	                  { input: '#add-ipAddressStart-ip1', message: 'ip起始地址不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
//	                  { input: '#add-ipAddressEnd-ip1', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
//	                  { input: '#add-ipAddressEnd-ip1', message: 'ip终止地址不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
//		               ]
//			});
//		$('#editIpForm').jqxValidator({
//	        rules: [  
//	                  { input: '#edit-ipAddressStart-ip1', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
//	                  { input: '#edit-ipAddressStart-ip1', message: 'ip起始地址不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
//	                  { input: '#edit-ipAddressEnd-ip1', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
//	                  { input: '#edit-ipAddressEnd-ip1', message: 'ip终止地址不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
//		               ]
//			});
		
//			// 新增ip池验证成功
//			$('#addIpForm').on('validationSuccess', function (event) {
//				 var pool = Core.parseJSON($("#addIpForm").serializeJson());
//				 var param = {};
//				 param.resTopologySid = resTopologySid;
//				 param.isResPoolSearch = resTopologyType;
//	    		 var newObj = $.extend(pool,param);
//		    		 Core.AjaxRequest({
//		 				url : ws_url + "/rest/ips/create",
//		 				params :newObj,
//		 				callback : function (data) {
//		 	            	me.initAddContent();
//		 					// 刷新基本信息
//		 					me.searchIpConfigInfo();
//		 				    // 初始化按钮
//		 					me.initButton();
//		 					$("#addIpWindow").jqxWindow('close');
//		 			    },
//		 			    failure:function(data){
//							$("#addIpWindow").jqxWindow('close');
//		 			    }
//		 			});
//		     });
			

//			// 更新ip池验证成功
//			$('#editIpForm').on('validationSuccess', function (event) {
//				 var pool = Core.parseJSON($("#editIpForm").serializeJson());
//				 var param = {};
//				 param.resTopologySid = resTopologySid;
//				 param.isResPoolSearch = resTopologyType;
//				 param.resSid = $("#edit-resSid-ip1").val();
//	    		 var newObj = $.extend(pool,param);
//		    		 Core.AjaxRequest({
//		 				url : ws_url + "/rest/ips/update",
//		 				params :newObj,
//		 				callback : function (data) {
//		 	            	me.initEditContent();
//		 					// 刷新基本信息
//		 					me.searchIpConfigInfo();
//		 				    // 初始化按钮
//		 					me.initButton();
//							$("#editIpWindow").jqxWindow('close');
//		 			    },
//		 			    failure:function(data){
//							$("#editIpWindow").jqxWindow('close');
//		 			    }
//		 			});
//		     });
	    
    };
    // 初始化用户datagrid的数据源
    this.initIpDatagrid = function(){
          $("#ipConfigMgtdatagrid").jqxGrid({
              width: "100%",
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
                  { text: 'IP类别', datafield: 'ipCategoryName'},
                  { text: 'IP类型', datafield: 'ipTypeName'},
                  { text: '对应公网地址', datafield: 'mapPublicIp'},
                  { text: 'VLAN ID', datafield: 'vlanId'},
                  { text: '备注', datafield: 'comments'},
                  { text: '管理状态', datafield: 'manageStatusName'},
                  { text: '使用状态', datafield: 'usageStatusName'},
                  { text: '操作', datafield: 'Edit', columntype: 'button', cellsrenderer: function () {
                      return "编辑";
                   }, buttonclick: function (row) {
                      // open the popup window when the user clicks a button.
                      editrow = row;
              		  var data = $('#ipConfigMgtdatagrid').jqxGrid('getrowdata', editrow);
            		  var ip = new editIpModel();
            		  // 为编辑赋值
            		  $("#edit-resSid-ip1").val(data.resSid);
            		  // 编辑框赋值
            		  ip.setEditIpForm(data);
	              	  // 设置弹出框位置
	              	  var windowW = $(window).width(); 
	                  var windowH = $(window).height(); 
	                  $("#editIpWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-250)/2 } });
	                  $("#editIpWindow").jqxWindow('open');                  }
                  }
              ],
              showtoolbar: true,
              // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var addIp = $("<div><a id='addIp' onclick='addIpInfoWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
                  //var editIP = $("<div><a id='editIP' style='margin-left:-1px' onclick='editIpInfoWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                  var removeIp = $("<div><a id='removeIp' onclick='removeIpInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
                  container.append(addIp);
                  //container.append(editIP);
                  container.append(removeIp);
              }
          });
          
       // 控制按钮是否可用
    	  $("#ipConfigMgtdatagrid").on('rowselect', function (event) {
    		  var args = event.args; 
    		  var index = args.rowindex;
    		  var data = $('#ipConfigMgtdatagrid').jqxGrid('getrowdata', index);
   			  //$("#editIP").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
   			  $("#removeIp").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
          });
    	  // 初始化按钮
    	  me.initButton();
          
    };
    
//    this.setEditIpForm = function(data){
//   	 	$("#edit-ipAddressStart-ip1").val(data.ipAddress);
//   	 	$("#edit-ipAddressEnd-ip1").val(data.ipAddress);
//        $("#edit-subnetMask-ip1").val(data.subnetMask);
//        $("#edit-dns-ip1").val(data.dns);
//        $("#edit-gateway-ip1").val(data.gateway);
//        $("#edit-comments-ip1").val(data.comments);
//        $("#edit-ipType-ip-ip1").val(data.ipType);
//        $("#edit-ipCategory-ip1").val(data.ipCategory);
//        $("#edit-vlanId-ip1").val(data.vlanId);
//   };
    
    // 判断操作按钮，初始化按钮
    this.initButton = function(){
    	 // 判断datagrid是否有被选中的
    	var selectedrowindex = $('#ipConfigMgtdatagrid').jqxGrid('selectedrowindex'); 
    	// 大于-1表示有被选中的
    	if(selectedrowindex > -1){
    		$("#addIp").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
   			$("#removeIp").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    	}else{
    		$("#addIp").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
  			//$("#editIP").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
  			$("#removeIp").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
    	}
    	
    };
    
    // 初始化弹出window
    this.initPopWindow = function(){
//    	$("#addIpWindow").jqxWindow({
//            width: 600, 
//            height:250,
//            theme:currentTheme,
//            resizable: false,  
//            isModal: true, 
//            autoOpen: false, 
//            cancelButton: $("#addIpCancel"), 
//            modalOpacity: 0.3,
//            initContent:function(){
//            	// 初始化组件
//                $("#add-ipAddressStart-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
//                $("#add-ipAddressEnd-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
//                $("#add-subnetMask-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
//                $("#add-dns-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
//                $("#add-gateway-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
//                $("#add-comments-ip1").jqxInput({placeHolder: "", height: 46, width:500, minLength: 1,theme:currentTheme});
//
//                $("#addIpSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
//                $("#addIpCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
//                
//                // 初始化下拉列表框 
//        		 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
//        		 codesearch.getCommonCode("add-ipType-ip1","IP_TYPE",false);
//        		 codesearch.getCommonCode("add-ipCategory-ip1","IP_CATEGORY",false);
//        		 codesearch.getCustomCode("add-vlanId-ip1","/vlans","vlanName","vlanId",false);          }
//        });
    	
//    	$("#editIpWindow").jqxWindow({
//            width: 600, 
//            height:250,
//            theme:currentTheme,
//            resizable: false,  
//            isModal: true, 
//            autoOpen: false, 
//            cancelButton: $("#editIpCancel"), 
//            modalOpacity: 0.3,
//            initContent:function(){
//            	// 初始化组件
//                $("#edit-ipAddressStart-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,disabled: true, theme:currentTheme});
//                $("#edit-ipAddressEnd-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,disabled: true, theme:currentTheme});
//                $("#edit-subnetMask-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
//                $("#edit-dns-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
//                $("#edit-gateway-ip1").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
//                $("#edit-comments-ip1").jqxInput({placeHolder: "", height: 46, width:500, minLength: 1,theme:currentTheme});
//
//                $("#editIpSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
//                $("#editIpCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
//                
//                // 初始化下拉列表框 
//        		 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
//        		 codesearch.getCommonCode("edit-ipType-ip1","IP_TYPE",false);
//        		 codesearch.getCommonCode("edit-ipCategory-ip1","IP_CATEGORY",false);
//        		 codesearch.getCustomCode("edit-vlanId-ip1","/vlans","vlanName","vlanId",false);          }
//        });

    };
    
//    // 判断操作按钮，初始化按钮
//    this.initAddContent = function(){
//    	// 初始化组件
//        $("#add-ipAddressStart-ip1").val("");
//        $("#add-ipAddressEnd-ip1").val("");
//        $("#add-subnetMask-ip1").val("");
//        $("#add-dns-ip1").val("");
//        $("#add-gateway-ip1").val("");
//        $("#add-comments-ip1").val("");
//        $("#add-ipType-ip1").val("");
//        $("#add-ipCategory-ip1").val("");
//        $("#add-vlanId-ip1").val("");
//    };
    
//    // 判断操作按钮，初始化按钮
//    this.initEditContent = function(){
//    	// 初始化组件
//        $("#edit-ipAddressStart-ip1").val("");
//        $("#edit-ipAddressEnd-ip1").val("");
//        $("#edit-subnetMask-ip1").val("");
//        $("#edit-dns-ip1").val("");
//        $("#edit-gateway-ip1").val("");
//        $("#edit-comments-ip1").val("");
//        $("#edit-ipType-ip1").val("");
//        $("#edit-ipCategory-ip1").val("");
//        $("#edit-vlanId-ip1").val("");
//    };
    
    this.getDataGridData = function(){
   	 var ipData="";
   	 Core.AjaxRequest({
	 			url : ws_url + "/rest/ips",
	 			type:'post',
	 			params:me.searchObj,
	 			async:false,
	 			callback : function (data) {
	 				ipData = data;
	 			}
	     });
   	 return ipData;
   };
    
    // ip资源统计
    this.IpResourcesStatistics = function(){
    	var ipData =  me.getDataGridData();
		
		var data = new Object();
		data.ipCount = ipData.length;
		data.attr = new Array();
		
		var value = [0,0];
		var name =["已使用","未使用"];
		for(var i=0;i < ipData.length;i++){
			
			if("01" == ipData[i].usageStatus){
				// 已使用
				value[0] += 1; 
			} else {
				// 未使用
				value[1] += 1; 
			}
		}
		for(var i=0; i<2;i++){
			var obj = new Object();
			obj.name = name[i];
			obj.value = value[i];
			data.attr.push(obj);
		}
		return data;
    };
};

//条件查询IP配置管理
function searchIpConfigMgt(){
	var ip = new ipConfigMgtModel();  
	ip.searchObj.ipAddress = $("#search-IP-address").val();
	ip.searchObj.ipCategory = $("#search-IP-category").val()=="全部"?"":$("#search-IP-category").val();
	ip.searchObj.ipType = $("#search-IP-type").val()=="全部"?"":$("#search-IP-type").val();
	ip.searchObj.manageStatus = $("#search-IP-mgt-status").val()=="全部"?"":$("#search-IP-mgt-status").val();
	ip.searchObj.usageStatus = $("#search-IP-usage-status").val()=="全部"?"":$("#search-IP-usage-status").val();
	ip.searchObj.resTopologySid = resTopologySid;
	ip.searchIpConfigInfo();
};

//// 弹出添加主机到池的window框
//function addIpInfoWindow(){
//	var windowW = $(window).width(); 
//  	var windowH = $(window).height();
//  	$("#addIpWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-250)/2 } });
//  	$("#addIpWindow").jqxWindow('open');
//}
//
//// 提交将选择主机加入池
//function addIpSubmit(){
//	$('#addIpForm').jqxValidator('validate');
//}


////弹出编辑ip框
//function editIpInfoWindow(){
//	var rowindex = $('#ipConfigMgtdatagrid').jqxGrid('getselectedrowindex');
//	if(rowindex >= 0){
//		var data = $('#ipConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
//		var respool = new ipConfigMgtModel();
//		// 为编辑赋值
//		$("#edit-resSid-ip1").val(data.resSid);
//		// 编辑框赋值
//		respool.setEditIpForm(data);
//		// 设置弹出框位置
//		var windowW = $(window).width(); 
//    	var windowH = $(window).height(); 
//    	$("#editIpWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-250)/2 } });
//    	$("#editIpWindow").jqxWindow('open');
//	}
//}
//
//// 提交将选择主机加入池
//function editIpSubmit(){
//	$('#editIpForm').jqxValidator('validate');
//}

//删除ip
function removeIpInfo1(){
	var rowindex = $('#ipConfigMgtdatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		var data = $('#ipConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
		Core.confirm({
			title:"提示",
			message:"确定要删除该IP吗?",
			yes:"确定",
			confirmCallback:function(){
				Core.AjaxRequest({
	 				url : ws_url + "/rest/ips/delete/"+data.resSid+"",
	 				type:"get",
	 				callback : function (data) {
	 					var resIp = new ipConfigMgtModel();
	 					resIp.searchIpConfigInfo();
	 				    // 初始化按钮
	 					resIp.initButton();
	 			    },
	 			    failure:function(data){
	 			    	
	 			    }
	 			});
			}
	    });
	}
}

//删除ip
function removeIpInfo(){
// 得到datagrid的选择项
	var rowArr = $('#ipConfigMgtdatagrid').jqxGrid('getselectedrowindexes');
	//sunyu add for #158
	var del_msg = "确定要删除所选ip吗?";
	var totalCount = $('#ipConfigMgtdatagrid').jqxGrid('getrows').length;
	if (totalCount == rowArr.length) {
		del_msg = "确定要删除全部ip吗?";
	}
	//end
	var isUsed = true;
	var ipSids ="";
	if(rowArr.length > 0){
		// 判断是否可以移除
		for(var i = 0; i < rowArr.length; i++){
			// 查询出该行数据
			var data = $('#ipConfigMgtdatagrid').jqxGrid('getrowdata', rowArr[i]);
			if(i == rowArr.length - 1){
				ipSids += data.resSid;
			}else{
				ipSids+= data.resSid+",";
			}
			
			if(data.usageStatus == "01"){ 
				isUsed = false;
				// 清除datagrid的选择项
				$('#ipConfigMgtdatagrid').jqxGrid('clearselection');
				break;
			}
		}
		
		// 判断是否有已使用的存储
		if(isUsed){
			// 移除所选择的存储
			Core.confirm({
				title:"提示",
				message:del_msg,
				yes:"确定",
				confirmCallback:function(){
					Core.AjaxRequest({
		 				url : ws_url + "/rest/ips/delete/"+ipSids+"",
		 				type:"get",
		 				callback : function (data) {

//		 					for(var i=0; i< rowArr.length;i++){
//		 						alert(rowArr.length);
//
//		 						$("#ipConfigMgtdatagrid").jqxGrid('deleterow', rowArr[i]);
//		 					}
		 					// 清除datagrid的选择项
							$('#ipConfigMgtdatagrid').jqxGrid('clearselection');
							
		 					var resIp = new ipConfigMgtModel();
		 					resIp.searchIpConfigInfo();
		 				    // 初始化按钮
		 					resIp.initButton();
		 			    },
		 			    failure:function(data){
		 			    	
		 			    }
		 			});
				}
		    });
		}else{
			Core.alert({
				message:"该ip已使用，无法删除！",
				type:"info"
			});
		}
	}else{
		Core.alert({
			message:"请选择ip数据！",
			type:"info"
		});
	}
}

  