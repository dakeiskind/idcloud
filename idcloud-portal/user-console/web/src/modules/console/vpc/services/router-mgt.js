define([],function() {
    var pnService = function() {

        //获取grid数据
        this.getGridDate = function(){
            return [
                {"routerId":"2008230201212","routerName":"路由器1","ownerNetwork":"vpcTest20150230","segment":"0","createTime":"2014-09-21 12:01:08","description":"2323sd"},
                {"routerId":"2123232343425","routerName":"路由器2","ownerNetwork":"vpcTest20160223","segment":"1","createTime":"2014-09-21 12:01:08","description":"sdsdsd"},
               
            ];
        };
    };
    return new pnService();
});