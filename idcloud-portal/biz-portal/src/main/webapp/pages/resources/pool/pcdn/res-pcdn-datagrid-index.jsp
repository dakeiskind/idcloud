<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div style="width:100%;height:100%;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
            <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;CDN名称：</td>
           			<td><input type="text" id="search-pcdn-cdnNameLike"/>&nbsp;&nbsp;</td>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;所属资源环境：</td>
           			<td>
           				<div id="search-pcdn-resTopologySids"></div>
           			</td>
           			<td><a onclick="searchPcdn()" id="search-pcdn-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='poolPCDNDatagrid' style="width:100%;height:450px;"></div> 
    </div>
</div>

<script type="text/javascript">
	function initPcdnDatagridModel(){
		 //初始化sys-index页面model
		 var pcdndata = new poolPcdnDatagridModel();
		 // 初始化页面组件
		 pcdndata.initInput();
		 // 初始化datagrid
		 pcdndata.initPcdnDatagrid();
		 // 为datagrid赋值
		// posdata.searchPosInfo();
	}
	
</script>
