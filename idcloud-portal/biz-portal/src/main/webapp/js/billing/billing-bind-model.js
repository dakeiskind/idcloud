var sysBindModel = function(billingMgtModel,billingPlanModel){
	var me=this;
      /** 查询资费 */
      this.billingSearch = function(){
    	  billingMgtModel.searchObj.tenantId = $("#search-billing-tenant").val()=="全部"?"":$("#search-billing-tenant").val(); 
    	  billingMgtModel.searchObj.status = $("#billing-status").val()=="全部"?"":$("#billing-status").val();
    	  billingMgtModel.searchBillignInfo();
      };
      
/*************************资费计划绑定事件********************************/      
    
	   /** 资费计划查询 */
	   this.billingPlanSearch = function(){
		   billingPlanModel.searchObj.billingPlanName = $("#search-billing-plan-name").val();
		   billingPlanModel.searchObj.billingPlanType = $("#search-billing-plan-type").val()=="全部"?"":$("#search-billing-plan-type").val();
		   billingPlanModel.searchObj.planStatus = $("#search-billing-plan-status").val()=="全部"?"":$("#search-billing-plan-status").val(); 
		   billingPlanModel.searchBillingPlanInfo();
	   };
	 
	   /** 弹出资费计划新增window*/
		  this.addBillingPlanItem = function () {
			  	// 初始化用户新增页面
			    $("#add-plan-name").val("");
		        $("#add-plan-scope").val("");
				
				// 初始化新增window位置
		    	var windowW = $(window).width(); 
		    	var windowH = $(window).height(); 
		    	$("#addBillingPlanWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-260)/2 } });
		    	$("#addBillingPlanWindow").jqxWindow('open');
		    };
		    /** 提交新增资费计划的信息 */
		    this.addBillingPlanSubmit = function(){
		    	// 判断是否通过了验证
		    	$('#addBillingPlanForm').jqxValidator('validate');
		    };
		    
		    /** 弹出资费计划编辑window */
		    this.editBillingPlanItem = function () {
		    	var rowindex = $('#billingPlanGrid').jqxGrid('getselectedrowindex');
		    	if(rowindex >= 0){
		    		    var data = $('#billingPlanGrid').jqxGrid('getrowdata', rowindex);
		    		    $("#billingPlanSid").val(data.billingPlanSid);
		    		    // 将常用的字段可以使用这个方法设置数据
		    		    billingPlanModel.setEditBillingPlanForm(data);
		    		   
		    		    var windowW = $(window).width(); 
		    	    	var windowH = $(window).height(); 
		    	    	$("#editBillingPlanWindow").jqxWindow({ position: { x: (windowW-500)/2, y: (windowH-260)/2 } });
		    	    	$("#editBillingPlanWindow").jqxWindow('open');
		    	}
		    };
		    /** 提交编辑用户信息 */
		    this.editBillingPlanSubmit = function(){
		    	// 判断是否通过了验证
		    	$('#editBillingPlanForm').jqxValidator('validate');
		    };
		    
		    /** 删除用户 */
		    this.removeBillingPlanItem = function () {
		    	var rowindex = $('#billingPlanGrid').jqxGrid('getselectedrowindex');
		    	if(rowindex >= 0){
		    		var data = $('#billingPlanGrid').jqxGrid('getrowdata', rowindex);
				    	Core.confirm({
							title:"提示",
							message:"确定要删除该资费计划吗？",
							confirmCallback:function(){
								Core.AjaxRequest({
					 				url : ws_url + "/rest/billing/deleteBillingPlan/"+data.billingPlanSid,
					 				type:"get",
					 				callback : function (data) {
					 					me.billingPlanSearch();
					 			    },
					 			    failure:function(data){
					 			    }
					 			});
							}
					});
		    	}
	      };
	      
	      // 弹出管理计费定价window
	      this.mgtBillingPrice = function(){
	    	  var rowindex = $('#billingPlanGrid').jqxGrid('getselectedrowindex');
		    	if(rowindex >= 0){
		    		    var data = $('#billingPlanGrid').jqxGrid('getrowdata', rowindex);
		    		    $("#mgtBillingPriceSid").val(data.billingPlanSid);
		    		    
		    		    // 给资费计划信息赋值
		    		    $("#billing-plan-name").html(data.billingPlanName);
		    		    $("#billing-plan-type").html(data.billingPlanTypeName);
		    		    $("#service-name").html(data.serviceName);
		    		    $("#plan-status").html(data.planStatusName);
		    		    $("#plan-scope").html(data.planScope);
		    		    
		    		    // 给资费定价明细赋值
		    		    Core.AjaxRequest({
		    	 			url : ws_url + "/rest/billing/getBillingBytype/Year/"+data.billingPlanSid+"",
		    	 			type:'get',
		    	 			async:false,
		    	 			callback : function (data) {
		    	 				 var sourcedatagrid ={
		    	 				          datatype: "json",
		    	 				          localdata: data
		    	 				   };
		    	 				   var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
		    	 				   $("#billingPriceDetails").jqxGrid({source: dataAdapter});
		    	 			}
		    	 		 });
		    		   
		    		    var windowW = $(window).width(); 
		    	    	var windowH = $(window).height(); 
		    	    	$("#mgtBillingPriceWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-385)/2 } });
		    	    	$("#mgtBillingPriceWindow").jqxWindow('open');
		    	}
	    	  
	      };
	      
	      // 弹出结算窗口
	      this.popBillingBalanceWindow = function(){
	    	  var windowW = $(window).width(); 
  	    	  var windowH = $(window).height(); 
  	    	  $("#billingBalanceWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-170)/2 } });
  	    	  $("#billingBalanceWindow").jqxWindow('open');
	      };
	      
	      this.submitBillingbalance = function(){
	    	  $('#billingBalanceForm').jqxValidator('validate');
	      };
	      
	      // 弹出账单信息Window
	      this.popBillingMgtDetailsWindow = function(){
	    	 
	    	  var rowindex = $('#billingdatagrid').jqxGrid('getselectedrowindex');
		  	  if(rowindex >= 0){
		  		  var data = $('#billingdatagrid').jqxGrid('getrowdata', rowindex);
		  		 // 给账单基本信息赋值
		  		  $("#billing-tenant-name").html(data.tenantName);
		  		  $("#billing-create-time").html(data.billCreateTime);
		  		  $("#billing-payoff-time").html(data.billPayoffTime);
		  		  
		  		  $("#money").html(data.money);
		  		  $("#paymentMoney").html(data.paymentMoney);
		  		  $("#payment_time").html(data.paymentTime);
		  		  $("#statusName").html(data.statusName);
		  		  
		  		  // 给账单明细列表赋值
			  	  Core.AjaxRequest({
			 			url : ws_url + "/rest/billing/findBillDetails/"+data.billSid+"",
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
		  	      $("#seeBillingDetailsWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-400)/2 } });
		  	      $("#seeBillingDetailsWindow").jqxWindow('open');
		  	  }
	    	  
	      };
	      
	      this.popBillingMgtWindow = function(){
	    	  var rowindex = $('#billingdatagrid').jqxGrid('getselectedrowindex');
		  	  if(rowindex >= 0){
		  		  var data = $('#billingdatagrid').jqxGrid('getrowdata', rowindex);
		  		  
		  		  $("#billSid").val(data.billSid);
		  		  var windowW = $(window).width(); 
		  	      var windowH = $(window).height(); 
		  	      $("#billingMgtWindow").jqxWindow({ position: { x: (windowW-450)/2, y: (windowH-100)/2 } });
		  	      $("#billingMgtWindow").jqxWindow('open');
		  	  
		  	  }
	      };
	      
	      this.submitPaymentMoney = function(){
	    	  $('#billingMgtForm').jqxValidator('validate');
	      };
};
  
  
  