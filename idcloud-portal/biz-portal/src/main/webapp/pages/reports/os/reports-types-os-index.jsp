<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<style type="text/css">
			
		</style>   
</head>
<body>
   <div style="width:100%;height:30px;padding:5px 0px 5px 0px;">
           <table border="0" style="margin-left:10px" width="850px" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="left" width="88px" nowrap="nowrap">操作系统类型：</td>
           			<td width="130px"><div id="search-os-each-type" ></div></td>
           			<td>&nbsp;&nbsp;&nbsp;<a onclick="searchOsEachTypeCount()" id="search-host-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
           			
           			<td align="right">
           			    <a style="margin-right:-5px;"id="search-report-word-button" onclick="downloadFile(this,'word')"><i class='icons-blue icon-search-4'></i>下载Word</a>
						<a style="margin-right:-5px;"id="search-report-pdf-button" onclick="downloadFile(this,'pdf')"><i class='icons-blue icon-search-4'></i>下载PDF</a>
						<a id="search-report-excel-button" onclick="downloadFile(this,'excel')"><i class='icons-blue icon-search-4'></i>下载Excel</a>
					</td>
            	</tr>
            </table>
   </div>
   <div style="width:850px;height:93%;margin-left:10px;">
		<iframe id="osEachtypesIframe" src="" width="100%" height="100%" frameborder="no" border="1" style="border:1px solid #E5E5E5" marginwidth="0" marginheight="0"></iframe>
	</div>
</body>
<script type="text/javascript">

	$(function(){
		initInput();
		var objSearch = {
			osType:$("#search-os-each-type").val(),
			osVersionName: $("#search-os-each-type").val() + "系统统计"
		};
		var val = JSON.stringify(objSearch);
		$("#osEachtypesIframe").attr("src",ws_url + "/rest/reports/os/jasper/osVersionCount/"+val).load(function() { 
			if($("#osEachtypesIframe").contents().find("#_blank").length > 0){
				$("#search-report-pdf-button").jqxButton({disabled:true});
				$("#search-report-excel-button").jqxButton({disabled:true});
				$("#search-report-word-button").jqxButton({disabled:true});
			}else{
				$("#search-report-pdf-button").jqxButton({disabled:false});
				$("#search-report-excel-button").jqxButton({disabled:false});
				$("#search-report-word-button").jqxButton({disabled:false});
			}
		});
	});
	
	//初始化查询组件
	function initInput(){
		// 初始化下拉列表框 
		var codesearch = new codeModel({width:120,autoDropDownHeight:true});
		codesearch.getCommonCode("search-os-each-type","OS_TYPE",false);
		
		$("#search-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
		$("#search-report-pdf-button").jqxButton({ width: '80px',height:'25px',theme:currentTheme});
		$("#search-report-excel-button").jqxButton({ width: '80px',height:'25px',theme:currentTheme});
		$("#search-report-word-button").jqxButton({ width: '80px',height:'25px',theme:currentTheme});
	}
	
	// 条件查询
	function searchOsEachTypeCount(){
	    var objSearch = {
			osType:$("#search-os-each-type").val(),
			osVersionName: $("#search-os-each-type").val() + "系统统计"
		};
		var val = JSON.stringify(objSearch);
		
		$("#osEachtypesIframe").attr("src",ws_url + "/rest/reports/os/jasper/osVersionCount/"+val).load(function() { 
			if($("#osEachtypesIframe").contents().find("#_blank").length > 0){
				$("#search-report-pdf-button").jqxButton({disabled:true});
				$("#search-report-excel-button").jqxButton({disabled:true});
				$("#search-report-word-button").jqxButton({disabled:true});
			}else{
				$("#search-report-pdf-button").jqxButton({disabled:false});
				$("#search-report-excel-button").jqxButton({disabled:false});
				$("#search-report-word-button").jqxButton({disabled:false});
			}
		});
	}
	
	// 文件下载
	function downloadFile(obj,type){
		var isok = $(obj).jqxButton("disabled");
		if(!isok){
		 	var objSearch = {
				osType:$("#search-os-each-type").val(),
				osVersionName: $("#search-os-each-type").val() + "系统统计",
				downloadType: type
			};
			var val = JSON.stringify(objSearch);
			window.location = ws_url + "/rest/reports/os/export/jasper/osVersionCount/"+val; 
		}

	}
</script>

</html>