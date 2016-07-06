define(['app-utils/validatorService','app-utils/codeService','avalon','validator'], function(validate,code) {
    var attachHostWindow = avalon.define({
      $id:'attachHostWindow',
      data:{},
      initValidator:function(){
          $("#attachHostForm").validate({
             onkeyup: function(element) {
                 $(element).valid();
             },
             rules: {
                 vmIp:{required: true},
             },
             messages: {
                 vmIp:{
                     required:"请选择云主机"
                 }
             },
             submitHandler:function(form){
                 alert("submitted");
             }
         });
      }
  });
    return attachHostWindow;
});
