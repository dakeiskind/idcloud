var labHouseDatagridModels = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		"qm.nameLike":null,
    		"qm.equipRoomSid":null,
        	"qm.resTopologyNameVE":null,
        	"qm.resTopologyNameDC":null,
//        	"qm.equipServerRoomSid":null,
        	"qm.resTopologyType" : resTopologyType,
        	"qm.findChildBySid":resTopologySid,
        	"parentTopologySid":resTopologySid,
	    	 parentTopologySid:resTopologySid
	};

    this.staObj = {
	     resTopologySid:resTopologySid
	};
	//统计机房资源查询方法
	    this.getDataGridData = function(){
	    	 var hostData;
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/phylocations/serverroom/statistical",
	 			type:'post',
	 			params:me.staObj,
	 			async:false,
	 			callback : function (data) {
	 				hostData = data;
	 			}
		     });
	    	 return hostData;
	    };
	this.LabHouseResourcesStatistics = function(){
		var labHouseData = me.getDataGridData();
		var data = new Object();
		data.attr = new Array();
		data.roomCount = labHouseData.staTotalServerRoom;
		var value = [labHouseData.staTotalServerRoom];
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




    
    this.initLabHouseInput = function(){
    	// 初始化查询组件
        $("#search-labHouseName").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-labHouseButton").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
    
    this.searchLabHouseInfo = function(){
    	$("#labHousedatagrid").jqxGrid("gotopage",0);
		var dataAdapter = Core.getPagingDataAdapter({
			gridId: "labHousedatagrid",
			url : ws_url + "/rest/phylocations/serverroom",
			params: me.searchObj
		});         
		$("#labHousedatagrid").jqxGrid({
			source: dataAdapter,
			rendergridrows: function(){
				return dataAdapter.records;
			}
		});
    };
    
    
    this.initLabHouseDatagrid = function(){	
    	
    	var dataAdapter = Core.getPagingDataAdapter({
			gridId: "labHousedatagrid",
			url: ws_url + "/rest/phylocations/serverroom",
			params: me.searchObj
		});
        $("#labHousedatagrid").jqxGrid({
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
				{ text: '机房名称', datafield: 'name',cellsalign:'left',width:200},   
				{ text: '机房位置', datafield: 'location',cellsalign:'left',width:200},   
				{ text: '区域', datafield: 'resTopologyNameVE',cellsalign:'left',width:150},   
				{ text: '数据中心', datafield: 'resTopologyNameDC',cellsalign:'left',width:150},   
				{ text: '描述', datafield: 'description',cellsalign:'left'}  
            ],
            showtoolbar: true,
            // 设置toolbar操作按钮
            rendertoolbar: function (toolbar) {
                var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
//                var refreshBtn = $("<div><a id='jqxRefreshHBtn' onclick='refreshHost()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                var addBtn = $("<div><a id='addHBtn' onclick='addHost()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>增加&nbsp;&nbsp;</a></div>");
                var editBtn = $("<div><a id='editHBtn' onclick='editHost()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                var deleteBtn = $("<div><a id='deleteHBtn' onclick='deleteHost()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                var refreshBtn = $("<div><a id='jqxRefreshHBtn' onclick='refreshHost()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                toolbar.append(container);
//                container.append(refreshBtn);
                container.append(addBtn);
                container.append(editBtn);
                container.append(deleteBtn);
                container.append(refreshBtn);
                $("#jqxRefreshHBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#addHBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#editHBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#deleteHBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
            }
        });
  };
  this.initPopWindow = function(){
	  $("#addHWindow").jqxWindow({
		  width: 400, 
          height:220,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#Cancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	  $("#add-name").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	  $("#add-location").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	  $("#add-resTopologySid").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	  $("#add-description").jqxInput({placeHolder: "", height:70 , width: 300, minLength: 1,theme:currentTheme});
        	  $("#Save").jqxButton({ width: '50',height:"25",theme:currentTheme});
  	          $("#Cancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
          }
	  });
	  $("#editHWindow").jqxWindow({
		  width: 400, 
          height:220,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#editCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	  $("#edit-name").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	  $("#edit-location").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	  $("#edit-resTopologySid").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	  $("#edit-description").jqxInput({placeHolder: "", height:70 , width: 300, minLength: 1,theme:currentTheme});
        	  $("#editSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
  	          $("#editCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
  	          $("#edit-equipServerRoomSid").jqxInput({placeHolder: "", height:70 , width: 300, minLength: 1,theme:currentTheme});
          }
	  });
	  
  };
  
  this.initValue = function(){
	  $('#addHForm').jqxValidator({
			rules : [ {
				input : '#add-name',
				message : '不能为空!',
				action : 'keyup, blur',
				rule : 'length=1,128'
			},
			{
				input : '#add-location',
				message : '不能为空!',
				action : 'keyup, blur',
				rule : 'length=1,128'
			},
			// {
			// 	input : '#add-description',
			// 	message : '不能为空!',
			// 	action : 'keyup, blur',
			// 	rule : 'length=1,256'
			// },
			]
		});
	  $('#editHForm').jqxValidator({
			rules : [ {
				input : '#edit-name',
				message : '不能为空!',
				action : 'keyup, blur',
				rule : 'length=1,128'
			},
			{
				input : '#edit-location',
				message : '不能为空!',
				action : 'keyup, blur',
				rule : 'length=1,128'
			},
			// {
			// 	input : '#edit-description',
			// 	message : '不能为空!',
			// 	action : 'keyup, blur',
			// 	rule : 'length=1,256'
			// },
			]
		});
  }
}
function refreshHost(){
	  var h = new labHouseDatagridModels();
	  h.searchLabHouseInfo();
}
function searchLabHouse(){
	  var lb = new labHouseDatagridModels();
	  lb.searchObj["qm.nameLike"]  = $("#search-labHouseName").val();
	  lb.searchLabHouseInfo();
}
function addHost() {
	 $("#add-name").val("");
     $("#add-location").val("");
     $("#add-description").val("");
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
	$("#addHWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
  	$("#addHWindow").jqxWindow('open');
}
function submitAddHInfo(){
	var isOk = $('#addHForm').jqxValidator('validate');
	var lb = new labHouseDatagridModels();
	$("#add-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#addHForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phylocations/serverroom/create",
			params :lh,
			callback : function (data) {
				 var house = new labHouseDatagridModels();
				 house.searchLabHouseInfo();
				 $("#addHWindow").jqxWindow('close');
		    }
	  });
	}
}
function editHost(){
	var rowindex = $('#labHousedatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#editHBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#labHousedatagrid').jqxGrid('getrowdata', rowindex);
		$("#edit-name").val(data.name);
  		$("#edit-location").val(data.location);
  		$("#edit-description").val(data.description);
  		$("#edit-equipServerRoomSid").val(data.equipRoomSid);
  		var windowW = $(window).width(); 
		var windowH = $(window).height(); 
		$("#editHWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-190)/2 } });
		$("#editHWindow").jqxWindow('open');
	}
}
function submitEditHInfo(){
	var isOk = $('#editHForm').jqxValidator('validate');
	var lb = new labHouseDatagridModels();
	$("#edit-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#editHForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phylocations/serverroom/update",
			params :lh,
			callback : function (data) {
				 var house = new labHouseDatagridModels();
				 house.searchLabHouseInfo();
				 $("#editHWindow").jqxWindow('close');
		    }
	    });
	}
}

function deleteHost(){
	var rowindex = $('#labHousedatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#deleteHBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#labHousedatagrid').jqxGrid('getrowdata', rowindex);
		var equipRoomSid = data.equipRoomSid
		 Core.confirm({
			 title:"提示",
		     message:"您确定要删除该机房吗？",
		     confirmCallback:function(){
		    	Core.AjaxRequest({
		    		url : ws_url + "/rest/phylocations/find/cabinetByServerroom/"+data.equipRoomSid, 
		    		type:"post",
		    		callback : function (data) {
		    			if(data>0){
		    				Core.confirm({
		    					 title:"提示",
		    				     message:"该机房下存在子集，不能删除！",
		    				});
		    			}else{
		    				Core.AjaxRequest({
								url : ws_url + "/rest/phylocations/serverroom/delete/"+equipRoomSid,
				 				type:"get",
				 				callback : function (data) {
				 					var house = new labHouseDatagridModels();
				 					house.searchLabHouseInfo();
				 			    },
				 		});
		    				
		    			}
		    		}
		    	});
		     }
		 });
	}
}