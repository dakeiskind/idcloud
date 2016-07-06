<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
     <div id="editPveVmWindow">
            <div>编辑分区</div>
            <div id="editPveVmForm">
            	<div>
            		 <input type="hidden" data-name="resVmSid" id="edit-pve-resVmSid"/>
            		 <input type="hidden" data-name="parType" id="edit-pve-parType"/>
	            	 <table border="0" width="100%" cellspacing="5" cellpadding="0">
		                    <tr>
		                        <td align="right" width="100"><font style="color:red">*</font>分区名称:</td>
		                        <td align="left"><input type="text" data-name="vmName" id="edit-pve-vmName"/></td>
		                        <td align="right" width="100"><font style="color:red">*</font>分区编号:</td>
		                        <td align="left"><input type="text" data-name="parId" id="edit-pve-parId"/></td>
		                        <td align="right" width="100"><font style="color:red">*</font>分区配置:</td>
		                        <td align="left"><input type="text" data-name="parProfile" id="edit-pve-parProfile"/></td>
		                    </tr>
		                    <tr>
		                     	<td align="right"><font style="color:red">*</font>物理CPU使用量:</td>
		                        <td align="left"><input type="text" data-name="powerCpuUsedUnits" id="edit-pve-powerCpuUsedUnits"/></td>
		                        <td align="right"><font style="color:red">*</font>物理CPU最小使用量:</td>
		                        <td align="left"><input type="text" data-name="powerCpuMinUnits" id="edit-pve-powerCpuMinUnits"/></td>
		                        <td align="right"><font style="color:red">*</font>物理CPU最大使用量:</td>
		                        <td align="left"><input type="text" data-name="powerCpuMaxUnits" id="edit-pve-powerCpuMaxUnits"/></td>
		                    </tr>
		                    <tr id="isEditPhyZoon">
		                     	<td align="right"><font style="color:red">*</font>虚拟CPU值:</td>
		                        <td align="left"><input type="text" data-name="cpuCores" id="edit-pve-cpuCores"/></td>
		                        <td align="right"><font style="color:red">*</font>虚拟CPU最小值:</td>
		                        <td align="left"><input type="text" data-name="cpuCoresMin" id="edit-pve-cpuCoresMin"/></td>
		                        <td align="right"><font style="color:red">*</font>虚拟CPU最大值:</td>
		                        <td align="left"><input type="text" data-name="cpuCoresMax" id="edit-pve-cpuCoresMax"/></td>
		                    </tr>
		                    <tr>
		                     	<td align="right"><font style="color:red">*</font>内存使用量:</td>
		                        <td align="left"><input type="text" data-name="memorySize" id="edit-pve-memorySize"/></td>
		                        <td align="right"><font style="color:red">*</font>内存最小使用量:</td>
		                        <td align="left"><input type="text" data-name="memoryMin" id="edit-pve-memoryMin"/></td>
		                        <td align="right"><font style="color:red">*</font>内存最大使用量:</td>
		                        <td align="left"><input type="text" data-name="memoryMax" id="edit-pve-memoryMax"/></td>
		                    </tr>
		                    <tr>
		                     	<td align="right">管理用户名:</td>
		                        <td align="left"><input type="text" data-name="managementAccount" id="edit-pve-managementAccount"/></td>
		                     	<td align="right">操作系统名称:</td>
		                        <td align="left"><input type="text" data-name="osName" id="edit-pve-osName"/></td>
		                    </tr>
		                    <tr>
		                     	<td align="right">管理用户密码:</td>
		                        <td align="left"><input type="text" data-name="managementPassword" id="edit-pve-managementPassword"/></td>
		                        <td align="right"><font style="color:red">*</font>虚拟机状态:</td>
		                        <td align="left"><div data-name="status" id="edit-pve-status"></div></td>
		                    </tr>
		                </table>
            	</div>
                <div style="width:100%;padding-top:5px;text-align:right">
            		  <input style="margin-right: 5px;" onclick="javascript:submitEditPveVmInfo()" type="button" id="editPveVmSave" value="保存" />
		              <input id="editPveVmCancel" type="button" value="取消" />&nbsp;&nbsp;&nbsp;&nbsp;
                </div>
            </div>
       </div>
         
  <script type="text/javascript">
	   var pveEditVm = new editPveVmModel();
	   pveEditVm.initPopWindow();
	   pveEditVm.initValidator();
	
  </script>