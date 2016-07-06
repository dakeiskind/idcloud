<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
       <table border="0" height="100%" cellspacing="0" cellpadding="2">
        	<tr>
        		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;存储名称：</td>
        			<td><input type="text" id="search-storage-host-name" />&nbsp;&nbsp;</td>
        			<td align="right" nowrap="nowrap">存储类别：</td>
        			<td>
        				<div id="search-storage-host-type"></div>
        			</td>
        			<td align="right">&nbsp;&nbsp;存储用途：</td>
        			<td>
        				<div id="search-storage-host-storagePurpose"></div>
        			</td>
<!--         			<td align="right">&nbsp;&nbsp;存储分类：</td>
        			<td>
        				<div id="search-storage-host-mgt-classify"></div>
        			</td> -->
        			<td><a onclick="searchStorageHostConfigMgt()" id="search-storage-host-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
        	</tr>
        </table>
 </div>
 <div style="width:98%;height:80%;margin-left:1%;margin-top:5px;"> 
  	<div id='storageHostDatagrid' style="width:100%;height:450px;"></div> 
 </div> 
 
   
 <script type="text/javascript">
 
	 	var storageHost = new storageHostMgtModel();
	 	storageHost.initInput();
	 	storageHost.initStorageDatagrid();
	 	storageHost.searchStorageHostInfo();
 	
 </script>