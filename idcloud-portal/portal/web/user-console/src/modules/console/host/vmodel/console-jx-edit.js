define(['avalon'] , function(){
    var editJx = avalon.define({
        $id:'editJx',
        data:null,
        initValidator:function(){
            $("#editJxForm").validate({
                onkeyup:function(element){
                    $(element).valid();
                },
                rules:{
                    mName:{required: true},
                    desc:{required: true, maxlength: 126,minlength:2}
                },
                messages:{
                    mName:{required:"必填项"},
                    desc:{
                        required:"必填项",
                        minlength:"字段不能少于2个字符",
                        maxlength: '字段不能超过126个字符'
                    }
                }
            });
        }
    });

    return editJx;

});
