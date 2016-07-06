<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	

<div class="titleFont">&nbsp;&nbsp;资源利用率概况</div>
<a href="###" style="width:100%; height:100%;">
	<div style="width: 100%;height: 84%;">
		<div id='resUsage' class='chartStyle'></div>
	</div>
</a>

<script type="text/javascript">
	$(function(){
		initResUsage("resUsage"); 
	});
	function initResUsage(str){
		 var  sampleData = "";
		 Core.AjaxRequest({
			url : ws_url + "/rest/count/findResRateSurvey",
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
               categories: sampleData.bizName,
               labels: {
		        	style:{
		        		color: '#000000',
		                fontWeight: 'normal',
		                fontFamily: 'Microsoft Yahei',
		                fontSize : '5px'
		        	}
		        }
           },
           yAxis: {
           	min:0,
               title: {
                   text: ''
               },
               labels: {
                   format: '{value} %',
		        	style:{
		        		color: '#000000',
		                fontWeight: 'normal',
		                fontFamily: 'Microsoft Yahei',
		                fontSize : '5px'
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
	                fontSize : '5px'
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
</script>