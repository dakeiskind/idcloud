<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="addStorageWindow">
            <div>新增块存储</div>
            <div id="addStorageForm" style="overflow: hidden;">
            	<input type="hidden" data-name="parentTopologySid" id="storage-parentTopologySid"/>
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


   