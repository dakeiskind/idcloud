function registed() {
    $("#regist").removeClass("show");
    $("#regist").addClass("hidden");
    $("#registinfo").removeClass("hidden");
    $("#registinfo").addClass("show");
}
function cheacksieyi() {
    if (vexistMobile()) {
        if ($("#xieyi").prop("checked")) {
            $("#commitregist").removeAttr("disabled");
        } else {
            $("#commitregist").attr("disabled", "true");
        }
    } else {
        $("#xieyi").attr("checked", false);
    }
}
//$('checkbox').click(function(){
//    alert($(this).attr('checked'));
//    if($(this).attr('checked')){
//    $("#commitregist").removeClass("irs-disabled");
// $(this).atttr('checked',false); }
//    else{
//    //do someting else
//    } });
function checkSubmitAccount() {
    //vexist();
    //vexistEmail();
    if ($("#account").val() === "") {
        $("#account").focus();
        return false;
    }
    return true;
}
function checkSubmitEmail() {
    checkSubmitPassword();
    if ($("#email").val() === "") {
        $("#confirmMsg").html("<font color='red'>邮箱地址不能为空！</font>");
        //alert("邮箱不能为空!")
        $("#email").focus();
        return false;
    }
    if (!$("#email").val()
            .match(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/)) {
        //alert("邮箱格式不正确");
        $("#confirmMsg").html("<font color='red'>邮箱格式不正确！请重新输入！</font>");
        $("#email").focus();
        return false;
    }
    if ($("#email").val()
            .match(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/)) {
        //alert("邮箱格式不正确");
        $("#confirmMsg").html("<font color='red'></font>");
        return true;
    }
    return true;
}

function checkrelname() {
    if ($("#realName").val() === "" || $("#realName").val() == null) {
        $("#realName").focus();
        checkcompanyname();
        return false;
    }
    return true;
}

function checkcompanyname() {
    if ($("#tenantName").val() === "" || $("#tenantName").val() == null) {
        $("#tenantName").focus();
        checkaddressmsg();
        return false;
    }
    return true;
}

function checkaddressmsg() {
    if ($("#addressmsg").val() === "" || $("#addressmsg").val() == null) {
        $("#addressmsg").focus();
        $("#realName").focus();
        //checkrelname();
        return false;
    }
    return true;
}
function checkSubmitPassword() {
    if ($("#confirmPassword").val() === "") {
        $("#confirmMsgPw").html("<font color='red'>密码不能为空！</font>");
        //alert("邮箱不能为空!")
        $("#confirmPassword").focus();
        return false;
    }
    if ($("#confirmPassword").val().length < 6) {
        $("#confirmMsgPw").html("<font color='red'>密码不能小于6位！</font>");
        //alert("邮箱不能为空!")
        $("#confirmPassword").focus();
        return false;
    }
    if ($("#confirmPassword").val().length >= 6) {
        $("#confirmMsgPw").html("<font color='red'></font>");
        //alert("邮箱不能为空!")
        return true;
    }
    return true;
}

function sureSubmitPassword() {
    if ($("#surepassword").val() != ($("#confirmPassword").val())) {
        $("#confirmMsgSpw").html("<font color='red'>两次输入密码不相等</font>");
        $("#surepassword").focus();
        return false;
    }
    if ($("#surepassword").val() == ($("#confirmPassword").val())) {
        $("#confirmMsgSpw").html("<font color='red'></font>");
        return true;
    }
    return true;
}

//jquery验证手机号码
function checkSubmitMobil() {

    if (!$("#mobile").val().match(/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/)) {
        //alert("请输入正确的手机号码！");
        $("#moileMsg").html("<font color='red'>请输入正确的手机号码！</font>");
        return false;
    }
    if ($("#mobile").val().match(/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/)) {
        $("#moileMsg").html("<font color='red'></font>");
        return true;
    }
    return true;
}

function checkSubmit() {
    if (!checkSubmitAccount() && checkSubmitEmail() && checkSubmitPassword() && checkSubmitMobil()
        && sureSubmitPassword() && vexist() && vexistEmail() && vexistMobile()) {
        return false;
    }
    if (checkSubmitAccount() && checkSubmitEmail() && checkSubmitPassword() && sureSubmitPassword()
        && vexist() && vexistEmail() && vexistMobile()) {
        registed();
        return true;
    }
    return true;
}

function checkIofoSubmit() {
    //alert(checkrelname())
    //checkrelname();
    if (checkrelname() && checkcompanyname() && checkaddressmsg == false) {
        //alert("sdsds")
        return false;
    }
    if (checkrelname() && $("#realName").val() != null && $("#tenantName").val() != "" && $(
            "#tenantName").val() != null && $("#addressmsg").val() != "" && $("#addressmsg").val()
                                                                            != null && vmgtobj()) {
        tenantinfo = getTenantInfo();
//获得管理员信息
        managerinfo = getManagerInfo();
        Core.AjaxRequest({
             url: ws_url + "/rest/user/registerProtalUser",
             type: "post",
             params: {
                 tenant: tenantinfo,
                 user: managerinfo
             },
             //showMsg:false,
             callback: function (data) {
                 if(data!==false){
                     $("#registinfo").removeClass("show");
                     $("#registinfo").addClass("hidden");
                     $("#registsendemalie").removeClass("hidden");
                     $("#registsendemalie").addClass("show");
                     var emailv = $("#email").val();
                     $("#semail").text(emailv);
                 }else {
                     $("#SubmitRegistEro").removeClass("hidden");
                     $("#SubmitRegist").addClass("hidden");
                 }
             },
             failure: function (data) {
                 //Core.alert({title:"提示",message:data.message});
             }
         })
        return true;
    } else {
        return false;
    }
}

function checkIofoSubmitP() {
    if (checkrelname() && $("#realName").val() != null && $("#addressmsg").val() != "" && $("#addressmsg").val()
                                                                                          != null) {
        tenantinfo = getTenantInfo();
        //获得管理员信息
        managerinfo = getManagerInfo();
        Core.AjaxRequest({
             url: ws_url + "/rest/user/registerProtalUser",
             type: "post",
             params: {
                 tenant: tenantinfo,
                 user: managerinfo
             },
             //showMsg:false,
             callback: function (data) {
                 if(data!==false){
                     $("#registinfo").removeClass("show");
                     $("#registinfo").addClass("hidden");
                     $("#registsendemalie").removeClass("hidden");
                     $("#registsendemalie").addClass("show");
                     var emailv = $("#email").val();
                     $("#semail").text(emailv);
                 }else {
                     $("#SubmitRegistEro").removeClass("hidden");
                     $("#SubmitRegist").addClass("hidden");
                 }
             },
             failure: function (data) {
                 //Core.alert({title:"提示",message:data.message});
             }
         })
        return true;
    } else {
        return false;
    }
}
function complate() {
//    // 获得企业信息
//    tenantinfo = getTenantInfo();
////获得管理员信息
//    managerinfo = getManagerInfo();
//    Core.AjaxRequest({
//        url : ws_url+"/rest/user/registerProtalUser",
//        type:"post",
//        params : {
//            tenant : tenantinfo,
//            user : managerinfo
//        },
//        showMsg:false,
//        callback : function(data) {
//            if(data.status == 1){
    $("#registsendemalie").removeClass("show");
    $("#registsendemalie").addClass("hidden");
    $("#complete").removeClass("hidden");
    $("#complete").addClass("show");
    //        }else{
    //            //Core.alert({title:"提示",message:"注册失败!"});
    //        }
    //    },
    //    failure: function(data){
    //        //Core.alert({title:"提示",message:data.message});
    //    }
    //});
}
//发送验证码
function send_check_code() {
    var me = $("#senmsg");
    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount;//当前剩余秒数
    sendMessage();
    createCode();
    function sendMessage() {
        curCount = count;
        me.attr("disabled", "true");
        me.text(curCount + "秒后重新获取验证码");
        InterValObj = window.setInterval(SetRemainTime, 1000);
    }

    function SetRemainTime() {
        if (curCount === 0) {
            window.clearInterval(InterValObj);//停止计时器
            me.removeAttr("disabled");//启用按钮
            //$me.closest(".step_two").find(".field input").removeAttr("readonly");
            me.addClass("bg-blue");
            me.text("重新发送验证码");
        } else {
            curCount--;
            me.attr('disabled', "true");
            me.text(curCount + "秒后重新获取验证码");
        }
    }
}

function createCode() {
    logCode = new Array();
    var codeLength = 4;//楠岃瘉鐮佺殑闀垮害

    var selectChar = new Array(2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

    for (var i = 0; i < codeLength; i++) {
        var charIndex = Math.floor(Math.random() * 32);
        logCode += selectChar[charIndex];
    }
    if (logCode.length != codeLength) {
        createCode();
    }
    $("#authcode").val(logCode);
}

function backlogin() {
    window.location.href = "login.html";
}

function pchoose() {
    $("#comche").removeAttr("checked");
    $("#perche").attr("checked", "checked");
    $("#companynames").addClass("hidden");
    $("#submit").addClass("hidden")
    $("#submitp").removeClass("hidden")
    //$("#tenantName").val(" ");
    //$("#caddressmodel").addClass("hidden");
    //$("#paddressmodel").removeClass("hidden")
}
function cchoose() {
    $("#perche").removeAttr("checked");
    $("#comche").attr("checked", "checked");
    $("#companynames").removeClass("hidden");
    $("#submitp").addClass("hidden")
    $("#submit").removeClass("hidden")
    //$("#caddressmodel").removeClass("hidden");
    //$("#paddressmodel").addClass("hidden")
}
// 得到用户注册信息
function getManagerInfo() {
    var user = new Object();
    // 账户
    user.account = $("#account").val();
    // 邮件
    user.email = $("#email").val();
    // 电话
    user.mobile = $("#mobile").val();
    // 密码
    user.password = $("#confirmPassword").val();
    // 名称
    user.realName = $("#realName").val();
    return user;
}
//得到用户企业信息
function getTenantInfo() {
    var tenant = new Object();
    //用户类型
    if ($("#comche").prop("checked")) {
        tenant.tenantType = $("#comche").val();
    } else if ($("#perche").prop("checked")) {
        tenant.tenantType = $("#perche").val();
    }
    // 企业名称
    if ($("#tenantName").val() != "") {
        tenant.tenantName = $("#tenantName").val();
    }
    tenant.tenantIndustry = $("#industry").val();
    tenant.tenantProvince = $("#selProvince").val();
    tenant.tenantCity = $("#selCity").val();
    tenant.tenantArea = $("#selArea").val();
    tenant.tenantAddress = $("#addressmsg").val();
    //判断企业名称是否重复
    //var tds = $("#tenantTable").find('td');
    //for ( var i = 1; i < tds.length; i=i+2) {
    //    var defName = $($(tds[i]).find('input')).attr('id');
    //    var defValue = $($(tds[i]).find('input')).val();
    //    tenant[defName] = defValue;
    //}
//	// 联系人
//	tenant.contact = $("#contact").val();
//	// 联系人电话
//	tenant.contactPhone = $("#contactPhone").val();
//	// 联系人职位
//	tenant.contactPosition = $("#contactPosition").val();
//	// 邮箱
//	tenant.email = $("#email").val();

    return tenant;
}


function validateExits(type,typeData){
    var result = false;
    Core.AjaxRequest({
         url: ws_url + "/rest/user/validateExits?validateType=" + type+"&validateData="+typeData+"&isjQuery=false",
         type: "get",
         showTimeout: false,
         showMsg: false,
         async: false,
         callback: function (data) {
             result = data;
         }
     });
    return result;
}

// 查询用户名是否重复
function searchUserByName(name) {
    var Todata = null;
    if (name != null && name !== "") {
        Core.AjaxRequest({
             url: ws_url + "/rest/user/validateUserAccount?account=" + name,
             type: "get",
             showTimeout: false,
             showMsg: false,
             async: false,
             callback: function (data) {
                 Todata = data;
             }
         });
    }
    return Todata;
}
// 查询邮箱是否重复
function searchUserByEmail(email) {
    var Todata = null;
    if (email != null && email !== "") {
        Core.AjaxRequest({
         url: ws_url + "/rest/user/validateUserEmail?email=" + email,
         type: "get",
         showTimeout: false,
         showMsg: false,
         async: false,
         callback: function (data) {
             Todata = data;
         }
     });
    }
    return Todata;
}
// 查询手机是否重复
function searchUserByMobile(mobile) {
    if (mobile != null || mobile !== "") {
        var Todata = null;
        if (mobile != null && mobile !== "") {
            Core.AjaxRequest({
                 url: ws_url + "/rest/user/validateUserMobile?mobile=" + mobile,
                 type: "get",
                 showTimeout: false,
                 showMsg: false,
                 async: false,
                 callback: function (data) {
                     Todata = data;
                 }
             });
        }
        return Todata;
    }
}
function vexist() {
    if ($("#account").val() !== "") {
        var validateResult = validateExits("account",$("#account").val());
        if(validateResult == true){
            $("#accountName").removeClass("text-red");
            $("#account").removeClass("border-red");
            $("#exist").html("<font color='red'></font>");
        }else{
            $("#accountName").addClass("text-red");
            $("#account").addClass("border-red");
            $("#exist").html("<span style='color: #E33'>账号已存在，请重新输入</span>");
            $("#account").focus();
        }
        return validateResult;
    }
}
function vexistEmail() {
    if ($("#email").val() !== "") {
        var validateResult = validateExits("email",$("#email").val());
        //alert(list.status);
        if (validateResult == true) {
            $("#emailName").removeClass("text-red");
            $("#email").removeClass("border-red");
            $("#existEmail").html("<font color='red'></font>");
            return true;
        } else {
            $("#emailName").addClass("text-red");
            $("#email").addClass("border-red");
            $("#existEmail").html("<span style='color: #E33'>邮箱已存在，请重新输入</span>");
            $("#email").focus();
            return false;
        }
    }
}
function vexistMobile() {
    if ($("#mobile").val() !== "") {
        var validateResult = validateExits("mobile",$("#mobile").val());
        //alert(list.status);
        if (validateResult == true) {
            $("#mobileName").removeClass("text-red");
            $("#mobile").removeClass("border-red");
            $("#existMobile").html("<font color='red'></font>");
            return true;
        } else {
            $("#mobileName").addClass("text-red");
            $("#mobile").addClass("border-red");
            $("#existMobile").html("<span style='color: #E33'>手机号已存在，请重新输入</span>");
            $("#mobile").focus();
            return false;
        }
    } else if ($("#mobile").val() === "") {
        $("#mobileName").removeClass("text-red");
        $("#mobile").removeClass("border-red");
        $("#existMobile").html("<font color='red'></font>");
        return true;
    }

    return true;
}
function vmgtobj() {
    var flag = checkMgtObjName();
    //alert(flag);
    if (flag) {
        $("#companyname").removeClass("text-red");
        $("#tenantName").removeClass("border-red");
        $("#mgtobjmsg").html("<font color='red'></font>");
        return true;
    } else {
        $("#companyname").addClass("text-red");
        $("#tenantName").addClass("border-red");
        $("#mgtobjmsg").html("<font color='red'>该公司已注册，请重新输入</font>");
        $("#tenantName").focus();
        return false;
    }
}

function checkMgtObjName() {
    var flag = false;
    var tenantName = $("#tenantName").val();
    if  (tenantName===""||tenantName===null){
        flag = true;
    }
    if (tenantName != null && tenantName !== "") {
        var condition = {};
        var params = {
            condition: condition
        };
        condition['name'] = tenantName;

        Core.AjaxRequest({
         url: ws_url + "/rest/mgtObj/find",
         type: "post",
         params: params,
         showTimeout: false,
         showMsg: false,
         async: false,
         callback: function (data) {
             var datas = eval(JSON.stringify(data));
             for (var i = 0; i < datas.length; i++){}
             if (data.length <= 0 || (data.length !== 0 && datas[0].name
                                                           == " ")) {
                 flag = true;
             }else {
                 flag = false;
             }
         }
     });
    }
    return flag;
}

var hash = {
    'qq.com': 'http://mail.qq.com',
    'gmail.com': 'http://mail.google.com',
    'sina.com': 'http://mail.sina.com.cn',
    '163.com': 'http://mail.163.com',
    '126.com': 'http://mail.126.com',
    'yeah.net': 'http://www.yeah.net/',
    'sohu.com': 'http://mail.sohu.com/',
    'tom.com': 'http://mail.tom.com/',
    'sogou.com': 'http://mail.sogou.com/',
    '139.com': 'http://mail.10086.cn/',
    'hotmail.com': 'http://www.hotmail.com',
    'live.com': 'http://login.live.com/',
    'live.cn': 'http://login.live.cn/',
    'live.com.cn': 'http://login.live.com.cn',
    '189.com': 'http://webmail16.189.cn/webmail/',
    'yahoo.com.cn': 'http://mail.cn.yahoo.com/',
    'yahoo.cn': 'http://mail.cn.yahoo.com/',
    'eyou.com': 'http://www.eyou.com/',
    '21cn.com': 'http://mail.21cn.com/',
    '188.com': 'http://www.188.com/',
    'foxmail.com': 'http://www.foxmail.com',
    'outlook.com': 'http://www.outlook.com'
}
function linkEmail() {
    // 点击登录邮箱
    var _mail = $("#semail").html().split('@')[1];    //获取邮箱域
    for (var j in hash) {
        if (j == _mail) {
            window.open(hash[_mail]);    //替换登陆链接
        }
    }
}
$(function () {
    $("#account").blur(function () {
        vexist();
    });
    $("#email").blur(function () {
        vexistEmail();
    });
    $("#tenantName").blur(function () {
        vmgtobj();
    });
    if ($("#mobile") != "" || $("#mobile") != null) {
        $("#mobile").blur(function () {
            vexistMobile();
        });
    }
});

//上一步
function toback(){
    $("#regist").removeClass("hidden");
    $("#regist").addClass("show");
    $("#registinfo").removeClass("show");
    $("#registinfo").addClass("hidden");
}
//获取数据字典
function getCommonCode(codeName){
    var codeObj = new Object();
    Core.AjaxRequest({
         url : ws_url +"/rest/system/"+codeName,
         type:"get",
         async:false,
         callback : function (data) {
             codeObj = data;
         }
     });
    return codeObj;
}
//设置所属行业
function setIndustry(id,codeName){
    var data = getCommonCode(codeName);
    if(data.data!=null){
        data = data.data;
    }
    for(var i = 0;i<data.length;i++){
        $("#"+id).append($("<option value='"+data[i].codeValue+"'>"+data[i].codeDisplay+"</option>"));
    }
}
//设置省份
function getProvince(id){
    Core.AjaxRequest({
         url :ws_url +"/rest/getAddress/getProvince",
         type: 'GET',
         async:false,
         callback : function (data) {
             if(data != null ){
                 console.log(data)
                 for(var i = 0;i<data.length;i++){
                     $("#"+id).append(
                         $("<option value='"+data[i].provinceId+"'>"+data[i].provinceName+"</option>")
                     );
                 }
             }
         }
     });
}
//设置城市
function getCity(idArea,id,provinceId){
    var returnData;
    Core.AjaxRequest({
         url :ws_url +"/rest/getAddress/getCityByProvince/"+provinceId,
         type: 'GET',
         async:false,
         callback : function (data) {
             if(data != null ){
                 $("#"+id).empty();
                 for(var i = 0;i<data.length;i++){
                     $("#"+id).append(
                         $("<option value='"+data[i].cityId+"'>"+data[i].cityName+"</option>")
                     );
                     if(i == 0){
                         returnData = data[i].cityId;
                     }
                     if(data[i].cityName == '市辖区'){
                         break;
                     }
                 }
             }
         }
     });
    return returnData;
}
//设置区县
function getArea(id,cityId){
    Core.AjaxRequest({
         url :ws_url +"/rest/getAddress/getAreaByCity/"+cityId,
         type: 'GET',
         async:false,
         callback : function (data) {
             if(data != null && data != ""){
                 $("#"+id).empty();
                 for(var i = 0;i<data.length;i++){
                     $("#"+id).append(
                         $("<option value='"+data[i].areaId+"'>"+data[i].areaName+"</option>")
                     );
                 }
             }else{
                 $("#"+id).empty();
                 $("#"+id).append(
                     $("<option>暂无数据</option>")
                 );
             }
         }
     });
}
function provinceSwitch(){
    var firstCityId = getCity('selArea','selCity',$("#selProvince").val());
    if(firstCityId != "" && firstCityId != null){
        getArea('selArea',firstCityId);
    }
}
function citySwitch(){
    if($("#selCity").val() != 100100){
        getArea('selArea',$("#selCity").val());
    }
}
$(function() {
    setIndustry('industry','TENANT_INDUSTRY');
    getProvince('selProvince');
    provinceSwitch()
});



