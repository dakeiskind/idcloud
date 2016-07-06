<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <!-- 编辑主机资源池信息 -->
  <div id="addHostRespoolWindow">
          <div>新增主机池</div>
          <div id="addHostRespoolForm" style="overflow: hidden;">
          		<input type="hidden" data-name="parentTopologySid" id="add-parentTopologySid"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="120px"><font style="color:red">*</font>资源池名称:</td>
                         <td align="left"><input type="text" data-name="resPoolName" id="add-resPoolName"/></td>
                         <td align="right" width="120px">资源池状态:</td>
                         <td align="left">
                         	<div data-name="status" id="add-status"></div>
                         </td>
                         <td align="right" width="120px">性能保障等级:</td>
                         <td align="left">
                         	<div data-name="perfLevel" id="add-perfLevel"></div>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right">分配策略:</td>
                         <td align="left" >
                         	<div data-name="allocationPolicy" id="add-allocationPolicy"></div>
                         </td>
                         <td align="right">换算公式:</td>
                         <td align="left">
                         	<input type="text" data-name="conversionFormula" id="add-conversionFormula"/>
                         </td>
                         <td align="right">可分配阀值:</td>
                         <td align="left">
                         	<input type="text" data-name="allocationThreshold" id="add-allocationThreshold"/>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right">资源池类型:</td>
                         <td align="left">
                         	<div data-name="resPoolType" id="add-resPoolType"></div>
                         </td>
                         <td class="virtualPlatformType_tb" align="right" style="display:none">虚拟平台类型:</td>
                         <td class="virtualPlatformType_tb" align="left" colspan="3" style="display:none">
                         	<div data-name="virtualPlatformType" id="add-pool-virtualPlatformType" ></div>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right"><font style="color:red">*</font>操作处理器:</td>
                         <td align="left" colspan="5">
                         	<input type="text" data-name="operationHandler" id="add-operationHandler"/>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">资源池描述:</td>
                         <td align="left" colspan="5">
                         	<textarea data-name="description" id="add-pool-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="6">
          				 	<input style="margin-right: 5px;" onclick="addRespoolSubmit()" type="button" id="addHostRespoolSave" value="保存" />
		              		<input id="addHostRespoolCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>   
  
  <script type="text/javascript">
  		
		//新增资源池
		var addPool = new addResPoolModel();
		addPool.initPopWindow();
		addPool.initValidator();
		
		// 控制虚拟化类型的显示
		$('#add-resPoolType').on('select', function (event){
		    var args = event.args;
		    if (args) {
			    var item = args.item;
			  	if(item.value == "RES-POOL-PM"){
			  		// 物理机资源池
			  		$(".virtualPlatformType_tb").hide();
			  	}else if(item.value == "RES-POOL-VM"){
			  		// 云主机资源池
			  		$(".virtualPlatformType_tb").show();
			  	}
		    }                        
		});
		
  </script>