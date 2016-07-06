<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;存储编号：</td>
           			<td><input type="text" id="search-sanName" />&nbsp;&nbsp;</td>
                    <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;序列号：</td>
                    <td><input type="text" id="search-sanName-serialNumber" />&nbsp;&nbsp;</td>
           			<td align="right" nowrap="nowrap">所属机架：</td>
           			<td>
           				<div id="search-san-mf"></div>
           			</td>
           			<td><a onclick="searchSAN()" id="search-sanButton"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='sandatagrid' style="width:100%;height:450px;"></div> 
    </div>
    
    <!-- 新增机房页面 -->
     <div id="addSanWindow">
            <div>新增存储设备</div>
            <div id="addSanForm" style="overflow: hidden;">
            <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
                 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" data-name="locationNumber" id="add-san-locationNumber"/></td>
                        <td align="right"><font style="color:red">*</font>存储设备编号:</td>
                        <td align="left"><input type="text" data-name="name" id="add-san-name"/></td>
                        <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="add-san-equipType"/></td>
                    </tr>
                    <tr>
                       <!--  <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="add-san-equipType"/></td> -->
                        <td align="right">序列号:</td>
                        <td align="left"><input type="text" data-name="serialNumber" id="add-san-serialNumber"/></td>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="add-san-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="add-san-model"/></td>
                    </tr>
                    <!-- <tr>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="add-san-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="add-san-model"/></td>
                    </tr> -->
                    <tr>
                        <td align="right" style="width:15%">所属机房:</td>
                        <td align="left">
                            <div data-name="equipRoomSid" id="add-san-equipRoomSid"></div>
                        </td>
                         <td align="right">所属机柜:</td>
                        <td align="left">
                            <div data-name="equipCabinetSid" id="add-san-equipCabinetSid"></div>
                        </td>
                        <td align="right">所属机架:</td>
                        <td align="left">
                            <div data-name="equipRackSid" id="add-san-equipRackSid"></div>
                        </td>
                        <!-- <td align="right">容量(GB):</td>
                        <td align="left"><input type="text" data-name="capacity" id="add-san-capacity"/></td> -->
                    </tr>
                     <tr>
                        <td align="right">容量(GB):</td>
                        <td align="left"><input type="text" data-name="capacity" id="add-san-capacity"/></td>
                    </tr>
                     <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="add-san-resTopologySid"/></td>
                    </tr>
                </table>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">规格:</td>
                        <td align="left"> <textarea rows="5" data-name="spec" id="add-san-spec"></textarea></td>
                    </tr>
                     <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="add-san-description"></textarea></td>
                    </tr>
                </table>
            </fieldset>
            <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">远程管理IP1:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp1" id="add-san-remoteMgtIp1"/></td>
                        <td align="right">远程管理IP2:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp2" id="add-san-remoteMgtIp2"/></td>
                        <td align="right">关联IP地址:</td>
                        <td align="left"><input type="text" data-name="relevanceIp" id="add-san-relevanceIp"/></td>
                    </tr>
                    <tr>
                        <td align="right">远程管理用户:</td>
                        <td align="left"><input type="text" data-name="remoteMgtUser" id="add-san-remoteMgtUser"/></td>
                        <td align="right">远程管理密码:</td>
                        <td align="left"><input type="password" data-name="remoteMgtPwd" id="add-san-remoteMgtPwd"/></td>
                    </tr>
                </table>
            </fieldset>  
            <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">维保厂商:</td>
                        <td align="left"><input type="text" data-name="maintCompany" id="add-san-maintCompany"/></td>
                        <td align="right">维保电话:</td>
                        <td align="left"><input type="text" data-name="maintTel" id="add-san-maintTel"/></td>
                        <td align="right">购置日期:</td>
                        <td align="left"> <div id='add-san-purchaseDate' data-name="purchaseDate"></div></td>
                    </tr>
                    <tr>
                        <td align="right">保修起始日期:</td>
                        <td align="left"> <div id='add-san-startEndDate' data-name="startEndDate"></div></td>
                        <td align="right">保修期限:</td>
                        <td align="left"><input type="text" data-name="warrantyPeriod" id="add-san-warrantyPeriod"/></td>
                        <td align="right">设备提供商:</td>
                        <td align="left"><input type="text" data-name="equipSupplier" id="add-san-equipSupplier"/></td>
                    </tr>
                </table>
            </fieldset>
             <table border="0" width="100%" cellspacing="4" cellpadding="0">
                   <tr>
                       <td align="right" colspan="3" height="32px">
                            <input style="margin-right: 5px;" onclick="submitAddSan()" type="button" id="sanSave" value="确定" />
                            <input id="sanCancel" type="button" value="取消" />&nbsp;&nbsp;
                       </td>
                   </tr>
              </table>
            </div>
       </div>
       <div id="editSanWindow">
            <div>编辑存储设备</div>
            <div id="editSanForm" style="overflow: hidden;">
            <input type="hidden" data-name="equipSid" id="edit-san-equipStorageSid"/>
            <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
                 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" data-name="locationNumber" id="edit-san-locationNumber"/></td>
                        <td align="right"><font style="color:red">*</font>存储设备编号:</td>
                        <td align="left"><input type="text" data-name="name" id="edit-san-name"/></td>
                        <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="edit-san-equipType"/></td>
                    </tr>
                    <tr>
                       <!--  <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="edit-san-equipType"/></td> -->
                        <td align="right">序列号:</td>
                        <td align="left"><input type="text" data-name="serialNumber" id="edit-san-serialNumber"/></td>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="edit-san-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="edit-san-model"/></td>
                    </tr>
                   <!--  <tr>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="edit-san-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="edit-san-model"/></td>
                    </tr> -->
                    <tr>
                        <td align="right" style="width:15%">所属机房:</td>
                        <td align="left">
                            <div data-name="equipRoomSid" id="edit-san-equipRoomSid"></div>
                        </td>
                         <td align="right">所属机柜:</td>
                        <td align="left">
                            <div data-name="equipCabinetSid" id="edit-san-equipCabinetSid"></div>
                        </td>
                        <td align="right">所属机架:</td>
                        <td align="left">
                            <div data-name="equipRackSid" id="edit-san-equipRackSid"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">容量(GB):</td>
                        <td align="left"><input type="text" data-name="capacity" id="edit-san-capacity"/></td>
                    </tr>
                     <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="edit-san-resTopologySid"/></td>
                    </tr>
                </table>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">规格:</td>
                        <td align="left"> <textarea rows="5" data-name="spec" id="edit-san-spec"></textarea></td>
                    </tr>
                     <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="edit-san-description"></textarea></td>
                    </tr>
                </table>
            </fieldset>
            <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">远程管理IP1:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp1" id="edit-san-remoteMgtIp1"/></td>
                        <td align="right">远程管理IP2:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp2" id="edit-san-remoteMgtIp2"/></td>
                        <td align="right">关联IP地址:</td>
                        <td align="left"><input type="text" data-name="relevanceIp" id="edit-san-relevanceIp"/></td>
                    </tr>
                    <tr>
                        <td align="right">远程管理用户:</td>
                        <td align="left"><input type="text" data-name="remoteMgtUser" id="edit-san-remoteMgtUser"/></td>
                        <td align="right">远程管理密码:</td>
                        <td align="left"><input type="password" data-name="remoteMgtPwd" id="edit-san-remoteMgtPwd"/></td>
                    </tr>
                </table>
            </fieldset>  
            <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">维保厂商:</td>
                        <td align="left"><input type="text" data-name="maintCompany" id="edit-san-maintCompany"/></td>
                        <td align="right">维保电话:</td>
                        <td align="left"><input type="text" data-name="maintTel" id="edit-san-maintTel"/></td>
                        <td align="right">购置日期:</td>
                        <td align="left"> <div id='edit-san-purchaseDate' data-name="purchaseDate"></div></td>
                    </tr>
                    <tr>
                        <td align="right">保修起始日期:</td>
                        <td align="left"> <div id='edit-san-startEndDate' data-name="startEndDate"></div></td>
                        <td align="right">保修期限:</td>
                        <td align="left"><input type="text" data-name="warrantyPeriod" id="edit-san-warrantyPeriod"/></td>
                        <td align="right">设备提供商:</td>
                        <td align="left"><input type="text" data-name="equipSupplier" id="edit-san-equipSupplier"/></td>
                    </tr>
                </table>
            </fieldset>
             <table border="0" width="100%" cellspacing="4" cellpadding="0">
                   <tr>
                       <td align="right" colspan="3" height="32px">
                            <input style="margin-right: 5px;" onclick="submitEditSan()" type="button" id="sanEditSave" value="确定" />
                            <input id="sanEditCancel" type="button" value="取消" />&nbsp;&nbsp;
                       </td>
                   </tr>
              </table>
            </div>
       </div>
	
</div>

<div id="storageWindows" >
     <div>详情</div>
     <div id="storageForm" style="overflow: hidden;">
             <div class="customPanel" style="width:99%;height:200px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;基本信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" width="85px">位置编号：</td>
                                    <td align="left"id="locationNumber"></td>
                                    <td align="right"  width="85px">设备编号：</td>
                                    <td align="left"id="name"></td>
                                    <td align="right">设备类型：</td>
                                    <td align="left"id="equipType"></td>
                                </tr>
                                <tr>
                                    <td align="right">设备分类：</td>
                                    <td align="left"id="equipCategoryName"></td>
                                    <td align="right">序列号：</td>
                                    <td align="left"id="serialNumber"></td>
                                    <td align="right">品牌：</td>
                                    <td align="left"id="brand" ></td>
                                </tr>
                                <tr>
                                    <td align="right">容量(GB)：</td>
                                    <td align="left"id="capacity" ></td>
                                    <td align="right">型号：</td>
                                    <td align="left"id="model"></td>
                                </tr>
                                <tr>
                                    <td align="right">所属机房：</td>
                                    <td align="left"id="equipRoomName" ></td>
                                    <td align="right">所属机柜：</td>
                                    <td align="left"id="equipCabinetName" ></td>
                                    <td align="right">所属机架：</td>
                                    <td align="left"id="equipRackName" ></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">规格：</td>
                                    <td align="left"id="spec" ></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">描述：</td>
                                    <td align="left"id="description" ></td>
                                </tr>
                        </table>
                    </div>
             </div>
            <div class="customPanel" style="width:99%;height:110px;margin-left:0.5%;margin-top:8px;">
                      
                            <div class="title">&nbsp;&nbsp;远程管理信息</div>
                            <div>
                                  <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td align="right" width="85px">远程管理IP1:</td>
                                            <td align="left"id="remoteMgtIp1"></td>
                                            <td align="right"  width="85px">远程管理IP2:</td>
                                            <td align="left"id="remoteMgtIp2"></td>
                                            <td align="right"  width="85px">关联IP地址：</td>
                                            <td align="left"id="relevanceIp"></td>
                                        </tr>
                                        <tr>    
                                            <td align="right">远程管理用户：</td>
                                            <td align="left"id="remoteMgtUser"></td>
                                            <td align="right">远程管理密码：</td>
                                            <td align="left"id="remoteMgtPwd"></td>
                                        </tr>
                                </table>
                            </div>
            </div>
            
            <div class="customPanel" style="width:99%;height:110px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;维保信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" width="95px">维保厂商：</td>
                                    <td align="left"id="maintCompany"></td>
                                    <td align="right" width="95px">维保电话：</td>
                                    <td align="left"id="maintTel"></td>
                                    <td align="right" width="95px">购置日期：</td>
                                    <td align="left"id="purchaseDate"></td>
                                </tr>
                                <tr>    
                                    <td align="right">保修起始日期：</td>
                                    <td align="left"id="startEndDate"></td>
                                    <td align="right">保修期限：</td>
                                    <td align="left"id="warrantyPeriod"></td>
                                    <td align="right">设备提供商：</td>
                                    <td align="left"id="equipSupplier"></td>
                                </tr>
                          </table>
                    </div>
            </div>
            <div style="width:99%;height:40px;margin-left:0.5%;margin-top:8px;text-align:right;">
                  <br/>
                  <input type="button" id="storage-close-button" value="关闭" />
            </div>
     </div>
</div>

<script type="text/javascript">
//初始化sys-index页面model
	function sanDatagridModel(){
		var san = new sanDatagridModels();
		 // 初始化页面组件
		 san.initSANInput();
		 // 初始化datagrid
		 san.initSANDatagrid();
		 // 为datagrid赋值
		 //san.searchSANInfo();
		 san.initComboxLinkage();
		 san.initPopWindow();
		 san.initValue();
	
    }
	 
</script>
