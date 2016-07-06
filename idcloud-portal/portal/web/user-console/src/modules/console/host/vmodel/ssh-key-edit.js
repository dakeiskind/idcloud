define(['app-modules/console/host/services/ssh-key','avalon'] , function(sshKeyService){
    var editSSH = avalon.define({
        $id:'editSSH',
        oldKeypairsName:null,
        data:null,
        initValidator:function(){
            $.validator.addMethod("checkName",function(value,element,params){
                if(value == editSSH.oldKeypairsName){
                    return true;
                }
                var result = sshKeyService.checkkeypairsName(value);
                if(result){
                    return true;
                }else{
                    return false;
                }
            });
            $("#editForm").validate({
                onkeyup:function(element){
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

    return editSSH;

});


