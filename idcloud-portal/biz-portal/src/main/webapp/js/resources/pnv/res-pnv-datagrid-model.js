var poolPnvDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    	 resPoolNameLike:"",
			 resTopologySid:resTopologySid
		};
	    
	    // 用户数据
	    this.searchPnvInfo = function(){
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/poolVlans",
	 			type:'post',
	 			params:me.searchObj,
	 			async:false,
	 			callback : function (data) {
	 				var sourcedatagrid ={
				              datatype: "json",
				              localdata: data
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#poolPNVDatagrid").jqxGrid({source: dataAdapter});
	 			 }
 		  });
	    };
	    
	    // 统计网络资源池下的vlan信息
	    this.statisticsVlanInPn = function(resTopologySid){
	    	var vlanData = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/vlanRes/statistics/pn/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				vlanData = data;
	 			}
	 		});
	    	
	    	var all = new Array();
	    	var arr = new Array();
	    	all.push(vlanData.totalCount);
	 		var name = ["未使用","已使用"];
	 		var value = [vlanData.unusedCount,vlanData.usedCount];
		 	for(var j=0;j<2;j++){
	 			var obj = new Object();
	 			obj.name = name[j];
	 			obj.value =(value[j] == null)? 0:value[j];
	 			arr.push(obj);
		    }
		 	all.push(arr);
			return all;
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-pnv-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-pnv-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initPnvDatagrid = function(){
	          $("#poolPNVDatagrid").jqxGrid({
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
						{ text: 'VLAN池名称', datafield: 'resPoolName'},
						{ text: 'VLAN池类型', datafield: 'vlanPoolTypeName'},
						{ text: '描述', datafield: 'description',width:150},
						{ text: 'VLAN个数', datafield: 'vlanCount',cellsalign:'right',}, 
		                { text: '操作', datafield: '', width:160, cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
		                	  return "<div style='padding-top:5px;'><a class='datagrid-link' onclick='popVlanMgtWindow("+row+")' href='#'>&nbsp;VLAN管理</a>&nbsp;&nbsp;<a class='datagrid-link' onclick='popRelationDVSwindow("+row+")' href='#'>&nbsp;关联DVS</a>&nbsp;&nbsp;<a class='datagrid-link' onclick='synchronousPoolWithDvs("+row+")' href='#'>&nbsp;同步</a></div>";
		                 	}
		                }
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addBtn = $("<div><a id='jqxAddBtn' onclick='popAddVlanPoolWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editBtn = $("<div><a id='jqxEditBtn' onclick='popEditVlanPoolWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var deleteBtn = $("<div><a id='jqxDeleteBtn' onclick='removeVlanPool()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addBtn);
	                  container.append(editBtn);
	                  container.append(deleteBtn);
	                  $("#jqxAddBtn").jqxButton({width: '60',theme:currentTheme,height: '18px'});
	                  $("#jqxEditBtn").jqxButton({width: '60',theme:currentTheme,height: '18px',disabled: true});
	                  $("#jqxDeleteBtn").jqxButton({width: '60',theme:currentTheme,height: '18px',disabled: true});
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#poolPNVDatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#poolPNVDatagrid').jqxGrid('getrowdata', index);
	    		  $("#jqxEditBtn").jqxButton({disabled: false});
                  $("#jqxDeleteBtn").jqxButton({disabled: false});
	    		  
	          });
	    };
};
  // 查询计算资源池关联的集群
  function searchPNV(){
	  var pnv = new poolPnvDatagridModel();
	  pnv.searchObj.resPoolNameLike = $("#search-pnv-name").val();
	  pnv.searchPnvInfo();
  }
  
  // 删除VLAN池
  function removeVlanPool(){
	    var rowindex = $('#poolPNVDatagrid').jqxGrid('getselectedrowindex');
		var ok =  $('#jqxDeleteBtn').jqxButton('disabled');
	  	if(!ok && rowindex >= 0){
	  		var data = $('#poolPNVDatagrid').jqxGrid('getrowdata', rowindex);
			    Core.confirm({
					title:"提示",
					message:"您确定要删除该VLAN池吗？",
					confirmCallback:function(){
						Core.AjaxRequest({
							url : ws_url + "/rest/poolVlans/delete/"+data.resPoolSid,
			 				type:"get",
			 				callback : function (data) {
			 					var pdm = new poolPnvDatagridModel();
			 					pdm.searchPnvInfo();
			 			    },
			 			});
					}
				});
	  	}
  }
  
  // 弹出Vlan管理Window
  function popVlanMgtWindow(row){
	  var data = $('#poolPNVDatagrid').jqxGrid('getrowdata', row);
	  
	  var vlan = new pnvNetworkMgtVlanGrid();
	  // 编辑框赋值
	  vlan.searchVlanConfigInfo(data.resPoolSid);
  	  // 设置弹出框位置
  	  var windowW = $(window).width(); 
      var windowH = $(window).height(); 
      $("#PnvMgtVlanWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-345)/2 } });
      $("#PnvMgtVlanWindow").jqxWindow('open');
  }
  
  //弹出关联dvsWindow
  function popRelationDVSwindow(row){
	  var data = $('#poolPNVDatagrid').jqxGrid('getrowdata', row);
	  
	  var dvs = new relationDVSGrid();
	  // 获取未关联的DVS
	  dvs.initNoRelationListBox(data.resPoolSid);
	  // 获取已关联的DVS
	  dvs.initRelationListBox(data.resPoolSid);
	  
	  
	  noRelationData = dvs.noRelationData;
	  relationData = dvs.relationData;
	  
	  dvs.initListBoxChangeEvent();
	  
	  var windowW = $(window).width(); 
      var windowH = $(window).height(); 
      $("#relationDVSWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-340)/2 } });
      $("#relationDVSWindow").jqxWindow('open');
  }
  
  // 同步
  function synchronousPoolWithDvs(row){
	  var data = $('#poolPNVDatagrid').jqxGrid('getrowdata', row);
	  
	  Core.AjaxRequest({
			url : ws_url + "/rest/poolVlanVss/sync/"+data.resPoolSid+"",
			type:'get',
			async:false,
			callback : function (data) {
				
			}
	  });
  }