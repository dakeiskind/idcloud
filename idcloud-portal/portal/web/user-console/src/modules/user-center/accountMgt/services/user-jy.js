define(['app-utils/httpService','app-utils/messageBoxService'], function(http,messageBox){
    var jyService = function() {

        //查询充值明细
        this.searchRecharge = function(obj){
            http.AjaxRequest({
                                 url : "/rest/billingAccount/displayPaymentRecords",
                                 type: "GET",
                                 params:obj,
                                 async:false,
                                 callback : function (data) {
                                     if(data.status == "1"){
                                         messageBox.msgNotification({
                                                                        type:"success",
                                                                        message:"成功!"
                                                                    });
                                     }
                                 }

                             });
        };

        //查询账单明细
        this.searchBilling = function(obj){
            http.AjaxRequest({
                                 url : "/rest/billingAccount/displayBillRecords",
                                 type: "GET",
                                 params:obj,
                                 async:false,
                                 callback : function (data) {
                                     if(data.status == "1"){
                                         messageBox.msgNotification({
                                                                        type:"success",
                                                                        message:"成功!"
                                                                    });
                                     }
                                 }

                             });
        };

    };
    return new  jyService();
});
