<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="pcRelationClusterWindow" style="display:none">
         <div>关联集群</div>
         <div id="pcRelationClusterForm" style="overflow: hidden;">
             <div style="width:100%;height:100%;">
             	 <div style="width:120px;height:100%;float:left;border-right:1px solid #DADADA;">
             	 	<ul style="list-style:none;width:100%;height:80%;margin:0px;padding:0px;">
             	 		<li id="virtualInfo" style="width:100%;height:35px;margin-top:10px;color:#0099d7"><i class='icon-ok'></i>选择资源集群</li>
             	 		<li id="confirmInfo"style="width:100%;height:35px;color:gray"><i class='icon-ok'></i>配置存储</li>
             	 	</ul>
             	 </div>
             	 <div style="position:relative;width:566px;height:100%;float:left;overflow:hidden;">
             	 	<div id="veInfo" style="position:absolute;top:0px;left:0px;width:100%;height:100%;">
             	 		<!-- 选择资源集群 -->
             	 		<div style="width:100%;height:35px;">
             	 			<table border="0" height="100%" cellspacing="0" cellpadding="2">
				            	<tr>
				            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;集群名称：</td>
				           			<td><input type="text" id="search-cluster-name" />&nbsp;&nbsp;</td>
				           			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;资源环境：</td>
				           			<td><div id="search-cluster-virtualev"></div></td>
				           			<td><a onclick="searchRelationCluster()" id="search-relation-cluster-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
				            	</tr>
				            </table>
             	 		</div>
             	 		<div id='pcRelationClusterDatagrid' style="width:98.6%;margin-left:0.7%;"></div>
             	 		<div style="width:99.3%;height:40px;margin-left:0.8%;text-align:right;line-height:40px;">
             	 			<input style="margin-right: 5px;" type="button" onclick="gotoNextConfigStorage()" id="addPcRelationClusterSave" value="下一步" />&nbsp;&nbsp;
              				<input id="addPcRelationClusterCancel" type="button" value="取消" /> &nbsp;
             	 		</div>
             	 			
             	 	</div>
             	 	<div id="confirmVeInfo" style="position:absolute;top:0px;left:566px;width:100%;height:100%;">
             	 		<div style="width:100%;height:35px;line-height:35px">
             	 			<font>&nbsp;&nbsp;<b>配置存储</b></font>
             	 		</div>
             	 		<div id='pcRelationConfigStorageDatagrid' style="width:98.6%;margin-left:0.7%;"></div>
             	 		<div style="width:99.3%;height:40px;margin-left:0.8%;text-align:right;line-height:40px;">
             	 			<input style="margin-right: 5px;" type="button" onclick="gobackChooseStorage()" id="addConfigStorage" value="上一步" />&nbsp;&nbsp;
              				<input style="margin-right: 5px;" type="button" onclick="submitRelationClusterAndStorage()" id="confirmAddStorage" value="确定" />&nbsp;&nbsp;
              				<input id="addStorageCancel" onclick="closeRelationClusterWindow()" type="button" value="取消" /> &nbsp;
             	 		</div>
             	 	</div>	
             	 
             	 </div>
             </div>
         </div>
 </div>

<script type="text/javascript"> 

	function initPcRelationCluster(){
		//初始化sys-index页面model
		var relation = new poolPcRelationClusterModel();
		// 初始化组件
		relation.initInput();
		// 初始化弹出框
		relation.initPopWindow();
		// 初始化datagrid
		relation.initDatagrid();
	}
	 
</script>
