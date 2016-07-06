<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	

<div class="titleFont">&nbsp;&nbsp;告警信息图</div>
<a href="###" style="width:100%; height:100%;">
	<div style="width: 100%;height: 84%;">
		<div id='alarmInfo' class='chartStyle'></div>
	</div>
</a>
<script type="text/javascript">
	$(function(){
		initAlarmInfo("alarmInfo");
	});
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
	        colors: ['#ED7D31','#5b9bd5','#f37926','#A5A5A5','#F3BF1D','#71ae48','#66CDAA','#2E8B57'],
	        title: {
	            text: ''
	        },
	        xAxis: {
	            categories: sampleData.types,
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
               title: {
                   text: ''
               },
//                type: 'logarithmic',//坐标轴类型为对数
               stackLabels: {
                   enabled: false,
                   style: {
                	   fontWeight: 'normal',
		                fontFamily: 'Microsoft Yahei',
		                fontSize : '12px',
                       color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                   }
               }
	        },
	        legend: {
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
               enabled:false
           },
           legend: {
               enabled: true,
               borderWidth: 0,
               itemStyle: {
	                color: '#000000',
	                fontWeight: 'normal',
	                fontFamily: 'Microsoft Yahei',
	                fontSize : '12px'
	            }
           },
	        tooltip: {
	        	style: {
		        	color: '#000000',
	                fontWeight: 'normal',
	                fontFamily: 'Microsoft Yahei',
	                fontSize : '12px'
		        }
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
                   },
                   showInLegend: true
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
</script>