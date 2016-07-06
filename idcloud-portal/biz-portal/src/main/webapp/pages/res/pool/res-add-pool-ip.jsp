<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <!-- 编辑主机资源池信息 -->
  <div id="addIpRespoolWindow">
          <div>新增IP池</div>
          <div id="addIpRespoolForm" style="overflow: hidden;">
          		<input type="hidden" data-name="parentTopologySid" id="add-parentTopologySid"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="120px"><font style="color:red">*</font>资源池名称:</td>
                         <td align="left"><input type="text" data-name="resPoolName" id="add-resPoolName-ip"/></td>
                         <td align="right" width="110px">资源池状态:</td>
                         <td align="left">
                         	<div data-name="status" id="add-status-ip"></div>
                         </td>
                         <td align="right" width="130px">性能保障等级:</td>
                         <td align="left">
                         	<div data-name="perfLevel" id="add-perfLevel-ip"></div>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right">分配策略:</td>
                         <td align="left">
                         	<div data-name="allocationPolicy" id="add-allocationPolicy-ip"></div>
                         </td>
                         <td align="right">换算公式:</td>
                         <td align="left">
                         	<input type="text" data-name="conversionFormula" id="add-conversionFormula-ip"/>
                         </td>
                         <td align="right">可分配阀值:</td>
                         <td align="left">
                         	<input type="text" data-name="allocationThreshold" id="add-allocationThreshold-ip"/>
                         </td>
          			</tr>
          			<tr>
          				<td align="right">浮动池名称:</td>
                         <td align="left">
                         	<input type="text" data-name="floatingIpPoolName" id="add-floatingIpPoolName-ip"/>
                         </td>
                         <td align="right">IP分类:</td>
                         <td align="left">
                         	<div data-name="ipCategory" id="add-ipCategory-ip"></div>
                         </td>
          				 <td align="right">虚拟平台类型:</td>
                         <td align="left">
                         	<div data-name="virtualPlatformType" id="add-virtualPlatformType-ip"></div>
                         </td>
          			</tr>

          			<tr>
          				 <td align="right"><font style="color:red">*</font>操作处理器:</td>
                         <td align="left" colspan="5">
                         	<input type="text" data-name="operationHandler" id="add-operationHandler-ip"/>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">资源池描述:</td>
                         <td align="left" colspan="5">
                         	<textarea data-name="description" id="add-description-ip"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="6">
          				 	<input style="margin-right: 5px;" onclick="addRespoolIpSubmit()" type="button" id="addIpRespoolSave" value="保存" />
		              		<input id="addIpRespoolCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>   
  
  <script type="text/javascript">
		//新增资源池
		var addPool = new addResPoolModel();
		addPool.initPopIpWindow();
		addPool.initIpValidator();
  </script>