var poolPosDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    	 "qm.objStoNameLike" : null,
	    	 "qm.parentTopologySid" :null,
	    	 "qm.resPoolSid" : resTopologySid,
	    	 resTopologySid:resTopologySid
		};
	    
	    // 用户数据
	    this.searchPosInfo = function(){
	    	var dataAdapter = Core.getPagingDataAdapter({
				gridId: "poolPOSDatagrid",
				url : ws_url + "/rest/objStorages",
				params: me.searchObj
			});
			$("#poolPOSDatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    };
	    
	    // 统计资源分区下的ObjectStorage信息
	    this.statisticsObjStrInPn = function(resTopologySid){
	    	var osData = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/objStorages/statistics/posCount/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				osData = data;
	 			}
	 		});
	    	
	    	var all = new Array();
	    	var arr = new Array();
	    	all.push(osData);
	 		var name = ["可用","不可用"];
	 		var value = [osData,0];
		 	for(var j=0;j<2;j++){
	 			var obj = new Object();
	 			var val = (value[j]==null)?0 : value[j];
	 			obj.name = name[j]+"("+val+")";
	 			obj.value = val;
	 			arr.push(obj);
		    }
		 	all.push(arr);
			return all;
	    };
	    
	    this.statisticsObjStrUseInPn = function(resTopologySid){
	    	var osData = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/objStorages/statistics/posUsed/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				osData = data;
	 			}
	 		});
	    	var all = new Array();
	    	var arr = new Array();
	    	all.push(osData);
	 		var name = ["未使用","已使用"];
	 		var value = [osData.withOutOsSums,osData.useOsSum];
		 	for(var j=0;j<2;j++){
	 			var obj = new Object();
//	 			var val = (value[j]==null)?0 : value[j];
	 			obj.name = name[j];
	 			obj.value = value[j];
	 			arr.push(obj);
		    }
		 	all.push(arr);
			return all;
	    };
	    
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	    	 $("#search-pos-objStoNameLike").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	    	 var codeadds = new codeModel({width:200,autoDropDownHeight:true,dropDownWidth:200});
 	    	 codeadds.getCustomCode("search-pos-resTopologySids","/objStorages/findVeBySid","resTopologyName","resTopologySid",true,"POST",{resTopologySid:resTopologySid});
	         $("#search-pos-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initPosDatagrid = function(){
	    	var dataAdapter = Core.getPagingDataAdapter({
				gridId: "poolPOSDatagrid",
				url : ws_url + "/rest/objStorages",
				params: me.searchObj
			});
	          $("#poolPOSDatagrid").jqxGrid({
	              width: "100%",
	              theme:currentTheme,
				  source: dataAdapter,
				  virtualmode: true,
				  rendergridrows: function(){
					  return dataAdapter.records;
				  },
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
//						{ text: '对象存储资源ID', datafield: 'resOsSid'},
						{ text: '对象存储名称', datafield: 'objStorageName'},
						{ text: '所属资源环境', datafield: 'resTopologyName'},
						{ text: '对象存储访问地址', datafield: 'osVisitAddress'},
						{ text: '客户端下载地址', datafield: 'clientDownloadUrl'},
						{ text: '总容量(GB)', datafield: 'totalCapacity',cellsalign:'right'}, 
						/*{ text: '使用率(%)', datafield: 'totalCapacity',cellsalign:'right'}*/
//		                { text: '操作', datafield: '', width:160, cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
//		                	  return "<div style='padding-top:5px;'><a class='datagrid-link' onclick='popVlanMgtWindow("+row+")' href='#'>&nbsp;VLAN管理</a>&nbsp;&nbsp;<a class='datagrid-link' onclick='popRelationDVSwindow("+row+")' href='#'>&nbsp;关联DVS</a>&nbsp;&nbsp;<a class='datagrid-link' onclick='synchronousPoolWithDvs("+row+")' href='#'>&nbsp;同步</a></div>";
//		                 	}
//		                }
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addBtn = $("<div><a id='jqxAddObjStoBtn' onclick='popAddObjectStroagePoolWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editBtn = $("<div><a id='jqxEditObjStoBtn' onclick='popEditObjectStroagePoolWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var deleteBtn = $("<div><a id='jqxDeleteObjStoBtn' onclick='removeObjectStroagePool()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addBtn);
	                  container.append(editBtn);
	                  container.append(deleteBtn);
	                  $("#jqxAddObjStoBtn").jqxButton({width: '60',theme:currentTheme,height: '18px'});
	                  $("#jqxEditObjStoBtn").jqxButton({width: '60',theme:currentTheme,height: '18px',disabled: true});
	                  $("#jqxDeleteObjStoBtn").jqxButton({width: '60',theme:currentTheme,height: '18px',disabled: true});
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#poolPOSDatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#poolPOSDatagrid').jqxGrid('getrowdata', index);
	    		  $("#jqxEditObjStoBtn").jqxButton({disabled: false});
                  $("#jqxDeleteObjStoBtn").jqxButton({disabled: false});
	    		  
	          });
	    };
};
  // 查询计算资源池关联的集群
  function searchPOS(){
	  var pos = new poolPosDatagridModel();
//	  pos.searchObj.resPoolNameLike = $("#search-pos-resTopologySids").val();
	  pos.searchObj["qm.objStoNameLike"] = $("#search-pos-objStoNameLike").val();
	  pos.searchObj["qm.parentTopologySid"] = $("#search-pos-resTopologySids").val()=="全部"?"":$("#search-pos-resTopologySids").val();
	  pos.searchPosInfo();
  }  
  // 删除os池
  function removeObjectStroagePool(){
	  var rowindex = $('#poolPOSDatagrid').jqxGrid('getselectedrowindex');
	  var ok =  $('#jqxDeleteObjStoBtn').jqxButton('disabled');
	  if(!ok && rowindex >= 0){
		  var data = $('#poolPOSDatagrid').jqxGrid('getrowdata', rowindex);
		  Core.confirm({
			  title:"提示",
			  message:"您确定要删除该对象存储资源池吗？",
			  confirmCallback:function(){
				  Core.AjaxRequest({
					  url : ws_url + "/rest/objStorages/deleteObjStorages/"+data.resOsSid,
					  type:"get",
					  callback : function (data) {
		 					// 刷新datagrid
		 					var pos = new poolPosDatagridModel();
	 					    pos.searchPosInfo();
					  }
				  });
			  }
		  });
	  }
  }