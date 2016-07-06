<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
       <table border="0" height="100%" cellspacing="0" cellpadding="2">
        	<tr>
        		    <td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;存储名称：</td>
        			<td><input type="text" id="search-biz-storage-name" />&nbsp;&nbsp;</td>
        			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;项目名称：</td>
        			<td><input type="text" id="search-biz-name-storage" />&nbsp;&nbsp;</td>
        			<td align="right" nowrap="nowrap">存储分类：</td>
        			<td>
        				<div id="search-biz-storage-type"></div>
        			</td>
        			<td align="right">&nbsp;&nbsp;存储用途：</td>
        			<td>
        				<div id="search-biz-storage-storagePurposes"></div>
        			</td>
        			<td><a onclick="searchBizStorageConfigMgt()" id="search-biz-storage-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
        	</tr>
        </table>
 </div>
 <div style="width:98%;height:80%;margin-left:1%;"> 
  	<div id='bizStorageConfigdatagrid' style="width:100%;height:450px;"></div> 
 </div> 
 
 <script type="text/javascript">
    function bizStorageDatagridModel(){
	    var storage = new bizStorageConfigMgtModel();
	 	storage.initInput();
	 	storage.initStorageDatagrid();
    }
 	
 </script>