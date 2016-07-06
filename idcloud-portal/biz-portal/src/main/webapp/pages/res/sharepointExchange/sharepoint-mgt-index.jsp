<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;服务地址：</td>
            			<td><input type="text" id="search-sharepoint-service-address" />&nbsp;&nbsp;</td>
            			<td align="right">&nbsp;&nbsp;管理状态：</td>
            			<td>
            				<div id="search-sharepoint-mgt-status"></div>
            			</td>
            			<td align="right">&nbsp;&nbsp;使用状态：</td>
            			<td>
            				<div id="search-sharepoint-usage-status"></div>
            			</td>
            			<td><input type="button" value="查询" onclick='searchSharepointConfigMgt()' id='search-sharepoint-button' /></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='sharepointConfigMgtdatagrid' style="width:100%;height:450px;"></div> 
    </div>  
    
	<div id="addSharepointWindow">
	            <div>新增Sharepoint</div>
	            <div id="addSharepointForm" style="overflow: hidden;">
	                <table border="0" width="100%" cellspacing="5" cellpadding="0">
	                    <tr>
	                        <td align="right"><font style="color:red">*</font>服务地址:</td>
	                        <td align="left"><input type="text" data-name="serviceAddress" id="add-sharepoint-serviceAddress"/></td>
	                        <td align="right"><font style="color:red">*</font>最大存储容量:</td>
	                        <td align="left"><input type="text" data-name="maxStorageCapacity" id="add-sharepoint-maxStorageCapacity"/></td>
	                    </tr>
	                    <tr>
	                        <td align="right" colspan="4" height="40px">
	                        <input style="margin-right: 5px;" onclick='addSharepointSubmit()' type="button" id="addSharepointSave" value="保存" />
	                        <input id="addSharepointCancel" type="button" value="取消" /></td>
	                    </tr>
	                </table>
	            </div>
       </div>  
       
       <div id="editSharepointWindow">
	            <div>编辑Sharepoint</div>
	            <div id="editSharepointForm" style="overflow: hidden;">
	            	<input type="hidden" data-name="resSid" id="resSidSharepoint"/>
	                <table border="0" width="100%" cellspacing="5" cellpadding="0">
	                    <tr>
	                        <td align="right"><font style="color:red">*</font>服务地址:</td>
	                        <td align="left"><input type="text" data-name="serviceAddress" id="edit-sharepoint-serviceAddress"/></td>
	                        <td align="right"><font style="color:red">*</font>最大存储容量:</td>
	                        <td align="left"><input type="text" data-name="maxStorageCapacity" id="edit-sharepoint-maxStorageCapacity"/></td>
	                    </tr>
	                    <tr>
	                        <td align="right" colspan="4" height="40px">
	                        <input style="margin-right: 5px;" onclick='editSharepointSubmit()' type="button" id="editSharepointSave" value="保存" />
	                        <input id="editSharepointCancel" type="button" value="取消" /></td>
	                    </tr>
	                </table>
	            </div>
       </div>  
       
       <div id="sharepointDetailInfoWindow">
            <div>SharePoint详细信息</div>
            <div id="sharepointDetailInfoForm" style="overflow: hidden;">
            	<div style="width:99%;height:190px;">
            		<div id='sharepointDetailInfoDatagrid' style="width:100%;height:100%;"></div> 
            	</div>
            	<div style="width:99%;height:30px;text-align:right;line-height:30px;">
            		<input id="sharepointDetailInfoCancel" type="button" value="取消" />
            	</div>
            </div>
       </div>    
       
      <script type="text/javascript">
   		 // 初始化sys-index页面model
		 var Sharepoint = new SharepointConfigModel();
		 // 初始化页面组件
		 Sharepoint.initInput();
		 // 初始化弹出框
		 Sharepoint.initPopWindow();
		 // 初始化组件验证规则
		 Sharepoint.initValidator();
		 // 初始化datagrid
		 Sharepoint.initSharepointDatagrid();
		 // 为datagrid赋值
		 Sharepoint.searchSharepointConfigInfo();
      </script>