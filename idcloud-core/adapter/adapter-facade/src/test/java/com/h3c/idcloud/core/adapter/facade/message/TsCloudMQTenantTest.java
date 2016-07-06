package com.h3c.idcloud.core.adapter.facade.message;

import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.facade.common.Constants;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantCreate;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by qct on 2016/3/15.
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TsCloudMQTenantTest extends AbstractJUnit4SpringContextTests {

    @Test
    public void testCreateTenant() throws MQException {
        TenantCreate tenantCreate = new TenantCreate();
        tenantCreate.setVirtEnvType("OpenStack");
        tenantCreate.setVirtEnvUuid("test");
        tenantCreate.setProviderType(Constants.Provider.OPEN_STACK);

        tenantCreate.setAuthUrl("http://192.168.9.192:35357/v2.0/");
        tenantCreate.setAuthUser("qct-project:qct");
        tenantCreate.setAuthPass("1");

        tenantCreate.setEnable(true);
        tenantCreate.setName("test-create-tenant");
        tenantCreate.setDescription("test-create-tenant-desc");

        MQHelper.rpc(tenantCreate);
    }
}
