/**
 * Created by Administrator on 2016/1/29.
 */
define([],function() {
    var elasticipServices = function() {

        //获取grid数据
        this.getGridDate = function(){
            return [
                {"instanceID":"rea-01010","addressIP":"127.0.0.2","monitor":">","bandwidth":"按使用流量计费1Mbps","state":"不可用","bindingInstance":"","stateInstance":"","time":"2016-01-27 09:04:32"},
                {"instanceID":"rea-01010","addressIP":"127.0.0.1","monitor":">","bandwidth":"按使用流量计费1Mbps","state":"可用","bindingInstance":"","stateInstance":"","time":"2016-01-27 09:04:32"},
                {"instanceID":"rea-01010","addressIP":"127.0.0.1","monitor":">","bandwidth":"按使用流量计费1Mbps","state":"可用","bindingInstance":"","stateInstance":"","time":"2016-01-27 09:04:32"},
                {"instanceID":"rea-01010","addressIP":"127.0.0.1","monitor":">","bandwidth":"按使用流量计费1Mbps","state":"可用","bindingInstance":"","stateInstance":"","time":"2016-01-27 09:04:32"},
                {"instanceID":"rea-01010","addressIP":"127.0.0.1","monitor":">","bandwidth":"按使用流量计费1Mbps","state":"可用","bindingInstance":"","stateInstance":"","time":"2016-01-27 09:04:32"},
                {"instanceID":"rea-01010","addressIP":"127.0.0.1","monitor":">","bandwidth":"按使用流量计费1Mbps","state":"可用","bindingInstance":"","stateInstance":"","time":"2016-01-27 09:04:32"},
                {"instanceID":"rea-01010","addressIP":"127.0.0.1","monitor":">","bandwidth":"按使用流量计费1Mbps","state":"可用","bindingInstance":"","stateInstance":"","time":"2016-01-27 09:04:32"},
                {"instanceID":"rea-01010","addressIP":"127.0.0.1","monitor":">","bandwidth":"按使用流量计费1Mbps","state":"可用","bindingInstance":"","stateInstance":"","time":"2016-01-27 09:04:32"},
                {"instanceID":"rea-01010","addressIP":"127.0.0.1","monitor":">","bandwidth":"按使用流量计费1Mbps","state":"可用","bindingInstance":"","stateInstance":"","time":"2016-01-27 09:04:32"},
                {"instanceID":"rea-01010","addressIP":"127.0.0.1","monitor":">","bandwidth":"按使用流量计费1Mbps","state":"可用","bindingInstance":"","stateInstance":"","time":"2016-01-27 09:04:32"},
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
    return new elasticipServices();
});