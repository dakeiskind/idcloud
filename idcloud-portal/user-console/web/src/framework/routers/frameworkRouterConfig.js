define(["avalon",'app-utils/variableService','data/menus',"lib/avalon/mmState"],function(avalon,variable,data) {
	
	var config = function(){
		
		this.initRouter = function(){
			var me = this;

			var router = data.routerData;
			if(router != null && router.length > 0){
				// 注册一级路由
				for(var i=0;i<router.length;i++){
					if(router[i].parentRouter == null || router[i].parentRouter == ""){
						if(router[i].tempVmodel != "" && router[i].tempVmodel != null){
							// 存在view控制器的提供者
							var provider = function(params){
			                	return new Promise(function(rs, rj) { 
			                        require([variable.app_framework+"/"+ router[i].tempVmodel], function($ctrl) { 
			                            rs($ctrl);
			                        });
			                    });
							}
							
							avalon.state(router[i].name, {
						        url: "/"+router[i].name,
						        views: {
						            "content": {                    	
						            	templateUrl: variable.app_framework+"/"+ router[i].tempHtml,
						                controllerProvider: provider()
						            }
						        }
						    });
						}else{
							avalon.state(router[i].name, {
						        url: "/"+router[i].name,
						        views: {
						            "content": {                    	
						            	templateUrl: variable.app_modules+"/"+ router[i].tempHtml
						            }
						        }
						    });
						}
					}
				}

				// 注册二级路由
				for(var i=0;i<router.length;i++){
					// 用户中心二级路由
					if(router[i].parentRouter == 'user'){
						var url = router[i].name.split(".");
						url[1] = url[1].replace(/-/g, "/");
						if(router[i].tempVmodel != "" && router[i].tempVmodel != null){

							// 存在view控制器的提供者
							var provider = function(params){
			                	return new Promise(function(rs, rj) { 
			                        require([variable.app_modules+"/"+ router[i].tempVmodel], function($ctrl) { 
			                            rs($ctrl);
			                        });
			                    });
							}
							
							avalon.state(router[i].name, {
						        url: "/"+url[1],
						        views: {
						            "user-container": {                    	
						            	templateUrl: variable.app_modules+"/"+ router[i].tempHtml,
						                controllerProvider: provider()
						            }
						        }
						    });
						}else{
							avalon.state(router[i].name, {
						        url: "/"+url[1],
						        views: {
						            "user-container": {                    	
						            	templateUrl: variable.app_modules+"/"+ router[i].tempHtml
						            }
						        }
						    });
						}
					}else if(router[i].parentRouter == 'console'){
						var url = router[i].name.split(".");
						url[1] = url[1].replace(/-/g, "/");
						// 控制中心二级路由
						if(router[i].tempVmodel != "" && router[i].tempVmodel != null){
							// 存在view控制器的提供者
							var provider = function(params){
			                	return new Promise(function(rs, rj) { 
			                        require([variable.app_modules+"/"+ router[i].tempVmodel], function($ctrl) { 
			                            rs($ctrl);
			                        });
			                    });
							}
							
							avalon.state(router[i].name, {
						        url: "/"+url[1],
						        views: {
						            "service-container": {                    	
						            	templateUrl: variable.app_modules+"/"+ router[i].tempHtml,
						                controllerProvider: provider()
						            }
						        }
						    });
						}else{
							avalon.state(router[i].name, {
						        url: "/"+url[1],
						        views: {
						            "service-container": {                    	
						            	templateUrl: variable.app_modules+"/"+ router[i].tempHtml
						            }
						        }
						    });
						}

					}else if(router[i].parentRouter == 'app'){
						var url = router[i].name.split(".");
						url[1] = url[1].replace(/-/g, "/");
						// 控制中心二级路由
						if(router[i].tempVmodel != "" && router[i].tempVmodel != null){
							// 存在view控制器的提供者
							var provider = function(params){
								return new Promise(function(rs, rj) {
									require([variable.app_modules+"/"+ router[i].tempVmodel], function($ctrl) {
										rs($ctrl);
									});
								});
							}

							avalon.state(router[i].name, {
								url: "/"+url[1],
								views: {
									"app-container": {
										templateUrl: variable.app_modules+"/"+ router[i].tempHtml,
										controllerProvider: provider()
									}
								}
							});
						}else{
							avalon.state(router[i].name, {
								url: "/"+url[1],
								views: {
									"app-container": {
										templateUrl: variable.app_modules+"/"+ router[i].tempHtml
									}
								}
							});
						}

					}
				}
			}

		};
	};
	
	return new config();
});