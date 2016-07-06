var vlanConfigMgtModel = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		vlanName: "", 
    		manageStatus:"",
    		usageStatus:"",
    		isResPoolSearch:resTopologyType,
    		resTopologySid:resTopologySid
   };
    // 给datagrid赋值
    this.searchVlanConfigInfo = function(){
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/vlans",
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
 			    $("#vlanConfigMgtdatagrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	$("#search-vlan-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	
    	var vlanconfig = new codeModel({width:120,autoDropDownHeight:true});
    	vlanconfig.getCommonCode("search-vlan-mgt-status","MANAGEMENT_STATUS",true);
    	vlanconfig.getCommonCode("search-valn-usage-status","USAGE_STATUS",true);
        $("#search-vlan-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    	
    };
    
    // 初始化验证规则   
    this.initValidator = function(){
    	
    };
    // 初始化用户datagrid的数据源
    this.initVlanDatagrid = function(){
          $("#vlanConfigMgtdatagrid").jqxGrid({
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
                  { text: 'VLAN名称', datafield: 'vlanName',width:150},
                  { text: 'VLAN ID', datafield: 'vlanId',width:150},
                  { text: '标签', datafield: 'tag',width:80},
                  { text: '管理状态', datafield: 'manageStatusName'},
                  { text: '使用状态', datafield: 'usageStatusName'}
              ],
              showtoolbar: true,
              // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                  var addVlan = $("<div><a id='addVlan' onclick='addVlanInfoWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
                  var editVlan = $("<div><a id='editVlan' style='margin-left:-1px' onclick='editVlanInfoWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                  var removeVlan = $("<div><a id='removeVlan' onclick='removeVlanInfo()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
                  container.append(addVlan);
                  container.append(editVlan);
                  container.append(removeVlan);
              }
          });
          
          // 控制按钮是否可用
    	  $("#vlanConfigMgtdatagrid").on('rowselect', function (event) {
    		  var args = event.args; 
    		  var index = args.rowindex;
    		  var data = $('#vlanConfigMgtdatagrid').jqxGrid('getrowdata', index);
   			  $("#editVlan").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
   			  $("#removeVlan").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
          });
    	  // 初始化按钮
    	  me.initButton();
          
    };
    // 判断操作按钮，初始化按钮
    this.initButton = function(){
    	 // 判断datagrid是否有被选中的
    	var selectedrowindex = $('#vlanConfigMgtdatagrid').jqxGrid('selectedrowindex'); 
    	// 大于-1表示有被选中的
    	if(selectedrowindex > -1){
    		$("#editVlan").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
   			$("#removeVlan").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
    	}else{
    		$("#addVlan").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
  			$("#editVlan").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
  			$("#removeVlan").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
    	}
    	
    };
    // 初始化弹出window
    this.initPopWindow = function(){
    	
    };
    
    this.getDataGridData = function(){
   	 var vlanData;
   	 Core.AjaxRequest({
	 			url : ws_url + "/rest/vlans",
	 			type:'post',
	 			params:me.searchObj,
	 			async:false,
	 			callback : function (data) {
	 				vlanData = data;
	 			}
	     });
   	 return vlanData;
   };
   
    // vlan资源统计
    this.VlanResourcesStatistics = function(){
    	var vlanData =  me.getDataGridData();
		
		var data = new Object();
		data.vlanCount = vlanData.length;
		data.attr = new Array();
		
		var value = [0,0];
		var name =["已使用","未使用"];
		for(var i=0;i < vlanData.length;i++){
			
			if("01" == vlanData[i].usageStatus){
				// 已使用
				value[0] += 1; 
			} else {
				// 未使用
				value[1] += 1; 
			}
		}
		for(var i=0; i<2;i++){
			var obj = new Object();
			obj.name = name[i];
			obj.value = value[i];
			data.attr.push(obj);
		}
		return data;
    };
    
};

//条件查询Vlan配置管理
function searchVlanConfigMgt(){  
	vlan.searchObj.vlanName = $("#search-vlan-name").val();
	vlan.searchObj.manageStatus = $("#search-vlan-mgt-status").val()=="全部"?"":$("#search-vlan-mgt-status").val();
	vlan.searchObj.usageStatus = $("#search-valn-usage-status").val()=="全部"?"":$("#search-valn-usage-status").val();
	vlan.searchObj.resTopologySid = resTopologySid;
	vlan.searchVlanConfigInfo();
};

// 删除vlan
function removeVlanInfo(){
	var rowindex = $('#vlanConfigMgtdatagrid').jqxGrid('getselectedrowindex');
	if(rowindex >= 0){
		var data = $('#vlanConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
		Core.confirm({
			title:"提示",
			message:"确定要删除该VLAN吗?",
			yes:"确定",
			confirmCallback:function(){
				Core.AjaxRequest({
	 				url : ws_url + "/rest/vlans/delete/"+data.resSid+"",
	 				type:"get",
	 				callback : function (data) {
	 					vlan.searchVlanConfigInfo();
	 				    // 初始化按钮
	 					vlan.initButton();
	 			    },
	 			    failure:function(data){
	 			    	
	 			    }
	 			});
			}
	    });
	}
}

  