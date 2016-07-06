define(['jquery','app-utils/httpService','cookie','app-utils/$extendService'],function($,http) {
	var service = function() {
		var me = this;
        this.collapsed = false;
        this.modulesData = null;

        this.initialization = function(){
            me.calculateContainerSize();
            // 监听浏览器窗口大小变化，
            window.onresize=function(){  
                me.calculateContainerSize();  
            }
            me.initAccordion();
        };

        // 获取用户中心权限
        this.getUserModules = function(type){
            var returnData = [];
            http.AjaxRequest({
                url : "/rest/user/previlege/"+ $.cookie("USER_SID"),
                type : "GET",
                async : false,
                callback : function(data) {
                    if (typeof data != "undefined" && null != data) {
                        me.modulesData = data;
                    }
                }
            });

            if(me.modulesData != null && me.modulesData.length > 0){
                var data = me.modulesData;
                if(type == 'user'){
                    for(var i=0;i<data.length;i++){
                        if(data[i].parentSid == 0 || data[i].parentSid == null){
                            for(var j=0;j<data.length;j++){
                                if(data[i].moduleSid == data[j].parentSid && data[i].moduleSid == 'SA'){
                                    var obj = new Object();
                                    obj.moduleSid = data[j].moduleSid;
                                    obj.moduleName = data[j].moduleName;
                                    obj.moduleUrl = data[j].moduleUrl;
                                    obj.moduleIconUrl = data[j].moduleIconUrl;
                                    obj.secondModules = [];
                                    for(var n=0;n<data.length;n++){
                                        if(data[j].moduleSid == data[n].parentSid){
                                            var subObj = new Object();
                                            subObj.moduleSid = data[n].moduleSid;
                                            subObj.moduleName = data[n].moduleName;
                                            subObj.moduleUrl = data[n].moduleUrl;
                                            subObj.moduleIconUrl = data[n].moduleIconUrl;
                                            obj.secondModules.push(subObj);
                                        }
                                    }
                                    returnData.push(obj);
                                }
                            }
                        }
                    }
                }else if(type == 'console'){
                    for(var i=0;i<data.length;i++){
                        if(data[i].parentSid == 0 || data[i].parentSid == null){
                            for(var j=0;j<data.length;j++){
                                if(data[i].moduleSid == data[j].parentSid && data[i].moduleSid == 'SB'){
                                    var obj = new Object();
                                    obj.moduleSid = data[j].moduleSid;
                                    obj.moduleName = data[j].moduleName;
                                    obj.moduleUrl = data[j].moduleUrl;
                                    obj.moduleIconUrl = data[j].moduleIconUrl;
                                    obj.secondModules = [];
                                    for(var n=0;n<data.length;n++){
                                        if(data[j].moduleSid == data[n].parentSid){
                                            var subObj = new Object();
                                            subObj.moduleSid = data[n].moduleSid;
                                            subObj.moduleName = data[n].moduleName;
                                            subObj.moduleUrl = data[n].moduleUrl;
                                            subObj.moduleIconUrl = data[n].moduleIconUrl;
                                            obj.secondModules.push(subObj);
                                        }
                                    }
                                    returnData.push(obj);
                                }
                            }
                        }
                    }
                }else if(type == 'app'){
                    for(var i=0;i<data.length;i++){
                        if(data[i].parentSid == 0 || data[i].parentSid == null){
                            for(var j=0;j<data.length;j++){
                                if(data[i].moduleSid == data[j].parentSid && data[i].moduleSid == 'SC'){
                                    var obj = new Object();
                                    obj.moduleSid = data[j].moduleSid;
                                    obj.moduleName = data[j].moduleName;
                                    obj.moduleUrl = data[j].moduleUrl;
                                    obj.moduleIconUrl = data[j].moduleIconUrl;
                                    obj.secondModules = [];
                                    for(var n=0;n<data.length;n++){
                                        if(data[j].moduleSid == data[n].parentSid){
                                            var subObj = new Object();
                                            subObj.moduleSid = data[n].moduleSid;
                                            subObj.moduleName = data[n].moduleName;
                                            subObj.moduleUrl = data[n].moduleUrl;
                                            subObj.moduleIconUrl = data[n].moduleIconUrl;
                                            obj.secondModules.push(subObj);
                                        }
                                    }
                                    returnData.push(obj);
                                }
                            }
                        }
                    }
                }
            }
            return returnData;

        };

        // 初始化手风琴
        this.initAccordion = function(){
            $('.sidebar-menu .has-sub > a').click(function () {
                var last = $('.has-sub.open', $('.sidebar-menu'));
                last.removeClass("open");
                $('.sidebar-arrow', last).removeClass("open");
                $('.sub', last).slideUp(200);
                
                var sub = $(this).next();
                if (sub.is(":visible")) {
                    $('.sidebar-arrow', $(this)).removeClass("open");
                    $(this).parent().removeClass("open");
                    sub.slideUp(200);
                } else {
                    $('.sidebar-arrow', $(this)).addClass("open");
                    $(this).parent().addClass("open");
                    sub.slideDown(200);
                }
            });
        
            // 子菜单
            $('.sidebar-menu .has-sub .sub .has-sub-sub > a').click(function () {
                var last = $('.has-sub-sub.open', $('.sidebar-menu'));
                last.removeClass("open");
                $('.sidebar-arrow', last).removeClass("open");
                $('.sub', last).slideUp(200);
                    
                var sub = $(this).next();
                if (sub.is(":visible")) {
                    $('.sidebar-arrow', jQuery(this)).removeClass("open");
                    $(this).parent().removeClass("open");
                } else {
                    $('.sidebar-arrow', $(this)).addClass("open");
                    $(this).parent().addClass("open");
                }
            });
        };

        // 计算Container容器大小
		this.calculateContainerSize = function(){
            var dh = document.documentElement.clientHeight;
            var dw = document.documentElement.clientWidth;
            document.getElementById("container").style.height=(dh-42)+"px";
            document.getElementById("right-mainer").style.width=(dw-218)+"px";
            document.getElementById("right-mainer").style.height=(dh-62)+"px";
        };

        // 左边导航栏切换动画
        this.switchSidebarStyleAnimation = function(){
            $('#sidebar-collapse').click(function () {
                
                if(me.collapsed){
                    
                    $('.navbar-brand').removeClass("mini-menu");
                    $('#sidebar').removeClass("mini-menu");

                    me.collapsed = false;

                    var left =  $("#right-mainer").css("left");
                    $("#left-nav").css("width",'220px');
                    $("#right-mainer").delay("slow").css({'left':'+=170px','width':'-=170px'});   
                    
                }else {
                    
                    $('.navbar-brand').addClass("mini-menu");
                    $('#sidebar').addClass("mini-menu");

                    me.collapsed = true;

                    $("#left-nav").css("width",'50px');
                    $("#right-mainer").delay("slow").css({"left":'-=170px',"width":'+=170px'}); 
                    
                }
            });
        };
	};
	return new service();
});