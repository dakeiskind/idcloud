var billingMgtModel = function () {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		tenantId: "", 
	    		status : ""
		};
	    
	    // 查询账户信息
	    this.searchUserBillingInfo = function(tenantId){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/billing",
	 			type:'post',
	 			params:{
	 				tenantId: tenantId, 
	 			},
	 			async:false,
	 			callback : function (data) {
	 				me.items(data);
	 				var sourcedatagrid ={
 			              datatype: "json",
 			              localdata: me.items
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#userBillingDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 查询租户账单
	    this.searchBillignInfo = function(){
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/mgtObj/findBillAccount",
	 			type:'post',
	 			params:{},
	 			async:false,
	 			callback : function (data) {
	 				me.items(data);
	 				var sourcedatagrid ={
 			              datatype: "json",
 			              localdata: me.items
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#billingAccountDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initInput = function(){
	    	// 初始化查询组件
	        $("#search-billing-button").jqxButton({ width: '50px',height:'25px',theme:"metro"});
	        $("#billing-button").jqxButton({ width: '50px',height:'25px',theme:"metro"});
	        
	        // 初始化下拉列表框 
     	    $("#search-billing-tenant").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:"metro"});
     	    $("#billing-balance-tenant").jqxInput({placeHolder: "", height: 22, width: 180, minLength: 1,disabled:true,theme:"metro"});
     	    
     	    // 用户账单window初始化组件
     	    $("#search-user-billing-button").jqxButton({ width: '50px',height:'25px',theme:"metro"});
			 
	    };
	    
	    // 验证
	    this.initValidator = function(){
	    	$('#billingBalanceForm').jqxValidator({
                rules: [
                          
                       ]
	    	});
	    	
	    	$('#billingMgtForm').jqxValidator({
                rules: [
                          { input: '#billing-pay-money', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#billing-pay-money', message: '只能输入数字', action: 'keyup, blur', rule: 'number' },
                          { input: '#billing-pay-money', message: '扣款金额必须大于0', action: 'keyup, blur', rule: function (input, commit) {
                        	  if(Number(input.val()) > 0) {
                                  return true;
                              }else{
                            	  return false;
                              }
                          	} 
                          }
                       ]
	    	});
	    	
	    	// 新增用户表单验证成功
	    	$('#billingBalanceForm').on('validationSuccess', function (event) {

	    			var type = $("#billing-balance-type").val();
	    			var tenantId = $("#billing-balance-tenant-sid").val();
	    			
	    			var year = $("#billing-balance-year-time").val();
	    			var month = $("#billing-balance-month-time").val();

	    			var time = year+"-"+((Number(month))>9?(Number(month)):"0"+(Number(month)));
	    			
	    			if(tenantId == "" || tenantId == null){
	    				 Core.alert({
	    						title:"提示",
	    						message:"租户名称不能为空 ！"
	    				  });
	    			}else{
			    		 Core.AjaxRequest({
				 				url : ws_url + "/rest/billing/billingbalance/"+type+"/"+time+"/"+tenantId+"",
				 				type:"get",
				 				callback : function (data) {
				 					$("#billingBalanceWindow").jqxWindow('close');
				 					me.searchBillignInfo();
				 			    },
				 			    failure:function(data){
				 			    	$("#billingBalanceWindow").jqxWindow('close');
				 			    }
				 		 });
	    			}
	    	 });
	    	
	    	// 账单管理---扣款
	    	$('#billingMgtForm').on('validationSuccess', function (event) {
	    		var bill = Core.parseJSON($("#billingMgtForm").serializeJson());
	    		
	    		var payableAmount = parseFloat($("#payableAmount").html());
	    		if(payableAmount < bill.paymentAmount){
	    			Core.alert({
	    				title:"提示",
	    				message:"不能超出应付金额！"
	    	  		});
	    		}else{
		    		// 调用BSS系统的接口
		    		 Core.AjaxRequest({
			 				url : ws_url + "/rest/billing/prepaidDebit/"+bill.billSid+"/"+bill.mgtObjSid+"/"+bill.paymentAmount+"/"+true+"",
			 				type:"GET",
			 				params :bill,
			 				callback : function (data) {
			 					// 刷新datagrid
			 					me.searchBillignInfo();
			 					
		 					    var userBill = new billingMgtModel();
		 					    // 获取到当前选中账户的SID.刷新租户账单
		 					    userBill.searchUserBillingInfo(bill.mgtObjSid);
		 					    // 刷新余额
		 					    var row = $('#billingAccountDatagrid').jqxGrid('getselectedrowindex');
		 				  	    var acData = $('#billingAccountDatagrid').jqxGrid('getrowdata', row);
		 					    // 账户余额
		 					    $("#userBalance").html(acData.balance);
		 					    // 清除选择项
		 					    $('#userBillingDatagrid').jqxGrid('clearselection');
			 					// 关闭window
			 					$("#billingMgtWindow").jqxWindow('close');
			 					
			 			    }
			 		 });
	    		}
	    	});
	    	
	    };
	    
	 // 初始化用户datagrid的数据源
	    this.initUserBillingDatagrid = function(){
	    	$("#userBillingDatagrid").jqxGrid({
	              width: "99%",
				  theme:"metro",
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 5, 
	              autoheight: true,
	              rowsheight: 30,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '账单时间', datafield: 'billName'},
	                  { text: '总费用(元)', datafield: 'money'},
	                  { text: '已支付金额(元)', datafield: 'paymentAmount'},
	                  { text: '状态', datafield: 'statusName'},
	                  { text: '操作', width:80,  datafield: '',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
 			        	  return "<div class='customButton' style='margin-top:4px;margin-left:10px;' onclick='popUserBillingDetailsWindow("+row+")'>账单明细</div>";
 			             }
 			          }
	              ],
	              showtoolbar: false
	          });
	    	// 控制按钮是否可用
	    	  $("#userBillingDatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#userBillingDatagrid').jqxGrid('getrowdata', index);
	    		  
	    		  if(data.status == "02"){
		   			 $("#search-user-billing-button").hide();
	    		  }else{
	    			 $("#search-user-billing-button").show();
	    		  }
	          });
	    	  
	    	
	    	$("#userCurrentBillingDatagrid").jqxGrid({
	              width: "99%",
				  theme:"metro",
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 5, 
	              autoheight: true,
	              rowsheight: 30,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '账单时间', datafield: 'billName'},
	                  { text: '总费用(元)', datafield: 'money'},
	                  { text: '操作', width:80,  datafield: '',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
			        	  return "<div class='customButton' style='margin-top:4px;margin-left:10px;' onclick='popUserCurrentBillingDetailsWindow()'>账单明细</div>";
			             }
			          }
	              ],
	              showtoolbar: false
	          });
 	    };
	 
	    // 初始化用户datagrid的数据源
	    this.initBillingDatagrid = function(){
	          $("#billingAccountDatagrid").jqxGrid({
	              width: "100%",
				  theme:"metro",
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              rowsheight: 30,
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"singlerow",
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '企业名称', datafield: 'name'},
	                  { text: '余额(元)', datafield: 'balance'},
	                  { text: '结算截止时间', datafield: 'billEndTime'},
	                  { text: '企业描述', datafield: 'description'},
	                  { text: '状态', datafield: 'statusName'},
	                  { text: '操作', width:150,  datafield: '',cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
 			        	  return "<div class='customButton' style='margin-top:4px;margin-left:10px;float:left' onclick='popTradingBillingWindow("+row+")'>交易明细</div><div class='customButton' style='margin-top:4px;margin-left:10px;float:left' onclick='popUserBillingWindow("+row+")'>账单</div>";
 			             }
 			          }
	              ],
	              showtoolbar: false
	          });
	         
 	    };
	    
	    this.initPopWindow = function(){
	    	 // 账单window
			 $("#userBillingWindow").jqxWindow({
	                width: 800, 
	                height:380,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#userBillingWindowCancel"), 
	                theme: currentTheme,
	                modalOpacity: 0.3,
	                initContent:function(){
	                	$("#userBillingWindowCancel").jqxButton({ width: '50',height:"25",theme:"metro"});
	               }
	         });
	    	
	    	// 结算window
			$("#billingBalanceWindow").jqxWindow({
	                width: 320, 
	                height:175,
	                resizable: false,  
	                isModal: true, 
	                autoOpen: false, 
	                cancelButton: $("#billingBalanceCancel"), 
	                theme: currentTheme,
	                modalOpacity: 0.3,
	                initContent:function(){
	                	// 初始化新增用户页面组件
	        	        $("#billingBalanceSave").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	        $("#billingBalanceCancel").jqxButton({ width: '50',height:"25",theme:"metro"});
	        	        
//	        	        $("#billing-balance-time").jqxDateTimeInput({width: '180px', culture: 'zh-CN', formatString: 'yyyy-MM', height: 22, allowNullDate: true,value:null,theme:currentTheme});
	        	       
	        	        var code = new codeModel({width:180,autoDropDownHeight:true,disabled:true});
	        	        
		        	    code.getCommonCode("billing-balance-type","BILLING_TYPE_YM");
//		        	    var code2 = new codeModel({width:180,autoDropDownHeight:false,dropDownWidth:180,dropDownHeight: 180});
//		        	    code2.getCustomCode("billing-balance-tenant","/mgtObj","name","mgtObjSid",false,"POST",{});
		        	    
		        	   
		       			
	                }
	         });
			
			// 扣款window
			$("#billingMgtWindow").jqxWindow({
                width: 400, 
                height:130,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#billingMgtCancel"), 
                theme: currentTheme,
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#billingMgtSave").jqxButton({ width: '50',height:"25",theme:"metro"});
        	        $("#billingMgtCancel").jqxButton({ width: '50',height:"25",theme:"metro"});
        	        $("#billing-pay-money").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
                }
           });
			
			// 查看账单详细表
			$("#seeBillingDetailsWindow").jqxWindow({
                width: 900, 
                height:350,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#billDetailsCancel"), 
                theme: currentTheme,
                modalOpacity: 0.3,
                initContent:function(){
                	// 初始化新增用户页面组件
        	        $("#billDetailsCancel").jqxButton({ width: '50',height:"25",theme:"metro"});
        	        // 初始化datagrid
        	        $("#billingDetails").jqxGrid({
		   	              width: "100%",
		   	              height:270,
		   				  theme:currentTheme,
		   	              columnsresize: true,
		   	              autoheight: false,
		   	              autowidth: false,
		   	              sortable: true,
		   	              selectionmode:"singlerow",
		   	              localization: gridLocalizationObj,
		   	              columns: [
		   	                  { text: '服务实例', datafield: 'serviceInstanceName'},
		   	                  { text: '服务类型', datafield: 'serviceName'},
		   	                  { text: '所有者', datafield: 'ownerId',width:70},
		   	                  { text: '计费类型', datafield: 'billingTypeName',width:70},
		   	                  { text: '计费开始时间', datafield: 'billingStartTime',width:90},
		   	                  { text: '计费结束时间', datafield: 'billingEndTime',width:90},
		   	                  { text: '时长(天)', datafield: 'longUse',width:60},
		   	                  { text: '配置项值', datafield: 'usageVolume',width:100},
		   	                  { text: '费用(元)', datafield: 'money',width:60}
		   	                 
		   	              ],
		   	              showtoolbar: false
	   	          });
	       			
                }
          });
	  };
  };
  
  // 弹出用户账单详情
  function popUserBillingWindow(row){
	  
	  $('#billingTabs').jqxTabs({position: 'top',width:"100%",height:"100%",theme:"metro",selectionTracker:"checked"});
	  
	  setCurrentBillingDatagridValue();
	  var userBill = new billingMgtModel();
  	  var data = $('#billingAccountDatagrid').jqxGrid('getrowdata', row);
	  // 获取到当前选中账户的SID
	  userBill.searchUserBillingInfo(data.mgtObjSid);
	  // 账户余额
	  $("#userBalance").html(data.balance);
	  
	  var windowW = $(window).width(); 
      var windowH = $(window).height(); 
      $("#userBillingWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-380)/2 } });
      $("#userBillingWindow").jqxWindow('open');
  }
  
  // 弹出当前月账单明细
  function popUserCurrentBillingDetailsWindow(){
	  var sourcedatagrid ={
	      datatype: "json",
	      localdata: userCurrentBillingData
      };
      var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
      $("#billingDetails").jqxGrid({source: dataAdapter});
	  var windowW = $(window).width(); 
      var windowH = $(window).height(); 
      $("#seeBillingDetailsWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-400)/2 } });
      $("#seeBillingDetailsWindow").jqxWindow('open');
  }
  
  // 弹出账单详情
  function popUserBillingDetailsWindow(){
	  var rowindex = $('#userBillingDatagrid').jqxGrid('getselectedrowindex');
  	  if(rowindex >= 0){
  		  var data = $('#userBillingDatagrid').jqxGrid('getrowdata', rowindex);
  		 
  		  // 给账单明细列表赋值
	  	  Core.AjaxRequest({
	 			url : ws_url + "/rest/billing/findBillDetails/"+data.billSid+"/"+data.billName+"",
	 			type:'get',
	 			async:false,
	 			callback : function (data) {
	 				var sourcedatagrid ={
				              datatype: "json",
				              localdata: data
		 			    };
		 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		 			    $("#billingDetails").jqxGrid({source: dataAdapter});
	 			}
	 	  });
  		  
  		  var windowW = $(window).width(); 
  	      var windowH = $(window).height(); 
  	      $("#seeBillingDetailsWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-400)/2 } });
  	      $("#seeBillingDetailsWindow").jqxWindow('open');
  	  }
  }
  
  // 弹出账户扣款window
  function popBillingMgtWindow(){
	  var rowindex = $('#userBillingDatagrid').jqxGrid('getselectedrowindex');
  	  if(rowindex >= 0){
  		  var index = $('#billingAccountDatagrid').jqxGrid('getselectedrowindex');
  		  var data = $('#billingAccountDatagrid').jqxGrid('getrowdata', index);
  		  var userBilldata = $('#userBillingDatagrid').jqxGrid('getrowdata', rowindex);
  		  
  		  $("#deduct-money-mgtObjSid").val(data.mgtObjSid);
  		  $("#deduct-money-billSid").val(userBilldata.billSid);
  		  // 获取应付金额
//	  		  var payment = userBilldata.paymentAmount == null? "0":userBilldata.paymentAmount;
//	  		  var distance = (parseFloat(userBilldata.money)-parseFloat(payment)).toFixed(2);
  		  $("#payableAmount").html(userBilldata.money+"元");
  		  var windowW = $(window).width(); 
  	      var windowH = $(window).height(); 
  	      $("#billingMgtWindow").jqxWindow({ position: { x: (windowW-400)/2, y: (windowH-130)/2 } });
  	      $("#billingMgtWindow").jqxWindow('open');
  		  
  		  
  	  }else{
  		Core.alert({
			title:"提示",
			message:"请选择一条账单记录！"
  		});
  	  }
  }
  
  // 弹出结算window
  function popBillingBalanceWindow(){
	  var rowindex = $('#billingAccountDatagrid').jqxGrid('getselectedrowindex');
	  var data = $('#billingAccountDatagrid').jqxGrid('getrowdata', rowindex);
	  if(undefined==data){
		  Core.confirm({
			  title:"提示",
			  message:"请选择一条账单！",
		  }); 
	  }else{
		  initDateTime("billing-balance-year-time","billing-balance-month-time");
		  $("#billing-balance-tenant-sid").val(data.mgtObjSid);
		  $("#billing-balance-tenant").val(data.name);
		  var windowW = $(window).width(); 
	  	  var windowH = $(window).height(); 
	  	  $("#billingBalanceWindow").jqxWindow({ position: { x: (windowW-320)/2, y: (windowH-175)/2 } });
	  	  $("#billingBalanceWindow").jqxWindow('open'); 
	  }
		 
	  
  }
  
  // 弹出租户交易明细
  function popTradingBillingWindow(row){
	  
	  var data = $('#billingAccountDatagrid').jqxGrid('getrowdata', row);
	  var billingRecord = new billingRecordModel();
	  billingRecord.searchBillingRecordInfo(data.accountSid);
	  
	  // 存储accountSid
	  $("#billing-record-accountSid").val(data.accountSid);
	  
	  var windowW = $(window).width(); 
  	  var windowH = $(window).height(); 
  	  $("#billingBalanceDetailWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-330)/2 } });
  	  $("#billingBalanceDetailWindow").jqxWindow('open');
  }
  
  // 确认缴费
  function submitPaymentMoney(){
	  $('#billingMgtForm').jqxValidator('validate');
  }
  
  // 结算
  function submitBillingbalance(){
	  $('#billingBalanceForm').jqxValidator('validate');
  }
  
  // 查询
  function billingAccountSearch(){
	  Core.AjaxRequest({
			url : ws_url + "/rest/mgtObj/findBillAccount",
			type:'post',
			params:{
				nameLike : $("#search-billing-tenant").val()
			},
			async:false,
			callback : function (data) {
				var sourcedatagrid ={
		              datatype: "json",
		              localdata: data
			    };
			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
			    $("#billingAccountDatagrid").jqxGrid({source: dataAdapter});
			}
		 });
  }
  
  /**
   *  初始化年、月
   *  年:当前年3年以内的
   */
  function initDateTime(yid,mid){
	  var date = new Date();
	  var year = date.getFullYear();
	  var month = date.getMonth();
	  var yearData = [];
	  var monthData = [];
	  for(var i=year;i>year-3;i--){
		  var obj = new Object();
		  obj.name = i;
		  obj.value = i;
		  yearData.push(obj);
	  }
	  for(var j=month+1;j>0;j--){
		  var obj1 = new Object();
		  obj1.name = j;
		  obj1.value = j;
		  monthData.push(obj1);
	  }
	  var ysource ={
          datatype: "json",
          datafields: [
              { name: 'name' },
              { name: 'value' }
          ],
          localData: yearData
      };
	  var msource ={
          datatype: "json",
          datafields: [
           { name: 'name' },
           { name: 'value' }
          ],
          localData: monthData
      };
	 
      var ydataAdapter = new $.jqx.dataAdapter(ysource);
      $("#"+yid+"").jqxDropDownList({
          selectedIndex: 0, 
          source: ydataAdapter, 
          displayMember: "name", 
          valueMember: "value", 
          dropDownHeight: 100,
          theme:"metro",
          width: 75, 
          height: 25
      });
      var mdataAdapter = new $.jqx.dataAdapter(msource);
      $("#"+mid+"").jqxDropDownList({
          selectedIndex: 0, 
          source: mdataAdapter, 
          displayMember: "name", 
          valueMember: "value", 
          dropDownHeight: 150,
          theme:"metro",
          width: 70, 
          height: 25
      });
	  
  }
  
  function setCurrentBillingDatagridValue(){
	  	var rowindex = $('#billingAccountDatagrid').jqxGrid('getselectedrowindex');
	  	  if(rowindex >= 0){
	  		  var data = $('#billingAccountDatagrid').jqxGrid('getrowdata', rowindex);
	  		  // 给账单明细列表赋值
		  	  Core.AjaxRequest({
		 			url : ws_url + "/rest/billing/current/billingbalance/"+data.mgtObjSid+"",
		 			type:'get',
		 			async:false,
		 			callback : function (data) {
		 			 	  userCurrentBillingData = data.billDetailList;
		 			 	  var sourcedatagrid ={
		 				      datatype: "json",
		 				      localdata: data.billList
		 			      };
		 			      var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		 			      $("#userCurrentBillingDatagrid").jqxGrid({source: dataAdapter});
		 			 
		 			}
		 	  });
	  	  }
  }
  