define(['app-utils/httpService'],function(http) {
    var diskService = function() {
        var userData = {};
        //获取grid数据
        this.getData = function(){
            var returnData;
            http.AjaxRequest({
                url : "/rest/dashboard/user",
                type : "GET",
                async : false,
                callback : function (data) {
                    returnData = data;
                    userData.account = data.user.account;
                    userData.mobile = data.user.mobile;
                    userData.realName = data.user.realName;
                }
            });
            return returnData;
        };
        this.getCharDate = function(){
            var returnData;
            http.AjaxRequest({
                url : "/rest/dashboard/user/line/balance/"+$("#dashboardChart").val(),
                type : "GET",
                async : false,
                callback : function (data) {
                    returnData = data;
                }
            });
            return returnData;
        };
        this.getLogData = function(){
            var returnData;
            http.AjaxRequest({
                url:"/rest/logs/findLogs",
                type:"GET",
                async:false,
                callback:function(data){
                    returnData = data.dataList;
                }
            });
            return returnData;
        };
        this.getUserData = function(){
            return userData;
        };
    };
    return new diskService();
});