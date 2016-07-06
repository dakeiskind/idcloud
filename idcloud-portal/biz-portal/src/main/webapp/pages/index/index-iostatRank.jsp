<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	

<div class="titleFont">&nbsp;&nbsp;业务流量排名（Top）
<a href='#' onclick='changeIoRank(this)'><i style='font-size:14px;' class='icon-down-3'></i></a>
</div>
<a href="###" style="width:100%; height:100%;">
	<div style="width: 100%;height: 84%;">
		<div id='ioStatRank' class='chartStyle'></div>
	</div>
</a>
<script type="text/javascript">
	$(function(){
		initIostatRank("DESC","ioStatRank");
	});
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
                	   initIostatRank("DESC","ioStatRank");
                   	   toFullScreen('ioStatRank','业务流量排名','DESC');
                   }
               }
	        },
	        title: {
	            text: ''
	        },
	        xAxis: {
	            categories: sampleData.bizList,
	            labels: {
	            	rotation: -45,
	                align: 'right',
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
	        legend: {
	            enabled:true,
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
	        tooltip: {
	            pointFormat: '{point.y}</b>',
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
	            name: '流量（Mbps）',
	            data: sampleData.ioList,
	            dataLabels: {
	                enabled: false,
	                rotation: null,
	                color: '#000000',
	                align: 'center'
	            }
	        }]
	    });
	 }
</script>