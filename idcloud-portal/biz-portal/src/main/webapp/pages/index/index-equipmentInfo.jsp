<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
	<%@ include file="/pages/common/taglibs.jsp"%>

<div class="titleFont">&nbsp;&nbsp;设备信息图</div>
<a href="###" style="width:100%; height:100%;color: black;" onclick="toFullScreen('equipmentInfo','设备信息图','')">
	<div class="chartStyle2" style="width:100%;height:90%;">
		<div id='equipmentInfoChart' style='width:49%; height:98%;float:left;border:0px;'></div>
		<div id='equipmentInfoImg' style='width:49%; height:98%;float:left;border:0px;'></div>
	</div>
</a>
<script type="text/javascript">
	$(function(){
		var chartdiv = $("#equipmentInfoChart");
		var imgdiv = $("#equipmentInfoImg");
		initEquipmentInfo("equipmentInfoChart","equipmentInfoImag",chartdiv,imgdiv);
	});
	function initEquipmentInfo(chartId,imgId,chartDiv,imgDiv){
		 Core.AjaxRequest({
			url : ws_url + "/rest/count/getEquipmentDataInfo",
			type : 'post',
			async:false,
			callback : function(data) {
				var html2 = "";
				html2 = html2+ "<div style='height:99%;width:99%;'><table style='width:100%;height:100%;font-size:16px;font-color:black;'><tr style='height:55px;'>";
				html2 = html2 + "<td style='width:40%;text-align:right;'><span style='margin-top: 1px;font-family: Microsoft Yahei;'>存储设备</span></td>"+
								"<td style='width: 15%;'><i class='icon-hdd-1' style='font-size:60px;color:#0B36FF;boder:0;'></i></td>"+
								"<td style='text-align:left;'>"+(data.SSum ==undefined ? 0:data.SSum.value)+"台</td>";
				html2 = html2 + "</tr><tr style='height:55px;'><td style='width:40%;text-align:right;'><span style='margin-top: 1px;font-family: Microsoft Yahei;'>交换机</span></td>"+
								"<td style='width: 15%;'><img alt='' src='${ctx}/images/indexStatic/router.png' style='height: 50px;border-top-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-right-width: 0px;'></td>"+
								"<td style='text-align:left;'>"+(data.SWSum ==undefined ? 0:data.SWSum.value)+"台</td>";
				html2 = html2 + "</tr><tr style='height:55px;'><td style='width:40%;text-align:right;'><span style='margin-top: 1px;font-family: Microsoft Yahei;'>刀箱</span></td>"+
								"<td style='width: 15%;'><img alt='' src='${ctx}/images/indexStatic/switch.png' style='height: 50px;border-top-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-right-width: 0px;'></td>"+
								"<td style='text-align:left;'>"+(data.BFSum ==undefined ? 0:data.BFSum.value)+"台</td>";
				html2 = html2 + "</tr><tr style='height:55px;'><td style='width:40%;text-align:right;'><span style='margin-top: 1px;font-family: Microsoft Yahei;'>负载均衡器</span></td>"+
								"<td style='width: 15%;'><img alt='' src='${ctx}/images/indexStatic/balanc.png' style='height: 50px;border-top-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-right-width: 0px;'></td>"+
								"<td style='text-align:left;'>"+(data.LBSum ==undefined ? 0:data.LBSum.value)+"台</td>";
				html2 = html2 + "</tr></table></div>";
				
				$(imgDiv).append(html2);
				
				initServerChart(chartId,data.serverEquip);
			}
		 });
	 }
	
	function initServerChart(id,data){
		$('#'+id).highcharts({
		       chart: {
		           plotBackgroundColor: null,
		           plotBorderWidth: null,
		           plotShadow: false
		       },
		       title: {
		           text: '服务器（'+data.sumNum+'台）',
		           style: {
		               color: '#000000',
		               fontWeight: 'bold',
		               fontFamily: 'Microsoft Yahei',
		               fontSize : '12px'
		           },
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
// 		            layout: 'vertical',
// 		            align: 'bottom',
		            verticalAlign: 'bottom',
		            borderWidth: 0,
		            y:-25,
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
		               dataLabels: {
		                   enabled: true,
		                   format: '<b style="color:{point.color}">{point.y}台',
		                   style: {
		                       color: 'black'
		                   },
		                   distance: 0
		               },
		               showInLegend: true,
		               colors:['#5b9bd5','#f37926','#A5A5A5','#F3BF1D','#71ae48','#66CDAA','#2E8B57']
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
		           data: eval(data.data)
		       }]
		   });
	}
</script>