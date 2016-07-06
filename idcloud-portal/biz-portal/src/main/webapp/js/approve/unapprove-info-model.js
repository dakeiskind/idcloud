var unapproveModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.itemsHistory = ko.observableArray();
	    //资源选择数据
	    this.resSelectData = new Array();
	    
	    this.getResData = function (instanceSid) {
	    	var resData = me.resSelectData;
	    	for(var i = 0; i < resData.length; i++) {
	    		if(resData[i].instanceSid == instanceSid) {
	    			return resData[i];
	    		}
	    	}
	    };
	    //设置订单实例的资源集配置
	    this.setInstanceResSet = function (instanceSid, rescomuteid, rescomuteType, vLanIDO, vLanIDI, wanIp, lanIp, 
	    		virtualSwitch, cpuResPool, rootHbaCard, networkHbaCard, stHbaCard, resType, partitionType, resPoolType, allocateType, aixParSid) {
	    	var resData = me.getResData(instanceSid);
			if(resData) {
				if(rescomuteid != undefined) resData.rescomuteid = rescomuteid; 
				if(rescomuteType != undefined) resData.rescomuteType = rescomuteType;
				if(vLanIDO != undefined) resData.vLanIDO = vLanIDO;
				if(vLanIDI != undefined) resData.vLanIDI = vLanIDI;
				if(wanIp != undefined) resData.wanIp = wanIp;
				if(lanIp != undefined) resData.lanIp = lanIp;
				if(virtualSwitch != undefined) resData.virtualSwitch = virtualSwitch;
				if(cpuResPool != undefined) resData.cpuResPool = cpuResPool;
				if(rootHbaCard != undefined) resData.rootHbaCard = rootHbaCard;
				if(networkHbaCard != undefined) resData.networkHbaCard = networkHbaCard;
				if(stHbaCard != undefined) resData.stHbaCard = stHbaCard;
				if(resType != undefined) resData.resType = resType;
				if(partitionType != undefined) resData.partitionType = partitionType;
				if(resPoolType != undefined) resData.resPoolType = resPoolType;
				if(allocateType != undefined) resData.allocateType = allocateType;
				if(aixParSid != undefined) resData.aixParSid = aixParSid;
	    	}
	    };

	    //批量设置资源集
	    this.setBatchInstanceResSet = function (instanceSid, map) {
	    	var resData = me.getResData(instanceSid);
	    	for(var key in map) {
	    		resData[key] = map[key];
	    	}
	    };
	    
	    this.targetServiceInstanceIndex = -1;
	    this.isFinal = false;
	    this.instanceSid = null;
	    this.instanceRowSelect = false;
		//清空资源选择配置
	    this.clearResSelectData = function () {
	    	me.targetServiceInstanceIndex = -1;
	    	me.isFinal = false;
	    	me.resSelectData = new Array();
	    	me.instanceSid = null;
	    };
	    
	    //将选择配置应用到所有实例
	    this.applyAllResSet = function (rescomuteid, vLanIDO, vLanIDI) {
	    	var resData = me.resSelectData;
	    	for(var i = 0; i < resData.length; i++) {
	    		resData[i].rescomuteid = rescomuteid;
	    		resData[i].vLanIDO = vLanIDO;
	    		resData[i].vLanIDI = vLanIDI;
	    	}
	    };

	    this.searchObj = {
	    	approveType:"01"
		};
	    // 用户数据
	    this.searchApproveInfo = function(){
	    	$("#unapprovedatagrid").jqxGrid("gotopage",0);
			var dataAdapter = Core.getPagingDataAdapter({
				gridId: "unapprovedatagrid",
				url: ws_url + "/rest/approve/getApproveByType",
				params: me.searchObj
			});
			$("#unapprovedatagrid").jqxGrid({
				source: dataAdapter,
				rendergridrows: function(){
					return dataAdapter.records;
				}
			});
	    	
			/* Core.AjaxRequest({
	 			url : ws_url + "/rest/approve/getApproveByType",
	 			type:'get',
	 			params:me.searchObj,
	 			async:false,
	 			callback : function (data) {
	 				me.items(data);
	 				var sourcedatagrid ={
 			              datatype: "json",
 			              localdata: me.items
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	 			    $("#unapprovedatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });*/
	 		 
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-flow-instance").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:"metro"});
	        $("#search-flow-createdby").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:"metro"});
	        var unapp = new codeModel({width:150,autoDropDownHeight:true});
	        unapp.getCommonCode("search-flow-type-unapprove","PROCESS_TYPE",true);
	        $("#search-approve-button").jqxButton({ width: '50px',height:'25px',theme:"metro"});
	    	
	    };
	    this.setEditUserForm = function(data){
	    	
	    };
	    // 初始化验证规则
	    this.initValidator = function(){
	    	$('#approveMgtForm').jqxValidator({
                rules: [
                          { input: '#approveNote', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#approveNote', message: '注释不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' }
                          
                       ]
	    	});
	    	$('#instance-approveForm').jqxValidator({
                rules: [
                          { input: '#instance-approveNote', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#instance-approveNote', message: '注释不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' }
                       ]
	    	});
	    	$('#cancel-approveForm').jqxValidator({
                rules: [
                          { input: '#cancel-approveNote', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
                          { input: '#cancel-approveNote', message: '注释不能超过128个字符!', action: 'keyup, blur', rule: 'length=1,128' }
                       ]
	    	});
	    	
	    };
	    // 初始化用户datagrid的数据源
	    this.initUnapproveDatagrid = function(){
	          $("#unapprovedatagrid").jqxGrid({
	              width: "100%",
				  theme:"metro",
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
   	              autorowheight: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '流程类型', datafield: 'processTypeName',width:120},
	                  { text: '审核对象', datafield: 'approveObject',width:200},
	                  { text: '审核操作', datafield: 'approvorActionName'},
	                  { text: '服务名称', datafield: 'serviceName'},
	                  { text: '创建时间', datafield: 'createdDt',width:150},
	                  { text: '申请人', datafield: 'proposeBy',width:150},
	                  { text: '申请时间', datafield: 'proposeDt',width:150}
	              ],
	              showtoolbar: true,
	              // 设置toolbar操作按钮
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  //var approveDetail = $("<div><a id='approveDetail' data-bind='click:openUnapproveDetails'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>审核详情&nbsp;&nbsp;</a></div>");
//	                  var approveDetail = $("<div><a Rid='approveDetail' data-bind='click:openUnapproveDetails'>&nbsp;&nbsp;<i class='icons-blue icon-doc-text'></i>审核详情&nbsp;&nbsp;</a></div>");
	                  var mgtApprove = $("<div><a id='mgtApprove'  data-bind='click:openUnapproveMgt' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-ok-3'></i>管理员审核&nbsp;&nbsp;</a></div>");
	                  //var historyApprove = $("<div><a id='historyApprove' data-bind='click:openUnapproveHistory' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-list-2'></i>审核历史&nbsp;&nbsp;</a></div>");
	               
	                  toolbar.append(container);
//	                  container.append(approveDetail);
	                  container.append(mgtApprove);
	                  //container.append(historyApprove);
	              }
	          });
	          
		    	// 初始化规格
		    	$("#unapprove-serviceSpecGrid").jqxGrid({
		             width: "732px",
		             autoheight:true,
		             theme:currentTheme,
		             columnsresize: true,
		             pageable: false, 
		             sortable: true,
		             localization: gridLocalizationObj,
		             columns: [
		                 { text: '规格名称', datafield: 'description',width:100},
		                 { text: '规格值', datafield: 'valueText'}
		             ],
		             toolbarheight: 0,
		             /*
		    		 showtoolbar: true
		    		 ,
		    		 rendertoolbar: function (toolbar) {
		    			   var btContainer = $("<div id='specBtnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;' ></div>");
		                   var modifyButton = $("<div><a id='specEditBtn'  data-bind='click:showInstanceSpecEditWindow'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
		                   btContainer.append(modifyButton);
		                   toolbar.append(btContainer);
		    		 }
		    		 */
		           
		         });
	          
	          // 控制按钮是否可用
	    	  $("#unapprovedatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  if(index >= 0) {
	    			var data = $('#unapprovedatagrid').jqxGrid('getrowdata', index);
	    		  	$("#orderId").val(data.approveObject);
	    		  	$("#processObjectId").val(data.processObjectId);
	    		  }
//	    		  $("#approveDetail").jqxButton({width: '60',theme:"metro",height: '18px', disabled: false });
	   			  $("#mgtApprove").jqxButton({ width: '60',theme:"metro",height: '18px',  disabled: false});
	   			  //$("#historyApprove").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: false});
	   			  
	   			  $("#unapprove-detail-serviceSpecGrid").hide();

	          });
	    	  
//	    	  $("#approveDetail").jqxButton({width: '60',theme:"metro",height: '18px', disabled: true });
	    	  $("#mgtApprove").jqxButton({ width: '60',theme:"metro",height: '18px',  disabled: true});
   			  //$("#specEditBtn").jqxButton({ width: '60',theme:"metro",height: '18px'});
   			  //$("#historyApprove").jqxButton({ width: '80',theme:"metro",height: '18px',  disabled: true});
   			  
   			  
   			//开通审核实例规格修改弹出窗初始化
  			$('#instanceSpecEditWindow').jqxWindow({
  	        	width:450,
  	        	height:250,
  				isModal : true,
  	            resizable: false,    
                autoOpen: false, 
  	            cancelButton : $("#instanceSpecEditCancel"),
  				theme: currentTheme,
  	            initContent: function () {
  	            	$("#instanceSpecEditSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        	    $("#instanceSpecEditCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
  	            }
  			});
  			$("#instanceSpecName").jqxInput({placeHolder: "", height: 22, width: '235px', minLength: 1,theme:currentTheme});
  			$("#resInstanceSpecName").jqxInput({placeHolder: "", height: 22, width: '235px', minLength: 1,theme:currentTheme});
  			$("#instanceSpecCpu").jqxNumberInput({ width: '235px', height: '22px', inputMode: 'simple',theme:currentTheme, spinButtons: true,decimalDigits:0, min: 1, max: 512, value: 1});
          	$("#instanceSpecMemory").jqxNumberInput({ width: '235px', height: '22px', inputMode: 'simple',theme:currentTheme, spinButtons: true,decimalDigits:0, min: 1, max: 512, value: 1});
          	$("#instanceSpecOs").jqxDropDownList({
				width: 195, 
	        	height: 22,
	        	dropDownWidth : 195,
	        	dropDownHeight : 150,
	        	selectedIndex: 0,
	        	theme:currentTheme,
	        	displayMember: "imageName",
	        	valueMember: "resImageSid",
                  placeHolder:""
			});
          	$('#instanceSpecEditForm').jqxValidator({
                rules: [
                    { input: '#instanceSpecName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                    { input: '#resInstanceSpecName', message: '为空或者主机名重复', action: 'keyup, blur', rule:  function (input, commit) {
                    	
                    	var result = true;
                    	if($.trim(input.val()) == '') {
                    		result = false;
                    		return false;
                    	}
                    	var rowindex = me.targetServiceInstanceIndex;
                		var data = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', rowindex);
               			var oldResInstName = data.resInstName ? data.resInstName: '' ;
               			
                    	Core.AjaxRequest({
            				url :ws_url +"/rest/approve/checkVmName", 
            				type: "post",
            				params:{
            					vmName: $.trim(input.val()),
            					oldVmName: $.trim(oldResInstName)
            				},
            				async: false,
            				showMsg: false,
            				callback : function (data) {
            					result = true;
            		        },
            		        failure: function (data) {
            		        	if(data.message != '') {
	            		        	Core.alert({
	             						title: "错误",
	             						message: data.message
	             					});
            		        	} else {
            		        		result = false;
            		        	}
            		        }
            		     });
                    	return result;
                    }}
                ]
	    	});
          	/*
          	$("#instanceSpecWan").jqxDropDownList({
				width: 195, 
	        	height: 22,
	        	dropDownWidth : 195,
	        	dropDownHeight : 50,
	        	selectedIndex: 0,
	        	theme:currentTheme,
	        	displayMember: "label",
	        	valueMember: "value",
                  placeHolder:"",
                  source: new $.jqx.dataAdapter({
                  	datatype: "json",
            		localdata: [
     			                {label:'需要', value:'1'},
     			                {label:'不需要', value:'0'}
     			     ]
                  })
			});

          	$("#instanceSpecLan").jqxDropDownList({
				width: 195, 
	        	height: 22,
	        	dropDownWidth : 195,
	        	dropDownHeight : 50,
	        	selectedIndex: 0,
	        	theme:currentTheme,
	        	displayMember: "label",
	        	valueMember: "value",
                  placeHolder:"",
                  source: new $.jqx.dataAdapter({
                  	datatype: "json",
            		localdata: [
     			                {label:'需要', value:'1'},
     			                {label:'不需要', value:'0'}
     			     ]
                  })
			});
	 		*/
   			  
	    };
	    
	    // 初始化弹出window
	    this.initPopWindow = function(){
	    	
      	     $("#rescomuteLabel").jqxDropDownButton({
  				width: 220, 
  	        	height: 22,
  	        	dropDownWidth : 250,
  	        	dropDownHeight : 180,
  	        	theme:currentTheme
//  	        	autoOpen: true
  			});
      	     
      	    
      	     
      	   $('#partitionType').on('change', function (event) {
      		   if(!me.instanceRowSelect) return;
      		   if(!me.instanceSid) return;
   	    	   var vEnv = $("#resVe").val();
   	    	   if(vEnv === 'HMC,IVM') {
	           	   me.setBatchInstanceResSet(me.instanceSid, {'partitionType': $(this).val()});
	           	   if($('#allocateType-nanotube').val() == true){
	           		$('#unapprove-resource-tabcontent .resSwitch').hide();
    	    		   $('#unapprove-resource-tabcontent .resHba').hide();
    	    		   $('#unapprove-resource-tabcontent .resStHba').hide();
    	    		   $('#unapprove-resource-tabcontent .resAixSelect').show();
	           	   }else{
	       	    	   if($(this).val() == '1') {
	       	    		   $('#unapprove-resource-tabcontent .resSwitch').show();
	       	    		   $('#unapprove-resource-tabcontent .resHba').hide();
	       	    		   $('#unapprove-resource-tabcontent .resStHba').hide();
	       	    	   } else if($(this).val() == '0') {
	       	    		   $('#unapprove-resource-tabcontent .resSwitch').hide();
	       	    		   $('#unapprove-resource-tabcontent .resHba').show();
	       	    		   $('#unapprove-resource-tabcontent .resStHba').show();
	       	    	   }
	           	   }
	    		   me.initResCompute();
	    		   me.checkIntanceAllSelect();
   	    	   }
   	       });
      	  $('#allocateType').on('change', function (event) {
    		   if(!me.instanceRowSelect) return;
    		   if(!me.instanceSid) return;
 	    	   var vEnv = $("#resVe").val();
 	    	   if(vEnv === 'HMC,IVM') {
	           	   me.setBatchInstanceResSet(me.instanceSid, {'allocateType': $(this).val()});
     	    	   if($(this).val() == '1') {
     	    		  $('#unapprove-resource-tabcontent .resPartition').show();
     	    		  if($("#partitionType").val() == '1') {
    	    		    $('#unapprove-resource-tabcontent .resSwitch').show();
    	    		    $('#unapprove-resource-tabcontent .resHba').hide();
    	    		    $('#unapprove-resource-tabcontent .resStHba').hide();
     	    		  }else{
     	    			$('#unapprove-resource-tabcontent .resSwitch').hide();
     	    		    $('#unapprove-resource-tabcontent .resHba').show();
     	    		    $('#unapprove-resource-tabcontent .resStHba').show();
     	    		  }
    	    		   
    	    		   $('#unapprove-resource-tabcontent .resLan').show();
     	    		   $('#unapprove-resource-tabcontent .resAixSelect').hide();
     	    	   } else if($(this).val() == '2') {
     	    		   $('#unapprove-resource-tabcontent .resPartition').show();
     	    		   $('#unapprove-resource-tabcontent .resSwitch').hide();
     	    		   $('#unapprove-resource-tabcontent .resHba').hide();
     	    		   $('#unapprove-resource-tabcontent .resStHba').hide();
     	    		   $('#unapprove-resource-tabcontent .resLan').hide();
     	    		   $('#unapprove-resource-tabcontent .resAixSelect').show();
     	    		   
     	    		   me.initDropDownList('aixPar', 'vmName', 'resVmSid',false);
     	    		   
     	    	   }
	    		   me.initResCompute();
	    		   me.checkIntanceAllSelect();
 	    	   }
 	       });
      	   $('#allocateType-create').on('checked', function (event) {
      		   if(!me.instanceRowSelect) return;
  		       if(!me.instanceSid) return;
	    	   var vEnv = $("#resVe").val();
	    	   if(vEnv === 'HMC,IVM') {
	           	   me.setBatchInstanceResSet(me.instanceSid, {'allocateType': '1'});
   	    		  $('#unapprove-resource-tabcontent .resPartition').show();
   	    		  if($("#partitionType").val() == '1') {
  	    		    $('#unapprove-resource-tabcontent .resSwitch').show();
  	    		    $('#unapprove-resource-tabcontent .resHba').hide();
  	    		    $('#unapprove-resource-tabcontent .resStHba').hide();
   	    		  }else{
   	    			$('#unapprove-resource-tabcontent .resSwitch').hide();
   	    		    $('#unapprove-resource-tabcontent .resHba').show();
   	    		    $('#unapprove-resource-tabcontent .resStHba').show();
   	    		  }
  	    		   $('#unapprove-resource-tabcontent .resLan').show();
   	    		   $('#unapprove-resource-tabcontent .resAixSelect').hide();
	    		   me.initResCompute();
	    		   me.checkIntanceAllSelect();
	    	   }
      	   });
      	   $('#allocateType-nanotube').on('checked', function (event) {
    		   if(!me.instanceRowSelect) return;
		       if(!me.instanceSid) return;
	    	   var vEnv = $("#resVe").val();
	    	   if(vEnv === 'HMC,IVM') {
	           	   me.setBatchInstanceResSet(me.instanceSid, {'allocateType': '2'});
	           	   $('#unapprove-resource-tabcontent .resPartition').show();
	    		   $('#unapprove-resource-tabcontent .resSwitch').hide();
	    		   $('#unapprove-resource-tabcontent .resHba').hide();
	    		   $('#unapprove-resource-tabcontent .resStHba').hide();
	    		   $('#unapprove-resource-tabcontent .resLan').hide();
	    		   $('#unapprove-resource-tabcontent .resAixSelect').show();
	    		   
	    		   me.initDropDownList('aixPar', 'vmName', 'resVmSid',false);
	    		   me.initResCompute();
	    		   me.checkIntanceAllSelect();
	    	   }
    	   });
      	  
      	  $('#aixPar').on('change', function (event) {
      		  if(!me.instanceRowSelect) return;
      		  if(!me.instanceSid) return;
      		  var vEnv = $("#resVe").val();
      		  if(vEnv === 'HMC,IVM') {
      			  me.setBatchInstanceResSet(me.instanceSid, {'aixParSid': $(this).val()});
      			  me.checkIntanceAllSelect();
      		  }
      	  });
      	   
      	   
//      	   $("#approveStatus").on('change', function () {
//	        	if($(this).val() == '01') {
//	        		$("#approveNote").val('通过');
//	        		var rowindex = $('#unapprovedatagrid').jqxGrid('getselectedrowindex');
//	        		if(rowindex >= 0) {
//    	     			var data = $('#unapprovedatagrid').jqxGrid('getrowdata', rowindex);
//    	     			if('运维管理员审核' == data.approvorAction) {
//    	     				$("#approveIsNeedPrincipal").show();
//    	     				$("#approveIsNeedPrincipal").jqxCheckBox({checked: true});
//    	     			} else {
//    	     				$("#approveIsNeedPrincipal").hide();
//    	     			}
//	        		}
//	        		me.setResCheckAndComfirmButton();
//	        	} else {
//	        		$("#approveIsNeedPrincipal").hide();
//	        		$("#approveNote").val('');
//	        		me.setResCheckAndComfirmButton();
//	        	}
//	        });
//      	   
//      	   $("#instance-approveStatus").on('change', function () {
//	        	if($(this).val() == '01') {
//	        		$("#instance-approveNote").val('通过');
//	        		var rowindex = $('#unapprovedatagrid').jqxGrid('getselectedrowindex');
//	        		if(rowindex >= 0) {
//    	     			var data = $('#unapprovedatagrid').jqxGrid('getrowdata', rowindex);
//    	     			if('运维管理员审核' == data.approvorAction) {
//    	     				$("#instance-isNeedPrincipal").show();
//    	     				$("#instance-isNeedPrincipal").jqxCheckBox({checked: true});
//    	     			} else {
//    	     				$("#instance-isNeedPrincipal").hide();
//    	     			}
//	        		}
//	        	} else {
//	        		$("#instance-isNeedPrincipal").hide();
//	        		$("#instance-approveNote").val('');
//	        	}
//	        });
//
//      	  $("#cancel-approveStatus").on('change', function () {
//	       	    if($(this).val() == '01') {
//	       	        $("#cancel-approveNote").val('通过');
//	       	        var rowindex = $('#unapprovedatagrid').jqxGrid('getselectedrowindex');
//	        		if(rowindex >= 0) {
//    	     			var data = $('#unapprovedatagrid').jqxGrid('getrowdata', rowindex);
//    	     			if('运维管理员审核' == data.approvorAction) {
//    	     				$("#cancel-isNeedPrincipal").show();
//    	     				$("#cancel-isNeedPrincipal").jqxCheckBox({checked: true});
//    	     			} else {
//    	     				$("#cancel-isNeedPrincipal").hide();
//    	     			}
//	        		}
//	       	    } else {
//	       	    	$("#cancel-isNeedPrincipal").hide();
//	       	        $("#cancel-approveNote").val('');
//	       	    }
//    	    });
      	   
	    	// 审核详情window
			$('#tabwindow').jqxWindow({
				showCollapseButton: false, 
				showCloseButton: true,
				maxHeight: 800, maxWidth: 850, minHeight: 400, minWidth: 400, height: 645, width: 750,
	        	isModal : true,
	            resizable: false,    
                autoOpen: false, 
	            cancelButton : $(".approveMgtCancel"),
				theme: currentTheme,
	            initContent: function () {
	                $('#tab2').jqxTabs({ height:'100%', width:'100%', theme:"metro"});
	                $("#unapprove-instance-tab").jqxTabs({ height:'220px', width:'734px', theme:"metro"});
	                $("#unapprove-spec-tab").jqxTabs({ height:'190px', width:'100%', theme:"metro"});
	                // 初始化
                	$("#approveNote").jqxInput({placeHolder: "", height: 50, width: 300, minLength: 1,theme:"metro"});
//                	var unapp = new codeModel({width:200,autoDropDownHeight:true});
                	$("#approveMgtSave").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	    $("#approveMgtCancel").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	    $("#approveMgtCancel2").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	    
	        	    Core.AjaxRequest({
	        			url :ws_url + "/rest/system/APPROVE_STATUS", 
	        			type:"get",
	        			async:false,
	        			callback : function (data) {
	        				if(data.length>0){
	        					for(var i=0;i<data.length;i++){
	        						$("#approveStatus").append("<div id='approveStatus-"+data[i].codeValue+"' name='"+data[i].codeValue+"' style='float:left;'>"+data[i].codeDisplay+"</div>");
	        						$("#instance-approveStatus").append("<div id='instance-approveStatus-"+data[i].codeValue+"' name='"+data[i].codeValue+"' style='float:left;'>"+data[i].codeDisplay+"</div>");
	        						$("#cancel-approveStatus").append("<div id='cancel-approveStatus-"+data[i].codeValue+"' name='"+data[i].codeValue+"' style='float:left;'>"+data[i].codeDisplay+"</div>");
        							$("#approveStatus-"+data[i].codeValue).jqxRadioButton({
	        						     width: 120,
	        						     height: 25,
	        						     theme:'metro',
	        						     groupName : "approveStatus"
	        						});
        							$("#instance-approveStatus-"+data[i].codeValue).jqxRadioButton({
	        						     width: 120,
	        						     height: 25,
	        						     theme:'metro',
	        						     groupName : "instance-approveStatus"
	        						});
        							$("#cancel-approveStatus-"+data[i].codeValue).jqxRadioButton({
	        						     width: 120,
	        						     height: 25,
	        						     theme:'metro',
	        						     groupName : "cancel-approveStatus"
	        						});
	        					}
	        					$("[id^='approveStatus-']").on('checked', function (event) {
	        						if($(this).attr('name') == '01') {
	        			        		$("#approveNote").val('通过');
	        			        		var rowindex = $('#unapprovedatagrid').jqxGrid('getselectedrowindex');
	        			        		if(rowindex >= 0) {
	        		    	     			var data = $('#unapprovedatagrid').jqxGrid('getrowdata', rowindex);
	        		    	     			if('运维管理员审核' == data.approvorAction) {
	        		    	     				$("#approveIsNeedPrincipal").show();
	        		    	     				$("#approveIsNeedPrincipal").jqxCheckBox({checked: true});
	        		    	     			} else {
	        		    	     				$("#approveIsNeedPrincipal").hide();
	        		    	     			}
	        			        		}
	        			        		me.setResCheckAndComfirmButton();
	        			        	} else {
	        			        		$("#approveIsNeedPrincipal").hide();
	        			        		$("#approveNote").val('');
	        			        		me.setResCheckAndComfirmButton();
	        			        	}
	        					});
	        					$("[id^='instance-approveStatus-']").on('checked', function (event) {
	        						if($(this).attr('name') == '01') {
	        			        		$("#instance-approveNote").val('通过');
	        			        		var rowindex = $('#unapprovedatagrid').jqxGrid('getselectedrowindex');
	        			        		if(rowindex >= 0) {
	        		    	     			var data = $('#unapprovedatagrid').jqxGrid('getrowdata', rowindex);
	        		    	     			if('运维管理员审核' == data.approvorAction) {
	        		    	     				$("#instance-isNeedPrincipal").show();
	        		    	     				$("#instance-isNeedPrincipal").jqxCheckBox({checked: true});
	        		    	     			} else {
	        		    	     				$("#instance-isNeedPrincipal").hide();
	        		    	     			}
	        			        		}
	        			        	} else {
	        			        		$("#instance-isNeedPrincipal").hide();
	        			        		$("#instance-approveNote").val('');
	        			        	}
	        					});
	        					$("[id^='cancel-approveStatus-']").on('checked', function (event) {
	        						if($(this).attr('name') == '01') {
	        			       	        $("#cancel-approveNote").val('通过');
	        			       	        var rowindex = $('#unapprovedatagrid').jqxGrid('getselectedrowindex');
	        			        		if(rowindex >= 0) {
	        		    	     			var data = $('#unapprovedatagrid').jqxGrid('getrowdata', rowindex);
	        		    	     			if('运维管理员审核' == data.approvorAction) {
	        		    	     				$("#cancel-isNeedPrincipal").show();
	        		    	     				$("#cancel-isNeedPrincipal").jqxCheckBox({checked: true});
	        		    	     				$(".cancelTypeTr").hide();
	        		    	     			} else {
	        		    	     				$("#cancel-isNeedPrincipal").hide();
	        		    	     				Core.AjaxRequest({
	        		    	     					type : 'get',
	        		    	     					url : ws_url + "/rest/order/getInstSpecForDataDisk/" + data.processObjectId,
	        		    	     					async: false,
	        		    	     					callback : function(data) {
	        		    	     						var cancelTrsFlag = false;
	        		    	     						for(var i=0;i<data.length;i++){
	        		    	     							if("HMC,IVM" == data[i].ve && data[i].name == "OS"){
	        		    	     								$(".cancelTypeTr").show();
	        		    	     								cancelTrsFlag = true;
	        		    	     								break;
	        		    	     							}
	        		    	     						}
	        		    	     						if(!cancelTrsFlag){
	        		    	     							$(".cancelTypeTr").hide();
	        		    	     						}
	        		    	     					}
	        		    	     				});
	        		    	     			}
	        			        		}
	        			       	    } else {
	        			       	    	$("#cancel-isNeedPrincipal").hide();
	        			       	        $("#cancel-approveNote").val('');
	        			       	        $(".cancelTypeTr").hide();
	        			       	    }
	        					});
	        					$($("[id^='approveStatus-']")[0]).jqxRadioButton('check');
	        					$($("[id^='instance-approveStatus-']")[0]).jqxRadioButton('check');
	        					$($("[id^='cancel-approveStatus-']")[0]).jqxRadioButton('check');
	        				}
	        			}
	        	    });
//         	        unapp.getCommonCode("approveStatus","APPROVE_STATUS");
         	        
         	        $("#approveIsNeedPrincipal").jqxCheckBox({checked: true, theme:currentTheme});
         	        
		 	        $("#vmResCheck").jqxButton({ width: '70',height:"25",theme:"metro"});
	       	        //$("#applyAll").jqxButton({ width: '150',height:"25",theme:"metro"});
	       	        
	       	        //云主机服务变更弹出页面初始化
                	$("#instance-approveNote").jqxInput({placeHolder: "", height: 50, width: 300, minLength: 1,theme:"metro"});
                	$("#instance-approveMgtSave").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	    $("#instance-approveMgtCancel").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	    
//         	        unapp.getCommonCode("instance-approveStatus","APPROVE_STATUS");
         	        
         	        $("#instance-isNeedPrincipal").jqxCheckBox({checked: true, theme:currentTheme});
	       	        $("#instance-changeCheck").jqxButton({ width: '70',height:"25",theme:"metro"});

	       	        //云主机服务退订弹出页面初始化
		       	    $("#cancel-approveNote").jqxInput({placeHolder: "", height: 50, width: 300, minLength: 1,theme: currentTheme});
	             	$("#cancel-approveMgtSave").jqxButton({ width: '50',height:"25",theme: currentTheme});
		        	$("#cancel-approveMgtCancel").jqxButton({ width: '50',height:"25",theme: currentTheme});
		        	
		        	$("#cancel-type-create").jqxRadioButton({ width: 80, height: 25,groupName :"cancelType", checked: true,theme:"metro"});
	 				$("#cancel-type-nanotube").jqxRadioButton({ width: 80, height: 25,groupName :"cancelType",theme:"metro"});
	 				
//	      	        unapp.getCommonCode("cancel-approveStatus","APPROVE_STATUS");
	      	        
	      	        $("#cancel-isNeedPrincipal").jqxCheckBox({checked: true, theme:currentTheme});
		       	    $("#cancel-changeCheck").jqxButton({ width: '70',height:"25",theme: currentTheme});
	       	      
	       	        $('#vLanIDO').change(function () {
	       	        	if(!me.instanceRowSelect) return;
	       	        	me.initIpAddressDropDownList('wanIps', $(this).val());
	       	        	var selectIndex = $('#unapprove-orderDetailsGrid').jqxGrid('selectedrowindex'); 
	       	        	if(selectIndex >= 0){
	               			var data = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', selectIndex);
	               			if(data) {
               					var resData = me.getResData(data.instanceSid); 
               					me.removeDupIps(selectIndex);
               					if(resData) {
    	           					$("#wanIps").val(resData.wanIp);
               					}
	               			}
	       	        	}
	       	        	me.checkIntanceAllSelect();
	       	        });
	       	        
	       	        $('#vLanIDI').change(function () {
	       	        	if(!me.instanceRowSelect) return;
	       	        	me.initIpAddressDropDownList('lanIps', $(this).val());
	       	        	var selectIndex = $('#unapprove-orderDetailsGrid').jqxGrid('selectedrowindex');
	       	        	if(selectIndex >= 0){
	               			var data = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', selectIndex);
	               			if(data) {
               					var resData = me.getResData(data.instanceSid); 
               					me.removeDupIps(selectIndex);
               					if(resData) {
    	           					$("#lanIps").val(resData.lanIp);
               					}
	               			}
	       	        	}
	       	        	me.checkIntanceAllSelect();
	       	        });

	       	     $('#rescomuteTree').jqxTree({height: '100%', width: '100%', theme : currentTheme});
	       	     $('#rescomuteTree').on('select', function (event) {
	       	    	 if($("#aixPar").length>0){$("#aixPar").jqxDropDownList('clear');}; 
	       	    	 if(!me.instanceRowSelect) return;
                     var args = event.args;
                     var item = $('#rescomuteTree').jqxTree('getItem', args.element);
                     var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;"><img width="16" height="16" style="float: left; margin-right: 4px;" class="itemicon" src="'+ item.icon+'">' + item.label + '</div>';
                     $("#rescomuteLabel").jqxDropDownButton('setContent', dropDownContent);
                     $('#rescomuteLabel').jqxDropDownButton('close'); 
                     var value = item.value;
                     $('#rescomuteid').val(value.split(",")[0]);
                     $('#rescomuteType').val(value.split(",")[1]);
                     if(value.split(",")[1] == 'RES-HOST') {
                    	me.getCustomCode('vLanIDI', '/approve/lans', 'networkName', 'resNetworkSid', false, 'POST', {'hostSid': value.split(",")[0]},false);
                    	if($('#resVe').val() == 'HMC,IVM') {
                    		me.getCustomCode('networkHbaCard', '/approve/hba', 'itemLocation', 'hostItemId', false, 'POST', {'hostSid': value.split(",")[0], 'itemType': 'Ethernet'},false);
                    		var vLanIDIItems = $("#vLanIDI").jqxDropDownList('getItems');
                    		if(vLanIDIItems && vLanIDIItems.length > 0) {
                    			me.getCustomCode('virtualSwitch', '/approve/switch', 'resVsName', 'resVsSid', false, 'POST', {'hostSid': value.split(",")[0]},false);
                    		} else {
                    			me.initDropDownList('virtualSwitch', 'resVsName', 'resVsSid' ,false);
                    		}
		   					me.getCustomCode('cpuResPool', '/approve/cpuPool', 'cpuPoolName', 'resCpuPoolSid', false, 'POST', {'hostSid': value.split(",")[0]},false);
		     			    me.getCustomCode('rootHbaCard', '/approve/hba', 'itemLocation', 'hostItemId', false, 'POST', {'hostSid': value.split(",")[0], 'itemType': '1'},false);
		     			    me.getCustomCode('stHbaDiv', '/approve/hba', 'itemLocation', 'hostItemId', false, 'POST', {'hostSid': value.split(",")[0], 'itemType': '2'},true);
		     			    
		     			    var selectIndex = $('#unapprove-orderDetailsGrid').jqxGrid('selectedrowindex');
		              		if(selectIndex >= 0) {
		              			var data = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', selectIndex);
		              			if(data) {
		              				me.getCustomCode('aixPar', '/vms/getUnnanotubeVMByHost', 'vmName', 'resVmSid', false, 'POST', {'hostSid': value.split(",")[0]},false);
		              			}
		              		}else{
		              			me.getCustomCode('aixPar', '/vms/getUnnanotubeVMByHost', 'vmName', 'resVmSid', false, 'POST', {'hostSid': value.split(",")[0]},false);
		              		}
	                    }
                    	
                     } else {
                    	 if($('#resVe').val() == 'HMC,IVM' && (value.split(",")[0] == '自动分配' || value.split(",")[1] == 'VE' || value.split(",")[1] == 'VC')) {
                    		 me.setAutoAllocate('virtualSwitch');
                    		 me.setAutoAllocate('cpuResPool');
                        	 me.setAutoAllocate('rootHbaCard');
                        	 me.setAutoAllocate('stHbaDiv');
                        	 me.setAutoAllocate('networkHbaCard');
 	                     }
                    	 if(value.split(",")[0] == '自动分配' || value.split(",")[1] == 'VE' || value.split(",")[1] == 'VC') {
	                    	me.setAutoAllocate('vLanIDI');
	                     }
                     }
                     me.checkIntanceAllSelect();
                 });
	       	        /*
	       	        //应用当前资源选择配置
	       	        $('#applyAll').click(function () {
	       	        	me.applyAllResSet($('#rescomuteid').val(), $('#vLanIDO').val(), $('#vLanIDI').val());
	       	        });
	       	        */
	       	       $('#openResTypeVm').jqxRadioButton({ width: 80, theme:currentTheme,checked: true});
	       	       $('#openResTypeHost').jqxRadioButton({ width: 80, theme:currentTheme,checked: false});
	       	       
	       	       $("#openResTypeVm").on('change', function (event) {
	       	    	  if(!me.instanceRowSelect) return;
	       	    	  if($(this).val() === true) {
	       	    		  me.setBatchInstanceResSet(me.instanceSid, {'resType': '1'});
	       	    	      me.switchResSelectView();
	       	    	  }
	              });
	       	       
	       	       $('#openResTypeHost').on('change', function (event) {
	       	    	   if(!me.instanceRowSelect) return;
	       	    	   if($(this).val() === true) { 
	       	    		   me.setBatchInstanceResSet(me.instanceSid, {'resType': '2'});
	       	    		   me.switchResSelectView();
	       	    	   }
	       	       });
	       	       
	            }
	        });
			/*
			 $('#window').jqxWindow({
		        	isModal : true,
		            showCollapseButton: false, 
		            resizable: false,    
	                autoOpen: false, 
	                showCloseButton: true,
		            maxHeight: 800, maxWidth: 850, minHeight: 400, minWidth: 400, height: 620, width: 750,
		            cancelButton : $("#approveDetailCancel"),
					theme: currentTheme,
		            initContent: function () {
		                $('#tab').jqxTabs({ height: '94%', width:  '100%', theme:"metro"});
		                $('#approveDetailCancel').jqxButton({
							width : '50px',
							height : '25px',
							theme :currentTheme
						});
		            }
		        });
			 */
		        
			
			$("#unapprove-orderDetailsGrid").on('rowselect', function (event) {
				me.instanceRowSelect = false;
				var selectIndex = $('#unapprove-orderDetailsGrid').jqxGrid('selectedrowindex');
				//$("#applyAll").attr('checked', false);
           		var rowindex = event.args.rowindex;
           		if(rowindex >= 0) {
           			var data = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', rowindex);
           			if(data) {
	           			me.targetServiceInstanceIndex = rowindex;
	           			me.instanceSid = data.instanceSid;
           			}
           		}
           		if(selectIndex >= 0 && rowindex >= 0 && selectIndex != rowindex) {
 	        		var data = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', selectIndex);
 	        		if(data && data.instanceSid) {
		 	        	me.setInstanceResSet(
		 	        		data.instanceSid, 
		 	        		$("#rescomuteid").val() == '自动分配' ? '' :$("#rescomuteid").val(),
		 	        		$("#rescomuteType").val(),
		 	        		$("#vLanIDO").val() == '自动分配' ? '' :$("#vLanIDO").val(),
		 	        		$("#vLanIDI").val() == '自动分配' ? '' :$("#vLanIDI").val(),
		 	        		$("#wanIps").val() == '自动分配' ? '' :$("#wanIps").val(),
		 	        		$("#lanIps").val() == '自动分配' ? '' :$("#lanIps").val(),
		 	        		$('#partitionType').val() == '1' ? ($("#virtualSwitch").val() == '自动分配' ? '' :$("#virtualSwitch").val()) : '',
		 	        		$('#partitionType').val() == '1' ? ($("#cpuResPool").val() == '自动分配' ? '' :$("#cpuResPool").val()) : '',
		 	        		$('#partitionType').val() == '0' ? ($("#rootHbaCard").val() == '自动分配' ? '' :$("#rootHbaCard").val()) : '',
		 	        		$('#partitionType').val() == '0' ? ($("#networkHbaCard").val() == '自动分配' ? '' :$("#networkHbaCard").val()) : '',
 	        				$('#partitionType').val() == '0' ? ($("#stHbaDiv").val() == '自动分配' ? '' :$("#stHbaDiv").val()) : '',
		 	        	    me.getResType(),
		 	        	    $('#resVe').val() == 'HMC,IVM' ? $('#partitionType').val() : '',
		 	        	    me.getResPoolType(),
//		 	        	    $("#allocateType").val(),
		 	        	    $("#allocateType-create").val()==true?"1":"2",
		 	        	    $("#aixPar").val()
		 	        	);
 	        		}
 	        	}
           		
           		if(rowindex >= 0){
           			var data = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', rowindex);
           			if(data) {
       					var resData = me.getResData(data.instanceSid); 
       					if(resData) {
       						if(resData.needWan === true) {
       							$('#unapprove-resource-tabcontent .resWan').show();
       							me.getCustomCode('vLanIDO', '/biz/getSubmitUserResources', 'resSetType', 'resSetSid', false, 'POST', {'resType': 1, 'orderId': data.orderId},false);
           						var vLanIDOItems = $('#vLanIDO').jqxDropDownList('getItems');
           						if(vLanIDOItems && vLanIDOItems.length > 0) {
           							if(resData.vLanIDO == '') {
           								resData.vLanIDO = $('#vLanIDO').val();
           							}
           							me.initIpAddressDropDownList('wanIps', resData.vLanIDO);
           						} else {
           							me.initIpAddressDropDownList('wanIps', '');
           						}
       						} else {
       							$('#unapprove-resource-tabcontent .resWan').hide();
       						}
       						if(resData.needLan === true) {
       							$('#unapprove-resource-tabcontent .resLan').show();
       							if(resData.rescomuteid != '')  {
	       							me.getCustomCode('vLanIDI', '/approve/lans', 'networkName', 'resNetworkSid', false, 'POST', {'hostSid': resData.rescomuteid},false);
	           						var vLanIDIItems = $('#vLanIDI').jqxDropDownList('getItems');
	           						if(vLanIDIItems && vLanIDIItems.length > 0) {
	           							if(resData.vLanIDI == '') {
	           								resData.vLanIDI = $('#vLanIDI').val();
	           							}
	           							me.initIpAddressDropDownList('lanIps', resData.vLanIDI == '' ? '自动分配' : resData.vLanIDI);
	           						} else {
	           							me.initIpAddressDropDownList('lanIps', '');
	           						}
       							} else {
       								me.setAutoAllocate('vLanIDI');
       								me.initIpAddressDropDownList('lanIps', '自动分配');
       							}
       						} else {
       							$('#unapprove-resource-tabcontent .resLan').hide();
       						}
       						
       						me.removeDupIps(rowindex);
       						//me.initResComputeTree(resData.ve, resData.partitionType, resData.mgtObjSid);
       						$("#resMgtObjSid").val(resData.mgtObjSid);
       						$('#resVe').val(resData.ve);
       						$("#partitionType").val(resData.partitionType);
       						me.setResType(resData.resType);
       						//虚拟机
       						if(resData.ve === 'HMC,IVM') {
       							$('#unapprove-resource-tabcontent .resAllocateType').show();
   								$('#unapprove-resource-tabcontent .resType').hide();
   								$('#unapprove-resource-tabcontent .resPartition').show();
   								$('#unapprove-resource-tabcontent .resCompute').show();
   								$('#unapprove-resource-tabcontent .resLan').show();
   								$('#unapprove-resource-tabcontent .resWan').hide();
   								if(resData.partitionType == '1') {
   								   me.getCustomCode('virtualSwitch', '/approve/switch', 'resVsName', 'resVsSid', false, 'POST', {'hostSid': resData.rescomuteid},false);
   	          					   me.getCustomCode('cpuResPool', '/approve/cpuPool', 'cpuPoolName', 'resCpuPoolSid', false, 'POST', {'hostSid': resData.rescomuteid},false);
 		     					   $('#unapprove-resource-tabcontent .resSwitch').show();
 		            	    	   $('#unapprove-resource-tabcontent .resHba').hide();
 		            	    	   $('#unapprove-resource-tabcontent .resStHba').hide();
 	            	    	    } else if(resData.partitionType == '0') {
 	            	    		   $('#unapprove-resource-tabcontent .resSwitch').hide();
 	            	    		   $('#unapprove-resource-tabcontent .resHba').show();
 	            	    		   $('#unapprove-resource-tabcontent .resStHba').show();
 			     				   me.getCustomCode('rootHbaCard', '/approve/hba', 'itemLocation', 'hostItemId', false, 'POST', {'hostSid': resData.rescomuteid, 'itemType': '1'},false);
 			     				   me.getCustomCode('networkHbaCard', '/approve/hba', 'itemLocation', 'hostItemId', false, 'POST', {'hostSid': resData.rescomuteid, 'itemType': '3'},false);
 			     				   me.getCustomCode('stHbaDiv', '/approve/hba', 'itemLocation', 'hostItemId', false, 'POST', {'hostSid': resData.rescomuteid, 'itemType': '2'},true);
 	            	    	    }
   							} else if(resData.ve === 'VMware') {
   								$('#unapprove-resource-tabcontent .resAllocateType').hide();
   								$('#unapprove-resource-tabcontent .resPartition').hide();
       							$('#unapprove-resource-tabcontent .resCompute').show();
       							$('#unapprove-resource-tabcontent .resSwitch').hide();
       							$('#unapprove-resource-tabcontent .resHba').hide();
       							$('#unapprove-resource-tabcontent .resStHba').hide();
       							$('#unapprove-resource-tabcontent .resWan').hide();
   								if(resData.resType == '1') {
   									$('#unapprove-resource-tabcontent .resLan').show();
   								} else if (resData.resType == '2'){
   									$('#unapprove-resource-tabcontent .resLan').hide();
   								}
   							}
       						$('#unapprove-resource-tabcontent .resAixSelect').hide();
       						
       						me.initResComputeTree();
       						if(resData.rescomuteid == '') {
       							me.setResCompute('自动分配', 'AUTO');
       						} else {
       							me.setResCompute(resData.rescomuteid, resData.rescomuteType);
       						}
           					$("#vLanIDO").val(resData.vLanIDO == '' ? '自动分配' : resData.vLanIDO);
           					$("#vLanIDI").val(resData.vLanIDI == '' ? '自动分配' : resData.vLanIDI);
           					$("#wanIps").val(resData.wanIp == '' ? '自动分配' : resData.wanIp);
           					$("#lanIps").val(resData.lanIp == '' ? '自动分配' : resData.lanIp);
           					
           					$("#virtualSwitch").val(resData.virtualSwitch);
           					$("#cpuResPool").val(resData.cpuResPool);
		 	        	    $("#rootHbaCard").val(resData.rootHbaCard);
		 	        	    $("#networkHbaCard").val(resData.networkHbaCard);
		 	        	    //me.switchResSelectView();
		 	        	   
		 	        	    $("#rescomuteid").val(resData.rescomuteid);
		 	        	    $("#rescomuteType").val(resData.rescomuteType);
		 	        	    
//		 	        	    $("#allocateType").val(resData.allocateType);
//      						$("#allocateType").trigger('change');
      						if(resData.allocateType == "2"){
      							$("#allocateType-nanotube").jqxRadioButton('check');
      							$('#unapprove-resource-tabcontent .resPartition').show();
      	     	    		    $('#unapprove-resource-tabcontent .resSwitch').hide();
      	     	    		    $('#unapprove-resource-tabcontent .resHba').hide();
      	     	    		    $('#unapprove-resource-tabcontent .resStHba').hide();
      	     	    		    $('#unapprove-resource-tabcontent .resLan').hide();
      	     	    		    $('#unapprove-resource-tabcontent .resAixSelect').show();
      							me.getCustomCode('aixPar', '/vms/getUnnanotubeVMByHost', 'vmName', 'resVmSid', false, 'POST', {'hostSid': resData.rescomuteid},false);
      							$("#aixPar").val(resData.aixParSid);
      						}else{
      							$("#allocateType-create").jqxRadioButton('check');
      						}
           				}
       					if(selectIndex == -1) {
       						event.args.owner._clickedcolumn = '';
       					}
       					if(event.args.owner._clickedcolumn != 'operate') {
			           		Core.AjaxRequest({
			     				type : 'get',
			     				url : ws_url + "/rest/order/getInstSpecForDataDisk/" + data.instanceSid,
			     				callback : function(data) {
			     					
			     					var sourcedatagrid ={
			     			              datatype: "json",
			     			              localdata: data
			     				    };
			     				    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
			     				    $("#unapprove-serviceSpecGrid").jqxGrid({source: dataAdapter});
			     				}
			     			});
			           		me.checkIntanceAllSelect();
       					}
       					
           			}
           			me.instanceRowSelect = true;
           		}
           		
            });
			
			
			
			$("#unapprove-detail-orderDetailsGrid").on('rowselect', function (event) {
           		var rowindex = event.args.rowindex;
           		if(rowindex >= 0){
           			var data = $('#unapprove-detail-orderDetailsGrid').jqxGrid('getrowdata', rowindex);
           			if(data) {
		           		Core.AjaxRequest({
		     				type : 'get',
		     				url : ws_url + "/rest/order/getInstSpecForDataDisk/" + data.instanceSid,
		     				callback : function(data) {

		     					var sourcedatagrid ={
		     			              datatype: "json",
		     			              localdata: data
		     				    };
		     				    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		     				    $("#unapprove-detail-serviceSpecGrid").jqxGrid({source: dataAdapter});
		     				  
		     				}
		     			});
           			}
           		}
            });
			
			$("#unapprove-orderDetailsGrid").on('bindingcomplete', function (event) {
		    	$("#unapprove-orderDetailsGrid").jqxGrid('selectrow', 0);
		    });
			
			$("#unapprove-detail-orderDetailsGrid").on('bindingcomplete', function (event) {
		    	$("#unapprove-detail-orderDetailsGrid").jqxGrid('selectrow', 0);
		    });
			
			$("#collapse-detail-serviceSpecGrid").on('click', function (event) {
		    	$("#unapprove-detail-serviceSpecGrid").show();
		    });
			
			/*
			$("#instance-specGrid").jqxGrid(
					{
			             width: "680px",
			             height: "227px",
						 theme:currentTheme,
			             columnsresize: true,
			             editable: true,
			             selectionmode: 'singlerow',
			             editmode: 'multiplecellsadvanced',
			             localization: gridLocalizationObj,
			             columns: [
			 	             { text: '规格项', datafield: 'description', editable: false},
			 	             { text: '当前值', datafield: 'value', width:215, editable: false},
			 	             { text: '调整后值', datafield: 'newValue', width:215, editable: true, columntype: 'custom',
			 	            	createeditor: function (row, cellvalue, editor, cellText, width, height) {
									var rowData = this.owner.getrowdatabyid(row);
									console.info('rowData=' + rowData);
									if(rowData.name === 'POWER_CPU_USED_UNIT'
										|| rowData.name === 'POWER_CPU_MIN_UNIT'
										|| rowData.name === 'POWER_CPU_MAX_UNIT'){
										editor.jqxNumberInput({ min: 0, decimalDigits: 1, inputMode: 'simple',  width: width, height: height, spinButtons: true, readOnly: true });
									} else if(rowData.name === 'MEMORY_MAX'
										|| rowData.name === 'MEMORY_MIN'
										|| rowData.name === 'CPU_MIN'
										|| rowData.name === 'CPU_MAX') {
										editor.jqxNumberInput({ min: 0, decimalDigits: 0, inputMode: 'simple',  width: width, height: height, spinButtons: true});
									}else if(rowData.name === 'MEMORY' ) {
										editor.jqxDropDownList({autoDropDownHeight: true,
											placeHolder: "请选择...",
											source: ['2', '4', '8', '16', '32', '64']
										});
									} else if(rowData.name === 'CPU') {
										editor.jqxDropDownList({autoDropDownHeight: true,
											placeHolder: "请选择...",
											source: ['1', '2', '4', '8', '16']
										});
									}
								},
								initeditor : function (row, cellValue, editor, cellText, width, height) {
									var rowData = this.owner.getrowdatabyid(row);
									if(rowData.name === 'POWER_CPU_USED_UNIT' || rowData.name === 'POWER_CPU_MIN_UNIT'|| rowData.name === 'POWER_CPU_MAX_UNIT'){
										editor.jqxNumberInput().val(cellValue);
									} else if(rowData.name === 'MEMORY' || rowData.name === 'MEMORY_MIN' || rowData.name === 'MEMORY_MAX'
										|| rowData.name === 'CPU' || rowData.name === 'CPU_MIN' || rowData.name === 'CPU_MAX') {
										editor.jqxDropDownList('selectItem', cellValue);
									}
								},
								validation: function (cell, value) {
									console.info('validation value=' + value);
									var rowData = this.owner.getrowdatabyid(cell.row);
									if(rowData.name === 'MEMORY_MIN') {
										var memory = me.getSpecData(this.owner.getrows(), 'MEMORY', 'newValue');
										var memoryDesc = me.getSpecData(this.owner.getrows(), 'MEMORY', 'description');
										console.info('memory=' + memory);
										if(memory < value) {
											return { result: false, message: rowData.description + "不能大于当前"+ memoryDesc +"。" };
										}
									}
									if(rowData.name === 'MEMORY_MAX') {
										var memory = me.getSpecData(this.owner.getrows(), 'MEMORY', 'newValue');
										var memoryDesc = me.getSpecData(this.owner.getrows(), 'MEMORY', 'description');
										console.info('memory=' + memory);
										if(memory > value) {
											return { result: false, message: rowData.description + "不能小于当前"+ memoryDesc +"。" };
										}
									}
									if(rowData.name === 'CPU_MIN') {
										var cpu = me.getSpecData(this.owner.getrows(), 'CPU', 'newValue');
										var cpuDesc = me.getSpecData(this.owner.getrows(), 'CPU', 'description');
										console.info('cpu=' + cpu);
										if(cpu < value) {
											return { result: false, message: rowData.description + "不能大于当前"+ cpuDesc +"。" };
										}
									}
									if(rowData.name === 'CPU_MAX') {
										var cpu = me.getSpecData(this.owner.getrows(), 'CPU', 'newValue');
										var cpuDesc = me.getSpecData(this.owner.getrows(), 'CPU', 'description');
										console.info('cpu=' + cpu);
										if(cpu > value) {
											return { result: false, message: rowData.description + "不能小于当前"+ cpuDesc +"。" };
										}
									}
									console.info('rowData.name=' + rowData.name);
									return true;
								}	
			 	             }
			             ],
			             showtoolbar: true,
			             toolbarheight:26,
		   	              // 设置toolbar操作按钮
		   	              rendertoolbar: function (toolbar) {
		   	            	  var container = $("<div style='width:100%;height:25px;line-height:25px;'></div>");
		   	                  var orderDetail = $("<font>&nbsp;&nbsp;基本规格变更列表</font>");
		   	                  toolbar.append(container); 
		   	                  container.append(orderDetail);
		   	              }
					 });
			*/
			
			$("#instance-diskSpecGrid").jqxGrid(
					{
			             width: "680px",
			             height: "137px",
						 theme:currentTheme,
			             columnsresize: true,
			             editable: true,
			             selectionmode: 'singlerow',
			             editmode: 'multiplecellsadvanced',
			             localization: gridLocalizationObj,
			             columns: [
			 	             { text: '磁盘名称', datafield: 'name', width:170, editable: false},
			 	             { text: '磁盘用途', datafield: 'valueText', width:130, editable: false},
			 	             { text: '当前值', datafield: 'value',  editable: false},
			 	             { text: '调整后值', datafield: 'newValue', editable: false},
			 	             { text: '操作', datafield: 'operate',width:80,editable: false}
			             ],
			             showtoolbar: false
					 });
			
			// 初始化规格
   	    	$("#cancel-specGrid").jqxGrid({
   	             width: "100%",
   	             height:"260px",
   	             theme:currentTheme,
   	             columnsresize: true,
   	             pageable: false, 
   	             sortable: true,
   	             localization: gridLocalizationObj,
   	             columns: [
   	                 { text: '规格名称', datafield: 'description',width:100},
   	                 { text: '规格值', datafield: 'valueText'}
   	             ],
   	             showtoolbar: false,
   	           // 设置toolbar操作按钮
   	           rendertoolbar: function (toolbar) {
   	               var container = $("<div id='explain' style='width:100%;height:30px;line-height:35px;'></div>");
   	               var orderDetail = $("<font>&nbsp;&nbsp;规格明细列表</font>");
   	               toolbar.append(container); 
   	               container.append(orderDetail);
   	           }
   	       });
   	    	$("#cancel-approvedspecGrid").jqxGrid({
   	    		width: "100%",
   	    		height:"260px",
   	    		theme:currentTheme,
   	    		columnsresize: true,
   	    		pageable: false, 
   	    		sortable: true,
   	    		localization: gridLocalizationObj,
   	    		columns: [
   	    		          { text: '规格名称', datafield: 'description',width:100},
   	    		          { text: '规格值', datafield: 'valueText'}
   	    		          ],
   	    		          showtoolbar: false,
   	    		          // 设置toolbar操作按钮
   	    		          rendertoolbar: function (toolbar) {
   	    		        	  var container = $("<div id='explain' style='width:100%;height:30px;line-height:35px;'></div>");
   	    		        	  var orderDetail = $("<font>&nbsp;&nbsp;规格明细列表</font>");
   	    		        	  toolbar.append(container); 
   	    		        	  container.append(orderDetail);
   	    		          }
   	    	});
			
	        $('#tab').on('selected', function (event) {
	            var index = event.args.item;
	            if (index == 1) {
	            	var rowindex = $('#unapprovedatagrid').jqxGrid('getselectedrowindex');
			    	if(rowindex >= 0){
					    var data = $('#unapprovedatagrid').jqxGrid('getrowdata', rowindex);
					    me.searchApproveHistory(data);
			    	}
	            }
	        });

	        $('#tab2').on('selected', function (event) {
	            var index = event.args.item;
	            if (index == 1) {
	            	var rowindex = $('#unapprovedatagrid').jqxGrid('getselectedrowindex');
			    	if(rowindex >= 0){
					    var data = $('#unapprovedatagrid').jqxGrid('getrowdata', rowindex);
					    me.searchApproveHistory(data);
			    	}
	            }
	        });
	    };
	    
	    this.checkRowResSelStatus = function (rowindex) {
	    	var data = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', rowindex);
	    	if(data) {
	   			var resInstName = data.resInstName;
	   			if($.trim(resInstName) == '') {
	   				me.setRowResSelStatus(rowindex, '0');
	   			}
	    	}
	    };
	    
	    this.checkIntanceAllSelect = function () {
	    	if(!me.instanceSid) return;
	    	var instanceSid = me.instanceSid;
	    	var rowindex = me.targetServiceInstanceIndex;
    		var data = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', rowindex);
   			var resInstName = data.resInstName;
   			var resData = me.getResData(instanceSid);

   			if(resData.ve === 'HMC,IVM') {
   				if(resData.allocateType == '1') {
		    		if(resData.partitionType === '0') {
		    			var rescomuteTreeItems = $("#rescomuteTree").jqxTree('getItems'); 
		    			var rootHbaCardItems = $("#rootHbaCard").jqxDropDownList('getItems');
		    			var networkHbaCardItems = $("#networkHbaCard").jqxDropDownList('getItems');
		    			var vLanIDIItems = $("#vLanIDI").jqxDropDownList('getItems');
		    			var lanIps = $("#lanIps").jqxDropDownList('getItems');
		    			if($.trim(resInstName) != '' &&
		    					(rescomuteTreeItems && rescomuteTreeItems.length > 0) &&
		    					(rootHbaCardItems && rootHbaCardItems.length > 0) &&
		    					(networkHbaCardItems && networkHbaCardItems.length > 0) &&
		    					(vLanIDIItems && vLanIDIItems.length > 0) &&
		    					(lanIps && lanIps.length > 0)) {
		    				me.setRowResSelStatus(rowindex, '1'); 
			    		} else {
			    			me.setRowResSelStatus(rowindex, '0');
			    		}
		    		} else if(resData.partitionType === '1') {
		    			var rescomuteTreeItems = $("#rescomuteTree").jqxTree('getItems'); 
		    			var virtualSwitchItems = $("#virtualSwitch").jqxDropDownList('getItems');
		    			var cpuResPoolItems = $("#cpuResPool").jqxDropDownList('getItems');
		    			var vLanIDIItems = $("#vLanIDI").jqxDropDownList('getItems');
		    			var lanIps = $("#lanIps").jqxDropDownList('getItems');
		    			if($.trim(resInstName) != '' &&
		    					(rescomuteTreeItems && rescomuteTreeItems.length > 0) &&
		    					(virtualSwitchItems && virtualSwitchItems.length > 0) &&
		    					(cpuResPoolItems && cpuResPoolItems.length > 0) &&
		    					(vLanIDIItems && vLanIDIItems.length > 0) &&
		    					(lanIps && lanIps.length > 0)) {
		    				me.setRowResSelStatus(rowindex, '1'); 
			    		} else {
			    			me.setRowResSelStatus(rowindex, '0');
			    		}
		    		}
   				} else if(resData.allocateType == '2') {
   					var rescomuteTreeItems = $("#rescomuteTree").jqxTree('getItems'); 
	    			var aixParItems = $("#aixPar").jqxDropDownList('getItems');
	    			if($.trim(resInstName) != '' &&
	    					(rescomuteTreeItems && rescomuteTreeItems.length > 0) &&
	    					(aixParItems && aixParItems.length > 0) && $("#aixPar").val() !="") {
	    				me.setRowResSelStatus(rowindex, '1'); 
		    		} else {
		    			me.setRowResSelStatus(rowindex, '0');
		    		}
   				}
	    	} else if(resData.ve === 'VMware') {
	    		if(resData.resType === '1') {
		    		var rescomuteTreeItems = $("#rescomuteTree").jqxTree('getItems'); 
	    			var vLanIDIItems = $("#vLanIDI").jqxDropDownList('getItems');
	    			var lanIps = $("#lanIps").jqxDropDownList('getItems');
		    		if($.trim(resInstName) != '' &&
		    				(rescomuteTreeItems && rescomuteTreeItems.length > 0) &&
		    				(vLanIDIItems && vLanIDIItems.length > 0) &&
		    				(lanIps && lanIps.length > 0)) {
	    				me.setRowResSelStatus(rowindex, '1'); 
		    		} else {
		    			me.setRowResSelStatus(rowindex, '0');
		    		}
	    		} else if(resData.resType === '2') {
	    			var rescomuteTreeItems = $("#rescomuteTree").jqxTree('getItems'); 
		    		if($.trim(resInstName) != '' &&
		    				(rescomuteTreeItems && rescomuteTreeItems.length > 0)) {
	    				me.setRowResSelStatus(rowindex, '1'); 
		    		} else {
		    			me.setRowResSelStatus(rowindex, '0');
		    		}
	    		}
	    	}
	    };

	    this.setRowResSelStatus = function (rowindex, resSelStatus) {
	    	if(me.isFinal !== true) return;
	    	if(me.targetServiceInstanceIndex == -1) return;
	   		var rowData = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', me.targetServiceInstanceIndex);
	   		if(!rowData) return;
	    	var rowId = $('#unapprove-orderDetailsGrid').jqxGrid('getrowid', me.targetServiceInstanceIndex);
			rowData['resSelStatus'] = resSelStatus;
			$('#unapprove-orderDetailsGrid').jqxGrid('updaterow', rowId, rowData);
	    	me.setResCheckAndComfirmButton();
	    };

	    this.setResCheckAndComfirmButton = function() {
	    	if(me.isFinal !== true) return;
	    	var allResSelect = true;
	    	var rows = $('#unapprove-orderDetailsGrid').jqxGrid('getrows');
	    	for(var i = 0; i < rows.length; i++) {
	    		if(rows[i]['resSelStatus'] !== '1') {
	    			allResSelect = false;
	    			break;
	    		}
	    	}
	    	var approveStatus;
	    	for(var i=0; i<$("[id^='approveStatus-']").length;i++){
	    		if($($("[id^='approveStatus-']")[i]).val()==true){
	    			approveStatus = $($("[id^='approveStatus-']")[i]).attr("name");
	    		}
	    	}
	    	if(approveStatus == '01') {
	    		if(allResSelect === false) {
	    			$("#vmResCheck").jqxButton({disabled: true});
		    		$("#approveMgtSave").jqxButton({disabled: true});
		    	} else if(allResSelect === true) {
		    		$("#vmResCheck").jqxButton({disabled: false});
		    		$("#approveMgtSave").jqxButton({disabled: false});
		    	}
	    	} else if(approveStatus == '02') {
	    		$("#vmResCheck").jqxButton({disabled: true});
	    		$("#approveMgtSave").jqxButton({disabled: false});
	    	}
	    };

	    this.setTreeExpend = function (id, level) {
	    	var tree = $("#" + id);
	    	var treeItems = tree.jqxTree("getItems");
	    	for(var i = 0; i < treeItems.length; i++) {
	    		if(treeItems[i].level < level) {
	    			tree.jqxTree('expandItem', treeItems[i]);
	    		} else if(treeItems[i].level >= level) {
	    			tree.jqxTree('collapseItem', treeItems[i]);
	    		}
	    	}
	    };

	    /** 根据虚拟机化环境，决定资源选择终审页面显示*/
		this.switchResSelectView = function() {
			var resType = me.getResType();
			var vEnv = $("#resVe").val();
			var mgtObjSid = $("#resMgtObjSid").val();
			var partitionType = $("#partitionType").val();
			//虚拟机
			if(resType == '1') {
				if(vEnv === 'VMware') {
					$('#unapprove-resource-tabcontent .resType').show();
					$('#unapprove-resource-tabcontent .resPartition').hide();
					$('#unapprove-resource-tabcontent .resAllocateType').hide();
					$('#unapprove-resource-tabcontent .resCompute').show();
					$('#unapprove-resource-tabcontent .resSwitch').hide();
					$('#unapprove-resource-tabcontent .resHba').hide();
					$('#unapprove-resource-tabcontent .resStHba').hide();
					$('#unapprove-resource-tabcontent .resLan').show();
					$('#unapprove-resource-tabcontent .resWan').hide();
					me.initResCompute();
				} else if(vEnv === 'HMC,IVM') {
					$('#unapprove-resource-tabcontent .resType').hide();
					$('#unapprove-resource-tabcontent .resPartition').show();
					$('#unapprove-resource-tabcontent .resAllocateType').show();
					$('#unapprove-resource-tabcontent .resCompute').show();
					$('#unapprove-resource-tabcontent .resSwitch').hide();
					$('#unapprove-resource-tabcontent .resHba').hide();
					$('#unapprove-resource-tabcontent .resStHba').hide();
					$('#unapprove-resource-tabcontent .resLan').show();
					$('#unapprove-resource-tabcontent .resWan').hide();
					$('#partitionType').trigger('change');
				}
			} else {
				$('#unapprove-resource-tabcontent .resPartition').hide();
				$('#unapprove-resource-tabcontent .resCompute').show();
				$('#unapprove-resource-tabcontent .resSwitch').hide();
				$('#unapprove-resource-tabcontent .resHba').hide();
				$('#unapprove-resource-tabcontent .resStHba').hide();
				if(me.getResType() == '1') {
					$('#unapprove-resource-tabcontent .resLan').show();
				} else {
					$('#unapprove-resource-tabcontent .resLan').hide();
				}
				$('#unapprove-resource-tabcontent .resWan').hide();
				me.initResCompute();
			}
		};

		this.initResCompute = function () {
	       me.initResComputeTree();
	       me.setResCompute('自动分配', 'AUTO');
		};

		this.getResPoolType = function () {
	    	var ve = $("#resVe").val();
 		    var partitionType = $("#partitionType").val();
 		    var resType = me.getResType();
			var resPoolType = "";
    		if(ve == 'HMC,IVM') {
    			if(partitionType == '1') {
    				//分区类型选择逻辑分区
    				resPoolType = "PCVP";
    			} else if(partitionType == '0'){
    				//分区类型选择微分区
    				resPoolType = "PCP";
    			}
  			} else if(ve == 'VMware') {
  				if(resType == '1') {
  					resPoolType = "PCVX";
  				} else if(resType == '2') {
  					resPoolType = "PCX";
  				}
  			}
    		return resPoolType;
		};

		this.getResType = function () {
			var resType = '1';
			if($('#openResTypeVm').val() === true) {
				resType = '1';
			}
			if($('#openResTypeHost').val() ===  true) {
				resType = '2';
			}
			return resType;
		};

		this.setResType = function (resType) {
			if(resType == '1') {
				$('#openResTypeVm').val(true);
			} else if(resType == '2') {
				$('#openResTypeHost').val(true);
			}
		};
	    /***********************************************审核历史记录****************************************/
    
	    // 查询审核历史记录
	    this.searchApproveHistory = function(data){
	    	
	    	//初始化工单记录
	    	 Core.AjaxRequest({
	    		url : ws_url + "/rest/approve/platform/getApproveHistoryRecords/" + data.processInstanceId,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				me.itemsHistory(data.approveRecord);
	 				me.initOrderApproveRecordDatagrid(data.approveRecord);
	 				if(data.activityImageInfo != null) {
	 					var widthScale = $("#processPic").width() / data.activityImageInfo.imageWidth;
	 					var heightScale = $("#processPic").height() / data.activityImageInfo.imageHeight;
	 					var borderWidth = $("#processTrace").css("border-width");
	 					var left = data.activityImageInfo.x * widthScale - (borderWidth == '' ?  0 : parseFloat(borderWidth));
	 					var top = data.activityImageInfo.y * heightScale - (borderWidth == '' ?  0 : parseFloat(borderWidth));
		 				$("#processTrace").css("left", left  + "px");
		 				$("#processTrace").css("top", top + "px");
		 				$("#processTrace").css("width", data.activityImageInfo.width * widthScale + "px");
		 				$("#processTrace").css("height", data.activityImageInfo.height * heightScale + "px");
	 				}
	 				data.activityImageInfo = null;
	 			}
	 		 });
	    };
	    
	    // 初始化查看工单datagrid的数据源
	    this.initOrderApproveRecordDatagrid = function(data){
	    	 var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		     };
	    	 
	    	 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	    	// 初始化
        	 $("#unapproveHistoryGrid").jqxGrid({
   	              width: "100%",
   	              height:"160px",
   				  theme:"metro",
   	              columnsresize: true,
   	              source:dataAdapter,
   	              //autoheight: false,
   	              //autowidth: false,
   	              //autorowheight: true,
   	              
   	              sortable: true,
   	              localization: gridLocalizationObj,
   	              columns: [
   	                  { text: '流程类型', datafield: 'processTypeName'},
   	                  { text: '审核操作', datafield: 'approvorActionName'},
   	                  { text: '审核者', datafield: 'approvorId'},
   	                  { text: '审核状态', datafield: 'approveStatusName'},   	                  
   	                  { text: '审核时间', datafield: 'approveDate'},
   	                  { text: '审核意见', datafield: 'approveOpinion'}
   	              ],
   	              showtoolbar: true,
   	              // 设置toolbar操作按钮
   	              rendertoolbar: function (toolbar) {
   	                  var container = $("<div style='width:100%;height:30px;line-height:35px;'></div>");
   	                  var orderDetail = $("<font>&nbsp;&nbsp;订单审批记录</font>");
   	                  toolbar.append(container);
   	                  container.append(orderDetail);
   	              }
   	          });
	    };
	    /***********************************************审核历史记录****************************************/
	    /** 初始化IP列表*/
	    this.initIpAddressDropDownList = function(id, netSid){
	    	if(!me.instanceSid) return;
	    	
	    	var dropdownCmp = $("#"+id+"");
	    	dropdownCmp.jqxDropDownList({
				   placeHolder: "请选择...",
                 displayMember: 'ipAddressWithStatus', 
                 valueMember: 'ipAddress',
                 width: 220,
                 height: 22,
                 autoDropDownHeight :false,
                 dropDownWidth :220,
                 dropDownHeight :180,
                 disabled:false,
                 theme: currentTheme
            });
	    	if(!netSid || netSid == '') {
	    		dropdownCmp.jqxDropDownList('clear');
	    		return;
	    	};
	    	if(netSid == '自动分配') {
	    		dropdownCmp.jqxDropDownList('clear');
	    		$("#"+id+"").jqxDropDownList('insertAt', { label:"自动分配" ,value:"自动分配"},0);
	    		$("#"+id+"").jqxDropDownList('selectIndex', 0 ); 
	    	} else {
	    		Core.AjaxRequest({
					url :ws_url +"/rest/ip/network/"+ netSid, 
					type:'get',
					async:false,
					callback : function (data) {
						if(data.length == 0) {
							me.checkInstanceResSelect(id);
						}
						for(var i = 0; i < data.length; i++) {
							data[i].ipAddressWithStatus = data[i].ipAddress + "(" + data[i].usageStatusName + ")";
						}
						var source ={
				             datatype: "json",
				             datafields: [
				                 { name: 'ipAddress' },
				                 { name: 'ipAddressWithStatus' },
				             ],
				             localdata:data
				         };
						 var dataAdapter = new $.jqx.dataAdapter(source);
						 dropdownCmp.jqxDropDownList({source: dataAdapter});
						if(data.length > 0) {
							$("#"+id+"").jqxDropDownList('insertAt', { label:"自动分配" ,value:"自动分配"},0);
						}
						$("#"+id+"").jqxDropDownList('selectIndex', 0 ); 
			        } 
			     });
	    	}
	    };

	    this.checkInstanceResSelect = function (id) {
	    	if(me.isFinal !== true) return;
	    	if(me.targetServiceInstanceIndex == -1) {
	    		return;
	    	}
	    	var rowData = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', me.targetServiceInstanceIndex);
	    	var instanceSid = rowData.instanceSid;
	    	var resData = me.getResData(instanceSid);
	    	if(resData.ve === 'HMC,IVM') {
	    		if(resData.partitionType === '0') {
	    			if(id === 'rescomuteTree' || id === 'rootHbaCard' || id === 'networkHbaCard' || id === 'vLanIDI' || id === 'lanIps') {
		    			me.setRowResSelStatus(me.targetServiceInstanceIndex, '0');
		    		}
	    		} else if(resData.partitionType === '1') {
	    			if(id === 'rescomuteTree' || id === 'virtualSwitch' || id === 'cpuResPool' || id === 'vLanIDI' || id === 'lanIps') {
		    			me.setRowResSelStatus(me.targetServiceInstanceIndex, '0');
		    		}
	    		}
	    	} else if(resData.ve === 'VMware') {
	    		var resType = resData.resType;
	    		if(resType == '1') {
		    		if(id === 'rescomuteTree' || id === 'vLanIDI' || id === 'lanIps') {
		    			me.setRowResSelStatus(me.targetServiceInstanceIndex, '0');
		    		}
	    		} else if(resType == '2') {
	    			if(id === 'rescomuteTree') {
		    			me.setRowResSelStatus(me.targetServiceInstanceIndex, '0');
		    		}
	    		}
	    	}
	    };
	    
	    
	    this.initDropDownList = function (id, fieldText, valueText, checkBoxFlag) {
	    	var dropdownCmp = $("#"+id+"");
			 dropdownCmp.jqxDropDownList({
				   placeHolder: "请选择...",
				   checkboxes: checkBoxFlag,
                  displayMember: fieldText, 
                  valueMember: valueText,
                  width: 220,
                  height: 22,
                  autoDropDownHeight: false,
                  dropDownWidth: 220,
                  dropDownHeight: 180,
                  disabled: false,
                  theme: currentTheme
           });
	    };
	    
	    // 自定义数据字典方法
	    this.getCustomCode =function(id,url,fieldText,valueText,nullItem,methodType,params, checkBoxFlag){
	    	Core.AjaxRequest({
				url :ws_url +"/rest"+ url, 
				type:methodType = null ? "get":methodType,
				params:params = null ? "":params,
				async:false,
				callback : function (data) {
					var dropdownCmp = $("#"+id+"");
					 dropdownCmp.jqxDropDownList({
						   placeHolder: "请选择...",
						   checkboxes: checkBoxFlag,
		                   displayMember: fieldText, 
		                   valueMember: valueText,
		                   width: 220,
		                   height: 22,
		                   autoDropDownHeight: false,
		                   dropDownWidth: 220,
		                   dropDownHeight: 180,
		                   disabled: false,
		                   theme: currentTheme
		            });
					 
					if(!data ||data.length == 0) {
						me.checkInstanceResSelect(id);
						if(id == 'vLanIDI') {
							$('#lanIps').jqxDropDownList('clear');
						}
					}
					var includeAutoAllc = [];
					if((id == 'virtualSwitch' || id == 'cpuResPool' || id == 'rootHbaCard' || id == 'networkHbaCard' || id == 'vLanIDI') && data.length > 0) {
						 var auto = {};
						 auto[fieldText] = '自动分配';
						 auto[valueText] = '自动分配';
						 includeAutoAllc.push(auto);
					}
					for(var i = 0; i < data.length; i++) {
						 includeAutoAllc.push(data[i]);
					}
					var source ={
			             datatype: "json",
			             datafields: [
			                 { name: fieldText },
			                 { name: valueText }
			             ],
			             localdata:includeAutoAllc
			         };
					 var dataAdapter = new $.jqx.dataAdapter(source);
					 dropdownCmp.jqxDropDownList({source: dataAdapter});
//					 if((id == 'virtualSwitch' || id == 'cpuResPool' || id == 'rootHbaCard' || id == 'networkHbaCard' || id == 'vLanIDI') && data.length > 0) {
//						 $("#"+id+"").jqxDropDownList('insertAt', { label:"自动分配" ,value:""},0);
//					 }
//					 $("#"+id+"").jqxDropDownList('selectIndex', 0 );
		        }
		     });
	    };
	    
	    this.setAutoAllocate = function (id) {
	    	var dropDownElem = $("#"+id+"");
	    	dropDownElem.jqxDropDownList('clear'); 
	    	dropDownElem.jqxDropDownList('insertAt', { label:"自动分配" ,value: "自动分配"}, 0);
	    	dropDownElem.jqxDropDownList('selectIndex', 0 );
	    };

	    this.initResComputeTree = function () {
	    	var mgtObjSid = $("#resMgtObjSid").val();
	    	var resPoolType = me.getResPoolType();
	    	if($.trim(resPoolType) == '' || $.trim(mgtObjSid) == '') {
	    		return;
	    	}
	    	Core.AjaxRequest({
  				type : 'post',
  				url : ws_url + "/rest/mgtObj/findMgtObjComTree",
  				async: false,
  				params: {
  					resPoolType: resPoolType,
  					mgtObjSid: mgtObjSid, 
  				},
  				callback: function (treeData) {
  				  if(treeData.length == 0) {
  					me.checkInstanceResSelect('rescomuteTree');
  				  }
  				  var treeFillData = [];
  				  if(treeData.length > 0) {
	  				  treeFillData = [{
	  					  'resTopologySid': '-1',
	  					  'parentTopologySid': '',
	  					  'resTopologyValue': '自动分配,AUTO',
	  					  'resTopologyName': '自动分配',
	  					  'topologyIcon': '../../images/icon/resource-pool-pni-2.png'
	  				  }];
	  				  for(var i = 0; i < treeData.length; i++) {
	  					treeData[i]['resTopologyValue'] = treeData[i]['resTopologySid'] + "," + treeData[i]['resTopologyType'];
	  					treeFillData.push(treeData[i]);
	  				  }
  				  } else {
  					  $("#rescomuteTree").jqxTree('clear');
  					  var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">请选择...</div>';
  					  $("#rescomuteLabel").jqxDropDownButton('setContent', dropDownContent);
  					  $("#rescomuteid").val('');
  					  $("#virtualSwitch").jqxDropDownList('clear');
  					  $("#cpuResPool").jqxDropDownList('clear');
		 	          $("#rootHbaCard").jqxDropDownList('clear');
		 	          $("#stHbaDiv").jqxDropDownList('clear');
		 	          $("#networkHbaCard").jqxDropDownList('clear');
		 	          $("#vLanIDI").jqxDropDownList('clear');
		 	          $("#lanIps").jqxDropDownList('clear');
  				  }
  	              var source =
  	              {
  	                  datatype: "json",
  	                  datafields: [
  	                      { name: 'resTopologySid' },
  	                      { name: 'parentTopologySid' },
  	                      { name: 'resTopologyValue' },
  	                      { name: 'resTopologyName' },
  				          { name: 'topologyIcon' },
  	                  ],
  	                  id: 'resTopologySid',
  	                  localdata: treeFillData
  	              };
  	              var dataAdapter = new $.jqx.dataAdapter(source);
  	              dataAdapter.dataBind();
  	              var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'resTopologyValue', map: 'value'},{ name: 'topologyIcon', map: 'icon'}]);
  		          $('#rescomuteTree').jqxTree({source: records, height: '100%', width: '100%', theme : currentTheme});
  		          $('#rescomuteTree').jqxTree('selectItem', null);
  		          me.setTreeExpend('rescomuteTree', 0);
  				}
  			});
	    };
	    
	    this.setResCompute = function (rescomuteid, rescomuteType) {
	    	var rescomuteLabel = '';
	    	var rescompteIcon = '';
	    	var currentTreeItem = null;
	    	var treeItems = $("#rescomuteTree").jqxTree('getItems');
	    	if(!treeItems) return;
			for(var i = 0; i < treeItems.length; i++) {
				if((rescomuteid + "," + rescomuteType) == treeItems[i].value) {
					rescomuteLabel = treeItems[i].label;
					rescompteIcon = treeItems[i].icon;
					currentTreeItem = treeItems[i];
					break;
				}
			}
//   			if(!rescomuteid || rescomuteid == '') {
//   				rescomuteLabel = '自动分配';
//   				rescompteIcon = "../../images/icon/resource-pool-pni-2.png";
//   				$("#rescomuteid").val('');
//   			} else {
//   				var treeItems = $("#rescomuteTree").jqxTree('getItems');
//   				for(var i = 0; i < treeItems.length; i++) {
//   					if((rescomuteid + "," + rescomuteType) == treeItems[i].value) {
//   						rescomuteLabel = treeItems[i].label;
//   						rescompteIcon = treeItems[i].icon;
//   						break;
//   					}
//   				}
//   			}
			if(currentTreeItem) {
	   			var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;"><img width="16" height="16" style="float: left; margin-right: 4px;" class="itemicon" src="'+ rescompteIcon+'">' + rescomuteLabel + '</div>';
	            $("#rescomuteLabel").jqxDropDownButton('setContent', dropDownContent);
	            $("#rescomuteTree").jqxTree('selectItem', currentTreeItem);
	            if(rescomuteid == '自动分配' || rescomuteType == 'VE' || rescomuteType == 'VC') {
	            	me.setAutoAllocate('virtualSwitch');
	            	me.setAutoAllocate('cpuResPool');
                	me.setAutoAllocate('rootHbaCard');
                	me.setAutoAllocate('stHbaDiv');
                	me.setAutoAllocate('networkHbaCard');
                	me.setAutoAllocate('vLanIDI');
                	$("#rescomuteid").val('');
	            } else {
	            	$("#rescomuteid").val(rescomuteid);
	            }
			} 
//			else {
				me.initDropDownList('virtualSwitch', 'resVsName', 'resVsSid',false );
				me.initDropDownList('cpuResPool', 'cpuPoolName', 'resCpuPoolSid',false );
				me.initDropDownList('rootHbaCard', 'itemLocation', 'hostItemId',false);
				me.initDropDownList('stHbaDiv', 'itemLocation', 'hostItemId',true);
				me.initDropDownList('networkHbaCard', 'itemLocation', 'hostItemId',false);
				me.initDropDownList('vLanIDI', 'networkName', 'resNetworkSid',false);
//			}
	    };
	    
	    this.removeDupIps = function (selectIndex) {
	    	var data = $('#unapprove-orderDetailsGrid').jqxGrid('getrowdata', selectIndex);
	    	var resData = me.getResData(data.instanceSid); 
	    	//清除已选择IP,避免IP重复
			if(me.resSelectData.length > 0) {
				for(var i = 0; i < me.resSelectData.length; i++) {
					if(me.resSelectData[i].wanIp && $.trim(me.resSelectData[i].wanIp) != '') {
						var items = $('#wanIps').jqxDropDownList('getItems');
						var hasIp = false;
						for(var j = 0; j < items.length; j++) {
							if(items[j].value == me.resSelectData[i].wanIp) {
								hasIp = true;
							}
						}
						if(hasIp && resData.wanIp != me.resSelectData[i].wanIp) {
							$('#wanIps').jqxDropDownList('removeItem', me.resSelectData[i].wanIp);
						}
					}
					if(me.resSelectData[i].lanIp && $.trim(me.resSelectData[i].lanIp) != '') {
						var items = $('#lanIps').jqxDropDownList('getItems');
						var hasIp = false;
						for(var j = 0; j < items.length; j++) {
							if(items[j].value == me.resSelectData[i].lanIp) {
								hasIp = true;
							}
						}
						if(hasIp && resData.lanIp != me.resSelectData[i].lanIp) {
							$('#lanIps').jqxDropDownList('removeItem', me.resSelectData[i].lanIp);
						}
					}
				}
			}
	    };
	    
	    this.getSpecData = function (datas, name, valueName) {
	    	var res = null;
	    	for(var i = 0; i < datas.length; i++) {
	    		if(datas[i]['name'] === name) {
	    			res = datas[i][valueName] + '';
	    			break;
	    		}
	    	}
	    	return res;
	    };
	    

  };

  