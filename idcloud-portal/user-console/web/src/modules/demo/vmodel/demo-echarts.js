define(['echarts','echarts/chart/bar'], function(ec) {
	
	var echarts = avalon.define({
		$id:'echartsWindow',
		initCharts:function(){
			var myChart = ec.init(document.getElementById('mainCharts')); 
            var option = {
                tooltip: {
                    show: true
                },
                legend: {
                    data:['销量']
                },
                grid:{
                	x:30,
                	y:20,
                	x2:20,
                	y2:30
                },
                xAxis : [
                    {
                        type : 'category',
                        data : ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        "name":"销量",
                        "type":"bar",
                        "data":[5, 20, 40, 10, 10, 20]
                    }
                ]
            };
            myChart.setOption(option); 
		}
		
	});

	  

	return avalon.controller(function ($ctrl) {
		  	
		    $ctrl.$onEnter = function (param, rs, rj) {
		    	// 进入视图
		    	avalon.vmodels.userContainer.navSelectedFlag = 'user.demo-echarts';
		    };
	    
		    $ctrl.$onRendered = function () {
		    	echarts.initCharts();

		    };
	 
		    $ctrl.$onBeforeUnload = function () {
		    	 // 视图销毁前
		    };
	    	$ctrl.$vmodels = [echarts];
   		});
	
});