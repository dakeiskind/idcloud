var resClusterSids;
var poolPcRelationClusterModel = function () {
		var me = this;
		this.resClusterSids = "";
		this.searchObj = {
				 resTopologyNameLike:"",
				 parentTopologySid : "",
				 notExistResPoolSid : "none"
		};
		// 初始化组件
		this.initInput = function(){
			
			// 查询组件初始化
			$("#search-cluster-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength:1,theme:currentTheme});
			// 初始化下拉列表框 
	        $("#search-relation-cluster-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        
	        // 操作按钮
	        $("#addPcRelationClusterSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        $("#addPcRelationClusterCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        $("#addConfigStorage").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        $("#confirmAddStorage").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        $("#addStorageCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        
		};
		
		// 给关联集群datagrid赋值
	    this.searchRelationClusterInfo = function(){
	    	
	    	if(resTopologyType == 'PCVX'){
	    		me.searchObj.resVcTopologyType = "'VMware'";
	    	}else if(resTopologyType == 'PCX'){
	    		me.searchObj.resVcTopologyType = "'Other'";
	    	}else if(resTopologyType == 'PCP'){
	    		me.searchObj.resVcTopologyType = "'HMC','IVM'";
	    	}else{
	    		me.searchObj.resVcTopologyType = "'HMC','IVM'";
	    	}
	    	
	    	 Core.AjaxRequest({
		 			url : ws_url + "/rest/vcs",
		 			type:'post',
		 			params:me.searchObj,
		 			async:false,
		 			callback : function (data) {
		 				var sourcedatagrid ={
					              datatype: "json",
					              localdata: data
		 			    };
		 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		 			    $("#pcRelationClusterDatagrid").jqxGrid({source: dataAdapter});
		 			 }
	 		  });
	    };
	    
	    // 给配置存储datagrid赋值
	    this.searchConfigStorageInfo = function(clusterSids){
	    	// 查询出集群下面所有的主机，在根据主机查询出所有的存储
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/storage",
	 			type:'post',
	 			params:{
	 				notExistResPoolSid : 'none',
	 				clusterSidCollection : clusterSids
	 			},
	 			async:false,
	 			callback : function (data) {
	 				var sourcedatagrid ={
				              datatype: "json",
				              localdata: data
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#pcRelationConfigStorageDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    	
	    };
	    
		// 初始化 datagrid
		this.initDatagrid = function(){
			$("#pcRelationClusterDatagrid").jqxGrid({
	              width: "99%",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true,
	              pagesizeoptions: ['5', '10'],
	              pagesize: 10,
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"checkbox",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '集群名称', datafield: 'clusterName', width:100},
	                  { text: '资源环境', datafield: 'parentTopologyName',width:100},
	                  { text: '描述', datafield: 'description'}
	              ]
	          });
			
			 $("#pcRelationConfigStorageDatagrid").jqxGrid({
	              width: "99%",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true,
	              pagesizeoptions: ['5', '10'],
	              pagesize: 10,
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"checkbox",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '存储名称', datafield: 'storageName'},
	                  { text: '存储类别', datafield: 'storageCategoryName'},
	                  { text: '所属拓扑', datafield: 'resTopologyName'},
	                  { text: '总容量(GB)', datafield: 'totalCapacity',width:120},
	                  { text: '可用容量(GB)', datafield: 'availableCapacity',width:120},
	                  { text: '状态', datafield: 'storageStatus'}
	              ]
	          });
		};
		
	    // 初始化弹出window
	    this.initPopWindow = function(){
	    	$("#pcRelationClusterWindow").jqxWindow({
                width: 700, 
                height:415, 
                resizable: false, 
                isModal: true, 
                autoOpen: false, 
                theme: currentTheme,
                cancelButton: $("#addPcRelationClusterCancel"),
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化可关联集群的datagrid
                	me.searchRelationClusterInfo();
                }
            });
	    };
};
  
// 弹出关联集群window
function popPcRelationCluster(){
	// 恢复位置
	$("#virtualInfo").css({"color":"#0099d7"});
	$("#confirmInfo").css({"color":"gray"});
	$("#confirmVeInfo").animate({left:'566px'},'fast',function(){
		 $("#veInfo").animate({left:'0px'},'fast');
		 var pc = new poolPcRelationClusterModel();
		 pc.searchRelationClusterInfo();
	});
	
	var topologyType = "";
	if(resTopologyType == 'PCVX'){
		topologyType = "'VMware'";
	}else if(resTopologyType == 'PCX'){
		topologyType = "'Other'";
	}else if(resTopologyType == 'PCP'){
		topologyType = "'HMC','IVM'";
	}else{
		topologyType = "'HMC','IVM'";
	}
	// 初始内容
	var codesearch = new codeModel({width:120,autoDropDownHeight:true});
	codesearch.getCustomCode("search-cluster-virtualev","/ves/findVe/"+resTopologySid+"/"+topologyType+"","resTopologyName","resTopologySid",true,"get",null);
	
	// 清除datagrid选中情况
//	$('#pcRelationClusterDatagrid').jqxGrid('clearselection');
//	$('#pcRelationConfigStorageDatagrid').jqxGrid('clearselection');
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#pcRelationClusterWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-415)/2 } });
	$("#pcRelationClusterWindow").jqxWindow('open');
}

// 搜索可关联集群
function searchRelationCluster(){
	var pcCluster = new poolPcRelationClusterModel();
	pcCluster.searchObj.resTopologyNameLike = $("#search-cluster-name").val(); 
	pcCluster.searchObj.parentTopologySid = $("#search-cluster-virtualev").val()=="全部"?"":$("#search-cluster-virtualev").val();
	pcCluster.searchRelationClusterInfo();
}

// 进入配置存储页面
function gotoNextConfigStorage(){
	 resClusterSids = "";
	 // 获取选中资源集群的datagrid数据，并保存起来
	 var rowindex = $('#pcRelationClusterDatagrid').jqxGrid('getselectedrowindexes');
	 if(rowindex.length > 0){
		 // 存储选中的集群
   		for(var i=0;i<rowindex.length;i++){
   			var data = $('#pcRelationClusterDatagrid').jqxGrid('getrowdata', rowindex[i]);
   			if(i == rowindex.length-1){
   				// 为了查询出集群下面的存储，加上引号
   				resClusterSids+= "'"+data.resTopologySid+"'";
			}else{
				resClusterSids+= "'"+data.resTopologySid+"'"+",";
			}
   		}
   		
   		// 如果正确，跳转到确认信息
		$("#virtualInfo").css({"color":"gray"});
		$("#confirmInfo").css({"color":"#0099d7"});
		$("#veInfo").animate({left:'-=566'},'fast',function(){
			 $("#confirmVeInfo").animate({left:'-=566'},'fast');
			 // 给存储列表赋值
			 var pc = new poolPcRelationClusterModel();
			 pc.searchConfigStorageInfo(resClusterSids);
		});
	  }else{
		  Core.alert({
			  title:"提示",
			  message:"请选择资源集群！"
		  });
	  } 
}

// 回到选择资源集群页面
function gobackChooseStorage(){
	$("#virtualInfo").css({"color":"#0099d7"});
	$("#confirmInfo").css({"color":"gray"});
	$("#confirmVeInfo").animate({left:'+=566'},'fast',function(){
		 $("#veInfo").animate({left:'+=566'},'fast');
		 var pcCluster = new poolPcRelationClusterModel();
		 pcCluster.searchObj.resTopologyNameLike = $("#search-cluster-name").val(); 
		 pcCluster.searchObj.parentTopologySid = $("#search-cluster-virtualev").val()=="全部"?"":$("#search-cluster-virtualev").val();
		 pcCluster.searchRelationClusterInfo();
	});
}

// 配置存储画面，关闭window
function closeRelationClusterWindow(){
	$("#pcRelationClusterWindow").jqxWindow('close');
}

// 提交关联集群
function submitRelationClusterAndStorage(){
	 var resStorageSids = "";
	 var rowindex = $('#pcRelationConfigStorageDatagrid').jqxGrid('getselectedrowindexes');
	 // 存储选中的集群，不选择存储的时候，也是能够关联集群的
	 for(var i=0;i<rowindex.length;i++){
		var data = $('#pcRelationConfigStorageDatagrid').jqxGrid('getrowdata', rowindex[i]);
		if(i == rowindex.length-1){
			resStorageSids+= data.resStorageSid;
		}else{
			resStorageSids+= data.resStorageSid+",";
		}
	 }
  		
	// 后台需要清除掉集群sid数组上面的引号
	// 提交选中的集群和存储
	Core.AjaxRequest({
			url : ws_url + "/rest/topology/relationCluster",
			type:"post",
			params:{
				resPoolSid : resTopologySid,
				resClusterSids : resClusterSids,
				resStorageSids : resStorageSids
			},
			callback : function (data) {
				$("#pcRelationClusterWindow").jqxWindow('close');
				var pcdata = new poolPcRelationClusterDatagridModel();
				pcdata.searchPcClusterInfo();
		    },
		    failure:function(data){
		    	
		    }
	 });
	 
	 
}