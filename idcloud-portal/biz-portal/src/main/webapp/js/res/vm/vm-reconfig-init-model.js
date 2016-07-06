// 查看主机详情主机 
var vmReconfigModel = function () {
	
		// 给主机详情赋值
	    this.setVmReconfigForm = function(data){
	    	
	    	$("#vm-cpu-cores").val(data.cpuCores);
	        $("#vm-memory-size").val(data.memorySize);
	    };
	    
	    //初始化下拉框
	    this.initInput=function(){
	    	var cpuData=[{"id":"1","value":"1"},{"id":"2","value":"2"},{"id":"4","value":"4"},{"id":"8","value":"8"}];
    	    
	    	var cpuSource =
		         {
		             datatype: "json",
		             datafields: [
		                 { name: "id" },
		                 { name: "value" }
		             ],
		             localdata:cpuData
		         };
			 var cpuDataAdapter = new $.jqx.dataAdapter(cpuSource);
			 $("#vm-cpu-cores").jqxDropDownList({
                  source: cpuDataAdapter,
                  displayMember: "id", 
                  valueMember: "value",
                  theme:"metro"
            });
			 
			var memoryData=[{"id":512,"value":512},{"id":1024,"value":1024},{"id":2048,"value":2048},{"id":4096,"value":4096}];
			var memorySource =
	         {
	             datatype: "json",
	             datafields: [
	                 { name: "id" },
	                 { name: "value" }
	             ],
	             localdata:memoryData
	         };
			 var memoryDataAdapter = new $.jqx.dataAdapter(memorySource);
			 $("#vm-memory-size").jqxDropDownList({
		         source: memoryDataAdapter,
		         displayMember: "id", 
		         valueMember: "value",
		         theme:"metro"
			 });
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	// 主机详情
	    	$("#vmReconfigWindow").jqxWindow({
                width: 400, 
                height:140,
                theme:currentTheme,  
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#vmReconfigCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
                	$("#vmReconfigSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
        	    	$("#vmReconfigCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
        	    	
                }
            });
	    };	
 };
 
//提交编辑主机信息
 function vmReconfigSubmit(){
	 var vlans = Core.parseJSON($("#vmReconfigForm").serializeJson());
	 Core.AjaxRequest({
			url : ws_url + "/rest/resourceInstance/platform/reconfigVm",
			params :vlans,
			callback : function (data) {
				var vmmanagedmodel = new vmManagedModel();
				vmmanagedmodel.searchVMInfo();
			$("#vmReconfigWindow").jqxWindow('close');
		    },
		    failure:function(data){
			$("#vmReconfigWindow").jqxWindow('close');
		    }
		});
 }
  
  