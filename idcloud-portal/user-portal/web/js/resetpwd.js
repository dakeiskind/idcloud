
function checkSubmitEmail() {
    if ($("#email").val() == "") {
        $("#email").focus();
        return false;
    }
    if (!$("#email").val().match(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/)) {
        //alert("邮箱格式不正确");
        $("#email").focus();
        return false;
    }
    if ($("#email").val().match(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/)) {
        //alert("邮箱格式不正确");
        return true;
    }
}

function checkSubmitMobil() {
    if ($("#phone").val() == "") {
        //alert("手机号码不能为空！");
        $("#phone").focus();
        return false;
    }

    if (!$("#phone").val().match(/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/)) {
        //alert("请输入正确的手机号码！");
        $("#phone").focus();
        return false;
    }
    if ($("#phone").val().match(/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/)) {
        return true;
    }
    return true;
}

function checkSubmitPassword() {
    if ($("#xpassword").val() == "") {
        //alert("邮箱不能为空!")
        $("#xpassword").focus();
        return false;
    }
    if ($("#xpassword").val().length < 6) {
        //alert("邮箱不能为空!")
        $("#xpassword").focus();
        return false;
    }
    if ($("#xpassword").val().length >= 6) {
        //alert("邮箱不能为空!")
        return true;
    }
    return true;
}

function sureSubmitPassword() {
    if ($("#rxpassword").val() != ($("#xpassword").val())) {
        $("#rxpassword").focus();
        return false;
    }
    if ($("#rxpassword").val() == ($("#xpassword").val())) {
        return true;
    }
    return true;
}
function sheckyanzm(){
    if($("#yzm").val() == ""){
        $("#yzm").focus();
        return false;
    }
    return true;
}
function commitrq(){
    console.log($.idcode.validateCode())
    if(checkSubmitEmail()&&$.idcode.validateCode()){
        $("#resetrq").removeClass("show");
        $("#resetrq").addClass("hidden");
        $("#resetpwd").removeClass("hidden");
        $("#resetpwd").addClass("show");
    }else {
        $("#cheackyzm").html("<font color='red'>&nbsp;验证码输入错误</font>");
    }
}

function resetp(){
    if(checkSubmitPassword()&&sureSubmitPassword()){
        $("#resetpwd").removeClass("show");
        $("#resetpwd").addClass("hidden");
        $("#resetsuccess").removeClass("hidden");
        $("#resetsuccess").addClass("show");
    }
}
//发送验证码
function send_check_code(){
    var me = $("#senmsg");
    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount;//当前剩余秒数
    sendMessage();
    function sendMessage() {
        curCount = count;
        me.attr("disabled", "true");
        me.text( curCount + "秒后重新获取验证码");
        InterValObj = window.setInterval(SetRemainTime, 1000);
    }

    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj);//停止计时器
            me.removeAttr("disabled");//启用按钮
            //$me.closest(".step_two").find(".field input").removeAttr("readonly");
            me.addClass("bg-blue");
            me.text("重新发送验证码");
        } else {
            curCount--;
            me.attr('disabled',"true");
            me.text( curCount + "秒后重新获取验证码");
        }
    }
}