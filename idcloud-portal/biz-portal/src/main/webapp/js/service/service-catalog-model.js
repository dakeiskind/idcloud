// 服务目录js
var serviceCatalogModel = function () {
		var me = this;
	    this.itemsServiceInstance = ko.observableArray();
	    this.itemsServiceCatalog = ko.observableArray();
	    var catalogSid = "";

	    // 初始化服务目录等
	    this.initServiceCatalogTree = function(){
	        
	    	$("#saveAddCatalog").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#cancelAddCatalog").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#saveEditCatalog").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#cancelEditCatalog").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        
			$('#jqxTabsCatalog').jqxTabs({width : '79.5%', height : '99.5%', position : 'top', theme : currentTheme});
		    Core.AjaxRequest({
				url : ws_url + "/rest/services/platform/findServiceCatalog",
				params : {},
				type:'post',
				callback : function (data) {
					  
	                var source =
	                {
	                    datatype: "json",
	                    datafields: [
	                        { name: 'catalogSid' },
	                        { name: 'parentCatalogSid' },
	                        { name: 'catalogName' },
	                        { name: 'value' }
	                    ],
	                    id: 'catalogSid',
	                    localdata: data
	                };
	                var dataAdapter = new $.jqx.dataAdapter(source);
	                dataAdapter.dataBind();
	                var records = dataAdapter.getRecordsHierarchy('catalogSid', 'parentCatalogSid', 'items', [{ name: 'catalogName', map: 'label'}]);
	
		            $('#jqxTreeCatalog').jqxTree({source: records, height: '100%', width: '100%', theme : currentTheme});
		            $('#jqxTreeCatalog').jqxTree('expandAll');
		            $('#jqxTreeCatalog').jqxTree('selectItem', null);
				}
			 });
		   
            $('#jqxExpanderCatalog').jqxExpander({ showArrow: false, toggleMode: 'none', width:'20%', height:'99.7%', theme : currentTheme});
            
            $('#jqxTreeCatalog').on('select',function(event) {
    			var args = event.args;
    			var item = $('#jqxTreeCatalog').jqxTree('getItem', args.element);
    			catalogSid = item.value;
    			Core.AjaxRequest({
    				url : ws_url + "/rest/services/platform/findServiceCatalogBySid",
    				params : {
    					"catalogSid" : item.value,
    				},
    				type : 'post',
    				callback : function(data) {
    					me.itemsServiceCatalog(data);
    	 				var sourcedatagrid ={
     			              datatype: "json",
     			              localdata: me.itemsServiceCatalog
    	 			    };
    	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
    	 			    $("#jqxgridCatalog").jqxGrid({source: dataAdapter});
    	 			    
    					$("#catalogAddBtn").jqxButton({disabled: false });
    					$("#catalogEditBtn").jqxButton({disabled: true });
    					$("#catalogDelBtn").jqxButton({disabled: true });
    				}
    			});

    		});
	    };
	    
	    // 初始化服务目录内容
	    this.searchServiceCatalogContent = function(params){
	    	 var defaultSearch = {
	    		
	         };
	    	
	    	 var obj = $.extend(defaultSearch, params);
	    	 Core.AjaxRequest({
				url : ws_url + "/rest/services/platform/findServiceCatalog",
	 			type:'post',
	 			params:obj,
	 			async:false,
	 			callback : function (data) {
	 			   me.itemsServiceCatalog(data);
	 		       me.initServiceCatalogDatagrid(me.itemsServiceCatalog);
	 			}
	 		 });
	    }; 
	    
	    // sunyu add for #132
	    this.updateServiceCatalogDatagrid = function(params){
	    	var defaultSearch = {
		    		
	         };
	    	
	    	 var obj = $.extend(defaultSearch, params);
	    	 Core.AjaxRequest({
					url : ws_url + "/rest/services/platform/findServiceCatalog",
		 			type:'post',
		 			params:obj,
		 			async:false,
		 			callback : function (data) {
		 			   me.itemsServiceCatalog(data);
		 			  var sourcedatagrid ={
		 		              datatype: "json",
		 		              localdata: me.itemsServiceCatalog
		 			    };
		 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		 			    $("#jqxgridCatalog").jqxGrid({source: dataAdapter});
		 			}
		 		 });
	    };
	    // end
	    
	    // 初始化服务定义的数据源
	    this.initServiceCatalogDatagrid = function(data){
	    	var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#jqxgridCatalog").jqxGrid(
	          {
	              width: "98%",
				  theme:currentTheme,
	              columnsresize: true,
	              source: dataAdapter,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              localization: gridLocalizationObj,
	              columns: [
	  	              { text: '服务目录名称', datafield: 'catalogName'},
	                  { text: '父服务目录名称', datafield: 'catalogParentName'},
	                  { text: '服务目录描述', datafield: 'description' },
	                  { text: '服务目录图片', datafield: 'imagePath' },
	                  { text: '状态', datafield: 'statusName' }
	              ],
	              showtoolbar: true,
	              rendertoolbar: function (toolbar) {
	                  var me = this;
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var catalogAddBtn = $("<div><a id='catalogAddBtn' onclick='popAddCatalogItemWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增子目录&nbsp;&nbsp;</a></div>");
	                  var catalogEditBtn = $("<div><a id='catalogEditBtn' onclick='popEditCatalogItemWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>更新目录&nbsp;&nbsp;</a></div>");
	                  var catalogDelBtn = $("<div><a id='catalogDelBtn' onclick='deleteCatalogItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除目录&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(catalogAddBtn);
	                  container.append(catalogEditBtn);
	                  container.append(catalogDelBtn);
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#jqxgridCatalog").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#jqxgridCatalog').jqxGrid('getrowdata', index);
	   			  $("#catalogEditBtn").jqxButton({ width: '80',theme:currentTheme,disabled: false});
	   			  $("#catalogEditBtn").jqxButton({ width: '80',theme:currentTheme,disabled: false});
	   			  $("#catalogDelBtn").jqxButton({ width: '100',theme:currentTheme,disabled: false});
	          });
	    	  
	    	  $("#catalogAddBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
	    	  $("#catalogEditBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });
   			  $("#catalogDelBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
   			  
   			  if(catalogSid != "") {
		    	 $("#catalogAddBtn").jqxButton({disabled: false });
	    	  }

	    };

	    // 初始化模板弹出window
	    this.initServiceCatalogTempPopWindow = function(){
			$("#addCatalogWindow").jqxWindow({
	                width: 400, 
	                height:200,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                theme:currentTheme,
	                cancelButton: $("#cancelAddCatalog"), 
	                modalOpacity: 0.3           
	         });
			
			$("#editCatalogWindow").jqxWindow({
                width: 400, 
                height:200,
                resizable: false,  
                isModal: true, 
                theme:currentTheme,
                autoOpen: false, 
                cancelButton: $("#cancelEditCatalog"), 
                modalOpacity: 0.3           
         });
	    };
	    
	    // 初始化验证规则
	    this.initValidator = function(){
	    	$('#addCatalogForm').jqxValidator({
                rules: [
                          { input: '#add-catalogName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-catalogName', message: '目录名称为0-32个字符!', action: 'keyup, blur', rule: 'length=0,32' }
               ]           
	    	});
	    	
	    	$('#editCatalogForm').jqxValidator({
                rules: [
                          { input: '#edit-catalogName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#edit-catalogName', message: '目录名称为0-32个字符!', action: 'keyup, blur', rule: 'length=0,32' }
               ]           
	    	});	    	
	    	// 新增用户表单验证成功
	    	$('#addCatalogForm').on('validationSuccess', function (event) {
	    		 var catalog = Core.parseJSON($("#addCatalogForm").serializeJson());
	    		 
	    		 // 根据不同的用户类型，传入不同的角色值
	    		 var param = {};
	    		 param.parentCatalogSid = catalogSid;
	    		 var newObj = $.extend(catalog,param);
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/services/platform/addServiceCatalog",
	 				params :newObj,
	 				callback : function (data) {
						me.initServiceCatalogTree();
						me.updateServiceCatalogDatagrid();
						catalogSid = "";
						$("#addCatalogWindow").jqxWindow('close');
	 			    },
	 			    failure:function(data){
	 			    	Core.alert({
	 						title:"提示",
	 						message:"服务目录新增失败！",
	 						callback:function(){
	 							$("#addCatalogWindow").jqxWindow('close');
	 						}
	 					});
	 			    }
	 			});
	    	 });
	    	
	    	//  编辑用户表单验证成功
	    	$('#editCatalogForm').on('validationSuccess', function (event) {
	    		var catalog = Core.parseJSON($("#editCatalogForm").serializeJson());
		   		 // 根据不同的用户类型，传入不同的角色值
		   		 var param = {};

		   		 var newObj = $.extend(catalog,param);
		   		 Core.AjaxRequest({
						url : ws_url + "/rest/services/platform/updateServiceCatalog",
						params :newObj,
						callback : function (data) {
							me.initServiceCatalogTree();
							me.updateServiceCatalogDatagrid();
 							catalogSid = "";
 							$("#editCatalogWindow").jqxWindow('close');
					    },
					    failure:function(data){
					    	Core.alert({
								title:"提示",
								message:"服务目录更新失败！",
								callback:function(){
		 							$("#editCatalogWindow").jqxWindow('close');
								}
							});
					    }
					});
	    	 });
	    	
	    };
	    
	    this.setEditCatalogForm = function(data){
	    	 $("#edit-catalogName").jqxInput({value:data.catalogName});
	         $("#edit-imagePath").jqxInput({value:data.imagePath});
	         $("#edit-description").jqxInput({value:(data.description == null)?"":data.description});
	    };
  };
  
  // 弹出新增子目录window
  function popAddCatalogItemWindow() {
		var ok =  $('#catalogAddBtn').jqxButton('disabled');
		if(!ok) {
			// 初始化用户新增页面
            $("#add-catalogName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
            $("#add-imagePath").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
            $("#add-description").jqxInput({placeHolder: "", height: 46, width: 250, minLength: 1,theme:currentTheme});
            $("#add-catalogName").val("");
            $("#add-imagePath").val("");
            $("#add-description").val("");
			// 初始化新增window位置
            var windowW = $(window).width();
            var windowH = $(window).height();
            $("#addCatalogWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-200)/2 } });
	    	$("#addCatalogWindow").jqxWindow('open');
		}

}
// 提交新增子目录信息
  function addCatalogInfoSubmit(){
  		$('#addCatalogForm').jqxValidator('validate');
  }
  
  // 弹出编辑子目录window
  function popEditCatalogItemWindow() {
		// 初始化用户新增页面
      $("#edit-catalogName").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
      $("#edit-imagePath").jqxInput({placeHolder: "", height: 23, width: 250, minLength: 1,theme:currentTheme});
      $("#edit-description").jqxInput({placeHolder: "", height: 46, width: 250, minLength: 1,theme:currentTheme});
      
      var rowindex = $('#jqxgridCatalog').jqxGrid('getselectedrowindex');
	  if(rowindex >= 0){
  		    var data = $('#jqxgridCatalog').jqxGrid('getrowdata', rowindex);
  		    $("#catalogSid").val(data.catalogSid);
  		    // 将常用的字段可以使用这个方法设置数据
  		    var serviceCatalog = new serviceCatalogModel();
  		    serviceCatalog.setEditCatalogForm(data);
  		   
  		    var windowW = $(window).width(); 
  	    	var windowH = $(window).height(); 
  	    	$("#editCatalogWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-200)/2 } });
  	    	$("#editCatalogWindow").jqxWindow('open');
  	  }
  }
  
  // 提交编辑子目录信息
  function editCatalogInfoSubmit(){
  	// 判断是否通过了验证
  	$('#editCatalogForm').jqxValidator('validate');
  }
// 删除服务目录
  function deleteCatalogItem() {
	  	var rowindex = $('#jqxgridCatalog').jqxGrid('getselectedrowindex');
	  	if(rowindex >= 0){
	  		var data = $('#jqxgridCatalog').jqxGrid('getrowdata', rowindex);
			    	Core.confirm({
						title:"提示",
						message:"确定要删除该服务目录吗？",
						confirmCallback:function(){
							Core.AjaxRequest({
								url : ws_url + "/rest/services/platform/deleteCatalog/"+data.catalogSid,
				 				type:"get",
				 				callback : function (data) {
				 					var serviceCatalog = new serviceCatalogModel();
		 							serviceCatalog.initServiceCatalogTree();
		 							serviceCatalog.updateServiceCatalogDatagrid();		 						
				 			    },
								failure : function(data) {
									console.log(data);
									//Core.alertError(data.message,"失败",null);
								}
				 			});
						}
				});
	  	}
	}

  