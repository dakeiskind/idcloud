<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
  
  <!-- 新增区域Window -->
  <div id="addHostRegionWindow">
          <div>新增区域</div>
          <div id="addHostRegionForm" style="overflow: hidden;">
          		<input type="hidden" data-name="resTopologyType" id="add-region-resTopologyType"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>区域名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="add-region-resTopologyName"/></td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">区域描述:</td>
                         <td align="left">
                         	<textarea data-name="description" id="add-region-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="2">
          				 	<input style="margin-right: 5px;" onclick="addHostRegionSubmit()" type="button" id="addHostRegionSave" value="保存" />
		              		<input id="addHostRegionCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>

   <!-- 新增数据中心Window -->
  <div id="addHostDcWindow">
          <div>新增数据中心</div>
          <div id="addHostDcForm" style="overflow: hidden;">
          		<input type="hidden" data-name="parentTopologySid" id="add-dc-parentTopologySid"/>
          		<input type="hidden" data-name="resTopologyType" id="add-dc-resTopologyType"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>数据中心名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="add-dc-resTopologyName"/></td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">数据中心描述:</td>
                         <td align="left">
                         	<textarea data-name="description" id="add-dc-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="2">
          				 	<input style="margin-right: 5px;" onclick="addHostDcSubmit()" type="button" id="addHostDcSave" value="保存" />
		              		<input id="addHostDcCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>
  
<!--     新增资源环境Window   -->
  <div id="addHostVeWindow">
          <div>新增资源环境</div>
          <div id="addHostVeForm" style="overflow: hidden;">
         		<input type="hidden" data-name="parentTopologySid" id="add-ve-parentTopologySid"/>
          		<input type="hidden" data-name="resTopologyType" id="add-ve-resTopologyType"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>环境名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="add-ve-resTopologyName"/></td>
                         <td align="right" ><font style="color:red">*</font>环境类型:</td>
                         <td align="left">
                         	<div data-name="virtualPlatformType" id="add-ve-virtualPlatformType" ></div>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right"><font style="color:red">*</font>环境版本:</td>
                         <td align="left">
                         	<div data-name="virtualPlatformVersion" id="add-ve-version" ></div>
                         </td>
                         <td align="right"><font style="color:red">*</font>管理地址:</td>
                         <td align="left">
                         	<input type="text" data-name="managementUrl" id="add-ve-mgtUrl"/>
                         </td>
          			</tr>
          			<tr>
          				
          				 <td align="right">管理账号:</td>
                         <td align="left">
                         	<input type="text" data-name="managementAccount" id="add-ve-mgtAccount"/>
                         </td>
                          <td align="right">管理密码:</td>
                         <td align="left">
                         	<input type="password" data-name="managementPassword" id="add-ve-mgtPasswd"/>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right">更新周期:</td>
                         <td align="left">
                         	<input type="text" data-name="updateCycle" id="add-ve-updateCycle"/>
                         </td>
                         <td align="right">消息队列:</td>
                         <td align="left">
                         	<input type="text" data-name="mqTopic" id="add-ve-mqTopic"/>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">环境描述:</td>
                         <td align="left"  colspan="3">
                         	<textarea data-name="description" id="add-ve-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="4">
          				 	<input style="margin-right: 5px;" onclick="addHostVeSubmit()" type="button" id="addHostVeSave" value="保存" />
		              		<input id="addHostVeCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>
  
  <!-- 新增集群Window -->
  <div id="addHostClusterWindow">
          <div>新增集群</div>
          <div id="addHostClusterForm" style="overflow: hidden;">
         		<input type="hidden" data-name="parentTopologySid" id="add-cluster-parentTopologySid"/>
          		<input type="hidden" data-name="resTopologyType" id="add-cluster-resTopologyType"/>
          		<table border="0" width="100%"cellspacing="0" cellpadding="5">
          			<tr>
          				 <td align="right" width="100"><font style="color:red">*</font>集群名称:</td>
                         <td align="left"><input type="text" data-name="resTopologyName" id="add-cluster-resTopologyName"/></td>
          			</tr>
          			<tr>
          				 <td align="right" valign="top">集群描述:</td>
                         <td align="left">
                         	<textarea data-name="description" id="add-cluster-description"></textarea>
                         </td>
          			</tr>
          			<tr>
          				 <td align="right" colspan="2">
          				 	<input style="margin-right: 5px;" onclick="addHostClusterSubmit()" type="button" id="addHostClusterSave" value="保存" />
		              		<input id="addHostClusterCancel" type="button" value="取消" />
          				 </td>
          			</tr>
          		</table>
          </div>
  </div>
  
   <script type="text/javascript">
	    // 新增集群或者数据中心
		var addDc = new resAddDcAndClusterModel();
		addDc.initPopWindow();
		addDc.initValidator();
		// 给资源环境版本初始化
		setVirtualPlatformVersion("VMware");
		
		$('#add-ve-virtualPlatformType').on('select', function (event) {
	             var args = event.args;
	             var item = $('#add-ve-virtualPlatformType').jqxDropDownList('getItem', args.index);
	             if (item != null) {
	            	 setVirtualPlatformVersion(item.value);
	             }
	     });
		
		
		function setVirtualPlatformVersion(val){
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
					 
					 $("#add-ve-version").jqxDropDownList({
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