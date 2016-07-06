<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>	
   <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;VLAN名称：</td>
            			<td><input type="text" id="search-vlan-name" />&nbsp;&nbsp;</td>
            			<td align="right">&nbsp;&nbsp;管理状态：</td>
            			<td>
            				<div id="search-vlan-mgt-status"></div>
            			</td>
            			<td align="right">&nbsp;&nbsp;使用状态：</td>
            			<td>
            				<div id="search-valn-usage-status"></div>
            			</td>
            			<td><a onclick="searchVlanConfigMgt()" id="search-vlan-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='vlanConfigMgtdatagrid' style="width:100%;height:450px;"></div> 
    </div>  
      <script type="text/javascript">
        	   var vlan = new vlanConfigMgtModel();
               vlan.initInput();
               vlan.initVlanDatagrid();
               vlan.searchVlanConfigInfo();
      </script>