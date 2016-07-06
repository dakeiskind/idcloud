define(['app-modules/user-center/dashboard/service/dashboard'
        ,"lib/jquery/pintuer"
        ,'echarts'
        ,'echarts/chart/pie'], function (dashboardService,pintuer,ec) {
    var dashbaord = avalon.define({
        $id: 'user-dashbaord',
        data:null,
        logData:null,
        ConsumeData:null,
        dayBalance:null,
        monthBalance:null,
        chartDate:null,
        //ConsumeData:{
        //    dayConsumeAll:0.00,
        //    dayConsumeHost:0.00,
        //    dayConsumePNetworkIP:0.00,
        //    dayConsumeDisk:0.00,
        //    dayConsumeObject:0.00,
        //    dayConsumeVPC:0.00,
        //    monthConsumeAll:0.00,
        //    monthConsumeHost:0.00,
        //    monthConsumePNetworkIP:0.00,
        //    monthConsumeDisk:0.00,
        //    monthConsumeObject:0.00,
        //    monthConsumeVPC:0.00
        //
        //},
        cz:function(){
            avalon.router.go("user.expense-recharge");
        },
        myBill:function(){
            avalon.router.go("user.expense-consumption");
        },
        gotoMyinfo:function(){
            avalon.router.go("user.account-info");
        },
        toCharge:function(){
            avalon.router.go("user.expense-renew");
        },
        toPaidOrder:function(){
            avalon.router.go("user.expense-order");
        },
        toProcessedIssue:function(){
            avalon.router.go("user.service-issue");
        },
        onChartChange:function(){
           var chartDate = dashboardService.getCharDate();
            dashbaord.chartDate = chartDate;
            dashbaord.initTotalConsumeCharts();
        },
        initDayConsumeCharts:function(){
            dashbaord.dayBalance.total = parseFloat(dashbaord.dayBalance.total).toFixed(2);
            var myChart = ec.init(document.getElementById('dayConsumeCharts'));
            var option = {
                title:{
                    y:15,
                    text:"日总额:"+dashbaord.dayBalance.total+"元",
                    textStyle:{
                        fontSize: 18,
                        fontWeight: 'bolder',
                        fontFamily: "Microsoft YaHei",
                        color: "#EA5200"
                    }
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} (元)"
                },
                legend: {
                    x : 'left',
                    y : 'center',
                    orient:'vertical',
                    textStyle:{
                        fontFamily: "Microsoft YaHei"
                    },
                    //data:['主机','公网IP','云硬盘','对象存储','VPC']
                    data:['云主机','公网IP','云硬盘']
                },
                toolbox: {
                    show : false,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {
                            show: true, 
                            type: ['pie', 'funnel'],
                            option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'center',
                                    max: 1548
                                }
                            }
                        },
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                series : [
                    {
                        name:'消费金额',
                        type:'pie',
                        center: ['65%', '50%'],
                        radius : ['50%', '70%'],
                        itemStyle : {
                            normal : {
                                label : {
                                    show : false
                                },
                                labelLine : {
                                    show : false
                                }
                            },
                            emphasis : {
                                label : {
                                    show : true,
                                    position : 'center',
                                    textStyle : {
                                        fontSize : '30',
                                        fontWeight : 'bold'
                                    }
                                }
                            }
                        },
                        data:[
                            {value:dashbaord.dayBalance.cs, name:'云主机'},
                            {value:dashbaord.dayBalance.eip, name:'公网IP'},
                            {value:dashbaord.dayBalance.cbs, name:'云硬盘'}
                            //{value:dashbaord.dayBalance.oss, name:'对象存储'},
                            //{value:dashbaord.dayBalance.vpc, name:'VPC'}
                        ]
                    }
                ]
            }
            myChart.setOption(option); 
            window.setTimeout(myChart.resize,500);
         },
        initMonthConsumeCharts:function(){
            dashbaord.monthBalance.total = parseFloat(dashbaord.monthBalance.total).toFixed(2);
            var myChart = ec.init(document.getElementById('monthConsumeCharts')); 
            var option = {
                title:{
                    y:15,
                    text:"月总额:"+dashbaord.monthBalance.total+"元",
                    textStyle:{
                        fontSize: 18,
                        fontWeight: 'bolder',
                        fontFamily: "Microsoft YaHei",
                        color: "#EA5200"
                    }
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} (元)"
                },
                legend: {
                    x : 'left',
                    y : 'center',
                    textStyle:{
                        fontFamily: "Microsoft YaHei"
                    },
                    orient:'vertical',
                    //data:['云主机','公网IP','云硬盘','对象存储','VPC']
                    data:['云主机','公网IP','云硬盘']
                },
                toolbox: {
                    show : false,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {
                            show: true, 
                            type: ['pie', 'funnel'],
                            option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'center',
                                    max: 1548
                                }
                            }
                        },
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                series : [
                    {
                        name:'消费金额',
                        type:'pie',
                        center: ['65%', '50%'],
                        radius : ['50%', '70%'],
                        itemStyle : {
                            normal : {
                                label : {
                                    show : false
                                },
                                labelLine : {
                                    show : false
                                }
                            },
                            emphasis : {
                                label : {
                                    show : true,
                                    position : 'center',
                                    textStyle : {
                                        fontSize : '30',
                                        fontWeight : 'bold'
                                    }
                                }
                            }
                        },
                        data:[
                            {value:dashbaord.monthBalance.cs, name:'云主机'},
                            {value:dashbaord.monthBalance.eip, name:'公网IP'},
                            {value:dashbaord.monthBalance.cbs, name:'云硬盘'}
                            //{value:dashbaord.monthBalance.oss, name:'对象存储'},
                            //{value:dashbaord.monthBalance.vpc, name:'VPC'}
                        ]
                    }
                ]
            }
            myChart.setOption(option); 
            window.setTimeout(myChart.resize,500);
        },
        initTotalConsumeCharts:function(){
            var myChart = ec.init(document.getElementById('totalConsumeCharts'));
            var option = {
                tooltip: {
                    show: true
                },
                grid: {
                    x: 50,
                    y: 40,
                    x2: 10,
                    y2: 30
                },

                xAxis: [
                    {
                        type: 'category',
                        data: dashbaord.chartDate.xAxisData
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        
                        "type": "line",
                        "data": dashbaord.chartDate.seriesData
                    }
                ]
            };
            myChart.setOption(option);
            window.setTimeout(myChart.resize,500);
        },
        initSysTLogRecord:function(){
            var logData = dashbaord.logData;
            for(var i=0;i<logData.length;i++){
                if(i >= 5){
                    $("#vertical-timeline").append(
                        '<div class="vertical-timeline-block" >'+
                        '<div class="vertical-timeline-icon bg-blue">'+
                        '<i class="icon-hand-o-up" style="color: #fff"></i>'+
                        '</div>'+
                        '<div class="vertical-timeline-content">'+
                        '<a href="javascrip:;" id="toLog"  style="color: #0066ff">点击查看更多</a>'+
                        '</div>'+
                        '</div>'
                    )
                    break;
                }
                if(logData[i].actResult == '1'){
                    $("#vertical-timeline").append(
                        '<div class="vertical-timeline-block" >'+
                        '<div class="vertical-timeline-icon bg-blue">'+
                        '<i class="icon-hand-o-up" style="color: #fff"></i>'+
                        '</div>'+
                        '<div class="vertical-timeline-content">'+
                        '<font>'+logData[i].actName+'</font>&nbsp;&nbsp;&nbsp;&nbsp;' +
                        '<font>成功</font>'+
                        '<p>'+logData[i].actDetail+'</p>'+
                        '<span>'+
                        '<small>'+logData[i].actEndDate+'</small>'+
                        '</span>'+
                        '</div>'+
                        '</div>'
                    )
                }else{
                    $("#vertical-timeline").append(
                        '<div class="vertical-timeline-block" >'+
                        '<div class="vertical-timeline-icon bg-blue">'+
                        '<i class="icon-hand-o-up" style="color: #fff"></i>'+
                        '</div>'+
                        '<div class="vertical-timeline-content">'+
                        '<font>'+logData[i].actName+'</font>&nbsp;&nbsp;&nbsp;&nbsp;' +
                        '<font>失败</font>'+
                        '<p>'+logData[i].actFailureReason+'</p>'+
                        '<span>'+
                        '<small>'+logData[i].actEndDate+'</small>'+
                        '</span>'+
                        '</div>'+
                        '</div>'
                    )
                }
            }
        }
    });

    var initData = function(){
        var data = dashboardService.getData();
        var logData = dashboardService.getLogData();
        var chartDate = dashboardService.getCharDate();
        data.user.balance = parseFloat(data.user.balance).toFixed(2)

        dashbaord.data = data;
        dashbaord.logData = logData;
        dashbaord.dayBalance = data.dayBalance;
        dashbaord.monthBalance = data.monthBalance;
        dashbaord.chartDate = chartDate;
    }

    return avalon.controller(function ($ctrl) {

        $ctrl.$onEnter = function (param, rs, rj) {
            // 进入视图
            avalon.vmodels.userContainer.navSelectedFlag = null;
            avalon.vmodels.userContainer.secondNavFlag = "user.home";

        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
            initData();
            dashbaord.initDayConsumeCharts();
            dashbaord.initMonthConsumeCharts();
            dashbaord.initTotalConsumeCharts();
            dashbaord.initSysTLogRecord();
            //$("#mobile").attr("title",variable.currentUser.mobile)

            $("#toLog").on("click",function(){
                avalon.router.go("user.service-log");
            });
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };
        $ctrl.$vmodels = [dashbaord];
    });

});