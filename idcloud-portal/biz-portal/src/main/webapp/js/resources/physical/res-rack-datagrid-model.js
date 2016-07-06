var rackDatagridModels = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    	"qm.nameLike":null,
    	"qm.resTopologyNameVE":null,
    	"qm.resTopologyNameDC":null,
    	"qm.equipCabinetSid":null,
    	"qm.findChildBySid":resTopologySid,
    	 parentTopologySid:resTopologySid
	};
    
    this.staObj = {
   	     resTopologySid:resTopologySid
   	};
    
    
  //统计机框资源查询方法
    this.getDataGridData = function(){
    	 var rackData;
    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/phylocations/rack/statistical",
	 			type:'post',
	 			params:me.staObj,
	 			async:false,
	 			callback : function (data) {
	 				rackData = data;
	 			}
	     });
    	 return rackData;
    };
		this.RackResourcesStatistics = function(){
			var rackData = me.getDataGridData();
			var data = new Object();
			data.attr = new Array();
			data.rackCount = rackData.staTotalRack;
			var value = [rackData.staTotalRack];
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
    
    this.initRackInput = function(){
    	// 初始化查询组件
        $("#search-rackName").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-rackButton").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
    this.initComboxLinkage = function(){
    	var codeadd = new codeModel({width:150,autoDropDownHeight : false,dropDownWidth:150});
    	codeadd.getCustomCode("rc-rack-name","/phylocations/downlist/cabinet","name","equipSid",true,"POST",{resTopologySid:resTopologySid});
    	//codeadd.getCustomCode("add-mf-cabinet-name","/phylocations/downlist/serverroom","name","equipServerRoomSid",false,"POST",{resTopologySid:resTopologySid});
    	//codeadd.getCustomCode("edit-mf-cabinet-name","/phylocations/downlist/serverroom","name","equipServerRoomSid",false,"POST",{resTopologySid:resTopologySid});
    }
    
    this.searchRackInfo = function(){
    	$("#rackdatagrid").jqxGrid("gotopage",0);
		var dataAdapter = Core.getPagingDataAdapter({
			gridId: "rackdatagrid",
			url : ws_url + "/rest/phylocations/find/rack",
			params: me.searchObj
		});         
		$("#rackdatagrid").jqxGrid({
			source: dataAdapter,
			rendergridrows: function(){
				return dataAdapter.records;
			}
		});
    };
    
    this.initRackDatagrid = function(){
    	
    	var dataAdapter = Core.getPagingDataAdapter({
			gridId: "rackdatagrid",
			url: ws_url + "/rest/phylocations/find/rack",
			params: me.searchObj
		});
          $("#rackdatagrid").jqxGrid({
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
				{ text: '机架编号', datafield: 'name',cellsalign:'left',width:200},   
				//{ text: '机柜位置', datafield: 'location',cellsalign:'left',width:200},    
				{ text: '区域', datafield: 'resTopologyNameVE',cellsalign:'left',width:150},   
				{ text: '数据中心', datafield: 'resTopologyNameDC',cellsalign:'left',width:150},   
				{ text: '所属机柜', datafield: 'equipCabinetName',cellsalign:'left'},    
				/*{ text: '描述', datafield: 'description',cellsalign:'left',width:170}*/
              ],
              showtoolbar:true,
           // 设置toolbar操作按钮
              rendertoolbar: function (toolbar) {
                var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
//                var refreshBtn = $("<div><a id='refreshCBtn' onclick='refreshCab()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                var addBtn = $("<div><a id='addRBtn' onclick='addRack()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>增加&nbsp;&nbsp;</a></div>");
                var editBtn = $("<div><a id='editRBtn' onclick='editRack()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                var deleteBtn = $("<div><a id='deleteRBtn' onclick='deleteRack()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                var refreshBtn = $("<div><a id='refreshRBtn' onclick='refreshRack()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-arrows-cw'></i>刷新&nbsp;&nbsp;</a></div>");
                toolbar.append(container);
//                container.append(refreshBtn);
                container.append(addBtn);
                container.append(editBtn);
                container.append(deleteBtn);
                container.append(refreshBtn);
                $("#refreshRBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#addRBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#editRBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
                $("#deleteRBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: false});
            }
          });
  };
  
  this.initPopWindow = function(){
	  $("#addRCWindow").jqxWindow({
		  width: 400, 
          height:160,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#rackCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	  $("#add-rc-name").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	 // $("#add-cabinet-location").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	  $("#add-rc-resTopologySid").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	 // $("#add-cabinet-description").jqxInput({placeHolder: "", height:70 , width: 300, minLength: 1,theme:currentTheme});
        	  $("#rackSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
  	          $("#rackCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
//  	          var codeadd = new codeModel({width:300,autoDropDownHeight:true,dropDownWidth:300});
//  	    	  codeadd.getCustomCode("add-mf-cabinet-name","/phylocations/downlist/serverroom","name","equipServerRoomSid",false,"POST",{resTopologySid:resTopologySid});
          }
	  });
	  $("#editRCWindow").jqxWindow({
		  width: 400, 
          height:260,
          resizable: false,  
          isModal: true, 
          autoOpen: false, 
          cancelButton: $("#editRcCancel"), 
          theme: currentTheme,
          modalOpacity: 0.3,
          initContent:function(){
        	  $("#edit-rc-name").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	 // $("#edit-rc-location").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	  $("#edit-rc-resTopologySid").jqxInput({placeHolder: "", height:22, width: 300, minLength: 1,theme:currentTheme});
        	 // $("#edit-cabinet-description").jqxInput({placeHolder: "", height:70 , width: 300, minLength: 1,theme:currentTheme});
        	  $("#editRcSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
  	          $("#editRcCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
//  	          var codeadd = new codeModel({width:300,autoDropDownHeight:true,dropDownWidth:300});
//  	    	  codeadd.getCustomCode("edit-mf-cabinet-name","/phylocations/downlist/serverroom","name","equipServerRoomSid",false,"POST",{resTopologySid:resTopologySid});
          }
	  });
	  
  };
  
  this.initValue = function(){
	  $('#addRCForm').jqxValidator({
			rules : [{
				input : '#add-rc-name',
				message : '不能为空!',
				action : 'keyup, blur',
				rule : 'length=1,128'
			},
			{
				input : '#add-rc-resTopologySid',
				message : '角色名称不能超过128个字符!',
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
	  $('#editRCForm').jqxValidator({
			rules : [{
				input : '#edit-rc-name',
				message : '不能为空!',
				action : 'keyup, blur',
				rule : 'length=1,128'
			},
//			{
//				input : '#edit-rc-location',
//				message : '角色名称不能超过128个字符!',
//				action : 'keyup, blur',
//				rule : 'length=1,128'
//			},
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

function refreshRack(){
	  var h = new rackDatagridModels();
	  h.searchRackInfo();
}

function searchRack(){
	  var lb = new rackDatagridModels();
	  lb.searchObj["qm.nameLike"] = $("#search-rackName").val();
	  lb.searchObj["qm.equipCabinetSid"] = $("#rc-rack-name").val()=="全部"?"":$("#rc-rack-name").val();
	  lb.searchRackInfo();
}

function addRack(){
	$("#add-rc-name").val("");
	var codeadd = new codeModel({width:300,autoDropDownHeight:false,dropDownWidth:300});
    codeadd.getCustomCode("add-rc-equipCabinetSid","/phylocations/downlist/cabinet","name","equipSid",false,"POST",{resTopologySid:resTopologySid});
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
	$("#addRCWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
  	$("#addRCWindow").jqxWindow('open');
}
function submitAddRack(){
	var isOk = $('#addRCForm').jqxValidator('validate');
	var lb = new cabinetDatagridModels();
	$("#add-rc-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#addRCForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phylocations/create/rack",
			params :lh,
			callback : function (data) {
			   var house = new rackDatagridModels();
			   house.searchRackInfo();
			   $("#addRCWindow").jqxWindow('close');
			    
		    }
	  });
	}
}

function editRack(){
	var codeadd = new codeModel({width:300,autoDropDownHeight:false,dropDownWidth:300});
	codeadd.getCustomCode("edit-rc-equipCabinetSid","/phylocations/downlist/cabinet","name","equipSid",false,"POST",{resTopologySid:resTopologySid});
	var rowindex = $('#rackdatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#editRBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#rackdatagrid').jqxGrid('getrowdata', rowindex);
		$("#edit-rc-name").val(data.name);
  		//$("#edit-cabinet-location").val(data.location);
  		//$("#edit-cabinet-description").val(data.description);
  		$("#edit-equipRackSid").val(data.equipSid);
  		$("#edit-rc-equipCabinetSid").val(data.equipCabinetSid);
  	
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
	$("#editRCWindow").jqxWindow({ position: { x: (windowW-350)/2, y: (windowH-158)/2 } });
  	$("#editRCWindow").jqxWindow('open');
	}
}
function submitEditRCInfo(){
	var isOk = $('#editRCForm').jqxValidator('validate');
	var lb = new labHouseDatagridModels();
	$("#edit-rc-resTopologySid").val(lb.searchObj.parentTopologySid);
	var lh=  JSON.parse($("#editRCForm").serializeJson());
	if(isOk){
		Core.AjaxRequest({
			url : ws_url + "/rest/phylocations/rack/update",
			params :lh,
			callback : function (data) {
				 var cab = new rackDatagridModels();
				 cab.searchRackInfo();
				 $("#editRCWindow").jqxWindow('close');
		    }
	    });
	}
}

function deleteRack(){
	var rowindex = $('#rackdatagrid').jqxGrid('getselectedrowindex');
	var ok =  $('#deleteRBtn').jqxButton('disabled');
	if(!ok && rowindex >= 0){
		var data = $('#rackdatagrid').jqxGrid('getrowdata', rowindex);
		var equipSid = data.equipSid;
		 Core.confirm({
			 title:"提示",
		     message:"您确定要删除该机架吗？",
		     confirmCallback:function(){
		    	 Core.AjaxRequest({
			    		url : ws_url + "/rest/phylocations/findResourceByRackSid/"+equipSid, 
			    		type:"post",
			    		callback : function (data) {
			    			if(data>0){
			    				Core.confirm({
			    					 title:"提示",
			    				     message:"该机柜下存在子集，不能删除！",
			    				});
			    			}else{
			    				Core.AjaxRequest({
									url : ws_url + "/rest/phylocations/delete/rack/"+equipSid,
					 				type:"get",
					 				callback : function (data) {
					 					 var cab = new rackDatagridModels();
					 					 cab.searchRackInfo();
					 			    },
					 		});
			    				
			    			}
			    		}
			    	});
		     }
		 });
	}
}