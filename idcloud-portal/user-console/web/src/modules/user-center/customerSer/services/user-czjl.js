define([],function(){
    var userCzjlService = function(){

        this.getGridDate = function(){
            return[
                {operation_type:'登录',operation_module:'用户',client_IP:'15.203.233.76',operation_time:'2015-05-11 11:32:11',operation_result:'成功'},
                {operation_type:'更换手机号',operation_module:'用户',client_IP:'15.203.433.76',operation_time:'2015-09-11 09:55:11',operation_result:'失败'},
                {operation_type:'登录',operation_module:'用户',client_IP:'15.203.233.76',operation_time:'2015-11-11 11:14:11',operation_result:'成功'},
                {operation_type:'更换邮箱',operation_module:'用户',client_IP:'15.203.233.76',operation_time:'2015-11-11 19:43:11',operation_result:'失败'},
                {operation_type:'登录',operation_module:'用户',client_IP:'15.203.243.74',operation_time:'2015-11-11 11:09:11',operation_result:'失败'},
                {operation_type:'修改密码',operation_module:'用户',client_IP:'15.203.233.76',operation_time:'2015-04-11 11:11:11',operation_result:'成功'},
                {operation_type:'登录',operation_module:'用户',client_IP:'15.203.233.76',operation_time:'2015-11-23 09:11:43',operation_result:'成功'},
                {operation_type:'更换邮箱',operation_module:'用户',client_IP:'15.234.423.46',operation_time:'2015-11-11 11:01:11',operation_result:'成功'},
                {operation_type:'登录',operation_module:'用户',client_IP:'15.203.233.76',operation_time:'2015-06-11 12:06:11',operation_result:'成功'},
                {operation_type:'修改密码',operation_module:'用户',client_IP:'15.231.233.76',operation_time:'2015-11-11 11:11:11',operation_result:'成功'},
                {operation_type:'登录',operation_module:'用户',client_IP:'15.203.233.86',operation_time:'2015-11-11 11:11:23',operation_result:'成功'},
                {operation_type:'登录',operation_module:'用户',client_IP:'162.203.233.76',operation_time:'2015-11-11 14:12:11',operation_result:'失败'},
                {operation_type:'登录',operation_module:'用户',client_IP:'192.203.233.76',operation_time:'2015-11-11 15:16:11',operation_result:'失败'},
                {operation_type:'用户注册',operation_module:'用户',client_IP:'15.203.783.76',operation_time:'2015-01-11 08:34:11',operation_result:'成功'}
            ];
        };
    };
    return new userCzjlService();
});