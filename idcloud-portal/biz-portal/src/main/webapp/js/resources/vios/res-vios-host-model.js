var viosHostModel = function () {
	var me = this;
    this.items = ko.observableArray();
    // 查询方法
    this.searchObj = {
    	 "viosLparNameLike":"",
		 "resVeSid":resTopologySid
	};
	
    // 查询出power主机的CPU池
    this.initInput = function(){
    	 $("#search-vios-host-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
    	 $("#search-vios-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
    
	 // 查询出power主机的CPU池
    this.searchViosHostInfo = function(){
    	// VIOS
    	Core.AjaxRequest({
 			url : ws_url + "/rest/vioss/ve",
 			type:'post',
 			params:me.searchObj,
 			async:false,
 			callback : function (data) {
 				var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
 			    };
 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
 			    $("#viosHostdatagrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
    
    // vios
    this.initViosHostDatagrid = function(){
    	$("#viosHostdatagrid").jqxGrid({
              width: "99.8%",
			  theme:currentTheme,
              columnsresize: true,
              pageable: true, 
              pagesize: 10, 
              autoheight: true,
              autowidth: false,
              sortable: true,
              selectionmode:"single",
              localization: gridLocalizationObj,
              columns: [
                  { text: 'vios分区名', datafield: 'viosLparName',width:100},
                  { text: '所属主机', datafield: 'hostName'},
            	  
                  { text: '使用量', columngroup: 'physicalCpu',datafield: 'powerCpuUnits',cellsalign:'center',align: 'center',width:60},
                  { text: '最小使用量', columngroup: 'physicalCpu',datafield: 'powerCpuMinUnits',cellsalign:'center',align: 'center',width:80},
                  { text: '最大使用量', columngroup: 'physicalCpu',datafield: 'powerCpuMaxUnits',cellsalign:'center',align: 'center',width:80},
                  
                  { text: '数', columngroup: 'virtualCpu',datafield: 'powerCpuCores',cellsalign:'center',align: 'center',width:60},
                  { text: '最小数', columngroup: 'virtualCpu',datafield: 'powerCpuMinCores',cellsalign:'center',align: 'center',width:80},
                  { text: '最大数', columngroup: 'virtualCpu',datafield: 'powerCpuMaxCores',cellsalign:'center',align: 'center',width:80},
                  
                  { text: '使用量', columngroup: 'memory',datafield: 'memorySize',cellsalign:'center',align: 'center',width:60},
                  { text: '最小使用量', columngroup: 'memory',datafield: 'memoryMin',cellsalign:'center',align: 'center',width:80},
                  { text: '最大使用量', columngroup: 'memory',datafield: 'memoryMax',cellsalign:'center',align: 'center',width:80},
                  
                  { text: '管理用户名', datafield: 'user',width:80},
                  { text: '管理IP', datafield: 'ip',width:100},
                  { text: 'vios版本', datafield: 'version',width:80}
              ],
              columngroups: [
     	                    { text: '物理CPU(个)', align: 'center', name: 'physicalCpu' },
     	                    { text: '虚拟CPU(个)', align: 'center', name: 'virtualCpu' },
     	                    { text: '内存(GB)', align: 'center', name: 'memory' }
     	      ],
              showtoolbar: true,
              // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var addHostVios = $("<div><a id='addHostVios' onclick='popAddHostViosWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
                  var editHostVios = $("<div><a id='editHostVios' onclick='popEditHostViosWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                  var removeHostVios = $("<div><a id='removeHostVios' onclick='deleteHostVios()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
            	  container.append(addHostVios);
                  container.append(editHostVios);
                  container.append(removeHostVios);
              }
          });
    	
    	 // 控制按钮是否可用
  	  	$("#viosHostdatagrid").on('rowselect', function (event) {
  		  
	  		  var args = event.args; 
	  		  var index = args.rowindex;
	  		  $("#editHostVios").jqxButton({ disabled: false });
	  		  $("#removeHostVios").jqxButton({ disabled: false });
        });
  	  	
  	    $("#addHostVios").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
		$("#editHostVios").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
		$("#removeHostVios").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
 
    };
};

// 弹出新增主机Vios
function popAddHostViosWindow(){
	var code = new codeModel({width:200,dropDownWidth:200,dropDownHeight:150,autoDropDownHeight:false});
	code.getCustomCode("add-vios-host-sid", "/host", "hostName", "resHostSid", false, "POST", {resTopologySid:resTopologySid});
	
	$("#add-vios-power-vios-lpar-name").val("");
	$("#add-vios-power-cpu-max-units").val("");
	$("#add-vios-power-cpu-min-units").val("");
	$("#add-vios-power-cpu-units").val("");
	$("#add-vios-power-cpu-max-cores").val("");
	$("#add-vios-power-cpu-min-cores").val("");
	$("#add-vios-power-cpu-cores").val("");
	$("#add-vios-power-memory-max").val("");
	$("#add-vios-power-memory-min").val("");
	$("#add-vios-power-memory-size").val("");
	$("#add-vios-host-user").val("");
	$("#add-vios-host-password").val("");
	$("#add-vios-host-ip").val("");
	
	// 清空数据
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#addHostViosWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-220)/2 } });
	$("#addHostViosWindow").jqxWindow('open');
}


//弹出编辑主机Vios
function popEditHostViosWindow(){
	var rowindex = $('#viosHostdatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#viosHostdatagrid').jqxGrid('getrowdata', rowindex);
		
	   	$("#edit-vios-power-vios-lpar-name").val(data.viosLparName);
	   	$("#edit-vios-power-version").val(data.version);
		$("#edit-vios-host-sid").val(data.resViosSid);
		$("#edit-vios-power-cpu-max-units").val(data.powerCpuMaxUnits);
    	$("#edit-vios-power-cpu-min-units").val(data.powerCpuMinUnits);
    	$("#edit-vios-power-cpu-units").val(data.powerCpuUnits);
    	$("#edit-vios-power-cpu-max-cores").val(data.powerCpuMaxCores);
    	$("#edit-vios-power-cpu-min-cores").val(data.powerCpuMinCores);
    	$("#edit-vios-power-cpu-cores").val(data.powerCpuCores);
    	$("#edit-vios-power-memory-max").val(data.memoryMax);
    	$("#edit-vios-power-memory-min").val(data.memoryMin);
    	$("#edit-vios-power-memory-size").val(data.memorySize);
    	
    	$("#edit-vios-host-user").val(data.user);
    	$("#edit-vios-host-password").val(data.password);
    	$("#edit-vios-host-ip").val(data.ip);
		
    	// 清空数据
    	var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#editHostViosWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-225)/2 } });
    	$("#editHostViosWindow").jqxWindow('open');
   	}
	
}

// 删除主机Vios信息
function deleteHostVios(){
	var rowindex = $('#viosHostdatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#viosHostdatagrid').jqxGrid('getrowdata', rowindex);
	   	Core.confirm({
			title:"提示",
			message:"确定要删该VIOS吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
					url : ws_url + "/rest/vioss/delete/"+data.resViosSid,
					type:"get",
					callback : function (data) {
						 // 关闭window
						 var vios = new viosHostModel();
						 vios.searchViosHostInfo();
				    }
			    });
			}
		});
   	}
}

// 查询vios主机
function searchViosHost(){
	  var vios = new viosHostModel();
	  vios.searchObj.viosLparNameLike = $("#search-vios-host-name").val();
	  vios.searchObj.resVeSid = resTopologySid;
	  vios.searchViosHostInfo();
}
