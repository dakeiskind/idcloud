<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;服务地址：</td>
            			<td><input type="text" id="search-service-address" />&nbsp;&nbsp;</td>
            			<td align="right">&nbsp;&nbsp;管理状态：</td>
            			<td>
            				<div id="search-exchange-mgt-status"></div>
            			</td>
            			<td align="right">&nbsp;&nbsp;使用状态：</td>
            			<td>
            				<div id="search-exchange-usage-status"></div>
            			</td>
            			<td><input type="button" value="查询" data-bind='click:searchExchangeConfigMgt' id='search-exchange-button' /></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='exchangeConfigMgtdatagrid' style="width:100%;height:450px;"></div> 
    </div>  
    
	<div id="addExchangeWindow">
	            <div>新增Exchange</div>
	            <div id="addExchangeForm" style="overflow: hidden;">
	                <table border="0" width="100%" cellspacing="5" cellpadding="0">
	                    <tr>
	                        <td align="right"><font style="color:red">*</font>服务地址:</td>
	                        <td align="left"><input type="text" data-name="serviceAddress" id="add-serviceAddress"/></td>
	                        <td align="right"><font style="color:red">*</font>最大分配租户数量:</td>
	                        <td align="left"><input type="text" data-name="maxTenantCount" id="add-maxTenantCount"/></td>
	                    </tr>
	                    <tr>
	                        <td align="right">最大邮箱存储容量:</td>
	                        <td align="left"><input type="text" data-name="maxStorageCapacity" id="add-maxStorageCapacity"/></td>
	                    </tr>
	                    <tr>
	                        <td align="right" colspan="4" height="40px">
	                        <input style="margin-right: 5px;" data-bind='click:addExchangeSubmit' type="button" id="addExchangeSave" value="保存" />
	                        <input id="addExchangeCancel" type="button" value="取消" /></td>
	                    </tr>
	                </table>
	            </div>
       </div>  
       
       <div id="editExchangeWindow">
	            <div>编辑Exchange</div>
	            <div id="editExchangeForm" style="overflow: hidden;">
	            	<input type="hidden" data-name="resSid" id="resSidExchange"/>
	                <table border="0" width="100%" cellspacing="5" cellpadding="0">
	                    <tr>
	                        <td align="right"><font style="color:red">*</font>服务地址:</td>
	                        <td align="left"><input type="text" data-name="serviceAddress" id="edit-serviceAddress"/></td>
	                        <td align="right"><font style="color:red">*</font>最大分配租户数量:</td>
	                        <td align="left"><input type="text" data-name="maxTenantCount" id="edit-maxTenantCount"/></td>
	                    </tr>
	                    <tr>
	                        <td align="right">最大邮箱存储容量:</td>
	                        <td align="left"><input type="text" data-name="maxStorageCapacity" id="edit-maxStorageCapacity"/></td>
	                    </tr>
	                    <tr>
	                        <td align="right" colspan="4" height="40px">
	                        <input style="margin-right: 5px;" data-bind='click:editExchangeSubmit' type="button" id="editExchangeSave" value="保存" />
	                        <input id="editExchangeCancel" type="button" value="取消" /></td>
	                    </tr>
	                </table>
	            </div>
       </div>  
       
      <script type="text/javascript">
      		function initExchangeConfigJs(){
       		 // 初始化sys-index页面model
   			 var exchange = new ExchangeConfigModel();
       		 // 初始化页面组件
   			 exchange.initInput();
       		 // 初始化组件验证规则
   			 exchange.initValidator();
       		 // 初始化弹出框
   			 exchange.initPopWindow();
       		 // 初始化datagrid
   			 exchange.initExchangeDatagrid();
       		 // 为datagrid赋值
   			 exchange.searchExchangeConfigInfo();
   	
   			 return exchange;
      		}
      </script>