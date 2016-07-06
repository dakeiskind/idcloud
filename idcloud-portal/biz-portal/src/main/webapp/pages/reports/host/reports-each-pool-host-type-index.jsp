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
            		<td align="left" width="80px" nowrap="nowrap">资源池类型：</td>
           			<td width="130px"><div id="search-pool-each-type" ></div></td>
           			<td>&nbsp;&nbsp;&nbsp;<a onclick="searchHostEachPoolTypeCount()" id="search-host-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
           			
           			<td align="right">
           			    <a style="margin-right:-5px;"id="search-report-word-button" onclick="downloadFile(this,'word')"><i class='icons-blue icon-search-4'></i>下载Word</a>
						<a style="margin-right:-5px;"id="search-report-pdf-button" onclick="downloadFile(this,'pdf')"><i class='icons-blue icon-search-4'></i>下载PDF</a>
						<a id="search-report-excel-button" onclick="downloadFile(this,'excel')"><i class='icons-blue icon-search-4'></i>下载Excel</a>
					</td>
            	</tr>
            </table>
   </div>
   <div style="width:850px;height:93%;margin-left:10px;">
		<iframe id="hostEachPoolTypesIframe" src="" width="100%" height="100%" frameborder="no" border="1" style="border:1px solid #E5E5E5" marginwidth="0" marginheight="0"></iframe>
	</div>
</body>
<script type="text/javascript">

	$(function(){
		initInput();
		var item = $("#search-pool-each-type").jqxDropDownList('getSelectedItem');
		var objSearch = {
			poolType:item.value ,
			reportName: item.label  + "统计"
		};
 		var val = JSON.stringify(objSearch);
		$("#hostEachPoolTypesIframe").attr("src",ws_url + "/rest/reports/host/jasper/each/pool/proportion/"+val).load(function() { 
			if($("#hostEachPoolTypesIframe").contents().find("#_blank").length > 0){
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
		var poolType = [
			{name:"全部",value:"'PCVX','PCX','PCVP','PCP'"},             
			{name:"X86虚拟化资源池",value:"'PCVX'"},
			{name:"X86非虚拟化资源池",value:"'PCX'"},
			{name:"Power虚拟化资源池",value:"'PCVP'"},
			{name:"Power非虚拟化资源池",value:"'PCP'"}
		];
		
		var source ={
 	             datatype: "json",
 	             datafields: [
 	                 { name: 'name' },
 	                 { name: 'value' }
 	             ],
 	             localData: poolType
 	         };
		 var dataAdapter = new $.jqx.dataAdapter(source);
         $("#search-pool-each-type").jqxDropDownList({
             selectedIndex: 0, 
             source: dataAdapter, 
             displayMember: "name", 
             valueMember: "value", 
             dropDownHeight: 95,
             theme:currentTheme,
             width: 150, 
             height: 25
         });
		
		
		$("#search-host-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
		$("#search-report-pdf-button").jqxButton({ width: '80px',height:'25px',theme:currentTheme});
		$("#search-report-excel-button").jqxButton({ width: '80px',height:'25px',theme:currentTheme});
		$("#search-report-word-button").jqxButton({ width: '80px',height:'25px',theme:currentTheme});
	}
	
	// 条件查询
	function searchHostEachPoolTypeCount(){
		var item = $("#search-pool-each-type").jqxDropDownList('getSelectedItem');
		var objSearch = {
			poolType:item.value ,
			reportName: item.label  + "统计"
		};
		var val = JSON.stringify(objSearch);
		
		$("#hostEachPoolTypesIframe").attr("src",ws_url + "/rest/reports/host/jasper/each/pool/proportion/"+val).load(function() { 
			if($("#hostEachPoolTypesIframe").contents().find("#_blank").length > 0){
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
			var item = $("#search-pool-each-type").jqxDropDownList('getSelectedItem');
			var objSearch = {
				downloadType: type,
				poolType:item.value ,
				reportName: item.label  + "统计"
			};

			var val = JSON.stringify(objSearch);
			window.location = ws_url + "/rest/reports/host/export/jasper/each/pool/proportion/"+val; 
		}

	}
</script>

</html>