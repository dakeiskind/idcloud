define(["data/menus","app-framework/services/frameworkService"], function(data,service) {
	
	var appContainer = avalon.define({
		$id:'appContainer',

		navSelectedFlag:"",

		secondNavFlag:"",

		navLeft:service.getUserModules("app"),
		
		switchAppCenterRouter:function(url){
			appContainer.navSelectedFlag = url;
			avalon.router.go(url);
		}
		
	});

	return avalon.controller(function ($ctrl) {
		  	
		    $ctrl.$onEnter = function (param, rs, rj) {

		    	// 进入视图
		    	avalon.vmodels.header.selected = "app.cloudapp-subscription";
		    };
	    
		    $ctrl.$onRendered = function () {
		    	// 视图渲染后，意思是avalon.scan完成
		    	service.initialization();
			};
	 
		    $ctrl.$onBeforeUnload = function () {
		    	 // 视图销毁前
		    };
	    	$ctrl.$vmodels = [appContainer];
   		});
	
});
