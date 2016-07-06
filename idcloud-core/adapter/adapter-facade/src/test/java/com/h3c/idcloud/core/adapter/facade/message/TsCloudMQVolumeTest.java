package com.h3c.idcloud.core.adapter.facade.message;

import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.facade.common.Constants;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskAttach;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskCreate;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDelete;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDetach;
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
public class TsCloudMQVolumeTest extends AbstractJUnit4SpringContextTests {
    @Test
    public void testCreateVolume() throws MQException {
        DiskCreate diskCreate = new DiskCreate();
        diskCreate.setVirtEnvType("OpenStack");
        diskCreate.setVirtEnvUuid("debug");
        diskCreate.setProviderType(Constants.Provider.OPEN_STACK);

        diskCreate.setAuthUrl("http://192.168.9.192:35357/v2.0/");
        diskCreate.setAuthTenant("admin");
        diskCreate.setAuthUser("admin");
        diskCreate.setAuthPass("1");

        diskCreate.setProviderUrl("http://192.168.9.192:5000/v2.0/");
        diskCreate.setTenantName("demo");
        diskCreate.setTenantUserName("admin");
        diskCreate.setTenantUserPass("1");

        diskCreate.setRegion("RegionOne");

        diskCreate.setName("qct-test-volume");
        diskCreate.setSize("1");
        diskCreate.setDescription("qct-test-volume-desc");
        MQHelper.sendMessage(diskCreate);
    }

    @Test
    public void testDeleteVolume() throws MQException {
        DiskDelete diskDelete = new DiskDelete();
        diskDelete.setVirtEnvType("OpenStack");
        diskDelete.setVirtEnvUuid("debug");
        diskDelete.setProviderType(Constants.Provider.OPEN_STACK);

        diskDelete.setAuthUrl("http://192.168.9.192:35357/v2.0/");
        diskDelete.setAuthTenant("admin");
        diskDelete.setAuthUser("admin");
        diskDelete.setAuthPass("1");

        diskDelete.setProviderUrl("http://192.168.9.192:5000/v2.0/");
        diskDelete.setTenantName("demo");
        diskDelete.setTenantUserName("admin");
        diskDelete.setTenantUserPass("1");

        diskDelete.setRegion("RegionOne");

//        diskDelete.setId("ffa01fe3-7399-4e02-a1bf-0f20a3bfad58");
        diskDelete.setId("778e8f50-bef3-4570-ab99-0d05bff0def5");

        MQHelper.sendMessage(diskDelete);
    }


    @Test
    public void testAttachVolume() throws MQException {
        DiskAttach diskAttach = new DiskAttach();
        diskAttach.setVirtEnvType("OpenStack");
        diskAttach.setVirtEnvUuid("debug");
        diskAttach.setProviderType(Constants.Provider.OPEN_STACK);

        diskAttach.setAuthUrl("http://192.168.9.192:35357/v2.0/");
        diskAttach.setAuthTenant("admin");
        diskAttach.setAuthUser("admin");
        diskAttach.setAuthPass("1");

        diskAttach.setProviderUrl("http://192.168.9.192:5000/v2.0/");
        diskAttach.setTenantName("demo");
        diskAttach.setTenantUserName("admin");
        diskAttach.setTenantUserPass("1");

        diskAttach.setRegion("RegionOne");

        diskAttach.setVolumeId("778e8f50-bef3-4570-ab99-0d05bff0def5");
        diskAttach.setServerId("c4354bc3-0f97-4097-9773-7437e5f2ebfa");
        diskAttach.setDevice("/dev/vdb");

        MQHelper.sendMessage(diskAttach);
    }


    @Test
    public void testDetachVolume() throws MQException {
        DiskDetach diskDetach = new DiskDetach();
        diskDetach.setVirtEnvType("OpenStack");
        diskDetach.setVirtEnvUuid("debug");
        diskDetach.setProviderType(Constants.Provider.OPEN_STACK);

        diskDetach.setAuthUrl("http://192.168.9.192:35357/v2.0/");
        diskDetach.setAuthTenant("admin");
        diskDetach.setAuthUser("admin");
        diskDetach.setAuthPass("1");

        diskDetach.setProviderUrl("http://192.168.9.192:5000/v2.0/");
        diskDetach.setTenantName("demo");
        diskDetach.setTenantUserName("admin");
        diskDetach.setTenantUserPass("1");

        diskDetach.setRegion("RegionOne");

//        diskDelete.setId("ffa01fe3-7399-4e02-a1bf-0f20a3bfad58");
        diskDetach.setVolumeId("778e8f50-bef3-4570-ab99-0d05bff0def5");
        diskDetach.setServerId("c4354bc3-0f97-4097-9773-7437e5f2ebfa");

        MQHelper.sendMessage(diskDetach);
    }
}
