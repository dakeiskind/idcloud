<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
  <div id="addVlanWindow">
      <div>新增VLAN</div>
      <div id="addVlanForm" style="overflow: hidden;">
          <table border="0" width="100%" cellspacing="5" cellpadding="0">
              <tr>
                  <td align="right"><font style="color:red">*</font>VLAN ID:</td>
                  <td align="left"><input type="text" data-name="vlanId" id="add-vlanId"/></td>
                  <td align="right"><font style="color:red">*</font>VLAN名称:</td>
                  <td align="left"><input type="text" data-name="vlanName" id="add-vlanName"/></td>
              </tr>
              <tr>
                  <td align="right">标签:</td>
                  <td align="left"><input type="text" data-name="tag" id="add-tag"/></td>
              </tr>
              <tr>
                  <td align="right" colspan="4" height="40px">
                  <input style="margin-right: 5px;" onclick='addVlanSubmit()' type="button" id="addVlanSave" value="保存" />
                  <input id="addVlanCancel" type="button" value="取消" /></td>
              </tr>
          </table>
      </div>
 </div>  
         
  <script type="text/javascript">
	    var addVlan = new addVlanModel();
	    addVlan.initPopWindow();
	    addVlan.initValidator();
       

  </script>