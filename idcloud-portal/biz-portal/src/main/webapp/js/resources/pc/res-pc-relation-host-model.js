var pcRelationHostModel = function () {
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		hostNameLike:"",
    		isNullResPoolSid:"yes"
	};
    // 用户数据
    this.searchHostInfo = function(){

    	if(resTopologyType == 'PCVX'){
    		me.searchObj.resHostTopologyType = "'VMware'";
    	}else if(resTopologyType == 'PCX'){
    		me.searchObj.resHostTopologyType = "'Other'";
    	}else if(resTopologyType == 'PCP'){
    		me.searchObj.resHostTopologyType = "'HMC','IVM'";
    		me.searchObj.isViosFlag = '01';
    	}else{
    		me.searchObj.resHostTopologyType = "'HMC','IVM'";
    		me.searchObj.isViosFlag = '02';
    	}
    	
    	 Core.AjaxRequest({
 			url : ws_url + "/rest/host",
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
 			    $("#pcRelationHostdatagrid").jqxGrid({source: dataAdapter});
 			}
 		 });
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	var codesearch = new codeModel({width:120,autoDropDownHeight:true});
    	$("#search-pc-relation-host-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
    	
        $("#search-pc-relation-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
        $("#pcRelationHostSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
        $("#pcRelationHostCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
  
    // 初始化用户datagrid的数据源
    this.initDatagrid = function(){
          $("#pcRelationHostdatagrid").jqxGrid({
              width: "99.5%",
			  theme:currentTheme,
              columnsresize: true,
              pageable: true, 
              pagesize: 10, 
              autoheight: true,
              autowidth: false,
              sortable: true,
              selectionmode:"checkbox",
              localization: gridLocalizationObj,
              columns: [
					{ text: '主机名称', datafield: 'hostName'},
					{ text: '所属集群', datafield: 'parentTopologyName',width:120},
					{ text: '主机IP', datafield: 'hostIp',width:120},
					{ text: 'CPU数(个)', datafield: 'cpuNumber',cellsalign:'right',width:80},
					{ text: 'CPU(核)', datafield: 'cpuCores',cellsalign:'right',width:80},
					{ text: '内存(GB)', datafield: 'memorySizeGb',cellsalign:'right',width:80},
					{ text: '主机状态', datafield: 'statusName',align: 'center',cellsalign:'center',width:60}
              ],
              showtoolbar: false,
          });
    };
    
    
    // 初始化弹出window
    this.initPopWindow = function(){
    	$("#pcRelationHostWindow").jqxWindow({
		      width: 750, 
		      height:415,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#pcRelationHostCancel"), 
		      modalOpacity: 0.3
		  });
    };
    
};

//条件查询IP配置管理
function searchRelationHostMgt(){
	var relationHost = new pcRelationHostModel();  
	relationHost.searchObj.hostNameLike = $("#search-pc-relation-host-name").val();
	relationHost.searchHostInfo();
};

// 保存选中的关联主机
function saveRelationHostInfo(){
	var resHostSids = "";
	  var rowindex = $('#pcRelationHostdatagrid').jqxGrid('getselectedrowindexes');
	  if(rowindex.length > 0){
		   
		   for(var i=0;i<rowindex.length;i++){
	   			 var data = $('#pcRelationHostdatagrid').jqxGrid('getrowdata', rowindex[i]);
		   		 if(i == rowindex.length-1){
		   			resHostSids += data.resHostSid;
				 }else{
					 resHostSids += data.resHostSid + ",";
				 }
	       }
		   
		   // 提交取消关联
	   	   Core.AjaxRequest({
	   			url : ws_url + "/rest/host/relation/pool",
				type:"POST",
				params:{
					resHostSids : resHostSids,
					parentTopologySid : resTopologySid
					
				},
				callback : function (data) {
					// 刷新可以关联的主机列表
					searchRelationHostMgt();
					// 刷新关联主机列表
					var pcHost = new pcHostDatagridModel();
					pcHost.searchHostInfo();
					// 关闭window
					$('#pcRelationHostdatagrid').jqxGrid('clearselection');
					$("#pcRelationHostWindow").jqxWindow('close');
			    }
			});
	 }
}

//弹出新增Ip框
function popPcRelationHostWindow(){
	// 刷新datagrid，情况查询输入框
	var relationHost = new pcRelationHostModel();  
	relationHost.searchHostInfo();
	$("#search-pc-relation-host-name").val("");
    // 设置弹出框位置
    var windowW = $(window).width(); 
    var windowH = $(window).height(); 
    $("#pcRelationHostWindow").jqxWindow({ position: { x: (windowW-750)/2, y: (windowH-465)/2 } });
    $("#pcRelationHostWindow").jqxWindow('open');
}

  