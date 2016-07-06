<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="addPnvWindow">
       <div>新增VLAN池</div>
       <div id="addPnvForm" style="overflow: hidden;">
           <div style="width:100%;height:100%;">
          	 	<table border="0" width="100%" cellspacing="5" cellpadding="0">
                   <tr>
                       <td align="right"><font style="color:red">*</font>VLAN池名称:</td>
                       <td align="left">
                            <input type="text" data-name="resPoolName" id="add-pnv-name"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>VLAN池类型:</td>
                       <td align="left" valign="middle">
                          <div id='add-pnv-type_1'  style='float: left;margin-top:5px;'>VLAN池</div>
                       </td>
                   </tr>
                   <tr>
                       <td align="right" valign="top">描述:</td>
                       <td align="left">
                           <textarea data-name="description" id="add-pnv-description"></textarea>
                       </td>
                   </tr>
                   <tr>
                       <td align="right" colspan="2" height="32px">
                       	<input style="margin-right: 5px;" onclick="submitAddVlan()" type="button" id="addPnvSave" value="保存" />
             				<input id="addPnvCancel" type="button" value="取消" />&nbsp;&nbsp;
                       </td>
                   </tr>
               </table>
           	 </div>
       </div>
 </div>

<script type="text/javascript">
	 //初始化
	 var pnvadd = new addPnvModel();
	 // 关联下拉列表框
	 pnvadd.initPopWindow();
	 // 验证初始化
	 pnvadd.initValidator();
</script>
