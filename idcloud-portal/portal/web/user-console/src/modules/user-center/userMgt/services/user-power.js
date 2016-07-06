define([],function() {

    var userPowerService = function() {

        //获取grid数据
        this.getGridDate = function(){
            return [
                {"role":"admin",Des:"test1"},
                {"role":"superRoot",Des:"test2"}
            ];
        };
    };
    return new userPowerService();
});
