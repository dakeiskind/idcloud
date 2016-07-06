<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   	 
   	 <div style="width:100%;height:100%;">
		<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
	           <table border="0" height="100%" cellspacing="0" cellpadding="2">
	            	<tr>
	            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;vios分区名称：</td>
	           			<td><input type="text" id="search-vios-host-name" />&nbsp;&nbsp;</td>
	           			<td><a onclick="searchViosHost()" id="search-vios-host-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
	            	</tr>
	            </table>
	    </div>
	    <div style="width:98%;height:80%;margin-left:1%;"> 
	     	<div id='viosHostdatagrid' style="width:100%;height:450px;"></div> 
	    </div>		
	</div>
         
  <script type="text/javascript">
	  function initViosHostModel(){
		    var viosHost = new viosHostModel();
		    viosHost.initInput();
		  	viosHost.initViosHostDatagrid();
		    viosHost.searchViosHostInfo();
		    
	  }
	    
  </script>