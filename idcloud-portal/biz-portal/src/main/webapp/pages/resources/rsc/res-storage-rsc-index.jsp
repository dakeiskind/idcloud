<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

 <div style="width:98%;height:95%;margin-left:1%;margin-top:5px;">
    <div style="margin-bottom:5px;">
	 	<table border="0" height="30px" cellspacing="0" cellpadding="2">
	       	<tr>
	       		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;存储名称：</td>
	       			<td><input type="text" id="search-rsc-storage-name" />&nbsp;&nbsp;</td>
	       			<td align="right" nowrap="nowrap">存储类型：</td>
	       			<td>
	       				<div id="search-rsc-storage-type"></div>
	       			</td>
	       			<td><a onclick="searchRscStorageConfigMgt()" id="search-rsc-storage-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
	       	</tr>
	    </table>
    </div> 
  	<div id='storageRscDatagrid' style="width:100%;"></div> 
 </div> 
 
   
 <script type="text/javascript">
        function initRscStorageModel(){
        	  var rsc = new rscStorageMgtModel();
        	  rsc.initInput();
        	  rsc.initStorageDatagrid();
        	  rsc.searchStorageRscInfo();
        }
 </script>