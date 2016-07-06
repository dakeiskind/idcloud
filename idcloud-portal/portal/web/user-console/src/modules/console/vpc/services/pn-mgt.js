define([],function() {
    var pnService = function() {

        //获取grid数据
        this.getGridDate = function(){
            return [
                {id:"id-vpcTest-01","vpcName":"vpcTest-01","segmentName":"192.168.0.0/16",segment:"0","createTime":"2014-09-21 12:01:08","description":""},
                {id:"id-vpcTest-02","vpcName":"vpcTest-02","segmentName":"10.0.0.0/8",segment:"1","createTime":"2014-09-21 12:01:08","description":""}
            ];
        };
    };
    return new pnService();
});