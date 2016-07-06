<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <!--新增存储资源池信息 -->
  <div id="addStorageRespoolWindow">
          <div>新增存储资源池</div>
          <div id="addStorageRespoolForm" style="overflow: hidden;">
          		<input type="hidden" data-name="parentTopologySid" id="add-storage-parentTopologySid"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="120px"><font style="color:red">*</font>资源池名称:</td>
                         <td align="left"><input type="text" data-name="resPoolName" id="add-storage-resPoolName"/></td>
                         <td align="right" width="110px">资源池状态:</td>
                         <td align="left">
                         	<div data-name="status" id="add-storage-status"></div>
                         </td>
                         <td align="right" width="130px">性能保障等级:</td>
                         <td align="left">
                         	<div data-name="perfLevel" id="add-storage-perfLevel"></div>
                         </td>
          			</tr>
          			<tr>
          				<td align="right">存储类型:</td>
                         <td align="left" >
                         	<div data-name="storageType" id="add-storage-storageType"></div>
                         </td>
                         <td align="right">换算公式:</td>
                         <td align="left">
                         	<input type="text" data-name="conversionFormula" id="add-storage-conversionFormula"/>
                         </td>
                         <td align="right">可分配阀值:</td>
                         <td align="left">
                         	<input type="text" data-name="allocationThreshold" id="add-storage-allocationThreshold"/>
                         </td>
          			</tr>
          			<tr>
                         <td align="right">分配策略:</td>
                         <td align="left" colspan="5">
                         	<div data-name="allocationPolicy" id="add-storage-allocationPolicy"></div>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right"><font style="color:red">*</font>操作处理器:</td>
                         <td align="left" colspan="5">
                         	<input type="text" data-name="operationHandler" id="add-storage-operationHandler"/>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">资源池描述:</td>
                         <td align="left" colspan="5">
                         	<textarea data-name="description" id="add-storage-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="6">
          				 	<input style="margin-right: 5px;" onclick="addResStoragePoolSubmit()" type="button" id="addResStoragePoolSave" value="保存" />
		              		<input id="addResStoragePoolCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>   
  
  <script type="text/javascript">
  		
		//新增存储资源池
 		var addStoragePool = new addResPoolModel();
 		addStoragePool.initStoragePopWindow();
 		addStoragePool.initStorageValidator();
		
  </script>