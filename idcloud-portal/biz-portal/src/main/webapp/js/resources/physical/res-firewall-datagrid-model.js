var firewallDatagridModels = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
            "qm.nameLike":null,
    		"qm.managementIpLike":null,
        	"qm.resTopologyNameVE":null,
        	"qm.resTopologyNameDC":null,
        	"qm.resTopologySid":null,
        	"qm.equipCabinetFrameSid":null,
            "qm.equipRackSid":null,
        	"qm.findChildBySid":resTopologySid,
          "qm.serialNumberLike":null,
        	 parentTopologySid:resTopologySid
	};
    
    this.staObj = {
    	     resTopologySid:resTopologySid
    	};
     
   //统计机框资源查询方法
     this.getDataGridData = function(){
     	 var firewallData;
     	 Core.AjaxRequest({
 	 			url : ws_url + "/rest/phynetwork/firewall/statistical",
 	 			type:'post',
 	 			params:me.staObj,
 	 			async:false,
 	 			callback : function (data) {
 	 				firewallData = data;
 	 			}
 	     });
     	 return firewallData;
     };
 		this.FirewallResourcesStatistics = function(){
 			var firewallData = me.getDataGridData();
 			var data = new Object();
 			data.attr = new Array();
 			data.firewallCount = firewallData.staTotalFirewall;
 			var value = [firewallData.staTotalFirewall];
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
   
    this.initFirewallInput = function(){
    	// 初始化查询组件
        $("#search-firewall-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-firewall-serialNumber").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-firewallbutton").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
    this.initComboxLinkage = function(){
    	var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
    	codeadd.getCustomCode("search-firewall-rack","/phylocations/downlist/rack","name","equipSid",true,"POST",{resTopologySid:resTopologySid});
//    	codeadd.getCustomCode("add-firewall-cf-name","/phycomputes/downlist/frame","name","equipCabinetFrameSid",false,"POST",{resTopologySid:resTopologySid});
//    	codeadd.getCustomCode("edit-firewall-cf-name","/phycomputes/downlist/frame","name","equipCabinetFrameSid",false,"POST",{resTopologySid:resTopologySid});

        codeadd.getCustomCode("add-firewall-equipRoomSid","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
        var zoneSid =  $("#add-firewall-equipRoomSid").val();
        codeadd.getCustomCode("add-firewall-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSid});
        var cabinetSid =  $("#add-firewall-equipCabinetSid").val();
        codeadd.getCustomCode("add-firewall-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSid});
        var rackSid =  $("#add-firewall-equipCabinetSid").val();

        $('#add-firewall-equipRoomSid').on('change', function (event){  
            var args = event.args;
            if (args) {
              var item = args.item;
              var value = item.value;
              codeadd.getCustomCode("add-firewall-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:value});
            } 
        });
        $('#add-firewall-equipCabinetSid').on('change', function (event){
          var args = event.args;
            if (args) {
              var item = args.item;
              var value = item.value;
              codeadd.getCustomCode("add-firewall-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:value});
              var items = $("#add-firewall-equipRackSid").jqxDropDownList('getItems');
              if(items.length > 0){
                $("#add-firewall-equipRackSid").jqxDropDownList('selectItem', items[0] );
              }
            }
        });

        codeadd.getCustomCode("edit-firewall-equipRoomSid","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
        var editzoneSid =  $("#edit-firewall-equipRoomSid").val();
        codeadd.getCustomCode("edit-firewall-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:editzoneSid});
        var editcabinetSid =  $("#edit-firewall-equipCabinetSid").val();
        codeadd.getCustomCode("edit-firewall-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:editcabinetSid});
        var editrackSid =  $("#edit-firewall-equipCabinetSid").val();

         $('#edit-firewall-equipRoomSid').on('change', function (event){  
            var args = event.args;
            if (args) {
              var edititem = args.item;
              var editvalue = edititem.value;
              codeadd.getCustomCode("edit-firewall-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:editvalue});
            } 
        });
        $('#edit-firewall-equipCabinetSid').on('change', function (event){
          var args = event.args;
            if (args) {
              var edititem = args.item;
              var editvalue = edititem.value;
              codeadd.getCustomCode("edit-firewall-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:editvalue});
              var items = $("#edit-firewall-equipRackSid").jqxDropDownList('getItems');
              if(items.length > 0){
                $("#edit-firewall-equipRackSid").jqxDropDownList('selectItem', items[0] );
              }
            }
        });
    }
    
    this.initFirewallDatagrid = function(){
    	
    	var dataAdapter = Core.getPagingDataAdapter({
			gridId: "switchdatagrid",
			url: ws_url + "/rest/phynetwork/find/firewall",
			params: me.searchObj
		});
        $("#firewalldatagrid").jqxGrid({
        	width: "99.8%",
			theme:currentTheme,
			source: dataAdapter,
			virtualmode: true,
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
				  { text: '位置编号', datafield: 'locationNumber',cellsalign:'left'},
                  { text: '防火墙编号', datafield: 'name',cellsalign:'left'},
                  // { text: '设备分类', datafield: 'equipCategoryName',cellsalign:'left'},   
                  { text: '设备类型', datafield: 'equipType',cellsalign:'left'},
                  { text: '序列号', datafield: 'serialNumber',cellsalign:'left'},
                  { text: '品牌', datafield: 'brand',cellsalign:'left'},
                  { text: '型号', datafield: 'model',cellsalign:'left'},
                  { text: '所属机架', datafield: 'equipRackName',cellsalign:'left'},
                  { text: '远程管理IP1', datafield: 'remoteMgtIp1',cellsalign:'left'},    
                  { text: '远程管理IP2', datafield: 'remoteMgtIp2',cellsalign:'left'},    
                  { text: '关联IP地址', datafield: 'relevanceIp',cellsalign:'left'},
                  { text: '操作', datafield: '',align: 'center',cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
                       return "<div  align='center' style='width:85px;height:22px;line-height:25px;'>&nbsp;&nbsp;<i onclick='goPhydirewallMonitorPage("+row+")' title='详情' class='icons-blue icon-popup'></i></div>";
                      },width:80  }
            ],
            showtoolbar: true,
            // 设置toolbar操作按钮
            rendertoolbar: function (toolbar) {
                var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
//                var refreshBtn = $("<div><a id='refreshFWBtn' onclick='refreshFireWall()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                var addBtn = $("<div><a id='addFWBtn' onclick='addFireWall()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>增加&nbsp;&nbsp;</a></div>");
                var editBtn = $("<div><a id='editFWBtn' onclick='editFireWall()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                var deleteBtn = $("<div><a id='deleteFWBtn' onclick='deleteFireWall()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                var refreshBtn = $("<div><a id='refreshFWBtn' onclick='refreshFireWall()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                toolbar.append(container);
                container.append(addBtn);
                container.append(editBtn);
                container.append(deleteBtn);
                container.append(refreshBtn);
                $("#refreshFWBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#addFWBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#editFWBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#deleteFWBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
            }
        });
  };
  
  this.initPopWindow = function(){
	  $("#addfirewallWindow").jqxWindow({
		  width: 1000, 
          height:500,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#firewallCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	  $("#add-firewall-locationNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-equipType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-name").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-serialNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-brand").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-model").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#add-firewall-spec").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            //$("#add-sw-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#add-firewall-resTopologySid").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#add-firewall-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-remoteMgtPwd").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-maintCompany").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-firewall-purchaseDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#add-firewall-startEndDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#firewallSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            $("#firewallCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
//  	        var codeadd = new codeModel({width:300,autoDropDownHeight:true,dropDownWidth:300});
//  	    	codeadd.getCustomCode("add-firewall-cf-name","/phycomputes/downlist/frame","name","equipCabinetFrameSid",false,"POST",{resTopologySid:resTopologySid});
          }
	  });
	  $("#editfirewallWindow").jqxWindow({
		      width: 1000, 
          height:500,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#firewallEditCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	  $("#edit-firewall-locationNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-equipType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-name").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-serialNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-brand").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-model").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#edit-firewall-spec").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#edit-firewall-resTopologySid").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#edit-firewall-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-remoteMgtPwd").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-maintCompany").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-firewall-purchaseDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#edit-firewall-startEndDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#firewallEditSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            $("#firewallEditCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
          }
	  });

    $("#firewallWindows").jqxWindow({
              width: 800, 
              height:600,
              theme:currentTheme,  
              resizable: false,  
              isModal: true, 
              autoOpen: false, 
              cancelButton: $("#firewall-close-button"), 
              modalOpacity: 0.3,
              initContent:function(){
                // 初始化新增用户页面组件
              $("#firewall-close-button").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
              }
     });
  };
  
  this.searchFirewallInfo = function(){
	  $("#firewalldatagrid").jqxGrid("gotopage",0);
		var dataAdapter = Core.getPagingDataAdapter({
			gridId: "firewalldatagrid",
			url : ws_url + "/rest/phynetwork/find/firewall",
			params: me.searchObj
		});         
		$("#firewalldatagrid").jqxGrid({
			source: dataAdapter,
			rendergridrows: function(){
				return dataAdapter.records;
			}
		});
  };
  
  this.initValue = function(){
	  $('#addfirewallForm').jqxValidator({
			 rules : [ 
                {input: '#add-firewall-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                {input : '#add-firewall-equipType',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
                {input: '#add-firewall-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                {input : '#add-firewall-name',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#add-firewall-remoteMgtIp1', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#add-firewall-remoteMgtIp2', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#add-firewall-relevanceIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             { input: '#add-firewall-warrantyPeriod', message: '只能输入数字', action: 'keyup', rule: function (input, commit) {
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
                {input: '#add-firewall-maintTel', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
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
	  $('#editfirewallForm').jqxValidator({
			rules : [ 
                {input: '#edit-firewall-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                {input : '#edit-firewall-equipType',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
                {input: '#edit-firewall-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                {input : '#edit-firewall-name',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
        {input: '#edit-firewall-remoteMgtIp1', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#edit-firewall-remoteMgtIp2', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#edit-firewall-relevanceIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#edit-firewall-warrantyPeriod', message: '只能输入数字', action: 'keyup', rule: function (input, commit) {
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
               {input: '#edit-firewall-maintTel', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
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

function refreshFireWall(){
	 var fw = new firewallDatagridModels();
	 fw.searchFirewallInfo();
}
function searchFirewall(){
	 var fw = new firewallDatagridModels();
	 fw.searchObj["qm.nameLike"] = $("#search-firewall-name").val();
   fw.searchObj["qm.serialNumberLike"] = $("#search-firewall-serialNumber").val();
	 fw.searchObj["qm.equipRackSid"] = $("#search-firewall-rack").val()=="全部"?"":$("#search-firewall-rack").val();
	 fw.searchFirewallInfo();
}
function addFireWall(){
  $("#add-firewall-locationNumber").val("");
  $("#add-firewall-equipType").val("");
  $("#add-firewall-name").val("");
  $("#add-firewall-serialNumber").val("");
  $("#add-firewall-brand").val("");
  $("#add-firewall-model").val("");
  $("#add-firewall-description").val("");
  $("#add-firewall-spec").val("");
  //$("#add-sw-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
  $("#add-firewall-resTopologySid").val("");
  $("#add-firewall-remoteMgtIp1").val("");
  $("#add-firewall-remoteMgtIp2").val("");
  $("#add-firewall-relevanceIp").val("");
  $("#add-firewall-remoteMgtUser").val("");
  $("#add-firewall-remoteMgtPwd").val("");
  $("#add-firewall-maintCompany").val("");
  $("#add-firewall-maintTel").val("");
  $("#add-firewall-warrantyPeriod").val("");
  $("#add-firewall-equipSupplier").val("");
  $("#add-firewall-purchaseDate").val("");
  $("#add-firewall-startEndDate").val("");
	  // var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
   //  codeadd.getCustomCode("add-firewall-equipRackSid","/phylocations/downlist/rack","name","equipSid",false,"POST",{resTopologySid:resTopologySid});
	  var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
	  $("#addfirewallWindow").jqxWindow({ position: { x: (windowW-1000)/2, y: (windowH-500)/2 } });
  	$("#addfirewallWindow").jqxWindow('open');
}
function submitAddfirewall(){
	var isOk = $('#addfirewallForm').jqxValidator('validate');
	var lb = new firewallDatagridModels();
	$("#add-firewall-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#addfirewallForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phynetwork/create/firewall",
			params :lh,
			callback : function (data) {
			   var sw = new firewallDatagridModels();
			   sw.searchFirewallInfo();
			   $("#addfirewallWindow").jqxWindow('close');
		    }
	  });
	}
}
function editFireWall(){
//	var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
//  codeadd.getCustomCode("edit-firewall-equipRackSid","/phylocations/downlist/rack","name","equipSid",false,"POST",{resTopologySid:resTopologySid});
	var rowindex = $('#firewalldatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#editFWBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#firewalldatagrid').jqxGrid('getrowdata', rowindex);
    $("#edit-firewall-locationNumber").val(data.locationNumber);
    $("#edit-firewall-name").val(data.name);
    $("#edit-firewall-equipType").val(data.equipType);
    $("#edit-firewall-serialNumber").val(data.serialNumber);
    $("#edit-firewall-brand").val(data.brand);
    $("#edit-firewall-model").val(data.model);
    $("#edit-firewall-remoteMgtIp1").val(data.remoteMgtIp1);
    $("#edit-firewall-remoteMgtIp2").val(data.remoteMgtIp2);
    $("#edit-firewall-relevanceIp").val(data.relevanceIp);
    $("#edit-firewall-remoteMgtUser").val(data.remoteMgtUser);
    $("#edit-firewall-remoteMgtPwd").val(data.remoteMgtPwd);  
    $("#edit-firewall-equipRoomSid").val(data.equipRoomSid);
    $("#edit-firewall-equipCabinetSid").val(data.equipCabinetSid);
    $("#edit-firewall-equipRackSid").val(data.equipRackSid);

      var equipSid = data.equipSid;
      Core.AjaxRequest({
      url : ws_url + "/rest/phynetwork/findMaintenanceRack/"+equipSid,
      type : "get",
      async : false,
      callback : function(result) {
        $("#edit-firewall-maintCompany").val(result.maintCompany);
        $("#edit-firewall-maintTel").val(result.maintTel);
        $("#edit-firewall-warrantyPeriod").val(result.warrantyPeriod);
        $("#edit-firewall-equipSupplier").val(result.equipSupplier);
        $("#edit-firewall-spec").val(result.spec);
        $("#edit-firewall-description").val(result.description);
        $("#edit-firewall-startEndDate").val(result.startEndDate);
        $("#edit-firewall-purchaseDate").val(result.purchaseDate);
      }
    });
  	
	  var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
	  $("#editfirewallWindow").jqxWindow({ position: { x: (windowW-1000)/2, y: (windowH-500)/2 } });
  	$("#editfirewallWindow").jqxWindow('open');
	}
}
function submitEditfirewall(){
	var isOk = $('#editfirewallForm').jqxValidator('validate');
	var lb = new firewallDatagridModels();
  var rowindex = $('#firewalldatagrid').jqxGrid('getselectedrowindex');
  var data = $('#firewalldatagrid').jqxGrid('getrowdata', rowindex);
  $("#edit-firewall-equipFwSid").val(data.equipSid);
	$("#edit-firewall-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#editfirewallForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phynetwork/update/firewall",
			params :lh,
			callback : function (data) {
				 var cab = new firewallDatagridModels();
				 cab.searchFirewallInfo();
				 $("#editfirewallWindow").jqxWindow('close');
		    }
	    });
	}
}

function deleteFireWall(){
	var rowindex = $('#firewalldatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#deleteFWBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#firewalldatagrid').jqxGrid('getrowdata', rowindex);
		var equipFirewallSid = data.equipSid;
		 Core.confirm({
			 title:"提示",
		     message:"您确定要删除该防火墙吗？",
		     confirmCallback:function(){
		    	 Core.AjaxRequest({
		    		 url : ws_url + "/rest/phynetwork/delete/firewall/"+equipFirewallSid,
		 				type:"get",
		 				callback : function (data) {
		 					 var sw = new firewallDatagridModels();
		 					 sw.searchFirewallInfo();
		 			    },
		    	 });
		     }
		 });
	}
}

function goPhydirewallMonitorPage(row){
   var rowData = $('#firewalldatagrid').jqxGrid('getrowdata', row);
   var windowW = $(window).width();
   var windowH = $(window).height(); 
   $("#firewallWindows").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-600)/2 } });
   $("#firewallWindows").jqxWindow('open');
   var firewall = new firewallDetailModel();
   firewall.popFirewallDetailInfoWindow(rowData);

}