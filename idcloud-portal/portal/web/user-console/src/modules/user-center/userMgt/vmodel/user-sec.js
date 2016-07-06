define(['lib/jquery/pintuer',
        'app-utils/validatorService',
        'app-utils/variableService',
        "app-modules/user-center/userMgt/services/user-sec",
        'validator'], function (pintuer, validate, variable, userSecService) {

    var userSec = avalon.define({
                                    $id: 'userSec',
                                    title: "帐号安全",
                                    email: variable.currentUser.email,
                                    mobile: variable.currentUser.mobile == "" || null ? "未填写"
                                        : variable.currentUser.mobile,
                                    password: variable.currentUser.password,
                                    change_email_title: "更换登录邮箱",
                                    //更换用户email
                                    edit_email: function () {
                                        $("#user-sec").addClass("hidden");
                                        $("#change_email").removeClass("hidden");
                                        $("#changeEmailForm .step_two").addClass("hidden");
                                        $("#changeEmailForm .step_three").addClass("hidden");
                                        $("#changeEmailForm .step_one").removeClass("hidden");
                                        $("#change_user_email").text($("#user_email").text());

                                        $("#change_email .step div:nth-child(1)")
                                            .addClass("active");
                                        $(".step .active").css("background", "#0099d7");
                                        $(".step .active .step-point").css("background", "#0099d7");
                                        $("#change_email .step div:nth-child(3)")
                                            .removeClass("active");
                                        $("#change_email .step div:nth-child(3)")
                                            .css("background", "");
                                        $("#change_email .step div:nth-child(3)")
                                            .find(".step-point").css("background", "");
                                        $("#change_email .step div:nth-child(2)")
                                            .removeClass("active");
                                        $("#change_email .step div:nth-child(2)")
                                            .css("background", "");
                                        $("#change_email .step div:nth-child(2)")
                                            .find(".step-point").css("background", "");
                                        $("#change_email .step div:nth-child(1)")
                                            .removeClass("complete");
                                        $("#change_email .step div:nth-child(2)")
                                            .removeClass("complete");
                                        $("#change_email .step div:nth-child(1) span:nth-child(1)")
                                            .text("1");
                                        $("#change_email .step div:nth-child(2) span:nth-child(1)")
                                            .text("2");
                                        $("#change_email .step div:nth-child(3) span:nth-child(1)")
                                            .text("3");
                                        $("#change_email .step div:nth-child(1) span:nth-child(1)")
                                            .removeClass("icon-check");
                                        $("#change_email .step div:nth-child(2) span:nth-child(1)")
                                            .removeClass("icon-check");
                                        $("#change_email .step div:nth-child(3) span:nth-child(1)")
                                            .removeClass("icon-check");
                                        $("#change_email .step").next().removeClass("hidden");

                                        $("#change_email .cancel_warning").val("");
                                        $("#change_email .form-group").removeClass("check-error");
                                        $("#change_email .form-group").removeClass("check-success");
                                        $("#change_email .input-help").remove()
                                    },
                                    //保存新邮箱
                                    save_new_email: function () {
                                        //var new_email_check_code =
                                        // $(this).closest("table").find(".check_code").val();
                                        // if(new_email_check_code == ""){
                                        // $(this).closest("tr").prev().find(".input-help").remove();
                                        // $(this).closest("tr").prev().find(".form-group").addClass("check-error");
                                        // $(this).closest("tr").prev().find(".field").append('<div
                                        // class="input-help
                                        // text-red"><ul><li>必填</li><li>请输入6位验证码</li></ul></div>');
                                        // return; }else if(new_email_check_code.length != 6){
                                        // $(this).closest("tr").prev().find(".input-help").remove();
                                        // $(this).closest("tr").prev().find(".form-group").addClass("check-error");
                                        // $(this).closest("tr").prev().find(".field").append('<div
                                        // class="input-help
                                        // text-red"><ul><li>请输入6位验证码</li></ul></div>'); return }
                                        var $second_step = $("#change_email").find(".step")
                                            .children("div").eq(1);
                                        $second_step.removeClass("active");
                                        $second_step.addClass("complete");
                                        $(".step .complete").css("background", "#2c7");
                                        $(".step .complete .step-point").css("background", "#2c7");
                                        $second_step.find(".step-point").text("");
                                        $second_step.find(".step-point").addClass("icon-check");
                                        $second_step.next().addClass("active");
                                        $(".step .active").css("background", "#0099d7");
                                        $(".step .active .step-point").css("background", "#0099d7");
                                        $("#change_email .step_two").addClass("hidden");
                                        $("#change_email .step_three").removeClass("hidden");
                                        $("#new_email_address").text($("#new_email").val());
                                        console.log($("#new_email").val());
                                        obj.email = $("#new_email").val();
                                        console.log(JSON.stringify(obj));
                                        userSecService.edit(obj);
                                    },

                                    change_password: "更换登录密码",
                                    //更换用户密码
                                    modify_pwd: function () {
                                        $("#user-sec").removeClass("show");
                                        $("#user-sec").addClass("hidden");
                                        $("#change_pwd").removeClass("hidden");
                                        $("#change_pwd").addClass("fadein-right");

                                        $("#change_pwd .step_two").addClass("hidden");
                                        $("#change_pwd .step_one").removeClass("hidden");
                                        $("#change_pwd .step_one").addClass("fadein-right");

                                        $("#change_pwd .cancel_warning").val("");
                                        $("#change_pwd .cancel_warning").closest(".field")
                                            .find(".input-help").remove();
                                        $("#change_pwd .cancel_warning").closest(".form-group")
                                            .removeClass("check-error");
                                        $("#change_pwd .cancel_warning").closest(".form-group")
                                            .removeClass("check-success");
                                    },
                                    //保存新密码
                                    save_password: function () {
                                        //var $old_password = $("#old_password");
                                        var $new_password = $("#new_password");
                                        var $again_new_password = $("#again_new_password");
                                        //if ($old_password.val() == "") {
                                        //    $old_password.parent().find(".input-help").remove();
                                        //    $old_password.closest(".form-group")
                                        //        .addClass("check-error");
                                        //    $old_password.parent().append(
                                        //        '<div class="input-help
                                        // text-red"><ul><li>必填</li><li>请输入6~16密码</li></ul></div>');
                                        // return; } else if ($old_password.val().length < 6 ||
                                        // $old_password.val().length > 16) {
                                        // $old_password.parent().find(".input-help").remove();
                                        // $old_password.closest(".form-group")
                                        // .addClass("check-error"); $old_password.parent().append(
                                        // '<div class="input-help
                                        // text-red"><ul><li>请输入6~16密码</li></ul></div>'); return; }

                                        if ($new_password.val() == "") {
                                            $new_password.parent().find(".input-help").remove();
                                            $new_password.closest(".form-group")
                                                .addClass("check-error");
                                            $new_password.parent().append(
                                                '<div class="input-help text-red"><ul><li>必填</li><li>请输入6~16密码</li></ul></div>');
                                            return;
                                        } else if ($new_password.val().length < 6) {
                                            $new_password.parent().find(".input-help").remove();
                                            $new_password.closest(".form-group")
                                                .addClass("check-error");
                                            $new_password.parent().append(
                                                '<div class="input-help text-red"><ul><li>请输入6~16密码</li></ul></div>');
                                            return;
                                        }

                                        if ($again_new_password.val() == "") {
                                            $again_new_password.parent().find(".input-help")
                                                .remove();
                                            $again_new_password.closest(".form-group")
                                                .addClass("check-error");
                                            $again_new_password.parent().append(
                                                '<div class="input-help text-red"><ul><li>必填</li><li>两次输入的密码不一致</li></ul></div>');
                                            return;
                                        } else if ($again_new_password.val()
                                                   != $new_password.val()) {
                                            $again_new_password.parent().find(".input-help")
                                                .remove();
                                            $again_new_password.closest(".form-group")
                                                .addClass("check-error");
                                            $again_new_password.parent().append(
                                                '<div class="input-help text-red"><ul><li>两次输入的密码不一致</li></ul></div>');
                                            return;
                                        }
                                        $(this).closest(".step_one").addClass("hidden");
                                        $(this).closest(".step_one").next().removeClass("hidden");
                                        obj.password = $("#new_password").val();
                                        console.log(JSON.stringify(obj));
                                        userSecService.edit(obj);
                                    },

                                    change_phone_num_title: "更换手机号码",
                                    //更换用户手机号码
                                    change_phone_num: function () {
                                        $("#user-sec").addClass("hidden");
                                        $("#change_phone_num").removeClass("hidden");
                                        $("#change_phone_num .step_two").addClass("hidden");
                                        $("#change_phone_num .step_three").addClass("hidden");
                                        $("#change_phone_num .step_one").removeClass("hidden");
                                        //$("#change_phone_num .step_one").addClass("fadein-right");
                                        $("#change_user_phone").text($("#user_phone_num").text());

                                        $("#change_phone_num .step div:nth-child(1)")
                                            .addClass("active");
                                        $(".step .active").css("background", "#0099d7");
                                        $(".step .active .step-point").css("background", "#0099d7");
                                        $("#change_phone_num .step div:nth-child(2)")
                                            .removeClass("active");
                                        $("#change_phone_num .step div:nth-child(2)")
                                            .css("background", "");
                                        $("#change_phone_num .step div:nth-child(2)")
                                            .find(".step-point").css("background", "");
                                        $("#change_phone_num .step div:nth-child(3)")
                                            .removeClass("active");
                                        $("#change_phone_num .step div:nth-child(3)")
                                            .css("background", "");
                                        $("#change_phone_num .step div:nth-child(3)")
                                            .find(".step-point").css("background", "");
                                        $("#change_phone_num .step div:nth-child(1)")
                                            .removeClass("complete");
                                        $("#change_phone_num .step div:nth-child(2)")
                                            .removeClass("complete");
                                        $("#change_phone_num .step div:nth-child(1) span:nth-child(1)")
                                            .text("1");
                                        $("#change_phone_num .step div:nth-child(2) span:nth-child(1)")
                                            .text("2");
                                        $("#change_phone_num .step div:nth-child(3) span:nth-child(1)")
                                            .text("3");
                                        $("#change_phone_num .step div:nth-child(1) span:nth-child(1)")
                                            .removeClass("icon-check");
                                        $("#change_phone_num .step div:nth-child(2) span:nth-child(1)")
                                            .removeClass("icon-check");
                                        $("#change_phone_num .step div:nth-child(3) span:nth-child(1)")
                                            .removeClass("icon-check");
                                        $("#change_phone_num .step").next().removeClass("hidden");

                                        $("#change_phone_num .cancel_warning").val("");
                                        $("#change_phone_num .form-group")
                                            .removeClass("check-error");
                                        $("#change_phone_num .form-group")
                                            .removeClass("check-success");
                                        $("#change_phone_num .input-help").remove()
                                    },
                                    //保存新手机号码
                                    save_new_phone: function () {
                                        //var new_phone_check_code = $(this).closest("table")
                                        //    .find(".check_code").val();
                                        //if (new_phone_check_code == "") {
                                        //    $(this).closest("tr").prev().find(".input-help")
                                        //        .remove();
                                        //    $(this).closest("tr").prev().find(".form-group")
                                        //        .addClass("check-error");
                                        //    $(this).closest("tr").prev().find(".field").append(
                                        //        '<div class="input-help
                                        // text-red"><ul><li>必填</li><li>请输入6位验证码</li></ul></div>');
                                        // return; } else if (new_phone_check_code.length != 6) {
                                        // $(this).closest("tr").prev().find(".input-help")
                                        // .remove();
                                        // $(this).closest("tr").prev().find(".form-group")
                                        // .addClass("check-error");
                                        // $(this).closest("tr").prev().find(".field").append(
                                        // '<div class="input-help
                                        // text-red"><ul><li>请输入6位验证码</li></ul></div>'); return }
                                        var $second_step = $("#change_phone_num").find(".step")
                                            .children("div").eq(1);
                                        $second_step.removeClass("active");
                                        $second_step.addClass("complete");
                                        $(".step .complete").css("background", "#2c7");
                                        $(".step .complete .step-point").css("background", "#2c7");
                                        $second_step.find(".step-point").text("");
                                        $second_step.find(".step-point").addClass("icon-check");
                                        $second_step.next().addClass("active");
                                        $(".step .active").css("background", "#0099d7");
                                        $(".step .active .step-point").css("background", "#0099d7");
                                        $("#change_phone_num .step_two").addClass("hidden");
                                        $("#change_phone_num .step_three").removeClass("hidden");
                                        $("#change_phone_num .step_three").addClass("fadein-right");
                                        $("#new_phone").text($("#new_phone_num").val());
                                        obj.mobile = $("#new_phone_num").val();
                                        console.log(JSON.stringify(obj));
                                        userSecService.edit(obj);
                                    },

                                    //发送验证码
                                    send_check_code: function () {
                                        var $me = $(this);
                                        var prompt_text = $me.parent().prev().prev().text();//根据提示文本判断更换邮箱还是手机号
                                        if (prompt_text.indexOf("新邮箱") > 0) {
                                            var new_email = $("#new_email").val();
                                            if (new_email == "") {
                                                $(".input-help").remove();
                                                $me.parent().prev().find(".form-group")
                                                    .addClass("check-error");
                                                $me.parent().prev().find(".field").append(
                                                    '<div class="input-help text-red"><ul><li>必填</li><li>请输入正确的邮箱地址</li></ul></div>');
                                                return;
                                            } else if ($me.closest("table")
                                                           .find(".input-help").length != 0) {
                                                $(".input-help").remove();
                                                $me.parent().prev().find(".form-group")
                                                    .addClass("check-error");
                                                $me.parent().prev().find(".field").append(
                                                    '<div class="input-help text-red"><ul><li>请输入正确的邮箱地址</li></ul></div>');
                                                return;
                                            }
                                        } else if (prompt_text.indexOf("新手机") > 0) {
                                            var new_phone_num = $("#new_phone_num").val();
                                            if (new_phone_num == "") {
                                                $(".input-help").remove();
                                                $me.parent().prev().find(".form-group")
                                                    .addClass("check-error");
                                                $me.parent().prev().find(".field").append(
                                                    '<div class="input-help text-red"><ul><li>必填</li><li>请输入正确的手机号码</li></ul></div>');
                                                return;
                                            } else if ($me.closest("table")
                                                           .find(".input-help").length != 0) {
                                                $(".input-help").remove();
                                                $me.parent().prev().find(".form-group")
                                                    .addClass("check-error");
                                                $me.parent().prev().find(".field").append(
                                                    '<div class="input-help text-red"><ul><li>请输入正确的手机号码</li></ul></div>');
                                                return;
                                            }
                                        }

                                        var InterValObj; //timer变量，控制时间
                                        var count = 60; //间隔函数，1秒执行
                                        var curCount;//当前剩余秒数
                                        sendMessage();
                                        function sendMessage() {
                                            curCount = count;
                                            $me.attr("disabled", "true");
                                            $me.text("请在" + curCount + "秒后重新发送验证码");
                                            InterValObj = window.setInterval(SetRemainTime, 1000);
                                        }

                                        function SetRemainTime() {
                                            if (curCount == 0) {
                                                window.clearInterval(InterValObj);//停止计时器
                                                $me.removeAttr("disabled");//启用按钮
                                                //$me.closest(".step_two").find(".field
                                                // input").removeAttr("readonly");
                                                $me.addClass("bg-blue");
                                                $me.text("重新发送验证码");
                                            } else {
                                                curCount--;
                                                $me.attr('disabled', "true");
                                                $me.text("请在" + curCount + "秒后重新发送验证码");
                                            }
                                        }
                                    },
                                    //下一步按钮
                                    next_step: function () {
                                        var $checkCode = $(this).closest("table")
                                            .find(".check_code");
                                        $(".input-help").remove();
                                        //if($checkCode.val() == ""){
                                        //    $checkCode.closest(".form-group").addClass("check-error");
                                        // $checkCode.parent().append('<div class="input-help
                                        // text-red"><ul><li>必填</li><li>请输入6位验证码</li></ul></div>');
                                        // return; }else if($checkCode.val().length != 6){
                                        // $checkCode.closest(".form-group").addClass("check-error");
                                        // $checkCode.parent().append('<div class="input-help
                                        // text-red"><ul><li>请输入6位验证码</li></ul></div>'); return; }
                                        var $first_step = $(this).closest(".change")
                                            .find(".step-bar").first();
                                        $first_step.removeClass("active");
                                        $first_step.addClass("complete");
                                        $(".step .complete").css("background", "#2c7");
                                        $(".step .complete .step-point").css("background", "#2c7");
                                        $first_step.find(".step-point").text("");
                                        $first_step.find(".step-point").addClass("icon-check");
                                        $first_step.next().addClass("active");
                                        $(".step .active").css("background", "#0099d7");
                                        $(".step .active .step-point").css("background", "#0099d7");

                                        $(this).closest(".step_one").addClass("hidden");
                                        $(this).closest(".step_one").next().removeClass("hidden");
                                    },
                                    //返回用户安全
                                    return_sec: function () {
                                        var curr = $("#new_email").val();
                                        var currPhone = $("#new_phone_num").val() ;
                                        if (curr =="" || curr == null){
                                            userSec.email = variable.currentUser.email;
                                        }else{
                                            userSec.email = $("#new_email").val();
                                        }
                                        if (currPhone == "" || currPhone == null){
                                            userSec.mobile = variable.currentUser.mobile == "" || null ? "未填写": variable.currentUser.mobile;
                                        }else{
                                            userSec.mobile = $("#new_phone_num").val();
                                        }
                                        $(".change").addClass("hidden");
                                        $("#user-sec").removeClass("hidden");
                                        $("#user-sec").addClass("fadein-left");
                                    }
                                });

    var obj = {
        email: null,
        password: null,
        mobile: null
    }

    cancel_warning = function () {
        $(".cancel_warning").focus(function () {
            var $me = $(this);
            $me.closest(".field").find(".input-help").remove();
            $me.closest(".form-group").removeClass("check-error");
        });
    }

    return avalon.controller(function ($ctrl) {
        $ctrl.$onEnter = function (param, rs, rj) {
            // 进入视图
            avalon.vmodels.userContainer.secondNavFlag = "account"
            avalon.vmodels.userContainer.navSelectedFlag = 'user.account-secure';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
            //取消警告提示
            cancel_warning();
        }
        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };
        $ctrl.$vmodels = [userSec];
    });
});