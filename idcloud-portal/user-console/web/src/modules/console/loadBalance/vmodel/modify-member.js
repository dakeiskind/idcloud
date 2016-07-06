define(['avalon','app-utils/validatorService','validator'], function(validate) {
    var modifyMember = avalon.define({
        $id:'modifyMember',
        data:null,

        initValidator:function(){
            $("#modifyMemberForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    memberID:{required: true},
                    resourcePool:{required: true},
                    weight:{required: true,min:1,max:256}
                },
                messages: {
                    memberID:{
                        required:"必填"
                    },
                    resourcePool: {
                        required:"请选择至少一个成员"
                    },
                    weight: {
                        required:'必填',
                        min:"请输入大于等于1的值",
                        max:"请输入小于等于256的值"
                    }
                },
                submitHandler:function(form){
                    alert("submitted");
                }
            });
        }

    });
    return modifyMember;
});