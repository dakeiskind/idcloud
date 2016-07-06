define(['app-utils/httpService',
        'app-utils/variableService',
        'app-utils/jqx/window',
        'app-utils/messageBoxService',
        "data/menus",'cookie','base64'], function(http,variable,windows,messageBox,data) {
	
    var header = avalon.define({ 
        $id: "header",
        accountName:variable.currentUser.realName,
        selected:"user.home",
        goRouter:function(url){
            avalon.router.go(url);
        },
        containerData:[
        	{name:"用户中心",type:"user",icon:"icon-users",url:"user.home"},
        	{name:"控制中心",type:"console",icon:"icon-desktop",url:"console.home"},
            {name:"云应用中心",type:"app",icon:"icon-desktop",url:"app.cloudapp-subscription"}
        ],
        data:{
            toDoAll:0,
            toDoMessage:null
        },
        myWorkOrder:function(){
            avalon.router.go("user.service-issue");
        },
        submitOrder:function(){
            windows.initWindow({
                title: "提交工单",
                url: variable.app_modules+"/user-center/customerSer/views/gd-add.html",
                btn: ['保存', '取消'],
                area: ['500px', '520px'],
                confirm:function(){
                    if($("#gdFrom").valid()){
                        messageBox.msgNotification({
                            type:"success",
                            message:"提交工单成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                }
            });
        },
        myNews:function(){
            avalon.router.go("user.service-notifications");
        },
        editMyInfo:function(){
            avalon.router.go("user.account-info");
        },
        editMyPassword:function(){
            avalon.router.go("user.account-secure");
        },
        loginout:function(){
            // 清除cookies
            $.removeCookie('IDC_TOKEN',{path:"/"});
            $.removeCookie('USER_SID',{path:"/"});

            // 跳转到首页
            var preUrl = $.base64.btoa(window.location.href);
            window.location.href = "http://"+window.location.host+"/login.html?callback="+preUrl;
        },
        collapsedLeftNavbar:function(){
            // 判断当前leftNav是收缩的还是张开的
            var width = $("#left-nav").css("width");
            if(header.collapsed){
                header.collapsed = false;
                $('#sidebar').removeClass("mini-menu");
                $("#left-nav").css("width",'220px');
                $("#right-mainer").delay("slow").css({'left':'+=170px','width':'-=170px'});   
                
            }else {
                header.collapsed = true;
                $('#sidebar').addClass("mini-menu");
                $("#left-nav").css("width",'50px');
                $("#right-mainer").delay("slow").css({"left":'-=170px',"width":'+=170px'}); 
                
            }
        }
        
    });

    var params = {
        readFlag:'02'
    };

    http.AjaxRequest({
        url : "/rest/message/getCountByCriteria",
        type:"POST",
        params:params,
        async:false,
        callback : function (data) {
            header.data.toDoMessage = data.toDoMessage;
        }
    });

    header.data.toDoAll += parseInt(header.data.toDoMessage);

    avalon.scan();
});
