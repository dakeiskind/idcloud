<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
     <div id="editX86HostWindow">
            <div>编辑X86主机</div>
            <div id="editX86HostForm">
            	<div>
            		  <input type="hidden" data-name="resHostSid" id="edit-pve-resHostSid"/>
	            	  <fieldset style="border-color:#FCFCFC"><legend><b>配置信息</b></legend>
	                    <table border="0" width="100%" cellspacing="5" cellpadding="0">
	                    <tr>
	                        <td align="right" style="width:15%"><font style="color:red">*</font>主机名称:</td>
	                        <td align="left" ><input type="text" data-name="hostName" id="edit-phyhost-hostName"/></td>
	                        <td align="right" style="width:15%"><font style="color:red">*</font>主机IP:</td>
	                        <td align="left"><input type="text" data-name="hostIp" id="edit-phyhost-hostIp"/></td>
	                        <td align="right" nowrap="nowrap">操作系统：</td>
	                        <td>
	                            <div data-name="hostOsType" id="edit-phyhost-hostOsType"></div>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td align="right" style="width:15%"><font style="color:red">*</font>CPU个数:</td>
	                        <td align="left"><input type="text" data-name="cpuNumber"  id="edit-phyhost-cpuNumber"/></td>
	                        <td align="right"><font style="color:red">*</font>CPU核数:</td>
	                        <td align="left"><input type="text" data-name="cpuCores" id="edit-phyhost-cpuCores"/></td>
	                        <td align="right" nowrap="nowrap">CPU类型：</td>
	                        <td align="left"><input type="text" data-name="cpuType" id="edit-phyhost-cpuType"/></td>
	                    </tr>
	                    <tr>
	                        <td align="right" style="width:15%"><font style="color:red">*</font>内存大小(MB):</td>
	                        <td align="left"><input type="text" data-name="memorySize" id="edit-phyhost-memorySize"/></td>
	                        <td align="right">主机用户名:</td>
	                        <td align="left"><input type="text" data-name="managementUser" id="edit-phyhost-managementUser"/></td>
	                        <td align="right" nowrap="nowrap">主机密码：</td>
	                        <td align="left"><input type="password" data-name="managementPwd" id="edit-phyhost-managementPwd"/></td>
	                    </tr>
	                 </table>
	                </fieldset>
	                <fieldset style="border-color:#FCFCFC"><legend><b>基本信息</b></legend>
	                 <table border="0" width="100%" cellspacing="5" cellpediting="0">
	                  <tr>
	                        <td align="right" style="width:15%"><font style="color:red">*</font>主机类型:</td>
	                        <td align="left" colspan="5">
	                            <input type="text" data-name="equipType" id="edit-phyhost-equipType"/>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td align="right" style="width:15%"><font style="color:red">*</font>主机分类:</td>
	                        <td align="left">
	                            <div data-name="equipCategory" id="edit-phyhost-equipCategory"></div>
	                        </td>
	                        <td align="right" style="width:15%"><font style="color:red">*</font>主机编号:</td>
	                        <td align="left"><input type="text" data-name="name" id="edit-phyhost-name"/></td>
	                        <td align="right">序列号:</td>
	                        <td align="left"><input type="text" data-name="serialNumber" id="edit-phyhost-serialNumber"/></td> 
	                       
	                    </tr>
	                     <tr>
	                        <td align="right" style="width:15%">品牌:</td>
	                        <td align="left"><input type="text" data-name="brand" id="edit-phyhost-brand"/></td>
	                        <td align="right" style="width:15%">型号:</td>
	                        <td align="left"><input type="text" data-name="model" id="edit-phyhost-model"/></td>
	                        <td align="right" style="width:15%">位置编号:</td>
	                        <td align="left"><input type="text" data-name="locationNumber" id="edit-phyhost-locationNumber"/></td>
	                    </tr>

	                    <tr>
	                        <td align="right" style="width:15%">所属机房:</td>
	                        <td align="left">
	                            <div data-name="equipRoomSid" id="edit-phyhost-equipRoomSid"></div>
	                        </td>
	                        <td align="right" style="width:15%">所属机柜:</td>
	                        <td align="left">
	                            <div data-name="equipCabinetSid" id="edit-phyhost-equipCabinetSid"></div>
	                        </td>
	                         <td align="right" style="width:15%">所属机架:</td>
	                        <td align="left">
	                            <div data-name="equipRackSid" id="edit-phyhost-equipRackSid"></div>
	                        </td>
	                    </tr>
	                </table>
	                <table border="0" width="100%" cellspacing="5" cellpadding="0">
	                    <tr>
	                        <td align="right" style="width:9.5%">规格:</td>
	                        <td align="left" colspan="3"> 
	                            <textarea data-name="spec" rows="5" id="edit-phyhost-spec"></textarea>
	                        </td>
	                    </tr>
	                     <tr>
	                        <td align="right">描述:</td>
	                        <td align="left" colspan="3"> 
	                            <textarea data-name="description" rows="5" id="edit-phyhost-description"></textarea>
	                        </td>
	                    </tr>
	                </table>
	                </fieldset>
	                <fieldset style="border-color:#FCFCFC"><legend><b>远程管理信息</b></legend>
	                <table border="0" width="100%" cellspacing="5" cellpadding="0">
	                     <tr>
	                        <td align="right" style="width:18%">管理IP1:</td>
	                        <td align="left"><input type="text" data-name="remoteMgtIp1" id="edit-phyhost-remoteMgtIp1"/></td>
	                        <td align="right" style="width:18%">管理IP2:</td>
	                        <td align="left"><input type="text" data-name="remoteMgtIp2" id="edit-phyhost-remoteMgtIp2"/></td>
	                        <td align="right"  style="width:18%">关联IP:</td>
	                        <td align="left"><input type="text" data-name="relevanceIp" id="edit-phyhost-relevanceIp"/></td>
	                    </tr>
	                    <tr>
	                        <td align="right">管理用户:</td>
	                        <td align="left"><input type="text" data-name="remoteMgtUser" id="edit-phyhost-remoteMgtUser"/></td>
	                        <td align="right">管理密码:</td>
	                        <td align="left"><input type="password" data-name="remoteMgtPwd" id="edit-phyhost-remoteMgtPwd"/></td>
	                    </tr>
	                </table>
	                </fieldset>  
	                <fieldset style="border-color:#FCFCFC"><legend><b>维保信息</b></legend>
	                <table border="0" width="100%" cellspacing="5" cellpadding="0">
	                     <tr>
	                        <td align="right" style="width:10%">维保厂商:</td>
	                        <td align="left"><input type="text" data-name="maintCompany" id="edit-phyhost-maintCompany"/></td>
	                        <td align="right">维保电话:</td>
	                        <td align="left"><input type="text" data-name="maintTel" id="edit-phyhost-maintTel"/></td>
	                        <td align="right">购置日期:</td>
	                        <td align="left"> <div data-name="purchaseDate" id='edit-phyhost-purchaseDate'></div></td>
	                    </tr>
	                    <tr>
	                        <td align="right">保修期限:</td>
	                        <td align="left"><input type="text" data-name="warrantyPeriod" id="edit-phyhost-warrantyPeriod"/></td>
	                        <td align="right">保修起始日期:</td>
	                        <td align="left"> <div id='edit-phyhost-startdate' data-name="startEndDate" ></div></td>
	                        <td align="right">设备提供商:</td>
	                        <td align="left"><input type="text" data-name="equipSupplier" id="edit-phyhost-equipSupplier"/></td>
	                    </tr>
	                </table>
	                </fieldset>	
            	</div>
                <div style="width:100%;padding-top:10px;text-align:right">
            		  <input style="margin-right: 5px;" onclick="javascript:editX86HostSubmit()" type="button" id="edithostSave" value="保存" />
		              <input id="edithostCancel" type="button" value="取消" />
                </div>
            </div>
       </div>
         
  <script type="text/javascript">
	    var editX86host = new editX86HostModel();
		editX86host.initPopWindow();
		editX86host.initValidator();
        
		// 机房联动
		$("#edit-phyhost-equipRoomSid").on('select', function (event){
				var codeedit = new codeModel({width:170,autoDropDownHeight:true,dropDownWidth:170});
			 	codeedit.getCustomCode("edit-phyhost-equipCabinetSid","/phylocations/find/cabinetByRoomSid","name","equipSid",false,"POST",{equipRoomSid:$("#edit-phyhost-equipRoomSid").val()});
     	   		$("#edit-phyhost-equipCabinetSid").jqxDropDownList({selectedIndex:0});
	    });
		
		// 机柜联动
		$("#edit-phyhost-equipCabinetSid").on('select', function (event){
				var codeedit = new codeModel({width:170,autoDropDownHeight:true,dropDownWidth:170});
		 		codeedit.getCustomCode("edit-phyhost-equipRackSid","/phylocations/find/rackByRoomSid","name","equipSid",false,"POST",{equipCabinetSid:$("#edit-phyhost-equipCabinetSid").val()});
		 		$("#edit-phyhost-equipRackSid").jqxDropDownList({selectedIndex:0});
		});

  </script>