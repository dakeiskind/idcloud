define(['avalon'], function() {
    var vpnAddWindow = avalon.define({
        $id:'vpnAddWindow',
        addVPNData:null,
        initValidator:function(){
            // 验证格式
            $("#addVPNForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    VPNName: {required: true,maxlength: 128,minlength: 2},
                },
                messages: {
                    VPNName: {
                        required:"不能为空",
                        maxlength: '字段不能超过128个字符',
                        minlength: '字段最少为2个字符'
                    }
                }
            });
        }

    });

    return vpnAddWindow;

});
