var switchDatagridModels = function(){
	
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		"qm.nameLike":null,
        	"qm.resTopologyNameVE":null,
        	"qm.resTopologyNameDC":null,
        	"qm.equipRackSid":null,
        	"qm.resTopologySid":null,
        	"qm.status":null,
        	"qm.findChildBySid":resTopologySid,
          "qm.serialNumberLike":null,
        	 parentTopologySid:resTopologySid
	};
    this.staObj = {
     	     resTopologySid:resTopologySid
     	};
      
    //统计机框资源查询方法
      this.getDataGridData = function(){
      	 var switchData;
      	 Core.AjaxRequest({
  	 			url : ws_url + "/rest/phynetwork/switch/statistical",
  	 			type:'post',
  	 			params:me.staObj,
  	 			async:false,
  	 			callback : function (data) {
  	 				switchData = data;
  	 			}
  	     });
      	 return switchData;
      };
  		this.SwitchResourcesStatistics = function(){
  			var switchData = me.getDataGridData();
  			var data = new Object();
  			data.attr = new Array();
  			data.switchCount = switchData.staTotalSwitch;
  			var value = [switchData.staTotalSwitch];
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
    
    this.initSwitchInput = function(){
    	// 初始化查询组件
        $("#search-switchName").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-switchName-serialNumber").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-switchbutton").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
    
    this.initComboxLinkage = function(){
    	  var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
    	  codeadd.getCustomCode("search-sw-mf","/phylocations/downlist/rack","name","equipSid",true,"POST",{resTopologySid:resTopologySid});
    	//codeadd.getCommonCode("search-sw-status","EQUIPMENT_STATUS",true);
//    	codeadd.getCustomCode("edit-sw-cf-name","/phycomputes/downlist/frame","name","equipCabinetFrameSid",false,"POST",{resTopologySid:resTopologySid});
//    	codeadd.getCustomCode("add-sw-cf-name","/phycomputes/downlist/frame","name","equipCabinetFrameSid",false,"POST",{resTopologySid:resTopologySid});
        codeadd.getCustomCode("add-sw-equipRoomSid","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
        var zoneSid =  $("#add-sw-equipRoomSid").val();
        codeadd.getCustomCode("add-sw-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSid});
        var cabinetSid =  $("#add-sw-equipCabinetSid").val();
        codeadd.getCustomCode("add-sw-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSid});
        var rackSid =  $("#add-sw-equipCabinetSid").val();

        $('#add-sw-equipRoomSid').on('change', function (event){  
            var args = event.args;
            if (args) {
              var item = args.item;
              var value = item.value;
              codeadd.getCustomCode("add-sw-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:value});
              var items = $("#add-sw-equipCabinetSid").jqxDropDownList('getItems');
              if(items.length > 0){
                $("#add-sw-equipCabinetSid").jqxDropDownList('selectItem', items[0] );
              }
            } 
        });
        $('#add-sw-equipCabinetSid').on('change', function (event){
          var args = event.args;
            if (args) {
              var item = args.item;
              var value = item.value;
              codeadd.getCustomCode("add-sw-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:value});
              var items = $("#add-sw-equipRackSid").jqxDropDownList('getItems');
              if(items.length > 0){
                $("#add-sw-equipRackSid").jqxDropDownList('selectItem', items[0] );
              }
            }
        });

        codeadd.getCustomCode("edit-sw-equipRoomSid","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
        var editzoneSid =  $("#edit-sw-equipRoomSid").val();
        codeadd.getCustomCode("edit-sw-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:editzoneSid});
        var editcabinetSid =  $("#edit-sw-equipCabinetSid").val();
        codeadd.getCustomCode("edit-sw-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:editcabinetSid});
        var editrackSid =  $("#edit-sw-equipCabinetSid").val();

         $('#edit-sw-equipRoomSid').on('change', function (event){  
            var args = event.args;
            if (args) {
              var edititem = args.item;
              var editvalue = edititem.value;
              codeadd.getCustomCode("edit-sw-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:editvalue});
              var items = $("#edit-sw-equipCabinetSid").jqxDropDownList('getItems');
              if(items.length > 0){
                $("#edit-sw-equipCabinetSid").jqxDropDownList('selectItem', items[0] );
              }
            } 
        });
        $('#edit-sw-equipCabinetSid').on('change', function (event){
          var args = event.args;
            if (args) {
              var edititem = args.item;
              var editvalue = edititem.value;
              codeadd.getCustomCode("edit-sw-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:editvalue});
              var items = $("#edit-sw-equipRackSid").jqxDropDownList('getItems');
              if(items.length > 0){
                $("#edit-sw-equipRackSid").jqxDropDownList('selectItem', items[0] );
              }
            }
        });

    }
    
    this.initSwitchDatagrid = function(){
    	var dataAdapter = Core.getPagingDataAdapter({
			gridId: "switchdatagrid",
			url: ws_url + "/rest/phynetwork/find/switch",
			params: me.searchObj
		});
    	
        $("#switchdatagrid").jqxGrid({
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
                  { text: '交换机编号', datafield: 'name',cellsalign:'left'},
                  /*{ text: '设备分类', datafield: 'equipCategoryName',cellsalign:'left'},*/   
                  { text: '设备类型', datafield: 'equipType',cellsalign:'left'},
                  { text: '序列号', datafield: 'serialNumber',cellsalign:'left'},
                  { text: '品牌', datafield: 'brand',cellsalign:'left'},
                  { text: '型号', datafield: 'model',cellsalign:'left'},
                  { text: '所属机柜', datafield: 'equipCabinetName',cellsalign:'left'},
                  { text: '所属机架', datafield: 'equipRackName',cellsalign:'left'},
                  { text: '远程管理IP1', datafield: 'remoteMgtIp1',cellsalign:'left'},    
                  { text: '远程管理IP2', datafield: 'remoteMgtIp2',cellsalign:'left'},    
                  { text: '关联IP地址', datafield: 'relevanceIp',cellsalign:'left'},
                  { text: '操作', datafield: '',align: 'center',cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
                       return "<div align='center' style='width:90px;height:22px;line-height:25px;'>&nbsp;&nbsp;<i onclick='goPhySwMonitorPage("+row+")' title='详情' class='icons-blue icon-popup'></i></div>";
                      },width:80
              }
            ],
            showtoolbar: true,
            // 设置toolbar操作按钮
            rendertoolbar: function (toolbar) {
                var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
//                var refreshBtn = $("<div><a id='refreshSWBtn' onclick='refreshSW()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                var addBtn = $("<div><a id='addSWBtn' onclick='addSW()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>增加&nbsp;&nbsp;</a></div>");
                var editBtn = $("<div><a id='editSWBtn' onclick='editSW()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                var deleteBtn = $("<div><a id='deleteSWBtn' onclick='deleteSW()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                var refreshBtn = $("<div><a id='refreshSWBtn' onclick='refreshSW()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                toolbar.append(container);
//              container.append(refreshBtn);
                container.append(addBtn);
                container.append(editBtn);
                container.append(deleteBtn);
                container.append(refreshBtn);
                $("#refreshSWBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#addSWBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#editSWBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#deleteSWBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
            }
        });
  };
  
  this.initPopWindow = function(){
	  $("#addSWWindow").jqxWindow({
		  width: 1000, 
          height:500,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#swCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	$("#add-sw-locationNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-equipType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-name").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-serialNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-brand").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-model").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#add-sw-spec").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            //$("#add-sw-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#add-sw-resTopologySid").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#add-sw-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-remoteMgtPwd").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-maintCompany").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-sw-purchaseDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#add-sw-startEndDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#swSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            $("#swCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
          }
	  });
	  $("#editSWWindow").jqxWindow({
		  width: 1000, 
          height:500,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#sweditCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	  $("#edit-sw-locationNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-equipType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-name").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-serialNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-brand").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-model").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#edit-sw-spec").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
           // $("#edit-sw-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#edit-sw-resTopologySid").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#edit-sw-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-remoteMgtPwd").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-maintCompany").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-sw-purchaseDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#edit-sw-startEndDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#sweditSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            $("#sweditCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
          }
	  });

    $("#switchWindows").jqxWindow({
	      width: 800, 
	      height:600,
	      theme:currentTheme,  
	      resizable: false,  
	      isModal: true, 
	      autoOpen: false, 
	      cancelButton: $("#switch-close-button"), 
	      modalOpacity: 0.3,
	      initContent:function(){
	        // 初始化新增用户页面组件
	      $("#switch-close-button").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
	      }
     });
  };
  
  this.searchSwitchInfo = function(){
	  $("#switchdatagrid").jqxGrid("gotopage",0);
		var dataAdapter = Core.getPagingDataAdapter({
			gridId: "switchdatagrid",
			url : ws_url + "/rest/phynetwork/find/switch",
			params: me.searchObj
		});         
		$("#switchdatagrid").jqxGrid({
			source: dataAdapter,
			rendergridrows: function(){
				return dataAdapter.records;
			}
		});
  };
  
  this.initValue = function(){
	  $('#addSWForm').jqxValidator({
			 rules : [ 
             {input: '#add-sw-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input : '#add-sw-equipType',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#add-sw-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input : '#add-sw-name',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#add-sw-remoteMgtIp1', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#add-sw-remoteMgtIp2', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#add-sw-relevanceIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             { input: '#add-sw-warrantyPeriod', message: '只能输入数字', action: 'keyup', rule: function (input, commit) {
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
                {input: '#add-sw-maintTel', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
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
	  $('#editSWForm').jqxValidator({
			rules : [ 
             {input: '#edit-sw-equipType', message: '长度不能大于32个字符', action: 'keyup, blur', rule: 'required' },
             {input : '#edit-sw-equipType',message : '不能为空!',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#edit-sw-name', message: '长度不能大于32个字符', action: 'keyup, blur', rule: 'required' },
             {input : '#edit-sw-name',message : '不能为空!',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#edit-sw-remoteMgtIp1', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#edit-sw-remoteMgtIp2', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#edit-sw-relevanceIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             { input: '#edit-sw-warrantyPeriod', message: '只能输入数字', action: 'keyup', rule: function (input, commit) {
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
               {input: '#edit-sw-maintTel', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
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

function refreshSW(){
	 var sw = new switchDatagridModels();
	 sw.searchSwitchInfo();
}

function searchSwitch(){
	 var sw = new switchDatagridModels();
	 sw.searchObj["qm.nameLike"] = $("#search-switchName").val();
   sw.searchObj["qm.serialNumberLike"] = $("#search-switchName-serialNumber").val();
	 sw.searchObj["qm.equipRackSid"] = $("#search-sw-mf").val()=="全部"?"":$("#search-sw-mf").val();
	// sw.searchObj["qm.status"] = $("#search-sw-status").val()=="全部"?"":$("#search-sw-status").val();
	 sw.searchSwitchInfo();
}
function addSW(){
  $("#add-sw-locationNumber").val("");
  $("#add-sw-equipType").val("");
  $("#add-sw-name").val("");
  $("#add-sw-serialNumber").val("");
  $("#add-sw-brand").val("");
  $("#add-sw-model").val("");
  $("#add-sw-description").val("");
  $("#add-sw-spec").val("");
  //$("#add-sw-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
  $("#add-sw-resTopologySid").val("");
  $("#add-sw-remoteMgtIp1").val("");
  $("#add-sw-remoteMgtIp2").val("");
  $("#add-sw-relevanceIp").val("");
  $("#add-sw-remoteMgtUser").val("");
  $("#add-sw-remoteMgtPwd").val("");
  $("#add-sw-maintCompany").val("");
  $("#add-sw-maintTel").val("");
  $("#add-sw-warrantyPeriod").val("");
  $("#add-sw-equipSupplier").val("");
  $("#add-sw-purchaseDate").val("");
  $("#add-sw-startEndDate").val("");
  /* var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
   codeadd.getCustomCode("add-sw-equipRackSid","/phylocations/downlist/rack","name","equipSid",false,"POST",{resTopologySid:resTopologySid});*/
    var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
	$("#addSWWindow").jqxWindow({ position: { x: (windowW-1000)/2, y: (windowH-500)/2 } });
  	$("#addSWWindow").jqxWindow('open');
}

function submitAddSW(){
	var isOk = $('#addSWForm').jqxValidator('validate');
	var lb = new cabinetDatagridModels();
	$("#add-sw-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#addSWForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phynetwork/create/switch",
			params :lh,
			callback : function (data) {
			   var sw = new switchDatagridModels();
			   sw.searchSwitchInfo();
			   $("#addSWWindow").jqxWindow('close');
		    }
	  });
	}
}

function editSW(){
    var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
    codeadd.getCustomCode("edit-sw-equipRackSid","/phylocations/downlist/rack","name","equipSid",false,"POST",{resTopologySid:resTopologySid});
    var rowindex = $('#switchdatagrid').jqxGrid('getselectedrowindex');
	  var ok =  $('#editSWBtn').jqxButton('disabled');
  	if(!ok && rowindex >= 0){
  		var data = $('#switchdatagrid').jqxGrid('getrowdata', rowindex);
      $("#edit-sw-locationNumber").val(data.locationNumber);
      $("#edit-sw-name").val(data.name);
      $("#edit-sw-equipType").val(data.equipType);
      $("#edit-sw-serialNumber").val(data.serialNumber);
      $("#edit-sw-brand").val(data.brand);
      $("#edit-sw-model").val(data.model);
      $("#edit-sw-remoteMgtIp1").val(data.remoteMgtIp1);
      $("#edit-sw-remoteMgtIp2").val(data.remoteMgtIp2);
      $("#edit-sw-relevanceIp").val(data.relevanceIp);
      $("#edit-sw-remoteMgtUser").val(data.remoteMgtUser);
      $("#edit-sw-remoteMgtPwd").val(data.remoteMgtPwd);  
      $("#edit-sw-equipRoomSid").val(data.equipRoomSid);
      $("#edit-sw-equipCabinetSid").val(data.equipCabinetSid);
      $("#edit-sw-equipRackSid").val(data.equipRackSid);
       var equipSwitchSid = data.equipSwitchSid;
        Core.AjaxRequest({
        url : ws_url + "/rest/phynetwork/findMaintenanceRack/"+equipSwitchSid,
        type : "get",
        async : false,
        callback : function(result) {
          $("#edit-sw-maintCompany").val(result.maintCompany);
          $("#edit-sw-maintTel").val(result.maintTel);
          $("#edit-sw-warrantyPeriod").val(result.warrantyPeriod);
          $("#edit-sw-equipSupplier").val(result.equipSupplier);
          $("#edit-sw-spec").val(result.spec);
          $("#edit-sw-description").val(result.description);
          $("#edit-sw-startEndDate").val(result.startEndDate);
          $("#edit-sw-purchaseDate").val(result.purchaseDate);
        }
      });
	    var windowW = $(window).width(); 
  	  var windowH = $(window).height(); 
	    $("#editSWWindow").jqxWindow({ position: { x: (windowW-1000)/2, y: (windowH-500)/2 } });
  	  $("#editSWWindow").jqxWindow('open');
	}
}
function submitEditSW(){
 
	var isOk = $('#editSWForm').jqxValidator('validate');
	var lb = new switchDatagridModels();
  var rowindex = $('#switchdatagrid').jqxGrid('getselectedrowindex');
  var data = $('#switchdatagrid').jqxGrid('getrowdata', rowindex);
  $("#edit-sw-equipSwitchSid").val(data.equipSwitchSid);
	$("#edit-sw-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#editSWForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phynetwork/update/switch",
			type:"post",
			params :lh,
			callback : function (data) {
				 var cab = new switchDatagridModels();
				 cab.searchSwitchInfo();
				 $("#editSWWindow").jqxWindow('close');
		    }
	    });
	}
}

function deleteSW(){
	var rowindex = $('#switchdatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#deleteSWBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#switchdatagrid').jqxGrid('getrowdata', rowindex);
		var equipSwitchSid = data.equipSwitchSid;
		 Core.confirm({
			 title:"提示",
		     message:"您确定要删除该交换机吗？",
		     confirmCallback:function(){
		    	 Core.AjaxRequest({
		    		 url : ws_url + "/rest/phynetwork/delete/switch/"+equipSwitchSid,
		 				type:"get",
		 				callback : function (data) {
		 					 var sw = new switchDatagridModels();
		 					 sw.searchSwitchInfo();
		 			    },
		    	 });
		     }
		 });
	}
}

//弹出详情
 function goPhySwMonitorPage(row){
   var rowData = $('#switchdatagrid').jqxGrid('getrowdata', row);
   var windowW = $(window).width();
   var windowH = $(window).height(); 
   $("#switchWindows").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-600)/2 } });
   $("#switchWindows").jqxWindow('open');
   var switchDetail = new switchDetailModel();
   switchDetail.popSwitchDetailInfoWindow(rowData);
 }
 


