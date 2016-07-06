var relationDVSGrid = function(){
	var me = this;
    this.items = ko.observableArray();
    this.noRelationData = new Object();
    this.relationData = new Object();
    // 获取未关联的DVS
    this.initNoRelationListBox = function(resPoolSid){
    	Core.AjaxRequest({
 			url : ws_url + "/rest/vss",
 			type:'post',
 			params:{
 				resPoolSid : resPoolSid,
 				resVsType : "01",
 				relation : "no"
 			},
 			async:false,
 			callback : function (data) {
 				me.noRelationData = data;
 				var source ={
                     datatype: "json",
                     datafields: [
                         { name: 'resVsName' },
                         { name: 'resVsSid' }
                     ],
                     id: 'resVsSid',
                     localdata:data
                };
 				var dataAdapter = new $.jqx.dataAdapter(source);
 				$("#noRelationDVS").jqxListBox({ source: dataAdapter, multiple: true,displayMember: "resVsName", valueMember: "resVsSid", width: 150, height: 250,theme:currentTheme});
 			 }
		});
    };
    
    // 获取已关联的DVS
    this.initRelationListBox = function(resPoolSid){
    	
    	Core.AjaxRequest({
 			url : ws_url + "/rest/vss",
 			type:'post',
 			params:{
 				resPoolSid : resPoolSid,
 				resVsType : "01",
 				relation : "yes"
 			},
 			async:false,
 			callback : function (data) {
 				me.relationData = data;
 				var source ={
                     datatype: "json",
                     datafields: [
                         { name: 'resVsName' },
                         { name: 'resVsSid' }
                     ],
                     id: 'resVsSid',
                     localdata:data
                };
 				var dataAdapter = new $.jqx.dataAdapter(source);
 				$("#relationDVS").jqxListBox({ source: dataAdapter, multiple: true,displayMember: "resVsName", valueMember: "resVsSid", width: 150, height: 250,theme:currentTheme});
 			}
		});
    };
    
    
    
    // 初始化ListBox的事件
    this.initListBoxChangeEvent = function(){
    	// 未关联
    	$("#noRelationDVS").on('select', function (event) {
            if (event.args) {
                var item = event.args.item;
                if (item) {
                	$("#relationBtn").jqxButton({disabled:false});
                	$("#unbundleBtn").jqxButton({disabled:true});
                }
            }
        });
    	
    	// 已关联
    	$("#relationDVS").on('select', function (event) {
            if (event.args) {
                var item = event.args.item;
                if (item) {
                	$("#relationBtn").jqxButton({disabled:true});
                	$("#unbundleBtn").jqxButton({disabled:false});
                }
            }
        });
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	
    	$("#relationBtn").jqxButton({ width: '50',height:"25",theme:currentTheme,disabled:true});
    	$("#unbundleBtn").jqxButton({ width: '50',height:"25",theme:currentTheme,disabled:true});
    	$("#closeRelationBtn").jqxButton({ width: '50',height:"25",theme:currentTheme});
    	$("#saveRelationBtn").jqxButton({ width: '50',height:"25",theme:currentTheme});
    	
    };
    
    // 初始化弹出window
    this.initPopWindow = function(){
    	$("#relationDVSWindow").jqxWindow({
		      width: 400, 
		      height:340,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#closeRelationBtn"), 
		      modalOpacity: 0.3
		});
    };
    
};

// 保存关联dvs
function saveRelationDvs(){
	// vlan池sid
	var rowindex = $('#poolPNVDatagrid').jqxGrid('getselectedrowindex');
	var vlanData = $('#poolPNVDatagrid').jqxGrid('getrowdata', rowindex);
	
	// 已关联的vs
	var items = $("#relationDVS").jqxListBox('getItems');
	var vsSids = "";
	for(var i=0;i<items.length;i++){
		if(i == items.length-1){
			// 为了查询出集群下面的存储，加上引号
			vsSids += items[i].value;
		}else{
			vsSids += items[i].value+",";
		}
	}
	if(vsSids == "" || vsSids == null){
		vsSids = "null";
	}
	Core.AjaxRequest({
		url : ws_url + "/rest/poolVlanVss/relation/"+vlanData.resPoolSid+"/"+vsSids,
		type:'get',
		async:false,
		callback : function (data) {
			// 关闭Window
			$("#relationDVSWindow").jqxWindow('close');
			var vlanVs = new poolPnvDatagridModel();
			vlanVs.searchPnvInfo();
		}
	});
	
	
}
