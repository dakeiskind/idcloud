<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<style>
.chartStyle2{
	width:100%; 
	height:75%;
	left: 0px;
	border:0px;
}
.fontStyle{
    font-family:  Microsoft Yahei;
	font-weight: normal;
	font-size: 12px;
}
</style>
<div class="titleFont">&nbsp;&nbsp;项目及主机概况</div>
<a href="###" style="width:100%;">
	<div class="chartStyle2" style="width:100%;">
		<div id='bizCount' style='width:49%; height:98%;float:left;border:0px;'></div>
		<div id='vmCount' style='width:49%; height:98%;float:left;border:0px;'></div>
	</div>
	<div style="width:100%;border:0px;text-align: center;color:black;bottom: 0px;position: absolute;">
		<table class="clearfix" align="center" style="margin: 0 auto;padding: 0.1%;">
			<tr align="center"><td id="legend" align="center"></td></tr>
		</table>
	</div>
</a>

<script type="text/javascript">
	$(function(){
		initVMChart("vmCount",150);
		initBizChart("bizCount","legend",150);
	});
	function initVMChart(str,size){
		 var  sampleData ="";
		 Core.AjaxRequest({
			url : ws_url + "/rest/count/findMgtObjResSurvey",
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
	               	toFullScreen('bizVm','项目及主机概况','');
	               }
	           }
	       },
	       title: {
	           text: '云主机（'+sampleData.sumNum+'台）',
	           style: {
	               color: '#000000',
	               fontWeight: 'bold',
	               fontFamily: 'Microsoft Yahei',
	               fontSize:'12px'
	           },
	           x: -5,
	           verticalAlign: 'bottom',
	           y: 10
	       },
	       tooltip: {
	   	       pointFormat: '{point.percentage:.1f}%',
		   	    style: {
		        	color: '#000000',
	                fontWeight: 'normal',
	                fontFamily: 'Microsoft Yahei',
	                fontSize : '12px'
		        }
	       },
	       legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0,
	            itemStyle: {
	                color: '#000000',
	                fontWeight: 'normal',
	                fontFamily: 'Microsoft Yahei',
	                fontSize : '12px'
	            }
	       },
	       plotOptions: {
	           pie: {
	               allowPointSelect: true,
	               size: size,
	               cursor: 'pointer',
	               dataLabels: {
	                   enabled: true,
	                   format: '<b style="color:{point.color}">{point.y}',
	                   style: {
	                       color: 'black'
	                   },
	                   distance: 0
	               },
	               showInLegend: false,
	               colors:['#5b9bd5','#f37926','#A5A5A5','#F3BF1D','#71ae48','#66CDAA','#2E8B57','#00CD00','#00CDCD','#90EE90','#C67171']
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
	function initBizChart(str,legend,size){
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
	               	toFullScreen('bizVm','项目及主机概况','');
	               }
	           }
	       },
	       title: {
	           text: '项目（'+sampleData.sumNum+'个）',
	           style: {
	               color: '#000000',
	               fontWeight: 'bold',
	               fontFamily: 'Microsoft Yahei',
	               fontSize : '12px'
	           },
	           x: -5,
	           verticalAlign: 'bottom',
	           y: 10
	       },
	       tooltip: {
	   	    pointFormat: '{point.percentage:.1f}%'
	       },
	       legend: {
// 	            layout: 'vertical',
// 	            align: 'right',
				x:100,
				y:80,
	            verticalAlign: 'middle',
	            borderWidth: 0,
	            itemStyle: {
	                color: '#000000',
	                fontWeight: 'normal',
	                fontFamily: 'Microsoft Yahei',
	                fontSize : '12px'
	            }
	       },
	       plotOptions: {
	           pie: {
	               allowPointSelect: true,
	               cursor: 'pointer',
	               size: size,
	               dataLabels: {
	                   enabled: true,
	                   format: '<b style="color:{point.color}">{point.y}',
	                   style: {
	                       color: 'black'
	                   },
	                   distance: 0
	               },
	               showInLegend: false,
	               colors:['#5b9bd5','#f37926','#A5A5A5','#F3BF1D','#71ae48','#66CDAA','#2E8B57','#00CD00','#00CDCD','#90EE90','#C67171']
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
	   var bizNames = sampleData.bizNames;
	   var colors = ['#5b9bd5','#f37926','#A5A5A5','#F3BF1D','#71ae48','#66CDAA','#2E8B57'];
	   $("#"+legend).empty();
	   for(var i=0;i<bizNames.length;i++){
		   if(i>colors.length-1){
			   $("#"+legend).append("<i style='float: left;background:"+colors[i%colors.length]+";width:25px;height:15px;'></i><span class='fontStyle' style='float: left;height:20px;'>&nbsp;&nbsp;"+bizNames[i]+"&nbsp;&nbsp;</span>");
		   }else{
		   	   $("#"+legend).append("<i style='float: left;background:"+colors[i]+";width:25px;height:15px;'></i><span class='fontStyle' style='float: left;height:20px;'>&nbsp;&nbsp;"+bizNames[i]+"&nbsp;&nbsp;</span>");
		   }
	   }
	}
</script>