<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   <html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=8" />
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
        <script type="text/javascript" src="${ctx}/js/common/validate-custom.js"></script>
		<script type="text/javascript" src="${ctx}/js/billing/billing-account-mgt.js"></script>
		<style type="text/css">
			table{
				font-size: 12px;
				font-family: '微软雅黑', 'Microsoft Yahei', 'segoe ui', arial, sans-serif;
				color: #767676;
			}
		</style>
    </head>
  <body>
   <div style="width:100%;height:30px;padding:5px 0px 5px 0px;">
           <table  border="0" height="100%" cellspacing="0" cellpadding="2">
           		<tr>
           			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;账户：</td>
           			<td><input type="text" id="search-account-name">&nbsp;&nbsp;</td>
           			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;账号：</td>
           			<td><input type="text" id="search-user-account-name">&nbsp;&nbsp;</td>
           			<td align="right" nowrap="nowrap">&nbsp;&nbsp;账户类型：</td>
           			<td>
                        <div id="search-account-type"></div>
           			</td>
           			<td align="right">&nbsp;&nbsp;账户级别：</td>
           			<td>
                        <div id="search-account-level-sid"></div>
           			</td>
                    <td align="right">&nbsp;&nbsp;状态：</td>
                    <td>
                        <div id="search-status"></div>
                    </td>
           			<td><a data-bind="click: searchAccount" id="searchBtn"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:99%;height:90%;margin-left:0.5%;overflow-y:auto;"> 
     	<div id='jqxgridAccount' style="width:100%;height:450px;"></div>
    </div>

   <!-- 添加账户弹窗 -->
   <div id="winAddAccount">
       <div>添加账户</div>
       <div id="winFormAddAccount" style="overflow: auto;">
           <table border="0" width="100%" cellspacing="5" cellpadding="0">
               <tr>
                   <td align="right"><font style="color:red">*</font>账户名:</td>
                   <td align="left">
                       <input type="text" id="add-account-name" />
                   </td>
                   <td align="right"><font style="color:red">*</font>账户类型:</td>
                   <td align="left"><div id="add-account-type"></div></td>
               </tr>
               <tr>
                   <td align="right"><font style="color:red">*</font>状态:</td>
                   <td align="left">
                       <div id="add-status"></div>
                   </td>
                   <td align="right"><font style="color:red">*</font>账户级别:</td>
                   <td align="left"><div id="add-account-level-sid"></div></td>
               </tr>
               <tr>
                   <td align="right"><font style="color:red">*</font>余额(RMB):</td>
                   <td align="left"><input type="text" id="add-balance" /></td>
                   <td align="right">赠送余额(RMB):</td>
                   <td align="left"><input type="text" id="add-gift-balance" /></td>
               </tr>
               <tr>
                   <td align="right">赠送空间(G):</td>
                   <td align="left"><input type="text" id="add-default-space" /></td>
                   <td align="right">购买空间(G):</td>
                   <td align="left"><input type="text" id="add-order-space"/></td>
               </tr>
               <tr>
                   <td align="right" colspan="4">
                       <input style="margin-right: 5px;" type="button" id='saveAddAccountBtn' data-bind='click: addAccountSubmit' value="保存" />
                       <input id="cancelAddAccountBtn" type="button" value="取消" />
                   </td>
               </tr>
           </table>
       </div>
   </div>

   <!-- 编辑账户弹窗 -->
   <div id="winEditAccount">
        <div>编辑账户</div>
        <div id="winFormEditAccount" style="overflow: auto;">
            <input type="hidden" id="edit-account-sid"/>
            <table border="0" width="100%" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right"><font style="color:red">*</font>账户名:</td>
                    <td align="left">
                        <input type="text" id="edit-account-name" />
                    </td>
                    <td align="right">账户类型:</td>
                    <td align="left"><div id="edit-account-type"></div></td>
                </tr>
                <tr>
                    <td align="right"><font style="color:red">*</font>状态:</td>
                    <td align="left"><div id="edit-status"></div></td>
                    <td align="right"><font style="color:red">*</font>账户级别:</td>
                    <td align="left"><div id="edit-account-level-sid"></div></td>
                </tr>
                <tr>
                    <td align="right"><font style="color:red">*</font>余额(RMB):</td>
                    <td align="left"><input type="text" id="edit-balance" /></td>
                    <td align="right">赠送余额(RMB):</td>
                    <td align="left"><input type="text" id="edit-gift-balance" /></td>
                </tr>
                <tr>
                    <td align="right">赠送空间(G):</td>
                    <td align="left"><input type="text" id="edit-default-space" /></td>
                    <td align="right">购买空间(G):</td>
                    <td align="left"><input type="text" id="edit-order-space"/></td>
                </tr>
                <tr>
                    <td align="right">可用积分:</td>
                    <td align="left" colspan="3"><input type="text" id="edit-usable-credit" /></td>
                </tr>
                <tr>
                    <td align="right" colspan="4">
                    <input style="margin-right: 5px;" type="button" id='saveEditAccountBtn' data-bind='click: editAccountSubmit' value="保存" />
                    <input id="cancelEditAccountBtn" type="button" value="取消" /></td>
                </tr>
            </table>
        </div>
   </div>
  </body>     
</html>