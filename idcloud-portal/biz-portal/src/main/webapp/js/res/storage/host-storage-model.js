var addMountedHostStorageModel = function(){
	     var me = this;
	     
	     this.searchMountedStorageData = function(hostSid){
	    	 	// 判断主机是否在数据中心和资源池下面
	    	   if(hostSid.substring(0,1) =="p"){
	    		  hostSid = hostSid.substring(1);
	    		}
				// 查询已挂载datagrid
				 Core.AjaxRequest({
		 			url : ws_url + "/rest/hosts/getMountStorage/"+hostSid+"",
		 			type:'get',
		 			callback : function (data) {
		 				var sourcedatagrid ={
				              datatype: "json",
				              localdata: data
		 			    };
		 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		 			    $("#mountedHostStorageDatagrid").jqxGrid({source: dataAdapter});
		 			}
		 		 });
	     };
	     
	     // 初始化挂载存储datagrid的数据源
	    this.initMountDatagrid = function(){
	  		 // 初始化已挂载存储
			 $("#mountedHostStorageDatagrid").jqxGrid({
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
		                  { text: '存储名称', datafield: 'volumeName'},
		                  { text: '存储类型', datafield: 'volumeTypeName'},
		                  { text: '硬盘类型', datafield: 'hardDiskTypeName',width:60},
		                  { text: '存储类别', datafield: 'storageCategoryName',width:60},
		                  { text: '存储用途', datafield: 'storagePurposeName',width:60},
		                  { text: '总容量(GB)', datafield: 'availableCapacity',width:80},
		                  { text: '使用状态', datafield: 'usageStatusName',width:60}
		              ] ,
		              showtoolbar: true,
		              // 设置toolbar操作按钮
		              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addMountStorage = $("<div><a id='addMountStorage'  onclick='popAddHostStorageWindow()'>&nbsp;&nbsp;<i class='icon-plus'></i>关联存储&nbsp;&nbsp;</a></div>");
	                  var editMountStorage = $("<div><a id='editMountStorage' onclick='removeHostStorage()' style='margin-left:-1px' >&nbsp;&nbsp;<i class='icon-pencil'></i>卸载存储&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addMountStorage);
	                  container.append(editMountStorage);
	              }
		          });
			 
				 $("#mountedHostStorageDatagrid").on('rowselect', function (event) {
		    		  var args = event.args; 
		    		  var index = args.rowindex;
		    		  var data = $('#mountedHostStorageDatagrid').jqxGrid('getrowdata', index);
		   			  $("#editMountStorage").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
		          });
		    	  $("#addMountStorage").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
		  		  $("#editMountStorage").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
		  		  
		  		  
		  		// 初始化可挂载datagrid
		   		 $("#addHostCanStorageDatagrid").jqxGrid({
		   	              width: "100%",
		   				  theme:currentTheme,
		   	              columnsresize: true,
		   	              pageable: true, 
			              pagesize: 5, 
		   	              autoheight: true,
		   	              autowidth: false,
		   	              sortable: true,
		   	              selectionmode:"checkbox",
		   	              localization: gridLocalizationObj,
		   	              columns: [
		   	                  { text: '存储名称', datafield: 'volumeName'},
		   	                  { text: '硬盘类型', datafield: 'hardDiskTypeName',width:60},
		   	                  { text: '存储类别', datafield: 'storageCategoryName',width:60},
		   	                  { text: '存储用途', datafield: 'storagePurposeName',width:60},
		   	                  { text: '总容量(GB)', datafield: 'availableCapacity',width:80},
		   	                  { text: '使用状态', datafield: 'usageStatusName',width:60}
		   	              ] 
		   	       });
	    };
	    
	 // 初始化弹出window
	    this.initPopWindow = function(){
	    	
	    	// 添加新挂载存储window
	    	$("#addHostStorageWindow").jqxWindow({
	            width: 600, 
	            height:290,
	            theme:currentTheme,  
	            resizable: false,  
	            isModal: true, 
	            autoOpen: false, 
	            cancelButton: $("#addHostCanStorageCancel"), 
	            modalOpacity: 0.3,
	            initContent:function(){
	            	// 初始化组件
	            	 $("#addHostCanStorageSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
	            	 $("#addHostCanStorageCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px',});
	            }
	        });
	    	    
	    };
   
};	

// 弹出新增主机存储框
function popAddHostStorageWindow(){
	// 判断主机是否在数据中心和资源池下面
	   if(resTopologySid.substring(0,1) =="p"){
		   resTopologySid = resTopologySid.substring(1);
		}
		
	    // 给已挂载datagrid赋值
	    Core.AjaxRequest({
			url : ws_url + "/rest/hosts/getCanMountStorage/"+resTopologySid+"",
			type:'get',
			callback : function (data) {
				var sourcedatagrid ={
			              datatype: "json",
			              localdata: data
			    };
			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
			    $("#addHostCanStorageDatagrid").jqxGrid({source: dataAdapter});
			}
		 });
	    
	// 设置弹出框位置
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#addHostStorageWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-300)/2 } });
	$("#addHostStorageWindow").jqxWindow('open');
}

// 删除挂载的存储
function removeHostStorage(){
	// 得到datagrid的选择项
	var rowArr = $('#mountedHostStorageDatagrid').jqxGrid('getselectedrowindexes');
	var isUsed = true;
	var storageSids ="";
	if(rowArr.length > 0){
		// 判断是否可以移除
		for(var i=0;i<rowArr.length;i++){
			// 查询出该行数据
			var data = $('#mountedHostStorageDatagrid').jqxGrid('getrowdata', rowArr[i]);
			if(i == rowArr.length-1){
				storageSids+= data.resSid;
			}else{
				storageSids+= data.resSid+",";
			}
			
			if(data.usageStatus == "01" && data.storageCategoryName == "本地存储"){ 
				
				isUsed = false;
				// 清除datagrid的选择项
				$('#mountedHostStorageDatagrid').jqxGrid('clearselection');
				break;
			}
		}
		// 判断是否有已使用的存储
		if(isUsed){
			if(resTopologySid.substring(0,1) =="p"){
			   resTopologySid = resTopologySid.substring(1);
			}
			// 移除所选择的存储
			Core.confirm({
				title:"提示",
				message:"确定要卸载该存储吗?",
				yes:"确定",
				confirmCallback:function(){
					Core.AjaxRequest({
		 				url : ws_url + "/rest/hosts/uninstallMountedStorage/"+resTopologySid+"/"+storageSids+"",
		 				type:"get",
		 				callback : function (data) {
		 					for(var i=0;i<rowArr.length;i++){
		 						$("#mountedHostStorageDatagrid").jqxGrid('deleterow', rowArr[i]);
		 					}
		 				    // 清除datagrid的选择项
							$('#mountedHostStorageDatagrid').jqxGrid('clearselection');
		 			    },
		 			    failure:function(data){
		 			    	
		 			    }
		 			});
				}
		    });
		}else{
			Core.alert({
				message:"该存储已使用，无法卸载！",
				type:"info"
			});
		}
	}else{
		// 没有选择存储
	}
}

// 提交新挂载的存储
function addHostCanStorageSubmit(){
	 	var storageSids ="";
		var rowindex = $('#addHostCanStorageDatagrid').jqxGrid('getselectedrowindexes');
		// 得到主机sid
		if(resTopologySid.substring(0,1) =="p"){
		   resTopologySid = resTopologySid.substring(1);
		}
		
	   	if(rowindex.length >= 0){
	   		for(var i=0;i<rowindex.length;i++){
	   			var data = $('#addHostCanStorageDatagrid').jqxGrid('getrowdata', rowindex[i]);
	   			if(i == rowindex.length-1){
						storageSids+= data.resSid;
					}else{
						storageSids+= data.resSid+",";
					}
	   		}
	   		
	   		// 提交添加存储
	   		Core.AjaxRequest({
					url : ws_url + "/rest/hosts/addHostMountedStorage/"+resTopologySid+"/"+storageSids+"",
					type:"get",
					callback : function (data) {
						for(var i=0;i<rowindex.length;i++){
							$("#addHostCanStorageDatagrid").jqxGrid('deleterow', rowindex[i]);
						}
						// 清除datagrid的选择项
						$('#addHostCanStorageDatagrid').jqxGrid('clearselection');
						
						// 刷新已挂载datagrid
						var hostStorage = new addMountedHostStorageModel(); 
						hostStorage.searchMountedStorageData(resTopologySid);
				    },
				    failure:function(data){
				    	
				    }
				});
	   	}
}


  
  