<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
     <div id="addPveVmWindow">
            <div>新增分区</div>
            <div id="addPveVmForm">
            	<div>
	            	 <table border="0" width="100%" cellspacing="5" cellpadding="0">
		                    <tr>
		                        <td align="right" width="100"><font style="color:red">*</font>分区名称:</td>
		                        <td align="left"><input type="text" data-name="vmName" id="add-pve-vmName"/></td>
		                        <td align="right" width="100"><font style="color:red">*</font>分区编号:</td>
		                        <td align="left"><input type="text" data-name="parId" id="add-pve-parId"/></td>
		                        <td align="right" width="100"><font style="color:red">*</font>分区配置:</td>
		                        <td align="left"><input type="text" data-name="parProfile" id="add-pve-parProfile"/></td>
		                    </tr>
		                    <tr>
		                    	<td align="right" width="100"><font style="color:red">*</font>分区类型:</td>
		                        <td align="left"><div data-name="parType" id="add-pve-parType"></div></td>
		                        <td align="right"><font style="color:red">*</font>主机:</td>
		                        <td align="left"><div data-name="allocateResHostSid" id="add-pve-allocateResHostSid"></div></td>
		                        <td align="right"><font style="color:red">*</font>CPU共享资源池:</td>
		                        <td align="left"><div data-name="resCpuPoolSid" id="add-pve-resCpuPoolSid"></div></td>
		                    </tr>
		                    <tr>
		                     	<td align="right"><font style="color:red">*</font>物理CPU使用量:</td>
		                        <td align="left"><input type="text" data-name="powerCpuUsedUnits" id="add-pve-powerCpuUsedUnits"/></td>
		                        <td align="right"><font style="color:red">*</font>物理CPU最小使用量:</td>
		                        <td align="left"><input type="text" data-name="powerCpuMinUnits" id="add-pve-powerCpuMinUnits"/></td>
		                        <td align="right"><font style="color:red">*</font>物理CPU最大使用量:</td>
		                        <td align="left"><input type="text" data-name="powerCpuMaxUnits" id="add-pve-powerCpuMaxUnits"/></td>
		                    </tr>
		                    <tr id="isPhyZoon">
		                     	<td align="right"><font style="color:red">*</font>虚拟CPU值:</td>
		                        <td align="left"><input type="text" data-name="cpuCores" id="add-pve-cpuCores"/></td>
		                        <td align="right"><font style="color:red">*</font>虚拟CPU最小值:</td>
		                        <td align="left"><input type="text" data-name="cpuCoresMin" id="add-pve-cpuCoresMin"/></td>
		                        <td align="right"><font style="color:red">*</font>虚拟CPU最大值:</td>
		                        <td align="left"><input type="text" data-name="cpuCoresMax" id="add-pve-cpuCoresMax"/></td>
		                    </tr>
		                    <tr>
		                     	<td align="right"><font style="color:red">*</font>内存使用量:</td>
		                        <td align="left"><input type="text" data-name="memorySize" id="add-pve-memorySize"/></td>
		                        <td align="right"><font style="color:red">*</font>内存最小使用量:</td>
		                        <td align="left"><input type="text" data-name="memoryMin" id="add-pve-memoryMin"/></td>
		                        <td align="right"><font style="color:red">*</font>内存最大使用量:</td>
		                        <td align="left"><input type="text" data-name="memoryMax" id="add-pve-memoryMax"/></td>
		                    </tr>
		                    <tr>
		                     	<td align="right">管理用户名:</td>
		                        <td align="left"><input type="text" data-name="managementAccount" id="add-pve-managementAccount"/></td>
		                     	<td align="right">操作系统名称:</td>
		                        <td align="left"><input type="text" data-name="osName" id="add-pve-osName"/></td>
		                        <td align="right">操作系统版本:</td>
		                        <td align="left"><div data-name="osVersion" id="add-pve-osVersion"></div></td>
		                    </tr>
		                    <tr>
		                     	<td align="right">管理用户密码:</td>
		                        <td align="left"><input type="text" data-name="managementPassword" id="add-pve-managementPassword"/></td>
		                        <td align="right"><font style="color:red">*</font>IP地址:</td>
		                        <td align="left"><input type="text" data-name="vmIp" id="add-pve-vmIp"/></td>
		                        <td align="right"><font style="color:red">*</font>虚拟机状态:</td>
		                        <td align="left"><div data-name="status" id="add-pve-status"></div></td>
		                    </tr>
		                </table>
            	</div>
                <div style="width:100%;padding-top:5px;text-align:right">
            		  <input style="margin-right: 5px;" onclick="javascript:submitAddPveVmInfo()" type="button" id="addPveVmSave" value="保存" />
		              <input id="addPveVmCancel" type="button" value="取消" />&nbsp;&nbsp;&nbsp;&nbsp;
                </div>
            </div>
       </div>
         
  <script type="text/javascript">
	   var pveVm = new addPveVmModel();
	   pveVm.initPopWindow();
	   pveVm.initValidator(); 
	   
	   // 下拉框联动
	   $("#add-pve-allocateResHostSid").on('select', function (event){
		   
		   var codeCustom2 = new codeModel({width:140,autoDropDownHeight:false,dropDownWidth:140,dropDownHeight:230});
     	   codeCustom2.getCustomCode("add-pve-resCpuPoolSid","/cpuPools","cpuPoolName","resCpuPoolSid",true,"POST",{resHostSid:event.args.item.value});
     	   $("#add-pve-resCpuPoolSid").jqxDropDownList({selectedIndex:0});
	   });
	   $("#add-pve-parType").on('select', function (event){
		   if(event.args.item.value == "0"){
			   $("#isPhyZoon").hide();
		   }else{
			   $("#isPhyZoon").show();
		   }
	   });
  </script>