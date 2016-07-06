<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;网络名称：</td>
            			<td><input type="text" id="search-pni-networkName" />&nbsp;&nbsp;</td>
            			<td align="right" nowrap="nowrap">&nbsp;&nbsp;IP地址：</td>
            			<td>
            				<input type="text" id="search-pni-ip" />&nbsp;&nbsp;
            			</td>
            			<td><input type="button" value="查询" onclick='searchPniNetwork()' id='search-pni-network-button' /></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='poolPniNetworkdatagrid' style="width:100%;height:450px;"></div> 
    </div>  
       
   <%@ include file="res-pni-add-index.jsp"%>
   <%@ include file="res-pni-edit-index.jsp"%>
   <%@ include file="res-pni-mgtIp-index.jsp"%>
   <%@ include file="res-pni-remark-ip-index.jsp"%>
   <%@ include file="res-pni-relation-host-index.jsp"%>
       
    <script type="text/javascript">
	    function initPniNetworkDatagrid(){
	    	 var network = new poolPniDatagridModel();
		   	 network.initInput();
		   	 network.initPoolPniDatagrid();
		   	 network.searchPoolPniInfo();
	    }
    </script>
