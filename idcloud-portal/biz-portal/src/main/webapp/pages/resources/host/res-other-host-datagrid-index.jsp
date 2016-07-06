<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;主机名称：</td>
           			<td><input type="text" id="search-other-host-name" />&nbsp;&nbsp;</td>
           			<td align="right" nowrap="nowrap">&nbsp;&nbsp;操作系统：</td>
           			<td>
           			<input type="text" id="search-other-host-os-type" />&nbsp;&nbsp;
           			</td>
           			<td align="right">&nbsp;&nbsp;状态：</td>
           			<td>
           				<div id="search-other-host-status"></div>
           			</td>
           			<td><a onclick="searchOtherHost()" id="search-other-host-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='otherhostdatagrid' style="width:100%;height:450px;"></div> 
    </div>
	
</div>

<script type="text/javascript">

function otherHostDatagridModel(){
	 var host = new virtualOtherHostDatagridModel();
	 // 初始化页面组件
	 host.initHostInput();
	 // 初始化datagrid
	 host.initHostDatagrid();
}
	
</script>
