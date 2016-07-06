var syscodeModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
    			 "qm.parentCodeValueLike": null,
    			 "qm.codeValuesLike": null
		};
	    // 用户数据
	    this.searchCodeInfo = function(){
	    	//$("#codeOsVersionDatagrid").jqxGrid("gotopage",0);
			var dataAdapter = Core.getPagingDataAdapter({
				gridId: "userdatagrid",
				url: ws_url + "/rest/system/findOsVersion",
				params: me.searchObj
			});
			$("#codeOsVersionDatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    };
	    
	    this.searchCodeInfos = function(){
	    	$("#codeOsVersionDatagrid").jqxGrid("gotopage",0);
			var dataAdapter = Core.getPagingDataAdapter({
				gridId: "userdatagrid",
				url: ws_url + "/rest/system/findOsVersion",
				params: me.searchObj
			});
			$("#codeOsVersionDatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	    	$("#search-code-osType").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
		    $("#search-code-osVersion").jqxInput({placeHolder: "", height: 23, width: 150, minLength: 1,theme:currentTheme});
	        $("#search-code-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        // 初始化下拉列表框 
//			 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
//			 codesearch.getCommonCode("search-code-osType","OS_TYPE",true);
//			 codesearch.getCommonCode("search-code-osVersion","OS_VERSION",true);
	    };
	    // 初始化验证规则
	    this.initValidator = function(){
	    	// 新增用户表单验证成功
	    	$('#addCodeForm').jqxValidator({
                rules: [
                        {input: '#add-sort', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                        {input: '#add-sort', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
                            if(/[^\d]/g.test(input.val())){
                                return false;
                              }else{
                                return true;
                              }
                            }
                          },
                          {input: '#add-codeValue', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          {input: '#add-codeDisplay', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                       ]
	    	
	    	
	    	});

	    	$('#editCodeForm').jqxValidator({
                rules: [
                        
                          {input: '#edit-codeValue', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          {input: '#edit-codeDisplay', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                       ]
	    	
	    	
	    	});

	    	$('#addTypeCodeForm').jqxValidator({
                rules: [
                        
                          {input: '#add-type-codeValue', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                         
                       ]
	    	});

	    	$('#addCodeForm').on('validationSuccess', function (event) {
	    		 var addCode = JSON.parse($("#addCodeForm").serializeJson());
	    		 if(addCode.parentCodeValue == "全部"){
	    			 Core.confirm({
						  title:"提示",
						  message:"请选择操作系统类型"
					  });
					  return false;
	    		 }
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/system/create",
	 				params :addCode,
	 				callback : function (data) {
	 					me.searchCodeInfo();
	 					$("#addCodeWindow").jqxWindow('close');

	 			    },
	 			    failure:function(data){
	 			    	
	 			    }
	 			});
	    	});

	    	$('#addTypeCodeForm').on('validationSuccess', function (event) {
	    		 var addCode = JSON.parse($("#addTypeCodeForm").serializeJson());
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/system/createOsType",
	 				params :addCode,
	 				callback : function (data) {
	 					
	 					$("#addTypeCodeWindow").jqxWindow('close');

	 			    },
	 			    failure:function(data){
	 			    	
	 			    }
	 			});
	    	});

	    	
	    	$('#editCodeForm').on('validationSuccess', function (event) {

		    		 var code = Core.parseJSON($("#editCodeForm").serializeJson());
		    			    	
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/system/updateSystemCode",
		 				params :code,
		 				callback : function (data) {
		 					me.searchCodeInfo();
							$("#editCodeWindow").jqxWindow('close');
		 			    },
		 			    failure:function(data){

		 			    }
		 			});
	    	 });
	    };
	    // 初始化用户datagrid的数据源
	    this.initCodeDatagrid = function(){
			var dataAdapter = Core.getPagingDataAdapter({
				gridId: "codeOsVersionDatagrid",
				url: ws_url + "/rest/system/findOsVersion",
				params: me.searchObj
			});
			
	          $("#codeOsVersionDatagrid").jqxGrid({
	              width: "99.8%",
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
	              selectionmode:"checkbox",
	              localization: gridLocalizationObj,
	              columns: [
						{ text: '排序号', datafield: 'sortNo',cellsalign: 'center', align: 'center',width:50},
						{ text: '操作系统类型', datafield: 'parentCodeValue',align: 'center',cellsalign: 'center',width:100},
						{ text: '操作系统版本', datafield: 'codeValue',align: 'center',cellsalign: 'center',width:300},
						{ text: '操作系统版本(显示值)', datafield: 'codeDisplay',align: 'center',cellsalign: 'center',width:300},
						{ text: '是否启用', datafield: 'enabledName',cellsalign: 'center', align: 'center',width:70},
						{ text: '属性1', datafield: 'attribute1',cellsalign: 'center', align: 'center',width:220},
						{ text: '属性2', datafield: 'attribute2',cellsalign: 'center', align: 'center',width:210}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var addBtn = $("<div><a id='jqxAddCodeBtn' onclick ='popAddCodeItemWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
	                  var editBtn = $("<div><a id='jqxEditCodeBtn' onclick ='popEditCodeItemWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
	                  var deleteBtn = $("<div><a id='jqxDeleteCodeBtn' onclick='removeCodeItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
	                  var refreshBtn = $("<div><a id='jqxRefreshCodeBtn' onclick='refreshCodeItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-loop-alt'></i>刷新&nbsp;&nbsp;</a></div>");
	                  var addTypeBtn = $("<div><a id='jqxAddTypeCodeBtn' onclick='popAddTypeCodeItemWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cog'></i>添加操作系统类型&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(refreshBtn);
	                  container.append(addBtn);
	                  container.append(editBtn);
	                  container.append(deleteBtn);
	                  container.append(addTypeBtn);
	                  
	              }
	          });
	    	  
   			  $("#jqxDeleteCodeBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
	          $("#jqxAddCodeBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme});
	          $("#jqxEditCodeBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme});
	          $("#jqxRefreshCodeBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme});
	          $("#jqxAddTypeCodeBtn").jqxButton({ width: '120',height: '18px', theme:currentTheme});
	          
	    };
	    // 初始化操作按钮
	    this.initOperationBtn = function(){
  			  $("#jqxDeleteCodeBtn").jqxButton({disabled: true});
	    };
	    // 初始化弹出window
	    this.initPopWindow = function(){
			$("#addCodeWindow").jqxWindow({
	                width: 700, 
	                height:260,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#codeCancel"), 
	                theme: currentTheme,
	                modalOpacity: 0.3,
	                initContent:function(){
	                	 // 初始化新增用户页面组件
	        	        $("#add-sort").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
	        	        $("#add-codeValue").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
	        	        $("#add-codeDisplay").jqxInput({placeHolder: "", height: 22, width: 170,minLength: 0,maxLength: 32, theme:currentTheme});
	        	        $("#add-attribute1").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
	        	        $("#add-attribute2").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
	        	       
	        	        $("#codeSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        $("#codeCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        var codesearch = new codeModel({width:170,autoDropDownHeight:true});
	       			 	codesearch.getCommonCode("add-parentCodeValue","OS_TYPE",true);
	       			 	
	       			 	var localData = [
	       		 	              {label:"是",value:"1"},
	       		 	              {label:"否",value:"0"},
	       		 	           ];
	       		 	          var source ={
	       		 	            datatype: "json",
	       		 	            datafields: [
	       		 	                { name: 'label' },
	       		 	                { name: 'value' }
	       		 	            ],
	       		 	            localData:localData
	       		 	          };
	       		 	        var dataAdapter = new $.jqx.dataAdapter(source);
	       		 	      
	       		 	        $("#add-enabled").jqxDropDownList({
	       		 	            selectedIndex: 0, source: dataAdapter, autoDropDownHeight: true, displayMember: "label", valueMember: "value",theme:currentTheme, width: 170, height: 22
	       		 	        });
	                }
	         });

			$("#addTypeCodeWindow").jqxWindow({
	                width: 700, 
	                height:160,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#typecodeCancel"), 
	                theme: currentTheme,
	                modalOpacity: 0.3,
	                initContent:function(){
	                	 // 初始化新增用户页面组件
	        	        $("#add-type-codeValue").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
	        	        $("#add-type-attribute1").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
	        	        $("#add-type-attribute2").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
	        	       
	        	        $("#typecodeSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	        $("#typecodeCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	       			 	
	       			 	var localData = [
	       		 	              {label:"是",value:"1"},
	       		 	              {label:"否",value:"0"},
	       		 	           ];
	       		 	          var source ={
	       		 	            datatype: "json",
	       		 	            datafields: [
	       		 	                { name: 'label' },
	       		 	                { name: 'value' }
	       		 	            ],
	       		 	            localData:localData
	       		 	          };
	       		 	        var dataAdapter = new $.jqx.dataAdapter(source);
	       		 	      
	       		 	        $("#add-type-enabled").jqxDropDownList({
	       		 	            selectedIndex: 0, source: dataAdapter, autoDropDownHeight: true, displayMember: "label", valueMember: "value",theme:currentTheme, width: 170, height: 22
	       		 	        });
	                }
	         });
			$("#editCodeWindow").jqxWindow({
			   width: 700, 
               height:260,
               resizable: false,  
               isModal: true, 
               autoOpen: false, 
               cancelButton: $("#editCodeCancel"), 
               theme: currentTheme,
               modalOpacity: 0.3, 
               initContent: function () {
               	 // 初始化编辑用户页面组件
       	        $("#edit-codeDisplay").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
       	        $("#edit-codeValue").jqxInput({placeHolder: "", height: 22, width: 170,minLength: 0,maxLength: 32, theme:currentTheme});
       	        $("#edit-attribute1").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
       	        $("#edit-attribute2").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});

       	        $("#editCodeSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
       	        $("#editCodeCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
       	        
	       	     var codeadd = new codeModel({width:170,autoDropDownHeight:true});
	             codeadd.getCommonCode("edit-parentCodeValue","OS_TYPE",false);

       	        var localData = [
   		 	              {label:"是",value:"1"},
   		 	              {label:"否",value:"0"},
   		 	           ];
   		 	          var source ={
   		 	            datatype: "json",
   		 	            datafields: [
   		 	                { name: 'label' },
   		 	                { name: 'value' }
   		 	            ],
   		 	            localData:localData
   		 	          };
   		 	        var dataAdapter = new $.jqx.dataAdapter(source);
   		 	      
   		 	        $("#edit-enabled").jqxDropDownList({
   		 	            selectedIndex: 0, source: dataAdapter, autoDropDownHeight: true, displayMember: "label", valueMember: "value",theme:currentTheme, width: 170, height: 22
   		 	        });
               }
            });
	    };

  };

  // 弹出新增用户window
  function popAddCodeItemWindow () {
	 // var user = new syscodeModel();
	  	// 初始化用户新增页面
	  $("#add-sort").val("");
      $("#add-codeValue").val("");
      $("#add-codeDisplay").val("");
      $("#add-attribute1").val("");
      $("#add-attribute2").val("");
      var codeadd = new codeModel({width:170,autoDropDownHeight:true});
      codeadd.getCommonCode("add-parentCodeValue","OS_TYPE",true);
		// 初始化新增window位置
	  var windowW = $(window).width(); 
	  var windowH = $(window).height(); 
	  $("#addCodeWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-260)/2 } });
	  $("#addCodeWindow").jqxWindow('open');
  };
  
  // 提交新增用户数据
  function submitAddCodeInfo (){
  	// 判断是否通过了验证
  	$('#addCodeForm').jqxValidator('validate');
  	
  };

  function popAddTypeCodeItemWindow(){
  	  $("#add-type-codeValue").val("");
      $("#add-type-attribute1").val("");
      $("#add-type-attribute2").val("");
		// 初始化新增window位置
	  var windowW = $(window).width(); 
	  var windowH = $(window).height(); 
	  $("#addTypeCodeWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-160)/2 } });
	  $("#addTypeCodeWindow").jqxWindow('open');
  }

  function submitAddTypeCodeInfo(){
  		$('#addTypeCodeForm').jqxValidator('validate');
  }
  
  // 弹出编辑用户window
  function popEditCodeItemWindow(row) {
	 
	  var row = $('#codeOsVersionDatagrid').jqxGrid('selectedrowindexes');
  	  if(row >= 0){
  		    var data = $('#codeOsVersionDatagrid').jqxGrid('getrowdata', row);
  			
  		    $("#codeSid").val(data.codeSid);
 			$("#edit-codeValue").val(data.codeValue);
            $("#edit-codeDisplay").val(data.codeDisplay);
            $("#edit-attribute1").val(data.attribute1);
            $("#edit-attribute2").val(data.attribute2);
            $("#edit-enabled").val(data.enabled);
            $("#edit-parentCodeValue").val(data.parentCodeValue);
            $("#edit-sort").val(data.sort);

  		    var windowW = $(window).width(); 
  	    	var windowH = $(window).height(); 
  	    	$("#editCodeWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-260)/2 } });
  	    	$("#editCodeWindow").jqxWindow('open');
  	}
  };
  
  /** 提交编辑用户信息 */
  function submitEditCodeInfo (){
  	// 判断是否通过了验证
  	$('#editCodeForm').jqxValidator('validate');
  };
  
  /** 删除用户 */
  function removeCodeItem (){
	
  	var rowindex = $('#codeOsVersionDatagrid').jqxGrid('getselectedrowindexes');
  	var codeSids = "";
  	if(rowindex.length > 0){
  		for(var i=0;i<rowindex.length;i++){
  			var data = $('#codeOsVersionDatagrid').jqxGrid('getrowdata', rowindex[i]);
   			if(i == rowindex.length-1){
   				if(data != null && data != "" && data != 'undefined' ){
   					codeSids += data.codeSid;
				}
   				
			}else{
				if(data != null && data != "" && data != 'undefined' ){
					codeSids += data.codeSid + ",";
				}
			}
   		}
    	Core.confirm({
			title:"提示",
			message:"确定要删除选中的操作系统吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
	 				url : ws_url + "/rest/system/deleteSystemCode?codeSids="+codeSids,
	 				type:"get",
	 				callback : function (data) {
	 					// 清除选择项
	 					$('#codeOsVersionDatagrid').jqxGrid('clearselection');
	 					// 刷新datagrid
	 					var code = new syscodeModel();
	 					code.searchCodeInfo();
	 			    }
	 			});
			}
	   });
  	}
};
/**刷新*/
function refreshCodeItem(){
	var code = new syscodeModel();
    code.searchCodeInfo();
}
  
   /** 查询用户 */
  function searchCodeOsVersion(){
	  var code = new syscodeModel();
	  code.searchObj["qm.parentCodeValueLike"] = $("#search-code-osType").val()=="全部"?"":$("#search-code-osType").val();
	  code.searchObj["qm.codeValuesLike"] = $("#search-code-osVersion").val()=="全部"?"":$("#search-code-osVersion").val();
  	  code.searchCodeInfos();
  }; 
  