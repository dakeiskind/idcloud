define(['app-utils/httpService','app-utils/messageBoxService'],function(http,messageBox) {
    var subUserService = function() {
        //添加子用户
        this.add = function(obj){
           http.AjaxRequest({
               url:"/rest/user/insertUser",
               type:"POST",
               params:obj,
               async:false,
               callback:function(data){
                   if (data){
                       messageBox.msgNotification({
                                                      type:"success",
                                                      message:"增加用户成功"
                                                  });
                   }
               }
                            });
        };
        this.edit = function(data){
            alert(data);
        };
        this.move = function(userSid){
            http.AjaxRequest({
                url:"/rest/user/deleteUser?userSids="+userSid,
                type:"get",
                callback:function(data){
                    if (data){
                        messageBox.msgNotification({
                                                       type:"success",
                                                       message:"删除用户成功"
                                                   });
                    }
                }
                             });
        };
    };
    return new subUserService();
});
