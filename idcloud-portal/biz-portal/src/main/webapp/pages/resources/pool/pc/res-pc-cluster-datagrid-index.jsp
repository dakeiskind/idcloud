<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;集群名称：</td>
           			<td><input type="text" id="search-pc-name" />&nbsp;&nbsp;</td>
           			<td align="right" nowrap="nowrap">&nbsp;&nbsp;资源环境：</td>
           			<td><div id="search-pc-virtualev" ></div></td>
           			<td>&nbsp;&nbsp;<a onclick="searchPcCluster()" id="search-pc-cluster-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='poolPcRelationClusterDatagrid' style="width:100%;height:450px;"></div> 
    </div>
</div>
  
<script type="text/javascript">
	 function initPcDatagridModel(){
		//初始化sys-index页面model
		 var pcCluster = new poolPcRelationClusterDatagridModel();
		 // 初始化页面组件
		 pcCluster.initInput();
		 // 初始化datagrid
		 pcCluster.initPcRelationClusterDatagrid();
		 // 为datagrid赋值
		 pcCluster.searchPcClusterInfo();
		 
		 // 初始化关联集群页面
		 initPcRelationCluster();
	 }
	 
</script>
