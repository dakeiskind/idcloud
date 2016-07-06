<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
  
  <!-- 新增资源分区Window -->
  <div id="addRzWindow">
          <div>新增资源分区</div>
          <div id="addRzForm" style="overflow: hidden;">
          		<input type="hidden" data-name="resTopologyType" id="add-rz-resTopologyType"/>
          		<input type="hidden" data-name="sortNo" id="add-rz-sortNo"/>
          		<input type="hidden" data-name="parentTopologySid" id="add-rz-parentTopologySid"/>
			  	<input type="hidden" data-name="resEnvType" id="add-rz-env-type"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>分区名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="add-rz-resTopologyName"/></td>
          			</tr>
					<tr>
						<td align="right"><font style="color:red">*</font>关联资源环境:</td>
						<td align="left">
							<div data-name="resEnvId" id="add-rz-relation-ve"></div>
						</td>
					</tr>
					<tr>
						<td align="right">资源环境类型:</td>
						<td align="left">
							<div id="veType"></div>
						</td>
					</tr>
					<tr id="openstackZoomName">
						<td align="right">区域名称:</td>
						<td align="left">
							<input type="text" data-name="regionName" id="add-rz-openstack-regionName"/>
						</td>
					</tr>
          			<tr>
          				 <td align="right" valign="top">分区描述:</td>
                         <td align="left">
                         	<textarea data-name="description" id="add-rz-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="2">
          				 	<input style="margin-right: 5px;" onclick="addRzSubmit()" type="button" id="addRzSave" value="保存" />
		              		<input id="addRzCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>
  
   <script type="text/javascript">
	    // 新增区域或者数据中心
		var addrz = new resAddRzModel();
		addrz.initPopWindow();
		addrz.initValidator();
		
  </script>