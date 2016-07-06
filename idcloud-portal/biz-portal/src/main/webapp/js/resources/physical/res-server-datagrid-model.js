var x86HostName ="";
var x86HostIp = "";
var serverDatagridModels = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		  "qm.hostEquipNameLike":null,
          "qm.equipRoomSid":null,
          "qm.equipCabinetSid":null,
          "qm.equipRackSid":null,
        	"qm.resTopologyNameVE":null,
        	"qm.resTopologyNameDC":null,
        	"qm.equipCabinetFrameSid":null,
        	"qm.resTopologySid":null,
        	"qm.resTopologySid":null,
        	"qm.workStatus":null,
        	"qm.cpuType":null,
        	"qm.hostOsType":null,
        	"qm.resTopologySid":resTopologySid,
          "qm.serialNumberLike":null,
        	 parentTopologySid:resTopologySid
	};
    this.staObj = {
     	     resTopologySid:resTopologySid
    };

    this.getHostName = function(name){
      var Todata = null;
      Core.AjaxRequest({
        url : ws_url + "/rest/host",
        type:'POST',
        async:false,
        params:{
          hostName : name
        },
        callback : function (data) {
          Todata = data;
        }
       });
      return Todata;
    };

    this.getHostIp = function(name){
      var Todata = null;
      Core.AjaxRequest({
        url : ws_url + "/rest/host",
        type:'POST',
        async:false,
        params:{
          hostIp : name
        },
        callback : function (data) {
          Todata = data;
        }
       });
      return Todata;
    };
	
  //统计机框资源查询方法
    this.getDataGridData = function(){
    	 var serverData;
    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/phycomputes/server/statistical",
	 			type:'post',
	 			params:me.staObj,
	 			async:false,
	 			callback : function (data) {
	 				serverData = data;
	 			}
	     });
    	 return serverData;
    };
		this.ServerResourcesStatistics = function(){
			var serverData = me.getDataGridData();
			var data = new Object();
			data.attr = new Array();
			data.serverCount = serverData.staTotalServer;
			var value = [serverData.staTotalServer];
			var name =["使用"];
			for(var i=0; i<1;i++){
				var obj = new Object();
				var val = value[i] == null ? 0:value[i];
				obj.name = name[i]+"("+val+")";
				obj.value = val;
				data.attr.push(obj);
			}
			return data;
		}
 
 
 this.initServerInput = function(){
 	// 初始化查询组件
     $("#search-serverName").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
     $("#search-serverName-serialNumber").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
     $("#search-serverButton").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
     $("#search-phyhost-choose").jqxDropDownList('selectIndex', 0 ); 
 };
 this.initComboxLinkage = function(){
 	var codeadd = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth:150});
  codeadd.getCustomCode("search-server-room","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
  var zoneSidS =  $("#search-server-room").val();
 	codeadd.getCustomCode("search-server-cabinet","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSidS});
  var cabinetSidS =  $("#search-server-cabinet").val();
 	codeadd.getCustomCode("search-server-rack","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSidS});

 	
 	codeadd.getCustomCode("add-phyhost-equipRoomSid","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
 	var zoneSid =  $("#add-phyhost-equipRoomSid").val();
 	codeadd.getCustomCode("add-phyhost-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSid});
 	var cabinetSid =  $("#add-phyhost-equipCabinetSid").val();
 	codeadd.getCustomCode("add-phyhost-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSid});
 	var rackSid =  $("#add-phyhost-equipRackSid").val();

    codeadd.getCustomCode("edit-phyhost-equipRoomSid","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
    var zoneSid =  $("#edit-phyhost-equipRoomSid").val();
    codeadd.getCustomCode("edit-phyhost-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSid});
    var cabinetSid =  $("#edit-phyhost-equipCabinetSid").val();
    codeadd.getCustomCode("edit-phyhost-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSid});
    var rackSid =  $("#edit-phyhost-equipRackSid").val();

	codeadd.getCustomCode("add-phyIbmhost-equipRoomSid","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
	var zoneSid =  $("#add-phyIbmhost-equipRoomSid").val();
	codeadd.getCustomCode("add-phyIbmhost-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSid});
	var cabinetSid =  $("#add-phyIbmhost-equipCabinetSid").val();
	codeadd.getCustomCode("add-phyIbmhost-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSid});
	var rackSid =  $("#add-phyIbmhost-equipRackSid").val();

  codeadd.getCustomCode("edit-phyIbmhost-equipRoomSid","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
  var zoneSid =  $("#edit-phyIbmhost-equipRoomSid").val();
  codeadd.getCustomCode("edit-phyIbmhost-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSid});
  var cabinetSid =  $("#edit-phyIbmhost-equipCabinetSid").val();
  codeadd.getCustomCode("edit-phyIbmhost-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSid});
  var rackSid =  $("#edit-phyIbmhost-equipRackSid").val();

    $('#search-server-room').on('change', function (event){  
      var args = event.args;
      if (args) {
        var item = args.item;
        var value = item.value;
        codeadd.getCustomCode("search-server-cabinet","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:value});
        var items = $("#search-server-cabinet").jqxDropDownList('getItems');
        if(items.length > 0){
          $("#search-server-cabinet").jqxDropDownList('selectItem', items[0] );
        }
      } 
  });
  $('#search-server-cabinet').on('change', function (event){
    var args = event.args;
      if (args) {
        var item = args.item;
        var value = item.value;
        codeadd.getCustomCode("search-server-rack","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:value});
        var items = $("#search-server-rack").jqxDropDownList('getItems');
        if(items.length > 0){
          $("#search-server-rack").jqxDropDownList('selectItem', items[0] );
        }
        var vesid = items.value;
      }
  });


    $('#add-phyIbmhost-equipRoomSid').on('change', function (event){  
      var args = event.args;
      if (args) {
        var item = args.item;
        var value = item.value;
        codeadd.getCustomCode("add-phyIbmhost-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:value});
        var items = $("#add-phyIbmhost-equipCabinetSid").jqxDropDownList('getItems');
        if(items.length > 0){
          $("#add-phyIbmhost-equipCabinetSid").jqxDropDownList('selectItem', items[0] );
        }
      } 
  });
  $('#add-phyIbmhost-equipCabinetSid').on('change', function (event){
    var args = event.args;
      if (args) {
        var item = args.item;
        var value = item.value;
        codeadd.getCustomCode("add-phyIbmhost-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:value});
        var items = $("#add-phyIbmhost-equipRackSid").jqxDropDownList('getItems');
        if(items.length > 0){
          $("#add-phyIbmhost-equipRackSid").jqxDropDownList('selectItem', items[0] );
        }
        var vesid = items.value;
      }
  });

  $('#edit-phyIbmhost-equipRoomSid').on('change', function (event){  
      var args = event.args;
      if (args) {
        var item = args.item;
        var value = item.value;
        codeadd.getCustomCode("edit-phyIbmhost-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:value});
        var items = $("#edit-phyIbmhost-equipCabinetSid").jqxDropDownList('getItems');
        if(items.length > 0){
          $("#edit-phyIbmhost-equipCabinetSid").jqxDropDownList('selectItem', items[0] );
        }
      } 
  });
  $('#edit-phyIbmhost-equipCabinetSid').on('change', function (event){
    var args = event.args;
      if (args) {
        var item = args.item;
        var value = item.value;
        codeadd.getCustomCode("edit-phyIbmhost-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:value});
        var items = $("#edit-phyIbmhost-equipRackSid").jqxDropDownList('getItems');
        if(items.length > 0){
          $("#edit-phyIbmhost-equipRackSid").jqxDropDownList('selectItem', items[0] );
        }
        var vesid = items.value;
      }
  });

 	
 	$('#add-phyhost-equipRoomSid').on('change', function (event){  
	    var args = event.args;
	    if (args) {
		    var item = args.item;
		    var value = item.value;
		    codeadd.getCustomCode("add-phyhost-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:value});
         var items = $("#add-phyhost-equipCabinetSid").jqxDropDownList('getItems');
        if(items.length > 0){
          $("#add-phyhost-equipCabinetSid").jqxDropDownList('selectItem', items[0] );
        }
	    } 
 	});
 	$('#add-phyhost-equipCabinetSid').on('change', function (event){
		var args = event.args;
	    if (args) {
		    var item = args.item;
		    var value = item.value;
		    codeadd.getCustomCode("add-phyhost-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:value});
		    var items = $("#add-phyhost-equipRackSid").jqxDropDownList('getItems');
		    if(items.length > 0){
		    	$("#add-phyhost-equipRackSid").jqxDropDownList('selectItem', items[0] );
		    }
		    
		    var vesid = items.value;
	    }
 	});

  $('#edit-phyhost-equipRoomSid').on('change', function (event){  
      var args = event.args;
      if (args) {
        var item = args.item;
        var value = item.value;
        codeadd.getCustomCode("edit-phyhost-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:value});
         var items = $("#edit-phyhost-equipCabinetSid").jqxDropDownList('getItems');
        if(items.length > 0){
          $("#edit-phyhost-equipCabinetSid").jqxDropDownList('selectItem', items[0] );
        }
      } 
  });
  $('#edit-phyhost-equipCabinetSid').on('change', function (event){
    var args = event.args;
      if (args) {
        var item = args.item;
        var value = item.value;
        codeadd.getCustomCode("edit-phyhost-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:value});
        var items = $("#edit-phyhost-equipRackSid").jqxDropDownList('getItems');
        if(items.length > 0){
          $("#edit-phyhost-equipRackSid").jqxDropDownList('selectItem', items[0] );
        }
        
        var vesid = items.value;
      }
  });
 	
 	 var localData = [
         {label:"X86",value:"00"},
         {label:"IBM",value:"01"},
      ];
     var source ={
       datatype: "json",
       datafields: [
           { name: 'label' },
           { name: 'value' }
       ],
       localData:localData
     };
   var dataAdapter = new $.jqx.dataAdapter(source);
 
   $("#search-phyhost-choose").jqxDropDownList({
       selectedIndex: 0, source: dataAdapter, autoDropDownHeight: true, displayMember: "label", valueMember: "value",theme:currentTheme, width: 150, height: 22
   });
   
   $('#search-phyhost-choose').on('change', function (event){  
	    var args = event.args;
	    if (args) {
		    var item = args.item;
		    var value = item.value;
		    if("00"==value){
		    	document.getElementById("add-phyhost-x86").style.display="";
		    	document.getElementById("add-phyhost-ibm").style.display="none";
		    }else{
		    	document.getElementById("add-phyhost-ibm").style.display="";
		    	document.getElementById("add-phyhost-x86").style.display="none";
		    }

	    } 
	});

  var isViosData = [
         {label:"是",value:"02"},
         {label:"否",value:"01"},
      ];
   var sources ={
       datatype: "json",
       datafields: [
           { name: 'label' },
           { name: 'value' }
       ],
       localData:isViosData
   };
   var dataAdapterVios = new $.jqx.dataAdapter(sources);
 
   $("#add-phyIbmhost-isVios").jqxDropDownList({
       selectedIndex: 0, source: dataAdapterVios, autoDropDownHeight: true, displayMember: "label", valueMember: "value",theme:currentTheme, width: 150, height: 22
   });

    $("#edit-phyIbmhost-isVios").jqxDropDownList({
       selectedIndex: 0, source: dataAdapterVios, autoDropDownHeight: true, displayMember: "label", valueMember: "value",theme:currentTheme, width: 150, height: 22
   });
 }
 
 
 this.initServerDatagrid = function(){
 	var dataAdapter = Core.getPagingDataAdapter({
			gridId: "serverdatagrid",
			url: ws_url + "/rest/phycomputes/findAll",
			params: me.searchObj
		});
     $("#serverdatagrid").jqxGrid({
		width: "100%",
	    theme:currentTheme,
	    source: dataAdapter,
	    virtualmode: true,
        columnsresize: true,
	    rendergridrows: function(){
	    return dataAdapter.records;
	    },
         columnsresize: true,
         pageable: true, 
         pagesize: 10, 
         autoheight: true,
         autowidth: false,
         sortable: true,
         selectionmode:"singlerow",
         localization: gridLocalizationObj,
         columns: [
                { text: '品牌', datafield: 'brand',align: 'center',cellsalign:'center',width:100},
                { text: '型号', datafield: 'phyModel',align: 'center',cellsalign:'center',width:100},
                { text: '分类', datafield: 'cabinetFrameName',cellsalign:'right',width:120},
                { text: '序列号', datafield: 'phySerialNumber',cellsalign:'right',width:150},
                { text: '主机IP', datafield: 'hostIp',width:100},
                { text: '操作系统', datafield: 'hostOsTypeName',width:180},
                { text: '主机名称', datafield: 'hostName',cellsalign:'right'},
                { text: '维保厂商', datafield: 'maintCompany',cellsalign:'right'},
                { text: '设备编号', datafield: 'name',width:80},
                { text: '维保电话', datafield: 'maintTel',cellsalign:'right',width:120},
                { text: '所属机柜', datafield: 'cabinetName',cellsalign:'right'},
                { text: '所属机架', datafield: 'rackName',cellsalign:'right'},
         	  /*  { text: '主机名称', datafield: 'hostName',cellsalign:'right'},    
                { text: '主机IP', datafield: 'hostIp'},
                { text: '操作系统', datafield: 'hostOsTypeName',width:100},
                { text: 'CPU类型', datafield: 'cpuType'},
                { text: 'CPU个数(个)', datafield: 'cpuNumber',cellsalign:'right',width:80},
                { text: 'CPU核数(核)', datafield: 'cpuCores',cellsalign:'right',width:80},
                { text: 'CPU使用率', datafield: 'hostCpuUsage',cellsalign:'right',width:80},
                { text: '内存大小(MB)', datafield: 'memorySizeGb',cellsalign:'right',width:60},
                { text: '服务器编号', datafield: 'name',width:80},
                { text: '序列号', datafield: 'phySerialNumber',cellsalign:'right',width:80},
                { text: '分类', datafield: 'cabinetFrameName',cellsalign:'right',width:80},
                { text: '品牌', datafield: 'brand',align: 'center',cellsalign:'center',width:150},
                { text: '型号', datafield: 'phyModel',align: 'center',cellsalign:'center',width:60},*/
              { text: '操作', datafield: '',align: 'center',cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
                  return "<div align='center' style='width:90px;height:22px;line-height:25px;'>&nbsp;&nbsp;<i onclick='goPhyHostMonitorPage("+row+")' title='详情' class='icons-blue icon-popup'></i></div>";
               },width:100  }
              
         ],
         showtoolbar: true,
         // 设置toolbar操作按钮
         rendertoolbar: function (toolbar) {
             var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
             var refreshBtn = $("<div><a id='refreshSBtn' onclick='refreshServer()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
             var addBtn = $("<div><a id='addSBtn' onclick='addServer()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>增加&nbsp;&nbsp;</a></div>");
             var editBtn = $("<div><a id='editSBtn' onclick='editServer()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
             var deleteBtn = $("<div><a id='deleteSBtn' onclick='deleteServer()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
             var refreshBtn = $("<div><a id='refreshSBtn' onclick='refreshServer()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
             toolbar.append(container);
             container.append(refreshBtn);
             container.append(addBtn);
             container.append(editBtn);
             container.append(deleteBtn);
             container.append(refreshBtn);
             $("#refreshSBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
             $("#addSBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
             $("#editSBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
             $("#deleteSBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
         }
     });
};


this.initPopWindow = function(){

    $("#x86ServerWindows").jqxWindow({
              width: 800, 
              height:700,
              theme:currentTheme,  
              resizable: false,  
              isModal: true, 
              autoOpen: false, 
              cancelButton: $("#serverx86-close-button"), 
              modalOpacity: 0.3,
              initContent:function(){
                // 初始化新增用户页面组件
              $("#serverx86-close-button").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
              }
     });
    $("#ibmServerWindows").jqxWindow({
              width: 800, 
              height:700,
              theme:currentTheme,  
              resizable: false,  
              isModal: true, 
              autoOpen: false, 
              cancelButton: $("#serveribm-close-button"), 
              modalOpacity: 0.3,
              initContent:function(){
                // 初始化新增用户页面组件
              $("#serveribm-close-button").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
              }
     });
	  $("#addphyHostWindow").jqxWindow({
        width:1550, 
        height:1000,
        resizable: false,  
        isModal: true, 
        autoOpen: false, 
        cancelButton: $("#addPhyHostCancel"), 
        theme: currentTheme,
        modalOpacity: 0.3,
        initContent:function(){
      	  $("#add-phyhost-hostName").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-hostIp").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-cpuNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-cpuType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyhost-cpuCores").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-managementPwd").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-managementUser").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-memorySize").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-locationNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-name").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-equipType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-serialNumber").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-brand").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-model").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
     	    $("#add-phyhost-resTopologySid").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-spec").jqxInput({placeHolder: "", height:30 , width: 500, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-description").jqxInput({placeHolder: "", height:30 , width: 500, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-remoteMgtPwd").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-maintCompany").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-purchaseDate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
      	  $("#add-phyhost-startdate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
      	  $("#add-phyhost-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#add-phyhost-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});


          $("#add-phyIbmhost-hostName").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-hostIp").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-cpuNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-cpuType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          //$("#add-phyIbmhost-cpuCores").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-managementPwd").jqxPasswordInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-viosPwd").jqxPasswordInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-viosUser").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-managementUser").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-memorySize").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-locationNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-name").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-equipType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-serialNumber").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-brand").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-model").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-resTopologySid").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-spec").jqxInput({placeHolder: "", height:30 , width: 500, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-description").jqxInput({placeHolder: "", height:30 , width: 500, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-remoteMgtPwd").jqxPasswordInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-maintCompany").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-purchaseDate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
          $("#add-phyIbmhost-startdate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
          $("#add-phyIbmhost-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#add-phyIbmhost-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#addPhyHostSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	      $("#addPhyHostCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
        }
	  });
	  
	  $("#editphyHostWindow").jqxWindow({
		    width:1000, 
        height:600,
        resizable: false,  
        isModal: true, 
        autoOpen: false, 
        cancelButton: $("#editPhyHostCancel"), 
        theme: currentTheme,
        modalOpacity: 0.3,
        initContent:function(){
      	  $("#edit-phyhost-hostName").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-hostIp").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-cpuNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-cpuCores").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyhost-cpuType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-managementPwd").jqxPasswordInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-managementUser").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-memorySize").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-locationNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-name").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-equipType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-serialNumber").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-brand").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-model").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	 // $("#edit-phyhost-equipCabinetSid").jqxInput({placeHolder: "", height:22 , width: 170, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-resTopologySid").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-spec").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
      	  $("#edit-phyhost-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyhost-remoteMgtPwd").jqxPasswordInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyhost-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyhost-maintCompany").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyhost-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyhost-purchaseDate").jqxDateTimeInput({width: '150px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
          $("#edit-phyhost-startEndDate").jqxDateTimeInput({width: '150px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
          $("#edit-phyhost-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyhost-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyhost-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          
          $("#edit-phyIbmhost-hostName").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-hostIp").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-cpuNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-cpuType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          //$("#add-phyIbmhost-cpuCores").jqxInput({placeHolder: "", height:22, width: 170, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-managementPwd").jqxPasswordInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-viosPwd").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-viosUser").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-managementUser").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-memorySize").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-locationNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-name").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-equipType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-serialNumber").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-brand").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-model").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-resTopologySid").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-spec").jqxInput({placeHolder: "", height:30 , width: 500, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-description").jqxInput({placeHolder: "", height:30 , width: 500, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-remoteMgtPwd").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-maintCompany").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-purchaseDate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
          $("#edit-phyIbmhost-startEndDate").jqxDateTimeInput({width: '170px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
          $("#edit-phyIbmhost-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#edit-phyIbmhost-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
          $("#editPhyHostSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
          $("#editPhyHostCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
        }
	  });
	  
};
this.searchServersInfo = function(){
	// $("#serverdatagrid").jqxGrid("gotopage",0);
	var dataAdapter = Core.getPagingDataAdapter({
		gridId: "serverdatagrid",
		url : ws_url + "/rest/phycomputes/findAll",
		params: me.searchObj
	});         
	$("#serverdatagrid").jqxGrid({
		source: dataAdapter,
		rendergridrows: function(){
			return dataAdapter.records;
		}
	});
};

this.initValue = function(){
  $('#add-phyhost-x86').jqxValidator({
		rules : [
             {input: '#add-phyhost-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input : '#add-phyhost-name',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#add-phyhost-hostName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input : '#add-phyhost-hostName',message : '长度不能大于64个字符!',action : 'keyup, blur',rule : 'length=1,64'},
             {input: '#add-phyhost-hostName', message: '主机名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
                  var list = me.getHostName(input.val());
                  if(list.length > 0){
                    return false;
                  }else{
                    return true;
                  }
                }
             },
             {input: '#add-phyhost-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input : '#add-phyhost-equipType',message : '长度不能大于50个字符!',action : 'keyup, blur',rule : 'length=1,50'},
             {input: '#add-phyhost-hostIp', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             { input: '#add-phyhost-hostIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
                  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                    if(!pattern.test(input.val())){
                      return false;
                    }else{
                      return true;
                    }
                  }
             },
             { input: '#add-phyhost-hostIp', message: '主机IP地址重复，请重新输入', action: 'blur', rule: function (input, commit) {
                            var list = me.getHostIp(input.val());
                            if(list.length > 0){
                              return false;
                            }else{
                              return true;
                            }
                          }
                    },
             {input: '#add-phyhost-cpuNumber', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input: '#add-phyhost-cpuNumber', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
                
                if(/[^\d]/g.test(input.val())){
                    return false;
                  }else{
                    return true;
                  }
                }
              },
             {input: '#add-phyhost-cpuCores', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input: '#add-phyhost-cpuCores', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
               
               	if(/[^\d]/g.test(input.val())){
                    return false;
                  }else{
                    return true;
                  }
                }
              },
             {input: '#add-phyhost-memorySize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input: '#add-phyhost-memorySize', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
                if(/[^\d]/g.test(input.val())){
                    return false;
                  }else{
                    return true;
                  }
                }
              },

              {input: '#add-phyhost-remoteMgtIp1', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
             var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                if(input.val() == null || input.val() == ""){
                              return true;
                            }else{
                              if(!pattern.test(input.val())){
                                return false;
                              }else{
                                return true;
                              }
                            }
                          }
             },
             {input: '#add-phyhost-remoteMgtIp2', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
             var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                if(input.val() == null || input.val() == ""){
                              return true;
                            }else{
                              if(!pattern.test(input.val())){
                                return false;
                              }else{
                                return true;
                              }
                            }
                          }
             },
             {input: '#add-phyhost-relevanceIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
             var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                if(input.val() == null || input.val() == ""){
                              return true;
                            }else{
                              if(!pattern.test(input.val())){
                                return false;
                              }else{
                                return true;
                              }
                            }
                          }
             },
             { input: '#add-phyhost-warrantyPeriod', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
                 if(input.val() == null || input.val() == ""){
                                    return true;
                                  }else{
                                    if(/[^\d]/g.test(input.val())){
                                      return false;
                                    }else{
                                      return true;
                                    }
                                  }
                  }
               },
		]
	});
$('#add-phyhost-ibm').jqxValidator({
    rules : [
             {input: '#add-phyIbmhost-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input: '#add-phyIbmhost-name',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#add-phyIbmhost-hostName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input: '#add-phyIbmhost-hostName',message : '长度不能大于64个字符!',action : 'keyup, blur',rule : 'length=1,64'},
             {input: '#add-phyIbmhost-hostName', message: '主机名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
                  var list = me.getHostName(input.val());
                  if(list.length > 0){
                    return false;
                  }else{
                    return true;
                  }
                }
             },
             {input: '#add-phyIbmhost-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input: '#add-phyIbmhost-equipType',message : '长度不能大于50个字符!',action : 'keyup, blur',rule : 'length=1,50'},
             {input: '#add-phyIbmhost-hostIp', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input: '#add-phyIbmhost-hostIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
                  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                    if(!pattern.test(input.val())){
                      return false;
                    }else{
                      return true;
                    }
                  }
             },
              { input: '#add-phyIbmhost-hostIp', message: '主机IP地址重复，请重新输入', action: 'blur', rule: function (input, commit) {
                            var list = me.getHostIp(input.val());
                            if(list.length > 0){
                              return false;
                            }else{
                              return true;
                            }
                          }
                    },
             {input: '#add-phyIbmhost-cpuNumber', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input: '#add-phyIbmhost-cpuNumber', message: '只能保留一位小数，请重新输入', action: 'keyup, blur', rule: function (input, commit) {
                if(/[^\d]/g.test(input.val())){
                    return false;
                  }else{
                    return true;
                  }
                }
              },
             {input: '#add-phyIbmhost-memorySize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input: '#add-phyIbmhost-memorySize', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
                if(/[^\d]/g.test(input.val())){
                    return false;
                  }else{
                    return true;
                  }
                }
              },

             {input: '#add-phyIbmhost-remoteMgtIp1', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
             var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                if(input.val() == null || input.val() == ""){
                              return true;
                            }else{
                              if(!pattern.test(input.val())){
                                return false;
                              }else{
                                return true;
                              }
                            }
                          }
             },
             {input: '#add-phyIbmhost-remoteMgtIp2', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
             var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                if(input.val() == null || input.val() == ""){
                              return true;
                            }else{
                              if(!pattern.test(input.val())){
                                return false;
                              }else{
                                return true;
                              }
                            }
                          }
             },
             {input: '#add-phyIbmhost-relevanceIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
             var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                if(input.val() == null || input.val() == ""){
                              return true;
                            }else{
                              if(!pattern.test(input.val())){
                                return false;
                              }else{
                                return true;
                              }
                            }
                          }
             },
             { input: '#add-phyIbmhost-warrantyPeriod', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
                 if(input.val() == null || input.val() == ""){
                                    return true;
                                  }else{
                                    if(/[^\d]/g.test(input.val())){
                                      return false;
                                    }else{
                                      return true;
                                    }
                                  }
                  }
               }
    ]
  });
  $('#edit-phyhost-x86').jqxValidator({
		rules : [
             {input: '#edit-phyhost-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input : '#edit-phyhost-name',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#edit-phyhost-hostName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input :'#edit-phyhost-hostName',message : '长度不能大于64个字符!',action : 'keyup, blur',rule : 'length=1,64'},
             {input: '#edit-phyhost-hostName', message: '主机名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
                 if(x86HostName == input.val()){
                            return true;
                          } else{
                             var list = me.getHostName(input.val());
                              if(list.length > 0){
                                return false;
                              }else{
                                return true;
                              }
                          } 
                }
             },
             {input: '#edit-phyhost-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input: '#edit-phyhost-equipType',message : '长度不能大于50个字符!',action : 'keyup, blur',rule : 'length=1,50'},
             {input: '#edit-phyhost-hostIp', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             { input: '#edit-phyhost-hostIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
                  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                    if(!pattern.test(input.val())){
                      return false;
                    }else{
                      return true;
                    }
                  }
             },
             { input: '#edit-phyhost-hostIp', message: '主机IP地址重复，请重新输入', action: 'blur', rule: function (input, commit) {
                          if(x86HostIp == input.val()){
                            return true;
                          } else{
                             var list = me.getHostIp(input.val());
                              if(list.length > 0){
                                return false;
                              }else{
                                return true;
                              }
                          } 
                          }
                    },
             {input: '#edit-phyhost-cpuNumber', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input: '#edit-phyhost-cpuNumber', message: '只能保留一位小数，请重新输入', action: 'keyup, blur', rule: function (input, commit) {
                if(/[^\d]/g.test(input.val())){
                    return false;
                  }else{
                    return true;
                  }
                }
              },
             {input: '#edit-phyhost-cpuCores', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input: '#edit-phyhost-cpuNumber', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
                if(/[^\d]/g.test(input.val())){
                    return false;
                  }else{
                    return true;
                  }
                }
              },
             {input: '#edit-phyhost-memorySize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input: '#edit-phyhost-cpuNumber', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
                if(/[^\d]/g.test(input.val())){
                    return false;
                  }else{
                    return true;
                  }
                }
              },
              {input: '#edit-phyhost-remoteMgtIp1', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
             var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                if(input.val() == null || input.val() == ""){
                              return true;
                            }else{
                              if(!pattern.test(input.val())){
                                return false;
                              }else{
                                return true;
                              }
                            }
                          }
             },
             {input: '#edit-phyhost-remoteMgtIp2', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
             var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                if(input.val() == null || input.val() == ""){
                              return true;
                            }else{
                              if(!pattern.test(input.val())){
                                return false;
                              }else{
                                return true;
                              }
                            }
                          }
             },
             {input: '#edit-phyhost-relevanceIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
             var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                if(input.val() == null || input.val() == ""){
                              return true;
                            }else{
                              if(!pattern.test(input.val())){
                                return false;
                              }else{
                                return true;
                              }
                            }
                          }
             },
             { input: '#edit-phyhost-warrantyPeriod', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
                 if(input.val() == null || input.val() == ""){
                          return true;
                        }else{
                          if(/[^\d]/g.test(input.val())){
                            return false;
                          }else{
                            return true;
                          }
                        }
                  }
               },
		    ]
	     });

        $('#edit-phyhost-ibm').jqxValidator({
            rules : [
                     {input: '#edit-phyIbmhost-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                     {input: '#edit-phyIbmhost-name',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
                     {input: '#edit-phyIbmhost-hostName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                     {input: '#edit-phyIbmhost-hostName',message : '长度不能大于64个字符!',action : 'keyup, blur',rule : 'length=1,64'},
                     {input: '#edit-phyIbmhost-hostName', message: '主机名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
                          if(x86HostName == input.val()){
                            return true;
                          } else{
                             var list = me.getHostName(input.val());
                              if(list.length > 0){
                                return false;
                              }else{
                                return true;
                              }
                          } 
                        }
                     },
                     {input: '#edit-phyIbmhost-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                     {input: '#edit-phyIbmhost-equipType',message : '长度不能大于50个字符!',action : 'keyup, blur',rule : 'length=1,50'},
                     {input: '#edit-phyIbmhost-hostIp', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                     {input: '#edit-phyIbmhost-hostIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
                          var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                              if(!pattern.test(input.val())){
                                return false;
                              }else{
                                return true;
                              }
                          }
                     },
                     { input: '#edit-phyIbmhost-hostIp', message: '主机IP地址重复，请重新输入', action: 'blur', rule: function (input, commit) {
                            if(x86HostIp == input.val()){
                            return true;
                          } else{
                             var list = me.getHostIp(input.val());
                              if(list.length > 0){
                                return false;
                              }else{
                                return true;
                              }
                          }
                          }
                    },
                     {input: '#edit-phyIbmhost-cpuNumber', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                     {input: '#edit-phyIbmhost-cpuNumber', message: '只能保留一位小数，请重新输入', action: 'keyup, blur', rule: function (input, commit) {
                        if(/[^\d]/g.test(input.val())){
                            return false;
                          }else{
                            return true;
                          }
                        }
                      },
                     {input: '#edit-phyIbmhost-memorySize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                     {input: '#edit-phyIbmhost-memorySize', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
                        if(/[^\d]/g.test(input.val())){
                            return false;
                          }else{
                            return true;
                          }
                        }
                      },

              {input: '#edit-phyIbmhost-remoteMgtIp1', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
             var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                if(input.val() == null || input.val() == ""){
                              return true;
                            }else{
                              if(!pattern.test(input.val())){
                                return false;
                              }else{
                                return true;
                              }
                            }
                          }
             },
             {input: '#edit-phyIbmhost-remoteMgtIp2', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
             var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                if(input.val() == null || input.val() == ""){
                              return true;
                            }else{
                              if(!pattern.test(input.val())){
                                return false;
                              }else{
                                return true;
                              }
                            }
                          }
             },
             {input: '#edit-phyIbmhost-relevanceIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
             var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
                if(input.val() == null || input.val() == ""){
                              return true;
                            }else{
                              if(!pattern.test(input.val())){
                                return false;
                              }else{
                                return true;
                              }
                            }
                          }
             },
             { input: '#edit-phyIbmhost-warrantyPeriod', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
                 if(input.val() == null || input.val() == ""){
                          return true;
                        }else{
                          if(/[^\d]/g.test(input.val())){
                            return false;
                          }else{
                            return true;
                          }
                        }
                  }
               }
            ]
          });
}
}
function refreshServer(){
	  var h = new serverDatagridModels();
	  h.searchServersInfo();
}

function searchServer(){
	  var lb = new serverDatagridModels();
	  lb.searchObj["qm.hostEquipNameLike"] = $("#search-serverName").val();
    lb.searchObj["qm.serialNumberLike"] = $("#search-serverName-serialNumber").val();
	  lb.searchObj["qm.equipRoomSid"] = $("#search-server-room").val()=="全部"?"":$("#search-server-room").val();
    lb.searchObj["qm.equipCabinetSid"] = $("#search-server-cabinet").val()=="全部"?"":$("#search-server-cabinet").val();
    lb.searchObj["qm.equipRackSid"] = $("#search-server-rack").val()=="全部"?"":$("#search-server-rack").val();
	  lb.searchServersInfo();
}
function addServer(){
     var localData = [
         {label:"X86",value:"00"},
         {label:"IBM",value:"01"},
      ];
     var source ={
       datatype: "json",
       datafields: [
           { name: 'label' },
           { name: 'value' }
       ],
       localData:localData
     };
   var dataAdapter = new $.jqx.dataAdapter(source);
 
   $("#search-phyhost-choose").jqxDropDownList({
       selectedIndex: 0, source: dataAdapter, autoDropDownHeight: true, displayMember: "label", valueMember: "value",theme:currentTheme, width: 150, height: 22
   });
  document.getElementById("add-phyhost-ibm").style.display="none";
  document.getElementById("add-phyhost-x86").style.display="";
    $("#add-phyhost-hostName").val("");
    $("#add-phyhost-hostIp").val("");
    $("#add-phyhost-cpuNumber").val("");
    $("#add-phyhost-cpuType").val("");
    $("#add-phyhost-cpuCores").val("");
    $("#add-phyhost-managementPwd").val("");
    $("#add-phyhost-managementUser").val("");
    $("#add-phyhost-memorySize").val("");
    $("#add-phyhost-locationNumber").val("");
    $("#add-phyhost-name").val("");
    $("#add-phyhost-equipType").val("");
    $("#add-phyhost-serialNumber").val("");
    $("#add-phyhost-brand").val("");
    $("#add-phyhost-model").val("");
    $("#add-phyhost-resTopologySid").val("");
    $("#add-phyhost-spec").val("");
    $("#add-phyhost-description").val("");
    $("#add-phyhost-remoteMgtIp1").val("");
    $("#add-phyhost-remoteMgtIp2").val("");
    $("#add-phyhost-relevanceIp").val("");
    $("#add-phyhost-remoteMgtPwd").val("");
    $("#add-phyhost-remoteMgtUser").val("");
    $("#add-phyhost-maintCompany").val("");
    $("#add-phyhost-maintTel").val("");
    $("#add-phyhost-purchaseDate").val("");
    $("#add-phyhost-startdate").val("");
    $("#add-phyhost-warrantyPeriod").val("");
    $("#add-phyhost-maintTel").val("");
    $("#add-phyhost-equipSupplier").val("");

    $("#add-phyIbmhost-hostName").val("");
    $("#add-phyIbmhost-hostIp").val("");
    $("#add-phyIbmhost-cpuNumber").val("");
    $("#add-phyIbmhost-cpuType").val("");
    $("#add-phyIbmhost-managementPwd").val("");
    $("#add-phyIbmhost-viosPwd").val("");
    $("#add-phyIbmhost-viosUser").val("");
    $("#add-phyIbmhost-managementUser").val("");
    $("#add-phyIbmhost-memorySize").val("");
    $("#add-phyIbmhost-locationNumber").val("");
    $("#add-phyIbmhost-name").val("");
    $("#add-phyIbmhost-equipType").val("");
    $("#add-phyIbmhost-serialNumber").val("");
    $("#add-phyIbmhost-brand").val("");
    $("#add-phyIbmhost-model").val("");
    $("#add-phyIbmhost-resTopologySid").val("");
    $("#add-phyIbmhost-spec").val("");
    $("#add-phyIbmhost-description").val("");
    $("#add-phyIbmhost-remoteMgtIp1").val("");
    $("#add-phyIbmhost-remoteMgtIp2").val("");
    $("#add-phyIbmhost-relevanceIp").val("");
    $("#add-phyIbmhost-remoteMgtPwd").val("");
    $("#add-phyIbmhost-remoteMgtUser").val("");
    $("#add-phyIbmhost-maintCompany").val("");
    $("#add-phyIbmhost-maintTel").val("");
    $("#add-phyIbmhost-purchaseDate").val("");
    $("#add-phyIbmhost-startEndDate").val("");
    $("#add-phyIbmhost-warrantyPeriod").val("");
    $("#add-phyIbmhost-maintTel").val("");
    $("#add-phyIbmhost-equipSupplier").val("");
	var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
	    codeadd.getCustomCode("add-phyhost-equipCategory","/system/getCodesByEquipCategory","codeDisplay","codeValue",false,"POST",{codeCategory:"EQUIP_CATEGORY"});
        codeadd.getCustomCode("add-phyIbmhost-equipCategory","/system/getCodesByEquipCategory","codeDisplay","codeValue",false,"POST",{codeCategory:"EQUIP_CATEGORY"});
	    codeadd.getCommonCode("add-phyhost-hostOsType","OS_VERSION",false);
        codeadd.getCommonCode("add-phyIbmhost-hostOsType","OS_VERSION",false);
       // codeadd.getCommonCode("add-phyhost-cpuType","CPU_TYPE",false);
       // codeadd.getCommonCode("add-phyIbmhost-cpuType","CPU_TYPE",false);
    var windowW = $(window).width(); 
    var windowH = $(window).height(); 
	  $("#addphyHostWindow").jqxWindow({ position: { x: (windowW-1000)/2, y: (windowH-600)/2 } });
      $("#addphyHostWindow").jqxWindow('open');
}

function submitAddPhyHost(){
	// var isOk = $('#add-phyhost-x86').jqxValidator('validate');

	var lb = new cabinetDatagridModels();
	$("#add-phyhost-resTopologySid").val(lb.searchObj.parentTopologySid);
    $("#add-phyIbmhost-resTopologySid").val(lb.searchObj.parentTopologySid);
    var phyhost = $("#search-phyhost-choose").val();
    var obj = {};
    if("00"==phyhost){
      document.getElementById("add-phyhost-x86").style.display="";
      document.getElementById("add-phyhost-ibm").style.display="none";
      var isOk = $('#add-phyhost-x86').jqxValidator('validate');
  
	  obj.resTopologySid = $("#add-phyhost-resTopologySid").val(lb.searchObj.parentTopologySid);
    obj.hostName = $("#add-phyhost-hostName").val();
    obj.hostIp = $("#add-phyhost-hostIp").val();
    obj.hostOsType = $("#add-phyhost-hostOsType").val();
    obj.cpuNumber = $("#add-phyhost-cpuNumber").val();
    obj.cpuCores = $("#add-phyhost-cpuCores").val();
    obj.cpuType = $("#add-phyhost-cpuType").val();
    obj.memorySize = $("#add-phyhost-memorySize").val();
    obj.managementUser = $("#add-phyhost-managementUser").val();
    obj.managementPwd = $("#add-phyhost-managementPwd").val();
    obj.equipCategory = $("#add-phyhost-equipCategory").val();
    obj.equipType = $("#add-phyhost-equipType").val();
    obj.name = $("#add-phyhost-name").val();
    obj.brand = $("#add-phyhost-brand").val();
    obj.model = $("#add-phyhost-model").val();
    obj.serialNumber = $("#add-phyhost-serialNumber").val();
    obj.locationNumber = $("#add-phyhost-locationNumber").val();
    obj.equipRoomSid = $("#add-phyhost-equipRoomSid").val();
    obj.equipCabinetSid = $("#add-phyhost-equipCabinetSid").val();
    obj.equipRackSid = $("#add-phyhost-equipRackSid").val();
    obj.spec = $("#add-phyhost-spec").val();
    obj.description = $("#add-phyhost-description").val();
    obj.remoteMgtIp1 = $("#add-phyhost-remoteMgtIp1").val();
    obj.remoteMgtIp2 = $("#add-phyhost-remoteMgtIp2").val();
    obj.relevanceIp = $("#add-phyhost-relevanceIp").val();
    obj.remoteMgtUser = $("#add-phyhost-remoteMgtUser").val();
    obj.remoteMgtPwd = $("#add-phyhost-remoteMgtPwd").val();
    obj.maintTel = $("#add-phyhost-maintTel").val();
    obj.maintCompany = $("#add-phyhost-maintCompany").val();
    obj.warrantyPeriod = $("#add-phyhost-warrantyPeriod").val();
    obj.startEndDate = $("#add-phyhost-startdate").val();
    obj.purchaseDate = $("#add-phyhost-purchaseDate").val();
    obj.equipSupplier = $("#add-phyhost-equipSupplier").val();
    obj.phyhost = phyhost;
    }else if("01"==phyhost){
      document.getElementById("add-phyhost-x86").style.display="none";
      document.getElementById("add-phyhost-ibm").style.display="";
    var isOkibm = $('#add-phyhost-ibm').jqxValidator('validate');
	  obj.resTopologySid = $("#add-phyIbmhost-resTopologySid").val(lb.searchObj.parentTopologySid);
    obj.hostName = $("#add-phyIbmhost-hostName").val();
    obj.hostIp = $("#add-phyIbmhost-hostIp").val();
    obj.hostOsType = $("#add-phyIbmhost-hostOsType").val();
    obj.cpuNumber = $("#add-phyIbmhost-cpuNumber").val();
    obj.cpuCores = $("#add-phyIbmhost-cpuCores").val();
    obj.cpuType = $("#add-phyIbmhost-cpuType").val();
    obj.memorySize = $("#add-phyIbmhost-memorySize").val();
    obj.managementUser = $("#add-phyIbmhost-managementUser").val();
    obj.managementPwd = $("#add-phyIbmhost-managementPwd").val();
    obj.isViosFlag = $("#add-phyIbmhost-isVios").val();
    obj.user = $("#add-phyIbmhost-viosUser").val();
    obj.password = $("#add-phyIbmhost-viosPwd").val();
    obj.equipCategory = $("#add-phyIbmhost-equipCategory").val();
    obj.equipType = $("#add-phyIbmhost-equipType").val();
    obj.name = $("#add-phyIbmhost-name").val();
    obj.brand = $("#add-phyIbmhost-brand").val();
    obj.model = $("#add-phyIbmhost-model").val();
    obj.serialNumber = $("#add-phyIbmhost-serialNumber").val();
    obj.locationNumber = $("#add-phyIbmhost-locationNumber").val();
    obj.equipRoomSid = $("#add-phyIbmhost-equipRoomSid").val();
    obj.equipCabinetSid = $("#add-phyIbmhost-equipCabinetSid").val();
    obj.equipRackSid = $("#add-phyIbmhost-equipRackSid").val();
    obj.spec = $("#add-phyIbmhost-spec").val();
    obj.description = $("#add-phyIbmhost-description").val();
    obj.remoteMgtIp1 = $("#add-phyIbmhost-remoteMgtIp1").val();
    obj.remoteMgtIp2 = $("#add-phyIbmhost-remoteMgtIp2").val();
    obj.relevanceIp = $("#add-phyIbmhost-relevanceIp").val();
    obj.remoteMgtUser = $("#add-phyIbmhost-remoteMgtUser").val();
    obj.remoteMgtPwd = $("#add-phyIbmhost-remoteMgtPwd").val();
    obj.maintTel = $("#add-phyIbmhost-maintTel").val();
    obj.maintCompany = $("#add-phyIbmhost-maintCompany").val();
    obj.warrantyPeriod = $("#add-phyIbmhost-warrantyPeriod").val();
    obj.startEndDate = $("#add-phyIbmhost-startEndDate").val();
    obj.equipSupplier = $("#add-phyIbmhost-equipSupplier").val();
    obj.phyhost = phyhost;
  }
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phycomputes/create/server",
			params :obj,
			callback : function (data) {
			   var house = new serverDatagridModels();
			   house.searchServersInfo();
			   $("#addphyHostWindow").jqxWindow('close');
		    }
	  });
	}else if(isOkibm){
    Core.AjaxRequest({
      url : ws_url + "/rest/phycomputes/create/server",
      params :obj,
      callback : function (data) {
         var house = new serverDatagridModels();
         house.searchServersInfo();
         $("#addphyHostWindow").jqxWindow('close');
        }
    });
  }
}
function editServer(){
  var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
  codeadd.getCustomCode("edit-phyhost-equipCategory","/system/getCodesByEquipCategory","codeDisplay","codeValue",false,"POST",{codeCategory:"EQUIP_CATEGORY"});
  codeadd.getCustomCode("edit-phyIbmhost-equipCategory","/system/getCodesByEquipCategory","codeDisplay","codeValue",false,"POST",{codeCategory:"EQUIP_CATEGORY"});
  codeadd.getCommonCode("edit-phyhost-hostOsType","OS_VERSION",false);
  codeadd.getCommonCode("edit-phyIbmhost-hostOsType","OS_VERSION",false);
 //codeadd.getCommonCode("edit-phyhost-cpuType","CPU_TYPE",false);
 //codeadd.getCommonCode("edit-phyIbmhost-cpuType","CPU_TYPE",false);
  var rowindex = $('#serverdatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#editSBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#serverdatagrid').jqxGrid('getrowdata', rowindex);
		console.log(JSON.stringify(data));
     x86HostName = data.hostName;
     x86HostIp = data.hostIp;
    var isViosFlag = data.isViosFlag;
    if (null==isViosFlag) {
      document.getElementById("edit-phyhost-x86").style.display="";
      document.getElementById("edit-phyhost-ibm").style.display="none";
      $("#edit-phyhost-hostName").val(data.hostName);
      $("#edit-phyhost-hostIp").val(data.hostIp);
      $("#edit-phyhost-hostOsType").val(data.hostOsType);
      $("#edit-phyhost-cpuNumber").val(data.cpuNumber);
      $("#edit-phyhost-cpuType").val(data.cpuType);
      $("#edit-phyhost-cpuCores").val(data.cpuCores);
      $("#edit-phyhost-memorySize").val(data.memorySize);
      $("#edit-phyhost-managementUser").val(data.managementUser);
      $("#edit-phyhost-managementPwd").val(data.managementPwd);
      $("#edit-phyhost-equipCategory").val(data.equipCategory);
      $("#edit-phyhost-equipType").val(data.equipType);
      $("#edit-phyhost-name").val(data.name);
      $("#edit-phyhost-brand").val(data.brand);
      $("#edit-phyhost-model").val(data.model);
      $("#edit-phyhost-serialNumber").val(data.serialNumber);
      $("#edit-phyhost-locationNumber").val(data.locationNumber);
      $("#edit-phyhost-equipRoomSid").val(data.equipRoomSid);
      $("#edit-phyhost-equipCabinetSid").val(data.equipCabinetSid);
      $("#edit-phyhost-equipRackSid").val(data.equipRackSid);
      $("#edit-phyhost-resTopologySid").val(data.resTopologySid);
      $("#edit-phyhost-description").val(data.description);
      $("#edit-phyhost-remoteMgtIp1").val(data.remoteMgtIp1);
      $("#edit-phyhost-remoteMgtIp2").val(data.remoteMgtIp2);
      $("#edit-phyhost-relevanceIp").val(data.relevanceIp);
      $("#edit-phyhost-remoteMgtUser").val(data.remoteMgtUser);
      $("#edit-phyhost-remoteMgtPwd").val(data.remoteMgtPwd);
//      $("#edit-phyhost-viosPassWord").val(data.viosPassWord);
//      $("#edit-phyhost-viosUser").val(data.viosUser);
     // $("#edit-phyhost-maintCompany").val(data.maintCompany);
     // $("#edit-phyhost-maintTel").val(data.maintTel);
     // $("#edit-phyhost-purchaseDate").val(data.purchaseDate);
     // $("#edit-phyhost-warrantyPeriod").val(data.warrantyPeriod);
     // $("#edit-phyhost-startdate").val(data.startdate);
     // $("#edit-phyhost-equipSupplier").val(data.equipSupplier);
     // $("#edit-phyhost-cpuCores").val(data.cpuCores);

      var equipSid = data.resHostSid;
      Core.AjaxRequest({
      url : ws_url + "/rest/phycomputes/findMaintenanceServer/"+equipSid,
      type : "get",
      async : false,
      callback : function(result) {
        if(null==result){
            $("#edit-phyhost-maintCompany").val("");
            $("#edit-phyhost-maintTel").val("");
            $("#edit-phyhost-purchaseDate").val("");
            $("#edit-phyhost-startEndDate").val("");
            $("#edit-phyhost-spec").val("");
            $("#edit-phyhost-description").val("");
            $("#edit-phyhost-warrantyPeriod").val("");
            $("#edit-phyhost-equipSupplier").val("");
        }else{
            $("#edit-phyhost-maintCompany").val(result.maintCompany);
            $("#edit-phyhost-maintTel").val(result.maintTel);
            $("#edit-phyhost-purchaseDate").val(result.purchaseDate);
            $("#edit-phyhost-startEndDate").val(result.startDate);
            $("#edit-phyhost-spec").val(result.spec);
            $("#edit-phyhost-description").val(result.description);
            $("#edit-phyhost-warrantyPeriod").val(result.warrantyPeriod);
            $("#edit-phyhost-equipSupplier").val(result.equipSupplier);
        } 
      }
    });
      var windowW = $(window).width(); 
      var windowH = $(window).height(); 
      $("#editphyHostWindow").jqxWindow({ position: { x: (windowW-1000)/2, y: (windowH-600)/2 } });
      $("#editphyHostWindow").jqxWindow('open');
    }else{
      document.getElementById("edit-phyhost-x86").style.display="none";
      document.getElementById("edit-phyhost-ibm").style.display="";
      $("#edit-phyIbmhost-hostName").val(data.hostName);
      $("#edit-phyIbmhost-hostIp").val(data.hostIp);
      $("#edit-phyIbmhost-hostOsType").val(data.hostOsType);
      $("#edit-phyIbmhost-cpuNumber").val(data.cpuNumber);
      $("#edit-phyIbmhost-cpuType").val(data.cpuType);
      $("#edit-phyIbmhost-memorySize").val(data.memorySize);
      $("#edit-phyIbmhost-managementUser").val(data.managementUser);
      $("#edit-phyIbmhost-managementPwd").val(data.managementPwd);
      $("#edit-phyIbmhost-equipCategory").val(data.equipCategory);
      $("#edit-phyIbmhost-equipType").val(data.equipType);
      $("#edit-phyIbmhost-name").val(data.name);
      $("#edit-phyIbmhost-brand").val(data.brand);
      $("#edit-phyIbmhost-model").val(data.model);
      if(data.serialNumber!=null){
    	  $("#edit-phyIbmhost-serialNumber").val(data.serialNumber);
      }else{
    	  $("#edit-phyIbmhost-serialNumber").val(data.phySerialNumber);
      }
      //$("#edit-phyIbmhost-serialNumber").val(data.serialNumber);
      $("#edit-phyIbmhost-locationNumber").val(data.locationNumber);
      $("#edit-phyIbmhost-equipRoomSid").val(data.equipRoomSid);
      $("#edit-phyIbmhost-equipCabinetSid").val(data.equipCabinetSid);
      $("#edit-phyIbmhost-equipRackSid").val(data.equipRackSid);
      $("#edit-phyIbmhost-resTopologySid").val(data.resTopologySid);
      $("#edit-phyIbmhost-description").val(data.description);
      $("#edit-phyIbmhost-remoteMgtIp1").val(data.remoteMgtIp1);
      $("#edit-phyIbmhost-remoteMgtIp2").val(data.remoteMgtIp2);
      $("#edit-phyIbmhost-relevanceIp").val(data.relevanceIp);
      $("#edit-phyIbmhost-remoteMgtUser").val(data.remoteMgtUser);
      $("#edit-phyIbmhost-remoteMgtPwd").val(data.remoteMgtPwd);
      $("#edit-phyIbmhost-viosPwd").val(data.viosPassWord);
      $("#edit-phyIbmhost-viosUser").val(data.viosUser);
      $("#edit-phyIbmhost-isVios").val(data.isViosFlag);
      // $("#edit-phyIbmhost-maintCompany").val(data.maintCompany);
      // $("#edit-phyIbmhost-maintTel").val(data.maintTel);
      // $("#edit-phyIbmhost-purchaseDate").val(data.purchaseDate);
      // $("#edit-phyIbmhost-warrantyPeriod").val(data.warrantyPeriod);
      // $("#edit-phyIbmhost-startEndDate").val(data.startEndDate);
      // $("#edit-phyIbmhost-equipSupplier").val(data.equipSupplier);
      var equipSid = data.resHostSid;

      Core.AjaxRequest({
      url : ws_url + "/rest/phycomputes/findMaintenanceServer/"+equipSid,
      type : "get",
      async : false,
      callback : function(result) {
        if(null==result){
          $("#edit-phyIbmhost-maintCompany").val("");
          $("#edit-phyIbmhost-maintTel").val("");
          $("#edit-phyIbmhost-purchaseDate").val("");
          $("#edit-phyIbmhost-startEndDate").val("");
          $("#edit-phyIbmhost-spec").val("");
          $("#edit-phyIbmhost-description").val("");
          $("#edit-phyIbmhost-warrantyPeriod").val("");
          $("#edit-phyIbmhost-equipSupplier").val("");
        }else{
          $("#edit-phyIbmhost-maintCompany").val(result.maintCompany);
          $("#edit-phyIbmhost-maintTel").val(result.maintTel);
          $("#edit-phyIbmhost-purchaseDate").val(result.purchaseDate);
          $("#edit-phyIbmhost-startEndDate").val(result.startEndDate);
          $("#edit-phyIbmhost-spec").val(result.spec);
          $("#edit-phyIbmhost-description").val(result.description);
          $("#edit-phyIbmhost-warrantyPeriod").val(result.warrantyPeriod);
          $("#edit-phyIbmhost-equipSupplier").val(result.equipSupplier);
        }
      }
    });
      var windowW = $(window).width(); 
      var windowH = $(window).height(); 
      $("#editphyHostWindow").jqxWindow({ position: { x: (windowW-1000)/2, y: (windowH-600)/2 } });
      $("#editphyHostWindow").jqxWindow('open');
    }
	}
}

function submitEditPhyHost(){
	// var isOk = $('#add-phyhost-X86').jqxValidator('validate');
 //  var isOkibm = $('#add-phyhost-ibm').jqxValidator('validate');
	var lb = new serverDatagridModels();
  var rowindex = $('#serverdatagrid').jqxGrid('getselectedrowindex');
  var data = $('#serverdatagrid').jqxGrid('getrowdata', rowindex);
  var json = {};
  if (null==data.isViosFlag) {
        var isOk = $('#edit-phyhost-x86').jqxValidator('validate');
        json.phyhost = "00";
        json.resTopologySid = $("#edit-phyhost-resTopologySid").val(lb.searchObj.parentTopologySid);
        json.hostName = $("#edit-phyhost-hostName").val();
        json.resHostSid = data.resHostSid;
        json.hostIp = $("#edit-phyhost-hostIp").val();
        json.hostOsType = $("#edit-phyhost-hostOsType").val();
        json.cpuNumber = $("#edit-phyhost-cpuNumber").val();
        json.cpuType = $("#edit-phyhost-cpuType").val();
        json.cpuCores = $("#edit-phyhost-cpuCores").val();
        json.memorySize = $("#edit-phyhost-memorySize").val();
        json.managementUser = $("#edit-phyhost-managementUser").val();
        json.managementPwd = $("#edit-phyhost-managementPwd").val();
        json.equipCategory = $("#edit-phyhost-equipCategory").val();
        json.equipType = $("#edit-phyhost-equipType").val();
        json.name = $("#edit-phyhost-name").val();
        json.model = $("#edit-phyhost-model").val();
        json.brand = $("#edit-phyhost-brand").val();
        json.serialNumber = $("#edit-phyhost-serialNumber").val();
        json.locationNumber = $("#edit-phyhost-locationNumber").val();
        json.equipRoomSid = $("#edit-phyhost-equipRoomSid").val();
        json.equipCabinetSid = $("#edit-phyhost-equipCabinetSid").val();
        json.equipRackSid = $("#edit-phyhost-equipRackSid").val();
        json.resTopologySid = $("#edit-phyhost-resTopologySid").val();
        json.description = $("#edit-phyhost-description").val();
        json.remoteMgtIp1 = $("#edit-phyhost-remoteMgtIp1").val();
        json.remoteMgtIp2 = $("#edit-phyhost-remoteMgtIp2").val();
        json.relevanceIp = $("#edit-phyhost-relevanceIp").val();
        json.remoteMgtUser = $("#edit-phyhost-remoteMgtUser").val();
        json.maintCompany = $("#edit-phyhost-maintCompany").val();
        json.maintTel = $("#edit-phyhost-maintTel").val();
        json.purchaseDate = $("#edit-phyhost-purchaseDate").val();
        json.startEndDate = $("#edit-phyhost-startEndDate").val();
        json.spec = $("#edit-phyhost-spec").val();
        json.description = $("#edit-phyhost-description").val();
        json.warrantyPeriod = $("#edit-phyhost-warrantyPeriod").val();
        json.equipSupplier = $("#edit-phyhost-equipSupplier").val();
  }else if(null!=data.isViosFlag){
        var isOkibm = $('#edit-phyhost-ibm').jqxValidator('validate');
        json.phyhost = "01";
       // json.isViosFlag = data.isViosFlag;
        //json.isViosFlag = $("#edit-phyIbmhost-isVios").val();
        json.resTopologySid = $("#edit-phyIbmhost-resTopologySid").val(lb.searchObj.parentTopologySid);
        json.hostName = $("#edit-phyIbmhost-hostName").val();
        json.resHostSid = data.resHostSid;
        json.hostIp = $("#edit-phyIbmhost-hostIp").val();
        json.hostOsType = $("#edit-phyIbmhost-hostOsType").val();
        json.cpuNumber = $("#edit-phyIbmhost-cpuNumber").val();
        json.cpuType = $("#edit-phyIbmhost-cpuType").val();
        json.memorySize = $("#edit-phyIbmhost-memorySize").val();
        json.managementUser = $("#edit-phyIbmhost-managementUser").val();
        json.managementPwd = $("#edit-phyIbmhost-managementPwd").val();
        json.equipCategory = $("#edit-phyIbmhost-equipCategory").val();
        json.equipType = $("#edit-phyIbmhost-equipType").val();
        json.name = $("#edit-phyIbmhost-name").val();
        json.brand = $("#edit-phyIbmhost-brand").val();
        json.model = $("#edit-phyIbmhost-model").val();
        json.serialNumber = $("#edit-phyIbmhost-serialNumber").val();
        json.locationNumber = $("#edit-phyIbmhost-locationNumber").val();
        json.equipRoomSid = $("#edit-phyIbmhost-equipRoomSid").val();
        json.equipCabinetSid = $("#edit-phyIbmhost-equipCabinetSid").val();
        json.equipRackSid = $("#edit-phyIbmhost-equipRackSid").val();
        json.resTopologySid = $("#edit-phyIbmhost-resTopologySid").val();
        json.description = $("#edit-phyIbmhost-description").val();
        json.remoteMgtIp1 = $("#edit-phyIbmhost-remoteMgtIp1").val();
        json.remoteMgtIp2 = $("#edit-phyIbmhost-remoteMgtIp2").val();
        json.relevanceIp = $("#edit-phyIbmhost-relevanceIp").val();
        json.remoteMgtUser = $("#edit-phyIbmhost-remoteMgtUser").val();
        json.remoteMgtPwd = $("#edit-phyIbmhost-remoteMgtPwd").val();
        json.viosUser = $("#edit-phyIbmhost-viosUser").val();
        json.viosPassWord = $("#edit-phyIbmhost-viosPwd").val();
        //json.isVios = $("#edit-phyIbmhost-isVios").val();
        json.isViosFlag = $("#edit-phyIbmhost-isVios").val();
        json.maintCompany = $("#edit-phyIbmhost-maintCompany").val();
        json.maintTel = $("#edit-phyIbmhost-maintTel").val();
        json.purchaseDate = $("#edit-phyIbmhost-purchaseDate").val();
        json.startEndDate = $("#edit-phyIbmhost-startEndDate").val();
        json.spec = $("#edit-phyIbmhost-spec").val();
        json.description = $("#edit-phyIbmhost-description").val();
        json.warrantyPeriod = $("#edit-phyIbmhost-warrantyPeriod").val();
        json.equipSupplier = $("#edit-phyIbmhost-equipSupplier").val();
  }
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phycomputes/update/server",
			params :json,
			callback : function (data) {
			 var sw = new serverDatagridModels();
			 sw.searchServersInfo();
			 $("#editphyHostWindow").jqxWindow('close');
		    }
	    });
	}else if(isOkibm){
    Core.AjaxRequest({
      url : ws_url + "/rest/phycomputes/update/server",
      params :json,
      callback : function (data) {
       var sw = new serverDatagridModels();
       sw.searchServersInfo();
       $("#editphyHostWindow").jqxWindow('close');
        }
      });
  }
}

function deleteServer(){
  var rowindex = $('#serverdatagrid').jqxGrid('getselectedrowindex');
  var ok =  $('#deleteSBtn').jqxButton('disabled');
  if(!ok && rowindex >= 0){
    var data = $('#serverdatagrid').jqxGrid('getrowdata', rowindex);
    var hostSid = data.resHostSid;
     Core.confirm({
       title:"提示",
         message:"您确定要删除该服务器吗？",
         confirmCallback:function(){
           Core.AjaxRequest({
             url : ws_url + "/rest/phycomputes/delete/server/"+hostSid,
            type:"get",
            callback : function (data) {
               var sw = new serverDatagridModels();
               sw.searchServersInfo();
              },
           });
         }
     });
  }
}

//弹出详情
 function goPhyHostMonitorPage(row){
   var rowData = $('#serverdatagrid').jqxGrid('getrowdata', row);
   if(null==rowData.isViosFlag){
	   document.getElementById("x86ServerWindows").style.display="";
       var windowW = $(window).width();
       var windowH = $(window).height(); 
       $("#x86ServerWindows").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-700)/2 } });
       $("#x86ServerWindows").jqxWindow('open');
   }else{
	   document.getElementById("ibmServerWindows").style.display="none";
       var windowW = $(window).width();
       var windowH = $(window).height(); 
       $("#ibmServerWindows").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-700)/2 } });
       $("#ibmServerWindows").jqxWindow('open');
   }
    var serverDetail = new serverinfoDetailModel();
    serverDetail.popServerDetailInfoWindow(rowData);
 }