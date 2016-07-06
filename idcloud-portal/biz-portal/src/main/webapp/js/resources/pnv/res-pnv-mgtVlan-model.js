var pnvNetworkMgtVlanGrid = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		vlanId:"",
    		usageStatus:"",
    		resPoolSid:""
    	};
    // 用户数据
    this.searchVlanConfigInfo = function(resPoolSid){
    	 me.searchObj.resPoolSid = resPoolSid;
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/vlanRes",
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
 			    $("#pnvMgtVlanDatagrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	var ipconfig = new codeModel({width:120,autoDropDownHeight:true});
    	$("#search-pnv-vlan-address").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	ipconfig.getCommonCode("search-pnv-vlan-usage-status","USAGE_STATUS",true);
        $("#search-pnv-vlan-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
        $("#pnvMgtVlanCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
    
 // 初始化验证规则
    this.initValidator = function(){
    	
    	$('#addMgtVlanForm').jqxValidator({
            rules: [
						{ input: '#pnv-vlan-add-startValnId', message: '不能为空', action: 'keyup, blur', rule: 'required' },
						{ input: '#pnv-vlan-add-startValnId', message: '起始VLAN ID不能超过10个数字', action: 'keyup, blur', rule: 'length=1,10' },
						{ input: '#pnv-vlan-add-startValnId', message: '只能输入数字', action: 'keyup, blur', rule: 'number' },
                        
                   ]
    	});
    	
    	$('#editMgtVlanForm').jqxValidator({
            rules: [
	                  { input: '#pnv-vlan-edit-vlanId', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#pnv-vlan-edit-vlanId', message: 'VLAN_ID不能超过10个字符', action: 'keyup, blur', rule: 'length=1,10' },
                      { input: '#pnv-vlan-edit-vlanId', message: '只能输入数字', action: 'keyup, blur', rule: 'number' },
                      { input: '#pnv-vlan-edit-vlanId', message: 'VLAN_ID重复，请重新输入', action: 'blur',rule: function (input, commit) {
	                    	  	var rowindex = $('#pnvMgtVlanDatagrid').jqxGrid('getselectedrowindex');
	              				var data = $('#pnvMgtVlanDatagrid').jqxGrid('getrowdata', rowindex);
	              				if(input.val() == data.vlanId){
	              					return true;
	              				}else{
	              					 var addpnv = new addPnvModel();
	                     	         var data = addpnv.searchAllVlanInResZone();
	                     	         var isok = true;
	                     	         if(data.length > 0){
	                     	        	for(var i=0;i< data.length;i++){
	                     	        		if(data[i].vlanId == input.val()){
	                     	        			isok = false;
	                     	        			break;
	                     	        		}
	                     	        	}
	                     	         }else{
	                     	        	 return isok;
	                     	         }
	                     	         
	                     	         return isok;
	              				}
        	  			 }
	                  }
                      
                   ]
    	});
    	
    	// 新增用户表单验证成功
    	$('#addMgtVlanForm').on('validationSuccess', function (event) {
	    		 var vlan = Core.parseJSON($("#addMgtVlanForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/vlanRes/create",
	 				params :vlan,
	 				callback : function (data) {
	 					var index = $('#poolPNVDatagrid').jqxGrid('getselectedrowindex');
	 					var data = $('#poolPNVDatagrid').jqxGrid('getrowdata', index);
	 					me.searchVlanConfigInfo(data.resPoolSid);
						$("#addMgtVlanWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
	 			    	$("#addMgtVlanWindow").jqxWindow('close');
	 			    }
	 			});
    	 });
    	
    	// 编辑用户表单验证成功
    	$('#editMgtVlanForm').on('validationSuccess', function (event) {
	    		 var vlan = Core.parseJSON($("#editMgtVlanForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/vlanRes/update",
	 				params :vlan,
	 				callback : function (data) {
	 					var index = $('#poolPNVDatagrid').jqxGrid('getselectedrowindex');
	 					var data = $('#poolPNVDatagrid').jqxGrid('getrowdata', index);
	 					me.searchVlanConfigInfo(data.resPoolSid);
						$("#editMgtVlanWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
	 			    	$("#editMgtVlanWindow").jqxWindow('close');
	 			    }
	 			});
    	 });
    };
    
    // 初始化用户datagrid的数据源
    this.initIpDatagrid = function(){
          $("#pnvMgtVlanDatagrid").jqxGrid({
              width: "99.6%",
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
                  { text: 'VLAN_ID', datafield: 'vlanId'},
                  { text: '端口组', datafield: 'tag'},
                  { text: '使用状态', datafield: 'usageStatusName'}
              ],
              showtoolbar: true,
              // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var addBtn = $("<div><a id='jqxAddPnvVlanBtn' onclick='popAddPnvMgtVlanWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
                  var editBtn = $("<div><a id='jqxEditPnvVlanBtn' onclick='popEditPnvMgtVlanWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                  var removeBtn = $("<div><a id='jqxRemovePnvVlanBtn' onclick='removePnvMgtVlan()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>删除&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
                  container.append(addBtn);
                  container.append(editBtn);
                  container.append(removeBtn);
                  $("#jqxEditPnvVlanBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
                  $("#jqxRemovePnvVlanBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
    			  $("#jqxAddPnvVlanBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme});
                 }
          });
          
          $("#pnvMgtVlanDatagrid").on('rowselect', function (event) {
        	  $("#jqxEditPnvVlanBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
        	  $("#jqxRemovePnvVlanBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
          });
          
    };
    
    
    // 初始化弹出window
    this.initPopWindow = function(){
    	$("#PnvMgtVlanWindow").jqxWindow({
		      width: 700, 
		      height:345,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#pnvMgtVlanCancel"), 
		      modalOpacity: 0.3
		});
    	
    	$("#addMgtVlanWindow").jqxWindow({
		      width: 300, 
		      height:160,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#addVlanCancel"), 
		      modalOpacity: 0.3,
		      initContent:function(){
	            	// 初始化组件
	                $("#pnv-vlan-add-tag").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                
        	        $("#pnv-vlan-add-startValnId").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
        	        $("#pnv-vlan-add-endVlanId").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                
	                $("#addVlanSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                $("#addVlanCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	            }
		});
    	
    	$("#editMgtVlanWindow").jqxWindow({
		      width: 350, 
		      height:130,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#editVlanCancel"), 
		      modalOpacity: 0.3,
		      initContent:function(){
	            	// 初始化组件
	                $("#pnv-vlan-edit-vlanId").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#pnv-vlan-edit-tag").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});

	                $("#editVlanSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                $("#editVlanCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	            }
		});
    };
    
};

//条件查询IP配置管理
function searchPnvMgtVlan(){
	var vlan = new pnvNetworkMgtVlanGrid();  
	vlan.searchObj.vlanId = $("#search-pnv-vlan-address").val();
	vlan.searchObj.usageStatus = $("#search-pnv-vlan-usage-status").val()=="全部"?"":$("#search-pnv-vlan-usage-status").val();
	var index = $('#poolPNVDatagrid').jqxGrid('getselectedrowindex');
	var data = $('#poolPNVDatagrid').jqxGrid('getrowdata', index);
	vlan.searchVlanConfigInfo(data.resPoolSid);
};

// 弹出新增Vlan窗口
function popAddPnvMgtVlanWindow(){
	 // 清空上次新增的数据
	 
	 // 给所属资源池赋值
	 var index = $('#poolPNVDatagrid').jqxGrid('getselectedrowindex');
	 var data = $('#poolPNVDatagrid').jqxGrid('getrowdata', index);
	 $("#pnv-vlan-resPoolSid").val(data.resPoolSid);
	 var windowW = $(window).width(); 
     var windowH = $(window).height(); 
     $("#addMgtVlanWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-160)/2 } });
     $("#addMgtVlanWindow").jqxWindow('open');
}

// 提交新增Vlan表单
function submitAddMgtVlan(){
	$('#addMgtVlanForm').jqxValidator('validate');
}

// 弹出编辑Vlan窗口
function popEditPnvMgtVlanWindow(){
	var rowindex = $('#pnvMgtVlanDatagrid').jqxGrid('getselectedrowindex');
  	// 判断审核按钮是否可用
  	var ok =  $("#jqxEditBtn").jqxButton("disabled");
	if(rowindex >= 0 && !ok){
		var data = $('#pnvMgtVlanDatagrid').jqxGrid('getrowdata', rowindex);
		 $("#pnv-vlan-resSid").val(data.resSid);
		 $("#pnv-vlan-edit-vlanId").val(data.vlanId);
		 $("#pnv-vlan-edit-tag").val(data.tag);
		 var windowW = $(window).width(); 
	     var windowH = $(window).height(); 
	     $("#editMgtVlanWindow").jqxWindow({ position: { x: (windowW-250)/2, y: (windowH-130)/2 } });
	     $("#editMgtVlanWindow").jqxWindow('open');
	}
}

//提交编辑Vlan表单
function submitEditMgtVlan(){
	$('#editMgtVlanForm').jqxValidator('validate');
}

// 删除选中的vlan
function removePnvMgtVlan(){
	var rowindex = $('#pnvMgtVlanDatagrid').jqxGrid('getselectedrowindexes');
  	// 判断审核按钮是否可用
  	var ok =  $("#jqxRemovePnvVlanBtn").jqxButton("disabled");
	if(rowindex.length >= 0 && !ok){
		var resSids = "";
		for(var i=0;i<rowindex.length;i++){
  			var data = $('#pnvMgtVlanDatagrid').jqxGrid('getrowdata', rowindex[i]);
//  			  if("01" == data.usageStatus){
//	  	  			Core.alert({
//	  					title:"提示",
//	  					message:"选中的VLAN有已使用的，不能删除！"
//	  	  			});
//	  	  			return false;
//  	  		  }else{
//	  	  			
//  	  		  }
  				if(i == rowindex.length-1){
	  				resSids+= data.resSid;
				}else{
					resSids+= data.resSid+",";
				}
  			
  		}
		
		Core.confirm({
			title:"提示",
			message:"确定要删除选中的VLAN吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
	 				url : ws_url + "/rest/vlanRes/delete/"+resSids,
	 				type:"get",
	 				callback : function (data) {
	 					var index = $('#poolPNVDatagrid').jqxGrid('getselectedrowindex');
	 					var data = $('#poolPNVDatagrid').jqxGrid('getrowdata', index);
	 					var pnv = new pnvNetworkMgtVlanGrid();
	 					pnv.searchVlanConfigInfo(data.resPoolSid);
	 				// 取消掉datagrid的选中状态
    					$('#pnvMgtVlanDatagrid').jqxGrid('clearselection');
	 			    },
	 			    failure:function(data){
	 			    	
	 			    }
	 			});
			}
	   });
		
	}
}
  