var virtualVcDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		 resTopologyName:"",
		    	 resTopologyType:"VC",	
		    	 parentTopologySid:resTopologySid
		};
	    // 查询用户名是否重复
	    this.searchUserByName = function(name){
	    	var Todata = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/user/findAll",
	 			type:'post',
	 			params:{
	 				accountRepeat:name
	 			},
	 			async:false,
	 			callback : function (data) {
	 				Todata = data;
	 			}
	 		 });
	    	return Todata;
	    };
	    // 用户数据
	    this.searchVCInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/vcs",
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
	 			    $("#vcdatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initVCInput = function(){
	    	// 初始化查询组件
	        $("#search-vcname").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-vcbutton").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initVCDatagrid = function(){
	          $("#vcdatagrid").jqxGrid({
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
					  { text: '集群名称', datafield: 'clusterName', cellsrenderer: function(row, columnfield, value, defaulthtml, columnproperties){
							  return "<div style='padding-top:3px;'><a class='datagrid-link' onclick='goVcDetailPage("+row+")' href='#'>&nbsp;"+value+"</a></div>";
					  }},
	                  { text: '资源环境', datafield: 'resTopologyName'},
	                  { text: 'CPU总核数(核)', datafield: 'cpuTotalCount',cellsalign:'right',width:90},
	                  { text: 'CPU分配率', datafield: 'cpuAllotRate',cellsalign:'right',width:90},
	                  { text: '内存总容量(GB)', datafield: 'memoryTotalVolume',cellsalign:'right',width:90},
	                  { text: '内存分配率', datafield: 'memoryAllotRate',cellsalign:'right',width:90},
//	                  { text: '存储总量(GB)', datafield: 'storageTotalVolume',cellsalign:'right',width:90},
//	                  { text: '存储分配率', datafield: 'storageAllotVolumeRate',cellsalign:'right',width:90},
	                  { text: '本地存储总量(GB)', datafield: 'storageLocalTotalVolume',cellsalign:'right',width:90},
	                  { text: '本地存储分配率', datafield: 'storageLocalAllotVolumeRate',cellsalign:'right',width:90},
	                  { text: '共享存储总量(GB)', datafield: 'storageShareTotalVolume',cellsalign:'right',width:90},
	                  { text: '共享存储分配率', datafield: 'storageShareAllotVolumeRate',cellsalign:'right',width:90},
	                  { text: '主机(个)', datafield: 'hostTotalCount',cellsalign:'right',width:70},
	                  { text: '虚拟机(个)', datafield: 'vmTotalCount',cellsalign:'right',width:70},
	                  { text: 'HA功能', datafield: 'openHaName',align: 'center',cellsalign:'center',width:50}	            
	                  
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var refreshBtn = $("<div><a id='jqxRefreshBtn' onclick='refreshVcGrid()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(refreshBtn);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#vcdatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#vcdatagrid').jqxGrid('getrowdata', index);
	    		  
	    		  if(data.status == "2"){
	    			  $("#jqxRefreshBtn").jqxButton({disabled: false});
	    		  }else{
	    			  $("#jqxRefreshBtn").jqxButton({disabled: false});
	    		  }
	          });
	    	  
	    	  $("#jqxRefreshBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme});
	          
	    };

  };
  
  function searchVC(){
	  var vc = new virtualVcDatagridModel();
	  
	  vc.searchObj.resTopologyName = $("#search-vcname").val();
	  vc.searchVCInfo();
  }
 
  // 跳转到集群详情页面
  function goVcDetailPage(row){
	// 通过Grid的index获得主机信息
	  var data = $('#vcdatagrid').jqxGrid('getrowdata', row);
	  
	  var items = $('#jqxTreeVirtual').jqxTree('getItems');
	  var index = 0;
	  for(var i=0;i<items.length;i++){
		  var attr = items[i].value.split(",");
		  if(data.resTopologySid == attr[0]){
			 index = i;
			 break;
		  }
	  }
	  
	  $('#jqxTreeVirtual').jqxTree('selectItem', $("#jqxTreeVirtual").find('li')[index]);
	  $('#jqxTreeVirtual').jqxTree('expandItem', $("#jqxTreeVirtual").find('li')[index]);
  }
  
  // 刷新vc列表
  function refreshVcGrid(){
	  var vc = new virtualVcDatagridModel();
	  vc.searchObj.resTopologyName = $("#search-vcname").val();
	  vc.searchVCInfo();
  }
  