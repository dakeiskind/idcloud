define(['avalon'], function() {
    var ipsecEditWindow = avalon.define({
        $id:'ipsecEditWindow',
        addIPSecData:null,
        initValidator:function(){
            // 验证格式
            $("#editIPSecForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    IPSecName: {required: true,maxlength: 128,minlength: 2},
                },
                messages: {
                    IPSecName: {
                        required:"不能为空",
                        maxlength: '字段不能超过128个字符',
                        minlength: '字段最少为2个字符'
                    }
                }
            });
        }

    });

    return ipsecEditWindow;

});

