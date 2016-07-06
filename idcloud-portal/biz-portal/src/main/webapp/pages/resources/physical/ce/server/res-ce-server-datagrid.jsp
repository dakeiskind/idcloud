<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<style type="text/css">
			
</style>
<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;设备编号：</td>
           			<td><input type="text" id="search-serverName" />&nbsp;&nbsp;</td>
                    <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;序列号：</td>
                    <td><input type="text" id="search-serverName-serialNumber" />&nbsp;&nbsp;</td>
           			<td align="right" nowrap="nowrap">机房：</td>
           			<td>
           				<div id="search-server-room"></div>
           			</td>
                    <td align="right" nowrap="nowrap">机柜：</td>
                    <td>
                        <div id="search-server-cabinet"></div>
                    </td>
                    <td align="right" nowrap="nowrap">机架：</td>
                    <td>
                        <div id="search-server-rack"></div>
                    </td>
           			<td><a onclick="searchServer()" id="search-serverButton"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='serverdatagrid' style="width:100%;height:450px;"></div> 
    </div>
	<!-- 新增服务器页面 -->
	<div id="addphyHostWindow">
            <div>新增服务器设备</div>
            <div id="addphyHostForm">
            <fieldset style="border-color:#FCFCFC"><legend><b>设备分类</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">选择主机设备：</td>
                        <td>
                            <div id="search-phyhost-choose"></div>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <div id="add-phyhost-x86">
                 <fieldset style="border-color:#FCFCFC"><legend><b>配置信息</b></legend>
                    <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%"><font style="color:red">*</font>主机名称:</td>
                        <td align="left" ><input type="text" id="add-phyhost-hostName"/></td>
                        <td align="right" style="width:15%"><font style="color:red">*</font>主机IP:</td>
                        <td align="left"><input type="text" id="add-phyhost-hostIp"/></td>
                        <td align="right" nowrap="nowrap">操作系统：</td>
                        <td>
                            <div id="add-phyhost-hostOsType"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="width:15%"><font style="color:red">*</font>CPU个数:</td>
                        <td align="left"><input type="text" id="add-phyhost-cpuNumber"/></td>
                        <td align="right"><font style="color:red">*</font>CPU核数:</td>
                        <td align="left"><input type="text" id="add-phyhost-cpuCores"/></td>
                        <td align="right" nowrap="nowrap">CPU类型：</td>
                        <td align="left"><input type="text" id="add-phyhost-cpuType"/></td>
                       <!--  <td>
                            <div id="add-phyhost-cpuType"></div>
                        </td> -->
                    </tr>
                    <tr>
                        <td align="right" style="width:15%"><font style="color:red">*</font>内存大小(MB):</td>
                        <td align="left"><input type="text" id="add-phyhost-memorySize"/></td>
                        <td align="right">主机用户名:</td>
                        <td align="left"><input type="text" id="add-phyhost-managementUser"/></td>
                        <td align="right" nowrap="nowrap">主机密码：</td>
                        <td align="left"><input type="password" id="add-phyhost-managementPwd"/></td>
                    </tr>
                 </table>
                </fieldset>
                <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
                 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%"><font style="color:red">*</font>主机分类:</td>
                        <td align="left">
                            <div id="add-phyhost-equipCategory"></div>
                        </td>
                        <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" id="add-phyhost-equipType"/></td>   
                        <td align="right" style="width:15%"><font style="color:red">*</font>设备编号:</td>
                        <td align="left"><input type="text" id="add-phyhost-name"/></td>
                    </tr>
                     <tr>
                        <td align="right" style="width:15%">品牌:</td>
                        <td align="left"><input type="text" id="add-phyhost-brand"/></td>
                        <td align="right" style="width:15%">型号:</td>
                        <td align="left"><input type="text" id="add-phyhost-model"/></td>
                        <td align="right">序列号:</td>
                        <td align="left"><input type="text" id="add-phyhost-serialNumber"/></td> 
                    </tr>
                     <tr>
                        <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" id="add-phyhost-locationNumber"/></td>
                    </tr> 
                    <tr>
                        <td align="right" style="width:15%">所属机房:</td>
                        <td align="left">
                            <div id="add-phyhost-equipRoomSid"></div>
                        </td>
                        <td align="right" style="width:15%">所属机柜:</td>
                        <td align="left">
                            <div id="add-phyhost-equipCabinetSid"></div>
                        </td>
                         <td align="right" style="width:15%">所属机架:</td>
                        <td align="left">
                            <div id="add-phyhost-equipRackSid"></div>
                        </td>
                    </tr>
                    <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" id="add-phyhost-resTopologySid"/></td>
                    </tr> 
                </table>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:9.5%">规格:</td>
                        <td align="left" colspan="3"> <textarea rows="5" id="add-phyhost-spec"></textarea></td>
                    </tr>
                     <tr>
                        <td align="right">描述:</td>
                        <td align="left" colspan="3"> <textarea rows="5" id="add-phyhost-description"></textarea></td>
                    </tr>
                </table>
                </fieldset>
                <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:18%">管理IP1:</td>
                        <td align="left"><input type="text" id="add-phyhost-remoteMgtIp1"/></td>
                        <td align="right" style="width:18%">管理IP2:</td>
                        <td align="left"><input type="text" id="add-phyhost-remoteMgtIp2"/></td>
                        <td align="right"  style="width:18%">关联IP:</td>
                        <td align="left"><input type="text" id="add-phyhost-relevanceIp"/></td>
                    </tr>
                    <tr>
                        <td align="right">管理用户:</td>
                        <td align="left"><input type="text" id="add-phyhost-remoteMgtUser"/></td>
                        <td align="right">管理密码:</td>
                        <td align="left"><input type="password" id="add-phyhost-remoteMgtPwd"/></td>
                    </tr>
                </table>
                </fieldset>  
                <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:10%">维保厂商:</td>
                        <td align="left"><input type="text" id="add-phyhost-maintCompany"/></td>
                        <td align="right">维保电话:</td>
                        <td align="left"><input type="text" id="add-phyhost-maintTel"/></td>
                        <td align="right">购置日期:</td>
                        <td align="left"> <div id='add-phyhost-purchaseDate'></div></td>
                    </tr>
                    <tr>
                        <td align="right">保修期限:</td>
                        <td align="left"><input type="text" id="add-phyhost-warrantyPeriod"/></td>
                        <td align="right">保修起始日期:</td>
                        <td align="left"> <div id='add-phyhost-startdate'></div></td>
                        <td align="right">设备提供商:</td>
                        <td align="left"><input type="text" id="add-phyhost-equipSupplier"/></td>
                    </tr>
                </table>
                </fieldset>
                </div>  
                <!-- <div id="add-phyhost-ibm" style="display:none"> -->
            <div id="add-phyhost-ibm">
                 <fieldset style="border-color:#FCFCFC"><legend><b>配置信息</b></legend>
                 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%"><font style="color:red">*</font>主机名称:</td>
                        <td align="left" ><input type="text" id="add-phyIbmhost-hostName"/></td>
                        <td align="right" style="width:15%"><font style="color:red">*</font>主机IP:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-hostIp"/></td>
                        <td align="right" nowrap="nowrap">操作系统：</td>
                        <td>
                            <div id="add-phyIbmhost-hostOsType"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="width:15%"><font style="color:red">*</font>CPU个数:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-cpuNumber"/></td>
                       <!--  <td align="right">CPU核数:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-cpuCores"/></td> -->
                        <td align="right" nowrap="nowrap">CPU类型：</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-cpuType"/></td>
                       <!--  <td>
                            <div id="add-phyIbmhost-cpuType"></div>
                        </td> -->
                        <td align="right" style="width:15%"><font style="color:red">*</font>内存大小(MB):</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-memorySize"/></td>
                    </tr>
                   <!--  <tr>
                       <td align="right" style="width:15%">内存大小(MB):</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-memorySize"/></td>
                    </tr>  -->
                    <tr>
                        <td align="right" style="width:15%">主机用户名:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-managementUser"/></td>
                        <td align="right" nowrap="nowrap">主机密码：</td>
                        <td align="left"><input type="password" id="add-phyIbmhost-managementPwd"/></td>
                        <td align="right" nowrap="nowrap">是否虚拟化：</td>
                        <td>
                            <div id="add-phyIbmhost-isVios"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="width:15%">VIOS用户名:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-viosUser"/></td>
                        <td align="right" nowrap="nowrap">VIOS密码：</td>
                        <td align="left"><input type="password" id="add-phyIbmhost-viosPwd"/></td>
                    </tr>
                 </table>
            </fieldset>
            <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
                 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%"><font style="color:red">*</font>主机分类:</td>
                        <td align="left">
                            <div id="add-phyIbmhost-equipCategory"></div>
                        </td>
                        <td align="right" style="width:15%"><font style="color:red">*</font>设备编号:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-name"/></td>
                        <td align="right">序列号:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-serialNumber"/></td> 
                    </tr>
                     <tr>
                        <td align="right" style="width:15%">品牌:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-brand"/></td>
                        <td align="right" style="width:15%">型号:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-model"/></td>
                        <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-locationNumber"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-equipType"/></td>   
                    </tr>
                    <tr>
                        <td align="right" style="width:15%">所属机房:</td>
                        <td align="left">
                            <div id="add-phyIbmhost-equipRoomSid"></div>
                        </td>
                        <td align="right" style="width:15%">所属机柜:</td>
                        <td align="left">
                            <div id="add-phyIbmhost-equipCabinetSid"></div>
                        </td>
                         <td align="right" style="width:15%">所属机架:</td>
                        <td align="left">
                            <div id="add-phyIbmhost-equipRackSid"></div>
                        </td>
                    </tr>
                     <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" id="add-phyIbmhost-resTopologySid"/></td>
                    </tr>
                </table>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:9.5%">规格:</td>
                        <td align="left" colspan="3"> <textarea rows="5" id="add-phyIbmhost-spec"></textarea></td>
                    </tr>
                     <tr>
                        <td align="right">描述:</td>
                        <td align="left" colspan="3"> <textarea rows="5" id="add-phyIbmhost-description"></textarea></td>
                    </tr>
                </table>
            </fieldset>
            <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:18%">远程管理IP1:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-remoteMgtIp1"/></td>
                        <td align="right" style="width:18%">远程管理IP2:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-remoteMgtIp2"/></td>
                        <td align="right"  style="width:18%">关联IP地址:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-relevanceIp"/></td>
                    </tr>
                    <tr>
                        <td align="right">管理用户:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-remoteMgtUser"/></td>
                        <td align="right">管理密码:</td>
                        <td align="left"><input type="password" id="add-phyIbmhost-remoteMgtPwd"/></td>
                    </tr>
                </table>
            </fieldset>  
            <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:10%">维保厂商:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-maintCompany"/></td>
                        <td align="right">维保电话:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-maintTel"/></td>
                        <td align="right">购置日期:</td>
                        <td align="left"> <div id='add-phyIbmhost-purchaseDate'></div></td>
                    </tr>
                    <tr>
                        <td align="right">保修期限:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-warrantyPeriod"/></td>
                        <td align="right">保修起始日期:</td>
                        <td align="left"> <div id='add-phyIbmhost-startdate'></div></td>
                        <td align="right">设备提供商:</td>
                        <td align="left"><input type="text" id="add-phyIbmhost-equipSupplier"/></td>
                    </tr>
                </table>
            </fieldset>
            </div>  
           
             <table border="0" width="100%" cellspacing="4" cellpadding="0">
                   <tr>
                       <td align="right" colspan="3" height="32px">
                            <input style="margin-right: 5px;" onclick="submitAddPhyHost()" type="button" id="addPhyHostSave" value="确定" />
                            <input id="addPhyHostCancel" type="button" value="取消" />&nbsp;&nbsp;
                       </td>
                   </tr>
             </table>
           </div>
    </div> 

       	<!-- 编辑服务器页面 -->
	   <div id="editphyHostWindow">
            <div>编辑服务器设备</div>
            <div id="editphyHostFrom">
            <div id="edit-phyhost-x86">
                 <fieldset style="border-color:#FCFCFC"><legend><b>配置信息</b></legend>
                    <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">主机名称:</td>
                        <td align="left" ><input type="text" id="edit-phyhost-hostName"/></td>
                        <td align="right" style="width:15%">主机IP:</td>
                        <td align="left"><input type="text" id="edit-phyhost-hostIp"/></td>
                        <td align="right" nowrap="nowrap">操作系统：</td>
                        <td>
                            <div id="edit-phyhost-hostOsType"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="width:15%">CPU个数:</td>
                        <td align="left"><input type="text" id="edit-phyhost-cpuNumber"/></td>
                        <td align="right">CPU核数:</td>
                        <td align="left"><input type="text" id="edit-phyhost-cpuCores"/></td>
                        <td align="right" nowrap="nowrap">CPU类型：</td>
                        <td align="left"><input type="text" id="edit-phyhost-cpuType"/></td>
                        <!-- <td>
                            <div id="edit-phyhost-cpuType"></div>
                        </td> -->
                    </tr>
                    <tr>
                        <td align="right" style="width:15%">内存大小(MB):</td>
                        <td align="left"><input type="text" id="edit-phyhost-memorySize"/></td>
                        <td align="right">主机用户名:</td>
                        <td align="left"><input type="text" id="edit-phyhost-managementUser"/></td>
                        <td align="right" nowrap="nowrap">主机密码：</td>
                        <td align="left"><input type="password" id="edit-phyhost-managementPwd"/></td>
                    </tr>
                 </table>
                </fieldset>
                <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
                 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">主机分类:</td>
                        <td align="left">
                            <div id="edit-phyhost-equipCategory"></div>
                        </td>
                        <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" id="edit-phyhost-equipType"/></td>   
                        <td align="right" style="width:15%"><font style="color:red">*</font>设备编号:</td>
                        <td align="left"><input type="text" id="edit-phyhost-name"/></td>
                    </tr>
                     <tr>
                        <td align="right" style="width:15%">品牌:</td>
                        <td align="left"><input type="text"id="edit-phyhost-brand"/></td>
                        <td align="right" style="width:15%">型号:</td>
                        <td align="left"><input type="text" id="edit-phyhost-model"/></td>
                        <td align="right">序列号:</td>
                        <td align="left"><input type="text" id="edit-phyhost-serialNumber"/></td> 
                    </tr>
                     <tr>
                        <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" id="edit-phyhost-locationNumber"/></td>
                    </tr> 
                    <tr>
                        <td align="right" style="width:15%">所属机房:</td>
                        <td align="left">
                            <div id="edit-phyhost-equipRoomSid"></div>
                        </td>
                        <td align="right" style="width:15%">所属机柜:</td>
                        <td align="left">
                            <div id="edit-phyhost-equipCabinetSid"></div>
                        </td>
                         <td align="right" style="width:15%">所属机架:</td>
                        <td align="left">
                            <div id="edit-phyhost-equipRackSid"></div>
                        </td>
                    </tr>
                    <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" id="edit-phyhost-resTopologySid"/></td>
                    </tr> 
                </table>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:9.5%">规格:</td>
                        <td align="left" colspan="3"> <textarea rows="5" id="edit-phyhost-spec"></textarea></td>
                    </tr>
                     <tr>
                        <td align="right">描述:</td>
                        <td align="left" colspan="3"> <textarea rows="5" id="edit-phyhost-description" ></textarea></td>
                    </tr>
                </table>
                </fieldset>
                <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:18%">管理IP1:</td>
                        <td align="left"><input type="text" id="edit-phyhost-remoteMgtIp1"/></td>
                        <td align="right" style="width:18%">管理IP2:</td>
                        <td align="left"><input type="text" id="edit-phyhost-remoteMgtIp2"/></td>
                        <td align="right"  style="width:18%">关联IP:</td>
                        <td align="left"><input type="text" id="edit-phyhost-relevanceIp"/></td>
                    </tr>
                    <tr>
                        <td align="right">管理用户:</td>
                        <td align="left"><input type="text" id="edit-phyhost-remoteMgtUser"/></td>
                        <td align="right">管理密码:</td>
                        <td align="left"><input type="password" id="edit-phyhost-remoteMgtPwd"/></td>
                    </tr>
                </table>
                </fieldset>  
                <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:10%">维保厂商:</td>
                        <td align="left"><input type="text" id="edit-phyhost-maintCompany"/></td>
                        <td align="right">维保电话:</td>
                        <td align="left"><input type="text" id="edit-phyhost-maintTel"/></td>
                        <td align="right">购置日期:</td>
                        <td align="left"> <div id='edit-phyhost-purchaseDate'></div></td>
                    </tr>
                    <tr>
                        <td align="right">保修期限:</td>
                        <td align="left"><input type="text" id="edit-phyhost-warrantyPeriod"/></td>
                        <td align="right">保修起始日期:</td>
                        <td align="left"> <div id='edit-phyhost-startEndDate'></div></td>
                        <td align="right">设备提供商:</td>
                        <td align="left"><input type="text" id="edit-phyhost-equipSupplier"/></td>
                    </tr>
                </table>
                </fieldset>
                </div>  
                
                <div id="edit-phyhost-ibm" style="display: none">
                 <fieldset style="border-color:#FCFCFC"><legend><b>配置信息</b></legend>
                    <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">主机名称:</td>
                        <td align="left" ><input type="text" id="edit-phyIbmhost-hostName"/></td>
                        <td align="right" style="width:15%">主机IP:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-hostIp"/></td>
                        <td align="right" nowrap="nowrap">操作系统：</td>
                        <td>
                            <div id="edit-phyIbmhost-hostOsType"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="width:15%">CPU个数:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-cpuNumber"/></td>
                        <td align="right" nowrap="nowrap">CPU类型：</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-cpuType"/></td>
                       <!--  <td>
                            <div id="edit-phyIbmhost-cpuNumber"></div>
                        </td> -->
                        <td align="right" style="width:15%">内存大小(MB):</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-memorySize"/></td>
                    </tr>
                    <tr>
                        <td align="right" style="width:15%">主机用户名:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-managementUser"/></td>
                        <td align="right" nowrap="nowrap">主机密码：</td>
                        <td align="left"><input type="password" id="edit-phyIbmhost-managementPwd"/></td>
                        <td align="right" nowrap="nowrap">是否虚拟化：</td>
                        <td>
                            <div id="edit-phyIbmhost-isVios"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="width:15%">VIOS用户名:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-viosUser"/></td>
                        <td align="right" nowrap="nowrap">VIOS密码：</td>
                        <td align="left"><input type="password" id="edit-phyIbmhost-viosPwd"/></td>
                    </tr>
                 </table>
                </fieldset>
                <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
                 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">主机分类:</td>
                        <td align="left">
                            <div id="edit-phyIbmhost-equipCategory"></div>
                        </td>
                        <!-- <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-equipType"/></td>    -->
                        <td align="right" ><font style="color:red">*</font>设备编号:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-name"/></td>
                        <td align="right" style="width:15%">序列号:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-serialNumber"/></td> 
                    </tr>
                     <tr>
                        <td align="right" style="width:15%">品牌:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-brand"/></td>
                        <td align="right" style="width:15%">型号:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-model"/></td>
                        <!-- <td align="right">序列号:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-serialNumber"/></td>  -->
                         <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-locationNumber"/></td>
                    </tr>
                     <tr>
                        <!--  <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-locationNumber"/></td> -->
                        <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-equipType"/></td>
                    </tr> 
                    <tr>
                        <td align="right" style="width:15%">所属机房:</td>
                        <td align="left">
                            <div id="edit-phyIbmhost-equipRoomSid"></div>
                        </td>
                        <td align="right" style="width:15%">所属机柜:</td>
                        <td align="left">
                            <div id="edit-phyIbmhost-equipCabinetSid"></div>
                        </td>
                         <td align="right" style="width:15%">所属机架:</td>
                        <td align="left">
                            <div id="edit-phyIbmhost-equipRackSid"></div>
                        </td>
                    </tr>
                    <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" id="edit-phyIbmhost-resTopologySid"/></td>
                    </tr> 
                </table>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:9.5%">规格:</td>
                        <td align="left" colspan="3"> <textarea rows="5" id="edit-phyIbmhost-spec"></textarea></td>
                    </tr>
                     <tr>
                        <td align="right">描述:</td>
                        <td align="left" colspan="3"> <textarea rows="5" id="edit-phyIbmhost-description"></textarea></td>
                    </tr>
                </table>
                </fieldset>
                <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:18%">管理IP1:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-remoteMgtIp1"/></td>
                        <td align="right" style="width:18%">管理IP2:</td>
                        <td align="left"><input type="text"  id="edit-phyIbmhost-remoteMgtIp2"/></td>
                        <td align="right"  style="width:18%">关联IP:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-relevanceIp"/></td>
                    </tr>
                    <tr>
                        <td align="right">管理用户:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-remoteMgtUser"/></td>
                        <td align="right">管理密码:</td>
                        <td align="left"><input type="password" id="edit-phyIbmhost-remoteMgtPwd"/></td>
                    </tr>
                </table>
                </fieldset>  
                <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:10%">维保厂商:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-maintCompany"/></td>
                        <td align="right">维保电话:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-maintTel"/></td>
                        <td align="right">购置日期:</td>
                        <td align="left"> <div id='edit-phyIbmhost-purchaseDate'></div></td>
                    </tr>
                    <tr>
                        <td align="right">保修期限:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-warrantyPeriod"/></td>
                        <td align="right">保修起始日期:</td>
                        <td align="left"> <div id='edit-phyIbmhost-startEndDate'></div></td>
                        <td align="right">设备提供商:</td>
                        <td align="left"><input type="text" id="edit-phyIbmhost-equipSupplier"/></td>
                    </tr>
                </table>
                </fieldset>
                </div> 
                 <table border="0" width="100%" cellspacing="4" cellpadding="0">
                       <tr>
                           <td align="right" colspan="3" height="32px">
                                <input style="margin-right: 5px;" onclick="submitEditPhyHost()" type="button" id="editPhyHostSave" value="确定" />
                                <input id="editPhyHostCancel" type="button" value="取消" />&nbsp;&nbsp;
                           </td>
                       </tr>
                </table> 
                
            </div>
       </div> 
</div>

<!-- <div id="x86ServerWindows">
     <div>详情</div>
     <div id="x86ServerForm" style="overflow: hidden;">
             <div class="customPanel" style="width:99%;height:110px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;配置信息</div>
                    <div>
                        <table border="0" width="100%" cellspacing="5" cellpadding="0">
                            <tr>
                                <td align="right" style="width:15%">主机名称:</td>
                                <td align="left" style="width:15%"id="hostName"></td>
                                <td align="right" style="width:15%">主机IP:</td>
                                <td align="left" style="width:15%"id="hostIp"/></td>
                                <td align="right" style="width:15%">操作系统：</td>
                                <td align="left"style="width:15%"id="equipHostOsType"></td>
                            </tr>
                            <tr>
                                <td align="right" style="width:15%">CPU个数:</td>
                                <td align="left" style="width:15%"id="cpuNumber"/></td>
                                <td align="right" style="width:15%">CPU核数:</td>
                                <td align="left" style="width:15%"id="cpuCores"/></td>
                                <td align="right" style="width:15%">CPU类型：</td>
                                <td align="left" style="width:15%"id="cpuType"/></td>
                            </tr>
                            <tr>
                                <td align="right" style="width:15%">内存大小(MB):</td>
                                <td align="left" style="width:15%"id="memorySize"/></td>
                                <td align="right" style="width:15%">主机用户名:</td>
                                <td align="left" style="width:15%"id="managementUser"/></td>
                                <td align="right" style="width:15%">主机密码：</td>
                                <td align="left" style="width:15%"id="managementPwd"/></td>
                            </tr>
                     </table>
                </div>
                 <div class="customPanel" style="width:99%;height:180px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;基本信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" style="width:15%">位置编号：</td>
                                    <td align="left" style="width:15%"id="locationNumber"></td>
                                    <td align="right"  style="width:15%">设备编号：</td>
                                    <td align="left" style="width:15%"id="name"></td>
                                    <td align="right">设备类型：</td>
                                    <td align="left" style="width:15%"id="equipType"></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">设备分类：</td>
                                    <td align="left" style="width:15%"id="equipCategoryName"></td>
                                    <td align="right"style="width:15%">序列号：</td>
                                    <td align="left" style="width:15%"id="serialNumber"></td>
                                    <td align="right">品牌：</td>
                                    <td align="left" style="width:15%"id="brand" ></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">型号：</td>
                                    <td align="left"style="width:15%"id="model"></td>
                                    <td align="right">所属机架：</td>
                                    <td align="left" style="width:15%"id="equipCabinetName" ></td>
                                </tr>
                                  <tr>
                                    <td align="right">所属机房：</td>
                                    <td align="left" style="width:15%"id="roomName" ></td>
                                    <td align="right">所属机柜：</td>
                                    <td align="left" style="width:15%"id="cabinetName" ></td>
                                    <td align="right">所属机架：</td>
                                    <td align="left" style="width:15%"id="rackName" ></td>
                                </tr>
                                <tr>
                                    <td align="right" colspan="1">规格：</td>
                                    <td align="left" colspan="7"id="spec" ></td>
                                </tr>
                                <tr>
                                    <td align="right" colspan="1">描述：</td>
                                    <td align="left" colspan="7"id="description" ></td>
                                </tr>
                        </table>
                    </div>
             </div>
            <div class="customPanel" style="width:99%;height:110px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;远程管理信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" style="width:15%">远程管理IP1:</td>
                                    <td align="left" style="width:15%"id="remoteMgtIp1"></td>
                                    <td align="right"  style="width:15%">远程管理IP2:</td>
                                    <td align="left" style="width:15%"id="remoteMgtIp2"></td>
                                    <td align="right"  style="width:15%">关联IP地址：</td>
                                    <td align="left" style="width:15%"id="relevanceIp"></td>
                                </tr>
                                <tr>    
                                    <td align="right"style="width:15%">远程管理用户：</td>
                                    <td align="left" style="width:15%"id="remoteMgtUser"></td>
                                    <td align="right"style="width:15%">远程管理密码：</td>
                                    <td align="left" style="width:15%"id="remoteMgtPwd"></td>
                                </tr>
                        </table>
                    </div>
            </div>
            
            <div class="customPanel" style="width:99%;height:110px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;维保信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" style="width:15%">维保厂商：</td>
                                    <td align="left" style="width:15%"id="maintCompany"></td>
                                    <td align="right"style="width:15%">维保电话：</td>
                                    <td align="left" style="width:15%"id="maintTel"></td>
                                    <td align="right" style="width:15%">购置日期：</td>
                                    <td align="left" style="width:15%"id="purchaseDate"></td>
                                </tr>
                                <tr>    
                                    <td align="right"style="width:15%">保修起始日期：</td>
                                    <td align="left" style="width:15%"id="startEndDate"></td>
                                    <td align="right"style="width:15%">保修期限：</td>
                                    <td align="left" style="width:15%"id="warrantyPeriod"></td>
                                    <td align="right"style="width:15%">设备提供商：</td>
                                    <td align="left" style="width:15%"id="equipSupplier"></td>
                                </tr>
                          </table>
                    </div>
            </div>
            <div style="width:99%;height:40px;margin-left:0.5%;margin-top:5px;text-align:right;">
                  <br/>
                  <input type="button" id="serverx86-close-button" value="关闭" />
            </div>
     </div>
</div>
</div> -->
<!-- <div id="ibmServerWindows">
     <div>详情</div>
     <div id="ibmServerForm" style="overflow: hidden;">
             <div class="customPanel" style="width:99%;height:150px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;配置信息</div>
                    <div>
                        <table border="0" width="100%" cellspacing="5" cellpadding="0">
                            <tr>
                                <td align="right" style="width:15%">主机名称:</td>
                                <td align="left"style="width:15%"id="ibm-hostName"></td>
                                <td align="right" style="width:15%">主机IP:</td>
                                <td align="left" style="width:15%"id="ibm-hostIp"/></td>
                                <td align="right" style="width:15%">操作系统：</td>
                                <td align="left"style="width:15%"id="ibm-equipHostOsType"></td>
                            </tr>
                            <tr>
                                <td align="right" style="width:15%">CPU个数:</td>
                                <td align="left"style="width:15%"id="ibm-cpuNumber"/></td>
                                <td align="right">CPU核数:</td>
                                <td align="left" id="cpuCores"/></td>
                                <td align="right">CPU类型：</td>
                                <td align="left" style="width:15%"id="ibm-cpuType"/></td>
                                <td align="right" style="width:15%">内存大小(MB):</td>
                                <td align="left" style="width:15%"id="ibm-memorySize"/></td>
                            </tr>
                            <tr>
                                <td align="right" style="width:15%">内存大小(MB):</td>
                                <td align="left" id="memorySize"/></td>
                                <td align="right">主机用户名:</td>
                                <td align="left" style="width:15%"id="ibm-managementUser"/></td>
                                <td align="right" style="width:15%">主机密码：</td>
                                <td align="left" width="85px"id="ibm-managementPwd"/></td>
                                <td align="right" style="width:15%"> 是否虚拟化：</td>
                                <td align="left" width="85px"id="ibm-isVios"/></td>
                            </tr>
                             <tr>
                                <td align="right" style="width:15%">VIOS用户名：</td>
                                <td align="left" width="85px"id="ibm-viosUser"/></td>
                                <td align="right" style="width:15%">VIOS密码：</td>
                                <td align="left"style="width:15%" id="ibm-viosPwd"/></td>
                            </tr>
                     </table>
                </div>
                <div class="customPanel" style="width:99%;height:190px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;基本信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" style="width:15%">位置编号：</td>
                                    <td align="left" id="ibm-locationNumber"></td>
                                    <td align="right"  style="width:15%">设备编号：</td>
                                    <td align="left" style="width:15%"id="ibm-name"></td>
                                    <td align="right" style="width:15%">设备类型：</td>
                                    <td align="left" style="width:15%"id="ibm-equipType"></td>
                                </tr>
                                <tr>
                                    <td align="right">设备分类：</td>
                                    <td align="left" style="width:15%"id="ibm-equipCategoryName"></td>
                                    <td align="right">序列号：</td>
                                    <td align="left" style="width:15%"id="ibm-serialNumber"></td>
                                    <td align="right">品牌：</td>
                                    <td align="left" style="width:15%"id="ibm-brand" ></td>
                                </tr>
                                <tr>
                                    <td align="right"style="width:15%">型号：</td>
                                    <td align="left" style="width:15%"id="ibm-model"></td>
                                    <td align="left"style="width:15%">所属机架：</td>
                                    <td align="right" style="width:15%"id="equipCabinetName" ></td>
                                </tr>
                                <tr>
                                    <td align="right"style="width:15%">所属机房：</td>
                                    <td align="left" style="width:15%"id="ibm-roomName" ></td>
                                    <td align="right">所属机柜：</td>
                                    <td align="left" style="width:15%"id="ibm-cabinetName" ></td>
                                    <td align="right">所属机架：</td>
                                    <td align="left" style="width:15%"id="ibm-rackName" ></td>
                                </tr>
                                <tr>
                                    <td align="right" colspan="1">规格：</td>
                                    <td align="left" colspan="7"id="ibm-spec" ></td>
                                </tr>
                                <tr>
                                    <td align="right" colspan="1">描述：</td>
                                    <td align="left" colspan="7"id="ibm-description" ></td>
                                </tr>
                        </table>
                    </div>
             </div>
            <div class="customPanel" style="width:99%;height:90px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;远程管理信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" style="width:15%">远程管理IP1:</td>
                                    <td align="left" style="width:15%"id="ibm-remoteMgtIp1"></td>
                                    <td align="right"  style="width:15%">远程管理IP2:</td>
                                    <td align="left" style="width:15%"id="ibm-remoteMgtIp2"></td>
                                    <td align="right"  style="width:15%">关联IP地址：</td>
                                    <td align="left" style="width:15%"id="ibm-relevanceIp"></td>
                                </tr>
                                <tr>    
                                    <td align="right"style="width:15%">远程管理用户：</td>
                                    <td align="left" style="width:15%"id="ibm-remoteMgtUser"></td>
                                    <td align="right"style="width:15%">远程管理密码：</td>
                                    <td align="left" style="width:15%"id="ibm-remoteMgtPwd"></td>
                                </tr>
                        </table>
                    </div>
            </div>
            
            <div class="customPanel" style="width:99%;height:90px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;维保信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" style="width:15%">维保厂商：</td>
                                    <td align="left" style="width:15%"id="ibm-maintCompany"></td>
                                    <td align="right" style="width:15%">维保电话：</td>
                                    <td align="left" style="width:15%"id="ibm-maintTel"></td>
                                    <td align="right" style="width:15%">购置日期：</td>
                                    <td align="left" style="width:15%"id="ibm-purchaseDate"></td>
                                </tr>
                                <tr>    
                                    <td align="right"style="width:15%">保修起始日期：</td>
                                    <td align="left" style="width:15%"id="ibm-startEndDate"></td>
                                    <td align="right"style="width:15%">保修期限：</td>
                                    <td align="left" style="width:15%"id="ibm-warrantyPeriod"></td>
                                    <td align="right"style="width:15%">设备提供商：</td>
                                    <td align="left" style="width:15%"id="ibm-equipSupplier"></td>
                                </tr>
                          </table>
                    </div>
            </div>
            <div style="width:99%;height:40px;margin-left:0.5%;margin-top:5px;text-align:right;">
                  <br/>
                  <input type="button" id="serveribm-close-button" value="关闭" />
            </div>
     </div>
</div>
</div> -->

<script type="text/javascript">
//初始化sys-index页面model
	function serverDatagridModel(){
		var server = new serverDatagridModels();
		 // 初始化页面组件
		 server.initServerInput();
		 // 初始化datagrid
		 server.initServerDatagrid();
		 // 为datagrid赋值
		// server.searchServersInfo();
		 server.initPopWindow();
		 server.initComboxLinkage();
		 server.initValue();
	
    }
	 
</script>
