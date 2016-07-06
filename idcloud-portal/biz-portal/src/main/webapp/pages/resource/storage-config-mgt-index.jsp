<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;存储名称：</td>
            			<td><input type="text" id="search-storage-name" />&nbsp;&nbsp;</td>
            			<td align="right" nowrap="nowrap">存储类型：</td>
            			<td>
            				<div id="search-storage-type"></div>
            			</td>
            			<td align="right">&nbsp;&nbsp;管理状态：</td>
            			<td>
            				<div id="search-storage-mgt-status"></div>
            			</td>
            			<td align="right">&nbsp;&nbsp;使用状态：</td>
            			<td>
            				<div id="search-storage-usage-status"></div>
            			</td>
            			<td><input type="button" value="查询" data-bind='click:searchStorageConfigMgt' id='search-storage-button' /></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='storageConfigMgtdatagrid' style="width:100%;height:450px;"></div> 
    </div>  
    
    <div id="addStorageWindow">
            <div>新增块存储</div>
            <div id="addStorageForm" style="overflow: hidden;">
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>存储名称:</td>
                        <td align="left"><input type="text" data-name="volumeName" id="add-volumeName"/></td>
                        <td align="right"><font style="color:red">*</font>总容量(GB):</td>
                        <td align="left"><input type="text" data-name="availableCapacity" id="add-availableCapacity"/></td>
                    </tr>
                    <tr>
                        <td align="right">存储类型:</td>
                        <td align="left">
                            <div data-name="volumeType" id="add-volumeType"></div>
                        </td>
                        <td align="right">硬盘类型:</td>
                        <td align="left">
                           <div data-name="hardDiskType" id="add-hardDiskType"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">存储类别:</td>
                        <td align="left">
                            <div data-name="storageCategory" id="add-storageCategory"></div>
                        </td>
                        <td align="right">存储用途:</td>
                        <td align="left">
                           <div data-name="storagePurpose" id="add-storagePurpose"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>逻辑单元号:</td>
                        <td align="left"><input type="text" data-name="volumeUnitNo" id='add-volumeUnitNo'/></td>
                        <td align="right">存储标签:</td>
                        <td align="left">
                            <input type="text" data-name="volumeTag" id="add-volumeTag" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4" height="40px">
                        <input style="margin-right: 5px;" data-bind='click: addStorageSubmit' type="button" id="storageSave" value="保存" />
                        <input id="storageCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>  
       
       <div id="editStorageWindow">
            <div>新增块存储</div>
            <div id="editStorageForm" style="overflow: hidden;">
            	<input type="hidden" data-name="resSid" id="resSidStorage"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>存储名称:</td>
                        <td align="left"><input type="text" data-name="volumeName" id="edit-volumeName"/></td>
                        <td align="right"><font style="color:red">*</font>总容量(GB):</td>
                        <td align="left"><input type="text" data-name="availableCapacity" id="edit-availableCapacity"/></td>
                    </tr>
                    <tr>
                        <td align="right">存储类型:</td>
                        <td align="left">
                            <div data-name="volumeType" id="edit-volumeType"></div>
                        </td>
                        <td align="right">硬盘类型:</td>
                        <td align="left">
                           <div data-name="hardDiskType" id="edit-hardDiskType"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">存储类别:</td>
                        <td align="left">
                            <div data-name="storageCategory" id="edit-storageCategory"></div>
                        </td>
                        <td align="right">存储用途:</td>
                        <td align="left">
                           <div data-name="storagePurpose" id="edit-storagePurpose"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>逻辑单元号:</td>
                        <td align="left"><input type="text" data-name="volumeUnitNo" id='edit-volumeUnitNo'/></td>
                        <td align="right">存储标签:</td>
                        <td align="left">
                            <input type="text" data-name="volumeTag" id="edit-volumeTag" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4" height="40px">
                        <input style="margin-right: 5px;" data-bind='click: editStorageSubmit' type="button" id="editStorageSave" value="保存" />
                        <input id="editStorageCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>  
    
      <script type="text/javascript">
      		function initStorageConfigJs(){
       		 // 初始化sys-index页面model
   			 var storageconfig = new StorageConfigModel();
       		 // 初始化页面组件
   			 storageconfig.initInput();
       		 // 初始化组件验证规则
   			 storageconfig.initValidator();
       		 // 初始化弹出框
   			 storageconfig.initPopWindow();
       		 // 初始化datagrid
   			 storageconfig.initStorageDatagrid();
       		 // 为datagrid赋值
   			 storageconfig.searchStorageConfigInfo();
   	
   			return storageconfig;
      		}
      </script>