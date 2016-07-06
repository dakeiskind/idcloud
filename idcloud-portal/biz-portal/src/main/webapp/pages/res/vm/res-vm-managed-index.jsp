<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
       <table border="0" height="100%" cellspacing="0" cellpadding="2">
        	<tr>
        		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;虚拟机名称：</td>
        			<td><input type="text" id="search-vm-name" />&nbsp;&nbsp;</td>
        			<td align="right" nowrap="nowrap">操作系统：</td>
        			<td>
        				<div id="search-operation-system"></div>
        			</td>
        			<td align="right">&nbsp;&nbsp;所属租户：</td>
        			<td>
        				<div id="search-vm-tenant"></div>
        			</td>
        			<td align="right">&nbsp;&nbsp;所有者：</td>
        			<td>
        				<div id="search-vm-owner"></div>
        			</td>
        			<td><a onclick="searchVMMgt()" id="search-vm-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
        	</tr>
        </table>
 </div>
 <div style="width:98%;height:80%;margin-left:1%;"> 
  	<div id='vmMgtdatagrid' style="width:100%;height:450px;"></div> 
 </div> 
 <%@ include file="res-vm-reconfig.jsp"%>
 <%@ include file="res-vm-migrate-target-host.jsp"%>
 <%@ include file="res-vm-migrate-target-storage.jsp"%>
 <%@ include file="res-vm-detail.jsp"%>
 <script type="text/javascript">
 	
 </script>