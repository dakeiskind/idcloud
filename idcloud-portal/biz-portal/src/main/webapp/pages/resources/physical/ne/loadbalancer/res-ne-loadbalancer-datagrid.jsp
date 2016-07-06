<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;负载均衡器编号：</td>
                    <td><input type="text" id="search-loadBalancerName" />&nbsp;&nbsp;</td>
                    <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;序列号：</td>
                    <td><input type="text" id="search-loadBalancerName-serialNumber" />&nbsp;&nbsp;</td>
           			<!-- <td align="right" nowrap="nowrap">区域：</td>
           			<td>
           				<div id="search-lb-rType"></div>
           			</td>-->
           			<td align="right" nowrap="nowrap">所属机架：</td>
           			<td>
           				<div id="search-load-equipRackSid"></div>
           			</td>
           			<td><a onclick="searchLoadbalancer()" id="search-loadbalancerbutton"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='loadbalancerdatagrid' style="width:100%;height:450px;"></div> 
    </div>
    
        <!-- 新增负载均衡器页面 -->
      <div id="addLoadWindow">
            <div>新增负载均衡设备</div>
            <div id="addLoadForm" style="overflow: hidden;">
            <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
                 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" data-name="locationNumber" id="add-load-locationNumber"/></td>
                        <td align="right" style="width:15%"><font style="color:red">*</font>设备编号:</td>
                        <td align="left"><input type="text" data-name="name" id="add-load-name"/></td>
                        <td align="right" style="width:15%"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="add-load-equipType"/></td>
                    </tr>
                    <tr>
                       <!--  <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="add-load-equipType"/></td> -->
                        <td align="right" style="width:15%">序列号:</td>
                        <td align="left"><input type="text" data-name="serialNumber" id="add-load-serialNumber"/></td>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="add-load-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="add-load-model"/></td>
                    </tr>
                  <!--   <tr>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="add-load-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="add-load-model"/></td>
                    </tr> -->
                    <tr>
                        <td align="right"style="width:15%">所属机房:</td>
                        <td align="left">
                            <div data-name="equipRoomSid" id="add-load-equipRoomSid"></div>
                        </td>
                        <td align="right">所属机柜:</td>
                        <td align="left">
                            <div data-name="equipCabinetSid" id="add-load-equipCabinetSid"></div>
                        </td>
                        <td align="right">所属机架:</td>
                        <td align="left">
                            <div data-name="equipRackSid" id="add-load-equipRackSid"></div>
                        </td>
                    </tr>
                     <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="add-load-resTopologySid"/></td>
                    </tr>
                </table>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:12%">规格:</td>
                        <td align="left"> <textarea rows="5" data-name="spec" id="add-load-spec"></textarea></td>
                    </tr>
                     <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="add-load-description"></textarea></td>
                    </tr>
                </table>
            </fieldset>
            <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">远程管理IP1:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp1" id="add-load-remoteMgtIp1"/></td>
                        <td align="right">远程管理IP2:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp2" id="add-load-remoteMgtIp2"/></td>
                        <td align="right">关联IP地址:</td>
                        <td align="left"><input type="text" data-name="relevanceIp" id="add-load-relevanceIp"/></td>
                    </tr>
                    <tr>
                        <td align="right">远程管理用户:</td>
                        <td align="left"><input type="text" data-name="remoteMgtUser" id="add-load-remoteMgtUser"/></td>
                        <td align="right">远程管理密码:</td>
                        <td align="left"><input type="password" data-name="remoteMgtPwd" id="add-load-remoteMgtPwd"/></td>
                    </tr>
                </table>
            </fieldset>  
            <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">维保厂商:</td>
                        <td align="left"><input type="text" data-name="maintCompany" id="add-load-maintCompany"/></td>
                        <td align="right">维保电话:</td>
                        <td align="left"><input type="text" data-name="maintTel" id="add-load-maintTel"/></td>
                        <td align="right">购置日期:</td>
                        <td align="left"> <div id='add-load-purchaseDate' data-name="purchaseDate"></div></td>
                    </tr>
                    <tr>
                        <td align="right" style="width:15%">保修起始日期:</td>
                        <td align="left"> <div id='add-load-startEndDate' data-name="startEndDate"></div></td>
                        <td align="right">保修期限:</td>
                        <td align="left"><input type="text" data-name="warrantyPeriod" id="add-load-warrantyPeriod"/></td>
                        <td align="right">设备提供商:</td>
                        <td align="left"><input type="text" data-name="equipSupplier" id="add-load-equipSupplier"/></td>
                    </tr>
                </table>
            </fieldset>
             <table border="0" width="100%" cellspacing="4" cellpadding="0">
                   <tr>
                       <td align="right" colspan="3" height="32px">
                            <input style="margin-right: 5px;" onclick="submitAddLoad()" type="button" id="loadSave" value="确定" />
                            <input id="loadCancel" type="button" value="取消" />&nbsp;&nbsp;
                       </td>
                   </tr>
              </table>
            </div>
       </div>
       
           <!--编辑负载均衡器页面 -->
     <div id="editLoadWindow">
            <div>编辑负载均衡设备</div>
            <div id="editLoadForm" style="overflow: hidden;">
            <input type="hidden" data-name="equipLoadBalanceSid" id="edit-load-equipLoadBalanceSid"/>
            <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
                 <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">位置编号:</td>
                        <td align="left"><input type="text" data-name="locationNumber" id="edit-load-locationNumber"/></td>
                        <td align="right"><font style="color:red">*</font>设备编号:</td>
                        <td align="left"><input type="text" data-name="name" id="edit-load-name"/></td>
                        <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="edit-load-equipType"/></td>
                    </tr>
                    <tr>
                       <!--  <td align="right"><font style="color:red">*</font>设备类型:</td>
                        <td align="left"><input type="text" data-name="equipType" id="edit-load-equipType"/></td> -->
                        <td align="right">序列号:</td>
                        <td align="left"><input type="text" data-name="serialNumber" id="edit-load-serialNumber"/></td>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="edit-load-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="edit-load-model"/></td>
                    </tr>
                   <!--  <tr>
                        <td align="right">品牌:</td>
                        <td align="left"><input type="text" data-name="brand" id="edit-load-brand"/></td>
                        <td align="right">型号:</td>
                        <td align="left"><input type="text" data-name="model" id="edit-load-model"/></td>
                    </tr> -->
                    <tr>
                        <td align="right"style="width:15%">所属机房:</td>
                        <td align="left">
                            <div data-name="equipRoomSid" id="edit-load-equipRoomSid"></div>
                        </td>
                        <td align="right">所属机柜:</td>
                        <td align="left">
                            <div data-name="equipCabinetSid" id="edit-load-equipCabinetSid"></div>
                        </td>
                        <td align="right"><font style="color:red">*</font>所属机架:</td>
                        <td align="left">
                            <div data-name="equipRackSid" id="edit-load-equipRackSid"></div>
                        </td>
                    </tr>
                     <tr style="display:none">
                        <td align="right"><font style="color:red">*</font>数据中心:</td>
                        <td align="left" colspan="3"><input type="text" data-name="resTopologySid" id="edit-load-resTopologySid"/></td>
                    </tr>
                </table>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" style="width:15%">规格:</td>
                        <td align="left"> <textarea rows="5" data-name="spec" id="edit-load-spec"></textarea></td>
                    </tr>
                     <tr>
                        <td align="right">描述:</td>
                        <td align="left"> <textarea rows="5" data-name="description" id="edit-load-description"></textarea></td>
                    </tr>
                </table>
            </fieldset>
            <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">远程管理IP1:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp1" id="edit-load-remoteMgtIp1"/></td>
                        <td align="right">远程管理IP2:</td>
                        <td align="left"><input type="text" data-name="remoteMgtIp2" id="edit-load-remoteMgtIp2"/></td>
                        <td align="right">关联IP地址:</td>
                        <td align="left"><input type="text" data-name="relevanceIp" id="edit-load-relevanceIp"/></td>
                    </tr>
                    <tr>
                        <td align="right">远程管理用户:</td>
                        <td align="left"><input type="text" data-name="remoteMgtUser" id="edit-load-remoteMgtUser"/></td>
                        <td align="right">远程管理密码:</td>
                        <td align="left"><input type="password" data-name="remoteMgtPwd" id="edit-load-remoteMgtPwd"/></td>
                    </tr>
                </table>
            </fieldset>  
            <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                     <tr>
                        <td align="right" style="width:15%">维保厂商:</td>
                        <td align="left"><input type="text" data-name="maintCompany" id="edit-load-maintCompany"/></td>
                        <td align="right">维保电话:</td>
                        <td align="left"><input type="text" data-name="maintTel" id="edit-load-maintTel"/></td>
                        <td align="right">购置日期:</td>
                        <td align="left"> <div id='edit-load-purchaseDate' data-name="purchaseDate"></div></td>
                    </tr>
                    <tr>
                        <td align="right">保修起始日期:</td>
                        <td align="left"> <div id='edit-load-startEndDate' data-name="startEndDate"></div></td>
                        <td align="right">保修期限:</td>
                        <td align="left"><input type="text" data-name="warrantyPeriod" id="edit-load-warrantyPeriod"/></td>
                        <td align="right">设备提供商:</td>
                        <td align="left"><input type="text" data-name="equipSupplier" id="edit-load-equipSupplier"/></td>
                    </tr>
                </table>
            </fieldset>
             <table border="0" width="100%" cellspacing="4" cellpadding="0">
                   <tr>
                      <td align="right" colspan="4">
                      <input style="margin-right: 5px;" onclick='submitEditLoad()' type="button" id="loadeditSave" value="保存" />
                      <input id="loadeditCancel" type="button" value="取消" /></td>
                  </tr>
              </table>
            </div>
       </div>
</div>

<div id="loadbalancerWindows" >
     <div>详情</div>
     <div id="loadbalancerForm" style="overflow: hidden;">
             <div class="customPanel" style="width:99%;height:200px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;基本信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" width="85px">位置编号：</td>
                                    <td align="left"id="loadbalance-locationNumber"></td>
                                    <td align="right" width="85px">设备编号：</td>
                                    <td align="left"id="loadbalance-name"></td>
                                    <td align="right">设备类型：</td>
                                    <td align="left"id="loadbalance-equipType"></td>
                                </tr>
                                <tr>
                                    <td align="right">设备分类：</td>
                                    <td align="left"id="loadbalance-equipCategoryName"></td>
                                    <td align="right">序列号：</td>
                                    <td align="left"id="loadbalance-serialNumber"></td>
                                    <td align="right">品牌：</td>
                                    <td align="left"id="loadbalance-brand" ></td>
                                </tr>
                                <tr>
                                    <td align="right">型号：</td>
                                    <td align="left"id="loadbalance-model"></td>
                                </tr>
                                 <tr>
                                    <td align="right">所属机房：</td>
                                    <td align="left"id="loadbalance-equipRoomName" ></td>
                                    <td align="right">所属机柜：</td>
                                    <td align="left"id="loadbalance-equipCabinetName" ></td>
                                    <td align="right">所属机架：</td>
                                    <td align="left"id="loadbalance-equipRackName" ></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">规格：</td>
                                    <td align="left"id="loadbalance-spec" ></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">描述：</td>
                                    <td align="left"id="loadbalance-description" ></td>
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
                                            <td align="left"id="loadbalance-remoteMgtIp1"></td>
                                            <td align="right"  width="85px">远程管理IP2:</td>
                                            <td align="left"id="loadbalance-remoteMgtIp2"></td>
                                            <td align="right"  width="85px">关联IP地址：</td>
                                            <td align="left"id="loadbalance-relevanceIp"></td>
                                        </tr>
                                        <tr>    
                                            <td align="right">远程管理用户：</td>
                                            <td align="left"id="loadbalance-remoteMgtUser"></td>
                                            <td align="right">远程管理密码：</td>
                                            <td align="left"id="loadbalance-remoteMgtPwd"></td>
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
                                    <td align="left"id="loadbalance-maintCompany"></td>
                                    <td align="right" width="95px">维保电话：</td>
                                    <td align="left"id="loadbalance-maintTel"></td>
                                    <td align="right" width="95px">购置日期：</td>
                                    <td align="left"id="loadbalance-purchaseDate"></td>
                                </tr>
                                <tr>    
                                    <td align="right">保修起始日期：</td>
                                    <td align="left"id="loadbalance-startEndDate"></td>
                                    <td align="right">保修期限：</td>
                                    <td align="left"id="loadbalance-warrantyPeriod"></td>
                                    <td align="right">设备提供商：</td>
                                    <td align="left"id="loadbalance-equipSupplier"></td>
                                </tr>
                          </table>
                    </div>
            </div>
            <div style="width:99%;height:40px;margin-left:0.5%;margin-top:8px;text-align:right;">
                  <br/>
                  <input type="button" id="loadbalancer-close-button" value="关闭" />
            </div>
     </div>
</div>

<script type="text/javascript">

 	function loadbalancerDatagridModel(){
		var lb = new loadbalancerDatagridModels();
		 // 初始化页面组件
		 lb.initLoadbalancerInput();
		 // 初始化datagrid
		 lb.initLoadbalancerDatagrid();
         lb.initComboxLinkage();
		 // 为datagrid赋值
		 //lb.searchLoadbalancerInfo();
		 
		 lb.initPopWindow();
		 lb.initValue();
	
    } 
	 
</script>
