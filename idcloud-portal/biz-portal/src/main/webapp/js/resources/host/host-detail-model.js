var hostInfoModel = function(resHostSid){
	var me = this;
    this.items = ko.observableArray();
    this.userItems = ko.observableArray();
       
	 // 初始化弹出window
    this.initPopWindow = function(){
    	
    	$("#addHostUserWindow").jqxWindow({
            width: 450, 
            height:170,
            resizable: true,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#addHostUserCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	
            	$("#add-host-user-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-host-user-password").jqxPasswordInput({ width: '150', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme });
            	
            	$("#add-host-user-userGroup").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-host-user-privilege").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#add-host-user-description").jqxInput({placeHolder: "", height: 35, width:370, minLength: 1,theme:currentTheme});
            	
            	$("#addHostUserSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            	$("#addHostUserCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            }
		});
		
		$("#editHostUserWindow").jqxWindow({
            width: 450, 
            height:170,
            resizable: true,  
            isModal: true, 
            autoOpen: false, 
            theme: currentTheme,
            cancelButton: $("#editHostUserCancel"), 
            modalOpacity: 0.3,
            initContent:function(){
            	$("#edit-host-user-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-host-user-password").jqxPasswordInput({ width: '150', height: '22px', showStrength: false, showStrengthPosition: "right",theme:currentTheme });
            	
            	$("#edit-host-user-userGroup").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-host-user-privilege").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
            	$("#edit-host-user-description").jqxInput({placeHolder: "", height: 35, width:370, minLength: 1,theme:currentTheme});
            	
            	$("#editHostUserSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            	$("#editHostUserCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
            }
		});
		
	    $("#x86ServerWindows").jqxWindow({
            width: 800, 
            height:700,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#serverx86-close-button"), 
            modalOpacity: 0.3,
            initContent:function(){
              // 初始化新增用户页面组件
            $("#serverx86-close-button").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            }
   });
      $("#ibmServerWindows").jqxWindow({
            width: 800, 
            height:700,
            theme:currentTheme,  
            resizable: false,  
            isModal: true, 
            autoOpen: false, 
            cancelButton: $("#serveribm-close-button"), 
            modalOpacity: 0.3,
            initContent:function(){
              // 初始化新增用户页面组件
            $("#serveribm-close-button").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
            }
     });
    	
//		$("#editVmNameWindow").jqxWindow({
//            width: 280, 
//            height:100,
//            resizable: true,  
//            isModal: true, 
//            autoOpen: false, 
//            theme: currentTheme,
//            cancelButton: $("#modifyVmCancel"), 
//            modalOpacity: 0.3,
//            initContent:function(){
//            	$("#edit-vm-name").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
//            	$("#modifyVmSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
//            	$("#modifyVmCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
//            }
//		});

    };
    
 // 初始化验证规则   
	   this.initValidator = function(){
	   	$('#addHostUserForm').jqxValidator({
	           rules: [  
	                     { input: '#add-host-user-name', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#add-host-user-name', message: '账户名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                     { input: '#add-host-user-password', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#add-host-user-password', message: '账户名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                   
	                  ]
	   	});
	   	
	   	$('#editHostUserForm').jqxValidator({
	           rules: [  
	                     { input: '#edit-host-user-name', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#edit-host-user-name', message: '账户名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                     { input: '#edit-host-user-password', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
	                     { input: '#edit-host-user-password', message: '账户名称不能超过64个字符!', action: 'keyup, blur', rule: 'length=1,64' },
	                     
	                  ]
	   	});
	   	
	   	// 编辑块存储验证成功
	   	$('#addHostUserForm').on('validationSuccess', function (event) {
	   		 var vmuser = JSON.parse($("#addHostUserForm").serializeJson());
	   		 Core.AjaxRequest({
					url : ws_url + "/rest/osUsers/platform/create",
					params :vmuser,
					async:false,
					callback : function (data) {
						 var info = new hostInfoModel(resHostSid);
						 info.initHostUserDatagrid();
						 info.searchHostUserInfo();
						 $("#addHostUserWindow").jqxWindow('close');
				    }
				});
		});
	   	
	    // 编辑块存储验证成功
	   	$('#editHostUserForm').on('validationSuccess', function (event) {
	   		 var vmuser = JSON.parse($("#editHostUserForm").serializeJson());
	   		 Core.AjaxRequest({
					url : ws_url + "/rest/osUsers/update",
					params :vmuser,
					async:false,
					callback : function (data) {
						 var info = new hostInfoModel(resHostSid);
						 info.initHostUserDatagrid();
						 info.searchHostUserInfo();
						 $("#editHostUserWindow").jqxWindow('close');
				    }
				});
		});
	   	
	 
	};
	
	  // 设值主机的存储
	  this.searchHostStorageInfo1 = function(){
		  Core.AjaxRequest({
	 			url : ws_url + "/rest/storage/host",
	 			type:'post',
	 			params:{
	 				resHostSid:resHostSid
	 			},
	 			async:false,
	 			callback : function (data) {
	 				me.items(data);
	 				var sourcedatagrid ={
			              datatype: "json",
			              localdata: me.items
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#hostStorageDatagrid").jqxGrid({source: dataAdapter});
	 			}
		    });
	    };
	    
	    // 设值主机的虚拟机
	    this.initHostStorageDatagrid = function(){
	          $("#hostStorageDatagrid").jqxGrid({
	              width: "98%",
				  theme:currentTheme,
	              columnsresize: true,
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '存储名称', datafield: 'storageName',width:120},
	                  { text: '存储类别', datafield: 'storageCategoryName'},
	                  { text: '总容量(GB)', datafield: 'totalCapacity',width:60},
	                  { text: '可用容量(GB)', datafield: 'availableCapacity',width:80}
	                  
	              ]
	          });
	    };
	    
	    
	    // 初始化虚拟机的IP
	    this.searchHostUserInfo = function(){
			  Core.AjaxRequest({
					url : ws_url + "/rest/osUsers",
					type:"post",
					async:false,
					params:{
						resSid:resHostSid
					},
					callback : function(data) {
						me.userItems(data);
		 				var sourcedatagrid1 ={
				              datatype: "json",
				              localdata: me.userItems
		 			    };
		 			    var dataAdapterUser = new $.jqx.dataAdapter(sourcedatagrid1);	
		 			    $("#hostUserDatagrid").jqxGrid({source: dataAdapterUser});
					}
				});
	     };
	    
	    // 初始化用户datagrid的数据源
	    this.initHostUserDatagrid = function(){
	          $("#hostUserDatagrid").jqxGrid({
	              width: "98%",
				  theme:currentTheme,
	              columnsresize: true,
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              localization: gridLocalizationObj,
	              columns: [
	                  { text: '用户名', datafield: 'userName'},
	                  { text: '密码', datafield: 'password'}
	              ]
	          });
	    };

	    
	    // cpu使用率
		this.initHostCpuUsedRate = function(nodeId,perid,timeType){
			var json = null;
			var stime = perid+" 00:00:00";
			
			// 获取监控信息
			Core.AjaxRequest({
				url : ws_url + "/rest/vms/getCpuInfo/"+nodeId+"/"+timeType,
				type:"get",
				async:false,
				callback : function(data) {
					json = data;
				}
			});
			
			var str = "";
			var chartsTitle = [];
			for(var i=0;i<json.length;i++){
				chartsTitle.push(json[i].name);
				str += "<div id='cpuHostUsedchartContainer"+i+"' style='width:1020px;height:275px;border:0px;'></div>";
			}
			
			$("#cpuHostContainer").html(str);
			
			for(var i=0;i<json.length;i++){
				// 初始化图表
				$('#cpuHostUsedchartContainer'+i+'').highcharts({
		            chart: {
		                zoomType: 'x',
		                spacingRight: 20
		            },
		            title: {
		                text: chartsTitle[i]
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
		                minRange : 600 * 1000,
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
		                pointInterval: 600 * 1000,
		                pointStart: stime,
		                data:json[i].value
		            }]
		        });
			}
		};
		
		// 内存使用率
		this.initHostMemoryUsedRate = function(nodeId,perid,timeType){
			var json = null;
			var stime = perid+" 00:00:00";
			
			// 获取监控信息
			Core.AjaxRequest({
				url : ws_url + "/rest/vms/getMemoryInfo/"+nodeId+"/"+timeType,
				type:"get",
				async:false,
				callback : function(data) {
					$("#mem_max_usage").html(data.maxUsage+"%");
					$("#mem_avg_usage").html(data.avgUsage+"%"); 
					$("#mem_min_usage").html(data.minUsage+"%");
					json = data;
				}
			});
			
			// 初始化图表
			$('#memoryHostUsedchartContainer').highcharts({
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
	                minRange : 600 * 1000,
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
	                pointInterval: 600 * 1000,
	                pointStart: stime,
	                data:json.data
	            }]
	        });
		};
		
		// 逻辑磁盘占用率
		this.initHostDiskHistoryUsedRate = function(nodeId,perid,timeType){
			var json = null;
			var stime = perid+" 00:00:00";
			
			// 获取监控信息
			Core.AjaxRequest({
				url : ws_url + "/rest/vms/getDiskInfo/"+nodeId+"/"+timeType,
				type:"get",
				async:false,
				callback : function(data) {
 					json = data;
				}
			});
			
			// 解析数据，如果数据中存在多个磁盘的，需要分开显示
			var str = "";
			var chartsTitle = [];
			for(var i=0;i<json.length;i++){
				chartsTitle.push(json[i].name);
				str += "<div id='diskHostUsedchartContainer"+i+"' style='width:1020px;height:275px;border:0px;'></div>";
			}
			
			$("#diskHostContainer").html(str);
			
			for(var i=0;i<json.length;i++){
				
				// 初始化charts
				$('#diskHostUsedchartContainer'+i+'').highcharts({
		            chart: {
		                zoomType: 'x',
		                spacingRight: 20
		            },
		            title: {
		                text: chartsTitle[i]
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
		                minRange : 600 * 1000,
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
		                pointInterval: 600 * 1000,
		                pointStart: stime,
		                data:json[i].value
		            }]
		        });
			}

		};
		
		// 初始化监控信息
		this.initMonitorInfo = function(){
			var timeType = $("#searchMonitorInfo").val();
			var peroid = getThePeridTime(1);
			me.initHostCpuUsedRate(monitorNodeId,peroid,timeType);
		};

};


// 设置时间
function getThePeridTime(dcount){
	// 当前时间
	 var date = new Date();
	// 目标时间
	var milliseconds=date.getTime()-1000*60*60*24*dcount;
    var newDate = new Date();       
    newDate.setTime(milliseconds);       
    
    var newYear = newDate.getFullYear();
	var newMonth = (newDate.getMonth()+1)<10?("0"+(newDate.getMonth()+1)):(newDate.getMonth()+1);
	var newDay = newDate.getDate()<10?("0"+newDate.getDate()):newDate.getDate();
	
	var newFromtTime = newYear +"-"+ newMonth +"-"+ newDay;
	
    return newFromtTime;  
}

//弹出新增虚机管理账户window
function popAddHostUserWindow(){
	$("#add-host-user-resVmSid").val(resHostSid);
	// 清除输入
	$("#add-host-user-name").val("");
	$("#add-host-user-password").val("");
	$("#add-host-user-userGroup").val("");
	$("#add-host-user-privilege").val("");
	$("#add-host-user-description").val("");
	
	$("#add-type-host-user").val("RES-HOST");
	
	var windowW = $(window).width(); 
	var windowH = $(window).height(); 
	$("#addHostUserWindow").jqxWindow({ position: { x: (windowW-450)/2, y: (windowH-170)/2 } });
	$("#addHostUserWindow").jqxWindow('open');
}

//查询
function setHostMonitorPerid(){
	var timeType = $("#searchMonitorInfo").val();
	
    if(monitorNodeId != null  && monitorNodeId != "" && monitorNodeId != "null"){
    	$("#monitorContent .customPanel").css("display","none");
    	$("#monitorContent .customPanel").eq(0).css("display","block");
    	var hostInfo = new hostInfoModel();
    	if("Day" == timeType){
    		var peroid = getThePeridTime(1);
    		hostInfo.initHostCpuUsedRate(monitorNodeId,peroid,timeType);
    	}else if("Week" == timeType){
    		var peroid = getThePeridTime(7);
    		hostInfo.initHostCpuUsedRate(monitorNodeId,peroid,timeType);
    	}else if("Month" == timeType){
    		var peroid = getThePeridTime(30);
    		hostInfo.initHostCpuUsedRate(monitorNodeId,peroid,timeType);
    	}
    	
    }
}

//提交新增vm管理账户
function submitHostUserInfo(){
	$('#addHostUserForm').jqxValidator('validate');
}

//弹出编辑虚机管理账户window
function popEditHostUserWindow(){
	
	var rowindex = $('#hostUserDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#hostUserDatagrid').jqxGrid('getrowdata', rowindex);
	   	$("#edit-host-user-sid").val(data.osUserSid);
		// 清除输入
		$("#edit-host-user-name").val(data.userName);
		$("#edit-host-user-password").val(data.password);
		
		$("#edit-host-user-userGroup").val(data.userGroup);
    	$("#edit-host-user-privilege").val(data.privilege);
    	$("#edit-host-user-description").val(data.description);
		
		var windowW = $(window).width(); 
		var windowH = $(window).height(); 
		$("#editHostUserWindow").jqxWindow({ position: { x: (windowW-450)/2, y: (windowH-170)/2 } });
		$("#editHostUserWindow").jqxWindow('open');
   	}
}

//提交编辑vm管理账户
function submitEditHostUserInfo(){
	$('#editHostUserForm').jqxValidator('validate');
}

// 删除vm账户
function removeHostUserInfo(){
	var rowindex = $('#hostUserDatagrid').jqxGrid('getselectedrowindex');
   	if(rowindex >= 0){
	   	var data = $('#hostUserDatagrid').jqxGrid('getrowdata', rowindex);
	   	Core.confirm({
			title:"提示",
			message:"确定要删该虚机账户吗？",
			confirmCallback:function(){
				Core.AjaxRequest({
					url : ws_url + "/rest/osUsers/delete/"+data.osUserSid,
					type:"get",
					callback : function (data) {
						 // 关闭window
						 var info = new hostInfoModel(resHostSid);
						 info.initHostUserDatagrid();
						 info.searchHostUserInfo();
				    }
			    });
			}
		});
   	}
}

function popSearchHostInfo(){
	Core.AjaxRequest({
		url : ws_url + "/rest/host/",
		type:'post',
		params:{
			resHostSid:resHostSid
		},
		async:false,
		callback : function (rowData){
			if(null==rowData[0].isViosFlag){
			       var windowW = $(window).width();
			       var windowH = $(window).height(); 
			       $("#x86ServerWindows").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-700)/2 } });
			       $("#x86ServerWindows").jqxWindow('open');
			   }else{
			       var windowW = $(window).width();
			       var windowH = $(window).height(); 
			       $("#ibmServerWindows").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-700)/2 } });
			       $("#ibmServerWindows").jqxWindow('open');
			   }
			    var serverDetail = new serverinfoDetailModel();
			    serverDetail.popServerDetailInfoWindow(rowData[0]);
	    }
    });
}


