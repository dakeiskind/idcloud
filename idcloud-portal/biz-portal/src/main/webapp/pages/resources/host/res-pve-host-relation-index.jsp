<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
   <!-- 新增主机CPU池--> 
   <div id="addPveHostCpuPoolWindow">
          <div>新增主机CPU池</div>
          <div id="addPveHostCpuPoolForm" style="overflow: hidden;">
          	<input type="hidden" data-name="resHostSid" id="add-host-cpu-pool-sid"/>
          	<div style="width:100%;height:100px;">
          		<table border="0" width="100%" cellspacing="5" cellpadding="0">
                   <tr>
                       <td align="right"><font style="color:red">*</font>CPU池名称:</td>
                       <td align="left"><input type="text" data-name="cpuPoolName" id="add-host-cpu-pool-name"/></td>
                   </tr>
                   <tr>    
                       <td align="right" width="100">最大值:</td>
                       <td align="left"><input type="text" data-name="maxValue" id="add-host-cpu-pool-max"/></td>
                   </tr>
                   <tr>    
                       <td align="right" width="80">预留值:</td>
                       <td align="left"><input type="text" data-name="reservedValue" id="add-host-cpu-pool-reserve"/></td>
                   </tr>
               </table>
          	</div>
              <div style="width:100%;text-align:right;margin-top:5px;">
          		  <input style="margin-right: 5px;" onclick="submitPvehostCpuPoolInfo()" type="button" id="addPvehostCpuPoolSave" value="保存" />
              	  <input id="addPvehostCpuPoolCancel" type="button" value="取消" />&nbsp;&nbsp;&nbsp;
              </div>
          </div>
   </div>
   <!-- 编辑主机CPU池--> 
   <div id="editPveHostCpuPoolWindow">
          <div>编辑主机CPU池</div>
          <div id="editPveHostCpuPoolForm" style="overflow: hidden;">
          	<input type="hidden" data-name="resCpuPoolSid" id="edit-host-cpu-pool-sid"/>
          	<div style="width:100%;height:100px;">
          		<table border="0" width="100%" cellspacing="5" cellpadding="0">
                   <tr>
                       <td align="right"><font style="color:red">*</font>CPU池名称:</td>
                       <td align="left"><input type="text" data-name="cpuPoolName" id="edit-host-cpu-pool-name"/></td>
                   </tr>
                   <tr>    
                       <td align="right" width="100">最大值:</td>
                       <td align="left"><input type="text" data-name="maxValue" id="edit-host-cpu-pool-max"/></td>
                   </tr>
                   <tr>    
                       <td align="right" width="80">预留值:</td>
                       <td align="left"><input type="text" data-name="reservedValue" id="edit-host-cpu-pool-reserve"/></td>
                   </tr>
               </table>
          	</div>
              <div style="width:100%;text-align:right;margin-top:5px;">
          		  <input style="margin-right: 5px;" onclick="submitEditPvehostCpuPoolInfo()" type="button" id="editPvehostCpuPoolSave" value="保存" />
              	  <input id="editPvehostCpuPoolCancel" type="button" value="取消" />&nbsp;&nbsp;&nbsp;
              </div>
          </div>
   </div>
     
   <!-- 新增虚拟交换机 -->
   <div id="addPveHostVsWindow">
          <div>新增虚拟交换机</div>
          <div id="addPveHostVsForm" style="overflow: hidden;">
          	<input type="hidden" data-name="resHostSid" id="add-host-vs-sid"/>
          	<div style="width:100%;height:100px;">
          		<table border="0" width="100%" height="65px" cellspacing="5" cellpadding="0">
                   <tr>
                       <td align="right"><font style="color:red">*</font>交换机名称:</td>
                       <td align="left">
                       		<div data-name="resVsSid" id="add-vs-sid"></div>
                       </td>
                       <td align="left">
                       	   <input style="margin-right: 5px;" onclick="submitAddPvehostVsInfo()" type="button" id="addPvehostVsSave" value="保存" />
              	  		   <input id="addPvehostVsCancel" type="button" value="取消" />
                       </td>
                   </tr>
                  
               </table>
          	</div>
          </div>
     </div>
     
      <!-- 新增Phy IO --> 
     <div id="addPveHostPhyIoWindow">
          <div>新增主机配件</div>
          <div id="addPveHostPhyIoForm" style="overflow: hidden;">
          	<input type="hidden" data-name="resHostSid" id="add-host-phy-io-sid"/>
          	<div style="width:100%;height:190px;">
          		<table border="0" width="100%" cellspacing="5" cellpadding="0">
                   <tr>
                       <td align="right"><font style="color:red">*</font>配件类型编码:</td>
                       <td align="left"><div data-name="hostItemTypeCode" id="add-host-item-type-code"></div></td>
                       <td align="right" ><font style="color:red">*</font>配件分配标识:</td>
                       <td align="left"><input type="text" data-name="hostItemIndex" id="add-host_item_index"/></td>
                   </tr>
                   <tr>    
                       <td align="right"><font style="color:red">*</font>配件物理地址:</td>
                       <td align="left"><input type="text" data-name="hostItemAddr" id="add-host_item_addr"/></td>
                       <td align="right"><font style="color:red">*</font>配件位置信息:</td>
                       <td align="left"><input type="text" data-name="itemLocation" id="add-host-item_location"/></td>
                   </tr>
                   <tr>    
                       <td align="right">配件资源分配标志:</td>
                       <td align="left"><div data-name="resAllocFlag" id="add-res_alloc_flag"></div></td>
                       <td align="right">配件资源分配状态:</td>
                       <td align="left"><input type="text" data-name="resAllocStatus" id="add-res_alloc_status"/></td>
                   </tr>
                    <tr class="allotZoon" style="display:none">    
                       <td align="right">分配分区:</td>
                       <td align="left" colspan="3"><div data-name="resVmSid" id="add-res_vm_sid"></div></td>
                   </tr>
                    <tr>    
                       <td align="right" valign="top">配件描述:</td>
                       <td align="left" colspan="3"><textarea data-name="hostItemDesc" id="add-host_item_desc"></textarea></td>
                   </tr>
               </table>
          	</div>
              <div style="width:100%;text-align:right;margin-top:5px;">
          		  <input style="margin-right: 5px;" onclick="submitPvehostPhyIoInfo()" type="button" id="addPvehostPhyIoSave" value="保存" />
              	  <input id="addPvehostPhyIoCancel" type="button" value="取消" />
              </div>
          </div>
   </div>
   
    <!-- 编辑Phy IO --> 
     <div id="editPveHostPhyIoWindow">
          <div>编辑主机配件</div>
          <div id="editPveHostPhyIoForm" style="overflow: hidden;">
          	<input type="hidden" data-name="hostItemId" id="edit-host-phy-io-sid"/>
          	<div style="width:100%;height:190px;">
          		<table border="0" width="100%" cellspacing="5" cellpadding="0">
                   <tr>
                       <td align="right"><font style="color:red">*</font>配件类型编码:</td>
                       <td align="left"><div data-name="hostItemTypeCode" id="edit-host-item-type-code"></div></td>
                       <td align="right"><font style="color:red">*</font>配件分配标识:</td>
                       <td align="left"><input type="text" data-name="hostItemIndex" id="edit-host_item_index"/></td>
                   </tr>
                   <tr>    
                       <td align="right"><font style="color:red">*</font>配件物理地址:</td>
                       <td align="left"><input type="text" data-name="hostItemAddr" id="edit-host_item_addr"/></td>
                       <td align="right"><font style="color:red">*</font>配件位置信息:</td>
                       <td align="left"><input type="text" data-name="itemLocation" id="edit-host-item_location"/></td>
                   </tr>
                   <tr>    
                       <td align="right">配件资源分配标志:</td>
                       <td align="left"><div data-name="resAllocFlag" id="edit-res_alloc_flag"></div></td>
                       <td align="right">配件资源分配状态:</td>
                       <td align="left"><input type="text" data-name="resAllocStatus" id="edit-res_alloc_status"/></td>
                   </tr>
                    <tr class="allotZoon" style="display:none">    
                       <td align="right">分配分区:</td>
                       <td align="left" colspan="3"><div data-name="resVmSid" id="edit-res_vm_sid"></div></td>
                   </tr>
                    <tr>    
                       <td align="right" valign="top">配件描述:</td>
                       <td align="left" colspan="3"><textarea data-name="hostItemDesc" id="edit-host_item_desc"></textarea></td>
                   </tr>
               </table>
          	</div>
              <div style="width:100%;text-align:right;margin-top:5px;">
          		  <input style="margin-right: 5px;" onclick="submitEditPvehostPhyIoInfo()" type="button" id="editPvehostPhyIoSave" value="保存" />
              	  <input id="editPvehostPhyIoCancel" type="button" value="取消" />
              </div>
          </div>
   </div>
   
      <!-- 新增主机VIOS --> 
     <div id="addPveHostViosWindow">
          <div>新增主机VIOS</div>
          <div id="addPveHostViosForm" style="overflow: hidden;">
          	<input type="hidden" data-name="resHostSid" id="add-host-vios-sid"/>
          	<div style="width:100%;height:95px;">
          		<table border="0" width="100%" cellspacing="5" cellpadding="0">
                   <tr>
                       <td align="right">CPU最大使用量:</td>
                       <td align="left"><input type="text"data-name="powerCpuMaxUnits" id="add-power-cpu-max-units" /></td>
                       <td align="right">CPU最小使用量:</td>
                       <td align="left"><input type="text" data-name="powerCpuMinUnits" id="add-power-cpu-min-units"/></td>
                       <td align="right">CPU使用量:</td>
                       <td align="left"><input type="text" data-name="powerCpuUnits" id="add-power-cpu-units"/></td>
                   </tr>
                   <tr>    
                       <td align="right">CPU最大核数:</td>
                       <td align="left"><input type="text" data-name="powerCpuMaxCores" id="add-power-cpu-max-cores" /></td>
                       <td align="right">CPU最小核数:</td>
                       <td align="left"><input type="text" data-name="powerCpuMinCores" id="add-power-cpu-min-cores"/></td>
                       <td align="right">CPU使用核数:</td>
                       <td align="left"><input type="text" data-name="powerCpuCores" id="add-power-cpu-cores"/></td>
                   </tr>
                   <tr>    
                       <td align="right">管理用户名:</td>
                       <td align="left"><input type="text" data-name="user" id="add-vios-user" /></td>
                       <td align="right">管理用户密码:</td>
                       <td align="left"><input type="password" data-name="password" id="add-vios-password"/></td>
                       <td align="right">管理用IP地址:</td>
                       <td align="left"><input type="text" data-name="ip" id="add-vios-ip"/></td>
                   </tr>
               </table>
          	</div>
              <div style="width:100%;text-align:right;margin-top:5px;">
          		  <input style="margin-right: 5px;" onclick="submitAddPvehostViosInfo()" type="button" id="addPvehostViosSave" value="保存" />
              	  <input id="addPvehostViosCancel" type="button" value="取消" />
              </div>
          </div>
   </div>
   
   <!-- 编辑主机VIOS --> 
     <div id="editPveHostViosWindow">
          <div>编辑主机VIOS</div>
          <div id="editPveHostViosForm" style="overflow: hidden;">
          	<input type="hidden" data-name="resViosId" id="edit-host-vios-sid"/>
          	<div style="width:100%;height:95px;">
          		<table border="0" width="100%" cellspacing="5" cellpadding="0">
                   <tr>
                       <td align="right">CPU最大使用量:</td>
                       <td align="left"><input type="text" data-name="powerCpuMaxUnits" id="edit-power-cpu-max-units"/></td>
                       <td align="right">CPU最小使用量:</td>
                       <td align="left"><input type="text" data-name="powerCpuMinUnits" id="edit-power-cpu-min-units"/></td>
                       <td align="right">CPU使用量:</td>
                       <td align="left"><input type="text" data-name="powerCpuUnits" id="edit-power-cpu-units"/></td>
                   </tr>
                   <tr>    
                       <td align="right">CPU最大核数:</td>
                       <td align="left"><input type="text" data-name="powerCpuMaxCores" id="edit-power-cpu-max-cores"/></td>
                       <td align="right">CPU最小核数:</td>
                       <td align="left"><input type="text" data-name="powerCpuMinCores" id="edit-power-cpu-min-cores"/></td>
                       <td align="right">CPU使用核数:</td>
                       <td align="left"><input type="text" data-name="powerCpuCores" id="edit-power-cpu-cores"/></td>
                   </tr>
                   <tr>    
                       <td align="right">管理用户名:</td>
                       <td align="left"><input type="text" data-name="user" id="edit-vios-user" /></td>
                       <td align="right">管理用户密码:</td>
                       <td align="left"><input type="password" data-name="password" id="edit-vios-password"/></td>
                       <td align="right">管理用IP地址:</td>
                       <td align="left"><input type="text" data-name="ip" id="edit-vios-ip"/></td>
                   </tr>
               </table>
          	</div>
              <div style="width:100%;text-align:right;margin-top:5px;">
          		  <input style="margin-right: 5px;" onclick="submitEditPvehostViosInfo()" type="button" id="editPvehostViosSave" value="保存" />
              	  <input id="editPvehostViosCancel" type="button" value="取消" />
              </div>
          </div>
   </div>         
         
  <script type="text/javascript">
	    var hostcpu = new virtualPveHostRelationDatagridModel();
	    hostcpu.initPopWindow();
	    hostcpu.initValidator();
	    
	    $('#add-res_alloc_flag').on('select', function (event){
   		    var args = event.args;
   		    if (args) {
	   		    var item = args.item;
	   		    var value = item.value;
	   		    if(value == "0"){
	   		    	$(".allotZoon").hide();
	   		    }else{
	   		    	$(".allotZoon").show();
	   		    }
	   		}                        
   		});
	    
  </script>