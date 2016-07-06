<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="editStoragesWindow">
            <div>编辑块存储</div>
            <div id="editStoragesForm" style="overflow: hidden;">
            	<input type="hidden" data-name="resStorageSid" id="resSidStorages"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>存储名称:</td>
                        <td align="left"><input type="text" data-name="storageName" id="edit-storageNames"/></td>
                        <td align="right"><font style="color:red">*</font>总容量(GB):</td>
                        <td align="left"><input type="text" data-name="totalCapacity" id="edit-totalCapacitys"/></td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>可用容量(GB):</td>
                        <td align="left"><input type="text" data-name="availableCapacity" id="edit-availableCapacitys"/></td>
<!--                         <td align="right"><font style="color:red">*</font>置备容量(GB):</td> -->
<!--                         <td align="left"><input type="text" data-name="provisionCapacity" id="edit-provisionCapacitys"/></td> -->
                    	<td align="right"><font style="color:red">*</font>UUID:</td>
                        <td align="left"><input type="text" data-name="uuid" id="edit-storage-uuids"/></td>
                    </tr>
                    <tr>
                        <td align="right">存储类型:</td>
                        <td align="left">
                            <div data-name="storageType" id="edit-storageTypes"></div>
                        </td>
                        <td align="right">硬盘类型:</td>
                        <td align="left">
                           <div data-name="hardDiskType" id="edit-hardDiskTypes"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">存储类别:</td>
                        <td align="left">
                            <div data-name="storageCategory" id="edit-storageCategorys"></div>
                        </td>
                        <td align="right">存储用途:</td>
                        <td align="left">
                           <div data-name="storagePurpose" id="edit-storagePurposes"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><font style="color:red">*</font>逻辑单元号:</td>
                        <td align="left"><input type="text" data-name="storageUnitNo" id='edit-storageUnitNos'/></td>
                        <td align="right">存储标签:</td>
                        <td align="left">
                            <input type="text" data-name="storageTag" id="edit-storageTags" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">存储状态:</td>
                        <td align="left">
                           <div data-name="status" id="edit-storage-statuss"></div>
                        </td>
                         <td align="right">所属存储设备:</td>
                        <td align="left"> 
                           <div data-name="resEquipStorageSid" id="edit-storage-resequipStorageSid"></div>
                        </td>
                    </tr>

                    <tr>
                        <td align="right" colspan="4" height="35px">
                        <input style="margin-right: 5px;" onclick='editStorageSubmits()' type="button" id="editStorageSaves" value="保存" />
                        <input id="editStorageCancels" type="button" value="取消" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>
                </table>
        </div>
</div>
<script type="text/javascript">
	var editStorage = new editStorageModel();
	editStorage.initPopWindow();
	editStorage.initValidator();
</script>