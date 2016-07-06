<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
  <!-- 编辑资源分区Window -->
  <div id="editRzWindow">
          <div>编辑资源分区</div>
          <div id="editRzForm" style="overflow: hidden;">
          		<input type="hidden" data-name="resTopologySid" id="edit-rz-resTopologySid"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>分区名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="edit-rz-resTopologyName"/></td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">分区描述:</td>
                         <td align="left">
                         	<textarea data-name="description" id="edit-rz-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="2">
          				 	<input style="margin-right: 5px;" onclick="editRzSubmit()" type="button" id="editRzSave" value="保存" />
		              		<input id="editRzCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>
  
   <script type="text/javascript">
	    // 新增区域或者数据中心
		var editrz = new resEditRzModel();
		editrz.initPopWindow();
		editrz.initValidator();
		
  </script>