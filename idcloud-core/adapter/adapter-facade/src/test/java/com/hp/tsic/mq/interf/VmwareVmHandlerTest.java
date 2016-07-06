package com.hp.tsic.mq.interf;

import com.h3c.idcloud.core.adapter.facade.VmHandler;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.SnapshotInfo;
import com.h3c.idcloud.core.adapter.pojo.vm.VmCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmDisk;
import com.h3c.idcloud.core.adapter.pojo.vm.VmMigrate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmModify;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNic;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNicAdd;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNicDelete;
import com.h3c.idcloud.core.adapter.pojo.vm.VmOperate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmReconfig;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRemove;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRename;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotDelete;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotQuery;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotRevert;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class VmwareVmHandlerTest {

    @Autowired
    private VmHandler vmHandler;

    public void testVmRename() {

        VmRename vmRename = new VmRename();

        vmRename.setProviderType("vmware");
        vmRename.setProviderUrl("https://192.168.7.3:443/sdk/");
        vmRename.setAuthUser("hpadmin");
        vmRename.setAuthPass("Hp1nvent");
        vmRename.setName("test-1128");
        vmRename.setNameTobe("testzy");

        boolean result = false;

        try {
            result = vmHandler.renameVm(vmRename);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertTrue(result);

    }

    public void testVmModify() {

        VmModify vmModify = new VmModify();

        vmModify.setProviderType("vmware");
        vmModify.setProviderUrl("https://192.168.7.3:443/sdk/");
        vmModify.setAuthUser("hpadmin");
        vmModify.setAuthPass("Hp1nvent");

        vmModify.setVmName("test-1128");
        vmModify.setOsCategory("windows");
        vmModify.setOsCategoryDetail("windows2008");
        vmModify.setCpu("1");
        vmModify.setMemory("1024");

        List<VmNic> nics = new ArrayList<VmNic>();

        VmNic nic = new VmNic();
        nic.setPort("dvPortGroup");
        nic.setNetmask("255.255.255.0");
        nic.setDns("192.168.7.1");
        nic.setPrivateIp("192.168.7.224");
        nic.setGateway("192.168.7.1");
        nic.setOperate("add");
        nic.setMac("");

        nics.add(nic);

        vmModify.setNics(nics);

        List<VmDisk> disks = new ArrayList<VmDisk>();

        VmDisk disk = new VmDisk();
        disk.setName("testzy");
        disk.setLocation("DS100-01");
        disk.setSize("20");
        disk.setOperate("add");
        disk.setPath("");

        disks.add(disk);

        vmModify.setDisks(disks);

        try {
            Server server = vmHandler.modifyVm(vmModify);

            System.out.println(server.getStatus());
        } catch (CommonAdapterException e) {

        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testVmSnapshotCreate() {

        VmSnapshotCreate vmSnapshotCreate = new VmSnapshotCreate();

        vmSnapshotCreate.setProviderType("vmware");
        vmSnapshotCreate.setProviderUrl("https://192.168.7.3:443/sdk/");
        vmSnapshotCreate.setAuthUser("hpadmin");
        vmSnapshotCreate.setAuthPass("Hp1nvent");
        vmSnapshotCreate.setVmName("test-llff");
        vmSnapshotCreate.setSnapshotName("testzy2");
        vmSnapshotCreate.setSnapshotDesp("test2");

        try {
            vmHandler.snapshotVm(vmSnapshotCreate);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testVmOperate() {

        VmOperate vmOperate = new VmOperate();

        vmOperate.setProviderType("vmware");
        // vmOperate.setProviderUrl("https://VCENTER0.hp-service365.com:443/sdk");

        vmOperate.setProviderUrl("https://192.168.9.3:443/sdk");
        vmOperate.setAuthUser("hp-service365\\master");
        vmOperate.setAuthPass("Hp1nvent");
        vmOperate.setVmName("OO-test-target");
        vmOperate.setAction("stop");

        try {
            boolean result = vmHandler.operateVm(vmOperate);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testVmSnapshotDelete() {

        VmSnapshotDelete vmSnapshotDelete = new VmSnapshotDelete();

        vmSnapshotDelete.setProviderType("vmware");
        vmSnapshotDelete.setProviderUrl("https://192.168.7.3:443/sdk/");
        vmSnapshotDelete.setAuthUser("hpadmin");
        vmSnapshotDelete.setAuthPass("Hp1nvent");
        vmSnapshotDelete.setVmName("test-llff");
        vmSnapshotDelete.setSnapshotName("testzy");
        vmSnapshotDelete.setCascade(true);

        try {
            boolean result = vmHandler.deleteVmSnapshot(vmSnapshotDelete);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testVmSnapshotRevert() {

        VmSnapshotRevert vmSnapshotRevert = new VmSnapshotRevert();

        vmSnapshotRevert.setProviderType("vmware");
        vmSnapshotRevert.setProviderUrl("https://192.168.7.3:443/sdk/");
        vmSnapshotRevert.setAuthUser("hpadmin");
        vmSnapshotRevert.setAuthPass("Hp1nvent");
        vmSnapshotRevert.setVmName("test-llff");
        vmSnapshotRevert.setSnapshotName("testzy2");
        vmSnapshotRevert.setHostName("192.168.7.100");
        vmSnapshotRevert.setSuppressPowerOn(true);

        try {
            boolean result = vmHandler.revertVm(vmSnapshotRevert);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testVmSnapshotQuery() {

        VmSnapshotQuery vmSnapshotQuery = new VmSnapshotQuery();

        vmSnapshotQuery.setProviderType("vmware");
        vmSnapshotQuery.setProviderUrl("https://192.168.7.3:443/sdk/");
        vmSnapshotQuery.setAuthUser("hpadmin");
        vmSnapshotQuery.setAuthPass("Hp1nvent");
        vmSnapshotQuery.setVmName("test-llff");

        try {
            List<SnapshotInfo> result = vmHandler.queryVmSnapshot(vmSnapshotQuery);

            System.out.println(result.size());

            for (SnapshotInfo snapshotInfo : result) {

                System.out.println(snapshotInfo.getName());
                System.out.println(snapshotInfo.getSnapshot());
                System.out.println(snapshotInfo.getCreateTime());
            }
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testVmReconfig() {

        VmReconfig vmReconfig = new VmReconfig();

        vmReconfig.setProviderType("vmware");
        vmReconfig.setProviderUrl("https://192.168.7.3:443/sdk/");
        vmReconfig.setAuthUser("hpadmin");
        vmReconfig.setAuthPass("Hp1nvent");
        vmReconfig.setVmName("test-llff");
        vmReconfig.setCpu("4");
        vmReconfig.setMemory("2048");

        try {
            vmHandler.reconfigVm(vmReconfig);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testVmRemove() {

        VmRemove vmRemove = new VmRemove();

        vmRemove.setProviderType("vmware");
        vmRemove.setProviderUrl("https://192.168.7.3:443/sdk/");
        vmRemove.setAuthUser("hpadmin");
        vmRemove.setAuthPass("Hp1nvent");

        vmRemove.setVmName("test-0903");

        try {
            vmHandler.removeVm(vmRemove);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testVmCreate() {

        VmCreate vmCreate = new VmCreate();

        vmCreate.setProviderType("VMware");
        vmCreate.setProviderUrl("https://18.5.197.151/sdk/");
        vmCreate.setAuthUser("administrator@vsphere.local");
        vmCreate.setAuthPass("P@ssw0rd");

        vmCreate.setAdminName("aroot");
        vmCreate.setAdminPass("P@ssw0rd");
        vmCreate.setName("测试云项目1-CESHIYXM1-RHEL5.4-64-574");
        vmCreate.setCpu("1");
        vmCreate.setMemory("1024");
        vmCreate.setImage("TEMP-RHEL5.4-snmpagent");
        vmCreate.setOsCategory("linux");
        vmCreate.setOsCategoryDetail("linux");
        vmCreate.setSysDiskLocation("datastore1 (3)");
        vmCreate.setHostName("18.5.197.104");

        // VmDataDisk disk = new VmDataDisk();
        // disk.setName("tt");
        // disk.setSize("20");
        // disk.setLocation("DS100-01");
        //
        List<VmDisk> disks = new ArrayList<VmDisk>();

        List<VmNic> nics = new ArrayList<VmNic>();

        // disks.add(disk);

        vmCreate.setDisks(disks);

        vmCreate.setNics(nics);
        try {
            Server server = vmHandler.createVm(vmCreate);

            System.out.println(server.getUuid());


        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            System.out.println(e.getErrCode());
            System.out.println(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testVmNicAdd() {

        VmNicAdd vmNicAdd = new VmNicAdd();

        vmNicAdd.setProviderType("vmware");
        vmNicAdd.setProviderUrl("https://192.168.7.3:443/sdk/");
        vmNicAdd.setAuthUser("hpadmin");
        vmNicAdd.setAuthPass("Hp1nvent");

        vmNicAdd.setOsType("linux");
        vmNicAdd.setVmName("test-llf-centos");

        List<VmNic> nics = new ArrayList<VmNic>();

        VmNic nic = new VmNic();
        nic.setPort("dvPortGroup");
        nic.setNetmask("255.255.255.0");
        nic.setDns("192.168.7.1");
        nic.setPrivateIp("192.168.7.224");
        nic.setGateway("192.168.7.1");

        nics.add(nic);

        vmNicAdd.setNics(nics);

        try {
            boolean flag = vmHandler.AddNic(vmNicAdd);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testVmNicDelete() {

        VmNicDelete vmNicDelete = new VmNicDelete();

        vmNicDelete.setProviderType("vmware");
        vmNicDelete.setProviderUrl("https://192.168.7.3:443/sdk/");
        vmNicDelete.setAuthUser("hpadmin");
        vmNicDelete.setAuthPass("Hp1nvent");

        vmNicDelete.setVmName("test-llf-centos");

        List<VmNic> nics = new ArrayList<VmNic>();

        VmNic nic = new VmNic();

        nic.setPrivateIp("192.168.7.224");

        nics.add(nic);

        vmNicDelete.setNics(nics);

        try {
            boolean flag = vmHandler.DeleteNic(vmNicDelete);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testVmMigrate() {

        VmMigrate vmMigrate = new VmMigrate();

        vmMigrate.setProviderType("vmware");
        vmMigrate.setProviderUrl("https://192.168.7.3:443/sdk/");
        vmMigrate.setAuthUser("hpadmin");
        vmMigrate.setAuthPass("Hp1nvent");

        vmMigrate.setVmName("test-1128");
        vmMigrate.setTargetStore("DS100-01");

        try {
            boolean flag = ((Server) vmHandler.migrateVm(vmMigrate)).isSuccess();
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
