<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Expires" CONTENT="-1"> 


<div id="<%=request.getParameter("jqxNavigationBarId")%>" style="width:99.5%;height:80%;margin-left:0.3%;margin-top:0.3%;">
	<div>服务基本信息</div>
	<div>
		<input type="hidden" id="serviceSid" value="<%=request.getParameter("serviceSid")%>" /> 
		<input type="hidden" id="jqxNavigationBarId" value="<%=request.getParameter("jqxNavigationBarId")%>" />
		<input type="hidden" id="vConfigGrid" value="<%=request.getParameter("vConfigGrid")%>" />
		<input type="hidden" id="vSpecGrid" value="<%=request.getParameter("vSpecGrid")%>" />
		<input type="hidden" id="vPerformanceGrid" value="<%=request.getParameter("vPerformanceGrid")%>" />
		<input type="hidden" id="vOperateGrid" value="<%=request.getParameter("vOperateGrid")%>" />
		<input type="hidden" id="serviceName" value="<%=request.getParameter("serviceName")%>" />
		<input type="hidden" id="description" value="<%=request.getParameter("description")%>" />

		<table class="viewTable" width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right" width="150px">服务名称：</td>
				<td id="<%=request.getParameter("serviceName")%>" width="750px"></td>
			</tr>
			<tr>
				<td align="right" width="150px">描述：</td>
				<td id="<%=request.getParameter("description")%>" width="750px"></td>
			</tr>
			<tr>
				<td align="right" width="150px">服务类型：</td>
				<td id="<%=request.getParameter("serviceTypeName")%>" width="750px"></td>
			</tr>
			<tr>
				<td align="right" width="150px">所属服务名称：</td>
				<td id="<%=request.getParameter("parentCatalogName")%>" width="750px"></td>
			</tr>
			<tr>
				<td align="right" width="150px">状态：</td>
				<td id="<%=request.getParameter("serviceStatusName")%>" width="750px"></td>
			</tr>
		</table>
	</div>
	<div>服务配置</div>
	<div>
		<div id="<%=request.getParameter("vConfigGrid")%>"
			style="height: 60%; width: 99%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
	</div>
	<div>服务规格</div>
	<div>
		<div id="<%=request.getParameter("vSpecGrid")%>"
			style="height: 60%; width: 99%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
	</div>
	<div>性能指标</div>
	<div>
		<div id="<%=request.getParameter("vPerformanceGrid")%>"
			style="height: 60%; width: 99%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>
	</div>
	<div>服务操作项</div>
	<div>
		<div id="<%=request.getParameter("vOperateGrid")%>"
			style="height: 60%; width: 99%; margin-top: 5px; font-size: 14px; overflow: hidden;"></div>

	</div>
</div>

<script type="text/javascript">
	$(function(){
		var serviceSid = '<%=request.getParameter("serviceSid")%>';
		var jqxNavigationBarId = '<%=request.getParameter("jqxNavigationBarId")%>';
		var vConfigGrid = '<%=request.getParameter("vConfigGrid")%>';
		var vSpecGrid = '<%=request.getParameter("vSpecGrid")%>';
		var vPerformanceGrid = '<%=request.getParameter("vPerformanceGrid")%>';
		var vOperateGrid = '<%=request.getParameter("vOperateGrid")%>';
		var serviceName = '<%=request.getParameter("serviceName")%>';
		var description = '<%=request.getParameter("description")%>';
		var serviceTypeName = '<%=request.getParameter("serviceTypeName")%>';
		var parentCatalogName = '<%=request.getParameter("parentCatalogName")%>';
		var serviceStatusName = '<%=request.getParameter("serviceStatusName")%>';

		$("#" + jqxNavigationBarId).jqxNavigationBar({
			theme: currentTheme,
			width : "99.5%",
			height : "390px"
		});
		
		var model = new serviceMgtModel();
		model.searchServiceMgtBaseInfo(serviceSid,vConfigGrid,vSpecGrid,vPerformanceGrid,vOperateGrid,serviceName
				,description,serviceTypeName,parentCatalogName,serviceStatusName);

	});	

</script>
