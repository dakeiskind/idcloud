define([],function() {
    var userService = function() {

        this.getData = function(){
            return[
                {"lpkName":"尚品","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2016-01-01","lpkEnd":"2016-02-05","lpkStatus":"已充值","useTime":"2016-02-01"},
                {"lpkName":"美幻","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2015-12-01","lpkEnd":"2016-01-05","lpkStatus":"已过期","useTime":"2016-01-01"},
                {"lpkName":"尚品","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2016-01-01","lpkEnd":"2016-02-05","lpkStatus":"已充值","useTime":"2016-02-01"},
                {"lpkName":"美幻","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2015-12-01","lpkEnd":"2016-01-05","lpkStatus":"已过期","useTime":"2016-01-01"},
                {"lpkName":"尚品","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2016-01-01","lpkEnd":"2016-02-05","lpkStatus":"已充值","useTime":"2016-02-01"},
                {"lpkName":"美幻","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2015-12-01","lpkEnd":"2016-01-05","lpkStatus":"已过期","useTime":"2016-01-01"},
                {"lpkName":"尚品","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2016-01-01","lpkEnd":"2016-02-05","lpkStatus":"已充值","useTime":"2016-02-01"},
                {"lpkName":"美幻","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2015-12-01","lpkEnd":"2016-01-05","lpkStatus":"已过期","useTime":"2016-01-01"},
                {"lpkName":"尚品","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2016-01-01","lpkEnd":"2016-02-05","lpkStatus":"已充值","useTime":"2016-02-01"},
                {"lpkName":"美幻","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2015-12-01","lpkEnd":"2016-01-05","lpkStatus":"已过期","useTime":"2016-01-01"},
                {"lpkName":"尚品","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2016-01-01","lpkEnd":"2016-02-05","lpkStatus":"已充值","useTime":"2016-02-01"},
                {"lpkName":"美幻","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2015-12-01","lpkEnd":"2016-01-05","lpkStatus":"已过期","useTime":"2016-01-01"},
                {"lpkName":"尚品","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2016-01-01","lpkEnd":"2016-02-05","lpkStatus":"已充值","useTime":"2016-02-01"},
                {"lpkName":"美幻","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2015-12-01","lpkEnd":"2016-01-05","lpkStatus":"已过期","useTime":"2016-01-01"},
                {"lpkName":"尚品","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2016-01-01","lpkEnd":"2016-02-05","lpkStatus":"已充值","useTime":"2016-02-01"},
                {"lpkName":"美幻","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2015-12-01","lpkEnd":"2016-01-05","lpkStatus":"已过期","useTime":"2016-01-01"},
                {"lpkName":"尚品","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2016-01-01","lpkEnd":"2016-02-05","lpkStatus":"已充值","useTime":"2016-02-01"},
                {"lpkName":"美幻","lpkNum":"2345-3423-1232","lpkPwd":"23434","lpkValue":"￥20.00","lpkStart":"2015-12-01","lpkEnd":"2016-01-05","lpkStatus":"已过期","useTime":"2016-01-01"},
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
    return new userService();
});