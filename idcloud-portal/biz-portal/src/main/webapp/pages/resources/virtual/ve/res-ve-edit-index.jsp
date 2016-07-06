<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="editVeWindow">
         <div>编辑资源环境</div>
         <div id="editVeForm" style="overflow: hidden;">
         	 <input type="hidden" data-name="resTopologySid" id="edit-ve-resTopologySid"/>
         	 <input type="hidden" data-name="resTopologyType" id="edit-ve-resTopologyType"/>
         	 <input type="hidden" data-name="taskId" id="edit-ve-taskId"/>
         	 <input type="hidden" data-name="virtualPlatformType" id="edit-ve-virtualPlatformType"/>
         	 <input type="hidden" data-name="virtualPlatformVersion" id="edit-ve-virtualPlatformVersion"/>
             <div style="width:100%;height:100%;">
             	 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>环境名称:</td>
                        <td align="left"><input type="text" data-name="resTopologyName" id="edit-ve-resTopologyName"/></td>
                    	<td align="right"><font style="color:red">*</font>管理地址:</td>
                        <td align="left"><input type="text" data-name="managementUrl" id="edit-ve-managementUrl"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>用户名:</td>
                        <td align="left"><input type="text" data-name="managementAccount" id="edit-ve-managementAccount"/></td>
                    	<td align="right"><font style="color:red">*</font>更新周期(小时):</td>
                        <td align="left"><input type="text" data-name="updateCycle" id="edit-ve-updateCycle"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>密码:</td>
                        <td align="left"><input type="password" data-name="managementPassword" id="edit-ve-managementPassword"/></td>
                    	<td align="right"><font style="color:red">*</font>确认密码:</td>
                        <td align="left"><input type="password"  id="edit-ve-confirmPassword"/></td>
                    </tr>
                    <tr>
                        <td align="right" valign="top">描述:</td>
                        <td align="left" colspan="3">
                        	<textarea data-name="description" id="edit-ve-description" ></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4" height="32px">
                        	<input style="margin-right: 5px;" onclick="saveEditVirtualInfo()" type="button" id="editVeSave" value="保存" />
              				<input id="editVeCancel" type="button" value="取消" />&nbsp;&nbsp;
                        </td>
                    </tr>
                </table>
             </div>
         </div>
 </div>
 
 <div id="editIvmAndOtherVeWindow">
         <div>编辑资源环境</div>
         <div id="editIvmAndOtherVeForm" style="overflow: hidden;">
         	 <input type="hidden" data-name="resTopologySid" id="edit-ivmOther-ve-resTopologySid"/>
         	 <input type="hidden" data-name="resTopologyType" id="edit-ivmOther-ve-resTopologyType"/>
         	 <input type="hidden" data-name="taskId" id="edit-ivmOther-ve-taskId"/>
         	 <input type="hidden" data-name="virtualPlatformType" id="edit-ivmOther-ve-virtualPlatformType"/>
         	 <input type="hidden" data-name="virtualPlatformVersion" id="edit-ivmOther-ve-virtualPlatformVersion"/>
             <div style="width:100%;height:100%;">
             	 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>环境名称:</td>
                        <td align="left"><input type="text" data-name="resTopologyName" id="edit-ivmOther-ve-resTopologyName"/></td>
                    </tr>
                    <tr>	
                    	<td align="right">描述:</td>
                        <td align="left">
                          <textarea data-name="description" id="edit-ivmOther-ve-description" ></textarea>
                        </td>
                    </tr>
                    <tr> 	
                    	<td align="right" height="32px" colspan="2">
                        	&nbsp;&nbsp;<input style="margin-right: 5px;" onclick="saveIvmOtherEditVirtualInfo()" type="button" id="editIvmOtherVeSave" value="保存" />
              				<input id="editIvmOtherVeCancel" type="button" value="取消" />
                        </td>
                    </tr>
                </table>
             </div>
         </div>
 </div>

<script type="text/javascript">
	 //初始化sys-index页面model
	 var veEdit = new virtualVeEditModel();
	 // 初始化弹出框
	 veEdit.initPopWindow();
	 // 验证初始化
	 veEdit.initValidator();
</script>
