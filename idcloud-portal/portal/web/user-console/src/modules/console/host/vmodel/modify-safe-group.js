define(['avalon',
        'app-utils/validatorService'], function() {
    var modifySafeGroup = avalon.define({
        $id:'modifySafeGroup',
        modifyData:null,
        initValidator:function(){
            $("#modifySafeGroupFrom").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    sgName: {required: true, minlength:2, maxlength:128},
                    sgNet: {minlength:2, maxlength:20},
                    sgDescription: {minlength:2, maxlength:128}
                },
                messages: {
                    sgName: {
                        required:"不能为空",
                        minlength:"快照名称为2-128个字符",
                        maxlength:"快照名称为2-128个字符"
                    },
                    sgNet: {
                        minlength:"快照名称为2-20个字符",
                        maxlength:"快照名称为2-20个字符"
                    },
                    sgDescription: {
                        minlength:"快照名称为2-128个字符",
                        maxlength:"快照名称为2-128个字符"
                    }
                }
            });
        }
    });

    return modifySafeGroup;
});