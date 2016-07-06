<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp; 交换机编号：</td>
           			<td><input type="text" id="search-switchName" />&nbsp;&nbsp;</td>
                    <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp; 序列号：</td>
                    <td><input type="text" id="search-switchName-serialNumber" />&nbsp;&nbsp;</td>
           			<td align="right" nowrap="nowrap">所属机架：</td>
           			<td>
           				<div id="search-sw-mf"></div>
           			</td>
           			<!-- <td align="right" nowrap="nowrap">状态：</td>
           			<td>
           				<div id="search-sw-status"></div>
           			</td> -->
           			<td><a onclick="searchSwitch()" id="search-switchbutton"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='switchdatagrid' style="width:100%;height:450px;"></div> 
    </div>
       
        <!-- 新增机框页面 -->
      <div id="addSWWindow">
            <div>新增交换机设备</div>
            <div id="addSWForm" style="overflow: hidden;">
            <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
                 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" data-name="locationNumber" id="add-sw-locationNumber"/></td>
                        <td align="right"><font style="color:red">*</font>交换机编号:</td>
                        <td align="left"><input type="text" data-name="name" id="add-sw-name"/></td>
                        <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="add-sw-equipType"/></td>
                    </tr>
                    <tr>
                        <!-- <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="add-sw-equipType"/></td> -->
                        <td align="right"style="width:15%">序列号:</td>
                        <td align="left"><input type="text" data-name="serialNumber" id="add-sw-serialNumber"/></td>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="add-sw-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="add-sw-model"/></td>
                    </tr>
                  <!--   <tr>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="add-sw-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="add-sw-model"/></td>
                    </tr> -->
                    <tr>
                        <td align="right" style="width:15%">所属机房:</td>
                        <td align="left">
                            <div data-name="equipRoomSid" id="add-sw-equipRoomSid"></div>
                        </td>
                         <td align="right">所属机柜:</td>
                        <td align="left">
                            <div data-name="equipCabinetSid" id="add-sw-equipCabinetSid"></div>
                        </td>
                        <td align="right">所属机架:</td>
                        <td align="left">
                            <div data-name="equipRackSid" id="add-sw-equipRackSid"></div>
                        </td>
                    </tr>
                     <tr style="display:none">
                        <td align="right">数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="add-sw-resTopologySid"/></td>
                    </tr>
                </table>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">规格:</td>
                        <td align="left"> <textarea rows="5" data-name="spec" id="add-sw-spec"></textarea></td>
                    </tr>
                     <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="add-sw-description"></textarea></td>
                    </tr>
                </table>
            </fieldset>
            <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">远程管理IP1:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp1" id="add-sw-remoteMgtIp1"/></td>
                        <td align="right">远程管理IP2:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp2" id="add-sw-remoteMgtIp2"/></td>
                        <td align="right">关联IP地址:</td>
                        <td align="left"><input type="text" data-name="relevanceIp" id="add-sw-relevanceIp"/></td>
                    </tr>
                    <tr>
                        <td align="right">远程管理用户:</td>
                        <td align="left"><input type="text" data-name="remoteMgtUser" id="add-sw-remoteMgtUser"/></td>
                        <td align="right">远程管理密码:</td>
                        <td align="left"><input type="password" data-name="remoteMgtPwd" id="add-sw-remoteMgtPwd"/></td>
                    </tr>
                </table>
            </fieldset>  
            <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">维保厂商:</td>
                        <td align="left"><input type="text" data-name="maintCompany" id="add-sw-maintCompany"/></td>
                        <td align="right">维保电话:</td>
                        <td align="left"><input type="text" data-name="maintTel" id="add-sw-maintTel"/></td>
                        <td align="right">购置日期:</td>
                        <td align="left"> <div id='add-sw-purchaseDate' data-name="purchaseDate"></div></td>
                    </tr>
                    <tr>
                        <td align="right">保修起始日期:</td>
                        <td align="left"> <div id='add-sw-startEndDate' data-name="startEndDate"></div></td>
                        <td align="right">保修期限:</td>
                        <td align="left"><input type="text" data-name="warrantyPeriod" id="add-sw-warrantyPeriod"/></td>
                        <td align="right">设备提供商:</td>
                        <td align="left"><input type="text" data-name="equipSupplier" id="add-sw-equipSupplier"/></td>
                    </tr>
                </table>
            </fieldset>
             <table border="0" width="100%" cellspacing="4" cellpadding="0">
                   <tr>
                       <td align="right" colspan="3" height="32px">
                            <input style="margin-right: 5px;" onclick="submitAddSW()" type="button" id="swSave" value="确定" />
                            <input id="swCancel" type="button" value="取消" />&nbsp;&nbsp;
                       </td>
                   </tr>
              </table>
            </div>
       </div>
       <div id="editSWWindow">
            <div>编辑交换机设备</div>
            <div id="editSWForm" style="overflow: hidden;">
            <input type="hidden" data-name="equipSwitchSid" id="edit-sw-equipSwitchSid"/>
            <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
                 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" data-name="locationNumber" id="edit-sw-locationNumber"/></td>
                        <td align="right"><font style="color:red">*</font>交换机编号:</td>
                        <td align="left"><input type="text" data-name="name" id="edit-sw-name"/></td>
                        <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="edit-sw-equipType"/></td>
                    </tr>
                    <tr>
                       <!--  <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="edit-sw-equipType"/></td> -->
                        <td align="right" style="width:15%">序列号:</td>
                        <td align="left"><input type="text" data-name="serialNumber" id="edit-sw-serialNumber"/></td>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="edit-sw-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="edit-sw-model"/></td>
                    </tr>
                   <!--  <tr>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="edit-sw-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="edit-sw-model"/></td>
                    </tr> -->
                    <tr>
                        <td align="right" style="width:15%">所属机房:</td>
                        <td align="left">
                            <div data-name="equipRoomSid" id="edit-sw-equipRoomSid"></div>
                        </td>
                         <td align="right">所属机柜:</td>
                        <td align="left">
                            <div data-name="equipCabinetSid" id="edit-sw-equipCabinetSid"></div>
                        </td>
                        <td align="right"><font style="color:red">*</font>所属机架:</td>
                        <td align="left">
                            <div data-name="equipRackSid" id="edit-sw-equipRackSid"></div>
                        </td>
                    </tr>
                     <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="edit-sw-resTopologySid"/></td>
                    </tr>
                </table>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">规格:</td>
                        <td align="left"> <textarea rows="5" data-name="spec" id="edit-sw-spec"></textarea></td>
                    </tr>
                     <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="edit-sw-description"></textarea></td>
                    </tr>
                </table>
            </fieldset>
            <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">远程管理IP1:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp1" id="edit-sw-remoteMgtIp1"/></td>
                        <td align="right">远程管理IP2:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp2" id="edit-sw-remoteMgtIp2"/></td>
                        <td align="right">关联IP地址:</td>
                        <td align="left"><input type="text" data-name="relevanceIp" id="edit-sw-relevanceIp"/></td>
                    </tr>
                    <tr>
                        <td align="right">远程管理用户:</td>
                        <td align="left"><input type="text" data-name="remoteMgtUser" id="edit-sw-remoteMgtUser"/></td>
                        <td align="right">远程管理密码:</td>
                        <td align="left"><input type="password" data-name="remoteMgtPwd" id="edit-sw-remoteMgtPwd"/></td>
                    </tr>
                </table>
            </fieldset>  
            <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">维保厂商:</td>
                        <td align="left"><input type="text" data-name="maintCompany" id="edit-sw-maintCompany"/></td>
                        <td align="right">维保电话:</td>
                        <td align="left"><input type="text" data-name="maintTel" id="edit-sw-maintTel"/></td>
                        <td align="right">购置日期:</td>
                        <td align="left"> <div id='edit-sw-purchaseDate' data-name="purchaseDate"></div></td>
                    </tr>
                    <tr>
                        <td align="right">保修起始日期:</td>
                        <td align="left"> <div id='edit-sw-startEndDate' data-name="startEndDate"></div></td>
                        <td align="right">保修期限:</td>
                        <td align="left"><input type="text" data-name="warrantyPeriod" id="edit-sw-warrantyPeriod"/></td>
                        <td align="right">设备提供商:</td>
                        <td align="left"><input type="text" data-name="equipSupplier" id="edit-sw-equipSupplier"/></td>
                    </tr>
                </table>
            </fieldset>
             <table border="0" width="100%" cellspacing="4" cellpadding="0">
                   <tr>
                      <td align="right" colspan="4">
                      <input style="margin-right: 5px;" onclick='submitEditSW()' type="button" id="sweditSave" value="保存" />
                      <input id="sweditCancel" type="button" value="取消" /></td>
                  </tr>
              </table>
            </div>
       </div>
</div>
<!-- <div id="switchWindows">
     <div>详情</div>
     <div id="switchForm" style="overflow: hidden;">
             <div class="customPanel" style="width:99%;height:150px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;基本信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" style="width:15%">位置编号：</td>
                                    <td align="left" style="width:15%" id="switch-locationNumber"></td>
                                    <td align="right" style="width:15%">设备编号：</td>
                                    <td align="left" style="width:15%"id="switch-name"></td>
                                    <td align="right"style="width:15%">设备类型：</td>
                                    <td align="left" style="width:15%"id="switch-equipType"></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">设备分类：</td>
                                    <td align="left" style="width:15%"id="switch-equipCategoryName"></td>
                                    <td align="right">序列号：</td>
                                    <td align="left" style="width:15%"id="switch-serialNumber"></td>
                                    <td align="right">品牌：</td>
                                    <td align="left" style="width:15%"id="switch-brand" ></td>
                                </tr>
                                <tr>
                                    <td align="right"style="width:15%">型号：</td>
                                    <td align="left" style="width:15%"id="switch-model"></td>
                                    <td align="right"style="width:15%">所属机架：</td>
                                    <td align="left" style="width:15%"id="switch-equipRackName" ></td>
                                </tr>
                                <tr>
                                    <td align="right" colspan="3"style="width:15%">规格：</td>
                                    <td align="left" style="width:15%"id="switch-spec" ></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%"colspan="3">描述：</td>
                                    <td align="left" style="width:15%"id="switch-description" ></td>
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
                                            <td align="left" style="width:15%"id="switch-remoteMgtIp1"></td>
                                            <td align="right"  style="width:15%">远程管理IP2:</td>
                                            <td align="left" style="width:15%"id="switch-remoteMgtIp2"></td>
                                            <td align="right"  style="width:15%">关联IP地址：</td>
                                            <td align="left" style="width:15%"id="switch-relevanceIp"></td>
                                        </tr>
                                        <tr>    
                                            <td align="right" style="width:15%">远程管理用户：</td>
                                            <td align="left" style="width:15%"id="switch-remoteMgtUser"></td>
                                            <td align="right"style="width:15%">远程管理密码：</td>
                                            <td align="left" style="width:15%"id="switch-remoteMgtPwd"></td>
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
                                    <td align="left" id="switch-maintCompany"></td>
                                    <td align="right" width="95px">维保电话：</td>
                                    <td align="left" id="switch-maintTel"></td>
                                    <td align="right" width="95px">购置日期：</td>
                                    <td align="left" id="switch-purchaseDate"></td>
                                </tr>
                                <tr>    
                                    <td align="right">保修起始日期：</td>
                                    <td align="left" id="switch-startEndDate"></td>
                                    <td align="right">保修期限：</td>
                                    <td align="left" id="switch-warrantyPeriod"></td>
                                    <td align="right">设备提供商：</td>
                                    <td align="left" id="switch-equipSupplier"></td>
                                </tr>
                          </table>
                    </div>
            </div>
            <div style="width:99%;height:40px;margin-left:0.5%;margin-top:8px;text-align:right;">
                  <br/>
                  <input type="button" id="switch-close-button" value="关闭" />
            </div>
     </div>
</div> 
 -->
<script type="text/javascript">

 	//function switchDatagridModel(){
		var sw = new switchDatagridModels();
		 // 初始化页面组件
		 sw.initSwitchInput();
		 // 初始化datagrid
		 sw.initSwitchDatagrid();
		 // 为datagrid赋值
		 sw.searchSwitchInfo();
		 
		 sw.initPopWindow();
		 sw.initComboxLinkage();
		 sw.initValue();
	
   // } 
	 
</script>
