define(['avalon'],function(){
    var addRole = avalon.define({
        $id:'addRole',
        data:{},
        addCB:function(obj){
            if($(obj).val() == ""){
                $(obj).val("add");
                $(".addIpt").attr("checked", true);
                return
            }else{
                $(obj).val("");
                $(".addIpt").removeAttr("checked");
            }
        },
        deleteCB:function(obj){
            if($(obj).val() == ""){
                $(obj).val("delete");
                $(".deleteIpt").attr("checked", true);
                return
            }
            if($(obj).val() == "delete"){
                $(obj).val("");
                $(".deleteIpt").removeAttr("checked");
                return
            }
        },
        modifyCB:function(obj){
            if($(obj).val() == ""){
                $(obj).val("modify");
                $(".modifyIpt").attr("checked", true);
                return
            }
            if($(obj).val() == "modify"){
                $(obj).val("");
                $(".modifyIpt").removeAttr("checked");
                return
            }
        },
        checkCB:function(obj){
            if($(obj).val() == ""){
                $(obj).val("check");
                $(".checkIpt").attr("checked", true);
                return
            }
            if($(obj).val() == "check"){
                $(obj).val("");
                $(".checkIpt").removeAttr("checked");
                return
            }
        },

        initValidator:function(){
            //验证格式
            $("#addRoleForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules:{
                    roleID:{required: true,maxlength:"32"}
                },
                messages: {
                    roleID:{
                        required:"必填项",
                        maxlength:"请输入字符长度小于等于32位的名称"
                    }
                }
            })
        }
    });

    return addRole;
});
