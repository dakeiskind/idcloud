// 主机发现、部署
var findAndDeploymentHostModel = function () {
	
	 // 给加入平台页面赋值
    this.setplatformhostForm = function(data){
    	$("#platform-hostName").val(data.ipaddress);
    	$("#platform-fullName").val(data.hostName);
    	$("#platform-hostType").val(data.productname);
  
    	$("#platform-uuid").val(data.uuid);
    	$("#platform-vendor").val(data.manufacturer);
    	
    	
    	$("#platform-hostIp").val(data.ipaddress);
    
    	$("#platform-cpuNumber").val(data.physicalprocessorcount);
    	$("#platform-cpuCores").val(data.processorcount);
    	$("#platform-cpuGhz").val(data.processorcountArray.processor0.substring(data.processorcountArray.processor0.length-7,data.processorcountArray.processor0.length-3));
    	$("#platform-cpuType").val(data.processorcountArray.processor0.substring(0,21));
    	
//        	$("#platform-virtualPlatformType").html(data.virtualPlatformType);
//        	$("#platform-hostOsType").html(data.hostOsType);

    };
    
    // 初始化验证规则   
    this.initValidator = function(){
    	// 加入平台
    	$('#addToPlatformForm').jqxValidator({
            rules: [  
                      { input: '#platform-hostName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#platform-hostName', message: '主机名称不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
                      
                      { input: '#platform-hostIp', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#platform-hostIp', message: '主机IP不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
                      
                      { input: '#platform-memorySize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#platform-memorySize', message: '内存大小不能超过10个字符', action: 'keyup, blur', rule: 'length=1,10' },
                      
                      { input: '#platform-cpuNumber', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#platform-cpuNumber', message: 'CPU个数不能超过4个字符', action: 'keyup, blur', rule: 'length=1,4' },
                      
                      { input: '#platform-cpuCores', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                      { input: '#platform-cpuCores', message: 'CPU核数不能超过4个字符', action: 'keyup, blur', rule: 'length=1,4' },
                   ]
    	});

    	// 加入平台
    	$('#addToPlatformForm').on('validationSuccess', function (event) {
	    		 var host = Core.parseJSON($("#addToPlatformForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/hosts/create",
	 				params :host,
	 				callback : function (data) {
	 						// 刷新datagrid
	 						refreshDiscoveryHost();
	 						// 刷新Tree
	 						setHostTreeValue();
	 						// 关闭window
							$("#addToPlatformWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
	 			    	$("#addToPlatformWindow").jqxWindow('close');
	 			    }
	 			});
    	 });
    };
	
	  // 初始化用户datagrid的数据源
    this.initFindHostDatagrid = function(){
      	// 初始化发现主机部署
		 $("#hostDiscoveryDeploymentDatagrid").jqxGrid({
	              width: "100%",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
                  pagesize: 5, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '节点', datafield: 'macAddress'},
	                  { text: '厂商', datafield: 'manufacturer'},
	                  { text: '产品名称', datafield: 'productname'},
	                  { text: '序列号', datafield: 'serialnumber'},
	                  { text: 'CPU个数', datafield: 'physicalprocessorcount',width:70},
	                  { text: 'CPU总核数', datafield: 'processorcount',width:70},
	                  { text: '操作系统', datafield: 'osname'},
	                  { text: '状态', datafield: 'statusName'}
	              ] ,
	              showtoolbar: true,
              // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var detailDiscoveryHost = $("<div><a id='detailDiscoveryHost'  onclick='discoveryHostDetailInfo()'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>详情&nbsp;&nbsp;</a></div>");
                  var deploymentSystem = $("<div><a id='deploymentSystem' onclick='popDeploymentSystemWindow()' style='margin-left:-1px' >&nbsp;&nbsp;<i class='icons-blue icon-retweet-1'></i>部署系统&nbsp;&nbsp;</a></div>");
                  var joinPlatform = $("<div><a id='joinPlatform'  onclick='javascript:popAddToPlatformWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>加入平台&nbsp;&nbsp;</a></div>");
                  var deleteDiscoveryHost = $("<div><a id='deleteDiscoveryHost' onclick='removeDiscoveryHost()' style='margin-left:-1px' >&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                  var discoveryHostRefresh = $("<div><a id='discoveryHostRefresh' onclick='refreshDiscoveryHost()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
                  container.append(detailDiscoveryHost);
                  container.append(deploymentSystem);
                  container.append(joinPlatform);
                  container.append(deleteDiscoveryHost);
                  container.append(discoveryHostRefresh);
              }
	          });
		 
		 $("#hostDiscoveryDeploymentDatagrid").on('rowselect', function (event) {
    		  var args = event.args; 
    		  var index = args.rowindex;
    		  var data = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getrowdata', index);
    		  
    		  if(data.status == "04"){
    			  $("#detailDiscoveryHost").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
		  		  $("#deploymentSystem").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
		  		  $("#joinPlatform").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false});
		  		  $("#deleteDiscoveryHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    		  }else if(data.status == "05"){
    			  $("#detailDiscoveryHost").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
		  		  $("#deploymentSystem").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
		  		  $("#joinPlatform").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true});
		  		  $("#deleteDiscoveryHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
    		  }else if(data.status == "02"){
    			  $("#detailDiscoveryHost").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
		  		  $("#deploymentSystem").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
		  		  $("#joinPlatform").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled:true });
		  		  $("#deleteDiscoveryHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    		  }else if(data.status == "03"){
    			  $("#detailDiscoveryHost").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
		  		  $("#deploymentSystem").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
		  		  $("#joinPlatform").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true});
		  		  $("#deleteDiscoveryHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
    		  }
    		 
          });
    	  $("#detailDiscoveryHost").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
  		  $("#deploymentSystem").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
  		  $("#joinPlatform").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
  		  $("#deleteDiscoveryHost").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
  		  $("#discoveryHostRefresh").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
    };
	
	
	 // 初始化弹出window
    this.initPopWindow = function(){
    	// 发现主机部署window
    	$("#hostDiscoveryDeploymentWindow").jqxWindow({
    		width:800, 
            height:280,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false,  
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化组件
            }
        });
    	
    	// 发现主机部署的主机详情
    	$("#discoveryDeploymentDetailWindow").jqxWindow({
    		width:750, 
            height:300,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false,  
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化组件
            }
        });
    	
    	// 给发现主机部署系统
    	$("#DeploymentSystemWindow").jqxWindow({
    		width:300, 
            height:100,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false,  
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化组件
            	$("#deploymentSystemSave").jqxButton({ width: '50',theme:currentTheme,height: '25px',});
            	var hostconfig = new codeModel({width:140,autoDropDownHeight:true});
    	    	hostconfig.getAttribute1CommonCode("hostOsType","HOST_OS_TYPE");
            }
        });
    	
    	// 加入平台
    	$("#addToPlatformWindow").jqxWindow({
            width: 800, 
            height:338,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#platformhostCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化新增用户页面组件
            	$("#platform-hostName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#platform-fullName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#platform-hostType").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#platform-clusterName").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#platform-rackNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#platform-cageEnclosure").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#platform-uuid").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#platform-vendor").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#platform-dataCenter").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	
            	$("#platform-hostIp").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#platform-memorySize").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#platform-cpuNumber").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#platform-cpuCores").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#platform-cpuGhz").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	$("#platform-cpuType").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
            	
            	var hostconfig = new codeModel({width:120,autoDropDownHeight:true});
    	    	hostconfig.getCommonCode("platform-virtualPlatformType","VIRTUAL_PLATFORM_TYPE");
    	    	hostconfig.getCommonCode("platform-hostOsType","OS_TYPE");
    	    	
    	    	$("#platformhostSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
    	    	$("#platformhostCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            }
        });
    };
};

// 刷新发现主机列表
function refreshDiscoveryHost(){
	Core.AjaxRequest({
			url : ws_url + "/rest/hosts/getDiscoveryHost",
			type:'post',
			callback : function (data) {
				var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
			    };
			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
			    $("#hostDiscoveryDeploymentDatagrid").jqxGrid({source: dataAdapter});
			}
		 });
}

// 发现主机的详情
function discoveryHostDetailInfo(){
	// 先查询出某个主机详细信息
	var index = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getselectedrowindex');
	if(index >=0){
		var data = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getrowdata', index);
		// 给window中的主机属性赋值
		$("#hostName").html(data.hostName);
		$("#macAddress").html(data.macAddress);
		$("#nodeHostName").html(data.nodeName);
		$("#osname").html(data.osname);
		$("#ipaddress").html(data.ipaddress);
		$("#manufacturer").html(data.manufacturer);
		$("#serialnumber").html(data.serialnumber);
		$("#productname").html(data.productname);
		$("#hardwaremodel").html(data.hardwaremodel);
		$("#status").html(data.status);
		$("#physicalprocessorcount").html(data.physicalprocessorcount);
		$("#uuid").html(data.uuid);
		$("#processorcount").html(data.processorcount);
		$("#interfaces").html(data.interfaces);
		// cpu核数详情
		var cpu = data.processorcountArray.processor0;
		var cpup = "";
		for(var i=0;i<data.processorcount;i++){
			cpup +="<p style='margin:0px;padding:0px;'>processor"+i+":"+cpu+"</p>";
		}
		$("#cpuDetail").html(cpup);
		// 网卡详情
		var nic = JSON.stringify(data.interfaceArray);
		var nicArr = nic.substring(1,nic.length-1).split(",");
		var nicDetail ="";
		for(var i=0;i<nicArr.length;i++){
			nicDetail +="<p style='margin:0px;padding:0px;'>"+nicArr[i].replace(/\"/g,"")+"</p>";
		}
		$("#interfaceDetail").html(nicDetail);

		// 设置window显示的位置
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#discoveryDeploymentDetailWindow").jqxWindow({ position: { x: (windowW-750)/2, y: (windowH-300)/2 } });
    	$("#discoveryDeploymentDetailWindow").jqxWindow('open');
	}
}
// 部署系统
function popDeploymentSystemWindow(){
	var index = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getselectedrowindex');
 	// 判断审核按钮是否可用
  	var ok =  $("#deploymentSystem").jqxButton("disabled");
	if(index >=0 && !ok){
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#DeploymentSystemWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-100)/2 } });
    	$("#DeploymentSystemWindow").jqxWindow('open');
	}
}

// 提交系统部署信息
function deploymentSystemSubmit(){
	var index = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getselectedrowindex');
	if(index >=0){
		var data = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getrowdata', index);
		
		var macAddress = data.macAddress;
		var hostOsType = $("#hostOsType").val();
		// 提交部署系统
		Core.AjaxRequest({
 			url : ws_url + "/rest/hosts/deploymentSystem",
 			type:'post',
 			params:{
 				macAddress : macAddress,
 				type : hostOsType
 			},
 			callback : function (data) {
 				$("#DeploymentSystemWindow").jqxWindow('close');
 			}
 		 });
	}
}
// 加入平台
function popAddToPlatformWindow(){
	var rowindex = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getselectedrowindex');
	// 判断审核按钮是否可用
  	var ok =  $("#joinPlatform").jqxButton("disabled");
	if(rowindex >= 0 && !ok){
		var data = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getrowdata', rowindex);
		var host = new findAndDeploymentHostModel();
		$("#join-parentTopologySid").val(resTopologySid);
		$("#join-deployNodeId").val(data.nodeName);
		host.setplatformhostForm(data);
		
		var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#addToPlatformWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-338)/2 } });
    	$("#addToPlatformWindow").jqxWindow('open');
		
	}
}

// 提交加入平台的主机信息
function platformHostSubmit(){
	$('#addToPlatformForm').jqxValidator('validate');
}

// 删除
function removeDiscoveryHost(){
	var index = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getselectedrowindex');
	if(index >=0){
		var data = $('#hostDiscoveryDeploymentDatagrid').jqxGrid('getrowdata', index);
		Core.confirm({
			title:"提示",
			message:"确定要删除该主机吗?",
			yes:"确定",
			confirmCallback:function(){
				Core.AjaxRequest({
		 			url : ws_url + "/rest/hosts/deleteDeploymentHost/"+data.nodeName+"",
		 			type:'get',
		 			callback : function (data) {
		 				refreshDiscoveryHost();
		 			}
		 		 });
			}
	    });
	}
}

  
  