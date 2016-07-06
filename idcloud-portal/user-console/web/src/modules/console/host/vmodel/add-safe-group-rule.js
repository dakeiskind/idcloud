define(['avalon',
        'app-utils/validatorService'], function() {

    var addSafeGroupRule = avalon.define({
        $id:'addSafeGroupRule',
        data:null,
        initValidator:function(){
            $("#addSafeGroupRuleFrom").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    port: {required: true,minlength:3, maxlength:11}
                    //sgName: {required: true, minlength:2, maxlength:128},
                    //sgNet: {minlength:2, maxlength:20},
                    //sgDescription: {minlength:2, maxlength:128}
                },
                messages: {
                    port: {
                        required:'必填',
                        minlength:'请按照要求输入',
                        maxlength:'请按照要求输入'
                    }
                    //sgName: {
                    //    required:"不能为空",
                    //    minlength:"快照名称为2-128个字符",
                    //    maxlength:"快照名称为2-128个字符"
                    //},
                    //sgNet: {
                    //    minlength:"快照名称为2-20个字符",
                    //    maxlength:"快照名称为2-20个字符"
                    //},
                    //sgDescription: {
                    //    minlength:"快照名称为2-128个字符",
                    //    maxlength:"快照名称为2-128个字符"
                    //}
                }
            });
        },
        agreementTyleSwitch: function(obj){
            var agreementTyle = $(obj).val();
            if(agreementTyle == "0"){
                $("#port").removeAttr("disabled");
            }else if(agreementTyle == "1"){
                $("#port").attr("disabled",true);
            }
        },
        authorizationTypeSwitch: function(obj){
            var authorizationType = $(obj).val();
            if(authorizationType == "0"){
                $("#authorizationObjectInput").removeClass("hidden");
                $("#authorizationObjectSelect").addClass("hidden");
            }else if(authorizationType == "1"){
                $("#authorizationObjectSelect").removeClass("hidden");
                $("#authorizationObjectInput").addClass("hidden");
            }
        }
    });

    return addSafeGroupRule;
});