/**
 * Created by tdz on 2016/3/31.
 */
define(['app-utils/httpService'],function(http) {
    var diskService = function() {
        //获取grid数据
        this.getData = function(){
            var returnData;
            http.AjaxRequest({
                 url : "/rest/dashboard/console",
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
    };
    return new diskService();
});