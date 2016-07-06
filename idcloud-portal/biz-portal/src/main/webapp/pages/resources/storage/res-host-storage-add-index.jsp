<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="addStorageWindow">
            <div>新增存储</div>
            <div id="addStorageForm" style="overflow: hidden;">
            	<input type="hidden" data-name="parentTopologySid" id="storage-parentTopologySid"/>
            	<input type="hidden" data-name="resTopologySid" id="storage-resHostSids"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>存储名称:</td>
                        <td align="left"><input type="text" data-name="storageName" id="add-storageName"/></td>
                        <td align="right"><font style="color:red">*</font>LUN编号:</td>
                        <td align="left"><input type="text" data-name="lunNo" id="add-lunNo"/></td>
                    </tr>
                     <tr>
                    	 <td align="right"><font style="color:red">*</font>总容量(GB):</td>
                        <td align="left"><input type="text" data-name="totalCapacity" id="add-totalCapacity"/></td>
                        <td align="right"><font style="color:red">*</font>可用容量(GB):</td>
                        <td align="left"><input type="text" data-name="availableCapacity" id="add-availableCapacity"/></td>
                    </tr>
                    <tr>
                        <td align="right">存储类型:</td>
                        <td align="left">
                            <div data-name="storageType" id="add-storageType"></div>
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
                        <td align="right">逻辑单元号:</td>
                        <td align="left"><input type="text" data-name="storageUnitNo" id='add-storageUnitNo'/></td>
                        <td align="right">存储标签:</td>
                        <td align="left">
                            <input type="text" data-name="storageTag" id="add-storageTag" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">UUID:</td>
                        <td align="left"><input type="text" data-name="uuid" id="add-storage-uuid"/></td>
                        <td align="right">存储状态:</td>
                        <td align="left">
                           <div data-name="status" id="add-storage-status"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">所属存储设备:</td>
                        <td align="left">
                           <div data-name="resEquipStorageSid" id="add-storage-resequipStorageSids"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4" height="40px">
                        <input style="margin-right: 5px;" onclick='addStorageSubmit()' type="button" id="storageSave" value="保存" />
                        <input id="storageCancel" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
</div>

<script type="text/javascript">
   var addStorage = new addStorageConfigMgtModel();
   addStorage.initPopWindow();
   addStorage.initValidator();
</script>


   