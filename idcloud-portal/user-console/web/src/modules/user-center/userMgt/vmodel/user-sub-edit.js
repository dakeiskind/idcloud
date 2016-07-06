define(['avalon'],function(){
   var subUserEdit = avalon.define({
       $id:'subUserEdit',
       editSubUserData:null,
       initValidator:function(){
           //验证格式
           $("#editSubForm").validate({
               onkeyup: function(element) {
                   $(element).valid();
               },
               rules:{
                   subName:{required: true},
                   subPhone:{required: true, number:true,minlength: 2},
                   subEmail:{required: true,email:true},
                   subPwd:{required: true},
                   confirmPwd:{required: true,equalTo:"#subPwd"}
               },
               messages: {
                   subName: {
                       required:"必填项"
                   },
                   subPhone:{
                       required:"必填项",
                       number:"输入合法的数字",
                       minlength: '电话为11位'
                   },
                   subEmail:{
                       required:"必填项",
                       email:"输入正确格式的电子邮件"
                   },
                   subPwd:{
                       required:"必填项"
                   },
                   confirmPwd:{
                       required:"必填项",
                       equalTo:"输入值必须和登陆密码相同"
                   }
               }
           })
       }
   });

    return subUserEdit;
});
