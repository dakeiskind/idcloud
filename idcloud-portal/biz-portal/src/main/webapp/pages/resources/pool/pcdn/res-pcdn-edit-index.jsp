<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="editPcdnWindow">
       <div>编辑CDN</div>
       <div id="editPcdnForm" style="overflow: hidden;">
           <div style="width:100%;height:100%;">
           <input type="hidden" data-name="resPoolSid" id="edit-pcdn-parentTopologySid"/>
            <input type="hidden" data-name="cdnSid" id="edit-pcdn-cdnSid"/>
          	 	<table border="0" width="100%" cellspacing="5" cellpadding="0">
          	 	   <tr>
	          	 	   	<td align="right" nowrap="nowrap">资源环境：</td>
	           			<td>
	           				<div id="edit-pcdn-resTopologySid" data-name="parentTopologySid"></div>
	           			</td>
          	 	   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>CDN名称:</td>
                       <td align="left">
                            <input type="text" data-name="cdnName" id="edit-pcdn-cdnName"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>验证用户名:</td>
                       <td align="left">
                           <input type="text" data-name="cdnAccount" id="edit-pcdn-cdnAccount"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>验证密码:</td>
                       <td align="left">
                           <input type="text" data-name="cdnPassword" id="edit-pcdn-cdnPassword"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>CDN地址:</td>
                       <td align="left">
                           <input type="text" data-name="cdnAddress" id="edit-pcdn-cdnAddress"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right" colspan="2" height="32px">
                       	<input style="margin-right: 5px;" onclick="submitEditCDN()" type="button" id="editPcdnSave" value="保存" />
             				<input id="editPcdnCancel" type="button" value="取消" />&nbsp;&nbsp;
                       </td>
                   </tr>
               </table>
           	 </div>
       </div>
 </div>

<script type="text/javascript">
	 //初始化
	 var pcdnedit = new editPcdnModel();
	 // 关联下拉列表框
	 pcdnedit.initPopWindow();
	 // 验证初始化
	 pcdnedit.initValidator();
</script>
