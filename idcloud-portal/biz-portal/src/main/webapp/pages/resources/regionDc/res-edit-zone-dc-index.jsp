<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
  
  <!-- 编辑区域Window -->
  <div id="editRegionWindow">
          <div>编辑区域</div>
          <div id="editRegionForm" style="overflow: hidden;">
          		<input type="hidden" data-name="resTopologyType" id="edit-region-resTopologyType"/>
          		<input type="hidden" data-name="resTopologySid" id="edit-region-resTopologySid"/>
          		<table border="0" width="100%"cellspacing="5" cellpadding="5">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>区域名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="edit-region-resTopologyName"/></td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">区域描述:</td>
                         <td align="left">
                         	<textarea data-name="description" id="edit-region-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="2">
          				 	<input style="margin-right: 5px;" onclick="editRegionSubmit()" type="button" id="editRegionSave" value="保存" />
		              		<input id="editRegionCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>

   <!-- 编辑数据中心Window -->
  <div id="editDcWindow">
          <div>编辑数据中心</div>
          <div id="editDcForm" style="overflow: hidden;">
          		<input type="hidden" data-name="resTopologySid" id="edit-dc-resTopologySid"/>
          		<input type="hidden" data-name="parentTopologySid" id="edit-dc-parentTopologySid"/>
          		<input type="hidden" data-name="resTopologyType" id="edit-dc-resTopologyType"/>
          		<table border="0" height="50px" width="100%"cellspacing="5" cellpadding="0">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>数据中心名称：</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="edit-dc-resTopologyName"/></td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">数据中心描述：</td>
                         <td align="left">
                         	<textarea data-name="description" id="edit-dc-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="2">
          				 	<input style="margin-right: 5px;" onclick="editDcSubmit()" type="button" id="editDcSave" value="保存" />
		              		<input id="editDcCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>
  
   <script type="text/javascript">
	    // 新增区域或者数据中心
		var editzd = new resEditZoneDcModel();
		editzd.initPopWindow();
		editzd.initValidator();
		
  </script>