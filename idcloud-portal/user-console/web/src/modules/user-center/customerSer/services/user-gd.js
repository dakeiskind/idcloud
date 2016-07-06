define(['app-utils/httpService','app-utils/messageBoxService','jquery-fileupload'] ,function(http,messageBox,fileupload){
    var userGdService = function(){

        this.createdGd = function(paramObj){
            paramObj.tempPath = $("#filePathUrl").val();
            http.AjaxRequest({
                url : "/rest/issue/create",
                type: "POST",
                params:paramObj,
                async:false,
                callback : function (data) {
                    if(data){
                        messageBox.msgNotification({
                            type:"success",
                            message:"提交工单成功!"
                        });
                    }
                }

            });
        };

        this.cancelGd = function(issue){
            http.AjaxRequest({
                url : "/rest/issue/update",
                type: "POST",
                params:issue,
                async:false,
                callback : function (data) {
                    if(data){
                        messageBox.msgNotification({
                            type:"success",
                            message:"已取消!"
                        });
                    }
                }

            });
        };
    };
    return new userGdService();
});