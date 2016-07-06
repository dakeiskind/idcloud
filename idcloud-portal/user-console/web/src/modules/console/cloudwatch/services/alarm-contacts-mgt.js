define([''], function(){
    var alarmContactsService = function() {

        //json数据
        this.getData = function(){
            return[
                {"contactName":"dennis","contactPhone":"18325198525","contactEmail":"wcdennis@sina.cn","contactGroup":"user"},
                {"contactName":"xh","contactPhone":"18325198525","contactEmail":"wcdennis@sina.cn","contactGroup":"sub-user"},
                {"contactName":"cco","contactPhone":"18325198525","contactEmail":"wcdennis@sina.cn","contactGroup":"self "},
                {"contactName":"con","contactPhone":"18325198525","contactEmail":"wcdennis@sina.cn","contactGroup":"parent "},
                {"contactName":"dennis","contactPhone":"18325198525","contactEmail":"wcdennis@sina.cn","contactGroup":"child"},
                {"contactName":"sre","contactPhone":"18325198525","contactEmail":"wcdennis@sina.cn","contactGroup":"childs"}
            ]
        };
        this.getGroupData = function(){
            return [
                {"groupName":"分组1","groupDes":"描述"},
                {"groupName":"分组2","groupDes":"描述"},
                {"groupName":"分组3","groupDes":"描述"},
                {"groupName":"分组4","groupDes":"描述"},
                {"groupName":"分组5","groupDes":"描述"},
                {"groupName":"分组6","groupDes":"描述"},
            ];
        }
    };
    return new  alarmContactsService();
});


