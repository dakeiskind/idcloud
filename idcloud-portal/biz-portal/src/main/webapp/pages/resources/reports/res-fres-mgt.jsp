<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/common/resources.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
<script type="text/javascript" src="${ctx}/js/resources/reports/res-fres-mgt.js"></script>
<style type="text/css">
#containerResource {
	width: 99.9%;
	height: 100%;
	margin: 0px;
}

#mainSplitterResource {
	width: 99%;
	height: 98%;
	margin-left: 0.5%;
}

#mainSplitterResource .listHost {
	width: 15%;
	height: 100%;
	float: left;
	border: 1px solid #E5E5E5;
}

#mainSplitterResource .listContent {
	width: 84.1%;
	height: 100%;
	margin-left: 0.5%;
	float: left;
	border: 1px solid #E5E5E5;
}

.textfiledSearch {
	font-size: 12px;
	font-family: '微软雅黑', 'Microsoft Yahei', 'segoe ui', arial, sans-serif;
	border-color: #e5e5e5;
	color: #767676;
	background-color: #fff;
}
</style>
</head>
<body class='default'>
	<div id="containerFres">
		<div id='jqxWidget'>
			<div>
				<div class="searchInfoDiv" style="width: 100%; height: 30px; padding: 10px 0px 10px 0px;">
					<table border="0" height="100%" cellspacing="0" cellpadding="2">
						<tr >
							<td align="right" nowrap="nowrap">
								<font class="textfiledSearch">&nbsp;&nbsp;&nbsp;&nbsp;业务属性：</font></td>
							<td>
								<div id="search-fbizs" ></div>
							</td>
							<td align="right" nowrap="nowrap">
								<font class="textfiledSearch">&nbsp;&nbsp;&nbsp;&nbsp;项目名称：</font></td>
							<td>
								<div id="search-sbizs" ></div>
							</td>
							<td align="right" nowrap="nowrap">
								<font class="textfiledSearch">&nbsp;&nbsp;&nbsp;&nbsp;状态：</font></td>
							<td>
								<div id="search-status" ></div>
							</td>
							<td align="right" nowrap="nowrap">
								<font class="textfiledSearch">&nbsp;&nbsp;&nbsp;&nbsp;创建时间：</font>
							</td>
							<td>
								<div id="start-year"></div>
							</td>
							<td>
								<font class="textfiledSearch">年</font>
							</td>
							<td>
								<div id="start-month"></div>
							</td>
							<td>
								<font class="textfiledSearch">月</font>
							</td>
							<td>
								<div id="start-week"></div>
							</td>
							<td><font class="textfiledSearch">&nbsp;至&nbsp;</font></td>
							<td>
								<div id="finish-year"></div>
							</td>
							<td>
								<font class="textfiledSearch">年</font>
							</td>
							<td>
								<div id="finish-month"></div>
							</td>
							<td>
								<font class="textfiledSearch">月</font>
							</td>
							<td>
								<div id="finish-week"></div>
							</td>
							<td><a data-bind="click: searchResource" id="search-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
							<td><a id="search-reset-button"
								class="icons-blue icon-cancel-5 jqx-rc-all jqx-rc-all-metro jqx-button jqx-button-metro jqx-widget jqx-widget-metro jqx-fill-state-normal jqx-fill-state-normal-metro"
								style="display: inline; white-space: nowrap; width: 50px; height: 25px;">清除条件&nbsp;</a></td>
						</tr>
					</table>
				</div>
			    <div style="width:98%;height:80%;margin-left:1%;"> 
			     	<div id='resourceDatagrid' style="width:100%;height:450px;"></div> 
			    </div>   
			</div>
		</div>
		
		<div id="changeInfoWindow">
            <div>配置调整建议</div>
            <div id="changeInfoForm" style="overflow: hidden;border:0px;padding-top: 3px;padding-left: 3px;padding-right: 3px;" >
            
	        <div id='jqxTabs'>
	            <ul >
	                <li style="margin-left: 30px;">资源信息</li>
	                <li>回收建议</li>
	            </ul>
		        <div >
	        	   <table border="0" width="100%" cellspacing="0" cellpadding="0"  class="textfiledSearch">
	        	   		<tr>
							<td colspan="4" height="18" >
								&nbsp;
							</td>
						</tr>
						<tr>
	        	   			<td width="25%" align="right" height="25px">CPU核数：</td>
	        	   			<td width="25%" align="left"><span id="cpuCores" style="color:#D45753">4</span></td>
	        	   			<td width="25%" align="right">CPU使用率(%)：</td>
	        	   			<td width="25%" align="left"><span id="cpuUsage" style="color:#D45753">40</span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" height="25px">内存大小(G)：</td>
	        	   			<td align="left"><span id="memorySize" style="color:#D45753">200</span></td>
	        	   			<td align="right">内存使用率(%)：</td>
	        	   			<td align="left"><span id="memoryUsage" style="color:#D45753">20</span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" height="25px">存储大小(G)：</td>
	        	   			<td align="left"><span id="storageSize" style="color:#D45753"></span></td>
	        	   			<td align="right">存储使用率(%)：</td>
	        	   			<td align="left"><span id="storageUsage" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td align="right" height="25px">内网网络：</td>
	        	   			<td align="left"><span id="pubIp" style="color:#D45753"></span></td>
	        	   			<td align="right">外网网络：</td>
	        	   			<td align="left"><span id="privateIp" style="color:#D45753"></span></td>
	        	   		</tr>
	        	   		<tr>
							<td colspan="4" height="18" >
								&nbsp;
							</td>
						</tr>
	        	   		<tr>
							<td colspan="4" align="center">
								<div id="diskList"></div>
							</td>
						</tr>
	        	   </table>	
		        </div>
  				<div>
			        <table width="450px" align="center" border="0" cellpadding="0"	cellspacing="10px" class="textfiledSearch" >

						<tr>
							<td colspan="2" height="18" >
								&nbsp;
							</td>
						</tr>
						<tr>
	        	   			<td align="right" height="25px" width="20%">回收配置建议：</td>
	        	   			<td align="left">
								<textarea id="recoveryDesc" style="width: 90%; height: 100px;"></textarea>
							</td>
	        	   		</tr>	 
	        	   		<tr>
							<td colspan="2" height="18" >
								&nbsp;
							</td>
						</tr>       	   		
						<tr>
	                        <td align="right" colspan="2">
	                        <input style="margin-right: 5px;" onclick='submitChangeInfo()' type="button" id="Save" value="保存" />
	                        <input id="Cancel" type="button" value="取消" /></td>
	                    </tr>					
					</table>
				</div>
            </div>
       </div> 
	</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$('#jqxTabs').jqxTabs({ width: '100%', height: 360,theme:currentTheme, position: 'top'});
		$('#jqxTabs').jqxTabs({ animationType: 'fade' });
		$('#jqxTabs').jqxTabs({ selectionTracker: true });
		
		var resource = initResourcePageJs();

		var bindmodel = new reportsBindModel(resource);
		//将model和view绑定
		ko.applyBindings(bindmodel);
	});

	function initResourcePageJs() {

		var resourcemodel = new resourceModel();
		// 初始化页面组件
		resourcemodel.initInput();
		// 初始化datagrid
		resourcemodel.initResourceDatagrid();
		// 为datagrid赋值
		resourcemodel.searchResourceReport();
		
		// 初始化弹出框
		resourcemodel.initPopWindow();
		
		return resourcemodel;
	}
	
	function initYearOp(obj,start,end){
       obj.length = 1;
       for(var i = start;i<=end;i++){
           try{
             obj.add(new Option(i,i),null);
           }catch(ex){
             obj.add(new Option(i,i));
           }
       }
   }
	
	$("#search-reset-button").on("click", function(event) {
		$("#search-fbizs").jqxDropDownList('selectedIndex',0);
		$("#search-sbizs").jqxDropDownList('selectedIndex',0);
		$("#search-status").jqxDropDownList('selectedIndex',-1);
		$("#start-year").jqxDropDownList('selectedIndex',-1);
		$("#start-month").jqxDropDownList('selectedIndex',-1);
		$("#start-week").jqxDropDownList('selectedIndex',-1);
		$("#finish-year").jqxDropDownList('selectedIndex',-1);
		$("#finish-month").jqxDropDownList('selectedIndex',-1);
		$("#finish-week").jqxDropDownList('selectedIndex',-1);
	});
</script>
</html>