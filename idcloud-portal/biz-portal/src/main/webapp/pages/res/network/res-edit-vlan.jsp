<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
  <!-- 编辑主机 -->
   <div id="editVlanWindow">
       <div>编辑VLAN</div>
       <div id="editVlanForm" style="overflow: hidden;">
       	<input type="hidden" data-name="resSid" id="resSidVlan"/>
           <table border="0" width="100%" cellspacing="5" cellpadding="0">
               <tr>
                   <td align="right"><font style="color:red">*</font>VLAN ID:</td>
                   <td align="left"><input type="text" data-name="vlanId" id="edit-vlanId"/></td>
                   <td align="right"><font style="color:red">*</font>VLAN名称:</td>
                   <td align="left"><input type="text" data-name="vlanName" id="edit-vlanName"/></td>
               </tr>
               <tr>
                   <td align="right">标签:</td>
                   <td align="left"><input type="text" data-name="tag" id="edit-tag"/></td>
               </tr>
               <tr>
                   <td align="right" colspan="4" height="40px">
                   <input style="margin-right: 5px;" onclick='editVlanSubmit()' type="button" id="editVlanSave" value="保存" />
                   <input id="editVlanCancel" type="button" value="取消" /></td>
               </tr>
           </table>
       </div>
  </div>  
       	 
  <script type="text/javascript">
	    var editVlan = new editVlanModel();
	    editVlan.initPopWindow();
	    editVlan.initValidator();
  </script>