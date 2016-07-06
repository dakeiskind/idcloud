<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="billingBalanceDetailWindow">
         <div>交易明细</div>
         <div id="billingBalanceDetailForm" style="overflow: hidden;">
         		<div style="width:99.2%;margin:5px;height:35px;"> 
         			<input type="hidden" data-name="accountSid" id="billing-record-accountSid"/>
				 	<table height="30px;" style="font-size:12px;" border="0" cellpadding="3" cellspacing="3"> 
					  <tr>
						<td>流水号:</td>
						<td>
							<input type="text" id="search-billingRecord-number"  />  
						</td>
						<td>创建时间:</td>
						<td>
							<div id="search-billingRecord-dateFrom" ></div>
						</td>
						<td>	
							<span>至</span>
						</td>
						<td>	
							<div id="search-billingRecord-dateTo" ></div>
						</td>
						<td>
							<button id="search-billingRecord-button" style="width:50px" type="submit" onclick="searchBillingRecord()">查询</button>
						</td>
					  </tr>
					</table>
				</div>
				<div  style="width:99%;height:285px;margin-left:0.5%;font-size:14px;">
						<div id="billingRecordGrid" style="width:100%;height:99%;font-size:14px;"></div>
				</div>
				<div style="width:100%;padding-top:25px;text-align:right;">
		              <input id="billingRecordCancel" type="button" value="取消" />&nbsp;
                </div>
				
         </div>
</div> 

<script type="text/javascript">
   var billingRecord = new billingRecordModel();
   billingRecord.initInput();
   billingRecord.initBillingRecordDatagrid();
   billingRecord.initPopWindow();
   billingRecord.searchBillingRecordInfo();
</script>
	