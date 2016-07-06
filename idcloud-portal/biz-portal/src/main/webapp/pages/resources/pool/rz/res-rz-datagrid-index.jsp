<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;分区名称：</td>
           			<td><input type="text" id="search-rz-name" />&nbsp;&nbsp;</td>
           			<td><a onclick="searchRZ()" id="search-rz-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='poolRzDatagrid' style="width:100%;height:450px;"></div> 
    </div>
</div>

<script type="text/javascript">
	 //初始化sys-index页面model
	 var gridrz = new poolRzDatagridModel();
	 // 初始化页面组件
	 gridrz.initInput();
	 // 初始化datagrid
	 gridrz.initPoolRzDatagrid();
	 // 为datagrid赋值
	 gridrz.searchPoolRzInfo();
</script>
