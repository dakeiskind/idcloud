/**
 * Created by Administrator on 2016/1/28.
 */
define([],function() {
    var resourcePoolService = function() {

        //获取grid数据
        this.getGridDate = function(){
            return [
                {"loadBalanceName":"test1","describe":"test1test1test","provider":"haproxy","subnet":"public-subnet 192.168.9.0/24","agreement":"HTTP","LBMode":"ROUND_ROBIN","status":"运行中","VIP":"-"},
                {"loadBalanceName":"test2","describe":"test2test2test","provider":"user","subnet":"public-subnet 192.168.9.0/24","agreement":"HTTPS","LBMode":"LEAST_CONNECTIONS","status":"已关闭","VIP":"-"},
                {"loadBalanceName":"test3","describe":"test3test3test","provider":"root","subnet":"public-subnet 192.1.9.0/24","agreement":"TCP","LBMode":"ROUND_ROBIN","status":"运行中","VIP":"-"},
                {"loadBalanceName":"test4","describe":"test4test4test","provider":"superuser","subnet":"public-subnet 192.2.9.0/24","agreement":"HTTPS","LBMode":"SOURCE_IP","status":"已关闭","VIP":"-"},
                {"loadBalanceName":"test5","describe":"test5test5test","provider":"haproxy","subnet":"public-subnet 172.168.9.0/24","agreement":"TCP","LBMode":"LEAST_CONNECTIONS","status":"运行中","VIP":"-"},
            ];
        };
    };
    return new resourcePoolService();
});