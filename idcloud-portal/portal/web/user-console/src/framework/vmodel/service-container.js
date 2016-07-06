define(["data/menus","app-framework/services/frameworkService"], function(data,service) {
	
	var serviceContainer = avalon.define({
		$id:'serviceContainer',

		navSelectedFlag:"",

		navLeft:service.getUserModules("console"),

		secondNavFlag:"",
		
		switchConsoleCenterRouter:function(url){
			serviceContainer.navSelectedFlag = url;
			avalon.router.go(url);
		}
		
	});
	
	return avalon.controller(function ($ctrl) {
		  	
		    $ctrl.$onEnter = function (param, rs, rj) {
		    	// 进入视图
		    	avalon.vmodels.header.selected = "console.home";
		    };
	    
		    $ctrl.$onRendered = function () {
		    	// 视图渲染后，意思是avalon.scan完成
		    	//require(['lib/jquery/pintuer']);
		    	service.initialization();
		    	
		    };
	 
		    $ctrl.$onBeforeUnload = function () {
		    	 // 视图销毁前
		    };
	    	$ctrl.$vmodels = [serviceContainer];
   		});
});
