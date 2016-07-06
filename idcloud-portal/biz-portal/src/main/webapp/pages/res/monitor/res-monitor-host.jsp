<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   <div style="width:100%;height:100%;overflow-y:auto;">
   		<div style="width:98%;height:40px;margin-left:1%;">
   			 <table border="0" width="100%" height="40px" cellpadding="0" cellspacing="0">
   			 	 <tr>
   			 	 	<td width="30" align="right">周期:</td>
   			 	 	<td width="130" align="center"><div id="searchMonitorInfo"></div></td>
   			 	 	<td width="80" align="left"><input id="searchMonitorBtn" type="button" value="查询" /></td>
   			 	 	<td align="right"><input id="searchMonitorRefresh" type="button" value="刷新" /></td>
   			 	 </tr>
   			 </table>
   		</div>
   		
		<div style="width:98%;margin-left:1%;">   		
		   		<div class="customPanel" style="width:49.5%;height:300px;float:left">
			  		    <div class="title">&nbsp;&nbsp;CPU使用率</div>
				        <div>
				        	<div id='cpuUsedchartContainer' style="width:100%; height:275px;border:0px;"></div>
				        </div>
		  		</div>
		  		
		  		<div class="customPanel" style="width:49%;height:300px;margin-left:1%;float:left">
			  		    <div class="title">&nbsp;&nbsp;内存使用率</div>
				        <div>
				        	<div id='memoryUsedchartContainer' style="width:100%; height:275px;border:0px;"></div>
				        </div>
		  		</div>
		  		<div class="customPanel" style="width:49.5%;margin-top:10px;height:300px;float:left">
			  		    <div class="title">&nbsp;&nbsp;CPU使用历史趋势</div>
				        <div>
				        	<div id='cpuUsedHistorychartContainer' style="width:100%; height:275px;border:0px;"></div>
				        </div>
		  		</div>
		  		
		  		<div class="customPanel" style="width:49%;height:300px;margin-top:10px;margin-left:1%;float:left">
			  		    <div class="title">&nbsp;&nbsp;内存使用历史趋势</div>
				        <div>
				        	<div id='memoryUsedHistorychartContainer' style="width:100%; height:275px;border:0px;"></div>
				        </div>
		  		</div>
	  	</div>
	  	
	  	<div style="width:98%;margin-left:1%;">   		
		   		<div class="customPanel" style="width:99.6%;height:300px;margin-top:10px; float:left">
			  		    <div class="title">&nbsp;&nbsp;网络流速</div>
				        <div>
				        	<div id='networkSpeedchartContainer' style="width:100%; height:275px;border:0px;"></div>
				        </div>
		  		</div>
	  	</div>
   
   </div>
   
   <script type="text/javascript">
   		// 初始化查询和刷新按钮
   		var source = [
                    "五分钟",
                    "一个小时",
                    "一天",
                    "一个月",
                    "一年"
		        ];
                // Create a jqxDropDownList
                $("#searchMonitorInfo").jqxDropDownList({ source: source, selectedIndex: 1, width: '120', height: '22'});
   		
   		
		$("#searchMonitorBtn").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		$("#searchMonitorRefresh").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		
		initHostCpuUsedRate();
		initHostMemoryUsedRate();
		initHostCpuHistoryUsedRate();
		initHostMemoryHistoryUsedRate();
		initNetworkSpeedchartContainerRate();
		
		// 内存使用率
		function initHostCpuUsedRate(){
			 var sampleData = [
					{ Day: '16:00', Goal: 40 },
                    { Day: '16:05', Goal: 40 },
                    { Day: '16:10', Goal: 50 },
                    { Day: '16:15', Goal: 60 },
                    { Day: '16:20', Goal: 40 },
                    { Day: '16:25', Goal: 50 },
                    { Day: '16:30', Goal: 60 },
                    { Day: '16:35', Goal: 40 },
                    { Day: '16:40', Goal: 50 },
                    { Day: '16:45', Goal: 60 },
                    { Day: '16:50', Goal: 40 },
                    { Day: '16:55', Goal: 50 }
                ];
            // prepare jqxChart settings
            var settings = {
                title: "CPU使用率",
                description: "当前查询时间2014-4-13 16点",
                enableAnimations: true,
                showLegend: true,
                showBorderLine: false,
                padding: { left: 10, top: 5, right: 10, bottom: 5 },
                titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
                source: sampleData,
                categoryAxis:
                    {
                        text: 'Category Axis',
                        textRotationAngle: 0,
                        dataField: 'Day',
                        showTickMarks: true,
                        valuesOnTicks: false,
                        tickMarksInterval: 1,
                        tickMarksColor: '#888888',
                        unitInterval: 1,
                        gridLinesInterval: 1,
                        gridLinesColor: '#888888',
                        axisSize: 'auto'
                    },
                colorScheme: 'scheme05',
                seriesGroups:
                    [
                        {
                            type: 'line',
                            showLabels: true,
                            valueAxis:
                            {
                                unitInterval: 10,
                                minValue: 0,
                                maxValue: 100,
                                description: '单位(%)',
                                axisSize: 'auto',
                                tickMarksColor: '#888888'
                            },
                            series: [
                                    { dataField: 'Goal', displayText: '时间粒度(5分钟)', symbolType: 'square'}
                                ]
                        }
                    ]
            };
            // setup the chart
            $('#cpuUsedchartContainer').jqxChart(settings);
		}
		
		// 内存使用率
		function initHostMemoryUsedRate(){
			 var sampleData = [
                    { Day: '16:00', Goal: 90 },
                    { Day: '16:05', Goal: 30 },
                    { Day: '16:10', Goal: 60 },
                    { Day: '16:15', Goal: 60 },
                    { Day: '16:20', Goal: 70 },
                    { Day: '16:25', Goal: 20 },
                    { Day: '16:30', Goal: 60 },
                    { Day: '16:35', Goal: 30 },
                    { Day: '16:40', Goal: 50 },
                    { Day: '16:45', Goal: 60 },
                    { Day: '16:50', Goal: 40 },
                    { Day: '16:55', Goal: 50 }
                ];
            // prepare jqxChart settings
            var settings = {
                title: "内存使用率",
                description: "当前查询时间2014-4-13 16点",
                enableAnimations: true,
                showLegend: true,
                showBorderLine: false,
                padding: { left: 10, top: 5, right: 10, bottom: 5 },
                titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
                source: sampleData,
                categoryAxis:
                    {
                        text: 'Category Axis',
                        textRotationAngle: 0,
                        dataField: 'Day',
                        showTickMarks: true,
                        valuesOnTicks: false,
                        tickMarksInterval: 1,
                        tickMarksColor: '#888888',
                        unitInterval: 1,
                        gridLinesInterval: 1,
                        gridLinesColor: '#888888',
                        axisSize: 'auto'
                    },
                colorScheme: 'scheme05',
                seriesGroups:
                    [
                        {
                            type: 'line',
                            showLabels: true,
                            valueAxis:
                            {
                                unitInterval: 10,
                                minValue: 0,
                                maxValue: 100,
                                description: '单位(%)',
                                axisSize: 'auto',
                                tickMarksColor: '#888888'
                            },
                            series: [
                                    { dataField: 'Goal', displayText: '时间粒度(5分钟)', symbolType: 'square'}
                                ]
                        }
                    ]
            };
            // setup the chart
            $('#memoryUsedchartContainer').jqxChart(settings);
		}
		
		// 逻辑磁盘占用率
		function initHostCpuHistoryUsedRate(){
			 var sampleData = [
					{ Day: '16:00', Goal: 90 },
					{ Day: '16:05', Goal: 30 },
					{ Day: '16:10', Goal: 60 },
					{ Day: '16:15', Goal: 60 },
					{ Day: '16:20', Goal: 70 },
					{ Day: '16:25', Goal: 20 },
					{ Day: '16:30', Goal: 60 },
					{ Day: '16:35', Goal: 30 },
					{ Day: '16:40', Goal: 50 },
					{ Day: '16:45', Goal: 60 },
					{ Day: '16:50', Goal: 40 },
					{ Day: '16:55', Goal: 50 }
                ];
            // prepare jqxChart settings
            var settings = {
                title: "逻辑磁盘占用率",
                description: "当前查询时间2014-4-13 16点",
                enableAnimations: true,
                showLegend: true,
                showBorderLine: false,
                padding: { left: 10, top: 5, right: 10, bottom: 5 },
                titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
                source: sampleData,
                categoryAxis:
                    {
                        text: 'Category Axis',
                        textRotationAngle: 0,
                        dataField: 'Day',
                        showTickMarks: true,
                        valuesOnTicks: false,
                        tickMarksInterval: 1,
                        tickMarksColor: '#888888',
                        unitInterval: 1,
                        gridLinesInterval: 1,
                        gridLinesColor: '#888888',
                        axisSize: 'auto'
                    },
                colorScheme: 'scheme05',
                seriesGroups:
                    [
                        {
                            type: 'line',
                            showLabels: true,
                            valueAxis:
                            {
                                unitInterval: 10,
                                minValue: 0,
                                maxValue: 100,
                                description: '单位(%)',
                                axisSize: 'auto',
                                tickMarksColor: '#888888'
                            },
                            series: [
                                    { dataField: 'Goal', displayText: '时间粒度(5分钟)', symbolType: 'square'}
                                ]
                        }
                    ]
            };
            // setup the chart
            $('#cpuUsedHistorychartContainer').jqxChart(settings);
		}
		
		// 磁盘I/O
		function initHostMemoryHistoryUsedRate(){
			 var sampleData = [
                    { Day: '16:00', Goal: 90 , Goal1: 60 },
					{ Day: '16:05', Goal: 30 , Goal1: 80},
					{ Day: '16:10', Goal: 60 , Goal1: 10},
					{ Day: '16:15', Goal: 60 , Goal1: 27},
					{ Day: '16:20', Goal: 70 , Goal1: 50},
					{ Day: '16:25', Goal: 20 , Goal1: 30},
					{ Day: '16:30', Goal: 60 , Goal1: 20},
					{ Day: '16:35', Goal: 30 , Goal1: 10},
					{ Day: '16:40', Goal: 50 , Goal1: 80},
					{ Day: '16:45', Goal: 60 , Goal1: 90},
					{ Day: '16:50', Goal: 40 , Goal1: 90},
					{ Day: '16:55', Goal: 50 , Goal1: 90},
					{ Day: '16:00', Goal: 40 , Goal1: 90},
					{ Day: '16:55', Goal: 70 , Goal1: 90}
                ];
            // prepare jqxChart settings
            var settings = {
                title: "磁盘I/O",
                description: "当前查询时间2014-4-13 16点",
                enableAnimations: true,
                showLegend: true,
                showBorderLine: false,
                padding: { left: 10, top: 5, right: 10, bottom: 5 },
                titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
                source: sampleData,
                categoryAxis:
                    {
                        text: 'Category Axis',
                        textRotationAngle: 0,
                        dataField: 'Day',
                        showTickMarks: true,
                        valuesOnTicks: false,
                        tickMarksInterval: 1,
                        tickMarksColor: '#888888',
                        unitInterval: 1,
                        gridLinesInterval: 1,
                        gridLinesColor: '#888888',
                        axisSize: 'auto'
                    },
                colorScheme: 'scheme05',
                seriesGroups:
                    [
                        {
                            type: 'line',
                            showLabels: true,
                            valueAxis:
                            {
                                unitInterval: 10,
                                minValue: 0,
                                maxValue: 100,
                                description: '单位(%)',
                                axisSize: 'auto',
                                tickMarksColor: '#888888'
                            },
                            series: [
                                    { dataField: 'Goal', displayText: '磁盘I/O写入', symbolType: 'square'},
                                    { dataField: 'Goal1', displayText: '磁盘I/O读出', symbolType: 'square'}
                                ]
                        }
                    ]
            };
            // setup the chart
            $('#memoryUsedHistorychartContainer').jqxChart(settings);
		}
		
		// 初始化网络流速
		function initNetworkSpeedchartContainerRate(){
			 var sampleData = [
                    { Day: '16:00', Goal: 90 , Goal1: 60 },
					{ Day: '16:05', Goal: 30 , Goal1: 80},
					{ Day: '16:10', Goal: 60 , Goal1: 10},
					{ Day: '16:15', Goal: 60 , Goal1: 27},
					{ Day: '16:20', Goal: 70 , Goal1: 50},
					{ Day: '16:25', Goal: 20 , Goal1: 30},
					{ Day: '16:30', Goal: 60 , Goal1: 20},
					{ Day: '16:35', Goal: 30 , Goal1: 10},
					{ Day: '16:40', Goal: 50 , Goal1: 80},
					{ Day: '16:45', Goal: 60 , Goal1: 90},
					{ Day: '16:50', Goal: 40 , Goal1: 90},
					{ Day: '16:55', Goal: 50 , Goal1: 90},
					{ Day: '16:00', Goal: 40 , Goal1: 90},
					{ Day: '16:55', Goal: 70 , Goal1: 90}
                ];
            // prepare jqxChart settings
            var settings = {
                title: "磁盘I/O",
                description: "当前查询时间2014-4-13 16点",
                enableAnimations: true,
                showLegend: true,
                showBorderLine: false,
                padding: { left: 10, top: 5, right: 10, bottom: 5 },
                titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
                source: sampleData,
                categoryAxis:
                    {
                        text: 'Category Axis',
                        textRotationAngle: 0,
                        dataField: 'Day',
                        showTickMarks: true,
                        valuesOnTicks: false,
                        tickMarksInterval: 1,
                        tickMarksColor: '#888888',
                        unitInterval: 1,
                        gridLinesInterval: 1,
                        gridLinesColor: '#888888',
                        axisSize: 'auto'
                    },
                colorScheme: 'scheme05',
                seriesGroups:
                    [
                        {
                            type: 'line',
                            showLabels: true,
                            valueAxis:
                            {
                                unitInterval: 10,
                                minValue: 0,
                                maxValue: 100,
                                description: '单位(%)',
                                axisSize: 'auto',
                                tickMarksColor: '#888888'
                            },
                            series: [
                                    { dataField: 'Goal', displayText: '网络流入流速', symbolType: 'square'},
                                    { dataField: 'Goal1', displayText: '网络流出流速', symbolType: 'square'}
                                ]
                        }
                    ]
            };
            // setup the chart
            $('#networkSpeedchartContainer').jqxChart(settings);
		}
		
		
   </script>
   