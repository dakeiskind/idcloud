package com.h3c.idcloud.core.adapter.facade.message;

import com.google.common.collect.Lists;

import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.facade.common.Constants;
import com.h3c.idcloud.core.adapter.pojo.vm.VmCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNic;
import com.h3c.idcloud.core.adapter.pojo.vm.VmOperate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmQuery;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRemove;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.UUID;

/**
 * Created by qct on 2016/2/24.
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TsCloudMQVmTest extends AbstractJUnit4SpringContextTests {

    @Test
    public void testQuery() throws Exception {
        //test for send message
        VmQuery vmQuery = new VmQuery();
        vmQuery.setServerId("9c54fb0d-d92e-41a1-ad64-e31eac75a177");
        vmQuery.setVirtEnvType("OpenStack");
        vmQuery.setVirtEnvUuid("2");
        vmQuery.setProviderType(Constants.Provider.OPEN_STACK);
        for (int i = 0; i < 1; i++) {
            MQHelper.sendMessage(vmQuery);
        }
    }

    @Test
    public void testVmCreate() throws MQException {
        VmCreate vmCreate = new VmCreate();
        vmCreate.setVirtEnvType("OpenStack");
        vmCreate.setVirtEnvUuid("debug");
        vmCreate.setProviderType(Constants.Provider.OPEN_STACK);

        vmCreate.setAuthUrl("http://192.168.9.191:35357/v2.0/");
        vmCreate.setAuthTenant("admin");
        vmCreate.setAuthUser("admin");
        vmCreate.setAuthPass("1");

        vmCreate.setProviderUrl("http://192.168.9.191:5000/v2.0/");
        vmCreate.setTenantName("qct-project");
        vmCreate.setTenantUserName("qct");
        vmCreate.setTenantUserPass("1");

        vmCreate.setRegion("RegionOne");

        vmCreate.setImage("e37579eb-6913-49fd-986c-611602d5ce27");
        vmCreate.setAdminName("qct");
        vmCreate.setAdminPass("1");

//        vmCreate.setAvailabilityZone("nova");

        VmNic vmNic = new VmNic();
        //qct-network
        vmNic.setNetId("ddef9105-71f5-4d8c-a0f6-30490fad53ce");

        //subnet2
//        vmNic.setNetId("c8d3bfb2-118d-40bb-8a76-0fbfd87060a0");
//        vmNic.setNetId("3babfdb4-a78a-4e05-94c4-e7853023c90c");

        vmCreate.setNics(Lists.newArrayList(vmNic));
        for (int i=0;i<2;i++) {
            vmCreate.setMsgId(UUID.randomUUID().toString());
            vmCreate.setName("qct-" + i);
            MQHelper.sendMessage(vmCreate);
        }
    }

    @Test
    public void testVmDelete() throws MQException {
        VmRemove vmRemove = new VmRemove();
        vmRemove.setVirtEnvType("OpenStack");
        vmRemove.setVirtEnvUuid("2");
        vmRemove.setProviderType(Constants.Provider.OPEN_STACK);

        vmRemove.setId("67c5e3c4-c17e-4071-94e8-76c3dc8bb20b");

        MQHelper.sendMessage(vmRemove);
    }


    @Test
    public void testVmOperate() throws MQException {
        VmOperate vmOperate = new VmOperate();
        vmOperate.setVirtEnvType("OpenStack");
        vmOperate.setVirtEnvUuid("test");
        vmOperate.setProviderType(Constants.Provider.OPEN_STACK);
        vmOperate.setEndpoint("http://192.168.9.192:5000/v2.0/");
        vmOperate.setAuthUser("qct-project:qct");
        vmOperate.setAuthPass("1");
        vmOperate.setRegion("RegionOne");

        vmOperate.setId("333118a5-1c8a-458b-a662-2bb380060498");
        vmOperate.setAction("stop");

        MQHelper.sendMessage(vmOperate);
    }
}