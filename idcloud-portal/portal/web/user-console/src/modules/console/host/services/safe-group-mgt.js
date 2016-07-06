define([],function(){

    var safeGroupService = function(){

        this.getGridDate = function(){
            return [
                {safe_group_id:'ds-343dfdfs212/Test1',proprietary_net:'192.1.1.1',instance:'0',net_type:"经典网络",createDt:'2016-11-11 11:11:11',describe:'测试测试测试测试测试测试测试测试测试测试测试测试'},
                {safe_group_id:'dr-34sfsd2s212/Test2',proprietary_net:'test',instance:'6',net_type:"专有网络",createDt:'2016-11-11 11:11:11',describe:'安全组安全组安全组安全组安全组安全组安全组安全组'},
                {safe_group_id:'aa-3fdsfffd212/Test3',proprietary_net:'',instance:'2',net_type:"经典网络",createDt:'2016-11-11 11:11:11',describe:'testtesttesttesttest'},
                {safe_group_id:'ws-343dsfs212d/Test4',proprietary_net:'test1',instance:'2',net_type:"经典网络",createDt:'2016-11-11 11:11:11',describe:'经典网络经典网络经典网络经典网络经典网络'},
                {safe_group_id:'ds-343dsfsfsd2/Test5',proprietary_net:'192.1.1.1',instance:'5',net_type:"专有网络",createDt:'2016-11-11 11:11:11',describe:'testtesttesttest11'},
                {safe_group_id:'ff-343dfsdfs22/Test6',proprietary_net:'',instance:'0',net_type:"专有网络",createDt:'2016-11-11 11:11:11',describe:'安全组安全组安全组安全组安全组安全组安全组安全组'},
                {safe_group_id:'ff-343dfsdfs22/Test7',proprietary_net:'192.1.1.1',instance:'0',net_type:"专有网络",createDt:'2016-11-11 11:11:11',describe:'安全组安全组安全组安全组安全组安全组安全组安全组'},
                {safe_group_id:'ff-343dfsdfs22/Test8',proprietary_net:'test1',instance:'0',net_type:"专有网络",createDt:'2016-11-11 11:11:11',describe:'安全组安全组安全组安全组安全组安全组安全组安全组'},
                {safe_group_id:'ff-343dfsdfs22/Test9',proprietary_net:'',instance:'0',net_type:"专有网络",createDt:'2016-11-11 11:11:11',describe:'安全组安全组安全组安全组安全组安全组安全组安全组'},
                {safe_group_id:'ff-343dfsdfs22/Test10',proprietary_net:'192.1.1.1',instance:'0',net_type:"专有网络",createDt:'2016-11-11 11:11:11',describe:'安全组安全组安全组安全组安全组安全组安全组安全组'},
                {safe_group_id:'ff-343dfsdfs22/Test11',proprietary_net:'test1',instance:'0',net_type:"专有网络",createDt:'2016-11-11 11:11:11',describe:'安全组安全组安全组安全组安全组安全组安全组安全组'},
                {safe_group_id:'ff-343dfsdfs22/Test12',proprietary_net:'192.1.1.1',instance:'0',net_type:"专有网络",createDt:'2016-11-11 11:11:11',describe:'安全组安全组安全组安全组安全组安全组安全组安全组'}

            ];
        };

        this.getENTERDate = function(){
            return [
                {IPType: 'IPv4', agreementType: 'UDP', port: '1/65334', authorizationType: '地址段访问', authorizationObject: '0.0.0.0/0'},
                {IPType: 'IPv4', agreementType: '全部', port: '-1/-1', authorizationType: '地址段访问', authorizationObject: '0.0.0.0/0'},
                {IPType: 'IPv4', agreementType: 'UDP', port: '1/65334', authorizationType: '安全组访问', authorizationObject: '0.0.0.0/0'},
                {IPType: 'IPv4', agreementType: 'TCP', port: '1/34234', authorizationType: '地址段访问', authorizationObject: '0.0.0.0/0'},
                {IPType: 'IPv4', agreementType: 'UDP', port: '1/8080', authorizationType: '安全组访问', authorizationObject: '0.0.0.0/0'},
                {IPType: 'IPv4', agreementType: 'TCP', port: '1/3532', authorizationType: '安全组访问', authorizationObject: '0.0.0.0/0'},
            ]
        };

        this.getEXITDate = function(){
            return [
                {IPType: 'IPv4', agreementType: 'UDP', port: '1/43242', authorizationType: '安全组访问', authorizationObject: '0.0.0.0/0'},
                {IPType: 'IPv4', agreementType: 'ICMP', port: '-1/-1', authorizationType: '安全组访问', authorizationObject: '0.0.0.0/0'},
                {IPType: 'IPv4', agreementType: 'UDP', port: '80/4324', authorizationType: '地址段访问', authorizationObject: '0.0.0.0/0'},
                {IPType: 'IPv4', agreementType: 'ICMP', port: '1/34234', authorizationType: '安全组访问', authorizationObject: '0.0.0.0/0'},
                {IPType: 'IPv4', agreementType: 'UDP', port: '1/8080', authorizationType: '地址段访问', authorizationObject: '0.0.0.0/0'},
                {IPType: 'IPv4', agreementType: 'ICMP', port: '51/3532', authorizationType: '安全组访问', authorizationObject: '0.0.0.0/0'},
            ]
        }
    };
    return new safeGroupService();
});