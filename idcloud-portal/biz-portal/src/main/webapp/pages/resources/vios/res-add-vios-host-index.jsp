<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

     <!-- 新增主机VIOS --> 
     <div id="addHostViosWindow">
          <div>新增主机VIOS</div>
          <div id="addHostViosForm" style="overflow: hidden;">
          	<div style="width:100%;height:95px;">
          		<table border="0" width="100%" cellspacing="5" cellpadding="0">
          		   <tr>
          		   	   <td align="right">VIOS分区名:</td>
                       <td align="left"><input type="text" data-name="viosLparName" id="add-vios-power-vios-lpar-name" /></td>
                       <td align="right">VIOS版本:</td>
                       <td align="left"><input type="text" data-name="version" id="add-vios-power-version" /></td>
                       <td align="right">关联主机:</td>
                       <td align="left">
                       		<div data-name="resHostSid" id="add-vios-host-sid"></div>
                       </td>
                   </tr> 
                   <tr>
                       <td align="right">CPU最大使用量:</td>
                       <td align="left"><input type="text"data-name="powerCpuMaxUnits" id="add-vios-power-cpu-max-units" /></td>
                       <td align="right">CPU最小使用量:</td>
                       <td align="left"><input type="text" data-name="powerCpuMinUnits" id="add-vios-power-cpu-min-units"/></td>
                       <td align="right">CPU使用量:</td>
                       <td align="left"><input type="text" data-name="powerCpuUnits" id="add-vios-power-cpu-units"/></td>
                   </tr>
                   <tr>    
                       <td align="right">CPU最大核数:</td>
                       <td align="left"><input type="text" data-name="powerCpuMaxCores" id="add-vios-power-cpu-max-cores" /></td>
                       <td align="right">CPU最小核数:</td>
                       <td align="left"><input type="text" data-name="powerCpuMinCores" id="add-vios-power-cpu-min-cores"/></td>
                       <td align="right">CPU使用核数:</td>
                       <td align="left"><input type="text" data-name="powerCpuCores" id="add-vios-power-cpu-cores"/></td>
                   </tr>
                   <tr>    
                       <td align="right">内存最大使用量:</td>
                       <td align="left"><input type="text" data-name="memoryMax" id="add-vios-power-memory-max" /></td>
                       <td align="right">内存最小使用量:</td>
                       <td align="left"><input type="text" data-name="memoryMin" id="add-vios-power-memory-min"/></td>
                       <td align="right">内存使用量:</td>
                       <td align="left"><input type="text" data-name="memorySize" id="add-vios-power-memory-size"/></td>
                   </tr>
                   <tr>    
                       <td align="right">管理用户名:</td>
                       <td align="left"><input type="text" data-name="user" id="add-vios-host-user" /></td>
                       <td align="right">管理用户密码:</td>
                       <td align="left"><input type="password" data-name="password" id="add-vios-host-password"/></td>
                       <td align="right">管理IP地址:</td>
                       <td align="left"><input type="text" data-name="ip" id="add-vios-host-ip"/></td>
                   </tr>
               </table>
          	</div>
              <div style="width:100%;text-align:right;margin-top:60px;">
          		  <input style="margin-right: 5px;" onclick="submitAddhostViosInfo()" type="button" id="addhostViosSave" value="保存" />
              	  <input id="addhostViosCancel" type="button" value="取消" /> &nbsp;&nbsp;&nbsp;
              </div>
          </div>
   </div>
   
   <script>
   		var addVios = new addViosHostModel();
   		addVios.initValidator();
   		addVios.initPopWindow();
   </script>
   