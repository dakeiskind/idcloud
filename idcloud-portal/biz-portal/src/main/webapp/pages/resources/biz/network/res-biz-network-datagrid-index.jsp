<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            			<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;网络名称：</td>
            			<td><input type="text" id="search-biz-networkName" />&nbsp;&nbsp;</td>
            			<td align="right" nowrap="nowrap">&nbsp;&nbsp;IP地址：</td>
            			<td>
            				<input type="text" id="search-biz-ip" />&nbsp;&nbsp;
            			</td>
            			<td align="right" nowrap="nowrap">&nbsp;&nbsp;网络类型：</td>
            			<td>
            				<div id="search-biz-network-type"></div>
            			</td>
            			<td><input type="button" value="查询" onclick='searchBizNetwork()' id='search-biz-network-button' /></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='bizNetworkdatagrid' style="width:100%;height:450px;"></div> 
    </div>  
       
    <script type="text/javascript">
	    function bizNetworkDatagridModelInfo(){
	    	 var network = new bizNetworkDatagridModel();
		   	 network.initInput();
		   	 network.initBizNetworkDatagrid();
		   	 network.searchBizNetworkInfo();
	    }
    </script>
