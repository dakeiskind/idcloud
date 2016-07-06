<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

   <!-- 编辑主机VIOS --> 
     <div id="editHostViosWindow">
          <div>编辑主机VIOS</div>
          <div id="editHostViosForm" style="overflow: hidden;">
          	<input type="hidden" data-name="resViosSid" id="edit-vios-host-sid"/>
          	<div style="width:100%;height:150px;">
          		<table border="0" width="100%" cellspacing="5" cellpadding="0">
          		    <tr>
          		   	   <td align="right">VIOS分区名:</td>
                       <td align="left"><input type="text" data-name="viosLparName" id="edit-vios-power-vios-lpar-name" /></td>
                       <td align="right">VIOS版本:</td>
                       <td align="left" colspan="3"><input type="text" data-name="version" id="edit-vios-power-version" /></td>
                   </tr> 
                   <tr>
                       <td align="right">CPU最大使用量:</td>
                       <td align="left"><input type="text" data-name="powerCpuMaxUnits" id="edit-vios-power-cpu-max-units"/></td>
                       <td align="right">CPU最小使用量:</td>
                       <td align="left"><input type="text" data-name="powerCpuMinUnits" id="edit-vios-power-cpu-min-units"/></td>
                       <td align="right">CPU使用量:</td>
                       <td align="left"><input type="text" data-name="powerCpuUnits" id="edit-vios-power-cpu-units"/></td>
                   </tr>
                   <tr>    
                       <td align="right">CPU最大核数:</td>
                       <td align="left"><input type="text" data-name="powerCpuMaxCores" id="edit-vios-power-cpu-max-cores"/></td>
                       <td align="right">CPU最小核数:</td>
                       <td align="left"><input type="text" data-name="powerCpuMinCores" id="edit-vios-power-cpu-min-cores"/></td>
                       <td align="right">CPU使用核数:</td>
                       <td align="left"><input type="text" data-name="powerCpuCores" id="edit-vios-power-cpu-cores"/></td>
                   </tr>
                   <tr>    
                       <td align="right">内存最大使用量:</td>
                       <td align="left"><input type="text" data-name="memoryMax" id="edit-vios-power-memory-max" /></td>
                       <td align="right">内存最小使用量:</td>
                       <td align="left"><input type="text" data-name="memoryMin" id="edit-vios-power-memory-min"/></td>
                       <td align="right">内存使用量:</td>
                       <td align="left"><input type="text" data-name="memorySize" id="edit-vios-power-memory-size"/></td>
                   </tr>
                   <tr>    
                       <td align="right">管理用户名:</td>
                       <td align="left"><input type="text" data-name="user" id="edit-vios-host-user" /></td>
                       <td align="right">管理用户密码:</td>
                       <td align="left"><input type="password" data-name="password" id="edit-vios-host-password"/></td>
                       <td align="right">管理用IP地址:</td>
                       <td align="left"><input type="text" data-name="ip" id="edit-vios-host-ip"/></td>
                   </tr>
               </table>
          	</div>
              <div style="width:100%;text-align:right;margin-top:10px;">
          		  <input style="margin-right: 5px;" onclick="submitEdithostViosInfo()" type="button" id="edithostViosSave" value="保存" />
              	  <input id="edithostViosCancel" type="button" value="取消" />&nbsp;&nbsp;&nbsp;&nbsp;
              </div>
          </div>
   </div>
   
   <script>
   		var editVios = new editViosHostModel();
   		editVios.initValidator();
   		editVios.initPopWindow();
   </script>         
  