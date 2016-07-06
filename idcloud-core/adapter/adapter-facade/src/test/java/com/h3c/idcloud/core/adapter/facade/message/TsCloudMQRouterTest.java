package com.h3c.idcloud.core.adapter.facade.message;

import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.facade.common.Constants;
import com.h3c.idcloud.core.adapter.pojo.network.ExternalGateway;
import com.h3c.idcloud.core.adapter.pojo.network.RouterAddExternalGateway;
import com.h3c.idcloud.core.adapter.pojo.network.RouterAddInterface;
import com.h3c.idcloud.core.adapter.pojo.network.RouterCreate;
import com.h3c.idcloud.core.adapter.pojo.network.RouterDelete;
import com.h3c.idcloud.core.adapter.pojo.network.RouterRemoveExternalGateway;
import com.h3c.idcloud.core.adapter.pojo.network.RouterRemoveInterface;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by qct on 2016/3/15.
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TsCloudMQRouterTest extends AbstractJUnit4SpringContextTests {
    @Test
    public void testCreateRouter() throws MQException {
        RouterCreate routerCreate = new RouterCreate();
        routerCreate.setVirtEnvType("OpenStack");
        routerCreate.setVirtEnvUuid("debug");
        routerCreate.setProviderType(Constants.Provider.OPEN_STACK);

        routerCreate.setAuthUrl("http://192.168.9.192:35357/v2.0/");
        routerCreate.setAuthTenant("admin");
        routerCreate.setAuthUser("admin");
        routerCreate.setAuthPass("1");

        routerCreate.setProviderUrl("http://192.168.9.192:5000/v2.0/");
        routerCreate.setTenantName("demo");
        routerCreate.setTenantUserName("admin");
        routerCreate.setTenantUserPass("1");

        routerCreate.setRegion("RegionOne");

        routerCreate.setName("qct-test-router");
        MQHelper.sendMessage(routerCreate);
    }

    @Test
    public void testDeleteRouter() throws MQException {
        RouterDelete routerDelete = new RouterDelete();
        routerDelete.setVirtEnvType("OpenStack");
        routerDelete.setVirtEnvUuid("debug");
        routerDelete.setProviderType(Constants.Provider.OPEN_STACK);

        routerDelete.setAuthUrl("http://192.168.9.192:35357/v2.0/");
        routerDelete.setAuthTenant("admin");
        routerDelete.setAuthUser("admin");
        routerDelete.setAuthPass("1");

        routerDelete.setProviderUrl("http://192.168.9.192:5000/v2.0/");
        routerDelete.setTenantName("demo");
        routerDelete.setTenantUserName("admin");
        routerDelete.setTenantUserPass("1");

        routerDelete.setRegion("RegionOne");

        routerDelete.setId("88924bb0-cd42-4bbb-9fa9-31e5adb701c4");
        MQHelper.rpc(routerDelete);
    }

    @Test
    public void testAddInterface() throws MQException {
        RouterAddInterface routerAddInterface = new RouterAddInterface();
        routerAddInterface.setVirtEnvType("OpenStack");
        routerAddInterface.setVirtEnvUuid("debug");
        routerAddInterface.setProviderType(Constants.Provider.OPEN_STACK);

        routerAddInterface.setAuthUrl("http://192.168.9.192:35357/v2.0/");
        routerAddInterface.setAuthTenant("admin");
        routerAddInterface.setAuthUser("admin");
        routerAddInterface.setAuthPass("1");

        routerAddInterface.setProviderUrl("http://192.168.9.192:5000/v2.0/");
        routerAddInterface.setTenantName("demo");
        routerAddInterface.setTenantUserName("admin");
        routerAddInterface.setTenantUserPass("1");

        routerAddInterface.setRegion("RegionOne");

        routerAddInterface.setRouterId("a64a5296-1d1a-456b-85b0-0a0347f933fc");
        routerAddInterface.setSubnetId("2e09732a-852b-4e3c-bb65-a923755f11cd");
        Object o = MQHelper.rpc(routerAddInterface);
        System.out.println(o);
    }

    @Test
    public void testAddExternalGateway() throws MQException {
        RouterAddExternalGateway routerAddExternalGateway = new RouterAddExternalGateway();
        routerAddExternalGateway.setVirtEnvType("OpenStack");
        routerAddExternalGateway.setVirtEnvUuid("debug");
        routerAddExternalGateway.setProviderType(Constants.Provider.OPEN_STACK);

        routerAddExternalGateway.setAuthUrl("http://192.168.9.192:35357/v2.0/");
        routerAddExternalGateway.setAuthTenant("admin");
        routerAddExternalGateway.setAuthUser("admin");
        routerAddExternalGateway.setAuthPass("1");

        routerAddExternalGateway.setProviderUrl("http://192.168.9.192:5000/v2.0/");
        routerAddExternalGateway.setTenantName("demo");
        routerAddExternalGateway.setTenantUserName("admin");
        routerAddExternalGateway.setTenantUserPass("1");

        routerAddExternalGateway.setRegion("RegionOne");

        routerAddExternalGateway.setRouterId("a64a5296-1d1a-456b-85b0-0a0347f933fc");
        routerAddExternalGateway.setExternalGateway(ExternalGateway.builder()
                .networkId("f7c09947-1345-4783-bdcd-6e85c817beb6").enableSnat(true).build());
        Object o = MQHelper.rpc(routerAddExternalGateway);
        System.out.println(o);
    }

    @Test
    public void testRemoveExternalGateway() throws MQException {
        RouterRemoveExternalGateway routerRemoveExternalGateway = new RouterRemoveExternalGateway();
        routerRemoveExternalGateway.setVirtEnvType("OpenStack");
        routerRemoveExternalGateway.setVirtEnvUuid("debug");
        routerRemoveExternalGateway.setProviderType(Constants.Provider.OPEN_STACK);

        routerRemoveExternalGateway.setAuthUrl("http://192.168.9.192:35357/v2.0/");
        routerRemoveExternalGateway.setAuthTenant("admin");
        routerRemoveExternalGateway.setAuthUser("admin");
        routerRemoveExternalGateway.setAuthPass("1");

        routerRemoveExternalGateway.setProviderUrl("http://192.168.9.192:5000/v2.0/");
        routerRemoveExternalGateway.setTenantName("demo");
        routerRemoveExternalGateway.setTenantUserName("admin");
        routerRemoveExternalGateway.setTenantUserPass("1");

        routerRemoveExternalGateway.setRegion("RegionOne");

        routerRemoveExternalGateway.setRouterId("a64a5296-1d1a-456b-85b0-0a0347f933fc");
        Object o = MQHelper.rpc(routerRemoveExternalGateway);
        System.out.println(o);
    }

    @Test
    public void testRemoveInterface() throws MQException {
        RouterRemoveInterface routerRemoveInterface = new RouterRemoveInterface();
        routerRemoveInterface.setVirtEnvType("OpenStack");
        routerRemoveInterface.setVirtEnvUuid("debug");
        routerRemoveInterface.setProviderType(Constants.Provider.OPEN_STACK);

        routerRemoveInterface.setAuthUrl("http://192.168.9.192:35357/v2.0/");
        routerRemoveInterface.setAuthTenant("admin");
        routerRemoveInterface.setAuthUser("admin");
        routerRemoveInterface.setAuthPass("1");

        routerRemoveInterface.setProviderUrl("http://192.168.9.192:5000/v2.0/");
        routerRemoveInterface.setTenantName("demo");
        routerRemoveInterface.setTenantUserName("admin");
        routerRemoveInterface.setTenantUserPass("1");

        routerRemoveInterface.setRegion("RegionOne");

        routerRemoveInterface.setRouterId("a64a5296-1d1a-456b-85b0-0a0347f933fc");
        routerRemoveInterface.setSubnetId("2e09732a-852b-4e3c-bb65-a923755f11cd");
        Object o = MQHelper.rpc(routerRemoveInterface);
        System.out.println(o);
    }
}
