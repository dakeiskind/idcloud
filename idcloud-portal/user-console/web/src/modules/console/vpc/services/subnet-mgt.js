define([],function() {
    var subService = function() {

        //获取grid数据
        this.getGridDate = function(){
            return [
                {"subnetName":"sub20120121","ownerNetworkName":"Test","hostCounts":"1","segmentName":"192.168.224.0/19",segment:"3","useableIpCounts":"2","createTime":"2014-09-21 12:01:08","description":""},
                {"subnetName":"sub20120122","ownerNetworkName":"Test","hostCounts":"1","segmentName":"192.168.248.0/21",segment:"5","useableIpCounts":"2","createTime":"2014-09-21 12:01:08","description":""},
                {"subnetName":"sub20120123","ownerNetworkName":"Test","hostCounts":"1","segmentName":"192.168.254.0/23",segment:"7","useableIpCounts":"2","createTime":"2014-09-21 12:01:08","description":"测试是滴是滴萨"},
                {"subnetName":"sub20120124","ownerNetworkName":"Test","hostCounts":"1","segmentName":"192.168.255.128/25",segment:"9","useableIpCounts":"2","createTime":"2014-09-21 12:01:08","description":""},
                {"subnetName":"sub20120125","ownerNetworkName":"Test","hostCounts":"1","segmentName":"192.168.255.248/29",segment:"13","useableIpCounts":"2","createTime":"2014-09-21 12:01:08","description":""},
                {"subnetName":"sub20120126","ownerNetworkName":"Test","hostCounts":"1","segmentName":"192.168.255.254/31",segment:"15","useableIpCounts":"2","createTime":"2014-09-21 12:01:08","description":""}
            ];
        };
    };
    return new subService();
});