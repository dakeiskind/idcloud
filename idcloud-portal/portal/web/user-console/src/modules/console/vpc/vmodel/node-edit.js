define(['avalon'], function() {
    var nodeEditWindow = avalon.define({
        $id:'nodeEditWindow',
        addNodeData:null,
        initValidator:function(){
            // 验证格式
            $("#editNodeForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    ipsNodeName: {required: true,maxlength: 128,minlength: 2},
                },
                messages: {
                    ipsNodeName: {
                        required:"不能为空",
                        maxlength: '字段不能超过128个字符',
                        minlength: '字段最少为2个字符'
                    }
                }
            });
        }

    });

    return nodeEditWindow;

});

