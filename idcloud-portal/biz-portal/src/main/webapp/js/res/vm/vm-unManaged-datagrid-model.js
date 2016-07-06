var vmUnManagedModel = function(){
    this.items = ko.observableArray();
    this.searchObj = {
    		allocateVmName: "", 
    		osType: "", 
    		tenantSid:"",
    		ownerId:"",
    		searchType:resTopologyType,
    		searchSid:resTopologySid
	};
    // 初始化用户datagrid的数据源
    this.initUnVMDatagrid = function(){
          $("#vmUnMgtdatagrid").jqxGrid({
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
                  { text: '云主机名称', datafield: 'name'},
                  { text: '操作系统', datafield: 'guestType'},
                  { text: '状态', datafield: 'statusName'},
                  { text: 'CPU（核）', datafield: 'numCpu',width:"70px"},
                  { text: '内存（MB）', datafield: 'memorySizeMB',width:"80px"},
                  { text: '内网IP', datafield: 'ipAddress',width:"90px"},
                  { text: '所属主机', datafield: 'hostName',width:"90px"},
                  { text: '操作', datafield: 'Edit' ,width:"40px", columntype: 'button', cellsrenderer: function () {
                      return "纳管";
                   }, buttonclick: function (row) {
                      editrow = row;
                      var dataRecord = $("#vmUnMgtdatagrid").jqxGrid('getrowdata', editrow);
                      var  unmgtvmtodb=new umMgtVmToDb();
                      unmgtvmtodb.initPopWindow();
                      unmgtvmtodb.initVMDatagrid();
                      unmgtvmtodb.setUmMgtVmToDbForm(dataRecord);
                     // 设置弹出框位置
            		 var windowW = $(window).width(); 
                  	 var windowH = $(window).height(); 
                  	 $("#unMgtVmToDbWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-410)/2 } });
                  	 $("#unMgtVmToDbWindow").jqxWindow('open');
                  }
                  }
              ],
              showtoolbar: true,
              // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var scanUnManagedVm = $("<div><a id='scanUnManagedVm' onclick='scanUnManagedVm()'>&nbsp;&nbsp;<i class='icons-blue icon-cw'></i>扫描未纳管虚拟机&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
                  container.append(scanUnManagedVm);
              }
          });
    	  $("#scanUnManagedVm").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
          
    };
 
};
//给datagrid赋值
function scanUnManagedVm(){
	var me = new vmUnManagedModel();
  	 Core.AjaxRequest({
			url : ws_url + "/rest/resourceInstance/platform/findAllUnManagedVM",
			params:me.searchObj,
			callback : function (data) {
				me.items(data);
				var sourcedatagrid ={
			              datatype: "json",
			              localdata: me.items
			    };
			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
			    $("#vmUnMgtdatagrid").jqxGrid({source: dataAdapter});
			}
		 });
//	var data={connectionState: "connected",
//	guestId: "rhel6_64Guest",
//	guestMemoryUsageMB: 20,
//	guestType: "Red Hat Enterprise Linux 6 (64 位)",
//	hostMemoryUsage: 2048,
//	hostName: "192.168.7.101",
//	instanceUuid: "50119684-1ef8-3da7-595d-1a14f44d8bc5",
//	ipAddress: null,
//	locationId: "564d501a-a348-5aa0-f62c-86ef6e87f8e8",
//	maxCpuUsage: 4532,
//	memorySizeMB: 2048,
//	name: "VM-10022-12411",
//	numCpu: 2,
//	overallCpuUsageMHZ: 0,
//	overallStatus: "green",
//	status: "poweredOn",
//	statusName: "正常",
//	storageCommitted: 4785843153,
//	storageUncommitted: 8212447232,
//	uid: 0,
//	uuid: "4211b9a4-a500-611b-afe0-2404fe8f2521",
//	vmLocation: null,
//	vmPath: "[DS101-01] VM-10022-12411/VM-10022-12411.vmx"
//	};
//	me.items(data);
//	var sourcedatagrid ={
//              datatype: "json",
//              localdata: me.items
//    };
//    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
//    $("#vmUnMgtdatagrid").jqxGrid({source: dataAdapter});
  };




  
  