<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <div id="rscRemoveStorageWindow">
     <div>移除存储</div>
     <div style="overflow: hidden;">
          <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
		       <table border="0" height="30px" cellspacing="0" cellpadding="2">
			       	<tr>
			       		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;存储名称：</td>
			       			<td><input type="text" id="search-rsc-remove-storage-name" />&nbsp;&nbsp;</td>
			       			<td align="right" nowrap="nowrap">存储类型：</td>
			       			<td>
			       				<div id="search-rsc-remove-storage-type"></div>
			       			</td>
			       			<td><a onclick="searchRscRemoveStorageConfigMgt()" id="search-rsc-remove-storage-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
			       	</tr>
			   </table>
		 </div>
		  <div style="width:100%;height:308px;overflow-y:auto">
          		<div id='storageRscRemoveDatagrid' ></div>
          </div>
          <div style="position:absolute;bottom:5px;width:100%;height:40px;text-align:right;line-height:45px;">
          		<input style="margin-right: 5px;" type="button" onclick='rscRemoveStorageSubmit()'  id="rscRemoveStorageSave" value="保存" />
                <input id="rscRemoveStorageCancel" type="button" value="取消" />&nbsp;&nbsp;&nbsp;&nbsp;
          </div>
     </div>
</div>
   
 <script type="text/javascript">
      function initRscRemoveStorageModel(){
    	  var rscRemove = new rscRemoveStorageMgtModel();
    	  rscRemove.initInput();
    	  rscRemove.initRemoveStorageDatagrid();
    	  rscRemove.initPopWindow();
    	  rscRemove.searchRemoveStorageRscInfo();
      }
    	  
 </script>