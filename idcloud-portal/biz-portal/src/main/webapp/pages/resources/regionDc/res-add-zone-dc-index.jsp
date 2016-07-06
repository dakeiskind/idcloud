<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
  
  <!-- 新增区域Window -->
  <div id="addRegionWindow">
          <div>新增区域</div>
          <div id="addRegionForm" style="overflow: hidden;">
          		<input type="hidden" data-name="sortNo" id="add-region-sortNo"/>
          		<input type="hidden" data-name="resTopologyType" id="add-region-resTopologyType"/>
          		<table border="0" width="100%"cellspacing="5" cellpadding="5">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>区域名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="add-region-resTopologyName"/></td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">区域描述:</td>
                         <td align="left">
                         	<textarea data-name="description" id="add-region-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="2">
          				 	<input style="margin-right: 5px;" onclick="addRegionSubmit()" type="button" id="addRegionSave" value="保存" />
		              		<input id="addRegionCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>

   <!-- 新增数据中心Window -->
  <div id="addDcWindow">
          <div>新增数据中心</div>
          <div id="addDcForm" style="overflow: hidden;">
          		<input type="hidden" data-name="parentTopologySid" id="add-dc-parentTopologySid"/>
          		<input type="hidden" data-name="resTopologyType" id="add-dc-resTopologyType"/>
          		<input type="hidden" data-name="sortNo" id="add-dc-sortNo"/>
          		<table border="0" width="100%"cellspacing="5" cellpadding="5">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>数据中心名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="add-dc-resTopologyName"/></td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">数据中心描述:</td>
                         <td align="left">
                         	<textarea data-name="description" id="add-dc-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="2">
          				 	<input style="margin-right: 5px;" onclick="addDcSubmit()" type="button" id="addDcSave" value="保存" />
		              		<input id="addDcCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>
  
   <!-- 新增存储分类Window -->
  <div id="addRscWindow">
          <div>新增存储分类</div>
          <div id="addRscForm" style="overflow: hidden;">
          		<input type="hidden" data-name="parentTopologySid" id="add-rsc-parentTopologySid"/>
          		<input type="hidden" data-name="resTopologyType" id="add-rsc-resTopologyType"/>
          		<input type="hidden" data-name="sortNo" id="add-rsc-sortNo"/>
          		<table border="0" width="100%"cellspacing="5" cellpadding="5">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>存储分类名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="add-rsc-resTopologyName"/></td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">存储分类描述:</td>
                         <td align="left">
                         	<textarea data-name="description" id="add-rsc-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="2">
          				 	<input style="margin-right: 5px;" onclick="addRscSubmit()" type="button" id="addRscSave" value="保存" />
		              		<input id="addRscCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>
  
   <script type="text/javascript">
	    // 新增区域或者数据中心
		var addzd = new resAddZoneDcModel();
		addzd.initPopWindow();
		addzd.initValidator();
		
  </script>