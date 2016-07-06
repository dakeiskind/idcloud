define([],function() {
    var vpnMgtService = function() {

        //获取ike策略grid数据
        this.getIkeGridData = function(){
            return [
                {"IKEName":"liuyang","IKEdesc":"IKE策略描述","IKEAlgorithm":"sha1","IKEEncryption":"aes-128","IKEPfs":"group5"},
                {"IKEName":"dennis","IKEdesc":"IKE策略描述","IKEAlgorithm":"sha1","IKEEncryption":"aes-198","IKEPfs":"group2"},
                {"IKEName":"dennis","IKEdesc":"IKE策略描述","IKEAlgorithm":"sha1","IKEEncryption":"aes-256","IKEPfs":"group4"},
                {"IKEName":"test1","IKEdesc":"IKE策略描述","IKEAlgorithm":"sha1","IKEEncryption":"aes-128","IKEPfs":"group1"},
                {"IKEName":"test2","IKEdesc":"IKE策略描述","IKEAlgorithm":"sha1","IKEEncryption":"aes-238","IKEPfs":"group0"},
                {"IKEName":"test3","IKEdesc":"IKE策略描述","IKEAlgorithm":"sha1","IKEEncryption":"aes-138","IKEPfs":"group3"},
                {"IKEName":"test4","IKEdesc":"IKE策略描述","IKEAlgorithm":"sha1","IKEEncryption":"aes-198","IKEPfs":"group4"},
                {"IKEName":"test5","IKEdesc":"IKE策略描述","IKEAlgorithm":"sha1","IKEEncryption":"aes-108","IKEPfs":"group5"},
            ];
        };

        //获取IPSec策略grid数据
        this.getIPSecGridData = function(){
          return [
              {"IPSecName":"liuyang","IPSecdesc":"IPSec策略描述","IPSecAlgorithm":"sha1","IPSecEncryption":"aes-128","IPSecPfs":"group5"},
              {"IPSecName":"dennis","IPSecdesc":"IPSec策略描述","IPSecAlgorithm":"sha1","IPSecEncryption":"aes-198","IPSecPfs":"group2"},
              {"IPSecName":"dennis","IPSecdesc":"IPSec策略描述","IPSecAlgorithm":"sha1","IPSecEncryption":"aes-256","IPSecPfs":"group4"},
              {"IPSecName":"test1","IPSecdesc":"IPSec策略描述","IPSecAlgorithm":"sha1","IPSecEncryption":"aes-128","IPSecPfs":"group1"},
              {"IPSecName":"test2","IPSecdesc":"IPSec策略描述","IPSecAlgorithm":"sha1","IPSecEncryption":"aes-238","IPSecPfs":"group0"},
              {"IPSecName":"test3","IPSecdesc":"IPSec策略描述","IPSecAlgorithm":"sha1","IPSecEncryption":"aes-138","IPSecPfs":"group3"},
              {"IPSecName":"test4","IPSecdesc":"IPSec策略描述","IPSecAlgorithm":"sha1","IPSecEncryption":"aes-198","IPSecPfs":"group4"},
              {"IPSecName":"test5","IPSecdesc":"IPSec策略描述","IPSecAlgorithm":"sha1","IPSecEncryption":"aes-108","IPSecPfs":"group5"},
          ]
        };

        //获取VPN服务grid数据
        this.getVPNGridData = function(){
          return[
              {"VPNName":"VPN_Test","VPNDesc":"VPN测试","publicIP":"IPV4:192.168.9.121","subNet":"192.168.100.0/24","VPNRouter":"v_test01","VPNStatus":"等待创建成功"},
              {"VPNName":"VPN_Test1","VPNDesc":"VPN测试","publicIP":"IPV4:192.168.9.121","subNet":"192.168.100.0/24","VPNRouter":"v_test01","VPNStatus":"创建成功"},
              {"VPNName":"VPN_Test2","VPNDesc":"VPN测试","publicIP":"IPV4:192.168.9.121","subNet":"192.168.100.0/24","VPNRouter":"v_test01","VPNStatus":"等待创建成功"},
              {"VPNName":"VPN_Test3","VPNDesc":"VPN测试","publicIP":"IPV4:192.168.9.121","subNet":"192.168.100.0/24","VPNRouter":"v_test01","VPNStatus":"创建成功"},
              {"VPNName":"VPN_Test4","VPNDesc":"VPN测试","publicIP":"IPV4:192.168.9.121","subNet":"192.168.100.0/24","VPNRouter":"v_test01","VPNStatus":"等待创建成功"},
              {"VPNName":"VPN_Test1","VPNDesc":"VPN测试","publicIP":"IPV4:192.168.9.121","subNet":"192.168.100.0/24","VPNRouter":"v_test01","VPNStatus":"创建成功"},
              {"VPNName":"VPN_Test3","VPNDesc":"VPN测试","publicIP":"IPV4:192.168.9.121","subNet":"192.168.100.0/24","VPNRouter":"v_test01","VPNStatus":"等待创建成功"},
              {"VPNName":"VPN_Test1","VPNDesc":"VPN测试","publicIP":"IPV4:192.168.9.121","subNet":"192.168.100.0/24","VPNRouter":"v_test01","VPNStatus":"等待创建成功"},
              {"VPNName":"VPN_Test2","VPNDesc":"VPN测试","publicIP":"IPV4:192.168.9.121","subNet":"192.168.100.0/24","VPNRouter":"v_test01","VPNStatus":"创建成功"},

          ]
        };

        //获取IPSec站点链接grid数据
        this.getIPSecNodeData = function(){
          return[
              {"nodeName":"node_test","nodeDesc":"重庆站点","vpnName":"VPN_test","nodeIKE":"dennis","nodeIPSec":"test1","nodeStatus":"未链接"},
              {"nodeName":"node_test1","nodeDesc":"重庆站点","vpnName":"VPN_test","nodeIKE":"dennis","nodeIPSec":"test1","nodeStatus":"未链接"},
              {"nodeName":"node_test2","nodeDesc":"重庆站点","vpnName":"VPN_test","nodeIKE":"dennis","nodeIPSec":"test1","nodeStatus":"未链接"},
              {"nodeName":"node_test3","nodeDesc":"重庆站点","vpnName":"VPN_test","nodeIKE":"dennis","nodeIPSec":"test1","nodeStatus":"未链接"},
              {"nodeName":"node_test4","nodeDesc":"重庆站点","vpnName":"VPN_test","nodeIKE":"dennis","nodeIPSec":"test1","nodeStatus":"未链接"},
              {"nodeName":"node_test5","nodeDesc":"重庆站点","vpnName":"VPN_test","nodeIKE":"dennis","nodeIPSec":"test1","nodeStatus":"未链接"},

          ]
        };
    };
    return new vpnMgtService();
});
