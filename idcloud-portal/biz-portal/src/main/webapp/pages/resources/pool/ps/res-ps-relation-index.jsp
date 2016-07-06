<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="relationPsPoolWindow">
     <div>新增关联存储</div>
     <div style="overflow: hidden;">
          <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
		       <table border="0" height="100%" cellspacing="0" cellpadding="2">
		        	<tr>
		        			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;存储名称：</td>
		        			<td><input type="text" id="search-select-storage-name" />&nbsp;&nbsp;</td>
		        			<td align="right">&nbsp;&nbsp;存储分类：</td>
		        			<td>
		        				<div id="search-select-storage-mgt-storageCategory"></div>
		        			</td>
		        			<td><a onclick="searchSelectStorageConfigMgt()" id="search-select-storage-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
		        	</tr>
		        </table>
		 </div>
		 
          <div id='poolPsRelationDatagrid' style="width:100%;height:250px;"></div>
          
          <div style="position:absolute;bottom:5px;width:100%;height:40px;text-align:right;line-height:45px;">
          		<input style="margin-right: 5px;" type="button" onclick='saveTheRelationStorage()'  id="relationPoolSave" value="保存" />
                <input id="relationPoolCancel" type="button" value="取消" />&nbsp;&nbsp;&nbsp;&nbsp;
          </div>
     </div>
</div> 

<script type="text/javascript">
	
</script>
