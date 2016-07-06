<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;存储名称：</td>
           			<td><input type="text" id="search-ps-name" />&nbsp;&nbsp;</td>
           			<td align="right">&nbsp;&nbsp;存储分类：</td>
        			<td>
        				<div id="search-storage-mgt-storageCategory"></div>
        			</td>
           			<td align="right">&nbsp;&nbsp;存储用途：</td>
        			<td>
        				<div id="search-storage-mgt-storagePurpose"></div>
        			</td>
           			<td><a onclick="searchPS()" id="search-ps-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='poolPsDatagrid' style="width:100%;height:450px;"></div> 
    </div>
</div>

<script type="text/javascript">
	function initPsDatagridModel(){
		//初始化sys-index页面model
		 var psgrid = new poolPsDatagridModel();
		 // 初始化页面组件
		 psgrid.initInput();
		 // 初始化datagrid
		 psgrid.initPsDatagrid();
		 // 为datagrid赋值
		 psgrid.searchPsInfo();
	}
</script>
