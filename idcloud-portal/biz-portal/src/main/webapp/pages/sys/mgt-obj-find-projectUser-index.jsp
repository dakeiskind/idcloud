<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- <div style="width:100%;height:100%;margin:0px;padding:0px;"> -->
<!-- 	<div style="width: 100%; height: 48px; background: #F9F9F9; color: #000; line-height: 48px; border-bottom: 1px solid #DADADA">
		&nbsp;&nbsp;<font style="color: #0099d7; font-weight: bold">用户管理</font>
	</div> -->
	<div id="findProjectUsersWindow">
	<div>成员管理</div>
   
	<div style="width:100%;height:15px;padding:5px 0px 10px 0px;">
		<table height="30px;"style="font-size: 12px; margin: 5px 0px 5px 5px"
			border="0" cellpadding="3" cellspacing="0">
			<tr>
				<td  align="right" nowrap="nowrap">用户账号:</td>
				<td>
				    <input type="text" id="tenant-user-platform-account"/>
				</td>
				<td  align="right" nowrap="nowrap">用户名称:</td>
				<td>
					<input type="text" id="tenant-user-platform-name"/>
				</td>
				<td  align="right" nowrap="nowrap">用户状态:</td>
				<td>
					<div id="tenant-user-platform-status"></div>
				</td>
				<td>
					<a id="search-user-platform-button" onclick="searchTenantProUser()"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a>
				</td>
			</tr>
		</table>
		<div style="width:99%;height:90%;margin-left:0.5%;overflow-y:auto;">
	     	<div id='tenantUserPlatformDatagrid' style="width:100%;border-top:0px;border-right:0px;border-left:0px;"></div> 
	    </div>  
	</div>	
	</div>
<!-- </div> -->
	
<script type="text/javascript">
    var tenant = new tenantPlatformModel();
    tenant.initInput();
    tenant.initDatagrid();
    tenant.searchInfo();
</script>