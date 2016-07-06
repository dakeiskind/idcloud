<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="extendStoragesWindow">
            <div>扩容块存储</div>
            <div id="extendStoragesForm" style="overflow: hidden;padding:20px;">
            	<input type="hidden" data-name="resStorageSid" id="resExtendStorageSid"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right">存储名称:</td>
                        <td align="left"><input type="text" data-name="storageName" id="extend-storageNames" readonly="readonly"/></td>
                        <td align="right"></font>总容量(GB):</td>
                        <td align="left"><input type="text" data-name="totalCapacity" id="extend-totalCapacitys" readonly="readonly"/></td>
                    </tr>
                    <tr>
                    	<td align="right"><font style="color:red">*</font>扩展容量(GB):</td>
                        <td align="left"><div data-name="totalCapacity" id="extend-newTotalCapacitys"></div>
                        </td>
                        <td align="left" colspan="2"><font style="color:#D3D3D3;"></font></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4" height="35px">
                        <input style="margin-right: 5px;" onclick='extendStorageSubmit()' type="button" id="extendStorageSave" value="保存" />
                        <input id="extendStorageCancel" type="button" value="取消" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>
                </table>
        </div>
</div>
<script type="text/javascript">
	var extendStorage = new extendStorageModel();
	extendStorage.initPopWindow();
	extendStorage.initValidator();
</script>