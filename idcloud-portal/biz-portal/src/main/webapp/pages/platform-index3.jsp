<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		
		<!-- virtual -->
		<script type="text/javascript" src="${ctx}/js/resources/virtual/res-ve-datagrid-model.js"></script>
		<script type="text/javascript" src="${ctx}/js/resources/virtual/res-vc-datagrid-model.js"></script>
		<!-- host -->
		<script type="text/javascript" src="${ctx}/js/resources/host/res-host-datagrid-model.js"></script>
		<!-- vm -->
		<script type="text/javascript" src="${ctx}/js/resources/vm/res-vm-datagrid-model.js"></script>
		
		<!-- hightcharts -->
		<script src="${ctx}/scripts/highcharts/highcharts.js"></script>
		<script src="${ctx}/scripts/highcharts/highcharts-more.js"></script>
		<script src="${ctx}/scripts/highcharts/highcharts-3d.js"></script>
		<script src="${ctx}/scripts/highcharts/modules/exporting.js"></script>
		<link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.ui-start.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.orange.css" type="text/css" />
	    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.summer.css" type="text/css" />
	    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.ui-le-frog.css" type="text/css" />
	    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.green.css" type="text/css" />
	    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.red.css" type="text/css" />
	    <script type="text/javascript" src="${ctx}/jqwidgets/jqxcore.js"></script>
	    <script type="text/javascript" src="${ctx}/jqwidgets/jqxdraw.js"></script>
	    <script type="text/javascript" src="${ctx}/jqwidgets/jqxchart.core.js"></script>
	    <script type="text/javascript" src="${ctx}/jqwidgets/jqxdata.js"></script>
	    <style type="text/css">
	    	.spanFloat {
			    background-position: 0 -210px;
			    color: #fff;
			    cursor: pointer;
			    font-family: Microsoft Yahei;
			    font-size: 11px;
			    height: 12px;
			    line-height: 11px;
			    padding: 0.1em 0.4em 0.2em;
			    position: absolute;
			    right: -2px;
			    text-align: center;
			    top: -25px;
			    background: #f16433;
			    box-shadow:0 1px 1px rgba(0,0,0,0.3);
			    border-radius:2px;
			    text-shadow:0 1px 1px rgba(0,0,0,0.3);
			}
			.indexHref{
				width: 25%;
				font-family: Microsoft Yahei;
				font-size: 14px;
				color:#0496f2;
				position: relative;
			}
			.titleFont{
				font-family: Microsoft Yahei;
				font-size: 14px;
			}
			.indexDiv{
				width:49.7%;
				height:100%;
				text-align: center;
				margin-left: 0.1%;
			}
			.progressBar{
				width: 20%;
				text-align: left;
				font-family:  Microsoft Yahei;
				font-weight: bold;
				font-size: 12px;
			}
			.progressFont{
				font-family:  Arial;
				font-weight: bold;
				font-size: 14px;
			}
			.chartStyle{
				width:100%; 
				height:95%;
				left: 0px;
				border:0px;
			}
	    </style>
</head>
<body>
	<div style="height:3%;width100%;text-align: right;position:relative;">
		<a href="#" onclick="getAheadPage()" style="color: #0099d7;"><i style="font-size:15px;" class='icon-left-open'></i></a>
		<span id="pageNum" style="color: #0099d7;font-size:15px;"></span>
		<a href="#" onclick="getBehindPage()" style="color: #0099d7;"><i style="font-size:15px;" class='icon-right-open'></i></a>
		<a id="full" href="#" onclick="doFull()" style="color: #0099d7;"><i style="font-size:15px" class='icon-resize-small-2'></i></a>
	</div>
	<div id="first" style="height:41%;width:100%;text-align: center;margin-bottom: 0.3%;">
		<div class="customPanel indexDiv" style="float:left;" id="chart-1">
			<div class="title titleFont">&nbsp;&nbsp;<strong></strong></div>
			<a href="###" style="width:100%; height:100%;"><div style="width:100%; height:95%;"></div></a>
		</div>
		<div class="customPanel indexDiv" style="float:right;" id="chart-2">
			<div class="title titleFont">&nbsp;&nbsp;<strong></strong></div>
			<a href="###" style="width:100%; height:100%;"><div style="width:100%; height:95%;"></div></a>
		</div>
	</div>
	<div id="second" style="height:41%;width:100%;">
		<div class="customPanel indexDiv" style="float:left;" id="chart-3">
			<div class="title titleFont">&nbsp;&nbsp;<strong></strong></div>
			<a href="###" style="width:100%; height:100%;"><div style="width:100%; height:95%;"></div></a>
		</div>
 		<div class="customPanel indexDiv" style="float:right;" id="chart-4">
			<div class="title titleFont">&nbsp;&nbsp;<strong></strong></div>
			<a href="###" style="width:100%; height:100%;"><div style="width:100%; height:95%;"></div></a>
		</div>
	</div>
	<div style="height:13.5%;width:100%;margin-top: 0.4%;">
		<table style="width: 100%;height: 98%;border:1px solid #e9e9e9;border-collapse:collapse;text-align: center;">
			<tr>
				<td style="border-right: 2px solid #0496f2;width: 70%;">
					<table style="width: 100%;height: 100%">
						<tr style="height: 30%;">
							<td class="progressBar" style="color: #5b9bd5;">CPU核数<span id="cpuPercent" style="float: right;">&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
							<td class="progressBar" style="color: #ed7d31;">虚拟内存<span id="memPercent" style="float: right;">&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
							<td class="progressBar" style="color: #71ae48;">SAN存储<span id="SANPercent" style="float: right;">&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
							<td class="progressBar" style="color: #71ae48;">NAS存储<span id="NASPercent" style="float: right;">&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
							<td class="progressBar" style="color: #71ae48;">总存储<span id="stPercent" style="float: right;">&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
						</tr>
						<tr>
							<td><div style='overflow: hidden;' id='cpuProgressBar'></div></td>
							<td><div style='overflow: hidden;' id='memProgressBar'></div></td>
							<td><div style='overflow: hidden;' id='sanProgressBar'></div></td>
							<td><div style='overflow: hidden;' id='nasProgressBar'></div></td>
							<td><div style='overflow: hidden;' id='stProgressBar'></div></td>
						</tr>
						<tr style="height: 30%;">
							<td style="width: 20%" align="center"><span class="progressFont" style="color: #5b9bd5;" id="cpuText"></span></td>
							<td style="width: 20%" align="center"><span class="progressFont" style="color: #ed7d31;" id="memText"></span></td>
							<td style="width: 20%" align="center"><span class="progressFont" style="color: #71ae48;" id="SANText"></span></td>
							<td style="width: 20%" align="center"><span class="progressFont" style="color: #71ae48;" id="NASText"></span></td>
							<td style="width: 20%" align="center"><span class="progressFont" style="color: #71ae48;" id="stText"></span></td>
						</tr>
					</table>
				</td>
				<td>
					<table style="width: 100%">
						<tr>
							<td><a href="#" onclick="showPage('bizres')" class="indexHref"><img alt="业务资源" src="${ctx}/images/icon/index_biz.png" style="height: 70%;"><br>业务资源</a></td>
							<td><a href="#" onclick="showPage('res')" class="indexHref"><img alt="资源管理" src="${ctx}/images/icon/index_res.png" style="height: 70%;"><br>资源管理</a></td>
							<td><a href="#" onclick="showPage('approve')" class="indexHref" ><img alt="待审核" src="${ctx}/images/icon/index_uncheck.png" style="height: 70%;"><br>待审核<span id="approveNum" class="spanFloat"></span></a></td>
							<td><a href="#" onclick="showPage('solve')" class="indexHref"><img alt="待解决" src="${ctx}/images/icon/index_unsolve.png" style="height: 70%;"><br>待解决<span id="ticketNum" class="spanFloat"></span></a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="fullDiv" style="display: none;">
		<div style="width:100%; height:95%;">
			<div id='fullScreen' style="width:790px;height:550px;"></div>
		</div>
	</div>
	<div id="fullResUsedDiv" style="display: none;">
		<div style="width:100%; height:95%;">
			<div style="width:100%;height:50px;float: right;">
				<input type="hidden" id="chartType" />
				 <table border="0" height="100%" cellspacing="0" cellpadding="2">
					<tr >
						<td align="right" nowrap="nowrap">
							<font class="textfiledSearch">&nbsp;&nbsp;&nbsp;&nbsp;业务属性：</font></td>
						<td>
							<div id="resUsedBiz" ></div>
						</td>
						<td align="right" nowrap="nowrap">
							<font class="textfiledSearch">&nbsp;&nbsp;&nbsp;&nbsp;时间：</font></td>
						<td>
							按&nbsp;&nbsp;
						</td>
						<td>
							<div id="resUsedDate-type"></div>
						</td>
						<td id="syear">
							<div id="resUsedDate-syear"></div>
						</td>
						<td id="syearText">年</td>
						<td id="smonth">
							<div id="resUsedDate-smonth"></div>
						</td>
						<td id="smonthText">月</td>
						<td id="sday">
							<div id="resUsedDate-sday"></div>
						</td>
						<td id="sdayText">日</td>
						<td id="flag1">&nbsp; ~ &nbsp;</td>
						<td id="eyear">
							<div id="resUsedDate-eyear"></div>
						</td>
						<td id="eyearText">年</td>
						<td id="emonth">
							<div id="resUsedDate-emonth"></div>
						</td>
						<td id="emonthText">月</td>
						<td id="eday">
							<div id="resUsedDate-eday"></div>
						</td>
						<td id="edayText">日</td>
						<td>
							<a id="countData" onclick="searchResUsed()"><i class='icons-blue icon-search-4'></i>统计&nbsp;</a>
						</td>
					</tr>
				</table>
			</div>
			<div id='fullResScreen' style="width:790px;height:500px;"></div>
		</div>
	</div>
	<div id="fullDiv1" style="display: none;">
		<div style="width: 580px;height:800px;" id="fullScreen1">
			<div style='float:left;width:15%;font-size:16px;'>&nbsp;&nbsp;刀片服务器<br/><i class='icon-doc-text' style='font-size:60px;color:#0B36FF;'></i></div>
			<div style='width:40%; height:48%;float:left;'><div id='dpPieFull' style='height:265px;width:270px;'></div></div>
			<div style='float:left;width:15%;font-size:16px;'>&nbsp;&nbsp;机架服务器<br/><i class='icon-doc-text' style='font-size:60px;color:#0B36FF;'></i></div>
			<div style='width:30%; height:48%;float:left;'><div id='jjPieFull' style='height:265px;width:230px;'></div></div>
		</div>
	</div>
	<div id="fullDiv2" style="display: none;">
		<div style="width:90%; height:90%;">
			<div id="bizfullScreen1" style="width:380px;height:550px;float:left;border:0px;"></div>
			<div id="vmfullScreen2" style="width:380px;height:550px;margin-left:0.5%;float:left;border:0px;"></div>
		</div> 
	</div>
</body>
<script type="text/javascript">
var indexdata;
	$(function(){
 		window.parent.coverDiv("cover");
		Core.AjaxRequest({ 
			url : ws_url + "/rest/count/findIndexChartForShow",
			type : "POST",
			async: false,
			callback : function (data) {
				if(data!=null&&data!=""&&data.length!=0){
					indexdata = data;
					var len = data.length;
					var count = parseInt(len/4);
					if(len%4>0){
						count = count +1;
					}
					$("#pageNum").html("<strong>1</strong>/<span>"+count+"</span>");
					var chartData = new Array;
					for(var i=0;i<data.length&&i<4;i++){
						chartData.push(data[i]);
					}
					doShowDiv(chartData);
				}
			} 
		});
		//初始化下面的进度条
		initProgressBar();
		//初始化待审核、待解决的个数
		initUnresolvedNum();
		initWindow();
		window.parent.operatPage("up");
		
		var cla = 'icon-resize-small-2';
		$("#full").find('i').attr('class',cla);
		
	});
	
	function doShowDiv(data){
		for(var i=0;i<data.length&&i<4;i++){
			var ids = new Array();
			ids = data[i].configKey.split(".");
			if(ids.length==2){
				var id = ids[0];
				var title = data[i].configName;
				$("#chart-"+(i+1)).children(".title").find("strong").html(title);
				if(id=="equipmentInfo"){
					var div = $("#chart-"+(i+1)).children(".title").next().children('div');
					initEquipmentInfo("equipmentInfo",div);
					$(div).css('color','black');
					$(div).on('click',function(){toFullScreen('equipmentInfo','设备信息图','')});
				}else{
					//找到包含chart的div
					$("#chart-"+(i+1)).children(".title").next().children('div').html("<div id='"+id+"' class='chartStyle'></div>");
					if(id=="bizResUsed"){
						initBizRes("bizResUsed");
					}else if(id=="iostatUsed"){
						initIostat("iostatUsed");
					}else if(id=="resUsageTrend"){
						initResUsed("resUsageTrend",{});
					}else if(id=="fresMsg"){
						initFreeRes("fresMsg");
					}else if(id=="ioStatRank"){
						$("#chart-"+(i+1)).children(".title").append("<div style='text-align: right;float: right;'>"+
								"<a href='#' onclick='changeIoRank(this)'><i style='font-size:14px;' class='icon-down-3'></i></a>"+
								"</div>");
						initIostatRank("DESC","ioStatRank");
					}else if(id=="resUsage"){
						initResUsage("resUsage",{});
					}else if(id=="bizDiskUsage"){
						$("#chart-"+(i+1)).children(".title").append("<div style='text-align: right;float: right;'>"+
								"<a href='#' onclick='changeStRank(this)'><i style='font-size:14px;' class='icon-down-3'></i></a>"+
								"</div>");
						initBizDiskUsage("DESC","bizDiskUsage");
					}else if(id=="alarmInfo"){
						initAlarmInfo("alarmInfo");
					}
				}
			}else if(ids.length==3){
				var id1 = ids[0];
				var id2 = ids[1];
				var title = data[i].configName;
				$("#chart-"+(i+1)).children(".title").find("strong").html(title);
				$("#chart-"+(i+1)).children(".title").next().children('div').html("<div id='"+id1+"' style='width:49%; height:95%;float:left;border:0px;'></div>"+
																  "<div id='"+id2+"' style='width:49%; height:95%;margin-left:0.5%;float:left;border:0px;'></div>");
				initVMChart("vmCount");
				initBizChart("bizCount");
			}
		}
	}
	
	//全屏
	function doFull(){
		var icon = $("#full").find('i').attr('class');
		if(icon=='icon-resize-full-3'){
			window.parent.operatPage("up");
			$("#full").find('i').attr('class','icon-resize-small-2');
		}
		if(icon=='icon-resize-small-2'){
			window.parent.operatPage("down");
			$("#full").find('i').attr('class','icon-resize-full-3');
		}
	}
	 //下一页
	 function getBehindPage(){
		 //判断是否有下一页
		 var prex = $("#pageNum").find('strong').html();
		 var next = $("#pageNum").find('span').html();
		 if(parseInt(prex)<parseInt(next)){
			 $(".title").html('&nbsp;&nbsp;<strong></strong>');
			 $(".title").next().children('div').html('');
			 $("#pageNum").html("<strong>"+parseInt(parseInt(prex)+1)+"</strong>/<span>"+next+"</span>");
			 var chartData = new Array;
			 for(var i=parseInt(prex)*4,j=0;i<indexdata.length&&j<4;i++,j++){
				 chartData.push(indexdata[i]);
			 }
			 doShowDiv(chartData);
		 }
	 }
	 
	 //上一页
	 function getAheadPage(){
		//判断是否有下一页
		 var prex = $("#pageNum").find('strong').html();
		 var next = $("#pageNum").find('span').html();
		 if(parseInt(prex)>1){
			 $(".title").html('&nbsp;&nbsp;<strong></strong>');
			 $(".title").next().children('div').html('');
			 $("#pageNum").html("<strong>"+parseInt(parseInt(prex)-1)+"</strong>/<span>"+next+"</span>");
			 var chartData = new Array;
			 for(var i=parseInt(parseInt(prex)-2)*4,j=0;i<indexdata.length&&j<4;i++,j++){
				 chartData.push(indexdata[i]);
			 }
			 doShowDiv(chartData);
		 }
	 }
	 
	function initProgressBar(){
		Core.AjaxRequest({
			url : ws_url + "/rest/count/getProgressData",
			type : 'post',
			async:false,
			callback : function(data) {
				$("#cpuPercent").html(data.percent.cpu+"%&nbsp;&nbsp;&nbsp;&nbsp;");
				$("#memPercent").html(data.percent.mem+"%&nbsp;&nbsp;&nbsp;&nbsp;");
				$("#SANPercent").html(data.percent.san+"%&nbsp;&nbsp;&nbsp;&nbsp;");
				$("#NASPercent").html(data.percent.nas+"%&nbsp;&nbsp;&nbsp;&nbsp;");
				$("#stPercent").html(data.percent.st+"%&nbsp;&nbsp;&nbsp;&nbsp;");
				
				$("#cpuText").html(data.value.cpu);
				$("#memText").html(data.value.mem);
				$("#SANText").html(data.value.san);
				$("#NASText").html(data.value.nas);
				$("#stText").html(data.value.st);
				if(data.percent.cpu<=60){
					$("#cpuProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.cpu ,showText:false,theme:'green'});
				}else if(data.percent.cpu>=80){
					$("#cpuProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.cpu ,showText:false,theme:'red'});
				}else{
					$("#cpuProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.cpu ,showText:false,theme:'summer'});
				}
				if(data.percent.mem<=60){
					$("#memProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.mem ,showText:false,theme:'green'});
				}else if(data.percent.mem>=80){
					$("#memProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.mem ,showText:false,theme:'red'});
				}else{
					$("#memProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.mem ,showText:false,theme:'summer'});
				}
				if(data.percent.san<=60){
					$("#sanProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.san ,showText:false,theme:'green'});
				}else if(data.percent.san>=80){
					$("#sanProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.san ,showText:false,theme:'red'});
				}else{
					$("#sanProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.san ,showText:false,theme:'summer'});
				}
				if(data.percent.nas<=60){
					$("#nasProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.nas ,showText:false,theme:'green'});
				}else if(data.percent.nas>=80){
					$("#nasProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.nas ,showText:false,theme:'red'});
				}else{
					$("#nasProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.nas ,showText:false,theme:'summer'});
				}
				if(data.percent.st<=60){
					$("#stProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.st ,showText:false,theme:'green'});
				}else if(data.percent.st>=80){
					$("#stProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.st ,showText:false,theme:'red'});
				}else{
					$("#stProgressBar").jqxProgressBar({ width: '90%', height: 18, value:data.percent.st ,showText:false,theme:'summer'});
				}
			}
		});
	}
	
	function initUnresolvedNum(){
		Core.AjaxRequest({ 
			url : ws_url + "/rest/count/findUnresolvedNum",
			type : "POST",
			async: false,
			callback : function (data) {
				if(parseInt(data.approve)>0){
					$("#approveNum").show();
					$("#approveNum").html(data.approve);
				}else{
					$("#approveNum").hide();
				}
				if(parseInt(data.ticket)>0){
					$("#ticketNum").show();
					$("#ticketNum").html(data.ticket);
				}else{
					$("#ticketNum").hide();
				}
			} 
		});
	}
	 
	var moudulesData = null; 
	Core.AjaxRequest({ 
		url : ws_url + "/rest/user/platform/findMoudules/" + currentUser.userSid,
		type : "GET",
		async: false,
		callback : function (data) {
			moudulesData = data;
		} 
	});
	function showPage(str){
		var menu = $(window.parent.document.getElementById('menuContent')).find('li');
		var icon = $("#full").find('i').attr('class');
		if(icon=='icon-resize-small-2'){
			window.parent.operatPage("down");
		}
		if(str=="bizres"){
			for ( var i = 0; i < moudulesData.length; i++) {
				if(moudulesData[i].moduleName=="业务管理"){
					for(var j=0;j<menu.length;j++){
						if($(menu[j]).find('b').html()=="业务管理"){
							window.parent.addTabsOnMainPage($(menu[j]),moudulesData[i].moduleUrl,moudulesData[i].moduleName,moudulesData[i].moduleIconUrl,moudulesData[i].moduleSid);
							break;
						}
					}
					break;
				}
			}
		}else if(str=="res"){
			for ( var i = 0; i < moudulesData.length; i++) {
				if(moudulesData[i].moduleName=="资源管理"){
					for(var j=0;j<menu.length;j++){
						if($(menu[j]).find('b').html()=="资源管理"){
							window.parent.addTabsOnMainPage($(menu[j]),moudulesData[i].moduleUrl,moudulesData[i].moduleName,moudulesData[i].moduleIconUrl,moudulesData[i].moduleSid);
							break;
						}
					}
					break;
				}
			}
		}else if(str=="approve"){
			for ( var i = 0; i < moudulesData.length; i++) {
				if(moudulesData[i].moduleName=="日常维护"){
					for(var j=0;j<menu.length;j++){
						if($(menu[j]).find('b').html()=="日常维护"){
							window.parent.selectIndex = 0;
							window.parent.addTabsOnMainPage2($(menu[j]),moudulesData[i].moduleUrl,moudulesData[i].moduleName,moudulesData[i].moduleIconUrl,moudulesData[i].moduleSid);
							break;
						}
					}
					break;
				}
			}
		}else if(str=="solve"){
			for ( var i = 0; i < moudulesData.length; i++) {
				if(moudulesData[i].moduleName=="日常维护"){
					for(var j=0;j<menu.length;j++){
						if($(menu[j]).find('b').html()=="日常维护"){
							window.parent.selectIndex = 2;
							window.parent.addTabsOnMainPage2($(menu[j]),moudulesData[i].moduleUrl,moudulesData[i].moduleName,moudulesData[i].moduleIconUrl,moudulesData[i].moduleSid);
							break;
						}
					}
					break;
				}
			}
		}
	}
	
	function changeStRank(obj){
		var icon = $(obj).find("i").attr('class');
		if(icon=='icon-down-3'){
			$(obj).parent().parent().find("strong").html("业务存储使用率排名（low）");
			initBizDiskUsage("ASC","bizDiskUsage");
			$(obj).find("i").attr('class','icon-up-3');
		}
		if(icon=='icon-up-3'){
			$(obj).parent().parent().find("strong").html("业务存储使用率排名（top）");
			initBizDiskUsage("DESC","bizDiskUsage");
			$(obj).find("i").attr('class','icon-down-3');
		}
	}
	 
	function changeIoRank(obj){
		var icon = $(obj).find("i").attr('class');
		if(icon=='icon-down-3'){
			$(obj).parent().parent().find("strong").html("业务流量排名（low）");
			initIostatRank("ASC","ioStatRank");
			$(obj).find("i").attr('class','icon-up-3');
		}
		if(icon=='icon-up-3'){
			$(obj).parent().parent().find("strong").html("业务流量排名（top）");
			initIostatRank("DESC","ioStatRank");
			$(obj).find("i").attr('class','icon-down-3');
		}
	}
	
	function initBizRes(str){
		var  sampleData = "";
		Core.AjaxRequest({
			url : ws_url + "/rest/count/findBizResSurvey",
			type : 'post',
			async:false,
			callback : function(data) {
				sampleData = data;
			}
		});
		$('#'+str).highcharts({
            chart: {
                type: 'column',
                events: {
                    click: function (event) {
                    	toFullScreen('bizResUsed','资源占用概况','');
                    }
                }
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: sampleData.bizList
            },
            yAxis: {
                type: 'logarithmic',//坐标轴类型为对数
//                 minorTickInterval: 'auto', //小单位刻度为自动
                title: {
                    text: ''
                }
            },
            tooltip: {
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0,
                    borderWidth: 0
                },
                series : {
                	events : {
                		legendItemClick: function(event) {
                			return false;
                		}
                	}
                }
            },
            legend: {
                borderWidth: 0
            },
            credits:{ 
                enabled:false
            },
            exporting:{ 
                enabled:false
            },
            series: [{
                name: 'CPU（核）',
                data: sampleData.cpu,
                color: '#5b9bd5'
    
            }, {
                name: '内存（Gb）',
                data: sampleData.mem,
                color: '#f37926'
    
            }, {
                name: '存储（Tb）',
                data: sampleData.storage,
                color:'#A5A5A5'
    
            }]
        });
	}
	
	function initIostat(str){
		var  sampleData = "";
		Core.AjaxRequest({
			url : ws_url + "/rest/count/findIostatSurvey",
			type : 'post',
			async:false,
			callback : function(data) {
				sampleData = data;
			}
		});
		$('#'+str).highcharts({
			chart:{
                events: {
                    click: function (event) {
                    	toFullScreen('iostatUsed','服务流量概况','');
                    }
                }
			},
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: sampleData.data,
//                 categories: ['7:00','7:30','8:00','8:30','9:00','9:30','10:00','10:30','11:00','11:30','12:00','12:30',]
//                 labels: {
// 	                rotation: -20,
// 	                align: 'right'
// 	            }
            },
            yAxis: {
            	min:0,
                title: {
                    text: ''
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                valueSuffix: ''
            },
            legend: {
                borderWidth: 0
            },
            credits:{ 
                enabled:false
            },
            exporting:{ 
                enabled:false
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: true
                },
                series : {
                	events : {
                		legendItemClick: function(event) {
                			return false;
                		}
                	}
                }
            },
            series: [{
                name: '总量（Mbps）',
                data: sampleData.sum,
// 				data: [400,500,600,650,670,700,650,550,500,480,450,400],
                color: '#5b9bd5'
            }, {
                name: '内容引入（Mbps）',
                data: sampleData.content,
//                 data: [250,300,350,350,400,480,500,450,400,330,330,280],
                color: '#f37926'
            }, {
                name: '其他（自有，创新，政企）（Mbps）',
                data: sampleData.others,
//                 data: [150,200,250,300,270,220,150,100,100,150,120,120],
                color:'#A5A5A5'
            }]
        });
	}
	
	 function initResUsed(str,param){
		var  sampleData = "";
		Core.AjaxRequest({
			url : ws_url + "/rest/count/findBizResTrend",
			type : 'post',
			async:false,
			callback : function(data) {
				sampleData = data;
			}
		});
		$('#'+str).highcharts({
			chart:{
                events: {
                    click: function (event) {
                    	 toFullScreen('resUsageTrend','资源占用趋势','');
                    }
                }
			},
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: sampleData.time
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                }
            },
            tooltip: {
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                },
                series : {
                	events : {
                		legendItemClick: function(event) {
                			return false;
                		}
                	}
                }
            },
            legend: {
                borderWidth: 0
            },
            credits:{ 
                enabled:false
            },
            exporting:{ 
                enabled:false
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: true
                }
            },
            series: [{
                name: 'CPU（核）',
                data: sampleData.cpu,
                color:'#77A753'
    
            }, {
                name: '内存（Gb）',
                data: sampleData.mem,
                color:'#4A72BA'
    
            }, {
                name: '存储（Tb）',
                data: sampleData.storage,
                color:'#F3BF1D'
    
            }]
        });
	 }

	 // 初始化虚拟机图表
	 function initVMChart(str){
		 var  sampleData ="";
		 Core.AjaxRequest({
			url : ws_url + "/rest/count/findVmSurvey",
			type : 'post',
			async:false,
			callback : function(data) {
				sampleData = data;
			}
		 });
		 $('#'+str).highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false,
                events: {
                    click: function (event) {
                    	toFullScreen('bizVm','业务及虚拟机概况','');
                    }
                }
	        },
	        title: {
	            text: '虚拟机（'+sampleData.sumNum+'台）',
	            style: {
	                color: '#000000',
	                fontWeight: 'bold',
	                fontFamily: 'Microsoft Yahei',
	                fontSize:'5px'
	            },
	            verticalAlign: 'bottom',
	            y: 0
	        },
	        tooltip: {
	    	    pointFormat: '{point.percentage:.1f}%'
	        },
	        legend: {
	        	itemStyle: {
	                color: '#000000',
	                fontSize:'5px'
	            },
                borderWidth: 0,
                y: -15
            },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
                        enabled: true,
                        format: '<b>{point.y}',
                        style: {
                            color: 'black'
                        },
                        distance: -20
                    },
	                showInLegend: true,
	                colors:['#5b9bd5','#f37926','#A5A5A5','#F3BF1D','#71ae48','#66CDAA','#2E8B57']
	            },
                series : {
                	events : {
                		legendItemClick: function(event) {
                			return false;
                		}
                	}
                }
	        },
	        credits:{ 
                enabled:false
            },
            exporting:{ 
                enabled:false
            },
	        series: [{
	            type: 'pie',
	            data: eval(sampleData.data)
	        }]
	    });
	 }
	 
	// 初始化业务图表
	 function initBizChart(str){
		 var  sampleData ="";
		 Core.AjaxRequest({
			url : ws_url + "/rest/count/findBizSurvey",
			type : 'post',
			async:false,
			callback : function(data) {
				sampleData = data;
			}
		 });
		 $('#'+str).highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false,
                events: {
                    click: function (event) {
                    	toFullScreen('bizVm','业务及虚拟机概况','');
                    }
                }
	        },
	        title: {
	            text: '业务（'+sampleData.sumNum+'个）',
	            style: {
	                color: '#000000',
	                fontWeight: 'bold',
	                fontFamily: 'Microsoft Yahei',
	                fontSize:'5px'
	            },
	            verticalAlign: 'bottom',
	            y: 0
	        },
	        tooltip: {
	    	    pointFormat: '{point.percentage:.1f}%'
	        },
	        legend: {
	        	itemStyle: {
	                color: '#000000',
	                fontSize:'5px'
	            },
                borderWidth: 0,
                y: -15
            },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
                        enabled: true,
                        format: '<b>{point.y}',
                        style: {
                            color: 'black'
                        },
                        distance: -20
                    },
	                showInLegend: true,
	                colors:['#5b9bd5','#f37926','#A5A5A5','#F3BF1D','#71ae48','#66CDAA','#2E8B57']
	            },
                series : {
                	events : {
                		legendItemClick: function(event) {
                			return false;
                		}
                	}
                }
	        },
	        credits:{ 
                enabled:false
            },
            exporting:{ 
                enabled:false
            },
	        series: [{
	            type: 'pie',
	            data: eval(sampleData.data)
	        }]
	    });
	 }
	
	//下一页的图
	 function initFreeRes(str){
		 var  sampleData = "";
		 Core.AjaxRequest({
			url : ws_url + "/rest/count/findResRecoverySurvey",
			type : 'post',
			async:false,
			callback : function(data) {
				sampleData = data;
			}
		 });
		 $('#'+str).highcharts({
	     	chart: {
	        	type: 'column',
                events: {
                    click: function (event) {
                    	toFullScreen('fresMsg','资源回收概况','');
                    }
                }
	        },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
	            categories: sampleData.biz
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                }
            },
            tooltip: {
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                },
                series : {
                	events : {
                		legendItemClick: function(event) {
                			return false;
                		}
                	}
                }
            },
            legend: {
                borderWidth: 0
            },
            credits:{ 
                enabled:false
            },
            exporting:{ 
                enabled:false
            },
            series: [{
                name: '回收CPU（核）',
                data: sampleData.cpu,
                color: '#579DDB'
    
            }, {
                name: '回收内存（Gb）',
                data: sampleData.mem,
                color: '#f37926'
    
            }, {
                name: '回收存储（Tb）',
                data: sampleData.storage,
                color:'#A5A5A5'
    
            }]
        });
	}
	
	 function initIostatRank(rank,str){
		 var  sampleData = "";
		 Core.AjaxRequest({
			url : ws_url + "/rest/count/findBizIostatRank/"+rank,
			type : 'post',
			async:false,
			callback : function(data) {
				sampleData = data;
			}
		 });
		 $('#'+str).highcharts({
	        chart: {
	            type: 'column',
	            margin: [ 50, 50, 100, 80],
                events: {
                    click: function (event) {
                    	toFullScreen('ioStatRank','业务流量排名','DESC');
                    }
                }
	        },
	        title: {
	            text: ''
	        },
	        xAxis: {
	            categories: sampleData.bizList,
// 	            categories: ['下载','CNTV','FAST-WEB','暴风影音','迅雷','营业厅业务','智能播报','消费分析'],
	            labels: {
	                rotation: -45,
	                align: 'right',
	                style: {
	                    fontSize: '8px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: ''
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        credits:{ 
                enabled:false
            },
            exporting:{ 
                enabled:false
            },
            legend: {
                enabled: true,
                borderWidth: 0
            },
	        tooltip: {
	            pointFormat: '{point.y}</b>',
	        },
	        plotOptions:{
                series : {
                	events : {
                		legendItemClick: function(event) {
                			return false;
                		}
                	}
                }
	        },
	        series: [{
	            name: '流量（Mbps）',
	            data: sampleData.ioList,
// 	            data: [255,251,240,230,200,160,140,100],
	            dataLabels: {
	                enabled: true,
	                rotation: null,
	                color: '#000000',
	                align: 'center'
	            }
	        }]
	    });
	 }
	 
	 function initResUsage(str,param){
		 var  sampleData = "";
		 Core.AjaxRequest({
			url : ws_url + "/rest/count/findResRateSurvey",
			params :param,
			type : 'post',
			async:false,
			callback : function(data) {
				sampleData = data;
			}
		 });
		 $('#'+str).highcharts({
			chart:{
                events: {
                    click: function (event) {
                    	toFullScreen('resUsage','资源利用率概况','');
                    }
                }
			},
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: sampleData.bizName
            },
            yAxis: {
            	min:0,
                title: {
                    text: ''
                },
                labels: {
                    format: '{value} %'
                }
            },
            tooltip: {
                shared: true,
                useHTML: true
            },
            legend: {
                borderWidth: 0
            },
            credits:{ 
                enabled:false
            },
            exporting:{ 
                enabled:false
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: true
                },
                series : {
                	events : {
                		legendItemClick: function(event) {
                			return false;
                		}
                	}
                }
            },
            tooltip: {
	            pointFormat: '{series.name}:{point.y:1f}%</b>',
	        },
            series: [{
                name: 'CPU（核）',
                data: sampleData.cpuUsage,
                color: '#579DDB'
            }, {
                name: '内存（Gb）',
                data: sampleData.memUsage,
                color: '#f37926'
            }, {
                name: '存储（Tb）',
                data: sampleData.stUsage,
                color:'#A5A5A5'
            }]
        });  
	 }
	
	 function initBizDiskUsage(rank,str){
		 var  sampleData = "";
		 Core.AjaxRequest({
			url : ws_url + "/rest/count/findBizStorageRateRank/"+rank,
			type : 'post',
			async:false,
			callback : function(data) {
				sampleData = data;
			}
		 });
		 $('#'+str).highcharts({
	        chart: {
	            type: 'column',
	            margin: [ 50, 50, 100, 80],
                events: {
                    click: function (event) {
                    	toFullScreen('bizDiskUsage','业务存储使用率排名','DESC');
                    }
                }
	        },
	        title: {
	            text: ''
	        },
	        xAxis: {
	            categories: sampleData.bizName,
	            labels: {
	                rotation: -45,
	                align: 'right',
	                style: {
	                    fontSize: '8px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        },
	        yAxis: {
                title: {
                    text: ''
                },
                labels: {
                    format: '{value} %'
                }
	        },
	        legend: {
	            enabled: false
	        },
	        credits:{ 
                enabled:false
            },
            exporting:{ 
                enabled:false
            },
            legend: {
                enabled: true,
                borderWidth: 0
            },
	        tooltip: {
	            pointFormat: '{point.y:1f}%</b>',
	        },
	        plotOptions:{
                series : {
                	events : {
                		legendItemClick: function(event) {
                			return false;
                		}
                	}
                }
	        },
	        series: [{
	            name: '使用率（%）',
	            data: sampleData.usages,
	            dataLabels: {
	                enabled: true,
	                rotation: null,
	                color: '#000000',
	                align: 'center'
	            },
	            color:'#ffc125'
	        }]
	    });
	 }
	 
	 function initEquipmentInfo(str,div){
		 var dpdatas;
		 var jjdatas;
		 Core.AjaxRequest({
			url : ws_url + "/rest/count/getEquipmentDataInfo",
			type : 'post',
			async:false,
			callback : function(data) {
				var html = "";
				var num = 0;
				for(var i=0;i<data.length;i++){
					if(data[i].deviceTypeName=='刀片服务器'){
						if(typeof($(div).attr('id'))=='undefined'){
							html = html+ "<div style='float:left;width:20%;font-size:16px;'>&nbsp;&nbsp;"
							           +data[i].deviceTypeName+"<br/><i class='icon-doc-text' style='font-size:60px;color:#0B36FF;'></i></div>"+
							           "<div style='width:30%; height:48%;float:left;'><div id='dpPie' style='height:100%;width:100%;'></div></div>";
						}
						num = num + 1;
						dpdatas = "[['已使用',"+data[i].usedTotal+"],['未使用',"+data[i].unusedTotal+"]]";
						break;
					}
				}
				for(var i=0;i<data.length;i++){
					if(data[i].deviceTypeName=='机架服务器'){
						if(typeof($(div).attr('id'))=='undefined'){
							html = html+ "<div style='float:left;width:20%;font-size:16px;'>&nbsp;&nbsp;"
							+data[i].deviceTypeName+"<br/><i class='icon-doc-text' style='font-size:60px;color:#0B36FF;'></i></div>"+
							"<div style='width:30%; height:48%;float:left;'><div id='jjPie' style='height:100%;width:100%;'></div></div>";
						}
						num = num + 1;
						jjdatas = "[['已使用',"+data[i].usedTotal+"],['未使用',"+data[i].unusedTotal+"]]";
						break;
					}
				}
				if(data.length!=num){
					html = html+ "<div style='height:48%;width:100%;'><table style='width:100%;height:100%;font-size:16px;font-color:black;'><tr>";
					for(var i=0;i<data.length;i++){
						if(data[i].deviceTypeName=='存储设备'){
							html = html + "<td style='width:25%;'><span style='margin-top: 1px;'>存储设备</span><br/><i class='icon-hdd-1' style='font-size:60px;color:#0B36FF;'></i>"+data[i].total+"台</td>";
						}else if(data[i].deviceTypeName=='FC交换机'){
							html = html + "<td style='width:25%;'><span style='margin-top: 1px;'>FC交换机</span><br/><img alt='' src='${ctx}/images/indexStatic/router.png' style='height: 50px;margin-top: 15px;'>"+data[i].total+"台</td>";
						}else if(data[i].deviceTypeName=='IP交换机'){
							html = html + "<td style='width:25%;'><span style='margin-top: 1px;'>IP交换机</span><br/><img alt='' src='${ctx}/images/indexStatic/switch.png' style='height: 50px;margin-top: 15px;'>"+data[i].total+"台</td>";
						}else if(data[i].deviceTypeName=='负载均衡器'){
							html = html + "<td style='width:25%;'><span style='margin-top: 1px;'>负载均衡器</span><br/><img alt='' src='${ctx}/images/indexStatic/balanc.png' style='height: 50px;margin-top: 15px;'>"+data[i].total+"台</td>";
						}
					}
					html = html + "</tr></table></div>";
				}
				if(typeof($(div).attr('id'))=='undefined'){
					$(div).html(html);
					doublePie("dpPie",dpdatas);
					doublePie("jjPie",jjdatas);
				}else{
					$(div).append(html);
					doublePie("dpPieFull",dpdatas);
					doublePie("jjPieFull",jjdatas);
				}
				
			}
		 });
		 
	 }
	 
	 //刀片和机架服务器的双饼图
	 function doublePie(id,datas){
		 $('#'+id).highcharts({
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 25
	            }
	        },
	        colors:["#E7D302","#40D603"],
	        title: {
	            text: ''
	        },
	        subtitle: {
	            text: ''
	        },
	        legend: {
                enabled: true,
                borderWidth: 0
            },
	        credits:{ 
                enabled:false
            },
            exporting:{ 
                enabled:false
            },
	        tooltip: {
	        	pointFormat: '{point.y}</b>',
	        },
	        plotOptions: {
	            pie: {
	                innerSize: 35,
	                depth: 35,
	                dataLabels: {
	                	distance: 0
	                }
	            }
	        },
	        series: [{
	            data: eval(datas)
	        }]
	    });
	 }
	 
	 function initAlarmInfo(str){
		 var  sampleData = "";
		 Core.AjaxRequest({
			url : ws_url + "/rest/count/getAlarmDataInfo",
			type : 'post',
			async:false,
			callback : function(data) {
				sampleData = data;
			}
		 });
		 $('#'+str).highcharts({
	        chart: {
	            type: 'column',
	            margin: [ 50, 50, 100, 80],
                events: {
                    click: function (event) {
                    	toFullScreen('alarmInfo','告警信息图','');
                    }
                }
	        },
	        colors: ['#ED7D31','#5A9BD5'],
	        title: {
	            text: ''
	        },
	        xAxis: {
	            categories: sampleData.types,
	        },
	        yAxis: {
                title: {
                    text: ''
                },
                type: 'logarithmic',//坐标轴类型为对数
                stackLabels: {
                    enabled: false,
                    style: {
                        fontWeight: 'bold',
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                    }
                }
	        },
	        legend: {
	            enabled: false
	        },
	        credits:{ 
                enabled:false
            },
            exporting:{ 
                enabled:false
            },
            legend: {
                enabled: true,
                borderWidth: 0
            },
	        tooltip: {
	        },
	        plotOptions:{
	        	column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                        style: {
                            textShadow: '0 0 3px black, 0 0 3px black'
                        }
                    }
                },
                series : {
                	events : {
                		legendItemClick: function(event) {
                			return false;
                		}
                	}
                }
	        },
	        series: sampleData.datas
	    });
	 }
	
	 function initWindow(){
		 $('#fullDiv').jqxWindow({
        	isModal : true,
            showCollapseButton: false, 
            resizable: false,    
            autoOpen: false, 
            showCloseButton: true,
            height: 600, width: 800,
			theme: currentTheme
		 });
		 $('#fullDiv1').jqxWindow({
        	isModal : true,
            showCollapseButton: false, 
            resizable: false,    
            autoOpen: false, 
            showCloseButton: true,
            height: 600, width: 800,
			theme: currentTheme
		 });
		 $('#fullDiv2').jqxWindow({
        	isModal : true,
            showCollapseButton: false, 
            resizable: false,    
            autoOpen: false, 
            showCloseButton: true,
            height: 600, width: 800,
			theme: currentTheme
		 });
		 $('#fullResUsedDiv').jqxWindow({
	        	isModal : true,
	            showCollapseButton: false, 
	            resizable: false,    
	            autoOpen: false, 
	            showCloseButton: true,
	            height: 600, width: 800,
				theme: currentTheme
			 });
	 }
	 
	 function toFullScreen(id,title,orderRule){
		 if(id=="bizVm"){
			 initVMChart("vmfullScreen2");
			 initBizChart("bizfullScreen1");
			 $('#fullDiv2').jqxWindow({ title: title }); 
			 $("#fullDiv2").jqxWindow('open');
		 }else if(id=="equipmentInfo"){
			 var div = $("#fullScreen1");
			 initEquipmentInfo('equipmentInfo',div);
			 $('#fullDiv1').jqxWindow({ title: title }); 
			 $("#fullDiv1").jqxWindow('open');
		 }else if(id=="resUsageTrend" || id=="resUsage"){
			 $("#countData").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
			 //初始化biz下拉框和时间的下拉框
			 Core.AjaxRequest({
				url : ws_url + "/rest/bizType/findFbizType",
				type : 'post',
				async:false,
				callback : function(data) {
					var d = {name:"全部",bizSid:""}; 
					data.unshift(d);
					$("#resUsedBiz").jqxDropDownList({
						   width: 70,
		                   height: 22,
		                   source: data,
		                   selectedIndex: 0,
		                   displayMember: "name", 
		                   valueMember: "bizSid",
		                   autoDropDownHeight :true,
		                   theme:"metro"
		             });
				}
			 });
			 var dateType = [{name:"当前",value:""},{name:"年",value:"year"},{name:"月",value:"month"},{name:"日",value:"day"}];
			 $("#resUsedDate-type").jqxDropDownList({
				   width: 50,
                   height: 22,
                   source: dateType,
                   selectedIndex: 0,
                   displayMember: "name", 
                   valueMember: "value",
                   autoDropDownHeight :true,
                   theme:"metro"
             });
			 $("#resUsedDate-type").on("change",function(){
				 var type = $("#resUsedDate-type").val();
				 if(type!=""){
					 var date = new Date();
					 var nowYear = date.getFullYear();
					 var nowMonth = date.getMonth()+1;
					 var nowDay = date.getDate();
					 var syear = new Array;
					 var eyear = new Array;
					 for(var i=10;i>=0;i--){
						 syear.push(nowYear-i);
						 eyear.unshift(nowYear-i);
					 }
					 $("#resUsedDate-syear").jqxDropDownList({
						   width: 55,
		                   height: 22,
		                   source: syear,
		                   selectedIndex: 0,
		                   autoDropDownHeight :true,
		                   theme:"metro"
		             });
					 $("#resUsedDate-eyear").jqxDropDownList({
						   width: 55,
		                   height: 22,
		                   source: eyear,
		                   selectedIndex: 0,
		                   autoDropDownHeight :true,
		                   theme:"metro"
		             });
					 $("#resUsedDate-syear").on("close",function(){
						 var year = $("#resUsedDate-syear").val();
						 var ey = $("#resUsedDate-eyear").val();
						 var eyear2 = new Array;
						 for(var i=year;i<=nowYear;i++){
							 eyear2.push(i);
						 }
						 $("#resUsedDate-eyear").jqxDropDownList({
							   width: 55,
			                   height: 22,
			                   source: eyear2,
			                   autoDropDownHeight :true,
			                   theme:"metro"
			             });
						 $("#resUsedDate-eyear").val(ey);
						 var smd = $("#resUsedDate-smonth").val();
						 var sm = new Array;
						 if(year==nowYear){
							 for(var i=1;i<nowMonth+1;i++){
								 sm.push(i);
							 }
						 }else{
							 for(var i=1;i<13;i++){
								 sm.push(i);
							 }
						 }
						 $("#resUsedDate-smonth").jqxDropDownList({
							   width: 40,
			                   height: 22,
			                   source: sm,
			                   selectedIndex: 0,
			                   autoDropDownHeight :true,
			                   theme:"metro"
			             });
						 $("#resUsedDate-smonth").val(smd);
					 });
					 $("#resUsedDate-eyear").on("close",function(){
						 var year = $("#resUsedDate-eyear").val();
						 var sy = $("#resUsedDate-syear").val();
						 var syear2 = new Array;
						 for(var i=nowYear-10;i<=year;i++){
							 syear2.push(i);
						 }
						 $("#resUsedDate-syear").jqxDropDownList({
							   width: 55,
			                   height: 22,
			                   source: syear2,
			                   autoDropDownHeight :true,
			                   theme:"metro"
			             });
						 $("#resUsedDate-syear").val(sy);
						 var smd = $("#resUsedDate-smonth").val();
						 var sm = new Array;
						 if(year==sy){
							 if(year==nowYear){
								 for(var i=smd;i<nowMonth+1;i++){
									 sm.push(i);
								 }
							 }else{
								 for(var i=smd;i<13;i++){
									 sm.push(i);
								 }
							 }
						 }else{
							 for(var i=1;i<13;i++){
								 sm.push(i);
							 }
						 }
						 $("#resUsedDate-emonth").jqxDropDownList({
							   width: 40,
			                   height: 22,
			                   source: sm,
			                   selectedIndex: 0,
			                   autoDropDownHeight :true,
			                   theme:"metro"
			             });
					 });
					 
					 var sy = $("#resUsedDate-syear").val();
					 var ey = $("#resUsedDate-eyear").val();
					 var smonth = new Array;
					 var emonth = new Array;
					 if(sy==nowYear){
						 for(var i=1;i<nowMonth+1;i++){
							 smonth.push(i);
						 }
					 }else{
						 for(var i=1;i<13;i++){
							 smonth.push(i);
						 }
					 }
					 if(ey==nowYear){
						 for(var i=1;i<nowMonth+1;i++){
							 emonth.push(i);
						 }
					 }else{
						 for(var i=1;i<13;i++){
							 emonth.push(i);
						 }
					 }
					 $("#resUsedDate-smonth").jqxDropDownList({
						   width: 40,
		                   height: 22,
		                   source: smonth,
		                   selectedIndex: 0,
		                   autoDropDownHeight :true,
		                   theme:"metro"
		             });
					 $("#resUsedDate-emonth").jqxDropDownList({
						   width: 40,
		                   height: 22,
		                   source: emonth,
		                   selectedIndex: 0,
		                   autoDropDownHeight :true,
		                   theme:"metro"
		             });
					 $("#resUsedDate-smonth").on("close",function(){
						 var sy = $("#resUsedDate-syear").val();
						 var ey = $("#resUsedDate-eyear").val();
						 var sm = $("#resUsedDate-smonth").val();
						 var em = $("#resUsedDate-emonth").val();
						 var sm2 = new Array;
						 if(sy==ey){
							 if(sy==nowYear){
								 for(var i=sm;i<=nowMonth;i++){
									 sm2.push(i);
								 }
							 }else{
								 for(var i=sm;i<=12;i++){
									 sm2.push(i);
								 }
							 }
						 }else{
							 for(var i=1;i<=12;i++){
								 sm2.push(i);
							 }
						 }
						 $("#resUsedDate-emonth").jqxDropDownList({
							   width: 40,
			                   height: 22,
			                   source: sm2,
			                   autoDropDownHeight :true,
			                   theme:"metro"
			             });
						 $("#resUsedDate-emonth").val(em);
						 
						 var ste = new Date(sy,(parseInt(sm)),0);
						 var sd1 = new Array;
						 var sds = ste.getDate();
						 if(sy==nowYear&&sm==nowMonth){
							 for(var i=1;i<nowDay+1;i++){
								 sd1.push(i);
							 }
						 }else{
							 for(var i=1;i<sds+1;i++){
								 sd1.push(i);
							 }
						 }
						 $("#resUsedDate-sday").jqxDropDownList({
							   width: 40,
			                   height: 22,
			                   source: sd1,
			                   selectedIndex: 0,
			                   dropDownHeight :200,
			                   dropDownWidth :50,
			                   theme:"metro"
			             });
					 });
					 $("#resUsedDate-emonth").on("close",function(){
						 var sy = $("#resUsedDate-syear").val();
						 var ey = $("#resUsedDate-eyear").val();
						 var sm = $("#resUsedDate-smonth").val();
						 var em = $("#resUsedDate-emonth").val();
						 var sm2 = new Array;
						 if(sy==ey){
							 if(ey==nowYear){
								 for(var i=1;i<=em&&i<=nowMonth;i++){
									 sm2.push(i);
								 }
							 }else{
								 for(var i=1;i<=em;i++){
									 sm2.push(i);
								 }
							 }
						 }else{
							 for(var i=1;i<=12;i++){
								 sm2.push(i);
							 }
						 }
						 $("#resUsedDate-smonth").jqxDropDownList({
							   width: 40,
			                   height: 22,
			                   source: sm2,
			                   autoDropDownHeight :true,
			                   theme:"metro"
			             });
						 $("#resUsedDate-smonth").val(sm);
						 
						 var ste = new Date(sy,(parseInt(em)),0);
						 var sd1 = new Array;
						 var sds = ste.getDate();
						 if(ey==nowYear&&em==nowMonth){
							 for(var i=1;i<nowDay+1;i++){
								 sd1.push(i);
							 }
						 }else{
							 for(var i=1;i<sds+1;i++){
								 sd1.push(i);
							 }
						 }
						 $("#resUsedDate-eday").jqxDropDownList({
							   width: 40,
			                   height: 22,
			                   source: sd1,
			                   selectedIndex: 0,
			                   dropDownHeight :200,
			                   dropDownWidth :50,
			                   theme:"metro"
			             });
					 });
					 var smo = $("#resUsedDate-smonth").val();
					 var emo = $("#resUsedDate-emonth").val();
					 var sd = new Array;
					 var ed = new Array;
					 var stemp = new Date(sy,(parseInt(smo)),0);
					 var sdays = stemp.getDate();
					 var etemp = new Date(ey,(parseInt(emo)),0);
					 var edays = etemp.getDate();
					 if(sy==nowYear&&smo==nowMonth){
						 for(var i=1;i<nowDay+1;i++){
							 sd.push(i);
						 }
					 }else{
						 for(var i=1;i<sdays+1;i++){
							 sd.push(i);
						 }
					 }
					 if(ey==nowYear&&emo==nowMonth){
						 for(var i=1;i<nowDay+1;i++){
							 ed.push(i);
						 }
					 }else{
						 for(var i=1;i<edays+1;i++){
							 ed.push(i);
						 }
					 }
					 $("#resUsedDate-sday").jqxDropDownList({
						   width: 40,
		                   height: 22,
		                   source: sd,
		                   selectedIndex: 0,
		                   dropDownHeight :200,
		                   dropDownWidth :50,
		                   theme:"metro"
		             });
					 $("#resUsedDate-eday").jqxDropDownList({
						   width: 40,
		                   height: 22,
		                   source: ed,
		                   selectedIndex: 0,
		                   dropDownHeight :200,
		                   dropDownWidth :50,
		                   theme:"metro"
		             });
					 $("#resUsedDate-sday").on("close",function(){
						 var sy = $("#resUsedDate-syear").val();
						 var ey = $("#resUsedDate-eyear").val();
						 var sm = $("#resUsedDate-smonth").val();
						 var em = $("#resUsedDate-emonth").val();
						 var sd = $("#resUsedDate-sday").val();
						 var ed = $("#resUsedDate-eday").val();
						 var ste = new Date(ey,(parseInt(em)),0);
						 var sd1 = new Array;
						 var sds = ste.getDate();
						 if(sy==ey&&sm==em){
							 if(ey==nowYear&&em==nowMonth){
								 for(var i=sd;i<=sds&&i<=nowDay;i++){
									 sd1.push(i);
								 }
							 }else{
								 for(var i=sd;i<=sds;i++){
									 sd1.push(i);
								 }
							 }
						 }else{
							 for(var i=1;i<=sds;i++){
								 sd1.push(i);
							 } 
						 }
						 $("#resUsedDate-eday").jqxDropDownList({
							   width: 40,
			                   height: 22,
			                   source: sd1,
			                   dropDownHeight :200,
			                   dropDownWidth :50,
			                   theme:"metro"
			             });
						 $("#resUsedDate-eday").val(ed);
					 });
					 $("#resUsedDate-eday").on("close",function(){
						 var sy = $("#resUsedDate-syear").val();
						 var ey = $("#resUsedDate-eyear").val();
						 var sm = $("#resUsedDate-smonth").val();
						 var em = $("#resUsedDate-emonth").val();
						 var sd = $("#resUsedDate-sday").val();
						 var ed = $("#resUsedDate-eday").val();
						 var ste = new Date(sy,(parseInt(sm)),0);
						 var sd1 = new Array;
						 var sds = ste.getDate();
						 if(sy==ey&&sm==em){
							 if(sy==nowYear&&sm==nowMonth){
								 for(var i=1;i<=sds&&i<=nowDay&&i<=ed;i++){
									 sd1.push(i);
								 }
							 }else{
								 for(var i=1;i<=sds&&i<=ed;i++){
									 sd1.push(i);
								 }
							 }
						 }else{
							 for(var i=1;i<=sds;i++){
								 sd1.push(i);
							 } 
						 }
						 $("#resUsedDate-sday").jqxDropDownList({
							   width: 40,
			                   height: 22,
			                   source: sd1,
			                   dropDownHeight :200,
			                   dropDownWidth :50,
			                   theme:"metro"
			             });
						 $("#resUsedDate-sday").val(sd);
					 });
					 if(type=="year"){
						 $("#syear").show();
						 $("#syearText").show();
						 $("#eyear").show();
						 $("#eyearText").show();
						 $("#flag1").show();
						 $("#smonth").hide();
						 $("#smonthText").hide();
						 $("#sday").hide();
						 $("#sdayText").hide();
						 $("#emonth").hide();
						 $("#emonthText").hide();
						 $("#eday").hide();
						 $("#edayText").hide();
					 }else if(type=="month"){
						 $("#syear").show();
						 $("#syearText").show();
						 $("#eyear").show();
						 $("#eyearText").show();
						 $("#flag1").show();
						 $("#smonth").show();
						 $("#smonthText").show();
						 $("#emonth").show();
						 $("#emonthText").show();
						 $("#sday").hide();
						 $("#sdayText").hide();
						 $("#eday").hide();
						 $("#edayText").hide();
					 }else if(type=="day"){
						 $("#syear").show();
						 $("#syearText").show();
						 $("#smonth").show();
						 $("#smonthText").show();
						 $("#sday").show();
						 $("#sdayText").show();
						 $("#flag1").show();
						 $("#eyear").show();
						 $("#eyearText").show();
						 $("#emonth").show();
						 $("#emonthText").show();
						 $("#eday").show();
						 $("#edayText").show();
					 }
				 }else{
					 $("#syear").hide();
					 $("#syearText").hide();
					 $("#smonth").hide();
					 $("#smonthText").hide();
					 $("#sday").hide();
					 $("#sdayText").hide();
					 $("#flag1").hide();
					 $("#eyear").hide();
					 $("#eyearText").hide();
					 $("#emonth").hide();
					 $("#emonthText").hide();
					 $("#eday").hide();
					 $("#edayText").hide();
				 }
			 });
			 $("#syear").hide();
			 $("#syearText").hide();
			 $("#smonth").hide();
			 $("#smonthText").hide();
			 $("#sday").hide();
			 $("#sdayText").hide();
			 $("#flag1").hide();
			 $("#eyear").hide();
			 $("#eyearText").hide();
			 $("#emonth").hide();
			 $("#emonthText").hide();
			 $("#eday").hide();
			 $("#edayText").hide();
			 if(id=="resUsageTrend"){
				 $("#chartType").val("resUsageTrend");
				 initResUsed("fullResScreen",{});
			 }else if(id=="resUsage"){
				 $("#chartType").val("resUsage");
				 initResUsage("fullResScreen",{});
			 }
			 $('#fullResUsedDiv').jqxWindow({ title: title }); 
			 $("#fullResUsedDiv").jqxWindow('open');
		 }else{
			 if(id=="bizResUsed"){
				 initBizRes("fullScreen");
			 }else if(id=="iostatUsed"){
				 initIostat("fullScreen");
			 }else if(id=="fresMsg"){
				 initFreeRes("fullScreen");
			 }else if(id=="ioStatRank"){
				 initIostatRank("DESC","fullScreen");
			 }else if(id=="bizDiskUsage"){
				 initBizDiskUsage("DESC","fullScreen");
			 }else if(id=="alarmInfo"){
				 initAlarmInfo("fullScreen");
			 }
			 $('#fullDiv').jqxWindow({ title: title }); 
			 $("#fullDiv").jqxWindow('open');
		 }
	 }
	 
	 function searchResUsed(){
		 var bizSid = $("#resUsedBiz").val();
		 var type = $("#resUsedDate-type").val();
		 var dateStr="";
		 if(type!=""){
			 if(type=="year"){
				 var sy = $("#resUsedDate-syear").val();
				 var ey = $("#resUsedDate-eyear").val();
				 dateStr = sy+":"+ey;
			 }else if(type=="month"){
				 var sy = $("#resUsedDate-syear").val();
				 var ey = $("#resUsedDate-eyear").val();
				 var sm = $("#resUsedDate-smonth").val();
				 var em = $("#resUsedDate-emonth").val();
				 dateStr = sy+"-"+sm+":"+ey+"-"+em;
			 }else if(type=="day"){
				 var sy = $("#resUsedDate-syear").val();
				 var ey = $("#resUsedDate-eyear").val();
				 var sm = $("#resUsedDate-smonth").val();
				 var em = $("#resUsedDate-emonth").val();
				 var sd = $("#resUsedDate-sday").val();
				 var ed = $("#resUsedDate-eday").val();
				 dateStr = sy+"-"+sm+"-"+sd+":"+ey+"-"+em+"-"+ed;
			 }
		 }
		 param = {
				 bizSid : bizSid,
				 dateType : type,
				 dateStr : dateStr
		 };
		 var chart = $("#chartType").val();
		 if(chart=="resUsageTrend"){
			 initResUsed2("fullResScreen",param);
		 }else if(chart=="resUsage"){
			 initResUsed3("fullResScreen",param);
		 }
	 }
	 
	 function initResUsed2(str,param){
		var  sampleData = "";
		Core.AjaxRequest({
			url : ws_url + "/rest/count/findBizResTrendByDate",
			type : 'post',
			params :param,
			async:false,
			callback : function(data) {
				sampleData = data;
			}
		});
		$('#'+str).highcharts({
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: sampleData.time
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                }
            },
            tooltip: {
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                },
                series : {
                	events : {
                		legendItemClick: function(event) {
                			return false;
                		}
                	}
                }
            },
            legend: {
                borderWidth: 0
            },
            credits:{ 
                enabled:false
            },
            exporting:{ 
                enabled:false
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: true
                }
            },
            series: [{
                name: 'CPU（核）',
                data: sampleData.cpu,
                color:'#77A753'
    
            }, {
                name: '内存（Gb）',
                data: sampleData.mem,
                color:'#4A72BA'
    
            }, {
                name: '存储（Tb）',
                data: sampleData.storage,
                color:'#F3BF1D'
    
            }]
        });
	 }
	 
	 function initResUsed3(str,param){
			var  sampleData = "";
			Core.AjaxRequest({
				url : ws_url + "/rest/count/findBizResTrendByDate",
				type : 'post',
				params :param,
				async:false,
				callback : function(data) {
					sampleData = data;
				}
			});
			$('#'+str).highcharts({
	            title: {
	                text: ''
	            },
	            subtitle: {
	                text: ''
	            },
	            xAxis: {
	                categories: sampleData.time
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: ''
	                },
	                labels: {
	                    format: '{value} %'
	                }
	            },
	            tooltip: {
		            pointFormat: '{series.name}:{point.y:1f}%</b>',
		        },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                },
	                series : {
	                	events : {
	                		legendItemClick: function(event) {
	                			return false;
	                		}
	                	}
	                }
	            },
	            legend: {
	                borderWidth: 0
	            },
	            credits:{ 
	                enabled:false
	            },
	            exporting:{ 
	                enabled:false
	            },
	            plotOptions: {
	                line: {
	                    dataLabels: {
	                        enabled: true
	                    },
	                    enableMouseTracking: true
	                }
	            },
	            series: [{
	                name: 'CPU（核）',
	                data: sampleData.cpuUsage,
	                color:'#77A753'
	    
	            }, {
	                name: '内存（Gb）',
	                data: sampleData.memUsage,
	                color:'#4A72BA'
	    
	            }, {
	                name: '存储（Tb）',
	                data: sampleData.stUsage,
	                color:'#F3BF1D'
	    
	            }]
	        });
		 }
	 
	 function searchResUsage(){
		 var bizSid = $("#resUsageBiz").val();
		 param = {
				 bizSid : bizSid
		 };
		 initResUsage("fullUsageScreen",param);
	 }
	 
</script>

</html>