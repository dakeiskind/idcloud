<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div style="width:100%;height:100%;margin:0px; padding:0px;">
	<div style="width:100%;height:30px;padding:10px 0px 10px 0px;">
           <table border="0" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;名称：</td>
           			<td><input type="text" id="search-image-name" />&nbsp;&nbsp;</td>
           			<!-- <td align="right" nowrap="nowrap">区域：</td>
           			<td>
           				<div id="search-image-rType"></div>
           			</td>
           			<td align="right" nowrap="nowrap">数据中心：</td>
           			<td>
           				<div id="search-image-dcType"></div>
           			</td> -->
           			<td align="right" nowrap="nowrap">资源环境：</td>
           			<td>
           				<div id="search-image-veType"></div>
           			</td>
           			<td align="right" nowrap="nowrap">&nbsp;&nbsp;操作系统类型：</td>
           			<td>
           				<div id="search-image-osType"></div>
           			</td>
           			
           			<td><a onclick="searchImage()" id="search-image-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
            	</tr>
            </table>
    </div>
    <div style="width:98%;height:80%;margin-left:1%;"> 
     	<div id='virtualImageDatagrid' style="width:100%;height:450px;"></div> 
    </div>
</div>

<script type="text/javascript">

	function initResVirtualImage(){
		 //初始化sys-index页面model
		 var image = new virtualImageDatagridModel();
		 // 初始化页面组件
		 image.initImageInput();
		 // 初始化datagrid
		 image.initVirtualImageDatagrid();
		 // 为datagrid赋值
		 image.searchVirtualImageInfo();
		 image.initOperationBtn();
	}
</script>
