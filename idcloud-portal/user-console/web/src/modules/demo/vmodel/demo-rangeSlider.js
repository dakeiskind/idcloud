
define(['slider'], function() {
	
	var slider = avalon.define({
		$id:'slider',
		value1:0,
		value2:1000,
		value3:4000
	});

	return avalon.controller(function ($ctrl) {
		  	
		    $ctrl.$onEnter = function (param, rs, rj) {
		    	// 进入视图
		    	avalon.vmodels.userContainer.navSelectedFlag = 'user.demo-slider';
		    };
	    
		    $ctrl.$onRendered = function () {
			    $("#range1").ionRangeSlider({
		    		grid: true,
				    min: 0,
				    max: 500,
				    from: 0,
				    step: 10,
				    grid_num: 10,
				    prettify_enabled: false,

				    onChange: function (data) {
				    	slider.value1 = data.from;
				    }
				    
		    	});

		        $("#range2").ionRangeSlider({
		            hide_min_max: true,
		            keyboard: true,
		            min: 0,
		            max: 5000,
		            from: 1000,
		            to: 4000,
		            type: 'double',
		            step: 1,
		            prefix: "$",
		            grid: true,
		            onChange: function (data) {
				    	slider.value2 = data.from;
				    	slider.value3 = data.to;
				       
				    }
		        });

		    };
	 
		    $ctrl.$onBeforeUnload = function () {
		    	 // 视图销毁前
		    };
	    	$ctrl.$vmodels = [slider];
   		});
	
});