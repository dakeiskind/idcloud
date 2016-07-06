define(['app-utils/validatorService','validator','avalon'], function(validate){
    var fpAdd = avalon.define({
        $id:'fpAdd',
        data:{},
        initValidator:function(){
            $("#addFpForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    invoiceItemAmount: {required: true,number:true,min:1}
                },
                messages: {
                    invoiceItemAmount: {
                        required:"必填",
                        number:"请输入正确的金额格式",
                        min:"请输入大于等于1元的金额"
                    }
                },
                submitHandler:function(form){
                }
            });
        }
    });
        return fpAdd;
});
