define([''], function(){
    var consoleJx = function() {

        //json数据
        this.getData = function(){
            return[
                        {"mirrName":"m-25nocsgvc","mirrType":"自定义镜像","description":"测试用的镜像","platform":"Ubuntu","byte":"64位","creatTime":"2016-01-17 22:28:30","status":"可用","process":"80%"},
                        {"mirrName":"L-5nocsgvc","mirrType":"自定义镜像","description":"该镜像用于创建CentOS7","platform":"CentOS","byte":"64位","creatTime":"2016-01-27 22:28:30","status":"不可用","process":"0%"},
                        {"mirrName":"X-20nocsgvc","mirrType":"自定义镜像","description":"该镜像用于安装服务器","platform":"Debian","byte":"32位","creatTime":"2016-01-10 22:28:30","status":"可用","process":"100%"},
                        {"mirrName":"A-25nocsgvc","mirrType":"自定义镜像","description":"用于测试Windows系列的服务器","platform":"windows2008-server","byte":"64位","creatTime":"2015-01-17 22:28:30","status":"不可用","process":"100%"},
                        {"mirrName":"Z-5nocsgvc","mirrType":"自定义镜像","description":"测试用的镜像","platform":"Mac OS","byte":"64位","creatTime":"2016-01-10 22:28:30","status":"可用","process":"80%"},
                        {"mirrName":"D-2nocsgvc","mirrType":"自定义镜像","description":"测试用的镜像","platform":"Ubuntu","byte":"64位","creatTime":"2016-01-17 22:28:30","status":"可用","process":"80%"},
                        {"mirrName":"K-25nocsgvc","mirrType":"自定义镜像","description":"测试用的镜像","platform":"Linux","byte":"64位","creatTime":"2016-01-17 22:28:30","status":"可用","process":"50%"},
                        {"mirrName":"m-25nocsgvc","mirrType":"自定义镜像","description":"测试用的镜像","platform":"Ubuntu","byte":"64位","creatTime":"2016-01-17 22:28:30","status":"可用","process":"80%"},
                        {"mirrName":"U-25nocsgvc","mirrType":"自定义镜像","description":"测试用的镜像","platform":"windows10","byte":"64位","creatTime":"2016-01-17 22:28:30","status":"可用","process":"80%"},
                        {"mirrName":"m-25nocsgvc","mirrType":"自定义镜像","description":"测试用的镜像","platform":"Ubuntu","byte":"64位","creatTime":"2016-01-17 22:28:30","status":"可用","process":"80%"},
                        {"mirrName":"L-5nocsgvc","mirrType":"自定义镜像","description":"该镜像用于创建CentOS7","platform":"CentOS","byte":"64位","creatTime":"2016-01-27 22:28:30","status":"不可用","process":"0%"},
                        {"mirrName":"X-20nocsgvc","mirrType":"自定义镜像","description":"该镜像用于安装服务器","platform":"Debian","byte":"32位","creatTime":"2016-01-10 22:28:30","status":"可用","process":"100%"},
                        {"mirrName":"A-25nocsgvc","mirrType":"自定义镜像","description":"用于测试Windows系列的服务器","platform":"windows2008-server","byte":"64位","creatTime":"2015-01-17 22:28:30","status":"不可用","process":"100%"},
                        {"mirrName":"Z-5nocsgvc","mirrType":"自定义镜像","description":"测试用的镜像","platform":"Mac OS","byte":"64位","creatTime":"2016-01-10 22:28:30","status":"可用","process":"80%"},
                        {"mirrName":"D-2nocsgvc","mirrType":"自定义镜像","description":"测试用的镜像","platform":"Ubuntu","byte":"64位","creatTime":"2016-01-17 22:28:30","status":"可用","process":"80%"},
                        {"mirrName":"K-25nocsgvc","mirrType":"自定义镜像","description":"测试用的镜像","platform":"Linux","byte":"64位","creatTime":"2016-01-17 22:28:30","status":"可用","process":"50%"},
                        {"mirrName":"m-25nocsgvc","mirrType":"自定义镜像","description":"测试用的镜像","platform":"Ubuntu","byte":"64位","creatTime":"2016-01-17 22:28:30","status":"可用","process":"80%"},
                        {"mirrName":"U-25nocsgvc","mirrType":"自定义镜像","description":"测试用的镜像","platform":"windows10","byte":"64位","creatTime":"2016-01-17 22:28:30","status":"可用","process":"80%"}
            ]
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
  return new  consoleJx();
});
