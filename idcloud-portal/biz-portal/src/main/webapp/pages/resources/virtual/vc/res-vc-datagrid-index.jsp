<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;集群名称：</td>
           			<td><input type="text" id="search-vcname" />&nbsp;&nbsp;</td>
           			<td><a onclick="searchVC()" id="search-vcbutton"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='vcdatagrid' style="width:100%;height:450px;"></div> 
    </div>
	
</div>

<script type="text/javascript">
//初始化sys-index页面model
	function vcDatagridModel(){
		var vc = new virtualVcDatagridModel();
		 // 初始化页面组件
		 vc.initVCInput();
		 // 初始化datagrid
		 vc.initVCDatagrid();
		 // 为datagrid赋值
		 vc.searchVCInfo();
	
    }
	 
</script>
