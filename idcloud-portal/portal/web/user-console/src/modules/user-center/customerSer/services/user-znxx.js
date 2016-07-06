define([] ,function(){
    var userZnxxService = function(){
        //获取grid数据
        this.getGridDate = function(){
            return [
                {title:'功能更新——故障告警支持电话通知',messageType:'通知',createdDt:'2016-1-1 00:00:00'},
                {title:'告警版本上线通知',messageType:'产品更新',createdDt:'2016-2-1 10:00:00'},
                {title:'XXXXXXX',messageType:'通知',createdDt:'2016-2-1 10:00:00'},
                {title:'XXXXXXX',messageType:'公告',createdDt:'2016-2-1 10:00:00'},
                {title:'XXXXXXX',messageType:'通知',createdDt:'2016-2-1 10:00:00'},
                {title:'XXXXXXX',messageType:'故障通知',createdDt:'2016-2-1 10:00:00'},
                {title:'告警版本上线通知',messageType:'产品更新',createdDt:'2016-2-1 10:00:00'},
                {title:'fsdfsdfds',messageType:'产品更新',createdDt:'2016-2-1 10:00:00'},
                {title:'似懂非懂所发生的',messageType:'产品更新',createdDt:'2016-2-1 10:00:00'},
                {title:'fdsfdsfsdfds',messageType:'产品更新',createdDt:'2016-2-1 10:00:00'},
                {title:'告警版本上线通知',messageType:'产品更新',createdDt:'2016-2-1 10:00:00'},
                {title:'测试测试',messageType:'产品更新',createdDt:'2016-2-1 10:00:00'}
            ];
        };
    };
    return new userZnxxService();
});