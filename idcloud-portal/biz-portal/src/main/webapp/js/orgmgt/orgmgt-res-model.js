
var orgresModel = function () {
		var me = this;
	    this.itemsOrg = ko.observableArray();
	    var orgSid = "";
	 // 定义右击弹出框
	    var contextMenu;
	    
	    // 初始化组织树等
	    this.initOrgTree = function(){
	        
	    	$("#saveAddOrg").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#cancelAddOrg").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#saveEditOrg").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        $("#cancelEditOrg").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        
			$('#jqxTabsOrg').jqxTabs({width : '79.5%', height : '98.8%', position : 'top', theme : currentTheme});
	    	var source;	    	// 查询出user值
		     Core.AjaxRequest({
				url : ws_url + "/rest/org/findAllOrg",
				params : {},
				type:'post',
				callback : function (data) {
					  
	                var source =
	                {
	                    datatype: "json",
	                    datafields: [
	                        { name: 'orgSid' },
				             { name: 'parentOrgSid' },
				             { name: 'orgText' },
				             { name: 'orgValue' }
	                    ],
	                    id: 'orgSid',
	                    localdata: data
	                };
	                var dataAdapter = new $.jqx.dataAdapter(source);
	                dataAdapter.dataBind();
	                var records = dataAdapter.getRecordsHierarchy('orgSid', 'parentOrgSid', 'items', [{ name: 'orgText', map: 'label'},{ name: 'orgSid', map: 'value'}]);
	
		            $('#jqxTreeOrg').jqxTree({source: records, height: '100%', width: '100%', theme : currentTheme});
		            $('#jqxTreeOrg').jqxTree('expandAll');
		            $('#jqxTreeOrg').jqxTree('selectItem', null);
		            

				}
			 });
		   
            $('#jqxExpanderOrg').jqxExpander({ showArrow: false, toggleMode: 'none', width:'20%', height:'99%', theme : currentTheme});
            
            $('#jqxTreeOrg').on('select',function(event) {

    			var args = event.args;
    			var item = $('#jqxTreeOrg').jqxTree('getItem', args.element);
    			orgSid = item.value;
    			Core.AjaxRequest({
    				url : ws_url + "/rest/org/"+orgSid+"",
    				params : {},
    				type : 'get',
    				callback : function(data) {
    					me.itemsOrg(data);
    	 				var sourcedatagrid ={
     			              datatype: "json",
     			              localdata: me.itemsOrg
    	 			    };
    	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
    	 			    $("#jqxgridOrg").jqxGrid({source: dataAdapter});
    	 			    
    				}
    			});

    		});
            

   	    };
	    
	    // 初始化组织树等
	    this.initResTree = function(){
	        
	    	var source;	    	// 查询出user值
		     Core.AjaxRequest({
		    	url : ws_url + "/rest/topologys",
	 			type:'post',
	 			params:{
	 				resPoolType:"'RES-POOL-VM','RES-POOL-PM'",
	 				resType:"RES-HOST"
	 			},
				callback : function (data) {
					  
				var source ={
		                 datatype: "json",
		                 datafields: [
		                     { name: 'topologySid' },
		                     { name: 'topologyParentSid' },
		                     { name: 'topologyText' },
		                     { name: 'topologyValue' }
		                 ],
		                 id: 'topologySid',
		                 localdata: data
		             };
		             // create data adapter. 
		             var dataAdapter = new $.jqx.dataAdapter(source);
		             // perform Data Binding.
		             dataAdapter.dataBind();
		             var records = dataAdapter.getRecordsHierarchy('topologySid', 'topologyParentSid', 'items', [{ name: 'topologyText', map: 'label'},{ name: 'topologyValue', map: 'value'}]);
		             // create jqxTree
		             $('#jqxTreeHost').jqxTree({ source: records, width: '99.7%',checkboxes: true, height:'50%',allowDrag:false});
		             $('#jqxTreeHost').jqxTree('selectItem', $("#jqxTreeHost").find('li:first')[0]);
		             $('#jqxTreeHost').jqxTree('expandItem', $("#jqxTreeHost").find('li:first')[0]);
				}
			 });

   	    };
   	    
   	    
	    // 初始化服务目录内容
	    this.searchOrgContent = function(params){
	    	 var defaultSearch = {
	    		
	         };
	    	
	    	 var obj = $.extend(defaultSearch, params);
	    	 Core.AjaxRequest({
	    		url : ws_url + "/rest/org/findAllOrg",
				params : {},
				type:'post',
	 			async:false,
	 			callback : function (data) {
	 			   me.itemsOrg(data);
	 		       me.initOrgDatagrid(me.itemsOrg);
	 			}
	 		 });
	    }; 
	    
	    // 初始化服务定义的数据源
	    this.initOrgDatagrid = function(data){
	    	var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	          $("#jqxgridOrg").jqxGrid(
	          {
	              width: "98%",
				  theme:currentTheme,
	              columnsresize: true,
	              source: dataAdapter,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              localization: gridLocalizationObj,
	              editable: true,
	              selectionmode:"checkbox",
	              columns: [
	  	              { text: '组织名称', datafield: 'orgName'},
	                  { text: '父组织名称', datafield: 'parentOrgName'},
	                  { text: '序号', datafield: 'sortRank' },
	                  { text: '组织描述', datafield: 'orgDesc' },
	                  { text: '创建人', datafield: 'createdBy' }
	              ],
	              showtoolbar: true,
	              rendertoolbar: function (toolbar) {
	                  var me = this;
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var orgAddBtn = $("<div><a id='orgAddBtn' data-bind='click: orgAddItem'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>保存关联&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(orgAddBtn);

	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#jqxgridOrg").on('rowselect', function (event) {
	    	
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#jqxgridOrg').jqxGrid('getrowdata', index);
				  $("#orgAddBtn").jqxButton({disabled: false });
				  

	          });
	    	  
	    	  $("#orgAddBtn").jqxButton({width: '60',theme:currentTheme,height: '18px', disabled: true });   			  

	    };

	    // 初始化模板弹出window
	    this.initOrgTempPopWindow = function(){
			$("#addOrgWindow").jqxWindow({
	                width: 400, 
	                height:220,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                theme:currentTheme,
	                cancelButton: $("#cancelAddOrg"), 
	                modalOpacity: 0.3           
	         });
			
			$("#editOrgWindow").jqxWindow({
                width: 400, 
                height:220,
                resizable: false,  
                isModal: true, 
                theme:currentTheme,
                autoOpen: false, 
                cancelButton: $("#cancelEditOrg"), 
                modalOpacity: 0.3           
         });
	    };
	    
	    // 初始化验证规则
	    this.initValidator = function(){

			$('#addOrgForm').jqxValidator({
		        rules: [  
		                  { input: '#add-orgName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-orgName', message: '组织名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },

		               ]
			});
			
		    	
			// 新增Vlan池验证成功
			$('#addOrgForm').on('validationSuccess', function (event) {
				 var pool = Core.parseJSON($("#addOrgForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/org/create",
		 				params :pool,
		 				callback : function (data) {
		 					// 刷新基本信息
		 					me.initOrgTree();
							$("#addOrgWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
							$("#addOrgWindow").jqxWindow('close');
		 			    }
		 			});
		     });
			
			$('#editOrgForm').jqxValidator({
		        rules: [  
		                  { input: '#edit-orgName', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
		                  { input: '#edit-orgName', message: '组织名称不能超过32个字符!', action: 'keyup, blur', rule: 'length=1,32' },
		                  
		               ]
			});
		    	
			// 编辑Ip验证成功
			$('#editOrgForm').on('validationSuccess', function (event) {
				 var pool = Core.parseJSON($("#editOrgForm").serializeJson());
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/org/update",
		 				params :pool,
		 				callback : function (data) {
		 					// 刷新基本信息
		 					me.initOrgTree();
							$("#editOrgWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){
							$("#editOrgWindow").jqxWindow('close');
		 			    }
		 			});
		     });
	    	
	    };
	    	    
  };
  
  
  