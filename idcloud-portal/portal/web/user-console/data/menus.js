define([], function() {
    var servicesMenu = {
        "routerData":[
            // 用户中心
            {name:"user",parentRouter:"",tempHtml:"views/user-container.html",tempVmodel:"vmodel/user-container.js"},
            {name:"user.home",parentRouter:"user",tempHtml:"user-center/dashboard/views/dashboard.html",tempVmodel:"user-center/dashboard/vmodel/dashboard.js"},
            {name:"user.account-info",parentRouter:"user",tempHtml:"user-center/userMgt/views/user-info.html",tempVmodel:"user-center/userMgt/vmodel/user-info.js"},
            {name:"user.account-auth",parentRouter:"user",tempHtml:"user-center/userMgt/views/user-smrz.html",tempVmodel:"user-center/userMgt/vmodel/user-smrz.js"},
            {name:"user.account-secure",parentRouter:"user",tempHtml:"user-center/userMgt/views/user-sec.html",tempVmodel:"user-center/userMgt/vmodel/user-sec.js"},
            {name:"user.account-msgConfig",parentRouter:"user",tempHtml:"user-center/userMgt/views/user-config.html",tempVmodel:"user-center/userMgt/vmodel/user-config.js"},
            {name:"user.account-subuser",parentRouter:"user",tempHtml:"user-center/userMgt/views/user-sub.html",tempVmodel:"user-center/userMgt/vmodel/user-sub.js"},
            {name:"user.account-privilege",parentRouter:"user",tempHtml:"user-center/userMgt/views/user-power.html",tempVmodel:"user-center/userMgt/vmodel/user-power.js"},

            //账户管理
            {name:"user.expense-recharge",parentRouter:"user",tempHtml:"user-center/accountMgt/views/user-cz.html",tempVmodel:"user-center/accountMgt/vmodel/user-cz.js"},
            {name:"user.expense-gift",parentRouter:"user",tempHtml:"user-center/accountMgt/views/user-lpk.html",tempVmodel:"user-center/accountMgt/vmodel/user-lpk.js"},
            {name:"user.expense-coupon",parentRouter:"user",tempHtml:"user-center/accountMgt/views/user-yhj.html",tempVmodel:"user-center/accountMgt/vmodel/user-yhj.js"},
            {name:"user.expense-invoice",parentRouter:"user",tempHtml:"user-center/accountMgt/views/user-fp.html",tempVmodel:"user-center/accountMgt/vmodel/user-fp.js"},
            {name:"user.expense-consumption",parentRouter:"user",tempHtml:"user-center/accountMgt/views/user-jy.html",tempVmodel:"user-center/accountMgt/vmodel/user-jy.js"},
            {name:"user.expense-order",parentRouter:"user",tempHtml:"user-center/orderMgt/views/order-list.html",tempVmodel:"user-center/orderMgt/vmodel/order-list.js"},
            {name:"user.expense-renew",parentRouter:"user",tempHtml:"user-center/accountMgt/views/user-xf.html",tempVmodel:"user-center/accountMgt/vmodel/user-xf.js"},
            //客户服务
            {name:"user.service-issue",parentRouter:"user",tempHtml:"user-center/customerSer/views/user-gd.html",tempVmodel:"user-center/customerSer/vmodel/user-gd.js"},
            {name:"user.service-notifications",parentRouter:"user",tempHtml:"user-center/customerSer/views/user-znxx.html",tempVmodel:"user-center/customerSer/vmodel/user-znxx.js"},
            {name:"user.service-log",parentRouter:"user",tempHtml:"user-center/customerSer/views/user-czjl.html",tempVmodel:"user-center/customerSer/vmodel/user-czjl.js"},


            // 测试组件
            {name:"user.demo-datepicker",parentRouter:"user",tempHtml:"demo/views/demo-datepicker.html",tempVmodel:"demo/vmodel/demo-datepicker.js"},
            {name:"user.demo-slider",parentRouter:"user",tempHtml:"demo/views/demo-rangeSlider.html",tempVmodel:"demo/vmodel/demo-rangeSlider.js"},
            {name:"user.demo-grid",parentRouter:"user",tempHtml:"demo/views/demo-grid.html",tempVmodel:"demo/vmodel/demo-grid.js"},
            {name:"user.demo-window",parentRouter:"user",tempHtml:"demo/views/demo-window.html",tempVmodel:"demo/vmodel/demo-window.js"},
            {name:"user.demo-validator",parentRouter:"user",tempHtml:"demo/views/demo-validator.html",tempVmodel:"demo/vmodel/demo-validator.js"},
            {name:"user.demo-shopping",parentRouter:"user",tempHtml:"demo/views/demo-shopping.html",tempVmodel:"demo/vmodel/demo-shopping.js"},
            {name:"user.demo-echarts",parentRouter:"user",tempHtml:"demo/views/demo-echarts.html",tempVmodel:"demo/vmodel/demo-echarts.js"},
            {name:"user.demo-fileUploader",parentRouter:"user",tempHtml:"demo/views/demo-fileUploader.html",tempVmodel:"demo/vmodel/demo-fileUploader.js"},
            {name:"user.demo-http",parentRouter:"user",tempHtml:"demo/views/demo-http.html",tempVmodel:"demo/vmodel/demo-http.js"},
            
            
            // 控制中心
            {name:"console",parentRouter:"",tempHtml:"views/service-container.html",tempVmodel:"vmodel/service-container.js"},
            {name:"console.home",parentRouter:"console",tempHtml:"console/dashboard/views/dashboard.html",tempVmodel:"console/dashboard/vmodel/dashboard.js"},

            // 云服务器
            {name:"console.cs-host",parentRouter:"console",tempHtml:"console/host/views/host-mgt.html",tempVmodel:"console/host/vmodel/host-mgt.js"},
            // {name:"console.hostDetails",parentRouter:"console",tempHtml:"console/host/views/host.html",tempVmodel:"console/host/vmodel/host.js"},
            {name:"console.cs-cbs",parentRouter:"console",tempHtml:"console/host/views/disk-mgt.html",tempVmodel:"console/host/vmodel/disk-mgt.js"},
            {name:"console.cs-snapshot",parentRouter:"console",tempHtml:"console/host/views/snapshot-mgt.html",tempVmodel:"console/host/vmodel/snapshot-mgt.js"},
            {name:"console.cs-image",parentRouter:"console",tempHtml:"console/host/views/console-jx.html",tempVmodel:"console/host/vmodel/console-jx.js"},
            {name:"console.cs-sshkey",parentRouter:"console",tempHtml:"console/host/views/ssh-key.html",tempVmodel:"console/host/vmodel/ssh-key.js"},
            {name:"console.cs-securityGroup",parentRouter:"console",tempHtml:"console/host/views/safe-group-mgt.html",tempVmodel:"console/host/vmodel/safe-group-mgt.js"},

            // 私有网络
            {name:"console.vpc-topology",parentRouter:"console",tempHtml:"console/vpc/views/network-topology.html",tempVmodel:"console/vpc/vmodel/network-topology.js"},
            {name:"console.vpc-network",parentRouter:"console",tempHtml:"console/vpc/views/pn-mgt.html",tempVmodel:"console/vpc/vmodel/pn-mgt.js"},
            {name:"console.vpc-subnet",parentRouter:"console",tempHtml:"console/vpc/views/subnet-mgt.html",tempVmodel:"console/vpc/vmodel/subnet-mgt.js"},
            {name:"console.vpc-router",parentRouter:"console",tempHtml:"console/vpc/views/router-mgt.html",tempVmodel:"console/vpc/vmodel/router-mgt.js"},
            {name:"console.vpc-vpn",parentRouter:"console",tempHtml:"console/vpc/views/vpn-mgt.html",tempVmodel:"console/vpc/vmodel/vpn-mgt.js"},

            // 弹性公网IP
            {name:"console.eip-home",parentRouter:"console",tempHtml:"console/elasticPublic/views/console-elasticipmgt.html",tempVmodel:"console/elasticPublic/vmodel/console-elasticipmgt.js"},
            // {name:"console.console-elasticip",parentRouter:"console",tempHtml:"console/elasticPublic/views/console-elasticip.html",tempVmodel:"console/elasticPublic/vmodel/console-elasticip.js"},

            //负载均衡
            {name:"console.loadbalance-instance",parentRouter:"console",tempHtml:"console/loadBalance/views/resource-pool.html",tempVmodel:"console/loadBalance/vmodel/resource-pool.js"},
            {name:"console.loadbalance-member",parentRouter:"console",tempHtml:"console/loadBalance/views/member.html",tempVmodel:"console/loadBalance/vmodel/member.js"},
            {name:"console.loadbalance-monitor",parentRouter:"console",tempHtml:"console/loadBalance/views/monitor.html",tempVmodel:"console/loadBalance/vmodel/monitor.js"},

            //云服务监控
            {name:"console.monitor-home",parentRouter:"console",tempHtml:"console/cloudwatch/views/console-cloudwatch-mgt.html",tempVmodel:"console/cloudwatch/vmodel/console-cloudwatch-mgt.js"},
            {name:"console.monitor-policy",parentRouter:"console",tempHtml:"console/cloudwatch/views/console-strategy-mgt.html",tempVmodel:"console/cloudwatch/vmodel/console-strategy-mgt.js"},
            {name:"console.monitor-contact",parentRouter:"console",tempHtml:"console/cloudwatch/views/alarm-contacts-mgt.html",tempVmodel:"console/cloudwatch/vmodel/alarm-contacts-mgt.js"},

            // 订单管理
            // {name:"user.order-blockStorage",parentRouter:"user",tempHtml:"user-center/orderMgt/views/order-blockStorage.html",tempVmodel:"user-center/orderMgt/vmodel/order-blockStorage.js"},

            // 云应用管理
            {name:"app",parentRouter:"",tempHtml:"views/app-container.html",tempVmodel:"vmodel/app-container.js"},
            {name:"app.cloudapp-subscription",parentRouter:"app",tempHtml:"cloud-app/appManage/views/app-subscription.html",tempVmodel:"cloud-app/appManage/vmodel/app-subscription.js"},
            {name:"app.cloudapp-management",parentRouter:"app",tempHtml:"cloud-app/appManage/views/app-management.html",tempVmodel:"cloud-app/appManage/vmodel/app-management.js"},

        ]

    }
    
    return servicesMenu;
});
