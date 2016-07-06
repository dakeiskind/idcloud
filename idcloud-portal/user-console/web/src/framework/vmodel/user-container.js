define(["data/menus","app-framework/services/frameworkService"], function(data,service) {
	
	var userContainer = avalon.define({
		$id:'userContainer',

		navSelectedFlag:"",

		secondNavFlag:"",

		navLeft:service.getUserModules("user"),
		
		switchUserCenterRouter:function(url){
			userContainer.navSelectedFlag = url;
			avalon.router.go(url);
		}
		
	});

	return avalon.controller(function ($ctrl) {
		  	
		    $ctrl.$onEnter = function (param, rs, rj) {

		    	// 进入视图
		    	avalon.vmodels.header.selected = "user.home";
		    };
	    
		    $ctrl.$onRendered = function () {
		    	// 视图渲染后，意思是avalon.scan完成
		    	service.initialization();
			};
	 
		    $ctrl.$onBeforeUnload = function () {
		    	 // 视图销毁前
		    };
	    	$ctrl.$vmodels = [userContainer];
   		});
	
});
