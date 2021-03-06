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
           			<td align="right">
           			    <a style="margin-right:-5px;"id="search-report-st-word-button" onclick="downloadFile(this,'word')"><i class='icons-blue icon-download-6'></i>Word下载</a>
						<a style="margin-right:-5px;"id="search-report-st-pdf-button" onclick="downloadFile(this,'pdf')"><i class='icons-blue icon-download-6'></i>PDF下载</a>
						<a id="search-report-st-excel-button" onclick="downloadFile(this,'excel')"><i class='icons-blue icon-download-6'></i>Excel下载</a>
					</td>
            	</tr>
            </table>
   </div>
   <div style="width:850px;height:93%;margin-left:10px;">
		<iframe id="storageCountIframe" src="" width="100%" height="100%" frameborder="no" border="1" style="border:1px solid #E5E5E5" marginwidth="0" marginheight="0"></iframe>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		//初始化组件
		initInput();
		// 初始化inframe
		$("#storageCountIframe").attr("src",ws_url + "/rest/reports/st/jasper/storage/proportion").load(function() { 
			if($("#storageCountIframe").contents().find("#_blank").length > 0){
				$("#search-report-st-pdf-button").jqxButton({disabled:true});
				$("#search-report-st-excel-button").jqxButton({disabled:true});
				$("#search-report-st-word-button").jqxButton({disabled:true});
			}else{
				$("#search-report-st-pdf-button").jqxButton({disabled:false});
				$("#search-report-st-excel-button").jqxButton({disabled:false});
				$("#search-report-st-word-button").jqxButton({disabled:false});
			}
		});
	});
	
	//初始化组件
	function initInput(){
		$("#search-report-st-pdf-button").jqxButton({ width: '80px',height:'25px',theme:currentTheme});
		$("#search-report-st-excel-button").jqxButton({ width: '80px',height:'25px',theme:currentTheme});
		$("#search-report-st-word-button").jqxButton({ width: '80px',height:'25px',theme:currentTheme});
	}

    // 下载文件
	function downloadFile(obj,type){
		var isok = $(obj).jqxButton("disabled");
		if(!isok){
			window.location = ws_url + "/rest/reports/st/export/jasper/storage/proportion/"+type; 
		}
		
	}
</script>

</html>