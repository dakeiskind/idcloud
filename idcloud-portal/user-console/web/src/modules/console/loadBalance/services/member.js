define([],function() {
    var memberService = function() {
        //获取grid数据
        this.getGridDate = function(){
            return [
                {"IPAddress":"192.168.9.0.0","agreementPort":"80","weight":"1","resourcePool":"test","status":"运行中",resourcePool:"test1"},
                {"IPAddress":"192.172.8.0.1","agreementPort":"60","weight":"2","resourcePool":"test","status":"已关闭",resourcePool:"test2"},
                {"IPAddress":"192.168.7.0.2","agreementPort":"8080","weight":"3","resourcePool":"test","status":"运行中",resourcePool:"test3"},
                {"IPAddress":"192.171.6.0.3","agreementPort":"40","weight":"4","resourcePool":"test","status":"已关闭",resourcePool:"test4"},
                {"IPAddress":"192.168.5.0.4","agreementPort":"8888","weight":"5","resourcePool":"test","status":"已关闭",resourcePool:"test5"},
            ];
        };
    };
    return new memberService();
});