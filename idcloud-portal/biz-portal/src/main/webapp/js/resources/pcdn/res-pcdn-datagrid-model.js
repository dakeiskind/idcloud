var poolPcdnDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	         "qm.cdnNameLike" : null,
		     "qm.parentTopologySid" :null,
	    	 "qm.resPoolSid" : resTopologySid,
	    	 resTopologySid:resTopologySid
		};
	    
	    // 用户数据
	    this.searchPcdnInfo = function(){
	    	var dataAdapter = Core.getPagingDataAdapter({
				gridId: "poolPCDNDatagrid",
				url : ws_url + "/rest/cdn/searchPcdn",
				params: me.searchObj
			});
			$("#poolPCDNDatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    };
	    
	    // 统计资源分区下的ObjectStorage信息
	    this.statisticsCDNInPool = function(resTopologySid){
	    	var osData = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/cdn/statistics/pcdnCount/"+resTopologySid,
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
	    
//	    this.statisticsObjStrUseInPn = function(resTopologySid){
//	    	var osData = null;
//	    	Core.AjaxRequest({
//	 			url : ws_url + "/rest/objStorages/statistics/posUsed/"+resTopologySid,
//	 			type:'get',
//	 			async:false,
//	 			callback : function (data) {
//	 				osData = data;
//	 			}
//	 		});
//	    	var all = new Array();
//	    	var arr = new Array();
//	    	all.push(osData);
//	 		var name = ["未使用","已使用"];
//	 		var value = [osData.withOutOsSums,osData.useOsSum];
//		 	for(var j=0;j<2;j++){
//	 			var obj = new Object();
////	 			var val = (value[j]==null)?0 : value[j];
//	 			obj.name = name[j];
//	 			obj.value = value[j];
//	 			arr.push(obj);
//		    }
//		 	all.push(arr);
//			return all;
//	    };
	    
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-pcdn-cdnNameLike").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-pcdn-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        var codeadds = new codeModel({width:200,autoDropDownHeight:true,dropDownWidth:200});
	    	 codeadds.getCustomCode("search-pcdn-resTopologySids","/objStorages/findVeBySid","resTopologyName","resTopologySid",true,"POST",{resTopologySid:resTopologySid});
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initPcdnDatagrid = function(){
	    	var dataAdapter = Core.getPagingDataAdapter({
				gridId: "poolPCDNDatagrid",
				url : ws_url + "/rest/cdn/searchPcdn",
				params: me.searchObj
			});
	          $("#poolPCDNDatagrid").jqxGrid({
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
						{ text: 'CDN名称', datafield: 'cdnName'},
						{ text: '所属资源环境', datafield: 'parentTopologyName'},
						{ text: 'CDN地址', datafield: 'cdnAddress'}, 
//		                { text: '操作', datafield: '', width:160, cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
//		                	  return "<div style='padding-top:5px;'><a class='datagrid-link' onclick='popVlanMgtWindow("+row+")' href='#'>&nbsp;VLAN管理</a>&nbsp;&nbsp;<a class='datagrid-link' onclick='popRelationDVSwindow("+row+")' href='#'>&nbsp;关联DVS</a>&nbsp;&nbsp;<a class='datagrid-link' onclick='synchronousPoolWithDvs("+row+")' href='#'>&nbsp;同步</a></div>";
//		                 	}
//		                }
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addBtn = $("<div><a id='jqxAddCDN' onclick='popAddCDNPoolWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editBtn = $("<div><a id='jqxEditCDNBtn' onclick='popEditCDNPoolWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var deleteBtn = $("<div><a id='jqxDeleteCDNBtn' onclick='removeCDNPool()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addBtn);
	                  container.append(editBtn);
	                  container.append(deleteBtn);
	                  $("#jqxAddCDN").jqxButton({width: '60',theme:currentTheme,height: '18px'});
	                  $("#jqxEditCDNBtn").jqxButton({width: '60',theme:currentTheme,height: '18px',disabled: true});
	                  $("#jqxDeleteCDNBtn").jqxButton({width: '60',theme:currentTheme,height: '18px',disabled: true});
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#poolPCDNDatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#poolPCDNDatagrid').jqxGrid('getrowdata', index);
	    		 
	    			  $("#jqxEditCDNBtn").jqxButton({disabled: false});
	                  $("#jqxDeleteCDNBtn").jqxButton({disabled: false});
	    		  
	          });
	    };
};
  // 查询计算资源池关联的集群
  function searchPcdn(){
	  var pcdn = new poolPcdnDatagridModel();
	  //pcdn.searchObj.resPoolNameLike = $("#search-pos-osVisitAddress").val();
	  pcdn.searchObj["qm.cdnNameLike"] = $("#search-pcdn-cdnNameLike").val();
	  pcdn.searchObj["qm.parentTopologySid"] = $("#search-pcdn-resTopologySids").val()=="全部"?"":$("#search-pcdn-resTopologySids").val();
	  pcdn.searchPcdnInfo();
  }  
  // 删除os池
  function removeCDNPool(){
	  var rowindex = $('#poolPCDNDatagrid').jqxGrid('getselectedrowindex');
	  var ok =  $('#jqxDeleteCDNBtn').jqxButton('disabled');
	  if(!ok && rowindex >= 0){
		  var data = $('#poolPCDNDatagrid').jqxGrid('getrowdata', rowindex);
		  Core.confirm({
			  title:"提示",
			  message:"您确定要删除该CDN吗？",
			  confirmCallback:function(){
				  Core.AjaxRequest({
					  url : ws_url + "/rest/cdn/deleteCDN/"+data.cdnSid,
					  type:"get",
					  callback : function (data) {
		 					// 刷新datagrid
						  var pcdn = new poolPcdnDatagridModel();
		 					pcdn.searchPcdnInfo();
					  }
				  });
			  }
		  });
	  }
  }