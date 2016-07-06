var virtualImageDatagridModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
//	    	 imageNameLike:"",
//	    	 osType:"",
	    	"qm.imageNameLike":null,
	    	"qm.osType":null,
	        "qm.resTopologyNameVE":null
//	    	"qm.resTopologyNameDC":null,
//	    	"qm.resTopologyNameR":null
		};
	    // 用户数据
	    this.searchVirtualImageInfo = function(){
//	    	 Core.AjaxRequest({
//	 			url : ws_url + "/rest/templates",
//	 			type:'post',
//	 			params:me.searchObj,
//	 			async:false,
//	 			callback : function (data) {
//	 				//console.log(JSON.stringify(data));
//	 				me.items(data);
//	 				var sourcedatagrid ={
// 			              datatype: "json",
// 			              localdata: me.items
//	 			    };
//	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
//	 			    $("#virtualImageDatagrid").jqxGrid({source: dataAdapter});
//	 			    $('#virtualImageDatagrid').jqxGrid({pagesizeoptions: ["10","15"]});
//	 			}
//	 		 });
	    	//$("#virtualImageDatagrid").jqxGrid("gotopage",0);
			var dataAdapter = Core.getPagingDataAdapter({
				gridId: "virtualImageDatagrid",
				url : ws_url + "/rest/templates",
				params: me.searchObj
				
			});
			$("#virtualImageDatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    	 
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initImageInput = function(){
	    	$("#search-image-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-image-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	        var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			codesearch.getCommonCode("search-image-osType","OS_TYPE",true);
			codesearch.getCustomCode("search-image-veType", "/topology", "resTopologyName",
					"resTopologySid", true, "POST", {"parentTopologySid":"","resTopologyType":"VE"});
	    };
//	    this.initImageInput = function(){
//	    	// 初始化查询组件
////	        $("#search-image-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
////	        $("#search-image-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
////	        // 初始化下拉列表框 
//			 var codesearch = new codeModel({width:100,autoDropDownHeight:true});
//			 codesearch.getCommonCode("search-image-osType","OS_TYPE",true);
//			 codesearch.getCustomCode("search-image-rType", "/topology/virtual/regional", "resTopologyName",
//						"resTopologySid", true, "POST", {});
//				codesearch.getCustomCode("search-image-dcType", "/topology", "resTopologyName",
//						"resTopologySid", true, "POST", {"parentTopologySid":$("#search-image-rType").val()});
//				codesearch.getCustomCode("search-image-veType", "/topology", "resTopologyName",
//						"resTopologySid", true, "POST", {"parentTopologySid":$("#search-image-dcType").val()});	
//	        $("#search-image-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
//	        $("#search-image-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
//	        $('#search-image-rType').on('open',function(event){
//				regional1 = $("#search-image-rType").val();
//			});
//	        $('#search-image-rType').on('close',function (event){
//				if(regional1!=$("#search-image-rType").val()){
//					if($("#search-image-rType").val()=="全部"){
//						resTopologySid = null;
//						resTopologyType = null;
//					}else{
//						resTopologySid = $("#search-image-rType").val();
//						resTopologyType = "R";
//					}
//					codesearch.getCustomCode("search-image-dcType", "/topology", "resTopologyName",
//							"resTopologySid", true, "POST", {"parentTopologySid":$("#search-image-rType").val(),"resTopologyType":"DC"});
//					codesearch.getCustomCode("search-image-veType", "/topology", "resTopologyName",
//							"resTopologySid", true, "POST", {"parentTopologySid":$("#search-image-dcType").val(),"resTopologyType":"VE"});
//				}
//			});
//	        $('#search-image-dcType').on('open',function(event){
//				dataCenter1 = $("#search-image-dcType").val();
//			});
//	        $('#search-image-dcType').on('close',function (event){
//				if(dataCenter1!=$("#search-image-dcType").val()){
//					if($("#search-image-dcType").val()=="全部"){
//						if($("#search-image-rType").val()=="全部"){
//							resTopologySid = null;
//							resTopologyType = null;
//						}else{
//							resTopologySid = $("#search-image-rType").val();
//							resTopologyType = "R";
//						}
//					}else{
//						resTopologySid = $("#search-image-dcType").val();
//						resTopologyType = "DC";
//					}
//					codesearch.getCustomCode("search-image-veType", "/topology", "resTopologyName",
//							"resTopologySid", true, "POST", {"parentTopologySid":$("#search-image-dcType").val(),"resTopologyType":"VE"});
//				}
//			});
//	        $('#search-image-veType').on('open',function(event){
//				virtual1 = $("#search-image-veType").val();
//			});
//			$('#search-image-veType').on('close',function (event){
//				if(virtual1!=$("#search-image-veType").val()){
//					if($("#search-image-veType").val()=="全部"){
//						if($("#search-image-dcType").val()=="全部"){
//							if($("#search-image-rType").val()=="全部"){
//								resTopologySid = null;
//								resTopologyType = null;
//							}else{
//								resTopologySid = $("#search-image-rType").val();
//								resTopologyType = "R";
//							}
//						}else{
//							resTopologySid = $("#search-image-dcType").val();
//							resTopologyType = "DC";
//						}
//					}else{
//						resTopologySid = $("#search-image-veType").val();
//						resTopologyType = "VE";
//					}
//				}
//			});
//	    };
	   
	    // 初始化用户datagrid的数据源
	    this.initVirtualImageDatagrid = function(){
	    	var dataAdapter = Core.getPagingDataAdapter({
				gridId: "virtualImageDatagrid",
				url: ws_url + "/rest/templates",
				params: me.searchObj
			});
	    	
	          $("#virtualImageDatagrid").jqxGrid({
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
	                  { text: '模板名称', datafield: 'imageName',width:200},
	                  { text: '模板类型', datafield: 'imageTypeName',align: 'center',cellsalign:'center'},
	                  { text: '操作系统类型', datafield: 'osType'},
	                  { text: '操作系统版本', datafield: 'osVersionName'},
//	                  { text: '区域', datafield: 'resTopologyNameR',width:100},
//	                  { text: '数据中心', datafield: 'resTopologyNameDC',width:100},
	                  { text: '资源环境', datafield: 'resVeSidTp',width:500},
	                  { text: '状态', datafield: 'imageStatus',align: 'center',cellsalign:'center'},
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var searchBtn = $("<div><a id='jqxSearchImageBtn' onclick='popSearchImageDataGridWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-spin3 animate-spin'></i>同步操作系统模板&nbsp;&nbsp;</a></div>");
	                  var search1Btn = $("<div><a id='jqxSearch1ImageBtn' onclick='popRelesaeImageDataGridWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-doc-add'></i>发布操作系统模板&nbsp;&nbsp;</a></div>");
	                  var stopBtn = $("<div><a id='jqxStopImageBtn' onclick='popStopImageDataGridWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-error'></i>停用操作系统模板&nbsp;&nbsp;</a></div>");
	                 // var editBtn = $("<div><a id='jqxEditImageBtn' onclick='popEditImageDataGridWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑虚拟机模板&nbsp;&nbsp;</a></div>");
	                  var deleteBtn = $("<div><a id='jqxDeleteImageBtn' onclick='popDeleteImageDataGridWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-trash'></i>删除操作系统模板&nbsp;&nbsp;</a></div>");
	                  var refreshBtn = $("<div><a id='jqxRefreshImageBtn' onclick='RefreshImageDataGridItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-loop-alt'></i>刷新&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(searchBtn);
	                  container.append(search1Btn);
	                  container.append(stopBtn);
	                  //container.append(editBtn);
	                  container.append(deleteBtn);
	                  container.append(refreshBtn);
	                  
	              }
	          });
	          
	          // 控制按钮是否可用
	    	  $("#virtualImageDatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#virtualImageDatagrid').jqxGrid('getrowdata', index);
	    		  
//	    		  if(data.status == "2"){
//	    			  $("#jqxSearch1ImageBtn").jqxButton({ disabled: false });
//	    			  $("#jqxSearchImageBtn").jqxButton({ disabled: false });
//		   			  $("#jqxRefreshImageBtn").jqxButton({disabled: false});
//		   			 // $("#jqxStopImageBtn").jqxButton({disabled: false});
//		   			  $("#jqxDeleteImageBtn").jqxButton({disabled: false});
//	    		  }else{
//	    			  $("#jqxSearch1ImageBtn").jqxButton({disabled: false });
//	    			  $("#jqxSearchImageBtn").jqxButton({disabled: false });
//		   			  $("#jqxRefreshImageBtn").jqxButton({disabled: false});
//		   			  $("#jqxStopImageBtn").jqxButton({disabled: false});
//		   			  $("#jqxDeleteImageBtn").jqxButton({disabled: false});
//	    		  }
	    		  if(data.status == "01"){
	    			  $("#jqxStopImageBtn").jqxButton({disabled: false});
	    			  $("#jqxDeleteImageBtn").jqxButton({disabled: true});
	    			 // $("#jqxEditImageBtn").jqxButton({disabled: true});
	    		  }else if(data.status == "02"){
	    			  $("#jqxStopImageBtn").jqxButton({disabled: true});
	    			  $("#jqxDeleteImageBtn").jqxButton({disabled: false});
	    			 // $("#jqxEditImageBtn").jqxButton({disabled: false});
	    		  }else{
	    			  $("#jqxSearch1ImageBtn").jqxButton({disabled: false });
	    			  $("#jqxSearchImageBtn").jqxButton({disabled: false });
		   			  $("#jqxRefreshImageBtn").jqxButton({disabled: false});
		   			  $("#jqxStopImageBtn").jqxButton({disabled: true});
		   			  $("#jqxDeleteImageBtn").jqxButton({disabled: false});
		   			//  $("#jqxEditImageBtn").jqxButton({disabled: false});
	    		  }
	          });
	    	  
	    	  $("#jqxSearch1ImageBtn").jqxButton({width: '60',theme:currentTheme,height: '18px'});
	    	  $("#jqxSearchImageBtn").jqxButton({width: '60',theme:currentTheme,height: '18px'});
   			  $("#jqxRefreshImageBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
   			  $("#jqxStopImageBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
   			  $("#jqxDeleteImageBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
   			//  $("#jqxEditImageBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',disabled: true});
	          
	    };
	    this.initOperationBtn = function(){
	  	  $("#jqxStopImageBtn").jqxButton({disabled: true });
	      $("#jqxDeleteImageBtn").jqxButton({disabled: false});
	     // $("#jqxEditImageBtn").jqxButton({disabled: false});
	  };
	    
  };

  //发布虚拟模板
  function popRelesaeImageDataGridWindow(){
	  var rowindex = $('#virtualImageDatagrid').jqxGrid('getselectedrowindex');
	  var data = $('#virtualImageDatagrid').jqxGrid('getrowdata', rowindex);
	  var images = new virtualImageAddModel();
	  images.setImageBasicInfo(data.imageName);
	  images.setImageUUIDInfo(data.uuid);
	  if(data.managementAccount!=null){
		  images.setImageAdminInfo(data.managementAccount);
	  }else{
		  images.setImageAdminInfo("");
	  }
	  if(data.managementPassword!=null){
		  images.setImagePasswdInfo(data.managementPassword);
	  }else{
		  images.setImagePasswdInfo("");
	  }
//	  images.setImagePasswdInfo(data.managementPassword);
	  images.setImageOsTypeInfo(data.osType);
	  images.setImageOsVersionInfo(data.osVersion);
	  images.setImageResVeSidInfo(data.resVeSid);
	  if(data.imageSize!=null){
		  images.setImageSizeInfo(data.imageSize);
	  }else{
		  images.setImageSizeInfo("");
	  }
	 
	  var add = new virtualImageAddModel();
	 
	  add.findVe('add');
	  if(rowindex >= 0){
			var data = $('#virtualImageDatagrid').jqxGrid('getrowdata', rowindex);
		    	var roles = data.resVeSid;
		    	var resVeSid = roles.split(",");
		    	for(var i = 0; i < resVeSid.length; i++) {
		    		var checkbox = $("#add-image-resve").find('[id^=add-image-resve][data='+ resVeSid[i]+']');
		    		if(i==resVeSid.length-1){
						checkbox.jqxCheckBox({disabled:true });
		    		}
		    		if(checkbox && checkbox.length > 0) {
		    			// if(i=0){
   		    // 				checkbox.jqxCheckBox({disabled:true });
   		    // 			}
		    			checkbox.jqxCheckBox('check');
		    			checkbox.val(true);
		    		}
		    	}
		}
	  add.findSoftWare('add');
	  if(rowindex >= 0){
  		var data = $('#virtualImageDatagrid').jqxGrid('getrowdata', rowindex);
  			if (""!=data.installedSoftware && data.installedSoftware!=null) {
  				var softwatrs = data.installedSoftware.split(",");
  				for(var j=0;j<softwatrs.length;j++){
  					Core.AjaxRequest({
  						url : ws_url + "/rest/system/findImageSoftWareTypeByCodeValue/"+softwatrs[j],
			 				type:"get",
			 				async:false,
			 				callback : function (softtypedata) {
			 					if ("db"==softtypedata[0].parentCodeValue) {
			 						var checkbox = $("#add-imageSoftWare-database").find('[id^=add-software-database-][data='+'"'+softwatrs[j]+'"'+']');
		           		    		if(checkbox && checkbox.length > 0) {
		           		    			checkbox.jqxRadioButton('check');
		           		    			checkbox.val(true);
		           		    		}
			 					}
			 					if ("mw"==softtypedata[0].parentCodeValue) {
			 						var checkbox = $("#add-imageSoftWare").find('[id^=add-software-][data='+'"'+softwatrs[j]+'"'+']');
		           		    		if(checkbox && checkbox.length > 0) {
		           		    			checkbox.jqxRadioButton('check');
		           		    			checkbox.val(true);
		           		    		}
			 					}
			 				}
  					});
  				}
  			};
  	}
	  var windowW = $(window).width();
	  var windowH = $(window).height();
	  $("#findImageWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-450)/2 } });
	  $("#findImageWindow").jqxWindow('open');
  }
  
  function popEditImageDataGridWindow(){
	  var rowindex = $('#virtualImageDatagrid').jqxGrid('getselectedrowindex');
	  var data = $('#virtualImageDatagrid').jqxGrid('getrowdata', rowindex);
	  var images = new virtualImageEditModel();
	  images.setImageBasicInfo(data.imageName);
	  images.setImageUUIDInfo(data.uuid);
	  images.setImageVeInfo(data.resVeSid);
	  images.setImageSidInfo(data.resImageSid);
	  images.setImageOsTypeInfo(data.osType);
	  images.setImageOsVersionInfo(data.osVersion);
	  var windowW = $(window).width();
	  var windowH = $(window).height();
	  $("#editImageWindow").jqxWindow({ position: { x: (windowW-450)/2, y: (windowH-160)/2 } });
	  $("#editImageWindow").jqxWindow('open');
  }
  
  //发现虚拟模板
  function popSearchImageDataGridWindow(){
	  var windowW = $(window).width();
	  var windowH = $(window).height();
	  $("#releaseImageWindow").jqxWindow({ position: { x: (windowW-450)/2, y: (windowH-160)/2 } });
	  $("#releaseImageWindow").jqxWindow('open');;
  }
  //刷新虚拟机模板
  function RefreshImageDataGridItem(){
	  var image = new virtualImageDatagridModel();
	  image.searchVirtualImageInfo();
  }
  
  // 查询镜像模板
  function searchImage(){
	  var image = new virtualImageDatagridModel();
	  image.searchObj["qm.imageNameLike"] = $("#search-image-name").val();
	  image.searchObj["qm.osType"] = $("#search-image-osType").val()=="全部"?"":$("#search-image-osType").val();
	  image.searchObj["qm.resTopologyNameVE"] = $("#search-image-veType").val()=="全部"?"":$("#search-image-veType").val();
  	  image.searchVirtualImageInfo();
  }
  
  //删除虚机模板
  
  function popDeleteImageDataGridWindow(){
	  var rowindex = $('#virtualImageDatagrid').jqxGrid('getselectedrowindex');
	  var ok =  $("#jqxDeleteImageBtn").jqxButton("disabled");
	  if(rowindex >= 0 && !ok){
		  var data = $('#virtualImageDatagrid').jqxGrid('getrowdata', rowindex);
		  Core.confirm({
			  title:"提示",
			  message:"您确定要删除该操作系统模板吗？",
			  confirmCallback:function(){
				  Core.AjaxRequest({
					  url : ws_url + "/rest/templates/delete/"+data.resImageSid,
					  type:"get",
					  callback : function (data) {
//						  var image = new virtualImageDatagridModel();
//		 					image.searchVirtualImageInfo();
						  searchImage();
					  }
				  });
			  }
		  });
	  }
  }
  
  //停用虚拟机模板
  
  function popStopImageDataGridWindow(){
	  var rowindex = $('#virtualImageDatagrid').jqxGrid('getselectedrowindex');
	  var ok =  $("#jqxStopImageBtn").jqxButton("disabled");
	  if(rowindex >= 0 && !ok){
		  var data = $('#virtualImageDatagrid').jqxGrid('getrowdata', rowindex);
		  //console.log(JSON.stringify(data));
		  if("01"==data.status){
			  Core.confirm({
				  title:"提示",
				  message:"您确定要停用该操作系统模板吗？",
				  confirmCallback:function(){
					  Core.AjaxRequest({
						  url : ws_url + "/rest/templates/updatestatus/"+data.resImageSid,
						  type:"get",
						  callback : function (data) {
//							  var image = new virtualImageDatagridModel();
//			 				  image.searchVirtualImageInfo();
							  searchImage();
			 				  image.initOperationBtn();
						  }
					  });
				  }
			  });
		  }else if("00"==data.status){
			  Core.confirm({
				  title:"提示",
				  message:"该模板状态为已新建，请选择状态为已发布的模板！",
			  });
		  }else if("02"==data.status){
			  Core.confirm({
				  title:"提示",
				  message:"该模板状态为已停用，请选择状态为已发布的模板！",
			  });
		  }
	  }
  }
  
  

  