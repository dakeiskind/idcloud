define(['app-utils/messageBoxService','avalon'], function(messageBox) {
    var manageCloudHost = avalon.define({
        $id:'manageCloudHost',
        data:null,
        search: function(){
            messageBox.msgNotification({
                type:"success",
                message:"搜索主机名或内网IP"
            });
        }
    });
});