define(['avalon','app-utils/validatorService','validator'], function(validate) {
    var modifyResourcePool = avalon.define({
        $id:'modifyResourcePool',
        data:null,
        initValidator:function(){
            $("#modifyResourcePoolForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    name:{required: true,maxlength:32},
                    LBMode:{required: true}
                },
                messages: {
                    name:{
                        required:"不能为空",
                        maxlength: '名称最长为32个字符'
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
    return modifyResourcePool;
});