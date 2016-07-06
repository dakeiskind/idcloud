var vmManagedModel = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		allocateVmName: "", 
    		osType: "", 
    		tenantSid:"",
    		ownerId:"",
    		searchType:resTopologyType,
    		searchSid:resTopologySid
	};
    
    //查询条件租户、用户联动查询
    $('#search-vm-tenant').on('select', function (event){
    		    var args = event.args;
    		    if (args) {
    		    var item = args.item;
    		    var value = item.value;
    		    if(item.value!="全部"){
    		    	var params={tenantSid:value};
    		    }
    		    var usersearch = new codeModel({autoDropDownHeight : false,dropDownHeight : 185});
    		    usersearch.getCustomCode("search-vm-owner","/user/findAll","realName","account",true,"post",params);
    		}                        
		});
    // 给datagrid赋值
    this.searchVMInfo = function(){
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/resourceInstance/platform/findAllManagedVM",
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
 			    $("#vmMgtdatagrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	$("#search-vm-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	
    	var systemconfig = new codeModel({autoDropDownHeight:false,dropDownWidth:200,dropDownHeight : 185});
    	systemconfig.getCommonCode("search-operation-system","OS_TYPE",true);
    	var tenantsearch = new codeModel({autoDropDownHeight : false,dropDownWidth : 155,dropDownHeight : 185});
		tenantsearch.getCustomCode("search-vm-tenant", "/tenants", "tenantName","tenantSid", true, "post", null);
		var usersearch = new codeModel({autoDropDownHeight : false,dropDownWidth : 135,dropDownHeight : 185});
		usersearch.getCustomCode("search-vm-owner","/user/findAll","realName","account",true,"post",null);
        $("#search-vm-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };

    // 初始化用户datagrid的数据源
    this.initVMDatagrid = function(){
          $("#vmMgtdatagrid").jqxGrid({
              width: "100%",
			  theme:currentTheme,
              columnsresize: true,
              pageable: true, 
              pagesize: 10, 
              autoheight: true,
              autowidth: false,
              sortable: true,
              selectionmode:"singlerow",
              localization: gridLocalizationObj,
              columns: [
                  { text: '云主机名称', datafield: 'allocateVmName'},
                  { text: '操作系统', datafield: 'osTypeName'},
                  { text: '状态', datafield: 'statusName'},
                  { text: 'CPU（核）', datafield: 'cpuCores',width:"70px"},
                  { text: '内存（MB）', datafield: 'memorySize',width:"80px"},
                  { text: '内网IP', datafield: 'ip' ,width:"90px"},
                  { text: '外网IP', datafield: 'publicIp',width:"90px"},  
                  { text: '所属主机', datafield: 'hostName',width:"90px"},  
                  { text: '所属租户', datafield: 'tenantName'},
                  { text: '所有者', datafield: 'realName',width:"65px"},
              ],
              showtoolbar: true,
              // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var vmDetail = $("<div><a id='vmDetail' onclick='popVmDetailInfo()' >&nbsp;&nbsp;<i class='icons-blue icon-th-list'></i>详情&nbsp;&nbsp;</a></div>");
                  var startVm = $("<div><a id='startVm' onclick='startVm()'style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-play'></i>启动&nbsp;&nbsp;</a></div>");
                  var stopVm = $("<div><a id='stopVm' style='margin-left:-1px' onclick='stopVm()'>&nbsp;&nbsp;<i class='icons-blue icon-stop'></i>关机&nbsp;&nbsp;</a></div>");
                  var rebootVm = $("<div><a id='rebootVm' style='margin-left:-1px' onclick='rebootVm()'>&nbsp;&nbsp;<i class='icons-blue icon-cw'></i>重启&nbsp;&nbsp;</a></div>");
                  var reConfigVm = $("<div><a id='reConfigVm' style='margin-left:-1px' onclick='reConfigVm()'>&nbsp;&nbsp;<i class='icons-blue icon-cog-1'></i>调整配置&nbsp;&nbsp;</a></div>");
                  var migrateVm = $("<div><a id='migrateVm' style='margin-left:-1px' onclick='migrateVm()'>&nbsp;&nbsp;<i class='icons-blue icon-move'></i>迁移&nbsp;&nbsp;</a></div>");
                  var destoryVm = $("<div><a id='destoryVm' onclick='destoryVm()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>退订&nbsp;&nbsp;</a></div>");
                  var syncVm = $("<div><a id='syncVm' onclick='syncVm()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>同步虚拟机&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
                  container.append(vmDetail);
                  container.append(startVm);
                  container.append(stopVm);
                  container.append(rebootVm);
                  container.append(reConfigVm);
                  container.append(migrateVm);
                  container.append(destoryVm);
                  container.append(syncVm);
              }
          });
          
          // 控制按钮是否可用
    	  $("#vmMgtdatagrid").on('rowselect', function (event) {
    		  var args = event.args; 
    		  var index = args.rowindex;
    		  var data = $('#vmMgtdatagrid').jqxGrid('getrowdata', index);
    		  
    		  $("#vmDetail").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
    		  if(data.statusName=="正常"){
    			  $("#startVm").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
    			  $("#stopVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    			  $("#rebootVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    			  $("#reConfigVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    			  $("#migrateVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    			  $("#destoryVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    		  }else if(data.statusName=="已关机"){
    			  $("#startVm").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
    			  $("#stopVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
    			  $("#rebootVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
    			  $("#reConfigVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    			  $("#migrateVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    			  $("#destoryVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    		  }
          });
    	  $("#vmDetail").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
  		  $("#startVm").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
		  $("#stopVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
		  $("#rebootVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
		  $("#reConfigVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
		  $("#migrateVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
		  $("#destoryVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
		  $("#syncVm").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
          
    };
    
    this.getVmStatisticsInfo = function(){
    	var vmData = null;
    	Core.AjaxRequest({
 			url : ws_url + "/rest/resourceInstance/platform/findAllManagedVM",
 			type:'post',
 			params:me.searchObj,
 			async:false,
 			callback : function (data) {
 				vmData = data;
 			}
 		});
    	
    	var data = new Object();
		data.vmCount = vmData.length;
		data.attr = new Array();
		
		var value = [0,0,0,0];
		var name =["正常","关机","暂停","其他"];
		for(var i=0;i<vmData.length;i++){
			if("02" == vmData[i].status){
				// 正常
				value[0] += 1; 
			}else if("03" == vmData[i].status){
				// 关机
				value[1] += 1; 
			}else if("04" == vmData[i].status){
				// 暂停
				value[2] += 1; 
			}else {
				// 其他
				value[3] += 1; 
			} 
		}
		for(var i=0; i<4;i++){
			var obj = new Object();
			obj.name = name[i];
			obj.value = value[i];
			data.attr.push(obj);
		}
		return data;
    	
    };

 
};
// 查询虚机
function searchVMMgt(){
	var vmmanagedmodel = new vmManagedModel();
	vmmanagedmodel.searchObj.allocateVmName = $("#search-vm-name").val();
	vmmanagedmodel.searchObj.osType = $("#search-operation-system").val()=="全部"?"":$("#search-operation-system").val();
	vmmanagedmodel.searchObj.tenantSid = $("#search-vm-tenant").val()=="全部"?"":$("#search-vm-tenant").val();
	vmmanagedmodel.searchObj.ownerId = $("#search-vm-owner").val()=="全部"?"":$("#search-vm-owner").val();
	vmmanagedmodel.searchVMInfo();
}

//启动虚机
function startVm(){
	var rowindex = $('#vmMgtdatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		    var data = $('#vmMgtdatagrid').jqxGrid('getrowdata', rowindex);
		   Core.AjaxRequest({
	 			url : ws_url + "/rest/resourceInstance/platform/operateVm/"+data.resInstanceSid+"/start",
	 			type:"GET",
	 			async:false,
	 			callback : function (data) {
	 				var me=new vmManagedModel();
	 				me.searchVMInfo();
	 			},
	 			failure:function(){
	 				
	 			}
	 		 });
	}
}
//关机
function stopVm(){
	var rowindex = $('#vmMgtdatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		var data = $('#vmMgtdatagrid').jqxGrid('getrowdata', rowindex);
		Core.confirm({
			title:"提示",
			message:"确定要关闭虚拟机吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
					url : ws_url + "/rest/resourceInstance/platform/operateVm/"+data.resInstanceSid+"/stop",
					type:"GET",
					async:false,
					callback : function (data) {
						var me=new vmManagedModel();
						me.searchVMInfo();
					},
					failure:function(){
						
					}
				});
			}
		});
	
	}
}
//重新启动虚机
function rebootVm(){
	var rowindex = $('#vmMgtdatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		var data = $('#vmMgtdatagrid').jqxGrid('getrowdata', rowindex);
		
		Core.confirm({
			title:"提示",
			message:"确定要重启虚拟机吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
					url : ws_url + "/rest/resourceInstance/platform/operateVm/"+data.resInstanceSid+"/reStart",
					type:"GET",
					async:false,
					callback : function (data) {
						var me=new vmManagedModel();
						me.searchVMInfo();
					},
					failure:function(){
						
					}
				});
			}
		});
	}
}
//退订虚机
function destoryVm(){
	var rowindex = $('#vmMgtdatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		var data = $('#vmMgtdatagrid').jqxGrid('getrowdata', rowindex);
		
		Core.confirm({
			title:"提示",
			message:"确定要退订虚拟机吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
					url : ws_url + "/rest/serviceInstance/release/"+data.serviceInstanceSid,
					type:"GET",
					async:false,
					callback : function (data) {
						var me=new vmManagedModel();
						me.searchVMInfo();
					},
					failure:function(){
						
					}
				});
			}
		});
	}
}
//重新配置虚机
function reConfigVm(){
	var rowindex = $('#vmMgtdatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		var data = $('#vmMgtdatagrid').jqxGrid('getrowdata', rowindex);
  		// 给编辑画面赋值
  		$("#resInstanceSid").val(data.resInstanceSid);
  		$("#allocateInstanceId").val(data.allocateVmName);
  		var vmreconfigmodel = new vmReconfigModel();
  		vmreconfigmodel.initPopWindow();
  		vmreconfigmodel.setVmReconfigForm(data);
  		
  		// 设置弹出框位置
  		var windowW = $(window).width(); 
      	var windowH = $(window).height(); 
      	$("#vmReconfigWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-150)/2 } });
      	$("#vmReconfigWindow").jqxWindow('open');
	}
}

//同步虚机
function syncVm(){
	var me = new vmManagedModel();
	Core.AjaxRequest({
		url : ws_url + "/rest/resourceInstance/platform/syncManagedVM",
		params:me.searchObj,
		callback : function (data) {
			me.searchVMInfo();
		},
		failure:function(){
			
		}
	});
}
  
//迁移虚机,选择目标主机
function migrateVm(){
	var rowindex = $('#vmMgtdatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		var data = $('#vmMgtdatagrid').jqxGrid('getrowdata', rowindex);
  		var targetHost = new targetHostModel();
  		targetHost.initPopWindow();
  		targetHost.initHostDatagrid();
  		targetHost.searchHostConfigInfo(data.resInstanceSid,data.resPoolSid);
  		
  		// 设置弹出框位置
  		var windowW = $(window).width(); 
      	var windowH = $(window).height(); 
      	$("#targetHostWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-300)/2 } });
      	$("#targetHostWindow").jqxWindow('open');
	}
}
  