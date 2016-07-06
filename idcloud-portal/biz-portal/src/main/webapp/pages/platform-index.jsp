<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
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
	    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.ui-le-frog.css" type="text/css" />
	    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.green.css" type="text/css" />
	    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.red.css" type="text/css" />
	    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.yellow.css" type="text/css" />
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
			.indexDiv{
/* 				width:49%; */
/* 				height:48%; */
				text-align: center;
 				margin-left: 0.5%; 
 				margin-top: 0.5%;
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
				font-weight: normal;
				font-size: 14px;
				text-align: center;
			}
			.titleFont{
				font-family: Microsoft Yahei;
				font-size: 16px;
				text-align: left;
				height:15%;
				line-height:42px;
				background:#fff;
			}
			.chartStyle{
				width:100%; 
				height:99%;
				left: 0px;
				border:0px;
			}
			#setSort{
				width:465px; 
				height:320px;
				border:0px;
				position: absolute;
				background-color: white; 
				top: 4%; 
            	right: 0.3%;
	            z-index:1050; 
	            overflow: auto; 
			}
			.black_overlay{ 
	            display: none; 
	            position: absolute; 
	            top: 4%; 
	            left: 0%; 
	            width: 100%; 
	            height: 96%; 
	            background-color: black; 
	            z-index:1001; 
	            -moz-opacity: 0.8; 
	            opacity:.50; 
	            filter: alpha(opacity=88); 
	        } 
	        .clearfix:after{clear:both;content:" ";display:block;font-size:0;height:0;visibility:hidden;}
	        .progressDiv{
	        	height:14%;
	        	width:100%;
	        	background: white;
	        	margin-top: 3%;
/* 	        	margin-left: 1%; */
/* 	        	top: 50%; */
	        }
	        #outer{
	        	height:100%;
	        	width:15%;
	        	background:#ECEFF0;
	        	float: left;
	        	overflow: hidden;
	        	position: relative;
	        	left:0.5%;
	        }
	        .inner{
	        	margin:0 auto;
	        	width:80%;
	        	position: relative;
	        	top: 20%;
	        }
	    </style>
</head>
<body style="background:#ECEFF0;">
	<div style="height:3%;width:100%;position:relative;background:#ECEFF0;">
	    <div style="width:20px;position:absolute;top:3px;right:38px;">
			<a href="###" style="color: #017EC4;margin-right: 4px;font-size: 16px;" onclick="showSortDiv()">
				<i class='icon-cog'></i>
			</a>
		</div>
		<div style="width:20px;position:absolute;top:3px;right:8px;">
			<a id="full" href="#" onclick="doFull()" style="color: #0099d7;margin-right: 7px;"><i style="font-size:16px" class='icon-resize-small-2'></i></a>
		</div>
		<div style="width:80px;position:absolute;top:2px;right:60px;">
			<a href="#" onclick="getAheadPage()" style="color: #0099d7;"><i style="font-size:16px;" class='icon-left-open-big'></i></a>
			<span id="pageNum" style="color: #0099d7;font-size:15px;"></span>
			<a href="#" onclick="getBehindPage()" style="color: #0099d7;"><i style="font-size:16px;" class='icon-right-open-big'></i></a>
		</div>
	</div>
	<div style="height:96%;width:100%;">
		<div style="height:100%;width:84.4%;background:#ECEFF0;float: right;margin-left: 0.2%;" id="divBox"></div>
		<div id="outer">
			<div class="progressDiv">
				<div class="inner">
					<div id="x86CpuProgressText">X86 CPU<span id="x86CpuPercent" style="float: right;">&nbsp;</span></div>
					<div style='overflow: hidden;' id='x86CpuProgressBar'></div>
					<div class="progressFont" id="x86CpuText"></div>
				</div>
			</div>
			<div class="progressDiv">
				<div class="inner">
					<div id="x86MemProgressText">X86  内存<span id="x86MemPercent" style="float: right;">&nbsp;</span></div>
					<div style='overflow: hidden;' id='x86MemProgressBar'></div>
					<div class="progressFont" id="x86MemText"></div>
				</div>
			</div>
			<div class="progressDiv">
				<div class="inner">
					<div id="aixCpuProgressText">AIX CPU<span id="aixCpuPercent" style="float: right;">&nbsp;</span></div>
					<div style='overflow: hidden;' id='aixCpuProgressBar'></div>
					<div class="progressFont" id="aixCpuText"></div>
				</div>
			</div>
			<div class="progressDiv">
				<div class="inner">
					<div id="aixMemProgressText">AIX 内存<span id="aixMemPercent" style="float: right;">&nbsp;</span></div>
					<div style='overflow: hidden;' id='aixMemProgressBar'></div>
					<div class="progressFont" id="aixMemText"></div>
				</div>
			</div>
			<div class="progressDiv">
				<div class="inner">
					<div id="stProgressText">总存储<span id="stPercent" style="float: right;">&nbsp;</span></div>
					<div style='overflow: hidden;' id='stProgressBar'></div>
					<div class="progressFont" id="stText"></div>
				</div>
			</div>
			<div style="height:25%;width:100%;">
				<table style="width: 100%;height: 100%;text-align: center;background:white;margin-top: 3%;margin-left: 1%;">
					<tr>
						<td><a href="#" onclick="showPage('bizres')" class="indexHref"><img alt="项目管理" src="${ctx}/images/icon/index_biz.png" style="height: 50%;border: 0;"><br>项目管理</a></td>
						<td><a href="#" onclick="showPage('res')" class="indexHref"><img alt="资源管理" src="${ctx}/images/icon/index_res.png" style="height: 50%;border: 0;"><br>资源管理</a></td>
					</tr>
					<tr>
						<td><a href="#" onclick="showPage('approve')" class="indexHref" ><img alt="待审核" src="${ctx}/images/icon/index_uncheck.png" style="height: 50%;border: 0;"><br>待审核<span id="approveNum" class="spanFloat"></span></a></td>
						<td><a href="#" onclick="showPage('solve')" class="indexHref"><img alt="待解决" src="${ctx}/images/icon/index_unsolve.png" style="height: 50%;border: 0;"><br>待解决<span id="ticketNum" class="spanFloat"></span></a></td>
					</tr>
				</table>
			</div>
		</div>
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
							<font class="textfiledSearch">&nbsp;&nbsp;&nbsp;&nbsp;项目分类：</font></td>
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
			<div style="width:100%; height:100%;position: relative;">
				<div id='epFullChart' style='height:500px;width:550px;float:left;border:0px;'></div>
				<div id='epFullImg' style='width:30%; height:98%;float:left;border:0px;'></div>
			</div>
		</div>
	</div>
	<div id="fullDiv2" style="display: none;">
		<div style="width:90%; height:90%;">
			<div style="width:99%; height:91%;">
				<div id="bizfullScreen1" style="width:380px;height:470px;float:left;border:0px;"></div>
				<div id="vmfullScreen2" style="width:380px;height:470px;margin-left:0.5%;float:left;border:0px;"></div>
			</div>
			<div style='width:100%; height:9%;border:0px;text-align: center;color:black;'>
				<table class="clearfix" align="center" style="margin: 0 auto;padding: 0.1%;">
					<tr align="center"><td id="legend2" align="center"></td></tr>
				</table>
			</div>
		</div>
	</div>
	<div id="setSort" style="display: none;overflow: hidden;">
		<div>设置</div>
		<div style="overflow: hidden;">
			<div>
				<div id='setSortGrid' style="width:99.8%;height:99%;font-size:14px;"></div>
			</div>
			<div style="width: 98%; height: 25px; text-align: right;margin-top: 10px;">
				<input onclick="setSortSubmit()" type="button" id="sortSave" value="保存" /> 
				<input style="margin-right: 6px;" id="sortCancel" class="sortCancel" type="button" value="取消" />
			</div>
		</div>
	</div>
	<!-- 遮罩层 -->
	<div id="fade" class="black_overlay"></div> 
</body>
<script type="text/javascript">
var indexdata;
	$(function(){
// 		doCreateDivChart();
		//初始化下面的进度条
//		initProgressBar();
		//初始化待审核、待解决的个数
//		initUnresolvedNum();
		initWindow();
// 		window.parent.operatPage("down");
		
		var cla = 'icon-resize-full-3';
		$("#full").find('i').attr('class',cla);
		
	});
	
	function doCreateDivChart(){
		Core.AjaxRequest({ 
			url : ws_url + "/rest/count/findIndexChartByUser",
			type : "POST",
			params:{displayFlag:1},
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
					doShowDiv(chartData,1);
				}
			} 
		});
	}
	
	function doShowDiv(data,num){
		var olddiv = $("#divBox").children();
		$(olddiv).remove();
		var div = '<div id="divs'+num+'" style="height:100%;width:100%;display:none;"></div>';
		$("#divBox").append(div);
		var width = new Array;
		var height = new Array;
		if(data.length==1){
			width.push(98);
			height.push(98);
		}else if(data.length==2){
			width.push(49);
			height.push(98);
			width.push(49);
			height.push(98);
		}else if(data.length==3){
			width.push(49);
			height.push(49);
			width.push(49);
			height.push(49);
			width.push(98.8);
			height.push(49);
		}else if(data.length==4){
			width.push(49);
			height.push(49);
			width.push(49);
			height.push(49);
			width.push(49);
			height.push(49);
			width.push(49);
			height.push(49);
		}
		for(var i=0;i<data.length&&i<4;i++){
			var html ='<div class="indexDiv" style="float:left;background:#fff;border:1px solid white;position:relative;'+
					  'width:'+width[i]+'%;height:'+height[i]+'%" id="chart-'+num+'-'+(i+1)+'"></div>';
		    $("#divs"+num).append(html);
			$("#chart-"+num+'-'+(i+1)).load(ctx+data[i].topicUrl);  
		}
		$("#divs"+num).show();
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
// 			 $(".indexDiv").empty();
			 $("#pageNum").html("<strong>"+parseInt(parseInt(prex)+1)+"</strong>/<span>"+next+"</span>");
			 var chartData = new Array;
			 for(var i=parseInt(prex)*4,j=0;i<indexdata.length&&j<4;i++,j++){
				 chartData.push(indexdata[i]);
			 }
			 doShowDiv(chartData,parseInt(parseInt(prex)+1));
		 }
	 }
	 
	 //上一页
	 function getAheadPage(){
		//判断是否有下一页
		 var prex = $("#pageNum").find('strong').html();
		 var next = $("#pageNum").find('span').html();
		 if(parseInt(prex)>1){
// 			 $(".indexDiv").empty();
			 $("#pageNum").html("<strong>"+parseInt(parseInt(prex)-1)+"</strong>/<span>"+next+"</span>");
			 var chartData = new Array;
			 for(var i=parseInt(parseInt(prex)-2)*4,j=0;i<indexdata.length&&j<4;i++,j++){
				 chartData.push(indexdata[i]);
			 }
			 doShowDiv(chartData,parseInt(parseInt(prex)-1));
		 }
	 }
	 
	function initProgressBar(){
		Core.AjaxRequest({
			url : ws_url + "/rest/count/getProgressData",
			type : 'post',
			async:false,
			callback : function(data) {
				$("#x86CpuPercent").html(data.percent.x86cpu+"%&nbsp;&nbsp;&nbsp;&nbsp;");
				$("#x86MemPercent").html(data.percent.x86mem+"%&nbsp;&nbsp;&nbsp;&nbsp;");
				$("#aixCpuPercent").html(data.percent.aixcpu+"%&nbsp;&nbsp;&nbsp;&nbsp;");
				$("#aixMemPercent").html(data.percent.aixmem+"%&nbsp;&nbsp;&nbsp;&nbsp;");
				$("#stPercent").html(data.percent.st+"%&nbsp;&nbsp;&nbsp;&nbsp;");
				
				$("#x86CpuText").html(data.value.x86cpu);
				$("#x86MemText").html(data.value.x86mem);
				$("#aixCpuText").html(data.value.aixcpu);
				$("#aixMemText").html(data.value.aixmem);
				$("#stText").html(data.value.st);
				if(data.percent.x86cpu<=60){
					$("#x86CpuProgressText").css("color","green");
					$("#x86CpuText").css("color","green");
					$("#x86CpuProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.x86cpu ,showText:false,theme:'green'});
				}else if(data.percent.x86cpu>=80){
					$("#x86CpuProgressText").css("color","red");
					$("#x86CpuText").css("color","red");
					$("#x86CpuProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.x86cpu ,showText:false,theme:'red'});
				}else{
					$("#x86CpuProgressText").css("color","#F3BF1D");
					$("#x86CpuText").css("color","#F3BF1D");
					$("#x86CpuProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.x86cpu ,showText:false,theme:'yellow'});
				}
				if(data.percent.x86mem<=60){
					$("#x86MemProgressText").css("color","green");
					$("#x86MemText").css("color","green");
					$("#x86MemProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.x86mem ,showText:false,theme:'green'});
				}else if(data.percent.x86mem>=80){
					$("#x86MemProgressText").css("color","red");
					$("#x86MemText").css("color","red");
					$("#x86MemProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.x86mem ,showText:false,theme:'red'});
				}else{
					$("#x86MemProgressText").css("color","#F3BF1D");
					$("#x86MemText").css("color","#F3BF1D");
					$("#x86MemProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.x86mem ,showText:false,theme:'yellow'});
				}
				if(data.percent.aixcpu<=60){
					$("#aixCpuProgressText").css("color","green");
					$("#aixCpuText").css("color","green");
					$("#aixCpuProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.aixcpu ,showText:false,theme:'green'});
				}else if(data.percent.aixcpu>=80){
					$("#aixCpuProgressText").css("color","red");
					$("#aixCpuText").css("color","red");
					$("#aixCpuProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.aixcpu ,showText:false,theme:'red'});
				}else{
					$("#aixCpuProgressText").css("color","#F3BF1D");
					$("#aixCpuText").css("color","#F3BF1D");
					$("#aixCpuProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.aixcpu ,showText:false,theme:'yellow'});
				}
				if(data.percent.aixmem<=60){
					$("#aixMemProgressText").css("color","green");
					$("#aixMemText").css("color","green");
					$("#aixMemProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.aixmem ,showText:false,theme:'green'});
				}else if(data.percent.aixmem>=80){
					$("#aixMemProgressText").css("color","red");
					$("#aixMemText").css("color","red");
					$("#aixMemProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.aixmem ,showText:false,theme:'red'});
				}else{
					$("#aixMemProgressText").css("color","#F3BF1D");
					$("#aixMemText").css("color","#F3BF1D");
					$("#aixMemProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.aixmem ,showText:false,theme:'yellow'});
				}
				if(data.percent.st<=60){
					$("#stProgressText").css("color","green");
					$("#stText").css("color","green");
					$("#stProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.st ,showText:false,theme:'green'});
				}else if(data.percent.st>=80){
					$("#stProgressText").css("color","red");
					$("#stText").css("color","red");
					$("#stProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.st ,showText:false,theme:'red'});
				}else{
					$("#stProgressText").css("color","#F3BF1D");
					$("#stText").css("color","#F3BF1D");
					$("#stProgressBar").jqxProgressBar({ width: '100%', height: 18, value:data.percent.st ,showText:false,theme:'yellow'});
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
				if(moudulesData[i].moduleName=="项目管理"){
					for(var j=0;j<menu.length;j++){
						if($(menu[j]).find('b').html()=="项目管理"){
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
				if(moudulesData[i].moduleName=="审核管理"){
					for(var j=0;j<menu.length;j++){
						if($(menu[j]).find('b').html()=="审核管理"){
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
				if(moudulesData[i].moduleName=="工单管理"){
					for(var j=0;j<menu.length;j++){
						if($(menu[j]).find('b').html()=="工单管理"){
							window.parent.selectIndex = 0;
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
		 $('#setSort').jqxWindow({
			 resizable: false,  
             isModal: true, 
             autoOpen: false, 
             cancelButton : $(".sortCancel"),
             height: 330, 
             width: 480,
			 theme: currentTheme
		 });
		 $("#setSortGrid").jqxGrid({
	            width: 463,
	            height: 250,
	            pageable: false,
	            autoheight: false,
	            theme:currentTheme,
	            columnsresize: true,
	            sortable: false,
	            editable : true,
	            selectionmode:"singlerow",
	            localization: gridLocalizationObj,
	            columns: [
				  { text: '序号', datafield: 'indexNum',editable : false, cellsalign: 'right',align: 'right'},
	              { text: '主题名称', datafield: 'topicName',editable : false, cellsalign: 'center',align: 'center'},
	              { text: '是否显示', datafield: 'displayFlagText',columntype: 'checkbox',cellsalign: 'center',align: 'center',editable : true}
	            ],
	            showtoolbar: true,
	            toolbarheight:35,
	            // 设置toolbar操作按钮
	            rendertoolbar: function (toolbar) {
	            	var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                    var addBtn = $("<div><a id='rowUp' onclick ='doRowUp()'>&nbsp;&nbsp;<i class='icons-blue icon-up-1'></i>上移&nbsp;&nbsp;</a></div>");
                    var deleteBtn = $("<div><a id='rowDown' onclick='doRowDown()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-down-1'></i>下移&nbsp;&nbsp;</a></div>");
                    toolbar.append(container);
                    container.append(addBtn);
                    container.append(deleteBtn);
	            }
	         });
		 $("#rowUp").jqxButton({ width: '60',height: '15px', theme:currentTheme});
		 $("#rowDown").jqxButton({ width: '60',height: '15px', theme:currentTheme});
	 }
	 
	 function toFullScreen(id,title,orderRule){
		 if(id=="bizVm"){
			 initVMChart("vmfullScreen2",320);
			 initBizChart("bizfullScreen1","legend2",320);
			 $('#fullDiv2').jqxWindow({ title: title }); 
			 $("#fullDiv2").jqxWindow('open');
		 }else if(id=="equipmentInfo"){
			 $("#epFullChart").empty();
			 $("#epFullImg").empty();
			 var length = $("#fullScreen1").children("div").length;
			 if(length>1){
				 for(var i =1;i<length;i++){
					 $($("#fullScreen1").children("div")[i]).remove();
				 }
			 }
			 initEquipmentInfo('epFullChart','epFullImg',$("#epFullChart"),$("#epFullImg"));
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
				 initResUsed2("fullResScreen",{});
			 }else if(id=="resUsage"){
				 $("#chartType").val("resUsage");
				 initResUsage2("fullResScreen",{});
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
			 initResUsage2("fullResScreen",param);
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
                categories: sampleData.time,
                labels: {
		        	style:{
		        		color: '#000000',
		                fontWeight: 'normal',
		                fontFamily: 'Microsoft Yahei',
		                fontSize : '12px'
		        	}
		        }
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                },
                labels: {
		        	style:{
		        		color: '#000000',
		                fontWeight: 'normal',
		                fontFamily: 'Microsoft Yahei',
		                fontSize : '12px'
		        	}
		        }
            },
            tooltip: {
                shared: true,
                useHTML: true,
                style: {
		        	color: '#000000',
	                fontWeight: 'normal',
	                fontFamily: 'Microsoft Yahei',
	                fontSize : '12px'
		        }
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
                borderWidth: 0,
                itemStyle: {
	                color: '#000000',
	                fontWeight: 'normal',
	                fontFamily: 'Microsoft Yahei',
	                fontSize : '5px'
	            }
            },
            credits:{ 
                enabled:false
            },
            exporting:{ 
                enabled:true,
                filename:"restUsed"
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
	 
	 function initResUsage2(str,param){
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
	                categories: sampleData.time,
	                labels: {
			        	style:{
			        		color: '#000000',
			                fontWeight: 'normal',
			                fontFamily: 'Microsoft Yahei',
			                fontSize : '12px'
			        	}
			        }
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: ''
	                },
	                labels: {
	                    format: '{value} %',
	                    labels: {
	    		        	style:{
	    		        		color: '#000000',
	    		                fontWeight: 'normal',
	    		                fontFamily: 'Microsoft Yahei',
	    		                fontSize : '12px'
	    		        	}
	    		        }
	                }
	            },
	            tooltip: {
		            pointFormat: '{series.name}:{point.y:1f}%</b>',
		            style: {
			        	color: '#000000',
		                fontWeight: 'normal',
		                fontFamily: 'Microsoft Yahei',
		                fontSize : '12px'
			        }
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
	                borderWidth: 0,
	                itemStyle: {
		                color: '#000000',
		                fontWeight: 'normal',
		                fontFamily: 'Microsoft Yahei',
		                fontSize : '12px'
		            }
	            },
	            credits:{ 
	                enabled:false
	            },
	            exporting:{ 
	                enabled:true,
	                filename:"resUsage"
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
	 
	 function showSortDiv(){
// 		 if($("#setSort").css("display")=="none"){
// 			 $("#fade").show();
			 doSetSort();
// 			 $("#setSort").slideDown();
// 		 }else{
// 			 $("#setSort").slideUp("fast",function(){
// 				 setSortSubmit();
// 				 $("#fade").hide();
// 			 });
// 		 }
	 }
	 
	 function doSetSort(){
		 $("#sortSave").jqxButton({ width: '70',height:"25",theme:"metro"});
		 $("#sortCancel").jqxButton({ width: '70',height:"25",theme:"metro"});
		 
		 var dataAdapter;
		 Core.AjaxRequest({ 
			url : ws_url + "/rest/count/findIndexChartByUser",
			type : "POST",
			params :{},
			async: false,
			callback : function (data) {
				if(data!=null&&data!=""&&data.length!=0){
					for(var i=0;i<data.length;i++){
						data[i].indexNum = i+1;
						if(data[i].displayFlag=="1"){
							data[i].displayFlagText = true;
						}else{
							data[i].displayFlagText = false;
						}
					}
					var sourcedatagrid ={
			              datatype: "json",
			              localdata: data
				    };
				    dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
				}
			} 
		 });
		 $("#setSortGrid").jqxGrid({source: dataAdapter});
		 $("#setSort").jqxWindow('open');
	 }
	 
	 //行的上移
	 function doRowUp(){
		 var rowindex = $('#setSortGrid').jqxGrid('getselectedrowindex');
		 //判断选中的是不是第一行
		 if(rowindex!=0){
			 var data = $('#setSortGrid').jqxGrid('getrowdata', rowindex);
			 var sort = data.sortRank;
			 var uid = data.uid;
			 var data2 = $('#setSortGrid').jqxGrid('getrowdata', rowindex-1);
			 var sort2 = data2.sortRank;
			 var uid2 = data2.uid;
			 data2.sortRank = sort;
			 data.sortRank = sort2;
			 var rows = new Array;
			 rows.push(data2);
			 rows.push(data);
			 var rowIDs = new Array;
			 rowIDs.push(uid);
			 rowIDs.push(uid2);
			 $("#setSortGrid").jqxGrid('updaterow', rowIDs, rows);
			 
			 $('#setSortGrid').jqxGrid('selectrow', rowindex-1);
		 }
	 }
	 
	 //行的下移
	 function doRowDown(){
		 var rowindex = $('#setSortGrid').jqxGrid('getselectedrowindex');
		 //判断选中的是不是最后一行
		 var length = $('#setSortGrid').jqxGrid('getrows').length-1;
		 if(rowindex!=length){
			 var data = $('#setSortGrid').jqxGrid('getrowdata', rowindex);
			 var sort = data.sortRank;
			 var uid = data.uid;
			 var data2 = $('#setSortGrid').jqxGrid('getrowdata', rowindex+1);
			 var sort2 = data2.sortRank;
			 var uid2 = data2.uid;
			 data2.sortRank = sort;
			 data.sortRank = sort2;
			 var rows = new Array;
			 rows.push(data);
			 rows.push(data2);
			 var rowIDs = new Array;
			 rowIDs.push(uid2);
			 rowIDs.push(uid);
			 $("#setSortGrid").jqxGrid('updaterow', rowIDs, rows);
			 
			 $('#setSortGrid').jqxGrid('selectrow', rowindex+1);
		 }
	 }
	 
	 function setSortSubmit(){
		 var data = $('#setSortGrid').jqxGrid('getrows');
		 for(var i=0;i<data.length;i++){
			 if(data[i].displayFlagText==true||data[i].displayFlagText=="true"){
				 data[i].displayFlag = 1;
			 }else{
				 data[i].displayFlag = 0;
			 }
		 }
		 Core.AjaxRequest({         
			url : ws_url + "/rest/count/updateChartSortRank",
			params :data,
			type : "POST",
			async: false,
			callback : function (data) {
				$("#setSort").jqxWindow('close');
				doCreateDivChart();
		    },
		    failure:function(data){
				$("#setSort").jqxWindow('close');
		    	doCreateDivChart();
		    }
		});
	 }
</script>

</html>