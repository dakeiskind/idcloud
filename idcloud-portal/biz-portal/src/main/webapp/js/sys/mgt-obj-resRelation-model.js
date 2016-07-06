var mgtObjResModel = function(){
	var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
    		ipAddressLike: "",
    		ipType:"",
    		usageStatus:"",
    		resNetworkSid:""
    	};
    //初始化window
    this.searchMgtObjResInfo = function(){
    	searchResData();
    };
    
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
    	// 初始化查询组件
    	var searchCondition = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth:150});
    	searchCondition.getCustomCode("resTopology","/topology","resTopologyName","resTopologySid",false,"post",{resTopologyType:"RZ"});
    	searchCondition.getCustomCode("resEnvironment","/topology","resTopologyName","resTopologySid",false,"post",{resTopologyType:"VE"});
    	$("#search-mgtObjRes-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    	
    };
  
    // 初始化用户datagrid的数据源
    this.initResDatagrid = function(){
          $("#mgtObjComResDatagrid").jqxGrid({
              width: "99.5%",
			  theme:currentTheme,
              columnsresize: true,
              pageable: true, 
              pagesize: 10, 
              autoheight: true,
              autowidth: false,
              sortable: true,
              selectionmode:"checkbox",
              localization: gridLocalizationObj,
              columns: [
                  { text: '主机名称', datafield: 'hostName'},
                  { text: '所属资源池', datafield: 'resPoolName'},
                  { text: '所属集群', datafield: 'parentTopologyName'},
                  { text: '状态', datafield: 'statusName'}
              ],
              showtoolbar: false
          });
          
          $("#mgtObjNetResDatagrid").jqxGrid({
        	  width: "99.5%",
        	  theme:currentTheme,
        	  columnsresize: true,
        	  pageable: true, 
        	  pagesize: 10, 
        	  autoheight: true,
        	  autowidth: false,
        	  sortable: true,
        	  selectionmode:"checkbox",
        	  localization: gridLocalizationObj,
        	  columns: [
	            { text: '网络名称', datafield: 'networkName'},
	            { text: '网络类型', datafield: 'networkTypeName'},
	            { text: 'IP类型', datafield: 'ipTypeName'},
	            { text: '状态', datafield: 'usageStatusName'}
	            ],
	           showtoolbar: false
          });
          
          $("#mgtObjStResDatagrid").jqxGrid({
        	  width: "99.5%",
        	  theme:currentTheme,
        	  columnsresize: true,
        	  pageable: true, 
        	  pagesize: 10, 
        	  autoheight: true,
        	  autowidth: false,
        	  sortable: true,
        	  selectionmode:"checkbox",
        	  localization: gridLocalizationObj,
        	  columns: [
        	            { text: '存储名称', datafield: 'storageName'},
        	            { text: '存储类型', datafield: 'storageTypeName'},
        	            { text: '所属集群', datafield: 'resTopologyName'},
        	            { text: '状态', datafield: 'statusName'}
        	            ],
              showtoolbar: false
          });
    };
    
    this.initGridRowChecked = function(str){
    	Core.AjaxRequest({
    		url :ws_url +"/rest/mgtObj/getMgtObjResList", 
    		type:"post",
    		params:{mgtObjSid:$("#opMgtObjSid").val()},
    		async:false,
    		callback : function (data) {
    			var crows = $('#mgtObjComResDatagrid').jqxGrid('getrows');
    			var nrows = $('#mgtObjNetResDatagrid').jqxGrid('getrows');
    			var srows = $('#mgtObjStResDatagrid').jqxGrid('getrows');
    			for(var i=0;i<data.length;i++){
    				if(data[i].resSetType=="RES-HOST" && str=="computing"){
	    				for(var r = 0; r < crows.length; r++){
		    				if(crows[r].resHostSid==data[i].resSetSid){
		    					$("#mgtObjComResDatagrid").jqxGrid('selectrow', r);
		    					break;
		    				}
		    			}
    				}else if(data[i].resSetType=="RES-NETWORK" && str=="network"){
		    			for(var r = 0; r < nrows.length; r++){
		    				if(nrows[r].resNetworkSid==data[i].resSetSid){
		    					$("#mgtObjNetResDatagrid").jqxGrid('selectrow', r);
		    					break;
		    				}
		    			}
    				}else if(data[i].resSetType=="RES-STORAGE" && str=="storage"){
		    			for(var r = 0; r < srows.length; r++){
		    				if(srows[r].resStorageSid==data[i].resSetSid){
		    					$("#mgtObjStResDatagrid").jqxGrid('selectrow', r);
		    					break;
		    				} 
		    			}
    				}
    			}
    		}
    	});
    };
    
    this.initGridRowUnchecked = function(){
		var comrowindex = $('#mgtObjComResDatagrid').jqxGrid('getselectedrowindexes');
		var array1 = new Array;
		for(var i=0;i<comrowindex.length;i++){
			array1.push(comrowindex[i]);
		}
		for(var i=0;i<array1.length;i++){
			$("#mgtObjComResDatagrid").jqxGrid('unselectrow', array1[i]);
		}
		//网络资源选中项
		var netrowindex = $('#mgtObjNetResDatagrid').jqxGrid('getselectedrowindexes');
		var array2 = new Array;
		for(var i=0;i<netrowindex.length;i++){
			array2.push(netrowindex[i]);
		}
		for(var i=0;i<array2.length;i++){
			$("#mgtObjNetResDatagrid").jqxGrid('unselectrow', array2[i]);
		}
		//存储资源选中项
		var strowindex = $('#mgtObjStResDatagrid').jqxGrid('getselectedrowindexes');
		var array3 = new Array;
		for(var i=0;i<strowindex.length;i++){
			array3.push(strowindex[i]);
		}
		for(var i=0;i<array3.length;i++){
			$("#mgtObjStResDatagrid").jqxGrid('unselectrow', array3[i]);
		}
    };
    
    // 初始化弹出window
    this.initPopWindow = function(){
    	$("#mgtObjResWin").jqxWindow({
		      width: 750, 
		      height:450,
		      theme:currentTheme,
		      resizable: false,  
		      isModal: true, 
		      autoOpen: false, 
		      cancelButton: $("#mgtObjResCancel"), 
		      modalOpacity: 0.3,
		      initContent: function () {
		    	  $('#tab2').jqxTabs({ height:'83%', width:'100%', theme:"metro"});
		    	  $("#mgtObjResSave").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	  $("#mgtObjResCancel").jqxButton({ width: '50',height:"25",theme:"metro"});
		      }
		  });
    };
    
};

function searchResData(){
	searchComResData();
	searchNetResData();
	searchStResData();
}

//条件查询
function searchComResData(){
//	$("#mgtObjComResDatagrid").jqxGrid('clearselection');
	var mgtObjRes = new mgtObjResModel();
	var resTopology = $("#resTopology").val()=="全部"?"":$("#resTopology").val();
	var topologySid = "";
	if(resTopology!=""){
		Core.AjaxRequest({
			url :ws_url +"/rest/topology", 
			type:"post",
			params:{topologyTypes:"'PCP','PCX','PCPW'",parentTopologySid:resTopology},
			async:false,
			callback : function (data) {
				if(data.length>0){
					for(var i=0;i<data.length;i++){
						topologySid = topologySid + "'"+data[i].resTopologySid +"',";
					}
				}
			}
		});
	}
	var resEnvironment = $("#resEnvironment").val()=="全部"?"":$("#resEnvironment").val();
	if(topologySid.length>0){
		topologySid = topologySid.substring(0, topologySid.length-1);
	}
	Core.AjaxRequest({
		url :ws_url +"/rest/host", 
		type:"post",
		params:{resTopologySid:resEnvironment,resPoolSid:topologySid},
		async:false,
		callback : function (data) {
			for(var i=0;i<data.length;i++){
				data[i].resVeSid = resEnvironment;
			}
			var sourcedatagrid ={
              datatype: "json",
              localdata: data
		    };
		    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		    $("#mgtObjComResDatagrid").jqxGrid({source: dataAdapter});
		}
	});
	mgtObjRes.initGridRowChecked("computing");
};
//条件查询
function searchNetResData(){
//	$("#mgtObjNetResDatagrid").jqxGrid('clearselection');
	var mgtObjRes = new mgtObjResModel();
	var resTopology = $("#resTopology").val()=="全部"?"":$("#resTopology").val();
	var topologySid = "";
	if(resTopology!=""){
		Core.AjaxRequest({
			url :ws_url +"/rest/topology", 
			type:"post",
			params:{resTopologyType:"PN",parentTopologySid:resTopology},
			async:false,
			callback : function (data) {
				if(data.length>0){
					topologySid = data[0].resTopologySid;
				}
			}
		});
	}
	var resEnvironment = $("#resEnvironment").val()=="全部"?"":$("#resEnvironment").val();
	
	Core.AjaxRequest({
		url :ws_url +"/rest/networks", 
		type:"post",
		params:{parentTopologySid:topologySid,networkType:"01"},
		async:false,
		callback : function (data) {
			for(var i=0;i<data.length;i++){
				data[i].resVeSid = resEnvironment;
			}
			var sourcedatagrid ={
					datatype: "json",
					localdata: data
			};
			var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
			$("#mgtObjNetResDatagrid").jqxGrid({source: dataAdapter});
		}
	});
	mgtObjRes.initGridRowChecked("network");
};
//条件查询
function searchStResData(){
//	$("#mgtObjStResDatagrid").jqxGrid('clearselection');
	var mgtObjRes = new mgtObjResModel();
	var resTopology = $("#resTopology").val()=="全部"?"":$("#resTopology").val();
	var topologySid = "";
	if(resTopology!=""){
		Core.AjaxRequest({
			url :ws_url +"/rest/topology", 
			type:"post",
			params:{resTopologyType:"PS",parentTopologySid:resTopology},
			async:false,
			callback : function (data) {
				if(data.length>0){
					topologySid = data[0].resTopologySid;
				}
			}
		});
	}
	var resEnvironment = $("#resEnvironment").val()=="全部"?"":$("#resEnvironment").val();
	
	Core.AjaxRequest({
		url :ws_url +"/rest/storage", 
		type:"post",
		params:{parentTopologySid:resEnvironment,resPoolSid:topologySid},
		async:false,
		callback : function (data) {
			for(var i=0;i<data.length;i++){
				data[i].resVeSid = resEnvironment;
			}
			var sourcedatagrid ={
					datatype: "json",
					localdata: data
			};
			var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
			$("#mgtObjStResDatagrid").jqxGrid({source: dataAdapter});
		}
	});
	mgtObjRes.initGridRowChecked("storage");
};

//保存关联
function saveMgtObjResRelation(){
	var bizResArray = [];
	var mgtObjSid = $("#opMgtObjSid").val();
	
	var oldMgtResList=new Array();
	Core.AjaxRequest({
		url :ws_url +"/rest/mgtObj/getMgtObjResList", 
		type:"post",
		params:{mgtObjSid:mgtObjSid},
		async:false,
		callback : function (data) {
			oldMgtResList = data;
		}
	});
	
	//计算资源选中项
	var comrowindex = $('#mgtObjComResDatagrid').jqxGrid('getselectedrowindexes');
	if(comrowindex.length > 0){
		for(var i=0;i<comrowindex.length;i++){
			var comdata = $('#mgtObjComResDatagrid').jqxGrid('getrowdata', comrowindex[i]);
			if(comdata != null && comdata != "" && comdata != 'undefined' ){
				if(comdata.resHostSid != null && comdata.resHostSid != "" && comdata.resHostSid != 'undefined'){
    				var comFlag = false;
    				if(oldMgtResList!=null&&oldMgtResList.length>0){
    					for(var m=0;m<oldMgtResList.length;m++){
    						if(oldMgtResList[m].resSetType=="RES-HOST"){
			    				if(oldMgtResList[m].resSetSid == comdata.resHostSid){
			    					comFlag = true;
//			    					oldMgtResList.pop(m);
			    					oldMgtResList.shift(m+1);
			    					break;
			    				}
    						}
    					}
    				}
    				if(!comFlag){
    					var bizRes = {};
        				bizRes.resSetSid = comdata.resHostSid;
        				bizRes.bizSid = mgtObjSid;
        				bizRes.resVeSid = comdata.resVeSid;
        				bizRes.resCategory = 0;
        				bizRes.resSetType = "RES-HOST";
        				bizRes.operate = "add";
        				bizResArray.push(bizRes);
    				}
				}
			}
		}
	}
	//网络资源选中项
	var netrowindex = $('#mgtObjNetResDatagrid').jqxGrid('getselectedrowindexes');
	if(netrowindex.length > 0){
		for(var i=0;i<netrowindex.length;i++){
			var netdata = $('#mgtObjNetResDatagrid').jqxGrid('getrowdata', netrowindex[i]);
			if(netdata != null && netdata != "" && netdata != 'undefined' ){
				if(netdata.resNetworkSid != null && netdata.resNetworkSid != "" && netdata.resNetworkSid != 'undefined'){
					var comFlag = false;
    				if(oldMgtResList!=null&&oldMgtResList.length>0){
    					for(var m=0;m<oldMgtResList.length;m++){
    						if(oldMgtResList[m].resSetType=="RES-NETWORK"){
			    				if(oldMgtResList[m].resSetSid == netdata.resNetworkSid){
			    					comFlag = true;
//			    					oldMgtResList.pop(m);
			    					oldMgtResList.shift(m+1);
			    					break;
			    				}
    						}
    					}
    				}
    				if(!comFlag){
    					var bizRes = {};
        				bizRes.resSetSid = netdata.resNetworkSid;
        				bizRes.bizSid = mgtObjSid;
        				bizRes.resVeSid = netdata.resVeSid;
        				bizRes.resCategory = 1;
        				bizRes.resSetType = "RES-NETWORK";
        				bizRes.operate = "add";
        				bizResArray.push(bizRes);
    				}
				}
			}
		}
	}
	//存储资源选中项
	var strowindex = $('#mgtObjStResDatagrid').jqxGrid('getselectedrowindexes');
	if(strowindex.length > 0){
		for(var i=0;i<strowindex.length;i++){
			var stdata = $('#mgtObjStResDatagrid').jqxGrid('getrowdata', strowindex[i]);
			if(stdata != null && stdata != "" && stdata != 'undefined' ){
				if(stdata.resStorageSid != null && stdata.resStorageSid != "" && stdata.resStorageSid != 'undefined'){
    				var comFlag = false;
    				if(oldMgtResList!=null&&oldMgtResList.length>0){
    					for(var m=0;m<oldMgtResList.length;m++){
    						if(oldMgtResList[m].resSetType=="RES-STORAGE"){
			    				if(oldMgtResList[m].resSetSid == stdata.resStorageSid){
			    					comFlag = true;
//			    					oldMgtResList.pop(m);
			    					oldMgtResList.shift(m+1);
			    					break;
			    				}
    						}
    					}
    				}
    				if(!comFlag){
    					var bizRes = {};
        				bizRes.resSetSid = stdata.resStorageSid;
        				bizRes.bizSid = mgtObjSid;
        				bizRes.resVeSid = stdata.resVeSid;
        				bizRes.resCategory = 4;
        				bizRes.resSetType = "RES-STORAGE";
        				bizRes.operate = "add";
        				bizResArray.push(bizRes);
    				}
				}
			}
		}
	}
	for(var m=0;m<oldMgtResList.length;m++){
		var bizRes = {};
		bizRes.resSetSid = oldMgtResList[m].resSetSid;
		bizRes.resSetType = oldMgtResList[m].resSetType;
		bizRes.bizSid = mgtObjSid;
		bizRes.operate = "delete";
		bizResArray.push(bizRes);
	}
	
	Core.AjaxRequest({
		url : ws_url + "/rest/mgtObj/relate",
		params :{
			'bizReses': bizResArray,
			'bizSid': mgtObjSid,
			"resVeSid":$("#resEnvironment").val()=="全部"?"":$("#resEnvironment").val()
		},
		type : "POST",
		callback : function (data) {
			 // 刷新基本信息
			var mgtObj = new MgtObjModel();
			mgtObj.initComputeResourceTree();
			mgtObj.initNetworkResourceTree();
			mgtObj.initStorageResourceTree();
			$("#mgtObjResWin").jqxWindow("close");
		},
	    failure:function(data){
	    	
	    }
	});
}

