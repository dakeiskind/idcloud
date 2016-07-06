var rscIndexMgtModel = function () {
		var me = this;
	    
	    // 查询存储类别信息
	    this.searchRscInfo = function(){
	    	 var topoData = null;
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/topology/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				topoData = data;
	 			}
	 		 });
	    	 return topoData;
	    };
	    
	    // 设置基本信息
	    this.setRscBasicInfo = function(){
	    	var data = me.searchRscInfo();
	    	$("#resTopologyName").html(data.resTopologyName);
	    	$("#description").html(data.description);
	    	
	    };

  };

  // 删除资源环境
  function removeRscItem(){
	  Core.confirm({
			title:"提示",
			message:"您确定要删除该存储分类吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
	 				url : ws_url + "/rest/topology/delete/rsc/"+resTopologySid,
	 				type:"get",
	 				callback : function (data) {
	 					// 刷新左边的tree
	 					setStorageVirtualTreeValue();
	 			    }
	 			});
			}
	});
  }

  