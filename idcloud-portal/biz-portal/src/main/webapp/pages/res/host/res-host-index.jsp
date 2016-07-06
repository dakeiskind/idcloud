<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
   <div id="hostDatagrid" style="width:100%;height:100%">		
	   <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
	           <table border="0" height="100%" cellspacing="0" cellpadding="2">
	            	<tr>
	            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;主机名称：</td>
	            			<td><input type="text" id="search-host-name" />&nbsp;&nbsp;</td>
	            			<td align="right">&nbsp;&nbsp;管理状态：</td>
	            			<td>
	            				<div id="search-host-mgt-status"></div>
	            			</td>
	            			<td align="right">&nbsp;&nbsp;使用状态：</td>
	            			<td>
	            				<div id="search-host-usage-status"></div>
	            			</td>
	            			<td><a onclick="searchHostDatagrid()" id="search-host-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
	            	</tr>
	            </table>
	    </div>
	    <div style="width:98%;height:80%;margin-left:1%;"> 
	     	<div id='hostConfigMgtdatagrid' style="width:100%;height:450px;"></div> 
	    </div>
	</div>
	
	<%@ include file="res-edit-host.jsp"%>
	<%@ include file="res-detail-host.jsp"%>
	<%@ include file="res-mountstorage-host.jsp"%>
	<%@ include file="res-monitor-host.jsp"%>      
	      
  <script type="text/javascript">
  		
	    // 初始化主机列表
		function initHostConfigModel(){
			 // 初始化主机列表
    	    var host = new hostConfigModel();
    		host.initInput();
    		host.initHostDatagrid();
    		host.searchHostConfigInfo();
		}
  			
  </script>