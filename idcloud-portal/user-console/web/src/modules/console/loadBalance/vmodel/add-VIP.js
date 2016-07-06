define(['avalon','app-utils/validatorService','validator'], function(validate) {
    var addVIP = avalon.define({
        $id:'addVIP',
        data:{
        },
        sessionPersistenceSwitch:function(obj){
            if($(obj).val() == "APP_COOKIE"){
                $("#appCookie").closest("tr").removeClass("hidden")
            }else{
                $("#appCookie").closest("tr").addClass("hidden")
            }
        },
        initValidator:function(){
            $("#addVIPForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    name:{required: true,maxlength:32},
                    portNumber:{required: true,min:2,max:65534},
                    agreement:{required: true},
                    appCookie:{required: true}
                },
                messages: {
                    name:{
                        required:"必填",
                        maxlength: '名称最长为32个字符'
                    },
                    portNumber: {
                        required:"必填",
                        min:"输入大于1小于65535的整数",
                        max:"输入大于1小于65535的整数"
                    },
                    agreement: {
                        required:"请选择协议"
                    },
                    appCookie: {
                        required:"必填"
                    }
                },
                submitHandler:function(form){
                    alert("submitted");
                }
            });
        }
    });

    return addVIP;
});