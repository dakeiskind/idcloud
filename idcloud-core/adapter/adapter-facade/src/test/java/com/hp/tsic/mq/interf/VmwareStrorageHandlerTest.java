package com.hp.tsic.mq.interf;

import com.h3c.idcloud.core.adapter.facade.StorageHandler;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.storage.DataStoreVo;
import com.h3c.idcloud.core.adapter.facade.provision.model.storage.Disk;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreCreate;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreDelete;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreExtend;
import com.h3c.idcloud.core.adapter.pojo.datastore.Volume;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskAttach;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskCreate;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDelete;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDetach;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskExpand;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class VmwareStrorageHandlerTest {

    @Autowired
    private StorageHandler storageHandler;

    //	@Test
    public void testCreateDisk() {

        DiskCreate diskCreate = new DiskCreate();

        diskCreate.setProviderType("vmware");
        diskCreate.setProviderUrl("https://192.168.7.3:443/sdk/");
        diskCreate.setAuthUser("hpadmin");
        diskCreate.setAuthPass("Hp1nvent");
        diskCreate.setName("test-zy-01");
        diskCreate.setLocation("DS101-01");
        diskCreate.setSize("10");
        diskCreate.setVmName("test");


        try {
            Disk disk = null;
            storageHandler.createDisk(diskCreate);

            System.out.println(disk.getName());
            System.out.println(disk.getSize());
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testDeleteDisk() {

        DiskDelete diskDelete = new DiskDelete();

        diskDelete.setProviderType("vmware");
        diskDelete.setProviderUrl("https://192.168.7.3:443/sdk/");
        diskDelete.setAuthUser("hpadmin");
        diskDelete.setAuthPass("Hp1nvent");
        diskDelete.setId("test-zy-01");
        diskDelete.setDataStore("DS101-01");

        try {
            boolean flag = storageHandler.deleteDisk(diskDelete);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testAttachDisk() {

        DiskAttach diskAttach = new DiskAttach();

        diskAttach.setProviderType("vmware");
        diskAttach.setProviderUrl("https://192.168.7.3:443/sdk/");
        diskAttach.setAuthUser("hpadmin");
        diskAttach.setAuthPass("Hp1nvent");
        diskAttach.setServerId("test-VM-10022-12494");
        diskAttach.setVolumeId("test-zy-01");
        diskAttach.setDataStore("DS101-01");
        diskAttach.setSize("10");

        try {
            boolean flag = storageHandler.attachDisk(diskAttach);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testDetachDisk() {

        DiskDetach diskDetach = new DiskDetach();

        diskDetach.setProviderType("vmware");
        diskDetach.setProviderUrl("https://192.168.7.3:443/sdk/");
        diskDetach.setAuthUser("hpadmin");
        diskDetach.setAuthPass("Hp1nvent");
        diskDetach.setServerId("test-VM-10022-12494");
        diskDetach.setVolumeId("test-zy-01");
        diskDetach.setDataStore("DS101-01");
        diskDetach.setSize("20");

        try {
            boolean flag = storageHandler.detachDisk(diskDetach);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testExpandDisk() {

        DiskExpand diskExpand = new DiskExpand();

        diskExpand.setProviderType("vmware");
        diskExpand.setProviderUrl("https://192.168.7.3:443/sdk/");
        diskExpand.setAuthUser("hpadmin");
        diskExpand.setAuthPass("Hp1nvent");
        diskExpand.setName("test-zy-01");
        diskExpand.setSize("20");
        diskExpand.setStorage("DS101-01");
        diskExpand.setVmName("test-VM-10022-12494");

        try {
            boolean flag = storageHandler.expandDisk(diskExpand);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //	@Test
    public void extendDatastore() {
        DataStoreExtend create = new DataStoreExtend();

        create.setProviderType("vmware");
        create.setProviderUrl("https://18.5.197.151:443/sdk/");
        create.setAuthUser("administrator@vsphere.local");
        create.setAuthPass("P@ssw0rd");
//		create.setHostName("18.5.197.107");
        create.setDatastoreName("datastore-llf");
        create.setClusterName("X440");
        create.setNewSize(100);
        try {
            DataStoreVo createDataStore = storageHandler.extendDataStore(create);
            for (Volume volume : createDataStore.getVolumes()) {
                System.out.println(volume.getWwn());
            }

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //	@Test
    public void deleteDatastore() {
        DataStoreDelete create = new DataStoreDelete();

        create.setProviderType("vmware");
        create.setProviderUrl("https://18.5.197.151:443/sdk/");
        create.setAuthUser("administrator@vsphere.local");
        create.setAuthPass("P@ssw0rd");
//		create.setHostName("18.5.197.107");
        create.setDatastoreName("datastore-llf");
        create.setClusterName("X440");

        try {
            storageHandler.deleteDataStore(create);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void createDatastore() {
        DataStoreCreate create = new DataStoreCreate();
        create.setTenantId("");
        create.setProviderType("vmware");
        create.setProviderUrl("https://18.5.197.151:443/sdk/");
        create.setAuthUser("administrator@vsphere.local");
        create.setAuthPass("P@ssw0rd");
        create.setSize(200);
//		create.setHostName("18.5.197.107");
        create.setDatastoreName("datastore-llf");
        create.setClusterName("X440");

        try {
            DataStoreVo createDataStore = storageHandler.createDataStore(create);

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
