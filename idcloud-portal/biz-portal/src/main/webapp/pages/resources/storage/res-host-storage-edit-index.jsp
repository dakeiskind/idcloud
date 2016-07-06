<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="editStorageWindow">
            <div>编辑块存储</div>
            <div id="editStorageForm" style="overflow: hidden;">
            	<input type="hidden" data-name="resStorageSid" id="resSidStorage"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>存储名称:</td>
                        <td align="left"><input type="text" data-name="storageName" id="edit-storageName"/></td>
                        <td align="right"><font style="color:red">*</font>总容量(GB):</td>
                        <td align="left"><input type="text" data-name="totalCapacity" id="edit-totalCapacity"/></td>
                    </tr>
                    <tr>
                        <td align="right">存储类型:</td>
                        <td align="left">
                            <div data-name="storageType" id="edit-storageType"></div>
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
                        <td align="left"><input type="text" data-name="storageUnitNo" id='edit-storageUnitNo'/></td>
                        <td align="right">存储标签:</td>
                        <td align="left">
                            <input type="text" data-name="storageTag" id="edit-storageTag" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>UUID:</td>
                        <td align="left"><input type="text" data-name="uuid" id="edit-storage-uuid"/></td>
                        <td align="right">存储状态:</td>
                        <td align="left">
                           <div data-name="status" id="edit-storage-status"></div>
                        </td>
                    </tr>
                     <tr>
                        <td align="right">所属存储设备:</td>
                        <td align="left">
                           <div data-name="resEquipStorageSid" id="edit-storage-resequipStorageSids"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4" height="40px">
                        <input style="margin-right: 5px;" onclick='editStorageSubmit()' type="button" id="editStoragebyHostSave" value="保存" />
                        <input id="editStorageCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>
       <script type="text/javascript">
       		var editStorage = new editStorageConfigMgtModel();
       		editStorage.initPopWindow();
       		editStorage.initValidator();
       </script>

   