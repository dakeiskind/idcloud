<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   <html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=8" />
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/sys/user-mgt-model.js"></script>
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
           			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;所属项目：</td>
           			<td><input type="text" id="search-owner-mgtobj" />&nbsp;&nbsp;</td>
           			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;用户账号：</td>
           			<td><input type="text" id="search-account" />&nbsp;&nbsp;</td>
           			<td align="right" nowrap="nowrap">&nbsp;&nbsp;所属角色：</td>
           			<td>
           				<div id="search-role"></div>
           			</td>
           			<td align="right">&nbsp;&nbsp;用户状态：</td>
           			<td>
           				<div id="search-status"></div>
           			</td>
           			<td><a  onclick="searchUser()" id="search-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:99%;height:90%;margin-left:0.5%;overflow-y:auto;"> 
     	<div id='userdatagrid' style="width:100%;height:450px;"></div> 
     	<div style="height: 30px;"><p style="margin:0px;margin-top:7px;font-size:14px;color: green;">
	    	※列表中<u style="color: rgb(205, 205, 56);">黄色</u>部分数据为待审核信息。</p>
	    </div>
    </div>   
    
    <div id="addUserWindow">
            <div>新增用户</div>
            <div id="addUserForm" style="overflow: auto;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>用户账号:</td>
                        <td align="left"><input type="text" data-name="account" id="add-account"/></td>
                        <td align="right"><font style="color:red">*</font>密码:</td>
                        <td align="left"><input type="password" data-name="password" id="add-password"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>用户名称:</td>
                        <td align="left" colspan="3"><input type="text" data-name="realName" id="add-name"/></td>
                    </tr>
                    <tr>
                        <td align="right">电话:</td>
                        <td align="left"><input type="text" data-name="mobile" id="add-mobil"/></td>
                        <td align="right"><font style="color:red">*</font>邮箱:</td>
                        <td align="left"><input type="text" data-name="email" id="add-email"/></td>
                    </tr>
                    <tr>
                        <td align="right">用户类型:</td>
                        <td align="left" >
                            <div  data-name="userType" id="add-type"></div>
                        </td>
                        <td align="right">用户状态:</td>
                        <td align="left">
                            <div data-name="status" id="add-status"></div>
                        </td>
                    </tr>
                    <tr height="30">
                        <td align="right" width="100"><font style="color:red">*</font>所属角色:</td>
                        <td align="left" valign="middle" colspan="3" id="add-rolesHolder">
                        </td>
                    </tr>
                   <!--  <tbody id="tenantTrs" style="display: none;">
	                    <tr>
	                    	<td align="right" width="100"><font style="color:red">*</font>关联项目:</td>
	                    	<td align="left" valign="middle" colspan="3">
	                    		<div  data-name="orgSid" id="addUserMgtSid">
	                            	<div id="add-mgtObjList"></div>
	                            </div>
	                    	</td>
	                    </tr>
                    </tbody> -->
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" onclick='submitAddUserInfo()' type="button" id="Save" value="保存" />
                        <input id="Cancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div> 
       
       <div id="editUserWindow">
            <div>编辑用户</div>
            <div id="editUserForm" style="overflow: auto;">
            	<input type="hidden" data-name="userSid" id="userSid"/>
            	<input type="hidden" data-name="orgSid" id="edit-mgtObjSid" />
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>用户账号:</td>
                        <td align="left">
                            <span id="edit-account"></span>
                        </td>
                        <td align="right"><font style="color:red">*</font>用户名称:</td>
                        <td align="left"><input type="text" data-name="realName" id="edit-name"/></td>
                    </tr>
                    <tr>
                        <td align="right">电话:</td>
                        <td align="left"><input type="text" data-name="mobile" id="edit-mobil"/></td>
                        <td align="right"><font style="color:red">*</font>邮箱:</td>
                        <td align="left"><input type="text" data-name="email" id="edit-email"/></td>
                    </tr>
                   <tr id="typeAndStatus">
                        <td align="right">用户类型:</td>
                        <td align="left" >
                            <div  data-name="userType" id="edit-type"></div>
                        </td>
                        <td align="right">用户状态:</td>
                        <td align="left">
                            <div data-name="status" id="edit-status"></div>
                        </td>
                    </tr>
                    <tr height="30" id="rolesTr">
                        <td align="right" width="100"><font style="color:red">*</font>所属角色:</td>
                        <td align="left" valign="middle" colspan="3" id="edit-rolesHolder">
                        </td>
                    </tr>
                    <tbody id="tenantEditTrs" style="display: none;">
	                    <tr>
	                    	<td align="right" width="100"><font style="color:red">*</font>所属项目:</td>
	                    	<td align="left" valign="middle" colspan="3">
	                    		<div id="editUserMgtSid">
	                            	<div id="edit-mgtObjList"></div>
	                            </div>
	                    	</td>
	                    </tr>
                    </tbody>
                    <tr>
                        <td align="right" colspan="4">
                        <input style="margin-right: 5px;" type="button" onclick='submitEditUserInfo()'  id="editSave" value="保存" />
                        <input id="editCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div> 
       
       <div id="changePasswdWindow">
            <div>重置密码</div>
            <div id="changePasswdForm" style="overflow: hidden;">
            	<input type="hidden" data-name="userSids" id="passwdUserSids"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>新密码:</td>
                        <td align="left"><input type="password" id="passwordInput" class="text-input" /></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>确认新密码:</td>
                        <td align="left"><input type="password" id="passwordConfirmInput" class="text-input" /></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="2">
                        	<br />
	                        <input style="margin-right: 5px;" type="button" onclick='passwd_submit()' id="passwdSave" value="保存" />
	                        <input id="passwdCancel" type="button" value="取消" />
                        </td>
                    </tr>
                </table>
            </div>
       </div>

        <%--< !-- 礼品卡绑定弹窗  -->--%>
        <div class="jqxwindow" id="addGiftWindow">
            <div><i class="icons-blue icon-edit"></i>礼品卡分发</div>
            <div id="giftForm" style="overflow: hidden;">
                <input type="hidden" id="edit-user-sid">
                <span><strong>分发对象：<lable type="text" id="edit-user-name" readonly="true"/></strong></span>
                <br>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right">
                            <input type="button" class="button_02" value='分发' id='saveBindingBtn'
                            onclick='giftBindingSubmit()' />
                        </td>
                        <td>
                            <input type="button" class="button_02" value='返回' id='cancelBindingBtn' />
                        </td>
                    </tr>
                    <tr><div id='jqxgridGift'></div></tr>
                </table>
            </div>
        </div>

   <%--< !-- 优惠券绑定弹窗  -->--%>
  <div class="jqxwindow" id="addCouponWindow">
      <div><i class="icons-blue icon-edit"></i>优惠券分发</div>
      <div id="couponForm" style="overflow: hidden">
          <input type="hidden" id="coupon-user-sid">
          <span><strong>分发对象：<lable type="text" id="coupon-user-name" readonly="true"/></strong></span>
          <br>
          <table border="0" width="100%" cellspacing="5" cellpadding="0">
              <tr>
                  <td align="right">
                      <input type="button" class="button_02" value='分发' id='saveBindingCouponBtn' onclick='couponBindingSubmit()' />
                  </td>
                  <td>
                      <input type="button" class="button_02" value='返回' id='cancelBindingCouponBtn' />
                  </td>
              </tr>
              <tr><div id='jqxGridCoupon'></div></tr>
          </table>
      </div>
  </div>

  </body>     
</html>          
       <script type="text/javascript">
	         $(".ownTenant").hide();
			 $(".frontRole").hide();
			 $('#add-type').on('change', function (event){
			     var args = event.args;
			     if (args) {
				     // index represents the item's index.                      
				     var index = args.index;
				     var item = args.item;
				     // get item's label and value.
				     var label = item.label;
				     var value = item.value;
				     if(value == "02"){
				    	 $(".ownTenant").show();
				    	 $(".frontRole").show();
				    	 $(".backRole").hide();
				     }else{
				    	 $(".ownTenant").hide();
				    	 $(".frontRole").hide();
				    	 $(".backRole").show();
				     }
			     } 
			});
			
			 // 编辑用户时
			$(".EditownTenant").hide();
			 $(".EditfrontRole").hide();
			 $('#edit-type').on('change', function (event){    
			     var args = event.args;
			     if (args) {
				     // index represents the item's index.                      
				     var index = args.index;
				     var item = args.item;
				     // get item's label and value.
				     var label = item.label;
				     var value = item.value;
				     if(value == "02"){
				    	 $(".EditownTenant").show();
				    	 $(".EditfrontRole").show();
				    	 $(".EditbackRole").hide();
				     }else{
				    	 $(".EditownTenant").hide();
				    	 $(".EditfrontRole").hide();
				    	 $(".EditbackRole").show();
				     }
			     } 
			}); 
			 
			 // 初始化sys-index页面model
   			 var usermodel = new userModel();
       		 // 初始化页面组件
   			 usermodel.initInput();
       		 // 初始化弹出框
   			 usermodel.initPopWindow();
       		 // 初始化datagrid
   			 usermodel.initUserDatagrid();
             //初始化可用礼品卡grid
             usermodel.initGfitBinding();
             //加载可用优惠券
             usermodel.initCouponBinding();
   			 // 初始化组件验证规则
   			 usermodel.initValidator();
//    			 //加上联动
//    			 usermodel.combineSelect();
//    			 usermodel.addCombineSelect();
   			
       		
       </script>