<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="customNetworkWindow" style="width:100%;height:100%;">
	<div>自定义网络</div>
	<div id="customNetworkForm" style="width:100%;height:30px;">
	     <div style="width:100%;height:32px;">
	         <table border="0" cellspacing="0" cellpadding="2">
	          	<tr>
	          		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;网络名称：</td>
	       			<td><input type="text" id="search-custom-network-name" />&nbsp;&nbsp;</td>
	       			<td><a onclick="searchCustomNetworkMgt()" id="search-pc-relation-host-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
	          	</tr>
	         </table>
         </div>
         <div style="width:98%;height:340px;margin-left:1%;"> 
	     	<div id='customNetworkDatagrid' style="width:100%;height:450px;"></div> 
	    </div>
	    <div style="width:98%;height:30px;margin-left:1%;text-align:right;"> 
	     	<input id="customNetworktCancel" style="margin-top:5px;" type="button" value="取消" />&nbsp;
	    </div>
    </div>
</div>

<script type="text/javascript">
      var customNetwork = new mgtObjCustomNetworkModel();
      customNetwork.initInput();
      customNetwork.initDatagrid();
      customNetwork.initPopWindow();
</script>
