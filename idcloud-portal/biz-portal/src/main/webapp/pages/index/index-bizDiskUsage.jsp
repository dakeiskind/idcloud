<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	

<div class="titleFont">&nbsp;&nbsp;业务存储使用率排名（Top）
<a href='#' onclick='changeIoRank(this)'><i style='font-size:14px;' class='icon-down-3'></i></a>
</div>
<a href="###" style="width:100%; height:100%;">
	<div style="width: 100%;height: 84%;">
		<div id='bizDiskUsage' class='chartStyle'></div>
	</div>
</a>
<script type="text/javascript">
	$(function(){
		initBizDiskUsage("DESC","bizDiskUsage");
	});
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
		        	style:{
		        		color: '#000000',
		                fontWeight: 'normal',
		                fontFamily: 'Microsoft Yahei',
		                fontSize : '5px'
		        	}
		        }
	        },
	        yAxis: {
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
	        legend: {
	            enabled: false,
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
           legend: {
               enabled: true,
               borderWidth: 0
           },
	        tooltip: {
	            pointFormat: '{point.y:1f}%</b>',
	            style: {
		        	color: '#000000',
	                fontWeight: 'normal',
	                fontFamily: 'Microsoft Yahei',
	                fontSize : '5px'
		        }
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
</script>