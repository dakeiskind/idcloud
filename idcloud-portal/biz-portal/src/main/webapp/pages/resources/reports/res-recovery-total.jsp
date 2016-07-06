<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/common/resources.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
<!-- hightcharts -->
	<script src="${ctx}/scripts/highcharts/highcharts.js"></script>
	<script src="${ctx}/scripts/highcharts/highcharts-more.js"></script>
	<script src="${ctx}/scripts/highcharts/modules/exporting.js"></script>

<script type="text/javascript" src="${ctx}/js/resources/reports/res-recovery-total.js"></script>
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
<body>
	<div id="containerResource">
		<div style="margin: 0px; padding: 0px; height: 8px;"></div>
		<div id="mainSplitterResource">
			<div class="listHost" style="display: none;">
				<div id='jqxTreeReports' style="width: 100%; height: 100%; background: #eee; border: 0px; overflow: hidden"></div>
			</div>
			<div id='jqxWidget'>
				<div >
					<div style="width: 100%; height: 30px; padding: 10px 0px 10px 0px;">
						<table border="0" height="100%" cellspacing="0" cellpadding="2">
							<tr>
								<td align="right" nowrap="nowrap"><font
									class="textfiledSearch">&nbsp;&nbsp;&nbsp;&nbsp;一级业务：</font></td>
								<td>
									<div id="search-fbizs" /></div>
								</td>
<!-- 								<td align="right" nowrap="nowrap"><font
									class="textfiledSearch">&nbsp;&nbsp;&nbsp;&nbsp;二级业务：</font></td>
								<td> -->
									<div id="search-sbizs" /></div>
								</td>
								<td align="right" nowrap="nowrap"><font
									class="textfiledSearch">&nbsp;&nbsp;&nbsp;&nbsp;开始时间：</font></td>
								<td>
								<td>
									<select id="start-year" onchange="compareYear()">
										<option value="0">--请选择--</option>
									</select><font class="textfiledSearch">年</font>
									<select id="start-month" onchange="compareMonth()">
										 <option value="0">--请选择--</option>
									</select><font class="textfiledSearch">月</font>
								</td>
								<td align="right" nowrap="nowrap"><font
									class="textfiledSearch">&nbsp;&nbsp;&nbsp;&nbsp;截至时间：</font></td>
								<td>
									<select id="finish-year" onchange="compareYear()">
										 <option value="0">--请选择--</option>
									</select><font class="textfiledSearch">年</font>
									<select id="finish-month" onchange="compareMonth()">
										 <option value="0">--请选择--</option>
									</select><font class="textfiledSearch">月</font>
								</td>
								<td><a data-bind='click: searchResource'
									id='search-resource-button'><i
										class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
							</tr>
						</table>
					</div>

					<div style="width:98%; margin-left:1%;"> 
				     	<div id='resourceDatagrid' style="width:100%;height:450px;"></div> 
				    </div> 
				      	
				    <div class="customPanel" style="width: 98%;height:320px;float:left;text-align: center;margin-left: 1%;margin-right: 1%;margin-top: 2%;">
						<div class="title" style="font-family: Microsoft Yahei;font-size: 14px;">&nbsp;&nbsp;<strong>回收统计图</strong></div>
						<div style="width:100%; height:95%;">
							<div id='resRecoveryTotal' style="width:100%; height:95%;left: 0px;border:0px;"></div>
						</div>
					</div>
				</div>
				
			</div>
			
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		// 页面初始化js
		var resource = initResourcePageJs();

		var bindmodel = new reportsBindModel(resource);
		//将model和view绑定
		ko.applyBindings(bindmodel);
		
		var syear = document.getElementById("start-year");
		var smonth = document.getElementById("start-month");
		var fyear = document.getElementById("finish-year");
		var fmonth = document.getElementById("finish-month");
		var now = new Date().getFullYear();
		initYearOp(syear,now-10,now);
		initYearOp(smonth,1,12);
		initYearOp(fyear,now-10,now);
		initYearOp(fmonth,1,12);
	});

	function initResourcePageJs() {

		// 初始化页面model
		var resourcemodel = new resourceModel();
		// 初始化页面组件
		resourcemodel.initInput();
		// 初始化datagrid
		resourcemodel.initResourceDatagrid();
		// 为datagrid赋值
		resourcemodel.searchResourceReport();
//		resourcemodel.recoveryTotalChart();
		
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
	function compareYear(){
		   var start = $("#start-year").val();
		   var finish = $("#finish-year").val();
		   if(finish=="0"||parseInt(finish)<parseInt(start)){
			   $("#finish-year").val("0");
			   $("#finish-month").val("0");
			   var fyear = document.getElementById("finish-year");
			   var now = new Date().getFullYear();
			   initYearOp(fyear,start,now);
			   compareMonth();
		   }
	   }
   function compareMonth(){
	   var start = $("#start-year").val();
	   var finish = $("#finish-year").val();
	   var startM = $("#start-month").val();
	   var finishM = $("#finish-month").val();
	   if(parseInt(finish)==parseInt(start)){
		   if(parseInt(finishM)<parseInt(startM)){
			   $("#finish-month").val("0");
			   var fmonth = document.getElementById("finish-month");
			   initYearOp(fmonth,startM,12);
		   }
	   }
   }
</script>
</html>