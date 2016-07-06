define(['app-utils/jqx/window',
	    'app-utils/httpService',
	    'app-utils/variableService'], function(window,http,variable) {
	
	var httpWindow = avalon.define({
		$id:'httpWindow',
		http:function(){
			http.AjaxRequest({
				url:"/rest/user/platform/findMoudules/100",
				type:"get",
				callback:function(data){
					alert(JSON.stringify(data));
				}
			})
		}
		
	});

	return avalon.controller(function ($ctrl) {
		  	
		    $ctrl.$onEnter = function (param, rs, rj) {
		    	// 进入视图
		    	avalon.vmodels.userContainer.navSelectedFlag = 'user.demo-http';
		    };
	    
		    $ctrl.$onRendered = function () {
		    	

		    };
	 
		    $ctrl.$onBeforeUnload = function () {
		    	 // 视图销毁前
		    };
	    	$ctrl.$vmodels = [httpWindow];
   		});
	
});