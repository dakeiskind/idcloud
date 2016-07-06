define(['app-utils/httpService','app-utils/messageBoxService','lib/filesaver/FileSaver.min'],function(http,messageBox,fileSaver) {
    var orderService = function() {

        //刷新
        this.refresh = function(){
            http.AjaxRequest({
                url : "/rest/orders/displayPersonalOrderList",
                type: "POST",
                params:null,
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

        this.exportOrder = function(searchObj){
            var json=JSON.stringify(searchObj);
            var url=http.ws_url+"/rest/orders/download/userOrderInfo";
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    var res = this.response;
                    var fileName=this.getResponseHeader('File-Name');
                    var data = new Blob([res]);
                    saveAs(data, fileName);
                }
            }
            xhr.open('POST', url);
            xhr.setRequestHeader('Authorization','Bearer ' + $.cookie('IDC_TOKEN'));
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.responseType = 'arraybuffer';
            xhr.send(json);
        };

    };
    return new orderService();
});