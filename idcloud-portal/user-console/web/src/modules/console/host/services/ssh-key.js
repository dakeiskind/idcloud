define(['app-utils/httpService',
        'app-utils/messageBoxService','layer'],function(http,messageBox,layer){

    var sshKeyService = function(){
        this.createSSHKey = function(params){
            //var index = layer.load(1, {
            //    shade: [0.1,'#000']
            //});
            http.AjaxRequest({
                url : "/rest/keypairs/creatKeypairs",
                type : "POST",
                params:params,
                async:false,
                showMsg:false,
                callback : function (data) {
                    //layer.close(index);
                    if(data != null){
                        messageBox.msgNotification({
                            type:"success",
                            message:"创建成功，请保存好您的私钥！"
                        });

                        var url = http.ws_url+"/rest/keypairs/download/exportprivateKeys/"+data;
                        var form=$("<form>");//定义一个form表单
                        form.attr("style","display:none");
                        //form.attr("target","_blank");
                        form.attr("method","GET");
                        form.attr("action",url);
                        form.submit();
                    }
                }
            });
        };
        this.deleteSSHs = function(params){
            var result = false;
            if(params != null && params != ""){
                http.AjaxRequest({
                    url : "/rest/keypairs/deleteKeypairs",
                    type : "POST",
                    params:params,
                    async:false,
                    showMsg:false,
                    callback : function (data) {
                        if(data){
                            result = data;
                            messageBox.msgNotification({
                                type:"success",
                                message:"删除成功"
                            });
                        }
                    }
                })
            }else{
                messageBox.msgNotification({
                    type:"warning",
                    message:"请选择需要删除的密钥！"
                });
            }
            return result;
        };
        this.modifySSH = function(params){
            var result = false;
            http.AjaxRequest({
                url : "/rest/keypairs/modifyKeypairs",
                type : "POST",
                params:params,
                async:false,
                showMsg:false,
                callback : function (data) {
                    if(data){
                        result = data;
                        messageBox.msgNotification({
                            type:"success",
                            message:"修改成功"
                        });
                    }
                }
            });
            return result;
        };
        this.ImportSSH = function(params){
            var result = false;
            http.AjaxRequest({
                url : "/rest/keypairs/importKeypairs",
                type : "POST",
                params:params,
                async:false,
                //showMsg:false,
                callback : function (data) {
                }
            });
            return result;
        };
        this.checkkeypairsName = function(params){
            var result = false;
            http.AjaxRequest({
                url : "/rest/keypairs/checkKeypairs/"+params,
                type : "GET",
                async:false,
                showMsg:false,
                callback : function (data) {
                    if(data){
                        result = data;
                    }
                }
            });
            return result;
        };
        this.exportAll = function(){
            var index = 0;
            http.AjaxRequest({
                url : "/rest/keypairs/download/exportAllKeypairs/"+ $.cookie("USER_SID"),
                type : "GET",
                async:false,
                showMsg:false,
                callback : function (data) {
                    if(!data){
                        index = 1;//如果接口有返回值则不进行下载
                        messageBox.msgNotification({
                            type:"error",
                            message:"导出失败！"
                        });
                    }
                }
            });
            if(index == 0){
                var url = http.ws_url + "/rest/keypairs/download/exportAllKeypairs/"+ $.cookie("USER_SID");
                var form=$("<form>");//定义一个form表单
                form.attr("style","display:none");
                //form.attr("target","_blank");
                form.attr("method","GET");
                form.attr("action",url);
                form.submit();
            }
        }
    };
    return new sshKeyService();
});