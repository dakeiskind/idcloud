var loadbalancerDatagridModels = function(){
	
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		  "qm.nameLike":null,
    		  "qm.equipRackSid":null,
        	"qm.resTopologyNameVE":null,
        	"qm.resTopologyNameDC":null,
        	"qm.resTopologySid":null,
        	"qm.findChildBySid":resTopologySid,
          "qm.serialNumberLike":null,
        	 parentTopologySid:resTopologySid
	};
    
    this.staObj = {
    	     resTopologySid:resTopologySid
    	};
     
   //统计机框资源查询方法
     this.getDataGridData = function(){
     	 var loadbalancerData;
     	 Core.AjaxRequest({
 	 			url : ws_url + "/rest/phynetwork/loadbalancer/statistical",
 	 			type:'post',
 	 			params:me.staObj,
 	 			async:false,
 	 			callback : function (data) {
 	 				loadbalancerData = data;
 	 			}
 	     });
     	 return loadbalancerData;
     };
 		this.LoadbalancerResourcesStatistics = function(){
 			var loadbalancerData = me.getDataGridData();
 			var data = new Object();
 			data.attr = new Array();
 			data.loadbalancerCount = loadbalancerData.staTotalLoadBalance;
 			var value = [loadbalancerData.staTotalLoadBalance];
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
    
    this.initLoadbalancerInput = function(){
    	// 初始化查询组件
        $("#search-loadBalancerName").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-loadBalancerName-serialNumber").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-loadbalancerbutton").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
        // var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
        // codeadd.getCustomCode("search-load-equipRackSid","/phylocations/downlist/rack","name","equipSid",true,"POST",{resTopologySid:resTopologySid});
       
    };
    this.initComboxLinkage = function(){
        var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
        codeadd.getCustomCode("search-load-equipRackSid","/phylocations/downlist/rack","name","equipSid",true,"POST",{resTopologySid:resTopologySid});
        codeadd.getCustomCode("add-load-equipRoomSid","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
        var zoneSid =  $("#add-load-equipRoomSid").val();
        codeadd.getCustomCode("add-load-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:zoneSid});
        var cabinetSid =  $("#add-load-equipCabinetSid").val();
        codeadd.getCustomCode("add-load-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:cabinetSid});
        var rackSid =  $("#add-load-equipCabinetSid").val();

        $('#add-load-equipRoomSid').on('change', function (event){  
            var args = event.args;
            if (args) {
              var item = args.item;
              var value = item.value;
              codeadd.getCustomCode("add-load-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:value});
            } 
        });
        $('#add-load-equipCabinetSid').on('change', function (event){
          var args = event.args;
            if (args) {
              var item = args.item;
              var value = item.value;
              codeadd.getCustomCode("add-load-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:value});
              var items = $("#add-load-equipRackSid").jqxDropDownList('getItems');
              if(items.length > 0){
                $("#add-load-equipRackSid").jqxDropDownList('selectItem', items[0] );
              }
            }
        });

        codeadd.getCustomCode("edit-load-equipRoomSid","/phylocations/downlistServer/room","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
        var editzoneSid =  $("#edit-load-equipRoomSid").val();
        codeadd.getCustomCode("edit-load-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:editzoneSid});
        var editcabinetSid =  $("#edit-load-equipCabinetSid").val();
        codeadd.getCustomCode("edit-load-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:editcabinetSid});
        var editrackSid =  $("#edit-load-equipCabinetSid").val();

         $('#edit-load-equipRoomSid').on('change', function (event){  
            var args = event.args;
            if (args) {
              var edititem = args.item;
              var editvalue = edititem.value;
              codeadd.getCustomCode("edit-load-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",true,"POST",{equipRoomSid:editvalue});
            } 
        });
        $('#edit-load-equipCabinetSid').on('change', function (event){
          var args = event.args;
            if (args) {
              var edititem = args.item;
              var editvalue = edititem.value;
              codeadd.getCustomCode("edit-load-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",true,"POST",{equipCabinetSid:editvalue});
              var items = $("#edit-load-equipRackSid").jqxDropDownList('getItems');
              if(items.length > 0){
                $("#edit-load-equipRackSid").jqxDropDownList('selectItem', items[0] );
              }
            }
        });

    }




    this.searchLoadbalancerInfo = function(){
  	  $("#loadbalancerdatagrid").jqxGrid("gotopage",0);
  		var dataAdapter = Core.getPagingDataAdapter({
  			gridId: "loadbalancerdatagrid",
  			url : ws_url + "/rest/phynetwork/find/loadbalance",
  			params: me.searchObj
  		});         
  		$("#loadbalancerdatagrid").jqxGrid({
  			source: dataAdapter,
  			rendergridrows: function(){
  				return dataAdapter.records;
  			}
  		});
    };
    
    this.initLoadbalancerDatagrid = function(){
    	var dataAdapter = Core.getPagingDataAdapter({
			gridId: "loadbalancerdatagrid",
			url: ws_url + "/rest/phynetwork/find/loadbalance",
			params: me.searchObj
		});
    	
        $("#loadbalancerdatagrid").jqxGrid({
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
                  { text: '负载均衡器编号', datafield: 'name',cellsalign:'left'},
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
                       return "<div  align='center' style='width:90px;height:22px;line-height:25px;'>&nbsp;&nbsp;<i onclick='goPhyLoadBalancerMonitorPage("+row+")' title='详情' class='icons-blue icon-popup'></i></div>";
                      },width:80  }
            ],
            showtoolbar: true,
            // 设置toolbar操作按钮
            rendertoolbar: function (toolbar) {
                var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
//                var refreshBtn = $("<div><a id='jqxRefreshLBBtn' onclick='refreshLoad()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                var addBtn = $("<div><a id='addLBBtn' onclick='addLoad()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>增加&nbsp;&nbsp;</a></div>");
                var editBtn = $("<div><a id='editLBBtn' onclick='editLoad()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                var deleteBtn = $("<div><a id='deleteLBBtn' onclick='deleteLoad()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                var refreshBtn = $("<div><a id='jqxRefreshLBBtn' onclick='refreshLoad()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                toolbar.append(container);
//                container.append(refreshBtn);
                container.append(addBtn);
                container.append(editBtn);
                container.append(deleteBtn);
                container.append(refreshBtn);
                $("#jqxRefreshLBBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#addLBBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#editLBBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#deleteLBBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
            }
        });
  };
  
  this.initPopWindow = function(){
	  $("#addLoadWindow").jqxWindow({
		  width: 1000, 
          height:500,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#loadCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	  $("#add-load-locationNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-equipType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-name").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-serialNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-brand").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-model").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#add-load-spec").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#add-load-resTopologySid").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#add-load-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-remoteMgtPwd").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-maintCompany").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#add-load-purchaseDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#add-load-startEndDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#loadSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            $("#loadCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
          }
	  });
	  $("#editLoadWindow").jqxWindow({
		  width: 1000, 
          height:500,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#loadeditCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	$("#edit-load-locationNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-equipType").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-name").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-serialNumber").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-brand").jqxInput({placeHolder: "", height:22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-model").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-description").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#edit-load-spec").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#edit-load-resTopologySid").jqxInput({placeHolder: "", height:40 , width: 500, minLength: 1,theme:currentTheme});
            $("#edit-load-remoteMgtIp1").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-remoteMgtIp2").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-relevanceIp").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-remoteMgtUser").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-remoteMgtPwd").jqxPasswordInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-maintCompany").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-maintTel").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-warrantyPeriod").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-equipSupplier").jqxInput({placeHolder: "", height:22 , width: 150, minLength: 1,theme:currentTheme});
            $("#edit-load-purchaseDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#edit-load-startEndDate").jqxDateTimeInput({ height:22, width: 150,theme:currentTheme,allowKeyboardDelete: true,allowNullDate: true, value:null, culture: 'zh-CN', formatString: 'yyyy-MM-dd HH:mm:ss'});
            $("#loadeditSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            $("#loadeditCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
          }
	  });
     $("#loadbalancerWindows").jqxWindow({
              width: 800, 
              height:600,
              theme:currentTheme,  
              resizable: false,  
              isModal: true, 
              autoOpen: false, 
              cancelButton: $("#loadbalancer-close-button"), 
              modalOpacity: 0.3,
              initContent:function(){
                // 初始化新增用户页面组件
              $("#loadbalancer-close-button").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
              }
     });
  };
  
  
  this.initValue = function(){
	  $('#addLoadForm').jqxValidator({
			rules : [
             {input: '#add-load-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input : '#add-load-equipType',message : '长度不能大于32个字符',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#add-load-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input : '#add-load-name',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#add-load-remoteMgtIp1', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#add-load-remoteMgtIp2', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#add-load-relevanceIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             { input: '#add-load-warrantyPeriod', message: '只能输入数字', action: 'keyup', rule: function (input, commit) {
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
               {input: '#add-load-maintTel', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
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
	  $('#editLoadForm').jqxValidator({
			rules : [ 
             {input: '#edit-load-equipType', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input : '#edit-load-equipType',message : '长度不能大于32个字符',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#edit-load-name', message: '不能为空', action: 'keyup, blur', rule: 'required' },
             {input : '#edit-load-name',message : '长度不能大于32个字符!',action : 'keyup, blur',rule : 'length=1,32'},
             {input: '#edit-load-remoteMgtIp1', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#edit-load-remoteMgtIp2', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             {input: '#edit-load-relevanceIp', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
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
             { input: '#edit-load-warrantyPeriod', message: '只能输入数字', action: 'keyup', rule: function (input, commit) {
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
                {input: '#edit-load-maintTel', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
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

function refreshLoad(){
	 var h = new loadbalancerDatagridModels();
	 h.searchLoadbalancerInfo();
}

function searchLoadbalancer(){
	  var lb = new loadbalancerDatagridModels();
	  lb.searchObj["qm.nameLike"] = $("#search-loadBalancerName").val();
    lb.searchObj["qm.serialNumberLike"] = $("#search-loadBalancerName-serialNumber").val();
	  lb.searchObj["qm.equipRackSid"] = $("#search-load-equipRackSid").val()=="全部"?"":$("#search-load-equipRackSid").val();
	  lb.searchLoadbalancerInfo();
}

function addLoad(){
  $("#add-load-locationNumber").val("");
  $("#add-load-equipType").val("");
  $("#add-load-name").val("");
  $("#add-load-serialNumber").val("");
  $("#add-load-brand").val("");
  $("#add-load-model").val("");
  $("#add-load-description").val("");
  $("#add-load-spec").val("");
  $("#add-load-resTopologySid").val("");
  $("#add-load-remoteMgtIp1").val("");
  $("#add-load-remoteMgtIp2").val("");
  $("#add-load-relevanceIp").val("");
  $("#add-load-remoteMgtUser").val("");
  $("#add-load-remoteMgtPwd").val("");
  $("#add-load-maintCompany").val("");
  $("#add-load-maintTel").val("");
  $("#add-load-warrantyPeriod").val("");
  $("#add-load-equipSupplier").val("");
  $("#add-load-purchaseDate").val("");
  $("#add-load-startEndDate").val("");
	// var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
 //    codeadd.getCustomCode("add-load-equipRackSid","/phylocations/downlist/rack","name","equipSid",false,"POST",{resTopologySid:resTopologySid});
    var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
	$("#addLoadWindow").jqxWindow({ position: { x: (windowW-1000)/2, y: (windowH-500)/2 } });
  	$("#addLoadWindow").jqxWindow('open');
}

function submitAddLoad(){
	var isOk = $('#addLoadForm').jqxValidator('validate');
	var lb = new loadbalancerDatagridModels();
	$("#add-load-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#addLoadForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phynetwork/create/loadbalance",
			params :lh,
			callback : function (data) {
			   var load = new loadbalancerDatagridModels();
			   load.searchLoadbalancerInfo();
			   $("#addLoadWindow").jqxWindow('close');
			    
		    }
	  });
	}
}
function editLoad(){
  // var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
  // codeadd.getCustomCode("edit-load-equipRackSid","/phylocations/downlist/rack","name","equipSid",false,"POST",{resTopologySid:resTopologySid});
	var rowindex = $('#loadbalancerdatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#editLBBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#loadbalancerdatagrid').jqxGrid('getrowdata', rowindex);
    $("#edit-load-locationNumber").val(data.locationNumber);
    $("#edit-load-name").val(data.name);
    $("#edit-load-equipType").val(data.equipType);
    $("#edit-load-serialNumber").val(data.serialNumber);
    $("#edit-load-brand").val(data.brand);
    $("#edit-load-model").val(data.model);
    $("#edit-load-remoteMgtIp1").val(data.remoteMgtIp1);
    $("#edit-load-remoteMgtIp2").val(data.remoteMgtIp2);
    $("#edit-load-relevanceIp").val(data.relevanceIp);
    $("#edit-load-remoteMgtUser").val(data.remoteMgtUser);
    $("#edit-load-remoteMgtPwd").val(data.remoteMgtPwd);  
    $("#edit-load-equidSid").val(data.equidSid);
    $("#edit-load-equipRoomSid").val(data.equipRoomSid);
    $("#edit-load-equipCabinetSid").val(data.equipCabinetSid);
    $("#edit-load-equipRackSid").val(data.equipRackSid);

      var equipLoadBalanceSid = data.equipLoadBalanceSid;
      Core.AjaxRequest({
      url : ws_url + "/rest/phynetwork/findMaintenanceLb/"+equipLoadBalanceSid,
      type : "get",
      async : false,
      callback : function(result) {
        $("#edit-load-maintCompany").val(result.maintCompany);
        $("#edit-load-maintTel").val(result.maintTel);
        $("#edit-load-warrantyPeriod").val(result.warrantyPeriod);
        $("#edit-load-equipSupplier").val(result.equipSupplier);
        $("#edit-load-spec").val(result.spec);
        $("#edit-load-description").val(result.description);
        $("#edit-load-purchaseDate").val(result.purchaseDate);
        $("#edit-load-startEndDate").val(result.startEndDate);
      }
    });
  	
	  var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
	  $("#editLoadWindow").jqxWindow({ position: { x: (windowW-1000)/2, y: (windowH-500)/2 } });
  	$("#editLoadWindow").jqxWindow('open');
	}
}

function submitEditLoad(){
	var isOk = $('#editLoadForm').jqxValidator('validate');
	var lb = new loadbalancerDatagridModels();
  var rowindex = $('#loadbalancerdatagrid').jqxGrid('getselectedrowindex');
  var data = $('#loadbalancerdatagrid').jqxGrid('getrowdata', rowindex);
  $("#edit-load-equipLoadBalanceSid").val(data.equipLoadBalanceSid);
	$("#edit-load-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#editLoadForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phynetwork/update/loadbalance",
			params :lh,
			callback : function (data) {
				  var load = new loadbalancerDatagridModels();
				   load.searchLoadbalancerInfo();
				 $("#editLoadWindow").jqxWindow('close');
		    }
	    });
	}
}

function deleteLoad(){
	var rowindex = $('#loadbalancerdatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#deleteLBBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#loadbalancerdatagrid').jqxGrid('getrowdata', rowindex);
		var equipLoadBalanceSid = data.equipLoadBalanceSid;
		 Core.confirm({
			 title:"提示",
		     message:"您确定要删除该负载均衡器吗？",
		     confirmCallback:function(){
		    	 Core.AjaxRequest({
		    		 url : ws_url + "/rest/phynetwork/delete/loadbalance/"+equipLoadBalanceSid,
		 				type:"get",
		 				callback : function (data) {
		 					 var sw = new loadbalancerDatagridModels();
		 					 sw.searchLoadbalancerInfo();
		 			    },
		    	 });
		     }
		 });
	}
}

function goPhyLoadBalancerMonitorPage(row){
   var rowData = $('#loadbalancerdatagrid').jqxGrid('getrowdata', row);
   var windowW = $(window).width();
   var windowH = $(window).height(); 
   $("#loadbalancerWindows").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-600)/2 } });
   $("#loadbalancerWindows").jqxWindow('open');
   var loadbalancer = new loadbalancerDetailModel();
   loadbalancer.popLoadbalancerDetailInfoWindow(rowData);
}