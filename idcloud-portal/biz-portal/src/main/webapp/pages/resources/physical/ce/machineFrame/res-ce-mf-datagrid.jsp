<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style> 
/* table { table-layout:fixed; word-break: break-all; word-wrap: break-word; }  
td {
overflow: hidden;
white-space: nowrap;
text-overflow: ellipsis;
} */
</style> 
<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;刀箱编号：</td>
           			<td><input type="text" id="search-machineFrameName" />&nbsp;&nbsp;</td>
           			<td align="right" nowrap="nowrap">序列号：</td>
           			<td><input type="text" id="search-cabinetframe-serialNumber"></input></td>
           			<td align="right" nowrap="nowrap">机房：</td>
           			<td>
           				<div id="search-mf-room"></div>
           			</td>
           			<td align="right" nowrap="nowrap">机柜：</td>
           			<td>
           				<div id="search-mf-cabinetframe"></div>
           			</td>
           			<td align="right" nowrap="nowrap">机架：</td>
                    <td>
                        <div id="search-mf-rack"></div>
                    </td>
           			<td><a onclick="searchMachineFrame()" id="search-machineFrameButton"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='framedatagrid' style="width:100%;height:450px;"></div> 
    </div>
    
     <!-- 新增机框页面 -->
     <div id="addCfWindow">
            <div>新增刀箱</div>
            <div id="addCfForm" style="overflow: hidden;">
            <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
            	 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" data-name="locationNumber" id="add-Cf-locationNumber"/></td>
                        <td align="right"><font style="color:red">*</font>刀箱编号:</td>
                        <td align="left"><input type="text" data-name="name" id="add-Cf-name"/></td>
                        <td align="right"><font style="color:red">*</font>刀箱类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="add-Cf-equipType"/></td>
                    </tr>
                  <!--   <tr>
                        <td align="right"><font style="color:red">*</font>刀箱类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="add-Cf-equipType"/></td>
                        <td align="right">序列号:</td>
                        <td align="left"><input type="text" data-name="serialNumber" id="add-Cf-serialNumber"/></td>
                    </tr> -->
                    <tr>
                        <td align="right">序列号:</td>
                        <td align="left"><input type="text" data-name="serialNumber" id="add-Cf-serialNumber"/></td>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="add-Cf-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="add-Cf-model"/></td>
                    </tr>
                    <tr>
                        <td align="right">所属机房:</td>
                        <td align="left">
                            <div data-name="equipRoomSid" id="add-Cf-equipRoomSid"></div>
                        </td>
                        <td align="right">所属机柜:</td>
                        <td align="left">
                            <div data-name="equipCabinetSid" id="add-Cf-equipCabinetSid"></div>
                        </td>
                         <td align="right">所属机架:</td>
                        <td align="left">
                            <div data-name="equipRackSid" id="add-Cf-equipRackSid"></div>
                        </td>
                    </tr>
                     <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="add-Cf-resTopologySid"/></td>
                    </tr>
            	</table>
            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
            		<tr>
                        <td align="right" style="width:15%">规格:</td>
                        <td align="left"> <textarea rows="5" data-name="spec" id="add-Cf-spec"></textarea></td>
                    </tr>
                     <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="add-Cf-description"></textarea></td>
                    </tr>
            	</table>
            </fieldset>
            <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
             	<table border="0" width="100%" cellspacing="5" cellpadding="0">
             		 <tr>
                        <td align="right" style="width:15%">远程管理IP1:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp1" id="add-Cf-remoteMgtIp1"/></td>
                        <td align="right">远程管理IP2:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp2" id="add-Cf-remoteMgtIp2"/></td>
                        <td align="right">关联IP地址:</td>
                        <td align="left"><input type="text" data-name="relevanceIp" id="add-Cf-relevanceIp"/></td>
                    </tr>
                    <tr>
                        <td align="right">远程管理用户:</td>
                        <td align="left"><input type="text" data-name="remoteMgtUser" id="add-Cf-remoteMgtUser"/></td>
                        <td align="right">远程管理密码:</td>
                        <td align="left"><input type="password" data-name="remoteMgtPwd" id="add-Cf-remoteMgtPwd"/></td>
                    </tr>
             	</table>
            </fieldset>  
            <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
            		 <tr>
                        <td align="right" style="width:15%">维保厂商:</td>
                        <td align="left"><input type="text" data-name="maintCompany" id="add-Cf-maintCompany"/></td>
                        <td align="right">维保电话:</td>
                        <td align="left"><input type="text" data-name="maintTel" id="add-Cf-maintTel"/></td>
                        <td align="right">购置日期:</td>
                        <td align="left"> <div id='add-Cf-purchaseDate' data-name="purchaseDate"></div></td>
                    </tr>
                    <tr>
                        <td align="right">保修起始日期:</td>
                        <td align="left"> <div id='add-Cf-startdate' data-name="startEndDate"></div></td>
                        <td align="right">保修期限:</td>
                        <td align="left"><input type="text" data-name="warrantyPeriod" id="add-Cf-warrantyPeriod"/></td>
                        <td align="right">设备提供商:</td>
                        <td align="left"><input type="text" data-name="equipSupplier" id="add-Cf-equipSupplier"/></td>
                    </tr>
            	</table>
            </fieldset>
             <table border="0" width="100%" cellspacing="4" cellpadding="0">
               	   <tr>
                       <td align="right" colspan="3" height="32px">
                       		<input style="margin-right: 5px;" onclick="submitAddBladeFrame()" type="button" id="addBladeframeSave" value="确定" />
             				<input id="addBladeframeCancel" type="button" value="取消" />&nbsp;&nbsp;
                       </td>
                   </tr>
		      </table>
            </div>
       </div>
        <!-- 编辑刀箱页面 -->
      <div id="editCfWindow">
            <div>编辑刀箱</div>
            <div id="editCfForm" style="overflow: hidden;">
            <input type="hidden" data-name="equipSid" id="edit-Cf-equidSid"/>
            <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
            	 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" data-name="locationNumber" id="edit-Cf-locationNumber"/></td>
                        <td align="right"><font style="color:red">*</font>刀箱编号:</td>
                        <td align="left"><input type="text" data-name="name" id="edit-Cf-name"/></td>
                        <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="edit-Cf-equipType"/></td>
                    </tr>
              <!--       <tr>
                        <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="edit-Cf-equipType"/></td>
                        <td align="right">序列号:</td>
                        <td align="left"><input type="text" data-name="serialNumber" id="edit-Cf-serialNumber"/></td>
                    </tr> -->
                    <tr>
                        <td align="right">序列号:</td>
                        <td align="left"><input type="text" data-name="serialNumber" id="edit-Cf-serialNumber"/></td>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="edit-Cf-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="edit-Cf-model"/></td>
                    </tr>
                    <tr>
                        <td align="right">所属机房:</td>
                        <td align="left">
                            <div data-name="equipRoomSid" id="edit-Cf-equipRoomSid"></div>
                        </td>
                        <td align="right">所属机柜:</td>
                        <td align="left">
                            <div data-name="equipCabinetSid" id="edit-Cf-equipCabinetSid"></div>
                        </td>
                        <td align="right">所属机架:</td>
                        <td align="left">
                            <div data-name="equipRackSid" id="edit-Cf-equipRackSid"></div>
                        </td>
                    </tr>
                     <tr style="display:none">
                        <td align="right">数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="edit-Cf-resTopologySid"/></td>
                    </tr>
            	</table>
            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
            		<tr>
                        <td align="right" style="width:15%">规格:</td>
                        <td align="left"> <textarea rows="5" data-name="spec" id="edit-Cf-spec"></textarea></td>
                    </tr>
                     <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="edit-Cf-description"></textarea></td>
                    </tr>
            	</table>
            </fieldset>
            <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
             	<table border="0" width="100%" cellspacing="5" cellpadding="0">
             		 <tr>
                        <td align="right" style="width:15%">远程管理IP1:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp1" id="edit-Cf-remoteMgtIp1"/></td>
                        <td align="right">远程管理IP2:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp2" id="edit-Cf-remoteMgtIp2"/></td>
                        <td align="right">关联IP地址:</td>
                        <td align="left"><input type="text" data-name="relevanceIp" id="edit-Cf-relevanceIp"/></td>
                    </tr>
                    <tr>
                        <td align="right">远程管理用户:</td>
                        <td align="left"><input type="text" data-name="remoteMgtUser" id="edit-Cf-remoteMgtUser"/></td>
                        <td align="right">远程管理密码:</td>
                        <td align="left"><input type="password" data-name="remoteMgtPwd" id="edit-Cf-remoteMgtPwd"/></td>
                    </tr>
             	</table>
            </fieldset>  
            <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
            		 <tr>
                        <td align="right" style="width:15%">维保厂商:</td>
                        <td align="left"><input type="text" data-name="maintCompany" id="edit-Cf-maintCompany"/></td>
                        <td align="right">维保电话:</td>
                        <td align="left"><input type="text" data-name="maintTel" id="edit-Cf-maintTel"/></td>
                        <td align="right">购置日期:</td>
                        <td align="left"> <div id='edit-Cf-purchaseDate' data-name="purchaseDate"></div></td>
                    </tr>
                    <tr>
                        <td align="right">保修起始日期:</td>
                        <td align="left"> <div id='edit-Cf-startEndDate' data-name="startEndDate"></div></td>
                        <td align="right">保修期限:</td>
                        <td align="left"><input type="text" data-name="warrantyPeriod" id="edit-Cf-warrantyPeriod"/></td>
                        <td align="right">设备提供商:</td>
                        <td align="left"><input type="text" data-name="equipSupplier" id="edit-Cf-equipSupplier"/></td>
                    </tr>
            	</table>
            </fieldset>
             <table border="0" width="100%" cellspacing="4" cellpadding="0">
             	   <tr>
                      <td align="right" colspan="4">
                      <input style="margin-right: 5px;" onclick='submitEditCf()' type="button" id="cfeditSave" value="保存" />
                      <input id="cfeditCancel" type="button" value="取消" /></td>
                  </tr>
		      </table>
            </div>
       </div>
</div>
<div id="bladeFrameWindows" >
     <div>详情</div>
     <div id="bladeFrameForm" style="overflow: hidden;">
     		 <div class="customPanel" style="width:99%;height:200px;margin-left:0.5%;margin-top:8px;">
		  		    <div class="title">&nbsp;&nbsp;基本信息</div>
			        <div>
			        	  <table border="0" width="100%" cellpadding="0" cellspacing="10" style="table-layout:fixed; word-break: break-all; word-wrap: break-word;">
								<tr>
									<td align="right" style="width:15%">位置编号：</td>
									<td align="left" id="bladeFrame-locationNumber" style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
									<td align="right"style="width:15%">设备编号：</td>
									<td align="left" id="bladeFrame-name" style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
									<td align="right"style="width:15%">设备类型：</td>
                                    <td align="left" id="bladeFrame-equipType" style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
								</tr>
								<tr>
                                    <td align="right"style="width:15%">设备分类：</td>
                                    <td align="left" id="bladeFrame-equipCategoryName"style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
                                    <td align="right"style="width:15%">序列号：</td>
                                    <td align="left" id="bladeFrame-serialNumber"style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
                                    <td align="right"style="width:15%">品牌：</td>
                                    <td align="left" id="bladeFrame-brand" style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
								</tr>
								<tr>
									<td align="right"style="width:15%">型号：</td>
									<td align="left" id="bladeFrame-model" style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
                                    <!-- <td align="right"style="width:15%">所属机柜：</td>
                                    <td align="left" id="bladeFrame-equipCabinetName" style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td> -->
								</tr>
								<tr>
									<td align="right"style="width:15%">所属机房：</td>
                                    <td align="left" id="bladeFrame-equipRoomName" style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
									<td align="right"style="width:15%">所属机柜：</td>
                                    <td align="left" id="bladeFrame-equipCabinetName" style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
                                    <td align="right"style="width:15%">所属机架：</td>
                                    <td align="left" id="bladeFrame-equipRackName" style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
								</tr>
								<tr>
									<td align="right"style="width:15%">规格：</td>
									<td align="left" id="bladeFrame-spec" style="width:50%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
								</tr>
								<tr>
									<td align="right"style="width:15%">描述：</td>
									<td align="left" id="bladeFrame-description" style="width:50%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
								</tr>
						</table>
			        </div>
	  		 </div>
     		<div class="customPanel" style="width:99%;height:110px;margin-left:0.5%;margin-top:8px;">
     				  
				  		    <div class="title">&nbsp;&nbsp;远程管理信息</div>
					        <div>
					        	  <table border="0" width="100%" cellpadding="0" cellspacing="10" style="table-layout:fixed; word-break: break-all; word-wrap: break-word;">
										<tr>
											<td align="right" style="width:15%">远程管理IP1:</td>
											<td align="left" id="bladeFrame-remoteMgtIp1" style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
											<td align="right" style="width:15%">远程管理IP2:</td>
											<td align="left" id="bladeFrame-remoteMgtIp2"style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
											<td align="right" style="width:15%">关联IP地址：</td>
											<td align="left" id="bladeFrame-relevanceIp"style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
										</tr>
										<tr>	
											<td align="right"style="width:15%">远程管理用户：</td>
											<td align="left" id="bladeFrame-remoteMgtUser"style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
											<td align="right"style="width:15%">远程管理密码：</td>
											<td align="left" id="bladeFrame-remoteMgtPwd"style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
										</tr>
								</table>
					        </div>
     		</div>
     		
     		<div class="customPanel" style="width:99%;height:110px;margin-left:0.5%;margin-top:8px;">
		  		    <div class="title">&nbsp;&nbsp;维保信息</div>
			        <div>
			        	  <table border="0" width="100%" cellpadding="0" cellspacing="10" style="table-layout:fixed; word-break: break-all; word-wrap: break-word;">
								<tr>
									<td align="right" style="width:15%">维保厂商：</td>
									<td align="left" id="bladeFrame-maintCompany"style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
									<td align="right" style="width:15%">维保电话：</td>
									<td align="left" id="bladeFrame-maintTel"style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
									<td align="right" >购置日期：</td>
									<td align="left" id="bladeFrame-purchaseDate"style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
								</tr>
								<tr>	
									<td align="right"style="width:15%">保修起始日期：</td>
									<td align="left" id="bladeFrame-startEndDate"style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
									<td align="right"style="width:15%">保修期限：</td>
									<td align="left" id="bladeFrame-warrantyPeriod"style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
									<td align="right"style="width:15%">设备提供商：</td>
									<td align="left" id="bladeFrame-equipSupplier"style="width:20%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
								</tr>
						  </table>
			        </div>
     		</div>
	        <div style="width:99%;height:40px;margin-left:0.5%;margin-top:8px;text-align:right;">
	        	  <br/>
     			  <input type="button" id="bladefarme-close-button" value="关闭" />
     		</div>
     </div>
</div> 

<script type="text/javascript">

 	function machineFrameDatagridModel(){
		var mf = new machineFrameDatagridModels();
		 // 初始化页面组件
		 mf.initMachineFrameInput();
		 // 初始化datagrid
		 mf.initMachineFrameDatagrid();
		 mf.initComboxLinkage();
		 // 为datagrid赋值		 
		 mf.initPopWindow();
		 mf.initValue();
    } 
	 
</script>
