/**
 * Created by Administrator on 2016/1/18.
 */
function checkSubmitEmail() {
    if ($("#email").val() == "") {
        $("#confirmMsg").html("<font color='red'>邮箱地址不能为空！</font>");
        //alert("邮箱不能为空!")
        $("#email").focus();
        return false;
    }
    if (!$("#email").val().match(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/)) {
        //alert("邮箱格式不正确");
        $("#confirmMsg").html("<font color='red'>邮箱格式不正确！请重新输入！</font>");
        $("#email").focus();
        return false;
    }
    if ($("#email").val().match(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/)) {
        //alert("邮箱格式不正确");
        $("#confirmMsg").html("<font color='red'></font>");
        return true;
    }
    return true;
}

function checkSubmitPassword() {
    if ($("#password").val() == "") {
        $("#confirmMsgPw").html("<font color='red'>密码不能为空！</font>");
        //alert("邮箱不能为空!")
        $("#password").focus();
        return false;
    }
    if ($("#password").val().length < 6) {
        $("#confirmMsgPw").html("<font color='red'>密码不能小于6位！</font>");
        //alert("邮箱不能为空!")
        $("#password").focus();
        return false;
    }
    if ($("#password").val().length >= 6) {
        $("#confirmMsgPw").html("<font color='red'></font>");
        //alert("邮箱不能为空!")
        return true;
    }
    return true;
}

function sureSubmitPassword() {
    if ($("#surepassword").val() != ($("#password").val())) {
        $("#confirmMsgSpw").html("<font color='red'>两次输入密码不相等</font>");
        $("#surepassword").focus();
        return false;
    }
    if ($("#surepassword").val() == ($("#password").val())) {
        $("#confirmMsgSpw").html("<font color='red'></font>");
        return true;
    }
    return true;
}

//jquery验证手机号码
function checkSubmitMobil() {
    if ($("#phonenum").val() == "") {
        //alert("手机号码不能为空！");
        $("#moileMsg").html("<font color='red'>手机号码不能为空！</font>");
        $("#phonenum").focus();
        return false;
    }

    if (!$("#phonenum").val().match(/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/)) {
        //alert("请输入正确的手机号码！");
        $("#moileMsg").html("<font color='red'>请输入正确的手机号码！</font>");
        $("#phonenum").focus();
        return false;
    }
    if ($("#phonenum").val().match(/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/)) {
        $("#moileMsg").html("<font color='red'></font>");
        return true;
    }
    return true;
}

function checkSubmit() {
    if (!checkSubmitEmail() && checkSubmitPassword() && checkSubmitMobil() && sureSubmitPassword()) {
        return false;
    }
    if (checkSubmitEmail() && checkSubmitPassword() && checkSubmitMobil() && sureSubmitPassword()) {
        window.location.href = "register-info.html"
        return true;
    }
    return true;
}

function iCheckSubmit() {
    if ($("#addressmsg").val() == "") {
        $("#cofaddressmsg").html("<font color='red'>请填写完整地址！</font>");
        $("#addressmsg").focus();
        return false;
    }
    if ($("#addressmsg").val() != "") {
        window.location.href = "accountactivation.html"
        return true;
    }
    return true;
}

