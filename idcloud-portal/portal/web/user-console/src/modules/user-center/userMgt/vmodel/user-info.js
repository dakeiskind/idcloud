define(['app-utils/codeService',
    'lib/jquery/pintuer',
    'app-utils/messageBoxService',
    'app-utils/httpService',
    'app-utils/variableService',
    'app-modules/user-center/dashboard/service/dashboard'], function (codeService,pintuer,messageBox,http,variable,dashboard) {
    var userInfo = avalon.define({
        $id: 'userInfo',
        title: "用户资料",
        userData:{},
        addressData:{
            tenantType:null,
            tenantName:null,
            Industry:null,
            selProvince:null,
            selCity:null,
            selArea:null,
            addressmsg:null
        },
        toSecure: function () {
            avalon.router.go("user.account-secure");
        },
        toAuth:function(){
            avalon.router.go("user.account-auth");
        },
        provinceSwitch:function(obj){
            var firstCityId = codeService.getCity('selArea','selCity',$(obj).val());
            if(firstCityId != "" && firstCityId != null){
                codeService.getArea('selArea',firstCityId);
            }
        },
        citySwitch:function(obj){
            if($(obj).val() != 100100){
                codeService.getArea('selArea',$(obj).val());
            }
        },
        modify: function () {
            $("#username").removeClass("border-red");
            $("#username").next().remove();
            $("#addressmsg").removeClass("border-red");
            $("#addressmsg").next().remove();
            if(userInfo.userData.realName == "" || userInfo.userData.realName == null){
                $("#username").addClass("border-red");
                $("#username").parent().append('<div class="text-red"><ul  style="list-style-type:none;"><li>必填</li></ul></div>');
                return;
            }
            if(userInfo.userData.realName.length > 16){
                $("#username").addClass("border-red");
                $("#username").parent().append('<div class="text-red"><ul  style="list-style-type:none;"><li>请输入16位长度之内的称呼</li></ul></div>');
                return;
            }

            if(userInfo.addressData.addressmsg == "" || userInfo.addressData.addressmsg == null){
                $("#addressmsg").addClass("border-red");
                $("#addressmsg").parent().append('<div class="text-red"><ul  style="list-style-type:none;"><li>必填</li></ul></div>');
                return;
            }
            if(userInfo.addressData.addressmsg.length > 512){
                $("#addressmsg").addClass("border-red");
                $("#addressmsg").parent().append('<div class="text-red"><ul  style="list-style-type:none;"><li>请输入512位长度之内的地址</li></ul></div>');
                return;
            }
                messageBox.confirm({
                    message: "您确定要修改用户资料吗？",
                    callback: function () {
                        var tenantinfo = userInfo.addressData;
                        var managerinfo = userInfo.userData;
                        tenantinfo.selCity = $("#selCity").val();
                        tenantinfo.selArea = $("#selArea").val();
                        managerinfo.userSid = $.cookie('USER_SID');
                        managerinfo.mgtObjSid = JSON.stringify(variable.currentUser.mgtObjSid);
                        http.AjaxRequest({
                            url: "/rest/user/updateProtalUser",
                            type: "POST",
                            params: {
                                tenant: tenantinfo,
                                user: managerinfo
                            },
                            showMsg:false,
                            async: false,
                            callback: function (data) {
                                if(data){
                                    //avalon.router.go("user.home");
                                    messageBox.msgNotification({
                                        type: "success",
                                        message: "修改成功！"
                                    });
                                }else{
                                    messageBox.msgNotification({
                                        type: "error",
                                        message: "修改失败！"
                                    });
                                }

                            }
                        });
                    }
                });
        }
    });

    var init = function(){
        codeService.setOption('industry','TENANT_INDUSTRY');
        codeService.getProvince('selProvince');
        userInfo.userData = dashboard.getUserData();
        var datas = getUserExt();;
        userInfo.addressData.tenantType = datas[0].attrValue;
        if(userInfo.addressData.tenantType == '个人用户'){
            $("#comn").addClass("hidden");
            userInfo.addressData.Industry = null;
            userInfo.addressData.Industry = datas[1].attrValue;
            userInfo.addressData.selProvince = null;
            userInfo.addressData.selProvince = datas[2].attrValue;
            codeService.getCity('selArea','selCity',$("#selProvince").val());
            userInfo.addressData.selCity = null;
            userInfo.addressData.selCity = datas[3].attrValue;
            codeService.getArea('selArea',$("#selCity").val());
            userInfo.addressData.selArea = null;
            userInfo.addressData.selArea = datas[4].attrValue;
            userInfo.addressData.addressmsg = datas[5].attrValue;
        }else{
            userInfo.addressData.Industry = null;
            userInfo.addressData.tenantName = datas[1].attrValue;
            userInfo.addressData.Industry = datas[2].attrValue;
            userInfo.addressData.selProvince = null;
            userInfo.addressData.selProvince = datas[3].attrValue;
            codeService.getCity('selArea','selCity',$("#selProvince").val());
            userInfo.addressData.selCity = null;
            userInfo.addressData.selCity = datas[4].attrValue;
            userInfo.addressData.selArea = null;
            codeService.getArea('selArea',$("#selCity").val());
            userInfo.addressData.selArea = datas[5].attrValue;
            userInfo.addressData.addressmsg = datas[6].attrValue;
        }
    };
    var InputFocusin = function(){
        $("#username").focusin(function(){
            $("#username").removeClass("border-red");
            $("#username").next().remove();
        })
        $("#addressmsg").focusin(function(){
            $("#addressmsg").removeClass("border-red");
            $("#addressmsg").next().remove();
        })
    };
    var getUserExt = function(){
        var returnData = null;
        http.AjaxRequest({
            url : "/rest/user/current/"+ $.cookie("USER_SID"),
            type : "GET",
            async : false,
            callback : function(data) {
                returnData = data.exts;
            }
        });
        return returnData;
    }

    return avalon.controller(function ($ctrl) {

        $ctrl.$onEnter = function (param, rs, rj) {
            // 进入视图
            avalon.vmodels.userContainer.secondNavFlag = "account";
            avalon.vmodels.userContainer.navSelectedFlag = 'user.account-info';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
            init();
            InputFocusin();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };
        $ctrl.$vmodels = [userInfo];
    });

});

