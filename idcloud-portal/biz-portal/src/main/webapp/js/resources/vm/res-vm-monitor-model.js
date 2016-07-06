// 监控主机 
var monitorVMModel = function () {
	    // 初始化window
	    this.initPopWindow = function(){
	    	// 监控信息
	    	$("#monitorVmWindow").jqxWindow({
                width: 800, 
                height:380,
                theme:currentTheme,  
                resizable: true,  
                isModal: true, 
                autoOpen: false, 
                modalOpacity: 0.3
            });
	    };
	    
	    // cpu使用率
		this.initHostCpuUsedRate = function(nodeId){
			var json = null;
			// 获取监控信息
			Core.AjaxRequest({
				url : ws_url + "/rest/vms/getCpuInfo/"+nodeId+"/20140821000000:20140827000000",
				type:"get",
				async:false,
				callback : function(data) {
					json = data;
				}
			});
			
			// 初始化图表
			$('#cpuUsedchartContainer').highcharts({
	            chart: {
	                zoomType: 'x',
	                spacingRight: 20
	            },
	            title: {
	                text: ''
	            },
	            subtitle: {
	                text: '（单击图表拖动进行放大）',
					style : {
						color: '#666666',
						fontFamily : '微软雅黑'
					}
	            },
	            xAxis: {
                    type: 'datetime',
                    dateTimeLabelFormats: {
                        day: '%m-%d'
                    },
                    style: {
                        fontFamily : '微软雅黑'
                    },
	                minRange : 1800 * 1000,
	                maxZoom: 7 * 24 * 3600 * 1000, // fourteen days
	                title: {
	                    text: ""
	                }
	            },
	            yAxis: {
	                title: {
	                    text: '使用率(%)',
		                style: {
	                		fontSize : '12px',
	                		fontWeight : 'bold',
							fontFamily : '微软雅黑'
		                }
	                },
	                min: 0,
	                max:100
	            },
	            tooltip: {
	                shared: true
	            },
	            credits:{ 
                    enabled:false
                },
	            exporting:{ 
                    enabled:false
                },
	            legend: {
	                enabled: false
	            },
	            plotOptions: {
	                area: {
                       tooltip: {
                            xDateFormat: '%Y-%m-%d %H:%M',
                            valueDecimals: 2,
                            valueSuffix: '%'
                        },
	                    fillColor: {
	                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
	                        stops: [
	                            [0, Highcharts.getOptions().colors[0]],
	                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
	                        ]
	                    },
	                    lineWidth: 1,
	                    marker: {
	                        enabled: false
	                    },
	                    shadow: false,
	                    states: {
	                        hover: {
	                            lineWidth: 1
	                        }
	                    },
	                    threshold: null
	                }
	            },
	    
	            series: [{
	                type: 'area',
	                name: '使用率',
	                pointInterval: 300 * 1000,
	                pointStart: "2014-08-20 09:46:16",
	                data:json
	            }]
	        });
		};
		
		// 内存使用率
		this.initHostMemoryUsedRate = function(nodeId){
			var json = null;
			// 获取监控信息
			Core.AjaxRequest({
				url : ws_url + "/rest/vms/getMemoryInfo/"+nodeId+"/20140821000000:20140827000000",
				type:"get",
				async:false,
				callback : function(data) {
					json = data;
				}
			});
			
			// 初始化图表
			$('#memoryUsedchartContainer').highcharts({
	            chart: {
	                zoomType: 'x',
	                spacingRight: 20
	            },
	            title: {
	                text: ''
	            },
	            subtitle: {
	                text: '（单击图表拖动进行放大）',
					style : {
						color: '#666666',
						fontFamily : '微软雅黑'
					}
	            },
	            xAxis: {
                    type: 'datetime',
                    dateTimeLabelFormats: {
                        day: '%m-%d'
                    },
                    style: {
                        fontFamily : '微软雅黑'
                    },
	                minRange : 1800 * 1000,
	                maxZoom: 7 * 24 * 3600 * 1000, // fourteen days
	                title: {
	                    text: ""
	                }
	            },
	            yAxis: {
	                title: {
	                    text: '使用率(%)',
		                style: {
	                		fontSize : '12px',
	                		fontWeight : 'bold',
							fontFamily : '微软雅黑'
		                }
	                },
	                min: 0,
	                max:100
	            },
	            tooltip: {
	                shared: true
	            },
	            credits:{ 
                    enabled:false
                },
	            exporting:{ 
                    enabled:false
                },
	            legend: {
	                enabled: false
	            },
	            plotOptions: {
	                area: {
                       tooltip: {
                            xDateFormat: '%Y-%m-%d %H:%M',
                            valueDecimals: 2,
                            valueSuffix: '%'
                        },
	                    fillColor: {
	                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
	                        stops: [
	                            [0, Highcharts.getOptions().colors[0]],
	                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
	                        ]
	                    },
	                    lineWidth: 1,
	                    marker: {
	                        enabled: false
	                    },
	                    shadow: false,
	                    states: {
	                        hover: {
	                            lineWidth: 1
	                        }
	                    },
	                    threshold: null
	                }
	            },
	    
	            series: [{
	                type: 'area',
	                name: '使用率',
	                pointInterval: 300 * 1000,
	                pointStart: "2014-08-20 09:46:16",
	                data:json
	            }]
	        });
		};
		
		// 逻辑磁盘占用率
		this.initHostDiskHistoryUsedRate = function(nodeId){
			var json = null;
			// 获取监控信息
			Core.AjaxRequest({
				url : ws_url + "/rest/vms/getCpuInfo/"+nodeId+"/20140821000000:20140827000000",
				type:"get",
				async:false,
				callback : function(data) {
// 					console.log(JSON.stringify(data));
// 					json = data;
				}
			});
			
			// 初始化图表
			$('#diskUsedchartContainer').highcharts({
	            chart: {
	                zoomType: 'x',
	                spacingRight: 20
	            },
	            title: {
	                text: ''
	            },
	            subtitle: {
	                text: '（单击图表拖动进行放大）',
					style : {
						color: '#666666',
						fontFamily : '微软雅黑'
					}
	            },
	            xAxis: {
                    type: 'datetime',
                    dateTimeLabelFormats: {
                        day: '%m-%d'
                    },
                    style: {
                        fontFamily : '微软雅黑'
                    },
	                minRange : 1800 * 1000,
	                maxZoom: 7 * 24 * 3600 * 1000, // fourteen days
	                title: {
	                    text: ""
	                }
	            },
	            yAxis: {
	                title: {
	                    text: '使用率(%)',
		                style: {
	                		fontSize : '12px',
	                		fontWeight : 'bold',
							fontFamily : '微软雅黑'
		                }
	                },
	                min: 0,
	                max:100
	            },
	            tooltip: {
	                shared: true
	            },
	            credits:{ 
                    enabled:false
                },
	            exporting:{ 
                    enabled:false
                },
	            legend: {
	                enabled: false
	            },
	            plotOptions: {
	                area: {
                       tooltip: {
                            xDateFormat: '%Y-%m-%d %H:%M',
                            valueDecimals: 0,
                            valueSuffix: '%'
                        },
	                    fillColor: {
	                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
	                        stops: [
	                            [0, Highcharts.getOptions().colors[0]],
	                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
	                        ]
	                    },
	                    lineWidth: 1,
	                    marker: {
	                        enabled: false
	                    },
	                    shadow: false,
	                    states: {
	                        hover: {
	                            lineWidth: 1
	                        }
	                    },
	                    threshold: null
	                }
	            },
	    
	            series: [{
	                type: 'area',
	                name: '使用率',
	                pointInterval: 300 * 1000,
	                pointStart: "2014-08-20 09:46:16",
	                data:json
	            }]
	        });
		};
		
		// 磁盘I/O
		this.initHostDiskIoHistoryUsedRate = function(nodeId){
			var json = null;
			// 获取监控信息
			Core.AjaxRequest({
				url : ws_url + "/rest/vms/getCpuInfo/"+nodeId+"/20140821000000:20140827000000",
				type:"get",
				async:false,
				callback : function(data) {
//					json = data;
				}
			});
			
			// 初始化图表
			$('#diskIoUsedchartContainer').highcharts({
	            chart: {
	                zoomType: 'x',
	                spacingRight: 20
	            },
	            title: {
	                text: ''
	            },
	            subtitle: {
	                text: '（单击图表拖动进行放大）',
					style : {
						color: '#666666',
						fontFamily : '微软雅黑'
					}
	            },
	            xAxis: {
                    type: 'datetime',
                    dateTimeLabelFormats: {
                        day: '%m-%d'
                    },
                    style: {
                        fontFamily : '微软雅黑'
                    },
	                minRange : 1800 * 1000,
	                maxZoom: 7 * 24 * 3600 * 1000, // fourteen days
	                title: {
	                    text: ""
	                }
	            },
	            yAxis: {
	                title: {
	                    text: '使用率(%)',
		                style: {
	                		fontSize : '12px',
	                		fontWeight : 'bold',
							fontFamily : '微软雅黑'
		                }
	                },
	                min: 0,
	                max:100
	            },
	            tooltip: {
	                shared: true
	            },
	            credits:{ 
                    enabled:false
                },
	            exporting:{ 
                    enabled:false
                },
	            legend: {
	                enabled: false
	            },
	            plotOptions: {
	                area: {
                       tooltip: {
                            xDateFormat: '%Y-%m-%d %H:%M',
                            valueDecimals: 0,
                            valueSuffix: '%'
                        },
	                    fillColor: {
	                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
	                        stops: [
	                            [0, Highcharts.getOptions().colors[0]],
	                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
	                        ]
	                    },
	                    lineWidth: 1,
	                    marker: {
	                        enabled: false
	                    },
	                    shadow: false,
	                    states: {
	                        hover: {
	                            lineWidth: 1
	                        }
	                    },
	                    threshold: null
	                }
	            },
	    
	            series: [{
	                type: 'area',
	                name: '使用率',
	                pointInterval: 300 * 1000,
	                pointStart: "2014-08-20 09:46:16",
	                data:json
	            }]
	        });
		};
		
		// 初始化网络流速
		this.initNetworkSpeedchartContainerRate = function(nodeId){
			var json = null;
			// 获取监控信息
			Core.AjaxRequest({
				url : ws_url + "/rest/vms/getNetworkInfo/"+nodeId+"/20140821000000:20140827000000",
				type:"get",
				async:false,
				callback : function(data) {
					json = data;
				}
			});
			$('#networkSpeedchartContainer').highcharts({
			    chart: {
			        type: 'arearange',
			        zoomType: 'x'
			    },
			    
			    title: {
			        text: '网络流速(KB)'
			    },
			    
	            credits:{ 
	             	enabled:false
		        },
		        exporting:{ 
		            enabled:false
		        },
		        legend: {
		            enabled: false
		        },
			    xAxis: {
			        type: 'datetime'
			    },
			    
			    yAxis: {
			        title: {
			            text: null
			        }
			    },
			
			    tooltip: {
			        crosshairs: true,
			        shared: true,
			        valueSuffix: 'KB'
			    },
			    
			    legend: {
			        enabled: false
			    },
			
			    series: [{
			        name: '速度',
			        data: json
			    }]
			});
		};
 };
  
 
// 弹出监控信息 
 function popMonitorWindow(){
	 var rowindex = $('#vmdatagrid').jqxGrid('getselectedrowindex');
   	 var ok =  $("#jqxMonitorBtn").jqxButton("disabled");
	 if(rowindex >= 0 && !ok){
		 	var data = $('#vmdatagrid').jqxGrid('getrowdata', rowindex);
		 	
		  	// 初始化监控信息
		 	var monitor = new monitorVMModel(data.monitorNodeId);
		 	monitor.initHostCpuUsedRate(data.monitorNodeId);
		 	monitor.initHostMemoryUsedRate(data.monitorNodeId);
		 	monitor.initHostDiskHistoryUsedRate(data.monitorNodeId);
		 	monitor.initHostDiskIoHistoryUsedRate(data.monitorNodeId);
		 	monitor.initNetworkSpeedchartContainerRate(data.monitorNodeId);
		 
		  var windowW = $(window).width(); 
	 	  var windowH = $(window).height(); 
	 	  $("#monitorVmWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-380)/2 } });
	 	  $("#monitorVmWindow").jqxWindow('open');
	 }
 }