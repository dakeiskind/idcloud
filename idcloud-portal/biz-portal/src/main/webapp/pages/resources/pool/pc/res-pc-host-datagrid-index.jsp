<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;主机名称：</td>
           			<td><input type="text" id="search-pc-host-name" />&nbsp;&nbsp;</td>
<!--            			<td align="right" nowrap="nowrap">&nbsp;&nbsp;所属集群：</td> -->
<!--            			<td> -->
<!--            			<input type="text" id="search-pc-host-vc" />&nbsp;&nbsp; -->
<!--            			</td> -->
           			<td align="right">&nbsp;&nbsp;状态：</td>
           			<td>
           				<div id="search-pc-host-status"></div>
           			</td>
           			<td><a onclick="searchPcHost()" id="search-pc-host-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='pcHostdatagrid' style="width:100%;height:450px;"></div> 
    </div>
	
</div>

<script type="text/javascript">

function initPcHostDatagridModel(){
	 var host = new pcHostDatagridModel();
	 // 初始化页面组件
	 host.initHostInput();
	 // 初始化datagrid
	 host.initHostDatagrid();
	 
	 host.searchHostInfo();
	
}
	
</script>
