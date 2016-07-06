define(['app-modules/console/host/services/ssh-key','avalon'], function(sshKeyService){
    var ImportSSH = avalon.define({
        $id:'ImportSSH',
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
            $("#ImportForm").validate({
                onfocusout:function(element){
                    $(element).valid();
                },
                rules:{
                    keypairsName:{required: true,minlength:2,maxlength:128,checkName:true},
                    publicKey:{required: true}
                },
                messages: {
                    keypairsName: {
                        required: "必填",
                        maxlength:"请输入2-128长度的字符",
                        minlength:"请输入2-128长度的字符",
                        checkName:"请勿输入重复名称"
                    },
                    publicKey: {
                        required: "必填"
                    }
                }
            });
        }
    });
    return ImportSSH;
});