<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
	<!-- 编辑区域Window -->
  <div id="editHostRegionWindow">
          <div>编辑区域</div>
          <div id="editHostRegionForm" style="overflow: hidden;">
          		<input type="hidden" data-name="resTopologySid" id="edit-region-resTopologySid"/>
          		<input type="hidden" data-name="resTopologyType" id="edit-region-resTopologyType"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>区域名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="edit-region-resTopologyName"/></td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">区域描述:</td>
                         <td align="left">
                         	<textarea data-name="description" id="edit-region-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="2">
          				 	<input style="margin-right: 5px;" onclick="editHostRegionSubmit()" type="button" id="editHostRegionSave" value="保存" />
		              		<input id="editHostRegionCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>

      <!-- 编辑数据中心Window -->
  <div id="editHostDcWindow">
          <div>编辑数据中心</div>
          <div id="editHostDcForm" style="overflow: hidden;">
          		<input type="hidden" data-name="resTopologySid" id="edit-dc-resTopologySid"/>
          		<input type="hidden" data-name="resTopologyType" id="edit-dc-resTopologyType"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>数据中心名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="edit-dc-resTopologyName"/></td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">数据中心描述:</td>
                         <td align="left">
                         	<textarea data-name="description" id="edit-dc-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="2">
          				 	<input style="margin-right: 5px;" onclick="editHostDcSubmit()" type="button" id="editHostDcSave" value="保存" />
		              		<input id="editHostDcCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>
  
  <!--     编辑资源环境Window   -->
  <div id="editHostVeWindow">
          <div>新增资源环境</div>
          <div id="editHostVeForm" style="overflow: hidden;">
         		<input type="hidden" data-name="resTopologySid" id="edit-ve-resTopologySid"/>
          		<input type="hidden" data-name="resTopologyType" id="edit-ve-resTopologyType"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>环境名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="edit-ve-resTopologyName"/></td>
                         <td align="right" ><font style="color:red">*</font>环境类型:</td>
                         <td align="left">
                         	<div data-name="virtualPlatformType" id="edit-ve-virtualPlatformType" ></div>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right"><font style="color:red">*</font>环境版本:</td>
                         <td align="left">
                         	<div data-name="virtualPlatformVersion" id="edit-ve-version" ></div>
                         </td>
                         <td align="right"><font style="color:red">*</font>管理地址:</td>
                         <td align="left">
                         	<input type="text" data-name="managementUrl" id="edit-ve-mgtUrl"/>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right">管理账号:</td>
                         <td align="left">
                         	<input type="text" data-name="managementAccount" id="edit-ve-mgtAccount"/>
                         </td>
                          <td align="right">管理密码:</td>
                         <td align="left">
                         	<input type="password" data-name="managementPassword" id="edit-ve-mgtPasswd"/>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right">更新周期:</td>
                         <td align="left">
                         	<input type="text" data-name="updateCycle" id="edit-ve-updateCycle"/>
                         </td>
                         <td align="right">消息队列:</td>
                         <td align="left">
                         	<input type="text" data-name="mqTopic" id="edit-ve-mqTopic"/>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">环境描述:</td>
                         <td align="left"  colspan="3">
                         	<textarea data-name="description" id="edit-ve-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="4">
          				 	<input style="margin-right: 5px;" onclick="editHostVeSubmit()" type="button" id="editHostVeSave" value="保存" />
		              		<input id="editHostVeCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>
  
  <!-- 编辑集群Window -->
  <div id="editHostClusterWindow">
          <div>编辑集群</div>
          <div id="editHostClusterForm" style="overflow: hidden;">
          		<input type="hidden" data-name="resTopologySid" id="edit-cluster-resTopologySid"/>
          		<input type="hidden" data-name="resTopologyType" id="edit-cluster-resTopologyType"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>集群名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="edit-cluster-resTopologyName"/></td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">集群描述:</td>
                         <td align="left">
                         	<textarea data-name="description" id="edit-cluster-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="2">
          				 	<input style="margin-right: 5px;" onclick="editHostClusterSubmit()" type="button" id="editHostClusterSave" value="保存" />
		              		<input id="editHostClusterCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>
  
  <!-- 高级设置Window -->
  <div id="editAdvancedConfigWindow">
          <div>高级设置</div>
          <div id="editAdvancedConfigForm" style="overflow: hidden;">
          		<input type="hidden" data-name="resTopologySid" id="edit-ha-resTopologySid"/>
          		<input type="hidden" data-name="resTopologyType" id="edit-ha-resTopologyType"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="left" width="100">
          				 	 <div id='openHA'>打开HA功能</div>
          				 	 HA 功能用于检测故障，对群集中运行的虚拟机提供快速恢复功能。核心功能包括主机监控和虚拟机监控功能，用于在检测不到检测信号时最大程度地缩短停机时间。
          				 </td>
          			</tr>
          			<tr>
          				 <td align="right">
          				 	<input style="margin-right: 5px;" onclick="editAdvancedConfigSubmit()" type="button" id="editAdvancedConfigSave" value="保存" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>
  
  <script type="text/javascript">
  				
    setEditVirtualPlatformVersion("VMware");
	
	$('#edit-ve-virtualPlatformType').on('select', function (event) {
           var args = event.args;
           var item = $('#edit-ve-virtualPlatformType').jqxDropDownList('getItem', args.index);
           if (item != null) {
        	   setEditVirtualPlatformVersion(item.value);
           }
   });
	
	
	function setEditVirtualPlatformVersion(val){
		Core.AjaxRequest({
			url :ws_url + "/rest/system/getCodeByParams", 
			type:"post",
			params:{
				codeCategory:"VIRTUAL_PLATFORM_VERSION",
				parentCodeValue:val
			},
			callback : function (data) {
				var source =
		         {
		             datatype: "json",
		             datafields: [
		                 { name: 'codeValue' },
		                 { name: 'codeDisplay' }
		             ],
		             localdata:data
		         };
				 var dataAdapter = new $.jqx.dataAdapter(source);
				 
				 $("#edit-ve-version").jqxDropDownList({
	                  selectedIndex: 0 ,
	                  source: dataAdapter,
	                  displayMember: "codeDisplay", 
	                  valueMember: "codeValue",
	                  width: 120,
	                  height: 22,
	                  autoDropDownHeight : true,
	                  dropDownWidth :120,
	                  dropDownHeight :120,
	                  theme:"metro"
	            });
	        } 
	     });
	}
  
  
  </script>