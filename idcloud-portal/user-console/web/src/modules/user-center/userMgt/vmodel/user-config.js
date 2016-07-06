define(['lib/jquery/pintuer','app-utils/messageBoxService'], function(pintuer,messageBox) {

    var userConfig = avalon.define({
        $id:'userConfig',
        title:"提示设置",
        email_remind: null,
        phone_remind: null,
        emailRemindYes: function(obj){
            userConfig.email_remind = $(obj).find("input").val();
            $(obj).next().removeClass("text-red");
            $(obj).addClass("text-blue");
        },
        emailRemindNo: function(obj){
            userConfig.email_remind = $(obj).find("input").val();
            $(obj).prev().removeClass("text-blue");
            $(obj).addClass("text-red");
        },
        phoneRemindYes: function(obj){
            userConfig.phone_remind = $(obj).find("input").val();
            $(obj).next().removeClass("text-red");
            $(obj).addClass("text-blue");
        },
        phoneRemindNo: function(obj){
            userConfig.phone_remind = $(obj).find("input").val();
            $(obj).prev().removeClass("text-blue");
            $(obj).addClass("text-red");
        },
        btn_submit:function(){
            userConfig.email_remind==null ? userConfig.email_remind="yes":userConfig.email_remind=userConfig.email_remind;
            userConfig.phone_remind==null ? userConfig.phone_remind="yes":userConfig.phone_remind=userConfig.phone_remind;
            messageBox.msgNotification({
                type:"success",
                message:"设置成功：邮件提醒:"+userConfig.email_remind+" 短信提醒:"+userConfig.phone_remind
            });
        }
    });

    return avalon.controller(function ($ctrl) {

        $ctrl.$onEnter = function (param, rs, rj) {
            // 进入视图
            avalon.vmodels.userContainer.secondNavFlag = "account"
            avalon.vmodels.userContainer.navSelectedFlag = 'user.account-msgConfig';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };
        $ctrl.$vmodels = [userConfig];
    });

});