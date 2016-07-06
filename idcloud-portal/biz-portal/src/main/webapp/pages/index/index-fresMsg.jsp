<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<div class="titleFont">&nbsp;&nbsp;资源回收概况</div>
<a href="###" style="width:100%; height:100%;">
	<div style="width: 100%;height: 84%;">
		<div id='fresMsg' class='chartStyle'></div>
	</div>
</a>

<script type="text/javascript">
	$(function(){
		initFreeRes("fresMsg"); 
	});
	function initFreeRes(str){
		 var  sampleData = "";
		 Core.AjaxRequest({
			url : ws_url + "/rest/count/findResRecoverySurvey",
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
                   	toFullScreen('fresMsg','资源回收概况','');
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
	            categories: sampleData.biz,
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
           series: [{
               name: '回收CPU（核）',
               data: sampleData.cpu,
               color: '#579DDB'
   
           }, {
               name: '回收内存（Gb）',
               data: sampleData.mem,
               color: '#f37926'
   
           }, {
               name: '回收存储（Tb）',
               data: sampleData.storage,
               color:'#A5A5A5'
   
           }]
       });
	}
</script>