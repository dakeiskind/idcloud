define(['app-utils/httpService','app-utils/messageBoxService'] ,function(http,messageBox){
    var userFp = function() {

        this.addInvoice = function(params1,params2){
            var result = false;
            http.AjaxRequest({
                url : "/rest/invoices/create",
                type: "POST",
                params:{
                    invoice:params1,
                    invoiceItem:params2
                },
                async:false,
                showMsg:false,
                callback : function (data) {
                    if(data == 'SUCCESS'){
                        result = true;
                    }else{
                        messageBox.msgNotification({
                            type:"error",
                            message:"申请发票失败！"
                        });
                    }
                }
            });
            return result;
        }
    };
    return new  userFp();
});

