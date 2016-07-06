<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/billing/billing-plan-model.js"></script>
		
		<title></title>
		<style type="text/css">
			#containerSys{
				width:100%;
				height:100%;
				margin:0px;
				padding:0px;
			}
			.datagridLink{
				color:#0099d7;  
			}
			.datagridLink:hover{
				color:#fff;  
			}
		</style>
</head>
<body  class='default'>
   <div id="containerSys">   
	   <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
	           <table border="0" height="100%" cellspacing="0" cellpadding="2">
	            	<tr>
	            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;资费计划名称：</td>
	            			<td><input type="text" id="search-billing-plan-name" />&nbsp;&nbsp;</td>   
	            			<td align="right" nowrap="nowrap">资费计划类型：</td>
	            			<td>
	            				<div id="search-billing-plan-type"></div>
	            			</td>
	            			<td align="right">&nbsp;&nbsp;状态：</td>
	            			<td>
	            				<div id="search-billing-plan-status"></div>
	            			</td>
	            			<td><a onclick="billingPlanSearch()" id="search-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
	            	</tr>
	            </table>
	    </div>
	    <div style="width:98%;height:80%;margin-left:1%;"> 
	     	<div id='billingPlanGrid' style="width:100%;"></div> 
	    </div>   
    </div>
     <div id="addBillingPlanWindow">
            <div>新增资费计划</div>
            <div id="addBillingPlanForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>资费计划名称:</td>
                        <td align="left"><input type="text" data-name="billingPlanName" id="add-plan-name"/></td>
                        <td align="right">资费计划类型:</td>
                        <td align="left"><div data-name="billingPlanType" id="add-plan-type"></div></td>
                    </tr>
                    <tr>
                        <td align="right">服务名称:</td>
                        <td align="left"><div data-name="serviceSid" id="add-service-name"></div></td>
                        <td align="right">计划状态:</td>
                        <td align="left">
                           <div data-name="planStatus" id="add-plan-status"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">计划范围:</td>
                        <td align="left"><input type="text" data-name="planScope" id="add-plan-scope"/></td>
                    </tr>
					<tr>
						<td align="right" colspan="4">

							<div class="customPanel" style="width:100%;height:250px;margin-top:5px;">
								<div class="title">&nbsp;资费定价明细
									<i class='icons-blue icon-plus-outline'  onclick="popAddSpecWindow()" style="margin-left:560px;cursor:pointer"></i>
									<i class='icons-blue icon-trash' onclick="deleteBillingDetails()" style="margin-left:5px;cursor:pointer"></i>
								</div>
								<div style="width:98%;margin-left:1%;margin-top:5px;margin-right: 1%">
									<div id='billingPalnSpec'></div>
								</div>
							</div>

						</td>

					</tr>
                     <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick='addBillingPlanSubmit()' type="button" id="Save" value="保存" />
                        <input id="Cancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div> 
       
        <div id="editBillingPlanWindow">
            <div>编辑资费计划</div>
            <div id="editBillingPlanForm" style="overflow: hidden;">
            	<input type="hidden" data-name="billingPlanSid" id="billingPlanSid"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>资费计划名称:</td>
                        <td align="left"><input type="text" data-name="billingPlanName" id="edit-plan-name"/></td>
                        <td align="right">资费计划类型:</td>
                        <td align="left"><div data-name="billingPlanType" id="edit-plan-type"></div></td>
                    </tr>
                    <tr>
                        <td align="right">服务名称:</td>
                        <td align="left"><div data-name="serviceSid" id="edit-service-name"></div></td>
                        <td align="right">计划状态:</td>
                        <td align="left">
                           <div data-name="planStatus" id="edit-plan-status"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">计划范围:</td>
                        <td align="left"><input type="text" data-name="planScope" id="edit-plan-scope"/></td>
                    </tr>
                     <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick ='editBillingPlanSubmit()' type="button" id="editSave" value="保存" />
                        <input id="editCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div> 
       
       <div id="mgtBillingPriceWindow">
            <div>管理资费定价</div>
            <div id="mgtBillingPriceForm" style="overflow: hidden;">
            	<input type="hidden" data-name="billingPlanSid" id="mgtBillingPriceSid"/>		        
		        <div class="customPanel" style="width:100%;height:65px;margin-top:5px;">
            	    <div class="title">&nbsp;定价资费信息</div>
		            <div>
		            	<table border="0" width="100%" cellspacing="0" cellpadding="5">
		            		 <tr>
		            		 	<td align="right">定价类型:</td>
		            		 	<td align="left">
		            		 		<div data-name="" id="billing-type"></div>
		            		 	</td>
		            		 	<td align="right">状态:</td>
		            		 	<td align="left">
		            		 		<div data-name="" id="billingStatus"></div>
		            		 	</td>
		            		 </tr>
		            	</table>
		            </div>
		        </div>
		        
		        <div class="customPanel" style="width:100%;height:250px;margin-top:5px;">
            	    <div class="title">&nbsp;资费定价明细
            	    	<i class='icons-blue icon-plus-outline'  onclick="popAddSpecWindow()" style="margin-left:560px;cursor:pointer"></i>
            	    	<i class='icons-blue icon-trash' onclick="deleteBillingDetails()" style="margin-left:5px;cursor:pointer"></i>
            	    </div>
		            <div style="width:98%;margin-left:1%;margin-top:5px;">
		            	<div id='billingPriceDetails'></div>
		            </div>
		        </div>
		        
		        <div style="width:100%;padding-top:10px;text-align:right">
		              <input id="mgtBillingCancel" type="button" value="取消" />
                </div>
		        
            </div>
       </div> 
       
       <div id="addSpecWindow">
            <div>资费服务规格组合添加</div>
            <div id="addSpecForm" style="overflow: hidden;">
            	<div id='addSpecGrid'></div>
		        <div style="width:100%;padding-top:10px;text-align:right">
            		  <input style="margin-right: 5px;" onclick='saveAddSpec()' type="button" id="addSpecSave" value="添加" />
		              <input id="addSpecCancel" type="button" value="取消" />
                </div>
		        
            </div>
       </div>


   <div id="addPriceDetailWindow">
	   <div>资费明细设置</div>
	   <div id="addPriceDetailForm" style="overflow: hidden;">
		   <div class="customPanel" style="width:100%;height:65px;margin-top:5px;">
			   <div class="title">&nbsp;选择定价规格</div>
			   <div>
				   <table border="0" width="100%" cellspacing="0" cellpadding="5">
					   <tr>
						   <td align="right">定价规格项:</td>
						   <td align="left" width="90%" colspan="2">
							   <div id="billed-specs"/>
							   <%--<div data-name="" id="billing-type1"></div>--%>
						   </td>
					   </tr>
				   </table>
			   </div>
		   </div>
		   <div class="customPanel" style="width:100%;height:250px;margin-top:5px;">
			   <div class="title">&nbsp;可定价规格
			   </div>
			   <div style="width:98%;margin-left:1%;margin-top:5px;">
				   <div id='addPriceDetailSpecsGrid'></div>
			   </div>
		   </div>

		   <div style="width:100%;padding-top:10px;text-align:right">
			   <input style="margin-right: 5px;" onclick='saveAddPriceDetail()' type="button" id="addPriceDetailSave" value="添加" />
			   <input id="addPriceDetailCancel" type="button" value="取消" />
		   </div>

	   </div>
   </div>

   <div id="popServicePricingWindow">
            <div>服务定价窗口</div>
            <div style="overflow: hidden;">
                <div style="width:100%;height:auto;margin-top:5px;">
                   <input type="hidden" id="billingPricingSid"/>
                   <input type="hidden" id="configBillingPriceDetails"/>
                   <input type="hidden" id="configBillingPriceDetailsDescription"/>
                   <input type="hidden" id="billDetailsChargeType"/>
            	   <table id="servicePricingTable" border="0" width="100%" cellspacing="5" cellpadding="0">
            	   		
            	   </table>
		        </div>
		        
		        <div style="width:100%;padding-top:10px;text-align:right;">
            		  <input style="margin-right: 5px;" onclick='saveMgtBillingPrice()' type="button" id="serviceBillingSave" value="保存" />
		              <input id="serviceBillingCancel" type="button" value="取消" />
                </div>
		        
            </div>
       </div> 
       
       <div id="popSetChargeTypeWindow">
            <div>设置收费类型</div>
            <div style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="0" cellpadding="5">
            		 <tr>
            		 	<td align="right">收费类型:</td>
            		 	<td align="left">
            		 		<div data-name="" id="charge-type"></div>
            		 	</td>
            		 	<td>
            		 		<input onclick="submitPricingInfo()" style="margin-right: 5px;" type="button" id="setChargeTypeSave" value="保存" />
            		 	</td>
            		 </tr>
            	</table>
            </div>
       </div>
   </body>    
       
       <script type="text/javascript">
       		
  		 // 初始化billing-plan-tab页面model
	 	 var billingplanmodel = new billingPlanModel();
		 // 初始化datagrid
		 billingplanmodel.initBillingPlanDatagrid();
		 billingplanmodel.initBillDetailAndSpecDatagrid();
		 // 为datagrid赋值
		 billingplanmodel.searchBillingPlanInfo();
		 //初始化输入框，下拉框等
		 billingplanmodel.initInput();
		 
		 //初始化弹出窗口
		 billingplanmodel.initPopWindow();
		 //初始化验证
		 billingplanmodel.initValidator();
 		
		 // 资费明细列表编辑时
		  $("#billingPriceDetails").on('cellendedit', function (event) {

			    // 更新billingPricing表
                var data = $('#billingPriceDetails').jqxGrid('getrowdata', event.args.rowindex);
			    var billingPricing = new Object();
			    billingPricing.billingPricingSid = data.billingPricingSid;
			    billingPricing.chargeType = event.args.value.value;
			    
			    Core.AjaxRequest({
		 			url : ws_url + "/rest/billings/update/BillingPrice",
		 			type:'post',
		 			params:billingPricing,
		 			async:false,
		 			callback : function (data) {
		 				// 刷新资费定价明细
		 				refreshBillPricing();

		 			}
				 });

            });
		 
       	 // 资费类型更新的时候
       	   $('#billing-type').on('select', function (event){
       		       var args = event.args;
       		       if (args) {
       			       var item = args.item;
       			       var value = item.value;
       			       var rowindex = $('#billingPlanGrid').jqxGrid('getselectedrowindex');
    		    	   var data = $('#billingPlanGrid').jqxGrid('getrowdata', rowindex);
    		    	   // 1、先要清空资费定价明细datagrid的值
    		    	   var source ={
   	 				          datatype: "json",
   	 				          localdata: null
   	 				   };
    		    	   var dataAdapter = new $.jqx.dataAdapter(source);	
  	 				   $("#billingPriceDetails").jqxGrid({source: dataAdapter});
  	 				   
  	 				   // 2、根据选择的定价类型显示定价详情
	  	 			   Core.AjaxRequest({
	  	 		 			url : ws_url + "/rest/billings/getBillingBytype/"+value+"/"+data.billingPlanSid+"",
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
	  	 			     
	  	 			     Core.AjaxRequest({
	  	 			  			url : ws_url + "/rest/billings/billingPricingDetail/findAll",
	  	 			  			type:'post',
	  	 			  			params:{},
	  	 			  			async:false,
	  	 			  			callback : function (data) {
	  	 			  				var sourcedatagrid ={
	  	 			 			              datatype: "json",
	  	 			 			              localdata: data
	  	 			  			    };
	  	 			  				 var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid, { autoBind: true });
	  	 			  			    detailsItems = dataAdapter.records;
	  	 			  			}
	  	 		  		 });	
       		       }                        
       	   });
       </script>
  </html>