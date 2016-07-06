// 挂载存储 
var mountStorageHostModel = function () {
	 // 初始化挂载存储datagrid的数据源
    this.initMountDatagrid = function(){
  		 // 初始化已挂载存储
		 $("#mountedStorageDatagrid").jqxGrid({
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
                  var addMountStorage = $("<div><a id='addMountStorage'  onclick='popAddHostStorage()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>关联存储&nbsp;&nbsp;</a></div>");
                  var editMountStorage = $("<div><a id='editMountStorage' onclick='removeCanHostStorage()' style='margin-left:-1px' >&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>卸载存储&nbsp;&nbsp;</a></div>");
                  toolbar.append(container);
                  container.append(addMountStorage);
                  container.append(editMountStorage);
              }
	          });
		 
			 $("#mountedStorageDatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#mountedStorageDatagrid').jqxGrid('getrowdata', index);
	   			  $("#editMountStorage").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: false});
	          });
	    	  $("#addMountStorage").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: false });
	  		  $("#editMountStorage").jqxButton({ width: '80',theme:currentTheme,height: '18px',  disabled: true});
  		  
	  		// 初始化可挂载datagrid
	   		 $("#addHostStorageDatagrid").jqxGrid({
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
    	// 挂载存储window
    	$("#mountStorageHosDatagridtWindow").jqxWindow({
            width: 700, 
            height:326,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#mountStorageCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化组件
            	 $("#mountStorageCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px',});
            }
        });
    	
    	// 添加新挂载存储window
    	$("#addHostStorageInfoWindow").jqxWindow({
            width: 600, 
            height:290,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#addHostStorageCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	// 初始化组件
            	 $("#addHostStorageSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            	 $("#addHostStorageCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px',});
            }
        });
    	    
    };
};
	
	//卸载已挂载的存储
	function removeCanHostStorage(){
	// 得到datagrid的选择项
		var rowArr = $('#mountedStorageDatagrid').jqxGrid('getselectedrowindexes');
		var isUsed = true;
		var storageSids ="";
		if(rowArr.length > 0){
			// 判断是否可以移除
			for(var i=0;i<rowArr.length;i++){
				// 查询出该行数据
				var data = $('#mountedStorageDatagrid').jqxGrid('getrowdata', rowArr[i]);
				if(i == rowArr.length-1){
					storageSids+= data.resSid;
				}else{
					storageSids+= data.resSid+",";
				}
				
				if(data.usageStatus == "01" && data.storageCategoryName == "本地存储"){ 
					isUsed = false;
					// 清除datagrid的选择项
					$('#mountedStorageDatagrid').jqxGrid('clearselection');
					break;
				}
			}
			// 判断是否有已使用的存储
			if(isUsed){
				// 移除所选择的存储
				var rowindex = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
		    	var hostData = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
				Core.confirm({
					title:"提示",
					message:"确定要卸载该存储吗?",
					yes:"确定",
					confirmCallback:function(){
						Core.AjaxRequest({
			 				url : ws_url + "/rest/hosts/uninstallMountedStorage/"+hostData.resSid+"/"+storageSids+"",
			 				type:"get",
			 				callback : function (data) {
			 					for(var i=0;i<rowArr.length;i++){
			 						$("#mountedStorageDatagrid").jqxGrid('deleterow', rowArr[i]);
			 					}
			 				    // 清除datagrid的选择项
								$('#mountedStorageDatagrid').jqxGrid('clearselection');
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
 
   // 弹出新增存储window
   function popAddHostStorage(){
		var rowindex = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
	   	if(rowindex >= 0){
	   		var data = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', rowindex);
	  		    // 给已挂载datagrid赋值
	  		    Core.AjaxRequest({
	  	 			url : ws_url + "/rest/hosts/getCanMountStorage/"+data.resSid+"",
	  	 			type:'get',
	  	 			callback : function (data) {
	  	 				var sourcedatagrid ={
	  				              datatype: "json",
	  				              localdata: data
	  	 			    };
	  	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	  	 			    $("#addHostStorageDatagrid").jqxGrid({source: dataAdapter});
	  	 			}
	  	 		 });
	  		    
	  		// 设置弹出框位置
	   		var windowW = $(window).width(); 
	       	var windowH = $(window).height(); 
	       	$("#addHostStorageInfoWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-300)/2 } });
	       	$("#addHostStorageInfoWindow").jqxWindow('open');
	   	}
   }
   
   // 提交新增的存储
   function addHostStorageSubmit(){
	    var storageSids ="";
		var rowindex = $('#addHostStorageDatagrid').jqxGrid('getselectedrowindexes');
		
		var index = $('#hostConfigMgtdatagrid').jqxGrid('getselectedrowindex');
   	    var hostData = $('#hostConfigMgtdatagrid').jqxGrid('getrowdata', index);
		
	   	if(rowindex.length >= 0){
	   		for(var i=0;i<rowindex.length;i++){
	   			var data = $('#addHostStorageDatagrid').jqxGrid('getrowdata', rowindex[i]);
	   			if(i == rowindex.length-1){
						storageSids+= data.resSid;
					}else{
						storageSids+= data.resSid+",";
					}
	   		}
	   		
	   		// 提交添加存储
	   		Core.AjaxRequest({
					url : ws_url + "/rest/hosts/addHostMountedStorage/"+hostData.resSid+"/"+storageSids+"",
					type:"get",
					callback : function (data) {
						for(var i=0;i<rowindex.length;i++){
							$("#addHostStorageDatagrid").jqxGrid('deleterow', rowindex[i]);
						}
						
						// 清除datagrid的选择项
						$('#addHostStorageDatagrid').jqxGrid('clearselection');
						
						// 刷新已挂载datagrid
						 Core.AjaxRequest({
				 			url : ws_url + "/rest/hosts/getMountStorage/"+hostData.resSid+"",
				 			type:'get',
				 			callback : function (data) {
				 				var sourcedatagrid ={
						              datatype: "json",
						              localdata: data
				 			    };
				 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
				 			    $("#mountedStorageDatagrid").jqxGrid({source: dataAdapter});
				 			}
				 		 });
						
				    },
				    failure:function(data){
				    	
				    }
				});
	   	}
   }
   
   
  
  