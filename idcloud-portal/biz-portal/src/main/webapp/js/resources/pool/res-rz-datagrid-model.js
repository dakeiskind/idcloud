var poolRzDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    	 resTopologyNameLike:"",
	    	 resTopologyType:"RZ",	
	    	 resTopologySid:resTopologySid
		};
	    // 查询用户名是否重复
	    this.searchPoolRzByName = function(name){
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
	    this.searchPoolRzInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/topology",
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
	 			    $("#poolRzDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 统计拓扑结构下的网络信息
	    this.statisticsNetworkInTopology = function(resTopologySid){
	    	
	    	var networkData = null;
	    	Core.AjaxRequest({
	 			url : ws_url + "/rest/networks/statistical/topology/"+resTopologySid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				networkData = data;
	 			}
		    });
			var data = new Object();
			data.totalCount = networkData.totalCount;
			data.attr = new Array();
			var value = [networkData.pniCount,networkData.pneCount];
			var name1 =["内部网络","外部网络"];
			for(var i=0; i<2;i++){
				var obj = new Object();
				var t = (value[i] == null)? 0:value[i];
				obj.name = name1[i]+"("+t+")";
				obj.value = t;
				data.attr.push(obj);
			}
			return data;
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-rz-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-rz-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initPoolRzDatagrid = function(){
	          $("#poolRzDatagrid").jqxGrid({
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
	                  { text: '分区名称', datafield: 'resTopologyName'},
	                  { text: '所属数据中心', datafield: 'parentTopologyName'},
	                  { text: '描述', datafield: 'description',width:500},
	                  { text: '最近更新时间', datafield: 'updatedDt',width:160}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addBtn = $("<div><a id='jqxAddBtn' onclick='popAddRzWindow()'> &nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editBtn = $("<div><a id='jqxEditBtn' onclick='popEditRzInGridWindow()' style='margin-left:-1px'> &nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var refreshBtn = $("<div><a id='jqxRefreshBtn' onclick='RefreshRz()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(addBtn);
	                  container.append(editBtn);
	                  container.append(refreshBtn);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#poolRzDatagrid").on('rowselect', function (event) {
	    		  
    			  $("#jqxEditBtn").jqxButton({ disabled: false });
	          });
	          
	          // 初始化按钮
	    	  $("#jqxAddBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme});
	          $("#jqxEditBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme, disabled: true});
   			  $("#jqxRefreshBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
	          
	    };
  };
  
  
  // 刷新datagrid
  function RefreshRz(){
	  var rz = new poolRzDatagridModel();
	  rz.searchPoolRzInfo();
  }
  
  // datagrid中新增资源分区时
  function popEditRzInGridWindow(){
	  var rowindex = $('#poolRzDatagrid').jqxGrid('getselectedrowindex');
	  var ok =  $("#jqxEditBtn").jqxButton("disabled");
  	  if(rowindex >= 0 && !ok){
  		    var data = $('#poolRzDatagrid').jqxGrid('getrowdata', rowindex);
  		    // 赋值
  		    $("#edit-rz-resTopologyName").val(data.resTopologyName);
  		    $("#edit-rz-description").val(data.description);
  		    $("#edit-rz-resTopologySid").val(data.resTopologySid);
  	        // 弹出Window
  	        var windowW = $(window).width(); 
  	  	    var windowH = $(window).height(); 
  	  	    $("#editRzWindow").jqxWindow({ position: { x: (windowW-450)/2, y: (windowH-160)/2 } });
  	  	    $("#editRzWindow").jqxWindow('open');
  	  }
   }
  
  // 查询
  function searchRZ(){
	  var rz = new poolRzDatagridModel();
	  rz.searchObj.resTopologyNameLike = $("#search-rz-name").val(); 
  	  rz.searchPoolRzInfo();
  }