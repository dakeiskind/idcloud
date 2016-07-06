define(['avalon',
        'app-utils/validatorService'], function() {
    var createSafeGroup = avalon.define({
        $id:'createSafeGroup',
        data:null,
        initValidator:function(){
            $("#createSafeGroupFrom").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    sgName: {required: true, minlength:2, maxlength:32},
                    sgDescription: {minlength:2, maxlength:128}
                },
                messages: {
                    sgName: {
                        required:"不能为空",
                        minlength:"安全组名称为2-32个字符",
                        maxlength:"安全组名称为2-32个字符"
                    },
                    sgDescription: {
                        minlength:"描述为2-128个字符",
                        maxlength:"描述为2-128个字符"
                    }
                }
            });
        }
    });

    return createSafeGroup;
});