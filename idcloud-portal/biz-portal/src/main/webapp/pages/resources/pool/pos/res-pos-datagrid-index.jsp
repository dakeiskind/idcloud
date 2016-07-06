<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div style="width:100%;height:100%;">
	 <div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;对象存储名称：</td>
           			<td><input type="text" id="search-pos-objStoNameLike"/>&nbsp;&nbsp;</td>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;所属资源环境：</td>
           			<td>
           				<div id="search-pos-resTopologySids"></div>
           			</td>
           			<td><a onclick="searchPOS()" id="search-pos-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div> 
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='poolPOSDatagrid' style="width:100%;height:450px;"></div> 
    </div>
</div>

<script type="text/javascript">
	function initPosDatagridModel(){
		 //初始化sys-index页面model
		 var posdata = new poolPosDatagridModel();
		 // 初始化页面组件
		 posdata.initInput();
		 // 初始化datagrid
		 posdata.initPosDatagrid();
		 // 为datagrid赋值
		// posdata.searchPosInfo();
	}
	
</script>
