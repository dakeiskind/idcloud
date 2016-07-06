define([],function() {
    var diskService = function() {

        //获取grid数据
        this.getGridDate = function(){
            return [
                {"diskId":"0101","diskName":"d-fds121","diskType":"普通云盘40G","diskStatus":"使用中","payType":"按量付费","isUninstall":"不支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0102","diskName":"d-fds121","diskType":"普通1云盘60Gaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","diskStatus":"未使用","payType":"按量付费","isUninstall":"支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0103","diskName":"d-fds121","diskType":"普通云盘40G","diskStatus":"使用中","payType":"按量付费","isUninstall":"支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0104","diskName":"d-fds121","diskType":"高级云盘120G","diskStatus":"未使用","payType":"按量付费","isUninstall":"支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0105","diskName":"d-fds121","diskType":"普通云盘40G","diskStatus":"未使用","payType":"按量付费","isUninstall":"不支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0106","diskName":"d-fds121","diskType":"高级云盘160G","diskStatus":"未使用","payType":"按量付费","isUninstall":"不支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0107","diskName":"0d-fds121101","diskType":"普通云盘40G","diskStatus":"使用中","payType":"按量付费","isUninstall":"支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0108","diskName":"d-fds121","diskType":"普通云盘60G","diskStatus":"使用中","payType":"按量付费","isUninstall":"支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0109","diskName":"d-fds121","diskType":"普通云盘70G","diskStatus":"使用中","payType":"按量付费","isUninstall":"不支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0110","diskName":"d-fds121","diskType":"高级云盘120G","diskStatus":"未使用","payType":"按量付费","isUninstall":"支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0111","diskName":"d-fds121","diskType":"普通云盘40G","diskStatus":"使用中","payType":"按量付费","isUninstall":"不支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0112","diskName":"d-fds121","diskType":"高级云盘150G","diskStatus":"未使用","payType":"按量付费","isUninstall":"支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0113","diskName":"d-fds121","diskType":"普通云盘40G","diskStatus":"使用中","payType":"按量付费","isUninstall":"不支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0114","diskName":"d-fds121","diskType":"普通云盘80G","diskStatus":"未使用","payType":"按量付费","isUninstall":"支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0115","diskName":"d-fds121","diskType":"高级云盘120G","diskStatus":"未使用","payType":"按量付费","isUninstall":"支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0116","diskName":"d-fds121","diskType":"高级云盘150G","diskStatus":"未使用","payType":"按量付费","isUninstall":"支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
                {"diskId":"0117","diskName":"d-fds121","diskType":"高级云盘200G","diskStatus":"使用中","payType":"按量付费","isUninstall":"不支持","isDiskArea":"北京可用区A","diskParam":"系统盘"}
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
    return new diskService();
});