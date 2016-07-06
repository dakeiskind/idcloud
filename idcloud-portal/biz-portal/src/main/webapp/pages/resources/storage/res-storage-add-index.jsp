<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="addStoragesWindow">
            <div>新增存储</div>
            <div id="addStorageForms" style="overflow: hidden;">
            	<input type="hidden" data-name="parentTopologySid" id="storage-parentTopologySids"/>
                <table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right"><font style="color:red">*</font>存储名称:</td>
                        <td align="left"><input type="text" data-name="storageName" id="add-storageNames"/></td>
                        <td align="right"><font style="color:red">*</font>LUN编号:</td>
                        <td align="left"><input type="text" data-name="lunNo" id="add-storage-lunNo"/></td>
                    </tr>
                    <tr>
                   		<td align="right"><font style="color:red">*</font>总容量(GB):</td>
                        <td align="left"><input type="text" data-name="totalCapacity" id="add-totalCapacitys"/></td>
                        <td align="right"><font style="color:red">*</font>可用容量(GB):</td>
                        <td align="left"><input type="text" data-name="availableCapacity" id="add-availableCapacitys"/></td>
                    </tr>
                    <tr>
                        <td align="right">存储类型:</td>
                        <td align="left">
                            <div data-name="storageType" id="add-storageTypes"></div>
                        </td>
                        <td align="right">硬盘类型:</td>
                        <td align="left">
                           <div data-name="hardDiskType" id="add-hardDiskTypes"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">存储类别:</td>
                        <td align="left">
                            <div data-name="storageCategory" id="add-storageCategorys"></div>
                        </td>
                        <td align="right">存储用途:</td>
                        <td align="left">
                           <div data-name="storagePurpose" id="add-storagePurposes"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">逻辑单元号:</td>
                        <td align="left"><input type="text" data-name="storageUnitNo" id='add-storageUnitNos'/></td>
                        <td align="right">存储标签:</td>
                        <td align="left">
                            <input type="text" data-name="storageTag" id="add-storageTags" />
                        </td>
                    </tr>
                    <tr style="display:none;">
                        <!-- <td align="right">UUID:</td>
                        <td align="left"><input type="text" data-name="uuid" id="add-storage-uuids"/></td> -->
                        <td align="right">存储状态:</td>
                        <td align="left">
                           <div data-name="status" id="add-storage-statuss"></div>
                        </td>
                    </tr>
                     <tr>
                        <td align="right">UUID:</td>
                        <td align="left"><input type="text" data-name="uuid" id="add-storage-uuids"/></td>
                        <td align="right">所属存储设备:</td>
                        <td align="left">
                           <div data-name="resEquipStorageSid" id="add-storage-resequipStorageSid"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4" height="40px">
<!--                              <input style="margin-right: 5px;" onclick='findHostsSubmit()' type="button" id="storageSaves" value="关联主机" />-->                       
                                  <input style="margin-right: 5px;" onclick='addStoragesSubmit()' type="button" id="storageSaves" value="保存" />
                        <input id="storageCancels" type="button" value="取消" /></td>
                    </tr>
                </table>
            </div>
</div>
<div id="addStorageWithHostWindow" style="display:none;">
       <div>关联主机</div>
       <div style="">
	       <div style="width:100%;height:30px;padding:0px 0px 5px 0px;">
		      <table border="0" height="100%" cellspacing="5" cellpadding="0">
		       	<tr>
		       		<td>主机名称:</td>
		      		<td><input type="text" id="add-search-storage-host-name"/></td>
		      		<td>&nbsp;主机IP:</td>
		      		<td><input type="text" id="add-search-storage-host-IP"/></td>
		      		<td>&nbsp;虚拟机&分区IP:</td>
		      		<td><input type="text" id="add-search-storage-vmIp"/></td>
		      		<td>&nbsp;&nbsp;&nbsp;<a onclick="searchStorageRelationHost()" id="add-search-storage-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
		       	</tr>
		       </table>
		   </div> 
	 	   <div style="width:100%;height:185px;overflow-y:auto;">
	       		<div id='findStorageWithHost' style="width:100%;"></div> 
	       </div>
	       <div style="position:absolute;bottom:10px;width:99%;padding-top:10px;text-align:right;">
	     		<input style="margin-right: 5px;" type="button"  onclick="addHostToClusterSubmit()" id="addStorageWithHostSave" value="保存" />
	         	<input style="margin-right: 5px;" id="addStorageWithHostCancel" type="button" value="取消" />
	       </div>
  		</div>  
</div>

<script type="text/javascript">
   var addStorage = new addStorageModel();
   addStorage.initPopWindow();
   addStorage.initValidator();
   addStorage.initfindHostAddToStorageDatagrid();
</script>