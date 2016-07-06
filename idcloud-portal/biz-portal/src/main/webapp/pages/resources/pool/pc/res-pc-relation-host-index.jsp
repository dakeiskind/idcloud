<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="pcRelationHostWindow" style="width:100%;height:100%;">
	<div>关联主机</div>
	<div id="pcRelationHostForm" style="width:100%;height:30px;">
	     <div style="width:100%;height:32px;">
	         <table border="0" cellspacing="0" cellpadding="2">
	          	<tr>
	          		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;主机名称：</td>
	       			<td><input type="text" id="search-pc-relation-host-name" />&nbsp;&nbsp;</td>
	       			<td><a onclick="searchRelationHostMgt()" id="search-pc-relation-host-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
	          	</tr>
	         </table>
         </div>
         <div style="width:98%;height:310px;margin-left:1%;"> 
	     	<div id='pcRelationHostdatagrid' style="width:100%;height:450px;"></div> 
	    </div>
	    <div style="width:98%;height:30px;margin-left:1%;text-align:right;"> 
	     	<input id="pcRelationHostSave" onclick="saveRelationHostInfo()" style="margin-top:5px;" type="button" value="保存" />
	     	<input id="pcRelationHostCancel" style="margin-top:5px;" type="button" value="取消" />&nbsp;
	    </div>
    </div>
</div>

<script type="text/javascript">
      var pcRelationHost = new pcRelationHostModel();
      pcRelationHost.initInput();
      pcRelationHost.initDatagrid();
      pcRelationHost.initPopWindow();
//      pcRelationHost.searchHostInfo();
</script>
