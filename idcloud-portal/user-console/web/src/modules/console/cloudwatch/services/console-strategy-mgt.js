define([],function() {
    var strategyMgtService = function() {

        //获取grid数据
        this.getGridDate = function(){
            return [
                {"strategyName":"test","strategyType":"云服务器策略","used":"0","editTime":"2016-2-23","desc":"对该策略进行描述"},
                {"strategyName":"复制test","strategyType":"云服务器策略","used":"0","editTime":"2016-1-23","desc":"-----"},
                {"strategyName":"test","strategyType":"云服务器策略","used":"0","editTime":"2016-2-23","desc":"对该策略进行描述"},
                {"strategyName":"复制test","strategyType":"云服务器策略","used":"0","editTime":"2016-1-23","desc":"-----"},
                {"strategyName":"test","strategyType":"云服务器策略","used":"0","editTime":"2016-2-23","desc":"对该策略进行描述"},
                {"strategyName":"复制test","strategyType":"云服务器策略","used":"0","editTime":"2016-1-23","desc":"-----"},
                {"strategyName":"test","strategyType":"云服务器策略","used":"0","editTime":"2016-2-23","desc":"对该策略进行描述"},
                {"strategyName":"复制test","strategyType":"云服务器策略","used":"0","editTime":"2016-1-23","desc":"-----"},
                {"strategyName":"test","strategyType":"云服务器策略","used":"0","editTime":"2016-2-23","desc":"对该策略进行描述"},
                {"strategyName":"复制test","strategyType":"云服务器策略","used":"0","editTime":"2016-1-23","desc":"-----"},
                {"strategyName":"test","strategyType":"云服务器策略","used":"0","editTime":"2016-2-23","desc":"对该策略进行描述"},
                {"strategyName":"复制test","strategyType":"云服务器策略","used":"0","editTime":"2016-1-23","desc":"-----"},
                {"strategyName":"test","strategyType":"云服务器策略","used":"0","editTime":"2016-2-23","desc":"对该策略进行描述"},
                {"strategyName":"复制test","strategyType":"云服务器策略","used":"0","editTime":"2016-1-23","desc":"-----"},
                {"strategyName":"test","strategyType":"云服务器策略","used":"0","editTime":"2016-2-23","desc":"对该策略进行描述"},
                {"strategyName":"复制test","strategyType":"云服务器策略","used":"0","editTime":"2016-1-23","desc":"-----"},
                {"strategyName":"test","strategyType":"云服务器策略","used":"0","editTime":"2016-2-23","desc":"对该策略进行描述"},
                {"strategyName":"复制test","strategyType":"云服务器策略","used":"0","editTime":"2016-1-23","desc":"-----"}
            ];
        };

        //获取告警管理规则的grid数据
        this.getRuleGridData = function(){
            return [
                {"monitoriItems":"磁盘使用量","condition":">","threshold":"90%","duration":"1"},
                {"monitoriItems":"CPU利用率","condition":">","threshold":"90%","duration":"15"},
                {"monitoriItems":"内存利用率","condition":">","threshold":"90%","duration":"20"}
            ]
        };

        //获取告警管理关联的grid数据
        this.getRelationGridData = function(){
            return [
                {"relationName":"外网test1","insiderNet":"10.20.0.4","netWork":"测试网络1","operation":"激活"}
            ]
        };

        //告警通知
        this.getNoticeGridData = function(){
            return [
                {"alarmCG":"test","alarmContacts":"Vify","noticeMethod":"邮件，短信"}
            ]
        };
    };
    return new strategyMgtService();
});


