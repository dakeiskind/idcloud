//第一块，配置
require.config({
    baseUrl: '',
    paths: {
        // 文件
        "jquery": 'lib/jquery/jquery-1.11.1.min',
        "datepicker":"lib/jquery/bootstrap-datepicker",
        "slider":"lib/jquery/ion.rangeSlider.min",
        "layer":"lib/layer/layer",
        "toastr":"lib/jquery/toastr.min",
        "validator":"lib/jquery/jquery.validate",
        "avalon": 'lib/avalon/avalon.shim',
        "echarts":'lib/echarts',
        "flot":'lib/jquery/jquery.flot.min',
        "webuploader":'lib/webuploader/webuploader.min',
        "d3":'lib/d3/d3.min',
        "cookie":'lib/jquery/js.cookie',
        "base64":'lib/jquery/jquery.base64',
        "jquery.ui.widget":'lib/jquery/jquery.ui.widget',
        "jquery-fileupload":'lib/jquery/jquery.fileupload',
        "jquery-transport":'lib/jquery/jquery.iframe-transport',

        // 文件夹别名
        "data" : "data",
        "i18n" : "i18n",
        "lib" : "lib",
        "app-framework" : "src/framework",
        "app-modules" : "src/modules",
        "app-utils" : "src/services"
       
    },
    urlArgs: "bust=" +  (new Date()).getTime(), // 防止requireJs缓存
    shim: {
        "jquery": {exports: "jQuery"},
        "avalon": {exports: "avalon"},
        "jquery-fileupload":{
            deps: ['jquery.ui.widget'],
            exports: "jquery-fileupload"
        },
        "datepicker":{
            deps: ['jquery'],
            exports: "datepicker"
        },
        "slider":{
            deps: ['jquery'],
            exports: "slider"
        },
        "layer":{
            deps: ['jquery'],
            exports: "layer"
        },
        "toastr":{
            deps: ['jquery'],
            exports: "toastr"
        },
        "validator":{
            deps: ['jquery'],
            exports: "validator"
        },
        "flot":{
            deps: ['jquery'],
            exports: "flot"
        },
        "webuploader":{
            deps:['jquery'],
            exports:"webuploader"
        },
        "d3":{
            exports:"d3"
        },
        "cookie":{
            deps:['jquery'],
            exports:"cookie"
        },
        "base64":{
            deps:['jquery'],
            exports:"base64"
        }
    }
});

// 第二块，添加根VM（处理共用部分）
require(['jquery','avalon','app-utils/variableService','app-framework/routers/frameworkRouterConfig'], function($,avalon,variale,config) { 
	    
		// 定义一个顶层的vmodel
	    var root = avalon.define({
	        $id: "root",
	        header: variale.app_framework + "/views/header.html"
	    });

        config.initRouter();

        avalon.history.start();
        var lastPath = avalon.router.getLastPath();
        if(lastPath == null || lastPath == ""){
            avalon.router.navigate("user/home");
        }else{
            avalon.router.navigate(lastPath);
        }
	    avalon.scan(0, root);
	    
});
