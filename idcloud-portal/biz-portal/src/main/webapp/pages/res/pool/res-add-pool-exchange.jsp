<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <!-- 编辑主机资源池信息 -->
  <div id="addExchangeRespoolWindow">
          <div>新增Exchange池</div>
          <div id="addExchangeRespoolForm" style="overflow: hidden;">
          		<input type="hidden" data-name="parentTopologySid" id="add-parentTopologySid-exchange"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="120px"><font style="color:red">*</font>资源池名称:</td>
                         <td align="left"><input type="text" data-name="resPoolName" id="add-resPoolName-exchange"/></td>
                         <td align="right" width="110px">资源池状态:</td>
                         <td align="left">
                         	<div data-name="status" id="add-status-exchange"></div>
                         </td>
                         <td align="right" width="130px">性能保障等级:</td>
                         <td align="left">
                         	<div data-name="perfLevel" id="add-perfLevel-exchange"></div>
                         </td>
          			</tr>
          			<tr>
                         <td align="right">换算公式:</td>
                         <td align="left">
                         	<input type="text" data-name="conversionFormula" id="add-conversionFormula-exchange"/>
                         </td>
                         <td align="right">可分配阀值:</td>
                         <td align="left">
                         	<input type="text" data-name="allocationThreshold" id="add-allocationThreshold-exchange"/>
                         </td>
                         <td align="right">分配策略:</td>
                         <td align="left" colspan="3">
                         	<div data-name="allocationPolicy" id="add-allocationPolicy-exchange"></div>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right"><font style="color:red">*</font>操作处理器:</td>
                         <td align="left" colspan="5">
                         	<input type="text" data-name="operationHandler" id="add-operationHandler-exchange"/>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">资源池描述:</td>
                         <td align="left" colspan="5">
                         	<textarea data-name="description" id="add-pool-description-exchange"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="6">
          				 	<input style="margin-right: 5px;" onclick="addRespoolExchangeSubmit()" type="button" id="addExchangeRespoolSave" value="保存" />
		              		<input id="addExchangeRespoolCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>   
  
  <script type="text/javascript">
  		
		//新增资源池
		var addPool = new addResPoolModel();
		addPool.initPopExchangeWindow();
		addPool.initExchangeValidator();
		
  </script>