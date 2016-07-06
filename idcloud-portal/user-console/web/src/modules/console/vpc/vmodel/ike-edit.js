define(['avalon'], function() {
    var ikeEditWindow = avalon.define({
        $id:'ikeEditWindow',
        addIKEData:null,
        initValidator:function(){
            // 验证格式
            $("#editIKEForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    IKEName: {required: true,maxlength: 128,minlength: 2},
                },
                messages: {
                    IKEName: {
                        required:"不能为空",
                        maxlength: '字段不能超过128个字符',
                        minlength: '字段最少为2个字符'
                    }
                }
            });
        }

    });

    return ikeEditWindow;

});
