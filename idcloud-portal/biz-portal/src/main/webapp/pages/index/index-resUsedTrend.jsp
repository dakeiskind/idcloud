<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	

<div class="titleFont">&nbsp;&nbsp;资源占用趋势</div>
<a href="###" style="width:100%; height:100%;">
	<div style="width: 100%;height: 84%;">
		<div id='resUsageTrend' class='chartStyle'></div>
	</div>
</a>

<script type="text/javascript">
	$(function(){
		initResUsed("resUsageTrend"); 
	});
	function initResUsed(str){
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
		        categories: sampleData.time,
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
		        min: 0,
		        title: {
		            text: ''
		        },
		        labels: {
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
</script>