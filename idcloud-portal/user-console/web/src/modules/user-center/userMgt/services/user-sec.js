define(['app-utils/httpService'],function(http) {
    var userSecService = function() {
        //修改
        this.edit = function(obj){
            http.AjaxRequest({
                                 url:"/rest/user/updateUserInfo",
                                 type:"POST",
                                 params:obj,
                                 async:false,
                                 callback:function(data){}
                             });
        };
    };
    return new userSecService();
});
