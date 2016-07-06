var sanDatagridModels = function(){
	
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		  "qm.nameLike":null,
          "qm.equipRackSid":null,
        	"qm.resTopologyNameVE":null,
        	"qm.resTopologyNameDC":null,
        	"qm.equipCabinetFrameSid":null,
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
     	 var storageData;
     	 Core.AjaxRequest({
 	 			url : ws_url + "/rest/phystorages/storage/statistical",
 	 			type:'post',
 	 			params:me.staObj,
 	 			async:false,
 	 			callback : function (data) {
 	 				storageData = data;
 	 			}
 	     });
     	 return storageData;
     };
 		this.StorageResourcesStatistics = function(){
 			var storageData = me.getDataGridData();
 			var data = new Object();
 			data.attr = new Array();
 			data.storageCount = storageData.staTotalStorage;
 			var value = [storageData.staTotalStorage];
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
    
    this.initSANInput = function(){
    	// 初始化查询组件
        $("#search-sanName").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-sanName-serialNumber").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-sanButton").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
    this.initComboxLinkage = function(){
    	 var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
         codeadd.getCustomCode("search-san-mf","/phylocations/downlist/rack","name","equipSid",true,"POST",{resTopologySid:resTopologySid});


          codeadd.getCustomCode("add-san-equipRoomSid","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
        var zoneSid =  $("#add-san-equipRoomSid").val();
        codeadd.getCustomCode("add-san-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSid});
        var cabinetSid =  $("#add-san-equipCabinetSid").val();
        codeadd.getCustomCode("add-san-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSid});
        var rackSid =  $("#add-san-equipCabinetSid").val();

        $('#add-san-equipRoomSid').on('change', function (event){  
            var args = event.args;
            if (args) {
              var item = args.item;
              var value = item.value;
              codeadd.getCustomCode("add-san-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:value});
            } 
        });
        $('#add-san-equipCabinetSid').on('change', function (event){
          var args = event.args;
            if (args) {
              var item = args.item;
              var value = item.value;
              codeadd.getCustomCode("add-san-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:value});
              var items = $("#add-san-equipRackSid").jqxDropDownList('getItems');
              if(items.length > 0){
                $("#add-san-equipRackSid").jqxDropDownList('selectItem', items[0] );
              }
            }
        });

        codeadd.getCustomCode("edit-san-equipRoomSid","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
        var editzoneSid =  $("#edit-san-equipRoomSid").val();
        codeadd.getCustomCode("edit-san-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:editzoneSid});
        var editcabinetSid =  $("#edit-san-equipCabinetSid").val();
        codeadd.getCustomCode("edit-san-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:editcabinetSid});
        var editrackSid =  $("#edit-san-equipCabinetSid").val();

         $('#edit-san-equipRoomSid').on('change', function (event){  
            var args = event.args;
            if (args) {
              var edititem = args.item;
              var editvalue = edititem.value;
              codeadd.getCustomCode("edit-san-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:editvalue});
            } 
        });
        $('#edit-san-equipCabinetSid').on('change', function (event){
          var args = event.args;
            if (args) {
              var edititem = args.item;
              var editvalue = edititem.value;
              codeadd.getCustomCode("edit-san-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:editvalue});
              var items = $("#edit-san-equipRackSid").jqxDropDownList('getItems');
              if(items.length > 0){
                $("#edit-san-equipRackSid").jqxDropDownList('selectItem', items[0] );
              }
            }
        });
    }
    
    this.searchSANInfo = function(){
  	  $("#sandatagrid").jqxGrid("gotopage",0);
  		var dataAdapter = Core.getPagingDataAdapter({
  			gridId: "sandatagrid",
  			url : ws_url + "/rest/phystorages/find/storage",
  			params: me.searchObj
  		});         
  		$("#sandatagrid").jqxGrid({
  			source: dataAdapter,
  			rendergridrows: function(){
  				return dataAdapter.records;
  			}
  		});
    };
    
    this.initSANDatagrid = function(){
    	var dataAdapter = Core.getPagingDataAdapter({
			gridId: "sandatagrid",
			url: ws_url + "/rest/phystorages/find/storage",
			params: me.searchObj
		});
        $("#sandatagrid").jqxGrid({
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
                  { text: ' 存储编号', datafield: 'name',cellsalign:'left'},
                  // { text: '设备分类', datafield: 'equipCategoryName',cellsalign:'left'},   
                  { text: '设备类型', datafield: 'equipType',cellsalign:'left'},
                  { text: '序列号', datafield: 'serialNumber',cellsalign:'left'},
                  { text: '品牌', datafield: 'brand',cellsalign:'left'},
                  { text: '型号', datafield: 'model',cellsalign:'left'},
                  { text: '所属机架', datafield: 'equipRackName',cellsalign:'left'},
                  { text: '容量(GB)', datafield: 'capacity',cellsalign:'left'},
                  { text: '远程管理IP1', datafield: 'remoteMgtIp1',cellsalign:'left'},    
                  { text: '远程管理IP2', datafield: 'remoteMgtIp2',cellsalign:'left'},    
                  { text: '关联IP地址', datafield: 'relevanceIp',cellsalign:'left'},
                  { text: '操作', datafield: '',align: 'center',cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
                       return "<div style='width:100px;height:22px;line-height:25px;'>&nbsp;&nbsp;<i onclick='goPhyStorageMonitorPage("+row+")' title='详情' class='icons-blue icon-popup'></i></div>";
                      },width:80  }
            ],
            showtoolbar: true,
            // 设置toolbar操作按钮
            rendertoolbar: function (toolbar) {
                var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
//                var refreshBtn = $("<div><a id='jqxRefreshSANBtn' onclick='refreshSan()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                var addBtn = $("<div><a id='addSANBtn' onclick='addSan()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>增加&nbsp;&nbsp;</a></div>");
                var editBtn = $("<div><a id='editSANBtn' onclick='editSan()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                var deleteBtn = $("<div><a id='deleteSANBtn' onclick='deleteSan()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                var refreshBtn = $("<div><a id='jqxRefreshSANBtn' onclick='refreshSan()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                toolbar.append(container);
//                container.append(refreshBtn);
                container.append(addBtn);
                container.append(editBtn);
                container.append(deleteBtn);
                container.append(refreshBtn);
                $("#jqxRefreshSANBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#addSANBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#editSANBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#deleteSANBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
            }
        });
  };
  
  this.initPopWindow = function(){
	  $("#addSanWindow").jqxWindow({
		  width: 1000, 
          height:500,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#sanCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	  $("#add-san-locationNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-equipType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-name").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-serialNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-brand").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-model").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#add-san-spec").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#add-san-resTopologySid").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#add-san-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-remoteMgtPwd").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-maintCompany").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-capacity").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-san-purchaseDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#add-san-startEndDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#sanSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            $("#sanCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
          }
	  });
	  $("#editSanWindow").jqxWindow({
		  width: 1000, 
          height:500,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#sanEditCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	  $("#edit-san-locationNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-equipType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-name").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-serialNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-brand").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-model").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#edit-san-spec").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#edit-san-resTopologySid").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#edit-san-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-remoteMgtPwd").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-maintCompany").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-capacity").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-san-purchaseDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#edit-san-startEndDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#sanEditSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            $("#sanEditCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
//  	      
          }
	  });
    $("#storageWindows").jqxWindow({
              width: 800, 
              height:600,
              theme:currentTheme,  
              resizable: false,  
              isModal: true, 
              autoOpen: false, 
              cancelButton: $("#storage-close-button"), 
              modalOpacity: 0.3,
              initContent:function(){
                // 初始化新增用户页面组件
              $("#storage-close-button").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
              }
     });
  };
  
  this.initValue = function(){
	  $('#addSanForm').jqxValidator({
			 rules : [ 
                {input: '#add-san-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                {input : '#add-san-equipType',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
                {input: '#add-san-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                {input : '#add-san-name',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#add-san-remoteMgtIp1', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#add-san-remoteMgtIp2', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#add-san-relevanceIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             { input: '#add-san-warrantyPeriod', message: '只能输入数字', action: 'keyup', rule: function (input, commit) {
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
                { input: '#add-san-capacity', message: '只能输入数字', action: 'keyup', rule: function (input, commit) {
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
               {input: '#add-san-maintTel', message: '只能输入数字', action: 'keyup', rule: function (input, commit) {
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
	  $('#editSanForm').jqxValidator({
			rules : [ 
                {input: '#edit-san-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                {input : '#edit-san-equipType',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
                {input: '#edit-san-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                {input : '#edit-san-name',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
                    {input: '#add-san-remoteMgtIp1', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#edit-san-remoteMgtIp2', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#edit-san-relevanceIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#edit-san-warrantyPeriod', message: '只能输入数字', action: 'keyup', rule: function (input, commit) {
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
              {input: '#edit-san-capacity', message: '只能输入数字', action: 'keyup', rule: function (input, commit) {
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
               {input: '#edit-san-maintTel', message: '只能输入数字', action: 'keyup', rule: function (input, commit) {
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

function refreshSan(){
	 var sw = new sanDatagridModels();
	 sw.searchSANInfo();
}

function searchSAN(){
	 var sw = new sanDatagridModels();
	 sw.searchObj["qm.nameLike"] = $("#search-sanName").val();
   sw.searchObj["qm.serialNumberLike"] = $("#search-sanName-serialNumber").val();
	 sw.searchObj["qm.equipRackSid"] = $("#search-san-mf").val()=="全部"?"":$("#search-san-mf").val();
	 sw.searchSANInfo();
}

function addSan(){
  $("#add-san-locationNumber").val("");
  $("#add-san-equipType").val("");
  $("#add-san-name").val("");
  $("#add-san-serialNumber").val("");
  $("#add-san-brand").val("");
  $("#add-san-model").val("");
  $("#add-san-description").val("");
  $("#add-san-spec").val("");
  $("#add-san-resTopologySid").val("");
  $("#add-san-remoteMgtIp1").val("");
  $("#add-san-remoteMgtIp2").val("");
  $("#add-san-relevanceIp").val("");
  $("#add-san-remoteMgtUser").val("");
  $("#add-san-remoteMgtPwd").val("");
  $("#add-san-maintCompany").val("");
  $("#add-san-maintTel").val("");
  $("#add-san-warrantyPeriod").val("");
  $("#add-san-equipSupplier").val("");
  $("#add-san-capacity").val("");
  $("#add-san-purchaseDate").val("");
  $("#add-san-startEndDate").val("");
	// var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
 //  codeadd.getCustomCode("add-san-equipRackSid","/phylocations/downlist/rack","name","equipSid",false,"POST",{resTopologySid:resTopologySid});
	var windowW = $(window).width(); 
  var windowH = $(window).height(); 
	$("#addSanWindow").jqxWindow({ position: { x: (windowW-1000)/2, y: (windowH-500)/2 } });
  $("#addSanWindow").jqxWindow('open');
}

function submitAddSan(){
	var isOk = $('#addSanForm').jqxValidator('validate');
	var lb = new sanDatagridModels();
	$("#add-san-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#addSanForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phystorages/create/storage",
			params :lh,
			callback : function (data) {
			   var sw = new sanDatagridModels();
			   sw.searchSANInfo();
			   $("#addSanWindow").jqxWindow('close');
		    }
	  });
	}
}

function editSan(){ 
  // var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
  // codeadd.getCustomCode("edit-san-equipRackSid","/phylocations/downlist/rack","name","equipSid",false,"POST",{resTopologySid:resTopologySid});
	var rowindex = $('#sandatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#editSANBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#sandatagrid').jqxGrid('getrowdata', rowindex);
		  $("#edit-san-locationNumber").val(data.locationNumber);
	    $("#edit-san-name").val(data.name);
	    $("#edit-san-equipType").val(data.equipType);
	    $("#edit-san-serialNumber").val(data.serialNumber);
	    $("#edit-san-brand").val(data.brand);
	    $("#edit-san-model").val(data.model);
	    $("#edit-san-remoteMgtIp1").val(data.remoteMgtIp1);
	    $("#edit-san-remoteMgtIp2").val(data.remoteMgtIp2);
	    $("#edit-san-relevanceIp").val(data.relevanceIp);
	    $("#edit-san-remoteMgtUser").val(data.remoteMgtUser);
	    $("#edit-san-remoteMgtPwd").val(data.remoteMgtPwd); 
	    $("#edit-san-capacity").val(data.capacity);
      $("#edit-san-equipRoomSid").val(data.equipRoomSid);
      $("#edit-san-equipCabinetSid").val(data.equipCabinetSid);
      $("#edit-san-equipRackSid").val(data.equipRackSid);

	      var equipStorageSid = data.equipSid;
	      Core.AjaxRequest({
	      url : ws_url + "/rest/phystorages/findMaintenanceSan/"+ equipStorageSid,
	      type : "get",
	      async : false,
	      callback : function(result) {
	        $("#edit-san-maintCompany").val(result.maintCompany);
	        $("#edit-san-maintTel").val(result.maintTel);
	        $("#edit-san-warrantyPeriod").val(result.warrantyPeriod);
	        $("#edit-san-equipSupplier").val(result.equipSupplier);
	        $("#edit-san-spec").val(result.spec);
	        $("#edit-san-description").val(result.description);
          $("#edit-firewall-startEndDate").val(result.startEndDate);
          $("#edit-firewall-purchaseDate").val(result.purchaseDate);
	      }
	    });
		var windowW = $(window).width(); 
	    var windowH = $(window).height(); 
		$("#editSanWindow").jqxWindow({ position: { x: (windowW-1000)/2, y: (windowH-500)/2 } });
	    $("#editSanWindow").jqxWindow('open');
	}
}

function submitEditSan(){
	var isOk = $('#editSanForm').jqxValidator('validate');
	var lb = new labHouseDatagridModels();
    var rowindex = $('#sandatagrid').jqxGrid('getselectedrowindex');
    var data = $('#sandatagrid').jqxGrid('getrowdata', rowindex);
  $("#edit-san-equipStorageSid").val(data.equipSid);
	$("#edit-san-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#editSanForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phystorages/update/storage",
			params :lh,
			callback : function (data) {
				 var cab = new sanDatagridModels();
				 cab.searchSANInfo();
				 $("#editSanWindow").jqxWindow('close');
		    }
	    });
	}
}

function deleteSan(){
	var rowindex = $('#sandatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#deleteSANBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#sandatagrid').jqxGrid('getrowdata', rowindex);
		var equipStorageSid = data.equipSid;
		Core.confirm({
			 title:"提示",
		     message:"您确定要删除该存储吗？",
		     confirmCallback:function(){
		    	 Core.AjaxRequest({
					url : ws_url + "/rest/phystorages/delete/storage/"+equipStorageSid,
	 				type:"get",
	 				callback : function (data) {
	 					 var cab = new sanDatagridModels();
	 					 cab.searchSANInfo();
	 			    },
		 		});
		     }
		     
		});
	}
}

function goPhyStorageMonitorPage(row){
   var rowData = $('#sandatagrid').jqxGrid('getrowdata', row);
   var windowW = $(window).width();
   var windowH = $(window).height(); 
   $("#storageWindows").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-600)/2 } });
   $("#storageWindows").jqxWindow('open');
   var storage = new storageDetailModel();
   storage.popStorageDetailInfoWindow(rowData);

}
