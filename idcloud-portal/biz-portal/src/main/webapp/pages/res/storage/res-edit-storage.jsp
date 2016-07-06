<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="editStorageWindow">
            <div>编辑块存储</div>
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
                        <input style="margin-right: 5px;" onclick='editStorageSubmit()' type="button" id="editStorageSave" value="保存" />
                        <input id="editStorageCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
       </div>
       <script type="text/javascript">
       		var editStorage = new editStorageConfigMgtModel();
       		editStorage.initValidator();
       		editStorage.initPopWindow();
       </script>

   