define(['avalon'],function(){
    var subUserAdd = avalon.define({
        $id:'subUserAdd',
        addSubUserData:null,
        initValidator:function(){
            //验证格式
            $("#addSubUserForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules:{
                    account:{required: true,remote:{
                                            url: "/idcloud-rest/rest/user/validateExits",
                                            type: "GET",
                                            data: {
                                                isjQuery:function(){return true;},
                                                validateType:function(){
                                                    return "account";
                                                },
                                                validateData: function() {
                                                           return $("#account").val();
                                                }
                                             }
                    }},
                    mobile:{required: true, number:true,minlength: 2,remote:{
                        url: "/idcloud-rest/rest/user/validateExits",
                        type: "GET",
                        data: {
                            isjQuery:function(){return true;},
                            validateType:function(){
                                return "mobile";
                            },
                            validateData: function() {
                                return $("#mobile").val();
                            }
                        }
                    }},
                    email:{required: true,email:true,remote:{
                        url: "/idcloud-rest/rest/user/validateExits",
                        type: "GET",
                        data: {
                            isjQuery:function(){return true;},
                            validateType:function(){
                                return "email";
                            },
                            validateData: function() {
                                return $("#email").val();
                            }
                        }
                    }},
                    password:{required: true},
                    confirmPwd:{required: true,equalTo:"#password"}
                },
                messages: {
                    account: {
                        required:"必填项",
                        remote:"账号已经存在"
                    },
                    mobile:{
                        required:"必填项",
                        number:"输入合法的数字",
                        minlength: '电话为11位',
                        remote:"手机号已经存在"
                    },
                    email:{
                        required:"必填项",
                        email:"输入正确格式的电子邮件",
                        remote:"邮箱已经存在"
                    },
                    password:{
                        required:"必填项"
                    },
                    confirmPwd:{
                        required:"必填项",
                        equalTo:"输入值必须和登陆密码相同"
                    }
                }
            })
        }
    });

    return subUserAdd;
});
