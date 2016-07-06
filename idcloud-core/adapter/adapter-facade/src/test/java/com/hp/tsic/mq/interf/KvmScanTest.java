package com.hp.tsic.mq.interf;

import com.h3c.idcloud.core.adapter.facade.ScanHandler;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Host;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanByEnv;
import com.h3c.idcloud.core.adapter.pojo.vm.VmCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmCreateResult;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class KvmScanTest {
    @Autowired
    private ScanHandler scanHandler;

    public static void main(String[] args) {
        VmCreateResult result = new VmCreateResult();
        VmCreate vmCreate = new VmCreate();
        vmCreate.setProviderType("aaa");
        try {
            BeanUtils.copyProperties(result, vmCreate);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//		result = (VmCreateResult) BaseUtil.setResult(vmCreate,VmCreateResult.class);
        System.out.println(result.getProviderType());
    }

    //	@Test
    public void testScanHosByenv() throws CommonAdapterException, AdapterUnavailableException {
        HostScanByEnv hostScanByEnv = new HostScanByEnv();
        hostScanByEnv.setProviderType("Openstack");
        hostScanByEnv.setTenantId("34f11c8cba8f42fda9000982fbdcf51d");
        hostScanByEnv.setTenantName("lileifeng0001");
        hostScanByEnv.setVirtEnvType("Openstack");
        hostScanByEnv.setVirtEnvUuid("1");
        List<Host> scanHostsByEnv = scanHandler.scanHostsByEnv(hostScanByEnv);
        List<Host> hosts = new ArrayList<Host>();
        if (scanHostsByEnv != null) {
            for (int i = 0; i < scanHostsByEnv.size(); i++) {
                Host host = new Host();
                try {
                    host = BaseUtil.castObject(scanHostsByEnv.get(i), Host.class);
                    System.out.println(host.getHostName());
                    hosts.add(host);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testclient() {
        WebClient client = WebClient.create("http://localhost:8090");

        Response delete = client.path("/tscloud-adapter-powervm/hmc/mpar").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).delete();
        System.out.println(delete.getStatus());
    }
}
