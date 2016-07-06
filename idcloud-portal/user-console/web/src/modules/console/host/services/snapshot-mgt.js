define([],function() {
    var snapshotService = function() {

        //获取grid数据
        this.getGridDate = function(){
            return [
                {"snapshotName":"01qwqqw01","diskID":"qwqwqw","diskCapacity":"40GB","diskAttr":"系统盘","creatTime":"2016-01-09 22:09:22","status":"完成","process":"100%"},
                {"snapshotName":"01qwqqw02","diskID":"qwqwqw","diskCapacity":"40GB","diskAttr":"系统盘","creatTime":"2016-01-09 22:09:22","status":"完成","process":"100%"},
                {"snapshotName":"01qwqqw03","diskID":"qwqwqw","diskCapacity":"40GB","diskAttr":"系统盘","creatTime":"2016-01-09 22:09:22","status":"完成","process":"100%"},
                {"snapshotName":"01qwqqw04","diskID":"qwqwqw","diskCapacity":"40GB","diskAttr":"系统盘","creatTime":"2016-01-09 22:09:22","status":"完成","process":"100%"},
                {"snapshotName":"01qwqqw05","diskID":"qwqwqw","diskCapacity":"40GB","diskAttr":"系统盘","creatTime":"2016-01-09 22:09:22","status":"完成","process":"100%"},
                {"snapshotName":"01qwqqw06","diskID":"qwqwqw","diskCapacity":"40GB","diskAttr":"系统盘","creatTime":"2016-01-09 22:09:22","status":"完成","process":"100%"},
                {"snapshotName":"01qwqqw07","diskID":"qwqwqw","diskCapacity":"40GB","diskAttr":"系统盘","creatTime":"2016-01-09 22:09:22","status":"完成","process":"100%"},
                {"snapshotName":"01qwqqw08","diskID":"qwqwqw","diskCapacity":"40GB","diskAttr":"系统盘","creatTime":"2016-01-09 22:09:22","status":"完成","process":"100%"},
                {"snapshotName":"01qwqqw09","diskID":"qwqwqw","diskCapacity":"40GB","diskAttr":"系统盘","creatTime":"2016-01-09 22:09:22","status":"完成","process":"100%"},
                {"snapshotName":"01qwqqw10","diskID":"qwqwqw","diskCapacity":"40GB","diskAttr":"系统盘","creatTime":"2016-01-09 22:09:22","status":"完成","process":"100%"},
                {"snapshotName":"01qwqqw11","diskID":"qwqwqw","diskCapacity":"40GB","diskAttr":"系统盘","creatTime":"2016-01-09 22:09:22","status":"完成","process":"100%"},
                {"snapshotName":"01qwqqw01","diskID":"qwqwqw","diskCapacity":"40GB","diskAttr":"系统盘","creatTime":"2016-01-09 22:09:22","status":"完成","process":"100%"},

            ];
        };

        this.add = function(){
            alert("add");
        };
        this.edit = function(data){
            alert(data);
        };
        this.remove = function(data){
            alert(data);
        };
    };
    return new snapshotService();
});