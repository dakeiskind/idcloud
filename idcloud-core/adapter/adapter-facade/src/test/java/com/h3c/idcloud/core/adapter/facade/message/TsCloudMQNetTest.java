package com.h3c.idcloud.core.adapter.facade.message;

import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.facade.common.Constants;
import com.h3c.idcloud.core.adapter.pojo.network.NetCreate;
import com.h3c.idcloud.core.adapter.pojo.network.Subnet;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by qct on 2016/3/15.
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TsCloudMQNetTest extends AbstractJUnit4SpringContextTests {
    @Test
    public void testCreateNet() throws MQException {
        NetCreate netCreate = new NetCreate();
        netCreate.setVirtEnvType("OpenStack");
        netCreate.setVirtEnvUuid("debug");
        netCreate.setProviderType(Constants.Provider.OPEN_STACK);

        netCreate.setAuthUrl("http://192.168.9.192:35357/v2.0/");
        netCreate.setAuthTenant("admin");
        netCreate.setAuthUser("admin");
        netCreate.setAuthPass("1");

        netCreate.setProviderUrl("http://192.168.9.192:5000/v2.0/");
        netCreate.setTenantName("admin");
        netCreate.setTenantUserName("admin");
        netCreate.setTenantUserPass("1");

        netCreate.setRegion("RegionOne");

        //一键创建网络
//        netCreate.setName("default-net");
//        netCreate.setCidr("10.10.10.0/24");
//        netCreate.setRouter(Router.builder()
//                .name("test-router")
//                .adminStateUp(true)
//                .externalGateway(ExternalGateway.builder()
//                        .networkId("f7c09947-1345-4783-bdcd-6e85c817beb6")
//                        .enableSnat(true).build()
//                ).build());

        //只创建子网
        netCreate.setId("34702854-f8f8-4c48-9a4f-2e52131f39c7");
        netCreate.setSubnet(Subnet.builder().cidr("10.10.30.0/24").build());
        MQHelper.sendMessage(netCreate);
    }
}
