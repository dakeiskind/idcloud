<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 新增用户 -->
<div id="addTenantProUserWindow">
      <div>新增用户</div>
      <div id="addTenantProUserForm" style="overflow: hidden;">
      <input type="hidden" data-name="userMgtObj" id="add-user-pro-projectName" />
          <table border="0" width="100%" cellspacing="5" cellpadding="0">
              <tr>
                  <td align="right"><font style="color:red">＊</font>用户账号:</td>
                  <td align="left">
                  	<input type="text" data-name="account" id="add-user-pro-account"/>
                  </td>
                   <!-- <td align="right"><font style="color:red">*</font>所属项目:</td>
                   <td align="left">
                        <div data-name="userMgtObj" id="add-project-pro-mgtObjSid"></div>
                        <input type="text" data-name="userMgtObj" id="add-project-pro-mgtObjSid"/>
                   </td> -->
              </tr>
              <tr>
              	  <td align="right"><font style="color:red">＊</font>用户名称:</td>
                  <td align="left">
                    <input type="text" data-name="realName" id="add-user-pro-realName" />
                  </td>
                  <td align="right"><font style="color:red">＊</font>密码:</td>
                  <td align="left">
                     <input type="password" data-name="password" id="add-user-pro-password" />
                  </td>
              </tr>
              <tr>
              	  <td align="right"><font style="color:red">＊</font>邮箱:</td>
                  <td align="left">
                      <input type="text" data-name="email" id="add-user-pro-emaile"/>
                  </td>
                  <td align="right">联系电话:</td>
                  <td align="left">
                      <input type="text" data-name="mobile" id="add-user-pro-mobile"/>
                  </td>
              </tr>
              <!-- <tr>
                   <td align="right"><font style="color:red">*</font>所属项目:</td>
                   <td align="left">
                        <div data-name="userMgtObj" id="add-project-pro-mgtObjSid"></div>
                   </td>
              </tr> -->
              <tr>
                  <td align="right" colspan="4">
	                  <input style="margin-right: 5px;margin-top:5px;" onclick="submitTenantProUserInfo()" type="button" id="add-user-pro-button-save" value="保存" />
	                  <input style="margin-top:5px;" id="add-user-pro-button-cancel" type="button" value="取消" />
                  </td>
              </tr>
          </table>
      </div>
 </div>
 
 <!-- 编辑用户 -->
<div id="editTenantProUserWindow">
      <div>编辑用户</div>
      <div id="editTenantProUserForm" style="overflow: hidden;">
      	  <input type="hidden" data-name="userSid" id="edit-user-pro-userSid"/> 
      	  <input type="hidden" data-name="userMgtObj" id="edit-project-pro-mgtObjSid"/> 
          <table border="0" width="100%" cellspacing="5" cellpadding="0">
              <tr>
                  <td align="right"><font style="color:red">＊</font>成员账号:</td>
                  <td align="left">
                  	<input type="text" data-name="account" id="edit-user-pro-account"/>
                  </td>
                   <td align="right"><font style="color:red">＊</font>成员名称:</td>
                  <td align="left">
                    <input type="text" data-name="realName" id="edit-user-pro-realName" />
                  </td>
              </tr>
              <tr>
                  <td align="right">联系电话:</td>
                  <td align="left">
                      <input type="text" data-name="mobile" id="edit-user-pro-mobile"/>
                  </td>
                   <td align="right"><font style="color:red">＊</font>邮箱:</td>
                  <td align="left">
                      <input type="text" data-name="email" id="edit-user-pro-emaile"/>
                  </td>
              </tr>
              <tr>
                  <td align="right">成员状态:</td>
                  <td align="left">
                      <div data-name="status" id="edit-user-pro-status"></div>
                  </td>
                   <!-- <td align="right"><font style="color:red">*</font>所属项目:</td>
                   <td align="left">
                        <div data-name="userMgtObj" id="edit-project-mgtObjSid"></div>
                   </td> -->
              </tr>
              <tr>
                  <td align="right" colspan="4">
	                  <input style="margin-right: 5px;margin-top:5px;" onclick="submitEditTenantUserInfo()" type="button" id="edit-user-pro-button-save" value="保存" />
	                  <input style="margin-top:5px;" id="edit-user-pro-button-cancel" type="button" value="取消" />
                  </td>
              </tr>
          </table>
      </div>
 </div>
 
 <!-- 重置密码 -->
<div id="changePasswordProWindow">
      <div>重置密码</div>
      <div id="changePasswordProForm" style="overflow: hidden;">
      	  <input type="hidden" data-name="userSids" id="change-user-pro-userSid"/> 
          <table border="0" width="100%" cellspacing="5" cellpadding="0">
          	  <tr>
                  <td align="right">用户账户:</td>
                  <td align="left">
                  	<span id="change-user-pro-account"></span>
                  </td>
              </tr>
              <tr>
                  <td align="right"><font style="color:red">＊</font>新密码:</td>
                  <td align="left">
                  	<input type="password" id="change-new-pro-password"/>
                  </td>
              </tr>
              <tr>
                  <td align="right"><font style="color:red">＊</font>确认密码:</td>
                  <td align="left">
                      <input type="password" data-name="newPassword" id="change-confirm-pro-password"/>
                  </td>
              </tr>
              <tr>
                  <td align="right" colspan="2">
	                  <input style="margin-right: 5px;margin-top:5px;" onclick="submitChangeUserProPasswdInfo()" type="button" id="change-passwd-pro-button-save" value="保存" />
	                  <input style="margin-top:5px;" id="change-passwd-pro-button-cancel" type="button" value="取消" />
                  </td>
              </tr>
          </table>
      </div>
 </div>
 
 <script>
 	var userWindow = new tenantProjectWindowModel();
 	userWindow.initPopWindow();
 	userWindow.initValidator();
 </script>
  		