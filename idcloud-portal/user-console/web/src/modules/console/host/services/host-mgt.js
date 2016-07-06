define([],function() {
    var hostService = function() {

        //获取grid数据
        this.getGridDate = function(){
            return [
                {"hostName":"01qwqqw01","monitor":"&","availableArea":"重庆可用区A","addressIP":"127.0.0.2（私有）","state":"运行中","networkType":"专有网络","configuration":"CPU：1核  内存：1024MB","payWay":"按量"},
                {"hostName":"01qwqqw02","monitor":"&","availableArea":"重庆可用区B","addressIP":"127.0.0.2（私有）","state":"运行中","networkType":"专有网络","configuration":"CPU：1核  内存：1024MB","payWay":"按量"},
                {"hostName":"01qwqqw03","monitor":"&","availableArea":"重庆可用区C","addressIP":"127.0.0.2（私有）","state":"运行中","networkType":"专有网络","configuration":"CPU：1核  内存：1024MB","payWay":"按量"},
                {"hostName":"01qwqqw01","monitor":"&","availableArea":"重庆可用区A","addressIP":"127.0.0.2（私有）","state":"运行中","networkType":"专有网络","configuration":"CPU：1核  内存：1024MB","payWay":"按量"},
                {"hostName":"01qwqqw01","monitor":"&","availableArea":"重庆可用区A","addressIP":"127.0.0.2（私有）","state":"运行中","networkType":"专有网络","configuration":"CPU：1核  内存：1024MB","payWay":"按量"},
                {"hostName":"01qwqqw01","monitor":"&","availableArea":"重庆可用区A","addressIP":"127.0.0.2（私有）","state":"运行中","networkType":"专有网络","configuration":"CPU：1核  内存：1024MB","payWay":"按量"},
                {"hostName":"01qwqqw01","monitor":"&","availableArea":"重庆可用区A","addressIP":"127.0.0.2（私有）","state":"运行中","networkType":"专有网络","configuration":"CPU：1核  内存：1024MB","payWay":"按量"},
                {"hostName":"01qwqqw01","monitor":"&","availableArea":"重庆可用区A","addressIP":"127.0.0.2（私有）","state":"运行中","networkType":"专有网络","configuration":"CPU：1核  内存：1024MB","payWay":"按量"},
                {"hostName":"01qwqqw01","monitor":"&","availableArea":"重庆可用区A","addressIP":"127.0.0.2（私有）","state":"运行中","networkType":"专有网络","configuration":"CPU：1核  内存：1024MB","payWay":"按量"},
                {"hostName":"01qwqqw01","monitor":"&","availableArea":"重庆可用区A","addressIP":"127.0.0.2（私有）","state":"运行中","networkType":"专有网络","configuration":"CPU：1核  内存：1024MB","payWay":"按量"},
                {"hostName":"01qwqqw01","monitor":"&","availableArea":"重庆可用区A","addressIP":"127.0.0.2（私有）","state":"运行中","networkType":"专有网络","configuration":"CPU：1核  内存：1024MB","payWay":"按量"},
                {"hostName":"01qwqqw01","monitor":"&","availableArea":"重庆可用区A","addressIP":"127.0.0.2（私有）","state":"运行中","networkType":"专有网络","configuration":"CPU：1核  内存：1024MB","payWay":"按量"},

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
    return new hostService();
});