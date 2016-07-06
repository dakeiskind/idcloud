
define(['datepicker'], function() {
	
	var demoDatepicker = avalon.define({
		$id:'demoDatepicker',
	});

	return avalon.controller(function ($ctrl) {
		  	
		    $ctrl.$onEnter = function (param, rs, rj) {
		    	// 进入视图
		    	avalon.vmodels.userContainer.navSelectedFlag = 'user.demo-datepicker';
		    };
	    
		    $ctrl.$onRendered = function () {
		    	$('#pickerType1').datepicker({
					format: 'yyyy-mm-dd ',
					autoclose: true
				});
				$('#pickerType2').datepicker({});
				$('#pickerType3').datepicker({});

				$('#granularity1').datepicker({});
				$('#granularity1').datepicker({startView: 1});
				$('#granularity3').datepicker({startView: 2});

		    };
	 
		    $ctrl.$onBeforeUnload = function () {
		    	 // 视图销毁前
		    };
	    	$ctrl.$vmodels = [demoDatepicker];
   		});
	
});