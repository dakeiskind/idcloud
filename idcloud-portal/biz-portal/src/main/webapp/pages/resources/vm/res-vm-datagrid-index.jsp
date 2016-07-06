<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;虚拟机名称：</td>
           			<td><input type="text" id="search-vm-name" />&nbsp;&nbsp;</td>
           			<td align="right" nowrap="nowrap">&nbsp;&nbsp;IP地址：</td>
           			<td>
           				<input type="text" id="search-vm-ip" />
           			</td>
           			<td align="right">&nbsp;&nbsp;状态：</td>
           			<td>
           				<div id="search-vm-status"></div>
           			</td>
           			<td>&nbsp;&nbsp;&nbsp;<a onclick="searchVm()" id="search-vm-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='vmdatagrid' style="width:100%;height:450px;"></div> 
    </div>
	
</div>

	
 <%@ include file="res-vm-detail.jsp"%>
<%@ include file="res-vm-monitor-index.jsp"%>
<%@ include file="res-vm-config-ip.jsp"%>
<%@ include file="res-vm-migrate-select.jsp"%>

<script type="text/javascript">
//初始化sys-index页面model
function VMDatagridModel(){
	var vm = new virtualVMDatagridModel();
	 // 初始化页面组件
	 vm.initVMInput();
	 // 初始化datagrid
	 vm.initVMDatagrid();
	 // 为datagrid赋值
	// vm.searchVMInfo();
}
	 
</script>
