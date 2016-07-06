define(['app-modules/console/host/services/ssh-key','avalon'], function(sshKeyService){
    var addSsh = avalon.define({
        $id:'addSsh',
        data:{},
        initValidator:function(){
            $.validator.addMethod("checkName",function(value,element,params){
                var result = sshKeyService.checkkeypairsName(value);
                if(result){
                    return true;
                }else{
                    return false;
                }
            });
            $("#addSshForm").validate({
                onfocusout:function(element){
                    $(element).valid();
                },
                rules:{
                    keypairsName:{required: true,minlength:2,maxlength:128,checkName:true},
                    description:{maxlength:255}
                },
                messages: {
                    keypairsName: {
                        required: "必填",
                        maxlength:"请输入2-128长度的字符",
                        minlength:"请输入2-128长度的字符",
                        checkName:"请勿输入重复名称"
                    },
                    description: {
                        maxlength:"请输入0-255长度的字符"
                    }
                }
            });
        }
    });
    return addSsh;
});