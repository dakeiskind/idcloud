<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="editImageWindow">
         <div>发布操作系统模板</div>
         <div id="editImageForm" style="overflow: hidden;">
         <input type="hidden" data-name="uuid" id="uuid"/>
         <input type="hidden" data-name="resImageSid" id="edit-image-resImageSid"/>
         <input type="hidden" data-name="resVeSid" id="edit-image-resVeSid"/>
              <table border="0" width="100%" cellspacing="5" cellpadding="0">
              				<tr>
		                        <td align="right"><font style="color:red">*</font>模板名称:</td>
		                        <td align="left"><input type="text" data-name="imageName" id="edit-image-imageName"/></td> 
		                    	
		                    </tr>
		                     <tr>
		                        <td align="right"><font style="color:red">*</font>操作系统类型:</td>
		                        <td align="left">
		                           <div data-name="osType" id="edit-image-osType"></div>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>操作系统版本:</td>
		                        <td align="left">
		                             <div data-name="osVersion" id="edit-image-osVersion"></div>
		                        </td>
		                    </tr> 
		                     <tr>
		                        <td align="right" colspan="2" height="32px">
		                        	<input style="margin-right: 5px;" onclick="editImageSaveInfo()" type="button" id="editImageSave" value="确定" />
		              				<input id="editImageCancel" type="button" value="取消" />&nbsp;&nbsp;
		                        </td>
		                    </tr>
		                </table>
         </div>
 </div>

<script type="text/javascript">
	 //初始化sys-index页面model
	 var imageEdit = new virtualImageEditModel();
	 imageEdit.initInput();
	 // 初始化弹出框
	 imageEdit.initPopWindow();
	 // 关联下拉列表框
	 imageEdit.initComboxLinkage();
	 // 验证初始化
	 imageEdit.initValidator();
	//imageAdd.setVmBasicInfo();
</script>
