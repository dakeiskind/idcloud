<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;防火墙编号：</td>
           			<td><input type="text" id="search-firewall-name" />&nbsp;&nbsp;</td>
                    <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;序列号：</td>
                    <td><input type="text" id="search-firewall-serialNumber" />&nbsp;&nbsp;</td>
           			<td align="right" nowrap="nowrap">机架：</td>
           			<td>
           				<div id="search-firewall-rack"></div>
           			</td>
           			
           			<td><a onclick="searchFirewall()" id="search-firewallbutton"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='firewalldatagrid' style="width:100%;height:450px;"></div> 
    </div>
    
        <!-- 新增防火墙页面 -->
     <div id="addfirewallWindow">
            <div>新增防火墙设备</div>
            <div id="addfirewallForm" style="overflow: hidden;">
            <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
                 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" data-name="locationNumber" id="add-firewall-locationNumber"/></td>
                        <td align="right"><font style="color:red">*</font>防火墙编号:</td>
                        <td align="left"><input type="text" data-name="name" id="add-firewall-name"/></td>
                        <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="add-firewall-equipType"/></td>
                    </tr>
                    <tr>
                        <!-- <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="add-firewall-equipType"/></td> -->
                        <td align="right" style="width:15%">序列号:</td>
                        <td align="left"><input type="text" data-name="serialNumber" id="add-firewall-serialNumber"/></td>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="add-firewall-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="add-firewall-model"/></td>
                    </tr>
                   <!--  <tr>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="add-firewall-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="add-firewall-model"/></td>
                    </tr> -->
                    <tr>
                        <td align="right" style="width:15%">所属机房:</td>
                        <td align="left">
                            <div data-name="equipRoomSid" id="add-firewall-equipRoomSid"></div>
                        </td>
                         <td align="right">所属机柜:</td>
                        <td align="left">
                            <div data-name="equipCabinetSid" id="add-firewall-equipCabinetSid"></div>
                        </td>
                        <td align="right">所属机架:</td>
                        <td align="left" colspan="3">
                            <div data-name="equipRackSid" id="add-firewall-equipRackSid"></div>
                        </td>
                    </tr>
                     <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="add-firewall-resTopologySid"/></td>
                    </tr>
                </table>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">规格:</td>
                        <td align="left"> <textarea rows="5" data-name="spec" id="add-firewall-spec"></textarea></td>
                    </tr>
                     <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="add-firewall-description"></textarea></td>
                    </tr>
                </table>
            </fieldset>
            <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">远程管理IP1:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp1" id="add-firewall-remoteMgtIp1"/></td>
                        <td align="right">远程管理IP2:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp2" id="add-firewall-remoteMgtIp2"/></td>
                        <td align="right">关联IP地址:</td>
                        <td align="left"><input type="text" data-name="relevanceIp" id="add-firewall-relevanceIp"/></td>
                    </tr>
                    <tr>
                        <td align="right">远程管理用户:</td>
                        <td align="left"><input type="text" data-name="remoteMgtUser" id="add-firewall-remoteMgtUser"/></td>
                        <td align="right">远程管理密码:</td>
                        <td align="left"><input type="password" data-name="remoteMgtPwd" id="add-firewall-remoteMgtPwd"/></td>
                    </tr>
                </table>
            </fieldset>  
            <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">维保厂商:</td>
                        <td align="left"><input type="text" data-name="maintCompany" id="add-firewall-maintCompany"/></td>
                        <td align="right">维保电话:</td>
                        <td align="left"><input type="text" data-name="maintTel" id="add-firewall-maintTel"/></td>
                        <td align="right">购置日期:</td>
                        <td align="left"> <div id='add-firewall-purchaseDate' data-name="purchaseDate"></div></td>
                    </tr>
                    <tr>
                        <td align="right">保修起始日期:</td>
                        <td align="left"> <div id='add-firewall-startEndDate' data-name="startEndDate"></div></td>
                        <td align="right">保修期限:</td>
                        <td align="left"><input type="text" data-name="warrantyPeriod" id="add-firewall-warrantyPeriod"/></td>
                        <td align="right">设备提供商:</td>
                        <td align="left"><input type="text" data-name="equipSupplier" id="add-firewall-equipSupplier"/></td>
                    </tr>
                </table>
            </fieldset>
             <table border="0" width="100%" cellspacing="4" cellpadding="0">
                   <tr>
                       <td align="right" colspan="3" height="32px">
                            <input style="margin-right: 5px;" onclick="submitAddfirewall()" type="button" id="firewallSave" value="确定" />
                            <input id="firewallCancel" type="button" value="取消" />&nbsp;&nbsp;
                       </td>
                   </tr>
              </table>
            </div>
       </div>
          <!-- 编辑防火墙页面 -->
     <div id="editfirewallWindow">
            <div>编辑防火墙设备</div>
            <div id="editfirewallForm" style="overflow: hidden;">
            <input type="hidden" data-name="equipSid" id="edit-firewall-equipFwSid"/>
            <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
                 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" data-name="locationNumber" id="edit-firewall-locationNumber"/></td>
                        <td align="right"><font style="color:red">*</font>防火墙编号:</td>
                        <td align="left"><input type="text" data-name="name" id="edit-firewall-name"/></td>
                        <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="edit-firewall-equipType"/></td>
                    </tr>
                    <tr>
                       <!--  <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="edit-firewall-equipType"/></td> -->
                        <td align="right">序列号:</td>
                        <td align="left"><input type="text" data-name="serialNumber" id="edit-firewall-serialNumber"/></td>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="edit-firewall-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="edit-firewall-model"/></td>
                    </tr>
                   <!--  <tr>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="edit-firewall-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="edit-firewall-model"/></td>
                    </tr> -->
                    <tr>
                        <td align="right" style="width:15%">所属机房:</td>
                        <td align="left">
                            <div data-name="equipRoomSid" id="edit-firewall-equipRoomSid"></div>
                        </td>
                         <td align="right">所属机柜:</td>
                        <td align="left">
                            <div data-name="equipCabinetSid" id="edit-firewall-equipCabinetSid"></div>
                        </td>
                        <td align="right">所属机架:</td>
                        <td align="left" colspan="3">
                            <div data-name="equipRackSid" id="edit-firewall-equipRackSid"></div>
                        </td>
                    </tr>
                     <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="edit-firewall-resTopologySid"/></td>
                    </tr>
                </table>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">规格:</td>
                        <td align="left"> <textarea rows="5" data-name="spec" id="edit-firewall-spec"></textarea></td>
                    </tr>
                     <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="edit-firewall-description"></textarea></td>
                    </tr>
                </table>
            </fieldset>
            <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">远程管理IP1:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp1" id="edit-firewall-remoteMgtIp1"/></td>
                        <td align="right">远程管理IP2:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp2" id="edit-firewall-remoteMgtIp2"/></td>
                        <td align="right">关联IP地址:</td>
                        <td align="left"><input type="text" data-name="relevanceIp" id="edit-firewall-relevanceIp"/></td>
                    </tr>
                    <tr>
                        <td align="right">远程管理用户:</td>
                        <td align="left"><input type="text" data-name="remoteMgtUser" id="edit-firewall-remoteMgtUser"/></td>
                        <td align="right">远程管理密码:</td>
                        <td align="left"><input type="password" data-name="remoteMgtPwd" id="edit-firewall-remoteMgtPwd"/></td>
                    </tr>
                </table>
            </fieldset>  
            <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">维保厂商:</td>
                        <td align="left"><input type="text" data-name="maintCompany" id="edit-firewall-maintCompany"/></td>
                        <td align="right">维保电话:</td>
                        <td align="left"><input type="text" data-name="maintTel" id="edit-firewall-maintTel"/></td>
                    <td align="right">购置日期:</td>
                        <td align="left"> <div id='edit-firewall-purchaseDate' data-name="purchaseDate"></div></td>
                    </tr>
                    <tr>
                        <td align="right">保修起始日期:</td>
                        <td align="left"> <div id='edit-firewall-startEndDate' data-name="startEndDate"></div></td>
                        <td align="right">保修期限:</td>
                        <td align="left"><input type="text" data-name="warrantyPeriod" id="edit-firewall-warrantyPeriod"/></td>
                        <td align="right">设备提供商:</td>
                        <td align="left"><input type="text" data-name="equipSupplier" id="edit-firewall-equipSupplier"/></td>
                    </tr>
                </table>
            </fieldset>
             <table border="0" width="100%" cellspacing="4" cellpadding="0">
                   <tr>
                      <td align="right" colspan="4">
                      <input style="margin-right: 5px;" onclick='submitEditfirewall()' type="button" id="firewallEditSave" value="保存" />
                      <input id="firewallEditCancel" type="button" value="取消" /></td>
                  </tr>
              </table>
            </div>
       </div>
	
</div>

<div id="firewallWindows" >
     <div>详情</div>
     <div id="firewallForm" style="overflow: hidden;">
             <div class="customPanel" style="width:99%;height:200px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;基本信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right"  style="width:15%">位置编号：</td>
                                    <td align="left" id="fire-locationNumber"></td>
                                    <td align="right"  style="width:15%">设备编号：</td>
                                    <td align="left" id="fire-name"></td>
                                    <td align="right"  style="width:15%">设备类型：</td>
                                    <td align="left" id="fire-equipType"></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">设备分类：</td>
                                    <td align="left" id="fire-equipCategoryName"></td>
                                    <td align="right"style="width:15%">序列号：</td>
                                    <td align="left" id="fire-serialNumber"></td>
                                    <td align="right"style="width:15%">品牌：</td>
                                    <td align="left" id="fire-brand" ></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">型号：</td>
                                    <td align="left" id="fire-model"></td>
                                </tr>
                                <tr>
                                    <td align="right"style="width:15%">所属机房：</td>
                                    <td align="left" id="fire-equipRoomName" ></td>
                                    <td align="right"style="width:15%">所属机柜：</td>
                                    <td align="left" id="fire-equipCabinetName" ></td>
                                    <td align="right"style="width:15%">所属机架：</td>
                                    <td align="left" id="fire-equipRackName" ></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">规格：</td>
                                    <td align="left"id="fire-spec" ></td>
                                   <!--  <td align="left" id="fire-spec" style="width:50%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td> -->
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">描述：</td>
                                    <td align="left"id="fire-description" ></td>
                                    <!-- <td align="left" id="fire-description" style="width:50%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td> -->
                                </tr>
                        </table>
                    </div>
             </div>
            <div class="customPanel" style="width:99%;height:110px;margin-left:0.5%;margin-top:8px;">
                      
                            <div class="title">&nbsp;&nbsp;远程管理信息</div>
                            <div>
                                  <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td align="right"  style="width:15%">远程管理IP1:</td>
                                            <td align="left" id="fire-remoteMgtIp1"></td>
                                            <td align="right"  style="width:15%">远程管理IP2:</td>
                                            <td align="left" id="fire-remoteMgtIp2"></td>
                                            <td align="right"  style="width:15%">关联IP地址：</td>
                                            <td align="left" id="fire-relevanceIp"></td>
                                        </tr>
                                        <tr>    
                                            <td align="right">远程管理用户：</td>
                                            <td align="left" id="fire-remoteMgtUser"></td>
                                            <td align="right">远程管理密码：</td>
                                            <td align="left" id="fire-remoteMgtPwd"></td>
                                        </tr>
                                </table>
                            </div>
            </div>
            
            <div class="customPanel" style="width:99%;height:110px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;维保信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right"  style="width:15%">维保厂商：</td>
                                    <td align="left" id="fire-maintCompany"></td>
                                    <td align="right"  style="width:15%">维保电话：</td>
                                    <td align="left" id="fire-maintTel"></td>
                                    <td align="right"  style="width:15%">购置日期：</td>
                                    <td align="left" id="fire-purchaseDate"></td>
                                </tr>
                                <tr>    
                                    <td align="right">保修起始日期：</td>
                                    <td align="left" id="fire-startEndDate"></td>
                                    <td align="right">保修期限：</td>
                                    <td align="left" id="fire-warrantyPeriod"></td>
                                    <td align="right">设备提供商：</td>
                                    <td align="left" id="fire-equipSupplier"></td>
                                </tr>
                          </table>
                    </div>
            </div>
            <div style="width:99%;height:40px;margin-left:0.5%;margin-top:8px;text-align:right;">
                  <br/>
                  <input type="button" id="firewall-close-button" value="关闭" />
            </div>
     </div>
</div>

<script type="text/javascript">

 	function firewallDatagridModel(){
		var fw = new firewallDatagridModels();
		 // 初始化页面组件
		 fw.initFirewallInput();
		 // 初始化datagrid
		 fw.initFirewallDatagrid();
		 // 为datagrid赋值
		 fw.initComboxLinkage();
		 fw.initPopWindow();
		 fw.initValue();
	
    } 
	 
</script>
