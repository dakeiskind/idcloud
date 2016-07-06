define(['avalon','app-utils/validatorService','validator'], function(validate) {
    var addMember = avalon.define({
        $id:'addMember',
        resourcePoolData:null,
        data:{
            resourcePool:"",
            memberAddress:null,
            member:"",
            weight:"",
            agreementPort:""
        },

        memberSourceSwitch:function(obj){
            if($(obj).val() == "01"){
                $("#memberAddress").closest("tr").addClass("hidden");
                $("#memberTr").removeClass("hidden");
            }else if($(obj).val() == "02"){
                $("#memberTr").addClass("hidden");
                $("#memberAddress").closest("tr").removeClass("hidden");
            }
        },
        initValidator:function(){
            $("#addMemberForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    resourcePool:{required: true},
                    member:{required: true},
                    memberAddress:{required: true,maxlength:32},
                    weight:{number: true,min:1,max:256},
                    agreementPort:{required: true,min:1,max:65535}
                },
                messages: {
                    resourcePool:{
                        required:"必填"
                    },
                    member: {
                        required:"请选择至少一个成员",
                        maxlength:"输入错误"
                    },
                    memberAddress: {
                        required:"必填"
                    },
                    weight: {
                        number:"请按要求输入",
                        min:"请按要求输入",
                        max:"请按要求输入"
                    },
                    agreementPort: {
                        required:"必填",
                        min:"请按要求输入",
                        max:"请按要求输入"
                    }
                },
                submitHandler:function(form){
                    alert("submitted");
                }
            });
        }
    });
    return addMember;
});