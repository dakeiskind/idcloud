define(['avalon',
    'app-utils/validatorService'], function() {
    var createAlarmContacts = avalon.define({
        $id:'createAlarmContacts',
        data:null,
        initValidator:function(){
            $("#createAlarmContactsFrom").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    contactName: {required: true, minlength:2, maxlength:40},
                    contactPhone: {required: true,isMobile:true},
                    code: {required: true},
                    contactEmail: {required: true,email:true}
                },
                messages: {
                    contactName: {
                        required:"不能为空",
                        minlength:"姓名为2-40个字符",
                        maxlength:"姓名为2-40个字符"
                    },
                    contactPhone: {
                        required:"不能为空"
                    },
                    code: {
                        required:"不能为空"
                    },
                    contactEmail: {
                        required:"不能为空",
                        email:"邮箱输入有误"
                    }
                }
            });
        }
    });

    return createAlarmContacts;
});