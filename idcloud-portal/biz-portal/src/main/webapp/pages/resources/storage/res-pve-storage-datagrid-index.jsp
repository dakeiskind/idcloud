<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
       <table border="0" height="100%" cellspacing="0" cellpadding="2">
        	<tr>
        		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;存储名称：</td>
        			<td><input type="text" id="search-pve-storage-name" />&nbsp;&nbsp;</td>
        			<td align="right" nowrap="nowrap">存储类别：</td>
        			<td>
        				<div id="search-pve-storage-type"></div>
        			</td>
        			<td align="right">&nbsp;&nbsp;存储分类：</td>
        			<td>
        				<div id="search-pve-storage-storagePurposes"></div>
        			</td>
        			<td><a onclick="searchPveStorageConfigMgt()" id="search-pve-storage-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
        	</tr>
        </table>
 </div>
 <div style="width:98%;height:80%;margin-left:1%;"> 
  	<div id='pveStorageConfigdatagrid' style="width:100%;height:450px;"></div> 
 </div> 
 
   
 <script type="text/javascript">
 function initPveHostViosStorage(){
	 	var storage = new pveStorageConfigMgtModel();
	 	storage.initInput();
	 	storage.initStorageDatagrid();
 }
 	
 </script>