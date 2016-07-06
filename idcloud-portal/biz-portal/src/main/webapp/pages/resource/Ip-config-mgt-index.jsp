<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;IP地址：</td>
            			<td><input type="text" id="search-IP-address" />&nbsp;&nbsp;</td>
            			<td align="right" nowrap="nowrap">IP类别：</td>
            			<td>
            				<div id="search-IP-category"></div>
            			</td>
            			<td align="right" nowrap="nowrap">&nbsp;&nbsp;IP类型：</td>
            			<td>
            				<div id="search-IP-type"></div>
            			</td>
            			<td align="right">&nbsp;&nbsp;管理状态：</td>
            			<td>
            				<div id="search-IP-mgt-status"></div>
            			</td>
            			<td align="right">&nbsp;&nbsp;使用状态：</td>
            			<td>
            				<div id="search-IP-usage-status"></div>
            			</td>
            			<td><input type="button" value="查询" data-bind='click:searchIpConfigMgt' id='search-ip-button' /></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='ipConfigMgtdatagrid' style="width:100%;height:450px;"></div> 
    </div>   
    
       <script type="text/javascript">
       		function initIPConfigJs(){
	       		 // 初始化sys-index页面model
	   			 var ipconfig = new IpConfigModel();
	       		 // 初始化页面组件
	   			 ipconfig.initInput();
	       		 // 初始化组件验证规则
	   			 ipconfig.initValidator();
	       		 // 初始化弹出框
	   			 ipconfig.initPopWindow();
	       		 // 初始化datagrid
	   			 ipconfig.initIpDatagrid();
	       		 // 为datagrid赋值
	   			 ipconfig.searchIpConfigInfo();
	   	
	   			return ipconfig;
       		}
       </script>