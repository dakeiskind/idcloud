<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/billing/billing-mgt-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/billing/billing-record-model.js"></script>
		<title></title>
		<style type="text/css">
			
		</style>
</head>
<body  class='default'>
		<div style="width: 100%; height: 30px; padding: 10px 0px 10px 0px;">
			<table border="0" height="100%" cellspacing="0" cellpadding="2">
				<tr>
					<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;账户名称：</td>
					<td>
						<input type="text" id="search-billing-tenant" />
					</td>
					<td>
					  	<a onclick="billingAccountSearch()" id="search-billing-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a>
					  	&nbsp;&nbsp;&nbsp;&nbsp;
					  	<a onclick="popBillingBalanceWindow()" id="billing-button"><i class='icons-blue icon-pencil-alt'></i>结算&nbsp;</a>
					</td>
				</tr>
			</table>
		</div>
		<div style="width: 98%; height: 80%; margin-left: 1%;">
			<div id='billingAccountDatagrid' style="width: 100%; height: 450px;"></div>
		</div>

		<!-- 结算window -->
		<div id="billingBalanceWindow">
            <div>确认结算</div>
            <div id="billingBalanceForm" style="overflow: hidden;">
            	<input type="hidden" id="billing-balance-tenant-sid"></input>
            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
            		<tr>
            			<td align="right" width="80px">结算类型：</td>
            			<td align="left">
            				<div data-name="" id="billing-balance-type"></div>
            			</td>
            		</tr>
<!--             		<tr>
            			<td align="right">租户名称：</td>
            			<td align="left">
            				<div id="billing-balance-tenant"></div>
            			</td>
            		</tr> -->
            		<tr>
						<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;租户名称：</td>
						<td>
							<input type="text" id="billing-balance-tenant"></input>
						</td>
					</tr>
<!--             		<tr> -->
<!--             			<td align="right"><font style="color:red">*</font>结算时间：</td> -->
<!--             			<td align="left"> -->
<!--             				<div id="billing-balance-time"></div> -->
            				
<!--             			</td> -->
<!--             		</tr> -->
					
            		<tr>
            			<td align="right"><font style="color:red">*</font>结算时间：</td>
            			<td align="left">
            				<div style="float:left" id="billing-balance-year-time"></div>
            				<div style="float:left;height:24px;padding:0px 3px 0px 3px;line-height:24px">年</div>
            				<div style="float:left" id="billing-balance-month-time"></div>
            				<div style="float:left;height:24px;padding:0px 3px 0px 3px;line-height:24px">月</div>
            			</td>
            		</tr>
            		<tr>
            			<td align="right" colspan="2" height="40">
            				 <input style="margin-right: 5px;" onclick="submitBillingbalance()" type="button" id="billingBalanceSave" value="结算" />
		              		 <input id="billingBalanceCancel" type="button" value="取消" />
            			</td>
            		</tr>
            	</table>
            </div>
		</div> 
		
		<!-- 用户账单window -->
		<div id="userBillingWindow">
            <div>用户账单</div>
            <div style="overflow: hidden;">
		        <div style="width: 100%;">
					<table border="0" width="500px" height="100%" cellspacing="0" cellpadding="2">
						<tr>
							<td width="80px" align="right" nowrap="nowrap">&nbsp;&nbsp;账户余额：</td>
							<td>
								<span style="color: #ff945c;font-size:18px">￥</span>
								<span id="userBalance" style="color: #ff945c;font-size:30px">0.00</span>
							</td>
							<td align="right">
							  	<a onclick="popBillingMgtWindow()" style="display:none" id="search-user-billing-button"><i class='icons-blue icon-minus'></i>扣款&nbsp;</a>
							</td>
						</tr>
					</table>
				</div>
				<div style="width: 98%; height: 270px; margin-left: 1%;">
					<div id='billingTabs'>
					    <ul>
					     	<li style="margin-left: 15px;">当月账单</li>
					        <li>历史账单</li> 
					    </ul>
					    <div>
					    	<div id='userCurrentBillingDatagrid' style="margin-left:3px;margin-top:3px;"></div>	
					    </div>
					    <div>  
					       <div id='userBillingDatagrid' style="margin-left:3px;margin-top:3px;"></div>
					    </div>
					</div>
				</div>
				 <div style="width:100%;padding-top:8px;text-align:right;">
		              <input id="userBillingWindowCancel" style="margin-right:7px;" type="button" value="取消" />
                </div>
            </div>
        </div>
		
		<!-- 账单明细window -->
		<div id="seeBillingDetailsWindow">
            <div>账单明细列表</div>
            <div id="seeBillingDetailsForm" style="overflow: hidden;">
		       <div style="width:98%;margin-left:1%;margin-top:5px;">
		            <div id='billingDetails'></div>
		       </div>
		        <div style="width:100%;padding-top:8px;text-align:right;">
		              <input id="billDetailsCancel" style="margin-right:6px;" type="button" value="取消" />
                </div>
		        
            </div>
        </div> 
        
        <!-- 账户扣款 -->
		<div id="billingMgtWindow">
            <div>扣款</div>
            <div id="billingMgtForm" style="overflow: hidden;">
                <input type="hidden" data-name="mgtObjSid" id="deduct-money-mgtObjSid"/>
                <input type="hidden" data-name="billSid" id="deduct-money-billSid"/>
            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
            		<tr>
            			<td align="left" colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;应付金额：<span id="payableAmount" style="font-size:16px;color:#ff945c">0.0</span></td>
            		</tr>
            		<tr>
            			<td align="right" width="80px"><font style="color:red">*</font>扣款金额：</td>
            			<td align="left">
            				<input type="text" data-name="paymentAmount" id="billing-pay-money"/>
            			</td>
            			<td align="right" colspan="4" height="40">
            				 <input style="margin-right: 5px;" onclick="submitPaymentMoney()" type="button" id="billingMgtSave" value="确定" />
		              		 <input id="billingMgtCancel" type="button" value="取消" />
            			</td>
            		</tr>
            	</table>
          </div>
	</div> 
	<%@ include file="billing-record-index.jsp"%>
</body>

<script type="text/javascript">
	var userCurrentBillingData = null; 
	// 初始化sys-index页面model
	var billingmgtmodel = new billingMgtModel();
	// 初始化datagrid
	billingmgtmodel.initBillingDatagrid();
	billingmgtmodel.initUserBillingDatagrid();
	// 为datagrid赋值
	billingmgtmodel.searchBillignInfo();
	// Grid 赋值
	billingmgtmodel.initInput();
	// 初始化window
	billingmgtmodel.initPopWindow();
	billingmgtmodel.initValidator();
	
	// 初始化结算时间
    $('#billing-balance-year-time').on('select', function (event){
	       var args = event.args;
	       var monthData = [];
	       if (args) {
		       var item = args.item;
		       var value = item.value;
		       var date = new Date();
		       var year = date.getFullYear();
		       var month = date.getMonth()+1;
		       if(value == year){
		    	   for(var j=month;j>0;j--){
	    			  var obj1 = new Object();
	    			  obj1.name = j;
	    			  obj1.value = j;
	    			  monthData.push(obj1);
	    		   }
		       }else{
		    	   for(var j=12;j>0;j--){
	    			  var obj = new Object();
	    			  obj.name = j;
	    			  obj.value = j;
	    			  monthData.push(obj);
	    		   }
		       }
		       
		       setMonthDropValue(monthData);
	       }                        
 });
	

 // 初始化结算时间
 $('#billingTabs').on('selected', function (event) {
	  	 var pageIndex = event.args.item;
	  	 if(pageIndex == 1){
	  		 var rowindex = $('#userBillingDatagrid').jqxGrid('getselectedrowindex');
	  	  	  if(rowindex >= 0){
	  	  		  var data = $('#userBillingDatagrid').jqxGrid('getrowdata', rowindex);
	  	  		 if(data.status == "02"){
		   			 $("#search-user-billing-button").hide();
		   		  }else{
		   			 $("#search-user-billing-button").show();
		   		  }
	  	  	  }else{
	  	  		 $("#search-user-billing-button").show();
	  	  	  }
         }else{
        	 $("#search-user-billing-button").hide();
         }
  });
	
    
function setMonthDropValue(monthData){
	
		  var msource ={
	          datatype: "json",
	          datafields: [
	           { name: 'name' },
	           { name: 'value' }
	          ],
	          localData: monthData
	      };
	      var mdataAdapter = new $.jqx.dataAdapter(msource);
	      $("#billing-balance-month-time").jqxDropDownList({
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
	
	
	
</script>
</html>