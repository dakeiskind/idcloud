define(['avalon'], function() {
    var IPSecAddWindow = avalon.define({
        $id:'IPSecAddWindow',
        addIPSecData:null,
        initValidator:function(){
            // 验证格式
            $("#addIPSecForm").validate({
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

    return IPSecAddWindow;

});
