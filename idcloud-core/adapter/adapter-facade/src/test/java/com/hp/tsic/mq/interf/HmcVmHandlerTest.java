package com.hp.tsic.mq.interf;

import com.h3c.idcloud.core.adapter.facade.AdminHandler;
import com.h3c.idcloud.core.adapter.facade.NetHandler;
import com.h3c.idcloud.core.adapter.facade.ScanHandler;
import com.h3c.idcloud.core.adapter.facade.StorageHandler;
import com.h3c.idcloud.core.adapter.facade.VmHandler;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.tenant.Tenants;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.user.Users;
import com.h3c.idcloud.core.adapter.facade.provision.model.keypair.KeyPairResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroup;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroupResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroupRulesResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Template;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Volume;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.admin.SgCreate;
import com.h3c.idcloud.core.adapter.pojo.admin.SgDelete;
import com.h3c.idcloud.core.adapter.pojo.block.Block;
import com.h3c.idcloud.core.adapter.pojo.block.BlockList;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockListResult;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskAttach;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskCreate;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDelete;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDetach;
import com.h3c.idcloud.core.adapter.pojo.image.ImageDelete;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairCreate;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairDelete;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairGet;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairListGet;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairGetResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairListGetResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.keyVo.KeyVo;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpAttach;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpCreate;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpDelete;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpDetach;
import com.h3c.idcloud.core.adapter.pojo.network.NetCreate;
import com.h3c.idcloud.core.adapter.pojo.network.SecurityGroupQuery;
import com.h3c.idcloud.core.adapter.pojo.network.ServerSecurityGroupAdd;
import com.h3c.idcloud.core.adapter.pojo.network.ServerSecurityGroupDelete;
import com.h3c.idcloud.core.adapter.pojo.network.SgRuleCreate;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpCreateResult;
import com.h3c.idcloud.core.adapter.pojo.scan.TemplateScan;
import com.h3c.idcloud.core.adapter.pojo.tenant.AddUserToTenant;
import com.h3c.idcloud.core.adapter.pojo.tenant.RemoveUserFromTenant;
import com.h3c.idcloud.core.adapter.pojo.tenant.Tenant;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantCreate;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantDelete;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantInfoGet;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantListGet;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantResourcesDelete;
import com.h3c.idcloud.core.adapter.pojo.user.User;
import com.h3c.idcloud.core.adapter.pojo.user.UserCreate;
import com.h3c.idcloud.core.adapter.pojo.user.UserDelete;
import com.h3c.idcloud.core.adapter.pojo.user.UserInfoGet;
import com.h3c.idcloud.core.adapter.pojo.user.UserListGet;
import com.h3c.idcloud.core.adapter.pojo.vm.VmCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmImageCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmInfoGet;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNic;
import com.h3c.idcloud.core.adapter.pojo.vm.VmOperate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmQuery;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRebuild;
import com.h3c.idcloud.core.adapter.pojo.vm.VmReconfig;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRecovery;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRemove;
import com.h3c.idcloud.core.adapter.pojo.vm.VmResize;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotDelete;
import com.h3c.idcloud.core.adapter.pojo.vm.VmVncConsole;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class HmcVmHandlerTest {

    @Autowired
    private VmHandler vmHandler;

    @Autowired
    private StorageHandler storageHandler;

    @Autowired
    private NetHandler netHandler;

    @Autowired
    private AdminHandler adminHandler;

    @Autowired
    private ScanHandler scanHandler;

    //	@Test
    public void testVmReconfig() {
        VmReconfig reconfig = new VmReconfig();
    }

    public void testVmCreate() {

        VmCreate vmCreate = new VmCreate();

        vmCreate.setTenantId("34f11c8cba8f42fda9000982fbdcf51d");
        vmCreate.setTenantName("lileifeng0001");
        vmCreate.setProviderType("HMC");
        vmCreate.setAuthUser("BOSS-user");
        vmCreate.setAuthPass("BOSSUser!@#");

        vmCreate.setAdminPass("Hp1nvent");
        vmCreate.setName("test-llf-0417");
        vmCreate.setCpu("1");
        vmCreate.setMemory("512");
        vmCreate.setImage("e2192de3-c507-4e1c-b300-4aab69e329a4");
        vmCreate.setHostName("overcloud-ce-novacompute0-novacompute0-yxml3cq4esez");
        vmCreate.setSysDiskSize("2");
//		vmCreate.setKeyName("refar3qefrwefsdfsFsdf");
        List<VmNic> nics = new ArrayList<VmNic>();

        VmNic nic = new VmNic();
        nic.setNetId("eb3387a0-8c5c-4f40-a9ca-3b4bbbfde18e");
        //nic.setPrivateIp("192.168.7.226");

        nics.add(nic);

        vmCreate.setNics(nics);

        vmCreate.setVirtEnvType("Openstack");
        vmCreate.setVirtEnvUuid("1");

        try {
            Server server = vmHandler.createVm(vmCreate);
            System.out.println(server.isSuccess());
            System.out.println(server.getUuid());
            System.out.println(server.getName());
            System.out.println(server.getNics().get(0).getPrivateIp());
            System.out.println(server.getNics().get(0).getMac());
            System.out.println(server.getNics().get(0).getNetId());

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            System.out.println(e.getErrCode());
            System.out.println(e.getErrMsg());
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void testVmOperate() {

        VmOperate vmOperate = new VmOperate();

        vmOperate.setProviderType("Kvm");
        vmOperate.setTenantId("81e043e3856442d89fa7813cea57c530");
        vmOperate.setTenantName("demo");
        vmOperate.setAuthUser("BOSS-user");
        vmOperate.setAuthPass("BOSSUser!@#");

        vmOperate.setUuid("f8640c8f-fa53-4c23-948d-37788b431090");
        vmOperate.setAction("stop");
        vmOperate.setRebootType("HARD");

        boolean result = false;

        try {
            result = vmHandler.operateVm(vmOperate);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertTrue(result);
    }


    public void testVmRemove() {

        VmRemove vmRemove = new VmRemove();
        vmRemove.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        vmRemove.setTenantName("admin");
        vmRemove.setProviderType("Kvm");
        vmRemove.setAuthUser("BOSS-user");
        vmRemove.setAuthPass("BOSSUser!@#");
        //7a275572-9d66-4441-8908-0a20ec88da0a
        vmRemove.setId("7a275572-9d66-4441-8908-0a20ec88da0a");

        boolean result = false;

        try {
            result = vmHandler.removeVm(vmRemove);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertTrue(result);
    }

    //	@Test
    //test true
    public void testGetVmInfo() {
        VmInfoGet vmInfoGet = new VmInfoGet();

        vmInfoGet.setProviderType("Openstack");
        vmInfoGet.setTenantId("c5c42d172b144a9f831be685453410e2");
        vmInfoGet.setTenantName("lileifeng0001");
        vmInfoGet.setAuthTenant("adaptor_manage");
        vmInfoGet.setAuthUser("adaptor_manage_user");
        vmInfoGet.setAuthPass("hpinvent1");
        vmInfoGet.setProviderUrl("https://192.168.103.30:5000/v2.0");
        vmInfoGet.setVirtEnvType("Openstack");
        vmInfoGet.setVirtEnvUuid("1");
        vmInfoGet.setServerId("be2ba47d-d082-46b7-8d31-28aafb955f6f");
        Server serverInfo = null;
        try {
            serverInfo = vmHandler.getVmInfo(vmInfoGet);
            System.out.println(serverInfo.getStatus());
            System.out.println(serverInfo.getName());
            // System.out.println(serverInfo.getNetworks().get(0).getIps().get(0));

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        org.junit.Assert.assertTrue(serverInfo.isSuccess());
    }

    //	@Test
    public void testVmRebulid() {

        VmRebuild vmRebuild = new VmRebuild();
        vmRebuild.setProviderType("Kvm");
        vmRebuild.setTenantId("81e043e3856442d89fa7813cea57c530");
        vmRebuild.setTenantName("demo");
        vmRebuild.setAuthUser("BOSS-user");
        vmRebuild.setAuthPass("BOSSUser!@#");
        vmRebuild.setImageId("a7e51354-2978-4bea-aaf7-30854f6497b8");
        vmRebuild.setId("6dce4a6b-659d-4a22-b22a-a047055b9ec5");

        try {
            Server server = vmHandler.rebuildVm(vmRebuild);
            System.out.println(server.getUuid());
            System.out.println(server.getName());
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    // test true
    public void testGetVncConsole() {
        VmVncConsole vmVncConsole = new VmVncConsole();

        vmVncConsole.setProviderType("Kvm");

        vmVncConsole.setAuthUser("BOSS-user");
        vmVncConsole.setAuthPass("BOSSUser!@#");

        vmVncConsole.setId("f9f14d1d-075b-4584-a40c-9e03ab11a38f");

        try {
            String url = vmHandler.getConsoleUrl(vmVncConsole);
            System.out.println(url);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void testVmResize() {
        VmResize vmResize = new VmResize();
        vmResize.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        vmResize.setTenantName("admin");
        vmResize.setProviderType("Kvm");
        vmResize.setAuthUser("BOSS-user");
        vmResize.setAuthPass("BOSSUser!@#");
        vmResize.setId("7a275572-9d66-4441-8908-0a20ec88da0a");
        vmResize.setDisk("10");
        vmResize.setMemory("512");
        vmResize.setCpu("1");
        boolean result = false;
        try {
            result = vmHandler.resizeVm(vmResize);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertTrue(result);
    }

    //	@Test
    public void testCreateSnapshot() {
        VmSnapshotCreate vmSnapshotCreate = new VmSnapshotCreate();

        vmSnapshotCreate.setSnapshotName("testsnapshot");
        vmSnapshotCreate.setProviderType("Kvm");

        vmSnapshotCreate.setServerId("c63c4b80-5d96-427e-b439-8e0356035aab");
        vmSnapshotCreate.setAuthUser("BOSS-user");
        vmSnapshotCreate.setAuthPass("BOSSUser!@#");
        Template template = new Template();
        try {
            template = vmHandler.snapshotVm(vmSnapshotCreate);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(template.getImageId());
        System.out.println(template.getImageName());
    }

    public void testRecoveryVm() throws CommonAdapterException, AdapterUnavailableException {

        VmRecovery vmRecovery = new VmRecovery();

        vmRecovery.setImageId("");
        vmRecovery.setHost("");
        vmRecovery.setId("");

        boolean result = vmHandler.recoveryVm(vmRecovery);
        Assert.assertTrue(result);
    }

    public void testCreateVmImage() throws CommonAdapterException, AdapterUnavailableException {
        VmImageCreate vmImageCreate = new VmImageCreate();

        vmImageCreate.setImageName("");
        vmImageCreate.setId("");

        boolean result = vmHandler.imageVm(vmImageCreate);
        Assert.assertTrue(result);
    }

    //	@Test
    public void testSnapshotDelete() throws CommonAdapterException, AdapterUnavailableException {
        VmSnapshotDelete vmSnapshotDelete = new VmSnapshotDelete();
        vmSnapshotDelete.setProviderType("Kvm");
        vmSnapshotDelete.setTenantId("81e043e3856442d89fa7813cea57c530");
        vmSnapshotDelete.setTenantName("demo");
        vmSnapshotDelete.setAuthUser("BOSS-user");
        vmSnapshotDelete.setAuthPass("BOSSUser!@#");
        vmSnapshotDelete.setVirtEnvType("vcenter");
        vmSnapshotDelete.setVirtEnvUuid("1");
        vmSnapshotDelete.setServerId("c63c4b80-5d96-427e-b439-8e0356035aab");
        vmSnapshotDelete.setImageId("2f74548b-8a46-47c0-843f-fb2755093458");

        boolean result = vmHandler.deleteVmSnapshot(vmSnapshotDelete);

        Assert.assertTrue(result);
    }


    public void testBlockCreate() throws CommonAdapterException, AdapterUnavailableException {
        DiskCreate diskCreate = new DiskCreate();
        diskCreate.setName("testllf-02");
        //diskCreate.setDescription("this is test desc");
        diskCreate.setSize("1");

        diskCreate.setProviderType("Kvm");
        diskCreate.setTenantId("81e043e3856442d89fa7813cea57c530");
        diskCreate.setTenantName("demo");
        diskCreate.setAuthUser("BOSS-user");
        diskCreate.setAuthPass("BOSSUser!@#");

        Volume volume = storageHandler.createDisk(diskCreate);
        System.out.println(volume.getVolumeId());
        System.out.println(volume.getName());
    }

    public void testDetachDisk() throws CommonAdapterException, AdapterUnavailableException {
        DiskDetach diskDetach = new DiskDetach();

        diskDetach.setProviderType("Kvm");
        diskDetach.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        diskDetach.setTenantName("admin");
        diskDetach.setAuthUser("BOSS-user");
        diskDetach.setAuthPass("BOSSUser!@#");

        diskDetach.setVolumeId("");
        diskDetach.setServerId("");

        boolean result = storageHandler.detachDisk(diskDetach);

        Assert.assertTrue(result);
        ;

    }

    //	@Test
    public void testQuertyBlocks() throws CommonAdapterException, AdapterUnavailableException {

        BlockList blockList = new BlockList();

        blockList.setProviderType("OpenStack");
        blockList.setTenantId("b6dc6493-5f8f-405c-9a4b-012e2cc5e5e0");
        blockList.setTenantName("admin");
        blockList.setAuthUser("BOSS-user");
        blockList.setAuthPass("BOSSUser!@#");

        BlockListResult blockListResult = storageHandler.getBlockList(blockList);
        List<Block> blocks = blockListResult.getBlocks();
        for (Block block : blocks) {
            System.out.println(block.getId());
            System.out.println(block.getName());
        }
    }

    //	@Test
    public void testAttachDisk() throws CommonAdapterException, AdapterUnavailableException {

        DiskAttach diskAttach = new DiskAttach();

        diskAttach.setProviderType("Kvm");
        diskAttach.setTenantId("81e043e3856442d89fa7813cea57c530");
        diskAttach.setTenantName("demo");
        diskAttach.setAuthUser("BOSS-user");
        diskAttach.setAuthPass("BOSSUser!@#");

        diskAttach.setServerId("366b0a22-6ce9-4307-9358-be125b03e79b");
        diskAttach.setVolumeId("e66e6f64-107c-4756-919d-fd2389e59650");
        //ac21ff97-3d0f-4aec-9e6e-888a0ac312b3
        boolean result = storageHandler.attachDisk(diskAttach);
        Assert.assertTrue(result);
    }


    public void testDeleteDisk() throws CommonAdapterException, AdapterUnavailableException {

        DiskDelete diskDelete = new DiskDelete();

        diskDelete.setId("ddb6ca17-b434-465f-93a9-7c5661c4c617");
        diskDelete.setProviderType("Kvm");
        diskDelete.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        diskDelete.setTenantName("admin");
        diskDelete.setAuthUser("BOSS-user");
        diskDelete.setAuthPass("BOSSUser!@#");

        boolean result = storageHandler.deleteDisk(diskDelete);

        Assert.assertTrue(result);
    }

    public void testDeleteImage() throws CommonAdapterException, AdapterUnavailableException {

        ImageDelete vmImageDelete = new ImageDelete();

        vmImageDelete.setImageId("");
        vmImageDelete.setProviderType("Kvm");
        vmImageDelete.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        vmImageDelete.setTenantName("admin");
        vmImageDelete.setAuthUser("BOSS-user");
        vmImageDelete.setAuthPass("BOSSUser!@#");

        boolean result = vmHandler.deleteImage(vmImageDelete);
        Assert.assertTrue(result);
    }

    //	@Test
    public void testCreateFloatingIp() throws CommonAdapterException, AdapterUnavailableException {
        FloatingIpCreate floatingIpCreate = new FloatingIpCreate();

        floatingIpCreate.setFloatingNetworkId("1370a1c8-f8e0-4104-a55c-49efbcf4ce52");
        floatingIpCreate.setProviderType("Kvm");
        floatingIpCreate.setTenantId("81e043e3856442d89fa7813cea57c530");
        floatingIpCreate.setTenantName("demo");
        floatingIpCreate.setAuthUser("BOSS-user");
        floatingIpCreate.setAuthPass("BOSSUser!@#");

        FloatingIpCreateResult floatingIpCreateResult = netHandler.createFloatingIp(floatingIpCreate);
        System.out.println(floatingIpCreateResult.getFloatingIpAddr());
        System.out.println(floatingIpCreateResult.getFloatingIpId());
    }

    public void testDeleteFloatingId() throws CommonAdapterException, AdapterUnavailableException {

        FloatingIpDelete floatingIpDelete = new FloatingIpDelete();
        floatingIpDelete.setProviderType("Kvm");
        floatingIpDelete.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        floatingIpDelete.setTenantName("admin");
        floatingIpDelete.setAuthUser("BOSS-user");
        floatingIpDelete.setAuthPass("BOSSUser!@#");

        floatingIpDelete.setFloatingIpId("d83a5775-ecae-48b8-ab9d-35dd093c5d35");

        boolean result = netHandler.deleteFloatingIp(floatingIpDelete);

        Assert.assertTrue(result);
    }

    //	@Test
    public void testAttachFloatingIp() throws CommonAdapterException, AdapterUnavailableException {

        FloatingIpAttach floatingIpAttach = new FloatingIpAttach();

        floatingIpAttach.setProviderType("Kvm");
        floatingIpAttach.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        floatingIpAttach.setTenantName("admin");
        floatingIpAttach.setAuthUser("BOSS-user");
        floatingIpAttach.setAuthPass("BOSSUser!@#");

        floatingIpAttach.setFloatingIp("172.16.0.142");
        floatingIpAttach.setFixedIp("192.168.111.4");
        floatingIpAttach.setServerId("7a275572-9d66-4441-8908-0a20ec88da0a");

//        boolean result = netHandler.attachFloatingIp(floatingIpAttach);

//        Assert.assertTrue(result);
    }

    public void testDetachFloating() throws CommonAdapterException, AdapterUnavailableException {

        FloatingIpDetach floatingIpDetach = new FloatingIpDetach();

        floatingIpDetach.setProviderType("Kvm");
        floatingIpDetach.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        floatingIpDetach.setTenantName("admin");
        floatingIpDetach.setAuthUser("BOSS-user");
        floatingIpDetach.setAuthPass("BOSSUser!@#");

        floatingIpDetach.setFloatingIp("");
        floatingIpDetach.setServerId("");

        boolean result = netHandler.detachFloatingIp(floatingIpDetach);

        Assert.assertTrue(result);
    }

    //	@Test
    public void testTenantCreate() throws CommonAdapterException, AdapterUnavailableException {

        TenantCreate tenantCreate = new TenantCreate();

        tenantCreate.setName("testenant");
        tenantCreate.setDescription("test");

        tenantCreate.setProviderType("Kvm");
        tenantCreate.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        tenantCreate.setTenantName("admin");
        tenantCreate.setAuthUser("BOSS-user");
        tenantCreate.setAuthPass("BOSSUser!@#");

        Tenants tenants = adminHandler.createTenant(tenantCreate);
        System.out.println(tenants.getTenantCreateResult().getId());
    }

    //	@Test
    public void testTenantDelete() throws CommonAdapterException, AdapterUnavailableException {

        TenantDelete tenantDelete = new TenantDelete();

        tenantDelete.setProviderType("Kvm");
        tenantDelete.setTenantId("beb31dd7bf894b88aa73db43c32f6b85");
        tenantDelete.setTenantName("demo");
        tenantDelete.setAuthUser("BOSS-user");
        tenantDelete.setAuthPass("BOSSUser!@#");

        boolean result = adminHandler.deleteTenant(tenantDelete);

        Assert.assertTrue(result);

    }

    //	@Test
    public void testTenantResoucesDelete() throws CommonAdapterException, AdapterUnavailableException {

        TenantResourcesDelete tenantDelete = new TenantResourcesDelete();

        tenantDelete.setProviderType("Kvm");
        tenantDelete.setTenantId("beb31dd7bf894b88aa73db43c32f6b85");
        tenantDelete.setTenantName("demo");
        tenantDelete.setAuthUser("BOSS-user");
        tenantDelete.setAuthPass("BOSSUser!@#");

        boolean result = adminHandler.deleteTenantResources(tenantDelete);

        Assert.assertTrue(result);

    }

    //	@Test
    public void testUserCreate() throws CommonAdapterException, AdapterUnavailableException {
        UserCreate userCreate = new UserCreate();

        userCreate.setName("lll");
        userCreate.setPassword("password");

        userCreate.setProviderType("Kvm");
        userCreate.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        userCreate.setTenantName("admin");
        userCreate.setAuthUser("BOSS-user");
        userCreate.setAuthPass("BOSSUser!@#");

        Users users = adminHandler.createUser(userCreate);

        System.out.println(users.getUserCreateResult().getId());
    }

    //	@Test
    public void testGetUserList() throws CommonAdapterException, AdapterUnavailableException, IOException {

        UserListGet userListGet = new UserListGet();
        userListGet.setProviderType("Openstack");
        userListGet.setTenantName("1101");
        userListGet.setAuthUser("BOSS-user");
        userListGet.setAuthPass("BOSSUser!@#");

        Users users = adminHandler.queryUser(userListGet);
        List<User> usrList = users.getUserListGetResult().getUsers();
        for (int i = 0; i < usrList.size(); i++) {
            User user = BaseUtil.castObject(usrList.get(i), User.class);
            System.out.println(user.getId());
            System.out.println(user.getName());
            System.out.println(user.getTenantId());
        }
    }

    //	@Test
    public void testGetuserInfo() throws CommonAdapterException, AdapterUnavailableException {
        UserInfoGet userInfoGet = new UserInfoGet();
        userInfoGet.setUserId("77655819fe324eef86080ab8036c5dc0");

        userInfoGet.setProviderType("Openstack");
        userInfoGet.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        userInfoGet.setTenantName("admin");
        userInfoGet.setAuthUser("BOSS-user");
        userInfoGet.setAuthPass("BOSSUser!@#");

        Users users = adminHandler.getUser(userInfoGet);
        System.out.println(users.getUserInfoGetResult().getId());
        System.out.println(users.getUserInfoGetResult().getName());
        System.out.println(users.getUserInfoGetResult().getTenantId());

    }

    //	@Test
    public void testAdduserToTenant() {
        AddUserToTenant addUserToTenant = new AddUserToTenant();
        addUserToTenant.setTenantId("");
    }

    public void testRemovUserFromTe() throws CommonAdapterException, AdapterUnavailableException {
        RemoveUserFromTenant removeUserFromTenant = new RemoveUserFromTenant();
        removeUserFromTenant.setTenantId("b9faccdc77fd4223b9e78bcaeabe27a4");
        removeUserFromTenant.setRoleId("9fe2ff9ee4384b1894a90878d3e92bab");
        removeUserFromTenant.setUserId("457b2c3bb9074c05a5310674ec01c7f9");
        removeUserFromTenant.setProviderType("Openstack");
        boolean removeUserFromTenant2 = adminHandler.removeUserFromTenant(removeUserFromTenant);
    }

    public void testDeleteuser() throws CommonAdapterException, AdapterUnavailableException {

        UserDelete userDelete = new UserDelete();
        userDelete.setUserId("5662e98ea32f4a29924e87d20e64cf68");
        //userDelete.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        userDelete.setTenantName("admin");
        userDelete.setAuthUser("BOSS-user");
        userDelete.setAuthPass("BOSSUser!@#");
        userDelete.setProviderType("Kvm");

        boolean result = adminHandler.deleteUser(userDelete);
        Assert.assertTrue(result);
    }

    //	@Test
    public void getTenantList() throws CommonAdapterException, AdapterUnavailableException, IOException {

        TenantListGet tenantListGet = new TenantListGet();
        tenantListGet.setProviderType("Kvm");
        tenantListGet.setTenantName("demo");
        tenantListGet.setAuthUser("BOSS-user");
        tenantListGet.setAuthPass("BOSSUser!@#");

        Tenants tenants = adminHandler.queryTenant(tenantListGet);
        List<Tenant> tenantlisTenants = tenants.getTenantListGetResult().getTenants();

        for (int i = 0; i < tenantlisTenants.size(); i++) {
            Tenant tenant = BaseUtil.castObject(tenantlisTenants.get(i), Tenant.class);
            System.out.println(tenant.getId());
            System.out.println(tenant.getName());
        }
    }

    //@Test
    public void testGetTenantinfo() throws CommonAdapterException, AdapterUnavailableException {

        TenantInfoGet tenantInfoGet = new TenantInfoGet();
        tenantInfoGet.setProviderType("Kvm");
        tenantInfoGet.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        tenantInfoGet.setTenantName("admin");
        tenantInfoGet.setAuthUser("BOSS-user");
        tenantInfoGet.setAuthPass("BOSSUser!@#");

        Tenants tenants = adminHandler.getTenant(tenantInfoGet);

        System.out.println(tenants.getTenantInfoGetResult().getName());
        System.out.println(tenants.getTenantInfoGetResult().getId());
    }

    public void testSecurityGroupCreate() throws CommonAdapterException, AdapterUnavailableException {
        SgCreate sgCreate = new SgCreate();
        sgCreate.setProviderType("Kvm");
        sgCreate.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        sgCreate.setTenantName("admin");
        sgCreate.setAuthUser("BOSS-user");
        sgCreate.setAuthPass("BOSSUser!@#");

        sgCreate.setName("testSecurityGroup");
        sgCreate.setDescription("testSecurityGroup desc");

        SecurityGroupResult securityGroupResult = netHandler.createSecurityGroup(sgCreate);
        System.out.println(securityGroupResult.getSecurityGroup().getId());
        System.out.println(securityGroupResult.getSecurityGroup().getName());
    }

    //	@Test
    public void testQuerySecurityGroup() throws CommonAdapterException, AdapterUnavailableException {
        SecurityGroupQuery securityGroupQuery = new SecurityGroupQuery();
        securityGroupQuery.setProviderType("Openstack");
        securityGroupQuery.setTenantId("6251483f4dde46a78e96d6b002f8204f");
        securityGroupQuery.setTenantName("1109");
/*		securityGroupQuery.setTenantId("34f11c8cba8f42fda9000982fbdcf51d");
        securityGroupQuery.setTenantName("lileifeng0001");
*/
        List<SecurityGroup> querySecurityGroup = vmHandler.querySecurityGroup(securityGroupQuery);
        for (SecurityGroup securityGroup : querySecurityGroup) {
            System.out.println(securityGroup.getName());
            System.out.println(securityGroup.getId());
        }
    }

    //	@Test
    public void testAttachSg() throws CommonAdapterException, AdapterUnavailableException {

        ServerSecurityGroupAdd securityGroupAdd = new ServerSecurityGroupAdd();

        securityGroupAdd.setServerId("7a275572-9d66-4441-8908-0a20ec88da0a");
        securityGroupAdd.setSgName("testSg");
        securityGroupAdd.setProviderType("Kvm");
        securityGroupAdd.setTenantId("81e043e3856442d89fa7813cea57c530");
        securityGroupAdd.setTenantName("demo");
        securityGroupAdd.setAuthUser("BOSS-user");
        securityGroupAdd.setAuthPass("BOSSUser!@#");

        boolean result = netHandler.attachSecurityGroup(securityGroupAdd);

        Assert.assertTrue(result);

    }

    public void testDetachSg() throws CommonAdapterException, AdapterUnavailableException {

        ServerSecurityGroupDelete securityGroupDelete = new ServerSecurityGroupDelete();

        securityGroupDelete.setServerId("7a275572-9d66-4441-8908-0a20ec88da0a");
        securityGroupDelete.setName("testSecurityGroup");

        securityGroupDelete.setProviderType("Kvm");
        securityGroupDelete.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        securityGroupDelete.setTenantName("admin");
        securityGroupDelete.setAuthUser("BOSS-user");
        securityGroupDelete.setAuthPass("BOSSUser!@#");

        boolean result = netHandler.detachSecurityGroup(securityGroupDelete);

        Assert.assertTrue(result);
    }

    public void testCreateSgRule() throws CommonAdapterException, AdapterUnavailableException {
        SgRuleCreate sgRuleCreate = new SgRuleCreate();

        sgRuleCreate.setProviderType("Kvm");
        sgRuleCreate.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        sgRuleCreate.setTenantName("admin");
        sgRuleCreate.setAuthUser("BOSS-user");
        sgRuleCreate.setAuthPass("BOSSUser!@#");

        sgRuleCreate.setSecurityGroupId("68aa71c2-7ac6-4c95-9a40-2f7bbd68e0a1");
        sgRuleCreate.setDirection("ingress");
        sgRuleCreate.setPortRangMin("1");
        sgRuleCreate.setPortRangeMax("2");
        sgRuleCreate.setEthertype("IPv4");
        sgRuleCreate.setProtocol("tcp");
        sgRuleCreate.setRemoteIpPrefix("192.168.0.0/24");

        SecurityGroupRulesResult sgRuleCreateResult = netHandler.createSgRule(sgRuleCreate);

        System.out.println(sgRuleCreateResult.getSecurityGroupRules().getSecurityGroupId());
        System.out.println(sgRuleCreateResult.getSecurityGroupRules().isSuccess());
    }


    public void testCreateNet() throws CommonAdapterException, AdapterUnavailableException {
        NetCreate netCreate = new NetCreate();

        netCreate.setProviderType("Kvm");
        netCreate.setTenantId("81e043e3856442d89fa7813cea57c530");
        netCreate.setTenantName("demo");
        netCreate.setAuthUser("BOSS-user");
        netCreate.setAuthPass("BOSSUser!@#");

        netCreate.setName("testCreateNet");
        netCreate.setCidr("192.168.2.0/24");
        netCreate.setGateway("192.168.2.1");
//        netCreate.setExtNetId("1370a1c8-f8e0-4104-a55c-49efbcf4ce52");

        /*NetworkResult network = netHandler.createNet(netCreate);
        System.out.println(network.getNetwork().getName());
        System.out.println(network.getNetwork().getId());*/
    }

    //	@Test
    public void testCreateSg() throws CommonAdapterException, AdapterUnavailableException {

        SgCreate sgCreate = new SgCreate();
        sgCreate.setProviderType("Kvm");
        sgCreate.setTenantId("81e043e3856442d89fa7813cea57c530");
        sgCreate.setTenantName("demo");
        sgCreate.setAuthUser("BOSS-user");
        sgCreate.setAuthPass("BOSSUser!@#");
        sgCreate.setName("testSg");
        sgCreate.setDescription("sg test desc");

        SecurityGroupResult securityGroupResult = netHandler.createSecurityGroup(sgCreate);

        System.out.println(securityGroupResult.getSecurityGroup().getId());
        System.out.println(securityGroupResult.getSecurityGroup().getName());
    }


    /*	public void testAttachSg() {
            ServerSecurityGroupAdd securityGroupAdd = new ServerSecurityGroupAdd();

            securityGroupAdd.setProviderType("Kvm");
            securityGroupAdd.setName("");
            securityGroupAdd.setServerId("");
            securityGroupAdd.setTenantName("demo");
            securityGroupAdd.setTenantId("");

            boolean result = vmHandler.addSecurityGroup(securityGroupAdd);
        }*/
//	@Test
    public void testDeleteSg() throws CommonAdapterException, AdapterUnavailableException {
        SgDelete sgDelete = new SgDelete();
        sgDelete.setProviderType("Kvm");
        sgDelete.setTenantId("c5c42d172b144a9f831be685453410e2");
        sgDelete.setTenantName("lileifeng");
        sgDelete.setAuthUser("BOSS-user");
        sgDelete.setAuthPass("BOSSUser!@#");

        sgDelete.setSecurityGroupId("e0a8ea82-7173-4b9b-9498-8a06197550a1");
        boolean result = netHandler.deleteSecurityGroup(sgDelete);

        Assert.assertTrue(result);
    }

    /*public void testUserCreate() {

        UserCreate userCreate = new UserCreate();
        userCreate.setProviderType("Kvm");
        userCreate.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
        userCreate.setTenantName("admin");
        userCreate.setAuthUser("BOSS-user");
        userCreate.setAuthPass("BOSSUser!@#");
    }*/
//	@Test
    public void testQueryVm() throws CommonAdapterException, AdapterUnavailableException {
        VmQuery vmQuery = new VmQuery();
        vmQuery.setProviderType("Openstack");

        List<Server> servers = vmHandler.queryVms(vmQuery);
        for (Server server : servers) {
            System.out.println(server.getUuid());
            System.out.println(server.getName());
        }
    }

    @Test
    public void testSgruleCreate() {
        SgRuleCreate sgRuleCreate = new SgRuleCreate();
        sgRuleCreate.setProtocol("fff");
        sgRuleCreate.setRemoteIpPrefix("ffff");
        sgRuleCreate.setSecurityGroupId("rrr");
        sgRuleCreate.setEthertype("ipv4");
        sgRuleCreate.setDirection("in");
//		sgRuleCreate.setPortRangMin("-1");
//		sgRuleCreate.setPortRangeMax("-1");
        sgRuleCreate.setProviderType("Openstack");
        sgRuleCreate.setVirtEnvType("Openstack");
        sgRuleCreate.setVirtEnvUuid("1");
        try {
            netHandler.createSgRule(sgRuleCreate);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //	@Test
    public void testQueryTempalte() throws CommonAdapterException, AdapterUnavailableException {

        TemplateScan templateScan = new TemplateScan();
        templateScan.setProviderType("Openstack");
        templateScan.setAuthTenant("adaptor_manage");
        templateScan.setAuthUser("adaptor_manage_user");
        templateScan.setAuthPass("hpinvent");
        templateScan.setProviderUrl("https://192.168.103.30:5000/v2.0");
        templateScan.setVirtEnvType("Openstack");
        templateScan.setVirtEnvUuid("1");

        List<Template> templates = scanHandler.scanTemplate(templateScan);

        for (Template template : templates) {
            System.out.println(template.getImageName());
            System.out.println(template.getUuid());
        }
    }

    //	@Test
    public void testQueryKeyPair() throws CommonAdapterException, AdapterUnavailableException {
        KeypairListGet keypairListGet = new KeypairListGet();

        keypairListGet.setProviderType("Openstack");
        keypairListGet.setTenantId("34f11c8cba8f42fda9000982fbdcf51d");
        keypairListGet.setTenantName("lileifeng0001");

        KeyPairResult keyPairList = adminHandler.getKeyPairList(keypairListGet);

        KeypairListGetResult result = keyPairList.getKeypairListGetResult();
        List<KeyVo> vos = result.getKeyVos();
        for (KeyVo keyVo : vos) {
            System.out.println(keyVo.getName());
        }
    }

    //	@Test
    public void testCreateKeyPair() throws CommonAdapterException, AdapterUnavailableException {
        KeypairCreate create = new KeypairCreate();

        create.setProviderType("Openstack");
        create.setTenantId("34f11c8cba8f42fda9000982fbdcf51d");
        create.setTenantName("lileifeng0001");
        create.setName("leifeng_key");
        KeyPairResult createKeyPair = adminHandler.createKeyPair(create);
        System.out.println(createKeyPair.getKeypairCreateResult().getKeyVo().getPrivateKey());
    }

    //	@Test
    public void testDeleteKeyPair() {
        KeypairDelete delete = new KeypairDelete();
        delete.setProviderType("Openstack");
        delete.setTenantId("34f11c8cba8f42fda9000982fbdcf51d");
        delete.setTenantName("lileifeng0001");
        delete.setKeyName("dasdada");
        try {
            adminHandler.deletekeyPair(delete);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getErrMsg());
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //	@Test
    public void testQueryKeyPairInfo() throws CommonAdapterException, AdapterUnavailableException {

        KeypairGet keyget = new KeypairGet();
        keyget.setProviderType("Openstack");
        keyget.setTenantId("34f11c8cba8f42fda9000982fbdcf51d");
        keyget.setTenantName("lileifeng0001");
        keyget.setKeyName("leifengs_key");

        KeyPairResult keyPairInfo = adminHandler.getKeyPairInfo(keyget);
        KeypairGetResult result = keyPairInfo.getKeypairGetResult();
        System.out.println(result.getKeyVo().getPublicKey());
    }
}
