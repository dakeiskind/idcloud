var cabinetDatagridModels = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    	"qm.nameLike":null,
    	"qm.resTopologyNameVE":null,
    	"qm.resTopologyNameDC":null,
    	"qm.equipRoomSid":null,
    	"qm.findChildBySid":resTopologySid,
    	 parentTopologySid:resTopologySid
	};
    
    this.staObj = {
   	     resTopologySid:resTopologySid
   	};
    
  //统计机框资源查询方法
    this.getDataGridData = function(){
    	 var cabinetData;
    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/phylocations/cabinet/statistical",
	 			type:'post',
	 			params:me.staObj,
	 			async:false,
	 			callback : function (data) {
	 				cabinetData = data;
	 			}
	     });
    	 return cabinetData;
    };
		this.CabinetResourcesStatistics = function(){
			var cabinetData = me.getDataGridData();
			var data = new Object();
			data.attr = new Array();
			data.cabinetCount = cabinetData.staTotalCabinet;
			var value = [cabinetData.staTotalCabinet];
			var name =["使用"];
			for(var i=0; i<1;i++){
				var obj = new Object();
				var val = value[i] == null ? 0:value[i];
				obj.name = name[i]+"("+val+")";
				obj.value = val;
				data.attr.push(obj);
			}
			return data;
		}
    
    this.initCabinetInput = function(){
    	// 初始化查询组件
        $("#search-cabinetName").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-cabinetButton").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
    this.initComboxLinkage = function(){
    	var codeadd = new codeModel({width:150,autoDropDownHeight : false,dropDownWidth:150});
    	codeadd.getCustomCode("mf-cabinet-name","/phylocations/downlist/serverroom","name","equipRoomSid",true,"POST",{resTopologySid:resTopologySid});
    	//codeadd.getCustomCode("add-mf-cabinet-name","/phylocations/downlist/serverroom","name","equipServerRoomSid",false,"POST",{resTopologySid:resTopologySid});
    	//codeadd.getCustomCode("edit-mf-cabinet-name","/phylocations/downlist/serverroom","name","equipServerRoomSid",false,"POST",{resTopologySid:resTopologySid});
    }
    
    this.searchCabinetInfo = function(){
    	$("#cabinetdatagrid").jqxGrid("gotopage",0);
		var dataAdapter = Core.getPagingDataAdapter({
			gridId: "cabinetdatagrid",
			url : ws_url + "/rest/phylocations/find/cabinet",
			params: me.searchObj
		});         
		$("#cabinetdatagrid").jqxGrid({
			source: dataAdapter,
			rendergridrows: function(){
				return dataAdapter.records;
			}
		});
    };
    
    this.initCabinetDatagrid = function(){
    	
    	var dataAdapter = Core.getPagingDataAdapter({
			gridId: "cabinetdatagrid",
			url: ws_url + "/rest/phylocations/find/cabinet",
			params: me.searchObj
		});
          $("#cabinetdatagrid").jqxGrid({
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
              selectionmode:"singlerow",
              localization: gridLocalizationObj,
              columns: [
				{ text: '机柜名称', datafield: 'name',cellsalign:'left',width:200},   
				{ text: '机柜位置', datafield: 'location',cellsalign:'left',width:200},    
				{ text: '区域', datafield: 'resTopologyNameVE',cellsalign:'left',width:150},   
				{ text: '数据中心', datafield: 'resTopologyNameDC',cellsalign:'left',width:150},   
				{ text: '所属机房', datafield: 'equipServerRoomName',cellsalign:'left'},    
				/*{ text: '描述', datafield: 'description',cellsalign:'left',width:170}*/
              ],
              showtoolbar:true,
           // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
//                var refreshBtn = $("<div><a id='refreshCBtn' onclick='refreshCab()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                var addBtn = $("<div><a id='addCBtn' onclick='addCab()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>增加&nbsp;&nbsp;</a></div>");
                var editBtn = $("<div><a id='editCBtn' onclick='editCab()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                var deleteBtn = $("<div><a id='deleteCBtn' onclick='deleteCab()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                var refreshBtn = $("<div><a id='refreshCBtn' onclick='refreshCab()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                toolbar.append(container);
//                container.append(refreshBtn);
                container.append(addBtn);
                container.append(editBtn);
                container.append(deleteBtn);
                container.append(refreshBtn);
                $("#refreshCBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#addCBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#editCBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#deleteCBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
            }
          });
  };
  
  this.initPopWindow = function(){
	  $("#addCabWindow").jqxWindow({
		  width: 400, 
          height:260,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#cabinetCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	  $("#add-cabinet-name").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	  $("#add-cabinet-location").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	  $("#add-cabinet-resTopologySid").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	 // $("#add-cabinet-description").jqxInput({placeHolder: "", height:70 , width: 300, minLength: 1,theme:currentTheme});
        	  $("#cabinetSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
  	          $("#cabinetCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
//  	          var codeadd = new codeModel({width:300,autoDropDownHeight:true,dropDownWidth:300});
//  	    	  codeadd.getCustomCode("add-mf-cabinet-name","/phylocations/downlist/serverroom","name","equipServerRoomSid",false,"POST",{resTopologySid:resTopologySid});
          }
	  });
	  $("#editCabWindow").jqxWindow({
		  width: 400, 
          height:260,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#editCabinetCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	  $("#edit-cabinet-name").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	  $("#edit-cabinet-location").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	  $("#edit-cabinet-resTopologySid").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	 // $("#edit-cabinet-description").jqxInput({placeHolder: "", height:70 , width: 300, minLength: 1,theme:currentTheme});
        	  $("#editCabinetSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
  	          $("#editCabinetCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
//  	          var codeadd = new codeModel({width:300,autoDropDownHeight:true,dropDownWidth:300});
//  	    	  codeadd.getCustomCode("edit-mf-cabinet-name","/phylocations/downlist/serverroom","name","equipServerRoomSid",false,"POST",{resTopologySid:resTopologySid});
          }
	  });
	  
  };
  
  this.initValue = function(){
	  $('#addCabForm').jqxValidator({
			rules : [{
				input : '#add-cabinet-name',
				message : '不能为空!',
				action : 'keyup, blur',
				rule : 'length=1,128'
			},
			{
				input : '#add-cabinet-location',
				message : '不能为空!',
				action : 'keyup, blur',
				rule : 'length=1,128'
			},
//			{
//				input : '#add-cabinet-description',
//				message : '角色描述不能超过256个字符!',
//				action : 'keyup, blur',
//				rule : 'length=1,256'
//			},
			]
		});
	  $('#editCabForm').jqxValidator({
			rules : [{
				input : '#edit-cabinet-name',
				message : '不能为空!',
				action : 'keyup, blur',
				rule : 'length=1,128'
			},
			{
				input : '#edit-cabinet-location',
				message : '不能为空!',
				action : 'keyup, blur',
				rule : 'length=1,128'
			},
	/*		{
				input : '#edit-cabinet-description',
				message : '角色描述不能超过256个字符!',
				action : 'keyup, blur',
				rule : 'length=1,256'
			},*/
			]
		});
  }
}

function refreshCab(){
	  var h = new cabinetDatagridModels();
	  h.searchCabinetInfo();
}

function searchcabinet(){
	  var lb = new cabinetDatagridModels();
	  lb.searchObj["qm.nameLike"] = $("#search-cabinetName").val();
	  lb.searchObj["qm.equipRoomSid"] = $("#mf-cabinet-name").val()=="全部"?"":$("#mf-cabinet-name").val();
	  lb.searchCabinetInfo();
}

function addCab(){
 	$("#add-cabinet-name").val("");
 	$("#add-cabinet-location").val("");
	var codeadd = new codeModel({width:300,autoDropDownHeight:false,dropDownWidth:300});
    codeadd.getCustomCode("add-mf-cabinet-name","/phylocations/downlist/serverroom","name","equipRoomSid",false,"POST",{resTopologySid:resTopologySid});
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
	$("#addCabWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
  	$("#addCabWindow").jqxWindow('open');
}
function submitAddCabinet(){
	var isOk = $('#addCabForm').jqxValidator('validate');
	var lb = new cabinetDatagridModels();
	$("#add-cabinet-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#addCabForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phylocations/create/cabinet",
			params :lh,
			callback : function (data) {
			   var house = new cabinetDatagridModels();
			   house.searchCabinetInfo();
			   $("#addCabWindow").jqxWindow('close');
			    
		    }
	  });
	}
}

function editCab(){
	var codeadd = new codeModel({width:300,autoDropDownHeight:false,dropDownWidth:300});
	codeadd.getCustomCode("edit-mf-cabinet-name","/phylocations/downlist/serverroom","name","equipRoomSid",false,"POST",{resTopologySid:resTopologySid});
	var rowindex = $('#cabinetdatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#editCBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#cabinetdatagrid').jqxGrid('getrowdata', rowindex);
		$("#edit-cabinet-name").val(data.name);
  		$("#edit-cabinet-location").val(data.location);
  		//$("#edit-cabinet-description").val(data.description);
  		$("#edit-mf-cabinet-name").val(data.equipRoomSid);
  	
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
	$("#editCabWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
  	$("#editCabWindow").jqxWindow('open');
	}
}
function submitEditCabInfo(){
	var isOk = $('#editCabForm').jqxValidator('validate');
	var lb = new labHouseDatagridModels();
	$("#edit-cabinet-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#editCabForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phylocations/update/cabinet",
			params :lh,
			callback : function (data) {
				 var cab = new cabinetDatagridModels();
				 cab.searchCabinetInfo();
				 $("#editCabWindow").jqxWindow('close');
		    }
	    });
	}
}

function deleteCab(){
	var rowindex = $('#cabinetdatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#deleteCBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#cabinetdatagrid').jqxGrid('getrowdata', rowindex);
		var equipSid = data.equipSid;
		 Core.confirm({
			 title:"提示",
		     message:"您确定要删除该机柜吗？",
		     confirmCallback:function(){
		    	 Core.AjaxRequest({
			    		url : ws_url + "/rest/phylocations/find/cabinetByFrame/"+equipSid, 
			    		type:"post",
			    		callback : function (data) {
			    			if(data>0){
			    				Core.confirm({
			    					 title:"提示",
			    				     message:"该机柜下存在子集，不能删除！",
			    				});
			    			}else{
			    				Core.AjaxRequest({
									url : ws_url + "/rest/phylocations/delete/cabinet/"+equipSid,
					 				type:"get",
					 				callback : function (data) {
					 					 var cab = new cabinetDatagridModels();
					 					 cab.searchCabinetInfo();
					 			    },
					 		});
			    			}
			    		}
			    	});
		     }
		 });
	}
}