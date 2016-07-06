define(['avalon','app-utils/validatorService','validator'], function(validate) {
    var addResourcePool = avalon.define({
        $id:'addResourcePool',
        data:{
            name:null,
            describe:null,
            provider:"0",
            subnet:"",
            agreement:"",
            LBMode:""
        },
        initValidator:function(){
            $("#addResourcePoolForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    name:{required: true,maxlength:32},
                    subnet:{required: true},
                    agreement:{required: true},
                    LBMode:{required: true}
                },
                messages: {
                    name:{
                        required:"必填",
                        maxlength: '名称最长为32个字符'
                    },
                    subnet: {
                        required:"请选择子网"
                    },
                    agreement: {
                        required:"请选择协议"
                    },
                    LBMode: {
                        required:"请选择负载均衡方式"
                    }
                },
                submitHandler:function(form){
                    alert("submitted");
                }
            });
        }
    });
    return addResourcePool;
});