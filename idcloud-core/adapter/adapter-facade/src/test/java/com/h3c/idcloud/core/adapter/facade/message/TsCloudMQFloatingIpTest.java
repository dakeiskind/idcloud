package com.h3c.idcloud.core.adapter.facade.message;

import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.facade.common.Constants;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpAttach;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpCreate;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpDelete;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpDetach;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpDetachResult;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by qct on 2016/3/15.
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TsCloudMQFloatingIpTest extends AbstractJUnit4SpringContextTests {
    @Test
    public void testCreateFloatingIp() throws MQException {
        FloatingIpCreate floatingIpCreate = new FloatingIpCreate();
        floatingIpCreate.setVirtEnvType("OpenStack");
        floatingIpCreate.setVirtEnvUuid("debug");
        floatingIpCreate.setProviderType(Constants.Provider.OPEN_STACK);

        floatingIpCreate.setAuthUrl("http://192.168.9.199:35357/v2.0/");
        floatingIpCreate.setAuthTenant("admin");
        floatingIpCreate.setAuthUser("admin");
        floatingIpCreate.setAuthPass("1");

        floatingIpCreate.setProviderUrl("http://192.168.9.199:5000/v2.0/");
        floatingIpCreate.setTenantName("admin");
        floatingIpCreate.setTenantUserName("admin");
        floatingIpCreate.setTenantUserPass("1");

        floatingIpCreate.setRegion("RegionOne");

        floatingIpCreate.setPool("Internet");
        MQHelper.sendMessage(floatingIpCreate);
    }

    @Test
    public void testDeleteFloatingIp() throws MQException {
        FloatingIpDelete floatingIpDelete = new FloatingIpDelete();
        floatingIpDelete.setVirtEnvType("OpenStack");
        floatingIpDelete.setVirtEnvUuid("debug");
        floatingIpDelete.setProviderType(Constants.Provider.OPEN_STACK);

        floatingIpDelete.setAuthUrl("http://192.168.9.191:35357/v2.0/");
        floatingIpDelete.setAuthTenant("admin");
        floatingIpDelete.setAuthUser("admin");
        floatingIpDelete.setAuthPass("1");

        floatingIpDelete.setProviderUrl("http://192.168.9.191:5000/v2.0/");
        floatingIpDelete.setTenantName("admin");
        floatingIpDelete.setTenantUserName("admin");
        floatingIpDelete.setTenantUserPass("1");

        floatingIpDelete.setRegion("RegionOne");

        floatingIpDelete.setFloatingIpId("b54f15cc-d2f6-4671-acdd-a46c49bb452e");
        MQHelper.sendMessage(floatingIpDelete);
    }

    @Test
    public void testAttachFloatingIp() throws MQException {
        FloatingIpAttach floatingIpAttach = new FloatingIpAttach();
        floatingIpAttach.setVirtEnvType("OpenStack");
        floatingIpAttach.setVirtEnvUuid("debug");
        floatingIpAttach.setProviderType(Constants.Provider.OPEN_STACK);

        floatingIpAttach.setAuthUrl("http://192.168.9.191:35357/v2.0/");
        floatingIpAttach.setAuthTenant("admin");
        floatingIpAttach.setAuthUser("admin");
        floatingIpAttach.setAuthPass("1");

        floatingIpAttach.setProviderUrl("http://192.168.9.191:5000/v2.0/");
        floatingIpAttach.setTenantName("admin");
        floatingIpAttach.setTenantUserName("admin");
        floatingIpAttach.setTenantUserPass("1");

        floatingIpAttach.setRegion("RegionOne");

        floatingIpAttach.setFloatingIp("192.168.9.129");
        floatingIpAttach.setFixedIp("192.168.100.8");
        floatingIpAttach.setServerId("2fc74c60-b3a8-492a-9b6c-048a66401e5d");
        MQHelper.rpc(floatingIpAttach);
    }

    @Test
    public void testDetachFloatingIp() throws MQException {
        FloatingIpDetach floatingIpDetach = new FloatingIpDetach();
        floatingIpDetach.setVirtEnvType("OpenStack");
        floatingIpDetach.setVirtEnvUuid("debug");
        floatingIpDetach.setProviderType(Constants.Provider.OPEN_STACK);

        floatingIpDetach.setAuthUrl("http://192.168.9.191:35357/v2.0/");
        floatingIpDetach.setAuthTenant("admin");
        floatingIpDetach.setAuthUser("admin");
        floatingIpDetach.setAuthPass("1");

        floatingIpDetach.setProviderUrl("http://192.168.9.191:5000/v2.0/");
        floatingIpDetach.setTenantName("admin");
        floatingIpDetach.setTenantUserName("admin");
        floatingIpDetach.setTenantUserPass("1");

        floatingIpDetach.setRegion("RegionOne");

        floatingIpDetach.setFloatingIp("192.168.9.124");
        floatingIpDetach.setServerId("2fc74c60-b3a8-492a-9b6c-048a66401e5d");
        Object o = MQHelper.rpc(floatingIpDetach);
        System.out.println(FloatingIpDetachResult.class.cast(o).isSuccess());
    }
}
