define(['lib/jquery/pintuer',"app-utils/$extendService",'app-utils/jqx/shoppingServer',
        'app-utils/validatorService',
        'app-utils/variableService',
        "lib/jquery/jquery.bgiframe.min","lib/jquery/jquery.multiSelect"
        ], function(pinter) {
	
	var testShoppingInfo = avalon.define({
		$id:'testShoppingId',
		del: function(divname){
			$('#testShopping').removeShopping(divname);//移除一个列表
		},
		initShopping: function(){
			//购物车列表
			var shopListJson='[{"id":"div1","title":"ECS","count":"1个月*1台","data":[{"name":"地域","value":"深圳"},{"name":"可用地区","value":"深圳"},'+
			'{"name":"容量","value":"5GB"},{"name":"配置费用","value":"￥0.002/时","style":"color:red"}],"delFunction":"del(\'div1\')"},'+
			'{"id":"div2","title":"ECS","count":"1个月*1台","data":[{"name":"地域","value":"深圳"},{"name":"可用地区","value":"深圳"},'+
			'{"name":"容量","value":"5GB"},{"name":"配置费用","value":"￥0.002/时"}],"delFunction":"del(\'div2\')"}]';
			$('#testShopping').showShopping(shopListJson);
		}
	});

	return avalon.controller(function ($ctrl) {
		  	
		    $ctrl.$onEnter = function (param, rs, rj) {
		    	// 进入视图
		    	avalon.vmodels.userContainer.navSelectedFlag = 'user.demo-shopping';
		    };
	    
		    $ctrl.$onRendered = function () {
		    	
		    	pinter.init();
		    	testShoppingInfo.initShopping();
		    	avalon.scan(0,testShoppingInfo);
		    	$('#example-getting-started').multiSelect();
		    };
	 
		    $ctrl.$onBeforeUnload = function () {
		    	 // 视图销毁前
		    };
	    	$ctrl.$vmodels = [testShoppingInfo];
   		});
	
});