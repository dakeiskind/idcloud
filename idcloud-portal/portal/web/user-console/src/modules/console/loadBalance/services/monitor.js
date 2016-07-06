define([],function() {
    var monitorService = function() {

        //获取grid数据
        this.getGridDate = function(){
            return [
                {"monitorType":"PING","delay":"10","overtime":"1","maxTryTime":"1","detail":"test1test1test1"},
                {"monitorType":"TCP","delay":"9","overtime":"2","maxTryTime":"2","detail":"test2test2test2"},
                {"monitorType":"HTTP","delay":"8","overtime":"3","maxTryTime":"3","detail":"test3test3test3"},
                {"monitorType":"HTTPS","delay":"7","overtime":"4","maxTryTime":"4","detail":"test4test4test4"},
                {"monitorType":"TCP","delay":"5","overtime":"5","maxTryTime":"5","detail":"test5test5test5"},
            ];
        };
    };
    return new monitorService();
});