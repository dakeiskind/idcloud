/**
 * Created by Administrator on 2016/4/18.
 */
define(['app-utils/validatorService','app-utils/codeService','avalon','validator'], function(validate,code) {
    var hostEipWindow = avalon.define({
       $id:'hostEipWindow',
       data:{},
       eipData:[],
       hostIP:[],
       initValidator:function(){
           $("#hostEipForm").validate({
            onkeyup: function(element) {
                $(element).valid();
            },
            rules: {
                vmIp:{required: true},
                eipIp:{required: true},
            },
            messages: {
                vmIp:{
                    required:"请选择云主机IP"
                },
                eipIp:{
                    required:"请选择弹性公网IP"
                }
            },
            submitHandler:function(form){
                alert("submitted");
            }
        });
       }
   });
    return hostEipWindow;
});