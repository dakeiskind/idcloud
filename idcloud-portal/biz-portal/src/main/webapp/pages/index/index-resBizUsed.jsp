<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<div class="titleFont">&nbsp;&nbsp;资源占用概况</div>
<a href="###" style="width:100%; height:100%;">
	<div style="width: 100%;height: 84%;">
		<div id='bizResUsed' class='chartStyle'></div>
	</div>
</a>

<script type="text/javascript">
	$(function(){
		initBizRes("bizResUsed");
	});
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
		        categories: sampleData.bizList,
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
		        type: 'logarithmic',//坐标轴类型为对数
		//         minorTickInterval: 'auto', //小单位刻度为自动
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
</script>