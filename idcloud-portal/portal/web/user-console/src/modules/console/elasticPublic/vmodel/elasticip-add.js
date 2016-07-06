/**
 * Created by Tdz on 2016/2/2.
 */
define(['app-utils/validatorService','app-utils/codeService','avalon','validator'], function(validate,code) {
    var addElasticipWindow = avalon.define({
        $id:'addElasticipWindow',
        data:{},
        hostData:[],
        hostIpData:[],
        initValidator:function(){
            $("#elasticipForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    hostValue:{required: true},
                },
                messages: {
                    hostValue:{
                        required:"请选择云主机"
                    }
                },
                submitHandler:function(form){
                    alert("submitted");
                }
            });
        },
        getIp:function(){
            $("#hostValue").change(function(){
                addElasticipWindow.hostIpData=code.getCustomCode("/cs","POST",{instanceSid:$("#hostValue").val()});

            });
        }
    });
    console.log(addElasticipWindow.hostData)
    return addElasticipWindow;
});