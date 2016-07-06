// 服务实例js
var serviceBillingModel = function () {
		var me = this;
	    this.itemsServiceInstance = ko.observableArray();
	    this.searchObj = {
	    	 mgtObjNameLike:""
	    };

	    // 用户数据
	    this.searchServiceBilling = function(){
	    	 Core.AjaxRequest({
	    		url : ws_url + "/rest/serviceInstances/mgtObj/find",
	 			type:'post',
	 			params:me.searchObj,
	 			async:false,
	 			callback : function (data) {
	 			   me.itemsServiceInstance(data);
	 			   var sourcedatagrid ={
			              datatype: "json",
			              localdata: me.itemsServiceInstance
			       };
		    	   var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
		    	   $("#jqxgrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 查询当前管理对象的服务实例详细数据
	    this.searchBillingServiceInstance = function(mgtObjSid){
	    	var billingServiceInstanceDetailData = null;
	    	 Core.AjaxRequest({
	    		url : ws_url + "/rest/serviceInstances/mgtObj/find/"+mgtObjSid,
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				console.log("服务计量数据："+JSON.stringify(data));
	 				billingServiceInstanceDetailData = data;
	 			}
	 		 });
	    	 return billingServiceInstanceDetailData;
	    };
	    
	 // 查询当前租户的服务实例详细数据
	    this.searchBillingServiceInstanceInfo = function(billingServiceInstanceDetailData){
	    	var vmData=[],blockData=[],objData=[],ipData=[];
	    	if(billingServiceInstanceDetailData != null || billingServiceInstanceDetailData != ""){
	    		for(var i=0;i<billingServiceInstanceDetailData.length;i++){
	    			if(billingServiceInstanceDetailData[i].serviceType == 'VM'){
	    				vmData.push(billingServiceInstanceDetailData[i]);
	    			}else if(billingServiceInstanceDetailData[i].serviceType == 'BS'){
	    				blockData.push(billingServiceInstanceDetailData[i]);
	    			}else if(billingServiceInstanceDetailData[i].serviceType == 'OS'){
	    				objData.push(billingServiceInstanceDetailData[i]);
	    			}else if(billingServiceInstanceDetailData[i].serviceType == 'IP'){
	    				ipData.push(billingServiceInstanceDetailData[i]);
	    			}
	    		}
	    	}
	    	// 云主机
	    	var vmdatagrid ={datatype: "json",localdata: vmData};
	    	var vmdataAdapter = new $.jqx.dataAdapter(vmdatagrid);
	    	$("#billingVmDatagrid").jqxGrid({source: vmdataAdapter});
	    	// 块存储
	    	var bsdatagrid ={datatype: "json",localdata: blockData};
	    	var bsdataAdapter = new $.jqx.dataAdapter(bsdatagrid);
	    	$("#billingBlockStorageDatagrid").jqxGrid({source: bsdataAdapter});
	    	// 对象存储
//	    	var osdatagrid ={datatype: "json",localdata: objData};
//	    	var osdataAdapter = new $.jqx.dataAdapter(osdatagrid);
//	    	$("#billingObjStorageDatagrid").jqxGrid({source: osdataAdapter});
	    	// ip
	    	var ipdatagrid ={datatype: "json",localdata: ipData};
	    	var ipdataAdapter = new $.jqx.dataAdapter(ipdatagrid);
	    	$("#billingIpDatagrid").jqxGrid({source: ipdataAdapter});
	    };
	    
	   // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initServiceBillingInput = function(){
	    	// 初始化查询组件
	        $("#mgtObjNameLike").jqxInput({placeHolder: "", height: 23, width: 120, minLength: 1,theme:currentTheme});
	        $("#search-billing-button").jqxButton({ width: '50',height: 23, theme:currentTheme});
	        $("#viewServiceDetailsCloseButton").jqxButton({ width: '50',height: 23, theme:currentTheme});
	    };

	    // 初始化用户datagrid的数据源
	    this.initServiceBillingDatagrid = function(){
	          $("#jqxgrid").jqxGrid({
	              width: "98%",
				 // height:"99%",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              rowsheight: 30,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '项目名称', datafield: 'mgtObjName'},
	  	              { text: '云主机(个)', datafield: 'vmInstanceCount'},
	  	              { text: '块存储(个)', datafield: 'blockStorageInstanceCount' },
//	  	              { text: '对象存储(个)', datafield: 'objectStorageInstanceCount' },
  	                  { text: '浮动IP(个)', datafield: 'floatingIpInstanceCount' },
  	                  { text: '总价(元)', datafield: 'totalMoney' },
  	                  { text: '操作', editable: false, width:150,  datafield: '',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
  	                	  	return "<div class='customButton' style='margin-top:4px;margin-left:12px;width:50px;height:15px;float:left' onclick='popServiceDetailsWindow("+row+")'>服务详情</div>";
  	                	  //return "<div class='customButton' style='margin-top:4px;margin-left:12px;width:50px;height:15px;float:left' onclick='popServiceDetailsWindow("+row+")'>服务详情</div>&nbsp;&nbsp;<div class='customButton' style='margin-top:4px;margin-left:12px;width:50px;height:15px;float:left' onclick='popBillingDetailsWindow("+row+")'>账单详情</div>";
 	                  	}
			          }
	              ],
	              showtoolbar: false
	          });
	          // 云主机
	          $("#billingVmDatagrid").jqxGrid({
	              width: 777,
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              localization: gridLocalizationObj,
	              columns: [
						{ text: '实例名称', datafield: 'instanceName'},
						{ text: '配置', datafield: 'serviceConfig',width:180},
						{ text: '状态', datafield: 'statusName',width:60},
						{ text: '开通时间', datafield: 'dredgeDate'},
						{ text: '退订时间', datafield: 'expiringDate'},
						{ text: '时长(天)', datafield: 'billingDay',width:60}
	              ],
	              showtoolbar: false
	          });
	          // 块存储
	          $("#billingBlockStorageDatagrid").jqxGrid({
	              width: 777,
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              localization: gridLocalizationObj,
	              columns: [
	                    { text: '实例名称', datafield: 'instanceName'},
	                    { text: '配置', datafield: 'serviceConfig',width:180},
						{ text: '状态', datafield: 'statusName',width:60},
						{ text: '开通时间', datafield: 'dredgeDate'},
						{ text: '退订时间', datafield: 'expiringDate'},
						{ text: '时长(天)', datafield: 'billingDay',width:60}
	              ],
	              showtoolbar: false
	          });
	          // 对象存储
//	          $("#billingObjStorageDatagrid").jqxGrid({
//	              width: 777,
//				  theme:currentTheme,
//	              columnsresize: true,
//	              pageable: true, 
//	              pagesize: 10, 
//	              autoheight: true,
//	              localization: gridLocalizationObj,
//	              columns: [
//	                    { text: '实例名称', datafield: 'instanceName'},
//	                    { text: '配置', datafield: 'serviceConfig',width:180},
//						{ text: '状态', datafield: 'statusName',width:60},
//						{ text: '开通时间', datafield: 'dredgeDate'},
//						{ text: '退订时间', datafield: 'expiringDate'},
//						{ text: '时长(天)', datafield: 'billingDay',width:60}
//	              ],
//	              showtoolbar: false
//	          });
	          // 浮动IP
	          $("#billingIpDatagrid").jqxGrid({
	              width: 777,
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              localization: gridLocalizationObj,
	              columns: [
	                    { text: 'IP地址/服务实例名称', datafield: 'instanceName'},
	                    /*{ text: '配置', datafield: 'serviceConfig',width:180},*/
						{ text: '状态', datafield: 'statusName',width:60},
						{ text: '开通时间', datafield: 'dredgeDate'},
						{ text: '退订时间', datafield: 'expiringDate'},
						/*{ text: '时长(天)', datafield: 'billingDay',width:60}*/
	              ],
	              showtoolbar: false
	          });
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	$("#popServiceBillingDetailsWindow").jqxWindow({
                width: 800, 
                height:415, 
                resizable: false, 
                isModal: true, 
                autoOpen: false, 
                theme: currentTheme,
                cancelButton: $("#viewServiceDetailsCloseButton"),
                modalOpacity: 0.3,
                initContent:function(){
                	$('#serviceBillingTabs').jqxTabs({position: 'top',width:"100%",height:"350px",theme:"metro",selectionTracker:"checked"});
                }
            });
	    };
  };
  
  // 查询
  function searchServiceInstanceBillingInfo(){
	  Core.AjaxRequest({
  		url : ws_url + "/rest/serviceInstances/mgtObj/find",
			type:'post',
			params:{
				mgtObjNameLike : $("#mgtObjNameLike").val()
			},
			async:false,
			callback : function (data) {
			   var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
		       };
	    	   var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
	    	   $("#jqxgrid").jqxGrid({source: dataAdapter});
			}
		 });
  }
  
  // 租户的服务详情
  function popServiceDetailsWindow(row){
	    var data = $('#jqxgrid').jqxGrid('getrowdata', row);
        
	    var me = new serviceBillingModel();
        var instancedata = me.searchBillingServiceInstance(data.mgtObjSid);
        me.searchBillingServiceInstanceInfo(instancedata);
        
        var windowW = $(window).width(); 
        var windowH = $(window).height(); 
        $("#popServiceBillingDetailsWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-415)/2 } });
        $("#popServiceBillingDetailsWindow").jqxWindow('open');
  }
  
  // 租户账单详情
  function popBillingDetailsWindow(row){
	  /* 1、能取到每种服务实例的使用天数; 
	   * 2、取出服务定价; 
	   * 3、算出价格，且按天数展示
	   */
	  var data = $('#jqxgrid').jqxGrid('getrowdata', row);
      
	  var me = new serviceBillingModel();
      var instancedata = me.searchBillingServiceInstance(data.mgtObjSid);
	  
  }
  
  // 计算租户每种服务使用的价钱
  function getServiceListData(instancedata){
	  
  }
  
  