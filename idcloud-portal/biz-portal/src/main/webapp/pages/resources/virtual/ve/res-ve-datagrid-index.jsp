<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;环境名称：</td>
           			<td><input type="text" id="search-ve-name" />&nbsp;&nbsp;</td>
           			<td align="right" nowrap="nowrap">虚拟化类型：</td>
           			<td>
           				<div id="search-ve-virtual-type"></div>
           			</td>
           			<td align="right" nowrap="nowrap">管理账户：</td>
           			<td><input type="text" id="search-mgt-account"/>&nbsp;&nbsp;</td>
           			<td align="right" nowrap="nowrap">连接状态：</td>
           			<td>
           				<div id="search-ve-connect-status"></div>
           			</td> 
           			
           			<td><a onclick="searchVE()" id="search-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='virtualVeDatagrid' style="width:100%;height:450px;"></div> 
    </div>
</div>

<script type="text/javascript">
	 //初始化sys-index页面model
	 var ve = new virtualVeDatagridModel();
	 // 初始化页面组件
	 ve.initInput();
	 // 初始化datagrid
	 ve.initVirtualVeDatagrid();
	 // 为datagrid赋值
	 ve.searchVirtualVeInfo();
</script>
