<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<div class="titleFont">&nbsp;&nbsp;服务流量概况</div>
<a href="###" style="width:100%; height:100%;">
	<div style="width: 100%;height: 84%;">
		<div id='iostatUsed' class='chartStyle'></div>
	</div>
</a>
<script type="text/javascript">
	var date ;
	var max = new Array;
	var avg = new Array;
	var  sampleData = "";
	$(function(){
		//加载图
		initIostat("iostatUsed");
	});
	function initIostat(str){
		Core.AjaxRequest({
			url : ws_url + "/rest/count/findIostatSurvey",
			type : 'post',
			async:false,
			callback : function(data) {
				sampleData = data;
				date = data.date;
				//加载对应的峰值和均值
				getIoMaxAndAvg();
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
		    scrollbar: {
				liveRedraw: false
			},
		    xAxis: {
		        categories: sampleData.data,
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
		        plotLines: [{
		            value: 0,
		            width: 1,
		            color: '#808080'
		        }],
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
		    	enabled: true,
		    	formatter: function () {
		    		var i = this.point.x;
		    		var y = this.point.y;
		    		var sname = this.series.name;
		    		var str = "";
	    			str = sname +'：<b>'+ y +'</b><br/>峰值流量：'+max[i]+',  均值流量：'+avg[i];
					return str;
	            },
		        valueSuffix: '',
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
		                enabled: false
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
		        color: '#5b9bd5'
		    }, {
		        name: '内容引入（Mbps）',
		        data: sampleData.content,
		        color: '#f37926'
		    }, {
		        name: '其他（自有，创新，政企）（Mbps）',
		        data: sampleData.others,
		        color:'#A5A5A5'
		    }]
		});
	}
	
	function getIoMaxAndAvg(){
		for(var i=0;i<date.length;i++){
			if(i==0){
				max.push(sampleData.sum[0]);
				avg.push(sampleData.sum[0]);
			}else{
				var params = date[0]+":"+date[i];
				Core.AjaxRequest({
					url : ws_url + "/rest/count/getIoMaxAndAvg",
					type : 'post',
					params: {name:params},
					showWaiting:false,
					async:true,
					callback : function(data) {
		                max.push(data.max);
						avg.push(data.avg);
					}
				});
			}
		}
	}
</script>