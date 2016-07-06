define(["lib/jquery/jquery.multiSelect"], function(window,messageBox,variable) {
	
	var dropdownWindow = avalon.define({
		$id:'dropdownWindow',
		getSelectValue: function(){
			var selectValue = $('#netName').selectedValuesString();
			$("#selectValue").text(selectValue);
		},
		demoData:[
			{name:"test1",value:"1"},
			{name:"test2",value:"2"},
			{name:"test3",value:"3"}
		]
		
	});

	return avalon.controller(function ($ctrl) {
		  	
		    $ctrl.$onEnter = function (param, rs, rj) {
		    	// 进入视图
		    	avalon.vmodels.userContainer.navSelectedFlag = 'user.demo-dropdown';
		    };
	    
		    $ctrl.$onRendered = function () {

				$('#netName').multiSelect();

		    };
	 
		    $ctrl.$onBeforeUnload = function () {
		    	 // 视图销毁前
		    };
	    	$ctrl.$vmodels = [dropdownWindow];
   		});
	
});