<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="editPnvWindow">
       <div>编辑VLAN池</div>
       <div id="editPnvForm" style="overflow: hidden;">
           <div style="width:100%;height:100%;">
           		<input type="hidden" data-name="resPoolSid" id="edit-pnv-resPoolSid"/>
          	 	<table border="0" width="100%" cellspacing="5" cellpadding="0">
<!--                    <tr> -->
<!--                        <td align="right"><font style="color:red">*</font>当前资源分区:</td> -->
<!--                        <td align="left"> -->
<!--                           <span id="add-ve-zone">资源分区1</span> -->
<!--                        </td> -->
<!--                    </tr> -->
                   <tr>
                       <td align="right"><font style="color:red">*</font>VLAN池名称:</td>
                       <td align="left">
                            <input type="text" data-name="resPoolName" id="edit-pnv-name"/>
                       </td>
                   </tr>
                   <tr>
                       <td align="right"><font style="color:red">*</font>VLAN池类型:</td>
                       <td align="left" valign="middle">
                          <div id='edit-pnv-type_1'  style='float: left;margin-top:5px;'>VLAN池</div>
				          <div id='edit-pnv-type_2'  style='float: left;margin-top:5px;'>VXLAN池</div>
                       </td>
                   </tr>
                   <tr>
                       <td align="right" valign="top">描述:</td>
                       <td align="left">
                           <textarea data-name="description" id="edit-pnv-description"></textarea>
                       </td>
                   </tr>
                   <tr>
                       <td align="right" colspan="2" height="32px">
                       	<input style="margin-right: 5px;" onclick="submitEditVlan()" type="button" id="editPnvSave" value="保存" />
             				<input id="editPnvCancel" type="button" value="取消" />&nbsp;&nbsp;
                       </td>
                   </tr>
               </table>
           	 </div>
       </div>
 </div>

<script type="text/javascript">
	 //初始化
	 var pnvedit = new editPnvModel();
	 // 关联下拉列表框
	 pnvedit.initPopWindow();
	 // 验证初始化
	 pnvedit.initValidator();
</script>
