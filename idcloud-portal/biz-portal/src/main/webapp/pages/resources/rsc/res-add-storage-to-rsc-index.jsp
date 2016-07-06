<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="addStorageToRscWindow">
     <div>添加存储</div>
     <div style="overflow: hidden;">
          <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
		       <table border="0" height="100%" cellspacing="0" cellpadding="2">
		        	<tr>
	        			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;存储名称：</td>
	        			<td><input type="text" id="search-add-rsc-storage-name" />&nbsp;&nbsp;</td>
	        			<td align="right">&nbsp;&nbsp;存储类别：</td>
	        			<td>
	        				<div id="search-add-rsc-storage-type"></div>
	        			</td>
	        			<td><a onclick="searchAddStorageToRscMgt()" id="search-add-rsc-storage-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
		        	</tr>
		        </table>
		 </div>
		 <div style="width:100%;height:308px;overflow-y:auto;">
          		  <div id='addStorageToRscDatagrid' style="width:100%;"></div>
          </div>
          
          <div style="position:absolute;bottom:5px;width:100%;height:40px;text-align:right;line-height:45px;">
          		<input style="margin-right: 5px;" type="button" onclick='submitTheStoragesToRsc()'  id="addStorageToRscSave" value="保存" />
                <input id="addStorageToRscCancel" type="button" value="取消" />&nbsp;&nbsp;&nbsp;&nbsp;
          </div>
     </div>
</div> 

<script type="text/javascript">
		var addStorage = new addStorageToRscModel();
		addStorage.initInput();
		addStorage.initStorageDatagrid();
		addStorage.initPopWindow();
//		addStorage.searchCanableStorageRscInfo();
</script>