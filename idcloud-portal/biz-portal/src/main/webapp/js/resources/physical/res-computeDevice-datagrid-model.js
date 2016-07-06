var rackequipSid;
var machineFrameDatagridModels = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
		"qm.nameLike":null,
		"qm.resTopologyNameVE":null,
		"qm.resTopologyNameDC":null,
		"qm.equipCabinetSid":null,
		"qm.findChildBySid":resTopologySid,
		"qm.serialNumberLike":null,
		"qm.equipRoomSid":null,
		"qm.equipRackSid":null,
		 parentTopologySid:resTopologySid
	};
    
    this.staObj = {
      	     resTopologySid:resTopologySid
      	};
       
     //统计机框资源查询方法
       this.getDataGridData = function(){
       	 var frameData;
       	 Core.AjaxRequest({
   	 			url : ws_url + "/rest/phycomputes/frames/statistical",
   	 			type:'post',
   	 			params:me.staObj,
   	 			async:false,
   	 			callback : function (data) {
   	 			frameData = data;
   	 			}
   	     });
       	 return frameData;
       };
   		this.FrameResourcesStatistics = function(){
   			var frameData = me.getDataGridData();
   			var data = new Object();
   			data.attr = new Array();
   			data.frameCount = frameData.staTotalFrame;
   			var value = [frameData.staTotalFrame];
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
       
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initMachineFrameInput = function(){
    	// 初始化查询组件
        $("#search-machineFrameName").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-cabinetframe-serialNumber").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-machineFrameButton").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
    this.initComboxLinkage = function(){
    	var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
    	//codeadd.getCustomCode("search-mf-cabinetframe","/phylocations/downlist/cabinet","name","equipSid",true,"POST",{resTopologySid:resTopologySid});
      //codeadd.getCustomCode("search-mf-cabinetframe","/phylocations/downlist/rack","name","equipSid",true,"POST",{resTopologySid:resTopologySid});
//    	codeadd.getCommonCode("search-cabinetframe-type","FRAME_TYPE",true);
//    	codeadd.getCustomCode("add-mf-Cf-name","/phycomputes/downlist/cabinet","name","equipCabinetSid",false,"POST",{resTopologySid:resTopologySid});
//	    codeadd.getCommonCode("add-Cf-type","FRAME_TYPE",true);
//	    codeadd.getCustomCode("edit-mf-Cf-name","/phycomputes/downlist/cabinet","name","equipCabinetSid",false,"POST",{resTopologySid:resTopologySid});
//        codeadd.getCommonCode("edit-Cf-type","FRAME_TYPE",true);
	  codeadd.getCustomCode("search-mf-room","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
	  var zoneSidS =  $("#search-mf-room").val();
	  codeadd.getCustomCode("search-mf-cabinetframe","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSidS});
	  var cabinetSidS =  $("#search-mf-cabinetframe").val();
	  codeadd.getCustomCode("search-mf-rack","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSidS});
    	
    	
      codeadd.getCustomCode("add-Cf-equipRoomSid","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
      var zoneSid =  $("#add-Cf-equipRoomSid").val();
      codeadd.getCustomCode("add-Cf-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSid});
      var cabinetSid =  $("#add-Cf-equipCabinetSid").val();
      codeadd.getCustomCode("add-Cf-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSid});
      
      $('#search-mf-room').on('change', function (event){  
          var args = event.args;
          if (args) {
            var item = args.item;
            var value = item.value;
            codeadd.getCustomCode("search-mf-cabinetframe","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:value});
            var items = $("#search-mf-cabinetframe").jqxDropDownList('getItems');
            if(items.length > 0){
              $("#search-mf-cabinetframe").jqxDropDownList('selectItem', items[0] );
            }
          } 
      });
      $('#search-mf-cabinetframe').on('change', function (event){
        var args = event.args;
          if (args) {
            var item = args.item;
            var value = item.value;
            codeadd.getCustomCode("search-mf-rack","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:value});
            var items = $("#search-mf-rack").jqxDropDownList('getItems');
            if(items.length > 0){
              $("#search-mf-rack").jqxDropDownList('selectItem', items[0] );
            }
            var vesid = items.value;
          }
      });
     
      $('#add-Cf-equipRoomSid').on('change', function (event){  
      var args = event.args;
      if (args) {
        var item = args.item;
        var value = item.value;
        codeadd.getCustomCode("add-Cf-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:value});
        var items = $("#add-Cf-equipCabinetSid").jqxDropDownList('getItems');
        if(items.length > 0){
          $("#add-Cf-equipCabinetSid").jqxDropDownList('selectItem', items[0] );
        }
      } 
      });
      $('#add-Cf-equipCabinetSid').on('change', function (event){
          var args = event.args;
            if (args) {
              var item = args.item;
              var value = item.value;
              codeadd.getCustomCode("add-Cf-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:value});
              var items = $("#add-Cf-equipRackSid").jqxDropDownList('getItems');
              if(items.length > 0){
                $("#add-Cf-equipRackSid").jqxDropDownList('selectItem', items[0] );
              }
            }
        });

      codeadd.getCustomCode("edit-Cf-equipRoomSid","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
      var editzoneSid =  $("#edit-Cf-equipRoomSid").val();
      codeadd.getCustomCode("edit-Cf-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:editzoneSid});
      var editcabinetSid =  $("#edit-Cf-equipCabinetSid").val();
      codeadd.getCustomCode("edit-Cf-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:editcabinetSid});
      $('#edit-Cf-equipRoomSid').on('change', function (event){  
      var args = event.args;
      if (args) {
        var edititem = args.item;
        var editvalue = edititem.value;
        codeadd.getCustomCode("edit-Cf-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:editvalue});
        var items = $("#edit-Cf-equipCabinetSid").jqxDropDownList('getItems');
        if(items.length > 0){
          $("#edit-Cf-equipCabinetSid").jqxDropDownList('selectItem', items[0] );
        }
      } 
      });
       $('#edit-Cf-equipCabinetSid').on('change', function (event){
          var args = event.args;
            if (args) {
              var edititem = args.item;
              var editvalue = edititem.value;
              codeadd.getCustomCode("edit-Cf-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:editvalue});
              var items = $("#edit-Cf-equipRackSid").jqxDropDownList('getItems');
              if(items.length > 0){
                $("#edit-Cf-equipRackSid").jqxDropDownList('selectItem', items[0] );
              }
            }
        });


    }
    this.initMachineFrameDatagrid = function(){
    	var dataAdapter = Core.getPagingDataAdapter({
			gridId: "framedatagrid",
			url: ws_url + "/rest/phycomputes/findframe",
			params: me.searchObj
		});
        $("#framedatagrid").jqxGrid({
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
				{ text: '刀箱编号', datafield: 'name',cellsalign:'left'},
				// { text: '设备分类', datafield: 'equipCategoryName',cellsalign:'left'},   
				{ text: '设备类型', datafield: 'equipType',cellsalign:'left'},
				{ text: '序列号', datafield: 'serialNumber',cellsalign:'left'},
				{ text: '品牌', datafield: 'brand',cellsalign:'left'},
				{ text: '型号', datafield: 'model',cellsalign:'left'},
				{ text: '所属机房', datafield: 'equipRoomName',cellsalign:'left'},
				{ text: '所属机柜', datafield: 'equipCabinetName',cellsalign:'left'},
        { text: '所属机架', datafield: 'equipRackName',cellsalign:'left'},
				{ text: '远程管理IP1', datafield: 'remoteMgtIp1',cellsalign:'left'},    
				{ text: '远程管理IP2', datafield: 'remoteMgtIp2',cellsalign:'left'},    
				{ text: '关联IP地址', datafield: 'relevanceIp',cellsalign:'left'},
				{ text: '操作', datafield: '',align: 'center',cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
					   return "<div align='center' style='width:80px;height:22px;line-height:25px;'>&nbsp;&nbsp;<i onclick='goPhyMonitorPage("+row+")' title='详情' class='icons-blue icon-popup'></i></div>";
				  	},width:80
	            }
             ],
             showtoolbar:true,
          // 设置toolbar操作按钮
             rendertoolbar: function (toolbar) {
               var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
               var addCFBtn = $("<div><a id='jqxAddCFBtn' onclick='addCF()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>增加&nbsp;&nbsp;</a></div>");
               var editCFBtn = $("<div><a id='jqxeditCFBtn' onclick='editCF()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
               var deleteCFBtn = $("<div><a id='jqxdeleteCFBtn' onclick='deleteCF()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
               var refreshCFBtn = $("<div><a id='jqxRefreshCFBtn' onclick='refreshCF()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
               toolbar.append(container);
               container.append(addCFBtn);
               container.append(editCFBtn);
               container.append(deleteCFBtn);
               container.append(refreshCFBtn);
               $("#jqxRefreshCFBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
               $("#jqxAddCFBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
               $("#jqxeditCFBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
               $("#jqxdeleteCFBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
           }
        });
  };
  
  this.searchMachineFrameInfo = function(){
  	$("#framedatagrid").jqxGrid("gotopage",0);
		var dataAdapter = Core.getPagingDataAdapter({
			gridId: "framedatagrid",
			url : ws_url + "/rest/phycomputes/findframe",
			params: me.searchObj
		});      
		
		$("#framedatagrid").jqxGrid({
			source: dataAdapter,
			rendergridrows: function(){
				return dataAdapter.records;
			}
		});
	}
  
  this.initPopWindow = function(){
	  $("#addCfWindow").jqxWindow({
		  width: 1000, 
          height:500,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#addBladeframeCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	  $("#add-Cf-locationNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
        	  $("#add-Cf-equipType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
        	  $("#add-Cf-name").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
        	  $("#add-Cf-serialNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
        	  $("#add-Cf-brand").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
        	  $("#add-Cf-model").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
        	  $("#add-Cf-description").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
        	  $("#add-Cf-spec").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
    			  $("#add-Cf-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
    			  $("#add-Cf-resTopologySid").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
    			  $("#add-Cf-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#add-Cf-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#add-Cf-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#add-Cf-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#add-Cf-remoteMgtPwd").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    			  $("#add-Cf-maintCompany").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#add-Cf-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#add-Cf-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#add-Cf-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#add-Cf-purchaseDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
    			  $("#add-Cf-startdate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
    			  $("#addBladeframeSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
  	        $("#addBladeframeCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
          }
	  });
	  
	  $("#editCfWindow").jqxWindow({
		  width: 1000, 
          height:500,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#cfeditCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	  $("#edit-Cf-locationNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
        	  $("#edit-Cf-equipType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
        	  $("#edit-Cf-name").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
        	  $("#edit-Cf-serialNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
        	  $("#edit-Cf-brand").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
        	  $("#edit-Cf-model").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
        	  $("#edit-Cf-description").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
        	  $("#edit-Cf-spec").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
    			  $("#edit-Cf-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
    			  $("#edit-Cf-resTopologySid").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
    			  $("#edit-Cf-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#edit-Cf-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#edit-Cf-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#edit-Cf-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#edit-Cf-remoteMgtPwd").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    			  $("#edit-Cf-maintCompany").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#edit-Cf-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#edit-Cf-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
    			  $("#edit-Cf-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
			 // $("#edit-Cf-purchaseDate").jqxDateTimeInput({width: '150px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
			      $("#edit-Cf-purchaseDate").jqxDateTimeInput({ height:25, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
			  //$("#edit-Cf-startdate").jqxDateTimeInput({width: '150px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
			      $("#edit-Cf-startEndDate").jqxDateTimeInput({ height:25, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
        	  $("#cfeditSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
  	          $("#cfeditCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
          }
	  });
	  
	  $("#bladeFrameWindows").jqxWindow({
	        width: 800, 
	        height:550,
	        theme:currentTheme,  
	        resizable: false,  
	        isModal: true, 
	        autoOpen: false, 
	        cancelButton: $("#bladefarme-close-button"), 
	        modalOpacity: 0.3,
	        initContent:function(){
	        	// 初始化新增用户页面组件
		    	$("#bladefarme-close-button").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
	        }
	    });
  }
  
  this.initValue = function(){
	   $('#addCfForm').jqxValidator({
		 	rules : [ 
             {input: '#add-Cf-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		 	       {input : '#add-Cf-equipType',message : '长度不能大于32个字符',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#add-Cf-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input : '#add-Cf-name',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#add-Cf-remoteMgtIp1', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#add-Cf-remoteMgtIp2', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#add-Cf-relevanceIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             { input: '#add-Cf-warrantyPeriod', message: '只能输入数字', action: 'keyup', rule: function (input, commit) {
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
               {input: '#add-Cf-maintTel', message: '只能输入数字', action: 'keyup', rule: function (input, commit) {
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
	  $('#editCfForm').jqxValidator({
			rules : [ 

             {input: '#edit-Cf-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input : '#edit-Cf-equipType',message : '长度不能大于32个字符',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#edit-Cf-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input : '#edit-Cf-name',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
			       {input: '#edit-Cf-remoteMgtIp1', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#edit-Cf-remoteMgtIp2', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#edit-Cf-relevanceIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             { input: '#edit-Cf-warrantyPeriod', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
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
               {input: '#edit-Cf-maintTel', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
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


function refreshCF(){
	  var h = new machineFrameDatagridModels();
	  h.searchMachineFrameInfo();
}

function searchMachineFrame(){
	  var lb = new machineFrameDatagridModels();
	  lb.searchObj["qm.nameLike"] = $("#search-machineFrameName").val();
	  lb.searchObj["qm.serialNumberLike"] = $("#search-cabinetframe-serialNumber").val();
	  lb.searchObj["qm.equipCabinetSid"] = $("#search-mf-cabinetframe").val()=="全部"?"":$("#search-mf-cabinetframe").val();
	  lb.searchObj["qm.equipRoomSid"] = $("#search-mf-room").val()=="全部"?"":$("#search-mf-room").val();
	  lb.searchObj["qm.equipRackSid"] = $("#search-mf-rack").val()=="全部"?"":$("#search-mf-rack").val();
	  lb.searchMachineFrameInfo();
}
function addCF(){
    $("#add-Cf-locationNumber").val("");
    $("#add-Cf-equipType").val("");
    $("#add-Cf-name").val("");
    $("#add-Cf-serialNumber").val("");
    $("#add-Cf-brand").val("");
    $("#add-Cf-model").val("");
    $("#add-Cf-description").val("");
    $("#add-Cf-spec").val("");
    $("#add-Cf-description").val("");
    $("#add-Cf-resTopologySid").val("");
    $("#add-Cf-remoteMgtIp1").val("");
    $("#add-Cf-remoteMgtIp2").val("");
    $("#add-Cf-relevanceIp").val("");
    $("#add-Cf-remoteMgtUser").val("");
    $("#add-Cf-remoteMgtPwd").val("");
    $("#add-Cf-maintCompany").val("");
    $("#add-Cf-maintTel").val("");
    $("#add-Cf-warrantyPeriod").val("");
    $("#add-Cf-equipSupplier").val("");
    $("#add-Cf-purchaseDate").val("");
    $("#add-Cf-startdate").val("");
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
	$("#addCfWindow").jqxWindow({ position: { x: (windowW-1000)/2, y: (windowH-500)/2 } });
  	$("#addCfWindow").jqxWindow('open');
}

function submitAddBladeFrame(){
	var isOk = $('#addCfForm').jqxValidator('validate');
	var lb = new machineFrameDatagridModels();
	$("#add-Cf-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#addCfForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phycomputes/create/cabinetFrame",
			params :lh,
			callback : function (data) {
				 var house = new machineFrameDatagridModels();
				 house.searchMachineFrameInfo();
				 $("#addCfWindow").jqxWindow('close');
		    }
	  });
	}
}

function editCF(){
	// var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
	// codeadd.getCustomCode("edit-Cf-equipCabinetSid","/phycomputes/downlist/cabinet","name","equipSid",false,"POST",{resTopologySid:resTopologySid});
	var rowindex = $('#framedatagrid').jqxGrid('getselectedrowindex');
	var data = $('#framedatagrid').jqxGrid('getrowdata', rowindex);
	var equipSid = data.equipSid;
	var ok =  $('#jqxeditCFBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
  		$("#edit-Cf-locationNumber").val(data.locationNumber);
		$("#edit-Cf-name").val(data.name);
		$("#edit-Cf-equipType").val(data.equipType);
		$("#edit-Cf-equipCategoryName").val(data.equipCategoryName);
		$("#edit-Cf-serialNumber").val(data.serialNumber);
		$("#edit-Cf-brand").val(data.brand);
		$("#edit-Cf-model").val(data.model);
		$("#edit-Cf-equipCabinetName").val(data.equipCabinetName);
		$("#edit-Cf-spec").val(data.spec);
		$("#edit-Cf-description").val(data.description);
		$("#edit-Cf-remoteMgtIp1").val(data.remoteMgtIp1);
		$("#edit-Cf-remoteMgtIp2").val(data.remoteMgtIp2);
		$("#edit-Cf-relevanceIp").val(data.relevanceIp);
		$("#edit-Cf-remoteMgtUser").val(data.remoteMgtUser);
		$("#edit-Cf-remoteMgtPwd").val(data.remoteMgtPwd);
		$("#edit-Cf-equidSid").val(data.equidSid);
    $("#edit-Cf-equipRoomSid").val(data.equipRoomSid);
    $("#edit-Cf-equipCabinetSid").val(data.equipCabinetSid);
    $("#edit-Cf-equipRackSid").val(data.equipRackSid);
  		
  		rackequipSid = data.equipSid;
  		Core.AjaxRequest({
			url : ws_url + "/rest/phycomputes/findMaintenance/"+equipSid,
			type : "get",
			async : false,
			callback : function(result) {
				$("#edit-Cf-maintCompany").val(result.maintCompany);
				$("#edit-Cf-maintTel").val(result.maintTel);
				$("#edit-Cf-purchaseDate").val(result.purchaseDate);
				$("#edit-Cf-startEndDate").val(result.startEndDate);
				$("#edit-Cf-warrantyPeriod").val(result.warrantyPeriod);
				$("#edit-Cf-equipSupplier").val(result.equipSupplier);
			}
		});

  		var windowW = $(window).width(); 
		var windowH = $(window).height(); 
		$("#editCfWindow").jqxWindow({ position: { x: (windowW-1000)/2, y: (windowH-500)/2 } });
		$("#editCfWindow").jqxWindow('open');
	}
}
function submitEditCf(){
	var isOk = $('#editCfForm').jqxValidator('validate');
	var lb = new machineFrameDatagridModels();
	var rowindex = $('#framedatagrid').jqxGrid('getselectedrowindex');
	var data = $('#framedatagrid').jqxGrid('getrowdata', rowindex);
	$("#edit-Cf-equidSid").val(data.equipSid);
	$("#edit-Cf-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#editCfForm").serializeJson());
//	var rowindex = $('#framedatagrid').jqxGrid('getselectedrowindex');
	
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phycomputes/update/cabinetFrame",
			type:"post",
			params :lh,
			callback : function (data) {
				 var house = new machineFrameDatagridModels();
				 house.searchMachineFrameInfo();
				 $("#editCfWindow").jqxWindow('close');
		    },
		    failure:function(data){
		    	//nsole.log(JSON.stringify(data));
 			}
	    });
	}
}

function deleteCF(){
	
	var rowindex = $('#framedatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#jqxdeleteCFBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#framedatagrid').jqxGrid('getrowdata', rowindex);
		var equipCabinetFrameSid = data.equipSid;
		Core.confirm({
			 title:"提示",
		     message:"您确定要删除该刀箱",
		     confirmCallback:function(){
		    	 Core.AjaxRequest({
						url : ws_url + "/rest/phycomputes/delete/framesSid/"+equipCabinetFrameSid,
		 				type:"get",
		 				callback : function (data) {
		 					 var cab = new machineFrameDatagridModels();
		 					 cab.searchMachineFrameInfo();
		 			    },
		    	 });
		     }
		});
	}
}
//弹出详情
 function goPhyMonitorPage(row){
 	 var rowData = $('#framedatagrid').jqxGrid('getrowdata', row);
 	 var windowW = $(window).width();
	 var windowH = $(window).height(); 
	 $("#bladeFrameWindows").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-500)/2 } });
	 $("#bladeFrameWindows").jqxWindow('open');
 	 var bladeframeDetail = new bladeFrameDetailModel();
	 bladeframeDetail.popBladeframeDetailInfoWindow(rowData);
 }


