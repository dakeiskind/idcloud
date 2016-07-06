<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;名称：</td>
           			<td><input type="text" id="search-pnv-name" />&nbsp;&nbsp;</td>
           			<td><a onclick="searchPNV()" id="search-pnv-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='poolPNVDatagrid' style="width:100%;height:450px;"></div> 
    </div>
</div>

	<%@ include file="res-pnv-add-index.jsp"%>
   <%@ include file="res-pnv-edit-index.jsp"%>
   <%@ include file="res-pnv-mgtVlan-index.jsp"%>
   <%@ include file="res-pnv-relation-DVS-index.jsp"%>
   
<script type="text/javascript">
	function initPnvDatagridModel(){
		 //初始化sys-index页面model
		 var pnvdata = new poolPnvDatagridModel();
		 // 初始化页面组件
		 pnvdata.initInput();
		 // 初始化datagrid
		 pnvdata.initPnvDatagrid();
		 // 为datagrid赋值
		 pnvdata.searchPnvInfo();
	}
	
</script>
