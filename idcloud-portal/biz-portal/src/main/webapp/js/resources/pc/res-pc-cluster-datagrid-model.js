var poolPcRelationClusterDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    	 resTopologyNameLike:"",
	    	 parentTopologySid : "",
	    	 resPoolSid:resTopologySid,
	    	 findOwnerDc: resTopologySid
		};
	    
	    // 用户数据
	    this.searchPcClusterInfo = function(){
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
	 			    $("#poolPcRelationClusterDatagrid").jqxGrid({source: dataAdapter});
	 			 }
 		  });
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-pc-name").jqxInput({placeHolder: "", height: 22, width: 120, minLength: 1,theme:currentTheme});
	        $("#search-pc-cluster-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        
	        // 初始化下拉列表框 
	        var topologyType = "";
	        if(resTopologyType == 'PCVX'){
	    		topologyType = "'VMware'";
	    	}else if(resTopologyType == 'PCX'){
	    		topologyType = "'Other'";
	    	}else if(resTopologyType == 'PCP'){
	    		topologyType = "'Other'";
	    	}else{
	    		topologyType = "'HMC','IVM'";
	    	}
	    	// 初始内容
	    	var codesearch = new codeModel({width:120,autoDropDownHeight:true});
	    	codesearch.getCustomCode("search-pc-virtualev","/ves/findVe/"+resTopologySid+"/"+topologyType+"","resTopologyName","resTopologySid",true,"get",null);
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initPcRelationClusterDatagrid = function(){
	          $("#poolPcRelationClusterDatagrid").jqxGrid({
	              width: "100%",
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
						{ text: '集群名称', datafield: 'clusterName'},
						{ text: '资源环境', datafield: 'resTopologyName'},
	                    { text: 'CPU(核)', datafield: 'cpuTotalCount',width:60,cellsalign:'right'},
	                    { text: 'CPU分配率(%)', datafield: 'cpuAllotRate',width:90,cellsalign:'right'},
	                    { text: '内存总容量(GB)', datafield: 'memoryTotalVolume',width:90,cellsalign:'right'},
	                    { text: '内存分配率(%)', datafield: 'memoryAllotRate',width:90,cellsalign:'right'},
	                    { text: '存储总量(GB)', datafield: 'storageTotalVolume',width:90,cellsalign:'right'},
	                    { text: '存储分配率(%)', datafield: 'storageAllotVolumeRate',width:90,cellsalign:'right'}
//	                    { text: '本地存储总量(GB)', datafield: 'storageLocalTotalVolume',width:90,cellsalign:'right'},
//	                    { text: '本地存储分配率(%)', datafield: 'storageLocalAllotVolumeRate',width:90,cellsalign:'right'},
//	                    { text: '共享存储总量(GB)', datafield: 'storageShareTotalVolume',width:90,cellsalign:'right'},
//	                    { text: '共享存储分配率(%)', datafield: 'storageShareAllotVolumeRate',width:90,cellsalign:'right'}
  
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var relationBtn = $("<div><a id='jqxRelationBtn' onclick='popPcRelationCluster()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>关联集群&nbsp;&nbsp;</a></div>");
	                  var relationCancel = $("<div><a id='jqxCancelBtn' onclick='cancelRelationCluster()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-reply'></i>取消关联&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(relationBtn);
	                  container.append(relationCancel);
	                  $("#jqxRelationBtn").jqxButton({width: '60',theme:currentTheme,height: '18px'});
	                  $("#jqxCancelBtn").jqxButton({width: '60',theme:currentTheme,height: '18px',disabled:true});
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#poolPcRelationClusterDatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#poolPcRelationClusterDatagrid').jqxGrid('getrowdata', index);
	    		  $("#jqxCancelBtn").jqxButton({width: '60',theme:currentTheme,height: '18px',disabled:false});
	          });
	    };
};
  // 查询计算资源池关联的集群
  function searchPcCluster(){
	    var pc = new poolPcRelationClusterDatagridModel();
	    pc.searchObj.resTopologyNameLike = $("#search-pc-name").val(); 
	    pc.searchObj.parentTopologySid = $("#search-pc-virtualev").val()=="全部"?"":$("#search-pc-virtualev").val();
	    pc.searchPcClusterInfo();
  }
  
  // 取消关联
  function cancelRelationCluster(){
	var resClusterSids = "";
	var rowindex = $('#poolPcRelationClusterDatagrid').jqxGrid('getselectedrowindexes');
	var ok =  $("#jqxCancelBtn").jqxButton("disabled");
  	if(rowindex.length > 0 && !ok){
  		Core.confirm({
			title:"提示",
			message: "您确定要取消关联选中的集群吗？",
			confirmCallback:function(){
				for(var i=0;i<rowindex.length;i++){
		   			var data = $('#poolPcRelationClusterDatagrid').jqxGrid('getrowdata', rowindex[i]);
		   			if(i == rowindex.length-1){
		   				// 为了查询出集群下面的存储，加上引号
		   				resClusterSids+= data.resTopologySid;
					}else{
						resClusterSids+= data.resTopologySid+",";
					}
		   	    }  
				
				Core.AjaxRequest({
	 				url : ws_url + "/rest/vcs/cancelRelation/",
	 				type:"post",
	 				params:{
	 					resClusterSids : resClusterSids
	 				},
	 				callback : function (data) {
	 					// 清除选择
	 					$('#poolPcRelationClusterDatagrid').jqxGrid('clearselection');
	 					// 刷新列表
	 					var relation = new poolPcRelationClusterDatagridModel();
	 					relation.searchPcClusterInfo();
	 			    }
	 			});
			}
	   });
  	}
	  
  }
  